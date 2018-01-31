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

<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="成绩单（首期班）.files/filelist.xml">
<link rel=Edit-Time-Data href="成绩单（首期班）.files/editdata.mso">
<link rel=OLE-Object-Data href="成绩单（首期班）.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>微软用户</o:Author>
  <o:LastAuthor>MC SYSTEM</o:LastAuthor>
  <o:LastPrinted>2011-05-11T06:00:55Z</o:LastPrinted>
  <o:Created>2011-05-10T06:17:19Z</o:Created>
  <o:LastSaved>2011-05-11T09:19:32Z</o:LastSaved>
  <o:Company>微软中国</o:Company>
  <o:Version>11.5606</o:Version>
 </o:DocumentProperties>
</xml><![endif]-->
<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:.56in .75in .3in 1.01in;
	mso-header-margin:.34in;
	mso-footer-margin:.16in;}
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
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl26
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl27
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl28
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl29
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl30
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl31
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;}
.xl32
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;}
.xl33
	{mso-style-parent:style0;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;}
.xl34
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;}
.xl35
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:top;}
.xl36
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:left;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:none;}
.xl37
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:left;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl38
	{mso-style-parent:style0;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;}
.xl39
	{mso-style-parent:style0;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl40
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
.xl41
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
.xl42
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid black;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl43
	{mso-style-parent:style0;
	font-weight:700;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid black;}
.xl44
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
.xl45
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;}
.xl46
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl47
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid black;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl48
	{mso-style-parent:style0;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:left;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl49
	{mso-style-parent:style0;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:left;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;}
.xl50
	{mso-style-parent:style0;
	font-family:楷体_GB2312, monospace;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:left;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid black;
	border-bottom:1.0pt solid windowtext;
	border-left:none;}
.xl51
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:none;}
.xl52
	{mso-style-parent:style0;
	font-size:16.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	white-space:normal;}
