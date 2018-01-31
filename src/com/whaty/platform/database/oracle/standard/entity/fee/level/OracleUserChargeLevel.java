/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.fee.level;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.fee.level.ChargeType;
import com.whaty.platform.entity.fee.level.UserChargeLevel;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleUserChargeLevel extends UserChargeLevel {

	private OracleChargeLevel oracleChargeLevel;

	public OracleUserChargeLevel() {
		super();
		oracleChargeLevel = new OracleChargeLevel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.level.UserChargeLevel#setChargeLevel(java.util.Map)
	 */
	public void setChargeLevel(Map chargeLevel) {
		Iterator iterator = chargeLevel.entrySet().iterator();
		List keyList = new ArrayList();
		List valueList = new ArrayList();

		for (int i = 0; i < chargeLevel.size(); i++) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if (oracleChargeLevel.InChargeLevel(key)) {
				keyList.add(key);
				valueList.add(value);
			}
		}
		if (keyList.size() < 1)
			return;

		dbpool db = new dbpool();
		String sql = "";
		// 该用户是否存在
		if (db.countselect("select user_id from entity_userfee_level where user_id='"
						+ this.getUser().getId() + "'") < 1) {

			String fields = "";
			String values = "";
			String keyTmp = null;
			String valueTmp = null;
			for (int i = 0; i < keyList.size(); i++) {
				keyTmp = (String) keyList.get(i);
				valueTmp = (String) valueList.get(i);
				fields = fields
						+ ((String) oracleChargeLevel.chargeTypeField
								.get(keyTmp))
						+ ","
						+ ((String) oracleChargeLevel.chargeTypeFieldValue
								.get(keyTmp)) + ",";
				values = values + "'" + keyTmp + "','" + valueTmp + "',";

			}
			if (fields.length() > 0)
				fields = fields.substring(0, fields.length() - 1);
			if (values.length() > 0)
				values = values.substring(0, values.length() - 1);
			sql = "insert into entity_userfee_level(user_id," + fields + ") "
					+ "values('" + this.getUser().getId() + "'," + values + ")";
			UserAddLog.setDebug("OracleUserChargeLevel.setChargeLevel(Map chargeLevel) SQL=" + sql + " DATE=" + new Date());
			
		} else {
			String fields = "";
			String keyTmp = null;
			String valueTmp = null;
			for (int i = 0; i < keyList.size(); i++) {
				keyTmp = (String) keyList.get(i);
				valueTmp = (String) valueList.get(i);
				fields = fields
						+ ((String) oracleChargeLevel.chargeTypeField
								.get(keyTmp))
						+ "='"
						+ keyTmp
						+ "',"
						+ ((String) oracleChargeLevel.chargeTypeFieldValue
								.get(keyTmp)) + "='" + valueTmp + "',";

			}
			if (fields.length() > 0)
				fields = fields.substring(0, fields.length() - 1);
			sql = "update entity_userfee_level set " + fields
					+ " where user_id='" + this.getUser().getId() + "'";
			UserUpdateLog.setDebug("OracleUserChargeLevel.setChargeLevel(Map chargeLevel) SQL=" + sql + " DATE=" + new Date());
		}
		db.executeUpdate(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.level.UserChargeLevel#getAllChargeLevel()
	 */
	public Map getAllChargeLevel() {
		String fields = "";
		List chargeTypeList = ChargeType.types();
		int listLength = chargeTypeList.size();
		Map allChargeLevel = new HashMap();
		if (listLength < 1)
			return allChargeLevel;
		for (int i = 0; i < listLength; i++) {
			fields = fields
					+ ((String) oracleChargeLevel.chargeTypeField
							.get(((String) (ChargeType.types().get(i))))) + ",";
		}
		fields = fields.substring(0, fields.length() - 1);
		String sql = "select " + fields
				+ " from entity_userfee_level where user_id='"
				+ this.getUser().getId() + "'";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				for (int i = 0; i < listLength; i++) {
					allChargeLevel.put(chargeTypeList.get(i), rs
							.getString((String) chargeTypeList.get(i)));
				}
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleUserChargeLevel.getAllChargeLevel error" + sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return allChargeLevel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.level.UserChargeLevel#getChargeLevel(java.lang.String)
	 */
	public String getChargeLevel(String chargeType) {
		String field = ((String) oracleChargeLevel.chargeTypeFieldValue
				.get(chargeType));
		String sql = "select " + field
				+ " from entity_userfee_level where user_id='"
				+ this.getUser().getId() + "'";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String chargeLevel = "";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				chargeLevel = rs.getString(field);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleUserChargeLevel.getChargeLevel error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return chargeLevel;
	}

	public void setRecruitChargeLevel(Map chargeLevel) {
		Iterator iterator = chargeLevel.entrySet().iterator();
		List keyList = new ArrayList();
		List valueList = new ArrayList();

		for (int i = 0; i < chargeLevel.size(); i++) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if (oracleChargeLevel.InChargeLevel(key)) {
				keyList.add(key);
				valueList.add(value);
			}
		}
		if (keyList.size() < 1)
			return;

		dbpool db = new dbpool();
		String sql = "";
		// 该用户是否存在
		if (db
				.countselect("select user_id from entity_recruit_userfee_level where user_id='"
						+ this.getUser().getId() + "'") < 1) {

			String fields = "";
			String values = "";
			String keyTmp = null;
			String valueTmp = null;
			for (int i = 0; i < keyList.size(); i++) {
				keyTmp = (String) keyList.get(i);
				valueTmp = (String) valueList.get(i);
				fields = fields
						+ ((String) oracleChargeLevel.chargeTypeField
								.get(keyTmp))
						+ ","
						+ ((String) oracleChargeLevel.chargeTypeFieldValue
								.get(keyTmp)) + ",";
				values = values + "'" + keyTmp + "','" + valueTmp + "',";

			}
			if (fields.length() > 0)
				fields = fields.substring(0, fields.length() - 1);
			if (values.length() > 0)
				values = values.substring(0, values.length() - 1);
			sql = "insert into entity_recruit_userfee_level(user_id," + fields + ") "
					+ "values('" + this.getUser().getId() + "'," + values + ")";
			UserAddLog.setDebug("OracleUserChargeLevel.setRecruitChargeLevel(Map chargeLevel) SQL=" + sql + " DATE=" + new Date());
		} else {
			String fields = "";
			String keyTmp = null;
			String valueTmp = null;
			for (int i = 0; i < keyList.size(); i++) {
				keyTmp = (String) keyList.get(i);
				valueTmp = (String) valueList.get(i);
				fields = fields
						+ ((String) oracleChargeLevel.chargeTypeField
								.get(keyTmp))
						+ "='"
						+ keyTmp
						+ "',"
						+ ((String) oracleChargeLevel.chargeTypeFieldValue
								.get(keyTmp)) + "='" + valueTmp + "',";

			}
			if (fields.length() > 0)
				fields = fields.substring(0, fields.length() - 1);
			sql = "update entity_recruit_userfee_level set " + fields
					+ " where user_id='" + this.getUser().getId() + "'";
			UserUpdateLog.setDebug("OracleUserChargeLevel.setRecruitChargeLevel(Map chargeLevel) SQL=" + sql + " DATE=" + new Date());
		}
		db.executeUpdate(sql); 
		

		
	}
	public String getRecruitChargeLevel(String chargeType) {
		String field = ((String) oracleChargeLevel.chargeTypeFieldValue
				.get(chargeType));
		String sql = "select " + field
				+ " from entity_recruit_userfee_level where user_id='"
				+ this.getUser().getId() + "'";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String chargeLevel = "";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				chargeLevel = rs.getString(field);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleUserChargeLevel.getRecruitChargeLevel error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return chargeLevel;
	}
}
