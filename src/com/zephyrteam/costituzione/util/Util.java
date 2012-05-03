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

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

public class Util {
	
	public static String getCategoryRomanNumber(int num) {
		switch(num) {
			case 140:
				return "I";
			case 141:
				return "II";
			case 142:
				return "III";
			case 143:
				return "IV";
			case 144:
				return "V";
			case 145:
				return "VI";
			case 146:
				return "VII";
			case 147:
				return "VIII";
			case 148:
				return "IX";
			case 149:
				return "X";
			case 150:
				return "XI";
			case 151:
				return "XII";
			case 152:
				return "XIII";
			case 153:
				return "XIV";
			case 154:
				return "XV";
			case 155:
				return "XVI";
			case 156:
				return "XVII";
			case 157:
				return "XVIII";
			case 158:
				return "XIX";
			case 159:
				return "XX";
			default:
				return null;
		}
	}
	
	public static int getCategoryRomanInt(String num) {
		if(num.equals("I"))
			return 140;
		else if(num.equals("II"))
			return 141;
		else if(num.equals("III"))
			return 142;
		else if(num.equals("IV"))
			return 143;
		else if(num.equals("V"))
			return 144;
		else if(num.equals("VI"))
			return 145;
		else if(num.equals("VII"))
			return 146;
		else if(num.equals("VIII"))
			return 147;
		else if(num.equals("IX"))
			return 148;
		else if(num.equals("X"))
			return 149;
		else if(num.equals("XI"))
			return 150;
		else if(num.equals("XII"))
			return 151;
		else if(num.equals("XIII"))
			return 152;
		else if(num.equals("XIV"))
			return 153;
		else if(num.equals("XV"))
			return 154;
		else if(num.equals("XVI"))
			return 155;
		else if(num.equals("XVII"))
			return 156;
		else if(num.equals("XVIII"))
			return 157;
		else if(num.equals("XIX"))
			return 158;
		else if(num.equals("XX"))
			return 159;
		else return 1;
	}
	
	public static boolean isOnlyNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (c < 48 || c > 57) return false;
		}
		return true;
	}
	
	public static boolean isOnlyRomanNumber(String str) {
		int x = getCategoryRomanInt(str);
		return (x > 139 && x < 158);
	}
	
	public static boolean isRange(String str) {
		if (!str.contains("-")) return false;
		
		String[] tmp = str.split("-");
		
		try {
			int min = Integer.parseInt(tmp[0]);
			int max = Integer.parseInt(tmp[1]);
			return (max >= min);
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isList(String str) {
		if (!str.contains(",")) return false;
		
		String [] tmp = str.replace(" ", "").split(",");
		
		for(String s : tmp) {
			if(!isOnlyNumeric(s)) return false;
		}
		return true;
	}
	
	public static boolean isRangeList(String str) {
		if (!str.contains(",") || !str.contains("-")) return false;
		String [] tmp = str.replace(" ,", ",").replace(", ", ",").split(",");
		
		for(String s : tmp) {
			if(!isOnlyNumeric(s) && !isRange(s)) return false;
		}
		return true;
	}
	
	public static int[] getListOfEntries(String list) {
		String [] tmp = list.replace(" ,", ",").replace(", ", ",").split(",");
		
		int[] ret = new int[tmp.length];
		
		for(int i = 0; i < tmp.length; i++) {
			ret[i] = Integer.parseInt(tmp[i]);
		}
		
		return ret;
	}
	
	public static int[] getRangeListOfEntries(String list) {
		List<Integer> ret = new ArrayList<Integer>();
		String [] tmp = list.replace(" ,", ",").replace(", ", ",").split(",");
		
		if (!list.contains("-")) return getListOfEntries(list);
		
		for (int i = 0; i < tmp.length; i++) {
			if (Util.isOnlyNumeric(tmp[i])) {
				ret.add(Integer.parseInt(tmp[i]));
			} else if (Util.isRange(tmp[i])) {
				String[] range = tmp[i].split("-");
				int min = Integer.parseInt(range[0]);
				int max = Integer.parseInt(range[1]);
				
				for (int x = min; x <= max; x++) {
					ret.add(x);
				}
			}
		}
		
		int []toRet = new int[ret.size()];
		
		for(int i = 0; i < toRet.length; i++) {
			toRet[i] = (int)ret.get(i);
		}
		
		return toRet;
	}
	
	public static boolean isTabletDevice(Activity activity) {
	    // Verifies if the Generalized Size of the device is XLARGE to be considered a Tablet
	    boolean xlarge = ((activity.getResources().getConfiguration().screenLayout & 
	                        Configuration.SCREENLAYOUT_SIZE_MASK) == 
	                        Configuration.SCREENLAYOUT_SIZE_XLARGE);

	    // If XLarge, checks if the Generalized Density is at least MDPI (160dpi)
	    if (!xlarge) return false;
	    		
	    DisplayMetrics metrics = new DisplayMetrics();
	    activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

	    // MDPI=160, DEFAULT=160, DENSITY_HIGH=240, DENSITY_MEDIUM=160,
	    // DENSITY_TV=213, DENSITY_XHIGH=320
	    if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
	    		|| metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
	            || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
	            || metrics.densityDpi == DisplayMetrics.DENSITY_TV
	            || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {

	            // Yes, this is a tablet!
	            return true;
	     } else return false;
	}
	
	
}
