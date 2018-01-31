/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleBasicActivityList;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleChangeMajorApply;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleDegreeActivity;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleDegreeCondition;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleElective;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleElectiveActivity;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleElectiveScore;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleGraduateActivity;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleGraduateCondition;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourseActivity;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleRegister;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleStatusChangeActivity;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleTeachClass;
import com.whaty.platform.database.oracle.standard.entity.activity.uniteexam.OracleUniteExamCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleBasicEntityList;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleExecutePlan;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleExecutePlanCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleExecutePlanCourseGroup;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleGrade;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSemester;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSingleTeachPlanCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleTeachBook;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleTeachPlan;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleTeachPlanCourse;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitActivity;
import com.whaty.platform.database.oracle.standard.entity.user.OracleBasicUserList;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacher;
import com.whaty.platform.database.oracle.standard.test.exam.OracleBasicSequence;
import com.whaty.platform.database.oracle.standard.test.exam.OracleExamList;
import com.whaty.platform.entity.BasicActivityManage;
import com.whaty.platform.entity.activity.ChangeMajorStudent;
import com.whaty.platform.entity.activity.DegreeCondition;
import com.whaty.platform.entity.activity.Elective;
import com.whaty.platform.entity.activity.ElectiveScore;
import com.whaty.platform.entity.activity.GraduateCondition;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.activity.TeachClass;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.entity.basic.ExecutePlan;
import com.whaty.platform.entity.basic.ExecutePlanCourse;
import com.whaty.platform.entity.basic.GradeEduMajorGroup;
import com.whaty.platform.entity.basic.TeachPlan;
import com.whaty.platform.entity.basic.TeachPlanCourse;
import com.whaty.platform.entity.basic.TeachPlanCourseType;
import com.whaty.platform.entity.fee.deal.FeeType;
import com.whaty.platform.entity.fee.deal.PayoutType;
import com.whaty.platform.entity.user.ManagerPriv;
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
 * @author chenjian
 * 
 */
public class OracleBasicActivityManage extends BasicActivityManage {

	ManagerPriv basicManagePriv;

	public OracleBasicActivityManage(ManagerPriv amanagerpriv) {
		this.basicManagePriv = amanagerpriv;
	}

	public OracleBasicActivityManage() {
	}

	public int cancelChangeStatus(List studentList, String statusChangeType)
			throws PlatformException {
		if (basicManagePriv.cancelChangeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			changeStatus.setStudentList(studentList);
			int suc = changeStatus.cancelChangeStatus(statusChangeType);
			UserLog.setInfo("<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$cancelChangeStatus</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有取消学籍异动的权限");
		}
	}

	public int cancelChangeStatus(String rid, String statusChangeType)
			throws PlatformException {
		if (basicManagePriv.cancelChangeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			int suc = changeStatus.cancelChangeStatus(rid, statusChangeType);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$cancelChangeStatus</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有取消学籍异动的权限");
		}
	}
/**
 * @author zlw
 * */
	public int changeMajorStatus(List studentList, String newmajor_id,
			String newedu_type_id, String newgrade_id, String newsite_id)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			changeStatus.setStudentList(studentList);
			int suc = changeStatus.changeMajorStatus(newmajor_id,
					newedu_type_id, newgrade_id, newsite_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$changeMajorStatus</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有学籍异动的权限");
		}
	}

	public int changeStatus(List studentList, String statusChangeType)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			changeStatus.setStudentList(studentList);
			int suc = changeStatus.changeStatus(statusChangeType);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$changeStatus</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有学籍异动的权限");
		}
	}

	public List getChangeMajorStudents() throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus.getChangeMajorStudents();
		} else {
			throw new PlatformException("您没有学籍异动的权限");
		}
	}

	public ChangeMajorStudent getChangeMajorStudent(String changeMajorId)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus.getChangeMajorStudent(changeMajorId);
		} else {
			throw new PlatformException("您没有学籍异动的权限");
		}
	}

	public List getStatusChangeStudents(String statusChangeType)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus.getStatusChangeStudents(statusChangeType);
		} else {
			throw new PlatformException("您没有学籍异动的权限");
		}
	}

	public List getCourseIDList(String semester_id) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList.getCourseIdBySemesterId(semester_id);
		} else {
			throw new PlatformException("您没有浏览学期开课的权限");
		}
	}

	public List getCourseIDHasExperimentList(String semester_id)
			throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList.getCourseIDHasExperimentList(semester_id);
		} else {
			throw new PlatformException("您没有浏览学期开课的权限");
		}
	}

	public List getCourseList(Page page, String semester_id)
			throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList.getCourseBySemesterId(page, semester_id);
		} else {
			throw new PlatformException("您没有浏览学期开课的权限");
		}
	}

	public List getCourseList(Page page, String semester_id, String courseId,
			String courseName) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			if (!semester_id.equals(null) && !semester_id.equals("")) {
				searchList.add(new SearchProperty("semester_id", semester_id
						.trim(), "="));
			}

			if (!courseId.equals(null) && !courseId.equals("")) {
				searchList.add(new SearchProperty("course_id", courseId.trim(),
						"="));
			}

			if (!courseName.equals(null) && !courseName.equals("")) {
				searchList.add(new SearchProperty("course_name", courseName
						.trim(), "like"));
			}
			orderList.add(new OrderProperty("course_id", OrderProperty.ASC));
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList.getCourseList(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有浏览学期开课的权限");
		}
	}
	
	/**
	 * @author shu
	 * @param semester_id
	 * @param courseId
	 * @param courseName
	 * @return 查找出课程发布信息.
	 * @throws PlatformException
	 */
	public List getPublishCourseList(Page page, String semester_id, String courseId,
			String courseName) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			if (!semester_id.equals(null) && !semester_id.equals("")) {
				searchList.add(new SearchProperty("semester_id", semester_id
						.trim(), "="));
			}

			if (!courseId.equals(null) && !courseId.equals("")) {
				searchList.add(new SearchProperty("course_id", courseId.trim(),
						"="));
			}

			if (!courseName.equals(null) && !courseName.equals("")) {
				searchList.add(new SearchProperty("course_name", courseName
						.trim(), "like"));
			}
			orderList.add(new OrderProperty("course_id", OrderProperty.ASC));
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList.getPublishCourseList(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有浏览学期开课的权限");
		}
	}

	public List getCourseIDList(Page page, String semester_id)
			throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList.getCourseIdBySemesterId(page, semester_id);
		} else {
			throw new PlatformException("您没有浏览学期开课的权限");
		}
	}

	public int getCourseIDListNum(String semester_id) throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList.getCourseIdBySemesterIdNum(semester_id);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}

	public List getCourseList(String semester_id) throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList.getCourseBySemesterId(semester_id);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}

	public int getCourseListNum(String semester_id) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList.getCourseBySemesterIdNum(semester_id);
		} else {
			throw new PlatformException("您没有浏览学期开课的权限");
		}
	}

	public int getCourseListNum(String semester_id, String courseId,
			String courseName) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			List searchList = new ArrayList();
			if (!semester_id.equals(null) && !semester_id.equals("")) {
				searchList.add(new SearchProperty("semester_id", semester_id
						.trim(), "="));
			}

			if (!courseId.equals(null) && !courseId.equals("")) {
				searchList.add(new SearchProperty("course_id", courseId.trim(),
						"="));
			}

			if (!courseName.equals(null) && !courseName.equals("")) {
				searchList.add(new SearchProperty("course_name", courseName
						.trim(), "like"));
			}
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList.getCourseListNum(searchList);
		} else {
			throw new PlatformException("您没有浏览学期开课的权限");
		}
	}
	/**
	 * @author shu
	 * @param semester_id
	 * @param courseId
	 * @param courseName
	 * @return 查找出课程发布信息的数目。
	 * @throws PlatformException
	 */
	public int getPublishCourseListNum(String semester_id, String courseId,
			String courseName) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			List searchList = new ArrayList();
			if (!semester_id.equals(null) && !semester_id.equals("")) {
				searchList.add(new SearchProperty("semester_id", semester_id
						.trim(), "="));
			}

			if (!courseId.equals(null) && !courseId.equals("")) {
				searchList.add(new SearchProperty("course_id", courseId.trim(),
						"="));
			}

			if (!courseName.equals(null) && !courseName.equals("")) {
				searchList.add(new SearchProperty("course_name", courseName
						.trim(), "like"));
			}
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList.getPublishCourseListNum(searchList);
		} else {
			throw new PlatformException("您没有浏览学期开课的权限");
		}
	}
	public List getCourseList1(Page page, String semester_id, String courseId,
			String courseName) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			List searchList = new ArrayList();
			List orderList = new ArrayList();
			if (!semester_id.equals(null) && !semester_id.equals("")) {
				searchList.add(new SearchProperty("semester_id", semester_id
						.trim(), "="));
			}

			if (!courseId.equals(null) && !courseId.equals("")) {
				searchList.add(new SearchProperty("course_id", courseId.trim(),
						"="));
			}

			if (!courseName.equals(null) && !courseName.equals("")) {
				searchList.add(new SearchProperty("course_name", courseName
						.trim(), "like"));
			}
			orderList.add(new OrderProperty("course_id", OrderProperty.ASC));
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList
					.getCourseList1(page, searchList, orderList);
		} else {
			throw new PlatformException("您没有浏览学期开课的权限");
		}
	}

	public int getCourseListNum1(String semester_id, String courseId,
			String courseName) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			List searchList = new ArrayList();
			if (!semester_id.equals(null) && !semester_id.equals("")) {
				searchList.add(new SearchProperty("semester_id", semester_id
						.trim(), "="));
			}

			if (!courseId.equals(null) && !courseId.equals("")) {
				searchList.add(new SearchProperty("course_id", courseId.trim(),
						"="));
			}

			if (!courseName.equals(null) && !courseName.equals("")) {
				searchList.add(new SearchProperty("course_name", courseName
						.trim(), "like"));
			}
			OracleOpenCourseActivity basicActivityList = new OracleOpenCourseActivity();
			return basicActivityList.getCourseListNum1(searchList);
		} else {
			throw new PlatformException("您没有浏览学期开课的权限");
		}
	}

	public List getelectList(String teachClassId) throws PlatformException {
		if (basicManagePriv.electiveStudent == 1) {
			OracleElective elect = new OracleElective();
			return elect.getElectiveId(teachClassId);
		} else {
			throw new PlatformException("您没有浏览选课学生的权限");
		}
	}

	public List getelectList1(String teachClassId) throws PlatformException {
		if (basicManagePriv.electiveStudent == 1) {
			OracleElective elect = new OracleElective();
			return elect.getElective(teachClassId);
		} else {
			throw new PlatformException("您没有浏览选课学生的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicActivityManage#openCourses(java.util.List,
	 *      java.lang.String)
	 */
	public void openCourses(List courses, String semesterId)
			throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleOpenCourseActivity openCourseActivity = new OracleOpenCourseActivity();
			openCourseActivity.setCourseList(courses);
			OracleSemester semester = new OracleSemester();
			semester.setId(semesterId);
			openCourseActivity.setSemester(semester);
			openCourseActivity.openCourses();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$changeStatus</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}

	/**
	 * author zlw
	 * param 课程编号，考场编号
	 */
	
	public void openCourses(String[] coursesId, String[] sequenceId,
			String semesterId) throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			List courseList = new ArrayList();
			for (int i = 0; i < coursesId.length; i++) {
				OracleOpenCourse c = new OracleOpenCourse();
				OracleCourse course = new OracleCourse(coursesId[i]);
				// course.setId(coursesId[i]);
//				c.setCourse(course);
				OracleBasicSequence es = new OracleBasicSequence();
				es.setId(sequenceId[i]);  
			    c.setExamSequence(es);   
				courseList.add(c);
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$openCourses</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有学期开课的权限");
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
		if (basicManagePriv.cancelOpenCourseBySemester == 1) {
			OracleOpenCourseActivity openCourseActivity = new OracleOpenCourseActivity();
			openCourseActivity.setCourseList(courses);
			OracleSemester semester = new OracleSemester();
			semester.setId(semesterId);
			openCourseActivity.setSemester(semester);
			openCourseActivity.unOpenCourses();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$unOpenCourses</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有取消学期开课的权限");
		}
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$unOpenCourses</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有取消学期开课的权限");
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
					searchproperty, orderproperty);
		} else {
			throw new PlatformException("您没有查询学期开课的权限");
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
			throw new PlatformException("您没有查询学期开课的权限");
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
			throw new PlatformException("您没有查询学期开课的权限");
		}
	}

	public List getExecutePlanCourses(Page page, String semesterId,
			String majorId, String eduTypeId, String gradeId)
			throws PlatformException {
		if (basicManagePriv.electiveBatchByStudent == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			ArrayList orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("course_type"));
			orderProperty.add(new OrderProperty("course_id"));
			return basicActivityList.getExecutePlanCourses(page, semesterId,
					gradeId, eduTypeId, majorId, null, null, orderProperty);

		} else {
			throw new PlatformException("您没有学生批量选课的权限");
		}
	}

	public List getCondtionEleNumOpenCourses(Page page, String semesterId,
			String site_id, String major_id, String grade_id,
			String edutype_id, String reg_no) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			SearchProperty search = new SearchProperty("semester_id",
					semesterId);
			OrderProperty order = new OrderProperty("course_id", null);
			List searchproperty1 = new ArrayList();
			List searchproperty2 = new ArrayList();
			List orderproperty = new ArrayList();

			searchproperty2.add(search);
			if (site_id != null && !site_id.equals(""))
				searchproperty1.add(new SearchProperty("site_id", site_id));

			if (major_id != null && !major_id.equals(""))
				searchproperty1.add(new SearchProperty("major_id", major_id));

			if (grade_id != null && !grade_id.equals(""))
				searchproperty1.add(new SearchProperty("grade_id", grade_id));

			if (edutype_id != null && !edutype_id.equals(""))
				searchproperty1
						.add(new SearchProperty("edutype_id", edutype_id));

			if (reg_no != null && !reg_no.equals(""))
				searchproperty1.add(new SearchProperty("reg_no", reg_no));

			orderproperty.add(order);

			return basicActivityList.searchOpenCourseWithConditionEleNum(page,
					searchproperty1, searchproperty2, orderproperty);
		} else {
			throw new PlatformException("您没有查询学期开课的权限");
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
	public List getUnCondtionEleNumExecutePlanCourses(Page page,
			String semesterId, String siteId, String majorId, String gradeId,
			String eduTypeId, String reg_no) throws PlatformException {
	
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			OrderProperty order = new OrderProperty("course_id", null);

			List orderproperty = new ArrayList();
			orderproperty.add(order);

//			return basicActivityList.getUnExecutePlanWithConditionEleNum(page,
//					semesterId, siteId, majorId, eduTypeId, gradeId, reg_no,
//					orderproperty);
			return null;
	
	}

	public List getOpenCourses(String open_course_id, String semesterId)
			throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchproperty = new ArrayList();
			if (open_course_id != null && !open_course_id.equals("")
					&& !open_course_id.equals("null"))
				searchproperty.add(new SearchProperty("id", open_course_id));
			if (semesterId != null && !semesterId.equals("")
					&& !semesterId.equals("null"))
				searchproperty
						.add(new SearchProperty("semester_id", semesterId));
			return basicActivityList.searchOpenCourse(null, searchproperty,
					null);
		} else {
			throw new PlatformException("您没有查询学期开课的权限");
		}
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
			// searchproperty.add(new SearchProperty("major_id",
			// basicManagePriv.getMajorList(), "in"));
			return basicActivityList.searchOpenCourse(page, searchproperty,
					null);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}

	}

	public List getOpenCourses(Page page, String major_id, String semesterId,
			String course_id, String course_name) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			// SearchProperty search = new SearchProperty("semester_id",
			// semesterId);
			List searchproperty = new ArrayList();
			// searchproperty.add(search);
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchproperty.add(new SearchProperty("major_id", major_id));

			if (semesterId != null && !semesterId.equals("")
					&& !semesterId.equals("null"))
				searchproperty
						.add(new SearchProperty("semester_id", semesterId));

			if (course_id != null && !course_id.equals("")) {
				searchproperty.add(new SearchProperty("course_id", course_id,
						"="));
			}
			if (course_name != null && !course_name.equals("")) {
				searchproperty.add(new SearchProperty("course_name",
						course_name, "like"));
			}
			// searchproperty.add(new SearchProperty("major_id",
			// basicManagePriv.getMajorList(), "in"));
			return basicActivityList.searchOpenCourse(page, searchproperty,
					null);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}

	}

	public List getOpenCoursesList(Page page, String major_id,
			String semesterId, String course_id, String course_name,
			String status) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			// SearchProperty search = new SearchProperty("semester_id",
			// semesterId);
			List searchproperty = new ArrayList();
			// searchproperty.add(search);
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchproperty.add(new SearchProperty("major_id", major_id));

			if (semesterId != null && !semesterId.equals("")
					&& !semesterId.equals("null"))
				searchproperty
						.add(new SearchProperty("semester_id", semesterId));

			if (course_id != null && !course_id.equals("")) {
				searchproperty.add(new SearchProperty("course_id", course_id,
						"="));
			}
			if (course_name != null && !course_name.equals("")) {
				searchproperty.add(new SearchProperty("course_name",
						course_name, "like"));
			}
			// searchproperty.add(new SearchProperty("major_id",
			// basicManagePriv.getMajorList(), "in"));
			if (status.equals("2")) {
				searchproperty.add(new SearchProperty("teachclass_id",
						basicActivityList.getTeacherForTeacherClass(), "in"));
			} else if (status.equals("3")) {
				searchproperty
						.add(new SearchProperty("teachclass_id",
								basicActivityList.getTeacherForTeacherClass(),
								"notIn"));
			} else {

			}
			return basicActivityList.searchOpenCourse(page, searchproperty,
					null);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}

	}
	public List getOpenCoursesList(Page page, String major_id,
			String semesterId, String course_id, String course_name,
			String status,String CWStatus) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			// SearchProperty search = new SearchProperty("semester_id",
			// semesterId);
			List searchproperty = new ArrayList();
			// searchproperty.add(search);
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchproperty.add(new SearchProperty("major_id", major_id));

			if (semesterId != null && !semesterId.equals("")
					&& !semesterId.equals("null"))
				searchproperty
						.add(new SearchProperty("semester_id", semesterId));

			if (course_id != null && !course_id.equals("")) {
				searchproperty.add(new SearchProperty("course_id", course_id,
						"="));
			}
			if (course_name != null && !course_name.equals("")) {
				searchproperty.add(new SearchProperty("course_name",
						course_name, "like"));
			}
			// searchproperty.add(new SearchProperty("major_id",
			// basicManagePriv.getMajorList(), "in"));
			if (status.equals("2")) {
				searchproperty.add(new SearchProperty("teachclass_id",
						basicActivityList.getTeacherForTeacherClass(), "in"));
			} else if (status.equals("3")) {
				searchproperty
						.add(new SearchProperty("teachclass_id",
								basicActivityList.getTeacherForTeacherClass(),
								"notIn"));
			} else {

			}
			
			if (CWStatus.equals("2")) {
				searchproperty.add(new SearchProperty("cw_num",
						"0", ">"));
			} else if (CWStatus.equals("3")) {
				searchproperty.add(new SearchProperty("cw_num",
						"0", "="));
			} else {

			}
			return basicActivityList.searchOpenCourse(page, searchproperty,
					null);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}

	}
	public List getTeachClasses(Page page, String major_id, String semesterId)
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
			searchproperty.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));
			return basicActivityList.searchTeachClasses(page, searchproperty,
					null);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}

	}

	public List getTeachClasses(Page page, String major_id, String semesterId,
			String courseId, String courseName, String appointStatus)
			throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			SearchProperty search = new SearchProperty("semester_id",
					semesterId);
			List searchproperty = new ArrayList();
			List orderProperty = new ArrayList();
			searchproperty.add(search);
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchproperty.add(new SearchProperty("major_id", major_id));
			searchproperty.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));
			if (courseId != null && !courseId.equals("")
					&& !courseId.equals("null")) {
				searchproperty.add(new SearchProperty("course_id", courseId));
			}
			if (courseName != null && !courseName.equals("")
					&& !courseName.equals("null")) {
				searchproperty.add(new SearchProperty("course_name",
						courseName, "like"));
			}
			if (appointStatus.equals("1")) {

			} else if (appointStatus.equals("2")) {
				searchproperty.add(new SearchProperty("teachclass_id",
						basicActivityList.getTeachclass_cware(), "in"));
			} else if (appointStatus.equals("3")) {
				searchproperty.add(new SearchProperty("teachclass_id",
						basicActivityList.getTeachclass_cware(), "notIn"));
			}
			orderProperty.add(new OrderProperty("id", OrderProperty.ASC));
			return basicActivityList.searchTeachClasses(page, searchproperty,
					orderProperty);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
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
				return basicActivityList.searchElective(page, searchProperties,
						null, student);
			} else
				return basicActivityList.searchElective(page, null, null,
						student);
		} else {
			throw new PlatformException("您没有确认选课的权限");
		}
	}

	public List getConfirmedElectiveByUserId(Page page, String semester_id,
			String student_id) throws PlatformException {
		if (basicManagePriv.confirmElectiveInfo == 1) {
			List searchProperties = new ArrayList();
			if (semester_id != null && !semester_id.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semester_id, "="));
			}
			searchProperties.add(new SearchProperty("status", "1", "="));
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			Student student = new OracleStudent();
			student.setId(student_id);
			return basicActivityList.searchElective(page, searchProperties,
					null, student);
		} else {
			throw new PlatformException("您没有确认选课的权限");
		}
	}

	public List getElectives(Page page, String site_id, String major_id,
			String edu_type_id, String grade_id, String semester_id)
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

			searchProperties.add(new SearchProperty("site_id", basicManagePriv
					.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("grade_id", basicManagePriv
					.getGradeList(), "in"));

			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.searchElective(page, searchProperties,
					null, (Student) null);
		} else {
			throw new PlatformException("您没有确认选课的权限");
		}
	}

	public List getElectivesExpend(Page page, String site_id, String major_id,
			String edu_type_id, String grade_id, String semester_id,
			String expend_score_student_status, String expend_score_status)
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
			if (expend_score_student_status != null
					&& !expend_score_student_status.equals("")) {
				searchProperties.add(new SearchProperty(
						"expend_score_student_status",
						expend_score_student_status, "="));
			}
			if (expend_score_status != null && !expend_score_status.equals("")) {
				searchProperties.add(new SearchProperty("expend_score_status",
						expend_score_status, "="));
			}

			searchProperties.add(new SearchProperty("site_id", basicManagePriv
					.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("grade_id", basicManagePriv
					.getGradeList(), "in"));

			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.searchElective(page, searchProperties,
					null, (Student) null);
		} else {
			throw new PlatformException("您没有确认选课的权限");
		}
	}

	public List getElectivesCourse(Page page, String site_id, String major_id,
			String edu_type_id, String grade_id, String semester_id)
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

			searchProperties.add(new SearchProperty("site_id", basicManagePriv
					.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("grade_id", basicManagePriv
					.getGradeList(), "in"));

			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.searchElectiveCourse(page,
					searchProperties, null, (Student) null);
		} else {
			throw new PlatformException("您没有确认选课的权限");
		}
	}

