package com.whaty.platform.database.oracle.standard.entity.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStatusChangedStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.activity.ChangeMajorStudent;
import com.whaty.platform.entity.activity.StatusChangeActivity;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleStatusChangeActivity extends StatusChangeActivity {
	
	public int changeStatus(String statusChangeType) throws PlatformException {
		if (statusChangeType.equals("ABORT")) {
			return this.abort();
		}
		if (statusChangeType.equals("SUSPEND")) {
			return this.suspendSchool();
		}

		return 0;
	}

	public int getStatusChangeStudentsNum(String statusChangeType)
			throws PlatformException {
		if (statusChangeType.equals("ABORT")) {
			return this.abortNum();
		} else if (statusChangeType.equals("SUSPEND")) {
			return this.suspendNum();
		} else
			return 0;
	}

	public List getStatusChangeStudents(String statusChangeType)
			throws PlatformException {
		if (statusChangeType.equals("ABORT")) {
			return this.abortList();
		} else if (statusChangeType.equals("SUSPEND")) {
			return this.suspendSchoolList();
		} else
			return null;
	}

	public List getStatusChangeStudents(Page page, String statusChangeType)
			throws PlatformException {
		if (statusChangeType.equals("ABORT")) {
			return this.abortList(page);
		} else if (statusChangeType.equals("SUSPEND")) {
			return this.suspendSchoolList(page);
		} else
			return null;
	}

	public int getStatusChangeStudentRecodsNum(String statusChangeType)
			throws PlatformException {
		if (statusChangeType.equals("ABORT")) {
			return this.abortRecordsNum();
		} else if (statusChangeType.equals("SUSPEND")) {
			return this.suspendRecordsNum();
		} else
			return 0;
	}

	public int getStatusChangeStudentRecodsNum(String statusChangeType,
			String siteId, String regNo) throws PlatformException {
		if (statusChangeType.equals("ABORT")) {
			return this.abortRecordsNum(siteId, regNo);
		} else if (statusChangeType.equals("SUSPEND")) {
			return this.suspendRecordsNum(siteId, regNo);
		} else
			return 0;
	}

	public List getStatusChangeStudentRecods(Page page, String statusChangeType)
			throws PlatformException {
		if (statusChangeType.equals("ABORT")) {
			return this.abortRecordsList(page);
		} else if (statusChangeType.equals("SUSPEND")) {
			return this.suspendRecordsList(page);
		} else
			return null;
	}

	public List getStatusChangeStudentRecods(Page page,
			String statusChangeType, String siteId, String regNo)
			throws PlatformException {
		if (statusChangeType.equals("ABORT")) {
			return this.abortRecordsList(page, siteId, regNo);
		} else if (statusChangeType.equals("SUSPEND")) {
			return this.suspendRecordsList(page, siteId, regNo);
		} else
			return null;
	}

	private int abort() throws PlatformException {
		dbpool db = new dbpool();
		String upSql = "";
		String insSql = "";
		ArrayList sqlList = new ArrayList();
		List studentList = this.getStudentList();
		for (int i = 0; i < studentList.size(); i++) {
			Student student = (Student) studentList.get(i);
			upSql = "update entity_student_info set status='1111' where id= '"
					+ student.getId() + "'";
			insSql = "insert into entity_abort_school (id,user_id,s_date) values(to_char(s_abort_school_id.nextval),'"
					+ student.getId() + "',sysdate)";
			UserUpdateLog.setDebug("OracleStatusChangeActivity.abort() SQL=" + upSql + " DATE=" + new Date());
			UserAddLog.setDebug("OracleStatusChangeActivity.abort() SQL=" + insSql + " DATE=" + new Date());
			sqlList.add(upSql);
			sqlList.add(insSql);
		}
		return db.executeUpdateBatch(sqlList);
	}

	private List abortList() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		try {
			sql = "select a.id,a.reg_no,a.name as u_name,b.name as m_name,f.name as g_name,g.name as e_name,c.name as s_name ,to_char(d.s_date,'yyyy-mm-dd') as a_date from"
					+ " entity_student_info a,entity_major_info b,entity_site_info c,entity_abort_school d,entity_grade_info f,entity_edu_type g where "
					+ "d.user_id = a.id and a.major_id = b.id and a.grade_id=f.id and a.edutype_id=g.id and a.site_id = c.id and a.status = '1111'";
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("e_name"));
				eduInfo.setGrade_name(rs.getString("g_name"));
				eduInfo.setMajor_name(rs.getString("m_name"));
				eduInfo.setSite_name(rs.getString("s_name"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;
	}

	private List abortList(Page page) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		try {
			/*
			 * sql = "select id,reg_no,u_name,m_name,g_name,e_name,s_name,a_date
			 * from (" + "select a.id,a.reg_no,a.name as u_name,b.name as
			 * m_name,f.name as g_name,g.name as e_name,c.name as s_name
			 * ,to_char(d.s_date,'yyyy-mm-dd') as a_date from" + "
			 * entity_student_info a,entity_major_info b,entity_site_info
			 * c,entity_abort_school d,entity_grade_info f,entity_edu_type g
			 * where " + "d.user_id = a.id and a.major_id = b.id and
			 * a.grade_id=f.id and a.edutype_id=g.id and a.site_id = c.id and
			 * a.status = '1111')";
			 */
			sql = "select id,reg_no,u_name,m_name,g_name,e_name,s_name,a_date from (select a.id as id,a.reg_no as reg_no,a.name as u_name,b.name as m_name,f.name as g_name,g.name as e_name,c.name as s_name ,to_char(d.s_date,'yyyy-mm-dd') as a_date from"
					+ " entity_student_info a,entity_major_info b,entity_site_info c,entity_abort_school d,entity_grade_info f,entity_edu_type g where "
					+ "d.user_id = a.id and a.major_id = b.id and a.grade_id=f.id and a.edutype_id=g.id and a.site_id = c.id and a.status = '1111')";

			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());

			while (rs != null && rs.next()) {
				OracleStatusChangedStudent student = new OracleStatusChangedStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("e_name"));
				eduInfo.setGrade_name(rs.getString("g_name"));
				eduInfo.setMajor_name(rs.getString("m_name"));
				eduInfo.setSite_name(rs.getString("s_name"));
				student.setStudentInfo(eduInfo);
				student.setStatusChange_date(rs.getString("a_date"));

				stuList.add(student);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;
	}

	private List abortRecordsList(Page page) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		try {
			sql = "select id,reg_no,u_name,g_name,m_name,e_name,s_name,eas_id,s_date,e_date from "
					+ "(select esi.id as id,esi.reg_no as reg_no,esi.name as u_name,egi.name as g_name,"
					+ "emi.name as m_name,eet.name as e_name,es.name as s_name,eas.id as eas_id,"
					+ "to_char(s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,"
					+ "to_char(e_date,'yyyy-mm-dd hh24:mi:ss') as e_date  from entity_student_info esi,"
					+ "entity_major_info emi,entity_site_info es,entity_grade_info egi,entity_edu_type eet,"
					+ "entity_abort_school eas where eas.user_id=esi.id and egi.id=esi.grade_id and "
					+ "emi.id=esi.major_id and eet.id=esi.edutype_id and es.id=esi.site_id)";

			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());

			while (rs != null && rs.next()) {
				OracleStatusChangedStudent student = new OracleStatusChangedStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("e_name"));
				eduInfo.setGrade_name(rs.getString("g_name"));
				eduInfo.setMajor_name(rs.getString("m_name"));
				eduInfo.setSite_name(rs.getString("s_name"));
				student.setStudentInfo(eduInfo);
				student.setStatusChange_date(rs.getString("s_date"));
				student.setStatusChangeEndDate(rs.getString("e_date"));
				student.setSsid(rs.getString("eas_id"));

				stuList.add(student);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;
	}

	private List abortRecordsList(Page page, String siteId, String regNo) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		String site_con = "";
		String reg_con = "";
		if (siteId != null && !siteId.trim().equals(""))
			site_con = " and es.id='" + siteId + "' ";
		if (regNo != null && !regNo.trim().equals(""))
			reg_con = " and esi.reg_no like '%" + regNo.trim() + "%' ";

		try {
			sql = "select id,reg_no,u_name,g_name,m_name,e_name,s_name,eas_id,s_date,e_date from "
					+ "(select esi.id as id,esi.reg_no as reg_no,esi.name as u_name,egi.name as g_name,"
					+ "emi.name as m_name,eet.name as e_name,es.name as s_name,eas.id as eas_id,"
					+ "to_char(s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,"
					+ "to_char(e_date,'yyyy-mm-dd hh24:mi:ss') as e_date  from entity_student_info esi,"
					+ "entity_major_info emi,entity_site_info es,entity_grade_info egi,entity_edu_type eet,"
					+ "entity_abort_school eas where eas.user_id=esi.id "
					+ site_con
					+ reg_con
					+ " and egi.id=esi.grade_id and "
					+ "emi.id=esi.major_id and eet.id=esi.edutype_id and es.id=esi.site_id) order by s_date desc";

			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());

			while (rs != null && rs.next()) {
				OracleStatusChangedStudent student = new OracleStatusChangedStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("e_name"));
				eduInfo.setGrade_name(rs.getString("g_name"));
				eduInfo.setMajor_name(rs.getString("m_name"));
				eduInfo.setSite_name(rs.getString("s_name"));
				student.setStudentInfo(eduInfo);
				student.setStatusChange_date(rs.getString("s_date"));
				student.setStatusChangeEndDate(rs.getString("e_date"));
				student.setSsid(rs.getString("eas_id"));

				stuList.add(student);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;
	}

	private int abortNum() {
		dbpool db = new dbpool();
		String sql = "select reg_no from (select a.id,a.reg_no as reg_no,a.name as u_name,b.name as m_name,f.name as g_name,g.name as e_name,c.name as s_name ,to_char(d.s_date,'yyyy-mm-dd') as a_date from"
				+ " entity_student_info a,entity_major_info b,entity_site_info c,entity_abort_school d,entity_grade_info f,entity_edu_type g where "
				+ "d.user_id = a.id and a.major_id = b.id and a.grade_id=f.id and a.edutype_id=g.id and a.site_id = c.id and a.status = '1111')";
		// System.out.print("abortNum:" + sql);
		return db.countselect(sql);
	}

	private int abortRecordsNum() {
		dbpool db = new dbpool();
		String sql = "select id from (select id,user_id,to_char(s_date,'yyyy-mm-dd h24:mi:ss') as s_date, "
				+ "to_char(e_date,'yyyy-mm-dd h24:mi:ss') as e_date,note from entity_abort_school)";

		return db.countselect(sql);
	}

	private int abortRecordsNum(String siteId, String regNo) {
		dbpool db = new dbpool();
		String sql;
		String site_con = "";
		String reg_con = "";
		if (siteId != null && !siteId.trim().equals(""))
			site_con = " and es.id='" + siteId + "' ";
		if (regNo != null && !regNo.trim().equals(""))
			reg_con = " and esi.reg_no like '%" + regNo.trim() + "%' ";
		sql = "select id,reg_no,u_name,g_name,m_name,e_name,s_name,eas_id,s_date,e_date from "
				+ "(select esi.id as id,esi.reg_no as reg_no,esi.name as u_name,egi.name as g_name,"
				+ "emi.name as m_name,eet.name as e_name,es.name as s_name,eas.id as eas_id,"
				+ "to_char(s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,"
				+ "to_char(e_date,'yyyy-mm-dd hh24:mi:ss') as e_date  from entity_student_info esi,"
				+ "entity_major_info emi,entity_site_info es,entity_grade_info egi,entity_edu_type eet,"
				+ "entity_abort_school eas where eas.user_id=esi.id "
				+ site_con
				+ reg_con
				+ " and egi.id=esi.grade_id and "
				+ "emi.id=esi.major_id and eet.id=esi.edutype_id and es.id=esi.site_id)";

		return db.countselect(sql);
	}

	private int suspendSchool() throws PlatformException {
		dbpool db = new dbpool();
		String upSql = "";
		String insSql = "";
		ArrayList sqlList = new ArrayList();
		List studentList = this.getStudentList();
		for (int i = 0; i < studentList.size(); i++) {
			Student student = (Student) studentList.get(i);

			upSql = "update entity_student_info set status='0001' where id='"
					+ student.getId() + "'";
			insSql = "insert into entity_suspend_school (id,user_id,s_date) values(to_char(s_suspend_school_id.nextval),'"
					+ student.getId() + "',sysdate)";
			UserUpdateLog.setDebug("OracleStatusChangeActivity.suspendSchool() SQL=" + upSql + " DATE=" + new Date());
			UserAddLog.setDebug("OracleStatusChangeActivity.suspendSchool() SQL=" + insSql + " DATE=" + new Date());
			sqlList.add(upSql);
			sqlList.add(insSql);
		}
		return db.executeUpdateBatch(sqlList);
	}

	private List suspendSchoolList() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		try {
			sql = "select a.id,a.reg_no,a.name as u_name,b.name as m_name,f.name as g_name,g.name as e_name,c.name as s_name ,to_char(d.s_date,'yyyy-mm-dd') as a_date,"
					+ "b.id as major_id,c.id as site_id,f.id as grade_id,g.id as edutype_id from"
					+ " entity_student_info a,entity_major_info b,entity_site_info c,entity_suspend_school d,entity_grade_info f,entity_edu_type g where "
					+ "d.user_id = a.id and a.major_id = b.id and a.grade_id=f.id and a.edutype_id=g.id and a.site_id = c.id and a.status = '0001'";
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStatusChangedStudent student = new OracleStatusChangedStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("e_name"));
				eduInfo.setGrade_name(rs.getString("g_name"));
				eduInfo.setMajor_name(rs.getString("m_name"));
				eduInfo.setSite_name(rs.getString("s_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				student.setStudentInfo(eduInfo);
				student.setStatusChange_date(rs.getString("a_date"));
				stuList.add(student);

			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;

	}

	private List suspendRecordsList(Page page) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		try {
			sql = "select id,reg_no,u_name,g_name,m_name,e_name,s_name,ess_id,s_date,e_date from "
					+ "(select esi.id as id,esi.reg_no as reg_no,esi.name as u_name,egi.name as g_name,"
					+ "emi.name as m_name,eet.name as e_name,es.name as s_name,ess.id as ess_id,"
					+ "to_char(s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,"
					+ "to_char(e_date,'yyyy-mm-dd hh24:mi:ss') as e_date  from entity_student_info esi,"
					+ "entity_major_info emi,entity_site_info es,entity_grade_info egi,entity_edu_type eet,"
					+ "entity_suspend_school ess where ess.user_id=esi.id and egi.id=esi.grade_id and "
					+ "emi.id=esi.major_id and eet.id=esi.edutype_id and es.id=esi.site_id)";

			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());

			while (rs != null && rs.next()) {
				OracleStatusChangedStudent student = new OracleStatusChangedStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("e_name"));
				eduInfo.setGrade_name(rs.getString("g_name"));
				eduInfo.setMajor_name(rs.getString("m_name"));
				eduInfo.setSite_name(rs.getString("s_name"));
				student.setStudentInfo(eduInfo);
				student.setStatusChange_date(rs.getString("s_date"));
				student.setStatusChangeEndDate(rs.getString("e_date"));
				student.setSsid(rs.getString("ess_id"));

				stuList.add(student);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;
	}

	private List suspendRecordsList(Page page, String siteId, String regNo) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		String site_con = "";
		String reg_con = "";
		if (siteId != null && !siteId.trim().equals(""))
			site_con = " and es.id='" + siteId + "' ";
		if (regNo != null && !regNo.trim().equals(""))
			reg_con = " and esi.reg_no like '%" + regNo.trim() + "%' ";

		try {
			sql = "select id,reg_no,u_name,g_name,m_name,e_name,s_name,ess_id,s_date,e_date from "
					+ "(select esi.id as id,esi.reg_no as reg_no,esi.name as u_name,egi.name as g_name,"
					+ "emi.name as m_name,eet.name as e_name,es.name as s_name,ess.id as ess_id,"
					+ "to_char(s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,"
					+ "to_char(e_date,'yyyy-mm-dd hh24:mi:ss') as e_date  from entity_student_info esi,"
					+ "entity_major_info emi,entity_site_info es,entity_grade_info egi,entity_edu_type eet,"
					+ "entity_suspend_school ess where ess.user_id=esi.id "
					+ site_con
					+ reg_con
					+ " and egi.id=esi.grade_id and "
					+ "emi.id=esi.major_id and eet.id=esi.edutype_id and es.id=esi.site_id) order by s_date desc";

			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());

			while (rs != null && rs.next()) {
				OracleStatusChangedStudent student = new OracleStatusChangedStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("e_name"));
				eduInfo.setGrade_name(rs.getString("g_name"));
				eduInfo.setMajor_name(rs.getString("m_name"));
				eduInfo.setSite_name(rs.getString("s_name"));
				student.setStudentInfo(eduInfo);
				student.setStatusChange_date(rs.getString("s_date"));
				student.setStatusChangeEndDate(rs.getString("e_date"));
				student.setSsid(rs.getString("ess_id"));

				stuList.add(student);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;
	}

	private List suspendSchoolList(Page page) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		try {
			sql = "select a.id,a.reg_no,a.name as u_name,b.name as m_name,f.name as g_name,g.name as e_name,c.name as s_name ,to_char(d.s_date,'yyyy-mm-dd') as a_date,"
					+ "b.id as major_id,c.id as site_id,f.id as grade_id,g.id as edutype_id from"
					+ " entity_student_info a,entity_major_info b,entity_site_info c,entity_suspend_school d,entity_grade_info f,entity_edu_type g where "
					+ "d.user_id = a.id and a.major_id = b.id and a.grade_id=f.id and a.edutype_id=g.id and a.site_id = c.id and a.status = '0001'";

			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			while (rs != null && rs.next()) {
				OracleStatusChangedStudent student = new OracleStatusChangedStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("e_name"));
				eduInfo.setGrade_name(rs.getString("g_name"));
				eduInfo.setMajor_name(rs.getString("m_name"));
				eduInfo.setSite_name(rs.getString("s_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				student.setStudentInfo(eduInfo);
				student.setStatusChange_date(rs.getString("a_date"));
				stuList.add(student);

			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;

	}

	private int suspendNum() {
		dbpool db = new dbpool();
		String sql = "select reg_no from (select a.id,a.reg_no as reg_no,a.name as u_name,b.name as m_name,f.name as g_name,g.name as e_name,c.name as s_name ,to_char(d.s_date,'yyyy-mm-dd') as a_date,"
				+ "b.id as major_id,c.id as site_id,f.id as grade_id,g.id as edutype_id from"
				+ " entity_student_info a,entity_major_info b,entity_site_info c,entity_suspend_school d,entity_grade_info f,entity_edu_type g where "
				+ "d.user_id = a.id and a.major_id = b.id and a.grade_id=f.id and a.edutype_id=g.id and a.site_id = c.id and a.status = '0001')";

		return db.countselect(sql);

	}

	private int suspendRecordsNum() {
		dbpool db = new dbpool();
		String sql = "select id from (select id,user_id,to_char(s_date,'yyyy-mm-dd h24:mi:ss') as s_date, "
				+ "to_char(e_date,'yyyy-mm-dd h24:mi:ss') as e_date,note from entity_suspend_school)";

		return db.countselect(sql);
	}

	private int suspendRecordsNum(String siteId, String regNo) {
		dbpool db = new dbpool();
		String sql;
		String site_con = "";
		String reg_con = "";
		if (siteId != null && !siteId.trim().equals(""))
			site_con = " and es.id='" + siteId + "' ";
		if (regNo != null && !regNo.trim().equals(""))
			reg_con = " and esi.reg_no like '%" + regNo.trim() + "%' ";

		sql = "select id,reg_no,u_name,g_name,m_name,e_name,s_name,ess_id,s_date,e_date from "
				+ "(select esi.id as id,esi.reg_no as reg_no,esi.name as u_name,egi.name as g_name,"
				+ "emi.name as m_name,eet.name as e_name,es.name as s_name,ess.id as ess_id,"
				+ "to_char(s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,"
				+ "to_char(e_date,'yyyy-mm-dd hh24:mi:ss') as e_date  from entity_student_info esi,"
				+ "entity_major_info emi,entity_site_info es,entity_grade_info egi,entity_edu_type eet,"
				+ "entity_suspend_school ess where ess.user_id=esi.id "
				+ site_con
				+ reg_con
				+ " and egi.id=esi.grade_id and "
				+ "emi.id=esi.major_id and eet.id=esi.edutype_id and es.id=esi.site_id)";

		return db.countselect(sql);
	}

	public int changeMajorStatus(String newmajor_id, String newedu_type_id,
			String newgrade_id, String newsite_id) throws PlatformException {
		dbpool db = new dbpool();
		String upSql;
		String upSql1;
		String insSql;
		ArrayList sqlList = new ArrayList();
		for (int i = 0; i < this.getStudentList().size(); i++) {
			upSql = "update entity_elective set change_major='1111' where student_id='"
					+ ((Student) this.getStudentList().get(i)).getId() + "'";
			upSql1 = "update entity_student_info set major_id='" + newmajor_id
					+ "',grade_id='" + newgrade_id + "',edutype_id='"
					+ newedu_type_id + "',site_id='" + newsite_id
					+ "' where id='"
					+ ((Student) this.getStudentList().get(i)).getId() + "'";
			insSql = "insert into entity_change_major (id,student_id,old_major_id,new_major_id,old_grade_id,"
					+ "new_grade_id,old_edu_type,new_edu_type,old_site,new_site,status,c_date) values"
					+ "(to_char(s_change_major_id.nextval),'"
					+ ((Student) this.getStudentList().get(i)).getId()
					+ "','"
					+ ((Student) this.getStudentList().get(i)).getStudentInfo()
							.getMajor_id()
					+ "','"
					+ newmajor_id
					+ "','"
					+ ((Student) this.getStudentList().get(i)).getStudentInfo()
							.getGrade_id()
					+ "','"
					+ newgrade_id
					+ "','"
					+ ((Student) this.getStudentList().get(i)).getStudentInfo()
							.getEdutype_id()
					+ "','"
					+ newedu_type_id
					+ "','"
					+ ((Student) this.getStudentList().get(i)).getStudentInfo()
							.getSite_id()
					+ "','"
					+ newsite_id
					+ "','00000000',sysdate)";
			// sqlList.add(upSql);
			UserUpdateLog.setDebug("OracleStatusChangeActivity.changeMajorStatus(String newmajor_id, String newedu_type_id,String newgrade_id, String newsite_id) SQL=" + upSql + " DATE=" + new Date());
			UserAddLog.setDebug("OracleStatusChangeActivity.changeMajorStatus(String newmajor_id, String newedu_type_id,String newgrade_id, String newsite_id) SQL=" + insSql + " DATE=" + new Date());
			sqlList.add(upSql1);
			sqlList.add(insSql);
		}
		return db.executeUpdateBatch(sqlList);
	}

	public int cancelChangeStatus(String statusChangeType)
			throws PlatformException {
		if (statusChangeType.equals("ABORT")) {
			return this.cancelAbort();
		}
		if (statusChangeType.equals("SUSPEND")) {
			return this.cancelSuspend();
		}

		return 0;
	}

	private int cancelAbort() throws PlatformException {
		dbpool db = new dbpool();
		String upSql = "";
		String edateUpSql = "";
		ArrayList sqlList = new ArrayList();
		List studentList = this.getStudentList();
		for (int i = 0; i < studentList.size(); i++) {
			Student student = (Student) studentList.get(i);
			edateUpSql = "update entity_abort_school set e_date=sysdate where user_id = '"
					+ student.getId() + "'";
			upSql = "update entity_student_info set status='0000' where id= '"
					+ student.getId() + "'";
			UserUpdateLog.setDebug("OracleStatusChangeActivity.cancelAbort() SQL1=" + upSql + " SQL2=" + edateUpSql + " DATE=" + new Date());
			sqlList.add(upSql);
			sqlList.add(edateUpSql);
		}
		return db.executeUpdateBatch(sqlList);
	}

	private int cancelAbort(String rid) throws PlatformException {
		dbpool db = new dbpool();
		String upSql = "";
		String edateUpSql = "";
		ArrayList sqlList = new ArrayList();
		edateUpSql = "update entity_abort_school set e_date=sysdate where id = '"
				+ rid + "'";
		upSql = "update entity_student_info set status='0' where id="
				+ "(select user_id from entity_abort_school where id='" + rid
				+ "')";
		UserUpdateLog.setDebug("OracleStatusChangeActivity.cancelAbort(String rid) SQL1=" + upSql + " SQL2=" + edateUpSql + " DATE=" + new Date());
		sqlList.add(upSql);
		sqlList.add(edateUpSql);
		return db.executeUpdateBatch(sqlList);
	}

	private int cancelSuspend() throws PlatformException {
		dbpool db = new dbpool();
		String upSql = "";
		String edateUpSql = "";
		ArrayList sqlList = new ArrayList();
		List studentList = this.getStudentList();
		for (int i = 0; i < studentList.size(); i++) {
			Student student = (Student) studentList.get(i);
			edateUpSql = "update entity_suspend_school set e_date=sysdate where user_id = '"
					+ student.getId() + "'";
			upSql = "update entity_student_info set status='0' where id= '"
					+ student.getId() + "'";
			UserUpdateLog.setDebug("OracleStatusChangeActivity.cancelSuspend() SQL1=" + upSql + " SQL2=" + edateUpSql + " DATE=" + new Date());
			sqlList.add(upSql);
			sqlList.add(edateUpSql);
		}
		return db.executeUpdateBatch(sqlList);
	}

	private int cancelSuspend(String rid) throws PlatformException {
		dbpool db = new dbpool();
		String upSql = "";
		String edateUpSql = "";
		ArrayList sqlList = new ArrayList();
		edateUpSql = "update entity_suspend_school set e_date=sysdate where id = '"
				+ rid + "'";
		/*
		 * upSql = "update entity_student_info set status='0000' where id=" +
		 * "(select user_id from entity_suspend_school where id='" + rid + "')";
		 */
		upSql = "update entity_student_info set status='0' where id="
				+ "(select user_id from entity_suspend_school where id='" + rid
				+ "')";
		UserUpdateLog.setDebug("OracleStatusChangeActivity.cancelSuspend(String rid) SQL1=" + upSql + " SQL2=" + edateUpSql + " DATE=" + new Date());
		sqlList.add(upSql);
		sqlList.add(edateUpSql);
		return db.executeUpdateBatch(sqlList);
	}

	public int getChangeMajorStudentsNum() throws PlatformException {
		dbpool db = new dbpool();
		String sql;
		sql = "select reg_no from ("
				+ "select a.reg_no as reg_no,a.name as u_name,b.name as om_name,e.name as nm_name,f.name as og_name,h.name as ng_name,"
				+ "g.name as oe_name,i.name as ne_name,j.name as ns_name,k.name as os_name,to_char(d.c_date,'yyyy-mm-dd') as c_date"
				+ ",d.new_site as ns_id,d.old_site as os_id from entity_student_info a,entity_major_info b,entity_change_major d,entity_major_info e,"
				+ "entity_grade_info f,entity_edu_type g,entity_grade_info h,entity_edu_type i,entity_site_info j,entity_site_info k "
				+ "where d.student_id = a.id and d.old_major_id = b.id and d.new_major_id = e.id and d.old_grade_id=f.id and d.old_edu_type=g.id "
				+ "and d.new_edu_type=i.id and d.new_grade_id=h.id and d.new_site = j.id and d.old_site = k.id order by a.reg_no,d.c_date desc)";

		return db.countselect(sql);
	}

	public int getChangeMajorStudentsNum(String site_id, String reg_no)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql;
		sql = "select a.reg_no as reg_no,a.name as u_name,b.name as om_name,e.name as nm_name,f.name as og_name,h.name as ng_name,"
				+ "g.name as oe_name,i.name as ne_name,j.name as ns_name,k.name as os_name,to_char(d.c_date,'yyyy-mm-dd') as c_date"
				+ ",d.new_site as ns_id,d.old_site as os_id from entity_student_info a,entity_major_info b,entity_change_major d,entity_major_info e,"
				+ "entity_grade_info f,entity_edu_type g,entity_grade_info h,entity_edu_type i,entity_site_info j,entity_site_info k "
				+ "where d.student_id = a.id and d.old_major_id = b.id and d.new_major_id = e.id and d.old_grade_id=f.id and d.old_edu_type=g.id "
				+ "and d.new_edu_type=i.id and d.new_grade_id=h.id and d.new_site = j.id and d.old_site = k.id ";
		if (site_id != null && !site_id.trim().equals(""))
			sql += " and (j.id='" + site_id + "' or k.id='" + site_id + "') ";

		if (reg_no != null && !reg_no.trim().equals(""))
			sql += " and a.reg_no like '%" + reg_no.trim() + "%' ";

		return db.countselect(sql);
	}

	public List getChangeMajorStudents() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		sql = "select d.id as change_major_id,  a.reg_no,a.name as u_name,b.name as om_name,e.name as nm_name,f.name as og_name,h.name as ng_name,"
				+ "g.name as oe_name,i.name as ne_name,j.name as ns_name,k.name as os_name,to_char(d.c_date,'yyyy-mm-dd') as c_date"
				+ ",d.new_site as ns_id,d.old_site as os_id from entity_student_info a,entity_major_info b,entity_change_major d,entity_major_info e,"
				+ "entity_grade_info f,entity_edu_type g,entity_grade_info h,entity_edu_type i,entity_site_info j,entity_site_info k "
				+ "where d.student_id = a.id and d.old_major_id = b.id and d.new_major_id = e.id and d.old_grade_id=f.id and d.old_edu_type=g.id "
				+ "and d.new_edu_type=i.id and d.new_grade_id=h.id and d.new_site = j.id and d.old_site = k.id order by a.reg_no,d.c_date desc";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleChangeMajorStudent student = new OracleChangeMajorStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setChange_major_id(rs.getString("change_major_id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("ne_name"));
				eduInfo.setGrade_name(rs.getString("ng_name"));
				eduInfo.setMajor_name(rs.getString("nm_name"));
				eduInfo.setSite_name(rs.getString("ns_name"));
				student.setStudentInfo(eduInfo);
				student.setOld_eduname(rs.getString("oe_name"));
				student.setOld_gradename(rs.getString("og_name"));
				student.setOld_majorname(rs.getString("om_name"));
				student.setOld_sitename(rs.getString("os_name"));
				student.setChange_date(rs.getString("c_date"));
				stuList.add(student);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;
	}

	public ChangeMajorStudent getChangeMajorStudent(String changeMajorId)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql;
		OracleChangeMajorStudent student = null;
		sql = "select d.id as change_major_id,  a.reg_no,a.name as u_name,b.name as om_name,e.name as nm_name,f.name as og_name,h.name as ng_name,"
				+ "g.name as oe_name,i.name as ne_name,j.name as ns_name,k.name as os_name,to_char(d.c_date,'yyyy-mm-dd') as c_date"
				+ ",d.new_site as ns_id,d.old_site as os_id from entity_student_info a,entity_major_info b,entity_change_major d,entity_major_info e,"
				+ "entity_grade_info f,entity_edu_type g,entity_grade_info h,entity_edu_type i,entity_site_info j,entity_site_info k "
				+ "where d.id='"
				+ changeMajorId
				+ "' and d.student_id = a.id and d.old_major_id = b.id and d.new_major_id = e.id and d.old_grade_id=f.id and d.old_edu_type=g.id "
				+ "and d.new_edu_type=i.id and d.new_grade_id=h.id and d.new_site = j.id and d.old_site = k.id order by a.reg_no,d.c_date desc";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				student = new OracleChangeMajorStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setChange_major_id(rs.getString("change_major_id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("ne_name"));
				eduInfo.setGrade_name(rs.getString("ng_name"));
				eduInfo.setMajor_name(rs.getString("nm_name"));
				eduInfo.setSite_name(rs.getString("ns_name"));
				student.setStudentInfo(eduInfo);
				student.setOld_eduname(rs.getString("oe_name"));
				student.setOld_gradename(rs.getString("og_name"));
				student.setOld_majorname(rs.getString("om_name"));
				student.setOld_sitename(rs.getString("os_name"));
				student.setChange_date(rs.getString("c_date"));
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return student;
	}

	public List getChangeMajorStudents(Page page) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		sql = "select d.id as change_major_id, a.reg_no,a.name as u_name,b.name as om_name,e.name as nm_name,f.name as og_name,h.name as ng_name,"
				+ "g.name as oe_name,i.name as ne_name,j.name as ns_name,k.name as os_name,to_char(d.c_date,'yyyy-mm-dd') as c_date"
				+ ",d.new_site as ns_id,d.old_site as os_id from entity_student_info a,entity_major_info b,entity_change_major d,entity_major_info e,"
				+ "entity_grade_info f,entity_edu_type g,entity_grade_info h,entity_edu_type i,entity_site_info j,entity_site_info k "
				+ "where d.student_id = a.id and d.old_major_id = b.id and d.new_major_id = e.id and d.old_grade_id=f.id and d.old_edu_type=g.id "
				+ "and d.new_edu_type=i.id and d.new_grade_id=h.id and d.new_site = j.id and d.old_site = k.id order by d.c_date desc,  a.reg_no";
		try {
			if (page == null) {
				rs = db.executeQuery(sql);
			} else {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			}
			while (rs != null && rs.next()) {
				OracleChangeMajorStudent student = new OracleChangeMajorStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setChange_major_id(rs.getString("change_major_id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("ne_name"));
				eduInfo.setGrade_name(rs.getString("ng_name"));
				eduInfo.setMajor_name(rs.getString("nm_name"));
				eduInfo.setSite_name(rs.getString("ns_name"));
				student.setStudentInfo(eduInfo);
				student.setOld_eduname(rs.getString("oe_name"));
				student.setOld_gradename(rs.getString("og_name"));
				student.setOld_majorname(rs.getString("om_name"));
				student.setOld_sitename(rs.getString("os_name"));
				student.setChange_date(rs.getString("c_date"));
				stuList.add(student);
			}

		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;
	}

	public List getChangeMajorStudents(Page page, String site_id, String reg_no)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		sql = "select d.id as change_major_id, a.reg_no,a.name as u_name,b.name as om_name,e.name as nm_name,f.name as og_name,h.name as ng_name,"
				+ "g.name as oe_name,i.name as ne_name,j.name as ns_name,k.name as os_name,to_char(d.c_date,'yyyy-mm-dd') as c_date"
				+ ",d.new_site as ns_id,d.old_site as os_id from entity_student_info a,entity_major_info b,entity_change_major d,entity_major_info e,"
				+ "entity_grade_info f,entity_edu_type g,entity_grade_info h,entity_edu_type i,entity_site_info j,entity_site_info k "
				+ "where d.student_id = a.id and d.old_major_id = b.id and d.new_major_id = e.id and d.old_grade_id=f.id and d.old_edu_type=g.id "
				+ "and d.new_edu_type=i.id and d.new_grade_id=h.id and d.new_site = j.id and d.old_site = k.id ";
		if (site_id != null && !site_id.trim().equals(""))
			sql += " and (j.id='" + site_id + "' or k.id='" + site_id + "') ";

		if (reg_no != null && !reg_no.trim().equals(""))
			sql += " and a.reg_no like '%" + reg_no.trim() + "%' ";

		sql += " order by d.c_date desc,  a.reg_no";
		Log.setDebug("getChangeMajorStudents=" + sql);
		try {
			if (page == null) {
				rs = db.executeQuery(sql);
			} else {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			}
			while (rs != null && rs.next()) {
				OracleChangeMajorStudent student = new OracleChangeMajorStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setChange_major_id(rs.getString("change_major_id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("ne_name"));
				eduInfo.setGrade_name(rs.getString("ng_name"));
				eduInfo.setMajor_name(rs.getString("nm_name"));
				eduInfo.setSite_name(rs.getString("ns_name"));
				student.setStudentInfo(eduInfo);
				student.setOld_eduname(rs.getString("oe_name"));
				student.setOld_gradename(rs.getString("og_name"));
				student.setOld_majorname(rs.getString("om_name"));
				student.setOld_sitename(rs.getString("os_name"));
				student.setChange_date(rs.getString("c_date"));
				stuList.add(student);
			}

		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;
	}

	public int cancelChangeStatus(String rid, String statusChangeType)
			throws PlatformException {
		if (statusChangeType.equals("ABORT")) {
			return this.cancelAbort(rid);
		}
		if (statusChangeType.equals("SUSPEND")) {
			return this.cancelSuspend(rid);
		}

		return 0;
	}

	public int getStatusChangeStudentRecodsNum(List searchProperty,
			String statusChangeType) throws PlatformException {
		if (statusChangeType.equals("ABORT")) {
			return abortRecordsNum(searchProperty);
		} else if (statusChangeType.equals("SUSPEND")) {
			return suspendRecordsNum(searchProperty);
		} else
			return 0;
	}

	public int abortRecordsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "select id from (select a.id from entity_abort_school a,entity_student_info b where a.user_id=b.id "
				+ Conditions.convertToAndCondition(searchProperty, null) + ")";
		return db.countselect(sql);
	}

	public int suspendRecordsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "select id from (select a.id from entity_suspend_school a,entity_student_info b where a.user_id=b.id "
				+ Conditions.convertToAndCondition(searchProperty, null) + ")";
		return db.countselect(sql);
	}

	public List getStatusChangeStudentRecods(Page page, List searchProperty,
			List orderProperty, String statusChangeType)
			throws PlatformException {
		if (statusChangeType.equals("ABORT")) {
			return this.abortRecordsList(page, searchProperty, orderProperty);
		} else if (statusChangeType.equals("SUSPEND")) {
			return this.suspendRecordsList(page, searchProperty, orderProperty);
		} else
			return null;
	}

	public List abortRecordsList(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		try {
			sql = "select id,reg_no,u_name,g_name,m_name,e_name,s_name,eas_id,s_date,e_date from "
					+ "(select esi.id as id,esi.reg_no as reg_no,esi.name as u_name,egi.name as g_name,"
					+ "emi.name as m_name,eet.name as e_name,es.name as s_name,eas.id as eas_id,"
					+ "to_char(s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,"
					+ "to_char(e_date,'yyyy-mm-dd hh24:mi:ss') as e_date  from entity_student_info esi,"
					+ "entity_major_info emi,entity_site_info es,entity_grade_info egi,entity_edu_type eet,"
					+ "entity_abort_school eas where eas.user_id=esi.id and egi.id=esi.grade_id and "
					+ "emi.id=esi.major_id and eet.id=esi.edutype_id and es.id=esi.site_id"
					+ Conditions.convertToAndCondition(searchProperty,
							orderProperty) + ")";

			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());

			while (rs != null && rs.next()) {
				OracleStatusChangedStudent student = new OracleStatusChangedStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("e_name"));
				eduInfo.setGrade_name(rs.getString("g_name"));
				eduInfo.setMajor_name(rs.getString("m_name"));
				eduInfo.setSite_name(rs.getString("s_name"));
				student.setStudentInfo(eduInfo);
				student.setStatusChange_date(rs.getString("s_date"));
				student.setStatusChangeEndDate(rs.getString("e_date"));
				student.setSsid(rs.getString("eas_id"));

				stuList.add(student);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;
	}

	public List suspendRecordsList(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		try {
			sql = "select id,reg_no,u_name,g_name,m_name,e_name,s_name,ess_id,s_date,e_date from "
					+ "(select esi.id as id,esi.reg_no as reg_no,esi.name as u_name,egi.name as g_name,"
					+ "emi.name as m_name,eet.name as e_name,es.name as s_name,ess.id as ess_id,"
					+ "to_char(s_date,'yyyy-mm-dd hh24:mi:ss') as s_date,"
					+ "to_char(e_date,'yyyy-mm-dd hh24:mi:ss') as e_date  from entity_student_info esi,"
					+ "entity_major_info emi,entity_site_info es,entity_grade_info egi,entity_edu_type eet,"
					+ "entity_suspend_school ess where ess.user_id=esi.id and egi.id=esi.grade_id and "
					+ "emi.id=esi.major_id and eet.id=esi.edutype_id and es.id=esi.site_id"
					+ Conditions.convertToAndCondition(searchProperty,
							orderProperty) + ")";
			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			while (rs != null && rs.next()) {
				OracleStatusChangedStudent student = new OracleStatusChangedStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("e_name"));
				eduInfo.setGrade_name(rs.getString("g_name"));
				eduInfo.setMajor_name(rs.getString("m_name"));
				eduInfo.setSite_name(rs.getString("s_name"));
				student.setStudentInfo(eduInfo);
				student.setStatusChange_date(rs.getString("s_date"));
				student.setStatusChangeEndDate(rs.getString("e_date"));
				student.setSsid(rs.getString("ess_id"));

				stuList.add(student);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;
	}

	public int getChangeMajorStudentsNum(List searchProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql;
		sql = "select reg_no from ("
				+ "select a.reg_no as reg_no,a.name as u_name,b.name as om_name,e.name as nm_name,f.name as og_name,h.name as ng_name,"
				+ "g.name as oe_name,i.name as ne_name,j.name as ns_name,k.name as os_name,to_char(d.c_date,'yyyy-mm-dd') as c_date"
				+ ",d.new_site as ns_id,d.old_site as os_id from entity_student_info a,entity_major_info b,entity_change_major d,entity_major_info e,"
				+ "entity_grade_info f,entity_edu_type g,entity_grade_info h,entity_edu_type i,entity_site_info j,entity_site_info k "
				+ "where d.student_id = a.id and d.old_major_id = b.id and d.new_major_id = e.id and d.old_grade_id=f.id and d.old_edu_type=g.id "
				+ "and d.new_edu_type=i.id and d.new_grade_id=h.id and d.new_site = j.id and d.old_site = k.id "
				+ Conditions.convertToAndCondition(searchProperty, null)
				+ " order by d.c_date desc,a.reg_no)";
		return db.countselect(sql);
	}

	public List getChangeMajorStudents(Page page, List searchProperty,
			List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList stuList = new ArrayList();
		String sql;
		sql = "select reg_no,u_name,om_name,nm_name,og_name,ng_name,oe_name,ne_name,ns_name,os_name,c_date"
				+ ",ns_id,os_id from (select a.reg_no,a.name as u_name,b.name as om_name,e.name as nm_name,f.name as og_name,h.name as ng_name,"
				+ "g.name as oe_name,i.name as ne_name,j.name as ns_name,k.name as os_name,to_char(d.c_date,'yyyy-mm-dd') as c_date"
				+ ",d.new_site as ns_id,d.old_site as os_id from entity_student_info a,entity_major_info b,entity_change_major d,entity_major_info e,"
				+ "entity_grade_info f,entity_edu_type g,entity_grade_info h,entity_edu_type i,entity_site_info j,entity_site_info k "
				+ "where d.student_id = a.id and d.old_major_id = b.id and d.new_major_id = e.id and d.old_grade_id=f.id and d.old_edu_type=g.id "
				+ "and d.new_edu_type=i.id and d.new_grade_id=h.id and d.new_site = j.id and d.old_site = k.id "
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty) + " order by d.c_date desc,a.reg_no)";
		try {
			if (page == null) {
				rs = db.executeQuery(sql);
			} else {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			}
			while (rs != null && rs.next()) {
				OracleChangeMajorStudent student = new OracleChangeMajorStudent();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setName(rs.getString("u_name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_name(rs.getString("ne_name"));
				eduInfo.setGrade_name(rs.getString("ng_name"));
				eduInfo.setMajor_name(rs.getString("nm_name"));
				eduInfo.setSite_name(rs.getString("ns_name"));
				student.setStudentInfo(eduInfo);
				student.setOld_eduname(rs.getString("oe_name"));
				student.setOld_gradename(rs.getString("og_name"));
				student.setOld_majorname(rs.getString("om_name"));
				student.setOld_sitename(rs.getString("os_name"));
				student.setChange_date(rs.getString("c_date"));
				stuList.add(student);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return stuList;
	}

}
