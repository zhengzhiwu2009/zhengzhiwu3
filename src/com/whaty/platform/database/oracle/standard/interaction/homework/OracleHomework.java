package com.whaty.platform.database.oracle.standard.interaction.homework;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.interaction.homework.Homework;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleHomework extends Homework {

	public OracleHomework() {
	}
	 
	public OracleHomework(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		boolean checkStatus = false;
		String sql = "";
		sql = "select id,title,body,date,handin_date,course_id,check_status,submiter_id,submiter_name,submiter_type"
				+ " from (select id,title,body,to_date(date,'yyyy-mm-dd') as date,to_date(handin_date,'yyyy-mm-dd') as handin_date,course_id,check_status,submiter_id,submiter_name,submiter_type from interaction_homework_info where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setBody(rs.getString("body"));
				this.setDate(rs.getString("date"));
				this.setHandinDate(rs.getString("handin_date"));
				this.setSubmituserId(rs.getString("submiter_id"));
				this.setSubmituserName(rs.getString("submiter_name"));
				this.setSubmituserType(rs.getString("submiter_type"));
				if (rs.getString("check_status").equals("1"))
					checkStatus = true;
				this.setCheckStatus(checkStatus);
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				this.setCourse(course);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleHomework(String aid) error" + sql);
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
		sql = "insert into interaction_homework_info (id,title,body,handin_date,course_id,check_status,submiter_id,submiter_name,submiter_type) values(to_char(s_homework_info_id.nextval),'"
				+ this.getTitle()
				+ "',?,to_date('"
				+ this.getHandinDate()
				+ "','yyyy-mm-dd'),'"
				+ this.getCourse().getId()
				+ "','"
				+ checkStatus
				+ "','"
				+ this.getSubmituserId()
				+ "','"
				+ this.getSubmituserName()
				+ "','"
				+ this.getSubmituserType()
				+ "')";
		int i = db.executeUpdate(sql, this.getBody());
		UserAddLog.setDebug("OracleHomework.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from interaction_homework_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleHomework.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String checkStatus = "0";
		if (this.isCheckStatus())
			checkStatus = "1";
		String sql = "";
		sql = "update interaction_homework_info set title='" + this.getTitle()
				+ "',date=sysdate,handin_date=to_date('" + this.getHandinDate()
				+ "','yyyy-mm-dd'),course_id='" + this.getCourse().getId()
				+ "',check_status='" + checkStatus + "',submiter_id='"
				+ this.getSubmituserId() + "',submiter_name='"
				+ this.getSubmituserName() + "',submiter_type='"
				+ this.getSubmituserType() + "',body=? where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleHomework.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
