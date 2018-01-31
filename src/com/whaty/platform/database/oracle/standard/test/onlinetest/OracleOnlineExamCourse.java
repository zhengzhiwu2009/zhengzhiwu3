package com.whaty.platform.database.oracle.standard.test.onlinetest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.test.paper.OracleTestPaper;
import com.whaty.platform.test.onlinetest.OnlineExamCourse;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleOnlineExamCourse extends OnlineExamCourse {

	public OracleOnlineExamCourse() {
	};	

	public OracleOnlineExamCourse(String id) {
		String sql = "select id,title,group_id,batch_id,note,start_date,end_date,isautocheck,ishiddenanswer,status,creatuser,creatdate"
				+ " from (select id,title,group_id,batch_id,note,start_date,end_date,isautocheck,ishiddenanswer,status,creatuser,to_char(creatdate,'yyyy-mm-dd') as creatdate"
				+ " from onlineexam_course_info) where id='" + id + "'";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setNote(rs.getString("note"));
				this.setTitle(rs.getString("title"));
				this.setGroupId(rs.getString("group_id"));
				OracleOnlineTestBatch batch = new OracleOnlineTestBatch();
				batch.setId(rs.getString("batch_id"));
				this.setBatch(batch);
				this.setNote(rs.getString("note"));
				this.setStartDate(rs.getString("start_date"));
				this.setEndDate(rs.getString("end_date"));
				this.setIsAutoCheck(rs.getString("isautocheck"));
				this.setIsHiddenAnswer(rs.getString("ishiddenanswer"));
				this.setStatus(rs.getString("status"));
				this.setCreatUser(rs.getString("creatuser"));
				this.setCreatDate(rs.getString("creatdate"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db=null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into onlineexam_course_info (id,title,group_id,batch_id,note,start_date,end_date,isautocheck,ishiddenanswer,status,creatuser,creatdate) values ("
				+ "to_char(s_onlineexam_course_info_id.nextval), '"
				+ this.getTitle()
				+ "', '"
				+ this.getGroupId()
				+ "', '"
				+ this.getBatch().getId()
				+ "', '"
				+ this.getNote()
				+ "', '"
				+ this.getStartDate()
				+ "', '"
				+ this.getEndDate()
				+ "', '"
				+ this.getIsAutoCheck()
				+ "', '"
				+ this.getIsHiddenAnswer()
				+ "','"
				+ this.getStatus()
				+ "','"
				+ this.getCreatUser()
				+ "',sysdate)";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleOnlineExamCourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update onlineexam_course_info set title='"
				+ this.getTitle() + "', group_id='" + this.getGroupId()
				+ "', batch_id='" + this.getBatch().getId() + "', status='"
				+ this.getStatus() + "', note='" + this.getNote()
				+ "', start_date='" + this.getStartDate() + "', end_date='"
				+ this.getEndDate() + "', isautocheck='"
				+ this.getIsAutoCheck() + "', ishiddenanswer='"
				+ this.getIsHiddenAnswer() + "' where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleOnlineExamCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from onlineexam_course_info where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleOnlineExamCourse.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int cancelActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update onlineexam_course_info set status='0' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleOnlineExamCourse.cancelActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int setActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update onlineexam_course_info set status='1' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleOnlineExamCourse.setActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int reverseActive() {
		if (this.getStatus().equals("0"))
			return this.setActive();
		else
			return this.cancelActive();
	}

	public List getExamPapers(Page page) {
		dbpool db = new dbpool();
		String sql = "select id,title,creatuser,creatdate,status,note,time,type,group_id from "
				+ "(select a.id,a.title,a.creatuser,to_char(a.creatdate,'yyyy-mm-dd') as creatdate,a.status,a.note,"
				+ "a.time,a.type,a.group_id from test_exampaper_info a,onlineexam_course_paper b where a.id=b.paper_id and b.testcourse_id='"
				+ this.getId() + "')";
		MyResultSet rs = null;
		ArrayList testPapers = null;
		try {
			db = new dbpool();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			testPapers = new ArrayList();
			while (rs != null && rs.next()) {
				OracleTestPaper testPaper = new OracleTestPaper();
				testPaper.setId(rs.getString("id"));
				testPaper.setTitle(rs.getString("title"));
				testPaper.setCreatUser(rs.getString("creatuser"));
				testPaper.setCreatDate(rs.getString("creatdate"));
				testPaper.setStatus(rs.getString("status"));
				testPaper.setNote(rs.getString("note"));
				testPaper.setTime(rs.getString("time"));
				testPaper.setType(rs.getString("type"));
				testPaper.setGroupId(rs.getString("group_id"));
				testPapers.add(testPaper);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testPapers;
	}

	public int getExamPapersNum() {
		dbpool db = new dbpool();
		String sql = "select id,title,creatuser,creatdate,status,note,time,type,group_id from "
				+ "(select a.id,a.title,a.creatuser,to_char(a.creatdate,'yyyy-mm-dd') as creatdate,a.status,a.note,"
				+ "a.time,a.type,a.group_id from test_exampaper_info a,onlineexam_course_paper b where a.id=b.paper_id and b.testcourse_id='"
				+ this.getId() + "')";
		return db.countselect(sql);
	}

	public int addExamPaper(String paperId) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into onlineexam_course_paper (id,paper_id,testcourse_id) values(to_char(s_onlineexam_course_paper_id.nextval),'"
				+ paperId + "','" + this.getId() + "')";
		//System.out.println("////////=========="+sql);
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleOnlineExamCourse.addExamPaper(String paperId) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int deleteExamPaper(String paperId) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from onlineexam_course_paper where paper_id='" + paperId
				+ "' and testcourse_id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleOnlineExamCourse.deleteExamPaper(String paperId) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