//	public void confirmElectives(String semester_id, String site_id,
//			String edu_type_id, String major_id, String grade_id)
//			throws PlatformException {
//		if (basicManagePriv.confirmElectiveInfo == 1) {
//			OracleElectiveActivity electiveActivity = new OracleElectiveActivity();
//			List searchProperties = new ArrayList();
//
//			if (site_id != null && !site_id.equals("")) {
//				searchProperties
//						.add(new SearchProperty("site_id", site_id, "="));
//			}
//
//			if (major_id != null && !major_id.equals("")) {
//				searchProperties.add(new SearchProperty("major_id", major_id,
//						"="));
//			}
//
//			if (edu_type_id != null && !edu_type_id.equals("")) {
//				searchProperties.add(new SearchProperty("edu_type_id",
//						edu_type_id, "="));
//			}
//
//			if (grade_id != null && !grade_id.equals("")) {
//				searchProperties.add(new SearchProperty("grade_id", grade_id,
//						"="));
//			}
//
//			if (semester_id != null && !semester_id.equals("")) {
//				searchProperties.add(new SearchProperty("semester_id",
//						semester_id, "="));
//			}
//			searchProperties.add(new SearchProperty("site_id", basicManagePriv
//					.getSiteList(), "in"));
//
//			searchProperties.add(new SearchProperty("major_id", basicManagePriv
//					.getMajorList(), "in"));
//
//			searchProperties.add(new SearchProperty("edutype_id",
//					basicManagePriv.getEduTypeList(), "in"));
//
//			searchProperties.add(new SearchProperty("grade_id", basicManagePriv
//					.getGradeList(), "in"));
//
//			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
//			List electiveList = basicActivityList.searchUnConfirmElective(null,
//					searchProperties, null);
//			electiveActivity.checkSelect(electiveList);
//			UserLog
//					.setInfo(
//							"<whaty>USERID$|$"
//									+ this.basicManagePriv.getSso_id()
//									+ "</whaty><whaty>BEHAVIOR$|$confirmElectives</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//									+ LogType.MANAGER
//									+ "</whaty><whaty>PRIORITY$|$"
//									+ LogPriority.INFO + "</whaty>", new Date());
//		} else {
//			throw new PlatformException("您没有确认选课的权限");
//		}
//	}

	public List getTeachClassByOpenCourseId(String open_course_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List teachClassList = new ArrayList();
		String sql = "select id,name,assistance_release_status,assistance_note,assistance_title,course_id,course_name  "
				+ "from (select etc.id,etc.name,etc.assistance_release_status,etc.assistance_note,assistance_title,eci.id as course_id,eci.name as course_name "
				+ "from entity_teach_class etc,entity_course_active eca,entity_course_info eci where etc.open_course_id = '"
				+ open_course_id
				+ "' and etc.open_course_id=eca.id and eci.id=eca.course_id)";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleTeachClass teachclass = new OracleTeachClass();
				teachclass.setId(rs.getString("id"));
				teachclass.setName(rs.getString("name"));
				teachclass.setAssistanceReleaseStatus(rs
						.getString("assistance_release_status"));
				teachclass.setAssistanceNote(rs.getString("assistance_note"));
				teachclass.setAssistanceTitle(rs.getString("assistance_title"));
				OracleOpenCourse opencourse = new OracleOpenCourse();
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
//				opencourse.setCourse(course);
				teachclass.setOpenCourse(opencourse);
				teachClassList.add(teachclass);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachClassList;
	}

	public int checkCourseIsOpen(String course_id, String semesterId)
			throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleOpenCourseActivity openCourseActivity = new OracleOpenCourseActivity();
			OracleSemester semester = new OracleSemester();
			semester.setId(semesterId);
			openCourseActivity.setSemester(semester);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$checkCourseIsOpen</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			if (openCourseActivity.getOpenCoursesStr().indexOf(course_id) >= 0)
				return 1;
			else
				return 0;
		} else {
			throw new PlatformException("您没有查询学期开课的权限");
		}
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

			// searchproperty.add(new SearchProperty("major_id", basicManagePriv
			// .getMajorList(), "in"));

			return basicActivityList.searchOpenCourseNum(searchproperty);
		} else {
			throw new PlatformException("您没有学期开课的权限");
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
			throw new PlatformException("您没有浏览学期开课的权限");
		}
	}

	public int getOpenCoursesNum(String major_id, String semesterId,
			String course_id, String course_name) throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			SearchProperty search = new SearchProperty("semester_id",
					semesterId);
			List searchproperty = new ArrayList();
			searchproperty.add(search);
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchproperty.add(new SearchProperty("major_id", major_id));
			if (course_id != null && !course_id.equals("")) {
				searchproperty.add(new SearchProperty("course_id", course_id,
						"="));
			}
			if (course_name != null && !course_name.equals("")) {
				searchproperty.add(new SearchProperty("course_name",
						course_name, "like"));
			}

			// searchproperty.add(new SearchProperty("major_id", basicManagePriv
			// .getMajorList(), "in"));

			return basicActivityList.searchOpenCourseNum(searchproperty);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}

	public int getOpenCoursesNum(String major_id, String semesterId,
			String course_id, String course_name, String status)
			throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			SearchProperty search = new SearchProperty("semester_id",
					semesterId);
			List searchproperty = new ArrayList();
			searchproperty.add(search);
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchproperty.add(new SearchProperty("major_id", major_id));
			if (course_id != null && !course_id.equals("")) {
				searchproperty.add(new SearchProperty("course_id", course_id,
						"="));
			}
			if (course_name != null && !course_name.equals("")) {
				searchproperty.add(new SearchProperty("course_name",
						course_name, "like"));
			}

			// searchproperty.add(new SearchProperty("major_id", basicManagePriv
			// .getMajorList(), "in"));
			if (status.equals("2")) {
				searchproperty.add(new SearchProperty("teacher_class_id",
						basicActivityList.getTeacherForTeacherClass(), "in"));
			} else if (status.equals("3")) {
				searchproperty
						.add(new SearchProperty("teacher_class_id",
								basicActivityList.getTeacherForTeacherClass(),
								"notIn"));
			} else {

			}
			return basicActivityList.searchOpenCourseNum(searchproperty);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}
	public int getOpenCoursesNum(String major_id, String semesterId,
			String course_id, String course_name, String status,String CWStatus)
			throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			SearchProperty search = new SearchProperty("semester_id",
					semesterId);
			List searchproperty = new ArrayList();
			searchproperty.add(search);
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchproperty.add(new SearchProperty("major_id", major_id));
			if (course_id != null && !course_id.equals("")) {
				searchproperty.add(new SearchProperty("course_id", course_id,
						"="));
			}
			if (course_name != null && !course_name.equals("")) {
				searchproperty.add(new SearchProperty("course_name",
						course_name, "like"));
			}

			// searchproperty.add(new SearchProperty("major_id", basicManagePriv
			// .getMajorList(), "in"));
			if (status.equals("2")) {
				searchproperty.add(new SearchProperty("teacher_class_id",
						basicActivityList.getTeacherForTeacherClass(), "in"));
			} else if (status.equals("3")) {
				searchproperty
						.add(new SearchProperty("teacher_class_id",
								basicActivityList.getTeacherForTeacherClass(),
								"notIn"));
			} else {

			}
			
			if (CWStatus.equals("2")) {
				searchproperty.add(new SearchProperty("cw_num",
						"0", ">"));
			} else if (CWStatus.equals("3")) {
				searchproperty.add(new SearchProperty("cw_num",
						"0", "="));
			} else {

			}
			return basicActivityList.searchOpenCourseNum(searchproperty);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}
	public int getTeachClassesNum(String major_id, String semesterId)
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

			searchproperty.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));

			return basicActivityList.searchTeachClassesNum(searchproperty);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}

	public int getTeachClassesNum(String major_id, String semesterId,
			String courseId, String courseName, String appointStatus)
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
			if (courseId != null && !courseId.equals("")
					&& !courseId.equals("null")) {
				searchproperty.add(new SearchProperty("course_id", courseId));
			}
			if (courseName != null && !courseName.equals("")
					&& !courseName.equals("null")) {
				searchproperty.add(new SearchProperty("course_name",
						courseName, "like"));
			}
			searchproperty.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));
			if (appointStatus.equals("1")) {

			} else if (appointStatus.equals("2")) {
				searchproperty.add(new SearchProperty("teachclass_id",
						basicActivityList.getTeachclass_cware(), "in"));
			} else if (appointStatus.equals("3")) {
				searchproperty.add(new SearchProperty("teachclass_id",
						basicActivityList.getTeachclass_cware(), "notIn"));
			}
			return basicActivityList.searchTeachClassesNum(searchproperty);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}

	public List getTeachPlanCourses(Page page, String teachPlanId,
			String courseId, String courseName, String credit,
			String courseTime, String majorId) throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			List searchProperties = new ArrayList();
			if (teachPlanId != null && !teachPlanId.equals("")) {
				searchProperties.add(new SearchProperty("teachplan_id",
						teachPlanId, "="));
			}
			if (courseId != null && !courseId.equals("")) {
				searchProperties
						.add(new SearchProperty("id", courseId, "like"));
			}
			if (courseName != null && !courseName.equals("")) {
				searchProperties.add(new SearchProperty("name", courseName,
						"like"));
			}
			if (credit != null && !credit.equals("")) {
				searchProperties.add(new SearchProperty("credit", credit, "="));
			}
			if (courseTime != null && !courseTime.equals("")) {
				searchProperties.add(new SearchProperty("course_time",
						courseTime, "="));
			}
			//if (majorId != null && !majorId.equals("")) {
				//searchProperties.add(new SearchProperty("major_id", majorId,
					//	"="));
			//}
			//searchProperties.add(new SearchProperty("major_id", basicManagePriv
					//.getMajorList(), "in"));
			List orderProperties = new ArrayList();
			orderProperties.add(new OrderProperty("id"));
			OracleTeachPlan oracleTeachplan = new OracleTeachPlan();
			return oracleTeachplan.getCourses(page, searchProperties,
					orderProperties);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}
/**
 * @author zlw 
 * @see     去掉searchPropertites 中的 major条件 和orderPropertier 中的major 条件，因为此处的开课课程与专业无关
 *         
 * */
	public List getTeachPlanCourses(Page page, String teachPlanId,
			String courseId, String courseName, String credit,
			String courseTime, String major_id, String order,String semester,String grade_id,String edutype_id)
			throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			List searchProperties = new ArrayList();
			if (teachPlanId != null && !teachPlanId.equals("")) {
				searchProperties.add(new SearchProperty("teachplan_id",
						teachPlanId, "="));
			}
			if (courseId != null && !courseId.equals("")) {
				searchProperties
						.add(new SearchProperty("id", courseId, "like"));
			}
			if (courseName != null && !courseName.equals("")) {
				searchProperties.add(new SearchProperty("name", courseName,
						"like"));
			}
			if (credit != null && !credit.equals("")) {
				searchProperties.add(new SearchProperty("credit", credit, "="));
			}
			if (courseTime != null && !courseTime.equals("")) {
				searchProperties.add(new SearchProperty("course_time",
						courseTime, "="));
				
			}
			if (semester != null && !semester.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semester, "="));
				}
			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id",
						grade_id, "="));
				}
			if (edutype_id != null && !edutype_id.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edutype_id, "="));
				}
			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id",
						major_id, "="));
				}
		//	if (majorId != null && !majorId.equals("")) { 
				//searchProperties.add(new SearchProperty("major_id", majorId, zlw
				//		"="));
		//	}
			//searchProperties.add(new SearchProperty("major_id", basicManagePriv  zlw
				//	.getMajorList(), "in"));
			List orderProperties = new ArrayList();
			//orderProperties.add(new OrderProperty(order));
			orderProperties.add(new OrderProperty("semester"));
			orderProperties.add(new OrderProperty("id"));
			OracleTeachPlan oracleTeachplan = new OracleTeachPlan();
			return oracleTeachplan.getCourses(page, searchProperties,
					orderProperties);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}
	/**
	 * @author zlw 
	 * @see     去掉searchPropertites 中的 major条件 和orderPropertier 中的major 条件，因为此处的开课课程与专业无关
	 *         
	 * */
		public List getTeachPlanCoursesTycj(Page page, String teachPlanId,
				String courseId, String courseName, String credit,
				String courseTime, String major_id, String order,String semester,String grade_id,String edutype_id)
				throws PlatformException {
			if (basicManagePriv.openCourseBySemester == 1) {
				List searchProperties = new ArrayList();
				
				if (credit != null && !credit.equals("")) {
					searchProperties.add(new SearchProperty("credit", credit, "="));
				}
				if (courseTime != null && !courseTime.equals("")) {
					searchProperties.add(new SearchProperty("course_time",
							courseTime, "="));
					
				}
				if (semester != null && !semester.equals("")) {
					searchProperties.add(new SearchProperty("semester_id",
							semester, "="));
					}
				if (grade_id != null && !grade_id.equals("")) {
					searchProperties.add(new SearchProperty("grade_id",
							grade_id, "="));
					}
				if (edutype_id != null && !edutype_id.equals("")) {
					searchProperties.add(new SearchProperty("edutype_id",
							edutype_id, "="));
					}
				if (major_id != null && !major_id.equals("")) {
					searchProperties.add(new SearchProperty("major_id",
							major_id, "="));
					}
				List orderProperties = new ArrayList();
				orderProperties.add(new OrderProperty("grade_id"));
				orderProperties.add(new OrderProperty("major_id"));
				OracleTeachPlan oracleTeachplan = new OracleTeachPlan();
//				return oracleTeachplan.getCoursesTycj(page, searchProperties,
//						orderProperties);
				return null;
			} else {
				throw new PlatformException("您没有学期开课的权限");
			}
		}

	public List getUnTeachPlanCourses(Page page, String teachPlanId,
			String courseId, String courseName, String credit,
			String courseTime, String majorId) throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			List searchProperties = new ArrayList();
			if (teachPlanId != null && !teachPlanId.equals("")) {
				searchProperties.add(new SearchProperty("teachplan_id",
						teachPlanId, "="));
			}
			if (courseId != null && !courseId.equals("")) {
				searchProperties.add(new SearchProperty("c.id", courseId,
						"like"));
			}
			if (courseName != null && !courseName.equals("")) {
				searchProperties.add(new SearchProperty("c.name", courseName,
						"like"));
			}
			if (credit != null && !credit.equals("")) {
				searchProperties.add(new SearchProperty("credit", credit, "="));
			}
			if (courseTime != null && !courseTime.equals("")) {
				searchProperties.add(new SearchProperty("course_time",
						courseTime, "="));
			}

			List orderProperties = new ArrayList();
			orderProperties.add(new OrderProperty("c.id"));
			OracleTeachPlan oracleTeachplan = new OracleTeachPlan();
			return oracleTeachplan.getOtherCourses(page, searchProperties,
					orderProperties);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}
//zlw 专业与课程无关联，与以前不同。
	public List getTeachPlans(Page page, String major_id, String edu_type_id,
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

			//searchProperties.add(new SearchProperty("major_id", basicManagePriv
					//.getMajorList(), "in"));  zlw

			//searchProperties.add(new SearchProperty("edutype_id",
					//basicManagePriv.getEduTypeList(), "in"));zlw

			//searchProperties.add(new SearchProperty("grade_id", basicManagePriv
					//.getGradeList(), "in"));   zlw
			List orderProperties = new ArrayList();
			orderProperties.add(new OrderProperty("grade_id", "1"));
			orderProperties.add(new OrderProperty("edutype_id"));
			orderProperties.add(new OrderProperty("major_id"));
			OracleBasicEntityList basicDateList = new OracleBasicEntityList();
			return basicDateList.getTeachPlans(page, searchProperties,
					orderProperties);
		} else {
			throw new PlatformException("您没有浏览教学计划的权限");
		}
	}
//zlw  注释掉权限控制
	public int getTeachPlansNum(String major_id, String edu_type_id,
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
		//	searchProperties.add(new SearchProperty("major_id", basicManagePriv
				//	.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));

		//	searchProperties.add(new SearchProperty("grade_id", basicManagePriv
					//.getGradeList(), "in"));
			OracleBasicEntityList basicDateList = new OracleBasicEntityList();
			return basicDateList.getTeachPlansNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览教学计划的权限");
		}
	}

	public int addTeachPlan(String major_id, String edu_type_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.addTeachPlan == 1) {
			OracleTeachPlan teachplan = new OracleTeachPlan();
			teachplan.setTitle("null");
			OracleMajor major = new OracleMajor(major_id);
			teachplan.setMajor(major);
			OracleEduType eduType = new OracleEduType(edu_type_id);
			teachplan.setEduType(eduType);
			OracleGrade grade = new OracleGrade(grade_id);
			teachplan.setGrade(grade);
			int suc = teachplan.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addTeachPlan</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加教学计划的权限");
		}
	}
/**@author zlw**/
	public int addTeachPlanCourses(List courseList, String teachPlanId)
			throws PlatformException {
		if (basicManagePriv.addTeachPlanCourse == 1) {
			OracleTeachPlan teachPlan = new OracleTeachPlan();
			teachPlan.setId(teachPlanId);
			int suc = teachPlan.addCourses(courseList);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addTeachPlanCourses</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加教学计划课程的权限");
		}
	}

	public int deleteAllElective(String semester_id, String site_id,
			String edu_type_id, String major_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.electiveBatchBySite == 1) {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteAllElective</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有教学站批量选课的权限");
		}
	}

	public int confirmElective(HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.confirmElectiveInfo == 1) {

			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.confirmElective(request);
		} else {
			throw new PlatformException("您没有确认选课的权限");
		}
	}

	public int rejectElective(HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.confirmElectiveInfo == 1) {

			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.rejectElective(request);
		} else {
			throw new PlatformException("您没有确认选课的权限");
		}
	}
