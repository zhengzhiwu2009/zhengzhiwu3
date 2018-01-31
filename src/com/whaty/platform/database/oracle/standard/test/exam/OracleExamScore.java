package com.whaty.platform.database.oracle.standard.test.exam;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.test.exam.ExamCourse;
import com.whaty.platform.test.exam.ExamScore;
import com.whaty.platform.test.exam.ExamUser;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleExamScore extends ExamScore {
	public OracleExamScore() {
	}

	public OracleExamScore(ExamUser examUser, ExamCourse examCourse) {
		this.setExamCourse(examCourse);
		this.setExamUser(examUser);

		String examUser_id = examUser.getId();
		String examCourse_id = examCourse.getId();
		String sql = "select score from test_examuser_course where user_id='"
				+ examUser_id + "' and course_id='" + examCourse_id + "'";
		dbpool db = new dbpool();
		MyResultSet rs = null;

		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setScore(rs.getString("score"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleExamScore(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int update() throws PlatformException {
		String sql = "update test_examuser_course set score='"
				+ this.getScore() + "' where user_id='"
				+ this.getExamUser().getId() + "' and course_id='"
				+ this.getExamCourse().getId() + "'";
		dbpool db = new dbpool();
		int ret = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExamScore.update() SQL=" + sql + " COUNT=" + ret + " DATE=" + new Date());
		return ret;
	}

	public int add() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

}
