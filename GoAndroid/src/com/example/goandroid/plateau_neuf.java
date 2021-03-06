package com.example.goandroid;

import java.util.List;

import structure.Pion;
import structure.Position;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import constante.Constante;
import enumeration.Couleur;
import enumeration.Erreur;
import enumeration.PasseOuJoue;

public class plateau_neuf extends MainActivity{
	
	public View maVue;
	private int taille_plateau;
	private int couleur_pion;
	private int nb_passe = 0;
	private float x_grille, y_grille, x_case, y_case;
	protected static MediaPlayer mPlayerPion = null;
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
		taille_plateau = 9;
		couleur_pion = 1;
		
		scoreJoueurs.scoreJoueurBlanc = 0;
		scoreJoueurs.scoreJoueurNoir = 0;
		
		if(plateau == null){
			initTaillePlateau(Constante.TAILLEPLATEAU_9);
		}
		intialisationOfSaound(R.raw.asian_dream);
		if(NoMusic!=false){
			playSound();
		}
		super.onCreate(savedInstanceState);                   
        setContentView(R.layout.plateau_neuf);     
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
                
               /* x_grille = (float) (xI - ((xI/100*3.75)*2));
                y_grille = (float) (xY - (xY/100*3.75) - (xY/100*3.75));*/
                //Toast.makeText(plateau_neuf.this, "taille grille "+x_grille, (int)2000).show();
                px 				= (int) (x/(x_grille/(taille_plateau-1)))+1;
                py 				= (int) (y/(y_grille/(taille_plateau-1)))+1;
                laPosition.x 	= px-1;
                laPosition.y 	= py-1;
                //Toast.makeText(plateau_neuf.this, "touché en ("+px+" / "+py+")", (int)2000).show();
                /*float cx 	= (float) x_grille/(taille_plateau-1);
                x_case 		= cx;
                //Toast.makeText(plateau_neuf.this, "taille case ("+cx+")", (int)2000).show();
                float cy 	= (float) y_grille/(taille_plateau-1);
                y_case 		= cy;
                cx 			= (float) ((px-1)*cx);
                cy 			= (float) ((py-1)*cy);*/
                //Toast.makeText(plateau_neuf.this, "touché en ("+cx+" / "+cy+")", (int)2000).show();
                if (action==MotionEvent.ACTION_UP)
                {
                	if(px>9 || px<=0 || py>9 || py<=0)
                	{
                		Toast.makeText(plateau_neuf.this, "Hors du plateau", (int)2000).show();
                    }
                	else
                    {
                    	Bitmap pion_noir  = BitmapFactory.decodeResource(getResources(),R.drawable.pion_noir);
                        Bitmap pion_blanc = BitmapFactory.decodeResource(getResources(),R.drawable.pion_blanc);
                    	Bitmap le_pion;
                    	TextView tour_joueur = (TextView) findViewById(R.id.tour_joueur);
                    	
                    	laCouleur = Couleur.NOIR;
                    	if(actionRealisee.nbrPositionsActuel==0){
                    		laCouleur = Couleur.NOIR;
                    	}else{
	                    	if(actionRealisee.lesActions.get(actionRealisee.nbrPositionsActuel-1).pion.couleur == Couleur.NOIR && actionRealisee.nbrPositionsActuel!=0){
	                    		laCouleur = Couleur.BLANC;
	                    	}else if(actionRealisee.lesActions.get(actionRealisee.nbrPositionsActuel-1).pion.couleur == Couleur.BLANC || actionRealisee.nbrPositionsActuel==0){
	                    		laCouleur = Couleur.NOIR;
	                    	}
                    	}
                    	                    	
                    	//récupération du core erreur renvoyé
                    	erreur = realiserAction(laCouleur, laPosition, PasseOuJoue.JOUE);
                    	//effaceImages(); 
                    	//Log.d("AS_TEST", "erreur : " + erreur.toString());
                    	switch(erreur){                    		
	                    	case NO_ERREUR_OK:
	                    		
		                    	if(NoSound!=false){
		                    		playSound_touche(R.raw.poser);
		                    	}
		                    	//couleur_pion++;
		                    	afficherPlateau(plateau.positionPlateau);
	                    		nb_passe = 0;	
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
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.go9);
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
		nb_passe++;
		/******************************************************/
    	/*							Codes					  */
    	/******************************************************/    	
		TextView tour_joueur = (TextView) findViewById(R.id.tour_joueur);
		if(actionRealisee.nbrPositionsActuel==0){
			tour_joueur.setText("Joueur Blanc");
    		traitement(Couleur.NOIR, new Position(), PasseOuJoue.PASSE);
		}else{
			if(actionRealisee.lesActions.get(actionRealisee.nbrPositionsActuel-1).pion.couleur == Couleur.NOIR){
	    		tour_joueur.setText("Joueur Noir");
	    		// On execute cette fonction afin d'ajouter l'action dans la liste des actions
	    		traitement(Couleur.BLANC, new Position(), PasseOuJoue.PASSE);
	    	}else if(actionRealisee.lesActions.get(actionRealisee.nbrPositionsActuel-1).pion.couleur == Couleur.BLANC){
	    		tour_joueur.setText("Joueur Blanc");
	    		traitement(Couleur.NOIR, new Position(), PasseOuJoue.PASSE);
	    	}
		}
		
		if(nb_passe == 2){
			nb_passe = 0;
			AlertDialog alertDialog = new AlertDialog.Builder(
			        plateau_neuf.this).create();
			
			this.leScore();
			
			
			// Le titre
			alertDialog.setTitle("Partie terminée");
			 
			// Le message
			alertDialog.setMessage("Scores :\nBlanc : " +
								   scoreJoueurs.scoreJoueurBlanc + " Noir : "+ 
								   scoreJoueurs.scoreJoueurNoir);
			
			// Ajout du bouton "OK"
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int which) {
			    	plateau = null;
			    	actionRealisee = null;
			    	
			    	if(mPlayer!=null){
		            	mPlayer.stop();
		            	mPlayer.release();
		            	mPlayer = null;
	            	}
	            	if(mPlayerPion!=null){			            	
		            	mPlayerPion.stop();
		            	mPlayerPion.release();
		            	mPlayerPion = null;
	            	}
			    	
			    	Intent intent = new Intent(plateau_neuf.this, MainActivity.class);
	                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   
	                startActivity(intent);
			        plateau_neuf.this.finish();
			    }
			});
			
