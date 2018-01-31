package com.whaty.platform.quartz;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.quartz.QuartzService;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.util.ServletUtil;




public class QuartzStatisticJob extends QuartzJobBean{

	private QuartzService quartzService;
	private String batch="";
	
    //public static final int DEFAULT_INTERVAL=1;
	
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	public QuartzService getQuartzService() {
		return quartzService;
	}

	public void setQuartzService(QuartzService quartzService) {
		this.quartzService = quartzService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		System.out.println("定时统计成功！！");
	}
	
	/**
	 * 按机构统计（机构统计，可同时连带机构类别和注册类别）
	 * @return
	 */
	private int statByEnterprise(){
		//生成空记录
		
		//更新数据
		return 0;
	}
	

}
