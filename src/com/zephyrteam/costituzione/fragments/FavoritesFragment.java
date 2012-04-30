package com.zephyrteam.costituzione.fragments;

import com.zephyrteam.costituzione.util.LoadFavoritesTask;
import com.zephyrteam.costituzione.util.LoadUiTask;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;

public class FavoritesFragment extends ListFragment {
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("DBG", "Activity CREATED!");
        super.onActivityCreated(savedInstanceState);
       
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		LoadFavoritesTask task = new LoadFavoritesTask(getActivity(), this);
        task.execute();
	}
}
