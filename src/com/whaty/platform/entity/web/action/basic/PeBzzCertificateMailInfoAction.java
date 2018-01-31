package com.whaty.platform.entity.web.action.basic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzCerficateMailInfo;
import com.whaty.platform.entity.bean.PeBzzExamBatch;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeBzzCertificateMailInfoAction extends MyBaseAction<PeBzzCerficateMailInfo> {
	
	
	private PeBzzExamBatch peBzzExamBatch = null;
	
	private String exambatch_id;
	
	public String getExambatch_id() {
		return exambatch_id;
	}

	public void setExambatch_id(String exambatch_id) {
		this.exambatch_id = exambatch_id;
	}
	Map session =  ActionContext.getContext().getSession();
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(true, true, true);
		
//		this.getGridConfig().setCanBack(true);
//		this.getGridConfig().setBackUrl(this.getBackUrl(ServletActionContext.getRequest()));
		
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location='/entity/basic/peCertificateErJi.action?backParam=true';}");
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		this.getGridConfig().setTitle(this.getText("邮寄信息列表"));
		
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		
		//this.getGridConfig().addColumn(this.getText("邮寄单位"),"peEnterprise.name",true,true,true,"TextField",true,250);
		this.getGridConfig().addColumn(this.getText("邮寄单位"), "peEnterprise.name", false,
				false, true, "");
		
		this.getGridConfig().addColumn(this.getText("件数"), "num",
				false, true, true, Const.fiftyNum_for_extjs);

		this.getGridConfig().addColumn(this.getText("收发确认"),"enumConstByFlagMailSendStatus.name");
		this.getGridConfig().addColumn(this.getText("快递单号"),"expressNo",true,true,true,"TextField",true,500);
		//this.getGridConfig().addColumn(this.getText("是否含发票"),"enumConstByFlagContainInvoiceState.name");
		
		this.getGridConfig().addColumn(this.getText("操作人"),"luruPeople",true,false,true,"TextField",true,250);
		this.getGridConfig().addColumn(this.getText("操作时间"),"luruDate",true,false,true,"TextField",true,250);
		this.getGridConfig().addColumn(this.getText("备注"),"bz",true,true,true,"TextField",true,1000);
		HttpSession session = ServletActionContext
		.getRequest().getSession();
		
		session.setAttribute("pebzzexambatchId", this.getExambatch_id());
//		this.getGridConfig().addMenuScript(this.getText("返回"),
//				"{window.location='/entity/recruit/peRegister.action'}");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass=PeBzzCerficateMailInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath="/entity/basic/peBzzCertificateMailInfo";
	}
	
	public void setBean(PeBzzCerficateMailInfo instance){
		super.superSetBean(instance);
	}
	
	public PeBzzCerficateMailInfo getBean() {
		return super.superGetBean();
	}
	
	@Override
	public void checkBeforeAdd() throws EntityException {
		// TODO Auto-generated method stub
		UserSession us = (UserSession)ServletActionContext
		.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us == null) {
			throw new EntityException("无操作权限");
		}
		
		String luru_people=us.getLoginId();
		
		this.getBean().setLuruDate(this.getCurSqlDate());
		this.getBean().setLuruPeople(luru_people);
		
		if(this.getExambatch_id() == null || "".equals(this.getExambatch_id())) {
			HttpSession session = ServletActionContext
			.getRequest().getSession();
			String batch_id = (String) session.getAttribute("pebzzexambatchId");
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamBatch.class);
			dc.add(Restrictions.eq("id", batch_id));
			List<PeBzzExamBatch> list = this.getGeneralService().getList(dc);
			PeBzzExamBatch peBzzExam = (PeBzzExamBatch) list.get(0);
			this.getBean().setPeBzzExamBatch(peBzzExam);
			
		}
	}

	@Override
	public void checkBeforeUpdate() throws EntityException {
		// TODO Auto-generated method stub
		UserSession us = (UserSession)ServletActionContext
		.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us == null) {
			throw new EntityException("无操作权限");
		}
		String luru_people=us.getLoginId();
		
		this.getBean().setLuruDate(this.getCurSqlDate());
		this.getBean().setLuruPeople(luru_people);
		
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzCerficateMailInfo.class);
		dc.createCriteria("peEnterprise", "peEnterprise");
		dc.createCriteria("enumConstByFlagMailSendStatus", "enumConstByFlagMailSendStatus");
		DetachedCriteria bat=dc.createCriteria("peBzzExamBatch", "peBzzExamBatch");
		DetachedCriteria sel= bat.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
		//sel.add(Restrictions.eq("enumConstByFlagExamBatch.id","402880a91dadc115011dadfcf26b00aa"));
		if(this.getBean() != null && !this.getExambatch_id().equals(""))
			dc.add(Restrictions.eq("peBzzExamBatch.id",this.getExambatch_id()));
		
		if(this.getBean() != null && this.getBean().getPeEnterprise() != null)
		dc.add(Restrictions.eq("peEnterprise.id",this.getBean().getPeEnterprise().getId()));
		
		return dc;
	}

	
	private java.sql.Date getCurSqlDate()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		Date currentTime_2 = null;
		try {
			currentTime_2 = formatter.parse(dateString);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date d2 = new java.sql.Date(currentTime_2.getTime());
		return d2;  
	}
	
	private PeBzzExamBatch getCurBatch() throws EntityException {
		if(this.peBzzExamBatch == null) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamBatch.class);
			DetachedCriteria sel= dc.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
			sel.add(Restrictions.eq("id","402880a91dadc115011dadfcf26b00aa"));
			
			List<PeBzzExamBatch> list = this.getGeneralService().getList(dc);
			if(list == null || list.size() == 0) {
				throw new EntityException("无当前考试批次");
			} else {
				this.peBzzExamBatch= list.get(0);
			}
		}
		
		return this.peBzzExamBatch;
	}

	public PeBzzExamBatch getPeBzzExamBatch() {
		return peBzzExamBatch;
	}

	public void setPeBzzExamBatch(PeBzzExamBatch peBzzExamBatch) {
		this.peBzzExamBatch = peBzzExamBatch;
	}
	
	
}
