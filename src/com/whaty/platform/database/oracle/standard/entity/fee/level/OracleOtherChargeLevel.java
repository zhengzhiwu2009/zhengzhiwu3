/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.fee.level;

import java.util.HashMap;

import com.whaty.platform.entity.fee.level.OtherChargeType;

/**
 * @author chenjian
 * 
 */
public class OracleOtherChargeLevel {

	public HashMap chargeTypeField = null;

	public HashMap chargeTypeFieldValue = null;

	public OracleOtherChargeLevel() {
		if (chargeTypeField == null)
			chargeTypeField = new HashMap();
		if (chargeTypeFieldValue == null)
			chargeTypeFieldValue = new HashMap();
		for (int i = 0; i < OtherChargeType.types().size(); i++) {
			this.chargeTypeField.put(OtherChargeType.types().get(i), "type"
					+ (i + 3) + "_name");
			this.chargeTypeFieldValue.put(OtherChargeType.types().get(i),
					"type" + (i + 3) + "_value");
		}
	}

	/**
	 * @param key
	 * @return
	 */
	public boolean InChargeLevel(String key) {
		if (OtherChargeType.types().contains(key))
			return true;
		else
			return false;
	}

}
