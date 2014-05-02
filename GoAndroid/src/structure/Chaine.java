package structure;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import enumeration.Couleur;


public class Chaine {
	
	/******************************************************************/
	/*						Structure Chaine				  		  */
	/******************************************************************/ 
	//public List<Pion> lesPions;
	//public int nbrPionsActuel;
	//public int nbrPionsMax;
	public int taille;	
	public Positions lesCoordCases;
	public Couleur laCouleur;
	
	/******************************************************************
	* 
	* @brief Détermine la position des yeux relatifs à une chaine
	* Si la chaine n'a aucun oeil alors valeur retournée est NULL
	* 
	******************************************************************/
	public Positions lesYeuxDeLaChaine(Chaine chaine, Plateau plateau) {
		/**************************************************/
		/*					Déclaration					  */
		/**************************************************/
		int i = 0, z = 0, j = 0, k = 0, l = 0;
		int x, y, x1, y1, t;
		int videAppartientAChaine = 0;
		int voisinsAutreCouleur = 0;
		int voisinsAvecMure = 0;
		int testDedans1, testDedans2;
		int testDejaPasse = 0;	
		Couleur couleurRef = Couleur.RIEN;	
		Position voisins[] 				= new Position[4];
		Position voisinsVide[]  		= new Position[4];
		Position pos 					= new Position();
		Position posTest2 				= new Position();
		Position posTest3 				= new Position();
		Positions rahimPositions 		= new Positions();
		Positions rahimPosDejaPasse 	= new Positions();
		Chaine rahimChainePosDejaPasse  = new Chaine();	
		Chaine chaineTest2				= new Chaine();	
		Libertes liberteTest2			= new Libertes();
		
		/**************************************************/
		/*						Codes					  */
		/**************************************************/
		rahimPositions.initialisationPositions(plateau, rahimPositions);
		rahimPosDejaPasse.initialisationPositions(plateau, rahimPosDejaPasse);
		rahimChainePosDejaPasse.initialisationChaine(plateau, rahimChainePosDejaPasse);
		chaineTest2.initialisationChaine(plateau, chaineTest2);
		liberteTest2.initialisationPositions(plateau, liberteTest2);
		
		//Récupération de la couleur  de la couleur de réference
		couleurRef = chaine.laCouleur;
		
		for (i = 0; i < chaine.lesCoordCases.nbrPositionsActuel; i++) {
			//récupération de l'élement i (position) dans la chaine
			pos = chaine.lesCoordCases.lesPositions.get(i);
			
			for (z = 0; z<4; z++){
				voisins[z] = new Position();
				voisins[z].x = pos.x;
				voisins[z].y = pos.y;
			}			
			voisins[0].x++;
			voisins[2].x--;	
			voisins[1].y++;	
			voisins[3].y--;
			
			//Voir si une case d'une couleur RIEN est en tourée avec la même couleur ce qui donne une oeil
			for (j = 0; j < 4; j++) {
				x1 = voisins[j].x;
				y1 = voisins[j].y;
				
				//Initialisation des des élement de l'Oeil
				videAppartientAChaine = 0;
				voisinsAutreCouleur = 0;
				voisinsAvecMure = 0;
				
				t = plateau.taille;//Récupérer la taille du plateau afin de ne pas aller audela de la taille dans le vérification
				// Tester si on est pas en dehors du plateau
				testDedans1 = 0;
				if((x1 >= 0) && (x1<t) && (y1 >= 0) && (y1<t)){
					testDedans1 = 1;
				}
				
				if (testDedans1 == 1) {
					Pion pionCoulTest = new Pion();
					pionCoulTest.initialiserUnPion(pionCoulTest);
					
					pionCoulTest = pionCoulTest.obtenirPionEnPosition(plateau, voisins[j]);
					
					//Avec ce teste je cherche que les cases vides
					if (pionCoulTest.couleur == Couleur.RIEN) {
						rahimChainePosDejaPasse.lesCoordCases.nbrPositionsActuel =
								rahimPosDejaPasse.nbrPositionsActuel;
						
						rahimChainePosDejaPasse.lesCoordCases.lesPositions =
								rahimPosDejaPasse.lesPositions;
						
						testDejaPasse = this.appartientAlaChaine(voisins[j],
								rahimChainePosDejaPasse);
						
						if (testDejaPasse == 0) {
							for (k = 0; k<4; k++){
								voisinsVide[k] = new Position();
								voisinsVide[k].x = voisins[j].x;
								voisinsVide[k].y = voisins[j].y;
							}
							
							voisinsVide[0].x++;
							voisinsVide[2].x--;	
							voisinsVide[1].y++;	
							voisinsVide[3].y--;
							
							// Détection du deja passage par vérification
							rahimPosDejaPasse.lesPositions.add(voisins[j]);
							rahimPosDejaPasse.nbrPositionsActuel++;
							
							for (l = 0; l < 4; l++) {
								x = voisinsVide[l].x;
								y = voisinsVide[l].y;
								
								t = plateau.taille;//Récupérer la taille du plateau afin de ne pas aller audela de la taille dans le vérification
								// Tester si on est pas en dehors du plateau
								testDedans2 = 0;
								if((x >= 0) && (x < t) && (y >= 0) && (y < t)){
									testDedans2 = 1;
								}
								if (testDedans2 == 1) {
									
									Pion pionTest = new Pion();
									pionTest.initialiserUnPion(pionTest);
									
									pionTest = pionTest.obtenirPionEnPosition(plateau, voisinsVide[l]);
									
									if (pionTest.couleur == couleurRef) // && appartientAlaChaine(voisinsVide[l], chaine) == 1
									{
										videAppartientAChaine++;
									}
									else if (pionTest.couleur != Couleur.RIEN
											&& pionTest.couleur != Couleur.ETRANGE) {
										
										posTest2.x = pionTest.position.x;
										posTest2.y = pionTest.position.y;										

										chaineTest2 = chaineTest2.determinerChaine(plateau, posTest2);
										liberteTest2 = liberteTest2.determineLiberte(plateau, chaineTest2);
																				
										posTest3 = liberteTest2.lesPositions.get(0);
										
										if (rahimPositions.memePosition(posTest3, voisins[j]) == 1
												&& liberteTest2.nbrPositionsActuel == 1) 
										{
											voisinsAutreCouleur++;
											
										}					
									}
								}
								else{
									voisinsAvecMure++;
								}
							}
						}
						if ((videAppartientAChaine == 3 && voisinsAutreCouleur == 1)
								|| (videAppartientAChaine == 4)
								|| (videAppartientAChaine == 2
								&& voisinsAvecMure == 2)
								|| (videAppartientAChaine == 3
								&& voisinsAvecMure == 1)
								|| (videAppartientAChaine == 2
								&& voisinsAvecMure == 1
								&& voisinsAutreCouleur == 1)) {
							
							rahimPositions.lesPositions.add(voisins[j]);							
							rahimPositions.nbrPositionsActuel++;
							
						}						
					}					
				}		
			}
		}		
		return rahimPositions;
	}
	
