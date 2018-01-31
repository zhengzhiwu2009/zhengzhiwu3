package com.whaty.platform.database.oracle.standard.entity.activity.uniteexam;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.activity.uniteexam.UniteExamCourse;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleUniteExamCourse extends UniteExamCourse {

	/** Creates a new instance of OracleUniteExamCourse */
	public OracleUniteExamCourse() {
	}

	public OracleUniteExamCourse(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select name,note from entity_uniteexam_course where id='"
					+ aid + "'";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(aid);
				this.setName(rs.getString("name"));
				this.setNote(rs.getString("note"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleUniteExamCourse@Method:OracleUniteExamCourse(String id) Error! (SQL = "+sql+")");
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_uniteexam_course (id,name,note) values(to_char(s_uniteexam_course_id.nextval),'"
				+ this.getName() + "','" + this.getNote() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleUniteExamCourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool dbDep = new dbpool();
		String sql = "";
		sql = "delete from entity_uniteexam_course where id='" + this.getId()
				+ "'";
		int i = dbDep.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleUniteExamCourse.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool dbDep = new dbpool();
		String sql = "";
		sql = "update entity_uniteexam_course set name='" + this.getName()
				+ "',note='" + this.getNote() + "' where id='" + this.getId()
				+ "'";
		int i = dbDep.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleUniteExamCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isIdExist() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int isOpenCourse() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int addTeachbookToCourse(String[] teachbookIdSet) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int removeTeachBookFromCourse(String[] teachbookIdSet) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

}
