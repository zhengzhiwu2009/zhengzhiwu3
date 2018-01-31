package com.whaty.platform.entity.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.quartz.QuartzService;
import com.whaty.util.SpringUtil;

public class MyUtil {
	/**
	 * 获取UUID的值
	 *@param 
	 *@author huguolong
	 * @return 返回不带中划线-表示的32位UUID的值
	 */
	public static String getUUID() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取UUID的值
	 *@param 
	 *@author huguolong
	 * @return 返回不带中划线-表示的32位UUID的值(字母大写)
	 */
	public static String getUpperUUID() {
		return java.util.UUID.randomUUID().toString().replace("-", "")
				.toUpperCase();
	}
	/**
	 * 获取当前时间
	 *@param 
	 *@author huguolong
	 * @return 当前时间
	 */
	public static Date getCurrentDate() {
		try {
			return new SimpleDateFormat().parse(new SimpleDateFormat(
					"yyyy-MM-dd").format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 32位md5加密方法
	 * @param str	要加密的字符串
	 * @author huguolong
	 * @return	加密后的32位字符串
	 */
	public static String md5(String str) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int length = b.length;
			int i;
			// BASE64Encoder
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取指定长度的随机数字
	 * @param length 要获取的随机数字的长度
	 * @author huguolong 
	 * @return 指定长度的随机数字
	 */
	public static String getRandomNum(int length){
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < length; i++){
			buf.append((int)(Math.random()*10));
		}
		return buf.toString();
	}
	/**
	 * 32位md5加密方法
	 * @param str
	 * @return
	 */
    /*public static String encodeMd5(String str) {
        MessageDigest md = null;
        String result = "";
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] bytes = md.digest();
            char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            int l = bytes.length;
            char[] out = new char[l << 1];
            for (int i = 0, j = 0; i < l; i++) {
                out[j++] = DIGITS[(0xF0 & bytes[i]) >>> 4];
                out[j++] = DIGITS[0x0F & bytes[i]];
            }
            result = new String(out);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }

        return result;
    }*/
	

	/**
	 * 将登录表中用户密码修改为MD5加密类型
	 * @param  
	 * @author huguolong 
	 * @return 
	 */
	public static void password2md5(){
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,password from sso_user ";
		rs = db.executeQuery(sql);
		Connection conn = null;
		try {
			conn = db.getConn();
			conn.setAutoCommit(false);
			while(rs != null && rs.next()){
				String id = rs.getString("id");
				String password = rs.getString("password");
				sql = "update sso_user set password_md5='"+md5(password)+"' where id='"+id+"'";
				db.executeUpdate(sql);
			}
			conn.commit();
			db.close(rs);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error code: " + e + " ");
			}
		}
	}

	/**
	 * 判断输入字符串是否为邮箱地址
	 * @param str 要进行判断的字符串
	 * @author huguolong 
	 * @return true or false
	 */
	public static boolean isEmail(String str){
		String regex = "^.+@.+\\..+$";
		return Pattern.compile(regex).matcher(str).matches();
	}
	
	/**
	 * 字符串截取，英文占一个字符，中文占两个字符长度来算
	 * @param str 要截取的字符串
	 * @param length 截取的长度
	 * @return 截取后的字符串，中文占两个字符长度，英文占一个字符长度
	 */
	public static String subStr(String str , int length){
		StringBuffer result = new StringBuffer();
		int curLength = 0;
		if(null == str || "".equals(str)){
			return "";
		}else{
			char[] c = str.toCharArray();	//转换成字符数据
			int cLength = c.length;	//字符数组长度
			for(int i = 0; i < cLength; i++){
				curLength += (c[i] > 127) ? 2 : 1;
				if(curLength > length){
					break;
				}else{
					result.append(c[i]);
				}
			}
			return result.toString();
		}
	}
	
	/**
	 * 获得spring中注入的类
	 * @throws PlatformException 
	 * @throws NoRightException 
	 */
	
	public static String doLog (String executeDetail,String userId,String modeType, String loginPost, String writeValue, String ipAdress ,String url) throws NoRightException, PlatformException{
		String result ="";
		GeneralService  generalService = null;
		if(generalService==null){
			generalService = (GeneralService) SpringUtil.getBean("generalService");
		}
		generalService.saveLoginfo(executeDetail, userId, modeType, loginPost, writeValue, ipAdress ,url);
		return result;
	}
}
