/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleBasicUserList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.activity.GraduateActivity;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.util.Address;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

/**
 * @author chenjian
 * 
 */
public class OracleGraduateActivity extends GraduateActivity {

	private String sql_site = "(select id,name from entity_site_info) s";

	private String sql_grade = "(select id,name from entity_grade_info) g";

	private String sql_edu_type = "(select id,name from entity_edu_type) e";

	private String sql_major = "(select id,name from entity_major_info where id<>'0') m";

	private String getStudentSql(String con, String orderby) {
		String sql = "";
		if (con == null)
			con = "";
		if (orderby == null)
			orderby = "";
		sql = "select id,u_name,id_card,m_name,g_name,e_name,s_name,s_id,reg_no,address,company,degree,edu,gender,phone,title,work_place,zip_address,m_id,g_id,s_id,e_id from"
				+ " (select u.id as id,u.name as u_name,u.grade_id as g_id,u.major_id as m_id,u.edu_type_id as e_id,u.site as s_id,u.id_card as id_card,u.reg_no as reg_no,u.address as address,u.company as company,"
				+ "u.degree as degree,u.edu as edu,u.gender as gender,u.phone as phone,u.title as title,u.work_place as work_place,"
				+ "u.zip_address as zip_address,m.name as m_name,g.name as g_name,e.name as e_name,s.name as s_name from "
				+ "(select id,name,major_id,grade_id,site,edu_type_id,id_card,reg_no,address,company,degree,edu,gender,id_card,phone,title,work_place,zip_address "
				+ "from entity_student_info where status = '0000' "
				+ con
				+ ") u,"
				+ sql_major
				+ ","
				+ sql_grade
				+ ","
				+ sql_edu_type
				+ ","
				+ sql_site
				+ " where "
				+ "u.major_id = m.id and u.grade_id = g.id and u.site = s.id and u.edu_type_id = e.id) "
				+ orderby;

		return sql;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.GraduateActivity#checkGraduate(java.lang.String)
	 */
	public List checkGraduate(String graduateType) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.GraduateActivity#setGraduateStatus(java.lang.String)
	 */
	public void setGraduateStatus(String graduateType) throws PlatformException {
	}

	public int setGraduateStatus(List studentList) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_student_info set graduate_status = '1' where id in ('";
		for (int i = 0; i < studentList.size(); i++) {
			if (i < studentList.size() - 1) {
				sql += ((Student) studentList.get(i)).getId() + "','";
			} else {
				sql += ((Student) studentList.get(i)).getId() + "')";
			}
		}
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleGraduateActivity.setGraduateStatus(List studentList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int cancelGraduateStatus(List studentList) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_student_info set isgraduated = '0' where id in ('";
		for (int i = 0; i < studentList.size(); i++) {
			if (i < studentList.size() - 1) {
				sql += ((Student) studentList.get(i)).getId() + "','";
			} else {
				sql += ((Student) studentList.get(i)).getId() + "')";
			}
		}
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleGraduateActivity.cancelGraduateStatus(List studentList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List checkByCredit(String credit) throws PlatformException {
		dbpool db = new dbpool();
		ArrayList filterStuList = new ArrayList();
		MyResultSet rs = null;
		String sql = "select student_id,credit from (select sum(credit) as credit,student_id as student_id from "
				+ "(select g.student_id,g.course_id,g.credit from (select a.student_id as student_id,c.id as course_id,"
				+ "c.credit as credit from entity_elective a,entity_course_active b,entity_course_info c where a.open_course_id = b.id and b.course_id = c.id "
				+ "and (a.score >= 60 or a.score < 60 and to_number(a.expend_score)>=60)) g where g.student_id in (";
		for (int i = 0; i < this.getStudentList().size(); i++) {
			if (i < this.getStudentList().size() - 1) {
				sql += ((Student) this.getStudentList().get(i)).getId() + ",";
			} else {
				sql += ((Student) this.getStudentList().get(i)).getId() + ")";
			}
		}
		sql += " group by student_id) where credit >= " + credit;
		try {
			EntityLog.setDebug("OracleGraduateActivity@Method:checkByCredit()="+sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				student.setId(rs.getString("student_id"));
				filterStuList.add(student);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return filterStuList;
	}

	public List checkByCompulsory(String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		dbpool db = new dbpool();
		ArrayList filterStuList = new ArrayList();
		MyResultSet rs = null;
		String sql = "select student_id,credit from (select sum(credit) as credit,student_id as student_id from "
				+ "(select g.student_id,g.course_id,g.credit from (select a.student_id as student_id,c.id as course_id,c.credit as credit "
				+ "from entity_elective a,entity_course_active b,entity_course_info c where a.open_course_id = b.id and b.course_id = c.id and a.score >= 60 "
				+ "union select d.student_id as student_id,f.id as course_id,f.credit as credit from entity_elective d,entity_course_active e,entity_course_info f "
				+ "where d.open_course_id = e.id and e.course_id = f.id and d.score < 60 and to_number(d.expend_score)>=60) g "
				+ "where g.student_id in ( ";
		for (int i = 0; i < this.getStudentList().size(); i++) {
			if (i < this.getStudentList().size() - 1) {
				sql += ((Student) this.getStudentList().get(i)).getId() + ",";
			} else {
				sql += ((Student) this.getStudentList().get(i)).getId() + ")";
			}
		}
		sql += "and g.course_id in (select y.course_id from entity_teachplan_info x,entity_teachplan_course y where x.major_id = '"
				+ major_id
				+ "' and x.edutype_id = '"
				+ edu_type_id
				+ "' and x.grade_id = '"
				+ grade_id
				+ "' and instr(y.type,'±ØÐÞ¿Î')>0) and x.id = y.teachplan_id) group by student_id"
				+ " having count(*) >= (select count(y.id) from entity_teachplan_info x,entity_teachplan_course y where x.major_id = '"
				+ major_id
				+ "' and x.edutype_id = '"
				+ edu_type_id
				+ "' and x.grade_id = '"
				+ grade_id
				+ "' and instr(y.type,'±ØÐÞ¿Î')>0) and x.id = y.teachplan_id) ";

		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				student.setId(rs.getString("student_id"));
				filterStuList.add(student);
			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return filterStuList;
	}

	public int getGraduateStudentsNum(List searchproperty) {
		dbpool db = new dbpool();
		int i = db.countselect(getStudentSql(Conditions
				.convertToAndCondition(searchproperty), null));
		return i;
	}

	public List getGraduateStudents(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = getStudentSql(Conditions
					.convertToAndCondition(searchproperty), Conditions
					.convertToCondition(null, orderproperty));
			rs = db.execute_oracle_page(sql, page.getPageInt(), page
					.getPageSize());
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
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
				eduInfo.setIsgraduated(rs.getString("isgraduated"));
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

	public void setGraduated(String graduateType) throws PlatformException {
		// TODO Auto-generated method stub

	}

	public List checkGraduate(List studentList, HashMap condition)
			throws PlatformException {
		if (condition.containsKey("CONA"))
			studentList = checkGraduateCONA(studentList, (String[]) condition
					.get("CONA"));
		if (condition.containsKey("CONB"))
			studentList = checkGraduateCONB(studentList, (String[]) condition
					.get("CONB"));
		if (condition.containsKey("CONC"))
			studentList = checkGraduateCONC(studentList, (String[]) condition
					.get("CONC"));
		return studentList;
	}

	private List checkGraduateCONA(List studentList, String[] cons)
			throws PlatformException {
		dbpool db = new dbpool();
		List tmpStudentList = new ArrayList();
		String sql = "";
		String studentId = "";
		for (int i = 0; i < studentList.size(); i++) {
			studentId = ((Student) studentList.get(i)).getId();
			sql = "select id from (select count(course_id) as course_num,'"
					+ studentId
					+ "' as id from(select b.course_id from entity_teachplan_info a,entity_teachplan_course b,entity_student_info d where a.id=b.teachplan_id and a.major_id=d.major_id and a.grade_id=d.grade_id and a.edutype_id=d.edutype_id and d.id='"
					+ studentId
					+ "' union select course_id from entity_singleteachplan_course where id='"
					+ studentId
					+ "' and status='1' minus select course_id from entity_singleteachplan_course where id='"
					+ studentId
					+ "' and status='0')) a,(select count(a.course_id) as course_num from (select distinct b.course_id from entity_teachplan_info a,entity_teachplan_course b,entity_student_info d where a.id=b.teachplan_id and a.major_id=d.major_id and a.grade_id=d.grade_id and a.edutype_id=d.edutype_id and d.id='"
					+ studentId
					+ "' union select course_id from entity_singleteachplan_course where id='"
					+ studentId
					+ "' and status='1' minus select course_id from entity_singleteachplan_course where id='"
					+ studentId
					+ "' and status='0') a, (select distinct c.course_id from entity_elective a,entity_teach_class b,entity_course_active c where a.teachclass_id=b.id and b.open_course_id=c.id and a.student_id='"
					+ studentId
					+ "') b where a.course_id=b.course_id) b where a.course_num=b.course_num";
			if (db.countselect(sql) > 0) {
				OracleStudent student = new OracleStudent();
				student.setId(studentId);
				tmpStudentList.add(student);
			}
		}
		return tmpStudentList;
	}

	private List checkGraduateCONB(List studentList, String[] cons)
			throws PlatformException {
		dbpool db = new dbpool();
		List tmpStudentList = new ArrayList();
		String credit = cons[0];
		String sql = "";
		String studentId = "";
		for (int i = 0; i < studentList.size(); i++) {
			studentId = ((Student) studentList.get(i)).getId();
			sql = "select '"
					+ studentId
					+ "' as id from (select sum(a.credit) as credit from (select distinct b.course_id,b.credit from entity_teachplan_info a,entity_teachplan_course b,entity_student_info d where a.id=b.teachplan_id and a.major_id=d.major_id and a.grade_id=d.grade_id and a.edutype_id=d.edutype_id and d.id='"
					+ studentId
					+ "' union select course_id,credit from entity_singleteachplan_course where id='"
					+ studentId
					+ "' and status='1' minus select course_id,credit from entity_singleteachplan_course where id='"
					+ studentId
					+ "' and status='0') a,(select distinct c.course_id from entity_elective a,entity_teach_class b,entity_course_active c where a.teachclass_id=b.id and b.open_course_id=c.id and a.student_id='"
					+ studentId
					+ "' and a.total_score>=60) b where a.course_id=b.course_id) a, (select sum(a.credit) as credit from entity_course_info a, (select distinct c.course_id from entity_elective a,entity_teach_class b,entity_course_active c where a.teachclass_id=b.id and a.total_score>=60 and b.open_course_id=c.id and a.student_id='"
					+ studentId
					+ "' and c.course_id not in (select distinct b.course_id from entity_teachplan_info a,entity_teachplan_course b,entity_student_info d where a.id=b.teachplan_id and a.major_id=d.major_id and a.grade_id=d.grade_id and a.edutype_id=d.edutype_id and d.id='"
					+ studentId
					+ "' union select course_id from entity_singleteachplan_course where id='"
					+ studentId
					+ "' and status='1' minus select course_id from entity_singleteachplan_course where id='"
					+ studentId
					+ "' and status='0')) b where a.id=b.course_id) b where (nvl(a.credit,0)+nvl(b.credit,0))>="
					+ credit;
			if (db.countselect(sql) > 0) {
				OracleStudent student = new OracleStudent();
				student.setId(studentId);
				tmpStudentList.add(student);
			}
		}
		return tmpStudentList;
	}

	private List checkGraduateCONC(List studentList, String[] cons)
			throws PlatformException {
		dbpool db = new dbpool();
		List tmpStudentList = new ArrayList();
		MyResultSet rs = null;
		String sql = "select id from entity_student_info where reg_no in(select reg_no from entity_uniteexam_score "
				+ "where score>=60) and reg_no not in(select reg_no from entity_uniteexam_score where score<60) and "
				+ "reg_no in (select a.reg_no from (select count(b.course_id) as course_num,b.reg_no from "
				+ "entity_uniteexam_course a,entity_uniteexam_score b where a.id=b.course_id group by b.reg_no) a,"
				+ "(select count(id) as course_num from entity_uniteexam_course) b where a.course_num=b.course_num) "
				+ "and id in ('";
		for (int i = 0; i < studentList.size(); i++) {
			if (i < studentList.size() - 1) {
				sql += ((Student) studentList.get(i)).getId() + "','";
			} else {
				sql += ((Student) studentList.get(i)).getId() + "')";
			}
		}
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				student.setId(rs.getString("id"));
				tmpStudentList.add(student);
			}
		} catch (SQLException e) {
		}
		return tmpStudentList;
	}

	public int setGraduateStatus(String major_id, String grade_id,
			String edutype_id) throws PlatformException {
		OracleGraduateCondition condition = new OracleGraduateCondition(
				major_id, grade_id, edutype_id);
		OracleBasicUserList userList = new OracleBasicUserList();
		List searchProperties = new ArrayList();
		if (grade_id != null && !grade_id.equals("")
				&& !grade_id.equals("null")) {
			searchProperties
					.add(new SearchProperty("u.grade_id", grade_id, "="));
		}
		if (edutype_id != null && !edutype_id.equals("")
				&& !edutype_id.equals("null")) {
			searchProperties.add(new SearchProperty("u.edutype_id", edutype_id,
					"="));
		}
		if (major_id != null && !major_id.equals("")
				&& !major_id.equals("null")) {
			searchProperties
					.add(new SearchProperty("u.major_id", major_id, "="));
		}
		List studentList = userList.getAllStudents(searchProperties, null);
		if (studentList != null && studentList.size() > 0) {
			studentList = this.checkGraduate(studentList, condition
					.getCondition());
			return setGraduateStatus(studentList);
		} else
			return 0;
	}

	public int setGraduated(List studentList, String operatorId,
			String operatorName) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_student_info set isgraduated = '1',graduate_operate_time=sysdate,graduate_operator='("
				+ operatorId + ")" + operatorName + "' where id in ('";
		for (int i = 0; i < studentList.size(); i++) {
			if (i < studentList.size() - 1) {
				sql += studentList.get(i) + "','";
			} else {
				sql += studentList.get(i) + "')";
			}
		}
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleGraduateActivity.setGraduated(List studentList,String operatorId,String operatorName) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int cancleGraduated(List studentList) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_student_info set isgraduated = '0',graduate_operate_time='',graduate_operator='' where id in ('";
		for (int i = 0; i < studentList.size(); i++) {
			if (i < studentList.size() - 1) {
				sql += studentList.get(i) + "','";
			} else {
				sql += studentList.get(i) + "')";
			}
		}
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleGraduateActivity.cancleGraduated(List studentList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int setGraduateNo(List idList) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		String id = "";
		String graduate_no = "";
		int count = 0;
		for (int i = 0; i < idList.size(); i++) {
			String[] id_no = (String[]) idList.get(i);
			id = id_no[0];
			graduate_no = id_no[1];
			sql = "update entity_student_info set graduate_no = '"
					+ graduate_no + "' where id='" + id + "'";
			UserUpdateLog.setDebug("OracleGraduateActivity.setGraduateNo(List idList) SQL=" + sql + " DATE=" + new Date());
			count += db.executeUpdate(sql);
		}
		return count;
	}

}
