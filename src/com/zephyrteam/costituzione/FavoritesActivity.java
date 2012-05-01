package com.zephyrteam.costituzione;

import com.zephyrteam.costituzione.fragments.FavoritesFragment;

import android.os.Bundle;

public class FavoritesActivity extends BasicActivity {
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        if (savedInstanceState == null) {
	        	FavoritesFragment frag = new FavoritesFragment();
	            getSupportFragmentManager().beginTransaction().add(android.R.id.content, frag).commit();
	        }
	    }
}
