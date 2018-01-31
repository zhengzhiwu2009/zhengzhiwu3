/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.fee.deal;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.User;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.fee.deal.UserFee;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.EntityLog;

/**
 * @author chenjian
 * 
 */
public class OracleUserFee extends UserFee {

	public OracleUserFee() {
		super();
	}

	public OracleUserFee(User user) {
		this.setUser(user);
	}

	public double getUserTotalFee() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		double fee_value = 0;
		sql = "select sum(fee_value) as fee_value from entity_userfee_record where user_id='"
				+ this.getUser().getId() + "' and checked='1'";

		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				fee_value = rs.getDouble("fee_value");
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleSite(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return fee_value;
	}
	
	public double getUserDepositTotalFee() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		double fee_value = 0;
		sql = "select sum(fee_value) as fee_value from entity_userfee_record where user_id='"
				+ this.getUser().getId() + "' and payout_type='DEPOSIT' and checked='1'";

		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				fee_value = rs.getDouble("fee_value");
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleSite(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return fee_value;
	}
	
	public double getUserTotalDeposit(String feeType) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		double fee_value = 0;
		sql = "select sum(fee_value) as fee_value from entity_userfee_record where user_id='"
				+ this.getUser().getId() + "' and checked='1' and payout_type='DEPOSIT'";
		if(feeType != null && !feeType.equals("") && !feeType.equalsIgnoreCase("null"))
			sql += " and fee_type='" + feeType + "'";

		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				fee_value = rs.getDouble("fee_value");
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleSite(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return fee_value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.deal.UserFee#getUserFeeRecord(com.whaty.platform.util.Page,
	 *      java.lang.String, java.lang.String)
	 */
	public List getUserFeeRecord(Page page, String feeType, String payoutType)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("fee_type", feeType));
		searchProperty.add(new SearchProperty("payout_type", payoutType));
		return feeList.searchFeeRecord(page, searchProperty, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.deal.UserFee#getTotalFee(java.lang.String,
	 *      java.lang.String)
	 */
	public double getTotalFee(String feeType, String payoutType) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		boolean hasCondition = false;
		double totalFee = 0;
		String sql = "select sum(fee_value) as total_fee from entity_userfee_record ";
		String condition = "";
		if (feeType == null || feeType.length() <= 0
				|| feeType.trim().equalsIgnoreCase("null")) {
		} else {
			condition = " fee_type='" + feeType + "'";
			hasCondition = true;
		}
		if (payoutType == null || payoutType.length() <= 0
				|| payoutType.trim().equalsIgnoreCase("null")) {
		} else {
			if (hasCondition) {
				condition = condition + " and payoutType='" + payoutType + "'";
			} else {
				condition = " payoutType='" + payoutType + "'";
			}
			hasCondition = true;
		}
		if (hasCondition) {
			sql = sql + " where " + condition;
		}

		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				totalFee = rs.getDouble("total_fee");
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("getTotalFee error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return totalFee;
	}

}
