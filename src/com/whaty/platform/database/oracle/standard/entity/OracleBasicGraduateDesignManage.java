package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleGrade;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleBasicGraduateList;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleDiscourse;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleFreeApply;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleGraduateExecPici;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleGraduatePici;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleMetaphaseCheck;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OraclePiciLimit;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleRegBgCourse;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleRejoinRequisition;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleSubjectQuestionary;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacherLimit;
import com.whaty.platform.entity.BasicGraduateDesignManage;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.entity.basic.Grade;
import com.whaty.platform.entity.basic.Major;
import com.whaty.platform.entity.graduatedesign.BasicGraduateList;
import com.whaty.platform.entity.graduatedesign.Discourse;
import com.whaty.platform.entity.graduatedesign.FreeApply;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.graduatedesign.PiciLimit;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.entity.graduatedesign.RejoinRequisition;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.recruit.TimeDef;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.entity.user.TeacherLimit;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;

public class OracleBasicGraduateDesignManage extends BasicGraduateDesignManage {
	
	ManagerPriv basicManagePriv;
	
	public OracleBasicGraduateDesignManage(ManagerPriv managerPriv) {
		basicManagePriv = managerPriv;
	}

	public int addGraduateDesginBatch(String id, String name, String startTime,
			String endTime, String subjectSTime, String subjectETime,
			String openSubSTime, String openSubETime, String subSubSTime,
			String subSubETime, String reJoinSTime, String reJoinETime,
			String reportGradeSTime, String reportGradeETime)
			throws PlatformException {
		if(this.basicManagePriv.addGradudateDesignPici == 1){
			OracleGraduatePici oracleGraduatePici = new OracleGraduatePici();
			oracleGraduatePici.setId(id);
			oracleGraduatePici.setName(name);

			TimeDef graduateDesignTime = new TimeDef();
			graduateDesignTime.setStartTime(startTime);
			graduateDesignTime.setEndTime(endTime);
			oracleGraduatePici.setGraduateDesignTime(graduateDesignTime);

			TimeDef subjectTime = new TimeDef();
			subjectTime.setStartTime(subjectSTime);
			subjectTime.setEndTime(subjectETime);
			oracleGraduatePici.setSubjectTime(subjectTime);

			TimeDef openSubTime = new TimeDef();
			openSubTime.setStartTime(openSubSTime);
			openSubTime.setEndTime(openSubETime);
			oracleGraduatePici.setOpenSubTime(openSubTime);

			TimeDef subSubTime = new TimeDef();
			subSubTime.setStartTime(subSubSTime);
			subSubTime.setEndTime(subSubETime);
			oracleGraduatePici.setSubSubTime(subSubTime);

			TimeDef rejoinTime = new TimeDef();
			rejoinTime.setStartTime(reJoinSTime);
			rejoinTime.setEndTime(reJoinETime);
			oracleGraduatePici.setReJoinTime(rejoinTime);

			TimeDef reportGradeTime = new TimeDef();
			reportGradeTime.setStartTime(reportGradeSTime);
			reportGradeTime.setEndTime(reportGradeETime);
			oracleGraduatePici.setReportGradeTime(reportGradeTime);

			int suc = oracleGraduatePici.add();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$addGraduateDesginBatch</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else {
			throw new PlatformException("��û����ӱ�ҵ������ε�Ȩ��");
		}
		
	}
	
	public int addGraduateDesginBatch(String id, String name, String startTime,
			String endTime, String subjectSTime, String subjectETime,
			String openSubSTime, String openSubETime, String subSubSTime,
			String subSubETime, String reJoinSTime, String reJoinETime,
			String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime)
			throws PlatformException {
		if(this.basicManagePriv.addGradudateDesignPici == 1){
			OracleGraduatePici oracleGraduatePici = new OracleGraduatePici();
			oracleGraduatePici.setId(id);
			oracleGraduatePici.setName(name);

			TimeDef graduateDesignTime = new TimeDef();
			graduateDesignTime.setStartTime(startTime);
			graduateDesignTime.setEndTime(endTime);
			oracleGraduatePici.setGraduateDesignTime(graduateDesignTime);

			TimeDef subjectTime = new TimeDef();
			subjectTime.setStartTime(subjectSTime);
			subjectTime.setEndTime(subjectETime);
			oracleGraduatePici.setSubjectTime(subjectTime);

			TimeDef openSubTime = new TimeDef();
			openSubTime.setStartTime(openSubSTime);
			openSubTime.setEndTime(openSubETime);
			oracleGraduatePici.setOpenSubTime(openSubTime);

			TimeDef subSubTime = new TimeDef();
			subSubTime.setStartTime(subSubSTime);
			subSubTime.setEndTime(subSubETime);
			oracleGraduatePici.setSubSubTime(subSubTime);

			TimeDef rejoinTime = new TimeDef();
			rejoinTime.setStartTime(reJoinSTime);
			rejoinTime.setEndTime(reJoinETime);
			oracleGraduatePici.setReJoinTime(rejoinTime);

			TimeDef reportGradeTime = new TimeDef();
			reportGradeTime.setStartTime(reportGradeSTime);
			reportGradeTime.setEndTime(reportGradeETime);
			oracleGraduatePici.setReportGradeTime(reportGradeTime);

			TimeDef writeDiscourseTime = new TimeDef();
			writeDiscourseTime.setStartTime(writeDiscourseBeginTime);
			writeDiscourseTime.setEndTime(writeDiscourseEndTime);
			oracleGraduatePici.setWriteDiscourseTiem(writeDiscourseTime);
			int suc = oracleGraduatePici.add();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$addGraduateDesginBatch</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else{
			throw new PlatformException("��û����ӱ�ҵ������ε�Ȩ��");
		}
		
	}
	public int addGraduateDesginBatch(String id, String name, String startTime,
			String endTime, String subjectSTime, String subjectETime,
			String openSubSTime, String openSubETime, String subSubSTime,
			String subSubETime, String reJoinSTime, String reJoinETime,
			String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime,String semester)
			throws PlatformException {
		if(this.basicManagePriv.addGradudateDesignPici == 1){
			OracleGraduatePici oracleGraduatePici = new OracleGraduatePici();
			oracleGraduatePici.setId(id);
			oracleGraduatePici.setName(name);

			TimeDef graduateDesignTime = new TimeDef();
			graduateDesignTime.setStartTime(startTime);
			graduateDesignTime.setEndTime(endTime);
			oracleGraduatePici.setGraduateDesignTime(graduateDesignTime);

			TimeDef subjectTime = new TimeDef();
			subjectTime.setStartTime(subjectSTime);
			subjectTime.setEndTime(subjectETime);
			oracleGraduatePici.setSubjectTime(subjectTime);

			TimeDef openSubTime = new TimeDef();
			openSubTime.setStartTime(openSubSTime);
			openSubTime.setEndTime(openSubETime);
			oracleGraduatePici.setOpenSubTime(openSubTime);

			TimeDef subSubTime = new TimeDef();
			subSubTime.setStartTime(subSubSTime);
			subSubTime.setEndTime(subSubETime);
			oracleGraduatePici.setSubSubTime(subSubTime);

			TimeDef rejoinTime = new TimeDef();
			rejoinTime.setStartTime(reJoinSTime);
			rejoinTime.setEndTime(reJoinETime);
			oracleGraduatePici.setReJoinTime(rejoinTime);

			TimeDef reportGradeTime = new TimeDef();
			reportGradeTime.setStartTime(reportGradeSTime);
			reportGradeTime.setEndTime(reportGradeETime);
			oracleGraduatePici.setReportGradeTime(reportGradeTime);

			TimeDef writeDiscourseTime = new TimeDef();
			writeDiscourseTime.setStartTime(writeDiscourseBeginTime);
			writeDiscourseTime.setEndTime(writeDiscourseEndTime);
			oracleGraduatePici.setWriteDiscourseTiem(writeDiscourseTime);
			oracleGraduatePici.setSemester_id(semester) ;
			int suc = oracleGraduatePici.add();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$addGraduateDesginBatch</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else{
			throw new PlatformException("��û����ӱ�ҵ������ε�Ȩ��");
		}
		
	}
	

	public int deleteGraduateDesignBatch(String id) throws PlatformException,
			PlatformException {
		if(this.basicManagePriv.deleteGraduateDesignPici == 1){
			OracleGraduatePici oracleGraduatePici = new OracleGraduatePici();
			oracleGraduatePici.setId(id);
			int suc = oracleGraduatePici.delete();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$deleteGraduateDesignBatch</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else{
			throw new PlatformException("��û��ɾ����ҵ������ε�Ȩ��");
		}
		
	}

	public int updateGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime)
			throws PlatformException {
		if(this.basicManagePriv.updateGraduateDesignPici == 1){
			OracleGraduatePici oracleGraduatePici = new OracleGraduatePici();
			oracleGraduatePici.setId(id);
			oracleGraduatePici.setName(name);

			TimeDef graduateDesignTime = new TimeDef();
			graduateDesignTime.setStartTime(startTime);
			graduateDesignTime.setEndTime(endTime);
			oracleGraduatePici.setGraduateDesignTime(graduateDesignTime);

			TimeDef subjectTime = new TimeDef();
			subjectTime.setStartTime(subjectSTime);
			subjectTime.setEndTime(subjectETime);
			oracleGraduatePici.setSubjectTime(subjectTime);

			TimeDef openSubTime = new TimeDef();
			openSubTime.setStartTime(openSubSTime);
			openSubTime.setEndTime(openSubETime);
			oracleGraduatePici.setOpenSubTime(openSubTime);

			TimeDef subSubTime = new TimeDef();
			subSubTime.setStartTime(subSubSTime);
			subSubTime.setEndTime(subSubETime);
			oracleGraduatePici.setSubSubTime(subSubTime);

			TimeDef rejoinTime = new TimeDef();
			rejoinTime.setStartTime(reJoinSTime);
			rejoinTime.setEndTime(reJoinETime);
			oracleGraduatePici.setReJoinTime(rejoinTime);

			TimeDef reportGradeTime = new TimeDef();
			reportGradeTime.setStartTime(reportGradeSTime);
			reportGradeTime.setEndTime(reportGradeETime);
			oracleGraduatePici.setReportGradeTime(reportGradeTime);
			int suc = oracleGraduatePici.update();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$updateGraduateDesginBatch</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());

			
			return suc;
		}else{
			throw new PlatformException("��û���޸ı�ҵ������ε�Ȩ��");
		}
		
	}
	
	public int updateGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime)
			throws PlatformException {
		
		if(this.basicManagePriv.updateGraduateDesignPici == 1){
			OracleGraduatePici oracleGraduatePici = new OracleGraduatePici();
			oracleGraduatePici.setId(id);
			oracleGraduatePici.setName(name);

			TimeDef graduateDesignTime = new TimeDef();
			graduateDesignTime.setStartTime(startTime);
			graduateDesignTime.setEndTime(endTime);
			oracleGraduatePici.setGraduateDesignTime(graduateDesignTime);

			TimeDef subjectTime = new TimeDef();
			subjectTime.setStartTime(subjectSTime);
			subjectTime.setEndTime(subjectETime);
			oracleGraduatePici.setSubjectTime(subjectTime);

			TimeDef openSubTime = new TimeDef();
			openSubTime.setStartTime(openSubSTime);
			openSubTime.setEndTime(openSubETime);
			oracleGraduatePici.setOpenSubTime(openSubTime);

			TimeDef subSubTime = new TimeDef();
			subSubTime.setStartTime(subSubSTime);
			subSubTime.setEndTime(subSubETime);
			oracleGraduatePici.setSubSubTime(subSubTime);

			TimeDef rejoinTime = new TimeDef();
			rejoinTime.setStartTime(reJoinSTime);
			rejoinTime.setEndTime(reJoinETime);
			oracleGraduatePici.setReJoinTime(rejoinTime);

			TimeDef reportGradeTime = new TimeDef();
			reportGradeTime.setStartTime(reportGradeSTime);
			reportGradeTime.setEndTime(reportGradeETime);
			oracleGraduatePici.setReportGradeTime(reportGradeTime);

			TimeDef writeDiscourseTime = new TimeDef();
			writeDiscourseTime.setStartTime(writeDiscourseBeginTime);
			writeDiscourseTime.setEndTime(writeDiscourseEndTime);
			oracleGraduatePici.setWriteDiscourseTiem(writeDiscourseTime);
			
			int suc = oracleGraduatePici.update();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$updateGraduateDesginBatch</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else{
			throw new PlatformException("��û���޸ı�ҵ������ε�Ȩ��");
		}
		
	}
	public int updateGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime,String semester_id)
			throws PlatformException {
		
		if(this.basicManagePriv.updateGraduateDesignPici == 1){
			OracleGraduatePici oracleGraduatePici = new OracleGraduatePici();
			oracleGraduatePici.setId(id);
			oracleGraduatePici.setName(name);

			TimeDef graduateDesignTime = new TimeDef();
			graduateDesignTime.setStartTime(startTime);
			graduateDesignTime.setEndTime(endTime);
			oracleGraduatePici.setGraduateDesignTime(graduateDesignTime);

			TimeDef subjectTime = new TimeDef();
			subjectTime.setStartTime(subjectSTime);
			subjectTime.setEndTime(subjectETime);
			oracleGraduatePici.setSubjectTime(subjectTime);

			TimeDef openSubTime = new TimeDef();
			openSubTime.setStartTime(openSubSTime);
			openSubTime.setEndTime(openSubETime);
			oracleGraduatePici.setOpenSubTime(openSubTime);

			TimeDef subSubTime = new TimeDef();
			subSubTime.setStartTime(subSubSTime);
			subSubTime.setEndTime(subSubETime);
			oracleGraduatePici.setSubSubTime(subSubTime);

			TimeDef rejoinTime = new TimeDef();
			rejoinTime.setStartTime(reJoinSTime);
			rejoinTime.setEndTime(reJoinETime);
			oracleGraduatePici.setReJoinTime(rejoinTime);

			TimeDef reportGradeTime = new TimeDef();
			reportGradeTime.setStartTime(reportGradeSTime);
			reportGradeTime.setEndTime(reportGradeETime);
			oracleGraduatePici.setReportGradeTime(reportGradeTime);

			TimeDef writeDiscourseTime = new TimeDef();
			writeDiscourseTime.setStartTime(writeDiscourseBeginTime);
			writeDiscourseTime.setEndTime(writeDiscourseEndTime);
			oracleGraduatePici.setWriteDiscourseTiem(writeDiscourseTime);
			oracleGraduatePici.setSemester_id(semester_id) ;
			int suc = oracleGraduatePici.update();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$updateGraduateDesginBatch</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else{
			throw new PlatformException("��û���޸ı�ҵ������ε�Ȩ��");
		}
		
	}

	public Pici getGraduateDesignBatch(String aid) throws PlatformException {
		if(this.basicManagePriv.getGraduateDesignPici == 1){
			Pici oracleGraduatePici = new OracleGraduatePici(aid);
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$getGraduateDesignBatch</whaty><whaty>STATUS$|$"
							+ oracleGraduatePici
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return oracleGraduatePici;
		}else{
			throw new PlatformException("��û�������ҵ������ε�Ȩ��");
		}
		
	}

	public Pici getActiveGraduateDesignBatch() throws PlatformException {
		
		if(this.basicManagePriv.getGraduateDesignPici == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("status", "1", "="));
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$getActiveGraduateDesignBatch</whaty><whaty>STATUS$|$"
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return basicdatalist.getGraduateDesignBatch(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ������ε�Ȩ��");
		}
		
	}

	public List getGraduateDesignBatches(Page page, String piciNo,
			String piciName) throws PlatformException {
		if(this.basicManagePriv.getGraduateDesignPici == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (piciNo != null && !piciNo.equals("")) {
				searchProperty.add(new SearchProperty("id", piciNo, "="));
			}
			if (piciName != null && !piciName.equals("")) {
				searchProperty.add(new SearchProperty("name", piciName, "like"));
			}
			List orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("id", OrderProperty.ASC));
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$getGraduateDesignBatches</whaty><whaty>STATUS$|$"
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return basicdatalist.getGraduateDesignBatch(page, searchProperty,orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ������ε�Ȩ��");
		}
		
	}

	public int getGraduateDesignBatchesNum(String piciNo, String piciName)
			throws PlatformException {
		if(this.basicManagePriv.getGraduateDesignPici == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (piciNo != null && !piciNo.equals("")) {
				searchProperty.add(new SearchProperty("id", piciNo, "="));
			}
			if (piciName != null && !piciName.equals("")) {
				searchProperty.add(new SearchProperty("name", piciName, "like"));
			}
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$getGraduateDesignBatchesNum</whaty><whaty>STATUS$|$"
							+ basicdatalist.getGraduateDesignBatchNum(searchProperty)
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return basicdatalist.getGraduateDesignBatchNum(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ������ε�Ȩ��");
		}
		
	}

	public int updateGraduateDesignBatchStatus(String id)
			throws PlatformException {
		if(this.basicManagePriv.updateGraduateDesignPici == 1){
			OracleGraduatePici oracleGraduatePici = new OracleGraduatePici(id);
			oracleGraduatePici.setId(id);
			int suc = 0;
			oracleGraduatePici.cancelActive();
			suc = oracleGraduatePici.setActive();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$updateGraduateDesignBatchStatus</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else{
			throw new PlatformException("��û���޸ı�ҵ������ε�Ȩ��");
		}
		

	}
	
	
//----------------��ҵ����ҵstart --------------2008-05-28 lwx	
	
	/**
	 * ��ӱ�ҵ����ҵ���� 2008-05-28 lwx
	 */
	public int addGraduateExecDesginBatch(String id, String name, String startTime,
			String endTime, String subjectSTime, String subjectETime,
			String openSubSTime, String openSubETime, String subSubSTime,
			String subSubETime, String reJoinSTime, String reJoinETime,
			String reportGradeSTime, String reportGradeETime)
			throws PlatformException {
		if(this.basicManagePriv.addGradudateDesignPiciExec == 1){
			OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici();
			oracleGraduatePici.setId(id);
			oracleGraduatePici.setName(name);

			TimeDef graduateDesignTime = new TimeDef();
			graduateDesignTime.setStartTime(startTime);
			graduateDesignTime.setEndTime(endTime);
			oracleGraduatePici.setGraduateDesignTime(graduateDesignTime);

			TimeDef subjectTime = new TimeDef();
			subjectTime.setStartTime(subjectSTime);
			subjectTime.setEndTime(subjectETime);
			oracleGraduatePici.setSubjectTime(subjectTime);

			TimeDef openSubTime = new TimeDef();
			openSubTime.setStartTime(openSubSTime);
			openSubTime.setEndTime(openSubETime);
			oracleGraduatePici.setOpenSubTime(openSubTime);

			TimeDef subSubTime = new TimeDef();
			subSubTime.setStartTime(subSubSTime);
			subSubTime.setEndTime(subSubETime);
			oracleGraduatePici.setSubSubTime(subSubTime);

			TimeDef rejoinTime = new TimeDef();
			rejoinTime.setStartTime(reJoinSTime);
			rejoinTime.setEndTime(reJoinETime);
			oracleGraduatePici.setReJoinTime(rejoinTime);

			TimeDef reportGradeTime = new TimeDef();
			reportGradeTime.setStartTime(reportGradeSTime);
			reportGradeTime.setEndTime(reportGradeETime);
			oracleGraduatePici.setReportGradeTime(reportGradeTime);

			int suc = oracleGraduatePici.add();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$addGraduateExecDesginBatch</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else{
			throw new PlatformException("��û����ӱ�ҵ����ҵ���ε�Ȩ��");
		}
		
	}
	
	/**
	 * ��ӱ�ҵ����ҵ���� 2008-05-28 lwx
	 */
	public int addGraduateExecDesginBatch(String id, String name, String startTime,
			String endTime, String subjectSTime, String subjectETime,
			String openSubSTime, String openSubETime, String subSubSTime,
			String subSubETime, String reJoinSTime, String reJoinETime,
			String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime)
			throws PlatformException {
		if(this.basicManagePriv.addGradudateDesignPiciExec == 1){
			OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici();
			oracleGraduatePici.setId(id);
			oracleGraduatePici.setName(name);

			TimeDef graduateDesignTime = new TimeDef();
			graduateDesignTime.setStartTime(startTime);
			graduateDesignTime.setEndTime(endTime);
			oracleGraduatePici.setGraduateDesignTime(graduateDesignTime);

			TimeDef subjectTime = new TimeDef();
			subjectTime.setStartTime(subjectSTime);
			subjectTime.setEndTime(subjectETime);
			oracleGraduatePici.setSubjectTime(subjectTime);

			TimeDef openSubTime = new TimeDef();
			openSubTime.setStartTime(openSubSTime);
			openSubTime.setEndTime(openSubETime);
			oracleGraduatePici.setOpenSubTime(openSubTime);

			TimeDef subSubTime = new TimeDef();
			subSubTime.setStartTime(subSubSTime);
			subSubTime.setEndTime(subSubETime);
			oracleGraduatePici.setSubSubTime(subSubTime);

			TimeDef rejoinTime = new TimeDef();
			rejoinTime.setStartTime(reJoinSTime);
			rejoinTime.setEndTime(reJoinETime);
			oracleGraduatePici.setReJoinTime(rejoinTime);

			TimeDef reportGradeTime = new TimeDef();
			reportGradeTime.setStartTime(reportGradeSTime);
			reportGradeTime.setEndTime(reportGradeETime);
			oracleGraduatePici.setReportGradeTime(reportGradeTime);

			TimeDef writeDiscourseTime = new TimeDef();
			writeDiscourseTime.setStartTime(writeDiscourseBeginTime);
			writeDiscourseTime.setEndTime(writeDiscourseEndTime);
			oracleGraduatePici.setWriteDiscourseTiem(writeDiscourseTime);
			int suc = oracleGraduatePici.add();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$addGraduateExecDesginBatch</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else{
			throw new PlatformException("��û����ӱ�ҵ����ҵ���ε�Ȩ��");
		}
		
	}
	public int addGraduateExecDesginBatch(String id, String name, String startTime,
			String endTime, String subjectSTime, String subjectETime,
			String openSubSTime, String openSubETime, String subSubSTime,
			String subSubETime, String reJoinSTime, String reJoinETime,
			String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime,String semester_id)
			throws PlatformException {
		if(this.basicManagePriv.addGradudateDesignPiciExec == 1){
			OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici();
			oracleGraduatePici.setId(id);
			oracleGraduatePici.setName(name);

			TimeDef graduateDesignTime = new TimeDef();
			graduateDesignTime.setStartTime(startTime);
			graduateDesignTime.setEndTime(endTime);
			oracleGraduatePici.setGraduateDesignTime(graduateDesignTime);

			TimeDef subjectTime = new TimeDef();
			subjectTime.setStartTime(subjectSTime);
			subjectTime.setEndTime(subjectETime);
			oracleGraduatePici.setSubjectTime(subjectTime);

			TimeDef openSubTime = new TimeDef();
			openSubTime.setStartTime(openSubSTime);
			openSubTime.setEndTime(openSubETime);
			oracleGraduatePici.setOpenSubTime(openSubTime);

			TimeDef subSubTime = new TimeDef();
			subSubTime.setStartTime(subSubSTime);
			subSubTime.setEndTime(subSubETime);
			oracleGraduatePici.setSubSubTime(subSubTime);

			TimeDef rejoinTime = new TimeDef();
			rejoinTime.setStartTime(reJoinSTime);
			rejoinTime.setEndTime(reJoinETime);
			oracleGraduatePici.setReJoinTime(rejoinTime);

			TimeDef reportGradeTime = new TimeDef();
			reportGradeTime.setStartTime(reportGradeSTime);
			reportGradeTime.setEndTime(reportGradeETime);
			oracleGraduatePici.setReportGradeTime(reportGradeTime);

			TimeDef writeDiscourseTime = new TimeDef();
			writeDiscourseTime.setStartTime(writeDiscourseBeginTime);
			writeDiscourseTime.setEndTime(writeDiscourseEndTime);
			oracleGraduatePici.setWriteDiscourseTiem(writeDiscourseTime);
			oracleGraduatePici.setSemester_id(semester_id) ;
			int suc = oracleGraduatePici.add();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$addGraduateExecDesginBatch</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else{
			throw new PlatformException("��û����ӱ�ҵ����ҵ���ε�Ȩ��");
		}
		
	}
	
	public int deleteGraduateExecDesignBatch(String id)
			throws PlatformException, PlatformException {
		if(this.basicManagePriv.deleteGraduateDesignPiciExec == 1){
			OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici();
			oracleGraduatePici.setId(id);
			int suc = oracleGraduatePici.delete();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$deleteGraduateExecDesignBatch</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else{
			throw new PlatformException("��û��ɾ����ҵ����ҵ���ε�Ȩ��");
		}
		
	}

	public int updateGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime)
			throws PlatformException {
		if(this.basicManagePriv.updateGraduateDesignPiciExec == 1){
			OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici();
			oracleGraduatePici.setId(id);
			oracleGraduatePici.setName(name);

			TimeDef graduateDesignTime = new TimeDef();
			graduateDesignTime.setStartTime(startTime);
			graduateDesignTime.setEndTime(endTime);
			oracleGraduatePici.setGraduateDesignTime(graduateDesignTime);

			TimeDef subjectTime = new TimeDef();
			subjectTime.setStartTime(subjectSTime);
			subjectTime.setEndTime(subjectETime);
			oracleGraduatePici.setSubjectTime(subjectTime);

			TimeDef openSubTime = new TimeDef();
			openSubTime.setStartTime(openSubSTime);
			openSubTime.setEndTime(openSubETime);
			oracleGraduatePici.setOpenSubTime(openSubTime);

			TimeDef subSubTime = new TimeDef();
			subSubTime.setStartTime(subSubSTime);
			subSubTime.setEndTime(subSubETime);
			oracleGraduatePici.setSubSubTime(subSubTime);

			TimeDef rejoinTime = new TimeDef();
			rejoinTime.setStartTime(reJoinSTime);
			rejoinTime.setEndTime(reJoinETime);
			oracleGraduatePici.setReJoinTime(rejoinTime);

			TimeDef reportGradeTime = new TimeDef();
			reportGradeTime.setStartTime(reportGradeSTime);
			reportGradeTime.setEndTime(reportGradeETime);
			oracleGraduatePici.setReportGradeTime(reportGradeTime);

			int suc = oracleGraduatePici.update();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$updateGraduateExecDesginBatch</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else{
			throw new PlatformException("��û���޸ı�ҵ����ҵ���ε�Ȩ��");
		}
		
	}

	public int updateGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime,
			String reportGradeETime, String writeDiscourseBeginTime,
			String writeDiscourseEndTime) throws PlatformException {

		if (this.basicManagePriv.updateGraduateDesignPiciExec == 1) {
			OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici();
			oracleGraduatePici.setId(id);
			oracleGraduatePici.setName(name);

			TimeDef graduateDesignTime = new TimeDef();
			graduateDesignTime.setStartTime(startTime);
			graduateDesignTime.setEndTime(endTime);
			oracleGraduatePici.setGraduateDesignTime(graduateDesignTime);

			TimeDef subjectTime = new TimeDef();
			subjectTime.setStartTime(subjectSTime);
			subjectTime.setEndTime(subjectETime);
			oracleGraduatePici.setSubjectTime(subjectTime);

			TimeDef openSubTime = new TimeDef();
			openSubTime.setStartTime(openSubSTime);
			openSubTime.setEndTime(openSubETime);
			oracleGraduatePici.setOpenSubTime(openSubTime);

			TimeDef subSubTime = new TimeDef();
			subSubTime.setStartTime(subSubSTime);
			subSubTime.setEndTime(subSubETime);
			oracleGraduatePici.setSubSubTime(subSubTime);

			TimeDef rejoinTime = new TimeDef();
			rejoinTime.setStartTime(reJoinSTime);
			rejoinTime.setEndTime(reJoinETime);
			oracleGraduatePici.setReJoinTime(rejoinTime);

			TimeDef reportGradeTime = new TimeDef();
			reportGradeTime.setStartTime(reportGradeSTime);
			reportGradeTime.setEndTime(reportGradeETime);
			oracleGraduatePici.setReportGradeTime(reportGradeTime);

			TimeDef writeDiscourseTime = new TimeDef();
			writeDiscourseTime.setStartTime(writeDiscourseBeginTime);
			writeDiscourseTime.setEndTime(writeDiscourseEndTime);
			oracleGraduatePici.setWriteDiscourseTiem(writeDiscourseTime);

			int suc = oracleGraduatePici.update();

			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateGraduateExecDesginBatch</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());

			return suc;
		} else {
			throw new PlatformException("��û���޸ı�ҵ����ҵ���ε�Ȩ��");
		}

	}
	public int updateGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime,
			String reportGradeETime, String writeDiscourseBeginTime,
			String writeDiscourseEndTime,String semester_id) throws PlatformException {

		if (this.basicManagePriv.updateGraduateDesignPiciExec == 1) {
			OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici();
			oracleGraduatePici.setId(id);
			oracleGraduatePici.setName(name);

			TimeDef graduateDesignTime = new TimeDef();
			graduateDesignTime.setStartTime(startTime);
			graduateDesignTime.setEndTime(endTime);
			oracleGraduatePici.setGraduateDesignTime(graduateDesignTime);

			TimeDef subjectTime = new TimeDef();
			subjectTime.setStartTime(subjectSTime);
			subjectTime.setEndTime(subjectETime);
			oracleGraduatePici.setSubjectTime(subjectTime);

			TimeDef openSubTime = new TimeDef();
			openSubTime.setStartTime(openSubSTime);
			openSubTime.setEndTime(openSubETime);
			oracleGraduatePici.setOpenSubTime(openSubTime);

			TimeDef subSubTime = new TimeDef();
			subSubTime.setStartTime(subSubSTime);
			subSubTime.setEndTime(subSubETime);
			oracleGraduatePici.setSubSubTime(subSubTime);

			TimeDef rejoinTime = new TimeDef();
			rejoinTime.setStartTime(reJoinSTime);
			rejoinTime.setEndTime(reJoinETime);
			oracleGraduatePici.setReJoinTime(rejoinTime);

			TimeDef reportGradeTime = new TimeDef();
			reportGradeTime.setStartTime(reportGradeSTime);
			reportGradeTime.setEndTime(reportGradeETime);
			oracleGraduatePici.setReportGradeTime(reportGradeTime);

			TimeDef writeDiscourseTime = new TimeDef();
			writeDiscourseTime.setStartTime(writeDiscourseBeginTime);
			writeDiscourseTime.setEndTime(writeDiscourseEndTime);
			oracleGraduatePici.setWriteDiscourseTiem(writeDiscourseTime);
			oracleGraduatePici.setSemester_id(semester_id) ;
			int suc = oracleGraduatePici.update();

			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateGraduateExecDesginBatch</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());

			return suc;
		} else {
			throw new PlatformException("��û���޸ı�ҵ����ҵ���ε�Ȩ��");
		}

	}

	public Pici getGraduateExecDesignBatch(String aid) throws PlatformException {
		if(this.basicManagePriv.getGraduateDesignPiciExec == 1){
			Pici oracleGraduatePici = new OracleGraduateExecPici(aid);
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$getGraduateExecDesignBatch</whaty><whaty>STATUS$|$"
							+ oracleGraduatePici
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return oracleGraduatePici;
		}else{
			throw new PlatformException("��û�������ҵ����ҵ���ε�Ȩ��");
		}
		
	}

	public Pici getActiveGraduateExecDesignBatch() throws PlatformException {
		if(this.basicManagePriv.getGraduateDesignPiciExec == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("status", "1", "="));
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$getActiveGraduateExecDesignBatch</whaty><whaty>STATUS$|$"
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return basicdatalist.getGraduateExecDesignBatch(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ����ҵ���ε�Ȩ��");
		}
		
	}

	public List getGraduateExecDesignBatches(Page page, String piciNo,
			String piciName) throws PlatformException {
		if(this.basicManagePriv.getGraduateDesignPiciExec == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (piciNo != null && !piciNo.equals("")) {
				searchProperty.add(new SearchProperty("id", piciNo, "="));
			}
			if (piciName != null && !piciName.equals("")) {
				searchProperty.add(new SearchProperty("name", piciName, "like"));
			}
			List orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("id", OrderProperty.ASC));
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$getGraduateExecDesignBatches</whaty><whaty>STATUS$|$"
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return basicdatalist.getGraduateExecDesignBatch(page, searchProperty,orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ����ҵ���ε�Ȩ��");
		}
		
	}

	public int getGraduateExecDesignBatchesNum(String piciNo, String piciName)
			throws PlatformException {
		if(this.basicManagePriv.getGraduateDesignPiciExec == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (piciNo != null && !piciNo.equals("")) {
				searchProperty.add(new SearchProperty("id", piciNo, "="));
			}
			if (piciName != null && !piciName.equals("")) {
				searchProperty.add(new SearchProperty("name", piciName, "like"));
			}
			

			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$getGraduateExecDesignBatchesNum</whaty><whaty>STATUS$|$"
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ basicdatalist.getGraduateExecDesignBatchNum(searchProperty)
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return basicdatalist.getGraduateExecDesignBatchNum(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ����ҵ���ε�Ȩ��");
		}
		
	}

	public int updateGraduateExecDesignBatchStatus(String id)
			throws PlatformException {
		if(this.basicManagePriv.updateGraduateDesignPiciExec == 1){
			OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici(id);
			oracleGraduatePici.setId(id);
			int suc = 0;
			oracleGraduatePici.cancelActive();
			suc = oracleGraduatePici.setActive();
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$updateGraduateExecDesignBatchStatus</whaty><whaty>STATUS$|$"
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ suc
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return suc;
		}else{
			throw new PlatformException("��û���޸ı�ҵ����ҵ���ε�Ȩ��");
		}
		

	}
	
//------------��ҵ����ҵ����end--------------

	public List getGradeEduTypeMajor(Page page, String piciId, String gradeId,
			String eduTypeID, String majorId) throws PlatformException {
		if(this.basicManagePriv.getGradeEdutypeMajor == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (gradeId != null && !gradeId.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", gradeId, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			searchProperty.add(new SearchProperty("pici_id", piciId, "="));
			List orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("grade_id", OrderProperty.ASC));
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$getGradeEduTypeMajor</whaty><whaty>STATUS$|$"
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return basicdatalist.getGradeEduTypeMajorsForPici(page, searchProperty,
					orderProperty);
		}else{
			throw new PlatformException("��û�л���꼶רҵ��ε�Ȩ��");
		}
		
	}

	public int getGradeEduTypeMajorNum(String piciId, String gradeId,
			String eduTypeID, String majorId) throws PlatformException {
		if(basicManagePriv.getGradeEdutypeMajor == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (gradeId != null && !gradeId.equals("")) {
				searchProperty.add(new SearchProperty("grade_id", gradeId, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty
						.add(new SearchProperty("edutype_id", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("major_id", majorId, "="));
			}
			searchProperty.add(new SearchProperty("pici_id", piciId, "="));
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$getGradeEduTypeMajorNum</whaty><whaty>STATUS$|$"
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return basicdatalist.getGradeEduTypeMajorsForPiciNum(searchProperty);
		}else{
			throw new PlatformException("��û�л���꼶רҵ��ε�Ȩ��");
		}
		
	}

	public List getGradeEduTypeMajorList(Page page, String pici_id,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException {
		if(basicManagePriv.getGradeEdutypeMajor == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (pici_id != null && !pici_id.equals("")) {
				searchProperty.add(new SearchProperty("pici_id", pici_id, "="));
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
			List orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("gradeid", OrderProperty.ASC));
			orderProperty.add(new OrderProperty("edutypeid", OrderProperty.ASC));
			orderProperty.add(new OrderProperty("majorid", OrderProperty.ASC));
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$getGradeEduTypeMajorList</whaty><whaty>STATUS$|$"
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return basicdatalist.getGradeEduTypeMajors(page, searchProperty,
					orderProperty);
		}else{
			throw new PlatformException("��û�л���꼶רҵ��ε�Ȩ��");
		}
		
	}

	public int getGradeEduTypeMajorListNum(String pici_id, String gradeId,
			String eduTypeID, String majorId) throws PlatformException {
		if(basicManagePriv.getGradeEdutypeMajor == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			if (pici_id != null && !pici_id.equals("")) {
				searchProperty.add(new SearchProperty("pici_id", pici_id, "="));
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
			List orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("gradeid", OrderProperty.ASC));
			
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.basicManagePriv.getSso_id()
							+ "</whaty><whaty>BEHAVIOR$|$getGradeEduTypeMajorListNum</whaty><whaty>STATUS$|$"
							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			
			return basicdatalist.getGradeEduTypeMajorsNum(searchProperty);
		}else{
			throw new PlatformException("��û�л���꼶רҵ��ε�Ȩ��");
		}
		
	}

	public int addGradeEduTypeMajors(String piciId, String gradeId,
			String eduTypeId, String majorId) throws PlatformException {
		OraclePiciLimit oraclePiciLimit = new OraclePiciLimit();
		OracleGraduatePici oracleGraduatePici = new OracleGraduatePici();
		oracleGraduatePici.setId(piciId);

		OracleGrade oracleGrade = new OracleGrade();
		oracleGrade.setId(gradeId);

		OracleEduType oracleEduType = new OracleEduType();
		oracleEduType.setId(eduTypeId);

		OracleMajor oracleMajor = new OracleMajor();
		oracleMajor.setId(majorId);

		oraclePiciLimit.setPiCi(oracleGraduatePici);
		oraclePiciLimit.setEduType(oracleEduType);
		oraclePiciLimit.setGrade(oracleGrade);
		oraclePiciLimit.setMajor(oracleMajor);

		return oraclePiciLimit.add();
	}

	public int removeGradeEduTypeMajors(String id) throws PlatformException {
		OraclePiciLimit oraclePiciLimit = new OraclePiciLimit();
		oraclePiciLimit.setId(id);
		return oraclePiciLimit.delete();
	}

	public int IsExistBatchId(String batch_id) throws PlatformException {
		OraclePiciLimit oraclePiciLimit = new OraclePiciLimit();
		return oraclePiciLimit.IsExsit(batch_id);
	}

	public List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId) throws PlatformException {
		if(this.basicManagePriv.getSubjectQuestionary == 1){
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
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getSubjectQuestionaryList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ����ҵ��Ȩ��");
		}
		
	}

	public int getSubjectQuestionaryListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId)
			throws PlatformException {
		if(this.basicManagePriv.getSubjectQuestionary == 1){
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
			return oracleBasicGraduateList
					.getSubjectQuestionaryListNum(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ����ҵ��Ȩ��");
		}
		
	}
	
	
	public List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId,String courseId,String semesterId) throws PlatformException {
		if(this.basicManagePriv.getSubjectQuestionary == 1){
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
			throw new PlatformException("��û�������ҵ����ҵ��Ȩ��");
		}
		
	}

	public int getSubjectQuestionaryListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId, String courseId, String semesterId)
			throws PlatformException {
		if (this.basicManagePriv.getSubjectQuestionary == 1) {
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
				searchProperty.add(new SearchProperty("edutype_id", eduTypeID,
						"="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty
						.add(new SearchProperty("major_id", majorId, "="));
			}
			if (piciId != null && !piciId.equals("")) {
				searchProperty.add(new SearchProperty("pici_id", piciId, "="));
			}
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id",
						semesterId, "="));
			}

			searchProperty.add(new SearchProperty("course_id", courseId, "="));
			return oracleBasicGraduateList
					.getSubjectQuestionaryListNum(searchProperty);
		} else {
			throw new PlatformException("��û�������ҵ����ҵ��Ȩ��");
		}

	}
	
	
	
	//�����µĲ���scoreʹ����԰���˽����ѯ
	public List getStudentFreeApplyList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId,String courseId,String semesterId,String score) throws PlatformException {
		if(this.basicManagePriv.getStudentFreeApply == 1){
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
			if(score !=null && !"".equals(score)){//����˽������������ѯ
				searchProperty.add(new SearchProperty("free_score",score,"="));
			}
			
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
			searchProperty.add(new SearchProperty("freestatus","1,2","in"));//ֻ��ʾ��˹���
			
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getStudentFreeApply(page,searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ������������Ȩ��");
		}
		
	}


//	�����µĲ���scoreʹ����԰���˽����ѯ
	public int getStudentFreeApplyListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId,String courseId,String semesterId,String score)
			throws PlatformException {
		if(this.basicManagePriv.getStudentFreeApply == 1){
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
			if(score !=null && !"".equals(score)){
				searchProperty.add(new SearchProperty("free_score",score,"="));//����˽������������ѯ
			}
			
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
			
			searchProperty.add(new SearchProperty("freestatus","1,2","in"));//ֻ��ʾ��˹���
			return oracleBasicGraduateList
					.getStudentFreeApplyNum(searchProperty,null);
		}else{
			throw new PlatformException("��û�������ҵ������������Ȩ��");
		}
		
	}
	
	
	/**
	 * ��վ����Ա��ñ�ҵ����ҵ�������� lwx 2008-05-30
	 * 
	 */
//	�����µĲ���scoreʹ����԰���˽����ѯ
	public List getStudentFreeApplyExecList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId,String courseId,String semesterId,String score) throws PlatformException {
		if(this.basicManagePriv.getStudentFreeApplyExec == 1){
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
			if(score !=null && !"".equals(score)){//����˽������������ѯ
				searchProperty.add(new SearchProperty("free_score",score,"="));
			}
			
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
			searchProperty.add(new SearchProperty("freestatus","1,2","in"));//ֻ��ʾ��˹���
			
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getStudentFreeApplyExec(page,searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ����ҵ���������Ȩ��");
		}
		
	}

	/**
	 * ��վ����Ա��ñ�ҵ����ҵ�������� lwx 2008-05-30
	 * 
	 */
//	�����µĲ���scoreʹ����԰���˽����ѯ
	public int getStudentFreeApplyExecListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId,String courseId,String semesterId,String score)
			throws PlatformException {
		if(this.basicManagePriv.getStudentFreeApplyExec == 1){
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
			if(score !=null && !"".equals(score)){
				searchProperty.add(new SearchProperty("free_score",score,"="));//����˽������������ѯ
			}
			
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
			searchProperty.add(new SearchProperty("freestatus","1,2","in"));//ֻ��ʾ��˹���
			return oracleBasicGraduateList.getStudentFreeApplyExecNum(searchProperty,null);
		}else{
			throw new PlatformException("��û�������ҵ����ҵ���������Ȩ��");
		}
		
	}
	
	
	
	public List getRegBeginCourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId)
			throws PlatformException {
		if(this.basicManagePriv.getRegBeginCourse == 1){
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
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getRegBeginCourseList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ��ƿ��ⱨ���Ȩ��");
		}
		
	}

	public int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId)
			throws PlatformException {
		if(this.basicManagePriv.getRegBeginCourse == 1){
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
			return oracleBasicGraduateList.getRegBeginCourseListNum(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ��ƿ��ⱨ���Ȩ��");
		}
		
	}

	public List getSiteTutorTeacherList(Page page, String teacherId, String name)
			throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSiteTutorTeacherListNum(String reg_no, String name)
			throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getMetaphaseCheckList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId)
			throws PlatformException {
		if(this.basicManagePriv.getMetaphaseCheck == 1){
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
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getMetaphaseCheckList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ������ڼ����Ϣ��Ȩ��");
		}
		
	}

