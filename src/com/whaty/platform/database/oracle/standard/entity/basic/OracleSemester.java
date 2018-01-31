/*
 * OracleSemester.java
 *
 * Created on 2005年5月4日, 上午11:30
 */

package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleBasicActivityList;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourseActivity;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * 
 * @author Administrator
 */
public class OracleSemester extends Semester {

	/** Creates a new instance of OracleSemester */
	public OracleSemester() {
	}

	public OracleSemester(String aid) {
		dbpool dbsemester = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select id,name,to_char(start_date,'yyyy-mm-dd') as start_date,to_char(end_date,'yyyy-mm-dd') as end_date,to_char(start_elective,'yyyy-mm-dd') as start_elective,to_char(end_elective,'yyyy-mm-dd') as end_elective,to_char(start_test,'yyyy-mm-dd') as start_test,to_char(end_test,'yyyy-mm-dd') as end_test,selected from entity_semester_info where id='"
					+ aid + "'";
			rs = dbsemester.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setStart_date(rs.getString("start_date"));
				this.setEnd_date(rs.getString("end_date"));
				this.setStart_elective(rs.getString("start_elective"));
				this.setEnd_elective(rs.getString("end_elective"));
				this.setStart_test(rs.getString("start_test"));
				this.setEnd_test(rs.getString("end_test"));
				this.setSelected(rs.getInt("selected"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("");
		} finally {
			dbsemester.close(rs);
			dbsemester = null;
		}
	}

	public int add() {
		dbpool dbsemester = new dbpool();
		String sql = "";
		sql = "insert into entity_semester_info (id,name,start_date,end_date,start_elective,end_elective,start_test,end_test,selected) values(to_char(s_semester_info_id.nextval),'"
				+ this.getName()
				+ "', to_date('"
				+ this.getStart_date()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getEnd_date()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getStart_elective()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getEnd_elective()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getStart_test()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getEnd_test()
				+ "','yyyy-mm-dd'),'0')";
		int i = dbsemester.executeUpdate(sql);
		UserAddLog.setDebug("OracleSemester.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public void addCourses(List courses) throws PlatformException {

		OracleOpenCourseActivity openCourseActivity = new OracleOpenCourseActivity();

		openCourseActivity.setCourseList(courses);
		openCourseActivity.setSemester(this);
		openCourseActivity.openCourses();
	}

	public int delete() throws PlatformException {
		if (openCourseNum() > 0)
			throw new PlatformException("The semester has open course!");
		dbpool dbsemester = new dbpool();
		String sql = "";
		sql = "delete from entity_semester_info where id='" + this.getId()
				+ "'";
		int i = dbsemester.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleSemester.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public void removeCourses(List courses) throws PlatformException {

		OracleOpenCourseActivity openCourseActivity = new OracleOpenCourseActivity();

		openCourseActivity.setCourseList(courses);
		openCourseActivity.setSemester(this);
		openCourseActivity.unOpenCourses();
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_semester_info set name='" + this.getName()
				+ "',start_date = to_date('" + this.getStart_date()
				+ "','yyyy-mm-dd'),end_date = to_date('" + this.getEnd_date()
				+ "','yyyy-mm-dd'),start_elective = to_date('"
				+ this.getStart_elective()
				+ "','yyyy-mm-dd'),end_elective = to_date('"
				+ this.getEnd_elective()
				+ "','yyyy-mm-dd'),start_test = to_date('"
				+ this.getStart_test() + "','yyyy-mm-dd'),end_test = to_date('"
				+ this.getEnd_test() + "','yyyy-mm-dd') where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSemester.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.whaty.platform.entity.Semester#isOpenCourse()
	 */
	public int openCourseNum() {
		dbpool dbsemester = new dbpool();
		String sql = "";
		sql = "select id from entity_course_active where semester_id = '"
				+ this.getId() + "'";
		int i = dbsemester.countselect(sql);
		return i;
	}

	public int cancelActive() {
		dbpool dbsemester = new dbpool();
		String sql = "";
		sql = "update entity_semester_info set selected='0' where id ='"
				+ this.getId() + "'";
		int i = dbsemester.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSemester.cancelActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int setActive() {
		dbpool dbsemester = new dbpool();
		String sql = "";
		sql = "update entity_semester_info set selected='1' where id ='"
				+ this.getId() + "'";
		int i = dbsemester.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSemester.setActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int reverseActive() {
		if (this.getSelected() == 0f)
			return this.setActive();
		else
			return this.cancelActive();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.basic.Semester#getCourses()
	 */
	public List getCourses() throws PlatformException {
		OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
		return basicActivityList.searchOpenCourse(null, null, null, this);

	}
}
