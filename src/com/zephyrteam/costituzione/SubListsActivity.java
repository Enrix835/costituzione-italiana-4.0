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
            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_categories, menu);
			
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