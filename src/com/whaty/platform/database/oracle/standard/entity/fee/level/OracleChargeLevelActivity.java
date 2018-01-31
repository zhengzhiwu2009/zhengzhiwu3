/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.fee.level;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.fee.level.ChargeLevelActivity;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author chenjian
 * 
 */
public class OracleChargeLevelActivity implements ChargeLevelActivity {

	private OracleChargeLevel oracleChargeLevel;

	private OracleOtherChargeLevel oracleOtherChargeLevel;

	public OracleChargeLevelActivity() {
		super();
		oracleChargeLevel = new OracleChargeLevel();
		oracleOtherChargeLevel = new OracleOtherChargeLevel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.level.chargeLevelActivity#setBatchChargeLevel(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	public void setBatchChargeLevel(String siteId, String majorId,
			String edutypeId, String gradeId, Map chargeLevel) {
		Iterator iterator = chargeLevel.entrySet().iterator();
		List keyList = new ArrayList();
		List valueList = new ArrayList();

		for (int i = 0; i < chargeLevel.size(); i++) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			// String value=(String)entry.getKey();
			String value = (String) entry.getValue();
			if (oracleChargeLevel.InChargeLevel(key)) {
				keyList.add(key);
				valueList.add(value);
			}
		}
		if (keyList.size() < 1)
			return;
		String fields = "";
		String values = "";
		String update = "";
		String keyTmp = null;
		String valueTmp = null;
		for (int i = 0; i < keyList.size(); i++) {
			keyTmp = (String) keyList.get(i);
			valueTmp = (String) valueList.get(i);
			fields = fields
					+ ((String) oracleChargeLevel.chargeTypeField.get(keyTmp))
					+ ","
					+ ((String) oracleChargeLevel.chargeTypeFieldValue
							.get(keyTmp)) + ",";
			values = values + "'" + keyTmp + "','" + valueTmp + "',";

		}
		if (values.length() > 0)
			values = values.substring(0, values.length() - 1);
		for (int i = 0; i < keyList.size(); i++) {
			keyTmp = (String) keyList.get(i);
			valueTmp = (String) valueList.get(i);
			update += " "
					+ ((String) oracleChargeLevel.chargeTypeField.get(keyTmp))
					+ "='" + keyTmp + "' ,";
			update += " "
					+ ((String) oracleChargeLevel.chargeTypeFieldValue
							.get(keyTmp)) + "='" + valueTmp + "' ,";
		}
		if (fields != null && fields.endsWith(","))
			fields = fields.substring(0, fields.length() - 1);
		if (update != null && update.endsWith(","))
			update = update.substring(0, update.length() - 1);

		String del1 = "delete from entity_userfee_level where user_id in(select id from entity_student_info ";
		String del2 = "delete from entity_recruit_userfee_level where user_id in(select id from recruit_student_info where reg_no is not null and reg_no <> 'null' ";
		String del3 = "delete from entity_fee_level ";

		String sql = "insert into entity_userfee_level(user_id," + fields
				+ ") select " + "id," + values + " from entity_student_info ";

		String sql2 = "insert into entity_recruit_userfee_level(user_id,"
				+ fields
				+ ") select "
				+ "id,"
				+ values
				+ " from recruit_student_info where reg_no is not null and reg_no <> 'null' ";

