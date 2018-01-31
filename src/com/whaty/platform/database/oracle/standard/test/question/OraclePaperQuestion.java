package com.whaty.platform.database.oracle.standard.test.question;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.test.question.PaperQuestion;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OraclePaperQuestion extends PaperQuestion {
	public OraclePaperQuestion() {
	};

	public OraclePaperQuestion(PaperQuestion question) {
		this.setTitle(question.getTitle());
		this.setCreatDate(question.getCreatDate());
		this.setCreatUser(question.getCreatUser());
		this.setDiff(question.getDiff());
		this.setQuestionCore(question.getQuestionCore());
		this.setIndex(question.getIndex());
		this.setScore(question.getScore());
		this.setLore(question.getLore());
		this.setCognizeType(question.getCognizeType());
		this.setPurpose(question.getPurpose());
		this.setReferenceScore(question.getReferenceScore());
		this.setReferenceTime(question.getReferenceTime());
		this.setStudentNote(question.getStudentNote());
		this.setTeacherNote(question.getTeacherNote());
		this.setTestPaperId(question.getTestPaperId());
		this.setType(question.getType());
		this.setUserId(question.getUserId());
		this.setId(question.getId());
		this.setRemark(question.getRemark());
	};

	public OraclePaperQuestion(String id) {
		String sql = "select id,title,creatuser,creatdate,diff,serial,score,questioncore,lore,"
				+ "cognizetype,purpose,referencescore,referencetime,studentnote,teachernote,testpaper_id,type ,remark from "
				+ "test_paperquestion_info where id='" + id + "'";
		
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setCreatDate(rs.getString("creatdate"));
				this.setCreatUser(rs.getString("creatuser"));
				this.setDiff(rs.getString("diff"));
				this.setQuestionCore(rs.getString("questioncore"));
				this.setIndex(rs.getString("serial"));
				this.setScore(rs.getString("score"));
				this.setLore(rs.getString("lore"));
				this.setCognizeType(rs.getString("cognizetype"));
				this.setPurpose(rs.getString("purpose"));
				this.setReferenceScore(rs.getString("referencescore"));
				this.setReferenceTime(rs.getString("referencetime"));
				this.setStudentNote(rs.getString("studentnote"));
				this.setTeacherNote(rs.getString("teachernote"));
				this.setTestPaperId(rs.getString("testpaper_id"));
				this.setType(rs.getString("type"));
				this.setRemark(rs.getString("remark"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}

	public int add() throws PlatformException {
		String sql = "insert into test_paperquestion_info(id,title,creatuser,creatdate,diff,serial,"
				+ "score,lore,cognizetype,purpose,referencescore,referencetime,questioncore,teachernote,studentnote,testpaper_id,type,user_id,TITILELIBRARY_ID,remark) "
				+ "values (to_char(s_test_paperquestion_info.nextval), '"
				+ this.getTitle()
				+ "', '"
				+ this.getCreatUser()
				+ "', to_date('"
				+ this.getCreatDate()
				+ "','yyyy-mm-dd'), '"
				+ this.getDiff()
				+ "', '"
				+ this.getIndex()
				+ "', '"
				+ this.getScore()
				+ "', '"
				+ this.getLore()
				+ "', '"
				+ this.getCognizeType()
				+ "', '"
				+ this.getPurpose()
				+ "', '"
				+ this.getReferenceScore()
				+ "', '"
				+ this.getReferenceTime()
				+ "',?, '"
				+ this.getTeacherNote()
				+ "', '"
				+ this.getStudentNote()
				+ "', '"
				+ this.getTestPaperId()
				+ "', '" + this.getType()
				+ "','" + this.getUserId()
				+ "','" + this.getId()
				+ "','" + this.getRemark()
				+ "')";
	//	System.out.println("getIndex()======================>"+this.getIndex());
	//	System.out.println("sql1111111111111==================>"+sql);
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql, this.getQuestionCore());
		UserAddLog.setDebug("OraclePaperQuestion.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateScore() {
		String sql = "update test_paperquestion_info set  score='"
				+ this.getScore() + "'" + " where id='" + this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OraclePaperQuestion.updateScore() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		String sql = "update test_paperquestion_info set title='"
				+ this.getTitle() + "'," + " creatuser='" + this.getCreatUser()
				+ "', creatdate=to_date('" + this.getCreatDate()
				+ "','yyyy-mm-dd'), diff='" + this.getDiff() + "', serial='"
				+ this.getIndex() + "', score='" + this.getScore() + "', "
				+ "questioncore=?,lore='" + this.getLore() + "',cognizetype='"
				+ this.getCognizeType() + "'," + "purpose='"
				+ this.getPurpose() + "',referencescore='"
				+ this.getReferenceScore() + "'," + "referencetime='"
				+ this.getReferenceTime() + "',studentnote='"
				+ this.getStudentNote() + "'," + "teachernote='"
				+ this.getTeacherNote() + "',testpaper_id='"
				+ this.getTestPaperId() + "', type='" + this.getType()
				+ "' where id='" + this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql, this.getQuestionCore());
		UserUpdateLog.setDebug("OraclePaperQuestion.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		String sql = "delete from test_paperquestion_info where id='"
				+ this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OraclePaperQuestion.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	public int removeQuestion() {
		String sql = "delete from test_paperquestion_info t where t.testpaper_id='"+this.getTestPaperId()+"' and t.user_id='"+this.getUserId()+"'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OraclePaperQuestion.remove() SQL=" + sql + "COUNT=" + i + " DATE="+ new Date());
		return i;
	}
}
