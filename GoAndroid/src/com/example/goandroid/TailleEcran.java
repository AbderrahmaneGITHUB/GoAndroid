package com.example.goandroid;

import android.content.Context;
import android.content.res.Configuration;

public class TailleEcran {
	public static String getSizeName(Context context) {
	    int screenLayout = context.getResources().getConfiguration().screenLayout;
	    screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK; 

	    switch (screenLayout) {
	    case Configuration.SCREENLAYOUT_SIZE_SMALL:
	        return "petit";
	    case Configuration.SCREENLAYOUT_SIZE_NORMAL:
	        return "normal";
	    case Configuration.SCREENLAYOUT_SIZE_LARGE:
	        return "grand";
	    case 4: // Configuration.SCREENLAYOUT_SIZE_XLARGE is API >= 9
	        return "xlarge";
	    default:
	        return "undefined";
	    }
	}
}
