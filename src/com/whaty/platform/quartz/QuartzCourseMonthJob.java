package com.whaty.platform.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.service.quartz.QuartzService;
import com.whaty.platform.entity.util.MyUtil;

/**
 * 定时统计每个月课程点击次数
 * @author mahaiyuan
 * @since 2012-04-23
 * @version v1.1.1
 *
 */
public class QuartzCourseMonthJob extends QuartzJobBean {

	private QuartzService quartzService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,fk_opencourse_id,fk_course_id,month_click from pe_bzz_tch_course_click";
		try{
			rs = db.executeQuery(sql);
			while(null != rs && rs.next()){
				String fk_click_id = rs.getString("id");
				String fk_opencourse_id = rs.getString("fk_opencourse_id");
				String fk_course_id = rs.getString("fk_course_id");
				Double month_click = rs.getDouble("month_click");
				String sql_insert = "insert into pe_bzz_tch_course_click_month (id, fk_click_id, fk_opencourse_id, fk_course_id, count, start_date, end_date) values "+
							"('"+MyUtil.getUUID()+"','"+fk_click_id+"','"+fk_opencourse_id+"','"+fk_course_id+"',"+month_click+",trunc(sysdate- to_char(sysdate-1,'dd')), trunc(sysdate - 1))";
//				System.out.println("sql_insert " + sql_insert);
				db.executeUpdate(sql_insert);	//写入每月课程点击次数汇总表
			}
			//把当月课程点击次数置0
			String sql_update = "update pe_bzz_tch_course_click set month_click=0";
			System.out.println("sql_update : " + sql_update);
			db.executeUpdate(sql_update);
		}catch(Exception e){
			System.out.println("定时统计每月课程点击次数失败！！"+e.toString());
		}finally{
			db.close(rs);
		}
		System.out.println("定时统计第月课程点击次数成功！！");
	}

	public QuartzService getQuartzService() {
		return quartzService;
	}

	public void setQuartzService(QuartzService quartzService) {
		this.quartzService = quartzService;
	}

}