//zlw
	public List getUnConfirmElectives(Page page, String semester_id,
			String site_id, String edu_type_id, String major_id,
			String grade_id, String reg_no, String student_name)
			throws PlatformException {
		if (basicManagePriv.confirmElectiveInfo == 1) {
			List searchProperties = new ArrayList();
			List orderProperties = new ArrayList();
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

			if (reg_no != null && !reg_no.equals("")) {
				searchProperties.add(new SearchProperty("reg_no", reg_no,
						"="));
			}

			if (student_name != null && !student_name.equals("")) {
				searchProperties.add(new SearchProperty("student_name",
						student_name, "like"));
			}

			if (semester_id != null && !semester_id.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semester_id, "="));
			}

		//	searchProperties.add(new SearchProperty("site_id", basicManagePriv
					//.getSiteList(), "in"));

		//	searchProperties.add(new SearchProperty("major_id", basicManagePriv
					//.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));

			//searchProperties.add(new SearchProperty("grade_id", basicManagePriv
				//	.getGradeList(), "in"));
			orderProperties.add(new OrderProperty("course_id"));

			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.searchElective(page, searchProperties,//false 改为true
					orderProperties, null, true);
		} else {
			throw new PlatformException("您没有确认选课的权限");
		}
	}

	public int getUnConfirmElectivesNum(String semester_id, String site_id,
			String edu_type_id, String major_id, String grade_id,
			String reg_no, String student_name) throws PlatformException {
		if (basicManagePriv.electiveSingle == 1) {
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

			if (reg_no != null && !reg_no.equals("")) {
				searchProperties.add(new SearchProperty("reg_no", reg_no,
						"="));
			}

			if (student_name != null && !student_name.equals("")) {
				searchProperties.add(new SearchProperty("student_name",
						student_name, "="));
			}

			if (semester_id != null && !semester_id.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semester_id, "="));
			}
			//searchProperties.add(new SearchProperty("site_id", basicManagePriv
					//.getSiteList(), "in"));

			//searchProperties.add(new SearchProperty("major_id", basicManagePriv
					//.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("edutype_id",
					//basicManagePriv.getEduTypeList(), "in"));

			//searchProperties.add(new SearchProperty("grade_id", basicManagePriv
					//.getGradeList(), "in"));

			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();//false 改为true
			return basicActivityList.searchElectiveNum(searchProperties, null,true);
		} else {
			throw new PlatformException("您没有确认选课的权限");
		}
	}

	public List getUnConfirmElectiveStudents(Page page, String semester_id,
			String site_id, String edu_type_id, String major_id,
			String grade_id, String reg_no, String student_name)
			throws PlatformException {
		if (basicManagePriv.confirmElectiveInfo == 1) {
			List searchProperties = new ArrayList();
			List orderProperties = new ArrayList();
			orderProperties.add(new OrderProperty("reg_no"));
			List searchProperties1 = new ArrayList();
			if (site_id != null && !site_id.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", site_id, "="));
				searchProperties1.add(new SearchProperty("site_id", site_id,
						"="));
			}
			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
				searchProperties1.add(new SearchProperty("major_id", major_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
				searchProperties1.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
				searchProperties1.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			if (reg_no != null && !reg_no.equals("")) {
				searchProperties.add(new SearchProperty("reg_no", reg_no,
						"like"));
				searchProperties1.add(new SearchProperty("reg_no", reg_no,
						"like"));
			}
			if (student_name != null && !student_name.equals("")) {
				searchProperties.add(new SearchProperty("a.name", student_name,
						"like"));
				searchProperties1.add(new SearchProperty("d.name",
						student_name, "like"));
			}
			if (semester_id != null && !semester_id.equals("")) {
				searchProperties1.add(new SearchProperty("semester_id",
						semester_id, "="));
			}
			/***searchProperties.add(new SearchProperty("site_id", basicManagePriv
					.getSiteList(), "in"));
			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));
			searchProperties.add(new SearchProperty("edutype_id",
					basicManagePriv.getEduTypeList(), "in"));
			searchProperties.add(new SearchProperty("grade_id", basicManagePriv
					.getGradeList(), "in"));*/
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList
					.searchElectiveStudents(page, searchProperties,
							orderProperties, searchProperties1, true);
		} else {
			throw new PlatformException("您没有确认选课的权限");
		}
	}

	public int getUnConfirmElectiveStudentsNum(String semester_id,
			String site_id, String edu_type_id, String major_id,
			String grade_id, String reg_no, String student_name)
			throws PlatformException {
		if (basicManagePriv.electiveSingle == 1) {
			List searchProperties = new ArrayList();
			List searchProperties1 = new ArrayList();
			if (site_id != null && !site_id.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", site_id, "="));
				searchProperties1.add(new SearchProperty("site_id", site_id,
						"="));
			}
			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
				searchProperties1.add(new SearchProperty("major_id", major_id,
						"="));
			}
			if (edu_type_id != null && !edu_type_id.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
				searchProperties1.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("grade_id", grade_id,
						"="));
				searchProperties1.add(new SearchProperty("grade_id", grade_id,
						"="));
			}
			if (reg_no != null && !reg_no.equals("")) {
				searchProperties.add(new SearchProperty("reg_no", reg_no,
						"like"));
				searchProperties1.add(new SearchProperty("reg_no", reg_no,
						"like"));
			}
			if (student_name != null && !student_name.equals("")) {
				searchProperties.add(new SearchProperty("a.name", student_name,
						"like"));
				searchProperties1.add(new SearchProperty("d.name",
						student_name, "like"));
			}
			if (semester_id != null && !semester_id.equals("")) {
				searchProperties1.add(new SearchProperty("semester_id",
						semester_id, "="));
			}
			/***searchProperties.add(new SearchProperty("site_id", basicManagePriv
					.getSiteList(), "in"));
			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));
			searchProperties.add(new SearchProperty("edutype_id",
					basicManagePriv.getEduTypeList(), "in"));
			searchProperties.add(new SearchProperty("grade_id", basicManagePriv
					.getGradeList(), "in"));*/
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.searchElectiveStudentsNum(
					searchProperties, searchProperties1,true);//fasle改为true。
		} else {
			throw new PlatformException("您没有确认选课的权限");
		}
	}

	public void electiveCoursesByUserId(String[] idSet, String semester_id,
			String student_id) throws PlatformException {
		if (basicManagePriv.electiveBatchByStudent == 1) {
			OracleElectiveActivity electiveActivity = new OracleElectiveActivity();
			electiveActivity.electiveCoursesByUserId(idSet, semester_id,
					student_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$electiveCoursesByUserId</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有为学生批量选课的权限");
		}
	}

	public void confirmElectiveCoursesByUserId(String[] idSet,
			String semester_id, String student_id) throws PlatformException {
		if (basicManagePriv.electiveBatchByStudent == 1) {
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$confirmElectiveCoursesByUserId</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有单个确认选课的权限");
		}
	}

	public void electiveCourses(String[] idSet, String semester_id,
			String site_id, String edu_type_id, String major_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.electiveBatchBySite == 1) {
			dbpool db = new dbpool();
			MyResultSet rs = null;
			String courseactive = "";
			String sql;
			 String feeUpdateSql ="";
			String delsql;
			
			List sqlgroup = new ArrayList();
			if (idSet == null) {
				if (site_id != null && !site_id.equals("null")
						&& !site_id.equals("")){
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
					 feeUpdateSql = "update entity_userfee_record set payout_type = '"+PayoutType.ROLLBACK+"' where user_id in (select id from entity_student_info where site_id ='"+site_id+"' and  edutype_id = '"+edu_type_id+"' and major_id = '"+edu_type_id+"' and grade_id = '"+grade_id+"')   and teachclass_id in (select c.id as id from entity_course_active a,entity_teach_class c where c.open_course_id = a.id and a.semester_id='"+ semester_id + "')";
					 sqlgroup.add(feeUpdateSql);
				}else{
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
							feeUpdateSql = "update entity_userfee_record set payout_type = '"+PayoutType.ROLLBACK+"' where user_id in (select id from entity_student_info where edutype_id = '"+edu_type_id+"' and major_id = '"+edu_type_id+"' and grade_id = '"+grade_id+"')   and teachclass_id in (select c.id as id from entity_course_active a,entity_teach_class c where c.open_course_id = a.id and a.semester_id='"+ semester_id + "')";
						 sqlgroup.add(feeUpdateSql);
				}
				db.executeUpdate(sql);
				
			} else {
				for (int i = 0; i < idSet.length; i++) {
					courseactive = courseactive + "'" + idSet[i] + "',";
				}
				courseactive = courseactive.substring(0,
						courseactive.length() - 1);
				if (site_id == null || site_id.equals("null")
						|| site_id.equals("")) {
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
							+ "' and isgraduated = '0' and status = '0') ";
					// System.out.print(delsql);
					sqlgroup.add(delsql);
					feeUpdateSql = "update entity_userfee_record set payout_type = '"+PayoutType.ROLLBACK+"' where teachclass_id in (select t.id as id from entity_teach_class t,entity_course_active a where a.semester_id='"+semester_id+"'  and t.open_course_id = a.id and t.id not in("+courseactive+"))  and user_id not in (select id from entity_student_info where  major_id = '"+major_id+"' and grade_id = '"+grade_id+"' and edutype_id = '"+edu_type_id+"' and isgraduated = '0' and status = '0')";
					 sqlgroup.add(feeUpdateSql);
					// db.executeUpdate(delsql);
					for (int i = 0; i < idSet.length; i++) {

						sql = "insert into entity_elective (id,student_id,teachclass_id,status) (select s_elective_id.nextval,id,"
								+ idSet[i]
								+ ",'1'  "
								+ "from (select a.id as id from entity_student_info a,entity_register_info b "
								+ "where a.id = b.user_id and b.semester_id = '"
								+ semester_id
								+ "' and a.major_id = '"
								+ major_id
								+ "' and a.edutype_id = '"
								+ edu_type_id
								+ "' "
								+ "and a.grade_id = '"
								+ grade_id
								+ "'  and a.isgraduated = '0' and a.status='0') "
								+ "where id not in (select student_id from entity_elective where teachclass_id = '"
								+ idSet[i] + "'))";
						// db.executeUpdate(sql);
						// System.out.print(sql);
						sqlgroup.add(sql);
						sql = "update entity_elective set status = '1' where student_id in (select c.id as id from "
								+ "(select a.id as id from entity_student_info a,entity_register_info b where a.id = b.user_id "
								+ "and b.semester_id = '"
								+ semester_id
								+ "' and a.major_id = '"
								+ major_id
								+ "' and a.edutype_id = '"
								+ edu_type_id
								+ "' "
								+ "and a.grade_id = '"
								+ grade_id
								+ "'  and a.isgraduated = '0' and a.status='0') c ,"
								+ "(select student_id from entity_elective where teachclass_id = '"
								+ idSet[i]
								+ "' and status = '0') d where c.id = d.student_id )";
						// db.executeUpdate(sql);
						sqlgroup.add(sql);

						String stuSql = "select id from entity_student_info where major_id = '"
								+ major_id
								+ "' and grade_id = '"
								+ grade_id
								+ "' and edutype_id = '"
								+ edu_type_id
								+ "' and isgraduated='0' and status = '0' "
								+ "minus select student_id from entity_elective where teachclass_id = '"
								+ idSet[i] + "'";

						try {
							rs = db.executeQuery(stuSql);
							while (rs != null && rs.next()) {
								String subSql = "select a.id from entity_course_active a,entity_teach_class c where a.id = c.open_course_id and c.id = '"
										+ idSet[i] + "'";
								String feeSql = "insert into entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note) "
										+ "select s_entity_fee_id.nextval,'"
										+ rs.getString("id")
										+ "',value,'"
										+ FeeType.CREDIT
										+ "','"
										+ PayoutType.CONSUME
										+ "','1',note "
										+ "from (select -(e.credit* f.creditfee) as value,concat(concat(concat(concat(concat(concat('选课 课程名称：', '('||e.course_id||')'||e.course_name),'；课程学分：'),e.credit),'；学分标准：每学分：'),f.creditfee),'元') as note "
										+ "from (select nvl(a.credit,b.credit) as credit,a.course_id,a.course_name from (select tc.credit,tc.course_id,c.name as course_name from "
										+ "entity_course_active a,entity_course_info c,entity_teachplan_course tc,entity_teachplan_info i,entity_student_info s where a.id in ("
										+ subSql
										+ ")  "
										+ "and tc.course_id = c.id and a.course_id = c.id and i.edutype_id = s.edutype_id and i.major_id = s.major_id and i.grade_id = s.grade_id and s.id = '"
										+ rs.getString("id")
										+ "' "
										+ "and tc.teachplan_id = i.id)a,(select a.course_id as course_id,c.credit  from entity_course_info c,entity_course_active a where a.id in ("
										+ subSql
										+ ") and a.course_id = c.id)b "
										+ "where b.course_id = a.course_id(+))e,(select type1_value as creditfee from entity_userfee_level where user_id = '"
										+ rs.getString("id") + "')f) ";
								sqlgroup.add(feeSql);
							}
						} catch (Exception e) {
							
						} finally {
							db.close(rs);
							db = null;
						}
					}
					db.executeUpdateBatch(sqlgroup);
				} else {
					delsql = "delete from entity_elective where teachclass_id in"
							+ "(select t.id as id from entity_teach_class t,entity_course_active a where a.semester_id='"
							+ semester_id
							+ "' "
							+ "and t.open_course_id = a.id and t.id not in("
							+ courseactive
							+ "))"
							+ "and student_id in (select id from entity_student_info where  site_id='"
							+ site_id
							+ "' and major_id = '"
							+ major_id
							+ "' "
							+ "and grade_id = '"
							+ grade_id
							+ "' and edutype_id = '"
							+ edu_type_id
							+ "' and isgraduated = '0' and status = '0') ";
					// System.out.print(delsql);
					// db.executeUpdate(delsql);
					sqlgroup.add(delsql);
					for (int i = 0; i < idSet.length; i++) {

						sql = "insert into entity_elective (id,student_id,teachclass_id,status) (select s_elective_id.nextval,id,"
								+ idSet[i]
								+ ",'1'  "
								+ "from (select a.id as id from entity_student_info a,entity_register_info b "
								+ "where a.id = b.user_id and b.semester_id = '"
								+ semester_id
								+ "' and a.site_id='"
								+ site_id
								+ "' and a.major_id = '"
								+ major_id
								+ "' and a.edutype_id = '"
								+ edu_type_id
								+ "' "
								+ "and a.grade_id = '"
								+ grade_id
								+ "'  and a.isgraduated = '0' and a.status='0') "
								+ "where id not in (select student_id from entity_elective where teachclass_id = '"
								+ idSet[i] + "'))";
						// db.executeUpdate(sql);
						// System.out.print("sql:"+sql);
						sqlgroup.add(sql);
						sql = "update entity_elective set status = '1' where student_id in (select c.id as id from "
								+ "(select a.id as id from entity_student_info a,entity_register_info b where a.id = b.user_id "
								+ "and b.semester_id = '"
								+ semester_id
								+ "' and a.site_id='"
								+ site_id
								+ "'  and a.major_id = '"
								+ major_id
								+ "' and a.edutype_id = '"
								+ edu_type_id
								+ "' "
								+ "and a.grade_id = '"
								+ grade_id
								+ "'  and a.isgraduated = '0' and a.status='0') c ,"
								+ "(select student_id from entity_elective where teachclass_id = '"
								+ idSet[i]
								+ "' and status = '0') d where c.id = d.student_id )";
						// db.executeUpdate(sql);
						sqlgroup.add(sql);
						String stuSql = "select id from entity_student_info where major_id = '"
								+ major_id
								+ "' and grade_id = '"
								+ grade_id
								+ "' and edutype_id = '"
								+ edu_type_id
								+ "' and isgraduated='0' and status = '0' "
								+ "minus select student_id from entity_elective where teachclass_id = '"
								+ idSet[i] + "'";

						try {
							rs = db.executeQuery(stuSql);
							while (rs != null && rs.next()) {
								String subSql = "select a.id from entity_course_active a,entity_teach_class c where a.id = c.open_course_id and c.id = '"
										+ idSet[i] + "'";
								String feeSql = "insert into entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note) "
										+ "select s_entity_fee_id.nextval,'"
										+ rs.getString("id")
										+ "',value,'"
										+ FeeType.CREDIT
										+ "','"
										+ PayoutType.CONSUME
										+ "','1',note "
										+ "from (select -(e.credit* f.creditfee) as value,concat(concat(concat(concat(concat(concat('选课 课程名称：', '('||e.course_id||')'||e.course_name),'；课程学分：'),e.credit),'；学分标准：每学分：'),f.creditfee),'元') as note "
										+ "from (select nvl(a.credit,b.credit) as credit,a.course_id,a.course_name from (select tc.credit,tc.course_id,c.name as course_name from "
										+ "entity_course_active a,entity_course_info c,entity_teachplan_course tc,entity_teachplan_info i,entity_student_info s where a.id in ("
										+ subSql
										+ ")  "
										+ "and tc.course_id = c.id and a.course_id = c.id and i.edutype_id = s.edutype_id and i.major_id = s.major_id and i.grade_id = s.grade_id and s.id = '"
										+ rs.getString("id")
										+ "' "
										+ "and tc.teachplan_id = i.id)a,(select a.course_id as course_id,c.credit  from entity_course_info c,entity_course_active a where a.id in ("
										+ subSql
										+ ") and a.course_id = c.id)b "
										+ "where b.course_id = a.course_id(+))e,(select type1_value as creditfee from entity_userfee_level where user_id = '"
										+ rs.getString("id") + "')f) ";
								sqlgroup.add(feeSql);
							}

						} catch (Exception e) {
							
						} finally {
							db.close(rs);
						}
					}
					db.executeUpdateBatch(sqlgroup);
				}
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$electiveCourses</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有教学站批量选课的权限");
		}

	}

	public int getElectiveNum(String teachclass_id) throws PlatformException {
		if (basicManagePriv.electiveBatchBySite == 1) {
			List teachclassList = new ArrayList();
			int electiveNum = 0;
			try {
				OracleTeachClass teachclass = new OracleTeachClass(
						teachclass_id);
				teachclassList.add(teachclass);
				OracleBasicActivityList activitylist = new OracleBasicActivityList();
				OrderProperty order = new OrderProperty("reg_no", null);
				List orderproperty = new ArrayList();
				orderproperty.add(order);
				electiveNum = activitylist.searchElectiveNum(null,
						teachclassList);
			} catch (Exception e) {
				
			} finally {
				return electiveNum;
			}
		} else {
			throw new PlatformException("您没有教学站批量选课的权限");
		}
	}

	// -----------------------------
	public int getElectiveNum(String teachclass_id, String student_id,
			String student_name) throws PlatformException {
		if (basicManagePriv.electiveBatchBySite == 1) {
			List teachclassList = new ArrayList();
			int electiveNum = 0;
			try {
//				OracleTeachClass teachclass = new OracleTeachClass(
//						teachclass_id);
				OracleTeachClass teachclass = new OracleTeachClass();
				teachclassList.add(teachclass);
				OracleBasicActivityList activitylist = new OracleBasicActivityList();
				OrderProperty order = new OrderProperty("reg_no", null);

				List searchList = new ArrayList();
				SearchProperty sp = new SearchProperty();
				sp.setField("reg_no");
				sp.setRelation("like");
				sp.setValue(student_id);
				searchList.add(sp);

				sp = new SearchProperty();
				sp.setField("name");
				sp.setRelation("like");
				sp.setValue(student_name);
				searchList.add(sp);
				
				sp = new SearchProperty();
				sp.setField("open_id");
				sp.setRelation("=");
				sp.setValue(teachclass_id);
				searchList.add(sp);

				List orderproperty = new ArrayList();
				orderproperty.add(order);
				electiveNum = activitylist.searchElectiveNum(searchList,
						teachclassList);
			} catch (Exception e) {
				
			} finally {
				return electiveNum;
			}
		} else {
			throw new PlatformException("您没有教学站批量选课的权限");
		}
	}

	public List getElectives(Page page, String teachclass_id)
			throws PlatformException {
		if (basicManagePriv.electiveStudent == 1) {
			List teachclassList = new ArrayList();
			List electiveList = new ArrayList();
			try {
				OracleTeachClass teachclass = new OracleTeachClass(
						teachclass_id);
				teachclassList.add(teachclass);
				OracleBasicActivityList activitylist = new OracleBasicActivityList();
				OrderProperty order = new OrderProperty("reg_no", null);
				List orderproperty = new ArrayList();
				orderproperty.add(order);
				electiveList = activitylist.searchElective(page, null,
						orderproperty, teachclassList);
			} catch (Exception e) {
				
			} finally {
				return electiveList;
			}
		} else {
			throw new PlatformException("您没有浏览选课学生的权限");
		}
	}

	public List getElectives(Page page, String teachclass_id,
			String student_id, String student_name) throws PlatformException {
		if (basicManagePriv.electiveStudent == 1) {
			List teachclassList = new ArrayList();
			List electiveList = new ArrayList();
			try {
//				OracleTeachClass teachclass = new OracleTeachClass(
//						teachclass_id);
				OracleTeachClass teachclass = new OracleTeachClass();
				teachclassList.add(teachclass);
				OracleBasicActivityList activitylist = new OracleBasicActivityList();
				OrderProperty order = new OrderProperty("reg_no", null);

				List searchList = new ArrayList();
				SearchProperty sp = new SearchProperty();
				sp.setField("reg_no");
				sp.setRelation("like");
				sp.setValue(student_id);
				searchList.add(sp);

				sp = new SearchProperty();
				sp.setField("name");
				sp.setRelation("like");
				sp.setValue(student_name);
				searchList.add(sp);
				
				sp = new SearchProperty();
				sp.setField("open_id");
				sp.setRelation("=");
				sp.setValue(teachclass_id);
				searchList.add(sp);

				List orderproperty = new ArrayList();
				orderproperty.add(order);
				electiveList = activitylist.searchElective(page, searchList,
						orderproperty, teachclassList);
			} catch (Exception e) {
				
			} finally {
				return electiveList;
			}
		} else {
			throw new PlatformException("您没有浏览选课学生的权限");
		}
	}

	public Elective getElective(String student_id, String teachclass_id)
			throws PlatformException {
		String sql = "select id from entity_elective where student_id = '"
				+ student_id + "' and teachclass_id = '" + teachclass_id + "'";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String eleId = "";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				eleId = rs.getString("id");
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return new OracleElective(eleId);
	}

	public int getElectiveNum(String teachclass_id, String site_id)
			throws PlatformException {
		if (basicManagePriv.electiveBatchBySite == 1) {
			List teachclassList = new ArrayList();
			int electiveNum = 0;
			try {
				OracleTeachClass teachclass = new OracleTeachClass(
						teachclass_id);
				teachclassList.add(teachclass);
				List searchproperty = new ArrayList();
				if (site_id != null && !site_id.equals(""))
					searchproperty.add(new SearchProperty("site_id", site_id,
							"="));
				OracleBasicActivityList activitylist = new OracleBasicActivityList();
				OrderProperty order = new OrderProperty("reg_no", null);
				List orderproperty = new ArrayList();
				orderproperty.add(order);
				electiveNum = activitylist.searchElectiveNum(searchproperty,
						teachclassList);
			} catch (Exception e) {
				
			} finally {
				return electiveNum;
			}
		} else {
			throw new PlatformException("您没有教学站批量选课的权限");
		}
	}

	public List getElectives(Page page, String teachclass_id, String site_id)
			throws PlatformException {
		if (basicManagePriv.electiveStudent == 1) {
			List teachclassList = new ArrayList();
			List electiveList = new ArrayList();
			try {
				OracleTeachClass teachclass = new OracleTeachClass(
						teachclass_id);
				teachclassList.add(teachclass);
				List searchproperty = new ArrayList();
				if (site_id != null && !site_id.equals(""))
					searchproperty.add(new SearchProperty("site_id", site_id,
							"="));
				OracleBasicActivityList activitylist = new OracleBasicActivityList();
				OrderProperty order = new OrderProperty("reg_no", null);
				List orderproperty = new ArrayList();
				orderproperty.add(order);
				electiveList = activitylist.searchElective(page,
						searchproperty, orderproperty, teachclassList);
			} catch (Exception e) {
				
			} finally {
				return electiveList;
			}
		} else {
			throw new PlatformException("您没有浏览选课学生的权限");
		}
	}
//zwl read
	public int getTeachPlanCoursesNum(String teachPlanId, String courseId,
			String courseName, String credit, String courseTime, String majorId)
			throws PlatformException {
		if (basicManagePriv.getTeachPlanCourse == 1) {
			List searchProperties = new ArrayList();
			if (teachPlanId != null && !teachPlanId.equals("")) {
				searchProperties.add(new SearchProperty("teachplan_id",
						teachPlanId, "="));
			}
			if (courseId != null && !courseId.equals("")) {
				searchProperties
						.add(new SearchProperty("id", courseId, "like"));
			}
			if (courseName != null && !courseName.equals("")) {
				searchProperties.add(new SearchProperty("name", courseName,
						"like"));
			}
			if (credit != null && !credit.equals("")) {
				searchProperties.add(new SearchProperty("credit", credit, "="));
			}
			if (courseTime != null && !courseTime.equals("")) {
				searchProperties.add(new SearchProperty("course_time",
						courseTime, "="));
			}
		//	if (majorId != null && !majorId.equals("")) {                 zlw
				//searchProperties.add(new SearchProperty("major_id", majorId,  zlw
				//		"="));
		//	}

			//searchProperties.add(new SearchProperty("major_id", basicManagePriv zlw
					//.getMajorList(), "in"));

			OracleTeachPlan oracleTeachplan = new OracleTeachPlan();
			return oracleTeachplan.getCoursesNum(searchProperties);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}

	public int getUnTeachPlanCoursesNum(String teachPlanId, String courseId,
			String courseName, String credit, String courseTime, String majorId)
			throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			List searchProperties = new ArrayList();
			if (teachPlanId != null && !teachPlanId.equals("")) {
				searchProperties.add(new SearchProperty("teachplan_id",
						teachPlanId, "="));
			}
			if (courseId != null && !courseId.equals("")) {
				searchProperties
						.add(new SearchProperty("id", courseId, "like"));
			}
			if (courseName != null && !courseName.equals("")) {
				searchProperties.add(new SearchProperty("c.name", courseName,
						"like"));
			}
			if (credit != null && !credit.equals("")) {
				searchProperties.add(new SearchProperty("credit", credit, "="));
			}
			if (courseTime != null && !courseTime.equals("")) {
				searchProperties.add(new SearchProperty("course_time",
						courseTime, "="));
			}
			OracleTeachPlan oracleTeachplan = new OracleTeachPlan();
			return oracleTeachplan.getOtherCoursesNum(searchProperties);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}

	public List getTeachersByTeachClass(String teachclass_id)
			throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getTeachersByTeachClass(teachclass_id);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}

	public List getTeachers(String opencourse_id, String teachclass_id)
			throws PlatformException {
		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getTeachers(opencourse_id, teachclass_id);
		} else {
			throw new PlatformException("您没有给课程指定教师的权限");
		}
	}

	public List getOpenCourse(String teacher_id) throws PlatformException {

		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getTeachClass(teacher_id);
		} else {
			throw new PlatformException("您没有给课程指定教师的权限");
		}
	}
	
	public List getOpenCourse2(String teacher_id) throws PlatformException {

		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
//			return basicActivityList.getTeachClass2(teacher_id);
			return null;
		} else {
			throw new PlatformException("您没有给课程指定教师的权限");
		}
	}

	public List getTeachBookByCourse(String courseId) throws PlatformException {
		if (basicManagePriv.appointTeachbookForCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getTeachBookByCourse(courseId);
		} else {
			throw new PlatformException("您没有给课程指定教材的权限");
		}
	}

	public List getTeachBookByCourse(HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.viewTeachbookForCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();
		 
			if (request.getParameter("teachbook_id") != null
					&& !request.getParameter("teachbook_id").trim()
							.equals("")
					&& !request.getParameter("teachbook_id").equals("null")) {
				searchProperties.add(new SearchProperty("teachbook_id",
						request.getParameter("teachbook_id"), "like"));
			}
			if (request.getParameter("teachbook_name") != null
					&& !request.getParameter("teachbook_name").trim()
							.equals("")
					&& !request.getParameter("teachbook_name").equals("null")) {
				searchProperties.add(new SearchProperty("teachbook_name",
						request.getParameter("teachbook_name"), "like"));
			}
			if (request.getParameter("publishhouse") != null
					&& !request.getParameter("publishhouse").trim().equals("")
					&& !request.getParameter("publishhouse").equals("null")) {
				searchProperties.add(new SearchProperty("publishhouse", request
						.getParameter("publishhouse"), "like"));
			}
			if (request.getParameter("maineditor") != null
					&& !request.getParameter("maineditor").trim().equals("")
					&& !request.getParameter("maineditor").equals("null")) {
				searchProperties.add(new SearchProperty("maineditor", request
						.getParameter("maineditor"), "like"));
			}
			if (request.getParameter("isbn") != null
					&& !request.getParameter("isbn").trim().equals("")
					&& !request.getParameter("isbn").equals("null")) {
				searchProperties.add(new SearchProperty("isbn", request
						.getParameter("isbn"), "like"));
			}
			searchProperties.add(new SearchProperty("course_id", request
					.getParameter("course_id"), "="));

			return basicActivityList.getTeachBookByCourse(searchProperties);
		} else {
			throw new PlatformException("您没有查看给课程指定了的教材的权限");
		}
	}

	public int getTeachBookNumByCourse(HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.viewTeachbookForCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();
			if (request.getParameter("teachbook_id") != null
					&& !request.getParameter("teachbook_id").trim()
							.equals("")
					&& !request.getParameter("teachbook_id").equals("null")) {
				searchProperties.add(new SearchProperty("teachbook_id",
						request.getParameter("teachbook_id"), "like"));
			}
			if (request.getParameter("teachbook_name") != null
					&& !request.getParameter("teachbook_name").trim()
							.equals("")
					&& !request.getParameter("teachbook_name").equals("null")) {
				searchProperties.add(new SearchProperty("teachbook_name",
						request.getParameter("teachbook_name"), "like"));
			}
			if (request.getParameter("publishhouse") != null
					&& !request.getParameter("publishhouse").trim().equals("")
					&& !request.getParameter("publishhouse").equals("null")) {
				searchProperties.add(new SearchProperty("publishhouse", request
						.getParameter("publishhouse"), "like"));
			}
			if (request.getParameter("maineditor") != null
					&& !request.getParameter("maineditor").trim().equals("")
					&& !request.getParameter("maineditor").equals("null")) {
				searchProperties.add(new SearchProperty("maineditor", request
						.getParameter("maineditor"), "like"));
			}
			if (request.getParameter("isbn") != null
					&& !request.getParameter("isbn").trim().equals("")
					&& !request.getParameter("isbn").equals("null")) {
				searchProperties.add(new SearchProperty("isbn", request
						.getParameter("isbn"), "like"));
			}
			searchProperties.add(new SearchProperty("course_id", request
					.getParameter("course_id"), "="));
			return basicActivityList.getTeachBookNumByCourse(searchProperties);
		} else {
			throw new PlatformException("你没有查看已经给课程指定了的教材的权限");
		}
	}

	public List getCourseByTeachBook(String bookId) throws PlatformException {
		if (basicManagePriv.appointCourseForTeachbook == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getCourseByTeachBook(bookId);
		} else {
			throw new PlatformException("您没有给教材指定课程的权限");
		}
	}

	public List getCourseIdByTeachbook(String bookId) throws PlatformException {
		if (basicManagePriv.appointCourseForTeachbook == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getCourseIdByTeachbook(bookId);
		} else {
			throw new PlatformException("您没有给教材指定课程的权限");
		}
	}

	public List getCourseByTeachBook(Page page, String bookId,
			String coursename, String courseno, String major_id)
			throws PlatformException {
		if (basicManagePriv.appointCourseForTeachbook == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();

			if (coursename != null && !coursename.trim().equals("")
					&& !coursename.equals("null")) {
				searchProperties.add(new SearchProperty(
						"entity_course_info.name", coursename, "like"));
			}
			if (courseno != null && !courseno.trim().equals("")
					&& !courseno.equals("null")) {
				searchProperties.add(new SearchProperty("course_id", courseno,
						"like"));
			}
			if (major_id != null && !major_id.trim().equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			searchProperties
					.add(new SearchProperty("teachbook_id", bookId, "="));

			return basicActivityList.getCourseByTeachBook(page,
					searchProperties);
		} else {
			throw new PlatformException("您没有给教材指定课程的权限");
		}
	}

	public int getCourseNumByTeachBook(String bookId, String coursename,
			String courseno, String major_id) throws PlatformException {
		if (basicManagePriv.appointCourseForTeachbook == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();

			if (coursename != null && !coursename.trim().equals("")
					&& !coursename.equals("null")) {
				searchProperties.add(new SearchProperty("name", coursename,
						"like"));
			}
			if (courseno != null && !courseno.trim().equals("")
					&& !courseno.equals("null")) {
				searchProperties.add(new SearchProperty("course_id", courseno,
						"like"));
			}
			if (major_id != null && !major_id.trim().equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			searchProperties
					.add(new SearchProperty("teachbook_id", bookId, "="));
			return basicActivityList.getCourseNumByTeachBook(searchProperties);
		} else {
			throw new PlatformException("您没有给教材指定课程的权限");
		}
	}

	public List getAllTeachersByTeachClass(String teachclass_id)
			throws PlatformException {
		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getAllTeachersByTeachClass(teachclass_id);
		} else {
			throw new PlatformException("您没有给课程指定教师的权限");
		}
	}

	public int checkTeacherByTeachClass(String teacher_id, String teachclass_id)
			throws PlatformException {
		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachclass_id);
			int suc = teachclass.checkTeacher(teacher_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$checkTeacherByTeachClass</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有给课程指定教师的权限");
		}
	}

	public int addTeachersToTeachClass(String[] teacherIdSet,
			String teachclass_id) throws PlatformException {
		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachclass_id);
			int suc = teachclass.addTeachersToTeachClass(teacherIdSet);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addTeachersToTeachClass</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有给课程指定教师的权限");
		}
	}

	public int addTeachBookToCourse(String[] teachbookIdSet, String course_id)
			throws PlatformException {
		if (basicManagePriv.appointTeachbookForCourse == 1) {
			OracleCourse course = new OracleCourse();
			course.setId(course_id);
			int suc = course.addTeachbookToCourse(teachbookIdSet);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addTeachBookToCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有给课程指定教材的权限");
		}
	}

	public List selectTeachBookId(String[] teachbook_ids, String course_id)
			throws PlatformException {
		if (basicManagePriv.appointTeachbookForCourse == 1) {
			OracleCourse course = new OracleCourse();
			course.setId(course_id);
			return course.selectTeachBookId(teachbook_ids);
		} else {
			throw new PlatformException("您没有给课程指定教材的权限");
		}
	}

	public int addCourseToTeachBook(String[] courseIdSet, String bookId)
			throws PlatformException {
		if (basicManagePriv.appointCourseForTeachbook == 1) {
			OracleTeachBook book = new OracleTeachBook();
			book.setId(bookId);
			int suc = book.addCourseToTeachBook(courseIdSet);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addCourseToTeachBook</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有给教材指定课程的权限");
		}
	}

	public int removeTeachersFromTeachClass(String[] teacherIdSet,
			String teachclass_id) throws PlatformException {
		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachclass_id);
			int suc = teachclass.removeTeachersFromTeachClass(teacherIdSet);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$removeTeachersFromTeachClass</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有给课程指定教师的权限");
		}

	}

	public void updateTeachersForTeachClass(String[] newSlectedIdSet,
			String[] oldSelectedSet, String teachclass_id)
			throws PlatformException {
		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachclass_id);
			List del = new ArrayList();
			if (oldSelectedSet != null) {
				for (int i = 0; i < oldSelectedSet.length; i++) {
					boolean a = true;
					for (int j = 0; j < newSlectedIdSet.length; j++) {
						if (oldSelectedSet[i] != null
								&& oldSelectedSet[i].equals(newSlectedIdSet[j])) {
							a = false;
							break;
						}
					}
					if (a)
						del.add(oldSelectedSet[i]);
				}
			}
			teachclass.addTeachersToTeachClass(newSlectedIdSet);
			teachclass.removeTeachersFromTeachClass(del);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateTeachersForTeachClass</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有给课程指定教师的权限");
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
		if (basicManagePriv.addTeachClass == 1) {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addTeachClass</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有添加培训班的权限");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.BasicActivityManage#deleteTeachClass(java.lang.String)
	 */
	public void deleteTeachClass(String id) throws PlatformException {
		if (basicManagePriv.deleteTeachClass == 1) {
			OracleTeachClass teachClass = new OracleTeachClass(id);
			teachClass.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTeachClass</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有删除培训班的权限");
		}
	}

	public int deleteTeachPlanCourse(String id) throws PlatformException {
		if (basicManagePriv.deleteTeachPlanCourse == 1) {
			OracleTeachPlan teachplan = new OracleTeachPlan();
			List courseList = new ArrayList();
			OracleCourse course = new OracleCourse();
			course.setId(id);
			courseList.add(course);
			int suc = teachplan.removeCourses(courseList);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTeachPlanCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除教学计划课程的权限");
		}
	}

	public int deleteTeachPlanCourses(String[] id) throws PlatformException {
		if (basicManagePriv.deleteTeachPlanCourse == 1) {
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTeachPlanCourses</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除教学计划课程的权限");
		}
	}

	public List getTeachClasses(String openCourseId) throws PlatformException {
		return null;
	}

	public void addTeachClassStudents(List studentIdList, String teachClassId)
			throws PlatformException {
	}

	public void removeTeachClassStudents(List studentIdList, String teachClassId)
			throws PlatformException {
	}

	public int getTeachClassStudentsNum(String teachClassId)
			throws PlatformException {
		if (basicManagePriv.getTeachClass == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachClassId);
			return teachclass.getStudentsNum();
		} else {
			throw new PlatformException("您没有查询培训班的权限");
		}
	}

	public List getTeachClassStudents(Page page, String teachClassId)
			throws PlatformException {
		if (basicManagePriv.getTeachClass == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachClassId);
			return teachclass.getStudents(page);
		} else {
			throw new PlatformException("您没有查询培训班的权限");
		}
	}

	public int getTeachClassStudentsNum(String teachClassId, String siteId)
			throws PlatformException {
		if (basicManagePriv.getTeachClass == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachClassId);
			return teachclass.getStudentsNum(siteId);
		} else {
			throw new PlatformException("您没有查询培训班的权限");
		}
	}

	public List getTeachClassStudents(Page page, String teachClassId,
			String siteId) throws PlatformException {
		if (basicManagePriv.getTeachClass == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachClassId);
			return teachclass.getStudents(page, siteId);
		} else {
			throw new PlatformException("您没有查询培训班的权限");
		}
	}

	public void moveTeachClassStudents(String oldTeachClassId,
			String newTeachClassId) throws PlatformException {
	}

	public List checkGraduateByCompulsory(List studentList, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getGraduateCondition == 1) {
			OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
			graduateactivity.setStudentList(studentList);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$checkGraduateByCompulsory</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return graduateactivity.checkByCompulsory(major_id, edu_type_id,
					grade_id);
		} else {
			throw new PlatformException("您没有查询毕业条件的权限");
		}
	}

	public List checkGraduateByCredit(List studentList, String credit)
			throws PlatformException {
		if (basicManagePriv.getGraduateCondition == 1) {
			OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
			graduateactivity.setStudentList(studentList);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$checkGraduateByCredit</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return graduateactivity.checkByCredit(credit);
		} else {
			throw new PlatformException("您没有查询毕业条件的权限");
		}
	}

	public List getGraduateStudents(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
			OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
			return graduateactivity.getGraduateStudents(page, searchproperty,
					orderproperty);
		} else {
			throw new PlatformException("您没有查询毕业学生的权限");
		}
	}

	public int getGraduateStudentsNum(List searchproperty)
			throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
			OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
			return graduateactivity.getGraduateStudentsNum(searchproperty);
		} else {
			throw new PlatformException("您没有查询毕业学生的权限");
		}
	}
