package com.whaty.platform.quartz;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.whaty.platform.entity.service.quartz.QuartzService;
import com.whaty.util.SpringUtil;

/**
 * 定时统计
 * @author shangbingcang
 * @since 2012-04-23
 * @version v1.1.1
 *
 */
public class QuartzCourseDayJob extends QuartzJobBean implements Serializable{

	private QuartzService quartzService;
	private Date preSysdate;     //获取系统当前时间的前一天，以便于数据更新实用
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		this.execute();
	}
	/**
	 * 定时任务 统计功能
	 * @param 
	 * @return 
	 * @author linjie
	 */
	public void execute(){
//		long start = new Date().getTime();
//
//		String dateStr = "sysdate";
//		System.out.println("开始定时任务"+new Date());
//		HashSet<String> typeSet = new HashSet<String>();
//		typeSet.add("all");
//		
//		/**执行统计主体**/
//		this.getQuartzService().exeAllStat(dateStr,typeSet);
//		
//		System.out.println("定时统计成功！！");
//		long end = new Date().getTime();
//		System.out.println("完成定时任务时间"+new Date()+"--耗时："+(end-start)+"ms");
		
	}
	
	public QuartzService getQuartzService() {
		if(quartzService==null){
			quartzService = (QuartzService) SpringUtil.getBean("quartzService");
		}
		return quartzService;
	}

	public void setQuartzService(QuartzService quartzService) {
		this.quartzService = quartzService;
	}
	
	
	/**
	 * 获取系统时间的前一天
	 * @param 
	 * @return 
	 * @author linjie
	 */
	public Date getPreSysdate() {
		Calendar calendar = Calendar.getInstance();		
		calendar.setTime(new Date());		
		calendar.add(Calendar.DAY_OF_MONTH, -1);		
		preSysdate = calendar.getTime();		
		return preSysdate;
	}
	public void setPreSysdate(Date preSysdate) {
		this.preSysdate = preSysdate;
	}

}
