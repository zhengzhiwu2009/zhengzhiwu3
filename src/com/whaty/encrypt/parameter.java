package com.whaty.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.whaty.util.MyContextCfg;

public class parameter {
	public static String g_ip = MyContextCfg.getProperty("g_ip"); // ������IP��ַ

	public static String g_domain = MyContextCfg.getProperty("g_domain"); // ����������

	public static String port = MyContextCfg.getProperty("port"); // ������˿�

	public static String g_root = MyContextCfg.getProperty("g_root"); // ��Ŀ¼��

	public static String g_basedir = MyContextCfg.getProperty("g_basedir"); // �����Ŀ¼

	public static String g_imagesdir = MyContextCfg.getProperty("g_imagesdir");// ͼƬĿ¼

	public static String g_cssdir = MyContextCfg.getProperty("g_cssdir");// CSSĿ¼

	public static String g_edudir = MyContextCfg.getProperty("g_edudir");// whatyedu·��

	public static String g_incoming = MyContextCfg.getProperty("g_incoming"); // �����ļ��ĸ�Ŀ¼

	public static String smtp_server = MyContextCfg.getProperty("smtp_server"); // �����Ŀ¼

	public static String smtp_user = MyContextCfg.getProperty("smtp_user");

	public static String smtp_password = MyContextCfg.getProperty("smtp_password");

	public static String g_courseware = MyContextCfg.getProperty("g_courseware"); // �����Ŀ¼

	public static String g_admin_email = MyContextCfg.getProperty("g_admin_email"); // ����Ա��email

	public static String g_scollfile = MyContextCfg.getProperty("g_scollfile"); // ftp�ļ�Ŀ¼

	public static String g_castfile = MyContextCfg.getProperty("g_castfile"); // �ֳ��㲥�����ļ�

	public static String g_imagefile = MyContextCfg.getProperty("g_imagefile"); // �ֳ��㲥ͼƬ�ļ�

	public static int g_ftpport = Integer.parseInt(MyContextCfg.getProperty("g_ftpport")); // ftp port

	public static String g_ftprootdir = MyContextCfg.getProperty("g_ftprootdir"); // ftp
																			// root
																			// directory

	public static String g_counterfile = MyContextCfg.getProperty("g_counterfile"); // �������ļ�

	public static String g_iccURL = MyContextCfg.getProperty("g_iccURL");

	public static String g_iccName = MyContextCfg.getProperty("g_iccName");

	public static String getsetup(String prop) {

		try {
			Properties Props = new Properties();
			String path = parameter.class.getResource("parameter.class")
					.toString();

			String curpath = path.substring(path.indexOf('/'), path
					.lastIndexOf('/'));
			File file = new File(curpath + "/competence.properties");
			FileInputStream in = new FileInputStream(file);
			try {
				Props.load(in);
				if (in != null) {
					in.close();
				}
				String temp = fixnull(Props.getProperty(prop));
				return temp;
			} catch (IOException e) {
				System.out.println("error in properties set");
				return "fsdf";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "sdfs";
		}
	}

	public static String getEncryptKey() {
		return "asdgkjga";
	}

	public static String fixnull(String temp) {
		if (temp == null || temp.equals("null") || temp.equals(""))
			temp = "";
		return temp;
	}

	public String getDbMsg(String prop) {

		try {
			Properties Props = new Properties();
			InputStream in = getClass()
					.getResourceAsStream("db_backup.properties");
			try {
				Props.load(in);
				if (in != null) {
					in.close();
				}
				String temp = fixnull(Props.getProperty(prop));
				System.out.println(temp);
				return temp;
			} catch (IOException e) {
				System.out.println("error in properties set");
				return "fsdf";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "sdfs";
		}
	}

	public static boolean adminLogin(String username,String password) {

		try {
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("whaty_init.properties"));
			String username1 = fixnull(props.getProperty("username"));
			String password1 = fixnull(props.getProperty("password"));
			if(username!=null&&username.equals(username1)&&password!=null&&password.equals(password1))
				return true;
			else 
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