/**@author zlw*/
	public int setGraduated(List studentList, String operatorId,
			String operatorName) throws PlatformException {
		if (basicManagePriv.setGraduated == 1) {
			OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
			int suc = graduateactivity.setGraduated(studentList, operatorId,
					operatorName);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$setGraduated</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有执行毕业操作的权限");
		}
	}
/**@author zlw**/
	public int cancelGraduated(List studentList) throws PlatformException {
		if (basicManagePriv.cancleGraduated == 1) {
			OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
			int suc = graduateactivity.cancleGraduated(studentList);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$cancelGraduated</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有执行取消毕业操作的权限");
		}
	}

	public int checkElective(String teachclass_id, String student_id)
			throws PlatformException {
		if (basicManagePriv.electiveStudent == 1) {
			dbpool db = new dbpool();
			String sql = "select id from entity_elective where teachclass_id ='"
					+ teachclass_id + "' and student_id = '" + student_id + "'";
			int count = db.countselect(sql);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$checkElective</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("您没有查询选课的权限");
		}
	}

	public int checkPreElective(String teachclass_id, String student_id)
			throws PlatformException {
		if (basicManagePriv.electiveStudent == 1) {
			dbpool db = new dbpool();
			String sql = "select id from entity_elective where teachclass_id  = '"
					+ teachclass_id
					+ "' and student_id = '"
					+ student_id
					+ "' and status = '1'";
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$checkPreElective</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return db.countselect(sql);
		} else {
			throw new PlatformException("您没有查询选课的权限");
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
			throw new PlatformException("您没有学生批量选课的权限！");
		}

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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$regStudents</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有注册学生的权限");
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$unRegStudents</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有注册学生的权限");
		}

	}
/**@author zlw*/
	public List getGraduatedStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id,String kaoqu_id,String shenfen_id,String flag)
			throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
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
			if (kaoqu_id != null && !kaoqu_id.equals("")) {
				searchProperties.add(new SearchProperty("u.kaoqu_id",
						kaoqu_id, "="));
			}
			if (shenfen_id != null && !shenfen_id.equals("")) {
				searchProperties.add(new SearchProperty("u.shenfen_id",
						shenfen_id, "="));
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
			if("biye".equals(flag)){
			searchProperties.add(new SearchProperty("u.isgraduated", "1", "="));
			}if("xuewei".equals(flag)){
				searchProperties.add(new SearchProperty("u.isdegreed", "1", "="));
			}
			//searchProperties.add(new SearchProperty("u.site_id",
					//basicManagePriv.getSiteList(), "in"));

			//searchProperties.add(new SearchProperty("u.major_id",
				//	basicManagePriv.getMajorList(), "in"));

		//	searchProperties.add(new SearchProperty("u.edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));

			//searchProperties.add(new SearchProperty("u.grade_id",
					//basicManagePriv.getGradeList(), "in"));
			OracleBasicUserList studentList = new OracleBasicUserList();
			if (page != null) {
				return studentList.getAllStudents(page, searchProperties, null);
			} else {
				return studentList.getAllStudents(searchProperties, null);
			}
		} else {
			throw new PlatformException("您没有浏览毕业学生的权限");
		}
	}
