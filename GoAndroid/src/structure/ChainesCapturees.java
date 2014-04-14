package structure;

import enumeration.Couleur;


public class ChainesCapturees  extends Chaines {

	
	public Chaines captureChaines(Pion pion, Plateau plateau, int valide) {		
		/**************************************************/
		/*					Déclaration					  */
		/**************************************************/		
		int i, x, y, t;
		int testDedans;
		Couleur couleurRef;
		
		Position positionRef 			= new Position();
		Position voisins[] 				= new Position[4];
		Chaine valideChaine 			= new Chaine();
		Libertes valideLiberte 			= new Libertes();
		Chaine rahim 					= new Chaine();
		Chaines touteChaineCapturees 	= new Chaines();
		/**************************************************/
		/*						Codes					  */
		/**************************************************/
		//si la chaine est vide, on sort de la fonction
		if (pion == null || plateau == null) return touteChaineCapturees = null;
		
		positionRef = pion.position;
		couleurRef = pion.couleur;
		/*************				Initialisation			**********/	
		this.initialisationChaine(plateau, valideChaine);
		valideLiberte.initialisationPositions(plateau, valideLiberte);
		rahim.initialisationChaine(plateau, rahim);
		this.initialisationChaines(plateau, touteChaineCapturees);
		
		// 4 chaine àcapturer au max
		touteChaineCapturees.nbrPositionsMax = 4;
		
		// Par defaut l'entier valide vaut 1
		valide = 1;
		
		// Vérification
		if (pion.couleur == Couleur.RIEN)
			return touteChaineCapturees = null;
		
		valideChaine = this.determinerChaine(plateau, pion.position);
		
		if (valideChaine != null) {

			valideLiberte = this.determineLiberte(plateau, valideChaine);

			if ((valideLiberte.nbrPositionsActuel == 0) && (valideLiberte != null)) {
				valide = 0;
			}
		}
		
		for (i = 0; i<4; i++){
			voisins[i] = new Position();
			voisins[i].x = positionRef.x;
			voisins[i].y = positionRef.y;
		}
		
		voisins[0].x++;
		voisins[2].x--;	
		voisins[1].y++;	
		voisins[3].y--;
		
		for (i = 0; i < 4; i++) {
			x = voisins[i].x;
			y = voisins[i].y;
			t = plateau.taille;
	
			testDedans = 0;
			if((x >= 0) && (x<t) && (y >= 0) && (y<t)){
				testDedans = 1;
			}
			
			if (testDedans == 1) {
				
				// Tester si dans cette position, s'il y a un pion
				Pion rahimPion = new Pion();
				rahimPion.initialiserUnPion(rahimPion);
				rahimPion.obtenirPionEn(plateau, x, y);
				
				if (rahimPion.couleur != Couleur.RIEN && rahimPion.couleur != Couleur.ETRANGE) {
					if (couleurRef != rahimPion.couleur) {
						Chaine  rahimChaineACapturer = new Chaine();
						Libertes rahimLiberte = new Libertes();
						rahimChaineACapturer.initialisationChaine(plateau, rahimChaineACapturer);
						rahimLiberte.initialisationPositions(plateau, rahimLiberte);
						
						// Construction de la chaine de ce pion trouvé (d'une autre couleur)
						rahimChaineACapturer = determinerChaine(plateau, rahimPion.position);

						if (rahimChaineACapturer != null) {
							
							// Construction de la chaine à qui appartien le pion trouvé
							rahimLiberte = determineLiberte(plateau, rahimChaineACapturer);
							if ((rahimLiberte.nbrPositionsActuel == 0) && (rahimLiberte != null)) {
								valide = 1;	
								
								touteChaineCapturees.lesChaines.get(touteChaineCapturees.nbrPositionsActuel).laCouleur = rahimChaineACapturer.laCouleur;
								touteChaineCapturees.lesChaines.get(touteChaineCapturees.nbrPositionsActuel).lesCoordCases = rahimChaineACapturer.lesCoordCases;
										
								touteChaineCapturees.nbrPositionsActuel++;							
							}				
						}			
					}		
				}		
			}		
		}	
		return touteChaineCapturees;
	}
}