		String sql3 = "insert into entity_fee_level(site_id,major_id,grade_id,edutype_id,"
				+ fields
				+ ") values ('"
				+ siteId
				+ "','"
				+ majorId
				+ "','"
				+ gradeId + "','" + edutypeId + "'," + values + ")";

		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("major_id", majorId));
		searchProperty.add(new SearchProperty("site_id", siteId));
		searchProperty.add(new SearchProperty("edutype_id", edutypeId));
		searchProperty.add(new SearchProperty("grade_id", gradeId));

		dbpool db = new dbpool();
		del1 += Conditions.convertToCondition(searchProperty, null) + ")";
		del2 += Conditions.convertToAndCondition(searchProperty, null) + ")";
		del3 += Conditions.convertToCondition(searchProperty, null);
		sql += Conditions.convertToCondition(searchProperty, null);
		sql2 += Conditions.convertToAndCondition(searchProperty, null);
		sql3 += Conditions.convertToCondition(searchProperty, null);
		ArrayList sqlList = new ArrayList();
		sqlList.add(del1);
		sqlList.add(del2);
		sqlList.add(del3);
		sqlList.add(sql);
		sqlList.add(sql2);
		sqlList.add(sql3);
		db.executeUpdateBatch(sqlList);
		
		
		UserAddLog.setDebug("OracleChargeLevelActivity.setBatchChargeLevel(String siteId, String majorId,String edutypeId, String gradeId, Map chargeLevel) SQL1=" + sql + "SQL2=" + sql2 + "SQL3=" + sql3 + " DATE=" + new Date() );
		UserDeleteLog.setDebug("OracleChargeLevelActivity.setBatchChargeLevel(String siteId, String majorId,String edutypeId, String gradeId, Map chargeLevel) SQL1=" + del1 + "SQL2=" + del2 + "SQL3=" + del3 + " DATE=" + new Date() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.level.chargeLevelActivity#setBatchChargeLevel(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	public void setBatchChargeLevel(String[] siteId, String[] majorId,
			String[] edutypeId, String[] gradeId, Map chargeLevel) throws PlatformException {
		dbpool db = new dbpool();
		
		Iterator iterator = chargeLevel.entrySet().iterator();
		List keyList = new ArrayList();
		List valueList = new ArrayList();

		for (int i = 0; i < chargeLevel.size(); i++) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			// String value=(String)entry.getKey();
			String value = (String) entry.getValue();
			if (oracleChargeLevel.InChargeLevel(key)) {
				keyList.add(key);
				valueList.add(value);
			}
		}
		if (keyList.size() < 1)
			return;
		String fields = "";
		String values = "";
		String update = "";
		String keyTmp = null;
		String valueTmp = null;
		for (int i = 0; i < keyList.size(); i++) {
			keyTmp = (String) keyList.get(i);
			valueTmp = (String) valueList.get(i);
			fields = fields
					+ ((String) oracleChargeLevel.chargeTypeField.get(keyTmp))
					+ ","
					+ ((String) oracleChargeLevel.chargeTypeFieldValue
							.get(keyTmp)) + ",";
			values = values + "'" + keyTmp + "','" + valueTmp + "',";

		}
		if (values.length() > 0)
			values = values.substring(0, values.length() - 1);
		for (int i = 0; i < keyList.size(); i++) {
			keyTmp = (String) keyList.get(i);
			valueTmp = (String) valueList.get(i);
			update += " "
					+ ((String) oracleChargeLevel.chargeTypeField.get(keyTmp))
					+ "='" + keyTmp + "' ,";
			update += " "
					+ ((String) oracleChargeLevel.chargeTypeFieldValue
							.get(keyTmp)) + "='" + valueTmp + "' ,";
		}
		if (fields != null && fields.endsWith(","))
			fields = fields.substring(0, fields.length() - 1);
		if (update != null && update.endsWith(","))
			update = update.substring(0, update.length() - 1);

		String del1 = "delete from entity_userfee_level where user_id in(select id from entity_student_info ";
		String del2 = "delete from entity_recruit_userfee_level where user_id in(select id from recruit_student_info where reg_no is not null and reg_no <> 'null' ";
		String del3 = "delete from entity_fee_level ";

		List searchProperty = new ArrayList();
		List searchProperty1 = new ArrayList();
		String majorStr = "";
		if (majorId!=null && majorId.length != 0) {
			for (int i = 0; i < majorId.length; i++) {
				majorStr = majorStr + ",'" + majorId[i] + "'";
			}
			majorStr = majorStr.substring(1);
			searchProperty.add(new SearchProperty("major_id", majorStr, "in"));
			searchProperty1.add(new SearchProperty("mi.id", majorStr, "in"));
		}
		String siteStr = "";
		if (siteId!=null && siteId.length != 0) {
			for (int i = 0; i < siteId.length; i++) {
				siteStr = siteStr + ",'" + siteId[i] + "'";
			}
			siteStr = siteStr.substring(1);
			searchProperty.add(new SearchProperty("site_id", siteStr, "in"));
			searchProperty1.add(new SearchProperty("si.id", siteStr, "in"));
		}
		String edutypeStr = "";
		if (edutypeId!=null && edutypeId.length != 0) {
			for (int i = 0; i < edutypeId.length; i++) {
				edutypeStr = edutypeStr + ",'" + edutypeId[i] + "'";
			}
			edutypeStr = edutypeStr.substring(1);
			searchProperty.add(new SearchProperty("edutype_id", edutypeStr, "in"));
			searchProperty1.add(new SearchProperty("ei.id", edutypeStr, "in"));
		}
		String gradeStr = "";
		if (gradeId!=null && gradeId.length != 0) {
			for (int i = 0; i < gradeId.length; i++) {
				gradeStr = gradeStr + ",'" + gradeId[i] + "'";
			}
			gradeStr = gradeStr.substring(1);
			searchProperty.add(new SearchProperty("grade_id", gradeStr, "in"));
			searchProperty1.add(new SearchProperty("gi.id", gradeStr, "in"));
		}

		String sql = "insert into entity_userfee_level(user_id," + fields
				+ ") select " + "id," + values + " from entity_student_info ";

		String sql2 = "insert into entity_recruit_userfee_level(user_id,"
				+ fields
				+ ") select "
				+ "id,"
				+ values
				+ " from recruit_student_info where reg_no is not null and reg_no <> 'null' ";
		String sql3 = "insert into entity_fee_level(site_id,major_id,grade_id,edutype_id,"
				+ fields
				+ ") select site_id,major_id,grade_id,edutype_id,"+values+" from (select si.id as site_id,mi.id as major_id,gi.id as grade_id,ei.id as edutype_id,"
				+ values
				+ " from entity_major_info mi,entity_site_info si,"
				+ "entity_edu_type ei,entity_grade_info gi "+Conditions.convertToCondition(searchProperty1, null)+")";
		
		del1 += Conditions.convertToCondition(searchProperty, null) + ")";
		del2 += Conditions.convertToAndCondition(searchProperty, null) + ")";
		del3 += Conditions.convertToCondition(searchProperty, null);
		sql += Conditions.convertToCondition(searchProperty, null);
		sql2 += Conditions.convertToAndCondition(searchProperty, null);
		sql3 += Conditions.convertToCondition(searchProperty, null);
		ArrayList sqlList = new ArrayList();
		sqlList.add(del1);
		sqlList.add(del2);
		sqlList.add(del3);
		sqlList.add(sql);
		sqlList.add(sql2);
		sqlList.add(sql3);
		db.executeUpdateBatch(sqlList);
		UserAddLog.setDebug("OracleChargeLevelActivity.setBatchChargeLevel(String siteId, String majorId,String edutypeId, String gradeId, Map chargeLevel) SQL1=" + sql + "SQL2=" + sql2 + "SQL3=" + sql3 + " DATE=" + new Date() );
		UserDeleteLog.setDebug("OracleChargeLevelActivity.setBatchChargeLevel(String siteId, String majorId,String edutypeId, String gradeId, Map chargeLevel) SQL1=" + del1 + "SQL2=" + del2 + "SQL3=" + del3 + " DATE=" + new Date() );
		
	}

	public void setBatchOtherChargeLevel(String[] siteId, String[] majorId,
			String[] edutypeId, String[] gradeId, String otherFeeTypeId,
			Map chargeLevel) {
		Iterator iterator = chargeLevel.entrySet().iterator();
		List keyList = new ArrayList();
		List valueList = new ArrayList();

		for (int i = 0; i < chargeLevel.size(); i++) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			keyList.add(key);
			valueList.add(value);
		}
		String fields = "";
		String values = "";
		String update = "";
		String keyTmp = null;
		String valueTmp = null;
		for (int i = 0; i < keyList.size(); i++) {
			keyTmp = (String) keyList.get(i);
			valueTmp = (String) valueList.get(i);
			fields = fields + "type3_name,type3_value,";
			values = values + "'" + keyTmp + "','" + valueTmp + "',";

		}
		if (values.length() > 0)
			values = values.substring(0, values.length() - 1);
		for (int i = 0; i < keyList.size(); i++) {
			keyTmp = (String) keyList.get(i);
			valueTmp = (String) valueList.get(i);
			update += " type3_name='" + keyTmp + "' ,";
			update += " type3_value='" + valueTmp + "' ,";
		}
		if (fields != null && fields.endsWith(","))
			fields = fields.substring(0, fields.length() - 1);
		if (update != null && update.endsWith(","))
			update = update.substring(0, update.length() - 1);

		String sql = "insert into entity_userfee_level(user_id," + fields
				+ ") select " + "id," + values + " from entity_student_info ";

		String sql2 = "update entity_userfee_level set " + update
				+ " where user_id in(select id from entity_student_info ";

		String check = "select site_id,major_id,grade_id,edutype_id from entity_fee_level ";

		String delete = "delete entity_fee_level";

		String sql4 = "update entity_fee_level set " + update + " ";

		List searchProperty = new ArrayList();
		String majorStr = "";
		if (majorId.length != 0) {
			for (int i = 0; i < majorId.length; i++) {
				majorStr = majorStr + ",'" + majorId[i] + "'";
			}
			majorStr = majorStr.substring(1);
		}
		String siteStr = "";
		if (siteId.length != 0) {
			for (int i = 0; i < siteId.length; i++) {
				siteStr = siteStr + ",'" + siteId[i] + "'";
			}
			siteStr = siteStr.substring(1);
		}
		String edutypeStr = "";
		if (edutypeId.length != 0) {
			for (int i = 0; i < edutypeId.length; i++) {
				edutypeStr = edutypeStr + ",'" + edutypeId[i] + "'";
			}
			edutypeStr = edutypeStr.substring(1);
		}
		String gradeStr = "";
		if (gradeId.length != 0) {
			for (int i = 0; i < gradeId.length; i++) {
				gradeStr = gradeStr + ",'" + gradeId[i] + "'";
			}
			gradeStr = gradeStr.substring(1);
		}

		searchProperty.add(new SearchProperty("major_id", majorStr, "in"));
		searchProperty.add(new SearchProperty("site_id", siteStr, "in"));
		searchProperty.add(new SearchProperty("edutype_id", edutypeStr, "in"));
		searchProperty.add(new SearchProperty("grade_id", gradeStr, "in"));
		String sql3 = "insert into entity_fee_level(site_id,major_id,grade_id,edutype_id,"
				+ fields
				+ ") select si.id,mi.id,gi.id,ei.id,"
				+ values
				+ " from entity_major_info mi,entity_site_info si,"
				+ "entity_edu_type ei,entity_grade_info gi where si.id in ("
				+ siteStr
				+ ") and "
				+ "mi.id in ("
				+ majorStr
				+ ") and ei.id in ("
				+ edutypeStr
				+ ") and gi.id in ("
				+ gradeStr + ")";
		sql = sql + Conditions.convertToCondition(searchProperty, null);
		sql2 += Conditions.convertToCondition(searchProperty, null) + ")";
		check += Conditions.convertToCondition(searchProperty, null);
		delete += Conditions.convertToCondition(searchProperty, null);
		sql4 += Conditions.convertToCondition(searchProperty, null);
		if (sql != null && sql.indexOf(" where ") > 0)
			sql += " and id not in (select user_id from entity_userfee_level) ";
		else
			sql += " where id not in (select user_id from entity_userfee_level) ";
		dbpool db = new dbpool();
		UserAddLog.setDebug("OracleChargeLevelActivity.setBatchOtherChargeLevel(String[] siteId, String[] majorId,String[] edutypeId, String[] gradeId, String otherFeeTypeId,Map chargeLevel) SQL=" + sql + " DATE=" + new Date());
		db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleChargeLevelActivity.setBatchOtherChargeLevel(String[] siteId, String[] majorId,String[] edutypeId, String[] gradeId, String otherFeeTypeId,Map chargeLevel) SQL=" + sql2 + " DATE=" + new Date());
		db.executeUpdate(sql2);
		if (db.countselect(check) == 0) {
			UserAddLog.setDebug("OracleChargeLevelActivity.setBatchOtherChargeLevel(String[] siteId, String[] majorId,String[] edutypeId, String[] gradeId, String otherFeeTypeId,Map chargeLevel) SQL=" + sql3 + " DATE=" + new Date());
			db.executeUpdate(sql3);
		} else {
			UserDeleteLog.setDebug("OracleChargeLevelActivity.setBatchOtherChargeLevel(String[] siteId, String[] majorId,String[] edutypeId, String[] gradeId, String otherFeeTypeId,Map chargeLevel) SQL=" + delete + " DATE=" + new Date());
			db.executeUpdate(delete);
			UserAddLog.setDebug("OracleChargeLevelActivity.setBatchOtherChargeLevel(String[] siteId, String[] majorId,String[] edutypeId, String[] gradeId, String otherFeeTypeId,Map chargeLevel) SQL=" + sql3 + " DATE=" + new Date());
			db.executeUpdate(sql3);
		}
	}

}
