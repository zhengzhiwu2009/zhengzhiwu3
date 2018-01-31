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


/**
 * 定时任务
 * 
 * @author gaoyuan
 * */

public class QuartzStatisticJobbak extends QuartzJobBean{

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
		//if(ServletUtil.getServletContext() == null){
		//	return;
		//} 
		String sql_date="select to_char(sysdate,'yyyy-mm-dd') as cur_date from dual";   //获取当前日期
		
		
	    //统计当前总的学时数
		String sql=
			"select sum((substr(s.total_time, 1, INSTR(s.total_time, ':', 1, 1) - 1) * 3600 +\n" +
			"           substr(s.total_time, INSTR(s.total_time, ':', 1, 1) + 1, 2) * 60 +\n" + 
			"           substr(s.total_time, INSTR(s.total_time, ':', -1, 1) + 1, 2))) /\n" + 
			"       (3600)\n" + 
			"  from scorm_stu_course s\n" + 
			" where s.total_time not like '%-%'";
		
		try{
			List dateList = this.quartzService.getBySQL(sql_date);
			String cur_date = (String) dateList.get(0);
			
			List timeList=this.quartzService.getBySQL(sql);
			BigDecimal ftime=(BigDecimal) timeList.get(0);
			//String total_time = (String) timeList.get(0);
			//double ftime=Double.parseDouble(total_time);
			String stime=ftime.toString();
			System.out.println("stime:"+stime);
			if(stime!=null && stime.indexOf(".")>0)
			{
				stime=stime.substring(0,stime.indexOf(".")+3);
			}
			System.out.println("stime1:"+stime);
			
			//查询该日期下是否已经存在统计数字
			String t_sql="select stat_date from stat_study_totaltime where stat_date=to_date('"+cur_date+"','yyyy-mm-dd')";
			List tList=this.quartzService.getBySQL(t_sql);
			String t_sql1="";
			if(tList.size()>0)    //该日期下已经有统计
			{
				 t_sql1="update stat_study_totaltime set total_time='"+stime+"',update_date=sysdate where stat_date=to_date('"+cur_date+"','yyyy-mm-dd')";
			}
			else{                 //该日期下没有统计
				 t_sql1="insert into stat_study_totaltime(stat_date,total_time,update_date) values(to_date('"+cur_date+"','yyyy-mm-dd'),'"+stime+"',sysdate)";
			}
			
			System.out.println("t_sql1:"+t_sql1);
			
			if(t_sql1!=null && !t_sql1.equals(""))
			{
				this.quartzService.executeBySQL(t_sql1);
			}
		}catch(Exception e){
			System.out.println("定时统计更新失败！！"+e.toString());
		}
		
		exeStatStudy();
		
