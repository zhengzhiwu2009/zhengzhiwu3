package com.whaty.platform.quartz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.codehaus.xfire.spring.SpringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.quartz.QuartzService;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.util.SpringUtil;

/**
 * 定时统计
 * @author shangbingcang
 * @since 2012-04-23
 * @version v1.1.1
 *
 */
public class QuartzMoveSite extends QuartzJobBean implements Serializable{

	private QuartzService quartzService;
	private Date preSysdate;     //获取系统当前时间的前一天，以便于数据更新实用
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		this.execute();
	}
	/**
	 * 定时任务 从业机构同步
	 * @param 
	 * @return 
	 * @author linjie
	 */
	public void execute(){
		getQuartzService();
		long start = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String msg = "";
		moveStu();
		Date now = new Date();
		long end = now.getTime();
		
		System.out.println("Sync Site Completed,Date:"+now.toString()+" Cost:"+(end-start)+"ms,"+msg);
	}
	public void moveStu(){
//		quartzService.moveStuService(10000);//同步学生
		
		quartzService.moveSiteTypeService(1000);//同步机构类别
		
		quartzService.moveSiteService(1000);//同步机构
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
}
