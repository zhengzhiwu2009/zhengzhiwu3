package com.whaty.platform.sso.web.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class AdminLoginInterceptor  extends MethodFilterInterceptor {

	private static final Log logger = LogFactory.getLog(AdminLoginInterceptor.class);

	/**
	 *管理员登录拦截器
	 *@author huguolong
	 */
	private static final long serialVersionUID = 6748629623401983281L;

	@SuppressWarnings("unchecked")
	@Override
	public String doIntercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		
		setExcludeMethods("login");
		
		String actionName = "/" + ac.getName();
		//xxx_xx.action 的形式
		int index = actionName.indexOf("_");
		if(index != -1 && index != 0){
			actionName = actionName.substring(0,index);
		}
		
		String namespace = invocation.getProxy().getNamespace();
		
		String method =  invocation.getProxy().getMethod();

		ServletContext servletContext = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		if (wc == null) {
		logger.error("ApplicationContext could not be found.");
		return "intercepthtml";
		} else {

		String us =  (String) ac.getSession().get("admin");

		if (us != null ) {// 通行证已登录
		return invocation.invoke();
		}else{
			
			if(method!=null && method.equals("login"))
				return invocation.invoke();
		return "input";
		}
		}
		}

}