		System.out.println("定时统计成功！！");
	}
	
	public void exeStatStudy(){
		dbpool db = new dbpool();
		MyResultSet rs=null;
		String sql="select id from pe_bzz_batch";
		rs=db.executeQuery(sql);
		
		sql="delete from stat_study_summary";
		db.executeUpdate(sql);
		
		String batch_id="";
		try{
			while(rs!=null && rs.next())
			{
				batch_id=rs.getString("id");
				if(batch_id!=null && !batch_id.equals(""))
				{
					this.setBatch(batch_id);
					exeStatByBatch();
				}
			} 
		}catch(Exception e) {e.printStackTrace();} finally{
			db.close(rs);
		}
	}
	public void exeStatByBatch(){
		String sql = "";
		String priEntIds = "";
    	String priSql = "";
    	
    	//针对2010春季学员，企业管理概览和企业管理概览（新），班组安全管理和班组安全管理（新）中选取进度高的一门，保证基础课共16门
    	String sql_training2="(select s.id,s.course_id,s.percent,s.learn_status,s.student_id from training_course_student s,pe_bzz_student bs where s.course_id in (select id from pr_bzz_tch_opencourse where fk_batch_id='"+this.getBatch()+
		"' and FLAG_COURSE_TYPE='402880f32200c249012200c780c40001' ) and bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"+this.getBatch()+"' ) ";
		if(this.getBatch().equals("ff8080812824ae6f012824b0a89e0008")){
			sql_training2="("+
			"select s.id,s.course_id,s.percent,s.learn_status,s.student_id from training_course_student s,pe_bzz_student bs where s.course_id in (select id from pr_bzz_tch_opencourse where fk_batch_id='ff8080812824ae6f012824b0a89e0008' and FLAG_COURSE_TYPE='402880f32200c249012200c780c40001' ) and bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and (s.course_id <> 'ff8080812910e7e601291150ddc70419' and s.course_id <>'ff8080812bf5c39a012bf6a1bab80820' and s.course_id <>'ff8080812910e7e601291150ddc70413' and s.course_id <>'ff8080812bf5c39a012bf6a1baba0821') " + 
			"union\n" + 
			"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
			"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
			"select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812910e7e601291150ddc70419' )a,\n" + 
			"(select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812bf5c39a012bf6a1bab80820' )b\n" + 
			"where a.student_id=b.student_id \n" + 
			"union\n" + 
			"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
			"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
			"select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812910e7e601291150ddc70413' )a,\n" + 
			"(select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812bf5c39a012bf6a1baba0821' )b\n" + 
			"where a.student_id=b.student_id"+
			")  ";
		}
		
		String studentSql = "select stu_id,sso_id,login_num,percent from (select stu_id,sso_id,login_num, to_char((nvl(sum(mtime), 0) / nvl(sum(ttime), 0))*100,'990.9') as percent\n" + 
		"          from (select ps.id as stu_id,u.id as sso_id,u.login_num,btc.time * (tcs.percent / 100) as mtime,btc.time as ttime\n" + 
		"                  from "+sql_training2+" tcs,pr_bzz_tch_opencourse bto,pe_bzz_tch_course btc,pe_bzz_student ps,sso_user u,pe_enterprise pe\n" + 
		"                 where tcs.course_id = bto.id and bto.fk_course_id = btc.id and bto.flag_course_type = '402880f32200c249012200c780c40001' and tcs.student_id = ps.fk_sso_user_id\n" + 
		"                 and ps.fk_sso_user_id = u.id and ps.fk_batch_id='"+this.getBatch()+"'" + priSql + " and u.flag_isvalid = '2' and u.login_num > 0 and ps.fk_enterprise_id=pe.id\n" + 
		"                 and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006') group by stu_id,sso_id,login_num) " ;
		
		//System.out.println("studentSql:"+studentSql);
		dbpool db = new dbpool();
		MyResultSet rs =null;
		/*rs=db.executeQuery(studentSql);
		StringBuilder sb = new StringBuilder(",'");
		StringBuilder sbsso = new StringBuilder(",'");
		int i = 0;
		List<String> sl = new ArrayList();
		List<String> slsso = new ArrayList();
		try {
			while (rs!=null&&rs.next()) {
				i++;
				if(i%800==0) {
					sb.append("'");
					sbsso.append("'");
					String ids = sb.substring(1);
					String ssoIds = sbsso.substring(1);
					sl.add(ids);
					slsso.add(ssoIds);
					sb = new StringBuilder(",'");
					sbsso = new StringBuilder(",'");
				} else {
					sb.append(rs.getString("stu_id")+"','");
					sbsso.append(rs.getString("sso_id")+"','");
				}
				
			}
		} catch(Exception e) {e.printStackTrace();} finally{
			db.close(rs);
		}
		sb.append("'");
		sbsso.append("'");
		
		String ids = sb.substring(1);
		String ssoIds = sbsso.substring(1);
		sl.add(ids);
		slsso.add(ssoIds);*/
		
		
		String sql_hk="";
		String sql_test="";
		 if(this.getBatch().equals("ff8080812253f04f0122540a58000004")||this.getBatch().equals("ff8080812824ae6f012824b0a89e0008")){
			 sql_hk = " and thi.batch_id is null ";
			 sql_test = " and ci.fk_batch_id is null ";
			}
		else{
				sql_hk = " and thi.batch_id='"+this.getBatch()+"'";
				sql_test = " and ci.fk_batch_id='"+this.getBatch()+"' ";
			}
		
		 String timesql =  "select \n" +
		 "       ts.student_id as sso_id,\n" + 
		 "       to_char(sum((ts.percent * tc.time / 100)),'990.9') as finished_ctime, sum(tc.time) as total_ctime\n" + 
		 "  from "+sql_training2+" ts,\n" + 
		 "       pe_bzz_tch_course       tc,\n" + 
		 "       pr_bzz_tch_opencourse   pto\n" + 
		 " where ts.course_id = pto.id\n" + 
		 "   and pto.fk_course_id = tc.id\n" + 
		 "   and pto.fk_batch_id='"+this.getBatch()+"'\n" + 
		 " group by ts.student_id";
		//TODO 
		 String hwsql = 
			 "select sso_id, stu_id,  sum(nvl2(b.id, 1, 0)) as finished_hw,sum(1) as total_hw "+
			 "  from (select thi.id             as hm_id,                            "+
			 "               pbs.fk_sso_user_id as sso_id,                           "+
			 "               pbs.id             as stu_id                            "+
			 "          from test_homeworkpaper_info thi,                            "+
			 "               "+sql_training2+" tcs,                            "+
			 "               pe_bzz_student          pbs,                            "+
			 "               pr_bzz_tch_opencourse   pbto                            "+
			 "         where tcs.course_id = pbto.id                                 "+
			 "           and pbto.fk_course_id = thi.group_id                        "+
			 "           and pbs.fk_batch_id='"+this.getBatch()+"'                    "+
			 "           and tcs.student_id = pbs.fk_sso_user_id and pbto.fk_batch_id=pbs.fk_batch_id"+sql_hk+") a,                            "+
			 "       (select testpaper_id, t_user_id, thh.id                             "+
			 "          from test_homeworkpaper_history thh,pe_bzz_student pbs         "+
			 "         where pbs.fk_batch_id='"+this.getBatch()+"' and pbs.id=thh.t_user_id) b                                    "+
			 " where a.hm_id = b.testpaper_id(+)                                     "+
			 "   and a.stu_id = b.t_user_id(+)	group by sso_id, stu_id									";
		 //TODO 
		 String tpsql = 
			 "select sso_id, stu_id, sum(nvl2(b.id, 1, 0)) as finished_test,sum(1) as total_test \n" +
			 "  from (select thi.id             as hm_id,\n" + 
			 "               pbs.fk_sso_user_id as sso_id,\n" + 
			 "               pbs.id             as stu_id\n" + 
			 "          from test_testpaper_info     thi,\n" + 
			 "               onlinetest_course_info  ci,\n" + 
			 "               onlinetest_course_paper cp,\n" + 
			 "               "+sql_training2+" tcs,\n" + 
			 "               pe_bzz_student          pbs,\n" + 
			 "               pr_bzz_tch_opencourse   pbto\n" + 
			 "         where tcs.course_id = pbto.id\n" + 
			 "           and pbto.fk_course_id = thi.group_id\n" + 
			 "           and pbs.fk_batch_id='"+this.getBatch()+"'\n" + 
			 "           and tcs.student_id = pbs.fk_sso_user_id\n" + 
			 "           and thi.id = cp.paper_id\n" + 
			 "           and cp.testcourse_id = ci.id\n" + 
			 "           and ci.group_id = pbto.fk_course_id and pbto.fk_batch_id=pbs.fk_batch_id "+sql_test+") a,\n" + 
			 "       test_testpaper_history b\n" + 
			 " where a.hm_id = b.testpaper_id(+)\n" + 
			 "   and a.stu_id = b.t_user_id(+)\n" + 
			 " group by sso_id, stu_id";
		//TODO 
		String asssql = 
			"select a.sso_id, sum(done) as finished_assess, sum(1) as total_assess\n" +
			"  from (select distinct a.student_id as sso_id,\n" + 
			"                        a.course_id,\n" + 
			"                        nvl2(b.fk_course_id, 1, 0) as done\n" + 
			"          from "+sql_training2+" a, pe_bzz_assess b\n" + 
			"         where a.course_id = b.fk_course_id(+)\n" + 
			"           and a.student_id = b.fk_student_id(+)\n" + 
			"            ) a\n" + 
			" group by a.sso_id";
		String stusql = "select a.fk_sso_user_id as sso_id,a.id as student_id,a.true_name as name,a.reg_no,a.mobile_phone,a.phone,a.photo,b.name as ename,b.id as erji_id,b.fk_parent_id as yiji_id from pe_bzz_student a,pe_enterprise b where a.fk_enterprise_id = b.id and a.fk_batch_id='"+this.getBatch()+"'";
		String allsql = "select e.sso_id,e.student_id,e.name,e.reg_no,e.mobile_phone,e.phone,e.photo,e.ename,e.erji_id,e.yiji_id,a.finished_ctime,a.total_ctime,b.finished_hw,b.total_hw,"+
		" c.finished_test,c.total_test,d.finished_assess,d.total_assess,f.percent,f.login_num from ("+timesql+") a, ("+hwsql+") b, ("+tpsql+") c,("+asssql+") d,("+stusql+") e,("+studentSql+") f where f.sso_id=a.sso_id(+) and f.sso_id=b.sso_id(+) and f.sso_id=c.sso_id and f.sso_id=d.sso_id(+) and f.sso_id=e.sso_id(+)";
		//System.out.println(allsql);
		//if(true) return;
		
		 List sqlList=new ArrayList();
		try {
			rs=db.executeQuery(allsql);
			while(rs!=null && rs.next())
			{
				String sso_id=rs.getString("sso_id");
				String student_id=rs.getString("student_id");
				String name=rs.getString("name");
				String reg_no=rs.getString("reg_no");
				String mobile_phone=fixnull(rs.getString("mobile_phone"));
				String phone=fixnull(rs.getString("phone"));
				String yiji_id=rs.getString("yiji_id");
				String erji_id=rs.getString("erji_id");
				String erji_name=rs.getString("ename");
				String finished_ctime=rs.getString("finished_ctime");
				String total_ctime=rs.getString("total_ctime");
				String finished_hw=rs.getString("finished_hw");
				String total_hw=rs.getString("total_hw");
				String finished_test=rs.getString("finished_test");
				String total_test=rs.getString("total_test");
				String finished_assess=rs.getString("finished_assess");
				String total_assess=rs.getString("total_assess");
				String percent=rs.getString("percent");
				String login_num=rs.getString("login_num");
				String photo=fixnull(rs.getString("photo"));
			//	String sql_delete="delete from STAT_STUDY_SUMMARY";
			//	db.executeUpdate(sql_delete);
				String sql_insert=
					"insert into STAT_STUDY_SUMMARY\n" +
					"  (id,\n" + 
					"   sso_id,\n" + 
					"   student_id,\n" + 
					"   name,\n" + 
					"   reg_no,\n" + 
					"   mobile_phone,\n" + 
					"   phone,\n" + 
					"   yiji_id,\n" + 
					"   erji_id,\n" + 
					"   erji_name,\n" + 
					"   total_ctime,\n" + 
					"   finished_ctime,\n" + 
					"   total_hw,\n" + 
					"   finished_hw,\n" + 
					"   finished_test,\n" + 
					"   total_test,\n" + 
					"   finished_assess,\n" + 
					"   total_assess,\n" + 
					"   percent,login_num,photo,batch_id)\n" + 
					"values"+
					"(to_char(S_STAT_STUDY_SUMMARY_ID.nextval),'"+
					sso_id+"','"+student_id+"','"+name+"','"+reg_no+"','"+
					mobile_phone+"','"+phone+"','"+yiji_id+"','"+erji_id+"','"+
					erji_name+"','"+total_ctime+"','"+finished_ctime+"','"+
					total_hw+"','"+finished_hw+"','"+finished_test+"','"+
					total_test+"','"+finished_assess+"','"+total_assess+
					"','"+percent+"','"+login_num+"','"+photo+"','"+this.getBatch()+"')";
				//sqlList.add(sql_insert);	
				db.executeUpdate(sql_insert);
				System.out.println("sql_insert:"+sql_insert);
				
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch(Exception e) {e.printStackTrace();} finally{
			db.close(rs);
		}
	/*	int k=db.executeUpdateBatch(sqlList);
        if(k<1)
        {
        	System.out.println("定时统计更新学习相关失败！！");
        }
        else{
        	System.out.println("定时统计更新学习成功！！");
        }*/
	}
	
	public String getsqlid (String cname,List<String> ids) {
		String sql = cname + " in ('')";
		for(String id : ids) {
			sql +=  " or " + cname + " in ("+id+")";
		}
		return sql;
	}
	
	/*public static void main(String[] args) {
		QuartzStatisticJob s = new QuartzStatisticJob();
		s.setBatch("ff8080812824ae6f012824b0a89e0008");
		s.exeStatByBatch();
		//s.exeStatStudy();
	}*/
	
	public String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}

}
