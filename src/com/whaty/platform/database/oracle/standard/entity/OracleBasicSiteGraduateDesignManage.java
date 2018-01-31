package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleBasicGraduateList;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleDiscourse;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleFreeApply;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleGraduateExecPici;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleGraduatePici;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleMetaphaseCheck;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleRegBgCourse;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleRejoinRequisition;
import com.whaty.platform.database.oracle.standard.entity.graduatedesign.OracleSubjectQuestionary;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacherLimit;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.BasicSiteGraduateDesignManage;
import com.whaty.platform.entity.graduatedesign.BasicGraduateList;
import com.whaty.platform.entity.graduatedesign.Discourse;
import com.whaty.platform.entity.graduatedesign.FreeApply;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.entity.graduatedesign.RejoinRequisition;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.SiteTeacherLimit;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;

public class OracleBasicSiteGraduateDesignManage extends
		BasicSiteGraduateDesignManage {
	
	SiteManagerPriv siteManagerPriv;
	
	public OracleBasicSiteGraduateDesignManage(SiteManagerPriv siteManagerPriv) {
		this.siteManagerPriv =  siteManagerPriv;
	}

	public Pici getGraduateDesignBatch(String aid) throws PlatformException {
		if(this.siteManagerPriv.getGraduateDesignPici == 1){
			OracleGraduatePici oracleGraduatePici = new OracleGraduatePici(aid);
			return oracleGraduatePici;
		}else{
			throw new PlatformException("您没有浏览毕业设计批次的权限");
		}
		
	}
	
	/**
	 * 获得到当前活动的毕业大作业批次
	 */
	public Pici getGraduateExecDesignBatch(String aid) throws PlatformException {
		if(this.siteManagerPriv.getGraduateDesignPiciExec == 1){
			OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici(aid);
			return oracleGraduatePici;
		}else{
			throw new PlatformException("您没有浏览毕业大作业批次的权限");
		}
		
	}

	public Pici getActiveGraduateDesignBatch() throws PlatformException {
		if(this.siteManagerPriv.getGraduateDesignPici == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("status", "1", "="));
			return basicdatalist.getGraduateDesignBatch(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计批次的权限");
		}
		
	}
	
	/**
	 * 获得到当前活动大作业批次
	 */
	public Pici getActiveGraduateExecDesignBatch() throws PlatformException {
		if(this.siteManagerPriv.getGraduateDesignPiciExec ==1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("status", "1", "="));
			return basicdatalist.getGraduateExecDesignBatch(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计批次的权限");
		}
		
	}

	public List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId) throws PlatformException {
		if(this.siteManagerPriv.getSubjectQuestionary == 1){
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
			return oracleBasicGraduateList.getSubjectQuestionaryList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("您没有浏览毕业大作业的权限");
		}
		
	}

	public int getSubjectQuestionaryListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId)
			throws PlatformException {
		if(this.siteManagerPriv.getSubjectQuestionary == 1){
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
			return oracleBasicGraduateList
					.getSubjectQuestionaryListNum(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业大作业的权限");
		}
		
	}

//	加入学院审核查询条件conResult
	public List getRegBeginCourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId,String conResult,String semesterId)
			throws PlatformException {
		if(this.siteManagerPriv.getRegBeginCourse == 1){
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
				searchProperty.add(new SearchProperty("status",conResult,"in"));//加入审核结果状态
			}
			
			if(semesterId !=null && !"".equals(semesterId)){
				searchProperty.add(new SearchProperty("semester_id",semesterId,"="));//加入学期
			}
			
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getRegBeginCourseList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计开题报告的权限");
		}
		
	}

