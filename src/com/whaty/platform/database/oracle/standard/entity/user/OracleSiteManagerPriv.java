package com.whaty.platform.database.oracle.standard.entity.user;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.user.SiteManagerPriv;

public class OracleSiteManagerPriv extends SiteManagerPriv {

	
	public OracleSiteManagerPriv() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleSiteManagerPriv(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select m.id,m.name,u.login_id,m.fk_site_id, m.group_id from pe_sitemanager m,sso_user u where m.id=u.id and u.login_id = '"
				+ id + "'";
		String def_right_sql = "select eri.id,eri.right_code from entity_office_info eoi" +
				",entity_modelgroup_info emi,entity_right_info eri ,entity_moduleright_info emri,entity_moduleright_right emrr " +
				"where eoi.type = 'submanager' and emi.status='1111' and emri.status = '1' and eri.status='1111'" +
				" and eoi.id = emi.office_id and emri.modelgroup_id = emi.id and emri.id = emrr.moduleright_id and emrr.right_id = eri.id order by eri.id";//分站初使管理员默认拥有学习中心所有权限
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setManagerId(rs.getString("id"));
				this.setSso_id(rs.getString("id"));
				this.setSite_id(rs.getString("fk_site_id"));
				String group_id = rs.getString("group_id");
				if ("0".equals(group_id)) {
					this.setGourp_id(group_id);
					this.addSiteAdmin = 1;
				} else
					this.setGourp_id("1");
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		
		
		
		
		
//		db = new dbpool();
		String privStr = "|";
//		String privSql = "select er.right_code from entity_manager_right g, entity_sitemanager_info r,entity_right_info er,entity_moduleright_info emi,entity_moduleright_right emr "
//				+ "where g.manager_id = r.id and r.id='"
//				+ id
//				+ "' and g.right_id=emi.id and emi.id = emr.moduleright_id and emr.right_id = er.id order by er.id";
//		
//		if(this.getGourp_id().equals("0"))//如果管理员为分站初使管理员，赋予最大权限
//			privSql = def_right_sql;
//		
//		try {
//			rs = db.executeQuery(privSql);
//			while (rs != null && rs.next()) {
//				privStr += rs.getString("right_code") + "|";
//			}
//		} catch (SQLException e) {
//			
//		} finally {
//			db.close(rs);
//			db = null;
//		}

		/***********************************************************************
		 * 新闻管理权限 *
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

		if (privStr.indexOf("|putTopNews|") >= 0) {
			this.putTopNews = 1;

		}
		if (privStr.indexOf("|addNewsType|") >= 0) {
			this.addNewsType = 1;

		}
		if (privStr.indexOf("|updateNewsType|") >= 0) {
			this.updateNewsType = 1;

		}
		if (privStr.indexOf("|deleteNewsType|") >= 0) {
			this.deleteNewsType = 1;

		}
		if (privStr.indexOf("|setNewsTypeManagers|") >= 0) {
			this.setNewsTypeManagers = 1;

		}
		if (privStr.indexOf("|getNewsTypeManagers|") >= 0) {
			this.getNewsTypeManagers = 1;

		}

		/***********************************************************************
		 * 重要信息管理权限 *
		 **********************************************************************/
		if (privStr.indexOf("|getImpnote|") >= 0) {
			this.getImpnote = 1;

		}
		if (privStr.indexOf("|addImpnote|") >= 0) {
			this.addImpnote = 1;

		}
		if (privStr.indexOf("|updateImpnote|") >= 0) {
			this.updateImpnote = 1;

		}
		if (privStr.indexOf("|deleteImpnote|") >= 0) {
			this.deleteImpnote = 1;

		}
		if (privStr.indexOf("|activeImpnote|") >= 0) {
			this.activeImpnote = 1;

		}

		/***********************************************************************
		 * 基本信息管理权限 *
		 **********************************************************************/

		if (privStr.indexOf("|getDep|") >= 0) {
			this.getDep = 1;

		}

		if (privStr.indexOf("|getEduType|") >= 0) {
			this.getEduType = 1;

		}
		if (privStr.indexOf("|getGrade|") >= 0) {
			this.getGrade = 1;

		}
		if (privStr.indexOf("|getMajor|") >= 0) {
			this.getMajor = 1;

		}
		if (privStr.indexOf("|getSite|") >= 0) {
			this.getSite = 1;

		}
		if (privStr.indexOf("|getSiteStudents|") >= 0) {
			this.getSiteStudents = 1;

		}
		if (privStr.indexOf("|getTemplate|") >= 0) {
			this.getTemplate = 1;

		}
		if (privStr.indexOf("|addDep|") >= 0) {
			this.addDep = 1;

		}
		if (privStr.indexOf("|addEduType|") >= 0) {
			this.addEduType = 1;

		}
		if (privStr.indexOf("|addGrade|") >= 0) {
			this.addGrade = 1;

		}
		if (privStr.indexOf("|addMajor|") >= 0) {
			this.addMajor = 1;

		}
		if (privStr.indexOf("|addSite|") >= 0) {
			this.addSite = 1;

		}
		if (privStr.indexOf("|addTemplate|") >= 0) {
			this.addTemplate = 1;

		}
		if (privStr.indexOf("|updateDep|") >= 0) {
			this.updateDep = 1;

		}
		if (privStr.indexOf("|updateEduType|") >= 0) {
			this.updateEduType = 1;

		}
		if (privStr.indexOf("|updateGrade|") >= 0) {
			this.updateGrade = 1;

		}
		if (privStr.indexOf("|updateMajor|") >= 0) {
			this.updateMajor = 1;

		}
		if (privStr.indexOf("|updateSite|") >= 0) {
			this.updateSite = 1;

		}
		if (privStr.indexOf("|updateTemplate|") >= 0) {
			this.updateTemplate = 1;

		}
		if (privStr.indexOf("|deleteDep|") >= 0) {
			this.deleteDep = 1;

		}

		if (privStr.indexOf("|deleteEduType|") >= 0) {
			this.deleteEduType = 1;

		}
		if (privStr.indexOf("|deleteGrade|") >= 0) {
			this.deleteGrade = 1;

		}
		if (privStr.indexOf("|deleteMajor|") >= 0) {
			this.deleteMajor = 1;

		}
		if (privStr.indexOf("|deleteSite|") >= 0) {
			this.deleteSite = 1;

		}
		if (privStr.indexOf("|deleteTemplate|") >= 0) {
			this.deleteTemplate = 1;

		}
		/***********************************************************************
		 * 人员信息管理权限 *
		 **********************************************************************/

		if (privStr.indexOf("|getTeacher|") >= 0) {
			this.getTeacher = 1;

		}
		if (privStr.indexOf("|addTeacher|") >= 0) {
			this.addTeacher = 1;

		}
		if (privStr.indexOf("|getSiteTeacher|") >= 0) {
			this.getSiteTeacher = 1;

		}
		if (privStr.indexOf("|addSiteTeacher|") >= 0) {
			this.addSiteTeacher = 1;

		}
		if (privStr.indexOf("|updateSiteTeacher|") >= 0) {
			this.updateSiteTeacher = 1;

		}
		if (privStr.indexOf("|deleteSiteTeacher|") >= 0) {
			this.deleteSiteTeacher = 1;

		}
		if (privStr.indexOf("|applySiteTeacherToCourse|") >= 0) {
			this.applySiteTeacherToCourse = 1;

		}

		if (privStr.indexOf("|updateTeacher|") >= 0) {
			this.updateTeacher = 1;

		}
		if (privStr.indexOf("|deleteTeacher|") >= 0) {
			this.deleteTeacher = 1;

		}

		if (privStr.indexOf("|getStudent|") >= 0) {
			this.getStudent = 1;

		}
		if (privStr.indexOf("|addStudent|") >= 0) {
			this.addStudent = 1;

		}
		if (privStr.indexOf("|updateStudent|") >= 0) {
			this.updateStudent = 1;

		}
		if (privStr.indexOf("|deleteStudent|") >= 0) {
			this.deleteStudent = 1;

		}
		if (privStr.indexOf("|queryRegDate|") >= 0) {
			this.queryRegDate = 1;

		}
		if (privStr.indexOf("|getRegStudents|") >= 0) {
			this.getRegStudents = 1;

		}
		if (privStr.indexOf("|getRegisterStat|") >= 0) {
			this.getRegisterStat = 1;

		}
		if (privStr.indexOf("|getStudentByEdu|") >= 0) {
			this.getStudentByEdu = 1;

		}
		if (privStr.indexOf("|checkChangeStudent|") >= 0) {
			this.checkChangeStudent = 1;

		}
		if (privStr.indexOf("|listChangeStudent|") >= 0) {
			this.listChangeStudent = 1;

		}
		if (privStr.indexOf("|listSuspendStudent|") >= 0) {
			this.listSuspendStudent = 1;

		}
		if (privStr.indexOf("|listLeftStudent|") >= 0) {
			this.listLeftStudent = 1;

		}
		if (privStr.indexOf("|displayScoreUploadDate|") >= 0) {
			this.displayScoreUploadDate = 1;

		}
		if (privStr.indexOf("|uploadHomeWorkScore|") >= 0) {
			this.uploadHomeWorkScore = 1;

		}
		if (privStr.indexOf("|uploadExperimentScore|") >= 0) {
			this.uploadExperimentScore = 1;

		}
		if (privStr.indexOf("|selectStudentExamScore|") >= 0) {
			this.selectStudentExamScore = 1;

		}
		if (privStr.indexOf("|selectStudentExpendScore|") >= 0) {
			this.selectStudentExpendScore = 1;

		}
		if (privStr.indexOf("|analizeScore|") >= 0) {
			this.analizeScore = 1;

		}
		if (privStr.indexOf("|getGraduatedStudent|") >= 0) {
			this.getGraduatedStudent = 1;

		}
		if (privStr.indexOf("|checkGraduated|") >= 0) {
			this.checkGraduated = 1;

		}
		if (privStr.indexOf("|totalGraduated|") >= 0) {
			this.totalGraduated = 1;

		}
		if (privStr.indexOf("|totalDegreedStudent|") >= 0) {
			this.totalDegreedStudent = 1;

		}
		if (privStr.indexOf("|signUpDegreedStudent|") >= 0) {
			this.signUpDegreedStudent = 1;

		}
		if (privStr.indexOf("|getDegreedStudent|") >= 0) {
			this.getDegreedStudent = 1;

		}
		if (privStr.indexOf("|updateStudentPwd|") >= 0) {
			this.updateStudentPwd = 1;

		}

		/***********************************************************************
		 * 教学信息管理权限 *
		 **********************************************************************/

		if (privStr.indexOf("|getCourse|") >= 0) {
			this.getCourse = 1;

		}
		if (privStr.indexOf("|addCourse|") >= 0) {
			this.addCourse = 1;

		}
		if (privStr.indexOf("|searchCourse|") >= 0) {
			this.searchCourse = 1;

		}
		if (privStr.indexOf("|updateCourse|") >= 0) {
			this.updateCourse = 1;

		}
		if (privStr.indexOf("|deleteCourse|") >= 0) {
			this.deleteCourse = 1;

		}
		if (privStr.indexOf("|getCourseType|") >= 0) {
			this.getCourseType = 1;

		}
		if (privStr.indexOf("|addCourseType|") >= 0) {
			this.addCourseType = 1;

		}
		if (privStr.indexOf("|updateCourseType|") >= 0) {
			this.updateCourseType = 1;

		}
		if (privStr.indexOf("|deleteCourseType|") >= 0) {
			this.deleteCourseType = 1;

		}
		if (privStr.indexOf("|searchCourseware|") >= 0) {
			this.searchCourseware = 1;

		}
		if (privStr.indexOf("|getCourseware|") >= 0) {
			this.getCourseware = 1;

		}

		if (privStr.indexOf("|addCourseware|") >= 0) {
			this.addCourseware = 1;

		}
		if (privStr.indexOf("|updateCourseware|") >= 0) {
			this.updateCourseware = 1;

		}
		if (privStr.indexOf("|deleteCourseware|") >= 0) {
			this.deleteCourseware = 1;

		}
		if (privStr.indexOf("|getCwareType|") >= 0) {
			this.getCwareType = 1;

		}
		if (privStr.indexOf("|addCwareType|") >= 0) {
			this.addCwareType = 1;

		}
		if (privStr.indexOf("|updateCwareType|") >= 0) {
			this.updateCwareType = 1;

		}

		if (privStr.indexOf("|deleteCwareType|") >= 0) {
			this.deleteCwareType = 1;

		}
		if (privStr.indexOf("|getSemester|") >= 0) {
			this.getSemester = 1;

		}
		if (privStr.indexOf("|activeSemester|") >= 0) {
			this.activeSemester = 1;

		}
		if (privStr.indexOf("|addSemester|") >= 0) {
			this.addSemester = 1;

		}
		if (privStr.indexOf("|updateSemester|") >= 0) {
			this.updateSemester = 1;

		}
		if (privStr.indexOf("|deleteSemester|") >= 0) {
			this.deleteSemester = 1;

		}

		if (privStr.indexOf("|setCourseMajor|") >= 0) {
			this.setCourseMajor = 1;

		}
		if (privStr.indexOf("|getCourseMajor|") >= 0) {
			this.getCourseMajor = 1;

		}
		/***********************************************************************
		 * 教务流程管理权限 *
		 **********************************************************************/
		if (privStr.indexOf("|deleteElective|") >= 0) {
			this.deleteElective = 1;

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
		if (privStr.indexOf("|registerSingle|") >= 0) {
			this.registerSingle = 1;

		}
		if (privStr.indexOf("|cancelRegisterSingle|") >= 0) {
			this.cancelRegisterSingle = 1;

		}
		if (privStr.indexOf("|registerBatch|") >= 0) {
			this.registerBatch = 1;

		}
		if (privStr.indexOf("|cancelRegisterBatch|") >= 0) {
			this.cancelRegisterBatch = 1;

		}
		if (privStr.indexOf("|getRegisterStudent|") >= 0) {
			this.getRegisterStudent = 1;

		}

		if (privStr.indexOf("|searchRegisterStudent|") >= 0) {
			this.searchRegisterStudent = 1;

		}
		if (privStr.indexOf("|openCourseBySemester|") >= 0) {
			this.openCourseBySemester = 1;

		}
		if (privStr.indexOf("|getOpenCoursesBySemester|") >= 0) {
			this.getOpenCoursesBySemester = 1;

		}
		if (privStr.indexOf("|cancelOpenCourseBySemester|") >= 0) {
			this.cancelOpenCourseBySemester = 1;

		}
		if (privStr.indexOf("|appointTeacherForCourse|") >= 0) {
			this.appointTeacherForCourse = 1;

		}
		if (privStr.indexOf("|appointSiteTeacherForCourse|") >= 0) {
			this.appointSiteTeacherForCourse = 1;

		}
		if (privStr.indexOf("|importUsualScore|") >= 0) {
			this.importUsualScore = 1;

		}
		if (privStr.indexOf("|importExamScore|") >= 0) {
			this.importExamScore = 1;

		}
		if (privStr.indexOf("|importExpendScore|") >= 0) {
			this.importExpendScore = 1;

		}
		if (privStr.indexOf("|importScoreSingle|") >= 0) {
			this.importScoreSingle = 1;

		}
		if (privStr.indexOf("|getScoreCard|") >= 0) {
			this.getScoreCard = 1;

		}
		if (privStr.indexOf("|getSingleCourseCard|") >= 0) {
			this.getSingleCourseCard = 1;

		}
		if (privStr.indexOf("|modifyUsualScore|") >= 0) {
			this.modifyUsualScore = 1;

		}
		if (privStr.indexOf("|confirmModifyUsualScore|") >= 0) {
			this.confirmModifyUsualScore = 1;

		}
		if (privStr.indexOf("|modifyExamScore|") >= 0) {
			this.modifyExamScore = 1;

		}
		if (privStr.indexOf("|confirmModifyExamScore|") >= 0) {
			this.confirmModifyExamScore = 1;

		}
		if (privStr.indexOf("|importScoreBatch|") >= 0) {
			this.importScoreBatch = 1;

		}
		if (privStr.indexOf("|generateTotalScore|") >= 0) {
			this.generateTotalScore = 1;

		}
		if (privStr.indexOf("|modifyTotalScore|") >= 0) {
			this.modifyTotalScore = 1;

		}
		if (privStr.indexOf("|confirmModifyTotalScore|") >= 0) {
			this.confirmModifyTotalScore = 1;

		}
		if (privStr.indexOf("|modifyExpendScore|") >= 0) {
			this.modifyExpendScore = 1;

		}
		if (privStr.indexOf("|confirmModifyExpendScore|") >= 0) {
			this.confirmModifyExpendScore = 1;

		}
		// if (privStr.indexOf("|signSingle|") >= 0) {
		// this.signSingle = 1;
		//
		// }
		// if (privStr.indexOf("|addSignBatch|") >= 0) {
		// this.addSignBatch = 1;
		//
		// }
		//
		// if (privStr.indexOf("|updateSignBatch|") >= 0) {
		// this.updateSignBatch = 1;
		//
		// }
		// if (privStr.indexOf("|deleteSignBatch|") >= 0) {
		// this.deleteSignBatch = 1;
		//
		// }
		// if (privStr.indexOf("|getSignBatch|") >= 0) {
		// this.getSignBatch = 1;
		//
		// }
		// if (privStr.indexOf("|setRecruitMajor|") >= 0) {
		// this.setRecruitMajor = 1;
		//
		// }
		// if (privStr.indexOf("|setRecruitEdutype|") >= 0) {
		// this.setRecruitEdutype = 1;
		//
		// }
		// if (privStr.indexOf("|setRecruitSite|") >= 0) {
		// this.setRecruitSite = 1;
		//
		// }
		// if (privStr.indexOf("|setRecruitCourse|") >= 0) {
		// this.setRecruitCourse = 1;
		//
		// }
		// if (privStr.indexOf("|recruitStudent|") >= 0) {
		// this.recruitStudent = 1;
		//
		// }

		if (privStr.indexOf("|getElectiveCourse|") >= 0) {
			this.getElectiveCourse = 1;

		}
		if (privStr.indexOf("|TotalElectiveCourse|") >= 0) {
			this.TotalElectiveCourse = 1;

		}
		/***********************************************************************
		 * 考试信息管理权限 *
		 **********************************************************************/
		if (privStr.indexOf("|getTestSite|") >= 0) {
			this.getTestSite = 1;

		}
		if (privStr.indexOf("|addTestSite|") >= 0) {
			this.addTestSite = 1;

		}
		if (privStr.indexOf("|updateTestSite|") >= 0) {
			this.updateTestSite = 1;

		}
		if (privStr.indexOf("|deleteTestSite|") >= 0) {
			this.deleteTestSite = 1;

		}
		if (privStr.indexOf("|assignTestSite|") >= 0) {
			this.assignTestSite = 1;

		}
		if (privStr.indexOf("|getTestRoom|") >= 0) {
			this.getTestRoom = 1;

		}
		if (privStr.indexOf("||") >= 0) {
			this.addTestRoom = 1;

		}
		if (privStr.indexOf("|updateTestRoom|") >= 0) {
			this.updateTestRoom = 1;

		}
		if (privStr.indexOf("|deleteTestRoom|") >= 0) {
			this.deleteTestRoom = 1;

		}
		if (privStr.indexOf("|getUniteExamScores|") >= 0) {
			this.getUniteExamScores = 1;

		}
		/***************************************************************************
		 * 考试管理权限 *
		 **************************************************************************/
		if (privStr.indexOf("|getExamCourse|") >= 0) {
			this.getExamCourse = 1;

		}
		if (privStr.indexOf("|getExamStudent|") >= 0) {
			this.getExamStudent = 1;

		}
		if (privStr.indexOf("|allotExamRoom|") >= 0) {
			this.allotExamRoom = 1;

		}
		if (privStr.indexOf("|getExamRoomTable|") >= 0) {
			this.getExamRoomTable = 1;

		}
		if (privStr.indexOf("|arrangeExam|") >= 0) {
			this.arrangeExam = 1;

		}
		if (privStr.indexOf("|examPaperlist|") >= 0) {
			this.examPaperlist = 1;

		}
		if (privStr.indexOf("|totalExamStudent|") >= 0) {
			this.totalExamStudent = 1;

		}
		if (privStr.indexOf("|lookExamRoom|") >= 0) {
			this.lookExamRoom = 1;

		}
		
		/***********************************************************************
		 * 站点招生管理权限 *
		 **********************************************************************/
		if (privStr.indexOf("|getRecruitBatch|") >= 0) {
			this.getRecruitBatch = 1;

		}

		if (privStr.indexOf("|deleteRecruitStudent|") >= 0) {
			this.deleteRecruitStudent = 1;

		}

		if (privStr.indexOf("|getRecruitPlan|") >= 0) {
			this.getRecruitPlan = 1;

		}
		if (privStr.indexOf("|addRecruitPlan|") >= 0) {
			this.addRecruitPlan = 1;

		}
		if (privStr.indexOf("|updateRecruitPlan|") >= 0) {
			this.updateRecruitPlan = 1;

		}
		if (privStr.indexOf("|addRecruitStudent|") >= 0) {
			this.addRecruitStudent = 1;

		}
		if (privStr.indexOf("|uploadImage|") >= 0) {
			this.uploadImage = 1;

		}
		if (privStr.indexOf("|downloadImage|") >= 0) {
			this.downloadImage = 1;

		}
		if (privStr.indexOf("|checkImage|") >= 0) {
			this.checkImage = 1;

		}
		if (privStr.indexOf("|uploadIdCard|") >= 0) {
			this.uploadIdCard = 1;

		}
		if (privStr.indexOf("|downloadIdCard|") >= 0) {
			this.downloadIdCard = 1;

		}
		if (privStr.indexOf("|uploadGraduatedCard|") >= 0) {
			this.uploadGraduatedCard = 1;

		}
		if (privStr.indexOf("|downloadGraduatedCard|") >= 0) {
			this.downloadGraduatedCard = 1;

		}
		if (privStr.indexOf("|getRecruitStudent|") >= 0) {
			this.getRecruitStudent = 1;

		}
		if (privStr.indexOf("|getFreeRecruitStudent|") >= 0) {
			this.getFreeRecruitStudent = 1;

		}
		if (privStr.indexOf("|getConfirmStudent|") >= 0) {
			this.getConfirmStudent = 1;

		}
		if (privStr.indexOf("|getConfirmFreeStudent|") >= 0) {
			this.getConfirmFreeStudent = 1;

		}
		if (privStr.indexOf("|getUnConfirmStudent|") >= 0) {
			this.getUnConfirmStudent = 1;

		}
		if (privStr.indexOf("|getUnConfirmFreeStudent|") >= 0) {
			this.getUnConfirmFreeStudent = 1;

		}
		if (privStr.indexOf("|uploadImage|") >= 0) {
			this.uploadImage = 1;

		}
		if (privStr.indexOf("|getStudentTestCourse|") >= 0) {
			this.getStudentTestCourse = 1;

		}
		if (privStr.indexOf("|getSignTongji|") >= 0) {
			this.getSignTongji = 1;

		}
		if (privStr.indexOf("|getTestRoomTongji|") >= 0) {
			this.getTestRoomTongji = 1;

		}
		if (privStr.indexOf("|getEdutypeMajorTestDesk|") >= 0) {
			this.getEdutypeMajorTestDesk = 1;

		}
		if (privStr.indexOf("|getTestRoomSignTable|") >= 0) {
			this.getTestRoomSignTable = 1;

		}
		if (privStr.indexOf("|deletePlan|") >= 0) {
			this.deletePlan = 1;

		}
		if (privStr.indexOf("|getBatchEdutype|") >= 0) {
			this.getBatchEdutype = 1;

		}

		if (privStr.indexOf("|getRecruitMajors|") >= 0) {
			this.getRecruitMajors = 1;

		}
		if (privStr.indexOf("|updateRecruitStudentInfo|") >= 0) {
			this.updateRecruitStudentInfo = 1;

		}
		if (privStr.indexOf("|getRecruitNoExamCondition|") >= 0) {
			this.getRecruitNoExamCondition = 1;

		}
		if (privStr.indexOf("|getExamroomDisplay|") >= 0) {
			this.getExamroomDisplay = 1;

		}
		if (privStr.indexOf("|generateTestRoom|") >= 0) {
			this.generateTestRoom = 1;

		}
		if (privStr.indexOf("|getRecruitTestRoom|") >= 0) {
			this.getRecruitTestRoom = 1;

		}
		if (privStr.indexOf("|getTotalRecruitStudents|") >= 0) {
			this.getTotalRecruitStudents = 1;

		}

		if (privStr.indexOf("|getPassRecruitStudents|") >= 0) {
			this.getPassRecruitStudents = 1;

		}
		if (privStr.indexOf("|getUnPassRecruitStudents|") >= 0) {
			this.getUnPassRecruitStudents = 1;

		}
		if (privStr.indexOf("|getRecruitScoreStudents|") >= 0) {
			this.getRecruitScoreStudents = 1;

		}
		/***********************************************************************
		 * 教学计划 *
		 **********************************************************************/
		if (privStr.indexOf("|getTeachPlan|") >= 0) {
			this.getTeachPlan = 1;

		}
		if (privStr.indexOf("|getTeachPlanCourse|") >= 0) {
			this.getTeachPlanCourse = 1;

		}
		if (privStr.indexOf("|addTeachPlan|") >= 0) {
			this.addTeachPlan = 1;

		}
		if (privStr.indexOf("|getUnTeachPlanCourse|") >= 0) {
			this.getUnTeachPlanCourse = 1;

		}
		if (privStr.indexOf("|addTeachPlanCourse|") >= 0) {
			this.addTeachPlanCourse = 1;

		}

		/***********************************************************************
		 * 个人信息管理 *
		 **********************************************************************/
		if (privStr.indexOf("|updatePwd|") >= 0) {
			this.updatePwd = 1;

		}
		/***********************************************************************
		 * 费用管理 *
		 **********************************************************************/
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
		if (privStr.indexOf("|getSiteFeeStat|") >= 0) {
			this.getSiteFeeStat = 1;

		}
		if (privStr.indexOf("|getStuOtherFee|") >= 0) {
			this.getStuOtherFee = 1;

		}
		if (privStr.indexOf("|getStuOtherFeeByTime|") >= 0) {
			this.getStuOtherFeeByTime = 1;

		}
		if (privStr.indexOf("|getStuFeeReturnApply|") >= 0) {
			this.getStuFeeReturnApply = 1;

		}
		if (privStr.indexOf("|getConfirmOrder|") >= 0) {
			this.getConfirmOrder = 1;

		}

		/***********************************************************************
		 * 短信管理 *
		 **********************************************************************/
		if (privStr.indexOf("|addSms|") >= 0) {
			this.addSms = 1;

		}
		if (privStr.indexOf("|getSms|") >= 0) {
			this.getSms = 1;

		}

		if (privStr.indexOf("|updateSms|") >= 0) {
			this.updateSms = 1;

		}
		if (privStr.indexOf("|deleteSms|") >= 0) {
			this.deleteSms = 1;

		}
		if (privStr.indexOf("|addSms|") >= 0) {
			this.addSms = 1;

		}
		if (privStr.indexOf("|batchImportMobiles|") >= 0) {
			this.batchImportMobiles = 1;

		}
		/***********************************************************************
		 * 考试管理权限 *
		 **********************************************************************/
		if (privStr.indexOf("|getLore|") >= 0) {
			this.getLore = 1;

		}
		if (privStr.indexOf("|getLores|") >= 0) {
			this.getLores = 1;

		}

		if (privStr.indexOf("|getPaperPolicy|") >= 0) {
			this.getPaperPolicy = 1;

		}
		if (privStr.indexOf("|getPaperPolicys|") >= 0) {
			this.getPaperPolicys = 1;

		}
		if (privStr.indexOf("|addPaperPolicy|") >= 0) {
			this.addPaperPolicy = 1;

		}

	}
}
