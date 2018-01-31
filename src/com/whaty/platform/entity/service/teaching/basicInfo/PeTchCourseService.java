package com.whaty.platform.entity.service.teaching.basicInfo;

import java.io.File;
import java.util.List;

import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeBzzTchCourseware;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.exception.EntityException;


public interface PeTchCourseService {

	public int save_uploadCourse(File file) throws EntityException;
	
	/**
	 * 批量添加课件
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int save_uploadCourseware(File file) throws EntityException;
	
	public PeBzzTchCourseware savePeBzzTchCourseware(PeBzzTchCourseware peBzzTchCourseware) throws EntityException;
	
	public int deleteCourse(List ids) throws EntityException;
	
	public PrBzzTchOpencourse add(PrBzzTchOpencourse prBzzTchOpencourse) throws EntityException;

	public PeBzzTchCourse saveCourseAndCourseware(PeBzzTchCourse dbInstance,PeBzzTchCourse bean);
}
