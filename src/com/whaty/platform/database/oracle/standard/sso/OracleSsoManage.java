/**
 * 
 */
package com.whaty.platform.database.oracle.standard.sso;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.sso.SsoManage;
import com.whaty.platform.sso.SsoManagerPriv;
import com.whaty.platform.sso.SsoUser;
import com.whaty.platform.util.log.SsoLog;
import com.whaty.platform.util.log.UserLog;

/**
 * @author chenjian
 *
 */
public class OracleSsoManage extends SsoManage {

	private SsoManagerPriv ssoManagerPriv;

	public OracleSsoManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleSsoManage(SsoManagerPriv ssoManagerPriv) {
		this.ssoManagerPriv = ssoManagerPriv;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.SsoManage#getSsoUser(java.lang.String)
	 */
	public SsoUser getSsoUser(String id) throws PlatformException {
		if (this.ssoManagerPriv.getUser != 1) {
			throw new PlatformException("no right to getSsoUser");
		}
		{
			return new OracleSsoUser(id);
		}
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.SsoManage#addSsoUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addSsoUser(String loginId, String password, String name,
			String nickName, String email) throws PlatformException {
		if (this.ssoManagerPriv.addUser != 1) {
			throw new PlatformException("no right to addSsoUser");
		}
		{
			OracleSsoUser ssoUser = new OracleSsoUser();
			ssoUser.setLoginId(loginId);
			ssoUser.setPassword(password);
			ssoUser.setName(name);
			ssoUser.setEmail(email);
			int suc = ssoUser.add() ;
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.ssoManagerPriv.getLoginId()
							+ "</whaty><whaty>BEHAVIOR$|$addSsoUser</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			if (suc> 0) {
				SsoLog.setDebug("addSsoUser success!" + loginId);
			} else {
				throw new PlatformException("addSsoUser error!");
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.SsoManage#updateSsoUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateSsoUser(String id, String loginId, String password,
			String name, String nickName, String email)
			throws PlatformException {
		if (this.ssoManagerPriv.updateUser != 1) {
			throw new PlatformException("no right to updateSsoUser");
		}
		{
			OracleSsoUser ssoUser = new OracleSsoUser(id);
			ssoUser.setLoginId(loginId);
			ssoUser.setPassword(password);
			ssoUser.setName(name);
			ssoUser.setEmail(email);
			int suc = ssoUser.update();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.ssoManagerPriv.getLoginId()
							+ "</whaty><whaty>BEHAVIOR$|$updateSsoUser</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			if (suc > 0) {
				SsoLog.setDebug("updateSsoUser success!" + id);
			} else {
				throw new PlatformException("updateSsoUser error!");
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.SsoManage#deleteSsoUser(java.lang.String)
	 */
	public void deleteSsoUser(String id) throws PlatformException {
		if (this.ssoManagerPriv.deleteUser != 1) {
			throw new PlatformException("no right to deleteSsoUser");
		}
		{
			OracleSsoUser ssoUser = new OracleSsoUser(id);
			int suc = ssoUser.delete();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.ssoManagerPriv.getLoginId()
							+ "</whaty><whaty>BEHAVIOR$|$deleteSsoUser</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			if (suc > 0) {
				SsoLog.setDebug("deleteSsoUser success!" + id);
			} else {
				throw new PlatformException("deleteSsoUser error!");
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.SsoManage#updateUserPwd(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateUserPwd(String id, String oldPwd, String newPwd)
			throws PlatformException {
		if (this.ssoManagerPriv.updateUser != 1) {
			throw new PlatformException("no right to updateUserPwd");
		}
		{
			OracleSsoUser ssoUser = new OracleSsoUser(id);
			if (oldPwd.equals(ssoUser.getPassword())) {
				ssoUser.setPassword(newPwd);
				int suc = ssoUser.update();
				UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.ssoManagerPriv.getLoginId()
								+ "</whaty><whaty>BEHAVIOR$|$updateUserPwd</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
				if (suc > 0) {
					SsoLog.setDebug("updateUserPwd success!" + id);
				} else {
					throw new PlatformException("updateUserPwd error!");
				}
			} else {
				throw new PlatformException(
						"updateUserPwd error because false oldpassword!");
			}
		}

	}

	public SsoUser getSsoUserLogin(String loginId) throws PlatformException {
		if (this.ssoManagerPriv.getUser != 1) {
			throw new PlatformException("no right to getSsoUser");
		} else {
			OracleSsoUser ssoUser = new OracleSsoUser();

			return ssoUser.getSsoLoginUser(loginId);
		}

	}

	public SsoUser getSsoUserByLogin(String loginId) throws PlatformException {
		OracleSsoUser ssoUser = new OracleSsoUser();
		if (ssoUser.isExist(loginId) > 0) {
			return ssoUser.getSsoLoginUser(loginId);
		} else {
			return null;
		}
	}

}