	/******************************************************************/
	/* @brief : Initialiser une Chaine			  		  			  */
	/******************************************************************/ 
	public int initialisationChaine(Plateau plateau, Chaine inChaine){	
		//inChaine.lesPions = new ArrayList<Pion>();
		//inChaine.nbrPionsActuel = 0;
		//inChaine.nbrPionsMax = Constante.NBR_MAX_CASES;
		inChaine.lesCoordCases = new Positions();
		inChaine.lesCoordCases.initialisationPositions(plateau, inChaine.lesCoordCases);
		inChaine.laCouleur = Couleur.RIEN;
		inChaine.taille = 0;	
		return  1;
	}
	
	/******************************************************************/
	/*						appartientAlaChaine	  					  */
	/*@brief Détermine si une position fait partie d'une chaine donnée*/
	/*@return retourne 0 pour non et 1 pour oui 				      */
	/******************************************************************/
	public int appartientAlaChaine(Position pos, Chaine chaine){
		/***************   Déclaration des variables   ****************/
		int res = 0;
		int i;		
		Position posClasse = new Position();
		
		/***************   				Code   		   ****************/
		for (i = 0; i < chaine.lesCoordCases.nbrPositionsActuel; i++)
		{
			if (posClasse.memePosition(pos, chaine.lesCoordCases.lesPositions.get(i)) == 1){				
				res = 1;
				break;
			}			
		}
		return res;
	}
	
	/******************************************************************/
	/*					appartientAuTerritoire						  */
	/* @brief Juste pour la lisibilité du code car un territoire      */	
	/* 	est un type synonyme de chaine							  	  */
	/******************************************************************/
	public int appartientAuTerritoire(Position pos, Territoire territoire){
		return appartientAlaChaine(pos, territoire);
	}
	
	/******************************************************************/
	/*							afficherChaine						  */
	/******************************************************************/
	public void afficherChaine(Chaine chaine){
		int i; 
		if (chaine == null){
			Log.i("afficherChaineFonction", "Chaine == NULL");
			return;
		}
		
		Log.i("afficherChaineFonction", "Nombre d'element de la chaine est  = " + chaine.lesCoordCases.nbrPositionsActuel);
		Log.i("afficherChaineFonction", "Couleur de la chaine est  = " + chaine.laCouleur);
		
		for (i = 0; i < chaine.lesCoordCases.nbrPositionsActuel; i++){
			Log.i("afficherChaineFonction", "Element dans la chaine (" + chaine.lesCoordCases.lesPositions.get(i).x + ", " 
																	   + chaine.lesCoordCases.lesPositions.get(i).y + ")");
		}
	}
	
