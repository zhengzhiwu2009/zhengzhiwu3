/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity;

import com.whaty.platform.entity.EntityTestPriv;

/**
 * @author whaty
 *
 */
public class OracleEntityTestPriv extends EntityTestPriv {
	public OracleEntityTestPriv() {
		
	}
	
	public OracleEntityTestPriv(String ssoId) {
		this.setSsoId(ssoId);
	}
}
