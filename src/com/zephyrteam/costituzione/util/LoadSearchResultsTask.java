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
