package com.example.goandroid;
/**********************************************************************/
/*						Import (Include)		   		  		      */
/**********************************************************************/
import enumeration.Couleur;
import enumeration.Erreur;
import enumeration.PasseOuJoue;
import structure.ActionJoueur;
import structure.ActionRealiseeStruct;
import structure.Chaine;
import structure.Chaines;
import structure.ChainesCapturees;
import structure.Pion;
import structure.Plateau;
import structure.Position;
import structure.Positions;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

/**********************************************************************/
/*								   		  		      				  */
/*							MainActivity  		      				  */
/*								   		  		      				  */
/**********************************************************************/
public class MainActivity extends Activity {	
	/******************************************************************/
	/*				Declaration des variables globales		   		  */
	/******************************************************************/
	protected Plateau plateau;
	//private Pion pionEnlever;
	//private Pion pionClasse;
	//private Chaine chaineTest;
	//private Chaines chainesTests;
	//private Territoire territoireTest;
	//private Libertes libertes;
	//private ChainesCapturees ChainesCapturesTest;
	//private Positions PosisionsYeuxDeCaine;
	private MediaPlayer mPlayer = null;
	public boolean NoSound = false;
	private TailleEcran tailleEcran;
	private ActionRealiseeStruct actionRealisee;
	protected int nombreDePasse; //si on pasee deux fois, on arret la partie
	protected int posPionValide;
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
		initialisationClasseGo();	
		
		/*********** Lecture musique ****************/
		if(mPlayer==null){
			this.intialisationOfSaound(R.raw.asian_dream);
		}
		
		/*********** Test de la fonction réalisé action *********/
		/*
		Position TestPosition = new Position();
		for(int i = 0; i < 4; i++){
			TestPosition.x = 1;
			TestPosition.y = 1 +1;
			this.realiserAction(Couleur.BLANC,  TestPosition, PasseOuJoue.JOUE);								
		}
		*/
		
		/*********** Si écran trop petit, on ne propose pas les plateau de 13 et 19 lignes ****************/
		String taille = tailleEcran.getSizeName(this);
		if(taille != "grand"){
			View bouton_13 = findViewById(R.id.treize_case);
			View bouton_19 = findViewById(R.id.dixneuf_case);
			bouton_13.setVisibility(View.INVISIBLE);
			bouton_19.setVisibility(View.INVISIBLE);
		}
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
	/*							Type plateau						  */
	/******************************************************************/
	public void plateau_neuf(View v){
		Intent intent = new Intent(MainActivity.this, plateau_neuf.class);
		startActivity(intent);
	}
	
	public void plateau_treize(View v){
		Intent intent = new Intent(MainActivity.this, plateau_treize.class);
		startActivity(intent);
	}
	
	public void plateau_dixneuf(View v){
		Intent intent = new Intent(MainActivity.this, plateau_dixneuf.class);
		startActivity(intent);
	}
	
	/******************************************************************/
	/*				Initialisation des structures					  */
	/******************************************************************/
	public void initialisationClasseGo(){
		//this.pionClasse 		= new Pion();						
		//this.pionEnlever 		= new Pion();		
		//this.chaineTest 		= new Chaine();		
		//this.territoireTest 	= new Territoire();		
		//this.libertes			= new Libertes();
		//this.ChainesCapturesTest 	= new ChainesCapturees(MainActivity.this);		
		//this.chainesTests 		= new Chaines();		
		//this.PosisionsYeuxDeCaine = new Positions();
		
		this.actionRealisee = new ActionRealiseeStruct(); 
		this.nombreDePasse 	= 0;
		this.posPionValide 	= 0;
		this.posPionValide  = 0; 
	}
	
	/******************************************************************/
	/*				Fonction d'Initialisation du plateau    		  */
	/******************************************************************/
	public void initTaillePlateau(int inTaille){		
		this.plateau = new Plateau(inTaille);
	}
		
