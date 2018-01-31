package com.whaty.platform.entity.web.action.basic;

import java.io.File;
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
import com.whaty.platform.entity.bean.IndustryTeacherInfo;
import com.whaty.platform.entity.bean.PeBzzPici;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class TeacherShowCoursesAction extends MyBaseAction<CourseInfo> {

	private String id;

	private CourseInfo courseInfo;
	
	private String loginId ;
	
	private String parentId;
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		boolean canAdd = true;
		boolean canUpdate = true;
		boolean canDelete = true;
		if(!getIsEquals()){
			canAdd = false;
		}
		if(!getIsEquals()){
			canDelete = false;
		}
		if(!getIsEquals()){
			canUpdate =false;
		}
		this.getGridConfig().setCapability(canAdd, canDelete, canUpdate);
		this.getGridConfig().setTitle("推荐课程");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("课程名称"), "courseName",
				true, true, true, "TextField", false, 200);
		ColumnConfig columnConfigCategory = new ColumnConfig(this
				.getText("业务分类"), "enumConstByFlagCourseCategory.name", true,
				true, true, "TextField", false, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
		columnConfigCategory.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigCategory);

		ColumnConfig columnCourseItemType = new ColumnConfig(this
				.getText("大纲分类"), "enumConstByFlagCourseItemType.name", true,
				true, true, "TextField", false, 100, "");
		String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
		columnCourseItemType.setComboSQL(sql7);
		this.getGridConfig().addColumn(columnCourseItemType);

		ColumnConfig columnContentProperty = new ColumnConfig(this
				.getText("内容属性分类"), "enumConstByFlagContentProperty.name",
				true, true, true, "TextField", false, 100, "");
		String sql9 = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnContentProperty.setComboSQL(sql9);
		this.getGridConfig().addColumn(columnContentProperty);

		this.getGridConfig().addColumn(this.getText("课程时长(分钟)"), "times", false,
				true, true, "TextField", false, 200);
		this.getGridConfig().addColumn(this.getText("课程简介"), "description",
				false, true, false, "TextArea", false, 500);
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("课程简介"),
						"<a href=\"/entity/basic/teacherShowCourses_courseView.action?id=${value}\" >课程简介</a>",
						"id");

		/*
		 * this .getGridConfig() .addRenderFunction( this.getText("修改"), "<a
		 * href=\"/entity/basic/teacherShowCourse.action?id=${value}\" >修改</a>",
		 * "id");
		 */
		this.getGridConfig().addMenuScript(this.getText("返回"),
				"{history.back()}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = CourseInfo.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/teacherShowCourses";

	}

	public void setBean(CourseInfo instance) {
		super.superSetBean(instance);

	}

	public CourseInfo getBean() {
		return super.superGetBean();
	}

	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("  SELECT * ");
			sqlBuffer.append("  FROM (SELECT INFO.ID          ID, ");
			sqlBuffer.append("  INFO.COURSE_NAME NAME,  ");
			sqlBuffer.append("  CONST1.NAME      COURSE_TYPE, ");
			sqlBuffer.append("  CONST2.NAME      COURSE_ITEM_TYPE, ");
			sqlBuffer.append("  CONST3.NAME      COURSE_PRO, ");
			sqlBuffer.append("  INFO.TIMES       TIMES, ");
			sqlBuffer.append("  CONST4.NAME      IDVALID,");
			sqlBuffer.append("  CONST5.NAME      OPERATION_STATUS, ");
			sqlBuffer.append("  CONST6.NAME      IS_RECOMMEND, ");
			sqlBuffer.append(" 	INFO.CREATE_DATE      CREATE_DATE ,");
			sqlBuffer.append(" 	INFO.TEACHER_ID      TEACHER_ID ");
			sqlBuffer.append("  FROM COURSE_INFO INFO ");
			sqlBuffer
					.append(" 	LEFT JOIN ENUM_CONST CONST1 ON INFO.FLAG_COURSE_TYPE = CONST1.ID  ");
			sqlBuffer
					.append("  LEFT JOIN ENUM_CONST CONST2 ON INFO.FLAG_COURSE_ITEM_TYPE =   CONST2.ID ");
			sqlBuffer
					.append("  LEFT JOIN ENUM_CONST CONST3 ON INFO.FLAG_CONTENT_PRO = CONST3.ID  ");
			sqlBuffer
					.append("  LEFT JOIN ENUM_CONST CONST4 ON INFO.FLAG_ISVALID = CONST4.ID  ");
			sqlBuffer
					.append("  LEFT JOIN ENUM_CONST CONST5 ON INFO.FLAG_OPERATION_STATUS =  CONST5.ID ");
			sqlBuffer
					.append("  LEFT JOIN ENUM_CONST CONST6 ON INFO.FLAG_IS_RECOMMEND = CONST6.ID ");
			sqlBuffer.append("   ) WHERE 1=1   AND TEACHER_ID ='" + id + "' ");

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

				if (name.equals("courseName")) {
					sqlBuffer.append(" and name like upper('%" + value
							+ "%')   ");
				}
				if (name.equals("enumConstByFlagCourseCategory.name")) {
					sqlBuffer.append(" and COURSE_TYPE like upper('%" + value
							+ "%') ");
				}
				if (name.equals("enumConstByFlagCourseItemType.name")) {
					sqlBuffer.append(" and COURSE_ITEM_TYPE like upper('%"
							+ value + "%') ");
				}
				if (name.equals("enumConstByFlagContentProperty.name")) {
					sqlBuffer.append(" and COURSE_PRO like upper('%" + value
							+ "%') ");
				}

			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("courseName")) {
					temp = "NAME";
				}
				if (temp.equals("enumConstByFlagCourseCategory.name")) {
					temp = "COURSE_TYPE";
				}
				if (temp.equals("enumConstByFlagCourseItemType.name")) {
					temp = "COURSE_ITEM_TYPE";
				}
				if (temp.equals("enumConstByFlagContentProperty.name")) {
					temp = "COURSE_PRO";
				}
				/* 是否有效 */				
				if (temp.equals("enumConstByFlagIsValid.name")) {
					temp = "IDVALID";
				}
				/* 处理状态 */
				if (temp.equals("enumConstByFlagOperationStatus.name")) {
					temp = "OPERATION_STATUS";
				}
				if (temp.equals("createOrganizationName")) {// 加提交机构  lzh
					temp = "organ_name";
				}
				if (temp.equals("course_count")) {// 加提交机构  lzh
					temp =  "TOTAL";
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

	public String courseView() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria criteria = DetachedCriteria.forClass(CourseInfo.class);
		criteria.add(Restrictions.eq("id", id));
		try {
			this.setCourseInfo(getGeneralService().getCourseInfo(criteria));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "courseView";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(CourseInfo.class);
		// dc.createCriteria("enumConstByFlagOffline",
		// "enumConstByFlagOffline");
		// dc.createCriteria("enumConstByFlagCourseType",
		// "enumConstByFlagCourseType");
		dc.createCriteria("enumConstByFlagCourseCategory",
				"enumConstByFlagCourseCategory", DetachedCriteria.LEFT_JOIN);
		// dc.createCriteria("enumConstByFlagIsvisitAfterPass",
		// "enumConstByFlagIsvisitAfterPass", DetachedCriteria.LEFT_JOIN);
		// dc.createCriteria("enumConstByFlagIsFree", "enumConstByFlagIsFree",
		// DetachedCriteria.LEFT_JOIN);
		// dc.createCriteria("enumConstByFlagIsRecommend",
		// "enumConstByFlagIsRecommend", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCourseItemType",
				"enumConstByFlagCourseItemType", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagContentProperty",
				"enumConstByFlagContentProperty", DetachedCriteria.LEFT_JOIN);
		// dc.createCriteria("enumConstByFlagCheckStatus",
		// "enumConstByFlagCheckStatus", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(dc,
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
			String id = this.getBean().getId();

			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this
						.getGridConfig().getListColumnConfig()));
				Container o = new Container();
				o.setBean(page.getItems().get(0));
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount", 1);
				map.put("models", list);
			}
			this.setJsonString(JsonUtil.toJSONString(map));
			JsonUtil.setDateformat("yyyy-MM-dd");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}

	/**
	 * 重写添加方法
	 * 
	 * @throws EntityException
	 */
	public void checkBeforeAdd() throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getBean().setTeacherId(id);
		this.getBean().setCreater(us.getSsoUser().getId());
		this.getBean().setCreateDate(new Date());
		this.getBean().setLoginId(us.getLoginId());
		if(!us.getUserLoginType().equals("3")){//协会端处理时都是已处理的问题  Lzh 
			String sql =" update INDUSTRY_TEACHER_INFO set flag_operation_status ='Uidispose3' where id ='"+id+"'" ;
			this.getGeneralService().executeBySQL(sql);
		}
	}

	/** 重写修改方法 */
	/*public void checkBeforeUpdate() throws EntityException {
		boolean flag = true;
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		CourseInfo courseInfo = (CourseInfo) (this
				.getGeneralService().getByHQL(
						"FROM CourseInfo WHERE id = '" + this.getBean().getId() + "'")
				.get(0));
		try {
			 flag = this.getGeneralService().getIsEqual(us.getLoginId(), courseInfo.getLoginId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!flag){
			//this.setMsg("非本机构数据，不能修改");
			
			//this.setTogo("/entity/basic/teacherShowCourse.action");
			throw new EntityException("非本机构推荐信息，不能修改");
		}
	}*/

	public void checkBeforeDelete(List listids) throws EntityException{
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(!us.getUserLoginType().equals("3")){
			CourseInfo courseInfo = (CourseInfo) (this
				.getGeneralService().getByHQL(
						"FROM CourseInfo WHERE id = '" + this.getIds().split(",")[0] + "'")
				.get(0));
		
		String sql =" update INDUSTRY_TEACHER_INFO set flag_operation_status ='Uidispose3' where id ='"+courseInfo.getTeacherId()+"'" ;
		this.getGeneralService().executeBySQL(sql);
		}
	}
	public void checkBeforeUpdate()throws EntityException{
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String hql = " from CourseInfo where id ='" + this.getBean().getId() + "' ";
		String parentId = "";
		try {
			CourseInfo courseInfo = (CourseInfo) this.getGeneralService().getByHQL(hql).get(0);
			parentId = courseInfo.getTeacherId();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!us.getUserLoginType().equals("3")){
		String sql =" update INDUSTRY_TEACHER_INFO set flag_operation_status ='Uidispose3' where id ='"+parentId+"'" ;
		this.getGeneralService().executeBySQL(sql);
		}
	}
	public boolean getIsEquals(){
		boolean flag = true;
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		try {
		IndustryTeacherInfo teacher2 = (IndustryTeacherInfo) (this
				.getGeneralService().getByHQL(
						"FROM IndustryTeacherInfo WHERE id = '" + id + "'")
				.get(0));
		
			 flag = this.getGeneralService().getIsEqual(us.getLoginId(), teacher2.getLoginId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

}
