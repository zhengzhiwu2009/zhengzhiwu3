package com.whaty.util;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	private static WebApplicationContext wac; 
	private static HzphCache hzphCache;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据Bean名称获取实例
	 * 
	 * @param name
	 *            Bean注册名称
	 * 
	 * @return bean实例
	 * 
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}
	
	public static Object getBeanWAC(String name){
		ServletContext servletContext = ServletActionContext.getServletContext();
		wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return wac.getBean(name);
	}
	
	public static HzphCache getHzphCache() {
		if (null == hzphCache) {
			ServletContext servletContext = ServletActionContext.getServletContext();
			ApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(servletContext);
			hzphCache = (HzphCache) cxt.getBean("hzphCacheCacheManager");
		}
		return hzphCache;
	}
}
