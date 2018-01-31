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
import com.whaty.platform.entity.activity.DegreeActivity;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleDegreeActivity extends DegreeActivity {
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

	public int setDegreeStatus(List studentList) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_student_info set degree_status = '1' where id in ('";
		for (int i = 0; i < studentList.size(); i++) {
			if (i < studentList.size() - 1) {
				sql += ((Student) studentList.get(i)).getId() + "','";
			} else {
				sql += ((Student) studentList.get(i)).getId() + "')";
			}
		}
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDegreeActivity.setDegreeStatus(List studentList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int setDegreeStatus(List studentList, List studentList1)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_student_info set degree_status = '0' where id in ('";
		for (int i = 0; i < studentList1.size(); i++) {
			if (i < studentList1.size() - 1) {
				sql += (String) studentList1.get(i) + "','";
			} else {
				sql += (String) studentList1.get(i) + "')";
			}
		}
		int suc = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDegreeActivity.setDegreeStatus(List studentList, List studentList1) SQL=" + sql + " COUNT=" + suc + " DATE=" + new Date());
		sql = "update entity_student_info set degree_status = '1' where id in ('";
		for (int i = 0; i < studentList.size(); i++) {
			if (i < studentList.size() - 1) {
				sql += ((Student) studentList.get(i)).getId() + "','";
			} else {
				sql += ((Student) studentList.get(i)).getId() + "')";
			}
		}
		suc = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDegreeActivity.setDegreeStatus(List studentList, List studentList1) SQL=" + sql + " COUNT=" + suc + " DATE=" + new Date());
		return suc;
	}

	public int cancelDegreeStatus(List studentList) throws PlatformException {
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
		UserUpdateLog.setDebug("OracleDegreeActivity.cancelDegreeStatus(List studentList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int getDegreeStudentsNum(List searchproperty) {
		dbpool db = new dbpool();
		int i = db.countselect(getStudentSql(Conditions
				.convertToAndCondition(searchproperty), null));
		return i;
	}

	public List checkDegree(List studentList, HashMap condition)
			throws PlatformException {
		if (condition.containsKey("CONA"))
			studentList = checkDegreeCONA(studentList, (String[]) condition
					.get("CONA"));
		return studentList;
	}

	private List checkDegreeCONA(List studentList, String[] cons)
			throws PlatformException {
		dbpool db = new dbpool();
		String avg_score = cons[0];
		List tmpStudentList = new ArrayList();
		MyResultSet rs = null;
		String sql = "select a.student_id from (select sum(total_score)/count(student_id) as avg_score,student_id "
				+ "from entity_elective group by student_id) a,(select id from entity_student_info where id in('";
		for (int i = 0; i < studentList.size(); i++) {
			if (i < studentList.size() - 1) {
				sql += ((Student) studentList.get(i)).getId() + "','";
			} else {
				sql += ((Student) studentList.get(i)).getId() + "')";
			}
		}
		sql += " and isgraduated='1') b where a.student_id=b.id and a.avg_score>="
				+ avg_score;
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				student.setId(rs.getString("student_id"));
				tmpStudentList.add(student);
			}
		} catch (SQLException e) {
		}
		return tmpStudentList;
	}

	public int setDegreeStatus(String major_id, String grade_id,
			String edutype_id) throws PlatformException {
		OracleDegreeCondition condition = new OracleDegreeCondition(major_id,
				grade_id, edutype_id);
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
			studentList = this.checkDegree(studentList, condition
					.getCondition());
			return setDegreeStatus(studentList);
		} else
			return 0;
	}

	public int setDegreed(List studentList, String operatorId,
			String operatorName) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_student_info set isdegreed = '1',degree_operate_time=sysdate,degree_operator='("
				+ operatorId + ")" + operatorName + "' where id in ('";
		for (int i = 0; i < studentList.size(); i++) {
			if (i < studentList.size() - 1) {
				sql += studentList.get(i) + "','";
			} else {
				sql += studentList.get(i) + "')";
			}
		}
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDegreeActivity.setDegreed(List studentList,String operatorId,String operatorName) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int cancleDegreed(List studentList) throws PlatformException {
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
		UserUpdateLog.setDebug("OracleDegreeActivity.cancleDegreed(List studentList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int setDegreeScore(List idList) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		String id = "";
		String degree_score = "";
		int count = 0;
		for (int i = 0; i < idList.size(); i++) {
			String[] id_no = (String[]) idList.get(i);
			id = id_no[0];
			degree_score = id_no[1];
			sql = "update entity_student_info set degree_score = '"
					+ degree_score + "' where id='" + id + "'";
			UserUpdateLog.setDebug("OracleDegreeActivity.setDegreeScore(List idList) SQL=" + sql + " DATE=" + new Date());
			count += db.executeUpdate(sql);
			
		}
		return count;
	}

	public int setDegreeRelease(List idList, List releaseList)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		String id = "";
		int count = 0;
		sql = "update entity_student_info set degree_release = '0' where id in (";
		for (int i = 0; i < idList.size(); i++) {
			id = (String) idList.get(i);
			sql += "'" + id + "',";
		}
		if (idList.size() > 0)
			sql = sql.substring(0, sql.length() - 1) + ")";
		else
			sql += ")";
		count += db.executeUpdate(sql);
		sql = "update entity_student_info set degree_release = '1' where id in (";
		for (int i = 0; i < releaseList.size(); i++) {
			id = (String) releaseList.get(i);
			sql += "'" + id + "',";
		}
		if (releaseList.size() > 0)
			sql = sql.substring(0, sql.length() - 1) + ")";
		else
			sql += ")";
		count += db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDegreeActivity.setDegreeRelease(List idList,List releaseList) SQL=" + sql + " DATE=" + new Date());
		return count;
	}

}
