package com.example.goandroid;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
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
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
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
                float x_grille = (float) (taille_plateau - (taille_plateau/30.6) - (taille_plateau/22));
                float y_grille = (float) (taille_plateau - (taille_plateau/28.6) - (taille_plateau/22));
                px = (int) (x/(xI/x_grille))+1;
                py = (int) (y/(xY/y_grille))+1;
                float cx = (float) xI/x_grille;
                float cy = (float) xY/y_grille;
                cx = (float) ((px-1)*cx+(taille_plateau/30.6));
                cy = (float) ((py-1)*cy+(taille_plateau/28.6));
                
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
                    	drawImg(iv, cx, cy, le_pion);
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
		
}
