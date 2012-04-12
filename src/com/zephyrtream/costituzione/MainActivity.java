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

package com.zephyrtream.costituzione;

import com.zephyrtream.costituzione.fragments.TestFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;

public class MainActivity extends BasicActivity {
    ActionBar mActionBar;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mActionBar = getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //mActionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        
        Tab mainTab = mActionBar.newTab()
        		.setText("Articoli")
        		.setTabListener(new TabEventListener(new TestFragment("Articoli")));
        Tab favTab = mActionBar.newTab()
        		.setText("Preferiti")
        		.setTabListener(new TabEventListener(new TestFragment("Preferiti")));
        
        mActionBar.addTab(mainTab);
        mActionBar.addTab(favTab);
        		
    }
}