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

package com.zephyrteam.costituzione.util;

import java.util.List;

import com.zephyrteam.costituzione.components.EntriesAdapter;
import com.zephyrteam.costituzione.components.MultipleTouchListener;
import com.zephyrteam.costituzione.components.SingleEntry;
import com.zephyrteam.costituzione.fragments.SubListsFragment;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.ListView;

public class LoadUiTask extends AsyncTask <SingleEntry, Integer, EntriesAdapter>{
	SubListsFragment mFragment;
	Context mContext;
	
	int mCategory;
	
	public LoadUiTask(Context context, SubListsFragment fragment, int category) {
		mFragment = fragment;
		mContext = context;
		mCategory = category;
	}
	

	@Override
	protected EntriesAdapter doInBackground(SingleEntry... arg0) {
		DatabaseHandler dbh = new DatabaseHandler(mContext);
		dbh.open(false);
		Cursor data = dbh.getCategory(mCategory);
		List<SingleEntry> entries = dbh.getEntriesFromCursor(data);
		
		data.close();
		dbh.close();
		
		final EntriesAdapter mAdapter = new EntriesAdapter(mContext, entries);
		
		return mAdapter;
	}
	
	@Override
	protected void onPostExecute(EntriesAdapter adapter) {

        mFragment.setListAdapter(adapter);
        
        final ListView mList = mFragment.getListView();
        mFragment.registerForContextMenu(mList);
        
        mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        MultipleTouchListener listener = new MultipleTouchListener(adapter, mList, mFragment.getActivity());
        
        mList.setMultiChoiceModeListener(listener);
        
        mList.setOnItemClickListener(listener.getClickListener());
	}

}
