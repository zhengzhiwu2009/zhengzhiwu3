package com.whaty.platform.entity.web.action.basic;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeBzzEvaluateAction extends MyBaseAction {
	public void initGrid() {
	}

	public void setEntityClass() {
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/basic/peBzzEvaluate";
	}
	
	
	public String Excellence(){
	
		return "excellence";
	}
	
	public String Optimal(){
		
		return "optimal";
	}
	public String StudentExcellence(){
			
			return "StudentExcellence";
	}
	
}
