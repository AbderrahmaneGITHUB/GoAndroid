package com.example.goandroid;

import java.util.List;

import structure.Pion;
import structure.Plateau;
import structure.Position;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import enumeration.Couleur;
import enumeration.Erreur;
import enumeration.PasseOuJoue;

public class plateau_neuf extends MainActivity{
	
	private View maVue;
	private int taille_plateau = 9;
	private Plateau plateau = new Plateau(taille_plateau);
	private int couleur_pion = 1;
	private int xImage,yImage;
	private float x_grille,y_grille,x_case,y_case;
	private MediaPlayer mPlayer = null;
	//public ImageView image_plateau = (ImageView) this.findViewById(R.id.imageView1);
	public ImageView image_plateau;
	public Pion pion = new Pion();
	private Canvas canva;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		image_plateau = (ImageView) this.findViewById(R.id.imageView1);
		
		if(mPlayer != null) {
	        mPlayer.stop();
	        mPlayer.release();
	    }
		
        super.onCreate(savedInstanceState);
                   
        setContentView(R.layout.plateau_neuf);
        
        maVue = findViewById(R.id.imageView1);
    	
    	maVue.setOnTouchListener(
    			
    	new View.OnTouchListener() {
            public boolean onTouch(View myView, MotionEvent event) {            	            
            	/******************************************************/
            	/*				Declaration variables				  */
            	/******************************************************/
                int action, xI, xY, px, py;
                float x, y;
                Pion unPion = new Pion();
                Erreur erreur;
                Position laPosition = new Position();
                Couleur laCouleur;
                
              	/******************************************************/
            	/*							Codes					  */
            	/******************************************************/
                action = event.getAction();
                x = event.getX();
                y = event.getY();
                xI = maVue.getWidth();
                xY = maVue.getHeight();

                x_grille = (float) (xI - ((xI/27.027)*2));
                y_grille = (float) (xY - (xY/27.027) - (xY/27.027));
                //Toast.makeText(plateau_neuf.this, "taille grille "+x_grille, (int)2000).show();
                px = (int) (x/(x_grille/(taille_plateau-1)))+1;
                py = (int) (y/(y_grille/(taille_plateau-1)))+1;
                laPosition.x = px;
                laPosition.y = py;
                //Toast.makeText(plateau_neuf.this, "touché en ("+px+" / "+py+")", (int)2000).show();
                float cx = (float) x_grille/(taille_plateau-1);
                x_case = cx;
                //Toast.makeText(plateau_neuf.this, "taille case ("+cx+")", (int)2000).show();
                float cy = (float) y_grille/(taille_plateau-1);
                y_case = cy;
                cx = (float) ((px-1)*cx);
                cy = (float) ((py-1)*cy);
                //Toast.makeText(plateau_neuf.this, "touché en ("+cx+" / "+cy+")", (int)2000).show();
                if (action==MotionEvent.ACTION_UP)
                {
                	if(px>9 || px<=0 || py>9 || py<=0)
                	{
                		Toast.makeText(plateau_neuf.this, "Hors du plateau", (int)2000).show();
                    }
                	else
                    {
                    	Bitmap pion_noir = BitmapFactory.decodeResource(getResources(),R.drawable.pion_noir);
                        Bitmap pion_blanc = BitmapFactory.decodeResource(getResources(),R.drawable.pion_blanc);
                    	ImageView iv = (ImageView) findViewById(R.id.imageView1);
                    	Bitmap le_pion;
                    	TextView tour_joueur = (TextView) findViewById(R.id.tour_joueur);
                    	if((couleur_pion % 2)==0 && couleur_pion !=1){
                    		le_pion = pion_noir;
                    		laCouleur = Couleur.NOIR;
                    		tour_joueur.setText("Joueur Noir");
                    	}else{
                    		le_pion = pion_blanc;
                    		laCouleur = Couleur.BLANC;
                    		tour_joueur.setText("Joueur Blanc");
                    	}
                    	 
                    	//récupération du core erreur renvoyé
                    	erreur = realiserAction(laCouleur, laPosition, PasseOuJoue.JOUE);
                    	//Log.d("AS_TEST", "erreur : " + erreur.toString());
                    	if(erreur==Erreur.NO_ERREUR_OK){
                    		unPion.couleur = laCouleur;
                    		unPion.position = laPosition;
                    		unPion.placerPionEn(plateau, unPion);
	                    	int taille_pion = (int)(xI/10)/2;
	                    	le_pion = getResizedBitmap(le_pion, taille_pion, taille_pion);
	                    	int width = le_pion.getWidth();  
	                    	
	                    	//drawImg(iv, (cx+(width/4)), (cy+(width/4)), le_pion);
	                    	playSound(R.raw.poser);
	                    	couleur_pion++;
	                    	afficherPlateau(plateau.positionPlateau);
                    	}else{
                    		Log.d("AS_TEST", "erreur : " + erreur.toString());
                    	}
                    }
                }      
                return true;
            }
        });
    	//test(plateau.positionPlateau,image_plateau);
    } 
	
	/***********************************/
	/** Placement des pions ************/
	/***********************************/	
	private void drawImg(ImageView iv, float x, float y, Bitmap pion){
		int tx = iv.getWidth();
		int ty = iv.getHeight();
		// Création d'un bitmap vide de la même taille que celui associé à l'ImageView
		Bitmap bmp = Bitmap.createBitmap(tx,ty, Config.ARGB_8888);
		String message = "Bitmap tx:"+tx+", ty:"+ty;
		// Un Canvas est associé à un bitamp et gère le dessin d'éléments graphiques sur ce bitmap
		// voir : http://developer.android.com/reference/android/graphics/Canvas.html
	    Canvas c = new Canvas(bmp);
	    // Indispensable pour que le dessin soit réalisé sur le bitmap
	    // Le bitmap du dans l'ImageView est recopié dans le Bitmap bmp
	    iv.draw(c);
	    // Fixe les propriété de l'élément pour peindre
	    Paint p = new Paint();
	    c.drawBitmap(pion, x, y,p);	    
	    // Recopie la nouvelle image bmp dans le contenu de l'ImageView
	    iv.setImageBitmap(bmp);
	}
	
	public void passer_tour(View v){
		couleur_pion ++;
		TextView tour_joueur = (TextView) findViewById(R.id.tour_joueur);
		if((couleur_pion % 2)==0){
    		tour_joueur.setText("Joueur Noir");
    	}else{
    		tour_joueur.setText("Joueur Blanc");
    	}
	}
		
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	
	}	
	
	private void playSound(int resId) {
	    if(mPlayer != null) {
	        mPlayer.stop();
	        mPlayer.release();
	    }
	    mPlayer = MediaPlayer.create(this, resId);
	    mPlayer.start();
	}
	
	public void afficherPlateau(List<Pion> pion){
		int z = 0;
		int taille = 9;
		float x,y;
		Plateau plateau = new Plateau(taille_plateau);
		Bitmap pion_noir = BitmapFactory.decodeResource(getResources(),R.drawable.pion_noir);
        Bitmap pion_blanc = BitmapFactory.decodeResource(getResources(),R.drawable.pion_blanc);
    	ImageView iv = (ImageView) findViewById(R.id.imageView1);
    	
    	Bitmap le_pion;
    	TextView tour_joueur = (TextView) findViewById(R.id.tour_joueur);
    	
		for(int j=0; j < taille; j++)
		{
			for(int k=0; k < taille; k++)
			{				
            	//Toast.makeText(plateau_neuf.this, "couleur : ("+pion.get(z).couleur+")", (int)2000).show();
            	if(pion.get(z).couleur != Couleur.RIEN){
            		if(pion.get(z).couleur==Couleur.NOIR){
                		le_pion = pion_blanc;
                	}else{
                		le_pion = pion_noir;
                	}
            		//Toast.makeText(plateau_neuf.this, "couleur : ("+pion.get(z).couleur+")", (int)2000).show();
	            	x = (pion.get(z).position.x-1)*x_case;
	            	y = (pion.get(z).position.y-1)*y_case;
            		//Log.d("AS_TEST", "("+x+"/"+y+") "+pion.get(z).couleur.toString());
	            	//Toast.makeText(plateau_neuf.this, "posé en ("+x+" / "+y+")", (int)2000).show();
	            	int taille_pion = (int)(xImage/10)/2;
	            	//le_pion = getResizedBitmap(le_pion, taille_pion, taille_pion);
	            	int width = le_pion.getWidth();                   	
	            	drawImg(iv, (x+(width/4)), (y+(width/4)), le_pion);
            	}
				z++;
			}
		}	
	}
	
	
	public void onWindowFocusChanged(boolean hasFocus) {
		int xI,xY;
	    super.onWindowFocusChanged(hasFocus);
	  /*pion.couleur = Couleur.NOIR;
		pion.position.x=2;
		pion.position.y=5;*/
		
		 xI = maVue.getWidth();
         xY = maVue.getHeight();      
           
         //ImageView img = (ImageView) findViewById(R.id.imageView1);
         Bitmap bmp = Bitmap.createBitmap(xI,xY, Config.ARGB_8888);
         canva = new Canvas(bmp);
	    }
}
