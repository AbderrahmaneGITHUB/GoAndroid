package structure;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import enumeration.Couleur;


public class Libertes extends Positions{

	
	/******************************************************************/
	/*							determineLiberte				      */
	/* @brief Détermine l'ensemble des libertés d'une chaine donnée   */
	/* en fonction de la position des pions sur le plateau  		  */
	/******************************************************************/
	public Libertes determineLiberte(Plateau plateau, Chaine chaine){
		/**************************************************************/
		/*					Déclaration des variable 		          */
		/**************************************************************/
		int t, x, y; 
		int i = 0, j = 0, z = 0;
		int testDedans;
		List<Position> lesPositions = new ArrayList<Position>();
		Libertes pLiberte 			= new Libertes();
		Position voisins[] 			= new Position[4];
		Position positionRef		= new Position();
		/*************************************************************/
		/*							Codes				 		     */
		/*************************************************************/
		//si la chaine est vide, on sort de la fonction
		if (chaine == null || plateau == null) return pLiberte = null;
		
		this.initialisationPositions(plateau, pLiberte);	
		lesPositions = pLiberte.lesPositions;
		
		for (i = 0; i < chaine.lesCoordCases.nbrPositionsActuel; i++)
		{
			positionRef = chaine.lesCoordCases.lesPositions.get(i);
			Log.i("determineLiberte", "positionRef: x =" + positionRef.x + "; y=" + positionRef.y);
			
			for (j = 0; j<4; j++){
				voisins[j] = new Position();
				voisins[j].x = positionRef.x;
				voisins[j].y = positionRef.y;
			}

			voisins[0].x++;
			Log.i("determineLiberte", "voisin 0 position: x =" + voisins[0].x + "; y=" + voisins[0].y);

			voisins[2].x--;
			Log.i("determineLiberte", "voisin 2 position: x =" + voisins[2].x + "; y=" + voisins[2].y);
			
			voisins[1].y++;
			Log.i("determineLiberte", "voisin 1 position: x =" + voisins[1].x + "; y=" + voisins[1].y);
			
			voisins[3].y--;
			Log.i("determineLiberte", "voisin 3 position: x =" + voisins[3].x + "; y=" + voisins[3].y);
			
			for (z = 0; z<4; z++)
			{
				x = voisins[z].x;
				y = voisins[z].y;
				
				t = plateau.taille; 	
				testDedans = 0;
				if((x >= 0) && (x<t) && (y >= 0) && (y<t)){
					testDedans = 1;
				}
				
				if (testDedans == 1)
				{
					int dansLibertes = this.appartientAuxPositions(voisins[z], pLiberte);
					if (dansLibertes == 0)
					{
						Pion pionIci = new Pion();
						pionIci.initialiserUnPion(pionIci);
						pionIci = pionIci.obtenirPionEnPosition(plateau, voisins[z]);
						if (pionIci.couleur == Couleur.RIEN)
						{
							lesPositions.add(voisins[z]);
							pLiberte.nbrPositionsActuel++;
						}				
					}
				}	
			}
		}
		Log.i("determineLiberte", "nbrPositionsActuel =" + pLiberte.nbrPositionsActuel);
		return pLiberte;
	}
}
