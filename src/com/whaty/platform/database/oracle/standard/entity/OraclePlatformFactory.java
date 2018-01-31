/*
 * OraclePlatformFactory.java
 *
 * Created on 2005年4月6日, 下午8:28
 */

package com.whaty.platform.database.oracle.standard.entity;

import com.whaty.platform.database.oracle.standard.entity.fee.OracleChargeLevelManage;
import com.whaty.platform.database.oracle.standard.entity.fee.OracleFeeManage;
import com.whaty.platform.database.oracle.standard.entity.fee.OracleFeeManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacherPriv;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudentPriv;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacherPriv;
import com.whaty.platform.database.oracle.standard.info.OracleInfoManage;
import com.whaty.platform.database.oracle.standard.info.user.OracleInfoManagerPriv;
import com.whaty.platform.database.oracle.standard.sms.OracleSmsManage;
import com.whaty.platform.entity.BasicActivityManage;
import com.whaty.platform.entity.BasicEduManage;
import com.whaty.platform.entity.BasicEntityManage;
import com.whaty.platform.entity.BasicGraduateDesignManage;
import com.whaty.platform.entity.BasicMailInfoManage;
import com.whaty.platform.entity.BasicRecruitManage;
import com.whaty.platform.entity.BasicRightManage;
import com.whaty.platform.entity.BasicScoreManage;
import com.whaty.platform.entity.BasicSiteActivityManage;
import com.whaty.platform.entity.BasicSiteEduManage;
import com.whaty.platform.entity.BasicSiteEntityManage;
import com.whaty.platform.entity.BasicSiteGraduateDesignManage;
import com.whaty.platform.entity.BasicSiteRecruitManage;
import com.whaty.platform.entity.BasicSiteScoreManage;
import com.whaty.platform.entity.BasicSiteUserManage;
import com.whaty.platform.entity.BasicUserManage;
import com.whaty.platform.entity.EntityTestManage;
import com.whaty.platform.entity.EntityTestPriv;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.PlatformManage;
import com.whaty.platform.entity.SiteManagerOperationManage;
import com.whaty.platform.entity.SiteTeacherOperationManage;
import com.whaty.platform.entity.StudentOperationManage;
import com.whaty.platform.entity.TeacherOperationManage;
import com.whaty.platform.entity.fee.ChargeLevelManage;
import com.whaty.platform.entity.fee.FeeManage;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.entity.user.SiteTeacherPriv;
import com.whaty.platform.entity.user.StudentPriv;
import com.whaty.platform.entity.user.TeacherPriv;
import com.whaty.platform.info.InfoManage;
import com.whaty.platform.info.user.InfoManagerPriv;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.sms.SmsManagerPriv;
import com.whaty.platform.sso.SsoManagerPriv;

/**
 * 
 * @author Administrator
 */
public class OraclePlatformFactory extends PlatformFactory {

	public OraclePlatformFactory() {
	} 

	public ManagerPriv getManagerPriv(java.lang.String id) {
		return new OracleManagerPriv(id);
	}

	public ManagerPriv getManagerPriv() {
		return new OracleManagerPriv();
	}

	public BasicEntityManage creatBasicEntityManage(ManagerPriv amanagerpriv) {
		return new OracleBasicEntityManage(amanagerpriv);
	}

