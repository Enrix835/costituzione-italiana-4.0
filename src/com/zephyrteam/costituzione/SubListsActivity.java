  /*
   * Costituzione Italiana 4.0 
   * Copyright (C) 2012 
   *	 Gnufabio <me@gnufabio.com>, Enrix835 <enrico.trt@gmail.com>
   *
   * This program is free software: you can redistribute it and/or modify
   * it under the terms of the GNU General Public License as published by
   * the Free Software Foundation, either version 3 of the License, or
   * (at your option) any later version.
   *
   * This program is distributed in the hope that it will be useful,
   * but WITHOUT ANY WARRANTY; without even the implied warranty of
   * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   * GNU General Public License for more details.
   *
   * You should have received a copy of the GNU General Public License
   * along with this program.  If not, see <http://www.gnu.org/licenses/>.
   */

package com.zephyrteam.costituzione;

import com.zephyrteam.costituzione.fragments.SubListsFragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

public class SubListsActivity extends BasicActivity {
	int index = -1;
	SearchView mSearchView;
	public MenuItem mSearchItem;
	String initialQuery = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (savedInstanceState == null) {
            SubListsFragment details = new SubListsFragment();
            details.setArguments(getIntent().getExtras());
            index = getIntent().getIntExtra(Constants.EXTRA_INDEX, -1);
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				Intent intent = new Intent(Intent.ACTION_SEARCH);
				intent.setClass(SubListsActivity.this, SearchResultsActivity.class);
				Bundle extraData = new Bundle();
				extraData.putInt(Constants.EXTRA_CATEGORY, index);
				extraData.putString(SearchManager.QUERY, query);
				
				intent.putExtras(extraData);
				startActivity(intent);
				
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
        
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.mainscreen_favorites:
				Intent intent = new Intent(this, FavoritesActivity.class);
				startActivity(intent);
				break;
		}
		return true;
	}
}