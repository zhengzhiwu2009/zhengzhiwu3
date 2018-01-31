/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.activity;

import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.training.activity.TrainingBasicActivity;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingBasicActivity extends TrainingBasicActivity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingBasicActivity#activeTrainingClass(java.util.List,
	 *      boolean)
	 */
	public void activeTrainingClass(List classIds, boolean flag)
			throws PlatformException {
		String classIdStr = "";
		if (classIds == null && classIds.size() < 1)
			return;
		else {
			for (int i = 0; i < classIds.size(); i++) {
				classIdStr = classIdStr + "'" + (String) classIds.get(i) + "',";
			}
			if (classIdStr.length() > 3)
				classIdStr = classIdStr.substring(0, classIdStr.length() - 1);
			dbpool db = new dbpool();
			int active = 0;
			if (flag)
				active = 1;
			String sql = "update training_class set status=" + active
					+ " where id in(" + classIdStr + ")";
			UserUpdateLog.setDebug("OracleTrainingBasicActivity.activeTrainingClass(List classIds, boolean flag) SQL=" + sql + " DATE=" + new Date());
			db.executeUpdate(sql);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingBasicActivity#activeTrainingCourse(java.util.List,
	 *      boolean)
	 */
	public void activeTrainingCourse(List courseIds, boolean flag)
			throws PlatformException {
		String courseIdStr = "";
		if (courseIds == null && courseIds.size() < 1)
			return;
		else {
			for (int i = 0; i < courseIds.size(); i++) {
				courseIdStr = courseIdStr + "'" + (String) courseIds.get(i)
						+ "',";
			}
			if (courseIdStr.length() > 3)
				courseIdStr = courseIdStr
						.substring(0, courseIdStr.length() - 1);
			dbpool db = new dbpool();
			int active = 0;
			if (flag)
				active = 1;
			String sql = "update training_course set status=concat('" + active
					+ "',substr(status,2,8)) where id in(" + courseIdStr + ")";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleTrainingBasicActivity.activeTrainingCourse(List courseIds, boolean flag) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingBasicActivity#activeSkill(java.util.List,
	 *      boolean)
	 */
	public void activeSkill(List skillIds, boolean flag)
			throws PlatformException {
		String skillIdStr = "";
		if (skillIds == null && skillIds.size() < 1)
			return;
		else {
			for (int i = 0; i < skillIds.size(); i++) {
				skillIdStr = skillIdStr + "'" + (String) skillIds.get(i) + "',";
			}
			if (skillIdStr.length() > 3)
				skillIdStr = skillIdStr.substring(0, skillIdStr.length() - 1);
			dbpool db = new dbpool();
			int active = 0;
			if (flag)
				active = 1;
			String sql = "update training_skill set status=" + active
					+ " where id in(" + skillIdStr + ")";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleTrainingBasicActivity.activeSkill(List skillIds, boolean flag) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	public void activeSkillChain(List skillChainIds, boolean flag)
			throws PlatformException {
		String skillChainIdStr = "";
		if (skillChainIds == null && skillChainIds.size() < 1)
			return;
		else {
			for (int i = 0; i < skillChainIds.size(); i++) {
				skillChainIdStr = skillChainIdStr + "'" + (String) skillChainIds.get(i) + "',";
			}
			if (skillChainIdStr.length() > 3)
				skillChainIdStr = skillChainIdStr.substring(0, skillChainIdStr.length() - 1);
			dbpool db = new dbpool();
			int active = 0;
			if (flag)
				active = 1;
			String sql = "update training_skill_chain set status=" + active
					+ " where id in(" + skillChainIdStr + ")";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleTrainingBasicActivity.activeSkillChain(List skillChainIds, boolean flag) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingBasicActivity#activeTrainingCourseType(java.util.List,
	 *      boolean)
	 */
	public void activeTrainingCourseType(List typeIds, boolean flag)
			throws PlatformException {
		String typeIdStr = "";
		if (typeIds == null && typeIds.size() < 1)
			return;
		else {
			for (int i = 0; i < typeIds.size(); i++) {
				typeIdStr = typeIdStr + "'" + (String) typeIds.get(i) + "',";
			}
			if (typeIdStr.length() > 3)
				typeIdStr = typeIdStr.substring(0, typeIdStr.length() - 1);
			dbpool db = new dbpool();
			int active = 0;
			if (flag)
				active = 1;
			String sql = "update training_course_type set status=" + active
					+ " where id in(" + typeIdStr + ")";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleTrainingBasicActivity.activeSkillCourseType(List TypeIds, boolean flag) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.activity.TrainingBasicActivity#activeTrainingStudent(java.util.List,
	 *      boolean)
	 */
	public void activeTrainingStudent(List studentIdList, boolean flag)
			throws PlatformException {
		String studentIdStr = "";
		if (studentIdList == null && studentIdList.size() < 1)
			return;
		else {
			for (int i = 0; i < studentIdList.size(); i++) {
				studentIdStr = studentIdStr + "'"
						+ (String) studentIdList.get(i) + "',";
			}
			if (studentIdStr.length() > 3)
				studentIdStr = studentIdStr.substring(0,
						studentIdStr.length() - 1);
			dbpool db = new dbpool();
			int active = 0;
			if (flag)
				active = 1;
			String sql = "update training_student set status=" + active
					+ " where id in(" + studentIdStr + ")";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleTrainingBasicActivity.activeTrainingStudent(List studentIdList, boolean flag) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

}
