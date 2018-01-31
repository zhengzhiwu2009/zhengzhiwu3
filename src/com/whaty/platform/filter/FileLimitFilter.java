/*
 * ��filter��Ϊ�˲��ù������ֹĳ��Ŀ¼��ĳ���ļ��ķ���
 * ��web.xml�е��������£��Խ�ֹ����jsp,xtp�ļ�Ϊ��
 * 
 	<filter>
		<filter-name>FileLimitFilter</filter-name>
		<filter-class>
			com.whaty.platform.filter.FileLimitFilter
		</filter-class>
	</filter>
	
	<filter-mapping>
		<filter-name>FileLimitFilter</filter-name>
		<url-pattern>/incoming/*.jsp</url-pattern>
	</filter-mapping>
	
	<filter-mapping>
		<filter-name>FileLimitFilter</filter-name>
		<url-pattern>/incoming/*.xtp</url-pattern>
	</filter-mapping>
 * 
 * */

package com.whaty.platform.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public class FileLimitFilter implements Filter {

	/**
	 * 文件读取权限过滤器的销毁期
	 * @author huguolong
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * 文件读取权限过滤器的逻辑方法
	 * @param req
	 * @param res
	 * @throws IOException，ServletException
	 * @author huguolong
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
		 FilterChain chain) throws IOException, ServletException {
		 HttpServletResponse response = (HttpServletResponse) res;
		 response.sendError(403);
	     return;
	     
	}

	/**
	 * 文件读取权限过滤器的初始化
	 * @param arg0
	 * @author huguolong
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
