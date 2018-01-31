/*
 * OracleStudent.java
 *
 * Created on 2005年4月7日, 下午10:00
 */

package com.whaty.platform.database.oracle.standard.entity.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.SubSystemType;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSemester;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUser;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.entity.user.EntityUserType;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.util.Address;
import com.whaty.platform.util.RedundanceData;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

/**
 * 
 * @author Administrator
 */
public class OracleStudent extends Student implements Serializable {

	private String condition = "";

	/*
	 * private String SQLSCORE = "select a.id as
	 * active_id,course_id,course_name,course_credit,semester_name,usual_score,exam_score,expend_score,total_score
	 * from "+ "(select i.id as course_id,i.course_name as course_name,i.credit
	 * as course_credit,s.name as semester_name,e.usual_score as
	 * usual_score,e.exam_score as exam_score,e.expend_score as
	 * expend_score,e.score as total_score "+ " from entity_course_info
	 * i,entity_course_active a,entity_semester_info s,entity_user_info
	 * u,entity_elective e "+ "where i.id=a.course_id and a.semester_id=s.id and
	 * a.id=e.open_course_id and e.student_id=u.id and e.status='1' and
	 * u.reg_no='" + this.getStudentInfo().getReg_no() + "' "+condition+")";
	 */

	private String SQLSCORE_ORDERBY = " order by s.id";

	private String SQLELCOURSE = "select g.c_id,g.course_name,g.active_id,f.semester_id,f.semester_name from "
			+ " (select a.user_id as student_id,b.id as semester_id,b.name as semester_name from entity_register_info a,entity_semester_info b where a.user_id = '"
			+ this.getId()
			+ "' and b.selected='1' and a.semester_id = b.id) f,"
			+ "(select c.id as c_id,c.course_name as course_name,e.student_id as student_id,d.semester_id as semester_id,d.id as active_id from entity_course_info c,entity_course_active d,lrn_elective e where e.open_course_id = d.id and d.course_id = c.id and e.student_id = '"
			+ this.getId()
			+ "' and e.status = '1') g "
			+ " where f.semester_id = g.semester_id";

	private String SQLELCOURSE_ORDERBY = " order by f.semester_id";

	/** Creates a new instance of OracleStudent */
	public OracleStudent() {
		super();
	}

