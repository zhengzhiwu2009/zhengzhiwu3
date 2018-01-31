package com.whaty.log.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.log.Loginfo;
import com.whaty.log.service.LoginfoService;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class LoggerConfigAction extends MyBaseAction<Loginfo>{
	private LoginfoService loginfoService;
	
	public LoginfoService getLoginfoService() {
		return loginfoService;
	}
	public void setLoginfoService(LoginfoService loginfoService) {
		this.loginfoService = loginfoService;
	}
	@Override
	public void initGrid() {
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setTitle("日志开关配置");
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("功能名称"), "discription");
		this.getGridConfig().addColumn(this.getText("是否开启"), "on_off");
		this.getGridConfig().addMenuFunction(this.getText("开启"), "FlagIsvalid.true");
		this.getGridConfig().addMenuFunction(this.getText("关闭"), "FlagIsvalid.false");
		//this.getGridConfig().addMenuFunction(this.getText("初始化日志配置"), "info.true");
	}
	public Page list() {
		Page page = null;
		String sql="";
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from ( select id id ,discription discription ,DECODE(ON_OFF,1,'是','否') on_off FROM log_info_config )AA where 1=1  ") ;
		StringBuffer searchSql = new StringBuffer();
		Map params = this.getParamsMap();
		Iterator iterator = params.entrySet().iterator();
		do {
			if (!iterator.hasNext())
				break;
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
			String name = entry.getKey().toString();
			String value = ((String[]) entry.getValue())[0].toString();
			if (!name.startsWith("search__"))
				continue;
			if ("".equals(value))
				continue;
			if (name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")) {
				name = name.substring(name.lastIndexOf(".", name.lastIndexOf(".") - 1) + 1);
			} else {
				name = name.substring(8);
			}
			if (name.equals("discription")) {
				searchSql.append(" AND UPPER(AA.discription) LIKE '%" + value.toUpperCase() + "%' ");
			}
			/* 考试名称 */
			if (name.equals("on_off")) {
				searchSql.append(" AND UPPER(AA.on_off) LIKE '%" + value.toUpperCase() + "%' ");
			}	
		}while (true);
		String temp = this.getSort();
		if (temp != null && temp.indexOf(".") > 1) {
			if (temp.toLowerCase().startsWith("combobox_")) {
				temp = temp.substring(temp.indexOf(".") + 1);
			}
		}
		if (this.getSort() != null && temp != null) {
			/* 课程性质 */
			if (temp.equals("enumConstByFlagCourseType.name")) {
				temp = "COURSETYPE";
			}
		}
		sb.append(searchSql);
		try {
			page = this.getGeneralService().getByPageSQL(sb.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	public Map updateColumn() {
		String msg = "";
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {

			String[] ids = getIds().split(",");
			List idList = new ArrayList();

			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			String action = this.getColumn();
			try {
				if ("FlagIsvalid.true".equals(action)) {
						if(idList !=null && idList.size()>0){
							for(int i=0 ;i<idList.size() ;i++){
								String sql =" update log_info_config set on_off ='1' where id ='"+idList.get(i).toString().trim()+"' ";
								this.getGeneralService().executeBySQL(sql);
								loginfoService.getConfigMap();
							}
						}
						msg = "开启日志记录";
					}
				
			
				if (action.equals("FlagIsvalid.false")) {
					if(idList !=null && idList.size()>0){
						for(int i=0 ;i<idList.size() ;i++){
							String sql =" update log_info_config set on_off ='0' where id ='"+idList.get(i).toString().trim()+"' ";
							this.getGeneralService().executeBySQL(sql);
							loginfoService.getConfigMap();
						}
					}
					msg = "关闭日志记录";
					
				}
			} catch (EntityException e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", msg + "共有" + ids.length + "条记录操作成功");

		}
		return map;
	}
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/log/loggerConfig";
		
	}

}
