/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.fee.deal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.User;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.fee.deal.FeeList;
import com.whaty.platform.entity.fee.deal.FeeType;
import com.whaty.platform.entity.fee.deal.PayoutType;
import com.whaty.platform.entity.fee.deal.UserFeeRecord;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.EntityLog;

/**
 * @author chenjian
 * 
 */
public class OracleFeeList implements FeeList {

	private String SQLFEE = "select distinct ID,USER_ID,DATE_TIME,FEE_VALUE,FEE_TYPE,PAYOUT_TYPE,NOTE,checked,orderno,invoice_status,invoice_date,login_id,name,site_id,grade_id,edutype_id,major_id,site_name from (select a.ID,a.USER_ID,"
			+ "a.DATE_TIME,a.FEE_VALUE,a.FEE_TYPE,a.PAYOUT_TYPE,a.NOTE,a.checked,a.orderno,a.invoice_status,a.invoice_date,b.login_id,b.name,c.site_id,c.grade_id,c.edutype_id,c.major_id,s.name as site_name "
			+ "from entity_userfee_record a,sso_user b,entity_student_info c,entity_site_info s where a.user_id=b.id and b.id=c.id and c.site_id=s.id)";

	private String SQLFEE2 = "select distinct ID,USER_ID,DATE_TIME,FEE_VALUE,FEE_TYPE,PAYOUT_TYPE,NOTE,checked,orderno,invoice_status,reg_no,name from (select a.ID,a.USER_ID,"
			+ "a.DATE_TIME,a.FEE_VALUE,a.FEE_TYPE,a.PAYOUT_TYPE,a.NOTE,a.checked,a.orderno,a.invoice_status,b.reg_no,b.name "
			+ "from entity_userfee_record a,recruit_student_info b where a.orderno like b.reg_no||'___________')";

