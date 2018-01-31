package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.bcel.internal.generic.DCMPG;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzCerficateMailInfo;
import com.whaty.platform.entity.bean.PeBzzCertificate;
import com.whaty.platform.entity.bean.PeBzzEditStudent;
import com.whaty.platform.entity.bean.PeBzzExamBatch;
import com.whaty.platform.entity.bean.PeBzzExamLate;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzExamSite;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.exception.MessageException;
import com.whaty.platform.entity.service.basic.PeBzzCertificateService;
import com.whaty.platform.entity.service.exam.PeBzzExamScoreService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeBzzCertificateInfoAction extends MyBaseAction<PeBzzCertificate>{
     
    private String exambatch_id;
    
    private String peEnterprise_id;
	
	public String getPeEnterprise_id() {
		return peEnterprise_id;
	}

	public void setPeEnterprise_id(String peEnterprise_id) {
		this.peEnterprise_id = peEnterprise_id;
	}

	public String getExambatch_id() {
		return exambatch_id;
	}

	public void setExambatch_id(String exambatch_id) {
		this.exambatch_id = exambatch_id;
	}	
	/**
	 * 
	 */
     
	@Override
	public void initGrid() {
		
		this.setCanProjections(true);
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setCapability(false,false,false);
		
//		this.getGridConfig().setCanBack(true);
//		this.getGridConfig().setBackUrl(this.getBackUrl(ServletActionContext.getRequest()));
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location='/entity/basic/peCertificateErJi.action?backParam=true';}");
		this.getGridConfig().setTitle("证书信息");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		//this.getGridConfig().addColumn(this.getText("学号"),"peBzzStudent.regNo",true,false,true,"TextField");
		this.getGridConfig().addColumn(this.getText("学号"),"peBzzStudent.regNo",true,true, true, "");
		this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.trueName",true,true, true, "");
		this.getGridConfig().addColumn(this.getText("移动电话"),"peBzzStudent.mobilePhone",true,false, true, "");
		//this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.trueName",true,false,true,"TextField",true,100);
		//this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.name",false,true,false,"TextField",false,100);
		this.getGridConfig().addColumn(this.getText("学期"),"peBzzStudent.peBzzBatch.name",false,
				false, true, "");
		//this.getGridConfig().addColumn(this.getText("学期"),"peBzzStudent.peBzzBatch.name",true,true,true,"TextField",false,50);
		ColumnConfig c_name1 = new ColumnConfig(this.getText("所在企业"),"peBzzStudent.peEnterprise.name");
		c_name1.setComboSQL("select t.id,t.name as p_name from pe_enterprise t where t.fk_parent_id is not null");
		this.getGridConfig().addColumn(c_name1);
		this.getGridConfig().addColumn(this.getText("证书号"),"certificate",true,true, true, "");
		
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzCertificate.class);
		//dc.createCriteria("peBzzStudent", "peBzzStudent", DetachedCriteria.INNER_JOIN);
		DetachedCriteria pebzz =dc.createCriteria("peBzzStudent","peBzzStudent", DetachedCriteria.INNER_JOIN);
		pebzz.createCriteria("peBzzBatch", "peBzzBatch", DetachedCriteria.INNER_JOIN);
		pebzz.createCriteria("peEnterprise", "peEnterprise", DetachedCriteria.INNER_JOIN);
		
		if(!this.getPeEnterprise_id().equals(""))
		{
			pebzz.add(Restrictions.eq("peEnterprise.id",this.getPeEnterprise_id()));
		}
		DetachedCriteria bat = DetachedCriteria.forClass(PeBzzExamBatch.class);
		bat.createCriteria("peBzzBatch", "peBzzBatch", DetachedCriteria.INNER_JOIN);
		
		if(!this.getExambatch_id().equals(""))
		{
			bat.add(Restrictions.eq("id",this.getExambatch_id()));
		}
		return dc;
	}
	
	
	public void setEntityClass() {
		this.entityClass = PeBzzCertificate.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peBzzCertificateInfo";
	}

	public void setBean(PeBzzCertificate instance) {
		super.superSetBean(instance);
	}

	public PeBzzCertificate getBean() {
		return super.superGetBean();
	}
	
	//add校验
	
	public void checkBeforeAdd() throws EntityException {
		
	}
	
	//update校验
	
	public void checkBeforeUpdate() throws EntityException {
		
	}

	
}