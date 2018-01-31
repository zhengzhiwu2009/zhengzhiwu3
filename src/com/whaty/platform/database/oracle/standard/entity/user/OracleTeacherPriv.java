/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.user;

import java.io.Serializable;

import com.whaty.platform.entity.user.TeacherPriv;

/**
 * @author chenjian
 *
 */
public class OracleTeacherPriv extends TeacherPriv implements Serializable{

	public OracleTeacherPriv() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OracleTeacherPriv(String id) {
		this.setTeacherId(id);
		
	}
}
