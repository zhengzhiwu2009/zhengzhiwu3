/**
 * 
 */
package com.whaty.platform.database.oracle.standard.sso;

import com.whaty.platform.Authorization;
import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public class OracleSsoManagerAuthorization extends Authorization {

	/* (non-Javadoc)
	 * @see com.whaty.platform.sso.Authorization#login(java.lang.String)
	 */
	public boolean login(String inputPwd) throws PlatformException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.Authorization#enCrypt(java.lang.String, java.lang.String)
	 */
	protected String enCrypt(String pwd, String cryptType) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
