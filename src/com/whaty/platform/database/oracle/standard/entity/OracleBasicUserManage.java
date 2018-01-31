package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.ClassMember;
import com.whaty.platform.database.oracle.standard.campus.base.OracleClassMember;
import com.whaty.platform.database.oracle.standard.entity.user.OracleBasicUserList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManager;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteManager;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacherLimit;
import com.whaty.platform.database.oracle.standard.entity.user.OracleUserBatchActivity;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUser;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUserAuthorization;
import com.whaty.platform.entity.BasicUserManage;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.Manager;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteManager;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.entity.user.TeacherEduInfo;
import com.whaty.platform.entity.user.TeacherLimit;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.RedundanceData;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.SsoLog;
import com.whaty.platform.util.log.UserLog;

public class OracleBasicUserManage extends BasicUserManage {

	ManagerPriv basicManagePriv;

	public OracleBasicUserManage(ManagerPriv amanagerpriv) {
		this.basicManagePriv = amanagerpriv;
	}

	public Teacher getTeacher(String aid) throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			return new OracleTeacher(aid);
		} else {
			throw new PlatformException("您没有查看教师信息的权限！");
		}
	}

	public int deleteTeacher(String aid) throws PlatformException {
		if (basicManagePriv.deleteTeacher == 1) {
			int suc = new OracleTeacher(aid).delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTeacher</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除教师信息的权限！");
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteSiteTeacher</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除辅导教师信息的权限！");
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateTeacher</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改教师信息的权限");
		}
	}

	/**
	 * 更新分站教师
	 */
	public int updateSiteTeacher(String teacherId, String name,
			String password, String email, HumanNormalInfo normalInfo,
			TeacherEduInfo teachereduInfo, HumanPlatformInfo platformInfo,
			RedundanceData redundace) throws PlatformException {
		if (basicManagePriv.updateTeacher == 1) {
			OracleSiteTeacher oracleteacher = new OracleSiteTeacher();
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateTeacher</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改教师信息的权限");
		}
	}

	public int getAssistanceTeachersNum(String teachclass_id)
			throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getAssistanceTeachersNum(teachclass_id);
	}

	public List getAssistanceTeachers(Page page, String teachclass_id)
			throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getAssistanceTeachers(page, teachclass_id);
	}

	public List getTeachers(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getTeachers(page, searchproperty, orderproperty);
	}

	public List getTeachers(List searchproperty, List orderproperty)
			throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getTeachers(searchproperty, orderproperty);
	}

	public int getTeachersNum(List searchproperty) throws PlatformException {

		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getTeachersNum(searchproperty);
	}

	public List getTeachers(Page page) throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			return this.getTeachers(page, null, null);
		} else {
			throw new PlatformException("您没有查看教师信息的权限");
		}
	}

	public List getTeachers() throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			return this.getTeachers(null, null, null);
		} else {
			throw new PlatformException("您没有查看教师信息的权限");
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

	public List getStudentsFee(Page page, List searchProperties,
			List orderProperties) throws PlatformException {
		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getStudentsFee(page, searchProperties,
				orderProperties);
	}

	public int getStudentsFeeNum(List searchProperties)
			throws PlatformException {
		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getStudentsFeeNum(searchProperties);
	}

	public List getStudents(Page page, List searchProperties,
			List orderProperties) throws PlatformException {
		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getStudents(page, searchProperties,
				orderProperties);
	}

	public List getStudents(List searchProperties, List orderProperties)
			throws PlatformException {

		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getStudents(searchProperties,
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addStudent</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;

		} else {
			throw new PlatformException("您没有添加学生的权限");
		}
	}

	public int addStudent(String name, String password, String email,
			HumanNormalInfo normalInfo, StudentEduInfo eduInfo,
			HumanPlatformInfo platformInfo) throws PlatformException {
		if (basicManagePriv.addStudent == 1) {
			OracleStudent oraclestudent = new OracleStudent();
			oraclestudent.setEmail(email);
			oraclestudent.setName(name);
			oraclestudent.setPassword(password);
			oraclestudent.setNormalInfo(normalInfo);
			oraclestudent.setStudentInfo(eduInfo);
			oraclestudent.setPlatformInfo(platformInfo);
			int suc = oraclestudent.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addStudent</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;

		} else {
			throw new PlatformException("您没有添加学生的权限");
		}
	}

	public void addStudentBatch(List studentList) throws PlatformException {
		if (basicManagePriv.addStudent == 1) {
			OracleUserBatchActivity batch = new OracleUserBatchActivity();
			batch.studentAddBatch(studentList);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addStudentBatch</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有添加学生的权限");
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteStudent</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除学生的权限");
		}
	}

	public void deleteStudentBatch(String studentId[]) throws PlatformException {
		if (basicManagePriv.deleteStudent == 1) {
			OracleUserBatchActivity batch = new OracleUserBatchActivity();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteStudentBatch</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			batch.studentDeleteBatch(studentId);
		} else {
			throw new PlatformException("您没有删除学生的权限");
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
			throw new PlatformException("您没有浏览学员信息的权限");
		}
	}

	public Student getStudentByRegNo(String regNo) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			OracleBasicUserList userList = new OracleBasicUserList();
			return userList.getStudentByRegNo(regNo);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限");
		}
	}

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
			OracleStudent oraclestudent = new OracleStudent(studentId);
			// oraclestudent.setEmail(email);
			oraclestudent.setName(name);
			// oraclestudent.setPassword(password);
			eduInfo.setReg_no(oraclestudent.getStudentInfo().getReg_no());
			oraclestudent.setNormalInfo(normalInfo);
			String photoLink = oraclestudent.getStudentInfo().getPhoto_link();
			if (eduInfo.getPhoto_link() == null
					|| eduInfo.getPhoto_link().equals("")
					|| eduInfo.getPhoto_link().equals("null"))
				eduInfo.setPhoto_link(photoLink);
			oraclestudent.setStudentInfo(eduInfo);
			oraclestudent.setPlatformInfo(platformInfo);
			oraclestudent.setRedundace(redundance);
			int suc = oraclestudent.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateStudent</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改教师信息的权限");
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
			throw new PlatformException("您没有查看教师信息的权限");
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addTeacher</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加教师信息的权限");
		}
	}

	public void addTeacherBatch(List teacherList) throws PlatformException {
		if (basicManagePriv.addTeacher == 1) {
			OracleUserBatchActivity batch = new OracleUserBatchActivity();
			batch.teacherAddBatch(teacherList);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addTeacherBatch</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有添加学生的权限");
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
								+ this.basicManagePriv.getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$addManager</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
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
								+ this.basicManagePriv.getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$deleteManager</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
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
		return manager.changeGroup();
	}

	public Student createStudent() throws PlatformException {
		return new OracleStudent();
	}

	public Teacher createTeacher() throws PlatformException {
		return new OracleTeacher();
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
								+ this.basicManagePriv.getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$addSiteManager</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int deleteSiteManager(String id) throws PlatformException {
		OracleSiteManager sitemanager = new OracleSiteManager();
		sitemanager.setId(id);
		int suc = sitemanager.delete();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$deleteSiteManager</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
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

	public int getManagerNum(String id, String login_id, String name,
			String gender) throws PlatformException {
		List searchProperties = new ArrayList();
		if (id != null && !id.equals("") && !id.equals("null")) {
			searchProperties.add(new SearchProperty("id", id, "="));
		}
		if (login_id != null && !login_id.equals("")
				&& !login_id.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", login_id, "="));
		}
		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "like"));
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
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (page != null) {
			return this.getManagers(page, searchProperties, orderProperties);
		} else {
			return this.getManagers(searchProperties, orderProperties);
		}
	}

	public List getManagers(Page page, String id, String login_id, String name,
			String gender) throws PlatformException {
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
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "like"));
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
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (site_id != null && !site_id.equals("") && !site_id.equals("null")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "like"));
		}
		return this.getSiteManagerNum(searchProperties);
	}

	public List getSiteManagers(Page page, String id, String login_id,
			String name, String site_id) throws PlatformException {
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
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (site_id != null && !site_id.equals("") && !site_id.equals("null")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		orderProperties.add(new OrderProperty("login_id"));
		if (page != null) {
			return this
					.getSiteManagers(page, searchProperties, orderProperties);
		} else {
			return this.getSiteManagers(searchProperties, orderProperties);
		}
	}

	public List getSiteManagers(Page page, String id, String login_id,
			String name, String site_id, String gender)
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
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (site_id != null && !site_id.equals("") && !site_id.equals("null")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "="));
		}
		orderProperties.add(new OrderProperty("login_id"));
		if (page != null) {
			return this
					.getSiteManagers(page, searchProperties, orderProperties);
		} else {
			return this.getSiteManagers(searchProperties, orderProperties);
		}
	}

	public List getStudents(Page page, String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			List searchProperties = new ArrayList();
			List orderProperties = new ArrayList();
			orderProperties.add(new OrderProperty(
					"site_name,grade_name,major_name,edutype_name,reg_no"));
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

			searchProperties.add(new SearchProperty("u.site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("u.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("u.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("u.grade_id",
					basicManagePriv.getGradeList(), "in"));

			if (page != null) {
				return this
						.getStudents(page, searchProperties, orderProperties);
			} else {
				return this.getStudents(searchProperties, orderProperties);
			}
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}
	
	public List getStudentsFee(Page page, String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			List searchProperties = new ArrayList();
			List orderProperties = new ArrayList();
			orderProperties.add(new OrderProperty(
					"site_name,grade_name,major_name,edutype_name,reg_no"));
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

			searchProperties.add(new SearchProperty("u.site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("u.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("u.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("u.grade_id",
					basicManagePriv.getGradeList(), "in"));

				return this
						.getStudentsFee(page, searchProperties, orderProperties);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
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
			searchProperties.add(new SearchProperty("u.site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("u.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("u.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("u.grade_id",
					basicManagePriv.getGradeList(), "in"));

			return this.getStudentsNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}
	
	public int getStudentsFeeNum(String id, String reg_no, String name,
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
			searchProperties.add(new SearchProperty("u.site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("u.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("u.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("u.grade_id",
					basicManagePriv.getGradeList(), "in"));

			return this.getStudentsFeeNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	public int getStudentsNum(String reg_no, String id_card, String site_id,
			String major_id, String edu_type_id, String grade_id,
			String semester_id) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			List searchProperties = new ArrayList();

			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}

			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
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
			if (semester_id != null && !semester_id.equals("")
					&& !semester_id.equals("null")) {
				searchProperties.add(new SearchProperty("u.semester_id",
						semester_id, "="));
			}
			searchProperties.add(new SearchProperty("u.site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("u.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("u.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("u.grade_id",
					basicManagePriv.getGradeList(), "in"));

			return this.getStudentsNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
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
			throw new PlatformException("您没有浏览教师信息的权限！");
		}
	}

	public int getTeachersNum(String search_type, String search_value,
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
			return this.getTeachersNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览教师信息的权限！");
		}
	}

	public List getEachStatusTeachersNum() throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			List numList = new ArrayList();
			List searchProperties = new ArrayList();

			searchProperties.add(new SearchProperty("status", "0000", "="));
			searchProperties.add(new SearchProperty("teach_level", "教师", "="));
			numList.add(new Integer(this.getTeachersNum(searchProperties)));

			searchProperties.clear();
			searchProperties.add(new SearchProperty("status", "0000", "="));
			searchProperties.add(new SearchProperty("teach_level", "助教", "="));
			numList.add(new Integer(this.getTeachersNum(searchProperties)));

			searchProperties.clear();
			searchProperties.add(new SearchProperty("status", "1111", "="));
			searchProperties.add(new SearchProperty("teach_level", "教师", "="));
			numList.add(new Integer(this.getTeachersNum(searchProperties)));

			searchProperties.clear();
			searchProperties.add(new SearchProperty("status", "1111", "="));
			searchProperties.add(new SearchProperty("teach_level", "助教", "="));
			numList.add(new Integer(this.getTeachersNum(searchProperties)));
			System.out.println(numList.get(0).toString());
			return numList;
		} else {
			throw new PlatformException("您没有浏览教师信息的权限！");
		}
	}

	//条件 作了修改 bjsy2 lwx
	public int getStudentsNum(String reg_no, String name, String id_card,
			String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk)
			throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			List searchProperties = new ArrayList();
