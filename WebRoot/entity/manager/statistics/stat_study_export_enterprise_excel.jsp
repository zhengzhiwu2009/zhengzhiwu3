<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%response.setHeader("Content-Disposition", "attachment;filename=stat_study_export_enterprise1_excel.xls"); %>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.*,java.text.SimpleDateFormat"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
	
	String getCurDate() 
	{
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
  		return dateformat1.format(new Date());
	}
	
	private String getPercent(double num)
    {      
        DecimalFormat df = new DecimalFormat("##0.00 %");

        return df.format(num);
    }
    
    private String getNumber(double num)
    {      
    	if(num == 0) {
    		return "0.000";
    	}
        DecimalFormat df = new DecimalFormat("##0.000");

        return df.format(num);
    }
%>
<%
	String batch_id = fixnull(request.getParameter("batch_id"));
	String tagEnt = fixnull(request.getParameter("tagEnt"));
	
        String sql_con="";
		//if(!search.equals(""))
			//sql_con=" and b.id='"+search+"'";
		
		if (!batch_id.equals("")) {
			sql_con = " and s.fk_batch_id='" + batch_id + "'";
		}
		
		dbpool db = new dbpool();
		MyResultSet rs = null;
		
		String batchName = "";
		String sql = "select batch.name as bname from pe_bzz_batch batch where batch.id='"+batch_id+"'";
		rs = db.executeQuery(sql);
		
		if(rs.next()) {
			batchName = rs.getString("bname");
		}
		db.close(rs);
		
		//查询所选学期的基础课程学时数和总学时数2011-11-10mhy
		String baseTime = "";	//基础学时数
		String base_sql = "select sum(b.time) as baseTime from pr_bzz_tch_opencourse a,pe_bzz_tch_course b "+
			"where a.fk_course_id=b.id and a.fk_batch_id='"+batch_id+"' and a.flag_course_type='402880f32200c249012200c780c40001' group by a.fk_batch_id";
		rs = db.executeQuery(base_sql);
		if(rs.next()) {
			baseTime = rs.getString("baseTime");
		}
		db.close(rs);
		String totalTime = "";	//总学时数
		String total_sql = "select sum(b.time) as totalTime from pr_bzz_tch_opencourse a,pe_bzz_tch_course b "+
			"where a.fk_course_id=b.id and a.fk_batch_id='"+batch_id+"' group by a.fk_batch_id";
		rs = db.executeQuery(total_sql);
		if(rs.next()){
			totalTime = rs.getString("totalTime");
		}
		db.close(rs);
		//end
		
		if(tagEnt == null || tagEnt.equals("1")) {
			sql = "select a.*,b.logined,c.basecmp,d.allcmp,e.xs,f.hwcmp,g.zccmp from \n"
					+ "--学员总人数 \n"
					+ "(select code, name, count(id) as zs \n"
					+ "  from (select s.id, e.name, e.code \n"
					+ "          from pe_bzz_student s, pe_enterprise e,sso_user su \n"
	 				+ "        where e.fk_parent_id is null \n"
					+ "           and s.fk_enterprise_id = e.id \n"
					+ "           and su.id=s.fk_sso_user_id and su.flag_isvalid='2' \n"
					+ "           and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "           " + sql_con + " \n"
					+ "        union \n"
					+ "        select s.id, e.name, e.code \n"
					+ "          from pe_bzz_student s, pe_enterprise e, pe_enterprise e1,sso_user su \n"
					+ "         where e.fk_parent_id is null \n"
					+ "           and e1.fk_parent_id = e.id \n"
					+ "           and s.fk_enterprise_id = e1.id \n"
					+ "           and su.id=s.fk_sso_user_id and su.flag_isvalid='2' \n"
					+ "           and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "           " + sql_con + " \n"
					+ "           ) \n"
	 				+ "group by code, name) a, \n"
					+ "--已登录人数 \n"
					+ "( select e.code,e.name,nvl(num,0) as logined from ( \n"
					+ "  select code, name, count(id) as num \n"
					+ "  from (select s.id, e.name, e.code \n"
					+ "          from pe_bzz_student s, pe_enterprise e,sso_user su \n"
					+ "         where e.fk_parent_id is null \n"
					+ "           and s.fk_enterprise_id = e.id \n"
					+ "           and su.id=s.fk_sso_user_id and su.login_num is not null and su.login_num>0 and su.flag_isvalid='2' \n"
					+ "           and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "           " + sql_con + " \n"
					+ "        union \n"
					+ "        select s.id, e.name, e.code \n"
					+ "          from pe_bzz_student s, pe_enterprise e, pe_enterprise e1,sso_user su \n"
					+ "         where e.fk_parent_id is null \n"
					+ "           and e1.fk_parent_id = e.id \n"
					+ "           and s.fk_enterprise_id = e1.id \n"
					+ "           and su.id=s.fk_sso_user_id and su.login_num is not null and su.login_num>0 and su.flag_isvalid='2' \n"
					+ "           and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "           " + sql_con + " \n"
					+ "         ) \n"
					+ "           group by code ,name) a, \n"
					+ "           (select code,name from pe_enterprise e where e.fk_parent_id is null ) e where e.code=a.code(+) order by e.code) b, \n"
					+ "--基础课已完成人数 \n"           
					+ "( select e.code,e.name,count(b.id) as basecmp from (select code,name from pe_enterprise e where e.fk_parent_id is null) e ,( \n"
					+ "   select code,id from ( \n"
					+ "    select s.id,pe1.code,sum(ce.time * (ts.percent / 100)) as stime \n"
					+ "    from sso_user                su, \n"
					+ "       training_course_student ts, \n"
					+ "       pe_bzz_student          s, \n"
					+ "       pr_bzz_tch_opencourse   co, \n"
					+ "       pe_bzz_tch_course       ce, \n"
					+ "       pe_enterprise           pe,pe_enterprise pe1 \n"
					+ "     where s.fk_sso_user_id = su.id \n"
					+ "     and su.id = ts.student_id and su.flag_isvalid='2' \n"
					+ "     and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "     and pe.id = s.fk_enterprise_id \n"
					+ "     and co.id = ts.course_id \n"
					+ "     and co.fk_course_id = ce.id \n"
					+ "     and s.fk_batch_id = co.fk_batch_id and pe1.fk_parent_id is null and pe.fk_parent_id=pe1.id \n"
					+ "     " + sql_con + " \n"
					+ "     and ts.percent=100 and co.flag_course_type='402880f32200c249012200c780c40001' group by s.id,pe1.code order by stime desc ) where stime='"+baseTime+"') b where b.code(+)=e.code group by e.code,e.name order by e.code) c, \n"
					+ "--所有课程已完成人数 \n"
					+ "(select e.code,e.name,count(b.id) as allcmp from (select code,name from pe_enterprise e where e.fk_parent_id is null) e ,( \n"
					+ "  select code,id from ( \n"
					+ "   select s.id,pe1.code,sum(ce.time * (ts.percent / 100)) as stime \n"
					+ "   from sso_user                su, \n"
					+ "       training_course_student ts, \n"
					+ "       pe_bzz_student          s, \n"
					+ "       pr_bzz_tch_opencourse   co, \n"
					+ "       pe_bzz_tch_course       ce, \n"
					+ "       pe_enterprise           pe,pe_enterprise pe1 \n"
					+ "   where s.fk_sso_user_id = su.id \n"
					+ "     and su.id = ts.student_id and su.flag_isvalid='2' \n"
					+ "     and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "     and pe.id = s.fk_enterprise_id \n"
					+ "     and co.id = ts.course_id \n"
					+ "     and co.fk_course_id = ce.id \n"
					+ "     and s.fk_batch_id = co.fk_batch_id and pe1.fk_parent_id is null and pe.fk_parent_id=pe1.id \n"
					+ "     " + sql_con + " \n"
					+ "   and ts.percent=100 group by s.id,pe1.code order by stime desc ) where stime='"+totalTime+"') b where b.code(+)=e.code group by e.code,e.name order by e.code) d, \n"       
					+ "--总学时数 \n"       
					+ "(select e.code,e.name,nvl(sum(stime),0) as xs from (select code,name from pe_enterprise e where e.fk_parent_id is null) e ,( \n"
					+ "  select code,stime from ( \n"
					+ "   select s.id,pe1.code,sum(ce.time * (ts.percent / 100)) as stime \n"
					+ "   from sso_user                su, \n"
					+ "       training_course_student ts, \n"
					+ "       pe_bzz_student          s, \n"
					+ "       pr_bzz_tch_opencourse   co, \n"
					+ "       pe_bzz_tch_course       ce, \n"
					+ "       pe_enterprise           pe,pe_enterprise pe1 \n"
					+ "   where s.fk_sso_user_id = su.id \n"
					+ "     and su.id = ts.student_id and su.flag_isvalid='2' \n"
					+ "     and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "     and pe.id = s.fk_enterprise_id \n"
					+ "     and co.id = ts.course_id \n"
					+ "     and co.fk_course_id = ce.id \n"
					+ "     and s.fk_batch_id = co.fk_batch_id and pe1.fk_parent_id is null and pe.fk_parent_id=pe1.id \n"
					+ "     " + sql_con + " \n"
					+ "   group by s.id,pe1.code order by stime desc ) ) b where b.code(+)=e.code group by e.code,e.name order by e.code) e , \n"   
					+ "--作业完成门次 \n"   
					+ "(select e.code,e.name,nvl(num,0) as hwcmp from (select code,name from pe_enterprise e where e.fk_parent_id is null) e,( \n"
					+ "  select code,s_name,count(tc_name) as num from ( \n"
					+ "   select distinct s.id ,s.code,s.name as s_name,tc.name as tc_name from ( \n"
					+ "    select t.t_user_id as user_id,t.testpaper_id from test_homeworkpaper_history t ) a, \n"
					+ "    (select s.id,e.code,e.name \n" 
					+ "     from pe_bzz_student s,pe_enterprise e ,sso_user su \n"
					+ "     where e.fk_parent_id is null and e.id=s.fk_enterprise_id \n" 
					+ "           and s.fk_sso_user_id=su.id and su.flag_isvalid='2' \n"
					+ "           and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "           " + sql_con + " \n"
					+ "    union \n"
					+ "    select s.id,e.code,e.name \n" 
					+ "    from pe_bzz_student s,pe_enterprise e,pe_enterprise e1,sso_user su \n" 
					+ "    where e.fk_parent_id is null and e1.fk_parent_id=e.id and e1.id=s.fk_enterprise_id \n" 
					+ "          and s.fk_sso_user_id=su.id and su.flag_isvalid='2' \n"
					+ "          and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "          " + sql_con + " \n"
					+ "        ) s, \n"
					+ "    test_homeworkpaper_info h ,pe_bzz_tch_course tc \n"
					+ "    where s.id=a.user_id and h.id=a.testpaper_id and h.group_id=tc.id ) group by code,s_name) b where b.code(+)=e.code order by e.code) f, \n"
					+ "--自测完成门数 \n"
					+ "(select e.code,e.name,nvl(num,0) as zccmp from (select code,name from pe_enterprise e where e.fk_parent_id is null) e,( \n"
					+ "  select code,s_name,count(tc_name) as num from ( \n"
					+ "   select distinct s.id ,s.code,s.name as s_name,tc.name as tc_name from ( \n"
					+ "    select substr(t.user_id,2,32) as user_id,t.testpaper_id,t.score from test_testpaper_history t where t.score>'59' ) a, \n"
					+ "    (select s.id,e.code,e.name  \n"
					+ "     from pe_bzz_student s,pe_enterprise e,sso_user su \n" 
					+ "     where e.fk_parent_id is null and e.id=s.fk_enterprise_id \n"
					+ "     and s.fk_sso_user_id=su.id and su.flag_isvalid='2' \n"
					+ "     and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "     " + sql_con + " \n"
					+ "     union \n"
					+ "     select s.id,e.code,e.name from pe_bzz_student s,pe_enterprise e,pe_enterprise e1,sso_user su \n"
					+ "     where e.fk_parent_id is null and e1.fk_parent_id=e.id and e1.id=s.fk_enterprise_id \n"
					+ "         and s.fk_sso_user_id=su.id and su.flag_isvalid='2' \n"
					+ "         and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "         " + sql_con + " \n"
					+ "    ) s,test_testpaper_info h ,pe_bzz_tch_course tc \n"
					+ "  where s.id=a.user_id and h.id=a.testpaper_id and h.group_id=tc.id ) group by code,s_name) b where b.code(+)=e.code order by e.code ) g \n"  
	       
					+ "where a.code = b.code and a.code=c.code and a.code=d.code and a.code=e.code and a.code=f.code and a.code=g.code \n"
					+ "order by a.code";
		} else {
			sql = "select a.*,b.logined,c.basecmp,d.allcmp,e.xs,f.hwcmp,g.zccmp from \n"
					+ "--学员总人数 \n"
					+ "(select code, name, count(id) as zs \n"
					+ "  from ( \n"
					+ "        select s.id, e.name, e.code \n"
					+ "          from pe_bzz_student s, pe_enterprise e,sso_user su \n"
					+ "         where e.fk_parent_id is not null \n"
					+ "           and s.fk_enterprise_id(+) = e.id \n"
					+ "           and s.fk_sso_user_id=su.id and su.flag_isvalid='2' \n"
					+ "           and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "          " + sql_con + " \n" 
					+ "           ) \n"
					+ " group by code, name) a, \n"
					+ "--已登录人数 \n"
					+ "(select e.code,e.name,nvl(num,0) as logined from ( \n"
					+ "select code, name, count(id) as num \n"
					+ "  from ( \n"
					+ "        select s.id, e.name, e.code \n"
					+ "          from pe_bzz_student s, pe_enterprise e,sso_user su \n"
					+ "         where e.fk_parent_id is not null \n"
					+ "           and s.fk_enterprise_id = e.id \n"
					+ "           and su.id=s.fk_sso_user_id and su.login_num is not null and su.login_num>0 and su.flag_isvalid='2' \n"
					+ "           and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
		    		+ "          " + sql_con + " \n" 
					+ "         ) \n"
					+ "           group by code ,name) a, \n"
					+ "           (select code,name from pe_enterprise e where e.fk_parent_id is not null ) e where e.code=a.code(+) order by e.code) b, \n"
					+ "--基础课已学完总人数 \n"
					+ "(select e.code,e.name,count(b.id) as basecmp from (select code,name from pe_enterprise e where e.fk_parent_id is not null) e ,( \n"
					+ "  select code,id from ( \n"
					+ "   select s.id,pe.code,sum(ce.time * (ts.percent / 100)) as stime \n"
					+ "   from sso_user                su, \n"
					+ "       training_course_student ts, \n"
					+ "       pe_bzz_student          s, \n"
					+ "       pr_bzz_tch_opencourse   co, \n"
					+ "       pe_bzz_tch_course       ce, \n"
					+ "       pe_enterprise           pe \n"
					+ "   where s.fk_sso_user_id = su.id \n"
					+ "     and su.id = ts.student_id and su.flag_isvalid='2' \n"
					+ "     and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "     and pe.id = s.fk_enterprise_id \n"
					+ "     and co.id = ts.course_id \n"
					+ "     and co.fk_course_id = ce.id \n"
					+ "     and s.fk_batch_id = co.fk_batch_id and pe.fk_parent_id is not null \n"
					+ "    " + sql_con + " \n" 
					+ "     and ts.percent=100 and co.flag_course_type='402880f32200c249012200c780c40001' group by s.id,pe.code order by stime desc ) where stime=78) b where b.code(+)=e.code group by e.code,e.name) c, \n" 
					+ "--所有课程均已学完人数 \n"
					+ "(select e.code,e.name,count(b.id) as allcmp from (select code,name from pe_enterprise e where e.fk_parent_id is not null) e ,( \n"
					+ "  select code,id from ( \n"
					+ "   select s.id,pe.code,sum(ce.time * (ts.percent / 100)) as stime \n"
					+ "   from sso_user                su, \n"
					+ "       training_course_student ts, \n"
					+ "       pe_bzz_student          s, \n"
					+ "       pr_bzz_tch_opencourse   co, \n"
					+ "       pe_bzz_tch_course       ce, \n"
					+ "       pe_enterprise           pe \n"
					+ "   where s.fk_sso_user_id = su.id \n"
					+ "     and su.id = ts.student_id and su.flag_isvalid='2' \n"
					+ "     and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "     and pe.id = s.fk_enterprise_id \n"
					+ "     and co.id = ts.course_id \n"
					+ "     and co.fk_course_id = ce.id \n"
					+ "     and s.fk_batch_id = co.fk_batch_id and pe.fk_parent_id is not null \n"
					+ "    " + sql_con + " \n" 
					+ "     and ts.percent=100 group by s.id,pe.code order by stime desc ) where stime=150) b where b.code(+)=e.code group by e.code,e.name order by e.code) d, \n"
					+ "--总学时数 \n"
					+ "(select e.code,e.name,nvl(sum(stime),0) as xs from (select code,name from pe_enterprise e where e.fk_parent_id is not null) e ,( \n"
					+ "  select code,stime from ( \n"
					+ "   select s.id,pe.code,sum(ce.time * (ts.percent / 100)) as stime \n"
					+ "   from sso_user                su, \n"
					+ "       training_course_student ts, \n"
					+ "       pe_bzz_student          s, \n"
					+ "       pr_bzz_tch_opencourse   co, \n"
					+ "       pe_bzz_tch_course       ce, \n"
					+ "       pe_enterprise           pe \n"
					+ "   where s.fk_sso_user_id = su.id \n"
					+ "     and su.id = ts.student_id and su.flag_isvalid='2' \n"
					+ "     and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "     and pe.id = s.fk_enterprise_id \n"
					+ "     and co.id = ts.course_id \n"
					+ "     and co.fk_course_id = ce.id \n"
					+ "     and s.fk_batch_id = co.fk_batch_id \n" 
					+ "    " + sql_con + " \n" 
					+ "  group by s.id,pe.code order by stime desc ) ) b where b.code(+)=e.code group by e.code,e.name order by e.code) e, \n"
					+ "--作业完成门数 \n"
					+ "(select e.code,e.name,nvl(num,0) as hwcmp from (select code,name from pe_enterprise e where e.fk_parent_id is not null) e,( \n"
					+ "  select code,s_name,count(tc_name) as num from ( \n"
					+ "   select distinct s.id ,s.code,s.name as s_name,tc.name as tc_name from ( \n"
					+ "    select t.t_user_id as user_id,t.testpaper_id from test_homeworkpaper_history t ) a, \n"
					+ "    (select s.id,e.code,e.name \n" 
	   				+ "from pe_bzz_student s,pe_enterprise e ,sso_user su \n"
					+ "     where e.fk_parent_id is not null and e.id=s.fk_enterprise_id \n" 
					+ "       and s.fk_sso_user_id=su.id and su.flag_isvalid='2' \n"
					+ "       and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "      " + sql_con + " \n" 
					+ "       ) s,test_homeworkpaper_info h ,pe_bzz_tch_course tc \n"
					+ " where s.id=a.user_id and h.id=a.testpaper_id and h.group_id=tc.id ) group by code,s_name) b where b.code(+)=e.code order by e.code) f, \n"
					+ "--自测完成门数 \n"
					+ "(select e.code,e.name,nvl(num,0) as zccmp from (select code,name from pe_enterprise e where e.fk_parent_id is not null) e,( \n"
					+ "  select code,s_name,count(tc_name) as num from ( \n"
					+ "   select distinct s.id ,s.code,s.name as s_name,tc.name as tc_name from ( \n"
					+ "    select substr(t.user_id,2,32) as user_id,t.testpaper_id from test_testpaper_history t where t.score>'59' ) a, \n"
					+ "    (select s.id,e.code,e.name \n" 
					+ "     from pe_bzz_student s,pe_enterprise e,sso_user su \n" 
					+ "     where e.fk_parent_id is not null and e.id=s.fk_enterprise_id \n"
					+ "       and s.fk_sso_user_id=su.id and su.flag_isvalid='2' \n"
					+ "       and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
					+ "      " + sql_con + " \n" 
					+ "       ) s, \n"
					+ " test_testpaper_info h ,pe_bzz_tch_course tc \n"
					+ " where s.id=a.user_id and h.id=a.testpaper_id and h.group_id=tc.id ) group by code,s_name) b where b.code(+)=e.code order by e.code) g \n"
	 
					+ "where a.code=b.code and a.code=c.code and a.code=d.code and a.code=e.code and a.code=f.code and a.code=g.code \n"
					+ "order by a.code";
				}
		//System.out.println("----" + tagEnt + "-----" + sql);
 %>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="表一(新).files/filelist.xml">
