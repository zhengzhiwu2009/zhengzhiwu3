<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=stat_erji_enterprise_exam_excel.xls"); %>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
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
%>
<%
	String exambatch_id = fixnull(request.getParameter("exambatch_id"));
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
	
	String search = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("search")),"UTF-8"));
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String exambatch_name="";
	MyResultSet rs_batch = null;
	String sql_enter = "";
	String entername="";
	MyResultSet rs_enter = null;
	
	String sql_batch="select name from pe_bzz_exambatch where id='"+exambatch_id+"'";
	rs_batch = db.executeQuery(sql_batch);
	while(rs_batch!=null&&rs_batch.next())
	{
		exambatch_name = fixnull(rs_batch.getString("name"));
	}
	db.close(rs_batch);
	
	sql_enter="select name from pe_enterprise where id='"+enterprise_id+"'";
	rs_enter = db.executeQuery(sql_enter);
	while(rs_enter!=null&&rs_enter.next())
	{
		entername = fixnull(rs_enter.getString("name"));
	}
	db.close(rs_enter);
	
	
	UserSession us1 = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	List entList=us1.getPriEnterprises();
	String sql_ent="";
	String sql_entbk="";
	for(int i=0;i<entList.size();i++)
	{
		PeEnterprise e=(PeEnterprise)entList.get(i);
		sql_ent+="'"+e.getId()+"',";
	}
	sql_entbk=sql_ent;
	if(!sql_ent.equals(""))
	{
		sql_ent="("+sql_ent.substring(0,sql_ent.length()-1)+")";
	}

	if(us1.getRoleId().equals("2") || us1.getRoleId().equals("402880a92137be1c012137db62100006"))  //表示为一级主管和一级辅导员
	{
		String t_sql="select id from pe_enterprise where fk_parent_id in "+sql_ent;
		rs=db.executeQuery(t_sql);
		while(rs!=null && rs.next())
		{
			sql_entbk+="'"+fixnull(rs.getString("id"))+"',";
		}
		db.close(rs);
		sql_ent="("+sql_entbk.substring(0,sql_entbk.length()-1)+")";
	}
	String sql_con="";
	String sql_con1="";
	String sql_con3="";
	if(us1.getRoleId().equals("3")){
		sql_con3 = "";
	}else{
		sql_con3 = " and be.totalgrade_release='4028809c2d625bcf011d66fd0dda7006'";
	}
	if(!sql_ent.equals(""))
	{
		sql_con+=" and pe.id in "+sql_ent;
		sql_con1=" and bs.fk_enterprise_id in "+sql_ent;
	}

	
		String sql = 
			"select pe.id,\n" +
			"       pe.name,\n" + 
			"       nvl(a.num, '0') as b_total_num,\n" + //考试报名人数
			"       nvl(b.num, '0') as b_uncheck_num,\n" + //考试未审核人数
			"       nvl(c.num, '0') as b_final_num,\n" + //考试已审核人数
			"       nvl(d.num, '0') as h_total_num,\n" + //缓考人数
			"       nvl(e.num, '0') as h_uncheck_num,\n" + //缓考未审核人数
			"       nvl(f.num, '0') as h_first_num,\n" + //缓考已初审人数
			"       nvl(g.num, '0') as h_final_num,\n" + //缓考已终审人数
			"       nvl(h.num, '0') as h_reject_num,\n" +// 缓考已驳回人数
			"       nvl(j.num, '0') as total_num,\n" +//参训总人数
			"       nvl(k.num, '0') as k_num,\n" + //优秀
			"       nvl(l.num, '0') as l_num,\n" + //良好
			"       nvl(m.num, '0') as m_num,\n" + //合格
			"       nvl(n.num, '0') as n_num,\n" +//不合格
			"       nvl(o.num, '0') as o_num,\n" + //缺考
			"       nvl(p.num, '0') as p_num,\n" + //违纪作弊
			"       nvl(q.study_total_num, '0') as study_total_num,\n" + //实际学习人数
			"       replace(to_char(nvl(r.total_time, '0'),'999999999999'),' ','') as total_time,\n" +//总学时
			"       nvl((nvl(j.num, '0') - nvl(a.num, '0') - nvl(d.num, '0')), '0') as n_total_num, \n" + // 考试未报名人数
			"       replace(to_char(nvl(r.total_time,'0')/decode(nvl(j.num,'1'),'0','1',nvl(j.num,'1')),'9999.9'),' ','') as avg_study_time"+//人均学时
			"  from pe_enterprise pe,\n" + 
			"       (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examscore be,\n" + 
			"                       pe_bzz_student   bs,\n" + 
			"                       pe_enterprise    e1,\n" +
			"                       pe_bzz_exambatch    pbe\n" +  
			"                 where be.student_id = bs.id\n" + sql_con1+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and pbe.id = be.exambatch_id \n" + 
			"                   and bs.fk_batch_id = pbe.batch_id \n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"')\n" + 
			"         group by exambatch_id, e2_id) a,\n" + 
			"       (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examscore be,\n" + 
			"                       pe_bzz_student   bs,\n" + 
			"                       pe_enterprise    e1,\n" + 
			"                       pe_bzz_exambatch    pbe\n" + 
			"                 where be.student_id = bs.id\n" + sql_con1+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"'\n" + 
				"                   and pbe.id = be.exambatch_id \n" + 
			"                   and bs.fk_batch_id = pbe.batch_id \n" + 
			"                   and be.status = '402880a9aaadc115062dadfcf26b0003')\n" + 
			"         group by exambatch_id, e2_id) b,\n" + 
			"       (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examscore be,\n" + 
			"                       pe_bzz_student   bs,\n" + 
			"                       pe_enterprise    e1\n" + 
			"                 where be.student_id = bs.id\n" + sql_con1+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"'\n" + 
			"                   and be.status = '402880a9aaadc115061dadfcf26b0003')\n" + 
			"         group by exambatch_id, e2_id) c,\n" + 
			"       (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examlate be,\n" + 
			"                       pe_bzz_student  bs,\n" + 
			"                       pe_enterprise   e1\n" + 
			"                 where be.student_id = bs.id\n" + sql_con1+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"')\n" + 
			"         group by exambatch_id, e2_id) d,\n" + 
			"       (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examlate be,\n" + 
			"                       pe_bzz_student  bs,\n" + 
			"                       pe_enterprise   e1\n" + 
			"                 where be.student_id = bs.id\n" + sql_con1+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"'\n" + 
			"                   and be.status = 'aa2880a91dadc115011dadfcf26b0002')\n" + 
			"         group by exambatch_id, e2_id) e,\n" + 
			"       (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examlate be,\n" + 
			"                       pe_bzz_student  bs,\n" + 
			"                       pe_enterprise   e1\n" + 
			"                 where be.student_id = bs.id\n" + sql_con1+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"' \n" + 
			"                   and be.status = 'abb2880a91dadc115011dadfcf26b0002')\n" + 
			"         group by exambatch_id, e2_id) f,\n" + 
			"       (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examlate be,\n" + 
			"                       pe_bzz_student  bs,\n" + 
			"                       pe_enterprise   e1\n" + 
			"                 where be.student_id = bs.id\n" + sql_con1+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"' \n" + 
			"                   and be.status = 'ccb2880a91dadc115011dadfcf26b0002')\n" + 
			"         group by exambatch_id, e2_id) g,\n" + 
			"         (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examlate be,\n" + 
			"                       pe_bzz_student  bs,\n" + 
			"                       pe_enterprise   e1\n" + 
			"                 where be.student_id = bs.id\n" + sql_con1+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"' \n" + 
			"                   and be.status = '1qb2880a91dadc115011dadfcf26b0002')\n" + 
			"         group by exambatch_id, e2_id) h,\n" + 
			"(select exambatch_id, e2_id, count(bs_id) as num\n" +
			"  from (select eb.id as exambatch_id,\n" + 
			"               e1.id as e2_id,\n" + 
			"               bs.id as bs_id\n" + 
			"          from pe_bzz_batch     b,\n" + 
			"               pe_bzz_student   bs,\n" + 
			"               pe_enterprise    e1,\n" + 
			"               pe_bzz_exambatch eb,\n" + 
			"               sso_user         u\n" + 
			"         where eb.batch_id = b.id\n" + sql_con1+
			"           and bs.fk_enterprise_id = e1.id\n" + 
			"           and bs.fk_batch_id = b.id\n" + 
			"           and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
			"           and bs.fk_sso_user_id = u.id\n" + 
			"           and u.flag_isvalid = '2'\n" + 
			"           and eb.id = '"+exambatch_id+"')\n" + 
			" group by exambatch_id, e2_id	) j,				\n" + 
			" (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examscore be,\n" + 
			"                       pe_bzz_student   bs,\n" + 
			"                       pe_enterprise    e1\n" + 
			"                 where be.student_id = bs.id and be.total_grade = '优秀' and be.exam_status='4028809c2s925bcf011d66fd0dda7006'\n" + sql_con1+sql_con3+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"')\n" + 
			"         group by exambatch_id, e2_id) k,\n" + 
			" (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examscore be,\n" + 
			"                       pe_bzz_student   bs,\n" + 
			"                       pe_enterprise    e1\n" + 
			"                 where be.student_id = bs.id and be.total_grade = '良好' and be.exam_status='4028809c2s925bcf011d66fd0dda7006'\n" + sql_con1+sql_con3+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"')\n" + 
			"         group by exambatch_id, e2_id) l,\n" + 
			" (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examscore be,\n" + 
			"                       pe_bzz_student   bs,\n" + 
			"                       pe_enterprise    e1\n" + 
			"                 where be.student_id = bs.id and be.total_grade = '合格' and be.exam_status='4028809c2s925bcf011d66fd0dda7006'\n" + sql_con1+sql_con3+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"')\n" + 
			"         group by exambatch_id, e2_id) m,\n" + 
			" (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examscore be,\n" + 
			"                       pe_bzz_student   bs,\n" + 
			"                       pe_enterprise    e1\n" + 
			"                 where be.student_id = bs.id and be.total_grade = '不合格' and be.exam_status='4028809c2s925bcf011d66fd0dda7006'\n" + sql_con1+sql_con3+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"')\n" + 
			"         group by exambatch_id, e2_id) n, \n" + 
			" (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examscore be,\n" + 
			"                       pe_bzz_student   bs,\n" + 
			"                       pe_enterprise    e1\n" + 
			"                 where be.student_id = bs.id and be.exam_status='4028709c2s925bcf011d66fd0dda7006'\n" + sql_con1+sql_con3+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"')\n" + 
			"         group by exambatch_id, e2_id) o, \n" + 
			" (select exambatch_id, e2_id, count(be_id) as num\n" + 
			"          from (select be.exambatch_id, e1.id as e2_id, be.id as be_id\n" + 
			"                  from pe_bzz_examscore be,\n" + 
			"                       pe_bzz_student   bs,\n" + 
			"                       pe_enterprise    e1\n" + 
			"                 where be.student_id = bs.id and be.exam_status='4028809cus925bcf011d66fd0dda7006'\n" + sql_con1+sql_con3+
			"                   and bs.fk_enterprise_id = e1.id\n" + 
			"                   and be.exambatch_id = '"+exambatch_id+"')\n" + 
			"         group by exambatch_id, e2_id) p, \n" + 
			"(select exambatch_id, e2_id, count(distinct bs_id) as study_total_num\n" +
			"  from (select eb.id as exambatch_id,\n" + 
			"               e1.id as e2_id,\n" + 
			"               bs.id as bs_id\n" + 
			"          from pe_bzz_batch     b,\n" + 
			"               pe_bzz_student   bs,\n" + 
			"               pe_enterprise    e1,\n" + 
			"               pe_bzz_exambatch eb,\n" + 
			"               sso_user         u,training_course_student tcs\n" + 
			"         where eb.batch_id = b.id\n" + sql_con1+
			"           and bs.fk_enterprise_id = e1.id and tcs.student_id=u.id and tcs.percent<>0\n" + 
			"           and bs.fk_batch_id = b.id\n" + 
			"           and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
			"           and bs.fk_sso_user_id = u.id\n" + 
			"           and u.flag_isvalid = '2'\n" + 
			"           and eb.id = '"+exambatch_id+"')\n" + 
			" group by exambatch_id, e2_id	) q, \n" + 
			"(select exambatch_id, e2_id, sum(time * (percent / 100)) as total_time\n" +
			"  from (select eb.id as exambatch_id,\n" + 
			"               e1.id as e2_id,\n" + 
			"               bs.id as bs_id,ce.time,tcs.percent\n" + 
			"          from pe_bzz_batch     b,\n" + 
			"               pe_bzz_student   bs,\n" + 
			"               pe_enterprise    e1,\n" + 
			"               pe_bzz_exambatch eb,\n" + 
			"               sso_user         u,training_course_student tcs,pr_bzz_tch_opencourse co,pe_bzz_tch_course ce\n" + 
			"         where eb.batch_id = b.id\n" + sql_con1+
			"           and bs.fk_enterprise_id = e1.id and tcs.student_id=u.id and co.id = tcs.course_id and co.fk_course_id = ce.id\n" + 
			"           and bs.fk_batch_id = b.id --and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
			"           and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
			"           and bs.fk_sso_user_id = u.id\n" + 
			"           and u.flag_isvalid = '2'\n" + 
			"           and eb.id = '"+exambatch_id+"')\n" + 
			" group by exambatch_id, e2_id	) r, \n" + 
			"       (select exambatch_id, e2_id, count(be_id) as num\n" + 
					"          from (select be.exambatch_id,\n" + 
					"                       e1.id as e2_id,\n" + 
					"                       be.id as be_id\n" + 
					"                  from pe_bzz_examscore be,\n" + 
					"                       pe_bzz_student   bs,\n" + 
					"                       pe_enterprise    e1\n" + 
					"                 where be.student_id = bs.id\n" +  sql_con1 +
					"                   and bs.fk_enterprise_id = e1.id\n" + 
					"                   and be.exambatch_id = '"+exambatch_id+"'\n" + 
					"                   and be.exam_type = '4028809c1d625bcf03d4e5d865800003')\n" + 
					"         group by exambatch_id, e2_id) kk,\n" + 
					"       (select exambatch_id, e2_id, count(be_id) as num\n" + 
					"          from (select be.exambatch_id,\n" + 
					"                       e1.id as e2_id,\n" + 
					"                       be.id as be_id\n" + 
					"                  from pe_bzz_examscore be,\n" + 
					"                       pe_bzz_student   bs,\n" + 
					"                       pe_enterprise    e1\n" + 
					"                 where be.student_id = bs.id\n" +  sql_con1 +
					"                   and bs.fk_enterprise_id = e1.id\n" + 
					"                   and be.exambatch_id = '"+exambatch_id+"'\n" + 
					"                   and be.exam_type = '4028809c1d625bcf03d4e5d865800003'\n" + 
					"                   and be.status = '402880a9aaadc115061dadfcf26b0003')"+
					"         group by exambatch_id, e2_id) ll,\n" + 
					"       (select exambatch_id, e2_id, count(be_id) as num\n" + 
					"          from (select be.exambatch_id,\n" + 
					"                       e1.id as e2_id,\n" + 
					"                       be.id as be_id\n" + 
					"                  from pe_bzz_examscore be,\n" + 
					"                       pe_bzz_student   bs,\n" + 
					"                       pe_enterprise    e1\n" + 
					"                 where be.student_id = bs.id\n" +  sql_con1 +
					"                   and bs.fk_enterprise_id = e1.id\n" + 
					"                   and be.exambatch_id = '"+exambatch_id+"'\n" + 
					"                   and be.exam_type = '4028809c1d625bcf03d4e5d865800002')\n" + 
					"         group by exambatch_id, e2_id) mm,\n" + 
					"       (select exambatch_id, e2_id, count(be_id) as num\n" + 
					"          from (select be.exambatch_id,\n" + 
					"                       e1.id as e2_id,\n" + 
					"                       be.id as be_id\n" + 
					"                  from pe_bzz_examscore be,\n" + 
					"                       pe_bzz_student   bs,\n" + 
					"                       pe_enterprise    e1\n" + 
					"                 where be.student_id = bs.id\n" +  sql_con1 +
					"                   and bs.fk_enterprise_id = e1.id\n" + 
					"                   and be.exambatch_id = '"+exambatch_id+"'\n" + 
					"                   and be.exam_type = '4028809c1d625bcf03d4e5d865800002'\n" + 
					"                   and be.status = '402880a9aaadc115061dadfcf26b0003')"+
					"         group by exambatch_id, e2_id) nn\n" +  
			" where pe.fk_parent_id = '"+enterprise_id+"'\n" + 
			"   and pe.id = a.e2_id(+)\n" + 
			"   and pe.id = b.e2_id(+)\n" + 
			"   and pe.id = c.e2_id(+)\n" + 
			"   and pe.id = d.e2_id(+)\n" + 
			"   and pe.id = e.e2_id(+)\n" + 
			"   and pe.id = f.e2_id(+)\n" + 
			"   and pe.id = g.e2_id(+)\n" + 
			"   and pe.id = h.e2_id(+)\n" + 
			"   and pe.id = j.e2_id(+)\n" +
			"   and pe.id = k.e2_id(+)\n" + 
			"   and pe.id = l.e2_id(+)\n" + 
			"   and pe.id = m.e2_id(+)\n" + 
			"   and pe.id = n.e2_id(+)\n" +
			"   and pe.id = o.e2_id(+)\n" + 
			"   and pe.id = p.e2_id(+)\n" + 
			"   and pe.id = q.e2_id(+)\n" +
			"   and pe.id = r.e2_id(+)\n" +
			"   and pe.id = kk.e2_id(+)\n" +
			"   and pe.id = ll.e2_id(+)\n" +
			"   and pe.id = mm.e2_id(+)\n" +
			"   and pe.id = nn.e2_id(+)\n" +
			"   and pe.name like '%"+ search +"%' \n" + sql_con+
			" order by (nvl(j.num, '0') - nvl(a.num, '0') - nvl(d.num, '0'))";

 %>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="stat_exam.files/filelist.xml">
