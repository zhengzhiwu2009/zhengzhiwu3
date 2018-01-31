/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity;

/**
 * @author wq
 * 
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleBasicGraduateList;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleBasicRecruitList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleBasicUserList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManager;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteManager;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacherLimit;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleUserBatchActivity;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUser;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUserAuthorization;
import com.whaty.platform.entity.BasicSiteUserManage;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.Manager;
import com.whaty.platform.entity.user.SiteManager;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.SiteTeacherLimit;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.entity.user.TeacherEduInfo;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.sso.SsoFactory;
import com.whaty.platform.sso.SsoManage;
import com.whaty.platform.sso.SsoManagerPriv;
import com.whaty.platform.sso.SsoUser;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.RedundanceData;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.SsoLog;
import com.whaty.platform.util.log.UserLog;
import com.whaty.util.string.WhatyAttributeManage;

public class OracleBasicSiteUserManage extends BasicSiteUserManage {

	SiteManagerPriv basicManagePriv;

	public OracleBasicSiteUserManage(SiteManagerPriv amanagerpriv) {
		this.basicManagePriv = amanagerpriv;
	}

	public int updateSiteManagerPwd(String sso_id, String old_pwd,
			String new_pwd) throws PlatformException {
		if (this.basicManagePriv.updatePwd != 1) {
			throw new PlatformException("��û���޸������Ȩ��!");
		} else {
			OracleSsoUser ssoUser = new OracleSsoUser(sso_id);
			OracleSsoUserAuthorization ssoUserAuthor = new OracleSsoUserAuthorization(
					ssoUser.getLoginId());

			if (old_pwd.equals(ssoUserAuthor.getRightPwd())) {
				ssoUser.setPassword(new_pwd);
				int suc = ssoUser.update();
				if (suc > 0) {
					SsoLog.setDebug("վ�����Ա" + sso_id + " �޸�����ɹ�!");
					UserLog
							.setInfo(
									"<whaty>USERID$|$"
											+ this.basicManagePriv
													.getManagerId()
											+ "</whaty><whaty>BEHAVIOR$|$updateSiteManagerPwd</whaty><whaty>STATUS$|$"
											+ suc
											+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
											+ LogType.SITEMANAGER
											+ "</whaty><whaty>PRIORITY$|$"
											+ LogPriority.INFO + "</whaty>",
									new Date());
					return suc;
				} else {
					throw new PlatformException("�޸�����ʧ��!");
				}
			} else {
				throw new PlatformException("ԭ�������!");
			}
		}
	}

	public Teacher getTeacher(String aid) throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			return new OracleTeacher(aid);
		} else {
			throw new PlatformException("��û�в鿴��ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public SiteTeacher getSiteTeacher(String aid) throws PlatformException {
		if (basicManagePriv.getSiteTeacher == 1) {
			OracleSiteTeacher siteTeacher = new OracleSiteTeacher(aid);
			if (siteTeacher.getSiteId() != null
					&& siteTeacher.getSiteId().equals(
							basicManagePriv.getSite_id()))
				return siteTeacher;
			else {
				throw new PlatformException("������ҵĸ�����ʦ�����ڸ�վ��");
			}
		} else {
			throw new PlatformException("��û�в鿴������ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int deleteTeacher(String aid) throws PlatformException {
		if (basicManagePriv.deleteTeacher == 1) {
			int suc = new OracleTeacher(aid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTeacher</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��ɾ���ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int deleteSiteTeacher(String aid) throws PlatformException {
		if (basicManagePriv.deleteSiteTeacher == 1) {
			OracleSiteTeacher oracleSiteTeacher = new OracleSiteTeacher();
			oracleSiteTeacher.setId(aid);
			int suc = oracleSiteTeacher.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteSiteTeacher</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��ɾ���ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int updateTeacher(String teacherId, String name, String password,
			String email, HumanNormalInfo normalInfo,
			TeacherEduInfo teachereduInfo, HumanPlatformInfo platformInfo,
			RedundanceData redundace) throws PlatformException {
		if (basicManagePriv.updateTeacher == 1) {
			OracleTeacher oracleteacher = new OracleTeacher();
			oracleteacher.setEmail(email);
			oracleteacher.setId(teacherId);
			oracleteacher.setName(name);
			oracleteacher.setPassword(password);
			oracleteacher.setNormalInfo(normalInfo);
			oracleteacher.setTeacherInfo(teachereduInfo);
			oracleteacher.setPlatformInfo(platformInfo);
			oracleteacher.setRedundace(redundace);
			int suc = oracleteacher.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateTeacher</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸Ľ�ʦ��Ϣ��Ȩ��");
		}
	}

	public int updateSiteTeacher(String teacherId, String name,
			String password, String email, HumanNormalInfo normalInfo,
			TeacherEduInfo teachereduInfo, HumanPlatformInfo platformInfo,
			RedundanceData redundace) throws PlatformException {
		if (basicManagePriv.updateSiteTeacher == 1) {
			OracleSiteTeacher oracleteacher = new OracleSiteTeacher(teacherId);
			oracleteacher.setSiteId(basicManagePriv.getSite_id());
			oracleteacher.setEmail(email);
			oracleteacher.setId(teacherId);
			oracleteacher.setName(name);
			oracleteacher.setPassword(password);
			oracleteacher.setNormalInfo(normalInfo);
			oracleteacher.setTeacherInfo(teachereduInfo);
			oracleteacher.setPlatformInfo(platformInfo);
			oracleteacher.setRedundace(redundace);
			//oracleteacher.setConfirmStatus("0");
			int suc = oracleteacher.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateSiteTeacher</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸ĸ�����ʦ��Ϣ��Ȩ��");
		}
	}

	public List getTeachers(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getTeachers(page, searchproperty, orderproperty);
	}

	public List getSiteTeachers(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		if (searchproperty == null)
			searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("site_id", this.basicManagePriv
				.getSite_id(), "="));
		return basicdatalist.getSiteTeachers(page, searchproperty,
				orderproperty);
	}

	public List getTeachers(List searchproperty, List orderproperty)
			throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getTeachers(searchproperty, orderproperty);
	}

	public List getSiteTeachers(List searchproperty, List orderproperty)
			throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		if (searchproperty == null)
			searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("site_id", this.basicManagePriv
				.getSite_id(), "="));
		return basicdatalist.getSiteTeachers(null, searchproperty,
				orderproperty);
	}

	public int getTeachersNum(List searchproperty) throws PlatformException {

		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getTeachersNum(searchproperty);
	}

	public int getSiteTeachersNum(List searchproperty) throws PlatformException {

		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		if (searchproperty == null)
			searchproperty = new ArrayList();
		searchproperty.add(new SearchProperty("site_id", this.basicManagePriv
				.getSite_id(), "="));
		return basicdatalist.getSiteTeachersNum(searchproperty);
	}

	public List getTeachers(Page page) throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			return this.getTeachers(page, null, null);
		} else {
			throw new PlatformException("��û�в鿴��ʦ��Ϣ��Ȩ��");
		}
	}

	public List getSiteTeachers(Page page) throws PlatformException {
		if (basicManagePriv.getSiteTeacher == 1) {
			List searchproperty = new ArrayList();
			searchproperty.add(new SearchProperty("site_id",
					this.basicManagePriv.getSite_id(), "="));
			return this.getSiteTeachers(page, searchproperty, null);
		} else {
			throw new PlatformException("��û�в鿴������ʦ��Ϣ��Ȩ��");
		}
	}

	public List getTeachers() throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			return this.getTeachers(null, null, null);
		} else {
			throw new PlatformException("��û�в鿴��ʦ��Ϣ��Ȩ��");
		}
	}

	public List getSiteTeachers() throws PlatformException {
		if (basicManagePriv.getSiteTeacher == 1) {
			List searchproperty = new ArrayList();
			searchproperty.add(new SearchProperty("site_id",
					this.basicManagePriv.getSite_id(), "="));
			return this.getSiteTeachers(null, searchproperty, null);
		} else {
			throw new PlatformException("��û�в鿴������ʦ��Ϣ��Ȩ��");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicUserManage#addSsoUser(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public int addSsoUser(String login_id, String password, String name,
			String email) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicUserManage#addStudentFromSso(java.util.List)
	 */
	public void addStudentFromSso(List ssoUserList) throws PlatformException {
		// TODO Auto-generated method stub

	}

	public int getStudentsNum(List searchProperties) throws PlatformException {
		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getStudentsNum(searchProperties);
	}

	public List getStudents(Page page, List searchProperties,
			List orderProperties) throws PlatformException {
		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getStudents(page, searchProperties,
				orderProperties);
	}

	// add by wq
	public List getAllStudents(Page page, List searchProperties,
			List orderProperties) throws PlatformException {
		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getAllStudents(page, searchProperties,
				orderProperties);
	}

	public List getStudents(List searchProperties, List orderProperties)
			throws PlatformException {

		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getStudents(searchProperties,
				orderProperties);
	}

	// add by wq
	public List getAllStudents(List searchProperties, List orderProperties)
			throws PlatformException {

		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getAllStudents(searchProperties,
				orderProperties);
	}

	public int addStudent(String name, String password, String email,
			HumanNormalInfo normalInfo, StudentEduInfo eduInfo,
			HumanPlatformInfo platformInfo, RedundanceData redundance)
			throws PlatformException {
		if (basicManagePriv.addStudent == 1) {
			OracleStudent oraclestudent = new OracleStudent();
			oraclestudent.setEmail(email);
			oraclestudent.setName(name);
			oraclestudent.setPassword(password);
			oraclestudent.setNormalInfo(normalInfo);
			oraclestudent.setStudentInfo(eduInfo);
			oraclestudent.setPlatformInfo(platformInfo);
			oraclestudent.setRedundace(redundance);
			int suc = oraclestudent.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addStudent</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û�����ѧ���Ȩ��");
		}
	}

	public void addStudentBatch(List studentList) throws PlatformException {
		if (basicManagePriv.addStudent == 1) {
			OracleUserBatchActivity batch = new OracleUserBatchActivity();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addStudentBatch</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			batch.studentAddBatch(studentList);
		} else {
			throw new PlatformException("��û�����ѧ���Ȩ��");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicUserManage#deleteStudent(java.lang.String)
	 */
	public int deleteStudent(String studentId) throws PlatformException {
		if (basicManagePriv.deleteStudent == 1) {
			OracleStudent student = new OracleStudent();
			student.setId(studentId);
			int suc = student.delete();
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
			throw new PlatformException("��û��ɾ��ѧ���Ȩ��");
		}
	}

	public void deleteStudentBatch(String studentId[]) throws PlatformException {
		if (basicManagePriv.deleteStudent == 1) {
			OracleUserBatchActivity batch = new OracleUserBatchActivity();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteStudentBatch</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			batch.studentDeleteBatch(studentId);
		} else {
			throw new PlatformException("��û��ɾ��ѧ���Ȩ��");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicUserManage#getStudent(java.lang.String)
	 */
	public Student getStudent(String studentId) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			return new OracleStudent(studentId);
		} else {
			throw new PlatformException("��û�����ѧ����Ϣ��Ȩ��");
		}
	}//

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicUserManage#addTeacherFormSso(java.util.List)
	 */
	public void addTeacherFormSso(List ssoUserList) throws PlatformException {
		// TODO Auto-generated method stub

	}

	public int updateStudent(String studentId, String name, String password,
			String email, HumanNormalInfo normalInfo, StudentEduInfo eduInfo,
			HumanPlatformInfo platformInfo, RedundanceData redundance)
			throws PlatformException {
		if (basicManagePriv.updateStudent == 1) {
			OracleStudent oraclestudent = new OracleStudent();
			oraclestudent.setId(studentId);
			oraclestudent.setEmail(email);
			oraclestudent.setName(name);
			oraclestudent.setPassword(password);
			oraclestudent.setNormalInfo(normalInfo);
			oraclestudent.setStudentInfo(eduInfo);
			oraclestudent.setPlatformInfo(platformInfo);
			oraclestudent.setRedundace(redundance);
			int suc = oraclestudent.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateStudent</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸�ѧ����Ϣ��Ȩ��");
		}
	}

	public int updateStudentInfo(String studentId, String name,
			String password, String email, HumanNormalInfo normalInfo)
			throws PlatformException {
		if (basicManagePriv.updateStudent == 1) {
			OracleStudent oraclestudent = new OracleStudent();
			oraclestudent.setId(studentId);
			oraclestudent.setEmail(email);
			oraclestudent.setName(name);
			oraclestudent.setPassword(password);
			oraclestudent.setNormalInfo(normalInfo);
			int suc = oraclestudent.updateNormalInfo();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateStudent</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸�ѧ����Ϣ��Ȩ��");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicUserManage#updateStudent(java.lang.String,
	 *      com.whaty.platform.entity.user.HumanNormalInfo,
	 *      com.whaty.platform.entity.user.StudentEduInfo,
	 *      com.whaty.platform.entity.user.HumanPlatformInfo,
	 *      com.whaty.platform.util.RedundanceData)
	 */
	public int updateStudent(String studentId, HumanNormalInfo normalInfo,
			StudentEduInfo eduInfo, HumanPlatformInfo platformInfo,
			RedundanceData redundanceData) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTeachersNum() throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			return this.getTeachersNum(null);
		} else {
			throw new PlatformException("��û�в鿴��ʦ��Ϣ��Ȩ��");
		}
	}

	public int getSiteTeachersNum() throws PlatformException {
		if (basicManagePriv.getSiteTeacher == 1) {
			List searchproperty = new ArrayList();
			searchproperty.add(new SearchProperty("site_id",
					this.basicManagePriv.getSite_id(), "="));
			return this.getSiteTeachersNum(searchproperty);
		} else {
			throw new PlatformException("��û�в鿴������ʦ��Ϣ��Ȩ��");
		}
	}

	public int addTeacher(String name, String password, String email,
			HumanNormalInfo normalInfo, TeacherEduInfo teachereduInfo,
			HumanPlatformInfo platformInfo, RedundanceData redundace)
			throws PlatformException {
		if (basicManagePriv.addTeacher == 1) {
			OracleTeacher oracleTeacher = new OracleTeacher();
			oracleTeacher.setEmail(email);
			oracleTeacher.setName(name);
			oracleTeacher.setPassword(password);
			oracleTeacher.setNormalInfo(normalInfo);
			oracleTeacher.setTeacherInfo(teachereduInfo);
			oracleTeacher.setPlatformInfo(platformInfo);
			int suc = oracleTeacher.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addTeacher</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û����ӽ�ʦ��Ϣ��Ȩ��");
		}
	}

	public int addSiteTeacher(String name, String password, String email,
			HumanNormalInfo normalInfo, TeacherEduInfo teachereduInfo,
			HumanPlatformInfo platformInfo, RedundanceData redundace)
			throws PlatformException {
		if (basicManagePriv.addSiteTeacher == 1) {
			OracleSiteTeacher oracleSiteTeacher = new OracleSiteTeacher();
			oracleSiteTeacher.setSiteId(basicManagePriv.getSite_id());
			oracleSiteTeacher.setEmail(email);
			oracleSiteTeacher.setName(name);
			oracleSiteTeacher.setPassword(password);
			oracleSiteTeacher.setNormalInfo(normalInfo);
			oracleSiteTeacher.setTeacherInfo(teachereduInfo);
			oracleSiteTeacher.setPlatformInfo(platformInfo);
			int suc = oracleSiteTeacher.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addSiteTeacher</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û����ӽ�ʦ��Ϣ��Ȩ��");
		}
	}

	public void addTeacherBatch(List teacherList) throws PlatformException {
		if (basicManagePriv.addTeacher == 1) {
			OracleUserBatchActivity batch = new OracleUserBatchActivity();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addTeacherBatch</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			batch.teacherAddBatch(teacherList);
		} else {
			throw new PlatformException("��û�����ѧ���Ȩ��");
		}
	}

	public void addSiteTeacherBatch(List teacherList) throws PlatformException {
		if (basicManagePriv.addSiteTeacher == 1) {
			OracleUserBatchActivity batch = new OracleUserBatchActivity();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addSiteTeacherBatch</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			batch.siteTeacherAddBatch(teacherList);
		} else {
			throw new PlatformException("��û�����ѧ���Ȩ��");
		}
	}

	public int addManager(String login_id, String name, String password,
			String type, String status) throws PlatformException {
		OracleManager manager = new OracleManager();
		manager.setLoginId(login_id);
		manager.setName(name);
		manager.setPassword(password);
		manager.setType(type);
		manager.setStatus(status);
		int suc = manager.add();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$addManager</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int deleteManager(String id) throws PlatformException {
		OracleManager manager = new OracleManager();
		manager.setId(id);
		int suc = manager.delete();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteManager</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public Manager getManager(String id) throws PlatformException {
		return new OracleManager(id);
	}

	public int getManagerNum(List searchProperty) throws PlatformException {
		OracleBasicUserList basicuserList = new OracleBasicUserList();
		return basicuserList.getManagersNum(searchProperty);
	}

	public List getManagers(List searchProperty, List orderProperty)
			throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getManagers(Page page, List searchProperty, List orderProperty)
			throws PlatformException {
		OracleBasicUserList basicuserList = new OracleBasicUserList();
		return basicuserList.getManagers(page, searchProperty, orderProperty);
	}

	public int changeManagerGroup(String manager_id, String group_id)
			throws PlatformException {

		OracleManager manager = new OracleManager();
		manager.setId(manager_id);
		manager.setGroup_id(group_id);
		int suc = manager.changeGroup();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$changeManagerGroup</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public Student createStudent() throws PlatformException {
		return new OracleStudent();
	}

	public Teacher createTeacher() throws PlatformException {
		return new OracleTeacher();
	}

	public SiteTeacher createSiteTeacher() throws PlatformException {
		return new OracleSiteTeacher();
	}

	public int addSiteManager(String login_id, String name, String password,
			String site_id, String status) throws PlatformException {
		OracleSiteManager sitemanager = new OracleSiteManager();
		sitemanager.setLoginId(login_id);
		sitemanager.setName(name);
		sitemanager.setPassword(password);
		sitemanager.setSite_id(site_id);
		sitemanager.setStatus(status);
		int suc = sitemanager.add();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$addSiteManager</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int deleteSiteManager(String id) throws PlatformException {
		int i = 0;
		OracleSiteManager sitemanager = new OracleSiteManager();
		sitemanager.setLoginId(id);

		i = sitemanager.delete();

		SsoFactory sso = SsoFactory.getInstance();
		SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
		managerPriv.deleteUser = 1;
		SsoManage ssoManage = sso.creatSsoManage(managerPriv);

		SsoUser ssouser = ssoManage.getSsoUserLogin(id);
		ssoManage.deleteSsoUser(ssouser.getId());

		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteSiteManager</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return i;
	}

	public SiteManager getSiteManager(String id) throws PlatformException {
		return new OracleSiteManager(id);
	}

	public int getSiteManagerNum(List searchProperty) throws PlatformException {
		OracleBasicUserList basicuserList = new OracleBasicUserList();
		return basicuserList.getSiteManagersNum(searchProperty);
	}

	public List getSiteManagers(List searchProperty, List orderProperty)
			throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getSiteManagers(Page page, List searchProperty,
			List orderProperty) throws PlatformException {
		OracleBasicUserList basicuserList = new OracleBasicUserList();
		return basicuserList.getSiteManagers(page, searchProperty,
				orderProperty);
	}

	public int getManagerNum(String id, String login_id, String name)
			throws PlatformException {
		List searchProperties = new ArrayList();
		if (id != null && !id.equals("") && !id.equals("null")) {
			searchProperties.add(new SearchProperty("id", id, "="));
		}
		if (login_id != null && !login_id.equals("")
				&& !login_id.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", login_id, "="));
		}
		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "="));
		}
		return this.getManagerNum(searchProperties);
	}

	public List getManagers(Page page, String id, String login_id, String name)
			throws PlatformException {
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();
		if (id != null && !id.equals("") && !id.equals("null")) {
			searchProperties.add(new SearchProperty("id", id, "="));
		}
		if (login_id != null && !login_id.equals("")
				&& !login_id.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", login_id, "="));
		}
		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "="));
		}
		if (page != null) {
			return this.getManagers(page, searchProperties, orderProperties);
		} else {
			return this.getManagers(searchProperties, orderProperties);
		}
	}

	public int getSiteManagerNum(String id, String login_id, String name,
			String site_id) throws PlatformException {
		List searchProperties = new ArrayList();
		if (id != null && !id.equals("") && !id.equals("null")) {
			searchProperties.add(new SearchProperty("id", id, "="));
		}
		if (login_id != null && !login_id.equals("")
				&& !login_id.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", login_id, "="));
		}
		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "="));
		}
		if (site_id != null && !site_id.equals("") && !site_id.equals("null")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		return this.getSiteManagerNum(searchProperties);
	}

	public int getSiteManagerNum(String id, String login_id, String name,
			String site_id, String gender) throws PlatformException {
		List searchProperties = new ArrayList();
		if (id != null && !id.equals("") && !id.equals("null")) {
			searchProperties.add(new SearchProperty("id", id, "="));
		}
		if (login_id != null && !login_id.equals("")
				&& !login_id.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", login_id, "="));
		}
		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "="));
		}
		if (site_id != null && !site_id.equals("") && !site_id.equals("null")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "="));
		}
		return this.getSiteManagerNum(searchProperties);
	}

	public List getSiteManagers(Page page, String id, String login_id,
			String name, String site_id) throws PlatformException {
		List searchProperties = new ArrayList();
		if (id != null && !id.equals("") && !id.equals("null")) {
			searchProperties.add(new SearchProperty("id", id, "="));
		}
		if (login_id != null && !login_id.equals("")
				&& !login_id.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", login_id, "="));
		}
		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "="));
		}
		if (site_id != null && !site_id.equals("") && !site_id.equals("null")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (page != null) {
			return this.getSiteManagers(page, searchProperties, null);
		} else {
			return this.getSiteManagers(searchProperties, null);
		}
	}

	public List getSiteManagers(Page page, String id, String login_id,
			String name, String site_id, String gender)
			throws PlatformException {
		List searchProperties = new ArrayList();
		if (id != null && !id.equals("") && !id.equals("null")) {
			searchProperties.add(new SearchProperty("id", id, "="));
		}
		if (login_id != null && !login_id.equals("")
				&& !login_id.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", login_id, "="));
		}
		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "="));
		}
		if (site_id != null && !site_id.equals("") && !site_id.equals("null")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "="));
		}
		if (page != null) {
			return this.getSiteManagers(page, searchProperties, null);
		} else {
			return this.getSiteManagers(searchProperties, null);
		}
	}

	public List getStudents(Page page, String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("") && !id.equals("null")) {
				searchProperties.add(new SearchProperty("u.id", id, "="));
			}
			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}
			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
						"like"));
			}
			if (phone != null && !phone.equals("") && !phone.equals("null")) {
				searchProperties.add(new SearchProperty("eu.phone", phone,
						"like"));
			}
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")
					&& !edu_type_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.major_id", major_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}
			if (page != null) {
				return this.getStudents(page, searchProperties, null);
			} else {
				return this.getStudents(searchProperties, null);
			}
		} else {
			throw new PlatformException("��û�����ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public List getStudentsFee(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException {
		List searchProperties = new ArrayList();
		if (id != null && !id.equals("") && !id.equals("null")) {
			searchProperties.add(new SearchProperty("u.id", id, "="));
		}
		if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
			searchProperties
					.add(new SearchProperty("u.reg_no", reg_no, "like"));
		}
		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (id_card != null && !id_card.equals("") && !id_card.equals("null")) {
			searchProperties.add(new SearchProperty("u.id_card", id_card,
					"like"));
		}
		if (phone != null && !phone.equals("") && !phone.equals("null")) {
			searchProperties.add(new SearchProperty("eu.phone", phone, "like"));
		}
		if (grade_id != null && !grade_id.equals("")
				&& !grade_id.equals("null")) {
			searchProperties
					.add(new SearchProperty("u.grade_id", grade_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")
				&& !edu_type_id.equals("null")) {
			searchProperties.add(new SearchProperty("u.edutype_id",
					edu_type_id, "="));
		}
		if (major_id != null && !major_id.equals("")
				&& !major_id.equals("null")) {
			searchProperties
					.add(new SearchProperty("u.major_id", major_id, "="));
		}
		if (site_id != null && !site_id.equals("") && !site_id.equals("null")) {
			searchProperties.add(new SearchProperty("u.site_id", site_id, "="));
		}

		return this.getStudentsFee(page, searchProperties, null);

	}

	public List getStudents(Page page, String id, String reg_no, String name,
			String id_card, String phone, String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			String site_id = basicManagePriv.getSite_id();
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("") && !id.equals("null")) {
				searchProperties.add(new SearchProperty("u.id", id, "="));
			}
			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}
			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
						"like"));
			}
			if (phone != null && !phone.equals("") && !phone.equals("null")) {
				searchProperties.add(new SearchProperty("eu.phone", phone,
						"like"));
			}
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")
					&& !edu_type_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.major_id", major_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}
			if (page != null) {
				return this.getStudents(page, searchProperties, null);
			} else {
				return this.getStudents(searchProperties, null);
			}
		} else {
			throw new PlatformException("��û�����ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public int getStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("") && !id.equals("null")) {
				searchProperties.add(new SearchProperty("u.id", id, "="));
			}
			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}
			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
						"like"));
			}
			if (phone != null && !phone.equals("") && !phone.equals("null")) {
				searchProperties.add(new SearchProperty("eu.phone", phone,
						"like"));
			}
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")
					&& !edu_type_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.major_id", major_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}
			return this.getStudentsNum(searchProperties);
		} else {
			throw new PlatformException("��û�����ѧ����Ϣ��Ȩ�ޣ�");
		}
	}
	
	public int getStudentsFeeNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("") && !id.equals("null")) {
				searchProperties.add(new SearchProperty("u.id", id, "="));
			}
			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}
			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
						"like"));
			}
			if (phone != null && !phone.equals("") && !phone.equals("null")) {
				searchProperties.add(new SearchProperty("eu.phone", phone,
						"like"));
			}
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")
					&& !edu_type_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.major_id", major_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}
			return this.getStudentsFeeNum(searchProperties);
	}

	public int getStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			String site_id = basicManagePriv.getSite_id();
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("") && !id.equals("null")) {
				searchProperties.add(new SearchProperty("u.id", id, "="));
			}
			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}
			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
						"like"));
			}
			if (phone != null && !phone.equals("") && !phone.equals("null")) {
				searchProperties.add(new SearchProperty("eu.phone", phone,
						"like"));
			}
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")
					&& !edu_type_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.major_id", major_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}
			return this.getStudentsNum(searchProperties);
		} else {
			throw new PlatformException("��û�����ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public List getTeachers(Page page, String search_type, String search_value,
			String teach_level) throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			List searchProperties = new ArrayList();
			if (search_value != null && !search_value.equals("")
					&& !search_value.equals("null")) {
				searchProperties.add(new SearchProperty(search_type,
						search_value, "like"));
			}
			if (teach_level != null && !teach_level.equals("")
					&& !teach_level.equals("null")) {
				searchProperties.add(new SearchProperty("teach_level",
						teach_level, "="));
			}
			if (page != null) {
				return this.getTeachers(page, searchProperties, null);
			} else {
				return this.getTeachers(searchProperties, null);
			}
		} else {
			throw new PlatformException("��û����=�ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public List getSiteTeachers(Page page, String search_type,
			String search_value, String isConfirm) throws PlatformException {
		if (basicManagePriv.getSiteTeacher == 1) {
			List searchProperties = new ArrayList();
			if (search_value != null && !search_value.equals("")
					&& !search_value.equals("null")) {
				searchProperties.add(new SearchProperty(search_type,
						search_value, "like"));
				if ("0".equals(isConfirm) || "1".equals(isConfirm))
					searchProperties.add(new SearchProperty("isConfirm",
							isConfirm, "="));

			}
			if (searchProperties == null)
				searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("site_id",
					this.basicManagePriv.getSite_id(), "="));
			if (page != null) {
				return this.getTeachers(page, searchProperties, null);
			} else {
				return this.getTeachers(searchProperties, null);
			}
		} else {
			throw new PlatformException("��û����=�ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int getSiteTeachersNum(String search_type, String search_value)
			throws PlatformException {
		if (basicManagePriv.getSiteTeacher == 1) {
			List searchProperties = new ArrayList();
			if (search_value != null && !search_value.equals("")
					&& !search_value.equals("null")) {
				searchProperties.add(new SearchProperty(search_type,
						search_value, "like"));
			}
			if (searchProperties == null)
				searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("site_id",
					this.basicManagePriv.getSite_id(), "="));
			return this.getTeachersNum(searchProperties);
		} else {
			throw new PlatformException("��û����8�����ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public List getAllStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			String site_id = basicManagePriv.getSite_id();
			List searchProperties = new ArrayList();
			if (id != null && !id.equals("") && !id.equals("null")) {
				searchProperties.add(new SearchProperty("u.id", id, "="));
			}
			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}
			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
						"like"));
			}
			if (phone != null && !phone.equals("") && !phone.equals("null")) {
				searchProperties.add(new SearchProperty("eu.phone", phone,
						"like"));
			}
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")
					&& !edu_type_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.major_id", major_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}
			if (page != null) {
				return this.getAllStudents(page, searchProperties, null);
			} else {
				return this.getAllStudents(searchProperties, null);
			}
		} else {
			throw new PlatformException("��û�����ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public int getStudentsNum(String reg_no, String name, String id_card,
			String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk)
			throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			List searchProperties = new ArrayList();
			if (card_type != null && !card_type.equals("")
					&& !card_type.equals("null")) {
				searchProperties.add(new SearchProperty("u.card_type",
						card_type, "="));
			}
			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}
			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
						"like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("eu.gender", gender,
						"="));
			}
			if (folk != null && !folk.equals("") && !folk.equals("null")) {
				searchProperties.add(new SearchProperty("eu.folk", folk, "="));
			}
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")
					&& !edu_type_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.major_id", major_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}
			return this.getStudentsNum(searchProperties);
		} else {
			throw new PlatformException("��û�����ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public int getStudentsNum(String reg_no, String name, String id_card,
			String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk,
			String degree_status) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			List searchProperties = new ArrayList();
			if (card_type != null && !card_type.equals("")
					&& !card_type.equals("null")) {
				searchProperties.add(new SearchProperty("u.card_type",
						card_type, "="));
			}
			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}
			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
						"like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("eu.gender", gender,
						"="));
			}
			if (folk != null && !folk.equals("") && !folk.equals("null")) {
				searchProperties.add(new SearchProperty("eu.folk", folk, "="));
			}
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")
					&& !edu_type_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.major_id", major_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}
			if (degree_status != null && !degree_status.equals("")
					&& !degree_status.equals("null")) {
				searchProperties.add(new SearchProperty("u.degree_status",
						degree_status, "="));
			}
			return this.getStudentsNum(searchProperties);
		} else {
			throw new PlatformException("��û�����ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public List getStudents(Page page, String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk)
			throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			List searchProperties = new ArrayList();
			if (card_type != null && !card_type.equals("")
					&& !card_type.equals("null")) {
				searchProperties.add(new SearchProperty("u.card_type",
						card_type, "="));
			}
			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}
			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
						"like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("eu.gender", gender,
						"="));
			}
			if (folk != null && !folk.equals("") && !folk.equals("null")) {
				searchProperties.add(new SearchProperty("eu.folk", folk, "="));
			}
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")
					&& !edu_type_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.major_id", major_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}
			if (page != null) {
				return this.getStudents(page, searchProperties, null);

			} else {
				return this.getStudents(searchProperties, null);
			}
		} else {
			throw new PlatformException("��û�����ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public List getStudents(Page page, String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk,
			String degree_status) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			List searchProperties = new ArrayList();
			if (card_type != null && !card_type.equals("")
					&& !card_type.equals("null")) {
				searchProperties.add(new SearchProperty("u.card_type",
						card_type, "="));
			}
			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}
			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
						"like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("eu.gender", gender,
						"="));
			}
			if (folk != null && !folk.equals("") && !folk.equals("null")) {
				searchProperties.add(new SearchProperty("eu.folk", folk, "="));
			}
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")
					&& !edu_type_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.major_id", major_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}
			if (degree_status != null && !degree_status.equals("")
					&& !degree_status.equals("null")) {
				searchProperties.add(new SearchProperty("u.degree_status",
						degree_status, "="));
			}
			if (page != null) {
				return this.getStudents(page, searchProperties, null);
			} else {
				return this.getStudents(searchProperties, null);
			}
		} else {
			throw new PlatformException("��û�����ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public HashMap getStudentStatByEduType(String siteId)
			throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			OracleBasicUserList basicUserList = new OracleBasicUserList();
			return basicUserList.getStudentStatByEduType(siteId, "");
		} else {
			throw new PlatformException("��û�����ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public HashMap getStudentStatByEduType(String siteId, String gradeId)
			throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			OracleBasicUserList basicUserList = new OracleBasicUserList();
			return basicUserList.getStudentStatByEduType(siteId, gradeId);
		} else {
			throw new PlatformException("��û�а����ͳ��ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public HashMap getStudentStatByGrade(String siteId, String gradeId)
			throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			OracleBasicUserList basicUserList = new OracleBasicUserList();
			return basicUserList.getStudentStatByGrade(siteId, gradeId);
		} else {
			throw new PlatformException("��û�����ѧ����Ϣ��Ȩ�ޣ�");
		}
	}

	public int getTeachersNum(String search_type, String search_value,
			String teach_level) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getSiteTeachersNum(String search_type, String search_value,
			String isConfirm) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getTeachers(Page page, String name, String cardno,
			String gender, String teacher_level) throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			List searchProperties = new ArrayList();

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("gh", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if (teacher_level != null && !teacher_level.equals("")
					&& !teacher_level.equals("null")) {
				searchProperties.add(new SearchProperty("teach_level",
						teacher_level, "="));
			}
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getTeachers(page, searchProperties, null);

		} else {
			throw new PlatformException("��û����=�ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int getTeachersNum(String name, String cardno, String gender,
			String teacher_level) throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			List searchProperties = new ArrayList();

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("gh", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if (teacher_level != null && !teacher_level.equals("")
					&& !teacher_level.equals("null")) {
				searchProperties.add(new SearchProperty("teach_level",
						teacher_level, "="));
			}
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getTeachersNum(searchProperties);

		} else {
			throw new PlatformException("��û����=�ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public List getSiteTeachers(Page page, String name, String cardno,
			String gender, String isConfirm) throws PlatformException {
		if (basicManagePriv.getSiteTeacher == 1) {
			List searchProperties = new ArrayList();

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("gh", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if (isConfirm != null && !isConfirm.equals(""))
				searchProperties.add(new SearchProperty("isConfirm", isConfirm,
						"="));
			if (searchProperties == null)
				searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("site_id",
					this.basicManagePriv.getSite_id(), "="));
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getSiteTeachers(page, searchProperties, null);

		} else {
			throw new PlatformException("��û����8�����ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int getSiteTeachersNum(String name, String cardno, String gender,
			String isConfirm) throws PlatformException {
		if (basicManagePriv.getSiteTeacher == 1) {
			List searchProperties = new ArrayList();

			searchProperties.add(new SearchProperty("site_id", basicManagePriv
					.getSite_id(), "="));

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("gh", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if ("0".equals(isConfirm) || "1".equals(isConfirm))
				searchProperties.add(new SearchProperty("isConfirm", isConfirm,
						"="));
			if (searchProperties == null)
				searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("site_id",
					this.basicManagePriv.getSite_id(), "="));
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getSiteTeachersNum(searchProperties);

		} else {
			throw new PlatformException("��û����8�����ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int addSiteAdmin(String login_id, String name, String password,
			String mobilePhone, String status, String type, String site_id,
			String group_id) throws PlatformException {
		if (basicManagePriv.addSiteAdmin == 1) {
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
				sitemanager.setMobilePhone(mobilePhone);
				sitemanager.setStatus(status);
				sitemanager.setType(type);
				sitemanager.setSite_id(site_id);
				if ("1".equals(group_id))
					sitemanager.setGroup_id(group_id);
				else
					sitemanager.setGroup_id("0");
				int suc = sitemanager.add();
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.basicManagePriv.getManagerId()
										+ "</whaty><whaty>BEHAVIOR$|$addSiteAdmin</whaty><whaty>STATUS$|$"
										+ suc
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.SITEMANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
				return suc;
			} catch (PlatformException e) {
				return 0;
			}
		} else {
			throw new PlatformException("��û����ӷ�վ����Ա��Ȩ�ޣ�");
		}
	}

	public int getSiteManagerListNum(String siteId, String groupId)
			throws PlatformException {
		OracleSiteManager sitemanager = new OracleSiteManager();
		return sitemanager.getSiteManagerListNum(siteId, groupId);
	}

	public List getSiteManagerList(Page page, String siteId, String groupId)
			throws PlatformException {
		OracleSiteManager sitemanager = new OracleSiteManager();
		return sitemanager.getSiteManagerList(siteId, groupId);
	}

	public List getSiteManagerList(String siteId, String groupId)
			throws PlatformException {
		OracleSiteManager sitemanager = new OracleSiteManager();
		return sitemanager.getSiteManagerList(siteId, groupId);
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
			if (password == null || password.trim().equals("")
					|| password.trim().equals("null"))
				ssoManage.updateSsoUser(ssouser.getId(), login_id, ssouser
						.getPassword(), name, null, null);
			else
				ssoManage.updateSsoUser(ssouser.getId(), login_id, password,
						name, null, null);

			OracleSiteManager sitemanager = new OracleSiteManager();

			int suc = sitemanager.update(login_id, name, site_id, password,
					mobile, status);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateSiteAdmin</whaty><whaty>STATUS$|$"
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

	public int updateStudentPassword(String id, String new_pwd)
			throws PlatformException {
		if (basicManagePriv.updateStudentPwd == 1) {
			OracleStudent oraclestudent = new OracleStudent(id);
			oraclestudent.setPassword(new_pwd);
			int suc = oraclestudent.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|updateStudentPassword</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸�ѧ�������Ȩ��");
		}
	}

	public int addSiteAdmin(HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.addSiteAdmin == 1) {
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

				ssoManage.addSsoUser(login_id, password, admin_name, null,
						email);
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
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.basicManagePriv.getManagerId()
										+ "</whaty><whaty>BEHAVIOR$|$addSiteAdmin</whaty><whaty>STATUS$|$"
										+ suc
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.SITEMANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
				return suc;
			} catch (PlatformException e) {
				return 0;
			}
		} else {
			throw new PlatformException("��û����ӷ�վ����Ա��Ȩ�ޣ�");
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
			sitemanager.setType(type);
			sitemanager.setSite_id(site_id);
			sitemanager.setRedundace(redundance);

			int suc = sitemanager.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateSiteAdmin</whaty><whaty>STATUS$|$"
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

	public List getRegStudents(Page page, String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk,
			String batchId, String status) throws PlatformException {
		List searchProperties = new ArrayList();
		if (card_type != null && !card_type.equals("")
				&& !card_type.equals("null")) {
			searchProperties
					.add(new SearchProperty("card_type", card_type, "="));
		}
		if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
			searchProperties.add(new SearchProperty("reg_no", reg_no, "like"));
		}
		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (id_card != null && !id_card.equals("") && !id_card.equals("null")) {
			searchProperties
					.add(new SearchProperty("id_card", id_card, "like"));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "="));
		}
		if (folk != null && !folk.equals("") && !folk.equals("null")) {
			searchProperties.add(new SearchProperty("folk", folk, "="));
		}
		if (grade_id != null && !grade_id.equals("")
				&& !grade_id.equals("null")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")
				&& !edu_type_id.equals("null")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (major_id != null && !major_id.equals("")
				&& !major_id.equals("null")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (site_id != null && !site_id.equals("") && !site_id.equals("null")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (batchId != null && !batchId.equals("") && !batchId.equals("null")) {
			searchProperties.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (status != null && !status.equals("") && !status.equals("null")) {
			searchProperties.add(new SearchProperty("register_status", status,
					"="));
		}
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		if (page != null) {
			return basicdatalist.getUnRegStudents(page, searchProperties, null);
		} else {
			return basicdatalist.getUnRegStudents(page, searchProperties, null);
		}

	}

	public int getRegStudentsNum(String reg_no, String name, String id_card,
			String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk,
			String batchId, String status) throws PlatformException {
		List searchProperties = new ArrayList();
		if (card_type != null && !card_type.equals("")
				&& !card_type.equals("null")) {
			searchProperties
					.add(new SearchProperty("card_type", card_type, "="));
		}
		if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
			searchProperties.add(new SearchProperty("reg_no", reg_no, "like"));
		}
		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (id_card != null && !id_card.equals("") && !id_card.equals("null")) {
			searchProperties
					.add(new SearchProperty("id_card", id_card, "like"));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "="));
		}
		if (folk != null && !folk.equals("") && !folk.equals("null")) {
			searchProperties.add(new SearchProperty("folk", folk, "="));
		}
		if (grade_id != null && !grade_id.equals("")
				&& !grade_id.equals("null")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")
				&& !edu_type_id.equals("null")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (major_id != null && !major_id.equals("")
				&& !major_id.equals("null")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (site_id != null && !site_id.equals("") && !site_id.equals("null")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (batchId != null && !batchId.equals("") && !batchId.equals("null")) {
			searchProperties.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (status != null && !status.equals("") && !status.equals("null")) {
			searchProperties.add(new SearchProperty("register_status", status,
					"="));
		}
		return this.getRegStudentsNum(searchProperties);
	}

	/**
	 * ��������б�
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public List getRecruitBatches(Page page) throws PlatformException {
		if (basicManagePriv.getRecruitBatch == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("id", OrderProperty.DESC));
			return basicdatalist.getBatchs(page, null, orderProperty);
		} else {
			throw new PlatformException("��û�в鿴���������Ϣ��Ȩ�ޣ�");
		}
	}

	public int getRegStudentsNum(List searchProperties)
			throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getUnRegStudentsNum(searchProperties);
	}

	/**
	 * ע�����ͳ��
	 * 
	 * @return ע�����ͳ��
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public List getRegisterStat(Page page, String batchId, String siteId,
			String majorId, String eduTypeId) throws PlatformException {
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		List searchProperties = new ArrayList();
		if (siteId != null && !siteId.equals("")) {
			searchProperties.add(new SearchProperty("site_id", siteId, "="));
		}
		if (eduTypeId != null && !eduTypeId.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", eduTypeId,
					"="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperties.add(new SearchProperty("major_id", majorId, "="));
		}
		if (batchId != null && !batchId.equals("")) {
			searchProperties.add(new SearchProperty("batch_id", batchId, "="));
		}
		return basicdatalist.getRegisterStat(page, searchProperties, null);
	}

	/**
	 * ע�����ͳ��
	 * 
	 * @return ע�����ͳ��
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public int getRegisterStatNum(String batchId, String siteId,
			String majorId, String eduTypeId) throws PlatformException,
			PlatformException {
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		List searchProperties = new ArrayList();
		if (siteId != null && !siteId.equals("")) {
			searchProperties.add(new SearchProperty("site_id", siteId, "="));
		}
		if (eduTypeId != null && !eduTypeId.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", eduTypeId,
					"="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperties.add(new SearchProperty("major_id", majorId, "="));
		}
		if (batchId != null && !batchId.equals("")) {
			searchProperties.add(new SearchProperty("batch_id", batchId, "="));
		}
		return basicdatalist.getRegisterStatNum(searchProperties);
	}

	public List getSiteTeachers(Page page, String name, String cardno,
			String gender, String type, String isConfirm)
			throws PlatformException {
		if (basicManagePriv.getSiteTeacher == 1) {
			List searchProperties = new ArrayList();

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("gh", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if (isConfirm != null && !isConfirm.equals(""))
				searchProperties.add(new SearchProperty("isConfirm", isConfirm,
						"="));
			if (searchProperties == null)
				searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("site_id",
					this.basicManagePriv.getSite_id(), "="));
			searchProperties.add(new SearchProperty("type", type, "="));
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getSiteTeachers(page, searchProperties, null);

		} else {
			throw new PlatformException("��û����8�����ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public int getSiteTeachersNum(String name, String cardno, String gender,
			String type, String isConfirm) throws PlatformException {
		if (basicManagePriv.getSiteTeacher == 1) {
			List searchProperties = new ArrayList();

			searchProperties.add(new SearchProperty("site_id", basicManagePriv
					.getSite_id(), "="));

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("gh", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if ("0".equals(isConfirm) || "1".equals(isConfirm))
				searchProperties.add(new SearchProperty("isConfirm", isConfirm,
						"="));
			if (searchProperties == null)
				searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("site_id",
					this.basicManagePriv.getSite_id(), "="));
			searchProperties.add(new SearchProperty("type", type, "="));
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getSiteTeachersNum(searchProperties);

		} else {
			throw new PlatformException("��û����8�����ʦ��Ϣ��Ȩ�ޣ�");
		}
	}

	public List getSiteTeachers(Page page, String name, String cardno,
			String gender, String type, String isConfirm, String siteId,
			String pici) throws PlatformException {
		List searchProperties = new ArrayList();

		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
			searchProperties.add(new SearchProperty("gh", cardno, "like"));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "="));
		}
		if (isConfirm != null && !isConfirm.equals("")) {
			searchProperties
					.add(new SearchProperty("isConfirm", isConfirm, "="));
		}
		if (siteId != null && !siteId.equals(""))
			searchProperties.add(new SearchProperty("site_id", siteId, "="));
		if (searchProperties == null)
			searchProperties = new ArrayList();
		searchProperties.add(new SearchProperty("type", type, "="));
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getSiteTeachers(page, searchProperties, null);
	}

	/**
	 * �жϷ�վ��ʦ�Ƿ�ָ����ָ��ѧ�� lwx 2008-06-05
	 */
	public int isHaveTutorStudent(String tid) throws PlatformException {
		OracleSiteTeacherLimit limit = new OracleSiteTeacherLimit();
		return limit.isHaveTutorStudent(tid);
	}

	public List getStudentsFee(Page page, List searchProperties, List orderProperties) throws PlatformException {
		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getStudentsFee(page, searchProperties,
				orderProperties);
	}

	public int getStudentsFeeNum(List searchProperties) throws PlatformException {
		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getStudentsFeeNum(searchProperties);
	}
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

	@Override
	public int delSiteTeacherLimit(String student_id) throws PlatformException{
		SiteTeacherLimit limit = new OracleSiteTeacherLimit(student_id,"STUDENT");
		int i = limit.delete() ;
		return i;
	}
	
	
}
