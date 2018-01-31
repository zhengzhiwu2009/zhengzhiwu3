<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>
<%@page import="org.springframework.web.bind.EscapedErrors"%>

<%	response.setHeader("Content-Disposition", "attachment;filename=stat_study_export_progress.xls"); %>

<%!
	String fixnull(String str)
    {
        if(str == null || str.equals("null") || str.equals(""))
		str = "";
		return str;
     }
     
    private String getNumber(double num)
    {

        DecimalFormat df = new DecimalFormat("##0.000");//使用系统默认的格式  

        return df.format(num);
     }
     
    String getCurDate() 
	{
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
  		return dateformat1.format(new Date());
	}

%>

<%
		String sStime = fixnull(request.getParameter("sStime"));
		String eStime = fixnull(request.getParameter("eStime"));
		
		String enterprise_id = fixnull(request.getParameter("enterprise_id"));
		String sub_enterprise_id = fixnull(request.getParameter("sub_enterprise_id"));
		
		String batch_id = fixnull(request.getParameter("batch_id"));
		
		dbpool db = new dbpool();
		MyResultSet rs = null;
		
		String sql_con = "";
		if(batch_id != null && !batch_id.equals("")) {
			sql_con=" and ps.fk_batch_id = '" + batch_id + "' \n";
		}
		if(!"all".equals(enterprise_id)) {
			if(sub_enterprise_id.equals("")) {
				sql_con+=" and (pe.id='"+enterprise_id+"' or pe.fk_parent_id='"+enterprise_id+"') ";
			} else {
				sql_con+=" and pe.id='"+sub_enterprise_id+"'";
			}
		}
		String batchName = "";
		String sql = "select batch.name as bname from pe_bzz_batch batch where batch.id='"+batch_id+"'";
		rs = db.executeQuery(sql);
		
		if(rs.next()) {
			batchName = rs.getString("bname");
		}
		db.close(rs);
		
		sql =" select a.id,s.true_name,s.reg_no,s.mobile_phone, e.name,stime  from ( \n"
			+ "  select ps.id, sum(ce.time * (ts.percent / 100)) as stime \n"
			+ "    from sso_user                su, \n"
			+ "         training_course_student ts, \n"
			+ "         pe_bzz_student          ps, \n"
			+ "         pr_bzz_tch_opencourse   co, \n"
			+ "         pe_bzz_tch_course       ce, \n"
			+ "         pe_enterprise           pe \n"
			+ "   where ps.fk_sso_user_id = su.id and su.flag_isvalid='2' \n"
			+ "     and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006'"
			+ "     and su.id = ts.student_id \n"
			+ "     and pe.id = ps.fk_enterprise_id \n"
			+ "     and co.id = ts.course_id \n"
			+ "     and co.fk_course_id = ce.id \n"
			+ "     and ps.fk_batch_id = co.fk_batch_id \n"
   
			+ "    " + sql_con 
			+ "   group by ps.id ) a,pe_bzz_student s,pe_enterprise e   \n"
			+ "   where  \n"
			+ "   stime > " + sStime + " and stime < " + eStime + "\n" 
			+ "   and s.id=a.id and e.id=s.fk_enterprise_id order by stime desc,e.name";

			//System.out.println(sql);
	  
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="个体学习进度表.files/filelist.xml">
<link rel=Edit-Time-Data href="个体学习进度表.files/editdata.mso">
<link rel=OLE-Object-Data href="个体学习进度表.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2010-04-29T02:20:48Z</o:LastSaved>
  <o:Version>11.9999</o:Version>
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
	font-size:10.5pt;
	text-align:left;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	font-size:10.5pt;
	text-align:left;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;}
.xl26
	{mso-style-parent:style0;
	font-size:10.5pt;
	text-align:center;
	border:1.0pt solid windowtext;
	background:#FFCC99;
	mso-pattern:auto none;}
.xl27
	{mso-style-parent:style0;
	font-size:10.5pt;
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	background:#FFCC99;
	mso-pattern:auto none;}
.xl28
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:center;}
.xl29
	{mso-style-parent:style0;
	text-align:center;}
.xl30
	{mso-style-parent:style0;
	text-align:right;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;}
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
     <x:CodeName>Sheet1</x:CodeName>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:RangeSelection>$A$1:$D$1</x:RangeSelection>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=762 style='border-collapse:
 collapse;table-layout:fixed;width:572pt'>
 <col width=91 style='mso-width-source:userset;mso-width-alt:2912;width:68pt'>
 <col width=194 style='mso-width-source:userset;mso-width-alt:6208;width:146pt'>
 <col width=395 style='mso-width-source:userset;mso-width-alt:12640;width:296pt'>
 <col width=82 style='mso-width-source:userset;mso-width-alt:2624;width:62pt'>
 <tr height=42 style='mso-height-source:userset;height:31.5pt'>
  <td colspan=5 height=42 class=xl28 width=762 style='height:31.5pt;width:572pt'><%=batchName%><span
  style='mso-spacerun:yes'>&nbsp; </span><%=sStime%>至<%=eStime%> 学时 个体学习进度</td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td colspan=5 height=20 class=xl30 style='height:15.0pt'>导出时间：<font
  class="font7"><%=getCurDate()%></font></td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl26 style='height:15.0pt;border-top:none'>姓名</td>
  <td class=xl27 style='border-top:none'>学号</td>
  <td class=xl27 style='border-top:none'>手机号</td>
  <td class=xl27 style='border-top:none'>所在企业</td>
  <td class=xl27 style='border-top:none'>总学时</td>
 </tr>
  <%rs = db.executeQuery(sql);
			
	while(rs.next()) 
	{
		String sName = fixnull(rs.getString("true_name"));
		String regNo = fixnull(rs.getString("reg_no"));
		String mobilePhone = fixnull(rs.getString("mobile_phone"));
		String enterprise = fixnull(rs.getString("name"));
		double sTime = rs.getDouble("stime");
 %>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl24 style='height:15.0pt'><%=sName%></td>
  <td class=xl25><%=regNo%></td>
  <td class=xl25><%=mobilePhone%></td>
  <td class=xl25><%=enterprise%></td>
  <td class=xl25 x:num><%=getNumber(sTime)%></td>
 </tr>
  <%
 	}
 	db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=91 style='width:68pt'></td>
  <td width=194 style='width:146pt'></td>
  <td width=194 style='width:146pt'></td>
  <td width=395 style='width:296pt'></td>
  <td width=82 style='width:62pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>


 

