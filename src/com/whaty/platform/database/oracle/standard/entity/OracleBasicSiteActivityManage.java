/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleBasicActivityList;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleChangeMajorApply;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleDegreeActivity;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleElectiveActivity;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleGraduateActivity;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourseActivity;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleRegister;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleStatusChangeActivity;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleTeachClass;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEntityList;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleGrade;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSemester;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleTeachPlan;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleTeachPlanCourse;
import com.whaty.platform.database.oracle.standard.entity.user.OracleBasicUserList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.BasicSiteActivityManage;
import com.whaty.platform.entity.activity.ChangeMajorStudent;
import com.whaty.platform.entity.activity.ElectiveActivity;
import com.whaty.platform.entity.basic.GradeEduMajorGroup;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.test.TestFactory;
import com.whaty.platform.test.TestManage;
import com.whaty.platform.test.TestPriv;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;

/**
 * @author wq
 * 
 */
public class OracleBasicSiteActivityManage extends BasicSiteActivityManage {

	SiteManagerPriv basicManagePriv;

	public OracleBasicSiteActivityManage(SiteManagerPriv amanagerpriv) {
		this.basicManagePriv = amanagerpriv;
	}

	public TestManage creatTestManage() {
		TestFactory testFactory = TestFactory.getInstance();
		TestPriv priv = testFactory.getTestPriv();
		priv.getLore = basicManagePriv.getLore;
		priv.getLores = basicManagePriv.getLores;
		priv.getPaperPolicy = basicManagePriv.getPaperPolicy;
		priv.getPaperPolicys = basicManagePriv.getPaperPolicys;
		priv.addPaperPolicy = basicManagePriv.addPaperPolicy;
		priv.getExamCourse = basicManagePriv.getExamCourse;
		return testFactory.creatTestManage(priv);
	}

	public void cancelChangeStatus(List studentList, String statusChangeType)
			throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		changeStatus.setStudentList(studentList);
		changeStatus.cancelChangeStatus(statusChangeType);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$cancelChangeStatus</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public void changeMajorStatus(List studentList, String newmajor_id,
			String newedu_type_id, String newgrade_id, String newsite_id)
			throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		changeStatus.setStudentList(studentList);
		changeStatus.changeMajorStatus(newmajor_id, newedu_type_id,
				newgrade_id, newsite_id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$changeMajorStatus</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public void changeStatus(List studentList, String statusChangeType)
			throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		changeStatus.setStudentList(studentList);
		changeStatus.changeStatus(statusChangeType);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$changeStatus</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public List getChangeMajorStudents() throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		return changeStatus.getChangeMajorStudents();
	}

	public List getStatusChangeStudents(String statusChangeType)
			throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		return changeStatus.getStatusChangeStudents(statusChangeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicActivityManage#openCourses(java.util.List,
	 *      java.lang.String)
	 */
	public void openCourses(List courses, String semesterId)
			throws PlatformException {
		OracleOpenCourseActivity openCourseActivity = new OracleOpenCourseActivity();
		openCourseActivity.setCourseList(courses);
		OracleSemester semester = new OracleSemester();
		semester.setId(semesterId);
		openCourseActivity.setSemester(semester);
		openCourseActivity.openCourses();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$openCourses</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public void openCourses(String[] coursesId, String semesterId)
			throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			List courseList = new ArrayList();
			for (int i = 0; i < coursesId.length; i++) {
				OracleCourse course = new OracleCourse(coursesId[i]);
				// course.setId(coursesId[i]);
				courseList.add(course);
			}
			OracleOpenCourseActivity openCourseActivity = new OracleOpenCourseActivity();
			openCourseActivity.setCourseList(courseList);
			OracleSemester semester = new OracleSemester();
			semester.setId(semesterId);
			openCourseActivity.setSemester(semester);
			openCourseActivity.openCourses();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$openCourses</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("��û��ѧ�ڿ��ε�Ȩ��");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicActivityManage#unOpenCourses(java.util.List,
	 *      java.lang.String)
	 */
	public void unOpenCourses(List courses, String semesterId)
			throws PlatformException {
		OracleOpenCourseActivity openCourseActivity = new OracleOpenCourseActivity();
		openCourseActivity.setCourseList(courses);
		OracleSemester semester = new OracleSemester();
		semester.setId(semesterId);
		openCourseActivity.setSemester(semester);
		openCourseActivity.unOpenCourses();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$unOpenCourses</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public void unOpenCourses(String[] coursesId, String semesterId)
			throws PlatformException {
		if (basicManagePriv.cancelOpenCourseBySemester == 1) {
			List courseList = new ArrayList();
			for (int i = 0; i < coursesId.length; i++) {
				OracleCourse course = new OracleCourse();
				course.setId(coursesId[i]);
				courseList.add(course);
			}
			OracleOpenCourseActivity openCourseActivity = new OracleOpenCourseActivity();
			openCourseActivity.setCourseList(courseList);
			OracleSemester semester = new OracleSemester();
			semester.setId(semesterId);
			openCourseActivity.setSemester(semester);
			openCourseActivity.unOpenCourses();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$unOpenCourses</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("��û��ȡ��ѧ�ڿ��ε�Ȩ��");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicActivityManage#getOpenCourses(com.whaty.platform.util.Page,
	 *      java.lang.String)
	 */
	public List getOpenCourses(Page page, String semesterId)
			throws PlatformException {
		OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
		SearchProperty search = new SearchProperty("semester_id", semesterId);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		return basicActivityList.searchOpenCourse(page, searchproperty, null);
	}

	public List getOpenCourses(Page page, String major_id, String semesterId)
			throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			SearchProperty search = new SearchProperty("semester_id",
					semesterId);
			List searchproperty = new ArrayList();
			searchproperty.add(search);
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchproperty.add(new SearchProperty("major_id", major_id));

			return basicActivityList.searchOpenCourse(page, searchproperty,
					null);
		} else {
			throw new PlatformException("��û��ѧ�ڿ��ε�Ȩ��");
		}

	}

	public List getOpenCourses(Page page, String major_id, String semesterId,
			String course_id, String course_name) throws PlatformException {
		OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
		SearchProperty search = new SearchProperty("semester_id", semesterId);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		if (major_id != null && !major_id.equals("")
				&& !major_id.equals("null"))
			searchproperty.add(new SearchProperty("major_id", major_id));
		if (course_id != null && !course_id.equals("")
				&& !course_id.equals("null"))
			searchproperty.add(new SearchProperty("course_id", course_id, "="));
		if (course_name != null && !course_name.equals("")
				&& !course_name.equals("null"))
			searchproperty.add(new SearchProperty("course_name", course_name,
					"like"));
		return basicActivityList.searchOpenCourse(page, searchproperty, null);
	}

