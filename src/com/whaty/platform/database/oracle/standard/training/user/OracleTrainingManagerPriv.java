/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.user;

import com.whaty.platform.training.user.TrainingManagerPriv;

/**
 * @author chenjian
 *
 */
public class OracleTrainingManagerPriv extends TrainingManagerPriv {

	/**
	 * @param id
	 */
	public OracleTrainingManagerPriv(String id) {
		this.setManagerId(id);
		this.getCourses=1;
	}

}
