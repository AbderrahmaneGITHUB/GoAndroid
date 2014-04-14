package structure;

import java.util.ArrayList;
import java.util.List;

import constante.Constante;


public class Positions extends Position{
	
	/******************************************************************/
	/*						Structure Positions				  		  */
	/******************************************************************/ 
	public List<Position> lesPositions;
	public int nbrPositionsActuel;
	public int nbrPositionsMax;
	
	/******************************************************************/
	/*					  initialisationPosition    				  */
	/******************************************************************/
	public int initialisationPositions(Plateau plateau, Positions inPositions)
	{
		inPositions.lesPositions = new ArrayList<Position>();
		inPositions.nbrPositionsActuel = 0;
		inPositions.nbrPositionsMax = Constante.NBR_MAX_CASES;
		return 1;
	}
}
