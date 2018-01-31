package com.whaty.platform.database.oracle.standard.interaction.homework;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.homework.HomeworkCheck;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleHomeworkCheck extends HomeworkCheck {
	public OracleHomeworkCheck() {
	}
	
	public OracleHomeworkCheck(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,homework_id,body,date,checkuser_id,checkuser_name,checkuser_type"
				+ " from (select id,homework_id,body,to_char(date,'yyyy-mm-dd') as date,checkuser_id,checkuser_name,checkuser_type from interaction_homework_check where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleHomework homework = new OracleHomework();
				homework.setId(rs.getString("homework_id"));
				this.setHomework(homework);
				this.setBody(rs.getString("body"));
				this.setDate(rs.getString("date"));
				this.setCheckuserId(rs.getString("checkuserId"));
				this.setCheckuserName(rs.getString("checkuserName"));
				this.setCheckuserType(rs.getString("checkuserType"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleHomeworkCheck(String homeworkId) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into interaction_homework_check (id,homeworkId,body,checkuserId,checkuserName,checkuserType) values(to_char(s_homework_checkId.nextval),'"
				+ this.getHomework().getId()
				+ "',?,'"
				+ this.getCheckuserId()
				+ "','"
				+ this.getCheckuserName()
				+ "','"
				+ this.getCheckuserType() + "')";
		int i = db.executeUpdate(sql, this.getBody());
		UserAddLog.setDebug("OracleHomeworkCheck.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i > 0) {
			sql = "update interaction_homework_info set check_status='1' where id='"
					+ this.getHomework().getId() + "'";
			i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleHomeworkCheck.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from interaction_homework_check where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleHomeworkCheck.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i > 0) {
			sql = "update interaction_homework_info set check_status='0' where id='"
					+ this.getHomework().getId() + "'";
			i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleHomeworkCheck.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update interaction_homework_check set homeworkId='"
				+ this.getHomework().getId() + "',date=sysdate,checkuserId='"
				+ this.getCheckuserId() + "',checkuserName='"
				+ this.getCheckuserName() + "',checkuserType='"
				+ this.getCheckuserType() + "',body=? where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleHomeworkCheck.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