			// Affichage
			alertDialog.show();
		}
	}
	
	
	public void annuler_action(View v){
		this.retourAction();
		this.afficherPlateau(plateau.positionPlateau);
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
	    if(mPlayerPion != null) {
	    	mPlayerPion.stop();
	    	mPlayerPion.release();
	    }
	    mPlayerPion = MediaPlayer.create(this, resId);
	    mPlayerPion.start();
	}
	
	public void afficherPlateau(List<Pion> listDesPion){
		/******************************************************************/
		/*				Declaration des variables globales		   		  */
		/******************************************************************/
		int z 		= 0;
		int taille 	= 9;
		float x,y;
		String a_qui_le_tour = "noir";
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
    	
		TextView tour_joueur = (TextView) findViewById(R.id.tour_joueur);
    	couleur_pion = 1;

		if(actionRealisee.nbrPositionsActuel!=0){
	    	for(int j=0; j < taille; j++)
			{
				for(int k=0; k < taille; k++)
				{				
	            	if(listDesPion.get(z).couleur != Couleur.RIEN){
	            		if(listDesPion.get(z).couleur==Couleur.NOIR){
	                		le_pion = pion_noir;
	                		a_qui_le_tour = "blanc";
	                	}else{
	                		le_pion = pion_blanc;
	                		a_qui_le_tour = "noir";
	                	}
                        //Log.v("AS_TEST","x_case : "+x_case);
                        /*x_case = 55.5f;
                        y_case = 55.5f;*/
	            		x = (listDesPion.get(z).position.x)*x_case;
		            	y = (listDesPion.get(z).position.y)*y_case;
		            	int taille_pion = (int)(x_grille/9)/2;
		            	le_pion = getResizedBitmap(le_pion, taille_pion, taille_pion);
		            	int width = le_pion.getWidth();            	
		            	drawImg((x+(width/4)), (y+(width/4)), le_pion);
	            	}
					z++;
				}
			}
            int taille_pion = (int)(x_grille/9)/2;
            le_pion = getResizedBitmap(pion_noir, taille_pion, taille_pion);
            //drawImg(444+25/2, 444+25/2, le_pion);
			/*if((couleur_pion % 2) == 0 && couleur_pion != 1){
				couleur_pion = 2;
				tour_joueur.setText("Joueur Blanc");
			}else{
				couleur_pion = 1;
				tour_joueur.setText("Joueur Noir");
			}*/
			if(actionRealisee.lesActions.get(actionRealisee.nbrPositionsActuel-1).pion.couleur == Couleur.NOIR){
				//couleur_pion = 2;
				tour_joueur.setText("Joueur Blanc");
			}else{
				//couleur_pion = 1;
				tour_joueur.setText("Joueur Noir");
			}
			afficheImages();
		}else{
			tour_joueur.setText("Joueur Noir");
		}
	}	
	
	
	public void onWindowFocusChanged(boolean hasFocus) {
		float xI,xY;
	    super.onWindowFocusChanged(hasFocus);

        /*TailleEcran tailleEcran = new TailleEcran();

        String tailleE = tailleEcran.getSizeName(this);*/



	     maVue = findViewById(R.id.imageView1);
		 xI = maVue.getWidth();
	     xY = maVue.getHeight();

        //patch correction grand écran
        if(xI == 800 || xY == 800){
            ViewGroup.LayoutParams params = maVue.getLayoutParams();
            params.width = 800;
            params.height = 800;
            maVue.setLayoutParams(params);
            xI = 800;
            xY = 800;
        }
	     
	     x_grille = (float) (xI - ((xI/100*3.75)*2));
         y_grille = (float) (xY - (xY/100*3.75) - (xY/100*3.75));



         Log.d("AS_TEST", "taille : ("+xI+"/"+xY+")");

         float cx 	= (float) x_grille/(taille_plateau-1);
         x_case 		= cx;

         float cy 	= (float) y_grille/(taille_plateau-1);
         y_case 		= cy;

         Log.v("AS_TEST", "x_case : " + x_case);
         Log.v("AS_TEST","y_case : "+y_case);
	     bitmap = Bitmap.createBitmap((int)xI,(int)xY, Config.ARGB_8888);
	     canva = new Canvas(bitmap);
	     canva.save(1);
	     maVue.draw(canva);
	     
	     if(actionRealisee.nbrPositionsActuel!=0){
		     afficherPlateau(plateau.positionPlateau);
		}
    }
	
	@Override
	public void onBackPressed(){		
				
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
		        plateau_neuf.this);
		 
		 
		// Le message
		alertDialog.setMessage("Souhaitez-vous sauvegarder la partie ?");
		 
		// L'icone
		alertDialog.setIcon(android.R.drawable.ic_menu_save);
		 
		// Le premier bouton "Oui" ( positif )
		alertDialog.setPositiveButton("Oui",
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		            	sauvegardePartie.EnregistrerLaPartie(actionRealisee);
	            		Log.v("AS_TEST","taille après sauvegarde : "+sauvegardePartie.lireLaTaille());
		            	if(mPlayer!=null){
			            	mPlayer.stop();
			            	mPlayer.release();
			            	mPlayer = null;
		            	}
		            	if(mPlayerPion!=null){			            	
			            	mPlayerPion.stop();
			            	mPlayerPion.release();
			            	mPlayerPion = null;
		            	}
		            	plateau = null;
		            	actionRealisee = null;
		            	
		            	Intent intent = new Intent(plateau_neuf.this, MainActivity.class);
		                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   
		                startActivity(intent);
		                plateau_neuf.this.finish();		                
		            }
		        });
		 
		// Le deuxième bouton "NON" ( négatif )
		alertDialog.setNegativeButton("Non",
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		            	if(mPlayer!=null){
			            	mPlayer.stop();
			            	mPlayer.release();
			            	mPlayer = null;
		            	}
		            	if(mPlayerPion!=null){			            	
			            	mPlayerPion.stop();
			            	mPlayerPion.release();
			            	mPlayerPion = null;
		            	}
		            	plateau = null;
		            	actionRealisee = null;		            	
		        		
		            	Intent intent = new Intent(plateau_neuf.this, MainActivity.class);
		                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   
		                startActivity(intent);
		                plateau_neuf.this.finish();
		            }
		        });
		
		// le troisième bouton "Annuler"
		alertDialog.setNeutralButton("Annuler", 
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
		                dialog.cancel();
					}
				});
		 
		// Affiche la boite du dialogue
		alertDialog.show();
	}
	
	@Override
	public void onPause(){
		super.onPause();
	}
		
}
