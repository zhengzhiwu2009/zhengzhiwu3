<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%response.setHeader("Content-Disposition", "attachment;filename=stat_phone_list_excel.xls"); %>
<%@ include file="./pub/priv.jsp"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%!
	//判断字符串为空的话，赋值为""
	String fixNull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "0";
			return str;
	}
%>
<%
	String sql = "";
	MyResultSet rs = null;
	dbpool db = new dbpool();
	sql = "select a.id,a.code,a.name,b.num09,c.num10,d.signnum,e.paynum,f.m_name,f.phone,f.mobile_phone,f.login_num from\n" +
		"(select e.id,e.code,e.name from pe_enterprise e where e.fk_parent_id is null order by e.code) a,\n" + 
		"(select e1.id,count(s.id) as num09 from pe_bzz_student s,pe_enterprise e,pe_enterprise e1,sso_user u\n" + 
		"where s.fk_enterprise_id=e.id and e.fk_parent_id=e1.id and u.id=s.fk_sso_user_id and u.flag_isvalid='2'\n" + 
		"and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_batch_id='ff8080812253f04f0122540a58000004' group by e1.id) b,\n" + 
		"(select e1.id,count(s.id) as num10 from pe_bzz_student s,pe_enterprise e,pe_enterprise e1,sso_user u\n" + 
		"where s.fk_enterprise_id=e.id and e.fk_parent_id=e1.id and u.id=s.fk_sso_user_id and u.flag_isvalid='2'\n" + 
		"and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_batch_id='ff8080812824ae6f012824b0a89e0008' group by e1.id) c,\n" + 
		"(select e1.id,count(s.id) as signnum from pe_bzz_recruit s,pe_enterprise e,pe_enterprise e1\n" + 
		"where s.fk_enterprise_id=e.id and e.fk_parent_id=e1.id  and s.fk_batch_id='ff8080812fce7858012fd2d27c1d1527' group by e1.id) d,\n" + 
		"(select e1.id,sum(s.num) as paynum from pe_bzz_fax_info s,pe_enterprise e,pe_enterprise e1\n" + 
		"where s.fk_enterprise_id=e.id and e.fk_parent_id=e1.id  and s.fk_batch_id='ff8080812fce7858012fd2d27c1d1527' group by e1.id) e,\n" + 
		"(select a.id,a.code,a.name,a.m_name,a.phone,a.mobile_phone,a.login_num from\n" + 
		"(select e.id,e.code,e.name,m.name as m_name,m.phone,m.mobile_phone,u.login_num\n" + 
		"from pe_enterprise_manager m,sso_user u,pe_enterprise e\n" + 
		"where m.fk_enterprise_id=e.id and m.fk_sso_user_id=u.id and u.fk_role_id in('402880a92137be1c012137db62100006','2') and e.fk_parent_id is null) a,\n" + 
		"(select e.id,e.code,e.name,max(u.login_num) as login_num\n" + 
		"from pe_enterprise_manager m,sso_user u,pe_enterprise e\n" + 
		"where m.fk_enterprise_id=e.id and m.fk_sso_user_id=u.id and u.fk_role_id in('402880a92137be1c012137db62100006','2') and e.fk_parent_id is null\n" + 
		"group by e.id,e.code,e.name) b\n" + 
		"where a.id=b.id and a.code=b.code and a.name=b.name and a.login_num=b.login_num) f\n" + 
		"where a.id=b.id(+) and a.id=c.id(+) and a.id=d.id(+) and a.id=e.id(+) and a.id=f.id(+)\n" + 
		"and (num09 is not null or num10 is not null or signnum is not null)";
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html;charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 12">
<link rel=File-List href="11111.files/filelist.xml">
<style id="11111_7318_Styles">
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
.xl157318
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
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
.xl637318
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl647318
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
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl657318
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl667318
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
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

<div id="11111_7318" align=center x:publishsource="Excel">

