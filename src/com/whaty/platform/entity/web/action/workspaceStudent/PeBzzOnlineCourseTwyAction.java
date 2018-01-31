package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzOnlineCourse;
import com.whaty.platform.entity.bean.PeBzzOnlineCourseTwy;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeBzzOnlineCourseTwyAction extends MyBaseAction<PeBzzOnlineCourseTwy>{

	private PeBzzOnlineCourseTwy peBzzOnlineCourseTwy;
	private List<PeBzzOnlineCourseTwy> onlineCourseTwyList;
	private String errMessage;
	private GeneralService generalService;
	
	private String onlineCourseId ;
	
	
	/**
	 * action 添加提问信息
	 * @return 返回到输入页面
	 */
	public String addQuestion() {
		if(this.peBzzOnlineCourseTwy == null || this.peBzzOnlineCourseTwy.getQuestion() == null || 
				this.peBzzOnlineCourseTwy.getQuestion().equals("") || this.peBzzOnlineCourseTwy.getQuestion().equals("请输入问题内容:")) {
			errMessage = "问题不能为空！";
			return "AddSuccess";
		}
		
		this.peBzzOnlineCourseTwy.setPeBzzOnlineCourse(this.getCurOnlineCourse());
		UserSession us = (UserSession) ServletActionContext.getRequest()
			.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(us == null) {
			errMessage = "连接超时！";
			return "AddSuccess";
		}
		this.peBzzOnlineCourseTwy.setSsoUser(us.getSsoUser());
		
		this.peBzzOnlineCourseTwy.setTwyDate(new Date());
		
		try {
			this.getGeneralService().save(this.peBzzOnlineCourseTwy);
		} catch (EntityException e) {
			e.printStackTrace();
			errMessage = "保存失败！";
			return "AddSuccess";
		}
		return "AddSuccess";
	}
	
	/**
	 * action 查询某学员的问题列表
	 * @return 返回到输入页面
	 */
	public String getQuerstionList() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOnlineCourseTwy.class);
		dc.createCriteria("peBzzOnlineCourse", "peBzzOnlineCourse");
		dc.createCriteria("ssoUser", "ssoUser");
		PeBzzOnlineCourse curOnlineCourse = this.getCurOnlineCourse();
		if(curOnlineCourse == null) {
			return "ErrorMessage";
		}
		dc.add(Restrictions.eq("peBzzOnlineCourse.id", curOnlineCourse.getId()));
		dc.add(Restrictions.eq("ssoUser.id", us.getSsoUser().getId()));
		
		dc.addOrder(Order.desc("twyDate"));
		
		try {
			this.setOnlineCourseTwyList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
			errMessage = "获取问题列表时失败！";
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	/**
	 * 获得当前直播课程
	 * @return
	 */
	private PeBzzOnlineCourse getCurOnlineCourse() {
		HttpSession session = ServletActionContext
		.getRequest().getSession();
		PeBzzOnlineCourse curOnlineCourse = (PeBzzOnlineCourse) session.getAttribute("CurOnlineCourse");
		if(curOnlineCourse == null) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOnlineCourse.class);
			Date date = new Date();
			dc.add(Restrictions.and(Restrictions.le("startDate",date), Restrictions.ge("endDate",date)));
			List list = null;
			try {
				list = generalService.getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(list != null && list.size() != 0) {
				curOnlineCourse = (PeBzzOnlineCourse) list.get(0);
				session.setAttribute("CurOnlineCourse", curOnlineCourse);
			}
		}
		return curOnlineCourse;
	}
	
	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		
	}

	public PeBzzOnlineCourseTwy getPeBzzOnlineCourseTwy() {
		return peBzzOnlineCourseTwy;
	}

	public void setPeBzzOnlineCourseTwy(PeBzzOnlineCourseTwy peBzzOnlineCourseTwy) {
		this.peBzzOnlineCourseTwy = peBzzOnlineCourseTwy;
	}

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	public List<PeBzzOnlineCourseTwy> getOnlineCourseTwyList() {
		return onlineCourseTwyList;
	}

	public void setOnlineCourseTwyList(
			List<PeBzzOnlineCourseTwy> onlineCourseTwyList) {
		this.onlineCourseTwyList = onlineCourseTwyList;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public String getOnlineCourseId() {
		return onlineCourseId;
	}

	public void setOnlineCourseId(String onlineCourseId) {
		this.onlineCourseId = onlineCourseId;
	}

	
}