/**@author zlw**/
	public int getGraduatedStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id,String kaoqu_id,String shenfen_id) throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
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
			if (kaoqu_id != null && !kaoqu_id.equals("")) {
				searchProperties.add(new SearchProperty("u.kaoqu_id", kaoqu_id,
						"="));
			}
			if (shenfen_id != null && !shenfen_id.equals("")) {
				searchProperties.add(new SearchProperty("u.shenfen_id", shenfen_id,
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
			searchProperties.add(new SearchProperty("u.isgraduated", "1", "="));

			//searchProperties.add(new SearchProperty("u.site_id",
					//basicManagePriv.getSiteList(), "in"));

			//searchProperties.add(new SearchProperty("u.major_id",
				//	basicManagePriv.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("u.edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));

			//searchProperties.add(new SearchProperty("u.grade_id",
					//basicManagePriv.getGradeList(), "in"));

			OracleBasicUserList studentList = new OracleBasicUserList();
			return studentList.getAllStudentsNum(searchProperties);

		} else {
			throw new PlatformException("您没有浏览毕业学生的权限");
		}
	}
	
	public int getGraduatedStudentsNum2(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id,String kaoqu_id,String shenfen_id) throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
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
			if (kaoqu_id != null && !kaoqu_id.equals("")) {
				searchProperties.add(new SearchProperty("u.kaoqu_id", kaoqu_id,
						"="));
			}
			if (shenfen_id != null && !shenfen_id.equals("")) {
				searchProperties.add(new SearchProperty("u.shenfen_id", shenfen_id,
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

			//searchProperties.add(new SearchProperty("u.site_id",
					//basicManagePriv.getSiteList(), "in"));

			//searchProperties.add(new SearchProperty("u.major_id",
				//	basicManagePriv.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("u.edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));

			//searchProperties.add(new SearchProperty("u.grade_id",
					//basicManagePriv.getGradeList(), "in"));

			OracleBasicUserList studentList = new OracleBasicUserList();
			return studentList.getAllStudentsNum(searchProperties);

		} else {
			throw new PlatformException("您没有浏览毕业学生的权限");
		}
	}

	public List getRegStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.getRegisterStudent == 1) {
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

			searchProperties.add(new SearchProperty("u.site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("u.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("u.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("u.grade_id",
					basicManagePriv.getGradeList(), "in"));

			OracleRegister register = new OracleRegister();
			return register.getRegStudents(page, searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览注册学员信息的权限");
		}
	}

	public int getRegStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getRegisterStudent == 1) {
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

			searchProperties.add(new SearchProperty("u.site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("u.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("u.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("u.grade_id",
					basicManagePriv.getGradeList(), "in"));

			OracleRegister register = new OracleRegister();
			return register.getRegStudentsNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览注册学员信息的权限");
		}
	}
//zlw
	public List getRegStudents(String semester_id, Page page, String id,
			String reg_no, String name, String id_card, String phone,
			String site_id, String major_id, String edu_type_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.getRegisterStudent == 1) {
			List searchProperties = new ArrayList();
			List orderProperties = new ArrayList();

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
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));

			//searchProperties.add(new SearchProperty("u.site_id", 已经建立外键约束无需判断
				//	basicManagePriv.getSiteList(), "in"));

			//searchProperties.add(new SearchProperty("u.major_id",
					//basicManagePriv.getMajorList(), "in"));

		//	searchProperties.add(new SearchProperty("u.edutype_id",
					//basicManagePriv.getEduTypeList(), "in"));

			//searchProperties.add(new SearchProperty("u.grade_id",
				//	basicManagePriv.getGradeList(), "in"));

			OracleRegister register = new OracleRegister();
			orderProperties.add(new OrderProperty("site_id"));
			orderProperties.add(new OrderProperty("grade_id"));
			orderProperties.add(new OrderProperty("edutype_id"));
			orderProperties.add(new OrderProperty("major_id"));
			orderProperties.add(new OrderProperty("reg_no"));
			orderProperties.add(new OrderProperty("name"));
			orderProperties.add(new OrderProperty("se_name"));

			return register.getRegStudents(page, searchProperties,
					orderProperties);
		} else {
			throw new PlatformException("您没有浏览注册学员信息的权限");
		}
	}

	public List getNoElectiveRegStudents(String semester_id, Page page,
			String id, String reg_no, String name, String id_card,
			String phone, String site_id, String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		if (basicManagePriv.getRegisterStudent == 1) {
			List searchProperties = new ArrayList();
			List orderProperties = new ArrayList();

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
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
			orderProperties.add(new OrderProperty("reg_no"));
			orderProperties.add(new OrderProperty("name"));
			orderProperties.add(new OrderProperty("se_name"));

			searchProperties.add(new SearchProperty("u.site_id",
					basicManagePriv.getSiteList(), "in"));

			searchProperties.add(new SearchProperty("u.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("u.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));

			searchProperties.add(new SearchProperty("u.grade_id",
					basicManagePriv.getGradeList(), "in"));

			OracleRegister register = new OracleRegister();
			return register.getNoElectiveRegStudents(page, searchProperties,
					orderProperties, semester_id);
		} else {
			throw new PlatformException("您没有浏览注册学员信息的权限");
		}
	}
//zlw
	public int getRegStudentsNum(String semester_id, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException {
		if (basicManagePriv.getRegisterStudent == 1) {
			List searchProperties = new ArrayList();

			if (site_id != null && !site_id.equals("")) {
				searchProperties.add(new SearchProperty("u.site_id", site_id,
						"="));
			}

			if (id != null && !id.equals("")) {
				searchProperties.add(new SearchProperty("u.id", id, "="));
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
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));

		//	searchProperties.add(new SearchProperty("u.site_id",
			//		basicManagePriv.getSiteList(), "in"));

			//searchProperties.add(new SearchProperty("u.major_id",
				//	basicManagePriv.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("u.edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));

		//	searchProperties.add(new SearchProperty("u.grade_id",
				//	basicManagePriv.getGradeList(), "in"));

			OracleRegister register = new OracleRegister();
			return register.getRegStudentsNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览注册学员信息的权限");
		}
	}
//zlw
	public List getAllGradeEduMajorGroups(String gradeId) throws PlatformException {
		String sql = "select distinct g.id as grade_id,g.name as grade_name,t.id as edutype_id,"
				+ "t.name as edutype_name,m.id as major_id,m.name as major_name "
				+ "from entity_grade_info g,entity_edu_type t,entity_major_info m,entity_student_info u "
				+ "where u.isgraduated=0 and t.id=u.edutype_id and g.id=u.grade_id and u.grade_id='"+gradeId+"' and m.id=u.major_id and m.id<>'0' order by g.id,t.id,m.id";
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
			String[] major_id, String[] edutype_id, String[] grade_id,String s)
			throws PlatformException {
		if (basicManagePriv.registerBatch == 1) {
			OracleRegister register = new OracleRegister();
			List site_id = basicManagePriv.getSite();
//			int suc = register.regStudentsBatch(semester_id, id, major_id,
//					edutype_id, grade_id, site_id,s);
			int suc =0;

			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$regStudentsBatch</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有批量注册学生的权限");
		}
	}
//zlw
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
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$unRegStudentsBatch</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有批量注册学生的权限");
		}
	}
//zlw
	public int unRegStudents(String[] register_id) throws PlatformException {
		if (basicManagePriv.registerSingle == 1) {
			OracleRegister register = new OracleRegister();
			int suc = register.unRegStudents(register_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$unRegStudents</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有单个取消注册学生的权限");
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
			throw new PlatformException("您没有学期开课权限");
		}
	}

	public List getUnOpenCourses(Page page, String semester_id,
			String major_id, String course_id, String course_name)
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
			if (course_id != null && !course_id.equals("")) {
				searchProperties.add(new SearchProperty("course_id", course_id,
						"like"));
			}
			if (course_name != null && !course_name.equals("")) {
				searchProperties.add(new SearchProperty("course_name",
						course_name, "like"));
			}
			return basicActivityList.searchUnOpenCourses(page,
					searchProperties, null, semester);
		} else {
			throw new PlatformException("您没有学期开课权限");
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
			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));

			return basicActivityList.searchUnOpenCourseNum(searchProperties,
					semester);
		} else {
			throw new PlatformException("您没有学期开课权限");
		}
	}

	public int getUnOpenCoursesNum(String semester_id, String major_id,
			String course_id, String course_name) throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			OracleSemester semester = new OracleSemester();
			semester.setId(semester_id);
			List searchProperties = new ArrayList();
			if (major_id != null && !major_id.equals("")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			if (course_id != null && !course_id.equals("")) {
				searchProperties.add(new SearchProperty("course_id", course_id,
						"like"));
			}
			if (course_name != null && !course_name.equals("")) {
				searchProperties.add(new SearchProperty("course_name",
						course_name, "like"));
			}
			/*
			 * searchProperties.add(new SearchProperty("major_id",
			 * basicManagePriv .getMajorList(), "in"));
			 */

			return basicActivityList.searchUnOpenCourseNum(searchProperties,
					semester);
		} else {
			throw new PlatformException("您没有学期开课权限");
		}
	}

	public int isRegStudents(String semester_id, String user_id)
			throws PlatformException {
		if (basicManagePriv.getRegisterStudent == 1) {
			OracleRegister or = new OracleRegister();
			return or.isRegStudent(semester_id, user_id);
		} else {
			throw new PlatformException("您没有查询注册学生的权限");
		}
	}

	/**
	 * @author shu
	 * @param batch_id
	 * @return 查看该批次是处于什么状态。发布还是取消发布。
	 * @throws PlatformException
	 */
	public String isRelease(String batch_id)
	throws PlatformException {
		    OracleRecruitActivity  ora= new OracleRecruitActivity();
			//OracleRegister or = new OracleRegister();
//		  return  ora.isRelease(batch_id);
		    return null;
		
	}

	public ElectiveScore creatElectiveScore() throws PlatformException {
		return new OracleElectiveScore();
	}

	public Elective creatElective() throws PlatformException {
		return new OracleElective();
	}

	public TeachClass creatTeachClass() throws PlatformException {
		return new OracleTeachClass();
	}

	public OpenCourse creatOpenCourse() throws PlatformException {
		return new OracleOpenCourse();
	}

	public int deleteTeachPlan(String id) throws PlatformException {
		if (basicManagePriv.deleteTeachPlan == 1) {
			OracleTeachPlan teachPlan = new OracleTeachPlan();
			teachPlan.setId(id);
			int suc = teachPlan.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTeachPlan</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除教学计划权限");
		}
	}

	public TeachPlan getTeachPlan(String id) throws PlatformException {
		if (basicManagePriv.getTeachPlan == 1) {
			return new OracleTeachPlan(id);
		} else {
			throw new PlatformException("您没有浏览教学计划权限");
		}
	}

	public TeachPlan getTeachPlan(String majorId, String eduTypeId,
			String gradeId) throws PlatformException {
		if (basicManagePriv.getTeachPlan == 1) {
			return new OracleTeachPlan(majorId, eduTypeId, gradeId);
		} else {
			throw new PlatformException("您没有浏览教学计划权限");
		}
	}

	public int updateTeachPlan(String id, String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		if (basicManagePriv.updateTeachPlan == 1) {
			OracleTeachPlan teachPlan = new OracleTeachPlan();
			teachPlan.setId(id);
			OracleMajor major = new OracleMajor();
			major.setId(major_id);
			teachPlan.setMajor(major);
			OracleEduType eduType = new OracleEduType();
			eduType.setId(edu_type_id);
			teachPlan.setEduType(eduType);
			OracleGrade grade = new OracleGrade();
			grade.setId(grade_id);
			teachPlan.setGrade(grade);
			int suc = teachPlan.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateTeachPlan</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有编辑教学计划权限");
		}
	}

	public int updateTeachPlanCourse(String id, String credit,
			String courseTime,String jhCourseTime, String type,String semester) throws PlatformException {
		if (basicManagePriv.updateTeachPlanCourse == 1) {
			OracleTeachPlanCourse teachPlanCourse = new OracleTeachPlanCourse();
			teachPlanCourse.setId(id);
			teachPlanCourse.setCredit(Float.parseFloat(credit));
			teachPlanCourse.setCoursetime(Float.parseFloat(courseTime));
			teachPlanCourse.setType(type);
//			teachPlanCourse.setPlanSJTime(jhCourseTime);
			teachPlanCourse.setSemester(semester);
			int suc = teachPlanCourse.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateTeachPlanCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有编辑教学计划课程权限");
		}
	}

	public int updateTeachPlanCourse(String id, String credit,
			String courseTime, String type, String semester)
			throws PlatformException {
		if (basicManagePriv.updateTeachPlanCourse == 1) {
			OracleTeachPlanCourse teachPlanCourse = new OracleTeachPlanCourse();
			teachPlanCourse.setId(id);
			teachPlanCourse.setCredit(Float.parseFloat(credit));
			teachPlanCourse.setCoursetime(Float.parseFloat(courseTime));
			teachPlanCourse.setType(type);
			teachPlanCourse.setSemester(semester);
			int suc = teachPlanCourse.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateTeachPlanCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有编辑教学计划课程权限");
		}
	}

	public List getTeachPlanCourseTypes() throws PlatformException {
		TeachPlanCourseType type = new TeachPlanCourseType();
		return type.types();
	}

	public int getSingleTeachPlanCoursesNum(String studentId)
			throws PlatformException {
		if (basicManagePriv.getSingleTeachPlanCourse == 1) {
			OracleBasicEntityList basicEntityList = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			if (studentId != null && !studentId.equals("")) {
				searchProperties.add(new SearchProperty("id", studentId, "="));
			}
			return basicEntityList
					.getSingleTeachPlanCoursesNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览个性教学计划课程的权限");
		}
	}

	public int getUnSingleTeachPlanCoursesNum(String studentId,
			String courseId, String courseName, String majorId)
			throws PlatformException {
		if (basicManagePriv.getSingleTeachPlanCourse == 1) {
			OracleBasicEntityList basicEntityList = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			if (studentId != null && !studentId.equals("")) {
				searchProperties.add(new SearchProperty("id", studentId, "="));
			}
			if (courseId != null && !courseId.equals("")) {
				searchProperties.add(new SearchProperty("course_id", courseId,
						"="));
			}
			if (courseName != null && !courseName.equals("")) {
				searchProperties.add(new SearchProperty("course_name",
						courseName, "like"));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));

			return basicEntityList
					.getUnSingleTeachPlanCoursesNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览非个性教学计划课程的权限");
		}
	}

	public List getSingleTeachPlanCourses(Page page, String studentId)
			throws PlatformException {
		if (basicManagePriv.getSingleTeachPlanCourse == 1) {
			OracleBasicEntityList basicEntityList = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			if (studentId != null && !studentId.equals("")) {
				searchProperties.add(new SearchProperty("id", studentId, "="));
			}
			return basicEntityList.getSingleTeachPlanCourses(null,
					searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览个性教学计划课程的权限");
		}
	}

	public List getUnSingleTeachPlanCourses(Page page, String studentId,
			String courseId, String courseName, String majorId)
			throws PlatformException {
		if (basicManagePriv.getSingleTeachPlanCourse == 1) {
			OracleBasicEntityList basicEntityList = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			if (studentId != null && !studentId.equals("")) {
				searchProperties.add(new SearchProperty("id", studentId, "="));
			}
			if (courseId != null && !courseId.equals("")) {
				searchProperties.add(new SearchProperty("course_id", courseId,
						"="));
			}
			if (courseName != null && !courseName.equals("")) {
				searchProperties.add(new SearchProperty("course_name",
						courseName, "like"));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			searchProperties.add(new SearchProperty("major_id", basicManagePriv
					.getMajorList(), "in"));
			return basicEntityList.getUnSingleTeachPlanCourses(page,
					searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览非个性教学计划课程的权限");
		}
	}

	public int deleteSingleTeachPlanCourses(String studentId, String[] courseId)
			throws PlatformException {
		if (basicManagePriv.deleteSingleTeachPlanCourse == 1) {
			int count = 0;
			for (int i = 0; i < courseId.length; i++) {
				OracleSingleTeachPlanCourse courseSTP = new OracleSingleTeachPlanCourse();
				OracleCourse course = new OracleCourse();
				course.setId(courseId[i]);
				courseSTP.setCourse(course);
				OracleStudent student = new OracleStudent();
				student.setId(studentId);
				courseSTP.setStudent(student);
				count += courseSTP.delete();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteSingleTeachPlanCourses</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("您没有删除个性教学计划课程的权限");
		}
	}

	public int addSingleTeachPlanCourses(String studentId, List courseList)
			throws PlatformException {
		if (basicManagePriv.addSingleTeachPlanCourse == 1) {
			int count = 0;
			for (int i = 0; i < courseList.size(); i++) {
				OracleSingleTeachPlanCourse courseSTP = new OracleSingleTeachPlanCourse();
				Course course = (Course) courseList.get(i);
				courseSTP.setCourse(course);
				OracleStudent student = new OracleStudent();
				student.setId(studentId);
				courseSTP.setStudent(student);
				count += courseSTP.add();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addSingleTeachPlanCourses</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("您没有添加个性教学计划课程的权限");
		}
	}

	public List getSemesterTeachPlanCourses(Page page, String semesterId,
			String majorId, String eduTypeId, String gradeId)
			throws PlatformException {
		if (basicManagePriv.getTeachPlanCourse == 1) {
			OracleBasicEntityList basicEntityList = new OracleBasicEntityList();
			List searchProperties = new ArrayList();
			if (semesterId != null && !semesterId.equals("")) {
				searchProperties.add(new SearchProperty("c.semester_id",
						semesterId, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("a.major_id", majorId,
						"="));
			}
			if (eduTypeId != null && !eduTypeId.equals("")) {
				searchProperties.add(new SearchProperty("a.edutype_id",
						eduTypeId, "="));
			}
			if (gradeId != null && !gradeId.equals("")) {
				searchProperties.add(new SearchProperty("a.grade_id", gradeId,
						"like"));
			}
			searchProperties.add(new SearchProperty("a.major_id",
					basicManagePriv.getMajorList(), "in"));

			searchProperties.add(new SearchProperty("a.edutype_id",
					basicManagePriv.getEduTypeList(), "in"));
			searchProperties.add(new SearchProperty("a.grade_id",
					basicManagePriv.getGradeList(), "in"));

			return basicEntityList.getSemesterTeachPlanCourses(null,
					searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览教学计划课程的权限");
		}
	}

	public int updateAssistance(String openCourseId, String assistanceNote,
			String assistanceTitle, String assistanceReleaseStatus,
			String publisherId, String publisherName, String publisherType)
			throws PlatformException {
		if (basicManagePriv.updateAssistance == 1) {
			OracleTeachClass teachClass = new OracleTeachClass();
			OracleOpenCourse course = new OracleOpenCourse();
			course.setId(openCourseId);
			teachClass.setOpenCourse(course);
			teachClass.setAssistanceNote(assistanceNote);
			teachClass.setAssistanceTitle(assistanceTitle);
			teachClass.setAssistanceReleaseStatus(assistanceReleaseStatus);
			int i = teachClass.updateAssistance();
			if (assistanceReleaseStatus == null
					|| assistanceReleaseStatus.equals(""))
				return i;
			else if (assistanceReleaseStatus.equals("1"))
				i += teachClass.releaseAssistance(publisherId, publisherName,
						publisherType);
			else
				i += teachClass.unReleaseAssistance(publisherId, publisherName,
						publisherType);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateAssistance</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return i;
		} else {
			throw new PlatformException("您没有更新辅导信息的权限");
		}
	}

	public int deleteAssistance(String openCourseId, String assistanceNote,
			String assistanceTitle, String assistanceReleaseStatus,
			String publisherId, String publisherName, String publisherType)
			throws PlatformException {
		if (basicManagePriv.deleteAssistance == 1) {
			OracleTeachClass teachClass = new OracleTeachClass();
			OracleOpenCourse course = new OracleOpenCourse();
			course.setId(openCourseId);
			teachClass.setOpenCourse(course);
			teachClass.setAssistanceNote(assistanceNote);
			teachClass.setAssistanceTitle(assistanceTitle);
			teachClass.setAssistanceReleaseStatus(assistanceReleaseStatus);
			int i = teachClass.updateAssistance();
			if (assistanceReleaseStatus == null
					|| assistanceReleaseStatus.equals(""))
				return i;
			else if (assistanceReleaseStatus.equals("1"))
				i += teachClass.releaseAssistance(publisherId, publisherName,
						publisherType);
			else
				i += teachClass.unReleaseAssistance(publisherId, publisherName,
						publisherType);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteAssistance</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return i;
		} else {
			throw new PlatformException("您没有删除辅导信息的权限");
		}
	}

	public int releaseAssistance(String openCourseId, String publisherId,
			String publisherName, String publisherType)
			throws PlatformException {
		if (basicManagePriv.updateAssistance == 1) {
			OracleTeachClass teachClass = new OracleTeachClass();
			OracleOpenCourse course = new OracleOpenCourse();
			course.setId(openCourseId);
			teachClass.setOpenCourse(course);
			int suc = teachClass.releaseAssistance(publisherId, publisherName,
					publisherType);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$releaseAssistance</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有发布辅导信息的权限");
		}
	}

	public List searchOpenCourseMajor(Page page, String semester_id)
			throws PlatformException {
		if (basicManagePriv.getTeachPlanCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();
			if (semester_id != null && !semester_id.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semester_id, "="));
			}

			return basicActivityList.searchOpenCourseMajor(page,
					searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览学期下开课和专业的权限");
		}
	}

	public int searchOpenCourseMajorNum(String semester_id)
			throws PlatformException {
		if (basicManagePriv.getTeachPlanCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();
			if (semester_id != null && !semester_id.equals("")) {
				searchProperties.add(new SearchProperty("semester_id",
						semester_id, "="));
			}
			int suc = basicActivityList.searchOpenCourseMajorNum(
					searchProperties, null);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$searchOpenCourseMajorNum</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有浏览学期下开课和专业的权限");
		}

	}

	public GraduateCondition creatGraduateCondition() throws PlatformException {
		return new OracleGraduateCondition();
	}

	public GraduateCondition getGraduateCondition(String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getGraduateCondition == 1) {
			return new OracleGraduateCondition(major_id, grade_id, edu_type_id);
		} else {
			throw new PlatformException("您没有浏览毕业条件的权限");
		}
	}

	public List getGraduateConditions(Page page, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		return null;
	}

	public int getGraduateConditionsNum(String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		return 0;
	}
/**@author zlw*/
	public List getAllGraduateConditions(Page page, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getGraduateCondition == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
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

		//	searchProperties.add(new SearchProperty("major_id", basicManagePriv
					//.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));
			//searchProperties.add(new SearchProperty("grade_id", basicManagePriv
				//	.getGradeList(), "in"));

			return basicActivityList.searchAllGraduateConditions(page,
					searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览毕业条件的权限");
		}
	}
	 /**@author zlw
	   * 
	   * */
	public int getAllGraduateConditionsNum(String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		if (basicManagePriv.getGraduateCondition == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
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

		//	searchProperties.add(new SearchProperty("major_id", basicManagePriv
				//	.getMajorList(), "in"));

		//	searchProperties.add(new SearchProperty("edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));
			//searchProperties.add(new SearchProperty("grade_id", basicManagePriv
					//.getGradeList(), "in"));

			return basicActivityList
					.searchAllGraduateConditionsNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览毕业条件的权限");
		}
	}
/**@author zlw
 * 应用
 * */
	public int addGraduateConditions(List gemList, HashMap condition)
			throws PlatformException {
		if (basicManagePriv.addGraduateCondition == 1) {
			int count = 0;
			for (int i = 0; i < gemList.size(); i++) {
				GradeEduMajorGroup gem = (GradeEduMajorGroup) gemList.get(i);
				OracleGraduateCondition con = new OracleGraduateCondition();
				con.setCondition(condition);
				OracleGrade grade = new OracleGrade();
				grade.setId(gem.getGrade_id());
				con.setGrade(grade);
				OracleEduType eduType = new OracleEduType();
				eduType.setId(gem.getEdutype_id());
				con.setEduType(eduType);
				OracleMajor major = new OracleMajor();
				major.setId(gem.getMajor_id());
				con.setMajor(major);
				if (con.isExist() > 0)
					count += con.update();
				else
					count += con.add();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addGraduateConditions</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("您没有添加毕业条件的权限");
		}
	}

	public int deleteGraduateConditions(List gemList) throws PlatformException {
		if (basicManagePriv.deleteGraduateCondition == 1) {
			int count = 0;
			for (int i = 0; i < gemList.size(); i++) {
				GradeEduMajorGroup gem = (GradeEduMajorGroup) gemList.get(i);
				OracleGraduateCondition con = new OracleGraduateCondition();
				OracleGrade grade = new OracleGrade();
				grade.setId(gem.getGrade_id());
				con.setGrade(grade);
				OracleEduType eduType = new OracleEduType();
				eduType.setId(gem.getEdutype_id());
				con.setEduType(eduType);
				OracleMajor major = new OracleMajor();
				major.setId(gem.getMajor_id());
				con.setMajor(major);
				count += con.delete();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteGraduateConditions</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("您没有删除毕业条件的权限");
		}
	}

	public int setGraduateStatus(String major_id, String grade_id,
			String edutype_id) throws PlatformException {
		if (basicManagePriv.checkGraduated == 1) {
			OracleGraduateActivity graduateActivity = new OracleGraduateActivity();
			int suc = graduateActivity.setGraduateStatus(major_id, grade_id,
					edutype_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$setGraduateStatus</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有设定学生毕业状态的权限");
		}
	}

	public List getTeachClassesByTeacherId(Page page, String teacherId)
			throws PlatformException {
		if (basicManagePriv.getTeachClass == 1) {
			OracleBasicActivityList list = new OracleBasicActivityList();
			OracleTeacher teacher = new OracleTeacher();
			teacher.setId(teacherId);
			return list.searchTeachClass(page, null, null, teacher);
		} else {
			throw new PlatformException("您没有查看教学班的权限");
		}
	}

	public int getTeachClassesByTeacherIdNum(String teacherId)
			throws PlatformException {
		if (basicManagePriv.getTeachClass == 1) {
			OracleBasicActivityList list = new OracleBasicActivityList();
			OracleTeacher teacher = new OracleTeacher();
			teacher.setId(teacherId);
			return list.searchTeachClassNum(null, teacher);
		} else {
			throw new PlatformException("您没有查看教学班的权限");
		}
	}

	// 获得课程已确认选课人数
	public int getElectiveStudentNumByOpenCourseId(String openCourseId,
			String status) throws PlatformException {
		if (basicManagePriv.electiveStudent == 1) {
			OracleBasicActivityList list = new OracleBasicActivityList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("open_course_id",
					openCourseId, "="));
			searchProperty.add(new SearchProperty("status", status, "="));
			return list.getElectiveStudentNumByOpenCourseId(searchProperty);
		} else {
			throw new PlatformException("您没有查看选课的权限");
		}
	}
/**@author zlw**/
	public List getUnGraduatedStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id,
			String graduate_status,String kaoqu_id,String shenfen_id) throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
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
			if (kaoqu_id != null && !kaoqu_id.equals("")) {
				searchProperties.add(new SearchProperty("u.kaoqu_id", kaoqu_id,
						"="));
			}
			if (shenfen_id != null && !shenfen_id.equals("")) {
				searchProperties.add(new SearchProperty("u.shenfen_id", shenfen_id,
						"="));
			}


			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("u.name", name, "like"));

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
			if (graduate_status != null && !graduate_status.equals("")) {
				searchProperties.add(new SearchProperty("u.graduate_status",
						graduate_status, "="));
			}
			searchProperties.add(new SearchProperty("u.isgraduated", "0", "="));

			//searchProperties.add(new SearchProperty("u.site_id",
				//	basicManagePriv.getSiteList(), "in"));

			//searchProperties.add(new SearchProperty("u.major_id",
				//	basicManagePriv.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("u.edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));
			//searchProperties.add(new SearchProperty("u.grade_id",
					//basicManagePriv.getGradeList(), "in"));
			List orderProperties = new ArrayList();
			orderProperties.add(new OrderProperty("site_id"));
			orderProperties.add(new OrderProperty("grade_id"));
			orderProperties.add(new OrderProperty("major_id"));
			orderProperties.add(new OrderProperty("edutype_id"));
			orderProperties.add(new OrderProperty("reg_no"));
			OracleBasicUserList studentList = new OracleBasicUserList();
			if (page != null) {
				return studentList.getAllStudents(page, searchProperties,
						orderProperties);
			} else {
				return studentList.getAllStudents(searchProperties,
						orderProperties);
			}
		} else {
			throw new PlatformException("您没有浏览毕业学生的权限");
		}
	}
/***@author zlw**/
	public int getUnGraduatedStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id, String graduate_status,String kaoqu_id,String shenfen_id)
			throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
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
			if (kaoqu_id != null && !kaoqu_id.equals("")) {
				searchProperties.add(new SearchProperty("u.kaoqu_id", kaoqu_id,
						"="));
			}
			if (shenfen_id != null && !shenfen_id.equals("")) {
				searchProperties.add(new SearchProperty("u.shenfen_id", shenfen_id,
						"="));
			}
			if (grade_id != null && !grade_id.equals("")) {
				searchProperties.add(new SearchProperty("u.grade_id", grade_id,
						"="));
			}

			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("u.name", name, "like"));

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
			if (graduate_status != null && !graduate_status.equals("")) {
				searchProperties.add(new SearchProperty("u.graduate_status",
						graduate_status, "="));
			}
			searchProperties.add(new SearchProperty("u.isgraduated", "0", "="));

			//searchProperties.add(new SearchProperty("u.site_id",
				//	basicManagePriv.getSiteList(), "in"));

			//searchProperties.add(new SearchProperty("u.major_id",
				//	basicManagePriv.getMajorList(), "in"));

		//	searchProperties.add(new SearchProperty("u.edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));
			//searchProperties.add(new SearchProperty("u.grade_id",
				//	basicManagePriv.getGradeList(), "in"));

			OracleBasicUserList studentList = new OracleBasicUserList();
			return studentList.getAllStudentsNum(searchProperties);

		} else {
			throw new PlatformException("您没有浏览毕业学生的权限");
		}
	}
