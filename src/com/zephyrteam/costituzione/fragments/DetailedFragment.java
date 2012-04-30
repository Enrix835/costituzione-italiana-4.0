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
import com.zephyrteam.costituzione.components.SingleEntry;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailedFragment extends Fragment {
	SingleEntry mEntry;
	
	TextView mTitleView;
	TextView mBodyView;
	
	public DetailedFragment(SingleEntry entry) {
		mEntry = entry;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View main = inflater.inflate(R.layout.detailed_view, container, true);
        
        mTitleView = (TextView) main.findViewById(R.id.detailed_title_view);
        mBodyView = (TextView) main.findViewById(R.id.detailed_body_view);
        
        mTitleView.setText(mEntry.getTitle());
        mBodyView.setText(mEntry.getBody());
        
        return null;
    }
}
