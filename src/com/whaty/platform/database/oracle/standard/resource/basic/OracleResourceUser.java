/**
 * 
 */
package com.whaty.platform.database.oracle.standard.resource.basic;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.resource.basic.ResourceUser;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author wq
 * 
 */
public class OracleResourceUser extends ResourceUser {

	public OracleResourceUser() {

	}

	public OracleResourceUser(String id) {
		String sql = "select id,login_id,password,name where id='" + id + "'";
		MyResultSet rs = null;
		dbpool db = new dbpool();
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setLoginId(rs.getString("login_id"));
				this.setPassword(rs.getString("password"));
				this.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}

	public int add() {
		String sql = "insert into resource_user(id,login_id,password,name) values (to_char(s_resource_user_id.nextval),'"
				+ this.getLoginId()
				+ "','"
				+ this.getPassword()
				+ "','"
				+ this.getName() + "')";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleResourceUser.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() {
		String sql = "delete from resource_user where id='"
				+ this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleResourceUser.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		String sql = "update resource_user set login_id='"
				+ this.getLoginId() + "', password='" + this.getPassword()
				+ "', name='" + this.getName() + "' where id='" + this.getId()
				+ "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleResourceUser.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
