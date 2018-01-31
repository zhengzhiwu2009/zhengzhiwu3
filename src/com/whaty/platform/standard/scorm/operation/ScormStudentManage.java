/**
 * 
 */
package com.whaty.platform.standard.scorm.operation;

import java.util.List;

import com.whaty.platform.standard.scorm.Exception.ScormException;

/**
 * @author chenjian
 *
 */
public abstract class ScormStudentManage {
	
	private ScormStudentPriv studentPriv;

	public ScormStudentPriv getStudentPriv() {
		return studentPriv;
	}

	public void setStudentPriv(ScormStudentPriv studentPriv) {
		this.studentPriv = studentPriv;
	}

	public ScormStudentManage() {
		
	}
	
	/**获得某个课程下该学生全部SCO对象的学习情况
	 * @param courseId
	 * @return
	 * @throws ScormException
	 */
	public abstract List getUserScos(String courseId)throws ScormException;
	
	/**得到某个学生学习某个课程的情况
	 * @param courseId
	 * @return
	 * @throws ScormException
	 */
	public abstract UserCourseData getUserCourseData(String courseId) throws ScormException;
	
	/**得到某个课程
	 * @param courseId
	 * @return
	 */
	public abstract ScormCourse getCourse(String courseId) throws ScormException;
	
	public abstract void addAttemptNum(String courseId);
}
