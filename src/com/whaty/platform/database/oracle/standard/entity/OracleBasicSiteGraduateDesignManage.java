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
			throw new PlatformException("��û�������ҵ������ε�Ȩ��");
		}
		
	}
	
	/**
	 * ��õ���ǰ��ı�ҵ����ҵ����
	 */
	public Pici getGraduateExecDesignBatch(String aid) throws PlatformException {
		if(this.siteManagerPriv.getGraduateDesignPiciExec == 1){
			OracleGraduateExecPici oracleGraduatePici = new OracleGraduateExecPici(aid);
			return oracleGraduatePici;
		}else{
			throw new PlatformException("��û�������ҵ����ҵ���ε�Ȩ��");
		}
		
	}

	public Pici getActiveGraduateDesignBatch() throws PlatformException {
		if(this.siteManagerPriv.getGraduateDesignPici == 1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("status", "1", "="));
			return basicdatalist.getGraduateDesignBatch(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ������ε�Ȩ��");
		}
		
	}
	
	/**
	 * ��õ���ǰ�����ҵ����
	 */
	public Pici getActiveGraduateExecDesignBatch() throws PlatformException {
		if(this.siteManagerPriv.getGraduateDesignPiciExec ==1){
			OracleBasicGraduateList basicdatalist = new OracleBasicGraduateList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("status", "1", "="));
			return basicdatalist.getGraduateExecDesignBatch(searchProperty);
		}else{
			throw new PlatformException("��û�������ҵ������ε�Ȩ��");
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
			throw new PlatformException("��û�������ҵ����ҵ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ����ҵ��Ȩ��");
		}
		
	}

//	����ѧԺ��˲�ѯ����conResult
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
				searchProperty.add(new SearchProperty("status",conResult,"in"));//������˽��״̬
			}
			
			if(semesterId !=null && !"".equals(semesterId)){
				searchProperty.add(new SearchProperty("semester_id",semesterId,"="));//����ѧ��
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
				searchProperty.add(new SearchProperty("status",conResult,"in"));//������˽��״̬
			}
			
			if(semesterId !=null && !"".equals(semesterId)){
				searchProperty.add(new SearchProperty("semester_id",semesterId,"="));//����ѧ��
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
			throw new PlatformException("��û�������ҵ������ڼ����Ϣ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ������ڼ����Ϣ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ��ƴ��������Ϣ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ��ƴ��������Ϣ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ����ո���Ϣ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ����ո���Ϣ��Ȩ��");
		}
		
	}

	public SubjectQuestionary getSubjectQuestionary(String id)
			throws PlatformException {
		if(this.siteManagerPriv.getSubjectQuestionary == 1){
			SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary(id);
			return subjectQuestionary;
		}else{
			throw new PlatformException("��û�������ҵ����ҵ��Ȩ��");
		}
		
	}

	public RegBgCourse getRegBeginCourse(String id) throws PlatformException {
		if(this.siteManagerPriv.getRegBeginCourse == 1){
			RegBgCourse regBgCourse = new OracleRegBgCourse(id);
			return regBgCourse;
		}else{
			throw new PlatformException("��û�������ҵ��ƿ��ⱨ���Ȩ��");
		}
		
	}

	public SiteTeacher getSiteTutorTeacher(String id) throws PlatformException {
		if(this.siteManagerPriv.getSiteTutorTeacher == 1){
			SiteTeacher siteTeacher = new OracleSiteTeacher(id);
			return siteTeacher;
		}else{
			throw new PlatformException("��û�������վ��ʦ��Ȩ��");
		}
	
	}

	public MetaphaseCheck getMetaphaseCheck(String id) throws PlatformException {
		if(this.siteManagerPriv.getMetaphaseCheck == 1){
			MetaphaseCheck metaphaseCheck = new OracleMetaphaseCheck(id);
			return metaphaseCheck;
		}else{
			throw new PlatformException("��û�������ҵ������ڼ����Ϣ��Ȩ��");
		}
		
	}

	public RejoinRequisition getRejoinRequisition(String id)
			throws PlatformException {
		if(this.siteManagerPriv.getRejoinRequisition == 1){
			RejoinRequisition rejoinRequisition = new OracleRejoinRequisition(id);
			return rejoinRequisition;
		}else{
			throw new PlatformException("��û�������ҵ��ƴ��������Ϣ��Ȩ��");
		}
		
	}

	public Discourse getDiscourse(String id) throws PlatformException {
		if(this.siteManagerPriv.getDiscourse == 1){
			Discourse discourse = new OracleDiscourse(id);
			return discourse;
		}else{
			throw new PlatformException("��û�������ҵ����ո���Ϣ��Ȩ��");
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
			throw new PlatformException("��û�л���꼶רҵ��ε�Ȩ��");
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
			throw new PlatformException("��û�л���꼶רҵ��ε�Ȩ��");
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
			throw new PlatformException("��û��Ϊѧ����ӷ�վ������ʦ��Ȩ��");
		}
		
	}

	public int removeStudentsForTeahcer(String id) throws PlatformException {
		if(this.siteManagerPriv.removeStudentsForTeahcer == 1){
			SiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit();
			oracleSiteTeacherLimit.setId(id);
			return oracleSiteTeacherLimit.delete();
		}else{
			throw new PlatformException("��û��Ϊѧ��ɾ����վ������ʦ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ����ҵ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ����ҵ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ����ҵ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ����ҵ��Ȩ��");
		}
		
	}
	
	
