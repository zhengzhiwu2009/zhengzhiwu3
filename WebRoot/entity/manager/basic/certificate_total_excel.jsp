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
<link rel=File-List href="certificate_book.files/filelist.xml">
<link rel=Edit-Time-Data href="certificate_book.files/editdata.mso">
<link rel=OLE-Object-Data href="certificate_book.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>微软用户</o:Author>
  <o:LastAuthor>MC SYSTEM</o:LastAuthor>
  <o:Created>2011-04-28T06:10:57Z</o:Created>
  <o:LastSaved>2011-04-28T07:11:28Z</o:LastSaved>
  <o:Company>微软中国</o:Company>
  <o:Version>11.5606</o:Version>
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
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font9
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
.style41
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
.xl66
	{mso-style-parent:style41;
	font-size:10.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl67
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl68
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl69
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:0%;
	text-align:center;
	vertical-align:bottom;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl70
	{mso-style-parent:style41;
	font-size:18.0pt;
	font-weight:700;
	text-align:center;
	white-space:normal;}
.xl71
	{mso-style-parent:style41;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:top;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl72
	{mso-style-parent:style41;
	font-size:10.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:top;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl73
	{mso-style-parent:style41;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:top;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl74
	{mso-style-parent:style41;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:top;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	white-space:normal;}
.xl75
	{mso-style-parent:style41;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:top;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl76
	{mso-style-parent:style41;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:top;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl77
	{mso-style-parent:style41;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	vertical-align:top;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	white-space:normal;}
.xl78
	{mso-style-parent:style41;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	vertical-align:top;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl79
	{mso-style-parent:style41;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	vertical-align:top;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl80
	{mso-style-parent:style41;
	font-size:10.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;
	white-space:normal;}
.xl81
	{mso-style-parent:style41;
	font-size:10.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl82
	{mso-style-parent:style41;
	font-size:10.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl83
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;
	white-space:normal;}
.xl84
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:bottom;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl85
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:bottom;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl86
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:bottom;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl87
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:bottom;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl88
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	white-space:normal;}
.xl89
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:bottom;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl90
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:bottom;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl91
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:0%;
	text-align:center;
	vertical-align:bottom;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl92
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:bottom;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl93
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:0%;
	text-align:center;
	vertical-align:bottom;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl94
	{mso-style-parent:style41;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:bottom;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:.5pt solid windowtext;
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
      <x:VerticalResolution>0</x:VerticalResolution>
     </x:Print>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>5</x:ActiveRow>
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
  <x:WindowHeight>9450</x:WindowHeight>
  <x:WindowWidth>14940</x:WindowWidth>
  <x:WindowTopX>240</x:WindowTopX>
  <x:WindowTopY>15</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1158 style='border-collapse:
 collapse;table-layout:fixed;width:869pt'>
 <col width=52 style='mso-width-source:userset;mso-width-alt:1664;width:39pt'>
 <col width=89 style='mso-width-source:userset;mso-width-alt:2848;width:67pt'>
 <col width=60 style='mso-width-source:userset;mso-width-alt:1920;width:45pt'>
 <col width=57 style='mso-width-source:userset;mso-width-alt:1824;width:43pt'>
 <col width=56 span=2 style='mso-width-source:userset;mso-width-alt:1792;
 width:42pt'>
 <col width=81 style='mso-width-source:userset;mso-width-alt:2592;width:61pt'>
 <col width=59 style='mso-width-source:userset;mso-width-alt:1888;width:44pt'>
 <col width=72 span=9 style='width:54pt'>
 <tr height=30 style='height:22.5pt'>
  <td colspan=17 height=30 class=xl70 width=1158 style='height:22.5pt;
  width:869pt'>中央企业班组长岗位管理能力资格认证项目结业资料汇总表</td>
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
 <tr height=27 style='mso-height-source:userset;height:20.25pt'>
  <td colspan=8 height=27 class=xl71 width=510 style='height:20.25pt;
  width:383pt'>主办单位：清华大学继续教育学院</td>
  <td colspan=2 class=xl72 width=144 style='width:108pt'>学员人数：<%=total_num%></td>
  <td colspan=7 class=xl73 width=504 style='width:378pt'>起止日期<font class="font7">：</font><font
  class="font9">2009.12.11-2010.12.10</font></td>
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
 <tr height=20 style='height:15.0pt'>
  <td colspan=17 height=20 class=xl74 width=1158 style='border-right:1.0pt solid black;
  height:15.0pt;width:869pt'>工作单位： 集团企业：<%=group_enterprise%></td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td colspan=17 height=20 class=xl77 width=1158 style='border-right:1.0pt solid black;
  height:15.0pt;width:869pt'><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span>下属企业：<%=enterprise_name%></td>
 </tr>
 <tr height=48 style='height:36.0pt'>
  <td height=48 class=xl80 width=52 style='height:36.0pt;width:39pt'>序号</td>
  <td class=xl66 width=89 style='width:67pt'>系统编号</td>
  <td class=xl66 width=60 style='width:45pt'>姓名</td>
  <td class=xl66 width=57 style='width:43pt'>性别</td>
  <td class=xl66 width=56 style='width:42pt'>民族</td>
  <td class=xl66 width=56 style='width:42pt'>学历</td>
  <td class=xl66 width=81 style='width:61pt'>出生年月日</td>
  <td class=xl66 width=59 style='width:44pt'>职务</td>
  <td class=xl66 width=72 style='width:54pt'>生源省（含直辖市）</td>
  <td class=xl66 width=72 style='width:54pt'>生源市</td>
  <td class=xl81 width=72 style='width:54pt'>联系电话</td>
  <td class=xl81 width=72 style='width:54pt'>必修课学时数</td>
  <td class=xl81 width=72 style='width:54pt'>选修课学时数</td>
  <td class=xl81 width=72 style='width:54pt'>必修课完成率</td>
  <td class=xl81 width=72 style='width:54pt'>必修课平时成绩</td>
  <td class=xl66 width=72 style='width:54pt'>考试成绩</td>
  <td class=xl82 width=72 style='width:54pt'>综合成绩</td>
 </tr>
 <%
 int t=0;
/* String sql_student = 
	 "select bs.reg_no as reg_no,\n" +
	 "       bs.true_name as true_name,\n" + 
	 "       bs.gender gender,\n" + 
	 "       bs.education,\n" + 
	 "       bs.phone,\n" + 
	 "       to_char(bs.birthday, 'yyyymmdd') as birthday,\n" + 
	 "       pe.info_sheng as sheng,\n" + 
	 "       pe.info_shi as shi,\n" + 
	 "       t.total_grade,\n" + 
	 "       bs.folk,\n" + 
	 "       replace(to_char(nvl(t.test_score, 0),'999'),' ','') as test_score,\n" + 
	 "       replace(to_char(nvl(t.score, 0),'999'),' ','') as exam_score,\n" + 
	 "       replace(to_char(nvl(t.total_score, 0),'999'),' ','') as total_score,\n" + 
	 "       replace(to_char(nvl(time.tisheng, 0),'990.9'),' ','') as tisheng\n" + 
	 "  from pe_bzz_examscore t,\n" + 
	 "       pe_enterprise pe,\n" + 
	 "       pe_enterprise pee,\n" + 
	 "       pe_bzz_student bs,\n" + 
	 "       (select stu_id, nvl(sum(mtime), 0) as tisheng\n" + 
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
	 "                       '402880f32200c249012200c7f8b30002'\n" + 
	 "                   and tcs.student_id = ps.fk_sso_user_id)\n" + 
	 "         group by stu_id) time\n" + 
	 " where t.exambatch_id = '"+exambatch_id+"'\n" + 
	 "   and t.total_grade in ('合格', '良好', '优秀')\n" + 
	 "   and t.student_id = bs.id\n" + 
	 "   and bs.fk_enterprise_id = pe.id\n" + 
	 "   and pe.fk_parent_id = pee.id\n" + 
	 "   and bs.id = time.stu_id\n" + 
	 "   and pe.id = '"+enterprise_id+"'";*/
	 
	 String sql_student=

		 "select bs.reg_no as reg_no,\n" +
		 "       bs.true_name as true_name,\n" + 
		 "       bs.gender gender,\n" + 
		 "       bs.education,\n" + 
		 "       bs.phone,\n" + 
		 "       to_char(bs.birthday, 'yyyymmdd') as birthday,\n" + 
		 "       pe.info_sheng as sheng,\n" + 
		 "       pe.info_shi as shi,\n" + 
		 "       t.total_grade,\n" + 
		 "       bs.folk,\n" + 
		 "       replace(to_char(nvl(t.test_score, 0), '999'), ' ', '') as test_score,\n" + 
		 "       replace(to_char(nvl(t.score, 0), '999'), ' ', '') as exam_score,\n" + 
		 "       replace(to_char(nvl(t.total_score, 0), '999'), ' ', '') as total_score,\n" + 
		 "       nvl(time.tisheng,0) as tisheng\n" + 
		 "  from pe_bzz_examscore t,\n" + 
		 "       pe_enterprise pe,\n" + 
		 "       pe_enterprise pee,\n" + 
		 "       pe_bzz_student bs,\n" + 
		 "       (select stu_id, nvl(sum(ttime), 0) as tisheng\n" + 
		 "          from (select ps.id as stu_id, btc.time as ttime\n" + 
		 "                  from training_course_student tcs,\n" + 
		 "                       pr_bzz_tch_opencourse   bto,\n" + 
		 "                       pe_bzz_tch_course       btc,\n" + 
		 "                       pe_bzz_student          ps\n" + 
		 "                 where tcs.course_id = bto.id\n" + 
		 "                   and bto.fk_course_id = btc.id\n" + 
		 "                   and bto.flag_course_type =\n" + 
		 "                       '402880f32200c249012200c7f8b30002'\n" + 
		 "                   and tcs.percent >= 50\n" + 
		 "                   and tcs.student_id = ps.fk_sso_user_id)\n" + 
		 "         group by stu_id) time\n" + 
		 " where t.exambatch_id = '"+exambatch_id+"'\n" + 
		 "   and t.total_grade in ('合格', '良好', '优秀')\n" + 
		 "   and t.student_id = bs.id\n" + 
		 "   and bs.fk_enterprise_id = pe.id\n" + 
		 "   and pe.fk_parent_id = pee.id\n" + 
		 "   and bs.id = time.stu_id(+)\n" + 
		 "   and pe.id = '"+enterprise_id+"'";

     
	rs_student = db.executeQuery(sql_student);
	int totalItem = db.countselect(sql_student);
	while (rs_student != null && rs_student.next()) 
	{
		t++;
		String reg_no = fixnull(rs_student.getString("reg_no"));
		String true_name = fixnull(rs_student.getString("true_name"));
		true_name = true_name.replaceAll(" ","");
		true_name = true_name.replaceAll(" ","");
		true_name = true_name.replaceAll("　","");
		String gender = fixnull(rs_student.getString("gender"));
		String folk = fixnull(rs_student.getString("folk"));
		String education = fixnull(rs_student.getString("education"));
		String mobile_phone = fixnull(rs_student.getString("phone"));
		if(mobile_phone.contains("@")||mobile_phone.contains("辽宁"))
		{
			mobile_phone="";
		}
		String birthday = fixnull(rs_student.getString("birthday"));
		String sheng = fixnull(rs_student.getString("sheng"));
		String shi = fixnull(rs_student.getString("shi"));
		String test_score = fixnull(rs_student.getString("test_score"));
		String total_grade = fixnull(rs_student.getString("total_grade"));
		String exam_score = fixnull(rs_student.getString("exam_score"));
		String total_score = fixnull(rs_student.getString("total_score"));
		String tisheng = fixnull(rs_student.getString("tisheng"));
		
 %>
 <%
 if(totalItem==1)
 {
	 %>
	 
	<tr height=20 style='height:15.0pt'>
  <td height=20 class=xl88 width=52 style='height:15.0pt;width:39pt'><%=t%></td>
  <td class=xl68 width=89 style='width:67pt'><%=reg_no%></td>
  <td class=xl89 width=60 style='width:45pt'><%=true_name%></td>
  <td class=xl89 width=57 style='width:43pt'><%=gender%></td>
  <td class=xl89 width=56 style='width:42pt'><%=folk%></td>
  <td class=xl89 width=56 style='width:42pt'><%=education%></td>
  <td class=xl89 width=81 style='width:61pt'><%=birthday%></td>
  <td class=xl89 width=59 style='width:44pt'>班组长</td>
  <td class=xl89 width=72 style='width:54pt'><%=sheng%></td>
  <td class=xl89 width=72 style='width:54pt'><%=shi%></td>
  <td class=xl89 width=72 style='width:54pt'><%=mobile_phone%></td>
  <td class=xl89 width=72 style='width:54pt'>76</td>
  <td class=xl89 width=72 style='width:54pt'><%=tisheng%></td>
  <td class=xl69 width=72 style='width:54pt' x:num="1">100%</td>
  <td class=xl94 width=72 style='border-left:none;width:54pt'><%=test_score%></td>
  <td class=xl94 width=72 style='border-left:none;width:54pt'><%=exam_score%></td>
  <td class=xl90 width=72 style='border-left:none;width:54pt'><%=total_score%></td>
 </tr>
 <%}
 else 
 {
 	if(t==1)
	 {
	 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl83 width=52 style='height:14.25pt;width:39pt' x:num><%=t%></td>
  <td class=xl67 width=89 style='width:67pt'><%=reg_no%></td>
  <td class=xl84 width=60 style='width:45pt'><%=true_name%></td>
  <td class=xl84 width=57 style='width:43pt'><%=gender%></td>
  <td class=xl84 width=56 style='width:42pt'><%=folk%></td>
  <td class=xl84 width=56 style='width:42pt'><%=education%></td>
  <td class=xl84 width=81 style='width:61pt'><%=birthday%></td>
  <td class=xl84 width=59 style='width:44pt'>班组长</td>
  <td class=xl84 width=72 style='width:54pt'><%=sheng%></td>
  <td class=xl84 width=72 style='width:54pt'><%=shi%></td>
  <td class=xl85 width=72 style='width:54pt'><%=mobile_phone%></td>
  <td class=xl85 width=72 style='width:54pt'>76</td>
  <td class=xl86 width=72 style='border-top:none;border-left:none;width:54pt'><%=tisheng%></td>
  <td class=xl91 width=72 style='border-top:none;border-left:none;width:54pt'
  x:num="1">100%</td>
  <td class=xl86 width=72 style='border-top:none;border-left:none;width:54pt'><%=test_score%></td>
  <td class=xl87 width=72 style='border-top:none;border-left:none;width:54pt'><%=exam_score%></td>
  <td class=xl92 width=72 style='border-top:none;border-left:none;width:54pt'><%=total_score%></td>
 </tr>
  <%}
	else if(t!=totalItem && t!=1)
	{
		%>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl83 width=52 style='height:14.25pt;width:39pt'><%=t%></td>
  <td class=xl67 width=89 style='width:67pt'><%=reg_no%></td>
  <td class=xl84 width=60 style='width:45pt'><%=true_name%></td>
  <td class=xl84 width=57 style='width:43pt'><%=gender%></td>
  <td class=xl84 width=56 style='width:42pt'><%=folk%></td>
  <td class=xl84 width=56 style='width:42pt'><%=education%></td>
  <td class=xl84 width=81 style='width:61pt'><%=birthday%></td>
  <td class=xl84 width=59 style='width:44pt'>班组长</td>
  <td class=xl84 width=72 style='width:54pt'><%=sheng%></td>
  <td class=xl84 width=72 style='width:54pt'><%=shi%></td>
  <td class=xl84 width=72 style='width:54pt'><%=mobile_phone%></td>
  <td class=xl84 width=72 style='width:54pt'>76</td>
  <td class=xl87 width=72 style='border-top:none;border-left:none;width:54pt'><%=tisheng%></td>
  <td class=xl93 width=72 style='border-top:none;border-left:none;width:54pt' x:num="1">100%</td>
  <td class=xl87 width=72 style='border-top:none;border-left:none;width:54pt'><%=test_score%></td>
  <td class=xl87 width=72 style='border-top:none;border-left:none;width:54pt'><%=exam_score%></td>
  <td class=xl92 width=72 style='border-top:none;border-left:none;width:54pt'><%=total_score%></td>
 </tr>
	<% 
	 } 
    else 
    {%>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl88 width=52 style='height:15.0pt;width:39pt'><%=t%></td>
  <td class=xl68 width=89 style='width:67pt'><%=reg_no%></td>
  <td class=xl89 width=60 style='width:45pt'><%=true_name%></td>
  <td class=xl89 width=57 style='width:43pt'><%=gender%></td>
  <td class=xl89 width=56 style='width:42pt'><%=folk%></td>
  <td class=xl89 width=56 style='width:42pt'><%=education%></td>
  <td class=xl89 width=81 style='width:61pt'><%=birthday%></td>
  <td class=xl89 width=59 style='width:44pt'>班组长</td>
  <td class=xl89 width=72 style='width:54pt'><%=sheng%></td>
  <td class=xl89 width=72 style='width:54pt'><%=shi%></td>
  <td class=xl89 width=72 style='width:54pt'><%=mobile_phone%></td>
  <td class=xl89 width=72 style='width:54pt'>76</td>
  <td class=xl89 width=72 style='width:54pt'><%=tisheng%></td>
  <td class=xl69 width=72 style='width:54pt' x:num="1">100%</td>
  <td class=xl94 width=72 style='border-left:none;width:54pt'><%=test_score%></td>
  <td class=xl94 width=72 style='border-left:none;width:54pt'><%=exam_score%></td>
  <td class=xl90 width=72 style='border-left:none;width:54pt'><%=total_score%></td>
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
  <td width=52 style='width:39pt'></td>
  <td width=89 style='width:67pt'></td>
  <td width=60 style='width:45pt'></td>
  <td width=57 style='width:43pt'></td>
  <td width=56 style='width:42pt'></td>
  <td width=56 style='width:42pt'></td>
  <td width=81 style='width:61pt'></td>
  <td width=59 style='width:44pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>



