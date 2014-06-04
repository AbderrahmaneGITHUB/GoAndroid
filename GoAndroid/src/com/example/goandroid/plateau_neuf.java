package com.example.goandroid;

import java.util.List;

import constante.Constante;
import structure.Pion;
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
	private int taille_plateau;
	private int couleur_pion;
	private float x_grille, y_grille, x_case, y_case;
	private MediaPlayer mPlayer = null;
	public  ImageView image_plateau;
	private Canvas canva;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		/******************************************************/
    	/*					Initialisation					  */
    	/******************************************************/
		image_plateau = (ImageView) this.findViewById(R.id.imageView1);
		taille_plateau = 9;
		couleur_pion = 1;
    	initTaillePlateau(Constante.TAILLEPLATEAU_9);
		intialisationOfSaound(R.raw.asian_dream);
		playSound();
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
                Pion unPion ;
                Erreur erreur;
                Position laPosition;
                Couleur laCouleur;                              	
            	            	
              	/******************************************************/
            	/*							Codes					  */
            	/******************************************************/                                                               
                action 	= event.getAction();
                x 		= event.getX();
                y 		= event.getY();
                xI 		= maVue.getWidth();
                xY 		= maVue.getHeight();
                unPion 	= new Pion();
                laPosition = new Position();
                
                x_grille = (float) (xI - ((xI/27.027)*2));
                y_grille = (float) (xY - (xY/27.027) - (xY/27.027));
                //Toast.makeText(plateau_neuf.this, "taille grille "+x_grille, (int)2000).show();
                px 				= (int) (x/(x_grille/(taille_plateau-1)))+1;
                py 				= (int) (y/(y_grille/(taille_plateau-1)))+1;
                laPosition.x 	= px-1;
                laPosition.y 	= py-1;
                //Toast.makeText(plateau_neuf.this, "touché en ("+px+" / "+py+")", (int)2000).show();
                float cx 	= (float) x_grille/(taille_plateau-1);
                x_case 		= cx;
                //Toast.makeText(plateau_neuf.this, "taille case ("+cx+")", (int)2000).show();
                float cy 	= (float) y_grille/(taille_plateau-1);
                y_case 		= cy;
                cx 			= (float) ((px-1)*cx);
                cy 			= (float) ((py-1)*cy);
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
                    	Bitmap le_pion;
                    	TextView tour_joueur = (TextView) findViewById(R.id.tour_joueur);
                    	if((couleur_pion % 2) == 0 && couleur_pion != 1){
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
                    	switch(erreur){
                    		
	                    	case NO_ERREUR_OK:	                    		
	                    		unPion.couleur  = laCouleur;
	                    		unPion.position = laPosition;                    		
		                    	int taille_pion = (int)(xI/10)/2;
		                    	le_pion = getResizedBitmap(le_pion, taille_pion, taille_pion);
		                    	playSound_touche(R.raw.poser);
		                    	couleur_pion++;
		                    	afficherPlateau(plateau.positionPlateau);		                    	
	                    		break;
	                    		
	                    	case FIN_DE_LA_PARTIE:
	                    		// TODO : il faut proposer à l'utilisateur d'entregistrer la partie	                    		
	                    		break;
	                    		
	                    	case ERR_PION_NON_VALIDE:	                 		
	                    	case ERR_EMPLACEMENT_OCCUPE:	                    			             	                    		
            				case ERR_PION_HORS_PLATEAU:	                    	
	                		default:
	                			Log.d("AS_TEST", "erreur : " + erreur.toString());
                    			break;
                    	
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
	
	private void playSound_touche(int resId) {
	    if(mPlayer != null) {
	        mPlayer.stop();
	        mPlayer.release();
	    }
	    mPlayer = MediaPlayer.create(this, resId);
	    mPlayer.start();
	}
	
	public void afficherPlateau(List<Pion> listDesPion){
		/******************************************************************/
		/*				Declaration des variables globales		   		  */
		/******************************************************************/
		int z 		= 0;
		int taille 	= 9;
		float x,y;
		Bitmap pion_noir;
        Bitmap pion_blanc;
    	Bitmap le_pion;
        ImageView iv;
		/**************************************************/
		/*						Codes					  */
		/**************************************************/
		pion_noir = BitmapFactory.decodeResource(getResources(),R.drawable.pion_noir);
        pion_blanc = BitmapFactory.decodeResource(getResources(),R.drawable.pion_blanc);
    	iv = (ImageView) findViewById(R.id.imageView1);	
	
		for(int j=0; j < taille; j++)
		{
			for(int k=0; k < taille; k++)
			{				
            	if(listDesPion.get(z).couleur != Couleur.RIEN){
            		if(listDesPion.get(z).couleur==Couleur.NOIR){
                		le_pion = pion_blanc;
                	}else{
                		le_pion = pion_noir;
                	}
	            	x = (listDesPion.get(z).position.x)*x_case;
	            	y = (listDesPion.get(z).position.y)*y_case;
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
		
		 xI = maVue.getWidth();
         xY = maVue.getHeight();      

         Bitmap bmp = Bitmap.createBitmap(xI,xY, Config.ARGB_8888);
         canva = new Canvas(bmp);
	    }
}
