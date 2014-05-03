package structure;

import java.util.ArrayList;
import java.util.List;
import constante.Constante;

public class Positions{
	
	/******************************************************************/
	/*						Structure Positions				  		  */
	/******************************************************************/ 
	public List<Position> lesPositions;
	public int nbrPositionsActuel;
	public int nbrPositionsMax;
	
	/******************************************************************/
	/*					Constructeur Positions				  		  */
	/******************************************************************/ 
	public Positions(){		
		this.initialisationPositions();
	}
	
	/******************************************************************/
	/*					  initialisationPosition    				  */
	/******************************************************************/
	public int initialisationPositions()
	{
		this.lesPositions = new ArrayList<Position>();
		this.nbrPositionsActuel = 0;
		this.nbrPositionsMax = Constante.NBR_MAX_CASES;
		return 1;
	}
}
