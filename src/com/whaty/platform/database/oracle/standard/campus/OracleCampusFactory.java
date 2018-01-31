package com.whaty.platform.database.oracle.standard.campus;

import com.whaty.platform.database.oracle.standard.campus.user.OracleCampusManagerPriv;
import com.whaty.platform.campus.CampusFactory;
import com.whaty.platform.campus.CampusManage;
import com.whaty.platform.campus.CampusNormalManage;
import com.whaty.platform.campus.user.CampusManagerPriv;

public class OracleCampusFactory extends CampusFactory {

	public CampusManage creatCampusManage(CampusManagerPriv amanagerpriv) {
		if (amanagerpriv != null)
			return new OracleCampusManage(amanagerpriv);
		else
			return new OracleCampusManage(new OracleCampusManagerPriv(""));
	}

	public CampusNormalManage creatCampusNormalManage(CampusManagerPriv amanagerpriv) {
		return new OracleCampusNormalManage(amanagerpriv);
	}

	public CampusNormalManage creatCampusNormalManage() {
		return new OracleCampusNormalManage(new OracleCampusManagerPriv(
		""));
	}

	public CampusManagerPriv getCampusManagerPriv(String id) {
		return new OracleCampusManagerPriv(id);
	}

	@Override
	public CampusManagerPriv getNormalPriv() {
		CampusManagerPriv priv = new OracleCampusManagerPriv();
		return priv.getNormalPriv();
	}

	@Override
	public CampusManagerPriv getManagerPriv() {
		CampusManagerPriv priv = new OracleCampusManagerPriv();
		return priv.getManagerPriv();
	}
}
