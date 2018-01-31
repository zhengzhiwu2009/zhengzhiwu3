package com.whaty.platform.database.oracle.standard.standard.aicc.operation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.model.AiccData;
import com.whaty.platform.standard.aicc.model.AiccScore;
import com.whaty.platform.standard.aicc.model.AiccStatus;
import com.whaty.platform.standard.aicc.model.Core;
import com.whaty.platform.standard.aicc.model.LaunchData;
import com.whaty.platform.standard.aicc.model.Objective;
import com.whaty.platform.standard.aicc.model.Objectives;
import com.whaty.platform.standard.aicc.model.SuspendData;
import com.whaty.platform.standard.aicc.operation.AiccDataManage;
import com.whaty.platform.standard.aicc.util.AiccLog;
import com.whaty.platform.standard.aicc.util.AiccUtil;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

public class OracleAiccDataManage extends AiccDataManage {
    
	public OracleAiccDataManage() {

	}

	public void putValueToDB(AiccData aiccData, String user_id,
			String course_id, String ausystem_id, String session_id)
			throws AiccException {

		AiccLog.setDebug("putValueToDB begin!!!!!!");
		AiccLog.setDebug("user_id=" + user_id);
		AiccLog.setDebug("course_id" + course_id);

		// ޸ѧAU¼

		updateStuAUInfo(aiccData, user_id, course_id, ausystem_id, session_id);

		// ޸ѧĿγ̼¼
		updateStuCourseInfo(aiccData, user_id, course_id, session_id);

	}

