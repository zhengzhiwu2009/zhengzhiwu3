package com.whaty.platform.database.oracle.standard.vote.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.VoteLog;
import com.whaty.platform.vote.basic.VoteList;
import com.whaty.platform.vote.util.exception.VoteException;

public class OracleVoteList extends VoteList {

	private static String SUGGESTSQL = "select id,paper_id,note,found_date,ip,status from vote_suggest ";

	private static String PAPERSQL = "select ID,TITLE,PICTITLE,ACTIVE,FOUND_DATE,START_DATE,END_DATE,NOTE,VIEW_SUGGEST,LIMIT_DIFFIP,LIMIT_DIFFSESSION,CAN_SUGGEST,type,keywords from vote_paper ";

	public List getVotePaperList(Page page, List searchList, List orderList)
			throws VoteException {
		dbpool db = new dbpool();
		List papers = new ArrayList();
		MyResultSet rs = null;
		try {
			String sql = PAPERSQL
					+ Conditions.convertToCondition(searchList, orderList);
			VoteLog.setDebug(sql);
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleVotePaper paper = new OracleVotePaper();
				paper.setId(rs.getString("id"));
				paper.setTitle(rs.getString("title"));
				paper.setPicTitle(rs.getString("pictitle"));
				if (rs.getInt("active") == 1)
					paper.setActive(true);
				else
					paper.setActive(false);
				paper.setFoundDate(rs.getDate("found_date"));
				paper.setStartDate(rs.getDate("start_date"));
				paper.setEndDate(rs.getDate("end_date"));
				paper.setNote(rs.getString("note"));
				paper.setType(rs.getString("type"));
				paper.setKeywords(rs.getString("keywords"));
				if (rs.getInt("view_suggest") == 1)
					paper.setViewSuggest(true);
				else
					paper.setViewSuggest(false);
				if (rs.getInt("LIMIT_DIFFIP") == 1)
					paper.setLimitDiffIP(true);
				else
					paper.setLimitDiffIP(false);
				if (rs.getInt("LIMIT_DIFFSESSION") == 1)
					paper.setLimitDiffSession(true);
				else
					paper.setLimitDiffSession(false);
				if (rs.getInt("CAN_SUGGEST") == 1)
					paper.setCanSuggest(true);
				else
					paper.setCanSuggest(false);
				papers.add(paper);
			}
		} catch (SQLException e) {
			VoteLog.setError(e.getMessage());
		} finally {
			db.close(rs);
			db = null;
		}
		return papers;
	}

	public List getVotePaperSuggests(Page page, List searchList, List orderList)
			throws VoteException {
		dbpool db = new dbpool();
		List suggests = new ArrayList();
		MyResultSet rs = null;
		try {
			String sql = SUGGESTSQL
					+ Conditions.convertToCondition(searchList, orderList);
			VoteLog.setDebug(sql);
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleVoteSuggest suggest = new OracleVoteSuggest();
				suggest.setId(rs.getString("id"));
				suggest.setNote(rs.getString("note"));
				suggest.setVotePaperId(rs.getString("paper_id"));
				suggest.setFound_date(rs.getDate("found_date"));
				suggest.setIp(rs.getString("ip"));
				if (rs.getInt("status") == 1)
					suggest.setActive(true);
				else
					suggest.setActive(false);
				suggests.add(suggest);
			}
		} catch (SQLException e) {
			VoteLog.setError(e.getMessage());
		} finally {
			db.close(rs);
			db = null;
		}
		return suggests;
	}

	public int getVotePaperNum(List searchList) throws VoteException {
		dbpool db = new dbpool();
		String sql = PAPERSQL + Conditions.convertToCondition(searchList, null);
		return db.countselect(sql);
	}

	public int getVotePaperSuggestsNum(List searchList) throws VoteException {
		dbpool db = new dbpool();
		String sql = SUGGESTSQL
				+ Conditions.convertToCondition(searchList, null);
		return db.countselect(sql);
	}

}
