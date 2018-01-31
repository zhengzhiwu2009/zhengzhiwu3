package com.whaty.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

public class MyContextCfg {
	public static String CONTEXT_PATH = "/";

	public static HashMap<String, String> DOMAINSUBROOT = new HashMap();// 存放域名到域名简称的映射
	public static HashMap<String, String> SUBROOTDOMAIN = new HashMap();// 存放<子域名简称,域名>，如<xxx,www.xxx.com>
	public static HashMap<String, Long> DOMAIN_WEBSITE = new HashMap();// 存放子域名与子域名在CMS库表中ID-KEY-VALUE
	public static String CMS_SYS_LOGINID = "admin";// CMS超级管理员账号
	public static String CMS_SYS_PWD = "password";// CMS超级管理员密码
	public static Long CMS_NEWMANAGER_ROLEID = 21L;// 新联管理员角色ID
	public static Long CMS_SYS_ROLEID = 1L;// 超级管理角色ID

	public static String CMS_SUFFIX = getProperty("web.cms.suffix");	//ＣＭＳ的名称
	public static Map<String, String> STU_PRO_NULLABLE = new HashMap<String, String>(); // 存放学员信息的必填项设置
	public static Map<String, String> SYS_PROPERTIES = new HashMap<String, String>(); // 存放whaty_init.properties中的key和value
	private static final String propertiesFileName = "whaty_init.properties"; // 获取配置文件路径

	static {
	}


	public static String getProperty(String key) {
		Properties props = new Properties();
		try {
			props.load(MyContextCfg.class
					.getResourceAsStream("/" + propertiesFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String)props.get(key);
	}

	/**
	 * 更新properties文件属性值
	 * 
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		Properties props = new Properties();
		OutputStream os = null;
		try {
			String classRootPath = MyContextCfg.class.getResource("/")
					.toString();
			if ("Windows".indexOf(System.getProperty("os.name")) != -1)
				classRootPath = classRootPath.replace("file:/", "");
			else
				classRootPath = classRootPath.replace("file:", "");

			props.load(MyContextCfg.class
					.getResourceAsStream("/" + propertiesFileName));
			os = new FileOutputStream(classRootPath + propertiesFileName);
			props.put(key, value);
			props.store(os, "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * 加载学员信息的必填项设置
	 */
	public static void loadStuNullablePro() {
		Properties props = new Properties();
		String stuProperties = "";
		try {
			props.load(MyContextCfg.class
					.getResourceAsStream("/" + propertiesFileName));
			stuProperties = (String) props
					.get("web.user.student.property.nullable");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] params = stuProperties.split(",");
		MyContextCfg.STU_PRO_NULLABLE.clear();
		for (String s : params) {
			String key = s.split(":")[0]; // 属性名字
			String val = s.split(":")[1]; // 是否必填
			MyContextCfg.STU_PRO_NULLABLE.put(key, val);// 存入MAP
		}
	}

	public static void loadSysProperties() {
		SYS_PROPERTIES.clear();
		Properties props = new Properties();
		try {
			props.load(MyContextCfg.class
					.getResourceAsStream("/" + propertiesFileName));
			Enumeration keyEnum = props.keys();
			while (keyEnum.hasMoreElements()) {
				String key = (String) keyEnum.nextElement();
				SYS_PROPERTIES.put(key, props.getProperty(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private MyContextCfg() {
	}
	
	public static String getAscii(String str) {
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c > 255) {
				sb.append("\\u");
				j = (c >>> 8);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
				j = (c & 0xFF);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
			} else {
				sb.append(c);
			}

		}
		return (new String(sb));
	}
	
}
