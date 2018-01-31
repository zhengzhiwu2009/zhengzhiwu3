package com.whaty.platform.database.oracle.standard.campus.user;


import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.user.CampusInfoManagerPriv;
import com.whaty.platform.database.oracle.standard.info.user.OracleInfoManagerPriv;

/**
 * @author chenjian
 * 
 */
public class OracleCampusInfoManagerPriv extends CampusInfoManagerPriv {

	public OracleCampusInfoManagerPriv() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleCampusInfoManagerPriv(String managerId, String newsTypeId) {
		this.setManagerId(managerId);
		this.setNewsTypeId(newsTypeId);

	}

	public OracleCampusInfoManagerPriv(String managerId) {
		this.setManagerId(managerId);
		this.addNews=1;
		this.confirmNews=1;
		this.copyNews=1;
		this.deleteNews=1;
		this.getNews=1;
		this.getNewsType=1;
		this.lockNews=1;
		this.topNews=1;
		this.updateNews=1;
		this.topNews=1;
		this.popNews=1;
	}

	public int putInfomanagerPriv(String userId, String siteId)
			throws PlatformException {
		 return 0;
	}

	public static void main(String[] args) throws Exception {
		OracleInfoManagerPriv a = new OracleInfoManagerPriv();
		a.putInfomanagerPriv("delete", "1983");
	}
	 
 
}
