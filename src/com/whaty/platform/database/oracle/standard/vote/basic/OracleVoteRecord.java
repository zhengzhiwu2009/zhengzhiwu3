package com.whaty.platform.database.oracle.standard.vote.basic;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.VoteLog;
import com.whaty.platform.vote.basic.VoteRecord;
import com.whaty.platform.vote.util.exception.VoteException;

public class OracleVoteRecord extends VoteRecord {
	public OracleVoteRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleVoteRecord(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			String sql = "select ID,PAPER_ID,IP,SESSION_ID,VOTE_DATE "
					+ " from vote_record where id='" + id + "'";
			VoteLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setIp(rs.getString("ip"));
				this.setSessionId(rs.getString("session_id"));
				this.setPaperId(rs.getString("paper_id"));
				this.setVoteDate(rs.getDate("vote_date"));
			}
		} catch (SQLException e) {
			VoteLog.setError(e.getMessage());
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public void add() throws VoteException {
		dbpool db = new dbpool();
		String sql;
		sql = "insert into vote_record(ID,PAPER_ID,IP,SESSION_ID) values("
				+ "to_char(s_vote_record_id.nextval),'" + this.getPaperId()
				+ "','" + this.getIp() + "','" + this.getSessionId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleVoteRecord.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i < 1)
			throw new VoteException("insert into vote_record error!(sql=" + sql
					+ ")");

	}

	public void delete() throws VoteException {
		dbpool db = new dbpool();
		String sql;
		sql = "delete from vote_record where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleVoteRecord.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if ( i < 1)
			throw new VoteException("delete from  vote_record error!(sql="
					+ sql + ")");

	}

}
