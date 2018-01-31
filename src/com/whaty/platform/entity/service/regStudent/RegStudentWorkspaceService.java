package com.whaty.platform.entity.service.regStudent;

import java.util.List;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;

public interface RegStudentWorkspaceService {

	public PeBzzStudent initStudentInfo(String userId);

	public Page initLearingCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName, String time, String courseItemType, String courseContent, String suggestren, String teacher, String coursearea, int pageSize, int startIndex) throws EntityException;

	public Page initLearingCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName, String time, String courseItemType, String courseContent, String suggestren, String teacher, String coursearea, String learnStatus, String orderInfo, String orderType, int pageSize, int startIndex)
			throws EntityException;

	public List tongjiLearningCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName, String time, String courseItemType, String courseContent, String suggestren, String teacher, String coursearea);

	public List tongjiLearningCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName, String time, String courseItemType, String courseContent, String suggestren, String teacher, String coursearea, String learnStatus);

	/**
	 * 课程性质
	 * 
	 * @param namespace
	 * @return
	 * @throws EntityException
	 */
	public List<EnumConst> initCouresType(String namespace) throws EntityException;

	/**
	 * 课程类别
	 * 
	 * @param namespace
	 * @return
	 * @throws EntityException
	 */
	public List<EnumConst> initCourseCategory(String namespace) throws EntityException;

	/**
	 * 按大纲分类
	 * 
	 * @param namespace
	 * @return
	 * @throws EntityException
	 */
	public List<EnumConst> initCourseItemType(String namespace) throws EntityException;

	/**
	 * 初始化内容属性列表 Lee 2014年03月12日
	 */
	public List initCourseContent(String params);

	/**
	 * 初始化内容属性列表 lzh
	 */
	public List iniziLiao(String params);

	/**
	 * 初始化内容属性列表 dgh 2014年06月20日
	 */
	public List initSuggestRen(String params);

	/**
	 * 初始化课程所属区域
	 */
	public List initCourseArea(String params);

	/**
	 * 初始化学习状态
	 */
	public List initLearnStatus(String params);

	/**
	 * 获取个人首页条目数量等
	 * 
	 * @return
	 */
	public List getNum(String method, String loginId, String studentId) throws Exception;

	public Page initFreeCourse(String userId, String courseCode, String courseName, String courseType, String courseItemType, String courseCategory, String courseContent, String time, String teacher, String suggestren, String coursearea, int pageSize, int startIndex) throws EntityException;

	public List tongjiFreeCourse(String stuId, String courseCode, String courseName, String courseType, String courseItemType, String courseCategory, String courseContent, String time, String teacher, String suggestren, String coursearea);

	/**
	 * 待考试课程
	 * 
	 * @author Lee
	 * @param stuId学员ID
	 * @param courseType课程性质
	 * @param courseCode课程编号
	 * @param courseName课程名称
	 * @param time学时
	 * @param teacher主讲人
	 * @param coursearea所属区域
	 * @return
	 * @throws Exception
	 */
	public Page initCompletedCourses(String stuId, String courseType, String courseCode, String courseName, String time, String teacher, String coursearea, int pageSize, int startIndex) throws Exception;

	public List tongjiCompletedCourse(String stuId, String courseType, String courseCode, String courseName, String time, String teacher, String coursearea) throws EntityException;

	public Page firstBulletinInfoByPage(String loginId, int pageSize, int startIndex);

	/**
	 * 已通过课程
	 * 
	 * @author Lee
	 * @param stuId学员ID
	 * @param courseType课程性质
	 * @param time学时
	 * @param teacher主讲人
	 * @param coursearea所属区域
	 * @param selSendDateStart获得学时时间起
	 * @param selSendDateEnd获得学时时间止
	 * @return
	 */
	public Page initPassedCourses(String stuId, String courseType, String time, String teacher, String coursearea, String selSendDateStart, String selSendDateEnd, String courseCode, String courseName, int pageSize, int startIndex) throws Exception;

	public List statisPassedCourse(String stuId, String courseType, String time, String teacher, String coursearea, String selSendDateStart, String selSendDateEnd, String courseCode, String courseName);
	
	/**
	 * 站内信箱
	 * 
	 * @param loginId
	 * @param selTitle
	 * @param selSendDate
	 * @param selSendName
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws EntityException
	 */
	public Page initSiteemail(String loginId, String selTitle, String selSendDateStart, String selSendDateEnd, String selSendName, int pageSize, int startIndex) throws EntityException;
}
