<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=stat_course_total_excel.xls"); %>
<%@page import="com.whaty.platform.database.oracle.*,java.util.*,java.text.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page" />
<jsp:directive.page
	import="com.whaty.platform.sso.web.action.SsoConstant" />
<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}

	String getCurDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
		return sdf.format(new Date());
	}
	%>
<%
	if(session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY) == null) {
		response.sendRedirect("/error/priv_error.htm");
		return ;
	}
	String courseId = fixnull(request.getParameter("id"));
	
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql = "select distinct pbo.subject from pe_bzz_onlinecourse pbo \n"
		+ "where pbo.id='" + courseId + "'";
	String cName = null;
	rs = db.executeQuery(sql);
	if(rs.next()) {
		cName = rs.getString("subject");
	}
	db.close(rs);
	
	sql = "select twy.id,twy.querstion,su.login_id,twy.twy_date \n"
		+ "from pe_bzz_onlinecourse_twy twy inner join sso_user su on twy.fk_sso_user_id=su.id \n" 
		+ "where twy.fk_online_course_id='"+courseId+"' \n"
		+ "order by twy.twy_date desc";
		
	//System.out.println(sql);
%>


<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="在线课堂问题列表.files/filelist.xml">
<link rel=Edit-Time-Data href="在线课堂问题列表.files/editdata.mso">
<link rel=OLE-Object-Data href="在线课堂问题列表.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>China User</o:Author>
  <o:LastAuthor>China User</o:LastAuthor>
  <o:Created>2010-06-22T03:40:48Z</o:Created>
  <o:LastSaved>2010-06-22T04:09:10Z</o:LastSaved>
  <o:Company>Micosoft</o:Company>
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
	text-align:center;
	border:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:center;}
.xl26
	{mso-style-parent:style0;
	text-align:center;}
.xl27
	{mso-style-parent:style0;
	text-align:right;}
.xl28
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:right;}
.xl29
	{mso-style-parent:style0;
	text-align:left;
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
      <x:HorizontalResolution>300</x:HorizontalResolution>
      <x:VerticalResolution>300</x:VerticalResolution>
     </x:Print>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>9</x:ActiveRow>
       <x:ActiveCol>1</x:ActiveCol>
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
  <x:WindowHeight>10695</x:WindowHeight>
  <x:WindowWidth>21435</x:WindowWidth>
  <x:WindowTopX>0</x:WindowTopX>
  <x:WindowTopY>105</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1247 style='border-collapse:
 collapse;table-layout:fixed;width:935pt'>
 <col width=59 style='mso-width-source:userset;mso-width-alt:1888;width:44pt'>
 <col width=695 style='mso-width-source:userset;mso-width-alt:22240;width:521pt'>
 <col width=250 style='mso-width-source:userset;mso-width-alt:8000;width:188pt'>
 <col width=243 style='mso-width-source:userset;mso-width-alt:7776;width:182pt'>
 <tr height=42 style='mso-height-source:userset;height:31.5pt'>
  <td colspan=4 height=42 class=xl25 width=1247 style='height:31.5pt;
  width:935pt'>在线课堂问题列表</td>
 </tr>
 <tr height=24 style='mso-height-source:userset;height:18.0pt'>
  <td colspan=4 height=24 class=xl27 style='height:18.0pt'>课程名称：<font
  class="font8"><%=cName %></font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=4 height=19 class=xl27 style='height:14.25pt'>导出时间：<font
  class="font8"><%=getCurDate() %></font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>编号</td>
  <td class=xl24 style='border-left:none'>内容</td>
  <td class=xl24 style='border-left:none'>时间</td>
  <td class=xl24 style='border-left:none'>提出人</td>
 </tr>
  <%
	rs = db.executeQuery(sql);
	int a = 0;
	while (rs != null && rs.next()) {
		a++;
		String id = fixnull(rs.getString("id"));
		String querstion = fixnull(rs.getString("querstion"));
		String login_id = fixnull(rs.getString("login_id"));
		String twy_date = rs.getString("twy_date");
%>
 <tr height=38 style='height:28.5pt'>
  <td height=38 class=xl24 style='height:28.5pt;border-top:none'><%=a %></td>
  <td class=xl29 width=695 style='border-top:none;border-left:none;width:521pt'><%=querstion %></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=twy_date %></td>
  <td class=xl24 style='border-top:none;border-left:none'><%= login_id%></td>
 </tr>
 <% 
	}
	db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=59 style='width:44pt'></td>
  <td width=695 style='width:521pt'></td>
  <td width=250 style='width:188pt'></td>
  <td width=243 style='width:182pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>


					