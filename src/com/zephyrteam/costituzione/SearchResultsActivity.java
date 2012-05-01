package com.zephyrteam.costituzione;

import com.zephyrteam.costituzione.fragments.SearchResultsFragment;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class SearchResultsActivity extends BasicActivity {
	
	@Override
	public void onCreate(Bundle instance) {
		super.onCreate(instance);
		Intent callingIntent = getIntent();
		
		if (callingIntent.getAction().equals(Intent.ACTION_SEARCH)) {
			String keyword = callingIntent.getStringExtra(SearchManager.QUERY);
			
			SearchResultsFragment fragment = SearchResultsFragment.newInstance(keyword);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(android.R.id.content, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
		}
	}
}
