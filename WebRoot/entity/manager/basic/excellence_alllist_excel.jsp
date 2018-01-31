<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=excellence_list_excel.xls"); %>
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
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="excellence.files/filelist.xml">
<link rel=Edit-Time-Data href="excellence.files/editdata.mso">
<link rel=OLE-Object-Data href="excellence.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2011-06-21T08:07:12Z</o:LastSaved>
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
.font7
	{color:windowtext;
	font-size:12.0pt;
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
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
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
	font-size:10.0pt;
	mso-number-format:"\@";
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=1009 style='border-collapse:
 collapse;table-layout:fixed;width:758pt'>
 <col width=150 style='mso-width-source:userset;mso-width-alt:1760;width:110pt'>
 <col width=100 style='mso-width-source:userset;mso-width-alt:4672;width:110pt'>
 <col width=150 style='mso-width-source:userset;mso-width-alt:1792;width:42pt'>
 <col width=230 style='mso-width-source:userset;mso-width-alt:7008;width:164pt'>
 <col width=230 style='mso-width-source:userset;mso-width-alt:7008;width:164pt'>
 <col width=126 style='mso-width-source:userset;mso-width-alt:4032;width:95pt'>
 <col width=69 style='mso-width-source:userset;mso-width-alt:2208;width:52pt'>
 <col width=111 style='mso-width-source:userset;mso-width-alt:3552;width:83pt'>
 <col width=114 style='mso-width-source:userset;mso-width-alt:3648;width:86pt'>
 <col width=113 style='mso-width-source:userset;mso-width-alt:3616;width:85pt'>
 <tr height=36 style='mso-height-source:userset;height:27.0pt'>
  <td colspan=11 height=36 class=xl24 width=1009 style='height:27.0pt;
  width:758pt'>《<%=batch_name%>》<font class="font7"><%=enterprise%>优秀管理员列表（此信息仅供参考）</font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td class=xl26 style='border-top:none;border-left:none'>姓名</td>
  <td class=xl26 style='border-top:none;border-left:none'>性别</td>
  <td class=xl26 style='border-top:none;border-left:none'>是否是优秀管理员</td>
  <td class=xl26 style='border-top:none;border-left:none'>所在集团</td>
  <td class=xl26 style='border-top:none;border-left:none'>所在企业</td>
  <td class=xl26 style='border-top:none;border-left:none'>移动电话</td>
  <td class=xl26 style='border-top:none;border-left:none'>学员总数</td>
  <td class=xl26 style='border-top:none;border-left:none'>综合成绩优秀率</td>
  <td class=xl26 style='border-top:none;border-left:none'>综合成绩良好率</td>
  <td class=xl26 style='border-top:none;border-left:none'>综合成绩优良率</td>
  <td class=xl26 style='border-top:none;border-left:none'>综合成绩通过率</td>
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
		"       k2.name as is_goodmag , k3.name as submit_status \n"+
		"  from pe_enterprise_manager         pem,\n" + 
		"       pr_bzz_pri_manager_enterprise pbpme,\n" + 
		"       pe_enterprise                 pe,\n" + 
		"       pe_enterprise                 pe1,\n" + 
		"       enum_const                    ec ,\n" + 
		"       pe_bzz_goodmag k1 , enum_const k2 , enum_const k3 \n"+
		" where pem.is_goodmag != 'ccb2880a91dadc115011dadfcf26b0010' \n "+
		"   and pem.is_goodmag = k2.id(+) and pem.id = k1.fk_managerid(+) and k1.submit_status = k3.id(+) \n"+ sql_conn+
		"   and pbpme.fk_sso_user_id = pem.fk_sso_user_id\n" + 
		"   and pem.fk_enterprise_id = pe.id\n" + 
		"   and ec.id(+) = pem.gender and pem.flag_isvalid='2' \n"+
		"   and pem.fk_enterprise_id=pe.id\n" +
		"   and pbpme.fk_enterprise_id=pe.id \n"+
		"   and pe.fk_parent_id=pe1.id(+) \n"+
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
		if(tongguo_rate >= 80.0 && youliang_rate>=40.0 && !total_num.equals(""))
		{
				
		}
		else
		{
			continue;
		}
 %>
 <tr height=19 style='height:14.25pt'>
  <td class=xl27 style='border-top:none;border-left:none'><%=manager_name%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=manager_gender%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=is_goodmag%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=enterprise1_name%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=enterprise_name%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=manager_phone%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=total_num%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=youxiu_rate%>%</td>
  <td class=xl27 style='border-top:none;border-left:none'><%=lianghao_rate%>%</td>
  <td class=xl27 style='border-top:none;border-left:none'><%=Double.parseDouble(youxiu_rate)+Double.parseDouble(lianghao_rate) %>%</td>
  <td class=xl27 style='border-top:none;border-left:none'><%=tongguo_rate%>%</td>
 </tr>
 <%}
 db.close(rs); %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=55 style='width:41pt'></td>
  <td width=146 style='width:110pt'></td>
  <td width=56 style='width:42pt'></td>
  <td width=219 style='width:164pt'></td>
  <td width=126 style='width:95pt'></td>
  <td width=69 style='width:52pt'></td>
  <td width=111 style='width:83pt'></td>
  <td width=114 style='width:86pt'></td>
  <td width=113 style='width:85pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>