<link rel=Edit-Time-Data href="stat_exam.files/editdata.mso">
<link rel=OLE-Object-Data href="stat_exam.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2010-12-24T08:12:40Z</o:LastSaved>
  <o:Version>11.5606</o:Version>
 </o:DocumentProperties>
 <o:OfficeDocumentSettings>
  <o:RemovePersonalInformation/>
 </o:OfficeDocumentSettings>
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
	font-size:16.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
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
	vertical-align:bottom;
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
	vertical-align:bottom;
	border:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:locked visible;
	white-space:nowrap;
	mso-rotate:0;}
.xl24
	{mso-style-parent:style0;
	color:red;
	font-size:16.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	font-size:16.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	font-size:11.0pt;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl27
	{mso-style-parent:style0;
	font-size:11.0pt;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl28
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:General;
	text-align:center;
	border:.5pt solid windowtext;}
.xl29
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl30
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	mso-number-format:General;
	text-align:center;
	border:.5pt solid windowtext;}
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
      <x:VerticalResolution>0</x:VerticalResolution>
     </x:Print>
     <x:CodeName>Sheet1</x:CodeName>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>2</x:ActiveRow>
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
     <x:CodeName>Sheet2</x:CodeName>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet3</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:CodeName>Sheet3</x:CodeName>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
  </x:ExcelWorksheets>
  <x:WindowHeight>4530</x:WindowHeight>
  <x:WindowWidth>8505</x:WindowWidth>
  <x:WindowTopX>480</x:WindowTopX>
  <x:WindowTopY>120</x:WindowTopY>
  <x:AcceptLabelsInFormulas/>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1060 style='border-collapse:
 collapse;table-layout:fixed;width:796pt'>
 <col width=140 style='mso-width-source:userset;mso-width-alt:4480;width:105pt'>
 <col width=86 style='mso-width-source:userset;mso-width-alt:2752;width:65pt'>
 <col width=115 style='mso-width-source:userset;mso-width-alt:3680;width:86pt'>
 <col width=135 style='mso-width-source:userset;mso-width-alt:4320;width:101pt'>
 <col width=84 style='mso-width-source:userset;mso-width-alt:2688;width:63pt'>
 <col width=117 style='mso-width-source:userset;mso-width-alt:3744;width:88pt'>
 <col width=113 style='mso-width-source:userset;mso-width-alt:3616;width:85pt'>
 <col width=125 style='mso-width-source:userset;mso-width-alt:4000;width:94pt'>
 <col width=145 style='mso-width-source:userset;mso-width-alt:4640;width:109pt'>
 <tr height=50 style='mso-height-source:userset;height:37.5pt'>
  <td colspan=21 height=50 class=xl24 width=1060 style='height:37.5pt;
  width:796pt'><%=exambatch_name%><font class="font8"> <%=entername%>下属企业考试统计</font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'>企业名称</td>
  <td class=xl27 style='border-top:none;border-left:none'>报名总人数</td>
  <td class=xl27 style='border-top:none;border-left:none'>实际学习人数</td>
  <td class=xl27 style='border-top:none;border-left:none'>总学时</td>
  <td class=xl27 style='border-top:none;border-left:none'>人均学时</td>
  <td class=xl27 style='border-top:none;border-left:none'>考试申请人数</td>
  <td class=xl27 style='border-top:none;border-left:none'>考试资格审核通过人数</td>
  <td class=xl27 style='border-top:none;border-left:none'>缓考申报人数</td>
    <td class=xl26 style='border-top:none;border-left:none'>补考报名考试人数</td>
  <td class=xl26 style='border-top:none;border-left:none'>补考报名审核通过人数</td>
  <td class=xl26 style='border-top:none;border-left:none'>缓考报名考试人数</td>
  <td class=xl26 style='border-top:none;border-left:none'>缓考报名审核通过人数</td>
  <td class=xl26 style='border-top:none;border-left:none'>优秀</td>
  <td class=xl26 style='border-top:none;border-left:none'>良好</td>
  <td class=xl26 style='border-top:none;border-left:none'>合格</td>
  <td class=xl26 style='border-top:none;border-left:none'>不合格</td>
  <td class=xl26 style='border-top:none;border-left:none'>缺考</td>
  <td class=xl26 style='border-top:none;border-left:none'>优秀率</td>
  <td class=xl26 style='border-top:none;border-left:none'>良好率</td>
  <td class=xl26 style='border-top:none;border-left:none'>合格率</td>
  <td class=xl26 style='border-top:none;border-left:none'>通过率</td>
 </tr>
 <%             int b_total_count = 0;
 				int b_uncheck_count = 0;
 				int b_final_count = 0;
 				int h_total_count = 0;
 				int h_uncheck_count = 0;
 				int h_first_count = 0;
 				int h_final_count = 0;
 				int h_reject_count = 0;
 				int n_total_count = 0;
 				int total_count = 0;
 				int k_count = 0;
 				int l_count = 0;
 				int m_count = 0;
 				int n_count = 0;
 				int o_count = 0;
 				int p_count = 0;
 				int shikao_count = 0;
 				int study_total_count = 0;
 				int total_time_count = 0;
 				int avg_study_time_count=0;
 				
 				int bukao_count=0;
 				int bukao_pass_count=0;
 				int huankao_count=0;
 				int huankao_pass_count=0;
                
  				rs = db.executeQuery(sql);
				while(rs!=null&&rs.next())
				{
					String enterprise_name = fixnull(rs.getString("name"));//企业名称
					String b_total_num = fixnull(rs.getString("b_total_num"));//考试报名人数
					String b_uncheck_num = fixnull(rs.getString("b_uncheck_num"));//考试未审核人数
					String b_final_num = fixnull(rs.getString("b_final_num"));//考试已审核人数
					String h_total_num = fixnull(rs.getString("h_total_num"));   //缓考人数
					String h_uncheck_num = fixnull(rs.getString("h_uncheck_num"));   //缓考未审核人数
					String h_first_num = fixnull(rs.getString("h_first_num"));   //缓考已初审人数
					String h_final_num = fixnull(rs.getString("h_final_num"));   //缓考已终审人数
					String h_reject_num = fixnull(rs.getString("h_reject_num"));  // 缓考已驳回人数
					String n_total_num = fixnull(rs.getString("n_total_num"));// 考试未报名人数
					String total_num = fixnull(rs.getString("total_num"));   //参训总人数
					
					//如果此处不去掉，则有的企业总人数为0，但是有补考人数显示不出来   gaoyuan 20120114
					//if(total_num!=null&&total_num.equals("0")){
					//	continue;
					//}
					String k_num = fixnull(rs.getString("k_num"));//优秀
					String l_num = fixnull(rs.getString("l_num"));//良好
					String m_num = fixnull(rs.getString("m_num"));//合格
					String n_num = fixnull(rs.getString("n_num"));//不合格
					String o_num = fixnull(rs.getString("o_num"));//缺考
					String p_num = fixnull(rs.getString("p_num"));//违纪作弊
					String study_total_num = fixnull(rs.getString("study_total_num"));//实际学习人数
					String total_time = fixnull(rs.getString("total_time"));//总学时
					String avg_study_time = fixnull(rs.getString("avg_study_time"));//人均学时
					int shikao_num = Integer.parseInt(b_final_num)-Integer.parseInt(o_num);
					if(shikao_num==0){
						shikao_num=1;
					}
					
				    b_total_count = b_total_count + Integer.parseInt(b_total_num);
				    b_uncheck_count = b_uncheck_count +Integer.parseInt(b_uncheck_num);
				    b_final_count = b_final_count +Integer.parseInt(b_final_num);
				    h_total_count = h_total_count +Integer.parseInt(h_total_num);
				    h_uncheck_count = h_uncheck_count +Integer.parseInt(h_uncheck_num);
				    h_first_count = h_first_count +Integer.parseInt(h_first_num);
				    h_final_count = h_final_count +Integer.parseInt(h_final_num);
				    h_reject_count = h_reject_count +Integer.parseInt(h_reject_num);
				    n_total_count = n_total_count +Integer.parseInt(n_total_num);
				    total_count = total_count +Integer.parseInt(total_num);
				    study_total_count = study_total_count + Integer.parseInt(study_total_num);
				    total_time_count = total_time_count + Integer.parseInt(total_time);
				    k_count = k_count +Integer.parseInt(k_num);
				    l_count = l_count +Integer.parseInt(l_num);
				    m_count = m_count +Integer.parseInt(m_num);
				    n_count = n_count +Integer.parseInt(n_num);
				    o_count = o_count +Integer.parseInt(o_num);
				    p_count = p_count +Integer.parseInt(p_num);
				    
				    int bukao_total_num = rs.getInt("bukao_total_num");
				    bukao_count+=bukao_total_num;
					int bukao_pass_num = rs.getInt("bukao_pass_num");
					bukao_pass_count+=bukao_pass_num;
					int huankao_total_num = rs.getInt("huankao_total_num");
					huankao_count+=huankao_total_num;
				    int huankao_pass_num = rs.getInt("huankao_pass_num");
				    huankao_pass_count+=huankao_pass_num;
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl28 style='height:14.25pt;border-top:none'><%=enterprise_name %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=total_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=study_total_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=total_time %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=avg_study_time %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=b_total_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=b_final_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=h_total_num %>　</td>
   <td class=xl28 style='border-top:none;border-left:none' x:num><%=bukao_total_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=bukao_pass_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=huankao_total_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=huankao_pass_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=k_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=l_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=m_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=n_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=o_num %>　</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=new BigDecimal(Float.parseFloat(k_num)*100.0/shikao_num).setScale(1, BigDecimal.ROUND_HALF_UP)%>%</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=new BigDecimal(Float.parseFloat(l_num)*100.0/shikao_num).setScale(1, BigDecimal.ROUND_HALF_UP)%>%</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=new BigDecimal(Float.parseFloat(m_num)*100.0/shikao_num).setScale(1, BigDecimal.ROUND_HALF_UP)%>%</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=new BigDecimal((Float.parseFloat(k_num)+Float.parseFloat(l_num)+Float.parseFloat(m_num))*100.0/shikao_num).setScale(1, BigDecimal.ROUND_HALF_UP)%>%</td>
 </tr>
           <% } 
              db.close(rs); 
              shikao_count = b_final_count-o_count;
              if(shikao_count==0){
            	  shikao_count=1;
              }
              int total_count_temp = 0;
              if(total_count==0){
            	  total_count_temp=1;
              }else{
            	  total_count_temp=total_count;
              }
              %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl28 style='height:14.25pt;border-top:none'>总计</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=total_count %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=study_total_count %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=total_time_count %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' ><%=new BigDecimal((float)total_time_count/total_count_temp).setScale(1, BigDecimal.ROUND_HALF_UP) %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=b_total_count %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=b_final_count %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=h_total_count %>　</td>
   <td class=xl30 style='border-top:none;border-left:none' x:num><%=bukao_count %>　</td>
  <td class=xl30 style='border-top:none;border-left:none' x:num><%=bukao_pass_count %>　</td>
  <td class=xl30 style='border-top:none;border-left:none' x:num><%=huankao_count %>　</td>
  <td class=xl30 style='border-top:none;border-left:none' x:num><%=huankao_pass_count %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=k_count %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=l_count %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=m_count %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=n_count %>　</td>
  <td class=xl28 style='border-top:none;border-left:none' x:num><%=o_count %>　</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=new BigDecimal(k_count*100.0/shikao_count).setScale(1, BigDecimal.ROUND_HALF_UP)%>%</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=new BigDecimal(l_count*100.0/shikao_count).setScale(1, BigDecimal.ROUND_HALF_UP)%>%</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=new BigDecimal(m_count*100.0/shikao_count).setScale(1, BigDecimal.ROUND_HALF_UP)%>%</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=new BigDecimal((k_count+l_count+m_count)*100.0/shikao_count).setScale(1, BigDecimal.ROUND_HALF_UP)%>%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
 	<td class=xl28 style='border-top:none;border-left:none' colspan="3">参训学员学习率： <%=new BigDecimal(study_total_count*100.0/total_count_temp).setScale(1, BigDecimal.ROUND_HALF_UP)%>%</td>
 	<td class=xl28 style='border-top:none;border-left:none' colspan="3">总学时： <%=total_time_count %>学时</td>
 	<td class=xl28 style='border-top:none;border-left:none' colspan="2">人均学时：<%=new BigDecimal((float)total_time_count/total_count_temp).setScale(1, BigDecimal.ROUND_HALF_UP) %> 学时</td>
 	<td class=xl28 style='border-top:none;border-left:none' colspan="5">学习基本任务要求：76 学时×80%</td>
 	<td class=xl28 style='border-top:none;border-left:none' colspan="4">平均任务完成情况：<%=new BigDecimal(total_time_count*100.0/(total_count_temp*76*0.8)).setScale(1, BigDecimal.ROUND_HALF_UP)%>%</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
 	<td style='border-top:none;border-left:none' colspan="17">备注：60-75分 为合格；76-89分 为合格；90分以上 为优秀</td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=140 style='width:105pt'></td>
  <td width=86 style='width:65pt'></td>
  <td width=115 style='width:86pt'></td>
  <td width=135 style='width:101pt'></td>
  <td width=84 style='width:63pt'></td>
  <td width=117 style='width:88pt'></td>
  <td width=113 style='width:85pt'></td>
  <td width=125 style='width:94pt'></td>
  <td width=145 style='width:109pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
 