package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.recruit.RecruitTestCourse;
import com.whaty.platform.entity.recruit.TimeDef;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleRecruitTestCourse extends RecruitTestCourse {
	/** Creates a new instance of OracleRecruitTestCourse */
	public OracleRecruitTestCourse() {
	}

	public OracleRecruitTestCourse(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,course_name,title,startdate,enddate,note,testsequence_id,course_id,batch_title from (select a.id,b.name as course_name,a.title,to_char(a.startdate,'yyyy-mm-dd hh24:mi') as startdate,to_char(a.enddate,'yyyy-mm-dd hh24:mi') as enddate,a.note,testsequence_id,course_id,c.title as batch_title from recruit_test_course a,recruit_course_info b,recruit_batch_info c where a.course_id=b.id and a.testsequence_id=c.id) where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				TimeDef time = new TimeDef();
				time.setStartTime(rs.getString("startdate"));
				time.setEndTime(rs.getString("enddate"));
				this.setTime(time);
				this.setNote(rs.getString("note"));
				OracleRecruitTestSequence testSequence = new OracleRecruitTestSequence();
				OracleRecruitTestBatch testBatch = new OracleRecruitTestBatch();
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("testsequence_id"));
				batch.setTitle(rs.getString("batch_title"));
				testBatch.setBatch(batch);
				testSequence.setTestBatch(testBatch);
				this.setTestSequence(testSequence);
				OracleRecruitCourse course = new OracleRecruitCourse();
				course.setId(rs.getString("course_id"));
				this.setCourse(course);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleRecruitTestCourse(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into recruit_test_course (id,title,startdate,enddate,note,testsequence_id,course_id) "
				+ "values(to_char(s_recruit_test_course_id.nextval),'"
				+ this.getTitle()
				+ "',to_date('"
				+ this.getTime().getStartTime()
				+ "','yyyy-mm-dd hh24:mi:ss'),to_date('"
				+ this.getTime().getEndTime()
				+ "','yyyy-mm-dd hh24:mi:ss'),'"
				+ this.getNote()
				+ "','"
				+ this.getTestSequence().getTestBatch().getBatch().getId()
				+ "','" + this.getCourse().getId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitTestCourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_test_course set title='" + this.getTitle()
				+ "',startdate=to_date('" + this.getTime().getStartTime()
				+ "','yyyy-mm-dd hh24:mi:ss'),enddate=to_date('"
				+ this.getTime().getEndTime()
				+ "','yyyy-mm-dd hh24:mi:ss'), note='" + this.getNote()
				+ "',testsequence_id='"
				+ this.getTestSequence().getTestBatch().getBatch().getId()
				+ "',course_id='" + this.getCourse().getId() + "' where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitTestCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		if (isHasTestDesks() > 0)
			throw new PlatformException(
					"The RecruitTestCourse has recruitTestDesks");
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_test_course where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitTestCourse.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isHasTestDesks() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select testcourse_id from recruit_test_desk where testcourse_id ='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

}
