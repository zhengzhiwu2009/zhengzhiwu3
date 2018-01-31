package com.whaty.platform.database.oracle.standard.vote.basic;

import java.util.Date;
import java.util.List;

import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.platform.util.log.VoteLog;
import com.whaty.platform.vote.basic.VoteActivity;
import com.whaty.platform.vote.util.exception.VoteException;

public class OracleVoteActivity extends VoteActivity {

	public void activeVotePapers(List paperIds, boolean active)
			throws VoteException {
		if (paperIds.size() < 1)
			return;
		String paperIdStr = "";
		for (int i = 0; i < paperIds.size(); i++) {
			paperIdStr = paperIdStr + "'" + (String) paperIds.get(i) + "',";
		}
		paperIdStr = paperIdStr.substring(0, paperIdStr.length() - 1);
		int flag = 0;
		if (active)
			flag = 1;
		String sql = "update vote_paper set active='" + flag + "' where id in("
				+ paperIdStr + ")";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleVoteActivity.activeVotePapers(List paperIds, boolean active) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i < 1)
			throw new VoteException("activeVotePaper error!");
	}

	public boolean checkSessionAndIp(String paperId, String sessionId, String Ip)
			throws VoteException {
		String condition = " ";
		if (sessionId == null && Ip == null)
			return false;
		if (sessionId != null && sessionId.length() > 0)
			condition = condition + " and session_id='" + sessionId + "'";
		if (Ip != null && Ip.length() > 0)
			condition = condition + " and Ip='" + Ip + "'";
		if (condition.length() < 1)
			return false;
		String sql = "select id from vote_record where paper_id='" + paperId
				+ "' " + condition;
		VoteLog
				.setDebug("OracleVoteActivity@Method:checkSessionAndIp()="
						+ sql);
		dbpool db = new dbpool();
		if (db.countselect(sql) < 1)
			return false;
		else
			return true;
	}

	public void activeVoteSuggests(List suggestIds, boolean active)
			throws VoteException {
		if (suggestIds.size() < 1)
			return;
		String suggestIdStr = "";
		for (int i = 0; i < suggestIds.size(); i++) {
			suggestIdStr = suggestIdStr + "'" + (String) suggestIds.get(i)
					+ "',";
		}
		suggestIdStr = suggestIdStr.substring(0, suggestIdStr.length() - 1);
		int flag = 0;
		if (active)
			flag = 1;
		String sql = "update vote_suggest set status=" + flag + " where id in("
				+ suggestIdStr + ")";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleVoteActivity.activeVoteSuggests(List suggestIds, boolean active) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i < 1)
			throw new VoteException("activeVoteSuggests error!");

	}

}
