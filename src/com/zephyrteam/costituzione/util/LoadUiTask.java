package com.zephyrteam.costituzione.util;

import java.util.List;

import com.zephyrteam.costituzione.R;
import com.zephyrteam.costituzione.components.EntriesAdapter;
import com.zephyrteam.costituzione.components.MultipleTouchListener;
import com.zephyrteam.costituzione.components.SingleEntry;
import com.zephyrteam.costituzione.fragments.SubListsFragment;

import android.app.ListFragment;
import android.app.ProgressDialog;
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
		Cursor data = dbh.getDataCursor(mCategory);
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
        MultipleTouchListener listener = new MultipleTouchListener(adapter, mList, mContext);
        
        mList.setMultiChoiceModeListener(listener);
        
        mList.setOnItemClickListener(listener.getClickListener());
	}

}
