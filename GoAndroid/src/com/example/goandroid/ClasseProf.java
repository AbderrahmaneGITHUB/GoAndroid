package com.example.goandroid;
import com.example.goandroid.Plateau;
import java.util.ArrayList;


public class ClasseProf {
	
	/******************************************************************/
	/*					appartientAuTerritoire						  */
	/* @brief Juste pour la lisibilité du code car un territoire      */	
	/* 	est un type synonyme de chaine							  	  */
	/******************************************************************/
	public int appartientAuTerritoire(Position pos, Territoire territoire){
		return appartientAlaChaine(pos, territoire);
	}
	
	/******************************************************************/
	/*					appartientAuxPositions						  */
	/* @brief 													  	  */
	/******************************************************************/
	public int appartientAuxPositions(Position pos, Positions lesPos){
		int res = 0;
		int i;
		for (i = 0; i < lesPos.nbrPositionsActuel; i++){
			if (memePosition(pos, lesPos.lesPositions.get(i)) == 1){
				res = 1;
				break;
			}
		}
		return res;
	}
		
	/******************************************************************/
	/*							obtenirPionEn						  */
	/* @brief initialise un pion								  	  */
	/******************************************************************/
	public Pion initialiserUnPion(){
		Pion pion = new Pion();
		pion.position = new Position();
		pion.position.x = 30;
		pion.position.y = 30;
		pion.couleur = Couleur.RIEN;
		return pion;
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
	int placerPionEnPosition(Plateau plateau, Position pos, Couleur couleur){
		Pion pion = obtenirPionEnPosition(plateau, pos);
		if (pion.couleur != Couleur.RIEN) return 0;
		plateau.positionPlateau.get(pos.y*plateau.taille + pos.x).couleur = couleur;
		return 1;
	}
	
	/******************************************************************/
	/*							obtenirPionEn						  */
	/* @brief Retourn le pion à la position pos du plateau		  	  */
	/******************************************************************/
	Pion obtenirPionEnPosition(Plateau plateau, Position pos){
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
	
	/******************************************************************/
	/*							memePosition	  					  */
	/******************************************************************/
	public int memePosition(Position pos1, Position pos2){
		if((pos1.x == pos2.x) && (pos1.y == pos2.y))
			return 1;
		else
			return 0;		
	}
	
	/******************************************************************/
	/*						appartientAlaChaine	  					  */
	/******************************************************************/
	public int appartientAlaChaine(Position pos, Chaine chaine){
		int res = 0;
		int i;
		for (i = 0; i < chaine.lesCoordCases.nbrPositionsActuel; i++)
		{
			if (memePosition(pos, chaine.lesCoordCases.lesPositions.get(i)) == 1){				
				res = 1;
				break;
			}			
		}
		return res;
	}
	
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
	/*						initialisationPosition					  */
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

