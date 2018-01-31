package com.whaty.platform.database.oracle.standard.interaction.homework;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.homework.InHomeworkCheck;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleInHomeworkCheck extends InHomeworkCheck {
	public OracleInHomeworkCheck() {
	}
	
	public OracleInHomeworkCheck(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,inhomework_id,body,score,date,checkuser_id,checkuser_name,checkuser_type"
				+ " from (select id,homework_id,body,score,to_char(date,'yyyy-mm-dd') as date,checkuser_id,checkuser_name,checkuser_type from interaction_inhomework_check where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleInHomework inHomework = new OracleInHomework();
				inHomework.setId(rs.getString("inhomework_id"));
				this.setInHomework(inHomework);
				this.setBody(rs.getString("body"));
				this.setScore(rs.getString("score"));
				this.setDate(rs.getString("date"));
				this.setCheckuserId(rs.getString("checkuser_id"));
				this.setCheckuserName(rs.getString("checkuser_name"));
				this.setCheckuserType(rs.getString("checkuser_type"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleInHomeworkCheck(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into interaction_inhomework_check (id,inhomework_id,body,score,checkuser_id,checkuser_name,checkuser_type) values(to_char(),'"
				+ this.getInHomework().getId()
				+ "',?,'"
				+ this.getScore()
				+ "','"
				+ this.getCheckuserId()
				+ "','"
				+ this.getCheckuserName()
				+ "','"
				+ this.getCheckuserType()
				+ "')";
		int i = db.executeUpdate(sql, this.getBody());
		UserAddLog.setDebug("OracleInHomeworkCheck.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i > 0) {
			sql = "update interaction_inhomework_info set check_status='1' where id='"
					+ this.getInHomework().getId() + "'";
			i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleInHomeworkCheck.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from interaction_Inhomework_check where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleInHomeworkCheck.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i > 0) {
			sql = "update interaction_inhomework_info set check_status='0' where id='"
					+ this.getInHomework().getId() + "'";		
			i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleInHomeworkCheck.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update interaction_Inhomework_check set inhomeworkId='"
				+ this.getInHomework().getId() + "',score='" + this.getScore()
				+ "',date=sysdate,checkuserId='" + this.getCheckuserId()
				+ "',checkuserName='" + this.getCheckuserName()
				+ "',checkusertype='" + this.getCheckuserType()
				+ "',body=? where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleInHomeworkCheck.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