.xl53
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
.xl54
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl55
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:.5pt solid black;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl56
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl57
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl58
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid black;
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
      <x:VerticalResolution>0</x:VerticalResolution>
     </x:Print>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>6</x:ActiveRow>
       <x:RangeSelection>$A$7:$D$7</x:RangeSelection>
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
<%
	dbpool db = new dbpool();
	MyResultSet rs =null;
	MyResultSet rs_stu =null;

   String sql_stu=
	"select bs.id as stu_id,\n" +
	"       bs.true_name as true_name,\n" + 
	"       bs.gender as gender,\n" + 
	"       pbc.certificate as certificate,\n" + 
	"       bs.reg_no,\n" + 
	"       pbb.id as batch_id,\n" + 
	"       pbb.start_time as batch_start_date,\n" + 
	"       pbb.end_time as batch_end_date,\n" + 
	"       es.exam_time\n" + 
	"  from pe_bzz_examscore   es,\n" + 
	"       pe_bzz_exambatch   eb,\n" + 
	"       pe_bzz_certificate pbc,\n" + 
	"       pe_bzz_student     bs,\n" + 
	"       pe_bzz_batch     pbb\n" + 
	" where es.student_id = bs.id\n" + 
	"   and es.exambatch_id = eb.id\n" + 
	"   and pbc.student_id = bs.id\n" + 
	"   and bs.fk_batch_id = pbb.id\n" + 
	"   and eb.selected = '402880a91dadc115011dadfcf26b00aa' order by bs.reg_no";
   
    rs_stu = db.executeQuery(sql_stu);
	while(rs_stu!=null&&rs_stu.next())
	{
		String true_name = fixnull(rs_stu.getString("true_name"));
		true_name = true_name.replaceAll(" ","");
		true_name = true_name.replaceAll(" ","");
		true_name = true_name.replaceAll("　","");
		String reg_no = fixnull(rs_stu.getString("reg_no"));
		String certificate = fixnull(rs_stu.getString("certificate"));
		String gender = fixnull(rs_stu.getString("gender"));
		String stu_id = fixnull(rs_stu.getString("stu_id"));
		//针对2010学期学员企业战略认知（新）和班组安全管理（新）要保证二选一
		//取得学期id号
		String batch_id = fixnull(rs_stu.getString("batch_id"));
		String batch_start_date = new java.text.SimpleDateFormat("yyyy年MM月").format(rs_stu.getDate("batch_start_date"));
		String batch_end_date = new java.text.SimpleDateFormat("yyyy年MM月").format(rs_stu.getDate("batch_end_date"));
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
<table x:str border=0 cellpadding=0 cellspacing=0 width=588 style='border-collapse:
 collapse;table-layout:fixed;width:441pt'>
 <col width=72 span=4 style='width:54pt'>
 <col width=76 style='mso-width-source:userset;mso-width-alt:2432;width:57pt'>
 <col width=85 style='mso-width-source:userset;mso-width-alt:2720;width:64pt'>
 <col width=139 style='mso-width-source:userset;mso-width-alt:4448;width:104pt'>
 <tr height=62 style='mso-height-source:userset;height:46.5pt'>
  <td colspan=7 height=62 class=xl52 width=588 style='height:46.5pt;width:441pt'>清
  华 大 学 培 训 项 目 成 绩 单</td>
 </tr>
 <tr height=32 style='mso-height-source:userset;height:24.0pt'>
  <td colspan=7 height=32 class=xl53 width=588 style='height:24.0pt;width:441pt'>项目名称：中央企业班组长岗位管理能力资格认证远程培训</td>
 </tr>
 <tr height=24 style='mso-height-source:userset;height:18.0pt'>
  <td height=24 class=xl24 style='height:18.0pt'>学<span
  style='mso-spacerun:yes'>&nbsp;&nbsp; </span>号</td>
  <td colspan=2 class=xl54 style='border-right:.5pt solid black;border-left:
  none'><%=reg_no%></td>
  <td class=xl25>姓<span style='mso-spacerun:yes'>&nbsp; </span>名</td>
  <td class=xl26><%=true_name%></td>
  <td class=xl25>性<span style='mso-spacerun:yes'>&nbsp; </span>别</td>
  <td class=xl27><%=gender%></td>
 </tr>
 <tr height=21 style='mso-height-source:userset;height:15.75pt'>
  <td height=21 class=xl24 style='height:15.75pt'>学习时间</td>
  <td colspan=4 class=xl56 style='border-right:.5pt solid black;border-left:
  none'><%=batch_start_date%>-<%=batch_end_date%></td>
  <td class=xl25>学<span style='mso-spacerun:yes'>&nbsp; </span>制</td>
  <td class=xl27>一年</td>
 </tr>
 <tr height=22 style='mso-height-source:userset;height:16.5pt'>
  <td height=22 class=xl24 style='height:16.5pt'>考试时间</td>
  <td colspan=4 class=xl56 style='border-right:.5pt solid black;border-left:
  none'><%=exam_time%></td>
  <td class=xl25>证书编号</td>
  <td class=xl27><%=certificate%></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=4 height=19 class=xl40 style='border-right:.5pt solid black;
  height:14.25pt'>课程名称</td>
  <td class=xl25>学时</td>
  <td class=xl25>课程属性</td>
  <td class=xl28>平时成绩</td>
 </tr>
 <%
 String sql_start = "";
 String sql_end = "";
 if("ff8080812824ae6f012824b0a89e0008".equals(batch_id)){
 	//针对2010学期学员班组安全管理（新）和企业战略认知（新）选其中一个成绩较高的
 	sql_start = " select course_name,course_id,course_type,mtime,max(to_number(score)) as score from \n"+
 		" (select course_type,mtime,score, \n"+
 		" case course_name  \n"+
 		" when '班组安全管理（新）' then '班组安全管理' \n"+
 		" when '企业战略认知（新）' then '企业战略认知' \n"+
 		" else course_name \n"+
 		" end as course_name, \n"+
 		" case course_id  \n"+
 		" when 'ff8080812becf1e1012bf1b6846c27b4' then 'ff8080812253c263012253c31a0d0098' \n"+
 		" when 'ff8080812becf1e1012bf1b85c9427d5' then '52cce2fd23412cc201234b85722b00ed' \n"+
 		" else course_id  \n"+
 		" end as course_id from (\n" ;
 	sql_end = " \n)) group by course_name,course_id,course_type,mtime \n"+
 		" order by course_type";
 } 
String sql_course=sql_start+
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
	" cs.percent >=50 ) or\n" + 
	" bo.flag_course_type = '402880f32200c249012200c780c40001')\n" + 
	" order by bo.flag_course_type, to_number(tc.suqnum)" + sql_end;


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
 <tr height=17 style='mso-height-source:userset;height:12.75pt'>
  <td colspan=4 height=17 class=xl45 style='border-right:.5pt solid black;
  height:12.75pt'><%=course_name%></td>
  <td class=xl29><%=course_time%></td>
  <td class=xl29><%=course_type%></td>
  <td class=xl30><%=testscore%></td>
 </tr>
 <%}
  db.close(rs);
  %>
  <%
  if(35-totalItems>0){
  %>
 <tr height=17 style='mso-height-source:userset;height:12.75pt'>
  <td colspan=4 height=17 class=xl45 style='border-right:.5pt solid black;
  height:12.75pt'>—————————</td>
  <td class=xl29>——</td>
  <td class=xl29>——</td>
  <td class=xl30>——</td>
 </tr>
 <%}
  for(int left=0;left<(35-totalItems-1);left++)
  {
 %>
 <tr height=17 style='mso-height-source:userset;height:12.75pt'>
  <td colspan=4 height=17 class=xl45 style='border-right:.5pt solid black;
  height:12.75pt'>　</td>
  <td class=xl29>　</td>
  <td class=xl29>　</td>
  <td class=xl30>　</td>
 </tr>
 <%}%>
 <tr height=21 style='mso-height-source:userset;height:15.75pt'>
  <td colspan=3 height=21 class=xl40 style='border-right:.5pt solid black;
  height:15.75pt'>必修课平时平均成绩</td>
  <td colspan=2 class=xl43 style='border-right:.5pt solid black;border-left:
  none'><%=test_score%></td>
  <td rowspan=2 class=xl36 style='border-bottom:.5pt solid black;border-top:
  none'>综合成绩</td>
  <td rowspan=2 class=xl38 style='border-bottom:.5pt solid black;border-top:
  none'><%=total_score%></td>
 </tr>
 <tr height=25 style='mso-height-source:userset;height:18.75pt'>
  <td colspan=3 height=25 class=xl40 style='border-right:.5pt solid black;
  height:18.75pt'>考试成绩</td>
  <td colspan=2 class=xl43 style='border-right:.5pt solid black;border-left:
  none'><%=score%></td>
 </tr>
 <tr height=28 style='mso-height-source:userset;height:21.0pt'>
  <td height=28 class=xl31 style='height:21.0pt'>备注</td>
  <td colspan=6 class=xl48 style='border-right:1.0pt solid black;border-left:
  none'>必修课平时平均成绩×<%=test_scale%>%＋考试成绩×<%=exam_scale%>%＝综合成绩</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=7 height=19 class=xl51 style='height:14.25pt'>　</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 colspan=7 class=xl32 style='height:14.25pt;mso-ignore:colspan'></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=7 height=19 class=xl32 style='height:14.25pt'></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=4 height=19 class=xl33 style='height:14.25pt'>清华大学教育培训管理处</td>
  <td colspan=3 class=xl33>清华大学继续教育学院</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=4 height=19 class=xl34 style='height:14.25pt'></td>
  <td colspan=3 class=xl35>打印日期：<%=getCurDate()%></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 colspan=7 class=xl32 style='height:14.25pt;mso-ignore:colspan'></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 colspan=7 class=xl32 style='height:14.25pt;mso-ignore:colspan'></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 colspan=7 class=xl32 style='height:14.25pt;mso-ignore:colspan'></td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=76 style='width:57pt'></td>
  <td width=85 style='width:64pt'></td>
  <td width=139 style='width:104pt'></td>
 </tr>
 <![endif]>
</table>
<%}
db.close(rs_stu);
%>
</body>
</html>


