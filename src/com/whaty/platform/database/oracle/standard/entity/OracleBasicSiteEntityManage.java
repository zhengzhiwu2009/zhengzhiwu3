/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicBatchActivity;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEduList;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEntityList;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleDep;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleGrade;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSemester;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSite;
import com.whaty.platform.database.oracle.standard.entity.fee.OracleChargeLevelManage;
import com.whaty.platform.database.oracle.standard.entity.fee.OracleFeeManage;
import com.whaty.platform.database.oracle.standard.entity.fee.OracleFeeManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleBasicGraduateList;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleBasicRecruitList;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitBatch;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitPlan;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.info.OracleInfoList;
import com.whaty.platform.database.oracle.standard.info.OracleTemplate;
import com.whaty.platform.database.oracle.standard.sms.OracleSmsManagerPriv;
import com.whaty.platform.entity.BasicSiteEntityManage;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.entity.basic.Dep;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.entity.basic.Grade;
import com.whaty.platform.entity.basic.Major;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.entity.basic.Site;
import com.whaty.platform.entity.fee.ChargeLevelManage;
import com.whaty.platform.entity.fee.FeeManage;
import com.whaty.platform.entity.recruit.RecruitBatch;
import com.whaty.platform.entity.recruit.RecruitEduInfo;
import com.whaty.platform.entity.recruit.RecruitLimit;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.info.InfoFactory;
import com.whaty.platform.info.InfoManage;
import com.whaty.platform.info.Template;
import com.whaty.platform.info.user.InfoManagerPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.sms.SmsFactory;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.sms.SmsManagerPriv;
import com.whaty.platform.util.Address;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;

/**
 * @author wq
 * 
 */
public class OracleBasicSiteEntityManage extends BasicSiteEntityManage {
	SiteManagerPriv basicManagePriv;

	public OracleBasicSiteEntityManage(SiteManagerPriv amanagerpriv) {
		this.basicManagePriv = amanagerpriv;
	}

	public FeeManage getFeeManage() {
		OracleFeeManagerPriv feeManagerPriv = new OracleFeeManagerPriv();
		feeManagerPriv.addFee = basicManagePriv.addFee;
		feeManagerPriv.addFeeStandard = basicManagePriv.addFeeStandard;
		feeManagerPriv.getConfirmOrder = basicManagePriv.getConfirmOrder;
		feeManagerPriv.getFee = basicManagePriv.getFee;
		feeManagerPriv.getFeeByTime = basicManagePriv.getFeeByTime;
		feeManagerPriv.getFeeStandard = basicManagePriv.getFeeStandard;
		feeManagerPriv.getSiteFeeStat = basicManagePriv.getSiteFeeStat;
		feeManagerPriv.getStuFeeReturnApply = basicManagePriv.getStuFeeReturnApply;
		feeManagerPriv.getStuOtherFee = basicManagePriv.getStuOtherFee;
		feeManagerPriv.getStuOtherFeeByTime = basicManagePriv.getStuOtherFeeByTime;

		OracleFeeManage feeManage = new OracleFeeManage(feeManagerPriv);

		return feeManage;
	}

	public ChargeLevelManage getChargeLevelManage() {
		OracleFeeManagerPriv feeManagerPriv = new OracleFeeManagerPriv();
		feeManagerPriv.addFee = basicManagePriv.addFee;
		feeManagerPriv.addFeeStandard = basicManagePriv.addFeeStandard;
		feeManagerPriv.getConfirmOrder = basicManagePriv.getConfirmOrder;
		feeManagerPriv.getFee = basicManagePriv.getFee;
		feeManagerPriv.getFeeByTime = basicManagePriv.getFeeByTime;
		feeManagerPriv.getFeeStandard = basicManagePriv.getFeeStandard;
		feeManagerPriv.getSiteFeeStat = basicManagePriv.getSiteFeeStat;
		feeManagerPriv.getStuFeeReturnApply = basicManagePriv.getStuFeeReturnApply;
		feeManagerPriv.getStuOtherFee = basicManagePriv.getStuOtherFee;
		feeManagerPriv.getStuOtherFeeByTime = basicManagePriv.getStuOtherFeeByTime;

		OracleChargeLevelManage chargeLevelManage = new OracleChargeLevelManage(
				feeManagerPriv);

		return chargeLevelManage;
	}

	public SmsManagerPriv getSmsManagerPriv(String managerId)
			throws PlatformException {
		SmsFactory smsFactory = SmsFactory.getInstance();
		SmsManagerPriv smsManagerPriv = smsFactory.getSmsManagerPriv(managerId);
		smsManagerPriv.addSms = basicManagePriv.addSms;
		smsManagerPriv.batchImportMobiles = basicManagePriv.batchImportMobiles;
		smsManagerPriv.checkSms = basicManagePriv.checkSms;
		smsManagerPriv.deleteSms = basicManagePriv.deleteSms;
		smsManagerPriv.getSms = basicManagePriv.getSms;
		smsManagerPriv.sendSms = basicManagePriv.sendSms;
		smsManagerPriv.updateSms = basicManagePriv.updateSms;

		return smsManagerPriv;
	}

