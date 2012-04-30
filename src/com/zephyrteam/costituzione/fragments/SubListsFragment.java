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

package com.zephyrteam.costituzione.fragments;

import java.util.ArrayList;
import java.util.List;

import com.zephyrteam.costituzione.DetailedActivity;
import com.zephyrteam.costituzione.components.EntriesAdapter;
import com.zephyrteam.costituzione.components.SingleEntry;
import com.zephyrteam.costituzione.util.LoadUiTask;

//import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class SubListsFragment extends ListFragment {
	List<SingleEntry> list;
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		LoadUiTask task = new LoadUiTask(getActivity(), this, getShownIndex());
        task.execute();
	}
	
	
	 public static SubListsFragment newInstance(int index) {
	     	SubListsFragment f = new SubListsFragment();
	        Bundle args = new Bundle();
	        args.putInt("index", index);
	        f.setArguments(args);
	        return f;
	    }

	    public int getShownIndex() {
	        return getArguments().getInt("index", 0);
	    }
	    
		public void setMyAdapter(EntriesAdapter adapter) {
			this.setListAdapter(adapter);
		}
	
}