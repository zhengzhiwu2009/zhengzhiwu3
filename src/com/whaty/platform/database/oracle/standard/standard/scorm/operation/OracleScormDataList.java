/**
 * 
 */
package com.whaty.platform.database.oracle.standard.standard.scorm.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.datamodels.cmi.CMIScore;
import com.whaty.platform.standard.scorm.operation.ScormCourse;
import com.whaty.platform.standard.scorm.operation.ScormItem;
import com.whaty.platform.standard.scorm.operation.UserCourseData;
import com.whaty.platform.standard.scorm.operation.UserScoData;
import com.whaty.platform.standard.scorm.util.ScormLog;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.util.log.Log;

/**
 * @author Administrator
 * 
 */
public class OracleScormDataList {

	private String SQLSCORMCOURSE = "select ID,TITLE,CONTROL_TYPE,VERSION,DESCRIPTION "
			+ "from scorm_course_info";

	private String SQLSCORMITEMS = "select COURSE_ID,ID,TYPE,TITLE,LAUNCH,PARAMETERSTRING,"
			+ "DATAFROMLMS,PREREQUISITES,MASTERYSCORE,MAXTIMEALLOWED,TIMELIMITACTION,"
			+ "SEQUENCE,THELEVEL from scorm_course_item";

	private String SQLUSERCOURSE = "select ID,STUDENT_ID,COURSE_ID,CREDIT,LESSON_LOCATION,"
			+ "LESSON_STATUS,RAW_SCORE,MAX_SCORE,MIN_SCORE,TOTAL_TIME,LESSON_MODE,"
			+ "CORE_LESSON,CORE_VENDOR,HIGH_SCORE,HIGH_STATUS,FIRST_DATE,to_char(LAST_DATE,'yyyy-MM-dd HH24:mi:ss') as LAST_DATE,"
			+ "BROWSE_SCORE,SESSION_DATE,COMPLETE_PERCENT,ATTEMPT_NUM from scorm_stu_course";

