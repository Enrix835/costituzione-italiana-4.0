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
