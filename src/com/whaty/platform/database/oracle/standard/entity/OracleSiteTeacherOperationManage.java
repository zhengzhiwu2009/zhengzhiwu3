package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.CoursewareFactory;
import com.whaty.platform.courseware.CoursewareManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleBasicActivityList;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEduList;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEntityList;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleBasicGraduateList;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleDiscourse;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleGraduateExecPici;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleGraduatePici;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleMetaphaseCheck;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleRegBgCourse;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleRejoinRequisition;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleSubjectQuestionary;
import com.whaty.platform.database.oracle.standard.entity.user.OracleBasicUserList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManagerPriv;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.interaction.OracleInteractionUserPriv;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUser;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUserAuthorization;
import com.whaty.platform.entity.BasicActivityManage;
import com.whaty.platform.entity.BasicScoreManage;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.SiteTeacherOperationManage;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.graduatedesign.BasicGraduateList;
import com.whaty.platform.entity.graduatedesign.Discourse;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.entity.graduatedesign.RejoinRequisition;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.SiteTeacherPriv;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.TeacherEduInfo;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.RedundanceData;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.SsoLog;
import com.whaty.platform.util.log.UserLog;
 
public class OracleSiteTeacherOperationManage extends
		SiteTeacherOperationManage {

	public OracleSiteTeacherOperationManage() {
	}

	public OracleSiteTeacherOperationManage(SiteTeacherPriv priv) {
		this.setSiteTeacherPriv(priv);
	}

	public SiteTeacher getSiteTeacher() throws PlatformException {
		if (this.getSiteTeacherPriv().getTeacher != 1) {
			throw new PlatformException("no right to getTeacher!");
		} else {
			return new OracleSiteTeacher(this.getSiteTeacherPriv()
					.getTeacherId());
		}
	}

	public int updateTeacher(String teacherId, String name, String password,
			String email, HumanNormalInfo normalInfo,
			TeacherEduInfo teachereduInfo, HumanPlatformInfo platformInfo,
			RedundanceData redundace) throws PlatformException {
		if (this.getSiteTeacherPriv().updateTeacher == 1) {
			OracleSiteTeacher oracleteacher = new OracleSiteTeacher(teacherId);
			oracleteacher.setEmail(email);
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
									+ this.getManagerPriv().getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateTeacher</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.TEACHER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸Ľ�ʦ��Ϣ��Ȩ��");
		}
	}

	public List getTeachClasses(Page page) throws PlatformException {
		// TODO Auto-generated method stub
		if (this.getSiteTeacherPriv().getTeachClasses != 1) {
			throw new PlatformException("no right to getTeachClasses!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			OracleSiteTeacher teacher = new OracleSiteTeacher(this
					.getSiteTeacherPriv().getTeacherId());
			List orderproperty = new ArrayList();
			OrderProperty order1 = new OrderProperty("to_number(semester_id)","DESC");
			OrderProperty order2 = new OrderProperty("course_name","ASC");
			orderproperty.add(order1);
			orderproperty.add(order2);
			return basicActivityList.searchSiteTeachClass(page, null, orderproperty,
					teacher);
		}
	}

	public int getTeachClassesNum() throws PlatformException {
		// TODO Auto-generated method stub
		if (this.getSiteTeacherPriv().getTeachClasses != 1) {
			throw new PlatformException("no right to getTeachClasses!");
		} else {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			OracleSiteTeacher teacher = new OracleSiteTeacher(this
					.getSiteTeacherPriv().getTeacherId());
			return basicActivityList.searchSiteTeachClassNum(null, teacher);
		}
	}

	public List getCourses(Page page, String id) throws PlatformException {
		// TODO Auto-generated method stub
		if (this.getSiteTeacherPriv().getCourses != 1) {
			throw new PlatformException("û�ж�ȡ�γ̵�Ȩ��!");
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

	public int updatePwd(String id, String oldPassword, String newPassword)
			throws PlatformException {
		// TODO Auto-generated method stub
		if (this.getSiteTeacherPriv().updatePwd != 1) {
			throw new PlatformException("no right to updatePwd!");
		} else {
			OracleSsoUser ssoUser = new OracleSsoUser(id);
			OracleSsoUserAuthorization ssoUserAuthor = new OracleSsoUserAuthorization(
					ssoUser.getLoginId());

			if (oldPassword.equals(ssoUserAuthor.getRightPwd())) {
				ssoUser.setPassword(newPassword);
				int suc = ssoUser.update();
				if (suc > 0) {
					SsoLog.setDebug("updatePwd success!" + id);
					UserLog
							.setInfo(
									"<whaty>USERID$|$"
											+ this.getManagerPriv().getSso_id()
											+ "</whaty><whaty>BEHAVIOR$|$updatePwd</whaty><whaty>STATUS$|$"
											+ suc
											+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
											+ LogType.TEACHER
											+ "</whaty><whaty>PRIORITY$|$"
											+ LogPriority.INFO + "</whaty>",
									new Date());
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

	public InteractionUserPriv getInteractionUserPriv(String userId)
			throws PlatformException {
		if (this.getSiteTeacherPriv().addCourseware != 1) {
			throw new PlatformException("û�н���γ̽�����Ȩ�ޣ�");
		} else {
			OracleInteractionUserPriv interactionUserPriv = new OracleInteractionUserPriv(
					userId);
			return interactionUserPriv;
		}
	}

	public InteractionUserPriv getInteractionUserPriv(String userId,
			String teachclassId) throws PlatformException {
		if (this.getSiteTeacherPriv().addCourseware != 1) {
			throw new PlatformException("û�н���γ̽�����Ȩ�ޣ�");
		} else {
			OracleInteractionUserPriv interactionUserPriv = new OracleInteractionUserPriv(
					userId);
			interactionUserPriv.setTeachclassId(teachclassId);
			return interactionUserPriv;
		}
	}

	public OpenCourse getOpenCourse(String openCourseId)
			throws PlatformException {
		if (this.getSiteTeacherPriv().getOpenCourse != 1) {
			throw new PlatformException("û�л�ȡ������Ϣ��Ȩ�ޣ�");
		} else {
			return new OracleOpenCourse(openCourseId);
		}
	}

	public CoursewareManagerPriv getCoursewareManagerPriv() {
		CoursewareFactory coursewareFactory = CoursewareFactory.getInstance();
		CoursewareManagerPriv priv = coursewareFactory
				.getCoursewareManagerPriv("");
		priv.setMessageId(this.getSiteTeacherPriv().getTeacherId());
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

	public ManagerPriv getManagerPriv() {
		ManagerPriv managerPriv = new OracleManagerPriv();
		managerPriv.setSso_id(this.getSiteTeacherPriv().getTeacherId());
		managerPriv.electiveStudent = 1;
		managerPriv.electiveBatchBySite = 1;
		managerPriv.electiveBatchByStudent = 1;
		managerPriv.electiveSingle = 1;
		managerPriv.addTeacher = 1;
		managerPriv.getTeacher = 1;
		managerPriv.appointTeacherForCourse = 1;
		managerPriv.deleteTeacher = 1;
		managerPriv.updateTeacher = 1;
		return managerPriv;
	}

	public Pici getActiveGraduateDesignBatch() throws PlatformException {
		if(this.getSiteTeacherPriv().getGraduateDesignPici == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("status", "1", "="));
			return basicdatalist.getGraduateDesignBatch(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ�����ε�Ȩ�ޣ�");
		}
		
	}

	@Override
	public Map getGraduateTypes() throws PlatformException{
		if(this.getSiteTeacherPriv().getGraduateDesignPici == 1){
			BasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("status", "1", "="));
			return basicdatalist.getGraduateTypes(this.getSiteTeacherPriv().getTeacherId());
		}else{
			throw new PlatformException("��û����1�ҵ�����ε�Ȩ�ޣ�");
		}
	}

	public Pici getGraduateDesignBatch(String aid) throws PlatformException {
		if(this.getSiteTeacherPriv().getGraduateDesignPici == 1){
			OracleGraduatePici oracleGraduatePici = new OracleGraduatePici(aid);
			return oracleGraduatePici;
		}else{
			throw new PlatformException("��û����1�ҵ�����ε�Ȩ�ޣ�");
		}
		
	}
	
	//--------------------------��ҵ����ҵ��δ���--------lwx 2008-05-28
	public Pici getActiveGraduateExecDesignBatch() throws PlatformException {
		if(this.getSiteTeacherPriv().getGraduateDesignPiciExec == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("status", "1", "="));
			return basicdatalist.getGraduateExecDesignBatch(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ����ҵ��ε�Ȩ�ޣ�");
		}
		
	}
	
	public Pici getGraduateExecDesignBatch(String aid) throws PlatformException {
		if(this.getSiteTeacherPriv().getGraduateDesignPiciExec == 1){
			OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici(aid);
			return oracleGraduatePici;
		}else{
			throw new PlatformException("��û����1�ҵ����ҵ��ε�Ȩ�ޣ�");
		}
		
	}
	//---------------------------end

	public List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String status) throws PlatformException {
		if(this.getSiteTeacherPriv().getSubjectQuestionary == 1){
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
			
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getSubjectQuestionaryList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û����1�ҵ����ҵ��Ȩ�ޣ�");
		}
		
	}

	public int getSubjectQuestionaryListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status) throws PlatformException {
		if(this.getSiteTeacherPriv().getSubjectQuestionary == 1){
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
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList
					.getSubjectQuestionaryListNum(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ����ҵ��Ȩ�ޣ�");
		}
		
	}

	public List getRegBeginCourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status) throws PlatformException {
		if(this.getSiteTeacherPriv().getRegBeginCourse == 1){
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
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getRegBeginCourseList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û����1�ҵ��ƿ��ⱨ���Ȩ�ޣ�");
		}
		
	}

	public int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status) throws PlatformException {
		if(this.getSiteTeacherPriv().getRegBeginCourse == 1){
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
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList.getRegBeginCourseListNum(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ��ƿ��ⱨ���Ȩ�ޣ�");
		}
		
	}

	public List getSiteTutorTeacherList(Page page, String teacherId,
			String name, String status) throws PlatformException {
		// TODO Auto-generated method stub
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
		if(this.getSiteTeacherPriv().getMetaphaseCheck == 1){
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
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getMetaphaseCheckList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û����1�ҵ������ڼ����Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status) throws PlatformException {
		if(this.getSiteTeacherPriv().getMetaphaseCheck == 1){
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
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList.getMetaphaseCheckListNum(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ������ڼ����Ϣ��Ȩ�ޣ�");
		}
		
	}

	public List getRejoinRequisitionList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String status) throws PlatformException {
		if(this.getSiteTeacherPriv().getRejoinRequisition == 1){
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
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList.getRejoinRequesList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û����1�ҵ��ƴ��������Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String status) throws PlatformException {
		if(this.getSiteTeacherPriv().getRejoinRequisition == 1){
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
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList.getRejoinRequesListNum(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ��ƴ��������Ϣ��Ȩ�ޣ�");
		}
		
	}

	public List getDiscourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String status) throws PlatformException {
		if(this.getSiteTeacherPriv().getDiscourse == 1){
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
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList.getDisCourseList(page, searchProperty,
					orderProperty);
		}else{
			throw new PlatformException("��û����1�ҵ����ո���Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int getDiscourseListNum(String reg_no, String name, String site,
			String grade, String eduTypeID, String majorId, String type,
			String status) throws PlatformException {
		if(this.getSiteTeacherPriv().getDiscourse == 1){
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
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList.getDisCourseListNum(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ����ո���Ϣ��Ȩ�ޣ�");
		}
		
	}

	public SubjectQuestionary getSubjectQuestionary(String id)
			throws PlatformException {
		if(this.getSiteTeacherPriv().getSubjectQuestionary == 1){
			SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary(id);
			return subjectQuestionary;
		}else{
			throw new PlatformException("��û����1�ҵ����ҵ��Ȩ�ޣ�");
		}
		
	}

	public RegBgCourse getRegBeginCourse(String id) throws PlatformException {
		if(this.getSiteTeacherPriv().getRegBeginCourse == 1){
			RegBgCourse regBgCourse = new OracleRegBgCourse(id);
			return regBgCourse;
		}else{
			throw new PlatformException("��û����1�ҵ��ƿ��ⱨ���Ȩ�ޣ�");
		}
		
	}

	public SiteTeacher getSiteTutorTeacher(String id) throws PlatformException {
		if(this.getSiteTeacherPriv().getSiteTutorTeacher == 1){
			SiteTeacher siteTeacher = new OracleSiteTeacher(id);
			return siteTeacher;
		}else{
			throw new PlatformException("��û����7�վָ����ʦ��Ȩ�ޣ�");
		}
		
	}

	public MetaphaseCheck getMetaphaseCheck(String id) throws PlatformException {
		if(this.getSiteTeacherPriv().getMetaphaseCheck == 1){
			MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck(id);
			return metaphaseCheck;
		}else{
			throw new PlatformException("��û����1�ҵ������ڼ����Ϣ��Ȩ�ޣ�");
		}
		
	}

	public RejoinRequisition getRejoinRequisition(String id)
			throws PlatformException {
		if(this.getSiteTeacherPriv().getRejoinRequisition == 1){
			RejoinRequisition rejoinRequisition = new OracleRejoinRequisition(id);
			return rejoinRequisition;
		}else{
			throw new PlatformException("��û����1�ҵ��ƴ��������Ϣ��Ȩ�ޣ�");
		}
		
	}

	public Discourse getDiscourse(String id) throws PlatformException {
		if(this.getSiteTeacherPriv().getDiscourse == 1){
			Discourse discourse = new OracleDiscourse(id);
			return discourse;
		}else{
			throw new PlatformException("��û����1�ҵ����ո���Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int changeSubjectQuestionary(String id, String status, String message) throws PlatformException{
		if(this.getSiteTeacherPriv().changeSubjectQuestionary == 1){
			SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary();
			subjectQuestionary.setId(id);
			subjectQuestionary.setSiteTeacher_note(message);
			int suc = 0;
			if (status.equals("PASS")) {
				suc = subjectQuestionary.siteTeacherConfirm();
			} else if (status.equals("NOPASS")) {
				suc = subjectQuestionary.siteTeacherReject();
			}
			return suc;
		}else{
			throw new PlatformException("��û���޸ı�ҵ����ҵ��Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int changeRegBeginCourse(String id, String status, String message)throws PlatformException {
		if(this.getSiteTeacherPriv().changeRegBeginCourse == 1){
			RegBgCourse regBeginCourse = new OracleRegBgCourse();
			regBeginCourse.setId(id);
			regBeginCourse.setSiteteacher_note(message);
			int suc = 0;
			if (status.equals("PASS")) {
				suc = regBeginCourse.siteTeacherConfirm();
			} else if (status.equals("NOPASS")) {
				suc = regBeginCourse.siteTeacherReject();
			}
			return suc;
		}else{
			throw new PlatformException("��û���޸ı�ҵ��ƿ�����Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int changeSiteTutorTeacher(String id, String status, String message) {
		// SiteTutorTeacher SiteTutorTeacher = new OracleSiteTutorTeacher();
		// TODO Auto-generated method stub
		return 0;
	}

	public int changeMetaphaseCheck(String id, String status, String message) throws PlatformException{
		if(this.getSiteTeacherPriv().changeMetaphaseCheck == 1){
			MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck();
			metaphaseCheck.setId(id);
			metaphaseCheck.setSiteteacher_note(message);
			int suc = 0;
			if (status.equals("PASS")) {
				suc = metaphaseCheck.siteTeacherConfirm();
			} else if (status.equals("NOPASS")) {
				suc = metaphaseCheck.siteTeacherReject();
			}
			return suc;
		}else{
			throw new PlatformException("��û���޸ı�ҵ������ڼ����Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int changeRejoinRequisition(String id, String status, String message) {
		RejoinRequisition rejoinRequisition = new OracleRejoinRequisition();
		rejoinRequisition.setId(id);
		rejoinRequisition.setSiteteacher_note(message);
		int suc = 0;
		if (status.equals("PASS")) {
			suc = rejoinRequisition.siteTeacherConfirm();
		} else if (status.equals("NOPASS")) {
			suc = rejoinRequisition.siteTeacherReject();
		}
		return suc;
	}

	public int changeDiscourse(String id, String status, String message) {
		Discourse discourse = new OracleDiscourse();
		discourse.setId(id);
		discourse.setSiteTeacher_note(message);
		int suc = 0;
		if (status.equals("PASS")) {
			suc = discourse.siteTeacherConfirm();
		} else if (status.equals("NOPASS")) {
			suc = discourse.siteTeacherReject();
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

	public List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String status,String teacherId) throws PlatformException {
		if(this.getSiteTeacherPriv().getSubjectQuestionary == 1){
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
			if (teacherId != null && !teacherId.equals("")) {
				OracleBasicUserList oracleBasicUserList= new OracleBasicUserList();
				String args = oracleBasicUserList.getStudentList(teacherId);
				searchProperty.add(new SearchProperty("student_id", args, "in"));
			}
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getSubjectQuestionaryList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û����1�ҵ����ҵ��Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int getSubjectQuestionaryListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status,String teacherId) throws PlatformException {
		if(this.getSiteTeacherPriv().getSubjectQuestionary == 1){
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
			if (teacherId != null && !teacherId.equals("")) {
				OracleBasicUserList oracleBasicUserList= new OracleBasicUserList();
				String args = oracleBasicUserList.getStudentList(teacherId);
				searchProperty.add(new SearchProperty("student_id", args, "in"));
			}
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList
					.getSubjectQuestionaryListNum(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ����ҵ��Ϣ��Ȩ�ޣ�");
		}
		
	}

	public List getSubjectQuestionaryList2(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String status,String teacherId,String semesterId) throws PlatformException {
		if(this.getSiteTeacherPriv().getSubjectQuestionary == 1){
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
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id", semesterId, "="));
			}
			if (teacherId != null && !teacherId.equals("")) {
				OracleBasicUserList oracleBasicUserList= new OracleBasicUserList();
				String args = oracleBasicUserList.getStudentList(teacherId);
				searchProperty.add(new SearchProperty("student_id", args, "in"));
			}
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getSubjectQuestionaryList2(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û����1�ҵ����ҵ��Ϣ��Ȩ�ޣ�");
		}
		
	}

	
	public int getSubjectQuestionaryListNum2(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status,String teacherId,String semesterId) throws PlatformException {
		if(this.getSiteTeacherPriv().getSubjectQuestionary == 1){
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
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id", semesterId, "="));
			}
			if (teacherId != null && !teacherId.equals("")) {
				OracleBasicUserList oracleBasicUserList= new OracleBasicUserList();
				String args = oracleBasicUserList.getStudentList(teacherId);
				searchProperty.add(new SearchProperty("student_id", args, "in"));
			}
			return oracleBasicGraduateList
					.getSubjectQuestionaryListNum2(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ����ҵ��Ϣ��Ȩ�ޣ�");
		}
		
	}

	
	public List getRegBeginCourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status,String teacherId,String semesterId) throws PlatformException {
		if(this.getSiteTeacherPriv().getRegBeginCourse == 1){
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
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id", semesterId, "="));
			}
			if (teacherId != null && !teacherId.equals("")) {
				OracleBasicUserList oracleBasicUserList= new OracleBasicUserList();
				String args = oracleBasicUserList.getStudentList(teacherId);
				searchProperty.add(new SearchProperty("student_id", args, "in"));
			}
			//Pici pici = getActiveGraduateDesignBatch();
			//searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getRegBeginCourseList2(page,
					searchProperty, orderProperty,this.getSiteTeacherPriv().getTeacherId());
		}else{
			throw new PlatformException("��û����1�ҵ��ƿ�����Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status,String teacherId,String semesterId) throws PlatformException {
		if(this.getSiteTeacherPriv().getRegBeginCourse == 1){
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
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id", semesterId, "="));
			}
			if (teacherId != null && !teacherId.equals("")) {
				OracleBasicUserList oracleBasicUserList= new OracleBasicUserList();
				String args = oracleBasicUserList.getStudentList(teacherId);
				searchProperty.add(new SearchProperty("student_id", args, "in"));
			}
			//Pici pici = getActiveGraduateDesignBatch();
			//searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList.getRegBeginCourseListNum2(searchProperty,this.getSiteTeacherPriv().getTeacherId());
		}else{
			throw new PlatformException("��û����1�ҵ��ƿ�����Ϣ��Ȩ�ޣ�");
		}
		
	}
 

	public List getMetaphaseCheckList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status,String teacherId,String semesterId) throws PlatformException {
		if(this.getSiteTeacherPriv().getMetaphaseCheck == 1){
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
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id", semesterId, "="));
			}
			if (teacherId != null && !teacherId.equals("")) {
				OracleBasicUserList oracleBasicUserList= new OracleBasicUserList();
				String args = oracleBasicUserList.getStudentList(teacherId);
				searchProperty.add(new SearchProperty("student_id", args, "in"));
			}
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getMetaphaseCheckList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û����1�ҵ������ڼ����Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String status,String teacherId,String semesterId) throws PlatformException {
		if(this.getSiteTeacherPriv().getMetaphaseCheck == 1){
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
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id", semesterId, "="));
			}
			if (teacherId != null && !teacherId.equals("")) {
				OracleBasicUserList oracleBasicUserList= new OracleBasicUserList();
				String args = oracleBasicUserList.getStudentList(teacherId);
				searchProperty.add(new SearchProperty("student_id", args, "in"));
			}
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList.getMetaphaseCheckListNum(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ������ڼ����Ϣ��Ȩ�ޣ�");
		}
		
	}

	public List getRejoinRequisitionList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String status,String teacherId) throws PlatformException {
		if(this.getSiteTeacherPriv().getRejoinRequisition == 1){
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
			if (teacherId != null && !teacherId.equals("")) {
				OracleBasicUserList oracleBasicUserList= new OracleBasicUserList();
				String args = oracleBasicUserList.getStudentList(teacherId);
				searchProperty.add(new SearchProperty("student_id", args, "in"));
			}
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList.getRejoinRequesList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û����1�ҵ��ƴ��������Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String status,String teacherId) throws PlatformException {
		if(this.getSiteTeacherPriv().getRejoinRequisition == 1){
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
			if (teacherId != null && !teacherId.equals("")) {
				OracleBasicUserList oracleBasicUserList= new OracleBasicUserList();
				String args = oracleBasicUserList.getStudentList(teacherId);
				searchProperty.add(new SearchProperty("student_id", args, "in"));
			}
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList.getRejoinRequesListNum(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ��ƴ��������Ϣ��Ȩ�ޣ�");
		}
		
	}

	public List getDiscourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String status,String teacherId) throws PlatformException {
		if(this.getSiteTeacherPriv().getDiscourse == 1){
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
			if (teacherId != null && !teacherId.equals("")) {
				OracleBasicUserList oracleBasicUserList= new OracleBasicUserList();
				String args = oracleBasicUserList.getStudentList(teacherId);
				searchProperty.add(new SearchProperty("student_id", args, "in"));
			}
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList.getDisCourseList(page, searchProperty,
					orderProperty);
		}else{
			throw new PlatformException("��û����1�ҵ����ո���Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int getDiscourseListNum(String reg_no, String name, String site,
			String grade, String eduTypeID, String majorId, String type,
			String status,String teacherId) throws PlatformException {
		if(this.getSiteTeacherPriv().getDiscourse == 1){
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
			if (teacherId != null && !teacherId.equals("")) {
				OracleBasicUserList oracleBasicUserList= new OracleBasicUserList();
				String args = oracleBasicUserList.getStudentList(teacherId);
				searchProperty.add(new SearchProperty("student_id", args, "in"));
			}
			Pici pici = getActiveGraduateDesignBatch();
			searchProperty.add(new SearchProperty("pici_id",pici.getId()));
			return oracleBasicGraduateList.getDisCourseListNum(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ����ո���Ϣ��Ȩ�ޣ�");
		}
		
	}
	
	public List getSubjectSearchStatusChangedStudent(String[] ids) throws PlatformException{
		if(this.getSiteTeacherPriv().getSubjectQuestionary == 1){
			return new OracleBasicGraduateList().getSubjectSearchStatusChangedStudent(ids);
		}else{
			throw new PlatformException("��û����1�ҵ����ҵ��Ϣ��Ȩ�ޣ�");
		}
		
	}

	public List getRegBeginCourseStatusChangedStudent(String[] ids) throws PlatformException{
		if(this.getSiteTeacherPriv().getRegBeginCourse == 1){
			return new OracleBasicGraduateList().getRegBeginCourseStatusChangedStudent(ids);	
		}else{
			throw new PlatformException("��û����1�ҵ��ƿ�����Ϣ��Ȩ�ޣ�");
		}
		
	}

	public List getMetaphaseCheckStatusChangedStudent(String[] ids)throws PlatformException {
		if(this.getSiteTeacherPriv().getMetaphaseCheck == 1){
			return new OracleBasicGraduateList().getMetaphaseCheckStatusChangedStudent(ids);	
		}else{
			throw new PlatformException("��û����1�ҵ������ڼ����Ϣ��Ȩ�ޣ�");
		}
		
	}
	
	public List getExerciseScoreSubmitStatusChangedStudent(String[] ids) throws PlatformException{
		return new OracleBasicGraduateList().getExerciseScoreSubmitStatusChangedStudent(ids);
	}

	public List getDiscourseScoreSubmitStatusChangedStudent(String[] ids) throws PlatformException{
		return new OracleBasicGraduateList().getDiscourseScoreSubmitStatusChangedStudent(ids);
	}

	public List getRejoinRequesStatusChangedStudent(String[] ids) throws PlatformException{
		return new OracleBasicGraduateList().getRejoinRequesStatusChangedStudent(ids);	
	}

	public List getDiscourseStatusChangedStudent(String[] ids) throws PlatformException{
		return new OracleBasicGraduateList().getDiscourseStatusChangedStudent(ids);	
	}

	public List getStudents(Page page,String stu_id,String name,String gradeId,String eduTypeID,String majorId)throws PlatformException{
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
		List searchProperty = new ArrayList();
		if (stu_id != null && !stu_id.equals("")) {
			searchProperty.add(new SearchProperty("studentid", stu_id, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("studentname", name, "="));
		}
		if (gradeId != null && !gradeId.equals("")) {
			searchProperty.add(new SearchProperty("gradeid", gradeId, "="));
		}
		if (eduTypeID != null && !eduTypeID.equals("")) {
			searchProperty.add(new SearchProperty("edutypeid", eduTypeID, "="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperty.add(new SearchProperty("majorid", majorId, "="));
		}
		searchProperty.add(new SearchProperty("siteteacherid", this.getSiteTeacherPriv().getTeacherId(), "="));
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("gradeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("edutypeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("majorid", OrderProperty.ASC));
		return basicdatalist.getSelectStudentAtCurrentPici(page,
				searchProperty, orderProperty);
	}
	
	public int getStudentsNum(String stu_id,String name,String gradeId,String eduTypeID,String majorId) throws PlatformException{
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
		List searchProperty = new ArrayList();
		if (stu_id != null && !stu_id.equals("")) {
			searchProperty.add(new SearchProperty("studentid", stu_id, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("studentname", name, "="));
		}
		if (gradeId != null && !gradeId.equals("")) {
			searchProperty.add(new SearchProperty("gradeid", gradeId, "="));
		}
		if (eduTypeID != null && !eduTypeID.equals("")) {
			searchProperty.add(new SearchProperty("edutypeid", eduTypeID, "="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperty.add(new SearchProperty("majorid", majorId, "="));
		}
		searchProperty.add(new SearchProperty("siteteacherid", this.getSiteTeacherPriv().getTeacherId(), "="));
		List orderProperty = new ArrayList();
		return basicdatalist.getSelectStudentAtCurrentPiciNum(searchProperty);
	}
	
	public List getGraduateStudents(Page page,String regNo,String name,String gradeId,String eduTypeID,String majorId)throws PlatformException{
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
		List searchProperty = new ArrayList();
		if (regNo != null && !regNo.equals("")) {
			searchProperty.add(new SearchProperty("reg_no", regNo, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("studentname", name, "="));
		}
		if (gradeId != null && !gradeId.equals("")) {
			searchProperty.add(new SearchProperty("gradeid", gradeId, "="));
		}
		if (eduTypeID != null && !eduTypeID.equals("")) {
			searchProperty.add(new SearchProperty("edutypeid", eduTypeID, "="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperty.add(new SearchProperty("majorid", majorId, "="));
		}
		searchProperty.add(new SearchProperty("siteteacherid", this.getSiteTeacherPriv().getTeacherId(), "="));
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("gradeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("edutypeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("majorid", OrderProperty.ASC));
		return basicdatalist.getSelectGraduateStudentAtCurrentPici(page,
				searchProperty, orderProperty);
	}
	
	public int getGraduateStudentsNum(String regNo,String name,String gradeId,String eduTypeID,String majorId)throws PlatformException {
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
		List searchProperty = new ArrayList();
		if (regNo != null && !regNo.equals("")) {
			searchProperty.add(new SearchProperty("reg_no", regNo, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("studentname", name, "="));
		}
		if (gradeId != null && !gradeId.equals("")) {
			searchProperty.add(new SearchProperty("gradeid", gradeId, "="));
		}
		if (eduTypeID != null && !eduTypeID.equals("")) {
			searchProperty.add(new SearchProperty("edutypeid", eduTypeID, "="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperty.add(new SearchProperty("majorid", majorId, "="));
		}
		searchProperty.add(new SearchProperty("siteteacherid", this.getSiteTeacherPriv().getTeacherId(), "="));
		List orderProperty = new ArrayList();
		return basicdatalist.getSelectGraduateStudentAtCurrentPiciNum(searchProperty);
	}
	
	public List getStudentsMajors() {
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
		return basicdatalist.getStudentMajors(this.getSiteTeacherPriv().getTeacherId());
	}
	

	@Override
	public int submitDiscourseScore(String subjectId, String score) {
		Discourse subject = new OracleDiscourse();
		subject.setId(subjectId);
		subject.setRequisitionGrade(score);
		int i = subject.updateGrade();
		return i;
	}

	@Override
	public int submitExerciseScore(String subjectId, String score) {
		SubjectQuestionary subject = new OracleSubjectQuestionary();
		subject.setId(subjectId);
		subject.setScore(score);
		int i = subject.updateScore();
		return i;
	}

	public List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId,String courseId,String semesterId) throws PlatformException {
		if(this.getSiteTeacherPriv().getSubjectQuestionary ==1){
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
			throw new PlatformException("��û����1�ҵ����ҵ��Ϣ��Ȩ�ޣ�");
		}
		
	}

	public int getSubjectQuestionaryListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId,String courseId,String semesterId)
			throws PlatformException {
		if(this.getSiteTeacherPriv().getSubjectQuestionary ==1){
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
			return oracleBasicGraduateList
					.getSubjectQuestionaryListNum(searchProperty);
		}else{
			throw new PlatformException("��û����1�ҵ����ҵ��Ϣ��Ȩ�ޣ�");
		}
		
	}

	@Override
	public boolean canOperateStduent(String student_id) throws PlatformException {
		Student student = new OracleStudent(student_id) ;
		String stu_site = student.getStudentInfo().getSite_id() ;
		String teacher_site = getSiteTeacher().getSiteId() ;
		if(stu_site!=null && stu_site.equalsIgnoreCase(teacher_site)) 
			return true ;
		return false;
	}

	
	/**
	 * ���ѧ��
	 */
	public  List getActiveSemeser() throws PlatformException{
		List searchProperties = new ArrayList();
		//searchProperties.add(new SearchProperty("selected", "1", "="));
		OracleBasicEduList basicdatalist = new OracleBasicEduList();
		return basicdatalist.getSemesters(searchProperties, null);
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
