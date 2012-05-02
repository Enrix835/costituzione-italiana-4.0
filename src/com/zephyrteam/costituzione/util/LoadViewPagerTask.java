package com.zephyrteam.costituzione.util;

import java.util.List;

import com.viewpagerindicator.TitlePageIndicator;
import com.zephyrteam.costituzione.DetailedActivity;
import com.zephyrteam.costituzione.R;
import com.zephyrteam.costituzione.components.DetailedFragmentAdapter;
import com.zephyrteam.costituzione.components.SingleEntry;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;

public class LoadViewPagerTask extends AsyncTask<Object, Object, List<SingleEntry>> {
	int currId;
	String idList;
	DetailedActivity activity;
	int position = 0;
	ViewPager vp;
	
	public LoadViewPagerTask(DetailedActivity activity, int id, String idlist, ViewPager vp) {
		this.activity = activity;
		currId = id;
		idList = idlist;
		this.vp = vp;
	}
	
	@Override
	protected List<SingleEntry> doInBackground(Object... params) {
		String[] ids = idList.split("-");
		int[] nids = new int[ids.length];
		
		for (int i = 0; i < ids.length; i++) {
			nids[i] = Integer.parseInt(ids[i]);
			if (nids[i] == currId) position = i;
		}
		
		activity.position = position;
		
		DatabaseHandler dbh = new DatabaseHandler(activity);
		dbh.open(false);
		List<SingleEntry> list = dbh.getListOfEntries(nids);
		dbh.close();
		
		return list;
	}
	
	public void onPostExecute(List<SingleEntry> result) {
		activity.list = result;
		
		DetailedFragmentAdapter fa = new DetailedFragmentAdapter(activity.getSupportFragmentManager(), result, activity);
		vp.setAdapter(fa);
		
		TitlePageIndicator tpi = (TitlePageIndicator)activity.findViewById(R.id.indicator);
		tpi.setViewPager(vp);
		
		vp.setCurrentItem(position, true);
		
		tpi.setOnPageChangeListener(activity);
		
		activity.invalidateOptionsMenu();
	}

}
