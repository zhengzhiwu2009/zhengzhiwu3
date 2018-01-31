package com.whaty.platform.entity.service.quartz;

import java.util.HashSet;

import com.whaty.platform.entity.service.GeneralService;

public interface QuartzService extends GeneralService {
	void exeAllStat(String dateStr, HashSet<String> typeSet);
	/**
	 * 迁移学员信息
	 * @param size 
	 * @return 
	 * @author linjie
	 */
	public void moveStuService(int size);
	/**
	 * 同步机构类别信息
	 * @param size 
	 * @return 
	 * @author linjie
	 */
	public void moveSiteTypeService(int size);
	/**
	 * 同步机构信息
	 * @param size 
	 * @return 
	 * @author linjie
	 */
	public void moveSiteService(int size);
	
	/**
	 * 按学员RegNo同步选课信息到中间库
	 * @param stuId 
	 * @return 
	 * @author linjie
	 */
	public void moveStuEleService(String stuRegNo);

}
