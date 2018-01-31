package com.whaty.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Element;

import com.whaty.platform.config.ForumConfig;
import com.whaty.platform.config.PlatformConfig;
import com.whaty.platform.entity.service.quartz.QuartzService;
import com.whaty.servlet.*;
import com.whaty.util.HzphCache;
import com.whaty.util.SpringUtil;

public class ResetOnlineNum extends HttpServlet {
	private static HzphCache hzphCache;
	/**
	 * Constructor of the object.
	 */
	public ResetOnlineNum() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * 引用启动是清空缓存中的在线人数
	 */
	public void init() throws ServletException {
		Context env;
		try {
			this.getHzphCache();
			env = (Context) new InitialContext().lookup("java:comp/env");
		
			String cname = "Onlinesum";
			String jvmRoute= (String)env.lookup("jvmRoute");
			if(jvmRoute!=null && !"".equals(jvmRoute)){
				cname = jvmRoute+"_"+cname;
			}
			hzphCache.putToCache(cname, 0, 5*1000);
			Element ell=hzphCache.getCache().get(cname);
			System.out.println(new Date()+"_"+jvmRoute+"在线用户还有："+ell.getValue());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("在线人数失败！！！！！！");
		}
	}

	public static HzphCache getHzphCache() {
		if(hzphCache==null){
			hzphCache = (HzphCache) SpringUtil.getBean("hzphCacheCacheManager");
		}
		return hzphCache;
	}

	public static void setHzphCache(HzphCache hzphCache) {
		ResetOnlineNum.hzphCache = hzphCache;
	}
}
