package com.example.goandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class OptionsActivity extends MainActivity {

	public boolean son,musique;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		/******************************************************/
    	/*					Initialisation					  */
    	/******************************************************/
		super.onCreate(savedInstanceState);                   
        setContentView(R.layout.optionsactivity);  
        
        if(NoMusic == true ){
        	View musique_oui = findViewById(R.id.musique_oui);
        	musique_oui.setBackgroundResource(R.drawable.bouton_oui_select);
        }else{
        	View musique_non = findViewById(R.id.musique_non);
        	musique_non.setBackgroundResource(R.drawable.bouton_non_select);
        }
        
        if(NoSound == true ){
        	View son_oui = findViewById(R.id.son_oui);
        	son_oui.setBackgroundResource(R.drawable.bouton_oui_select);
        }else{
        	View son_non = findViewById(R.id.son_non);
        	son_non.setBackgroundResource(R.drawable.bouton_non_select);
        }
        
    } 

	public void couper_musique(View v){
		NoMusic = false;
		musique = false;
		
		View musique_non = findViewById(R.id.musique_non);
    	musique_non.setBackgroundResource(R.drawable.bouton_non_select);
    	View musique_oui = findViewById(R.id.musique_oui);
    	musique_oui.setBackgroundResource(R.drawable.bouton_oui);
	}
	
	public void activer_musique(View v){
		NoMusic = true;
		musique = true;
		
		View musique_oui = findViewById(R.id.musique_oui);
    	musique_oui.setBackgroundResource(R.drawable.bouton_oui_select);
    	View musique_non = findViewById(R.id.musique_non);
    	musique_non.setBackgroundResource(R.drawable.bouton_non);
	}
	
	public void couper_son(View v){
		NoSound = false;
		son = false;
		
		View son_non = findViewById(R.id.son_non);
    	son_non.setBackgroundResource(R.drawable.bouton_non_select);
    	View son_oui = findViewById(R.id.son_oui);
    	son_oui.setBackgroundResource(R.drawable.bouton_oui);
	}
	
	public void activer_son(View v){
		NoSound = true;
		son = true;
		Log.v("AS_TEST", "NoSound : "+NoSound);
		
		View son_oui = findViewById(R.id.son_oui);
    	son_oui.setBackgroundResource(R.drawable.bouton_oui_select);
    	View son_non = findViewById(R.id.son_non);
    	son_non.setBackgroundResource(R.drawable.bouton_non);
	}
}
