package com.whaty.platform.entity.web.action.recruit.baoming;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzZkInfo;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeBzzZkInfoAction extends MyBaseAction<PeBzzZkInfo> {
	
	private String e_id;
	private String select_batch_id;	//学期信息
	
	private PeBzzBatch curBatch = null;
	private PeBzzBatch selectBatch = null;	//所选择的学期
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true);
		
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location.href='/entity/recruit/peRegister.action?backParam=true';}");
//		this.getGridConfig().setCanBack(true);
//		this.getGridConfig().setBackUrl(this.getBackUrl(ServletActionContext.getRequest()));
		
		this.getGridConfig().setTitle(this.getText("制卡信息列表"));
		
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		//this.getGridConfig().addColumn(this.getText("企业"),"peEnterprise.name",
		//		false,false,true,Const.fiftyNum_for_extjs,false,5);
//		this.getGridConfig().addColumn(this.getText("企业"),"peEnterprise.name");
		
		
		this.getGridConfig().addColumn(this.getText("给出人数"),"givedPersonNum",
				true,true,true,"TextField",false,5,Const.fiftyNum_for_extjs);
		this.getGridConfig().addColumn(this.getText("收到人数"),"receivedPersonNum",
				true,true,true,"TextField",false,5,Const.fiftyNum_for_extjs);
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
		this.getGridConfig().addColumn(this.getText("操作时间"),"luruDate",
				true,false,true,"TextField",false,5);	
		
		}

	@Override
	public void setEntityClass() {
		this.entityClass=PeBzzZkInfo.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/recruit/peBzzZkInfo";
	}
	
	public void setBean(PeBzzZkInfo instance){
		super.superSetBean(instance);
	}
	
	public PeBzzZkInfo getBean() {
		return super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzZkInfo.class);
		dc.createCriteria("peEnterprise", "peEnterprise");
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		
//		dc.add(Restrictions.eq("peBzzBatch.recruitSelected", "1"));
		//学期处理
		if(null != this.getSelect_batch_id() && !"".equals(this.getSelect_batch_id())){
			dc.add(Restrictions.eq("peBzzBatch.id", this.getSelect_batch_id()));
		}
		dc.add(Restrictions.eq("peEnterprise.id", this.getE_id()));
		return dc;
	}
	
	@Override
	public void checkBeforeAdd() throws EntityException {
		UserSession us = (UserSession)ServletActionContext
		.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us == null) {
			throw new EntityException("无操作权限");
		}
		
		String luru_people=us.getLoginId();
		
		PeEnterprise peEnterprise = new PeEnterprise();
		peEnterprise.setId(this.getE_id());
		this.getBean().setPeEnterprise(peEnterprise);
		
		this.getBean().setLuruDate(this.getCurSqlDate());
		this.getBean().setLuruPeople(luru_people);
		
//		this.getBean().setPeBzzBatch(getCurBatch());
		if(null != this.getSelect_batch_id() && !"".equals(this.getSelect_batch_id())){
			this.getBean().setPeBzzBatch(this.getSelectBatch(this.getSelect_batch_id()));
		}
	}
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
		UserSession us = (UserSession)ServletActionContext
		.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us == null) {
			throw new EntityException("无操作权限");
		}
		String luru_people=us.getLoginId();
		this.getBean().setLuruDate(this.getCurSqlDate());
		this.getBean().setLuruPeople(luru_people);
		
//		this.getBean().setPeBzzBatch(getCurBatch());
		if(null != this.getSelect_batch_id() && !"".equals(this.getSelect_batch_id())){
			this.getBean().setPeBzzBatch(this.getSelectBatch(this.getSelect_batch_id()));
		}
	}

	public String getE_id() {
		HttpSession session = ServletActionContext
		.getRequest().getSession();
		
		e_id = (String) session.getAttribute("e_id");
		return e_id;
	}

	public void setE_id(String e_id) {
		this.e_id = e_id;
		HttpSession session = ServletActionContext
		.getRequest().getSession();
		session.setAttribute("e_id", e_id);
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
			e.printStackTrace();
		}
		java.sql.Date d2 = new java.sql.Date(currentTime_2.getTime());
		return d2;  
	}

	private PeBzzBatch getCurBatch() throws EntityException {
		if(this.curBatch== null) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzBatch.class);
			dc.add(Restrictions.eq("recruitSelected","1"));
			
			List<PeBzzBatch> list = this.getGeneralService().getList(dc);
			if(list == null || list.size() == 0) {
				throw new EntityException("无当前报名学期");
			} else {
				this.curBatch = list.get(0);
			}
		}
		
		return this.curBatch;
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
