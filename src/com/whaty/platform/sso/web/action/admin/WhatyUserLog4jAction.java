package com.whaty.platform.sso.web.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

public class WhatyUserLog4jAction extends MyBaseAction<WhatyuserLog4j> {
	
	/**
	 * 日志管理列表加载
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("日志管理"));
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("用户名"), "userid");
		this.getGridConfig().addColumn(this.getText("行为"), "behavior");
		this.getGridConfig().addColumn(this.getText("操作时间"), "operateTime",true,false,true,"Datetime",false,0);
		this.getGridConfig().addColumn(this.getText("状态"), "status");
//		this.getGridConfig().addColumn(this.getText("说明"), "notes");
		this.getGridConfig().addPreviewColumn("notes");
		this.getGridConfig().addColumn(this.getText("身份"), "logtype");
		this.getGridConfig().addColumn(this.getText("IP"), "ip");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = WhatyuserLog4j.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/log4j";
	}
	/**
	 * 日志管理列表查询
	 */
	@Override
	public DetachedCriteria initDetachedCriteria() {
		JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
		DetachedCriteria dc = DetachedCriteria.forClass(WhatyuserLog4j.class);
		if(!this.getSort().equals("operateTime"))
			dc.addOrder(Order.desc("operateTime"));
		return dc;
		
	}
	
	

}
