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

public final class Constants {
	public final static int ACTIONMODE_COPY_ID = 0x32;
	public final static int ACTIONMODE_FAVOURITE_ID = 0x33;
	public final static int ACTIONMODE_NOT_FAVOURITE_ID = 0x34;
	
	public final static int DB_VERSION = 1;
	public final static String DB_NAME = "costituzione.db";
	public final static String DB_TABLE = "articoli";
	public final static boolean DB_SHOULD_UPDATE = false;
	
	public final static String PREF_KEY = "costituzione";
	public final static String PREF_DB_UPDATED = "db-updated-";
	
	public final static int DB_COLUMN_ID = 0;
	public final static int DB_COLUMN_BODY = 1;
	public final static int DB_COLUMN_CATEGORY = 2;
	public final static int DB_COLUMN_FAVORITE = 3;
	
	public static final String EXTRA_KEYWORD = "keyword";
	public static final String EXTRA_CATEGORY = "category";
	public static final String EXTRA_INDEX = "index";
}