	/**********************************************************************/
	/*						realiserAction					      		  */
	/**********************************************************************/
	public Erreur realiserAction(Couleur inCouleur, Position inPosition, PasseOuJoue inPasseOuJoue){
		/******************************************************************/
		/*				Declaration des variables 				   		  */
		/******************************************************************/
		int testDedans, taille, rea;
		int testPlacementPion = 0;
		int testPionEnleve = 0;
		Pion pionDeReference = new Pion();
		Pion pionAEnlever = new Pion();
		Positions positonsYeuxChaine 		= new Positions();
		ChainesCapturees fonctionCaptures = new ChainesCapturees(MainActivity.this);
		Chaines chainesCaptures = new Chaines();
		Chaine chaineCap 		= new Chaine();
		
		/******************************************************************/
		/*				Declaration des variables 				   		  */
		/******************************************************************/		
		pionDeReference.position = inPosition;
		pionDeReference.couleur = inCouleur;
		/****** 	switch 		***************************/
		switch(inPasseOuJoue){
		/***	cas d'un pion joué   *****/
		case JOUE:
			taille = this.plateau.taille;
			testDedans = 0;
			if((inPosition.x >= 0) && (inPosition.x < taille) && (inPosition.y >= 0) && (inPosition.y < taille)){
				testDedans = 1;
			}
			else{
				return Erreur.ERR_PION_HORS_PLATEAU;
			}
			
			if(testDedans == 1){
				//Vérification si l'emplacement est déja occupé 
				testPlacementPion = pionDeReference.placerPionEnPosition(this.plateau, inPosition, inCouleur);
				
				if(testPlacementPion == 0){
					return Erreur.ERR_EMPLACEMENT_OCCUPE;
				}
				else if(testPlacementPion == 1){
					//Vérifier si on a capturé des chaines avec la pos du pion			
					chainesCaptures = fonctionCaptures.captureChaines(pionDeReference, 
							this.plateau, 
							this.posPionValide);
					//Vérification les chaine capturée
					if ((chainesCaptures != null) && (chainesCaptures.nbrPositionsActuel > 0)) {
						for (rea = 0; rea < chainesCaptures.nbrPositionsActuel; rea++) {
							//Récupération un chaine
							chaineCap = chainesCaptures.lesChaines.get(rea);
							//Vérifier si la chaine récupérée à la des yeux
							positonsYeuxChaine = chaineCap.lesYeuxDeLaChaine(chaineCap, this.plateau);
							if (positonsYeuxChaine.nbrPositionsActuel < 2) {
								//si la chaine capturée a moins de deux yeux donc elle capturée
								fonctionCaptures.RealiserCapture(chaineCap, this.plateau);
							}						
						}
					}else if ((chainesCaptures == null ||
							   chainesCaptures.nbrPositionsActuel == 0)&&
							   (this.posPionValide == 0)) {
						//tester si le pion joué avant est bien enlevé (car a pos de pion n'est pas valide) 
						testPionEnleve = pionAEnlever.enleverPionEn(this.plateau, inPosition, pionAEnlever);
						if ((inPosition.memePosition(pionAEnlever.position, inPosition) == 1) && 
							(pionAEnlever.couleur == inCouleur) && 
							(testPionEnleve == 1)) {
							return Erreur.ERR_PION_NON_VALIDE;
							//TODO coninu
						}	
					}					
					this.actionRealisee.lesActions.add(new ActionJoueur(pionDeReference, inPasseOuJoue));
					this.actionRealisee.nbrPositionsActuel++;
					this.nombreDePasse  = 0; //réinitialisation de la variable passe
				}				
			}							
			break;
		/***	cas d'un joueur passe   *****/				
		case PASSE:	
			this.nombreDePasse ++;
			pionDeReference.position.x = 30;
			pionDeReference.position.y = 30;
			this.actionRealisee.lesActions.add(new ActionJoueur(pionDeReference, inPasseOuJoue));
			this.actionRealisee.nbrPositionsActuel++;
			/***	tester s'il y'a deux passe (fin de la partie) ***/
			if(this.nombreDePasse  == 2){
				return Erreur.FIN_DE_LA_PARTIE;
			}						
			
			return Erreur.PASSE;
			//break;			
		}		
		return Erreur.NO_ERREUR_OK;
	}			

	/**********************************************************************/
	/*						 getPosPionValide					      	  */
	/**********************************************************************/
	public int getPosPionValide() {
		return posPionValide;
	}

	/**********************************************************************/
	/*						 setPosPionValide					      	  */
	/**********************************************************************/
	public void setPosPionValide(int posPionValide) {
		this.posPionValide = posPionValide;
	}

	/**********************************************************************/
	/*						 MediaPlayer					      		  */
	/**********************************************************************/
    public void intialisationOfSaound(int resId){
    	if(this.mPlayer != null){
			this.mPlayer.stop();
			this.mPlayer.release();
		}
    	this.mPlayer = this.mPlayer.create(MainActivity.this, resId);
    }
	
	public void playSound() {
		this.mPlayer.start();
	}
	
	public void stopSound(){		
		if(this.mPlayer != null){
			this.mPlayer.stop();
			this.mPlayer.release();
		}
	}	 
		
	public void onBackPressed(){
		String string_activity = this.getClass().getName();

		if(string_activity.equals("com.example.goandroid.MainActivity")){
			// TODO : Il faut proposer à l'utilisateur d'enregistrer sa partie
			super.onDestroy();

		}else{
			if(mPlayer != null){
				mPlayer.stop();
			}
		}
		super.onBackPressed();
	}	

}//  Fin du main


