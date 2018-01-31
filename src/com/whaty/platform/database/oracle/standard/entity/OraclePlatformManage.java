/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.user.OracleEntityUser;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManager;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteManager;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacher;
import com.whaty.platform.database.oracle.standard.info.user.OracleInfoManager;
import com.whaty.platform.entity.PlatformManage;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.info.InfoFactory;
import com.whaty.platform.info.NormalInfoManage;
import com.whaty.platform.info.user.InfoManager;
import com.whaty.platform.vote.VoteFactory;
import com.whaty.platform.vote.VoteNormalManage;

/**
 * @author Administrator
 * 
 */
public class OraclePlatformManage extends PlatformManage {

	public OraclePlatformManage() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.PlatformManage#getEnterPage(java.lang.String)
	 */
	public EntityUser getEntityUser(String ssoId) throws PlatformException {
		return new OracleEntityUser(ssoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.PlatformManage#getEnterPage(java.lang.String,
	 *      java.lang.String)
	 */
	public EntityUser getEntityUser(String id, String selectType)
			throws PlatformException {
		EntityUser user = null;
		if (selectType.equalsIgnoreCase("student")) {
			return new OracleStudent(id);

		} else if (selectType.equalsIgnoreCase("teacher")) {
			return new OracleTeacher(id);
		} else if (selectType.equalsIgnoreCase("manager")) {
			return new OracleManager(id);
		} else if (selectType.equalsIgnoreCase("sitemanager")) {
			return new OracleSiteManager(id);
		} else if (selectType.equalsIgnoreCase("siteteacher")) {
			return new OracleSiteTeacher(id);
		}
		return user;

	}

	public EntityUser getEntityUserWithoutType(String id)
			throws PlatformException {
		EntityUser user = null;
		user = new OracleStudent(id);
		if (user!=null&&user.getId()!=null&&!user.getId().equals("")) {
			user.setType("student");
			return user;
		}
		user = new OracleTeacher(id);
		if (user!=null&&user.getId()!=null&&!user.getId().equals("")) {
			user.setType("teacher");
			return user;
		}
		user = new OracleManager(id);
		if (user!=null&&user.getId()!=null&&!user.getId().equals("")) {
			user.setType("manager");
			return user;
		}
		user = new OracleSiteManager(id);
		if (user!=null&&user.getId()!=null&&!user.getId().equals("")) {
			user.setType("sitemanager");
			return user;
		}
		user = new OracleSiteTeacher(id);
		if (user!=null&&user.getId()!=null&&!user.getId().equals("")) {
			user.setType("siteteacher");
			return user;
		}
		return null;
	}

	public NormalInfoManage getNormalInfoManage() throws PlatformException {
		InfoFactory factory = InfoFactory.getInstance();
		return factory.creatNormalInfoManage();
	}

	public InfoManager getInfoManager(String id) throws PlatformException {
		return new OracleInfoManager(id);
	}

	public VoteNormalManage getVoteNormalManage() throws PlatformException {
		VoteFactory factory = VoteFactory.getInstance();
		return factory.creatVoteNormalManage();
	}

}
