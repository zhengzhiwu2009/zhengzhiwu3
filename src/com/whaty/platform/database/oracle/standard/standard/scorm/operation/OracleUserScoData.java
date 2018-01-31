/**
 * 
 */
package com.whaty.platform.database.oracle.standard.standard.scorm.operation;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.datamodels.cmi.CMIScore;
import com.whaty.platform.standard.scorm.operation.UserScoData;
import com.whaty.platform.standard.scorm.util.ScormLog;

/**
 * @author Administrator
 *
 */
public class OracleUserScoData extends UserScoData {

	public OracleUserScoData() {
		super();
	}
	public OracleUserScoData(String Id) throws ScormException{
		super();
	}
	public OracleUserScoData(String userId,String courseId,String systemId) throws ScormException{
		super();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = "select ID,SYSTEM_ID,COURSE_ID,STUDENT_ID,FIRST_ACCESSDATE," +
				"LAST_ACCESSDATE,SESSION_ID,RAW_SCORE,MAX_SCORE,MIN_SCORE,STATUS," +
				"TOTAL_TIME,CREDIT,ATTEMPT_NUM,LESSON_LOCATION,CORE_LESSON," +
				"CORE_VENDOR,LESSON_MODE,HIGH_SCORE,HIGH_STATUS,BROWSE_SCORE," +
				"SESSION_DATA,COMPLETE_PERCENT,EXIT,ENTRY,comments from SCORM_STU_SCO where  " +
				"student_id='" + userId + "' and " +" course_id='"+courseId+"' " +
				"and system_id='"+systemId+"'";
			ScormLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setCourseId(rs.getString("course_id"));
				this.setId(rs.getString("id"));
				this.setSystemId(rs.getString("system_id"));
				this.getCore().setStudentId(rs.getString("student_id"));
				this.getCore().setCredit(rs.getString("credit"));
				this.getCore().setLessonStatus(rs.getString("status"));
				this.getCore().setLessonLocation(
						rs.getString("lesson_location"));
				this.getCore().setTotalTime(rs.getString("total_time"));
				CMIScore score = new CMIScore();
				score.setRaw(rs.getString("raw_score"));
				score.setMax(rs.getString("max_score"));
				score.setMin(rs.getString("min_score"));
				this.getCore().setScore(score);
				this.getLaunchData().setLaunchData(
						rs.getString("core_vendor"));
				this.getSuspendData().setSuspendData(rs.getString("core_lesson"));
				this.getCore().setExit(rs.getString("exit"));
				this.getCore().setEntry(rs.getString("entry"));
				
				this.getComments().setComments(rs.getString("comments"));
			}
		} catch (java.sql.SQLException e) {
			throw new ScormException("init ScormCourse error!");
		} finally {
			db.close(rs);
			db = null;
		}
	}
	
	public int add()
	{
		dbpool db=new dbpool();
		String sql="insert into SCORM_STU_SCO(ID,SYSTEM_ID,COURSE_ID,STUDENT_ID," +
				"FIRST_ACCESSDATE,LAST_ACCESSDATE,SESSION_ID,RAW_SCORE,MAX_SCORE," +
				"MIN_SCORE,STATUS,TOTAL_TIME,CREDIT,ATTEMPT_NUM,LESSON_LOCATION," +
				"CORE_LESSON,CORE_VENDOR,LESSON_MODE,HIGH_SCORE,HIGH_STATUS,BROWSE_SCORE," +
				"SESSION_DATA,COMPLETE_PERCENT,EXIT,ENTRY from SCORM_STU_SCO) values(" +
				"to_char(s_scorm_id.nextval),'"+this.getSystemId()+"'," +
				"'"+this.getCourseId()+"','"+this.getCore().getStudentId()+"'," +
				"sysdate,sysdate,'','"+this.getCore().getScore().getRaw().getValue()+"'," +
				"'"+this.getCore().getScore().getMax().getValue()+"'," +
				"'"+this.getCore().getScore().getMin().getValue()+"'," +
				"'"+this.getCore().getLessonStatus().getValue()+"'," +
				"'"+this.getCore().getTotalTime().getValue()+"'," +
				"'"+this.getCore().getCredit().getValue()+"'," +
				"0,'"+this.getCore().getLessonLocation()+"'," +
				"'"+this.getSuspendData().getSuspendData().getValue()+"'," +
				"'"+this.getLaunchData().getLaunchData().getValue()+"'," +
				"'"+this.getCore().getLessonMode().getValue()+"'," +
				"','','','',sysdate,'0','"+this.getCore().getExit().getValue()+"'," +
				"'"+this.getCore().getEntry()+"')";
		return db.executeUpdate(sql);
	}
	public int update()
	{
		dbpool db=new dbpool();
		String sql="update SCORM_STU_SCO set LAST_ACCESSDATE=sysdate," +
			"RAW_SCORE='"+this.getCore().getScore().getRaw().getValue()+"'," +
			"MAX_SCORE='"+this.getCore().getScore().getMax().getValue()+"'," +
			"MIN_SCORE='"+this.getCore().getScore().getMax().getValue()+"'," +
			"STATUS='"+this.getCore().getLessonStatus().getValue()+"'," +
			"TOTAL_TIME=totaltime+"+this.getCore().getSessionTime().getValue()+"," +
			"CREDIT='"+this.getCore().getCredit().getValue()+"'," +
			"LESSON_LOCATION='"+this.getCore().getLessonLocation()+"," +
			"CORE_LESSON='"+this.getSuspendData().getSuspendData().getValue()+"," +
			"CORE_VENDOR='"+this.getLaunchData().getLaunchData().getValue()+"," +
			"LESSON_MODE='"+this.getCore().getLessonMode()+"," +
			"SESSION_DATA=sysdate," +
			"EXIT='"+this.getCore().getExit().getValue()+"," +
			"ENTRY='"+this.getCore().getEntry().getValue()+" " +
			"where id='"+this.getId()+"'";
		return db.executeUpdate(sql);
	}
	public int delete()
	{
		dbpool db=new dbpool();
		String sql="delete from  SCORM_STU_SCO where id='"+this.getId()+"'";
		return db.executeUpdate(sql);
	}
}
