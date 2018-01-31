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
public class QuartzCheckDayJob extends QuartzJobBean implements Serializable{

	private QuartzService quartzService;
	private Date preSysdate;     //获取系统当前时间的前一天，以便于数据更新实用
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		//this.execute(); //暂时不执行了
	}
	/**
	 * 定时任务 自动对账 （已弃用）
	 * @param 
	 * @return 
	 * @author linjie
	 */
	public void execute(){
		long start = new Date().getTime();
		Date preDate = this.getPreSysdate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String preDateStr = sdf.format(preDate);
		String msg = "";
		try {
			this.getQuartzService().checkReconciliation(preDateStr, preDateStr, "");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		}
		
		long end = new Date().getTime();
		Date now = new Date();
		WhatyuserLog4j log = new WhatyuserLog4j();
		//log.setIp(ServletActionContext.getRequest().getRemoteAddr());
		log.setBehavior("GeneralServiceImp_reconciliation");	//动作
		log.setNotes("自动对账完成，日期："+preDateStr+" 耗时："+(end-start)+"ms,"+msg);//说明；
		log.setStatus("".equals(msg)?"success":"error");
		log.setOperateTime(now);	//时间
		log.setUserid("定时任务");	//SSO LOGINID
		//log.setLogtype("");
		try {
			this.getQuartzService().save(log);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("自动对账，日志记录异常");
		}
		System.out.println("自动对账完成，日期："+now.toString()+" 耗时："+(end-start)+"ms,"+msg);
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
	public Date getPreSysdate() {//获取系统当前时间的前一天
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