//	�����µĲ���scoreʹ����԰���˽����ѯ
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
				searchProperty.add(new SearchProperty("free_score",score,"="));//����˽������������ѯ
			}
			
			searchProperty.add(new SearchProperty("course_id", courseId, "="));
			searchProperty.add(new SearchProperty("freestatus","1,2","in"));//ֻ��ʾ��˹���
			return oracleBasicGraduateList.getStudentFreeApplyNum(searchProperty,null);
		}else{
			throw new PlatformException("��û�������ҵ������������Ȩ��");
		}
	
	}
	
	//---------------��ñ�ҵ����ҵ�������� lwx 2008-05-30
//	�����µĲ���scoreʹ����԰���˽����ѯ
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


//	�����µĲ���scoreʹ����԰���˽����ѯ
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
			throw new PlatformException("��û�������ҵ��ƿ��ⱨ���Ȩ��");
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
			throw new PlatformException("��û�������ҵ��ƿ��ⱨ���Ȩ��");
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
			throw new PlatformException("��û�������ҵ������ڼ����Ϣ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ������ڼ����Ϣ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ��ƴ��������Ϣ��Ȩ��");
		}
		
	}
	
	/**
	 * ͨ��ѧ�ڵȻ�ô�������б� ����
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
			throw new PlatformException("��û�������ҵ��ƴ��������Ϣ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ����ո���Ϣ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ����ո���Ϣ��Ȩ��");
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
			throw new PlatformException("��û���޸ı�ҵ�����Ϣ��Ȩ��");
		}
		
	}

	public void addDiscourseRejionGradeByRegNO(List discourseList)
			throws PlatformException {
		if(this.siteManagerPriv.updateDiscourseGrade == 1){
			Discourse oracleDiscourse = new OracleDiscourse();
			oracleDiscourse.graduateRejoinGradeAddBatch(discourseList);
		}else{
			throw new PlatformException("��û���޸ı�ҵ�����Ϣ��Ȩ��");
		}
		
	}
	
//	 --------------------------�����ҵ��Ƴɼ�,�����뵽�����ɼ��� lwx 2008-06-07
	public void addDiscourseAssessGradeByRegNOTotalGrade(List discourseList,String semesterId) throws PlatformException {
		if(this.siteManagerPriv.updateDiscourseGrade == 1){
			Discourse oracleDiscourse = new OracleDiscourse();
	        oracleDiscourse.graduateAssessGradeAddBatchToTotalGrade(discourseList,semesterId);	
		}else{
			throw new PlatformException("��û���޸ı�ҵ�����Ϣ��Ȩ��");
		}
		
	}
	public  void addDiscourseRejionGradeByRegNOTotalGrade(List discourseList,String semesterId)
	     throws PlatformException{
		if(this.siteManagerPriv.updateDiscourseGrade == 1){
			Discourse oracleDiscourse = new OracleDiscourse();
	        oracleDiscourse.graduateRejoinGradeAddBatchToTotalGrade(discourseList,semesterId);	
		}else{
			throw new PlatformException("��û���޸ı�ҵ�����Ϣ��Ȩ��");
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
			throw new PlatformException("��û���޸ı�ҵ�����Ϣ��Ȩ��");
		}
		
	}
	
	
	/**
	 * ��ӱ�ҵ���ĳɼ�,���ѳɼ��ӵ������ɼ���. 
	 */
	public int updateDiscourseGradeAndTotalGrade(String disId, String grade,String studentId,String courseId,String semesterId)
	throws PlatformException{
		if(this.siteManagerPriv.updateDiscourseGrade == 1){
			OracleDiscourse discourse = new OracleDiscourse();
			discourse.setId(disId);
			discourse.setRequisitionGrade(grade);
			return discourse.updateGradeAndTotalGrade(studentId,courseId,semesterId);
		}else{
			throw new PlatformException("��û���޸ı�ҵ�����Ϣ��Ȩ��");
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
			throw new PlatformException("��û�������ҵ������ε�Ȩ��");
		}
			
	}
	public int updateGraduateHomeWork(String id, String score) throws PlatformException {
		if(this.siteManagerPriv.updateGraduateHomeWork == 1){
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
		if(this.siteManagerPriv.updateGraduateHomeWork == 1){
			SubjectQuestionary oracleSubjectQuestionary = new OracleSubjectQuestionary();
			oracleSubjectQuestionary.setId(homeworkId);
			oracleSubjectQuestionary.setScore(score);
			return oracleSubjectQuestionary.updateScoreToTotalGrade(homeworkId,studentId,semesterId,courseId,score);
			
		}else{
			throw new PlatformException("��û��¼���ҵ��ƴ���ҵ�ɼ���Ȩ��");
		}
		
		
	}

	public void updateBatchGraduateHomeWork(List scoreList) throws PlatformException {
		if(this.siteManagerPriv.updateGraduateHomeWork == 1){
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
		if (this.siteManagerPriv.updateGraduateHomeWork == 1) {
			SubjectQuestionary oracleSubjectQuestionary = new OracleSubjectQuestionary();
			oracleSubjectQuestionary.updateScore_BatchExeTotalGrade(scoreList,
					semesterId);
		} else {
			throw new PlatformException("��û��¼���ҵ��ƴ���ҵ�ɼ���Ȩ��");
		}

	}
	

	public FreeApply getStudentFreeApply(String studentId) throws PlatformException {
		if(this.siteManagerPriv.getStudentFreeApply == 1){
			OracleFreeApply oracleFreeApply = new OracleFreeApply(studentId);
			return oracleFreeApply;
		}else{
			throw new PlatformException("��û�������ҵ������������Ȩ��");
		}
		
	}
	public List getGraduateSiteTeacherMajors(String teacherId) throws PlatformException {
		if(this.siteManagerPriv.getGraduateSiteTeacherMajors == 1){
			BasicGraduateList major = new OracleBasicGraduateList() ;
			List majorList = major.getStudentMajors(teacherId);
			return majorList;
		}else{
			throw new PlatformException("��û�������վ��ʦָ��רҵ��Ϣ��Ȩ��");
		}
		
	}
}
