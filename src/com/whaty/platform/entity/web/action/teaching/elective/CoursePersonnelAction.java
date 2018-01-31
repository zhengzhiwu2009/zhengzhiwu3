package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class CoursePersonnelAction extends MyBaseAction{

	@Override
	public void initGrid() {
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setTitle("按人员查询       表中默认显示为年度数据");
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);	
		this.getGridConfig().addColumn(this.getText("年"), "year");
		this.getGridConfig().addColumn(this.getText("学员姓名"), "studentName");
		this.getGridConfig().addColumn(this.getText("所在机构"), "organid");
		this.getGridConfig().addColumn(this.getText("工作部门"), "dept");
		//this.getGridConfig().addColumn(this.getText("资格类型"), "zige",false);
		if (!"131AF5EC87836928E0530100007F9F54".equals(us.getSsoUser().getPePriRole().getId())) {
			ColumnConfig c_name = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name");
			c_name.setAdd(true);
			c_name.setSearch(true);
			c_name.setList(true);
			c_name.setComboSQL("SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagQualificationsType' and t.code not in('9','90','91')");
			this.getGridConfig().addColumn(c_name);
		}
		this.getGridConfig().addColumn(this.getText("开始学习课程数"), "startStudySum",false);
		this.getGridConfig().addColumn(this.getText("完成学习课程数"), "completeStudySum",false);
		this.getGridConfig().addColumn(this.getText("开始测验课程数"), "startTestSum",false);
		this.getGridConfig().addColumn(this.getText("完成测验课程数"), "completeTestSum",false);
		this.getGridConfig().addColumn(this.getText("获得学时数"), "getTimes",false);
		this.getGridConfig().addColumn(this.getText("必修学时数"), "equiredTimes",false);
		this.getGridConfig().addColumn(this.getText("选修学时数"), "optionTimes",false);
		
		//this.getGridConfig().addMenuFunction(this.getText("开启"), "FlagIsvalid.true");
		//this.getGridConfig().addMenuFunction(this.getText("关闭"), "FlagIsvalid.false");
	
	}
	public Page list() {
		Page page = null;
		String sql="";
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM (SELECT p.id as id, p.YEAR as year, ") ;
		sb.append(" P.A1 AS A1, ");
		sb.append(" P.A2 AS A2, ");
		sb.append(" P.A11 AS A11, ");
		sb.append(" P.A12 AS A12， ");
		sb.append(" P.A4 AS A4, ");
		sb.append(" P.A5 AS A5, ");
		sb.append(" P.A6 AS A6, ");
		sb.append(" P.A7 AS A7, ");
		sb.append(" P.A3 AS A3, ");
		sb.append(" P.A9 AS A9, ");
		sb.append(" P.A10 AS A10 ");
		
		sb.append("FROM REPORT_JT_LEN_PERSONNEL P where  (p.ORGANID ='"+us.getPriEnterprises().get(0).getId()+"'  OR p.ORGANID IN (SELECT ID FROM PE_ENTERPRISE WHERE FK_PARENT_ID = '"+us.getPriEnterprises().get(0).getId()+"'))  )AA where 1=1   ");
		
		//StringBuffer searchSql = new StringBuffer();
		Map params = this.getParamsMap();
		Iterator iterator = params.entrySet().iterator();
		do {
			if (!iterator.hasNext())
				break;
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
			String name = entry.getKey().toString();
			String value = ((String[]) entry.getValue())[0].toString();
			if (!name.startsWith("search__"))
				continue;
			if ("".equals(value))
				continue;
			if (name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")) {
				name = name.substring(name.lastIndexOf(".", name.lastIndexOf(".") - 1) + 1);
			} else {
				name = name.substring(8);
			}
			if (name.equals("year")) {
				sb.append(" AND UPPER(AA.year) LIKE '%" + value.toUpperCase() + "%' ");
			}
			if (name.equals("studentName")) {
				sb.append(" AND UPPER(AA.A1) LIKE '%" + value.toUpperCase() + "%' ");
			}
			if (name.equals("organid")) {
				sb.append(" AND UPPER(AA.A2) LIKE '%" + value.toUpperCase() + "%' ");
			}
			if (name.equals("dept")){
				sb.append(" AND UPPER(AA.A11) LIKE '%" + value.toUpperCase() + "%' ");
			}
			if (name.equals("enumConstByFlagQualificationsType.name")){
				sb.append(" AND UPPER(AA.A12) LIKE '%" + value.toUpperCase() + "%' ");
			}
		}while (true);
		String temp = this.getSort();
		if (temp != null && temp.indexOf(".") > 1) {
			if (temp.toLowerCase().startsWith("combobox_")) {
				temp = temp.substring(temp.indexOf(".") + 1);
			}
		}
		if (this.getSort() != null && temp != null) {
			/* 课程性质 */
			if (temp.equals("year")) {
				temp = "year";
			}
			if(temp.equals("studentName")){
				temp ="A1";
			}
			if(temp.equals("organid")){
				temp ="A2";
			}
			if(temp.equals("dept")){
				temp ="A11";
			}
			if(temp.equals("zige")){
				temp ="A12";
			}
			if(temp.equals("startStudySum")){
				temp ="A4";
			}
			if(temp.equals("completeStudySum")){
				temp ="A5";
			}
			if(temp.equals("startTestSum")){
				temp ="A6";
			}
			if(temp.equals("completeTestSum")){
				temp ="A7";
			}
			if(temp.equals("getTimes")){
				temp ="A3";
			}
			if(temp.equals("equiredTimes")){
				temp ="A9";
			}
			if(temp.equals("optionTimes")){
				temp ="A10";
			}
			
		}
		if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
			sb.append(" order by " + temp + " desc ");
		}

		else {
			sb.append(" order by " + temp + " asc ");
		}
		
		try {
			page = this.getGeneralService().getByPageSQL(sb.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/teaching/coursePersonnel";
		
	}
}