	public AiccData getValueFromDB(String user_id, String course_id,
			String ausystem_id, String session_id) throws AiccException {
		AiccData aiccData = new AiccData();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		MyResultSet rsObj = null;
		try {

			// ûиѧĿγѧϰ¼ü¼
			insertStuCourseInfo(user_id, course_id, session_id);
			// ûиѧAUѧϰ¼ü¼
			insertStuAUInfo(user_id, course_id, ausystem_id, session_id);

			sql = "select CREDIT,LESSON_LOCATION,STATUS,CORE_VENDOR,"
					+ "RAW_SCORE,MAX_SCORE,MIN_SCORE,TOTAL_TIME,LESSON_MODE,CORE_LESSON,ATTEMPT_NUM"
					+ " from AICC_STU_AU where course_id='" + course_id + "' "
					+ "and student_id='" + user_id + "' and system_id='"
					+ ausystem_id + "'";
			// AiccLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {

				Core core = new Core();
				core.setStudentId(user_id);
				core.setStudentName(user_id);
				core.setLessonLocation(rs.getString("LESSON_LOCATION"));
				core.setLessonStatus(rs.getString("STATUS"));
				core.setCredit(rs.getString("CREDIT"));
				AiccScore score = new AiccScore();
				if (rs.getString("RAW_SCORE") == null
						|| rs.getString("RAW_SCORE").equalsIgnoreCase("null")
						|| rs.getString("RAW_SCORE").equals(""))
					score.setRawScore("0");
				else
					score.setRawScore(rs.getString("RAW_SCORE"));
				if (rs.getString("MAX_SCORE") == null
						|| rs.getString("MAX_SCORE").equalsIgnoreCase("null")
						|| rs.getString("MAX_SCORE").equals(""))
					score.setMaxScore(null);
				else
					score.setMaxScore(rs.getString("MAX_SCORE"));
				if (rs.getString("MIN_SCORE") == null
						|| rs.getString("MIN_SCORE").equalsIgnoreCase("null")
						|| rs.getString("MIN_SCORE").equals(""))
					score.setMinScore(null);
				else
					score.setMinScore(rs.getString("MIN_SCORE"));
				core.setScore(score);
				core.setTotalTime(AiccUtil.getTimeStrOfSecond(rs
						.getInt("TOTAL_TIME")));
				core.setLessonMode(rs.getString("LESSON_MODE"));
				aiccData.setCore(core);
				LaunchData launchData = new LaunchData();
				launchData.setValue(rs.getString("CORE_VENDOR"));
				aiccData.setLaunchData(launchData);
				SuspendData suspendData = new SuspendData();
				suspendData.setValue(rs.getString("CORE_LESSON"));
				aiccData.setSuspendData(suspendData);
				String sqlObj = "select sub_id,objectives_id,raw_score,status  "
						+ "from aicc_stu_objectives where course_id='"
						+ course_id
						+ "' "
						+ "and student_id='"
						+ user_id
						+ "' and system_id='"
						+ ausystem_id
						+ "' "
						+ "order by to_number(sub_id)";
				// AiccLog.setDebug(sqlObj);
				rsObj = db.executeQuery(sqlObj);
				Map jIdMap = new HashMap();
				Map jScoreMap = new HashMap();
				Map jStatusMap = new HashMap();
				while (rsObj != null && rsObj.next()) {
					AiccLog.setDebug("objectives=" + rsObj.getString("sub_id")
							+ "____" + rsObj.getString("objectives_id") + "___"
							+ rsObj.getString("raw_score") + "__"
							+ rsObj.getString("status"));
					jIdMap.put(rsObj.getString("sub_id"), rsObj
							.getString("objectives_id"));
					jScoreMap.put(rsObj.getString("sub_id"), rsObj
							.getString("raw_score"));
					jStatusMap.put(rsObj.getString("sub_id"), rsObj
							.getString("status"));
				}
				Objectives objectives = new Objectives(jIdMap, jScoreMap,
						jStatusMap);
				AiccLog.setDebug("objectives=" + objectives.toStrData());
				aiccData.setObjectives(objectives);
			}
		} catch (java.sql.SQLException e) {
			throw new AiccException("Get values from dB error!");
		} finally {
			db.close(rs);
			db.close(rsObj);
			db = null;
			return aiccData;
		}
	}

	public void exitAU(String user_id, String course_id, String ausystem_id,
			String session_id) throws AiccException {
		AiccLog.setDebug("ExitAU begin!!!!!!");

		// ޸Ŀγ̵ɼ¼ѧѧϰ
		updateAndExitStuCourse(user_id, course_id, session_id);

	}

	private void updateAndExitStuCourse(String user_id, String course_id,
			String session_id) throws AiccException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		int totalAus = 0;
		int totalBlocks = 0;
		int totalObjectives = 0;
		int totalComplexObjectives = 0;
		try {
			sql = "select TOTAL_AUS,TOTAL_BLOCKS,TOTAL_OBJECTIVES,TOTAL_COMPLEX_OBJECTIVES from aicc_course_info where id='"
					+ course_id + "'";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				totalAus = rs.getInt("TOTAL_AUS");
				totalBlocks = rs.getInt("TOTAL_BLOCKS");
				totalObjectives = rs.getInt("TOTAL_OBJECTIVES");
				totalComplexObjectives = rs.getInt("TOTAL_COMPLEX_OBJECTIVES");
			}
		} catch (java.sql.SQLException e) {
			throw new AiccException("update And Exit Student Course error!");
		} finally {
			db.close(rs);
		}
		AiccLog.setDebug("total_aus=" + totalAus + "  totalObjectives="
				+ totalObjectives);
		Float completedPercent;
		if (totalAus < 2)
		// AUϵͳոAUµObjectives
		{
			sql = "select id  from aicc_stu_objectives where course_id='"
					+ course_id
					+ "' "
					+ "and student_id='"
					+ user_id
					+ "' and (status='completed' or status='passed')"
					+ " and system_id in (select system_id from aicc_course_au "
					+ "where course_id='" + course_id + "')";
			int total = db.countselect(sql);
			AiccLog.setDebug(sql);
			completedPercent = new Float(total * 100.0 / totalObjectives);
			AiccLog.setDebug("completedobjectives percent="
					+ completedPercent.toString());
			if (total == totalObjectives) {
				sql = "update aicc_stu_au set status='" + AiccStatus.COMPLETED
						+ "' " + "where course_id='" + course_id
						+ "' and student_id='" + user_id + "'";
			} else if (total > 0) {
				sql = "update aicc_stu_au set status='" + AiccStatus.INCOMPLETE
						+ "' " + "where course_id='" + course_id
						+ "' and student_id='" + user_id + "'";
			} else {
				sql = "update aicc_stu_au set status='"
						+ AiccStatus.NOTATTEMPTED + "' " + "where course_id='"
						+ course_id + "' and student_id='" + user_id + "'";
			}
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleAiccDataManage.updateAndExitStuCourse(String user_id, String course_id,String session_id) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
			if (i < 1) {
				throw new AiccException("update aicc_stu_au of the course:"
						+ course_id + " and student_id=" + user_id + " error!");
			}
		} else
		// AUϵͳAU
		{
			sql = "select id from aicc_stu_au where course_id='"
					+ course_id
					+ "' "
					+ "and student_id='"
					+ user_id
					+ "' and (status='completed' or status='passed')"
					+ " and system_id in (select system_id from aicc_course_au "
					+ "where course_id='" + course_id + "')";
//			AiccLog.setDebug(sql);
			int total = db.countselect(sql);
			completedPercent = new Float(total * 100.0 / totalAus);
			AiccLog.setDebug("completedobjectives percent="
					+ completedPercent.toString());
		}

		if (completedPercent.floatValue() > 99.9) {
			sql = "update aicc_stu_course set COMPLETE_PERCENT=100,lesson_status='"
					+ AiccStatus.COMPLETED
					+ "' where course_id='"
					+ course_id
					+ "' and student_id='" + user_id + "'";
		} else {
			sql = "update aicc_stu_course set COMPLETE_PERCENT="
					+ completedPercent + ", lesson_status='"
					+ AiccStatus.INCOMPLETE + "' where course_id='" + course_id
					+ "' and student_id='" + user_id + "'";
		}
		int flag = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleAiccDataManage.updateAndExitStuCourse(String user_id, String course_id,String session_id) SQL=" + sql + "COUNT=" + flag + " DATE=" + new Date());
		if (flag < 1) {
			throw new AiccException("compute the percent of the course:"
					+ course_id + " and student_id=" + user_id);
		} else {
			updatePlatformDB(user_id, course_id, completedPercent);
		}
	}

	/**
	 * ÷GetParamʱѧγݵĳʼͬʱ趨ÿγ̵ʱԼʿγ̵Ĵһ
	 * 
	 * @param user_id
	 * @param course_id
	 * @param session_id
	 * @throws AiccException
	 */
	private void insertStuCourseInfo(String user_id, String course_id,
			String session_id) throws AiccException {
		dbpool db = new dbpool();
		String sql = "";
		StrManage strManage = StrManageFactory.creat();
		String dateTimeStr = null;
		try {
			dateTimeStr = strManage
					.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
		} catch (WhatyUtilException e) {
			// TODO Auto-generated catch block
			
		}
		sql = "select id from aicc_stu_course where student_id='" + user_id
				+ "' and course_id='" + course_id + "'";
		int count = db.countselect(sql);
		int i = 0;

		// GetParamʱûиѧĿγѧϰ¼ü¼
		if (count < 1) {
			sql = "insert into aicc_stu_course(ID,STUDENT_ID,COURSE_ID,"
					+ "FIRST_DATE,SESSION_DATE)"
					+ " values(to_char(s_stu_course_id.nextval),'" + user_id
					+ "','" + course_id + "'," + "to_date('" + dateTimeStr
					+ "','yyyy-mm-dd hh24:mi:ss')," + "to_date('" + dateTimeStr
					+ "','yyyy-mm-dd hh24:mi:ss'))";
		} else {
			sql = "update aicc_stu_course set attempt_num=attempt_num+1,"
					+ " SESSION_DATE=to_date('" + dateTimeStr
					+ "','yyyy-mm-dd hh24:mi:ss')" + " where student_id='"
					+ user_id + "' and course_id='" + course_id + "'";
		}
		i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleAiccDataManage.insertStuCourseInfo(String user_id, String course_id,String session_id) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		if (i < 1) {
			throw new AiccException("check the student and course error!");
		}

	}

	/**
	 * GetParamʱʼѧѧϰAUļ¼ͬʱʴһ
	 * 
	 * @param user_id
	 * @param course_id
	 * @param ausystem_id
	 * @param session_id
	 * @throws AiccException
	 */
	private void insertStuAUInfo(String user_id, String course_id,
			String ausystem_id, String session_id) throws AiccException {
		dbpool db = new dbpool();
		String sql = "";
		StrManage strManage = StrManageFactory.creat();
		String dateTimeStr = null;
		try {
			dateTimeStr = strManage
					.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
		} catch (WhatyUtilException e) {
			// TODO Auto-generated catch block
			
		}
		sql = "select id from aicc_stu_au where student_id='" + user_id
				+ "' and course_id='" + course_id + "' and system_id='"
				+ ausystem_id + "'";
		AiccLog.setDebug(sql);
		int count = db.countselect(sql);
		int i = 0;

		if (count < 1) {
			sql = "insert into aicc_stu_au(ID,SYSTEM_ID,STUDENT_ID,COURSE_ID,FIRST_ACCESSDATE,"
					+ "SESSION_ID)"
					+ " values(to_char(s_stu_au_id.nextval),'"
					+ ausystem_id
					+ "','"
					+ user_id
					+ "','"
					+ course_id
					+ "',"
					+ "to_date('"
					+ dateTimeStr
					+ "','yyyy-mm-dd hh24:mi:ss'),'" + session_id + "')";
			UserAddLog.setDebug("OracleAiccDataManage.insertStuAUInfo(String user_id, String course_id,String ausystem_id, String session_id) SQL=" + sql + " DATE=" + new Date());
		} else {
			sql = "update aicc_stu_au set attempt_num=attempt_num+1 "
					+ " where student_id='" + user_id + "' and course_id='"
					+ course_id + "' and system_id='" + ausystem_id + "'";
			UserUpdateLog.setDebug("OracleAiccDataManage.insertStuAUInfo(String user_id, String course_id,String ausystem_id, String session_id) SQL=" + sql + " DATE=" + new Date());
		}
		i = db.executeUpdate(sql);
		if (i < 1) {
			throw new AiccException("check the student and AU error!");
		}

	}

	private void updateStuCourseInfo(AiccData aiccData, String user_id,
			String course_id, String session_id) throws AiccException {
		dbpool db = new dbpool();
		String sql;
		MyResultSet rs = null;
		int totalTimeOfAus = 0;
		try {
			sql = "select sum(total_time) from aicc_stu_au "
					+ "where student_id='" + user_id + "' and course_id='"
					+ course_id + "'";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				totalTimeOfAus = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new AiccException("compute the totalTimeOfAus from db error!");
		} finally {
			db.close(rs);
		}
		int i = 0;
		sql = "update aicc_stu_course set credit='"
				+ aiccData.getCore().getCredit() + "'," + "lesson_location='"
				+ aiccData.getCore().getLessonLocation() + "',"
				+ "lesson_status='" + aiccData.getCore().getLessonStatus()
				+ "'," + "core_vendor='" + aiccData.getLaunchData().getValue()
				+ "'," + "raw_score='"
				+ aiccData.getCore().getScore().getRawScore() + "',"
				+ "max_score='" + aiccData.getCore().getScore().getMaxScore()
				+ "'," + "min_score='"
				+ aiccData.getCore().getScore().getMinScore() + "',"
				+ "total_time=" + totalTimeOfAus + "," + "lesson_mode='"
				+ aiccData.getCore().getLessonMode() + "'," + "core_lesson='"
				+ aiccData.getSuspendData().getValue() + "'," + "attempt_num='"
				+ aiccData.getStudentData().getAttempNumber() + "' "
				+ "where student_id='" + user_id + "' and course_id='"
				+ course_id + "'";
		i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleAiccDataManage.updateStuCourseInfo(AiccData aiccData, String user_id,String course_id, String session_id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i < 1) {
			throw new AiccException(
					"putParam putValue to DB error in updateStuCourseInfo!");
		}

	}

	private void updateStuAUInfo(AiccData aiccData, String user_id,
			String course_id, String ausystem_id, String session_id)
			throws AiccException {
		dbpool db = new dbpool();
		String sql;
		MyResultSet rs = null;
		int i = 0;
		List sqlList = new ArrayList();
		List objectives_id = new ArrayList();
		try {
			sql = "select objectives_id from aicc_stu_objectives where student_id='"
					+ user_id
					+ "' "
					+ "and course_id='"
					+ course_id
					+ "' and system_id='" + ausystem_id + "'";
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				objectives_id.add(rs.getString("objectives_id"));
			}
		} catch (SQLException e) {
			throw new AiccException(
					"get objectives_id error in updateStuAUInfo!");
		} finally {
			db.close(rs);
		}
		// ޸ĸAUϢ
		sql = "update aicc_stu_au set credit='"
				+ aiccData.getCore().getCredit()
				+ "',"
				+ "lesson_location='"
				+ aiccData.getCore().getLessonLocation()
				+ "',"
				+ "core_lesson='"
				+ aiccData.getSuspendData().getValue()
				+ "',"
				+ "core_vendor='"
				+ aiccData.getLaunchData().getValue()
				+ "',"
				+ "raw_score='"
				+ aiccData.getCore().getScore().getRawScore()
				+ "',"
				+ "max_score='"
				+ aiccData.getCore().getScore().getMaxScore()
				+ "',"
				+ "min_score='"
				+ aiccData.getCore().getScore().getMinScore()
				+ "',"
				+ "total_time=total_time+"
				+ AiccUtil
						.getSecondsOfTime(aiccData.getCore().getSessionTime())
				+ " " + "where student_id='" + user_id + "' and course_id='"
				+ course_id + "' " + "and system_id='" + ausystem_id + "'";
		UserUpdateLog.setDebug("OracleAiccDataManage.updateStuAUInfo(AiccData aiccData, String user_id,String course_id, String ausystem_id, String session_id) SQL=" + sql + " DATE=" + new Date());
		sqlList.add(sql);

		// ޸ObjectivesϢ
		Objectives objectives = aiccData.getObjectives();
		for (Iterator iter = objectives.getObjectives().keySet().iterator(); iter
				.hasNext();) {
			String key = (String) iter.next();
			String objs_id = ((Objective) objectives.getObjectives().get(key))
					.getObjectiveId();
			if (objectives_id.contains(objs_id)) {
				String sql1 = "update aicc_stu_objectives set raw_score='"
						+ ""
						+ ((Objective) objectives.getObjectives().get(key))
								.getObjectiveScore().getRawScore()
						+ "',"
						+ "status='"
						+ ((Objective) objectives.getObjectives().get(key))
								.getObjectiveStatus() + "' "
						+ " where student_id='" + user_id + "' and course_id='"
						+ course_id + "' " + "and system_id='" + ausystem_id
						+ "' and objectives_id='" + objs_id + "'";
				UserUpdateLog.setDebug("OracleAiccDataManage.updateStuAUInfo(AiccData aiccData, String user_id,String course_id, String ausystem_id, String session_id) SQL=" + sql1 + " DATE=" + new Date());
				sqlList.add(sql1);
			} else {
				String sql1 = "insert into  aicc_stu_objectives( ID,STUDENT_ID,SYSTEM_ID,COURSE_ID,"
						+ "OBJECTIVES_ID,RAW_SCORE,MAX_SCORE,MIN_SCORE,STATUS,SESSION_ID,SUB_ID) "
						+ "values(to_char(s_stu_au_id.nextval),'"
						+ user_id
						+ "',"
						+ "'"
						+ ausystem_id
						+ "','"
						+ course_id
						+ "','"
						+ objs_id
						+ "',"
						+ "'"
						+ ((Objective) objectives.getObjectives().get(key))
								.getObjectiveScore().getRawScore()
						+ "',"
						+ "'"
						+ ((Objective) objectives.getObjectives().get(key))
								.getObjectiveScore().getMaxScore()
						+ "',"
						+ "'"
						+ ((Objective) objectives.getObjectives().get(key))
								.getObjectiveScore().getMinScore()
						+ "',"
						+ "'"
						+ ((Objective) objectives.getObjectives().get(key))
								.getObjectiveStatus()
						+ "',"
						+ "'"
						+ session_id
						+ "','" + key + "')";
				UserAddLog.setDebug("OracleAiccDataManage.updateStuAUInfo(AiccData aiccData, String user_id,String course_id, String ausystem_id, String session_id) SQL=" + sql1 + " DATE=" + new Date());
				sqlList.add(sql1);
			}
		}
		i = db.executeUpdateBatch(sqlList);
		if (i < 1) {
			throw new AiccException(
					"update Student AU Info error! in updateStuAUInfo");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.standard.aicc.operation.AiccDataManage#updatePlatformDB(java.lang.String,
	 *      java.lang.String, java.lang.Float)
	 */
	public void updatePlatformDB(String user_id, String course_id,
			Float completedPercent) throws AiccException {
		dbpool db = new dbpool();
		String sql = "";
		if (completedPercent.floatValue() > 99.9) {
			sql = "update training_course_student set PERCENT=100,learn_status='"
					+ AiccStatus.COMPLETED
					+ "' where course_id in (select id from training_course where courseware_id='"
					+ course_id + "') and student_id='" + user_id + "'";
		} else {
			sql = "update training_course_student set PERCENT="
					+ completedPercent
					+ ", learn_status='"
					+ AiccStatus.INCOMPLETE
					+ "' where course_id in (select id from training_course where courseware_id='"
					+ course_id + "') and student_id='" + user_id + "'";
		}
		int flag = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleAiccDataManage.updateStuAUInfo(AiccData aiccData, String user_id,String course_id, String ausystem_id, String session_id) SQL=" + sql + " COUNT=" + flag + " DATE=" + new Date());
		if (flag < 1) {
			throw new AiccException("compute the percent of the courseware_id:"
					+ course_id + " and student_id=" + user_id);
		}

	}
}
