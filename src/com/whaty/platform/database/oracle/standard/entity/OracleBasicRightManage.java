package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleElectiveActivity;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleRegister;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEntityList;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSite;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleBasicRecruitList;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitBatch;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitStudent;
import com.whaty.platform.database.oracle.standard.entity.right.OracleModuleRight;
import com.whaty.platform.database.oracle.standard.entity.right.OracleOffice;
import com.whaty.platform.database.oracle.standard.entity.right.OracleRight;
import com.whaty.platform.database.oracle.standard.entity.right.OracleRightGroup;
import com.whaty.platform.database.oracle.standard.entity.right.OracleRightModel;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManager;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteManager;
import com.whaty.platform.database.oracle.standard.entity.user.OracleUserBatchActivity;
import com.whaty.platform.entity.BasicRightManage;
import com.whaty.platform.entity.basic.Site;
import com.whaty.platform.entity.recruit.RecruitBatch;
import com.whaty.platform.entity.recruit.RecruitEduInfo;
import com.whaty.platform.entity.recruit.RecruitStudent;
import com.whaty.platform.entity.right.RightGroup;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.Manager;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteManager;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.sso.SsoFactory;
import com.whaty.platform.sso.SsoManage;
import com.whaty.platform.sso.SsoManagerPriv;
import com.whaty.platform.sso.SsoUser;
import com.whaty.platform.util.Address;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.RedundanceData;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;
import com.whaty.util.string.WhatyAttributeManage;

;

