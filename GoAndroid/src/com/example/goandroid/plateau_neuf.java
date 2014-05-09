package com.example.goandroid;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class plateau_neuf extends MainActivity{
	
	private View maVue;
	//private Plateau plateau;
	private int taille_plateau = 9;
	private int couleur_pion = 1;
	private MediaPlayer mPlayer = null;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
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

              	/******************************************************/
            	/*							Codes					  */
            	/******************************************************/
                action = event.getAction();
                x = event.getX();
                y = event.getY();
                xI = maVue.getWidth();
                xY = maVue.getHeight();
                float x_grille = (float) (xI - ((xI/27.027)*2));
                float y_grille = (float) (xY - (xY/27.027) - (xY/27.027));
                //Toast.makeText(plateau_neuf.this, "taille grille "+x_grille, (int)2000).show();
                px = (int) (x/(x_grille/(taille_plateau-1)))+1;
                py = (int) (y/(y_grille/(taille_plateau-1)))+1;
                //Toast.makeText(plateau_neuf.this, "touché en ("+px+" / "+py+")", (int)2000).show();
                float cx = (float) x_grille/(taille_plateau-1);
                //Toast.makeText(plateau_neuf.this, "taille case ("+cx+")", (int)2000).show();
                float cy = (float) y_grille/(taille_plateau-1);
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
                		//Drawable test = drawable(R.drawable.pion_noir);
                		
                    	//Toast.makeText(plateau_neuf.this, "touché en ("+px+" / "+py+")", (int)2000).show();
                    	//Test
                    	Bitmap pion_noir = BitmapFactory.decodeResource(getResources(),R.drawable.pion_noir);
                        Bitmap pion_blanc = BitmapFactory.decodeResource(getResources(),R.drawable.pion_blanc);
                    	ImageView iv = (ImageView) findViewById(R.id.imageView1);
                    	Bitmap le_pion;
                    	TextView tour_joueur = (TextView) findViewById(R.id.tour_joueur);
                    	if((couleur_pion % 2)==0){
                    		le_pion = pion_blanc;
                    		tour_joueur.setText("Joueur Noir");
                    	}else{
                    		le_pion = pion_noir;
                    		tour_joueur.setText("Joueur Blanc");
                    	}
                    	
                    	
                    	/*le_pion.setWidth(xI/10);
                    	le_pion.setHeight(xY/10);*/
                    	
                    	int taille_pion = (int)(xI/10)/2;
                    	le_pion = getResizedBitmap(le_pion, taille_pion, taille_pion);
                    	int width = le_pion.getWidth();                   	
                    	drawImg(iv, (cx+(width/4)), (cy+(width/4)), le_pion);
                    	playSound(R.raw.poser);
                    	couleur_pion++;
                        
                    }
                }      
                return true;
            }
        });
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
		//Toast.makeText(this,message,Toast.LENGTH_LONG).show();
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
}
