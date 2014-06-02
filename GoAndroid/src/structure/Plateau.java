package structure;

import java.util.ArrayList;
import java.util.List;

import com.example.goandroid.R;

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
	public Plateau(int intaille){		
		this.taille = intaille;	
		this.positionPlateau = new ArrayList<Pion>();			
		this.effacerPlateau();
		this.initialisationPosition ();
	}
	
	/******************************************************************/
	/*							initialisationPlateau    			  */
	/******************************************************************/
//	public void initialisationPlateau(int taille, Plateau plateau)
//	{
//		plateau.positionPlateau = new ArrayList<Pion>();
//		plateau.taille = taille;		
//		this.effacerPlateau();
//		this.initialisationPosition ();
//	}
	
	/******************************************************************/
	/*							effacerPlateau						  */
	/* @brief : Mettre la couleur RIEN dans chaque position du tableau*/
	/* Afin d'avoir un tableau vide sans une couleur enregistré       */
	/******************************************************************/
	public void effacerPlateau()
	{
		int taille = this.taille;
		
		for(int i = 0; i < taille*taille; i++)
		{
			Pion pion = new Pion();	
			pion.position = new Position();
			this.positionPlateau.add(pion);
			this.positionPlateau.get(i).couleur = Couleur.RIEN;
		}		
	}
	
	/******************************************************************/
	/*						initialisationPosition 					  */
	/* @brief : Initialiser les positions du plateau aprés sa création*/
	/******************************************************************/
	public void initialisationPosition (){
		int z = 0;
		int taille = this.taille;
		for(int j=0; j < taille; j++)
		{
			for(int k=0; k < taille; k++)
			{
				this.positionPlateau.get(z).position.x =  k;
				this.positionPlateau.get(z).position.y =  j;
				z++;
			}
		}	
		z = 4;
	}	
	
	// TODO méthode afficher plateau en fonction de la taille du plateau
	public void afficherPlateau(){
		int z = 0;
		int taille = this.taille;
		
		for(int j=0; j < taille; j++)
		{
			for(int k=0; k < taille; k++)
			{
								
				z++;
			}
		}	
	}
}
