/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.user;

import java.io.Serializable;

import com.whaty.platform.entity.user.StudentPriv;

/**
 * @author chenjian
 *
 */
public class OracleStudentPriv extends StudentPriv implements Serializable{

	public OracleStudentPriv() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OracleStudentPriv(String id) {
		this.setStudentId(id);
	}
	

}