	public BasicUserManage creatBasicUserManage(ManagerPriv amanagerpriv) {
		return new OracleBasicUserManage(amanagerpriv);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.PlatformFactory#creatStudentOperationManage(com.whaty.platform.entity.user.StudentPriv)
	 */
	public StudentOperationManage creatStudentOperationManage(
			StudentPriv studentpriv) {
		return new OracleStudentOperationManage(studentpriv);
	}

	public StudentOperationManage creatStudentOperationManage() {
		return new OracleStudentOperationManage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.PlatformFactory#creatPlatformManage()
	 */
	public PlatformManage createPlatformManage() {
		return new OraclePlatformManage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.PlatformFactory#creatTeacherOperationManage(com.whaty.platform.entity.user.TeacherPriv)
	 */
	public TeacherOperationManage creatTeacherOperationManage(
			TeacherPriv teacherpriv) {
		return new OracleTeacherOperationManage(teacherpriv);
	}

	public SiteManagerOperationManage creatSiteManagerOperationManage(
			SiteManagerPriv sitemanagerpriv) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.PlatformFactory#getStudentPriv(java.lang.String)
	 */
	public StudentPriv getStudentPriv(String id) {
		return new OracleStudentPriv(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.PlatformFactory#getTeacherPriv(java.lang.String)
	 */
	public TeacherPriv getTeacherPriv(String id) {
		return new OracleTeacherPriv(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.PlatformFactory#getSiteManagerPriv(java.lang.String)
	 */
	public SiteManagerPriv getSiteManagerPriv(String id) {
		return new OracleSiteManagerPriv(id);
	}

	public BasicEduManage creatBasicEduManage(ManagerPriv amanagerpriv) {
		return new OracleBasicEduManage(amanagerpriv);
	}

//	public BasicActivityManage creatBasicActivityManage(ManagerPriv amanagerpriv) {
//		return new OracleBasicActivityManage(amanagerpriv);
//	}

	public BasicRightManage creatBasicRightManage() {
		return new OracleBasicRightManage();
	}
	
	public BasicRightManage creatBasicRightManage(ManagerPriv amanagerpriv) {
		return new OracleBasicRightManage(amanagerpriv);
	}

	public BasicSiteUserManage creatBasicSiteUserManage(
			SiteManagerPriv smanagerpriv) {
		return new OracleBasicSiteUserManage(smanagerpriv);
	}

	public BasicSiteEntityManage creatBasicSiteEntityManage(
			SiteManagerPriv smanagerpriv) {
		return new OracleBasicSiteEntityManage(smanagerpriv);
	}

	public BasicSiteActivityManage creatBasicSiteActivityManage(
			SiteManagerPriv smanagerpriv) {
		return new OracleBasicSiteActivityManage(smanagerpriv);
	}

	public BasicScoreManage creatBasicScoreManage(ManagerPriv managerpriv) {
		return new OracleBasicScoreManage();
	}

	public BasicScoreManage creatBasicScoreManage() {
		return new OracleBasicScoreManage();
	}

//	public BasicActivityManage creatBasicActivityManage() {
//		return new OracleBasicActivityManage();
//	}

//	public BasicSiteScoreManage creatBasicSiteScoreManage(
//			SiteManagerPriv smanagerpriv) {
//		return new OracleBasicSiteScoreManage(smanagerpriv);
//	}

	public BasicSiteEduManage creatBasicSiteEduManage(
			SiteManagerPriv smanagerpriv) {
		return new OracleBasicSiteEduManage(smanagerpriv);
	}

//	public BasicRecruitManage creatBasicRecruitManage(ManagerPriv smanagerpriv) {
//		return new OracleBasicRecruitManage(smanagerpriv);
//	}

	public BasicSiteRecruitManage creatBasicSiteRecruitManage(
			SiteManagerPriv smanagerPriv) {
		return new OracleBasicSiteRecruitManage(smanagerPriv);
	}

	public ChargeLevelManage createStudentChargeLevelManage(
			StudentPriv studentpriv) {
		return new OracleChargeLevelManage();
	}

	public ChargeLevelManage createChargeLevelManage(ManagerPriv managerpriv) {
		// 目前没有加权限
		return new OracleChargeLevelManage();
	}

	public FeeManage createStudentFeeManage(StudentPriv studentpriv) {
		// 目前没有加权限
		return new OracleFeeManage();
	}

	public FeeManage createStudentFeeManage() {
		return new OracleFeeManage();
	}

	public SsoManagerPriv getSsoManagerPriv(String id) {
		return null;
	}

	public InfoManage creatInfoManage() {
		OracleInfoManagerPriv priv = new OracleInfoManagerPriv();
		OracleInfoManage manage = new OracleInfoManage(priv);
		return manage;
	}

	public SiteTeacherPriv getSiteTeacherPriv(String id) throws Exception {
		return new OracleSiteTeacherPriv(id);
	}

	public SiteTeacherOperationManage creatSiteTeacherOperationManage(
			SiteTeacherPriv steacherPriv) {
		// TODO Auto-generated method stub
		// OracleSiteTeacherPriv priv = new OracleSiteTeacherPriv();
		OracleSiteTeacherOperationManage siteTeacher = new OracleSiteTeacherOperationManage(
				steacherPriv);
		return siteTeacher;
	}

	public FeeManage createFeeManage(SiteManagerPriv managerpriv) {
		// 目前没有加权限
		return new OracleFeeManage();
	}

	public ChargeLevelManage createChargeLevelManage(SiteManagerPriv managerpriv) {
		// 目前没有加权限
		return new OracleChargeLevelManage();
	}

	public SmsManage createSmsManage(SmsManagerPriv smsPriv) {
		return new OracleSmsManage(smsPriv);
	}

	public EntityTestManage getEntityTestManage(EntityTestPriv testPriv) {
		return new OracleEntityTestManage(testPriv);
	}

	public EntityTestPriv getEntityTestPriv(ManagerPriv amanagerpriv) {
		String ssoId = amanagerpriv.sso_id;
		OracleEntityTestPriv testPriv = new OracleEntityTestPriv(ssoId);
		
		return testPriv;
	}

	public EntityTestPriv getEntityTestPriv(SiteManagerPriv amanagerpriv) {
		String ssoId = amanagerpriv.getManagerId();
		OracleEntityTestPriv testPriv = new OracleEntityTestPriv(ssoId);
		
		return testPriv;
	}
	
	public BasicMailInfoManage creatBasicMailManage(ManagerPriv managerpriv) {
		return new OracleBasciMailInfoManage(managerpriv);
	}

	public BasicGraduateDesignManage creatBasicGraduateDesignManage(ManagerPriv managerPriv) {
		return new OracleBasicGraduateDesignManage(managerPriv);
	}

	public BasicSiteGraduateDesignManage creatBasicSiteGraduateDesignManage(SiteManagerPriv siteManagerPriv) {
		return new OracleBasicSiteGraduateDesignManage(siteManagerPriv);
	}

	public BasicEntityManage creatBasicEntityManage(InfoManagerPriv infoManagerPriv) {
		ManagerPriv priv = new OracleManagerPriv();
		priv.getSite = infoManagerPriv.getSite;
		priv.getEduType = infoManagerPriv.getEduType;
		priv.getMajor = infoManagerPriv.getMajor;
		priv.getGrade = infoManagerPriv.getGrade;
		priv.getStudent = infoManagerPriv.getStudent;
		return new OracleBasicEntityManage(priv);
	}

	public BasicUserManage creatBasicUserManage(InfoManagerPriv infoManagerPriv) {
		ManagerPriv priv = new OracleManagerPriv();
		priv.getStudent = infoManagerPriv.getStudent;
		priv.getTeacher = infoManagerPriv.getTeacher;
		return new OracleBasicUserManage(priv);
	}

//	public BasicRecruitManage creatBasicRecruitManage(InfoManagerPriv infoManagerPriv) {
//		ManagerPriv priv = new OracleManagerPriv();
//		priv.getRecruitBatch = infoManagerPriv.getRecruitBatch;
//		priv.getPassRecruitStudent = infoManagerPriv.getPassRecruitStudent;
//		priv.getStudent = infoManagerPriv.getStudent;
//		return new OracleBasicRecruitManage(priv);
//	}

	public ChargeLevelManage getNormalChargeLevelManage() {
		OracleFeeManagerPriv feeManagerPriv = new OracleFeeManagerPriv();
		feeManagerPriv.addFee = 0;
		feeManagerPriv.addFeeStandard = 0;
		feeManagerPriv.getConfirmOrder = 0;
		feeManagerPriv.getFee = 0;
		feeManagerPriv.getFeeByTime = 0;
		feeManagerPriv.getFeeStandard = 1;
		feeManagerPriv.getSiteFeeStat = 0;
		feeManagerPriv.getStuFeeReturnApply = 1;
		feeManagerPriv.getStuOtherFee = 1;
		feeManagerPriv.getStuOtherFeeByTime = 1;

		OracleChargeLevelManage chargeLevelManage = new OracleChargeLevelManage(
				feeManagerPriv);

		return chargeLevelManage;
	}

	 

}
