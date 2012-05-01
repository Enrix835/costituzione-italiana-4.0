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

package com.zephyrteam.costituzione;

import java.util.List;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.viewpagerindicator.TitlePageIndicator;
import com.zephyrteam.costituzione.components.DetailedFragmentAdapter;
import com.zephyrteam.costituzione.components.SingleEntry;
import com.zephyrteam.costituzione.util.DatabaseHandler;

public class DetailedActivity extends BasicActivity implements OnPageChangeListener {
	SingleEntry mEntry;
	ViewPager vp;
	ShareActionProvider mShareProvider;
	int id, position;
	List<SingleEntry> list;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_container);
        vp = (ViewPager) findViewById(R.id.pager);
        
        if (savedInstanceState == null) {
        	Bundle data = getIntent().getExtras();
        	
        	id = data.getInt("id");
        	String idlist = data.getString("idlist");
        	
        	String[] ids = idlist.split("-");
    		int[] nids = new int[ids.length];
    		
    		for (int i = 0; i < ids.length; i++) {
    			nids[i] = Integer.parseInt(ids[i]);
    			if (nids[i] == id) position = i;
    		}
    		
    		DatabaseHandler dbh = new DatabaseHandler(this);
    		dbh.open(false);
    		list = dbh.getListOfEntries(nids);
    		dbh.close();
    		
    		DetailedFragmentAdapter fa = new DetailedFragmentAdapter(getSupportFragmentManager(), list, this);
    		vp.setAdapter(fa);
    		
    		TitlePageIndicator tpi = (TitlePageIndicator)findViewById(R.id.indicator);
    		tpi.setViewPager(vp);
    		
    		vp.setCurrentItem(position, true);
    		tpi.setOnPageChangeListener(this);
    		
        }
    } 
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.actionbar_details, menu);
    	
    	mEntry = list.get(vp.getCurrentItem());
    	updateFavoriteButton(menu.findItem(R.id.favourite_status));
    	
    	MenuItem shareItem = menu.findItem(R.id.menu_item_share);
    	mShareProvider = (ShareActionProvider) shareItem.getActionProvider();
    	mShareProvider.setShareIntent(getShareIntent());
    	return true;
    }
    
    @SuppressWarnings("static-access")
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.copy_entry:
				String toCopy = mEntry.getTitle() + ": \n" + mEntry.getBody().replace("\n\n", "\n");
				
				ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
				clipboard.setPrimaryClip(ClipData.newPlainText("Costituzione", toCopy));
				new Toast(this).makeText(this, R.string.text_copied_to_clipboard, 2000).show();
				
				break;
				
			case R.id.favourite_status:
				DatabaseHandler dbh = new DatabaseHandler(this);
				dbh.open(true);
				mEntry.setIsFavourite(!mEntry.isFavourite());
				dbh.updateFavoriteStatus(mEntry);
				dbh.close();
				
				new Toast(this).makeText(this, mEntry.isFavourite() ? R.string.added_to_fav : R.string.removed_from_fav, 2000).show();
				updateFavoriteButton(item);
		}
		return true;
	}
    
    
    public Intent getShareIntent() {
    	String text = mEntry.getTitle() + ": " + mEntry.getBody();
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/*");
		shareIntent.putExtra(Intent.EXTRA_TEXT, text);
		return shareIntent;
    }
    
    public void updateFavoriteButton(MenuItem favorite) {
    	boolean isFav = mEntry.isFavourite();
    	
    	int icon = isFav ? R.drawable.is_favorite_light : R.drawable.is_not_favorite_light;
    	int title = isFav ? R.string.remove_from_fav : R.string.add_to_fav;
    	
    	favorite.setIcon(icon).setTitle(title);

    }

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		invalidateOptionsMenu();
	}
    
}
