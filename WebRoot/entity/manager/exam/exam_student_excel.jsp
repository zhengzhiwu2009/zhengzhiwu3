<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import="com.sun.org.apache.bcel.internal.generic.NEW"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=exam_student.xls"); %>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.*"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}
	
	String getCurDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		return sdf.format(new Date());
	}	
%>
<%
        String trueName = request.getParameter("_s_peBzzStudent_trueName");//学生姓名
		String batchName = request.getParameter("_s_peBzzExamBatch_name");//所在考试批次
		String siteNme = request.getParameter("_s_peBzzExamSite_name");//考点
		String status = request.getParameter("_s_enumConstByFlagExamScoreStatus_name");//审核状态
		
		
		String sqlCon = "";
		if(trueName != null && !trueName.trim().equals("")) {
			sqlCon += " and true_name like '%" + batchName.trim() + "%' ";
		}
		
		if(batchName != null && !batchName.trim().equals("")) {
			sqlCon += " and exam_batch like '%" + batchName.trim() + "%' ";
		}
		
		if(siteNme != null && !siteNme.trim().equals("")) {
			sqlCon += " and site like '%" + siteNme.trim() + "%' ";
		}
		
		if(status != null && !status.trim().equals("")) {
			sqlCon += " and status_flag like '%" + status.trim() + "%' ";
		}
        
		dbpool db = new dbpool();
		MyResultSet rs = null;
		
		String sql = 
			"select true_name,\n" +
			"       reg_no,\n" + 
			"       enterprise_name,\n" + 
			"       student_batch,\n" + 
			"       site,\n" + 
			"       exam_batch,\n" + 
			"       status_flag\n" + 
			"  from (select stu.true_name as true_name,\n" + 
			"               stu.reg_no    as reg_no,\n" + 
			"               ent.name      as enterprise_name,\n" + 
			"               batc.name     as student_batch,\n" + 
			"               si.name       as site,\n" + 
			"               bat.name      as exam_batch,\n" + 
			"               enu.name      as status_flag\n" + 
			"          from pe_bzz_examscore sco,\n" + 
			"               pe_bzz_student   stu,\n" + 
			"               pe_bzz_examsite  si,\n" + 
			"               pe_bzz_exambatch bat,\n" + 
			"               pe_bzz_batch     batc,\n" + 
			"               enum_const       enu,\n" + 
			"               pe_enterprise    ent\n" + 
			"         where sco.student_id = stu.id\n" + 
			"           and sco.exambatch_id = bat.id\n" + 
			"           and sco.site_id = si.id\n" + 
			"           and sco.status = enu.id\n" + 
			"           and stu.fk_enterprise_id = ent.id\n" + 
			"           and stu.fk_batch_id = batc.id)\n" + 
			"where true_name like '%%'"+sqlCon+"order by reg_no";


		//System.out.println(sql);
		
 %>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="exam_student.files/filelist.xml">
<link rel=Edit-Time-Data href="exam_student.files/editdata.mso">
<link rel=OLE-Object-Data href="exam_student.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2010-11-15T05:31:56Z</o:LastSaved>
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
	font-size:11.0pt;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=934 style='border-collapse:
 collapse;table-layout:fixed;width:701pt'>
 <col width=111 style='mso-width-source:userset;mso-width-alt:3552;width:83pt'>
 <col width=132 style='mso-width-source:userset;mso-width-alt:4224;width:99pt'>
 <col width=226 style='mso-width-source:userset;mso-width-alt:7232;width:170pt'>
 <col width=125 style='mso-width-source:userset;mso-width-alt:4000;width:94pt'>
 <col width=99 style='mso-width-source:userset;mso-width-alt:3168;width:74pt'>
 <col width=156 style='mso-width-source:userset;mso-width-alt:4992;width:117pt'>
 <col width=85 style='mso-width-source:userset;mso-width-alt:2720;width:64pt'>
 <tr height=27 style='height:20.25pt'>
  <td colspan=7 height=27 class=xl24 width=934 style='height:20.25pt;
  width:701pt'>考试报名学员列表</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl25 style='height:14.25pt;border-top:none'>姓名</td>
  <td class=xl25 style='border-top:none;border-left:none'>系统编号</td>
  <td class=xl25 style='border-top:none;border-left:none'>所在企业</td>
  <td class=xl25 style='border-top:none;border-left:none'>所在学期</td>
  <td class=xl25 style='border-top:none;border-left:none'>考点</td>
  <td class=xl25 style='border-top:none;border-left:none'>考试批次</td>
  <td class=xl25 style='border-top:none;border-left:none'>报名状态</td>
 </tr>
 <%
 	rs = db.executeQuery(sql);
 	while(rs.next()) {
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'><%=fixnull(rs.getString("true_name")) %>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=fixnull(rs.getString("reg_no")) %>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=fixnull(rs.getString("enterprise_name")) %>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=fixnull(rs.getString("student_batch")) %>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=fixnull(rs.getString("site")) %>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=fixnull(rs.getString("exam_batch")) %>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=fixnull(rs.getString("status_flag")) %>　</td>
 </tr>
 <%}
 db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=111 style='width:83pt'></td>
  <td width=132 style='width:99pt'></td>
  <td width=226 style='width:170pt'></td>
  <td width=125 style='width:94pt'></td>
  <td width=99 style='width:74pt'></td>
  <td width=156 style='width:117pt'></td>
  <td width=85 style='width:64pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
 