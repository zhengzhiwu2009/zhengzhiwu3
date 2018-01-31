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
import com.whaty.platform.database.oracle.standard.training.skill.OracleSkill;
import com.whaty.platform.training.activity.TrainingSelectSkillActivity;
import com.whaty.platform.training.skill.StudentSkillStatus;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingSelectSkillActivity extends
		TrainingSelectSkillActivity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingSelectCourseActivity#selectCourses(java.util.Map)
	 */
	public void selectSkill(Map studentClasses) throws PlatformException {
		Iterator selects = studentClasses.entrySet().iterator();
		List sqlGroup = new ArrayList();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String skillId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				String studentStr = "";
				for (int i = 0; i < studentList.size(); i++) {
					studentStr = studentStr + "'"
							+ ((String) studentList.get(i)) + "',";
				}
				studentStr = studentStr.substring(0, studentStr.length() - 1);
				String sql = "insert into training_skill_student(id,student_id,skill_id,status) "
						+ " select to_char(s_training_skill_student_id.nextval),id,'"
						+ skillId
						+ "','"
						+ StudentSkillStatus.CHECKAPPLY
						+ "' from"
						+ " training_student where id in ("
						+ studentStr + ")";
				UserAddLog.setDebug("OracleTrainingSelectSkillActivity.selectSkill(Map studentClasses) SQL=" + sql + " DATE=" + new Date());
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
	public void unSelectSkill(List studentClasses) throws PlatformException {
		if (studentClasses.size() > 0) {
			String studentStr = "";
			for (int i = 0; i < studentClasses.size(); i++) {
				studentStr = studentStr + "'" + (String) studentClasses.get(i)
						+ "',";
			}
			studentStr = studentStr.substring(0, studentStr.length() - 1);
			String sql = "delete from training_skill_student where id in ("
					+ studentStr + ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserDeleteLog.setDebug("OracleTrainingSelectSkillActivity.unSelectSkill(List studentClasses) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingSelectCourseActivity#checkSelectCourses(java.util.Map)
	 */
	public void checkSelectSkill(List studentClasses) throws PlatformException {
		if (studentClasses.size() > 0) {
			String studentStr = "";
			for (int i = 0; i < studentClasses.size(); i++) {
				studentStr = studentStr + "'" + (String) studentClasses.get(i)
						+ "',";
			}
			studentStr = studentStr.substring(0, studentStr.length() - 1);
			String sql = "update training_skill_student set status='"
					+ StudentSkillStatus.APPLYED + "' where id in ("
					+ studentStr + ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleTrainingSelectSkillActivity.checkSelectSkill(List studentClasses) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingSelectCourseActivity#unSelectCourses(java.util.Map)
	 */
	public void unSelectSkill(Map studentClasses) throws PlatformException {
		Iterator selects = studentClasses.entrySet().iterator();
		List sqlGroup = new ArrayList();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String skillId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				String studentStr = "";
				for (int i = 0; i < studentList.size(); i++) {
					studentStr = studentStr + "'"
							+ ((String) studentList.get(i)) + "',";
				}
				studentStr = studentStr.substring(0, studentStr.length() - 1);
				String sql = "delete from training_skill_student"
						+ " where skill_id='" + skillId + "' "
						+ "and student_id in (" + studentStr + ")";
				UserDeleteLog.setDebug("OracleTrainingSelectSkillActivity.unSelectSkill(Map studentClasses) SQL=" + sql + " DATE=" + new Date());
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
	public void checkSelectSkill(Map studentClasses) throws PlatformException {
		Iterator selects = studentClasses.entrySet().iterator();
		List sqlGroup = new ArrayList();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String skillId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				String studentStr = "";
				for (int i = 0; i < studentList.size(); i++) {
					studentStr = studentStr + "'"
							+ ((String) studentList.get(i)) + "',";
				}
				studentStr = studentStr.substring(0, studentStr.length() - 1);
				String sql = "update training_skill_student set status='"
						+ StudentSkillStatus.APPLYED + "' "
						+ " where skill_id='" + skillId + "' "
						+ "and student_id in (" + studentStr + ")";
				UserUpdateLog.setDebug("OracleTrainingSelectSkillActivity.checkSelectSkill(Map studentClasses) SQL=" + sql + " DATE=" + new Date());
				sqlGroup.add(sql);
			}
		}
		if (sqlGroup.size() > 0) {
			dbpool db = new dbpool();
			db.executeUpdateBatch(sqlGroup);
		}

	}

	public void unCheckSelectSkill(Map studentClasses) throws PlatformException {
		Iterator selects = studentClasses.entrySet().iterator();
		List sqlGroup = new ArrayList();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String skillId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				String studentStr = "";
				for (int i = 0; i < studentList.size(); i++) {
					studentStr = studentStr + "'"
							+ ((String) studentList.get(i)) + "',";
				}
				studentStr = studentStr.substring(0, studentStr.length() - 1);
				String sql = "update training_skill_student set status='"
						+ StudentSkillStatus.CHECKAPPLY + "' "
						+ " where skill_id='" + skillId + "' "
						+ "and student_id in (" + studentStr + ")";
				UserUpdateLog.setDebug("OracleTrainingSelectSkillActivity.unCheckSelectSkill(Map studentClasses) SQL=" + sql + " DATE=" + new Date());
				sqlGroup.add(sql);
			}
		}
		if (sqlGroup.size() > 0) {
			dbpool db = new dbpool();
			db.executeUpdateBatch(sqlGroup);
		}
	}

	public void targetSkill(Map studentClasses) throws PlatformException {
		Iterator selects = studentClasses.entrySet().iterator();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String skillId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				OracleSkill skill = new OracleSkill(skillId);
				skill.target(studentList);
			}
		}

	}

	public void unTargetSkill(Map studentClasses) throws PlatformException {
		Iterator selects = studentClasses.entrySet().iterator();
		while (selects.hasNext()) {
			Map.Entry select = (Map.Entry) selects.next();
			String skillId = (String) select.getKey();
			List studentList = (List) select.getValue();
			if (studentList.size() > 0) {
				OracleSkill skill = new OracleSkill(skillId);
				skill.unTarget(studentList);
			}
		}
	}

}
