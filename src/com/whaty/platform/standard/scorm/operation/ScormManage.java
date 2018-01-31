package com.whaty.platform.standard.scorm.operation;

import java.util.List;

import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.datamodels.SCODataManager;
import com.whaty.platform.standard.scorm.datamodels.StudentSco;


public abstract class ScormManage {

	public abstract List getScormCourses();
	
	public abstract int addCourse(String courseId,String CourseTitle,String controlType);
	
	public abstract ScormCourse getScormCourse(String courseId) throws ScormException;
	
	public abstract List getUserCourses(String userId) throws ScormException;
	
	public abstract List getCoursesItems(String courseId)throws ScormException;
	
	public abstract List getCoursesScos(String courseId)throws ScormException;
	
	/**
	 * 多表现形式下获得SCO列表
	 * @param courseId
	 * @param scormType
	 * @return
	 * @throws ScormException
	 */
	public abstract List getCoursesScos(String courseId,String scormType)throws ScormException;
	
	public abstract List getUserScos(String userId,String courseId)throws ScormException;
	
	/**
	 * 多表现形式下获得SCO列表
	 * @param userId
	 * @param courseId
	 * @param scormType
	 * @return
	 * @throws ScormException
	 */
	public abstract List getUserScos(String userId,String courseId,String scormType)throws ScormException;
	
	public abstract int updateUserScoStatus(String userId,String courseId,String systemId, String lessonStatus,String lessonExit,String lessonEntry,String lessonTime) throws ScormException;
	
	public abstract List getUserScoDatas(String userId,String couseId,String systemId) throws ScormException;

	public abstract UserScoData getUserScoData(String userId,String couseId,String systemId) throws ScormException;

	public abstract SCODataManager getFromDB(String userId,String CourseId,String SystemId) throws ScormException;
	
	public abstract void putIntoDB(SCODataManager scoData,String userId,String CourseId,String SystemId,String openId) throws ScormException;
	
	public int updateUserScoStatus(String userId,String courseId,String systemId, String lessonStatus) throws ScormException{
		return updateUserScoStatus(userId,courseId,systemId, lessonStatus,null,null,null);
	}
	/**
	 * 获得学生sco学习记录
	 * @param courseId
	 * @param studentId
	 * @return
	 * @throws ScormException
	 */
	public abstract StudentSco getUserCourseInfo(String courseId,String studentId) throws ScormException;
}
