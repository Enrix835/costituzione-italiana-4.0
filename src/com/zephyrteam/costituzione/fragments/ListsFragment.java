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

package com.zephyrteam.costituzione.fragments;

import com.zephyrteam.costituzione.R;
import com.zephyrteam.costituzione.SubListsActivity;
import com.zephyrteam.costituzione.components.Categories;
import com.zephyrteam.costituzione.components.TitlesAdapter;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView; 
import android.widget.Toast;

public class ListsFragment extends ListFragment {
		
	boolean mDualPane;
    int mCurCheckPosition = 0;
    
    public ListsFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        TitlesAdapter ta = new TitlesAdapter(getActivity(), Categories.getStructuredTitles(getActivity()));
        setListAdapter(ta);
        
        View detailsFrame = getActivity().findViewById(R.id.details);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            showDetails(mCurCheckPosition);
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	
    	int category = Categories.getCategoryFromPosition(position);
        showDetails(category);
    }

    void showDetails(int index) {
        mCurCheckPosition = index;

        if (mDualPane) {
            //getListView().setItemChecked(index, true);

           	SubListsFragment details = (SubListsFragment)
                    getFragmentManager().findFragmentById(R.id.details);
            if (details == null || details.getShownIndex() != index) {
                details = SubListsFragment.newInstance(index);
 
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), SubListsActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }

}
