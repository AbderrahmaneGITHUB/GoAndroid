package structure;

import java.util.ArrayList;
import java.util.List;

import constante.Constante;

public class Action {
	public List<ActionJoueur> lesActions;
	public int nbrPositionsActuel;	
	public int nbrPositionsMax;	
	
	/******************************************************************/
	/*					  initialisation Action		   				  */
	/******************************************************************/
	public int initialisationPositions(Plateau plateau, Action inAction)
	{
		inAction.lesActions = new ArrayList<ActionJoueur>();
		inAction.nbrPositionsActuel = 0;
		inAction.nbrPositionsMax = 3*Constante.NBR_MAX_CASES;
		
		return 1;
	}	
}