/**@author zlw**/
	public List getGraduateStat(String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
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

			//searchProperties.add(new SearchProperty("major_id", basicManagePriv
					//.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("edutype_id",
					//basicManagePriv.getEduTypeList(), "in"));
			//searchProperties.add(new SearchProperty("grade_id", basicManagePriv
					//.getGradeList(), "in"));

			return activityList.getGraduateStat(null, searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览毕业学生的权限");
		}
	}
/**@author zlw
 * @param flag="biye"输入毕业证号
 *        flag="xuewei"输入学位证号
 * **/
	public int setGraduateNo(List idList,String flag) throws PlatformException {
		if (basicManagePriv.setGraduateNo == 1) {
			OracleGraduateActivity graduateactivity = new OracleGraduateActivity();
//			int suc = graduateactivity.setGraduateNo(idList,flag);
			int suc = 0;
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$setGraduateNo</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有上报毕业证书的权限");
		}
	}

	// 统考相关
	/**@author zlw
	 * 增加课程操作
	 * **/
	public int addUniteExamCourse(String courseName, String note)
			throws PlatformException {
		if (basicManagePriv.addUniteExamCourse == 1) {
			OracleUniteExamCourse course = new OracleUniteExamCourse();
			course.setName(courseName);
			course.setNote(note);
			int suc = course.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addUniteExamCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加统考课程的权限");
		}
	}
	/**@author zlw
	 * 更新课程操作
	 * **/
	public int updateUniteExamCourse(String id, String courseName, String note)
			throws PlatformException {
		if (basicManagePriv.updateUniteExamCourse == 1) {
			OracleUniteExamCourse course = new OracleUniteExamCourse();
			course.setId(id);
			course.setName(courseName);
			course.setNote(note);
			int suc = course.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateUniteExamCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改统考课程的权限");
		}
	}
	/**@author zlw
	 * 查找课程操作
	 * **/
	public List getUniteExamCourses(Page page, String id, String courseName)
			throws PlatformException {
		if (basicManagePriv.getUniteExamCourses == 1) {
			OracleBasicActivityList list = new OracleBasicActivityList();
			List searchPropertys = new ArrayList();
			List orderPropertys = new ArrayList();
			if (id != null && !id.equals("") && !id.equals("null")) {
				searchPropertys.add(new SearchProperty("id", id, "="));
			}
			if (courseName != null && !courseName.equals("")
					&& !courseName.equals("null")) {
				searchPropertys.add(new SearchProperty("name", courseName,
						"like"));
				orderPropertys.add(new OrderProperty("name", "ASC"));
			}
			return list.getUniteExamCoursesByName(page, searchPropertys,
					orderPropertys);
		} else {
			throw new PlatformException("您没有查询统考课程的权限");
		}
	}
	/**@author zlw
	 * @return 返回统考课程数目
	 * **/
	public int getUniteExamCourseNum(String courseName)
			throws PlatformException {
		if (basicManagePriv.getUniteExamCourses == 1) {
			OracleBasicActivityList list = new OracleBasicActivityList();
			List searchPropertys = new ArrayList();
			if (courseName != null && !courseName.equals("")
					&& !courseName.equals("null")) {
				searchPropertys.add(new SearchProperty("name", courseName,
						"like"));
			}
			return list.getUniteExamCourseNumByName(searchPropertys);
		} else {
			throw new PlatformException("您没有查询统考课程的权限");
		}
	}

	public int delUniteExamCourse(String id) throws PlatformException {
		if (basicManagePriv.delUniteExamCourse == 1) {
			OracleUniteExamCourse course = new OracleUniteExamCourse();
			course.setId(id);
			int suc = course.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$delUniteExamCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除统考课程的权限");
		}
	}
	/**
	 * @author Administrator
	 * 批量删除统考课程
	 * **/
	public int delBatchUniteExamCourse(String[] ids) throws PlatformException {
		if (basicManagePriv.delBatchUniteExamCourse == 1) {
			OracleUniteExamCourse course = new OracleUniteExamCourse();
			int count = 0;
			if (ids == null)
				return count;
			for (int i = 0; i < ids.length; i++) {
				course.setId(ids[i]);
				count += course.delete();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$delBatchUniteExamCourse</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("您没有批量删除统考课程的权限");
		}
	}
/**@author zlw*/
	public List getUniteExamScores(Page page, String siteId, String gradeId,
			String eduTypeId, String majorId, String regNo, String name,String kaoqu_id,String shenfen_id)
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
			if (kaoqu_id != null && !kaoqu_id.equals("")
					&& !kaoqu_id.equals("null"))
				searchPropertys.add(new SearchProperty("kaoqu_id", kaoqu_id,
						"="));
			if (shenfen_id != null && !shenfen_id.equals("")
					&& !shenfen_id.equals("null"))
				searchPropertys.add(new SearchProperty("shenfen_id", shenfen_id,
						"="));
			if (majorId != null && !majorId.equals("")
					&& !majorId.equals("null"))
				searchPropertys
						.add(new SearchProperty("major_id", majorId, "="));
			if (regNo != null && !regNo.equals("") && !regNo.equals("null"))
				searchPropertys.add(new SearchProperty("reg_no", regNo, "="));
			if (name != null && !name.equals("") && !name.equals("null"))
				searchPropertys.add(new SearchProperty("name", name, "like"));

			//searchPropertys.add(new SearchProperty("site_id", basicManagePriv
					//.getSiteList(), "in"));
		//	searchPropertys.add(new SearchProperty("major_id", basicManagePriv
					//.getMajorList(), "in"));

		//	searchPropertys.add(new SearchProperty("edutype_id",
		//			basicManagePriv.getEduTypeList(), "in"));
		//	searchPropertys.add(new SearchProperty("grade_id", basicManagePriv
				//	.getGradeList(), "in"));

			return list.getUniteExamScores(page, searchPropertys,
					orderPropertys);
		} else {
			throw new PlatformException("您没有查询统考成绩的权限");
		}
	}
	
	/**
	 * @author shu
	 * @param page
	 * @param siteId
	 * @param gradeId
	 * @param eduTypeId
	 * @param majorId
	 * @param regNo
	 * @param name
	 * @param kaoqu_id
	 * @param shenfen_id
	 * @return 通过学生的id来查询出其相关的课程信息。
	 * @throws PlatformException
	 */	
	public List getUniteExamScores3(Page page, String stu_id,String siteId, String gradeId,
			String eduTypeId, String majorId, String regNo, String name,String kaoqu_id,String shenfen_id)
			throws PlatformException {
		if (basicManagePriv.getUniteExamScores == 1) {
			OracleBasicActivityList list = new OracleBasicActivityList();
			List searchPropertys = new ArrayList();
			List orderPropertys = new ArrayList();
			//orderPropertys.add(new OrderProperty("reg_no", "ASC"));
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
			if (kaoqu_id != null && !kaoqu_id.equals("")
					&& !kaoqu_id.equals("null"))
				searchPropertys.add(new SearchProperty("kaoqu_id", kaoqu_id,
						"="));
			if (shenfen_id != null && !shenfen_id.equals("")
					&& !shenfen_id.equals("null"))
				searchPropertys.add(new SearchProperty("shenfen_id", shenfen_id,
						"="));
			if (majorId != null && !majorId.equals("")
					&& !majorId.equals("null"))
				searchPropertys
						.add(new SearchProperty("major_id", majorId, "="));
			if (regNo != null && !regNo.equals("") && !regNo.equals("null"))
				searchPropertys.add(new SearchProperty("reg_no", regNo, "like"));
			if (name != null && !name.equals("") && !name.equals("null"))
				searchPropertys.add(new SearchProperty("name", name, "like"));
			
			if (stu_id != null && !stu_id.equals("") && !stu_id.equals("null"))
				searchPropertys.add(new SearchProperty("id", stu_id, "="));
			//searchPropertys.add(new SearchProperty("site_id", basicManagePriv
					//.getSiteList(), "in"));
		//	searchPropertys.add(new SearchProperty("major_id", basicManagePriv
					//.getMajorList(), "in"));

		//	searchPropertys.add(new SearchProperty("edutype_id",
		//			basicManagePriv.getEduTypeList(), "in"));
		//	searchPropertys.add(new SearchProperty("grade_id", basicManagePriv
				//	.getGradeList(), "in"));

//			return list.getUniteExamScores3(page, searchPropertys,
//					orderPropertys);
			return null;
		} else {
			throw new PlatformException("您没有查询统考成绩的权限");
		}
	}
	
	public List getUniteExamScores2(Page page, String siteId, String gradeId,
			String eduTypeId, String majorId, String regNo, String name,String kaoqu_id,String shenfen_id)
			throws PlatformException {
		if (basicManagePriv.getUniteExamScores == 1) {
			OracleBasicActivityList list = new OracleBasicActivityList();
			List searchPropertys = new ArrayList();
			List orderPropertys = new ArrayList();
			orderPropertys.add(new OrderProperty("id", "ASC"));
			//orderPropertys.add(new OrderProperty("reg_no", "ASC"));
			//orderPropertys.add(new OrderProperty("course_id", "ASC"));
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
			if (kaoqu_id != null && !kaoqu_id.equals("")
					&& !kaoqu_id.equals("null"))
				searchPropertys.add(new SearchProperty("kaoqu_id", kaoqu_id,
						"="));
			if (shenfen_id != null && !shenfen_id.equals("")
					&& !shenfen_id.equals("null"))
				searchPropertys.add(new SearchProperty("shenfen_id", shenfen_id,
						"="));
			if (majorId != null && !majorId.equals("")
					&& !majorId.equals("null"))
				searchPropertys
						.add(new SearchProperty("major_id", majorId, "="));
			if (regNo != null && !regNo.equals("") && !regNo.equals("null"))
				searchPropertys.add(new SearchProperty("reg_no", regNo, "like"));
			if (name != null && !name.equals("") && !name.equals("null"))
				searchPropertys.add(new SearchProperty("name", name, "like"));

			//searchPropertys.add(new SearchProperty("site_id", basicManagePriv
					//.getSiteList(), "in"));
		//	searchPropertys.add(new SearchProperty("major_id", basicManagePriv
					//.getMajorList(), "in"));

		//	searchPropertys.add(new SearchProperty("edutype_id",
		//			basicManagePriv.getEduTypeList(), "in"));
		//	searchPropertys.add(new SearchProperty("grade_id", basicManagePriv
				//	.getGradeList(), "in"));

//			return list.getUniteExamScores2(page, searchPropertys,
//					orderPropertys);
			return null;
		} else {
			throw new PlatformException("您没有查询统考成绩的权限");
		}
	}
	
/**@author zlw**/
	public int getUniteExamScoreNum(String siteId, String gradeId,
			String eduTypeId, String majorId, String regNo, String name,String kaoqu_id,String shenfen_id)
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
			if (kaoqu_id != null && !kaoqu_id.equals("")
					&& !kaoqu_id.equals("null"))
				searchPropertys.add(new SearchProperty("kaoqu_id", kaoqu_id,
						"="));
			if (shenfen_id != null && !shenfen_id.equals("")
					&& !shenfen_id.equals("null"))
				searchPropertys.add(new SearchProperty("shenfen_id", shenfen_id,
						"="));
			if (majorId != null && !majorId.equals("")
					&& !majorId.equals("null"))
				searchPropertys
						.add(new SearchProperty("major_id", majorId, "="));
			
			if (regNo != null && !regNo.equals("") && !regNo.equals("null"))
				searchPropertys.add(new SearchProperty("reg_no", regNo, "like"));
			
			if (name != null && !name.equals("") && !name.equals("null"))
				searchPropertys.add(new SearchProperty("name", name, "like"));

			//searchPropertys.add(new SearchProperty("site_id", basicManagePriv
				//	.getSiteList(), "in"));
			//searchPropertys.add(new SearchProperty("major_id", basicManagePriv
				//	.getMajorList(), "in"));

			//searchPropertys.add(new SearchProperty("edutype_id",
					//basicManagePriv.getEduTypeList(), "in"));
		//	searchPropertys.add(new SearchProperty("grade_id", basicManagePriv
				//	.getGradeList(), "in"));

			return list.getUniteExamScoreNum(searchPropertys);
		} else {
			throw new PlatformException("您没有查询统考成绩的权限");
		}
	}
