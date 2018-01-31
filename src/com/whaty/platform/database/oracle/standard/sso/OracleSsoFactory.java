/**
 * 
 */
package com.whaty.platform.database.oracle.standard.sso;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.sso.SsoFactory;
import com.whaty.platform.sso.SsoManage;
import com.whaty.platform.sso.SsoManagerPriv;
import com.whaty.platform.sso.SsoUserOperation;
import com.whaty.platform.sso.SsoUserPriv;

/**
 * @author chenjian
 *
 */
public class OracleSsoFactory extends SsoFactory {

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.SsoFactory#creatSsoManage()
	 */
	public SsoManage creatSsoManage() {
		return new OracleSsoManage();
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.SsoFactory#creatSsoUserPriv()
	 */
	public SsoUserPriv creatSsoUserPriv(String loginId) throws PlatformException {
		return new OracleSsoUserPriv(loginId);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.SsoFactory#creatSsoManagerPriv()
	 */
	public SsoManagerPriv creatSsoManagerPriv(String managerId) {
		return new OracleSsoManagerPriv(managerId);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.SsoFactory#creatSsoManage(com.whaty.platform.sso.SsoManagerPriv)
	 */
	public SsoManage creatSsoManage(SsoManagerPriv ssoManagerPriv) {
		return new OracleSsoManage(ssoManagerPriv);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.SsoFactory#creatSsoUserOperation(com.whaty.platform.sso.SsoUserPriv)
	 */
	public SsoUserOperation creatSsoUserOperation(SsoUserPriv ssoUserPriv) {
		return new OracleSsoUserOperation(ssoUserPriv);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.SsoFactory#creatSsoUserOperation()
	 */
	public SsoUserOperation creatSsoUserOperation() {
		return new OracleSsoUserOperation();
	}

	public SsoManagerPriv creatSsoManagerPriv() throws PlatformException {
		return new OracleSsoManagerPriv();
	}

}