//			if (card_type != null && !card_type.equals("")
//					&& !card_type.equals("null")) {
//				searchProperties.add(new SearchProperty("u.card_type",
//						card_type, "="));
//			}
//			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
//				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
//						"like"));
//			}
//			if (name != null && !name.equals("") && !name.equals("null")) {
//				searchProperties.add(new SearchProperty("name", name, "like"));
//			}
//			if (id_card != null && !id_card.equals("")
//					&& !id_card.equals("null")) {
//				searchProperties.add(new SearchProperty("u.id_card", id_card,
//						"like"));
//			}
//			if (gender != null && !gender.equals("") && !gender.equals("null")) {
//				searchProperties.add(new SearchProperty("eu.gender", gender,
//						"="));
//			}
//			if (folk != null && !folk.equals("") && !folk.equals("null")) {
//				searchProperties.add(new SearchProperty("eu.folk", folk, "="));
//			}
//			if (grade_id != null && !grade_id.equals("")
//					&& !grade_id.equals("null")) {
//				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
//						"="));
//			}
//			if (edu_type_id != null && !edu_type_id.equals("")
//					&& !edu_type_id.equals("null")) {
//				searchProperties.add(new SearchProperty("u.edutype_id",
//						edu_type_id, "="));
//			}
//			if (major_id != null && !major_id.equals("")
//					&& !major_id.equals("null")) {
//				searchProperties.add(new SearchProperty("u.major_id", major_id,
//						"="));
//			}
//			if (site_id != null && !site_id.equals("")
//					&& !site_id.equals("null")) {
//				searchProperties.add(new SearchProperty("u.site_id", site_id,
//						"="));
//			}
			if (card_type != null && !card_type.equals("")
					&& !card_type.equals("null")) {
				searchProperties.add(new SearchProperty("card_type",
						card_type, "="));
			}
			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("reg_no", reg_no,
						"like"));
			}
			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("id_card", id_card,
						"like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender,
						"like"));
			}
			if (folk != null && !folk.equals("") && !folk.equals("null")) {
				searchProperties.add(new SearchProperty("folk", folk, "="));
			}
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")
					&& !edu_type_id.equals("null")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null")) {
				searchProperties.add(new SearchProperty("site_id", site_id,
						"="));
			}
			searchProperties.add(new SearchProperty("site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("grade_id",
					basicManagePriv.getGradeList(), "in"));
			return this.getStudentsNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	public int getAllStudentsNum(String reg_no, String name, String id_card,
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
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	public int getUnRegStudentsNum(String semesterId, String reg_no,
			String name, String id_card, String card_type, String site_id,
			String major_id, String edu_type_id, String grade_id,
			String gender, String folk) throws PlatformException {
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
			searchProperties.add(new SearchProperty("u.site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("u.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("u.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("u.grade_id",
					basicManagePriv.getGradeList(), "in"));

			searchProperties.add(new SearchProperty("u.id",
					"select user_id from entity_register_info where semester_id = '"
							+ semesterId + "'", "notIn"));
			return this.getStudentsNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
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
				searchProperties.add(new SearchProperty("card_type",
						card_type, "="));
			}
			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null")) {
				searchProperties.add(new SearchProperty("reg_no", reg_no,
						"like"));
			}
			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (id_card != null && !id_card.equals("")
					&& !id_card.equals("null")) {
				searchProperties.add(new SearchProperty("id_card", id_card,
						"like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender,
						"like"));
			}
			if (folk != null && !folk.equals("") && !folk.equals("null")) {
				searchProperties.add(new SearchProperty("folk", folk, "="));
			}
			if (grade_id != null && !grade_id.equals("")
					&& !grade_id.equals("null")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")
					&& !edu_type_id.equals("null")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null")) {
				searchProperties.add(new SearchProperty("site_id", site_id,
						"="));
			}
			searchProperties.add(new SearchProperty("site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("grade_id",
					basicManagePriv.getGradeList(), "in"));
			List order = new ArrayList();
			order.add(new OrderProperty("reg_no", OrderProperty.ASC));
			if (page != null) {
				return this.getStudents(page, searchProperties, order);
			} else {
				return this.getStudents(searchProperties, order);
			}
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	public List getAllStudents(Page page, String reg_no, String name,
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
			List order = new ArrayList();
			order.add(new OrderProperty("reg_no", OrderProperty.ASC));
			if (page != null) {
				return this.getStudents(page, searchProperties, order);
			} else {
				return this.getStudents(searchProperties, order);
			}
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	public List getUnRegStudents(Page page, String semesterId, String reg_no,
			String name, String id_card, String card_type, String site_id,
			String major_id, String edu_type_id, String grade_id,
			String gender, String folk) throws PlatformException {
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
			searchProperties.add(new SearchProperty("u.site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("u.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("u.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("u.grade_id",
					basicManagePriv.getGradeList(), "in"));

			searchProperties.add(new SearchProperty("u.id",
					"select user_id from entity_register_info where semester_id = '"
							+ semesterId + "'", "notIn"));

			if (page != null) {
				return this.getStudents(page, searchProperties, null);
			} else {
				return this.getStudents(searchProperties, null);
			}
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	public List getRegStudentIds(String semesterId) throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
			return oracleBasicUserList.getRegStudentIds(semesterId);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	public String getNextRegNo(String reg_no) throws PlatformException {
		if (basicManagePriv.addStudent == 1) {
			OracleStudent student = new OracleStudent();
			return student.getNextRegNo(reg_no);
		} else {
			throw new PlatformException("您没有添加学生的权限！");
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
				searchProperties.add(new SearchProperty("login_id", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "like"));
			}
			if (teacher_level != null && !teacher_level.equals("")
					&& !teacher_level.equals("null")) {
				searchProperties.add(new SearchProperty("teach_level",
						teacher_level, "="));
			}
//			searchProperties.add(new SearchProperty("type", "0", "="));
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getTeachersNum(searchProperties);

		} else {
			throw new PlatformException("您没有浏览教师信息的权限！");
		}
	}

	public int getTeachersNum(String name, String cardno, String gender,
			String teacher_level, String dep_id) throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			List searchProperties = new ArrayList();

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("login_id", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if (teacher_level != null && !teacher_level.equals("")
					&& !teacher_level.equals("null")) {
				searchProperties.add(new SearchProperty("teach_level",
						teacher_level, "="));
			}
			if (dep_id != null && !dep_id.equals("") && !dep_id.equals("null")) {
				searchProperties.add(new SearchProperty("dep_id", dep_id, "="));
			}
//			searchProperties.add(new SearchProperty("type", "0", "="));
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getTeachersNum(searchProperties);

		} else {
			throw new PlatformException("您没有浏览教师信息的权限！");
		}
	}

	public List getTeachers(Page page, String name, String cardno,
			String gender, String teacher_level) throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			List searchProperties = new ArrayList();

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("login_id", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if (teacher_level != null && !teacher_level.equals("")
					&& !teacher_level.equals("null")) {
				searchProperties.add(new SearchProperty("teach_level",
						teacher_level, "="));
			}
//			searchProperties.add(new SearchProperty("type", "0", "="));
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getTeachers(page, searchProperties, null);

		} else {
			throw new PlatformException("您没有浏览教师信息的权限！");
		}
	}

	public List getTeachers(Page page, String name, String cardno,
			String gender, String teacher_level, String dep_id)
			throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			List searchProperties = new ArrayList();

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("login_id", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if (teacher_level != null && !teacher_level.equals("")
					&& !teacher_level.equals("null")) {
				searchProperties.add(new SearchProperty("teach_level",
						teacher_level, "="));
			}
			if (dep_id != null && !dep_id.equals("") && !dep_id.equals("null")) {
				searchProperties.add(new SearchProperty("dep_id", dep_id, "="));
			}
//			searchProperties.add(new SearchProperty("type", "0", "="));
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getTeachers(page, searchProperties, null);

		} else {
			throw new PlatformException("您没有浏览教师信息的权限！");
		}
	}

	public void regNewStudentBatch(String[] studentId, String gradeId)
			throws PlatformException {
		if (basicManagePriv.registerBatch == 1) {
			OracleUserBatchActivity batch = new OracleUserBatchActivity();
			batch.newStudentRegBatch(studentId, gradeId);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$regNewStudentBatch</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有注册新生的权限");
		}
	}

	public String[] checkExistRegIdCard(String[] studentId, String gradeId)
			throws PlatformException {
		if (basicManagePriv.registerBatch == 1) {
			OracleUserBatchActivity batch = new OracleUserBatchActivity();
			return batch.checkExistRegIdCard(studentId, gradeId);
		} else {
			throw new PlatformException("您没有注册新生的权限");
		}
	}

	public void unRegNewStudentBatch(String[] studentId)
			throws PlatformException {
		if (basicManagePriv.registerBatch == 1) {
			OracleUserBatchActivity batch = new OracleUserBatchActivity();
			batch.newStudentUnRegBatch(studentId);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$unRegNewStudentBatch</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有取消注册新生的权限");
		}
	}

	public int updateManagerPwd(String sso_id, String old_pwd, String new_pwd)
			throws PlatformException {
		if (this.basicManagePriv.updatePwd != 1) {
			throw new PlatformException("您没有修改密码的权限!");
		} else {
			OracleSsoUser ssoUser = new OracleSsoUser(sso_id);
			OracleSsoUserAuthorization ssoUserAuthor = new OracleSsoUserAuthorization(
					ssoUser.getLoginId());

			if (old_pwd.equals(ssoUserAuthor.getRightPwd())) {
				ssoUser.setPassword(new_pwd);
				int suc = ssoUser.update();
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.basicManagePriv.getSso_id()
										+ "</whaty><whaty>BEHAVIOR$|$updateManagerPwd</whaty><whaty>STATUS$|$"
										+ suc
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.MANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
				if (suc > 0) {
					SsoLog.setDebug("管理员" + sso_id + " 修改密码成功!");
					return 1;
				} else {
					throw new PlatformException("修改密码失败!");
				}
			} else {
				throw new PlatformException("原密码错误!");
			}
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|updateStudentPassword</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改学生密码的权限");
		}
	}

	public List getSiteTeachers(Page page, String name, String cardno,
			String gender, String isConfirm, String siteId)
			throws PlatformException {
		if (basicManagePriv.getSiteTeacher == 1) {
			List searchProperties = new ArrayList();

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("login_id", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if ("0".equals(isConfirm) || "1".equals(isConfirm)
					|| "2".equals(isConfirm))
				searchProperties.add(new SearchProperty("isConfirm", isConfirm,
						"="));
			if (searchProperties == null)
				searchProperties = new ArrayList();
			if (siteId != null && !siteId.equals("null") && !siteId.equals(""))
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			List order = new ArrayList();
			order.add(new OrderProperty("site_id", OrderProperty.ASC));
			return basicdatalist.getSiteTeachers(page, searchProperties, order);

		} else {
			throw new PlatformException("您没有浏览辅导教师信息的权限！");
		}
	}

	public int getSiteTeachersNum(String name, String cardno, String gender,
			String isConfirm, String siteId) throws PlatformException {
		if (basicManagePriv.getSiteTeacher == 1) {
			List searchProperties = new ArrayList();

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("login_id", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if ("0".equals(isConfirm) || "1".equals(isConfirm)
					|| "2".equals(isConfirm))
				searchProperties.add(new SearchProperty("isConfirm", isConfirm,
						"="));
			if (searchProperties == null)
				searchProperties = new ArrayList();
			if (siteId != null && !siteId.equals("null") && !siteId.equals(""))
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getSiteTeachersNum(searchProperties);

		} else {
			throw new PlatformException("您没有浏览辅导教师信息的权限！");
		}
	}

	public void confirmSiteTeachers(String[] selectedIds, String[] notes)
			throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		basicdatalist.confirmSiteTeachers(selectedIds, notes);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$confirmSiteTeachers</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public void confirmSiteTeachers(String[] selectedIds)
			throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		basicdatalist.confirmSiteTeachers(selectedIds);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$confirmSiteTeachers</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public void rejectSiteTeachers(String[] selectedIds, String[] notes)
			throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		basicdatalist.rejectSiteTeachers(selectedIds, notes);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$rejectSiteTeachers</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public void deleteSiteTeachers(String[] selectedIds)
			throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		basicdatalist.deleteSiteTeachers(selectedIds);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$deleteSiteTeachers</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public SiteTeacher getSiteTeacher(String id) throws PlatformException {
		if (this.basicManagePriv.getSiteTeacher == 1)
			return new OracleSiteTeacher(id);
		else
			throw new PlatformException("你没有浏览辅导教师的权限!");
	}

	public List getRegStat() throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getRegStat();
	}

	public HashMap getStudentStatByEduType(String siteId)
			throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			OracleBasicUserList basicUserList = new OracleBasicUserList();
			return basicUserList.getStudentStatByEduType(siteId, "");
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	public HashMap getStudentStatByEduType(String siteId, String gradeId)
			throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			OracleBasicUserList basicUserList = new OracleBasicUserList();
			return basicUserList.getStudentStatByEduType(siteId, gradeId);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	public HashMap getStudentStatByGrade(String siteId, String gradeId)
			throws PlatformException {
		if (basicManagePriv.getStudent == 1) {
			OracleBasicUserList basicUserList = new OracleBasicUserList();
			return basicUserList.getStudentStatByGrade(siteId, gradeId);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	// 上传学生照片
	public int uploadImage(String card_no, String filename)
			throws PlatformException, NoRightException {
		if (basicManagePriv.uploadImage == 1) {
			OracleStudent student = new OracleStudent();
			int suc = student.uploadImage(card_no, filename);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$uploadImage</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;

		} else {
			throw new NoRightException("您没有上传照片的权限！");
		}

	}

	public int uploadBatchImage(String card_no, String filename)
			throws PlatformException, NoRightException {
		if (basicManagePriv.uploadImage == 1) {
			OracleStudent student = new OracleStudent();
			int suc = student.uploadImage(card_no, filename);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$uploadBatchImage</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("您没有批量上传照片的权限！");
		}

	}

	public List getTeacherList(Page page, String name, String cardno,
			String gender) throws PlatformException {
		List searchProperties = new ArrayList();

		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", cardno, "like"));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "="));
		}
		searchProperties.add(new SearchProperty("type", "1", "="));
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		List order = new ArrayList();
		return basicdatalist.getTeachers(page, searchProperties, order);
	}

	public int getTeacherListNum(String name, String cardno, String gender)
			throws PlatformException {
		List searchProperties = new ArrayList();

		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", cardno, "like"));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "="));
		}
		searchProperties.add(new SearchProperty("type", "1", "="));
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getTeachersNum(searchProperties);
	}

	public List getSiteTeachers(Page page, String name, String cardno,
			String gender, String type, String isConfirm, String siteId,
			String pici) throws PlatformException {
		List searchProperties = new ArrayList();

		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", cardno, "like"));
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
		List order = new ArrayList();
		order.add(new OrderProperty("site_id", OrderProperty.ASC));
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getSiteTeachers(page, searchProperties, order);
	}
	
	public List getSiteTeachers2(Page page, String name, String cardno,
			String gender, String type, String isConfirm, String siteId,
			String pici) throws PlatformException {
		List searchProperties = new ArrayList();

		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", cardno, "like"));
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
		List order = new ArrayList();
		order.add(new OrderProperty("site_id", OrderProperty.ASC));
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getSiteTeachers2(page, searchProperties, order);
	}

	/**
	 * 得到已指定学生的分站教师
	 * 
	 */
	public List getAppointStudentSiteTeachers(Page page, String name,
			String cardno, String gender, String type, String isConfirm,
			String siteId, String pici,String semesterId) throws PlatformException {
		List searchProperties = new ArrayList();

		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("f.name", name, "like"));
		}
		if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
			searchProperties.add(new SearchProperty("f.login_id", cardno, "like"));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("f.gender", gender, "="));
		}
		if (isConfirm != null && !isConfirm.equals("")) {
			searchProperties.add(new SearchProperty("f.isConfirm", isConfirm,
					"="));
		}
		if (semesterId != null && !semesterId.equals("")) {
			searchProperties.add(new SearchProperty("f.semester_id", semesterId,
			"="));
		}
		if (siteId != null && !siteId.equals(""))
			searchProperties.add(new SearchProperty("f.site_id", siteId, "="));
		if (searchProperties == null)
			searchProperties = new ArrayList();
		searchProperties.add(new SearchProperty("f.type", type, "="));
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getAppointStudentSiteTeachers(page,
				searchProperties, null);
	}

	public List getAppointStudentSiteTeachers2(Page page, String name,
			String cardno, String gender, String type, String isConfirm,
			String siteId, String pici,String semesterId) throws PlatformException {
		List searchProperties = new ArrayList();

		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", cardno, "like"));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "="));
		}
		if (isConfirm != null && !isConfirm.equals(""))
			searchProperties.add(new SearchProperty("isConfirm", isConfirm,
					"="));
		if (siteId != null && !siteId.equals(""))
			searchProperties.add(new SearchProperty("site_id", siteId,
					"="));
		if (type != null && !type.equals(""))
			searchProperties.add(new SearchProperty("type", type,
					"="));
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getAppointStudentSiteTeachers2(page,
				searchProperties, null);
	}

	public int getSiteTeachersNum(String name, String cardno, String gender,
			String type, String isConfirm, String siteId, String pici)
			throws PlatformException {
		List searchProperties = new ArrayList();

		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", cardno, "like"));
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
		return basicdatalist.getSiteTeachersNum(searchProperties);
	}

	/**
	 * 得到已指定学生的分站教师的数量.
	 */
	public int getAppointStudentSiteTeachersNum(String name, String cardno,
			String gender, String type, String isConfirm, String siteId,
			String pici,String semesterId) throws PlatformException {
		List searchProperties = new ArrayList();

		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("f.name", name, "like"));
		}
		if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
			searchProperties.add(new SearchProperty("f.login_id", cardno, "like"));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("f.gender", gender, "="));
		}
		if (isConfirm != null && !isConfirm.equals("")) {
			searchProperties.add(new SearchProperty("f.isConfirm", isConfirm,
					"="));
		}
		if (semesterId != null && !semesterId.equals("")) {
			searchProperties.add(new SearchProperty("f.semester_id", semesterId,
			"="));
		}
		if (siteId != null && !siteId.equals(""))
			searchProperties.add(new SearchProperty("f.site_id", siteId, "="));
		if (searchProperties == null)
			searchProperties = new ArrayList();
		searchProperties.add(new SearchProperty("f.type", type, "="));
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getAppointStudentSiteTeachersNum(searchProperties);
	}
	public int getAppointStudentSiteTeachersNum2(String name, String cardno,
			String gender, String type, String isConfirm, String siteId,
			String pici,String semesterId) throws PlatformException {

		List searchProperties = new ArrayList();

		if (name != null && !name.equals("") && !name.equals("null")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
			searchProperties.add(new SearchProperty("login_id", cardno, "like"));
		}
		if (gender != null && !gender.equals("") && !gender.equals("null")) {
			searchProperties.add(new SearchProperty("gender", gender, "="));
		}
		if (isConfirm != null && !isConfirm.equals(""))
			searchProperties.add(new SearchProperty("isConfirm", isConfirm,
					"="));
		if (siteId != null && !siteId.equals(""))
			searchProperties.add(new SearchProperty("site_id", siteId,
					"="));
		if (type != null && !type.equals(""))
			searchProperties.add(new SearchProperty("type", type,
					"="));
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getAppointStudentSiteTeachersNum2(searchProperties);
	}

	public int isExistGradeEduTypeMajor(String teacherId)
			throws PlatformException {
		TeacherLimit teahcerLimit = new OracleTeacherLimit();
		return teahcerLimit.IsExsitGradeEduMajor(teacherId);
	}

	@Override
	public List getNoneExistStudents(Page page, String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk,
			String classId) throws PlatformException {
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
			searchProperties.add(new SearchProperty("u.site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("u.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("u.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("u.grade_id",
					basicManagePriv.getGradeList(), "in"));

			searchProperties.add(new SearchProperty("u.id",
					new OracleClassMember().getIsExistMemberId(
							ClassMember.STUDENT_MEMBER, classId), "notIn"));

			List order = new ArrayList();
			order.add(new OrderProperty("reg_no", OrderProperty.ASC));
			if (page != null) {
				return this.getStudents(page, searchProperties, order);
			} else {
				return this.getStudents(searchProperties, order);
			}
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	@Override
	public int getNoneExistStudentsNum(String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk,
			String classId) throws PlatformException {
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
			searchProperties.add(new SearchProperty("u.site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("u.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("u.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("u.grade_id",
					basicManagePriv.getGradeList(), "in"));

			searchProperties.add(new SearchProperty("u.id",
					new OracleClassMember().getIsExistMemberId(
							ClassMember.STUDENT_MEMBER, classId), "notIn"));
			return this.getStudentsNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览学员信息的权限！");
		}
	}

	@Override
	public List getNoneExistTeachers(Page page, String name, String cardno,
			String gender, String teacher_level, String classId)
			throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			List searchProperties = new ArrayList();

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("login_id", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if (teacher_level != null && !teacher_level.equals("")
					&& !teacher_level.equals("null")) {
				searchProperties.add(new SearchProperty("teach_level",
						teacher_level, "="));
			}
			searchProperties.add(new SearchProperty("type", "0", "="));

			searchProperties.add(new SearchProperty("id",
					new OracleClassMember().getIsExistMemberId(
							ClassMember.TEACHER_MEMBER, classId), "notIn"));
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getTeachers(page, searchProperties, null);

		} else {
			throw new PlatformException("您没有浏览教师信息的权限！");
		}
	}

	@Override
	public int getNoneExistTeachersNum(String name, String cardno,
			String gender, String teacher_level, String classId)
			throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			List searchProperties = new ArrayList();

			if (name != null && !name.equals("") && !name.equals("null")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardno != null && !cardno.equals("") && !cardno.equals("null")) {
				searchProperties.add(new SearchProperty("login_id", cardno, "like"));
			}
			if (gender != null && !gender.equals("") && !gender.equals("null")) {
				searchProperties.add(new SearchProperty("gender", gender, "="));
			}
			if (teacher_level != null && !teacher_level.equals("")
					&& !teacher_level.equals("null")) {
				searchProperties.add(new SearchProperty("teach_level",
						teacher_level, "="));
			}
			searchProperties.add(new SearchProperty("type", "0", "="));
			searchProperties.add(new SearchProperty("id",
					new OracleClassMember().getIsExistMemberId(
							ClassMember.TEACHER_MEMBER, classId), "notIn"));
			OracleBasicUserList basicdatalist = new OracleBasicUserList();
			return basicdatalist.getTeachersNum(searchProperties);

		} else {
			throw new PlatformException("您没有浏览教师信息的权限！");
		}
	}

	@Override
	public int getNoneExistManagerNum(String id, String login_id, String name,
			String classId) throws PlatformException {
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

		searchProperties.add(new SearchProperty("id", new OracleClassMember()
				.getIsExistMemberId(ClassMember.MANAGER_MEMBER, classId),
				"notIn"));

		return this.getManagerNum(searchProperties);
	}

	@Override
	public List getNoneExistManagers(Page page, String id, String login_id,
			String name, String classId) throws PlatformException {
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
			searchProperties.add(new SearchProperty("name", name, "like"));
		}

		searchProperties.add(new SearchProperty("id", new OracleClassMember()
				.getIsExistMemberId(ClassMember.MANAGER_MEMBER, classId),
				"notIn"));
		if (page != null) {
			return this.getManagers(page, searchProperties, orderProperties);
		} else {
			return this.getManagers(searchProperties, orderProperties);
		}
	}

	@Override
	public int getNoneExistSiteManagerNum(String id, String login_id,
			String name, String site_id, String classId)
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
		searchProperties.add(new SearchProperty("id", new OracleClassMember()
				.getIsExistMemberId(ClassMember.SITEMANAGER_MEMBER, classId),
				"notIn"));

		return this.getSiteManagerNum(searchProperties);
	}

	@Override
	public List getNoneExistSiteManagers(Page page, String id, String login_id,
			String name, String site_id, String classId)
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
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (site_id != null && !site_id.equals("") && !site_id.equals("null")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		searchProperties.add(new SearchProperty("id", new OracleClassMember()
				.getIsExistMemberId(ClassMember.SITEMANAGER_MEMBER, classId),
				"notIn"));

		orderProperties.add(new OrderProperty("login_id"));
		if (page != null) {
			return this
					.getSiteManagers(page, searchProperties, orderProperties);
		} else {
			return this.getSiteManagers(searchProperties, orderProperties);
		}
	}

	public Map getTeacherPTStat(Page page) throws PlatformException {
		OracleBasicUserList basicdatalist = new OracleBasicUserList();
		return basicdatalist.getTeacherStat(page);
	}

}
