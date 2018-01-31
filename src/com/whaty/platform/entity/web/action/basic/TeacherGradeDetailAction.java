package com.whaty.platform.entity.web.action.basic;

import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.bean.TeacherInfo;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class TeacherGradeDetailAction extends MyBaseAction<TeacherInfo> {
	private String year;
	private String teacherName;
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		boolean canAdd = false;
		boolean canUpdate = false;
		boolean canDelete = false;
		this.getGridConfig().setCapability(canAdd, canDelete, canUpdate);

		this.getGridConfig().setTitle("远程师资评价");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("讲师姓名"), "TEACHER_NAME", true, true, true, "TextField", false, 200);
		
		this.getGridConfig().addColumn(this.getText("课程名称"), "COURSE_NAME", true, true, true, "TextField", false, 200);

		this.getGridConfig().addColumn(this.getText("授课水平得分"), "FIR", false, true, true, "TextField", true, 25);

		this.getGridConfig().addColumn(this.getText("学员学习效果得分"), "SIX", false, true, true, "TextField", true, 25);

		this.getGridConfig().addColumn(this.getText("报名人次排名得分"), "REG_NUM_GRA", false, true, true, "");

		this.getGridConfig().addColumn(this.getText("总分数"), "SUM_GRA", false, true, true, "TextField", true, 25);
		
		if (capabilitySet.contains("/entity/basic/teacherResource_teacherInfo.action")) {
			this.getGridConfig().addRenderScript(this.getText("查看讲师详情"),
					"{return '<a href=\"/entity/basic/teacherResource_showTeacherInfo.action?id='+${value}+'\"  target=\"_blank\">查看详情</a>';}", "id");
		}
		
		if (capabilitySet.contains("/entity/teaching/peBzzCoursePubManager_view.action")) {
			this.getGridConfig().addRenderScript(this.getText("课程信息"),
					"{return '<a href=\"/entity/teaching/peBzzCoursePubManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}", "id");
		}
		
		this.getGridConfig().addMenuScript(this.getText("返回"), "{location.href='teacherGrade_toSearchTeacherGrade.action'};");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = TeacherInfo.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/teacherGradeDetail";
	}
	
	public Page list() {
		Page page = null;
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("SELECT * FROM (SELECT STDG.TEACHER_ID AS ID, STDG.TEACHER_NAME, ");
			sb.append("       STDG.COURSE_NAME, NVL(STDG.SKSP_GRADE, 0) FIR, ");
			sb.append("       NVL(STDG.XXXG_GRADE, 0) SIX, NVL(STDG.BMRC_GRADE, 0) REG_NUM_GRA, NVL(STDG.SUM_GRADE, 0) SUM_GRA ");
			sb.append("  FROM STATISTIC_TEACHER_DETAIL_GRADE STDG ");
			sb.append(" WHERE STDG.VOTE_DATE = '" + year + "'  ORDER BY STDG.TEACHER_NAME)A  WHERE 1=1");
			if(teacherName != null && !"".equals(teacherName)) {
				sb.append(" AND TEACHER_NAME LIKE '%" + URLDecoder.decode(teacherName, "UTF-8") + "%'");
			}
			/* 拼接查询条件 */
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
				sb.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("id")) {
					temp = "TEACHER_NAME";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sb.append(" order by " + temp + " desc ");
				} else {
					sb.append(" order by " + temp + " asc ");
				}
			} else {
				sb.append(" order by TEACHER_NAME desc");
			}
			page = this.getGeneralService().getByPageSQL(sb.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return page;
	}
	
	public String toSearchTeacherGradeDetail() {
		return "to_search_teacher_grade_detail";
	}
	
	public String searchTeacherGradeDetail() {
		if(year != null && !"".equals(year)) {
			ServletActionContext.getRequest().getSession().setAttribute("year", year);
		}
		if(teacherName != null && !"".equals(teacherName)) {
			ServletActionContext.getRequest().getSession().setAttribute("teacherName", teacherName);
		}
		return "search_teacher_grade_detail";
	}

}
