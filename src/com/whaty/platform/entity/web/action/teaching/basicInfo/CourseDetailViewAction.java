package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PrBzzTchOpenCourseService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class CourseDetailViewAction extends MyBaseAction<PrBzzTchOpencourse>{

	private PrBzzTchOpenCourseService prBzzTchOpenCourseService;

	private String id;
	private boolean flag;
	private String tempFlag;
	private String method;// 用于区别必修课程、选修课程
	private EnumConstService enumConstService;

	private String batchid;

	private String coursename;// 课程名称
	private String coursecode;// 课程编号
	private String coursetype;// 课程性质
	private String ywfl;// 业务分类
	private String dgfl;// 大纲分类
	private String nrfl;// 内容分类
	private String isfee;// 是否收费
	private String teacher;// 主讲人
	private String jyxxrq;// 建议学习人群
	private String isks;// 是否收费

	private String discription;// 专项培训

	private List courseTypeList;
	private List courseCategoryList;
	private List courseItemTypeList;
	private List courseContentList;
	private List suggestrenList;

	public List getCourseTypeList() {
		return courseTypeList;
	}

	public void setCourseTypeList(List courseTypeList) {
		this.courseTypeList = courseTypeList;
	}

	public List getCourseCategoryList() {
		return courseCategoryList;
	}

	public void setCourseCategoryList(List courseCategoryList) {
		this.courseCategoryList = courseCategoryList;
	}

	public List getCourseItemTypeList() {
		return courseItemTypeList;
	}

	public void setCourseItemTypeList(List courseItemTypeList) {
		this.courseItemTypeList = courseItemTypeList;
	}

	public List getCourseContentList() {
		return courseContentList;
	}

	public void setCourseContentList(List courseContentList) {
		this.courseContentList = courseContentList;
	}

	public List getSuggestrenList() {
		return suggestrenList;
	}

	public void setSuggestrenList(List suggestrenList) {
		this.suggestrenList = suggestrenList;
	}

	public String getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setTitle("课程列表");
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程编号"), "code");
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, true, true, "TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
		columnConfigType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnConfigType);
		ColumnConfig columnCategoryType = new ColumnConfig(this.getText("业务分类"), "enumConstByFlagCourseCategory.name", true, true, true, "TextField", false, 100, "");
		String sqlCategory = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
		columnCategoryType.setComboSQL(sqlCategory);
		this.getGridConfig().addColumn(columnCategoryType);
		ColumnConfig columnCourseItemTypeType = new ColumnConfig(this.getText("大纲分类"), "enumConstByFlagCourseItemType.name", true, true, true, "TextField", false, 100, "");
		String sqlCourseItem = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
		columnCourseItemTypeType.setComboSQL(sqlCourseItem);
		this.getGridConfig().addColumn(columnCourseItemTypeType);
		ColumnConfig columnContentProperty = new ColumnConfig(this.getText("内容属性分类"), "enumConstByFlagContentProperty.name", true, true, true, "TextField", false, 100, "");
		String sqlContentProperty = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnContentProperty.setComboSQL(sqlContentProperty);
		this.getGridConfig().addColumn(columnContentProperty);
		ColumnConfig columnConfigFree = new ColumnConfig(this.getText("收费状态"), "enumConstByFlagIsFree.name", true, true, true, "TextField", false, 100, "");
		String sql6 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsFree'";
		columnConfigFree.setComboSQL(sql6);
		this.getGridConfig().addColumn(columnConfigFree);

		//this.getGridConfig().addColumn(this.getText("价格(元)"), "price");
		//this.getGridConfig().addColumn(this.getText("学时"), "time", false, true, true, null);
		// this.getGridConfig().addColumn(this.getText("课程性质"),"kc_type");

		
		this.getGridConfig().addColumn(this.getText("主讲人"), "teacher", true, false, true, null);
		this.getGridConfig().addColumn(this.getText("满意度调查"), "sid", false, false, false, "");
		this.getGridConfig().addRenderScript(
				this.getText("查看统计"),
				"{return '<a href=\"/entity/information/peVotePaperTotal_viewDetail.action?bean.id='+record.data['sid']+'&togo=1\" target=\"_blank\">查看统计</a>';}",
				"id");
		
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrBzzTchOpencourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/courseDetailView";

	}

	public PrBzzTchOpencourse getBean() {
		return (PrBzzTchOpencourse) super.superGetBean();
	}

	public void setBean(PrBzzTchOpencourse prBzzTchOpencourse) {
		super.superSetBean(prBzzTchOpencourse);
	}

	
	public Page list() {
		
		Page page = null;
		try {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append(" SELECT * FROM ( ");
			sqlBuffer.append(" SELECT  COURSE.ID ID  , ");
			sqlBuffer.append("        COURSE.CODE CODE ,");
			sqlBuffer.append("        COURSE.NAME NAME,");
			sqlBuffer.append("       NVL(TYPE.NAME, '-')  TYPENAME, ");
			sqlBuffer.append("        NVL( COURSECATEGORY.NAME, '-')  COURSECATEGORY, ");
			sqlBuffer.append("       NVL( ITEM_TYPE.NAME, '-')    ITEMNAME, ");
			sqlBuffer.append("      NVL(PROPERTY.NAME, '-')      PROPERTY, ");
			sqlBuffer.append("       FLAG_ISFREE.NAME      FLAGNAME, ");
			sqlBuffer.append("        COURSE.TEACHER  TEACHER ,");
			sqlBuffer.append("        pe.id SID ");
			sqlBuffer.append("   FROM PE_VOTE_PAPER PE ");
			sqlBuffer.append("                INNER JOIN PE_BZZ_TCH_COURSE course on pe.courseid = course.id ");
			sqlBuffer.append("                 LEFT JOIN ENUM_CONST type on course.flag_coursetype = type.id "); //将内连接改为左连接，协会内训课程监管机构内训没有这些属性
			sqlBuffer.append("                 LEFT JOIN ENUM_CONST COURSECATEGORY on course.flag_coursecategory = COURSECATEGORY.id ");
			sqlBuffer.append("                 LEFT JOIN ENUM_CONST ITEM_TYPE ON COURSE.FLAG_COURSE_ITEM_TYPE = ITEM_TYPE.ID ");
			sqlBuffer.append("                 LEFT JOIN ENUM_CONST PROPERTY ON COURSE.FLAG_CONTENT_PROPERTY =  PROPERTY.ID ");
			sqlBuffer.append("                 LEFT JOIN ENUM_CONST FLAG_ISFREE ON COURSE.FLAG_ISFREE = FLAG_ISFREE.ID ");//新改协会内训 监管内训设置收费状态， 原数据可能没有
			sqlBuffer.append("                 INNER join enum_const  ec1 on course.flag_isvalid= ec1.id and course.flag_isvalid='2' ");//有效
			sqlBuffer.append("                 WHERE PE.FK_PARENT_ID = '"+this.getId()+"' ");
			sqlBuffer.append(" ) AA WHERE 1 = 1") ;
			StringBuffer searchSql = new StringBuffer();
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
				/* 课程编号 */
				if (name.equals("code")) {
					searchSql.append(" AND UPPER(AA.CODE) LIKE '%" + value.toUpperCase() + "%' ");
				}
				/* 课程名称 */
				if (name.equals("name")) {
					searchSql.append(" AND UPPER(AA.NAME) LIKE '%" + value.toUpperCase() + "%' ");
				}	
				/* 主讲人 */
				if (name.equals("teacher")) {
					searchSql.append(" AND UPPER(AA.TEACHER) LIKE '%" + value.toUpperCase() + "%' ");
				}
				/* 课程性质 */
				if (name.equals("enumConstByFlagCourseType.name")) {
					searchSql.append(" AND AA.TYPENAME = '" + value + "' ");
				}
				/* 业务分类 */
				if (name.equals("enumConstByFlagCourseCategory.name")) {
					searchSql.append(" AND AA.COURSECATEGORY = '" + value + "' ");
				}
				/* 大纲分类 */
				if (name.equals("enumConstByFlagCourseItemType.name")) {
					searchSql.append(" AND AA.ITEMNAME = '" + value + "' ");
				}
				/* 内容属性分类 */
				if (name.equals("enumConstByFlagContentProperty.name")) {
					searchSql.append(" AND AA.PROPERTY = '" + value + "' ");
				}
				/* 课程收费状态 */
				if (name.equals("enumConstByFlagIsFree.name")) {
					searchSql.append(" AND AA.FLAGNAME = '" + value + "' ");
				}
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 课程性质 */
				if (temp.equals("enumConstByFlagCourseType.name")) {
					temp = "COURSETYPE";
				}
			}
			sqlBuffer.append(searchSql);
		
			
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}



	// lwq 增加查看专项描述的方法
	public String showDiscription() {
		String sql = "select p.batch_note from PE_BZZ_BATCH p where p.id='" + id + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list.size() > 0) {
				discription = (String) list.get(0);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "showDiscription";
	}

	public PrBzzTchOpenCourseService getPrBzzTchOpenCourseService() {
		return prBzzTchOpenCourseService;
	}

	public void setPrBzzTchOpenCourseService(PrBzzTchOpenCourseService prBzzTchOpenCourseService) {
		this.prBzzTchOpenCourseService = prBzzTchOpenCourseService;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getCoursetype() {
		return coursetype;
	}

	public void setCoursetype(String coursetype) {
		this.coursetype = coursetype;
	}

	public String getYwfl() {
		return ywfl;
	}

	public void setYwfl(String ywfl) {
		this.ywfl = ywfl;
	}

	public String getDgfl() {
		return dgfl;
	}

	public void setDgfl(String dgfl) {
		this.dgfl = dgfl;
	}

	public String getNrfl() {
		return nrfl;
	}

	public void setNrfl(String nrfl) {
		this.nrfl = nrfl;
	}

	public String getIsfee() {
		return isfee;
	}

	public void setIsfee(String isfee) {
		this.isfee = isfee;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getJyxxrq() {
		return jyxxrq;
	}

	public void setJyxxrq(String jyxxrq) {
		this.jyxxrq = jyxxrq;
	}

	public String getIsks() {
		return isks;
	}

	public void setIsks(String isks) {
		this.isks = isks;
	}
}
