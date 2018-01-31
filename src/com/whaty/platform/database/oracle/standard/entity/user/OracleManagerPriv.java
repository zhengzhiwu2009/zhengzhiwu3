/*
 * OracleManagerPriv.java
 *
 * Created on 2005��4��6��, ����8:30
 */

package com.whaty.platform.database.oracle.standard.entity.user;

import java.sql.SQLException;
import java.util.ArrayList;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.user.ManagerPriv;

/**
 * 
 * @author Administrator
 */
public class OracleManagerPriv extends ManagerPriv {

	/** Creates a new instance of OracleManagerPriv */
	public OracleManagerPriv() {

		this.getSite = 1;
		this.getRecruitBatch = 1;
	}

	public OracleManagerPriv(java.lang.String id){
		this.getSite = 1;
		this.getRecruitBatch = 1;
		this.sso_id = id;

		dbpool db = new dbpool();
		MyResultSet rs = null;
//		String sql = "select a.site as site,a.major as major,a.grade as grade,a.edutype as edutype from entity_manager_info a,entity_manager_right b "
//				+ "where a.id=b.manager_id and a.id='" + id + "'";
		String sql = "select prs.fk_site_id from pe_manager m,pr_pri_manager_site  prs,sso_user u where u.id=m.id and prs.fk_sso_user_id=m.id and u.login_id = '"+id+"'";
		// System.out.println(sql);
		rs = db.executeQuery(sql);
		
		String sites = "";
		String majors = "";
		String grades = "";
		String edutypes = "";
		this.site = new ArrayList();
		try {
			
			while(rs.next()){
				sites = rs.getString("fk_site_id");
				site.add(sites);
			}
		} catch (SQLException e1) {
		}
		db.close(rs);
		
		if(site.isEmpty()){
			String q = "select id from pe_site";
			try{
				rs = db.executeQuery(q);
				while(rs.next()){
					sites = rs.getString("id");
					site.add(sites);
				}
			}catch(SQLException e){
			}finally{
				db.close(rs);
			}
		}
		
		sql = "select prs.fk_edutype_id from pe_manager m,pr_pri_manager_edutype  prs where prs.fk_sso_user_id=m.id and  m.id = '"+id+"'";
		this.eduType = new ArrayList();
		try{
			rs = db.executeQuery(sql);
			while(rs.next()){
				edutypes = rs.getString("fk_edutype_id");
				this.eduType.add(edutypes);
			}
		}catch(SQLException e){
		}finally{
			db.close(rs);
		}
		
		if(this.eduType.isEmpty()){
			sql = "select id from pe_edutype";
			try{
				rs = db.executeQuery(sql);
				while(rs.next()){
					edutypes = rs.getString("id");
					this.eduType.add(edutypes);
				}
			}catch(SQLException e){
			}finally{
				db.close(rs);
			}
		}
		
		
		sql = "select prs.fk_major_id from pe_manager m,pr_pri_manager_major  prs where  prs.fk_sso_user_id=m.id and  m.id = '"+id+"'";
		this.major = new ArrayList();
		try{
			rs = db.executeQuery(sql);
			while(rs.next()){
				majors = rs.getString("fk_major_id");
				this.major.add(majors);
			}
		}catch(SQLException e){
		}finally{
			db.close(rs);
		}
		
		if(this.major.isEmpty()){
			sql = "select id from pe_major";
			try{
				rs = db.executeQuery(sql);
				while(rs.next()){
					majors = rs.getString("id");
					this.major.add(majors);
				}
			}catch(SQLException e){
			}finally{
				db.close(rs);
			}
		}
		
		sql = "select prs.fk_grade_id from pe_manager m,pr_pri_manager_grade  prs where  prs.fk_sso_user_id=m.id and  m.id = '"+id+"'";
		this.grade = new ArrayList();
		try{
			rs = db.executeQuery(sql);
			while(rs.next()){
				grades = rs.getString("fk_grade_id");
				this.grade.add(grades);
			}
		}catch(SQLException e){
		}finally{
			db.close(rs);
		}
		
		if(this.grade.isEmpty()){
			try{
				sql = "select id from pe_grade";
				rs = db.executeQuery(sql);
				while(rs.next()){
					grades = rs.getString("id");
					this.grade.add(grades);
				}
			}catch(SQLException e){
			}finally{
				db.close(rs);
			}
		}
		
		String privStr = "|";
		String privSql = "select er.right_code from entity_manager_right g, entity_manager_info r,entity_right_info er,entity_moduleright_info emri,entity_moduleright_right emrr "
				+ "where g.manager_id = r.id and r.id='"
				+ id
				+ "' and g.right_id=emri.id and emri.id = emrr.moduleright_id and emrr.right_id = er.id order by er.id";
		try {
			rs = db.executeQuery(privSql);
			while (rs != null && rs.next()) {
				privStr += rs.getString("right_code") + "|";
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}

		/***********************************************************************
		 * ���Ź���Ȩ�� *
		 **********************************************************************/

		if (privStr.indexOf("|getNews|") >= 0) {
			this.getNews = 1;

		}

		if (privStr.indexOf("|addNews|") >= 0) {
			this.addNews = 1;

		}

		if (privStr.indexOf("|updateNews|") >= 0) {
			this.updateNews = 1;

		}

		if (privStr.indexOf("|deleteNews|") >= 0) {
			this.deleteNews = 1;

		}

		if (privStr.indexOf("|topNews|") >= 0) {
			this.topNews = 1;

		}
		if (privStr.indexOf("|lockNews|") >= 0) {
			this.lockNews = 1;

		}
		if (privStr.indexOf("|popNews|") >= 0) {
			this.popNews = 1;

		}
		if (privStr.indexOf("|confirmNews|") >= 0) {
			this.confirmNews = 1;

		}
		if (privStr.indexOf("|copyNews|") >= 0) {
			this.copyNews = 1;

		}
		if (privStr.indexOf("|getNewsType|") >= 0) {
			this.getNewsType = 1;

		}
		/***********************************************************************
		 * ͶƱ����Ȩ�� *
		 **********************************************************************/

		if (privStr.indexOf("|addVotePaper|") >= 0) {
			this.addVotePaper = 1;

		}
		if (privStr.indexOf("|getVotePaper|") >= 0) {
			this.getVotePaper = 1;

		}

		/***********************************************************************
		 * ���Թ���Ȩ�� *
		 **********************************************************************/

		if (privStr.indexOf("|getLeaveWord|") >= 0) {
			this.getLeaveWord = 1;

		}
		if (privStr.indexOf("|totalLeaveWord|") >= 0) {
			this.totalLeaveWord = 1;

		}
		/***********************************************************************
		 * ��Դ����Ȩ�� *
		 **********************************************************************/

		if (privStr.indexOf("|resourceManage|") >= 0) {
			this.resourceManage = 1;

		}
		if (privStr.indexOf("|resourceTypeManage|") >= 0) {
			this.resourceTypeManage = 1;

		}

		/***********************************************************************
		 * ����Ϣ����Ȩ�� *
		 **********************************************************************/

		// Site
		if (privStr.indexOf("|addSite|") >= 0) {
			this.addSite = 1;

		}

		if (privStr.indexOf("|deleteSite|") >= 0) {
			this.deleteSite = 1;

		}

		if (privStr.indexOf("|getSite|") >= 0) {
			this.getSite = 1;

		}

		if (privStr.indexOf("|updateSite|") >= 0) {
			this.updateSite = 1;

		}

		// Dep
		if (privStr.indexOf("|addDep|") >= 0) {
			this.addDep = 1;

		}

		if (privStr.indexOf("|deleteDep|") >= 0) {
			this.deleteDep = 1;

		}

		if (privStr.indexOf("|getDep|") >= 0) {
			this.getDep = 1;

		}

		if (privStr.indexOf("|updateDep|") >= 0) {
			this.updateDep = 1;

		}

		// Grade
		if (privStr.indexOf("|addGrade|") >= 0) {
			this.addGrade = 1;

		}

		if (privStr.indexOf("|deleteGrade|") >= 0) {
			this.deleteGrade = 1;

		}

		if (privStr.indexOf("|getGrade|") >= 0) {
			this.getGrade = 1;

		}

		if (privStr.indexOf("|updateGrade|") >= 0) {
			this.updateGrade = 1;

		}

		// EduType
		if (privStr.indexOf("|addEduType|") >= 0) {
			this.addEduType = 1;

		}

		if (privStr.indexOf("|deleteEduType|") >= 0) {
			this.deleteEduType = 1;

		}

		if (privStr.indexOf("|getEduType|") >= 0) {
			this.getEduType = 1;

		}

		if (privStr.indexOf("|updateEduType|") >= 0) {
			this.updateEduType = 1;

		}

		// Major
		if (privStr.indexOf("|addMajor|") >= 0) {
			this.addMajor = 1;

		}

		if (privStr.indexOf("|deleteMajor|") >= 0) {
			this.deleteMajor = 1;

		}

		if (privStr.indexOf("|getMajor|") >= 0) {
			this.getMajor = 1;

		}

		if (privStr.indexOf("|updateMajor|") >= 0) {
			this.updateMajor = 1;

		}

		// Template
		if (privStr.indexOf("|addTemplate|") >= 0) {
			this.addTemplate = 1;

		}

		if (privStr.indexOf("|deleteTemplate|") >= 0) {
			this.deleteTemplate = 1;

		}

		if (privStr.indexOf("|getTemplate|") >= 0) {
			this.getTemplate = 1;

		}

		if (privStr.indexOf("|updateTemplate|") >= 0) {
			this.updateTemplate = 1;

		}

		/***********************************************************************
		 * ��ʦ��Ϣ����Ȩ�� *
		 **********************************************************************/
		if (privStr.indexOf("|addTeacher|") >= 0) {
			this.addTeacher = 1;

		}

		if (privStr.indexOf("|addTeacherBatch|") >= 0) {
			this.addTeacherBatch = 1;

		}

		if (privStr.indexOf("|deleteTeacher|") >= 0) {
			this.deleteTeacher = 1;

		}

		if (privStr.indexOf("|getTeacher|") >= 0) {
			this.getTeacher = 1;

		}

		if (privStr.indexOf("|updateTeacher|") >= 0) {
			this.updateTeacher = 1;

		}

		/***********************************************************************
		 * ������ʦ��Ϣ����Ȩ�� *
		 **********************************************************************/
		if (privStr.indexOf("|addSiteTeacher|") >= 0) {
			this.addSiteTeacher = 1;

		}

		if (privStr.indexOf("|addSiteTeacherBatch|") >= 0) {
			this.addSiteTeacherBatch = 1;

		}

		if (privStr.indexOf("|deleteSiteTeacher|") >= 0) {
			this.deleteSiteTeacher = 1;

		}

		if (privStr.indexOf("|getSiteTeacher|") >= 0) {
			this.getSiteTeacher = 1;

		}

		if (privStr.indexOf("|updateSiteTeacher|") >= 0) {
			this.updateSiteTeacher = 1;

		}

		/***********************************************************************
		 * ѧ����Ϣ����Ȩ�� *
		 **********************************************************************/
		if (privStr.indexOf("|addStudent|") >= 0) {
			this.addStudent = 1;

		}

		if (privStr.indexOf("|updateStudentPwd|") >= 0) {
			this.updateStudentPwd = 1;

		}

		if (privStr.indexOf("|deleteStudent|") >= 0) {
			this.deleteStudent = 1;

		}

		if (privStr.indexOf("|getStudent|") >= 0) {
			this.getStudent = 1;

		}

		if (privStr.indexOf("|updateStudent|") >= 0) {
			this.updateStudent = 1;

		}

		if (privStr.indexOf("|addStudentBatch|") >= 0) {
			this.addStudentBatch = 1;

		}

		if (privStr.indexOf("|deleteStudentBatch|") >= 0) {
			this.deleteStudentBatch = 1;

		}

		if (privStr.indexOf("|uploadImage|") >= 0) {
			this.uploadImage = 1;

		}

		/***********************************************************************
		 * ��ѧ��Ϣ����Ȩ�� *
		 **********************************************************************/
		// Course
		if (privStr.indexOf("|addCourse|") >= 0) {
			this.addCourse = 1;

		}

		if (privStr.indexOf("|addCourseBatch|") >= 0) {
			this.addCourseBatch = 1;

		}

		if (privStr.indexOf("|deleteCourse|") >= 0) {
			this.deleteCourse = 1;

		}

		if (privStr.indexOf("|getCourse|") >= 0) {
			this.getCourse = 1;

		}

		if (privStr.indexOf("|updateCourse|") >= 0) {
			this.updateCourse = 1;

		}

		// CourseType
		if (privStr.indexOf("|addCourseType|") >= 0) {
			this.addCourseType = 1;

		}

		if (privStr.indexOf("|deleteCourseType|") >= 0) {
			this.deleteCourseType = 1;

		}

		if (privStr.indexOf("|getCourseType|") >= 0) {
			this.getCourseType = 1;

		}

		if (privStr.indexOf("|updateCourseType|") >= 0) {
			this.updateCourseType = 1;

		}

		// Courseware
		if (privStr.indexOf("|addCourseware|") >= 0) {
			this.addCourseware = 1;

		}

		if (privStr.indexOf("|deleteCourseware|") >= 0) {
			this.deleteCourseware = 1;

		}

		if (privStr.indexOf("|getCourseware|") >= 0) {
			this.getCourseware = 1;

		}

		if (privStr.indexOf("|updateCourseware|") >= 0) {
			this.updateCourseware = 1;

		}

		// CwareType
		if (privStr.indexOf("|addCwareType|") >= 0) {
			this.addCwareType = 1;

		}

		if (privStr.indexOf("|deleteCwareType|") >= 0) {
			this.deleteCwareType = 1;

		}

		if (privStr.indexOf("|getCwareType|") >= 0) {
			this.getCwareType = 1;

		}

		if (privStr.indexOf("|updateCwareType|") >= 0) {
			this.updateCwareType = 1;

		}
		// Semester
		if (privStr.indexOf("|addSemester|") >= 0) {
			this.addSemester = 1;

		}

		if (privStr.indexOf("|activeSemester|") >= 0) {
			this.activeSemester = 1;

		}

		if (privStr.indexOf("|deleteSemester|") >= 0) {
			this.deleteSemester = 1;

		}

		if (privStr.indexOf("|getSemester|") >= 0) {
			this.getSemester = 1;

		}

		if (privStr.indexOf("|updateSemester|") >= 0) {
			this.updateSemester = 1;

		}

		// changeStatus
		if (privStr.indexOf("|changeStatus|") >= 0) {
			this.changeStatus = 1;

		}

		if (privStr.indexOf("|cancelChangeStatus|") >= 0) {
			this.cancelChangeStatus = 1;

		}

		/***********************************************************************
		 * ����ֹ���Ȩ�� *
		 **********************************************************************/
		// RecruitBatch
		if (privStr.indexOf("|addRecruitBatch|") >= 0) {
			this.addRecruitBatch = 1;

		}

		if (privStr.indexOf("|deleteRecruitBatch|") >= 0) {
			this.deleteRecruitBatch = 1;

		}

		if (privStr.indexOf("|getRecruitBatch|") >= 0) {
			this.getRecruitBatch = 1;

		}

		if (privStr.indexOf("|updateRecruitBatch|") >= 0) {
			this.updateRecruitBatch = 1;

		}

		if (privStr.indexOf("|updateRecruitBatchStatus|") >= 0) {
			this.updateRecruitBatchStatus = 1;

		}

		if (privStr.indexOf("|getRecruitMajors|") >= 0) {
			this.getRecruitMajors = 1;

		}

		if (privStr.indexOf("|deleteRecruitBatchMajor|") >= 0) {
			this.deleteRecruitBatchMajor = 1;

		}

		if (privStr.indexOf("|addRecruitBatchMajor|") >= 0) {
			this.addRecruitBatchMajor = 1;

		}

		// RecruitPlan
		if (privStr.indexOf("|getRecruitPlan|") >= 0) {
			this.getRecruitPlan = 1;

		}

		if (privStr.indexOf("|updatePlanStatus|") >= 0) {
			this.updatePlanStatus = 1;

		}

		// RecruitCourse
		if (privStr.indexOf("|addRecruitCourse|") >= 0) {
			this.addRecruitCourse = 1;

		}

		if (privStr.indexOf("|deleteRecruitCourse|") >= 0) {
			this.deleteRecruitCourse = 1;

		}

		if (privStr.indexOf("|getRecruitCourse|") >= 0) {
			this.getRecruitCourse = 1;

		}

		if (privStr.indexOf("|updateRecruitCourse|") >= 0) {
			this.updateRecruitCourse = 1;

		}

		if (privStr.indexOf("|getRecruitCourseSort|") >= 0) {
			this.getRecruitCourseSort = 1;

		}

		if (privStr.indexOf("|setRecruitCourseSort|") >= 0) {
			this.setRecruitCourseSort = 1;

		}

		// RecruitSort
		if (privStr.indexOf("|addRecruitSort|") >= 0) {
			this.addRecruitSort = 1;

		}

		if (privStr.indexOf("|deleteRecruitSort|") >= 0) {
			this.deleteRecruitSort = 1;

		}

		if (privStr.indexOf("|getRecruitSort|") >= 0) {
			this.getRecruitSort = 1;

		}

		if (privStr.indexOf("|updateRecruitSort|") >= 0) {
			this.updateRecruitSort = 1;

		}

		if (privStr.indexOf("|getRecruitSortMajor|") >= 0) {
			this.getRecruitSortMajor = 1;

		}

		if (privStr.indexOf("|getRecruitMajorSort|") >= 0) {
			this.getRecruitMajorSort = 1;

		}

		if (privStr.indexOf("|setRecruitSortMajor|") >= 0) {
			this.setRecruitSortMajor = 1;

		}

		if (privStr.indexOf("|getRecruitSortCourse|") >= 0) {
			this.getRecruitSortCourse = 1;

		}

		if (privStr.indexOf("|setRecruitSortCourse|") >= 0) {
			this.setRecruitSortCourse = 1;

		}

		// RecruitTestCourses
		if (privStr.indexOf("|addRecruitTestCourse|") >= 0) {
			this.addRecruitTestCourse = 1;

		}

		if (privStr.indexOf("|deleteRecruitTestCourse|") >= 0) {
			this.deleteRecruitTestCourse = 1;

		}

		if (privStr.indexOf("|getRecruitTestCourse|") >= 0) {
			this.getRecruitTestCourse = 1;

		}

		if (privStr.indexOf("|getUnRecruitTestCourse|") >= 0) {
			this.getUnRecruitTestCourse = 1;

		}

		if (privStr.indexOf("|updateRecruitTestCourse|") >= 0) {
			this.updateRecruitTestCourse = 1;

		}

		// RecruitStudent
		if (privStr.indexOf("|addRecruitStudent|") >= 0) {
			this.addRecruitStudent = 1;

		}

		if (privStr.indexOf("|getRecruitStudent|") >= 0) {
			this.getRecruitStudent = 1;

		}

		if (privStr.indexOf("|updateRecruitStudent|") >= 0) {
			this.updateRecruitStudent = 1;

		}

		if (privStr.indexOf("|getSignStatistic|") >= 0) {
			this.getSignStatistic = 1;

		}

		// RecruitTestStudent
		if (privStr.indexOf("|getRecruitTestStudent|") >= 0) {
			this.getRecruitTestStudent = 1;

		}

		// Score
		if (privStr.indexOf("|releaseStudentScore|") >= 0) {
			this.releaseStudentScore = 1;

		}

		if (privStr.indexOf("|updateRecruitStudentScore|") >= 0) {
			this.updateRecruitStudentScore = 1;

		}

		if (privStr.indexOf("|updateRecruitStudentScoreBatch|") >= 0) {
			this.updateRecruitStudentScoreBatch = 1;

		}

		if (privStr.indexOf("|getRecruitScoreStudent|") >= 0) {
			this.getRecruitScoreStudent = 1;

		}

		// Pass
		if (privStr.indexOf("|getPassstastic|") >= 0) {
			this.getPassstastic = 1;

		}

		if (privStr.indexOf("|getTotalStu|") >= 0) {
			this.getTotalStu = 1;

		}

		if (privStr.indexOf("|releaseStudentPass|") >= 0) {
			this.releaseStudentPass = 1;

		}

		if (privStr.indexOf("|updateRecruitStudentPassstatus|") >= 0) {
			this.updateRecruitStudentPassstatus = 1;

		}

		if (privStr.indexOf("|setStudentCon|") >= 0) {
			this.setStudentCon = 1;

		}

		if (privStr.indexOf("|getRecruitStat|") >= 0) {
			this.getRecruitStat = 1;

		}

		if (privStr.indexOf("|getPassRecruitStudent|") >= 0) {
			this.getPassRecruitStudent = 1;

		}

		if (privStr.indexOf("|getRegStudent|") >= 0) {
			this.getRegStudent = 1;

		}

		// NoExam
		if (privStr.indexOf("|addRecruitNoExamCondition|") >= 0) {
			this.addRecruitNoExamCondition = 1;

		}

		if (privStr.indexOf("|getRecruitNoExamCondition|") >= 0) {
			this.getRecruitNoExamCondition = 1;

		}

		if (privStr.indexOf("|updateRecruitNoExamCondition|") >= 0) {
			this.updateRecruitNoExamCondition = 1;

		}

		if (privStr.indexOf("|deleteRecruitNoExamCondition|") >= 0) {
			this.deleteRecruitNoExamCondition = 1;

		}

		if (privStr.indexOf("|getFreeRecruitStudent|") >= 0) {
			this.getFreeRecruitStudent = 1;

		}

		if (privStr.indexOf("|getConfirmFreeStudent|") >= 0) {
			this.getConfirmFreeStudent = 1;

		}

		if (privStr.indexOf("|getUnConfirmFreeStudent|") >= 0) {
			this.getUnConfirmFreeStudent = 1;

		}
		// ѧϰ�����춯
		if (privStr.indexOf("|changeSite|") >= 0) {
			this.changeSite = 1;

		}

		/***********************************************************************
		 * ������̹���Ȩ�� *
		 **********************************************************************/
		// elective
		if (privStr.indexOf("|electiveStudent|") >= 0) {
			this.electiveStudent = 1;

		}

		if (privStr.indexOf("|electiveBatchBySite|") >= 0) {
			this.electiveBatchBySite = 1;

		}

		if (privStr.indexOf("|electiveBatchByStudent|") >= 0) {
			this.electiveBatchByStudent = 1;

		}

		if (privStr.indexOf("|electiveSingle|") >= 0) {
			this.electiveSingle = 1;

		}

		if (privStr.indexOf("|downloadElectiveInfo|") >= 0) {
			this.downloadElectiveInfo = 1;

		}

		if (privStr.indexOf("|uploadElectiveInfo|") >= 0) {
			this.uploadElectiveInfo = 1;

		}

		if (privStr.indexOf("|confirmElectiveInfo|") >= 0) {
			this.confirmElectiveInfo = 1;

		}

		// register
		if (privStr.indexOf("|cancelRegisterSingle|") >= 0) {
			this.cancelRegisterSingle = 1;

		}

		if (privStr.indexOf("|getRegisterStudent|") >= 0) {
			this.getRegisterStudent = 1;

		}

		if (privStr.indexOf("|registerSingle|") >= 0) {
			this.registerSingle = 1;

		}

		if (privStr.indexOf("|registerBatch|") >= 0) {
			this.registerBatch = 1;

		}

		if (privStr.indexOf("|cancelRegisterBatch|") >= 0) {
			this.cancelRegisterBatch = 1;

		}
		if (privStr.indexOf("|setRegisterDateTime|") >= 0) {
			this.setRegisterDateTime = 1;

		}

		// openCourse
		if (privStr.indexOf("|openCourseBySemester|") >= 0) {
			this.openCourseBySemester = 1;

		}

		if (privStr.indexOf("|cancelOpenCourseBySemester|") >= 0) {
			this.cancelOpenCourseBySemester = 1;

		}

		if (privStr.indexOf("|getOpenCoursesBySemester|") >= 0) {
			this.getOpenCoursesBySemester = 1;

		}

		// TeachPlan
		if (privStr.indexOf("|addTeachPlan|") >= 0) {
			this.addTeachPlan = 1;

		}

		if (privStr.indexOf("|deleteTeachPlan|") >= 0) {
			this.deleteTeachPlan = 1;

		}

		if (privStr.indexOf("|getTeachPlan|") >= 0) {
			this.getTeachPlan = 1;

		}

		if (privStr.indexOf("|updateTeachPlan|") >= 0) {
			this.updateTeachPlan = 1;

		}

		// TeachPlanCourse
		if (privStr.indexOf("|addTeachPlanCourse|") >= 0) {
			this.addTeachPlanCourse = 1;

		}

		if (privStr.indexOf("|deleteTeachPlanCourse|") >= 0) {
			this.deleteTeachPlanCourse = 1;

		}

		if (privStr.indexOf("|getTeachPlanCourse|") >= 0) {
			this.getTeachPlanCourse = 1;

		}

		if (privStr.indexOf("|updateTeachPlanCourse|") >= 0) {
			this.updateTeachPlanCourse = 1;

		}

		// SingleTeachPlanCourse
		if (privStr.indexOf("|addSingleTeachPlanCourse|") >= 0) {
			this.addSingleTeachPlanCourse = 1;

		}

		if (privStr.indexOf("|deleteSingleTeachPlanCourse|") >= 0) {
			this.deleteSingleTeachPlanCourse = 1;

		}

		if (privStr.indexOf("|getSingleTeachPlanCourse|") >= 0) {
			this.getSingleTeachPlanCourse = 1;

		}

		// ExecutePlan
		if (privStr.indexOf("|addExecutePlan|") >= 0) {
			this.addExecutePlan = 1;

		}

		if (privStr.indexOf("|deleteExecutePlan|") >= 0) {
			this.deleteExecutePlan = 1;

		}

		if (privStr.indexOf("|getExecutePlan|") >= 0) {
			this.getExecutePlan = 1;

		}

		if (privStr.indexOf("|updateExecutePlan|") >= 0) {
			this.updateExecutePlan = 1;

		}

		// ExecutePlanCourse
		if (privStr.indexOf("|addExecutePlanCourse|") >= 0) {
			this.addExecutePlanCourse = 1;

		}

		if (privStr.indexOf("|deleteExecutePlanCourse|") >= 0) {
			this.deleteExecutePlanCourse = 1;

		}

		if (privStr.indexOf("|getExecutePlanCourse|") >= 0) {
			this.getExecutePlanCourse = 1;

		}

		if (privStr.indexOf("|addExecutePlanCourseGroup|") >= 0) {
			this.addExecutePlanCourseGroup = 1;

		}

		if (privStr.indexOf("|getExecutePlanCourseGroup|") >= 0) {
			this.getExecutePlanCourseGroup = 1;

		}

		// Assistance
		if (privStr.indexOf("|addAssistance|") >= 0) {
			this.addAssistance = 1;

		}

		if (privStr.indexOf("|deleteAssistance|") >= 0) {
			this.deleteAssistance = 1;

		}

		if (privStr.indexOf("|updateAssistance|") >= 0) {
			this.updateAssistance = 1;

		}

		if (privStr.indexOf("|releaseAssistance|") >= 0) {
			this.releaseAssistance = 1;

		}

		// Course TeachClass
		if (privStr.indexOf("|appointTeacherForCourse|") >= 0) {
			this.appointTeacherForCourse = 1;

		}

		if (privStr.indexOf("|addTeachClass|") >= 0) {
			this.addTeachClass = 1;

		}

		if (privStr.indexOf("|deleteTeachClass|") >= 0) {
			this.deleteTeachClass = 1;

		}

		if (privStr.indexOf("|getTeachClass|") >= 0) {
			this.getTeachClass = 1;

		}

		// Graduate
		if (privStr.indexOf("|addGraduateCondition|") >= 0) {
			this.addGraduateCondition = 1;

		}

		if (privStr.indexOf("|deleteGraduateCondition|") >= 0) {
			this.deleteGraduateCondition = 1;

		}

		if (privStr.indexOf("|getGraduateCondition|") >= 0) {
			this.getGraduateCondition = 1;

		}

		if (privStr.indexOf("|getGraduatedStudent|") >= 0) {
			this.getGraduatedStudent = 1;

		}

		if (privStr.indexOf("|checkGraduated|") >= 0) {
			this.checkGraduated = 1;

		}

		if (privStr.indexOf("|setGraduated|") >= 0) {
			this.setGraduated = 1;

		}

		if (privStr.indexOf("|cancleGraduated|") >= 0) {
			this.cancleGraduated = 1;

		}

		if (privStr.indexOf("|setGraduateNo|") >= 0) {
			this.setGraduateNo = 1;

		}

		// Degree
		if (privStr.indexOf("|addDegreeCondition|") >= 0) {
			this.addDegreeCondition = 1;

		}

		if (privStr.indexOf("|deleteDegreeCondition|") >= 0) {
			this.deleteDegreeCondition = 1;

		}

		if (privStr.indexOf("|getDegreeCondition|") >= 0) {
			this.getDegreeCondition = 1;

		}

		if (privStr.indexOf("|getDegreedStudent|") >= 0) {
			this.getDegreedStudent = 1;

		}

		if (privStr.indexOf("|checkDegreed|") >= 0) {
			this.checkDegreed = 1;

		}

		if (privStr.indexOf("|setDegreed|") >= 0) {
			this.setDegreed = 1;

		}

		if (privStr.indexOf("|cancleDegreed|") >= 0) {
			this.cancleDegreed = 1;

		}

		if (privStr.indexOf("|setDegreeScore|") >= 0) {
			this.setDegreeScore = 1;

		}

		if (privStr.indexOf("|setDegreeScoreRelease|") >= 0) {
			this.setDegreeScoreRelease = 1;

		}
		// ѧϰ����

		if (privStr.indexOf("|mockTeacher|") >= 0) {
			this.mockTeacher = 1;

		}

		if (privStr.indexOf("|mockStudent|") >= 0) {
			this.mockStudent = 1;

		}
		// ֤�����

		if (privStr.indexOf("|addCerificateNO|") >= 0) {
			this.addCerificateNO = 1;

		}
		/***********************************************************************
		 * ������Ϣ����Ȩ�� *
		 **********************************************************************/
		// TestSite
		if (privStr.indexOf("|addTestSite|") >= 0) {
			this.addTestSite = 1;

		}

		if (privStr.indexOf("|deleteTestSite|") >= 0) {
			this.deleteTestSite = 1;

		}

		if (privStr.indexOf("|getTestSite|") >= 0) {
			this.getTestSite = 1;

		}

		if (privStr.indexOf("|updateTestSite|") >= 0) {
			this.updateTestSite = 1;

		}

		if (privStr.indexOf("|assignTestSite|") >= 0) {
			this.assignTestSite = 1;

		}

		// TestRoom
		if (privStr.indexOf("|addTestRoom|") >= 0) {
			this.addTestRoom = 1;

		}

		if (privStr.indexOf("|deleteTestRoom|") >= 0) {
			this.deleteTestRoom = 1;

		}

		if (privStr.indexOf("|getTestRoom|") >= 0) {
			this.getTestRoom = 1;

		}

		if (privStr.indexOf("|updateTestRoom|") >= 0) {
			this.updateTestRoom = 1;

		}

		if (privStr.indexOf("|getTestRoomTongji|") >= 0) {
			this.getTestRoomTongji = 1;

		}

		// Student
		if (privStr.indexOf("|getNormalStudents|") >= 0) {
			this.getNormalStudents = 1;

		}

		if (privStr.indexOf("|getEdutypeMajorTestDesk|") >= 0) {
			this.getEdutypeMajorTestDesk = 1;

		}

		// UniteExamCourse
		if (privStr.indexOf("|addUniteExamCourse|") >= 0) {
			this.addUniteExamCourse = 1;

		}

		if (privStr.indexOf("|addBatchUniteExamScore|") >= 0) {
			this.addBatchUniteExamScore = 1;

		}

		if (privStr.indexOf("|delUniteExamCourse|") >= 0) {
			this.delUniteExamCourse = 1;

		}

		if (privStr.indexOf("|delBatchUniteExamCourse|") >= 0) {
			this.delBatchUniteExamCourse = 1;

		}

		if (privStr.indexOf("|getUniteExamCourses|") >= 0) {
			this.getUniteExamCourses = 1;

		}

		if (privStr.indexOf("|getUniteExamScores|") >= 0) {
			this.getUniteExamScores = 1;

		}

		if (privStr.indexOf("|updateUniteExamCourse|") >= 0) {
			this.updateUniteExamCourse = 1;

		}
		// �������

		if (privStr.indexOf("|manageBasicSequence|") >= 0) {
			this.manageBasicSequence = 1;

		}

		if (privStr.indexOf("|manageExamBatch|") >= 0) {
			this.manageExamBatch = 1;

		}
		if (privStr.indexOf("|manageExamSequence|") >= 0) {
			this.manageExamSequence = 1;

		}

		if (privStr.indexOf("|importExamData|") >= 0) {
			this.importExamData = 1;

		}
		if (privStr.indexOf("|getExamCourse|") >= 0) {
			this.getExamCourse = 1;

		}

		if (privStr.indexOf("|getExamStudent|") >= 0) {
			this.getExamStudent = 1;

		}

		// ���԰���

		if (privStr.indexOf("|totalExamRoom|") >= 0) {
			this.totalExamRoom = 1;

		}

		if (privStr.indexOf("|allotExamRoom|") >= 0) {
			this.allotExamRoom = 1;

		}
		if (privStr.indexOf("|examRoomSign|") >= 0) {
			this.examRoomSign = 1;

		}

		if (privStr.indexOf("|arrangeExam|") >= 0) {
			this.arrangeExam = 1;

		}
		if (privStr.indexOf("|examPaper|") >= 0) {
			this.examPaper = 1;

		}

		if (privStr.indexOf("|totalExamStudent|") >= 0) {
			this.totalExamStudent = 1;

		}

		/***********************************************************************
		 * ������Ϣ���� *
		 **********************************************************************/
		if (privStr.indexOf("|updatePwd|") >= 0) {
			this.updatePwd = 1;

		}

		/***********************************************************************
		 * �̲Ĺ��� *
		 **********************************************************************/
		if (privStr.indexOf("|addTeachBook|") >= 0) {
			this.addTeachBook = 1;

		}

		if (privStr.indexOf("|deleteTeachBook|") >= 0) {
			this.deleteTeachBook = 1;

		}

		if (privStr.indexOf("|getTeachBook|") >= 0) {
			this.getTeachBook = 1;

		}

		if (privStr.indexOf("|updateTeachBook|") >= 0) {
			this.updateTeachBook = 1;

		}

		if (privStr.indexOf("|appointTeachbookForCourse|") >= 0) {
			this.appointTeachbookForCourse = 1;

		}

		if (privStr.indexOf("|appointCourseForTeachbook|") >= 0) {
			this.appointCourseForTeachbook = 1;

		}

		if (privStr.indexOf("|viewTeachbookForCourse|") >= 0) {
			this.viewTeachbookForCourse = 1;

		}

		/***********************************************************************
		 * ���ù��� *
		 **********************************************************************/
		if (privStr.indexOf("|getConfirmOrder|") >= 0) {
			this.getConfirmOrder = 1;
		}

		if (privStr.indexOf("|getReConfirmOrder|") >= 0) {
			this.getReConfirmOrder = 1;
		}

		if (privStr.indexOf("|getSiteFeeStat|") >= 0) {
			this.getSiteFeeStat = 1;
		}

		if (privStr.indexOf("|getStuFeeReturnApply|") >= 0) {
			this.getStuFeeReturnApply = 1;
		}

		if (privStr.indexOf("|getStuOtherFeeByTime|") >= 0) {
			this.getStuOtherFeeByTime = 1;
		}

		if (privStr.indexOf("|getStuOtherFee|") >= 0) {
			this.getStuOtherFee = 1;
		}

		if (privStr.indexOf("|addFeeStandard|") >= 0) {
			this.addFeeStandard = 1;
		}

		if (privStr.indexOf("|getFeeStandard|") >= 0) {
			this.getFeeStandard = 1;
		}

		if (privStr.indexOf("|addFee|") >= 0) {
			this.addFee = 1;
		}

		if (privStr.indexOf("|getFee|") >= 0) {
			this.getFee = 1;
		}

		if (privStr.indexOf("|getFeeByTime|") >= 0) {
			this.getFeeByTime = 1;
		}

		/***********************************************************************
		 * ���Ź��� *
		 **********************************************************************/
		if (privStr.indexOf("|sendSms|") >= 0) {
			this.sendSms = 1;
		}

		if (privStr.indexOf("|getSms|") >= 0) {
			this.getSms = 1;
		}

		if (privStr.indexOf("|updateSms|") >= 0) {
			this.updateSms = 1;
		}

		if (privStr.indexOf("|getStuOtherFeeByTime|") >= 0) {
			this.getStuOtherFeeByTime = 1;
		}

		if (privStr.indexOf("|deleteSms|") >= 0) {
			this.deleteSms = 1;
		}

		if (privStr.indexOf("|checkSms|") >= 0) {
			this.checkSms = 1;
		}

		if (privStr.indexOf("|addSms|") >= 0) {
			this.addSms = 1;
		}

		if (privStr.indexOf("|batchImportMobiles|") >= 0) {
			this.batchImportMobiles = 1;
		}
		if (privStr.indexOf("|manageSmsSystemPoint|") >= 0) {
			this.manageSmsSystemPoint = 1;
		}

		/***********************************************************************
		 * ��Ƭ���� *
		 **********************************************************************/
		if (privStr.indexOf("|uploadIdCard|") >= 0) {
			this.uploadIdCard = 1;
		}
		if (privStr.indexOf("|uploadGraduateCard|") >= 0) {
			this.uploadGraduateCard = 1;
		}
		if (privStr.indexOf("|uploadImage|") >= 0) {
			this.uploadImage = 1;
		}
	}

	public String getSiteList() {
		String str = "";
		if (site != null && site.size() > 0) {
			for (int i = 0; i < site.size(); i++) {
				str += ",'" + (String) site.get(i) + "'";
			}
			str = str.substring(1);

		} else
			str = " ";
		return str;
	}

	public String getMajorList() {
		String str = "";
		if (major != null && major.size() > 0) {
			for (int i = 0; i < major.size(); i++) {
				str += ",'" + major.get(i) + "'";
			}
			str = str.substring(1);
		} else
			str = " ";
		return str;
	}

	public String getGradeList() {
		String str = "";
		if (grade != null && grade.size() > 0) {
			for (int i = 0; i < grade.size(); i++) {
				str += ",'" + grade.get(i) + "'";
			}
			str = str.substring(1);
		} else
			str = " ";
		return str;
	}

	public String getEduTypeList() {
		String str = "";
		if (eduType != null && eduType.size() > 0) {
			for (int i = 0; i < eduType.size(); i++) {
				str += ",'" + eduType.get(i) + "'";
			}
			str = str.substring(1);
		} else
			str = " ";
		return str;
	}

}
