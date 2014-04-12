package com.example.goandroid;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	/******************************************************************/
	/*				Declaration des variables globales		   		  */
	/******************************************************************/
	private Plateau plateau;
	private Pion pionEnlever;
	private ClasseProf classeProf; 
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
		//maVue = findViewById(R.id.imageView1);
		
		/************   Initialisation du tableau		***************/
		this.classeProf = new ClasseProf();
		this.pionEnlever = this.classeProf.initialiserUnPion();
		this.plateau = new Plateau();
		this.classeProf.initialisationPlateau(13, this.plateau);
		Log.i("test1", "*******************************");
		Log.i("test1", "Obtenir un Pion en (5,1) qui est NOIR");
		this.plateau.positionPlateau.get(1*this.plateau.taille + 5).couleur = Couleur.NOIR;
		Position pos = new Position();
		pos.x = 5; 
		pos.y = 1;
		Pion pion = this.classeProf.obtenirPionEnPosition(this.plateau, pos);
		Log.i("test1", "pion position: x=" + pion.position.x + "; y=" + pion.position.y);
		Log.i("test1", "pion couleur : " + pion.couleur.toString());		
		Log.i("test1", "*******************************");
		Log.i("test1", "Placer un Pion en (5,1) BLANC");
		pos.x = 5; 
		pos.y = 1;
		int testCouleur = this.classeProf.placerPionEnPosition(this.plateau, pos, Couleur.BLANC);
		Log.i("test1", "Resultat place Pion en (5,1) : " + testCouleur);
		Log.i("test1", "*******************************");
		Log.i("test1", "Placer un Pion en (5,2) BLANC");
		pos.x = 5; 
		pos.y = 2;
		int testCouleur2 = this.classeProf.placerPionEnPosition(this.plateau, pos, Couleur.BLANC);
		Log.i("test1", "Resultat place Pion en (5,2) : " + testCouleur2);
		Log.i("test1", "*******************************");		
		Pion pion2 = this.classeProf.obtenirPionEnPosition(this.plateau, pos);	
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
		int testEnlevement = this.classeProf.enleverPionEn(this.plateau, pos, this.pionEnlever);
		Log.i("test1", "test denlevement du pion : " + testEnlevement);
		
		Log.i("test1", "**  3  **");
		Pion pion3 = this.classeProf.obtenirPionEnPosition(this.plateau, pos);
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
	
	
	/**************************************/
	/* Action des boutons				  */
	/**************************************/
	
	public void plateau_neuf(View v){
		/*Toast.makeText(this, "initialisation ",
				Toast.LENGTH_LONG).show();*/
		Intent intent = new Intent(MainActivity.this, plateau_neuf.class);
		startActivity(intent);
	}
	public void plateau_treize(View v){
		/*Toast.makeText(this, "initialisation ",
				Toast.LENGTH_LONG).show();*/
		Intent intent = new Intent(MainActivity.this, plateau_treize.class);
		startActivity(intent);
	}
	public void plateau_dixneuf(View v){
		/*Toast.makeText(this, "initialisation ",
				Toast.LENGTH_LONG).show();*/
		Intent intent = new Intent(MainActivity.this, plateau_dixneuf.class);
		startActivity(intent);
	}
}


