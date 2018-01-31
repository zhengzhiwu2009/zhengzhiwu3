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

import com.whaty.platform.entity.bean.CourseInfo;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class IndustryCourseResourceAction extends MyBaseAction<CourseInfo> {

	private String id;

	private CourseInfo courseInfo;

	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		boolean canAdd = true;
		boolean canUpdate = true;
		boolean canDelete = false;
		this.getGridConfig().setCapability(canAdd, canDelete, canUpdate);
		this.getGridConfig().setTitle("推荐课程");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("课程名称"), "courseName", true, true, true, "TextField", false, 200);
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

		this.getGridConfig().addColumn(this.getText("课程时长(分钟)"), "times", false, true, true, "TextField", false, 200);
		if (us.getUserLoginType().equals("3")) {
			ColumnConfig columnConfigCategory10 = new ColumnConfig(this.getText("是否有效"), "enumConstByFlagIsValid.name", true, false, true, "TextField", false, 100, "");
			String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid' and a.id<>'3x'";
			columnConfigCategory10.setComboSQL(sql1);
			this.getGridConfig().addColumn(columnConfigCategory10);
		}
		if (!us.getUserLoginType().equals("3")) {
			ColumnConfig columnConfigCategory20 = new ColumnConfig(this.getText("是否推荐到协会"), "enumConstByFlagIsRecommends.name", true, false, true, "TextField", false, 100, "");
			String sql20 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsRecommends'";
			columnConfigCategory20.setComboSQL(sql20);
			this.getGridConfig().addColumn(columnConfigCategory20);
		}
		ColumnConfig columnConfigCategory11 = new ColumnConfig(this.getText("处理状态"), "enumConstByFlagOperationStatus.name", true, false, true, "TextField", false, 100, "");
		String sqlStatus = "select a.id ,a.name from enum_const a where a.namespace='FlagOperationStatus'";
		columnConfigCategory11.setComboSQL(sqlStatus);
		this.getGridConfig().addColumn(columnConfigCategory11);

		this.getGridConfig().addColumn(this.getText("授课讲师数"), "course_count", false, false, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("课程简介"), "description", false, true, false, "TextArea", true, 500);
		this.getGridConfig().addColumn(this.getText("提交机构"), "createOrganizationName", true, false, true, "TextField", true, 25);// 加提交机构
		// lzh
		this.getGridConfig().addRenderFunction(this.getText("课程简介"), "<a href=\"/entity/basic/industryCourseResource_courseView.action?id=${value}\" >课程简介</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看师资"), "<a href=\"/entity/basic/courseShowTeacher.action?id=${value}\" >查看师资</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看"), "<a href=\"/entity/basic/industryCourseResource_showDetail.action?id=${value}\" >查看</a>", "id");
		// if (capabilitySet.contains(this.servletPath + "_setValid.action")) {
		if (us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuFunction(this.getText("设为有效"), "FlagIsvalid.true");
		}
		// }

		// if (capabilitySet.contains(this.servletPath + "_setVain.action")) {
		if (us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuFunction(this.getText("设为无效"), "FlagIsvalid.false");
		}
		// }

		// if (capabilitySet.contains(this.servletPath +
		// "_setProcessed.action")) {
		if (us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuFunction(this.getText("设为已处理"), "FlagUidispose.true");
		}
		// }

		// if (capabilitySet.contains(this.servletPath +
		// "_setUntreated.action")) {
		if (us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuFunction(this.getText("设为未处理"), "FlagUidispose.false");
		}
		if (!us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuFunction(this.getText("推荐到协会"), "FlagIsRecommends.true");
		}
		// }

	}

	@Override
	public void setEntityClass() {
		this.entityClass = CourseInfo.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/industryCourseResource";

	}

	public void setBean(CourseInfo instance) {
		super.superSetBean(instance);

	}

	public CourseInfo getBean() {
		return super.superGetBean();
	}

	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("  SELECT * ");
			sqlBuffer.append("  FROM (SELECT INFO.ID          ID, ");
			sqlBuffer.append("  INFO.COURSE_NAME COURSENAME ,  ");
			sqlBuffer.append("  CONST1.NAME      COURSE_TYPE, ");
			sqlBuffer.append("  CONST2.NAME      COURSE_ITEM_TYPE, ");
			sqlBuffer.append("  CONST3.NAME      COURSE_PRO, ");
			sqlBuffer.append("  INFO.TIMES       TIMES, ");
			if (us.getUserLoginType().equals("3")) {
				sqlBuffer.append("  CONST4.NAME      IDVALID,");
			} else {
				sqlBuffer.append("  CONST6.NAME      isrecommend,");
			}
			sqlBuffer.append("  CONST5.NAME      OPERATION_STATUS, ");
			sqlBuffer.append("  NVL(TEACHERS.TOTAL,0)      TOTAL, ");
			sqlBuffer.append(" 	INFO.DESCRIPTION DESCRIPTION ,  info.organ_name, ");// 加提交机构
			// lzh
			sqlBuffer.append(" 	INFO.CREATE_DATE CREATE_DATE ,  ");
			sqlBuffer.append(" 	INFO.TEACHER_ID, ");
			sqlBuffer.append("  INFO.LOGIN_ID, ");
			sqlBuffer.append("  CONST6.CODE CODE ");
			sqlBuffer.append("  FROM COURSE_INFO INFO ");
			sqlBuffer.append("  LEFT JOIN (SELECT COURSE_ID, COUNT(*) TOTAL FROM INDUSTRY_TEACHER_INFO TEACHER GROUP BY COURSE_ID  ) TEACHERS ");
			sqlBuffer.append("  ON INFO.ID = TEACHERS.COURSE_ID ");
			sqlBuffer.append(" 	LEFT JOIN ENUM_CONST CONST1 ON INFO.FLAG_COURSE_TYPE = CONST1.ID  ");
			sqlBuffer.append("  LEFT JOIN ENUM_CONST CONST2 ON INFO.FLAG_COURSE_ITEM_TYPE =   CONST2.ID ");
			sqlBuffer.append("  LEFT JOIN ENUM_CONST CONST3 ON INFO.FLAG_CONTENT_PRO = CONST3.ID  ");
			sqlBuffer.append("  LEFT JOIN ENUM_CONST CONST4 ON INFO.FLAG_ISVALID = CONST4.ID  ");
			sqlBuffer.append("  LEFT JOIN ENUM_CONST CONST5 ON INFO.FLAG_OPERATION_STATUS =  CONST5.ID ");
			sqlBuffer.append("  LEFT JOIN ENUM_CONST CONST6 ON INFO.FLAG_IS_RECOMMEND = CONST6.ID ");
			sqlBuffer.append("   ) WHERE 1=1   AND TEACHER_ID IS NULL ");
			if (us.getUserLoginType().equals("3")) {
				sqlBuffer.append(" and CODE ='1' ");
			} else {
				sqlBuffer.append(" AND LOGIN_ID ='" + us.getLoginId() + "' ");
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

				if (name.equals("courseName")) {
					sqlBuffer.append(" and COURSENAME like upper('%" + value + "%')   ");
				}
				if (name.equals("enumConstByFlagCourseCategory.name")) {
					sqlBuffer.append(" and COURSE_TYPE like upper('%" + value + "%') ");
				}
				if (name.equals("enumConstByFlagCourseItemType.name")) {
					sqlBuffer.append(" and COURSE_ITEM_TYPE like upper('%" + value + "%') ");
				}
				if (name.equals("enumConstByFlagContentProperty.name")) {
					sqlBuffer.append(" and COURSE_PRO like upper('%" + value + "%') ");
				}
				if (name.equals("enumConstFlagIsValid.name")) {
					sqlBuffer.append(" and IDVALID like upper('%" + value + "%') ");
				}
				/* 处理状态 */
				if (name.equals("enumConstByFlagOperationStatus.name")) {
					sqlBuffer.append(" and OPERATION_STATUS like upper('%" + value + "%') ");
				}
				if (name.equals("enumConstByFlagIsRecommends.name")) {
					sqlBuffer.append(" and isrecommend like upper('%" + value + "%') ");
				}
				if (name.equals("createOrganizationName")) {// 加提交机构 lzh
					sqlBuffer.append(" and organ_name like upper('%" + value + "%') ");
				}

			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
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
				if (temp.equals("createOrganizationName")) {// 加提交机构 lzh
					temp = "organ_name";
				}
				if (temp.equals("course_count")) {// 加提交机构 lzh
					temp = "TOTAL";
				}
				if(temp.equals("times")){
					temp = " to_number(times) ";
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

	public String courseView() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
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

	/** 查看课程详情信息 */
	public String showDetail() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria criteria = DetachedCriteria.forClass(CourseInfo.class);
		criteria.add(Restrictions.eq("id", id));
		try {
			this.setCourseInfo(getGeneralService().getCourseInfo(criteria));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "showDetail";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 重写添加方法
	 * 
	 * @throws EntityException
	 */
	public void checkBeforeAdd() throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		String createOrganization = "";
		// PeEnterprise organization = null;
		EnumConst ec1 = null;
		EnumConst ec2 = null;
		EnumConst ec3 = null;
		if ("3".equals(us.getSsoUser().getPePriRole().getId())) {
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
		String courseSql = " select * from course_info where course_name ='" + this.getBean().getCourseName() + "' and organ_name ='" + createOrganization + "' and teacher_id is null ";
		List courseList = this.getGeneralService().getBySQL(courseSql);
		if (courseList != null && courseList.size() > 0) {
			throw new EntityException("同一机构不能推荐相同课程");
		}
		this.getBean().setCreater(us.getSsoUser().getId());
		this.getBean().setCreateDate(new Date());
		this.getBean().setLoginId(us.getLoginId());
		this.getBean().setCreateOrganizationName(createOrganization);
		this.getBean().setEnumConstByFlagIsValid(ec1);
		this.getBean().setEnumConstByFlagOperationStatus(ec2);
		this.getBean().setEnumConstByFlagIsRecommends(ec3);

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
				DetachedCriteria tempdc = DetachedCriteria.forClass(CourseInfo.class);
				if (action.equals("FlagIsvalid.true")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
					tempdc.add(Restrictions.in("id", ids));
					List<CourseInfo> teacherList = new ArrayList<CourseInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<CourseInfo> iterator = teacherList.iterator();
					while (iterator.hasNext()) {
						CourseInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstByFlagIsValid(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "设为有效";
				}

				if (action.equals("FlagIsvalid.false")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
					tempdc.add(Restrictions.in("id", ids));
					List<CourseInfo> teacherList = new ArrayList<CourseInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<CourseInfo> iterator = teacherList.iterator();
					while (iterator.hasNext()) {
						CourseInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstByFlagIsValid(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "设为无效";
				}
				if (action.equals("FlagUidispose.true")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagOperationStatus", "1");
					tempdc.add(Restrictions.in("id", ids));
					List<CourseInfo> teacherList = new ArrayList<CourseInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<CourseInfo> iterator = teacherList.iterator();
					while (iterator.hasNext()) {
						CourseInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstByFlagOperationStatus(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "设为已处理";
				}
				if (action.equals("FlagUidispose.false")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagOperationStatus", "0");
					tempdc.add(Restrictions.in("id", ids));
					List<CourseInfo> teacherList = new ArrayList<CourseInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<CourseInfo> iterator = teacherList.iterator();
					while (iterator.hasNext()) {
						CourseInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstByFlagOperationStatus(enumConst);
						this.getGeneralService().save(teacherInfo);
					}
					msg = "设为未处理";
				}
				if (action.equals("FlagIsRecommends.true")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsRecommends", "1");
					tempdc.add(Restrictions.in("id", ids));
					List<CourseInfo> teacherList = new ArrayList<CourseInfo>();
					teacherList = this.getGeneralService().getList(tempdc);
					Iterator<CourseInfo> iterator = teacherList.iterator();
					while (iterator.hasNext()) {
						CourseInfo teacherInfo = iterator.next();
						teacherInfo.setEnumConstByFlagIsRecommends(enumConst);
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
			map.put("info", "共有" + ids.length + "条记录" + msg + "操作成功");

		}
		return map;
	}

	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(CourseInfo.class);
		dc.createCriteria("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCourseItemType", "enumConstByFlagCourseItemType", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagContentProperty", "enumConstByFlagContentProperty", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsValid", "enumConstByFlagIsValid", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsRecommends", "enumConstByFlagIsRecommends", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagOperationStatus", "enumConstByFlagOperationStatus", DetachedCriteria.LEFT_JOIN);
		// dc.createCriteria("enumConstByFlagCheckStatus",
		// "enumConstByFlagCheckStatus", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(dc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			String id = this.getBean().getId();

			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig()));
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
	 * 重写修改
	 * 
	 * @throws EntityException
	 */
	public void checkBeforeUpdate() throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String courseSql = " select * from course_info where course_name ='" + this.getBean().getCourseName() + "' and login_id ='" + us.getLoginId() + "' and id <>'" + this.getBean().getId() + "' ";
		List courseList = this.getGeneralService().getBySQL(courseSql);
		if (courseList != null && courseList.size() > 0) {
			throw new EntityException("同一机构不能推荐相同课程");
		}
		String hql = " from CourseInfo where id ='" + this.getBean().getId() + "' ";
		String loginId = "";
		try {
			CourseInfo courseInfo = (CourseInfo) this.getGeneralService().getByHQL(hql).get(0);
			loginId = courseInfo.getLoginId();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!us.getLoginId().equals(loginId)) {
			throw new EntityException("非本机构推荐信息，不能修改");

		}
		if (!us.getUserLoginType().equals("3")) {// 协会端处理时都是已处理的问题 Lzh
			EnumConst enumConst = this.getEnumConstService().getByNamespaceCode("FlagOperationStatus", "0");
			this.getBean().setEnumConstByFlagOperationStatus(enumConst);
		}
	}

}
