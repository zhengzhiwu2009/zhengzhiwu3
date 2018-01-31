/**
 * MD5�����㷨
 */
package com.whaty.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;

/**
 * <p>Title: MD5�����㷨</p>
 * <p>Description: �̻�����Ҫ�����޸�</p>
 * <p>���������Ƽ���չ��˾ 2009. All rights reserved.</p>
 */
public class Crypto {

	/**
	 * ������ϢժҪ
	 * ���������ȣ�SHA-1 40byte, MD5 32byte, SHA-256 64byte
	 * @param strSrc ����ժҪ��Դ�ַ���
	 * @param encName ժҪ�㷨: "SHA-1"  "MD5"  "SHA-256", Ĭ��Ϊ"SHA-1"
	 * @return ��ȷ����ժҪֵ,���󷵻�null
	 */
	public static String GetMessageDigest(String strSrc, String encName) {
		MessageDigest md = null;
		String strDes = null;
		final String ALGO_DEFAULT = "SHA-1";
		//final String ALGO_MD5 = "MD5";
		//final String ALGO_SHA256 = "SHA-256";

		byte[] bt = strSrc.getBytes();
		try {
			if (StringUtils.isEmpty(encName)) {
				encName = ALGO_DEFAULT;
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); //to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}
	/**
	 * ���ܣ�MD5����
	 * @param strSrc ���ܵ�Դ�ַ���
	 * @return ���ܴ� ����32λ
	 */
	public static String GetMessageDigest(String strSrc) {
		MessageDigest md = null;
		String strDes = null;
		final String ALGO_MD5 = "MD5";

		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance(ALGO_MD5);
			md.update(bt);
			strDes = bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(
				"ϵͳ��֧�ֵ�MD5�㷨��");
		}
		return strDes;
	}

	/**
	 * ���ֽ�����תΪHEX�ַ���(16���ƴ�)
	 * @param bts Ҫת�����ֽ�����
	 * @return ת�����HEX��
	 */
	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

}
