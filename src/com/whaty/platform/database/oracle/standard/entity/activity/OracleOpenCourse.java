/*
 * OracleOpenCourse.java
 *
 * Created on 2005��5��2��, ����4:27
 */

package com.whaty.platform.database.oracle.standard.entity.activity;

import java.io.Serializable;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSemester;
import com.whaty.platform.database.oracle.standard.test.exam.OracleBasicSequence;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.activity.TeachClass;
import com.whaty.platform.util.Page;

/**
 * 
 * @author Administrator
 */
public class OracleOpenCourse extends OpenCourse implements Serializable{

	/** Creates a new instance of OracleOpenCourse */
	public OracleOpenCourse() {
	}

	public OracleOpenCourse(String id) {
		dbpool dbDep = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select id,course_id,course_name,semester_id,semester_name,credit,course_time,exam_type,major_name,examsequence from "
					+ "(select a.id,a.examsequence,b.id as course_id,b.name as course_name,b.credit,b.course_time,b.exam_type,m.name as major_name,"
					+ "c.id as semester_id,c.name as semester_name from "
					+ "(select id,course_id,semester_id,examsequence from "
					+ "entity_course_active where id='"
					+ id
					+ "') a,entity_course_info b,"
					+ "entity_semester_info c,entity_major_info m where a.course_id=b.id "
					+ "and a.semester_id=c.id and b.major_id = m.id(+))";
			rs = dbDep.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCourse_time(rs.getFloat("course_time"));
				course.setCredit(rs.getString("credit"));
				course.setExam_type(rs.getString("exam_type"));
				course.setMajor_name(rs.getString("major_name"));
//				this.setCourse(course);
				OracleSemester semester = new OracleSemester();
				semester.setId(rs.getString("semester_id"));
				semester.setName(rs.getString("semester_name"));
//				this.setSemester(semester);
				OracleBasicSequence examseq=new OracleBasicSequence(rs.getString("examsequence"));
				this.setExamSequence(examseq);
			}
		} catch (java.sql.SQLException e) {

		} finally {
			dbDep.close(rs);
			dbDep = null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.OpenCourse#addTeachClass(com.whaty.platform.entity.activity.TeachClass)
	 */
	public void addTeachClass(TeachClass teachClass) throws PlatformException {
		int i = teachClass.add();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.OpenCourse#removeTeachClass(java.util.List)
	 */
	public void removeTeachClass(List teachClassList) throws PlatformException {
		for (int i = 0; i < teachClassList.size(); i++) {
			int flag = ((TeachClass) teachClassList.get(i)).delete();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.OpenCourse#getTeachClasses(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List)
	 */
	public List getTeachClasses(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		OracleBasicActivityList basicActivityList = new OracleBasicActivityList();
		return basicActivityList.searchTeachClass(page, searchproperty,
				orderproperty, this);
	}

}
