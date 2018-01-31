/**
 * 
 */
package com.whaty.platform.entity.user;

import java.io.Serializable;

import com.whaty.platform.User;

/**
 * @author chenjian
 *
 */
public  class EntityUser extends User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3901447325059576647L;
	private String nickname;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	

}
