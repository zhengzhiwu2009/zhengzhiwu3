/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.fee.level;

import java.util.HashMap;

import com.whaty.platform.entity.fee.level.ChargeType;

/**
 * @author chenjian
 * 
 */
public class OracleChargeLevel {

	public HashMap chargeTypeField = null;

	public HashMap chargeTypeFieldValue = null;

	public OracleChargeLevel() {
		if (chargeTypeField == null)
			chargeTypeField = new HashMap();
		if (chargeTypeFieldValue == null)
			chargeTypeFieldValue = new HashMap();
		for (int i = 0; i < ChargeType.types().size(); i++) {
			this.chargeTypeField.put(ChargeType.types().get(i), "type"
					+ (i + 1) + "_name");
			this.chargeTypeFieldValue.put(ChargeType.types().get(i), "type"
					+ (i + 1) + "_value");
		}
	}

	/**
	 * @param key
	 * @return
	 */
	public boolean InChargeLevel(String key) {
		if (ChargeType.types().contains(key))
			return true;
		else
			return false;
	}

}