public class OracleBasicRightManage extends BasicRightManage {
	private ManagerPriv priv;
	public OracleBasicRightManage(){
		priv = new OracleManagerPriv();
	}
    public OracleBasicRightManage(ManagerPriv aManagerPriv){
    	this.priv = aManagerPriv;
    }
	public int addOffice(String name) throws PlatformException {
		OracleOffice office = new OracleOffice();
		office.setName(name);

		int suc = office.add();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getManagerPriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$addOffice</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int updateOffice(String id, String name) throws PlatformException {
		OracleOffice office = new OracleOffice();
		office.setId(id);
		office.setName(name);
		int suc = office.update();

		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getManagerPriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateOffice</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int deleteOffice(String id) throws PlatformException {
		OracleOffice office = new OracleOffice();
		office.setId(id);
	    int suc = office.delete();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$deleteOffice</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return suc;
	}

	public int addRightModel(String office_id, String model_id, String name,
			String status) throws PlatformException {
		OracleRightModel rightmodel = new OracleRightModel();
		rightmodel.setModel_id(model_id);
		rightmodel.setOffice_id(office_id);
		rightmodel.setName(name);
		rightmodel.setStatus(status);
		int suc =  rightmodel.add();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$addRightModel</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return suc;
	}

	public int updateRightModel() throws PlatformException {
		return 0;
	}

	public int deleteRightModel(String id) throws PlatformException {
		OracleRightModel rightmodel = new OracleRightModel();
		rightmodel.setId(id);
		int suc = rightmodel.delete();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$deleteRightModel</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return suc;
	}

	public int addRight(String model_id, String right_id, String name,
			String status) throws PlatformException {
		OracleRight right = new OracleRight();
		right.setModel_id(model_id);
		right.setRight_id(right_id);
		right.setName(name);
		right.setStatus(status);
		int suc = right.add();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$addRight</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return suc;
	}

	public int deleteRight(String right_id, String model_id)
			throws PlatformException {
		OracleRight right = new OracleRight();
		right.setModel_id(model_id);
		right.setRight_id(right_id);
		int suc =  right.delete();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$deleteRight</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return suc;
	}

	public int addRightGroup(String name) throws PlatformException {
		OracleRightGroup rightgroup = new OracleRightGroup();
		rightgroup.setName(name);
		int suc = rightgroup.add();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$addRightGroup</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return suc;

	}

	public int updateRightGroup(String id, String name)
			throws PlatformException {
		OracleRightGroup rightgroup = new OracleRightGroup();
		rightgroup.setName(name);
		rightgroup.setId(id);
		int suc = rightgroup.update();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$updateRightGroup</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return suc;

	}

	public int deleteRightGroup(String id) throws PlatformException {
		OracleRightGroup rightgroup = new OracleRightGroup();
		rightgroup.setId(id);
		int suc = rightgroup.delete();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$deleteRightGroup</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return suc;
	}

	public List getOffices() throws PlatformException {

		OracleOffice office = new OracleOffice();
		return office.getOffices();
	}

	public List getManagerOffices() throws PlatformException {
		OracleOffice office = new OracleOffice();
		return office.getManagerOffices();
	}

	public List getSiteOffices() throws PlatformException {
		OracleOffice office = new OracleOffice();
		return office.getSiteOffice();
	}

	public List getOfficeRightModel(String office_id) throws PlatformException {
		OracleOffice office = new OracleOffice();
		office.setId(office_id);
		return office.getOfficeRightModel();
	}

	public Hashtable getModelGroupHash(String office_id)
			throws PlatformException {
		OracleOffice office = new OracleOffice();
		office.setId(office_id);
		return office.getModelGroupHash();
	}

	public int changeGroupRights(String rightGroup_id, String office_id,
			String[] checkgroup) throws PlatformException {
		OracleRightGroup rightgroup = new OracleRightGroup();
		rightgroup.setId(rightGroup_id);

		int suc = rightgroup.changeGroupRights(office_id, checkgroup);
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$changeGroupRights</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return suc;
	}

	public Hashtable getRightsHash(String rightGroup_id)
			throws PlatformException {
		OracleRightGroup rightgroup = new OracleRightGroup();
		rightgroup.setId(rightGroup_id);
		return rightgroup.getRightsHash();
	}

	public List getModelRights(String model_id) throws PlatformException {
		OracleRightModel model = new OracleRightModel();
		model.setModel_id(model_id);
		return model.getModelRights();
	}

	public List getRightModels() throws PlatformException {
		OracleRightModel model = new OracleRightModel();

		return model.getRightModels();
	}

	public List getSiteRightModels() throws PlatformException {
		OracleRightModel model = new OracleRightModel();

		return model.getRightModels();
	}

	public List getModelGroups(String manager_id) throws PlatformException {
		OracleRight right = new OracleRight();
		return right.getModelGroups(manager_id);
	}

	public List getModelRights(String manager_id, String modelgroup)
			throws PlatformException {
		OracleRight right = new OracleRight();
		return right.getRights(manager_id, modelgroup);
	}

	public List getModelRights2(String manager_id, String modelgroup)
		throws PlatformException {
		OracleModuleRight right = new OracleModuleRight();
		return right.getRights(manager_id, modelgroup);
	}

	public List getSiteModelRights(String submanager_id, String modelgroup)
			throws PlatformException {
		OracleRight right = new OracleRight();
		return right.getSiteRights(submanager_id, modelgroup);
	}

	public List getSiteModelRights2(String submanager_id, String modelgroup)
		throws PlatformException {
		OracleModuleRight right = new OracleModuleRight();
		return right.getSiteRights(submanager_id, modelgroup);
	}
	public List getRightGroup() throws PlatformException {

		OracleRightGroup group = new OracleRightGroup();
		return group.getRightGroup();
	}

	public List getRightGroupMember(String group_id) throws PlatformException {
		OracleRightGroup group = new OracleRightGroup();
		return group.getRightGroupMember(group_id);
	}

	public int deleteRightGroupMember(String id) throws PlatformException {
		OracleManager manager = new OracleManager(id);

		int suc = manager.changeGroup();
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$deleteRightGroupMember</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
return suc;
	}

	public List getManagerList() throws PlatformException {
		OracleManager manager = new OracleManager();
		return manager.getManagerList();
	}

	public List getSiteManagerList(Page page, String loginId, String name,
			String siteId, String groupId) throws PlatformException {
		OracleSiteManager sitemanager = new OracleSiteManager();
		ArrayList searchProperties = new ArrayList();
		ArrayList orderProperties = new ArrayList();
		if (loginId != null && !loginId.equals("")) {
			searchProperties.add(new SearchProperty("login_id", loginId));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("name", name));
		}
		if (siteId != null && !siteId.equals("")) {
			searchProperties.add(new SearchProperty("site_id", siteId));
		}
		if (groupId != null && !groupId.equals("")) {
			searchProperties.add(new SearchProperty("group_id", groupId));
		}

		orderProperties.add(new OrderProperty("login_id"));

		return sitemanager.getSiteManagerList(page, searchProperties,
				orderProperties);
	}

	public int addAdmin(String login_id, String name, String password,
			String status, String type, String mobilephone)
			throws PlatformException {

		try {
			SsoFactory sso = SsoFactory.getInstance();
			SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
			managerPriv.addUser = 1;
			SsoManage ssoManage = sso.creatSsoManage(managerPriv);

			ssoManage.addSsoUser(login_id, password, name, null, null);

			SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);

			OracleManager manager = new OracleManager();
			manager.setId(ssouser.getId());
			manager.setLoginId(login_id);
			manager.setName(name);
			manager.setPassword(password);
			manager.setStatus(status);
			manager.setType(type);
			manager.setMobilePhone(mobilephone);
		    int suc =  manager.add();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getManagerPriv().getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$addAdmin</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
	return suc;
		} catch (PlatformException e) {
			return 0;
		}
	}

