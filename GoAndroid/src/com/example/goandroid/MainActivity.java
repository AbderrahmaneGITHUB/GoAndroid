package com.example.goandroid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	/******************************************************************/
	/*				Declaration des variables globales		   		  */
	/******************************************************************/
	private View maVue;	
	private Plateau plateau;
	private Pion pionEnlever;
	/******************************************************************/
	/*							onCreate		   					  */
	/******************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/**************************************************************/
    	/*				Declaration variables				  		  */
    	/**************************************************************/ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		maVue = findViewById(R.id.imageView1);
		
		/************   Initialisation du tableau		***************/
		this.pionEnlever = initialiserUnPion();
		this.plateau = new Plateau();
		initialisationPlateau(13, this.plateau);
		Log.i("test1", "*******************************");
		Log.i("test1", "Obtenir un Pion en (5,1) qui est NOIR");
		this.plateau.positionPlateau.get(1*this.plateau.taille + 5).couleur = Couleur.NOIR;
		Position pos = new Position();
		pos.x = 5; 
		pos.y = 1;
		Pion pion = obtenirPionEnPosition(this.plateau, pos);
		Log.i("test1", "pion position: x=" + pion.position.x + "; y=" + pion.position.y);
		Log.i("test1", "pion couleur : " + pion.couleur.toString());		
		Log.i("test1", "*******************************");
		Log.i("test1", "Placer un Pion en (5,1) BLANC");
		pos.x = 5; 
		pos.y = 1;
		int testCouleur = placerPionEnPosition(this.plateau, pos, Couleur.BLANC);
		Log.i("test1", "Resultat place Pion en (5,1) : " + testCouleur);
		Log.i("test1", "*******************************");
		Log.i("test1", "Placer un Pion en (5,2) BLANC");
		pos.x = 5; 
		pos.y = 2;
		int testCouleur2 = placerPionEnPosition(this.plateau, pos, Couleur.BLANC);
		Log.i("test1", "Resultat place Pion en (5,2) : " + testCouleur2);
		Log.i("test1", "*******************************");		
		Pion pion2 = obtenirPionEnPosition(this.plateau, pos);	
		Log.i("test1", "Verifier la couleur du Pion en position (5,2), qui est BLANC");
		Log.i("test1", "pion2 position: x=" + pion.position.x + "; y=" + pion.position.y);
		Log.i("test1", "pion2 couleur : " + pion2.couleur.toString());
		Log.i("test1", "*******************************");
		Log.i("test1", "Enlever un Pion en (5,2): BLANC: 4 etapes de verif");
		pos.x = 5; 
		pos.y = 2;
		Log.i("test1", "**  1  **");
		Log.i("test1", "Voir le contenu du Pion  pionEnlever avant");
		Log.i("test1", "pionEnlever position: x=" + pionEnlever.position.x + "; y=" + pionEnlever.position.y);
		Log.i("test1", "pionEnlever couleur : " + pionEnlever.couleur.toString());
		
		Log.i("test1", "**  2  **");
		int testEnlevement = enleverPionEn(this.plateau, pos, this.pionEnlever);
		Log.i("test1", "test denlevement du pion : " + testEnlevement);
		
		Log.i("test1", "**  3  **");
		Pion pion3 = obtenirPionEnPosition(this.plateau, pos);
		Log.i("test1", "pion3 couleur : "   + pion3.couleur.toString());
		Log.i("test1", "pion3 position: x=" + pion3.position.x 
				  								  + "; y=" + pion3.position.y);
		Log.i("test1", "**  4  **");
		Log.i("test1", "Voir le contenu du Pion  pionEnlever après");
		Log.i("test1", "pionEnlever position: x=" + pionEnlever.position.x 
												  + "; y=" + pionEnlever.position.y);													
		Log.i("test1", "pionEnlever couleur : " + pionEnlever.couleur.toString());
		Log.i("test1", "*******************************");
		
		/**************************************************************/
		/*						setOnTouchListener				  	  */
    	/**************************************************************/ 		
		maVue.setOnTouchListener(	new View.OnTouchListener() {
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
	            px = (int) (x/(xI/9))+1;
	            py = (int) (y/(xY/9))+1;
	            py = (int) (y/(xY/9))+1;
	            
	            if (action==MotionEvent.ACTION_UP)
	            {
	            	if(px>9 || px<=0 || py>9 || py<=0)
	            	{
	            		Toast.makeText(MainActivity.this, "Hors du plateau", 2000).show();
	                }
	            	else
	                {
	                	Toast.makeText(MainActivity.this, "touch� en ("+px+" / "+py+")", 2000).show();
	                	//Test
	                    ImageView iv = new ImageView(MainActivity.this);
	                    iv.setImageResource(R.drawable.pion_noir);                
	                }
	            }      
	            return true;
	        }
        });	
	}
	
	
	
	
	/******************************************************************/
	/*					appartientAuTerritoire						  */
	/* @brief Juste pour la lisibilité du code car un territoire      */	
	/* 	est un type synonyme de chaine							  	  */
	/******************************************************************/
	int appartientAuTerritoire(Position pos, Territoire territoire){
		return appartientAlaChaine(pos, territoire);
	}
	
	/******************************************************************/
	/*					appartientAuxPositions						  */
	/* @brief 													  	  */
	/******************************************************************/
	int appartientAuxPositions(Position pos, Positions lesPos){
		int res = 0;
		int i;
		for (i = 0; i < lesPos.nbrPositionsActuel; i++){
			if (memePosition(pos, lesPos.lesPositions.get(i)) == 1){
				res = 1;
				break;
			}
		}
		return res;
	}
	
	
	/******************************************************************/
	/*							obtenirPionEn						  */
	/* @brief initialise un pion								  	  */
	/******************************************************************/
	Pion initialiserUnPion(){
		Pion pion = new Pion();
		pion.position = new Position();
		pion.position.x = 30;
		pion.position.y = 30;
		pion.couleur = Couleur.RIEN;
		return pion;
	}
	
	
	/******************************************************************/
	/*							enleverPionEn						  */
	/* @ brief E nlève le pion en fonction de sa position La valeur   */
	/*  retournée est 1 sauf s ’il n ’est pas possible d ’enlever un  */
	/*  pion en cette position (la valeur 0 est alors				  */
	/*  retourné e)													  */
	/*  Si le pointeur pionEnlever n ’est pas NULL , alors écrit      */
	/*  à cette adresse les information sur le pion enlevé			  */
	/******************************************************************/
	int enleverPionEn(Plateau plateau, Position pos, Pion pionEnlever){
		Couleur coul;

		coul = plateau.positionPlateau.get(pos.y*plateau.taille + pos.x).couleur;
		if (coul != Couleur.RIEN)
		{
			plateau.positionPlateau.get(pos.y*plateau.taille + pos.x).couleur = Couleur.RIEN;

			if (pionEnlever != null)
			{
				pionEnlever.position = pos;
				pionEnlever.couleur = coul;
			}

			return 1; //bien enlevé
		}

		return 0; //il y a aucun pion à cette position
	}
	
	/******************************************************************/
	/*							placerPionEn						  */
	/* @brief Place le pion en fonction de sa position (contenu dans  */
	/* sa structure) La valeur retournÃ©e est 1 sauf sâ€™il nâ€™est   */
	/* pas possible de placer un pion en cette position    			  */
	/* (la valeur 0 est alors retournÃ©e)							  */
	/******************************************************************/
	int placerPionEn(Plateau plateau, Pion pion){
		return placerPionEnPosition(plateau, pion.position, pion.couleur);
	}
	
	/******************************************************************/
	/*					placerPionEnPosition						  */
	/* * @brief Place un pion en fonction de sa position et de sa 	  */
	/* couleur La valeur retournée est 1 sauf s’il n’est pas possible */
	/*  de placer un pion en cette position (la valeur 0 est alors	  */
	/*  retournée)													  */
	/******************************************************************/
	int placerPionEnPosition(Plateau plateau, Position pos, Couleur couleur){
		Pion pion = obtenirPionEnPosition(plateau, pos);
		if (pion.couleur != Couleur.RIEN) return 0;
		plateau.positionPlateau.get(pos.y*plateau.taille + pos.x).couleur = couleur;
		return 1;
	}
	
	/******************************************************************/
	/*							obtenirPionEn						  */
	/* @brief Retourn le pion à la position pos du plateau		  	  */
	/******************************************************************/
	Pion obtenirPionEnPosition(Plateau plateau, Position pos){
		return obtenirPionEn(plateau, pos.x, pos.y);
	}
	
	
	/******************************************************************/
	/*							obtenirPionEn						  */
	/* @brief Retourne le pion en la position (x,y) du plateau		  */
	/******************************************************************/
	Pion obtenirPionEn(Plateau plateau, int x, int y){
		Pion pion = new Pion();
		pion.position = new Position();
		pion.position.x = x;
		pion.position.y = y;
		pion.couleur = plateau.positionPlateau.get(y*plateau.taille + x).couleur;
		return pion;
	}
	
	/******************************************************************/
	/*							onCreateOptionsMenu					  */
	/******************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	
	/******************************************************************/
	/*							memePosition	  					  */
	/******************************************************************/
	public int memePosition(Position pos1, Position pos2){
		if((pos1.x == pos2.x) && (pos1.y == pos2.y))
			return 1;
		else
			return 0;		
	}
	
	/******************************************************************/
	/*						appartientAlaChaine	  					  */
	/******************************************************************/
	public int appartientAlaChaine(Position pos, Chaine chaine){
		int res = 0;
		int i;
		for (i = 0; i < chaine.lesCoordCases.nbrPositionsActuel; i++)
		{
			if (memePosition(pos, chaine.lesCoordCases.lesPositions.get(i)) == 1){				
				res = 1;
				break;
			}			
		}
		return res;
	}
	
	/******************************************************************/
	/*							initialisationPlateau    			  */
	/******************************************************************/
	void  initialisationPlateau(int taille, Plateau plateau)
	{
		plateau.positionPlateau = new ArrayList<Pion>();
		plateau.taille = taille;		
		effacerPlateau(plateau);
		initialisationPosition (plateau);
	}
	
	/******************************************************************/
	/*							effacerPlateau						  */
	/******************************************************************/
	void effacerPlateau(Plateau plateau)
	{
		int taille = plateau.taille;
		
		for(int i = 0; i < taille*taille; i++)
		{
			Pion pion = new Pion();	
			pion.position = new Position();
			plateau.positionPlateau.add(pion);
			plateau.positionPlateau.get(i).couleur = Couleur.RIEN;
		}		
	}
	
	/******************************************************************/
	/*						initialisationPosition					  */
	/******************************************************************/
	void initialisationPosition (Plateau plateau){
		int z = 0;
		int taille = plateau.taille;
		for(int j=0; j < taille; j++)
		{
			for(int k=0; k < taille; k++)
			{
				plateau.positionPlateau.get(z).position.x =  k;
				plateau.positionPlateau.get(z).position.y =  j;
				z++;
			}
		}	
		z = 4;
	}	
		
	/******************************************************************/
	/*			Declaration des Structures et des Enums globales	  */
	/******************************************************************/
	public enum Couleur {
		RIEN,
		BLANC,
		NOIR,
		ETRANGE;
	}

	public class Position{
		int x;		
		int y;						
	}

	public class Pion{
		Position position;
		Couleur couleur;	 		
	}

	public class Positions{
		List<Position> lesPositions;
		int nbrPositionsActuel;
		int nbrPositionsMax;
	}

	public class Chaine{
		Positions lesCoordCases;
		Couleur couleur;
	}
	
	public class Territoire extends Chaine{
		
	}
	
	public class chaines{
		List<Chaine> lesChaines;
		int nbrPositionsActuel;
		int nbrPositionsMax;
	}	
	
	public class Plateau{
		List<Pion> positionPlateau;
		int taille;		
	}
}


