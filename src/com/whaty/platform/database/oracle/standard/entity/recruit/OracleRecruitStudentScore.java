package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.recruit.RecruitStudentScore;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleRecruitStudentScore extends RecruitStudentScore {
	/** Creates a new instance of OracleRecruitStudentScore */
	public OracleRecruitStudentScore() {
	}
	
	public OracleRecruitStudentScore(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,recruitstudent_id,testcourse_id,score,status from recruit_test_desk where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleRecruitStudent student = new OracleRecruitStudent();
				student.setId(rs.getString("recruitstudent_id"));
				this.setStudent(student);
				OracleRecruitTestCourse testCourse = new OracleRecruitTestCourse();
				testCourse.setId(rs.getString("testcourse_id"));
				this.setTestCourse(testCourse);
				this.setScore(rs.getString("score"));
				this.setStatus(rs.getString("numbycourse"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleRecruitStudentScore(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into recruit_test_desk (id,recruitstudent_id,testcourse_id) values(to_char(s_recruit_test_desk_id.nextval),'"
				+ this.getStudent().getId()
				+ "','"
				+ this.getTestCourse().getId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitStudentScore.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_test_desk set recruitstudent_id='"
				+ this.getStudent().getId() + "',testcourse_id='"
				+ this.getTestCourse().getId() + "' where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitStudentScore.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_test_desk where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitStudentScore.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