	public int addAdmin(HttpServletRequest request) throws PlatformException {
		try {
			SsoFactory sso = SsoFactory.getInstance();
			SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
			managerPriv.addUser = 1;
			SsoManage ssoManage = sso.creatSsoManage(managerPriv);

			String login_id = request.getParameter("admin_id");
			String admin_name = request.getParameter("admin_name");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String gender = request.getParameter("gender");
			String mobilephone = request.getParameter("mobilephone");
			String phone = request.getParameter("phone");
			String status = request.getParameter("status");
			String birthday = request.getParameter("birthday");
			String type = request.getParameter("type");
			String graduate_sch = request.getParameter("graduate_sch");
			String graduate_date = request.getParameter("graduate_date");
			String title = request.getParameter("title");
			String work_time = request.getParameter("work_time");
			String address = request.getParameter("address");
			String idcard = request.getParameter("idcard");
			WhatyAttributeManage idcheck = new WhatyAttributeManage();
			try {
				birthday = idcheck.getDateFromIdcard(idcard);
				gender = idcheck.getGenderFromIdcard(idcard);
				if (gender.equals("M"))
					gender = "��";
				if (gender.equals("F"))
					gender = "Ů";
			} catch (Exception e) {
			}

			ssoManage.addSsoUser(login_id, password, admin_name, null, email);

			SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);

			OracleManager manager = new OracleManager();
			RedundanceData redundance = new RedundanceData();
			redundance.setRe1(gender);
			redundance.setRe2(phone);
			redundance.setRe3(birthday);
			redundance.setRe4(graduate_sch);
			redundance.setRe5(graduate_date);
			redundance.setRe6(title);
			redundance.setRe7(work_time);
			redundance.setRe8(address);
			redundance.setRe9(idcard);
			manager.setId(ssouser.getId());
			manager.setLoginId(login_id);
			manager.setName(admin_name);
			manager.setPassword(password);
			manager.setStatus(status);
			manager.setType(type);
			manager.setMobilePhone(mobilephone);
			manager.setRedundace(redundance);
			int suc = manager.add();
			UserLog.setInfo(
					"<whaty>USERID$|$"
							+ this.getManagerPriv().getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$addAdmin</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
	return suc;
		} catch (PlatformException e) {
			return 0;
		}
	}

	public int addSiteAdmin(String login_id, String name, String password,
			String status, String type, String site_id)
			throws PlatformException {
		try {
			SsoFactory sso = SsoFactory.getInstance();
			SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
			managerPriv.addUser = 1;
			SsoManage ssoManage = sso.creatSsoManage(managerPriv);
			ssoManage.addSsoUser(login_id, password, name, null, null);
			SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);

			OracleSiteManager sitemanager = new OracleSiteManager();
			sitemanager.setId(ssouser.getId());
			sitemanager.setLoginId(login_id);
			sitemanager.setName(name);

			sitemanager.setPassword(password);
			sitemanager.setStatus(status);
			sitemanager.setType(type);
			sitemanager.setSite_id(site_id);
			int suc = sitemanager.add();
			UserLog.setInfo(
					"<whaty>USERID$|$"
							+ this.getManagerPriv().getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$addSiteAdmin</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
	return suc;
		} catch (PlatformException e) {
			return 0;
		}
	}

	public int addSiteAdmin(String login_id, String name, String password,
			String mobilephone, String status, String type, String site_id,
			String group_id) throws PlatformException {
		try {
			SsoFactory sso = SsoFactory.getInstance();
			SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
			managerPriv.addUser = 1;
			SsoManage ssoManage = sso.creatSsoManage(managerPriv);
			ssoManage.addSsoUser(login_id, password, name, null, null);
			SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);

			OracleSiteManager sitemanager = new OracleSiteManager();
			sitemanager.setId(ssouser.getId());
			sitemanager.setLoginId(login_id);
			sitemanager.setName(name);
			sitemanager.setMobilePhone(mobilephone);
			sitemanager.setPassword(password);
			sitemanager.setStatus(status);
			sitemanager.setType(type);
			sitemanager.setSite_id(site_id);
			if ("1".equals(group_id))
				sitemanager.setGroup_id(group_id);
			else
				sitemanager.setGroup_id("0");
			int suc = sitemanager.add();
			
			UserLog.setInfo(
					"<whaty>USERID$|$"
							+ this.getManagerPriv().getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$addSiteAdmin</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
	        return suc;
		} catch (PlatformException e) {
			return 0;
		}
	}

