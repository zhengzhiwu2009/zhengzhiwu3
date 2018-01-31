package com.whaty.platform.database.oracle.standard.interaction.answer;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.answer.EliteAnswer;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleEliteAnswer extends EliteAnswer {
	public OracleEliteAnswer() {
	}

	public OracleEliteAnswer(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,elitequestion_id,body,date,reuser_id,reuser_name,reuser_type"
				+ " from (select id,elitequestion_id,body,to_char(date,'yyyy-mm-dd') as date,reuser_id,reuser_name,reuser_type from interaction_eliteanswer_info where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleEliteQuestion eliteQuestion = new OracleEliteQuestion();
				eliteQuestion.setId(rs.getString("elitequestion_id"));
				this.setEliteQuestion(eliteQuestion);
				this.setBody(rs.getString("body"));
				this.setDate(rs.getString("date"));
				this.setReuserId(rs.getString("reuser_id"));
				this.setReuserName(rs.getString("reuser_name"));
				this.setReuserType(rs.getString("reuser_type"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleEliteAnswer(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into interaction_eliteanswer_info (id,elitequestion_id,body,reuser_id,reuser_name,reuser_type) values(to_char(s_eliteanswer_info_id.nextval),'"
				+ this.getEliteQuestion().getId()
				+ "',?,'"
				+ this.getReuserId()
				+ "','"
				+ this.getReuserName()
				+ "','"
				+ this.getReuserType() + "')";
		int i = db.executeUpdate(sql, this.getBody());
		UserAddLog.setDebug("OracleEliteAnswer.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from interaction_eliteanswer_info where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleEliteAnswer.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update interaction_eliteanswer_info set elitequestion_id='"
				+ this.getEliteQuestion().getId()
				+ "',date=sysdate,reuser_id='" + this.getReuserId()
				+ "',reuser_name='" + this.getReuserName() + "',reuser_type='"
				+ this.getReuserType() + "',body=? where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleEliteAnswer.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
