<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=excellence_totallist_excel.xls"); %>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equalsIgnoreCase("null")) str = "";
		return str.trim();
	}%>
<%
	String search = fixnull(request.getParameter("search"));
	String search1 = fixnull(request.getParameter("search1"));
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String c_search = fixnull(request.getParameter("c_search"));
	String c_pageInt = fixnull(request.getParameter("c_pageInt"));
	String enterprise_id = fixnull(request
			.getParameter("enterprise_id"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	//String name = fixnull(request.getParameter("name"));
	String name = fixnull(java.net.URLDecoder.decode(fixnull(request
			.getParameter("name")), "UTF-8"));
	String bSub=fixnull(request.getParameter("bSub"));
	String erji_id=fixnull(request.getParameter("erji_id"));
	
	UserSession us = (UserSession) session
			.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	List entList = us.getPriEnterprises();
	String sql_ent = "";
	String sql_entbk = "";
	for (int i = 0; i < entList.size(); i++) {
		PeEnterprise e = (PeEnterprise) entList.get(i);
		sql_ent += "'" + e.getId() + "',";
	}
	sql_entbk = sql_ent;
	if (sql_ent!=null && !sql_ent.equals("")) {
		sql_ent = "(" + sql_ent.substring(0, sql_ent.length() - 1)
				+ ")";
	}
	dbpool db = new dbpool();
	MyResultSet rs = null;
	MyResultSet rs_stu = null;
	
	

		String enterprise="";
		String esql="";
		String tmp_sql="select name from pe_enterprise p where p.fk_parent_id is null and p.id='"+enterprise_id+"'";
		rs=db.executeQuery(tmp_sql);
		if(rs!=null && rs.next())
		{
			enterprise=fixnull(rs.getString("name"));
		}
		db.close(rs);
		
		String batch_name="";
		String bat_sql="select t.name from pe_bzz_batch t where  t.id='"+batch_id+"'";
		rs=db.executeQuery(bat_sql);
		if(rs!=null && rs.next())
		{
			batch_name=fixnull(rs.getString("name"));
		}
		db.close(rs);
		
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 12">
<link rel=File-List href="biaotou.files/filelist.xml">
<style id="biaotou_8636_Styles">
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
.font58636
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.xl658636
	{padding-top:1px;
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
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl668636
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl678636
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:10.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl688636
	{padding-top:1px;
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
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
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
	mso-char-type:none;}
-->
</style>
</head>

<body>
<!--[if !excel]>　　<![endif]-->
<!--下列信息由 Microsoft Office Excel 的“发布为网页”向导生成。-->
<!--如果同一条目从 Excel 中重新发布，则所有位于 DIV 标记之间的信息均将被替换。-->
<!----------------------------->
<!--“从 EXCEL 发布网页”向导开始-->
<!----------------------------->

<div id="biaotou_8636" align=center x:publishsource="Excel">

<table border=0 cellpadding=0 cellspacing=0 width=1642 class=xl658636
 style='border-collapse:collapse;table-layout:fixed;width:1234pt'>
 <col class=xl658636 width=219 style='mso-width-source:userset;mso-width-alt:
 7008;width:164pt'>
 <col class=xl658636 width=55 style='mso-width-source:userset;mso-width-alt:
 1760;width:41pt'>
 <col class=xl658636 width=126 style='mso-width-source:userset;mso-width-alt:
 4032;width:95pt'>
 <col class=xl658636 width=82 style='mso-width-source:userset;mso-width-alt:
 2624;width:62pt'>
 <col class=xl658636 width=100 style='mso-width-source:userset;mso-width-alt:
 3200;width:75pt'>
 <col class=xl658636 width=111 style='mso-width-source:userset;mso-width-alt:
 3552;width:83pt'>
 <col class=xl658636 width=114 style='mso-width-source:userset;mso-width-alt:
 3648;width:86pt'>
 <col class=xl658636 width=113 style='mso-width-source:userset;mso-width-alt:
 3616;width:85pt'>
 <col class=xl658636 width=144 style='mso-width-source:userset;mso-width-alt:
 4608;width:108pt'>
 <col class=xl658636 width=121 style='mso-width-source:userset;mso-width-alt:
 3872;width:91pt'>
 <col class=xl658636 width=128 style='mso-width-source:userset;mso-width-alt:
 4096;width:96pt'>
 <col class=xl658636 width=90 style='mso-width-source:userset;mso-width-alt:
 2880;width:68pt'>
 <col class=xl658636 width=125 style='mso-width-source:userset;mso-width-alt:
 4000;width:94pt'>
 <col class=xl658636 width=114 style='mso-width-source:userset;mso-width-alt:
 3648;width:86pt'>
 <tr height=41 style='mso-height-source:userset;height:30.75pt'>
  <td height=41 class=xl668636 width=219 style='height:30.75pt;width:164pt'>所在集团</td>
  <td class=xl668636 width=55 style='border-left:none;width:41pt'>姓名</td>
  <td class=xl668636 width=126 style='border-left:none;width:95pt'>所在单位</td>
  <td class=xl668636 width=82 style='border-left:none;width:62pt'>移动电话</td>
  <td class=xl668636 width=100 style='border-left:none;width:75pt'>网站登陆次数</td>
  <td class=xl668636 width=111 style='border-left:none;width:83pt'>学员总数</td>
  <td class=xl668636 width=114 style='border-left:none;width:86pt'>综合成绩优秀率</td>
  <td class=xl668636 width=113 style='border-left:none;width:85pt'>综合成绩良好率</td>
  <td class=xl668636 width=144 style='border-left:none;width:108pt'>综合成绩优良率</td>
  <td class=xl668636 width=121 style='border-left:none;width:91pt'>综合成绩通过率</td>
  <td class=xl668636 width=128 style='border-left:none;width:96pt'>系统参考状态</td>
  <td class=xl668636 width=90 style='border-left:none;width:68pt'>申报状态</td>
  <td class=xl668636 width=125 style='border-left:none;width:94pt'>是否提交电子版</td>
  <td class=xl668636 width=114 style='border-left:none;width:86pt'>是否提交纸板</td>
 </tr>
 <%
 	String sql_t = "";
	String sql_conn="";
	if(!us.getRoleId().equals("3"))//非总管理员
	{
		if(!enterprise_id.equals(""))
		{
			sql_conn=" and pbpme.fk_enterprise_id in"+sql_ent;
		}
		if(!erji_id.equals(""))
		{
			sql_conn+=" and pbpme.fk_enterprise_id in('"+erji_id+"')";
		}
	}
	else//总管理员
	{
		if(!enterprise_id.equals(""))
		{
			String sql_e=" select id from pe_enterprise where fk_parent_id='"+enterprise_id+"'";
			rs=db.executeQuery(sql_e);
			List eet = new ArrayList();
			String sql_eeet="";
			eet.add(enterprise_id);
			while (rs!=null && rs.next())
			{
				String man_id=fixnull(rs.getString("id"));
				eet.add(man_id);
			}
			db.close(rs);
			for(int i=0;i<eet.size();i++)
			{
			
				sql_eeet+="'"+eet.get(i)+"',";
			}
			if(!sql_eeet.equals(""))
			{
				sql_eeet="("+sql_eeet.substring(0,sql_eeet.length()-1)+")";
			}
			sql_conn=" and pbpme.fk_enterprise_id in"+sql_eeet;
		}
		if(!erji_id.equals("")&& !enterprise_id.equals(""))
		{
			sql_conn+=" and pbpme.fk_enterprise_id in('"+erji_id+"')";
		}
	}
	sql_t=
		"select pem.fk_sso_user_id           as manager_id,\n" +
		"       pem.name         as manager_name,\n" + 
		"       pe.name          as enterprise_name,\n" + 
		"       nvl(pe1.name,'无')          as enterprise1_name,\n" + 
		"       pem.mobile_phone as mobile_phone,\n" + 
		"       ec.name          as gender ,\n" + 
		"       k2.name as is_goodmag , k3.name as submit_status,su.login_num \n"+
		"  from pe_enterprise_manager         pem,\n" + 
		"       pr_bzz_pri_manager_enterprise pbpme,\n" + 
		"       pe_enterprise                 pe,\n" + 
		"       pe_enterprise                 pe1,\n" + 
		"       enum_const                    ec ,\n" + 
		"       pe_bzz_goodmag k1 , enum_const k2 , enum_const k3,sso_user su \n"+
		" where pem.is_goodmag != 'ccb2880a91dadc115011dadfcf26b0010' \n "+
		"   and pem.is_goodmag = k2.id(+) and pem.id = k1.fk_managerid(+) and k1.submit_status = k3.id(+) \n"+ sql_conn+
		"   and pbpme.fk_sso_user_id = pem.fk_sso_user_id\n" + 
		"   and pem.fk_enterprise_id = pe.id\n" + 
		"   and ec.id(+) = pem.gender and pem.flag_isvalid='2' \n"+
		"   and pem.fk_enterprise_id=pe.id\n" +
		"   and pbpme.fk_enterprise_id=pe.id \n"+
		"   and pe.fk_parent_id=pe1.id(+)  and su.id=pem.fk_sso_user_id\n"+
		"   order by pe1.id , pe.id , pem.id ";
	
	//System.out.println(sql_t);
    rs = db.executeQuery(sql_t);
    
	int a = 0;
	while(rs!=null&&rs.next())
	{
		String manager_id = fixnull(rs.getString("manager_id"));
		String manager_name = fixnull(rs.getString("manager_name"));
		String manager_gender = fixnull(rs.getString("gender"));
		String enterprise1_name = fixnull(rs.getString("enterprise1_name"));
		String enterprise_name = fixnull(rs.getString("enterprise_name"));
		String manager_phone = fixnull(rs.getString("mobile_phone"));
		
		String is_goodmag = fixnull(rs.getString("is_goodmag"));
		String submit_status = fixnull(rs.getString("submit_status"));
		if(!"".equals(submit_status)){
			is_goodmag = is_goodmag + "("+submit_status+")";
		}
		
		String login_num=fixnull(rs.getString("login_num"));
		
		String total_num="";
		String youxiu_rate ="";
		String lianghao_rate ="";
		Double tongguo_rate=0.0 ;
		Double youliang_rate=0.0;
		//根据该管理员查找管理企业下的学员统计
		String sql_stu=
			"--总sql\n" +
			"select a.total_num as total_num,\n" + 
			"       replace(to_char(nvl(f.tongguo, 0) * 100 / nvl(b.shikao, 1), '990.9'),\n" + 
			"               ' ',\n" + 
			"               '') as tongguo_rate,\n" + 
			"       replace(to_char(nvl(c.youliang, 0) * 100 / nvl(b.shikao, 1), '990.9'),\n" + 
			"               ' ',\n" + 
			"               '') as youliang_rate,\n" + 
			"       replace(to_char(nvl(d.youxiu, 0) * 100 / nvl(b.shikao, 1), '990.9'),\n" + 
			"               ' ',\n" + 
			"               '') as youxiu_rate,\n" + 
			"       replace(to_char(nvl(e.lianghao, 0) * 100 / nvl(b.shikao, 1), '990.9'),\n" + 
			"               ' ',\n" + 
			"               '') as lianghao_rate\n" + 
			"  from (\n" + 
			"        --总人数\n" + 
			"        select count(s.id) as total_num\n" + 
			"          from pe_bzz_student s, sso_user u\n" + 
			"         where u.id = s.fk_sso_user_id\n" + 
			"           and u.flag_isvalid = '2'\n" + 
			"           and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
			"           and s.fk_batch_id = '"+batch_id+"'\n" + 
			"           and s.fk_enterprise_id in\n" + 
			"               (select t.fk_enterprise_id\n" + 
			"                  from pr_bzz_pri_manager_enterprise t\n" + 
			"                 where t.fk_sso_user_id = '"+manager_id+"')) a,\n" + 
			"       (select decode(count(s.id),'0','1',count(s.id)) as shikao\n" + 
			"          from pe_bzz_student   s,\n" + 
			"               sso_user         u,\n" + 
			"               pe_bzz_examscore be,\n" + 
			"               pe_bzz_exambatch pb\n" + 
			"         where u.id = s.fk_sso_user_id\n" + 
			"           and u.flag_isvalid = '2'\n" + 
			"           and be.status = '402880a9aaadc115061dadfcf26b0003'\n" + 
			"           and be.exambatch_id = pb.id\n" + 
			"           and pb.batch_id = s.fk_batch_id\n" + 
			"           and be.student_id = s.id\n" + 
			"           and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
			"           and be.exam_status <> '4028709c2s925bcf011d66fd0dda7006'\n" + 
			"           and s.fk_batch_id = '"+batch_id+"'\n" + 
			"           and s.fk_enterprise_id in\n" + 
			"               (select t.fk_enterprise_id\n" + 
			"                  from pr_bzz_pri_manager_enterprise t\n" + 
			"                 where t.fk_sso_user_id = '"+manager_id+"')) b,\n" + 
			"       ( --优良人数\n" + 
			"        select count(s.id) as youliang\n" + 
			"          from pe_bzz_student   s,\n" + 
			"                sso_user         u,\n" + 
			"                pe_bzz_examscore be,\n" + 
			"                pe_bzz_exambatch pb\n" + 
			"         where u.id = s.fk_sso_user_id\n" + 
			"           and u.flag_isvalid = '2'\n" + 
			"           and be.status = '402880a9aaadc115061dadfcf26b0003'\n" + 
			"           and be.exambatch_id = pb.id\n" + 
			"           and pb.batch_id = s.fk_batch_id\n" + 
			"           and be.student_id = s.id\n" + 
			"           and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
			"           and be.total_grade in ('优秀', '良好')\n" + 
			"           and s.fk_batch_id = '"+batch_id+"'\n" + 
			"           and s.fk_enterprise_id in\n" + 
			"               (select t.fk_enterprise_id\n" + 
			"                  from pr_bzz_pri_manager_enterprise t\n" + 
			"                 where t.fk_sso_user_id = '"+manager_id+"')) c,\n" + 
			"       ( --优秀人数\n" + 
			"        select count(s.id) as youxiu\n" + 
			"          from pe_bzz_student   s,\n" + 
			"                sso_user         u,\n" + 
			"                pe_bzz_examscore be,\n" + 
			"                pe_bzz_exambatch pb\n" + 
			"         where u.id = s.fk_sso_user_id\n" + 
			"           and u.flag_isvalid = '2'\n" + 
			"           and be.status = '402880a9aaadc115061dadfcf26b0003'\n" + 
			"           and be.exambatch_id = pb.id\n" + 
			"           and pb.batch_id = s.fk_batch_id\n" + 
			"           and be.student_id = s.id\n" + 
			"           and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
			"           and be.total_grade in ('优秀')\n" + 
			"           and s.fk_batch_id = '"+batch_id+"'\n" + 
			"           and s.fk_enterprise_id in\n" + 
			"               (select t.fk_enterprise_id\n" + 
			"                  from pr_bzz_pri_manager_enterprise t\n" + 
			"                 where t.fk_sso_user_id = '"+manager_id+"')) d,\n" + 
			"       ( --良好人数\n" + 
			"        select count(s.id) as lianghao\n" + 
			"          from pe_bzz_student   s,\n" + 
			"                sso_user         u,\n" + 
			"                pe_bzz_examscore be,\n" + 
			"                pe_bzz_exambatch pb\n" + 
			"         where u.id = s.fk_sso_user_id\n" + 
			"           and u.flag_isvalid = '2'\n" + 
			"           and be.status = '402880a9aaadc115061dadfcf26b0003'\n" + 
			"           and be.exambatch_id = pb.id\n" + 
			"           and pb.batch_id = s.fk_batch_id\n" + 
			"           and be.student_id = s.id\n" + 
			"           and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
			"           and be.total_grade in ('良好')\n" + 
			"           and s.fk_batch_id = '"+batch_id+"'\n" + 
			"           and s.fk_enterprise_id in\n" + 
			"               (select t.fk_enterprise_id\n" + 
			"                  from pr_bzz_pri_manager_enterprise t\n" + 
			"                 where t.fk_sso_user_id = '"+manager_id+"')) e,\n" + 
			"\n" + 
			"       ( --通过人数\n" + 
			"        select count(s.id) as tongguo\n" + 
			"          from pe_bzz_student   s,\n" + 
			"                sso_user         u,\n" + 
			"                pe_bzz_examscore be,\n" + 
			"                pe_bzz_exambatch pb\n" + 
			"         where u.id = s.fk_sso_user_id\n" + 
			"           and u.flag_isvalid = '2'\n" + 
			"           and be.status = '402880a9aaadc115061dadfcf26b0003'\n" + 
			"           and be.exambatch_id = pb.id\n" + 
			"           and pb.batch_id = s.fk_batch_id\n" + 
			"           and be.student_id = s.id\n" + 
			"           and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
			"           and be.total_grade in ('优秀', '良好', '合格')\n" + 
			"           and s.fk_batch_id = '"+batch_id+"'\n" + 
			"           and s.fk_enterprise_id in\n" + 
			"               (select t.fk_enterprise_id\n" + 
			"                  from pr_bzz_pri_manager_enterprise t\n" + 
			"                 where t.fk_sso_user_id = '"+manager_id+"')) f";
        
		rs_stu=db.executeQuery(sql_stu);
		if(rs_stu!=null && rs_stu.next())
		{
			total_num = fixnull(rs_stu.getString("total_num"));
			youxiu_rate = fixnull(rs_stu.getString("youxiu_rate"));
			lianghao_rate = fixnull(rs_stu.getString("lianghao_rate"));
			tongguo_rate = rs_stu.getDouble("tongguo_rate");
			youliang_rate=rs_stu.getDouble("youliang_rate");
		}
		db.close(rs_stu);
		
		String status="";
		if(tongguo_rate >= 80.0 && youliang_rate>=40.0 &&!(tongguo_rate >=90.0 && youliang_rate>=60.0)  && !total_num.equals(""))
		{
				status="优秀";
		}
		else if(tongguo_rate >= 90.0 && youliang_rate>=60.0 && !total_num.equals(""))
		{
			status="最佳";
		}
		else
		{
			continue;
		}
 %>
 <tr height=33 style='mso-height-source:userset;height:24.75pt'>
  <td height=33 class=xl678636 style='height:24.75pt;border-top:none'><%=enterprise1_name%></td>
  <td class=xl678636 style='border-top:none;border-left:none'><%=manager_name%></td>
  <td class=xl678636 style='border-top:none;border-left:none'><%=enterprise_name%></td>
  <td class=xl678636 style='border-top:none;border-left:none'><%=manager_phone%></td>
  <td class=xl678636 style='border-top:none;border-left:none'><%=login_num%></td>
  <td class=xl678636 style='border-top:none;border-left:none'><%=total_num%></td>
  <td class=xl678636 style='border-top:none;border-left:none'><%=youxiu_rate%>%</td>
  <td class=xl678636 style='border-top:none;border-left:none'><%=lianghao_rate%>%</td>
  <td class=xl678636 style='border-top:none;border-left:none'><%=Double.parseDouble(youxiu_rate)+Double.parseDouble(lianghao_rate) %>%</td>
  <td class=xl678636 style='border-top:none;border-left:none'><%=tongguo_rate%>%</td>
  <td class=xl678636 style='border-top:none;border-left:none'><%=status%></td>
  <td class=xl678636 style='border-top:none;border-left:none'></td>
  <td class=xl688636 style='border-top:none;border-left:none'>　</td>
  <td class=xl688636 style='border-top:none;border-left:none'>　</td>
 </tr>
  <%}
 db.close(rs); %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=219 style='width:164pt'></td>
  <td width=55 style='width:41pt'></td>
  <td width=126 style='width:95pt'></td>
  <td width=82 style='width:62pt'></td>
  <td width=100 style='width:75pt'></td>
  <td width=111 style='width:83pt'></td>
  <td width=114 style='width:86pt'></td>
  <td width=113 style='width:85pt'></td>
  <td width=144 style='width:108pt'></td>
  <td width=121 style='width:91pt'></td>
  <td width=128 style='width:96pt'></td>
  <td width=90 style='width:68pt'></td>
  <td width=125 style='width:94pt'></td>
  <td width=114 style='width:86pt'></td>
 </tr>
 <![endif]>
</table>

</div>


<!----------------------------->
<!--“从 EXCEL 发布网页”向导结束-->
<!----------------------------->
</body>

</html>