//	加入学院审核查询条件conResult
	public int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId,String conResult,String semesterId)
			throws PlatformException {
		if(this.siteManagerPriv.getRegBeginCourse == 1){
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
				searchProperty.add(new SearchProperty("status",conResult,"in"));//加入审核结果状态
			}
			
			if(semesterId !=null && !"".equals(semesterId)){
				searchProperty.add(new SearchProperty("semester_id",semesterId,"="));//加入学期
			}
			
			return oracleBasicGraduateList.getRegBeginCourseListNum(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计开题报告的权限");
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
		if(this.siteManagerPriv.getMetaphaseCheck == 1){
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
			throw new PlatformException("您没有浏览毕业设计中期检查信息的权限");
		}
		
	}

	public int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId)
			throws PlatformException {
		if(this.siteManagerPriv.getMetaphaseCheck == 1){
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
			throw new PlatformException("您没有浏览毕业设计中期检查信息的权限");
		}
		
	}

	public List getRejoinRequisitionList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type) throws PlatformException {
		if(this.siteManagerPriv.getRejoinRequisition == 1){
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
			throw new PlatformException("您没有浏览毕业设计答辩评审信息的权限");
		}
		
	}

	public int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type) throws PlatformException {
		if(this.siteManagerPriv.getRejoinRequisition == 1){
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
			throw new PlatformException("您没有浏览毕业设计答辩评审信息的权限");
		}
		
	}

	public List getDiscourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type) throws PlatformException {
		if(this.siteManagerPriv.getDiscourse == 1){
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
			throw new PlatformException("您没有浏览毕业设计终稿信息的权限");
		}
		
	}

	public int getDiscourseListNum(String reg_no, String name, String site,
			String grade, String eduTypeID, String majorId, String type)
			throws PlatformException {
		if(this.siteManagerPriv.getDiscourse == 1){
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
			throw new PlatformException("您没有浏览毕业设计终稿信息的权限");
		}
		
	}

	public SubjectQuestionary getSubjectQuestionary(String id)
			throws PlatformException {
		if(this.siteManagerPriv.getSubjectQuestionary == 1){
			SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary(id);
			return subjectQuestionary;
		}else{
			throw new PlatformException("您没有浏览毕业大作业的权限");
		}
		
	}

	public RegBgCourse getRegBeginCourse(String id) throws PlatformException {
		if(this.siteManagerPriv.getRegBeginCourse == 1){
			RegBgCourse regBgCourse = new OracleRegBgCourse(id);
			return regBgCourse;
		}else{
			throw new PlatformException("您没有浏览毕业设计开题报告的权限");
		}
		
	}

	public SiteTeacher getSiteTutorTeacher(String id) throws PlatformException {
		if(this.siteManagerPriv.getSiteTutorTeacher == 1){
			SiteTeacher siteTeacher = new OracleSiteTeacher(id);
			return siteTeacher;
		}else{
			throw new PlatformException("您没有浏览分站教师的权限");
		}
	
	}

	public MetaphaseCheck getMetaphaseCheck(String id) throws PlatformException {
		if(this.siteManagerPriv.getMetaphaseCheck == 1){
			MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck(id);
			return metaphaseCheck;
		}else{
			throw new PlatformException("您没有浏览毕业设计中期检查信息的权限");
		}
		
	}

	public RejoinRequisition getRejoinRequisition(String id)
			throws PlatformException {
		if(this.siteManagerPriv.getRejoinRequisition == 1){
			RejoinRequisition rejoinRequisition = new OracleRejoinRequisition(id);
			return rejoinRequisition;
		}else{
			throw new PlatformException("您没有浏览毕业设计答辩评审信息的权限");
		}
		
	}

	public Discourse getDiscourse(String id) throws PlatformException {
		if(this.siteManagerPriv.getDiscourse == 1){
			Discourse discourse = new OracleDiscourse(id);
			return discourse;
		}else{
			throw new PlatformException("您没有浏览毕业设计终稿信息的权限");
		}
		
	}

	public List getGradeEduTypeMajor(Page page, String piciId, String gradeId,
			String eduTypeID, String majorId) throws PlatformException {
		if(this.siteManagerPriv.getGradeEdutypeMajor == 1){
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
			return basicdatalist.getGradeEduTypeMajorsForPici(page, searchProperty,
					orderProperty);
		}else{
			throw new PlatformException("您没有获得年级专业层次的权限");
		}
		
	}

	public int getGradeEduTypeMajorNum(String piciId, String gradeId,
			String eduTypeID, String majorId) throws PlatformException {
		if(this.siteManagerPriv.getGradeEdutypeMajor == 1){
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
			return basicdatalist.getGradeEduTypeMajorsForPiciNum(searchProperty);
		}else{
			throw new PlatformException("您没有获得年级专业层次的权限");
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
	
	public List getSelectStudentAtCurrentPici(Page page, String teacerId,
			String gradeId, String eduTypeID, String majorId,String courseId)
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
		if (courseId != null && !courseId.equals("")) {
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
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
			String eduTypeID, String majorId,String courseId) throws PlatformException {
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
		if (courseId != null && !courseId.equals("")) {
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
		}
		searchProperty.add(new SearchProperty("siteteacherid", teacerId, "="));
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("gradeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("edutypeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("majorid", OrderProperty.ASC));
		return basicdatalist.getSelectStudentAtCurrentPiciNum(searchProperty);
	}

	public List getNotSelectStudentAtCurrentPici(Page page, String teacerId,
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
		return basicdatalist.getNotSelectStudentAtCurrentPici(page,
				searchProperty, orderProperty);
	}

	public int getNotSelectStudentAtCurrentPici(String teacerId,
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
		return basicdatalist
				.getNotSelectStudentAtCurrentPiciNum(searchProperty);
	}

	public int addStudentForTeacher(String teacherId, String studentId)
			throws PlatformException {
		if(this.siteManagerPriv.addStudentForTeacher == 1){
			SiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit();
			SiteTeacher oracleSiteTeacher = new OracleSiteTeacher();
			Student oracleStudent = new OracleStudent();
			oracleSiteTeacher.setId(teacherId);
			oracleStudent.setId(studentId);

			oracleSiteTeacherLimit.setSiteTeacher(oracleSiteTeacher);
			oracleSiteTeacherLimit.setStudent(oracleStudent);
			return oracleSiteTeacherLimit.add();
		}else{
			throw new PlatformException("您没有为学生添加分站辅导教师的权限");
		}
		
	}

	public int removeStudentsForTeahcer(String id) throws PlatformException {
		if(this.siteManagerPriv.removeStudentsForTeahcer == 1){
			SiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit();
			oracleSiteTeacherLimit.setId(id);
			return oracleSiteTeacherLimit.delete();
		}else{
			throw new PlatformException("您没有为学生删除分站辅导教师的权限");
		}
		
	}

	public List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId) throws PlatformException {
		if(this.siteManagerPriv.getSubjectQuestionary == 1){
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
			throw new PlatformException("您没有浏览毕业大作业的权限");
		}
		
	}

	public int getSubjectQuestionaryListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId) throws PlatformException {
		if(this.siteManagerPriv.getSubjectQuestionary == 1){
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
			throw new PlatformException("您没有浏览毕业大作业的权限");
		}
		
	}
	
	public List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId,String courseId,String semesterId) throws PlatformException {
		if(this.siteManagerPriv.getSubjectQuestionary == 1){
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
		if(this.siteManagerPriv.getSubjectQuestionary == 1){
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
	
	
//	加入新的参数score使其可以按审核结果查询
	public List getStudentFreeApplyList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId,String courseId,String semesterId,String score) throws PlatformException {
		if(this.siteManagerPriv.getStudentFreeApply == 1){
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
			if(score !=null && !"".equals(score)){//把审核结果加入条件查询
				searchProperty.add(new SearchProperty("free_score",score,"="));
			}
			
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
			searchProperty.add(new SearchProperty("freestatus","1,2","in"));//只显示审核过的
			
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getStudentFreeApply(page,searchProperty, orderProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计免做申请的权限");
		}
		
	}


//	加入新的参数score使其可以按审核结果查询
	public int getStudentFreeApplyListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId,String courseId,String semesterId,String score)
			throws PlatformException {
		if(this.siteManagerPriv.getStudentFreeApply == 1){
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
				searchProperty.add(new SearchProperty("free_score",score,"="));//把审核结果加入条件查询
			}
			
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
			searchProperty.add(new SearchProperty("freestatus","1,2","in"));//只显示审核过的
			return oracleBasicGraduateList.getStudentFreeApplyNum(searchProperty,null);
		}else{
			throw new PlatformException("您没有浏览毕业设计免做申请的权限");
		}
	
	}
	
	//---------------获得毕业大作业免做申请 lwx 2008-05-30
//	加入新的参数score使其可以按审核结果查询
	public List getStudentFreeApplyExecList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId,String courseId,String semesterId,String score) throws PlatformException {
		if(this.siteManagerPriv.getStudentFreeApplyExec == 1){
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
			if(score !=null && !"".equals(score)){//把审核结果加入条件查询
				searchProperty.add(new SearchProperty("free_score",score,"="));
			}
			
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
			searchProperty.add(new SearchProperty("freestatus","1,2","in"));//只显示审核过的
			
			orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
			return oracleBasicGraduateList.getStudentFreeApplyExec(page,searchProperty, orderProperty);
		}else{
			throw new PlatformException("您没有浏览毕业大作业免做申请的权限");
		}
		
	}


