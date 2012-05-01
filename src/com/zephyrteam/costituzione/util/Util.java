package com.zephyrteam.costituzione.util;

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
	
	public static boolean isOnlyNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (c < 48 || c > 57) return false;
		}
		return true;
	}
	
}
