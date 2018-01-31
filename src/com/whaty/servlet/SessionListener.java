package com.whaty.servlet;

import java.io.Serializable;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.sf.ehcache.Element;

import com.whaty.util.HzphCache;
import com.whaty.util.SpringUtil;

public class SessionListener implements HttpSessionBindingListener,
		Serializable {
	private String name;
	private static HzphCache hzphCache;
	private String cname = "Onlinesum";

	private static String getJvmRoute() {
		Context env;
		String jvmRoute = "";
		try {
			env = (Context) new InitialContext().lookup("java:comp/env");
			jvmRoute = (String) env.lookup("jvmRoute");
		} catch (NamingException e) {
			System.out.println("在线人数失败！！！！！！");
			e.printStackTrace();
		}
		return jvmRoute;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		HttpSession session = arg0.getSession();
		ServletContext application = session.getServletContext();
		String jvmRoute = getJvmRoute();
		if (jvmRoute != null && !"".equals(jvmRoute)) {
			cname = jvmRoute + "_" + cname;
		}
		int sum = 0;
		Object ol = application.getAttribute("Onlinesum");
		if (ol != null && !"".equals(ol)) {
			sum = Integer.parseInt(ol.toString());
		}
		sum += 1;
		application.setAttribute("Onlinesum", sum);
		SpringUtil.getHzphCache().putToCache(cname, sum,
				5 * 1000);
		System.out.println(new Date() + "_" + jvmRoute + "_Online:" + (sum));
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		HttpSession session = arg0.getSession();
		ServletContext application = session.getServletContext();
		String jvmRoute = getJvmRoute();
		if (jvmRoute != null && !"".equals(jvmRoute)) {
			cname = jvmRoute + "_" + cname;
		}
		int sum = 0;
		Object ol = application.getAttribute("Onlinesum");
		if (ol != null && !"".equals(ol)) {
			sum = Integer.parseInt(ol.toString());
		}
		sum -= 1;
		if (sum < 0)
			sum = 0;

		application.setAttribute("Onlinesum", sum);
		SpringUtil.getHzphCache().putToCache(cname, sum,
				5 * 1000);
		System.out.println(new Date() + "_" + jvmRoute + "_OnlineSum:" + (sum));
	}
}
