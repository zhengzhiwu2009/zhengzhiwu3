package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzFeedbackInfo;
import com.whaty.platform.entity.bean.PeBzzReplyInfo;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeBzzFeedbackInfoAction extends MyBaseAction<PeBzzFeedbackInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String feedback_id;	//反馈id号
	private PeBzzFeedbackInfo peBzzFeedbackInfo;	//反馈内容
	private List<PeBzzReplyInfo> peBzzReplyInfoList = new ArrayList<PeBzzReplyInfo>();	//反馈对应的回复

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if("3".equals(us.getRoleId())){
			this.getGridConfig().setCapability(true, true, true);
		}else{
			this.getGridConfig().setCapability(true, false, true);
		}
		this.getGridConfig().setTitle(this.getText("反馈列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("发布人"), "author", true, false, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("反馈标题"), "title", true, true, true, "TextField", false, 50, Const.feedbackTitle_for_extjs);
		this.getGridConfig().addColumn(this.getText("反馈内容"), "content", false, true, true, "TextArea", false, 500);
		this.getGridConfig().addColumn(this.getText("反馈时间"), "addDate", true, false, true, "Date", false, 20);
		this.getGridConfig().addColumn(this.getText("联系方式"), "phone", false, true, true, "TextField", false, 20, Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFlagFeedbackStatus.name", true, false, true, "TextField", true, 50);
		this.getGridConfig().addRenderScript(this.getText("详情"), "{return '<a href=peBzzFeedbackInfo_showInfo.action?feedback_id='+record.data['id']+' target=_blank>详情</a>';}", "");
		if("3".equals(us.getRoleId())){
			this.getGridConfig().addMenuFunction(this.getText("设为有效"), "enumConstByFlagFeedbackStatus.id","3028de3427f5b99sd347f5bdadc70002");
			this.getGridConfig().addMenuFunction(this.getText("设为无效"), "enumConstByFlagFeedbackStatus.id","3028de3427f5b99sd347f5bdadc70001");
			this.getGridConfig().addRenderScript(this.getText("操作"), "{return '<a href=peBzzReplyInfo_addReply.action?feedback_id='+record.data['id']+'>添加回复</a>';}", "");
		}
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzFeedbackInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/peBzzFeedbackInfo";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forClass(PeBzzFeedbackInfo.class);
		dc.createCriteria("enumConstByFlagFeedbackStatus", "enumConstByFlagFeedbackStatus", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ssoUser", "ssoUser", DetachedCriteria.LEFT_JOIN);
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(!"3".equals(us.getRoleId())){
			dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
			dc.add(Restrictions.eq("status", "1"));
		}
		return dc;
	}

	public void setBean(PeBzzFeedbackInfo bean){
		super.superSetBean(bean);
	}
	
	public PeBzzFeedbackInfo getBean(){
		return super.superGetBean();
	}

	@Override
	public Map add() {
		Map map = new HashMap();
		this.superSetBean((PeBzzFeedbackInfo)setSubIds(this.getBean()));
		
		try {
			checkBeforeAdd();
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}

		PeBzzFeedbackInfo instance = null;
		try {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			this.getBean().setAddDate(new Date());	//设置添加时间
			
			DetachedCriteria dc = null;
			//设置默认状态
			dc = DetachedCriteria.forClass(EnumConst.class);
			dc.add(Restrictions.eq("id","3028de3427f5b99sd347f5bdadc70002"));
			List<EnumConst> list = this.getGeneralService().getList(dc);
			if(null != list && 0 < list.size()){
				this.getBean().setEnumConstByFlagFeedbackStatus(list.get(0));
			}
			//设置发布人
			dc = DetachedCriteria.forClass(PePriRole.class);
			dc.add(Restrictions.eq("id", us.getRoleId()));
			PePriRole role = (PePriRole)this.getGeneralService().getList(dc).get(0);
			String trueName = "";
			if("3".equals(us.getRoleId())){
				dc = DetachedCriteria.forClass(PeManager.class);
				dc.add(Restrictions.eq("loginId", us.getLoginId()));
				PeManager peManager = (PeManager) this.getGeneralService().getList(dc).get(0);
				if(null != peManager && !"".equals(peManager)){
					trueName = peManager.getTrueName();
				}
			}else{
				dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
				dc.add(Restrictions.eq("loginId", us.getLoginId()));
				PeEnterpriseManager peEnterpriseManager = (PeEnterpriseManager) this.getGeneralService().getList(dc).get(0);
				if(null != peEnterpriseManager && !"".equals(peEnterpriseManager)){
					trueName = peEnterpriseManager.getName();
				}
			}
			this.getBean().setAuthor(role.getName() + "/" + trueName);
			this.getBean().setSsoUser(us.getSsoUser());
			instance = this.getGeneralService().save(this.getBean());
			map.put("success", "true");
			map.put("info", "添加成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.checkAlternateKey(e, "添加");
			
		}
		return map;
	}
	
	public String showInfo(){
		DetachedCriteria dc = null;
		//获取反馈的信息
		dc = DetachedCriteria.forClass(PeBzzFeedbackInfo.class);
		dc.add(Restrictions.eq("id", this.getFeedback_id()));
		try {
			this.peBzzFeedbackInfo = (PeBzzFeedbackInfo) this.getGeneralService().getList(dc).get(0);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取回复的信息
		dc = DetachedCriteria.forClass(PeBzzReplyInfo.class);
		dc.createCriteria("peBzzFeedbackInfo", "peBzzFeedbackInfo", DetachedCriteria.INNER_JOIN);
		dc.add(Restrictions.eq("peBzzFeedbackInfo.id", this.getFeedback_id()));
		dc.addOrder(Order.desc("addDate"));
		try {
			this.peBzzReplyInfoList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "showInfo";
	}

	public String getFeedback_id() {
		return feedback_id;
	}

	public void setFeedback_id(String feedback_id) {
		this.feedback_id = feedback_id;
	}

	public PeBzzFeedbackInfo getPeBzzFeedbackInfo() {
		return peBzzFeedbackInfo;
	}

	public void setPeBzzFeedbackInfo(PeBzzFeedbackInfo peBzzFeedbackInfo) {
		this.peBzzFeedbackInfo = peBzzFeedbackInfo;
	}

	public List<PeBzzReplyInfo> getPeBzzReplyInfoList() {
		return peBzzReplyInfoList;
	}

	public void setPeBzzReplyInfoList(List<PeBzzReplyInfo> peBzzReplyInfoList) {
		this.peBzzReplyInfoList = peBzzReplyInfoList;
	}
	
}
