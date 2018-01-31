package com.whaty.platform.database.oracle.standard.test.exam;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.test.exam.ExamCourse;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;
import com.whaty.util.string.WhatyStrManage;

public class OracleExamCourse extends ExamCourse {
	public OracleExamCourse() {
	}

	public OracleExamCourse(String aid) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select name,to_char(s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,to_char(e_date,'yyyy-mm-dd hh24:Mi:ss')"
			//	+ " as e_date,test_batch_id,course_id,examsequence_id,course_type,exam_type from test_examcourse_info where id='"
				+ " as e_date,test_batch_id,open_course_id,basicsequence_id,course_type,exam_type from test_examcourse_info where id='"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(aid);
				this.setName(rs.getString("name"));
				this.setStartDate(rs.getString("s_date"));
				this.setEndDate(rs.getString("e_date"));
			//	this.setCourse_id(rs.getString("course_id"));
				this.setCourse_id(rs.getString("open_course_id"));
				this.setExamBatch(new OracleExamBatch(rs
						.getString("test_batch_id")));
				this.setSequence(new OracleExamSequence(rs
						.getString("basicsequence_id")));
			//			.getString("examsequence_id")));
				this.setExamType(rs.getString("exam_type"));
				this.setCourseType(rs.getString("course_type"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleExamCourse(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		int i = 1;
		try {
			if (IsExist() <= 0) {
				sql = "select s_test_examcourse_info_id.nextval as id from dual";
				rs = db.executeQuery(sql);
				if (rs != null && rs.next())
					i = rs.getInt("id");
				db.close(rs);
				sql = "insert into test_examcourse_info (id,name,s_date,e_date,test_batch_id,course_id,examsequence_id,course_type,exam_type) values ('"
						+ i
						+ "','"
						+ this.getName()
						+ "',to_date('"
						+ new WhatyStrManage().fixNull(this.getStartDate())
						+ "','yyyy-mm-dd hh24:Mi:ss'),to_date('"
						+ new WhatyStrManage().fixNull(this.getEndDate())
						+ "','yyyy-mm-dd hh24:Mi:ss'),'"
						+ this.getExamBatch().getId()
						+ "','"
						+ this.getCourse_id()
						+ "','"
						+ this.getSequence().getId()
						+ "','"
						+ this.getCourseType()
						+ "','"
						+ this.getExamType()
						+ "')";
				db.executeUpdate(sql);
				UserAddLog.setDebug("OracleExamCourse.add() SQL=" + sql + " DATE=" + new Date());
			} else {
				sql = "update test_examcourse_info set exam_type='"
						+ this.getExamType() + "' where course_id='"
						+ this.getCourse_id() + "' and test_batch_id='"
						+ this.getExamBatch().getId() + "' and name='"
						+ this.getName() + "' and examsequence_id='"+this.getSequence().getId()+"'";
				db.executeUpdate(sql);
				UserUpdateLog.setDebug("OracleExamCourse.add() SQL=" + sql + " DATE=" + new Date());
				sql = "select id from test_examcourse_info where course_id='"
						+ this.getCourse_id() + "' and test_batch_id='"
						+ this.getExamBatch().getId() + "' and name='"
						+ this.getName() + "' and examsequence_id='"+this.getSequence().getId()+"'";
				rs = db.executeQuery(sql);
				if (rs != null && rs.next())
					i = rs.getInt("id");
				db.close(rs);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
		}
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update test_examcourse_info set name='" + this.getName()
				+ "',s_date=to_date('" + this.getStartDate()
				+ "','yyyy-mm-dd hh24:Mi:ss')," + "e_date=to_date('"
				+ this.getEndDate() + "','yyyy-mm-dd hh24:Mi:ss'),"
				+ "test_batch_id='" + this.getExamBatch().getId()
				+ "',course_id='" + this.getCourse_id() + "',course_type='"
				+ this.getCourseType() + "',exam_type='" + this.getExamType()
				+ "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExamCourse.add() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from test_examcourse_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleExamCourse.delete() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

	public int IsExist() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select *  from test_examcourse_info where course_id='"
				+ this.getCourse_id() + "' and test_batch_id='"
				+ this.getExamBatch().getId() + "' and name='" + this.getName()
				+ "' and examsequence_id='" + this.getSequence().getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public ExamCourse getExamCourse(String batch_id, String course_id) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select name,to_char(s_date,'yyyy-mm-dd hh24:Mi:ss') as s_date,to_char(e_date,'yyyy-mm-dd hh24:Mi:ss') as e_date,test_batch_id,course_id,course_type,exam_type from test_examcourse_info where id='"
				+ course_id + "' and test_batch_id='" + batch_id + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {

				this.setName(rs.getString("name"));
				this.setStartDate(rs.getString("s_date"));
				this.setEndDate(rs.getString("e_date"));
				this.setCourse_id(rs.getString("course_id"));
				this.setExamBatch(new OracleExamBatch(rs
						.getString("test_batch_id")));
				this.setCourseType(rs.getString("course_type"));
				this.setExamType(rs.getString("exam_type"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleExamCourse(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return this;
	}

	public int changeCourseType() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update test_examcourse_info set course_type='"
				+ this.getCourseType() + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExamCourse.changeCourseType() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}
}
