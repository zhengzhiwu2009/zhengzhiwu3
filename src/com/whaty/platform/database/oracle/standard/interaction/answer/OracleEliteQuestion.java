package com.whaty.platform.database.oracle.standard.interaction.answer;

import java.util.ArrayList;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.answer.EliteQuestion;
import com.whaty.platform.interaction.answer.QuestionEliteDir;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleEliteQuestion extends EliteQuestion {
	public OracleEliteQuestion() {
	}
	
	public OracleEliteQuestion(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,title,body,key,date,course_id,dir_id,submituser_id,submituser_name,submituser_type"
				+ " from (select id,title,body,key,to_char(date,'yyyy-mm-dd') as date,course_id,dir_id,submituser_id,submituser_name,submituser_type from interaction_elitequestion_info where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setBody(rs.getString("body"));
				if (rs.getString("key") != null
						&& rs.getString("key").length() > 0)
					this.setKey(rs.getString("key").split(","));
				else
					this.setKey(null);
				this.setDate(rs.getString("date"));
				this.setCourseId(rs.getString("course_id"));
				OracleQuestionEliteDir dir = new OracleQuestionEliteDir();
				dir.setId(rs.getString("dir_id"));
				this.setDir(dir);
				this.setSubmituserId(rs.getString("submituser_id"));
				this.setSubmituserName(rs.getString("submituser_name"));
				this.setSubmituserType(rs.getString("submituser_type"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleEliteQuestion(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into interaction_elitequestion_info (id,title,body,key,date,course_id,dir_id,submituser_id,submituser_name,submituser_type) values(to_char(s_elitequestion_info_id.nextval),'"
				+ this.getTitle()
				+ "',?,'"
				+ this.getKeys()
				+ "','"
				+ this.getCourseId()
				+ "','"
				+ this.getDir().getId()
				+ "','"
				+ this.getSubmituserId()
				+ "','"
				+ this.getSubmituserName()
				+ "','" + this.getSubmituserType() + "')";
		int i = db.executeUpdate(sql, this.getBody());
		UserAddLog.setDebug("OracleEliteQuestion.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		ArrayList sqlgroup = new ArrayList();
		sql = "delete from interaction_elitequestion_info where id='"
				+ this.getId() + "'";
		sqlgroup.add(sql);
		sql = "delete from interaction_eliteanswer_info where elitequestion_id='"
				+ this.getId() + "'";
		sqlgroup.add(sql);
		
		int i = db.executeUpdateBatch(sqlgroup);
		UserDeleteLog.setDebug("OracleEliteQuestion.delete() SQL1=" + (String)sqlgroup.get(0) + " SQL=" + (String)sqlgroup.get(1) + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update interaction_elitequestion_info set title='"
				+ this.getTitle() + "',date=sysdate,key='" + this.getKeys()
				+ "',submituser_id='" + this.getSubmituserId()
				+ "',submituser_name='" + this.getSubmituserName()
				+ "',submituser_type='" + this.getSubmituserType()
				+ "',course_id='" + this.getCourseId() + "',dir_id='"
				+ this.getDir().getId() + "',body=? where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleEliteQuestion.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int addToElite(QuestionEliteDir dir) {
		// TODO 自动生成方法存根
		return 0;
	}

}
