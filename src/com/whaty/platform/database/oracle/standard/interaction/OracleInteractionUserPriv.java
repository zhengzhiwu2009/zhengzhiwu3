package com.whaty.platform.database.oracle.standard.interaction;

import java.io.Serializable;

import com.whaty.platform.interaction.InteractionUserPriv;

public class OracleInteractionUserPriv extends InteractionUserPriv implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OracleInteractionUserPriv() {
		super();
	}

	public OracleInteractionUserPriv(String id) {
		this.setUserId(id);
	}

}
