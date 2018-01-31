/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.training.activity.OracleTrainingSelectClassActivity;
import com.whaty.platform.database.oracle.standard.training.activity.OracleTrainingSelectCourseActivity;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingBasicList;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingClass;
import com.whaty.platform.database.oracle.standard.training.basic.OracleTrainingCourse;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudent;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingUserList;
import com.whaty.platform.training.TrainingClassManage;
import com.whaty.platform.training.basic.StudentClassStatus;
import com.whaty.platform.training.basic.TrainingClass;
import com.whaty.platform.training.basic.TrainingCourse;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.TrainingLog;

/**
 * @author wq
 * 
 */
public class OracleTrainingClassManage extends TrainingClassManage {

	public void addTrainingClassCourse(String classId, List courseIdList)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		trainingClass.addTrainingCourse(courseIdList);
	}

	public void removeTrainingClassCourse(String classId, List courseIdList)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		trainingClass.removeTrainingCourse(courseIdList);
	}

	public void addTrainingClassStudent(String classId, List studentIdList)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		trainingClass.addTrainingStudent(studentIdList);
	}

	public void removeTrainingClassStudent(String classId, List studentIdList)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		trainingClass.removeTrainingStudent(studentIdList);
	}

	public void checkTrainingClassStudent(String classId, List studentIdList)
			throws PlatformException {
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		trainingClass.checkTrainingStudent(studentIdList);
	}

	public List getTrainingClass() throws PlatformException {
		String classManagerId = getTrainingClassManagerPriv()
				.getClassManagerId();
		dbpool db = new dbpool();
		String sql = "select id, name, status, startdate, enddate, start_electdate, end_electdate, note "
				+ "from training_class where id in (select class_id from training_class_chief where chief_id='"
				+ classManagerId + "')";
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList classList = null;
		try {
			db = new dbpool();
			classList = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				TrainingClass trainingClass = new OracleTrainingClass();
				trainingClass.setId(rs.getString("id"));
				trainingClass.setName(rs.getString("name"));
				trainingClass.setStartDate(rs.getDate("startdate"));
				trainingClass.setEndDate(rs.getDate("enddate"));
				trainingClass
						.setStartElectCourse(rs.getDate("start_electdate"));
				trainingClass.setEndElectCourse(rs.getDate("end_electdate"));

				classList.add(trainingClass);
			}
		} catch (Exception e) {
			throw new PlatformException("error in getTrainingClass()");
		} finally {
			db.close(rs);
			db = null;

		}
		return classList;
	}

	public List getTrainingClassCourse(String classId) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);

		return basicList.searchClassCourses(null, null, null, trainingClass);
	}

	public List getTrainingClassCourse(String classId, String field,
			String order) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));

		return basicList.searchClassCourses(null, null, orderList,
				trainingClass);
	}

	public List getTrainingClassCourse(String courseId, String courseName,
			String courseTypeId, String classId) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		List searchList = new ArrayList();
		if (courseId != null)
			searchList.add(new SearchProperty("course_id", courseId, "like"));
		if (courseName != null)
			searchList
					.add(new SearchProperty("course_name", courseName, "like"));
		if (courseTypeId != null)
			searchList.add(new SearchProperty("course_typeid", courseTypeId,
					"like"));

		return basicList.searchClassCourses(null, searchList, null,
				trainingClass);
	}

	public List getTrainingClassCourse(String courseId, String courseName,
			String courseTypeId, String classId, String field, String order)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		List searchList = new ArrayList();
		if (courseId != null)
			searchList.add(new SearchProperty("course_id", courseId, "like"));
		if (courseName != null)
			searchList
					.add(new SearchProperty("course_name", courseName, "like"));
		if (courseTypeId != null)
			searchList.add(new SearchProperty("course_typeid", courseTypeId,
					"like"));

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));

		return basicList.searchClassCourses(null, searchList, orderList,
				trainingClass);
	}

	public int getTrainingCourseNum(String courseId, String courseName,
			String courseTypeId) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List searchList = new ArrayList();
		if (courseId != null)
			searchList.add(new SearchProperty("course_id", courseId, "like"));
		if (courseName != null)
			searchList
					.add(new SearchProperty("course_name", courseName, "like"));
		if (courseTypeId != null)
			searchList.add(new SearchProperty("course_typeid", courseTypeId,
					"="));

		// return basicList.searchCourse(page, searchList, null);
		return basicList.searchCourseNum(searchList, null);
	}

	public List getTrainingCourse(Page page, String courseId,
			String courseName, String courseTypeId) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List searchList = new ArrayList();
		if (courseId != null)
			searchList.add(new SearchProperty("course_id", courseId, "like"));
		if (courseName != null)
			searchList
					.add(new SearchProperty("course_name", courseName, "like"));
		if (courseTypeId != null)
			searchList.add(new SearchProperty("course_typeid", courseTypeId,
					"="));

		return basicList.searchCourse(page, searchList, null);
	}

	public List getTrainingCourse(Page page, String courseId,
			String courseName, String courseTypeId, String field, String order)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List searchList = new ArrayList();
		if (courseId != null)
			searchList.add(new SearchProperty("course_id", courseId, "like"));
		if (courseName != null)
			searchList
					.add(new SearchProperty("course_name", courseName, "like"));
		if (courseTypeId != null)
			searchList.add(new SearchProperty("course_typeid", courseTypeId,
					"="));

		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));

		return basicList.searchCourse(page, searchList, orderList);
	}

	public List getTrainingClassStudent(Page page, String studentId,
			String studentName, String classId) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		List searchList = new ArrayList();
		searchList.add(new SearchProperty("status",
				StudentClassStatus.SELECTED, "="));
		if (studentId != null)
			searchList.add(new SearchProperty("student_id", studentId, "like"));
		if (studentName != null)
			searchList.add(new SearchProperty("student_name", studentName,
					"like"));

		return basicList.searchClassStudents(page, searchList, null,
				trainingClass);
	}

	public List getTrainingClassStudent(Page page, String studentId,
			String studentName, String field, String order, String classId)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		List searchList = new ArrayList();
		List orderList = new ArrayList();

		searchList.add(new SearchProperty("status",
				StudentClassStatus.SELECTED, "="));
		orderList.add(new OrderProperty(field, order));

		if (studentId != null)
			searchList.add(new SearchProperty("student_id", studentId, "like"));
		if (studentName != null)
			searchList.add(new SearchProperty("student_name", studentName,
					"like"));

		return basicList.searchClassStudents(page, searchList, orderList,
				trainingClass);
	}

	public int getTrainingClassStudentNum(String studentId, String studentName,
			String classId) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		List searchList = new ArrayList();
		searchList.add(new SearchProperty("status",
				StudentClassStatus.SELECTED, "="));
		if (studentId != null)
			searchList.add(new SearchProperty("student_id", studentId, "like"));
		if (studentName != null)
			searchList.add(new SearchProperty("student_name", studentName,
					"like"));

		return basicList.searchClassStudentsNum(searchList, trainingClass);
	}

	public List getTrainingStudent(Page page, String studentId,
			String studentName, String nickName, String active)
			throws PlatformException {
		OracleTrainingUserList userList = new OracleTrainingUserList();
		List searchList = new ArrayList();

		if (studentId != null)
			searchList.add(new SearchProperty("id", studentId, "like"));
		if (studentName != null)
			searchList.add(new SearchProperty("name", studentName, "like"));
		if (nickName != null)
			searchList.add(new SearchProperty("nick_name", nickName, "like"));
		if (active != null && !active.equals(""))
			searchList.add(new SearchProperty("status", active, "="));

		return userList.searchTrainingStudents(page, searchList, null);
	}

	public List getTrainingStudent(Page page, String studentId,
			String studentName, String nickName, String active, String field,
			String order) throws PlatformException {
		OracleTrainingUserList userList = new OracleTrainingUserList();
		List searchList = new ArrayList();
		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));

		if (studentId != null)
			searchList.add(new SearchProperty("id", studentId, "like"));
		if (studentName != null)
			searchList.add(new SearchProperty("name", studentName, "like"));
		if (nickName != null)
			searchList.add(new SearchProperty("nick_name", nickName, "like"));
		if (active != null && !active.equals(""))
			searchList.add(new SearchProperty("status", active, "="));

		return userList.searchTrainingStudents(page, searchList, orderList);
	}

	public int getTrainingStudentNum(String studentId, String studentName,
			String nickName, String active) throws PlatformException {
		OracleTrainingUserList userList = new OracleTrainingUserList();
		List searchList = new ArrayList();
		if (studentId != null)
			searchList.add(new SearchProperty("id", studentId, "like"));
		if (studentName != null)
			searchList.add(new SearchProperty("name", studentName, "like"));
		if (nickName != null)
			searchList.add(new SearchProperty("nick_name", nickName, "like"));
		if (active != null && !active.equals(""))
			searchList.add(new SearchProperty("status", active, "="));

		return userList.getTrainingStudentsNum(searchList, null);
	}

	public List getCheckedStudents(String classId) throws PlatformException {

		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		return trainingClass.getTrainingStudents();
	}

	public int getUnCheckedClassStudentsNum(String classId)
			throws PlatformException {
		List searchList = new ArrayList();
		searchList.add(new SearchProperty("status",
				StudentClassStatus.UNDERCHECK, "="));

		OracleTrainingBasicList basicList = new OracleTrainingBasicList();

		return basicList.searchClassStudentsNum(searchList,
				new OracleTrainingClass(classId));
	}

	public List getUnCheckedClassStudents(Page page, String classId)
			throws PlatformException {
		List searchList = new ArrayList();
		searchList.add(new SearchProperty("status",
				StudentClassStatus.UNDERCHECK, "="));

		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchClassStudents(page, searchList, null,
				new OracleTrainingClass(classId));
	}

	public List getUnCheckedClassStudents(Page page, String field,
			String order, String classId) throws PlatformException {
		List searchList = new ArrayList();
		List orderList = new ArrayList();
		orderList.add(new OrderProperty(field, order));
		searchList.add(new SearchProperty("status",
				StudentClassStatus.UNDERCHECK, "="));

		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchClassStudents(page, searchList, orderList,
				new OracleTrainingClass(classId));
	}

	public TrainingStudent getTrainingStudent(String studentId)
			throws PlatformException {
		OracleTrainingStudent student = new OracleTrainingStudent(studentId);
		return student;
	}

	public TrainingCourse getTrainingCourse(String courseId)
			throws PlatformException {
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		return course;
	}

	public int getClassCourseStudentsNum(String id, String name, String note,
			String status, String classId, String courseId)
			throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);

		List searchList = new ArrayList();

		if (id != null)
			searchList.add(new SearchProperty("student_id", id, "like"));
		if (name != null)
			searchList.add(new SearchProperty("student_name", name, "like"));
		if (note != null)
			searchList.add(new SearchProperty("student_note", note, "like"));
		if (status != null)
			searchList.add(new SearchProperty("studentcourse_status", status,
					"="));

		// return basicList.searchCourseStudentsNum(searchList, course);
		return basicList.searchClassCourseStudentsNum(searchList, null,
				trainingClass, course);
	}

	public List getClassCourseStudents(Page page, String id, String name,
			String note, String field, String order, String status,
			String classId, String courseId) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		OracleTrainingCourse course = new OracleTrainingCourse(courseId);
		OracleTrainingClass trainingClass = new OracleTrainingClass(classId);
		/*
		 * SearchProperty search = new SearchProperty("studentcourse_status",
		 * StudentCourseStatus.SELECTED);
		 */
		List searchList = new ArrayList();

		if (id != null)
			searchList.add(new SearchProperty("student_id", id, "like"));
		if (name != null)
			searchList.add(new SearchProperty("student_name", name, "like"));
		if (note != null)
			searchList.add(new SearchProperty("student_note", note, "like"));
		if (status != null)
			searchList.add(new SearchProperty("studentcourse_status", status,
					"="));

		List orderList = new ArrayList();
		if (field != null)
			orderList.add(new OrderProperty(field, order));

		return basicList.searchClassCourseStudents(page, searchList, orderList,
				trainingClass, course);
	}

	public void unApplyCourseStudent(String courseId, List studentList) throws PlatformException {
		OracleTrainingSelectCourseActivity activity = new OracleTrainingSelectCourseActivity();
		Map map = new HashMap();

		map.put(courseId, studentList);

		activity.unSelectCourses(map);
	}

	public void checkCourseStudent(List studentIds, String courseId) throws PlatformException {
		Map studentCourses = new HashMap();
		studentCourses.put(courseId, studentIds);
		OracleTrainingSelectCourseActivity activity = new OracleTrainingSelectCourseActivity();
		activity.checkSelectCourses(studentCourses);
	}

	public void unCheckCourseStudent(List studentIds, String courseId) throws PlatformException {
		Map studentCourses = new HashMap();
		studentCourses.put(courseId, studentIds);
		OracleTrainingSelectCourseActivity activity = new OracleTrainingSelectCourseActivity();
		activity.unCheckSelectCourses(studentCourses);
	}

	public void unCheckClassStudent(String classId, List studentList) throws PlatformException {
		OracleTrainingSelectClassActivity activity = new OracleTrainingSelectClassActivity();
		Map map = new HashMap();

		map.put(classId, studentList);

		activity.unCheckSelectClasses(map);
	}

}
