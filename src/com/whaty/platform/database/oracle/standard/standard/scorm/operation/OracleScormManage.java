/**
 * 
 */
package com.whaty.platform.database.oracle.standard.standard.scorm.operation;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.datamodels.SCODataManager;
import com.whaty.platform.standard.scorm.datamodels.StudentSco;
import com.whaty.platform.standard.scorm.operation.ScormCourse;
import com.whaty.platform.standard.scorm.operation.ScormManage;
import com.whaty.platform.standard.scorm.operation.UserScoData;
import com.whaty.platform.util.SearchProperty;

/**
 * @author Administrator
 *
 */
public class OracleScormManage extends ScormManage {

	public OracleScormManage() {
		
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormManage#getScormCourses()
	 */
	public List getScormCourses() {
		OracleScormDataList dataList=new OracleScormDataList();
		return dataList.searchScormCourse(null, null, null);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormManage#addCourse(java.lang.String, java.lang.String, java.lang.String)
	 */
	public int addCourse(String courseId, String CourseTitle, String controlType) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormManage#getScormCourse(java.lang.String)
	 */
	public ScormCourse getScormCourse(String courseId) throws ScormException {
		return new OracleScormCourse(courseId);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormManage#getUserCourses(java.lang.String)
	 */
	public List getUserCourses(String user_id) throws ScormException {
		OracleUserData userData=new OracleUserData();
		userData.setStudent_id(user_id);
		return userData.getSelectedCourses();
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormManage#getCoursesItems(java.lang.String)
	 */
	public List getCoursesItems(String courseId) throws ScormException {
		OracleScormCourse course=new OracleScormCourse(courseId);
		return course.getItems();
	}

	public List getCoursesScos(String courseId) throws ScormException {
		OracleScormCourse course=new OracleScormCourse(courseId);
		return course.getScos();
	}
	
	@Override
	public List getCoursesScos(String courseId, String scormType)
			throws ScormException {
		OracleScormCourse course=new OracleScormCourse(courseId);
		return course.getScos(scormType);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormManage#getUserScos(java.lang.String, java.lang.String)
	 */
	public List getUserScos(String userId, String courseId) throws ScormException {
		OracleScormDataList dataList=new OracleScormDataList();
/*		List searchproperty=new ArrayList();
		//List orderproperty=new ArrayList();
		searchproperty.add(new SearchProperty("student_id",userId));
		searchproperty.add(new SearchProperty("course_id",courseId));
		
		return dataList.searchUserScos(null,searchproperty,null);*/
		return dataList.searchAllUserScos(userId, courseId);
	}

	@Override
	public List getUserScos(String userId, String courseId, String scormType)
			throws ScormException {
		OracleScormDataList dataList=new OracleScormDataList();
		return dataList.searchAllUserScos(userId, courseId,scormType);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormManage#updateUserScoStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int updateUserScoStatus(String userId, String courseId, String systemId, String lessonStatus,String lessonExit,String lessonEntry,String lessonTime) throws ScormException {
		OracleUserScoData userScoData=new OracleUserScoData(userId,courseId,systemId);
		userScoData.getCore().setLessonStatus(lessonStatus);
		userScoData.getCore().setExit(lessonExit);
		userScoData.getCore().setEntry(lessonEntry);
		userScoData.getCore().setSessionTime(lessonTime);
		return userScoData.update();
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormManage#getUserScoData(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getUserScoDatas(String userId, String courseId, String systemId) throws ScormException {
		OracleScormDataList dataList=new OracleScormDataList();
		List searchproperty=new ArrayList();
		//List orderproperty=new ArrayList();
		searchproperty.add(new SearchProperty("student_id",userId));
		searchproperty.add(new SearchProperty("course_id",courseId));
		searchproperty.add(new SearchProperty("system_id",systemId));
		return dataList.searchUserScos(null,searchproperty,null);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormManage#getUserScoData(java.lang.String, java.lang.String, java.lang.String)
	 */
	public UserScoData getUserScoData(String userId, String couseId, String systemId) throws ScormException {
		// TODO Auto-generated method stub
		return new OracleUserScoData(userId,couseId,systemId);
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormManage#getFromDB(java.lang.String, java.lang.String, java.lang.String)
	 */
	public SCODataManager getFromDB(String userId, String CourseId, String SystemId) throws ScormException {
		SCODataManager scoDataManager=new SCODataManager();
		UserScoData userScoData=this.getUserScoData(userId, CourseId, SystemId);
		scoDataManager.setCore(userScoData.getCore());
		scoDataManager.setStudentData(userScoData.getStudentData());
		scoDataManager.setComments(userScoData.getComments());
		scoDataManager.setCommentsFromLMS(userScoData.getCommentsFromLMS());
		scoDataManager.setInteractions(userScoData.getInteractions());
		scoDataManager.setLaunchData(userScoData.getLaunchData());
		scoDataManager.setObjectives(userScoData.getObjectives());
		scoDataManager.setStudentPreference(userScoData.getStudentPreference());
		scoDataManager.setSuspendData(userScoData.getSuspendData());
		
		scoDataManager.setComments(userScoData.getComments());
		return scoDataManager;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.operation.ScormManage#putIntoDB(com.whaty.platform.standard.scorm.operation.UserScoData)
	 */
	public void putIntoDB(SCODataManager ScoData,String userId, String CourseId, String SystemId,String openId) throws ScormException {
		//如果是未定义的sco不做任何操作
		if("undefined".equalsIgnoreCase(SystemId)) {
			return;
		}
		OracleCMIWithDB cmiWithDB=new OracleCMIWithDB();
		cmiWithDB.setUserId(userId);
		cmiWithDB.setCourseId(CourseId);
		cmiWithDB.setSystemId(SystemId);
		cmiWithDB.setScoData(ScoData);
		
		cmiWithDB.setOpenId(openId);
		try{
			cmiWithDB.putToDB();
		}catch(ScormException e){
			throw new ScormException(e.getMessage());
		}
	}
	
	public StudentSco getUserCourseInfo(String courseId, String studentId)
			throws ScormException {
		OracleUserData userData=new OracleUserData();
		return userData.getSudentSco(studentId, courseId);
	}
}
