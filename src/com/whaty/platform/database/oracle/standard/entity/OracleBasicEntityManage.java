package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.CampusFactory;
import com.whaty.platform.campus.CampusManage;
import com.whaty.platform.campus.CampusNormalManage;
import com.whaty.platform.campus.user.CampusInfoManagerPriv;
import com.whaty.platform.campus.user.CampusManagerPriv;
import com.whaty.platform.database.oracle.standard.campus.user.OracleCampusInfoManagerPriv;
import com.whaty.platform.database.oracle.standard.campus.user.OracleCampusManagerPriv;
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
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSiteDiqu;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleTeachBook;
import com.whaty.platform.database.oracle.standard.entity.fee.OracleChargeLevelManage;
import com.whaty.platform.database.oracle.standard.entity.fee.OracleFeeManage;
import com.whaty.platform.database.oracle.standard.entity.fee.OracleFeeManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleBasicRecruitList;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitBatch;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitPlan;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.info.OracleInfoList;
import com.whaty.platform.database.oracle.standard.info.OracleNewsType;
import com.whaty.platform.database.oracle.standard.info.OracleTemplate;
import com.whaty.platform.database.oracle.standard.leaveword.user.OracleLeaveWordManagerPriv;
import com.whaty.platform.entity.BasicEntityManage;
import com.whaty.platform.entity.basic.BasicEntityList;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.entity.basic.Dep;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.entity.basic.Grade;
import com.whaty.platform.entity.basic.Major;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.entity.basic.Site;
import com.whaty.platform.entity.basic.SiteDiqu;
import com.whaty.platform.entity.basic.TeachBook;
import com.whaty.platform.entity.fee.ChargeLevelManage;
import com.whaty.platform.entity.fee.FeeManage;
import com.whaty.platform.entity.recruit.RecruitBatch;
import com.whaty.platform.entity.recruit.RecruitEduInfo;
import com.whaty.platform.entity.recruit.RecruitLimit;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.info.InfoFactory;
import com.whaty.platform.info.InfoManage;
import com.whaty.platform.info.Template;
import com.whaty.platform.info.user.InfoManagerPriv;
import com.whaty.platform.leaveword.LeaveWordFactory;
import com.whaty.platform.leaveword.LeaveWordManage;
import com.whaty.platform.leaveword.LeaveWordNormalManage;
import com.whaty.platform.leaveword.user.LeaveWordManagerPriv;
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

public class OracleBasicEntityManage extends BasicEntityManage {

	ManagerPriv basicManagePriv;

	public OracleBasicEntityManage(ManagerPriv amanagerpriv) {
		this.basicManagePriv = amanagerpriv;
	}

	public Course createCourse() throws PlatformException {
		return new OracleCourse();
	}

	public Dep creatDep(String id, String name) throws PlatformException {
		return null;
	}

	public EduType creatEduType(String id, String name)
			throws PlatformException {
		return null;
	}

	public Grade creatGrade(String id, String name) throws PlatformException {
		return null;
	}

	public Major creatMajor(String id, String name) throws PlatformException {
		return null;
	}

	public Site creatSite(String id, String name) throws PlatformException {
		return null;
	}

	public Semester creatSemester(String id, String name)
			throws PlatformException {
		return null;
	}

	public Template creatTemplate(String id, String name)
			throws PlatformException {
		return null;
	}

	public Dep getDep(String aid) throws PlatformException {
		if (basicManagePriv.getDep == 1) {
			return new OracleDep(aid);
		} else {
			throw new PlatformException("您没有查看院系信息的权限！");
		}

	}

	public EduType getEduType(String aid) throws PlatformException {
		if (basicManagePriv.getEduType == 1) {
			return new OracleEduType(aid);
		} else {
			throw new PlatformException("您没有查看层次信息的权限！");
		}
	}

	public Grade getGrade(String aid) throws PlatformException {
		if (basicManagePriv.getGrade == 1) {
			return new OracleGrade(aid);
		} else {
			throw new PlatformException("您没有查看年级信息的权限！");
		}
	}

	public Major getMajor(String aid) throws PlatformException {
		if (basicManagePriv.getMajor == 1) {
			return new OracleMajor(aid);
		} else {
			throw new PlatformException("您没有查看专业信息的权限！");
		}
	}

