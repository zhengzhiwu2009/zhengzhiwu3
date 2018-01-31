/*
 * OracleClasse.java
 *
 * Created on 2005年5月8日, 上午10:55
 */

package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacher;
import com.whaty.platform.entity.basic.Classe;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.util.Page;
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
public class OracleClasse extends Classe {

	/** Creates a new instance of OracleClasse */
	public OracleClasse() {
	}

	public OracleClasse(String aid) {
		dbpool db = new dbpool();
		ArrayList classlist = new ArrayList();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,homepage,enounce from lrn_class_info where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setHomepage(rs.getString("homepage"));
				this.setEnounce(rs.getString("enounce"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleClasse(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into lrn_class_info (id,name) values('" + this.getId()
				+ "','" + this.getName() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleClasse.add() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

	/** 为班级添加班主任 */
	public void addChargers(List chargers) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into lrn_class_charge(id,teacher_id,class_id) select to_char(s_lrn_class_charge_id.nextval),id,'"
				+ this.getId() + "' from lrn_teacher_info where id in (";
		for (int i = 0; i < chargers.size(); i++) {
			OracleTeacher teacher = (OracleTeacher) chargers.get(i);
			if (i < chargers.size()) {
				sql = sql + "'" + teacher.getId() + "',";
			} else {
				sql = sql + "'" + teacher.getId() + "')";
			}
		}
		int count = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleClasse.addChargers(List chargers) SQL=" + sql + " COUNT" + count + " DATE=" + new Date());
	}

	/** 为班级添加学生 */
	public void addStudents(List students) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update lrn_user_info set class_id='" + this.getId()
				+ "' where id in (";
		for (int i = 0; i < students.size(); i++) {
			OracleStudent student = (OracleStudent) students.get(i);
			if (i < students.size() - 1) {
				sql = sql + "'" + student.getId() + "',";
			} else {
				sql = sql + "'" + student.getId() + "')";
			}
		}
		int count = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleClasse.addStudent(List students) SQL=" + sql + " COUNT" + count + " DATE=" + new Date());
	}

	public int delete() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from lrn_class_info where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleClasse.delete() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

	/** 获得班主任列表 */
	public List getChargers() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList chargerlist = new ArrayList();
		String sql = "";
		sql = "select id,name,phone,gender,teach_level,title from lrn_teacher_info where id in (select teacher_id from lrn_class_charge where class_id = '"
				+ this.getId() + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleTeacher teacher = new OracleTeacher();
				teacher.setId(rs.getString("id"));
				teacher.setName(rs.getString("name"));
				chargerlist.add(teacher);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("Classe.getChargers() error" + sql);
		} finally {
			db.close(rs);
			db = null;
			return chargerlist;
		}
	}

	/** 获得班级公告列表 */
	public List getClassAnnouncesByPage(Page page) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList announcelist = new ArrayList();
		String sql = "";
		sql = "select id,title,time from lrn_class_announce where class_id='"
				+ this.getId() + "'";
		try {
			rs = db.execute_oracle_page(sql, page.getPageInt(), page
					.getPageSize());
			while (rs != null && rs.next()) {
				OracleClassAnnounce announce = new OracleClassAnnounce();
				announce.setId(rs.getString("id"));
				announce.setTitle(rs.getString("title"));
				announce.setTime(rs.getString("time"));
				announcelist.add(announce);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("Classe.getClassAnnounceByPage() error" + sql);
		} finally {
			db.close(rs);
			db = null;
			return announcelist;
		}
	}

	/** 获得班级公告的数量 */
	public int getClassAnnouncesNum() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id,title,time from lrn_class_announce where class_id='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	/** 得到该班级学生列表 */
	public List getStudents(Page page) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList studentlist = new ArrayList();
		String sql = "";
		sql = "select id ,reg_no,name ,edu_type_id ,major_id,grade_id ,site ,id_card from lrn_user_info where class_id ='"
				+ this.getId() + "' ";
		try {
			rs = db.execute_oracle_page(sql, page.getPageInt(), page
					.getPageSize());
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setEdutype_id(rs.getString("edu_type_id"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setSite_id(rs.getString("site"));
				normalInfo.setIdcard(rs.getString("id_card"));
				student.setNormalInfo(normalInfo);
				student.setStudentInfo(eduInfo);
				student.setPlatformInfo(platformInfo);
				student.setRedundace(redundance);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("Classe.getStudents() error" + sql);
		} finally {
			db.close(rs);
			db = null;
			return studentlist;
		}
	}

	/** 删除班主任 */
	public void removeChargers(List chargers) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from lrn_class_charge where class_id='" + this.getId()
				+ "' and teacher_id in (";
		for (int i = 0; i < chargers.size(); i++) {
			OracleTeacher teacher = (OracleTeacher) chargers.get(i);
			if (i < chargers.size()) {
				sql = sql + "'" + teacher.getId() + "',";
			} else {
				sql = sql + "'" + teacher.getId() + "')";
			}
		}
		int count = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleClasse.removeChargers(List chargers) SQL=" + sql + " COUNT" + count + " DATE=" + new Date());
	}

	/** 删除学生 */
	public void removeStudents(List students) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update lrn_user_info set class_id='' where id in (";
		for (int i = 0; i < students.size(); i++) {
			OracleStudent student = (OracleStudent) students.get(i);
			if (i < students.size() - 1) {
				sql = sql + "'" + student.getId() + "',";
			} else {
				sql = sql + "'" + student.getId() + "')";
			}
		}
		int count = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleClasse.removeStudents(List students) SQL=" + sql + " COUNT" + count + " DATE=" + new Date());
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update lrn_class_info set name='" + this.getName()
				+ "' where id='" + this.getId() + "'";
		EntityLog.setDebug("OracleClasse@Method:update()=" + sql);
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleClasse.update() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

}
