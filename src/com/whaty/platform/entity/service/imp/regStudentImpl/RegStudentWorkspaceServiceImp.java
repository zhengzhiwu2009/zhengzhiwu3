package com.whaty.platform.entity.service.imp.regStudentImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.cxf.ws.security.policy.SP11Constants;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.framework.cache.aopcache.annotation.Cacheable;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.dao.EnumConstDAO;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.regStudent.RegStudentWorkspaceService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.training.basic.StudyProgress;

public class RegStudentWorkspaceServiceImp implements RegStudentWorkspaceService {

	private GeneralDao generalDao;
	private EnumConstDAO enumConstDao;

	public EnumConstDAO getEnumConstDao() {
		return enumConstDao;
	}

	public void setEnumConstDao(EnumConstDAO enumConstDao) {
		this.enumConstDao = enumConstDao;
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	@Override
	public List<EnumConst> initCouresType(String namespace) throws EntityException {
		DetachedCriteria dcType = DetachedCriteria.forClass(EnumConst.class);
		dcType.add(Restrictions.eq("namespace", "FlagCourseType"));
		List<EnumConst> courseTypeList = this.generalDao.getList(dcType);
		return courseTypeList;
	}

	@Override
	public List initCourseArea(String params) {
		DetachedCriteria dcCategory = DetachedCriteria.forClass(EnumConst.class);
		dcCategory.add(Restrictions.eq("namespace", "FlagCoursearea"));
		List<EnumConst> courseAreaList = this.generalDao.getList(dcCategory);
		return courseAreaList;
	}

	@Override
	public List initLearnStatus(String params) {
		DetachedCriteria dcLearnStatus = DetachedCriteria.forClass(EnumConst.class);
		dcLearnStatus.add(Restrictions.eq("namespace", "LearnStatus"));
		List<EnumConst> learnStatusList = this.generalDao.getList(dcLearnStatus);
		return learnStatusList;
	}

	@Override
	public List<EnumConst> initCourseCategory(String namespace) throws EntityException {
		DetachedCriteria dcCategory = DetachedCriteria.forClass(EnumConst.class);
		dcCategory.add(Restrictions.eq("namespace", "FlagCourseCategory"));
		List<EnumConst> courseCategoryList = this.generalDao.getList(dcCategory);
		return courseCategoryList;
	}

	@Override
	public List initCourseContent(String params) {
		DetachedCriteria dcCategory = DetachedCriteria.forClass(EnumConst.class);
		dcCategory.add(Restrictions.eq("namespace", "FlagContentProperty"));
		List<EnumConst> courseCategoryList = this.generalDao.getList(dcCategory);
		return courseCategoryList;
	}

	@Override
	public List<EnumConst> initCourseItemType(String namespace) throws EntityException {
		DetachedCriteria dcEnumConst = DetachedCriteria.forClass(EnumConst.class);
		dcEnumConst.add(Restrictions.eq("namespace", namespace));
		List<EnumConst> courseCategoryList = this.generalDao.getList(dcEnumConst);
		return courseCategoryList;
	}

	/**
	 * 正在使用
	 */
	@Override
	public Page initLearingCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName, String time, String courseItemType, String courseContent, String suggestren, String teacher, String coursearea, String learnStatus, String orderInfo, String orderType, int pageSize, int startIndex)
			throws EntityException {
		Page page = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ( ");
		sb.append("SELECT PBTC.ID, ");
		sb.append("       PBTC.CODE, ");
		sb.append("       PBTC.NAME, ");
		sb.append("		  PBTC.TEACHER, ");
		sb.append("       EC2.NAME COURSEAREA, ");
		sb.append("       EC1.NAME COURSETYPE, ");
		sb.append("       PBTC.TIME, ");
		sb.append("       PBTC.END_DATE, ");
		sb.append("       TCS.LEARN_STATUS, TCS.GET_DATE START_TIME, SSS.LAST_ACCESSDATE LAST_TIME ");
		sb.append("  FROM PE_BZZ_TCH_COURSE PBTC ");
		sb.append("  LEFT JOIN ENUM_CONST EC1 ON PBTC.FLAG_COURSETYPE = EC1.ID ");
		sb.append("  JOIN ENUM_CONST EC2 ON PBTC.FLAG_COURSEAREA = EC2.ID ");
		sb.append("  JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("  JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID AND PBTSE.FK_STU_ID = '" + stuId + "' ");
		sb.append("  JOIN TRAINING_COURSE_STUDENT TCS ON TCS.ID = PBTSE.FK_TRAINING_ID ");
		sb.append("  JOIN SSO_USER SU ON SU.ID = TCS.STUDENT_ID ");
		sb.append("  LEFT JOIN SCORM_STU_SCO SSS ON SU.ID = SSS.STUDENT_ID AND PBTC.CODE = SSS.COURSE_ID ");
		sb.append("  JOIN PE_BZZ_STUDENT PBS ON PBS.FK_SSO_USER_ID = SU.ID ");
		// sb.append(" WHERE TCS.LEARN_STATUS <> 'COMPLETED' ");
		sb.append("   WHERE PBS.ID = '" + stuId + "' ");
		sb.append("  AND EC2.CODE IN('1', '2', '5')");
		if (null != courseCategory && !"".equals(courseCategory))// 业务分类
			sb.append(" AND PBTC.FLAG_COURSECATEGORY = '" + courseCategory + "' ");
		if (null != courseType && !"".equals(courseType))// 课程性质
			sb.append(" AND PBTC.FLAG_COURSETYPE = '" + courseType + "' ");
		if (null != courseCode && !"".equals(courseCode))// 课程编号
			sb.append(" AND UPPER(PBTC.CODE) LIKE '%" + courseCode.toUpperCase() + "%' ");
		if (null != courseName && !"".equals(courseName))// 课程名称
			sb.append(" AND UPPER(PBTC.NAME) LIKE '%" + courseName.toUpperCase() + "%' ");
		if (null != time && !"".equals(time))// 学时
			sb.append(" AND PBTC.TIME LIKE '%" + time + "%' ");
		if (null != courseItemType && !"".equals(courseItemType))// 大纲分类
			sb.append(" AND PBTC.FLAG_COURSE_ITEM_TYPE = '" + courseItemType + "' ");
		if (null != courseContent && !"".equals(courseContent))// 内容属性
			sb.append(" AND PBTC.FLAG_CONTENT_PROPERTY = '" + courseContent + "' ");
		if (null != coursearea && !"".equals(coursearea))// 所属区域
			sb.append(" AND PBTC.FLAG_COURSEAREA = '" + coursearea + "' ");
		if (null != teacher && !"".equals(teacher))// 主讲人
			sb.append(" AND UPPER(PBTC.TEACHER) LIKE '%" + teacher.toUpperCase() + "%' ");
		if (null != suggestren && !"".equals(suggestren))// 建议学习人群
			sb.append(" AND INSTR(FS, '" + suggestren + "', 1, 1) > 0 ");
		if (null != learnStatus && !"".equals(learnStatus)) {// 学习状态
			if (learnStatus.indexOf("0") > -1) {// 未开始学习
				sb.append(" AND TCS.LEARN_STATUS = 'UNCOMPLETE' ");
			} else if (learnStatus.indexOf("1") > -1) {// 正在学习
				sb.append(" AND TCS.LEARN_STATUS = 'INCOMPLETE' ");
			} else if (learnStatus.indexOf("2") > -1) {// 已完成学习
				sb.append(" AND TCS.LEARN_STATUS = 'COMPLETED' ");
			}
		}
		sb.append(" ) ");
		if(orderInfo != null && !"".equals(orderInfo)) {
			sb.append(" ORDER BY " + orderInfo + " " + orderType);
		} else {
			sb.append(" ORDER BY CODE DESC ");
		}
		try {
			page = this.generalDao.getByPageSQL(sb.toString(), pageSize, startIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public Page initLearingCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName, String time, String courseItemType, String courseContent, String suggestren, String teacher, String coursearea, int pageSize, int startIndex) throws EntityException {
		Page page = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ( ");
		sb.append("SELECT PBTC.ID, ");
		sb.append("       PBTC.CODE, ");
		sb.append("       PBTC.NAME, ");
		sb.append("		  PBTC.TEACHER, ");
		sb.append("       EC2.NAME COURSEAREA, ");
		sb.append("       EC1.NAME COURSETYPE, ");
		sb.append("       PBTC.TIME, ");
		sb.append("       PBTC.END_DATE, ");
		sb.append("       TCS.LEARN_STATUS ");
		sb.append("  FROM PE_BZZ_TCH_COURSE PBTC ");
		sb.append("  LEFT JOIN ENUM_CONST EC1 ON PBTC.FLAG_COURSETYPE = EC1.ID ");
		sb.append("  JOIN ENUM_CONST EC2 ON PBTC.FLAG_COURSEAREA = EC2.ID ");
		sb.append("  JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("  JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
		sb.append("  JOIN TRAINING_COURSE_STUDENT TCS ON TCS.ID = PBTSE.FK_TRAINING_ID ");
		sb.append("  JOIN SSO_USER SU ON SU.ID = TCS.STUDENT_ID ");
		sb.append("  JOIN PE_BZZ_STUDENT PBS ON PBS.FK_SSO_USER_ID = SU.ID ");
		// sb.append(" WHERE TCS.LEARN_STATUS <> 'COMPLETED' ");
		sb.append("   WHERE PBS.ID = '" + stuId + "' ");
		sb.append("  AND EC2.CODE IN('1', '2', '5')");
		if (null != courseCategory && !"".equals(courseCategory))// 业务分类
			sb.append(" AND PBTC.FLAG_COURSECATEGORY = '" + courseCategory + "' ");
		if (null != courseType && !"".equals(courseType))// 课程性质
			sb.append(" AND PBTC.FLAG_COURSETYPE = '" + courseType + "' ");
		if (null != courseCode && !"".equals(courseCode))// 课程编号
			sb.append(" AND UPPER(PBTC.CODE) LIKE '%" + courseCode.toUpperCase() + "%' ");
		if (null != courseName && !"".equals(courseName))// 课程名称
			sb.append(" AND UPPER(PBTC.NAME) LIKE '%" + courseName.toUpperCase() + "%' ");
		if (null != time && !"".equals(time))// 学时
			sb.append(" AND PBTC.TIME LIKE '%" + time + "%' ");
		if (null != courseItemType && !"".equals(courseItemType))// 大纲分类
			sb.append(" AND PBTC.FLAG_COURSE_ITEM_TYPE = '" + courseItemType + "' ");
		if (null != courseContent && !"".equals(courseContent))// 内容属性
			sb.append(" AND PBTC.FLAG_CONTENT_PROPERTY = '" + courseContent + "' ");
		if (null != coursearea && !"".equals(coursearea))// 所属区域
			sb.append(" AND PBTC.FLAG_COURSEAREA = '" + coursearea + "' ");
		if (null != teacher && !"".equals(teacher))// 主讲人
			sb.append(" AND UPPER(PBTC.TEACHER) LIKE '%" + teacher.toUpperCase() + "%' ");
		if (null != suggestren && !"".equals(suggestren))// 建议学习人群
			sb.append(" AND INSTR(FS, '" + suggestren + "', 1, 1) > 0 ");
		sb.append(" ) ORDER BY CODE DESC ");
		try {
			page = this.generalDao.getByPageSQL(sb.toString(), pageSize, startIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public PeBzzStudent initStudentInfo(String userId) {
		DetachedCriteria peStudentDC = DetachedCriteria.forClass(PeBzzStudent.class);
		peStudentDC.createAlias("ssoUser", "ssoUser");
		peStudentDC.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee", peStudentDC.LEFT_JOIN);
		peStudentDC.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType", DetachedCriteria.LEFT_JOIN);
		peStudentDC.createCriteria("peEnterprise", "peEnterprise");
		peStudentDC.add(Restrictions.eq("ssoUser.id", userId));
		List peStudentList = new ArrayList();
		peStudentList = this.generalDao.getList(peStudentDC);
		return (PeBzzStudent) peStudentList.get(0);
	}

	@Override
	public List initSuggestRen(String params) {
		DetachedCriteria dcCategory = DetachedCriteria.forClass(EnumConst.class);
		dcCategory.add(Restrictions.eq("namespace", "FlagSuggest"));
		List<EnumConst> suggestRenList = this.generalDao.getList(dcCategory);
		return suggestRenList;
	}

	@Override
	public List iniziLiao(String params) {
		DetachedCriteria dcCategory = DetachedCriteria.forClass(EnumConst.class);
		dcCategory.add(Restrictions.eq("namespace", "FlagZltype"));
		List<EnumConst> ziLiaolist = this.generalDao.getList(dcCategory);
		return ziLiaolist;
	}

	@Override
	public List tongjiLearningCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName, String time, String courseItemType, String courseContent, String suggestren, String teacher, String coursearea) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT NVL(SUM(C.TIME),0)||','|| ");
		sb.append("        NVL(SUM(CASE ");
		sb.append("              WHEN D.LEARN_STATUS = 'INCOMPLETE' THEN ");
		sb.append("               C.TIME ");
		sb.append("              ELSE ");
		sb.append("               NULL ");
		sb.append("            END),0)||','|| ");
		sb.append("        NVL(SUM(CASE ");
		sb.append("              WHEN D.LEARN_STATUS = 'UNCOMPLETE' THEN ");
		sb.append("               C.TIME ");
		sb.append("              ELSE ");
		sb.append("               NULL ");
		sb.append("            END),0) ");
		sb.append("   FROM PR_BZZ_TCH_STU_ELECTIVE A ");
		sb.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE B ");
		sb.append("     ON A.FK_TCH_OPENCOURSE_ID = B.ID ");
		sb.append("    AND A.FK_STU_ID = '" + stuId + "' ");
		sb.append("  INNER JOIN TRAINING_COURSE_STUDENT D ");
		sb.append("     ON A.FK_TRAINING_ID = D.ID ");
		sb.append("  INNER JOIN PE_BZZ_TCH_COURSE C ");
		sb.append("     ON B.FK_COURSE_ID = C.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC1 ");
		sb.append("     ON C.FLAG_COURSEAREA = EC1.ID ");
		sb.append("    AND EC1.CODE <> '0' ");
		sb.append("  INNER JOIN ENUM_CONST EC2 ");
		sb.append("     ON C.FLAG_COURSETYPE = EC2.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC4 ");
		sb.append("     ON C.FLAG_ISFREE = EC4.ID ");
		sb.append("           LEFT JOIN (SELECT FK_COURSE_ID, ");
		sb.append("                            TO_CHAR(WM_CONCAT(FK_ENUM_CONST_ID)) FS ");
		sb.append("                       FROM PE_BZZ_TCH_COURSE_SUGGEST ");
		sb.append("                      WHERE TABLE_NAME = 'PE_BZZ_TCH_COURSE' ");
		sb.append("                      GROUP BY FK_COURSE_ID) F ");
		sb.append("             ON C.ID = F.FK_COURSE_ID WHERE 1 = 1 ");
		if (null != courseCategory && !"".equals(courseCategory))// 业务分类
			sb.append(" AND C.FLAG_COURSECATEGORY = '" + courseCategory + "' ");
		if (null != courseType && !"".equals(courseType))// 课程性质
			sb.append(" AND C.FLAG_COURSETYPE = '" + courseType + "' ");
		if (null != courseCode && !"".equals(courseCode))// 课程编号
			sb.append(" AND UPPER(C.CODE) LIKE '%" + courseCode.toUpperCase() + "%' ");
		if (null != courseName && !"".equals(courseName))// 课程名称
			sb.append(" AND UPPER(C.NAME) LIKE '%" + courseName.toUpperCase() + "%' ");
		if (null != time && !"".equals(time))// 学时
			sb.append(" AND C.TIME LIKE '%" + time + "%' ");
		if (null != courseItemType && !"".equals(courseItemType))// 大纲分类
			sb.append(" AND C.FLAG_COURSE_ITEM_TYPE = '" + courseItemType + "' ");
		if (null != courseContent && !"".equals(courseContent))// 内容属性
			sb.append(" AND C.FLAG_CONTENT_PROPERTY = '" + courseContent + "' ");
		if (null != coursearea && !"".equals(coursearea))// 所属区域
			sb.append(" AND C.FLAG_COURSEAREA = '" + coursearea + "' ");
		if (null != teacher && !"".equals(teacher))// 主讲人
			sb.append(" AND UPPER(C.TEACHER) LIKE '%" + teacher.toUpperCase() + "%' ");
		if (null != suggestren && !"".equals(suggestren))// 建议学习人群
			sb.append(" AND INSTR(FS, '" + suggestren + "', 1, 1) > 0 ");
		List list = this.getGeneralDao().getBySQL(sb.toString());
		return list;
	}

	@Override
	public List tongjiLearningCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName, String time, String courseItemType, String courseContent, String suggestren, String teacher, String coursearea, String learnStatus) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT NVL(SUM(C.TIME),0)||','|| ");
		sb.append("        NVL(SUM(CASE ");
		sb.append("              WHEN D.LEARN_STATUS = 'INCOMPLETE' THEN ");
		sb.append("               C.TIME ");
		sb.append("              ELSE ");
		sb.append("               NULL ");
		sb.append("            END),0)||','|| ");
		sb.append("        NVL(SUM(CASE ");
		sb.append("              WHEN D.LEARN_STATUS = 'UNCOMPLETE' THEN ");
		sb.append("               C.TIME ");
		sb.append("              ELSE ");
		sb.append("               NULL ");
		sb.append("            END),0) ");
		sb.append("   FROM PR_BZZ_TCH_STU_ELECTIVE A ");
		sb.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE B ");
		sb.append("     ON A.FK_TCH_OPENCOURSE_ID = B.ID ");
		sb.append("    AND A.FK_STU_ID = '" + stuId + "' ");
		sb.append("  INNER JOIN TRAINING_COURSE_STUDENT D ");
		sb.append("     ON A.FK_TRAINING_ID = D.ID ");
		sb.append("  INNER JOIN PE_BZZ_TCH_COURSE C ");
		sb.append("     ON B.FK_COURSE_ID = C.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC1 ");
		sb.append("     ON C.FLAG_COURSEAREA = EC1.ID ");
		sb.append("    AND EC1.CODE <> '0' ");
		sb.append("  INNER JOIN ENUM_CONST EC2 ");
		sb.append("     ON C.FLAG_COURSETYPE = EC2.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC4 ");
		sb.append("     ON C.FLAG_ISFREE = EC4.ID ");
		sb.append("           LEFT JOIN (SELECT FK_COURSE_ID, ");
		sb.append("                            TO_CHAR(WM_CONCAT(FK_ENUM_CONST_ID)) FS ");
		sb.append("                       FROM PE_BZZ_TCH_COURSE_SUGGEST ");
		sb.append("                      WHERE TABLE_NAME = 'PE_BZZ_TCH_COURSE' ");
		sb.append("                      GROUP BY FK_COURSE_ID) F ");
		sb.append("             ON C.ID = F.FK_COURSE_ID WHERE 1 = 1 ");
		if (null != courseCategory && !"".equals(courseCategory))// 业务分类
			sb.append(" AND C.FLAG_COURSECATEGORY = '" + courseCategory + "' ");
		if (null != courseType && !"".equals(courseType))// 课程性质
			sb.append(" AND C.FLAG_COURSETYPE = '" + courseType + "' ");
		if (null != courseCode && !"".equals(courseCode))// 课程编号
			sb.append(" AND UPPER(C.CODE) LIKE '%" + courseCode.toUpperCase() + "%' ");
		if (null != courseName && !"".equals(courseName))// 课程名称
			sb.append(" AND UPPER(C.NAME) LIKE '%" + courseName.toUpperCase() + "%' ");
		if (null != time && !"".equals(time))// 学时
			sb.append(" AND C.TIME LIKE '%" + time + "%' ");
		if (null != courseItemType && !"".equals(courseItemType))// 大纲分类
			sb.append(" AND C.FLAG_COURSE_ITEM_TYPE = '" + courseItemType + "' ");
		if (null != courseContent && !"".equals(courseContent))// 内容属性
			sb.append(" AND C.FLAG_CONTENT_PROPERTY = '" + courseContent + "' ");
		if (null != coursearea && !"".equals(coursearea))// 所属区域
			sb.append(" AND C.FLAG_COURSEAREA = '" + coursearea + "' ");
		if (null != teacher && !"".equals(teacher))// 主讲人
			sb.append(" AND UPPER(C.TEACHER) LIKE '%" + teacher.toUpperCase() + "%' ");
		if (null != suggestren && !"".equals(suggestren))// 建议学习人群
			sb.append(" AND INSTR(FS, '" + suggestren + "', 1, 1) > 0 ");
		if (null != learnStatus && !"".equals(learnStatus)) {// 学习状态
			if (learnStatus.indexOf("0") > -1) {// 未开始学习
				sb.append(" AND D.LEARN_STATUS = 'UNCOMPLETE' ");
			} else if (learnStatus.indexOf("1") > -1) {// 正在学习
				sb.append(" AND D.LEARN_STATUS = 'INCOMPLETE' ");
			} else if (learnStatus.indexOf("2") > -1) {// 已完成学习
				sb.append(" AND D.LEARN_STATUS = 'COMPLETED' ");
			}
		}
		List list = this.getGeneralDao().getBySQL(sb.toString());
		return list;
	}

	/**
	 * 查询个人主页条目数量的方法
	 */
	public List getNum(String method, String loginId, String studentId) throws Exception {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sb = new StringBuffer();
		if ("zhanneixin".equals(method)) {// 未读站内信
			sb.append(" SELECT COUNT(*) FROM ( ");
			sb.append(" SELECT S.ID, S.TITLE, PM.TRUE_NAME AS TRUE_NAME, S.SENDDATE, S.STATUS ");
			sb.append("   FROM SITE_EMAIL S, PE_MANAGER PM ");
			sb.append("  WHERE S.SENDER_ID = PM.LOGIN_ID ");
			sb.append("    AND S.ADDRESSEE_DEL = 0 ");
			sb.append("    AND S.STATUS = 0 ");
			sb.append("    AND S.ADDRESSEE_ID = '" + loginId + "' ");
			sb.append(" UNION ");
			sb.append(" SELECT S.ID, S.TITLE, PEM.NAME AS TRUE_NAME, S.SENDDATE, S.STATUS ");
			sb.append("   FROM SITE_EMAIL S, PE_ENTERPRISE_MANAGER PEM ");
			sb.append("  WHERE S.SENDER_ID = PEM.LOGIN_ID ");
			sb.append("    AND S.ADDRESSEE_DEL = 0 ");
			sb.append("    AND S.STATUS = 0 ");
			sb.append("    AND S.ADDRESSEE_ID = '" + loginId + "' ");
			sb.append(" UNION ");
			sb.append(" SELECT S.ID, S.TITLE, PS.TRUE_NAME, S.SENDDATE, S.STATUS ");
			sb.append("   FROM SITE_EMAIL S, PE_BZZ_STUDENT PS ");
			sb.append("  WHERE S.SENDER_ID = PS.REG_NO ");
			sb.append("    AND S.ADDRESSEE_DEL = 0 ");
			sb.append("    AND S.STATUS = 0 ");
			sb.append("    AND S.ADDRESSEE_ID = '" + loginId + "') ");
		}
		if ("diaochawenjuan".equals(method)) {// 可填写的调查问卷
			String sql = "select ec.code from pe_bzz_student pbs join sso_user su on pbs.fk_sso_user_id = su.id join enum_const ec on ec.id = pbs.zige where su.login_id = '" + userSession.getLoginId() + "' ";
			String zige = (String) this.getGeneralDao().getBySQL(sql).get(0);
			sb.append(" SELECT COUNT(*) ");
			sb.append("   FROM PE_VOTE_PAPER A ");
			sb.append("  INNER JOIN ENUM_CONST B ");
			sb.append("     ON A.FLAG_ISVALID = B.ID ");
			sb.append("  INNER JOIN ENUM_CONST C ");
			sb.append("     ON A.FLAG_TYPE = C.ID ");
			sb.append("  WHERE A.TYPE IS NULL ");
			sb.append("    AND B.CODE = '1' ");
			sb.append("    AND (C.CODE = '" + zige + "' OR (C.CODE = '9' OR C.CODE = '90'))");
			sb.append("    AND TO_DATE(TO_CHAR(START_DATE, 'yyyy-MM-dd'), 'yyyy-mm-dd') <= to_date(to_char(SYSDATE,'yyyy-mm-dd'),'yyyy-mm-dd') and   TO_DATE(TO_CHAR(end_date, 'yyyy-MM-dd'), 'yyyy-mm-dd') >= to_date(to_char(SYSDATE,'yyyy-mm-dd'),'yyyy-mm-dd')");// 加了一结束日期判断
		}
		if ("daikaoshikecheng".equals(method)) {// 待考试课程
			sb.append(" SELECT COUNT(*),SUM(TIME) ");
			sb.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
			sb.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sb.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sb.append("  INNER JOIN (SELECT FK_STU_ID, ");
			sb.append("                     FK_TRAINING_ID, ");
			sb.append("                     FK_TCH_OPENCOURSE_ID, ");
			sb.append("                     SCORE_EXAM, ");
			sb.append("                     EXAM_TIMES, ");
			sb.append("                     COMPLETED_TIME, ");
			sb.append("                     START_TIME ");
			sb.append("                FROM PR_BZZ_TCH_STU_ELECTIVE ");
			sb.append("               WHERE FK_STU_ID = '" + studentId + "') PBTSE ");
			sb.append("     ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
			sb.append("  INNER JOIN (SELECT TC.ID, TC.LEARN_STATUS, TC.GET_DATE ");
			sb.append("                FROM TRAINING_COURSE_STUDENT TC ");
			sb.append("               JOIN SSO_USER SU ON TC.STUDENT_ID = SU.ID ");
			sb.append("               JOIN PE_BZZ_STUDENT PBS ON PBS.FK_SSO_USER_ID = SU.ID ");
			sb.append("               WHERE PBS.ID = '" + studentId + "' ");
			sb.append("                 AND TC.LEARN_STATUS = 'COMPLETED') TCS ");
			sb.append("     ON PBTSE.FK_TRAINING_ID = TCS.ID ");
			sb.append("  INNER JOIN PE_BZZ_STUDENT PBS ");
			sb.append("     ON PBTSE.FK_STU_ID = PBS.ID ");
			sb.append("  WHERE 1 = 1 AND ( ");
			// 注释掉免考课程条件：原逻辑：免考课程或者未通过考试的课程
			// sb.append(" (PBTC.FLAG_IS_EXAM =
			// '40288acf3ae01103013ae0130d030002' AND PBTSE.COMPLETED_TIME IS
			// NULL) OR ( ");
			sb.append("        PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001' AND NVL(PBTSE.SCORE_EXAM, 0) < NVL(PBTC.PASSROLE, 0) ");
			// sb.append(" ) ");
			sb.append("    ) ");
		}
		
		if ("tongzhigonggao".equals(method)) {// 通知公告
//			StringBuffer sbCodeBuffer = new StringBuffer();
//			sbCodeBuffer.append(" SELECT CODE FROM PE_ENTERPRISE WHERE ID = (SELECT T1.ID ");
//			sbCodeBuffer.append(" FROM (SELECT (CASE WHEN PE.FK_PARENT_ID IS NULL THEN ");
//			sbCodeBuffer.append(" PE.ID ELSE PE.FK_PARENT_ID END) ID ");
//			sbCodeBuffer.append(" FROM PE_ENTERPRISE PE, PE_BZZ_STUDENT STU ");
//			sbCodeBuffer.append(" WHERE PE.ID = STU.FK_ENTERPRISE_ID ");
//			sbCodeBuffer.append(" AND STU.FK_SSO_USER_ID = (SELECT ID FROM SSO_USER WHERE LOGIN_ID = '" + loginId + "')) T1) ");
//			List codeList = this.getGeneralDao().getBySQL(sbCodeBuffer.toString());
//			String enterpriseCode = "-1";
//			if (null != codeList && codeList.size() > 0)
//				enterpriseCode = codeList.get(0).toString();
//			sb.append(" SELECT COUNT(PB.ID) FROM PE_BULLETIN PB, PE_MANAGER PE, ENUM_CONST EC ");
//			sb.append(" WHERE PE.ID = PB.FK_MANAGER_ID AND EC.ID = PB.FLAG_ISVALID ");
//			sb.append(" AND EC.CODE = '1' AND PB.SCOPE_STRING LIKE '%ff808081493288bd0149335225b90036%' AND PB.SCOPE_STRING LIKE '%" + enterpriseCode + "%' ");
			sb.append(" select count(*) from ( ");
			sb.append(" select id,title,publishDate, publisher from ( ");
			sb.append(" Select  p.id as id ,p.title as title , p.publish_date as publishDate, pr.name as publisher   from  pe_bulletin p   , pe_manager pe  ,pe_pri_role pr ,sso_user so ");
			sb.append("  where so.fk_role_id = pr.id   and pe.fk_sso_user_id = so.id  and pe.id = p.fk_manager_id and p.scope_string  like '%ff808081493288bd0149335225b90036%' ) p  Order by p.publishDate desc  ) ");
			
		}
		List list = null;
		list = this.getGeneralDao().getBySQL(sb.toString());
		return list;
	}

	@Override
	public Page initFreeCourse(String userId, String courseCode, String courseName, String courseType, String courseItemType, String courseCategory, String courseContent, String time, String teacher, String suggestren, String coursearea, int pageSize, int startIndex) throws EntityException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Page page = null;
		// EnumConst enumConstByFlagIsFree =
		// this.enumConstDao.getByNamespaceCode("FlagIsFree", "1");// 免费课程
		EnumConst enumConstByFlagIsvalid = this.enumConstDao.getByNamespaceCode("FlagIsvalid", "1");// 已发布的
		EnumConst enumConstByFlagCoursearea1 = this.enumConstDao.getByNamespaceCode("FlagCoursearea", "1");// 课程所属区域
		EnumConst enumConstByFlagCoursearea2 = this.enumConstDao.getByNamespaceCode("FlagCoursearea", "2");
		EnumConst enumConstByFlagCoursearea4 = this.enumConstDao.getByNamespaceCode("FlagCoursearea", "5");
		// 用于查找默认专项id
		String sqlBatch = "select pb.id from pe_bzz_batch pb\n" + "inner join enum_const ec on ec.id = pb.flag_batch_type\n" + "where ec.namespace = 'FlagBatchType'\n" + "and ec.code = '1'";
		String batchId = "";
		List list1 = this.getGeneralDao().getBySQL(sqlBatch);
		if (list1 != null) {
			if (list1.size() > 0) {
				batchId = list1.get(0).toString();
			}
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		dc.createCriteria("peBzzTchCourse", "peBzzTchCourse").createAlias("enumConstByFlagCourseType", "enumConstByFlagCourseType", DetachedCriteria.LEFT_JOIN) // 课程性质
				.createAlias("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory", DetachedCriteria.LEFT_JOIN) // 业务分类
				.createAlias("enumConstByFlagContentProperty", "enumConstByFlagContentProperty", DetachedCriteria.LEFT_JOIN) // 内容属性分类
				.createAlias("enumConstByFlagCourseItemType", "enumConstByFlagCourseItemType", DetachedCriteria.LEFT_JOIN) // 大纲分类
				.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.INNER_JOIN);//
		// dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagIsFree",
		// enumConstByFlagIsFree));
		dc.add(Restrictions.isNotNull("id"));
		EnumConst[] courseArea = { enumConstByFlagCoursearea1, enumConstByFlagCoursearea2, enumConstByFlagCoursearea4 };
		// dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCoursearea",
		// enumConstByFlagCoursearea1));
		dc.add(Restrictions.in("peBzzTchCourse.enumConstByFlagCoursearea", courseArea));
		// dc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagIsvalid", enumConstByFlagIsvalid));
		dc.add(Restrictions.or(Restrictions.isNull("peBzzTchCourse.endDate"), Restrictions.ge("peBzzTchCourse.endDate", cal.getTime())));
		if (batchId != null) {
			if (!batchId.equals("")) {
				dc.add(Restrictions.or(Restrictions.isNull("peBzzBatch"), Restrictions.eq("peBzzBatch.id", batchId)));
			}
		}
		StringBuffer sbWhere = new StringBuffer(" 1 = 1 ");
		if (courseCode != null && !"".equalsIgnoreCase(courseCode)) {
			sbWhere.append(" AND UPPER(pebzztchco1_.CODE) LIKE '%" + courseCode.toUpperCase() + "%' ");
		}
		if (courseName != null && !"".equalsIgnoreCase(courseName)) {
			sbWhere.append(" AND UPPER(pebzztchco1_.NAME) LIKE '%" + courseName.toUpperCase() + "%' ");
		}
		if (courseType != null && !"".equalsIgnoreCase(courseType)) {
			sbWhere.append(" AND pebzztchco1_.FLAG_COURSETYPE = '" + courseType + "'");
		}
		if (courseItemType != null && !"".equalsIgnoreCase(courseItemType)) {
			sbWhere.append(" AND pebzztchco1_.FLAG_COURSE_ITEM_TYPE = '" + courseItemType + "'");
		}
		if (courseCategory != null && !"".equalsIgnoreCase(courseCategory)) {
			sbWhere.append(" AND pebzztchco1_.FLAG_COURSECATEGORY = '" + courseCategory + "'");
		}
		if (courseContent != null && !"".equalsIgnoreCase(courseContent)) {
			sbWhere.append(" AND pebzztchco1_.FLAG_CONTENT_PROPERTY = '" + courseContent + "'");
		}
		if(coursearea != null && !"".equalsIgnoreCase(coursearea)) {
			sbWhere.append(" AND pebzztchco1_.FLAG_COURSEAREA = '" + coursearea + "'");
		}
		if (time != null && !"".equalsIgnoreCase(time)) {
			sbWhere.append(" AND pebzztchco1_.TIME = '" + time + "'");
		}
		if (teacher != null && !"".equalsIgnoreCase(teacher)) {
			sbWhere.append(" AND UPPER(pebzztchco1_.TEACHER) LIKE '%" + teacher.toUpperCase() + "%' ");
		}
		if (suggestren != null && !"".equalsIgnoreCase(suggestren)) {
			sbWhere.append(" AND pebzztchco1_.ID IN (SELECT DISTINCT PBTCS.FK_COURSE_ID FROM PE_BZZ_TCH_COURSE_SUGGEST PBTCS, ENUM_CONST EC WHERE PBTCS.FK_ENUM_CONST_ID = EC.ID AND EC.NAMESPACE = 'FlagSuggest' AND EC.NAME = '" + suggestren + "') ");
		}
		dc.add(Restrictions.sqlRestriction(sbWhere.toString() + " and pebzztchco1_.FLAG_OFFLINE = '22' and this_.id not in(select p.fk_tch_opencourse_id from  Pr_Bzz_Tch_Stu_Elective p where p.fk_stu_id='" + userId
				+ "' AND P.FK_TCH_OPENCOURSE_ID IS NOT NULL)  order by substr(pebzztchco1_.code,1,1),pebzztchco1_.code desc"));
		page = this.generalDao.getByPage(dc, pageSize, startIndex);
		return page;
	}

	@Override
	public List tongjiFreeCourse(String stuId, String courseCode, String courseName, String courseType, String courseItemType, String courseCategory, String courseContent, String time, String teacher, String suggestren, String coursearea) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT NVL(SUM(PBTC.TIME), 0) ");
		sb.append("   FROM PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("  INNER JOIN PE_BZZ_TCH_COURSE PBTC ");
		sb.append("     ON PBTO.FK_COURSE_ID = PBTC.ID ");
		sb.append("    AND PBTO.ID IS NOT NULL ");
		sb.append("    AND PBTC.FLAG_COURSEAREA in ('Coursearea1', 'Coursearea2', 'Coursearea3', 'Coursearea5') ");
		sb.append("    AND PBTC.FLAG_ISVALID = '2' ");
		sb.append("    AND (PBTC.END_DATE IS NULL OR PBTC.END_DATE >= SYSDATE) ");
		sb.append("    AND PBTC.FLAG_OFFLINE = '22' ");
		sb.append("  INNER JOIN ENUM_CONST EC1 ");
		sb.append("     ON PBTC.FLAG_COURSETYPE = EC1.ID ");
		sb.append("  INNER JOIN ENUM_CONST E2 ");
		sb.append("     ON PBTC.FLAG_ISVALID = E2.ID ");
		sb.append("  LEFT JOIN ENUM_CONST EC3 ");
		sb.append("     ON PBTC.FLAG_COURSECATEGORY = EC3.ID ");
		sb.append("  LEFT JOIN ENUM_CONST EC4 ");
		sb.append("     ON PBTC.FLAG_CONTENT_PROPERTY = EC4.ID ");
		sb.append("  LEFT JOIN ENUM_CONST EC5 ");
		sb.append("     ON PBTC.FLAG_COURSE_ITEM_TYPE = EC5.ID ");
		// sb.append(" INNER JOIN ENUM_CONST EC6 ");
		// sb.append(" ON PBTC.FLAG_ISFREE = EC6.ID ");
		// sb.append(" AND EC6.CODE = '1' ");
		sb.append("  WHERE 1 = 1 ");
		sb.append("  AND (PBTO.FK_BATCH_ID IS NULL OR PBTO.FK_BATCH_ID = '40288a7b394d676d01394dad824c003b') ");
		sb.append("    AND PBTO.ID NOT IN ");
		sb.append("        (SELECT P.FK_TCH_OPENCOURSE_ID ");
		sb.append("           FROM PR_BZZ_TCH_STU_ELECTIVE P ");
		sb.append("          WHERE P.FK_STU_ID = '" + stuId + "' ");
		sb.append("            AND P.FK_TCH_OPENCOURSE_ID IS NOT NULL) ");
		if (courseCode != null && !"".equalsIgnoreCase(courseCode)) {
			sb.append(" AND UPPER(PBTC.CODE) LIKE '%" + courseCode.toUpperCase() + "%' ");
		}
		if (courseName != null && !"".equalsIgnoreCase(courseName)) {
			sb.append(" AND UPPER(PBTC.NAME) LIKE '%" + courseName.toUpperCase() + "%' ");
		}
		if (courseType != null && !"".equalsIgnoreCase(courseType)) {
			sb.append(" AND PBTC.FLAG_COURSETYPE = '" + courseType + "'");
		}
		if (courseItemType != null && !"".equalsIgnoreCase(courseItemType)) {
			sb.append(" AND PBTC.FLAG_COURSE_ITEM_TYPE = '" + courseItemType + "'");
		}
		if (courseCategory != null && !"".equalsIgnoreCase(courseCategory)) {
			sb.append(" AND PBTC.FLAG_COURSECATEGORY = '" + courseCategory + "'");
		}
		if (courseContent != null && !"".equalsIgnoreCase(courseContent)) {
			sb.append(" AND PBTC.FLAG_CONTENT_PROPERTY = '" + courseContent + "'");
		}
		if (time != null && !"".equalsIgnoreCase(time)) {
			sb.append(" AND PBTC.TIME = '" + time + "'");
		}
		if (teacher != null && !"".equalsIgnoreCase(teacher)) {
			sb.append(" AND UPPER(PBTC.TEACHER) LIKE '%" + teacher.toUpperCase() + "%' ");
		}
		if (suggestren != null && !"".equalsIgnoreCase(suggestren)) {
			sb.append(" AND PBTC.ID IN (SELECT DISTINCT PBTCS.FK_COURSE_ID FROM PE_BZZ_TCH_COURSE_SUGGEST PBTCS, ENUM_CONST EC WHERE PBTCS.FK_ENUM_CONST_ID = EC.ID AND EC.NAMESPACE = 'FlagSuggest' AND EC.NAME = '" + suggestren + "') ");
		}
		if(coursearea != null && !"".equalsIgnoreCase(coursearea)) {
			sb.append(" AND PBTC.FLAG_COURSEAREA = '" + coursearea + "'");
		}
		List list = this.getGeneralDao().getBySQL(sb.toString());
		return list;
	}

	/**
	 * 待考试课程-Lee正在使用
	 * 
	 * @author Lee
	 * @param stuId学员ID
	 * @param courseType课程性质
	 * @param courseCode课程编号
	 * @param courseName课程名称
	 * @param time学时
	 * @param teacher主讲人
	 * @param coursearea所属区域
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws Exception
	 */
	@Override
	//@Cacheable(extension = "[0]+'_'+[1]+'_'+[2]+'_'+[3]+'_'+[4]+'_'+[5]", tTLSeconds = 30)
	public Page initCompletedCourses(String stuId, String courseType, String courseCode, String courseName, String time, String teacher, String coursearea, int pageSize, int startIndex) throws Exception {
		StringBuffer sb = new StringBuffer();
		EnumConst ec = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		EnumConst ec_isExam = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sid = us.getSsoUser().getId();
		sb.append(" SELECT DISTINCT PBTC.ID AS COURSEID, ");
		sb.append("                 PBTC.CODE AS COURSECODE, ");
		sb.append("                 PBTC.NAME AS COURSENAME, ");
		sb.append("                 PBTC.TIME AS COURSETIME, ");
		sb.append("                 EC.NAME AS COURSETYPE, ");
		sb.append("                 TCS.GET_DATE AS STARTDATE, ");
		sb.append("                 SSC.LAST_DATE AS ENDDATE, ");
		sb.append("                 TCS.LEARN_STATUS AS LEATNRESULT, ");
		sb.append("                 PBTO.ID AS OPCOURSEID, ");
		sb.append("                 PBTC.TEACHER AS PBTCTEACHER, ");
		sb.append("                 CASE ");
		sb.append("                   WHEN PBTC.FLAG_IS_EXAM = ");
		sb.append("                        '40288acf3ae01103013ae0130d030002' THEN ");
		sb.append("                    'NOEXAM' ");
		sb.append("                   WHEN NVL(PBTSE.SCORE_EXAM, 0) >= NVL(PBTC.PASSROLE, 0) THEN ");
		sb.append("                    'PASS' ");
		sb.append("                   ELSE ");
		sb.append("                    'FAILED' ");
		sb.append("                 END AS SCORE_EXAM, ");
		sb.append("                 PBTC.STUDYDATES AS STUDYDATES, ");
		sb.append("                 EC2.CODE AS VISITCODE, ");
		sb.append("                 CASE ");
		sb.append("                   WHEN PBTC.FLAG_IS_EXAM = ");
		sb.append("                        '40288acf3ae01103013ae012940b0001' AND ");
		sb.append("                        NVL(PBTSE.SCORE_EXAM, 0) < NVL(PBTC.PASSROLE, 0) AND ");
		sb.append("                        PBTSE.EXAM_TIMES >= PBTC.EXAMTIMES_ALLOW THEN ");
		sb.append("                    'GOBUY' ");
		sb.append("                   ELSE ");
		sb.append("                    'GOON' ");
		sb.append("                 END AS EXAM_STATUS, ");
		sb.append("                 CASE ");
		sb.append("                   WHEN PBTC.STUDYDATES = 0 THEN ");
		sb.append("                    '-' ");
		sb.append("                   ELSE ");
		sb.append("                    TO_CHAR(TCS.GET_DATE + PBTC.STUDYDATES, ");
		sb.append("                            'yyyy-MM-dd hh24:mi:ss') ");
		sb.append("                 END AS GQSJ, ");
		sb.append("                 EC3.NAME AS COURSEAREA, ");
		sb.append("                 EC4.CODE, ");
		sb.append("                 PBTC.FLAG_ISFREE, ");
		sb.append("                 EC3.ID ");
		sb.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
		sb.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("  INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sb.append("     ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		sb.append("    AND FK_STU_ID = '" + stuId + "' ");
		sb.append("    AND (PBTC.FLAG_IS_EXAM = '" + ec_isExam.getId() + "' AND ");
		sb.append("        NVL(PBTSE.SCORE_EXAM, 0) < NVL(PBTC.PASSROLE, 0)) ");
		sb.append("  INNER JOIN TRAINING_COURSE_STUDENT TCS ");
		sb.append("     ON PBTSE.FK_TRAINING_ID = TCS.ID ");
		sb.append("    AND STUDENT_ID = '" + sid + "' ");
		sb.append("    AND LEARN_STATUS = '" + StudyProgress.COMPLETED + "' ");
		sb.append("   LEFT JOIN SCORM_COURSE_INFO SCI ");
		sb.append("     ON PBTC.CODE = SCI.ID ");
		sb.append("   LEFT JOIN SCORM_STU_COURSE SSC ");
		sb.append("     ON SCI.ID = SSC.COURSE_ID ");
		sb.append("    AND SSC.STUDENT_ID = '" + stuId + "' ");
		sb.append("  INNER JOIN PE_BZZ_STUDENT PBS ");
		sb.append("     ON PBTSE.FK_STU_ID = PBS.ID ");
		sb.append("  INNER JOIN SSO_USER SU ");
		sb.append("     ON PBS.FK_SSO_USER_ID = SU.ID ");
		sb.append("   LEFT JOIN ENUM_CONST EC ");
		sb.append("     ON PBTC.FLAG_COURSETYPE = EC.ID ");
		sb.append("   LEFT JOIN ENUM_CONST EC2 ");
		sb.append("     ON PBTC.FLAG_ISVISITAFTERPASS = EC2.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC3 ");
		sb.append("     ON PBTC.FLAG_COURSEAREA = EC3.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC4 ");
		sb.append("     ON PBTC.FLAG_ISFREE = EC4.ID ");
		sb.append("	WHERE 1 = 1 ");
		if (null != courseType && !"".equals(courseType))
			sb.append(" AND PBTC.FLAG_COURSETYPE = '" + courseType + "' ");
		if (null != courseCode && !"".equals(courseCode))
			sb.append(" AND UPPER(PBTC.CODE) LIKE '%" + courseCode.toUpperCase() + "%'");
		if (null != courseName && !"".equals(courseName))
			sb.append(" AND UPPER(PBTC.NAME) LIKE '%" + courseName.toUpperCase() + "%'");
		if (null != time && !"".equals(time))
			sb.append(" AND PBTC.TIME = '" + time + "'");
		if (null != teacher && !"".equals(teacher))
			sb.append(" AND UPPER(PBTC.TEACHER) LIKE '%" + teacher.toUpperCase() + "%'");
		if (null != coursearea && !"".equals(coursearea))
			sb.append(" AND PBTC.FLAG_COURSEAREA = '" + coursearea + "'");
		sb.append(" ORDER BY  PBTC.CODE ");
		Page page = null;
		page = this.generalDao.getByPageSQL(sb.toString(), pageSize, startIndex);
		return page;
	}

	@Override
	public List tongjiCompletedCourse(String stuId, String courseType, String courseCode, String courseName, String time, String teacher, String coursearea) throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sid = us.getSsoUser().getId();
		EnumConst ec_isExam = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT 		  nvl(sum(pbtc.time), 0) as time ");
		sqlBuffer.append("  FROM pe_bzz_tch_course pbtc	 ");
		sqlBuffer.append(" INNER JOIN pr_bzz_tch_opencourse pbto ");
		sqlBuffer.append("    ON pbtc.id = pbto.fk_course_id	 ");
		sqlBuffer.append(" INNER JOIN (select fk_stu_id, fk_training_id, fk_tch_opencourse_id,score_exam,COMPLETED_TIME  ");
		sqlBuffer.append("               from pr_bzz_tch_stu_elective ");
		sqlBuffer.append("              where fk_stu_id = '" + stuId + "' ) pbtse			  ");
		sqlBuffer.append("    ON pbto.id = pbtse.fk_tch_opencourse_id ");
		sqlBuffer.append(" INNER JOIN (select id, learn_status,GET_DATE ");
		sqlBuffer.append("               from training_course_student ");
		sqlBuffer.append("              where student_id = '" + sid + "'			  ");
		sqlBuffer.append("                and learn_status = '" + StudyProgress.COMPLETED + "') tcs			  ");
		sqlBuffer.append("    ON pbtse.fk_training_id = tcs.id ");
		sqlBuffer.append("  left JOIN scorm_course_info sci	 ");
		sqlBuffer.append("    ON pbtc.code = sci.id		 ");
		sqlBuffer.append("  left JOIN (select * from scorm_stu_course where student_id='" + stuId + "') ssc	 ");
		sqlBuffer.append("    ON sci.id = ssc.course_id	 ");
		sqlBuffer.append(" INNER JOIN pe_bzz_student pbs	 ");
		sqlBuffer.append("    ON pbtse.fk_stu_id = pbs.id	 ");
		sqlBuffer.append("  left JOIN sso_user su		 ");
		sqlBuffer.append("    ON pbs.fk_sso_user_id = su.id	 ");
		sqlBuffer.append("  LEFT JOIN enum_const ec		 ");
		sqlBuffer.append("    ON pbtc.flag_coursetype = ec.id	LEFT JOIN enum_const ec2 ON pbtc.FLAG_ISVISITAFTERPASS = ec2.id		where 1=1	");
		sqlBuffer.append("	and ( pbtc.FLAG_IS_EXAM = '" + ec_isExam.getId() + "' and nvl(pbtse.score_exam,0) < nvl(pbtc.passrole,0) ) ");
		if (null != courseType && !"".equals(courseType))
			sqlBuffer.append(" AND PBTC.FLAG_COURSETYPE = '" + courseType + "' ");
		if (null != courseCode && !"".equals(courseCode))
			sqlBuffer.append(" AND UPPER(PBTC.CODE) LIKE '%" + courseCode.toUpperCase() + "%'");
		if (null != courseName && !"".equals(courseName))
			sqlBuffer.append(" AND UPPER(PBTC.NAME) LIKE '%" + courseName.toUpperCase() + "%'");
		if (null != time && !"".equals(time))
			sqlBuffer.append(" AND PBTC.TIME = '" + time + "'");
		if (null != teacher && !"".equals(teacher))
			sqlBuffer.append(" AND UPPER(PBTC.TEACHER) LIKE '%" + teacher.toUpperCase() + "%'");
		if (null != coursearea && !"".equals(coursearea))
			sqlBuffer.append(" AND PBTC.FLAG_COURSEAREA = '" + coursearea + "'");
		List list = this.generalDao.getBySQL(sqlBuffer.toString());
		return list;
	}

	public Page firstBulletinInfoByPage(String loginId, int pageSize, int startIndex) {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		// String cardNo = us.getRoleId();
		String sql = "select id,title,publishDate, publisher from (" + " Select  p.id as id ,p.title as title , p.publish_date as publishDate, pr.name as publisher " + "  from  pe_bulletin p   , pe_manager pe  ,pe_pri_role pr ,sso_user so where so.fk_role_id = pr.id "
				+ "  and pe.fk_sso_user_id = so.id  and pe.id = p.fk_manager_id and p.scope_string  like '%ff808081493288bd0149335225b90036%' ) p" + "  Order by p.publishDate desc ";
		Page page = this.generalDao.getByPageSQL(sql, pageSize, startIndex);
		return page;
	}

	/**
	 * 已通过课程-正在使用
	 * 
	 * @author Lee
	 * @param stuId学员ID
	 * @param courseType课程性质
	 * @param time学时
	 * @param teacher主讲人
	 * @param coursearea所属区域
	 * @param selSendDateStart获得学时时间起
	 * @param selSendDateEnd获得学时时间止
	 * @return
	 */
	public Page initPassedCourses(String stuId, String courseType, String time, String teacher, String coursearea, String selSendDateStart, String selSendDateEnd, String courseCode, String courseName, int pageSize, int startIndex) throws Exception {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		EnumConst ec = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		String sid = us.getSsoUser().getId();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ID, ");
		sb.append("        CODE, ");
		sb.append("        NAME, ");
		sb.append("        TIME, ");
		sb.append("        COURSETYPE, ");
		sb.append("        LEARN_STATUS, ");
		sb.append("        ID2, ");
		sb.append("        TEACHER, ");
		sb.append("        CODE2, ");
		sb.append("        NAME2, ");
		sb.append("        TO_CHAR(COMPLETED_TIME, 'yyyy-MM-dd hh24:mi:ss'), ");
		sb.append("        SCORE_EXAM, ");
		sb.append("        VISITCODE, ");
		sb.append("        CODE3 ");
		sb.append("   FROM (SELECT DISTINCT PBTC.ID, ");
		sb.append("                         PBTC.CODE, ");
		sb.append("                         PBTC.NAME, ");
		sb.append("                         PBTC.TIME, ");
		sb.append("                         EC.NAME AS COURSETYPE, ");
		sb.append("                         TCS.LEARN_STATUS, ");
		sb.append("                         PBTO.ID ID2, ");
		sb.append("                         PBTC.TEACHER, ");
		sb.append("                         EC2.CODE CODE2, ");
		sb.append("                         EC3.NAME NAME2, ");
		sb.append("                         PBTSE.COMPLETED_TIME, ");
		sb.append("                         CASE ");
		sb.append("                           WHEN PBTC.FLAG_IS_EXAM = '" + ec.getId() + "' THEN ");
		sb.append("                            'NOEXAM' ");
		sb.append("                           WHEN NVL(PBTSE.SCORE_EXAM, 0) >= ");
		sb.append("                                NVL(PBTC.PASSROLE, 0) THEN ");
		sb.append("                            'PASS' ");
		sb.append("                           ELSE ");
		sb.append("                            'FAILED' ");
		sb.append("                         END AS SCORE_EXAM, ");
		sb.append("                         EC2.CODE AS VISITCODE, ");
		sb.append("                         EC3.CODE CODE3 ");
		sb.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
		sb.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("             ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("          INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sb.append("             ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		sb.append("            AND FK_STU_ID = '" + stuId + "' ");
		sb.append("          INNER JOIN TRAINING_COURSE_STUDENT TCS ");
		sb.append("             ON PBTSE.FK_TRAINING_ID = TCS.ID ");
		sb.append("            AND STUDENT_ID = '" + sid + "' ");
		sb.append("            AND LEARN_STATUS = '" + StudyProgress.COMPLETED + "' ");
		sb.append("           LEFT JOIN SCORM_COURSE_INFO SCI ");
		sb.append("             ON PBTC.CODE = SCI.ID ");
		sb.append("           LEFT JOIN SCORM_STU_COURSE SSC ");
		sb.append("             ON SCI.ID = SSC.COURSE_ID ");
		sb.append("            AND SSC.STUDENT_ID = '" + stuId + "' ");
		sb.append("          INNER JOIN PE_BZZ_STUDENT PBS ");
		sb.append("             ON PBTSE.FK_STU_ID = PBS.ID ");
		sb.append("           LEFT JOIN SSO_USER SU ");
		sb.append("             ON PBS.FK_SSO_USER_ID = SU.ID ");
		sb.append("           LEFT JOIN ENUM_CONST EC ");
		sb.append("             ON PBTC.FLAG_COURSETYPE = EC.ID ");
		sb.append("           LEFT JOIN ENUM_CONST EC2 ");
		sb.append("             ON PBTC.FLAG_ISVISITAFTERPASS = EC2.ID ");
		sb.append("          INNER JOIN ENUM_CONST EC3 ");
		sb.append("             ON PBTC.FLAG_COURSEAREA = EC3.ID ");
		sb.append("          INNER JOIN ENUM_CONST EC4 ");
		sb.append("             ON PBTC.FLAG_IS_EXAM = EC4.ID ");
		sb.append("          WHERE (PBTSE.ISPASS = 1 OR EC4.CODE = '0') ");
		if (null != courseType && !"".equals(courseType))// 课程性质
			sb.append(" AND PBTC.FLAG_COURSETYPE = '" + courseType + "'");
		if (null != time && !"".equals(time))// 课程学时
			sb.append(" AND PBTC.TIME = '" + time + "'");
		if (null != teacher && !"".equals(teacher))// 主讲人
			sb.append(" AND UPPER(PBTC.TEACHER) LIKE '%" + teacher.toUpperCase() + "%'");
		if (null != coursearea && !"".equals(coursearea))// 所属区域
			sb.append(" AND PBTC.FLAG_COURSEAREA = '" + coursearea + "'");
		if (null != selSendDateStart && !"".equals(selSendDateStart))// 获得学时时间起
			sb.append(" AND PBTSE.COMPLETED_TIME >= TO_DATE('" + selSendDateStart + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
		if (null != selSendDateEnd && !"".equals(selSendDateEnd))// 获得学时时间止
			sb.append(" AND PBTSE.COMPLETED_TIME <= TO_DATE('" + selSendDateEnd + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
		if (null != courseCode && !"".equals(courseCode))// 课程编号
			sb.append(" AND UPPER(PBTC.CODE) LIKE '%" + courseCode.toUpperCase() + "%'");
		if (null != courseName && !"".equals(courseName))// 课程名称
			sb.append(" AND UPPER(PBTC.NAME) LIKE '%" + courseName.toUpperCase() + "%'");
		sb.append(" ORDER BY  PBTSE.COMPLETED_TIME desc ) ");
		Page page = null;
		page = this.getGeneralDao().getByPageSQL(sb.toString(), pageSize, startIndex);
		return page;
	}

	public List statisPassedCourse(String stuId, String courseType, String time, String teacher, String coursearea, String selSendDateStart, String selSendDateEnd, String courseCode, String courseName) {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		EnumConst ec = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		String sid = us.getSsoUser().getId();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT NVL(SUM(PBTC.TIME), 0) || ',' || ");
		sb.append("        NVL(SUM(CASE ");
		sb.append("                  WHEN EC.CODE = '0' THEN ");
		sb.append("                   PBTC.TIME ");
		sb.append("                  ELSE ");
		sb.append("                   NULL ");
		sb.append("                END), ");
		sb.append("            0) || ',' || NVL(SUM(CASE ");
		sb.append("                                   WHEN EC.CODE = '1' THEN ");
		sb.append("                                    PBTC.TIME ");
		sb.append("                                   ELSE ");
		sb.append("                                    NULL ");
		sb.append("                                 END), ");
		sb.append("                             0) ");
		sb.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
		sb.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("  INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sb.append("     ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		sb.append("    AND FK_STU_ID = '" + stuId + "' ");
		sb.append("  INNER JOIN TRAINING_COURSE_STUDENT TCS ");
		sb.append("     ON PBTSE.FK_TRAINING_ID = TCS.ID ");
		sb.append("    AND STUDENT_ID = '" + sid + "' ");
		sb.append("    AND LEARN_STATUS = '" + StudyProgress.COMPLETED + "' ");
		sb.append("  INNER JOIN PE_BZZ_STUDENT PBS ");
		sb.append("     ON PBTSE.FK_STU_ID = PBS.ID ");
		sb.append("   LEFT JOIN ENUM_CONST EC ");
		sb.append("     ON PBTC.FLAG_COURSETYPE = EC.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC3 ");
		sb.append("     ON PBTC.FLAG_COURSEAREA = EC3.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC4 ");
		sb.append("     ON PBTC.FLAG_IS_EXAM = EC4.ID ");
		sb.append("  WHERE (PBTSE.ISPASS = 1 OR EC4.CODE = '0') ");
		if (null != courseType && !"".equals(courseType))// 课程性质
			sb.append(" AND PBTC.FLAG_COURSETYPE = '" + courseType + "'");
		if (null != time && !"".equals(time))// 课程学时
			sb.append(" AND PBTC.TIME = '" + time + "'");
		if (null != teacher && !"".equals(teacher))// 主讲人
			sb.append(" AND UPPER(PBTC.TEACHER) LIKE '%" + teacher.toUpperCase() + "%'");
		if (null != coursearea && !"".equals(coursearea))// 所属区域
			sb.append(" AND PBTC.FLAG_COURSEAREA = '" + coursearea + "'");
		if (null != selSendDateStart && !"".equals(selSendDateStart))// 获得学时时间起
			sb.append(" AND PBTSE.COMPLETED_TIME >= TO_DATE('" + selSendDateStart + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
		if (null != selSendDateEnd && !"".equals(selSendDateEnd))// 获得学时时间止
			sb.append(" AND PBTSE.COMPLETED_TIME <= TO_DATE('" + selSendDateEnd + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
		if (null != courseCode && !"".equals(courseCode))// 课程编号
			sb.append(" AND UPPER(PBTC.CODE) LIKE '%" + courseCode.toUpperCase() + "%'");
		if (null != courseName && !"".equals(courseName))// 课程名称
			sb.append(" AND UPPER(PBTC.NAME) LIKE '%" + courseName.toUpperCase() + "%'");
		List list = this.getGeneralDao().getBySQL(sb.toString());
		return list;
	}
	
	public Page initSiteemail(String loginId, String selTitle, String selSendDateStart, String selSendDateEnd, String selSendName, int pageSize, int startIndex) throws EntityException {
		Page page = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select         id,          ");
		sql.append("               title,		      ");
		sql.append("               true_name as true_name,    ");
		sql.append("               senddate,		      ");
		sql.append("               status		      ");
		sql.append("  from (				      ");
		sql.append(" select s.id, s.title, pm.true_name as true_name,  s.senddate,s.status ");
		sql.append("   from site_email s, pe_manager pm					    ");
		sql.append("  where s.sender_id = pm.login_id	and s.addressee_del=0 and s.addressee_id='" + loginId + "'				    ");
		sql.append(" union								    ");
		sql.append(" select s.id, s.title, pem.name as true_name,  s.senddate,s.status	    ");
		sql.append("   from site_email s, pe_enterprise_manager pem			    ");
		sql.append("  where s.sender_id = pem.login_id	and s.addressee_del=0 and s.addressee_id='" + loginId + "'				    ");
		sql.append(" union								    ");
		sql.append(" select s.id, s.title, ps.true_name,  s.senddate ,s.status		    ");
		sql.append("   from site_email s, pe_bzz_student ps				    ");
		sql.append("  where s.sender_id = ps.reg_no	 and s.addressee_del=0 and s.addressee_id='" + loginId + "'				    ");
		sql.append(")t where 1=1");
		if (selTitle != null && !("".equals(selTitle.trim()))) {
			sql.append(" and t.title like '%" + selTitle + "%'");
		}
		if (selSendDateStart != null && !("".equals(selSendDateStart.trim()))) {
			sql.append(" and to_date(to_char(t.senddate,'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + selSendDateStart + "', 'yyyy-mm-dd')");
		}
		if (selSendDateEnd != null && !("".equals(selSendDateEnd.trim()))) {
			sql.append(" and to_date(to_char(t.senddate,'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + selSendDateEnd + "', 'yyyy-mm-dd')");
		}
		if (selSendName != null && !("".equals(selSendName.trim()))) {
			sql.append("and  t.true_name like '%" + selSendName + "%'");
		}
		sql.append(" order by t.senddate desc");
		try {
			page = this.generalDao.getByPageSQL(sql.toString(), pageSize, startIndex);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}
}
