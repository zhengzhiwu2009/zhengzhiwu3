package com.whaty.platform.entity.web.action.exam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpSession;

import com.sun.org.apache.bcel.internal.generic.DCMPG;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzExamBatch;
import com.whaty.platform.entity.bean.PeBzzExamLate;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzExamSite;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.exception.MessageException;
import com.whaty.platform.entity.service.exam.PeBzzExamScoreService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeBzzExamScoreEditAction extends MyBaseAction<PeBzzExamScore>{
	
	private List<PeBzzExamBatch> peBzzExamBatch;
	
	@Override
	public void initGrid() {
		this.setCanProjections(true);
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setCapability(false,false,false);
		//this.getGridConfig().setCapability(true, true, true);
		
		this.getGridConfig().setTitle("成绩信息");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学生姓名"),"peBzzStudent.trueName",false,true,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("所在考试批次"),"peBzzExamBatch.name",true,true,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("考点"),"peBzzExamSite.name",true,true,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("成绩"),"score",false,true,false,"TextField");
		this
		.getGridConfig()
		.addRenderScript(
				this.getText("操作"),
				"{return '<a href=/entity/manager/exam/reject_examlate.jsp?id='+record.data['id']+'&type=enterprise>录入|修改</a>';}",
				"");
	}

	public void setEntityClass() {
		this.entityClass = PeBzzExamScore.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/exam/peBzzExamScore";
	}

	public void setBean(PeBzzExamScore instance) {
		super.superSetBean(instance);
	}

	public PeBzzExamScore getBean() {
		return super.superGetBean();
	}
	
	//MenuFunction  方法
	

	
	//add校验
	
	public void checkBeforeAdd() throws EntityException {
		
	}
	
	//update校验
	
	public void checkBeforeUpdate() throws EntityException {
		
	}
	
	//懒加载
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamScore.class);
		dc.createCriteria("peBzzStudent", "peBzzStudent", DetachedCriteria.INNER_JOIN);
		dc.createCriteria("peBzzExamBatch", "peBzzExamBatch", DetachedCriteria.INNER_JOIN);
		dc.createCriteria("peBzzExamSite", "peBzzExamSite", DetachedCriteria.INNER_JOIN);
		
		DetachedCriteria dct = DetachedCriteria.forClass(PeBzzStudent.class);
		dct.createAlias("peEnterprise", "peEnterprise",DetachedCriteria.LEFT_JOIN);
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		if(!us.getUserLoginType().equals("3")) {
			List<String> priEntIds = new ArrayList<String>();
			for(PeEnterprise pe : us.getPriEnterprises()) {
				priEntIds.add(pe.getId());
			}
			if(priEntIds.size() != 0) {
				dc.add(Restrictions.in("peBzzStudent.peEnterprise.id", priEntIds));
			} else {
				dc.add(Expression.eq("2", "1"));
			}
		}
		return dc;
	}
	

	public List<PeBzzExamBatch> getPeBzzExamBatch() {
		return peBzzExamBatch;
	}

	public void setPeBzzExamBatch(List<PeBzzExamBatch> peBzzExamBatch) {
		this.peBzzExamBatch = peBzzExamBatch;
	}
}
