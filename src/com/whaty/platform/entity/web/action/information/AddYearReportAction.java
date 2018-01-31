package com.whaty.platform.entity.web.action.information;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.ReportInfo;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class AddYearReportAction extends MyBaseAction{

	private ReportInfo reportInfo;
	private String reportType;
	private String reportId;
	private EnumConstService enumConstService;
	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public ReportInfo getReportInfo() {
		return reportInfo;
	}

	public void setReportInfo(ReportInfo reportInfo) {
		this.reportInfo = reportInfo;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(false, true, false);	
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("报告题目"), "title");
		this.getGridConfig().addColumn(this.getText("添加人"), "trueName");
		this.getGridConfig().addColumn(this.getText("报告类型"), "enumConstByReportType.name");
		this
		.getGridConfig()
		.addRenderFunction(
				this.getText("详细内容"),
				"<a href=\"/entity/information/addYearReport_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>",
				"id");
			this.getGridConfig().addRenderFunction(this.getText("操作"),
			"<a href=\"/entity/information/addYearReport_edit.action?bean.id=${value}\">修改</a>", "id");	
		
		}

	@Override
	public void setEntityClass() {
		this.entityClass = ReportInfo.class;
	}
	public ReportInfo getBean() {
		return (ReportInfo) super.superGetBean();
	}

	public void setBean(ReportInfo bean) {
		super.superSetBean(bean);
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/addYearReport";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(ReportInfo.class);
		dc.createCriteria("enumConstByReportType", "enumConstByReportType");
		return dc;
	}
	public String showAddYear() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy");
		String date = df.format(new Date());
		ServletActionContext.getRequest().setAttribute("date", date);
		return "showAddYear";
	}
	public String saveReportInfo() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.reportInfo.setYear(this.reportInfo.getYear().substring(0, 4));
		
		DetachedCriteria dc = DetachedCriteria.forClass(ReportInfo.class);
		dc.add(Restrictions.eq("year", this.reportInfo.getYear()));
		
		EnumConst enumConstByReportType = null;
		if("0".equals(reportType)) {   //年报
			enumConstByReportType = this.getEnumConstService().getByNamespaceCode("ReportType", "0");
		} else if("1".equals(reportType)) {  //半年报
			enumConstByReportType = this.getEnumConstService().getByNamespaceCode("ReportType", "1");
			dc.add(Restrictions.eq("half", this.reportInfo.getHalf())); //0为上半年，1为下半年
   
		} else if("2".equals(reportType)) {  //季报
			enumConstByReportType = this.getEnumConstService().getByNamespaceCode("ReportType", "2");
			dc.add(Restrictions.eq("quarter", this.reportInfo.getQuarter())); //1为第一季度，2第二季度，3第三季度，4第四季度
		} else if("3".equals(reportType)) { //月报
			enumConstByReportType = this.getEnumConstService().getByNamespaceCode("ReportType", "3");
			dc.add(Restrictions.eq("month", this.reportInfo.getMonth()));
		}
		dc.add(Restrictions.eq("enumConstByReportType", enumConstByReportType));
		try {
			List list = this.getGeneralService().getList(dc);
			if(list.size()>0) {
				this.setMsg("此类报告已经添加过，不能再次添加");
				this.setTogo("/entity/information/addYearReport_showAddYear.action");
				return "msg";
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("对不起，好像出错了");
			this.setTogo("/entity/information/addYearReport_showAddYear.action");
			return "msg";
		}
		SsoUser ssoUser = us.getSsoUser();
		this.reportInfo.setSsoUser(ssoUser);
		this.reportInfo.setEnumConstByReportType(enumConstByReportType);
		this.reportInfo.setTrueName(us.getUserName());
		try {
			this.getGeneralService().save(this.reportInfo);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("对不起，好像出错了");
			this.setTogo("/entity/information/addYearReport_showAddYear.action");
			return "msg";
		}
		this.setMsg("添加成功");
		this.setTogo("/entity/information/addYearReport_showAddYear.action");
		return "msg";
	}
	
	public String viewDetail() {
		DetachedCriteria dc = DetachedCriteria.forClass(ReportInfo.class);
		dc.add(Restrictions.eq("id", this.getBean().getId()));
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list.size()>0) {
			this.reportInfo = (ReportInfo)list.get(0);
		}
		return "viewDetail";
	}
	
	public String edit() {
		DetachedCriteria dc = DetachedCriteria.forClass(ReportInfo.class);
		dc.add(Restrictions.eq("id", this.getBean().getId()));
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list.size()>0) {
			this.reportInfo = (ReportInfo)list.get(0);
		} else {
			this.setMsg("对不起，此报告不存在");
			this.setTogo("/entity/information/addYearReport.action");
			return "msg";
		}
		return "edit";
	}
	public String editReportInfo() {
		DetachedCriteria dc = DetachedCriteria.forClass(ReportInfo.class);
		dc.add(Restrictions.eq("id", this.reportId));
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list.size()>0) {
			ReportInfo ri = new ReportInfo();
			ri = (ReportInfo)list.get(0);
			ri.setNote(this.reportInfo.getNote());
			ri.setTitle(this.reportInfo.getTitle());
			try {
				this.getGeneralService().save(ri);
				this.setMsg("修改成功");
			} catch (EntityException e) {
				this.setMsg("对不起，好像出错了");
			}
		} else {
			this.setMsg("对不起，好像出错了");
			
		}
		this.setTogo("/entity/information/addYearReport.action");
		return "msg";
	}
}