//	加入新的参数score使其可以按审核结果查询
	public int getStudentFreeApplyExecListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String piciId,String courseId,String semesterId,String score)
			throws PlatformException {
		if(this.siteManagerPriv.getStudentFreeApplyExec == 1){
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
				searchProperty.add(new SearchProperty("free_score",score,"="));//把审核结果加入条件查询
			}
			
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
			searchProperty.add(new SearchProperty("freestatus","1,2","in"));//只显示审核过的
			return oracleBasicGraduateList.getStudentFreeApplyExecNum(searchProperty,null);
		}else{
			throw new PlatformException("您没有浏览毕业大作业免做申请的权限");
		}
		
	}
	
	public List getRegBeginCourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId) throws PlatformException {
		if(this.siteManagerPriv.getRegBeginCourse == 1){
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
			return oracleBasicGraduateList.getRegBeginCourseList(page,
					searchProperty, orderProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计开题报告的权限");
		}
		
	}

	public int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId) throws PlatformException {
		if(this.siteManagerPriv.getRegBeginCourse == 1){
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
			return oracleBasicGraduateList.getRegBeginCourseListNum(searchProperty);
		}else{
			throw new PlatformException("您没有浏览毕业设计开题报告的权限");
		}
		
	}

	public List getMetaphaseCheckList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId) throws PlatformException {
		if(this.siteManagerPriv.getMetaphaseCheck == 1){
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
			throw new PlatformException("您没有浏览毕业设计中期检查信息的权限");
		}
		
	}

	public int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId) throws PlatformException {
		if(this.siteManagerPriv.getMetaphaseCheck == 1){
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
			throw new PlatformException("您没有浏览毕业设计中期检查信息的权限");
		}
		
	}

	public List getRejoinRequisitionList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String piciId,String semesterId) throws PlatformException {
		if(this.siteManagerPriv.getRejoinRequisition == 1){
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
			throw new PlatformException("您没有浏览毕业设计答辩评审信息的权限");
		}
		
	}
	
	/**
	 * 通过学期等获得答辩评审列表 数量
	 */
	public int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String piciId,String semesterId) throws PlatformException {
		if(this.siteManagerPriv.getRejoinRequisition == 1){
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
			throw new PlatformException("您没有浏览毕业设计答辩评审信息的权限");
		}
		
	}

	public List getDiscourseList(Page page, String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String piciId,String semesterId) throws PlatformException {
		if(this.siteManagerPriv.getDiscourse == 1){
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
			throw new PlatformException("您没有浏览毕业设计终稿信息的权限");
		}
		
	}

	public int getDiscourseListNum(String reg_no, String name, String site,
			String grade, String eduTypeID, String majorId, String type,String piciId,String semesterId)
			throws PlatformException {
		if(this.siteManagerPriv.getDiscourse == 1){
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
			throw new PlatformException("您没有浏览毕业设计终稿信息的权限");
		}
		
	}

	public Discourse createDiscourse() throws PlatformException {
		return new OracleDiscourse();
	}

	public void addDiscourseAssessGradeByRegNO(List discourseList)
			throws PlatformException {
		if(this.siteManagerPriv.updateDiscourseGrade == 1){
			Discourse oracleDiscourse = new OracleDiscourse();
			oracleDiscourse.graduateAssessGradeAddBatch(discourseList);
		}else{
			throw new PlatformException("您没有修改毕业设计信息的权限");
		}
		
	}

	public void addDiscourseRejionGradeByRegNO(List discourseList)
			throws PlatformException {
		if(this.siteManagerPriv.updateDiscourseGrade == 1){
			Discourse oracleDiscourse = new OracleDiscourse();
			oracleDiscourse.graduateRejoinGradeAddBatch(discourseList);
		}else{
			throw new PlatformException("您没有修改毕业设计信息的权限");
		}
		
	}
	
