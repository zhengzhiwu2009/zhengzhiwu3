/**
 * 
 */
package com.whaty.platform.database.oracle.standard.standard.scorm.operation;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.operation.ScormCourse;
import com.whaty.platform.standard.scorm.operation.ScormStudentManage;
import com.whaty.platform.standard.scorm.operation.ScormStudentPriv;
import com.whaty.platform.standard.scorm.operation.UserCourseData;
import com.whaty.platform.standard.scorm.util.ScormLog;
import com.whaty.platform.util.SearchProperty;

/**
 * @author chenjian
 *
 */
public class OracleScormStudentManage extends ScormStudentManage {

	public OracleScormStudentManage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OracleScormStudentManage(ScormStudentPriv studentPriv) {
		this.setStudentPriv(studentPriv);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormStudentManage#getUserScos(java.lang.String)
	 */
	
	public List getUserScos(String courseId) throws ScormException {
		OracleScormDataList dataList=new OracleScormDataList();
/*		List searchproperty=new ArrayList();
		//List orderproperty=new ArrayList();
		searchproperty.add(new SearchProperty("student_id",this.getStudentPriv().getStudentId()));
		searchproperty.add(new SearchProperty("course_id",courseId));
		return dataList.searchUserScos(null,searchproperty,null);*/
		return dataList.searchAllUserScos(this.getStudentPriv().getStudentId(), courseId);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormStudentManage#getUserCourseData(java.lang.String)
	 */
	
	public UserCourseData getUserCourseData(String courseId) throws ScormException {
		OracleScormDataList dataList=new OracleScormDataList();
		List searchproperty=new ArrayList();
		searchproperty.add(new SearchProperty("student_id",this.getStudentPriv().getStudentId()));
		searchproperty.add(new SearchProperty("course_id",courseId));
		List courseList=dataList.searchUserCourses(null, searchproperty, null); 
		if(courseList.size()>0) 
			return (UserCourseData)courseList.get(0);
		else
			return null;
		
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormStudentManage#getCourse(java.lang.String)
	 */
	
	public ScormCourse getCourse(String courseId) throws ScormException {
		return new OracleScormCourse(courseId);
	}
	public   void addAttemptNum(String courseId){
		dbpool db = new dbpool();
		String sql = "update scorm_stu_course set attempt_num = attempt_num + 1 where student_id='"+this.getStudentPriv().getStudentId()+"' and course_id='"+courseId+"'";
		ScormLog.setDebug(sql);
		db.executeUpdate(sql);
		
	}
}