	public List getElectiveByUserId(Page page, String semester_id,
			String student_id) throws PlatformException {
		if (basicManagePriv.confirmElectiveInfo == 1) {
			List searchProperties = new ArrayList();
			if (semester_id != null && !semester_id.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semester_id, "="));
			}
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			Student student = new OracleStudent();
			student.setId(student_id);
			if (semester_id != null && !semester_id.equals("")) {
				return basicActivityList.searchElective(null, searchProperties,
						null, student);
			} else
				return basicActivityList.searchElective(null, null, null,
						student);
		} else {
			throw new PlatformException("��û��ȷ��ѡ�ε�Ȩ��");
		}
	}

	public String getOpenCourseId(String semesterId, String courseId)
			throws PlatformException {
		OracleOpenCourseActivity opencourse = new OracleOpenCourseActivity();
		return opencourse.getOpenCourseId(semesterId, courseId);
	}

	public List getTeachClassByOpenCourseId(String open_course_id)
			throws PlatformException {
		OracleOpenCourseActivity opencourse = new OracleOpenCourseActivity();
		return opencourse.getTeachClassByOpenCourseId(open_course_id);
	}

	public int checkCourseIsOpen(String course_id, String semesterId)
			throws PlatformException {
		OracleOpenCourseActivity openCourseActivity = new OracleOpenCourseActivity();
		OracleSemester semester = new OracleSemester();
		semester.setId(semesterId);
		openCourseActivity.setSemester(semester);
		if (openCourseActivity.getOpenCoursesStr().indexOf(course_id) >= 0)
			return 1;
		else
			return 0;
	}

	public int getOpenCoursesNum(String major_id, String semesterId)
			throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			SearchProperty search = new SearchProperty("semester_id",
					semesterId);
			List searchproperty = new ArrayList();
			searchproperty.add(search);
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchproperty.add(new SearchProperty("major_id", major_id));
			return basicActivityList.searchOpenCourseNum(searchproperty);
		} else {
			throw new PlatformException("��û��ѧ�ڿ��ε�Ȩ��");
		}
	}

	public int getOpenCoursesNum(String major_id, String semesterId,
			String course_id, String course_name) throws PlatformException {
		OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
		SearchProperty search = new SearchProperty("semester_id", semesterId);
		List searchproperty = new ArrayList();
		searchproperty.add(search);
		if (major_id != null && !major_id.equals("")
				&& !major_id.equals("null"))
			searchproperty.add(new SearchProperty("major_id", major_id));
		if (course_id != null && !course_id.equals("")
				&& !course_id.equals("null"))
			searchproperty.add(new SearchProperty("course_id", course_id, "="));
		if (course_name != null && !course_name.equals("")
				&& !course_name.equals("null"))
			searchproperty.add(new SearchProperty("course_name", course_name,
					"like"));
		return basicActivityList.searchOpenCourseNum(searchproperty);
	}

	public List getTeachPlanCourses(Page page, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getTeachPlanCourse == 1) {
			List searchProperties = new ArrayList();

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			OracleTeachPlan oracleTeachplan = new OracleTeachPlan();
			return oracleTeachplan.getCourses(page, searchProperties, null);
		} else {
			throw new PlatformException("��û��ѧ�ڿ��ε�Ȩ��");
		}
	}

	public List getUnTeachPlanCourses(Page page, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getUnTeachPlanCourse == 1) {
			List searchProperties = new ArrayList();

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			OracleTeachPlan oracleTeachplan = new OracleTeachPlan();
			return oracleTeachplan
					.getOtherCourses(page, searchProperties, null);
		} else {
			throw new PlatformException("��û��ѧ�ڿ��ε�Ȩ��");
		}
	}

	public List getTeachPlan(String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		if (basicManagePriv.getTeachPlan == 1) {
			List searchProperties = new ArrayList();

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			OracleBasicEntityList basicEntityList = new OracleBasicEntityList();
			return basicEntityList.getTeachPlans(null, searchProperties, null);
		} else {
			throw new PlatformException("��û��ѧ�ڿ��ε�Ȩ��");
		}
	}

	public void addTeachPlan(String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		if (basicManagePriv.addTeachPlan == 1) {
			OracleTeachPlan teachplan = new OracleTeachPlan();
			teachplan.setTitle("null");
			OracleMajor major = new OracleMajor(major_id);
			teachplan.setMajor(major);
			OracleEduType eduType = new OracleEduType(edu_type_id);
			teachplan.setEduType(eduType);
			OracleGrade grade = new OracleGrade(grade_id);
			teachplan.setGrade(grade);
			teachplan.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addTeachPlan</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("��û��ѧ�ڿ��ε�Ȩ��");
		}
	}

	public int addTeachPlanCourse(String course_id, String major_id,
			String edu_type_id, String grade_id, String type, String isdegree)
			throws PlatformException {
		if (basicManagePriv.addTeachPlanCourse == 1) {
			OracleTeachPlanCourse teachplancourse = new OracleTeachPlanCourse();
			OracleCourse course = new OracleCourse();
			course.setId(course_id);
			course.setCourse_type(type);
			teachplancourse.setCourse(course);
			OracleTeachPlan teachplan = new OracleTeachPlan();
			OracleMajor major = new OracleMajor(major_id);
			major.setId(major_id);
			teachplan.setMajor(major);
			OracleGrade grade = new OracleGrade(grade_id);
			grade.setId(grade_id);
			teachplan.setGrade(grade);
			OracleEduType edutype = new OracleEduType(edu_type_id);
			edutype.setId(edu_type_id);
			teachplan.setEduType(edutype);
			teachplancourse.setIsdegree(isdegree);
			int suc = teachplancourse.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addTeachPlanCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��ѧ�ڿ��ε�Ȩ��");
		}
	}

	public int deleteAllElective(String semester_id, String site_id,
			String edu_type_id, String major_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.deleteElective == 1) {
			dbpool db = new dbpool();
			String sql;
			if (site_id != null && !site_id.equals("null")
					&& !site_id.equals(""))
				sql = "delete from entity_elective where student_id in "
						+ "(select id from entity_student_info where site_id = '"
						+ site_id
						+ "' "
						+ "and edutype_id = '"
						+ edu_type_id
						+ "' and major_id = '"
						+ major_id
						+ "' and grade_id = '"
						+ grade_id
						+ "') "
						+ "and teachclass_id in (select c.id as id from entity_teach_class c,entity_course_active a "
						+ "where c.open_course_id = a.id and a.semester_id = '"
						+ semester_id + "')";
			else
				sql = "delete from entity_elective where student_id in "
						+ "(select id from entity_student_info where  "
						+ "edutype_id = '"
						+ edu_type_id
						+ "' and major_id = '"
						+ major_id
						+ "' and grade_id = '"
						+ grade_id
						+ "') "
						+ "and teachclass_id in (select c.id as id from entity_teach_class c,entity_course_active a "
						+ "where c.open_course_id = a.id and a.semester_id = '"
						+ semester_id + "')";
			int suc = db.executeUpdate(sql);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteAllElective</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û�н�ѧվ��ѡ�ε�Ȩ��");
		}
	}

	public List getUnConfirmElectives(Page page, String semester_id,
			String site_id, String edu_type_id, String major_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.confirmElectiveInfo == 1) {
			List searchProperties = new ArrayList();

			if (site_id != null && !site_id.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", site_id, "="));
			}

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edu_type_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}

			if (semester_id != null && !semester_id.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semester_id, "="));
			}
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.searchUnConfirmElective(page,
					searchProperties, null);
		} else {
			throw new PlatformException("��û�е���ѡ�ε�Ȩ��");
		}
	}

	public int getUnConfirmElectivesNum(String semester_id, String site_id,
			String edu_type_id, String major_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.confirmElectiveInfo == 1) {
			List searchProperties = new ArrayList();

			if (site_id != null && !site_id.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", site_id, "="));
			}

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edu_type_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}

			if (semester_id != null && !semester_id.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semester_id, "="));
			}
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList
					.searchUnConfirmElectiveNum(searchProperties);
		} else {
			throw new PlatformException("��û�е���ѡ�ε�Ȩ��");
		}
	}

	/*
	 * (non-Javadoc) ��վѧ����ѡ��
	 * 
	 * @see com.whaty.platform.entity.BasicSiteActivityManage#electiveCoursesByUserId(java.lang.String[],
	 *      java.lang.String, java.lang.String)
	 */
	public void electiveCoursesByUserId(String[] idSet, String semester_id,
			String student_id) throws PlatformException {
		if (basicManagePriv.electiveBatchByStudent == 1) {
			OracleElectiveActivity electiveActivity = new OracleElectiveActivity();
			electiveActivity.electiveCoursesByUserId(idSet, semester_id,
					student_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$electiveCoursesByUserId</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("��û��Ϊѧ����ѡ�ε�Ȩ��");
		}
	}

	public void confirmElectiveCoursesByUserId(String[] idSet,
			String semester_id, String student_id) throws PlatformException {
		if (basicManagePriv.confirmElectiveInfo == 1) {

		} else {
			throw new PlatformException("��û�е���ȷ��ѡ�ε�Ȩ��");
		}
	}

	public void electiveCourses(String[] idSet, String semester_id,
			String site_id, String edu_type_id, String major_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.electiveBatchBySite == 1) {
			dbpool db = new dbpool();
			String courseactive = "";
			String sql;
			String delsql;
			if (idSet == null) {

				if (site_id != null && !site_id.equals("null")
						&& !site_id.equals(""))
					sql = "delete from entity_elective where student_id in "
							+ "(select id from entity_student_info where site_id = '"
							+ site_id
							+ "' "
							+ "and edutype_id = '"
							+ edu_type_id
							+ "' and major_id = '"
							+ major_id
							+ "' and grade_id = '"
							+ grade_id
							+ "') "
							+ "and teachclass_id in (select c.id as id from entity_teach_class c,entity_course_active a "
							+ "where c.open_course_id = a.id and a.semester_id = '"
							+ semester_id + "')";
				else
					sql = "delete from entity_elective where student_id in "
							+ "(select id from entity_student_info where  "
							+ "edutype_id = '"
							+ edu_type_id
							+ "' and major_id = '"
							+ major_id
							+ "' and grade_id = '"
							+ grade_id
							+ "') "
							+ "and teachclass_id in (select c.id as id from entity_teach_class c,entity_course_active a "
							+ "where c.open_course_id = a.id and a.semester_id = '"
							+ semester_id + "')";
				db.executeUpdate(sql);
			} else {
				for (int i = 0; i < idSet.length; i++) {
					courseactive = courseactive + "'" + idSet[i] + "',";
				}
				courseactive = courseactive.substring(0,
						courseactive.length() - 1);
				if (site_id != null && !site_id.equals("null")
						&& !site_id.equals("")) {
					delsql = "delete from entity_elective where teachclass_id in"
							+ "(select t.id as id from entity_teach_class t,entity_course_active a where a.semester_id='"
							+ semester_id
							+ "' "
							+ "and t.open_course_id = a.id and t.id not in("
							+ courseactive
							+ "))"
							+ "and student_id in (select id from entity_student_info where  major_id = '"
							+ major_id
							+ "' "
							+ "and grade_id = '"
							+ grade_id
							+ "' and edutype_id = '"
							+ edu_type_id
							+ "' and isgraduated = '0' and status = '0000') ";
					db.executeUpdate(delsql);
					for (int i = 0; i < idSet.length; i++) {

						sql = "insert into entity_elective (student_id,open_course_id,status) (select id,"
								+ idSet[i]
								+ ",'1'  "
								+ "from (select a.id as id from entity_student_info a,entity_register_info b "
								+ "where a.id = b.user_id and b.semester_id = '"
								+ semester_id
								+ "' and a.major_id = '"
								+ major_id
								+ "' and a.edu_type_id = '"
								+ edu_type_id
								+ "' "
								+ "and a.grade_id = '"
								+ grade_id
								+ "'  and a.isgraduated = '0' and a.status='0000') "
								+ "where id not in (select student_id from entity_elective where teachclass_id = '"
								+ idSet[i] + "'))";
						db.executeUpdate(sql);

						sql = "update entity_elective set status = '1' where student_id in (select c.id as id from "
								+ "(select a.id as id from entity_student_info a,entity_register_info b where a.id = b.user_id "
								+ "and b.semester_id = '"
								+ semester_id
								+ "' and a.major_id = '"
								+ major_id
								+ "' and a.edu_type_id = '"
								+ edu_type_id
								+ "' "
								+ "and a.grade_id = '"
								+ grade_id
								+ "'  and a.isgraduated = '0' and a.status='0000') c ,"
								+ "(select student_id from entity_elective where teachclass_id = '"
								+ idSet[i]
								+ "' and status = '0') d where c.id = d.student_id )";
						db.executeUpdate(sql);
					}
				} else {
					delsql = "delete from entity_elective where teachclass_id in"
							+ "(select t.id as id from entity_teach_class t,entity_course_active a where a.semester_id='"
							+ semester_id
							+ "' "
							+ "and t.open_course_id = a.id and t.id not in("
							+ courseactive
							+ "))"
							+ "and student_id in (select id from entity_student_info where  site='"
							+ site_id
							+ "' and major_id = '"
							+ major_id
							+ "' "
							+ "and grade_id = '"
							+ grade_id
							+ "' and edutype_id = '"
							+ edu_type_id
							+ "' and isgraduated = '0' and status = '0000') ";
					db.executeUpdate(delsql);
					for (int i = 0; i < idSet.length; i++) {

						sql = "insert into entity_elective (student_id,open_course_id,status) (select id,"
								+ idSet[i]
								+ ",'1'  "
								+ "from (select a.id as id from entity_student_info a,entity_register_info b "
								+ "where a.id = b.user_id and b.semester_id = '"
								+ semester_id
								+ "' and a.site='"
								+ site_id
								+ "' and a.major_id = '"
								+ major_id
								+ "' and a.edu_type_id = '"
								+ edu_type_id
								+ "' "
								+ "and a.grade_id = '"
								+ grade_id
								+ "'  and a.isgraduated = '0' and a.status='0000') "
								+ "where id not in (select student_id from entity_elective where teachclass_id = '"
								+ idSet[i] + "'))";
						db.executeUpdate(sql);

						sql = "update entity_elective set status = '1' where student_id in (select c.id as id from "
								+ "(select a.id as id from entity_student_info a,entity_register_info b where a.id = b.user_id "
								+ "and b.semester_id = '"
								+ semester_id
								+ "' and a.site='"
								+ site_id
								+ "'  and a.major_id = '"
								+ major_id
								+ "' and a.edu_type_id = '"
								+ edu_type_id
								+ "' "
								+ "and a.grade_id = '"
								+ grade_id
								+ "'  and a.isgraduated = '0' and a.status='0000') c ,"
								+ "(select student_id from entity_elective where teachclass_id = '"
								+ idSet[i]
								+ "' and status = '0') d where c.id = d.student_id )";
						db.executeUpdate(sql);
					}
				}
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$electiveCourses</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("��û�н�ѧվ��ѡ�ε�Ȩ��");
		}

	}

	public void electiveCourses(String[] idSet, String semester_id,
			String edu_type_id, String major_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.electiveBatchBySite == 1) {
			String site_id = basicManagePriv.getSite_id();
			dbpool db = new dbpool();
			String courseactive = "";
			String sql;
			String delsql;
			if (idSet == null) {

				if (site_id != null && !site_id.equals("null")
						&& !site_id.equals(""))
					sql = "delete from entity_elective where student_id in "
							+ "(select id from entity_student_info where site_id = '"
							+ site_id
							+ "' "
							+ "and edutype_id = '"
							+ edu_type_id
							+ "' and major_id = '"
							+ major_id
							+ "' and grade_id = '"
							+ grade_id
							+ "') "
							+ "and teachclass_id in (select c.id as id from entity_teach_class c,entity_course_active a "
							+ "where c.open_course_id = a.id and a.semester_id = '"
							+ semester_id + "')";
				else
					sql = "delete from entity_elective where student_id in "
							+ "(select id from entity_student_info where  "
							+ "edutype_id = '"
							+ edu_type_id
							+ "' and major_id = '"
							+ major_id
							+ "' and grade_id = '"
							+ grade_id
							+ "') "
							+ "and teachclass_id in (select c.id as id from entity_teach_class c,entity_course_active a "
							+ "where c.open_course_id = a.id and a.semester_id = '"
							+ semester_id + "')";
				db.executeUpdate(sql);
			} else {
				for (int i = 0; i < idSet.length; i++) {
					courseactive = courseactive + "'" + idSet[i] + "',";
				}
				courseactive = courseactive.substring(0,
						courseactive.length() - 1);
				if (site_id != null && !site_id.equals("null")
						&& !site_id.equals("")) {
					delsql = "delete from entity_elective where teachclass_id in"
							+ "(select t.id as id from entity_teach_class t,entity_course_active a where a.semester_id='"
							+ semester_id
							+ "' "
							+ "and t.open_course_id = a.id and t.id not in("
							+ courseactive
							+ "))"
							+ "and student_id in (select id from entity_student_info where  major_id = '"
							+ major_id
							+ "' "
							+ "and grade_id = '"
							+ grade_id
							+ "' and edutype_id = '"
							+ edu_type_id
							+ "' and isgraduated = '0' and status = '0000') ";
					db.executeUpdate(delsql);
					for (int i = 0; i < idSet.length; i++) {

						sql = "insert into entity_elective (student_id,open_course_id,status) (select id,"
								+ idSet[i]
								+ ",'1'  "
								+ "from (select a.id as id from entity_student_info a,entity_register_info b "
								+ "where a.id = b.user_id and b.semester_id = '"
								+ semester_id
								+ "' and a.major_id = '"
								+ major_id
								+ "' and a.edu_type_id = '"
								+ edu_type_id
								+ "' "
								+ "and a.grade_id = '"
								+ grade_id
								+ "'  and a.isgraduated = '0' and a.status='0000') "
								+ "where id not in (select student_id from entity_elective where teachclass_id = '"
								+ idSet[i] + "'))";
						db.executeUpdate(sql);

						sql = "update entity_elective set status = '1' where student_id in (select c.id as id from "
								+ "(select a.id as id from entity_student_info a,entity_register_info b where a.id = b.user_id "
								+ "and b.semester_id = '"
								+ semester_id
								+ "' and a.major_id = '"
								+ major_id
								+ "' and a.edu_type_id = '"
								+ edu_type_id
								+ "' "
								+ "and a.grade_id = '"
								+ grade_id
								+ "'  and a.isgraduated = '0' and a.status='0000') c ,"
								+ "(select student_id from entity_elective where teachclass_id = '"
								+ idSet[i]
								+ "' and status = '0') d where c.id = d.student_id )";
						db.executeUpdate(sql);
					}
				} else {
					delsql = "delete from entity_elective where teachclass_id in"
							+ "(select t.id as id from entity_teach_class t,entity_course_active a where a.semester_id='"
							+ semester_id
							+ "' "
							+ "and t.open_course_id = a.id and t.id not in("
							+ courseactive
							+ "))"
							+ "and student_id in (select id from entity_student_info where  site='"
							+ site_id
							+ "' and major_id = '"
							+ major_id
							+ "' "
							+ "and grade_id = '"
							+ grade_id
							+ "' and edutype_id = '"
							+ edu_type_id
							+ "' and isgraduated = '0' and status = '0000') ";
					db.executeUpdate(delsql);
					for (int i = 0; i < idSet.length; i++) {

						sql = "insert into entity_elective (student_id,open_course_id,status) (select id,"
								+ idSet[i]
								+ ",'1'  "
								+ "from (select a.id as id from entity_student_info a,entity_register_info b "
								+ "where a.id = b.user_id and b.semester_id = '"
								+ semester_id
								+ "' and a.site='"
								+ site_id
								+ "' and a.major_id = '"
								+ major_id
								+ "' and a.edu_type_id = '"
								+ edu_type_id
								+ "' "
								+ "and a.grade_id = '"
								+ grade_id
								+ "'  and a.isgraduated = '0' and a.status='0000') "
								+ "where id not in (select student_id from entity_elective where teachclass_id = '"
								+ idSet[i] + "'))";
						db.executeUpdate(sql);

						sql = "update entity_elective set status = '1' where student_id in (select c.id as id from "
								+ "(select a.id as id from entity_student_info a,entity_register_info b where a.id = b.user_id "
								+ "and b.semester_id = '"
								+ semester_id
								+ "' and a.site='"
								+ site_id
								+ "'  and a.major_id = '"
								+ major_id
								+ "' and a.edu_type_id = '"
								+ edu_type_id
								+ "' "
								+ "and a.grade_id = '"
								+ grade_id
								+ "'  and a.isgraduated = '0' and a.status='0000') c ,"
								+ "(select student_id from entity_elective where teachclass_id = '"
								+ idSet[i]
								+ "' and status = '0') d where c.id = d.student_id )";
						db.executeUpdate(sql);
					}
				}
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$electiveCourses</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("��û�н�ѧվ��ѡ�ε�Ȩ��");
		}

	}

	public int getElectiveNum(String open_course_id) throws PlatformException {
		if (basicManagePriv.electiveBatchBySite == 1) {
			dbpool db = new dbpool();
			MyResultSet rs = null;
			List teachclassList = new ArrayList();
			String sql = "select id from entity_teach_class where open_course_id = '"
					+ open_course_id + "'";
			try {
				rs = db.executeQuery(sql);
				while (rs != null && rs.next()) {
					OracleTeachClass teachclass = new OracleTeachClass();
					teachclass.setId(rs.getString("id"));
					teachclassList.add(teachclass);
				}
			} catch (Exception e) {
				
			} finally {
				db.close(rs);
				db = null;
			}
			OracleBasicActivityList activitylist = new OracleBasicActivityList();
			return activitylist.searchElectiveNum(null, teachclassList);
		} else {
			throw new PlatformException("��û�н�ѧվ��ѡ�ε�Ȩ��");
		}
	}

	public int getTeachPlanCoursesNum(String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			List searchProperties = new ArrayList();

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			OracleTeachPlan oracleTeachplan = new OracleTeachPlan();
			return oracleTeachplan.getCoursesNum(searchProperties);
		} else {
			throw new PlatformException("��û��ѧ�ڿ��ε�Ȩ��");
		}
	}

	public int getUnTeachPlanCoursesNum(String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			List searchProperties = new ArrayList();

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			OracleTeachPlan oracleTeachplan = new OracleTeachPlan();
			return oracleTeachplan.getOtherCoursesNum(searchProperties);
		} else {
			throw new PlatformException("��û��ѧ�ڿ��ε�Ȩ��");
		}
	}

	public List getTeachersByTeachClass(String teachclass_id)
			throws PlatformException {
		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getTeachersByTeachClass(teachclass_id);
		} else {
			throw new PlatformException("��û�и�γ�ָ����ʦ��Ȩ��");
		}
	}

	public List getSiteTeachersByTeachClass(String teachclass_id)
			throws PlatformException {
		if (basicManagePriv.appointSiteTeacherForCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getSiteTeachersByTeachClass(teachclass_id);
		} else {
			throw new PlatformException("��û�и�γ�ָ����ʦ��Ȩ��");
		}
	}

	public int checkTeacherByTeachClass(String teacher_id, String teachclass_id)
			throws PlatformException {
		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachclass_id);
			return teachclass.checkTeacher(teacher_id);
		} else {
			throw new PlatformException("��û�и�γ�ָ����ʦ��Ȩ��");
		}
	}

	public void addTeachersToTeachClass(String[] teacherIdSet,
			String teachclass_id) throws PlatformException {
		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachclass_id);
			teachclass.addTeachers(teacherIdSet);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addTeachersToTeachClass</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("��û�и�γ�ָ����ʦ��Ȩ��");
		}
	}

	public void addSiteTeachersToTeachClass(String[] siteteacherIdSet,
			String teachclass_id) throws PlatformException {
		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachclass_id);
			teachclass.addSiteTeachers(siteteacherIdSet);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addTeachersToTeachClass</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("��û�и�γ�ָ����ʦ��Ȩ��");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicActivityManage#addTeachClass(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void addTeachClass(String id, String name, String openCourseId)
			throws PlatformException {
		OracleTeachClass teachClass = new OracleTeachClass();
		teachClass.setId(id);
		teachClass.setName(name);
		OracleOpenCourse openCourse = new OracleOpenCourse();
		openCourse.setId(openCourseId);
		teachClass.setOpenCourse(openCourse);
		int i = teachClass.add();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$addTeachClass</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicActivityManage#deleteTeachClass(java.lang.String)
	 */
	public void deleteTeachClass(String id) throws PlatformException {
		OracleTeachClass teachClass = new OracleTeachClass(id);
		teachClass.delete();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteTeachClass</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public int deleteTeachPlanCourse(String id) throws PlatformException {
		OracleTeachPlan teachplan = new OracleTeachPlan();
		List courseList = new ArrayList();
		OracleCourse course = new OracleCourse();
		course.setId(id);
		courseList.add(course);
		int suc = teachplan.removeCourses(courseList);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteTeachPlanCourse</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int deleteTeachPlanCourses(String[] id) throws PlatformException {
		OracleTeachPlan teachplan = new OracleTeachPlan();
		List courseList = new ArrayList();
		for (int i = 0; i < id.length; i++) {
			OracleCourse course = new OracleCourse();
			course.setId(id[i]);
			courseList.add(course);
		}
		int suc = teachplan.removeCourses(courseList);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$deleteTeachPlanCourses</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicActivityManage#getTeachClasses(java.lang.String)
	 */
	public List getTeachClasses(String openCourseId) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicActivityManage#addTeachClassStudents(java.util.List,
	 *      java.lang.String)
	 */
	public void addTeachClassStudents(List studentIdList, String teachClassId)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicActivityManage#removeTeachClassStudents(java.util.List,
	 *      java.lang.String)
	 */
	public void removeTeachClassStudents(List studentIdList, String teachClassId)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicActivityManage#getTeachClassStudents(java.lang.String)
	 */
	public List getTeachClassStudents(String teachClassId)
			throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicActivityManage#moveTeachClassStudents(java.lang.String,
	 *      java.lang.String)
	 */
	public void moveTeachClassStudents(String oldTeachClassId,
			String newTeachClassId) throws PlatformException {
		// TODO Auto-generated method stub

	}

	public List checkGraduateByCompulsory(List studentList, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
		graduateactivity.setStudentList(studentList);
		return graduateactivity.checkByCompulsory(major_id, edu_type_id,
				grade_id);
	}

	public List checkGraduateByCredit(List studentList, String credit)
			throws PlatformException {
		OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
		graduateactivity.setStudentList(studentList);
		return graduateactivity.checkByCredit(credit);
	}

	public List getGraduateStudents(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
		return graduateactivity.getGraduateStudents(page, searchproperty,
				orderproperty);
	}

	public int getGraduateStudentsNum(List searchproperty)
			throws PlatformException {
		OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
		return graduateactivity.getGraduateStudentsNum(searchproperty);
	}

	public int setGraduate(List studentList) throws PlatformException {
		OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
		int suc = graduateactivity.setGraduateStatus(studentList);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$setGraduate</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int cancelGraduate(List studentList) throws PlatformException {
		OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
		int suc = graduateactivity.cancelGraduateStatus(studentList);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$cancelGraduate</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int checkElective(String open_course_id, String student_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select id from entity_elective where teachclass_id in(select id from entity_teach_class  where open_course_id = '"
				+ open_course_id + "' ) and student_id = '" + student_id + "'";
		int count = db.countselect(sql);
		return count;
	}

	public int checkPreElective(String open_course_id, String student_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select id from entity_elective where teachclass_id in(select id from entity_teach_class  where open_course_id = '"
				+ open_course_id
				+ "' ) and student_id = '"
				+ student_id
				+ "' and status = '1'";
		return db.countselect(sql);
	}

	public int regStudents(String[] user_id, String semester_id)
			throws PlatformException {
		if (basicManagePriv.registerSingle == 1) {
			List studentList = new ArrayList();
			for (int i = 0; i < user_id.length; i++) {
				studentList.add(user_id[i]);
			}
			OracleRegister register = new OracleRegister();
			int suc = register.regStudents(semester_id, studentList);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$regStudents</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��ע��ѧ���Ȩ��");
		}

	}

	public int unRegStudents(String[] user_id, String semester_id)
			throws PlatformException {
		if (basicManagePriv.registerSingle == 1) {
			List studentList = new ArrayList();
			for (int i = 0; i < user_id.length; i++) {
				studentList.add(user_id[i]);
			}
			OracleRegister register = new OracleRegister();
			int suc = register.unRegStudents(semester_id, studentList);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$unRegStudents</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��ע��ѧ���Ȩ��");
		}

	}

	public List getGraduatedStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
			List searchProperties = new ArrayList();

			if (site_id != null && !site_id.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", site_id, "="));
			}

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edu_type_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}

			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));

			}

			if (reg_no != null && !reg_no.equals("")) {
				searchProperties.add(new SearchProperty("reg_no", reg_no,
						"like"));
			}

			if (id_card != null && !id_card.equals("")) {
				searchProperties.add(new SearchProperty("id_card", id_card,
						"like"));
			}

			if (phone != null && !phone.equals("")) {
				searchProperties
						.add(new SearchProperty("phone", phone, "like"));
			}
			searchProperties.add(new SearchProperty("u.isgraduated", "1", "="));
			OracleBasicUserList studentList = new OracleBasicUserList();
			if (page != null) {
				return studentList.getStudents(page, searchProperties, null);
			} else {
				return studentList.getStudents(searchProperties, null);
			}
		} else {
			throw new PlatformException("��û����1�ҵѧ���Ȩ��");
		}
	}

	public List getGraduatedStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
			List searchProperties = new ArrayList();
			String site_id = basicManagePriv.getSite_id();

			if (site_id != null && !site_id.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", site_id, "="));
			}

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edu_type_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}

			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));

			}

			if (reg_no != null && !reg_no.equals("")) {
				searchProperties.add(new SearchProperty("reg_no", reg_no,
						"like"));
			}

			if (id_card != null && !id_card.equals("")) {
				searchProperties.add(new SearchProperty("id_card", id_card,
						"like"));
			}

			if (phone != null && !phone.equals("")) {
				searchProperties
						.add(new SearchProperty("phone", phone, "like"));
			}
			searchProperties.add(new SearchProperty("isgraduated", "1", "="));
			OracleBasicUserList studentList = new OracleBasicUserList();
			if (page != null) {
				return studentList.getStudents(page, searchProperties, null);
			} else {
				return studentList.getStudents(searchProperties, null);
			}
		} else {
			throw new PlatformException("��û����1�ҵѧ���Ȩ��");
		}
	}

	public int getGraduatedStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
			List searchProperties = new ArrayList();

			if (site_id != null && !site_id.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", site_id, "="));
			}

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edu_type_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}

			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));

			}

			if (reg_no != null && !reg_no.equals("")) {
				searchProperties.add(new SearchProperty("reg_no", reg_no,
						"like"));
			}

			if (id_card != null && !id_card.equals("")) {
				searchProperties.add(new SearchProperty("id_card", id_card,
						"like"));
			}

			if (phone != null && !phone.equals("")) {
				searchProperties
						.add(new SearchProperty("phone", phone, "like"));
			}
			searchProperties.add(new SearchProperty("isgraduated", "1", "="));
			OracleBasicUserList studentList = new OracleBasicUserList();
			return studentList.getStudentsNum(searchProperties);

		} else {
			throw new PlatformException("��û����1�ҵѧ���Ȩ��");
		}
	}

	public int getGraduatedStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
			List searchProperties = new ArrayList();
			String site_id = basicManagePriv.getSite_id();
			if (site_id != null && !site_id.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", site_id, "="));
			}

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edu_type_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}

			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));

			}

			if (reg_no != null && !reg_no.equals("")) {
				searchProperties.add(new SearchProperty("reg_no", reg_no,
						"like"));
			}

			if (id_card != null && !id_card.equals("")) {
				searchProperties.add(new SearchProperty("id_card", id_card,
						"like"));
			}

			if (phone != null && !phone.equals("")) {
				searchProperties
						.add(new SearchProperty("phone", phone, "like"));
			}
			searchProperties.add(new SearchProperty("isgraduated", "1", "="));
			OracleBasicUserList studentList = new OracleBasicUserList();
			return studentList.getStudentsNum(searchProperties);

		} else {
			throw new PlatformException("��û����1�ҵѧ���Ȩ��");
		}
	}

	public List getRegStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException {
		// if (basicManagePriv.getRegisterStudent == 1) {
		List searchProperties = new ArrayList();

		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("u.site_id", site_id, "="));
		}

		if (major_id != null && !major_id.equals("")) {
			searchProperties
					.add(new SearchProperty("u.major_id", major_id, "="));
		}

		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("u.edutype_id",
					edu_type_id, "="));
		}

		if (grade_id != null && !grade_id.equals("")) {
			searchProperties
					.add(new SearchProperty("u.grade_id", grade_id, "="));
		}

		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("u.name", name, "like"));

		}

		if (reg_no != null && !reg_no.equals("")) {
			searchProperties
					.add(new SearchProperty("u.reg_no", reg_no, "like"));
		}

		if (id_card != null && !id_card.equals("")) {
			searchProperties.add(new SearchProperty("u.id_card", id_card,
					"like"));
		}

		if (phone != null && !phone.equals("")) {
			searchProperties.add(new SearchProperty("u.phone", phone, "like"));
		}
		OracleRegister register = new OracleRegister();
		return register.getRegStudents(page, searchProperties, null);
		// } else {
		// throw new PlatformException("��û�����ע��ѧ����Ϣ��Ȩ��");
		// }
	}

	public int getRegStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		// if (basicManagePriv.getRegisterStudent == 1) {
		List searchProperties = new ArrayList();

		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("u.site_id", site_id, "="));
		}

		if (major_id != null && !major_id.equals("")) {
			searchProperties
					.add(new SearchProperty("u.major_id", major_id, "="));
		}

		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("u.edutype_id",
					edu_type_id, "="));
		}

		if (grade_id != null && !grade_id.equals("")) {
			searchProperties
					.add(new SearchProperty("u.grade_id", grade_id, "="));
		}

		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("u.name", name, "like"));

		}

		if (reg_no != null && !reg_no.equals("")) {
			searchProperties
					.add(new SearchProperty("u.reg_no", reg_no, "like"));
		}

		if (id_card != null && !id_card.equals("")) {
			searchProperties.add(new SearchProperty("u.id_card", id_card,
					"like"));
		}

		if (phone != null && !phone.equals("")) {
			searchProperties.add(new SearchProperty("u.phone", phone, "like"));
		}
		OracleRegister register = new OracleRegister();
		return register.getRegStudentsNum(searchProperties);
		// } else {
		// throw new PlatformException("��û�����ע��ѧ����Ϣ��Ȩ��");
		// }
	}

	public List getRegStudents(String semester_id, Page page, String id,
			String reg_no, String name, String id_card, String phone,
			String site_id, String major_id, String edu_type_id, String grade_id)
			throws PlatformException {
		// if (basicManagePriv.getRegisterStudent == 1) {
		List searchProperties = new ArrayList();

		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("u.site_id", site_id, "="));
		}

		if (major_id != null && !major_id.equals("")) {
			searchProperties
					.add(new SearchProperty("u.major_id", major_id, "="));
		}

		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("u.edutype_id",
					edu_type_id, "="));
		}

		if (grade_id != null && !grade_id.equals("")) {
			searchProperties
					.add(new SearchProperty("u.grade_id", grade_id, "="));
		}

		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("u.name", name, "like"));

		}

		if (reg_no != null && !reg_no.equals("")) {
			searchProperties
					.add(new SearchProperty("u.reg_no", reg_no, "like"));
		}

		if (id_card != null && !id_card.equals("")) {
			searchProperties.add(new SearchProperty("u.id_card", id_card,
					"like"));
		}

		if (phone != null && !phone.equals("")) {
			searchProperties.add(new SearchProperty("u.phone", phone, "like"));
		}
		searchProperties
				.add(new SearchProperty("semester_id", semester_id, "="));
		List orderproperty = new ArrayList();
		orderproperty.add(new OrderProperty("reg_no"));
		OracleRegister register = new OracleRegister();
		return register.getRegStudents(page, searchProperties, orderproperty);
		// } else {
		// throw new PlatformException("��û�����ע��ѧ����Ϣ��Ȩ��");
		// }
	}

	public int getRegStudentsNum(String semester_id, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException {
		// if (basicManagePriv.getRegisterStudent == 1) {
		List searchProperties = new ArrayList();

		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("u.site_id", site_id, "="));
		}

		if (major_id != null && !major_id.equals("")) {
			searchProperties
					.add(new SearchProperty("u.major_id", major_id, "="));
		}

		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("u.edutype_id",
					edu_type_id, "="));
		}

		if (grade_id != null && !grade_id.equals("")) {
			searchProperties
					.add(new SearchProperty("u.grade_id", grade_id, "="));
		}

		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("u.name", name, "like"));

		}

		if (reg_no != null && !reg_no.equals("")) {
			searchProperties
					.add(new SearchProperty("u.reg_no", reg_no, "like"));
		}

		if (id_card != null && !id_card.equals("")) {
			searchProperties.add(new SearchProperty("u.id_card", id_card,
					"like"));
		}

		if (phone != null && !phone.equals("")) {
			searchProperties.add(new SearchProperty("u.phone", phone, "like"));
		}
		searchProperties
				.add(new SearchProperty("semester_id", semester_id, "="));
		OracleRegister register = new OracleRegister();
		return register.getRegStudentsNum(searchProperties);
		// } else {
		// throw new PlatformException("��û�����ע��ѧ����Ϣ��Ȩ��");
		// }
	}

	public List getAllGradeEduMajorGroups() throws PlatformException {
		String sql = "select distinct g.id as grade_id,g.name as grade_name,t.id as edutype_id,"
				+ "t.name as edutype_name,m.id as major_id,m.name as major_name "
				+ "from entity_grade_info g,entity_edu_type t,entity_major_info m,entity_student_info u "
				+ "where u.isgraduated=0 and t.id=u.edutype_id and g.id=u.grade_id and m.id=u.major_id and m.id<>'0' order by g.id,t.id,m.id";
		dbpool db = new dbpool();
		ArrayList groupList = new ArrayList();
		MyResultSet rs = null;
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				GradeEduMajorGroup group = new GradeEduMajorGroup();
				group.setEdutype_id(rs.getString("edutype_id"));
				group.setEdutype_name(rs.getString("edutype_name"));
				group.setGrade_id(rs.getString("grade_id"));
				group.setGrade_name(rs.getString("grade_name"));
				group.setMajor_id(rs.getString("major_id"));
				group.setMajor_name(rs.getString("major_name"));
				groupList.add(group);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return groupList;
	}

	public List getRegGradeEduMajorGroups() throws PlatformException {
		String sql = "select distinct g.id as grade_id,g.name as grade_name,t.id as edutype_id,"
				+ "t.name as edutype_name,m.id as major_id,m.name as major_name "
				+ "from entity_grade_info g,entity_edu_type t,entity_major_info m,entity_student_info u,entity_register_info r,entity_semester_info se "
				+ "where u.id=r.user_id and se.id=r.semester_id and u.isgraduated=0 and t.id=u.edutype_id and g.id=u.grade_id and m.id=u.major_id and se.selected=1 and m.id<>'0' "
				+ "order by g.id,t.id,m.id";
		dbpool db = new dbpool();
		ArrayList groupList = new ArrayList();
		MyResultSet rs = null;
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				GradeEduMajorGroup group = new GradeEduMajorGroup();
				group.setEdutype_id(rs.getString("edutype_id"));
				group.setEdutype_name(rs.getString("edutype_name"));
				group.setGrade_id(rs.getString("grade_id"));
				group.setGrade_name(rs.getString("grade_name"));
				group.setMajor_id(rs.getString("major_id"));
				group.setMajor_name(rs.getString("major_name"));
				groupList.add(group);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return groupList;
	}

	public int getAllGradeEduMajorGroupsNum() throws PlatformException {
		String sql = "select distinct g.id as grade_id,g.name as grade_name,t.id as edutype_id,"
				+ "t.name as edutype_name,m.id as major_id,m.name as major_name "
				+ "from entity_grade_info g,entity_edu_type t,entity_major_info m,entity_student_info u "
				+ "where u.isgraduated=0 and t.id=u.edutype_id and g.id=u.grade_id and m.id=u.major_id and m.id<>'0' order by g.id,t.id,m.id";
		dbpool db = new dbpool();
		return db.countselect(sql);
	}

	public int getRegGradeEduMajorGroupsNum() throws PlatformException {
		String sql = "select distinct g.id as grade_id,g.name as grade_name,t.id as edutype_id,"
				+ "t.name as edutype_name,m.id as major_id,m.name as major_name "
				+ "from entity_grade_info g,entity_edu_type t,entity_major_info m,entity_student_info u,entity_register_info r,entity_semester_info se "
				+ "where u.id=r.user_id and se.id=r.semester_id and u.isgraduated=0 and t.id=u.edutype_id and g.id=u.grade_id and m.id=u.major_id and se.selected=1 and m.id<>'0' "
				+ "order by g.id,t.id,m.id";
		dbpool db = new dbpool();
		return db.countselect(sql);
	}

	public int regStudentsBatch(String semester_id, String[] id,
			String[] major_id, String[] edutype_id, String[] grade_id)
			throws PlatformException {
		if (basicManagePriv.registerBatch == 1) {
			OracleRegister register = new OracleRegister();
			int suc = register.regStudentsBatch(semester_id, id, major_id,
					edutype_id, grade_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$regStudentsBatch</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û����ע��ѧ���Ȩ��");
		}
	}

	public int unRegStudentsBatch(String semester_id, String[] id,
			String[] major_id, String[] edutype_id, String[] grade_id)
			throws PlatformException {
		if (basicManagePriv.registerBatch == 1) {
			OracleRegister register = new OracleRegister();
			int suc = register.unRegStudentsBatch(semester_id, id, major_id,
					edutype_id, grade_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$unRegStudentsBatch</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û����ע��ѧ���Ȩ��");
		}
	}

	public int unRegStudents(String[] register_id) throws PlatformException {
		if (basicManagePriv.registerSingle == 1) {
			OracleRegister register = new OracleRegister();
			int suc = register.unRegStudents(register_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$unRegStudents</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û�е���ȡ��ע��ѧ���Ȩ��");
		}
	}

	public List getUnOpenCourses(Page page, String semester_id, String major_id)
			throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			OracleSemester semester = new OracleSemester();
			semester.setId(semester_id);
			List searchProperties = new ArrayList();

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			return basicActivityList.searchUnOpenCourses(page,
					searchProperties, null, semester);
		} else {
			throw new PlatformException("��û��ѧ�ڿ���Ȩ��");
		}
	}

	public int getUnOpenCoursesNum(String semester_id, String major_id)
			throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			OracleSemester semester = new OracleSemester();
			semester.setId(semester_id);
			List searchProperties = new ArrayList();

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			return basicActivityList.searchUnOpenCourseNum(searchProperties,
					semester);
		} else {
			throw new PlatformException("��û��ѧ�ڿ���Ȩ��");
		}
	}

	public int isRegStudents(String semester_id, String user_id)
			throws PlatformException {
		OracleRegister or = new OracleRegister();
		return or.isRegStudent(semester_id, user_id);
	}

	public List getElectives(Page page, String major_id, String edu_type_id,
			String grade_id, String semester_id) throws PlatformException {
		// TODO Auto-generated method stub
		String site_id = basicManagePriv.getSite_id();
		if (basicManagePriv.confirmElectiveInfo == 1) {
			List searchProperties = new ArrayList();
			if (site_id != null && !site_id.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", site_id, "="));
			}
			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			if (semester_id != null && !semester_id.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semester_id, "="));
			}
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.searchElective(page, searchProperties,
					null, (Student) null);
		} else {
			throw new PlatformException("��û��ȷ��ѡ�ε�Ȩ��");
		}
	}

	public int getElectivesNum(String major_id, String edu_type_id,
			String grade_id, String semester_id) throws PlatformException {
		String site_id = basicManagePriv.getSite_id();
		if (basicManagePriv.confirmElectiveInfo == 1) {
			List searchProperties = new ArrayList();
			if (site_id != null && !site_id.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", site_id, "="));
			}
			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			if (semester_id != null && !semester_id.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semester_id, "="));
			}
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.searchElectiveNum(searchProperties,
					(Student) null);
		} else {
			throw new PlatformException("��û��ȷ��ѡ�ε�Ȩ��");
		}
	}

	public List getOpenCourses(String open_course_id, String semesterId)
			throws PlatformException {
		OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
		List searchproperty = new ArrayList();
		if (open_course_id != null && !open_course_id.equals("")
				&& !open_course_id.equals("null"))
			searchproperty.add(new SearchProperty("id", open_course_id));
		if (semesterId != null && !semesterId.equals("")
				&& !semesterId.equals("null"))
			searchproperty.add(new SearchProperty("semester_id", semesterId));
		return basicActivityList.searchOpenCourse(null, searchproperty, null);
	}

	public List getElectiveStudentsByCourseAndSemester(Page page,
			String siteId, String semesterId, String courseId)
			throws PlatformException {
		OracleBasicActivityList list = new OracleBasicActivityList();
		return list.getElectiveStudentsByCourseAndSemester(page, siteId,
				semesterId, courseId);
	}

	public int getElectiveStudentsByCourseAndSemesterNum(String siteId,
			String semesterId, String courseId) throws PlatformException {
		OracleBasicActivityList list = new OracleBasicActivityList();
		return list.getElectiveStudentsByCourseAndSemesterNum(siteId,
				semesterId, courseId);
	}

	public List getElectiveByUserIdAndSemester(Page page, String userId,
			String semesterId) throws PlatformException {
		if (basicManagePriv.getElectiveCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();
			List orderProperties = new ArrayList();
			OracleStudent student = null;
			if (userId != null && !userId.equals("")) {
				student = new OracleStudent();
				student.setId(userId);
			}
			if (semesterId != null && !semesterId.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semesterId, "="));
			}
			return basicActivityList.getElectiveByUserIdAndSemester(page,
					searchProperties, orderProperties, student);
		} else {
			throw new PlatformException("��û�в�ѯѡ�ε�Ȩ��");
		}
	}

	public int getElectiveByUserIdAndSemesterNum(String userId,
			String semesterId) throws PlatformException {
		if (basicManagePriv.getElectiveCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();
			List orderProperties = new ArrayList();
			OracleStudent student = null;
			if (userId != null && !userId.equals("")) {
				student = new OracleStudent();
				student.setId(userId);
			}
			if (semesterId != null && !semesterId.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semesterId, "="));
			}
			return basicActivityList.getElectiveByUserIdAndSemesterNum(
					searchProperties, orderProperties, student);
		} else {
			throw new PlatformException("��û�в�ѯѡ�ε�Ȩ��");
		}
	}

	public List getCourseIDList(String semester_id) throws PlatformException {
		OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
		return basicActivityList.getCourseIdBySemesterId(semester_id);
	}

	public int deleteSiteTeachersFromTeachClass(String[] siteteacherIdSet,
			String teachclass_id) throws PlatformException {
		if (basicManagePriv.appointSiteTeacherForCourse == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachclass_id);
			int suc = teachclass
					.removeSiteTeachersFromTeachClass(siteteacherIdSet);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTeachersToTeachClass</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û�и�γ�ָ����ʦ��Ȩ��");
		}
	}

	public List getCondtionEleNumExecutePlanCourses(Page page,
			String semesterId, String siteId, String majorId, String gradeId,
			String eduTypeId, String reg_no) throws PlatformException {
		OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
		OrderProperty order = new OrderProperty("course_id", null);
		List orderproperty = new ArrayList();
		orderproperty.add(order);
		return basicActivityList.getExecutePlanWithConditionEleNum(page,
				semesterId, siteId, majorId, eduTypeId, gradeId, reg_no,
				orderproperty);
	}

	public int getStatusChangeStudentsNum(String statusChangeType)
			throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		return changeStatus.getStatusChangeStudentsNum(statusChangeType);
	}

	public List getChangeMajorStudents(Page page) throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		return changeStatus.getChangeMajorStudents(page);
	}

	public int getChangeMajorStudentsNum() throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		return changeStatus.getChangeMajorStudentsNum();
	}

	public List getStatusChangeStudents(Page page, String statusChangeType)
			throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		return changeStatus.getStatusChangeStudents(page, statusChangeType);
	}

	public int getStatusChangeStudentRecordsNum(String site_id,
			String statusChangeType) throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("site_id", site_id, "="));
		return changeStatus.getStatusChangeStudentRecodsNum(searchProperty,
				statusChangeType);
	}

	public List getStatusChangeStudentRecords(Page page, String site_id,
			String statusChangeType) throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		searchProperty.add(new SearchProperty("site_id", site_id, "="));
		orderProperty.add(new OrderProperty("s_date", OrderProperty.DESC));
		orderProperty.add(new OrderProperty("e_date", OrderProperty.DESC));
		return changeStatus.getStatusChangeStudentRecods(page, searchProperty,
				orderProperty, statusChangeType);
	}

	public List getChangeMajorStudents(Page page, String site_id)
			throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		return changeStatus.getChangeMajorStudents(page);
	}

	public int getChangeMajorStudentsNum(String site_id)
			throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		return changeStatus.getChangeMajorStudentsNum();
	}

	public int getChangeMajorStudentsNum(String site_id, String reg_no)
			throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		return changeStatus.getChangeMajorStudentsNum(site_id, reg_no);
	}

	public int getChangeMajorApplysNum(String site_id) throws PlatformException {
		OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
		List searchPropertis = new ArrayList();
		searchPropertis.add(new SearchProperty("old_site_id", site_id, "="));
		return basicActivityList.getChangeMajorApplysNum(searchPropertis);
	}

	public List getChangeMajorStudents(Page page, String site_id, String reg_no)
			throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		return changeStatus.getChangeMajorStudents(page, site_id, reg_no);
	}

	public ChangeMajorStudent getChangeMajorStudent(String changeMajorId)
			throws PlatformException {
		OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
		return changeStatus.getChangeMajorStudent(changeMajorId);
	}

	public List getChangeMajorApplys(Page page, String site_id)
			throws PlatformException {
		OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
		List searchProperties = new ArrayList();
		searchProperties.add(new SearchProperty("old_site_id", site_id, "="));
		List orderProperties = new ArrayList();
		orderProperties
				.add(new OrderProperty("apply_date", OrderProperty.DESC));
		return basicActivityList.getChangeMajorApplys(page, searchProperties,
				orderProperties);
	}

	public int changeMajorApplyStatus(String id, String status)
			throws PlatformException {
		OracleChangeMajorApply apply = new OracleChangeMajorApply(id);
		apply.setId(id);
		int suc = apply.changeStauts(status);

		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$changeMajorApplyStatus</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;

	}

	public int setDegreeStatus(List id, List id1) throws PlatformException {
		OracleDegreeActivity degreeActivity = new OracleDegreeActivity();
		int suc = degreeActivity.setDegreeStatus(id, id1);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getManagerId()
								+ "</whaty><whaty>BEHAVIOR$|$setDegreeStatus</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.SITEMANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public List getGraduateStat(String major_id, String edu_type_id,
			String grade_id, String site_id) throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
			OracleBasicActivityList activityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();
			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", site_id, "="));
			}

			return activityList.getGraduateStat(null, searchProperties, null);
		} else {
			throw new PlatformException("��û����1�ҵѧ���Ȩ��");
		}
	}

	public List getDegreeStat(String major_id, String edu_type_id,
			String grade_id, String site_id) throws PlatformException {
		if (basicManagePriv.totalDegreedStudent == 1) {
			OracleBasicActivityList activityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();
			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			if (site_id != null && !site_id.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", site_id, "="));
			}

			return activityList.getDegreeStat(null, searchProperties, null);
		} else {
			throw new PlatformException("��û��ѧλ����ͳ�Ƶ�Ȩ��");
		}
	}

	public List getDegreedStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.getDegreedStudent == 1) {
			List searchProperties = new ArrayList();

			if (site_id != null && !site_id.equals("")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("u.major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("u.edutype_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}

			if (name != null && !name.equals("")) {
				searchProperties
						.add(new SearchProperty("u.name", name, "like"));

			}

			if (reg_no != null && !reg_no.equals("")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}

			if (id_card != null && !id_card.equals("")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
						"like"));
			}

			if (phone != null && !phone.equals("")) {
				searchProperties.add(new SearchProperty("u.phone", phone,
						"like"));
			}
			searchProperties.add(new SearchProperty("u.isdegreed", "1", "="));

			OracleBasicUserList studentList = new OracleBasicUserList();
			if (page != null) {
				return studentList.getAllStudents(page, searchProperties, null);
			} else {
				return studentList.getAllStudents(searchProperties, null);
			}
		} else {
			throw new PlatformException("��û�в�ѯѧλ�ɼ���Ȩ��");
		}
	}

	public int getDegreedStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getDegreedStudent == 1) {
			List searchProperties = new ArrayList();

			if (site_id != null && !site_id.equals("")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}

			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("u.major_id", major_id,
						"="));
			}

			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("u.edutype_id",
						edu_type_id, "="));
			}

			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}

			if (name != null && !name.equals("")) {
				searchProperties
						.add(new SearchProperty("u.name", name, "like"));

			}

			if (reg_no != null && !reg_no.equals("")) {
				searchProperties.add(new SearchProperty("u.reg_no", reg_no,
						"like"));
			}

			if (id_card != null && !id_card.equals("")) {
				searchProperties.add(new SearchProperty("u.id_card", id_card,
						"like"));
			}

			if (phone != null && !phone.equals("")) {
				searchProperties.add(new SearchProperty("u.phone", phone,
						"like"));
			}
			searchProperties.add(new SearchProperty("u.isdegreed", "1", "="));

			OracleBasicUserList studentList = new OracleBasicUserList();
			return studentList.getAllStudentsNum(searchProperties);

		} else {
			throw new PlatformException("��û�в�ѯѧλ�ɼ���Ȩ��");
		}
	}

	public List getUniteExamScores(Page page, String siteId, String gradeId,
			String eduTypeId, String majorId, String regNo, String name)
			throws PlatformException {
		if (basicManagePriv.getUniteExamScores == 1) {
			OracleBasicActivityList list = new OracleBasicActivityList();
			List searchPropertys = new ArrayList();
			List orderPropertys = new ArrayList();
			orderPropertys.add(new OrderProperty("reg_no", "ASC"));
			orderPropertys.add(new OrderProperty("course_id", "ASC"));
			if (siteId != null && !siteId.equals("") && !siteId.equals("null"))
				searchPropertys.add(new SearchProperty("site_id", siteId, "="));
			if (gradeId != null && !gradeId.equals("")
					&& !gradeId.equals("null"))
				searchPropertys
						.add(new SearchProperty("grade_id", gradeId, "="));
			if (eduTypeId != null && !eduTypeId.equals("")
					&& !eduTypeId.equals("null"))
				searchPropertys.add(new SearchProperty("edutype_id", eduTypeId,
						"="));
			if (majorId != null && !majorId.equals("")
					&& !majorId.equals("null"))
				searchPropertys
						.add(new SearchProperty("major_id", majorId, "="));
			if (regNo != null && !regNo.equals("") && !regNo.equals("null"))
				searchPropertys.add(new SearchProperty("reg_no", regNo, "="));
			if (name != null && !name.equals("") && !name.equals("null"))
				searchPropertys.add(new SearchProperty("name", name, "like"));

			return list.getUniteExamScores(page, searchPropertys,
					orderPropertys);
		} else {
			throw new PlatformException("��û�в�ѯͳ���ɼ���Ȩ��");
		}
	}

	public int getUniteExamScoreNum(String siteId, String gradeId,
			String eduTypeId, String majorId, String regNo, String name)
			throws PlatformException {
		if (basicManagePriv.getUniteExamScores == 1) {
			OracleBasicActivityList list = new OracleBasicActivityList();
			List searchPropertys = new ArrayList();
			if (siteId != null && !siteId.equals("") && !siteId.equals("null"))
				searchPropertys.add(new SearchProperty("site_id", siteId, "="));
			if (gradeId != null && !gradeId.equals("")
					&& !gradeId.equals("null"))
				searchPropertys
						.add(new SearchProperty("grade_id", gradeId, "="));
			if (eduTypeId != null && !eduTypeId.equals("")
					&& !eduTypeId.equals("null"))
				searchPropertys.add(new SearchProperty("edutype_id", eduTypeId,
						"="));
			if (majorId != null && !majorId.equals("")
					&& !majorId.equals("null"))
				searchPropertys
						.add(new SearchProperty("major_id", majorId, "="));
			if (regNo != null && !regNo.equals("") && !regNo.equals("null"))
				searchPropertys.add(new SearchProperty("reg_no", regNo, "="));
			if (name != null && !name.equals("") && !name.equals("null"))
				searchPropertys.add(new SearchProperty("name", name, "like"));

			return list.getUniteExamScoreNum(searchPropertys);
		} else {
			throw new PlatformException("��û�в�ѯͳ���ɼ���Ȩ��");
		}
	}

	public List getOpenCourses(Page page, String semesterId, String major_id,
			String site_id) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			SearchProperty search = new SearchProperty("semester_id",
					semesterId);
			OrderProperty order = new OrderProperty("course_id", null);
			List searchproperty = new ArrayList();
			List orderproperty = new ArrayList();
			searchproperty.add(search);
			orderproperty.add(order);
			return basicActivityList.searchOpenCourseWithEleNum(page,
					searchproperty, orderproperty, site_id);
		} else {
			throw new PlatformException("��û�в�ѯѧ�ڿ��ε�Ȩ��");
		}
	}

	public int getOpenCoursesNum(String major_id, String semesterId,
			String site_id) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			SearchProperty search = new SearchProperty("semester_id",
					semesterId);
			List searchproperty = new ArrayList();
			searchproperty.add(search);
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchproperty.add(new SearchProperty("major_id", major_id));

			// searchproperty.add(new SearchProperty("major_id", basicManagePriv
			// .getMajorList(), "in"));

			return basicActivityList.searchOpenCourseNum(searchproperty,
					site_id);
		} else {
			throw new PlatformException("��û�����ѧ�ڿ��ε�Ȩ��");
		}
	}

	public int getElectiveStatNum(String teachclass_id, String site_id)
			throws PlatformException {
		if (basicManagePriv.electiveBatchBySite == 1) {
			OracleBasicActivityList activitylist = new OracleBasicActivityList();
			List searchList = new ArrayList();
			if (teachclass_id != null && !teachclass_id.equals("")
					&& !teachclass_id.equals("null"))
				searchList.add(new SearchProperty("a.teachclass_id",
						teachclass_id, "="));
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null"))
				searchList.add(new SearchProperty("b.site_id", site_id, "="));
			int electiveNum = electiveNum = activitylist
					.searchElectiveStatNum(searchList);
			return electiveNum;
		} else {
			throw new PlatformException("��û�н�ѧվ��ѡ�ε�Ȩ��");
		}
	}

	public List getElectiveStat(Page page, String teachclass_id, String site_id)
			throws PlatformException {
		if (basicManagePriv.electiveBatchBySite == 1) {
			OracleBasicActivityList activitylist = new OracleBasicActivityList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			if (teachclass_id != null && !teachclass_id.equals("")
					&& !teachclass_id.equals("null"))
				searchList.add(new SearchProperty("teachclass_id",
						teachclass_id, "="));
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null"))
				searchList.add(new SearchProperty("site_id", site_id, "="));
			orderList.add(new OrderProperty("site_id"));
			return activitylist.searchElectiveStat(page, searchList, orderList);
		} else {
			throw new PlatformException("��û�����ѡ��ѧ���Ȩ��");
		}
	}

	public int getElectiveStatNum(String teachclass_id, String site_id,
			String gradeId, String eduTypeId, String majorId)
			throws PlatformException {
		if (basicManagePriv.electiveBatchBySite == 1) {
			OracleBasicActivityList activitylist = new OracleBasicActivityList();
			List searchList = new ArrayList();
			if (teachclass_id != null && !teachclass_id.equals("")
					&& !teachclass_id.equals("null"))
				searchList.add(new SearchProperty("a.teachclass_id",
						teachclass_id, "="));
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null"))
				searchList.add(new SearchProperty("b.site_id", site_id, "="));
			if (gradeId != null && !gradeId.equals("")
					&& !gradeId.equals("null"))
				searchList.add(new SearchProperty("grade_id", gradeId, "="));
			if (eduTypeId != null && !eduTypeId.equals("")
					&& !eduTypeId.equals("null"))
				searchList
						.add(new SearchProperty("edutype_id", eduTypeId, "="));
			if (majorId != null && !majorId.equals("")
					&& !majorId.equals("null"))
				searchList.add(new SearchProperty("major_id", majorId, "="));
			int electiveNum = electiveNum = activitylist
					.searchElectiveStatNum(searchList);
			return electiveNum;
		} else {
			throw new PlatformException("��û�н�ѧվ��ѡ�ε�Ȩ��");
		}
	}

	public List getElectiveStat(Page page, String teachclass_id,
			String site_id, String gradeId, String eduTypeId, String majorId)
			throws PlatformException {
		if (basicManagePriv.electiveBatchBySite == 1) {
			OracleBasicActivityList activitylist = new OracleBasicActivityList();
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			if (teachclass_id != null && !teachclass_id.equals("")
					&& !teachclass_id.equals("null"))
				searchList.add(new SearchProperty("teachclass_id",
						teachclass_id, "="));
			if (site_id != null && !site_id.equals("")
					&& !site_id.equals("null"))
				searchList.add(new SearchProperty("site_id", site_id, "="));
			if (gradeId != null && !gradeId.equals("")
					&& !gradeId.equals("null"))
				searchList.add(new SearchProperty("grade_id", gradeId, "="));
			if (eduTypeId != null && !eduTypeId.equals("")
					&& !eduTypeId.equals("null"))
				searchList
						.add(new SearchProperty("edutype_id", eduTypeId, "="));
			if (majorId != null && !majorId.equals("")
					&& !majorId.equals("null"))
				searchList.add(new SearchProperty("major_id", majorId, "="));
			orderList.add(new OrderProperty("site_id"));
			return activitylist.searchElectiveStat(page, searchList, orderList);
		} else {
			throw new PlatformException("��û�����ѡ��ѧ���Ȩ��");
		}
	}

	public List getExecutePlanCourses(Page page, String semesterId,
			String majorId, String eduTypeId, String gradeId)
			throws PlatformException {
		if (basicManagePriv.electiveBatchByStudent == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			ArrayList orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("course_id"));
			return basicActivityList.getExecutePlanCourses(page, semesterId,
					gradeId, eduTypeId, majorId, null, null, orderProperty);

		} else {
			throw new PlatformException("��û��ѧ����ѡ�ε�Ȩ��");
		}
	}

	public Map getElectiveStudentsByExecutePlanCourses(String semesterId,
			String majorId, String eduTypeId, String gradeId)
			throws PlatformException {
		if (basicManagePriv.electiveBatchByStudent == 1) {
			OracleBasicActivityList list = new OracleBasicActivityList();
			return list.getElectiveStudentsByExecutePlanCourses(semesterId,
					majorId, eduTypeId, gradeId);

		} else {
			throw new PlatformException("��û��ѧ����ѡ�ε�Ȩ�ޣ�");
		}

	}

	public List getOpenCourses(Page page, String semesterId, String major_id,
			String site_id, String eduType_Id, String grade_Id)
			throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchproperty = new ArrayList();

			if (site_id != null && !site_id.equals(""))
				searchproperty.add(new SearchProperty("site_id", site_id));

			if (major_id != null && !major_id.equals(""))
				searchproperty.add(new SearchProperty("major_id", major_id));

			if (grade_Id != null && !grade_Id.equals(""))
				searchproperty.add(new SearchProperty("grade_id", grade_Id));

			if (eduType_Id != null && !eduType_Id.equals(""))
				searchproperty
						.add(new SearchProperty("edutype_id", eduType_Id));
			return basicActivityList.searchOpenCourses(page, searchproperty,
					null, semesterId);
		} else {
			throw new PlatformException("��û�в�ѯѧ�ڿ��ε�Ȩ��");
		}
	}

	public int getOpenCoursesNum(Page page, String semesterId, String major_id,
			String site_id, String eduType_Id, String grade_Id)
			throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchproperty = new ArrayList();

			if (site_id != null && !site_id.equals(""))
				searchproperty.add(new SearchProperty("site_id", site_id));

			if (major_id != null && !major_id.equals(""))
				searchproperty.add(new SearchProperty("major_id", major_id));

			if (grade_Id != null && !grade_Id.equals(""))
				searchproperty.add(new SearchProperty("grade_id", grade_Id));

			if (eduType_Id != null && !eduType_Id.equals(""))
				searchproperty
						.add(new SearchProperty("edutype_id", eduType_Id));
			return basicActivityList.searchOpenCoursesNum(page, searchproperty,
					null, semesterId);
		} else {
			throw new PlatformException("��û�в�ѯѧ�ڿ��ε�Ȩ��");
		}
	}

	public int getElectiveNum(String teachclass_id, String site_id)
			throws PlatformException {
		List teachclassList = new ArrayList();
		int electiveNum = 0;
		OracleTeachClass teachclass = new OracleTeachClass(teachclass_id);
		teachclassList.add(teachclass);
		List searchproperty = new ArrayList();
		if (site_id != null && !site_id.equals(""))
			searchproperty.add(new SearchProperty("site_id", site_id, "="));
		OracleBasicActivityList activitylist = new OracleBasicActivityList();
		OrderProperty order = new OrderProperty("reg_no", null);
		List orderproperty = new ArrayList();
		orderproperty.add(order);
		electiveNum = activitylist.searchElectiveNum(searchproperty,
				teachclassList);
		return electiveNum;
	}

	public List getElectives(Page page, String teachclass_id, String site_id)
			throws PlatformException {
		List teachclassList = new ArrayList();
		List electiveList = new ArrayList();
		try {
			OracleTeachClass teachclass = new OracleTeachClass(teachclass_id);
			teachclassList.add(teachclass);
			List searchproperty = new ArrayList();
			if (site_id != null && !site_id.equals(""))
				searchproperty.add(new SearchProperty("site_id", site_id, "="));
			OracleBasicActivityList activitylist = new OracleBasicActivityList();
			OrderProperty order = new OrderProperty("reg_no", null);
			List orderproperty = new ArrayList();
			orderproperty.add(order);
			electiveList = activitylist.searchElective(page, searchproperty,
					orderproperty, teachclassList);
		} catch (Exception e) {
			
		} finally {
			return electiveList;
		}
	}

	public int getElectiveNum(String teachclass_id, String site_id,
			String gradeId, String eduTypeId, String majorId)
			throws PlatformException {
		List teachclassList = new ArrayList();
		int electiveNum = 0;
		OracleTeachClass teachclass = new OracleTeachClass(teachclass_id);
		teachclassList.add(teachclass);
		List searchproperty = new ArrayList();
		if (site_id != null && !site_id.equals(""))
			searchproperty.add(new SearchProperty("site_id", site_id, "="));
		if (gradeId != null && !gradeId.equals(""))
			searchproperty.add(new SearchProperty("grade_id", gradeId, "="));
		if (eduTypeId != null && !eduTypeId.equals(""))
			searchproperty
					.add(new SearchProperty("edu_type_id", eduTypeId, "="));
		if (majorId != null && !majorId.equals(""))
			searchproperty.add(new SearchProperty("major_id", majorId, "="));
		OracleBasicActivityList activitylist = new OracleBasicActivityList();
		OrderProperty order = new OrderProperty("reg_no", null);
		List orderproperty = new ArrayList();
		orderproperty.add(order);
		electiveNum = activitylist.searchElectiveNum(searchproperty,
				teachclassList);
		return electiveNum;
	}

	public List getElectives(Page page, String teachclass_id, String site_id,
			String gradeId, String eduTypeId, String majorId)
			throws PlatformException {
		List teachclassList = new ArrayList();
		List electiveList = new ArrayList();
		try {
			OracleTeachClass teachclass = new OracleTeachClass(teachclass_id);
			teachclassList.add(teachclass);
			List searchproperty = new ArrayList();
			if (site_id != null && !site_id.equals(""))
				searchproperty.add(new SearchProperty("site_id", site_id, "="));
			if (gradeId != null && !gradeId.equals(""))
				searchproperty
						.add(new SearchProperty("grade_id", gradeId, "="));
			if (eduTypeId != null && !eduTypeId.equals(""))
				searchproperty.add(new SearchProperty("edu_type_id", eduTypeId,
						"="));
			if (majorId != null && !majorId.equals(""))
				searchproperty
						.add(new SearchProperty("major_id", majorId, "="));
			OracleBasicActivityList activitylist = new OracleBasicActivityList();
			OrderProperty order = new OrderProperty("reg_no", null);
			List orderproperty = new ArrayList();
			orderproperty.add(order);
			electiveList = activitylist.searchElective(page, searchproperty,
					orderproperty, teachclassList);
		} catch (Exception e) {
			
		} finally {
			return electiveList;
		}
	}

	public List getCourseIDHasExperimentList(String semester_id)
			throws PlatformException {
		OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
		return basicActivityList.getCourseIDHasExperimentList(semester_id);
	}

	public void electiveWithFee(String[] selectIds, String[] allIds,
			String semester_id, String site_id, String edu_type_id,
			String major_id, String grade_id) throws PlatformException {
		ElectiveActivity electiveActivity = new OracleElectiveActivity();
		electiveActivity.electiveWithFee(selectIds, allIds, semester_id,
				site_id, edu_type_id, major_id, grade_id);
	}

	public void electiveWithFee(String[] selectIds, String[] allIds,
			String semester_id, String student_id) throws PlatformException {
		ElectiveActivity electiveActivity = new OracleElectiveActivity();
		electiveActivity.electiveWithFee(selectIds, allIds, semester_id,
				student_id);
	}

	public void electiveWithoutFee(String[] selectIds, String[] allIds,
			String semester_id, String student_id) throws PlatformException {
		ElectiveActivity electiveActivity = new OracleElectiveActivity();
		electiveActivity.electiveWithoutFee(selectIds, allIds, semester_id,
				student_id);
	}

}