//	 --------------------------导入毕业设计成绩,并加入到总评成绩中 lwx 2008-06-07
	public void addDiscourseAssessGradeByRegNOTotalGrade(List discourseList,String semesterId) throws PlatformException {
		if(this.siteManagerPriv.updateDiscourseGrade == 1){
			Discourse oracleDiscourse = new OracleDiscourse();
	        oracleDiscourse.graduateAssessGradeAddBatchToTotalGrade(discourseList,semesterId);	
		}else{
			throw new PlatformException("您没有修改毕业设计信息的权限");
		}
		
	}
	public  void addDiscourseRejionGradeByRegNOTotalGrade(List discourseList,String semesterId)
	     throws PlatformException{
		if(this.siteManagerPriv.updateDiscourseGrade == 1){
			Discourse oracleDiscourse = new OracleDiscourse();
	        oracleDiscourse.graduateRejoinGradeAddBatchToTotalGrade(discourseList,semesterId);	
		}else{
			throw new PlatformException("您没有修改毕业设计信息的权限");
		}
		
	}
	
	//---------------------------------------------------------end
	

	@Override
	public List getNotSelectStudentAtCurrentPici(Page page, String teacerId,
			String gradeId, String eduTypeID, String majorId, String siteid,
			String reg_no, String name) throws PlatformException {
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
		if (reg_no != null && !reg_no.equals("")) {
			searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("name", name, "like"));
		}
		searchProperty.add(new SearchProperty("siteteacherid", teacerId, "="));

		searchProperty.add(new SearchProperty("siteid", siteid, "="));
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("gradeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("edutypeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("majorid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
		return basicdatalist.getNotSelectStudentAtCurrentPici(page,
				searchProperty, orderProperty);
	}

	public int getNotSelectStudentAtCurrentPiciNum(String teacerId,
			String gradeId, String eduTypeID, String majorId, String siteid,
			String reg_no, String name) throws PlatformException {
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
		if (reg_no != null && !reg_no.equals("")) {
			searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("name", name, "like"));
		}
		searchProperty.add(new SearchProperty("siteteacherid", teacerId, "="));
		searchProperty.add(new SearchProperty("siteid", siteid, "="));
		return basicdatalist
				.getNotSelectStudentAtCurrentPiciNum(searchProperty);
	}
	
	
	public List getNotSelectStudentAtCurrentPici(Page page, String teacerId,
			String gradeId, String eduTypeID, String majorId, String siteid,
			String reg_no, String name,String courseId) throws PlatformException {
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
		if (reg_no != null && !reg_no.equals("")) {
			searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("name", name, "like"));
		}
		if (courseId != null && !courseId.equals("")) {
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
		}
		searchProperty.add(new SearchProperty("siteteacherid", teacerId, "="));

		searchProperty.add(new SearchProperty("siteid", siteid, "="));
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("gradeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("edutypeid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("majorid", OrderProperty.ASC));
		orderProperty.add(new OrderProperty("reg_no", OrderProperty.ASC));
		return basicdatalist.getNotSelectStudentAtCurrentPici(page,
				searchProperty, orderProperty);
	}

	public int getNotSelectStudentAtCurrentPiciNum(String teacerId,
			String gradeId, String eduTypeID, String majorId, String siteid,
			String reg_no, String name,String courseId) throws PlatformException {
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
		if (reg_no != null && !reg_no.equals("")) {
			searchProperty.add(new SearchProperty("reg_no", reg_no, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperty.add(new SearchProperty("name", name, "like"));
		}
		if (courseId != null && !courseId.equals("")) {
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
		}
		searchProperty.add(new SearchProperty("siteteacherid", teacerId, "="));
		searchProperty.add(new SearchProperty("siteid", siteid, "="));
		return basicdatalist
				.getNotSelectStudentAtCurrentPiciNum(searchProperty);
	}
	

	public int updateDiscourseGrade(String id, String grade)
			throws PlatformException {
		if(this.siteManagerPriv.updateDiscourseGrade == 1){
			Discourse discourse = new OracleDiscourse();
			discourse.setId(id);
			discourse.setRequisitionGrade(grade);
			return discourse.updateGrade();
		}else{
			throw new PlatformException("您没有修改毕业设计信息的权限");
		}
		
	}
	
	
	/**
	 * 添加毕业论文成绩,并把成绩加到总评成绩中. 
	 */
	public int updateDiscourseGradeAndTotalGrade(String disId, String grade,String studentId,String courseId,String semesterId)
	throws PlatformException{
		if(this.siteManagerPriv.updateDiscourseGrade == 1){
			OracleDiscourse discourse = new OracleDiscourse();
			discourse.setId(disId);
			discourse.setRequisitionGrade(grade);
			return discourse.updateGradeAndTotalGrade(studentId,courseId,semesterId);
		}else{
			throw new PlatformException("您没有修改毕业设计信息的权限");
		}
		
	}
	

	public int limitTime(String type) throws PlatformException {
		Pici pici = new OracleGraduatePici();
		int suc = 0;

		if (type.equals("TYPE1")) {
			suc = pici.graduateDesignTimeSect();
		} else if (type.equals("TYPE2")) {
			suc = pici.subjectResearchTimeSect();
		} else if (type.equals("TYPE3")) {
			suc = pici.regBgCourseTimeSect();
		} else if (type.equals("TYPE4")) {
			suc = pici.discourseSumbitTimeSect();
		} else if (type.equals("TYPE5")) {
			suc = pici.rejoinRequesTimeSect();
		} else if (type.equals("TYPE6")) {
			suc = pici.reportGraduTimeSect();
		}
		return suc;
	}

	@Override
	public List getGraduateActivePici() throws PlatformException {
		if(this.siteManagerPriv.getGraduateDesignPici == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("status", "1", "="));
			return basicdatalist.getGraduateDesignBatch(null, searchProperty, null);
		}else{
			throw new PlatformException("您没有浏览毕业设计批次的权限");
		}
			
	}
	public int updateGraduateHomeWork(String id, String score) throws PlatformException {
		if(this.siteManagerPriv.updateGraduateHomeWork == 1){
			SubjectQuestionary oracleSubjectQuestionary = new OracleSubjectQuestionary();
			oracleSubjectQuestionary.setId(id);
			oracleSubjectQuestionary.setScore(score);
			return oracleSubjectQuestionary.updateScore();
		}else{
			throw new PlatformException("您没有录入毕业设计大作业成绩的权限");
		}
		
	}
	
	/**
	 * 添加大作业成绩,并录入总评成绩
	 */
	public int updateGraduateHomeWorkToTotalGrade(String homeworkId,String studentId,String semesterId,String courseId,String score)throws PlatformException{
		if(this.siteManagerPriv.updateGraduateHomeWork == 1){
			SubjectQuestionary oracleSubjectQuestionary = new OracleSubjectQuestionary();
			oracleSubjectQuestionary.setId(homeworkId);
			oracleSubjectQuestionary.setScore(score);
			return oracleSubjectQuestionary.updateScoreToTotalGrade(homeworkId,studentId,semesterId,courseId,score);
			
		}else{
			throw new PlatformException("您没有录入毕业设计大作业成绩的权限");
		}
		
		
	}

	public void updateBatchGraduateHomeWork(List scoreList) throws PlatformException {
		if(this.siteManagerPriv.updateGraduateHomeWork == 1){
			SubjectQuestionary oracleSubjectQuestionary = new OracleSubjectQuestionary();
			oracleSubjectQuestionary.updateScore_BatchExe(scoreList);
		}else{
			throw new PlatformException("您没有录入毕业设计大作业成绩的权限");
		}
		
	}
	
	/**
	 * 批量导入大作业成绩,并加入到总评成绩中
	 */
	public void updateBatchGraduateHomeWorkTotalGrade(List scoreList,
			String semesterId) throws PlatformException {
		if (this.siteManagerPriv.updateGraduateHomeWork == 1) {
			SubjectQuestionary oracleSubjectQuestionary = new OracleSubjectQuestionary();
			oracleSubjectQuestionary.updateScore_BatchExeTotalGrade(scoreList,
					semesterId);
		} else {
			throw new PlatformException("您没有录入毕业设计大作业成绩的权限");
		}

	}
	

	public FreeApply getStudentFreeApply(String studentId) throws PlatformException {
		if(this.siteManagerPriv.getStudentFreeApply == 1){
			OracleFreeApply oracleFreeApply = new OracleFreeApply(studentId);
			return oracleFreeApply;
		}else{
			throw new PlatformException("您没有浏览毕业设计免做申请的权限");
		}
		
	}
	public List getGraduateSiteTeacherMajors(String teacherId) throws PlatformException {
		if(this.siteManagerPriv.getGraduateSiteTeacherMajors == 1){
			BasicGraduateList major = new OracleBasicGraduateList() ;
			List majorList = major.getStudentMajors(teacherId);
			return majorList;
		}else{
			throw new PlatformException("您没有浏览分站教师指导专业信息的权限");
		}
		
	}
}
