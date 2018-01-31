package com.whaty.platform.util;


import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * @author xmeans
 * */
@Component
public class ServletUtil implements ServletContextAware{

	private static ServletContext servletContext;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}
}
