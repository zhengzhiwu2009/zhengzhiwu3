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
import com.whaty.platform.vote.basic.VotePaperGroup;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

public class OracleVotePaperGroup extends VotePaperGroup {
	
	public OracleVotePaperGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleVotePaperGroup(String groupId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			String sql = "select ID,NAME,ACTIVE,NOTE from vote_paper_group where id='" + groupId + "'";
			VoteLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setNote(rs.getString("note"));
				if (rs.getInt("active") == 1)
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
		StrManage strManage = StrManageFactory.creat();
		String sql;
		try {
			sql = "insert into vote_paper_group (ID,NAME,ACTIVE,NOTE) values("
					+ "to_char(s_vote_group_id.nextval),'"
					+ this.getName()
					+ "',"
					+ status
					+ ",'"
					+ this.getNote()
					+ "')";
			int i = db.executeUpdate(sql);
			UserAddLog.setDebug("OracleVotePaperGroup.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			return i;
		} catch (Exception e) {
			VoteLog.setError("date error!");
			throw new PlatformException("insert into vote_paper error!");
		}

	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from vote_paper_group where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleVotePaperGroup.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		int status = 0;
				if (this.isActive())
			status = 1;
		StrManage strManage = StrManageFactory.creat();
		String sql;
		try {
			sql = "update  vote_paper_group set " + "NAME='" + this.getName()
					+ "',ACTIVE=" + status + ", NOTE='" + this.getNote()+"' where id='"
					+ this.getId() + "'";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleVotePaperGroup.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			return i;
		} catch (Exception e) {
			VoteLog.setError("date error!");
			throw new PlatformException("update vote_paper error!");
		}
	}

}