<table border=0 cellpadding=0 cellspacing=0 width=4081 style='border-collapse:
 collapse;table-layout:fixed;width:3067pt'>
 <col width=38 style='mso-width-source:userset;mso-width-alt:1216;width:29pt'>
 <col width=64 style='mso-width-source:userset;mso-width-alt:2048;width:48pt'>
 <col width=151 style='mso-width-source:userset;mso-width-alt:4832;width:113pt'>
 <col width=92 span=2 style='mso-width-source:userset;mso-width-alt:2944;
 width:69pt'>
 <col width=135 span=2 style='mso-width-source:userset;mso-width-alt:4320;
 width:101pt'>
 <col width=151 style='mso-width-source:userset;mso-width-alt:4832;width:113pt'>
 <col width=134 style='mso-width-source:userset;mso-width-alt:4288;width:101pt'>
 <col width=105 style='mso-width-source:userset;mso-width-alt:3360;width:79pt'>
 <col width=38 style='mso-width-source:userset;mso-width-alt:1216;width:29pt'>
 <col width=291 style='mso-width-source:userset;mso-width-alt:9312;width:218pt'>
 <col width=78 style='mso-width-source:userset;mso-width-alt:2496;width:59pt'>
 <col width=92 span=2 style='mso-width-source:userset;mso-width-alt:2944;
 width:69pt'>
 <col width=99 style='mso-width-source:userset;mso-width-alt:3168;width:74pt'>
 <col width=92 style='mso-width-source:userset;mso-width-alt:2944;width:69pt'>
 <col width=134 style='mso-width-source:userset;mso-width-alt:4288;width:101pt'>
 <col width=121 style='mso-width-source:userset;mso-width-alt:3872;width:91pt'>
 <col width=150 style='mso-width-source:userset;mso-width-alt:4800;width:113pt'>
 <col width=105 style='mso-width-source:userset;mso-width-alt:3360;width:79pt'>
 <col width=134 style='mso-width-source:userset;mso-width-alt:4288;width:101pt'>
 <col width=105 span=2 style='mso-width-source:userset;mso-width-alt:3360;
 width:79pt'>
 <col width=134 style='mso-width-source:userset;mso-width-alt:4288;width:101pt'>
 <col width=105 span=2 style='mso-width-source:userset;mso-width-alt:3360;
 width:79pt'>
 <col width=134 style='mso-width-source:userset;mso-width-alt:4288;width:101pt'>
 <col width=105 span=2 style='mso-width-source:userset;mso-width-alt:3360;
 width:79pt'>
 <col width=134 style='mso-width-source:userset;mso-width-alt:4288;width:101pt'>
 <col width=105 style='mso-width-source:userset;mso-width-alt:3360;width:79pt'>
 <col width=135 style='mso-width-source:userset;mso-width-alt:4320;width:101pt'>
 <col width=92 span=2 style='mso-width-source:userset;mso-width-alt:2944;
 width:69pt'>
 <col width=64 style='mso-width-source:userset;mso-width-alt:2048;width:48pt'>
 <col width=38 style='mso-width-source:userset;mso-width-alt:1216;width:29pt'>
 <tr height=18 style='height:13.5pt'>
  <td height=18 class=xl637318 width=38 style='height:13.5pt;width:29pt'>序号</td>
  <td class=xl637318 width=64 style='width:48pt'>企业编号</td>
  <td class=xl637318 width=151 style='width:113pt'>一级企业名称</td>
  <td class=xl637318 width=92 style='width:69pt'>09学习总人数</td>
  <td class=xl637318 width=92 style='width:69pt'>10学习总人数</td>
  <td class=xl637318 width=135 style='width:101pt'>11年动态报名总人数</td>
  <td class=xl637318 width=135 style='width:101pt'>11年动态交费总人数</td>
  <td class=xl637318 width=151 style='width:113pt'>一级管理员</td>
  <td class=xl637318 width=134 style='width:101pt'>一级管理员固定电话</td>
  <td class=xl637318 width=105 style='width:79pt'>一级管理员手机</td>
  <td class=xl637318 width=38 style='width:29pt'>序号</td>
  <td class=xl637318 width=291 style='width:218pt'>二级企业名称</td>
  <td class=xl637318 width=78 style='width:59pt'>09学习人数</td>
  <td class=xl637318 width=92 style='width:69pt'>09年考试人数</td>
  <td class=xl637318 width=92 style='width:69pt'>09年缓考人员</td>
  <td class=xl637318 width=99 style='width:74pt'>通过率/优秀率</td>
  <td class=xl637318 width=92 style='width:69pt'>10年学习人数</td>
  <td class=xl637318 width=134 style='width:101pt'>已开学期学习总人数</td>
  <td class=xl637318 width=121 style='width:91pt'>11年动态报名人数</td>
  <td class=xl637318 width=150 style='width:113pt'>11年动态交费人数动态</td>
  <td class=xl637318 width=105 style='width:79pt'>本级管理员姓名</td>
  <td class=xl637318 width=134 style='width:101pt'>本级管理员固定电话</td>
  <td class=xl637318 width=105 style='width:79pt'>本级管理员手机</td>
  <td class=xl637318 width=105 style='width:79pt'>本级负责人姓名</td>
  <td class=xl637318 width=134 style='width:101pt'>本级负责人固定电话</td>
  <td class=xl637318 width=105 style='width:79pt'>本级负责人手机</td>
  <td class=xl637318 width=105 style='width:79pt'>本级联系人姓名</td>
  <td class=xl637318 width=134 style='width:101pt'>本级联系人固定电话</td>
  <td class=xl637318 width=105 style='width:79pt'>本级联系人手机</td>
  <td class=xl637318 width=105 style='width:79pt'>上级负责人姓名</td>
  <td class=xl637318 width=134 style='width:101pt'>上级负责人固定电话</td>
  <td class=xl637318 width=105 style='width:79pt'>上级负责人手机</td>
  <td class=xl637318 width=135 style='width:101pt'>09证书邮寄快递单号</td>
  <td class=xl637318 width=92 style='width:69pt'>10年学习详情</td>
  <td class=xl637318 width=92 style='width:69pt'>11年预报人数</td>
  <td class=xl637318 width=64 style='width:48pt'>沟通记录</td>
  <td class=xl637318 width=38 style='width:29pt'>备注</td>
 </tr>
 <%
				sql = "select a.id,a.code,a.name,b.num09,c.num10,d.signnum,e.paynum,f.m_name,f.phone,f.mobile_phone,f.login_num from\n" +
				"(select e.id,e.code,e.name from pe_enterprise e where e.fk_parent_id is null order by e.code) a,\n" + 
				"(select e1.id,count(s.id) as num09 from pe_bzz_student s,pe_enterprise e,pe_enterprise e1,sso_user u\n" + 
				"where s.fk_enterprise_id=e.id and e.fk_parent_id=e1.id and u.id=s.fk_sso_user_id and u.flag_isvalid='2'\n" + 
				"and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_batch_id='ff8080812253f04f0122540a58000004' group by e1.id) b,\n" + 
				"(select e1.id,count(s.id) as num10 from pe_bzz_student s,pe_enterprise e,pe_enterprise e1,sso_user u\n" + 
				"where s.fk_enterprise_id=e.id and e.fk_parent_id=e1.id and u.id=s.fk_sso_user_id and u.flag_isvalid='2'\n" + 
				"and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_batch_id='ff8080812824ae6f012824b0a89e0008' group by e1.id) c,\n" + 
				"(select e1.id,count(s.id) as signnum from pe_bzz_recruit s,pe_enterprise e,pe_enterprise e1\n" + 
				"where s.fk_enterprise_id=e.id and e.fk_parent_id=e1.id  and s.fk_batch_id='ff8080812fce7858012fd2d27c1d1527' group by e1.id) d,\n" + 
				"(select e1.id,sum(s.num) as paynum from pe_bzz_fax_info s,pe_enterprise e,pe_enterprise e1\n" + 
				"where s.fk_enterprise_id=e.id and e.fk_parent_id=e1.id  and s.fk_batch_id='ff8080812fce7858012fd2d27c1d1527' group by e1.id) e,\n" + 
				"(select a.id,a.code,a.name,a.m_name,a.phone,a.mobile_phone,a.login_num from\n" + 
				"(select e.id,e.code,e.name,m.name as m_name,m.phone,m.mobile_phone,u.login_num\n" + 
				"from pe_enterprise_manager m,sso_user u,pe_enterprise e\n" + 
				"where m.fk_enterprise_id=e.id and m.fk_sso_user_id=u.id and u.fk_role_id in('402880a92137be1c012137db62100006','2') and e.fk_parent_id is null) a,\n" + 
				"(select e.id,e.code,e.name,max(u.login_num) as login_num\n" + 
				"from pe_enterprise_manager m,sso_user u,pe_enterprise e\n" + 
				"where m.fk_enterprise_id=e.id and m.fk_sso_user_id=u.id and u.fk_role_id in('402880a92137be1c012137db62100006','2') and e.fk_parent_id is null\n" + 
				"group by e.id,e.code,e.name) b\n" + 
				"where a.id=b.id and a.code=b.code and a.name=b.name and a.login_num=b.login_num) f\n" + 
				"where a.id=b.id(+) and a.id=c.id(+) and a.id=d.id(+) and a.id=e.id(+) and a.id=f.id(+)\n" + 
				"and (num09 is not null or num10 is not null or signnum is not null)";
				rs = db.executeQuery(sql);
				int a = 0;
				while(rs!=null&&rs.next())
				{
					a++;
					int count = 1;
					int row = 0;
					String id = fixnull(rs.getString("id"));
					String code = fixnull(rs.getString("code"));
					String name = fixnull(rs.getString("name"));
					String num09 = fixnull(rs.getString("num09"));
					String num10 = fixnull(rs.getString("num10"));
					String signnum = fixnull(rs.getString("signnum"));
					String paynum = fixnull(rs.getString("paynum"));
					String m_name = fixnull(rs.getString("m_name"));
					String phone = fixnull(rs.getString("phone"));
					String mobile_phone = fixnull(rs.getString("mobile_phone"));
					String login_num = fixnull(rs.getString("login_num"));
					String erji_sql =  "select id,code,name,fzr_name,fzr_phone,fzr_mobile,lxr_name,lxr_phone,lxr_mobile,num09,num10,nvl(num09,0)+nvl(num10,0) as num_total,signnum,paynum,m_name,phone,mobile_phone,login_num,num09exam,num09late,\n" +
						"replace(to_char(nvl(num09tongguo,0)*100/nvl(num09shikao,1),'990.9')||'%',' ','') as tongguo_rate,\n" + 
						"replace(to_char(nvl(num09youxiu,0)*100/nvl(num09shikao,1),'990.9')||'%',' ','') as youxiu_rate,s_name,s_phone,s_mobile_phone from\n" + 
						"(select a.id,a.code,a.name,a.fzr_name,a.fzr_phone,a.fzr_mobile,a.lxr_name,a.lxr_phone,a.lxr_mobile,b.num09,c.num10,d.signnum,e.paynum,f.m_name,f.phone,f.mobile_phone,f.login_num,g.num09exam,h.num09late,i.num09shikao,j.num09youxiu,k.num09tongguo,\n" + 
						"l.name as s_name,l.phone as s_phone,l.mobile_phone as s_mobile_phone from\n" + 
						"(select e.id,e.code,e.name,e.fzr_name,e.fzr_phone,e.fzr_mobile,e.lxr_name,e.lxr_phone,e.lxr_mobile from pe_enterprise e where e.fk_parent_id='"+id+"' order by e.code) a,\n" + 
						"(select e.id,count(s.id) as num09 from pe_bzz_student s,pe_enterprise e,sso_user u\n" + 
						"where s.fk_enterprise_id=e.id and e.fk_parent_id ='"+id+"' and u.id=s.fk_sso_user_id and u.flag_isvalid='2'\n" + 
						"and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_batch_id='ff8080812253f04f0122540a58000004' group by e.id) b,\n" + 
						"(select e.id,count(s.id) as num10 from pe_bzz_student s,pe_enterprise e,sso_user u\n" + 
						"where s.fk_enterprise_id=e.id and e.fk_parent_id ='"+id+"' and u.id=s.fk_sso_user_id and u.flag_isvalid='2'\n" + 
						"and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_batch_id='ff8080812824ae6f012824b0a89e0008' group by e.id) c,\n" + 
						"(select e.id,count(s.id) as signnum from pe_bzz_recruit s,pe_enterprise e\n" + 
						"where s.fk_enterprise_id=e.id and e.fk_parent_id ='"+id+"' and s.fk_batch_id='ff8080812fce7858012fd2d27c1d1527' group by e.id) d,\n" + 
						"(select e.id,sum(s.num) as paynum from pe_bzz_fax_info s,pe_enterprise e\n" + 
						"where s.fk_enterprise_id=e.id and e.fk_parent_id ='"+id+"'  and s.fk_batch_id='ff8080812fce7858012fd2d27c1d1527' group by e.id) e,\n" + 
						"(select a.id,a.code,a.name,a.m_name,a.phone,a.mobile_phone,a.login_num from\n" + 
						"(select e.id,e.code,e.name,m.name as m_name,m.phone,m.mobile_phone,u.login_num from pe_enterprise_manager m,sso_user u,pe_enterprise e\n" + 
						"where m.fk_enterprise_id=e.id and m.fk_sso_user_id=u.id and u.fk_role_id in('402880f322736b760122737a968a0010','402880f322736b760122737a60c40008')\n" + 
						"and e.fk_parent_id ='"+id+"') a,\n" + 
						"(select e.id,e.code,e.name,max(u.login_num) as login_num from pe_enterprise_manager m,sso_user u,pe_enterprise e\n" + 
						"where m.fk_enterprise_id=e.id and m.fk_sso_user_id=u.id and u.fk_role_id in('402880f322736b760122737a968a0010','402880f322736b760122737a60c40008')\n" + 
						"and e.fk_parent_id ='"+id+"' group by e.id,e.code,e.name) b\n" + 
						"where a.id=b.id and a.code=b.code and a.name=b.name and a.login_num=b.login_num) f,\n" + 
						"(select e.id,count(s.id) as num09exam from pe_bzz_student s,pe_enterprise e,sso_user u,pe_bzz_examscore be\n" + 
						"where s.fk_enterprise_id=e.id and e.fk_parent_id ='"+id+"' and u.id=s.fk_sso_user_id and u.flag_isvalid='2' and be.status='402880a9aaadc115061dadfcf26b0003'\n" + 
						"and be.exambatch_id='ff8080812c3ecbb4012c3ee59c440199' and be.student_id=s.id and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
						"and s.fk_batch_id='ff8080812253f04f0122540a58000004' group by e.id) g,\n" + 
						"(select e.id,count(s.id) as num09late from pe_bzz_student s,pe_enterprise e,sso_user u,pe_bzz_examlate be\n" + 
						"where s.fk_enterprise_id=e.id and e.fk_parent_id ='"+id+"' and u.id=s.fk_sso_user_id and u.flag_isvalid='2' and be.status='ccb2880a91dadc115011dadfcf26b0002'\n" + 
						"and be.exambatch_id='ff8080812c3ecbb4012c3ee59c440199' and be.student_id=s.id and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n" + 
						"and s.fk_batch_id='ff8080812253f04f0122540a58000004' group by e.id) h,\n" + 
						"(select e.id,count(s.id) as num09shikao from pe_bzz_student s,pe_enterprise e,sso_user u,pe_bzz_examscore be\n" + 
						"where s.fk_enterprise_id=e.id and e.fk_parent_id ='"+id+"' and u.id=s.fk_sso_user_id and u.flag_isvalid='2' and be.status='402880a9aaadc115061dadfcf26b0003'\n" + 
						"and be.exambatch_id='ff8080812c3ecbb4012c3ee59c440199' and be.student_id=s.id and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and be.exam_status<>'4028709c2s925bcf011d66fd0dda7006'\n" + 
						"and s.fk_batch_id='ff8080812253f04f0122540a58000004' group by e.id) i,\n" + 
						"(select e.id,count(s.id) as num09youxiu from pe_bzz_student s,pe_enterprise e,sso_user u,pe_bzz_examscore be\n" + 
						"where s.fk_enterprise_id=e.id and e.fk_parent_id ='"+id+"' and u.id=s.fk_sso_user_id and u.flag_isvalid='2' and be.status='402880a9aaadc115061dadfcf26b0003'\n" + 
						"and be.exambatch_id='ff8080812c3ecbb4012c3ee59c440199' and be.student_id=s.id and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and be.total_grade='优秀'\n" + 
						"and s.fk_batch_id='ff8080812253f04f0122540a58000004' group by e.id) j,\n" + 
						"(select e.id,count(s.id) as num09tongguo from pe_bzz_student s,pe_enterprise e,sso_user u,pe_bzz_examscore be\n" + 
						"where s.fk_enterprise_id=e.id and e.fk_parent_id ='"+id+"' and u.id=s.fk_sso_user_id and u.flag_isvalid='2' and be.status='402880a9aaadc115061dadfcf26b0003'\n" + 
						"and be.exambatch_id='ff8080812c3ecbb4012c3ee59c440199' and be.student_id=s.id and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and be.total_grade in('优秀','良好','合格')\n" + 
						"and s.fk_batch_id='ff8080812253f04f0122540a58000004' group by e.id) k,\n" + 
						"(select a.fk_sso_user_id,a.name,a.phone,a.mobile_phone,a.login_num,a.id,b.num from\n" + 
						"(select m.fk_sso_user_id,m.name,m.phone,m.mobile_phone,u.login_num,pme.fk_enterprise_id as id from pe_enterprise_manager m,pr_bzz_pri_manager_enterprise pme,sso_user u\n" + 
						"where pme.fk_sso_user_id=m.fk_sso_user_id and u.id=m.fk_sso_user_id) a,\n" + 
						"(select fk_sso_user_id,count(distinct fk_enterprise_id) as num from pr_bzz_pri_manager_enterprise pme,sso_user u where pme.fk_sso_user_id=u.id\n" + 
						"and u.fk_role_id in('402880f322736b760122737a968a0010','402880f322736b760122737a60c40008')\n" + 
						" group by fk_sso_user_id having count(distinct fk_enterprise_id)>1) b where a.fk_sso_user_id=b.fk_sso_user_id) l\n" + 
						"where a.id=b.id(+) and a.id=c.id(+) and a.id=d.id(+) and a.id=e.id(+) and a.id=f.id(+) and a.id=g.id(+) and a.id=h.id(+) and a.id=i.id(+) and a.id=j.id(+) and a.id=k.id(+)\n" + 
						"and a.id=l.id(+) and (num09 is not null or num10 is not null or signnum is not null))";
					count = db.countselect(erji_sql);
					MyResultSet erji_rs = db.executeQuery(erji_sql);
					while(erji_rs!=null&&erji_rs.next()){
						row++;
						String ej_id = fixnull(erji_rs.getString("id"));
						String ej_code = fixnull(erji_rs.getString("code"));
						String ej_name = fixnull(erji_rs.getString("name"));
						String ej_fzr_name = fixnull(erji_rs.getString("fzr_name"));
						String ej_fzr_phone = fixnull(erji_rs.getString("fzr_phone"));
						String ej_fzr_mobile = fixnull(erji_rs.getString("fzr_mobile"));
						String ej_lxr_name = fixnull(erji_rs.getString("lxr_name"));
						String ej_lxr_phone = fixnull(erji_rs.getString("lxr_phone"));
						String ej_lxr_mobile = fixnull(erji_rs.getString("lxr_mobile"));
						String ej_num09 = fixnull(erji_rs.getString("num09"));
						String ej_num10 = fixnull(erji_rs.getString("num10"));
						String ej_num_total = fixnull(erji_rs.getString("num_total"));
						String ej_signnum = fixnull(erji_rs.getString("signnum"));
						String ej_paynum = fixnull(erji_rs.getString("paynum"));
						String ej_m_name = fixnull(erji_rs.getString("m_name"));
						String ej_phone = fixnull(erji_rs.getString("phone"));
						String ej_mobile_phone = fixnull(erji_rs.getString("mobile_phone"));
						String ej_login_num = fixnull(erji_rs.getString("login_num"));
						String ej_num09exam = fixnull(erji_rs.getString("num09exam"));
						String ej_num09late = fixnull(erji_rs.getString("num09late"));
						String ej_tongguo_rate = fixnull(erji_rs.getString("tongguo_rate"));
						String ej_youxiu_rate = fixnull(erji_rs.getString("youxiu_rate"));
						String ej_s_name = fixnull(erji_rs.getString("s_name"));
						String ej_s_phone = fixnull(erji_rs.getString("s_phone"));
						String ej_s_mobile_phone = fixnull(erji_rs.getString("s_mobile_phone"));
						String mail_sql = "select express_no from pe_bzz_certificate_mail_info where fk_enterprise_id='"+ej_id+"' and fk_exam_batch_id='ff8080812c3ecbb4012c3ee59c440199'";
						MyResultSet mail_rs = db.executeQuery(mail_sql);
						String express_no = "";
						String sign_up_num="";
						String communicate_record="";
						String note="";
						while(mail_rs!=null&&mail_rs.next()){
							express_no = fixnull(mail_rs.getString("express_no"))+",";
						}
						db.close(mail_rs);
						String before_sql = 
							"select t.sign_up_num        as sign_up_num,\n" +
							"       t.communicate_record as communicate_record,\n" + 
							"       t.note               as note\n" + 
							"  from pe_bzz_recruit_before t\n" + 
							" where t.fk_enterprise_id = '"+ej_id+"'\n" + 
							"   and t.fk_batch_id =\n" + 
							"       (select id from pe_bzz_batch s where s.recruit_selected = '1')";

						MyResultSet before_rs = db.executeQuery(before_sql);
						while(before_rs!=null&&before_rs.next()){
							sign_up_num = fixNull(before_rs.getString("sign_up_num"));
							communicate_record = fixnull(before_rs.getString("communicate_record"));
							note = fixnull(before_rs.getString("note"));
						}
						db.close(before_rs);
						
						if(row==1){
%>
 <tr class=xl647318 height=18 style='height:13.5pt'>
  <td rowspan="<%=count %>" height=72 class=xl667318 style='height:54.0pt'><%=a%></td>
  <td rowspan="<%=count %>" class=xl667318><%=code%></td>
  <td rowspan="<%=count %>" class=xl667318><%=name%></td>
  <td rowspan="<%=count %>" class=xl657318><%=num09%></td>
  <td rowspan="<%=count %>" class=xl657318><%=num10%></td>
  <td rowspan="<%=count %>" class=xl657318><%=signnum%></td>
  <td rowspan="<%=count %>" class=xl657318><%=paynum%></td>
  <td rowspan="<%=count %>" class=xl667318><%=m_name%></td>
  <td rowspan="<%=count %>" class=xl667318><%=phone%></td>
  <td rowspan="<%=count %>" class=xl667318><%=mobile_phone%></td>
  <td class=xl667318 style='border-left:none'><%=row %></td>
  <td class=xl667318 style='border-left:none'><%=ej_name %></td>
  <td class=xl657318 style='border-left:none'><%=ej_num09 %></td>
  <td class=xl657318 style='border-left:none'><%=ej_num09exam %></td>
  <td class=xl657318 style='border-left:none'><%=ej_num09late %></td>
  <td class=xl657318 style='border-left:none'><%=ej_tongguo_rate+"/"+ej_youxiu_rate %></td>
  <td class=xl657318 style='border-left:none'><%=ej_num10 %></td>
  <td class=xl657318 style='border-left:none'><%=ej_num_total %></td>
  <td class=xl657318 style='border-left:none'><%=ej_signnum %></td>
  <td class=xl657318 style='border-left:none'><%=ej_paynum %></td>
  <td class=xl667318 style='border-left:none'><%=ej_m_name %></td>
  <td class=xl667318 style='border-left:none'><%=ej_phone %></td>
  <td class=xl667318 style='border-left:none'><%=ej_mobile_phone %></td>
  <td class=xl667318 style='border-left:none'><%=ej_fzr_name %></td>
  <td class=xl667318 style='border-left:none'><%=ej_fzr_phone %></td>
  <td class=xl667318 style='border-left:none'><%=ej_fzr_mobile %></td>
  <td class=xl667318 style='border-left:none'><%=ej_lxr_name %></td>
  <td class=xl667318 style='border-left:none'><%=ej_lxr_phone %></td>
  <td class=xl667318 style='border-left:none'><%=ej_lxr_mobile %></td>
  <td class=xl667318 style='border-left:none'><%=ej_s_name %></td>
  <td class=xl667318 style='border-left:none'><%=ej_s_phone %></td>
  <td class=xl667318 style='border-left:none'><%=ej_s_mobile_phone %></td>
  <td class=xl667318 style='border-left:none'><%=express_no %></td>
  <td class=xl667318 style='border-left:none'>　</td>
  <td class=xl667318 style='border-left:none'><%=sign_up_num %></td>
  <td class=xl667318 style='border-left:none'><%=communicate_record %></td>
  <td class=xl667318 style='border-left:none'><%=note %></td>
 </tr>
 <%
						}else{
%>
 <tr class=xl647318 height=18 style='height:13.5pt'>
  <td height=18 class=xl667318 style='height:13.5pt;border-top:none;border-left:
  none'><%=row %></td>
  <td class=xl667318 style='border-left:none'><%=ej_name %></td>
  <td class=xl657318 style='border-left:none'><%=ej_num09 %></td>
  <td class=xl657318 style='border-left:none'><%=ej_num09exam %></td>
  <td class=xl657318 style='border-left:none'><%=ej_num09late %></td>
  <td class=xl657318 style='border-left:none'><%=ej_tongguo_rate+"/"+ej_youxiu_rate %></td>
  <td class=xl657318 style='border-left:none'><%=ej_num10 %></td>
  <td class=xl657318 style='border-left:none'><%=ej_num_total %></td>
  <td class=xl657318 style='border-left:none'><%=ej_signnum %></td>
  <td class=xl657318 style='border-left:none'><%=ej_paynum %></td>
  <td class=xl667318 style='border-left:none'><%=ej_m_name %></td>
  <td class=xl667318 style='border-left:none'><%=ej_phone %></td>
  <td class=xl667318 style='border-left:none'><%=ej_mobile_phone %></td>
  <td class=xl667318 style='border-left:none'><%=ej_fzr_name %></td>
  <td class=xl667318 style='border-left:none'><%=ej_fzr_phone %></td>
  <td class=xl667318 style='border-left:none'><%=ej_fzr_mobile %></td>
  <td class=xl667318 style='border-left:none'><%=ej_lxr_name %></td>
  <td class=xl667318 style='border-left:none'><%=ej_lxr_phone %></td>
  <td class=xl667318 style='border-left:none'><%=ej_lxr_mobile %></td>
  <td class=xl667318 style='border-left:none'><%=ej_s_name %></td>
  <td class=xl667318 style='border-left:none'><%=ej_s_phone %></td>
  <td class=xl667318 style='border-left:none'><%=ej_s_mobile_phone %></td>
  <td class=xl667318 style='border-left:none'><%=express_no %></td>
  <td class=xl667318 style='border-left:none'>　</td>
  <td class=xl667318 style='border-left:none'><%=sign_up_num %></td>
  <td class=xl667318 style='border-left:none'><%=communicate_record %></td>
  <td class=xl667318 style='border-left:none'><%=note %></td>
 </tr>
 <%
						}
					}
					db.close(erji_rs);
%>
            <%
            	}
            	db.close(rs);
            %>
 
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=38 style='width:29pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=151 style='width:113pt'></td>
  <td width=92 style='width:69pt'></td>
  <td width=92 style='width:69pt'></td>
  <td width=135 style='width:101pt'></td>
  <td width=135 style='width:101pt'></td>
  <td width=151 style='width:113pt'></td>
  <td width=134 style='width:101pt'></td>
  <td width=105 style='width:79pt'></td>
  <td width=38 style='width:29pt'></td>
  <td width=291 style='width:218pt'></td>
  <td width=78 style='width:59pt'></td>
  <td width=92 style='width:69pt'></td>
  <td width=92 style='width:69pt'></td>
  <td width=99 style='width:74pt'></td>
  <td width=92 style='width:69pt'></td>
  <td width=134 style='width:101pt'></td>
  <td width=121 style='width:91pt'></td>
  <td width=150 style='width:113pt'></td>
  <td width=105 style='width:79pt'></td>
  <td width=134 style='width:101pt'></td>
  <td width=105 style='width:79pt'></td>
  <td width=105 style='width:79pt'></td>
  <td width=134 style='width:101pt'></td>
  <td width=105 style='width:79pt'></td>
  <td width=105 style='width:79pt'></td>
  <td width=134 style='width:101pt'></td>
  <td width=105 style='width:79pt'></td>
  <td width=105 style='width:79pt'></td>
  <td width=134 style='width:101pt'></td>
  <td width=105 style='width:79pt'></td>
  <td width=135 style='width:101pt'></td>
  <td width=92 style='width:69pt'></td>
  <td width=92 style='width:69pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=38 style='width:29pt'></td>
 </tr>
 <![endif]>
</table>

</div>


<!----------------------------->
<!--“从 EXCEL 发布网页”向导结束-->
<!----------------------------->
</body>

</html>
