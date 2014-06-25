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
import structure.ScoreJoueurs;
import structure.Territoire;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
	protected static Plateau plateau;
	protected static MediaPlayer mPlayer = null;
	protected static boolean NoSound = true;
	protected static boolean NoMusic = true;
	//private static TailleEcran tailleEcran;
	protected static ActionRealiseeStruct actionRealisee;
	protected static int nombreDePasse; //si on pasee deux fois, on arret la partie
	protected static int posPionValide;
	protected static SauvegardePartie sauvegardePartie;
	protected static ScoreJoueurs scoreJoueurs;
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
		
		this.initialisationClasseGo();	
		
		this.sauvegardePartie = new SauvegardePartie(MainActivity.this);
		/*********** Lecture musique ****************/
		if(mPlayer==null){
			this.intialisationOfSaound(R.raw.asian_dream);
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
	public void nouvelle_partie(View v){
		Intent intent = new Intent(MainActivity.this, MenuPlateau.class);
		startActivity(intent);
	}
	
	/******************************************************************/
	/*							charger_partie						  */
	/******************************************************************/
	public void charger_partie(View v){
		
		///test//est///test/zsetest
		///////  TEST/////////////////
		sauvegardePartie = null;
		sauvegardePartie = new SauvegardePartie(this);		
		int laTaille = this.sauvegardePartie.lireLaTaille();
		initTaillePlateau(sauvegardePartie.lireLaTaille());
		
		actionRealisee = null;
		actionRealisee = sauvegardePartie.lecture();
		rejouerPartie();
		//Intent intent = new Intent(MainActivity.this, plateau_neuf.class);
		//startActivity(intent);
		Log.v("AS_TEST","taille sauvegarde : "+laTaille);
		if(laTaille==9){
			Intent intent = new Intent(MainActivity.this, plateau_neuf.class);
			startActivity(intent);
		}else if(laTaille==13){
			Intent intent = new Intent(MainActivity.this, plateau_treize.class);
			startActivity(intent);
		}else if(laTaille==19){
			Intent intent = new Intent(MainActivity.this, plateau_dixneuf.class);
			startActivity(intent);
		}
	}
	
	public void option(View v){
		Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
		startActivity(intent);
	}
	
	public void a_propos(View v){
		Intent intent = new Intent(MainActivity.this, AProposActivity.class);
		startActivity(intent);
	}
	
	/******************************************************************/
	/*				Initialisation des structures					  */
	/******************************************************************/
	public void initialisationClasseGo(){
		
		this.scoreJoueurs = new ScoreJoueurs();
		if(this.actionRealisee == null)
		{
			this.actionRealisee = new ActionRealiseeStruct(); 
		}
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
						Log.d("AS_TEST", "capture");
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
		this.mPlayer.setLooping(true);
	}
	
	public void stopSound(){		
		if(this.mPlayer != null){
			this.mPlayer.stop();
			this.mPlayer.release();
		}
	}	 
	
	
	/**********************************************************************/
	/*																	  */
	/*						   rejouerPartie				      		  */
	/**********************************************************************/
	public void rejouerPartie(){
		for(int i = 0; i < this.actionRealisee.nbrPositionsActuel; i++){
			realiserAction(this.actionRealisee.lesActions.get(i).pion.couleur,
						   this.actionRealisee.lesActions.get(i).pion.position,
						   this.actionRealisee.lesActions.get(i).passeeOuJouee);			
		}		
	}	
	
	public void onBackPressed(){		
		MainActivity.this.finish();
	}	
	
	
	/**********************************************************************/
	/*																	  */
	/*						   rejouerPartie				      		  */
	/**********************************************************************/
	public int leScore(){
		
		int i = 0, j = 0, iterTerritoire = 0, app = 1, z = 0;
		int testAppartenanceTerritoire = 0;
		Chaine territoireTestAppartenan  = new Chaine();
		LesTerritoires lv_LesTerritoires = new LesTerritoires();
		Territoire lv_Territoire = new Territoire();
		Position lv_pos 		 = new Position();
		Pion typePion			 = new Pion();
		float valKomi = 7.5f;
		
		if (plateau == null)
			return 0;

		for (i = 0; i < plateau.taille; i++) 
		{
			for (j = 0; j < plateau.taille; j++) 
			{
				app = 1;
				lv_pos.x = i;
				lv_pos.y = j;

				typePion = typePion.obtenirPionEn(plateau, lv_pos.x, lv_pos.y);

				switch (typePion.couleur)
				{
					case RIEN:
						testAppartenanceTerritoire = 0;
						if (lv_LesTerritoires.nbrPositionsActuel != 0) {
							for (iterTerritoire = 0; iterTerritoire < lv_LesTerritoires.nbrPositionsActuel; iterTerritoire++) {
	
								territoireTestAppartenan = lv_LesTerritoires.lesChaines.get(iterTerritoire);
								app = territoireTestAppartenan.appartientAlaChaine(lv_pos, territoireTestAppartenan);
	
								if (app == 1)
									testAppartenanceTerritoire = 1;
							}
						}
	
						if (testAppartenanceTerritoire == 0) {
							lv_Territoire = lv_Territoire.determineTerritoire(plateau, lv_pos);
							lv_LesTerritoires.lesChaines.add(lv_Territoire);
							lv_LesTerritoires.nbrPositionsActuel++;
						}
						break;
	
					case NOIR:
						this.scoreJoueurs.scoreJoueurNoir++;
						break;
	
					case BLANC:
						this.scoreJoueurs.scoreJoueurBlanc++;
						break;
	
					default:
						break;
				}
			}
		}

		for (z = 0; z < lv_LesTerritoires.nbrPositionsActuel; z++)
		{
			switch (lv_LesTerritoires.lesChaines.get(z).laCouleur)
			{
				case NOIR:
					this.scoreJoueurs.scoreJoueurNoir += (float)lv_LesTerritoires.lesChaines.get(z).lesCoordCases.nbrPositionsActuel;
					break;
	
				case BLANC:
					this.scoreJoueurs.scoreJoueurBlanc += (float)lv_LesTerritoires.lesChaines.get(z).lesCoordCases.nbrPositionsActuel;
					break;
	
				default:
					break;
			}
		}
		this.scoreJoueurs.scoreJoueurBlanc += valKomi;
		return 1;
	}
	

	/**********************************************************************/
	/*																	  */
	/*						   rejouerPartie				      		  */
	/**********************************************************************/
	public void retourAction(){
		int taillePlateau;
		
		taillePlateau = this.plateau.taille;
		
		if(this.actionRealisee.nbrPositionsActuel == 1){
			this.initTaillePlateau(taillePlateau);
			this.actionRealisee = null;
			this.actionRealisee = new ActionRealiseeStruct(); 
			
		}else if(this.actionRealisee.nbrPositionsActuel > 1){
			this.initTaillePlateau(taillePlateau);			
			this.actionRealisee.lesActions.remove(this.actionRealisee.nbrPositionsActuel);
			this.actionRealisee.nbrPositionsActuel--;
			
			for(int i = 0; i < this.actionRealisee.nbrPositionsActuel; i++){
				realiserAction(this.actionRealisee.lesActions.get(i).pion.couleur,
							   this.actionRealisee.lesActions.get(i).pion.position,
							   this.actionRealisee.lesActions.get(i).passeeOuJouee);			
			}
		}
		
	}
	
	
	
	
}//  Fin du main


