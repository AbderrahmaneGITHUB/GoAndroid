package com.example.goandroid;

import java.util.List;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import constante.Constante;
import enumeration.Couleur;
import enumeration.Erreur;
import enumeration.PasseOuJoue;

public class plateau_treize extends MainActivity{
	public View maVue;
	private int taille_plateau;
	private int couleur_pion;
	private float x_grille, y_grille, x_case, y_case;
	private MediaPlayer mPlayer = null;
	public  ImageView image_plateau;
	private Canvas canva;
	private Bitmap bitmap;
	private Paint p = new Paint();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		/******************************************************/
    	/*					Initialisation					  */
    	/******************************************************/
		image_plateau = (ImageView) this.findViewById(R.id.imageView1);
		taille_plateau = 13;
		couleur_pion = 1;
    	initTaillePlateau(Constante.TAILLEPLATEAU_13);
		intialisationOfSaound(R.raw.asian_dream);
		playSound();
		super.onCreate(savedInstanceState);                   
        setContentView(R.layout.plateau_treize);     
        maVue = findViewById(R.id.imageView1);
        //Log.d("AS_TEST", "taille : " + maVue.getWidth());
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
                
                x_grille = (float) (xI - ((xI/100*3.75)*2));
                y_grille = (float) (xY - (xY/100*3.75) - (xY/100*3.75));

                px 				= (int) (x/(x_grille/(taille_plateau-1)))+1;
                py 				= (int) (y/(y_grille/(taille_plateau-1)))+1;
                laPosition.x 	= px-1;
                laPosition.y 	= py-1;

                float cx 	= (float) x_grille/(taille_plateau-1);
                x_case 		= cx;

                float cy 	= (float) y_grille/(taille_plateau-1);
                y_case 		= cy;
                cx 			= (float) ((px-1)*cx);
                cy 			= (float) ((py-1)*cy);

