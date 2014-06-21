package com.example.goandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuPlateau extends MainActivity{	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		/******************************************************/
    	/*					Initialisation					  */
    	/******************************************************/
		super.onCreate(savedInstanceState);                   
        setContentView(R.layout.menuplateau);     
    	
    } 
	
	/******************************************************************/
	/*							Type plateau						  */
	/******************************************************************/
	public void plateau_neuf(View v){
		Intent intent = new Intent(MenuPlateau.this, plateau_neuf.class);
		startActivity(intent);
	}
	
	public void plateau_treize(View v){
		Intent intent = new Intent(MenuPlateau.this, plateau_treize.class);
		startActivity(intent);
	}
	
	public void plateau_dixneuf(View v){
		Intent intent = new Intent(MenuPlateau.this, plateau_dixneuf.class);
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed(){
		MenuPlateau.this.finish();
	}
}
