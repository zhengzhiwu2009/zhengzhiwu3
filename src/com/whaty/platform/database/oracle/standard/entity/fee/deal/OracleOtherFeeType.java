package com.whaty.platform.database.oracle.standard.entity.fee.deal;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.fee.deal.OtherFeeType;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleOtherFeeType extends OtherFeeType {

	public OracleOtherFeeType() {
	}

	public OracleOtherFeeType(String aid) {
		dbpool dbDep = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select id,name from entity_otherfee_type where id='" + aid
					+ "'";
			rs = dbDep.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(aid);
				this.setName(rs.getString("name"));
			}
		} catch (java.sql.SQLException e) {

		} finally {
			dbDep.close(rs);
			dbDep = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		if (isIdExist() > 0) {
			throw new PlatformException("该编号已经存在!");
		}
		sql = "insert into entity_otherfee_type (id,name) values('"
				+ this.getId() + "','" + this.getName() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleOtherFeeType.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_otherfee_type where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleOtherFeeType.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_otherfee_type set name='" + this.getName()
				+ "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleOtherFeeType.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isIdExist() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_otherfee_type where id='" + this.getId()
				+ "'";
		int i = db.countselect(sql);
		return i;
	}

}
