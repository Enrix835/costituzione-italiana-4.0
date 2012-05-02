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

package com.zephyrteam.costituzione.components;

import java.util.List;

import com.viewpagerindicator.TitleProvider;
import com.zephyrteam.costituzione.fragments.DetailedFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DetailedFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
	SingleEntry[] mList;
	DetailedFragment[] mFragments;
	FragmentActivity activity;
	
	public DetailedFragmentAdapter(FragmentManager fm, List<SingleEntry> list, FragmentActivity activity) {
		super(fm);
		mList = new SingleEntry[list.size()];
		mList = list.toArray(mList);
		
		mFragments = new DetailedFragment[mList.length];
		for (int i = 0; i < mList.length; i++) {
			mFragments[i] = new DetailedFragment(mList[i]);
		}
	}

	@Override
	public Fragment getItem(int position) {
		return (Fragment)mFragments[position];
	}

	@Override
	public int getCount() {
		return mFragments.length;
	}

	@Override
	public String getTitle(int position) {
		return mList[position].getTitle();
	}
	
	

}
