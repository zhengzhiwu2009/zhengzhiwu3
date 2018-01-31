package com.whaty.platform.database.oracle.standard.test.paper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.test.question.OraclePaperQuestion;
import com.whaty.platform.test.paper.HomeworkPaper;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleHomeworkPaper extends HomeworkPaper {
	public OracleHomeworkPaper() {
	};
	
	public OracleHomeworkPaper(String id) {
		String sql = "select id,title,creatuser,to_char(creatdate,'yyyy-mm-dd') as creatdate,status,note,comments,startdate,enddate,type,group_id,batch_id "
				+ " from test_homeworkpaper_info where id='" + id + "'";
		
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setNote(rs.getString("note"));
				this.setCreatDate(rs.getString("creatdate"));
				this.setCreatUser(rs.getString("creatuser"));
				this.setStatus(rs.getString("status"));
				this.setTitle(rs.getString("title"));
				this.setComments(rs.getString("comments"));
				this.setEndDate(rs.getString("enddate"));
				this.setStartDate(rs.getString("startdate"));
				this.setType(rs.getString("type"));
				this.setGroupId(rs.getString("group_id"));
				this.setBatch_id(rs.getString("batch_id"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String id = "";
		String sql_pre = "select to_char(s_test_paper_info.nextval) as id from dual";
		MyResultSet rs = db.executeQuery(sql_pre);
		try {
			if (rs != null && rs.next())
				id = rs.getString("id");
		} catch (SQLException e) {
			throw new PlatformException();
		}
		db.close(rs);
		String sql = "insert into test_homeworkpaper_info(id,title,creatuser,creatdate,status,note,comments,"
				+ "startdate,type,group_id,enddate) values ("
				+ id
				+ ", '"
				+ this.getTitle()
				+ "', '"
				+ this.getCreatUser()
				+ "', sysdate, '"
				+ this.getStatus()
				+ "', '"
				+ this.getNote()
				+ "', '"
				+ this.getComments()
				+ "', '"
				+ this.getStartDate()
				+ "', '"
				+ this.getType()
				+ "', '"
				+ this.getGroupId()
				+ "', '"
				+ this.getEndDate() + "')";
		int count = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleHomeworkPaper.add() SQL=" + sql + " COUNT=" + count + " DATE=" + new Date());
		if (count > 0) {
			count = Integer.parseInt(id);
		} else {
			throw new PlatformException();
		}
		return count;
	}
	public int add(boolean flag) throws PlatformException {
		dbpool db = new dbpool();
		String id = "";
		String sql_pre = "select to_char(s_test_paper_info.nextval) as id from dual";
		MyResultSet rs = db.executeQuery(sql_pre);
		try {
			if (rs != null && rs.next())
				id = rs.getString("id");
		} catch (SQLException e) {
			throw new PlatformException();
		}
		db.close(rs);
		String sql = "insert into test_homeworkpaper_info(id,title,creatuser,creatdate,status,note,comments,"
				+ "startdate,type,group_id,enddate,batch_id) values ("
				+ id
				+ ", '"
				+ this.getTitle()
				+ "', '"
				+ this.getCreatUser()
				+ "', sysdate, '"
				+ this.getStatus()
				+ "', '"
				+ this.getNote()
				+ "', '"
				+ this.getComments()
				+ "', '"
				+ this.getStartDate()
				+ "', '"
				+ this.getType()
				+ "', '"
				+ this.getGroupId()
				+ "', '"
				+ this.getEndDate() + "','"
//				+this.getBatch_id()+"')";
				+"')";
		
		int count = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleHomeworkPaper.add(flag) SQL=" + sql + " COUNT=" + count + " DATE=" + new Date());
		if (count > 0) {
			count = Integer.parseInt(id);
		} else {
			throw new PlatformException();
		}
		return count;
	}

	public int update() throws PlatformException {
		String sql = "update test_homeworkpaper_info set title='"
				+ this.getTitle() + "', creatuser='" + this.getCreatUser()
				+ "', status='" + this.getStatus() + "', note='"
				+ this.getNote() + "', comments='" + this.getComments()
				+ "', startdate='" + this.getStartDate() + "',enddate='"
				+ this.getEndDate() + "',type='" + this.getType() + "',"
				+ "group_id='" + this.getGroupId() + "' where id='"
				+ this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleHomeworkPaper.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	public int update(boolean flag) throws PlatformException {
		String sql = "update test_homeworkpaper_info set title='"
				+ this.getTitle() + "', creatuser='" + this.getCreatUser()
				+ "', status='" + this.getStatus() + "', note='"
				+ this.getNote() + "', comments='" + this.getComments()
				+ "', startdate='" + this.getStartDate() + "',enddate='"
				+ this.getEndDate() + "',type='" + this.getType() + "',"
//				+ "group_id='" + this.getGroupId() + "',batch_id='"+this.getBatch_id()+"' where id='"
				+ "group_id='" + this.getGroupId() + "' where id='"
				+ this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleHomeworkPaper.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}


	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from test_paperquestion_info where testpaper_id='"
				+ this.getId() + "'";
		db.executeUpdate(sql);
		sql = "delete from test_paperpolicy_info where title='"
				+ this.getId() + "'";
		db.executeUpdate(sql);
		sql = "delete from test_homeworkpaper_history where testpaper_id='"
				+ this.getId() + "'";
		db.executeUpdate(sql);
		sql = "delete from test_homeworkpaper_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleHomeworkPaper.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getPaperQuestion(String userId) throws PlatformException {
		List pqList = new ArrayList();
		String sql = "select id,creatuser,creatdate,diff,questioncore,title,serial,score,lore,cognizetype,purpose,"
			+ "referencescore,referencetime,studentnote,teachernote,testpaper_id,type from test_paperquestion_info "
			+ "where testpaper_id='" + this.getId() + "' and user_id='" + userId + "'";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				OraclePaperQuestion pq = new OraclePaperQuestion();
				pq.setId(rs.getString("id"));
				pq.setCreatUser(rs.getString("creatuser"));
				pq.setCreatDate(rs.getString("creatdate"));
				pq.setDiff(rs.getString("diff"));
				pq.setQuestionCore(rs.getString("questioncore"));
				pq.setTitle(rs.getString("title"));
				pq.setIndex(rs.getString("serial"));
				pq.setScore(rs.getString("score"));
				pq.setLore(rs.getString("lore"));
				pq.setCognizeType(rs.getString("cognizetype"));
				pq.setPurpose(rs.getString("purpose"));
				pq.setReferenceScore(rs.getString("referencescore"));
				pq.setReferenceTime(rs.getString("referencetime"));
				pq.setStudentNote(rs.getString("studentnote"));
				pq.setTeacherNote(rs.getString("teachernote"));
				pq.setTestPaperId(rs.getString("testpaper_id"));
				pq.setType(rs.getString("type"));
				pqList.add(pq);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return pqList;
	}
	
	public List getPaperQuestion() throws PlatformException {
		String sql = "select id,creatuser,creatdate,diff,questioncore,title,serial,score,lore,cognizetype,purpose,"
				+ "referencescore,referencetime,studentnote,teachernote,testpaper_id,type from test_paperquestion_info "
				+ "where testpaper_id='" + this.getId() + "'";
		//System.out.println("++++++++++++++++"+sql);
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		List pqList = new ArrayList();
		try {
			while (rs != null && rs.next()) {
				OraclePaperQuestion pq = new OraclePaperQuestion();
				pq.setId(rs.getString("id"));
				pq.setCreatUser(rs.getString("creatuser"));
				pq.setCreatDate(rs.getString("creatdate"));
				pq.setDiff(rs.getString("diff"));
				pq.setQuestionCore(rs.getString("questioncore"));
				pq.setTitle(rs.getString("title"));
				pq.setIndex(rs.getString("serial"));
				pq.setScore(rs.getString("score"));
				pq.setLore(rs.getString("lore"));
				pq.setCognizeType(rs.getString("cognizetype"));
				pq.setPurpose(rs.getString("purpose"));
				pq.setReferenceScore(rs.getString("referencescore"));
				pq.setReferenceTime(rs.getString("referencetime"));
				pq.setStudentNote(rs.getString("studentnote"));
				pq.setTeacherNote(rs.getString("teachernote"));
				pq.setTestPaperId(rs.getString("testpaper_id"));
				pq.setType(rs.getString("type"));
				pqList.add(pq);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return pqList;
	}

	public void addPaperQuestion(List PaperQuestion) throws PlatformException {
		// TODO Auto-generated method stub

	}

	public void removePaperQuestion(List PaperQuestion)
			throws PlatformException {
		// TODO Auto-generated method stub
	}

	public int cancelActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update test_homeworkpaper_info set status='0' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleHomeworkPaper.cancelActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int setActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update test_homeworkpaper_info set status='1' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleHomeworkPaper.setActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int reverseActive() {
		if (this.getStatus().equals("0"))
			return this.setActive();
		else
			return this.cancelActive();
	}

	public int removePaperQuestions() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int removePaperQuestions(String questionIds) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}
}