<link rel=Edit-Time-Data href="表一(新).files/editdata.mso">
<link rel=OLE-Object-Data href="表一(新).files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>微软用户</o:Author>
  <o:LastAuthor>China User</o:LastAuthor>
  <o:Created>2010-04-09T03:59:03Z</o:Created>
  <o:LastSaved>2010-04-29T02:37:57Z</o:LastSaved>
  <o:Company>Whaty</o:Company>
  <o:Version>11.9999</o:Version>
 </o:DocumentProperties>
</xml><![endif]-->
<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:1.0in .75in 1.0in .75in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;}
.font8
	{color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
tr
	{mso-height-source:auto;
	mso-ruby-visibility:none;}
col
	{mso-width-source:auto;
	mso-ruby-visibility:none;}
br
	{mso-data-placement:same-cell;}
.style0
	{mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	white-space:nowrap;
	mso-rotate:0;
	mso-background-source:auto;
	mso-pattern:auto;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;
	mso-style-name:常规;
	mso-style-id:0;}
td
	{mso-style-parent:style0;
	padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:locked visible;
	white-space:nowrap;
	mso-rotate:0;}
.xl24
	{mso-style-parent:style0;
	mso-number-format:"\@";}
.xl25
	{mso-style-parent:style0;
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	font-weight:700;
	mso-number-format:"\@";}
.xl27
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;}
.xl28
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;}
.xl29
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:right;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl30
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl31
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl32
	{mso-style-parent:style0;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl33
	{mso-style-parent:style0;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;
	white-space:normal;}
ruby
	{ruby-align:left;}
rt
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-char-type:none;
	display:none;}
