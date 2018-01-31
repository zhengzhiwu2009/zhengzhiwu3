/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.basic;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudent;
import com.whaty.platform.training.basic.StudentCourse;
import com.whaty.platform.training.basic.StudyProgress;
import com.whaty.platform.training.basic.TrainingCourse;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.log.TrainingLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleStudentCourse extends StudentCourse {
	
	public OracleStudentCourse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleStudentCourse(String id) throws PlatformException {
		String sql = "select course_id,status,percent,learn_status,"
				+ "to_char(get_date,'yyyy-mm-dd') as get_date from "
				+ "training_course_student where id='" + id + "'";
		TrainingLog.setDebug(sql);
		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				OracleTrainingStudent student = new OracleTrainingStudent();
				student.setId(rs.getString("student_id"));
				this.setTrainingStudent(student);
				OracleTrainingCourse course = new OracleTrainingCourse();
				course.setId(rs.getString("course_id"));
				this.setTrainingCourse(course);
				this.setGetDate(rs.getString("get_date"));
				StudyProgress learnProgress = new StudyProgress();
				learnProgress.setPercent(rs.getFloat("percent"));
				learnProgress.setLearnStatus(rs.getString("learn_status"));
				this.setLearnProgress(learnProgress);
				this.setStatus(rs.getString("status"));
			}
		} catch (SQLException e) {
			throw new PlatformException(e.toString());
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public OracleStudentCourse(TrainingStudent student, TrainingCourse course)
			throws PlatformException {
		this.setTrainingStudent(student);
		this.setTrainingCourse(course);
		if (!this.isExist())
			throw new PlatformException("student don't select the course!");
		else {
			String sql = "select course_id,status,percent,learn_status,"
					+ "to_char(get_date,'yyyy-mm-dd') as get_date from "
					+ "training_course_student where student_id='" + ""
					+ this.getTrainingStudent().getId() + "' and "
					+ "course_id='" + this.getTrainingCourse().getId() + "'";
			TrainingLog.setDebug(sql);
			dbpool db = new dbpool();
			MyResultSet rs = null;
			try {
				rs = db.executeQuery(sql);
				if (rs != null && rs.next()) {
					this.setGetDate(rs.getString("get_date"));
					StudyProgress learnProgress = new StudyProgress();
					learnProgress.setPercent(rs.getFloat("percent"));
					learnProgress.setLearnStatus(rs.getString("learn_status"));
					this.setLearnProgress(learnProgress);
					this.setStatus(rs.getString("status"));
				}
			} catch (SQLException e) {
				throw new PlatformException(e.toString());
			} finally {
				db.close(rs);
				db = null;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.StudentCourse#update()
	 */
	public void update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update training_student_course " + "set percent="
				+ this.getLearnProgress().getPercent() + "," + "learn_status='"
				+ this.getLearnProgress().getLearnStatus() + "'" + "status='"
				+ this.getStatus() + "' where student_id='" + ""
				+ this.getTrainingStudent().getId() + "' and " + "course_id='"
				+ this.getTrainingCourse().getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleStudentCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.StudentCourse#isExist()
	 */
	public boolean isExist() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select id from training_course_student where student_id='"
				+ ""
				+ this.getTrainingStudent().getId()
				+ "' and "
				+ "course_id='" + this.getTrainingCourse().getId() + "'";
		
		if (db.countselect(sql) < 1)
			return false;
		else
			return true;

	}

}
