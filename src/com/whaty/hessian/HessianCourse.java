package com.whaty.hessian;

import java.util.List;
import java.util.Map;

public interface HessianCourse {
	
	/*
	 * 推荐课程列表
	 */
	public Map recommendCourse(int pageInt, int pageSize);
	
	/**
	 * 按照课程分类读取课程
	 * @param type
	 * @param type1
	 * @param pageNo
	 * @param count
	 * @param isRecommended
	 * @param isFree
	 * @param courseName
	 * @param courseTime
	 * @param courseCategory
	 * @return Map
	 * @author linjie
	 */
	public Map coursesByType(String type,String type1,int pageNo, int count,
			boolean isRecommended,boolean isFree,String courseName,String courseTime,String courseCategory);
	
	/**
	 * 读取有课程的课程的课程分类
	 * @param count 数量限制
	 * @return 课程分类列表
	 * @author linjie
	 */
	public List courseTypes(int count);
	
	/**
	 * 读取课程简介
	 * @param courseId
	 * @return Map
	 * @author linjie
	 */
	public Map courseKCJJ(String courseId);

	/**
	 * 获得课程分类列表
	 * @param nameSpace ‘FlagCourseItemType’ 大纲分类、‘FlagCourseCategory’业务分类、‘FlagCourseType’性质分类
	 * @param code 分类编号（预留，暂无实际作用）
	 * @return List
	 * @author linjie
	 */
	public List getEc(String nameSpace, String code);

}
