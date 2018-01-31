<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>
<%@ page import="com.whaty.platform.entity.bean.*" %>

<%	response.setHeader("Content-Disposition", "attachment;filename=score_excel.xls"); %>

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
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="score5.files/filelist.xml">
<link rel=Edit-Time-Data href="score5.files/editdata.mso">
<link rel=OLE-Object-Data href="score5.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>MC SYSTEM</o:Author>
  <o:LastAuthor>MC SYSTEM</o:LastAuthor>
  <o:LastPrinted>2011-04-20T03:39:06Z</o:LastPrinted>
  <o:Created>2005-03-04T00:54:10Z</o:Created>
  <o:LastSaved>2011-04-20T03:39:33Z</o:LastSaved>
  <o:Company>MC SYSTEM</o:Company>
  <o:Version>11.5606</o:Version>
 </o:DocumentProperties>
</xml><![endif]-->
<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:.24in 1.17in .23in .9in;
	mso-header-margin:.16in;
	mso-footer-margin:.16in;
	mso-horizontal-page-align:center;}
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
	text-align:center;}
.xl25
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl27
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;}
.xl28
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl29
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl30
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl31
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;}
.xl32
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;}
.xl33
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl34
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl35
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl36
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl37
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:top;}
.xl38
	{mso-style-parent:style0;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;}
.xl39
	{mso-style-parent:style0;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:left;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl40
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:left;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;}
.xl41
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:left;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;}
.xl42
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:left;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;}
.xl43
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:left;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl44
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;}
.xl45
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl46
	{mso-style-parent:style0;
	font-size:16.0pt;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	white-space:normal;}
.xl47
	{mso-style-parent:style0;
	font-size:16.0pt;
	font-weight:700;
	font-family:华文新魏;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;}
.xl48
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl49
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl50
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:left;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	white-space:normal;}
.xl51
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl52
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl53
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl54
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;}
.x254
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;}
.xl55
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl56
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl57
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl58
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl59
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl60
	{mso-style-parent:style0;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;}
.xl61
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl62
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
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
    <x:Name>Sheet3</x:Name>
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
       <x:ActiveRow>46</x:ActiveRow>
      </x:Pane>
     </x:Panes>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
  </x:ExcelWorksheets>
  <x:WindowHeight>6750</x:WindowHeight>
  <x:WindowWidth>8460</x:WindowWidth>
  <x:WindowTopX>240</x:WindowTopX>
  <x:WindowTopY>90</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple class=xl24>
