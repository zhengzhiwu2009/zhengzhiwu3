package com.whaty.platform.database.oracle.standard.standard.aicc.operation;

import java.util.List;

import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.model.Objectives;
import com.whaty.platform.standard.aicc.operation.AiccCourse;
import com.whaty.platform.standard.aicc.operation.AiccUserDataManage;
import com.whaty.platform.standard.aicc.operation.AiccUserDataManagePriv;
import com.whaty.platform.standard.aicc.operation.UserCourseData;

public class OracleAiccUserDataManage extends AiccUserDataManage {

	
	
	public OracleAiccUserDataManage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OracleAiccUserDataManage(AiccUserDataManagePriv userPriv) {
		this.setUserpriv(userPriv);
	}

	public List getCoursesDataOfUser() throws AiccException {
		if(this.getUserpriv().getCoursesDataOfUser==1)
		{
			OracleUserData userData=new OracleUserData(this.getUserpriv().getStudentId());
			return userData.getCoursesData();
		}
		else
		{
			throw new AiccException("no right to getCoursesDataOfUser");
		}
		
	}

	public List getCourses() throws AiccException {
		if(this.getUserpriv().getCourses==1)
		{
			OracleAiccDataList dataList=new OracleAiccDataList();
			return dataList.searchCourses(null,null,null);
		}
		else
		{
			throw new AiccException("no right to getCourses");
		}
	}

	public AiccCourse getCourse(String course_id) throws AiccException {
		if(this.getUserpriv().getCourse==1)
		{
			return new OracleAiccCourse(course_id);
		}
		else
		{
			throw new AiccException("no right to getCourse");
		}
	}

	public List getAUsDataOfUser(String course_id) throws AiccException {
		if(this.getUserpriv().getAUsDataOfUser==1)
		{
			OracleUserData userData=new OracleUserData(this.getUserpriv().getStudentId());
			return userData.getAUsData(course_id);
		}
		else
		{
			throw new AiccException("no right to getAUsDataOfUser");
		}
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.aicc.operation.AiccUserDataManage#getObjectivesDataOfUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Objectives getObjectivesDataOfUser(String course_id,String ausystem_id) throws AiccException {
		if(this.getUserpriv().getObjectivesDataOfUser==1)
		{
			OracleUserData userData=new OracleUserData(this.getUserpriv().getStudentId());
			return userData.getObjectivsData(course_id,ausystem_id);
		}
		else
		{
			throw new AiccException("no right to getObjectivesDataOfUser");
		}
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.aicc.operation.AiccUserDataManage#getCoursesDataOfUser(java.lang.String)
	 */
	public UserCourseData getCourseDataOfUser(String courseId) throws AiccException {
		if(this.getUserpriv().getCoursesDataOfUser==1)
		{
			OracleUserData userData=new OracleUserData(this.getUserpriv().getStudentId());
			return userData.getCourseData(courseId);
		}
		else
		{
			throw new AiccException("no right to getCoursesDataOfUser");
		}
		
	}

}
