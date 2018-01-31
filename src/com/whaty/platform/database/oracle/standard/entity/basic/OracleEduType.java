/*
 * OracleEduType.java
 *
 * Created on 2005��5��7��, ����10:16
 */

package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

/**
 * 
 * @author Administrator
 */
public class OracleEduType extends EduType {

	/** Creates a new instance of OracleEduType */
	public OracleEduType() {
	}

	public OracleEduType(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,forshort,introduction,recruit_status from entity_edu_type where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setCode(rs.getString("code"));
//				this.setForshort(rs.getString("forshort"));
//				this.setIntroduction(rs.getString("introduction"));
//				this.setRecruit_status(rs.getString("recruit_status"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("BasicDataList.getEduType() error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_edu_type(id,name,forshort,introduction) values('"
				+ this.getId()
				+ "','"
				+ this.getName()
				+ "','"
				+ this.getForshort() + "','" + this.getIntroduction() + "')";
		int i = db.executeUpdate(sql);
		
		UserAddLog.setDebug("OracleEduType.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		
		if (i > 0) {
			sql = "update entity_manager_info set edutype=edutype||'|"
					+ this.getId() + "'";
			int suc = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleEduType.add() SQL=" + sql + " COUNT=" + suc + " DATE=" + new Date());
		}
		
		return i;
	}

	public int delete() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_edu_type where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleEduType.Delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		if (isHasStudents() > 0)
			throw new PlatformException("This edu type has students!");
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_edu_type set name='" + this.getName()
				+ "',forshort='" + this.getForshort() + "',introduction='"
				+ this.getIntroduction() + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleEduType.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isHasStudents() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_user_info where edu_type_id = '"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int isIdExist() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_edu_type where id = '" + this.getId()
				+ "'";
		int i = db.countselect(sql);
		return i;
	}

}
