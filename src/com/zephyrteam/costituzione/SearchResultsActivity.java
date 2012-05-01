package com.zephyrteam.costituzione;

import com.zephyrteam.costituzione.fragments.SearchResultsFragment;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

public class SearchResultsActivity extends BasicActivity {
	
	@Override
	public void onCreate(Bundle instance) {
		super.onCreate(instance);
		Intent callingIntent = getIntent();
		
		if (callingIntent.getAction().equals(Intent.ACTION_SEARCH)) {
			String keyword = callingIntent.getStringExtra(SearchManager.QUERY);
			Bundle data = callingIntent.getBundleExtra(SearchManager.APP_DATA);
			
			SearchResultsFragment fragment = 
					(data != null) ? SearchResultsFragment.newInstance(keyword, data.getInt("category", -1)) : SearchResultsFragment.newInstance(keyword);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(android.R.id.content, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_mainscreen, menu);
        
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.search:
				onSearchRequested();
				break;
			case R.id.mainscreen_favorites:
				Intent intent = new Intent(this, FavoritesActivity.class);
				startActivity(intent);
				break;
		}
		return true;
	}
}