/**@author zlw
 * 批量插入统考数据
 * **/
	public List addBatchUniteExamScore(String src, String filename,
			boolean isUpdate) throws PlatformException {
		if (basicManagePriv.addBatchUniteExamScore == 1) {
			OracleBasicActivityList list = new OracleBasicActivityList();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addBatchUniteExamScore</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return list.addBatchUniteExamScore(src, filename, isUpdate);
		} else {
			throw new PlatformException("您没有批量导入统考成绩的权限");
		}
	}

	public TestManage creatTestManage() {
		TestFactory testFactory = TestFactory.getInstance();
		TestPriv priv = testFactory.getTestPriv();
		priv.getLore = 1;
		priv.getLores = 1;
		priv.getPaperPolicy = 1;
		priv.getPaperPolicys = 1;
		priv.addPaperPolicy = 1;
		return testFactory.creatTestManage(priv);
	}

	public TestManage creatExpendTestManage() {
		TestFactory testFactory = TestFactory.getInstance();
		TestPriv priv = testFactory.getTestPriv();
		priv.getLore = 1;
		priv.getLores = 1;
		priv.getPaperPolicy = 1;
		priv.getPaperPolicys = 1;
		priv.addPaperPolicy = 1;
		return testFactory.creatExpendTestManage(priv);
	}
	
	public DegreeCondition getDegreeCondition(String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getGraduateCondition == 1) {
			return new OracleDegreeCondition(major_id, grade_id, edu_type_id);
		} else {
			throw new PlatformException("您没有浏览学位报名条件的权限");
		}
	}

	public List getDegreeConditions(Page page, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		return null;
	}

	public int getDegreeConditionsNum(String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		return 0;
	}
/***@author zlw
 * */
	public List getAllDegreeConditions(Page page, String major_id,
			String edu_type_id, String grade_id) throws PlatformException {
		if (basicManagePriv.getDegreeCondition == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
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

			//searchProperties.add(new SearchProperty("major_id", basicManagePriv
					//.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));
			//searchProperties.add(new SearchProperty("grade_id", basicManagePriv
				//	.getGradeList(), "in"));

			return basicActivityList.searchAllDegreeConditions(page,
					searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览学位条件的权限");
		}
	}
/**@author zlw**/
	public int getAllDegreeConditionsNum(String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
		if (basicManagePriv.getGraduateCondition == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
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

			//searchProperties.add(new SearchProperty("major_id", basicManagePriv
					//.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("edutype_id",
					//basicManagePriv.getEduTypeList(), "in"));
			//searchProperties.add(new SearchProperty("grade_id", basicManagePriv
					//.getGradeList(), "in"));
			return basicActivityList
					.searchAllDegreeConditionsNum(searchProperties);
		} else {
			throw new PlatformException("您没有浏览学位条件的权限");
		}
	}

	public int addDegreeConditions(List gemList, HashMap condition)
			throws PlatformException {
		if (basicManagePriv.addDegreeCondition == 1) {
			int count = 0;
			for (int i = 0; i < gemList.size(); i++) {
				GradeEduMajorGroup gem = (GradeEduMajorGroup) gemList.get(i);
				OracleDegreeCondition con = new OracleDegreeCondition();
				con.setCondition(condition);
				OracleGrade grade = new OracleGrade();
				grade.setId(gem.getGrade_id());
				con.setGrade(grade);
				OracleEduType eduType = new OracleEduType();
				eduType.setId(gem.getEdutype_id());
				con.setEduType(eduType);
				OracleMajor major = new OracleMajor();
				major.setId(gem.getMajor_id());
				con.setMajor(major);
				if (con.isExist() > 0)
					count += con.update();
				else
					count += con.add();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addDegreeConditions</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("您没有添加学位报名条件的权限");
		}
	}

	public int deleteDegreeConditions(List gemList) throws PlatformException {
		if (basicManagePriv.deleteDegreeCondition == 1) {
			int count = 0;
			for (int i = 0; i < gemList.size(); i++) {
				GradeEduMajorGroup gem = (GradeEduMajorGroup) gemList.get(i);
				OracleDegreeCondition con = new OracleDegreeCondition();
				OracleGrade grade = new OracleGrade();
				grade.setId(gem.getGrade_id());
				con.setGrade(grade);
				OracleEduType eduType = new OracleEduType();
				eduType.setId(gem.getEdutype_id());
				con.setEduType(eduType);
				OracleMajor major = new OracleMajor();
				major.setId(gem.getMajor_id());
				con.setMajor(major);
				count += con.delete();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteDegreeConditions</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new PlatformException("您没有删除学位报名条件的权限");
		}
	}

	public int setDegreeStatus(String major_id, String grade_id,
			String edutype_id) throws PlatformException {
		if (basicManagePriv.checkDegreed == 1) {
			OracleDegreeActivity degreeActivity = new OracleDegreeActivity();
			int suc = degreeActivity.setDegreeStatus(major_id, grade_id,
					edutype_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$setDegreeStatus</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有设定学生学位报名状态的权限");
		}
	}

	public List getDegreeStat(String major_id, String edu_type_id,
			String grade_id) throws PlatformException {
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

		//	searchProperties.add(new SearchProperty("major_id", basicManagePriv
					//.getMajorList(), "in"));

		//	searchProperties.add(new SearchProperty("edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));
			//searchProperties.add(new SearchProperty("grade_id", basicManagePriv
					//.getGradeList(), "in"));

			return activityList.getDegreeStat(null, searchProperties, null);
		} else {
			throw new PlatformException("您没有浏览毕业学生的权限");
		}
	}

	public List getChangeMajorStudents(Page page) throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus.getChangeMajorStudents(page);
		} else {
			throw new PlatformException("您没有浏览学籍异动的权限");
		}
	}
 /**
  * @author zlw
  * */
	public List getChangeMajorStudents(Page page, String site_id, String reg_no)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus.getChangeMajorStudents(page, site_id, reg_no);
		} else {
			throw new PlatformException("您没有浏览学籍异动的权限");
		}
	}

	public int getChangeMajorStudentsNum() throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus.getChangeMajorStudentsNum();
		} else {
			throw new PlatformException("您没有浏览学籍异动的权限");
		}
	}
/**
 * @author zlw
 * */
	public int getChangeMajorStudentsNum(String site_id, String reg_no)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus.getChangeMajorStudentsNum(site_id, reg_no);
		} else {
			throw new PlatformException("您没有浏览学籍异动的权限");
		}
	}

	public List getStatusChangeStudents(Page page, String statusChangeType)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus.getStatusChangeStudents(page, statusChangeType);
		} else {
			throw new PlatformException("您没有浏览学籍异动的权限");
		}
	}

	public int getStatusChangeStudentsNum(String statusChangeType)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus.getStatusChangeStudentsNum(statusChangeType);
		} else {
			throw new PlatformException("您没有浏览学籍异动的权限");
		}
	}

	public int getStatusChangeStudentRecordsNum(String statusChangeType)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus
					.getStatusChangeStudentRecodsNum(statusChangeType);
		} else {
			throw new PlatformException("您没有浏览学籍异动的权限");
		}
	}
	/**@author zlw
	 * */
	public int getStatusChangeStudentRecordsNum(String statusChangeType,
			String siteId, String regNo) throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus.getStatusChangeStudentRecodsNum(
					statusChangeType, siteId, regNo);
		} else {
			throw new PlatformException("您没有浏览学籍异动的权限");
		}
	}

	public List getStatusChangeStudentRecords(Page page, String statusChangeType)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus.getStatusChangeStudentRecods(page,
					statusChangeType);
		} else {
			throw new PlatformException("您没有浏览学籍异动的权限");
		}
	}
	/**@author zlw
	 * */
	public List getStatusChangeStudentRecords(Page page,
			String statusChangeType, String siteId, String regNo)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleStatusChangeActivity changeStatus = new OracleStatusChangeActivity();
			return changeStatus.getStatusChangeStudentRecods(page,
					statusChangeType, siteId, regNo);
		} else {
			throw new PlatformException("您没有浏览学籍异动的权限");
		}
	}
/**@author zlw
 * @return 已经毕业申请学位的学生
 * */
	public List getUnDegreedStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id,
			String degree_status) throws PlatformException {
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
				searchProperties.add(new SearchProperty("u.name", name, "like"));

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
			if (degree_status != null && !degree_status.equals("")) {
				searchProperties.add(new SearchProperty("u.degree_status",
						degree_status, "="));
			}
			searchProperties.add(new SearchProperty("u.isgraduated", "1", "="));

			searchProperties.add(new SearchProperty("u.isdegreed", "0", "="));

		//	searchProperties.add(new SearchProperty("u.site_id",
				//	basicManagePriv.getSiteList(), "in"));

			//searchProperties.add(new SearchProperty("u.major_id",
					//basicManagePriv.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("u.edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));
		//	searchProperties.add(new SearchProperty("u.grade_id",
					//basicManagePriv.getGradeList(), "in"));

			OracleBasicUserList studentList = new OracleBasicUserList();
			if (page != null) {
				return studentList.getAllStudents(page, searchProperties, null);
			} else {
				return studentList.getAllStudents(searchProperties, null);
			}
		} else {
			throw new PlatformException("您没有浏览学位报名学生的权限");
		}
	}

	public int getUnDegreedStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id, String degree_status)
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
				searchProperties.add(new SearchProperty("u.name", name, "like"));

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
			if (degree_status != null && !degree_status.equals("")) {
				searchProperties.add(new SearchProperty("u.degree_status",
						degree_status, "="));
			}
			searchProperties.add(new SearchProperty("u.isgraduated", "1", "="));

			searchProperties.add(new SearchProperty("u.isdegreed", "0", "="));
			//searchProperties.add(new SearchProperty("u.site_id",
					//basicManagePriv.getSiteList(), "in"));

			//searchProperties.add(new SearchProperty("u.major_id",
					//basicManagePriv.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("u.edutype_id",
					//basicManagePriv.getEduTypeList(), "in"));
			//searchProperties.add(new SearchProperty("u.grade_id",
					//basicManagePriv.getGradeList(), "in"));

			OracleBasicUserList studentList = new OracleBasicUserList();
			return studentList.getAllStudentsNum(searchProperties);

		} else {
			throw new PlatformException("您没有浏览学位报名学生的权限");
		}
	}

	public int setDegreed(List studentList, String operatorId,
			String operatorName) throws PlatformException {
		if (basicManagePriv.setDegreed == 1) {
			OracleDegreeActivity degreeactivity = new OracleDegreeActivity();
			int suc = degreeactivity.setDegreed(studentList, operatorId,
					operatorName);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$setDegreed</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有执行学位报名操作的权限");
		}
	}
	
	
	public int setDegreedUn(List studentList, String operatorId,
			String operatorName) throws PlatformException {
		if (basicManagePriv.setDegreed == 1) {
			OracleDegreeActivity degreeactivity = new OracleDegreeActivity();
//			int suc = degreeactivity.setDegreedUn(studentList, operatorId,
//					operatorName);
			int suc = 0;
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$setDegreed</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有执行学位报名操作的权限");
		}
	}
/**@author zlw
 * @see 在该参数条件下查询通过毕业审核的学生**/
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
			searchProperties.add(new SearchProperty("u.isgraduated", "1", "="));

		//	searchProperties.add(new SearchProperty("u.site_id",
					//basicManagePriv.getSiteList(), "in"));

			//searchProperties.add(new SearchProperty("u.major_id",
					//basicManagePriv.getMajorList(), "in"));

			//searchProperties.add(new SearchProperty("u.edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));

			//searchProperties.add(new SearchProperty("u.grade_id",
					//basicManagePriv.getGradeList(), "in"));
			OracleBasicUserList studentList = new OracleBasicUserList();
			if (page != null) {
				return studentList.getAllStudents(page, searchProperties, null);
			} else {
				return studentList.getAllStudents(searchProperties, null);
			}
		} else {
			throw new PlatformException("您没有浏览学位报名学生的权限");
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
			searchProperties.add(new SearchProperty("u.isgraduated", "1", "="));

		//	searchProperties.add(new SearchProperty("u.site_id",
				//	basicManagePriv.getSiteList(), "in"));

			//searchProperties.add(new SearchProperty("u.major_id",
				//	basicManagePriv.getMajorList(), "in"));

		//	searchProperties.add(new SearchProperty("u.edutype_id",
				//	basicManagePriv.getEduTypeList(), "in"));

			//searchProperties.add(new SearchProperty("u.grade_id",
					//basicManagePriv.getGradeList(), "in"));

			OracleBasicUserList studentList = new OracleBasicUserList();
			return studentList.getAllStudentsNum(searchProperties);

		} else {
			throw new PlatformException("您没有浏览学位报名学生的权限");
		}
	}

	public int setDegreeScore(List idList) throws PlatformException {
		if (basicManagePriv.setDegreeScore == 1) {
			OracleDegreeActivity degreeactivity = new OracleDegreeActivity();
			int suc = degreeactivity.setDegreeScore(idList);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$setDegreeScore</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有上报学位成绩的权限");
		}
	}

	public int setDegreeRelease(List idList, List releaseList)
			throws PlatformException {
		if (basicManagePriv.setDegreeScoreRelease == 1) {
			OracleDegreeActivity degreeactivity = new OracleDegreeActivity();
			int suc = degreeactivity.setDegreeRelease(idList, releaseList);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$setDegreeRelease</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有发布学位成绩的权限");
		}
	}

	public int getTeacherInteractionStatisticNum(String semesterId)
			throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList
					.getTeacherInteractionStatisticNum(semesterId);
		} else {
			throw new PlatformException("您没有查询教师的权限");
		}
	}
	
	public int getTeacherInteractionStatisticNum(String semesterId,String dep_id)
			throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList
					.getTeacherInteractionStatisticNum(semesterId,dep_id);
		} else {
			throw new PlatformException("您没有查询教师的权限");
		}
	}
