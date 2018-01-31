package com.whaty.platform.database.oracle.standard.interaction.exam;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.answer.Answer;
import com.whaty.platform.interaction.exam.ExamAnswer;
import com.whaty.platform.interaction.exam.ExamUserQuestion;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleUserQuestion extends ExamUserQuestion {
	public OracleUserQuestion() {
	}
	
	public int add() {
		dbpool db = new dbpool();
		String sql1 = "";
		sql1 = "insert into exam_user_question (id,user_id,question_id) values(to_char(s_exam_user_question_id.nextval),'"+this.getUserId()+"','"+this.getQuestion().getId()+"')";
		int i = db.executeUpdate(sql1);
		UserAddLog.setDebug("OracleUserQuestion.add() SQL=" + sql1 + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from exam_user_question where user_id='" + this.getUserId()
				+ "' and question_id='"+this.getQuestion().getId()+"'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleUserQuestion.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}
	public ExamUserQuestion getExamUserQuestion(String question_id,String user_id) {
		// TODO Auto-generated method stub

		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,user_id question_id from exam_user_question where user_id='"+user_id+"' and question_id='"+question_id+"'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleExamQuestion question = new OracleExamQuestion();
				question.setId(rs.getString("question_id"));
				this.setQuestion(question);
				this.setUserId(rs.getString("user_id"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("getExamUserQuestion: error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return this;
	}

}
