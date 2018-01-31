/**
 * 
 */
package com.whaty.platform.database.oracle.standard.sms;

import com.whaty.platform.sms.SmsManagerPriv;

/**
 * @author Admin
 *
 */
public class OracleSmsManagerPriv extends SmsManagerPriv {

	/**
	 * 
	 */
	public OracleSmsManagerPriv() {
		super();
	}
	
	public OracleSmsManagerPriv(String managerId) {
		this.setManagerId(managerId);
	}

}
