package com.whaty.platform.database.oracle.standard.test.onlinetest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.test.paper.OracleTestPaper;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.test.onlinetest.OnlineTestCourse;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleOnlineTestCourse extends OnlineTestCourse {
	public OracleOnlineTestCourse() {
	};
	
	public OracleOnlineTestCourse(String id) {
		String sql = "select id,title,group_id,batch_id,note,start_date,end_date,isautocheck,ishiddenanswer,status,creatuser,creatdate,fk_batch_id "
				+ " from (select id,title,group_id,batch_id,note,start_date,end_date,isautocheck,ishiddenanswer,status,creatuser," +
						"to_char(creatdate,'yyyy-mm-dd') as creatdate,fk_batch_id "
				+ " from onlinetest_course_info) where id='" + id + "'";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setNote(rs.getString("note"));
				this.setTitle(rs.getString("title"));
				this.setGroupId(rs.getString("group_id"));
				this.setBatch_id(rs.getString("fk_batch_id"));
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
		String sql = "insert into onlinetest_course_info (id,title,group_id,batch_id,note,start_date,end_date,isautocheck,ishiddenanswer,status,creatuser,creatdate) values ("
				+ "to_char(s_onlinetest_course_info_id.nextval), '"
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
		UserAddLog.setDebug("OracleOnlineTestCourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	public int add(boolean flag) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into onlinetest_course_info (id,title,group_id,batch_id,note,start_date,end_date,isautocheck,ishiddenanswer,status,creatuser,creatdate,fk_batch_id ) values ("
				+ "to_char(s_onlinetest_course_info_id.nextval), '"
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
				+ "',sysdate,'"
//				+this.getBatch_id()+"')";
				+"ff8080812253f04f0122540a58000004')";
		
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleOnlineTestCourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update onlinetest_course_info set title='"
				+ this.getTitle() + "', group_id='" + this.getGroupId()
				+ "', batch_id='" + this.getBatch().getId() + "', status='"
				+ this.getStatus() + "', note='" + this.getNote()
				+ "', start_date='" + this.getStartDate() + "', end_date='"
				+ this.getEndDate() + "', isautocheck='"
				+ this.getIsAutoCheck() + "', ishiddenanswer='"
				+ this.getIsHiddenAnswer() + "' where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleOnlineTestCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	public int update(boolean flag) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update onlinetest_course_info set title='"
				+ this.getTitle() + "', group_id='" + this.getGroupId()
				+ "', batch_id='" + this.getBatch().getId() + "', status='"
				+ this.getStatus() + "', note='" + this.getNote()
				+ "', start_date='" + this.getStartDate() + "', end_date='"
				+ this.getEndDate() + "', isautocheck='"
				+ this.getIsAutoCheck() + "', ishiddenanswer='"
				+ this.getIsHiddenAnswer() + "',fk_batch_id='"+this.getBatch_id()+"' where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleOnlineTestCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String checkSql = 
			"select *\n" +
			"  from onlinetest_course_paper ocp, test_testpaper_info tti\n" + 
			" where tti.id = ocp.paper_id\n" + 
			"   and ocp.testcourse_id = '"+ this.getId() + "'";

		int cou = db.countselect(checkSql);
		
		if(cou>0){//已经存在试卷，不能删除
			return -1;
		}
		String sql = "delete from onlinetest_course_info where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleOnlineTestCourse.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int cancelActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update onlinetest_course_info set status='0' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleOnlineTestCourse.cancelActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int setActive() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update onlinetest_course_info set status='1' where id ='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleOnlineTestCourse.setActive() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int reverseActive() {
		if (this.getStatus().equals("0"))
			return this.setActive();
		else
			return this.cancelActive();
	}

	public List getTestPapers(Page page) {
		dbpool db = new dbpool();
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		//学员段课程考试 和 协会管理端试卷管理 sql 复用，sql增加status=‘1’ 试卷状态无效不能用来考试
		String sql = "";
		if ("0".equals(userSession.getUserLoginType())) {
			sql = "select id,title,creatuser,creatdate,status,note,time,type,group_id from "
				+ "(select a.id,a.title,a.creatuser,to_char(a.creatdate,'yyyy-mm-dd') as creatdate,a.status,a.note,"
				+ "a.time,a.type,a.group_id from test_testpaper_info a,onlinetest_course_paper b where a.status='1' and a.id=b.paper_id and b.testcourse_id='"
				+ this.getId() + "')  order by title asc ";
		} else {
			sql = "select id,title,creatuser,creatdate,status,note,time,type,group_id from "
				+ "(select a.id,a.title,a.creatuser,to_char(a.creatdate,'yyyy-mm-dd') as creatdate,a.status,a.note,"
				+ "a.time,a.type,a.group_id from test_testpaper_info a,onlinetest_course_paper b where a.id=b.paper_id and b.testcourse_id='"
				+ this.getId() + "')  order by title asc ";
		}
		MyResultSet rs = null;
		//System.out.println(sql);
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
		//System.out.println(sql);
		return testPapers;
	}

	public int getTestPapersNum() {
		dbpool db = new dbpool();
		String sql = "select id,title,creatuser,creatdate,status,note,time,type,group_id from "
				+ "(select a.id,a.title,a.creatuser,to_char(a.creatdate,'yyyy-mm-dd') as creatdate,a.status,a.note,"
				+ "a.time,a.type,a.group_id from test_testpaper_info a,onlinetest_course_paper b where a.id=b.paper_id and b.testcourse_id='"
				+ this.getId() + "')";
		return db.countselect(sql);
	}

	public int addTestPaper(String paperId) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into onlinetest_course_paper (id,paper_id,testcourse_id) values(to_char(s_onlinetest_course_paper_id.nextval),'"
				+ paperId + "','" + this.getId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleOnlineTestCourse.addTestPaper(String paperId) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int deleteTestPaper(String paperId) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from onlinetest_course_paper where paper_id='" + paperId
				+ "' and testcourse_id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleOnlineTestCourse.deleteTestPaper(String paperId) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
