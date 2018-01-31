/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.fee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.User;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleBasicActivityList;
import com.whaty.platform.database.oracle.standard.entity.fee.deal.OracleDealWithFee;
import com.whaty.platform.database.oracle.standard.entity.fee.deal.OracleFeeList;
import com.whaty.platform.database.oracle.standard.entity.fee.deal.OracleOtherFeeType;
import com.whaty.platform.database.oracle.standard.entity.fee.deal.OracleUserFee;
import com.whaty.platform.database.oracle.standard.entity.fee.deal.OracleUserFeeReturnApply;
import com.whaty.platform.database.oracle.standard.entity.fee.exception.FeeException;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamList;
import com.whaty.platform.entity.fee.FeeManage;
import com.whaty.platform.entity.fee.FeeManagerPriv;
import com.whaty.platform.entity.fee.deal.FeeType;
import com.whaty.platform.entity.fee.deal.OtherFeeType;
import com.whaty.platform.entity.fee.deal.PayoutType;
import com.whaty.platform.entity.fee.deal.UserFeeRecord;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;

/**
 * @author chenjian
 * 
 */
public class OracleFeeManage implements FeeManage {

	FeeManagerPriv feeManagerPriv;

	public OracleFeeManage() {

	}

	public OracleFeeManage(FeeManagerPriv feeManagerPriv) {
		this.feeManagerPriv = feeManagerPriv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.FeeManage#dealWith(java.lang.String,
	 *      double, java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean dealWith(String userId, double feeValue, String feeType,
			String payoutType, String note, boolean needCheck)
			throws PlatformException {
		if (feeManagerPriv.addFee == 1) {
			OracleDealWithFee dealFee = new OracleDealWithFee();
			String recordId;
			try {
				recordId = dealFee.getNewRecordId();

				return dealFee.dealWith(recordId, userId, feeValue, feeType,
						payoutType, note, needCheck);
			} catch (FeeException e) {
				throw new PlatformException("dealWith error!(" + e.toString()
						+ ")");
			}
		} else {
			throw new PlatformException("��û�����ѧ�ѵ�Ȩ��!");
		}
	}

	public double getCurrentDeposit(String userId) throws PlatformException {
		User user = new User();
		user.setId(userId);
		OracleUserFee userFee = new OracleUserFee(user);
		return userFee.getUserTotalDeposit(FeeType.CREDIT);
	}

	public double getUserTotalFee(String userId) throws PlatformException {
		User user = new User();
		user.setId(userId);
		OracleUserFee userFee = new OracleUserFee(user);
		return userFee.getUserTotalFee();
	}
	public double getUserDepositTotalFee(String userId) throws PlatformException {
		User user = new User();
		user.setId(userId);
		OracleUserFee userFee = new OracleUserFee(user);
		return userFee.getUserDepositTotalFee();
	}

	public List getUserFeeRecord(Page page, String userId, String feeType,
			String payoutType, String startDate, String endDate)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("user_id", userId));
		searchProperty.add(new SearchProperty("fee_type", feeType));
		searchProperty.add(new SearchProperty("payout_type", payoutType));
		searchProperty.add(new SearchProperty("date_time", startDate, "D>="));
		searchProperty.add(new SearchProperty("date_time", endDate, "D<="));
		searchProperty.add(new SearchProperty("checked", "1", "=num"));
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("date_time"));
		return feeList.searchFeeRecord(page, searchProperty, orderProperty);
	}

	public int getUserFeeRecordNum(String userId, String feeType,
			String payoutType, String startDate, String endDate)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("user_id", userId));
		searchProperty.add(new SearchProperty("fee_type", feeType));
		searchProperty.add(new SearchProperty("payout_type", payoutType));
		searchProperty.add(new SearchProperty("date_time", startDate, "D>="));
		searchProperty.add(new SearchProperty("date_time", endDate, "D<="));
		searchProperty.add(new SearchProperty("checked", "1", "=num"));
		return feeList.searchFeeRecordNum(searchProperty);
	}

	public List searchUserFeeRecord(Page page, String loginId, String name,
			String feeType, String payoutType, String startDate,
			String endDate, String site_id) throws PlatformException {
		if (feeManagerPriv.getFeeByTime == 1) {
			OracleFeeList feeList = new OracleFeeList();
			List searchProperty = new ArrayList();
			if (loginId != null && !loginId.equals("")
					&& !loginId.equals("null"))
				searchProperty.add(new SearchProperty("login_id", loginId,
						"like"));
			if (name != null && !name.equals("") && !name.equals("null"))
				searchProperty.add(new SearchProperty("name", name, "like"));
			if (feeType != null && !feeType.equals("")
					&& !feeType.equals("null"))
				searchProperty
						.add(new SearchProperty("fee_type", feeType, "="));
			if (payoutType != null && !payoutType.equals("")
					&& !payoutType.equals("null"))
				searchProperty.add(new SearchProperty("payout_type",
						payoutType, "="));
			if (startDate != null && !startDate.equals("")
					&& !startDate.equals("null"))
				searchProperty.add(new SearchProperty("date_time", startDate,
						"D>="));
			if (endDate != null && !endDate.equals("")
					&& !endDate.equals("null"))
				searchProperty.add(new SearchProperty("date_time", endDate,
						"D<="));
			searchProperty.add(new SearchProperty("checked", "1", "="));
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null"))
				searchProperty.add(new SearchProperty("site_id", site_id, "="));
			List orderProperty = new ArrayList();
			orderProperty
					.add(new OrderProperty("date_time", OrderProperty.DESC));
			return feeList.searchFeeRecord(page, searchProperty, orderProperty);
		} else {
			throw new PlatformException("��û��ʱ��β�ѯѧ�ѵ�Ȩ��!");
		}
	}

	public int searchUserFeeRecordNum(String loginId, String name,
			String feeType, String payoutType, String startDate,
			String endDate, String site_id) throws PlatformException {
		if (feeManagerPriv.getFeeByTime == 1) {
			OracleFeeList feeList = new OracleFeeList();
			List searchProperty = new ArrayList();
			if (loginId != null && !loginId.equals("")
					&& !loginId.equals("null"))
				searchProperty.add(new SearchProperty("login_id", loginId,
						"like"));
			if (name != null && !name.equals("") && !name.equals("null"))
				searchProperty.add(new SearchProperty("name", name, "like"));
			if (feeType != null && !feeType.equals("")
					&& !feeType.equals("null"))
				searchProperty
						.add(new SearchProperty("fee_type", feeType, "="));
			if (payoutType != null && !payoutType.equals("")
					&& !payoutType.equals("null"))
				searchProperty.add(new SearchProperty("payout_type",
						payoutType, "="));
			if (startDate != null && !startDate.equals("")
					&& !startDate.equals("null"))
				searchProperty.add(new SearchProperty("date_time", startDate,
						"D>="));
			if (endDate != null && !endDate.equals("")
					&& !endDate.equals("null"))
				searchProperty.add(new SearchProperty("date_time", endDate,
						"D<="));
			searchProperty.add(new SearchProperty("checked", "1", "="));
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null"))
				searchProperty.add(new SearchProperty("site_id", site_id, "="));
			return feeList.searchFeeRecordNum(searchProperty);
		} else {
			throw new PlatformException("��û��ʱ��β�ѯѧ�ѵ�Ȩ��!");
		}
	}

	public List searchUserFeeRecord(Page page, String loginId, String name,
			String feeType, String payoutType, String startDate,
			String endDate, String site_id, String grade_id, String edutype_id,
			String major_id, String invoice_status) throws PlatformException {
		if (feeManagerPriv.getFeeByTime == 1) {
			OracleFeeList feeList = new OracleFeeList();
			List searchProperty = new ArrayList();
			if (loginId != null && !loginId.equals("")
					&& !loginId.equals("null"))
				searchProperty.add(new SearchProperty("login_id", loginId,
						"like"));
			if (name != null && !name.equals("") && !name.equals("null"))
				searchProperty.add(new SearchProperty("name", name, "like"));
			if (feeType != null && !feeType.equals("")
					&& !feeType.equals("null"))
				searchProperty
						.add(new SearchProperty("fee_type", feeType, "="));
			if (payoutType != null && !payoutType.equals("")
					&& !payoutType.equals("null"))
				searchProperty.add(new SearchProperty("payout_type",
						payoutType, "="));
			if (startDate != null && !startDate.equals("")
					&& !startDate.equals("null"))
				searchProperty.add(new SearchProperty("date_time", startDate,
						"D>="));
			if (endDate != null && !endDate.equals("")
					&& !endDate.equals("null"))
				searchProperty.add(new SearchProperty("date_time", endDate,
						"D<="));
			searchProperty.add(new SearchProperty("checked", "1", "="));
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null"))
				searchProperty.add(new SearchProperty("site_id", site_id, "="));
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null"))
				searchProperty
						.add(new SearchProperty("grade_id", grade_id, "="));
			if (edutype_id != null && !edutype_id.equals("")
					&& !edutype_id.equals("null"))
				searchProperty.add(new SearchProperty("edutype_id", edutype_id,
						"="));
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchProperty
						.add(new SearchProperty("major_id", major_id, "="));
			if (invoice_status != null && !invoice_status.equals("")
					&& !invoice_status.equals("null"))
				searchProperty.add(new SearchProperty("invoice_status",
						invoice_status, "="));
			List orderProperty = new ArrayList();
			orderProperty
					.add(new OrderProperty("date_time", OrderProperty.DESC));
			return feeList.searchFeeRecord(page, searchProperty, orderProperty);
		} else {
			throw new PlatformException("��û��ʱ��β�ѯѧ�ѵ�Ȩ��!");
		}
	}

	public int searchUserFeeRecordNum(String loginId, String name,
			String feeType, String payoutType, String startDate,
			String endDate, String site_id, String grade_id, String edutype_id,
			String major_id, String invoice_status) throws PlatformException {
		if (feeManagerPriv.getFeeByTime == 1) {
			OracleFeeList feeList = new OracleFeeList();
			List searchProperty = new ArrayList();
			if (loginId != null && !loginId.equals("")
					&& !loginId.equals("null"))
				searchProperty.add(new SearchProperty("login_id", loginId,
						"like"));
			if (name != null && !name.equals("") && !name.equals("null"))
				searchProperty.add(new SearchProperty("name", name, "like"));
			if (feeType != null && !feeType.equals("")
					&& !feeType.equals("null"))
				searchProperty
						.add(new SearchProperty("fee_type", feeType, "="));
			if (payoutType != null && !payoutType.equals("")
					&& !payoutType.equals("null"))
				searchProperty.add(new SearchProperty("payout_type",
						payoutType, "="));
			if (startDate != null && !startDate.equals("")
					&& !startDate.equals("null"))
				searchProperty.add(new SearchProperty("date_time", startDate,
						"D>="));
			if (endDate != null && !endDate.equals("")
					&& !endDate.equals("null"))
				searchProperty.add(new SearchProperty("date_time", endDate,
						"D<="));
			searchProperty.add(new SearchProperty("checked", "1", "="));
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null"))
				searchProperty.add(new SearchProperty("site_id", site_id, "="));
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null"))
				searchProperty
						.add(new SearchProperty("grade_id", grade_id, "="));
			if (edutype_id != null && !edutype_id.equals("")
					&& !edutype_id.equals("null"))
				searchProperty.add(new SearchProperty("edutype_id", edutype_id,
						"="));
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchProperty
						.add(new SearchProperty("major_id", major_id, "="));
			if (invoice_status != null && !invoice_status.equals("")
					&& !invoice_status.equals("null"))
				searchProperty.add(new SearchProperty("invoice_status",
						invoice_status, "="));
			return feeList.searchFeeRecordNum(searchProperty);
		} else {
			throw new PlatformException("��û��ʱ��β�ѯѧ�ѵ�Ȩ��!");
		}
	}

	public List searchUnConfirmUserFeeRecord(Page page, String loginId,
			String name, String feeType, String payoutType, String startDate,
			String endDate, String site_id, String order_no)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("fee_type", feeType));
		searchProperty.add(new SearchProperty("payout_type", payoutType));
		searchProperty.add(new SearchProperty("date_time", startDate, "D>="));
		searchProperty.add(new SearchProperty("date_time", endDate, "D<="));
		searchProperty.add(new SearchProperty("checked", "0", "=num"));
		if (site_id != null && !site_id.equals("") && !site_id.equals("null"))
			searchProperty.add(new SearchProperty("site_id", site_id, "="));
		searchProperty.add(new SearchProperty("orderno", order_no, "="));
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("date_time", OrderProperty.DESC));
		return feeList.searchUnConfirmFeeRecord(page, searchProperty,
				orderProperty);
	}

	public int searchUnConfirmUserFeeRecordNum(String loginId, String name,
			String feeType, String payoutType, String startDate,
			String endDate, String site_id, String order_no)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("fee_type", feeType));
		searchProperty.add(new SearchProperty("payout_type", payoutType));
		searchProperty.add(new SearchProperty("date_time", startDate, "D>="));
		searchProperty.add(new SearchProperty("date_time", endDate, "D<="));
		searchProperty.add(new SearchProperty("checked", "0", "=num"));
		if (site_id != null && !site_id.equals("") && !site_id.equals("null"))
			searchProperty.add(new SearchProperty("site_id", site_id, "="));
		searchProperty.add(new SearchProperty("orderno", order_no, "="));
		return feeList.searchUnConfirmFeeRecordNum(searchProperty);
	}

	public List searchTotalUserFeeRecord(Page page, String loginId,
			String name, String feeType, String payoutType, String startDate,
			String endDate, String site_id, String order_no)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("fee_type", feeType));
		searchProperty.add(new SearchProperty("payout_type", payoutType));
		searchProperty.add(new SearchProperty("date_time", startDate, "D>="));
		searchProperty.add(new SearchProperty("date_time", endDate, "D<="));
		searchProperty.add(new SearchProperty("checked", "1", "=num"));
		if (site_id != null && !site_id.equals("") && !site_id.equals("null"))
			searchProperty.add(new SearchProperty("site_id", site_id, "="));
		searchProperty.add(new SearchProperty("orderno", order_no, "="));
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("date_time", OrderProperty.DESC));
		return feeList.searchUnConfirmFeeRecord(page, searchProperty,
				orderProperty);
	}

	public int searchTotalUserFeeRecordNum(String loginId, String name,
			String feeType, String payoutType, String startDate,
			String endDate, String site_id, String order_no)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("fee_type", feeType));
		searchProperty.add(new SearchProperty("payout_type", payoutType));
		searchProperty.add(new SearchProperty("date_time", startDate, "D>="));
		searchProperty.add(new SearchProperty("date_time", endDate, "D<="));
		searchProperty.add(new SearchProperty("checked", "1", "=num"));
		if (site_id != null && !site_id.equals("") && !site_id.equals("null"))
			searchProperty.add(new SearchProperty("site_id", site_id, "="));
		searchProperty.add(new SearchProperty("orderno", order_no, "="));
		return feeList.searchUnConfirmFeeRecordNum(searchProperty);
	}

	public List getFeeStatBySemester(String semesterId)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		return feeList.getFeeStatBySemester(semesterId);
	}

	public List getSiteFeeStat(String startDate, String endDate, String siteId)
			throws PlatformException {
		if (feeManagerPriv.getSiteFeeStat == 1) {
			OracleFeeList feeList = new OracleFeeList();
			return feeList.getSiteFeeStat(startDate, endDate, siteId);
		} else {
			throw new PlatformException("�Բ�����û�в鿴��ѧվѧ��ͳ�Ƶ�Ȩ��!");
		}
	}

	public double getUserFeeStandard(String userId) throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		return feeList.getUserFeeStandard(userId);
	}

	public double getUserFeeStandard(String siteId, String majorId,
			String gradeId, String eduTypeId) throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchList = new ArrayList();

		if (siteId != null && !"".equals(siteId)
				&& !"null".equalsIgnoreCase(siteId))
			searchList.add(new SearchProperty("site_id", siteId, "="));

		if (majorId != null && !"".equals(majorId)
				&& !"null".equalsIgnoreCase(majorId))
			searchList.add(new SearchProperty("major_id", majorId, "="));

		if (gradeId != null && !"".equals(gradeId)
				&& !"null".equalsIgnoreCase(gradeId))
			searchList.add(new SearchProperty("grade_id", gradeId, "="));

		if (eduTypeId != null && !"".equals(eduTypeId)
				&& !"null".equalsIgnoreCase(eduTypeId))
			searchList.add(new SearchProperty("edutype_id", eduTypeId, "="));

		return feeList.getUserFeeStandard(searchList, null);
	}

	public List getUserFeeStandard(Page page, String siteId, String majorId,
			String gradeId, String eduTypeId) throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchList = new ArrayList();
		if (siteId != null && !"".equals(siteId)
				&& !"null".equalsIgnoreCase(siteId))
			searchList.add(new SearchProperty("site_id", siteId, "="));

		if (majorId != null && !"".equals(majorId)
				&& !"null".equalsIgnoreCase(majorId))
			searchList.add(new SearchProperty("major_id", majorId, "="));

		if (gradeId != null && !"".equals(gradeId)
				&& !"null".equalsIgnoreCase(gradeId))
			searchList.add(new SearchProperty("grade_id", gradeId, "="));

		if (eduTypeId != null && !"".equals(eduTypeId)
				&& !"null".equalsIgnoreCase(eduTypeId))
			searchList.add(new SearchProperty("edutype_id", eduTypeId, "="));

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(
				"site_name,grade_name,edutype_name,major_name"));
		return feeList.getUserFeeStandard(page, searchList, orderList);
	}

	public int getUserFeeStandardNum(String siteId, String majorId,
			String gradeId, String eduTypeId) throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchList = new ArrayList();

		if (siteId != null && !"".equals(siteId)
				&& !"null".equalsIgnoreCase(siteId))
			searchList.add(new SearchProperty("site_id", siteId, "="));

		if (majorId != null && !"".equals(majorId)
				&& !"null".equalsIgnoreCase(majorId))
			searchList.add(new SearchProperty("major_id", majorId, "="));

		if (gradeId != null && !"".equals(gradeId)
				&& !"null".equalsIgnoreCase(gradeId))
			searchList.add(new SearchProperty("grade_id", gradeId, "="));

		if (eduTypeId != null && !"".equals(eduTypeId)
				&& !"null".equalsIgnoreCase(eduTypeId))
			searchList.add(new SearchProperty("edutype_id", eduTypeId, "="));

		return feeList.getUserFeeStandardNum(searchList, null);
	}

	public int backReturnApply(String id, String note) throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		return feeList.backReturnApply(id, note);
	}

	public int checkReturnApply(String id) throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		return feeList.checkReturnApply(id);
	}

	public List getUserFeeReturnApplyList(Page page, String userId,
			String isChecked, String isReturned, String note)
			throws PlatformException {
		if (feeManagerPriv.getStuFeeReturnApply == 1) {
			OracleBasicActivityList activityList = new OracleBasicActivityList();
			List searchList = new ArrayList();
			if (userId != null && !"null".equalsIgnoreCase(userId)
					&& !"".equals(userId))
				searchList.add(new SearchProperty("user_id", userId, "="));
			if (isChecked != null && !"null".equalsIgnoreCase(isChecked)
					&& !"".equals(isChecked))
				searchList.add(new SearchProperty("ischecked", isChecked, "="));
			if (isReturned != null && !"null".equalsIgnoreCase(isReturned)
					&& !"".equals(isReturned))
				searchList
						.add(new SearchProperty("isreturned", isReturned, "="));
			if (note != null && !"null".equalsIgnoreCase(note)
					&& !"".equals(note))
				searchList.add(new SearchProperty("note", note, "like"));

			return activityList.getUserFeeReturnApplyList(page, searchList,
					null);
		} else {
			throw new PlatformException("��û�в鿴ѧ���˷������Ȩ��!");
		}
	}

	public int getUserFeeReturnApplyNum(String userId, String isChecked,
			String isReturned, String note) throws PlatformException {
		if (feeManagerPriv.getStuFeeReturnApply == 1) {
			OracleBasicActivityList activityList = new OracleBasicActivityList();
			List searchList = new ArrayList();
			if (userId != null && !"null".equalsIgnoreCase(userId)
					&& !"".equals(userId))
				searchList.add(new SearchProperty("user_id", userId, "="));
			if (isChecked != null && !"null".equalsIgnoreCase(isChecked)
					&& !"".equals(isChecked))
				searchList.add(new SearchProperty("ischecked", isChecked, "="));
			if (isReturned != null && !"null".equalsIgnoreCase(isReturned)
					&& !"".equals(isReturned))
				searchList
						.add(new SearchProperty("isreturned", isReturned, "="));
			if (note != null && !"null".equalsIgnoreCase(note)
					&& !"".equals(note))
				searchList.add(new SearchProperty("note", note, "like"));

			return activityList.getUserFeeReturnApplyNum(searchList);
		} else {
			throw new PlatformException("��û�в鿴ѧ���˷������Ȩ��!");
		}
	}

	public int payReturnApply(String id) throws PlatformException {
		OracleUserFeeReturnApply apply = new OracleUserFeeReturnApply(id);
		double amount = apply.getAmount();
		amount = 0 - amount;
		OracleFeeList feeList = new OracleFeeList();
		int i = feeList.payReturnApply(id);
		this.dealWith(apply.getUserId(), amount, FeeType.CREDIT,
				PayoutType.CONSUME, "ѧ�������˷�", false);
		return i;
	}

	public List getStudentFeeList(Page page, String site_id, String edutype_id,
			String major_id, String grade_id, String name, String reg_no,
			String id_card) throws PlatformException {
		return null;
	}

	public UserFeeRecord getUserFeeRecord(String orderno, String userId)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchProperty = new ArrayList();
		if (orderno != null && !orderno.equals("")
				&& !orderno.equalsIgnoreCase("null"))
			searchProperty.add(new SearchProperty("orderno", orderno));
		if (userId != null && !userId.equals("")
				&& !userId.equalsIgnoreCase("null"))
			searchProperty.add(new SearchProperty("user_id", userId));
		List list = feeList
				.searchUnConfirmFeeRecord(null, searchProperty, null);
		if (list != null && list.size() > 0)
			return (UserFeeRecord) list.get(0);
		else
			return null;
	}

	public UserFeeRecord getUserFeeRecord(String id) throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		List searchProperty = new ArrayList();
		if (id != null && !id.equals("") && !id.equalsIgnoreCase("null"))
			searchProperty.add(new SearchProperty("id", id, "="));
		List list = feeList.searchFeeRecord(null, searchProperty, null);
		if (list != null && list.size() > 0)
			return (UserFeeRecord) list.get(0);
		else
			return null;
	}

	public double getSiteTotalStandardFee(String siteId)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		return feeList.getSiteTotalFee(siteId);
	}

	public double getFeeSum(String startDate, String endDate, String siteId)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();

		return feeList.getFeeSum(startDate, endDate, siteId);
	}

	public double getFeeSum(String startDate, String endDate, String site_id,
			String grade_id, String edutype_id, String major_id,
			String invoice_status) throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();

		return feeList.getFeeSum(startDate, endDate, site_id, grade_id,
				edutype_id, major_id, invoice_status);
	}

	public double getFeeSum(String feeType, String payoutType,
			String startDate, String endDate, String siteId)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();

		return feeList.getFeeSum(feeType, payoutType, startDate, endDate,
				siteId);
	}

	public int setUserFeeRecordPrinted(String id) throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();

		return feeList.setUserFeeRecordPrinted(id);
	}

	public int addOtherFeeType(HttpServletRequest request)
			throws PlatformException {
		if (feeManagerPriv.addOtherFeeType == 1) {
			OracleOtherFeeType otherFeeType = new OracleOtherFeeType();
			otherFeeType.setId(request.getParameter("id"));
			otherFeeType.setName(request.getParameter("name"));
			return otherFeeType.add();

		} else {
			throw new PlatformException("��û������ӷ����͵�Ȩ��");
		}
	}

	public int deleteOtherFeeType(HttpServletRequest request)
			throws PlatformException {
		if (feeManagerPriv.deleteOtherFeeType == 1) {
			OracleOtherFeeType otherFeeType = new OracleOtherFeeType(request
					.getParameter("id"));
			return otherFeeType.delete();
		} else {
			throw new PlatformException("��û��ɾ���ӷ����͵�Ȩ��");
		}
	}

	public OtherFeeType getOtherFeeType(HttpServletRequest request)
			throws PlatformException {
		if (feeManagerPriv.getOtherFeeType == 1) {
			return new OracleOtherFeeType(request.getParameter("id"));
		} else {
			throw new PlatformException("��û�в鿴�ӷ����͵�Ȩ��");
		}
	}

	public List getOtherFeeTypes(Page page, HttpServletRequest request)
			throws PlatformException {
		if (feeManagerPriv.getOtherFeeType == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			orderList.add(new OrderProperty("id"));
			return testList.getOtherFeeTypes(page, searchList, orderList);
		} else {
			throw new PlatformException("��û�в鿴�ӷ����͵�Ȩ��");
		}
	}

	public int getOtherFeeTypesNum(HttpServletRequest request)
			throws PlatformException {
		if (feeManagerPriv.getOtherFeeType == 1) {
			OracleExamList testList = new OracleExamList();
			List searchList = new ArrayList();
			return testList.getOtherFeeTypesNum(searchList);
		} else {
			throw new PlatformException("��û�в鿴�ӷ����͵�Ȩ��");
		}
	}

	public int updateOtherFeeType(HttpServletRequest request)
			throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getUserTotalXueFee(String userId) throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		return feeList.getUserTotalXueFee(userId);
	}

	public double getUserXueFeeByTime(String userId) throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		return feeList.getUserXueFeeByTime(userId);
	}

	public double getUserElectiveFee(String userId, HttpServletRequest request)
			throws PlatformException {
		OracleFeeList feeList = new OracleFeeList();
		String[] opencourse_ids = request.getParameterValues("opencourse_id");
		String[] major_xuanxiucourse_id = request
				.getParameterValues("major_xuanxiucourse_id");
		String[] pub_xuanxiucourse_id = request
				.getParameterValues("pub_xuanxiucourse_id");
		String[] bixiu_course_id = request
				.getParameterValues("bixiu_course_id");
		String selectStr = "";

		if (major_xuanxiucourse_id != null) {
			for (int i = 0; i < major_xuanxiucourse_id.length; i++) {
				selectStr += major_xuanxiucourse_id[i].substring(0,
						major_xuanxiucourse_id[i].indexOf("|"))
						+ ",";
			}
		}
		if (pub_xuanxiucourse_id != null) {
			for (int i = 0; i < pub_xuanxiucourse_id.length; i++) {
				selectStr += pub_xuanxiucourse_id[i].substring(0,
						pub_xuanxiucourse_id[i].indexOf("|"))
						+ ",";
			}
		}
		if (bixiu_course_id != null) {
			for (int i = 0; i < bixiu_course_id.length; i++) {
				selectStr += bixiu_course_id[i].substring(0, bixiu_course_id[i]
						.indexOf("|"))
						+ ",";
			}
		}

		if (selectStr.length() > 0) {
			selectStr = selectStr.substring(0, selectStr.length() - 1);
		}
		String[] select_opencourse_ids = selectStr.split(",");
		return feeList.getUserElectiveFee(userId, opencourse_ids,
				select_opencourse_ids);
	}

	public List getSiteFeeStat(String startDate, String endDate, String siteId,
			String gradeId) throws PlatformException {
		if (feeManagerPriv.getSiteFeeStat == 1) {
			OracleFeeList feeList = new OracleFeeList();
			return feeList.getSiteFeeStat(startDate, endDate, siteId, gradeId);
		} else {
			throw new PlatformException("�Բ�����û�в鿴��ѧվѧ��ͳ�Ƶ�Ȩ��!");
		}
	}

	public List getGradeFeeStat(String startDate, String endDate,
			String siteId, String gradeId) throws PlatformException {
		if (feeManagerPriv.getSiteFeeStat == 1) {
			OracleFeeList feeList = new OracleFeeList();
			return feeList.getGradeFeeStat(startDate, endDate, siteId, gradeId);
		} else {
			throw new PlatformException("�Բ�����û�в鿴��ѧվѧ��ͳ�Ƶ�Ȩ��!");
		}
	}

	public List getStudentFeeStat(String startDate, String endDate,
			String siteId, String gradeId) throws PlatformException {
		if (feeManagerPriv.getSiteFeeStat == 1) {
			OracleFeeList feeList = new OracleFeeList();
			return feeList.getStudentFeeStat(startDate, endDate, siteId,
					gradeId);
		} else {
			throw new PlatformException("�Բ�����û�в鿴��ѧվѧ��ͳ�Ƶ�Ȩ��!");
		}
	}

}