	private String SQLUNCONFIRMFEE = "select ID,user_id,DATE_TIME,FEE_VALUE,FEE_TYPE,PAYOUT_TYPE,NOTE,checked,orderno from entity_userfee_record";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.deal.FeeList#searchFeeRecord(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List)
	 */
	public List searchFeeRecord(Page page, List searchProperty,
			List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = SQLFEE
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;

		ArrayList records = null;
		try {
			records = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				UserFeeRecord feeRecord = new UserFeeRecord();
				feeRecord.setId(rs.getString("id"));
				User user = new User();
				user.setId(rs.getString("user_id"));
				user.setLoginId(rs.getString("login_id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("site_name"));
				feeRecord.setUser(user);
				feeRecord.setFeeValue(rs.getDouble("fee_value"));
				feeRecord.setFeeType(rs.getString("fee_type"));
				feeRecord.setPayoutType(rs.getString("payout_type"));
				feeRecord.setNote(rs.getString("note"));
				feeRecord.setInvoiceStatus(rs.getString("invoice_status"));
				Date date = rs.getDate("date_time");
				Date time = rs.getTime("date_time");
				Date all = new Date(date.getYear(), date.getMonth(), date
						.getDate(), time.getHours(), time.getMinutes(), time
						.getSeconds());
				feeRecord.setDateTime(all);
				feeRecord.setInvoiceDate(rs.getString("invoice_date"));
				
				if (rs.getInt("checked") == 1) {
					feeRecord.setChecked(true);
				} else {
					feeRecord.setChecked(false);
				}
				feeRecord.setOrderNo(rs.getString("orderno"));
				records.add(feeRecord);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return records;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.deal.FeeList#searchFeeRecordNum(java.util.List)
	 */
	public int searchFeeRecordNum(List searchProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = SQLFEE
				+ Conditions.convertToCondition(searchProperty, null);
		return db.countselect(sql);
	}

	public List searchUnConfirmFeeRecord(Page page, List searchProperty,
			List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select distinct ID,USER_ID,DATE_TIME,FEE_VALUE,FEE_TYPE,PAYOUT_TYPE,NOTE,checked,orderno,invoice_status,login_id as reg_no,name from (select a.ID,a.USER_ID,"
				+ "a.DATE_TIME,a.FEE_VALUE,a.FEE_TYPE,a.PAYOUT_TYPE,a.NOTE,a.checked,a.orderno,a.invoice_status,b.login_id,b.name,c.site_id,c.grade_id,c.edutype_id,c.major_id,s.name as site_name "
				+ "from entity_userfee_record a,sso_user b,entity_student_info c,entity_site_info s where a.user_id=b.id and b.id=c.id and c.site_id=s.id)"
				+ Conditions.convertToCondition(searchProperty, null);
		String sql2 = "select distinct ID,USER_ID,DATE_TIME,FEE_VALUE,FEE_TYPE,PAYOUT_TYPE,NOTE,checked,orderno,invoice_status,reg_no,name from (select a.ID,a.USER_ID,"
				+ "a.DATE_TIME,a.FEE_VALUE,a.FEE_TYPE,a.PAYOUT_TYPE,a.NOTE,a.checked,a.orderno,a.invoice_status,b.reg_no,b.name "
				+ "from entity_userfee_record a,recruit_student_info b where a.orderno like b.reg_no||'___________')"
				+ Conditions.convertToCondition(searchProperty, null);
		EntityLog.setDebug(sql2);
		sql = "select distinct ID,USER_ID,DATE_TIME,FEE_VALUE,FEE_TYPE,PAYOUT_TYPE,NOTE,checked,orderno,invoice_status,reg_no,name from ("
				+ sql + " union " + sql2 + ") order by date_time desc";
		MyResultSet rs = null;

		ArrayList records = null;
		try {
			records = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				UserFeeRecord feeRecord = new UserFeeRecord();
				feeRecord.setId(rs.getString("id"));
				User user = new User();
				user.setId(rs.getString("user_id"));
				user.setLoginId(rs.getString("reg_no"));
				user.setName(rs.getString("name"));
				feeRecord.setUser(user);
				feeRecord.setFeeValue(rs.getDouble("fee_value"));
				feeRecord.setFeeType(rs.getString("fee_type"));
				feeRecord.setPayoutType(rs.getString("payout_type"));
				feeRecord.setNote(rs.getString("note"));
				Date date = rs.getDate("date_time");
				Date time = rs.getTime("date_time");
				Date all = new Date(date.getYear(), date.getMonth(), date
						.getDate(), time.getHours(), time.getMinutes(), time
						.getSeconds());
				feeRecord.setDateTime(all);
				if (rs.getInt("checked") == 1) {
					feeRecord.setChecked(true);
				} else {
					feeRecord.setChecked(false);
				}
				feeRecord.setOrderNo(rs.getString("orderno"));
				records.add(feeRecord);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		/*
		 * String sql = SQLFEE + Conditions.convertToCondition(searchProperty,
		 * orderProperty); EntityLog.setDebug(sql); String sql2 = SQLFEE2 +
		 * Conditions.convertToCondition(searchProperty, orderProperty);
		 * EntityLog.setDebug(sql2); MyResultSet rs = null;
		 * 
		 * ArrayList records = null; try { records = new ArrayList(); if (page !=
		 * null) { int pageint = page.getPageInt(); int pagesize =
		 * page.getPageSize(); rs = db.execute_oracle_page(sql, pageint,
		 * pagesize); } else { rs = db.executeQuery(sql); } while (rs != null &&
		 * rs.next()) { UserFeeRecord feeRecord = new UserFeeRecord();
		 * feeRecord.setId(rs.getString("id")); User user = new User();
		 * user.setId(rs.getString("user_id"));
		 * user.setLoginId(rs.getString("login_id"));
		 * user.setName(rs.getString("name")); feeRecord.setUser(user);
		 * feeRecord.setFeeValue(rs.getDouble("fee_value"));
		 * feeRecord.setFeeType(rs.getString("fee_type"));
		 * feeRecord.setPayoutType(rs.getString("payout_type"));
		 * feeRecord.setNote(rs.getString("note")); Date date =
		 * rs.getDate("date_time"); Date time = rs.getTime("date_time"); Date
		 * all = new Date(date.getYear(), date.getMonth(), date .getDate(),
		 * time.getHours(), time.getMinutes(), time .getSeconds());
		 * feeRecord.setDateTime(all); if (rs.getInt("checked") == 1) {
		 * feeRecord.setChecked(true); } else { feeRecord.setChecked(false); }
		 * feeRecord.setOrderNo(rs.getString("orderno"));
		 * records.add(feeRecord); } } catch (Exception e) {
		 *  } finally { db.close(rs); db = null; } db = new
		 * dbpool(); rs = null; try { if (page != null) { int pageint =
		 * page.getPageInt(); int pagesize = page.getPageSize(); rs =
		 * db.execute_oracle_page(sql2, pageint, pagesize); } else { rs =
		 * db.executeQuery(sql2); } while (rs != null && rs.next()) {
		 * UserFeeRecord feeRecord = new UserFeeRecord();
		 * feeRecord.setId(rs.getString("id")); User user = new User();
		 * user.setId(rs.getString("user_id"));
		 * user.setLoginId(rs.getString("reg_no"));
		 * user.setName(rs.getString("name")); feeRecord.setUser(user);
		 * feeRecord.setFeeValue(rs.getDouble("fee_value"));
		 * feeRecord.setFeeType(rs.getString("fee_type"));
		 * feeRecord.setPayoutType(rs.getString("payout_type"));
		 * feeRecord.setNote(rs.getString("note")); Date date =
		 * rs.getDate("date_time"); Date time = rs.getTime("date_time"); Date
		 * all = new Date(date.getYear(), date.getMonth(), date .getDate(),
		 * time.getHours(), time.getMinutes(), time .getSeconds());
		 * feeRecord.setDateTime(all); if (rs.getInt("checked") == 1) {
		 * feeRecord.setChecked(true); } else { feeRecord.setChecked(false); }
		 * feeRecord.setOrderNo(rs.getString("orderno"));
		 * records.add(feeRecord); } } catch (Exception e) {
		 *  } finally { db.close(rs); db = null; }
		 */
		return records;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.deal.FeeList#searchFeeRecordNum(java.util.List)
	 */
	public int searchUnConfirmFeeRecordNum(List searchProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = SQLFEE
				+ Conditions.convertToCondition(searchProperty, null);
		String sql2 = SQLFEE2
				+ Conditions.convertToCondition(searchProperty, null);
		return db.countselect(sql) + db.countselect(sql2);
	}

	public List searchLeftFee(Page page, List searchProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList lefts = null;
		String leftFeeSql = "select sum(fee_value) as leftvalue from (select id,user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno from entity_userfee_record "
				+ Conditions.convertToCondition(searchProperty, null)
				+ ") group by user_id";
		try {
			lefts = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(leftFeeSql, pageint, pagesize);
			} else {
				rs = db.executeQuery(leftFeeSql);
			}
			while (rs != null && rs.next()) {
				String leftvalue = rs.getString("leftvalue");
				lefts.add(leftvalue);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return lefts;
	}

	public List getFeeStatBySemester(String semesterId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();

		String sql = "select d.name as site_name, a.fee_value as deposit_fee_sum, b.fee_value as consume_fee_sum, c.rfee as into_fee_sum from "
				+ "(select site_id,sum(fee_value) as fee_value from (select eui.site_id,esi.id as s_id,eur.id, user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno from entity_userfee_record eur, entity_semester_info esi,entity_student_info eui where (eur.date_time between esi.start_date and esi.end_date) and eui.id=eur.user_id and esi.id='"
				+ semesterId
				+ "' and eur.payout_type='"
				+ PayoutType.DEPOSIT
				+ "' and fee_type='"
				+ FeeType.CREDIT
				+ "') group by site_id) a, (select site_id,sum(fee_value) as fee_value from (select eui.site_id,esi.id as s_id,eur.id, user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno from entity_userfee_record eur, entity_semester_info esi,entity_student_info eui where (eur.date_time between esi.start_date and esi.end_date) and eui.id=eur.user_id and esi.id='"
				+ semesterId
				+ "' and eur.payout_type='"
				+ PayoutType.CONSUME
				+ "' and fee_type='"
				+ FeeType.CREDIT
				+ "') group by site_id) b, (select site_id,round(sum(rfee),1) as rfee from (select eui.site_id,esi.id as s_id,eur.id, eur.user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno, case when type5_value!='0' then fee_value * type2_value/type5_value else 0 end as rfee from entity_userfee_record eur, entity_semester_info esi,entity_student_info eui,(select user_id,type2_value,type5_value from entity_userfee_level) eul where (eur.date_time between esi.start_date and esi.end_date) and eui.id=eur.user_id and esi.id='"
				+ semesterId
				+ "' and eur.payout_type='"
				+ PayoutType.DEPOSIT
				+ "' and fee_type='"
				+ FeeType.CREDIT
				+ "' and eul.user_id=eur.user_id) group by site_id) c, (select id, name from entity_site_info) d where a.site_id=b.site_id(+) and a.site_id=c.site_id(+) and a.site_id=d.id";

		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				List subList = new ArrayList();
				subList.add(rs.getString("site_name"));
				subList.add(rs.getString("deposit_fee_sum"));
				subList.add(rs.getString("consume_fee_sum"));
				subList.add(rs.getString("into_fee_sum"));
				list.add(subList);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return list;
	}

	public List getSiteFeeStat(String startDate, String endDate, String siteId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();

		String timeCondition = "";
		if (startDate != null && !"".equals(startDate)) {
			timeCondition += "eur.date_time >=to_date('" + startDate
					+ "','yyyy-mm-dd')";
		}

		if (endDate != null && !"".equals(endDate)) {
			if (timeCondition.length() > 0)
				timeCondition += " and eur.date_time <=to_date('" + endDate
						+ "','yyyy-mm-dd')";
			else
				timeCondition += "eur.date_time <=to_date('" + endDate
						+ "','yyyy-mm-dd')";
		}

		if (timeCondition.length() > 0)
			timeCondition += " and ";

		String sql = "select d.id as site_id, d.name as site_name, a.fee_value as deposit_fee_sum, b.fee_value as consume_fee_sum, c.rfee as into_fee_sum from "
				+ "(select site_id,round(sum(fee_value),2) as fee_value from (select eui.site_id,eur.id, user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno from entity_userfee_record eur, entity_student_info eui where "
				+ timeCondition
				+ " eui.id=eur.user_id and eur.payout_type='"
				+ PayoutType.DEPOSIT
				+ "' and eur.checked = '1' and fee_type='"
				+ FeeType.CREDIT
				+ "') group by site_id) a, (select site_id,nvl(round(sum(fee_value),2),0) as fee_value from (select eui.site_id,eur.id, user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno from entity_userfee_record eur, entity_student_info eui where "
				+ timeCondition
				+ " eui.id=eur.user_id and eur.payout_type='"
				+ PayoutType.CONSUME
				+ "' and eur.checked = '1' and fee_type='"
				+ FeeType.CREDIT
				+ "') group by site_id) b, (select site_id,nvl(round(sum(rfee),2),0) as rfee from (select eui.site_id,eur.id, eur.user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno, case when type5_value!='0' then fee_value * type2_value/type5_value else 0 end as rfee from entity_userfee_record eur, entity_student_info eui,(select user_id,type5_value,type2_value from entity_userfee_level) eul where "
				+ timeCondition
				+ " eui.id=eur.user_id and eur.payout_type='"
				+ PayoutType.DEPOSIT
				+ "' and eur.checked = '1' and fee_type='"
				+ FeeType.CREDIT
				+ "' and eul.user_id=eur.user_id) group by site_id) c, (select id, name from entity_site_info) d where a.site_id=b.site_id(+) and a.site_id=c.site_id(+) and a.site_id=d.id";

		if (siteId != null && !"".equals(siteId)
				&& !"null".equalsIgnoreCase(siteId)) {
			sql += " and a.site_id='" + siteId + "'";
		}

		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				List subList = new ArrayList();
				subList.add(rs.getString("site_id"));
				subList.add(rs.getString("site_name"));
				subList.add(rs.getString("deposit_fee_sum"));
				subList.add(rs.getString("consume_fee_sum"));
				subList.add(rs.getString("into_fee_sum"));
				list.add(subList);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return list;
	}

	public double getUserFeeStandard(String userId) {
		double standard = 0;
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select type1_value from entity_userfee_level where user_id='"
				+ userId + "'";
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				standard = rs.getDouble("type1_value");
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return standard;
	}

	public double getUserFeeStandard(List searchProperty, List orderProperty) {
		double standard = 0;
		dbpool db = new dbpool();
		MyResultSet rs = null;

		String sql = "select type1_value from entity_fee_level ";
		sql += Conditions.convertToCondition(searchProperty, orderProperty);
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				standard = rs.getDouble("type1_value");
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return standard;
	}

	public List getUserFeeStandard(Page page, List searchProperty,
			List orderProperty) {
		List feeStandard = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;

		String sql = "select site_id,site_name,grade_id,grade_name,major_id,major_name,edutype_id,"
				+ "edutype_name,type1_value,type2_value,type3_value,type4_value,type5_value from (select si.id as site_id,si.name as site_name,"
				+ "gi.id as grade_id,gi.name as grade_name,"
				+ "mi.id as major_id,mi.name as major_name,et.id as edutype_id,et.name as edutype_name,"
				+ "fl.type1_value as type1_value,fl.type2_value as type2_value,fl.type3_value as type3_value,"
				+ "fl.type4_value as type4_value,fl.type5_value as type5_value from entity_fee_level fl,"
				+ "entity_site_info si,"
				+ "entity_major_info mi,entity_grade_info gi,entity_edu_type et where fl.site_id=si.id "
				+ "and fl.major_id=mi.id and fl.grade_id=gi.id and fl.edutype_id=et.id)";
		sql += Conditions.convertToCondition(searchProperty, orderProperty);
		if (page != null) {
			int pageint = page.getPageInt();
			int pagesize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageint, pagesize);
		} else {
			rs = db.executeQuery(sql);
		}
		try {
			while (rs != null && rs.next()) {
				List tempList = new ArrayList();
				tempList.add(rs.getString("site_name"));
				tempList.add(rs.getString("grade_name"));
				tempList.add(rs.getString("major_name"));
				tempList.add(rs.getString("edutype_name"));
				tempList.add(rs.getString("type1_value"));
				tempList.add(rs.getString("type2_value"));
				tempList.add(rs.getString("type3_value"));
				tempList.add(rs.getString("type4_value"));
				tempList.add(rs.getString("type5_value"));
				feeStandard.add(tempList);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return feeStandard;
	}

	public int getUserFeeStandardNum(List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;

		String sql = "select type1_value from entity_fee_level ";
		sql += Conditions.convertToCondition(searchProperty, orderProperty);
		return db.countselect(sql);
	}

	public int backReturnApply(String id, String note) {
		dbpool db = new dbpool();
		String sql = "update entity_userfee_returnapply set ischecked='-1', isreturned='-1', note='"
				+ note + "' where id='" + id + "'";
		EntityLog.setDebug("OracleFeeList@Method:backReturnApply()=" + sql);
		return db.executeUpdate(sql);
	}

	public int checkReturnApply(String id) {
		dbpool db = new dbpool();
		String sql = "update entity_userfee_returnapply set ischecked='1', check_time=sysdate where id='"
				+ id + "'";
		EntityLog.setDebug("OracleFeeList@Method:checkReturnApply()=" + sql);
		return db.executeUpdate(sql);
	}

	public int payReturnApply(String id) {
		dbpool db = new dbpool();
		String sql = "update entity_userfee_returnapply set isreturned='1', return_time=sysdate where id='"
				+ id + "'";
		EntityLog.setDebug("OracleFeeList@Method:payReturnApply()=" + sql);
		return db.executeUpdate(sql);
	}

	public double getSiteTotalFee(String siteId) {
		double total = 0;
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select sum(type1_value)*80 as total from entity_userfee_level where user_id in "
				+ "(select id from entity_student_info where site_id='"
				+ siteId + "' and isgraduated='0')";
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next())
				total = rs.getDouble("total");
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return total;
	}

	public double getFeeSum(String startDate, String endDate, String siteId) {
		double sum = 0;
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select sum(fee_value) as fee_sum from entity_userfee_record where checked='1' and fee_type='"
				+ FeeType.CREDIT
				+ "' and payout_type='"
				+ PayoutType.DEPOSIT
				+ "'";
		if (startDate != null && !"".endsWith(startDate)
				&& !"null".equalsIgnoreCase(startDate)) {
			startDate += " 00:00:00";
			sql += " and date_time>=to_date('" + startDate
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (endDate != null && !"".endsWith(endDate)
				&& !"null".equalsIgnoreCase(endDate)) {
			endDate += " 23:59:59";
			sql += " and date_time<=to_date('" + endDate
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}

		if (siteId != null && !"".endsWith(siteId)
				&& !"null".equalsIgnoreCase(siteId)) {
			sql += " and user_id in (select id from entity_student_info where site_id='"
					+ siteId + "')";
		}

		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next())
				sum = rs.getDouble("fee_sum");
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return sum;
	}

	public double getFeeSum(String startDate, String endDate, String site_id,
			String grade_id, String edutype_id, String major_id,
			String invoice_status) {
		double sum = 0;
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select sum(fee_value) as fee_sum from entity_userfee_record where checked='1' and fee_type='"
				+ FeeType.CREDIT
				+ "' and payout_type='"
				+ PayoutType.DEPOSIT
				+ "'";
		if (startDate != null && !"".endsWith(startDate)
				&& !"null".equalsIgnoreCase(startDate)) {
			startDate += " 00:00:00";
			sql += " and date_time>=to_date('" + startDate
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (endDate != null && !"".endsWith(endDate)
				&& !"null".equalsIgnoreCase(endDate)) {
			endDate += " 23:59:59";
			sql += " and date_time<=to_date('" + endDate
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		List searchProperty = new ArrayList();
		if (site_id != null && !site_id.equals("") && !site_id.equals("null"))
			searchProperty.add(new SearchProperty("site_id", site_id, "="));
		if (grade_id != null && !grade_id.equals("")
				&& !grade_id.equals("null"))
			searchProperty.add(new SearchProperty("grade_id", grade_id, "="));
		if (edutype_id != null && !edutype_id.equals("")
				&& !edutype_id.equals("null"))
			searchProperty
					.add(new SearchProperty("edutype_id", edutype_id, "="));
		if (major_id != null && !major_id.equals("")
				&& !major_id.equals("null"))
			searchProperty.add(new SearchProperty("major_id", major_id, "="));

		sql += " and user_id in (select id from entity_student_info "
				+ Conditions.convertToCondition(searchProperty, null) + ")";

		if (invoice_status != null && !invoice_status.equals("")
				&& !invoice_status.equals("null"))
			sql += " and invoice_status ='" + invoice_status + "'";

		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next())
				sum = rs.getDouble("fee_sum");
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return sum;
	}

	public double getFeeSum(String feeType, String payoutType,
			String startDate, String endDate, String siteId) {
		double sum = 0;
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select sum(fee_value) as fee_sum from entity_userfee_record where checked='1' and fee_type='"
				+ feeType + "' and payout_type='" + payoutType + "'";
		if (startDate != null && !"".endsWith(startDate)
				&& !"null".equalsIgnoreCase(startDate)) {
			startDate += " 00:00:00";
			sql += " and date_time>=to_date('" + startDate
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (endDate != null && !"".endsWith(endDate)
				&& !"null".equalsIgnoreCase(endDate)) {
			endDate += " 23:59:59";
			sql += " and date_time<=to_date('" + endDate
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}

		if (siteId != null && !"".endsWith(siteId)
				&& !"null".equalsIgnoreCase(siteId)) {
			sql += " and user_id in (select id from entity_student_info where site_id='"
					+ siteId + "')";
		}

		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next())
				sum = rs.getDouble("fee_sum");
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return sum;
	}

	public int setUserFeeRecordPrinted(String id) {
		dbpool db = new dbpool();
		String sql = "update entity_userfee_record set invoice_status='1' , invoice_date = to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') where id='"
				+ id + "'";
		EntityLog.setDebug("OracleFeeList@Method:setUserFeeRecordPrinted()="
				+ sql);
		return db.executeUpdate(sql);
	}

	public double getUserTotalXueFee(String userId) {
		double standard = 0;
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select type3_value from entity_userfee_level where user_id='"
				+ userId + "'";
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				standard = rs.getDouble("type3_value");
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return standard;
	}

	public double getUserXueFeeByTime(String userId) {
		double standard = 0;
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select type5_value from entity_userfee_level where user_id='"
				+ userId + "'";
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				standard = rs.getDouble("type5_value");
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return standard;
	}

	public double getUserElectiveFee(String userId, String[] opencourse_ids,
			String[] select_opencourse_ids) {
		double standard = 0;
		String opencourse_str = "";
		String select_opencourse_str = "";
		for (int i = 0; i < opencourse_ids.length; i++) {
			opencourse_str += opencourse_ids[i] + ",";
		}
		if (opencourse_str.length() > 0)
			opencourse_str = opencourse_str.substring(0, opencourse_str
					.length() - 1);

		for (int i = 0; i < select_opencourse_ids.length; i++) {
			select_opencourse_str += select_opencourse_ids[i] + ",";
		}
		if (select_opencourse_str.length() > 0)
			select_opencourse_str = select_opencourse_str.substring(0,
					select_opencourse_str.length() - 1);
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select -sum(e.credit* f.creditfee) as value from (select nvl(a.credit,b.credit) as credit,a.course_id,a.course_name "
				+ "from (select tc.credit,tc.course_id,c.name as course_name from entity_course_active a,entity_course_info c,"
				+ "entity_teachplan_course tc,entity_teachplan_info i,entity_student_info s where a.id in ("
				+ select_opencourse_str
				+ ") and tc.course_id = c.id and a.course_id = c.id and i.edutype_id = s.edutype_id and i.major_id = s.major_id "
				+ "and i.grade_id = s.grade_id and s.id = '"
				+ userId
				+ "' and tc.teachplan_id = i.id)a,(select a.course_id as course_id,c.credit  "
				+ "from entity_course_info c,entity_course_active a where a.id in ("
				+ select_opencourse_str
				+ ") and a.course_id = c.id)b "
				+ "where b.course_id = a.course_id(+))e,(select type1_value as creditfee from entity_userfee_level where user_id = '"
				+ userId + "')f";
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				standard = rs.getDouble("value");
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return standard;
	}

	public List getSiteFeeStat(String startDate, String endDate, String siteId,
			String gradeId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();

		String timeCondition = "";
		if (startDate != null && !"".equals(startDate)) {
			timeCondition += "eur.date_time >=to_date('" + startDate
					+ "','yyyy-mm-dd')";
		}

		if (endDate != null && !"".equals(endDate)) {
			if (timeCondition.length() > 0)
				timeCondition += " and eur.date_time <=to_date('" + endDate
						+ "','yyyy-mm-dd')";
			else
				timeCondition += "eur.date_time <=to_date('" + endDate
						+ "','yyyy-mm-dd')";
		}

		if (timeCondition.length() > 0)
			timeCondition += " and ";

		String sql = "select d.id as site_id, d.name as site_name, sum(a.fee_value) as deposit_fee_sum, sum(b.fee_value)+ sum(k.fee_value) as consume_fee_sum, sum(c.rfee) as into_fee_sum from "
				+ "(select site_id,grade_id,round(sum(fee_value),2) as fee_value from (select eui.site_id, eui.grade_id,eur.id, user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno from entity_userfee_record eur, entity_student_info eui where "
				+ timeCondition
				+ " eui.id=eur.user_id and eur.payout_type='"
				+ PayoutType.DEPOSIT
				+ "' and eur.checked = '1' and fee_type='"
				+ FeeType.CREDIT
				+ "') group by site_id,grade_id) a, (select site_id,grade_id,nvl(round(sum(fee_value),2),0) as fee_value from (select eui.site_id,eur.id, eui.grade_id,user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno from entity_userfee_record eur, entity_student_info eui where "
				+ timeCondition
				+ " eui.id=eur.user_id and eur.payout_type='"
				+ PayoutType.CONSUME
				+ "' and eur.checked = '1' and fee_type='"
				+ FeeType.CREDIT
				+ "') group by site_id,grade_id) b, (select site_id,grade_id,nvl(round(sum(rfee),2),0) as rfee from (select eui.site_id,eur.id, eui.grade_id,eur.user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno, case when type3_value!='0' then fee_value * type2_value/type5_value else 0 end as rfee from entity_userfee_record eur, entity_student_info eui,(select user_id,type5_value,type2_value,type3_value from entity_userfee_level) eul where "
				+ timeCondition
				+ " eui.id=eur.user_id and eur.payout_type='"
				+ PayoutType.DEPOSIT
				+ "' and eur.checked = '1' and fee_type='"
				+ FeeType.CREDIT
				+ "' and eul.user_id=eur.user_id) group by site_id,grade_id) c, "
				+ "(select id, name from entity_site_info) d ,(select id,name from entity_grade_info) e ,(select site_id, grade_id, nvl(round(sum(fee_value), 2), 0) as fee_value from (select eui.site_id, eur.id, eui.grade_id, user_id, date_time, fee_value, fee_type, payout_type, note, checked, orderno from entity_userfee_record eur, entity_student_info eui where eui.id = eur.user_id and eur.payout_type = 'ROLLBACK' and eur.checked = '1' and fee_type = 'CREDIT') group by site_id, grade_id) k"
				+ " where a.site_id=b.site_id(+) "
				+ " and a.site_id=c.site_id(+) "
				+ " and a.site_id=d.id  and a.site_id = k.site_id(+)   and a.grade_id = k.grade_id(+) "
				+ " and a.grade_id = b.grade_id(+) and a.grade_id = c.grade_id(+) and a.grade_id = e.id ";

		if (siteId != null && !"".equals(siteId)
				&& !"null".equalsIgnoreCase(siteId)) {
			sql += " and a.site_id='" + siteId + "'";
		}
		if (gradeId != null && !"".equals(gradeId)) {
			sql += " and a.grade_id='" + gradeId + "'";
		}
		sql += " group by d.id ,d.name";
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				List subList = new ArrayList();
				subList.add(rs.getString("site_id"));
				subList.add(rs.getString("site_name"));
				subList.add(rs.getString("deposit_fee_sum"));
				subList.add(rs.getString("consume_fee_sum"));
				subList.add(rs.getString("into_fee_sum"));
				list.add(subList);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return list;
	}

	public List getGradeFeeStat(String startDate, String endDate,
			String siteId, String gradeId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();

		String timeCondition = "";
		if (startDate != null && !"".equals(startDate)) {
			timeCondition += "eur.date_time >=to_date('" + startDate
					+ "','yyyy-mm-dd')";
		}

		if (endDate != null && !"".equals(endDate)) {
			if (timeCondition.length() > 0)
				timeCondition += " and eur.date_time <=to_date('" + endDate
						+ "','yyyy-mm-dd')";
			else
				timeCondition += "eur.date_time <=to_date('" + endDate
						+ "','yyyy-mm-dd')";
		}

		if (timeCondition.length() > 0)
			timeCondition += " and ";

		//޸ԭû˷Ѽ¼ԭͳѡγ̵ѽΪѡѽ˷ѽ
		String sql = "select d.id as site_id, d.name as site_name, e.id as grade_id,e.name as grade_name,sum(a.fee_value) as deposit_fee_sum,  sum(b.fee_value)+sum(k.fee_value) as consume_fee_sum, sum(c.rfee) as into_fee_sum from "
				+ "(select site_id,grade_id,round(sum(fee_value),2) as fee_value from (select eui.site_id, eui.grade_id,eur.id, user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno from entity_userfee_record eur, entity_student_info eui where "
				+ timeCondition
				+ " eui.id=eur.user_id and eur.payout_type='"
				+ PayoutType.DEPOSIT
				+ "' and eur.checked = '1' and fee_type='"
				+ FeeType.CREDIT
				+ "') group by site_id,grade_id) a, (select site_id,grade_id,nvl(round(sum(fee_value),2),0) as fee_value from (select eui.site_id,eur.id, eui.grade_id,user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno from entity_userfee_record eur, entity_student_info eui where "
				+ timeCondition
				+ " eui.id=eur.user_id and eur.payout_type='"
				+ PayoutType.CONSUME
				+ "' and eur.checked = '1' and fee_type='"
				+ FeeType.CREDIT
				+ "') group by site_id,grade_id) b, (select site_id,grade_id,nvl(round(sum(rfee),2),0) as rfee from (select eui.site_id,eur.id, eui.grade_id,eur.user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno, case when type3_value!='0' then fee_value * type2_value/type3_value else 0 end as rfee from entity_userfee_record eur, entity_student_info eui,(select user_id,type2_value,type5_value,type3_value from entity_userfee_level) eul where "
				+ timeCondition
				+ " eui.id=eur.user_id and eur.payout_type='"
				+ PayoutType.DEPOSIT
				+ "' and eur.checked = '1' and fee_type='"
				+ FeeType.CREDIT
				+ "' and eul.user_id=eur.user_id) group by site_id,grade_id) c, "
				+ "(select id, name from entity_site_info) d ,(select id,name from entity_grade_info) e,(select site_id, grade_id, nvl(round(sum(fee_value), 2), 0) as fee_value from (select eui.site_id, eur.id, eui.grade_id, user_id, date_time, fee_value, fee_type, payout_type, note, checked, orderno from entity_userfee_record eur, entity_student_info eui where eui.id = eur.user_id and eur.payout_type = '"+PayoutType.ROLLBACK+"' and eur.checked = '1' and fee_type = 'CREDIT') group by site_id, grade_id) k"
				+ " where a.site_id=b.site_id(+) "
				+ " and a.site_id=c.site_id(+) "
				+ " and a.site_id=d.id  and a.site_id = k.site_id(+)   and a.grade_id = k.grade_id(+) "
				+ " and a.grade_id = b.grade_id(+) and a.grade_id = c.grade_id(+) and a.grade_id = e.id";

		if (siteId != null && !"".equals(siteId)
				&& !"null".equalsIgnoreCase(siteId)) {
			sql += " and a.site_id='" + siteId + "'";
		}
		if (gradeId != null && !"".equals(gradeId)) {
			sql += " and a.grade_id='" + gradeId + "'";
		}
		sql += " group by d.id ,d.name,e.id,e.name";
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				List subList = new ArrayList();
				subList.add(rs.getString("site_id"));
				subList.add(rs.getString("site_name"));
				subList.add(rs.getString("deposit_fee_sum"));
				subList.add(rs.getString("consume_fee_sum"));
				subList.add(rs.getString("into_fee_sum"));
				subList.add(rs.getString("grade_id"));
				subList.add(rs.getString("grade_name"));
				list.add(subList);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return list;
	}

	public List getStudentFeeStat(String startDate, String endDate,
			String siteId, String gradeId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();

		String timeCondition = "";
		if (startDate != null && !"".equals(startDate)) {
			timeCondition += "eur.date_time >=to_date('" + startDate
					+ "','yyyy-mm-dd')";
		}

		if (endDate != null && !"".equals(endDate)) {
			if (timeCondition.length() > 0)
				timeCondition += " and eur.date_time <=to_date('" + endDate
						+ "','yyyy-mm-dd')";
			else
				timeCondition += "eur.date_time <=to_date('" + endDate
						+ "','yyyy-mm-dd')";
		}

		if (timeCondition.length() > 0)
			timeCondition += " and ";

		//޸ԭû˷Ѽ¼ԭͳѡγ̵ѽΪѡѽ˷ѽ
		String sql = "select i.*, j.type1_value, j.type2_value, j.type3_value, j.type4_value, j.type5_value  from (select d.id as site_id,   d.name as site_name,   e.id as grade_id,   e.name as grade_name,   h.id as stu_id,   h.name as stu_name,   h.reg_no as reg_no,   h.id_card,   h.edutype_id,   h.edutype_name,   h.major_id,   h.major_name,   sum(a.fee_value) as deposit_fee_sum,     nvl(sum(b.fee_value),0)+nvl(sum(k.fee_value),0) as consume_fee_sum,   sum(c.rfee) as into_fee_sum    from (select site_id,     grade_id,     esid,     round(sum(fee_value), 2) as fee_value      from (select eui.site_id, eui.grade_id, eui.id as esid, eur.id, user_id, date_time, fee_value, fee_type, payout_type, note, checked, orderno  from entity_userfee_record eur, entity_student_info   eui where "
				+ timeCondition
				+ " eui.id=eur.user_id and eur.payout_type='"
				+ PayoutType.DEPOSIT
				+ "' and eur.checked = '1' and fee_type='"
				+ FeeType.CREDIT
				+ "') group by site_id,grade_id,esid) a, (select site_id,grade_id,esid,nvl(round(sum(fee_value),2),0) as fee_value from (select eui.site_id,eur.id, eui.grade_id,eui.id as esid,user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno from entity_userfee_record eur, entity_student_info eui where "
				+ timeCondition
				+ " eui.id=eur.user_id and eur.payout_type='"
				+ PayoutType.CONSUME
				+ "' and eur.checked = '1' and fee_type='"
				+ FeeType.CREDIT
				+ "') group by site_id,grade_id,esid) b, (select user_id, nvl(round(sum(fee_value), 2), 0) as fee_value from entity_userfee_record where payout_type = '"+PayoutType.ROLLBACK+"' and checked = '1' and fee_type = 'CREDIT' group by  user_id) k ,(select site_id,grade_id,esid,nvl(round(sum(rfee),2),0) as rfee from (select eui.site_id,eur.id, eui.grade_id,eui.id as esid,eur.user_id,date_time,fee_value,fee_type,payout_type,note,checked,orderno, case when type3_value!='0' then fee_value * type2_value/type3_value else 0 end as rfee from entity_userfee_record eur, entity_student_info eui,(select user_id,type2_value,type5_value,type3_value from entity_userfee_level) eul where "
				+ timeCondition
				+ " eui.id=eur.user_id and eur.payout_type='"
				+ PayoutType.DEPOSIT
				+ "' and eur.checked = '1' and fee_type='"
				+ FeeType.CREDIT
				+ "' and eul.user_id = eur.user_id)   group by site_id, grade_id, esid) c, (select id, name from entity_site_info) d, (select id, name from entity_grade_info) e, (select esi.id, esi.name, esi.id_card, esi.edutype_id, f.name as edutype_name, esi.major_id as major_id, g.name as major_name, esi.reg_no    from entity_student_info esi, (select id, name from entity_edu_type) f, (select id, name from entity_major_info) g   where esi.edutype_id = f.id(+) and esi.major_id = g.id(+)) h where a.site_id = b.site_id(+) and a.site_id = c.site_id(+) and a.site_id = d.id and a.grade_id = b.grade_id(+) and a.grade_id = c.grade_id(+) and a.grade_id = e.id and a.esid = b.esid(+) and a.esid = k.user_id(+) and a.esid = c.esid(+) and a.esid = h.id";

		if (siteId != null && !"".equals(siteId)
				&& !"null".equalsIgnoreCase(siteId)) {
			sql += " and a.site_id='" + siteId + "'";
		}
		if (gradeId != null && !"".equals(gradeId)) {
			sql += " and a.grade_id='" + gradeId + "'";
		}
		sql += " group by d.id, d.name, e.id, e.name, h.id, h.name, h.reg_no, h.id_card, h.edutype_id, h.edutype_name, h.major_id, h.major_name) i, (select site_id,  grade_id,  major_id,  edutype_id,  type1_value,  type2_value,  type3_value,  type4_value,  type5_value from (select site_id, grade_id, major_id, edutype_id, fl.type1_value as type1_value, fl.type2_value as type2_value, fl.type3_value as type3_value, fl.type4_value as type4_value, fl.type5_value as type5_value from entity_fee_level fl)) j  where i.site_id = j.site_id(+) and i.grade_id = j.grade_id(+) and i.edutype_id = j.edutype_id(+) and i.major_id = j.major_id(+)";
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				List subList = new ArrayList();
				subList.add(rs.getString("site_id"));
				subList.add(rs.getString("site_name"));
				subList.add(rs.getString("grade_id"));
				subList.add(rs.getString("grade_name"));
				subList.add(rs.getString("stu_id"));
				subList.add(rs.getString("stu_name"));
				subList.add(rs.getString("reg_no"));
				subList.add(rs.getString("id_card"));
				subList.add(rs.getString("edutype_id"));
				subList.add(rs.getString("edutype_name"));
				subList.add(rs.getString("major_id"));
				subList.add(rs.getString("major_name"));
				subList.add(rs.getString("deposit_fee_sum"));
				subList.add(rs.getString("consume_fee_sum"));
				subList.add(rs.getString("into_fee_sum"));
				subList.add(rs.getString("type1_value"));
				subList.add(rs.getString("type2_value"));
				subList.add(rs.getString("type3_value"));
				subList.add(rs.getString("type4_value"));
				subList.add(rs.getString("type5_value"));
				list.add(subList);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return list;
	}
}
