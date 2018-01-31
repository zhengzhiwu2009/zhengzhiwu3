package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
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

public class CourseViewAction extends MyBaseAction<PeBzzTchCourse> {

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

	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("在审课程");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("课程编号"), "code", true, true, true, Const.coursecode_for_extjs);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true, true, true, "TextField", false, 200, "");

		ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", false, true, true, "TextField", false, 100, "");
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

		// Lee start 2014年03月12日 添加按内容属性查询条件
		ColumnConfig columnCourseContent = new ColumnConfig(this.getText("按内容属性分类"), "enumConstByFlagContentProperty.name", true, true, true, "TextField", false, 100, "");
		String sql8_ = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnCourseContent.setComboSQL(sql8_);
		this.getGridConfig().addColumn(columnCourseContent);
		// Lee end
		//		
		// ColumnConfig columnConfigIsvalid = new
		// ColumnConfig(this.getText("发布状态"),"enumConstByFlagIsvalid.name",
		// false,false,false,"TextField",false,100,"");
		// String sql3 ="select a.id ,a.name from enum_const a where
		// a.namespace='FlagIsvalid'";
		// columnConfigIsvalid.setComboSQL(sql3);
		// this.getGridConfig().addColumn(columnConfigIsvalid);
		// //后来添加，是否可添加到专项
		// ColumnConfig columncanJoinBatch = new
		// ColumnConfig(this.getText("专项培训课程"),"enumConstByFlagCanJoinBatch.name",
		// false,false,false,"TextField",false,100,"");
		// String sqlcjb ="select a.id ,a.name from enum_const a where
		// a.namespace='FlagCanJoinBatch'";
		// columncanJoinBatch.setComboSQL(sqlcjb);
		// this.getGridConfig().addColumn(columncanJoinBatch);
		//
		//		
		// ColumnConfig columnConfigIsExam = new
		// ColumnConfig(this.getText("是否考试"),"enumConstByFlagIsExam.name",
		// false,true,false,"TextField",false,100,"");
		// String sqlx ="select a.id ,a.name from enum_const a where
		// a.namespace='FlagIsExam'";
		// columnConfigIsExam.setComboSQL(sqlx);
		// this.getGridConfig().addColumn(columnConfigIsExam);
		//
		// ColumnConfig columnConfigRecommend = new
		// ColumnConfig(this.getText("推荐状态"),"enumConstByFlagIsRecommend.name",
		// false,true,false,"TextField",false,100,"");
		// String sql5 ="select a.id ,a.name from enum_const a where
		// a.namespace='FlagIsRecommend'";
		// columnConfigRecommend.setComboSQL(sql5);
		// this.getGridConfig().addColumn(columnConfigRecommend);
		//		
		// ColumnConfig columnConfigFree = new
		// ColumnConfig(this.getText("收费状态"),"enumConstByFlagIsFree.name",
		// false,true,false,"TextField",false,100,"");
		// String sql6 ="select a.id ,a.name from enum_const a where
		// a.namespace='FlagIsFree'";
		// columnConfigFree.setComboSQL(sql6);
		// this.getGridConfig().addColumn(columnConfigFree);
		//		
		// ColumnConfig columnConfigCheck = new
		// ColumnConfig(this.getText("审核状态"),"enumConstByFlagCheckStatus.name",
		// false,false,false,"TextField",false,100,"");
		// String sql_check ="select a.id ,a.name from enum_const a where
		// a.namespace='FlagCheckStatus'";
		// columnConfigCheck.setComboSQL(sql_check);
		// this.getGridConfig().addColumn(columnConfigCheck);

		this.getGridConfig().addColumn(this.getText("主讲人"), "teacher", true, false, true, "TextField", true, 25);

		String comboSQL = "select ID,NAME from enum_const ec where ec.namespace='FlagCoursearea' and ec.id<>'Coursearea0'";
		ColumnConfig columnConfig = new ColumnConfig(this.getText("课程所属区域"), "Combobox_quyu");
		columnConfig.setAdd(true);
		columnConfig.setSearch(true);
		columnConfig.setList(true);
		columnConfig.setComboSQL(comboSQL);
		this.getGridConfig().addColumn(columnConfig);

		this.getGridConfig().addColumn(this.getText("学时"), "time", true, true, true, "TextField");

		EnumConst classHourRate = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");// 学时金钱兑换比例
		this.getGridConfig().addColumn(this.getText("学习期限(天)"), "studyDates", false, true, false, Const.number_for_extjs);

		this.getGridConfig().addColumn(this.getText("停用日期"), "endDate", false, true, false, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("发布日期"), "announceDate", false, true, false, "TextField", false, 10);
		this.getGridConfig().addColumn(this.getText("答疑开始时间"), "answerBeginDate", false, true, false, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("答疑结束时间"), "answerEndDate", false, true, false, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("通过规则(百分比分数线)"), "passRole", false, true, false, Const.oneTwoNum_for_extjs);
		// this.getGridConfig()
		// .addColumn(this.getText("考试限制次数"), "examTimesAllow", false,
		// true, false, Const.oneTwoNum_for_extjs);
		// ColumnConfig columnConfig = new
		// ColumnConfig(this.getText("通过后能否继续访问"),"enumConstByFlagIsvisitAfterPass.name",
		// false,true,false,"TextField",false,100,"");
		// String sql4 ="select b.id ,b.name from enum_const b where
		// b.namespace='FlagIsvisitAfterPass'";
		// columnConfig.setComboSQL(sql4);
		// this.getGridConfig().addColumn(columnConfig);

		// this.getGridConfig().addColumn(this.getText("通过规则描述"),
		// "passRoleNote",
		// false, true, true, "");
		// this.getGridConfig().addColumn(this.getText("学习建议"), "suggestion",
		// false, true, true, "TextArea", true, 500);
		this.getGridConfig().addColumn(this.getText("课件id"), "coursewareId", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("课件类型"), "scormType", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("课件首页"), "indexFile", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("满意度调查"), "sid", false, false, false, "");

		this
				.getGridConfig()
				.addRenderScript(
						this.getText("预览"),
						"{if(record.data['coursewareId'] ==''){"
								+ "	return '<a href=\"/entity/teaching/peBzzCourseManager_preView.action?mydate= '+new Date().getTime()+'\" target=\"_blank\">预览</a>';"
								+ "}else {"
								+ "	return '<a href=/CourseImports/'+record.data['coursewareId']+'/'+record.data['scormType']+'/'+record.data['indexFile']+'?mydate='+ new Date().getTime() +' target=\"_blank\">预览</a>';"
								+ "}}", "");
		// this.getGridConfig().addRenderScript(
		// this.getText("查看信息"),
		// "{return '<a
		// href=\"/entity/teaching/peBzzCourseManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"
		// target=\"_blank\">查看信息</a>';}",
		// "id");
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
		this.servletPath = "/entity/teaching/courseView";
	}

	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);

	}

	public PeBzzTchCourse getBean() {
		return super.superGetBean();
	}

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

	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us.getUserLoginType().equals("3")) {
			sqlBuffer.append("SELECT * ");
			sqlBuffer.append("FROM   (SELECT a.id, ");
			sqlBuffer.append("               a.code, ");
			sqlBuffer.append("               a.name, ");
			sqlBuffer.append("               NVL(a.courseTypeName, '-') courseTypeName,");
			sqlBuffer.append("    NVL(  a.categoryName, '-')  categoryName       , ");
			sqlBuffer.append("    NVL(   a.courseItemType, '-')      courseItemType     , ");
			sqlBuffer.append("       NVL(   a.flagcontentproperty, '-')   flagcontentproperty      , ");
			sqlBuffer.append("               a.teacher, ");
			sqlBuffer.append("               a.quyu as Combobox_quyu, ");
			sqlBuffer.append("               a.time, ");
			sqlBuffer.append("               a.endDate, ");
			sqlBuffer.append("               a.announceDate, ");
			sqlBuffer.append("               a.answerBeginDate, ");
			sqlBuffer.append("               a.answerEndDate, ");
			sqlBuffer.append("               a.visitName, ");
			sqlBuffer.append("               a.passRoleNote, ");
			sqlBuffer.append("               a.coursewareId, ");
			sqlBuffer.append("               a.scormType, ");
			sqlBuffer.append("               indexFile, ");
			sqlBuffer.append("				 a.sid ");
			sqlBuffer.append("        FROM   (SELECT tc.id               AS id, ");
			sqlBuffer.append("                       tc.name             AS name, ");
			sqlBuffer.append("                       tc.code             AS code, ");
			sqlBuffer.append("                       tc.studyDates       AS studyDates, ");
			sqlBuffer.append("                       tc.price            AS price, ");
			sqlBuffer.append("                       tc.end_date         AS endDate, ");
			sqlBuffer.append("                       tc.announce_Date         AS announceDate, ");
			sqlBuffer.append("                       tc.answer_BeginDate AS answerBeginDate, ");
			sqlBuffer.append("                       tc.answer_EndDate   AS answerEndDate, ");
			sqlBuffer.append("                       tc.passRole         AS passRole, ");
			sqlBuffer.append("                       tc.examTimes_Allow  AS examTimesAllow, ");
			sqlBuffer.append("                       tc.passRole_Note    AS passRoleNote, ");
			sqlBuffer.append("                       tc.suggestion       AS suggestion, ");
			sqlBuffer.append("                       ec1.name            AS validName, ");
			sqlBuffer.append("                       ec8.name          	 AS courseItemType, ");
			sqlBuffer.append("                       ec9.name            AS isExam, ");
			sqlBuffer.append("                       tc.suqnum           AS suqNum, ");
			sqlBuffer.append("                       ec2.name            AS categoryName, ");
			sqlBuffer.append("                       ec3.name            AS courseTypeName, ");
			sqlBuffer.append("                       ec4.name            AS visitName, ");
			sqlBuffer.append("                       ec5.name            AS isRecommend, ");
			sqlBuffer.append("                       ec6.name            AS isFree, ");
			sqlBuffer.append("                       ec10.name           AS isCheck, ");
			sqlBuffer.append("                       tc.time             AS time, ");
			sqlBuffer.append("                       ec12.name            AS quyu, ");
			sqlBuffer.append("                       scorm.indexFile     AS indexFile, ");
			sqlBuffer.append("                       scorm.course_id     AS coursewareId, ");
			sqlBuffer.append("                       scormType.code      AS scormType, ");
			sqlBuffer.append("                       tc.teacher          AS teacher, ");
			sqlBuffer.append("                       tc.teacher_note     AS teacherNote, ");
			sqlBuffer.append(" ec11.name as flagcontentproperty, ");
			sqlBuffer.append("                       tc.note             AS note ,tc.SATISFACTION_ID as sid");
			sqlBuffer.append("                FROM   pe_bzz_tch_course tc, ");
			sqlBuffer.append("                       (select distinct course_id as course_id, scorm_type as scorm_type, indexfile as indexfile from scorm_sco_launch)  scorm, ");
			sqlBuffer.append("                       scorm_type scormType, ");
			sqlBuffer.append("                       enum_const ec1, ");
			sqlBuffer.append("                       enum_const ec2, ");
			sqlBuffer.append("                       enum_const ec3, ");
			sqlBuffer.append("                       enum_const ec4, ");
			sqlBuffer.append("                       enum_const ec5, ");
			sqlBuffer.append("                       enum_const ec6, ");
			sqlBuffer.append("                       enum_const ec7, ");
			sqlBuffer.append("                       enum_const ec8, ");
			sqlBuffer.append("                       enum_const ec9, ");
			sqlBuffer.append("                       enum_const ec10 ");
			sqlBuffer.append("                       ,enum_const ec11, enum_const ec12  ");// Lee
			// 2014年3月12日
			sqlBuffer.append("                WHERE  tc.flag_isvalid = ec1.id ");
			sqlBuffer.append("                       AND tc.flag_coursecategory = ec2.id ");
			sqlBuffer.append("                       AND tc.flag_coursetype = ec3.id ");
			sqlBuffer.append("                       AND tc.code = scorm.course_id(+) ");
			sqlBuffer.append("                       AND tc.flag_isrecommend = ec5.id(+) ");
			sqlBuffer.append("                       AND tc.flag_isfree = ec6.id ");
			sqlBuffer.append("                       AND tc.FLAG_OFFLINE =ec7.id ");
			sqlBuffer.append("                       AND EC7.CODE = '0' ");// 未下线
			sqlBuffer.append("                       AND scorm.scorm_type =scormType.code(+) ");
			sqlBuffer.append("                       AND tc.FLAG_ISVISITAFTERPASS = ec4.id(+)   ");
			sqlBuffer.append("                       AND tc.FLAG_COURSE_ITEM_TYPE = ec8.id(+)  ");
			sqlBuffer.append("                       AND tc.FLAG_CONTENT_PROPERTY = ec11.id(+)  ");
			sqlBuffer.append("                       AND tc.FLAG_COURSEAREA = ec12.id and ec12.id<>'Coursearea0'  ");
			sqlBuffer.append("                       AND tc.FLAG_CHECK_STATUS = ec10.id(+)   and ec1.code = '0'  ");// 未发布
			sqlBuffer.append("                       AND tc.FLAG_IS_EXAM = ec9.id" + " ) a ");// 公共课程，专项课程
			sqlBuffer.append("               LEFT JOIN pe_bzz_tch_courseware pbtc ");
			sqlBuffer.append("                 ON a.id = pbtc.fk_course_id) where 1=1");
		} else {

		}
		try {
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

				if (name.equals("enumConstByFlagIsvalid.name")) {
					name = "validName";
				}
				if (name.equals("enumConstByFlagCourseItemType.name")) {
					name = "courseItemType";
				}
				if (name.equals("enumConstByFlagCourseCategory.name")) {
					name = "categoryName";
				}
				if (name.equals("enumConstByFlagCourseType.name")) {
					name = "courseTypeName";
				}
				if (name.equals("enumConstByFlagIsvisitAfterPass.name")) {
					name = "visitName";
				}
				if (name.equals("enumConstByFlagIsFree.name")) {
					name = "isFree";
				}
				if (name.equals("enumConstByFlagCheckStatus.name")) {
					name = "isCheck";
				}
				if (name.equals("enumConstByFlagIsRecommend.name")) {
					name = "isRecommend";
				}
				if (name.equals("enumConstByFlagIsExam.name")) {
					name = "isExam";
				}
				if (name.equals("enumConstByFlagCanJoinBatch.name")) {
					name = "canJoinBatch";
				}
				// Lee start 2014年3月13日 按内容属性分类查询条件
				if (name.equals("enumConstByFlagContentProperty.name")) {
					name = "flagcontentproperty";
				}
				// Lee end
				if (name.equals("isRecommend")) {
					sqlBuffer.append(" and " + name + " like '" + value + "'");
				} else {
					if ("flagcontentproperty".equals(name) || "courseTypeName".equals(name) || "categoryName".equals(name)) {
						sqlBuffer.append(" and UPPER(" + name + ") = UPPER('" + value + "')");
					} else {
						sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
					}
				}

			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("enumConstByFlagIsvalid.name")) {
					temp = "validName";
				}
				if (temp.equals("enumConstByFlagCourseCategory.name")) {
					temp = "categoryName";
				}
				if (temp.equals("enumConstByFlagCourseType.name")) {
					temp = "courseTypeName";
				}
				if (temp.equals("enumConstByFlagIsvisitAfterPass.name")) {
					temp = "visitName";
				}
				if (temp.equals("enumConstByFlagIsFree.name")) {
					temp = "isFree";
				}
				if (temp.equals("enumConstByFlagCheckStatus.name")) {
					temp = "isCheck";
				}
				if (temp.equals("enumConstByFlagIsRecommend.name")) {
					temp = "isRecommend";
				}
				if (temp.equals("enumConstByFlagCourseItemType.name")) {
					temp = "courseItemType";
				}
				if (temp.equals("enumConstByFlagIsExam.name")) {
					temp = "isExam";
				}
				if (temp.equals("enumConstByFlagCanJoinBatch.name")) {
					temp = "canJoinBatch";
				}
				// Lee start 2014年3月13日 按内容属性分类查询条件
				if (temp.equals("enumConstByFlagContentProperty.name")) {
					temp = "flagcontentproperty";
				}
				// Lee end
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					if (temp.equals("time")) {
						sqlBuffer.append(" order by to_number(" + temp + ") desc ");
					} else {
						sqlBuffer.append(" order by " + temp + " desc ");
					}
				} else {
					if (temp.equals("time")) {
						sqlBuffer.append(" order by to_number(" + temp + ") asc ");
					} else {
						sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
			} else {
				sqlBuffer.append(" order by id desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
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
