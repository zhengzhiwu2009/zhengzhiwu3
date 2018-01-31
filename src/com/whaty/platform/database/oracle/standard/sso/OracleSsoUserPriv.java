/**
 * 
 */
package com.whaty.platform.database.oracle.standard.sso;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.sso.SsoUserPriv;
import com.whaty.platform.util.log.SsoLog;

/**
 * @author chenjian
 *
 */
public class OracleSsoUserPriv extends SsoUserPriv {

	public OracleSsoUserPriv() {

	}

	public OracleSsoUserPriv(String loginId) throws PlatformException {

		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = "select u.id,u.login_id,u.password,u.fk_role_id,en.id as typeId from sso_user u ,enum_const en where u.user_type=en.id(+) and u.login_id='"+loginId+"'";
			SsoLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs.next()) {
				this.setId(rs.getString("id"));
				this.setLoginId(rs.getString("login_id"));
//				this.setName(rs.getString("name"));
			}
		} catch (java.sql.SQLException e) {
			SsoLog.setError("OracleSsoUserPriv init error!");
		} finally {
			db.close(rs);
			db = null;
		}

	}

}
