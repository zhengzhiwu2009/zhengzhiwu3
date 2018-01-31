package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.bean.EnumConst;
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
 * 公司内训课程查询Action 2014年10月14日
 * 
 * @author Lzh
 * 
 */
public class PeBzzCourseInternalSearchAction extends MyBaseAction<PeBzzTchCourse> {

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
	 * @author Lzh
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		boolean xiehui = true;// 判断协会端登录或者集体端登录
		if (us.getUserLoginType().equals("2") || us.getUserLoginType().equals("4")) {// 集体管理员
			// 获取登陆集体账号的机构ID
			xiehui = false;
		}
		this.getGridConfig().setTitle("公司内训查询");
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程编号"), "code");
		this.getGridConfig().addColumn(this.getText("课程名称"), "ksname");
		this.getGridConfig().addColumn(this.getText("主讲人"), "teacher");
		this.getGridConfig().addColumn(this.getText("课程时长（分钟）"), "courseLen", false, true, true, null);

		ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", false, false, false, "TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
		columnConfigType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnConfigType);
		this.getGridConfig().addColumn(this.getText("课程提供机构"), "orgname", xiehui, false, xiehui, null);
		this.getGridConfig().addColumn(this.getText("报名人数"), "BM_CT", false, true, true, null);

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

		ColumnConfig columnConfigOffline = new ColumnConfig(this.getText("是否下线"), "enumConstByFlagOffline.name", true, true, false, "TextField", false, 100, "");
		String sqly = "select a.id ,a.name from enum_const a where a.namespace='FlagOffline'";
		columnConfigOffline.setComboSQL(sqly);
		this.getGridConfig().addColumn(columnConfigOffline);

		ColumnConfig columnConfigIsExam = new ColumnConfig(this.getText("是否考试"), "enumConstByFlagIsExam.name", true, true, false, "TextField", false, 100, "");
		String sqlx = "select a.id ,a.name from enum_const a where a.namespace='FlagIsExam'";
		columnConfigIsExam.setComboSQL(sqlx);
		this.getGridConfig().addColumn(columnConfigIsExam);


		this.getGridConfig().addRenderFunction(this.getText("查看已报名学员"), "<a href=\"/entity/basic/peInternalStuDetail.action?id=${value}\">查看学员</a>", "id");

		this.getGridConfig().addRenderScript(this.getText("课程信息"),
				"{return '<a href=\"/entity/teaching/peBzzCourseInternalSearch_courseInfo.action?id='+${value}+'&indexFile='+record.data['indexFile']+'\"  target=\"_blank\">课程信息</a>';}", "id");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzTchCourse.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peBzzCourseInternalSearch";
	}

	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);

	}

	public PeBzzTchCourse getBean() {
		return super.superGetBean();
	}

	/**
	 * 课程详细信息
	 * 
	 * @author Lzh
	 * @return
	 */
	public String courseInfo() {
		String id = ServletActionContext.getRequest().getParameter("id");
		EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
		this.indexFile = ServletActionContext.getRequest().getParameter("indexFile");
		if ("undefined".equals(this.indexFile.trim()))
			this.indexFile = "未设置";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT A.CODE,A.NAME,A.TEACHER,a.course_len,A.PRICE,A.STUDYDATES,A.END_DATE, ");
			sb.append(" A.ANSWER_BEGINDATE,A.ANSWER_ENDDATE,EC1.NAME FLAGCOURSETYPE,A.TIME,A.PASSROLE,A.EXAMTIMES_ALLOW, ");
			sb.append(" EC2.NAME FLAGISVISITAFTERPASS,EC3.NAME FLAGISEXAM,EC4.NAME FLAGCOURSECATEGORY,EC5.NAME FLAGCOURSEITEMTYPE, ");
			sb.append(" LISTAGG(C.NAME, ',') WITHIN GROUP(ORDER BY C.NAME) WC,EC7.NAME FLAGISRECOMMEND, ");
			sb.append("	A.PHOTO_LINK,A.TEA_IMG,A.PASSROLE_NOTE,A.SUGGESTION,ssl.indexfile ,a.com_code ,EC6.NAME FLAGCONTENTPROPERTY");
			sb.append(" FROM (SELECT * FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "') A ");
			sb.append(" INNER JOIN ENUM_CONST EC1 ON A.FLAG_COURSETYPE = EC1.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC2 ON A.FLAG_ISVISITAFTERPASS = EC2.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC3 ON A.FLAG_IS_EXAM = EC3.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC4 ON A.FLAG_COURSECATEGORY = EC4.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC5 ON A.FLAG_COURSE_ITEM_TYPE = EC5.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC6 ON A.FLAG_CONTENT_PROPERTY = EC6.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC7 ON A.FLAG_ISRECOMMEND = EC7.ID ");
			sb.append(" LEFT JOIN (SELECT * FROM PE_BZZ_TCH_COURSE_SUGGEST WHERE TABLE_NAME = 'PE_BZZ_TCH_COURSE') B ");
			sb.append(" ON A.ID = B.FK_COURSE_ID ");
			sb.append(" LEFT JOIN ENUM_CONST C ON B.FK_ENUM_CONST_ID = C.ID ");
			sb.append(" left join SCORM_SCO_LAUNCH ssl on ssl.course_id = a.code ");
			sb.append(" GROUP BY A.CODE,A.NAME,A.TEACHER,A.PRICE,A.STUDYDATES,A.END_DATE, a.course_len, EC6.NAME,");
			sb.append(" A.ANSWER_BEGINDATE,A.ANSWER_ENDDATE,EC1.NAME,A.TIME,A.PASSROLE, ");
			sb.append(" A.EXAMTIMES_ALLOW,EC2.NAME,EC3.NAME,EC4.NAME,EC5.NAME,");
			sb.append(" EC7.NAME,a.com_code,A.PHOTO_LINK,A.TEA_IMG,A.PASSROLE_NOTE,A.SUGGESTION,B.TABLE_NAME,ssl.indexfile ");// 删除EC8
			List detailList = this.getGeneralService().getBySQL(sb.toString());
			ServletActionContext.getRequest().setAttribute("courseDetail", detailList);
			this.indexFile = ServletActionContext.getRequest().getParameter("indexFile");
			// this.setBean(this.getGeneralService().getById(id));
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "courseInfo";
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
			StringBuffer sqlBuffer = new StringBuffer();
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			// 查询集体Code
			String comCodeSql = "";
			// 机构ID
			String enterpriseIdSql = "";
			if ("2".equals(us.getUserLoginType())) {// 一级集体
				comCodeSql = " SELECT LOGIN_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'";
				enterpriseIdSql = "SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "' UNION SELECT ID FROM PE_ENTERPRISE WHERE FK_PARENT_ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "')";
			}
			if ("4".equals(us.getUserLoginType())) {// 二级集体
				comCodeSql = "SELECT CODE FROM PE_ENTERPRISE WHERE ID = (SELECT FK_PARENT_ID FROM PE_ENTERPRISE WHERE ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '"
						+ us.getSsoUser().getId() + "'))";
				enterpriseIdSql = "SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'";
			}
			if ("3".equals(us.getUserLoginType())) {// 协会端
				sqlBuffer.append(" SELECT * ");
				sqlBuffer.append("   FROM (SELECT PBTC.ID, ");
				sqlBuffer.append("                PBTC.CODE, ");
				sqlBuffer.append("                PBTC.NAME KSNAME, ");
				sqlBuffer.append("                PBTC.TEACHER, ");
				sqlBuffer.append("                PBTC.COURSE_LEN, ");
				sqlBuffer.append("                EC1.NAME AS KC_TYPE, ");
				sqlBuffer.append("                PE.NAME ORGNAME, ");
				sqlBuffer.append("                COUNT(DISTINCT SI.FK_STU_ID) BM_CT, ");
				sqlBuffer.append("                ROUND(DECODE(COUNT(DISTINCT SI.FK_STU_ID), ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                     WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
				sqlBuffer.append("                                      PBTSE.FK_STU_ID ");
				sqlBuffer.append("                                     ELSE ");
				sqlBuffer.append("                                      NULL ");
				sqlBuffer.append("                                   END) / COUNT(DISTINCT SI.FK_STU_ID)) * 100, ");
				sqlBuffer.append("                      2)||'%' COMPERCENT, ");
				sqlBuffer.append("                DECODE(EC5.CODE,0,'-',ROUND(DECODE(COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                     WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
				sqlBuffer.append("                                      PBTSE.FK_STU_ID ");
				sqlBuffer.append("                                     ELSE ");
				sqlBuffer.append("                                      NULL ");
				sqlBuffer.append("                                   END), ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                     WHEN PBTSE.ISPASS = '1' THEN ");
				sqlBuffer.append("                                      PBTSE.FK_STU_ID ");
				sqlBuffer.append("                                     ELSE ");
				sqlBuffer.append("                                      NULL ");
				sqlBuffer.append("                                   END) / COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                                  WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
				sqlBuffer.append("                                                   PBTSE.FK_STU_ID ");
				sqlBuffer.append("                                                  ELSE ");
				sqlBuffer.append("                                                   NULL ");
				sqlBuffer.append("                                                END)) * 100, ");
				sqlBuffer.append("                      2)||'%') KSPERCENT, ");
				sqlBuffer.append("                SCO.INDEXFILE AS INDEXFILE, ");
				sqlBuffer.append("                EC1.NAME FLAGCOURSETYPE, ");
				sqlBuffer.append("                EC2.NAME FLAGCOURSECATEGORY, ");
				sqlBuffer.append("                EC3.NAME FLAGCOURSEITEMTYPE, ");
				sqlBuffer.append("                EC4.NAME FLAGCONTENTPROPERTY, ");
				sqlBuffer.append("                EC.NAME FLAGOFFLINE, ");
				sqlBuffer.append("                EC5.NAME FLAGISEXAM, ");
				sqlBuffer.append("                B.FS ");
				sqlBuffer.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
				sqlBuffer.append("           JOIN ENUM_CONST EC7 ");
				sqlBuffer.append("             ON PBTC.FLAG_ISVALID = EC7.ID ");
				sqlBuffer.append("            AND EC7.CODE = '1' ");
				sqlBuffer.append("           JOIN ENUM_CONST EC1 ");
				sqlBuffer.append("             ON PBTC.FLAG_COURSETYPE = EC1.ID ");
				sqlBuffer.append("            AND PBTC.FLAG_COURSEAREA = 'Coursearea3' ");
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
				sqlBuffer.append("          INNER  JOIN (SELECT p.name, pem.login_id as code FROM PE_ENTERPRISE p " +
						" INNER JOIN PE_ENTERPRISE_MANAGER pem on pem.fk_enterprise_id = p.id )PE ON PBTC.com_code = pe.code ");
				sqlBuffer.append("           LEFT JOIN SCORM_SCO_LAUNCH SCO ");
				sqlBuffer.append("             ON PBTC.CODE = SCO.COURSE_ID ");
				sqlBuffer.append("           LEFT JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
				sqlBuffer.append("             ON PBTC.ID = PBTO.FK_COURSE_ID ");
				sqlBuffer.append("           left JOIN STU_INTERNAL SI ");
				sqlBuffer.append("             ON PBTO.ID = SI.FK_TCH_OPENCOURSE_ID ");
				sqlBuffer.append("           LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
				sqlBuffer.append("             ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
				sqlBuffer.append("           LEFT JOIN TRAINING_COURSE_STUDENT TCS ");
				sqlBuffer.append("             ON PBTSE.FK_TRAINING_ID = TCS.ID ");
				sqlBuffer.append("           LEFT JOIN PE_BZZ_STUDENT PBS ");
				sqlBuffer.append("             ON SI.FK_STU_ID = PBS.ID ");
				sqlBuffer.append("            AND PBTSE.FK_STU_ID = PBS.ID ");
				sqlBuffer.append("           LEFT JOIN SSO_USER SU ");
				sqlBuffer.append("             ON PBS.FK_SSO_USER_ID = SU.ID ");
				sqlBuffer.append("            AND SU.FLAG_ISVALID = '2' ");
				sqlBuffer.append("           LEFT JOIN (SELECT FK_COURSE_ID, TO_CHAR(WM_CONCAT(EC.NAME)) FS ");
				sqlBuffer.append("                       FROM PE_BZZ_TCH_COURSE_SUGGEST PBTCS ");
				sqlBuffer.append("                      INNER JOIN ENUM_CONST EC ");
				sqlBuffer.append("                         ON PBTCS.FK_ENUM_CONST_ID = EC.ID ");
				sqlBuffer.append("                        AND PBTCS.TABLE_NAME = 'PE_BZZ_TCH_COURSE' ");
				sqlBuffer.append("                      GROUP BY FK_COURSE_ID) B ");
				sqlBuffer.append("             ON PBTC.ID = B.FK_COURSE_ID ");
				sqlBuffer.append("          WHERE 1 = 1 ");
				sqlBuffer.append("          GROUP BY PBTC.ID, ");
				sqlBuffer.append("                   PBTC.CODE, ");
				sqlBuffer.append("                   PBTC.NAME, ");
				sqlBuffer.append("                   PBTC.TEACHER, ");
				sqlBuffer.append("                   PBTC.COURSE_LEN, ");
				sqlBuffer.append("                   EC1.NAME, ");
				sqlBuffer.append("                   PE.NAME, ");
				sqlBuffer.append("                   SCO.INDEXFILE, ");
				sqlBuffer.append("                   EC1.NAME, ");
				sqlBuffer.append("                   EC2.NAME, ");
				sqlBuffer.append("                   EC3.NAME, ");
				sqlBuffer.append("                   EC4.NAME, ");
				sqlBuffer.append("                   EC.NAME, ");
				sqlBuffer.append("                   EC5.NAME, ");
				sqlBuffer.append("                   B.FS,EC5.CODE) where 1=1 ");
			}
			if ("2".equals(us.getUserLoginType()) || "4".equals(us.getUserLoginType())) {// 集体管理员
				sqlBuffer.append(" SELECT * ");
				sqlBuffer.append("   FROM (SELECT PBTC.ID, ");
				sqlBuffer.append("                PBTC.CODE, ");
				sqlBuffer.append("                PBTC.NAME KSNAME, ");
				sqlBuffer.append("                PBTC.TEACHER, ");
				sqlBuffer.append("                PBTC.COURSE_LEN, ");
				sqlBuffer.append("                EC1.NAME AS KC_TYPE, ");
				sqlBuffer.append("                PE.NAME ORGNAME, ");
				sqlBuffer.append("                COUNT(DISTINCT SI.FK_STU_ID) BM_CT, ");
				sqlBuffer.append("                ROUND(DECODE(COUNT(DISTINCT SI.FK_STU_ID), ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                     WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
				sqlBuffer.append("                                      PBTSE.FK_STU_ID ");
				sqlBuffer.append("                                     ELSE ");
				sqlBuffer.append("                                      NULL ");
				sqlBuffer.append("                                   END) / COUNT(DISTINCT SI.FK_STU_ID)) * 100, ");
				sqlBuffer.append("                      2)||'%' COMPERCENT, ");
				sqlBuffer.append("                DECODE(EC5.CODE,0,'-',ROUND(DECODE(COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                     WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
				sqlBuffer.append("                                      PBTSE.FK_STU_ID ");
				sqlBuffer.append("                                     ELSE ");
				sqlBuffer.append("                                      NULL ");
				sqlBuffer.append("                                   END), ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             0, ");
				sqlBuffer.append("                             COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                     WHEN PBTSE.ISPASS = '1' THEN ");
				sqlBuffer.append("                                      PBTSE.FK_STU_ID ");
				sqlBuffer.append("                                     ELSE ");
				sqlBuffer.append("                                      NULL ");
				sqlBuffer.append("                                   END) / COUNT(DISTINCT CASE ");
				sqlBuffer.append("                                                  WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
				sqlBuffer.append("                                                   PBTSE.FK_STU_ID ");
				sqlBuffer.append("                                                  ELSE ");
				sqlBuffer.append("                                                   NULL ");
				sqlBuffer.append("                                                END)) * 100, ");
				sqlBuffer.append("                      2)||'%') KSPERCENT, ");
				sqlBuffer.append("                SCO.INDEXFILE AS INDEXFILE, ");
				sqlBuffer.append("                EC1.NAME FLAGCOURSETYPE, ");
				sqlBuffer.append("                EC2.NAME FLAGCOURSECATEGORY, ");
				sqlBuffer.append("                EC3.NAME FLAGCOURSEITEMTYPE, ");
				sqlBuffer.append("                EC4.NAME FLAGCONTENTPROPERTY, ");
				sqlBuffer.append("                EC.NAME FLAGOFFLINE, ");
				sqlBuffer.append("                EC5.NAME FLAGISEXAM, ");
				sqlBuffer.append("                B.FS ");
				sqlBuffer.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
				sqlBuffer.append("           JOIN ENUM_CONST EC7 ");
				sqlBuffer.append("             ON PBTC.FLAG_ISVALID = EC7.ID ");
				sqlBuffer.append("             AND PBTC.COM_CODE = (" + comCodeSql + ") ");
				sqlBuffer.append("            AND EC7.CODE = '1' ");
				sqlBuffer.append("           JOIN ENUM_CONST EC1 ");
				sqlBuffer.append("             ON PBTC.FLAG_COURSETYPE = EC1.ID ");
				sqlBuffer.append("            AND PBTC.FLAG_COURSEAREA = 'Coursearea3' ");
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
				sqlBuffer.append("          left JOIN PE_ENTERPRISE PE ");
				sqlBuffer.append("             ON PBTC.COM_CODE = PE.CODE ");
				sqlBuffer.append("           LEFT JOIN SCORM_SCO_LAUNCH SCO ");
				sqlBuffer.append("             ON PBTC.CODE = SCO.COURSE_ID ");
				sqlBuffer.append("           LEFT JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
				sqlBuffer.append("             ON PBTC.ID = PBTO.FK_COURSE_ID ");
				sqlBuffer.append("           left JOIN STU_INTERNAL SI ");
				sqlBuffer.append("             ON PBTO.ID = SI.FK_TCH_OPENCOURSE_ID ");
				sqlBuffer.append("           LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
				sqlBuffer.append("             ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
				sqlBuffer.append("           LEFT JOIN TRAINING_COURSE_STUDENT TCS ");
				sqlBuffer.append("             ON PBTSE.FK_TRAINING_ID = TCS.ID ");
				sqlBuffer.append("           LEFT JOIN PE_BZZ_STUDENT PBS ");
				sqlBuffer.append("             ON SI.FK_STU_ID = PBS.ID ");
				sqlBuffer.append("            AND PBTSE.FK_STU_ID = PBS.ID ");
				sqlBuffer.append("            AND PBS.FK_ENTERPRISE_ID IN (" + enterpriseIdSql + ") ");
				sqlBuffer.append("           LEFT JOIN SSO_USER SU ");
				sqlBuffer.append("             ON PBS.FK_SSO_USER_ID = SU.ID ");
				sqlBuffer.append("            AND SU.FLAG_ISVALID = '2' ");
				sqlBuffer.append("           LEFT JOIN (SELECT FK_COURSE_ID, TO_CHAR(WM_CONCAT(EC.NAME)) FS ");
				sqlBuffer.append("                       FROM PE_BZZ_TCH_COURSE_SUGGEST PBTCS ");
				sqlBuffer.append("                      INNER JOIN ENUM_CONST EC ");
				sqlBuffer.append("                         ON PBTCS.FK_ENUM_CONST_ID = EC.ID ");
				sqlBuffer.append("                        AND PBTCS.TABLE_NAME = 'PE_BZZ_TCH_COURSE' ");
				sqlBuffer.append("                      GROUP BY FK_COURSE_ID) B ");
				sqlBuffer.append("             ON PBTC.ID = B.FK_COURSE_ID ");
				sqlBuffer.append("          WHERE 1 = 1 ");
				sqlBuffer.append("          GROUP BY PBTC.ID, ");
				sqlBuffer.append("                   PBTC.CODE, ");
				sqlBuffer.append("                   PBTC.NAME, ");
				sqlBuffer.append("                   PBTC.TEACHER, ");
				sqlBuffer.append("                   PBTC.COURSE_LEN, ");
				sqlBuffer.append("                   EC1.NAME, ");
				sqlBuffer.append("                   PE.NAME, ");
				sqlBuffer.append("                   SCO.INDEXFILE, ");
				sqlBuffer.append("                   EC1.NAME, ");
				sqlBuffer.append("                   EC2.NAME, ");
				sqlBuffer.append("                   EC3.NAME, ");
				sqlBuffer.append("                   EC4.NAME, ");
				sqlBuffer.append("                   EC.NAME, ");
				sqlBuffer.append("                   EC5.NAME, ");
				sqlBuffer.append("                   B.FS,EC5.CODE) where 1=1 ");
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
				if (!name.equals("enumConstByFlagSuggest.name")) {
					/* 查看一级机构 */
					if (name.equals("orgname")) {
						sqlBuffer.append(" and UPPER(orgname) like UPPER('%" + value + "%')");
					} else if ("FlagCourseType".equals(name) || "FlagCourseCategory".equals(name) || "FlagCourseItemType".equals(name) || "FlagContentProperty".equals(name)
							|| "FlagIsvalid".equals(name) || "FlagOffline".equals(name) || "FlagIsExam".equals(name)) {
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
				/* 课程时长排序 */
				if (temp.equals("courseLen")) {
					temp = "to_number(course_len)";
				}
				/* 机构排序 */
				if (temp.equals("comCode")) {
					temp = "orgname";
				}
				if(temp.equals("compercent")){
					temp = "to_number(replace(compercent,'%',''))";
				}
				if(temp.equals("KSpercent")){
					temp = "to_number(replace(replace(KSpercent,'-','-0.1'),'%',''))";
				}				
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sqlBuffer.append(" order by " + temp + " desc ");

				} else {
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

}