	/******************************************************************/
	/*							determinerChaine				      */
	/* @brief Produit la chaîne à laquelle appartient le pion à la    */
	/* position pos sur le plateau.									  */
	/* S'il n'y a pas de pion sur cette case, alors le résultat 	  */
	/* retourné est NULL											  */
	/******************************************************************/
	public Chaine determinerChaine(Plateau plateau, Position pos){
		/**************************************************************/
		/*					Déclaration des variable 		          */
		/**************************************************************/
		//int dansChaine;
		int actuel;
		int i = 0;
		int z = 0;
		int x, y, t;
		int testDedans = 0;
		Chaine pChaine 				= new Chaine();
		List<Position> lesPositions = new ArrayList<Position>();
		Pion pion 					= new Pion();
		
		/*************************************************************/
		/*							Codes				 		     */
		/*************************************************************/
		/*************				Initialisation			**********/
		this.initialisationChaine(plateau, pChaine);
		pion.initialiserUnPion(pion); 
		
		//Obtenir le type de pion X ou O à la position pos
		pion = pion.obtenirPionEnPosition(plateau, pos); 
		Log.i("ChaineFonction", "Pion en position (" + pion.position.x +", "+ pion.position.y+")");
		Log.i("ChaineFonction", "La couleur du Pion est " + pion.couleur);
		
		//Y a pas de pion => y a pas de chaine
		if (pion.couleur == Couleur.RIEN) return pChaine = null; 	
		// Récupérer la couleur de la chaine
		pChaine.laCouleur = pion.couleur;
		
		pChaine.lesCoordCases.nbrPositionsActuel = 1;
		Log.i("ChaineFonction", "PnbrPositionsActuel" + pChaine.lesCoordCases.nbrPositionsActuel);
		//Récupérer l'adresse du tableau de position pour commecer à remplir le
		lesPositions = pChaine.lesCoordCases.lesPositions; 
		//la chaine
		lesPositions.add(pion.position); //Commencer la chaine par la position du pion demander

		actuel = 0;
	
		while (actuel < pChaine.lesCoordCases.nbrPositionsActuel){
			Position positionRef = new Position();
			positionRef = lesPositions.get(actuel);
			
			List<Position> voisins = new ArrayList<Position>();
			
			for (z = 0; z<4; z++){ 
				voisins.add(new Position()); // Récupérer la position de réference du pion
				voisins.get(z).x = positionRef.x;
				voisins.get(z).y = positionRef.y;
			}
					
			/*voir sur les 4 cotés du pion s'il ya un autre pion de même type afin de l'ajouter dans la chaine*/
			
			voisins.get(0).x = voisins.get(0).x+1;
			Log.i("voisins", "voisin 0 position: x =" + voisins.get(0).x + "; y=" + voisins.get(0).y);
			
			voisins.get(2).x = voisins.get(2).x-1;
			Log.i("voisins", "voisin 2 position: x =" + voisins.get(2).x + "; y=" + voisins.get(2).y);
			
			voisins.get(1).y = voisins.get(1).y+1;
			Log.i("voisins", "voisin 1 position: x =" + voisins.get(1).x + "; y=" + voisins.get(1).y);
			
			voisins.get(3).y = voisins.get(3).y-1;
			Log.i("voisins", "voisin 3 position: x =" + voisins.get(3).x + "; y=" + voisins.get(3).y);
			
			for (i = 0; i<4; i++){
				x = voisins.get(i).x;
				y = voisins.get(i).y;
				//Récupérer la taille du plateau afin de ne pas aller audela de la taille dans le vérification
				t = plateau.taille; 
				testDedans = 0;
				if((x >= 0) && (x<t) && (y >= 0) && (y<t)){
					testDedans = 1;
				}
				
				if (testDedans == 1){
					//vérification si cet élement est déja pris en compte déja
					int dansChainedansChaine = pChaine.appartientAlaChaine(voisins.get(i), pChaine); 
					//si cet element n'est pas compté déja
					if (dansChainedansChaine == 0){ 
						// récupérer le type d'élement
						Pion pionIci = new Pion(); 
						pionIci.initialiserUnPion(pionIci);
						pionIci = pionIci.obtenirPionEnPosition(plateau, voisins.get(i)); 
						// si cet élement est de meme type que l'élement qu'on cherche => on l'ajoute dans la chaine
						if (pionIci.couleur == pChaine.laCouleur){ 
							lesPositions.add(voisins.get(i));
							pChaine.lesCoordCases.nbrPositionsActuel++;
						}
					}
				}
			}		
			voisins.clear();
			actuel++;
		}
		pChaine.lesCoordCases.lesPositions = lesPositions; //Récupérer l'adresse du tableau de position pour commecer à remplir le
		this.afficherChaine(pChaine);
		return pChaine;
	}	
}



