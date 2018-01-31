package com.whaty.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class OnlineExamParameter {
public static String onlineExam_isExam=getsetup("isExam");
	
	public static String onlineExam_type=getsetup("type");
	
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
				return "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public static String fixnull(String temp) {
		if (temp == null || temp.equals("null") || temp.equals(""))
			temp = "";
		return temp;
	}
}