-->
</style>
<!--[if gte mso 9]><xml>
 <x:ExcelWorkbook>
  <x:ExcelWorksheets>
   <x:ExcelWorksheet>
    <x:Name>Sheet1</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:Print>
      <x:ValidPrinterInfo/>
      <x:PaperSizeIndex>9</x:PaperSizeIndex>
      <x:HorizontalResolution>600</x:HorizontalResolution>
      <x:VerticalResolution>200</x:VerticalResolution>
     </x:Print>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>12</x:ActiveRow>
       <x:ActiveCol>8</x:ActiveCol>
       <x:RangeSelection>$I$12:$I$13</x:RangeSelection>
      </x:Pane>
     </x:Panes>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet2</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet3</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
  </x:ExcelWorksheets>
  <x:WindowHeight>10440</x:WindowHeight>
  <x:WindowWidth>21195</x:WindowWidth>
  <x:WindowTopX>240</x:WindowTopX>
  <x:WindowTopY>360</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple class=xl24>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1293 style='border-collapse:
 collapse;table-layout:fixed;width:971pt'>
 <col class=xl24 width=72 style='width:54pt'>
 <col class=xl24 width=262 style='mso-width-source:userset;mso-width-alt:8384;
 width:197pt'>
 <col class=xl24 width=108 style='mso-width-source:userset;mso-width-alt:3456;
 width:81pt'>
 <col class=xl24 width=76 style='mso-width-source:userset;mso-width-alt:2432;
 width:57pt'>
 <col class=xl24 width=99 span=2 style='mso-width-source:userset;mso-width-alt:
 3168;width:74pt'>
 <col class=xl24 width=104 style='mso-width-source:userset;mso-width-alt:3328;
 width:78pt'>
 <col class=xl24 width=86 style='mso-width-source:userset;mso-width-alt:2752;
 width:65pt'>
 <col class=xl24 width=97 style='mso-width-source:userset;mso-width-alt:3104;
 width:73pt'>
 <col class=xl24 width=85 style='mso-width-source:userset;mso-width-alt:2720;
 width:64pt'>
 <col class=xl24 width=112 style='mso-width-source:userset;mso-width-alt:3584;
 width:84pt'>
 <col class=xl24 width=93 style='mso-width-source:userset;mso-width-alt:2976;
 width:70pt'>
 <tr height=47 style='mso-height-source:userset;height:35.25pt'>
  <td colspan=12 height=47 class=xl27 width=1293 style='height:35.25pt;
  width:971pt'><%if(tagEnt==null || tagEnt.equals("1")) {%>一级企业<%} else {%>二级企业<%}%> <%=batchName%> 学习状态表</td>
 </tr>
 <tr height=29 style='mso-height-source:userset;height:21.75pt'>
  <td colspan=12 height=29 class=xl29 style='height:21.75pt'>导出时间：<font
  class="font8"><%=getCurDate()%></font></td>
 </tr>
 <tr class=xl26 height=49 style='mso-height-source:userset;height:36.75pt'>
  <td height=49 class=xl32 style='height:36.75pt;border-top:none'>企业编码</td>
  <td class=xl32 style='border-top:none;border-left:none'><%if(tagEnt==null || tagEnt.equals("1")) {%>一级企业名称<%} else {%>二级企业名称<%}%></td>
  <td class=xl32 style='border-top:none;border-left:none'>学员总人数</td>
  <td class=xl33 width=76 style='border-top:none;border-left:none;width:57pt'>已登录学员人数</td>
  <td class=xl32 style='border-top:none;border-left:none'>学员登录率</td>
  <td class=xl33 width=99 style='border-top:none;border-left:none;width:74pt'>基础课已学完人数</td>
  <td class=xl33 width=104 style='border-top:none;border-left:none;width:78pt'>所有课程已完成人数</td>
  <td class=xl32 style='border-top:none;border-left:none'>总学时数</td>
  <td class=xl32 style='border-top:none;border-left:none'>人均学时数</td>
  <td class=xl33 width=85 style='border-top:none;border-left:none;width:64pt'>作业完成门次</td>
  <td class=xl33 width=112 style='border-top:none;border-left:none;width:84pt'>人均完成作业门数</td>
  <td class=xl33 width=93 style='border-top:none;border-left:none;width:70pt'>自测完成门数</td>
 </tr>
 <%
 	rs = db.executeQuery(sql);
 	int countZS=0;
	int countLogined=0;	
	double countLoginRage = 0;
	int countBaseCmp=0;
	int countAllCmp=0;
	int countXS=0;
	double countPerCapitaXS = 0;
	int countHWCmp=0;
	double countPerCapitaHWCmp = 0;
	int countZCCmp=0;
	while(rs.next()) {
		String fCode = fixnull(rs.getString("code"));
		String fName = fixnull(rs.getString("name"));
		int fZS = rs.getInt("zs");
		int fLogined = rs.getInt("logined");
		double fLoginRate= ((double)fLogined / fZS);
		int fBaseCmp = rs.getInt("basecmp");
		int fAllCmp = rs.getInt("allcmp");
		int fXS = rs.getInt("xs");
		double fPerCapitaXS = ((double)fXS / fZS);
		int fHWCmp = rs.getInt("hwcmp");
		double fPerCapitaHWCmp = ((double)fHWCmp / fZS);
		int fZCCmp = rs.getInt("zccmp");
		
		countZS += fZS;
		countLogined += fLogined;
		countLoginRage += fLoginRate;
		countBaseCmp += fBaseCmp;
		countAllCmp += fAllCmp;
		countXS += fXS;
		countPerCapitaXS += fPerCapitaXS;
		countHWCmp += fHWCmp;
		countPerCapitaHWCmp +=fPerCapitaHWCmp;
		countZCCmp += fZCCmp;	
 %>
 <tr height=40 style='mso-height-source:userset;height:30.0pt'>
  <td height=40 class=xl25 style='height:30.0pt;border-top:none'><%=fCode%></td>
  <td class=xl25 style='border-top:none;border-left:none'><%=fName%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=fZS%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=fLogined%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=getPercent(fLoginRate)%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=fBaseCmp%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=fAllCmp%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=fXS%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=getNumber(fPerCapitaXS)%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=fHWCmp%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=getNumber(fPerCapitaHWCmp)%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=fZCCmp%></td>
 </tr>
  <%
 	}
	db.close(rs);
 %>
 <tr height=38 style='mso-height-source:userset;height:28.5pt'>
  <td colspan=2 height=38 class=xl30 style='border-right:.5pt solid black;
  height:28.5pt'>总计:</td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=countZS%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=countLogined%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=countBaseCmp%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=countAllCmp%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=countXS%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=getNumber(countPerCapitaXS)%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=countHWCmp%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=getNumber(countPerCapitaHWCmp)%></td>
  <td class=xl25 style='border-top:none;border-left:none' x:num><%=countZCCmp%></td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=262 style='width:197pt'></td>
  <td width=108 style='width:81pt'></td>
  <td width=76 style='width:57pt'></td>
  <td width=99 style='width:74pt'></td>
  <td width=99 style='width:74pt'></td>
  <td width=104 style='width:78pt'></td>
  <td width=86 style='width:65pt'></td>
  <td width=97 style='width:73pt'></td>
  <td width=85 style='width:64pt'></td>
  <td width=112 style='width:84pt'></td>
  <td width=93 style='width:70pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
 <tr height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl29 style='height:14.25pt'>总计：</td>
  <td class=xl28 style='border-top:none;border-left:none'></td>
  <td class=xl28 style='border-top:none;border-left:none'></td>
  <td class=xl28 style='border-top:none;border-left:none'></td>
  <td class=xl28 style='border-top:none;border-left:none'></td>
  <td class=xl28 style='border-top:none;border-left:none'></td>
  <td class=xl28 style='border-top:none;border-left:none'></td>
  <td class=xl28 style='border-top:none;border-left:none'></td>
 </tr>

