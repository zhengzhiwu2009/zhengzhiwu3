package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.TeacherInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class CourseResourceAction extends MyBaseAction<PeBzzTchCourse> {
	/**
	 * 初始化列表
	 * 
	 * @author Lee
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		boolean canAdd = false;
		boolean canUpdate = false;
		boolean canDelete = false;
		this.getGridConfig().setCapability(canAdd, canDelete, canUpdate);

		this.getGridConfig().setTitle("课程库管理");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true, true, true, Const.coursecode_for_extjs);

		ColumnConfig columnConfigCategory = new ColumnConfig(this.getText("业务分类"), "enumConstByFlagCourseCategory.name", true, true, true, "TextField", false, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
		columnConfigCategory.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigCategory);
		
		ColumnConfig columnCourseItemType = new ColumnConfig(this.getText("大纲分类"), "enumConstByFlagCourseItemType.name", true, true, true, "TextField", false, 100, "");
		String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
		columnCourseItemType.setComboSQL(sql7);
		this.getGridConfig().addColumn(columnCourseItemType);
		
		ColumnConfig columnContentProperty = new ColumnConfig(this.getText("内容属性分类"), "enumConstByFlagContentProperty.name", true, true, true, "TextField", false, 100, "");
		String sql9 = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnContentProperty.setComboSQL(sql9);
		this.getGridConfig().addColumn(columnContentProperty);
		
		ColumnConfig ccFlagSuggest = new ColumnConfig(this.getText("建议学习人群"), "enumConstByFlagSuggest.name", true, true, true, "TextField", false, 100, "");
		String sqlFlagSuggest = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
		ccFlagSuggest.setComboSQL(sqlFlagSuggest);
		this.getGridConfig().addColumn(ccFlagSuggest);

		this.getGridConfig().addColumn(this.getText("提交人"), "creater", false, true, true, "");
		
		if (capabilitySet.contains(this.servletPath + "_addCourse.action")) {
			this.getGridConfig().addRenderScript(this.getText("添加课程"),
					"{return '<a href=\"/entity/teaching/peBzzCourseAssManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}", "id");
		}
		
		if (capabilitySet.contains(this.servletPath + "_showCourse.action")) {
			this.getGridConfig().addRenderScript(this.getText("查看课程"),
					"{return '<a href=\"/entity/teaching/peBzzCourseAssManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}", "id");
		}
		
		if (capabilitySet.contains(this.servletPath + "_teacherInfo.action")) {
			this.getGridConfig().addRenderScript(this.getText("详细信息"),
					"{return '<a href=\"/entity/teaching/peBzzCourseAssManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}", "id");
		}

		Map<String, String> defaultValueMap = new HashMap<String, String>();
		defaultValueMap.put("passRole", "80");
		defaultValueMap.put("examTimesAllow", "5");
		defaultValueMap.put("studyDates", "90");
		this.setDefaultValueMap(defaultValueMap);
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzTchCourse.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/courseResource";
	}

	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);

	}

	public PeBzzTchCourse getBean() {
		return super.superGetBean();
	}
	
	/**
	 * 课程库列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("SELECT * FROM (SELECT pbtc.id, pbtc.name, ec.name FlagCourseCategory, ec1.name FlagCourseItemType, ");
			sqlBuffer.append("	ec2.name FlagContentProperty, ec3.name FlagSuggest, pbtc.creater, pbtc.create_date ");
			sqlBuffer.append("  from pe_bzz_tch_course pbtc ");
			sqlBuffer.append("  join enum_const ec on pbtc.flag_coursecategory = ec.id ");
			sqlBuffer.append("	join enum_const ec1 on pbtc.flag_course_item_type = ec1.id ");
			sqlBuffer.append("	join enum_const ec2 on pbtc.flag_content_property = ec2.id ");
			sqlBuffer.append("	left join enum_const ec3 on pbtc.flag_suggest = ec3.id ");
			sqlBuffer.append(" ) WHERE 1 = 1 ");
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
				/* 业务分类 */
				if (name.equals("enumConstByFlagCourseCategory.name")) {
					name = "FlagCourseCategory";
				}
				/* 大纲分类 */
				if (name.equals("enumConstByFlagCourseItemType.name")) {
					name = "FlagCourseItemType";
				}
				/* 内容属性分类 */
				if (name.equals("enumConstByFlagContentProperty.name")) {
					name = "FlagContentProperty";
				}
				/* 建议学习人群 */
				if (name.equals("enumConstByFlagSuggest.name")) {
					name = "FlagSuggest";
				}
				if (!name.equals("enumConstByFlagSuggest.name")) {
					sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
				}
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 业务分类 */
				if (temp.equals("enumConstByFlagCourseCategory.name")) {
					temp = "FlagCourseCategory";
				}
				/* 大纲分类 */
				if (temp.equals("enumConstByFlagCourseItemType.name")) {
					temp = "FlagCourseItemType";
				}
				/* 内容属性分类 */
				if (temp.equals("enumConstByFlagContentProperty.name")) {
					temp = "FlagContentProperty";
				}
				/* 建议学习人群 */
				if (temp.equals("enumConstByFlagSuggest.name")) {
					temp = "FlagSuggest";
				}
				if (temp.equals("id")) {
					temp = "create_date";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sqlBuffer.append(" order by " + temp + " desc ");
				} else {
					sqlBuffer.append(" order by " + temp + " asc ");
				}
			} else {
				sqlBuffer.append(" order by create_date desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
}
