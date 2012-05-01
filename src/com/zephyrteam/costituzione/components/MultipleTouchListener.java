package com.zephyrteam.costituzione.components;

import java.util.ArrayList;
import java.util.List;

import com.zephyrteam.costituzione.Constants;
import com.zephyrteam.costituzione.DetailedActivity;
import com.zephyrteam.costituzione.R;
import com.zephyrteam.costituzione.SubListsActivity;
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

public class MultipleTouchListener implements MultiChoiceModeListener {
		ListView mList;
		EntriesAdapter mAdapter;
		List<Integer> selected;
		List<SingleEntry> mEntries;
		Context context;
		
		public MultipleTouchListener(EntriesAdapter adapter, ListView list, Context context) {
			mList = list;
			mAdapter = adapter;
			selected = new ArrayList<Integer>();
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
			
			mMenu.add(Menu.NONE, Constants.ACTIONMODE_FAVOURITE_ID, 1, res.getString(R.string.favorites))
				.setIcon(R.drawable.is_favorite_light);
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
					entry.setIsFavourite(true);
					dbh.updateFavoriteStatus(entry);
					list.remove(i);
					list.add(i, entry);
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
			
			selected.add(position);
			
		}
		
		public ItemClickListener getClickListener() {
			return new ItemClickListener(context, mAdapter.getList());
		}
		
		
		
		public class ItemClickListener implements AdapterView.OnItemClickListener {
			List<SingleEntry> list;
			Context context;
			SubListsActivity activity;
			
			public ItemClickListener(Context context, List<SingleEntry> list) {
				this.context = context;
				this.list = list;
			}

			@Override
		    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				Intent intent = new Intent(context, DetailedActivity.class);
				SingleEntry entry = list.get(position);
				intent.putExtra("id", entry.getId());
				context.startActivity(intent);
		    }
			
		}
    
}
