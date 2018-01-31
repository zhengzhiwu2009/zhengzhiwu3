/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.training.activity.TrainingSelectCourseActivity;
import com.whaty.platform.training.basic.StudentCourseStatus;
import com.whaty.platform.training.basic.StudyProgress;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingSelectCourseActivity extends
		TrainingSelectCourseActivity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingSelectCourseActivity#selectCourses(java.util.Map)
	 */
	public void selectCourses(Map studentCourses) throws PlatformException {
		Iterator selects = studentCourses.entrySet().iterator();
		List sqlGroup = new ArrayList();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String courseId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				String studentStr = "";
				for (int i = 0; i < studentList.size(); i++) {
					studentStr = studentStr + "'"
							+ ((String) studentList.get(i)) + "',";
				}
				studentStr = studentStr.substring(0, studentStr.length() - 1);
				String sql = "insert into training_course_student(id,student_id,course_id,status,learn_status) "
						+ " select to_char(s_training_id.nextval),id,'"
						+ courseId
						+ "','"
						+ StudentCourseStatus.UNDERCHECK
						+ "','"
						+ StudyProgress.UNCOMPLETE
						+ "' from"
						+ " training_student where id in (" + studentStr + ")";
				UserAddLog.setDebug("OracleTrainingSelectCourseActivity.selectCourse(Map studentCourses) SQL=" + sql + " DATE=" + new Date());
				sqlGroup.add(sql);
			}
		}
		if (sqlGroup.size() > 0) {
			dbpool db = new dbpool();
			db.executeUpdateBatch(sqlGroup);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingSelectCourseActivity#unSelectCourses(java.util.Map)
	 */
	public void unSelectCourses(List studentCourses) throws PlatformException {
		if (studentCourses.size() > 0) {
			String studentStr = "";
			for (int i = 0; i < studentCourses.size(); i++) {
				studentStr = studentStr + "'" + (String) studentCourses.get(i)
						+ "',";
			}
			studentStr = studentStr.substring(0, studentStr.length() - 1);
			String sql = "delete from training_course_student where id in ("
					+ studentStr + ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserDeleteLog.setDebug("OracleTrainingSelectCourseActivity.unSelectCourse(Map studentCourses) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingSelectCourseActivity#checkSelectCourses(java.util.Map)
	 */
	public void checkSelectCourses(List studentCourses)
			throws PlatformException {
		if (studentCourses.size() > 0) {
			String studentStr = "";
			for (int i = 0; i < studentCourses.size(); i++) {
				studentStr = studentStr + "'" + (String) studentCourses.get(i)
						+ "',";
			}
			studentStr = studentStr.substring(0, studentStr.length() - 1);
			String sql = "update training_course_student set status='"
					+ StudentCourseStatus.SELECTED + "' where id in ("
					+ studentStr + ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleTrainingSelectCourseActivity.checkSelectCourse(List studentCourses) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingSelectCourseActivity#unSelectCourses(java.util.Map)
	 */
	public void unSelectCourses(Map studentCourses) throws PlatformException {
		Iterator selects = studentCourses.entrySet().iterator();
		List sqlGroup = new ArrayList();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String courseId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				String studentStr = "";
				for (int i = 0; i < studentList.size(); i++) {
					studentStr = studentStr + "'"
							+ ((String) studentList.get(i)) + "',";
				}
				studentStr = studentStr.substring(0, studentStr.length() - 1);
				String sql = "delete from training_course_student"
						+ " where course_id='" + courseId + "' "
						+ "and student_id in (" + studentStr + ")";
				UserDeleteLog.setDebug("OracleTrainingSelectCourseActivity.unSelectCourse(Map studentCourses) SQL=" + sql + " DATE=" + new Date());
				sqlGroup.add(sql);
			}
		}
		if (sqlGroup.size() > 0) {
			dbpool db = new dbpool();
			db.executeUpdateBatch(sqlGroup);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingSelectCourseActivity#checkSelectCourses(java.util.Map)
	 */
	public void checkSelectCourses(Map studentCourses) throws PlatformException {
		Iterator selects = studentCourses.entrySet().iterator();
		List sqlGroup = new ArrayList();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String courseId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				String studentStr = "";
				for (int i = 0; i < studentList.size(); i++) {
					studentStr = studentStr + "'"
							+ ((String) studentList.get(i)) + "',";
				}
				studentStr = studentStr.substring(0, studentStr.length() - 1);
				String sql = "update training_course_student set status='"
						+ StudentCourseStatus.SELECTED + "' "
						+ " where course_id='" + courseId + "' "
						+ "and student_id in (" + studentStr + ")";
				UserUpdateLog.setDebug("OracleTrainingSelectCourseActivity.checkSelectCourse(Map studentCourses) SQL=" + sql + " DATE=" + new Date());
				sqlGroup.add(sql);
			}
		}
		if (sqlGroup.size() > 0) {
			dbpool db = new dbpool();
			db.executeUpdateBatch(sqlGroup);
		}

	}

	public void unCheckSelectCourses(Map studentCourses)
			throws PlatformException {
		// TODO Auto-generated method stub
		Iterator selects = studentCourses.entrySet().iterator();
		List sqlGroup = new ArrayList();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String courseId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				String studentStr = "";
				for (int i = 0; i < studentList.size(); i++) {
					studentStr = studentStr + "'"
							+ ((String) studentList.get(i)) + "',";
				}
				studentStr = studentStr.substring(0, studentStr.length() - 1);
				String sql = "update training_course_student set status='"
						+ StudentCourseStatus.UNDERCHECK + "' "
						+ " where course_id='" + courseId + "' "
						+ "and student_id in (" + studentStr + ")";
				UserUpdateLog.setDebug("OracleTrainingSelectCourseActivity.unCheckSelectCourse(Map studentCourses) SQL=" + sql + " DATE=" + new Date());
				sqlGroup.add(sql);
			}
		}
		if (sqlGroup.size() > 0) {
			dbpool db = new dbpool();
			db.executeUpdateBatch(sqlGroup);
		}
	}

}