	public int addSiteAdmin(HttpServletRequest request)
			throws PlatformException {
		try {
			SsoFactory sso = SsoFactory.getInstance();
			SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
			managerPriv.addUser = 1;
			SsoManage ssoManage = sso.creatSsoManage(managerPriv);

			String login_id = request.getParameter("admin_id");
			String site_id = request.getParameter("site_id");
			String group_id = request.getParameter("group_id");
			String admin_name = request.getParameter("admin_name");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String gender = request.getParameter("gender");
			String mobilephone = request.getParameter("mobilephone");
			String phone = request.getParameter("phone");
			String status = request.getParameter("status");
			String birthday = request.getParameter("birthday");
			String type = request.getParameter("type");
			String graduate_sch = request.getParameter("graduate_sch");
			String graduate_date = request.getParameter("graduate_date");
			String title = request.getParameter("title");
			String work_time = request.getParameter("work_time");
			String address = request.getParameter("address");
			String idcard = request.getParameter("idcard");
			WhatyAttributeManage idcheck = new WhatyAttributeManage();
			try {
				birthday = idcheck.getDateFromIdcard(idcard);
				gender = idcheck.getGenderFromIdcard(idcard);
				if (gender.equals("M"))
					gender = "��";
				if (gender.equals("F"))
					gender = "Ů";
			} catch (Exception e) {
			}

			ssoManage.addSsoUser(login_id, password, admin_name, null, email);
			SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);

			OracleSiteManager sitemanager = new OracleSiteManager();
			RedundanceData redundance = new RedundanceData();
			redundance.setRe1(gender);
			redundance.setRe2(phone);
			redundance.setRe3(birthday);
			redundance.setRe4(graduate_sch);
			redundance.setRe5(graduate_date);
			redundance.setRe6(title);
			redundance.setRe7(work_time);
			redundance.setRe8(address);
			redundance.setRe9(idcard);
			sitemanager.setId(ssouser.getId());
			sitemanager.setLoginId(login_id);
			sitemanager.setName(admin_name);
			sitemanager.setMobilePhone(mobilephone);
			sitemanager.setPassword(password);
			sitemanager.setStatus(status);
			sitemanager.setType(type);
			sitemanager.setSite_id(site_id);
			sitemanager.setRedundace(redundance);

			if ("1".equals(group_id))
				sitemanager.setGroup_id(group_id);
			else
				sitemanager.setGroup_id("0");
			int suc = sitemanager.add();
			UserLog.setInfo(
					"<whaty>USERID$|$"
							+ this.getManagerPriv().getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$addSiteAdmin</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
	        return suc;
		} catch (PlatformException e) {
			return 0;
		}
	}

	public int putSiteAdminRight(String admin_id) throws PlatformException {
		OracleSiteManager sitemanager = new OracleSiteManager();
		SsoFactory sso = SsoFactory.getInstance();
		SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
		managerPriv.getUser = 1;
		SsoManage ssoManage = sso.creatSsoManage(managerPriv);
		SsoUser ssoUser = ssoManage.getSsoUserByLogin(admin_id);
		sitemanager.setId(ssoUser.getId());
		int suc = sitemanager.putRight();
		UserLog.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$putSiteAdminRight</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
        return suc;
	}

	public int showSiteAdminRight(String login_id) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateSiteAdmin(String login_id, String name, String password,
			String mobile, String status, String site_id)
			throws PlatformException {
		try {
			SsoFactory sso = SsoFactory.getInstance();
			SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
			managerPriv.updateUser = 1;
			SsoManage ssoManage = sso.creatSsoManage(managerPriv);

			SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);

			ssoManage.updateSsoUser(ssouser.getId(), login_id, password, name,
					null, null);

			OracleSiteManager sitemanager = new OracleSiteManager();

			int suc = sitemanager.update(login_id, name, site_id, password,
					mobile, status);
			UserLog.setInfo(
					"<whaty>USERID$|$"
							+ this.getManagerPriv().getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$updateSiteAdmin</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
	        return suc;
		} catch (PlatformException e) {
			return 0;
		}
	}

