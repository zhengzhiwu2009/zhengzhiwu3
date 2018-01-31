<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*,java.io.*,java.util.*"%>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.*"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page import="com.whaty.platform.entity.bean.*" %>
<%@ page isELIgnored="false" %>
<%@ page
	import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
	<%@ page import="java.io.ByteArrayOutputStream,java.io.FileNotFoundException,java.io.FileOutputStream,java.io.IOException"%>
	<%@ page import="com.lowagie.text.Document,com.lowagie.text.DocumentException,com.lowagie.text.Image,com.lowagie.text.pdf.AcroFields,com.lowagie.text.pdf.PdfCopy"%>
	<%@ page import="com.lowagie.text.pdf.PdfImportedPage,com.lowagie.text.pdf.PdfReader,com.lowagie.text.pdf.PdfStamper,com.lowagie.text.pdf.PdfWriter"%>
	<%@ page import="com.lowagie.text.pdf.PdfContentByte"%>
	<%@page import="com.lowagie.text.Rectangle"%>
	
 
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
<%
	List peBzzExamScores = (List)session.getAttribute("peBzzExamScores");
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
<s:if test="peBzzExamScores.size > 0">
<s:iterator value="peBzzExamScores" id="examScore">
<div class=Section1 style='layout-grid:15.6pt'>
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
  10.0pt;font-family:宋体'><s:property value="getPeBzzStudent().getTrueName()"  escape="false"/></span></p>
  </td>
 </tr>
 <tr>
  <td width=115 style='width:86.4pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>性<span lang=EN-US>&nbsp;&nbsp; </span>别</span></b></p>
  </td>
  <td width=439 style='width:329.15pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  10.0pt;font-family:宋体'><s:property value="getPeBzzStudent().getGender()"  escape="false"/></span></p>
  </td>
 </tr>
 <tr>
  <td width=115 style='width:86.4pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>学<span lang=EN-US>&nbsp;&nbsp; </span>号</span></b></p>
  </td>
  <td width=439 style='width:329.15pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span lang=EN-US
  style='font-size:10.0pt;font-family:宋体'><s:property value="getPeBzzStudent().getRegNo()"  escape="false"/></span></p>
  </td>
 </tr>
 <tr>
  <td width=115 style='width:86.4pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>准考证号</span></b></p>
  </td>
  <td width=439 style='width:329.15pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span lang=EN-US
  style='font-size:10.0pt;font-family:宋体'><s:property value="getTestcard_id()"  escape="false"/></span></p>
  </td>
 </tr>
 <tr>
  <td width=115 style='width:86.4pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>工作单位</span></b></p>
  </td>
  <td width=439 style='width:329.15pt;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span lang=EN-US
  style='font-size:10.0pt;font-family:宋体'><s:property value="getPeBzzStudent().getPeEnterprise().getName()"  escape="false"/></span></p>
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
   lang=EN-US style='font-size:10.0pt;font-family:宋体'>2011</span><span
   style='font-size:10.0pt;font-family:宋体'>年<span lang=EN-US>03</span>月<span
   lang=EN-US>19</span>日</span><span style='font-size:10.0pt;font-family:宋体'> <span
  lang=EN-US>14:00 - 16:00</span></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>考点名称</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  10.0pt;font-family:宋体'><s:property value="getPeBzzExamBatchSite().getPeBzzExamSite().getName()"  escape="false"/></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>考场名称</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal><span style='font-size:10.0pt;font-family:宋体'><s:property value="getRoom_id()"  escape="false"/></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>座 位 号</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal><span lang=EN-US style='font-size:10.0pt;font-family:宋体'><s:property value="getDesk_no()"  escape="false"/></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>考场地址</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  10.0pt;font-family:宋体'><s:property value="getAddress()"  escape="false"/></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>乘车路线</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  10.0pt;font-family:宋体'><s:property value="getNote()"  escape="false"/></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>咨询电话</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span lang=EN-US
  style='font-size:10.0pt;font-family:宋体'><s:property value="getPhone()"  escape="false"/></span></p>
  </td>
 </tr>
 <tr>
  <td width="19%" style='width:19.72%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:10.0pt;font-family:宋体'>照片采集</span></b></p>
  </td>
  <td width="80%" style='width:80.28%;padding:1.5pt 1.5pt 1.5pt 1.5pt'>
  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  10.0pt;font-family:宋体'>学员应着装整洁，提前<span lang=EN-US>60</span>分钟进入考场进行现场拍照，照片用做学员证书</span></p>
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
lang=EN-US style='font-family:宋体'>1</span><span style='font-family:宋体'>．考试开始前<span
lang=EN-US>60</span>分钟，考生凭准考证和有效身份证件（身份证、军官证、士兵证）入场，并现场拍照，照片作为认证证书上的照片。</span></p>

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
</s:iterator>
</s:if>
</body>

</html>

