package com.example.goandroid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
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
		this.plateau = new Plateau();
		initialisationPlateau(13, this.plateau);		

		/**************************************************************/
	/*						setOnTouchListener				  		  */
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
	/*							onCreateOptionsMenu					  */
	/******************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	
	/******************************************************************/
	/*							Les fonctions	  					  */
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
		BLAN,
		NOIR,
		ETRANGE;
	}

	public class Position{
		int x;		
		int y;						
	}
 /*
	public class Position{
		private int x;		
		private int y;		
		
		Position(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}		
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
	}
*/
	public class Pion{
		Position position;
		Couleur couleur;	 		
	}

 /*
	class Pion{
		private Position position;
		private Couleur couleur;	 
		public Pion(Couleur couleur, Position position){
			this.couleur = couleur;
			this.position = position;		
		}
		public Position getPosition() {
			return position;
		}
		public void setPosition(Position position) {
			this.position = position;
		}
		public Couleur getCouleur() {
			return couleur;
		}
		public void setCouleur(Couleur couleur) {
			this.couleur = couleur;
		}		
	}
*/
	public class Positions{
		List<Position> lesPositions;
		int nbrPositionsActuel;
		int nbrPositionsMax;
	}

	public class Chaine{
		Positions lesCoordCases;
		Couleur couleur;
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
	 /*
			
	public class Plateau{
		private List<Pion> positionPlateau;
		private int taille;
		
		public Plateau(List<Pion> positionPlateau, int taille){
			this.positionPlateau = positionPlateau;
			this.taille = taille;			
		}
		
		public List<Pion> getPositionPlateau() {
			return positionPlateau;
		}
		public void setPositionPlateau(List<Pion> positionPlateau) {
			this.positionPlateau = positionPlateau;
		}
		public int getTaille() {
			return taille;
		}
		public void setTaille(int taille) {
			this.taille = taille;
		}				
	}
	*/
}


