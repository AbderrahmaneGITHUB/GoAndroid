package com.example.goandroid;

import constante.Constante;
import structure.Chaine;
import structure.Chaines;
import structure.ChainesCapturees;
import structure.Libertes;
import structure.Pion;
import structure.Plateau;
import structure.Positions;
import structure.Territoire;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {	
	/******************************************************************/
	/*				Declaration des variables globales		   		  */
	/******************************************************************/
	private Plateau plateau;
	private Pion pionEnlever;
	private Pion pionClasse;
	private Chaine chaineTest;
	private Chaines chainesTests;
	private Territoire territoireTest;
	private Libertes libertes;
	private ChainesCapturees ChainesCapturesTest;
	private Positions PosisionsYeuxDeCaine;
	
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
		
		/************   Initialisation du tableau		***************/
		InitialisationClasseGo();	

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
	
	/******************************************************************/
	/*				Initialisation des structures					  */
	/******************************************************************/
	public void InitialisationClasseGo(){
		this.pionClasse 	= new Pion();
		
		this.plateau 		= new Plateau(Constante.TAILLEPLATEAU_13);
		
		this.pionEnlever 	= new Pion();
		
		this.chaineTest = new Chaine();
		
		this.territoireTest = new Territoire();
		
		this.libertes= new Libertes();
		
		this.ChainesCapturesTest = new ChainesCapturees();
		
		this.chainesTests = new Chaines();
		
		this.PosisionsYeuxDeCaine = new Positions();
	}	
}


