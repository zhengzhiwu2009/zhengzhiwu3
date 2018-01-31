/*
 * OracleMajor.java
 *
 * Created on 2005��5��7��, ����10:32
 */

package com.whaty.platform.database.oracle.standard.entity.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitSort;
import com.whaty.platform.entity.basic.Major;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * 
 * @author Administrator
 */
public class OracleMajor extends Major {

	/** Creates a new instance of OracleMajor */
	public OracleMajor() {
	}

	public OracleMajor(String aid) {
		String status;
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,major_alias,status,dep_id,dep_name,major_link,major_type,recruit_status,note from (select mi.id,mi.name,mi.major_alias,mi.status,mi.dep_id,di.name as dep_name,mi.major_link,mi.major_type,mi.recruit_status,mi.note from entity_major_info mi,entity_dep_info di where mi.dep_id=di.id and mi.id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setDep_id(rs.getString("dep_id"));
				this.setDep_name(rs.getString("dep_name"));
				this.setMajor_alias(rs.getString("major_alias"));
				this.setMajor_link(rs.getString("major_link"));
				this.setNote(rs.getString("note"));
				this.setRecruit_status(rs.getString("recruit_status"));
				status = rs.getString("status");
				if (status.equals("1"))
					this.setStatus(true);
				else
					this.setStatus(false);

			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleMajor(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		if (isIdExist() > 0) {
			throw new PlatformException("רҵ����Ѿ�����!");
		}
		sql = " insert into entity_major_info (id,name,major_alias,major_link,note,status,dep_id) values('"
				+ this.getId()
				+ "','"
				+ this.getName()
				+ "','"
				+ this.getMajor_alias()
				+ "','"
				+ this.getMajor_link()
				+ "',?,'0','" + this.getDep_id() + "')";
		int i = db.executeUpdate(sql, this.getNote());
		if (i > 0) {
			sql = "update entity_manager_info set major=major||'|"
					+ this.getId() + "'";
			db.executeUpdate(sql);
		}
		
		UserAddLog.setDebug("OracleMajor.add()   SQL = "+ sql+ " COUNT="+ i +" DATE:"+new Date());
		return i;
	}

	public int delete() throws PlatformException {
		if (isHasStudents() > 0)
			throw new PlatformException("רҵ����ѧ��!");
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_major_info where id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		
		UserDeleteLog.setDebug("OracleMajor.delete() SQL = "+ sql+ " COUNT="+ i +" DATE:"+new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_major_info set name='" + this.getName()
				+ "',major_alias='" + this.getMajor_alias() + "',major_link='"
				+ this.getMajor_link() + "',note= ? where id='" + this.getId()
				+ "'";
		EntityLog.setDebug("OracleMajor@Method:update()=" + sql);
		int i = db.executeUpdate(sql, this.getNote());
		if (i > 0) {
			sql = "update entity_manager_info set major=major||'|"
					+ this.getId() + "'";
			db.executeUpdate(sql);
		}
		UserUpdateLog.setDebug("OracleMajor.update() SQL = "+ sql+ " COUNT="+ i +" DATE:"+new Date());
		return i;
	}

	/*
	 * public void get_status(String aid) { String status; dbpool db = new
	 * dbpool(); MyResultSet rs = null; String sql = ""; sql = "select status
	 * from entity_major_info where id = '" + aid + "'"; try { rs =
	 * db.executeQuery(sql); while (rs != null && rs.next()) {
	 * status=rs.getString("status"); if(status.equals("1"))
	 * this.setStatus(true); else this.setStatus(false); } } catch
	 * (java.sql.SQLException e) { Log.setError("get_status(String aid) error" +
	 * sql); } finally { db.close(rs); db = null; } }
	 */

	public int update_status() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_major_info set status='" + this.getStatus() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleMajor.update_status() SQL = "+ sql+ " COUNT="+ i +" DATE:"+new Date());
		return i;
	}

	public int isHasCourses() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_course_info where major_id = '"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int isHasEduplan() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from mng_edu_eduplan where major_id = '"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int isHasStudents() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select major_id from entity_student_info where major_id = '"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	protected int isIdExist() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_major_info where id = '" + this.getId()
				+ "'";
		int i = db.countselect(sql);
		return i;
	}

	public int getMajorCoursesNum(String aid) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_course_info where major_id='" + aid + "'";
		int i = db.countselect(sql);
		return i;
	}

	public List getMajorCourses(Page page, String aid) {
		dbpool db = new dbpool();
		String sql = "select id,course_name,credit,course_time,exam_type,course_type,teaching_type,course_status,ref_book,note,standard_fee,drift_fee,text_book,text_book_price,redundance0,redundance1,redundance2,redundance3,redundance4 from entity_course_info where major_id='"
				+ aid + "'";
		MyResultSet rs = null;
		ArrayList courses = null;
		try {
			db = new dbpool();
			courses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("course_name"));
				course.setCredit(rs.getFloat("credit"));
				course.setCourse_time(rs.getFloat("course_time"));
				course.setExam_type(rs.getString("exam_type"));
				course.setCourse_type(rs.getString("course_type"));
				course.setTeaching_type(rs.getString("teaching_type"));
				course.setCourse_status(rs.getString("course_status"));
				course.setRef_book(rs.getString("ref_book"));
				course.setNote(rs.getString("note"));
				course.setStandard_fee(rs.getString("standard_fee"));
				course.setDrift_fee(rs.getString("drift_fee"));
				course.setText_book(rs.getString("text_book"));
				course.setText_book_price(rs.getString("text_book_price"));
				course.setRedundance0(rs.getString("redundance0"));
				course.setRedundance1(rs.getString("redundance1"));
				course.setRedundance2(rs.getString("redundance2"));
				course.setRedundance3(rs.getString("redundance3"));
				course.setRedundance4(rs.getString("redundance4"));
				courses.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courses;
	}

	public int changeMajorStatus(String aid) {
		dbpool db = new dbpool();
		String sql = "";
		String status = "";
		sql = "select status from entity_major_info where id='" + aid + "'";
		MyResultSet rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				status = rs.getString("status");
			}
		} catch (SQLException e) {
		}
		db.close(rs);
		if (status.equals("1"))
			status = "0";
		else
			status = "1";
		sql = "update entity_major_info set status='" + status + "' where id='"
				+ aid + "'";
		EntityLog.setDebug("OracleMajor@Method:changeMajorStatus()=" + sql);
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleMajor.changeMajorStatus(String aid) SQL = "+ sql+ " COUNT="+ i +" DATE:"+new Date());
		return i;
	}

	public List getSorts() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select b.id,b.name from recruit_majorsort_relation a,recruit_sort_info b where a.major_id='"
				+ this.getId() + "' and b.id=a.sort_id";
		MyResultSet rs = null;
		ArrayList sortList = null;
		try {
			db = new dbpool();
			sortList = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleRecruitSort recruitSort = new OracleRecruitSort();
				recruitSort.setId(rs.getString("id"));
				recruitSort.setName(rs.getString("name"));
				sortList.add(recruitSort);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return sortList;
	}

}