                if (action==MotionEvent.ACTION_UP)
                {
                	if(px>13 || px<=0 || py>13 || py<=0)
                	{
                		Toast.makeText(plateau_treize.this, "Hors du plateau", (int)2000).show();
                    }
                	else
                    {
                    	Bitmap pion_noir  = BitmapFactory.decodeResource(getResources(),R.drawable.pion_noir);
                        Bitmap pion_blanc = BitmapFactory.decodeResource(getResources(),R.drawable.pion_blanc);
                    	Bitmap le_pion;
                    	TextView tour_joueur = (TextView) findViewById(R.id.tour_joueur);
                    	
                    	if((couleur_pion % 2) == 0 && couleur_pion != 1){
                    		laCouleur = Couleur.BLANC;
                    	}else{
                    		laCouleur = Couleur.NOIR;
                    	}
                    	                    	
                    	//récupération du core erreur renvoyé
                    	erreur = realiserAction(laCouleur, laPosition, PasseOuJoue.JOUE);
                    	//effaceImages(); 
                    	//Log.d("AS_TEST", "erreur : " + erreur.toString());
                    	switch(erreur){                    		
	                    	case NO_ERREUR_OK:
	                    		if((couleur_pion % 2) == 0 && couleur_pion != 1){
	                        		le_pion = pion_blanc;
	                        		tour_joueur.setText("Joueur Noir"); 
	                        	}else{
	                        		le_pion = pion_noir;
	                        		tour_joueur.setText("Joueur Blanc");
	                        	}
	                    		unPion.couleur  = laCouleur;
	                    		unPion.position = laPosition;                    		
		                    	
		                    	playSound_touche(R.raw.poser);
		                    	couleur_pion++;
		                    	afficherPlateau(plateau.positionPlateau);		                    	
	                    		break;
	                    		
	                    	case FIN_DE_LA_PARTIE:
	                    		// TODO : il faut proposer à l'utilisateur d'entregistrer la partie	                    		
	                    		break;
	                    		
	                    	case PASSE:	                    		
	                    		break;
	                    		
	                    	case ERR_PION_NON_VALIDE:	                 		
	                    	case ERR_EMPLACEMENT_OCCUPE:	                    			             	                    		
            				case ERR_PION_HORS_PLATEAU:	                    	
	                		default:
	                			//Log.d("AS_TEST", "erreur : " + erreur.toString());
                    			break;                    	
                    	}                    	                    	
                    }
                }      
                return true;
            }
        });
    	//test(plateau.positionPlateau,image_plateau);
    } 
	
	public void traitement(Couleur inCouleur, Position inPosition, PasseOuJoue inPasseOuJoue)
	{
		/******************************************************/
    	/*				Declaration variables				  */
    	/******************************************************/
		Erreur erreur;
		
		/******************************************************/
    	/*							Codes					  */
    	/******************************************************/    
		//récupération du core erreur renvoyé
    	erreur = realiserAction(inCouleur, inPosition, inPasseOuJoue);
    	//Log.d("AS_TEST", "erreur : " + erreur.toString());
    	switch(erreur){
    		
        	case NO_ERREUR_OK:	                    		
        		                    	
        		break;
        		
        	case FIN_DE_LA_PARTIE:
        		// TODO : il faut proposer à l'utilisateur d'entregistrer la partie	                    		
        		break;
        		
        	case PASSE:
        		int z =5;
        		break;
        		
        	case ERR_PION_NON_VALIDE:	                 		
        	case ERR_EMPLACEMENT_OCCUPE:	                    			             	                    		
			case ERR_PION_HORS_PLATEAU:	                    	
    		default:
    			//Log.d("AS_TEST", "erreur : " + erreur.toString());
    			break;                    	
    	}   			
	}
	
	/*****************************************************************/
	/** 				Placement des pions 					   ***/
	/*****************************************************************/	
	private void drawImg(float x, float y, Bitmap pion){			    
	    canva.drawBitmap(pion, x, y,p);	    
	   
	    // Recopie la nouvelle image bmp dans le contenu de l'ImageView	    
	    
	}
	
	private void afficheImages(){
		((ImageView) maVue).setImageBitmap(bitmap);
	}
	
	private void effaceImages(){
		int x = maVue.getWidth();
		int y = maVue.getHeight();
		//int test = bmp.getWidth();
		//Log.d("AS_TEST", "width bmp :"+test);
		ImageView image = (ImageView)findViewById(R.id.imageView1);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.go13);
		bmp = getResizedBitmap(bmp, x, y);		
		canva.drawBitmap(bmp, 0, 0,p);	
		((ImageView) maVue).setImageBitmap(bmp); 
	}
	
	/*****************************************************************/
	/** 				Placement des pions 					   ***/
	/*****************************************************************/	
	public void passer_tour(View v){
		/******************************************************/
    	/*				Declaration variables				  */
    	/******************************************************/
		couleur_pion ++;
		
		/******************************************************/
    	/*							Codes					  */
    	/******************************************************/    	
		TextView tour_joueur = (TextView) findViewById(R.id.tour_joueur);
		if((couleur_pion % 2)==0){
    		tour_joueur.setText("Joueur Noir");
    		// On execute cette fonction afin d'ajouter l'action dans la liste des actions
    		traitement(Couleur.BLANC, new Position(), PasseOuJoue.PASSE);
    	}else{
    		tour_joueur.setText("Joueur Blanc");
    		traitement(Couleur.NOIR, new Position(), PasseOuJoue.PASSE);
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
		int taille 	= 13;
		float x,y;
		Bitmap pion_noir;
        Bitmap pion_blanc;
    	Bitmap le_pion;
        ImageView iv;
		/**************************************************/
		/*						Codes					  */
		/**************************************************/
        effaceImages();
		pion_noir = BitmapFactory.decodeResource(getResources(),R.drawable.pion_noir);
        pion_blanc = BitmapFactory.decodeResource(getResources(),R.drawable.pion_blanc);
    	iv = (ImageView) findViewById(R.id.imageView1);	
	
		for(int j=0; j < taille; j++)
		{
			for(int k=0; k < taille; k++)
			{				
            	if(listDesPion.get(z).couleur != Couleur.RIEN){
            		if(listDesPion.get(z).couleur==Couleur.NOIR){
                		le_pion = pion_noir;
                	}else{
                		le_pion = pion_blanc;
                	}
	            	x = (listDesPion.get(z).position.x)*x_case;
	            	y = (listDesPion.get(z).position.y)*y_case;
	            	int width = le_pion.getWidth();  
	            	int taille_pion = (int)(x_grille/10)/2;
	            	le_pion = getResizedBitmap(le_pion, taille_pion, taille_pion);                 	
	            	drawImg((x+(width/4)), (y+(width/4)), le_pion);
            	}
				z++;
			}
		}			
		afficheImages();		
		
	}	
	
	public void onWindowFocusChanged(boolean hasFocus) {
		int xI,xY;
	    super.onWindowFocusChanged(hasFocus);
		 
	     maVue = findViewById(R.id.imageView1);
		 xI = maVue.getWidth();
	     xY = maVue.getHeight();   
	     
	     //Log.d("AS_TEST", "taille : ("+xI+"/"+xY+")");
	     bitmap = Bitmap.createBitmap(xI,xY, Config.ARGB_8888);
	     canva = new Canvas(bitmap);
	     canva.save(1);
	     maVue.draw(canva);
    } 
}
