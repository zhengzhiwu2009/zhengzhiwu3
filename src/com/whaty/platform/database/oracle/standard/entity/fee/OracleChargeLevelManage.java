/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.fee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.whaty.platform.User;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.fee.level.OracleChargeLevelActivity;
import com.whaty.platform.database.oracle.standard.entity.fee.level.OracleChargeLevelList;
import com.whaty.platform.database.oracle.standard.entity.fee.level.OracleUserChargeLevel;
import com.whaty.platform.entity.fee.ChargeLevelManage;
import com.whaty.platform.entity.fee.FeeManagerPriv;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;

/**
 * @author chenjian
 * 
 */
public class OracleChargeLevelManage implements ChargeLevelManage {

	FeeManagerPriv feeManagerPriv;

	public OracleChargeLevelManage() {

	}

	public OracleChargeLevelManage(FeeManagerPriv feeManagerPriv) {
		this.feeManagerPriv = feeManagerPriv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.ChargeLevelManage#getChargeLevel(java.lang.String)
	 */
	public Map getChargeLevel(String userId) {
		OracleUserChargeLevel chargeLevel = new OracleUserChargeLevel();
		User user = new User();
		user.setId(userId);
		chargeLevel.setUser(user);
		return chargeLevel.getAllChargeLevel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.ChargeLevelManage#setChargeLevel(java.lang.String,
	 *      java.util.Map)
	 */
	public void setChargeLevel(String userId, Map chargeLevel)
			throws PlatformException {
		if (feeManagerPriv.addFeeStandard == 1) {
			OracleUserChargeLevel userChargeLevel = new OracleUserChargeLevel();
			User user = new User();
			user.setId(userId);
			userChargeLevel.setUser(user);
			userChargeLevel.setChargeLevel(chargeLevel);
		} else {
			throw new PlatformException("��û�����ѧ�ѱ�׼��Ȩ��!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.fee.ChargeLevelManage#setChargeLevel(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	public void setChargeLevel(String siteId, String majorId, String edutypeId,
			String gradeId, Map chargeLevel) throws PlatformException {
		if (feeManagerPriv.addFeeStandard == 1) {
			OracleChargeLevelActivity chargeLevelActivity = new OracleChargeLevelActivity();
			chargeLevelActivity.setBatchChargeLevel(siteId, majorId, edutypeId,
					gradeId, chargeLevel);
		} else {
			throw new PlatformException("��û�����ѧ�ѱ�׼��Ȩ��!");
		}

	}

	public void setChargeLevel(String[] siteId, String[] majorId,
			String[] edutypeId, String[] gradeId, Map chargeLevel)
			throws PlatformException {
		if (feeManagerPriv.addFeeStandard == 1) {
			OracleChargeLevelActivity chargeLevelActivity = new OracleChargeLevelActivity();
			chargeLevelActivity.setBatchChargeLevel(siteId, majorId, edutypeId,
					gradeId, chargeLevel);
		} else {
			throw new PlatformException("��û�����ѧ�ѱ�׼��Ȩ��!");
		}
	}

	public void setOtherChargeLevel(String[] siteId, String[] majorId,
			String[] edutypeId, String[] gradeId, String otherFeeTypeId,
			Map chargeLevel) throws PlatformException {
		if (feeManagerPriv.addOtherFeeStandard == 1) {
			OracleChargeLevelActivity chargeLevelActivity = new OracleChargeLevelActivity();
			chargeLevelActivity.setBatchOtherChargeLevel(siteId, majorId,
					edutypeId, gradeId, otherFeeTypeId, chargeLevel);
		} else {
			throw new PlatformException("��û������ӷѱ�׼��Ȩ��!");
		}
	}

	public List getChargeLevelByType(Page page, String regNo, String name,
			String siteId, String majorId, String edutypeId, String gradeId,
			String type) {
		OracleChargeLevelList list = new OracleChargeLevelList();
		List searchList = new ArrayList();
		searchList.add(new SearchProperty("reg_no", regNo, "="));
		searchList.add(new SearchProperty("name", name, "like"));
		searchList.add(new SearchProperty("major_id", majorId));
		searchList.add(new SearchProperty("site_id", siteId));
		searchList.add(new SearchProperty("edutype_id", edutypeId));
		searchList.add(new SearchProperty("grade_id", gradeId));
		List orderList = new ArrayList();
		orderList.add(new OrderProperty("reg_no"));
		return list.searchChargeLevelByType(page, searchList, orderList, type);
	}

	public int getChargeLevelByTypeNum(String regNo, String name,
			String siteId, String majorId, String edutypeId, String gradeId,
			String type) {
		OracleChargeLevelList list = new OracleChargeLevelList();
		List searchList = new ArrayList();
		searchList.add(new SearchProperty("reg_no", regNo, "like"));
		searchList.add(new SearchProperty("name", name, "like"));
		searchList.add(new SearchProperty("major_id", majorId));
		searchList.add(new SearchProperty("site_id", siteId));
		searchList.add(new SearchProperty("edutype_id", edutypeId));
		searchList.add(new SearchProperty("grade_id", gradeId));
		return list.searchChargeLevelByTypeNum(searchList, type);
	}

	public String getUserChargeLevel(String userId, String type) {
		OracleUserChargeLevel chargeLevel = new OracleUserChargeLevel();
		User user = new User();
		user.setId(userId);
		chargeLevel.setUser(user);
		return chargeLevel.getChargeLevel(type);

	}

	public List getChargeLevels(Page page, String regNo, String name,
			String siteId, String majorId, String edutypeId, String gradeId) {
		OracleChargeLevelList list = new OracleChargeLevelList();
		List searchList = new ArrayList();

		if (regNo != null && !"".equals(regNo)
				&& !"null".equalsIgnoreCase(regNo))
			searchList.add(new SearchProperty("reg_no", regNo, "like"));
		if (name != null && !"".equals(name) && !"null".equalsIgnoreCase(name))
			searchList.add(new SearchProperty("name", name, "like"));
		if (majorId != null && !"".equals(majorId)
				&& !"null".equalsIgnoreCase(majorId))
			searchList.add(new SearchProperty("major_id", majorId, "="));
		if (siteId != null && !"".equals(siteId)
				&& !"null".equalsIgnoreCase(siteId))
			searchList.add(new SearchProperty("site_id", siteId, "="));
		if (edutypeId != null && !"".equals(edutypeId)
				&& !"null".equalsIgnoreCase(edutypeId))
			searchList.add(new SearchProperty("edutype_id", edutypeId, "="));
		if (gradeId != null && !"".equals(gradeId)
				&& !"null".equalsIgnoreCase(gradeId))
			searchList.add(new SearchProperty("grade_id", gradeId, "="));
		List orderList = new ArrayList();
		orderList.add(new OrderProperty(
				"site_name,grade_name,edutype_name,major_name,reg_no"));
		return list.searchChargeLevels(page, searchList, orderList);
	}

	public int getChargeLevelsNum(String regNo, String name, String siteId,
			String majorId, String edutypeId, String gradeId) {
		OracleChargeLevelList list = new OracleChargeLevelList();
		List searchList = new ArrayList();
		if (regNo != null && !"".equals(regNo)
				&& !"null".equalsIgnoreCase(regNo))
			searchList.add(new SearchProperty("reg_no", regNo, "like"));
		if (name != null && !"".equals(name) && !"null".equalsIgnoreCase(name))
			searchList.add(new SearchProperty("name", name, "like"));
		if (majorId != null && !"".equals(majorId)
				&& !"null".equalsIgnoreCase(majorId))
			searchList.add(new SearchProperty("major_id", majorId, "="));
		if (siteId != null && !"".equals(siteId)
				&& !"null".equalsIgnoreCase(siteId))
			searchList.add(new SearchProperty("site_id", siteId, "="));
		if (edutypeId != null && !"".equals(edutypeId)
				&& !"null".equalsIgnoreCase(edutypeId))
			searchList.add(new SearchProperty("edutype_id", edutypeId, "="));
		if (gradeId != null && !"".equals(gradeId)
				&& !"null".equalsIgnoreCase(gradeId))
			searchList.add(new SearchProperty("grade_id", gradeId, "="));
		return list.searchChargeLevelsNum(searchList);
	}

	public void setRecruitChargeLevel(String userId, Map chargeLevel)
	throws PlatformException {
			OracleUserChargeLevel userChargeLevel = new OracleUserChargeLevel();
			User user = new User();
			user.setId(userId);
			userChargeLevel.setUser(user);
			userChargeLevel.setRecruitChargeLevel(chargeLevel);
	}
	public List getRecruitChargeLevels(Page page, String regNo, String name,
			String siteId, String majorId, String edutypeId, String gradeId) {
		OracleChargeLevelList list = new OracleChargeLevelList();
		List searchList = new ArrayList();

		if (regNo != null && !"".equals(regNo)
				&& !"null".equalsIgnoreCase(regNo))
			searchList.add(new SearchProperty("reg_no", regNo, "like"));
		if (name != null && !"".equals(name) && !"null".equalsIgnoreCase(name))
			searchList.add(new SearchProperty("name", name, "like"));
		if (majorId != null && !"".equals(majorId)
				&& !"null".equalsIgnoreCase(majorId))
			searchList.add(new SearchProperty("major_id", majorId, "="));
		if (siteId != null && !"".equals(siteId)
				&& !"null".equalsIgnoreCase(siteId))
			searchList.add(new SearchProperty("site_id", siteId, "="));
		if (edutypeId != null && !"".equals(edutypeId)
				&& !"null".equalsIgnoreCase(edutypeId))
			searchList.add(new SearchProperty("edutype_id", edutypeId, "="));
		if (gradeId != null && !"".equals(gradeId)
				&& !"null".equalsIgnoreCase(gradeId))
			searchList.add(new SearchProperty("grade_id", gradeId, "="));
		List orderList = new ArrayList();
		orderList.add(new OrderProperty(
				"site_name,grade_name,edutype_name,major_name,reg_no"));
		return list.searchRecruitChargeLevels(page, searchList, orderList);
	}

	public int getRecruitChargeLevelsNum(String regNo, String name, String siteId,
			String majorId, String edutypeId, String gradeId) {
		OracleChargeLevelList list = new OracleChargeLevelList();
		List searchList = new ArrayList();
		if (regNo != null && !"".equals(regNo)
				&& !"null".equalsIgnoreCase(regNo))
			searchList.add(new SearchProperty("reg_no", regNo, "like"));
		if (name != null && !"".equals(name) && !"null".equalsIgnoreCase(name))
			searchList.add(new SearchProperty("name", name, "like"));
		if (majorId != null && !"".equals(majorId)
				&& !"null".equalsIgnoreCase(majorId))
			searchList.add(new SearchProperty("major_id", majorId, "="));
		if (siteId != null && !"".equals(siteId)
				&& !"null".equalsIgnoreCase(siteId))
			searchList.add(new SearchProperty("site_id", siteId, "="));
		if (edutypeId != null && !"".equals(edutypeId)
				&& !"null".equalsIgnoreCase(edutypeId))
			searchList.add(new SearchProperty("edutype_id", edutypeId, "="));
		if (gradeId != null && !"".equals(gradeId)
				&& !"null".equalsIgnoreCase(gradeId))
			searchList.add(new SearchProperty("grade_id", gradeId, "="));
		return list.searchRecruitChargeLevelsNum(searchList);
	}
	public String getRecruitUserChargeLevel(String userId, String type) {
		OracleUserChargeLevel chargeLevel = new OracleUserChargeLevel();
		User user = new User();
		user.setId(userId);
		chargeLevel.setUser(user);
		return chargeLevel.getRecruitChargeLevel(type);

	}
}
