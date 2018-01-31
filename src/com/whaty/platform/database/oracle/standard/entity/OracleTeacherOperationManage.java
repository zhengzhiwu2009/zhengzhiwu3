/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.CoursewareFactory;
import com.whaty.platform.courseware.CoursewareManage;
import com.whaty.platform.courseware.CoursewareManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleBasicActivityList;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEduList;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEntityList;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourseItem;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleBasicGraduateList;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleDiscourse;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleFreeApply;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleGraduateExecPici;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleGraduatePici;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleMetaphaseCheck;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleRegBgCourse;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleRejoinRequisition;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleSubjectQuestionary;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacher;
import com.whaty.platform.database.oracle.standard.info.OracleInfoList;
import com.whaty.platform.database.oracle.standard.interaction.OracleInteractionUserPriv;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUser;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUserAuthorization;
import com.whaty.platform.entity.BasicActivityManage;
import com.whaty.platform.entity.BasicScoreManage;
import com.whaty.platform.entity.BasicUserManage;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.TeacherOperationManage;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.graduatedesign.BasicGraduateList;
import com.whaty.platform.entity.graduatedesign.Discourse;
import com.whaty.platform.entity.graduatedesign.FreeApply;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.entity.graduatedesign.RejoinRequisition;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.entity.user.TeacherEduInfo;
import com.whaty.platform.entity.user.TeacherPriv;
import com.whaty.platform.info.News;
import com.whaty.platform.interaction.InteractionFactory;
import com.whaty.platform.interaction.InteractionManage;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.sms.SmsFactory;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.sms.SmsManagerPriv;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.RedundanceData;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.SsoLog;
import com.whaty.platform.util.log.UserLog;

/**
 * @author chenjian
 * 
 */
public class OracleTeacherOperationManage extends TeacherOperationManage {

