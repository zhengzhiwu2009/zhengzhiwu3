package com.whaty.platform.sso.web.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.config.PlatformConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class AdminLoginAction extends MyBaseAction {

	private String admin_pwd; // 密码
	private String admin_nm; // 用户名

	public String getAdmin_pwd() {
		return admin_pwd;
	}

	public void setAdmin_pwd(String admin_pwd) {
		this.admin_pwd = admin_pwd;
	}

	public String getAdmin_nm() {
		return admin_nm;
	}

	public void setAdmin_nm(String admin_nm) {
		this.admin_nm = admin_nm;
	}

	/**
	 * 后台管理员入口
	 * 
	 * @return
	 * @author linjie
	 */
	public String adminLogin() {
		return "login";
	}

	/**
	 * 验证用户名和密码是否正确，正确则进入管理页面，否则返回登录页面
	 * 
	 * @return
	 * @author linjie
	 */
	public String login() {
		// Lee start 2014年9月24日15:26:34 限制外网访问
		String loginIpString = this.getRemortIP(ServletActionContext.getRequest());
		if (!(loginIpString.indexOf("192.168") == 0 || loginIpString.indexOf("10.") == 0 || loginIpString.indexOf("127.0.0.1") == 0)) {
			return "login";
		}
		// Lee end
		String adming_name = "training";
		ActionContext act = ActionContext.getContext();

		PlatformConfig platformConfig = (PlatformConfig) act.getApplication().get("platformConfig");
		String admin_pwd = this.getAdmin_pwd();
		if (admin_pwd.equals(platformConfig.getManagepwd()) && adming_name.equals(this.getAdmin_nm())) {
			act.getSession().put("admin", "1");
			return "in";
		} else {
			return "login";
		}
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/admin";

	}

	private String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
}
