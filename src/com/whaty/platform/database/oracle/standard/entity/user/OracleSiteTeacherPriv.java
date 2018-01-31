/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.user;

import java.sql.SQLException;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.user.SiteTeacherPriv;
import com.whaty.platform.util.log.EntityLog;

/**
 * @author chenjian
 * 
 */
public class OracleSiteTeacherPriv extends SiteTeacherPriv {

	public OracleSiteTeacherPriv() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleSiteTeacherPriv(String id) throws SQLException {
		this.setTeacherId(id);
		dbpool db = new dbpool();
		String sql = "select site_id from entity_siteteacher_info where id = '"
				+ id + "'";
		MyResultSet rs = null;
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setSiteId(rs.getString("site_id"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setDebug("OracleSiteTeacherPriv(String id):" + sql);
			throw e;
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public OracleSiteTeacherPriv(String id, String siteId) throws SQLException {
		this.setTeacherId(id);
		this.setSiteId(siteId);
	}
}