	public int updateSiteAdmin(HttpServletRequest request)
			throws PlatformException {
		try {
			SsoFactory sso = SsoFactory.getInstance();
			SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
			managerPriv.updateUser = 1;
			SsoManage ssoManage = sso.creatSsoManage(managerPriv);

			String login_id = request.getParameter("admin_id");
			String admin_name = request.getParameter("admin_name");
			String site_id = request.getParameter("site_id");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String gender = request.getParameter("gender");
			String mobilephone = request.getParameter("mobilephone");
			String phone = request.getParameter("phone");
			String status = request.getParameter("status");
			String birthday = request.getParameter("birthday");
			String type = request.getParameter("type");
			String graduate_sch = request.getParameter("graduate_sch");
			String graduate_date = request.getParameter("graduate_date");
			String title = request.getParameter("title");
			String work_time = request.getParameter("work_time");
			String address = request.getParameter("address");
			String idcard = request.getParameter("idcard");
			WhatyAttributeManage idcheck = new WhatyAttributeManage();
			try {
				birthday = idcheck.getDateFromIdcard(idcard);
				gender = idcheck.getGenderFromIdcard(idcard);
				if (gender.equals("M"))
					gender = "��";
				if (gender.equals("F"))
					gender = "Ů";
			} catch (Exception e) {
			}
			SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);
			ssoManage.updateSsoUser(ssouser.getId(), login_id, password,
					admin_name, null, email);
			OracleSiteManager sitemanager = new OracleSiteManager();

			RedundanceData redundance = new RedundanceData();
			redundance.setRe1(gender);
			redundance.setRe2(phone);
			redundance.setRe3(birthday);
			redundance.setRe4(graduate_sch);
			redundance.setRe5(graduate_date);
			redundance.setRe6(title);
			redundance.setRe7(work_time);
			redundance.setRe8(address);
			redundance.setRe9(idcard);
			sitemanager.setId(ssouser.getId());
			sitemanager.setLoginId(login_id);
			sitemanager.setName(admin_name);
			sitemanager.setMobilePhone(mobilephone);
			sitemanager.setPassword(password);
			sitemanager.setStatus(status);
			// sitemanager.setType(type);
			sitemanager.setSite_id(site_id);
			sitemanager.setRedundace(redundance);

			int suc = sitemanager.update();
			
			UserLog.setInfo(
					"<whaty>USERID$|$"
							+ this.getManagerPriv().getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$updateSiteAdmin</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
	        return suc;
		} catch (PlatformException e) {
			return 0;
		}
	}

	public int updateAdminMobile(String login_id, String mobilephone)
			throws PlatformException {
		OracleManager manager = new OracleManager(login_id);
		int suc = 0;
		if (manager != null) {
			manager.setMobilePhone(mobilephone);
			suc= manager.updateMobile();
		} else {
			OracleSiteManager sitemanager = new OracleSiteManager(login_id);
			sitemanager.setMobilePhone(mobilephone);
			suc= sitemanager.updateMobile();
		}
		UserLog.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$updateAdminMobile</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
		return suc;

	}

	public int updateAdmin(String login_id, String name, String password,
			String mobile, String status) throws PlatformException {
		try {
			SsoFactory sso = SsoFactory.getInstance();
			SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
			managerPriv.updateUser = 1;
			SsoManage ssoManage = sso.creatSsoManage(managerPriv);

			SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);

			ssoManage.updateSsoUser(ssouser.getId(), login_id, password, name,
					null, null);

			OracleManager sitemanager = new OracleManager();

			int suc = sitemanager.update(login_id, name, password, mobile, status);
			UserLog.setInfo(
					"<whaty>USERID$|$"
							+ this.getManagerPriv().getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$updateAdmin</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;

		} catch (PlatformException e) {
			return 0;
		}
	}

	public int updateAdmin(HttpServletRequest request) throws PlatformException {
		try {
			SsoFactory sso = SsoFactory.getInstance();
			SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
			managerPriv.updateUser = 1;
			SsoManage ssoManage = sso.creatSsoManage(managerPriv);

			String login_id = request.getParameter("admin_id");
			String admin_name = request.getParameter("admin_name");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String gender = request.getParameter("gender");
			String mobilephone = request.getParameter("mobilephone");
			String phone = request.getParameter("phone");
			String status = request.getParameter("status");
			String birthday = request.getParameter("birthday");
			String type = request.getParameter("type");
			String graduate_sch = request.getParameter("graduate_sch");
			String graduate_date = request.getParameter("graduate_date");
			String title = request.getParameter("title");
			String work_time = request.getParameter("work_time");
			String address = request.getParameter("address");
			String idcard = request.getParameter("idcard");
			WhatyAttributeManage idcheck = new WhatyAttributeManage();
			try {
				birthday = idcheck.getDateFromIdcard(idcard);
				gender = idcheck.getGenderFromIdcard(idcard);
				if (gender.equals("M"))
					gender = "��";
				if (gender.equals("F"))
					gender = "Ů";
			} catch (Exception e) {
			}
			SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);

			ssoManage.updateSsoUser(ssouser.getId(), login_id, password,
					admin_name, null, email);

			OracleManager manager = new OracleManager();

			RedundanceData redundance = new RedundanceData();
			redundance.setRe1(gender);
			redundance.setRe2(phone);
			redundance.setRe3(birthday);
			redundance.setRe4(graduate_sch);
			redundance.setRe5(graduate_date);
			redundance.setRe6(title);
			redundance.setRe7(work_time);
			redundance.setRe8(address);
			redundance.setRe9(idcard);
			manager.setId(ssouser.getId());
			manager.setLoginId(login_id);
			manager.setName(admin_name);
			manager.setPassword(password);
			manager.setStatus(status);
			manager.setType(type);
			manager.setMobilePhone(mobilephone);
			manager.setRedundace(redundance);
			int suc = manager.update();
			UserLog.setInfo(
					"<whaty>USERID$|$"
							+ this.getManagerPriv().getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$updateAdmin</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} catch (PlatformException e) {
			return 0;
		}
	}

