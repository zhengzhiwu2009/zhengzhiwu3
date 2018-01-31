package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
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

public class PeBzzReplyInfoAction extends MyBaseAction<PeBzzReplyInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String feedback_id;	//反馈的id号
	private PeBzzFeedbackInfo peBzzFeedbackInfo;	//对应的反馈
	private String content;	//回复的内容
	
	private String message;	//返回的信息
	private String jumpUrl;	//返回后跳转的url
	
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzReplyInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/peBzzReplyInfo";
	}
	
	/**
	 * 添加回复
	 * @return
	 */
	public String addReply(){
		this.peBzzFeedbackInfo = getPeBzzFeedbackInfo(this.getFeedback_id());
		return "addReply";
	}

	public String addReplyexe(){
		try {
			PeBzzReplyInfo instance = null;
			this.setBean(new PeBzzReplyInfo());
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			this.getBean().setAddDate(new Date());	//设置添加时间
			
			DetachedCriteria dc = null;
			//设置默认状态
			dc = DetachedCriteria.forClass(EnumConst.class);
			dc.add(Restrictions.eq("id","3028de3427f5b99sd347f5bdadc70004"));
			List list = this.getGeneralService().getList(dc);
			if(null != list && 0 < list.size()){
				this.getBean().setEnumConstByFlagReplyStatus((EnumConst) list.get(0));
			}
			
			//设置发布人
			dc = DetachedCriteria.forClass(PePriRole.class);
			dc.add(Restrictions.eq("id", us.getRoleId()));
//			PePriRole role = (PePriRole)this.getGeneralService().getList(dc).get(0);
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
//			this.getBean().setAuthor(role + "/" + trueName);
			this.getBean().setAuthor(trueName);
			this.getBean().setSsoUser(us.getSsoUser());
			this.getBean().setContent(this.getContent());
			this.getBean().setPeBzzFeedbackInfo(this.getPeBzzFeedbackInfo(this.getFeedback_id()));
			instance = this.getGeneralService().save(this.getBean());
			this.message = "添加成功！";
			this.jumpUrl = "/entity/basic/peBzzReplyInfo_addReply.action?feedback_id="+this.getFeedback_id();
		} catch(EntityException e){
			this.message = "添加失败！";
			this.jumpUrl = "/entity/basic/peBzzReplyInfo_addReply.action?feedback_id="+this.getFeedback_id();
		}
		return "addReplyexe";
	}
	/**
	 * 根据id获取反馈的内容
	 * @param id
	 */
	private PeBzzFeedbackInfo getPeBzzFeedbackInfo(String id) {
		PeBzzFeedbackInfo peBzzFeedbackInfo = null;
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forClass(PeBzzFeedbackInfo.class);
		dc.add(Restrictions.eq("id", id));
		try {
			peBzzFeedbackInfo = (PeBzzFeedbackInfo) this.getGeneralService().getList(dc).get(0);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return peBzzFeedbackInfo;
	}

	public void setBean(PeBzzReplyInfo bean){
		super.superSetBean(bean);
	}
	
	public PeBzzReplyInfo getBean(){
		return super.superGetBean();
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

}
