/**
 * 
 */
package com.whaty.platform.sms.imp;

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

	
	public SmsManage creatSmsManage() {
		return new OracleSmsManage();
	}

	@Override
	public SmsManage creatSmsManage(SmsManagerPriv amanagerpriv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsManagerPriv getSmsManagerPriv(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsManagerPriv getSmsManagerPriv() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sms.SmsFactory#getSmsManagerPriv(java.lang.String)
	 */

}
