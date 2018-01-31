package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeBzzTchCourseware;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchCourseService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 公共课程查询Action 2014年7月17日
 * 
 * @author dgh
 * 
 */
public class PeBzzCoursePubSearchAction extends MyBaseAction<PeBzzTchCourse> {

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
	private String courseParams;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * 初始化列表
	 * 
	 * @author dgh
	 * @return
	 */
	@Override
	public void initGrid() {
		courseParams = ServletActionContext.getRequest().getSession().getAttribute("courseParams") + "";
		if (null != courseParams && !"".equals(courseParams) && !"null".equals(courseParams)) {
			//this.getGridConfig().setTitle("单一公共课程查询");
			this.getGridConfig().setTitle("公共课程查询");
			this.getGridConfig().setCapability(false, false, false, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "code");
			this.getGridConfig().addColumn(this.getText("课程名称"), "ksname");
			this.getGridConfig().addColumn(this.getText("主讲人"), "teacher");
			this.getGridConfig().addColumn(this.getText("学时"), "time", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("价格(元)"), "price", false, true, true, null);
			ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, true, true, "TextField", false, 100, "");
			String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
			columnConfigType.setComboSQL(sql);
			this.getGridConfig().addColumn(columnConfigType);
			this.getGridConfig().addColumn(this.getText("课程报名人数"), "BM_CT", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("学习完成率"), "compercent", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("考试通过率"), "KSpercent", false, true, true, null);
			this.getGridConfig().addColumn(this.getText(""), "indexFile", false, false, false, null);
			ColumnConfig columnCategoryType = new ColumnConfig(this.getText("业务分类"), "enumConstByFlagCourseCategory.name", true, true, false, "TextField", false, 100, "");
			String sqlCategory = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
			columnCategoryType.setComboSQL(sqlCategory);
			this.getGridConfig().addColumn(columnCategoryType);
			ColumnConfig columnCourseItemTypeType = new ColumnConfig(this.getText("大纲分类"), "enumConstByFlagCourseItemType.name", true, true, false, "TextField", false, 100, "");
			String sqlCourseItem = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
			columnCourseItemTypeType.setComboSQL(sqlCourseItem);
			this.getGridConfig().addColumn(columnCourseItemTypeType);
			ColumnConfig columnContentProperty = new ColumnConfig(this.getText("内容属性分类"), "enumConstByFlagContentProperty.name", true, true, false, "TextField", false, 100, "");
			String sqlContentProperty = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
			columnContentProperty.setComboSQL(sqlContentProperty);
			this.getGridConfig().addColumn(columnContentProperty);
			ColumnConfig columnFlagSuggestType = new ColumnConfig(this.getText("建议学习人群"), "enumConstByFlagSuggest.name", true, true, false, "TextField", false, 100, "");
			String sqlSuggest = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
			columnFlagSuggestType.setComboSQL(sqlSuggest);
			this.getGridConfig().addColumn(columnFlagSuggestType);
			ColumnConfig columnConfigFree = new ColumnConfig(this.getText("收费状态"), "enumConstByFlagIsFree.name", true, true, false, "TextField", false, 100, "");
			String sql6 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsFree'";
			columnConfigFree.setComboSQL(sql6);
			this.getGridConfig().addColumn(columnConfigFree);
			ColumnConfig columnConfigOffline = new ColumnConfig(this.getText("是否下线"), "enumConstByFlagOffline.name", true, true, false, "TextField", false, 100, "");
			String sqly = "select a.id ,a.name from enum_const a where a.namespace='FlagOffline'";
			columnConfigOffline.setComboSQL(sqly);
			this.getGridConfig().addColumn(columnConfigOffline);
			ColumnConfig columnConfigIsExam = new ColumnConfig(this.getText("是否考试"), "enumConstByFlagIsExam.name", true, true, false, "TextField", false, 100, "");
			String sqlx = "select a.id ,a.name from enum_const a where a.namespace='FlagIsExam'";
			columnConfigIsExam.setComboSQL(sqlx);
			this.getGridConfig().addColumn(columnConfigIsExam);
			this.getGridConfig().addRenderScript(this.getText("查看报名学员"), "{return '<a href=\"/entity/basic/pePubStuDetail.action?id='+${value}+'&indexFile='+record.data['indexFile']+'&isFree='+record.data['enumConstByFlagIsFree.name']+'\" target=\"_blank\">查看报名学员</a>';}", "id");
			this.getGridConfig().addRenderScript(this.getText("课程信息"), "{return '<a href=\"/entity/teaching/peBzzCourseZixuanManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}", "id");
		} else {
			this.getGridConfig().setTitle("全部公共课程查询");
			this.getGridConfig().setCapability(false, false, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "code");
			this.getGridConfig().addColumn(this.getText("课程名称"), "ksname");
			this.getGridConfig().addColumn(this.getText("主讲人"), "teacher");
			this.getGridConfig().addColumn(this.getText("学时"), "time", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("价格(元)"), "price", false, true, true, null);
			ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, true, true, "TextField", false, 100, "");
			String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
			columnConfigType.setComboSQL(sql);
			this.getGridConfig().addColumn(columnConfigType);
			this.getGridConfig().addColumn(this.getText("课程报名人数"), "BM_CT", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("学习完成率"), "compercent", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("考试通过率"), "KSpercent", false, true, true, null);
			this.getGridConfig().addColumn(this.getText(""), "indexFile", false, false, false, null);
			ColumnConfig columnCategoryType = new ColumnConfig(this.getText("业务分类"), "enumConstByFlagCourseCategory.name", true, true, false, "TextField", false, 100, "");
			String sqlCategory = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
			columnCategoryType.setComboSQL(sqlCategory);
			this.getGridConfig().addColumn(columnCategoryType);
			ColumnConfig columnCourseItemTypeType = new ColumnConfig(this.getText("大纲分类"), "enumConstByFlagCourseItemType.name", true, true, false, "TextField", false, 100, "");
			String sqlCourseItem = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
			columnCourseItemTypeType.setComboSQL(sqlCourseItem);
			this.getGridConfig().addColumn(columnCourseItemTypeType);
			ColumnConfig columnContentProperty = new ColumnConfig(this.getText("内容属性分类"), "enumConstByFlagContentProperty.name", true, true, false, "TextField", false, 100, "");
			String sqlContentProperty = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
			columnContentProperty.setComboSQL(sqlContentProperty);
			this.getGridConfig().addColumn(columnContentProperty);
			ColumnConfig columnFlagSuggestType = new ColumnConfig(this.getText("建议学习人群"), "enumConstByFlagSuggest.name", true, true, false, "TextField", false, 100, "");
			String sqlSuggest = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
			columnFlagSuggestType.setComboSQL(sqlSuggest);
			this.getGridConfig().addColumn(columnFlagSuggestType);
			ColumnConfig columnConfigFree = new ColumnConfig(this.getText("收费状态"), "enumConstByFlagIsFree.name", true, true, false, "TextField", false, 100, "");
			String sql6 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsFree'";
			columnConfigFree.setComboSQL(sql6);
			this.getGridConfig().addColumn(columnConfigFree);
			ColumnConfig columnConfigOffline = new ColumnConfig(this.getText("是否下线"), "enumConstByFlagOffline.name", true, true, false, "TextField", false, 100, "");
			String sqly = "select a.id ,a.name from enum_const a where a.namespace='FlagOffline'";
			columnConfigOffline.setComboSQL(sqly);
			this.getGridConfig().addColumn(columnConfigOffline);
			ColumnConfig columnConfigIsExam = new ColumnConfig(this.getText("是否考试"), "enumConstByFlagIsExam.name", true, true, false, "TextField", false, 100, "");
			String sqlx = "select a.id ,a.name from enum_const a where a.namespace='FlagIsExam'";
			columnConfigIsExam.setComboSQL(sqlx);
			this.getGridConfig().addColumn(columnConfigIsExam);
			this.getGridConfig().addRenderScript(this.getText("查看报名学员"), "{return '<a href=\"/entity/basic/pePubStuDetail.action?id='+${value}+'&indexFile='+record.data['indexFile']+'&isFree='+record.data['enumConstByFlagIsFree.name']+'\" target=\"_blank\">查看报名学员</a>';}", "id");
			this.getGridConfig().addRenderScript(this.getText("课程信息"), "{return '<a href=\"/entity/teaching/peBzzCourseZixuanManager_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}", "id");
		}
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzTchCourse.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peBzzCoursePubSearch";
	}

	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);

	}

	public PeBzzTchCourse getBean() {
		return super.superGetBean();
	}

	/**
	 * 课程管理列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page list() {
		Page page = null;
		try {
			String[] courseParamsArr = null;
			courseParams = ServletActionContext.getRequest().getSession().getAttribute("courseParams") + "";
			ServletActionContext.getRequest().getSession().removeAttribute("courseParams");
			if (null != courseParams && !"".equals(courseParams) && !"null".equals(courseParams))
				courseParamsArr = courseParams.split("-");
			String courseCode = "";
			if (null != courseParamsArr)
				courseCode = courseParamsArr[0].replace("null", "");
			String courseName = "";
			if (null != courseParamsArr)
				courseName = courseParamsArr[1].replace("null", "");
			String courseTeacher = "";
			if (null != courseParamsArr)
				courseTeacher = courseParamsArr[2].replace("null", "");
			StringBuffer sqlBuffer = new StringBuffer();
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			// 机构ID
			String enterpriseIdSql = "";
			if ("2".equals(us.getUserLoginType())) {// 一级集体
				enterpriseIdSql = "SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "' UNION SELECT ID FROM PE_ENTERPRISE WHERE FK_PARENT_ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '"
						+ us.getSsoUser().getId() + "')";
			}
			if ("4".equals(us.getUserLoginType())) {// 二级集体
				enterpriseIdSql = "SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'";
			}
			if ("3".equals(us.getUserLoginType())) {// 协会管理员
				sqlBuffer.append(" SELECT * ");
				sqlBuffer.append("   FROM (SELECT PBTC.ID, ");
				sqlBuffer.append("                PBTC.CODE, ");
				sqlBuffer.append("                PBTC.NAME KSNAME, ");
				sqlBuffer.append("                DECODE(PBTC.TEACHER, NULL, '-', PBTC.TEACHER) TEACHER, ");
				sqlBuffer.append("                PBTC.TIME, ");
				sqlBuffer.append("                PBTC.PRICE, ");
				sqlBuffer.append("                EC1.NAME AS KC_TYPE, ");
				sqlBuffer.append("                NVL(COUNT(DISTINCT PBS.ID), 0) BM_CT, ");
				sqlBuffer.append("                to_char(ROUND(DECODE(COUNT(DISTINCT PBS.ID), ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                     WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
				sqlBuffer.append("                                      PBS.ID ");
				sqlBuffer.append("                                     ELSE ");
				sqlBuffer.append("                                      NULL ");
				sqlBuffer.append("                                   END) * 100 / ");
				sqlBuffer.append("                             COUNT(DISTINCT PBS.ID)), ");
				sqlBuffer.append("                      2),'fm9999990.90') || '%' COMPERCENT, ");
				sqlBuffer.append("                DECODE(EC5.CODE,0,'-',to_char(ROUND(DECODE(COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                     WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
				sqlBuffer.append("                                      PBS.ID ");
				sqlBuffer.append("                                     ELSE ");
				sqlBuffer.append("                                      NULL ");
				sqlBuffer.append("                                   END), ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                     WHEN PBTSE.ISPASS = '1' THEN ");
				sqlBuffer.append("                                      PBS.ID ");
				sqlBuffer.append("                                     ELSE ");
				sqlBuffer.append("                                      NULL ");
				sqlBuffer.append("                                   END) * 100 / COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                                        WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
				sqlBuffer.append("                                                         PBS.ID ");
				sqlBuffer.append("                                                        ELSE ");
				sqlBuffer.append("                                                         NULL ");
				sqlBuffer.append("                                                      END)), ");
				sqlBuffer.append("                      2),'fm9999990.90') || '%') KSPERCENT, ");
				sqlBuffer.append("                SCO.INDEXFILE AS INDEXFILE, ");
				sqlBuffer.append("                EC1.NAME FLAGCOURSETYPE, ");
				sqlBuffer.append("                EC2.NAME FLAGCOURSECATEGORY, ");
				sqlBuffer.append("                EC3.NAME FLAGCOURSEITEMTYPE, ");
				sqlBuffer.append("                EC4.NAME FLAGCONTENTPROPERTY, ");
				sqlBuffer.append("                EC6.CODE AS ISFREE, ");
				sqlBuffer.append("                EC.NAME FLAGOFFLINE, ");
				sqlBuffer.append("                EC5.NAME FLAGISEXAM, ");
				sqlBuffer.append("                EC6.NAME FLAGISFREE, ");
				sqlBuffer.append("                B.FS ");
				sqlBuffer.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
				sqlBuffer.append("           JOIN ENUM_CONST EC7 ");
				sqlBuffer.append("             ON PBTC.FLAG_ISVALID = EC7.ID ");
				sqlBuffer.append("            AND PBTC.FLAG_COURSEAREA = 'Coursearea1' ");
				sqlBuffer.append("            AND EC7.CODE = '1' ");
				if (null != courseCode && !"".equals(courseCode))
					sqlBuffer.append("            AND UPPER(PBTC.CODE) LIKE '%" + courseCode.trim().toUpperCase() + "%' ");
				if (null != courseName && !"".equals(courseName))
					sqlBuffer.append("            AND UPPER(PBTC.NAME) LIKE '%" + courseName.trim().toUpperCase() + "%' ");
				if (null != courseTeacher && !"".equals(courseTeacher))
					sqlBuffer.append("            AND UPPER(PBTC.TEACHER) LIKE '%" + courseTeacher.trim().toUpperCase() + "%' ");
				sqlBuffer.append("           JOIN ENUM_CONST EC1 ");
				sqlBuffer.append("             ON PBTC.FLAG_COURSETYPE = EC1.ID ");
				sqlBuffer.append("           JOIN ENUM_CONST EC2 ");
				sqlBuffer.append("             ON PBTC.FLAG_COURSECATEGORY = EC2.ID ");
				sqlBuffer.append("           JOIN ENUM_CONST EC3 ");
				sqlBuffer.append("             ON PBTC.FLAG_COURSE_ITEM_TYPE = EC3.ID ");
				sqlBuffer.append("           JOIN ENUM_CONST EC4 ");
				sqlBuffer.append("             ON PBTC.FLAG_CONTENT_PROPERTY = EC4.ID ");
				sqlBuffer.append("           JOIN ENUM_CONST EC ");
				sqlBuffer.append("             ON PBTC.FLAG_OFFLINE = EC.ID ");
				sqlBuffer.append("           JOIN ENUM_CONST EC5 ");
				sqlBuffer.append("             ON PBTC.FLAG_IS_EXAM = EC5.ID ");
				sqlBuffer.append("           JOIN ENUM_CONST EC6 ");
				sqlBuffer.append("             ON PBTC.FLAG_ISFREE = EC6.ID ");
				sqlBuffer.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
				sqlBuffer.append("             ON PBTC.ID = PBTO.FK_COURSE_ID ");
				sqlBuffer.append("           LEFT JOIN SCORM_SCO_LAUNCH SCO ");
				sqlBuffer.append("             ON PBTC.CODE = SCO.COURSE_ID ");
				sqlBuffer.append("           LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
				sqlBuffer.append("             ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
				sqlBuffer.append("           LEFT JOIN PE_BZZ_STUDENT PBS ");
				sqlBuffer.append("             ON PBTSE.FK_STU_ID = PBS.ID ");
				sqlBuffer.append("           LEFT JOIN TRAINING_COURSE_STUDENT TCS ");
				sqlBuffer.append("             ON PBTSE.FK_TRAINING_ID = TCS.ID ");
				sqlBuffer.append("           LEFT JOIN (SELECT FK_COURSE_ID, TO_CHAR(WM_CONCAT(EC.NAME)) FS ");
				sqlBuffer.append("                       FROM PE_BZZ_TCH_COURSE_SUGGEST PBTCS ");
				sqlBuffer.append("                      INNER JOIN ENUM_CONST EC ");
				sqlBuffer.append("                         ON PBTCS.FK_ENUM_CONST_ID = EC.ID ");
				sqlBuffer.append("                        AND PBTCS.TABLE_NAME = 'PE_BZZ_TCH_COURSE' ");
				sqlBuffer.append("                      GROUP BY FK_COURSE_ID) B ");
				sqlBuffer.append("             ON PBTC.ID = B.FK_COURSE_ID ");
				sqlBuffer.append("          GROUP BY PBTC.ID, ");
				sqlBuffer.append("                   PBTC.CODE, ");
				sqlBuffer.append("                   PBTC.NAME, ");
				sqlBuffer.append("                   PBTC.TEACHER, ");
				sqlBuffer.append("                   PBTC.TIME, ");
				sqlBuffer.append("                   PBTC.PRICE, ");
				sqlBuffer.append("                   EC1.NAME, ");
				sqlBuffer.append("                   SCO.INDEXFILE, ");
				sqlBuffer.append("                   EC1.NAME, ");
				sqlBuffer.append("                   EC2.NAME, ");
				sqlBuffer.append("                   EC3.NAME, ");
				sqlBuffer.append("                   EC4.NAME, ");
				sqlBuffer.append("                   EC6.CODE, ");
				sqlBuffer.append("                   EC.NAME, ");
				sqlBuffer.append("                   EC5.NAME, ");
				sqlBuffer.append("                   EC6.NAME, ");
				sqlBuffer.append("                   B.FS,EC5.CODE) ");
				sqlBuffer.append("  WHERE 1 = 1 ");
			}
			if ("2".equals(us.getUserLoginType()) || "4".equals(us.getUserLoginType())) {// 集体管理员
				sqlBuffer.append(" SELECT * ");
				sqlBuffer.append("   FROM (SELECT PBTC.ID, ");
				sqlBuffer.append("                PBTC.CODE, ");
				sqlBuffer.append("                PBTC.NAME KSNAME, ");
				sqlBuffer.append("                DECODE(PBTC.TEACHER, NULL, '-', PBTC.TEACHER) TEACHER, ");
				sqlBuffer.append("                PBTC.TIME, ");
				sqlBuffer.append("                PBTC.PRICE, ");
				sqlBuffer.append("                EC1.NAME AS KC_TYPE, ");
				sqlBuffer.append("                NVL(COUNT(DISTINCT PBS.ID), 0) BM_CT, ");
				sqlBuffer.append("                ROUND(DECODE(COUNT(DISTINCT PBS.ID), ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                     WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
				sqlBuffer.append("                                      PBS.ID ");
				sqlBuffer.append("                                     ELSE ");
				sqlBuffer.append("                                      NULL ");
				sqlBuffer.append("                                   END) * 100 / ");
				sqlBuffer.append("                             COUNT(DISTINCT PBS.ID)), ");
				sqlBuffer.append("                      2) || '%' COMPERCENT, ");
				sqlBuffer.append("                DECODE(EC5.CODE,0,'-',ROUND(DECODE(COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                     WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
				sqlBuffer.append("                                      PBS.ID ");
				sqlBuffer.append("                                     ELSE ");
				sqlBuffer.append("                                      NULL ");
				sqlBuffer.append("                                   END), ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                     WHEN PBTSE.ISPASS = '1' THEN ");
				sqlBuffer.append("                                      PBS.ID ");
				sqlBuffer.append("                                     ELSE ");
				sqlBuffer.append("                                      NULL ");
				sqlBuffer.append("                                   END) * 100 / COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                                        WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
				sqlBuffer.append("                                                         PBS.ID ");
				sqlBuffer.append("                                                        ELSE ");
				sqlBuffer.append("                                                         NULL ");
				sqlBuffer.append("                                                      END)), ");
				sqlBuffer.append("                      2) || '%') KSPERCENT, ");
				sqlBuffer.append("                SCO.INDEXFILE AS INDEXFILE, ");
				sqlBuffer.append("                EC1.NAME FLAGCOURSETYPE, ");
				sqlBuffer.append("                EC2.NAME FLAGCOURSECATEGORY, ");
				sqlBuffer.append("                EC3.NAME FLAGCOURSEITEMTYPE, ");
				sqlBuffer.append("                EC4.NAME FLAGCONTENTPROPERTY, ");
				sqlBuffer.append("                EC6.CODE AS ISFREE, ");
				sqlBuffer.append("                EC.NAME FLAGOFFLINE, ");
				sqlBuffer.append("                EC5.NAME FLAGISEXAM, ");
				sqlBuffer.append("                EC6.NAME FLAGISFREE, ");
				sqlBuffer.append("                B.FS ");
				sqlBuffer.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
				sqlBuffer.append("           JOIN ENUM_CONST EC7 ");
				sqlBuffer.append("             ON PBTC.FLAG_ISVALID = EC7.ID ");
				sqlBuffer.append("            AND PBTC.FLAG_COURSEAREA = 'Coursearea1' ");
				sqlBuffer.append("            AND EC7.CODE = '1' ");
				if (null != courseCode && !"".equals(courseCode))
					sqlBuffer.append("            AND UPPER(PBTC.CODE) LIKE '%" + courseCode.trim().toUpperCase() + "%' ");
				if (null != courseName && !"".equals(courseName))
					sqlBuffer.append("            AND UPPER(PBTC.NAME) LIKE '%" + courseName.trim().toUpperCase() + "%' ");
				if (null != courseTeacher && !"".equals(courseTeacher))
					sqlBuffer.append("            AND UPPER(PBTC.TEACHER) LIKE '%" + courseTeacher.trim().toUpperCase() + "%' ");
				sqlBuffer.append("           JOIN ENUM_CONST EC1 ");
				sqlBuffer.append("             ON PBTC.FLAG_COURSETYPE = EC1.ID ");
				sqlBuffer.append("           JOIN ENUM_CONST EC2 ");
				sqlBuffer.append("             ON PBTC.FLAG_COURSECATEGORY = EC2.ID ");
				sqlBuffer.append("           JOIN ENUM_CONST EC3 ");
				sqlBuffer.append("             ON PBTC.FLAG_COURSE_ITEM_TYPE = EC3.ID ");
				sqlBuffer.append("           JOIN ENUM_CONST EC4 ");
				sqlBuffer.append("             ON PBTC.FLAG_CONTENT_PROPERTY = EC4.ID ");
				sqlBuffer.append("           JOIN ENUM_CONST EC ");
				sqlBuffer.append("             ON PBTC.FLAG_OFFLINE = EC.ID ");
				sqlBuffer.append("           JOIN ENUM_CONST EC5 ");
				sqlBuffer.append("             ON PBTC.FLAG_IS_EXAM = EC5.ID ");
				sqlBuffer.append("           JOIN ENUM_CONST EC6 ");
				sqlBuffer.append("             ON PBTC.FLAG_ISFREE = EC6.ID ");
				sqlBuffer.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
				sqlBuffer.append("             ON PBTC.ID = PBTO.FK_COURSE_ID ");
				sqlBuffer.append("           LEFT JOIN SCORM_SCO_LAUNCH SCO ");
				sqlBuffer.append("             ON PBTC.CODE = SCO.COURSE_ID ");
				sqlBuffer.append("           LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
				sqlBuffer.append("             ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
				sqlBuffer.append("           LEFT JOIN PE_BZZ_STUDENT PBS ");
				sqlBuffer.append("             ON PBTSE.FK_STU_ID = PBS.ID ");
				sqlBuffer.append("             AND PBS.FK_ENTERPRISE_ID IN (" + enterpriseIdSql + ") ");
				sqlBuffer.append("           LEFT JOIN TRAINING_COURSE_STUDENT TCS ");
				sqlBuffer.append("             ON PBTSE.FK_TRAINING_ID = TCS.ID ");
				sqlBuffer.append("           LEFT JOIN (SELECT FK_COURSE_ID, TO_CHAR(WM_CONCAT(EC.NAME)) FS ");
				sqlBuffer.append("                       FROM PE_BZZ_TCH_COURSE_SUGGEST PBTCS ");
				sqlBuffer.append("                      INNER JOIN ENUM_CONST EC ");
				sqlBuffer.append("                         ON PBTCS.FK_ENUM_CONST_ID = EC.ID ");
				sqlBuffer.append("                        AND PBTCS.TABLE_NAME = 'PE_BZZ_TCH_COURSE' ");
				sqlBuffer.append("                      GROUP BY FK_COURSE_ID) B ");
				sqlBuffer.append("             ON PBTC.ID = B.FK_COURSE_ID ");
				sqlBuffer.append("          GROUP BY PBTC.ID, ");
				sqlBuffer.append("                   PBTC.CODE, ");
				sqlBuffer.append("                   PBTC.NAME, ");
				sqlBuffer.append("                   PBTC.TEACHER, ");
				sqlBuffer.append("                   PBTC.TIME, ");
				sqlBuffer.append("                   PBTC.PRICE, ");
				sqlBuffer.append("                   EC1.NAME, ");
				sqlBuffer.append("                   SCO.INDEXFILE, ");
				sqlBuffer.append("                   EC1.NAME, ");
				sqlBuffer.append("                   EC2.NAME, ");
				sqlBuffer.append("                   EC3.NAME, ");
				sqlBuffer.append("                   EC4.NAME, ");
				sqlBuffer.append("                   EC6.CODE, ");
				sqlBuffer.append("                   EC.NAME, ");
				sqlBuffer.append("                   EC5.NAME, ");
				sqlBuffer.append("                   EC6.NAME, ");
				sqlBuffer.append("                   B.FS, ");
				sqlBuffer.append("                   EC5.CODE) ");
				sqlBuffer.append("  WHERE 1 = 1 ");
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
				/* 建议学习人群 */
				if (name.equals("enumConstByFlagSuggest.name")) {
					sqlBuffer.append(" AND INSTR(FS, '" + value + "', 1, 1) > 0 ");
				}
				/* 是否下线 */
				if (name.equals("enumConstByFlagOffline.name")) {
					name = "FlagOffline";
				}
				/* 是否考试 */
				if (name.equals("enumConstByFlagIsExam.name")) {
					name = "FlagIsExam";
				}
				/* 课程收费状态 */
				if (name.equals("enumConstByFlagIsFree.name")) {
					name = "FlagIsFree";
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
				/* 是否考试 */
				if (temp.equals("enumConstByFlagIsExam.name")) {
					temp = "FlagIsExam";
				}
				/* 课程收费状态 */
				if (temp.equals("enumConstByFlagIsFree.name")) {
					temp = "FlagIsFree";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc") && !"id".equalsIgnoreCase(temp)) {
					sqlBuffer.append(" order by " + temp + " desc ");

				} else {
					if (!"id".equalsIgnoreCase(temp))
						sqlBuffer.append(" order by " + temp + " asc ");
				}
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
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

	public String singleCourseSearch() {
		String courseCode = ServletActionContext.getRequest().getParameter("courseCode");
		if (null == courseCode || "".equals(courseCode))
			courseCode = "null";
		String courseName = ServletActionContext.getRequest().getParameter("courseName");
		if (null == courseName || "".equals(courseName))
			courseName = "null";
		String courseTeacher = ServletActionContext.getRequest().getParameter("courseTeacher");
		if (null == courseTeacher || "".equals(courseTeacher))
			courseTeacher = "null";
		String courseParams = courseCode + "-" + courseName + "-" + courseTeacher;
		ServletActionContext.getRequest().getSession().setAttribute("courseParams", courseParams);
		return "singleCourseSearch";
	}

	public String getCourseParams() {
		return courseParams;
	}

	public void setCourseParams(String courseParams) {
		this.courseParams = courseParams;
	}
}