	public OracleStudent(java.lang.String aid) {
		super();
		this.setType(EntityUserType.STUDENT);
		dbpool dbStu = new dbpool();
		String sql = null;
		MyResultSet rs = null;
		try {
//			sql  = "select id,reg_no,password,name,gender,site_id,site_name,major_id,major_name,edutype_id,edutype_name,grade_id,grade_name from (select prst.id,u.password,pest.reg_no,pest.name,prst.gender,si.id as site_id,si.name as site_name,ma.id as major_id ,ma.name as major_name,edu.id as edutype_id,edu.name as edutype_name,gr.id as grade_id ,gr.name as grade_name from pr_student_info prst,pe_student pest,sso_user u,pe_site si,pe_major ma,pe_edutype edu,pe_grade gr where u.id=pest.id and prst.id=pest.id and pest.fk_site_id = si.id and pest.fk_major_id=ma.id and pest.fk_edutype_id=edu.id and pest.fk_grade_id = gr.id) where id='"+aid+"'";
//			
//			EntityLog.setDebug("OracleStudent: " + sql);
//			rs = dbStu.executeQuery(sql);
//			if (rs != null && rs.next()) {
//				HumanNormalInfo normalInfo = new HumanNormalInfo();
//				StudentEduInfo eduInfo = new StudentEduInfo();
//				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
//				RedundanceData redundance = new RedundanceData();
//				this.setId(aid);
//				this.setName(rs.getString("name"));
//				this.setLoginId(rs.getString("reg_no"));
//				this.setPassword(rs.getString("password"));
//				normalInfo.setTxt_Reg_No(rs.getString("reg_no")); // 学生学号
//				normalInfo.setGraduated_sch(rs.getString("site_name"));// 学校名称
//				normalInfo.setGraduated_Sch_Id(rs.getString("site_id")); // 学校ID
//				normalInfo.setGender(rs.getString("gender"));
//				this.setNormalInfo(normalInfo);
//				eduInfo.setEdutype_id(rs.getString("edutype_id"));
//				eduInfo.setEdutype_name(rs.getString("edutype_name"));
//				eduInfo.setGrade_id(rs.getString("grade_id"));
//				eduInfo.setGrade_name(rs.getString("grade_name"));
//				eduInfo.setMajor_id(rs.getString("major_id"));
//				eduInfo.setMajor_name(rs.getString("major_name"));
//				eduInfo.setReg_no(rs.getString("reg_no"));
//				eduInfo.setSite_id(rs.getString("site_id"));
//				eduInfo.setSite_name(rs.getString("site_name"));
//				this.setStudentInfo(eduInfo);
//				this.setPlatformInfo(platformInfo);
//				this.setRedundace(redundance);
//			sql = "select pest.id,pest.true_name,pest.reg_no, sso.login_id from pe_student pest, sso_user sso where pest.fk_sso_user_id = sso.id and sso.id = '" + aid + "'";
			//sql = "select pest.id,pest.true_name,pest.reg_no from pe_student pest where pest.id = '" + aid + "'";
			sql = "select pest.id,pest.true_name,pest.reg_no from pe_bzz_student pest,sso_user sso where sso.id = '" + aid + "' and pest.fk_sso_user_id=sso.id";
			EntityLog.setDebug("OracleStudent: " + sql);
			rs = dbStu.executeQuery(sql);
			if (rs != null && rs.next()) {
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				this.setId(rs.getString("id"));
				this.setName(rs.getString("true_name"));
//				this.setLoginId(rs.getString("login_id"));
				normalInfo.setTxt_Reg_No(rs.getString("reg_no")); // 学生学号
				this.setNormalInfo(normalInfo);
				eduInfo.setReg_no(rs.getString("reg_no"));
				this.setStudentInfo(eduInfo);
				this.setPlatformInfo(platformInfo);
				this.setRedundace(redundance);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleStudent(String aid) error" + sql);
		} finally {
			dbStu.close(rs);
			dbStu = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		if (OracleSsoUser.isExist(this.getStudentInfo().getReg_no()) > 0) {
			throw new PlatformException("该统一登陆系统用户已经存在！");
		}
		if (isRegNoExist() > 0) {
			throw new PlatformException("该学号已经存在！");
		}
		if (isIdCardNoExist() > 0) {
			throw new PlatformException("该身份证号码已经存在！");
		}
		ArrayList sqlList = new ArrayList();
		String ssoSql = "insert into sso_user (id,login_id,password,name,email,ADD_SUBSYSTEM) values (to_char(s_sso_id.nextval),'"
				+ this.getStudentInfo().getReg_no()
				+ "','"
				+ this.getNormalInfo().getIdcard()
				+ "','"
				+ this.getName()
				+ "','" + this.getEmail() + "','" + SubSystemType.ENTITY + "')";

		String feeSql = "insert into entity_userfee_level(user_id,type1_name,type1_value) select to_char(s_sso_id.currval),'PERCREDIT',type1_value from entity_fee_level where site_id='"
				+ this.getStudentInfo().getSite_id()
				+ "' and grade_id='"
				+ this.getStudentInfo().getGrade_id()
				+ "' and edutype_id='"
				+ this.getStudentInfo().getEdutype_id()
				+ "' and major_id='"
				+ this.getStudentInfo().getMajor_id() + "'";

		String eduSql = "insert into entity_student_info (id,name,SCHOOL_NAME,SCHOOL_CODE,GRADUATE_YEAR,GRADUATE_CARDNO,id_card,card_type,reg_no,class_id,edutype_id,grade_id,site_id,major_id,study_form,study_status,status,entrance_date,photo_link) values "
				+ "(to_char(s_sso_id.currval),'"
				+ this.getName()
				+ "','"
				+ this.getStudentInfo().getSchool_name()
				+ "','"
				+ this.getStudentInfo().getSchool_code()
				+ "','"
				+ this.getStudentInfo().getGraduate_year()
				+ "','"
				+ this.getStudentInfo().getGraduate_cardno()
				+ "','"
				+ this.getNormalInfo().getIdcard()
				+ "','"
				+ this.getNormalInfo().getCardType()
				+ "','"
				+ this.getStudentInfo().getReg_no()
				+ "','"
				+ this.getStudentInfo().getClass_id()
				+ "','"
				+ this.getStudentInfo().getEdutype_id()
				+ "',"
				+ "'"
				+ this.getStudentInfo().getGrade_id()
				+ "','"
				+ this.getStudentInfo().getSite_id()
				+ "','"
				+ this.getStudentInfo().getMajor_id()
				+ "','"
				+ this.getStudentInfo().getStudy_form()
				+ "',"
				+ "'"
				+ this.getStudentInfo().getStudy_status()
				+ "','"
				+ this.getStudentInfo().getStatus()
				+ "','"
				+ this.getStudentInfo().getEntrance_date()
				+ "','"
				+ this.getStudentInfo().getPhoto_link() + "')";

		String normalSql = "insert into entity_usernormal_info (id,name,birthday,gender,idcard,card_type,edu,folk,hometown,workplace,position,title,zzmm,graduated_major,graduated_sch,graduated_time,email,phone,mobilephone,"
				+ "fax,work_address,work_postalcode,home_address,home_postalcode,degree,note) values (to_char(s_sso_id.currval),'"
				+ this.getName()
				+ "','"
				+ this.getNormalInfo().getBirthday()
				+ "','"
				+ this.getNormalInfo().getGender()
				+ "','"
				+ this.getNormalInfo().getIdcard()
				+ "','"
				+ this.getNormalInfo().getCardType()
				+ "','"
				+ this.getNormalInfo().getEdu()
				+ "','"
				+ this.getNormalInfo().getFolk()
				+ "','"
				+ this.getNormalInfo().getHometown()
				+ "','"
				+ this.getNormalInfo().getWorkplace()
				+ "','"
				+ this.getNormalInfo().getPosition()
				+ "','"
				+ this.getNormalInfo().getTitle()
				+ "','"
				+ this.getNormalInfo().getZzmm()
				+ "','"
				+ this.getNormalInfo().getGraduated_major()
				+ "','"
				+ this.getNormalInfo().getGraduated_sch()
				+ "','"
				+ this.getNormalInfo().getGraduated_time()
				+ "','"
				+ this.getNormalInfo().getEmails()
				+ "','"
				+ this.getNormalInfo().getPhones()
				+ "','"
				+ this.getNormalInfo().getMobilePhones()
				+ "','"
				+ this.getNormalInfo().getFaxs()
				+ "','"
				+ this.getNormalInfo().getWork_address().getAddress()
				+ "','"
				+ this.getNormalInfo().getWork_address().getPostalcode()
				+ "','"
				+ this.getNormalInfo().getHome_address().getAddress()
				+ "','"
				+ this.getNormalInfo().getHome_address().getPostalcode()
				+ "','"
				+ this.getNormalInfo().getDegree()
				+ "','"
				+ this.getNormalInfo().getNote() + "')";

		sqlList.add(ssoSql);
		sqlList.add(feeSql);
		sqlList.add(eduSql);
		sqlList.add(normalSql);
		int i = db.executeUpdateBatch(sqlList);
		UserAddLog.setDebug("OracleStudent.add() SQL1=" + ssoSql + " SQL2="
				+ feeSql + " SQL3=" + eduSql + " SQL4=" + normalSql + " COUNT="
				+ i + " DATE=" + new Date());
		db = null;
		return i;
	}

	public void addElectiveCourses(List opencourses) {
	}

	public int delete() {
		dbpool db = new dbpool();
		String regSql = "delete from entity_register_info where user_id = '"
				+ this.getId() + "'";
		String electiveSql = "delete from entity_elective where student_id = '"
				+ this.getId() + "'";
		String ssoSql = "delete from sso_user where id = '" + this.getId()
				+ "'";
		String eduSql = "delete from entity_student_info where id = '"
				+ this.getId() + "'";
		String normalSql = "delete from entity_usernormal_info where id = '"
				+ this.getId() + "'";
		ArrayList sqlList = new ArrayList();
		sqlList.add(regSql);
		sqlList.add(electiveSql);
		sqlList.add(ssoSql);
		sqlList.add(eduSql);
		sqlList.add(normalSql);
		int i = db.executeUpdateBatch(sqlList);
		UserDeleteLog.setDebug("OracleStudent.delete() SQL1=" + regSql
				+ " SQL2=" + electiveSql + " SQL3=" + ssoSql + " SQL4" + eduSql
				+ " SQL5=" + normalSql + " COUNT=" + i + " DATE=" + new Date());
		db = null;
		return i;
	}

	/** 获得该学生所有所选课程列表 */
	public List getElectiveCourses() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList electivecourses = new ArrayList();
		String sql = "";
		sql = SQLELCOURSE + condition + SQLELCOURSE_ORDERBY;
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleOpenCourse oracleopencourse = new OracleOpenCourse();
				OracleCourse oraclecourse = new OracleCourse();
				OracleSemester oraclesemester = new OracleSemester();
				oraclecourse.setId(rs.getString("c_id"));
				oraclecourse.setName(rs.getString("course_name"));
				oraclesemester.setId(rs.getString("semester_id"));
				oraclesemester.setName(rs.getString("semester_name"));
				oracleopencourse.setId(rs.getString("active_id"));
//				oracleopencourse.setCourse(oraclecourse);
//				oracleopencourse.setSemester(oraclesemester);
				electivecourses.add(oracleopencourse);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("Student.getElectiveCourses() error! " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return electivecourses;
	}

	/** 获得该学生某一学期所选课程列表 */
	public List getElectiveCourses(Semester semester) {
		condition = " g.semester_id = '" + semester.getId() + "'";
		return getElectiveCourses();
	}

	/** 获得该学生所选课程的成绩列表 */
	/*
	 * public List getElectiveScores() { ArrayList electivescores=new
	 * ArrayList(); dbpool db = new dbpool(); MyResultSet rs = null; String sql =
	 * ""; sql = SQLSCORE + SQLSCORE_ORDERBY; try { rs = db.executeQuery(sql);
	 * while(rs!=null && rs.next()) { java.util.HashMap usual_scoremap = new
	 * java.util.HashMap(); java.util.HashMap exam_scoremap = new
	 * java.util.HashMap(); java.util.HashMap expend_scoremap = new
	 * java.util.HashMap(); java.util.HashMap total_scoremap = new
	 * java.util.HashMap();
	 * usual_scoremap.put(rs.getString("active_id"),rs.getString("usual_score"));
	 * exam_scoremap.put(rs.getString("active_id"),rs.getString("exam_score"));
	 * total_scoremap.put(rs.getString("active_id"),rs.getString("total_score"));
	 * expend_scoremap.put(rs.getString("active_id"),rs.getString("expend_score"));
	 * OracleScore oraclescore = new OracleScore();
	 * oraclescore.setUsual_score(usual_scoremap);
	 * oraclescore.setExam_score(exam_scoremap);
	 * oraclescore.setTotal_score(total_scoremap);
	 * oraclescore.setExpend_score(expend_scoremap);
	 * electivescores.add(oraclescore); } } catch (java.sql.SQLException e) {
	 * Log.setError("Student.getElectiveScores() " + sql); } finally {
	 * db.close(rs); db = null; } return electivescores; }
	 */

	/** 获得该学生某一学期所选课程成绩列表 */
	/*
	 * public List getElectiveScores(Semester semester) { condition = " s.id =
	 * '"+semester.getId()+"' "; return getElectiveScores(); }
	 */

	/** 学生退选课程 */
	public void removeElectiveCourses(List opencourses) {

	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		Student tempStudent = new OracleStudent(this.getId());
		if (!tempStudent.getStudentInfo().getReg_no().equals(
				this.getStudentInfo().getReg_no())) {
			if (OracleSsoUser.isExist(this.getStudentInfo().getReg_no()) > 0) {
				throw new PlatformException("该统一登陆系统用户已经存在！");
			}
			if (isRegNoExist() > 1) {
				throw new PlatformException("该学号已经存在！");
			}
		}
		if (!tempStudent.getNormalInfo().getIdcard().equals(
				this.getNormalInfo().getIdcard())) {
			if (isEditIdCardNoExist() > 0) {
				throw new PlatformException("该身份证号码已经存在！");
			}
		}
		ArrayList sqlList = new ArrayList();
		String ssoSql = "update sso_user set login_id='"
				+ this.getStudentInfo().getReg_no() + "',password='"
				+ this.getPassword() + "',name='" + this.getName()
				+ "',email='" + this.getEmail() + "' where id = '"
				+ this.getId() + "'";
		String eduSql = "update entity_student_info set id_card='"
				+ this.getNormalInfo().getIdcard() + "',card_type='"
				+ this.getNormalInfo().getCardType() + "',name='"
				+ this.getName() + "',reg_no='"
				+ this.getStudentInfo().getReg_no() + "'," + "study_form='"
				+ this.getStudentInfo().getStudy_form() + "',study_status='"
				+ this.getStudentInfo().getStudy_status() + "',status='"
				+ this.getStudentInfo().getStatus() + "',entrance_date='"
				+ this.getStudentInfo().getEntrance_date() + "',SCHOOL_NAME='"
				+ this.getStudentInfo().getSchool_name() + "',SCHOOL_CODE='"
				+ this.getStudentInfo().getSchool_code() + "',GRADUATE_YEAR='"
				+ this.getStudentInfo().getGraduate_year()
				+ "',GRADUATE_CARDNO='"
				+ this.getStudentInfo().getGraduate_cardno() + "',"
				+ "photo_link='" + this.getStudentInfo().getPhoto_link()
				+ "' where id = '" + this.getId() + "'";
		String normalSql = "update entity_usernormal_info set birthday='"
				+ this.getNormalInfo().getBirthday() + "',name='"
				+ this.getName() + "',gender='"
				+ this.getNormalInfo().getGender() + "',idcard='"
				+ this.getNormalInfo().getIdcard() + "',card_type='"
				+ this.getNormalInfo().getCardType() + "',edu='"
				+ this.getNormalInfo().getEdu() + "'," + "folk='"
				+ this.getNormalInfo().getFolk() + "',hometown='"
				+ this.getNormalInfo().getHometown() + "',workplace='"
				+ this.getNormalInfo().getWorkplace() + "',position='"
				+ this.getNormalInfo().getPosition() + "',title='"
				+ this.getNormalInfo().getTitle() + "'," + "zzmm='"
				+ this.getNormalInfo().getZzmm() + "',graduated_major='"
				+ this.getNormalInfo().getGraduated_major()
				+ "',graduated_sch='" + this.getNormalInfo().getGraduated_sch()
				+ "',graduated_time='"
				+ this.getNormalInfo().getGraduated_time() + "'," + "email='"
				+ this.getNormalInfo().getEmails() + "',phone='"
				+ this.getNormalInfo().getPhones() + "',mobilephone='"
				+ this.getNormalInfo().getMobilePhones() + "',fax='"
				+ this.getNormalInfo().getFaxs() + "',";
		if (this.getNormalInfo().getWork_address() != null)
			normalSql = normalSql + "work_address='"
					+ this.getNormalInfo().getWork_address().getAddress()
					+ "',work_postalcode='"
					+ this.getNormalInfo().getWork_address().getPostalcode();
		else
			normalSql = normalSql + "work_address='',work_postalcode='";
		if (this.getNormalInfo().getHome_address() != null)
			normalSql = normalSql + "',home_address='"
					+ this.getNormalInfo().getHome_address().getAddress()
					+ "'," + "home_postalcode='"
					+ this.getNormalInfo().getHome_address().getPostalcode()
					+ "',degree='" + this.getNormalInfo().getDegree()
					+ "',note='" + this.getNormalInfo().getNote()
					+ "' where id = '" + this.getId() + "'";
		else
			normalSql = normalSql
					+ "',home_address='',home_postalcode='',degree='"
					+ this.getNormalInfo().getDegree() + "',note='"
					+ this.getNormalInfo().getNote() + "' where id = '"
					+ this.getId() + "'";
		sqlList.add(ssoSql);
		sqlList.add(eduSql);
		sqlList.add(normalSql);
		int i = db.executeUpdateBatch(sqlList);
		UserUpdateLog.setDebug("OracleStudent.update() SQL1=" + ssoSql
				+ " SQL2=" + eduSql + " SQL3=" + normalSql + " COUNT=" + i
				+ " DATE=" + new Date());
		db = null;
		return i;
	}

	/** 判断学号是否存在，返回0则不存在，大于0则存在 */
	private int isRegNoExist() {
		dbpool db = new dbpool();
		String sql = "select id from entity_student_info where reg_no='"
				+ this.getStudentInfo().getReg_no() + "'";
		int i = db.countselect(sql);
		db = null;
		return i;
	}

	/** 判断身份证号码是否存在，返回0则不存在，大于0则存在 */
	private int isIdCardNoExist() {
		dbpool db = new dbpool();
		String sql = "select id from entity_student_info where id_card='"
				+ this.getNormalInfo().getIdcard().toUpperCase() + "' or id_card='"+this.getNormalInfo().getIdcard().toLowerCase()+"'";
		int i = db.countselect(sql);
		db = null;
		return i;
	}
	
	/** 判断身份证号码是否存在，返回0则不存在，大于0则存在 */
	private int isEditIdCardNoExist() {
		dbpool db = new dbpool();
		String sql = "select id from entity_student_info where id != '"+this.getId()+"' and id_card='"
				+ this.getNormalInfo().getIdcard().toUpperCase() + "' or id_card='"+this.getNormalInfo().getIdcard().toLowerCase()+"'";
		int i = db.countselect(sql);
		db = null;
		return i;
	}

	public String getId(String reg_no) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		String id = "";
		sql = "select id from lrn_user_info where reg_no = '" + reg_no + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				id = rs.getString("id");
			}
		} catch (java.sql.SQLException e) {
			Log.setError("Student.getId(String reg_no) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return id;
	}

	public List getRegSemester() {
		ArrayList semesterlist = new ArrayList();
		return semesterlist;
	}

	public Student getSimpleInfo(String student_id) {
		OracleStudent student = new OracleStudent();
		dbpool dbStu = new dbpool();
		String sql = null;
		MyResultSet rs = null;
		try {
			sql = "select * from lrn_user_info where id = '" + student_id + "'";
			rs = dbStu.executeQuery(sql);
			if (rs != null && rs.next()) {
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				student.setId(student_id);
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				student.setNickname(rs.getString("nickname"));
				student.setPassword(rs.getString("password"));
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setDegree(rs.getString("degree"));
				normalInfo.setEdu(rs.getString("edu"));
				normalInfo.setEmail(rs.getString("").split(","));
				normalInfo.setFax(rs.getString("fax").split(","));
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setGraduated_major(rs.getString("graduate_major"));
				normalInfo.setGraduated_sch(rs.getString("graduate_sch"));
				normalInfo.setGraduated_time(rs.getString("graduate_time"));
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("home_address"));
				homeaddress.setPostalcode(rs.getString("home_postalcode"));
				Address workaddress = new Address();
				workaddress.setAddress(rs.getString("work_address"));
				workaddress.setPostalcode(rs.getString("work_postalcode"));
				normalInfo.setHome_address(homeaddress);
				normalInfo.setHometown(rs.getString("hometown"));
				normalInfo.setIdcard(rs.getString("idcard"));
				normalInfo.setMobilePhone(rs.getString("mobilephone")
						.split(","));
				normalInfo.setNote(rs.getString("note"));
				normalInfo.setPhone(rs.getString("phone").split(","));
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setWork_address(workaddress);
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				student.setNormalInfo(normalInfo);
				eduInfo.setClass_id(rs.getString("class_id"));
				eduInfo.setClass_name(rs.getString("class_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setEntrance_date(rs.getString("entrance_date"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setStudy_form(rs.getString("study_form"));
				eduInfo.setStudy_status(rs.getString("study_status"));
				student.setStudentInfo(eduInfo);
				platformInfo.setLastlogin_ip(rs.getString("lastlogin_ip"));
				platformInfo.setLastlogin_time(rs.getString("lastlogin_time"));
				student.setPlatformInfo(platformInfo);
				student.setRedundace(redundance);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleStudent(String aid) error" + sql);
		} finally {
			dbStu.close(rs);
			dbStu = null;
		}
		return student;
	}

	/**
	 * @param reg_no
	 * @return
	 */
	/**
	 * @param reg_no
	 * @return
	 */
	public String getNextRegNo(String reg_no) {
		dbpool db = new dbpool();
		String sql = null;
		MyResultSet rs = null;
		String seriesNo = "0000";
		String reg_No = "";
		int seriesNoI = 0;
		try {
			sql = "select nvl(max(reg_no),'0000') as reg_no from entity_student_info where reg_no like '"
					+ reg_no + "____' ";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				reg_No = rs.getString("reg_no");
				seriesNo = reg_No.substring(reg_No.length() - 4, reg_No
						.length());
				seriesNoI = Integer.parseInt(seriesNo) + 1;
				seriesNo = Integer.toString(seriesNoI);
				for (int i = seriesNo.length(); i < 4; i++)
					seriesNo = "0" + seriesNo;
				reg_No = reg_no + seriesNo;
			} else {
				reg_No = reg_no + seriesNo;
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("getNextRegNo(String reg_no) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return reg_No;
	}

	/** 上传学生照片 */
	public int uploadImage(String card_no, String filename)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_student_info set photo_link='" + filename
				+ "' where id_card='" + card_no + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleStudent.updateImage() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	public int updateNormalInfo() throws PlatformException {
		dbpool db = new dbpool();
		String normalSql = "update entity_usernormal_info set workplace='"
				+ this.getNormalInfo().getWorkplace() + "',email='"
				+ this.getNormalInfo().getEmails() + "',phone='"
				+ this.getNormalInfo().getPhones() + "',mobilephone='"
				+ this.getNormalInfo().getMobilePhones() + "',";
		if (this.getNormalInfo().getWork_address() != null)
			normalSql = normalSql + "work_address='"
					+ this.getNormalInfo().getWork_address().getAddress()
					+ "',work_postalcode='"
					+ this.getNormalInfo().getWork_address().getPostalcode();
		else
			normalSql = normalSql + "work_address='',work_postalcode='";
		if (this.getNormalInfo().getHome_address() != null)
			normalSql = normalSql + "',home_address='"
					+ this.getNormalInfo().getHome_address().getAddress()
					+ "'," + "home_postalcode='"
					+ this.getNormalInfo().getHome_address().getPostalcode()
					+ "' where id = '" + this.getId() + "'";
		else
			normalSql = normalSql
					+ "',home_address='',home_postalcode='' where id = '"
					+ this.getId() + "'";
		int i = db.executeUpdate(normalSql);
		UserUpdateLog.setDebug("OracleStudent.updateNormalInfo() SQL1=" + normalSql
				+ " COUNT=" + i
				+ " DATE=" + new Date());
		db = null;
		return i;
	}

}
