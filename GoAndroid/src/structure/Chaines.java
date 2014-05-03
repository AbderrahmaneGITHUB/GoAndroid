package structure;

import java.util.ArrayList;
import java.util.List;
import constante.Constante;

public class Chaines {
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
		inChaines.nbrPositionsActuel = 0;
		inChaines.nbrPositionsMax = Constante.NBR_MAX_CASES;			
		return 1;
	}
}
