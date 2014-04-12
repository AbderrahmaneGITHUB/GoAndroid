package com.example.goandroid;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class plateau_neuf extends MainActivity{
	
	private View maVue;
	int taille_plateau = 9;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plateau_neuf);
        
        maVue = findViewById(R.id.imageView1);
    	
    	maVue.setOnTouchListener(
    			
    	new View.OnTouchListener() {
            public boolean onTouch(View myView, MotionEvent event) {          	
            	
            	/******************************************************/
            	/*				Declaration variables				  */
            	/******************************************************/
                int action, xI, xY, px, py;
                float x, y;

              	/******************************************************/
            	/*							Codes					  */
            	/******************************************************/
                action = event.getAction();
                x = event.getX();
                y = event.getY();
                xI = maVue.getWidth();
                xY = maVue.getHeight();
                px = (int) (x/(xI/taille_plateau))+1;
                py = (int) (y/(xY/taille_plateau))+1;
                
                if (action==MotionEvent.ACTION_UP)
                {
                	if(px>9 || px<=0 || py>9 || py<=0)
                	{
                		Toast.makeText(plateau_neuf.this, "Hors du plateau", (int)2000).show();
                    }
                	else
                    {
                		//Drawable test = drawable(R.drawable.pion_noir);            		
                    	Toast.makeText(plateau_neuf.this, "touché en ("+px+" / "+py+")", (int)2000).show();
                    	//Test
                        ImageView iv = new ImageView(plateau_neuf.this);
                        iv.setImageResource(R.drawable.pion_noir);                     
                    }
                }      
                return true;
            }
        });
    } 	
}
