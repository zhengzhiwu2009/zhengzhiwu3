/**
 * 
 */
package com.whaty.platform.database.oracle.standard.training.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingClassManager;
import com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudent;
import com.whaty.platform.training.basic.StudentClass;
import com.whaty.platform.training.basic.StudentClassStatus;
import com.whaty.platform.training.basic.TrainingClass;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.TrainingLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

/**
 * @author chenjian
 * 
 */
public class OracleTrainingClass extends TrainingClass {

	public OracleTrainingClass() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleTrainingClass(String classId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		try {
			String sql = "select ID,NAME,STATUS,STARTDATE,ENDDATE,START_ELECTDATE,"
					+ "END_ELECTDATE,NOTE,CLASS_CHIEF from training_class where id='"
					+ classId + "'";
			TrainingLog.setDebug(sql);
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("ID"));
				this.setName(rs.getString("NAME"));
				if (rs.getInt("STATUS") == 1)
					this.setActive(true);
				else
					this.setActive(false);
				this.setStartDate(rs.getDate("STARTDATE"));
				this.setEndDate(rs.getDate("ENDDATE"));
				this.setStartElectCourse(rs.getDate("START_ELECTDATE"));
				this.setEndElectCourse(rs.getDate("END_ELECTDATE"));
				this.setNote(rs.getString("NOTE"));

			}
		} catch (SQLException e) {

		} finally {
			db.close(rs);
			db = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#addTrainingCourse(java.util.List)
	 */
	public void addTrainingCourse(List courseList) throws PlatformException {
		if (courseList.size() > 0) {
			List sqlGroup = new ArrayList();
			for (int i = 0; i < courseList.size(); i++) {
				String sql = "insert into training_class_course(class_id,course_id) "
						+ "values('"
						+ this.getId()
						+ "',"
						+ "'"
						+ ((String) courseList.get(i)) + "')";
				UserAddLog.setDebug("OracleTrainingClass.addTrainingCourse(List courseList) SQL=" + sql + " DATE=" + new Date());
				sqlGroup.add(sql);
			}
			dbpool db = new dbpool();
			db.executeUpdateBatch(sqlGroup);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#removeTrainingCourse(java.util.List)
	 */
	public void removeTrainingCourse(List courseList) throws PlatformException {
		if (courseList.size() > 0) {
			String courseIds = "";
			for (int i = 0; i < courseList.size(); i++) {
				courseIds = courseIds + "'" + ((String) courseList.get(i))
						+ "',";
			}
			if (courseIds.length() >= 3)
				courseIds = courseIds.substring(0, courseIds.length() - 1);
			String sql = "delete from training_class_course where "
					+ "class_id='" + this.getId() + "' and course_id in("
					+ courseIds + ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserDeleteLog.setDebug("OracleTrainingClass.removeTrainingCourse(List courseList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#getTrainingCourse()
	 */
	public List getTrainingCourse() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchClassCourses(null, null, null, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#addTrainingStudent(java.util.List)
	 */
	public void addTrainingStudent(List studentList) throws PlatformException {
		if (studentList.size() > 0) {
			List sqlGroup = new ArrayList();
			for (int i = 0; i < studentList.size(); i++) {
				String sql = "insert into training_class_student(id,class_id,student_id,status,learn_status) "
						+ "values(to_char(s_training_id.nextval),'"
						+ this.getId()
						+ "',"
						+ "'"
						+ ((String) studentList.get(i))
						+ "','"
						+ StudentClassStatus.UNDERCHECK + "','')";
				UserAddLog.setDebug("OracleTrainingClass.addTrainingStudent(List studentList) SQL=" + sql + " DATE=" + new Date());
				sqlGroup.add(sql);
			}
			dbpool db = new dbpool();
			db.executeUpdateBatch(sqlGroup);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#removeTrainingStudent(java.util.List)
	 */
	public void removeTrainingStudent(List studentList)
			throws PlatformException {
		if (studentList.size() > 0) {
			String studentIds = "";
			for (int i = 0; i < studentList.size(); i++) {
				studentIds = studentIds + "'" + ((String) studentList.get(i))
						+ "',";
			}
			if (studentIds.length() >= 3)
				studentIds = studentIds.substring(0, studentIds.length() - 1);
			String sql = "delete from training_class_student where "
					+ "class_id='" + this.getId() + "' and student_id in("
					+ studentIds + ")";
			dbpool db = new dbpool();
			int i = db.executeUpdate(sql);
			UserDeleteLog.setDebug("OracleTrainingClass.removeTrainingStudent(List studentList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#getTrainingStudents()
	 */
	public List getTrainingStudents() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List spList = new ArrayList();
		SearchProperty sp = new SearchProperty();
		sp.setField("status");
		sp.setRelation("=");
		sp.setValue(StudentClassStatus.SELECTED);
		spList.add(sp);

		return basicList.searchClassStudents(null, spList, null, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#add()
	 */
	public int add() throws PlatformException {
		StrManage strManage = StrManageFactory.creat();
		dbpool db = new dbpool();
		int active = 0;
		if (this.getIsActive())
			active = 1;
		try {
			String sql = "insert into TRAINING_CLASS(ID,NAME,STATUS,"
					+ "STARTDATE,ENDDATE,START_ELECTDATE,END_ELECTDATE,NOTE)  "
					+ "values(to_char(S_TRAINING_ID.nextval),'"
					+ this.getName() + "'," + active + "," + "to_date('"
					+ strManage.dateToStr(this.getStartDate(), null)
					+ "','yyyy-mm-dd')," + "to_date('"
					+ strManage.dateToStr(this.getEndDate(), null)
					+ "','yyyy-mm-dd')," + "to_date('"
					+ strManage.dateToStr(this.getStartElectCourse(), null)
					+ "','yyyy-mm-dd')," + "to_date('"
					+ strManage.dateToStr(this.getEndElectCourse(), null)
					+ "','yyyy-mm-dd')," + "'" + this.getNote() + "')";
			int i = db.executeUpdate(sql);
			UserAddLog.setDebug("OracleTrainingClass.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			return i;
		} catch (WhatyUtilException e) {
			throw new PlatformException(
					"error in addTrainingClass with the error of "
							+ e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#update()
	 */
	public int update() throws PlatformException {
		StrManage strManage = StrManageFactory.creat();
		dbpool db = new dbpool();
		int active = 0;
		if (this.getIsActive())
			active = 1;
		try {
			String sql = "update  TRAINING_CLASS set NAME='" + this.getName()
					+ "',STATUS=" + active + "," + "STARTDATE=to_date('"
					+ strManage.dateToStr(this.getStartDate(), null)
					+ "','yyyy-mm-dd')," + "ENDDATE=to_date('"
					+ strManage.dateToStr(this.getEndDate(), null)
					+ "','yyyy-mm-dd')," + "START_ELECTDATE=to_date('"
					+ strManage.dateToStr(this.getStartElectCourse(), null)
					+ "','yyyy-mm-dd')," + "END_ELECTDATE=to_date('"
					+ strManage.dateToStr(this.getEndElectCourse(), null)
					+ "','yyyy-mm-dd')," + "NOTE='" + this.getNote()
					+ "' where id='" + this.getId() + "'";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleTrainingClass.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			return i;
		} catch (WhatyUtilException e) {
			throw new PlatformException(
					"error in updateTrainingClass with the error of "
							+ e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.Items#delete()
	 */
	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from TRAINING_CLASS where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTrainingClass.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#getStudentClass(java.lang.String)
	 */
	public StudentClass getStudentClass(String studentId)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		StudentClass studentClass = new StudentClass();
		studentClass.setTrainingClass(this);
		OracleTrainingStudent student = new OracleTrainingStudent();
		student.setId(studentId);
		studentClass.setTrainingStudent(student);
		studentClass.setStatus(StudentClassStatus.UNSELECTED);
		try {
			String sql = "select class_id,class_name,class_note,"
					+ "class_status,studentclass_status,get_date from ("
					+ "select a.id as class_id,a.name as class_name,a.note "
					+ "as class_note,a.status as class_status,"
					+ "b.studentclass_status as studentclass_status,"
					+ "b.get_date as get_date "
					+ " from training_class a,(select class_id,status as studentclass_status,"
					+ "to_char(apply_date,'yyyy-mm-dd') as get_date from "
					+ "training_class_student where student_id='" + studentId
					+ "' and class_id='" + this.getId() + "') b "
					+ "where a.id=b.class_id) ";
			TrainingLog.setDebug(sql);
			rs = db.executeQuery(sql);

			if (rs != null && rs.next()) {
				studentClass.setStatus(rs.getString("studentclass_status"));
			}
		} catch (SQLException e) {
			throw new PlatformException("getStudentClass db query error!");
		} finally {
			db.close(rs);
			db = null;
			return studentClass;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#getClassChiefs()
	 */
	public List getClassManagers() throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List chiefList = new ArrayList();
		try {
			String sql = "select a.id,a.name,a.nick_name,a.email,a.mobilephone,a.phone "
					+ "from training_chief a,training_class_chief b where "
					+ "b.class_id='" + this.getId() + "' and a.id=b.chief_id";
			TrainingLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleTrainingClassManager classManager = new OracleTrainingClassManager();
				classManager.setId(rs.getString("id"));
				classManager.setName(rs.getString("name"));
				classManager.setEmail(rs.getString("email"));
				chiefList.add(classManager);
			}
		} catch (SQLException e) {
			throw new PlatformException("getClassManagers db query error!");
		} finally {
			db.close(rs);
			db = null;
			return chiefList;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#addClassChiefs(java.util.List)
	 */
	public void addClassManagers(List chiefList) throws PlatformException {
		if (chiefList.size() > 0) {
			List sqlGroup = new ArrayList();
			for (int i = 0; i < chiefList.size(); i++) {
				String sql = "insert into training_class_chief(id,class_id,chief_id,"
						+ "chief_type,status) values(to_char(s_training_id.nextval),"
						+ "'"
						+ this.getId()
						+ "',"
						+ "'"
						+ ((String) chiefList.get(i)) + "'," + "'',1)";
				TrainingLog.setDebug("OracleTrainingClass@Method:addClassManagers()="+ sql);
				sqlGroup.add(sql);
			}
			dbpool db = new dbpool();
			db.executeUpdateBatch(sqlGroup);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#removeClassChiefs(java.util.List)
	 */
	public void removeClassManagers(List chiefList) throws PlatformException {
		if (chiefList.size() > 0) {
			String chiefIds = "";
			for (int i = 0; i < chiefList.size(); i++) {
				chiefIds = chiefIds + "'" + ((String) chiefList.get(i)) + "',";
			}
			if (chiefIds.length() >= 3)
				chiefIds = chiefIds.substring(0, chiefIds.length() - 1);
			String sql = "delete from training_class_chief where chief_id in("
					+ chiefIds + ")";
			TrainingLog.setDebug("OracleTrainingClass@Method:removeClassManagers()="+ sql);
			dbpool db = new dbpool();
			db.executeUpdate(sql);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#checkTrainingStudent(java.util.List)
	 */
	public void checkTrainingStudent(List studentList) throws PlatformException {
		if (studentList.size() > 0) {
			String studentIds = "";
			for (int i = 0; i < studentList.size(); i++) {
				studentIds = studentIds + "'" + ((String) studentList.get(i))
						+ "',";
			}
			if (studentIds.length() >= 3)
				studentIds = studentIds.substring(0, studentIds.length() - 1);
			String sql = "update training_class_student set status='"
					+ StudentClassStatus.SELECTED + "' where " + "class_id='"
					+ this.getId() + "' and student_id in(" + studentIds + ")";
			dbpool db = new dbpool();
			TrainingLog.setDebug("OracleTrainingClass@Method:checkTrainingStudent()="+ sql);
			db.executeUpdate(sql);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#getTrainingCourseNum()
	 */
	public int getTrainingCourseNum() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		return basicList.searchClassCoursesNum(null, this);
	}
	
	public int getTrainingCourseNum(String course_id, String course_name) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List searchList = new ArrayList();
		if(course_id != null)
			searchList.add(new SearchProperty("course_id", course_id, "like"));
		if(course_name != null)
			searchList.add(new SearchProperty("course_name", course_name, "like"));
		return basicList.searchClassCoursesNum(searchList, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.training.basic.TrainingClass#getTrainingStudentsNum()
	 */
	public int getTrainingStudentsNum() throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List searchList = new ArrayList();
		searchList.add(new SearchProperty("status", StudentClassStatus.SELECTED, "="));
		//return basicList.searchClassStudentsNum(null, this);
		return basicList.getClassStudentsNum(null, searchList, null, this);
	}
	
	public int getTrainingStudentsNum(String student_id, String student_name) throws PlatformException {
		OracleTrainingBasicList basicList = new OracleTrainingBasicList();
		List searchList = new ArrayList();
		if(student_id != null)
			searchList.add(new SearchProperty("student_id", student_id, "like"));
		if(student_name != null)
			searchList.add(new SearchProperty("student_name", student_name, "like"));
		//return basicList.searchClassStudentsNum(null, this);
		return basicList.getClassStudentsNum(null, searchList, null, this);
	}

}
