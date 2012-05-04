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

import java.util.List;

import com.zephyrteam.costituzione.Constants;
import com.zephyrteam.costituzione.R;
import com.zephyrteam.costituzione.components.EntriesAdapter;
import com.zephyrteam.costituzione.components.SingleEntry;
import com.zephyrteam.costituzione.util.LoadSearchResultsTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class SearchResultsFragment extends Fragment {
	public List<SingleEntry> list;
	public TextView pSearchResults;
	public ListView pListView;
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       
        LoadSearchResultsTask task = new LoadSearchResultsTask(getActivity(), this);
        task.execute();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View main = inflater.inflate(R.layout.search_results_view, null, true);
		
		if (main != null) {
			pListView = (ListView)main.findViewById(android.R.id.list);
			pSearchResults = (TextView)main.findViewById(R.id.search_results_text);
		}
		
		return main;
	}
	
	 public static SearchResultsFragment newInstance(String keyword) {
		 return newInstance(keyword, -1);
	 }
	
	 public static SearchResultsFragment newInstance(String keyword, int category) {
	     	SearchResultsFragment f = new SearchResultsFragment();
	        Bundle args = new Bundle();
	        args.putString(Constants.EXTRA_KEYWORD, keyword);
	        args.putInt(Constants.EXTRA_CATEGORY, category);
	        f.setArguments(args);
	        return f;
	   }

	   public String getKeyword() {
	        return getArguments().getString(Constants.EXTRA_KEYWORD);
	   }
	   
	   public int getCallingCategory() {
		   return getArguments().getInt(Constants.EXTRA_CATEGORY, -1);
	   }
	    
	   public void setAdapter(EntriesAdapter adapter) {
		   if (pListView != null) {
			   pListView.setAdapter(adapter);
		   }
	   }
	   
	   public void setResultsText(String keyword) {
		   if (pSearchResults != null) {
			   int num = (list == null) ? 0 : list.size();
			   
			   if (num == 0) {
				   pSearchResults.setText(getActivity().getResources().getString(R.string.search_results_zero, keyword));
			   } else if (num == 1) {
				   pSearchResults.setText(getActivity().getResources().getQuantityString(R.plurals.search_results, num, keyword));
			   } else if (num >= 2) {
				   pSearchResults.setText(getActivity().getResources().getQuantityString(R.plurals.search_results, num, num, keyword));
			   }
			   
			   pSearchResults.setVisibility(View.VISIBLE);
		   }
	   }
	
}