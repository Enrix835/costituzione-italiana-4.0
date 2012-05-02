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
import com.zephyrteam.costituzione.fragments.SearchResultsFragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.ListView;

public class LoadSearchResultsTask extends AsyncTask<Object, Object, EntriesAdapter> {
	Activity activity;
	SearchResultsFragment fragment;
	
	public LoadSearchResultsTask(Activity activity, SearchResultsFragment fragment) {
		this.activity = activity;
		this.fragment = fragment;
	}
	
	@Override
	protected EntriesAdapter doInBackground(Object... params) {
		String keyword = fragment.getKeyword();
		int category = fragment.getCallingCategory();
		
		DatabaseHandler dbh = new DatabaseHandler(activity);
		dbh.open(false);
		Cursor res = null;
		
		if (Util.isOnlyNumeric(keyword)) {
			res = (category == -1) ? 
					dbh.getEntry(Integer.parseInt(keyword)) : dbh.getEntry(Integer.parseInt(keyword), category);
		
		} else if (Util.isOnlyRomanNumber(keyword)) {
			res = dbh.getEntry(Util.getCategoryRomanInt(keyword));
			
		}else if (keyword.contains("-")){
			String[] range = keyword.split("-");
			res = (category == -1) ? 
					dbh.getRangeEntries(range[0], range[1]) : dbh.getRangeEntries(range[0], range[1], category);
		}
		
		if (res == null) {
			res = (category == -1) ?
					dbh.getSimilarEntries(keyword) : dbh.getSimilarEntries(keyword, category);
		}
		
		List<SingleEntry> list = dbh.getEntriesFromCursor(res);
		res.close();
		dbh.close();
		
		return new EntriesAdapter(activity, list);
	}
	
	@Override
	protected void onPostExecute(EntriesAdapter result) {
		fragment.setListAdapter(result);
        
        final ListView mList = fragment.getListView();
        fragment.registerForContextMenu(mList);
        
        mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        MultipleTouchListener listener = new MultipleTouchListener(result, mList, activity);
        
        mList.setMultiChoiceModeListener(listener);
        
        mList.setOnItemClickListener(listener.getClickListener());
	}

}