	public Course createCourse() throws PlatformException {
		return new OracleCourse();
	}

	public Dep creatDep(String id, String name) throws NoRightException {
		// TODO Auto-generated method stub
		return null;
	}

	public EduType creatEduType(String id, String name) throws NoRightException {
		// TODO Auto-generated method stub
		return null;
	}

	public Grade creatGrade(String id, String name) throws NoRightException {
		// TODO Auto-generated method stub
		return null;
	}

	public Major creatMajor(String id, String name) throws NoRightException {
		// TODO Auto-generated method stub
		return null;
	}

	public Site creatSite(String id, String name) throws NoRightException {
		// TODO Auto-generated method stub
		return null;
	}

	public Semester creatSemester(String id, String name)
			throws NoRightException {
		// TODO Auto-generated method stub
		return null;
	}

	public Template creatTemplate(String id, String name)
			throws NoRightException {
		// TODO Auto-generated method stub
		return null;
	}

	public Dep getDep(String aid) throws NoRightException {
		if (basicManagePriv.getDep == 1) {
			return new OracleDep(aid);
		} else {
			throw new NoRightException("��û�в鿴Ժϵ��Ϣ��Ȩ�ޣ�");
		}

	}

	public EduType getEduType(String aid) throws NoRightException {
		if (basicManagePriv.getEduType == 1) {
			return new OracleEduType(aid);
		} else {
			throw new NoRightException("��û�в鿴�����Ϣ��Ȩ�ޣ�");
		}
	}

	public Grade getGrade(String aid) throws NoRightException {
		if (basicManagePriv.getGrade == 1) {
			return new OracleGrade(aid);
		} else {
			throw new NoRightException("��û�в鿴�꼶��Ϣ��Ȩ�ޣ�");
		}
	}

	public Major getMajor(String aid) throws NoRightException {
		if (basicManagePriv.getMajor == 1) {
			return new OracleMajor(aid);
		} else {
			throw new NoRightException("��û�в鿴רҵ��Ϣ��Ȩ�ޣ�");
		}
	}

	public Site getSite(String aid) throws NoRightException {
		if (basicManagePriv.getSite == 1) {
			return new OracleSite(aid);
		} else {
			throw new NoRightException("��û�в鿴��ѧվ��Ϣ��Ȩ�ޣ�");
		}
	}

