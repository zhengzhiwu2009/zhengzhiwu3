package com.whaty.platform.database.oracle.standard.vote.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.platform.util.log.VoteLog;
import com.whaty.platform.vote.basic.VoteQuestion;
import com.whaty.platform.vote.util.exception.VoteException;

public class OracleVoteQuestion extends VoteQuestion {

	public OracleVoteQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleVoteQuestion(String questionId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String itemStr = "";
		for (int i = 0; i < 15; i++) {
			itemStr = itemStr + ",item" + (i + 1) + ",item" + (i + 1)
					+ "_result";
		}
		try {
			String sql = "select id,paper_id,question_body,question_type,item_num"
					+ itemStr
					+ " from vote_question "
					+ "where id='"
					+ questionId + "'";
			VoteLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setQuestionBody(rs.getString("question_body"));
				this.setQuestionType(rs.getString("question_type"));
				this.setItemTotalNum(rs.getInt("item_num"));
				List items = new ArrayList();
				List itemResults = new ArrayList();
				for (int i = 0; i < this.getItemTotalNum(); i++) {
					items.add(rs.getString("item" + (i + 1)));
					itemResults.add(rs.getString("item" + (i + 1) + "_result"));
				}
				this.setQuestionItems(items);
				this.setQuestionItemResults(itemResults);

			}
		} catch (SQLException e) {
			VoteLog.setError(e.getMessage());
		} finally {
			db.close(rs);
			db = null;
		}
	}

	// δʹ��
	public void addQuestionItems(List items) throws VoteException {

		dbpool db = new dbpool();
		if (items.size() < 1)
			throw new VoteException("no question items");
		String itemStr = "";
		for (int i = 0; i < items.size(); i++) {
			itemStr = itemStr + "item" + (i + 1) + "='" + (String) items.get(i)
					+ "',";
		}
		itemStr = itemStr.substring(0, itemStr.length() - 1);
		String sql = "update vote_question set " + itemStr + " where id='"
				+ this.getId() + "'";
		
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleVoteQuestion.addQuestionItems(List items) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	public void addQuestionResult(int num, int itemNum) throws VoteException {
		dbpool db = new dbpool();
		String sql = "update vote_question set item" + itemNum + "_result=item"
				+ itemNum + "_result+" + num + "  where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleVoteQuestion.addQuestionResult(int num,int itemNum) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i < 1)
			throw new VoteException("addQuestionResult error!(sql=" + sql + ")");

	}

	public void clearResult() throws VoteException {
		dbpool db = new dbpool();
		String itemStr = "";
		for (int i = 0; i < 15; i++) {
			itemStr = itemStr + "item" + (i + 1) + "_result=0,";
		}
		itemStr = itemStr.substring(0, itemStr.length() - 1);
		String sql = "update vote_question set " + itemStr + " where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleVoteQuestion.clearResult() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		

	}

	// δʹ��
	public void deleteQuestionItems(int num) throws VoteException {
		dbpool db = new dbpool();
		List sqlList = new ArrayList();
		sqlList.add("update vote_question set item" + num + "=null,item" + num
				+ "_result=0 where id='" + this.getId() + "'");
		String itemChange = "";
		for (int i = num; i < this.getItemTotalNum(); i++) {
			itemChange = itemChange + "item" + (i - 1) + "=item" + i + ",item"
					+ (i - 1) + "_result=item" + i + "_result,";
		}
		itemChange = itemChange.substring(0, itemChange.length() - 1);
		sqlList.add("update vote_question set " + itemChange + " where id='"
				+ this.getId() + "'");
		sqlList.add("update vote_question set item_num=item_num-1 where id='"
				+ this.getId() + "'");
		int i = db.executeUpdateBatch(sqlList);
		UserUpdateLog.setDebug("OracleVoteQuestion.deleteQuestionItems(int num) SQL1=" + (String)sqlList.get(0) + " SQL2=" + (String)sqlList.get(1) + " SQL3=" + (String)sqlList.get(0) + " COUNT=" + i + " DATE=" + new Date());

	}

	public void update() throws VoteException {
		dbpool db = new dbpool();
		if (this.getItemTotalNum() < 1)
			throw new VoteException("no question items");
		String itemStr = "";
		for (int i = 0; i < this.getItemTotalNum(); i++) {
			itemStr = itemStr + "item" + (i + 1) + "='"
					+ (String) this.getQuestionItems().get(i) + "',";
		}
		itemStr = itemStr.substring(0, itemStr.length() - 1);
		String sql = "update vote_question set " + "question_body='"
				+ this.getQuestionBody() + "'," + "question_type='"
				+ this.getQuestionType() + "'," + "item_num="
				+ this.getItemTotalNum() + "," + "" + itemStr + " where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleVoteQuestion.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

}
