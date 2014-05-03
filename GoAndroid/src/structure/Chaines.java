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
	/*					Constructeur Chaines				  		  */
	/******************************************************************/ 
	public Chaines(){	
		this.initialisationChaines();		
	}
	
	/******************************************************************/
	/* @brief : Initialiser une Chaine			  		  			  */
	/******************************************************************/ 
	public int initialisationChaines(){	
		this.lesChaines = new ArrayList<Chaine>();
		this.nbrPositionsActuel = 0;
		this.nbrPositionsMax = Constante.NBR_MAX_CASES;			
		return 1;
	}
}
