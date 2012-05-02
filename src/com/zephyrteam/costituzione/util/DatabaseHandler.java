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

package com.zephyrteam.costituzione.util;

import java.util.ArrayList;
import java.util.List;

import com.zephyrteam.costituzione.Constants;
import com.zephyrteam.costituzione.components.SingleEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.provider.BaseColumns;
import android.util.Log;

public class DatabaseHandler {
	SQLiteDatabase db;
    DatabaseHelper mDatabaseHelper;
    Context context;
    
    public DatabaseHandler(Context context) {
   	 	this.context = context;
   	 	
        mDatabaseHelper = new DatabaseHelper(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }
    
    public void open(boolean write) {  
   	 	db = write ? mDatabaseHelper.getWritableDatabase() : mDatabaseHelper.getReadableDatabase();
   	 
   	 	if (db == null) {
   	 		Log.i("DBG", "db == null");
   	 		db = context.openOrCreateDatabase(Constants.DB_NAME, write ? SQLiteDatabase.OPEN_READWRITE : SQLiteDatabase.OPEN_READONLY, null);
   	 	}
    }
    

    public void close() { 
    	db.close();
    }
    
    public SQLiteDatabase getDatabase() {
    	return db;
    }
    
    public Cursor getDataCursor() {
        return db.query(Constants.DB_TABLE, CustomTable.COLUMNS, null, null, null, null, null);
    }
    
    
    public Cursor getCategory(int category) {
    	String SELECTION = CustomTable.CATEGORY + "=" + category;
    	return db.query(Constants.DB_TABLE, CustomTable.COLUMNS, SELECTION, null, null, null, null);
    }
    
    public Cursor getEntry(int id) {
    	String SELECTION = CustomTable._ID + "=" + id;
    	return db.query(Constants.DB_TABLE, CustomTable.COLUMNS, SELECTION, null, null, null, null);
    }
    
    public Cursor getFavorites() {
    	String SELECTION = CustomTable.FAVORITE + "=1";
    	return db.query(Constants.DB_TABLE, CustomTable.COLUMNS, SELECTION, null, null, null, null);
    }
    
    public Cursor getSimilarEntries(String keyword) {
    	String selection = CustomTable.BODY + " LIKE ?";
    	String [] selectionArgs = { "%" + keyword + "%"};
    	
    	return db.query(Constants.DB_TABLE, CustomTable.COLUMNS, selection, selectionArgs, null, null, null);
    }
    
    public Cursor getRangeEntries(String from, String to) {
    	String selection = CustomTable._ID + " BETWEEN ? AND ?";
    	String[] selectionArgs = { from, to };
    	
    	
    	return db.query(Constants.DB_TABLE, CustomTable.COLUMNS, selection, selectionArgs, null, null, null);
    }
    
    public Cursor getSimilarEntries(String keyword, int category) {
    	String selection = CustomTable.BODY + " LIKE ? AND " + CustomTable.CATEGORY + "=?";
    	String [] selectionArgs = { "%" + keyword + "%", ""+category };
    	
    	return db.query(Constants.DB_TABLE, CustomTable.COLUMNS, selection, selectionArgs, null, null, null);
    }
    
    public Cursor getRangeEntries(String from, String to, int category) {
    	String selection = CustomTable.CATEGORY + "=? AND " + CustomTable._ID + " BETWEEN ? AND ?";
    	String[] selectionArgs = { ""+category, from, to };
    	
    	return db.query(Constants.DB_TABLE, CustomTable.COLUMNS, selection, selectionArgs, null, null, null);
    }
    
    public Cursor getEntry(int id, int category) {
    	String selection = CustomTable.CATEGORY + "=? AND " + CustomTable._ID + "=?";
    	String[] selectionArgs = { ""+category, ""+id };
    	
    	return db.query(Constants.DB_TABLE, CustomTable.COLUMNS, selection, selectionArgs, null, null, null);
    }
    
    public List<SingleEntry> getListOfEntries(int[] ids) {
    	List<SingleEntry> mList = new ArrayList<SingleEntry>();
    	for (int id : ids) {
    		mList.add(getEntriesFromCursor(getEntry(id)).get(0));
    	}
    	
    	return mList;
    }
    
    public List<SingleEntry> getEntriesFromCursor(Cursor cur) {
    	List<SingleEntry> list = new ArrayList<SingleEntry>();
    	
    	cur.moveToFirst();
    	while(!cur.isAfterLast()) {
    		SingleEntry entry;
    		
    		int id = cur.getInt(Constants.DB_COLUMN_ID);
    		int category = cur.getInt(Constants.DB_COLUMN_CATEGORY);
    		boolean favorite = cur.getInt(Constants.DB_COLUMN_FAVORITE) == 1;
    		String text = cur.getString(Constants.DB_COLUMN_BODY);
    		String title = SingleEntry.getTitleFromId(context, id);
    		entry = new SingleEntry(title, text, id, category, favorite);
    		
    		list.add(entry);
    		cur.moveToNext();
    	}
    	
    	return list;
    }
    
    public void updateFavoriteStatus(SingleEntry entry) {
    	ContentValues cv = new ContentValues();
    	cv.put(CustomTable.FAVORITE, entry.isFavourite() ? 1 : 0);
    	
    	String SELECTION = CustomTable._ID + "=" + entry.getId();
    	db.update(Constants.DB_TABLE, cv, SELECTION, null);
    }
    
    public SingleEntry getSingleEntry(int id) {
    	Cursor cur = getEntry(id);
    	cur.moveToFirst();
    	
    	int category = cur.getInt(Constants.DB_COLUMN_CATEGORY);
		boolean favorite = cur.getInt(Constants.DB_COLUMN_FAVORITE) == 1;
		String text = cur.getString(Constants.DB_COLUMN_BODY);
		String title = SingleEntry.getTitleFromId(context, id);
		
		return new SingleEntry(title, text, id, category, favorite);
    }
    

    private class DatabaseHelper extends SQLiteOpenHelper {
   	 	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
                super(context, name, null, version);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        	//NOTHING ATM
        }

		@Override
		public void onCreate(SQLiteDatabase arg0) {
			//NOTHING ATM
		}
    }
}

interface CustomTable extends BaseColumns {
    String BODY = "testo";
    String CATEGORY = "categoria";
    String FAVORITE = "preferito";
    String[] COLUMNS = new String[] { 
            _ID,
            BODY,
            CATEGORY,
            FAVORITE
    };
}
