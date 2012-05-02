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
