package com.whaty.platform.entity.web.action.basic;

import java.io.UnsupportedEncodingException;
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

import com.whaty.platform.entity.bean.CourseInfo;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.IndustryTeacherInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.TeacherInfo;
import com.whaty.platform.entity.bean.TeachingField;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class CourseShowTeacherAction extends
		MyBaseAction<IndustryTeacherInfo> {
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
	private String courseId;
	private IndustryTeacherInfo teacherInfo;
	private Date createDate;
	private String createOrganizationName;
	private String loginId;
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

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
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		boolean canAdd = false;
		boolean canUpdate = false;
		boolean canDelete = true;
		if(!getIsEquals()){
			canDelete = false;
		}
		this.getGridConfig().setCapability(canAdd, canDelete, canUpdate);

		this.getGridConfig().setTitle("推荐讲师");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("讲师姓名"), "name", true,
				true, true, "TextField", false, 200);

		this.getGridConfig().addColumn(this.getText("所在机构"), "organization",
				true, false, true, "TextField", true, 25);

		this.getGridConfig().addColumn(this.getText("工作部门"), "department_name",
				false, true, true, "TextField", true, 25);

		this.getGridConfig().addColumn(this.getText("证件号码"), "code", true,
				true, true, "");

		this.getGridConfig().addColumn(this.getText("联系电话"), "mobile", true,
				true, true, "TextField", false, 25);

		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", false,
				true, true, "TextField", true, 25);

		/*this.getGridConfig()
				.addColumn(this.getText("提交机构"), "create_organization", true,
						false, true, "TextField", true, 25);*/

		this.getGridConfig().addColumn(this.getText("讲师简介"), "description",
				false, true, false, "TextArea", false, 25);

		

		//if (capabilitySet.contains(this.servletPath + "_showCourse.action")) {
		

			

		//if (capabilitySet.contains(this.servletPath + "_update.action")) {
			this
					.getGridConfig()
					.addRenderFunction(
							this.getText("查看详情"),
							"<a href=\"/entity/basic/courseShowTeacher_showDetail.action?id=${value}\" >查看详情</a>",
							"id");

			this
					.getGridConfig()
					.addRenderFunction(
							this.getText("修改"),
							"<a href=\"/entity/basic/courseShowTeacher_updateDetail.action?id=${value}\" >修改</a>",
							"id");

		//}

		//if (capabilitySet.contains(this.servletPath + "_add.action")) {
			if(getIsEquals()){
			this
					.getGridConfig()
					.addMenuScript(this.getText("添加"),
							"{window.location='/entity/basic/courseShowTeacher_toAdd.action?id="+this.getId()+"';}");
			}
		//}

		/*if (capabilitySet.contains(this.servletPath + "_setValid.action")) {
			this.getGridConfig().addMenuFunction(this.getText("设为有效"),
					"FlagIsvalid.true");
		}

		if (capabilitySet.contains(this.servletPath + "_setVain.action")) {
			this.getGridConfig().addMenuFunction(this.getText("设为无效"),
					"FlagIsvalid.false");
		}

		if (capabilitySet.contains(this.servletPath + "_setProcessed.action")) {
			this.getGridConfig().addMenuFunction(this.getText("设为已处理"),
					"FlagUidispose.true");
		}

		if (capabilitySet.contains(this.servletPath + "_setUntreated.action")) {
			this.getGridConfig().addMenuFunction(this.getText("设为未处理"),
					"FlagUidispose.false");
		}*/
		this.getGridConfig().addMenuScript(this.getText("返回"),
		"{location.href='entity/basic/industryCourseResource.action'}");

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
		this.servletPath = "/entity/basic/courseShowTeacher";
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
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer
					.append("SELECT * FROM (SELECT iti.id, iti.name, iti.organization_name organization, iti.department_name, iti.code, ");
			sqlBuffer
					.append(" iti.mobile,ITI.Email,ITI.DESCRIPTION  ,");
			sqlBuffer.append("  iti.create_date  ");
			sqlBuffer.append(" from industry_teacher_info iti ");
			sqlBuffer.append(" where ITI.COURSE_ID ='"+this.getId()+"' ");
			sqlBuffer.append(" ) WHERE 1 = 1 ");
			/* 拼接查询条件 */
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1
						&& name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name
							.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}
				/* 是否有效 */
				if (name.equals("enumConstFlagIsValid.name")) {
					name = "FlagIsValid";
				}
				/* 处理状态 */
				if (name.equals("enumConstFlagOprationStatus.name")) {
					name = "FlagUidispose";
				}
				sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%"
						+ value + "%')");
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
				if (temp.equals("id")) {
					temp = "create_date";
				}
				if (this.getDir() != null
						&& this.getDir().equalsIgnoreCase("desc")) {
					sqlBuffer.append(" order by " + temp + " desc ");
				} else {
					sqlBuffer.append(" order by " + temp + " asc ");
				}
			} else {
				sqlBuffer.append(" order by create_date desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
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
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String createOrganization = "";
		PeEnterprise organization = null;
		EnumConst ec1 = null;
		EnumConst ec2 = null;
		EnumConst ec3 = null;
		
		IndustryTeacherInfo teacher = new IndustryTeacherInfo();
		teacher.setCourseId(id);
		teacher.setName(name);
		teacher.setOrganization_name(organization_name);
		teacher.setDepartmentName(department);
		teacher.setCode(code);
		teacher.setMobile(mobile);
		teacher.setEmail(email);
		teacher.setLoginId(us.getLoginId());
		teacher.setDescription(description);
		//teacher.setCreateOrganizationName(us.getPriEnterprises().get(0).getName());
		teacher.setTfc_name(fields);
		teacher.setCreateDate(new Date());
		if(!us.getUserLoginType().equals("3")){
			String sql =" update course_info set flag_operation_status ='Uidispose3' where id ='"+id+"'" ;
			try {
				this.getGeneralService().executeBySQL(sql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			this.getGeneralService().save(teacher);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toIndustryTeacher";
	}
	public String updateTeacher(){
		boolean flag =true;
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		EnumConst ec1 = null;
		EnumConst ec2 = null;
		EnumConst ec3 = null;		
		IndustryTeacherInfo teacher = new IndustryTeacherInfo();
		teacher.setId(id);
		teacher.setName(name);
		teacher.setOrganization_name(organization_name);
		teacher.setDepartmentName(department);
		teacher.setCode(code);
		teacher.setMobile(mobile);
		teacher.setEmail(email);
		teacher.setDescription(description);
		//teacher.setCreateOrganizationName(createOrganizationName);
		teacher.setTfc_name(fields);
		teacher.setCreateDate(createDate);
		teacher.setCourseId(courseId);
		teacher.setLoginId(loginId);
		try {
			flag =this.getGeneralService().getIsEqual(us.getLoginId(), this.loginId);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(!flag){
			this.setMsg("非本机构推荐信息，不能修改");
			this.setTogo("/entity/basic/courseShowTeacher.action?id=" + teacher.getCourseId());
			return "msg";
		}
		try {
			this.getGeneralService().update(teacher);
			if(!us.getUserLoginType().equals("3")){
			String sql ="update Course_Info set flag_operation_status ='Uidispose3' where id ='"+courseId+"' ";
			this.getGeneralService().executeBySQL(sql);
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.id=courseId;
		return "toIndustryTeacher";
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
				DetachedCriteria tempdc = DetachedCriteria
						.forClass(IndustryTeacherInfo.class);
				if (action.equals("FlagIsvalid.true")) {
					EnumConst enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagIsvalid", "1");
					tempdc.add(Restrictions.in("id", ids));
					List<IndustryTeacherInfo> teacherList = new ArrayList<IndustryTeacherInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<IndustryTeacherInfo> iterator = teacherList
							.iterator();
					while (iterator.hasNext()) {
						IndustryTeacherInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstFlagIsValid(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "推荐讲师设为有效";
				}

				if (action.equals("FlagIsvalid.false")) {
					EnumConst enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagIsvalid", "0");
					tempdc.add(Restrictions.in("id", ids));
					List<IndustryTeacherInfo> teacherList = new ArrayList<IndustryTeacherInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<IndustryTeacherInfo> iterator = teacherList
							.iterator();
					while (iterator.hasNext()) {
						IndustryTeacherInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstFlagIsValid(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "推荐讲师设为无效";
				}
				if (action.equals("FlagUidispose.true")) {
					EnumConst enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagUidispose", "1");
					tempdc.add(Restrictions.in("id", ids));
					List<IndustryTeacherInfo> teacherList = new ArrayList<IndustryTeacherInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<IndustryTeacherInfo> iterator = teacherList
							.iterator();
					while (iterator.hasNext()) {
						IndustryTeacherInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstFlagOperationStatus(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "推荐讲师设为已处理";
				}
				if (action.equals("FlagUidispose.false")) {
					EnumConst enumConst = this.getMyListService()
							.getEnumConstByNamespaceCode("FlagUidispose", "0");
					tempdc.add(Restrictions.in("id", ids));
					List<IndustryTeacherInfo> teacherList = new ArrayList<IndustryTeacherInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<IndustryTeacherInfo> iterator = teacherList
							.iterator();
					while (iterator.hasNext()) {
						IndustryTeacherInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstFlagOperationStatus(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "推荐讲师设为未处理";
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
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria criteria = DetachedCriteria
				.forClass(IndustryTeacherInfo.class);
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
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		toAdd();
		DetachedCriteria criteria = DetachedCriteria
				.forClass(IndustryTeacherInfo.class);
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

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public void checkBeforeDelete(List listids) throws EntityException{
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(!us.getUserLoginType().equals("3")){
			IndustryTeacherInfo courseInfo = (IndustryTeacherInfo) (this
				.getGeneralService().getByHQL(
						"FROM IndustryTeacherInfo WHERE id = '" + this.getIds().split(",")[0] + "'")
				.get(0));
		
		String sql =" update COURSE_INFO set flag_operation_status ='Uidispose3' where id ='"+courseInfo.getCourseId()+"'" ;
		this.getGeneralService().executeBySQL(sql);
		}
		/*String condition ="";
		for(int i= 0;i<listids.size(); i++){
			condition +="'"+listids.get(i)+"',";
		}
			condition = condition.substring(0, condition.length()-1);
		String hql =" from IndustryTeacherInfo where id in ("+condition+") ";
		
			List <IndustryTeacherInfo> courseInfo = this.getGeneralService().getByHQL(hql);
			if (courseInfo !=null && courseInfo.size() > 0) {
				for(IndustryTeacherInfo info :courseInfo){
					if(!us.getLoginId().equals(info.getLoginId())){
						throw new EntityException("不能删除非本机构的数据");
					}
				}
				
			}*/
	}
	/**判断该数据是不是本机构的*/
	public boolean getIsEquals(){
		boolean flag = true;
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			CourseInfo teacher2 = (CourseInfo) (this
				.getGeneralService().getByHQL(
						"FROM CourseInfo WHERE id = '" + id + "'")
				.get(0));
		
			 flag = this.getGeneralService().getIsEqual(us.getLoginId(), teacher2.getLoginId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
}
