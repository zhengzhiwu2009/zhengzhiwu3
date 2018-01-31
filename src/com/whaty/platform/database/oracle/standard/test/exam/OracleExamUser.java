package com.whaty.platform.database.oracle.standard.test.exam;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.test.OracleTestUser;
import com.whaty.platform.test.exam.ExamUser;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleExamUser extends ExamUser {
	public OracleExamUser() {
	}

	public OracleExamUser(String aid) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select batch_id,user_id,examcode,note,status from test_examuser_batch where user_id='"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(aid);
				this
						.setExamBatch(new OracleExamBatch(rs
								.getString("batch_id")));
				this.setTestUser(new OracleTestUser(rs.getString("user_id")));
				this.setExamcode(rs.getString("examcode"));
				this.setNote(rs.getString("note"));
				this.setStatus(rs.getString("status"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleExamUser(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		int i = 1;
		if (IsExist() <= 0) {
		sql = "insert into test_examuser_batch (id,batch_id,user_id,examcode,note,status) values (to_char(s_test_examuser_batch_id.nextval),'"
				+ this.getExamBatch().getId()
				+ "','"
				+ this.getTestUser().getId()
				+ "','"
				+ this.getExamcode()
				+ "','" + this.getNote() + "','" + this.getStatus() + "')";
		i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleExamUser.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update test_examuser_batch set batch_id='"
				+ this.getExamBatch().getId() + "',user_id='"
				+ this.getTestUser().getId() + "'," + "examcode='"
				+ this.getExamcode() + "'," + "note='" + this.getNote() + "',"
				+ "status='" + this.getStatus() + "'" + " where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExamUser.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from test_examuser_batch where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleExamUser.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	public int IsExist() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select *  from test_examuser_batch where batch_id='"
				+ this.getExamBatch().getId() + "' and user_id='"+this.getTestUser().getId()+"'";
		int i = db.countselect(sql);
		return i;
	}
	
	
}
