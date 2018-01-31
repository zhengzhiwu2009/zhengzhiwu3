<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*,java.text.SimpleDateFormat"%>
<%@ page import="com.whaty.platform.entity.bean.*"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=baoming_register_exportZK.xls"); %>

<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}
	
	String dateFormat(Date date) {
		if(date == null) {
			return " ";
		}
		
  		SimpleDateFormat sf=new SimpleDateFormat("yyyy年MM月dd日");
  		return sf.format(date);
	}
	%>
	
	
	<%
		
		List<PeBzzStudent> stuList = (List<PeBzzStudent>)request.getAttribute("stuList");
		PeEnterprise peEnterprise = (PeEnterprise)request.getAttribute("peEnterprise");
		int stuCount = (Integer)request.getAttribute("stuCount");
		//System.out.println(stuList.size());
	 %>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="平台管理员导出学员表.files/filelist.xml">
<link rel=Edit-Time-Data href="平台管理员导出学员表.files/editdata.mso">
<link rel=OLE-Object-Data href="平台管理员导出学员表.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2009-08-14T05:10:35Z</o:LastSaved>
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
.style17
	{color:blue;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-style-name:超链接;
	mso-style-id:8;}
a:link
	{color:blue;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
a:visited
	{color:purple;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
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
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl25
	{mso-style-parent:style0;
	font-size:10.0pt;}
.xl26
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl27
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl28
	{mso-style-parent:style0;
	font-size:10.0pt;
	text-align:left;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl29
	{mso-style-parent:style17;
	color:blue;
	text-decoration:underline;
	text-underline-style:single;
	text-align:left;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl30
	{mso-style-parent:style0;
	font-size:10.0pt;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl31
	{mso-style-parent:style0;
	font-size:10.0pt;
	text-align:left;
	vertical-align:middle;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl32
	{mso-style-parent:style17;
	color:blue;
	text-decoration:underline;
	text-underline-style:single;
	text-align:left;
	vertical-align:middle;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl33
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl34
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl35
	{mso-style-parent:style0;
	font-size:10.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl36
	{mso-style-parent:style0;
	font-size:10.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl37
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-weight:700;
	text-align:center;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl38
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl39
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl40
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl41
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl42
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl43
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl44
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl45
	{mso-style-parent:style0;
	font-size:10.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
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
      <x:HorizontalResolution>600</x:HorizontalResolution>
      <x:VerticalResolution>600</x:VerticalResolution>
     </x:Print>
     <x:CodeName>Sheet1</x:CodeName>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>18</x:ActiveRow>
       <x:ActiveCol>1</x:ActiveCol>
       <x:RangeSelection>$A$1:$A$1</x:RangeSelection>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=1183 style='border-collapse:
 collapse;table-layout:fixed;width:888pt'>
 <col width=99 style='mso-width-source:userset;mso-width-alt:3168;width:74pt'>
 <col width=72 style='width:54pt'>
 <col width=76 span=2 style='mso-width-source:userset;mso-width-alt:2432;
 width:57pt'>
 <col width=72 style='width:54pt'>
 <col width=77 style='mso-width-source:userset;mso-width-alt:2464;width:58pt'>
 <col width=91 style='mso-width-source:userset;mso-width-alt:2912;width:68pt'>
 <col width=94 style='mso-width-source:userset;mso-width-alt:3008;width:71pt'>
 <col width=72 span=4 style='width:54pt'>
 <col width=238 style='mso-width-source:userset;mso-width-alt:7616;width:179pt'>
 <tr height=30 style='height:22.5pt'>
  <td colspan=14 height=30 class=xl37 width=1183 style='height:22.5pt;
  width:888pt'>导出学员信息表</td>
 </tr>
 

 <tr height=26 style='mso-height-source:userset;height:19.5pt'>
  <td colspan=14 height=26 class=xl39 style='border-right:.5pt solid black;
  height:19.5pt'>企业名称：<%=fixnull(peEnterprise.getName())%></td>
 </tr>
 <tr height=26 style='mso-height-source:userset;height:19.5pt'>
  <td colspan=14 height=26 class=xl39 style='border-right:.5pt solid black;
  height:19.5pt'>单位地址：<%=fixnull(peEnterprise.getAddress())%></td>
 </tr>
 <tr height=26 style='mso-height-source:userset;height:19.5pt'>
  <td colspan=14 height=26 class=xl40 style='border-right:.5pt solid black;
  height:19.5pt'>学员总人数：<span style='mso-spacerun:yes'>&nbsp; </span><%=stuCount%><span
  style='mso-spacerun:yes'>&nbsp;&nbsp; </span>人</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'>　</td>
  <td class=xl27 style='border-top:none;border-left:none'>姓名</td>
  <td colspan=4 class=xl33 style='border-right:.5pt solid black;border-left:
  none'>部门</td>
  <td class=xl27 style='border-top:none;border-left:none'>职务</td>
  <td colspan=2 class=xl33 style='border-right:.5pt solid black;border-left:
  none'>电话</td>
  <td colspan=2 class=xl33 style='border-right:.5pt solid black;border-left:
  none'>手机</td>
  <td class=xl27 style='border-top:none;border-left:none'>传真</td>
  <td class=xl27 style='border-top:none;border-left:none' colspan='2'>电子邮件</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'>企业负责人</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(peEnterprise.getFzrName())%></td>
  <td colspan=4 class=xl35 style='border-right:.5pt solid black;border-left:
  none'><%=fixnull(peEnterprise.getFzrDepart())%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(peEnterprise.getFzrPosition())%></td>
  <td colspan=2 class=xl35 style='border-right:.5pt solid black;border-left:
  none' ><%=fixnull(peEnterprise.getFzrPhone())%></td>
  <td colspan=2 class=xl35 style='border-right:.5pt solid black;border-left:
  none' >
  <%=fixnull(peEnterprise.getFzrMobile())%>
</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(peEnterprise.getFax())%></td>
  <td class=xl29 style='border-top:none;border-left:none' colspan='2'><a
  href="<%=fixnull(peEnterprise.getFzrEmail())%>"><%=fixnull(peEnterprise.getFzrEmail())%></a></td>
 </tr>
 
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'>企业联系人</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(peEnterprise.getLxrName())%></td>
  <td colspan=4 class=xl35 style='border-right:.5pt solid black;border-left:none'><%=fixnull(peEnterprise.getLxrDepart())%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fixnull(peEnterprise.getLxrPosition())%></td>
  <td colspan=2 class=xl35 style='border-right:.5pt solid black;border-left:none' ><%=fixnull(peEnterprise.getLxrPhone())%></td>
  <td colspan=2 class=xl35 style='border-right:.5pt solid black;border-left:
  none'><%=fixnull(peEnterprise.getLxrMobile())%></td>
  <td class=xl28 style='border-top:none;border-left:none' ><%=fixnull(peEnterprise.getFax())%></td>
  <td class=xl29 style='border-top:none;border-left:none' colspan='2'><a
  href="mailto:<%=fixnull(peEnterprise.getLxrEmail())%>"><%=fixnull(peEnterprise.getLxrEmail())%></a></td>
 </tr>

 <tr height=32 style='mso-height-source:userset;height:24.0pt'>
  <td rowspan=2 height=51 class=xl24 width=99 style='height:38.25pt;border-top:
  none;width:74pt'>学员序号</td>
  <td rowspan=2 class=xl24 width=72 style='border-top:none;width:54pt'>姓名</td>
  <td rowspan=2 class=xl42 width=76 style='border-bottom:.5pt solid black;
  border-top:none;width:57pt'>学号</td>
  <td rowspan=2 class=xl42 width=76 style='border-bottom:.5pt solid black;
  border-top:none;width:57pt'>密码</td>
  <td rowspan=2 class=xl24 width=72 style='border-top:none;width:54pt'>性别</td>
  <td rowspan=2 class=xl24 width=77 style='border-top:none;width:58pt'>民族</td>
  <td rowspan=2 class=xl24 width=91 style='border-top:none;width:68pt'>学历</td>
  <td rowspan=2 class=xl24 width=94 style='border-top:none;width:71pt'>出生年月日</td>
  <td colspan=2 class=xl24 width=144 style='border-left:none;width:108pt'>工作单位</td>
  <td colspan=2 class=xl24 width=144 style='border-left:none;width:108pt'>联系电话</td>
  <td rowspan=2 class=xl24 width=160 style='border-top:none;width:179pt'>电子邮件</td>
  <td rowspan=2 class=xl24 width=150 style='border-top:none;width:179pt'>导出时间</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 width=72 style='height:14.25pt;border-top:none;
  border-left:none;width:54pt'>集团公司</td>
  <td class=xl24 width=72 style='border-top:none;border-left:none;width:54pt'>分公司</td>
  <td class=xl24 width=72 style='border-top:none;border-left:none;width:54pt'>手机</td>
  <td class=xl24 width=72 style='border-top:none;border-left:none;width:54pt'>固定电话</td>
 </tr>
  
  <%
 	int index=1;
 	for(PeBzzStudent stu : stuList){

 %>
 <tr class=xl25 height=24 style='mso-height-source:userset;height:18.0pt'>
  <td height=24 class=xl30 width=99 style='height:18.0pt;border-top:none;
  width:74pt' x:num><%=index++%></td>
  <td class=xl31 width=72 style='border-top:none;border-left:none;width:54pt'><%=fixnull(stu.getTrueName())%></td>
  <td class=xl31 width=76 style='border-top:none;border-left:none;width:57pt'
  ><%=fixnull(stu.getRegNo())%></td>
  <td class=xl31 width=76 style='border-top:none;border-left:none;width:57pt'><%=fixnull(stu.getSsoUser().getPassword())%></td>
  <td class=xl31 width=72 style='border-top:none;border-left:none;width:54pt'><%=fixnull(stu.getGender())%></td>
  <td class=xl31 width=77 style='border-top:none;border-left:none;width:58pt'><%=fixnull(stu.getFolk())%></td>
  <td class=xl31 width=91 style='border-top:none;border-left:none;width:68pt'><%=fixnull(stu.getEducation())%></td>
  <td class=xl31 width=94 style='border-top:none;border-left:none;width:71pt'><%=fixnull(dateFormat(stu.getBirthdayDate()))%></td>
  <td class=xl31 width=72 style='border-top:none;border-left:none;width:54pt'><%=fixnull(stu.getPeEnterprise().getPeEnterprise().getName())%></td>
  <td class=xl31 width=72 style='border-top:none;border-left:none;width:54pt'><%=fixnull(stu.getPeEnterprise().getName())%></td>
  <td class=xl31 width=72 style='border-top:none;border-left:none;width:54pt'><%=fixnull(stu.getMobilePhone())%></td>
  <td class=xl31 width=72 style='border-top:none;border-left:none;width:54pt'><%=fixnull(stu.getPhone())%></td>
  <td class=xl32 width=160 style='border-top:none;border-left:none;width:179pt'><a href="mailto:<%=fixnull(stu.getEmail())%>"><%=fixnull(stu.getEmail())%></a></td>
   <td class=xl31 width=150 style='border-top:none;border-left:none;width:54pt'><%=dateFormat(stu.getExportDate())%></td>
 </tr>
 <%
 }
 %>
 
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=99 style='width:74pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=76 style='width:57pt'></td>
  <td width=76 style='width:57pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=77 style='width:58pt'></td>
  <td width=91 style='width:68pt'></td>
  <td width=94 style='width:71pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=238 style='width:179pt'></td>
 </tr>
 <![endif]>
</table>
</body>
</html>
