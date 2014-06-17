package com.example.goandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class OptionsActivity extends MainActivity {

	public boolean son,musique;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		/******************************************************/
    	/*					Initialisation					  */
    	/******************************************************/
		super.onCreate(savedInstanceState);                   
        setContentView(R.layout.optionsactivity);     
    } 

	public void couper_musique(View v){
		NoMusic = false;
		musique = false;
		//Log.v("AS_TEST", "NoMusic : "+NoMusic);
	}
	
	public void activer_musique(View v){
		NoMusic = true;
		musique = true;
	}
	
	public void couper_son(View v){
		NoSound = false;
		son = false;
	}
	
	public void activer_son(View v){
		NoSound = true;
		son = true;
		Log.v("AS_TEST", "NoSound : "+NoSound);
	}
}
