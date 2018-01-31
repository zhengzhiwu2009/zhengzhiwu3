package com.whaty.platform.database.oracle.standard.interaction.homework;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.homework.InHomework;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleInHomework extends InHomework {

	public OracleInHomework() {
	}

	public OracleInHomework(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		boolean checkStatus = false;
		String sql = "";
		sql = "select id,title,file,body,date,submiter_id,submiter_name,submiter_type,homework_id,check_status"
				+ " from (select id,title,file,body,to_date(date,'yyyy-mm-dd') as date,submiter_id,submiter_name,submiter_type,homework_id,check_status from interaction_inhomework_info where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setBody(rs.getString("body"));
				this.setDate(rs.getString("date"));
				this.setSubmituserId(rs.getString("submiter_id"));
				this.setSubmituserName(rs.getString("submiter_name"));
				this.setSubmituserType(rs.getString("submiter_type"));
				OracleHomework homework = new OracleHomework();
				homework.setId(rs.getString("homework_id"));
				this.setHomework(homework);
				if (rs.getString("check_status").equals("1"))
					checkStatus = true;
				this.setCheckStatus(checkStatus);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleInHomework(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String checkStatus = "0";
		if (this.isCheckStatus())
			checkStatus = "1";
		String sql = "";
		sql = "insert into interaction_inhomework_info (id,title,file,body,submiter_id,submiter_name,submiter_type,homework_id,check_status) values(to_char(s_inhomework_info_id.nextval),'"
				+ this.getTitle()
				+ "','"
				+ this.getFile()
				+ "',?,'"
				+ this.getSubmituserId()
				+ "','"
				+ this.getSubmituserName()
				+ "','"
				+ this.getSubmituserType()
				+ "','"
				+ this.getHomework().getId() + "','" + checkStatus + "')";
		int i = db.executeUpdate(sql, this.getBody());
		UserAddLog.setDebug("OracleInHomework.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from interaction_inhomework_info where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleInHomework.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String checkStatus = "0";
		if (this.isCheckStatus())
			checkStatus = "1";
		String sql = "";
		sql = "update interaction_inhomework_info set title='"
				+ this.getTitle() + "',file='" + this.getFile()
				+ "',date=sysdate,homework_id='" + this.getHomework().getId()
				+ "',check_status='" + checkStatus + "',submiter_id='"
				+ this.getSubmituserId() + "',submiter_name='"
				+ this.getSubmituserName() + "',submiter_type='"
				+ this.getSubmituserType() + "',body=? where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleInHomework.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