	public Semester getSemester(String aid) throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			return new OracleSemester(aid);
		} else {
			throw new NoRightException("��û�в鿴ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public Template getTemplate(String aid) throws NoRightException {
		if (basicManagePriv.getTemplate == 1) {
			return new OracleTemplate(aid);
		} else {
			throw new NoRightException("��û�в鿴ģ����Ϣ��Ȩ��! ");
		}
	}

	public int deleteDep(String id) throws NoRightException, PlatformException {
		if (basicManagePriv.deleteDep == 1) {
			int suc = new OracleDep(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteDep</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û�в鿴��ѧվ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int deleteEduType(String id) throws NoRightException {
		if (basicManagePriv.deleteEduType == 1) {
			int suc = new OracleEduType(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteEduType</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û�в鿴��ѧվ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int deleteGrade(String id) throws NoRightException,
			PlatformException {
		if (basicManagePriv.deleteGrade == 1) {
			int suc = new OracleGrade(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteGrade</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û�в鿴��ѧվ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int deleteMajor(String id) throws NoRightException,
			PlatformException {
		if (basicManagePriv.deleteMajor == 1) {
			int suc = new OracleMajor(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteMajor</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û�в鿴��ѧվ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int deleteSite(String id) throws NoRightException {
		if (basicManagePriv.deleteSite == 1) {
			int suc = new OracleSite(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteSite</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û��ɾ���ѧվ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int deleteSemester(String id) throws NoRightException,
			PlatformException {
		if (basicManagePriv.deleteSemester == 1) {
			int suc = new OracleSemester(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteSemester</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û��ɾ��ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public int deleteTemplate(String id) throws NoRightException {
		if (basicManagePriv.deleteTemplate == 1) {
			int suc = new OracleTemplate(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTemplate</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û��ɾ��ģ����Ϣ��Ȩ�ޣ�");
		}
	}

	public int deleteStudent(String id) throws NoRightException,
			PlatformException {
		if (basicManagePriv.deleteRecruitStudent == 1) {
			// System.out.print("id=" + id);
			int suc = new OracleRecruitStudent(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteStudent</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("��û��ɾ������Ϣ��Ȩ�ޣ�");
		}
	}

	public void addCourseBatch(List courseList) throws PlatformException {
		if (basicManagePriv.addStudent == 1) {
			OracleBasicBatchActivity batch = new OracleBasicBatchActivity();
			batch.courseAddBatch(courseList);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addCourseBatch</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("��û�����ѧ���Ȩ��");
		}
	}

	public int addDep(String id, String name, String forshort, String note)
			throws NoRightException, PlatformException {
		if (basicManagePriv.addDep == 1) {
			OracleDep oracledep = new OracleDep();
			oracledep.setId(id);
			oracledep.setName(name);
			oracledep.setForshort(forshort);
			oracledep.setNote(note);
			int suc = oracledep.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addDep</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no addDep right");
		}
	}

	public int addEduType(String id, String name, String forshort,
			String introduction) throws NoRightException {
		if (basicManagePriv.addEduType == 1) {
			OracleEduType oracleedutype = new OracleEduType();
			oracleedutype.setId(id);
			oracleedutype.setName(name);
			oracleedutype.setForshort(forshort);
			oracleedutype.setIntroduction(introduction);
			int suc = oracleedutype.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addEduType</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no addEduType right");
		}
	}

	public int addGrade(String name, String begin_date) throws NoRightException {
		if (basicManagePriv.addGrade == 1) {
			OracleGrade oraclegrade = new OracleGrade();
			oraclegrade.setName(name);
			oraclegrade.setBegin_date(begin_date);
			int suc = oraclegrade.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addGrade</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no addGrade right");
		}
	}

	public int addMajor(String id, String name, String dep_id,
			String major_alias, String major_link, String note)
			throws NoRightException, PlatformException {
		if (basicManagePriv.addMajor == 1) {
			OracleMajor oraclemajor = new OracleMajor();
			oraclemajor.setId(id);
			oraclemajor.setDep_id(dep_id);
			oraclemajor.setMajor_alias(major_alias);
			oraclemajor.setMajor_link(major_link);
			oraclemajor.setName(name);
			oraclemajor.setNote(note);
			int suc = oraclemajor.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addMajor</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no addMajor right");
		}
	}

	public int addSite(String id, String name, String address, String email,
			String fax, String found_date, String linkman, String manager,
			String phone, String zip_code, String note)
			throws NoRightException, PlatformException {
		if (basicManagePriv.addSite == 1) {
			OracleSite oraclesite = new OracleSite();
			oraclesite.setAddress(address);
			oraclesite.setEmail(email);
			oraclesite.setFax(fax);
			oraclesite.setFound_date(found_date);
			oraclesite.setId(id);
			oraclesite.setLinkman(linkman);
			oraclesite.setManager(manager);
			oraclesite.setName(name);
			oraclesite.setNote(note);
			oraclesite.setPhone(phone);
			oraclesite.setZip_code(zip_code);
			int suc = oraclesite.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addSite</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no addSite right");
		}
	}

	public int addSemester(String name, String start_date, String end_date,
			String start_elective, String end_elective) throws NoRightException {
		if (basicManagePriv.addSemester == 1) {
			OracleSemester semester = new OracleSemester();
			semester.setName(name);
			semester.setStart_date(start_date);
			semester.setEnd_date(end_date);
			semester.setStart_elective(start_elective);
			semester.setEnd_elective(end_elective);
			int suc = semester.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addSemester</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no addSemester right");
		}
	}

	public int addTemplate(String id, String name, String content, String type,
			String pub_type, String note, String mark) throws NoRightException {
		if (basicManagePriv.addTemplate == 1) {
			OracleTemplate template = new OracleTemplate(id);
			template.setName(name);
			template.setContent(content);
			template.setType(type);
			template.setPub_type(pub_type);
			template.setNote(note);
			template.setMark(mark);
			int suc = template.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addTemplate</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no addTemplate right");
		}
	}

	public int addRecruitPlan(String edu_type_id, String major_id,
			String recruitnum, String site_id) throws NoRightException,
			PlatformException {
		if (basicManagePriv.addRecruitPlan == 1) {
			OracleRecruitPlan plan = new OracleRecruitPlan();

			plan.setMajor(new OracleMajor(major_id));
			plan.setEdutype(new OracleEduType(edu_type_id));

			RecruitLimit limit = new RecruitLimit();
			if (recruitnum == null || recruitnum.equals(""))
				recruitnum = "0";
			limit.setRecruitNum(Integer.valueOf(recruitnum).intValue());

			// limit.setSignTime(new TimeDef("2006-10-01", "2006-12-01"));
			// plan.setTitle("test");
			plan.setSite(new OracleSite(site_id));

			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("active", "1", "="));
			List batchs = basicdatalist.getBatchs(null, searchProperty, null);
			if (batchs == null || batchs.size() == 0)
				throw new PlatformException("û���趨��ǰ���!");
			else
				plan.setBatch(new OracleRecruitBatch(((RecruitBatch) batchs
						.get(0)).getId()));
			plan.setLimit(limit);
			plan.setStatus("0");
			int suc = plan.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addRecruitPlan</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no addRecruitPlan right");
		}
	}

	public int addRecruitStudent(String name, String password, String gender,
			String folk, String birthday, String zzmm, String edu,
			String site_id, String edutype_id, String major_id,
			String card_type, String card_no, String hometown, String[] email,
			String postalcode, String address, String[] mobilephone,
			String[] phone, String considertype, String status)
			throws NoRightException, PlatformException {
		if (basicManagePriv.addRecruitStudent == 1) {
			OracleRecruitStudent student = new OracleRecruitStudent();
			student.setName(name);
			student.setPassword(password);

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
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addRecruitStudent</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no addRecruitPlan right");
		}
	}
   
	public int addRecruitStudent(String name, String password,
			String gender, String folk, String birthday, String zzmm,
			String edu, String site_id, String edutype_id, String major_id,
			String card_type, String card_no, String hometown, String[] email,
			String postalcode, String address, String[] mobilephone,
			String[] phone, String considertype, String status,
			String premajor_status, String school_name, String school_code,
			String graduate_year, String graduate_cardno)
			throws NoRightException, PlatformException{
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
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$addRecruitStudent</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.SITEMANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} catch (PlatformException e) {
			return 0;
		}
	}

	public int updateDep(String id, String name, String forshort, String note)
			throws NoRightException {
		if (basicManagePriv.updateDep == 1) {
			OracleDep oracledep = new OracleDep();
			oracledep.setId(id);
			oracledep.setName(name);
			oracledep.setForshort(forshort);
			oracledep.setNote(note);
			int suc = oracledep.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateDep</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no updateDep right");
		}
	}

	public int updateEduType(String id, String name, String forshort,
			String introduction) throws NoRightException, PlatformException {
		if (basicManagePriv.updateEduType == 1) {
			OracleEduType oracleedutype = new OracleEduType();
			oracleedutype.setId(id);
			oracleedutype.setName(name);
			oracleedutype.setForshort(forshort);
			oracleedutype.setIntroduction(introduction);
			int suc = oracleedutype.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateEduType</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no updateEduType right");
		}
	}

	public int updateGrade(String id, String name, String begin_date)
			throws NoRightException {
		if (basicManagePriv.updateGrade == 1) {
			OracleGrade oraclegrade = new OracleGrade(id);
			oraclegrade.setName(name);
			oraclegrade.setBegin_date(begin_date);
			int suc = oraclegrade.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateGrade</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no updateGrade right");
		}
	}

	public int updateMajor(String id, String name, String major_alias,
			String major_link, String note) throws NoRightException {
		if (basicManagePriv.updateMajor == 1) {
			OracleMajor oraclemajor = new OracleMajor();
			oraclemajor.setId(id);
			oraclemajor.setMajor_alias(major_alias);
			oraclemajor.setMajor_link(major_link);
			oraclemajor.setName(name);
			oraclemajor.setNote(note);
			int suc = oraclemajor.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateMajor</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no updateMajor right");
		}
	}

	public int updateSite(String id, String name, String address, String email,
			String fax, String found_date, String linkman, String manager,
			String phone, String zip_code, String website, String note)
			throws NoRightException {
		if (basicManagePriv.updateSite == 1) {
			OracleSite site = new OracleSite(id);
			OracleSite oraclesite = new OracleSite();
			oraclesite.setAddress(address);
			oraclesite.setEmail(email);
			oraclesite.setFax(fax);
			oraclesite.setFound_date(found_date);
			oraclesite.setId(id);
			oraclesite.setLinkman(linkman);
			oraclesite.setManager(manager);
			oraclesite.setName(name);
			oraclesite.setNote(note);
			oraclesite.setPhone(phone);
			oraclesite.setURL(website);
			oraclesite.setZip_code(zip_code);
			oraclesite.setDiqu_id(site.getDiqu_id());
			int suc = oraclesite.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateSite</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no updateSite right");
		}
	}

	
	
	@Override
	public int updateSite(String id, String name, String address, String email,
			String fax, String found_date, String linkman, String manager,
			String phone, String zip_code, String website, String note, 
			String site_company, String site_type, String corporation, String 
			first_student_date) throws NoRightException {
		if (basicManagePriv.updateSite == 1) {
			OracleSite site = new OracleSite(id);
			OracleSite oraclesite = new OracleSite();
			oraclesite.setAddress(address);
			oraclesite.setEmail(email);
			oraclesite.setFax(fax);
			oraclesite.setFound_date(found_date);
			oraclesite.setId(id);
			oraclesite.setLinkman(linkman);
			oraclesite.setManager(manager);
			oraclesite.setName(name);
			oraclesite.setNote(note);
			oraclesite.setPhone(phone);
			oraclesite.setURL(website);
			oraclesite.setZip_code(zip_code);
			oraclesite.setDiqu_id(site.getDiqu_id());
			oraclesite.setSite_company(site_company) ;
			oraclesite.setSite_type(site_type) ;
			oraclesite.setCorporation(corporation) ;
			oraclesite.setFirst_student_date(first_student_date) ;
			int suc = oraclesite.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateSite</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no updateSite right");
		}
	}

	public int updateSemester(String id, String name, String start_date,
			String end_date, String start_elective, String end_elective)
			throws NoRightException {
		if (basicManagePriv.updateSemester == 1) {
			OracleSemester semester = new OracleSemester(id);
			semester.setName(name);
			semester.setStart_date(start_date);
			semester.setEnd_date(end_date);
			semester.setStart_elective(start_elective);
			semester.setEnd_elective(end_elective);
			int suc = semester.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateSemester</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no updateSemester right");
		}
	}

	public int updateTemplate(String id, String name, String content,
			String type, String pub_type, String note, String mark)
			throws NoRightException {
		if (basicManagePriv.addTemplate == 1) {
			OracleTemplate template = new OracleTemplate(id);
			template.setName(name);
			template.setContent(content);
			template.setType(type);
			template.setPub_type(pub_type);
			template.setNote(note);
			template.setMark(mark);
			int suc = template.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateTemplate</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no updateTemplate right");
		}
	}

	public List getDeps() throws NoRightException {
		if (basicManagePriv.getDep == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getDeps(null, null, null);
		} else {
			throw new NoRightException("no getDeps right");
		}
	}

	public List getEduTypes() throws NoRightException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getEduTypes(null, null, null);
		} else {
			throw new NoRightException("no getEduTypes right��");
		}
	}

	public List getGrades() throws NoRightException {
		if (basicManagePriv.getGrade == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getGrades(null, null, null);
		} else {
			throw new NoRightException("no getGrades right");
		}
	}

	public List getMajors() throws NoRightException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getMajors(null, null, null);
		} else {
			throw new NoRightException("no getMajors right");
		}
	}

	public List getSites() throws NoRightException {
		if (basicManagePriv.getSite == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSites(null, null, null);
		} else {
			throw new NoRightException("no get sites right");
		}
	}

	public List getSemesters() throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getSemesters(null, null);
		} else {
			throw new NoRightException("no get semesters right");
		}
	}

	/*
	 * public List getTemplates() throws NoRightException { if
	 * (basicManagePriv.getDep == 1) { OracleBasicInfosList basicdatalist = new
	 * OracleBasicInfosList(); return basicdatalist.getTemplates(null, null,
	 * null); } else { throw new NoRightException("no get templates right"); } }
	 */

	public List getActiveSemesters() throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("selected", "1", "="));
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getSemesters(searchProperties, null);
		} else {
			throw new NoRightException("no get semesters right");
		}
	}

	public int getActiveSemestersNum() throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("selected", "1", "="));
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getSemestersNum(searchProperties);
		} else {
			throw new NoRightException("no get semesters right");
		}
	}

	public List getDeps(Page page) throws NoRightException {
		if (basicManagePriv.getDep == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getDeps(page, null, null);
		} else {
			throw new NoRightException("no getDeps right");
		}
	}

	public List getEduTypes(Page page) throws NoRightException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getEduTypes(page, null, null);
		} else {
			throw new NoRightException("no getEduTypes right��");
		}
	}

	public List getGrades(Page page) throws NoRightException {
		if (basicManagePriv.getGrade == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getGrades(page, null, null);
		} else {
			throw new NoRightException("no getGrades right");
		}
	}

	public List getMajors(Page page) throws NoRightException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getMajors(page, null, null);
		} else {
			throw new NoRightException("no getMajors right");
		}
	}

	public List getSites(Page page) throws NoRightException {
		if (basicManagePriv.getDep == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSites(page, null, null);
		} else {
			throw new NoRightException("no get sites right");
		}
	}

	public List getSemesters(Page page) throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getSemesters(page, null, null);
		} else {
			throw new NoRightException("no get Semesters right");
		}
	}

	/*
	 * public List getTemplates(Page page) throws NoRightException { if
	 * (basicManagePriv.getDep == 1) { OracleBasicInfosList basicdatalist = new
	 * OracleBasicInfosList(); return basicdatalist.getTemplates(page, null,
	 * null); } else { throw new NoRightException("no get templates right"); } }
	 */

	public List getDeps(Page page, List searchproperty, List orderproperty)
			throws NoRightException {
		if (basicManagePriv.getDep == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getDeps(page, searchproperty, orderproperty);
		} else {
			throw new NoRightException("no get deps right");
		}
	}

	public List getEduTypes(Page page, List searchproperty, List orderproperty)
			throws NoRightException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getEduTypes(page, searchproperty,
					orderproperty);
		} else {
			throw new NoRightException("no getEduTypes right��");
		}
	}

	public List getGrades(Page page, List searchproperty, List orderproperty)
			throws NoRightException {
		if (basicManagePriv.getGrade == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getGrades(page, searchproperty, orderproperty);
		} else {
			throw new NoRightException("no getGrades right");
		}
	}

	public List getMajors(Page page, List searchproperty, List orderproperty)
			throws NoRightException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getMajors(page, searchproperty, orderproperty);
		} else {
			throw new NoRightException("no getMajors right");
		}
	}

	public List getSites(Page page, List searchProperty, List orderProperty)
			throws NoRightException {
		if (basicManagePriv.getSite == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSites(page, searchProperty, orderProperty);
		} else {
			throw new NoRightException("no get sites right");
		}
	}

	public List getSemesters(Page page, List searchProperty, List orderProperty)
			throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getSemesters(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("no get semesters right");
		}
	}

	/*
	 * public List getTemplates(Page page, List searchProperty, List
	 * orderProperty) throws NoRightException { if (basicManagePriv.getDep == 1) {
	 * OracleBasicInfosList basicdatalist = new OracleBasicInfosList(); return
	 * basicdatalist.getTemplates(page, searchProperty, orderProperty); } else {
	 * throw new NoRightException("no get templates right"); } }
	 */
	public int getDepsNum() throws NoRightException {
		if (basicManagePriv.getDep == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getDepsNum(null);
		} else {
			throw new NoRightException("no getDeps right");
		}
	}

	public int getEduTypesNum() throws NoRightException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getEduTypesNum(null);
		} else {
			throw new NoRightException("no getEduTypes right");
		}
	}

	public int getGradesNum() throws NoRightException {
		if (basicManagePriv.getGrade == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getGradesNum(null);
		} else {
			throw new NoRightException("no getGrades right");
		}
	}

	public int getMajorsNum() throws NoRightException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getMajorsNum(null);
		} else {
			throw new NoRightException("no getMajors right");
		}
	}

	public int getSitesNum() throws NoRightException {
		if (basicManagePriv.getSite == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getSitesNum(null);
		} else {
			throw new NoRightException("no get sites right");
		}
	}

	public int getSemestersNum() throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicDataList = new OracleBasicEduList();
			return basicDataList.getSemestersNum(null);
		} else {
			throw new NoRightException("no get semesters right");
		}
	}

	/*
	 * public int getTemplatesNum() throws NoRightException { if
	 * (basicManagePriv.getTemplates == 1) { OracleBasicInfosList basicDataList =
	 * new OracleBasicInfosList(); return basicDataList.getTemplatesNum(null); }
	 * else { throw new NoRightException("no getTemplates right"); } }
	 */

	public int getDepsNum(List searchProperty) throws NoRightException {
		if (basicManagePriv.getDep == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getDepsNum(searchProperty);
		} else {
			throw new NoRightException("no getDeps right");
		}
	}

	public int getEduTypesNum(List searchProperty) throws NoRightException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getEduTypesNum(searchProperty);
		} else {
			throw new NoRightException("no getEduTypes right");
		}
	}

	public int getGradesNum(List searchProperty) throws NoRightException {
		if (basicManagePriv.getGrade == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getGradesNum(searchProperty);
		} else {
			throw new NoRightException("no getGrades right");
		}
	}

	public int getMajorsNum(List searchProperty) throws NoRightException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getMajorsNum(searchProperty);
		} else {
			throw new NoRightException("no getMajors right");
		}
	}

	public int getSitesNum(List searchProperty) throws NoRightException {
		if (basicManagePriv.getSite == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getSitesNum(searchProperty);
		} else {
			throw new NoRightException("no get sites right");
		}
	}

	public int getSemestersNum(List searchProperty) throws NoRightException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicDataList = new OracleBasicEduList();
			return basicDataList.getSemestersNum(searchProperty);
		} else {
			throw new NoRightException("no get semesters right");
		}
	}

	/*
	 * public int getTemplatesNum(List searchProperty) throws NoRightException {
	 * if (basicManagePriv.getTemplates == 1) { OracleBasicInfosList
	 * basicDataList = new OracleBasicInfosList(); return
	 * basicDataList.getTemplatesNum(searchProperty); } else { throw new
	 * NoRightException("no getTemplates right"); } }
	 */

	/**
	 * ���ѧ�ڼ���״̬
	 * 
	 * @return 1 Ϊ�ɹ��� 0 Ϊʧ��
	 */
	public int reverseSemesterActive(String semesterId) {
		Semester semester = new OracleSemester(semesterId);
		int suc = semester.reverseActive();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$reverseSemesterActive</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicEntityManage#getTemplates()
	 */
	public List getTemplates() throws NoRightException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicEntityManage#getTemplates(com.whaty.platform.util.Page)
	 */
	public List getTemplates(Page page) throws NoRightException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicEntityManage#getTemplates(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List)
	 */
	public List getTemplates(Page page, List searchProperty, List orderProperty)
			throws NoRightException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicEntityManage#getTemplatesNum()
	 */
	public int getTemplatesNum() throws NoRightException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMajorCoursesNum(String aid) throws NoRightException {
		if (basicManagePriv.getMajor == 1) {
			OracleMajor major = new OracleMajor();
			return major.getMajorCoursesNum(aid);
		} else {
			throw new NoRightException("no get majors right");
		}
	}

	public List getMajorCourses(Page page, String aid) throws NoRightException {
		if (basicManagePriv.getMajor == 1) {
			OracleMajor major = new OracleMajor();
			return major.getMajorCourses(page, aid);
		} else {
			throw new NoRightException("no get majors right");
		}
	}

	public int changeMajorStatus(String aid) throws NoRightException,
			SQLException {
		if (basicManagePriv.getMajor == 1) {
			OracleMajor major = new OracleMajor();
			int suc = major.changeMajorStatus(aid);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$changeMajorStatus</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no get majors right");
		}
	}

	public List getSites(Page page, String result) throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("name", result, "like"));
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSites(page, searchProperties, null);
		} else {
			throw new PlatformException("��û����=�ѧվ��Ϣ��Ȩ��");
		}
	}

	public int getSitesNum(String result) throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("name", result, "like"));
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSitesNum(searchProperties);
		} else {
			throw new PlatformException("��û����=�ѧվ��Ϣ��Ȩ��");
		}
	}

	public List getGrades(Page page, String name, String start_date,
			String end_date) throws PlatformException {
		if (basicManagePriv.getGrade == 1) {
			List searchProperties = new ArrayList();
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (start_date != null && !start_date.equals("")) {
				searchProperties.add(new SearchProperty("start_date",
						start_date, "d>="));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("end_date", end_date,
						"d<="));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getGrades(page, searchProperties, null);
		} else {
			throw new PlatformException("��û����=�ѧվ��Ϣ��Ȩ��");
		}
	}

	public int getGradesNum(String name, String start_date, String end_date)
			throws PlatformException {
		if (basicManagePriv.getGrade == 1) {
			List searchProperties = new ArrayList();
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (start_date != null && !start_date.equals("")) {
				searchProperties.add(new SearchProperty("start_date",
						start_date, "d>="));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("end_date", end_date,
						"d<="));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getGradesNum(searchProperties);
		} else {
			throw new PlatformException("��û����=�ѧվ��Ϣ��Ȩ��");
		}
	}

	public InfoManagerPriv getInfoManagerPriv(String managerId)
			throws PlatformException {
		InfoFactory infoFactory = InfoFactory.getInstance();
		InfoManagerPriv infoManagerPriv = infoFactory
				.getInfoManagerPriv(managerId);
		infoManagerPriv.addNews = basicManagePriv.addNews;
		infoManagerPriv.confirmNews = basicManagePriv.confirmNews;
		infoManagerPriv.copyNews = basicManagePriv.copyNews;
		infoManagerPriv.deleteNews = basicManagePriv.deleteNews;
		infoManagerPriv.getNews = basicManagePriv.getNews;
		infoManagerPriv.lockNews = basicManagePriv.lockNews;
		infoManagerPriv.popNews = basicManagePriv.popNews;
		infoManagerPriv.topNews = basicManagePriv.topNews;
		infoManagerPriv.updateNews = basicManagePriv.updateNews;
		return infoManagerPriv;
	}

	public InfoManagerPriv getInfoManagerPriv(String managerId,
			String newsTypeId) throws PlatformException {
		InfoFactory infoFactory = InfoFactory.getInstance();
		return infoFactory.getInfoManagerPriv(managerId, newsTypeId);
	}

	public InfoManage getInfoManage(InfoManagerPriv infoManagerPriv)
			throws PlatformException {
		InfoFactory infoFactory = InfoFactory.getInstance();
		return infoFactory.creatInfoManage(infoManagerPriv);
	}

	public SmsManage getSmsManage() throws PlatformException {
		SmsFactory smsFactory = SmsFactory.getInstance();
		OracleSmsManagerPriv amanagerpriv = new OracleSmsManagerPriv();
		amanagerpriv.addSms = basicManagePriv.addSms;
		amanagerpriv.batchImportMobiles = basicManagePriv.batchImportMobiles;
		amanagerpriv.checkSms = basicManagePriv.checkSms;
		amanagerpriv.deleteSms = basicManagePriv.deleteSms;
		amanagerpriv.getSms = basicManagePriv.getSms;
		amanagerpriv.sendSms = basicManagePriv.sendSms;
		amanagerpriv.updateSms = basicManagePriv.updateSms;

		return smsFactory.creatSmsManage(amanagerpriv);
	}

	public List getCourses() throws NoRightException {
		if (basicManagePriv.getCourse == 1) {
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getCourses(null, null, null);
		} else {
			throw new NoRightException("no get courses right");
		}
	}

	public Course getCourse(String course_id) throws NoRightException {
		if (basicManagePriv.getCourse == 1) {
			return new OracleCourse(course_id);
		} else {
			throw new NoRightException("no get courses right");
		}
	}

	public List getAllEduTypes() throws NoRightException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getEduTypes(null, null, null);
		} else {
			throw new NoRightException("no getEduTypes right��");
		}
	}

	public List getAllGrades() throws NoRightException {
		if (basicManagePriv.getGrade == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getGrades(null, null, null);
		} else {
			throw new NoRightException("no getGrades right");
		}
	}

	public List getAllMajors() throws NoRightException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("m.id", "0", "<>"));
			return basicdatalist.getMajors(null, searchProperties, null);
		} else {
			throw new NoRightException("no getMajors right");
		}
	}

	public List getAllSites() throws NoRightException {
		if (basicManagePriv.getSite == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSites(null, null, null);
		} else {
			throw new NoRightException("no get sites right");
		}
	}

	public List getInfoList(Page page) throws PlatformException {
		OracleInfoList infoList = new OracleInfoList();
		String siteId = this.basicManagePriv.getSite_id();
		return infoList.getNewsListForSubAdmin(page, siteId);
	}

	public int getInfoListNum() throws PlatformException {
		OracleInfoList infoList = new OracleInfoList();
		String siteId = this.basicManagePriv.getSite_id();
		return infoList.getNewsListNumForSubAdmin(siteId);

	}
	
	public List getInfoList(Page page, String newsTypeId) throws PlatformException {
		OracleInfoList infoList = new OracleInfoList();
		String siteId = this.basicManagePriv.getSite_id();
		return infoList.getNewsListForSubAdmin(page, siteId, newsTypeId);
	}

	public int getInfoListNum(String newsTypeId) throws PlatformException {
		OracleInfoList infoList = new OracleInfoList();
		String siteId = this.basicManagePriv.getSite_id();
		return infoList.getNewsListNumForSubAdmin(siteId, newsTypeId);
	}

	public List getPlanEduTypes(String batchId, String siteId)
			throws NoRightException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			List orderProperties = new ArrayList();
			if (batchId != null && !batchId.equals("")) {
				searchProperties.add(new SearchProperty("batch_id", batchId,
						"="));
			}
			if (siteId != null && !siteId.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			}
			return basicdatalist.getPlanEduTypes(searchProperties,
					orderProperties);
		} else {
			throw new NoRightException("no getEduTypes right��");
		}
	}

	public List getPlanMajors(String batchId, String siteId, String eduType)
			throws NoRightException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			List orderProperties = new ArrayList();
			if (batchId != null && !batchId.equals("")) {
				searchProperties.add(new SearchProperty("batch_id", batchId,
						"="));
			}
			if (siteId != null && !siteId.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			}
			if (eduType != null && !eduType.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id", eduType,
						"="));
			}
			return basicdatalist.getPlanMajors(searchProperties,
					orderProperties);
		} else {
			throw new NoRightException("no getEduTypes right��");
		}
	}

