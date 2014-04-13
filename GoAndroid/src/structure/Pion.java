package structure;

import enumeration.Couleur;

public class Pion {
	
	/******************************************************************/
	/*						Structure Pion				  		  	  */
	/******************************************************************/ 
	public Position position;
	public Couleur couleur;
	
	/******************************************************************/
	/*					initialiserUnPion							  */
	/* @brief Initialisation d'un Pion							  	  */
	/******************************************************************/
	public void initialiserUnPion(Pion inPion){

		inPion.position = new Position();
		inPion.position.x = 30;
		inPion.position.y = 30;
		inPion.couleur = Couleur.RIEN;
	}
	
	/******************************************************************/
	/*							enleverPionEn						  */
	/* @ brief E nlève le pion en fonction de sa position La valeur   */
	/*  retournée est 1 sauf s ’il n ’est pas possible d ’enlever un  */
	/*  pion en cette position (la valeur 0 est alors				  */
	/*  retourné e)													  */
	/*  Si le pointeur pionEnlever n ’est pas NULL , alors écrit      */
	/*  à cette adresse les information sur le pion enlevé			  */
	/******************************************************************/
	public int enleverPionEn(Plateau plateau, Position pos, Pion pionEnlever){
		Couleur coul;

		coul = plateau.positionPlateau.get(pos.y*plateau.taille + pos.x).couleur;
		if (coul != Couleur.RIEN)
		{
			plateau.positionPlateau.get(pos.y*plateau.taille + pos.x).couleur = Couleur.RIEN;

			if (pionEnlever != null)
			{
				pionEnlever.position = pos;
				pionEnlever.couleur = coul;
			}
			return 1; //bien enlevé
		}
		return 0; //il y a aucun pion à cette position
	}
	
	/******************************************************************/
	/*							placerPionEn						  */
	/* @brief Place le pion en fonction de sa position (contenu dans  */
	/* sa structure) La valeur retournÃ©e est 1 sauf sâ€™il nâ€™est   */
	/* pas possible de placer un pion en cette position    			  */
	/* (la valeur 0 est alors retournÃ©e)							  */
	/******************************************************************/
	public int placerPionEn(Plateau plateau, Pion pion){
		return placerPionEnPosition(plateau, pion.position, pion.couleur);
	}
	
	/******************************************************************/
	/*					placerPionEnPosition						  */
	/* * @brief Place un pion en fonction de sa position et de sa 	  */
	/* couleur La valeur retournée est 1 sauf s’il n’est pas possible */
	/*  de placer un pion en cette position (la valeur 0 est alors	  */
	/*  retournée)													  */
	/******************************************************************/
	public int placerPionEnPosition(Plateau plateau, Position pos, Couleur couleur){
		Pion pion = obtenirPionEnPosition(plateau, pos);
		if (pion.couleur != Couleur.RIEN) return 0;
		plateau.positionPlateau.get(pos.y*plateau.taille + pos.x).couleur = couleur;
		return 1;
	}
	
	/******************************************************************/
	/*							obtenirPionEn						  */
	/* @brief Retourn le pion à la position pos du plateau		  	  */
	/******************************************************************/
	public Pion obtenirPionEnPosition(Plateau plateau, Position pos){
		return obtenirPionEn(plateau, pos.x, pos.y);
	}
	
	/******************************************************************/
	/*							obtenirPionEn						  */
	/* @brief Retourne le pion en la position (x,y) du plateau		  */
	/******************************************************************/
	public Pion obtenirPionEn(Plateau plateau, int x, int y){
		Pion pion = new Pion();
		pion.position = new Position();
		pion.position.x = x;
		pion.position.y = y;
		pion.couleur = plateau.positionPlateau.get(y*plateau.taille + x).couleur;
		return pion;
	}
}
