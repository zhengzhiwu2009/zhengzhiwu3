/*
 * OracleRegister.java
 *
 * Created on 2005年5月25日, 下午5:22
 */

package com.whaty.platform.database.oracle.standard.entity.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleRegStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.activity.RegisterActivity;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.util.log.Log;

/**
 * 
 * @author Administrator
 */
public class OracleRegister extends RegisterActivity {

	// private String condition = "";

	private String sql_site = "(select id,name from entity_site_info) s";

	private String sql_grade = "(select id,name from entity_grade_info) g";

	private String sql_edu_type = "(select id,name from entity_edu_type) e";

	private String sql_major = "(select id,name from entity_major_info where id<>'0') m";

	/** Creates a new instance of OracleRegister */
	public OracleRegister() {
	}

	private String getRegStudentSql(String con, String orderby) {

		if (con == null)
			con = "";
		if (orderby == null)
			orderby = "";
		/*
		 * String sql = "select
		 * id,name,id_card,m_name,g_name,e_name,s_name,s_id,reg_no,se_name " +
		 * "from (select u.id as id,u.name as name,u.id_card as id_card,u.reg_no
		 * as reg_no,m.name as m_name,g.name as g_name,e.name as e_name,s.name
		 * as s_name,s.id as s_id,se.name as se_name " + " from (select
		 * id,name,major_id,grade_id,site,edu_type_id,id_card,reg_no from
		 * entity_user_info where isgraduated = '0' and status = '0000')
		 * u,entity_register_info r,entity_semester_info se" + "," + sql_major +
		 * "," + sql_grade + "," + sql_edu_type + "," + sql_site + " where
		 * u.major_id = m.id and u.grade_id = g.id and u.site = s.id and
		 * u.edu_type_id = e.id and u.id=r.user_id and r.semester_id=se.id " +
		 * con + ") " + orderby;
		 */
//		String sql = "select id,name,id_card,m_name,g_name,e_name,s_name,s_id,reg_no,phone,mobilephone,se_id,se_name,register_id from "
//				+ "(select u.id as id,u.name as name,u.id_card as id_card,u.reg_no as reg_no,u.phone as phone,u.mobilephone as mobilephone,m.name as m_name,"
//				+ "g.name as g_name,e.name as e_name,s.name as s_name,s.id as s_id,se.id as se_id,se.name as se_name,r.id as register_id from "
//				+ "(select u.id,u.name,u.major_id,u.grade_id,u.site_id,u.edutype_id,u.id_card,u.reg_no,eu.phone,eu.mobilephone from entity_student_info u,entity_usernormal_info eu where u.id=eu.id and u.isgraduated='0' and u.status='0') u,"
//				+ "entity_register_info r,entity_semester_info se,"
//				+ sql_major
//				+ ","
//				+ sql_grade
//				+ ","
//				+ sql_edu_type
//				+ ","
//				+ sql_site
//				+ " "
//				+ "where u.id=r.user_id and se.id=r.semester_id and u.major_id=m.id and u.grade_id=g.id and u.site_id=s.id and u.edutype_id=e.id and se.selected=1 "
//				+ con + ")" + orderby;
		String sql = "select id,name,id_card,m_name,g_name,e_name,s_name,s_id,reg_no,phone,mobilephone,se_id,se_name,register_id,site_id,grade_id,edutype_id,major_id from "
			+ "(select u.id as id,u.name as name,u.id_card as id_card,u.reg_no as reg_no,u.phone as phone,u.mobilephone as mobilephone,u.major_id as major_id,u.site_id as site_id,u.grade_id as grade_id,u.edutype_id as edutype_id,m.name as m_name,"
			+ "g.name as g_name,e.name as e_name,s.name as s_name,s.id as s_id,se.id as se_id,se.name as se_name,r.id as register_id from "
			+ "(select u.id,u.name,u.major_id,u.grade_id,u.site_id,u.edutype_id,u.id_card,u.reg_no,eu.phone,eu.mobilephone from entity_student_info u,entity_usernormal_info eu where u.id=eu.id and u.isgraduated='0' and u.status='0') u,"
			+ "entity_register_info r,entity_semester_info se,"
			+ sql_major
			+ ","
			+ sql_grade
			+ ","
			+ sql_edu_type
			+ ","
			+ sql_site
			+ " "
			+ "where u.id=r.user_id and se.id=r.semester_id and u.major_id=m.id and u.grade_id=g.id and u.site_id=s.id and u.edutype_id=e.id and se.selected=1 "
			+ con + ")" + orderby;
		return sql;
	}

