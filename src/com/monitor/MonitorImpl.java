package com.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.jws.WebService;

@WebService(endpointInterface="com.monitor.Monitor",serviceName="Monitor")
public class MonitorImpl implements Monitor { 
	private String validate(String curUrl) {
		String flag = "true";
		try {
			URL url = new URL(curUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			InputStream input = conn.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
			String sCurrentLine = "";
			String sTotalString = "";
			while((sCurrentLine = l_reader.readLine()) != null){
				sTotalString+=sCurrentLine;
			} 
		} catch (IOException e) {
			e.printStackTrace();
			flag = "false";
		}
		return flag;
	}
	//登陆 CMS
	@Override
	public String loginCMSMonitor(){
		//String curUrl="http://localhost:9201";
		return "true";
	} 
	//登陆 CMS
	@Override
	public String loginHTMonitor() {
		//String curUrl="http://localhost:9201/login.jsp";
		return "true";
	}
	 
}
