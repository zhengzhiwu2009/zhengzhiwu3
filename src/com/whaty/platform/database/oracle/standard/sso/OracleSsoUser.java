/**
 * 
 */
package com.whaty.platform.database.oracle.standard.sso;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.SubSystemType;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.sso.SsoUser;
import com.whaty.platform.util.log.SsoLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleSsoUser extends SsoUser {

	public OracleSsoUser() {

	}

	public OracleSsoUser(String userId) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = "select * from sso_user where id='" + userId + "'";
			SsoLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setLoginId(rs.getString("login_id"));
				this.setName(rs.getString("name"));
				this.setEmail(rs.getString("email"));
				this.setLoginType(rs.getString("login_type"));
				this.setAddSubSystem(rs.getString("ADD_SUBSYSTEM"));
				this.setPassword(rs.getString("password"));

			}
		} catch (java.sql.SQLException e) {
			SsoLog.setError("OracleSsoUserPriv init error!");
		} finally {
			db.close(rs);
			db = null;
		}

	}

	public SsoUser getSsoLoginUser(String login_id) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = "select * from sso_user where login_id='" + login_id + "'";
			SsoLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setLoginId(rs.getString("login_id"));
				this.setName(rs.getString("name"));
				this.setEmail(rs.getString("email"));
				this.setLoginType(rs.getString("login_type"));
				this.setAddSubSystem(rs.getString("ADD_SUBSYSTEM"));
				this.setPassword(rs.getString("password"));

			}
		} catch (java.sql.SQLException e) {
			SsoLog.setError("OracleSsoUserPriv init error!");
		} finally {
			db.close(rs);
			db = null;
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#add()
	 */
	public int add() throws PlatformException {
		dbpool db = new dbpool();
		if (isExist(this.getLoginId()) > 0) {
			throw new PlatformException("该统一登陆系统用户已经存在！");
		}
		String sql = "insert into sso_user(ID,LOGIN_ID,PASSWORD,NAME,EMAIL,"
				+ "LOGIN_TYPE,ADD_SUBSYSTEM) values(to_char(s_sso_id.nextval),'"
				+ this.getLoginId() + "'," + "'" + this.getPassword() + "','"
				+ this.getName() + "'," + "'" + this.getEmail() + "'," + "'"
				+ this.getLoginType() + "','" + SubSystemType.SSO + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleSsoUser.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#update()
	 */
	public int update() {
		dbpool db = new dbpool();
		String sql = "update sso_user set login_id='" + this.getLoginId()
				+ "'," + "password='" + this.getPassword() + "',name='"
				+ this.getName() + "'," + "email='" + this.getEmail()
				+ "',login_type='" + this.getLoginType() + "'" + " where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSsoUser.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#delete()
	 */
	public int delete() {
		dbpool db = new dbpool();
		String sql = "delete from sso_user  where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleSsoUser.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sso.SsoUser#initByLoginId()
	 */
	public void initByLoginId() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = "select ID,LOGIN_ID,PASSWORD,NAME,"
					+ "NICK_NAME,EMAIL,LOGIN_TYPE,ADD_SUBSYSTEM from sso_user where login_id='"
					+ this.getLoginId() + "'";
//			SsoLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setLoginId(rs.getString("login_id"));
				this.setName(rs.getString("name"));
				this.setEmail(rs.getString("email"));
				this.setLoginType(rs.getString("login_type"));
				this.setAddSubSystem(rs.getString("add_subsystem"));
			}
		} catch (java.sql.SQLException e) {
			SsoLog.setError("OracleSsoUserPriv init error!");
		} finally {
			db.close(rs);
			db = null;
		}

	}

	public static String getSsoUserId() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		String ssoId = null;
		try {
			sql = "select to_char(s_sso_id.nextval) as sso_id from dual";
//			SsoLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				ssoId = rs.getString("sso_id");
			}
		} catch (java.sql.SQLException e) {
			SsoLog.setError("OracleSsoUserPriv init error!");
		} finally {
			db.close(rs);
			db = null;
			return ssoId;
		}

	}

	public static int isExist(String loginId) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select id from sso_user  where login_id='" + loginId
				+ "'";
//		SsoLog.setDebug(sql);
		return db.countselect(sql);
	}
}
