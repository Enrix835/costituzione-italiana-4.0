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

import com.zephyrteam.costituzione.components.EntriesAdapter;
import com.zephyrteam.costituzione.components.MultipleTouchListener;
import com.zephyrteam.costituzione.fragments.SearchResultsFragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

public class LoadSearchResultsTask extends AsyncTask<Object, Object, EntriesAdapter> {
	Activity activity;
	SearchResultsFragment fragment;
	String keyword;
	
	public LoadSearchResultsTask(Activity activity, SearchResultsFragment fragment) {
		this.activity = activity;
		this.fragment = fragment;
	}
	
	@Override
	protected EntriesAdapter doInBackground(Object... params) {
		keyword = fragment.getKeyword();
		int category = fragment.getCallingCategory();
		
		fragment.list = null;
		
		DatabaseHandler dbh = new DatabaseHandler(activity);
		dbh.open(false);
		Cursor res = null;
		
		if (Util.isOnlyNumeric(keyword)) {
			res = (category == -1) ? 
					dbh.getEntry(Integer.parseInt(keyword)) : dbh.getEntry(Integer.parseInt(keyword), category);
		
		} else if (Util.isOnlyRomanNumber(keyword)) {
			res = dbh.getEntry(Util.getCategoryRomanInt(keyword));
			
		} else if (Util.isRange(keyword)){
			res = (category == -1) ? 
					dbh.getEntries(Util.getRange(keyword)) : dbh.getEntries(Util.getRange(keyword), category);
		
		} else if (Util.isList(keyword)) {
			res = (category == -1) ?
					dbh.getEntries(Util.getListOfEntries(keyword)) : dbh.getEntries(Util.getListOfEntries(keyword), category);		
		
		} else if (Util.isRangeList(keyword)) {
			res = (category == -1) ?
					dbh.getEntries(Util.getRangeListOfEntries(keyword)) : dbh.getEntries(Util.getRangeListOfEntries(keyword), category);		
		}
		
		if (res == null) {
			res = (category == -1) ?
					dbh.getSimilarEntries(keyword) : dbh.getSimilarEntries(keyword, category);
		}
		
		fragment.list = dbh.getEntriesFromCursor(res);
		
		res.close();
		dbh.close();
		
		return new EntriesAdapter(activity, fragment.list);
	}
	
	@Override
	protected void onPostExecute(EntriesAdapter result) {
        fragment.setResultsText(keyword);
        fragment.pListView.setVisibility(View.VISIBLE);
        
		fragment.setAdapter(result);
		
        fragment.registerForContextMenu(fragment.pListView);
        fragment.pListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        MultipleTouchListener listener = new MultipleTouchListener(result, fragment.pListView, activity);
        fragment.pListView.setMultiChoiceModeListener(listener);
        fragment.pListView.setOnItemClickListener(listener.getClickListener());
	}

}
