package com.whaty.platform.database.oracle.standard.vote.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.platform.util.log.VoteLog;
import com.whaty.platform.vote.basic.VotePaper;
import com.whaty.platform.vote.basic.VoteQuestion;
import com.whaty.platform.vote.util.exception.VoteException;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

public class OracleVotePaper extends VotePaper {

	public OracleVotePaper() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleVotePaper(String paperId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			String sql = "select ID,TITLE,PICTITLE,ACTIVE,FOUND_DATE,START_DATE,END_DATE,"
					+ "NOTE,VIEW_SUGGEST,LIMIT_DIFFIP,LIMIT_DIFFSESSION,CAN_SUGGEST,type,keywords "
					+ " from vote_paper where id='" + paperId + "'";
			VoteLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setPicTitle(rs.getString("pictitle"));
				this.setFoundDate(rs.getDate("found_date"));
				this.setStartDate(rs.getDate("start_date"));
				this.setEndDate(rs.getDate("end_date"));
				this.setNote(rs.getString("note"));
				if (rs.getInt("active") == 1)
					this.setActive(true);
				else
					this.setActive(false);
				if (rs.getInt("view_suggest") == 1)
					this.setViewSuggest(true);
				else
					this.setViewSuggest(false);
				if (rs.getInt("LIMIT_DIFFIP") == 1)
					this.setLimitDiffIP(true);
				else
					this.setLimitDiffIP(false);
				if (rs.getInt("LIMIT_DIFFSESSION") == 1)
					this.setLimitDiffSession(true);
				else
					this.setLimitDiffSession(false);
				if (rs.getInt("CAN_SUGGEST") == 1)
					this.setCanSuggest(true);
				else
					this.setCanSuggest(false);
				this.setType(rs.getString("type"));
				this.setKeywords(rs.getString("keywords"));
			}
		} catch (SQLException e) {
			VoteLog.setError(e.getMessage());
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public void addVoteQuestion(VoteQuestion question) throws VoteException {
		dbpool db = new dbpool();
		int itemNum = question.getItemTotalNum();
		if (itemNum < 1)
			throw new VoteException("no voteQuestion!");
		String itemStr = "";
		String itemValue = "";
		List items = question.getQuestionItems();
		for (int i = 0; i < itemNum; i++) {
			itemStr = itemStr + ",item" + (i + 1);
			itemValue = itemValue + ",'" + items.get(i) + "'";
		}
		String sql = "insert into vote_question (id,paper_id,question_body,question_type,item_num"
				+ itemStr
				+ ") "
				+ "values(to_char(s_vote_id.nextval),'"
				+ this.getId()
				+ "',"
				+ "'"
				+ question.getQuestionBody()
				+ "','"
				+ question.getQuestionType()
				+ "',"
				+ ""
				+ question.getItemTotalNum() + itemValue + ")";
		
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleVotePaper.addVoteQuestion(VoteQuestion question) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	public List getVoteQuestions() throws VoteException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String itemStr = "";
		List questions = new ArrayList();
		for (int i = 0; i < 15; i++) {
			itemStr = itemStr + ",item" + (i + 1) + ",item" + (i + 1)
					+ "_result";
		}
		try {
			String sql = "select id,paper_id,question_body,question_type,item_num"
					+ itemStr
					+ " from vote_question "
					+ "where paper_id='"
					+ this.getId() + "'";
			VoteLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleVoteQuestion question = new OracleVoteQuestion();
				question.setId(rs.getString("id"));
				question.setQuestionBody(rs.getString("question_body"));
				question.setQuestionType(rs.getString("question_type"));
				question.setItemTotalNum(rs.getInt("item_num"));
				List items = new ArrayList();
				List itemResults = new ArrayList();
				for (int i = 0; i < question.getItemTotalNum(); i++) {
					items.add(rs.getString("item" + (i + 1)));
					itemResults.add(rs.getString("item" + (i + 1) + "_result"));
				}
				question.setQuestionItems(items);
				question.setQuestionItemResults(itemResults);
				questions.add(question);
			}
		} catch (SQLException e) {
			VoteLog.setError(e.getMessage());
		} finally {
			db.close(rs);
			db = null;
		}
		return questions;

	}

	public void removeVoteQuestion(List questionIds) throws VoteException {
		dbpool db = new dbpool();
		String questionIdStr = "";
		if (questionIds.size() < 1)
			throw new VoteException("no question id!");
		for (int i = 0; i < questionIds.size(); i++) {
			questionIdStr = questionIdStr + "'" + (String) questionIds.get(i)
					+ "',";
		}
		questionIdStr = questionIdStr.substring(0, questionIdStr.length() - 1);
		String sql = "delete from vote_question where id in (" + questionIdStr
				+ ") and paper_id='" + this.getId() + "'";
		
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleVotePaper.removeVoteQuestion(List questionIds) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());

	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		int status = 0;
		int viewSuggest = 0;
		int limitIp = 0;
		int limitSession = 0;
		int canSuggest = 0;
		if (this.isActive())
			status = 1;
		if (this.isViewSuggest())
			viewSuggest = 1;
		if (this.isLimitDiffIP())
			limitIp = 1;
		if (this.isLimitDiffSession())
			limitSession = 1;
		if (this.isCanSuggest())
			canSuggest = 1;
		StrManage strManage = StrManageFactory.creat();
		String sql;
		try {
			sql = "insert into vote_paper(ID,TITLE,PICTITLE,ACTIVE,START_DATE,END_DATE,"
					+ "NOTE,VIEW_SUGGEST,LIMIT_DIFFIP,LIMIT_DIFFSESSION,CAN_SUGGEST,type,keywords) values("
					+ "to_char(s_vote_id.nextval),'"
					+ this.getTitle()
					+ "','"
					+ this.getPicTitle()
					+ "',"
					+ status
					+ ","
					+ "to_date('"
					+ strManage.dateToStr(this.getStartDate(), "yyyy-MM-dd")
					+ "','yyyy-mm-dd'),"
					+ "to_date('"
					+ strManage.dateToStr(this.getEndDate(), "yyyy-MM-dd")
					+ "','yyyy-mm-dd'),"
					+ "'"
					+ this.getNote()
					+ "',"
					+ viewSuggest
					+ ","
					+ limitIp
					+ ","
					+ limitSession
					+ ","
					+ canSuggest
					+ ",'"
					+ this.getType()
					+ "','"
					+ this.getKeywords() + "')";
			int i = db.executeUpdate(sql);
			UserAddLog.setDebug("OracleVotePaper.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			return i;
		} catch (WhatyUtilException e) {
			VoteLog.setError("date error!");
			throw new PlatformException("insert into vote_paper error!");
		}

	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from vote_paper where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleVotePaper.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		int status = 0;
		int viewSuggest = 0;
		int limitIp = 0;
		int limitSession = 0;
		int canSuggest = 0;
		if (this.isActive())
			status = 1;
		if (this.isViewSuggest())
			viewSuggest = 1;
		if (this.isLimitDiffIP())
			limitIp = 1;
		if (this.isLimitDiffSession())
			limitSession = 1;
		if (this.isCanSuggest())
			canSuggest = 1;
		StrManage strManage = StrManageFactory.creat();
		String sql;
		try {
			sql = "update  vote_paper set " + "TITLE='" + this.getTitle()
					+ "'," + "PICTITLE='" + this.getPicTitle() + "',"
					+ "ACTIVE=" + status + "," + "START_DATE=" + "to_date('"
					+ strManage.dateToStr(this.getStartDate(), "yyyy-MM-dd")
					+ "','yyyy-mm-dd')," + "END_DATE=" + "to_date('"
					+ strManage.dateToStr(this.getEndDate(), "yyyy-MM-dd")
					+ "','yyyy-mm-dd')," + "NOTE='" + this.getNote()
					+ "',VIEW_SUGGEST=" + viewSuggest + ", " + "LIMIT_DIFFIP="
					+ limitIp + ", " + "LIMIT_DIFFSESSION=" + limitSession
					+ ", CAN_SUGGEST=" + canSuggest + ", type='"
					+ this.getType() + "', keywords='" + this.getKeywords()
					+ "' where id='" + this.getId() + "'";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleVotePaper.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			return i;
		} catch (WhatyUtilException e) {
			VoteLog.setError("date error!");
			throw new PlatformException("update vote_paper error!");
		}
	}

	public int getVoteNum() throws VoteException {
		dbpool db = new dbpool();
		int i = 0;
		String sql = "select id from vote_record where paper_id='"
				+ this.getId() + "'";
		i = db.countselect(sql);
		return i;
	}

}
