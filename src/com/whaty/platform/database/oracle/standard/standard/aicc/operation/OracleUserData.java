package com.whaty.platform.database.oracle.standard.standard.aicc.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.file.AUData;
import com.whaty.platform.standard.aicc.file.DESData;
import com.whaty.platform.standard.aicc.model.AiccScore;
import com.whaty.platform.standard.aicc.model.Objectives;
import com.whaty.platform.standard.aicc.operation.UserAUData;
import com.whaty.platform.standard.aicc.operation.UserCourseData;
import com.whaty.platform.standard.aicc.operation.UserData;
import com.whaty.platform.standard.aicc.util.AiccLog;
import com.whaty.platform.standard.aicc.util.AiccUtil;

public class OracleUserData extends UserData {

	public OracleUserData(String student_id) {
		this.setStudent_id(student_id);

	}

	public List getCoursesData() throws AiccException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List courseList = new ArrayList();
		try {

			sql = "select a.LESSON_LOCATION,a.LESSON_STATUS,a.total_time,a.core_lesson,"
					+ "a.core_vendor,a.COMPLETE_PERCENT,a.course_id,b.title as course_title "
					+ "from AICC_STU_COURSE a,AICC_COURSE_INFO b "
					+ "where a.student_id='"
					+ this.getStudent_id()
					+ "' and a.course_id=b.id";
			AiccLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				UserCourseData userCourseData = new UserCourseData();
				userCourseData.getCore().setStudentId(this.getStudent_id());
				userCourseData.getCore().setStudentName(this.getStudent_name());
				userCourseData.getCore().setLessonLocation(
						rs.getString("LESSON_LOCATION"));
				userCourseData.getCore().setLessonStatus(
						rs.getString("LESSON_STATUS"));
				userCourseData.getCore().setTotalTime(
						AiccUtil.getTimeStrOfSecond(rs.getInt("total_time")));
				userCourseData.getSuspendData().setValue(
						rs.getString("core_lesson"));
				userCourseData.getLaunchData().setValue(
						rs.getString("core_vendor"));
				userCourseData.setCompletePercent(rs
						.getString("COMPLETE_PERCENT"));
				OracleAiccCourse course = new OracleAiccCourse();
				course.getCourse().setCourseTitle(sql);
				course.getCourse().setCourseTitle(rs.getString("course_title"));
				userCourseData.setAiccCourse(course);
				courseList.add(userCourseData);
			}
		} catch (java.sql.SQLException e) {
			throw new AiccException("Get student's courses error!");
		} finally {
			db.close(rs);
			db = null;

		}
		return courseList;
	}

	public List getAUsData(String course_id) throws AiccException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List auList = new ArrayList();
		try {

			sql = "select a.system_id,a.raw_score,a.status,a.total_time,c.develop_id,"
					+ "c.title as au_title"
					+ " from AICC_STU_AU a, aicc_course_au b,aicc_course_des c "
					+ "where a.student_id='"
					+ this.getStudent_id()
					+ "' "
					+ "and a.course_id='"
					+ course_id
					+ "' and b.course_id='"
					+ course_id
					+ "'"
					+ "and c.course_id='"
					+ course_id
					+ "' and a.system_id=b.system_id "
					+ " and a.system_id=c.system_id ";
			AiccLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				UserAUData userAuData = new UserAUData();
				AUData auData = new AUData();
				DESData desData = new DESData();
				userAuData.setSystemId(rs.getString("system_id"));
				userAuData.getCore().setScore(
						new AiccScore(rs.getString("raw_score")));
				userAuData.getCore().setLessonStatus(rs.getString("status"));
				userAuData.getCore().setTotalTime(
						AiccUtil.getTimeStrOfSecond(rs.getInt("total_time")));
				desData.setSystemId(rs.getString("system_id"));
				desData.setTitle(rs.getString("au_title"));
				desData.setDeveloperId(rs.getString("develop_id"));
				auData.setSystemId(rs.getString("system_id"));
				userAuData.setAuData(auData);
				userAuData.setDesData(desData);
				auList.add(userAuData);
			}
		} catch (java.sql.SQLException e) {
			throw new AiccException("Get student's courses error!");
		} finally {
			db.close(rs);
			db = null;

		}
		return auList;
	}

	/**
	 * @param course_id
	 * @param ausystem_id
	 * @return
	 * @throws AiccException
	 */
	public Objectives getObjectivsData(String course_id, String ausystem_id)
			throws AiccException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		Objectives objectives = null;
		try {

			String sqlObj = "select a.sub_id,a.objectives_id,b.develop_id,a.raw_score,a.status,b.title as obj_title "
					+ "from (select sub_id,objectives_id,raw_score,status "
					+ " from aicc_stu_objectives"
					+ " where course_id='"
					+ course_id
					+ "' and student_id='"
					+ this.getStudent_id()
					+ "' "
					+ " and system_id='"
					+ ausystem_id
					+ "') a,"
					+ " aicc_course_des b where b.course_id='"
					+ course_id
					+ "' "
					+ " and  a.objectives_id=b.develop_id order by to_number(a.sub_id)";
			AiccLog.setDebug(sqlObj);
			rs = db.executeQuery(sqlObj);
			Map jIdMap = new HashMap();
			Map jDesMap = new HashMap();
			Map jScoreMap = new HashMap();
			Map jStatusMap = new HashMap();
			while (rs != null && rs.next()) {
				AiccLog.setDebug("objectives=" + rs.getString("sub_id")
						+ "____" + rs.getString("objectives_id") + "___"
						+ rs.getString("raw_score") + "__"
						+ rs.getString("status"));
				jIdMap.put(rs.getString("sub_id"), rs
						.getString("objectives_id"));
				DESData desData = new DESData();
				desData.setTitle(rs.getString("obj_title"));
				jDesMap.put(rs.getString("sub_id"), desData);
				jScoreMap
						.put(rs.getString("sub_id"), rs.getString("raw_score"));
				jStatusMap.put(rs.getString("sub_id"), rs.getString("status"));
			}
			objectives = new Objectives(jIdMap, jDesMap, jScoreMap, jStatusMap);

		} catch (java.sql.SQLException e) {
			throw new AiccException("Get student's courses error!");
		} finally {
			db.close(rs);
			db = null;
			return objectives;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.standard.aicc.operation.UserData#getCourseData(java.lang.String)
	 */
	public UserCourseData getCourseData(String courseId) throws AiccException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		UserCourseData userCourseData = null;
		try {
			sql = "select a.LESSON_LOCATION,a.LESSON_STATUS,a.total_time,a.core_lesson,"
					+ "a.core_vendor,a.COMPLETE_PERCENT,a.course_id,b.title as course_title from"
					+ " AICC_STU_COURSE a,AICC_COURSE_INFO b "
					+ " where a.student_id='"
					+ this.getStudent_id()
					+ "' and a.course_id=b.id "
					+ " and a.course_id='"
					+ courseId + "'";
			AiccLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				userCourseData = new UserCourseData();
				userCourseData.getCore().setStudentId(this.getStudent_id());
				userCourseData.getCore().setStudentName(this.getStudent_name());
				userCourseData.getCore().setLessonLocation(
						rs.getString("LESSON_LOCATION"));
				userCourseData.getCore().setLessonStatus(
						rs.getString("LESSON_STATUS"));
				userCourseData.getCore().setTotalTime(
						AiccUtil.getTimeStrOfSecond(rs.getInt("total_time")));
				userCourseData.getSuspendData().setValue(
						rs.getString("core_lesson"));
				userCourseData.getLaunchData().setValue(
						rs.getString("core_vendor"));
				userCourseData.setCompletePercent(rs
						.getString("COMPLETE_PERCENT"));
				OracleAiccCourse course = new OracleAiccCourse();
				course.getCourse().setCourseTitle(sql);
				course.getCourse().setCourseTitle(rs.getString("course_title"));
				userCourseData.setAiccCourse(course);

			}
		} catch (java.sql.SQLException e) {
			throw new AiccException("Get student's courses error!");
		} finally {
			db.close(rs);
			db = null;
			return userCourseData;

		}

	}

}
