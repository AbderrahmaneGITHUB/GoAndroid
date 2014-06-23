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

	private TailleEcran tailleEcran;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		/******************************************************/
    	/*					Initialisation					  */
    	/******************************************************/
		super.onCreate(savedInstanceState);                   
        setContentView(R.layout.menuplateau);     
    	
        /*********** Si écran trop petit, on ne propose pas les plateau de 13 et 19 lignes ****************/
		String taille = tailleEcran.getSizeName(this);
		if(taille != "grand"){
			View bouton_13 = findViewById(R.id.treize_case);
			View bouton_19 = findViewById(R.id.dixneuf_case);
			bouton_13.setVisibility(View.INVISIBLE);
			bouton_19.setVisibility(View.INVISIBLE);
		}
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
