package structure;

import java.util.List;



public class Chaines {
	
	/******************************************************************/
	/*						Structure Chaines				  		  */
	/******************************************************************/ 
	List<Chaine> lesChaines;
	int nbrPositionsActuel;
	int nbrPositionsMax;
	
	/******************************************************************/
	/*					appartientAuTerritoire						  */
	/* @brief Juste pour la lisibilité du code car un territoire      */	
	/* 	est un type synonyme de chaine							  	  */
	/******************************************************************/
	public int appartientAuTerritoire(Position pos, Territoire territoire){
		return appartientAlaChaine(pos, territoire);
	}
	
	/******************************************************************/
	/*						appartientAlaChaine	  					  */
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
}
