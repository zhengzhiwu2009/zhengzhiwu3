<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>

<%	response.setHeader("Content-Disposition", "attachment;filename=votesuggest_excel.xls"); %>
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
	String id = request.getParameter("id");
	String vote_title = "";
	String sql = "select title from pe_vote_paper where id='"+id+"'";
	dbpool db = new dbpool();
	MyResultSet rs = null;
	rs = db.executeQuery(sql);
	if(rs!=null&&rs.next()){
		vote_title = fixnull(rs.getString("title"));
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
<style id="新建 Microsoft Office Excel 工作表 (2)_21755_Styles">
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
.font521755
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.xl1521755
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
.xl6321755
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:14.0pt;
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
.xl6421755
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:10.0pt;
	font-weight:700;
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
.xl6521755
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:10.0pt;
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

<div id="新建 Microsoft Office Excel 工作表 (2)_21755" align=center
x:publishsource="Excel">

<table border=0 cellpadding=0 cellspacing=0 width=956 style='border-collapse:
 collapse;table-layout:fixed;width:717pt'>
 <col width=72 style='width:54pt'>
 <col width=96 style='mso-width-source:userset;mso-width-alt:3072;width:72pt'>
 <col width=121 style='mso-width-source:userset;mso-width-alt:3872;width:91pt'>
 <col width=667 style='mso-width-source:userset;mso-width-alt:21344;width:500pt'>
 <tr height=35 style='mso-height-source:userset;height:26.25pt'>
  <td colspan=4 height=35 class=xl6321755 width=956 style='height:26.25pt;
  width:717pt'><%=vote_title %></td>
 </tr>
 <tr height=18 style='height:13.5pt'>
  <td height=18 class=xl6421755 style='height:13.5pt'>审核状态</td>
  <td class=xl6421755 style='border-left:none'>发表时间</td>
  <td class=xl6421755 style='border-left:none'>IP地址</td>
  <td class=xl6421755 style='border-left:none'>建议内容</td>
 </tr>
<%
	sql ="select s.ip,e.name,to_char(s.found_date,'yyyy-mm-dd') as found_date,s.note\n" +
		"from pr_vote_suggest s,enum_const e where fk_vote_paper_id='"+id+"' and s.flag_check=e.id order by found_date desc";
	rs = db.executeQuery(sql);
	while(rs!=null&&rs.next())
	{
		String ip = fixnull(rs.getString("ip"));
		String name = fixnull(rs.getString("name"));
		String found_date = fixnull(rs.getString("found_date"));
		String note = fixnull(rs.getString("note"));
%>
 <tr height=18 style='height:13.5pt'>
  <td height=18 class=xl6521755 style='height:13.5pt;border-top:none'><%=name %></td>
  <td class=xl6521755 style='border-top:none;border-left:none'><%=found_date %></td>
  <td class=xl6521755 style='border-top:none;border-left:none'><%=ip %></td>
  <td class=xl6521755 style='border-top:none;border-left:none'><%=note %></td>
 </tr>
<%
	}
	db.close(rs);
%>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=96 style='width:72pt'></td>
  <td width=121 style='width:91pt'></td>
  <td width=667 style='width:500pt'></td>
 </tr>
 <![endif]>
</table>

</div>


<!----------------------------->
<!--“从 EXCEL 发布网页”向导结束-->
<!----------------------------->
</body>

</html>