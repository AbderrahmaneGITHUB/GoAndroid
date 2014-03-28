package com.example.goandroid;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	/******************************************************************/
	/*				Declaration des variables globales		   		  */
	/******************************************************************/
	private View maVue;	
	
	/******************************************************************/
	/*							onCreate		   					  */
	/******************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		maVue = findViewById(R.id.imageView1);
		
		maVue.setOnTouchListener(
				
		new View.OnTouchListener() {
	        public boolean onTouch(View myView, MotionEvent event) {
	        	
	        	
	        	/******************************************************/
	        	/*				Declaration variables				  */
	        	/******************************************************/
	            int action, xI, xY, px, py;
	            float x, y;
555555
	          	/******************************************************/
	        	/*							Codes					  */
	        	/******************************************************/
	            action = event.getAction();
	            x = event.getX();
	            y = event.getY();
	            xI = maVue.getWidth();
	            xY = maVue.getHeight();
	            px = (int) (x/(xI/9))+1;
	            py = (int) (y/(xY/9))+1;
	            
	            if (action==MotionEvent.ACTION_UP)
	            {
	            	if(px>9 || px<=0 || py>9 || py<=0)
	            	{
	            		Toast.makeText(MainActivity.this, "Hors du plateau", 2000).show();
	                }
	            	else
	                {
	                	Toast.makeText(MainActivity.this, "touch� en ("+px+" / "+py+")", 2000).show();
	                	//Test
	                    ImageView iv = new ImageView(MainActivity.this);
	                    iv.setImageResource(R.drawable.pion_noir);                
	                }
	            }      
	            return true;
	        }
        });	
	}
	
	/******************************************************************/
	/*							onCreateOptionsMenu					  */
	/******************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	
	/******************************************************************/
	/*							Les fonctions	  					  */
	/******************************************************************/
	public int memePosition(Position pos1, Position pos2){
		if((pos1.x == pos2.x) && (pos1.y == pos2.y))
			return 1;
		else
			return 0;		
	}
	
	/******************************************************************/
	/*							Les fonctions	  					  */
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
	/*			Declaration des Structures et des Enums globales	  */
	/******************************************************************/

	public enum Couleur {
		BLAN,
		NOIR;
	}
	
	public enum Couleur2 {
		BLAN,
		NOIR;
	}
	public class Position{
		int x;
		int y;
		
	}

	public class Pion{
		Position position;
		Couleur couleur;
	}

	public class Positions{
		List<Position> lesPositions;
		int nbrPositionsActuel;
		int nbrPositionsMax;
	}

	class Chaine{
		Positions lesCoordCases;
		Couleur couleur;
	}
}


