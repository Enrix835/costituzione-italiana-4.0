package com.zephyrteam.costituzione.fragments;

import com.zephyrteam.costituzione.util.LoadFavoritesTask;

import android.app.ListFragment;
import android.os.Bundle;

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
