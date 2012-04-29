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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.zephyrteam.costituzione.R;

public class Categories {
	private static int[] CATEGORY_TITLES_IDS = { 
			R.string.categories_0,
			R.string.categories_1,
			R.string.categories_2,
			R.string.categories_3,
			R.string.categories_4,
			R.string.categories_5,
			R.string.categories_6,
			R.string.categories_7,
			R.string.categories_8,
			R.string.categories_9,
			R.string.categories_10,
			R.string.categories_11,
	};
	
	private static int[] PARTS_TITLES_IDS = {
		R.string.part_0,
		R.string.part_1,
		R.string.part_2,
		R.string.part_3
	};
	
	private static int[][] CATEGORIES_INTERVALS = {
			{1, 12},
			{13, 28},
			{29, 34},
			{35, 47},
			{48, 54},
			{55, 82},
			{83, 91},
			{92, 100},
			{101, 113},
			{114, 133},
			{134, 139},
			{140, 157}
	};
	
	public static int getCategoryTitleId(int category) {
		return CATEGORY_TITLES_IDS[category];
	}
	
	public static int[] getCategoryTitlesIds (){
		return CATEGORY_TITLES_IDS;
	}
	
	public static int[] getPartsTitlesIds() {
		return PARTS_TITLES_IDS;
	}
	
	public static String[] getCategoriesTitles(Context context) {
		return context.getResources().getStringArray(R.array.categories);
	}
	
	public static String[] getPartsTitles(Context context) {
		return context.getResources().getStringArray(R.array.parts);
	}
	
	public static List<Object> getStructuredTitles(Context context) {
		List<Object> list = new ArrayList<Object>();
		String[] mTitles = getCategoriesTitles(context);
		String[] mParts = getPartsTitles(context);
		
		list.add(new TitleCategory(mParts[0]));
		list.add(new SingleTitle(mTitles[0], CATEGORIES_INTERVALS[0]));
		list.add(new TitleCategory(mParts[1]));
		list.add(new SingleTitle(mTitles[1], CATEGORIES_INTERVALS[1]));
		list.add(new SingleTitle(mTitles[2], CATEGORIES_INTERVALS[2]));
		list.add(new SingleTitle(mTitles[3], CATEGORIES_INTERVALS[3]));
		list.add(new SingleTitle(mTitles[4], CATEGORIES_INTERVALS[4]));
		list.add(new TitleCategory(mParts[2]));
		list.add(new SingleTitle(mTitles[5], CATEGORIES_INTERVALS[5]));
		list.add(new SingleTitle(mTitles[6], CATEGORIES_INTERVALS[6]));
		list.add(new SingleTitle(mTitles[7], CATEGORIES_INTERVALS[7]));
		list.add(new SingleTitle(mTitles[8], CATEGORIES_INTERVALS[8]));
		list.add(new SingleTitle(mTitles[9], CATEGORIES_INTERVALS[9]));
		list.add(new SingleTitle(mTitles[10], CATEGORIES_INTERVALS[10]));
		list.add(new TitleCategory(mParts[3]));
		list.add(new SingleTitle(mTitles[11], CATEGORIES_INTERVALS[11]));
		
		return list;
	}
}
