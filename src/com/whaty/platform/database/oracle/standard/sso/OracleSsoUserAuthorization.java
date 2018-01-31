/**
 * 
 */
package com.whaty.platform.database.oracle.standard.sso;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.sso.SsoUserAuthorization;
import com.whaty.platform.util.log.SsoLog;

/**
 * @author chenjian
 *
 */
public class OracleSsoUserAuthorization extends SsoUserAuthorization {

	public OracleSsoUserAuthorization(String loginId) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select password from  sso_user where LOGIN_ID='" + loginId
					+ "'";
			SsoLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setLoginId(loginId);
				this.setRightPwd(rs.getString("password"));
			} else {
				throw new PlatformException("no this user!");
			}
		} catch (java.sql.SQLException e) {
			throw new PlatformException("search password from db error!");
		} finally {
			db.close(rs);
			db = null;
		}
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.Authorization#login(java.lang.String)
	 */
	public boolean login(String inputPwd) throws PlatformException {
		if (this.getRightPwd() != null
				&& this.getRightPwd().toLowerCase().equals(
						enCrypt(inputPwd, null)))
			return true;
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.Authorization#enCrypt(java.lang.String, java.lang.String)
	 */
	protected String enCrypt(String pwd, String cryptType)
			throws PlatformException {
		// TODO Auto-generated method stub
		return pwd;
	}

}
