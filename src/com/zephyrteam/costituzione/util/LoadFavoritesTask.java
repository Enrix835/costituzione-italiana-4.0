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
