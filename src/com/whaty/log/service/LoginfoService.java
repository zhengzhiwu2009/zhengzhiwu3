package com.whaty.log.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.struts2.ServletActionContext;

import com.whaty.log.Loginfo;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.util.HzphCache;
import com.whaty.util.SpringUtil;

public class LoginfoService {
	private static Properties properties = new Properties();

	private GeneralService generalService;

	private GeneralDao generalDao;

	//private Map<String, String> configMap = new HashMap<String, String>();
	
	private Hashtable <String, String> configMap = new Hashtable<String, String>();
	
	private String url;
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	/**
	 * 读取配置文件 获取value
	 */
	public String getValue(String key) {
		String value = "";
		String filePath = getClass().getResource("/").getPath()
				+ "logger.properties";
		if (filePath != null) {
			try {
				InputStream in = new BufferedInputStream(new FileInputStream(
						filePath));
				properties.load(in);
				value = properties.getProperty(key);
				if (value != null && !value.equals("")) {
					value = new String(value.getBytes("ISO-8859-1"), "gbk");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	/**
	 * 执行动作 获取信息，生成中文信息描述 actionName 方法名 map 请求参数 url 路径
	 * 
	 */
	public String getExecuteDetail(String actionName, Map req_map,
			Object action, String method, String namespace) {
		
		//if (configMap == null || configMap.isEmpty()) {
			getConfigMap();
		//}
		String resultName = "";
		String parameter ="";
		// xxx_xx.action 的形式
		int index = actionName.indexOf("_");
		if (index != -1 && index != 0) {
			actionName = actionName.substring(0, index);
		}
			url = namespace + "/" + actionName + "_" + method;
		if (req_map.containsKey("column")) {
			parameter = ((String[]) req_map.get("column"))[0];
		}
		if(!parameter.equals("")){
			url =url + "?" + parameter;
		}
		for(int i=0 ;i<configMap.size() ;i++){
			if(configMap.containsKey(url)){
				resultName = configMap.get(url);
				if(resultName.length()>0){
					String on_off = resultName.substring(resultName.length()-1); //如果on_off 状态为0不记录日志
					if(on_off.length()>0 && on_off.equals("0")){
						return resultName="";
					}else{
						resultName =resultName.substring(0,resultName.length()-1);
							break;
					}
				}
			}
		}
		
		if(resultName ==null || resultName.equals("")){
			String flag =getValue("loginfoconfig");
			if(flag.equals("1")){
				resultName ="/" + actionName + "_" + method;
			}
		}

		return resultName;
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void setProperties(Properties properties) {
		LoginfoService.properties = properties;
	}

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	public void getConfigMap() {
		HzphCache hzphCache = SpringUtil.getHzphCache();
		Cache cache = hzphCache.getCache();
		Element tempMap = cache.get("logConfigMap");
		
		if(tempMap ==null){
			configMap = null;
		}else{
			configMap = (Hashtable)tempMap.getValue();
		}
		
		if(configMap == null){
			configMap = new Hashtable<String, String>();
			String sql = " select id ,discription ,mode_type ,on_off from log_info_config ";
			List list = generalDao.getBySQL(sql);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					configMap
							.put(String.valueOf(obj[0]), String.valueOf(obj[1])
									+ "&" + String.valueOf(obj[2])
									+ String.valueOf(obj[3]));
				}
			}
			hzphCache.putToCache("logConfigMap",configMap, 5 * 1000);
		}
	}

	public String loginPostName(String parameter) {
		String postName = "";
		if (parameter != null && parameter.equals("0")) {
			postName = "学员端";
		} else if (parameter != null && parameter.equals("2")) {
			postName = "集体端";
		} else if (parameter != null && parameter.equals("3")) {
			postName = "协会端";
		} else {
			postName = "其他";
		}
		return postName;
	}
	public String getIpAddr() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
			// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	/**
	 * jsp日志记录
	 * 调用方法
	 * */
	public void saveLoginfo(String userName){
		Loginfo log =new Loginfo();
		log.setUserCode(userName);
		generalDao.save(log);
	}
}
