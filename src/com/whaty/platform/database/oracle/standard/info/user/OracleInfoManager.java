/**
 * 
 */
package com.whaty.platform.database.oracle.standard.info.user;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.info.user.InfoManager;
import com.whaty.platform.util.log.InfoLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;

/**
 * @author chenjian
 * 
 */
public class OracleInfoManager extends InfoManager {

	public OracleInfoManager() {

	}

	public OracleInfoManager(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,login_id, name ,status from info_manager_info where id = '"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setLoginId(rs.getString("login_id"));
				this.setStatus(rs.getString("status"));
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("OracleInfoManager(String id) error:" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		if (isIdExist() != 0)
			throw new PlatformException("InfoManager id has exist");
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into info_manager_info(id,name,login_id,status) values('"
				+ this.getId() + "','" + this.getName() + "','"
				+ this.getLoginId() + "','" + this.getStatus() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleInfoManager.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		int i = 0;
		sql = "delete from info_manager_info where login_id = '"
				+ this.getLoginId() + "'";
		InfoLog.setDebug("OracleInfoManage@Method:delete()/sql: " + sql);
		i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleInfoManager.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		return 0;
	}

	public int isIdExist() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from info_manager_info where id = '" + this.getId()
				+ "'";
		int i = db.countselect(sql);
		return i;
	}
}