//	public WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
//			throws PlatformException {
//		ServletContext application = session.getServletContext();
//		EntityConfig config = (EntityConfig) application
//				.getAttribute("entityConfig");
//		PlatformConfig platformConfig = (PlatformConfig) application
//				.getAttribute("platformConfig");
//		WhatyEditorConfig editorConfig = new WhatyEditorConfig();
//		editorConfig.setAppRootURI(platformConfig.getPlatformWebAppUriPath());
//		editorConfig.setEditorRefURI("WhatyEditor/");
//		editorConfig.setEditorURI(platformConfig.getPlatformWebAppUriPath()
//				+ "WhatyEditor/");
//		editorConfig.setUploadAbsPath(config.getEntityWebIncomingAbsPath()
//				+ File.separator);
//		editorConfig.setUploadURI(config.getEntityWebIncomingUriPath());
//		return editorConfig;
//	}

	public int updateRecruitStudentInfo(String id, String name,
			String password, String gender, String folk, String birthday,
			String zzmm, String edu, String site_id, String edutype_id,
			String major_id, String card_type, String card_no, String hometown,
			String[] email, String postalcode, String address,
			String[] mobilephone, String[] phone, String considertype,
			String status, String school_name, String school_code,
			String graduate_year, String graduate_cardno)
			throws PlatformException, NoRightException {
		if (basicManagePriv.updateRecruitStudentInfo == 1) {
			OracleRecruitStudent student = new OracleRecruitStudent();
			student.setId(id);
			student.setName(name);
			student.setPassword(password);

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
			int suc = student.update();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$updateRecruitStudentInfo</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.SITEMANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException(" no updateStudentInfo right��");
		}
	}

	public Student getStudentInfo(String studentId) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			OracleStudent student = new OracleStudent(studentId);
			return student;
		} else {
			throw new PlatformException(" ��û�в鿴ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	@Override
	public List getGraduateStudent(Page page, String name, String reg_no, String site, String edutype_id, String major_id ,String grade_id,String teacher_id) {
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
		List searchProperty = new ArrayList();
		if (reg_no != null && !reg_no.equals("")) {
			searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("studentname", name, "="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperty.add(new SearchProperty("gradeid", grade_id, "="));
		}
		if (edutype_id != null && !edutype_id.equals("")) {
			searchProperty.add(new SearchProperty("edutypeid", edutype_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperty.add(new SearchProperty("majorid", major_id, "="));
		}
		searchProperty.add(new SearchProperty("siteteacherid", teacher_id, "="));
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("gradeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("edutypeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("majorid", OrderProperty.ASC));
		return basicdatalist.getSelectGraduateStudentAtCurrentPici(page,
				searchProperty, orderProperty);
	}

	@Override
	public int getGraduateStudentNum(String name, String reg_no, String site, String edutype_id, String major_id,String grade_id,String teacher_id) {
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
		List searchProperty = new ArrayList();
		if (reg_no != null && !reg_no.equals("")) {
			searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("studentname", name, "="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperty.add(new SearchProperty("gradeid", grade_id, "="));
		}
		if (edutype_id != null && !edutype_id.equals("")) {
			searchProperty.add(new SearchProperty("edutypeid", edutype_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperty.add(new SearchProperty("majorid", major_id, "="));
		}
		searchProperty.add(new SearchProperty("siteteacherid", teacher_id, "="));
		return basicdatalist.getSelectGraduateStudentAtCurrentPiciNum(searchProperty);
	}
	
}