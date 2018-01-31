package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.recruit.RecruitTestSequence;
import com.whaty.platform.entity.recruit.TimeDef;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleRecruitTestSequence extends RecruitTestSequence {
	/** Creates a new instance of OracleRecruitTestSequence */
	public OracleRecruitTestSequence() {
	}
	
	public OracleRecruitTestSequence(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,title,startdate,enddate,note,testbatch_id from (select id,title,to_char(startdate,'yyyy-mm-dd') as startdate,to_char(enddate,'yyyy-mm-dd') as enddate,note,testbatch_id from recruit_test_sequence) where id = '"
				+ aid + "')";
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
				OracleRecruitTestBatch recruitTestBatch = new OracleRecruitTestBatch();
				recruitTestBatch.setId(rs.getString("testbatch_id"));
				this.setTestBatch(recruitTestBatch);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleRecruitTestSequence(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public void addCourses(List courseList) throws PlatformException {
		// TODO 自动生成方法存根

	}

	public void deleteCourses(List courseList) throws PlatformException {
		// TODO 自动生成方法存根

	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into recruit_test_sequence (id,title,startdate,enddate,note,testbatch_id) values(to_char(s_recruit_test_sequence_id.nextval),'"
				+ this.getTitle()
				+ "',to_date('"
				+ this.getTime().getStartTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getTime().getEndTime()
				+ "','yyyy-mm-dd'),'"
				+ this.getNote() + "','" + this.getTestBatch().getId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitTestSequence.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_test_sequence set title='" + this.getTitle()
				+ "',startdate=to_date('" + this.getTime().getStartTime()
				+ "','yyyy-mm-dd'),enddate=to_date('"
				+ this.getTime().getEndTime() + "','yyyy-mm-dd'), note='"
				+ this.getNote() + "',batch_id='" + this.getTestBatch().getId()
				+ "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitTestSequence.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		if (isHasCourses() > 0)
			throw new PlatformException(
					"The recruitTestSequence has recruitTestCourses");
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_test_sequence where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitTestSequence.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isHasCourses() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select testsequence_id from recruit_test_course where testsequence_id ='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}
}
