package com.example.goandroid;

public class Position {
	int x;		
	int y;	
	
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
}
