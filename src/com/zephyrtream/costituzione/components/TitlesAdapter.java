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

package com.zephyrtream.costituzione.components;

import java.util.List;

import com.zephyrtream.costituzione.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TitlesAdapter extends BaseAdapter {
	private Context context;
    private List<Object> entries;
    
    private final int TYPE_TITLE = 0;
    private final int TYPE_SEPARATOR = 1;
    private final int TYPE_MAXCOUNT = 2;
    
	public TitlesAdapter(Context context, List<Object> entries) {
		this.context = context;
		this.entries = entries;
	}
	
    
	@Override
	public int getCount() {
		return entries.size();
	}
	
	@Override
	public Object getItem(int position) {
		return entries.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
    public int getItemViewType(int position) {
		Object obj = entries.get(position);
		
		return ((obj instanceof SingleTitle) ? TYPE_TITLE : TYPE_SEPARATOR);
	}
	
	@Override
    public int getViewTypeCount() {
        return TYPE_MAXCOUNT;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Object entry = entries.get(position);
		boolean isCategory = (entry instanceof TitleCategory);
	
		if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(isCategory ? R.layout.list_category_divider : R.layout.list_items_simple, null);
        }
		
        if (isCategory) {
        	TitleCategory tc = (TitleCategory)entry;
        	convertView.setClickable(false);
        	
        	TextView tv = (TextView) convertView.findViewById(R.id.list_item_section_text);
        	if (tv != null) tv.setText(tc.getTitle());
        } else {
        	SingleTitle tc = (SingleTitle)entry;
        	
        	TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        	if (tv != null) 
        		tv.setText(tc.getTitle());
        }
        return convertView;
        
	}
	

	public List<Object> getList() {
		return entries;
	}
	
	public void setList(List<Object> newentries) {
		entries = newentries;
		notifyDataSetChanged();
	}
}
