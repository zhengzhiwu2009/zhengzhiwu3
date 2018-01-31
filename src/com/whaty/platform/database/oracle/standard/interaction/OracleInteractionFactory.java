package com.whaty.platform.database.oracle.standard.interaction;

import com.whaty.platform.interaction.InteractionFactory;
import com.whaty.platform.interaction.InteractionManage;
import com.whaty.platform.interaction.InteractionUserPriv;

public class OracleInteractionFactory extends InteractionFactory {

	public InteractionUserPriv getInteractionUserPriv(String id) {
		return new OracleInteractionUserPriv(id);
	}

	public InteractionManage creatInteractionManage(InteractionUserPriv interactionUserPriv) {
		return new OracleInteractionManage(interactionUserPriv);
	}

}
