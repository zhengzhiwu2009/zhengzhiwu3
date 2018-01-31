package com.whaty.platform.database.oracle.standard.interaction.exam;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.answer.Answer;
import com.whaty.platform.interaction.exam.ExamAnswer;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleExamAnswer extends ExamAnswer {
	public OracleExamAnswer() {
	}
	
	public OracleExamAnswer(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,question_id,body,publish_date,publish_id,publish_name,publish_type,is_key "
				+ " from (select id,question_id,body,to_char(publish_date,'yyyy-mm-dd') as publish_date," +
				"publish_id,publish_name,publish_type,is_key from exam_answer_info where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleExamQuestion question = new OracleExamQuestion();
				question.setId(rs.getString("question_id"));
				this.setQuestion(question);
				this.setBody(rs.getString("body"));
				this.setPublishDate(rs.getString("publish_date"));
				this.setPublishId(rs.getString("publish_id"));
				this.setPublishName(rs.getString("publish_name"));
				this.setPublishType(rs.getString("publish_type"));
				this.setIsKey(rs.getString("is_key"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleExamAnswer(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}
    
	public int add() {
		dbpool db = new dbpool();
		String sql1 = "";
		String sql2 = "";
		sql1 = "insert into exam_answer_info (id,question_id,body,publish_id,publish_name,publish_type) values(to_char(s_exam_answer_info_id.nextval),'"
				+ this.getQuestion().getId()
				+ "',?,'"
				+ this.getPublishId()
				+ "','"
				+ this.getPublishName()
				+ "','"
				+ this.getPublishType()
				+ "')";
       
		int i = db.executeUpdate(sql1, this.getBody());
		UserAddLog.setDebug("OracleExamAnswer.add() SQL=" + sql1 + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from exam_answer_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleExamAnswer.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update exam_answer_info set question_id='"
				+ this.getQuestion().getId()
				+ "',publish_date=sysdate,publish_id='" + this.getPublishId()
				+ "',publish_name='" + this.getPublishName() + "',publish_type='"
				+ this.getPublishType() + "',body=? where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleExamAnswer.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	public ExamAnswer getExamAnswerTeacher(String question_id) {
		// TODO Auto-generated method stub

		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,question_id,body,publish_date,publish_id,publish_name,publish_type,is_key "
				+ " from (select id,question_id,body,to_char(publish_date,'yyyy-mm-dd') as publish_date," +
				"publish_id,publish_name,publish_type,is_key from exam_answer_info where question_id = '"
				+ question_id + "' and publish_type='manager')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleExamQuestion question = new OracleExamQuestion();
				question.setId(rs.getString("question_id"));
				this.setQuestion(question);
				this.setBody(rs.getString("body"));
				this.setPublishDate(rs.getString("publish_date"));
				this.setPublishId(rs.getString("publish_id"));
				this.setPublishName(rs.getString("publish_name"));
				this.setPublishType(rs.getString("publish_type"));
				this.setIsKey(rs.getString("is_key"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("getExamAnswerTeacher(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return this;
	}
	
	public ExamAnswer getExamAnswerKey(String question_id) {
		// TODO Auto-generated method stub

		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,question_id,body,publish_date,publish_id,publish_name,publish_type,is_key "
				+ " from (select id,question_id,body,to_char(publish_date,'yyyy-mm-dd') as publish_date," +
				"publish_id,publish_name,publish_type,is_key from exam_answer_info where question_id = '"
				+ question_id + "' and is_key='1')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleExamQuestion question = new OracleExamQuestion();
				question.setId(rs.getString("question_id"));
				this.setQuestion(question);
				this.setBody(rs.getString("body"));
				this.setPublishDate(rs.getString("publish_date"));
				this.setPublishId(rs.getString("publish_id"));
				this.setPublishName(rs.getString("publish_name"));
				this.setPublishType(rs.getString("publish_type"));
				this.setIsKey(rs.getString("is_key"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("getExamAnswerKey(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return this;
	}

	public int toKey() {
		dbpool db = new dbpool();
		String sql1 = "";
		sql1 = "update exam_answer_info set is_key='1' where id='"+this.getId()+"'";
       
		int i = db.executeUpdate(sql1);
		UserAddLog.setDebug("OracleExamAnswer.toKey() SQL=" + sql1 + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
