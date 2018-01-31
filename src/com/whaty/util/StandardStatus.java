package com.whaty.util;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.util.log.UserLog;

public class StandardStatus {
   public static String SSO_USER="userInfo";
   public static String SSO_USER_TYPE="sso_user_type";
   public static String USER_LOGIN_TYPE="login_type";
   public static String SITE_NAME_CHIEF = "�������﹫˾";
   public static String SITE_ID_CHIEF = "8008"; 
   public static String ERROR_MESSAGE = "�ѳ�ʱ";
   
   public static void insertLog(int i,String opraitionName,String note,HttpServletRequest request){
		String suc="";
		if(i>0){
			suc="�ɹ�";
			
		}
		else{
			suc="ʧ��";
		}
		UserLog.setInfo("<whaty>USERID$|$"
				//UserLog.getUser()
				+ "</whaty><whaty>BEHAVIOR$|$"+opraitionName+"</whaty><whaty>STATUS$|$"
				+ suc
				+ "</whaty><whaty>NOTES$|$"+note+"</whaty><whaty>USER_TYPE$|$"
				+ "manager</whaty><whaty>OPERATE_TIME$|$sysdate</whaty>"
			);//��(���ڵ�Ϊ�滻�ַ�
	}
}
