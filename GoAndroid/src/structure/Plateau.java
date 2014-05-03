package structure;

import java.util.ArrayList;
import java.util.List;

import enumeration.Couleur;


public class Plateau {
	
	/******************************************************************/
	/*						Structure Plateau				  		  */
	/******************************************************************/ 
	public List<Pion> positionPlateau;
	public int taille;	
	
	/******************************************************************/
	/*							Constructeur		    			  */
	/******************************************************************/
	
	
	
	/******************************************************************/
	/*							initialisationPlateau    			  */
	/******************************************************************/
	public void initialisationPlateau(int taille, Plateau plateau)
	{
		plateau.positionPlateau = new ArrayList<Pion>();
		plateau.taille = taille;		
		effacerPlateau(plateau);
		initialisationPosition (plateau);
	}
	
	/******************************************************************/
	/*							effacerPlateau						  */
	/* @brief : Mettre la couleur RIEN dans chaque position du tableau*/
	/* Afin d'avoir un tableau vide sans une couleur enregistré       */
	/******************************************************************/
	public void effacerPlateau(Plateau plateau)
	{
		int taille = plateau.taille;
		
		for(int i = 0; i < taille*taille; i++)
		{
			Pion pion = new Pion();	
			pion.position = new Position();
			plateau.positionPlateau.add(pion);
			plateau.positionPlateau.get(i).couleur = Couleur.RIEN;
		}		
	}
	
	/******************************************************************/
	/*						initialisationPosition 					  */
	/* @brief : Initialiser les positions du plateau aprés sa création*/
	/******************************************************************/
	public void initialisationPosition (Plateau plateau){
		int z = 0;
		int taille = plateau.taille;
		for(int j=0; j < taille; j++)
		{
			for(int k=0; k < taille; k++)
			{
				plateau.positionPlateau.get(z).position.x =  k;
				plateau.positionPlateau.get(z).position.y =  j;
				z++;
			}
		}	
		z = 4;
	}		
}
