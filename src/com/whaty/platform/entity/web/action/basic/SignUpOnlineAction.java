package com.whaty.platform.entity.web.action.basic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.CourseVO;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeSignUpOrder;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.PrSignUpOrderCourse;
import com.whaty.platform.entity.bean.PrSignUpOrderStudent;
import com.whaty.platform.entity.bean.StudentVO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class SignUpOnlineAction extends MyBaseAction<PeSignUpOrder> {

	private String id;
	private String limitStu;
	private String startStu;
	private String limitCou;
	private String startCou;
	private String studentIds;
	private String courseIds;
	private String orderNote;
	private String regNo;
	private String stuName;
	private String courseCode;
	private String courseName;
	private String signUpId;
	private List signUpDetailList;
	private String stuids;
	private List<Map> electiveMapList = new ArrayList();
	private BigDecimal allCredit = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);
	private BigDecimal allAmount = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);
	private BigDecimal bTimes = new BigDecimal(0.0).setScale(1, BigDecimal.ROUND_HALF_UP);
	private BigDecimal xTimes = new BigDecimal(0.0).setScale(1, BigDecimal.ROUND_HALF_UP);

	private String validateStr;

	private String typeflag;
	private String dcourseids;

	// Lee start 2014年1月23日 添加参数 用于修改功能使用 避免影响其他调用此类方法的功能受影响
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// Lee end
	/**
	 * 初始化列表
	 * 
	 * @author dgh
	 * @return
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("在线报名单"));
		this.getGridConfig().setCapability(true, true, true);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("报名单序号"), "orderSequence", true, false, true, "TextField", false, 120, true);
		this.getGridConfig().addColumn(this.getText("报名单名称"), "note", true, true, true, "TextField", false, 200, true);
		this.getGridConfig().addColumn(this.getText("添加日期"), "addDate", true, false, true, "TextField", false, 100, true);
		this.getGridConfig().addColumn(this.getText("操作人"), "peEnterpriseManager.name", true, false, true, "TextField", false, 100, true);
		this.getGridConfig().addColumn(this.getText("支付状态"), "enumConstByFlagElectivePayStatus.name", true, false, false, "TextField", false, 100, true);
		this.getGridConfig().addRenderFunction(this.getText("添加学员"), "<a onclick=alert('以免操作缓慢!建议：在线报名单最多允许添加100人,每人10门课程') href=\"signStudentCourse.action?typeflag=addstu&signUpId=${value}\">添加学员</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("添加课程"), "<a href=\"signStudentCourse.action?typeflag=addcou&signUpId=${value}\">添加课程</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看学员"), "<a href=\"signStudentCourse.action?typeflag=viewstu&signUpId=${value}\">查看学员</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看课程"), "<a href=\"signStudentCourse.action?typeflag=viewcour&signUpId=${value}\">查看课程</a>", "id");
		// this.getGridConfig().addRenderFunction(
		// this.getText("去支付"),
		// "<a
		// href=\"signStudentCourse_toConfirm.action?typeflag=topay&signUpId=${value}\">去支付</a>",
		// "id");
		// this.getGridConfig().addRenderFunction(
		// this.getText("去支付"),
		// "<a
		// href=\"signStudentCourse.action?typeflag=viewcour&signUpId=${value}\">查看课程</a>",
		// "id");
		// this.getGridConfig().addRenderFunction(
		// this.getText("查看学员"),
		// "<a href=\"prSignUpOrderStudent.action?signUpId=${value}\">查看学员</a>",
		// "id");
		// this.getGridConfig().addRenderFunction(
		// this.getText("查看课程"),
		// "<a href=\"prSignUpOrderCourse.action?signUpId=${value}\">查看课程</a>",
		// "id");
		this.getGridConfig().addColumn(this.getText("支付状态1"), "enumConstByFlagElectivePayStatus.code", false, false, false, "");
		this.getGridConfig().addRenderScript(this.getText("支付状态"),
				"{if(record.data['enumConstByFlagElectivePayStatus.code'] == '0') return '<a href=\"signUpOnline_toPaySignUpOrder.action?signUpId='+record.data['id']+'\">去支付</a>';else return '已支付'}",
				"id");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeSignUpOrder.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/signUpOnline";
	}

	/**
	 * 删除课程信息
	 * 
	 * @return
	 */
	public String deleteSelectCourseInfo() {
		try {
			List courseList = new ArrayList();
			if (this.dcourseids.indexOf(",") >= 0) {
				String[] stuArry = this.dcourseids.split(",");
				for (int j = 0; j < stuArry.length; j++) {
					if (null != stuArry[j] && !"".equals(stuArry[j])) {
						courseList.add(stuArry[j]);
					}
				}
			} else {
				courseList.add(this.dcourseids);
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeSignUpOrder.class);
			dc.createCriteria("enumConstByFlagElectivePayStatus", "enumConstByFlagElectivePayStatus", dc.INNER_JOIN);
			dc.add(Restrictions.eq("id", this.id));
			List<PeSignUpOrder> periodList = this.getGeneralService().getList(dc);
			String code = periodList.get(0).getEnumConstByFlagElectivePayStatus().getCode();

			if (code.equals("1")) {
				this.setMsg("在线报名单已支付,不能添加或删除课程。");
				this.setTogo("back");
				return "paymentMsg";
			}

			// 1. 删除语句，验证是否交费
			if (null != courseList && courseList.size() > 0) {
				String delStuSql = "";
				for (int i = 0; i < courseList.size(); i++) {
					String validSql = "SELECT PBOI.* FROM PE_BZZ_ORDER_INFO PBOI WHERE FK_SIGNUP_ORDER_ID='" + this.getId() + "'";
					List temStuList = this.getGeneralService().getBySQL(validSql);
					if (null == temStuList || temStuList.size() <= 0) {// 若此人未
						delStuSql = "DELETE PR_SIGNUP_ORDER_COURSE S WHERE S.COURSE_ID='" + courseList.get(i) + "' AND S.FK_SIGNUP_ORDER_ID='" + this.getId() + "'";
						this.getGeneralService().executeBySQL(delStuSql);
					} else {
						this.setMsg("订单信息已生成，不能删除!");
						return "msg";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setMsg("删除成功");
		this.setTogo("back");
//		return "paymentMsg";
		return "paymentMsg";
	}

	/**
	 * 删除学员 dinggh
	 * 
	 * @return
	 */
	public String deleteSelectStudentInfo() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			List stuList = new ArrayList();
			if (this.stuids.indexOf(",") >= 0) {
				String[] stuArry = this.stuids.split(",");
				for (int j = 0; j < stuArry.length; j++) {
					if (null != stuArry[j] && !"".equals(stuArry[j])) {
						stuList.add(stuArry[j]);
					}
				}
			} else {
				stuList.add(this.stuids);
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeSignUpOrder.class);
			dc.createCriteria("enumConstByFlagElectivePayStatus", "enumConstByFlagElectivePayStatus", dc.INNER_JOIN);
			dc.add(Restrictions.eq("id", this.id));
			List<PeSignUpOrder> periodList = this.getGeneralService().getList(dc);
			String code = periodList.get(0).getEnumConstByFlagElectivePayStatus().getCode();

			if (code.equals("1")) {
				this.setMsg("在线报名单已支付,不能添加或删除学员。");
				this.setTogo("back");
				return "paymentMsg";
			}
			// 1. 删除语句，验证是否交费
			if (null != stuList && stuList.size() > 0) {
				String delStuSql = "";
				for (int i = 0; i < stuList.size(); i++) {
					String validSql = "SELECT PBOI.* FROM PE_BZZ_ORDER_INFO PBOI WHERE FK_SIGNUP_ORDER_ID='" + this.getId() + "'";
					List temStuList = this.getGeneralService().getBySQL(validSql);
					if (null == temStuList || temStuList.size() <= 0) {// 不存在支付记录
						delStuSql = "DELETE PR_SIGNUP_ORDER_STUDENT S WHERE S.STUDENT_ID='" + stuList.get(i) + "' AND S.FK_SIGNUP_ORDER_ID='" + this.getId() + "'";
						this.getGeneralService().executeBySQL(delStuSql);
					}
				}
			} else {
				this.setMsg("订单信息已生成，不能删除!");
				return "msg";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setMsg("删除成功");
		this.setTogo("back");
		return "paymentMsg";
//		return "tosinouppage";
	}

	/**
	 * 添加课程
	 */
	public String saveSelectCourseInfo() {
		try {
			List courseList = new ArrayList();
			if (this.dcourseids.indexOf(",") >= 0) {
				String[] stuArry = this.dcourseids.split(",");
				for (int j = 0; j < stuArry.length; j++) {
					if (null != stuArry[j] && !"".equals(stuArry[j])) {
						courseList.add(stuArry[j]);
					}
				}
			} else {
				courseList.add(this.dcourseids);
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeSignUpOrder.class);
			dc.createCriteria("enumConstByFlagElectivePayStatus", "enumConstByFlagElectivePayStatus", dc.INNER_JOIN);
			dc.add(Restrictions.eq("id", this.id));
			List<PeSignUpOrder> periodList = this.getGeneralService().getList(dc);
			String code = periodList.get(0).getEnumConstByFlagElectivePayStatus().getCode();

			if (code.equals("1")) {
				this.setMsg("在线报名单已支付,不能添加或删除课程。");
				this.setTogo("back");
				return "paymentMsg";
			}

			// 1.2报名单中不存存在学员
			String insertCourseSql = "";
			for (int k = 0; k < courseList.size(); k++) {
				// 1.2.1 该次在线报名单 是否已存在此学员
				String validStu = "SELECT PSSC.ID FROM PR_SIGNUP_ORDER_COURSE PSSC WHERE PSSC.FK_SIGNUP_ORDER_ID='" + this.id + "' AND PSSC.COURSE_ID='" + courseList.get(k) + "'";
				List validStuList = this.getGeneralService().getBySQL(validStu);
				if (null == validStuList || validStuList.size() <= 0) {
					insertCourseSql = "INSERT INTO PR_SIGNUP_ORDER_COURSE(ID,COURSE_ID,FK_SIGNUP_ORDER_ID) VALUES(SEQ_PE_SIGNUP_STUDENT_COURSE.Nextval,'" + courseList.get(k) + "','" + this.id + "')";
					this.getGeneralService().executeBySQL(insertCourseSql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setMsg("添加成功");
		this.setTogo("back");
		return "paymentMsg";
//		return "tosinouppage";
	}

	/**
	 * 添加学员 dinggh
	 * 
	 * @return
	 */
	public String saveSelectStudentInfo() {
		try {
			List stuList = new ArrayList();
			if (this.stuids.indexOf(",") >= 0) {
				String[] stuArry = this.stuids.split(",");
				for (int j = 0; j < stuArry.length; j++) {
					if (null != stuArry[j] && !"".equals(stuArry[j])) {
						stuList.add(stuArry[j]);
					}
				}
			} else {
				stuList.add(this.stuids);
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeSignUpOrder.class);
			dc.createCriteria("enumConstByFlagElectivePayStatus", "enumConstByFlagElectivePayStatus", dc.INNER_JOIN);
			dc.add(Restrictions.eq("id", this.id));
			List<PeSignUpOrder> periodList = this.getGeneralService().getList(dc);
			String code = periodList.get(0).getEnumConstByFlagElectivePayStatus().getCode();

			if (code.equals("1")) {
				this.setMsg("在线报名单已支付,不能添加或删除学员。");
				this.setTogo("back");
				return "paymentMsg";
			}
			String insertStuSql = "";
			for (int k = 0; k < stuList.size(); k++) {
				// 1.2.1 该次在线报名单 是否已存在此学员
				String validStu = "SELECT PSSC.ID FROM PR_SIGNUP_ORDER_STUDENT PSSC WHERE PSSC.FK_SIGNUP_ORDER_ID='" + this.id + "' AND PSSC.STUDENT_ID='" + stuList.get(k) + "'";
				List validStuList = this.getGeneralService().getBySQL(validStu);
				if (null == validStuList || validStuList.size() <= 0) {
					insertStuSql = "INSERT INTO PR_SIGNUP_ORDER_STUDENT(ID,STUDENT_ID,FK_SIGNUP_ORDER_ID) VALUES(SEQ_PE_SIGNUP_STUDENT_COURSE.Nextval,'" + stuList.get(k) + "','" + this.id + "')";
					this.getGeneralService().executeBySQL(insertStuSql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setMsg("添加成功");
		this.setTogo("back");
		return "paymentMsg";
//		return "tosinouppage";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeSignUpOrder.class);
		dc.createCriteria("peEnterpriseManager", "peEnterpriseManager").createCriteria("peEnterpriseManager.ssoUser", "peEnterpriseManager.ssoUser");
		dc.createCriteria("enumConstByFlagElectivePayStatus", "enumConstByFlagElectivePayStatus");
		dc.add(Restrictions.eq("peEnterpriseManager.ssoUser", us.getSsoUser()));
		return dc;
	}

	public String toAddSignUp() {

		return "toAddSignUp";
	}

	/**
	 * 获得学生列表
	 * 
	 * @author linjie
	 * @return
	 */
	public String getStudentListJson() {
		try {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			StringBuffer studentFilter = new StringBuffer("");
			String enterpriseId = us.getUserLoginType().equals("3") ? "" : us.getPriEnterprises().get(0).getId();
			if (us.getUserLoginType().equals("2")) {
				studentFilter.append("  and exists ( select id from (																										\n");
				studentFilter.append("         select pe.id, pe.name                                                    \n");
				studentFilter.append("         from pe_enterprise  pe,                                                  \n");
				studentFilter.append("         pe_enterprise pe_parent                                                  \n");
				studentFilter.append("         where pe_parent.id = pe.fk_parent_id and pe_parent.id = '" + enterpriseId + "' \n");
				studentFilter.append("         union select pe.id, pe.name  from pe_enterprise pe where pe.id = '" + enterpriseId + "' )\n");
				studentFilter.append("  a where a.id = pbs.fk_enterprise_id )                                           \n");
			} else if (us.getUserLoginType().equals("4")) {
				studentFilter.append("  and exists ( select pe.id, pe.name  from pe_enterprise pe where pe.id = '" + enterpriseId + "'  and pe.id = pbs.fk_enterprise_id ) ");
			}
			StringBuffer sql = new StringBuffer();
			sql.append("  select pbs.id as id,true_name as name,card_no as idCard,pe.name as enterprise,ec.name as isValid,pbs.reg_no as regno from pe_bzz_student pbs,sso_user su,");
			sql.append("  pe_enterprise pe,enum_const ec where pbs.fk_enterprise_id(+) = pe.id                ");
			sql.append("  and pbs.fk_sso_user_id = su.id(+) and su.flag_isvalid = ec.id(+) and ec.code='1' ");
			if (!StringUtils.defaultString(this.getRegNo()).equals("")) {
				sql.append(" and instr(pbs.reg_no,'" + StringUtils.defaultString(this.getRegNo()) + "') > 0 ");
			}
			if (!StringUtils.defaultString(this.getStuName()).equals("")) {
				sql.append(" and instr(true_name,'" + StringUtils.defaultString(this.getStuName()) + "') > 0 ");
			}
			sql.append(studentFilter.toString());
			Page page = this.getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(StringUtils.defaultString(this.getLimit(), "10")),
					Integer.parseInt(StringUtils.defaultString(this.getStart(), "0")));
			List list = page.getItems();
			List<StudentVO> stuList = new ArrayList<StudentVO>();
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				StudentVO stuVO = new StudentVO();
				stuVO.setId(objs[0].toString());
				stuVO.setName(objs[1].toString());
				if (null == objs[2]) {
					stuVO.setIdCard("");
				} else {
					stuVO.setIdCard(objs[2].toString());
				}

				stuVO.setEnterprise(objs[3].toString());
				stuVO.setIsValid(objs[4].toString());
				stuVO.setRegNO(objs[5].toString());
				stuList.add(stuVO);
			}
			JSONArray jsonArray = JSONArray.fromObject(stuList);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("totalCount", page.getTotalCount() + "");
			jsonObject.put("topics", jsonArray);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/**
	 * 获得课程列表
	 * 
	 * @author linjie
	 * @return
	 */
	public String getCourseListJson() {
		try {

			Page page = null;
			StringBuffer sqlBuffer = new StringBuffer();
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

			sqlBuffer.append("SELECT * ");
			sqlBuffer.append("FROM   (SELECT a.id, ");
			sqlBuffer.append("               a.code, ");
			sqlBuffer.append("               a.name, ");
			sqlBuffer.append("               a.courseTypeName, ");
			sqlBuffer.append("               a.categoryName, ");
			sqlBuffer.append("               a.courseItemType, ");
			sqlBuffer.append("               a.validName, ");
			sqlBuffer.append("               a.canJoinBatch, ");
			sqlBuffer.append("               a.isExam, ");
			sqlBuffer.append("               a.isRecommend, ");
			sqlBuffer.append("               a.isFree, ");
			sqlBuffer.append("               a.teacher, ");
			sqlBuffer.append("               a.time, ");
			sqlBuffer.append("               a.studyDates, ");
			sqlBuffer.append("               a.endDate, ");
			sqlBuffer.append("               a.answerBeginDate, ");
			sqlBuffer.append("               a.answerEndDate, ");
			sqlBuffer.append("               a.passRole, ");
			sqlBuffer.append("               a.examTimesAllow, ");
			sqlBuffer.append("               a.visitName, ");
			sqlBuffer.append("               a.passRoleNote, ");
			sqlBuffer.append("               a.suggestion, ");
			sqlBuffer.append("               a.coursewareId, ");
			sqlBuffer.append("               a.scormType, ");
			sqlBuffer.append("               indexFile,a.sid ");
			sqlBuffer.append("        FROM   (SELECT tc.id               AS id, ");
			sqlBuffer.append("                       tc.name             AS name, ");
			sqlBuffer.append("                       tc.code             AS code, ");
			sqlBuffer.append("                       tc.studyDates       AS studyDates, ");
			sqlBuffer.append("                       tc.price            AS price, ");
			sqlBuffer.append("                       tc.end_date         AS endDate, ");
			sqlBuffer.append("                       tc.answer_BeginDate AS answerBeginDate, ");
			sqlBuffer.append("                       tc.answer_EndDate   AS answerEndDate, ");
			sqlBuffer.append("                       tc.passRole         AS passRole, ");
			sqlBuffer.append("                       tc.examTimes_Allow  AS examTimesAllow, ");
			sqlBuffer.append("                       tc.passRole_Note    AS passRoleNote, ");
			sqlBuffer.append("                       tc.suggestion       AS suggestion, ");
			sqlBuffer.append("                       ec1.name            AS validName, ");
			sqlBuffer.append("                       ec8.name          	 AS courseItemType, ");
			sqlBuffer.append("                       ec9.name            AS isExam, ");
			sqlBuffer.append("                       ec7.name            AS canJoinBatch, "); // 可否添加到专项，后来添加
			sqlBuffer.append("                       tc.suqnum           AS suqNum, ");
			sqlBuffer.append("                       ec2.name            AS categoryName, ");
			sqlBuffer.append("                       ec3.name            AS courseTypeName, ");
			sqlBuffer.append("                       ec4.name            AS visitName, ");
			sqlBuffer.append("                       ec5.name            AS isRecommend, ");
			sqlBuffer.append("                       ec6.name            AS isFree, ");
			sqlBuffer.append("                       tc.time             AS time, ");
			sqlBuffer.append("                       scorm.indexFile     AS indexFile, ");
			sqlBuffer.append("                       scorm.course_id     AS coursewareId, ");
			sqlBuffer.append("                       scormType.code      AS scormType, ");
			sqlBuffer.append("                       tc.teacher          AS teacher, ");
			sqlBuffer.append("                       tc.teacher_note     AS teacherNote, ");
			sqlBuffer.append("                       tc.note             AS note ,tc.SATISFACTION_ID as sid");
			sqlBuffer.append("                FROM   pe_bzz_tch_course tc, (select fk_course_id,count(fk_course_id) from pr_bzz_tch_opencourse group by fk_course_id ) pbto,");
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
			sqlBuffer.append("                       enum_const ec9 ");
			sqlBuffer.append("                WHERE  tc.flag_isvalid = ec1.id(+) ");
			if (!StringUtils.defaultString(this.getCourseCode()).equals("")) {
				sqlBuffer.append(" and instr(tc.code,'" + StringUtils.defaultString(this.getCourseCode()) + "') > 0 ");
			}
			if (!StringUtils.defaultString(this.getCourseName()).equals("")) {
				sqlBuffer.append(" and instr(tc.name,'" + StringUtils.defaultString(this.getCourseName()) + "') > 0 ");
			}
			sqlBuffer.append("                       AND tc.flag_coursecategory = ec2.id(+) ");
			sqlBuffer.append("                       AND tc.flag_coursetype = ec3.id(+) ");
			sqlBuffer.append("                       AND tc.code = scorm.course_id(+) ");
			sqlBuffer.append("                       AND tc.flag_isrecommend = ec5.id(+) ");
			sqlBuffer.append("                       AND tc.flag_isfree = ec6.id(+) ");
			sqlBuffer.append("                       AND tc.FLAG_CANJOINBATCH =ec7.id(+) ");
			sqlBuffer.append("                       AND scorm.scorm_type =scormType.code(+) ");
			sqlBuffer.append("                       AND tc.FLAG_ISVISITAFTERPASS = ec4.id(+)  ");
			sqlBuffer.append("                       AND tc.FLAG_COURSE_ITEM_TYPE = ec8.id(+)  ");
			// Lee 2014年1月23日 如果是报名单添加功能的 则判断课程是否为上线状态
			/* 添加课程有效条件 AND tc.FLAG_OFFLINE = '22' */
			if ("lee".equals(type)) {
				sqlBuffer.append("                       AND tc.FLAG_IS_EXAM = ec9.id(+)    and ec6.code = '0' and ec1.code = '1'  and tc.id = pbto.fk_course_id " + " AND tc.FLAG_OFFLINE = '22' "
						+ " ) a ");
			} else {
				sqlBuffer.append("                       AND tc.FLAG_IS_EXAM = ec9.id(+)    and ec6.code = '0' and ec1.code = '1'  and tc.id = pbto.fk_course_id ) a ");
			}
			sqlBuffer.append("               LEFT JOIN pe_bzz_tch_courseware pbtc ");
			sqlBuffer.append("                 ON a.id = pbtc.fk_course_id) where 1=1");
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(StringUtils.defaultString(this.getLimit(), "10")),
					Integer.parseInt(StringUtils.defaultString(this.getStart(), "0")));

			List list = page.getItems();
			List<CourseVO> couList = new ArrayList<CourseVO>();
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				CourseVO couVO = new CourseVO();
				couVO.setId(objs[0].toString());
				couVO.setCode(objs[1].toString());
				couVO.setName(objs[2].toString());
				couVO.setCourseType(objs[3].toString());
				couVO.setCourseCategory(objs[4].toString());
				couVO.setItemType(objs[5] == null ? "" : objs[5].toString());
				couVO.setIsValid(objs[6].toString());
				couVO.setCanJoinBatch(objs[7] == null ? "" : objs[7].toString());
				couList.add(couVO);
			}
			JSONArray jsonArray = JSONArray.fromObject(couList);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("totalCount", page.getTotalCount() + "");
			jsonObject.put("topics", jsonArray);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/**
	 * 记录出错信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String submitSignUpResult() {
		StringBuffer msg = new StringBuffer();
		this.disposalSignUpData();
		return "confirmSignUp";
	}

	/**
	 * 添加验证，验证是否是返回操作后的添加
	 * 
	 * @author linjie
	 * @return
	 */
	public String toConfirmSignUp() {
		try {
			String signUpOrderResubmitStr = (String) ActionContext.getContext().getSession().get("signUpOrderResubmitStr");
			if (signUpOrderResubmitStr == null || StringUtils.defaultString(signUpOrderResubmitStr).equals("")) {// 说明是重复提交
				this.setTogo("/entity/manager/signUpOnline/signUpOnline.jsp");
				this.setMsg("报名单重复提交！");
				return "msg";
			}
			this.submitOnlineSignUp();
			ActionContext.getContext().getSession().remove("signUpOrderResubmitStr");
			this.setMsg("报名单提交成功!");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("报名单提交失败!");
			this.setTogo("");
		}
		this.setTogo("/entity/manager/signUpOnline/signUpOnline.jsp");
		return "msg";
	}

	/**
	 * 处理提交的选课数据
	 * 
	 * @author linjie
	 * @return
	 */
	public String disposalSignUpData() {
		StringBuffer checkMsg = new StringBuffer("");

		String[] studentIdsArr = this.getStudentIds().split(",");
		String[] courseIdsArr = this.getCourseIds().split(",");
		
		BigDecimal classHourRate = new BigDecimal(this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("ClassHourRate", "0").getName()).setScale(2);
		String flagIsFree = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsFree", "0").getId();
		String flagIsvalid = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "1").getId();

		List<Map> electiveMapList = new ArrayList();
		BigDecimal allAmount = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal allCredit = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);
		int rows = studentIdsArr.length;
		int kilos = (rows % 1000 == 0) ? (rows / 1000) : ((rows / 1000) + 1);
		Set<PeBzzTchCourse> allCoursesSet = new HashSet<PeBzzTchCourse>();
		// 构造用于查询学员数据的idsz字符串
		for (int k = 0; k < kilos; k++) {
			// 查询学员信息
			Map<String, List<String>> stuInfoMap = new HashMap<String, List<String>>();
			// 课程信息的map
			Map<String, List<String>> courseInfoMap = new HashMap<String, List<String>>();

			StringBuffer stuIds = new StringBuffer();// 学员编号ids
			String studentIds = "";
			stuIds.append("(");
			for (int i = k * 1000; i < (k + 1) * 1000 && i < rows; i++) {
				String stuId = studentIdsArr[i].trim();
				stuIds.append("'" + stuId + "',");
			}
			if (stuIds.toString().length() > 1) {
				studentIds = stuIds.toString().substring(0, stuIds.toString().length() - 1);
				studentIds += ")";
			} else {
				studentIds += "('')";
			}

			// 构造用于查询课程信息数据的idsz字符串
			StringBuffer courseIds = new StringBuffer();
			String courseIdsStr = "";
			courseIds.append("(");
			for (String s : courseIdsArr) {
				courseIds.append("'" + s + "',");
				// 添加到
				PeBzzTchCourse pbtc = new PeBzzTchCourse();
				pbtc.setId(s);
				allCoursesSet.add(pbtc);
			}
			if (courseIds.toString().length() > 1) {
				courseIdsStr = courseIds.toString().substring(0, courseIds.toString().length() - 1);
				courseIdsStr += ")";
			} else {
				courseIdsStr += "('')";
			}
			// 验证学员信息的sql
			String checkStuSql = "  select ps.id as id, ps.reg_no as reg_no, ps.true_name as true_name,ps.fk_enterprise_id,card_no\n"
					+ "  from ( select id,reg_no,fk_sso_user_id,true_name,fk_enterprise_id,card_no from pe_bzz_student ps where ps.id in " + studentIds + " ) ps, sso_user su "
					+ "  where ps.fk_sso_user_id = su.id\n" + "  and su.flag_isvalid = '" + flagIsvalid + "'";
			List<Object[]> resultList = this.getGeneralService().getGeneralDao().getBySQL(checkStuSql);
			for (int i = 0; i < resultList.size(); i++) {
				Object[] objs = resultList.get(i);
				List<String> tmpList = new ArrayList<String>();
				tmpList.add(objs[0].toString());
				tmpList.add(objs[1].toString());
				tmpList.add(objs[2].toString());
				tmpList.add(objs[3].toString());
				stuInfoMap.put(objs[0].toString(), tmpList);
			}

			// 验证课程信息的SQL
			String checkCourseSql = " select pto.id   as opencourse_id,\n" + "       ptc.id   as course_id,\n" + "       ptc.name   as course_name,\n" + "       ptc.code as course_code,\n"
					+ "       ptc.time as course_time\n" + "  from pr_bzz_tch_opencourse pto," + "  (select id,name,code,time,flag_isfree from pe_bzz_tch_course where id in " + courseIdsStr
					+ " and flag_isfree = '" + flagIsFree;
			if ("lee".equals(type)) {
				checkCourseSql += "' AND FLAG_OFFLINE = '22' ) ptc\n" + "  where pto.fk_course_id = ptc.id\n";
			} else {
				checkCourseSql += "') ptc\n" + "  where pto.fk_course_id = ptc.id\n";
			}
			// System.out.println("*********************************************");
			// System.out.println(checkCourseSql);
			// System.out.println("*********************************************");
			// System.out.println("*********************************************");
			List<Object[]> courseInfoList = this.getGeneralService().getGeneralDao().getBySQL(checkCourseSql);
			for (int i = 0; i < courseInfoList.size(); i++) {
				Object[] objs = courseInfoList.get(i);
				List<String> tmpList = new ArrayList<String>();
				tmpList.add(objs[0].toString());
				tmpList.add(objs[1].toString());
				tmpList.add(objs[2].toString());
				tmpList.add(objs[3].toString());
				tmpList.add(objs[4].toString());
				courseInfoMap.put(objs[1].toString(), tmpList);
			}

			// 构造验证课程是否已选的SQL
			String checkElectiveBackSql = " select distinct ele.fk_stu_id,pto.fk_course_id from ( select fk_tch_opencourse_id,fk_stu_id from pr_bzz_tch_stu_elective_back where fk_stu_id in "
					+ studentIds + " ) ele, " + " ( select id,fk_course_id from pr_bzz_tch_opencourse where fk_course_id in " + courseIdsStr + ")  pto " + " where ele.fk_tch_opencourse_id = pto.id ";
			String checkElectiveSql = " select distinct ele.fk_stu_id,pto.fk_course_id from ( select fk_tch_opencourse_id,fk_stu_id from pr_bzz_tch_stu_elective where fk_stu_id in " + studentIds
					+ " ) ele, " + " ( select id,fk_course_id from pr_bzz_tch_opencourse where fk_course_id in " + courseIdsStr + ")  pto " + " where ele.fk_tch_opencourse_id = pto.id ";
			List<Object[]> eleList = this.getGeneralService().getGeneralDao().getBySQL(checkElectiveSql);
			List<Object[]> eleBackList = this.getGeneralService().getGeneralDao().getBySQL(checkElectiveBackSql);
			// 构造用于判断的map
			Map<String, String> eleMap = new HashMap<String, String>();
			for (Object[] o : eleList) {
				eleMap.put(o[0].toString(), o[1].toString());
			}
			Map<String, String> eleBackMap = new HashMap<String, String>();
			for (Object[] o : eleBackList) {
				eleBackMap.put(o[0].toString(), o[1].toString());
			}
			for (int i = k * 1000; i < (k + 1) * 1000 && i < rows; i++) {
				Map electiveMap = new HashMap();// 存储信息的map
				BigDecimal amount = new BigDecimal(0.0).setScale(2);
				BigDecimal credit = new BigDecimal(0.0).setScale(2);
				
				
				try {
					List<String> peStudentList = stuInfoMap.get(studentIdsArr[i]);
					PeBzzStudent peBzzStudent = new PeBzzStudent();
					if (peStudentList.size() > 0) {
						peBzzStudent.setId(peStudentList.get(0));
						peBzzStudent.setRegNo(peStudentList.get(1));
						peBzzStudent.setTrueName(peStudentList.get(2));
					}

					StringBuffer courseStr = new StringBuffer();
					for (int j = 0; j < courseIdsArr.length; j++) {// 对课程编号进行循环start
						// 验证课程是否存在
						// 优化性能 dc改sql查询
						List<String> prBzzTchOpencourseList = courseInfoMap.get(courseIdsArr[j]);
						PrBzzTchOpencourse tempOpenCourse = new PrBzzTchOpencourse();
						tempOpenCourse.setId(prBzzTchOpencourseList.get(0));
						PeBzzTchCourse tempCourse = new PeBzzTchCourse();
						tempCourse.setId(prBzzTchOpencourseList.get(1));
						tempCourse.setName(prBzzTchOpencourseList.get(2));
						tempCourse.setCode(prBzzTchOpencourseList.get(3));
						tempCourse.setTime(prBzzTchOpencourseList.get(4));
						tempOpenCourse.setPeBzzTchCourse(tempCourse);

						// 判断课程是否已选
						boolean isBack = false;
						for (Object[] o : eleBackList) {
							if (o[0].equals(peBzzStudent.getId())) {
								if (o[1].equals(tempCourse.getId())) {
									checkMsg.append("" + peBzzStudent.getRegNo() + "[" + peBzzStudent.getTrueName() + "]已选过" + tempCourse.getCode() + "课程.\n");
									isBack = true;
									continue;
								}
							}
						}
						if (!isBack) {
							for (Object[] o : eleList) {
								if (o[0].equals(peBzzStudent.getId())) {
									if (o[1].equals(tempCourse.getId())) {
										checkMsg.append("" + peBzzStudent.getRegNo() + "[" + peBzzStudent.getTrueName() + "]已选过" + tempCourse.getCode() + "课程.\n");
										continue;
									}
								}
							}
						}
						// 旧：判断课程是否已选（提示信息不全）；
						// if (eleMap.get(peBzzStudent.getId())!=null &&
						// eleMap.get(peBzzStudent.getId()).length() >0 &&
						// eleMap.get(peBzzStudent.getId()).equals(tempCourse.getId()))
						// {
						// checkMsg.append(""+peBzzStudent.getRegNo()+"["+peBzzStudent.getTrueName()+"]已选过"+
						// tempCourse.getCode() +"课程.\n");
						// continue;
						// }
						//						
						// if (eleBackMap.get(peBzzStudent.getId())!=null &&
						// eleBackMap.get(peBzzStudent.getId()).length() >0 &&
						// eleBackMap.get(peBzzStudent.getId()).equals(tempCourse.getId()))
						// {
						// checkMsg.append(""+peBzzStudent.getRegNo()+"["+peBzzStudent.getTrueName()+"]已选过"+
						// tempCourse.getCode() +"课程.\n");
						// continue;
						// }

						if (j == courseIdsArr.length - 1) {
							courseStr.append(tempCourse.getCode());
						} else {
							courseStr.append(tempCourse.getCode() + ",");
						}
						credit = credit.add(new BigDecimal(tempCourse.getTime())).setScale(1);
						amount = credit.multiply(classHourRate).setScale(2, BigDecimal.ROUND_HALF_UP);
						

					}// 对课程编号进行循环end
					electiveMap.put("peBzzStudent", peBzzStudent);
					electiveMap.put("courseStr", courseStr);
					electiveMap.put("amount", amount);
					electiveMap.put("credit", credit);
					allAmount = allAmount.add(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
					allCredit = allCredit.add(credit).setScale(1, BigDecimal.ROUND_HALF_UP);
					electiveMapList.add(electiveMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		ActionContext.getContext().getSession().put("electiveMapList", electiveMapList);
		ActionContext.getContext().getSession().put("allAmount", allAmount);
		ActionContext.getContext().getSession().put("allCredit", allCredit);
		ActionContext.getContext().getSession().put("allSignUpCoursesSet", allCoursesSet);
		
		return checkMsg.toString();
	}

	/**
	 * 选课信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String getSignUpDetails() {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select pbs.reg_no,pbs.true_name,pbtc.code,pbtc.name,pbtc.time from pe_signup_order pso,\n");
			sql.append(" pr_signup_order_detail psod,pe_bzz_student pbs,pe_bzz_tch_course pbtc \n");
			sql.append("  where pso.id = psod.order_id and psod.student_id = pbs.id and psod.course_id = pbtc.id and pso.id = '" + this.getSignUpId() + "'\n");
			if (!StringUtils.defaultString(this.getRegNo()).equals("")) {
				sql.append(" and instr(pbs.reg_no,'" + StringUtils.defaultString(this.getRegNo()) + "') > 0 ");
			}
			if (!StringUtils.defaultString(this.getStuName()).equals("")) {
				sql.append(" and instr(pbs.true_name,'" + StringUtils.defaultString(this.getStuName()) + "') > 0 ");
			}
			if (!StringUtils.defaultString(this.getCourseCode()).equals("")) {
				sql.append(" and instr(pbtc.code,'" + StringUtils.defaultString(this.getCourseCode()) + "') > 0 ");
			}
			if (!StringUtils.defaultString(this.getCourseName()).equals("")) {
				sql.append(" and instr(pbtc.name,'" + StringUtils.defaultString(this.getCourseName()) + "') > 0 ");
			}
			sql.append(" order by pbs.reg_no asc,pbtc.code asc");
			this.setSignUpDetailList(this.getGeneralService().getBySQL(sql.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "getSignUpDetails";
	}

	/**
	 * 验证选课信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String validateSignUp() {
		try {
			String msg = this.disposalSignUpData();
			String isOK = msg.equals("") ? "true" : "false";
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("isOK", isOK);
			jsonObj.put("msg", msg);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	public String getLimitStu() {
		return limitStu;
	}

	public void setLimitStu(String limitStu) {
		this.limitStu = limitStu;
	}

	public String getStartStu() {
		return startStu;
	}

	public void setStartStu(String startStu) {
		this.startStu = startStu;
	}

	public String getLimitCou() {
		return limitCou;
	}

	public void setLimitCou(String limitCou) {
		this.limitCou = limitCou;
	}

	public String getStartCou() {
		return startCou;
	}

	public void setStartCou(String startCou) {
		this.startCou = startCou;
	}

	public String getStudentIds() {
		return studentIds;
	}

	public void setStudentIds(String studentIds) {
		this.studentIds = studentIds;
	}

	public String getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(String courseIds) {
		this.courseIds = courseIds;
	}

	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSignUpId() {
		return signUpId;
	}

	public void setSignUpId(String signUpId) {
		this.signUpId = signUpId;
	}

	public List getSignUpDetailList() {
		return signUpDetailList;
	}

	public void setSignUpDetailList(List signUpList) {
		this.signUpDetailList = signUpList;
	}

	/**
	 * 从数据恢复数据 1.查询学员 2.查询课程
	 * 
	 * @author linjie
	 * @return
	 */
	public String getSignUpDataFromDB() {
		StringBuffer msg = new StringBuffer("");
		try {
			BigDecimal classHourRate = new BigDecimal(this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("ClassHourRate", "0").getName()).setScale(2);
			String flagIsFree = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsFree", "0").getId();
			String flagIsvalid = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsvalid", "1").getId();

			DetachedCriteria dcStudent = DetachedCriteria.forClass(PrSignUpOrderStudent.class);
			dcStudent.createCriteria("peSignUpOrder", "peSignUpOrder");
			dcStudent.createCriteria("peBzzStudent", "peBzzStudent");
			dcStudent.add(Restrictions.eq("peSignUpOrder.id", this.getSignUpId()));
			List listStu = this.getGeneralService().getList(dcStudent);
			// 添加验证，如果没有学员信息时，不能去支付
			if (listStu.size() == 0) {
				msg.append("该订单下没有学员.</br>");
			}
			DetachedCriteria dcCourse = DetachedCriteria.forClass(PrSignUpOrderCourse.class);
			dcCourse.createCriteria("peSignUpOrder", "peSignUpOrder");
			dcCourse.createCriteria("peBzzTchCourse", "peBzzTchCourse");
			dcCourse.add(Restrictions.eq("peSignUpOrder.id", this.getSignUpId()));
			List<PrSignUpOrderCourse> listCou = this.getGeneralService().getList(dcCourse);
			// 添加验证，如果没有课程信息时，不能去支付
			if (listCou.size() == 0) {
				msg.append("该订单下没有课程.</br>");
			}
			// 课程ids
			StringBuffer courseStr = new StringBuffer();
			StringBuffer courseStrDB = new StringBuffer("(");
			for (int j = 0; j < listCou.size(); j++) {
				PrSignUpOrderCourse prSignUpOrderCourse = (PrSignUpOrderCourse) listCou.get(j);
				courseStrDB.append("'" + prSignUpOrderCourse.getPeBzzTchCourse().getId() + "',");
				if (j == listCou.size() - 1) {
					courseStr.append(prSignUpOrderCourse.getPeBzzTchCourse().getCode());
				} else {
					courseStr.append(prSignUpOrderCourse.getPeBzzTchCourse().getCode() + ",");
				}
			}
			courseStrDB.append("'')");
			int rows = listStu.size();
			int kilos = (rows % 1000 == 0) ? (rows / 1000) : ((rows / 1000) + 1);

			for (int k = 0; k < kilos; k++) {// LOOP START
				StringBuffer stuIds = new StringBuffer();// 学员编号ids
				String studentIds = "";
				stuIds.append("(");
				for (int i = k * 1000; i < (k + 1) * 1000 && i < rows; i++) {
					PrSignUpOrderStudent prSignUpOrderStudent = (PrSignUpOrderStudent) listStu.get(i);
					PeBzzStudent dbStudent = prSignUpOrderStudent.getPeBzzStudent();
					stuIds.append("'" + dbStudent.getId() + "',");
				}
				if (stuIds.toString().length() > 1) {
					studentIds = stuIds.toString().substring(0, stuIds.toString().length() - 1);
					studentIds += ")";
				} else {
					studentIds += "('')";
				}
				// 构造验证课程是否已选的SQL
				//String checkElectiveSql = " select a.fk_stu_id, p.id from ( "
				//	+ " select distinct ele.fk_stu_id,pto.fk_course_id from ( select fk_tch_opencourse_id,fk_stu_id from pr_bzz_tch_stu_elective_back where fk_stu_id in " + studentIds.toString()
				//	+ " AND FK_TCH_OPENCOURSE_ID IS NOT NULL union all select fk_tch_opencourse_id,fk_stu_id from pr_bzz_tch_stu_elective where fk_stu_id in " + studentIds.toString() + " AND FK_TCH_OPENCOURSE_ID IS NOT NULL ) ele, "
				//	+ " ( select id,fk_course_id from pr_bzz_tch_opencourse where fk_course_id in " + courseStrDB.toString() + " AND ID IS NOT NULL)  pto "
				//	+ " where ele.fk_tch_opencourse_id = pto.id )a,  pe_bzz_tch_course p " + "  where a.fk_course_id=p.id  ";
				String checkElectiveSql = " select a.fk_stu_id, p.id from ( "
					+ " select distinct ele.fk_stu_id,pto.fk_course_id from ( select fk_tch_opencourse_id,fk_stu_id from pr_bzz_tch_stu_elective_back where fk_stu_id in " + studentIds.toString()
					+ " AND FK_TCH_OPENCOURSE_ID IS NOT NULL) ele, "
					+ " ( select id,fk_course_id from pr_bzz_tch_opencourse where fk_course_id in " + courseStrDB.toString() + " AND ID IS NOT NULL)  pto "
					+ " where ele.fk_tch_opencourse_id = pto.id )a,  pe_bzz_tch_course p " + "  where a.fk_course_id=p.id  ";
				
				List<Object[]> eleList = this.getGeneralService().getGeneralDao().getBySQL(checkElectiveSql);
				// 构造用于判断的map
				Map<String, String> eleMap = new HashMap<String, String>();
				for (Object[] o : eleList) {
					eleMap.put(o[0].toString() + "_" + o[1].toString(), o[1].toString());
				}
				// 查询课程信息
				for (int i = k * 1000; i < (k + 1) * 1000 && i < rows; i++) {// 每1000行处理一次start

					Map electiveMap = new HashMap();// 存储信息的map
					BigDecimal amount = new BigDecimal(0.0).setScale(2);
					BigDecimal credit = new BigDecimal(0.0).setScale(2);

					PrSignUpOrderStudent prSignUpOrderStudent = (PrSignUpOrderStudent) listStu.get(i);
					PeBzzStudent dbStudent = prSignUpOrderStudent.getPeBzzStudent();

					try {
						electiveMap.put("peBzzStudent", dbStudent);
						for (int j = 0; j < listCou.size(); j++) {// 对课程编号进行循环start
							PrSignUpOrderCourse prSignUpOrder = (PrSignUpOrderCourse) listCou.get(j);
							PeBzzTchCourse dbCourse = prSignUpOrder.getPeBzzTchCourse();
							// 验证课程是否存在
							// 优化性能 dc改sql查询
							if (eleMap.get(dbStudent.getId() + "_" + dbCourse.getId()) != null && eleMap.get(dbStudent.getId() + "_" + dbCourse.getId()).length() > 0) {
								if ((eleMap.get(dbStudent.getId() + "_" + dbCourse.getId())).equals(dbCourse.getId())) {
									msg.append("学员" + dbStudent.getRegNo() + "对课程" + dbCourse.getCode() + "已存在选课记录！<br/>");
								}
								continue;
							}
							credit = credit.add(new BigDecimal(dbCourse.getTime())).setScale(1);
							amount = amount.add(new BigDecimal(dbCourse.getPrice()));
							if(dbCourse.getEnumConstByFlagCourseType().getCode().equals("0")){
								bTimes = bTimes.add(new BigDecimal(dbCourse.getTime())).setScale(1);
							}else{
								xTimes = xTimes.add(new BigDecimal(dbCourse.getTime())).setScale(1);

							}
						}// 对课程编号进行循环end
						electiveMap.put("courseStr", courseStr);
						electiveMap.put("amount", amount);
						electiveMap.put("credit", credit);
						allAmount = allAmount.add(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
						allCredit = allCredit.add(credit).setScale(1, BigDecimal.ROUND_HALF_UP);
						electiveMapList.add(electiveMap);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}// 每1000行处理一次end

			}// ===LOOP END
			ActionContext.getContext().getSession().put("electiveMapList", electiveMapList);
			ActionContext.getContext().getSession().put("allAmount", allAmount);
			ActionContext.getContext().getSession().put("allCredit", allCredit);
			ActionContext.getContext().getSession().put("bTimes", bTimes);
			ActionContext.getContext().getSession().put("xTimes", xTimes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg.toString();
	}

	/**
	 * 支付选课
	 * 
	 * @author linjie
	 * @return
	 */
	public String toPaySignUpOrder() {
		// 判断订单的状态
		PeSignUpOrder signUpOrder = new PeSignUpOrder();
		try {
			DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			dc1.createCriteria("peSignUpOrder", "peSignUpOrder");
			dc1.add(Restrictions.eq("peSignUpOrder.id", this.getSignUpId()));
			dc1.createCriteria("enumConstByFlagPaymentState", "enumConstByFlagPaymentState", dc1.INNER_JOIN);
			List orderList = this.getGeneralService().getList(dc1);
			if (orderList != null && orderList.size() > 0) {
				PeBzzOrderInfo orderTemp = (PeBzzOrderInfo) orderList.get(0);
				if (orderTemp != null && orderTemp.getEnumConstByFlagPaymentState() != null && "1".equals(orderTemp.getEnumConstByFlagPaymentState().getCode())) {
					this.setMsg("在线报名单已支付，请刷新在线报名单列表，不要重新提交。");
				} else {
					this.setMsg("在线报名单已存在，请至“报名付费历史查询”中继续支付。");
				}
				this.setTogo("/entity/basic/signUpOnline.action");
				return "msg";
			}
			String msg = this.getSignUpDataFromDB();
			signUpOrder = this.getGeneralService().getGeneralDao().getById(PeSignUpOrder.class, this.getSignUpId());
			if (!msg.equals("")) {// 验证不通过
				this.setTogo("/entity/basic/signUpOnline.action");
				this.setMsg(msg);
				return "msg";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ActionContext.getContext().getSession().put("signUpOrder2PayFlag", signUpOrder);
		ActionContext.getContext().getSession().put("peSignupOrderId", signUpOrder.getId());
		return "toPaySignUpOrder";
	}

	public List<Map> getElectiveMapList() {
		return electiveMapList;
	}

	public void setElectiveMapList(List<Map> electiveMapList) {
		this.electiveMapList = electiveMapList;
	}

	public BigDecimal getAllCredit() {
		return allCredit;
	}

	public void setAllCredit(BigDecimal allCredit) {
		this.allCredit = allCredit;
	}

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}

	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		EnumConst enumConstByFlagOrderIsValid = this.getEnumConstService().getByNamespaceCode("FlagOrderIsValid", "1");
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.createCriteria("peSignUpOrder", "peSignUpOrder");
		dc.add(Restrictions.eq("enumConstByFlagOrderIsValid", enumConstByFlagOrderIsValid));
		dc.add(Restrictions.in("peSignUpOrder.id", idList));
		List list = this.getGeneralService().getList(dc);
		if (list.size() > 0) {
			throw new EntityException("所选报名单已产生订单,不能删除.");
		}
	}

	public String toPayOnlineSignUp() {
		try {
			String currSignUpOrderId = (String) ActionContext.getContext().getSession().get("currSignUpOrderId");
			this.setSignUpId(currSignUpOrderId);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("报名单转到去支付页面失败!");
			this.setTogo("/entity/manager/signUpOnline/signUpOnline.jsp");
			return "msg";
		}
		return "toPayOnlineSignUp";
	}

	/**
	 * 重写添加方法
	 */
	public Map add() {
		Map map = new HashMap();
		try {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.createCriteria("ssoUser", "ssoUser");
			dc.add(Restrictions.eq("ssoUser.id", us.getSsoUser().getId()));
			List list = this.getGeneralService().getList(dc);
			PeEnterpriseManager peManager = (PeEnterpriseManager) list.get(0);
			PeSignUpOrder psBean = this.getBean();
			EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagElectivePayStatus", "0");
			psBean.setEnumConstByFlagElectivePayStatus(ec);
			psBean.setAddDate(new Date());
			psBean.setPeEnterpriseManager(peManager);
			psBean.setOrderSequence(this.getGeneralService().getSignUpOrderSeq());
			this.getGeneralService().save(psBean);
			map.put("success", "true");
			map.put("info", "添加成功");

		} catch (Exception e) {
			e.printStackTrace();
			return this.checkAlternateKey(e, "添加");

		}

		return map;
	}

	/**
	 * 提交在线选课单
	 * 
	 * @author linjie
	 * @return
	 */
	public String submitOnlineSignUp() throws EntityException {
		String orderId = "";
		try {
			// 将选课数据添加到数据库
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.createCriteria("ssoUser", "ssoUser");
			dc.add(Restrictions.eq("ssoUser.id", us.getSsoUser().getId()));
			List list = this.getGeneralService().getList(dc);
			PeEnterpriseManager peManager = (PeEnterpriseManager) list.get(0);
			PeSignUpOrder order = new PeSignUpOrder();
			EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagElectivePayStatus", "0");
			order.setEnumConstByFlagElectivePayStatus(ec);
			order.setNote(this.getOrderNote());
			order.setAddDate(new Date());
			order.setPeEnterpriseManager(peManager);
			String orderSeq = this.getGeneralService().getSignUpOrderSeq();
			order.setOrderSequence(orderSeq);
			this.getGeneralService().save(order);
			// 再查出来
			DetachedCriteria dcOrder = DetachedCriteria.forClass(PeSignUpOrder.class);
			dcOrder.add(Restrictions.eq("orderSequence", orderSeq));
			List listOrder = this.getGeneralService().getList(dcOrder);
			PeSignUpOrder signUpOrder = (PeSignUpOrder) listOrder.get(0);
			orderId = signUpOrder.getId();
			// 并把它放到session里边，避免重复提交
			ActionContext.getContext().getSession().put("currSignUpOrderId", orderId);
			List<Map> electiveMapList = (List<Map>) ActionContext.getContext().getSession().get("electiveMapList");
			Set<PeBzzTchCourse> allCoursesSet = (Set<PeBzzTchCourse>) ActionContext.getContext().getSession().get("allSignUpCoursesSet");

			List<PrSignUpOrderCourse> listCourse = new ArrayList<PrSignUpOrderCourse>();
			for (PeBzzTchCourse cou : allCoursesSet) {
				PrSignUpOrderCourse detail = new PrSignUpOrderCourse();
				detail.setPeBzzTchCourse(cou);
				detail.setPeSignUpOrder(signUpOrder);
				listCourse.add(detail);
			}
			this.getGeneralService().saveList(listCourse);
			List<PrSignUpOrderStudent> listStu = new ArrayList<PrSignUpOrderStudent>();
			for (int i = 0; i < electiveMapList.size(); i++) {
				PeBzzStudent peBzzStudent = (PeBzzStudent) (electiveMapList.get(i).get("peBzzStudent"));
				PrSignUpOrderStudent stu = new PrSignUpOrderStudent();
				stu.setPeBzzStudent(peBzzStudent);
				stu.setPeSignUpOrder(signUpOrder);
				listStu.add(stu);
			}
			this.getGeneralService().saveList(listStu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderId;
	}

	public String getValidateStr() {
		return validateStr;
	}

	public void setValidateStr(String validateStr) {
		this.validateStr = validateStr;
	}

	/**
	 * 检查数据是否重复提交
	 * 
	 * @author linjie
	 * @return
	 */
	public String checkSignUpDataFromDB() {
		StringBuffer msg = new StringBuffer("");
		try {

			DetachedCriteria dcStudent = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
			dcStudent.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
			dcStudent.createCriteria("peBzzOrderInfo.peSignUpOrder", "peSignUpOrder");
			dcStudent.add(Restrictions.eq("peSignUpOrder.id", this.getSignUpId()));
			List list = this.getGeneralService().getList(dcStudent);
			if (list.size() > 0) {
				msg.append("该订单已提交.</br>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg.toString();
	}

	public void setBean(PeSignUpOrder instance) {
		super.superSetBean(instance);

	}

	public PeSignUpOrder getBean() {
		return (PeSignUpOrder) super.superGetBean();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStuids() {
		return stuids;
	}

	public void setStuids(String stuids) {
		this.stuids = stuids;
	}

	public String getTypeflag() {
		return typeflag;
	}

	public void setTypeflag(String typeflag) {
		this.typeflag = typeflag;
	}

	public String getDcourseids() {
		return dcourseids;
	}

	public void setDcourseids(String dcourseids) {
		this.dcourseids = dcourseids;
	}

	public BigDecimal getBTimes() {
		return bTimes;
	}

	public void setBTimes(BigDecimal times) {
		bTimes = times;
	}

	public BigDecimal getXTimes() {
		return xTimes;
	}

	public void setXTimes(BigDecimal times) {
		xTimes = times;
	}

}
