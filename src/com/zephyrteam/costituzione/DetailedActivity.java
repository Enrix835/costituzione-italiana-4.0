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

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import com.zephyrteam.costituzione.components.SingleEntry;
import com.zephyrteam.costituzione.fragments.DetailedFragment;

public class DetailedActivity extends BasicActivity {
	SingleEntry mEntry;
	ShareActionProvider mShareProvider;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
        	Bundle data = getIntent().getExtras();
        	mEntry = new SingleEntry(data.getString("title"), data.getString("body"), data.getInt("category"), data.getInt("id"), data.getBoolean("favorite"));
            DetailedFragment df = new DetailedFragment(data);
            getFragmentManager().beginTransaction().replace(android.R.id.content, df).commit();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.actionbar_details, menu);
    	if (mEntry == null) return false;
    	boolean isFav = mEntry.isFavourite();
    	
    	int icon = isFav ? R.drawable.is_favourite : R.drawable.is_not_favourite;
    	String text = isFav ? "Remove from favorites" : "Add to favorites";
    	
    	menu.findItem(R.id.favourite_status).setIcon(icon).setTitle(text);
    	
    	MenuItem shareItem = menu.findItem(R.id.menu_item_share);
    	mShareProvider = (ShareActionProvider) shareItem.getActionProvider();
    	mShareProvider.setShareIntent(getShareIntent());
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
		
		return true;
	}
    
    
    public Intent getShareIntent() {
    	String text = mEntry.getTitle() + ": " + mEntry.getBody();
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/*");
		shareIntent.putExtra(Intent.EXTRA_TEXT, text);
		return shareIntent;
    }
}
