<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>
<%@ page import="com.whaty.platform.entity.bean.*" %>

<%	response.setHeader("Content-Disposition", "attachment;filename=certificate_total_excel.xls"); %>

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
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy年MM月dd日");
  		return dateformat1.format(new Date());
	}
%>

<%
  String exambatch_id=fixnull(request.getParameter("exambatch_id") );
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="办证所需资料附件一（1）.files/filelist.xml">
<link rel=Edit-Time-Data href="办证所需资料附件一（1）.files/editdata.mso">
<link rel=OLE-Object-Data href="办证所需资料附件一（1）.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>微软用户</o:Author>
  <o:LastAuthor>MC SYSTEM</o:LastAuthor>
  <o:LastPrinted>2011-04-20T02:33:36Z</o:LastPrinted>
  <o:Created>2010-06-17T01:34:42Z</o:Created>
  <o:LastSaved>2011-04-20T06:05:53Z</o:LastSaved>
  <o:Company>微软中国</o:Company>
  <o:Version>11.5606</o:Version>
 </o:DocumentProperties>
</xml><![endif]-->
<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:1.0in .14in 1.0in .14in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;
	mso-page-orientation:landscape;}
.font6
	{color:windowtext;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font8
	{color:windowtext;
	font-size:8.0pt;
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
.style17
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
	mso-style-name:常规_Sheet1;}
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
.xl25
	{mso-style-parent:style17;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;
	white-space:normal;}
.xl26
	{mso-style-parent:style17;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl27
	{mso-style-parent:style17;
	font-size:10.0pt;
	text-align:center;
	vertical-align:bottom;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl28
	{mso-style-parent:style17;
	font-size:10.0pt;
	mso-number-format:0%;
	text-align:center;
	vertical-align:bottom;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl29
	{mso-style-parent:style17;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl30
	{mso-style-parent:style17;
	font-size:10.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl31
	{mso-style-parent:style0;
	mso-number-format:"\@";}
.xl32
	{mso-style-parent:style17;
	font-size:10.0pt;
	text-align:center;
	vertical-align:bottom;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl33
	{mso-style-parent:style17;
	font-size:10.0pt;
	text-align:center;
	vertical-align:bottom;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl34
	{mso-style-parent:style17;
	font-size:10.0pt;
	text-align:center;
	vertical-align:bottom;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl35
	{mso-style-parent:style17;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:top;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	white-space:normal;}
.xl36
	{mso-style-parent:style17;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:top;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl37
	{mso-style-parent:style17;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:top;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl38
	{mso-style-parent:style17;
	font-size:11.0pt;
	font-weight:700;
	vertical-align:top;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	white-space:normal;}
.xl39
	{mso-style-parent:style17;
	font-size:11.0pt;
	font-weight:700;
	vertical-align:top;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl40
	{mso-style-parent:style17;
	font-size:11.0pt;
	font-weight:700;
	vertical-align:top;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl41
	{mso-style-parent:style17;
	font-size:18.0pt;
	font-weight:700;
	text-align:center;
	white-space:normal;}
.xl42
	{mso-style-parent:style17;
	font-size:10.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:top;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl43
	{mso-style-parent:style17;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:top;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl44
	{mso-style-parent:style17;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:top;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl45
	{mso-style-parent:style17;
	font-size:10.0pt;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;
	white-space:normal;}
.xl46
	{mso-style-parent:style17;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl47
	{mso-style-parent:style17;
	font-size:10.0pt;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	white-space:normal;}
.xl48
	{mso-style-parent:style17;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl49
	{mso-style-parent:style17;
	font-size:10.0pt;
	text-align:center;
	vertical-align:bottom;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl50
	{mso-style-parent:style17;
	font-size:10.0pt;
	mso-number-format:0%;
	text-align:center;
	vertical-align:bottom;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl51
	{mso-style-parent:style17;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl52
	{mso-style-parent:style17;
	font-size:10.0pt;
	text-align:center;
	vertical-align:bottom;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl53
	{mso-style-parent:style17;
	font-size:10.0pt;
	text-align:center;
	vertical-align:bottom;
	white-space:normal;}
.xl54
	{mso-style-parent:style17;
	font-size:10.0pt;
	mso-number-format:0%;
	text-align:center;
	vertical-align:bottom;
	white-space:normal;}
.xl55
	{mso-style-parent:style17;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:top;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl56
	{mso-style-parent:style17;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	white-space:normal;}
.xl57
	{mso-style-parent:style17;
	font-size:10.0pt;
	text-align:center;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl58
	{mso-style-parent:style17;
	font-size:10.0pt;
	text-align:center;
	white-space:normal;}
.xl59
	{mso-style-parent:style17;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:top;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	white-space:normal;}
.xl60
	{mso-style-parent:style17;
	font-size:10.0pt;
	mso-number-format:0%;
	text-align:center;
	vertical-align:bottom;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
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
      <x:HorizontalResolution>600</x:HorizontalResolution>
      <x:VerticalResolution>600</x:VerticalResolution>
     </x:Print>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>5</x:ActiveRow>
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
  <x:WindowWidth>13875</x:WindowWidth>
  <x:WindowTopX>120</x:WindowTopX>
  <x:WindowTopY>30</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1199 style='border-collapse:
 collapse;table-layout:fixed;width:901pt'>
 <col width=32 style='mso-width-source:userset;mso-width-alt:1024;width:24pt'>
 <col class=xl31 width=121 style='mso-width-source:userset;mso-width-alt:3872;
 width:91pt'>
 <col width=94 style='mso-width-source:userset;mso-width-alt:3008;width:71pt'>
 <col width=35 span=2 style='mso-width-source:userset;mso-width-alt:1120;
 width:26pt'>
 <col width=36 style='mso-width-source:userset;mso-width-alt:1152;width:27pt'>
 <col width=77 style='mso-width-source:userset;mso-width-alt:2464;width:58pt'>
 <col width=46 style='mso-width-source:userset;mso-width-alt:1472;width:35pt'>
 <col width=64 style='mso-width-source:userset;mso-width-alt:2048;width:48pt'>
 <col width=56 style='mso-width-source:userset;mso-width-alt:1792;width:42pt'>
 <col width=110 style='mso-width-source:userset;mso-width-alt:3520;width:83pt'>
 <col width=53 style='mso-width-source:userset;mso-width-alt:1696;width:40pt'>
 <col width=95 style='mso-width-source:userset;mso-width-alt:3040;width:71pt'>
 <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>
 <col width=72 span=2 style='width:54pt'>
 <col width=128 style='mso-width-source:userset;mso-width-alt:4096;width:96pt'>
 <tr height=34 style='mso-height-source:userset;height:25.5pt'>
  <td colspan=17 height=34 class=xl41 width=1199 style='height:25.5pt;
  width:901pt'>中央企业班组长岗位管理能力资格认证项目结业资料汇总表</td>
 </tr>
 <%
 	dbpool db = new dbpool();
	MyResultSet rs = null;
	MyResultSet rs_enterprise = null;
	MyResultSet rs_student = null;
	String total_num="";
	String sql_total=
	 "select count(t.id) as total_num \n" +
	 "  from pe_bzz_examscore t\n" + 
	 " where t.exambatch_id = '"+exambatch_id+"'\n" + 
	 "   and t.total_grade in ('合格', '良好', '优秀')";
	
	rs = db.executeQuery(sql_total);
	while (rs != null && rs.next()) {
		total_num = fixnull(rs.getString("total_num"));
	}
	db.close(rs);

 %>
 
 <tr height=38 style='mso-height-source:userset;height:28.5pt'>
  <td colspan=8 height=38 class=xl43 width=476 style='height:28.5pt;width:358pt'>主办单位：清华大学继续教育学院</td>
  <td colspan=2 class=xl42 width=120 style='width:90pt'>学员人数：<%=total_num%></td>
  <td colspan=3 class=xl44 width=258 style='width:194pt'>起止日期<font class="font6">：</font><font
  class="font8">2009.12.11-2010.12.10</font></td>
  <td colspan=4 class=xl42 width=345 style='width:259pt'>总学时数：76学时</td>
 </tr>
 <%
 String sql_enterprise = 
	 "select distinct pe.id as enterprise_id,\n" +
	 "                pe.name as enterprise_name,\n" + 
	 "                pee.name as group_enterprise,\n" + 
	 "                pe.code,\n" + 
	 "                pee.code as group_code\n" + 
	 "  from pe_bzz_examscore t,\n" + 
	 "       pe_enterprise    pe,\n" + 
	 "       pe_enterprise    pee,\n" + 
	 "       pe_bzz_student   bs\n" + 
	 " where t.exambatch_id = '"+exambatch_id+"'\n" + 
	 "   and t.total_grade in ('合格', '良好', '优秀')\n" + 
	 "   and t.student_id = bs.id\n" + 
	 "   and bs.fk_enterprise_id = pe.id\n" + 
	 "   and pe.fk_parent_id = pee.id\n" + 
	 " order by pee.code, pe.code";
 
    rs_enterprise = db.executeQuery(sql_enterprise);
	while (rs_enterprise != null && rs_enterprise.next()) {
		String enterprise_id = fixnull(rs_enterprise.getString("enterprise_id"));
		String enterprise_name = fixnull(rs_enterprise.getString("enterprise_name"));
		String group_enterprise = fixnull(rs_enterprise.getString("group_enterprise"));
 %>
 <tr height=26 style='mso-height-source:userset;height:19.5pt'>
  <td colspan=17 height=26 class=xl35 width=1199 style='border-right:1.0pt solid black;
  height:19.5pt;width:901pt'>工作单位： 集团企业：<%=group_enterprise%></td>
 </tr>
 <tr height=24 style='mso-height-source:userset;height:18.0pt'>
  <td colspan=17 height=24 class=xl38 width=1199 style='border-right:1.0pt solid black;
  height:18.0pt;width:901pt'><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span>下属企业：<%=enterprise_name%></td>
 </tr>
 <tr height=48 style='height:36.0pt'>
  <td height=48 class=xl25 width=32 style='height:36.0pt;border-top:none;
  width:24pt'>序号</td>
  <td class=xl30 width=121 style='border-top:none;width:91pt'>系统编号</td>
  <td class=xl26 width=94 style='border-top:none;border-left:none;width:71pt'>姓名</td>
  <td class=xl26 width=35 style='border-top:none;border-left:none;width:26pt'>性别</td>
  <td class=xl26 width=35 style='border-top:none;border-left:none;width:26pt'>民族</td>
  <td class=xl26 width=36 style='border-top:none;border-left:none;width:27pt'>学历</td>
  <td class=xl26 width=77 style='border-top:none;border-left:none;width:58pt'>出生年月日</td>
  <td class=xl26 width=46 style='border-top:none;border-left:none;width:35pt'>职务</td>
  <td class=xl26 width=64 style='border-top:none;border-left:none;width:48pt'>生源省（含直辖市）</td>
  <td class=xl26 width=56 style='border-top:none;border-left:none;width:42pt'>生源市</td>
  <td class=xl26 width=110 style='border-top:none;border-left:none;width:83pt'>联系电话</td>
  <td class=xl26 width=53 style='border-top:none;border-left:none;width:40pt'>实际学时数</td>
  <td class=xl26 width=95 style='border-top:none;border-left:none;width:71pt'>实际学时数与总学时数比例（出勤率）</td>
  <td class=xl26 width=73 style='border-top:none;border-left:none;width:55pt'>在线作业完成情况</td>
  <td class=xl26 width=72 style='border-top:none;border-left:none;width:54pt'>在线自测情况</td>
  <td class=xl26 width=72 style='border-top:none;border-left:none;width:54pt'>考试成绩</td>
  <td class=xl29 width=128 style='border-top:none;border-left:none;width:96pt'>阅卷老师签字</td>
 </tr>
 <%
 int t=0;
 String sql_student = 
	/* "select bs.reg_no as reg_no,\n" +
	 "       bs.true_name as true_name,\n" + 
	 "       bs.gender gender,\n" + 
	 "       bs.education,\n" + 
	 "       bs.mobile_phone,\n" + 
	 "       to_char(bs.birthday, 'yyyymmdd') as birthday,\n" + 
	 "       pe.info_sheng as sheng,\n" + 
	 "       pe.info_shi as shi,\n" + 
	 "       time.jichu as jichu,\n" + 
	 "       decode(to_char(time.jichu / 76 * 100, 999.99),\n" + 
	 "              100.00,\n" + 
	 "              100,\n" + 
	 "              to_char(time.jichu / 76 * 100, 999.99)) as scale,\n" + 
	 "       t.total_grade,bs.folk \n" + 
	 "  from pe_bzz_examscore t,\n" + 
	 "       pe_enterprise pe,\n" + 
	 "       pe_enterprise pee,\n" + 
	 "       pe_bzz_student bs,\n" + 
	 "       (select stu_id, nvl(sum(mtime), 0) as jichu\n" + 
	 "          from (select ps.id as stu_id,\n" + 
	 "                       btc.time * (tcs.percent / 100) as mtime,\n" + 
	 "                       btc.time as ttime\n" + 
	 "                  from training_course_student tcs,\n" + 
	 "                       pr_bzz_tch_opencourse   bto,\n" + 
	 "                       pe_bzz_tch_course       btc,\n" + 
	 "                       pe_bzz_student          ps\n" + 
	 "                 where tcs.course_id = bto.id\n" + 
	 "                   and bto.fk_course_id = btc.id\n" + 
	 "                   and bto.flag_course_type =\n" + 
	 "                       '402880f32200c249012200c780c40001'\n" + 
	 "                   and tcs.student_id = ps.fk_sso_user_id)\n" + 
	 "         group by stu_id) time\n" + 
	 " where t.exambatch_id = '"+exambatch_id+"'\n" + 
	 "   and t.total_grade in ('合格', '良好', '优秀')\n" + 
	 "   and t.student_id = bs.id\n" + 
	 "   and bs.fk_enterprise_id = pe.id\n" + 
	 "   and pe.fk_parent_id = pee.id\n" + 
	 "   and time.stu_id = bs.id\n" + 
	 "   and pe.id = '"+enterprise_id+"'";*/
	 

	 "select bs.reg_no as reg_no,\n" +
	 "       bs.true_name as true_name,\n" + 
	 "       bs.gender gender,\n" + 
	 "       bs.education,\n" + 
	 "       bs.mobile_phone,\n" + 
	 "       to_char(bs.birthday, 'yyyymmdd') as birthday,\n" + 
	 "       pe.info_sheng as sheng,\n" + 
	 "       pe.info_shi as shi,\n" + 
	 "       t.total_grade,\n" + 
	 "       bs.folk\n" + 
	 "  from pe_bzz_examscore t,\n" + 
	 "       pe_enterprise pe,\n" + 
	 "       pe_enterprise pee,\n" + 
	 "       pe_bzz_student bs\n" + 
	 " where t.exambatch_id = '"+exambatch_id+"'\n" + 
	 "   and t.total_grade in ('合格', '良好', '优秀')\n" + 
	 "   and t.student_id = bs.id\n" + 
	 "   and bs.fk_enterprise_id = pe.id\n" + 
	 "   and pe.fk_parent_id = pee.id\n" + 
	 "   and pe.id = '"+enterprise_id+"'";

   // System.out.println(sql_student);
    rs_student = db.executeQuery(sql_student);
    int totalItem = db.countselect(sql_student);
	while (rs_student != null && rs_student.next()) {
		t++;
		String reg_no = fixnull(rs_student.getString("reg_no"));
		String true_name = fixnull(rs_student.getString("true_name"));
		true_name = true_name.replaceAll(" ","");
		true_name = true_name.replaceAll(" ","");
		true_name = true_name.replaceAll("　","");
		String gender = fixnull(rs_student.getString("gender"));
		String folk = fixnull(rs_student.getString("folk"));
		String education = fixnull(rs_student.getString("education"));
		String mobile_phone = fixnull(rs_student.getString("mobile_phone"));
		String birthday = fixnull(rs_student.getString("birthday"));
		String sheng = fixnull(rs_student.getString("sheng"));
		String shi = fixnull(rs_student.getString("shi"));
		String total_grade = fixnull(rs_student.getString("total_grade"));
 %>
 <%
 if(totalItem==1)
 {
	 %>
	 <tr height=20 style='height:15.0pt'>
	  <td height=20 class=xl47 width=32 style='height:15.0pt;border-top:none;
	  width:24pt' x:num><%=t%></td>
	  <td class=xl48 width=121 style='border-top:none;width:91pt'><%=reg_no%></td>
	  <td class=xl49 width=94 style='border-top:none;border-left:none;width:71pt'><%=true_name%></td>
	  <td class=xl49 width=35 style='border-top:none;border-left:none;width:26pt'><%=gender%></td>
	  <td class=xl49 width=35 style='border-top:none;border-left:none;width:26pt'><%=folk%></td>
	  <td class=xl49 width=36 style='border-top:none;border-left:none;width:27pt'><%=education%></td>
	  <td class=xl49 width=77 style='border-top:none;border-left:none;width:58pt'><%=birthday%></td>
	  <td class=xl49 width=46 style='border-top:none;border-left:none;width:35pt'>班组长</td>
	  <td class=xl49 width=64 style='border-top:none;border-left:none;width:48pt'><%=sheng%></td>
	  <td class=xl49 width=56 style='border-top:none;border-left:none;width:42pt'><%=shi%></td>
	  <td class=xl49 width=110 style='border-top:none;border-left:none;width:83pt'><%=mobile_phone%></td>
	  <td class=xl49 width=53 style='border-top:none;border-left:none;width:40pt'>76</td>
	  <td class=xl50 width=95 style='border-top:none;border-left:none;width:71pt'
	  x:num="1">100%</td>
	  <td class=xl49 width=73 style='border-top:none;border-left:none;width:55pt'>完成</td>
	  <td class=xl49 width=72 style='border-top:none;border-left:none;width:54pt'>通过</td>
	  <td class=xl49 width=72 style='border-top:none;border-left:none;width:54pt'><%=total_grade%></td>
	  <td rowspan=<%=totalItem%> class=xl32 width=128 style='border-bottom:1.0pt solid black;
	  border-top:none;width:96pt'>　</td>
	 </tr>
	 <%
 }
 else{
	 if(t==1)
	 {
	 %>
		 <tr height=19 style='height:14.25pt'>
		  <td height=19 class=xl45 width=32 style='height:14.25pt;border-top:none;
		  width:24pt' x:num><%=t%></td>
		  <td class=xl46 width=121 style='border-top:none;width:91pt'><%=reg_no%></td>
		  <td class=xl27 width=94 style='border-top:none;border-left:none;width:71pt'><%=true_name%></td>
		  <td class=xl27 width=35 style='border-top:none;border-left:none;width:26pt'><%=gender%></td>
		  <td class=xl27 width=35 style='border-top:none;border-left:none;width:26pt'><%=folk%></td>
		  <td class=xl27 width=36 style='border-top:none;border-left:none;width:27pt'><%=education%></td>
		  <td class=xl27 width=77 style='border-top:none;border-left:none;width:58pt'><%=birthday%></td>
		  <td class=xl27 width=46 style='border-top:none;border-left:none;width:35pt'>班组长</td>
		  <td class=xl27 width=64 style='border-top:none;border-left:none;width:48pt'><%=sheng%></td>
		  <td class=xl27 width=56 style='border-top:none;border-left:none;width:42pt'><%=shi%></td>
		  <td class=xl27 width=110 style='border-top:none;border-left:none;width:83pt'><%=mobile_phone%></td>
		  <td class=xl27 width=53 style='border-top:none;border-left:none;width:40pt'>76</td>
		  <td class=xl28 width=95 style='border-top:none;border-left:none;width:71pt'
		  x:num="1">100%</td>
		  <td class=xl27 width=73 style='border-top:none;border-left:none;width:55pt'>完成</td>
		  <td class=xl27 width=72 style='border-top:none;border-left:none;width:54pt'>通过</td>
		  <td class=xl27 width=72 style='border-top:none;border-left:none;width:54pt'><%=total_grade%></td>
		  <td rowspan=<%=totalItem%> class=xl32 width=128 style='border-bottom:1.0pt solid black;
			  border-top:none;width:96pt'>　</td>
		 </tr>
 <%}
	 else if(t!=totalItem && t!=1)
	 {
		%>
		 <tr height=19 style='height:14.25pt'>
		  <td height=19 class=xl45 width=32 style='height:14.25pt;border-top:none;
		  width:24pt' x:num><%=t%></td>
		  <td class=xl46 width=121 style='border-top:none;width:91pt'><%=reg_no%></td>
		  <td class=xl27 width=94 style='border-top:none;border-left:none;width:71pt'><%=true_name%></td>
		  <td class=xl27 width=35 style='border-top:none;border-left:none;width:26pt'><%=gender%></td>
		  <td class=xl27 width=35 style='border-top:none;border-left:none;width:26pt'><%=folk%></td>
		  <td class=xl27 width=36 style='border-top:none;border-left:none;width:27pt'><%=education%></td>
		  <td class=xl27 width=77 style='border-top:none;border-left:none;width:58pt'><%=birthday%></td>
		  <td class=xl27 width=46 style='border-top:none;border-left:none;width:35pt'>班组长</td>
		  <td class=xl27 width=64 style='border-top:none;border-left:none;width:48pt'><%=sheng%></td>
		  <td class=xl27 width=56 style='border-top:none;border-left:none;width:42pt'><%=shi%></td>
		  <td class=xl27 width=110 style='border-top:none;border-left:none;width:83pt'><%=mobile_phone%></td>
		  <td class=xl27 width=53 style='border-top:none;border-left:none;width:40pt'>76</td>
		  <td class=xl28 width=95 style='border-top:none;border-left:none;width:71pt'
		  x:num="1">100%</td>
		  <td class=xl27 width=73 style='border-top:none;border-left:none;width:55pt'>完成</td>
		  <td class=xl27 width=72 style='border-top:none;border-left:none;width:54pt'>通过</td>
		  <td class=xl27 width=72 style='border-top:none;border-left:none;width:54pt'><%=total_grade%></td>
		 </tr>
		<% 
	 } 
     else {%>
	    <tr height=20 style='height:15.0pt'>
	  <td height=20 class=xl47 width=32 style='height:15.0pt;border-top:none;
	  width:24pt' x:num><%=t%></td>
	  <td class=xl48 width=121 style='border-top:none;width:91pt'><%=reg_no%></td>
	  <td class=xl49 width=94 style='border-top:none;border-left:none;width:71pt'><%=true_name%></td>
	  <td class=xl49 width=35 style='border-top:none;border-left:none;width:26pt'><%=gender%></td>
	  <td class=xl49 width=35 style='border-top:none;border-left:none;width:26pt'><%=folk%></td>
	  <td class=xl49 width=36 style='border-top:none;border-left:none;width:27pt'><%=education%></td>
	  <td class=xl49 width=77 style='border-top:none;border-left:none;width:58pt'><%=birthday%></td>
	  <td class=xl49 width=46 style='border-top:none;border-left:none;width:35pt'>班组长</td>
	  <td class=xl49 width=64 style='border-top:none;border-left:none;width:48pt'><%=sheng%></td>
	  <td class=xl49 width=56 style='border-top:none;border-left:none;width:42pt'><%=shi%></td>
	  <td class=xl49 width=110 style='border-top:none;border-left:none;width:83pt'><%=mobile_phone%></td>
	  <td class=xl49 width=53 style='border-top:none;border-left:none;width:40pt'>76</td>
	  <td class=xl50 width=95 style='border-top:none;border-left:none;width:71pt'
	  x:num="1">100%</td>
	  <td class=xl49 width=73 style='border-top:none;border-left:none;width:55pt'>完成</td>
	  <td class=xl49 width=72 style='border-top:none;border-left:none;width:54pt'>通过</td>
	  <td class=xl49 width=72 style='border-top:none;border-left:none;width:54pt'><%=total_grade%></td>
	 </tr>
  <%}
	}%>
 <%}
    db.close(rs_student); %>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl58 style='height:15.0pt'></td>
  <td class=xl56></td>
  <td colspan=10 class=xl53 style='mso-ignore:colspan'></td>
  <td class=xl54></td>
  <td colspan=4 class=xl53 style='mso-ignore:colspan'></td>
 </tr>
 <%
	}
	db.close(rs_enterprise);
 %>
 
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=32 style='width:24pt'></td>
  <td width=121 style='width:91pt'></td>
  <td width=94 style='width:71pt'></td>
  <td width=35 style='width:26pt'></td>
  <td width=35 style='width:26pt'></td>
  <td width=36 style='width:27pt'></td>
  <td width=77 style='width:58pt'></td>
  <td width=46 style='width:35pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=56 style='width:42pt'></td>
  <td width=110 style='width:83pt'></td>
  <td width=53 style='width:40pt'></td>
  <td width=95 style='width:71pt'></td>
  <td width=73 style='width:55pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=128 style='width:96pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>



