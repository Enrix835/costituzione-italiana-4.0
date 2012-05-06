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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zephyrteam.costituzione.Constants;
import com.zephyrteam.costituzione.DetailedActivity;
import com.zephyrteam.costituzione.R;
import com.zephyrteam.costituzione.util.DatabaseHandler;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MultipleFavoriteTouchListener implements MultiChoiceModeListener {
		ListView mList;
		EntriesAdapter mAdapter;
		Set<Integer> selected;
		List<SingleEntry> mEntries;
		Context context;
		
		public MultipleFavoriteTouchListener(EntriesAdapter adapter, ListView list, Context context) {
			mList = list;
			mAdapter = adapter;
			selected = new HashSet<Integer>();
			this.context = context;
			mEntries = adapter.getList();
		}
		
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			selected.clear();
		}
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			Menu mMenu = mode.getMenu();
			mMenu.clear();
			Resources res = context.getResources();
			
			mMenu.add(Menu.NONE, Constants.ACTIONMODE_FAVOURITE_ID, 1, res.getString(R.string.remove_from_fav))
				.setIcon(R.drawable.is_not_favorite_light);
			mMenu.add(Menu.NONE, Constants.ACTIONMODE_COPY_ID, 0, res.getString(android.R.string.copy))
				.setIcon(R.drawable.content_copy);
			return true;
		}
		
		@SuppressWarnings("static-access")
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			Integer [] array = new Integer[selected.size()];
			selected.toArray(array);
			List<SingleEntry> list = mAdapter.getList();
			DatabaseHandler dbh;
			
			switch(item.getItemId()){
			case Constants.ACTIONMODE_FAVOURITE_ID:
				dbh = new DatabaseHandler(context);
				dbh.open(true);
				
				for(int i : array){
					SingleEntry entry = list.get(i);
					entry.setIsFavourite(false);
					dbh.updateFavoriteStatus(entry);
					list.remove(i);
					mAdapter.setList(list);
					//When we update the adapter we should update the database too.
				}
				mList.invalidateViews();
				mode.finish();
				dbh.close();
				break;
				
			case Constants.ACTIONMODE_COPY_ID:
				String toCopy = "";
				for (int i : array) {
					SingleEntry entry = list.get(i);
					toCopy += entry.getTitle() + ":\n" + entry.getBody().replace("\n\n", "\n") + "\n\n";
				}
				ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboard.setPrimaryClip(ClipData.newPlainText("Costituzione", toCopy));
				
				new Toast(context).makeText(context, R.string.text_copied_to_clipboard, 2000).show();
				break;
			}
			return true;
		}
		
		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {
			if (checked) 
				selected.add(position);
			else 
				selected.remove(position);
		}
		
		public ItemClickListener getClickListener() {
			return new ItemClickListener(context, mAdapter.getList());
		}
		
		
		
		public class ItemClickListener implements AdapterView.OnItemClickListener {
			List<SingleEntry> list;
			Context context;
			
			public ItemClickListener(Context context, List<SingleEntry> list) {
				this.context = context;
				this.list = list;
			}

			@Override
		    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				Intent intent = new Intent(context, DetailedActivity.class);
				SingleEntry entry = list.get(position);
				intent.putExtra("id", entry.getId());
				intent.putExtra("idlist", getEntriesListString());
				context.startActivity(intent);
		    }
			
			public String getEntriesListString() {
				SingleEntry[] entries = new SingleEntry[list.size()];
				String ret = "-";
				
				entries = list.toArray(entries);
				
				for(SingleEntry entry : entries) {
					ret += "-" + entry.getId();
				}
				
				return ret.replace("--", "");
			}
			
		}
    
}
