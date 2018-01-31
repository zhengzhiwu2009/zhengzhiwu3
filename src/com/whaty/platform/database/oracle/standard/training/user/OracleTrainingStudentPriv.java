/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.user;

import java.io.Serializable;

import com.whaty.platform.training.user.TrainingStudentPriv;

/**
 * @author chenjian
 *
 */
public class OracleTrainingStudentPriv extends TrainingStudentPriv implements Serializable{

	public OracleTrainingStudentPriv() {
		
	}
	public OracleTrainingStudentPriv(String id) {
		this.setStudentId(id);
	
	}
}
