package com.whaty.platform.database.oracle.standard.test;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.test.TestUser;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleTestUser extends TestUser {

	public OracleTestUser() {
	}

	public OracleTestUser(String aid) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select name,email,ftpaccount,login_id,login_type,password from test_user_info where id='"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(aid);
				this.setName(rs.getString("name"));
				this.setEmail(rs.getString("email"));
				// this.setFtpaccount(rs.getString("ftpaccount"));
				this.setLoginId(rs.getString("login_id"));
				this.setLoginType(rs.getString("login_type"));
				this.setPassword(rs.getString("password"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleTestUser(String aid) error" + sql);
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
			sql = "insert into test_user_info (id,name,email,ftpaccount,login_id,login_type,password) values ('"
					+ this.getId()
					+ "','"
					+ this.getName()
					+ "','"
					+ this.getEmail()
					+ "','"
					+ this.getFtpaccount()
					+ "','"
					+ this.getLoginId()
					+ "','"
					+ this.getLoginType()
					+ "','"
					+ this.getPassword() + "')";
			i = db.executeUpdate(sql);
			UserAddLog.setDebug("OracleTestUser.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update test_user_info set name='" + this.getName() + "',email='"
				+ this.getEmail() + "'," + "ftpaccount='"
				+ this.getFtpaccount() + "'," + "login_id='"
				+ this.getLoginId() + "'," + "login_type='"
				+ this.getLoginType() + "'," + "password='"
				+ this.getPassword() + "'" + " where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTestUser.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from test_user_info where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTestUser.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int IsExist() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select *  from test_user_info where login_id='"
				+ this.getLoginId() + "'";
		int i = db.countselect(sql);
		return i;
	}
}
