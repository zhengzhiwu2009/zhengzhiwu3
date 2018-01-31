package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.IndustryTeacherInfo;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.TeachingField;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class IndustryTeacherResourceAction extends MyBaseAction<IndustryTeacherInfo> {
	private List<TeachingField> fieldList = null;
	private String id;
	private String name;
	private String department;
	private String code;
	private String mobile;
	private String email;
	private String description;
	private String fields;
	private String organization_name;
	private IndustryTeacherInfo teacherInfo;
	private Date createDate;
	private String createOrganizationName;

	public String getCreateOrganization() {
		return createOrganizationName;
	}

	public void setCreateOrganization(String createOrganizationName) {
		this.createOrganizationName = createOrganizationName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<TeachingField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<TeachingField> fieldList) {
		this.fieldList = fieldList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public IndustryTeacherInfo getTeacherInfo() {
		return teacherInfo;
	}

	public void setTeacherInfo(IndustryTeacherInfo teacherInfo) {
		this.teacherInfo = teacherInfo;
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

		this.getGridConfig().setTitle("推荐讲师");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("讲师姓名"), "name", true, true, true, "TextField", false, 200);

		this.getGridConfig().addColumn(this.getText("所在机构"), "organization", true, false, true, "TextField", true, 25);

		this.getGridConfig().addColumn(this.getText("工作部门"), "department_name", false, true, true, "TextField", true, 25);

		this.getGridConfig().addColumn(this.getText("证件号码"), "code", true, true, true, "");

		this.getGridConfig().addColumn(this.getText("联系电话"), "mobile", true, true, true, "TextField", false, 25);

		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", false, true, true, "TextField", true, 25);

		this.getGridConfig().addColumn(this.getText("提交机构"), "create_organization", true, false, true, "TextField", true, 25);

		this.getGridConfig().addColumn(this.getText("讲师简介"), "description", false, true, false, "TextArea", false, 25);
		if (us.getUserLoginType().equals("3")) {
			ColumnConfig columnConfigCategory = new ColumnConfig(this.getText("是否有效"), "enumConstFlagIsValid.name", true, false, true, "TextField", false, 100, "");
			String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid' and a.id<>'3x'";
			columnConfigCategory.setComboSQL(sql1);
			this.getGridConfig().addColumn(columnConfigCategory);
		} else {
			ColumnConfig columnConfigCategory20 = new ColumnConfig(this.getText("是否推荐到协会"), "enumConstByFlagIsRecommends.name", true, false, true, "TextField", false, 100, "");
			String sql20 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsRecommends'";
			columnConfigCategory20.setComboSQL(sql20);
			this.getGridConfig().addColumn(columnConfigCategory20);
		}

		ColumnConfig columnConfigCategory1 = new ColumnConfig(this.getText("处理状态"), "enumConstFlagOprationStatus.name", true, false, true, "TextField", false, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagOperationStatus'";
		columnConfigCategory1.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigCategory1);

		this.getGridConfig().addColumn(this.getText("可授课数量"), "course_count", false, false, true, "TextField", true, 25);

		if (capabilitySet.contains(this.servletPath + "_showCourse.action")) {
			/*
			 * this .getGridConfig() .addRenderScript( this.getText("查看课程"),
			 * "{return '<a
			 * href=\"/entity/basic/teacherShowCourse.action?teacherId='+${value}+'\"
			 * target=\"_blank\">查看课程</a>';}", "id");
			 */

			this.getGridConfig().addRenderFunction(this.getText("查看课程"), "<a href=\"/entity/basic/teacherShowCourses.action?id=${value}\" >查看课程</a>", "id");

		}

		if (capabilitySet.contains(this.servletPath + "_update.action")) {
			this.getGridConfig().addRenderFunction(this.getText("操作"), "<a href=\"/entity/basic/industryTeacherResource_showDetail.action?id=${value}\" >查看</a>", "id");

			this.getGridConfig().addRenderFunction(this.getText("操作"), "<a href=\"/entity/basic/industryTeacherResource_updateDetail.action?id=${value}\" >修改</a>", "id");

			// this
			// .getGridConfig()
			// .addRenderScript(
			// this.getText("操作"),
			// "{if(record.data['create_organization'] != 'SAC'){return
			// '&nbsp;&nbsp;&nbsp;&nbsp;<a
			// href=\"/entity/basic/industryTeacherResource_showDetail.action?id='+${value}+'\"
			// target=\"_blank\">查看</a>';} else return '<a
			// href=\"/entity/basic/industryTeacherResource_showDetail.action?id='+${value}+'\"
			// target=\"_blank\">查看</a>&nbsp;&nbsp;<a
			// href=\"/entity/basic/industryTeacherResource_toUpdate.action?id='+${value}+'\"
			// target=\"_blank\">修改</a>'}",
			// "id");
		}

		if (capabilitySet.contains(this.servletPath + "_add.action")) {
			this.getGridConfig().addMenuScript(this.getText("添加"), "{window.location='/entity/basic/industryTeacherResource_toAdd.action';}");
		}

		// if (capabilitySet.contains(this.servletPath + "_setValid.action")) {
		if (us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuFunction(this.getText("设为有效"), "FlagIsvalid.true");
		}
		// }

		if (us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuFunction(this.getText("设为无效"), "FlagIsvalid.false");
		}

		if (us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuFunction(this.getText("设为已处理"), "FlagUidispose.true");
		}

		if (us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuFunction(this.getText("设为未处理"), "FlagUidispose.false");
		}
		if (!us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuFunction(this.getText("推荐到协会"), "FlagIsRecommends.true");
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
		this.entityClass = IndustryTeacherInfo.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/industryTeacherResource";
	}

	public void setBean(IndustryTeacherInfo instance) {
		super.superSetBean(instance);

	}

	public IndustryTeacherInfo getBean() {
		return super.superGetBean();
	}

	/**
	 * 讲师库列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("SELECT * FROM (SELECT iti.id, iti.name, iti.organization_name organization, iti.department_name, iti.code, ");
			sqlBuffer.append(" iti.mobile,ITI.Email, iti.create_Organization_name create_organization ,ITI.DESCRIPTION,  ");
			if (us.getUserLoginType().equals("3")) {
				sqlBuffer.append(" ec1.name flagIsValid, ");
			} else {
				sqlBuffer.append(" ec3.name  FlagIsRecommends ,");
			}
			sqlBuffer.append("    ec2.name FlagUidispose, NVL(COURSE_COUNT,0) as  course_count, iti.create_date  ,ITI.COURSE_ID ,ec3.code enumCode ,login_id");
			sqlBuffer.append(" from industry_teacher_info iti ");
			sqlBuffer.append("  LEFT JOIN (SELECT TEACHER_ID,COUNT(*) COURSE_COUNT FROM COURSE_INFO GROUP BY TEACHER_ID ) COURSE ");
			sqlBuffer.append("  ON ITI.ID =COURSE.TEACHER_ID ");
			sqlBuffer.append(" left join pe_enterprise pe on iti.organization = pe.id ");

			sqlBuffer.append(" left join pe_enterprise pe1 on iti.create_organization = pe1.id ");
			sqlBuffer.append(" left join enum_const ec1 on iti.flag_is_valid = ec1.id ");
			sqlBuffer.append(" left join enum_const ec2 on iti.flag_operation_status = ec2.id ");
			sqlBuffer.append(" left join enum_const ec3 on iti.flag_is_recommend = ec3.id ");

			sqlBuffer.append(" ) WHERE 1 = 1  AND COURSE_ID IS NULL ");
			if (us.getUserLoginType().equals("3")) {
				sqlBuffer.append(" and enumCode ='1' ");
			} else {
				sqlBuffer.append(" and LOGIN_ID ='" + us.getLoginId() + "' ");
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
				/* 是否有效 */
				if (name.equals("enumConstFlagIsValid.name")) {
					name = "FlagIsValid";
				}
				if (name.equals("enumConstByFlagIsRecommends.name")) {
					name = "FlagIsRecommends";
				}
				/* 处理状态 */
				if (name.equals("enumConstFlagOprationStatus.name")) {
					name = "FlagUidispose";
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
				/* 是否有效 */
				if (temp.equals("enumConstFlagIsValid.name")) {
					temp = "FlagIsValid";
				}
				/* 处理状态 */
				if (temp.equals("enumConstFlagOprationStatus.name")) {
					temp = "FlagUidispose";
				}
				if (temp.equals("enumConstByFlagIsRecommends.name")) {
					temp = "FlagIsRecommends";
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

	public String toAdd() {
		String field_hql = "from TeachingField";
		try {
			fieldList = this.getGeneralService().getByHQL(field_hql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "toAdd";
	}

	/**
	 * 重写框架方法：添加数据
	 * 
	 * @author Lee
	 * @return
	 */
	public String addTeacher() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		String createOrganization = "";
		PeEnterprise organization = null;
		EnumConst ec1 = null;
		EnumConst ec2 = null;
		EnumConst ec3 = null;
		if ("3".equals(us.getUserLoginType())) {
			createOrganization = "SAC";
			ec1 = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "1");
			ec2 = this.getEnumConstService().getByNamespaceCode("FlagOperationStatus", "1");
			ec3 = this.getEnumConstService().getByNamespaceCode("FlagIsRecommends", "1");
		} else {
			ec1 = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "1");
			ec2 = this.getEnumConstService().getByNamespaceCode("FlagOperationStatus", "0");
			ec3 = this.getEnumConstService().getByNamespaceCode("FlagIsRecommends", "0");
			createOrganization = us.getPriEnterprises().get(0).getName();
		}
		String sql =" select 1 from INDUSTRY_TEACHER_INFO where code ='"+code+"' and create_organization_name ='"+createOrganization+"' and login_id ='"+us.getLoginId()+"' and course_id is null ";
		try {
			List teacherList = this.getGeneralService().getBySQL(sql);
			if(teacherList !=null && teacherList.size()>0){
				this.setMsg("同机构推荐讲师证件号码不能相同");
				this.setTogo("/entity/basic/industryTeacherResource.action");
				return "msg";
			}
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IndustryTeacherInfo teacher = new IndustryTeacherInfo();
		teacher.setName(name);
		teacher.setOrganization_name(organization_name);
		teacher.setDepartmentName(department);
		teacher.setCode(code);
		teacher.setMobile(mobile);
		teacher.setEmail(email);
		teacher.setDescription(description);
		teacher.setTfc_name(fields);
		teacher.setCreateOrganizationName(createOrganization);
		teacher.setLoginId(us.getLoginId());
		teacher.setEnumConstFlagIsValid(ec1);
		teacher.setEnumConstFlagOperationStatus(ec2);
		teacher.setEnumConstFlagIsRecommends(ec3);
		teacher.setCreateDate(new Date());
		try {
			this.getGeneralService().save(teacher);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toIndustryTeacher";
	}

	public String updateTeacher() {
		boolean flag = true;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String createOrganization ="";
		if(us.getUserLoginType().equals("3")){
			createOrganization ="SAC";
		}else{
			createOrganization = us.getPriEnterprises().get(0).getName();
		}
		String teacherSql =" select * from INDUSTRY_TEACHER_INFO where code ='"+code+"' and login_id ='"+us.getLoginId()+"' and   create_organization_name ='"+createOrganization+"' and id <> '"+id+"' ";
		try {
			List teacherList = this.getGeneralService().getBySQL(teacherSql);
			if(teacherList !=null && teacherList.size()>0){
				this.setMsg("同机构推荐讲师证件号码不能相同");
				this.setTogo("/entity/basic/industryTeacherResource.action");
				return "msg";
			}
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			IndustryTeacherInfo teacher2 = (IndustryTeacherInfo) (this.getGeneralService().getByHQL("FROM IndustryTeacherInfo WHERE id = '" + id + "'").get(0));
			try {
				flag = this.getGeneralService().getIsEqual(us.getLoginId(), teacher2.getLoginId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!flag) {
				this.setMsg("非本机构推荐信息，不能修改");
				this.setTogo("/entity/basic/industryTeacherResource.action");
				return "msg";
			}
			teacher2.setName(name);
			teacher2.setOrganization_name(organization_name);
			teacher2.setDepartmentName(department);
			teacher2.setCode(code);
			teacher2.setMobile(mobile);
			teacher2.setEmail(email);
			teacher2.setDescription(description);
			teacher2.setTfc_name(fields);
			this.getGeneralService().update(teacher2);
			if(!us.getUserLoginType().equals("3")){
			String sql = " update INDUSTRY_TEACHER_INFO set flag_operation_status ='Uidispose3' where id ='"
						+ id + "'";
				this.getGeneralService().executeBySQL(sql);
			}
			
			this.setMsg("操作成功！");
			this.setTogo("/entity/basic/industryTeacherResource.action");
			return "msg";
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			this.setMsg("操作失败！");
			this.setTogo("javascript:history.go(-1)");
			return "msg";
		}
	}

	public Map updateColumn() {
		String msg = "";
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {

			String[] ids = getIds().split(",");
			List idList = new ArrayList();

			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			String action = this.getColumn();
			try {
				DetachedCriteria tempdc = DetachedCriteria.forClass(IndustryTeacherInfo.class);
				if (action.equals("FlagIsvalid.true")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
					tempdc.add(Restrictions.in("id", ids));
					List<IndustryTeacherInfo> teacherList = new ArrayList<IndustryTeacherInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<IndustryTeacherInfo> iterator = teacherList.iterator();
					while (iterator.hasNext()) {
						IndustryTeacherInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstFlagIsValid(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "推荐讲师设为有效";
				}

				if (action.equals("FlagIsvalid.false")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
					tempdc.add(Restrictions.in("id", ids));
					List<IndustryTeacherInfo> teacherList = new ArrayList<IndustryTeacherInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<IndustryTeacherInfo> iterator = teacherList.iterator();
					while (iterator.hasNext()) {
						IndustryTeacherInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstFlagIsValid(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "推荐讲师设为无效";
				}
				if (action.equals("FlagUidispose.true")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagOperationStatus", "1");
					tempdc.add(Restrictions.in("id", ids));
					List<IndustryTeacherInfo> teacherList = new ArrayList<IndustryTeacherInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<IndustryTeacherInfo> iterator = teacherList.iterator();
					while (iterator.hasNext()) {
						IndustryTeacherInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstFlagOperationStatus(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "推荐讲师设为已处理";
				}
				if (action.equals("FlagUidispose.false")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagOperationStatus", "0");
					tempdc.add(Restrictions.in("id", ids));
					List<IndustryTeacherInfo> teacherList = new ArrayList<IndustryTeacherInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<IndustryTeacherInfo> iterator = teacherList.iterator();
					while (iterator.hasNext()) {
						IndustryTeacherInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstFlagOperationStatus(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "推荐讲师设为未处理";
				}
				if (action.equals("FlagIsRecommends.true")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsRecommends", "1");
					tempdc.add(Restrictions.in("id", ids));
					List<IndustryTeacherInfo> teacherList = new ArrayList<IndustryTeacherInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<IndustryTeacherInfo> iterator = teacherList.iterator();
					while (iterator.hasNext()) {
						IndustryTeacherInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstFlagIsRecommends(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "推荐到协会";
				}

			} catch (EntityException e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", msg + "共有" + ids.length + "条记录操作成功");

		}
		return map;
	}

	public String showDetail() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria criteria = DetachedCriteria.forClass(IndustryTeacherInfo.class);
		criteria.add(Restrictions.eq("id", id));
		try {
			this.setTeacherInfo(getGeneralService().getTeacherInfo(criteria));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "showDetail";
	}

	public String updateDetail() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		toAdd();
		DetachedCriteria criteria = DetachedCriteria.forClass(IndustryTeacherInfo.class);
		criteria.add(Restrictions.eq("id", id));
		try {
			this.setTeacherInfo(getGeneralService().getTeacherInfo(criteria));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "updateDetail";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganization_name() {
		return organization_name;
	}

	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}

}
