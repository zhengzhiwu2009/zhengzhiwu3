package com.whaty.platform.database.oracle.standard.interaction.exam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.answer.Question;
import com.whaty.platform.interaction.answer.QuestionEliteDir;
import com.whaty.platform.interaction.exam.ExamQuestion;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleExamQuestion extends ExamQuestion {
	public OracleExamQuestion() {
	}

	public OracleExamQuestion(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		boolean reStatus = false;
		String sql = "";
		sql = "select id,title,body,publish_date,exambatch_id,submituser_id,submituser_name,submituser_type,question_type,questiontype_id "
				+ " from (select eqi.id,eqi.title,body,to_char(publish_date,'yyyy-mm-dd') as publish_date,exambatch_id,submituser_id," +
				"submituser_name,submituser_type,eqt.name as question_type,eqt.id as questiontype_id from exam_question_info eqi," +
				"exam_question_type eqt where eqi.question_type=eqt.id(+) and eqi.id = '"
				+ aid + "')";
		
		//System.out.println(sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setBody(rs.getString("body"));
				this.setPublishDate(rs.getString("publish_date"));
				this.setExambatchId(rs.getString("exambatch_id"));
				this.setSubmituserId(rs.getString("submituser_id"));
				this.setSubmituserName(rs.getString("submituser_name"));
				this.setSubmituserType(rs.getString("submituser_type"));
				this.setQuestionType(rs.getString("questiontype_id"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleExamQuestion(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		
		sql = "insert into exam_question_info (id,title,body,exambatch_id,submituser_id,submituser_name,submituser_type,question_type) values(to_char(s_exam_question_info_id.nextval),'"
				+ this.getTitle()
				+ "',?,'"
				+ this.getExambatchId()
				+ "','"
				+ this.getSubmituserId()
				+ "','"
				+ this.getSubmituserName()
				+ "','"
				+ this.getSubmituserType()
				+ "','" 
			    + this.getQuestionType() + "')";
		int i = db.executeUpdate(sql, this.getBody());
		UserAddLog.setDebug("OracleExamQuestion.add() SQL=" + sql + " COUNT=" + i
				+ " DATE=" + new Date());
		return i;
		
		
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		ArrayList sqlgroup = new ArrayList();
		sql = "delete from exam_question_info where id='" + this.getId()
				+ "'";
		sqlgroup.add(sql);
		sql = "delete from exam_answer_info where question_id='"
				+ this.getId() + "'";
		sqlgroup.add(sql);
		int i = db.executeUpdateBatch(sqlgroup);
		UserDeleteLog.setDebug("OracleExamQuestion.delete() SQL1="
				+ (String) sqlgroup.get(0) + " SQL2="
				+ (String) sqlgroup.get(1) + " COUNT=" + i + " DATE="
				+ new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update exam_question_info set title='" + this.getTitle()
				+ "',publish_date=sysdate,submituser_id='" + this.getSubmituserId()
				+ "',submituser_name='" + this.getSubmituserName()
				+ "',submituser_type='" + this.getSubmituserType()
				+ "',exambatch_id='" + this.getExambatchId() + "',body=? where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleExamQuestion.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
