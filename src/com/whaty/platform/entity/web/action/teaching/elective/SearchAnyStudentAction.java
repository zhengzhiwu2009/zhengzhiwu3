package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.training.basic.StudyProgress;

@SuppressWarnings("unchecked")
public class SearchAnyStudentAction extends MyBaseAction {

	private static final long serialVersionUID = -1761771855459976663L;
	private PeBzzStudent peBzzStudent;
	private PeBzzTchCourse peBzzTchCourse;
	private PrBzzTchOpencourse prBzzTchOpencourse;
	private List list;
	private String courseId;
	private String opencourseId;
	private String sid;
	private List firstList;
	private List secondList;
	private List lastList;
	private String sumCourse;// 学时总数
	private String status;
	private String stuId;//
	private String flag;// 判断是否是从学员列表页链接过来的。flag=stu表示是
	private String examStatus;
	private String method;
	private String type;
	private String batchId;// 专项编号

	private String countbx;// 必修课程统计
	private String sumtiembx;// 必修学时
	private String countxx;// 选 修课程统计
	private String sumtiemxx;// 选修学时

	private String completedhour;// dgh 已获得学时 查看学员学习情况使用

	/*
	 * 后来添加，用于时间段搜索
	 */
	private String paymentDateStart; // 支付开始时间
	private String paymentDateEnd; // 支付结束时间
	private String studyDateStart; // 开始学时开始时间
	private String studyDateEnd; // 开始学习结束时间

	private String eleDateStart;
	private String eleDateEnd;

	private String seq; // lwq 订单号

	private String courseRegion;// 课程所属区域

	/*
	 * 2014-06-17添加
	 */
	private String courseNo; // 课程编号
	private String courseName;// 课程名称
	private String courseNature;// 课程性质
	private String gainClassHourDateStart;// 开始-获得学时时间
	private String gainClassHourDateEnd;// 结束-获得学时时间

	private List coursesSituation;// 所学课程情况
	private List coursesSituations;// 公司内训所学课程情况
	private List courseLearningProcess;// 课程学习过程
	private String ljcourseLearningTime;// 课程学习过程
	private String ljcourseLearningTimes;// 课程学习过程
	private List satisfactionSurvey;// 满意度调查
	private List classTest;// 课后测验

	private String allid;// 公共课程查看学习详细情况 dgh

	private String tmDateStart;
	private String tmDateEnd;

	public String getTmDateStart() {
		return tmDateStart;
	}

	public void setTmDateStart(String tmDateStart) {
		this.tmDateStart = tmDateStart;
	}

	public String getTmDateEnd() {
		return tmDateEnd;
	}

	public void setTmDateEnd(String tmDateEnd) {
		this.tmDateEnd = tmDateEnd;
	}

	public List getCoursesSituation() {
		return coursesSituation;
	}

	public void setCoursesSituation(List coursesSituation) {
		this.coursesSituation = coursesSituation;
	}

	public List getClassTest() {
		return classTest;
	}

	public void setClassTest(List classTest) {
		this.classTest = classTest;
	}

	public List getSatisfactionSurvey() {
		return satisfactionSurvey;
	}

	public void setSatisfactionSurvey(List satisfactionSurvey) {
		this.satisfactionSurvey = satisfactionSurvey;
	}

	public List getCourseLearningProcess() {
		return courseLearningProcess;
	}

	public void setCourseLearningProcess(List courseLearningProcess) {
		this.courseLearningProcess = courseLearningProcess;
	}

	public String getGainClassHourDateStart() {
		return gainClassHourDateStart;
	}

	public void setGainClassHourDateStart(String gainClassHourDateStart) {
		this.gainClassHourDateStart = gainClassHourDateStart;
	}

	public String getGainClassHourDateEnd() {
		return gainClassHourDateEnd;
	}

	public void setGainClassHourDateEnd(String gainClassHourDateEnd) {
		this.gainClassHourDateEnd = gainClassHourDateEnd;
	}

	public String getCourseNature() {
		return courseNature;
	}

