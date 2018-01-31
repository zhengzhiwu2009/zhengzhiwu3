/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.basic;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudent;
import com.whaty.platform.training.basic.StudentClass;
import com.whaty.platform.training.basic.StudentClassStatus;
import com.whaty.platform.training.basic.StudentCourse;
import com.whaty.platform.training.basic.StudentCourseStatus;
import com.whaty.platform.training.basic.StudyProgress;
import com.whaty.platform.training.basic.TrainingBasicList;
import com.whaty.platform.training.basic.TrainingClass;
import com.whaty.platform.training.basic.TrainingCourse;
import com.whaty.platform.training.basic.TrainingCourseType;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.TrainingLog;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingBasicList implements TrainingBasicList {

	private String COURSETYPETREESQL = "select id,name,status,parent_id,level from "
			+ "(select id,name,status,parent_id from training_course_type) "
			+ "start with parent_id='root' connect by prior id = parent_id ";

	private String COURSESQL = "select id,name,course_typeid,status,note,coursetype_name from "
			+ "(select a.id,a.name,a.course_typeid,a.status,a.note,b.name as coursetype_name"
			+ " from training_course a,training_course_type  b where a.course_typeid=b.id) ";

	private String CLASSSQL = "select ID,NAME,STATUS,STARTDATE,ENDDATE,START_ELECTDATE,"
			+ "END_ELECTDATE,NOTE,CLASS_CHIEF,course_num from (select a.ID,a.NAME,a.STATUS,a.STARTDATE,a.ENDDATE,a.START_ELECTDATE,"
			+ "a.END_ELECTDATE,a.NOTE,a.CLASS_CHIEF,b.course_num from training_class a,"
			+ "(select count(course_id) as course_num,class_id from training_class_course"
			+ "  group by class_id) b where a.id=b.class_id(+))";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#searchCourseTypeTree(java.util.List,
	 *      java.util.List)
	 */
	public List searchCourseTypeTree(List searchProperty, List orderProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = COURSETYPETREESQL
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList courseTypeTree = null;
		try {
			db = new dbpool();
			courseTypeTree = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				TrainingCourseType courseType = new OracleTrainingCourseType();
				courseType.setId(rs.getString("id"));
				courseType.setName(rs.getString("name"));
				courseType.setLevel(rs.getInt("level"));
				TrainingCourseType parentType = new OracleTrainingCourseType();
				parentType.setId(rs.getString("parent_id"));
				courseType.setParentType(parentType);
				if (rs.getInt("status") == 1)
					courseType.setActive(true);
				else
					courseType.setActive(false);
				courseTypeTree.add(courseType);
			}
		} catch (Exception e) {
			throw new PlatformException("error in searchCourseTypeTree");
		} finally {
			db.close(rs);
			db = null;

		}
		return courseTypeTree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#searchCourse(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List)
	 */
	public List searchCourse(Page page, List searchProperty, List orderProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = COURSESQL
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList courseList = null;
		try {
			db = new dbpool();
			courseList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}

			while (rs != null && rs.next()) {
				TrainingCourse course = new OracleTrainingCourse();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("name"));
				course.setStatus(rs.getString("status"));
				TrainingCourseType courseType = new OracleTrainingCourseType();
				courseType.setId(rs.getString("course_typeid"));
				courseType.setName(rs.getString("coursetype_name"));
				course.setTrainingCourseType(courseType);
				course.setNote(rs.getString("note"));
				courseList.add(course);
			}
		} catch (Exception e) {
			throw new PlatformException("error in searchCourseTypeTree");
		} finally {
			db.close(rs);
			db = null;

		}
		return courseList;
	}

	public int searchCourseNum(List searchProperty, List orderProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = COURSESQL
				+ Conditions.convertToCondition(searchProperty, orderProperty);

		return db.countselect(sql);
	}

	public int searchStudentCourseNum(List searchProperty, List orderProperty,
			TrainingStudent student) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select course_id,course_name,course_note,"
				+ "course_status,course_typeid,studentcourse_status,studentlearn_status,get_date from ("
				+ "select a.id as course_id,a.name as course_name,a.note "
				+ "as course_note,a.status as course_status,a.course_typeid as course_typeid,"
				+ "b.studentcourse_status "
				+ "as studentcourse_status,b.studentlearn_status as studentlearn_status,"
				+ "b.get_date as get_date "
				+ " from training_course a,(select course_id,status as studentcourse_status,"
				+ "learn_status as studentlearn_status,"
				+ "to_char(get_date,'yyyy-mm-dd') as get_date from "
				+ "training_course_student where student_id='"
				+ student.getId() + "') b " + "where a.id=b.course_id) ";
		sql = sql
				+ Conditions.convertToCondition(searchProperty, orderProperty);

		return db.countselect(sql);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#searchStudentCourse(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List)
	 */
	public List searchStudentCourse(Page page, List searchProperty,
			List orderProperty, TrainingStudent student)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select course_id,course_name,course_note,"
				+ "course_status,course_typeid,studentcourse_status,studentlearn_status,get_date,percent from ("
				+ "select a.id as course_id,a.name as course_name,a.note "
				+ "as course_note,a.status as course_status,a.course_typeid as course_typeid,"
				+ "b.studentcourse_status "
				+ "as studentcourse_status,b.studentlearn_status as studentlearn_status,"
				+ "b.get_date as get_date,b.percent as percent "
				+ " from training_course a,(select course_id,status as studentcourse_status,"
				+ "learn_status as studentlearn_status,"
				+ "to_char(get_date,'yyyy-mm-dd') as get_date,percent from "
				+ "training_course_student where student_id='"
				+ student.getId() + "') b " + "where a.id=b.course_id) ";
		sql = sql
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList courses = null;
		String status = null;
		try {
			db = new dbpool();
			courses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				StudentCourse studentCourse = new OracleStudentCourse();
				TrainingCourse course = new OracleTrainingCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				TrainingCourseType trainingCourseType = new OracleTrainingCourseType();
				trainingCourseType.setId(rs.getString("course_typeid"));
				course.setTrainingCourseType(trainingCourseType);
				course.setNote(rs.getString("course_note"));
				course.setStatus(rs.getString("course_status"));
				studentCourse.setTrainingCourse(course);
				studentCourse.setTrainingStudent(student);
				status = rs.getString("studentcourse_status");
				if (status == null || status.length() < 1)
					status = StudentCourseStatus.UNSELECTED;
				studentCourse.setStatus(status);
				StudyProgress studyProgress = new StudyProgress();
				studyProgress.setLearnStatus(rs
						.getString("studentlearn_status"));
				studyProgress.setPercent(rs.getFloat("percent"));
				studentCourse.setLearnProgress(studyProgress);
				studentCourse.setGetDate(rs.getString("get_date"));
				courses.add(studentCourse);
			}
		} catch (Exception e) {
			throw new PlatformException("error in searchStudentCourse");
		} finally {
			db.close(rs);
			db = null;

		}
		return courses;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#getClassNum(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List)
	 */
	public int getClassNum(Page page, List searchProperty, List orderProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = CLASSSQL
				+ Conditions.convertToCondition(searchProperty, orderProperty);

		return db.countselect(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#searchClass(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List)
	 */
	public List searchClass(Page page, List searchProperty, List orderProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = CLASSSQL
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList classList = null;
		try {
			db = new dbpool();
			classList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				TrainingClass newClass = new OracleTrainingClass();
				newClass.setId(rs.getString("id"));
				newClass.setName(rs.getString("name"));
				if (rs.getInt("status") == 1) {
					newClass.setActive(true);
				} else {
					newClass.setActive(false);
				}
				newClass.setStartDate(rs.getDate("STARTDATE"));
				newClass.setEndDate(rs.getDate("ENDDATE"));
				newClass.setStartElectCourse(rs.getDate("START_ELECTDATE"));
				newClass.setEndElectCourse(rs.getDate("END_ELECTDATE"));
				newClass.setNote(rs.getString("note"));
				newClass.setCourseNum(rs.getInt("course_num"));
				classList.add(newClass);
			}
		} catch (Exception e) {
			throw new PlatformException("error in searchClass");
		} finally {
			db.close(rs);
			db = null;

		}
		return classList;
	}

	public int getStudentClassNum(List searchProperty, List orderProperty,
			TrainingStudent student) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select class_id,class_name,class_note,"
				+ "class_status,studentclass_status,get_date from ("
				+ "select a.id as class_id,a.name as class_name,a.note "
				+ "as class_note,a.status as class_status,"
				+ "b.studentclass_status as studentclass_status,"
				+ "b.get_date as get_date "
				+ " from training_class a,(select class_id,status as studentclass_status,"
				+ "to_char(apply_date,'yyyy-mm-dd') as get_date from "
				+ "training_class_student where student_id='" + student.getId()
				+ "') b " + "where a.id=b.class_id) ";
		sql = sql
				+ Conditions.convertToCondition(searchProperty, orderProperty);

		return db.countselect(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#searchStudentClass(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,
	 *      com.whaty.platform.training.user.TrainingStudent)
	 */
	public List searchStudentClass(Page page, List searchProperty,
			List orderProperty, TrainingStudent student)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select class_id,class_name,class_note,"
				+ "class_status,studentclass_status,get_date from ("
				+ "select a.id as class_id,a.name as class_name,a.note "
				+ "as class_note,a.status as class_status,"
				+ "b.studentclass_status as studentclass_status,"
				+ "b.get_date as get_date "
				+ " from training_class a,(select class_id,status as studentclass_status,"
				+ "to_char(apply_date,'yyyy-mm-dd') as get_date from "
				+ "training_class_student where student_id='" + student.getId()
				+ "') b " + "where a.id=b.class_id) ";
		sql = sql
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList classes = null;
		String status = null;
		try {
			db = new dbpool();
			classes = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				StudentClass studentClass = new StudentClass();
				TrainingClass newclass = new OracleTrainingClass();
				newclass.setId(rs.getString("class_id"));
				newclass.setName(rs.getString("class_name"));
				newclass.setNote(rs.getString("class_note"));
				int flag = rs.getInt("class_status");
				if (flag != 1)
					newclass.setActive(false);
				else
					newclass.setActive(true);
				studentClass.setTrainingClass(newclass);
				studentClass.setTrainingStudent(student);
				status = rs.getString("studentclass_status");
				if (status == null || status.length() < 1)
					status = StudentClassStatus.UNSELECTED;
				studentClass.setStatus(status);
				studentClass.setGetDate(rs.getString("get_date"));
				classes.add(studentClass);
			}
		} catch (Exception e) {
			throw new PlatformException("error in searchStudentClass");
		} finally {
			db.close(rs);
			db = null;

		}
		return classes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#searchClassCourses(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,
	 *      com.whaty.platform.training.user.TrainingStudent)
	 */
	public List searchClassCourses(Page page, List searchProperty,
			List orderProperty, TrainingClass newClass)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select course_id,course_name,course_note,"
				+ "course_status,course_typeid from ("
				+ "select a.id as course_id,a.name as course_name,a.note "
				+ "as course_note,a.status as course_status,a.course_typeid as course_typeid "
				+ " from training_course a,(select course_id from "
				+ "training_class_course where class_id='" + newClass.getId()
				+ "') b " + "where a.id=b.course_id) ";
		sql = sql
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList courses = null;
		try {
			db = new dbpool();
			courses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				TrainingCourse course = new OracleTrainingCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				TrainingCourseType trainingCourseType = new OracleTrainingCourseType(
						rs.getString("course_typeid"));
				// trainingCourseType.setId(rs.getString("course_typeid"));
				course.setTrainingCourseType(trainingCourseType);
				course.setNote(rs.getString("course_note"));
				course.setStatus(rs.getString("course_status"));
				courses.add(course);
			}
		} catch (Exception e) {
			throw new PlatformException("error in searchClassCourses");
		} finally {
			db.close(rs);
			db = null;

		}
		return courses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#getClassStudentsNum(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,
	 *      com.whaty.platform.training.basic.TrainingClass)
	 */
	public int getClassStudentsNum(Page page, List searchProperty,
			List orderProperty, TrainingClass newClass)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select student_id,student_name,nick_name,"
				+ "email, status from ("
				+ "select a.id as student_id,a.name as student_name,a.nick_name,a.email, b.status  "
				+ " from training_student a,(select student_id, status from "
				+ "training_class_student where class_id='" + newClass.getId()
				+ "') b " + "where a.id=b.student_id) ";
		sql = sql
				+ Conditions.convertToCondition(searchProperty, orderProperty);

		return db.countselect(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#searchClassStudents(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,
	 *      com.whaty.platform.training.basic.TrainingClass)
	 */
	public List searchClassStudents(Page page, List searchProperty,
			List orderProperty, TrainingClass newClass)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select student_id,student_name,nick_name,"
				+ "email, status from ("
				+ "select a.id as student_id,a.name as student_name,a.nick_name,a.email, b.status  "
				+ " from training_student a,(select student_id, status from "
				+ "training_class_student where class_id='" + newClass.getId()
				+ "') b " + "where a.id=b.student_id) ";
		sql = sql
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList students = null;
		try {
			db = new dbpool();
			students = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTrainingStudent student = new OracleTrainingStudent();
				student.setId(rs.getString("student_id"));
				student.setName(rs.getString("student_name"));
				student.setNickName(rs.getString("nick_name"));
				student.setEmail(rs.getString("email"));
				students.add(student);
			}
		} catch (Exception e) {
			throw new PlatformException("error in searchClassStudents");
		} finally {
			db.close(rs);
			db = null;

		}
		return students;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#searchCourseStudents(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,
	 *      com.whaty.platform.training.basic.TrainingCourse)
	 */
	public List searchCourseStudents(Page page, List searchProperty,
			List orderProperty, TrainingCourse course) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select student_id,student_name,nick_name,"
				+ "email,studentcourse_status from ("
				+ "select a.id as student_id,a.name as student_name,a.nick_name,a.email,"
				+ "b.studentcourse_status  "
				+ " from training_student a,(select student_id,status as studentcourse_status,"
				+ "learn_status as studentlearn_status,"
				+ "to_char(get_date,'yyyy-mm-dd') as get_date from "
				+ "training_course_student where course_id='" + course.getId()
				+ "') b " + "where a.id=b.student_id) ";
		sql = sql
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList students = null;
		String status = null;
		try {
			db = new dbpool();
			students = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTrainingStudent student = new OracleTrainingStudent();
				student.setId(rs.getString("student_id"));
				student.setName(rs.getString("student_name"));
				student.setNickName(rs.getString("nick_name"));
				student.setEmail(rs.getString("email"));
				students.add(student);

			}
		} catch (Exception e) {
			throw new PlatformException("error in searchCourseStudents");
		} finally {
			db.close(rs);
			db = null;

		}
		return students;
	}

	public int searchClassCourseStudentsNum(List searchProperty,
			List orderProperty, TrainingClass trainingClass,
			TrainingCourse course) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select student_id,student_name,nick_name,"
				+ "email,studentcourse_status from ("
				+ "select a.id as student_id,a.name as student_name,a.nick_name,a.email,"
				+ "b.studentcourse_status  "
				+ " from training_student a,(select student_id,status as studentcourse_status,"
				+ "learn_status as studentlearn_status,"
				+ "to_char(get_date,'yyyy-mm-dd') as get_date from "
				+ "training_course_student where course_id='"
				+ course.getId()
				+ "') b, (select student_id from training_class_student where class_id='"
				+ trainingClass.getId() + "') c "
				+ "where a.id=b.student_id and a.id=c.student_id) ";
		sql = sql
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		TrainingLog.setDebug(sql);

		db = new dbpool();

		return db.countselect(sql);
	}

	public List searchClassCourseStudents(Page page, List searchProperty,
			List orderProperty, TrainingClass trainingClass,
			TrainingCourse course) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select student_id,student_name,nick_name,"
				+ "email,studentcourse_status from ("
				+ "select a.id as student_id,a.name as student_name,a.nick_name,a.email,"
				+ "b.studentcourse_status  "
				+ " from training_student a,(select student_id,status as studentcourse_status,"
				+ "learn_status as studentlearn_status,"
				+ "to_char(get_date,'yyyy-mm-dd') as get_date from "
				+ "training_course_student where course_id='"
				+ course.getId()
				+ "') b, (select student_id from training_class_student where class_id='"
				+ trainingClass.getId() + "') c "
				+ "where a.id=b.student_id and a.id=c.student_id) ";
		sql = sql
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		TrainingLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList students = null;
		String status = null;
		try {
			db = new dbpool();
			students = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTrainingStudent student = new OracleTrainingStudent();
				student.setId(rs.getString("student_id"));
				student.setName(rs.getString("student_name"));
				student.setNickName(rs.getString("nick_name"));
				student.setEmail(rs.getString("email"));
				students.add(student);

			}
		} catch (Exception e) {
			throw new PlatformException("error in searchCourseStudents");
		} finally {
			db.close(rs);
			db = null;

		}
		return students;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#searchClassCoursesNum(java.util.List,
	 *      com.whaty.platform.training.basic.TrainingClass)
	 */
	public int searchClassCoursesNum(List searchProperty, TrainingClass newClass)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select course_id,course_name,course_note,"
				+ "course_status,course_typeid from ("
				+ "select a.id as course_id,a.name as course_name,a.note "
				+ "as course_note,a.status as course_status,a.course_typeid as course_typeid "
				+ " from training_course a,(select course_id from "
				+ "training_class_course where class_id='" + newClass.getId()
				+ "') b " + "where a.id=b.course_id) ";
		sql = sql + Conditions.convertToCondition(searchProperty, null);
		TrainingLog.setDebug(sql);
		return db.countselect(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#searchCourseStudents(java.util.List,
	 *      com.whaty.platform.training.basic.TrainingCourse)
	 */
	public int searchCourseStudentsNum(List searchProperty,
			TrainingCourse course) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select student_id,student_name,nick_name,"
				+ "email,studentcourse_status from ("
				+ "select a.id as student_id,a.name as student_name,a.nick_name,a.email,"
				+ "b.studentcourse_status  "
				+ " from training_student a,(select student_id,status as studentcourse_status,"
				+ "learn_status as studentlearn_status,"
				+ "to_char(get_date,'yyyy-mm-dd') as get_date from "
				+ "training_course_student where course_id='" + course.getId()
				+ "') b " + "where a.id=b.student_id) ";
		sql = sql + Conditions.convertToCondition(searchProperty, null);
		TrainingLog.setDebug(sql);
		return db.countselect(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingBasicList#searchClassStudentsNum(java.util.List,
	 *      com.whaty.platform.training.basic.TrainingClass)
	 */
	public int searchClassStudentsNum(List searchProperty,
			TrainingClass newClass) throws PlatformException {
		dbpool db = new dbpool();
		/*
		 * String sql="select student_id,student_name,nick_name," +
		 * "email,status from (" + "select a.id as student_id,a.name as
		 * student_name,a.nick_name,a.email " + " from training_student
		 * a,(select student_id,status from " + "training_class_student where
		 * class_id='"+newClass.getId()+"') b " + "where a.id=b.student_id) ";
		 */
		String sql = "select student_id,student_name,nick_name,"
				+ "email, status from ("
				+ "select a.id as student_id,a.name as student_name,a.nick_name,a.email, b.status  "
				+ " from training_student a,(select student_id, status from "
				+ "training_class_student where class_id='" + newClass.getId()
				+ "') b " + "where a.id=b.student_id) ";
		sql = sql + Conditions.convertToCondition(searchProperty, null);
		TrainingLog.setDebug(sql);
		return db.countselect(sql);
	}

}
