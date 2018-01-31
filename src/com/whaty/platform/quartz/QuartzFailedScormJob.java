package com.whaty.platform.quartz;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.service.cache.scorm.FailedScormService;
import com.whaty.platform.entity.service.quartz.QuartzService;
import com.whaty.platform.standard.scorm.datamodels.SCODataManager;
import com.whaty.platform.standard.scorm.operation.ScormFactory;
import com.whaty.platform.standard.scorm.operation.ScormManage;


/**
 * 定时任务
 * 处理提交失败的scorm节点
 * @author gaoyuan
 * */

public class QuartzFailedScormJob extends QuartzJobBean{

	private QuartzService quartzService;
	
	private FailedScormService failedScormService;
	
    //public static final int DEFAULT_INTERVAL=1;
	

	public FailedScormService getFailedScormService() {
		return failedScormService;
	}

	public void setFailedScormService(FailedScormService failedScormService) {
		this.failedScormService = failedScormService;
	}

	public QuartzService getQuartzService() {
		return quartzService;
	}

	public void setQuartzService(QuartzService quartzService) {
		this.quartzService = quartzService;
	}
	
	public String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
	
	public String getOpenCoursewareId(String courseId,String openCourseId) throws SQLException{
		
		dbpool db=new dbpool();
		MyResultSet rs = null;
		
		String opencoursewareId="";
		String sqlcourse="select ptoc.id as id from pr_tch_opencourse_courseware ptoc, pe_bzz_tch_courseware pbtc" +
		"			where ptoc.fk_courseware_id=pbtc.id" +
		"			  and pbtc.code='" + courseId + "' and ptoc.fk_opencourse_id='" + openCourseId +"'";

		rs = db.executeQuery(sqlcourse);
		if(rs!=null && rs.next())
		{
			opencoursewareId=fixnull(rs.getString("id"));
		}
		db.close(rs);
		return opencoursewareId;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		dbpool db=new dbpool();
		MyResultSet rs = null;
		
		Cache cache=failedScormService.getCache();
//		System.out.println("定时统计开始！！缓存列表大小："+cache.getSize()+"\n");
		List key_list=(List)cache.getKeys();
		
		String t_key="";
		for(int i=0;i<key_list.size();i++)
		{
			try{
				String key=(String)key_list.get(i);
				String key_bk=key;
				t_key=key;
				System.out.println("定时统计过程中！！ key:"+key+"\n");
				
				//从关键字中解析用户ID等信息
				int index=key_bk.indexOf("|");
				String userID=key_bk.substring(0,index);
				key_bk=key_bk.substring(index+1);
				index=key_bk.indexOf("|");
				String courseID=key_bk.substring(0,index);
				key_bk=key_bk.substring(index+1);
				index=key_bk.indexOf("|");
				String scoID=key_bk.substring(0,index);
				String openCourseId=key_bk.substring(index+1);
//				System.out.println("userid:"+userID+"\n");
//				System.out.println("courseID:"+courseID+"\n");
//				System.out.println("scoID:"+scoID+"\n");
//				System.out.println("openCourseId:"+openCourseId+"\n");
				
				//获取key对应的值
				SCODataManager scoData=(SCODataManager)failedScormService.getScormListFromCache(key);
				ScormFactory scormFactory = ScormFactory.getInstance();
				ScormManage scormManage = scormFactory.creatScormManage();
//				System.out.println("scormManage:"+scormManage);
				
				//根据课件ID和开课 ID获取开课与课件对应表id
				String opencoursewareId=getOpenCoursewareId(courseID,openCourseId);
				
				//获取系统中当前节点已经存在信息
				String t_sql="select id,status,total_time from scorm_stu_sco where student_id='"+userID+"' " +
							"and course_id='"+opencoursewareId+"' and system_id='"+scoID+"'";
				
				String status="";
				String t_id="";
				String total_time="00:00:00.0";
				rs=db.executeQuery(t_sql);
				if(rs!=null && rs.next()){
					t_id=fixnull(rs.getString("id"));
					status=fixnull(rs.getString("status"));
					total_time=fixnull(rs.getString("total_time"));
//					System.out.println("status:"+status);
				}
				db.close(rs);
				
				//如果该节点已经完成，需要设置当前scoData中状态为完成状态
				if(status.equals("completed"))
				{
					scoData.getCore().setLessonStatus(status);
				}
				
//				System.out.println("scor_id: "+scoID+",  new total_time>>>>>>>>>:"+scoData.getCore().getTotalTime().getValue());
//				System.out.println("scor_id: "+scoID+",  old total_time>>>>>>>>>:"+total_time);
				//total_time=ScormUtil.TimeAdd(total_time,scoData.getCore().getTotalTime().getValue());
				
				//判断总时间,取最大值
				int first = total_time.indexOf(":");
				String hours = total_time.substring(0,first);
				String munites = total_time.substring(first+1,first+3);
				int total_exist = Integer.parseInt(hours)*60+Integer.parseInt(munites);
				
				String c_total_time=scoData.getCore().getTotalTime().getValue();
				
				int first1 = c_total_time.indexOf(":");
				String hours1 = c_total_time.substring(0,first1);
				String munites1 = c_total_time.substring(first1+1,first1+3);
				int total_current = Integer.parseInt(hours1)*60+Integer.parseInt(munites1);
				
				if(total_exist<total_current)
				{
					scoData.getCore().setTotalTime(c_total_time);
				}
				//判断总时间结束
				
				scormManage.putIntoDB(scoData, userID, courseID, scoID,openCourseId);
				
				System.out.println("当前缓存列表大小>>>>>>>>："+cache.getSize()+"\n");
			}catch(Exception e){
				
			}finally{
				failedScormService.removeScorm(t_key);
			}
		}
		
		cache=failedScormService.getCache();
//		for(int i=0;i<key_list.size();i++)
//		{
//			String key=(String)key_list.get(i);
//			System.out.println("定时统计结束！！ key:"+key+"\n");
//		}
		System.out.println("定时统计结束！！缓存列表大小："+cache.getSize()+"\n");
	}
}
