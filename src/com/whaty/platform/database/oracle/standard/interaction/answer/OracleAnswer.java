package com.whaty.platform.database.oracle.standard.interaction.answer;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.answer.Answer;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleAnswer extends Answer {
	public OracleAnswer() {
	}
	
	public OracleAnswer(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,question_id,body,publish_date,reuser_id,reuser_name,reuser_type"
				+ " from (select id,question_id,body,to_char(publish_date,'yyyy-mm-dd') as publish_date,reuser_id,reuser_name,reuser_type from interaction_answer_info where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleQuestion question = new OracleQuestion();
				question.setId(rs.getString("question_id"));
				this.setQuestion(question);
				this.setBody(rs.getString("body"));
				this.setDate(rs.getString("publish_date"));
				this.setReuserId(rs.getString("reuser_id"));
				this.setReuserName(rs.getString("reuser_name"));
				this.setReuserType(rs.getString("reuser_type"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleAnswer(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql1 = "";
		String sql2 = "";
		sql1 = "insert into interaction_answer_info (id,question_id,body,reuser_id,reuser_name,reuser_type) values(to_char(s_answer_info_id.nextval),'"
				+ this.getQuestion().getId()
				+ "',?,'"
				+ this.getReuserId()
				+ "','"
				+ this.getReuserName()
				+ "','"
				+ this.getReuserType()
				+ "')";
       
		sql2 = "update interaction_question_info set re_status = '1' where id = '"
				+ this.getQuestion().getId() + "'";
		int i = db.executeUpdate(sql1, this.getBody());
		int j = db.executeUpdate(sql2);
		UserAddLog.setDebug("OracleAnswer.add() SQL=" + sql1 + " COUNT=" + i + " DATE=" + new Date());
		UserUpdateLog.setDebug("OracleAnswer.add() SQL=" + sql2 + " COUNT=" + j + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from interaction_answer_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleAnswer.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update interaction_answer_info set question_id='"
				+ this.getQuestion().getId()
				+ "',publish_date=sysdate,reuser_id='" + this.getReuserId()
				+ "',reuser_name='" + this.getReuserName() + "',reuser_type='"
				+ this.getReuserType() + "',body=? where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleAnswer.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
