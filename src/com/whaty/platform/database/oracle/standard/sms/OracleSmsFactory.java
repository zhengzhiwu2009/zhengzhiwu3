/**
 * 
 */
package com.whaty.platform.database.oracle.standard.sms;

import com.whaty.platform.sms.SmsFactory;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.sms.SmsManagerPriv;

/**
 * @author wq
 * 
 */
public class OracleSmsFactory extends SmsFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sms.SmsFactory#creatSmsManage(com.whaty.platform.sms.SmsManagerPriv)
	 */
	public SmsManage creatSmsManage(SmsManagerPriv amanagerpriv) {
		return new OracleSmsManage(amanagerpriv);
	}
	
	public SmsManage creatSmsManage() {
		return new OracleSmsManage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sms.SmsFactory#getSmsManagerPriv(java.lang.String)
	 */
	public SmsManagerPriv getSmsManagerPriv(String id) {
		return new OracleSmsManagerPriv(id);
	}

	public SmsManagerPriv getSmsManagerPriv() {
		// TODO Auto-generated method stub
		return new OracleSmsManagerPriv();
	}

}
