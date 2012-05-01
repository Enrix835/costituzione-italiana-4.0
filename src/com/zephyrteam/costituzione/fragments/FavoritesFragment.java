package com.zephyrteam.costituzione.fragments;

import com.zephyrteam.costituzione.util.LoadFavoritesTask;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class FavoritesFragment extends ListFragment {
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		LoadFavoritesTask task = new LoadFavoritesTask(getActivity(), this);
        task.execute();
	}
}
