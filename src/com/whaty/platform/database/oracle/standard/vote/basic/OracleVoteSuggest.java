package com.whaty.platform.database.oracle.standard.vote.basic;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.platform.util.log.VoteLog;
import com.whaty.platform.vote.basic.VoteSuggest;

public class OracleVoteSuggest extends VoteSuggest {

	
	public OracleVoteSuggest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OracleVoteSuggest(String suggestId) {
		dbpool db=new dbpool();
		MyResultSet rs=null;
		try {
			String sql="select id,paper_id,note,found_date,ip,status from vote_suggest " +
			"where id='"+suggestId+"'";
			VoteLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setNote(rs.getString("note"));
				this.setVotePaperId(rs.getString("paper_id"));
				this.setFound_date(rs.getDate("found_date"));
				this.setIp(rs.getString("ip"));
				if(rs.getInt("status")==1)
					this.setActive(true);
				else
					this.setActive(false);
			}
		} catch (SQLException e) {
			VoteLog.setError(e.getMessage());
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		int status = 0;
		if (this.isActive())
			status = 1;
		String sql;
		sql = "insert into vote_suggest(id,paper_id,note,ip,status,found_date) values("
					+ "to_char(s_vote_id.nextval),'"+ this.getVotePaperId()+"','"+this.getNote()+ "'," +
					"'"+this.getIp()+"',"+status+",sysdate)";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleVoteSuggest.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
		
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql;
		sql = "delete from  vote_suggest  where id='"+this.getId()+"'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleVoteSuggest.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		int status = 0;
		if (this.isActive())
			status = 1;
		String sql;
		sql = "update vote_suggest set note='"+this.getNote()+"',status="+status +" where id='"+this.getId()+"'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleVoteSuggest.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