	public OracleTeacherOperationManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleTeacherOperationManage(TeacherPriv teacherPriv) {
		this.setTeacherPriv(teacherPriv);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.TeacherOperationManage#getTeachClasses()
	 */
	public List getTeachClasses() throws PlatformException {
		if (this.getTeacherPriv().getTeachClasses != 1) {
			throw new PlatformException("no right to getTeachClasses!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			OracleTeacher teacher = new OracleTeacher();
			teacher.setId(this.getTeacherPriv().getTeacherId());
			
			List orderProperty = new ArrayList();
			
			orderProperty.add(new OrderProperty("to_number(semester_id)",OrderProperty.DESC));
			orderProperty.add(new OrderProperty("to_number(course_id)",OrderProperty.DESC));
			return basicActivityList
					.searchTeachClass(null, null, orderProperty, teacher);
		}
	}

	public List getTeachClasses(Page page) throws PlatformException {
		// TODO 自动生成方法存根
		if (this.getTeacherPriv().getTeachClasses != 1) {
			throw new PlatformException("no right to getTeachClasses!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			OracleTeacher teacher = new OracleTeacher(this.getTeacherPriv()
					.getTeacherId());
			return basicActivityList
					.searchTeachClass(page, null, null, teacher);
		}
	}

	public int getTeachClassesNum() throws PlatformException {
		// TODO 自动生成方法存根
		if (this.getTeacherPriv().getTeachClasses != 1) {
			throw new PlatformException("no right to getTeachClasses!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			OracleTeacher teacher = new OracleTeacher(this.getTeacherPriv()
					.getTeacherId());
			return basicActivityList.searchTeachClassNum(null, teacher);
		}
	}

	public Teacher getTeacher() throws PlatformException {
		// TODO 自动生成方法存根
		if (this.getTeacherPriv().getTeacher != 1) {
			throw new PlatformException("no right to getTeacher!");
		} else {
			return new OracleTeacher(this.getTeacherPriv().getTeacherId());
		}
	}

	public List getInfoList(Page page) throws PlatformException {
		// TODO 自动生成方法存根
		if (this.getTeacherPriv().getTeacher != 1) {
			throw new PlatformException("no right to getTeacher!");
		} else {
			OracleInfoList infoList = new OracleInfoList();
			Teacher teacher = this.getTeacher();

			return infoList.getNewsListForTeacher(page, teacher);
		}
	}

	public int getInfoListNum() throws PlatformException {
		// TODO 自动生成方法存根
		if (this.getTeacherPriv().getTeacher != 1) {
			throw new PlatformException("no right to getTeacher!");
		} else {
			OracleInfoList infoList = new OracleInfoList();
			Teacher teacher = this.getTeacher();

			return infoList.getNewsListNumForTeacher(teacher);
		}
	}

	// Write by Wulijun
	public int updateTeacher(String teacherId, String name, String password,
			String email, HumanNormalInfo normalInfo,
			TeacherEduInfo teachereduInfo, HumanPlatformInfo platformInfo,
			RedundanceData redundace) throws PlatformException {
		if (this.getTeacherPriv().updateTeacher == 1) {
			OracleTeacher oracleteacher = new OracleTeacher();
			oracleteacher.setEmail(email);
			oracleteacher.setId(teacherId);
			oracleteacher.setName(name);
			oracleteacher.setPassword(password);
			oracleteacher.setNormalInfo(normalInfo);
			oracleteacher.setTeacherInfo(teachereduInfo);
			oracleteacher.setPlatformInfo(platformInfo);
			oracleteacher.setRedundace(redundace);
			int sub = oracleteacher.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getManagerPriv().getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateTeacher</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.TEACHER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new PlatformException("您没有修改教师信息的权限");
		}
	}

	public int updatePwd(String id, String oldPassword, String newPassword)
			throws PlatformException {
		if (this.getTeacherPriv().updatePwd != 1) {
			throw new PlatformException("no right to updatePwd!");
		} else {
			OracleSsoUser ssoUser = new OracleSsoUser(id);
			OracleSsoUserAuthorization ssoUserAuthor = new OracleSsoUserAuthorization(
					ssoUser.getLoginId());

			if (oldPassword.equals(ssoUserAuthor.getRightPwd())) {
				ssoUser.setPassword(newPassword);
				int sub = ssoUser.update();
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.getManagerPriv().getSso_id()
										+ "</whaty><whaty>BEHAVIOR$|$updatePwd</whaty><whaty>STATUS$|$"
										+ sub
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.TEACHER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
				if (sub > 0) {
					SsoLog.setDebug("updatePwd success!" + id);
					return 1;
				} else {
					throw new PlatformException("updatePwd error!");
				}
			} else {
				throw new PlatformException(
						"updatePwd error because false oldpassword!");
			}
		}
	}

	public News getNews(String news_id) throws PlatformException {
		// TODO 自动生成方法存根
		if (this.getTeacherPriv().getAnswer != 1) {
			throw new PlatformException("no right to getNews!");
		} else {
			OracleInfoList infoList = new OracleInfoList();
			List searchPropertys = new ArrayList();
			searchPropertys.add(new SearchProperty("id", news_id, "="));

			return (News) infoList.getNewsList(null, searchPropertys, null)
					.get(0);
		}
	}

	public List getCourses(Page page, String id) throws PlatformException {
		// TODO 自动生成方法存根
		if (this.getTeacherPriv().getCourses != 1) {
			throw new PlatformException("没有读取课程的权限!");
		} else {
			OracleBasicEduList basicEduList = new OracleBasicEduList();
			List searchproperty = new ArrayList();
			List orderproperty = new ArrayList();
			if (null != id && !id.equals("") && !id.equals("null")) {
				searchproperty.add(new SearchProperty("id", id, "="));
				orderproperty.add(new OrderProperty("id"));
			}
			return basicEduList.getCourses(page, searchproperty, orderproperty);
		}
	}

	public InteractionUserPriv getInteractionUserPriv(String user_id)
			throws PlatformException {
		if (this.getTeacherPriv().addCourseware != 1) {
			throw new PlatformException("没有进入课程交互的权限！");
		} else {
			OracleInteractionUserPriv interactionUserPriv = new OracleInteractionUserPriv(
					user_id);
			return interactionUserPriv;
		}
	}

	public InteractionUserPriv getInteractionUserPriv(String user_id,
			String teachclassId) throws PlatformException {
		if (this.getTeacherPriv().addCourseware != 1) {
			throw new PlatformException("没有进入课程交互的权限！");
		} else {
			OracleInteractionUserPriv interactionUserPriv = new OracleInteractionUserPriv(
					user_id);
			interactionUserPriv.setTeachclassId(teachclassId);
			return interactionUserPriv;
		}
	}

	public OpenCourse getOpenCourse(String opend_course_id)
			throws PlatformException {
		if (this.getTeacherPriv().getOpenCourse != 1) {
			throw new PlatformException("没有获取开课信息的权限！");
		} else {
			return new OracleOpenCourse(opend_course_id);
		}
	}

	public CoursewareManage creatCoursewareManage() {
		CoursewareFactory coursewareFactory = CoursewareFactory.getInstance();
		CoursewareManagerPriv priv = coursewareFactory
				.getCoursewareManagerPriv("");
		priv.setMessageId(this.getTeacherPriv().getTeacherId());
		priv.addChapterPage = 1;
		priv.addOnlineCwInfo = 1;
		priv.addWhatyCoursewareTemplate = 1;
		priv.buildNormalHttpCourseware = 1;
		priv.buildWhatyOnlineCourseware = 1;
		priv.buildWhatyUploadCourseware = 1;
		priv.deleteOnlineCwInfo = 1;
		priv.deleteWhatyCoursewareTemplate = 1;
		priv.filemanage = 1;
		priv.getCourseware = 1;
		priv.getOnlineCwInfo = 1;
		priv.getOnlineCwRootDir = 1;
		priv.getWhatyCoursewareTemplate = 1;
		priv.updateNormalHttpLink = 1;
		priv.updateUploadEnterFile = 1;
		priv.updateWhatyCoursewareTemplate = 1;
		return coursewareFactory.creatCoursewareManage(priv);
	}

	public CoursewareManagerPriv getCoursewareManagerPriv() {
		CoursewareFactory coursewareFactory = CoursewareFactory.getInstance();
		CoursewareManagerPriv priv = coursewareFactory
				.getCoursewareManagerPriv("");
		priv.setMessageId(this.getTeacherPriv().getTeacherId());
		priv.addChapterPage = 1;
		priv.addOnlineCwInfo = 1;
		priv.addWhatyCoursewareTemplate = 1;
		priv.buildNormalHttpCourseware = 1;
		priv.buildWhatyOnlineCourseware = 1;
		priv.buildWhatyUploadCourseware = 1;
		priv.deleteOnlineCwInfo = 1;
		priv.deleteWhatyCoursewareTemplate = 1;
		priv.filemanage = 1;
		priv.getCourseware = 1;
		priv.getOnlineCwInfo = 1;
		priv.getOnlineCwRootDir = 1;
		priv.getWhatyCoursewareTemplate = 1;
		priv.updateNormalHttpLink = 1;
		priv.updateUploadEnterFile = 1;
		priv.updateWhatyCoursewareTemplate = 1;
		return priv;
	}

	public InteractionManage createInteractionManage() throws PlatformException {
		InteractionFactory interactionFactory = InteractionFactory
				.getInstance();
		InteractionUserPriv userPriv = this.getInteractionUserPriv(this
				.getTeacherPriv().getTeacherId());
		InteractionManage interactionManage = interactionFactory
				.creatInteractionManage(userPriv);
		return interactionManage;
	}

	public BasicUserManage createBasicUserManage() {
		PlatformFactory platformfactory = PlatformFactory.getInstance();
		BasicUserManage basicUserManage = platformfactory
				.creatBasicUserManage(this.getManagerPriv());
		return basicUserManage;
	}

	public ManagerPriv getManagerPriv() {
		ManagerPriv managerPriv = new OracleManagerPriv();
		managerPriv.setSso_id(this.getTeacherPriv().getTeacherId());
		managerPriv.electiveStudent = 1;
		managerPriv.electiveBatchBySite = 1;
		managerPriv.electiveBatchByStudent = 1;
		managerPriv.electiveSingle = 1;
		managerPriv.addTeacher = 1;
		managerPriv.getSiteTeacher = 1;
		managerPriv.getTeacher = 1;
		managerPriv.appointTeacherForCourse = 1;
		managerPriv.deleteTeacher = 1;
		managerPriv.updateTeacher = 1;
		return managerPriv;
	}

	public SmsManagerPriv getSmsManagerPriv() {
		SmsFactory smsFactory = SmsFactory.getInstance();
		SmsManagerPriv smsManagerPriv = smsFactory.getSmsManagerPriv();
		smsManagerPriv.addSms = 1;
		smsManagerPriv.deleteSms = 1;
		smsManagerPriv.getSms = 1;
		smsManagerPriv.updateSms = 1;
		smsManagerPriv.getSms = 1;
		return smsManagerPriv;
	}

	public SmsManage getSmsManage(SmsManagerPriv smsManagerPriv)
			throws PlatformException {
		SmsFactory smsFactory = SmsFactory.getInstance();
		return smsFactory.creatSmsManage(smsManagerPriv);
	}

	public List getTheachItem(String id) {
		List itemList = new ArrayList();
		OracleCourseItem teachItem = new OracleCourseItem();
		teachItem.setId(id);
		if (teachItem.isIdExist() > 0) {
			itemList = teachItem.getItemById(id);
		} else {
			try {
				teachItem.setDayi("1");
				teachItem.setGonggao("1");
				teachItem.setTaolun("1");
				teachItem.setKaoshi("1");
				teachItem.setZuoye("1");
				teachItem.setZiyuan("1");
				teachItem.setZfx("1");
				teachItem.setZice("1");
				teachItem.setZxdayi("1");
				teachItem.setBoke("1");
				teachItem.setDaohang("1");
				teachItem.setDaoxue("1");
				teachItem.setSmzuoye("1");
				teachItem.setPingjia("1");
				teachItem.setShiyan("1");

				teachItem.add();

				itemList = teachItem.getItemById(id);

			} catch (Exception e) {
				
			}
		}
		return itemList;
	}

	public int updateTeachItemStatus(String item, String status, String id)
			throws PlatformException {
		OracleCourseItem teachItem = new OracleCourseItem();

		int sub = teachItem.updateItemStatus(item, status, id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getManagerPriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateTeachItemStatus</whaty><whaty>STATUS$|$"
								+ sub
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.TEACHER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return sub;
	}

	public Pici getActiveGraduateDesignBatch() throws PlatformException {
		if(this.getTeacherPriv().getGraduateDesignPici == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("status", "1", "="));
			return basicdatalist.getGraduateDesignBatch(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计批次的权限");
		}
		
	}

	public Pici getGraduateDesignBatch(String aid) throws PlatformException {
		if(this.getTeacherPriv().getGraduateDesignPici == 1){
			OracleGraduatePici oracleGraduatePici = new OracleGraduatePici(aid);
			return oracleGraduatePici;
		}else{
			throw new PlatformException("您没有浏览毕业设计批次的权限");
		}
	
	}
	
	//-------------获得毕业大作业批次 lwx 2008-05-28
	public Pici getActiveGraduateExecDesignBatch() throws PlatformException {
		if(this.getTeacherPriv().getGraduateDesignPiciExec == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("status", "1", "="));
			return basicdatalist.getGraduateExecDesignBatch(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业大作业批次的权限");
		}
		
	}
	
	public Pici getGraduateExecDesignBatch(String aid) throws PlatformException {
		if(this.getTeacherPriv().getGraduateDesignPiciExec == 1){
			OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici(aid);
			return oracleGraduatePici;
		}else{
			throw new PlatformException("您没有浏览毕业大作业批次的权限");
		}
		
	}
	//-----------------end

	public  String getActiveMetaphaseCheckStudentIdString(String name ,String regNo , String siteId , String gradeId , String eduTypeId , String majorId) throws PlatformException {
		String str = "";
		BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
		List searchProperty = new ArrayList();
		if (regNo != null && !regNo.equals("")) {
			searchProperty.add(new SearchProperty("reg_no", regNo, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("name", name, "like"));
		}
		if (siteId != null && !siteId.equals("")) {
			searchProperty.add(new SearchProperty("site_id", siteId, "="));
		}
		if (gradeId != null && !gradeId.equals("")) {
			searchProperty.add(new SearchProperty("grade_id", gradeId, "="));
		}
		if (eduTypeId != null && !eduTypeId.equals("")) {
			searchProperty
					.add(new SearchProperty("edutype_id", eduTypeId, "="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperty.add(new SearchProperty("major_id", majorId, "="));
		}
		searchProperty.add(new SearchProperty("status", "3", "="));
		str = oracleBasicGraduateList.getActiveMetaphaseCheckStudentIdString(searchProperty);
		return str;
	}
	
	
	public  String getActiveDiscourseCheckStudentIdString(String name ,String regNo , String siteId , String gradeId , String eduTypeId , String majorId) throws PlatformException {
		String str = "";
		BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
		List searchProperty = new ArrayList();
		if (regNo != null && !regNo.equals("")) {
			searchProperty.add(new SearchProperty("reg_no", regNo, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("name", name, "like"));
		}
		if (siteId != null && !siteId.equals("")) {
			searchProperty.add(new SearchProperty("site_id", siteId, "="));
		}
		if (gradeId != null && !gradeId.equals("")) {
			searchProperty.add(new SearchProperty("grade_id", gradeId, "="));
		}
		if (eduTypeId != null && !eduTypeId.equals("")) {
			searchProperty
					.add(new SearchProperty("edutype_id", eduTypeId, "="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperty.add(new SearchProperty("major_id", majorId, "="));
		}
		searchProperty.add(new SearchProperty("status", "3", "="));
		str = oracleBasicGraduateList.getActiveDiscourseCheckStudentIdString(searchProperty);
		return str;
	}
	
	public  String getActiveSubjectSearchStudentIdString() throws PlatformException {
		String str = "";
		BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
		str = oracleBasicGraduateList.getActiveSubjectSearchStudentIdString();
		return str;
	}

	
	public  String getActiveRegBeginCourseStudentIdString(String name ,String regNo , String siteId , String gradeId , String eduTypeId , String majorId) throws PlatformException {
		String str = "";
		BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
		List searchProperty = new ArrayList();
		if (regNo != null && !regNo.equals("")) {
			searchProperty.add(new SearchProperty("reg_no", regNo, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("name", name, "like"));
		}
		if (siteId != null && !siteId.equals("")) {
			searchProperty.add(new SearchProperty("site_id", siteId, "="));
		}
		if (gradeId != null && !gradeId.equals("")) {
			searchProperty.add(new SearchProperty("grade_id", gradeId, "="));
		}
		if (eduTypeId != null && !eduTypeId.equals("")) {
			searchProperty
					.add(new SearchProperty("edutype_id", eduTypeId, "="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperty.add(new SearchProperty("major_id", majorId, "="));
		}
		searchProperty.add(new SearchProperty("status", "3", "="));
		str = oracleBasicGraduateList.getActiveRegBeginCourseStudentIdString(searchProperty);
		return str;
	}
	
	public List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String status) throws PlatformException {
		if(this.getTeacherPriv().getSubjectQuestionary == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (status != null && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getSubjectQuestionaryList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("您没有浏览毕业大作业的权限");
		}
		
	}

	public int getSubjectQuestionaryListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status) throws PlatformException {
		if(this.getTeacherPriv().getSubjectQuestionary == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (status != null && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			return oracleBasicGraduateList
					.getSubjectQuestionaryListNum(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业大作业的权限");
		}
		
	}

	public List getSubjectQuestionaryList2(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String status) throws PlatformException {
		if(this.getTeacherPriv().getSubjectQuestionary == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (status != null && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getSubjectQuestionaryList2(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("您没有浏览毕业大作业的权限");
		}
		
	}

	public int getSubjectQuestionaryListNum2(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status) throws PlatformException {
		if(this.getTeacherPriv().getSubjectQuestionary == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (status != null && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			return oracleBasicGraduateList
					.getSubjectQuestionaryListNum2(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业大作业的权限");
		}
	}

	
	public List getRegBeginCourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status,String semesterId) throws PlatformException {
		if(this.getTeacherPriv().getRegBeginCourse == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (status != null && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "notIn"));  //修改了＂＝＂条件，此处返回分站教师反馈过的记录
			}
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id", semesterId, "="));  
			}
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getRegBeginCourseList1(page,
					searchProperty, orderProperty,this.getTeacherPriv().getTeacherId());
		}else{
			throw new PlatformException("您没有浏览毕业设计开题报告的权限");
		}
		
	}

	public int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status,String semesterId) throws PlatformException {
		if(this.getTeacherPriv().getRegBeginCourse == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (status != null && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "notIn")); //修改了＂＝＂条件，此处返回分站教师反馈过的记录
			}
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id", semesterId, "="));  
			}
			return oracleBasicGraduateList.getRegBeginCourseListNum1(searchProperty,this.getTeacherPriv().getTeacherId());
		}else{
			throw new PlatformException("您没有浏览毕业设计开题报告的权限");
		}
	}

	public List getSiteTutorTeacherList(Page page, String teacherId,
			String name, String status) throws PlatformException {
		return null;
	}

	public int getSiteTutorTeacherListNum(String reg_no, String name,
			String status) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getMetaphaseCheckList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status) throws PlatformException {
		if(this.getTeacherPriv().getMetaphaseCheck == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (status != null && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getMetaphaseCheckList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计中期检查信息的权限");
		}
		
	}

	public int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status) throws PlatformException {
		if(this.getTeacherPriv().getMetaphaseCheck == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (status != null && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			return oracleBasicGraduateList.getMetaphaseCheckListNum(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计中期检查信息的权限");
		}
		
	}

	public List getRejoinRequisitionList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String status) throws PlatformException {
		if(this.getTeacherPriv().getRejoinRequisition == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (type != null && !type.equals("")) {
				searchProperty.add(new SearchProperty("requistiontype", type, "="));
			}
			if (status != null && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			return oracleBasicGraduateList.getRejoinRequesList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计答辩评审信息的权限");
		}
		
	}

	public int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String status) throws PlatformException {
		if(this.getTeacherPriv().getRejoinRequisition == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (type != null && !type.equals("")) {
				searchProperty.add(new SearchProperty("requistiontype", type, "="));
			}
			if (status != null && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			return oracleBasicGraduateList.getRejoinRequesListNum(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计答辩评审信息的权限");
		}
		
		
	}

	public List getDiscourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String status) throws PlatformException {
		if(this.getTeacherPriv().getDiscourse == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (type != null && !type.equals("")) {
				searchProperty
						.add(new SearchProperty("requisitiontype", type, "="));
			}
			if (status != null && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			return oracleBasicGraduateList.getDisCourseList(page, searchProperty,
					orderProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计终稿信息的权限");
		}
		
	}

	public int getDiscourseListNum(String reg_no, String name, String site,
			String grade, String eduTypeID, String majorId, String type,
			String status) throws PlatformException {
		if(this.getTeacherPriv().getDiscourse == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (type != null && !type.equals("")) {
				searchProperty
						.add(new SearchProperty("requisitiontype", type, "="));
			}
			if (status != null && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			return oracleBasicGraduateList.getDisCourseListNum(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计终稿信息的权限");
		}
	}

	public SubjectQuestionary getSubjectQuestionary(String id)
			throws PlatformException {
		if(this.getTeacherPriv().getSubjectQuestionary == 1){
			SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary(id);
			return subjectQuestionary;
		}else{
			throw new PlatformException("您没有浏览毕业大作业的权限");
		}
		
	}

	public RegBgCourse getRegBeginCourse(String id) throws PlatformException {
		if(this.getTeacherPriv().getRegBeginCourse == 1){
			RegBgCourse regBgCourse = new OracleRegBgCourse(id);
			return regBgCourse;
		}else{
			throw new PlatformException("您没有浏览毕业设计开题报告的权限");
		}
		
	}

	public SiteTeacher getSiteTutorTeacher(String id) throws PlatformException {
		if(this.getTeacherPriv().getSiteTutorTeacher == 1){
			SiteTeacher siteTeacher = new OracleSiteTeacher(id);
			return siteTeacher;
		}else{
			throw new PlatformException("您没有浏览分站老师的权限");
		}
	
	}

	public MetaphaseCheck getMetaphaseCheck(String id) throws PlatformException {
		if(this.getTeacherPriv().getMetaphaseCheck == 1){
			MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck(id);
			return metaphaseCheck;
		}else{
			throw new PlatformException("您没有浏览毕业设计中期检查信息的权限");
		}
		
	}

	public RejoinRequisition getRejoinRequisition(String id)
			throws PlatformException {
		if(this.getTeacherPriv().getRejoinRequisition == 1){
			RejoinRequisition rejoinRequisition = new OracleRejoinRequisition(id);
			return rejoinRequisition;
		}else{
			throw new PlatformException("您没有浏览毕业设计答辩评审信息的权限");
		}
		
	}

	public Discourse getDiscourse(String id) throws PlatformException {
		if(this.getTeacherPriv().getDiscourse == 1){
			Discourse discourse = new OracleDiscourse(id);
			return discourse;
		}else{
			throw new PlatformException("您没有浏览毕业设计终稿信息的权限");
		}
	
	}

	public int changeSubjectQuestionary(String id, String status, String message) {
		SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary();
		subjectQuestionary.setId(id);
		subjectQuestionary.setTeacher_note(message);
		int suc = 0;
		if (status.equals("PASS")) {
			suc = subjectQuestionary.teacherConfirm();
		} else if (status.equals("NOPASS")) {
			suc = subjectQuestionary.teacherReject();
		}
		return suc;
	}

	public int changeRegBeginCourse(String id, String status, String message) {
		RegBgCourse regBeginCourse = new OracleRegBgCourse();
		regBeginCourse.setId(id);
		regBeginCourse.setTeacher_note(message);
		int suc = 0;
		if (status.equals("PASS")) {
			suc = regBeginCourse.teacherConfirm();
		} else if (status.equals("NOPASS")) {
			suc = regBeginCourse.teacherReject();
		}
		return suc;
	}

	public int changeSiteTutorTeacher(String id, String status, String message) {
		// SiteTutorTeacher SiteTutorTeacher = new OracleSiteTutorTeacher();
		// TODO Auto-generated method stub
		return 0;
	}

	public int changeMetaphaseCheck(String id, String status, String message) {
		MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck();
		metaphaseCheck.setId(id);
		metaphaseCheck.setTeacher_note(message);
		int suc = 0;
		if (status.equals("PASS")) {
			suc = metaphaseCheck.teacherConfirm();
		} else if (status.equals("NOPASS")) {
			suc = metaphaseCheck.teacherReject();
		}
		return suc;
	}

	public int changeRejoinRequisition(String id, String status, String message) {
		RejoinRequisition rejoinRequisition = new OracleRejoinRequisition(id);
		rejoinRequisition.setTeacher_note(message);
		int suc = 0;
		if (status.equals("PASS")) {
			suc = rejoinRequisition.teacherConfirm();
		} else if (status.equals("NOPASS")) {
			suc = rejoinRequisition.teacherReject();
		}
		return suc;
	}

	public int changeDiscourse(String id, String status, String message) {
		Discourse discourse = new OracleDiscourse();
		discourse.setId(id);
		discourse.setTeacher_note(message);
		int suc = 0;
		if (status.equals("PASS")) {
			suc = discourse.teacherConfirm();
		} else if (status.equals("NOPASS")) {
			suc = discourse.teacherReject();
		}
		return suc;
	}

	public List getAllSites() throws PlatformException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		List OrderProperties = new ArrayList();
		OrderProperties.add(new OrderProperty("id"));
		return basicdatalist.getSites(null, null, OrderProperties);
	}

	public List getAllGrades() throws PlatformException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		List OrderProperties = new ArrayList();
		OrderProperties.add(new OrderProperty("name", OrderProperty.DESC));
		return basicdatalist.getGrades(null, null, OrderProperties);
	}

	public List getAllEduTypes() throws PlatformException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		List OrderProperties = new ArrayList();
		OrderProperties.add(new OrderProperty("id"));
		return basicdatalist.getEduTypes(null, null, OrderProperties);
	}

	public List getAllMajors() throws PlatformException {
		OracleBasicEntityList basicdatalist = new OracleBasicEntityList();
		List searchProperties = new ArrayList();
		searchProperties.add(new SearchProperty("m.id", "0", "<>"));
		List OrderProperties = new ArrayList();
		OrderProperties.add(new OrderProperty("id"));
		return basicdatalist.getMajors(null, searchProperties, OrderProperties);
	}

	public List getStudentsMajors(String siteTeacher_id) {
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
		return basicdatalist.getStudentMajors(siteTeacher_id);
	}

	public int getStudentFreeApplyNum(String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo) throws PlatformException {
		if(this.getTeacherPriv().getStudentFreeApply == 1){
			List searchProperties = new ArrayList();
			if(siteId != null || !"".equals(siteId) || !"null".equalsIgnoreCase(siteId))
				searchProperties.add(new SearchProperty("site_id",siteId,"="));
			if(edutypeId != null || !"".equals(edutypeId) || !"null".equalsIgnoreCase(edutypeId))
				searchProperties.add(new SearchProperty("edutype_id",edutypeId,"="));
			if(majorId != null || !"".equals(majorId) || !"null".equalsIgnoreCase(majorId))
				searchProperties.add(new SearchProperty("major_id",majorId,"="));
			if(gradeId != null || !"".equals(gradeId) || !"null".equalsIgnoreCase(gradeId))
				searchProperties.add(new SearchProperty("grade_id",gradeId,"="));
			if(status != null || !"".equals(status) || !"null".equalsIgnoreCase(status))
				searchProperties.add(new SearchProperty("status",status,"="));
			if(studentName != null || !"".equals(studentName) || !"null".equalsIgnoreCase(studentName))
				searchProperties.add(new SearchProperty("name",studentName,"like"));
			if(regNo != null || !"".equals(regNo) || !"null".equalsIgnoreCase(regNo))
				searchProperties.add(new SearchProperty("reg_no",regNo,"="));
			//BasicEntityList basicdatalist = new OracleBasicEntityList();
			Pici pici = getActiveGraduateDesignBatch();
			if(pici != null)
				searchProperties.add(new SearchProperty("pici_id",pici.getId(),"="));
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			return oracleBasicGraduateList.getStudentFreeApplyNum(searchProperties,null);
		}else{
			throw new PlatformException("您没有浏览毕业设计免做申请的权限");
		}
		
	}
	
	public int getStudentFreeApplyNum(String siteId, String edutypeId,
			String majorId, String gradeId, String status, String studentName,
			String regNo, String courseId) throws PlatformException {
		if (this.getTeacherPriv().getStudentFreeApply == 1) {
			List searchProperties = new ArrayList();
			if (courseId != null || !"".equals(courseId)
					|| !"null".equalsIgnoreCase(courseId))
				searchProperties.add(new SearchProperty("course_id", courseId,
						"="));
			if (siteId != null || !"".equals(siteId)
					|| !"null".equalsIgnoreCase(siteId))
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			if (edutypeId != null || !"".equals(edutypeId)
					|| !"null".equalsIgnoreCase(edutypeId))
				searchProperties.add(new SearchProperty("edutype_id",
						edutypeId, "="));
			if (majorId != null || !"".equals(majorId)
					|| !"null".equalsIgnoreCase(majorId))
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			if (gradeId != null || !"".equals(gradeId)
					|| !"null".equalsIgnoreCase(gradeId))
				searchProperties.add(new SearchProperty("grade_id", gradeId,
						"="));
			if (status != null || !"".equals(status)
					|| !"null".equalsIgnoreCase(status))
				searchProperties.add(new SearchProperty("status", status, "="));
			if (studentName != null || !"".equals(studentName)
					|| !"null".equalsIgnoreCase(studentName))
				searchProperties.add(new SearchProperty("name",
						studentName, "like"));
			if (regNo != null || !"".equals(regNo)
					|| !"null".equalsIgnoreCase(regNo))
				searchProperties.add(new SearchProperty("reg_no", regNo, "="));
			// BasicEntityList basicdatalist = new OracleBasicEntityList();
			Pici pici = getActiveGraduateDesignBatch();
			if (pici != null)
				searchProperties.add(new SearchProperty("pici_id",
						pici.getId(), "="));
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			return oracleBasicGraduateList.getStudentFreeApplyNum(
					searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览毕业设计免做申请的权限");
		}
	}
	
	public int getStudentFreeApplyNum(String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo,String courseId,String teacher_id,String semesterId) throws PlatformException {
		if (this.getTeacherPriv().getStudentFreeApply == 1) {
			List searchProperties = new ArrayList();
			if(courseId != null && !"".equals(courseId) && !"null".equalsIgnoreCase(courseId))
				searchProperties.add(new SearchProperty("course_id",courseId,"="));
			if(siteId != null && !"".equals(siteId) && !"null".equalsIgnoreCase(siteId))
				searchProperties.add(new SearchProperty("site_id",siteId,"="));
			if(edutypeId != null && !"".equals(edutypeId) && !"null".equalsIgnoreCase(edutypeId))
				searchProperties.add(new SearchProperty("edutype_id",edutypeId,"="));
			if(majorId != null && !"".equals(majorId) && !"null".equalsIgnoreCase(majorId))
				searchProperties.add(new SearchProperty("major_id",majorId,"="));
			if(gradeId != null && !"".equals(gradeId) && !"null".equalsIgnoreCase(gradeId))
				searchProperties.add(new SearchProperty("grade_id",gradeId,"="));
			if(status != null && !"".equals(status) && !"null".equalsIgnoreCase(status))
				searchProperties.add(new SearchProperty("status",status,"="));
			if(studentName != null && !"".equals(studentName) && !"null".equalsIgnoreCase(studentName))
				searchProperties.add(new SearchProperty("name",studentName,"like"));
			if(regNo != null && !"".equals(regNo) && !"null".equalsIgnoreCase(regNo))
				searchProperties.add(new SearchProperty("reg_no",regNo,"="));
			if(teacher_id!=null&&!"".equals(teacher_id)&&!"null".equalsIgnoreCase(teacher_id)){
				searchProperties.add(new SearchProperty("teacher_id",teacher_id));
			}
			if(semesterId!=null &&	!"".equals(semesterId)	&& 	!"null".equalsIgnoreCase(semesterId)){
				searchProperties.add(new SearchProperty("semester_id",semesterId));
			}
			//BasicEntityList basicdatalist = new OracleBasicEntityList();
			Pici pici = getActiveGraduateDesignBatch();
			if(pici != null)
				searchProperties.add(new SearchProperty("pici_id",pici.getId(),"="));
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			return oracleBasicGraduateList.getStudentFreeApplyTutorNum(searchProperties,null);
		} else {
			throw new PlatformException("您没有浏览毕业设计免做申请的权限");
		}
	}
	
	public List getStudentFreeApply(Page page,String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo) throws PlatformException {
		if (this.getTeacherPriv().getStudentFreeApply == 1) {
			List searchProperties = new ArrayList();
			if(siteId != null || !"".equals(siteId) || !"null".equalsIgnoreCase(siteId))
				searchProperties.add(new SearchProperty("site_id",siteId,"="));
			if(edutypeId != null || !"".equals(edutypeId) || !"null".equalsIgnoreCase(edutypeId))
				searchProperties.add(new SearchProperty("edutype_id",edutypeId,"="));
			if(majorId != null || !"".equals(majorId) || !"null".equalsIgnoreCase(majorId))
				searchProperties.add(new SearchProperty("major_id",majorId,"="));
			if(gradeId != null || !"".equals(gradeId) || !"null".equalsIgnoreCase(gradeId))
				searchProperties.add(new SearchProperty("grade_id",gradeId,"="));
			if(status != null || !"".equals(status) || !"null".equalsIgnoreCase(status))
				searchProperties.add(new SearchProperty("status",status,"="));
			if(studentName != null || !"".equals(studentName) || !"null".equalsIgnoreCase(studentName))
				searchProperties.add(new SearchProperty("name",studentName,"like"));
			if(regNo != null || !"".equals(regNo) || !"null".equalsIgnoreCase(regNo))
				searchProperties.add(new SearchProperty("reg_no",regNo,"="));
			//BasicEntityList basicdatalist = new OracleBasicEntityList();
			
			Pici pici = getActiveGraduateDesignBatch();
			if(pici != null)
				searchProperties.add(new SearchProperty("pici_id",pici.getId(),"="));
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			return oracleBasicGraduateList.getStudentFreeApply(page,searchProperties,null);
		} else {
			throw new PlatformException("您没有浏览毕业设计免做申请的权限");
		}
	}
	
	public List getStudentFreeApply(Page page,String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo,String courseId) throws PlatformException {
		if (this.getTeacherPriv().getStudentFreeApply == 1) {
			List searchProperties = new ArrayList();
			if(courseId != null || !"".equals(courseId) || !"null".equalsIgnoreCase(courseId))
				searchProperties.add(new SearchProperty("course_id",courseId,"="));
			if(siteId != null || !"".equals(siteId) || !"null".equalsIgnoreCase(siteId))
				searchProperties.add(new SearchProperty("site_id",siteId,"="));
			if(edutypeId != null || !"".equals(edutypeId) || !"null".equalsIgnoreCase(edutypeId))
				searchProperties.add(new SearchProperty("edutype_id",edutypeId,"="));
			if(majorId != null || !"".equals(majorId) || !"null".equalsIgnoreCase(majorId))
				searchProperties.add(new SearchProperty("major_id",majorId,"="));
			if(gradeId != null || !"".equals(gradeId) || !"null".equalsIgnoreCase(gradeId))
				searchProperties.add(new SearchProperty("grade_id",gradeId,"="));
			if(status != null || !"".equals(status) || !"null".equalsIgnoreCase(status))
				searchProperties.add(new SearchProperty("status",status,"="));
			if(studentName != null || !"".equals(studentName) || !"null".equalsIgnoreCase(studentName))
				searchProperties.add(new SearchProperty("name",studentName,"like"));
			if(regNo != null || !"".equals(regNo) || !"null".equalsIgnoreCase(regNo))
				searchProperties.add(new SearchProperty("reg_no",regNo,"="));
			Pici pici = getActiveGraduateDesignBatch();
			if(pici != null)
				searchProperties.add(new SearchProperty("pici_id",pici.getId(),"="));
			//BasicEntityList basicdatalist = new OracleBasicEntityList();
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			return oracleBasicGraduateList.getStudentFreeApply(page,searchProperties,null);
		} else {
			throw new PlatformException("您没有浏览毕业设计免做申请的权限");
		}
	}
	
	public List getStudentFreeApply(Page page,String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo,String courseId,String teacher_id,String semesterId) throws PlatformException {
		if (this.getTeacherPriv().getStudentFreeApply == 1) {
			List searchProperties = new ArrayList();
			if(courseId != null && !"".equals(courseId) && !"null".equalsIgnoreCase(courseId))
				searchProperties.add(new SearchProperty("course_id",courseId,"="));
			if(siteId != null && !"".equals(siteId) && !"null".equalsIgnoreCase(siteId))
				searchProperties.add(new SearchProperty("site_id",siteId,"="));
			if(edutypeId != null && !"".equals(edutypeId) && !"null".equalsIgnoreCase(edutypeId))
				searchProperties.add(new SearchProperty("edutype_id",edutypeId,"="));
			if(majorId != null && !"".equals(majorId) && !"null".equalsIgnoreCase(majorId))
				searchProperties.add(new SearchProperty("major_id",majorId,"="));
			if(gradeId != null && !"".equals(gradeId) && !"null".equalsIgnoreCase(gradeId))
				searchProperties.add(new SearchProperty("grade_id",gradeId,"="));
			if(status != null && !"".equals(status) && !"null".equalsIgnoreCase(status))
				searchProperties.add(new SearchProperty("status",status,"="));
			if(studentName != null && !"".equals(studentName) && !"null".equalsIgnoreCase(studentName))
				searchProperties.add(new SearchProperty("name",studentName,"like"));
			if(regNo != null && !"".equals(regNo) && !"null".equalsIgnoreCase(regNo))
				searchProperties.add(new SearchProperty("reg_no",regNo,"="));
			if(teacher_id!=null &&	!"".equals(teacher_id)	&& 	!"null".equalsIgnoreCase(teacher_id)){
				searchProperties.add(new SearchProperty("teacher_id",teacher_id));
			}
			if(semesterId!=null &&	!"".equals(semesterId)	&& 	!"null".equalsIgnoreCase(semesterId)){
				searchProperties.add(new SearchProperty("semester_id",semesterId));
			}
			Pici pici = getActiveGraduateDesignBatch();
			if(pici != null)
				searchProperties.add(new SearchProperty("pici_id",pici.getId(),"="));
			//BasicEntityList basicdatalist = new OracleBasicEntityList();
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			return oracleBasicGraduateList.getStudentFreeApplyTutor(page,searchProperties,null);
		} else {
			throw new PlatformException("您没有浏览毕业设计免做申请的权限");
		}
	}
	
	/**
	 * 获得学生的毕业设计与毕业大做业免做申请
	 */
	public List getStudentFreeApplyExec(Page page,String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo,String courseId,String teacher_id,String semesterId) throws PlatformException {
		if(this.getTeacherPriv().getGraduateDesignPiciExec == 1){
			List searchProperties = new ArrayList();
			if(courseId != null && !"".equals(courseId) && !"null".equalsIgnoreCase(courseId))
				searchProperties.add(new SearchProperty("course_id",courseId,"="));
			if(siteId != null && !"".equals(siteId) && !"null".equalsIgnoreCase(siteId))
				searchProperties.add(new SearchProperty("site_id",siteId,"="));
			if(edutypeId != null && !"".equals(edutypeId) && !"null".equalsIgnoreCase(edutypeId))
				searchProperties.add(new SearchProperty("edutype_id",edutypeId,"="));
			if(majorId != null && !"".equals(majorId) && !"null".equalsIgnoreCase(majorId))
				searchProperties.add(new SearchProperty("major_id",majorId,"="));
			if(gradeId != null && !"".equals(gradeId) && !"null".equalsIgnoreCase(gradeId))
				searchProperties.add(new SearchProperty("grade_id",gradeId,"="));
			if(status != null && !"".equals(status) && !"null".equalsIgnoreCase(status))
				searchProperties.add(new SearchProperty("status",status,"="));
			if(studentName != null && !"".equals(studentName) && !"null".equalsIgnoreCase(studentName))
				searchProperties.add(new SearchProperty("name",studentName,"like"));
			if(regNo != null && !"".equals(regNo) && !"null".equalsIgnoreCase(regNo))
				searchProperties.add(new SearchProperty("reg_no",regNo,"="));
			if(teacher_id!=null &&	!"".equals(teacher_id)	&& 	!"null".equalsIgnoreCase(teacher_id)){
				searchProperties.add(new SearchProperty("teacher_id",teacher_id));
			}
			if(semesterId!=null &&	!"".equals(semesterId)	&& 	!"null".equalsIgnoreCase(semesterId)){
				searchProperties.add(new SearchProperty("semester_id",semesterId));
			}
			Pici pici = getActiveGraduateExecDesignBatch();
			if(pici != null)
				searchProperties.add(new SearchProperty("pici_id",pici.getId(),"="));
			//BasicEntityList basicdatalist = new OracleBasicEntityList();
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			return oracleBasicGraduateList.getStudentFreeApplyExecTutor(page,searchProperties,null);
		}else{
			throw new PlatformException("您没有浏览毕业大作业免做申请的权限");
		}
		
	}
	
	/**
	 * 获得毕业设计及毕业大做业申请列表
	 */
	public int getStudentFreeApplyExecNum(String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo,String courseId,String teacher_id,String semesterId) throws PlatformException {
		if(this.getTeacherPriv().getGraduateDesignPiciExec == 1){
			List searchProperties = new ArrayList();
			if(courseId != null && !"".equals(courseId) && !"null".equalsIgnoreCase(courseId))
				searchProperties.add(new SearchProperty("course_id",courseId,"="));
			if(siteId != null && !"".equals(siteId) && !"null".equalsIgnoreCase(siteId))
				searchProperties.add(new SearchProperty("site_id",siteId,"="));
			if(edutypeId != null && !"".equals(edutypeId) && !"null".equalsIgnoreCase(edutypeId))
				searchProperties.add(new SearchProperty("edutype_id",edutypeId,"="));
			if(majorId != null && !"".equals(majorId) && !"null".equalsIgnoreCase(majorId))
				searchProperties.add(new SearchProperty("major_id",majorId,"="));
			if(gradeId != null && !"".equals(gradeId) && !"null".equalsIgnoreCase(gradeId))
				searchProperties.add(new SearchProperty("grade_id",gradeId,"="));
			if(status != null && !"".equals(status) && !"null".equalsIgnoreCase(status))
				searchProperties.add(new SearchProperty("status",status,"="));
			if(studentName != null && !"".equals(studentName) && !"null".equalsIgnoreCase(studentName))
				searchProperties.add(new SearchProperty("name",studentName,"like"));
			if(regNo != null && !"".equals(regNo) && !"null".equalsIgnoreCase(regNo))
				searchProperties.add(new SearchProperty("reg_no",regNo,"="));
			if(teacher_id!=null&&!"".equals(teacher_id)&&!"null".equalsIgnoreCase(teacher_id)){
				searchProperties.add(new SearchProperty("teacher_id",teacher_id));
			}
			if(semesterId!=null &&	!"".equals(semesterId)	&& 	!"null".equalsIgnoreCase(semesterId)){
				searchProperties.add(new SearchProperty("semester_id",semesterId));
			}
			//BasicEntityList basicdatalist = new OracleBasicEntityList();
			Pici pici = getActiveGraduateExecDesignBatch();
			if(pici != null)
				searchProperties.add(new SearchProperty("pici_id",pici.getId(),"="));
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			return oracleBasicGraduateList.getStudentFreeApplyExecTutorNum(searchProperties,null);
		}else{
			throw new PlatformException("您没有浏览毕业大作业免做申请的权限");
		}
		
	}
	
	/**
	 * 根据id 获得freapply
	 */
	public FreeApply getStudentFreeApply(String id) throws PlatformException {
		if(this.getTeacherPriv().getStudentFreeApply == 1){
			List searchProperties = new ArrayList();
			if(id != null && !"".equals(id) && !"null".equalsIgnoreCase(id))
				searchProperties.add(new SearchProperty("free_id",id,"="));
			List rs = new ArrayList();
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			rs = oracleBasicGraduateList.getStudentFreeApply(null,searchProperties,null);
			FreeApply app = new OracleFreeApply();
			if(rs.size()>0){
				app = (FreeApply)rs.get(0);
				return app;
			}else {
				return null;
			}
		} else {
			throw new PlatformException("您没有浏览毕业设计免做申请的权限");
		}
		
	}
	public FreeApply getStudentFreeApplyExec(String id) throws PlatformException {
		if(this.getTeacherPriv().getGraduateDesignPiciExec == 1){
			List searchProperties = new ArrayList();
			if(id != null && !"".equals(id) && !"null".equalsIgnoreCase(id))
				searchProperties.add(new SearchProperty("free_id",id,"="));
			List rs = new ArrayList();
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			rs = oracleBasicGraduateList.getStudentFreeApplyExec(null,searchProperties,null);
			FreeApply app = new OracleFreeApply();
			if(rs.size()>0){
				app = (FreeApply)rs.get(0);
				return app;
			}else {
				return null;
			}
		}else{
			throw new PlatformException("您没有浏览毕业大作业免做申请的权限");
		}
		
	}
	
	public int checkFreeApply(String id,String type,String note) throws PlatformException {
		if(this.getTeacherPriv().updateStudentFreeApply == 1){
			String status = "";
			if(type.equals("PASS"))
				status = "1";
			else if(type.equals("NOPASS")) 
				status = "2";
			FreeApply apply = new OracleFreeApply();
			apply.setId(id);
			apply.setTeacher_note(note);
			return apply.UpdateFreeApplyStatus(status);
		}else{
			throw new PlatformException("您没有修改毕业设计免做申请的权限");
		}
		
	}
	
	/**
	 * 审核freeApply ,加入分数 ,type为作业类型
	 */
	public int checkFreeApply(String id,String type,String note,String score) throws PlatformException {
		if(this.getTeacherPriv().updateStudentFreeApply == 1){
			String status = "";
			if(!"5".equals(score)){
				status ="1";
			}else{
				status = "2";
			}
			
			FreeApply apply = new OracleFreeApply();
			apply.setId(id);
			apply.setTeacher_note(note);
			apply.setScore_status(score);
			return apply.UpdateFreeApplyStatus(status);
		}else{
			throw new PlatformException("您没有修改毕业设计免做申请的权限");
		}
	}
	
	/**
	 * 获得当前学期
	 */
	public  List getActiveSemeser() throws PlatformException{
		List searchProperties = new ArrayList();
		//searchProperties.add(new SearchProperty("selected", "1", "="));
		OracleBasicEduList basicdatalist = new OracleBasicEduList();
		return basicdatalist.getSemesters(searchProperties, null);
	}
	
	/**
	 * 把免做申请的成绩加入总评成绩中. lwx 2008-06-07
	 */
	public int checkFreeApplyToTatalGrade(String studentId,String semesterId,String courseId,String score) throws PlatformException{
		if(this.getTeacherPriv().updateStudentFreeApply == 1){
			FreeApply apply = new OracleFreeApply();
			return apply.updateFreeApplyToTotalGrade(studentId,semesterId,courseId,score);
		}else{
			throw new PlatformException("您没有修改毕业设计免做申请的权限");
		}
	}
	

	@Override
	public int rejectDiscourseScore(String subjectId, String note) {
		Discourse subject = new OracleDiscourse();
		subject.setId(subjectId);
		subject.setScore_note(note);
		int i = subject.teacherScoreReject();
		return i;
	}

	@Override
	public int rejectExerciseScore(String subjectId, String note) {
		SubjectQuestionary subject = new OracleSubjectQuestionary();
		subject.setId(subjectId);
		subject.setScore_note(note);
		int i = subject.teacherScoreReject();
		return i;
	}

	@Override
	public int setDiscourseScore(String subjectId, String score) {
		Discourse subject = new OracleDiscourse();
		subject.setId(subjectId);
		subject.setRequisitionGrade(score);
		int i = subject.updateGrade();
		return i;
	}

	@Override
	public int setExerciseScore(String subjectId, String score) {
		SubjectQuestionary subject = new OracleSubjectQuestionary();
		subject.setId(subjectId);
		subject.setScore(score);
		int i = subject.updateScore();
		return i;
	}


	@Override
	public int confirmDiscourseScore(String subjectId, String note) {
		Discourse subject = new OracleDiscourse();
		subject.setId(subjectId);
		subject.setScore_note(note);
		int i = subject.teacherScoreConfirm();
		return i;
	}

	@Override
	public int confirmExerciseScore(String subjectId, String note) {
		SubjectQuestionary subject = new OracleSubjectQuestionary();
		subject.setId(subjectId);
		subject.setScore_note(note);
		int i = subject.teacherScoreConfirm();
		return i;
	}
	public List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId,String courseId,String semesterId) throws PlatformException {
		if(this.getTeacherPriv().getSubjectQuestionary == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (piciId != null && !piciId.equals("")) {
				searchProperty.add(new SearchProperty("pici_id", piciId, "="));
			}
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id", semesterId, "="));
			}
			
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
			
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getSubjectQuestionaryList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("您没有浏览毕业大作业的权限");
		}
	}

	public int getSubjectQuestionaryListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId,String courseId,String semesterId)
			throws PlatformException {
		if(this.getTeacherPriv().getSubjectQuestionary == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (reg_no != null && !reg_no.equals("")) {
				searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (site != null && !site.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site, "="));
			}
			if (grade != null && !grade.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", grade, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			if (piciId != null && !piciId.equals("")) {
				searchProperty.add(new SearchProperty("pici_id", piciId, "="));
			}
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id", semesterId, "="));
			}
			
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
			return oracleBasicGraduateList.getSubjectQuestionaryListNum(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业大作业的权限");
		}
	}
	
	public List getGraduatePici() throws PlatformException {
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
		return basicdatalist.getGraduateDesignBatch(null, null, null);
	}

	public List getGraduateExecPici() throws PlatformException {
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
		return basicdatalist.getGraduateExecDesignBatch(null, null, null);
	}

}
