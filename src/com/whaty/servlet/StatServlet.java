package com.whaty.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 统计跳转Servlet
 * 
 * @author Lee
 * 
 */
public class StatServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		response.setContentType("text/html");
		String u = request.getParameter("u");
		request.setAttribute("u", u);
		if ("24".indexOf(us.getUserLoginType()) > -1) {// 集体管理员
			response.sendRedirect("/birt/zqyxh/" + u + "?organId=" + us.getPriEnterprises().get(0).getId());
		} else if ("56".indexOf(us.getUserLoginType()) > -1) {// 监管集体管理员
			response.sendRedirect("/birt/zqyxh/secondzqyxh/" + u + "?organId=" + us.getPriEnterprises().get(0).getId());
		} else {// 协会管理员
			response.sendRedirect("/birt/zqyxh/" + u);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		response.setContentType("text/html");
		String u = request.getParameter("u");
		request.setAttribute("u", u);
		if ("24".indexOf(us.getUserLoginType()) > -1) {// 集体管理员
			response.sendRedirect("/birt/zqyxh/" + u + "?organId=" + us.getPriEnterprises().get(0).getId());
		} else if ("56".indexOf(us.getUserLoginType()) > -1) {// 监管集体管理员
			response.sendRedirect("/birt/zqyxh/secondzqyxh/" + u + "?organId=" + us.getPriEnterprises().get(0).getId());
		} else {// 协会管理员
			response.sendRedirect("/birt/zqyxh/" + u);
		}
	}
}