	public void setCourseNature(String courseNature) {
		this.courseNature = courseNature;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public PeBzzTchCourse getPeBzzTchCourse() {
		return peBzzTchCourse;
	}

	public void setPeBzzTchCourse(PeBzzTchCourse peBzzTchCourse) {
		this.peBzzTchCourse = peBzzTchCourse;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public List getFirstList() {
		return firstList;
	}

	public void setFirstList(List firstList) {
		this.firstList = firstList;
	}

	public List getSecondList() {
		return secondList;
	}

	public void setSecondList(List secondList) {
		this.secondList = secondList;
	}

	public List getLastList() {
		return lastList;
	}

	public void setLastList(List lastList) {
		this.lastList = lastList;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getEleDateStart() {
		return eleDateStart;
	}

	public void setEleDateStart(String eleDateStart) {
		this.eleDateStart = eleDateStart;
	}

	public String getEleDateEnd() {
		return eleDateEnd;
	}

	public void setEleDateEnd(String eleDateEnd) {
		this.eleDateEnd = eleDateEnd;
	}

	@Override
	public void initGrid() {

	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/searchAnyStudent";
	}

	/**
	 * 查询学生信息
	 * 
	 * @author linjie
	 * @return
	 */
	public PeBzzStudent searchStudent() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		// dc.add(Restrictions.eq("trueName",
		// peBzzStudent.getTrueName().trim()));
		if(null == peBzzStudent.getCardNo() || !"".equals(peBzzStudent.getCardNo().trim())){
			dc.add(Restrictions.eq("cardNo", peBzzStudent.getCardNo().trim()));
		}else{
			dc.add(Restrictions.eq("regNo", peBzzStudent.getRegNo()));
		}
		try {
			List list = this.getGeneralService().getList(dc);
			if (list.size() != 0) {
				peBzzStudent = (PeBzzStudent) list.get(0);
			} else {
				peBzzStudent.setTrueName("");
				peBzzStudent.setCardNo("");
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return this.peBzzStudent;
	}

	/**
	 * 用id查询学生信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void getStudentById() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.add(Restrictions.eq("id", sid));
		try {
			this.peBzzStudent = (PeBzzStudent) this.getGeneralService().getList(dc).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用id查询课程信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void getCourseById() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.add(Restrictions.eq("id", this.getCourseId()));
		try {
			peBzzTchCourse = (PeBzzTchCourse) this.getGeneralService().getList(dc).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用id查询开课信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void getOpenCourseById() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		dc.add(Restrictions.eq("id", this.getOpencourseId()));
		try {
			prBzzTchOpencourse = (PrBzzTchOpencourse) this.getGeneralService().getList(dc).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询机构信息
	 * 
	 * @author linjie
	 * @return
	 */
	public PeBzzStudent getPe(String id) {
		PeBzzStudent student = new PeBzzStudent();
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("peEnterprise", "peEnterprise", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("id", id));
		try {
			List list = this.getGeneralService().getList(dc);
			if (list.size() > 0) {
				student = (PeBzzStudent) this.getGeneralService().getList(dc).get(0);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return student;
	}

	/**
	 * 课程列表
	 * 
	 * @author linjie
	 * @return
	 */
	public void courseList() {
		String passLine = "80";
		EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagTestPassLine", "0");
		EnumConst ec_isNoExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		EnumConst ec_isExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		if (ec != null) {
			passLine = ec.getName();
		}
		String ssoUserId = peBzzStudent.getSsoUser().getId();
		String studentId = peBzzStudent.getId();
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct c.id,c.code, c.name,c.time,   ");
		sql.append(" ec.name as x,   ");
		sql.append(" TO_CHAR(o.payment_date,'YYYY-MM-DD') AS payment_date,  ");
		sql.append(" tr.get_date,       ");
		sql.append(" CASE WHEN TR.LEARN_STATUS <> 'COMPLETED' AND TR.GET_DATE + STUDYDATES < SYSDATE AND PRICE > 0 AND ELE.DEL = '1' THEN 'GUOQI' WHEN ELE.DEL = '0' THEN 'DEL' ELSE TR.LEARN_STATUS END ,   ");
		sql.append(" case when tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) >= nvl(c.passrole, 0) then 'PASSED' ");
		sql.append(" when tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isNoExam.getId() + "' and ele.completed_time is not null then 'NOEXAMLEE' ");
		sql.append(" when tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and  nvl(ele.score_exam, 0) < nvl(c.passrole, 0) then 'FAILED' ");
		sql.append(" when tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isNoExam.getId() + "' and ele.completed_time is null then 'NOEXAMLEE' ");
		sql.append(" 	 else 'UNFINISHED' end E1,ele.fk_tch_opencourse_id  ,cty.name as courseRegion,o.seq,   op.Flag_Choose as xxlx,ELE.T,ELE.TS,ECS.NAME ECSNAME, TO_CHAR( ele.completed_time, 'yyyy-mm-dd hh24:mi:ss') AS completed_time, to_char(ele.elective_date, 'yyyy-mm-dd hh24:mi:ss')");
		sql.append(" from  ( ( select fk_training_id,score_exam,fk_tch_opencourse_id,elective_date,fk_stu_id,fk_order_id,COMPLETED_TIME ,PER.COMPLETED_TIME AS T,PER.ISPASS AS TS, '1' DEL from pr_bzz_tch_stu_elective   PER       ");
		sql.append(" where FK_STU_ID = '"
				+ studentId
				+ "'       UNION SELECT fk_training_id,SCORE_EXAM, FK_TCH_OPENCOURSE_ID,elective_date, FK_STU_ID, FK_ORDER_ID, COMPLETED_TIME, PER.COMPLETED_TIME AS T, PER.ISPASS AS TS, '0' DEL FROM ELECTIVE_HISTORY PER WHERE FK_STU_ID = '"
				+ studentId + "' ) ele        ");
		sql.append(" inner join ( select id,fk_sso_user_id,card_no,true_name from pe_bzz_student pbs      ");
		sql.append(" where pbs.id = '" + peBzzStudent.getId() + "') stu  on ele.fk_stu_id = stu.id  ");
		sql.append(" inner join pr_bzz_tch_opencourse op on ele.fk_tch_opencourse_id = op.id ");
		sql.append(" inner join pe_bzz_tch_course c on c.id = op.fk_course_id     ");
		sql.append(" INNER JOIN ENUM_CONST ECS ON C.FLAG_IS_EXAM = ECS.ID    ");
		sql.append(" left join pe_bzz_order_info o on o.id = ele.fk_order_id   ");
		sql.append("  inner join ( select id,percent,course_id,student_id,learn_status ,get_date from      ");
		sql.append("  training_course_student where student_id = '" + ssoUserId
				+ "' UNION select id,percent,course_id,student_id,learn_status ,get_date from tcs_history where student_id = '" + ssoUserId
				+ "') tr on tr.id = ele.fk_training_id ");
		sql.append("  left join enum_const ec on ec.id = c.flag_coursetype  left join enum_const cty on cty.id=c.FLAG_COURSEAREA )     where 1=1    ");
		if (null != this.getBatchId() && !"".equals(this.getBatchId())) {
			sql.append(" and O.FK_BATCH_ID='" + this.getBatchId() + "' ");
		}
		// 学习状态
		if ("INCOMPLETE".equals(status)) {
			sql.append(" and tr.learn_status='INCOMPLETE' ");
		} else if ("COMPLETED".equals(status)) {
			sql.append(" and tr.learn_status='COMPLETED'  ");
		} else if ("UNCOMPLETE".equals(status)) {
			sql.append(" and tr.learn_status='UNCOMPLETE' ");
		} else if ("GUOQI".equals(status)) {
			sql.append(" and tr.learn_status<>'COMPLETED' AND TR.GET_DATE + STUDYDATES < SYSDATE AND PRICE > 0 AND ELE.DEL = '1' ");
		} else if ("DEL".equals(status)) {
			sql.append(" and ELE.DEL = '0' ");
		}
		// 考试结果
		if ("PASSED".equals(this.getExamStatus())) {
			sql.append(" and ( (tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) >= nvl(c.passrole, 0)) ");
			sql.append(" ) ");
			// sql.append(" or ( tr.learn_status = 'COMPLETED' and
			// c.flag_is_exam = '" + ec_isNoExam.getId() + "' and
			// ele.completed_time is not null ) ) ");
		} else if ("FAILED".equals(this.getExamStatus())) {
			sql.append(" and ( (tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) < nvl(c.passrole, 0)) or ");
			sql.append(" ( tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isNoExam.getId() + "' and ele.completed_time is null )	) ");
		} else if ("UNFINISHED".equals(this.getExamStatus())) {
			sql.append("  and tr.learn_status <> 'COMPLETED'");
		} else if ("NOEXAMLEE".equals(this.getExamStatus())) {
			sql.append(" and c.flag_is_exam = '" + ec_isNoExam.getId() + "' ");
		}
		// 支付时间-开始时间
		if (paymentDateStart != null && !paymentDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + paymentDateStart + "', 'yyyy-mm-dd')");
		}
		// 支付时间-结束时间
		if (paymentDateEnd != null && !paymentDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + paymentDateEnd + "', 'yyyy-mm-dd')");
		}

		if (eleDateStart != null && !"".equals(eleDateStart)) {
			sql.append(" and to_date(to_char(ele.elective_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + eleDateStart + "', 'yyyy-mm-dd')");
		}
		if (eleDateEnd != null && !"".equals(eleDateEnd)) {
			sql.append(" and to_date(to_char(ele.elective_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + eleDateEnd + "', 'yyyy-mm-dd')");
		}

		// 获得学时-开始时间
		if (tmDateStart != null && !tmDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(ele.completed_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + tmDateStart + "', 'yyyy-mm-dd')");
		}
		// 获得学时-结束时间
		if (tmDateEnd != null && !tmDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(ele.completed_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + tmDateEnd + "', 'yyyy-mm-dd')");
		}
		// 开始学习时间-开始时间
		if (studyDateStart != null && !studyDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + studyDateStart + "', 'yyyy-mm-dd')");
		}
		// 开始学习时间-结束时间
		if (studyDateEnd != null && !studyDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + studyDateEnd + "', 'yyyy-mm-dd')");
		}
		// 课程编号查询
		if (courseNo != null && !courseNo.equalsIgnoreCase("")) {
			sql.append(" and c.code='" + courseNo + "'");
		}
		// 课程名称
		if (courseName != null && !courseName.equalsIgnoreCase("")) {
			sql.append(" and c.name like '%" + courseName + "%'");
		}
		// 课程所属区域
		if (courseRegion != null && !courseRegion.equalsIgnoreCase("")) {
			sql.append(" and CTY.id like '%" + courseRegion + "%'");
		}
		// 课程性质
		if (courseNature != null && !courseNature.equalsIgnoreCase("") && !courseNature.equals("all")) {
			sql.append(" and ec.id='" + courseNature + "'");
		}
		// 开始-获得学时时间
		if (gainClassHourDateStart != null && !gainClassHourDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_char(ele.completed_time,'yyyy-MM-dd')>='" + gainClassHourDateStart + "'");
		}
		// 结束-获得学时时间
		if (gainClassHourDateEnd != null && !gainClassHourDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_char(ele.completed_time,'yyyy-MM-dd')<='" + gainClassHourDateEnd + "'");
		}
		// 订单号
		if (seq != null && !seq.equalsIgnoreCase("")) {
			sql.append(" and o.seq like '%" + seq + "%'");
		}
		sql.append(" order by  TO_CHAR(o.payment_date,'YYYY-MM-DD') desc ");
		try {
			list = this.getGeneralService().getBySQL(sql.toString());
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询已获得学时 dgh 20140715
	 */
	public void sumCompleteTime() {
		String passLine = "80";
		EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagTestPassLine", "0");
		EnumConst ec_isNoExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		EnumConst ec_isExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		if (ec != null) {
			passLine = ec.getName();
		}
		String ssoUserId = peBzzStudent.getSsoUser().getId();
		String studentId = peBzzStudent.getId();
		StringBuffer sql = new StringBuffer();
		sql.append(" select NVL(sum(c.time),0) ");
		sql.append(" from  ( ( select score_exam,fk_tch_opencourse_id,fk_stu_id,fk_order_id,COMPLETED_TIME from pr_bzz_tch_stu_elective      ");
		sql.append(" where FK_STU_ID = '" + studentId + "' ) ele        ");
		sql.append(" inner join ( select id,fk_sso_user_id,card_no,true_name from pe_bzz_student pbs      ");
		sql.append(" where pbs.card_no = '" + peBzzStudent.getCardNo().trim() + "' and pbs.true_name = '" + peBzzStudent.getTrueName().trim()
				+ "' ) stu  on ele.fk_stu_id = stu.id  ");
		sql.append(" inner join pr_bzz_tch_opencourse op on ele.fk_tch_opencourse_id = op.id ");
		sql.append(" inner join pe_bzz_tch_course c on c.id = op.fk_course_id     ");
		sql.append(" left join pe_bzz_order_info o on o.id = ele.fk_order_id   ");
		sql.append("  inner join ( select distinct percent,course_id,student_id,learn_status ,get_date from      ");
		sql.append("  training_course_student where student_id = '" + ssoUserId + "' ) tr on tr.course_id = op.id            ");
		sql.append("  left join enum_const ec on ec.id = c.flag_coursetype left join enum_const cty on cty.id=c.FLAG_COURSEAREA  )     where 1=1    ");
		if (null != this.getBatchId() && !"".equals(this.getBatchId())) {
			sql.append(" and O.FK_BATCH_ID='" + this.getBatchId() + "' ");
		}
		sql.append(" AND( ( ");
		sql.append(" TR.LEARN_STATUS = 'COMPLETED' AND C.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001' AND NVL(ELE.SCORE_EXAM, 0) >= NVL(C.PASSROLE, 0) ");
		sql.append(" )OR( ");
		sql.append(" TR.LEARN_STATUS = 'COMPLETED' AND C.FLAG_IS_EXAM = '40288acf3ae01103013ae0130d030002' AND ELE.COMPLETED_TIME IS NOT NULL ");
		sql.append(" )) ");
		// 学习状态
		if ("INCOMPLETE".equals(status)) {
			sql.append(" and tr.learn_status='INCOMPLETE' ");
		} else if ("COMPLETED".equals(status)) {
			sql.append(" and tr.learn_status='COMPLETED'  ");
		} else if ("UNCOMPLETE".equals(status)) {
			sql.append(" and tr.learn_status='UNCOMPLETE' ");
		} else if ("GUOQI".equals(status)) {
			sql.append(" and tr.learn_status<>'COMPLETED' AND TR.GET_DATE + STUDYDATES < SYSDATE AND PRICE > 0 ");
		}
		/*
		 * 111 else if ("DEL".equals(status)) { sql.append(" and ELE.DEL = '0'
		 * "); }
		 */
		// 考试结果
		if ("PASSED".equals(this.getExamStatus())) {
			sql.append(" and ( (tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) >= nvl(c.passrole, 0)) ");
			sql.append(" ) ");
			// sql.append(" or ( tr.learn_status = 'COMPLETED' and
			// c.flag_is_exam = '" + ec_isNoExam.getId() + "' and
			// ele.completed_time is not null ) ) ");
		} else if ("FAILED".equals(this.getExamStatus())) {
			sql.append(" and ( (tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) < nvl(c.passrole, 0)) or ");
			sql.append(" ( tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isNoExam.getId() + "' and ele.completed_time is null )	) ");
		} else if ("UNFINISHED".equals(this.getExamStatus())) {
			sql.append("  and tr.learn_status <> 'COMPLETED'");
		} else if ("NOEXAMLEE".equals(this.getExamStatus())) {
			sql.append(" and c.flag_is_exam = '" + ec_isNoExam.getId() + "' ");
		}
		// 支付时间-开始时间
		if (paymentDateStart != null && !paymentDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + paymentDateStart + "', 'yyyy-mm-dd')");
		}
		// 支付时间-结束时间
		if (paymentDateEnd != null && !paymentDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + paymentDateEnd + "', 'yyyy-mm-dd')");
		}
		// 获得学时-开始时间
		if (tmDateStart != null && !tmDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(ele.completed_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + tmDateStart + "', 'yyyy-mm-dd')");
		}
		// 获得学时-结束时间
		if (tmDateEnd != null && !tmDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(ele.completed_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + tmDateEnd + "', 'yyyy-mm-dd')");
		}
		// 开始学习时间-开始时间
		if (studyDateStart != null && !studyDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + studyDateStart + "', 'yyyy-mm-dd')");
		}
		// 开始学习时间-结束时间
		if (studyDateEnd != null && !studyDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + studyDateEnd + "', 'yyyy-mm-dd')");
		}
		// 课程编号查询
		if (courseNo != null && !courseNo.equalsIgnoreCase("")) {
			sql.append(" and c.code='" + courseNo + "'");
		}
		// 课程名称
		if (courseName != null && !courseName.equalsIgnoreCase("")) {
			sql.append(" and c.name like '%" + courseName + "%'");
		}
		// 课程所属区域
		if (courseRegion != null && !courseRegion.equalsIgnoreCase("")) {
			sql.append(" and CTY.id like '%" + courseRegion + "%'");
		}
		// 课程性质
		if (courseNature != null && !courseNature.equalsIgnoreCase("") && !courseNature.equals("all")) {
			sql.append(" and ec.id='" + courseNature + "'");
		}
		// 开始-获得学时时间
		if (gainClassHourDateStart != null && !gainClassHourDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_char(ele.completed_time,'yyyy-MM-dd')>='" + gainClassHourDateStart + "'");
		}
		// 结束-获得学时时间
		if (gainClassHourDateEnd != null && !gainClassHourDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_char(ele.completed_time,'yyyy-MM-dd')<='" + gainClassHourDateEnd + "'");
		}
		// 订单号
		if (seq != null && !seq.equalsIgnoreCase("")) {
			sql.append(" and o.seq like '%" + seq + "%'");
		}
		sql.append(" order by  o.payment_date desc ");
		try {
			if (this.getGeneralService().getBySQL(sql.toString()).size() > 0) {
				List list = (List) this.getGeneralService().getBySQL(sql.toString());
				if (list.get(0) != null) {
					this.completedhour = list.get(0).toString();
				} else {
					this.completedhour = "0";
				}

			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询必修 学时 lzh
	 */
	public void countbxaction() {
		String passLine = "80";
		EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagTestPassLine", "0");
		EnumConst ec_isNoExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		EnumConst ec_isExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		if (ec != null) {
			passLine = ec.getName();
		}
		String ssoUserId = peBzzStudent.getSsoUser().getId();
		String studentId = peBzzStudent.getId();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(c.flag_coursetype),sum(c.time)");
		sql.append(" from  ( ( select score_exam,fk_tch_opencourse_id,fk_stu_id,fk_order_id,COMPLETED_TIME from pr_bzz_tch_stu_elective      ");
		sql.append(" where FK_STU_ID = '" + studentId + "' ) ele        ");
		sql.append(" inner join ( select id,fk_sso_user_id,card_no,true_name from pe_bzz_student pbs      ");
		sql.append(" where pbs.card_no = '" + peBzzStudent.getCardNo().trim() + "' and pbs.true_name = '" + peBzzStudent.getTrueName().trim()
				+ "' ) stu  on ele.fk_stu_id = stu.id  ");
		sql.append(" inner join pr_bzz_tch_opencourse op on ele.fk_tch_opencourse_id = op.id ");
		sql.append("  inner join  pe_bzz_tch_course c on c.id = op.fk_course_id  and  c.flag_coursetype = '402880f32200c249012200c780c40001'     ");
		sql.append(" left join pe_bzz_order_info o on o.id = ele.fk_order_id   ");
		sql.append("  inner join ( select distinct percent,course_id,student_id,learn_status ,get_date from      ");
		sql.append("  training_course_student where student_id = '" + ssoUserId + "' ) tr on tr.course_id = op.id            ");
		sql.append("  left join enum_const ec on ec.id = c.flag_coursetype left join enum_const cty on cty.id=c.FLAG_COURSEAREA  )     where 1=1    ");
		if (null != this.getBatchId() && !"".equals(this.getBatchId())) {
			sql.append(" and O.FK_BATCH_ID='" + this.getBatchId() + "' ");
		}
		// 学习状态
		if ("INCOMPLETE".equals(status)) {
			sql.append(" and tr.learn_status='INCOMPLETE' ");
		} else if ("COMPLETED".equals(status)) {
			sql.append(" and tr.learn_status='COMPLETED'  ");
		} else if ("UNCOMPLETE".equals(status)) {
			sql.append(" and tr.learn_status='UNCOMPLETE' ");
		} else if ("GUOQI".equals(status)) {
			sql.append(" and tr.learn_status<>'COMPLETED' AND TR.GET_DATE + STUDYDATES < SYSDATE AND PRICE > 0 ");
		}
		/*
		 * 11 else if ("DEL".equals(status)) { sql.append(" and ELE.DEL = '0'
		 * "); }
		 */
		// 考试结果
		if ("PASSED".equals(this.getExamStatus())) {
			sql.append(" and ( (tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) >= nvl(c.passrole, 0)) ");
			sql.append(" ) ");
			// sql.append(" or ( tr.learn_status = 'COMPLETED' and
			// c.flag_is_exam = '" + ec_isNoExam.getId() + "' and
			// ele.completed_time is not null ) ) ");
		} else if ("FAILED".equals(this.getExamStatus())) {
			sql.append(" and ( (tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) < nvl(c.passrole, 0)) or ");
			sql.append(" ( tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isNoExam.getId() + "' and ele.completed_time is null )	) ");
		} else if ("UNFINISHED".equals(this.getExamStatus())) {
			sql.append("  and tr.learn_status <> 'COMPLETED'");
		} else if ("NOEXAMLEE".equals(this.getExamStatus())) {
			sql.append(" and c.flag_is_exam = '" + ec_isNoExam.getId() + "' ");
		}
		// 支付时间-开始时间
		if (paymentDateStart != null && !paymentDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + paymentDateStart + "', 'yyyy-mm-dd')");
		}
		// 支付时间-结束时间
		if (paymentDateEnd != null && !paymentDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + paymentDateEnd + "', 'yyyy-mm-dd')");
		}
		// 获得学时-开始时间
		if (tmDateStart != null && !tmDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(ele.completed_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + tmDateStart + "', 'yyyy-mm-dd')");
		}
		// 获得学时-结束时间
		if (tmDateEnd != null && !tmDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(ele.completed_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + tmDateEnd + "', 'yyyy-mm-dd')");
		}
		// 开始学习时间-开始时间
		if (studyDateStart != null && !studyDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + studyDateStart + "', 'yyyy-mm-dd')");
		}
		// 开始学习时间-结束时间
		if (studyDateEnd != null && !studyDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + studyDateEnd + "', 'yyyy-mm-dd')");
		}
		// 课程编号查询
		if (courseNo != null && !courseNo.equalsIgnoreCase("")) {
			sql.append(" and c.code='" + courseNo + "'");
		}
		// 课程名称
		if (courseName != null && !courseName.equalsIgnoreCase("")) {
			sql.append(" and c.name like '%" + courseName + "%'");
		}
		// 课程所属区域
		if (courseRegion != null && !courseRegion.equalsIgnoreCase("")) {
			sql.append(" and CTY.id like '%" + courseRegion + "%'");
		}
		// 课程性质
		if (courseNature != null && !courseNature.equalsIgnoreCase("") && !courseNature.equals("all")) {
			sql.append(" and ec.id='" + courseNature + "'");
		}
		// 开始-获得学时时间
		if (gainClassHourDateStart != null && !gainClassHourDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_char(ele.completed_time,'yyyy-MM-dd')>='" + gainClassHourDateStart + "'");
		}
		// 结束-获得学时时间
		if (gainClassHourDateEnd != null && !gainClassHourDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_char(ele.completed_time,'yyyy-MM-dd')<='" + gainClassHourDateEnd + "'");
		}
		// 订单号
		if (seq != null && !seq.equalsIgnoreCase("")) {
			sql.append(" and o.seq like '%" + seq + "%'");
		}
		sql.append(" order by  o.payment_date desc ");
		try {
			if (this.getGeneralService().getBySQL(sql.toString()).size() > 0) {
				List<Object[]> list = (List) this.getGeneralService().getBySQL(sql.toString());
				if (list.get(0) != null) {
					this.countbx = list.get(0)[0].toString();
					if (list.get(0)[1] == null) {
						this.sumtiembx = "0";
					} else {
						this.sumtiembx = list.get(0)[1].toString();
					}

				} else {
					this.countbx = "0";
					this.sumtiembx = "0";
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询选修 学时 lzh
	 */
	public void countxxaction() {
		String passLine = "80";
		EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagTestPassLine", "0");
		EnumConst ec_isNoExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		EnumConst ec_isExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		if (ec != null) {
			passLine = ec.getName();
		}
		String ssoUserId = peBzzStudent.getSsoUser().getId();
		String studentId = peBzzStudent.getId();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(c.flag_coursetype),sum(c.time)");
		sql.append(" from  ( ( select score_exam,fk_tch_opencourse_id,fk_stu_id,fk_order_id,COMPLETED_TIME from pr_bzz_tch_stu_elective      ");
		sql.append(" where FK_STU_ID = '" + studentId + "' ) ele        ");
		sql.append(" inner join ( select id,fk_sso_user_id,card_no,true_name from pe_bzz_student pbs      ");
		sql.append(" where pbs.card_no = '" + peBzzStudent.getCardNo().trim() + "' and pbs.true_name = '" + peBzzStudent.getTrueName().trim()
				+ "' ) stu  on ele.fk_stu_id = stu.id  ");
		sql.append(" inner join pr_bzz_tch_opencourse op on ele.fk_tch_opencourse_id = op.id ");
		sql.append("  inner join  pe_bzz_tch_course c on c.id = op.fk_course_id  and  c.flag_coursetype = '402880f32200c249012200c7f8b30002'     ");
		sql.append(" left join pe_bzz_order_info o on o.id = ele.fk_order_id   ");
		sql.append("  inner join ( select distinct percent,course_id,student_id,learn_status ,get_date from      ");
		sql.append("  training_course_student where student_id = '" + ssoUserId + "' ) tr on tr.course_id = op.id            ");
		sql.append("  left join enum_const ec on ec.id = c.flag_coursetype left join enum_const cty on cty.id=c.FLAG_COURSEAREA  )     where 1=1    ");
		if (null != this.getBatchId() && !"".equals(this.getBatchId())) {
			sql.append(" and O.FK_BATCH_ID='" + this.getBatchId() + "' ");
		}
		// 学习状态
		if ("INCOMPLETE".equals(status)) {
			sql.append(" and tr.learn_status='INCOMPLETE' ");
		} else if ("COMPLETED".equals(status)) {
			sql.append(" and tr.learn_status='COMPLETED'  ");
		} else if ("UNCOMPLETE".equals(status)) {
			sql.append(" and tr.learn_status='UNCOMPLETE' ");
		} else if ("GUOQI".equals(status)) {
			sql.append(" and tr.learn_status<>'COMPLETED' AND TR.GET_DATE + STUDYDATES < SYSDATE AND PRICE > 0 ");
		}
		/*
		 * 11 else if ("DEL".equals(status)) { sql.append(" and ELE.DEL = '0'
		 * "); }
		 */
		// 考试结果
		if ("PASSED".equals(this.getExamStatus())) {
			sql.append(" and ( (tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) >= nvl(c.passrole, 0)) ");
			sql.append(" ) ");
			// sql.append(" or ( tr.learn_status = 'COMPLETED' and
			// c.flag_is_exam = '" + ec_isNoExam.getId() + "' and
			// ele.completed_time is not null ) ) ");
		} else if ("FAILED".equals(this.getExamStatus())) {
			sql.append(" and ( (tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) < nvl(c.passrole, 0)) or ");
			sql.append(" ( tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isNoExam.getId() + "' and ele.completed_time is null )	) ");
		} else if ("UNFINISHED".equals(this.getExamStatus())) {
			sql.append("  and tr.learn_status <> 'COMPLETED'");
		} else if ("NOEXAMLEE".equals(this.getExamStatus())) {
			sql.append(" and c.flag_is_exam = '" + ec_isNoExam.getId() + "' ");
		}
		// 支付时间-开始时间
		if (paymentDateStart != null && !paymentDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + paymentDateStart + "', 'yyyy-mm-dd')");
		}
		// 支付时间-结束时间
		if (paymentDateEnd != null && !paymentDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + paymentDateEnd + "', 'yyyy-mm-dd')");
		}
		// 获得学时-开始时间
		if (tmDateStart != null && !tmDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(ele.completed_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + tmDateStart + "', 'yyyy-mm-dd')");
		}
		// 获得学时-结束时间
		if (tmDateEnd != null && !tmDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(ele.completed_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + tmDateEnd + "', 'yyyy-mm-dd')");
		}
		// 开始学习时间-开始时间
		if (studyDateStart != null && !studyDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + studyDateStart + "', 'yyyy-mm-dd')");
		}
		// 开始学习时间-结束时间
		if (studyDateEnd != null && !studyDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + studyDateEnd + "', 'yyyy-mm-dd')");
		}
		// 课程编号查询
		if (courseNo != null && !courseNo.equalsIgnoreCase("")) {
			sql.append(" and c.code='" + courseNo + "'");
		}
		// 课程名称
		if (courseName != null && !courseName.equalsIgnoreCase("")) {
			sql.append(" and c.name like '%" + courseName + "%'");
		}
		// 课程所属区域
		if (courseRegion != null && !courseRegion.equalsIgnoreCase("")) {
			sql.append(" and CTY.id like '%" + courseRegion + "%'");
		}
		// 课程性质
		if (courseNature != null && !courseNature.equalsIgnoreCase("") && !courseNature.equals("all")) {
			sql.append(" and ec.id='" + courseNature + "'");
		}
		// 开始-获得学时时间
		if (gainClassHourDateStart != null && !gainClassHourDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_char(ele.completed_time,'yyyy-MM-dd')>='" + gainClassHourDateStart + "'");
		}
		// 结束-获得学时时间
		if (gainClassHourDateEnd != null && !gainClassHourDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_char(ele.completed_time,'yyyy-MM-dd')<='" + gainClassHourDateEnd + "'");
		}
		// 订单号
		if (seq != null && !seq.equalsIgnoreCase("")) {
			sql.append(" and o.seq like '%" + seq + "%'");
		}
		sql.append(" order by  o.payment_date desc ");
		try {
			if (this.getGeneralService().getBySQL(sql.toString()).size() > 0) {
				List<Object[]> list = (List) this.getGeneralService().getBySQL(sql.toString());
				if (list.get(0) != null) {
					this.countxx = list.get(0)[0].toString();
					if (list.get(0)[1] == null) {
						this.sumtiemxx = "0";
					} else {

						this.sumtiemxx = list.get(0)[1].toString();
					}
				} else {
					this.countxx = "0";
					this.sumtiemxx = "0";
				}

			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查看专项学时总数
	 * 
	 * @author wangjt
	 */
	public void sumBatchCourse() {
		String ssoUserId = peBzzStudent.getSsoUser().getId();
		String studentId = peBzzStudent.getId();
		StringBuffer sql = new StringBuffer();
		String passLine = "80";
		EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagTestPassLine", "0");
		if (ec != null) {
			passLine = ec.getName();
		}
		String id = ActionContext.getContext().getSession().get("id").toString().trim();
		sql.append("  select sum(c.time)																																													");
		sql.append("  from ( ( select score_exam,fk_tch_opencourse_id,fk_stu_id,fk_order_id from pr_bzz_tch_stu_elective      ");
		sql.append("  where FK_STU_ID = '" + studentId + "' ) ele                                                               ");
		sql.append(" 	inner join ( select id,fk_sso_user_id,card_no,true_name from pe_bzz_student pbs   ");
		sql.append(" 	where pbs.card_no = '" + peBzzStudent.getCardNo().trim() + "' and pbs.true_name = '" + peBzzStudent.getTrueName().trim()
				+ "' ) stu  on ele.fk_stu_id = stu.id  ");
		sql.append(" 	inner join pr_bzz_tch_opencourse op on ele.fk_tch_opencourse_id = op.id    ");
		sql.append(" 	inner join pe_bzz_tch_course c on c.id = op.fk_course_id  ");

		sql.append(" 	inner join PE_BZZ_BATCH p on  p.id=op.fk_batch_id   ");

		sql.append("  left join pe_bzz_order_info o on o.id = ele.fk_order_id    ");
		sql.append("  inner join ( select distinct percent,course_id,student_id,learn_status,get_date from      ");
		sql.append("  training_course_student where student_id = '" + ssoUserId + "' ) tr on tr.course_id = op.id                  ");
		sql.append("  left join enum_const ec on ec.id = c.flag_coursetype left join enum_const cty on cty.id=c.FLAG_COURSEAREA  ) where  1=1    ");
		sql.append("  and p.id='" + id + "'   ");

		sql.append(" and tr.learn_status = 'COMPLETED' and nvl(ele.score_exam,0) >= " + passLine + "  ");

		/*
		 * 支付时间-开始时间
		 */
		if (paymentDateStart != null && !paymentDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + paymentDateStart + "', 'yyyy-mm-dd')");
			// paymentDateStart = null;
		}
		/*
		 * 支付时间-结束时间
		 */
		if (paymentDateEnd != null && !paymentDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + paymentDateEnd + "', 'yyyy-mm-dd')");
			// paymentDateEnd = null;
		}
		/*
		 * 开始学习时间-开始时间
		 */
		if (studyDateStart != null && !studyDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + studyDateStart + "', 'yyyy-mm-dd')");
			// studyDateStart = null;
		}
		/*
		 * 开始学习时间-结束时间
		 */
		if (studyDateEnd != null && !studyDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + studyDateEnd + "', 'yyyy-mm-dd')");
			// studyDateEnd = null;
		}

		try {
			if (this.getGeneralService().getBySQL(sql.toString()).size() > 0) {
				List list = (List) this.getGeneralService().getBySQL(sql.toString());
				if (list.get(0) != null) {
					this.sumCourse = list.get(0).toString();
				} else {
					this.sumCourse = "0";
				}

			}

		} catch (EntityException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 专项课程列表
	 * 
	 * @author wangjt
	 * @return
	 */
	public void courseBatchList() {
		String passLine = "80";
		EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagTestPassLine", "0");
		EnumConst ec_isNoExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		EnumConst ec_isExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		if (ec != null) {
			passLine = ec.getName();
		}
		String id = ActionContext.getContext().getSession().get("id").toString().trim();
		String ssoUserId = peBzzStudent.getSsoUser().getId();
		String studentId = peBzzStudent.getId();
		StringBuffer sql = new StringBuffer();
		sql.append(" 	select distinct c.id,																																													");
		sql.append(" c.code,   ");
		sql.append(" c.name,  ");
		sql.append("  c.time,  ");
		sql.append(" ec.name as x,  ");
		sql.append("  o.payment_date,    ");
		sql.append(" tr.get_date,    ");
		sql.append(" tr.learn_status,  ");
		sql.append("case when tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) >= nvl(c.passrole, 0) then 'PASSED' ");
		sql.append(" 	 when tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isNoExam.getId() + "' and ele.completed_time is not null then 'PASSED' ");
		sql.append(" when tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and  nvl(ele.score_exam, 0) < nvl(c.passrole, 0) then 'FAILED' ");
		sql.append("  when tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isNoExam.getId() + "' and ele.completed_time is null then 'FAILED' ");
		sql.append(" 	 else 'UNFINISHED' end E2,ele.fk_tch_opencourse_id  ");
		sql.append("  from  ( ( select score_exam,fk_tch_opencourse_id,fk_stu_id,fk_order_id,COMPLETED_TIME from pr_bzz_tch_stu_elective      ");
		sql.append("  where FK_STU_ID = '" + studentId + "' ) ele    ");
		sql.append(" 	inner join ( select id,fk_sso_user_id,card_no,true_name from pe_bzz_student pbs    ");
		sql.append(" 	where pbs.card_no = '" + peBzzStudent.getCardNo().trim() + "' and pbs.true_name = '" + peBzzStudent.getTrueName().trim()
				+ "' ) stu  on ele.fk_stu_id = stu.id  ");
		sql.append(" 	inner join pr_bzz_tch_opencourse op on ele.fk_tch_opencourse_id = op.id     ");

		sql.append(" 	inner join pe_bzz_tch_course c on c.id = op.fk_course_id    ");
		sql.append(" 	inner join PE_BZZ_BATCH p on p.id= op.fk_batch_id   ");

		sql.append("  left join pe_bzz_order_info o on o.id = ele.fk_order_id    ");
		sql.append("  inner join ( select distinct percent,course_id,student_id,learn_status ,get_date from     ");
		sql.append("  training_course_student where student_id = '" + ssoUserId + "' ) tr on tr.course_id = op.id    ");
		sql.append("  left join enum_const ec on ec.id = c.flag_coursetype  )     where 1=1     ");
		sql.append("  and p.id='" + id + "'   ");
		if ("INCOMPLETE".equals(status)) {
			sql.append(" and tr.learn_status='INCOMPLETE' ");
		} else if ("COMPLETED".equals(status)) {
			sql.append(" and tr.learn_status='COMPLETED'  ");
		} else if ("UNCOMPLETE".equals(status)) {
			sql.append(" and tr.learn_status='UNCOMPLETE' ");
		} else if ("GUOQI".equals(status)) {
			sql.append(" and tr.learn_status<>'COMPLETED' AND TR.GET_DATE + STUDYDATES < SYSDATE AND PRICE > 0 AND ELE.DEL = '1' ");
		} else if ("DEL".equals(status)) {
			sql.append(" and ELE.DEL = '0' ");
		}

		if ("PASSED".equals(this.getExamStatus())) {
			sql.append(" and ( (tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) >= nvl(c.passrole, 0)) ");
			sql.append(" ) ");
			// sql.append(" or ( tr.learn_status = 'COMPLETED' and
			// c.flag_is_exam = '" + ec_isNoExam.getId() + "' and
			// ele.completed_time is not null ) ) ");
		} else if ("FAILED".equals(this.getExamStatus())) {
			sql.append(" and ( (tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) < nvl(c.passrole, 0)) or ");
			sql.append(" ( tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isNoExam.getId() + "' and ele.completed_time is null )	) ");
		} else if ("UNFINISHED".equals(this.getExamStatus())) {
			sql.append("  and tr.learn_status <> 'COMPLETED'");
		} else if ("NOEXAMLEE".equals(this.getExamStatus())) {
			sql.append(" and c.flag_is_exam = '" + ec_isNoExam.getId() + "' ");
		}
		/*
		 * 支付时间-开始时间
		 */
		if (paymentDateStart != null && !paymentDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + paymentDateStart + "', 'yyyy-mm-dd')");
			// paymentDateStart = null;
		}
		/*
		 * 支付时间-结束时间
		 */
		if (paymentDateEnd != null && !paymentDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + paymentDateEnd + "', 'yyyy-mm-dd')");
			// paymentDateEnd = null;
		}
		/*
		 * 开始学习时间-开始时间
		 */
		if (studyDateStart != null && !studyDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + studyDateStart + "', 'yyyy-mm-dd')");
			// studyDateStart = null;
		}
		/**
		 * 课程编号
		 */
		if (courseNo != null && !courseNo.equalsIgnoreCase("")) {
			sql.append(" and c.CODE= '" + courseNo + "'");
			// studyDateStart = null;
		}
		/**
		 * 课程名称
		 */
		if (courseName != null && !courseName.equalsIgnoreCase("")) {
			sql.append(" and c.NAME= '" + courseName + "'");
		}
		/**
		 * 课程性质
		 */
		if (courseNature != null && !courseNature.equalsIgnoreCase("")) {
			sql.append(" and c.FLAG_COURSETYPE= '" + courseNature + "'");
			// studyDateStart = null;
		}
		/*
		 * 开始学习时间-结束时间
		 */
		if (studyDateEnd != null && !studyDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + studyDateEnd + "', 'yyyy-mm-dd')");
			// studyDateEnd = null;
		}
		sql.append(" order by  o.payment_date desc ");
		try {
			list = this.getGeneralService().getBySQL(sql.toString());
		} catch (EntityException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 学时总数
	 */
	public void sumCourse() {
		String ssoUserId = peBzzStudent.getSsoUser().getId();
		String studentId = peBzzStudent.getId();
		StringBuffer sql = new StringBuffer();
		String passLine = "80";
		EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagTestPassLine", "0");
		EnumConst ec_isNoExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		EnumConst ec_isExam = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		if (ec != null) {
			passLine = ec.getName();
		}
		sql.append(" SELECT NVL(SUM(C.TIME),0) ");
		sql.append("   FROM ((SELECT FK_TRAINING_ID, ");
		sql.append("                 SCORE_EXAM, ");
		sql.append("                 FK_TCH_OPENCOURSE_ID, ");
		sql.append("                 FK_STU_ID, ");
		sql.append("                 FK_ORDER_ID, ");
		sql.append("                 COMPLETED_TIME, ");
		sql.append("                 PER.COMPLETED_TIME AS T, ");
		sql.append("                 PER.ISPASS AS TS, ");
		sql.append("                 '1' DEL ");
		sql.append("            FROM PR_BZZ_TCH_STU_ELECTIVE PER ");
		sql.append("           WHERE FK_STU_ID = '" + studentId + "' ");
		sql.append("          UNION ");
		sql.append("          SELECT FK_TRAINING_ID, ");
		sql.append("                 SCORE_EXAM, ");
		sql.append("                 FK_TCH_OPENCOURSE_ID, ");
		sql.append("                 FK_STU_ID, ");
		sql.append("                 FK_ORDER_ID, ");
		sql.append("                 COMPLETED_TIME, ");
		sql.append("                 PER.COMPLETED_TIME AS T, ");
		sql.append("                 PER.ISPASS AS TS, ");
		sql.append("                 '0' DEL ");
		sql.append("            FROM ELECTIVE_HISTORY PER ");
		sql.append("           WHERE FK_STU_ID = '" + studentId + "') ELE INNER JOIN ");
		sql.append("         (SELECT ID, FK_SSO_USER_ID, CARD_NO, TRUE_NAME ");
		sql.append("            FROM PE_BZZ_STUDENT PBS ");
		sql.append("           WHERE PBS.CARD_NO = '" + peBzzStudent.getCardNo().trim() + "' ");
		sql.append("             AND PBS.TRUE_NAME = '" + peBzzStudent.getTrueName().trim() + "') STU ON ELE.FK_STU_ID = STU.ID INNER JOIN ");
		sql.append("         PR_BZZ_TCH_OPENCOURSE OP ON ELE.FK_TCH_OPENCOURSE_ID = OP.ID ");
		if (null != this.getBatchId() && !"".equals(this.getBatchId())) {
			sql.append(" and OP.FK_BATCH_ID='" + this.getBatchId() + "' ");
		}
		sql.append("         INNER JOIN PE_BZZ_TCH_COURSE C ON C.ID = OP.FK_COURSE_ID INNER JOIN ENUM_CONST ECS ON ");
		sql.append("         C.FLAG_IS_EXAM = ECS.ID LEFT JOIN PE_BZZ_ORDER_INFO O ON ");
		sql.append("         O.ID = ELE.FK_ORDER_ID INNER JOIN ");
		sql.append("         (SELECT ID, PERCENT, COURSE_ID, STUDENT_ID, LEARN_STATUS, GET_DATE ");
		sql.append("            FROM TRAINING_COURSE_STUDENT ");
		sql.append("           WHERE STUDENT_ID = '" + ssoUserId + "' ");
		sql.append("          UNION ");
		sql.append("          SELECT ID, PERCENT, COURSE_ID, STUDENT_ID, LEARN_STATUS, GET_DATE ");
		sql.append("            FROM TCS_HISTORY ");
		sql.append("           WHERE STUDENT_ID = '" + ssoUserId + "') TR ON ");
		sql.append("         TR.ID = ELE.FK_TRAINING_ID LEFT JOIN ENUM_CONST EC ON ");
		sql.append("         EC.ID = C.FLAG_COURSETYPE LEFT JOIN ENUM_CONST CTY ON ");
		sql.append("         CTY.ID = C.FLAG_COURSEAREA ");
		// 获得学时-开始时间
		if (tmDateStart != null && !tmDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(ele.completed_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + tmDateStart + "', 'yyyy-mm-dd')");
		}
		// 获得学时-结束时间
		if (tmDateEnd != null && !tmDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(ele.completed_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + tmDateEnd + "', 'yyyy-mm-dd')");
		}
		sql.append(" )WHERE 1 = 1 ");
		// 学习状态
		if ("INCOMPLETE".equals(status)) {
			sql.append(" and tr.learn_status='INCOMPLETE' ");
		} else if ("COMPLETED".equals(status)) {
			sql.append(" and tr.learn_status='COMPLETED'  ");
		} else if ("UNCOMPLETE".equals(status)) {
			sql.append(" and tr.learn_status='UNCOMPLETE' ");
		} else if ("GUOQI".equals(status)) {
			sql.append(" and tr.learn_status<>'COMPLETED' AND TR.GET_DATE + STUDYDATES < SYSDATE AND PRICE > 0 AND ELE.DEL = '1' ");
		} else if ("DEL".equals(status)) {
			sql.append(" and ELE.DEL = '0' ");
		}
		// 考试结果
		if ("PASSED".equals(this.getExamStatus())) {
			sql.append(" and ( (tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) >= nvl(c.passrole, 0)) ");
			sql.append(" ) ");
			// sql.append(" or ( tr.learn_status = 'COMPLETED' and
			// c.flag_is_exam = '" + ec_isNoExam.getId() + "' and
			// ele.completed_time is not null ) ) ");
		} else if ("FAILED".equals(this.getExamStatus())) {
			sql.append(" and ( (tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isExam.getId() + "' and nvl(ele.score_exam,0) < nvl(c.passrole, 0)) or ");
			sql.append(" ( tr.learn_status = 'COMPLETED' and c.flag_is_exam = '" + ec_isNoExam.getId() + "' and ele.completed_time is null )	) ");
		} else if ("UNFINISHED".equals(this.getExamStatus())) {
			sql.append("  and tr.learn_status <> 'COMPLETED'");
		} else if ("NOEXAMLEE".equals(this.getExamStatus())) {
			sql.append(" and c.flag_is_exam = '" + ec_isNoExam.getId() + "' ");
		}
		// 支付时间-开始时间
		if (paymentDateStart != null && !paymentDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + paymentDateStart + "', 'yyyy-mm-dd')");
		}
		// 支付时间-结束时间
		if (paymentDateEnd != null && !paymentDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(o.payment_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + paymentDateEnd + "', 'yyyy-mm-dd')");
		}
		// 开始学习时间-开始时间
		if (studyDateStart != null && !studyDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + studyDateStart + "', 'yyyy-mm-dd')");
		}
		// 开始学习时间-结束时间
		if (studyDateEnd != null && !studyDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_date(to_char(tr.get_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + studyDateEnd + "', 'yyyy-mm-dd')");
		}
		// 课程编号查询
		if (courseNo != null && !courseNo.equalsIgnoreCase("")) {
			sql.append(" and c.code='" + courseNo + "'");
		}
		// 课程名称
		if (courseName != null && !courseName.equalsIgnoreCase("")) {
			sql.append(" and c.name like '%" + courseName + "%'");
		}
		// 课程所属区域
		if (courseRegion != null && !courseRegion.equalsIgnoreCase("")) {
			sql.append(" and CTY.id like '%" + courseRegion + "%'");
		}
		// 课程性质
		if (courseNature != null && !courseNature.equalsIgnoreCase("") && !courseNature.equals("all")) {
			sql.append(" and ec.id='" + courseNature + "'");
		}
		// 开始-获得学时时间
		if (gainClassHourDateStart != null && !gainClassHourDateStart.equalsIgnoreCase("")) {
			sql.append(" and to_char(ele.completed_time,'yyyy-MM-dd')>='" + gainClassHourDateStart + "'");
		}
		// 结束-获得学时时间
		if (gainClassHourDateEnd != null && !gainClassHourDateEnd.equalsIgnoreCase("")) {
			sql.append(" and to_char(ele.completed_time,'yyyy-MM-dd')<='" + gainClassHourDateEnd + "'");
		}
		// 订单号
		if (seq != null && !seq.equalsIgnoreCase("")) {
			sql.append(" and o.seq like '%" + seq + "%'");
		}
		try {
			if (this.getGeneralService().getBySQL(sql.toString()).size() > 0) {
				List list = (List) this.getGeneralService().getBySQL(sql.toString());
				if (list.get(0) != null) {
					this.sumCourse = list.get(0).toString();
				} else {
					this.sumCourse = "0";
				}

			}

		} catch (EntityException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查看学习详情
	 * 
	 * @author linjie
	 * @return
	 */
	public String electivedCourse() {
		String return_str;
		if (!"stu".equals(flag)) {
			this.searchStudent();// 查询学生信息
		} else {
			this.peBzzStudent = this.getPe(this.stuId);
		}
		if (this.peBzzStudent.getId() != null) {
			if ((peBzzStudent.getPeEnterprise().getPeEnterprise() == null && "V".equals(peBzzStudent.getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode()))
					|| (peBzzStudent.getPeEnterprise().getPeEnterprise() != null && "V".equals(peBzzStudent.getPeEnterprise().getPeEnterprise().getEnumConstByFlagEnterpriseType()
							.getCode()))) {
				return_str = "reg_electivedCourse";
			} else {
				return_str = "electivedCourse";
			}
			this.courseList();// 查询学生课程信息
			// this.sumCourse();// 学时总数
			// this.sumCompleteTime();// 已完成、已获得学时数 dgh
			// this.countbxaction();// 必修的课数和学时
			// this.countxxaction();// 选修的课数和学时
			// ServletActionContext.getRequest().setAttribute("courseCount",
			// Integer.parseInt(countbx) + Integer.parseInt(countxx));
		} else {
			this.setMsg("此学员不存在，请检查检索条件");
			// Lee 注释原因：点击链接后 弹出窗口显示新页面 但后台窗口会依据下边方法进行跳转 注释掉 取消后台跳转
			// this.setTogo("/entity/teacher/search_any_student.jsp");
			this.setTogo("close");
			return "msg";
		}
		return return_str;
	}

	/**
	 * 查看获得专项学时情况
	 * 
	 * @author linjie
	 * @return
	 */
	public String electivedBatchCourse() {
		if (!"stu".equals(flag)) {
			this.searchStudent();
		} else {
			this.peBzzStudent = this.getPe(this.stuId);
		}
		if (this.peBzzStudent.getId() != null) {
			this.courseBatchList();
			this.sumBatchCourse();

		} else {
			this.setMsg("此学员不存在，请检查检索条件");
			this.setTogo("/entity/teacher/search_any_student.jsp");
			return "msg";
		}
		return "electivedBatchCourse";
	}

	/**
	 * 考试结果
	 * 
	 * @author linjie
	 * @return
	 */
	public int examResult(String score, String passRole, double percent) {
		double passScore = percent * Double.parseDouble(passRole);
		double d_score = Double.parseDouble(score);
		if (score.equals("null") || percent == 0.0) { // 未开始学习
			return 0;
		} else if (d_score >= passScore) { // 通过
			return 1;
		} else if (d_score < passScore) { // 未通过
			return 2;
		} else { // 未预知情况
			return 3;
		}
	}

	/**
	 * 课程信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String courseInfo() {
		if (null != this.getAllid() && !"".equals(this.getAllid()) && this.getAllid().indexOf("-") > 0) {// 是否为公共课程查询查看学习情况
			String[] strAyy = this.getAllid().split("-");
			this.setSid(strAyy[0]);
			this.setCourseId(strAyy[1]);
			this.setOpencourseId(strAyy[2]);
		}
		this.getStudentById(); // 用id查询学生信息
		this.getCourseById(); // 用id查询课程信息
		this.getOpenCourseById();// 用id查询开课信息
		this.getFirstTable(); // 按页面第一个table获得列表信息
		this.getSecondTable(); // 按页面第二个table获得列表信息
		this.getLastTable(); // 按页面第三个table获得列表信息
		this.coursesSituation(); // 所学课程情况
		this.CourseLearningProcess(); // 课程学习过程
		this.satisfactionSurvey(); // 满意度调查
		this.classTest(); // 课后测验
		return "courseInfo";
	}

	/**
	 * 公司内训课程信息
	 * 
	 * @author lzh
	 * @return
	 */
	public String courseInfos() {
		// HttpServletRequest request = ServletActionContext.getRequest();
		// String ids =request.getParameter("id");
		if (null != this.getAllid() && !"".equals(this.getAllid()) && this.getAllid().indexOf("-") > 0) {// 是否为公共课程查询查看学习情况
			String[] strAyy = this.getAllid().split("-");
			this.setSid(strAyy[0]);
			this.setCourseId(strAyy[1]);
			this.setOpencourseId(strAyy[2]);
		}

		this.getStudentById(); // 用id查询学生信息
		this.getCourseById(); // 用id查询课程信息
		this.getOpenCourseById();// 用id查询开课信息
		this.getFirstTable(); // 按页面第一个table获得列表信息
		this.getSecondTable(); // 按页面第二个table获得列表信息
		this.getLastTable(); // 按页面第三个table获得列表信息

		this.coursesSituations(); // 所学课程情况
		this.CourseLearningProcess(); // 课程学习过程
		this.satisfactionSurvey(); // 满意度调查
		this.classTest(); // 课后测验
		return "courseInfos";
	}

	/**
	 * 按页面第一个table获得列表信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void getFirstTable() {
		/*
		 * String sql = "select c.id,\n" + " c.name,\n" + " c.code,\n" + "
		 * ec.name as x,\n" + " c.time,\n" + " o.payment_date,\n" + "
		 * ssc.first_date,\n" + " ssc.last_date,\n" + " c.end_date,\n" + "
		 * tr.learn_status,\n" + " tr.score\n" + " from pr_bzz_tch_stu_elective
		 * ele\n" + " inner join pe_bzz_student stu on stu.id =
		 * ele.fk_stu_id\n" + " inner join pr_bzz_tch_opencourse op on
		 * ele.fk_tch_opencourse_id = op.id\n" + " inner join pe_bzz_tch_course
		 * c on c.id = op.fk_course_id\n" + " left join scorm_stu_course ssc on
		 * c.code = ssc.course_id\n" + " left join enum_const ec on
		 * c.flag_coursetype = ec.id\n" + " left join training_course_student tr
		 * on tr.student_id =\n" + " stu.fk_sso_user_id\n" + " left join
		 * pe_bzz_order_info o on o.create_user = stu.fk_sso_user_id\n" + "
		 * where c.id = '"+this.getCourseId()+"'\n" + " and stu.id =
		 * '"+peBzzStudent.getId()+"'\n" + " and tr.course_id = op.id";
		 */String passLine = "80";
		EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagTestPassLine", "0");
		if (ec != null) {
			passLine = ec.getName();
		}
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("	select distinct pbtc.id,pbtc.name,pbtc.code,ec.name as type,pbtc.time,to_char(pboi.payment_date, 'YYYY-MM-DD HH24:MI:SS'),to_char(tcs.get_date, 'YYYY-MM-DD HH24:MI:SS'),to_char(tcs.complete_date, 'YYYY-MM-DD HH24:MI:SS'),pbtc.STUDYDATES,tcs.learn_status, ");
		sqlBuff.append("  case when tcs.learn_status = 'COMPLETED' and (pbtse.score_exam >= " + passLine
				+ " or pbtse.completed_time is not null) then 'PASSED'                                             ");
		sqlBuff.append("  when tcs.learn_status = 'COMPLETED' and pbtse.score_exam < " + passLine + " then 'FAILED'                                                   ");
		sqlBuff.append("  else 'UNFINISHED' end as examstatus                                                                                        ");
		sqlBuff.append("  from                                                                                                                          ");
		sqlBuff.append("  pe_bzz_tch_course pbtc,                                                                                                       ");
		sqlBuff.append("  ( select distinct LEARN_STATUS ,COURSE_ID,get_date, complete_date from training_course_student where STUDENT_ID = '"
				+ this.getPeBzzStudent().getSsoUser().getId() + "' ) tcs,                 ");
		sqlBuff.append("   ( select id,FK_COURSE_ID from pr_bzz_tch_opencourse where fk_course_id = '" + this.getCourseId() + "') pbto,                                     ");
		sqlBuff.append("   ( select SCORE_EXAM,FK_ORDER_ID,FK_TCH_OPENCOURSE_ID,completed_time from  PR_BZZ_TCH_STU_ELECTIVE  where FK_STU_ID = '" + this.getPeBzzStudent().getId()
				+ "' ) pbtse,  ");
		sqlBuff.append("   pe_bzz_order_info pboi,                                                                                                      ");
		sqlBuff.append("   enum_const ec,                                                                                                               ");
		sqlBuff.append("   ( select student_id,last_date,course_id from scorm_stu_course where course_id = '" + this.getCourseId() + "' and student_id = '"
				+ this.getPeBzzStudent().getId() + "' ) ssc ");
		sqlBuff.append("   where pbto.id = tcs.COURSE_ID                                                                                                ");
		sqlBuff.append("   and pbtc.id = pbto.FK_COURSE_ID(+)                                                                                           ");
		sqlBuff.append("   and pbtse.fk_order_id = pboi.id(+)                                                                                           ");
		sqlBuff.append("   and pbtse.FK_TCH_OPENCOURSE_ID = pbto.id                                                                                     ");
		sqlBuff.append("   and ec.id = pbtc.flag_coursetype                                                                                             ");
		sqlBuff.append("   and ssc.course_id(+) = pbtc.id                                                                                               ");

		try {
			firstList = this.getGeneralService().getBySQL(sqlBuff.toString());
		} catch (EntityException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 所学课程情况
	 * 
	 * @author hdg
	 * @return
	 */
	public void coursesSituation() {
		String passLine = "80";
		EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagTestPassLine", "0");
		if (ec != null) {
			passLine = ec.getName();
		}
		StringBuffer sqlBuff = new StringBuffer();
		String sql = "SELECT 1 FROM PE_BZZ_TCH_COURSE WHERE FLAG_COURSEAREA = 'Coursearea0' AND ID = '" + this.getCourseId() + "'";
		List liveList = null;
		try {
			liveList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (null == liveList || liveList.size() == 0) {// 非直播课程

			sqlBuff.append(" SELECT DISTINCT * ");
			sqlBuff.append("   FROM (SELECT PBOI.SEQ, ");
			sqlBuff.append("                PBTC.CODE CODE, ");
			sqlBuff.append("                PBTC.NAME NAME, ");
			sqlBuff.append("                EC.NAME EC1NAME, ");
			sqlBuff.append("                PBTC.TIME, ");
			sqlBuff.append("                TO_CHAR(PBOI.PAYMENT_DATE, 'yyyy-MM-dd hh24:mi:ss') PAYMENT_DATE, ");
			sqlBuff.append("                TO_CHAR(TCS.GET_DATE, 'yyyy-MM-dd hh24:mi:ss') START_TIME, ");
			sqlBuff.append("                DECODE(PBTSE.START_TIME, ");
			sqlBuff.append("                       NULL, ");
			sqlBuff.append("                       '-', ");
			sqlBuff.append("                       TO_CHAR(SSS.LAST_ACCESSDATE, 'yyyy-MM-dd hh24:mi:ss')) END_TIME, ");
			sqlBuff.append("                TO_CHAR(TCS.COMPLETE_DATE, 'yyyy-MM-dd hh24:mi:ss') COMPLETE_DATE2, ");
			sqlBuff.append("                PBTC.STUDYDATES, ");
			sqlBuff.append("                TCS.LEARN_STATUS, ");
			sqlBuff.append("                CASE ");
			sqlBuff.append("                  WHEN TCS.LEARN_STATUS = 'COMPLETED' AND ");
			sqlBuff.append("                       (PBTSE.SCORE_EXAM >= PBTC.PASSROLE OR ");
			sqlBuff.append("                       PBTSE.COMPLETED_TIME IS NOT NULL) AND ");
			sqlBuff.append("                       PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001' THEN ");
			sqlBuff.append("                   'PASSED' ");
			sqlBuff.append("                  WHEN PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae0130d030002' THEN ");
			sqlBuff.append("                   'NOEXAMLEE' ");
			sqlBuff.append("                  WHEN TCS.LEARN_STATUS = 'COMPLETED' AND ");
			sqlBuff.append("                       PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001' AND ");
			sqlBuff.append("                       PBTSE.SCORE_EXAM < 80 THEN ");
			sqlBuff.append("                   'FAILED' ");
			sqlBuff.append("                  ELSE ");
			sqlBuff.append("                   'UNFINISHED' ");
			sqlBuff.append("                END AS EXAMSTATUS, ");
			sqlBuff.append("                PBTSE.ISPASS, ");
			sqlBuff.append("                TO_CHAR(PBTSE.COMPLETED_TIME, 'yyyy-MM-dd hh24:mi:ss') COMPLETED_TIME, ");
			sqlBuff.append("                PBTC.FLAG_ISFREE ");
			sqlBuff.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
			sqlBuff.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuff.append("             ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuff.append("            AND PBTC.ID = '" + this.getCourseId() + "' ");
			sqlBuff.append("          INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sqlBuff.append("             ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
			sqlBuff.append("          INNER JOIN PE_BZZ_STUDENT PBS ");
			sqlBuff.append("             ON PBTSE.FK_STU_ID = PBS.ID ");
			sqlBuff.append("            AND PBS.ID = '" + this.getPeBzzStudent().getId() + "' ");
			sqlBuff.append("          INNER JOIN SSO_USER SU ");
			sqlBuff.append("             ON PBS.FK_SSO_USER_ID = SU.ID ");
			sqlBuff.append("           LEFT JOIN SCORM_STU_SCO SSS ");
			sqlBuff.append("             ON SU.ID = SSS.STUDENT_ID ");
			sqlBuff.append("            AND PBTC.CODE = SSS.COURSE_ID ");
			sqlBuff.append("          LEFT JOIN ENUM_CONST EC ");
			sqlBuff.append("             ON PBTC.FLAG_COURSETYPE = EC.ID ");
			sqlBuff.append("           LEFT JOIN TRAINING_COURSE_STUDENT TCS ");
			sqlBuff.append("             ON PBTSE.FK_TRAINING_ID = TCS.ID ");
			sqlBuff.append("           LEFT JOIN PE_BZZ_ORDER_INFO PBOI ");
			sqlBuff.append("             ON PBTSE.FK_ORDER_ID = PBOI.ID ");
			sqlBuff.append("         UNION ALL ");
			sqlBuff.append("         SELECT PBOI.SEQ, ");
			sqlBuff.append("                PBTC.CODE CODE, ");
			sqlBuff.append("                PBTC.NAME NAME, ");
			sqlBuff.append("                EC.NAME EC1NAME, ");
			sqlBuff.append("                PBTC.TIME, ");
			sqlBuff.append("                TO_CHAR(PBOI.PAYMENT_DATE, 'yyyy-MM-dd hh24:mi:ss') PAYMENT_DATE, ");
			sqlBuff.append("                TO_CHAR(TCS.GET_DATE, 'yyyy-MM-dd hh24:mi:ss') START_TIME, ");
			sqlBuff.append("                TO_CHAR(PBTSE.DEL_DATE, 'yyyy-MM-dd hh24:mi:ss') END_TIME, ");
			sqlBuff.append("                TO_CHAR(TCS.COMPLETE_DATE, 'yyyy-MM-dd hh24:mi:ss') COMPLETE_DATE, ");
			sqlBuff.append("                PBTC.STUDYDATES, ");
			sqlBuff.append("                DECODE(TCS.LEARN_STATUS, ");
			sqlBuff.append("                       'COMPLETED', ");
			sqlBuff.append("                       TCS.LEARN_STATUS, ");
			sqlBuff.append("                       'GUOQI') LEARN_STATUS, ");
			sqlBuff.append("                CASE ");
			sqlBuff.append("                  WHEN TCS.LEARN_STATUS = 'COMPLETED' AND ");
			sqlBuff.append("                       (PBTSE.SCORE_EXAM >= PBTC.PASSROLE OR ");
			sqlBuff.append("                       PBTSE.COMPLETED_TIME IS NOT NULL) AND ");
			sqlBuff.append("                       PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001' THEN ");
			sqlBuff.append("                   'PASSED' ");
			sqlBuff.append("                  WHEN PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae0130d030002' THEN ");
			sqlBuff.append("                   'NOEXAMLEE' ");
			sqlBuff.append("                  WHEN TCS.LEARN_STATUS = 'COMPLETED' AND ");
			sqlBuff.append("                       PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001' AND ");
			sqlBuff.append("                       PBTSE.SCORE_EXAM < 80 THEN ");
			sqlBuff.append("                   'FAILED' ");
			sqlBuff.append("                  ELSE ");
			sqlBuff.append("                   'UNFINISHED' ");
			sqlBuff.append("                END AS EXAMSTATUS, ");
			sqlBuff.append("                PBTSE.ISPASS, ");
			sqlBuff.append("                TO_CHAR(PBTSE.COMPLETED_TIME, 'yyyy-MM-dd hh24:mi:ss') COMPLETED_TIME, ");
			sqlBuff.append("                PBTC.FLAG_ISFREE ");
			sqlBuff.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
			sqlBuff.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuff.append("             ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuff.append("            AND PBTC.ID = '" + this.getCourseId() + "' ");
			sqlBuff.append("          INNER JOIN ELECTIVE_HISTORY PBTSE ");
			sqlBuff.append("             ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
			sqlBuff.append("          INNER JOIN PE_BZZ_STUDENT PBS ");
			sqlBuff.append("             ON PBTSE.FK_STU_ID = PBS.ID ");
			sqlBuff.append("            AND PBS.ID = '" + this.getPeBzzStudent().getId() + "' ");
			sqlBuff.append("          INNER JOIN SSO_USER SU ");
			sqlBuff.append("             ON PBS.FK_SSO_USER_ID = SU.ID ");
			sqlBuff.append("           LEFT JOIN SCORM_STU_SCO SSS ");
			sqlBuff.append("             ON SU.ID = SSS.STUDENT_ID ");
			sqlBuff.append("            AND PBTC.CODE = SSS.COURSE_ID ");
			sqlBuff.append("          LEFT JOIN ENUM_CONST EC ");
			sqlBuff.append("             ON PBTC.FLAG_COURSETYPE = EC.ID ");
			sqlBuff.append("           LEFT JOIN TCS_HISTORY TCS ");
			sqlBuff.append("             ON PBTSE.FK_TRAINING_ID = TCS.ID ");
			sqlBuff.append("           LEFT JOIN PE_BZZ_ORDER_INFO PBOI ");
			sqlBuff.append("             ON PBTSE.FK_ORDER_ID = PBOI.ID) ");
			sqlBuff.append("  ORDER BY TO_DATE(START_TIME, 'yyyy-MM-dd hh24:mi:ss') DESC ");
			// sqlBuff.append(" SELECT DISTINCT PBOI.SEQ, PBTC.CODE, PBTC.NAME,
			// EC.NAME AS TYPE, PBTC.TIME, ");
			// sqlBuff.append(" TO_CHAR(PBOI.PAYMENT_DATE, 'yyyy-MM-dd
			// hh24:mi:ss'), TO_CHAR(TCS.GET_DATE, 'yyyy-MM-dd hh24:mi:ss'), ");
			// sqlBuff.append("
			// DECODE(PBTSE.START_TIME,NULL,'-',TO_CHAR(SSS.LAST_ACCESSDATE,
			// 'yyyy-MM-dd hh24:mi:ss')), TO_CHAR(TCS.COMPLETE_DATE, 'yyyy-MM-dd
			// hh24:mi:ss'), ");
			// sqlBuff.append(" PBTC.STUDYDATES, TCS.LEARN_STATUS, ");
			// sqlBuff
			// .append(" CASE WHEN TCS.LEARN_STATUS = 'COMPLETED' AND
			// (PBTSE.SCORE_EXAM >= PBTC.PASSROLE OR PBTSE.COMPLETED_TIME IS NOT
			// NULL) AND PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001'
			// THEN 'PASSED' ");
			// sqlBuff.append(" WHEN PBTC.FLAG_IS_EXAM =
			// '40288acf3ae01103013ae0130d030002' THEN 'NOEXAMLEE' ");
			// sqlBuff.append(" WHEN TCS.LEARN_STATUS = 'COMPLETED' AND
			// PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001' AND
			// PBTSE.SCORE_EXAM < 80 THEN 'FAILED' ");
			// sqlBuff.append(" ELSE 'UNFINISHED' END AS EXAMSTATUS,
			// TO_CHAR(PBTSE.COMPLETED_TIME, 'yyyy-MM-dd hh24:mi:ss')
			// ,pbtc.flag_isfree ");
			// sqlBuff.append(" FROM PE_BZZ_TCH_COURSE PBTC INNER JOIN
			// PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID AND
			// PBTC.ID = '" + this.getCourseId() + "' ");
			// sqlBuff.append(" INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON
			// PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
			// sqlBuff.append(" INNER JOIN PE_BZZ_STUDENT PBS ON PBTSE.FK_STU_ID
			// = PBS.ID AND PBS.ID = '" + this.getPeBzzStudent().getId() + "'
			// ");
			// sqlBuff.append(" INNER JOIN SSO_USER SU ON PBS.FK_SSO_USER_ID =
			// SU.ID ");
			// sqlBuff.append(" LEFT JOIN SCORM_STU_SCO SSS ON SU.ID =
			// SSS.STUDENT_ID AND PBTC.CODE = SSS.COURSE_ID ");
			// sqlBuff.append(" INNER JOIN ENUM_CONST EC ON PBTC.FLAG_COURSETYPE
			// = EC.ID ");
			// sqlBuff.append(" LEFT JOIN TRAINING_COURSE_STUDENT TCS ON
			// PBTSE.FK_TRAINING_ID = TCS.ID ");
			// sqlBuff.append(" LEFT JOIN PE_BZZ_ORDER_INFO PBOI ON
			// PBTSE.FK_ORDER_ID = PBOI.ID ");
			// sqlBuff.append(" UNION ALL ");
			// sqlBuff.append(" SELECT DISTINCT PBOI.SEQ, PBTC.CODE, PBTC.NAME,
			// EC.NAME AS TYPE, PBTC.TIME, ");
			// sqlBuff.append(" TO_CHAR(PBOI.PAYMENT_DATE, 'yyyy-MM-dd
			// hh24:mi:ss'), TO_CHAR(SSS.FIRST_ACCESSDATE, 'yyyy-MM-dd
			// hh24:mi:ss'), ");
			// sqlBuff.append(" TO_CHAR(PBTSE.DEL_DATE, 'yyyy-MM-dd
			// hh24:mi:ss'), TO_CHAR(TCS.COMPLETE_DATE, 'yyyy-MM-dd
			// hh24:mi:ss'), ");
			// sqlBuff.append(" PBTC.STUDYDATES, TCS.LEARN_STATUS, ");
			// sqlBuff
			// .append(" CASE WHEN TCS.LEARN_STATUS = 'COMPLETED' AND
			// (PBTSE.SCORE_EXAM >= PBTC.PASSROLE OR PBTSE.COMPLETED_TIME IS NOT
			// NULL) AND PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001'
			// THEN 'PASSED' ");
			// sqlBuff.append(" WHEN PBTC.FLAG_IS_EXAM =
			// '40288acf3ae01103013ae0130d030002' THEN 'NOEXAMLEE' ");
			// sqlBuff.append(" WHEN TCS.LEARN_STATUS = 'COMPLETED' AND
			// PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001' AND
			// PBTSE.SCORE_EXAM < 80 THEN 'FAILED' ");
			// sqlBuff.append(" ELSE 'UNFINISHED' END AS EXAMSTATUS,
			// TO_CHAR(PBTSE.COMPLETED_TIME, 'yyyy-MM-dd hh24:mi:ss')
			// ,pbtc.flag_isfree ");
			// sqlBuff.append(" FROM PE_BZZ_TCH_COURSE PBTC INNER JOIN
			// PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID AND
			// PBTC.ID = '" + this.getCourseId() + "' ");
			// sqlBuff.append(" INNER JOIN ELECTIVE_HISTORY PBTSE ON PBTO.ID =
			// PBTSE.FK_TCH_OPENCOURSE_ID ");
			// sqlBuff.append(" INNER JOIN PE_BZZ_STUDENT PBS ON PBTSE.FK_STU_ID
			// = PBS.ID AND PBS.ID = '" + this.getPeBzzStudent().getId() + "'
			// ");
			// sqlBuff.append(" INNER JOIN SSO_USER SU ON PBS.FK_SSO_USER_ID =
			// SU.ID ");
			// sqlBuff.append(" LEFT JOIN SCORM_STU_SCO SSS ON SU.ID =
			// SSS.STUDENT_ID AND PBTC.CODE = SSS.COURSE_ID ");
			// sqlBuff.append(" INNER JOIN ENUM_CONST EC ON PBTC.FLAG_COURSETYPE
			// = EC.ID ");
			// sqlBuff.append(" LEFT JOIN TCS_HISTORY TCS ON
			// PBTSE.FK_TRAINING_ID = TCS.ID ");
			// sqlBuff.append(" LEFT JOIN PE_BZZ_ORDER_INFO PBOI ON
			// PBTSE.FK_ORDER_ID = PBOI.ID ");
		} else {// 直播课程
			sqlBuff.append(" SELECT DISTINCT PBOI.SEQ, ");
			sqlBuff.append("                 PBTC.CODE, ");
			sqlBuff.append("                 PBTC.NAME, ");
			sqlBuff.append("                 EC.NAME AS TYPE, ");
			sqlBuff.append("                 PBTC.TIME, ");
			sqlBuff.append("                 TO_CHAR(PBOI.PAYMENT_DATE, 'yyyy-MM-dd hh24:mi:ss'), ");
			sqlBuff.append("                 TO_CHAR(TCS.GET_DATE, 'yyyy-MM-dd hh24:mi:ss'), ");
			sqlBuff.append("                 TO_CHAR(TCS.COMPLETE_DATE, 'yyyy-MM-dd hh24:mi:ss'), ");
			sqlBuff.append("                 TO_CHAR(TCS.COMPLETE_DATE, 'yyyy-MM-dd hh24:mi:ss'), ");
			sqlBuff.append("                 '-' STUDYDATES, ");
			sqlBuff.append("                 TCS.LEARN_STATUS, ");
			sqlBuff.append("                 'NOEXAMLEE' AS EXAMSTATUS, ");
			sqlBuff.append("                 TO_CHAR(PBTSE.COMPLETED_TIME, 'yyyy-MM-dd hh24:mi:ss') ");
			sqlBuff.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
			sqlBuff.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuff.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuff.append("  INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sqlBuff.append("     ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
			sqlBuff.append("  INNER JOIN PE_BZZ_STUDENT PBS ");
			sqlBuff.append("     ON PBTSE.FK_STU_ID = PBS.ID ");
			sqlBuff.append("  INNER JOIN SSO_USER SU ");
			sqlBuff.append("     ON PBS.FK_SSO_USER_ID = SU.ID ");
			sqlBuff.append("  INNER JOIN ENUM_CONST EC ");
			sqlBuff.append("     ON PBTC.FLAG_COURSETYPE = EC.ID ");
			sqlBuff.append("   LEFT JOIN TRAINING_COURSE_STUDENT TCS ");
			sqlBuff.append("     ON PBTSE.FK_TRAINING_ID = TCS.ID ");
			sqlBuff.append("   LEFT JOIN PE_BZZ_ORDER_INFO PBOI ");
			sqlBuff.append("     ON PBTSE.FK_ORDER_ID = PBOI.ID ");
			sqlBuff.append("  WHERE PBS.ID = '" + this.getPeBzzStudent().getId() + "' ");
			sqlBuff.append("    AND PBTC.ID = '" + this.getCourseId() + "' ");
		}
		try {
			coursesSituation = this.getGeneralService().getBySQL(sqlBuff.toString());
		} catch (EntityException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 所学课程情况
	 * 
	 * @author lzh
	 * @return
	 */
	public void coursesSituations() {
		String passLine = "80";
		EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagTestPassLine", "0");
		if (ec != null) {
			passLine = ec.getName();
		}
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" SELECT DISTINCT  PBTC.CODE, PBTC.NAME, EC.NAME AS TYPE, PBTC.Course_Len, ");
		sqlBuff.append(" TO_CHAR(TCS.GET_DATE, 'yyyy-MM-dd hh24:mi:ss'), ");
		sqlBuff.append(" TO_CHAR(SSS.LAST_ACCESSDATE, 'yyyy-MM-dd hh24:mi:ss'), TO_CHAR(TCS.COMPLETE_DATE, 'yyyy-MM-dd hh24:mi:ss'), ");
		sqlBuff.append(" PBTC.STUDYDATES, TCS.LEARN_STATUS, ");
		sqlBuff.append(" CASE WHEN TCS.LEARN_STATUS = 'COMPLETED' AND (PBTSE.SCORE_EXAM >= PBTC.PASSROLE OR PBTSE.COMPLETED_TIME IS NOT NULL) AND PBTC.FLAG_IS_EXAM =   '40288acf3ae01103013ae012940b0001' THEN 'PASSED' ");
		sqlBuff.append(" WHEN PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae0130d030002' THEN 'NOEXAMLEE' ");
		sqlBuff.append(" WHEN TCS.LEARN_STATUS = 'COMPLETED' AND PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001' AND PBTSE.SCORE_EXAM < 80 THEN 'FAILED' ");
		sqlBuff.append(" ELSE 'UNFINISHED' END AS EXAMSTATUS, TO_CHAR(PBTSE.COMPLETED_TIME, 'yyyy-MM-dd hh24:mi:ss') ");
		sqlBuff.append(" FROM PE_BZZ_TCH_COURSE PBTC INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sqlBuff.append(" INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		sqlBuff.append(" INNER JOIN PE_BZZ_STUDENT PBS ON PBTSE.FK_STU_ID = PBS.ID ");
		sqlBuff.append(" INNER JOIN SSO_USER SU ON PBS.FK_SSO_USER_ID = SU.ID ");
		sqlBuff.append(" INNER JOIN SCORM_STU_SCO SSS ON SU.ID = SSS.STUDENT_ID AND PBTC.CODE = SSS.COURSE_ID ");
		sqlBuff.append(" INNER JOIN ENUM_CONST EC ON PBTC.FLAG_COURSETYPE = EC.ID ");
		sqlBuff.append(" LEFT JOIN TRAINING_COURSE_STUDENT TCS ON PBTSE.FK_TRAINING_ID = TCS.ID ");
		sqlBuff.append(" LEFT JOIN PE_BZZ_ORDER_INFO PBOI ON PBTSE.FK_ORDER_ID = PBOI.ID ");
		sqlBuff.append(" WHERE PBS.ID = '" + this.getPeBzzStudent().getId() + "' AND PBTC.ID = '" + this.getCourseId() + "' ");
		try {
			coursesSituations = this.getGeneralService().getBySQL(sqlBuff.toString());
		} catch (EntityException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 课程学习过程
	 * 
	 * @author hdg
	 * @return
	 */
	public void CourseLearningProcess() {
		String sql = "SELECT 1 FROM PE_BZZ_TCH_COURSE WHERE FLAG_COURSEAREA = 'Coursearea0' AND ID = '" + this.getCourseId() + "'";
		List liveList = null;
		try {
			liveList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (null == liveList || liveList.size() == 0) {// 非直播课程
			StringBuffer sqlBuff = new StringBuffer();
			sqlBuff.append(" SELECT G.SEQ,TO_CHAR(A.START_TIME, 'yyyy-MM-dd hh24:mi:ss') START_TIME, ");
			sqlBuff.append("        NVL(TO_CHAR(A.END_TIME, 'yyyy-MM-dd hh24:mi:ss'), '-') END_TIME, ");
			sqlBuff.append("        NVL(A.TOTAL_TIME, 0) TOTAL_TIME ");
			sqlBuff.append("   FROM TRAINING_COURSE_STUDENT_HIS A ");
			sqlBuff.append("  INNER JOIN PE_BZZ_STUDENT C ");
			sqlBuff.append("     ON A.FK_STU_ID = C.FK_SSO_USER_ID ");
			sqlBuff.append("    AND C.ID = '" + this.getPeBzzStudent().getId() + "' ");
			sqlBuff.append("  INNER JOIN (SELECT FK_STU_ID, FK_TCH_OPENCOURSE_ID ");
			sqlBuff.append("                FROM PR_BZZ_TCH_STU_ELECTIVE ");
			sqlBuff.append("              UNION ALL ");
			sqlBuff.append("              SELECT FK_STU_ID, FK_TCH_OPENCOURSE_ID FROM ELECTIVE_HISTORY) B ");
			sqlBuff.append("     ON C.ID = B.FK_STU_ID ");
			sqlBuff.append("    AND A.COURSE_ID = B.FK_TCH_OPENCOURSE_ID ");
			sqlBuff.append("  INNER JOIN SSO_USER D ");
			sqlBuff.append("     ON C.FK_SSO_USER_ID = D.ID ");
			sqlBuff.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE E ");
			sqlBuff.append("     ON B.FK_TCH_OPENCOURSE_ID = E.ID ");
			sqlBuff.append("  INNER JOIN PE_BZZ_TCH_COURSE F ");
			sqlBuff.append("     ON E.FK_COURSE_ID = F.ID ");
			sqlBuff.append("    AND F.ID = '" + this.getCourseId() + "' ");
			sqlBuff.append("   LEFT JOIN PE_BZZ_ORDER_INFO G ");
			sqlBuff.append("     ON A.FK_ORDER_ID = G.ID ");
			sqlBuff.append("  GROUP BY G.SEQ, A.START_TIME, A.END_TIME, A.TOTAL_TIME ");
			sqlBuff.append("  ORDER BY A.START_TIME DESC ");
			try {
				courseLearningProcess = this.getGeneralService().getBySQL(sqlBuff.toString());
				ServletActionContext.getRequest().setAttribute("studyList", courseLearningProcess);
			} catch (EntityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			StringBuffer sqlBuffTime = new StringBuffer();
			sqlBuffTime.append(" SELECT TOTAL_TIME FROM SCORM_STU_COURSE SSC WHERE SSC.STUDENT_ID = '" + this.getPeBzzStudent().getSsoUser().getId() + "' AND SSC.COURSE_ID = '"
					+ this.getCourseId() + "' ");
			StringBuffer sTime2 = new StringBuffer();
			sTime2.append(" SELECT TOTAL_TIME FROM SCORM_STU_SCO WHERE COURSE_ID = (SELECT CODE FROM PE_BZZ_TCH_COURSE WHERE ID = '" + this.getCourseId() + "') AND STUDENT_ID = '"
					+ this.getPeBzzStudent().getSsoUser().getId() + "'");

			StringBuffer sqlBuffCount = new StringBuffer();
			sqlBuffCount.append("SELECT COUNT(*) AS TOTAL_TIMES from TRAINING_COURSE_STUDENT_HIS tcs ");
			sqlBuffCount.append(" INNER JOIN PE_BZZ_STUDENT pbs ON pbs.id=tcs.fk_stu_id");
			sqlBuffCount.append(" INNER JOIN PR_BZZ_TCH_STU_ELECTIVE pbtse ON pbtse.fk_stu_id=pbs.id ");
			sqlBuffCount.append(" left JOIN PE_BZZ_ORDER_INFO pboi ON pboi.id=pbtse.fk_order_id");
			sqlBuffCount.append(" INNER JOIN PR_BZZ_TCH_OPENCOURSE pbto ON pbto.id=pbtse.fk_tch_opencourse_id AND tcs.course_id=pbto.id ");
			sqlBuffCount.append("   WHERE pbs.id='" + this.getPeBzzStudent().getSsoUser().getId() + "' AND pbto.fk_course_id='" + this.getCourseId() + "'");
			try {
				List arryList = this.getGeneralService().getBySQL(sqlBuffTime.toString());
				if (null != arryList && arryList.size() > 0 && !"null".equals(arryList.get(0))) {
					// 累计学习时间
					ljcourseLearningTime = arryList.get(0).toString();
				} else {
					arryList = this.getGeneralService().getBySQL(sTime2.toString());
					if (null != arryList && arryList.size() > 0 && arryList.get(0) != null && !"null".equals(arryList.get(0))) {
						ljcourseLearningTime = arryList.get(0).toString();
					} else {
						ljcourseLearningTime = "0";
					}
				}
				List arryListt = this.getGeneralService().getBySQL(sqlBuffCount.toString());
				if (null != arryListt && arryListt.size() > 0) {
					// 累计学习次数
					ljcourseLearningTimes = arryListt.get(0).toString();
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
		} else {// 直播课程
			StringBuffer sbProcess = new StringBuffer();
			sbProcess.append(" SELECT G.SEQ, ");
			sbProcess.append("        NVL(TO_CHAR(A.GET_DATE, 'yyyy-MM-dd hh24:mi:ss'), '-'), ");
			sbProcess.append("        NVL(TO_CHAR(A.COMPLETE_DATE, 'yyyy-MM-dd hh24:mi:ss'), '-'), ");
			sbProcess.append("        DECODE(A.COMPLETE_DATE,NULL,'-',NVL(ABC.TOTAL_TIME, 0)) ");
			sbProcess.append("   FROM TRAINING_COURSE_STUDENT A ");
			sbProcess.append("  INNER JOIN PE_BZZ_STUDENT C ");
			sbProcess.append("     ON A.STUDENT_ID = C.FK_SSO_USER_ID ");
			sbProcess.append("  INNER JOIN PR_BZZ_TCH_STU_ELECTIVE B ");
			sbProcess.append("     ON C.ID = B.FK_STU_ID ");
			sbProcess.append("    AND A.COURSE_ID = B.FK_TCH_OPENCOURSE_ID ");
			sbProcess.append("  INNER JOIN SSO_USER D ");
			sbProcess.append("     ON C.FK_SSO_USER_ID = D.ID ");
			sbProcess.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE E ");
			sbProcess.append("     ON B.FK_TCH_OPENCOURSE_ID = E.ID ");
			sbProcess.append("  INNER JOIN PE_BZZ_TCH_COURSE F ");
			sbProcess.append("     ON E.FK_COURSE_ID = F.ID ");
			sbProcess.append("   LEFT JOIN PE_BZZ_ORDER_INFO G ");
			sbProcess.append("     ON B.FK_ORDER_ID = G.ID ");
			sbProcess.append("   LEFT JOIN (SELECT TRUNC(SUM((WH.WH_LEAVETIME - WH.WH_JOINTIME) / 60 / 1000), ");
			sbProcess.append("                           2) TOTAL_TIME, ");
			sbProcess.append("                     PBS.ID PBSID, ");
			sbProcess.append("                     PBTC.ID PBTCID ");
			sbProcess.append("                FROM WE_HISTORY        WH, ");
			sbProcess.append("                     PE_BZZ_STUDENT    PBS, ");
			sbProcess.append("                     LIVE_SEQ_STU      LSS, ");
			sbProcess.append("                     PE_BZZ_TCH_COURSE PBTC ");
			sbProcess.append("               WHERE WH.WH_UID = LSS.ID ");
			sbProcess.append("                 AND LSS.LOGIN_ID = PBS.FK_SSO_USER_ID ");
			sbProcess.append("                 AND WH.WH_WEBCASTID = LSS.LIVE_ID ");
			sbProcess.append("                 AND WH.WH_WEBCASTID = PBTC.LIVEID ");
			sbProcess.append("               GROUP BY WH.WH_WEBCASTID, PBS.ID, PBTC.ID) ABC ");
			sbProcess.append("     ON C.ID = ABC.PBSID ");
			sbProcess.append("    AND F.ID = ABC.PBTCID ");
			sbProcess.append("  WHERE D.LOGIN_ID = '" + this.getPeBzzStudent().getSsoUser().getLoginId() + "' ");
			sbProcess.append("    AND F.ID = '" + this.getCourseId() + "' ");
			sbProcess.append("  ORDER BY A.GET_DATE ASC ");
			try {
				courseLearningProcess = this.getGeneralService().getBySQL(sbProcess.toString());
			} catch (EntityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ServletActionContext.getRequest().setAttribute("studyList", courseLearningProcess);
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT NVL(TRUNC(SUM((WH_LEAVETIME - WH_JOINTIME) / 60 / 1000), 2), 0) STUDYTIME, ");
			sb.append("        COUNT(*) STUDYTIMES ");
			sb.append("   FROM PE_BZZ_STUDENT          PBS, ");
			sb.append("        WE_HISTORY              WH, ");
			sb.append("        PR_BZZ_TCH_OPENCOURSE   PBTO, ");
			sb.append("        LIVE_SEQ_STU            LSS, ");
			sb.append("        PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sb.append("  WHERE PBS.FK_SSO_USER_ID = LSS.LOGIN_ID ");
			sb.append("    AND PBTSE.FK_STU_ID = PBS.ID ");
			sb.append("    AND PBS.ID = '" + this.getPeBzzStudent().getSsoUser().getId() + "' ");
			sb.append("    AND PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
			sb.append("    AND PBTO.FK_COURSE_ID = '" + this.getCourseId() + "' ");
			sb.append("    AND WH.WH_UID = LSS.ID ");
			try {
				List timeTimesList = this.getGeneralService().getBySQL(sb.toString());
				Object[] timeTimeArr = (Object[]) timeTimesList.get(0);
				// 累计学习时间
				ljcourseLearningTime = timeTimeArr[0].toString();
				// 累计学习次数
				ljcourseLearningTimes = timeTimeArr[1].toString();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 满意度调查
	 * 
	 * @author hdg
	 * @return
	 */
	public void satisfactionSurvey() {
		StringBuffer sqlBuff = new StringBuffer();
		// sqlBuff.append(" SELECT
		// COURSE_MEGRE.SEQ,NVL(to_char(PVR.VOTE_DATE,'yyyy-MM-dd
		// hh24:mi:ss'),'--'),PVR.ID FROM pr_vote_record PVR ");
		// sqlBuff.append(" JOIN PE_VOTE_PAPER pvp ON
		// PVR.FK_VOTE_PAPER_ID=pvp.id ");
		// sqlBuff.append(" JOIN SSO_USER su ON su.id=pvr.ssoid ");
		// sqlBuff.append(" JOIN PE_BZZ_STUDENT pbs ON pbs.fk_sso_user_id=su.id
		// ");
		// sqlBuff.append(" JOIN PR_BZZ_TCH_OPENCOURSE pbto ON
		// pbto.fk_course_id=pvp.courseid ");
		// sqlBuff.append(" JOIN ( ");
		// sqlBuff.append(" SELECT PBTC.ID,PBOI.SEQ,PBTO.ID
		// PBTO_ID,PBTSE.FK_STU_ID FROM PE_BZZ_TCH_COURSE PBTC ");
		// sqlBuff.append(" JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON
		// PBTO.FK_COURSE_ID=PBTC.ID ");
		// sqlBuff.append(" JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON
		// PBTSE.FK_TCH_OPENCOURSE_ID=PBTO.ID ");
		// sqlBuff.append(" LEFT JOIN PE_BZZ_ORDER_INFO PBOI ON
		// PBOI.ID=PBTSE.FK_ORDER_ID ");
		// sqlBuff.append(" GROUP BY PBTC.ID,PBOI.SEQ,PBTO.ID,PBTSE.FK_STU_ID
		// ");
		// sqlBuff.append(" )COURSE_MEGRE ON COURSE_MEGRE.ID=PVP.COURSEID AND
		// COURSE_MEGRE.PBTO_ID=PBTO.ID AND pbs.id=COURSE_MEGRE.fk_stu_id ");
		// sqlBuff.append(" WHERE pbs.id='" + this.getPeBzzStudent().getId() +
		// "' AND pbto.fk_course_id='" + this.getCourseId() + "' ");
		sqlBuff.append(" SELECT DISTINCT TO_CHAR(A.VOTE_DATE, 'yyyy-MM-dd') VOTE_DATE FROM PR_VOTE_RECORD A ");
		sqlBuff.append(" INNER JOIN PE_VOTE_PAPER B ON A.FK_VOTE_PAPER_ID = B.ID AND B.COURSEID = '" + this.getCourseId() + "' ");
		sqlBuff.append(" INNER JOIN SSO_USER SU ON A.SSOID = SU.ID ");
		sqlBuff.append(" INNER JOIN PE_BZZ_STUDENT PBS ON SU.ID = PBS.FK_SSO_USER_ID ");
		sqlBuff.append(" INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON B.COURSEID = PBTO.FK_COURSE_ID ");
		sqlBuff.append(" INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBS.ID = PBTSE.FK_STU_ID ");
		sqlBuff.append(" AND PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID AND PBS.FK_SSO_USER_ID = ");
		sqlBuff.append(" (SELECT FK_SSO_USER_ID FROM PE_BZZ_STUDENT WHERE ID = '" + this.getPeBzzStudent().getId() + "') ");
		try {
			satisfactionSurvey = this.getGeneralService().getBySQL(sqlBuff.toString());
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 课后测验
	 * 
	 * @author hdg
	 * @return
	 */

	public void classTest() {
		StringBuffer s1 = new StringBuffer();
		// s1.append(" SELECT DISTINCT H.SEQ, DECODE(TEST_OPEN_DATE, NULL, '-',
		// TEST_OPEN_DATE) OPEN_TIME, TO_CHAR(G.TEST_DATE, 'yyyy-MM-dd
		// hh24:mi:ss') SUBMIT_TIME, A.EXAMTIMES_ALLOW, ");
		// s1.append(" G.SCORE SCORE_EXAM, A.PASSROLE,
		// DECODE(D.ISPASS,1,'1','0'), G.SCORE - A.PASSROLE ");
		// s1.append(" FROM PE_BZZ_TCH_COURSE A INNER JOIN PR_BZZ_TCH_OPENCOURSE
		// B ON A.ID = B.FK_COURSE_ID AND A.ID = '" + this.getCourseId() + "'
		// ");
		// s1.append(" INNER JOIN TEST_TESTPAPER_INFO C ON A.ID = C.GROUP_ID ");
		// s1.append(" INNER JOIN PR_BZZ_TCH_STU_ELECTIVE D ON B.ID =
		// D.FK_TCH_OPENCOURSE_ID ");
		// s1.append(" INNER JOIN PE_BZZ_STUDENT E ON D.FK_STU_ID = E.ID AND
		// E.ID = '" + this.getPeBzzStudent().getId() + "' ");
		// s1.append(" INNER JOIN ENUM_CONST F ON A.FLAG_IS_EXAM = F.ID ");
		// s1.append(" INNER JOIN TEST_TESTPAPER_HISTORY G ON C.ID =
		// G.TESTPAPER_ID AND E.FK_SSO_USER_ID = G.T_USER_ID ");
		// s1.append(" LEFT JOIN PE_BZZ_ORDER_INFO H ON D.FK_ORDER_ID = H.ID
		// ORDER BY SUBMIT_TIME ASC ");
		s1.append(" SELECT * FROM ( ");
		s1.append(" SELECT DISTINCT DECODE(TEST_OPEN_DATE, NULL, '-', TEST_OPEN_DATE) OPEN_TIME, TO_CHAR(G.TEST_DATE, 'yyyy-MM-dd hh24:mi:ss') SUBMIT_TIME, A.EXAMTIMES_ALLOW,  ");
		s1.append(" G.SCORE SCORE_EXAM, A.PASSROLE, DECODE(D.ISPASS,1,'1','0'), G.SCORE - A.PASSROLE ");
		s1.append(" FROM PE_BZZ_TCH_COURSE A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.ID = B.FK_COURSE_ID AND A.ID = '" + this.getCourseId() + "' ");
		s1.append(" INNER JOIN TEST_TESTPAPER_INFO C ON A.ID = C.GROUP_ID ");
		s1.append(" INNER JOIN PR_BZZ_TCH_STU_ELECTIVE D ON B.ID = D.FK_TCH_OPENCOURSE_ID ");
		s1.append(" INNER JOIN PE_BZZ_STUDENT E ON D.FK_STU_ID = E.ID AND E.ID = '" + this.getPeBzzStudent().getId() + "' ");
		s1.append(" INNER JOIN ENUM_CONST F ON A.FLAG_IS_EXAM = F.ID ");
		s1.append(" INNER JOIN TEST_TESTPAPER_HISTORY G ON C.ID = G.TESTPAPER_ID AND E.ID = G.T_USER_ID ");
		s1.append(" UNION ");
		s1.append(" SELECT DISTINCT DECODE(TEST_OPEN_DATE, NULL, '-', TEST_OPEN_DATE) OPEN_TIME, TO_CHAR(G.TEST_DATE, 'yyyy-MM-dd hh24:mi:ss') SUBMIT_TIME, A.EXAMTIMES_ALLOW,  ");
		s1.append(" G.SCORE SCORE_EXAM, A.PASSROLE, DECODE(D.ISPASS,1,'1','0'), G.SCORE - A.PASSROLE ");
		s1.append(" FROM PE_BZZ_TCH_COURSE A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.ID = B.FK_COURSE_ID AND A.ID = '" + this.getCourseId() + "' ");
		s1.append(" INNER JOIN TEST_TESTPAPER_INFO C ON A.ID = C.GROUP_ID ");
		s1.append(" INNER JOIN ELECTIVE_HISTORY D ON B.ID = D.FK_TCH_OPENCOURSE_ID ");
		s1.append(" INNER JOIN PE_BZZ_STUDENT E ON D.FK_STU_ID = E.ID AND E.ID = '" + this.getPeBzzStudent().getId() + "' ");
		s1.append(" INNER JOIN ENUM_CONST F ON A.FLAG_IS_EXAM = F.ID ");
		s1.append(" INNER JOIN TEST_TESTPAPER_HISTORY_HIS G ON C.ID = G.TESTPAPER_ID AND E.ID = G.T_USER_ID ");
		s1.append(" ) ORDER BY SUBMIT_TIME DESC ");
		try {
			classTest = this.getGeneralService().getBySQL(s1.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ↓这个判断应该没有用了
		if (null == classTest || classTest.size() == 0) {
			StringBuffer sqlBuff = new StringBuffer();
			sqlBuff.append("  SELECT COURSE_MEGRE.SEQ,TTH.TEST_OPEN_DATE,TO_CHAR(TTH.TEST_DATE,'yyyy-MM-dd hh24:mi:ss'),COURSE_MEGRE.EXAMTIMES_ALLOW,TTH.SCORE,COURSE_MEGRE.PASSROLE  ");
			sqlBuff.append("  ,CASE WHEN to_number(TTH.SCORE) >=to_number(COURSE_MEGRE.PASSROLE) THEN '1' ELSE '0'END AS psresult, ");
			sqlBuff.append(" TTH.SCORE-COURSE_MEGRE.PASSROLE,flag_isfree FROM TEST_TESTPAPER_HISTORY TTH ");
			sqlBuff.append("  JOIN TEST_TESTPAPER_INFO TTI ON TTI.ID=TTH.TESTPAPER_ID ");
			sqlBuff.append("  JOIN PE_BZZ_STUDENT PBS ON PBS.ID=TTH.t_User_Id ");
			sqlBuff.append("  JOIN PR_BZZ_TCH_OPENCOURSE pbto ON pbto.fk_course_id=tti.group_id ");
			sqlBuff.append("  JOIN (  ");
			sqlBuff.append("  SELECT PBTC.ID,PBOI.SEQ,PBTO.ID PBTO_ID,PBTSE.FK_STU_ID,PBTC.Examtimes_Allow,PBTC.PASSROLE, pbtc.flag_isfree FROM PE_BZZ_TCH_COURSE PBTC  ");
			sqlBuff.append("  JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTO.FK_COURSE_ID=PBTC.ID  ");
			sqlBuff.append("  JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBTSE.FK_TCH_OPENCOURSE_ID=PBTO.ID  ");
			// 处理免费课程，没有订单的情况
			sqlBuff.append("  Left JOIN PE_BZZ_ORDER_INFO PBOI ON PBOI.ID=PBTSE.FK_ORDER_ID  ");
			sqlBuff.append("   WHERE  pbtc.flag_is_exam='40288acf3ae01103013ae012940b0001' ");
			sqlBuff.append("  GROUP BY PBTC.ID,PBOI.SEQ,PBTO.ID,PBTSE.FK_STU_ID,PBTC.Examtimes_Allow,PBTC.PASSROLE, pbtc.flag_isfree ");
			sqlBuff.append("  )COURSE_MEGRE ON COURSE_MEGRE.ID=tti.group_id  AND pbs.id=COURSE_MEGRE.fk_stu_id  AND COURSE_MEGRE.pbto_id=pbto.id ");
			sqlBuff.append(" WHERE TTI.GROUP_ID='" + this.getCourseId() + "' AND PBS.ID='" + this.getPeBzzStudent().getId() + "' order by tth.test_open_date asc");
			try {
				classTest = this.getGeneralService().getBySQL(sqlBuff.toString());
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 按页面第二个table获得列表信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void getSecondTable() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.add(Restrictions.eq("id", this.getCourseId()));
		try {
			peBzzTchCourse = (PeBzzTchCourse) this.getGeneralService().getList(dc).get(0);
			// String sql = "select substr(t.total_time,1,8) as total_time,
			// t.attempt_num,to_char( t.last_date, 'YYYY-MM-DD HH24:MI:SS') \n"
			String sql = "select t.id, to_char(t.first_date, 'YYYY-MM-DD HH24:MI:SS'),to_char(t.last_date, 'YYYY-MM-DD HH24:MI:SS'), t.attempt_num \n"
					+ "  from scorm_stu_course t\n" + " where t.course_id = '" + peBzzTchCourse.getCode() + "'\n" + "   and t.student_id = '"
					+ this.getPeBzzStudent().getSsoUser().getId() + "'";
			secondList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * 按页面第三个table获得列表信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void getLastTable() {

		String sql = "";

		sql =

		"select max(pbtc.examtimes_allow), max(to_number(tth.score)), max(tth.test_date)\n" + "  from pr_bzz_tch_stu_elective ele\n" + " inner join pr_bzz_tch_opencourse pbto\n"
				+ "    on pbto.id = ele.fk_tch_opencourse_id\n" + " inner join pe_bzz_tch_course pbtc\n" + "    on pbtc.id = pbto.fk_course_id\n"
				+ " inner join pe_bzz_student pbs\n" + "    on pbs.id = ele.fk_stu_id\n" + "  inner join test_testpaper_history tth\n" + "   on tth.t_user_id = pbs.id\n"
				+ "  left join test_testpaper_info tti\n" + "    on tti.id = tth.testpaper_id\n" + "   and tti.group_id = '" + this.peBzzTchCourse.getId() + "'\n"
				+ " where ele.fk_stu_id = '" + this.getPeBzzStudent().getId() + "'\n" + "   and tti.group_id = '" + this.peBzzTchCourse.getId() + "'\n" + "   and pbto.id = '"
				+ this.prBzzTchOpencourse.getId() + "'\n" + "   group by pbtc.examtimes_allow";

		try {
			lastList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否完成
	 * 
	 * @author linjie
	 * @return
	 */
	public boolean isCompleted(String flag) {
		if (flag.equals(StudyProgress.INCOMPLETE)) {
			return true;
		}
		return false;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSumCourse() {
		return sumCourse;
	}

	public void setSumCourse(String sumCourse) {
		this.sumCourse = sumCourse;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(String examStatus) {
		this.examStatus = examStatus;
	}

	public String getPaymentDateStart() {
		return paymentDateStart;
	}

	public void setPaymentDateStart(String paymentDateStart) {
		this.paymentDateStart = paymentDateStart;
	}

	public String getPaymentDateEnd() {
		return paymentDateEnd;
	}

	public void setPaymentDateEnd(String paymentDateEnd) {
		this.paymentDateEnd = paymentDateEnd;
	}

	public String getStudyDateStart() {
		return studyDateStart;
	}

	public void setStudyDateStart(String studyDateStart) {
		this.studyDateStart = studyDateStart;
	}

	public String getStudyDateEnd() {
		return studyDateEnd;
	}

	public void setStudyDateEnd(String studyDateEnd) {
		this.studyDateEnd = studyDateEnd;
	}

	public String getOpencourseId() {
		return opencourseId;
	}

	public void setOpencourseId(String opencourseId) {
		this.opencourseId = opencourseId;
	}

	public PrBzzTchOpencourse getPrBzzTchOpencourse() {
		return prBzzTchOpencourse;
	}

	public void setPrBzzTchOpencourse(PrBzzTchOpencourse prBzzTchOpencourse) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getCompletedhour() {
		return completedhour;
	}

	public void setCompletedhour(String completedhour) {
		this.completedhour = completedhour;
	}

	public String getAllid() {
		return allid;
	}

	public void setAllid(String allid) {
		this.allid = allid;
	}

	public String getCourseRegion() {
		return courseRegion;
	}

	public void setCourseRegion(String courseRegion) {
		this.courseRegion = courseRegion;
	}

	public String getLjcourseLearningTime() {
		return ljcourseLearningTime;
	}

	public void setLjcourseLearningTime(String ljcourseLearningTime) {
		this.ljcourseLearningTime = ljcourseLearningTime;
	}

	public String getLjcourseLearningTimes() {
		return ljcourseLearningTimes;
	}

	public void setLjcourseLearningTimes(String ljcourseLearningTimes) {
		this.ljcourseLearningTimes = ljcourseLearningTimes;
	}

	public String getCountbx() {
		return countbx;
	}

	public void setCountbx(String countbx) {
		this.countbx = countbx;
	}

	public String getSumtiembx() {
		return sumtiembx;
	}

	public void setSumtiembx(String sumtiembx) {
		this.sumtiembx = sumtiembx;
	}

	public String getCountxx() {
		return countxx;
	}

	public void setCountxx(String countxx) {
		this.countxx = countxx;
	}

	public String getSumtiemxx() {
		return sumtiemxx;
	}

	public void setSumtiemxx(String sumtiemxx) {
		this.sumtiemxx = sumtiemxx;
	}

	public List getCoursesSituations() {
		return coursesSituations;
	}

	public void setCoursesSituations(List coursesSituations) {
		this.coursesSituations = coursesSituations;
	}

	public Double CalcTime(String now, String step) {
		if (null == now || "".equals(now))
			now = "0";
		if (null == step || "".equals(step))
			step = "0";
		Double nowInt = Double.parseDouble(now);
		Double stepInt = Double.parseDouble(step);
		return nowInt + stepInt;
	}
}
