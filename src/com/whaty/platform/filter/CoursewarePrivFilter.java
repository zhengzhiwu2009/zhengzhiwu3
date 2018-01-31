package com.whaty.platform.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.whaty.platform.config.PlatformConfig;
import com.whaty.util.log.Log;

public class CoursewarePrivFilter implements Filter {

	/**
	 * 课件权限的过滤器的销毁期
	 * @author huguolong
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * 课件权限的过滤器的逻辑方法
	 * @param req
	 * @param res
	 * @throws IOException，ServletException
	 * @author huguolong
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) req;
		 HttpServletResponse response = (HttpServletResponse) res;
		 HttpSession session=request.getSession();
		 ServletContext application=session.getServletContext();
		 PlatformConfig config=(PlatformConfig)application.getAttribute("platformConfig");
		 Log.setDebug("enter CoursewarePrivFilter");
	     if(session.getAttribute("courseware_priv")==null)
	     {
	    	 response.sendRedirect(config.getPlatformWebAppUriPath()+"error/priv_error.jsp");
	    	 return;
	     }
	     else
	     {
	    	 chain.doFilter(request, response);
	     }

	}
	/**
	 * 课件权限的过滤器的初始化
	 * @param arg0
	 * @author huguolong
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