/**
 * @author shubeibei
 * @return 得到开课id。
 */
	public String getOpenCourseId(String semesterId, String courseId)
			throws PlatformException {
		if (basicManagePriv.getOpenCoursesBySemester == 1) {
			OracleOpenCourseActivity opencourse = new OracleOpenCourseActivity();
			return opencourse.getOpenCourseId(semesterId, courseId);
		} else {
			throw new PlatformException("您没有查询开课的权限");
		}
	}

	public List getTeacherInteractionStatistic(Page page, String semesterId,
			String orderBy, String order) throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getTeacherInteractionStatistic(page,
					semesterId, orderBy, order);
		} else {
			throw new PlatformException("您没有查询教师的权限");
		}
	}
	
	public List getTeacherInteractionStatistic(Page page, String semesterId,
			String orderBy, String order,String dep_id) throws PlatformException {
		if (basicManagePriv.getTeacher == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getTeacherInteractionStatistic(page,
					semesterId, orderBy, order,dep_id);
		} else {
			throw new PlatformException("您没有查询教师的权限");
		}
	}

	public int deleteTeachersToTeachClass(String[] teacherIdSet,
			String teachclass_id) throws PlatformException {
		if (basicManagePriv.appointTeacherForCourse == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachclass_id);
			int suc = teachclass.removeTeachersFromTeachClass(teacherIdSet);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTeachersToTeachClass</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有给课程指定教师的权限");
		}
	}

	public int deleteTeachBookToCourse(String[] teachbookIdSet, String courseId)
			throws PlatformException {
		if (basicManagePriv.appointTeachbookForCourse == 1) {
			OracleCourse course = new OracleCourse();
			course.setId(courseId);

			int suc = course.removeTeachBookFromCourse(teachbookIdSet);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTeachBookToCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有给课程指定教材的权限");
		}
	}

	public int deleteCourseToTeachBook(String[] courseIdSet, String bookId)
			throws PlatformException {
		if (basicManagePriv.appointCourseForTeachbook == 1) {
			OracleTeachBook book = new OracleTeachBook();
			book.setId(bookId);

			int suc = book.removeCourseFromTeachBook(courseIdSet);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteCourseToTeachBook</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有给教材指定课程的权限");
		}
	}

	public List getActiveTeachplanCourseList(Page page, String semesterId,
			String gradeId, String eduTypeId, String majorId)
			throws PlatformException {
		if (basicManagePriv.getTeachPlanCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getActiveTeachplanCourseList(page,
					semesterId, gradeId, eduTypeId, majorId);
		} else {
			throw new PlatformException("您没查看教学计划课程的权限");
		}
	}

	public int getActiveTeachplanCoursesNum(String semesterId, String gradeId,
			String eduTypeId, String majorId) throws PlatformException {
		if (basicManagePriv.getTeachPlanCourse == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			return basicActivityList.getActiveTeachplanCoursesNum(semesterId,
					gradeId, eduTypeId, majorId);
		} else {
			throw new PlatformException("您没查看教学计划课程的权限");
		}
	}

	public int addExecutePlan(String title, String semesterId,
			String teachPlanId) throws PlatformException {
		if (basicManagePriv.addExecutePlan == 1) {
			OracleExecutePlan plan = new OracleExecutePlan();
			plan.setTitle(title);
			plan.setSemester(new OracleSemester(semesterId));
			plan.setPlan(new OracleTeachPlan(teachPlanId));

			if (plan.isExist() > 0) {
				return -1;
			} else {
				int suc = plan.add();
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.basicManagePriv.getSso_id()
										+ "</whaty><whaty>BEHAVIOR$|$addExecutePlan</whaty><whaty>STATUS$|$"
										+ suc
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.MANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
				return suc;
			}
		} else {
			throw new PlatformException("您没有添加执行计划的权限");
		}
	}

	public int deleteExecutePlan(String id) throws PlatformException {
		if (basicManagePriv.deleteExecutePlan == 1) {
			OracleExecutePlan plan = new OracleExecutePlan(id);
			int suc = plan.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteExecutePlan</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除执行计划的权限");
		}
	}

	public ExecutePlan getExecutePlan(String planId) throws PlatformException {
		if (basicManagePriv.addExecutePlan == 1) {
			OracleExecutePlan plan = new OracleExecutePlan(planId);

			return plan;
		} else {
			throw new PlatformException("您没有查看执行计划的权限");
		}
	}

	public ExecutePlan getExecutePlan(String semesterId, String teachPlanId)
			throws PlatformException {
		//System.out.println("aaaaa"+semesterId+teachPlanId);
		if (basicManagePriv.getExecutePlan == 1) {
			OracleExecutePlan plan = new OracleExecutePlan(semesterId,
					teachPlanId);

			return plan;
		} else {
			throw new PlatformException("您没有查看执行计划的权限");
		}
	}

	public List getExecutePlans(Page page, String id, String title,
			String semesterId, String teachPlanId) throws PlatformException {
		if (basicManagePriv.getExecutePlan == 1) {
			List searchList = new ArrayList();
			OracleBasicActivityList activityList = new OracleBasicActivityList();

			if (id != null && !"".equals(id) && !"null".equalsIgnoreCase(id))
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null && !"".equals(title)
					&& !"null".equalsIgnoreCase(title))
				searchList.add(new SearchProperty("title", title, "like"));
			if (semesterId != null && !"".equals(semesterId)
					&& !"null".equalsIgnoreCase(semesterId))
				searchList.add(new SearchProperty("semester_id", semesterId,
						"="));
			if (teachPlanId != null && !"".equals(teachPlanId)
					&& !"null".equalsIgnoreCase(teachPlanId))
				searchList.add(new SearchProperty("teachplan_id", teachPlanId,
						"="));

			return activityList.getExecutePlans(page, searchList, null);
		} else {
			throw new PlatformException("您没有查看执行计划的权限");
		}
	}

	public int getExecutePlansNum(String id, String title, String semesterId,
			String teachPlanId) throws PlatformException {
		if (basicManagePriv.getExecutePlan == 1) {
			List searchList = new ArrayList();
			OracleBasicActivityList activityList = new OracleBasicActivityList();

			if (id != null && !"".equals(id) && !"null".equalsIgnoreCase(id))
				searchList.add(new SearchProperty("id", id, "="));
			if (title != null && !"".equals(title)
					&& !"null".equalsIgnoreCase(title))
				searchList.add(new SearchProperty("title", title, "like"));
			if (semesterId != null && !"".equals(semesterId)
					&& !"null".equalsIgnoreCase(semesterId))
				searchList.add(new SearchProperty("semester_id", semesterId,
						"="));
			if (teachPlanId != null && !"".equals(teachPlanId)
					&& !"null".equalsIgnoreCase(teachPlanId))
				searchList.add(new SearchProperty("teachplan_id", teachPlanId,
						"="));

			return activityList.getExecutePlansNum(searchList);
		} else {
			throw new PlatformException("您没有查看执行计划的权限");
		}
	}

	public int updateExecutePlan(String id, String title, String semesterId,
			String teachPlanId) throws PlatformException {
		if (basicManagePriv.updateExecutePlan == 1) {
			OracleExecutePlan plan = new OracleExecutePlan(id);
			int suc = plan.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateExecutePlan</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有修改执行计划的权限");
		}
	}

	public int updateExecutePlanCourseExamSeq(String id, String esId)
			throws PlatformException {
		if (basicManagePriv.updateExecutePlan == 1) {
			OracleExecutePlanCourse exeCourse = new OracleExecutePlanCourse(id);
			OracleBasicSequence esq = new OracleBasicSequence(esId);
			exeCourse.setSequence(esq);
			return exeCourse.update();
		} else {
			throw new PlatformException("您没有修改执行计划的权限");
		}
	}

	public int updateExecutePlanCourseExamSeq(HttpServletRequest request)
			throws PlatformException {
		if (basicManagePriv.updateExecutePlan == 1) {
			OracleExecutePlanCourse exeCourse = new OracleExecutePlanCourse(
					request.getParameter("id"));
			OracleBasicSequence esq = new OracleBasicSequence(request
					.getParameter("esId"));
			exeCourse.setSequence(esq);
			exeCourse.setExamType(request.getParameter("examType"));
			return exeCourse.update();
		} else {
			throw new PlatformException("您没有修改执行计划的权限");
		}
	}

	public int addExecutePlanCourseGroup(String title, String executePlanId,
			String type, int max, int min) throws PlatformException {
		if (basicManagePriv.addExecutePlanCourseGroup == 1) {
			OracleExecutePlanCourseGroup group = new OracleExecutePlanCourseGroup();
			group.setTitle(title);
			group.setExecutePlan(new OracleExecutePlan(executePlanId));
			group.setType(type);
			group.setMax(max);
			group.setMin(min);
			int suc = group.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addExecutePlanCourseGroup</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加执行计划课程组的权限");
		}
	}

	public List getExecutePlanCourseGroupsByExecutePlanId(String executePlanId)
			throws PlatformException {
		if (basicManagePriv.getExecutePlanCourseGroup == 1) {
			List searchList = new ArrayList();
			OracleBasicActivityList activityList = new OracleBasicActivityList();

			if (executePlanId != null && !"".equals(executePlanId)
					&& !"null".equalsIgnoreCase(executePlanId))
				searchList.add(new SearchProperty("executeplan_id",
						executePlanId, "="));

			return activityList.getExecutePlanCourseGroups(null, searchList,
					null);
		} else {
			throw new PlatformException("您没有查看执行计划课程组的权限");
		}
	}

	public int addExecutePlanCourse(String groupId, String teachPlanCourseId,
			String esId) throws PlatformException {
		if (basicManagePriv.addExecutePlanCourse == 1) {
			OracleExecutePlanCourse course = new OracleExecutePlanCourse();
			OracleExecutePlanCourseGroup group = new OracleExecutePlanCourseGroup();
			group.setId(groupId);
			course.setGroup(group);
			OracleTeachPlanCourse tcourse = new OracleTeachPlanCourse();
			tcourse.setId(teachPlanCourseId);
			course.setCourse(tcourse);
			OracleBasicSequence es = new OracleBasicSequence();
			es.setId(esId);
			course.setSequence(es);
			int suc = course.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addExecutePlanCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有添加执行计划课程的权限");
		}
	}

	public int deleteExecutePlanCourse(String id) throws PlatformException {
		if (basicManagePriv.deleteExecutePlanCourse == 1) {
			OracleExecutePlanCourse course = new OracleExecutePlanCourse(id);

			int suc = course.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteExecutePlanCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有删除执行计划课程的权限");
		}
	}

	public ExecutePlanCourse getExecutePlanCourse(String id)
			throws PlatformException {
		if (basicManagePriv.getExecutePlanCourse == 1) {
			OracleExecutePlanCourse course = new OracleExecutePlanCourse(id);

			return course;
		} else {
			throw new PlatformException("您没有查看执行计划课程的权限");
		}
	}

	public List getExecutePlanCourses(Page page, String groupId,
			String teachPlanCourseId) throws PlatformException {
		if (basicManagePriv.getExecutePlanCourse == 1) {
			OracleBasicActivityList activityList = new OracleBasicActivityList();
			List searchList = new ArrayList();
			if (groupId != null && !"".equals(groupId)
					&& !"null".equalsIgnoreCase(groupId))
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (teachPlanCourseId != null && !"".equals(teachPlanCourseId)
					&& !"null".equalsIgnoreCase(teachPlanCourseId))
				searchList.add(new SearchProperty("teachplan_course_id",
						teachPlanCourseId, "="));

			return activityList.getExecutePlanCourses(page, searchList, null);
		} else {
			throw new PlatformException("您没有查看执行计划课程的权限");
		}
	}

	public int getExecutePlanCoursesNum(String id, String groupId,
			String teachPlanCourseId) throws PlatformException {
		if (basicManagePriv.getExecutePlanCourse == 1) {
			OracleBasicActivityList activityList = new OracleBasicActivityList();
			List searchList = new ArrayList();
			if (groupId != null && !"".equals(groupId)
					&& !"null".equalsIgnoreCase(groupId))
				searchList.add(new SearchProperty("group_id", groupId, "="));
			if (teachPlanCourseId != null && !"".equals(teachPlanCourseId)
					&& !"null".equalsIgnoreCase(teachPlanCourseId))
				searchList.add(new SearchProperty("teachplan_course_id",
						teachPlanCourseId, "="));

			return activityList.getExecutePlanCoursesNum(searchList);
		} else {
			throw new PlatformException("您没有查看执行计划课程的权限");
		}
	}

	public List getExecutePlanCoursesByExecutePlanId(Page page,
			String executePlanId) throws PlatformException {
		if (basicManagePriv.getExecutePlanCourse == 1) {
			OracleBasicActivityList activityList = new OracleBasicActivityList();

			return activityList.getExecutePlanCoursesByExecutePlanId(page,
					executePlanId);
		} else {
			throw new PlatformException("您没有查看执行计划课程的权限");
		}
	}

	public int getExecutePlanCoursesNumByExecutePlanId(String executePlanId)
			throws PlatformException {
		if (basicManagePriv.getExecutePlanCourse == 1) {
			OracleBasicActivityList activityList = new OracleBasicActivityList();

			return activityList
					.getExecutePlanCoursesNumByExecutePlanId(executePlanId);
		} else {
			throw new PlatformException("您没有查看执行计划课程的权限");
		}
	}

	public int getToBeSelectedTeachPlanCoursesNumOfExecutePlan(
			String executePlanId) throws PlatformException {
		if (basicManagePriv.getTeachPlanCourse == 1) {
			OracleBasicActivityList activityList = new OracleBasicActivityList();

			return activityList
					.getToBeSelectedTeachPlanCoursesNumOfExecutePlan(executePlanId);
		} else {
			throw new PlatformException("您没有查看教学计划课程的权限");
		}
	}

	public List getToBeSelectedTeachPlanCoursesOfExecutePlan(Page page,
			String executePlanId) throws PlatformException {
		if (basicManagePriv.getTeachPlanCourse == 1) {
			OracleBasicActivityList activityList = new OracleBasicActivityList();

			return activityList.getToBeSelectedTeachPlanCoursesOfExecutePlan(
					page, executePlanId);
		} else {
			throw new PlatformException("您没有查看教学计划课程的权限");
		}
	}

	public TeachPlanCourse getTeachPlanCourse(String id)
			throws PlatformException {
		if (basicManagePriv.getTeachPlanCourse == 1) {
			OracleTeachPlanCourse course = new OracleTeachPlanCourse(id);

			return course;
		} else {
			throw new PlatformException("您没有查看教学计划课程的权限");
		}
	}

	public int deleteExecutePlanCourses(String[] ids) throws PlatformException {
		if (basicManagePriv.deleteExecutePlanCourse == 1) {
			OracleBasicActivityList activityList = new OracleBasicActivityList();

			return activityList.deleteExecutePlanCourses(ids);
		} else {
			throw new PlatformException("您没有删除执行计划课程的权限");
		}
	}

	public int getElectiveStatNum(String teachclass_id, String site_id)
			throws PlatformException {
		if (basicManagePriv.electiveStudent == 1) {
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
			throw new PlatformException("您没有教学站批量选课的权限");
		}
	}

	public List getElectiveStat(Page page, String teachclass_id, String site_id)
			throws PlatformException {
		if (basicManagePriv.electiveStudent == 1) {
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
			throw new PlatformException("您没有浏览选课学生的权限");
		}
	}

	public List getActiveExamSequences() throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleExamList list = new OracleExamList();
			ArrayList orderList = new ArrayList();
			orderList.add(new OrderProperty("id"));
			return list.getActiveExamSequences(null, null, orderList);
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}

	public int getChangeMajorApplysNum() throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();
			searchProperties.add(new SearchProperty("status", "0", "<>"));

			return basicActivityList.getChangeMajorApplysNum(searchProperties);
		} else {
			throw new PlatformException("您没有学籍异动的权限");
		}
	}

	public List getChangeMajorApplys(Page page) throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();
			List orderProperties = new ArrayList();
			searchProperties.add(new SearchProperty("status", "0", "<>"));
			orderProperties.add(new OrderProperty("apply_date",
					OrderProperty.DESC));
			return basicActivityList.getChangeMajorApplys(page,
					searchProperties, orderProperties);
		} else {
			throw new PlatformException("您没有学籍异动的权限");
		}
	}

	public int changeMajorApplyStatus(String id, String status)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			OracleChangeMajorApply apply = new OracleChangeMajorApply(id);
			apply.setId(id);
			return apply.changeStauts(status);
		} else {
			throw new PlatformException("您没有学籍异动的权限");
		}
	}

	public int confirmMajorApplyStatus(String[] id) throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			int count = 0;
			if (id != null) {
				for (int i = 0; i < id.length; i++) {
					OracleChangeMajorApply apply = new OracleChangeMajorApply(
							id[i]);
					apply.setId(id[i]);
					count += apply.changeStauts("2");
				}
			}
			return count;
		} else {
			throw new PlatformException("您没有学籍异动的权限");
		}
	}

	public int rejectMajorApplyStatus(String[] id, String[] notes)
			throws PlatformException {
		if (basicManagePriv.changeStatus == 1) {
			int count = 0;
			if (id != null) {
				for (int i = 0; i < id.length; i++) {
					OracleChangeMajorApply apply = new OracleChangeMajorApply(
							id[i]);
					apply.setId(id[i]);
					count += apply.reject(notes[i]);
				}
			}
			return count;
		} else {
			throw new PlatformException("您没有学籍异动的权限");
		}
	}

	public int getTeachClassStudentsNum(String teachClassId, String stuRegNo,
			String stuName) throws PlatformException {
		if (basicManagePriv.getTeachClass == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachClassId);
			return teachclass.getStudentsNum(stuRegNo, stuName,teachClassId);
		} else {
			throw new PlatformException("您没有查询培训班的权限");
		}

	}
	
	public int getTeachClassStudentsNum(String teachClassId, String stuRegNo,
			String stuName,String siteId) throws PlatformException {
		if (basicManagePriv.getTeachClass == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachClassId);
			return teachclass.getStudentsNum(stuRegNo, stuName,siteId);
		} else {
			throw new PlatformException("您没有查询培训班的权限");
		}

	}

	public List getTeachClassStudents(Page page, String teachClassId,
			String stuRegNo, String stuName) throws PlatformException {
		if (basicManagePriv.getTeachClass == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachClassId);
			return teachclass.getStudents(page, stuRegNo, stuName,teachClassId);
		} else {
			throw new PlatformException("您没有查询培训班的权限");
		}
	}
	
	public List getTeachClassStudents(Page page, String teachClassId,
			String stuRegNo, String stuName,String siteId) throws PlatformException {
		if (basicManagePriv.getTeachClass == 1) {
			OracleTeachClass teachclass = new OracleTeachClass();
			teachclass.setId(teachClassId);
			return teachclass.getStudents(page, stuRegNo, stuName,siteId);
		} else {
			throw new PlatformException("您没有查询培训班的权限");
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
			throw new PlatformException("您没有查询学期开课的权限");
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
			throw new PlatformException("您没有教学站批量选课的权限");
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
			throw new PlatformException("您没有浏览选课学生的权限");
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

	public List getElectives1(Page page, String teachclass_id, String site_id,
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

	public List getCourseWareByTeachClass(String teachclass_id) throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
//			return basicActivityList.getCourseWaresByTeachClass(teachclass_id);
			return null;
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}


	public List getBooksByTeachClass(String teachclass_id) throws PlatformException {
		if (basicManagePriv.openCourseBySemester == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
//			return basicActivityList.getBooksByTeachClass(teachclass_id);
			return null;
		} else {
			throw new PlatformException("您没有学期开课的权限");
		}
	}
	/**
	 * yelina 2008.6.12
	 * @param searchProperties //作为教材的搜索条件
	 * @param teachclass_id
	 * @return
	 */
	public int  getBooksByTeachClassNum(String teachclass_id,String bookId,String teachbook_name) throws PlatformException {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();

			if (teachbook_name != null && !teachbook_name.trim().equals("")
					&& !teachbook_name.equals("null")) {
				searchProperties.add(new SearchProperty("teachbook_name", teachbook_name,
						"like"));
			}
			if (bookId != null && !bookId.trim().equals("")
					&& !bookId.equals("null")) {
				searchProperties.add(new SearchProperty("t1.id", bookId,
						"like"));
			}
//			return basicActivityList.getBooksByTeachClassNum(searchProperties,teachclass_id);
			return 0;
	}

	
	/***
	 * yelina 2008.6.12
	 * @param page
	 * @param searchProperties //作为教材的搜索条件
	 * @param teachclass_id
	 * @return
	 */
	public List getBooksByTeachClass(Page page, String teachclass_id,String bookId,String coursename) throws PlatformException {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();

			if (coursename != null && !coursename.trim().equals("")
					&& !coursename.equals("null")) {
				searchProperties.add(new SearchProperty("teachbook_name", coursename,
						"like"));
			}
			if (bookId != null && !bookId.trim().equals("")
					&& !bookId.equals("null")) {
				searchProperties.add(new SearchProperty("t1.id", bookId,
						"like"));
			}
//			return basicActivityList.getBooksByTeachClass(page,searchProperties,teachclass_id);
			return null;
	}
	public int addBookToCourse(String courseIdSet, String[] bookId) throws PlatformException {
		if (basicManagePriv.appointCourseForTeachbook == 1) {
			OracleCourse course = new OracleCourse();
			course.setId(courseIdSet);
//			int suc = course.addBookToCourse(bookId);
			int suc =0 ;
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addCourseToTeachBook</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有给教材指定课程的权限");
		}
	}

	
	public int deleteCourseBookById(String courseId,String[] ids) throws PlatformException {
		
		
			OracleCourse course = new OracleCourse(courseId);
//			int suc = course. removeBooksFromCourse(courseId,ids);
			int suc = 0;
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$deleteTeachersToTeachClass</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		
	}

	public int deleteCourseWareById(String courseId, String[] ids) throws PlatformException {

		OracleCourse course = new OracleCourse(courseId);
//		int suc = course.removeCourseWareFromCourse(courseId,ids);
		int suc = 0;
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$deleteTeachersToTeachClass</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	
	}

	
	public int addCourseWareToCourse(String courseIdSet, String[] courseWareId) throws PlatformException {
		OracleCourse course = new OracleCourse();
		course.setId(courseIdSet);
//		int suc = course.addCourseWareToCourse(courseWareId);
		int suc = 0;
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.basicManagePriv.getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$addCourseToTeachBook</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int updateTeachPlanCourse(String id, String credit, String courseTime, String type) throws PlatformException {
		if (basicManagePriv.updateTeachPlanCourse == 1) {
			OracleTeachPlanCourse teachPlanCourse = new OracleTeachPlanCourse();
			teachPlanCourse.setId(id);
			teachPlanCourse.setCredit(Float.parseFloat(credit));
			teachPlanCourse.setCoursetime(Float.parseFloat(courseTime));
			teachPlanCourse.setType(type);
			int suc = teachPlanCourse.update();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$updateTeachPlanCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("您没有编辑教学计划课程权限");
		}
	}

	public int addExelTeachPlans(List sqlList) throws PlatformException {
//		return new OracleTeachPlanCourse().addExelTeachPlans(sqlList);
		return 0;
	}

	
	public int unelectiveCoursesByUserId(String[] idSet,String[] str, String semester_id, String student_id) throws PlatformException {
		int i = 0;
		if (basicManagePriv.electiveBatchByStudent == 1) {
			OracleElectiveActivity electiveActivity = new OracleElectiveActivity();
//			i = electiveActivity.unelectiveCoursesByUserId(idSet,str, semester_id,
//					student_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$electiveCoursesByUserId</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new PlatformException("您没有为学生批量选课的权限");
		}
		return i;
		
	}

	
	public int getCourseWaresByTeachClass(String courseWareID, String courseWareName, String teachClassId, String major_id) throws PlatformException {
		if (basicManagePriv.appointCourseForTeachbook == 1) {
			OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
			List searchProperties = new ArrayList();

			if (courseWareName != null && !courseWareName.trim().equals("")
					&& !courseWareName.equals("null")) {
				searchProperties.add(new SearchProperty("name", courseWareName,
						"like"));
			}
			if (courseWareID != null && !courseWareID.trim().equals("")
					&& !courseWareID.equals("null")) {
				searchProperties.add(new SearchProperty("courseware_id", courseWareID,
						"like"));
			}
			if (major_id != null && !major_id.trim().equals("")
					&& !major_id.equals("null")) {
				searchProperties.add(new SearchProperty("major_id", major_id,
						"="));
			}
			searchProperties
					.add(new SearchProperty("courseware_id", courseWareID, "="));
			return basicActivityList.getCourseNumByTeachBook(searchProperties);
		} else {
			throw new PlatformException("您没有给教材指定课程的权限");
		}
	}

	public int delTeachPlanCourseByTeachPlan(String TeachPlanId) throws PlatformException {
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.basicManagePriv.getSso_id()
						+ "</whaty><whaty>BEHAVIOR$|delTeachPlanCourseByTeachPlan</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
//		return new OracleTeachPlanCourse().deleteTeachePlanCoursesByTeachPlan(TeachPlanId);
		return 0;
	}

	/**
	 * @author yelina
	 * @param teachPlanId
	 * @see 通过年级，专业，层次等基本信息导入教学计划课程
	 * */
		public int addTeachPlanAndCourse(String major_id, String edu_type_id, String grade_id,String course_id,String credit,
				String courseTime,String sjcoursetime, String type,String semester) throws PlatformException {
//			int suc=new OracleTeachPlanCourse().addTeachPlanAndCourse(major_id, edu_type_id, grade_id, course_id, credit, courseTime, sjcoursetime, type, semester);
			int suc = 0;
			UserLog.setInfo(
								"<whaty>USERID$|$"
										+ this.basicManagePriv.getSso_id()
										+ "</whaty><whaty>BEHAVIOR$|$addTeachPlanAndCourse</whaty><whaty>STATUS$|$"
										+ suc
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.MANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>", new Date());
				return suc;
		}

		/**
		 * @author yelina
		 * @param teachPlanId
		 * @see 通过教学计划ID导入教学计划课程
		 * */
				
	public int addTeachPlanCourse(String teachPlanId, String course_id, String credit, String courseTime, String sjcoursetime, String type, String semester) throws PlatformException {
//		int suc=new OracleTeachPlanCourse().addTeachPlanCourse(teachPlanId, course_id, credit, courseTime, sjcoursetime, type, semester);
		int suc = 0;
		UserLog.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$addTeachPlanCourse</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
	}

		
	/**
	 * @author 黄海平
	 */
	public int getGraduatedStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id,String kaoqu_id,String shenfen_id,String xuewei) throws PlatformException {
		if (basicManagePriv.getGraduatedStudent == 1) {
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
			if (kaoqu_id != null && !kaoqu_id.equals("")) {
				searchProperties.add(new SearchProperty("u.kaoqu_id", kaoqu_id,
						"="));
			}
			if (shenfen_id != null && !shenfen_id.equals("")) {
				searchProperties.add(new SearchProperty("u.shenfen_id", shenfen_id,
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
			if("xuewei".equals(xuewei)){
				searchProperties.add(new SearchProperty("u.isdegreed", "1", "="));
			}
			OracleBasicUserList studentList = new OracleBasicUserList();
			return studentList.getAllStudentsNum(searchProperties);

		} else {
			throw new PlatformException("您没有浏览毕业学生的权限");
		}
	}

}
