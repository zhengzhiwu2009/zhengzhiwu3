package com.whaty.platform.entity.web.action.recruit.baoming;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzMailInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeBzzMailInfoAction extends MyBaseAction<PeBzzMailInfo> {
	
	
	private PeBzzBatch peBzzBatch = null;
	private String select_batch_id;	//学期信息
	private PeBzzBatch selectBatch = null;	//所选择的学期
	
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(true, true, true);
		
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location.href='/entity/recruit/peRegister.action?backParam=true';}");
//		this.getGridConfig().setCanBack(true);
//		this.getGridConfig().setBackUrl(this.getBackUrl(ServletActionContext.getRequest()));
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		this.getGridConfig().setTitle(this.getText("邮件信息列表"));
		
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		
		//this.getGridConfig().addColumn(this.getText("邮寄单位"),"peEnterprise.name",true,true,true,"TextField",true,250);
		this.getGridConfig().addColumn(this.getText("邮寄单位"), "peEnterprise.name", false,
				false, true, "");
		
		this.getGridConfig().addColumn(this.getText("件数"), "num",
				false, true, true, Const.fiftyNum_for_extjs);

		this.getGridConfig().addColumn(this.getText("收发确认"),"enumConstByFlagMailSendState.name");
		this.getGridConfig().addColumn(this.getText("快递单号"),"expressNo",true,true,true,"TextField",true,500);
		this.getGridConfig().addColumn(this.getText("是否含发票"),"enumConstByFlagContainInvoiceState.name");
		
		this.getGridConfig().addColumn(this.getText("操作人"),"luruPeople",true,false,true,"TextField",true,250);
		this.getGridConfig().addColumn(this.getText("操作时间"),"luruDate",true,false,true,"TextField",true,250);
		this.getGridConfig().addColumn(this.getText("备注"),"bz",true,true,true,"TextField",true,1000);
		boolean addSearchStatus = true;
		boolean allowBlank = false;
		if(null != this.getSelect_batch_id() && !"".equals(this.getSelect_batch_id())){
			addSearchStatus = false;
			allowBlank = true;
		}
		//学期信息
		ColumnConfig c_name1 = new ColumnConfig(this.getText("学期"), "peBzzBatch.name", addSearchStatus,addSearchStatus, true,"TextField",allowBlank,25,"");
		c_name1.setComboSQL("select b.id,b.name from pe_bzz_batch b");
		this.getGridConfig().addColumn(c_name1);
		
//		this.getGridConfig().addMenuScript(this.getText("返回"),
//				"{window.location='/entity/recruit/peRegister.action'}");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass=PeBzzMailInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath="/entity/recruit/peBzzMailInfo";
	}
	
	public void setBean(PeBzzMailInfo instance){
		super.superSetBean(instance);
	}
	
	public PeBzzMailInfo getBean() {
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
		
//		this.getBean().setPeBzzBatch(this.getCurBatch());
		if(null != this.getSelect_batch_id() && !"".equals(this.getSelect_batch_id())){
			this.getBean().setPeBzzBatch(this.getSelectBatch(this.getSelect_batch_id()));
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
		
//		this.getBean().setPeBzzBatch(this.getCurBatch());
		if(null != this.getSelect_batch_id() && !"".equals(this.getSelect_batch_id())){
			this.getBean().setPeBzzBatch(this.getSelectBatch(this.getSelect_batch_id()));
		}
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzMailInfo.class);
		dc.createCriteria("peEnterprise", "peEnterprise");
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.createCriteria("enumConstByFlagMailSendState", "enumConstByFlagMailSendState");
		dc.createCriteria("enumConstByFlagContainInvoiceState", "enumConstByFlagContainInvoiceState");
		
//		dc.add(Restrictions.eq("peBzzBatch.recruitSelected","1"));
		//学期处理
		if(null != this.getSelect_batch_id() && !"".equals(this.getSelect_batch_id())){
			dc.add(Restrictions.eq("peBzzBatch.id", this.getSelect_batch_id()));
		}
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
	
	private PeBzzBatch getCurBatch() throws EntityException {
		if(this.peBzzBatch == null) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzBatch.class);
			dc.add(Restrictions.eq("recruitSelected","1"));
			
			List<PeBzzBatch> list = this.getGeneralService().getList(dc);
			if(list == null || list.size() == 0) {
				throw new EntityException("无当前报名学期");
			} else {
				this.peBzzBatch = list.get(0);
			}
		}
		
		return this.peBzzBatch;
	}
	private PeBzzBatch getSelectBatch(String id) throws EntityException {
		if(null == this.selectBatch){
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzBatch.class);
			dc.add(Restrictions.eq("id", id));
			List<PeBzzBatch> list = this.getGeneralService().getList(dc);
			if(list == null || list.size() == 0) {
				throw new EntityException("无当前报名学期");
			} else {
				this.selectBatch = list.get(0);
			}
		}
		return this.selectBatch;
	}
	
	public String getSelect_batch_id() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		select_batch_id = (String) session.getAttribute("select_batch_id");
		return select_batch_id;
	}

	public void setSelect_batch_id(String select_batch_id) {
		this.select_batch_id = select_batch_id;
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("select_batch_id", select_batch_id);
	}

	public PeBzzBatch getSelectBatch() {
		return selectBatch;
	}

	public void setSelectBatch(PeBzzBatch selectBatch) {
		this.selectBatch = selectBatch;
	}
	
}
