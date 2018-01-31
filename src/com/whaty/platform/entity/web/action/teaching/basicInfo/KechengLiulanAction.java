package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeBzzTchCourseware;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchCourseService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class KechengLiulanAction extends MyBaseAction<PeBzzTchCourse> {

	private List peBzzTchCourses;
	private String tempFlag;
	private PeBzzTchCourseware peBzzTchCourseware;
	private PeTchCourseService peTchCourseService;
	private String courseware_id;
	private String courseware_code;
	private String courseware_name;
	private String scormType;
	private String indexFile;
	private String _uploadContentType; // 文件类型属性
	private EnumConstService enumConstService;
	private double price; // 课程的价格

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("在线课程浏览");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("课程编号"), "code", true, true, true, Const.coursecode_for_extjs);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true, true, true, "TextField", false, 200, "");

		ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, true, true, "TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
		columnConfigType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnConfigType);

		ColumnConfig columnConfigCategory = new ColumnConfig(this.getText("按业务分类"), "enumConstByFlagCourseCategory.name", true, true, true, "TextField", false, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
		columnConfigCategory.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigCategory);

		ColumnConfig columnCourseItemType = new ColumnConfig(this.getText("按大纲分类"), "enumConstByFlagCourseItemType.name", true, true, true, "TextField", false, 100, "");
		String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
		columnCourseItemType.setComboSQL(sql7);
		this.getGridConfig().addColumn(columnCourseItemType);

		ColumnConfig columnCourseContent = new ColumnConfig(this.getText("按内容属性分类"), "enumConstByFlagContentProperty.name", true, true, true, "TextField", false, 100, "");
		String sql8_ = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnCourseContent.setComboSQL(sql8_);
		this.getGridConfig().addColumn(columnCourseContent);

		// this.getGridConfig().addColumn(this.getText("课程所属区域"), "quyu", false,
		// false, true, "TextField", true, 25);
		//		

		String comboSQL = "select ID,NAME from enum_const ec where ec.namespace='FlagCoursearea' and ec.id<>'Coursearea0'";
		ColumnConfig columnConfig = new ColumnConfig(this.getText("课程所属区域"), "Combobox_quyu");
		columnConfig.setAdd(true);
		columnConfig.setSearch(true);
		columnConfig.setList(true);
		columnConfig.setComboSQL(comboSQL);
		this.getGridConfig().addColumn(columnConfig);
		this.getGridConfig().addColumn(this.getText("学时"), "time", false, false, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("课程时长(分钟)"), "course_Len", false, true, true , Const.number_for_extjs);
		this.getGridConfig().addColumn(this.getText("主讲人"), "teacher", true, false, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("课程评分"), "score", false, false, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("发布日期"), "ANNOUNCE_DATE", false, false, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("课件id"), "coursewareId", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("课件类型"), "scormType", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("课件首页"), "indexFile", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("满意度调查"), "sid", false, false, false, "");
		ColumnConfig ccFlagSuggest = new ColumnConfig(this.getText("建议学习人群"), "enumConstByFlagSuggest.name", true, false, false, "TextField", false, 100, "");
		String sqlFlagSuggest = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
		ccFlagSuggest.setComboSQL(sqlFlagSuggest);
		this.getGridConfig().addColumn(ccFlagSuggest);
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("预览"),
						"{if(record.data['coursewareId'] ==''){"
								+ "	return '预览';"
								+ "}else {"
								+ "	return '<a href=/CourseImports/'+record.data['coursewareId']+'/'+record.data['scormType']+'/'+record.data['indexFile']+'?mydate='+ new Date().getTime() +' target=\"_blank\">预览</a>';"
								+ "}}", "");
		this.getGridConfig().addRenderScript(this.getText("查看信息"),
				"{return '<a href=\"/entity/teaching/peBzzCourseManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">查看信息</a>';}", "id");
		// this.getGridConfig().addRenderScript(this.getText("查看调查结果"),
		// "{if(record.data['sid'] !=''){return '<a
		// href=\"/entity/information/peVotePaper_viewDetail.action?bean.id='+record.data['sid']+'&togo=1\"
		// target=\"_blank\">查看</a>';}else return '未添加'}", "sid");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzTchCourse.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/kechengLiulan";
	}

	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);

	}

	public PeBzzTchCourse getBean() {
		return super.superGetBean();
	}

	/**
	 * 获得课程信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");
		dc.createCriteria("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsvisitAfterPass", "enumConstByFlagIsvisitAfterPass", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsFree", "enumConstByFlagIsFree", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsRecommend", "enumConstByFlagIsRecommend", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCourseItemType", "enumConstByFlagCourseItemType", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCanJoinBatch", "enumConstByFlagCanJoinBatch", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCheckStatus", "enumConstByFlagCheckStatus", DetachedCriteria.LEFT_JOIN);
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
	 * 重写框架方法：课程信息（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("SELECT * FROM (SELECT A.ID, A.CODE, A.NAME, NVL(EC1.NAME, '-') FLAGCOURSETYPE, NVL(EC2.NAME, '-') FLAGCOURSECATEGORY, NVL(EC3.NAME, '-') FLAGCOURSEITEMTYPE, ");
			sqlBuffer.append(" NVL(EC4.NAME, '-') FLAGCONTENTPROPERTY,NVL(EC11.NAME, '-') quyu,a.time,a.course_len ,a.TEACHER," +
					" DECODE(PBTC_SCORE,0,NULL,PBTC_SCORE) SCORE, a.ANNOUNCE_DATE, ");
			sqlBuffer.append(" C.COURSE_ID COURSEWAREID, C.SCORM_TYPE SCORMTYPE, C.INDEXFILE, A.CREATE_DATE CREATEDATE,WM_CONCAT(EC10.NAME) FS ");
			sqlBuffer.append(" FROM PE_BZZ_TCH_COURSE A ");
			sqlBuffer.append(" left JOIN ENUM_CONST EC1 ON A.FLAG_COURSETYPE = EC1.ID ");
			sqlBuffer.append(" left JOIN ENUM_CONST EC2 ON A.FLAG_COURSECATEGORY = EC2.ID ");
			sqlBuffer.append(" left JOIN ENUM_CONST EC3 ON A.FLAG_COURSE_ITEM_TYPE = EC3.ID ");
			sqlBuffer.append(" left JOIN ENUM_CONST EC4 ON A.FLAG_CONTENT_PROPERTY = EC4.ID ");
			sqlBuffer.append(" left JOIN ENUM_CONST EC5 ON A.FLAG_IS_EXAM = EC5.ID ");
			sqlBuffer.append(" left JOIN ENUM_CONST EC ON A.FLAG_ISRECOMMEND = EC.ID");
			sqlBuffer.append(" left JOIN ENUM_CONST EC6 ON A.FLAG_ISFREE = EC6.ID ");
			sqlBuffer.append(" left JOIN ENUM_CONST EC7 ON A.FLAG_ISVISITAFTERPASS = EC7.ID ");
			sqlBuffer.append(" INNER JOIN ENUM_CONST EC8 ON A.FLAG_ISVALID = EC8.ID  and ec8.code='1' ");// 已发布
			sqlBuffer.append(" INNER JOIN ENUM_CONST EC9 ON A.FLAG_OFFLINE = EC9.ID  and ec9.code='0' ");// 未下线
			sqlBuffer.append(" LEFT JOIN PE_BZZ_TCH_COURSE_SUGGEST B ON A.ID = B.FK_COURSE_ID AND TABLE_NAME = 'PE_BZZ_TCH_COURSE' ");
			sqlBuffer.append(" LEFT JOIN ENUM_CONST EC10 ON B.FK_ENUM_CONST_ID = EC10.ID ");
			sqlBuffer.append(" LEFT JOIN SCORM_SCO_LAUNCH C ON A.CODE = C.COURSE_ID");
			sqlBuffer.append(" LEFT JOIN SCORM_TYPE D ON C.SCORM_TYPE = D.CODE");
			sqlBuffer.append(" INNER join enum_const ec11 on a.FLAG_COURSEAREA =ec11.id   and ec11.id<>'Coursearea0' ");
			sqlBuffer.append("LEFT JOIN (SELECT DECODE(SUM(A5), 0, '', ROUND(SUM(A5 * A6) / SUM(A5), 1)) PBTC_SCORE, RSC.A1 FROM REPORT_VOTE_TOTAL RSC WHERE RSC.A5 IS NOT NULL AND RSC.A6 IS NOT NULL AND " +
					"  exists (SELECT ID FROM PE_VOTE_PAPER PVP WHERE FID = ID AND  PVP.TITLE LIKE '满意度调查问卷（201%') GROUP BY RSC.A1) PBTCSCORE ON A.CODE = PBTCSCORE.A1");
			sqlBuffer.append(" GROUP BY A.ID,A.CODE,A.NAME,EC1.NAME,EC2.NAME,EC3.NAME,EC4.NAME, ");
			sqlBuffer.append(" EC8.NAME,EC9.NAME,EC5.NAME,EC6.NAME,EC1.NAME,EC2.NAME,EC3.NAME,PBTC_SCORE,a.ANNOUNCE_DATE, ");
			sqlBuffer.append(" EC4.NAME,ec11.name,EC8.NAME,EC9.NAME,EC5.NAME,EC6.NAME,FLAG_ISRECOMMEND,EC.NAME,a.time, ");
			sqlBuffer.append(" A.course_len,A.TEACHER,C.COURSE_ID,A.CREATE_DATE, ");
			sqlBuffer.append(" C.SCORM_TYPE, C.INDEXFILE ");
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
				/* 课程性质 */
				if (name.equals("enumConstByFlagCourseType.name")) {
					name = "FlagCourseType";
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
				/* 是否有效-发布 */
				if (name.equals("enumConstByFlagIsvalid.name")) {
					name = "FlagIsvalid";
				}
				/* 下线 */
				if (name.equals("enumConstByFlagOffline.name")) {
					name = "FlagOffline";
				}
				/* 是否考试 */
				if (name.equals("enumConstByFlagIsExam.name")) {
					name = "FlagIsExam";
				}
				/* 课程所属区域 */
				if (name.equals("Combobox_quyu")) {
					name = "quyu";
				}
				if (!name.equals("enumConstByFlagSuggest.name")) {
					// 课程性质、业务分类、大纲分类、内容属性分类用=其他用like
					if ("FlagCourseType".equals(name) || "quyu".equals(name) || "FlagCourseCategory".equals(name) || "FlagCourseItemType".equals(name) || "FlagContentProperty".equals(name)) {
						sqlBuffer.append(" and UPPER(" + name + ") = UPPER('" + value + "')");
					} else {
						sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
					}
				}
				/* 建议学习人群 */
				if (name.equals("enumConstByFlagSuggest.name")) {
					sqlBuffer.append(" AND INSTR(FS, '" + value + "', 1, 1) > 0 ");
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
					temp = "FlagCourseType";
				}
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
				/* 是否有效-发布 */
				if (temp.equals("enumConstByFlagIsvalid.name")) {
					temp = "FlagIsvalid";
				}
				/* 下线 */
				if (temp.equals("enumConstByFlagOffline.name")) {
					temp = "FlagOffline";
				}
				/* 是否考试 */
				if (temp.equals("enumConstByFlagIsExam.name")) {
					temp = "FlagIsExam";
				}
				/* 课程所属区域 */
				if (temp.equals("Combobox_quyu")) {
					temp = "quyu";
				}
				if (temp.equals("id")) {
					temp = "createDate";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					if ("time".equalsIgnoreCase(temp) || "price".equalsIgnoreCase(temp) || "score".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by to_number(" + temp + ") desc ");
					} else {
						if (!temp.equals("enumConstByFlagSuggest.name"))
							sqlBuffer.append(" order by " + temp + " desc ");
					}
				} else {
					if ("time".equalsIgnoreCase(temp) || "price".equalsIgnoreCase(temp) || "score".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by to_number(" + temp + ") asc ");
					} else {
						if (!temp.equals("enumConstByFlagSuggest.name"))
							sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
			} else {
				sqlBuffer.append(" order by createDate desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");
		dc.createCriteria("ssoUser", "ssoUser").createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCanJoinBatch", "enumConstByFlagCanJoinBatch", DetachedCriteria.LEFT_JOIN);
		return dc;
	}

	public String preView() {
		return "noPreView";
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public String get_uploadContentType() {
		return _uploadContentType;
	}

	public void set_uploadContentType(String contentType) {
		_uploadContentType = contentType;
	}

	public String getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	public List getPeBzzTchCourses() {
		return peBzzTchCourses;
	}

	public void setPeBzzTchCourses(List peBzzTchCourses) {
		this.peBzzTchCourses = peBzzTchCourses;
	}

	public PeBzzTchCourseware getPeBzzTchCourseware() {
		return peBzzTchCourseware;
	}

	public void setPeBzzTchCourseware(PeBzzTchCourseware peBzzTchCourseware) {
		this.peBzzTchCourseware = peBzzTchCourseware;
	}

	public PeTchCourseService getPeTchCourseService() {
		return peTchCourseService;
	}

	public void setPeTchCourseService(PeTchCourseService peTchCourseService) {
		this.peTchCourseService = peTchCourseService;
	}

	public String getCourseware_id() {
		return courseware_id;
	}

	public void setCourseware_id(String courseware_id) {
		this.courseware_id = courseware_id;
	}

	public String getCourseware_name() {
		return courseware_name;
	}

	public void setCourseware_name(String courseware_name) {
		this.courseware_name = courseware_name;
	}

	public String getCourseware_code() {
		return courseware_code;
	}

	public void setCourseware_code(String courseware_code) {
		this.courseware_code = courseware_code;
	}

	public String getIndexFile() {
		return indexFile;
	}

	public void setIndexFile(String indexFile) {
		this.indexFile = indexFile;
	}

	public String getScormType() {
		return scormType;
	}

	public void setScormType(String scormType) {
		this.scormType = scormType;
	}
}
