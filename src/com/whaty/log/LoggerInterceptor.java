package com.whaty.log;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.whaty.log.service.LoginfoService;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.interceptor.UserPriorityInterceptor;
import com.whaty.platform.sso.web.servlet.UserSession;

public class LoggerInterceptor extends MethodFilterInterceptor {
	private LoginfoService loginfoService;
	private GeneralService generalService;

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	private static final Log logger = LogFactory.getLog(LoggerInterceptor.class);

	private static final long serialVersionUID = 185764090121544073L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {

		ActionContext actionContext = ActionContext.getContext();
		setExcludeMethods(SsoConstant.INTERCEPTOR_EXCLUDEMETHOD);
		HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
		ActionContext ac = invocation.getInvocationContext();
		UserSession us = null;
		String result = "";
		try {
			us = (UserSession) ac.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		} catch (Exception e) {
			return "sessionNull";
		}
		String actionName = ac.getName();
		result = invocation.invoke();
		if (actionName != null && !actionName.equals("peBulletinView_getOnlineNum")) {
			Object action = invocation.getProxy().getAction();
			String namespace = invocation.getProxy().getNamespace();
			String url = request.getRequestURI();

			String method = invocation.getProxy().getMethod();
			if (method != null && !method.equals("execute") && !method.equals("abstractList")
					&& ((MyBaseAction) invocation.getAction()).isDoLog() && !method.equals("gridjs")) {

				String writeValue = "";
				String readValue = "";
				String executeStatus = "";
				StringBuffer req_parm = new StringBuffer();
				Map req_map = ServletActionContext.getRequest().getParameterMap();
				Iterator iterator = req_map.entrySet().iterator();
				while (iterator.hasNext()) {
					java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
					req_parm.append(entry.getKey().toString() + ":" + ((String[]) entry.getValue())[0] + ";");
				}
				writeValue = req_parm.toString();// 说明；
				String json;
				try {
					json = ((MyBaseAction) invocation.getAction()).getJsonString();
				} catch (Exception e) {
					json = null;
				}

				executeStatus = "unknown";
				if (json != null) {
					if (json.indexOf("\"success\":\"true\"") > 0) {
						executeStatus = "操作成功";
						JSONObject js = JSONObject.fromObject(json);
						if (js.containsKey("info")) {
							req_parm.append(js.get("info"));
						}
						writeValue = req_parm.toString();
					} else if (json.indexOf("\"success\":\"false\"") > 0) {
						executeStatus = "操作失败";
						JSONObject js = JSONObject.fromObject(json);
						if (js.containsKey("info")) {
							req_parm.append(js.get("info"));
						}
						writeValue = req_parm.toString();
					}

				}
				String ipAdress = loginfoService.getIpAddr();
				String executeDetail = loginfoService.getExecuteDetail(actionName, req_map, action, method, namespace);
				if (executeDetail != null && !executeDetail.equals("")) {
					String[] detail = executeDetail.split("&");

					String postName = loginfoService.loginPostName(us.getUserLoginType());
					Loginfo loginfo = new Loginfo();
					loginfo.setSessionId(request.getSession().getId());
					loginfo.setUserCode(us.getLoginId());
					loginfo.setUserName(us.getUserName());
					loginfo.setExecuteDetail(detail[0]); // 执行动作
					if (detail.length > 1) {
						loginfo.setModeType(detail[1]); // 操作类型
					} else {
						loginfo.setModeType("unknown");
					}
					loginfo.setWriteValue(writeValue); // 输入值
					loginfo.setReadValue(readValue); // 输出值
					loginfo.setLoginPost(postName);
					loginfo.setIpAdress(ipAdress);
					loginfo.setExecuteStatus(executeStatus);
					loginfo.setUrl(url);
					loginfo.setLogConfigId(loginfoService.getUrl());
					loginfo.setExecuteDate(new Date());
					try {
						generalService.save(loginfo);
					} catch (Exception e) {
						System.out.println("LOG_INFO:ERROR|记录日志失败-----" + e.getLocalizedMessage());
					}
				}
			}
		}

		return result;
	}

	public LoginfoService getLoginfoService() {
		return loginfoService;
	}

	public void setLoginfoService(LoginfoService loginfoService) {
		this.loginfoService = loginfoService;
	}

}
