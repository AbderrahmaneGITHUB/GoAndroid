package structure;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import enumeration.Couleur;

public class Territoire extends Chaine{
	
	/*******************************************************************
	 * 
	*@brief Détermine la ou les chaines entourant un territoire
	*
	*******************************************************************/
	public Chaines entoureUnTerritoire(Territoire leTerritoire, Plateau plateau) {
		/**************************************************************/
		/*					Déclaration des variable 		          */
		/**************************************************************/
		int i = 0, z = 0, j = 0, res = 0;
		int xA, yA, taillePlateau, testDedans;
		int iterAppChaine = 0;
		int testAppartenanceChaine = 1;
		Position voisins[] 		= new Position[4];
		Position pos 			= new Position();
		Chaine lv_Chaine 		= new Chaine();
		Chaine chaineTestAppartenanChaine = new Chaine();
		Chaines pChaines  		= new Chaines();
		
		/*************************************************************/
		/*							Codes				 		     */
		/*************************************************************/
		
		if (plateau == null || leTerritoire == null)
		{
			return pChaines = null;
		}
		//initialisation du Flag qui teste l'appartenance d'un élement à une chaine
		testAppartenanceChaine = 0;
		
		for (i = 0; i < leTerritoire.lesCoordCases.nbrPositionsActuel; i++) {
			//récupération de l'élement i (position) dans la chaine
			pos = leTerritoire.lesCoordCases.lesPositions.get(i);
			 
			for (z = 0; z < 4; z++){
				voisins[z] = new Position();
				voisins[z].x = pos.x;
				voisins[z].y = pos.y;
			}
			voisins[0].x++;
			voisins[2].x--;	
			voisins[1].y++;	
			voisins[3].y--;			
			
			for (j = 0; j < 4; j++) {
				xA = voisins[j].x;
				yA = voisins[j].y;	
				
				//Récupérer la taille du plateau afin de ne pas aller audela de la taille dans le vérification
				taillePlateau = plateau.taille; 
				// Tester si on est pas en dehors du plateau
				testDedans = 0;
				if((xA >= 0) && (xA < taillePlateau) && (yA >= 0) && (yA < taillePlateau)){
					testDedans = 1;
				}
				
				if (testDedans == 1) {
					Pion pionTest = new Pion();
					//pionTest.initialiserUnPion(pionTest);
					pionTest = pionTest.obtenirPionEnPosition(plateau, voisins[j]);
					
					// TODO : Voir si ce pion a une chaine à coté de lui
					if (pionTest.couleur == Couleur.NOIR || pionTest.couleur == Couleur.BLANC) {
						//initialisation du Flag qui teste l'appartenance d'un élement à une chaine
						testAppartenanceChaine = 0;
						if (pChaines.nbrPositionsActuel != 0)
						{
							for (iterAppChaine = 0; iterAppChaine < pChaines.nbrPositionsActuel; iterAppChaine++) {
								
								chaineTestAppartenanChaine = pChaines.lesChaines.get(iterAppChaine);
								
								res = this.appartientAlaChaine(pionTest.position, chaineTestAppartenanChaine);
								if (res == 1){
									testAppartenanceChaine = 1;
								}
							}
						}						
						if (testAppartenanceChaine == 0) {
							lv_Chaine = this.determinerChaine(plateau, pionTest.position);
							if (lv_Chaine != null) {
								pChaines.lesChaines.add(lv_Chaine);
								pChaines.nbrPositionsActuel++;							
							}					
						}					
					}		
				}												
			}	
		}	
		return pChaines;
	}

