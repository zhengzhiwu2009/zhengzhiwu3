/*
 * OracleTeachClass.java
 *
 * Created on 2005年5月17日, 下午9:24
 */

package com.whaty.platform.database.oracle.standard.entity.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.courseware.basic.OracleCourseware;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSemester;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleTeachBook;
import com.whaty.platform.database.oracle.standard.entity.user.OracleBasicUserList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacher;
import com.whaty.platform.database.oracle.standard.interaction.announce.OracleAnnounce;
import com.whaty.platform.entity.activity.TeachClass;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.util.Address;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

/**
 * 
 * @author Administrator
 */
public class OracleTeachClass extends TeachClass {

	/** Creates a new instance of OracleTeachClass */
	public OracleTeachClass() {
	}

	/**
	 * @param id
	 */
	public OracleTeachClass(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select a.id,a.name,a.open_course_id,b.course_id as course_id,c.name as course_name,c.credit,c.course_time,"
					+ "b.semester_id as semester_id,d.name as semester_name,a.assistance_note,a.assistance_release_status,a.assistance_title,c.note from "
					+ " (select id,name,open_course_id,assistance_note,assistance_release_status,assistance_title from entity_teach_class where id='"
					+ id
					+ "') a,"
					+ "entity_course_active b,entity_course_info c,entity_semester_info d where a.open_course_id=b.id "
					+ "and b.course_id=c.id and b.semester_id=d.id";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				OracleOpenCourse openCourse = new OracleOpenCourse();
				openCourse.setId(rs.getString("open_course_id"));
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setNote(rs.getString("note"));
				course.setCourse_time(rs.getString("course_time"));
				course.setCredit(rs.getString("credit"));
//				openCourse.setCourse(course);
				OracleSemester semester = new OracleSemester();
				semester.setId(rs.getString("semester_id"));
				semester.setName(rs.getString("semester_name"));
//				openCourse.setSemester(semester);
				this.setOpenCourse(openCourse);
				this.setAssistanceNote(rs.getString("assistance_note"));
				this.setAssistanceReleaseStatus(rs
						.getString("assistance_release_status"));
				this.setAssistanceTitle(rs.getString("assistance_title"));
			}
		} catch (java.sql.SQLException e) {

		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_teach_class (id,name,open_course_id) values (s__teach_class_id.nextval,'"
				+ this.getName() + "','" + this.getOpenCourse().getId() + "')";
		
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTeachClass.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public void addStudents(List students) throws PlatformException {
		Map stuTeachClassMap = new HashMap();
		for (int i = 0; i < students.size(); i++) {
			stuTeachClassMap.put((Student) students.get(i), this);
		}
		OracleElectiveActivity electiveActivity = new OracleElectiveActivity();
		electiveActivity.setStudentTeachClassMap(stuTeachClassMap);
		electiveActivity.selectTeachClass();
	}

	public void addTeachers(List teachers) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_teacher_course(id,teacher_id,open_course_id) select to_char(s_teacher_course_id.nextval),id,'"
				+ this.getId() + "' from entity_teacher_info where id in (";
		for (int i = 0; i < teachers.size(); i++) {
			OracleTeacher teacher = (OracleTeacher) teachers.get(i);
			if (i < teachers.size() - 1) {
				sql = sql + "'" + teacher.getId() + "',";
			} else {
				sql = sql + "'" + teacher.getId() + "')";
			}
		}
		// System.out.println();
		// System.out.println("add=" + sql);
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTeachClass.addTeachers(List teachers) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());

	}

	public int delete() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_teach_class where id = '" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTeachClass.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int getStudentsNum() {
		dbpool db = new dbpool();
		/*
		 * String sql = "select a.student_id from entity_elective a,
		 * entity_teach_class b where a.teachclass_id = b.id " + " and b.id = '" +
		 * this.getId() + "'";
		 */
		String sql = "select u.* from entity_student_info u,entity_usernormal_info eu, entity_teach_class a, entity_elective b"
				+ " where u.id=eu.id and u.isgraduated = '0' and u.status = '0' and u.id = b.student_id and b.teachclass_id = a.id and a.id = '"
				+ this.getId() + "'  ";
		sql = "select u.id as id,u.name as name,u.grade_id as grade_id,u.major_id as major_id,u.edutype_id as edutype_id,"
				+ " u.site_id as site_id,u.id_card as idcard,eu.card_type,u.reg_no as reg_no,eu.home_address as address,"
				+ " eu.degree as degree,eu.edu as edu,eu.gender as gender,eu.phone as phone,eu.title as title,eu.workplace as workplace,"
				+ " eu.work_postalcode as zip_address,m.name as major_name,g.name as grade_name,e.name as edutype_name,"
				+ " s.name as site_name,su.password,eu.birthday,su.email,eu.fax,eu.folk,eu.graduated_major,eu.graduated_sch,"
				+ " eu.graduated_time,eu.home_address,eu.home_postalcode,eu.work_address,eu.work_postalcode,eu.hometown,eu.mobilephone,"
				+ " eu.note,eu.position,eu.zzmm,u.class_id,u.entrance_date,u.photo_link,u.status as status,u.study_form,u.study_status "
				+ " from ("
				+ sql
				+ ") u,"
				+ "entity_usernormal_info eu,sso_user su,(select id,name from entity_major_info where id<>'0') m,(select id,name from entity_grade_info) g,"
				+ "(select id,name from entity_edu_type) e,(select id,name from entity_site_info) s where u.major_id = m.id and u.grade_id = g.id "
				+ "and u.site_id = s.id and u.edutype_id = e.id and u.id=eu.id and u.id=su.id";
		sql = "select id,name,idcard,card_type,major_name,grade_name,edutype_name,site_name,site_id,reg_no,address,"
				+ " degree,edu,gender,phone,title,workplace,zip_address,major_id,grade_id,edutype_id,password,birthday,email,"
				+ "fax,folk,graduated_major,graduated_sch,graduated_time,home_address,home_postalcode,work_address,work_postalcode,"
				+ "hometown,mobilephone,note,position,zzmm,class_id,entrance_date,photo_link,status,study_form,study_status "
				+ "from (" + sql + ")";
		return db.countselect(sql);
	}

	public List getStudents(Page page) {
		String sql = "select u.* from entity_student_info u,entity_usernormal_info eu, entity_teach_class a, entity_elective b,entity_course_active ca,entity_semester_info si "
				+ " where u.id=eu.id and u.isgraduated = '0' and u.status = '0' and u.id = b.student_id and b.teachclass_id = a.id and ca.id = a.open_course_id and si.id = ca.semester_id and a.id = '"
				+ this.getId() + "'  ";
		sql = "select u.id as id,u.name as name,u.grade_id as grade_id,u.major_id as major_id,u.edutype_id as edutype_id,"
				+ " u.site_id as site_id,u.id_card as idcard,eu.card_type,u.reg_no as reg_no,eu.home_address as address,"
				+ " eu.degree as degree,eu.edu as edu,eu.gender as gender,eu.phone as phone,eu.title as title,eu.workplace as workplace,"
				+ " eu.work_postalcode as zip_address,m.name as major_name,g.name as grade_name,e.name as edutype_name,"
				+ " s.name as site_name,su.password,eu.birthday,su.email,eu.fax,eu.folk,eu.graduated_major,eu.graduated_sch,"
				+ " eu.graduated_time,eu.home_address,eu.home_postalcode,eu.work_address,eu.work_postalcode,eu.hometown,eu.mobilephone,"
				+ " eu.note,eu.position,eu.zzmm,u.class_id,u.entrance_date,u.photo_link,u.status as status,u.study_form,u.study_status "
				+ " from ("
				+ sql
				+ ") u,"
				+ "entity_usernormal_info eu,sso_user su,(select id,name from entity_major_info where id<>'0') m,(select id,name from entity_grade_info) g,"
				+ "(select id,name from entity_edu_type) e,(select id,name from entity_site_info) s where u.major_id = m.id and u.grade_id = g.id "
				+ "and u.site_id = s.id and u.edutype_id = e.id and u.id=eu.id and u.id=su.id";
		sql = "select id,name,idcard,card_type,major_name,grade_name,edutype_name,site_name,site_id,reg_no,address,"
				+ " degree,edu,gender,phone,title,workplace,zip_address,major_id,grade_id,edutype_id,password,birthday,email,"
				+ "fax,folk,graduated_major,graduated_sch,graduated_time,home_address,home_postalcode,work_address,work_postalcode,"
				+ "hometown,mobilephone,note,position,zzmm,class_id,entrance_date,photo_link,status,study_form,study_status "
				+ "from (" + sql + ")";

		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList studentlist = new ArrayList();
		try {
			db = new dbpool();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				// student.setNickname(rs.getString("nickname"));
				student.setPassword(rs.getString("password"));
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setDegree(rs.getString("degree"));
				normalInfo.setEdu(rs.getString("edu"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				if (rs.getString("fax") != null
						&& rs.getString("fax").length() > 0)
					normalInfo.setFax(rs.getString("fax").split(","));
				else
					normalInfo.setFax(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setGraduated_major(rs.getString("graduated_major"));
				normalInfo.setGraduated_sch(rs.getString("graduated_sch"));
				normalInfo.setGraduated_time(rs.getString("graduated_time"));
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("home_address"));
				homeaddress.setPostalcode(rs.getString("home_postalcode"));
				Address workaddress = new Address();
				workaddress.setAddress(rs.getString("work_address"));
				workaddress.setPostalcode(rs.getString("work_postalcode"));
				normalInfo.setHome_address(homeaddress);
				normalInfo.setHometown(rs.getString("hometown"));
				normalInfo.setIdcard(rs.getString("idcard"));
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				normalInfo.setNote(rs.getString("note"));
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setWork_address(workaddress);
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				student.setNormalInfo(normalInfo);
				eduInfo.setClass_id(rs.getString("class_id"));
				// eduInfo.setClass_name(rs.getString("class_name"));
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
				// eduInfo.setIsgraduated(rs.getString("isgraduated"));
				eduInfo.setStudy_form(rs.getString("study_form"));
				eduInfo.setStudy_status(rs.getString("study_status"));
				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("StudentList.getStudents() error! " + sql);
			//System.out.println("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return studentlist;
	}
	
	
	public List getStudents(Page page , String stuRegNo,String stuName) {
//		String sql = "select u.* from entity_student_info u,entity_usernormal_info eu, entity_teach_class a, entity_elective b,entity_course_active ca,entity_semester_info si "
//				+ " where u.id=eu.id and u.isgraduated = '0' and u.status = '0' and u.id = b.student_id and b.teachclass_id = a.id and ca.id = a.open_course_id and si.id = ca.semester_id and a.id = '"
//				+ this.getId() + "'";
//		if(stuRegNo!=null&&(!stuRegNo.equals(""))&&stuRegNo.trim().length()>0){
//			sql +=" and u.reg_no ='"+stuRegNo+"'";
//		}
//		if(stuName!=null&&(!stuName.equals(""))&&stuName.trim().length()>0){
//			sql +=" and u.name like '%"+stuName+"%'";
//		}
//		sql = "select u.id as id,u.name as name,u.grade_id as grade_id,u.major_id as major_id,u.edutype_id as edutype_id,"
//				+ " u.site_id as site_id,u.id_card as idcard,eu.card_type,u.reg_no as reg_no,eu.home_address as address,"
//				+ " eu.degree as degree,eu.edu as edu,eu.gender as gender,eu.phone as phone,eu.title as title,eu.workplace as workplace,"
//				+ " eu.work_postalcode as zip_address,m.name as major_name,g.name as grade_name,e.name as edutype_name,"
//				+ " s.name as site_name,su.password,eu.birthday,su.email,eu.fax,eu.folk,eu.graduated_major,eu.graduated_sch,"
//				+ " eu.graduated_time,eu.home_address,eu.home_postalcode,eu.work_address,eu.work_postalcode,eu.hometown,eu.mobilephone,"
//				+ " eu.note,eu.position,eu.zzmm,u.class_id,u.entrance_date,u.photo_link,u.status as status,u.study_form,u.study_status "
//				+ " from ("
//				+ sql
//				+ ") u,"
//				+ "entity_usernormal_info eu,sso_user su,(select id,name from entity_major_info where id<>'0') m,(select id,name from entity_grade_info) g,"
//				+ "(select id,name from entity_edu_type) e,(select id,name from entity_site_info) s where u.major_id = m.id and u.grade_id = g.id "
//				+ "and u.site_id = s.id and u.edutype_id = e.id and u.id=eu.id and u.id=su.id";
//		sql = "select id,name,idcard,card_type,major_name,grade_name,edutype_name,site_name,site_id,reg_no,address,"
//				+ " degree,edu,gender,phone,title,workplace,zip_address,major_id,grade_id,edutype_id,password,birthday,email,"
//				+ "fax,folk,graduated_major,graduated_sch,graduated_time,home_address,home_postalcode,work_address,work_postalcode,"
//				+ "hometown,mobilephone,note,position,zzmm,class_id,entrance_date,photo_link,status,study_form,study_status "
//				+ "from (" + sql + ")";
		
		String sql = "select id,reg_no,name,gender,site_name,major_name,edu_name,grade_name,open_id from (select distinct prst.id as id,pest.reg_no,pest.name,prst.gender," +
		"si.name as site_name,ma.name as major_name,edu.name as edu_name,gr.name as grade_name,op.id as open_id  " +
		"	from pr_tch_opencourse op ,pr_tch_stu_elective el ,pr_student_info prst,pe_student pest ,pr_tch_opencourse_teacher prt" +
		"		,pe_teacher t,pe_site si,pe_major ma,pe_edutype edu,pe_grade gr where op.id=el.fk_opencourse_id and prst.id=pest.id " +
		"and el.fk_stu_id=pest.id and prt.fk_opencourse_id=op.id and t.id=prt.fk_teacher_id and si.id=pest.fk_site_id and ma.id=pest.fk_major_id " +
		"and edu.id=pest.fk_edutype_id and gr.id=pest.fk_grade_id) se where 1=1 ";
		if(stuRegNo!=null&&(!stuRegNo.equals(""))||stuRegNo.trim().length()>0){
				sql +=" and reg_no ='"+stuRegNo+"'";
		}
		if(stuName!=null&&(!stuName.equals(""))||stuName.trim().length()>0){
				sql +=" and name like '%"+stuName+"%'";
		}

		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList studentlist = new ArrayList();
		try {
			db = new dbpool();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				// student.setNickname(rs.getString("nickname"));
//				student.setPassword(rs.getString("password"));
//				normalInfo.setBirthday(rs.getString("birthday"));
//				normalInfo.setDegree(rs.getString("degree"));
//				normalInfo.setEdu(rs.getString("edu"));
//				if (rs.getString("email") != null
//						&& rs.getString("email").length() > 0)
//					normalInfo.setEmail(rs.getString("email").split(","));
//				else
//					normalInfo.setEmail(null);
//				if (rs.getString("fax") != null
//						&& rs.getString("fax").length() > 0)
//					normalInfo.setFax(rs.getString("fax").split(","));
//				else
//					normalInfo.setFax(null);
//				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
//				normalInfo.setGraduated_major(rs.getString("graduated_major"));
//				normalInfo.setGraduated_sch(rs.getString("graduated_sch"));
//				normalInfo.setGraduated_time(rs.getString("graduated_time"));
//				Address homeaddress = new Address();
//				homeaddress.setAddress(rs.getString("home_address"));
//				homeaddress.setPostalcode(rs.getString("home_postalcode"));
//				Address workaddress = new Address();
//				workaddress.setAddress(rs.getString("work_address"));
//				workaddress.setPostalcode(rs.getString("work_postalcode"));
//				normalInfo.setHome_address(homeaddress);
//				normalInfo.setHometown(rs.getString("hometown"));
//				normalInfo.setIdcard(rs.getString("idcard"));
//				if (rs.getString("mobilephone") != null
//						&& rs.getString("mobilephone").length() > 0)
//					normalInfo.setMobilePhone(rs.getString("mobilephone")
//							.split(","));
//				else
//					normalInfo.setMobilePhone(null);
//				normalInfo.setNote(rs.getString("note"));
//				if (rs.getString("phone") != null
//						&& rs.getString("phone").length() > 0)
//					normalInfo.setPhone(rs.getString("phone").split(","));
//				else
//					normalInfo.setPhone(null);
//				normalInfo.setPosition(rs.getString("position"));
//				normalInfo.setTitle(rs.getString("title"));
//				normalInfo.setWork_address(workaddress);
//				normalInfo.setWorkplace(rs.getString("workplace"));
//				normalInfo.setZzmm(rs.getString("zzmm"));
//				student.setNormalInfo(normalInfo);
//				eduInfo.setClass_id(rs.getString("class_id"));
//				// eduInfo.setClass_name(rs.getString("class_name"));
//				eduInfo.setEdutype_id(rs.getString("edutype_id"));
//				eduInfo.setEdutype_name(rs.getString("edutype_name"));
//				eduInfo.setEntrance_date(rs.getString("entrance_date"));
//				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
//				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
//				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
//				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
//				eduInfo.setStatus(rs.getString("status"));
				// eduInfo.setIsgraduated(rs.getString("isgraduated"));
//				eduInfo.setStudy_form(rs.getString("study_form"));
//				eduInfo.setStudy_status(rs.getString("study_status"));
				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("StudentList.getStudents() error! " + sql);
			//System.out.println("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return studentlist;
	}

	
	public List getStudents(Page page , String stuRegNo,String stuName,String siteId) {
//		String sql = "select id,reg_no,name,gender,site_name,major_name,edu_name,grade_name,open_id from (select distinct prst.id as id,pest.reg_no,pest.true_name as name,prst.gender," +
//		"si.name as site_name,ma.name as major_name,edu.name as edu_name,gr.name as grade_name,op.id as open_id  " +
//		"	from pr_tch_opencourse op ,pr_tch_stu_elective el ,pr_student_info prst,pe_student pest ,pr_tch_opencourse_teacher prt" +
//		"		,pe_teacher t,pe_site si,pe_major ma,pe_edutype edu,pe_grade gr where op.id=el.fk_opencourse_id and prst.id=pest.id " +
//		"and el.fk_stu_id=pest.id and prt.fk_opencourse_id=op.id and t.id=prt.fk_teacher_id and si.id=pest.fk_site_id and ma.id=pest.fk_major_id " +
//		"and edu.id=pest.fk_edutype_id and gr.id=pest.fk_grade_id  and pest.flag_abort_school = '0' and pest.graduation_status = '0') se where 1=1 ";
		String sql = "select id,                                                                    " +
		"       reg_no,                                                                " +
		"       name,                                                                  " +
		"       gender,                                                                " +
		"       site_name,                                                             " +
		"       major_name,                                                            " +
		"       edu_name,                                                              " +
		"       grade_name,                                                            " +
		"       open_id                                                                " +
		"  from (select one.id,                                                        " +
		"               one.reg_no,                                                    " +
		"               one.name,                                                      " +
		"               one.gender,                                                    " +
		"               one.site_name,                                                 " +
		"               one.major_name,                                                " +
		"               one.edu_name,                                                  " +
		"               one.grade_name,                                                " +
		"               two.open_id                                                    " +
		"          from (select distinct prst.id as id,                                " +
		"                                pest.reg_no,                                  " +
		"                                pest.true_name as name,                       " +
		"                                prst.gender,                                  " +
		"                                si.name as site_name,                         " +
		"                                ma.name as major_name,                        " +
		"                                edu.name as edu_name,                         " +
		"                                gr.name as grade_name,                        " +
		"                                course.id as c_id                             " +
		"                  from pr_tch_opencourse         op,                          " +
		"                       pr_tch_stu_elective       el,                          " +
		"                       pr_student_info           prst,                        " +
		"                       pe_student                pest,                        " +
		"                       pe_site                   si,                          " +
		"                       pe_major                  ma,                          " +
		"                       pe_edutype                edu,                         " +
		"                       pe_grade                  gr,                          " +
		"                       pe_tch_course             course,                      " +
		"                       pe_semester               semester,                    " +
		"                       enum_const                enum                         " +
		"                 where op.id = el.fk_tch_opencourse_id                        " +
		"                   and prst.id = pest.fk_student_info_id                      " +
		"                   and el.fk_stu_id = pest.id                                 " +
		"                   and si.id = pest.fk_site_id                                " +
		"                   and ma.id = pest.fk_major_id                               " +
		"                   and edu.id = pest.fk_edutype_id                            " +
		"                   and gr.id = pest.fk_grade_id                               " +
		"                   and op.fk_semester_id= semester.id                         " +
		"                   and pest.flag_student_status = enum.id                     " +
		"                   and enum.code = '4'                                        " +
		"                   and op.fk_course_id = course.id                            " +
		"                   and (semester.flag_active = '1' or                         " +
		"                       (semester.flag_active = '0' and                        " +
		"                       (el.score_total is null or el.score_total < 60)))) one," +
		"               (select distinct course.id as c_id, op.id as open_id           " +
		"                  from pr_tch_opencourse op, pe_tch_course course             " +
		"                 where op.fk_course_id = course.id) two                       " +
		"         where one.c_id = two.c_id) se                                        " +
		" where 1 = 1";
		if(stuRegNo!=null&&(!stuRegNo.equals(""))||stuRegNo.trim().length()>0){
				sql +=" and reg_no like'%"+stuRegNo+"%'";
		}
		if(stuName!=null&&(!stuName.equals(""))||stuName.trim().length()>0){
				sql +=" and name like '%"+stuName+"%'";
		}
		if(siteId!=null&&(!siteId.equals(""))&&siteId.trim().length()>0){
			sql +=" and open_id like '%"+siteId+"%'";
		}

		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList studentlist = new ArrayList();
		try {
			db = new dbpool();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				// student.setNickname(rs.getString("nickname"));
//				student.setPassword(rs.getString("password"));
//				normalInfo.setBirthday(rs.getString("birthday"));
//				normalInfo.setDegree(rs.getString("degree"));
//				normalInfo.setEdu(rs.getString("edu"));
//				if (rs.getString("email") != null
//						&& rs.getString("email").length() > 0)
//					normalInfo.setEmail(rs.getString("email").split(","));
//				else
//					normalInfo.setEmail(null);
//				if (rs.getString("fax") != null
//						&& rs.getString("fax").length() > 0)
//					normalInfo.setFax(rs.getString("fax").split(","));
//				else
//					normalInfo.setFax(null);
//				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
//				normalInfo.setGraduated_major(rs.getString("graduated_major"));
//				normalInfo.setGraduated_sch(rs.getString("graduated_sch"));
//				normalInfo.setGraduated_time(rs.getString("graduated_time"));
//				Address homeaddress = new Address();
//				homeaddress.setAddress(rs.getString("home_address"));
//				homeaddress.setPostalcode(rs.getString("home_postalcode"));
//				Address workaddress = new Address();
//				workaddress.setAddress(rs.getString("work_address"));
//				workaddress.setPostalcode(rs.getString("work_postalcode"));
//				normalInfo.setHome_address(homeaddress);
//				normalInfo.setHometown(rs.getString("hometown"));
//				normalInfo.setIdcard(rs.getString("idcard"));
//				if (rs.getString("mobilephone") != null
//						&& rs.getString("mobilephone").length() > 0)
//					normalInfo.setMobilePhone(rs.getString("mobilephone")
//							.split(","));
//				else
//					normalInfo.setMobilePhone(null);
//				normalInfo.setNote(rs.getString("note"));
//				if (rs.getString("phone") != null
//						&& rs.getString("phone").length() > 0)
//					normalInfo.setPhone(rs.getString("phone").split(","));
//				else
//					normalInfo.setPhone(null);
//				normalInfo.setPosition(rs.getString("position"));
//				normalInfo.setTitle(rs.getString("title"));
//				normalInfo.setWork_address(workaddress);
//				normalInfo.setWorkplace(rs.getString("workplace"));
//				normalInfo.setZzmm(rs.getString("zzmm"));
//				student.setNormalInfo(normalInfo);
//				eduInfo.setClass_id(rs.getString("class_id"));
//				// eduInfo.setClass_name(rs.getString("class_name"));
//				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edu_name"));
//				eduInfo.setEntrance_date(rs.getString("entrance_date"));
//				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
//				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
//				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
//				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
//				eduInfo.setStatus(rs.getString("status"));
				// eduInfo.setIsgraduated(rs.getString("isgraduated"));
//				eduInfo.setStudy_form(rs.getString("study_form"));
//				eduInfo.setStudy_status(rs.getString("study_status"));
				student.setStudentInfo(eduInfo);
				student.setNormalInfo(normalInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("StudentList.getStudents() error! " + sql);
			//System.out.println("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return studentlist;
	}
	
	public int getStudentsNum(String site_id) {
		dbpool db = new dbpool();
		/*
		 * String sql = "select a.student_id from entity_elective a,
		 * entity_teach_class b where a.teachclass_id = b.id " + " and b.id = '" +
		 * this.getId() + "'";
		 */
		String sql = "select u.* from entity_student_info u,entity_usernormal_info eu, entity_teach_class a, entity_elective b"
				+ " where u.id=eu.id and u.isgraduated = '0' and u.status = '0' and u.id = b.student_id and b.teachclass_id = a.id and a.id = '"
				+ this.getId() + "' and u.site_id='" + site_id + "'  ";
		sql = "select u.id as id,u.name as name,u.grade_id as grade_id,u.major_id as major_id,u.edutype_id as edutype_id,"
				+ " u.site_id as site_id,u.id_card as idcard,eu.card_type,u.reg_no as reg_no,eu.home_address as address,"
				+ " eu.degree as degree,eu.edu as edu,eu.gender as gender,eu.phone as phone,eu.title as title,eu.workplace as workplace,"
				+ " eu.work_postalcode as zip_address,m.name as major_name,g.name as grade_name,e.name as edutype_name,"
				+ " s.name as site_name,su.password,eu.birthday,su.email,eu.fax,eu.folk,eu.graduated_major,eu.graduated_sch,"
				+ " eu.graduated_time,eu.home_address,eu.home_postalcode,eu.work_address,eu.work_postalcode,eu.hometown,eu.mobilephone,"
				+ " eu.note,eu.position,eu.zzmm,u.class_id,u.entrance_date,u.photo_link,u.status as status,u.study_form,u.study_status "
				+ " from ("
				+ sql
				+ ") u,"
				+ "entity_usernormal_info eu,sso_user su,(select id,name from entity_major_info where id<>'0') m,(select id,name from entity_grade_info) g,"
				+ "(select id,name from entity_edu_type) e,(select id,name from entity_site_info) s where u.major_id = m.id and u.grade_id = g.id "
				+ "and u.site_id = s.id and u.edutype_id = e.id and u.id=eu.id and u.id=su.id";
		sql = "select id,name,idcard,card_type,major_name,grade_name,edutype_name,site_name,site_id,reg_no,address,"
				+ " degree,edu,gender,phone,title,workplace,zip_address,major_id,grade_id,edutype_id,password,birthday,email,"
				+ "fax,folk,graduated_major,graduated_sch,graduated_time,home_address,home_postalcode,work_address,work_postalcode,"
				+ "hometown,mobilephone,note,position,zzmm,class_id,entrance_date,photo_link,status,study_form,study_status "
				+ "from (" + sql + ")";
		return db.countselect(sql);
	}
	public int getStudentsNum(String stuRegNo,String stuName) {
		dbpool db = new dbpool();
		/*
		 * String sql = "select a.student_id from entity_elective a,
		 * entity_teach_class b where a.teachclass_id = b.id " + " and b.id = '" +
		 * this.getId() + "'";
		 */
//		String sql = "select u.* from entity_student_info u,entity_usernormal_info eu, entity_teach_class a, entity_elective b"
//				+ " where u.id=eu.id and u.isgraduated = '0' and u.status = '0' and u.id = b.student_id and b.teachclass_id = a.id and a.id = '"
//				+ this.getId() + "'";
//		if(stuRegNo!=null&&(!stuRegNo.equals(""))||stuRegNo.trim().length()>0){
//			sql +=" and u.reg_no ='"+stuRegNo+"'";
//		}
//		if(stuName!=null&&(!stuName.equals(""))||stuName.trim().length()>0){
//			sql +=" and u.name like '%"+stuName+"%'";
//		}
//		sql = "select u.id as id,u.name as name,u.grade_id as grade_id,u.major_id as major_id,u.edutype_id as edutype_id,"
//				+ " u.site_id as site_id,u.id_card as idcard,eu.card_type,u.reg_no as reg_no,eu.home_address as address,"
//				+ " eu.degree as degree,eu.edu as edu,eu.gender as gender,eu.phone as phone,eu.title as title,eu.workplace as workplace,"
//				+ " eu.work_postalcode as zip_address,m.name as major_name,g.name as grade_name,e.name as edutype_name,"
//				+ " s.name as site_name,su.password,eu.birthday,su.email,eu.fax,eu.folk,eu.graduated_major,eu.graduated_sch,"
//				+ " eu.graduated_time,eu.home_address,eu.home_postalcode,eu.work_address,eu.work_postalcode,eu.hometown,eu.mobilephone,"
//				+ " eu.note,eu.position,eu.zzmm,u.class_id,u.entrance_date,u.photo_link,u.status as status,u.study_form,u.study_status "
//				+ " from ("
//				+ sql
//				+ ") u,"
//				+ "entity_usernormal_info eu,sso_user su,(select id,name from entity_major_info where id<>'0') m,(select id,name from entity_grade_info) g,"
//				+ "(select id,name from entity_edu_type) e,(select id,name from entity_site_info) s where u.major_id = m.id and u.grade_id = g.id "
//				+ "and u.site_id = s.id and u.edutype_id = e.id and u.id=eu.id and u.id=su.id";
//		sql = "select id,name,idcard,card_type,major_name,grade_name,edutype_name,site_name,site_id,reg_no,address,"
//				+ " degree,edu,gender,phone,title,workplace,zip_address,major_id,grade_id,edutype_id,password,birthday,email,"
//				+ "fax,folk,graduated_major,graduated_sch,graduated_time,home_address,home_postalcode,work_address,work_postalcode,"
//				+ "hometown,mobilephone,note,position,zzmm,class_id,entrance_date,photo_link,status,study_form,study_status "
//				+ "from (" + sql + ")";
		String sql = "select id,reg_no,name,gender,site_name,major_name,edu_name,grade_name,open_id from (select distinct prst.id as id,pest.reg_no,pest.name,prst.gender," +
		"si.name as site_name,ma.name as major_name,edu.name as edu_name,gr.name as grade_name,op.id as open_id  " +
		"	from pr_tch_opencourse op ,pr_tch_stu_elective el ,pr_student_info prst,pe_student pest ,pr_tch_opencourse_teacher prt" +
		"		,pe_teacher t,pe_site si,pe_major ma,pe_edutype edu,pe_grade gr where op.id=el.fk_opencourse_id and prst.id=pest.id " +
		"and el.fk_stu_id=pest.id and prt.fk_opencourse_id=op.id and t.id=prt.fk_teacher_id and si.id=pest.fk_site_id and ma.id=pest.fk_major_id " +
		"and edu.id=pest.fk_edutype_id and gr.id=pest.fk_grade_id) se where 1=1 ";
		
		if(stuRegNo!=null&&(!stuRegNo.equals(""))||stuRegNo.trim().length()>0){
			sql +=" and reg_no ='"+stuRegNo+"'";
		}
		if(stuName!=null&&(!stuName.equals(""))||stuName.trim().length()>0){
			sql +=" and name like '%"+stuName+"%'";
		}
		return db.countselect(sql);
	}
	
	
	/**
	 * 传入开课id 获得学生 siteid = opencourseId
	 */
	public int getStudentsNum(String stuRegNo,String stuName,String siteId) {
		dbpool db = new dbpool();

		String sql = "select id,                                                                    " +
		"       reg_no,                                                                " +
		"       name,                                                                  " +
		"       gender,                                                                " +
		"       site_name,                                                             " +
		"       major_name,                                                            " +
		"       edu_name,                                                              " +
		"       grade_name,                                                            " +
		"       open_id                                                                " +
		"  from (select one.id,                                                        " +
		"               one.reg_no,                                                    " +
		"               one.name,                                                      " +
		"               one.gender,                                                    " +
		"               one.site_name,                                                 " +
		"               one.major_name,                                                " +
		"               one.edu_name,                                                  " +
		"               one.grade_name,                                                " +
		"               two.open_id                                                    " +
		"          from (select distinct prst.id as id,                                " +
		"                                pest.reg_no,                                  " +
		"                                pest.true_name as name,                       " +
		"                                prst.gender,                                  " +
		"                                si.name as site_name,                         " +
		"                                ma.name as major_name,                        " +
		"                                edu.name as edu_name,                         " +
		"                                gr.name as grade_name,                        " +
		"                                course.id as c_id                             " +
		"                  from pr_tch_opencourse         op,                          " +
		"                       pr_tch_stu_elective       el,                          " +
		"                       pr_student_info           prst,                        " +
		"                       pe_student                pest,                        " +
		"                       pe_site                   si,                          " +
		"                       pe_major                  ma,                          " +
		"                       pe_edutype                edu,                         " +
		"                       pe_grade                  gr,                          " +
		"                       pe_tch_course             course,                      " +
		"                       pe_semester               semester,                    " +
		"                       enum_const                enum                         " +
		"                 where op.id = el.fk_tch_opencourse_id                        " +
		"                   and prst.id = pest.fk_student_info_id                      " +
		"                   and el.fk_stu_id = pest.id                                 " +
		"                   and si.id = pest.fk_site_id                                " +
		"                   and ma.id = pest.fk_major_id                               " +
		"                   and edu.id = pest.fk_edutype_id                            " +
		"                   and gr.id = pest.fk_grade_id                               " +
		"                   and op.fk_semester_id= semester.id                         " +
		"                   and pest.flag_student_status = enum.id                     " +
		"                   and enum.code = '4'                                        " +
		"                   and op.fk_course_id = course.id                            " +
		"                   and (semester.flag_active = '1' or                         " +
		"                       (semester.flag_active = '0' and                        " +
		"                       (el.score_total is null or el.score_total < 60)))) one," +
		"               (select distinct course.id as c_id, op.id as open_id           " +
		"                  from pr_tch_opencourse op, pe_tch_course course             " +
		"                 where op.fk_course_id = course.id) two                       " +
		"         where one.c_id = two.c_id) se                                        " +
		" where 1 = 1" ;
		if(stuRegNo!=null&&(!stuRegNo.equals(""))||stuRegNo.trim().length()>0){
			sql +=" and reg_no like'%"+stuRegNo+"%'";
		}
		if(stuName!=null&&(!stuName.equals(""))||stuName.trim().length()>0){
			sql +=" and name like '%"+stuName+"%'";
		}
		if(siteId!=null&&(!siteId.equals(""))||siteId.trim().length()>0){
			sql +=" and open_id = '"+siteId+"'";
		}
		
		return db.countselect(sql);
		
	}
	

	public List getStudents(Page page, String site_id) {
		String sql = "select u.* from entity_student_info u,entity_usernormal_info eu, entity_teach_class a, entity_elective b"
				+ " where u.id=eu.id and u.isgraduated = '0' and u.status = '0' and u.id = b.student_id and b.teachclass_id = a.id and a.id = '"
				+ this.getId() + "' and u.site_id='" + site_id + "' ";
		sql = "select u.id as id,u.name as name,u.grade_id as grade_id,u.major_id as major_id,u.edutype_id as edutype_id,"
				+ " u.site_id as site_id,u.id_card as idcard,eu.card_type,u.reg_no as reg_no,eu.home_address as address,"
				+ " eu.degree as degree,eu.edu as edu,eu.gender as gender,eu.phone as phone,eu.title as title,eu.workplace as workplace,"
				+ " eu.work_postalcode as zip_address,m.name as major_name,g.name as grade_name,e.name as edutype_name,"
				+ " s.name as site_name,su.password,eu.birthday,su.email,eu.fax,eu.folk,eu.graduated_major,eu.graduated_sch,"
				+ " eu.graduated_time,eu.home_address,eu.home_postalcode,eu.work_address,eu.work_postalcode,eu.hometown,eu.mobilephone,"
				+ " eu.note,eu.position,eu.zzmm,u.class_id,u.entrance_date,u.photo_link,u.status as status,u.study_form,u.study_status "
				+ " from ("
				+ sql
				+ ") u,"
				+ "entity_usernormal_info eu,sso_user su,(select id,name from entity_major_info where id<>'0') m,(select id,name from entity_grade_info) g,"
				+ "(select id,name from entity_edu_type) e,(select id,name from entity_site_info) s where u.major_id = m.id and u.grade_id = g.id "
				+ "and u.site_id = s.id and u.edutype_id = e.id and u.id=eu.id and u.id=su.id";
		sql = "select id,name,idcard,card_type,major_name,grade_name,edutype_name,site_name,site_id,reg_no,address,"
				+ " degree,edu,gender,phone,title,workplace,zip_address,major_id,grade_id,edutype_id,password,birthday,email,"
				+ "fax,folk,graduated_major,graduated_sch,graduated_time,home_address,home_postalcode,work_address,work_postalcode,"
				+ "hometown,mobilephone,note,position,zzmm,class_id,entrance_date,photo_link,status,study_form,study_status "
				+ "from (" + sql + ")";

		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList studentlist = new ArrayList();
		try {
			db = new dbpool();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				// student.setNickname(rs.getString("nickname"));
				student.setPassword(rs.getString("password"));
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setDegree(rs.getString("degree"));
				normalInfo.setEdu(rs.getString("edu"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				if (rs.getString("fax") != null
						&& rs.getString("fax").length() > 0)
					normalInfo.setFax(rs.getString("fax").split(","));
				else
					normalInfo.setFax(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setGraduated_major(rs.getString("graduated_major"));
				normalInfo.setGraduated_sch(rs.getString("graduated_sch"));
				normalInfo.setGraduated_time(rs.getString("graduated_time"));
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("home_address"));
				homeaddress.setPostalcode(rs.getString("home_postalcode"));
				Address workaddress = new Address();
				workaddress.setAddress(rs.getString("work_address"));
				workaddress.setPostalcode(rs.getString("work_postalcode"));
				normalInfo.setHome_address(homeaddress);
				normalInfo.setHometown(rs.getString("hometown"));
				normalInfo.setIdcard(rs.getString("idcard"));
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				normalInfo.setNote(rs.getString("note"));
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setWork_address(workaddress);
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				student.setNormalInfo(normalInfo);
				eduInfo.setClass_id(rs.getString("class_id"));
				// eduInfo.setClass_name(rs.getString("class_name"));
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
				// eduInfo.setIsgraduated(rs.getString("isgraduated"));
				eduInfo.setStudy_form(rs.getString("study_form"));
				eduInfo.setStudy_status(rs.getString("study_status"));
				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("StudentList.getStudents() error! " + sql);
		//	System.out.println("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return studentlist;
	}

	public List getTeachers(Page page) {
		String SQLTEACHER = "";
		String SQLTEACHER_ORDERBY = "";
		OracleBasicUserList teacherlist = new OracleBasicUserList();
		SQLTEACHER = "select id,name,nickname,password,email,teach_time,gender,teach_level,teach_time,phone,address,zip_code,work_place,position,note,title "
				+ "from lrn_teacher_info t,lrn_teach_course tc,lrn_teach_class c where c.id = tc.open_course_id and t.id = tc.teacher_id and c.id = '"
				+ this.getId() + "'";
		SQLTEACHER_ORDERBY = "order by id asc";
		return teacherlist.getTeachers(page, null, null);
	}

	public void removeStudents(List students) throws PlatformException {

		Map stuTeachClassMap = new HashMap();
		for (int i = 0; i < students.size(); i++) {
			stuTeachClassMap.put((Student) students.get(i), this);
		}
		OracleElectiveActivity electiveActivity = new OracleElectiveActivity();
		electiveActivity.setStudentTeachClassMap(stuTeachClassMap);
		electiveActivity.unSelectTeachClass();

	}

	public void removeTeachers(List teachers) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_teacher_course where teacher_id in (";
		for (int i = 0; i < teachers.size(); i++) {
			OracleTeacher tea = (OracleTeacher) teachers.get(i);
			if (i < teachers.size() - 1) {
				sql = sql + "'" + tea.getId() + "',";
			} else {
				sql = sql + "'" + tea.getId() + "')";
			}
		}
		
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTeachClass.removeTeachers(List teachers) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	public int addTeachersToTeachClass(String[] teacher_ids) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_teacher_course(id,teacher_id,teachclass_id) select to_char(s_teacher_course_id.nextval),id,'"
				+ this.getId() + "' from entity_teacher_info where id in (";
		if (teacher_ids == null)
			return 0;
		for (int i = 0; i < teacher_ids.length; i++) {
			if (i < teacher_ids.length - 1) {
				sql = sql + "'" + teacher_ids[i] + "',";
			} else {
				sql = sql + "'" + teacher_ids[i] + "')";
			}
		}
		sql = sql
				+ "and id not in (select teacher_id from entity_teacher_course where teachclass_id='"
				+ this.getId() + "')";
		// System.out.println();
		// System.out.println("add=" + sql);
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTeachClass.addTeachersToTeachClass(String[] teacher_ids) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public void addTeachersToTeachClass(List teacher_ids) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_teacher_course(id,teacher_id,teachclass_id) select to_char(s_teacher_course_id.nextval),id,'"
				+ this.getId() + "' from entity_teacher_info where id in (";
		if (teacher_ids == null)
			return;
		for (int i = 0; i < teacher_ids.size(); i++) {
			if (i < teacher_ids.size() - 1) {
				sql = sql + "'" + teacher_ids.get(i) + "',";
			} else {
				sql = sql + "'" + teacher_ids.get(i) + "')";
			}
		}
		// System.out.println();
		// System.out.println("add=" + sql);
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTeachClass.addTeachersToTeachClass(List teacher_ids) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	public int removeTeachersFromTeachClass(String[] teacher_ids) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_teacher_course where teachclass_id = '"
				+ this.getId() + "' and  teacher_id in (";
		if (teacher_ids == null)
			return 0;
		for (int i = 0; i < teacher_ids.length; i++) {
			if (i < teacher_ids.length - 1) {
				sql = sql + "'" + teacher_ids[i] + "',";
			} else {
				sql = sql + "'" + teacher_ids[i] + "')";
			}
		}
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTeachClass.removeTeachersFromTeachClass(String[] teacher_ids) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int removeSiteTeachersFromTeachClass(String[] siteteacher_ids) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_siteteacher_course where teachclass_id = '"
				+ this.getId() + "' and  teacher_id in (";
		if (siteteacher_ids == null)
			return 0;
		for (int i = 0; i < siteteacher_ids.length; i++) {
			if (i < siteteacher_ids.length - 1) {
				sql = sql + "'" + siteteacher_ids[i] + "',";
			} else {
				sql = sql + "'" + siteteacher_ids[i] + "')";
			}
		}
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTeachClass.removeSiteTeachersFromTeachClass(String[] siteteacher_ids) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public void removeTeachersFromTeachClass(List teacher_ids) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_teacher_course where teachclass_id = '"
				+ this.getId() + "' and  teacher_id in (";
		if (teacher_ids == null || teacher_ids.size() < 1)
			return;
		for (int i = 0; i < teacher_ids.size(); i++) {
			if (i < teacher_ids.size() - 1) {
				sql = sql + "'" + teacher_ids.get(i) + "',";
			} else {
				sql = sql + "'" + teacher_ids.get(i) + "')";
			}
		}
		// System.out.println("del=" + sql);
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTeachClass.removeTeachersFromTeachClass(List siteteacher_ids) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_teach_class set name='" + this.getName()
				+ "' where id='" + this.getId() + "";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTeachClass.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public boolean isHasStudents() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_elective where open_course_id = '"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		if (i < 1)
			return false;
		else
			return true;
	}

	public boolean isHasTeachers() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_teacher_course where open_course_id = '"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		if (i < 1)
			return false;
		else
			return true;
	}

	public boolean isNameExist() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_teach_class where name = '"
				+ this.getName() + "'";
		int i = db.countselect(sql);
		if (i < 1)
			return false;
		else
			return true;
	}

	public int checkTeacher(String teacher_id) {
		dbpool db = new dbpool();
		String sql = "select id from entity_teacher_course where teacher_id = '"
				+ teacher_id
				+ "' "
				+ "and teachclass_id = '"
				+ this.getId()
				+ "'";
		// System.out.println("check=" + sql);
		return db.countselect(sql);
	}

	public void addTeachers(String[] teacherIdSet) {
		dbpool db = new dbpool();
		ArrayList sqlList = new ArrayList();
		String sql1 = "delete from entity_teacher_course where teachclass_id='"
				+ this.getId() + "'";
		String sql = "";
		if (teacherIdSet != null) {
			sql = "insert into entity_teacher_course(id,teacher_id,teachclass_id) select to_char(s_teacher_course_id.nextval),id,'"
					+ this.getId() + "' from entity_teacher_info where id in (";
			for (int i = 0; i < teacherIdSet.length; i++) {
				if (i != teacherIdSet.length - 1) {
					sql = sql + "'" + teacherIdSet[i] + "',";
				} else {
					sql = sql + "'" + teacherIdSet[i] + "')";
				}
			}
		}
		UserDeleteLog.setDebug("OracleTeachClass.addTeachers(String[] teacherIdSet) SQL=" + sql1 + " DATE=" + new Date());
		UserAddLog.setDebug("OracleTeachClass.addTeachers(String[] teacherIdSet) SQL=" + sql + " DATE=" + new Date());
		sqlList.add(sql1);
		sqlList.add(sql);
		db.executeUpdateBatch(sqlList);
	}

	public void addSiteTeachers(String[] teacherIdSet) {
		dbpool db = new dbpool();
		ArrayList sqlList = new ArrayList();
		String sql1 = "delete from entity_siteteacher_course where teachclass_id='"
				+ this.getId() + "'";
		String sql = "";
		if (teacherIdSet != null) {
			sql = "insert into entity_siteteacher_course(id,teacher_id,teachclass_id) select to_char(s_siteteacher_course_id.nextval),id,'"
					+ this.getId()
					+ "' from entity_siteteacher_info where id in (";
			for (int i = 0; i < teacherIdSet.length; i++) {
				if (i != teacherIdSet.length - 1) {
					sql = sql + "'" + teacherIdSet[i] + "',";
				} else {
					sql = sql + "'" + teacherIdSet[i] + "')";
				}
			}
		}
		UserDeleteLog.setDebug("OracleTeachClass.addSiteTeachers(String[] teacherIdSet) SQL=" + sql1 + " DATE=" + new Date());
		UserAddLog.setDebug("OracleTeachClass.addSiteTeachers(String[] teacherIdSet) SQL=" + sql + " DATE=" + new Date());
		sqlList.add(sql1);
		sqlList.add(sql);
		db.executeUpdateBatch(sqlList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.TeachClass#getCoursewares()
	 * zlw
	 */
	public List getCoursewares() throws PlatformException {
		List coursewareList = new ArrayList();
		dbpool db = new dbpool();
		String sql = "select a.id as cware_id, b.id as courseware_id, b.name as courseware_name," +
				"AUTHOR,PUBLISHER,b.NOTE,LINK,en.code as active,cd.id as courseware_dir " +
				"from PR_TCH_OPENCOURSE_COURSEWARE a, PE_TCH_COURSEWARE b,pe_tch_coursewaredir cd,enum_const en where en.id=b.flag_isvalid and b.fk_coursewaredir_id=cd.id and  a.fk_opencourse_id = '"+this.getId()+"' and a.fk_courseware_id=b.id ";
		MyResultSet rs = null;

		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleCourseware courseware = new OracleCourseware();
				courseware.setId(rs.getString("courseware_id"));
				courseware.setName(rs.getString("courseware_name"));
				courseware.setAuthor(rs.getString("author"));
				if ("1".equals(rs.getString("active")))
					courseware.setActive(false);
				else
					courseware.setActive(true);
//				courseware.setFoundDate(rs.getString("found_date"));
//				courseware.setFounderId(rs.getString("founder_id"));
//				courseware.setCoursewareType(rs.getString("courseware_type"));
				courseware.setCoursewareDirId(rs.getString("courseware_dir"));
				coursewareList.add(courseware);
			}
		} catch (SQLException sqle) {
			throw new PlatformException("查询教学班的课件，读取数据库失败！");
		} finally {
			db.close(rs);
			rs = null;
		}
		return coursewareList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.TeachClass#getCoursewares()
	 */
	public List getActiveCoursewares() throws PlatformException {
		List coursewareList = new ArrayList();
		dbpool db = new dbpool();
		String sql = "select a.id as cware_id, b.id as courseware_id, b.name as courseware_name from PR_TCH_OPENCOURSE_COURSEWARE a, PE_TCH_COURSEWARE b,pe_tch_coursewaredir cd,enum_const en where en.id=b.flag_isvalid and b.fk_coursewaredir_id=cd.id and  a.fk_opencourse_id = '"+this.getId()+"' and a.fk_courseware_id=b.id and en.code='1' ";
		MyResultSet rs = null;

		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleCourseware courseware = new OracleCourseware();
				courseware.setId(rs.getString("courseware_id"));
				courseware.setName(rs.getString("courseware_name"));
				courseware.setActive(true);
				coursewareList.add(courseware);
			}
		} catch (SQLException sqle) {
			throw new PlatformException("查询教学班的课件，读取数据库失败！");
		} finally {
			db.close(rs);
			rs = null;
		}
		return coursewareList;
	}

	public int updateAssistance() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_teach_class set assistance_note='"
				+ this.getAssistanceNote() + "',assistance_title='"
				+ this.getAssistanceTitle() + "',assistance_release_status='"
				+ this.getAssistanceReleaseStatus()
				+ "' where open_course_id='" + this.getOpenCourse().getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTeachClass.updateAssistance() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;

	}

	public int releaseAssistance(String publisherId, String publisherName,
			String publisherType) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select assistance_id,course_id from "
				+ "(select a.assistance_id,b.course_id from entity_teach_class a,entity_course_active b "
				+ "where a.open_course_id=b.id and a.open_course_id='"
				+ this.getOpenCourse().getId() + "')";
		rs = db.executeQuery(sql);
		String assistanceId = "";
		String courseId = "";
		try {
			if (rs != null && rs.next()) {
				assistanceId = rs.getString("assistance_id");
				courseId = rs.getString("course_id");
			}
		} catch (SQLException e) {
			
		}
		db.close(rs);
		OracleAnnounce announce = new OracleAnnounce();
		announce.setTitle(this.getAssistanceTitle());
		announce.setBody(this.getAssistanceNote());
		announce.setPublisherId(publisherId);
		announce.setPublisherName(publisherName);
		announce.setPublisherType(publisherType);
		OracleCourse course = new OracleCourse();
		course.setId(courseId);
		announce.setCourseId(courseId);
		int i = 0;
		if (assistanceId == null || assistanceId.equals("")
				|| assistanceId.equals("null")) {
			int id = announce.add();
			sql = "update entity_teach_class set assistance_id='" + id + "'"
					+ "where open_course_id='" + this.getOpenCourse().getId()
					+ "'";
			i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleTeachClass.releaseAssistance(String publisherId, String publisherName,String publisherType) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		} else {
			announce.setId(assistanceId);
			i = announce.update();
		}
		return i;
	}

	public int unReleaseAssistance(String publisherId, String publisherName,
			String publisherType) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select assistance_id,course_id from "
				+ "(select a.assistance_id,b.course_id from entity_teach_class a,entity_course_active b "
				+ "where a.open_course_id=b.id and a.open_course_id='"
				+ this.getOpenCourse().getId() + "')";
		rs = db.executeQuery(sql);
		String assistanceId = "";
		String courseId = "";
		try {
			if (rs != null && rs.next()) {
				assistanceId = rs.getString("assistance_id");
				courseId = rs.getString("course_id");
			}
		} catch (SQLException e) {
			
		}
		db.close(rs);
		int i = 0;
		if (assistanceId == null || assistanceId.equals("")
				|| assistanceId.equals("null")) {
			i = 1;
		} else {
			sql = "delete from interaction_announce_info where id='"
					+ assistanceId + "'";
			i = db.executeUpdate(sql);
			UserDeleteLog.setDebug("OracleTeachClass.unReleaseAssistance(String publisherId, String publisherName,String publisherType) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			sql = "update entity_teach_class set assistance_id='' where open_course_id='"
					+ this.getOpenCourse().getId() + "'";
			i += db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleTeachClass.unReleaseAssistance(String publisherId, String publisherName,String publisherType) SQL=" + sql + " DATE=" + new Date());
		}
		return i;
	}

	public String getTeachClassId(String open_course_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select id from  entity_teach_class  where open_course_id='"
				+ open_course_id + "'";
		rs = db.executeQuery(sql);

		String teachClassId = "";
		try {
			if (rs != null && rs.next()) {
				teachClassId = rs.getString("id");

			}
		} catch (SQLException e) {
			
		}
		db.close(rs);

		return teachClassId;
	}
	public List getOtherCoursewares(String id) throws PlatformException {
		List coursewareList = new ArrayList();
		dbpool db = new dbpool();
		String sql = "select  t1.active,t1.id,t1.name,t1.author,t1.courseware_type,t1.publisher,t1.note from PE_TCH_COURSEWARE t1 where t1.id not in(select t3.courseware_id from PR_TCH_OPENCOURSE_COURSEWARE t3 where t3.teachclass_id = '"+id+"')";
		MyResultSet rs = null;

		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleCourseware courseware = new OracleCourseware();
				courseware.setId(rs.getString("id"));
				courseware.setName(rs.getString("name"));
				courseware.setAuthor(rs.getString("author"));
				if (rs.getInt("active") != 1)
					courseware.setActive(false);
				else
					courseware.setActive(true);
				courseware.setPublisher(rs.getString("publisher"));
				courseware.setFounderId(rs.getString("note"));
				courseware.setCoursewareType(rs.getString("courseware_type"));
				//courseware.setCoursewareDirId(rs.getString("courseware_dir"));
				coursewareList.add(courseware);
			}
		} catch (SQLException sqle) {
			throw new PlatformException("查询教学班的课件，读取数据库失败！");
		} finally {
			db.close(rs);
			rs = null;
		}
		return coursewareList;
	}
	/**@author zlw**/
	public List getOtherBooks(Page page,String id,List search,List order) throws PlatformException {
		List books = new ArrayList();
		dbpool db = new dbpool();
		String sql = " select t1.id,t1.teachbook_name, t1.publishhouse,  t1.maineditor, t1.isbn,t1.publish_date, t1.price,  t1.banben, t1.note   from entity_teachbook_info t1  where t1.id not in (select t2.teachbook_id from entity_teachbook_course t2  where t2.course_id ='"+id+"')";
		if(Conditions.convertToCondition(search, order).length()>0){
		String conn="and   "+Conditions.convertToCondition(search, order).substring(6,Conditions.convertToCondition(search, order).length());
		sql=sql+conn;}
		MyResultSet rs = null;
		if (page != null) {
			int pageint = page.getPageInt();
			int pagesize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageint, pagesize);
		} else {
			rs = db.executeQuery(sql);
		}

		try {
			
			while (rs != null && rs.next()) {
				OracleTeachBook book = new OracleTeachBook();
				book.setId(rs.getString("id"));
				book.setTeachbook_name(rs.getString("teachbook_name"));
				book.setPrice(rs.getString("price"));
				book.setPublishhouse(rs.getString("publishhouse"));
				book.setMaineditor(rs.getString("maineditor"));
				book.setIsbn(rs.getString("isbn"));
				book.setPublish_date(rs.getString("publish_date"));
//				book.setBanben(rs.getString("banben"));
				book.setNote(rs.getString("note"));
				 books.add(book);
				
			}
		} catch (SQLException sqle) {
			throw new PlatformException("查询教学班的课件，读取数据库失败！");
		} finally {
			db.close(rs);
			rs = null;
		}
		return books;
	}
	/**@author zlw*/
	public int getOtherBooksNum(String id,List list,List l) throws PlatformException {
		dbpool db = new dbpool();
		String sql = " select t1.id,t1.teachbook_name, t1.publishhouse,  t1.maineditor, t1.isbn,t1.publish_date, t1.price,  t1.banben, t1.note   from entity_teachbook_info t1  where t1.id not in (select t2.teachbook_id from entity_teachbook_course t2  where t2.course_id ='"+id+"')";
		if(Conditions.convertToCondition(list, l).length()>0){
		String conn="and   "+Conditions.convertToCondition(list, l).substring(6,Conditions.convertToCondition(list, l).length());
		sql=sql+conn;
		}
		
		int i = 0;

		
	   i = db.countselect(sql);
		
		return i;
	}
	
}
