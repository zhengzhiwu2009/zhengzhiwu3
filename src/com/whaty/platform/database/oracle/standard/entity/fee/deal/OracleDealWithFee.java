/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.fee.deal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.fee.exception.FeeException;
import com.whaty.platform.entity.fee.deal.DealWithFee;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

/**
 * @author chenjian
 * 
 */
public class OracleDealWithFee implements DealWithFee {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.deal.DealWithFee#dealWith(java.lang.String,
	 *      double, java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean dealWith(String recordId, String userId, double feeValue,
			String feeType, String payoutType, String note, boolean needCheck)
			throws FeeException {
		if (feeType == null)
			feeType = "";
		if (payoutType == null)
			payoutType = "";
		if (note == null)
			note = "";
		String checked = "0";
		if (!needCheck) {
			checked = "1";
		}
		dbpool db = new dbpool();
		List sqlList = new ArrayList();
		String sql1 = "insert into entity_userfee_record(id,user_id,"
				+ "fee_value,fee_type,payout_type,checked,note)" + "values ('"
				+ recordId + "','" + userId + "'," + feeValue + "," + "'"
				+ feeType + "','" + payoutType + "','" + checked + "','" + note
				+ "')";
		sqlList.add(sql1);
		int i = db.executeUpdate(sql1);
		UserAddLog.setDebug("OracleDealWithFee.dealWith(String recordId,String userId,double feeValue,String feeType,String payoutType,String note,boolean needCheck) Sql=" + sql1 + " COUNT=" + i + " DATE=" + new Date());
		if (i > 0)
			return true;
		else
			return false;
	}

	public boolean dealWith(String recordId, String userId, double feeValue,
			String feeType, String payoutType, String note, boolean needCheck,
			String orderNo) throws FeeException {
		if (feeType == null)
			feeType = "";
		if (payoutType == null)
			payoutType = "";
		if (note == null)
			note = "";
		String checked = "0";
		if (!needCheck) {
			checked = "1";
		}
		dbpool db = new dbpool();
		List sqlList = new ArrayList();
		String sql1 = "insert into entity_userfee_record(id,user_id,"
				+ "fee_value,fee_type,payout_type,checked,note,orderno)"
				+ "values ('" + recordId + "','" + userId + "'," + feeValue
				+ "," + "'" + feeType + "','" + payoutType + "','" + checked
				+ "','" + note + "','" + orderNo + "')";
		sqlList.add(sql1);
		int i = db.executeUpdate(sql1);
		UserAddLog.setDebug("OracleDealWithFee.dealWith(String recordId,String userId,double feeValue,String feeType,String payoutType,String note,boolean needCheck,String orderNo) Sql=" + sql1 + " COUNT=" + i + " DATE=" + new Date());
		if (i > 0)
			return true;
		else
			return false;
	}

	public boolean checkFee(String id) throws FeeException {
		dbpool db = new dbpool();
		String sql = "update entity_userfee_record set checked=1 where id='"
				+ id + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDealWithFee.checkFee(String id) Sql=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getCheckStatusByOrderNo(String orderNo) throws FeeException {
		dbpool db = new dbpool();
		String sql = "select orderNo from entity_userfee_record where orderno = '"
				+ orderNo + "' and checked = '1'";
		if (db.countselect(sql) > 0)
			return true;
		else
			return false;
	}

	public String getNewRecordId() throws FeeException {
		dbpool db = new dbpool();
		int i = db.getSequenceNextId("s_entity_fee_id");
		return new Integer(i).toString();
	}

	public String getOrderNo(String regno) throws FeeException {
		dbpool db = new dbpool();
		// int i = db.getSequenceCurId("s_fee_order_id");
		int i = db.getSequenceNextId("s_fee_order_id");
		if (i < 1000)
			i = i + 1000;
		String no = new Integer(i).toString();
		Date date = new Date();
		StrManage strManage = StrManageFactory.creat();
		String orderNo;
		try {
			orderNo = regno + "" + strManage.dateToStr(date, "yyyyMMdd") + ""
					+ no.substring(no.length() - 3, no.length());
		} catch (WhatyUtilException e) {
			throw new FeeException("getOrderNo error!");
		}
		return orderNo;
	}

	public boolean checkFeeByOrderNo(String orderNo) throws FeeException {
		dbpool db = new dbpool();
		String sql = "update entity_userfee_record set checked=1 where orderno='"
				+ orderNo + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDealWithFee.checkFeeByOrderNo(String orderNo) Sql=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean repealByOrderNo(String orderNo) throws FeeException {
		dbpool db = new dbpool();
		String sql = "update entity_userfee_record set checked=3 where orderno='"
				+ orderNo + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDealWithFee.repealByOrderNo(String orderNo) Sql=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean unCheckFeeByOrderNo(String orderNo) throws FeeException {
		dbpool db = new dbpool();
		String sql = "update entity_userfee_record set checked=0 where orderno='"
				+ orderNo + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleDealWithFee.unCheckFeeByOrderNo(String orderNo) Sql=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void deleteByOrderNo(String orderNo) throws FeeException {
		dbpool db = new dbpool();
		String sql = "delete from  entity_userfee_record where  checked=0 and orderno='"
				+ orderNo + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleDealWithFee.deleteByOrderNo(String orderNo) Sql=" + sql + " COUNT=" + i + " DATE=" + new Date());

	}

}
