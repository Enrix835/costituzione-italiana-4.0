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
import com.zephyrteam.costituzione.components.MultipleFavoriteTouchListener;
import com.zephyrteam.costituzione.components.SingleEntry;
import com.zephyrteam.costituzione.fragments.FavoritesFragment;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.ListView;

public class LoadFavoritesTask extends AsyncTask<Object, Object, EntriesAdapter> {
	Context context;
	FavoritesFragment fragment;
	
	public LoadFavoritesTask(Context context, FavoritesFragment fragment) {
		this.context = context;
		this.fragment = fragment;
	}
	
	@Override
	protected EntriesAdapter doInBackground(Object... params) {
		DatabaseHandler dbh = new DatabaseHandler(context);
		dbh.open(false);
		Cursor favCur = dbh.getFavorites();
		List<SingleEntry> list = dbh.getEntriesFromCursor(favCur);
		
		favCur.close();
		dbh.close();
		
		EntriesAdapter adapter = new EntriesAdapter(context, list);
		return adapter;
	}
	
	@Override
	protected void onPostExecute(EntriesAdapter adapter) {

        fragment.setListAdapter(adapter);
        
        final ListView mList = fragment.getListView();
        fragment.registerForContextMenu(mList);
        
        mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        MultipleFavoriteTouchListener listener = new MultipleFavoriteTouchListener(adapter, mList, context);
        
        mList.setMultiChoiceModeListener(listener);
        
        mList.setOnItemClickListener(listener.getClickListener());
	}

}
