package com.whaty.platform.database.oracle.standard.test.reexam;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;

//import com.whaty.platform.test.exam.ExamCourse;
//import com.whaty.platform.test.exam.ExamDesk;
//import com.whaty.platform.test.exam.ExamUser;

import com.whaty.platform.test.reexam.ReExamCourse;
import com.whaty.platform.test.reexam.ReExamDesk;
import com.whaty.platform.test.reexam.ReExamUser;

import com.whaty.util.log.Log;

public class OracleReExamDesk extends ReExamDesk {
	public OracleReExamDesk() {
	}

	public OracleReExamDesk(ReExamUser examUser, ReExamCourse examCourse) {
		this.setExamUser(examUser);
		this.setExamCourse(examCourse);

		String examUser_id = examUser.getId();
		String examCourse_id = examCourse.getId();
		String sql = "select room_id,desk_id from test_examuser_course where user_id='"
				+ examUser_id + "' and course_id='" + examCourse_id + "'";
		dbpool db = new dbpool();
		MyResultSet rs = null;

		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setDeskNo(rs.getString("desk_no"));
				this.setExamRoom(new OracleReExamRoom(rs.getString("room_id")));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleExamDesk(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int update() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

}