	private String SQLUSERSCO = "select ID,SYSTEM_ID,COURSE_ID,STUDENT_ID,FIRST_ACCESSDATE," +
					"LAST_ACCESSDATE,SESSION_ID,RAW_SCORE,MAX_SCORE,MIN_SCORE,STATUS," +
					"TOTAL_TIME,CREDIT,ATTEMPT_NUM,LESSON_LOCATION,CORE_LESSON," +
					"CORE_VENDOR,LESSON_MODE,HIGH_SCORE,HIGH_STATUS,BROWSE_SCORE," +
					"SESSION_DATA,COMPLETE_PERCENT,EXIT,ENTRY,launch ,title from" +
					"(select a.ID,a.SYSTEM_ID,a.COURSE_ID,a.STUDENT_ID,a.FIRST_ACCESSDATE," +
					"a.LAST_ACCESSDATE,a.SESSION_ID,a.RAW_SCORE,a.MAX_SCORE,a.MIN_SCORE,a.STATUS," +
					"a.TOTAL_TIME,a.CREDIT,a.ATTEMPT_NUM,a.LESSON_LOCATION,a.CORE_LESSON," +
					"a.CORE_VENDOR,a.LESSON_MODE,a.HIGH_SCORE,a.HIGH_STATUS,a.BROWSE_SCORE," +
					"a.SESSION_DATA,a.COMPLETE_PERCENT,a.EXIT,a.ENTRY,b.launch,b.title from " +
					"(select ID,SYSTEM_ID,COURSE_ID,STUDENT_ID,FIRST_ACCESSDATE," +
					"LAST_ACCESSDATE,SESSION_ID,RAW_SCORE,MAX_SCORE,MIN_SCORE,STATUS," +
					"TOTAL_TIME,CREDIT,ATTEMPT_NUM,LESSON_LOCATION,CORE_LESSON," +
					"CORE_VENDOR,LESSON_MODE,HIGH_SCORE,HIGH_STATUS,BROWSE_SCORE," +
					"SESSION_DATA,COMPLETE_PERCENT,EXIT,ENTRY from SCORM_STU_SCO) a," +
					"(select COURSE_ID,ID,TYPE,TITLE,LAUNCH,PARAMETERSTRING," + 
					"DATAFROMLMS,PREREQUISITES,MASTERYSCORE,MAXTIMEALLOWED,TIMELIMITACTION," +
					"SEQUENCE,THELEVEL from scorm_course_item) b where a.system_id=b.id " +
					"and a.course_id=b.course_id) ";
	private String SQLALLUSERSCO = "select a.ID, a.COURSE_ID,a.STUDENT_ID,a.FIRST_ACCESSDATE," + 
					" a.LAST_ACCESSDATE,a.SESSION_ID,a.RAW_SCORE,a.MAX_SCORE,a.MIN_SCORE,a.STATUS," + 
					" a.TOTAL_TIME,a.CREDIT,a.ATTEMPT_NUM,a.LESSON_LOCATION,a.CORE_LESSON,a.CORE_VENDOR," + 
					" a.LESSON_MODE,a.HIGH_SCORE,a.HIGH_STATUS,a.BROWSE_SCORE,a.SESSION_DATA,a.COMPLETE_PERCENT," + 
					" a.EXIT,a.ENTRY,b.launch,b.id as SYSTEM_ID,b.course_id ,b.sequence,b.title " + 
					" from  SCORM_STU_SCO a,scorm_course_item b " + 
					" where a.system_id(+)=b.id and a.course_id(+)=b.course_id and " + 
					"a.student_id(+)= ? and b.course_id= ? and b.type='sco' order by b.sequence ";
	public List searchScormCourse(Page page, List searchproperty,
			List orderproperty) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		List courseList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = SQLSCORMCOURSE + condition;
			ScormLog.setDebug(sql);
			if (page != null) {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				ScormCourse course = new OracleScormCourse();
				course.setCourseId(rs.getString("id"));
				course.setCourseTitle(rs.getString("title"));
				course.setControlType(rs.getString("control_type"));
				course.setVersion(rs.getString("version"));
				course.setDescription(rs.getString("description"));
				courseList.add(course);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("ScormataList.searchScormCourse() error" + sql);
		} finally {
			db.close(rs);
			db = null;
			return courseList;
		}
	}

	public List searchScormItems(Page page, List searchproperty,
			List orderproperty) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		List itemList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = SQLSCORMITEMS + condition;
			ScormLog.setDebug(sql);
			if (page != null) {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				ScormItem item = new ScormItem();
				item.setId(rs.getString("ID"));
				item.setTitle(rs.getString("TITLE"));
				item.setType(rs.getString("TYPE"));
				item.setCourseId(rs.getString("COURSE_ID"));
				item.setLaunch(rs.getString("LAUNCH"));
				item.setDataFromLms(rs.getString("DATAFROMLMS"));
				item.setMasteryScore(rs.getString("MASTERYSCORE"));
				item.setMaxTimeAllowed(rs.getString("MAXTIMEALLOWED"));
				item.setParameterString(rs.getString("PARAMETERSTRING"));
				item.setPreRequisites(rs.getString("PREREQUISITES"));
				item.setSequence(rs.getInt("SEQUENCE"));
				item.setTimeLimitation(rs.getString("TIMELIMITACTION"));
				item.setTheLevel(rs.getInt("THELEVEL"));
				itemList.add(item);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("ScormataList.searchScormItems() error" + sql);
		} finally {
			db.close(rs);
			db = null;
			return itemList;
		}

	}
	
	/**
	 * 用于多表现形式
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @param scormType
	 * @return
	 */
	public List searchScormItems(Page page, List searchproperty,
			List orderproperty,String scormType) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		List itemList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		sb.append("select * from (select a.COURSE_ID,                              ");
		sb.append("       a.ID,                                     ");
		sb.append("       a.TYPE,                                   ");
		sb.append("       a.TITLE,                                  ");
		sb.append("       b.LAUNCH,                                 ");
		sb.append("       a.PARAMETERSTRING, a.DATAFROMLMS,            ");
		sb.append("       a.PREREQUISITES,                          ");
		sb.append("       a.MASTERYSCORE,                           ");
		sb.append("       a.MAXTIMEALLOWED,                         ");
		sb.append("       a.TIMELIMITACTION, a.SEQUENCE,               ");
		sb.append("       a.THELEVEL                                ");
		sb.append("  from scorm_course_item a, scorm_sco_launch b   ");
		sb.append(" where a.course_id = b.course_id                 ");
		sb.append("   and a.id = b.sco_id                           ");
		sb.append("   and b.scorm_type = '"+scormType+"')			");
		String sql = "";
		try {
			sql = sb.toString() + condition;
			ScormLog.setDebug(sql);
			if (page != null) {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				ScormItem item = new ScormItem();
				item.setId(rs.getString("ID"));
				item.setTitle(rs.getString("TITLE"));
				item.setType(rs.getString("TYPE"));
				item.setCourseId(rs.getString("COURSE_ID"));
				item.setLaunch(rs.getString("LAUNCH"));
				item.setDataFromLms(rs.getString("DATAFROMLMS"));
				item.setMasteryScore(rs.getString("MASTERYSCORE"));
				item.setMaxTimeAllowed(rs.getString("MAXTIMEALLOWED"));
				item.setParameterString(rs.getString("PARAMETERSTRING"));
				item.setPreRequisites(rs.getString("PREREQUISITES"));
				item.setSequence(rs.getInt("SEQUENCE"));
				item.setTimeLimitation(rs.getString("TIMELIMITACTION"));
				item.setTheLevel(rs.getInt("THELEVEL"));
				itemList.add(item);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("ScormataList.searchScormItems() error" + sql);
		} finally {
			db.close(rs);
			db = null;
			return itemList;
		}

	}

	public List searchUserCourses(Page page, List searchproperty,
			List orderproperty) throws ScormException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List usercourseList = new ArrayList();
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		try {

			sql = SQLUSERCOURSE + condition;
			ScormLog.setDebug(sql);
			rs = db.executeQuery(sql); 
			while (rs != null && rs.next()) {
				UserCourseData userCourseData = new UserCourseData();
				userCourseData.setId(rs.getString("id"));
				userCourseData.setCourseId(rs.getString("course_id"));
				userCourseData.setLessonLocation(rs
						.getString("lesson_location"));
				userCourseData.setLessonStatus(rs.getString("lesson_status"));
				userCourseData.setTotalTime(rs.getString("total_time"));
				userCourseData.setFirstDate(rs.getString("first_date"));
				userCourseData.setLastDate(rs.getString("last_date"));
				userCourseData.setCompletedPercent(rs.getString("COMPLETE_PERCENT"));
				userCourseData.setAttemptNum(rs.getString("attempt_num"));
				usercourseList.add(userCourseData);
			}
		} catch (java.sql.SQLException e) {
			throw new ScormException("Get student's courses error!");
		} finally {
			db.close(rs);
			db = null;
			return usercourseList;
		}

	}
	/*
	 *  ��ѯ�û��ڸÿγ̵�����scos����(����¼�ĺ�û�б���¼��
	 */
	public List searchAllUserScos(String userId,String courseId)
	{
		dbpool db = new dbpool();
		String sql = "";
		PreparedStatement state=null;
		List userScoList = new ArrayList();
		MyResultSet rs=new MyResultSet();
		Connection conn=null;
		 
		try {
			conn=db.getConn();
			state= conn.prepareStatement(SQLALLUSERSCO); 
			state.setString(1, userId);
			state.setString(2, courseId);
			rs.setMyResuleSet(state.executeQuery());
			 
			while (rs != null && rs.next()) {
				UserScoData userScoData = new OracleUserScoData();
				userScoData.setId(rs.getString("id"));
				userScoData.setCourseId(rs.getString("course_id"));
				userScoData.setSystemId(rs.getString("system_id"));
				userScoData.getCore().setStudentId(rs.getString("student_id"));
				userScoData.getCore().setCredit(rs.getString("credit"));
				if(rs.getString("status") == null)
					userScoData.getCore().setLessonStatus("not attempted");
				else
					userScoData.getCore().setLessonStatus(rs.getString("status"));
				userScoData.getCore().setLessonLocation(
						rs.getString("lesson_location"));
				if(rs.getString("total_time") == null)
					userScoData.getCore().setTotalTime("00:00:00");
				else
					userScoData.getCore().setTotalTime(rs.getString("total_time"));
				CMIScore score = new CMIScore();
				 
				if(rs.getString("raw_score") == null || rs.getString("raw_score").equals("null"))
					score.setRaw("0");
				else
					score.setRaw(rs.getString("raw_score"));
				if(rs.getString("max_score") == null || rs.getString("max_score").equals("null"))
					score.setMax("0");
				else
					score.setMax(rs.getString("max_score"));
				if(rs.getString("min_score") == null || rs.getString("min_score").equals("null"))
					score.setMin("0");
				else
					score.setMin(rs.getString("min_score"));
				userScoData.getCore().setScore(score);
				userScoData.getLaunchData().setLaunchData(
						rs.getString("core_vendor"));
				userScoData.getSuspendData().setSuspendData(
						rs.getString("core_lesson"));
				userScoData.setLaunch(rs.getString("launch"));
				userScoData.setTitle(rs.getString("title"));
				userScoList.add(userScoData);
			}
			
		} catch (java.sql.SQLException e) {
			throw new ScormException("Get student's Scos error!");
		} finally {
			try {if(state!=null){
				conn.close();
				state.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.close(rs);
			db = null;
			return userScoList;
		}
	}

	/**
	 * 多表现形式下获得SCO列表
	 * @param userId
	 * @param courseId
	 * @param scormTpye
	 * @return
	 */
	public List searchAllUserScos(String userId,String courseId,String scormTpye)
	{
		dbpool db = new dbpool();
		String sql = "";
		PreparedStatement state=null;
		List userScoList = new ArrayList();
		MyResultSet rs=new MyResultSet();
		Connection conn=null;
		 
		try {
			conn=db.getConn();
			StringBuilder sb = new StringBuilder();
			sb.append("select a.ID,                                                       ");
			sb.append("       a.COURSE_ID,                                                ");
			sb.append("       a.STUDENT_ID,                                               ");
			sb.append("       a.FIRST_ACCESSDATE,                                         ");
			sb.append("       a.LAST_ACCESSDATE,                                          ");
			sb.append("       a.SESSION_ID,                                               ");
			sb.append("       a.RAW_SCORE,                                                ");
			sb.append("       a.MAX_SCORE,                                                ");
			sb.append("       a.MIN_SCORE,                                                ");
			sb.append("       a.STATUS,                                                   ");
			sb.append("       a.TOTAL_TIME,                                               ");
			sb.append("       a.CREDIT,                                                   ");
			sb.append("       a.ATTEMPT_NUM,                                              ");
			sb.append("       a.LESSON_LOCATION,                                          ");
			sb.append("       a.CORE_LESSON,                                              ");
			sb.append("       a.CORE_VENDOR,                                              ");
			sb.append("       a.LESSON_MODE,                                              ");
			sb.append("       a.HIGH_SCORE,                                               ");
			sb.append("       a.HIGH_STATUS,                                              ");
			sb.append("       a.BROWSE_SCORE,                                             ");
			sb.append("       a.SESSION_DATA,                                             ");
			sb.append("       a.COMPLETE_PERCENT,                                         ");
			sb.append("       a.EXIT,                                                     ");
			sb.append("       a.ENTRY,                                                    ");
			sb.append("       c.launch,                                                   ");
			sb.append("       b.id as SYSTEM_ID,                                          ");
			sb.append("       b.course_id,                                                ");
			sb.append("       b.sequence,                                                 ");
			sb.append("       b.title                                                     ");
			sb.append("  from SCORM_STU_SCO a, scorm_course_item b,scorm_sco_launch c     ");
			sb.append(" where a.system_id(+) = b.id                                       ");
			sb.append("   and a.course_id(+) = b.course_id                                ");
			sb.append("   and b.course_id = c.course_id                                   ");
			sb.append("   and b.id = c.sco_id                                             ");
			sb.append("   and c.scorm_type = ?                                            ");
			sb.append("   and a.student_id(+) = ?                                         ");
			sb.append("   and b.course_id = ?                                             ");
			sb.append("   and b.type = 'sco'                                              ");
			sb.append(" order by b.sequence												  ");
			state= conn.prepareStatement(sb.toString()); 
			state.setString(1, scormTpye);
			state.setString(2, userId);
			state.setString(3, courseId);
			//rs.setMyResuleSet(state.executeQuery());
			
			/**
			 * 修改20101031
			 */
			String sql1 = 
				"select a.ID,\n" +
				"       a.COURSE_ID,\n" + 
				"       a.STUDENT_ID,\n" + 
				"       a.FIRST_ACCESSDATE,\n" + 
				"       a.LAST_ACCESSDATE,\n" + 
				"       a.SESSION_ID,\n" + 
				"       a.RAW_SCORE,\n" + 
				"       a.MAX_SCORE,\n" + 
				"       a.MIN_SCORE,\n" + 
				"       a.STATUS,\n" + 
				"       a.TOTAL_TIME,\n" + 
				"       a.CREDIT,\n" + 
				"       a.ATTEMPT_NUM,\n" + 
				"       a.LESSON_LOCATION,\n" + 
				"       a.CORE_LESSON,\n" + 
				"       a.CORE_VENDOR,\n" + 
				"       a.LESSON_MODE,\n" + 
				"       a.HIGH_SCORE,\n" + 
				"       a.HIGH_STATUS,\n" + 
				"       a.BROWSE_SCORE,\n" + 
				"       a.SESSION_DATA,\n" + 
				"       a.COMPLETE_PERCENT,\n" + 
				"       a.EXIT,\n" + 
				"       a.ENTRY,\n" + 
				"       c.launch,\n" + 
				"       b.id as SYSTEM_ID,\n" + 
				"       b.course_id,\n" + 
				"       b.sequence,\n" + 
				"       b.title\n" + 
				"  from SCORM_STU_SCO a, scorm_course_item b, scorm_sco_launch c\n" + 
				" where a.system_id(+) = b.id\n" + 
				"   and a.course_id(+) = b.course_id\n" + 
				"   and b.course_id = c.course_id\n" + 
				"   and b.id = c.sco_id\n" + 
				"   and c.scorm_type = '"+scormTpye+"'\n" + 
				"   and a.student_id(+) = '"+userId+"'\n" + 
				"   and b.course_id = '"+courseId+"'\n" + 
				"   and b.type = 'sco'\n" + 
				" order by b.sequence";
				rs = db.executeQuery(sql1);
			 
			while (rs != null && rs.next()) {
				UserScoData userScoData = new OracleUserScoData();
				userScoData.setId(rs.getString("id"));
				userScoData.setCourseId(rs.getString("course_id"));
				userScoData.setSystemId(rs.getString("system_id"));
				userScoData.getCore().setStudentId(rs.getString("student_id"));
				userScoData.getCore().setCredit(rs.getString("credit"));
				if(rs.getString("status") == null)
					userScoData.getCore().setLessonStatus("not attempted");
				else
					userScoData.getCore().setLessonStatus(rs.getString("status"));
				userScoData.getCore().setLessonLocation(
						rs.getString("lesson_location"));
				if(rs.getString("total_time") == null)
					userScoData.getCore().setTotalTime("00:00:00");
				else
					userScoData.getCore().setTotalTime(rs.getString("total_time"));
				CMIScore score = new CMIScore();
				 
				if(rs.getString("raw_score") == null || rs.getString("raw_score").equals("null"))
					score.setRaw("0");
				else
					score.setRaw(rs.getString("raw_score"));
				if(rs.getString("max_score") == null || rs.getString("max_score").equals("null"))
					score.setMax("0");
				else
					score.setMax(rs.getString("max_score"));
				if(rs.getString("min_score") == null || rs.getString("min_score").equals("null"))
					score.setMin("0");
				else
					score.setMin(rs.getString("min_score"));
				userScoData.getCore().setScore(score);
				userScoData.getLaunchData().setLaunchData(
						rs.getString("core_vendor"));
				userScoData.getSuspendData().setSuspendData(
						rs.getString("core_lesson"));
				userScoData.setLaunch(rs.getString("launch"));
				userScoData.setTitle(rs.getString("title"));
				userScoList.add(userScoData);
			}
			
		} catch (java.sql.SQLException e) {
			throw new ScormException("Get student's Scos error!");
		} finally {
			try {if(state!=null){
				conn.close();
				state.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.close(rs);
			db = null;
			return userScoList;
		}
	}

	public List searchUserScos(Page page, List searchproperty,
			List orderproperty) throws ScormException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List userScoList = new ArrayList();
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		try {

			sql = SQLUSERSCO + condition;
			ScormLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				UserScoData userScoData = new OracleUserScoData();
				userScoData.setId(rs.getString("id"));
				userScoData.setCourseId(rs.getString("course_id"));
				userScoData.setSystemId(rs.getString("system_id"));
				userScoData.getCore().setStudentId(rs.getString("student_id"));
				userScoData.getCore().setCredit(rs.getString("credit"));
				userScoData.getCore().setLessonStatus(rs.getString("status"));
				userScoData.getCore().setLessonLocation(
						rs.getString("lesson_location"));
				userScoData.getCore().setTotalTime(rs.getString("total_time"));
				CMIScore score = new CMIScore();
				score.setRaw(rs.getString("raw_score"));
				score.setMax(rs.getString("max_score"));
				score.setMin(rs.getString("min_score"));
				userScoData.getCore().setScore(score);
				userScoData.getLaunchData().setLaunchData(
						rs.getString("core_vendor"));
				userScoData.getSuspendData().setSuspendData(
						rs.getString("core_lesson"));
				userScoData.setLaunch(rs.getString("launch"));
				userScoData.setTitle(rs.getString("title"));
				userScoList.add(userScoData);
			}
		} catch (java.sql.SQLException e) {
			throw new ScormException("Get student's Scos error!");
		} finally {
			db.close(rs);
			db = null;
			return userScoList;
		}
	}
}