	public Site getSite(String aid) throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			return new OracleSite(aid);
		} else {
			throw new PlatformException("您没有查看教学站信息的权限！");
		}
	}

	public int changeSiteShow(String aid) throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			OracleSite site = new OracleSite(aid);
			int suc = site.changeShow();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$changeSiteShow</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有浏览教学站信息的权限");
		}
	}

	public Semester getSemester(String aid) throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			return new OracleSemester(aid);
		} else {
			throw new PlatformException("您没有查看学期信息的权限！");
		}
	}

	public Template getTemplate(String aid) throws PlatformException {
		if (basicManagePriv.getTemplate == 1) {
			return new OracleTemplate(aid);
		} else {
			throw new PlatformException("您没有查看模板信息的权限! ");
		}
	}

	public int deleteDep(String id) throws PlatformException {
		if (basicManagePriv.deleteDep == 1) {
			OracleDep dep = new OracleDep(id);
			int suc = dep.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteDep</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有查看教学站信息的权限！");
		}
	}

	public int deleteEduType(String id) throws PlatformException {
		if (basicManagePriv.deleteEduType == 1) {
			int suc = new OracleEduType(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteEduType</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有查看教学站信息的权限！");
		}
	}

	public int deleteGrade(String id) throws PlatformException {
		if (basicManagePriv.deleteGrade == 1) {
			int suc = new OracleGrade(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteGrade</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有查看教学站信息的权限！");
		}
	}

	public int deleteMajor(String id) throws PlatformException {
		if (basicManagePriv.deleteMajor == 1) {
			int suc = new OracleMajor(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteMajor</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有查看教学站信息的权限！");
		}
	}

	public int deleteSite(String id) throws PlatformException {
		if (basicManagePriv.deleteSite == 1) {
			int suc = new OracleSite(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteSite</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除教学站信息的权限！");
		}
	}

	public int deleteSemester(String id) throws PlatformException {
		if (basicManagePriv.deleteSemester == 1) {
			int suc = new OracleSemester(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteSemester</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除学期信息的权限！");
		}
	}

	public int deleteTemplate(String id) throws PlatformException {
		if (basicManagePriv.deleteTemplate == 1) {
			int suc = new OracleTemplate(id).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTemplate</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除模板信息的权限！");
		}
	}

	public void addCourseBatch(List courseList) throws PlatformException {
		if (basicManagePriv.addCourseBatch == 1) {
			OracleBasicBatchActivity batch = new OracleBasicBatchActivity();
			batch.courseAddBatch(courseList, basicManagePriv.getMajor());
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addCourseBatch</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有添加课程的权限");
		}
	}

	public void addBookBatch(String srcFile) throws PlatformException {
		if (basicManagePriv.addTeachBook == 1) {
			OracleBasicBatchActivity batch = new OracleBasicBatchActivity();
			batch.bookAddBatch(srcFile);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addBookBatch</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有添加教材的权限");
		}
	}

	public int addDep(String id, String name, String forshort, String note)
			throws PlatformException {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addDep</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加院系信息的权限！");
		}
	}

	public int addEduType(String id, String name, String forshort,
			String introduction) throws PlatformException {
		if (basicManagePriv.addEduType == 1) {
			OracleEduType oracleedutype = new OracleEduType();
			oracleedutype.setId(id);
			oracleedutype.setName(name);
			oracleedutype.setForshort(forshort);
			oracleedutype.setIntroduction(introduction);
			if (oracleedutype.isIdExist() < 1) {
				int suc = oracleedutype.add();
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.basicManagePriv.getSso_id()
										+ "</whaty><whaty>BEHAVIOR$|$addEduType</whaty><whaty>STATUS$|$"
										+ suc
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.MANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
				return suc;
			} else {
				throw new PlatformException("当前ID已被使用");
			}
		} else {
			throw new PlatformException("您没有添加层次的权限！");
		}
	}

	public int addGrade(String name, String begin_date, String grade_id)
			throws PlatformException {
		if (basicManagePriv.addGrade == 1) {
			OracleGrade oraclegrade = new OracleGrade();
			oraclegrade.setId(grade_id);
			oraclegrade.setName(name);
			oraclegrade.setBegin_date(begin_date);
			if (oraclegrade.isUniqueID() < 1) {
				int suc = oraclegrade.add();
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.basicManagePriv.getSso_id()
										+ "</whaty><whaty>BEHAVIOR$|$addGrade</whaty><whaty>STATUS$|$"
										+ suc
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.MANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
				return suc;
			} else {
				throw new PlatformException("当前年级编号已经被使用，请更换");
			}
		} else {
			throw new PlatformException("您没有添加年级的权限！");
		}
	}

	public int addMajor(String id, String name, String dep_id,
			String major_alias, String major_link, String note)
			throws PlatformException {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addMajor</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加专业的权限！");
		}
	}

	public int addSite(String id, String name, String address, String email,
			String fax, String found_date, String linkman, String manager,
			String phone, String zip_code, String note, String URL,
			String diquId) throws PlatformException {
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
			oraclesite.setURL(URL);
			oraclesite.setDiqu_id(diquId);
			int suc = oraclesite.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addSite</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加站点的权限！");
		}
	}

	
	
	@Override
	public int addSite(String id, String name, String address, String email, String fax, String found_date, String linkman, String manager, String phone, String zip_code, String note, String URL, String diquId, String site_company, String site_type, String corporation, String first_student_date) throws PlatformException {
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
			oraclesite.setURL(URL);
			oraclesite.setDiqu_id(diquId);
			oraclesite.setSite_company(site_company) ;
			oraclesite.setSite_type(site_type) ;
			oraclesite.setCorporation(corporation) ;
			oraclesite.setFirst_student_date(first_student_date) ;
			int suc = oraclesite.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addSite</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加站点的权限！");
		}
	}

	public int addSemester(String name, String start_date, String end_date,
			String start_elective, String end_elective, String start_test,
			String end_test) throws PlatformException {
		if (basicManagePriv.addSemester == 1) {
			OracleSemester semester = new OracleSemester();
			semester.setName(name);
			semester.setStart_date(start_date);
			semester.setEnd_date(end_date);
			semester.setStart_elective(start_elective);
			semester.setEnd_elective(end_elective);
			semester.setStart_test(start_test);
			semester.setEnd_test(end_test);
			int suc = semester.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addSemester</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加学期的权限！");
		}
	}

	public int addTemplate(String id, String name, String content, String type,
			String pub_type, String note, String mark) throws PlatformException {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addTemplate</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("no addTemplate right");
		}
	}

	public int updateDep(String id, String name, String forshort, String note)
			throws PlatformException {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateDep</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改院系信息的权限！");
		}
	}

	public int updateEduType(String id, String name, String forshort,
			String introduction) throws PlatformException {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateEduType</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改层次信息的权限！");
		}
	}

	public int updateGrade(String id, String name, String begin_date)
			throws PlatformException {
		if (basicManagePriv.updateGrade == 1) {
			OracleGrade oraclegrade = new OracleGrade(id);
			oraclegrade.setName(name);
			oraclegrade.setBegin_date(begin_date);
			int suc = oraclegrade.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateGrade</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改年级信息的权限！");
		}
	}

	public int updateMajor(String id, String name, String major_alias,
			String major_link, String note) throws PlatformException {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateMajor</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改专业信息的权限！");
		}
	}

	public int updateSite(String id, String name, String address, String email,
			String fax, String found_date, String linkman, String manager,
			String phone, String zip_code, String webSite, String note,
			String diquId) throws PlatformException {
		if (basicManagePriv.updateSite == 1) {
			OracleSite oraclesite = new OracleSite();
			oraclesite.setId(id);
			oraclesite.setAddress(address);
			oraclesite.setEmail(email);
			oraclesite.setFax(fax);
			oraclesite.setFound_date(found_date);
			oraclesite.setLinkman(linkman);
			oraclesite.setManager(manager);
			oraclesite.setName(name);
			oraclesite.setURL(webSite);
			oraclesite.setNote(note);
			oraclesite.setPhone(phone);
			oraclesite.setZip_code(zip_code);
			oraclesite.setDiqu_id(diquId);
			int suc = oraclesite.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateSite</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改站点信息的权限！");
		}
	}

	@Override
	public int updateSite(String id, String name, String address, String email, String fax, String found_date, String linkman, String manager, String phone, String zip_code, String webSite, String note, String diquId, String site_company, String site_type, String corporation, String first_student_date) throws PlatformException {
		if (basicManagePriv.updateSite == 1) {
			OracleSite oraclesite = new OracleSite();
			oraclesite.setId(id);
			oraclesite.setAddress(address);
			oraclesite.setEmail(email);
			oraclesite.setFax(fax);
			oraclesite.setFound_date(found_date);
			oraclesite.setLinkman(linkman);
			oraclesite.setManager(manager);
			oraclesite.setName(name);
			oraclesite.setURL(webSite);
			oraclesite.setNote(note);
			oraclesite.setPhone(phone);
			oraclesite.setZip_code(zip_code);
			oraclesite.setDiqu_id(diquId);
			oraclesite.setSite_company(site_company) ;
			oraclesite.setSite_type(site_type) ;
			oraclesite.setCorporation(corporation) ;
			oraclesite.setFirst_student_date(first_student_date) ;
			int suc = oraclesite.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateSite</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改站点信息的权限！");
		}
	}

	public int updateSemester(String id, String name, String start_date,
			String end_date, String start_elective, String end_elective,
			String start_test, String end_test) throws PlatformException {
		if (basicManagePriv.updateSemester == 1) {
			OracleSemester semester = new OracleSemester(id);
			semester.setName(name);
			semester.setStart_date(start_date);
			semester.setEnd_date(end_date);
			semester.setStart_elective(start_elective);
			semester.setEnd_elective(end_elective);
			semester.setStart_test(start_test);
			semester.setEnd_test(end_test);
			int suc = semester.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateSemester</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改学期信息的权限！");
		}
	}

	public int updateTemplate(String id, String name, String content,
			String type, String pub_type, String note, String mark)
			throws PlatformException {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateTemplate</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("no updateTemplate right");
		}
	}

	public List getDeps() throws PlatformException {
		if (basicManagePriv.getDep == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List OrderProperties = new ArrayList();
			OrderProperties.add(new OrderProperty("id"));
			return basicdatalist.getDeps(null, null, OrderProperties);
		} else {
			throw new PlatformException("您没有获得院系信息的权限！");
		}
	}

	public List getAllEduTypes() throws PlatformException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List OrderProperties = new ArrayList();
			OrderProperties.add(new OrderProperty("id"));
			return basicdatalist.getEduTypes(null, null, OrderProperties);
		} else {
			throw new PlatformException("您没有获得层次信息的权限！");
		}
	}

	public List getEduTypes() throws PlatformException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("id", basicManagePriv
					.getEduTypeList(), "in"));
			List OrderProperties = new ArrayList();
			OrderProperties.add(new OrderProperty("id"));
			return basicdatalist.getEduTypes(null, searchProperties,
					OrderProperties);
		} else {
			throw new PlatformException("您没有获得层次信息的权限！");
		}
	}

	public List getAllGrades() throws PlatformException {
		if (basicManagePriv.getGrade == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List OrderProperties = new ArrayList();
			OrderProperties.add(new OrderProperty("name", OrderProperty.DESC));
			return basicdatalist.getGrades(null, null, OrderProperties);
		} else {
			throw new PlatformException("您没有获得年级信息的权限！");
		}
	}

	public List getGrades() throws PlatformException {
		if (basicManagePriv.getGrade == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("id", basicManagePriv
					.getGradeList(), "in"));
			return basicdatalist.getGrades(null, searchProperties, null);
		} else {
			throw new PlatformException("您没有获得年级信息的权限！");
		}
	}

	public List getAllMajors() throws PlatformException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("m.id", "0", "<>"));
			List OrderProperties = new ArrayList();
			OrderProperties.add(new OrderProperty("id"));
			return basicdatalist.getMajors(null, searchProperties,
					OrderProperties);
		} else {
			throw new PlatformException("您没有获得专业信息的权限！");
		}
	}

	public List getMajors() throws PlatformException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("m.id", "0", "<>"));
			searchProperties.add(new SearchProperty("m.id", basicManagePriv
					.getMajorList(), "in"));
			List OrderProperties = new ArrayList();
			OrderProperties.add(new OrderProperty("id"));
			return basicdatalist.getMajors(null, searchProperties,
					OrderProperties);
		} else {
			throw new PlatformException("您没有获得专业信息的权限！");
		}
	}

	public List getAllSites() throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List OrderProperties = new ArrayList();
			OrderProperties.add(new OrderProperty("id"));
			return basicdatalist.getSites(null, null, OrderProperties);
		} else {
			throw new PlatformException("您没有获得站点信息的权限！");
		}
	}

	public List getSites() throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("id", basicManagePriv
					.getSiteList(), "in"));
			searchProperties.add(new SearchProperty("id", basicManagePriv
					.getSiteList(), "in"));
			List OrderProperties = new ArrayList();
			OrderProperties.add(new OrderProperty("id"));
			return basicdatalist.getSites(null, searchProperties,
					OrderProperties);
		} else {
			throw new PlatformException("您没有获得站点信息的权限！");
		}
	}
	public Map getSitesMap() throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("id", basicManagePriv
					.getSiteList(), "in"));
			searchProperties.add(new SearchProperty("id", basicManagePriv
					.getSiteList(), "in"));
			List OrderProperties = new ArrayList();
			OrderProperties.add(new OrderProperty("id"));
			return basicdatalist.getSitesMap();
		} else {
			throw new PlatformException("您没有获得站点信息的权限！");
		}
	}

	public List getSemesters() throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getSemesters(null, null);
		} else {
			throw new PlatformException("您没有获得学期信息的权限！");
		}
	}

	public List getCourses() throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getCourses(null, null, null);
		} else {
			throw new PlatformException("您没有获得课程信息的权限！");
		}
	}

	public Course getCourse(String id) throws PlatformException {
		if (basicManagePriv.getCourse == 1) {
			OracleCourse course = new OracleCourse(id);
			return course;
		} else {
			throw new PlatformException("您没有获得课程信息的权限！");
		}
	}

	/*
	 * public List getTemplates() throws PlatformException { if
	 * (basicManagePriv.getDep == 1) { OracleBasicInfosList basicdatalist = new
	 * OracleBasicInfosList(); return basicdatalist.getTemplates(null, null,
	 * null); } else { throw new PlatformException("no get templates right"); } }
	 */

	public List getActiveSemesters() throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("selected", "1", "="));
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getSemesters(searchProperties, null);
		} else {
			throw new PlatformException("没有获得学期的权限！");
		}
	}

	public int getActiveSemestersNum() throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("selected", "1", "="));
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getSemestersNum(searchProperties);
		} else {
			throw new PlatformException("没有获得学期的权限！");
		}
	}

	public List getDeps(Page page) throws PlatformException {
		if (basicManagePriv.getDep == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getDeps(page, null, null);
		} else {
			throw new PlatformException("没有获得院系信息的权限！");
		}
	}

	public List getEduTypes(Page page) throws PlatformException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getEduTypes(page, null, null);
		} else {
			throw new PlatformException("没有获得层次信息的权限！");
		}
	}

	public List getGrades(Page page) throws PlatformException {
		if (basicManagePriv.getGrade == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getGrades(page, null, null);
		} else {
			throw new PlatformException("没有获得年级信息的权限！");
		}
	}

	public List getMajors(Page page) throws PlatformException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("m.id", "0", "<>"));
			return basicdatalist.getMajors(page, searchProperties, null);
		} else {
			throw new PlatformException("没有获得专业信息的权限！");
		}
	}

	public List getSites(Page page) throws PlatformException {
		if (basicManagePriv.getDep == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSites(page, null, null);
		} else {
			throw new PlatformException("没有获得站点信息的权限！");
		}
	}

	public List getSemesters(Page page) throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getSemesters(page, null, null);
		} else {
			throw new PlatformException("没有获得学期信息的权限！");
		}
	}

	/*
	 * public List getTemplates(Page page) throws PlatformException { if
	 * (basicManagePriv.getDep == 1) { OracleBasicInfosList basicdatalist = new
	 * OracleBasicInfosList(); return basicdatalist.getTemplates(page, null,
	 * null); } else { throw new PlatformException("no get templates right"); } }
	 */

	public List getDeps(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		if (basicManagePriv.getDep == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getDeps(page, searchproperty, orderproperty);
		} else {
			throw new PlatformException("没有获得院系信息的权限！");
		}
	}

	public List getEduTypes(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getEduTypes(page, searchproperty,
					orderproperty);
		} else {
			throw new PlatformException("没有获得层次信息的权限！");
		}
	}

	public List getGrades(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		if (basicManagePriv.getGrade == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			if (orderproperty == null)
				orderproperty = new ArrayList();
			orderproperty.add(new OrderProperty("name", OrderProperty.DESC));
			return basicdatalist.getGrades(page, searchproperty, orderproperty);
		} else {
			throw new PlatformException("没有获得年级信息的权限！");
		}
	}

	public List getMajors(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getMajors(page, searchproperty, orderproperty);
		} else {
			throw new PlatformException("没有获得专业信息的权限！");
		}
	}

	public List getSites(Page page, List searchProperty, List orderProperty)
			throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSites(page, searchProperty, orderProperty);
		} else {
			throw new PlatformException("没有获得站点信息的权限！");
		}
	}

	public List getSemesters(Page page, List searchProperty, List orderProperty)
			throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getSemesters(page, searchProperty,
					orderProperty);
		} else {
			throw new PlatformException("没有获得学期信息的权限！");
		}
	}

	/*
	 * public List getTemplates(Page page, List searchProperty, List
	 * orderProperty) throws PlatformException { if (basicManagePriv.getDep ==
	 * 1) { OracleBasicInfosList basicdatalist = new OracleBasicInfosList();
	 * return basicdatalist.getTemplates(page, searchProperty, orderProperty); }
	 * else { throw new PlatformException("no get templates right"); } }
	 */
	public int getDepsNum() throws PlatformException {
		if (basicManagePriv.getDep == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getDepsNum(null);
		} else {
			throw new PlatformException("没有获得院系信息的权限！");
		}
	}

	public int getEduTypesNum() throws PlatformException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getEduTypesNum(null);
		} else {
			throw new PlatformException("没有获得层次信息的权限！");
		}
	}

	public int getGradesNum() throws PlatformException {
		if (basicManagePriv.getGrade == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getGradesNum(null);
		} else {
			throw new PlatformException("没有获得年级信息的权限！");
		}
	}

	public int getMajorsNum() throws PlatformException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("m.id", "0", "<>"));
			return basicdatalist.getMajorsNum(searchProperties);
		} else {
			throw new PlatformException("您没有获得专业信息的权限！");
		}
	}

	public int getSitesNum() throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getSitesNum(null);
		} else {
			throw new PlatformException("no get sites right");
		}
	}

	public int getSemestersNum() throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicDataList = new OracleBasicEduList();
			return basicDataList.getSemestersNum(null);
		} else {
			throw new PlatformException("您没有获得学期信息的权限！");
		}
	}

	/*
	 * public int getTemplatesNum() throws PlatformException { if
	 * (basicManagePriv.getTemplates == 1) { OracleBasicInfosList basicDataList =
	 * new OracleBasicInfosList(); return basicDataList.getTemplatesNum(null); }
	 * else { throw new PlatformException("no getTemplates right"); } }
	 */

	public int getDepsNum(List searchProperty) throws PlatformException {
		if (basicManagePriv.getDep == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getDepsNum(searchProperty);
		} else {
			throw new PlatformException("no getDeps right");
		}
	}

	public int getEduTypesNum(List searchProperty) throws PlatformException {
		if (basicManagePriv.getEduType == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getEduTypesNum(searchProperty);
		} else {
			throw new PlatformException("no getEduTypes right");
		}
	}

	public int getGradesNum(List searchProperty) throws PlatformException {
		if (basicManagePriv.getGrade == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getGradesNum(searchProperty);
		} else {
			throw new PlatformException("no getGrades right");
		}
	}

	public int getMajorsNum(List searchProperty) throws PlatformException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getMajorsNum(searchProperty);
		} else {
			throw new PlatformException("您没有获得专业信息的权限！");
		}
	}

	public int getSitesNum(List searchProperty) throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			OracleBasicEntityList basicDataList = new OracleBasicEntityList();
			return basicDataList.getSitesNum(searchProperty);
		} else {
			throw new PlatformException("no get sites right");
		}
	}

	public int getSemestersNum(List searchProperty) throws PlatformException {
		if (basicManagePriv.getSemester == 1) {
			OracleBasicEduList basicDataList = new OracleBasicEduList();
			return basicDataList.getSemestersNum(searchProperty);
		} else {
			throw new PlatformException("您没有获得学期信息的权限！");
		}
	}

	/*
	 * public int getTemplatesNum(List searchProperty) throws PlatformException {
	 * if (basicManagePriv.getTemplates == 1) { OracleBasicInfosList
	 * basicDataList = new OracleBasicInfosList(); return
	 * basicDataList.getTemplatesNum(searchProperty); } else { throw new
	 * PlatformException("no getTemplates right"); } }
	 */

	/**
	 * 更改学期激活状态
	 * 
	 * @return 1 为成功， 0 为失败
	 * @throws PlatformException
	 */
	public int reverseSemesterActive(String semesterId)
			throws PlatformException {
		if (basicManagePriv.activeSemester == 1) {
			Semester semester = new OracleSemester(semesterId);
			int suc = semester.reverseActive();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$reverseSemesterActive</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有激活学期的权限!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicEntityManage#getTemplates()
	 */
	public List getTemplates() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicEntityManage#getTemplates(com.whaty.platform.util.Page)
	 */
	public List getTemplates(Page page) throws PlatformException {
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
			throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicEntityManage#getTemplatesNum()
	 */
	public int getTemplatesNum() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMajorCoursesNum(String aid) throws PlatformException {
		if (basicManagePriv.getMajor == 1) {
			OracleMajor major = new OracleMajor();
			return major.getMajorCoursesNum(aid);
		} else {
			throw new PlatformException("您没有获得专业信息的权限！");
		}
	}

	public List getMajorCourses(Page page, String aid) throws PlatformException {
		if (basicManagePriv.getMajor == 1) {
			OracleMajor major = new OracleMajor();
			return major.getMajorCourses(page, aid);
		} else {
			throw new PlatformException("您没有获得专业信息的权限！");
		}
	}

	public int changeMajorStatus(String aid) throws PlatformException {
		if (basicManagePriv.updateMajor == 1) {
			OracleMajor major = new OracleMajor();
			int suc = major.changeMajorStatus(aid);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$changeMajorStatus</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有获得专业信息的权限！");
		}
	}

	public List getSites(Page page, String result) throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("name", result, "like"));
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSites(page, searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览教学站信息的权限");
		}
	}

	public int getSitesNum(String result) throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("name", result, "like"));
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSitesNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览教学站信息的权限");
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
			if (end_date != null && !end_date.equals("")) {
				searchProperties.add(new SearchProperty("end_date", end_date,
						"d<="));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getGrades(page, searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览年级信息的权限");
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
			if (end_date != null && !end_date.equals("")) {
				searchProperties.add(new SearchProperty("end_date", end_date,
						"d<="));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getGradesNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览年级信息的权限");
		}
	}

	public List getSites(Page page, String id, String name)
			throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("")) {
				searchProperties.add(new SearchProperty("id", id, "like"));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}

			List OrderProperties = new ArrayList();
			OrderProperties.add(new OrderProperty("id"));

			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSites(page, searchProperties,
					OrderProperties);
		} else {
			throw new PlatformException("您没有浏览教学站信息的权限");
		}
	}

	public List getSites(String sites) throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			List searchProperties = new ArrayList();
			if (sites != null && !sites.equals("")) {
				searchProperties.add(new SearchProperty("id", sites, "in"));
			}
			List OrderProperties = new ArrayList();
			OrderProperties.add(new OrderProperty("id"));

			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSites(null, searchProperties,
					OrderProperties);
		} else {
			throw new PlatformException("您没有浏览教学站信息的权限");
		}
	}

	public List getSitesByDiqu(Page page, String diquId)
			throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			List searchProperties = new ArrayList();
			if (diquId != null && !diquId.equals("")) {
				searchProperties.add(new SearchProperty("diqu_id", diquId));
			}
			List orderProperties = new ArrayList();
			orderProperties.add(new OrderProperty("to_number(right)"));
			orderProperties.add(new OrderProperty("id"));
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSites(page, searchProperties,
					orderProperties);
		} else {
			throw new PlatformException("您没有浏览教学站信息的权限");
		}
	}

	public int getSitesNum(String id, String name) throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("")) {
				searchProperties.add(new SearchProperty("id", id, "like"));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSitesNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览教学站信息的权限");
		}
	}

	public List getEduTypes(Page page, String id, String name)
			throws PlatformException {
		if (basicManagePriv.getEduType == 1) {
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("")) {
				searchProperties.add(new SearchProperty("id", id, "like"));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getEduTypes(page, searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览层次信息的权限");
		}
	}

	public int getEduTypesNum(String id, String name) throws PlatformException {
		if (basicManagePriv.getEduType == 1) {
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("")) {
				searchProperties.add(new SearchProperty("id", id, "like"));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getEduTypesNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览层次信息的权限");
		}
	}

	public List getMajors(Page page, String id, String name)
			throws PlatformException {
		if (basicManagePriv.getMajor == 1) {
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("m.id", "0", "<>"));
			if (id != null && !id.equals("")) {
				searchProperties.add(new SearchProperty("m.id", id, "like"));
			}
			if (name != null && !name.equals("")) {
				searchProperties
						.add(new SearchProperty("m.name", name, "like"));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getMajors(page, searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览专业信息的权限");
		}
	}

	public int getMajorsNum(String id, String name) throws PlatformException {
		if (basicManagePriv.getMajor == 1) {
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("m.id", "0", "<>"));
			if (id != null && !id.equals("")) {
				searchProperties.add(new SearchProperty("m.id", id, "like"));
			}
			if (name != null && !name.equals("")) {
				searchProperties
						.add(new SearchProperty("m.name", name, "like"));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getMajorsNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览专业信息的权限");
		}
	}

	public List getDeps(Page page, String id, String name)
			throws PlatformException {
		if (basicManagePriv.getDep == 1) {
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("")) {
				searchProperties.add(new SearchProperty("id", id, "like"));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getDeps(page, searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览学院信息的权限");
		}
	}

	public int getDepsNum(String id, String name) throws PlatformException {
		if (basicManagePriv.getDep == 1) {
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("")) {
				searchProperties.add(new SearchProperty("id", id, "like"));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getDepsNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览学院信息的权限");
		}
	}

	public List getGrades(Page page, String id, String name)
			throws PlatformException {
		if (basicManagePriv.getGrade == 1) {
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("")) {
				searchProperties.add(new SearchProperty("id", id, "like"));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getGrades(page, searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览年级信息的权限");
		}
	}

	public SiteDiqu getSiteDiqu(String id) throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			return new OracleSiteDiqu(id);
		} else {
			throw new PlatformException("您没有浏览站点地区的权限");
		}
	}

	public List getSiteDiqu(Page page, HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			List searchProperties = new ArrayList();
			if (request.getParameter("diquId") != null
					&& !request.getParameter("diquId").equals("")) {
				searchProperties.add(new SearchProperty("id", request
						.getParameter("diquId")));
			}
			if (request.getParameter("diquname") != null
					&& !request.getParameter("diquname").equals("")) {
				searchProperties.add(new SearchProperty("name", request
						.getParameter("diquname")));
			}
			List orderProperties = new ArrayList();
			orderProperties.add(new OrderProperty("to_number(rightcode)"));
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSitesDiqu(page, searchProperties,
					orderProperties);
		} else {
			throw new PlatformException("您没有浏览站点地区的权限");
		}
	}

	public int getSiteDiquNum(HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			List searchProperties = new ArrayList();
			if (request.getParameter("id") != null
					&& !request.getParameter("id").equals("")) {
				searchProperties.add(new SearchProperty("id", request
						.getParameter("id")));
			}
			if (request.getParameter("name") != null
					&& !request.getParameter("name").equals("")) {
				searchProperties.add(new SearchProperty("name", request
						.getParameter("name")));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getSiteDiquNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览站点地区的权限");
		}
	}

	public int addSiteDiqu(HttpServletRequest request) throws PlatformException {
		if (basicManagePriv.addSite == 1) {
			OracleSiteDiqu siteDiqu = new OracleSiteDiqu();
			siteDiqu.setName(request.getParameter("name"));
			siteDiqu.setRightCode(request.getParameter("rightcode"));
			int suc = siteDiqu.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addSiteDiqu</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加站点地区的权限");
		}
	}

	public int setSiteRight(HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.getSite == 1) {
			String[] siteIds = request.getParameterValues("siteId");
			String[] rights = request.getParameterValues("right");
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			int suc = basicdatalist.setSiteRight(siteIds, rights);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$setSiteRight</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有浏览站点地区的权限");
		}
	}

	public int deleteSiteDiqu(HttpServletRequest request)
			throws PlatformException {

		if (basicManagePriv.deleteSite == 1) {

			String[] ids = request.getParameterValues("ids");
			int count = 0;
			if (request.getParameter("id") != null && ids == null) {
				ids = new String[1];
				ids[0] = request.getParameter("id");
			}

			for (int i = 0; i < ids.length; i++) {
				OracleSiteDiqu siteDiqu = new OracleSiteDiqu();
				siteDiqu.setId(ids[i]);
				count += siteDiqu.delete();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$setSiteRight</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("您没有删除站点地区的权限");
		}
	}

	public int updateSiteDiqu(HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.updateSite == 1) {
			OracleSiteDiqu siteDiqu = new OracleSiteDiqu();
			siteDiqu.setId(request.getParameter("id"));
			siteDiqu.setName(request.getParameter("name"));
			siteDiqu.setRightCode(request.getParameter("rightcode"));
			int suc = siteDiqu.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$setSiteRight</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改站点地区的权限");
		}
	}

	public int getGradesNum(String id, String name) throws PlatformException {
		if (basicManagePriv.getGrade == 1) {
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("")) {
				searchProperties.add(new SearchProperty("id", id, "like"));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getGradesNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览年级信息的权限");
		}
	}

	public int getSemestersNum(String name, String start_date, String end_date,
			String start_elective, String end_elective, String start_test,
			String end_test) throws PlatformException {
		if (basicManagePriv.getSemester == 1) {

			List searchProperties = new ArrayList();
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (start_date != null && !start_date.equals("")) {
				searchProperties.add(new SearchProperty("start_date",
						start_date, "D<="));
			}
			if (end_date != null && !end_date.equals("")) {
				searchProperties.add(new SearchProperty("end_date", end_date,
						"D>="));
			}
			if (start_elective != null && !start_elective.equals("")) {
				searchProperties.add(new SearchProperty("start_elective",
						start_elective, "D<="));
			}
			if (end_elective != null && !end_elective.equals("")) {
				searchProperties.add(new SearchProperty("end_elective",
						end_elective, "D>="));
			}
			if (start_test != null && !start_test.equals("")) {
				searchProperties.add(new SearchProperty("start_test",
						start_test, "D<="));
			}
			if (end_test != null && !end_test.equals("")) {
				searchProperties.add(new SearchProperty("end_test", end_test,
						"D>="));
			}
			OracleBasicEduList basicDataList = new OracleBasicEduList();
			return basicDataList.getSemestersNum(searchProperties);
		} else {
			throw new PlatformException("您没有获得学期信息的权限！");
		}
	}

	public List getListSemesters(Page page, String name, String start_date,
			String end_date, String start_elective, String end_elective,
			String start_test, String end_test) throws PlatformException {
		if (basicManagePriv.getSemester == 1) {

			List searchProperties = new ArrayList();
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (start_date != null && !start_date.equals("")) {
				searchProperties.add(new SearchProperty("start_date",
						start_date, "D>="));
			}
			if (end_date != null && !end_date.equals("")) {
				searchProperties.add(new SearchProperty("end_date", end_date,
						"D<="));
			}
			if (start_elective != null && !start_elective.equals("")) {
				searchProperties.add(new SearchProperty("start_elective",
						start_elective, "D>="));
			}
			if (end_elective != null && !end_elective.equals("")) {
				searchProperties.add(new SearchProperty("end_elective",
						end_elective, "D<="));
			}
			if (start_test != null && !start_test.equals("")) {
				searchProperties.add(new SearchProperty("start_test",
						start_test, "D<="));
			}
			if (end_test != null && !end_test.equals("")) {
				searchProperties.add(new SearchProperty("end_test", end_test,
						"D>="));
			}
			OracleBasicEduList basicdatalist = new OracleBasicEduList();
			return basicdatalist.getSemesters(page, searchProperties, null);
		} else {
			throw new PlatformException("您没有获得学期信息的权限！");
		}
	}

	public int getAllMajorsNum() throws PlatformException {
		if (basicManagePriv.getMajor == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
			return basicdatalist.getMajorsNum(null);
		} else {
			throw new PlatformException("您没有获得专业信息的权限！");
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
		smsManagerPriv.manageSmsSystemPoint = basicManagePriv.manageSmsSystemPoint;
		smsManagerPriv.getSmsStatistic = basicManagePriv.getSmsStatistic;
		smsManagerPriv.rejectSms = basicManagePriv.rejectSms;

		return smsManagerPriv;
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

	public SmsManage getSmsManage(SmsManagerPriv smsManagerPriv)
			throws PlatformException {
		SmsFactory smsFactory = SmsFactory.getInstance();
		return smsFactory.creatSmsManage(smsManagerPriv);
	}

	public int updateInfoRight(String newsTypeId, String[] pageManagerId,
			String[] selectedManagerId) throws PlatformException {
		OracleNewsType newsType = new OracleNewsType();
		newsType.setId(newsTypeId);
		int suc = newsType.updateInfoRight(pageManagerId, selectedManagerId);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateInfoRight</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public List getInfoRightUserIds(String newsTypeId) throws PlatformException {
		OracleNewsType newsType = new OracleNewsType();
		newsType.setId(newsTypeId);
		return newsType.getRightUserIds();
	}

	public List getInfoList(Page page) throws PlatformException {
		OracleInfoList infoList = new OracleInfoList();
		return infoList.getNewsListForManager(page);
	}

	public int getInfoListNum() throws PlatformException {
		OracleInfoList infoList = new OracleInfoList();
		return infoList.getNewsListNumForManager();

	}

	public Student getStudentInfo(String studentId) throws PlatformException {
		OracleStudent student = new OracleStudent(studentId);
		return student;

	}

	public TeachBook getTeachBook(String id) throws PlatformException {
		if (basicManagePriv.getTeachBook == 1) {
			return new OracleTeachBook(id);
		} else {
			throw new PlatformException("您没有查看教材的权限");
		}
	}

	public List getTeachBookList(Page page, HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.getTeachBook == 1) {
			BasicEntityList teachBookList = new OracleBasicEntityList();
			List searchList = new ArrayList();
			if (request != null) {
				String teachbook_name = null;
				if (request.getParameter("teachbook_name") != null) {
					if (!(request.getParameter("teachbook_name"))
							.equals("null")
							&& !request.getParameter("teachbook_name").trim()
									.equals("")) {
						teachbook_name = request.getParameter("teachbook_name").trim();
					}
				}

				String publishhouse = null;
				if (request.getParameter("publishhouse") != null) {
					if (!(request.getParameter("publishhouse")).equals("null")
							&& !request.getParameter("publishhouse").trim()
									.equals("")) {
						publishhouse = request.getParameter("publishhouse").trim();
					}
				}

				String id = null;
				if (request.getParameter("teachbook_id") != null) {
					if (!(request.getParameter("teachbook_id")).equals("null")
							&& !request.getParameter("teachbook_id").trim()
									.equals("")) {
						id = request.getParameter("teachbook_id").trim();
					}
				}
				if (teachbook_name != null)
					searchList.add(new SearchProperty("teachbook_name",
							teachbook_name, "like"));
				if (publishhouse != null)
					searchList.add(new SearchProperty("publishhouse",
							publishhouse, "like"));
				if (id != null)
					searchList.add(new SearchProperty("id", id, "like"));
			}
			return teachBookList.getTeachBooks(page, searchList, null);
		} else {
			throw new PlatformException("您没有查看教材的权限");
		}
	}

	public List getNoSelectTeachBookList(Page page, HttpServletRequest request,
			String selTbId) throws PlatformException {
		if (basicManagePriv.appointTeachbookForCourse == 1) {
			BasicEntityList teachBookList = new OracleBasicEntityList();
			List searchList = new ArrayList();
			if (request != null) {
				String teachbook_id = null;
				String teachbook_name = null;
				if (request.getParameter("teachbook_id") != null
						&& !request.getParameter("teachbook_id").trim()
								.equals("")) {
					teachbook_id = request.getParameter("teachbook_id");
				}

				if (request.getParameter("teachbook_name") != null) {
					if (!(request.getParameter("teachbook_name"))
							.equals("null")
							&& !request.getParameter("teachbook_name").trim()
									.equals("")) {
						teachbook_name = request.getParameter("teachbook_name");
					}
				}

				String publishhouse = null;
				if (request.getParameter("publishhouse") != null) {
					if (!(request.getParameter("publishhouse")).equals("null")
							&& !request.getParameter("publishhouse").trim()
									.equals("")) {
						publishhouse = request.getParameter("publishhouse");
					}
				}

				String maineditor = null;
				if (request.getParameter("maineditor") != null) {
					if (!(request.getParameter("maineditor")).equals("null")
							&& !request.getParameter("maineditor").trim()
									.equals("")) {
						maineditor = request.getParameter("maineditor");
					}
				}

				String isbn = null;
				if (request.getParameter("isbn") != null) {
					if (!(request.getParameter("isbn")).equals("null")
							&& !request.getParameter("isbn").trim().equals("")) {
						isbn = request.getParameter("isbn");
					}
				}
				if (teachbook_id != null)
					searchList.add(new SearchProperty("id",
							teachbook_id, "like"));
				if (teachbook_name != null)
					searchList.add(new SearchProperty("teachbook_name",
							teachbook_name, "like"));
				if (publishhouse != null)
					searchList.add(new SearchProperty("publishhouse",
							publishhouse, "like"));
				if (maineditor != null)
					searchList.add(new SearchProperty("maineditor", maineditor,
							"like"));
				if (isbn != null)
					searchList.add(new SearchProperty("isbn", isbn, "like"));
			}
			if (!selTbId.equals("")) {
				searchList.add(new SearchProperty("id", selTbId, "notIn"));
			}
			return teachBookList.getTeachBooks(page, searchList, null);
		} else {
			throw new PlatformException("您没有查看教材的权限");
		}
	}

	public List getTeachBookList() throws PlatformException {
		if (basicManagePriv.getTeachBook == 1) {
			BasicEntityList teachBookList = new OracleBasicEntityList();
			return teachBookList.getTeachBooks(null, null, null);
		} else {
			throw new PlatformException("您没有查看教材的权限");
		}
	}

	public int addTeachBook(HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.addTeachBook == 1) {
			OracleTeachBook oracleTeachBook = new OracleTeachBook();
			oracleTeachBook.setId(request.getParameter("teachbook_id"));
			oracleTeachBook.setTeachbook_name(request
					.getParameter("teachbook_name"));
			oracleTeachBook.setPublishhouse(request
					.getParameter("publishhouse"));
			oracleTeachBook.setMaineditor(request.getParameter("maineditor"));
			oracleTeachBook.setIsbn(request.getParameter("isbn"));
			oracleTeachBook.setPublish_date(request
					.getParameter("publish_date"));
			oracleTeachBook.setPrice(request.getParameter("price"));
			if (request.getParameter("note") == null) {
				oracleTeachBook.setNote("");
			} else {
				oracleTeachBook.setNote(request.getParameter("note"));
			}
			int suc = oracleTeachBook.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addTeachBook</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;

		} else {
			throw new PlatformException("您没有添加教材的权限");
		}
	}

	public int updateTeachBook(HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.updateTeachBook == 1) {
			OracleTeachBook oracleTeachBook = new OracleTeachBook(request
					.getParameter("id"));
			oracleTeachBook.setTeachbook_name(request
					.getParameter("teachbook_name"));
			oracleTeachBook.setPublishhouse(request
					.getParameter("publishhouse"));
			oracleTeachBook.setMaineditor(request.getParameter("maineditor"));
			oracleTeachBook.setIsbn(request.getParameter("isbn"));
			oracleTeachBook.setPublish_date(request
					.getParameter("publish_date"));
			oracleTeachBook.setPrice(request.getParameter("price"));
			if (request.getParameter("note") != null) {
				oracleTeachBook.setNote(request.getParameter("note"));
			}
			int suc = oracleTeachBook.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateTeachBook</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有更新教材的权限");
		}
	}

	public int deleteTeachBook(String id) throws PlatformException {
		if (basicManagePriv.deleteTeachBook == 1) {
			OracleTeachBook oracleTeachBook = new OracleTeachBook(id);
			int suc = oracleTeachBook.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTeachBook</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除教材的权限");
		}
	}

	public int getTeachBookNum(HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.getTeachBook == 1) {
			BasicEntityList teachBookList = new OracleBasicEntityList();
			List searchList = new ArrayList();
			if (request != null) {
				String teachbook_name = null;
				if (request.getParameter("teachbook_name") != null) {
					if (!(request.getParameter("teachbook_name"))
							.equals("null")
							&& !request.getParameter("teachbook_name").trim()
									.equals("")) {
						teachbook_name = request.getParameter("teachbook_name");
					}
				}

				String publishhouse = null;
				if (request.getParameter("publishhouse") != null) {
					if (!(request.getParameter("publishhouse")).equals("null")
							&& !request.getParameter("publishhouse").trim()
									.equals("")) {
						publishhouse = request.getParameter("publishhouse");
					}
				}

				String id = null;
				if (request.getParameter("teachbook_id") != null) {
					if (!(request.getParameter("teachbook_id")).equals("null")
							&& !request.getParameter("teachbook_id").trim()
									.equals("")) {
						id = request.getParameter("teachbook_id");
					}
				}
				if (teachbook_name != null)
					searchList.add(new SearchProperty("teachbook_name",
							teachbook_name, "like"));
				if (publishhouse != null)
					searchList.add(new SearchProperty("publishhouse",
							publishhouse, "like"));
				if (id != null)
					searchList.add(new SearchProperty("id", id, "like"));
			}
			return  teachBookList.getTeachBookNum(searchList);
		} else {
			throw new PlatformException("您没有查看教材的权限");
		}
		 
	}

	public int getNoSelectTeachBookNum(HttpServletRequest request,
			String selectedID) throws PlatformException {
		if (basicManagePriv.appointTeachbookForCourse == 1) {
			BasicEntityList teachBookList = new OracleBasicEntityList();
			List searchList = new ArrayList();

			if (request.getParameter("teachbook_name") != null
					&& !request.getParameter("teachbook_name").trim()
							.equals("")
					&& !request.getParameter("teachbook_name").equals("null")) {
				searchList.add(new SearchProperty("teachbook_name", request
						.getParameter("teachbook_name"), "like"));
			}
			if (request.getParameter("publishhouse") != null
					&& !request.getParameter("publishhouse").trim().equals("")
					&& !request.getParameter("publishhouse").equals("null")) {
				searchList.add(new SearchProperty("publishhouse", request
						.getParameter("publishhouse"), "like"));
			}
			if (request.getParameter("maineditor") != null
					&& !request.getParameter("maineditor").trim().equals("")
					&& !request.getParameter("maineditor").equals("null")) {
				searchList.add(new SearchProperty("maineditor", request
						.getParameter("maineditor"), "like"));
			}
			if (request.getParameter("isbn") != null
					&& !request.getParameter("isbn").trim().equals("")
					&& !request.getParameter("isbn").equals("null")) {
				searchList.add(new SearchProperty("isbn", request
						.getParameter("isbn"), "like"));
			}
			searchList.add(new SearchProperty("id", selectedID, "notIn"));
			return teachBookList.getTeachBookNum(searchList);
		} else {
			throw new PlatformException("您没有给课程添加教材的权限");
		}
	}

	public List getPlanEduTypes(String batchId, String siteId, String edutype) {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();
		if (batchId != null && !batchId.equals("")) {
			searchProperties.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (siteId != null && !siteId.equals("")) {
			searchProperties.add(new SearchProperty("site_id", siteId, "="));
		}
		if (edutype != null && !edutype.equals("")) {
			searchProperties
					.add(new SearchProperty("edutype_id", edutype, "="));
		}

		return basicdatalist.getPlanEduTypes(searchProperties, orderProperties);
	}

	public List getPlanSites(String batchId) {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();
		if (batchId != null && !batchId.equals("")) {
			searchProperties.add(new SearchProperty("batch_id", batchId, "="));
		}

		return basicdatalist.getPlanSites(searchProperties, orderProperties);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicEntityManage#getMobilePhones(java.lang.String[],
	 *      java.lang.String[], java.lang.String[], java.lang.String[],
	 *      java.lang.String[])
	 */
	public String getMobilePhones(String[] persons, String[] siteIds,
			String[] gradeIds, String[] majorIds, String[] eduTypeIds)
			throws PlatformException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		return basicdatalist.getMobilePhones(persons, siteIds, gradeIds,
				majorIds, eduTypeIds);
	}

	public String getScope(String[] persons, String[] siteIds,
			String[] gradeIds, String[] majorIds, String[] eduTypeIds)
			throws PlatformException {
		String scope = "";
		int len = 0;
		try {
			if (siteIds != null) {
				scope = scope + "[";
				for (len = 0; len < siteIds.length - 1; len++) {
					scope = scope + this.getSite(siteIds[len]).getName() + ",";
				}
				scope = scope + this.getSite(siteIds[len]).getName()
						+ "]站点<br>";
			}

			if (gradeIds != null) {
				scope = scope + "[";
				for (len = 0; len < gradeIds.length - 1; len++) {
					scope = scope + this.getGrade(gradeIds[len]).getName()
							+ ",";
				}
				scope = scope + this.getGrade(gradeIds[len]).getName()
						+ "]年级<br>";
			}

			if (majorIds != null) {
				scope = scope + "[";
				for (len = 0; len < majorIds.length - 1; len++) {
					scope = scope + this.getMajor(majorIds[len]).getName()
							+ ",";
				}
				scope = scope + this.getMajor(majorIds[len]).getName()
						+ "]专业<br>";
			}

			if (eduTypeIds != null) {
				scope = scope + "[";
				for (len = 0; len < eduTypeIds.length - 1; len++) {
					scope = scope + this.getEduType(eduTypeIds[len]).getName()
							+ ",";
				}
				scope = scope + this.getEduType(eduTypeIds[len]).getName()
						+ "]层次";
			}

			String personScope = "[";
			if (persons != null) {
				for (len = 0; len < persons.length - 1; len++) {
					if (persons[len].equalsIgnoreCase("managers"))
						personScope = personScope + "管理员" + ",";
					if (persons[len].equalsIgnoreCase("subadmins"))
						personScope = personScope + "分站管理员" + ",";
					if (persons[len].equalsIgnoreCase("teachers"))
						personScope = personScope + "教师" + ",";
					if (persons[len].equalsIgnoreCase("students"))
						personScope = personScope + "学生" + ",";
				}
				if (persons[len].equalsIgnoreCase("managers"))
					personScope = personScope + "管理员" + "]";
				if (persons[len].equalsIgnoreCase("subadmins"))
					personScope = personScope + "分站管理员" + "]";
				if (persons[len].equalsIgnoreCase("teachers"))
					personScope = personScope + "教师" + "]";
				if (persons[len].equalsIgnoreCase("students"))
					personScope = personScope + "学生" + "]";
			}
			scope = scope + "下的<br>" + personScope;
		} catch (PlatformException e) {
			
		}

		return scope;
	}

	public List getPlanEduTypes(String batchId, String siteId)
			throws PlatformException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();
		if (batchId != null && !batchId.equals("")) {
			searchProperties.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (siteId != null && !siteId.equals("")) {
			searchProperties.add(new SearchProperty("site_id", siteId, "="));
		}
		return basicdatalist.getPlanEduTypes(searchProperties, orderProperties);
	}

	public List getPlanMajors(String batchId, String siteId, String eduType)
			throws PlatformException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();
		if (batchId != null && !batchId.equals("")) {
			searchProperties.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (siteId != null && !siteId.equals("")) {
			searchProperties.add(new SearchProperty("site_id", siteId, "="));
		}
		if (eduType != null && !eduType.equals("")) {
			searchProperties
					.add(new SearchProperty("edutype_id", eduType, "="));
		}
		return basicdatalist.getPlanMajors(searchProperties, orderProperties);
	}

	public int updateRecruitStudentInfo(String id, String name,
			String password, String gender, String folk, String birthday,
			String zzmm, String edu, String site_id, String edutype_id,
			String major_id, String card_type, String card_no, String hometown,
			String[] email, String postalcode, String address,
			String[] mobilephone, String[] phone, String considertype,
			String status, String school_name, String school_code,
			String graduate_year, String graduate_cardno)
			throws PlatformException {
		if (basicManagePriv.updateRecruitStudent == 1) {
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
				throw new PlatformException("没有设定当前批次!");
			else
				batch = new OracleRecruitBatch(((RecruitBatch) batchs.get(0))
						.getId());
			eduInfo.setBatch(batch);

			student.setEduInfo(eduInfo);
			return student.update();
		} else {
			throw new PlatformException(" no updateStudentInfo right！");
		}
	}

	public int getTeachBooksNumOfCourse(String courseId)
			throws PlatformException {
		if (basicManagePriv.getTeachBook == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();

			return basicdatalist.getTeachBooksNumOfCourse(courseId);
		} else {
			throw new PlatformException("您没有查看教材的权限");
		}
	}

	public List getTeachBooksOfCourse(Page page, String courseId)
			throws PlatformException {
		if (basicManagePriv.getTeachBook == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();

			return basicdatalist.getTeachBooksOfCourse(page, courseId);
		} else {
			throw new PlatformException("您没有查看教材的权限");
		}
	}

	public int getTeachBooksNumToAddOfCourse(String courseId)
			throws PlatformException {
		if (basicManagePriv.getTeachBook == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();

			return basicdatalist.getTeachBooksNumToAddOfCourse(courseId);
		} else {
			throw new PlatformException("您没有查看教材的权限");
		}
	}

	public List getTeachBooksToAddOfCourse(Page page, String courseId)
			throws PlatformException {
		if (basicManagePriv.getTeachBook == 1) {
			OracleBasicEntityList basicdatalist = new OracleBasicEntityList();

			return basicdatalist.getTeachBooksToAddOfCourse(page, courseId);
		} else {
			throw new PlatformException("您没有查看教材的权限");
		}
	}

	public int addRecruitStudent(String name, String password, String gender,
			String folk, String birthday, String zzmm, String edu,
			String site_id, String grade_id, String edutype_id,
			String major_id, String card_type, String card_no, String hometown,
			String[] email, String postalcode, String address,
			String[] mobilephone, String[] phone, String considertype,
			String status, String batchId) throws PlatformException {
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
			eduInfo.setGrade_id(grade_id);
			eduInfo.setEdutype_id(edutype_id);
			eduInfo.setMajor_id(major_id);
			eduInfo.setConsiderType(considertype);
			eduInfo.setStatus(status);

			eduInfo.setBatch(new OracleRecruitBatch(batchId));

			student.setEduInfo(eduInfo);
			int suc = student.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addRecruitStudent</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加学生的权限！");
		}
	}

	public int addMatriculatetudent(String name, String password,
			String gender, String folk, String birthday, String zzmm,
			String edu, String site_id, String grade_id, String edutype_id,
			String major_id, String card_type, String card_no, String hometown,
			String[] email, String postalcode, String address,
			String[] mobilephone, String[] phone, String considertype,
			String status, String batchId, String reg_no, String score_1,
			String score_2, String score_3, String score_4, String score,
			String testcard_id, String school_name, String school_code,
			String graduate_year, String graduate_cardno)
			throws PlatformException {
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
			eduInfo.setGrade_id(grade_id);
			eduInfo.setEdutype_id(edutype_id);
			eduInfo.setMajor_id(major_id);
			eduInfo.setConsiderType(considertype);
			eduInfo.setStatus(status);
			// eduInfo.setReg_no(reg_no);
			eduInfo.setSchool_name(school_name);
			eduInfo.setSchool_code(school_code);
			eduInfo.setGraduate_year(graduate_year);
			eduInfo.setGraduate_cardno(graduate_cardno);
			eduInfo.setBatch(new OracleRecruitBatch(batchId));
			eduInfo.setTestCardId(testcard_id);

			student.setEduInfo(eduInfo);
			student.updateTestCardId();
			int suc = student.add();
			
			student = new OracleRecruitStudent(student.getNormalInfo().getIdcard(),null);
			
			HashMap scores = new HashMap();
			if (score_1 != null && !score_1.equalsIgnoreCase("null")
					&& !score_1.equals(""))
				scores.put("score_1", score_1);
			if (score_2 != null && !score_2.equalsIgnoreCase("null")
					&& !score_2.equals(""))
				scores.put("score_2", score_2);
			if (score_3 != null && !score_3.equalsIgnoreCase("null")
					&& !score_3.equals(""))
				scores.put("score_3", score_3);
			if (score_4 != null && !score_4.equalsIgnoreCase("null")
					&& !score_4.equals(""))
				scores.put("score_4", score_4);
			if (score != null && !score.equalsIgnoreCase("null")
					&& !score.equals(""))
				scores.put("score", score);
			if (testcard_id != null && !testcard_id.equalsIgnoreCase("null")
					&& !testcard_id.equals(""))
				scores.put("testcard_id", testcard_id);
			student.updateScore(scores);
			// student.updateRegNo2();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addRecruitStudent</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加学生的权限！");
		}
	}

	public LeaveWordManagerPriv getLeaveWordManagerPriv(String id)
			throws PlatformException {
		return new OracleLeaveWordManagerPriv(id);
	}

	public LeaveWordManage getLeaveWordManage(LeaveWordManagerPriv priv)
			throws PlatformException {
		if (priv == null)
			priv = new OracleLeaveWordManagerPriv("");
		LeaveWordFactory factory = LeaveWordFactory.getInstance();
		return factory.creatLeaveWordManage(priv);
	}

	public LeaveWordNormalManage getLeaveWordNormalManage()
			throws PlatformException {
		LeaveWordFactory factory = LeaveWordFactory.getInstance();
		return factory.creatLeaveWordNormalManage(null);
	}

	public CampusInfoManagerPriv getCampusInfoManagerPriv(String managerId) throws PlatformException {
		// TODO Auto-generated method stub
		return new OracleCampusInfoManagerPriv(managerId);
	}

	@Override
	public CampusManagerPriv getCampusManagerPriv(String id) throws PlatformException {
		return new OracleCampusManagerPriv(id);
	}

	@Override
	public CampusManage getCampusManage(CampusManagerPriv priv) throws PlatformException {
		if (priv == null)
			priv = new OracleCampusManagerPriv("");
		CampusFactory factory = CampusFactory.getInstance();
		return factory.creatCampusManage(priv);
	}

	@Override
	public CampusNormalManage getCampusNormalManage() throws PlatformException {
		CampusFactory factory = CampusFactory.getInstance();
		return factory.creatCampusNormalManage(null);
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
				throw new PlatformException("没有设定当前批次!");
			else
				plan.setBatch(new OracleRecruitBatch(((RecruitBatch) batchs
						.get(0)).getId()));
			plan.setLimit(limit);
			plan.setStatus("1");
			int suc = plan.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addRecruitPlan</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("no addRecruitPlan right");
		}
	}

}
