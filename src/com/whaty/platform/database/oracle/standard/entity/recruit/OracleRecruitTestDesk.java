package com.whaty.platform.database.oracle.standard.entity.recruit;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.recruit.RecruitTestDesk;
import com.whaty.platform.util.log.EntityLog;

public class OracleRecruitTestDesk extends RecruitTestDesk {
	/** Creates a new instance of OracleRecruitTestDesk */
	public OracleRecruitTestDesk() {
	}

	public OracleRecruitTestDesk(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,testroom_id,testcourse_id,recruitstudent_id,numbyroom,numbycourse from recruit_test_desk where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleRecruitTestRoom testRoom = new OracleRecruitTestRoom();
				testRoom.setId(rs.getString("testroom_id"));
				this.setTestRoom(testRoom);
				OracleRecruitTestCourse testCourse = new OracleRecruitTestCourse();
				testCourse.setId(rs.getString("testcourse_id"));
				this.setTestCourse(testCourse);
				OracleRecruitStudent student = new OracleRecruitStudent();
				student.setId(rs.getString("recruitstudent_id"));
				this.setStudent(student);
				this.setNumByRoom(rs.getInt("numbyroom"));
				this.setNumByCourse(rs.getInt("numbycourse"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleRecruitTestDesk(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int update() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}
}
