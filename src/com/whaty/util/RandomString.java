package com.whaty.util;

import java.util.StringTokenizer;
import java.util.UUID;

public class RandomString {
	
	private static final String str_num ="0123456789";
	private static final String str_char ="abcdefghijklmnopqrstuvwxyz";
	private static final String str_Upperchar =str_char.toUpperCase();
	private static final String src_hex_lower = "0123456789abcdef";   
	
	@SuppressWarnings("unused")
	public static String getString(final int size){
		StringBuffer strbuffer = new StringBuffer(size);
		String str = str_Upperchar+str_num;
		for(int i=0;i<size;i++){
			strbuffer.append(getRandomchar(str));
		}
		return strbuffer.toString();
	}
	
	public static String getHexString(int size){
		StringBuffer strbuffer = new StringBuffer(size);
		for(int i=0;i<size;i++){
			strbuffer.append(getRandomchar(src_hex_lower));
		}
		return strbuffer.toString();
	}


	private static String getRandomchar(String str) {
		if(str.length()<1||str==""||str==null){
			return "";
		}
		return String.valueOf(str.charAt((int)(Math.random()*str.length())));
	}
	
	public static String generatePassStr() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 8; i++) {

			sb.append(rendomChar());
		}

		return sb.toString();
	}

	private static char rendomChar() {
		int n = (int) (Math.random() * 26);
		return (char) ('a' + n);
		
	}
	
}

