package com.whaty.platform.database.oracle.standard.test.paper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.test.question.OraclePaperQuestion;
import com.whaty.platform.test.paper.ExperimentPaper;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleExperimentPaper extends ExperimentPaper {
	public OracleExperimentPaper() {
	};
	
	public OracleExperimentPaper(String id) {
		String sql = "select id,title,creatuser,to_char(creatdate,'yyyy-mm-dd') as creatdate,status,note,comments,startdate,enddate,type,group_id"
				+ " from test_experimentpaper_info where id='" + id + "'";
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
			
		}
		db.close(rs);
		String sql = "insert into test_experimentpaper_info(id,title,creatuser,creatdate,status,note,comments,"
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
		UserAddLog.setDebug("OracleExperimentPaper.add() SQL=" + sql + " COUNT=" + count + " DATE=" + new Date());
		if (count > 0)
			count = Integer.parseInt(id);
		return count;
	}

	public int update() throws PlatformException {
		String sql = "update test_experimentpaper_info set title='"
				+ this.getTitle() + "', creatuser='" + this.getCreatUser()
				+ "', status='" + this.getStatus() + "', note='"
				+ this.getNote() + "', comments='" + this.getComments()
				+ "', startdate='" + this.getStartDate() + "',enddate='"
				+ this.getEndDate() + "',type='" + this.getType() + "',"
				+ "group_id='" + this.getGroupId() + "' where id='"
				+ this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExperimentPaper.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from test_paperquestion_info where testpaper_id='"
				+ this.getId() + "'";
		db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleExperimentPaper.delete() SQL=" + sql + " DATE=" + new Date());
		sql = "delete from test_experimentpaper_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleExperimentPaper.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getPaperQuestion() throws PlatformException {
		String sql = "select id,creatuser,creatdate,diff,questioncore,title,serial,score,lore,cognizetype,purpose,"
				+ "referencescore,referencetime,studentnote,teachernote,testpaper_id,type from test_paperquestion_info "
				+ "where testpaper_id='" + this.getId() + "'";
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
		sql = "update test_experimentpaper_info set status='0' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExperimentPaper.cancelActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int setActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update test_experimentpaper_info set status='1' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExperimentPaper.setActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
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

	public int removePaperQuestions(String questionIds)
			throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}
}
