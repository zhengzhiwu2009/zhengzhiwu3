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

import com.whaty.platform.sso.web.servlet.UserSession;

public class SessionOutPrivFilter implements Filter {
	/**
	 * 登陆过滤器的逻辑方法
	 * @author huguolong
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 登陆过滤器的逻辑方法
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
		 //ServletContext application=session.getServletContext();
		 String path1 = request.getContextPath();
		 String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
		 
		 if(session.getAttribute("user_session")==null)
	     {
	    	 response.sendRedirect(basePath1+"error/priv_error.htm");
	    	 return;
	     }
	     else
	     {
	    	 UserSession userSession = (UserSession)session.getAttribute("user_session");
	    	 if(userSession==null)
	    	 {
		    	 response.sendRedirect(basePath1+"error/priv_error.htm");
		    	 return;
		     }
	    	 else
	    	 {
	    		 chain.doFilter(request, response);
	    	 }
	     }
	}
	
	/**
	 * 登陆过滤器的初始化
	 * @param arg0
	 * @author huguolong
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
