<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*,java.io.*,java.util.*"%>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.*"%>
<%@ page
	import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
	<%@ page import="java.io.ByteArrayOutputStream,java.io.FileNotFoundException,java.io.FileOutputStream,java.io.IOException"%>
	<%@ page import="com.lowagie.text.Document,com.lowagie.text.DocumentException,com.lowagie.text.Image,com.lowagie.text.pdf.AcroFields,com.lowagie.text.pdf.PdfCopy"%>
	<%@ page import="com.lowagie.text.pdf.PdfImportedPage,com.lowagie.text.pdf.PdfReader,com.lowagie.text.pdf.PdfStamper,com.lowagie.text.pdf.PdfWriter"%>
	<%@ page import="com.lowagie.text.pdf.PdfContentByte"%>
	<%@page import="com.lowagie.text.Rectangle"%>
<%@ include file="../statistics/pub/priv.jsp"%>
	
 
	<%!String fixnull(String str) { 
		if (str == null || str.equals("null"))
			str = "";
		return str;
	}%>
 
 <%!
	public   static   String   subTitle(String   str,   int  len)   {   
          if   (str   ==   null)   {   
              return   "";   
          }   
          str   =   str.trim();   
          StringBuffer   r   =   new   StringBuffer();   
          int   l   =   str.length();   
          float   count   =   0;   
          for   (int   i   =   0;   i   <   l;   ++i)   {   
              char   c   =   str.charAt(i);   
              if   (c   >   255   ||   c   <   0)   {   
                  ++count;   
                  r.append(c);   
              }   
              else   {   
                  count   +=   0.5;   
                  r.append(c);   
              }   
          }
          if   (count   <=   len)   {   
              	  for(int j = 0;j<len-count;j++)
              	  {
              	  	r.append(".");
              	  }
          }
          return   r.toString();   
      }

%>

<html>

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=Generator content="Microsoft Word 11 (filtered)">
<title>中国证券业协会远程培训系统</title>

<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:宋体;
	panose-1:2 1 6 0 3 1 1 1 1 1;}
@font-face
	{font-family:黑体;
	panose-1:2 1 6 0 3 1 1 1 1 1;}
@font-face
	{font-family:"\@宋体";
	panose-1:2 1 6 0 3 1 1 1 1 1;}
@font-face
	{font-family:"\@黑体";
	panose-1:2 1 6 0 3 1 1 1 1 1;}
@font-face
	{font-family:Calibri;
	panose-1:2 15 5 2 2 2 4 3 2 4;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	font-size:10.5pt;
	font-family:Calibri;}
h1
	{margin-top:17.0pt;
	margin-right:0cm;
	margin-bottom:16.5pt;
	margin-left:0cm;
	text-align:justify;
	text-justify:inter-ideograph;
	line-height:240%;
	page-break-after:avoid;
	font-size:22.0pt;
	font-family:Calibri;
	font-weight:bold;}
h2
	{margin-top:13.0pt;
	margin-right:0cm;
	margin-bottom:13.0pt;
	margin-left:0cm;
	text-align:justify;
	text-justify:inter-ideograph;
	line-height:173%;
	page-break-after:avoid;
	font-size:16.0pt;
	font-family:Arial;
	font-weight:bold;}
h3
	{margin-top:13.0pt;
	margin-right:0cm;
	margin-bottom:13.0pt;
	margin-left:0cm;
	text-align:justify;
	text-justify:inter-ideograph;
	line-height:173%;
	page-break-after:avoid;
	font-size:16.0pt;
	font-family:Calibri;
	font-weight:bold;}
p.MsoHeader, li.MsoHeader, div.MsoHeader
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:center;
	layout-grid-mode:char;
	border:none;
	padding:0cm;
	font-size:9.0pt;
	font-family:Calibri;}
p.MsoFooter, li.MsoFooter, div.MsoFooter
	{margin:0cm;
	margin-bottom:.0001pt;
	layout-grid-mode:char;
	font-size:9.0pt;
	font-family:Calibri;}
p.MsoCaption, li.MsoCaption, div.MsoCaption
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	font-size:10.0pt;
	font-family:Arial;}
p
	{margin-right:0cm;
	margin-left:0cm;
	font-size:12.0pt;
	font-family:宋体;}
p.MsoAcetate, li.MsoAcetate, div.MsoAcetate
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	font-size:9.0pt;
	font-family:Calibri;}
span.CharChar5
	{font-family:Calibri;
	font-weight:bold;}
span.CharChar4
	{font-family:Arial;
	font-weight:bold;}
span.CharChar3
	{font-family:Calibri;
	font-weight:bold;}
