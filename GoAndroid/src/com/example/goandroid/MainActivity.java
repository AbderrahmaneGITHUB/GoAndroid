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
	protected static ActionRealiseeStruct actionRealisee2;
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
		
		sauvegardePartie = new SauvegardePartie(MainActivity.this);
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
		int laTaille = sauvegardePartie.lireLaTaille();
		initTaillePlateau(sauvegardePartie.lireLaTaille());
		
		actionRealisee = null;
		actionRealisee = new ActionRealiseeStruct();
		
		actionRealisee2 = null;
		actionRealisee2 = sauvegardePartie.lecture();
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
		
		scoreJoueurs = new ScoreJoueurs();
		if(actionRealisee == null)
		{
			actionRealisee = new ActionRealiseeStruct(); 
		}
		nombreDePasse 	= 0;
		posPionValide 	= 0;
		posPionValide  = 0; 
	}
	
	/******************************************************************/
	/*				Fonction d'Initialisation du plateau    		  */
	/******************************************************************/
	public void initTaillePlateau(int inTaille){		
		plateau = new Plateau(inTaille);
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
			taille = plateau.taille;
			testDedans = 0;
			if((inPosition.x >= 0) && (inPosition.x < taille) && (inPosition.y >= 0) && (inPosition.y < taille)){
				testDedans = 1;
			}
			else{
				return Erreur.ERR_PION_HORS_PLATEAU;
			}
			
			if(testDedans == 1){
				//Vérification si l'emplacement est déja occupé 
				testPlacementPion = pionDeReference.placerPionEnPosition(plateau, inPosition, inCouleur);
				
				if(testPlacementPion == 0){
					return Erreur.ERR_EMPLACEMENT_OCCUPE;
				}
				else if(testPlacementPion == 1){
					//Vérifier si on a capturé des chaines avec la pos du pion			
					chainesCaptures = fonctionCaptures.captureChaines(pionDeReference, 
							plateau, 
							posPionValide);
					//Vérification les chaine capturée
					if ((chainesCaptures != null) && (chainesCaptures.nbrPositionsActuel > 0)) {
						Log.d("AS_TEST", "capture");
						for (rea = 0; rea < chainesCaptures.nbrPositionsActuel; rea++) {
							//Récupération un chaine
							chaineCap = chainesCaptures.lesChaines.get(rea);
							//Vérifier si la chaine récupérée à la des yeux
							positonsYeuxChaine = chaineCap.lesYeuxDeLaChaine(chaineCap, plateau);
							if (positonsYeuxChaine.nbrPositionsActuel < 2) {
								//si la chaine capturée a moins de deux yeux donc elle capturée
								fonctionCaptures.RealiserCapture(chaineCap, plateau);
							}						
						}
					}else if ((chainesCaptures == null ||
							   chainesCaptures.nbrPositionsActuel == 0)&&
							   (posPionValide == 0)) {
						//tester si le pion joué avant est bien enlevé (car a pos de pion n'est pas valide) 
						testPionEnleve = pionAEnlever.enleverPionEn(plateau, inPosition, pionAEnlever);
						if ((inPosition.memePosition(pionAEnlever.position, inPosition) == 1) && 
							(pionAEnlever.couleur == inCouleur) && 
							(testPionEnleve == 1)) {
							return Erreur.ERR_PION_NON_VALIDE;
						}	
					}					
					actionRealisee.lesActions.add(new ActionJoueur(pionDeReference, inPasseOuJoue));
					actionRealisee.nbrPositionsActuel++;
					nombreDePasse  = 0; //réinitialisation de la variable passe
				}				
			}							
			break;
		/***	cas d'un joueur passe   *****/				
		case PASSE:	
			nombreDePasse ++;
			pionDeReference.position.x = 30;
			pionDeReference.position.y = 30;
			actionRealisee.lesActions.add(new ActionJoueur(pionDeReference, inPasseOuJoue));
			actionRealisee.nbrPositionsActuel++;
			/***	tester s'il y'a deux passe (fin de la partie) ***/
			if(nombreDePasse  == 2){
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
	public void setPosPionValide(int inPosPionValide) {
		posPionValide = inPosPionValide;
	}

	/**********************************************************************/
	/*						 MediaPlayer					      		  */
	/**********************************************************************/
    public void intialisationOfSaound(int resId){
    	if(mPlayer != null){
			mPlayer.stop();
			mPlayer.release();
		}
    	mPlayer = mPlayer.create(MainActivity.this, resId);  
    }
	
	public void playSound() {
		mPlayer.start();
		mPlayer.setLooping(true);
	}
	
	public void stopSound(){		
		if(mPlayer != null){
			mPlayer.stop();
			mPlayer.release();
		}
	}	 
	
	
	/**********************************************************************/
	/*																	  */
	/*						   rejouerPartie				      		  */
	/**********************************************************************/
	public void rejouerPartie(){
		for(int i = 0; i < actionRealisee2.nbrPositionsActuel; i++){
			realiserAction(actionRealisee2.lesActions.get(i).pion.couleur,
						   actionRealisee2.lesActions.get(i).pion.position,
						   actionRealisee2.lesActions.get(i).passeeOuJouee);			
		}		
	}	
	
	/**********************************************************************/
	/*																	  */
	/*						   onBackPressed				      		  */
	/**********************************************************************/
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
						scoreJoueurs.scoreJoueurNoir++;
						break;
	
					case BLANC:
						scoreJoueurs.scoreJoueurBlanc++;
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
					scoreJoueurs.scoreJoueurNoir += (float)lv_LesTerritoires.lesChaines.get(z).lesCoordCases.nbrPositionsActuel;
					break;
	
				case BLANC:
					scoreJoueurs.scoreJoueurBlanc += (float)lv_LesTerritoires.lesChaines.get(z).lesCoordCases.nbrPositionsActuel;
					break;
	
				default:
					break;
			}
		}
		scoreJoueurs.scoreJoueurBlanc += valKomi;
		return 1;
	}
	

	/**********************************************************************/
	/*																	  */
	/*						   rejouerPartie				      		  */
	/**********************************************************************/
	public void retourAction(){
		int taillePlateau;
		
		taillePlateau = plateau.taille;
		
		if(actionRealisee.nbrPositionsActuel == 1){
			plateau = null;
			this.initTaillePlateau(taillePlateau);
			actionRealisee = null;
			actionRealisee = new ActionRealiseeStruct(); 
			
		}else if(actionRealisee.nbrPositionsActuel > 1){
			plateau = null;
			this.initTaillePlateau(taillePlateau);	
			
			actionRealisee.lesActions.remove(actionRealisee.nbrPositionsActuel-1);
			actionRealisee.nbrPositionsActuel--;
			actionRealisee2 = actionRealisee;
			actionRealisee = null;
			actionRealisee = new ActionRealiseeStruct();
			for(int i = 0; i < actionRealisee2.nbrPositionsActuel; i++){
				realiserAction(actionRealisee2.lesActions.get(i).pion.couleur,
							   actionRealisee2.lesActions.get(i).pion.position,
							   actionRealisee2.lesActions.get(i).passeeOuJouee);			
			}
		}
		
	}
	
	
	
	
}//  Fin du main