	public String getPassword(String login_id) throws PlatformException {
		try {

			SsoFactory sso = SsoFactory.getInstance();
			SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
			managerPriv.getUser = 1;
			SsoManage ssoManage = sso.creatSsoManage(managerPriv);

			SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);

			return ssouser.getPassword();
		} catch (PlatformException e) {
			return "0";
		}

	}

	public int deleteRightAdmin(String login_id) throws PlatformException {
		int i = 0;
		OracleManager manager = new OracleManager();
		manager.setLoginId(login_id);
		i = manager.delete();

		SsoFactory sso = SsoFactory.getInstance();
		SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
		managerPriv.deleteUser = 1;
		SsoManage ssoManage = sso.creatSsoManage(managerPriv);

		SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);
		ssoManage.deleteSsoUser(ssouser.getId());
		UserLog.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$deleteRightAdmin</whaty><whaty>STATUS$|$"
						+ i
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
		return i;
	}

	public int deleteRightSiteAdmin(String login_id) throws PlatformException {
		int i = 0;
		OracleSiteManager sitemanager = new OracleSiteManager();
		sitemanager.setLoginId(login_id);

		i = sitemanager.delete();

		SsoFactory sso = SsoFactory.getInstance();
		SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
		managerPriv.deleteUser = 1;
		SsoManage ssoManage = sso.creatSsoManage(managerPriv);

		SsoUser ssouser = ssoManage.getSsoUserLogin(login_id);
		ssoManage.deleteSsoUser(ssouser.getId());
		UserLog.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$deleteRightSiteAdmin</whaty><whaty>STATUS$|$"
						+ i
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
		return i;

	}

	public List getSiteList() throws PlatformException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		return basicdatalist.getSites(null, null, null);
	}

	public List getGradeList() throws PlatformException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		return basicdatalist.getGrades(null, null, null);
	}

	public List getMajorList() throws PlatformException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		List searchProperties = new ArrayList();
		searchProperties.add(new SearchProperty("m.id", "0", "<>"));
		return basicdatalist.getMajors(null, searchProperties, null);

	}

	public List getEduTypeList() throws PlatformException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		return basicdatalist.getEduTypes(null, null, null);
	}

	public int updateGroup(String id, String group_id) throws PlatformException {
		OracleManager manager = new OracleManager();
		manager.setId(id);
		manager.setGroup_id(group_id);

		int suc = manager.changeGroup();
		
		UserLog.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$updateGroup</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
		return suc ;
	}

	public int updateRight(String id, List rightList) throws PlatformException {
		OracleManager manager = new OracleManager();
		manager.setId(id);
		int suc = manager.updateRight(rightList);
		UserLog.setInfo(
				"<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$updateRight</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
		return suc ;
	}

	public int IsExist(String group_id, String right_id)
			throws PlatformException {
		OracleRightGroup rightgroup = new OracleRightGroup();
		return rightgroup.isIdExist(group_id, right_id);
	}

	public int updateRangeGroupRights(String id, String site, String grade,
			String major, String edutype) throws PlatformException {
		OracleRightGroup rightgroup = new OracleRightGroup();
		int suc = rightgroup.updateRangeGroupRights(id, site, grade, major,
				edutype);
		UserLog.setInfo("<whaty>USERID$|$"
						+ this.getManagerPriv().getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|$updateRangeGroupRights</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
		return suc ;
	}

	public RightGroup getRightGroup(String id) throws PlatformException {
		OracleRightGroup rightgroup = new OracleRightGroup();
		return rightgroup.getRightGroup(id);
	}

	public int addRecruitStudent(String name, String password, String gender,
			String folk, String birthday, String zzmm, String edu,
			String site_id, String edutype_id, String major_id,
			String card_type, String card_no, String hometown, String[] email,
			String postalcode, String address, String[] mobilephone,
			String[] phone, String considertype, String status,
			String premajor_status, String school_name, String school_code,
			String graduate_year, String graduate_cardno)
			throws NoRightException, PlatformException {
		try {
			OracleRecruitStudent student = new OracleRecruitStudent();
			student.setName(name);
			student.setPassword(password);
			student.setPremajor_status(premajor_status);

			HumanNormalInfo normalInfo = new HumanNormalInfo();
			normalInfo.setGender(gender);
			normalInfo.setFolk(folk);
			normalInfo.setBirthday(birthday);
			normalInfo.setZzmm(zzmm);
			normalInfo.setEdu(edu);
			normalInfo.setCardType(card_type);
			normalInfo.setIdcard(card_no);
			normalInfo.setHometown(hometown);
			normalInfo.setEmail(email);

			Address home_address = new Address();
			home_address.setPostalcode(postalcode);
			home_address.setAddress(address);
			normalInfo.setHome_address(home_address);

			normalInfo.setMobilePhone(mobilephone);
			normalInfo.setPhone(phone);

			student.setNormalInfo(normalInfo);

			RecruitEduInfo eduInfo = new RecruitEduInfo();
			eduInfo.setSite_id(site_id);
			eduInfo.setEdutype_id(edutype_id);
			eduInfo.setMajor_id(major_id);
			eduInfo.setConsiderType(considertype);
			eduInfo.setStatus(status);
			eduInfo.setSchool_name(school_name);
			eduInfo.setSchool_code(school_code);
			eduInfo.setGraduate_year(graduate_year);
			eduInfo.setGraduate_cardno(graduate_cardno);

			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("active", "1", "="));
			List batchs = basicdatalist.getBatchs(null, searchProperty, null);
			RecruitBatch batch = null;
			if (batchs == null || batchs.size() == 0)
				throw new PlatformException("û���趨��ǰ���!");
			else
				batch = new OracleRecruitBatch(((RecruitBatch) batchs.get(0))
						.getId());
			eduInfo.setBatch(batch);

			student.setEduInfo(eduInfo);

			int suc = student.add();
			UserLog.setInfo("<whaty>USERID$|$"
					+ this.getManagerPriv().getSso_id()
					+ "</whaty><whaty>BEHAVIOR$|$addRecruitStudent</whaty><whaty>STATUS$|$"
					+ suc
					+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
					+ LogType.MANAGER
					+ "</whaty><whaty>PRIORITY$|$"
					+ LogPriority.INFO + "</whaty>", new Date());
	return suc ;
		} catch (PlatformException e) {
			return 0;
		}
	}

	public RecruitStudent getRecruitInfo(String card_no, String password,
			String batchId) throws PlatformException {
		OracleRecruitStudent student = new OracleRecruitStudent();

		return student.getRecruitInfo(card_no, password, batchId);
	}

	public Site getSiteInfo(String id) throws PlatformException {
		OracleSite site = new OracleSite();
		return site.getSiteInfo(id);
	}

	public int updateRecruitStudentInfo(String id, String name,
			String password, String gender, String folk, String birthday,
			String zzmm, String edu, String site_id, String edutype_id,
			String major_id, String card_type, String card_no, String hometown,
			String[] email, String postalcode, String address,
			String[] mobilephone, String[] phone, String considertype,
			String status, String premajor_status, String school_name,
			String school_code, String graduate_year, String graduate_cardno)
			throws PlatformException {
		OracleRecruitStudent student = new OracleRecruitStudent(id);
		student.setName(name);
		student.setPassword(password);
		student.setPremajor_status(premajor_status);
		HumanNormalInfo normalInfo = new HumanNormalInfo();
		normalInfo.setGender(gender);
		normalInfo.setFolk(folk);
		normalInfo.setBirthday(birthday);
		normalInfo.setZzmm(zzmm);
		normalInfo.setEdu(edu);
		normalInfo.setCardType(card_type);
		normalInfo.setIdcard(card_no);
		normalInfo.setHometown(hometown);
		normalInfo.setEmail(email);

		Address home_address = new Address();
		home_address.setPostalcode(postalcode);
		home_address.setAddress(address);
		normalInfo.setHome_address(home_address);

		normalInfo.setMobilePhone(mobilephone);
		normalInfo.setPhone(phone);

		student.setNormalInfo(normalInfo);

		RecruitEduInfo eduInfo = new RecruitEduInfo();
		eduInfo.setSite_id(site_id);
		eduInfo.setEdutype_id(edutype_id);
		eduInfo.setMajor_id(major_id);
		eduInfo.setConsiderType(considertype);
		eduInfo.setStatus(status);
		eduInfo.setSchool_name(school_name);
		eduInfo.setSchool_code(school_code);
		eduInfo.setGraduate_year(graduate_year);
		eduInfo.setGraduate_cardno(graduate_cardno);

		RecruitBatch batch = new OracleRecruitBatch(student.getEduInfo().getBatch().getId());
		eduInfo.setBatch(batch);
		
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("active", "1", "="));

		/*
		 * List batchs = basicdatalist.getBatchs(null, searchProperty, null);
		 * RecruitBatch batch = null; if (batchs == null || batchs.size() == 0)
		 * throw new PlatformException("û���趨��ǰ���!"); else batch = new
		 * OracleRecruitBatch(((RecruitBatch) batchs.get(0)) .getId());
		 * eduInfo.setBatch(batch);
		 */
		student.setEduInfo(eduInfo);
		int suc = student.update();
		UserLog.setInfo("<whaty>USERID$|$"
				+ this.getManagerPriv().getSso_id()
				+ "</whaty><whaty>BEHAVIOR$|$updateRecruitStudentInfo</whaty><whaty>STATUS$|$"
				+ suc
				+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
				+ LogType.MANAGER
				+ "</whaty><whaty>PRIORITY$|$"
				+ LogPriority.INFO + "</whaty>", new Date());
return suc ;
	}

	public RecruitStudent getRecruitInfo(String id) throws PlatformException {
		OracleRecruitStudent student = new OracleRecruitStudent(id);

		return student;
	}

	public RecruitStudent getRecruitInfo1(String regNo)
			throws PlatformException {
		OracleRecruitStudent student = new OracleRecruitStudent();
		return student.getRecruitInfo1(regNo);
	}

	public RecruitStudent getRecruitInfo2(String cardNO)
			throws PlatformException {
		OracleRecruitStudent student = new OracleRecruitStudent();

		return student.getRecruitInfo(cardNO);
	}

	public int checkRecruitExist(String cardNO) throws PlatformException {
		OracleRecruitStudent student = new OracleRecruitStudent();
		return student.checkRecruitExist(cardNO);
	}

	public RecruitStudent getRecruitInfo2(String name, String cardNo,
			String batchId) throws PlatformException {
		if (name == null || name.equals("") || name.equals("null"))
			name = null;
		if (cardNo == null || cardNo.equals("") || cardNo.equals("null"))
			cardNo = null;
		OracleRecruitStudent student = new OracleRecruitStudent();
		return student.getRecruitInfo2(name, cardNo, batchId);
	}

	public Manager getAdmin(String id) throws PlatformException {
		return new OracleManager(id);
	}

	public SiteManager getSiteAdmin(String id) throws PlatformException {
		return new OracleSiteManager(id);
	}

	public int updateRangeAdminRights(String id, String site, String grade,
			String major, String edutype) throws PlatformException {
		OracleManager manager = new OracleManager();
		int suc = manager.updateRangeAdminRights(id, site, grade, major, edutype);
		UserLog.setInfo("<whaty>USERID$|$"
				+ this.getManagerPriv().getSso_id()
				+ "</whaty><whaty>BEHAVIOR$|$updateRangeAdminRights</whaty><whaty>STATUS$|$"
				+ suc
				+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
				+ LogType.MANAGER
				+ "</whaty><whaty>PRIORITY$|$"
				+ LogPriority.INFO + "</whaty>", new Date());
          return suc ;
	}

	public List getActiveBatch() {
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("active", "1", "="));
		return basicdatalist.getActiveBatchs(null, searchProperty, null);
	}

	public String updateRecruitStudentInfo(String regNo, String siteId,
			String majorId) throws PlatformException {
		OracleRecruitStudent student = new OracleRecruitStudent();
		RecruitStudent stu = student.getRecruitInfo1(regNo);
		RecruitEduInfo eduInfo = stu.getEduInfo();
		eduInfo.setSite_id(siteId);
		eduInfo.setMajor_id(majorId);
		stu.setEduInfo(eduInfo);
		String login = stu.updateSiteMajor();
		
		UserLog.setInfo("<whaty>USERID$|$"
				+ this.getManagerPriv().getSso_id()
				+ "</whaty><whaty>BEHAVIOR$|$updateRecruitStudentInfo</whaty><whaty>STATUS$|$"
				+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
				+ LogType.MANAGER
				+ "</whaty><whaty>PRIORITY$|$"
				+ LogPriority.INFO + "</whaty>", new Date());
		return login;
	}

	public int checkNewStudentEleByFee(String regNo, String ssoUserId)
			throws PlatformException {
		OracleElectiveActivity elec = new OracleElectiveActivity();
		return elec.checkElectiveByFee(regNo, ssoUserId);
	}

	public RecruitBatch getActiveRecruitBatch() throws PlatformException {
		List searchProperties = new ArrayList();
		searchProperties.add(new SearchProperty("active", "1", "="));
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		return basicdatalist.getBatch(searchProperties);
	}

	public ManagerPriv getManagerPriv() throws PlatformException {
		OracleManagerPriv managerPriv = new OracleManagerPriv();
		managerPriv.setSso_id(this.priv.getSso_id());
		managerPriv.getRecruitBatch = 1;
		managerPriv.getSite = 1;
		managerPriv.getEduType = 1;
		managerPriv.getMajor = 1;
		return managerPriv;
	}

}
