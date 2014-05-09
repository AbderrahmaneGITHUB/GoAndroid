package structure;

import enumeration.PasseOuJoue;

public class ActionJoueur {
	public Pion pion;
	public PasseOuJoue passeeOuJouee;
	
	/******  Initialisation ActionJoueur   ****/
	public ActionJoueur(Pion inPion, PasseOuJoue inPasseeOuJouee){
		this.initialisationActionJoueur();
		this.pion = inPion;
		this.passeeOuJouee = inPasseeOuJouee;
	}
	
	/******  Initialisation ActionJoueur   ****/
	public int initialisationActionJoueur(){
		this.pion = new Pion();
		return 1;
	}
}
