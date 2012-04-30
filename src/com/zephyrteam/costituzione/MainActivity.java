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

import java.io.File;

import com.zephyrteam.costituzione.util.IO;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends BasicActivity {
    ActionBar mActionBar;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(false);
        final Context context = this;
        
        new Runnable() {
        	@Override
			public void run() {
				File database = context.getDatabasePath("costituzione.db");
		   	 	if (!database.exists()) {
		   	 		IO.copyDatabase(context);
		   	 	}
			}
        	
        }.run();
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_mainscreen, menu);
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