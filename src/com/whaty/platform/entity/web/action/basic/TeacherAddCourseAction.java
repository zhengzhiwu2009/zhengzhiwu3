package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.bean.CourseInfo;
import com.whaty.platform.entity.bean.TeacherInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class TeacherAddCourseAction extends MyBaseAction<CourseInfo> {
	
	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

		this.getGridConfig().setTitle("添加课程");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true, true, true, "TextField", false, 200);

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
		
		this.getGridConfig().addColumn(this.getText("提交人"), "add_user", false, false, true, "TextField", true, 25);
		
		if (capabilitySet.contains(this.servletPath + "_addCourse.action")) {
			this.getGridConfig().addMenuFunction(this.getText("添加"), "checkStatus");
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
		this.entityClass = CourseInfo.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/teacherAddCourse";
	}

	public void setBean(CourseInfo instance) {
		super.superSetBean(instance);

	}

	public CourseInfo getBean() {
		return super.superGetBean();
	}
	
	/**
	 * 讲师库列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("SELECT * FROM (SELECT stci.id, ec.name, ec1.name, ec2.name, ec3.name, stci.creater, stci.create_date  ");
			sqlBuffer.append("  from sg_train_course_info stci ");
			sqlBuffer.append("  join enum_const ec on ec.id = stci.flag_course_category ");
			sqlBuffer.append("  join enum_const ec1 on ec1.id = stci.flag_course_itemtype ");
			sqlBuffer.append("  join enum_const ec2 on ec2.id = stci.flag_content_property ");
			sqlBuffer.append("  join enum_const ec3 on ec3.id = stci.flag_suggest ");
			sqlBuffer.append(" where stci.id not in (select stc.course_id ");
			sqlBuffer.append("                         from stci. sg_teacher_course_info stc ");
			sqlBuffer.append("                        where stc.teacher_id = '" + id + "')");
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
					name = "FlagContentProperty.name";
				}
				/* 建议学习人群 */
				if (name.equals("enumConstByFlagSuggest.name")) {
					name = "FlagSuggest";
				}
				sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 系统编号 */
				if (temp.equals("enumConstByFlagCourseCategory.name")) {
					temp = "FlagCourseCategory";
				}
				/* 讲师姓名 */
				if (temp.equals("enumConstByFlagCourseItemType.name")) {
					temp = "FlagCourseItemType";
				}
				/* 工作部门 */
				if (temp.equals("enumConstByFlagContentProperty.name")) {
					temp = "FlagContentProperty";
				}
				if (temp.equals("enumConstByFlagSuggest.name")) {
					temp = "FlagSuggest";
				}
				/* 证件号码 */
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
