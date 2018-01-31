package com.whaty.platform.entity.web.action.information;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionContext;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jboss.netty.handler.codec.http.HttpResponse;

import com.googlecode.jsonplugin.annotations.JSON;
import com.sun.net.httpserver.HttpContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.ReportInfo;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class SearchPasswordAction extends MyBaseAction{
	private String userName;
	private EnumConstService enumConstService;
	private GeneralService generalService;
	public EnumConstService getEnumConstService() {
		return enumConstService;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	@Override
	public void initGrid() {
	}
	@Override
	public void setEntityClass() {
	}
	@Override
	public void setServletPath() {
	}
	public String goPage(){
		return "searchPassword";
	}
	
	/**
	 * 按账号查询密码
	 * @author 魏慧宁
	 * @return
	 */
	public String goSearch(){
		PrintWriter out = null;
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			out = response.getWriter();
			if (!checkUserName()) {
				out.print("输入账号不存在或不是学员账号！");
				return NONE;
			}
			String searchSql = "select u.password from sso_user u where u.login_id='"+this.getUserName().trim()+"'";
			try {
				List list = this.getGeneralService().getBySQL(searchSql);
				if (list.size()>1) {
					out.print("账号问题：出现同名账号！");
				} else if(list.size()==0) {
					out.print("该账号不存在，请检查！");
				} else {
					out.print("密码为："+list.get(0).toString());
				}
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	/**
	 * 检查：输入账号是否为学员；
	 * @author 魏慧宁
	 * @return
	 */
	public Boolean checkUserName() {
		String sql = "select * from sso_user u where u.fk_role_id = '0' and u.login_id = '"+this.getUserName().trim()+"'";
		try {
			List list  = this.getGeneralService().getBySQL(sql);
			if (list.size()>0) {
				return true;
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return false;
	}
}
