package com.whaty.platform.database.oracle.standard.interaction;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.interaction.InteractionUserManage;

public class OracleInteractionUserManage implements InteractionUserManage {

	public EntityUser getEntityUser(EntityUser entityUser)
			throws PlatformException {
		return entityUser;
	}

}
