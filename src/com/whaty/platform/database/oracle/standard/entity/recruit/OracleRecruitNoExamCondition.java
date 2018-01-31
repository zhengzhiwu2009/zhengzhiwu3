package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.recruit.RecruitNoExamCondition;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleRecruitNoExamCondition extends RecruitNoExamCondition {
	/** Creates a new instance of OracleRecruitSort */
	public OracleRecruitNoExamCondition() {
	}
	
	public OracleRecruitNoExamCondition(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,note from recruit_noexam_condition where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setNote(rs.getString("note"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog
					.setError("OracleRecruitNoExamCondition(String aid) error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into recruit_noexam_condition (id,name,note) values(to_char(s_recruit_noexam_condition_id.nextval),'"
				+ this.getName() + "','" + this.getNote() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitNoExamCondition.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_noexam_condition set name='" + this.getName()
				+ "',note='" + this.getNote() + "' where id='" + this.getId()
				+ "'";
		EntityLog.setDebug("OracleRecruitNoExamCondition@Method:update()="+sql);
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitNoExamCondition.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_noexam_condition where id='" + this.getId()
				+ "'";
		EntityLog.setDebug("OracleRecruitNoExamCondition@Method:delete()="+sql);
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitNoExamCondition.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
