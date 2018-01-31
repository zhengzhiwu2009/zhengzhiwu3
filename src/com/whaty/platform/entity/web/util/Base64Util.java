package com.whaty.platform.entity.web.util;
import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;

public class Base64Util {

	// 将 s 进行 BASE64 编码
	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}

	// 将 BASE64 编码的字符串 s 进行解码
	public static String getFromBASE64(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(new String(((String)Base64Util.getFromBASE64("6K6i5Y2V5L+d5a2Y5aSx6LSlLG5lc3RlZCBleGNlcHRpb24gaXMgb3JnLmFwYWNoZS5pYmF0aXMuYmluZGluZy5CaW5kaW5nRXhjZXB0aW9uOiBQYXJhbWV0ZXIgJ2RkYmgnIG5vdCBmb3VuZC4gQXZhaWxhYmxlIHBhcmFtZXRlcnMgYXJlIFtsaXN0XQ==")).getBytes(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
}