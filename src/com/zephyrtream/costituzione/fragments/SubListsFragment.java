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

package com.zephyrtream.costituzione.fragments;

import java.util.ArrayList;
import java.util.List;

import com.zephyrtream.costituzione.Constants;
import com.zephyrtream.costituzione.DetailedActivity;
import com.zephyrtream.costituzione.R;
import com.zephyrtream.costituzione.components.EntriesAdapter;
import com.zephyrtream.costituzione.components.SingleEntry;

//import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;

public class SubListsFragment extends ListFragment {
	List<SingleEntry> list;
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        // We use a custom adapter that accept a List as argoument.
        list = new ArrayList<SingleEntry>();
        list.add(new SingleEntry("Title1", "Summary1", 1, 0, false));
        list.add(new SingleEntry("Title2", "Summary2", 1, 1, false));
        list.add(new SingleEntry("Title3", "Summary3", 1, 2, false));
        
        //This is a simple list that contains all the selected entries
        final List<Integer> selected = new ArrayList<Integer>();
        
        final EntriesAdapter mAdapter = new EntriesAdapter(getActivity(), list);
        setListAdapter(mAdapter);
        
        final ListView mList = getListView();
        registerForContextMenu(mList);
        
        mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mList.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				selected.clear();
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch(item.getItemId()){
				case Constants.ACTIONMODE_FAVOURITE_ID:
					//This just temporarily set new favorites. 
					//In the real code we should write the changes to the database.
					Integer [] array = new Integer[selected.size()];
					selected.toArray(array);
					for(int i : array){
						List<SingleEntry> list = mAdapter.getList();
						SingleEntry entry = list.get(i);
						entry.setIsFavourite(true);
						list.remove(i);
						list.add(i, entry);
						mAdapter.setList(list);
						//When we update the adapter we should update the database too.
					}
					mList.invalidateViews();
					mode.finish();
					break;
				}
				return true;
			}
			
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position,
					long id, boolean checked) {
				// TODO Auto-generated method stub
				
				//SingleEntry entry = (SingleEntry)mAdapter.getItem(position);
				selected.add(position);
				Menu mMenu = mode.getMenu();
				mMenu.clear();
				
				mMenu.add(Menu.NONE, Constants.ACTIONMODE_FAVOURITE_ID, 1, "")
					.setIcon(R.drawable.is_favourite);
			}
		});
        
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
		
		@Override
	    public void onListItemClick(ListView l, View v, int position, long id) {
			Intent intent = new Intent(getActivity(), DetailedActivity.class);
			SingleEntry entry = list.get(position);
			intent.putExtra("title", entry.getTitle());
			intent.putExtra("body", entry.getBody());
			intent.putExtra("id", entry.getId());
			intent.putExtra("category", entry.getCategory());
			intent.putExtra("favorite", entry.isFavourite());
			startActivity(intent);
	    }
	
}