	public int regStudents(String semester_id, List students) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_register_info(id,user_id,semester_id) select to_char(s_register_info_id.nextval),id,'"
				+ semester_id
				+ "' "
				+ " from (select id from (select id from entity_student_info where id in (";
		for (int i = 0; i < students.size(); i++) {
			// OracleStudent stu = (OracleStudent) students.get(i);
			String id = (String) students.get(i);
			if (i < students.size() - 1) {
				sql = sql + "'" + id + "',";
			} else {
				sql = sql
						+ "'"
						+ id
						+ "') minus (select user_id as id from entity_register_info where semester_id='"
						+ semester_id + "')))";
			}
		}
		// System.out.println("regStudents: " + sql);
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRegister.regStudents(String semester_id,List students) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getRegStudents(Page page, List searchproperty,
			List orderproperty) {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		String[] empty = new String[] {};
		MyResultSet rs = null;
		try {
			sql = getRegStudentSql(Conditions
					.convertToAndCondition(searchproperty), Conditions
					.convertToCondition(null, orderproperty));
			if (page != null) {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRegStudent student = new OracleRegStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				student.setReg_id(rs.getString("register_id"));
				student.setSemester_id(rs.getString("se_id"));
				student.setSemester_name(rs.getString("se_name"));
				/*
				 * student.setNickname(rs.getString("nickname"));
				 * student.setPassword(rs.getString("password"));
				 * normalInfo.setBirthday(rs.getString("birthday"));
				 * normalInfo.setDegree(rs.getString("degree"));
				 * normalInfo.setEdu(rs.getString("edu"));
				 * normalInfo.setEmail(rs.getString("").split(","));
				 * normalInfo.setFax(rs.getString("fax").split(","));
				 * normalInfo.setFolk(rs.getString("folk"));
				 * normalInfo.setGender(rs.getString("gender"));
				 * normalInfo.setGraduated_major(rs.getString("graduate_major"));
				 * normalInfo.setGraduated_sch(rs.getString("graduate_sch"));
				 * normalInfo.setGraduated_time(rs.getString("graduate_time"));
				 * Address homeaddress = new Address();
				 * homeaddress.setAddress(rs.getString("home_address"));
				 * homeaddress.setPostalcode(rs.getString("home_postalcode"));
				 * Address workaddress = new Address();
				 * workaddress.setAddress(rs.getString("work_address"));
				 * workaddress.setPostalcode(rs.getString("work_postalcode"));
				 * normalInfo.setHome_address(homeaddress);
				 * normalInfo.setHometown(rs.getString("hometown"));
				 * normalInfo.setIdcard(rs.getString("idcard"));
				 * normalInfo.setMobilePhone(rs.getString("mobilephone")
				 * .split(",")); normalInfo.setNote(rs.getString("note"));
				 */
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(empty);

				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(empty);
				/*
				 * normalInfo.setPosition(rs.getString("position"));
				 * normalInfo.setTitle(rs.getString("title"));
				 * normalInfo.setWork_address(workaddress);
				 * normalInfo.setWorkplace(rs.getString("workplace"));
				 * normalInfo.setZzmm(rs.getString("zzmm"));
				 */student.setNormalInfo(normalInfo);
				/*
				 * eduInfo.setClass_id(rs.getString("class_id"));
				 * eduInfo.setClass_name(rs.getString("class_name"));
				 * eduInfo.setEdutype_id(rs.getString("edutype_id"));
				 */
				eduInfo.setEdutype_name(rs.getString("e_name"));
				/*
				 * eduInfo.setEntrance_date(rs.getString("entrance_date"));
				 * eduInfo.setGrade_id(rs.getString("grade_id"));
				 */
				eduInfo.setGrade_name(rs.getString("g_name"));
				// eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("m_name"));
				// eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("s_id"));
				eduInfo.setSite_name(rs.getString("s_name"));
				/*
				 * eduInfo.setStatus(rs.getString("status"));
				 * eduInfo.setStudy_form(rs.getString("study_form"));
				 * eduInfo.setStudy_status(rs.getString("study_status"));
				 */
				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return studentlist;
	}

	public List getNoElectiveRegStudents(Page page, List searchproperty,
			List orderproperty, String semester_id) {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		String[] empty = new String[] {};
		MyResultSet rs = null;
		try {
			sql = "select id,name,id_card,m_name,g_name,e_name,s_name,s_id,reg_no,phone,mobilephone,se_id,se_name,register_id from "
					+ "(select u.id as id,u.name as name,u.id_card as id_card,u.reg_no as reg_no,u.phone as phone,u.mobilephone as mobilephone,m.name as m_name,"
					+ "g.name as g_name,e.name as e_name,s.name as s_name,s.id as s_id,se.id as se_id,se.name as se_name,r.id as register_id from "
					+ "(select u.id,u.name,u.major_id,u.grade_id,u.site_id,u.edutype_id,u.id_card,u.reg_no,eu.phone,eu.mobilephone from entity_student_info u,entity_usernormal_info eu where u.id=eu.id and u.isgraduated='0' and u.status='0') u,"
					+ "entity_register_info r,entity_semester_info se,"
					+ sql_major
					+ ","
					+ sql_grade
					+ ","
					+ sql_edu_type
					+ ","
					+ sql_site
					+ " "
					+ "where u.id=r.user_id and se.id=r.semester_id and u.major_id=m.id and u.grade_id=g.id and u.site_id=s.id and u.edutype_id=e.id and se.selected=1 "
					+ Conditions.convertToAndCondition(searchproperty)
					+ " and u.id not in (select distinct c.student_id from entity_course_active a, entity_teach_class b, entity_elective c where a.id = b.open_course_id and b.id = c.teachclass_id and a.semester_id = '"
					+ semester_id
					+ "'))"
					+ Conditions.convertToCondition(null, orderproperty);

			if (page != null) {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRegStudent student = new OracleRegStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				student.setReg_id(rs.getString("register_id"));
				student.setSemester_id(rs.getString("se_id"));
				student.setSemester_name(rs.getString("se_name"));
				/*
				 * student.setNickname(rs.getString("nickname"));
				 * student.setPassword(rs.getString("password"));
				 * normalInfo.setBirthday(rs.getString("birthday"));
				 * normalInfo.setDegree(rs.getString("degree"));
				 * normalInfo.setEdu(rs.getString("edu"));
				 * normalInfo.setEmail(rs.getString("").split(","));
				 * normalInfo.setFax(rs.getString("fax").split(","));
				 * normalInfo.setFolk(rs.getString("folk"));
				 * normalInfo.setGender(rs.getString("gender"));
				 * normalInfo.setGraduated_major(rs.getString("graduate_major"));
				 * normalInfo.setGraduated_sch(rs.getString("graduate_sch"));
				 * normalInfo.setGraduated_time(rs.getString("graduate_time"));
				 * Address homeaddress = new Address();
				 * homeaddress.setAddress(rs.getString("home_address"));
				 * homeaddress.setPostalcode(rs.getString("home_postalcode"));
				 * Address workaddress = new Address();
				 * workaddress.setAddress(rs.getString("work_address"));
				 * workaddress.setPostalcode(rs.getString("work_postalcode"));
				 * normalInfo.setHome_address(homeaddress);
				 * normalInfo.setHometown(rs.getString("hometown"));
				 * normalInfo.setIdcard(rs.getString("idcard"));
				 * normalInfo.setMobilePhone(rs.getString("mobilephone")
				 * .split(",")); normalInfo.setNote(rs.getString("note"));
				 */
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(empty);

				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(empty);
				/*
				 * normalInfo.setPosition(rs.getString("position"));
				 * normalInfo.setTitle(rs.getString("title"));
				 * normalInfo.setWork_address(workaddress);
				 * normalInfo.setWorkplace(rs.getString("workplace"));
				 * normalInfo.setZzmm(rs.getString("zzmm"));
				 */student.setNormalInfo(normalInfo);
				/*
				 * eduInfo.setClass_id(rs.getString("class_id"));
				 * eduInfo.setClass_name(rs.getString("class_name"));
				 * eduInfo.setEdutype_id(rs.getString("edutype_id"));
				 */
				eduInfo.setEdutype_name(rs.getString("e_name"));
				/*
				 * eduInfo.setEntrance_date(rs.getString("entrance_date"));
				 * eduInfo.setGrade_id(rs.getString("grade_id"));
				 */
				eduInfo.setGrade_name(rs.getString("g_name"));
				// eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("m_name"));
				// eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("s_id"));
				eduInfo.setSite_name(rs.getString("s_name"));
				/*
				 * eduInfo.setStatus(rs.getString("status"));
				 * eduInfo.setStudy_form(rs.getString("study_form"));
				 * eduInfo.setStudy_status(rs.getString("study_status"));
				 */
				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return studentlist;
	}

	public int getRegStudentsNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = getRegStudentSql(Conditions
				.convertToAndCondition(searchproperty), null);
		// System.out.println("Reg SQL:" + sql);
		int i = db.countselect(sql);
		return i;
	}

	public int unRegStudents(String semester_id, List students)
			throws PlatformException {
		// TODO Auto-generated method stub
		String err_info = "";
		dbpool db = new dbpool();
		int count = 0;
		for (int i = 0; i < students.size(); i++) {
			OracleStudent student = (OracleStudent) students.get(i);
			String student_id = student.getId();
			// 下面的sql是检查该学生在该学期下是否已经选课
			String check_sql = "select semester_id, student_id from (select id, semester_id from entity_course_active where semester_id='"
					+ semester_id
					+ "') A, (select id, open_course_id from entity_teacher_class) B,"
					+ " (select student_id, teacherclass_id from entity_elective where student_id='"
					+ student_id
					+ "') C where A.id = B.open_coourse_id and B.id = C.teacherclass_id";
			if (db.countselect(check_sql) > 0)
				err_info = err_info + "("
						+ student.getStudentInfo().getReg_no() + ")"
						+ student.getName() + ",";
			else {
				String unreg_sql = "delete from entity_register_info where semester_id='"
						+ semester_id + "' and user_id='" + student_id + "'";
				UserDeleteLog.setDebug("OracleRegister.unRegStudents(String semester_id,List students) SQL=" + unreg_sql + " DATE=" + new Date());
				count += db.executeUpdate(unreg_sql);
			}
		}
		if (err_info.length() > 0) {
			err_info = err_info.substring(0, err_info.length() - 1);
			throw new PlatformException(err_info + "已经在本学期选课.");
		}
		return count;
	}

	public int regStudentsBatch(String semester_id, String[] id,
			String[] major_id, String[] edutype_id, String[] grade_id)
			throws PlatformException {
		int first_index = 0;
		int last_index = 0;
		dbpool db = new dbpool();
		for (int i = 1; i < id.length; i++) {
			first_index = id[i].indexOf('|');
			last_index = id[i].lastIndexOf('|');
			grade_id[i] = id[i].substring(0, first_index);
			edutype_id[i] = id[i].substring(first_index + 1, last_index);
			major_id[i] = id[i].substring(last_index + 1);
		}
		String sql = "insert into entity_register_info(id,user_id,semester_id) select to_char(s_register_info_id.nextval),id,'"
				+ semester_id
				+ "' from (select id from entity_student_info where ";
		for (int i = 1; i < major_id.length; i++) {
			if (i != major_id.length - 1) {
				sql = sql + "(grade_id='" + grade_id[i] + "' and edutype_id='"
						+ edutype_id[i] + "' and major_id='" + major_id[i]
						+ "') or ";
			} else {
				sql = sql
						+ "(grade_id='"
						+ grade_id[i]
						+ "' and edutype_id='"
						+ edutype_id[i]
						+ "' and major_id='"
						+ major_id[i]
						+ "') minus (select user_id as id from entity_register_info where semester_id='"
						+ semester_id + "'))";
			}
		}
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRegister.regStudentsBatch(String semester_id,List students) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int regStudentsBatch(String semester_id, String[] id,
			String[] major_id, String[] edutype_id, String[] grade_id,
			List site_id) throws PlatformException {
		int first_index = 0;
		int last_index = 0;
		dbpool db = new dbpool();
		for (int i = 1; i < id.length; i++) {
			first_index = id[i].indexOf('|');
			last_index = id[i].lastIndexOf('|');
			grade_id[i] = id[i].substring(0, first_index);
			edutype_id[i] = id[i].substring(first_index + 1, last_index);
			major_id[i] = id[i].substring(last_index + 1);
		}
		String sql = "insert into entity_register_info(id,user_id,semester_id) select to_char(s_register_info_id.nextval),id,'"
				+ semester_id
				+ "' from (select id from entity_student_info where ";
		int site_size = 0;
		if (site_id != null) {
			site_size = site_id.size();
		}
		String site_str = "";
		for (int i = 0; i < site_size; i++) {
			site_str +="'"+ site_id.get(i) + "',";
		}
		site_str = site_str.substring(0, site_str.length() - 1);
		for (int i = 1; i < major_id.length; i++) {
			if (i != major_id.length - 1) {
				sql = sql + "(grade_id='" + grade_id[i] + "' and edutype_id='"
						+ edutype_id[i] + "' and major_id='" + major_id[i]
						+ "' and site_id in (" + site_str + ")) or ";
			} else {
				sql = sql
						+ "(grade_id='"
						+ grade_id[i]
						+ "' and edutype_id='"
						+ edutype_id[i]
						+ "' and major_id='"
						+ major_id[i]
						+ "' and site_id in ("
						+ site_str
						+ ") and status='0') minus (select user_id as id from entity_register_info where semester_id='"
						+ semester_id + "'))";
			}
		}
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRegister.regStudentsBatch(String semester_id, String[] id,String[] major_id, String[] edutype_id, String[] grade_id,List site_id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int unRegStudentsBatch(String semester_id, String[] id,
			String[] major_id, String[] edutype_id, String[] grade_id)
			throws PlatformException {
		int first_index = 0;
		int last_index = 0;
		dbpool db = new dbpool();
		for (int i = 1; i < id.length; i++) {
			first_index = id[i].indexOf('|');
			last_index = id[i].lastIndexOf('|');
			grade_id[i] = id[i].substring(0, first_index);
			edutype_id[i] = id[i].substring(first_index + 1, last_index);
			major_id[i] = id[i].substring(last_index + 1);
		}
		String sql = "delete from entity_register_info where semester_id='"
				+ semester_id
				+ "' and user_id in (select id from entity_student_info where ";
		for (int i = 1; i < major_id.length; i++) {
			if (i != major_id.length - 1) {
				sql = sql + "(grade_id='" + grade_id[i] + "' and edutype_id='"
						+ edutype_id[i] + "' and major_id='" + major_id[i]
						+ "') or ";
			} else {
				sql = sql + "(grade_id='" + grade_id[i] + "' and edutype_id='"
						+ edutype_id[i] + "' and major_id='" + major_id[i]
						+ "'))";
			}
		}
		sql += " and user_id not in(select a.user_id from entity_register_info a,entity_course_active b,entity_teach_class c,"
				+ "entity_elective d where a.semester_id='"
				+ semester_id
				+ "' and a.semester_id=b.semester_id and b.id=c.open_course_id "
				+ "and c.id=d.teachclass_id and a.user_id=d.student_id)";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRegister.unRegStudentsBatch(String semester_id, String[] id,String[] major_id, String[] edutype_id, String[] grade_id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int unRegStudents(String[] register_id) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from entity_register_info where id in (";
		String sql1 = "";
		for (int i = 0; i < register_id.length; i++) {
			sql1 = "select id from (select a.id from entity_register_info a,entity_course_active b,entity_teach_class c,"
					+ "entity_elective d where a.id='"
					+ register_id[i]
					+ "' and a.semester_id=b.semester_id and b.id=c.open_course_id "
					+ "and c.id=d.teachclass_id and a.user_id=d.student_id)";
			if (db.countselect(sql1) == 0) {
				if (i != register_id.length - 1) {
					sql = sql + "'" + register_id[i] + "',";
				} else {
					sql = sql + "'" + register_id[i] + "')";
				}
			}
		}
		// System.out.println("unRegStudents: " + sql);
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRegister.unRegStudents(String[] register_id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isRegStudent(String semester_id, String student_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select id from entity_register_info where semester_id = '"
				+ semester_id + "' and user_id = '" + student_id + "'";
		return db.countselect(sql);
	}

	public int regStudentActiveSemester(String regNo) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into entity_register_info (id,user_id,semester_id) "
				+ "select to_char(s_register_info_id.nextval),a.id,b.id from "
				+ "(select id from entity_student_info where reg_no='"
				+ regNo
				+ "') a,(select id from entity_semester_info where selected='1') b";
		int count = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRegister.regStudentsActiveSemester(String[] regNo) SQL=" + sql + " COUNT=" + count + " DATE=" + new Date());
		return count;
	}
}
