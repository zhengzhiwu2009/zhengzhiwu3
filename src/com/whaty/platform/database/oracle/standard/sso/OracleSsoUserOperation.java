/**
 * 
 */
package com.whaty.platform.database.oracle.standard.sso;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.sso.SsoUserOperation;
import com.whaty.platform.sso.SsoUserPriv;
import com.whaty.platform.util.log.SsoLog;
import com.whaty.platform.util.log.UserLog;

/**
 * @author chenjian
 * 
 */
public class OracleSsoUserOperation extends SsoUserOperation {

	private SsoUserPriv ssoUserPriv;

	public OracleSsoUserOperation() {

	}

	public OracleSsoUserOperation(SsoUserPriv ssoUserPriv) {
		this.ssoUserPriv = ssoUserPriv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sso.SsoUserOperation#login(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */

	public boolean login(String loginId, String inputPassword)
			throws PlatformException {
		if (this.ssoUserPriv.login != 1) {
			throw new PlatformException("no right to login!");
		} else {
			OracleSsoUserAuthorization authorization = new OracleSsoUserAuthorization(
					loginId);
			boolean flag = authorization.login(inputPassword);

			UserLog.setInfo("<whaty>USERID$|$" + this.ssoUserPriv.getId()
					+ "</whaty><whaty>BEHAVIOR$|$login</whaty><whaty>STATUS$|$"
					+ flag + "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
					+ LogType.USER + "</whaty><whaty>PRIORITY$|$"
					+ LogPriority.INFO + "</whaty>", new Date());

			return flag;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sso.SsoUserOperation#updatePwd(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void updatePwd(String id, String oldPassword, String newPassword)
			throws PlatformException {
		if (this.ssoUserPriv.updatePwd != 1) {
			throw new PlatformException("no right to updatePwd!");
		} else {
			OracleSsoUser ssoUser = new OracleSsoUser(id);
			if (oldPassword.equals(ssoUser.getPassword())) {
				ssoUser.setPassword(newPassword);
				if (ssoUser.update() > 0) {
					SsoLog.setDebug("updatePwd success!" + id);
				} else {
					throw new PlatformException("updatePwd error!");
				}
			} else {
				throw new PlatformException(
						"updatePwd error because false oldpassword!");
			}
		}

	}

}
