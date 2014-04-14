package structure;

import java.util.ArrayList;
import java.util.List;

import constante.Constante;



public class Chaines extends Chaine{
	
	/******************************************************************/
	/*						Structure Chaines				  		  */
	/******************************************************************/ 
	public List<Chaine> lesChaines;
	public int nbrPositionsActuel;
	public int nbrPositionsMax;
	
	/******************************************************************/
	/* @brief : Initialiser une Chaine			  		  			  */
	/******************************************************************/ 
	public int initialisationChaines(Plateau plateau, Chaines inChaines){
	
		inChaines.lesChaines = new ArrayList<Chaine>();
		inChaines.nbrPionsActuel = 0;
		inChaines.nbrPionsMax = Constante.NBR_MAX_CASES;			
		return 1;
	}
	
}
