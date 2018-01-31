package com.whaty.platform.database.oracle.standard.campus;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.CampusInfoFactory;
import com.whaty.platform.campus.CampusInfoManage;
import com.whaty.platform.campus.CampusNormalInfoManage;
import com.whaty.platform.campus.user.CampusInfoManagerPriv;
import com.whaty.platform.database.oracle.standard.campus.user.OracleCampusInfoManagerPriv;

/**
 * @author chenjian
 * 
 */
public class OracleCampusInfoFactory extends CampusInfoFactory {

	public CampusInfoManage creatCampusInfoManage(
			CampusInfoManagerPriv campusInfoManagerPriv)
			throws PlatformException {
		return new OracleCampusInfoManage(campusInfoManagerPriv);
	}

	public CampusNormalInfoManage creatCampusNormalInfoManage()
			throws PlatformException {
		return new OracleCampusNormalInfoManage();
	}

	public CampusInfoManagerPriv getCampusInfoManagerPriv(String managerId)
			throws PlatformException {
		return new OracleCampusInfoManagerPriv(managerId);
	}

}