p.a, li.a, div.a
	{margin-right:0cm;
	margin-left:0cm;
	font-size:12.0pt;
	font-family:宋体;}
span.CharChar2
	{font-family:Calibri;}
span.CharChar1
	{font-family:Calibri;}
span.CharChar
	{font-family:Calibri;}
 /* Page Definitions */
 @page Section1
	{size:595.3pt 841.9pt;
	margin:54.55pt 90.0pt 46.75pt 90.0pt;
	layout-grid:15.6pt;}
div.Section1
	{page:Section1;}
-->
</style>

</head>

<body lang=ZH-CN style='text-justify-trim:punctuation'>
<%
	dbpool db = new dbpool();
    MyResultSet rs = null;
    
	String sql_con = "";
	String priTag = "2";  //0:总站管理员；1：一级管理员;2：二级管理员
	//判断是一级企业管理员，还是二级企业管理员
	if (us.getRoleId().equals("3")) { //表示总站管理员
	
		priTag = "0";
	} else if(us.getRoleId().equals("2") || us.getRoleId().equals("402880a92137be1c012137db62100006")) {
		priTag = "1";
	} else {
		priTag = "2";
	}
	
	String enterprise_id="";
	if (priTag.equals("0")) { //表示总站管理员
		sql_con = "";
	} else {//一级企业
		String sql_enter = 
		"select m.fk_enterprise_id as enterprise_id\n" +
		"  from pe_enterprise_manager m\n" + 
		" where m.fk_sso_user_id = '"+us.getId()+"'";
        rs = db.executeQuery(sql_enter);
		while(rs!=null&&rs.next())
		{
			enterprise_id = fixnull(rs.getString("enterprise_id"));
		}
		db.close(rs);
	}
	if(priTag.equals("1"))//一级企业管理员可以查看所有该企业下的学员，包括二级企业
	{
		sql_con = " and (ep.id = '"+enterprise_id+"' or ep.fk_parent_id ='"+enterprise_id+"')";
	}
	else if(priTag.equals("2"))//二级企业
	{
		sql_con = " and ep.fk_parent_id is not null and ep.id='"+enterprise_id+"'";
	}
	
	String sql = 
		"select bs.true_name   as true_name,\n" +
		"       bs.gender      as gender,\n" + 
		"       bs.reg_no      as reg_no,\n" + 
		"       es.testcard_id as testcard_id,\n" + 
		"       ep.name        as enterprise_name,\n" + 
		"       bes.name       as site_name,\n" + 
		"       es.room_id     as room_name,\n" + 
		"       es.desk_no     as desk_no,\n" + 
		"       es.address     as address,\n" + 
		"       es.note        as note,\n" + 
		"       es.phone       as phone,es.exam_time as exam_time \n" + 
		"  from pe_bzz_examscore      es,\n" + 
		"       pe_bzz_student        bs,\n" + 
		"       pe_enterprise         ep,\n" + 
		"       pe_bzz_exambatch_site ebs,\n" + 
		"       pe_bzz_examsite       bes,\n" + 
		"       pe_bzz_exambatch      eb\n" + 
		" where es.student_id = bs.id\n" + 
		"   and es.exambatch_id = eb.id\n" + 
		"   and es.site_id = ebs.id\n" + 
		"   and ebs.site_id = bes.id\n" + 
		"   and ebs.exambatch_id = eb.id\n" + 
		"   and bs.fk_enterprise_id = ep.id and es.status='402880a9aaadc115061dadfcf26b0003' \n" + 
		"   and eb.selected = '402880a91dadc115011dadfcf26b00aa'\n" + sql_con+
		" order by bs.reg_no";
	if(db.countselect(sql)<1)
	{
		%>
			<script type="text/javascript">
				alert("没有审核通过的学生可打印");
				window.close();
			</script>
		<%
	}
	else
	{
	    rs = db.executeQuery(sql);
	    int i=0;
	 	while(rs!=null && rs.next())
	 	{
	 	   i++;
	 	   String true_name = fixnull(rs.getString("true_name"));
	 	   String gender = fixnull(rs.getString("gender"));
	 	   String reg_no = fixnull(rs.getString("reg_no"));
	 	   String testcard_id = fixnull(rs.getString("testcard_id"));
	 	   String enterprise_name = fixnull(rs.getString("enterprise_name"));
	 	   String site_name = fixnull(rs.getString("site_name"));
	 	   String room_name = fixnull(rs.getString("room_name"));
	 	   String desk_no = fixnull(rs.getString("desk_no"));
	 	   String address = fixnull(rs.getString("address"));
	 	   String note = fixnull(rs.getString("note"));
	 	   String phone = fixnull(rs.getString("phone"));
	 	   String exam_time = fixnull(rs.getString("exam_time"));
%>
<%if(i == db.countselect(sql)) { %>
<div class=Section1  style="layout-grid:15.6pt;">
<%} else { %>
<div class=Section1  style="layout-grid:15.6pt;PAGE-BREAK-AFTER: always">
<%} %>

<p class=MsoNormal><span lang=EN-US style='font-family:宋体'><img width=291
height=58 src="/entity/function/images/testcard.jpg"></span></p>

<p class=MsoNormal><span lang=EN-US style='font-family:宋体'>&nbsp;</span></p>

<p class=MsoNormal align=center style='text-align:center'><b><span
style='font-size:16.0pt;font-family:宋体'>准 <span lang=EN-US>&nbsp;&nbsp;</span>考<span
lang=EN-US>&nbsp; &nbsp;</span>证</span></b></p>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width="99%"
 style='width:99.34%;border-collapse:collapse'>
 <tr>
  <td width=115 style='width:86.4pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>姓<span lang=EN-US>&nbsp;&nbsp; </span>名</span></b></p>
  </td>
  <td width=439 style='width:329.15pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  10.0pt;font-family:宋体'><%=true_name%></span></p>
  </td>
 </tr>
 <tr>
  <td width=115 style='width:86.4pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>性<span lang=EN-US>&nbsp;&nbsp; </span>别</span></b></p>
  </td>
  <td width=439 style='width:329.15pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  10.0pt;font-family:宋体'><%=gender%></span></p>
  </td>
 </tr>
 <tr>
  <td width=115 style='width:86.4pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>学<span lang=EN-US>&nbsp;&nbsp; </span>号</span></b></p>
  </td>
  <td width=439 style='width:329.15pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span lang=EN-US
  style='font-size:10.0pt;font-family:宋体'><%=reg_no%></span></p>
  </td>
 </tr>
 <tr>
  <td width=115 style='width:86.4pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>准考证号</span></b></p>
  </td>
  <td width=439 style='width:329.15pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span lang=EN-US
  style='font-size:10.0pt;font-family:宋体'><%=testcard_id%></span></p>
  </td>
 </tr>
 <tr>
  <td width=115 style='width:86.4pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>工作单位</span></b></p>
  </td>
  <td width=439 style='width:329.15pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span lang=EN-US
  style='font-size:10.0pt;font-family:宋体'><%=enterprise_name%></span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal align=center style='text-align:center'><b><span lang=EN-US
style='font-size:12.0pt;font-family:宋体'>&nbsp;</span></b></p>

<p class=MsoNormal align=center style='text-align:center'><b><span
style='font-size:12.0pt;font-family:宋体'>考 试 安 排 </span></b></p>

<p class=MsoNormal align=center style='text-align:center'><b><span lang=EN-US
style='font-size:9.0pt;font-family:宋体'>&nbsp;</span></b></p>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse'>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>考试形式</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  10.0pt;font-family:宋体'>上机考试（客观题<span lang=EN-US>85%</span>） </span><span
  lang=EN-US style='font-size:12.0pt;font-family:宋体'>+</span><span lang=EN-US
  style='font-size:10.0pt;font-family:宋体'>&nbsp; </span><span style='font-size:
  10.0pt;font-family:宋体'>试卷笔试（主观题<span lang=EN-US>15%</span>）</span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>考试时间</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal><span
   lang=EN-US style='font-size:10.0pt;font-family:宋体'><%=exam_time %></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>考点名称</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  10.0pt;font-family:宋体'><%=site_name%></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>考场名称</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal><span style='font-size:10.0pt;font-family:宋体'><%=room_name%></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>座 位 号</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal><span lang=EN-US style='font-size:10.0pt;font-family:宋体'><%=desk_no%></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>考场地址</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  10.0pt;font-family:宋体'><%=address%></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>乘车路线</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  10.0pt;font-family:宋体'><%=note%></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>咨询电话</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span lang=EN-US
  style='font-size:10.0pt;font-family:宋体'><%=phone%></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>照片采集</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  10.0pt;font-family:宋体'>学员应着装整洁，第一场考试提前<span lang=EN-US>60</span>分钟，第二场考试提前<span lang=EN-US>30</span>分钟进入考场进行现场拍照，照片用做学员证书</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal align=center style='text-align:center'><b><span lang=EN-US
style='font-size:12.0pt;font-family:宋体'>&nbsp;</span></b></p>

<p class=MsoNormal align=center style='text-align:center'><b><span
style='font-size:12.0pt;font-family:宋体'>考生须知</span></b></p>

<p class=MsoNormal align=center style='margin-top:2.9pt;margin-right:4.6pt;
margin-bottom:1.75pt;margin-left:5.75pt;text-align:center'><b><span
style='font-size:9.0pt;font-family:宋体'>本人参加考试，承认已完整阅读《考生须知》各项内容，并自愿遵守相关规定。</span></b></p>

<p class=MsoNormal align=left style='margin-top:2.9pt;margin-right:4.6pt;
margin-bottom:1.75pt;margin-left:21.5pt;text-align:left;text-indent:-15.75pt'><span
lang=EN-US style='font-family:宋体'>1</span><span style='font-family:宋体'>．第一场考试开始前<span
lang=EN-US>60</span>分钟，第二场考试开始前<span
lang=EN-US>30</span>分钟考生凭准考证和有效身份证件（身份证、军官证、士兵证）入场，并现场拍照，照片作为认证证书上的照片。</span></p>

<p class=MsoNormal align=left style='margin-top:2.9pt;margin-right:4.6pt;
margin-bottom:1.75pt;margin-left:21.5pt;text-align:left;text-indent:-15.75pt'><span
lang=EN-US style='font-family:宋体'>2</span><span style='font-family:宋体'>．考生只能携带黑<span
lang=EN-US>(</span>蓝<span lang=EN-US>)</span>色签字笔或圆珠笔进入考室，不得携带书籍及其他工具进入考室；已带入考室者，应按要求存放在指定地点（携带的通讯工具、电子设备等应全部关闭后，再存放到指定位置）。若不按监考教师指定的位置进行存放，对考生按违纪行为处理。不服从者按作弊处理。</span></p>

<p class=MsoNormal align=left style='margin-top:2.9pt;margin-right:4.6pt;
margin-bottom:1.75pt;margin-left:16.25pt;text-align:left;text-indent:-10.5pt'><span
lang=EN-US style='font-family:宋体'>3.</span><span style='font-family:宋体'>考试按准考证上指定的座位号对号入座，不得随意调换座位。入座后，须将身份证和准考证放在桌子的左前方，以备监考人员检查。</span></p>

<p class=MsoNormal align=left style='margin-top:2.9pt;margin-right:4.6pt;
margin-bottom:1.75pt;margin-left:16.25pt;text-align:left;text-indent:-10.5pt'><span
lang=EN-US style='font-family:宋体'>4.</span><span style='font-family:宋体'>考生入座后，登录考试系统后应仔细核对姓名、照片等个人信息。考试如发现信息有误，应举手向监考人员示意，并听从监考人员的安排进行现场处理。</span></p>

<p class=MsoNormal align=left style='margin-top:2.9pt;margin-right:4.6pt;
margin-bottom:1.75pt;margin-left:16.25pt;text-align:left;text-indent:-10.5pt'><span
lang=EN-US style='font-family:宋体'>5.</span><span style='font-family:宋体'>考试开始<span
lang=EN-US>30</span>分钟后，考生不得入场，考试系统将不再接受该准考证的登录。</span></p>

<p class=MsoNormal align=left style='margin-top:2.9pt;margin-right:4.6pt;
margin-bottom:1.75pt;margin-left:16.25pt;text-align:left;text-indent:-10.5pt'><span
lang=EN-US style='font-family:宋体'>6.</span><span style='font-family:宋体'>考试开始<span
lang=EN-US>30</span>分钟后，才可以交卷离开考场，考生交卷后应立即退场，不得在考场附近逗留、交谈，不得再次返回考场。</span></p>

<p class=MsoNormal align=left style='margin-top:2.9pt;margin-right:4.6pt;
margin-bottom:1.75pt;margin-left:16.25pt;text-align:left;text-indent:-10.5pt'><span
lang=EN-US style='font-family:宋体'>7.</span><span style='font-family:宋体'>考生必须服从监考教师的管理，自觉维护考试秩序。考试结束时间一到，考生立即停止答卷，待监考教师收卷后，考生退离考室，严禁考生将答题纸带出考室。</span></p>

<p class=MsoNormal align=left style='margin-top:2.9pt;margin-right:4.6pt;
margin-bottom:1.75pt;margin-left:16.25pt;text-align:left;text-indent:-10.5pt'><span
lang=EN-US style='font-family:宋体'>8.</span><span style='font-family:宋体'>考试因病不能坚持考试的，应报告监考人员，监考人员将根据具体情况进行处理。</span></p>

</div>
<%}
 db.close(rs); 
 }
 %>
</body>

</html>

