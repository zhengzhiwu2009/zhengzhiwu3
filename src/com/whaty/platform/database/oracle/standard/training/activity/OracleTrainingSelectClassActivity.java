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
import com.whaty.platform.training.activity.TrainingSelectClassActivity;
import com.whaty.platform.training.basic.StudentClassStatus;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingSelectClassActivity extends
		TrainingSelectClassActivity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingSelectCourseActivity#selectCourses(java.util.Map)
	 */
	public void selectClasses(Map studentClasses) throws PlatformException {
		Iterator selects = studentClasses.entrySet().iterator();
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
				String sql = "insert into training_class_student(id,student_id,class_id,status) "
						+ " select to_char(s_training_class_student_id.nextval),id,'"
						+ courseId
						+ "','"
						+ StudentClassStatus.UNDERCHECK
						+ "' from"
						+ " training_student where id in ("
						+ studentStr + ")";
				UserAddLog.setDebug("OracleTrainingSelectClassActivity.selectClasses(Map studentClasses) SQL=" + sql + " DATE=" + new Date());
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
	public void unSelectClasses(List studentClasses) throws PlatformException {
		if (studentClasses.size() > 0) {
			String studentStr = "";
			for (int i = 0; i < studentClasses.size(); i++) {
				studentStr = studentStr + "'" + (String) studentClasses.get(i)
						+ "',";
			}
			studentStr = studentStr.substring(0, studentStr.length() - 1);
			String sql = "delete from training_class_student where id in ("
					+ studentStr + ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserDeleteLog.setDebug("OracleTrainingSelectClassActivity.unSelectClasses(List studentClasses) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingSelectCourseActivity#checkSelectCourses(java.util.Map)
	 */
	public void checkSelectClasses(List studentClasses)
			throws PlatformException {
		if (studentClasses.size() > 0) {
			String studentStr = "";
			for (int i = 0; i < studentClasses.size(); i++) {
				studentStr = studentStr + "'" + (String) studentClasses.get(i)
						+ "',";
			}
			studentStr = studentStr.substring(0, studentStr.length() - 1);
			String sql = "update training_class_student set status='"
					+ StudentClassStatus.SELECTED + "' where id in ("
					+ studentStr + ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleTrainingSelectClassActivity.checkSelectClasses(List studentClasses) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingSelectCourseActivity#unSelectCourses(java.util.Map)
	 */
	public void unSelectClasses(Map studentClasses) throws PlatformException {
		Iterator selects = studentClasses.entrySet().iterator();
		List sqlGroup = new ArrayList();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String classId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				String studentStr = "";
				for (int i = 0; i < studentList.size(); i++) {
					studentStr = studentStr + "'"
							+ ((String) studentList.get(i)) + "',";
				}
				studentStr = studentStr.substring(0, studentStr.length() - 1);
				String sql = "delete from training_class_student"
						+ " where class_id='" + classId + "' "
						+ "and student_id in (" + studentStr + ")";
				UserDeleteLog.setDebug("OracleTrainingSelectClassActivity.unSelectClasses(Map studentClasses) SQL=" + sql + " DATE=" + new Date());
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
	public void checkSelectClasses(Map studentClasses) throws PlatformException {
		Iterator selects = studentClasses.entrySet().iterator();
		List sqlGroup = new ArrayList();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String classId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				String studentStr = "";
				for (int i = 0; i < studentList.size(); i++) {
					studentStr = studentStr + "'"
							+ ((String) studentList.get(i)) + "',";
				}
				studentStr = studentStr.substring(0, studentStr.length() - 1);
				String sql = "update training_class_student set status='"
						+ StudentClassStatus.SELECTED + "' "
						+ " where class_id='" + classId + "' "
						+ "and student_id in (" + studentStr + ")";
				UserUpdateLog.setDebug("OracleTrainingSelectClassActivity.checkSelectClasses(Map studentClasses) SQL=" + sql + " DATE=" + new Date());
				sqlGroup.add(sql);
			}
		}
		if (sqlGroup.size() > 0) {
			dbpool db = new dbpool();
			db.executeUpdateBatch(sqlGroup);
		}

	}

	public void unCheckSelectClasses(Map studentClasses)
			throws PlatformException {
		// TODO Auto-generated method stub
		Iterator selects = studentClasses.entrySet().iterator();
		List sqlGroup = new ArrayList();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String classId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				String studentStr = "";
				for (int i = 0; i < studentList.size(); i++) {
					studentStr = studentStr + "'"
							+ ((String) studentList.get(i)) + "',";
				}
				studentStr = studentStr.substring(0, studentStr.length() - 1);
				String sql = "update training_class_student set status='"
						+ StudentClassStatus.UNDERCHECK + "' "
						+ " where class_id='" + classId + "' "
						+ "and student_id in (" + studentStr + ")";
				UserUpdateLog.setDebug("OracleTrainingSelectClassActivity.unCheckSelectClasses(Map studentClasses) SQL=" + sql + " DATE=" + new Date());
				sqlGroup.add(sql);
			}
		}
		if (sqlGroup.size() > 0) {
			dbpool db = new dbpool();
			db.executeUpdateBatch(sqlGroup);
		}
	}

}
