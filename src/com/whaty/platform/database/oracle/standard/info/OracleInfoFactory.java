/**
 * 
 */
package com.whaty.platform.database.oracle.standard.info;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.info.user.OracleInfoManagerPriv;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.info.InfoFactory;
import com.whaty.platform.info.InfoManage;
import com.whaty.platform.info.NormalInfoManage;
import com.whaty.platform.info.user.InfoManagerPriv;

/**
 * @author chenjian
 * 
 */
public class OracleInfoFactory extends InfoFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoFactory#creatInfoManage(com.whaty.platform.info.InfoManagerPriv,
	 *      java.lang.String)
	 */
	public InfoManage creatInfoManage(InfoManagerPriv managerpriv,
			String newsTypeId) throws PlatformException {
		return new OracleInfoManage(managerpriv, newsTypeId);
	}

	public InfoManagerPriv getInfoManagerPriv(String managerId)
			throws PlatformException {
		return new OracleInfoManagerPriv(managerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoFactory#getInfoManagerPriv(java.lang.String,
	 *      java.lang.String)
	 */
	public InfoManagerPriv getInfoManagerPriv(String managerId,
			String newsTypeId) throws PlatformException {
		return new OracleInfoManagerPriv(managerId, newsTypeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.info.InfoFactory#creatNormalInfoManage()
	 */
	public NormalInfoManage creatNormalInfoManage() throws PlatformException {
		return new OracleNormalInfoManage("root");
	}

	public InfoManage creatInfoManage(InfoManagerPriv managerpriv)
			throws PlatformException {
		return new OracleInfoManage(managerpriv);
	}
	
	
	/**
	 * 通过平台管理权限构建信息管理权限
	 */
	public InfoManagerPriv getInfoManagerPriv(ManagerPriv priv) throws PlatformException{
		return new OracleInfoManagerPriv(priv);
	}
	
	/**
	 * submanager
	 */
	public InfoManagerPriv getInfoManagerPriv(SiteManagerPriv priv) throws PlatformException{
		return new OracleInfoManagerPriv(priv);
	}

}
