package com.whaty.platform.database.oracle.standard.entity.activity.uniteexam;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.activity.uniteexam.UniteExamScore;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleUniteExamScore extends UniteExamScore {

	/** Creates a new instance of OracleUniteExamCourse */
	public OracleUniteExamScore() {
	}

	public OracleUniteExamScore(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select course_id,course_name,score,reg_no,name from entity_uniteexam_score where id='"
					+ aid + "'";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(aid);
				OracleUniteExamCourse course = new OracleUniteExamCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				this.setCourse(course);
				OracleStudent student = new OracleStudent();
				student.setName(rs.getString("name"));
				StudentEduInfo eduInfo = new StudentEduInfo();
				eduInfo.setReg_no(rs.getString("reg_no"));
				student.setStudentInfo(eduInfo);
				this.setStudent(student);
				this.setScore(rs.getString("score"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleUniteExamScore@Method:OracleUniteExamScore(String id) Error! (SQL = "+sql+")");
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_uniteexam_score (id,course_id,course_name,score,reg_no,name) "
				+ "values(to_char(s_uniteexam_score_id.nextval),'"
				+ this.getCourse().getId()
				+ "','"
				+ this.getCourse().getName()
				+ "','"
				+ this.getScore()
				+ "','"
				+ this.getStudent().getStudentInfo().getReg_no()
				+ "','"
				+ this.getStudent().getName() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleUniteExamScore.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_uniteexam_score where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleUniteExamScore.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_uniteexam_score set name='"
				+ this.getStudent().getName() + "',reg_no='"
				+ this.getStudent().getStudentInfo().getReg_no()
				+ "',course_id='" + this.getCourse().getId()
				+ "',course_name='" + this.getCourse().getName() + "',score='"
				+ this.getScore() + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleUniteExamScore.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
