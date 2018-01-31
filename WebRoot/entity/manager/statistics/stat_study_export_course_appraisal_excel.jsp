<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=stat_study_export_course_appraisal_excel.xls"); %>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.SimpleDateFormat"%>
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
%>
<%
	String batch_id = fixnull(request.getParameter("batch_id"));
	
        String sql_con="";
		//if(!search.equals(""))
			//sql_con=" and b.id='"+search+"'";
		
		if (!batch_id.equals("")) {
			sql_con = " and tc.fk_batch_id='" + batch_id + "'";
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

		sql = "select c.flag_coursetype,c.name as cname,f.name as fname, t.assess,f.px, count(t.fk_student_id) as num \n"
			+ "  from pe_bzz_assess t, pe_bzz_course_feedback f,pe_bzz_tch_course c,pr_bzz_tch_opencourse tc \n"
			+ " where c.id=tc.fk_course_id and tc.id=t.fk_course_id  \n"
			+ "  " + sql_con + " \n"
			+ "and f.id(+) = t.fk_feedback_id group by c.flag_coursetype,c.name,f.name,t.assess,f.px "
			+ "order by c.flag_coursetype,c.name,to_number(f.px),to_number(decode(t.assess,'很好','1','好','2','一般','3','较差','4','差','5','有','6','无','7','合适','8','不合适','9','偏长','10','偏深','11','偏难','12','偏短','13','偏浅','14','偏易','15')),t.assess ";
		
		//System.out.println("---------" + sql);
 %>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="20100310课程评估结果.files/filelist.xml">
<link rel=Edit-Time-Data href="20100310课程评估结果.files/editdata.mso">
<link rel=OLE-Object-Data href="20100310课程评估结果.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>微软用户</o:Author>
  <o:LastAuthor>China User</o:LastAuthor>
  <o:Created>2010-03-10T05:17:52Z</o:Created>
  <o:LastSaved>2010-04-28T08:10:42Z</o:LastSaved>
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
.font7
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
	font-size:18.0pt;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:center;}
.xl25
	{mso-style-parent:style0;
	text-align:center;}
.xl26
	{mso-style-parent:style0;
	text-align:right;}
.xl27
	{mso-style-parent:style0;
	border:.5pt solid windowtext;}
.xl28
	{mso-style-parent:style0;
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
      <x:HorizontalResolution>200</x:HorizontalResolution>
      <x:VerticalResolution>200</x:VerticalResolution>
     </x:Print>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>3</x:ActiveRow>
       <x:ActiveCol>4</x:ActiveCol>
       <x:RangeSelection>$A$1:$E$4</x:RangeSelection>
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
  <x:WindowWidth>19275</x:WindowWidth>
  <x:WindowTopX>360</x:WindowTopX>
  <x:WindowTopY>105</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=811 style='border-collapse:
 collapse;table-layout:fixed;width:608pt'>
 <col width=72 style='width:54pt'>
 <col width=211 style='mso-width-source:userset;mso-width-alt:6752;width:158pt'>
 <col width=384 style='mso-width-source:userset;mso-width-alt:12288;width:288pt'>
 <col width=72 span=2 style='width:54pt'>
 <tr height=43 style='mso-height-source:userset;height:32.25pt'>
  <td colspan=5 height=43 class=xl24 width=811 style='height:32.25pt;
  width:608pt'><%=batchName%> 课程评估结果</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=5 height=19 class=xl26 style='height:14.25pt'>导出时间：<font
  class="font7"><%=getCurDate()%></font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl28 style='height:14.25pt'>序号</td>
  <td class=xl28 style='border-left:none'>课程名称</td>
  <td class=xl28 style='border-left:none'>评估内容</td>
  <td class=xl28 style='border-left:none'>评估结果</td>
  <td class=xl28 style='border-left:none'>结果</td>
 </tr>
  <%
 	rs = db.executeQuery(sql);
 	int index = 1;
	while(rs.next()) {
		String cName = fixnull(rs.getString("cname"));
		String fName = fixnull(rs.getString("fname"));
		String  assess = fixnull(rs.getString("assess"));
		int num = rs.getInt("num");
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl27 align=right style='height:14.25pt;border-top:none'
  x:num><%=index%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=cName%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=fName%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=assess%></td>
  <td class=xl27 align=right style='border-top:none;border-left:none' x:num><%=num%></td>
 </tr>
 <%
 		index++;
 	}
 	db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=211 style='width:158pt'></td>
  <td width=384 style='width:288pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
