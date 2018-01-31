package com.whaty.platform.database.oracle.standard.interaction.answer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.answer.Question;
import com.whaty.platform.interaction.answer.QuestionEliteDir;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleQuestion extends Question {
	public OracleQuestion() {
	}

	public OracleQuestion(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		boolean reStatus = false;
		String sql = "";
		sql = "select id,title,body,key,publish_date,course_id,submituser_id,submituser_name,submituser_type,re_status,question_type"
				+ " from (select iqi.id,iqi.title,body,key,to_char(publish_date,'yyyy-mm-dd') as publish_date,course_id,submituser_id,submituser_name,submituser_type,re_status,iqt.name as question_type from interaction_question_info iqi,interaction_question_type iqt where iqi.question_type=iqt.id(+) and iqi.id = '"
				+ aid + "')";
		String temp_id="  "+aid;
		if(temp_id.indexOf("exam_")>0){
			temp_id = aid.substring(5);
			sql = "select id,title,body,key,publish_date,course_id,submituser_id,submituser_name,submituser_type,re_status,question_type"
				+ " from (select iqi.id,iqi.title,body,'' as key,to_char(publish_date,'yyyy-mm-dd') as publish_date,'' as course_id,submituser_id,submituser_name,submituser_type,'1' as re_status,iqt.name as question_type from exam_question_info iqi,exam_question_type iqt where iqi.question_type=iqt.id(+) and iqi.id = '"
				+ temp_id + "')";
		}
		EntityLog.setDebug("OracleQuestion="+sql);
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
				this.setDate(rs.getString("publish_date"));
				this.setCourseId(rs.getString("course_id"));
				this.setSubmituserId(rs.getString("submituser_id"));
				this.setSubmituserName(rs.getString("submituser_name"));
				this.setSubmituserType(rs.getString("submituser_type"));
				this.setQuestionType(rs.getString("question_type"));
				if (rs.getString("re_status").equals("1"))
					reStatus = true;
				this.setReStatus(reStatus);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleQuestion(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		String reStatus = "0";
		if (this.isReStatus())
			reStatus = "1";
		sql = "insert into interaction_question_info (id,title,body,key,course_id,submituser_id,submituser_name,submituser_type,re_status,question_type) values(to_char(s_question_info_id.nextval),'"
				+ this.getTitle()
				+ "',?,'"
				+ this.getKeys()
				+ "','"
				+ this.getCourseId()
				+ "','"
				+ this.getSubmituserId()
				+ "','"
				+ this.getSubmituserName()
				+ "','"
				+ this.getSubmituserType()
				+ "','" + reStatus 
				+ "','" + this.getQuestionType() + "')";
		int i = db.executeUpdate(sql, this.getBody());
		UserAddLog.setDebug("OracleQuestion.add() SQL=" + sql + " COUNT=" + i
				+ " DATE=" + new Date());
		return i;
		
		
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		ArrayList sqlgroup = new ArrayList();
		sql = "delete from interaction_question_info where id='" + this.getId()
				+ "'";
		sqlgroup.add(sql);
		sql = "delete from interaction_answer_info where question_id='"
				+ this.getId() + "'";
		sqlgroup.add(sql);
		int i = db.executeUpdateBatch(sqlgroup);
		UserDeleteLog.setDebug("OracleQuestion.delete() SQL1="
				+ (String) sqlgroup.get(0) + " SQL2="
				+ (String) sqlgroup.get(1) + " COUNT=" + i + " DATE="
				+ new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String reStatus = "0";
		if (this.isReStatus())
			reStatus = "1";
		String sql = "";
		sql = "update interaction_question_info set title='" + this.getTitle()
				+ "',publish_date=sysdate,key='" + this.getKeys()
				+ "',submituser_id='" + this.getSubmituserId()
				+ "',submituser_name='" + this.getSubmituserName()
				+ "',submituser_type='" + this.getSubmituserType()
				+ "',course_id='" + this.getCourseId() + "',re_status='"
				+ reStatus + "',body=? where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleQuestion.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int addToElite(QuestionEliteDir dir) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		String id = "";
		sql = "select to_char(s_elitequestion_info_id.nextval) as id from dual";
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next())
				id = rs.getString("id");
		} catch (SQLException e) {
			
		}
		ArrayList sqlgroup = new ArrayList();
		sql = "insert into interaction_elitequestion_info (id,title,body,key,course_id,dir_id,submituser_id,submituser_name,submituser_type) "
				+ "select '"
				+ id
				+ "',title,body,key,course_id,'"
				+ dir.getId()
				+ "',submituser_id,submituser_name,submituser_type from interaction_question_info where id = '"
				+ this.getId() + "')";
		sqlgroup.add(sql);
		UserAddLog.setDebug("OracleQuestion.addToElite(QuestionEliteDir dir) SQL=" + sql + " DATE=" + new Date());
		if (this.isReStatus()) {
			sql = "insert into interaction_eliteanswer_info (id,elitequestion_id,body,reuser_id,reuser_name,reuser_type) "
					+ "select to_char(s_eliteanswer_info_id.nextval),'"
					+ id
					+ "',body,reuser_id,reuser_name,reuser_type from interaction_question_info where id = '"
					+ this.getId() + "')";
			sqlgroup.add(sql);
			UserAddLog.setDebug("OracleQuestion.addToElite(QuestionEliteDir dir) SQL=" + sql + " DATE=" + new Date());
		}
		int i = db.executeUpdateBatch(sqlgroup);
		return i;
	}

}