<%
	dbpool db = new dbpool();
	MyResultSet rs =null;

	List peBzzCertificates = (List)session.getAttribute("peBzzCertificates");
	for (int item = 0; item < peBzzCertificates.size(); item++) 
	{
		String true_name = fixnull(((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getTrueName());
		true_name = true_name.replaceAll(" ","");
		true_name = true_name.replaceAll(" ","");
		true_name = true_name.replaceAll("　","");
		String gender = fixnull(((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getGender());
		String reg_no = fixnull(((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getRegNo());
		String certificate = fixnull(((PeBzzCertificate)peBzzCertificates.get(item)).getCertificate());
		String stu_id = fixnull(((PeBzzCertificate)peBzzCertificates.get(item)).getPeBzzStudent().getId());
		
		 String score = "";
		 String test_score="";
		 String total_score="";
		 String exam_scale = "";
		 String test_scale = "";
		 String exam_time = "";
		 String sql_score=
			 "select replace(to_char(nvl(es.score, 0),'999'),' ','') as score,\n" +
			 "       replace(to_char(nvl(es.test_score, 0),'999'),' ','') as test_score,\n" + 
			 "       replace(to_char(nvl(es.total_score, 0),'999'),' ','') as total_score,\n" + 
			 "       eb.exam_scale as exam_scale,\n" + 
			 "       eb.test_scale as test_scale,\n" + 
			 "       es.exam_time\n" + 
			 "  from pe_bzz_examscore es, pe_bzz_exambatch eb\n" + 
			 " where es.exambatch_id = eb.id\n" + 
			 "   and es.student_id = '"+stu_id+"'";
		 rs = db.executeQuery(sql_score);
		 while(rs!=null&&rs.next())
			{
			    score = fixnull(rs.getString("score"));
			    test_score =fixnull(rs.getString("test_score"));
			    total_score = fixnull(rs.getString("total_score"));
			    exam_scale = fixnull(rs.getString("exam_scale"));
			    test_scale = fixnull(rs.getString("test_scale"));
			    exam_time = fixnull(rs.getString("exam_time"));
			    if(exam_time.length()>=11)
			    {
			    	exam_time = exam_time.substring(0,10);
			    }
			}
		 db.close(rs);
		 
%>
<table x:str border=0 cellpadding=0 cellspacing=0 width=559 style='border-collapse:
 collapse;table-layout:fixed;width:419pt'>
 <col class=xl24 width=75 style='mso-width-source:userset;mso-width-alt:2400;
 width:56pt'>
 <col class=xl24 width=48 style='mso-width-source:userset;mso-width-alt:1536;
 width:36pt'>
 <col class=xl24 width=83 style='mso-width-source:userset;mso-width-alt:2656;
 width:62pt'>
 <col class=xl24 width=61 style='mso-width-source:userset;mso-width-alt:1952;
 width:46pt'>
 <col class=xl24 width=108 style='mso-width-source:userset;mso-width-alt:3456;
 width:81pt'>
 <col class=xl24 width=79 style='mso-width-source:userset;mso-width-alt:2528;
 width:59pt'>
 <col class=xl24 width=105 style='mso-width-source:userset;mso-width-alt:3360;
 width:79pt'>
 <col class=xl24 width=122 style='mso-width-source:userset;mso-width-alt:3904;
 width:92pt'>
 <tr height=36 style='mso-height-source:userset;height:27.0pt'>
  <td colspan=7 height=36 class=xl46 width=559 style='height:27.0pt;width:419pt'>清华大学培训项目成绩单</td>
 </tr>
 <tr height=27 style='mso-height-source:userset;height:20.25pt'>
  <td colspan=7 height=27 class=xl50 width=559 style='height:20.25pt;
  width:419pt'>项目名称：中央企业班组长岗位管理能力资格认证远程培训</td>
 </tr>
 <tr height=24 style='mso-height-source:userset;height:18.0pt'>
  <td height=24 class=xl25 style='height:18.0pt;border-top:none'>学<span
  style='mso-spacerun:yes'>&nbsp;&nbsp; </span>号</td>
  <td colspan=2 class=xl48 style='border-right:.5pt solid black;border-left:
  none'><%=reg_no%></td>
  <td class=xl26 style='border-top:none;border-left:none'>姓<span
  style='mso-spacerun:yes'>&nbsp; </span>名</td>
  <td class=xl34 style='border-top:none;border-left:none'><%=true_name%></td>
  <td class=xl26 style='border-top:none;border-left:none'>性<span
  style='mso-spacerun:yes'>&nbsp; </span>别</td>
  <td class=xl35 style='border-top:none;border-left:none'><%=gender%></td>
 </tr>
 <tr height=22 style='mso-height-source:userset;height:16.5pt'>
  <td height=22 class=xl27 style='height:16.5pt;border-top:none'>学习时间</td>
  <td colspan=4 class=xl51 style='border-right:.5pt solid black;border-left:
  none'>2009年12月-2010年12月</td>
  <td class=xl28 style='border-top:none'>学<span style='mso-spacerun:yes'>&nbsp;
  </span>制</td>
  <td class=xl36 style='border-top:none'>一年</td>
 </tr>
 <tr height=21 style='mso-height-source:userset;height:15.75pt'>
  <td height=21 class=xl27 style='height:15.75pt;border-top:none'>考试时间</td>
  <td colspan=4 class=xl51 style='border-right:.5pt solid black;border-left:
  none'><%=exam_time%></td>
  <td class=xl28 style='border-top:none'>证书编号</td>
  <td class=xl33 style='border-top:none;border-left:none'><%=certificate%></td>
 </tr>
 <tr height=23 style='mso-height-source:userset;height:17.25pt'>
  <td colspan=4 height=23 class=xl44 style='border-right:.5pt solid black;
  height:17.25pt'>课程名称</td>
  <td class=xl29 style='border-top:none;border-left:none'>学时</td>
  <td class=xl29 style='border-top:none;border-left:none'>课程属性</td>
  <td class=xl30 style='border-top:none;border-left:none'>平时成绩</td>
 </tr>
 <%
 
String sql_course=
	"select tc.name as course_name,\n" +
	"       tc.id as course_id,\n" + 
	"       ec.name as course_type,\n" + 
	"       tc.time as mtime,\n" + 
	"       tth.score\n" + 
	"  from pe_bzz_student bs,\n" + 
	"       enum_const     ec,\n" + 
	"       --pe_bzz_batch   bb,\n" + 
	"       --pe_enterprise pe,\n" + 
	"       pr_bzz_tch_stu_elective se,\n" + 
	"       pe_bzz_tch_course       tc,\n" + 
	"       --sso_user su,\n" + 
	"       training_course_student cs,\n" + 
	"       pr_bzz_tch_opencourse bo,\n" + 
	"       (select replace(to_char(ty.score,'999'),' ','') as score, ti.group_id as group_id\n" + 
	"          from test_testpaper_history ty, test_testpaper_info ti\n" + 
	"         where ty.testpaper_id = ti.id\n" + 
	"           and ty.t_user_id = '"+stu_id+"') tth\n" + 
	" where --bs.fk_batch_id = bb.id\n" + 
	"--and bs.id = '52cce2fd24654f770124656a5a6230b7'\n" + 
	"--and bs.fk_enterprise_id = pe.id\n" + 
	" ec.id = bo.flag_course_type\n" + 
	" and se.fk_stu_id = bs.id\n" + 
	" and bo.fk_course_id = tc.id\n" + 
	" and bo.fk_batch_id = bs.fk_batch_id\n" + 
	"--and bs.fk_sso_user_id = su.id\n" + 
	" and bs.fk_sso_user_id = cs.student_id\n" + 
	" and se.fk_stu_id = '"+stu_id+"'\n" + 
	" and cs.course_id = bo.id\n" + 
	" and tth.group_id(+) = bo.fk_course_id\n" + 
	" and se.fk_tch_opencourse_id = bo.id\n" + 
	" and ((bo.flag_course_type = '402880f32200c249012200c7f8b30002' and\n" + 
	" cs.percent >= 50 ) or\n" + 
	" bo.flag_course_type = '402880f32200c249012200c780c40001')\n" + 
	" order by bo.flag_course_type, to_number(tc.suqnum)";

 int totalItems = db.countselect(sql_course);
 rs = db.executeQuery(sql_course);
 while(rs!=null&&rs.next())
	{
		String course_id = fixnull(rs.getString("course_id"));
		String course_name = fixnull(rs.getString("course_name"));
		String course_type = fixnull(rs.getString("course_type"));
		String course_time = fixnull(rs.getString("mtime"));
		String testscore = fixnull(rs.getString("score"));
		if(course_type.equals("提升课程"))
		{
			testscore = "/";
		}
		else if(testscore.equals(""))
		{
			if(course_type.equals("基础课程"))
			{
				testscore = "0";
			}
			
		}
		if(course_type.equals("提升课程"))
		{
			course_type="选修课程";
		}
		else if(course_type.equals("基础课程"))
		{
			course_type="必修课程";
		}

 %>
 <tr height=21 style='mso-height-source:userset;height:15.75pt'>
  <td colspan=4 height=21 class=xl54 style='border-right:.5pt solid black;
  height:15.75pt'><%=course_name%></td>
  <td class=xl58 style='border-top:none;border-left:none'><%=course_time%></td>
  <td class=xl58 style='border-top:none;border-left:none'><%=course_type%></td>
  <td class=xl57 style='border-top:none;border-left:none'><%=testscore%></td>
 </tr>
 <%}
  db.close(rs);
  %>
  <td colspan=3 height=28 class=xl44 style='border-right:.5pt solid black;
  height:21.0pt'>必修课平时平均成绩</td>
  <td colspan=2 class=xl59 style='border-right:.5pt solid black;border-left:
  none'><%=test_score%></td>
  <td rowspan=2 class=xl42 style='border-bottom:.5pt solid black;border-top:
  none'>综合成绩</td>
  <td rowspan=2 class=xl60 style='border-bottom:.5pt solid black;border-top:
  none'><%=total_score%></td>
 </tr>
 <tr height=26 style='mso-height-source:userset;height:19.5pt'>
  <td colspan=3 height=26 class=xl44 style='border-right:.5pt solid black;
  height:19.5pt'>考试成绩</td>
  <td colspan=2 class=xl59 style='border-right:.5pt solid black;border-left:
  none'><%=score%></td>
 </tr>
 <tr height=31 style='mso-height-source:userset;height:23.25pt'>
  <td height=31 class=xl31 style='height:23.25pt;border-top:none'>备注</td>
  <td colspan=6 class=xl39 style='border-right:1.0pt solid black;border-left:
  none'>必修课平时平均成绩×<%=test_scale%>%＋考试成绩×<%=exam_scale%>%＝综合成绩</td>
 </tr>
 <%
 if(35-totalItems>=4)
 {
	 for(int bc=0;bc<4;bc++)
	 {
	 %>
	 <tr height=21 style='mso-height-source:userset;height:15.75pt'>
	  <td colspan=7 height=21 class=x254 style='height:15.75pt'>　</td>
	 </tr>
	 <%
	 }
 }
 %>
 <tr height=35 style='mso-height-source:userset;height:26.25pt'>
  <td colspan=4 height=35 class=xl38 style='height:26.25pt'>清华大学教育培训管理处</td>
  <td colspan=3 class=xl38>清华大学继续教育学院</td>
 </tr>
 <tr height=24 style='mso-height-source:userset;height:18.0pt'>
  <td colspan=4 height=24 class=xl32 style='height:18.0pt'></td>
  <td colspan=3 class=xl37>打印日期：<%=getCurDate()%></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 colspan=7 class=xl32 style='height:14.25pt;mso-ignore:colspan'></td>
 </tr>
 <%
 if(35-totalItems>=4)
 {
	 for(int bc=0;bc<(35-totalItems-4);bc++)
	 {
	 %>
	 <tr height=21 style='mso-height-source:userset;height:15.75pt'>
	  <td colspan=7 height=21 class=x254 style='height:15.75pt'>　</td>
	 </tr>
	 <%
	 }
 }
 else
 {
 for(int bctt=0;bctt<35-totalItems;bctt++)
	 {
	 %>
	 <tr height=21 style='mso-height-source:userset;height:15.75pt'>
	  <td colspan=7 height=21 class=x254 style='height:15.75pt'>　</td>
	 </tr>
	 <%
	 }
 }%>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=75 style='width:56pt'></td>
  <td width=48 style='width:36pt'></td>
  <td width=83 style='width:62pt'></td>
  <td width=61 style='width:46pt'></td>
  <td width=108 style='width:81pt'></td>
  <td width=79 style='width:59pt'></td>
  <td width=105 style='width:79pt'></td>
 </tr>
 <![endif]>
</table>
<%}%>
</body>


