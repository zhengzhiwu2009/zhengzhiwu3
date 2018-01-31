package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.recruit.RecruitTestBatch;
import com.whaty.platform.entity.recruit.TimeDef;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleRecruitTestBatch extends RecruitTestBatch {
	/** Creates a new instance of OracleRecruitTestBatch */
	public OracleRecruitTestBatch() {
	}

	public OracleRecruitTestBatch(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		boolean active = false;
		String sql = "";
		sql = "select id,title,startdate,enddate,active,note,batch_id from (select id,title,to_char(startdate,'yyyy-mm-dd') as startdate,to_char(enddate,'yyyy-mm-dd') as enddate,active,note,batch_id from recruit_test_batch) where id = '"
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
				if (rs.getString("active").equals("1"))
					active = true;
				this.setActive(active);
				this.setNote(rs.getString("note"));
				OracleRecruitBatch recruitBatch = new OracleRecruitBatch();
				recruitBatch.setId(rs.getString("batch_id"));
				this.setBatch(recruitBatch);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleRecruitTestBatch(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int setActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_test_batch set active='1' where id ='"
				+ this.getId() + "'";
		EntityLog.setDebug("OracleRecruitTestBatch@Method:setActive()="+sql);
		int i = db.executeUpdate(sql);
		return i;
	}

	public int cancelActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_test_batch set active='0' where id ='"
				+ this.getId() + "'";
		EntityLog.setDebug("OracleRecruitTestBatch@Method:cancelActive()="+sql);
		int i = db.executeUpdate(sql);
		return i;
	}

	public void addSequences(List sequenceList) throws PlatformException {
		// TODO 自动生成方法存根

	}

	public void deleteSequences(List sequenceList) throws PlatformException {
		// TODO 自动生成方法存根

	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String active = "0";
		if (this.isActive())
			active = "1";
		String sql = "";
		sql = "insert into recruit_test_batch (id,title,startdate,enddate,active,note,batch_id) values(to_char(s_recruit_test_batch_id.nextval),'"
				+ this.getTitle()
				+ "',to_date('"
				+ this.getTime().getStartTime()
				+ "','yyyy-mm-dd'),to_date('"
				+ this.getTime().getEndTime()
				+ "','yyyy-mm-dd'),'"
				+ active
				+ "','"
				+ this.getNote()
				+ "','"
				+ this.getBatch().getId()
				+ "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitTestBatch.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String active = "0";
		if (this.isActive())
			active = "1";
		String sql = "";
		sql = "update recruit_test_batch set title='" + this.getTitle()
				+ "',startdate=to_date('" + this.getTime().getStartTime()
				+ "','yyyy-mm-dd'),enddate=to_date('"
				+ this.getTime().getEndTime() + "','yyyy-mm-dd'),active='"
				+ active + "', note='" + this.getNote() + "',batch_id='"
				+ this.getBatch().getId() + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitTestBatch.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		if (isHasSequences() > 0)
			throw new PlatformException(
					"The recruitTestBatch has recruitTestSequences");
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_test_batch where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitTestBatch.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isHasSequences() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select testbatch_id from recruit_test_sequence where batch_id ='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

}
