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

public class SingleEntry {
	private String mTitle;
	private String mBody;
	private int mId;
	private int mCategory;
	private boolean mIsFavourite;
	
	public SingleEntry(String title, String body, int category, int id, boolean favourite) {
		mTitle = title;
		mBody = body;
		mCategory = category;
		mId = id;
		mIsFavourite = favourite;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public String getBody() {
		return mBody;
	}
	
	public int getId() {
		return mId;
	}
	
	public int getCategory() {
		return mCategory;
	}
	
	public boolean isFavourite() {
		return mIsFavourite;
	}
	
	public void setIsFavourite(boolean isFavourite) {
		mIsFavourite = isFavourite;
	}
}
