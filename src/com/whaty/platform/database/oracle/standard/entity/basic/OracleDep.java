/*
 * OracleDep.java
 *
 * Created on 2005年5月5日, 上午10:19
 */

package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.Dep;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * 
 * @author Administrator
 */
public class OracleDep extends Dep {

	/** Creates a new instance of OracleDep */
	public OracleDep() {
	}

	public OracleDep(String aid) {
		dbpool dbDep = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select name,forshort,note from entity_dep_info where id='"
					+ aid + "'";
			rs = dbDep.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(aid);
				this.setName(rs.getString("name"));
				this.setForshort(rs.getString("forshort"));
				this.setNote(rs.getString("note"));
			}
		} catch (java.sql.SQLException e) {

		} finally {
			dbDep.close(rs);
			dbDep = null;
		}
	}

	public int add() throws PlatformException {
		dbpool dbDep = new dbpool();
		String sql = "";
		if(isIdExist()>0)
		{
			throw new PlatformException("该编号已经存在!");
		}
		sql = "insert into entity_dep_info(id,name,forshort,note) values('"
				+ this.getId() + "','" + this.getName() + "','"
				+ this.getForshort() + "','" + this.getNote() + "')";
		int i = dbDep.executeUpdate(sql);
		UserAddLog.setDebug("OracleDep.add() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		if (isHasMajor() > 0)
			throw new PlatformException("The dep has majors");
		dbpool dbDep = new dbpool();
		String sql = "";
		sql = "delete from entity_dep_info where id='" + this.getId() + "'";
		int i = dbDep.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleDep.delete() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool dbDep = new dbpool();
		String sql = "";
		sql = "update entity_dep_info set name='" + this.getName()
				+ "',forshort='" + this.getForshort() + "',note='"
				+ this.getNote() + "' where id='" + this.getId() + "'";
		int i = dbDep.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDep.update() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

	public int isIdExist() {
		dbpool dbDep = new dbpool();
		String sql = "";
		sql = "select id from entity_dep_info where id='" + this.getId() + "'";
		int i = dbDep.countselect(sql);
		return i;
	}

	public int isHasMajor() {
		dbpool dbDep = new dbpool();
		String sql = "";
		sql = "select id from entity_major_info where dep_id='" + this.getId()
				+ "'";
		int i = dbDep.countselect(sql);
		return i;
	}
}
