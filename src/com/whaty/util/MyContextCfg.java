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

	public static HashMap<String, String> DOMAINSUBROOT = new HashMap();// ���������������Ƶ�ӳ��
	public static HashMap<String, String> SUBROOTDOMAIN = new HashMap();// ���<���������,����>����<xxx,www.xxx.com>
	public static HashMap<String, Long> DOMAIN_WEBSITE = new HashMap();// �������������������CMS�����ID-KEY-VALUE
	public static String CMS_SYS_LOGINID = "admin";// CMS��������Ա�˺�
	public static String CMS_SYS_PWD = "password";// CMS��������Ա����
	public static Long CMS_NEWMANAGER_ROLEID = 21L;// ��������Ա��ɫID
	public static Long CMS_SYS_ROLEID = 1L;// ���������ɫID

	public static String CMS_SUFFIX = getProperty("web.cms.suffix");	//�ãͣӵ�����
	public static Map<String, String> STU_PRO_NULLABLE = new HashMap<String, String>(); // ���ѧԱ��Ϣ�ı���������
	public static Map<String, String> SYS_PROPERTIES = new HashMap<String, String>(); // ���whaty_init.properties�е�key��value
	private static final String propertiesFileName = "whaty_init.properties"; // ��ȡ�����ļ�·��

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
	 * ����properties�ļ�����ֵ
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
	 * ����ѧԱ��Ϣ�ı���������
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
			String key = s.split(":")[0]; // ��������
			String val = s.split(":")[1]; // �Ƿ����
			MyContextCfg.STU_PRO_NULLABLE.put(key, val);// ����MAP
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
