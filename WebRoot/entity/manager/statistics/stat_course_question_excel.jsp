<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=stat_enterprise_incompleted_excel.xls"); %>
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
	
				String sql_t=
					"select id,\n" +
					"       title,\n" + 
					"       publish_date,\n" + 
					"       course_id,\n" + 
					"       course_name,\n" + 
					"       submituser_id,\n" + 
					"       submituser_name,\n" + 
					"       submituser_type,\n" + 
					"       re_status,\n" + 
					"       question_type,\n" + 
					"       click_num\n" + 
					"  from (select iqi.id,\n" + 
					"               iqi.title,\n" + 
					"               to_char(publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n" + 
					"               course_id,\n" + 
					"               c.name as course_name,\n" + 
					"               submituser_id,\n" + 
					"               submituser_name,\n" + 
					"               submituser_type,\n" + 
					"               re_status,\n" + 
					"               iqt.name as question_type,\n" + 
					"               click_num\n" + 
					"          from interaction_question_info iqi, interaction_question_type iqt,pe_bzz_tch_course c\n" + 
					"         where iqi.question_type = iqt.id(+) and iqi.course_id=c.id) order by course_name,title";
	
			//System.out.println(sql_t);		
 %>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 12">
<link rel=File-List href="stat_course_question_excel.files/filelist.xml">
<style id="stat_course_question_excel_6427_Styles">
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
.font56427
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.xl636427
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
	mso-number-format:"\@";
	text-align:general;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl646427
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
	mso-number-format:"\@";
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl656427
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:700;
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
.xl666427
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:16.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
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

<div id="stat_course_question_excel_6427" align=center x:publishsource="Excel">

<table border=0 cellpadding=0 cellspacing=0 width=862 class=xl636427
 style='border-collapse:collapse;table-layout:fixed;width:647pt'>
 <col class=xl636427 width=212 style='mso-width-source:userset;mso-width-alt:
 6784;width:159pt'>
 <col class=xl636427 width=239 style='mso-width-source:userset;mso-width-alt:
 7648;width:179pt'>
 <col class=xl636427 width=72 span=2 style='width:54pt'>
 <col class=xl636427 width=81 style='mso-width-source:userset;mso-width-alt:
 2592;width:61pt'>
 <col class=xl636427 width=114 style='mso-width-source:userset;mso-width-alt:
 3648;width:86pt'>
 <col class=xl636427 width=72 style='width:54pt'>
 <tr height=27 style='height:20.25pt'>
  <td colspan=7 height=27 class=xl666427 width=862 style='height:20.25pt;
  width:647pt'>课程答疑情况统计</td>
 </tr>
 <tr height=18 style='height:13.5pt'>
  <td height=18 class=xl656427 style='height:13.5pt'>课程名称</td>
  <td class=xl656427 style='border-left:none'>问题</td>
  <td class=xl656427 style='border-left:none'>问题分类</td>
  <td class=xl656427 style='border-left:none'>是否回答</td>
  <td class=xl656427 style='border-left:none'>发布人</td>
  <td class=xl656427 style='border-left:none'>发布时间</td>
  <td class=xl656427 style='border-left:none'>浏览次数</td>
 </tr>
<%
dbpool db = new dbpool();
MyResultSet rs = null;
rs = db.executeQuery(sql_t);
int a = 0;
while(rs!=null&&rs.next())
{	
	a=a+rs.getInt("click_num");
%>
 <tr height=18 style='height:13.5pt'>
  <td height=18 class=xl646427 style='height:13.5pt;border-top:none'><%=fixnull(rs.getString("course_name")) %></td>
  <td class=xl646427 style='border-top:none;border-left:none'><%=fixnull(rs.getString("title")) %></td>
  <td class=xl646427 style='border-top:none;border-left:none'><%=fixnull(rs.getString("question_type")) %></td>
  <td class=xl646427 style='border-top:none;border-left:none'><%if("1".equals(fixnull(rs.getString("re_status")))) out.print("是");else out.print("否"); %></td>
  <td class=xl646427 style='border-top:none;border-left:none'><%=fixnull(rs.getString("submituser_name")) %></td>
  <td class=xl646427 style='border-top:none;border-left:none'><%=fixnull(rs.getString("publish_date")) %></td>
  <td class=xl646427 style='border-top:none;border-left:none'><%=fixnull(rs.getString("click_num")) %></td>
 </tr>
<%
}
db.close(rs);
%>
<tr height=18 style='height:13.5pt'>
  <td height=18 class=xl646427 style='height:13.5pt;border-top:none'>合计：</td>
  <td class=xl646427 style='border-top:none;border-left:none'> </td>
  <td class=xl646427 style='border-top:none;border-left:none'></td>
  <td class=xl646427 style='border-top:none;border-left:none'></td>
  <td class=xl646427 style='border-top:none;border-left:none'></td>
  <td class=xl646427 style='border-top:none;border-left:none'></td>
  <td class=xl646427 style='border-top:none;border-left:none'><%=a%></td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=212 style='width:159pt'></td>
  <td width=239 style='width:179pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=81 style='width:61pt'></td>
  <td width=114 style='width:86pt'></td>
  <td width=72 style='width:54pt'></td>
 </tr>
 <![endif]>
</table>

</div>


<!----------------------------->
<!--“从 EXCEL 发布网页”向导结束-->
<!----------------------------->
</body>

</html>