	/******************************************************************/
	/*							determineTerritoire				      */
	/* @brief retourne un ensemble d'intersections inoccupées voisine */
	/* de proche en proche délimitées par des pierre de même couleur  */
	/* en commençant par l'intersection vide à la position pos.       */
	/* Important : Si la case ne fait pas partie d'un territoire      */
	/* de même couleur, retourne quand même l'ensemble des            */
    /* intersections voisines mais en spécifiant que ce "Territoire"  */
	/*  n'a aucune couleur    										  */
	/******************************************************************/
	public Territoire determineTerritoire(Plateau plateau, Position pos){
		/**************************************************************/
		/*					Déclaration des variable 		          */
		/**************************************************************/
		int i;
		int x, y, t;
		int actuel;
		int testDedans, dansTerritoire;
		Pion pion 					= new Pion();
		Position positionRef        = new Position();
		List<Position> voisins 		= new ArrayList<Position>();
		List<Position> lesPositions = new ArrayList<Position>();
		Territoire pTerritoire      = new Territoire();
		
		/*************************************************************/
		/*							Codes				 		     */
		/*************************************************************/
		if (plateau == null) return pTerritoire = null;		
		
		pion = pion.obtenirPionEnPosition(plateau, pos);
		if (pion.couleur != Couleur.RIEN) return pTerritoire = null;
		
		pTerritoire.lesCoordCases.nbrPositionsActuel = 1;
		pTerritoire.laCouleur = Couleur.ETRANGE; // La couleur n'est pas connue au début
		
		lesPositions = pTerritoire.lesCoordCases.lesPositions; 
		lesPositions.add(pion.position);
		
		actuel = 0;
		
		while (actuel < pTerritoire.lesCoordCases.nbrPositionsActuel){
			positionRef = lesPositions.get(actuel);
			

			for (i = 0; i < 4; i++){ 
				voisins.add(new Position()); // Récupérer la position de réference du pion
				voisins.get(i).x = positionRef.x;
				voisins.get(i).y = positionRef.y;
			}
			
			voisins.get(0).x = voisins.get(0).x+1;
			Log.i("determineTerritoire", "voisin 0 position: x =" + voisins.get(0).x + "; y=" + voisins.get(0).y);
			
			voisins.get(2).x = voisins.get(2).x-1;
			Log.i("determineTerritoire", "voisin 2 position: x =" + voisins.get(2).x + "; y=" + voisins.get(2).y);
			
			voisins.get(1).y = voisins.get(1).y+1;
			Log.i("determineTerritoire", "voisin 1 position: x =" + voisins.get(1).x + "; y=" + voisins.get(1).y);
			
			voisins.get(3).y = voisins.get(3).y-1;
			Log.i("determineTerritoire", "voisin 3 position: x =" + voisins.get(3).x + "; y=" + voisins.get(3).y);
			
			for (i = 0; i<4; i++){
				x = voisins.get(i).x;
				y = voisins.get(i).y;
				
				t = plateau.taille; 
				testDedans = 0;
				if((x >= 0) && (x<t) && (y >= 0) && (y<t)){
					testDedans = 1;
				}
				
				if (testDedans == 1){
					dansTerritoire = this.appartientAuTerritoire(voisins.get(i), pTerritoire);
					if (dansTerritoire == 0){ 
						Pion pionIci = new Pion();
						//pionIci.initialiserUnPion(pionIci);
						pionIci = pionIci.obtenirPionEnPosition(plateau, voisins.get(i));
									
						if (pionIci.couleur == Couleur.RIEN){
							lesPositions.add(voisins.get(i));
							pTerritoire.lesCoordCases.nbrPositionsActuel++;
						}												
						else {
							// Mettre à jour la couleur du territoire
							if (pTerritoire.laCouleur == Couleur.ETRANGE){
								// C'est le premier pion d'une couleur proche du territoire
								// Par défaut, c'est la couleur du territoire
								pTerritoire.laCouleur = pionIci.couleur;
							}
							else if (pTerritoire.laCouleur != Couleur.RIEN){
								// Si un autre pion n'est pas de la couleur du territoire alors
								// le territoire n'a pas de couleur
								if (pTerritoire.laCouleur != pionIci.couleur){
									pTerritoire.laCouleur = Couleur.RIEN;
								}
							}
						}																		
					}			
				}		
			}
			voisins.clear();
			actuel++;		
		}
		Log.i("determineTerritoire", "*************************************************************");
		Log.i("determineTerritoire", "pTerritoire appartient à la couleur =" + pTerritoire.laCouleur);
		Log.i("determineTerritoire", "nombre d'élément du territoire =" + pTerritoire.lesCoordCases.nbrPositionsActuel);
		Log.i("determineTerritoire", "nombre actuel =" + actuel);
		this.afficherChaine(pTerritoire);
		return pTerritoire;
	}
}