	public int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId)
			throws PlatformException {
		if(this.basicManagePriv.getMetaphaseCheck == 1){
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
			return oracleBasicGraduateList.getMetaphaseCheckListNum(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ������ڼ����Ϣ��Ȩ��");
		}
		
	}

	public List getRejoinRequisitionList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type) throws PlatformException {
		if(this.basicManagePriv.getRejoinRequisition == 1){
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
			return oracleBasicGraduateList.getRejoinRequesList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ��ƴ��������Ϣ��Ȩ��");
		}
		
	}

	public int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type) throws PlatformException {
		if(this.basicManagePriv.getRejoinRequisition == 1){
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
			return oracleBasicGraduateList.getRejoinRequesListNum(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ��ƴ��������Ϣ��Ȩ��");
		}
		
	}

	public List getDiscourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type) throws PlatformException {
		if(this.basicManagePriv.getDiscourse == 1){
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
			return oracleBasicGraduateList.getDisCourseList(page, searchProperty,
					orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ����ո���Ϣ��Ȩ��");
		}
		
	}

	public int getDiscourseListNum(String reg_no, String name, String site,
			String grade, String eduTypeID, String majorId, String type)
			throws PlatformException {
		if(this.basicManagePriv.getDiscourse == 1){
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
			return oracleBasicGraduateList.getDisCourseListNum(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ����ո���Ϣ��Ȩ��");
		}
		
	}

	public SubjectQuestionary getSubjectQuestionary(String id)
			throws PlatformException {
		if(this.basicManagePriv.getSubjectQuestionary == 1){
			SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary(id);
			return subjectQuestionary;
		}else{
			throw new PlatformException("��û�������ҵ����ҵ��Ȩ��");
		}
		
	}

	public RegBgCourse getRegBeginCourse(String id) throws PlatformException {
		if(this.basicManagePriv.getRegBeginCourse == 1){
			RegBgCourse regBgCourse = new OracleRegBgCourse(id);
			return regBgCourse;
		}else{
			throw new PlatformException("��û�������ҵ��ƿ��ⱨ���Ȩ��");
		}
		
	}

	public SiteTeacher getSiteTutorTeacher(String id) throws PlatformException {
		if(this.basicManagePriv.getSiteTutorTeacher == 1){
			SiteTeacher siteTeacher = new OracleSiteTeacher(id);
			return siteTeacher;
		}else{
			throw new PlatformException("��û�������վָ����ʦ��Ȩ��");
		}
		
	}

	public MetaphaseCheck getMetaphaseCheck(String id) throws PlatformException {
		if(this.basicManagePriv.getMetaphaseCheck == 1){
			MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck(id);
			return metaphaseCheck;
		}else{
			throw new PlatformException("��û�������ҵ������ڼ����Ϣ��Ȩ��");
		}
		
	}

	public RejoinRequisition getRejoinRequisition(String id)
			throws PlatformException {
		if(this.basicManagePriv.getRejoinRequisition == 1){
			RejoinRequisition rejoinRequisition = new OracleRejoinRequisition(id);
			return rejoinRequisition;
		}else{
			throw new PlatformException("��û�������ҵ��ƴ��������Ϣ��Ȩ��");
		}
	
	}

	public Discourse getDiscourse(String id) throws PlatformException {
		if(this.basicManagePriv.getDiscourse == 1){
			Discourse discourse = new OracleDiscourse(id);
			return discourse;
		}else{
			throw new PlatformException("��û�������ҵ����ո���Ϣ��Ȩ��");
		}
		
	}

	public int updateDiscourseGrade(String id, String grade)
			throws PlatformException {
		if(this.basicManagePriv.updateDiscourseGrade == 1){
			Discourse discourse = new OracleDiscourse();
			discourse.setId(id);
			discourse.setRequisitionGrade(grade);
			return discourse.updateGrade();
		}else{
			throw new PlatformException("��û���޸ı�ҵ�����Ϣ��Ȩ��");
		}
		
	}
	
	/**
	 * ��ӱ�ҵ���ĳɼ�,���ѳɼ��ӵ������ɼ���. 
	 */
	public int updateDiscourseGradeAndTotalGrade(String disId, String grade,String studentId,String courseId,String semesterId)
	throws PlatformException{
		if(this.basicManagePriv.updateDiscourseGrade == 1){
			OracleDiscourse discourse = new OracleDiscourse();
			discourse.setId(disId);
			discourse.setRequisitionGrade(grade);
			return discourse.updateGradeAndTotalGrade(studentId,courseId,semesterId);
		}else{
			throw new PlatformException("��û���޸ı�ҵ�����Ϣ��Ȩ��");
		}
		
	}

	public List getGradeEduTypeMajorForTeacher(Page page, String teacerId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException {
		if(this.basicManagePriv.getGradeEduTypeMajorForTeacher == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();

			if (gradeId != null && !gradeId.equals("")) {
				searchProperty.add(new SearchProperty("gradeid", gradeId, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty.add(new SearchProperty("edutypeid", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("majorid", majorId, "="));
			}
			searchProperty.add(new SearchProperty("teacherid", teacerId, "="));

			orderProperty.add(new OrderProperty("gradeid", OrderProperty.ASC));
			orderProperty.add(new OrderProperty("edutypeid", OrderProperty.ASC));
			orderProperty.add(new OrderProperty("majorid", OrderProperty.ASC));
			return oracleBasicGraduateList.getGradeEduMajorsForTeacher(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û�������վָ����ʦ��ָ���꼶���רҵ��Ϣ��Ȩ��");
		}
		
	}

	public int getGradeEduTypeMajorForTeacherNum(String teacerId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException {
		if(this.basicManagePriv.getGradeEduTypeMajorForTeacher == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();

			if (gradeId != null && !gradeId.equals("")) {
				searchProperty.add(new SearchProperty("gradeid", gradeId, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty.add(new SearchProperty("edutypeid", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("majorid", majorId, "="));
			}
			searchProperty.add(new SearchProperty("teacherid", teacerId, "="));
			return oracleBasicGraduateList
					.getGradeEduMajorsForTeacherNum(searchProperty);
		}else{
			throw new PlatformException("��û�������վָ����ʦ��ָ���꼶���רҵ��Ϣ��Ȩ��");
		}
		
	}

	public int addGradeEduTypeMajorsForTeacher(String teacherId,
			String gradeId, String eduTypeId, String majorId)
			throws PlatformException {
		if(this.basicManagePriv.addGradeEduTypeMajorsForTeacher == 1){
			TeacherLimit teacherLimit = new OracleTeacherLimit();
			Teacher teacher = new OracleTeacher();
			teacher.setId(teacherId);
			teacherLimit.setTeacher(teacher);

			Grade grade = new OracleGrade();
			grade.setId(gradeId);
			teacherLimit.setGrade(grade);

			EduType eduType = new OracleEduType();
			eduType.setId(eduTypeId);
			teacherLimit.setEduType(eduType);

			Major major = new OracleMajor();
			major.setId(majorId);
			teacherLimit.setMajor(major);

			return teacherLimit.add();
		}else{
			throw new PlatformException("��û��ָ����վָ����ʦ�꼶���רҵ��Ϣ��Ȩ��");
		}
		
	}

	public int removeGradeEduTypeMajorsForTeahcer(String id)
			throws PlatformException {
		if(this.basicManagePriv.removeGradeEduTypeMajorsForTeahcer == 1){
			TeacherLimit teacherLimit = new OracleTeacherLimit();
			teacherLimit.setId(id);
			return teacherLimit.delete();
		}else{
			throw new PlatformException("��û��ɾ����վָ����ʦ�꼶���רҵ��Ϣ��Ȩ��");
		}
		
	}

	public List getNoneGradeEduTypeMajorForTeacher(Page page, String teacerId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException {
		if(this.basicManagePriv.getGradeEduTypeMajorForTeacher == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();

			if (gradeId != null && !gradeId.equals("")) {
				searchProperty.add(new SearchProperty("gradeid", gradeId, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty.add(new SearchProperty("edutypeid", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("majorid", majorId, "="));
			}
			searchProperty.add(new SearchProperty("teacherid", teacerId, "="));

			orderProperty.add(new OrderProperty("gradeid", OrderProperty.ASC));
			orderProperty.add(new OrderProperty("edutypeid", OrderProperty.ASC));
			orderProperty.add(new OrderProperty("majorid", OrderProperty.ASC));
			return oracleBasicGraduateList.getNoneGradeEduMajorsForTeacher(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û�������վָ����ʦ�꼶���רҵ��Ϣ��Ȩ��");
		}
	
	}

	public int getNoneGradeEduTypeMajorForTeacherNum(String teacerId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException {
		if(this.basicManagePriv.getGradeEduTypeMajorForTeacher == 1){
			BasicGraduateList oracleBasicGraduateList = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();

			if (gradeId != null && !gradeId.equals("")) {
				searchProperty.add(new SearchProperty("gradeid", gradeId, "="));
			}
			if (eduTypeID != null && !eduTypeID.equals("")) {
				searchProperty.add(new SearchProperty("edutypeid", eduTypeID, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperty.add(new SearchProperty("majorid", majorId, "="));
			}
			searchProperty.add(new SearchProperty("teacherid", teacerId, "="));
			return oracleBasicGraduateList
					.getNoneGradeEduMajorsForTeacherNum(searchProperty);
		}else{
			throw new PlatformException("��û�������վָ����ʦ�꼶���רҵ��Ϣ��Ȩ��");
		}
		
	}

	public List getSelectStudentAtCurrentPici(Page page, String teacerId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException {
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();

		List searchProperty = new ArrayList();
		if (gradeId != null && !gradeId.equals("")) {
			searchProperty.add(new SearchProperty("gradeid", gradeId, "="));
		}
		if (eduTypeID != null && !eduTypeID.equals("")) {
			searchProperty.add(new SearchProperty("edutypeid", eduTypeID, "="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperty.add(new SearchProperty("majorid", majorId, "="));
		}
		searchProperty.add(new SearchProperty("siteteacherid", teacerId, "="));
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("gradeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("edutypeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("majorid", OrderProperty.ASC));
		return basicdatalist.getSelectStudentAtCurrentPici(page,
				searchProperty, orderProperty);
	}

	public int getStudentAtCurrentPiciNum(String teacerId, String gradeId,
			String eduTypeID, String majorId) throws PlatformException {
		OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();

		List searchProperty = new ArrayList();
		if (gradeId != null && !gradeId.equals("")) {
			searchProperty.add(new SearchProperty("gradeid", gradeId, "="));
		}
		if (eduTypeID != null && !eduTypeID.equals("")) {
			searchProperty.add(new SearchProperty("edutypeid", eduTypeID, "="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperty.add(new SearchProperty("majorid", majorId, "="));
		}
		searchProperty.add(new SearchProperty("siteteacherid", teacerId, "="));
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("gradeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("edutypeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("majorid", OrderProperty.ASC));
		return basicdatalist.getSelectStudentAtCurrentPiciNum(searchProperty);
	}

	public int IsExitGraduateStudent(String id) throws PlatformException {
		PiciLimit pici = new OraclePiciLimit();
		return pici.IsExistStudent(id);
	}

	public List getGraduatePici() throws PlatformException {
		if(this.basicManagePriv.getGraduateDesignPici == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			return basicdatalist.getGraduateDesignBatch(null, null, null);
		}else{
			throw new PlatformException("��û�������ҵ������ε�Ȩ��");
		}
		
	}
	/**
	 * ��ô���ҵ�б�
	 */
	public List getGraduateExecPici() throws PlatformException {
		if(this.basicManagePriv.getGraduateDesignPiciExec == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			return basicdatalist.getGraduateExecDesignBatch(null, null, null);
		}else{
			throw new PlatformException("��û�������ҵ����ҵ���ε�Ȩ��");
		}
		
	}

	public List getDiscourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String piciId,String semesterId) throws PlatformException {
		if(this.basicManagePriv.getDiscourse == 1){
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
						.add(new SearchProperty("requistiontype", type, "="));
			}
			if (piciId != null && !piciId.equals("")) {
				searchProperty
						.add(new SearchProperty("pici_id", piciId, "="));
			}
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty
				.add(new SearchProperty("semester_id", semesterId, "="));
			}
			return oracleBasicGraduateList.getDisCourseList(page, searchProperty,
					orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ����ո��Ȩ��");
		}
		
	}

	public int getDiscourseListNum(String reg_no, String name, String site,
			String grade, String eduTypeID, String majorId, String type,String piciId,String semesterId)
			throws PlatformException {
		if(this.basicManagePriv.getDiscourse == 1){
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
						.add(new SearchProperty("requistiontype", type, "="));
			}
			if (piciId != null && !piciId.equals("")) {
				searchProperty
						.add(new SearchProperty("pici_id", piciId, "="));
			}
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty
				.add(new SearchProperty("semester_id", semesterId, "="));
			}
			return oracleBasicGraduateList.getDisCourseListNum(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ����ո��Ȩ��");
		}
		
	}

//	����ѧԺ��˲�ѯ����conResult
	public List getRegBeginCourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId,String conResult,String semesterId)
			throws PlatformException {
		if(this.basicManagePriv.getRegBeginCourse == 1){
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
			
			if(conResult !=null && !"".equals(conResult)){
				searchProperty.add(new SearchProperty("status",conResult,"in"));//������˽��״̬
			}
			
			if(semesterId !=null && !"".equals(semesterId)){
				searchProperty.add(new SearchProperty("semester_id",semesterId,"="));//������˽��״̬
			}
			
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getRegBeginCourseList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ��ƿ��ⱨ���Ȩ��");
		}
		
	}

//	����ѧԺ��˲�ѯ����conResult
	public int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId,String conResult,String semesterId)
			throws PlatformException {
		if(this.basicManagePriv.getRegBeginCourse == 1){
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
			
			if(conResult !=null && !"".equals(conResult)){
				searchProperty.add(new SearchProperty("status",conResult,"in"));//������˽��״̬
			}
			
			if(semesterId !=null && !"".equals(semesterId)){
				searchProperty.add(new SearchProperty("semester_id",semesterId,"="));//������˽��״̬
			}
			
			return oracleBasicGraduateList.getRegBeginCourseListNum(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ��ƿ��ⱨ���Ȩ��");
		}
		
	}

	public List getMetaphaseCheckList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId)
			throws PlatformException {
		if(this.basicManagePriv.getMetaphaseCheck == 1){
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
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getMetaphaseCheckList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ������ڼ����Ϣ��Ȩ��");
		}
		
	}

	public int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId)
			throws PlatformException {
		if(this.basicManagePriv.getMetaphaseCheck == 1){
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
			return oracleBasicGraduateList.getMetaphaseCheckListNum(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ������ڼ����Ϣ��Ȩ��");
		}
		
	}

	/**
	 * ͨ��ѧ�ڵȻ�ô�������б�
	 */
	public List getRejoinRequisitionList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String piciId,String semesterId) throws PlatformException {
		if(this.basicManagePriv.getRejoinRequisition == 1){
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
			if (piciId != null && !piciId.equals("")) {
				searchProperty.add(new SearchProperty("pici_id", piciId, "="));
			}
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id", semesterId, "="));
			}
			return oracleBasicGraduateList.getRejoinRequesList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("��û�������ҵ��ƴ��������Ϣ��Ȩ��");
		}
		
	}
	
	/**
	 * ͨ��ѧ�ڵȻ�ô�������б� ����
	 */
	public int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String piciId,String semesterId) throws PlatformException {
		if(this.basicManagePriv.getRejoinRequisition == 1){
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
			if (piciId != null && !piciId.equals("")) {
				searchProperty.add(new SearchProperty("pici_id", piciId, "="));
			}
			if (semesterId != null && !semesterId.equals("")) {
				searchProperty.add(new SearchProperty("semester_id", semesterId, "="));
			}
			return oracleBasicGraduateList.getRejoinRequesListNum(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ��ƴ��������Ϣ��Ȩ��");
		}
		
	}

	public Discourse createDiscourse() throws PlatformException {
		return new OracleDiscourse();
	}

	public void addDiscourseAssessGradeByRegNO(List discourseList) throws PlatformException {
		if(this.basicManagePriv.updateDiscourseGrade == 1){
			Discourse oracleDiscourse = new OracleDiscourse();
	        oracleDiscourse.graduateAssessGradeAddBatch(discourseList);	
		}else{
			throw new PlatformException("��û���޸ı�ҵ�����Ϣ��Ȩ��");
		}
		
	}
	public  void addDiscourseRejionGradeByRegNO(List discourseList)
	     throws PlatformException{
		if(this.basicManagePriv.updateDiscourseGrade == 1){
			Discourse oracleDiscourse = new OracleDiscourse();
	        oracleDiscourse.graduateRejoinGradeAddBatch(discourseList);	
		}else{
			throw new PlatformException("��û���޸ı�ҵ�����Ϣ��Ȩ��");
		}
		
	}
	
	// --------------------------�����ҵ��Ƴɼ�,�����뵽�����ɼ��� lwx 2008-06-07
	public void addDiscourseAssessGradeByRegNOTotalGrade(List discourseList,String semesterId) throws PlatformException {
		if(this.basicManagePriv.updateDiscourseGrade == 1){
			Discourse oracleDiscourse = new OracleDiscourse();
	        oracleDiscourse.graduateAssessGradeAddBatchToTotalGrade(discourseList,semesterId);	
		}else{
			throw new PlatformException("��û���޸ı�ҵ�����Ϣ��Ȩ��");
		}
		
	}
	public  void addDiscourseRejionGradeByRegNOTotalGrade(List discourseList,String semesterId)
	     throws PlatformException{
		if(this.basicManagePriv.updateDiscourseGrade ==1 ){
			Discourse oracleDiscourse = new OracleDiscourse();
	        oracleDiscourse.graduateRejoinGradeAddBatchToTotalGrade(discourseList,semesterId);	
		}else{
			throw new PlatformException("��û���޸ı�ҵ�����Ϣ��Ȩ��");
		}
		
	}
	//---------------------------------------------------------end

	public int limitTime(String type) throws PlatformException {
		Pici pici = new OracleGraduatePici();
		int suc = 0;
		
		if(type.equals("TYPE1")){
			suc = pici.graduateDesignTimeSect();
		}else if(type.equals("TYPE2")){
			suc = pici.subjectResearchTimeSect();
		}else if(type.equals("TYPE3")){
			suc = pici.regBgCourseTimeSect();
		}else if(type.equals("TYPE4")){
			suc = pici.discourseSumbitTimeSect();
		}else if(type.equals("TYPE5")){
			suc = pici.rejoinRequesTimeSect();
		}else if(type.equals("TYPE6")){
			suc = pici.reportGraduTimeSect();
		} 
		return suc;
	}

	public FreeApply getStudentFreeApply(String studentId) throws PlatformException {
		if(this.basicManagePriv.getStudentFreeApply == 1){
			OracleFreeApply oracleFreeApply = new OracleFreeApply(studentId);
			return oracleFreeApply;
		}else{
			throw new PlatformException("��û�������ҵ���������Ϣ��Ȩ��");
		}
		
	}

	public int updateGraduateHomeWork(String id, String score) throws PlatformException {
		if(this.basicManagePriv.updateGraduateHomeWork == 1){
			SubjectQuestionary oracleSubjectQuestionary = new OracleSubjectQuestionary();
			oracleSubjectQuestionary.setId(id);
			oracleSubjectQuestionary.setScore(score);
			return oracleSubjectQuestionary.updateScore();
		}else{
			throw new PlatformException("��û��¼���ҵ��ƴ���ҵ�ɼ���Ȩ��");
		}
		
	}
	
	/**
	 * ��Ӵ���ҵ�ɼ�,��¼�������ɼ�
	 */
	public int updateGraduateHomeWorkToTotalGrade(String homeworkId,String studentId,String semesterId,String courseId,String score)throws PlatformException{
		if(this.basicManagePriv.updateGraduateHomeWork == 1){
			SubjectQuestionary oracleSubjectQuestionary = new OracleSubjectQuestionary();
			oracleSubjectQuestionary.setId(homeworkId);
			oracleSubjectQuestionary.setScore(score);
			return oracleSubjectQuestionary.updateScoreToTotalGrade(homeworkId,studentId,semesterId,courseId,score);
		}else{
			throw new PlatformException("��û��¼���ҵ��ƴ���ҵ�ɼ���Ȩ��");
		}
		
		
	}
	

	public void updateBatchGraduateHomeWork(List scoreList) throws PlatformException {
		if(this.basicManagePriv.updateGraduateHomeWork == 1){
			SubjectQuestionary oracleSubjectQuestionary = new OracleSubjectQuestionary();
			oracleSubjectQuestionary.updateScore_BatchExe(scoreList);
		}else{
			throw new PlatformException("��û��¼���ҵ��ƴ���ҵ�ɼ���Ȩ��");
		}
		
	}
	
	/**
	 * �����������ҵ�ɼ�,�����뵽�����ɼ���
	 */
	public void updateBatchGraduateHomeWorkTotalGrade(List scoreList,
			String semesterId) throws PlatformException {
		if (this.basicManagePriv.updateGraduateHomeWork == 1) {
			SubjectQuestionary oracleSubjectQuestionary = new OracleSubjectQuestionary();
			oracleSubjectQuestionary.updateScore_BatchExeTotalGrade(scoreList,
					semesterId);
		} else {
			throw new PlatformException("��û��¼���ҵ��ƴ���ҵ�ɼ���Ȩ��");
		}

	}

	public List getGraduateSiteTeacherMajors(String teacherId) throws PlatformException {
		if(this.basicManagePriv.getGraduateSiteTeacherMajors == 1){
			BasicGraduateList major = new OracleBasicGraduateList() ;
			List majorList = major.getStudentMajors(teacherId);
			return majorList;
		}else{
			throw new PlatformException("��û�������վ��ʦָ��רҵ��Ϣ��Ȩ��");
		}
		
	}
}
