/*
 * SystemProperties.java
 *
 * Created on 2004��11��26��, ����3:35
 */

package com.whaty.platform;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * 全局参数工具类
 * @author  Administrator
 */
public class GlobalProperties {
	
	private static Properties props;
	private static Properties hzph_props;
    
    /** Creates a new instance of SystemProperties */
    public GlobalProperties() {
    	String propsFile = getClass().getResource("").toString();
    	String os = System.getProperty("os.name");
    	if(os.indexOf("Windows") >= 0)
    		propsFile = propsFile.substring(6);	//ȥ��file:/
    	if(os.indexOf("Linux") >= 0)
    		propsFile = propsFile.substring(5); //ȥ��file:
    	propsFile = propsFile.substring(0,propsFile.indexOf("classes")) + "platform.properties";
    	//System.out.println(propsFile);
    	props = new Properties();
    	try {
			props.load(new FileInputStream(propsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("URL:" + props.get("smsgw_url"));
    }
    
    public GlobalProperties(String location) {
    	String propsFile = getClass().getResource("").toString();
    	String os = System.getProperty("os.name");
    	if(os.indexOf("Windows") >= 0)
    		propsFile = propsFile.substring(6);	//ȥ��file:/
    	if(os.indexOf("Linux") >= 0)
    		propsFile = propsFile.substring(5); //ȥ��file:
    	propsFile = propsFile.substring(0,propsFile.indexOf("classes")+8) + location + ".properties";
    	//System.out.println(propsFile);
    	hzph_props = new Properties();
    	try {
    		hzph_props.load(new FileInputStream(propsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("URL:" + props.get("smsgw_url"));
    }
    
    public static String getBasicEntityClass(String classname) {
        return null;
    }
    
    public static String getUserFactoryClass(java.lang.String classname) {
        return null;
    }
    
    public static String getInfoFactoryClass(java.lang.String classname) {
        return null;
    }
    
    public static String getResourceFactoryClass(java.lang.String classname) {
        return null;
    }
    
    public static String getAuthorizationFactoryClass(java.lang.String classname) {
        return null;
    }
    public static String getPrivilegeFactoryClass(String classname) {
        return null;
    }
    
   
    
    public static String getFactoryClass(String classname) {
        return null;
    }
    
    public HashMap getSmsGWSendPrameter() {
    	HashMap map = new HashMap();
    	map.put("url", props.getProperty("smsgw_url"));
    	map.put("account", props.getProperty("smsgw_account"));
    	map.put("password", props.getProperty("smsgw_password"));
    	return map;
    }
    
    
    public static String getProperty(String properName){
    	
    	
    	return getProperties().getProperty(properName);
    }
    
    public static Properties getProperties() {
    	return new GlobalProperties("init").hzph_props;
    }
}
