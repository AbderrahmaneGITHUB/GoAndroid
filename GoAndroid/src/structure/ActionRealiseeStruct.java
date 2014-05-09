package structure;

import java.util.ArrayList;
import java.util.List;

import constante.Constante;

public class ActionRealiseeStruct {
	public List<ActionJoueur> lesActions;
	public int nbrPositionsActuel;	
	public int nbrPositionsMax;	
	
	/******************************************************************/
	/*					  Constructeur Action		   				  */
	/******************************************************************/
	public  ActionRealiseeStruct()
	{
		this.initialisationAction();
	}
	
	/******************************************************************/
	/*					  initialisation Action		   				  */
	/******************************************************************/
	public int initialisationAction()
	{
		this.lesActions = new ArrayList<ActionJoueur>();
		this.nbrPositionsActuel = 0;
		this.nbrPositionsMax = 3*Constante.NBR_MAX_CASES;
		return 1;
	}	
}
