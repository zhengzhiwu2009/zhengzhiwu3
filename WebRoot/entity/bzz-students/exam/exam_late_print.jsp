<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*,java.io.*,java.util.*"%>
<html xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns="http://www.w3.org/TR/REC-html40">

<%	response.setHeader("Content-Disposition", "attachment;filename=hksqb.doc"); %>
	<%!String fixnull(String str) { 
		if (str == null || str.equals("null"))
			str = "";
		return str;
	}%>

<%
	String student_id = fixnull(request.getParameter("student_id"));
		String temp_sql=
			"select t.reg_no       as reg_no,t.true_name as name,\n" +
			"       t.mobile_phone as phone,\n" + 
			"       pe.name        as enterprise_name,\n" + 
			"       pe.name        as group_name\n" + 
			"  from pe_bzz_student t, pe_enterprise pe\n" + 
			" where t.id = '"+student_id+"'\n" + 
			"   and t.fk_enterprise_id = pe.id\n" + 
			"   and pe.fk_parent_id is null\n" + 
			"union\n" + 
			"select t.reg_no       as reg_no,t.true_name as name,\n" + 
			"       t.mobile_phone as phone,\n" + 
			"       pe.name        as enterprise_name,\n" + 
			"       p.name         as group_name\n" + 
			"  from pe_bzz_student t, pe_enterprise pe, pe_enterprise p\n" + 
			" where t.id = '"+student_id+"'\n" + 
			"   and t.fk_enterprise_id = pe.id\n" + 
			"   and pe.fk_parent_id = p.id";
		//System.out.println("print_late_apply:"+temp_sql);
		String reg_no="";
		String enterprise_name="";
		String group_name="";
		String phone="";
		String name = "";
		
		dbpool db = new dbpool();
	    MyResultSet rs = null;
	
		rs = db.executeQuery(temp_sql);
		while (rs != null && rs.next()) {
			reg_no = fixnull(rs.getString("reg_no"));
			phone = fixnull(rs.getString("phone"));
			enterprise_name = fixnull(rs.getString("enterprise_name"));
			group_name = fixnull(rs.getString("group_name"));
			name =  fixnull(rs.getString("name"));
		}
		db.close(rs);
		
	
%>

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 11">
<meta name=Originator content="Microsoft Word 11">
<link rel=File-List href="缓考表.files/filelist.xml">
<title>中国证券业协会远程培训系统</title>
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>acer</o:Author>
  <o:LastAuthor>MC SYSTEM</o:LastAuthor>
  <o:Revision>2</o:Revision>
  <o:TotalTime>2</o:TotalTime>
  <o:Created>2010-11-12T02:39:00Z</o:Created>
  <o:LastSaved>2010-11-12T02:39:00Z</o:LastSaved>
  <o:Pages>1</o:Pages>
  <o:Words>41</o:Words>
  <o:Characters>236</o:Characters>
  <o:Lines>1</o:Lines>
  <o:Paragraphs>1</o:Paragraphs>
  <o:CharactersWithSpaces>276</o:CharactersWithSpaces>
  <o:Version>11.5606</o:Version>
 </o:DocumentProperties>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:View>Print</w:View>
  <w:SpellingState>Clean</w:SpellingState>
  <w:GrammarState>Clean</w:GrammarState>
  <w:PunctuationKerning/>
  <w:DrawingGridVerticalSpacing>7.8 磅</w:DrawingGridVerticalSpacing>
  <w:DisplayHorizontalDrawingGridEvery>0</w:DisplayHorizontalDrawingGridEvery>
  <w:DisplayVerticalDrawingGridEvery>2</w:DisplayVerticalDrawingGridEvery>
  <w:ValidateAgainstSchemas/>
  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>
  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>
  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>
  <w:Compatibility>
   <w:SpaceForUL/>
   <w:BalanceSingleByteDoubleByteWidth/>
   <w:DoNotLeaveBackslashAlone/>
   <w:ULTrailSpace/>
   <w:DoNotExpandShiftReturn/>
   <w:AdjustLineHeightInTable/>
   <w:BreakWrappedTables/>
   <w:SnapToGridInCell/>
   <w:WrapTextWithPunct/>
   <w:UseAsianBreakRules/>
   <w:DontGrowAutofit/>
   <w:UseFELayout/>
  </w:Compatibility>
  <w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel>
 </w:WordDocument>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:LatentStyles DefLockedState="false" LatentStyleCount="156">
 </w:LatentStyles>
</xml><![endif]-->
<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:宋体;
	panose-1:2 1 6 0 3 1 1 1 1 1;
	mso-font-alt:SimSun;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:3 135135232 16 0 262145 0;}
@font-face
	{font-family:"\@宋体";
	panose-1:2 1 6 0 3 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:3 135135232 16 0 262145 0;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
 /* Page Definitions */
 @page
	{mso-page-border-surround-header:no;
	mso-page-border-surround-footer:no;}
@page Section1
	{size:595.3pt 841.9pt;
	margin:72.0pt 90.0pt 72.0pt 90.0pt;
	mso-header-margin:42.55pt;
	mso-footer-margin:49.6pt;
	mso-paper-source:0;
	layout-grid:15.6pt;}
div.Section1
	{page:Section1;}
-->
</style>
<!--[if gte mso 10]>
<style>
 /* Style Definitions */
 table.MsoNormalTable
	{mso-style-name:普通表格;
	mso-tstyle-rowband-size:0;
	mso-tstyle-colband-size:0;
	mso-style-noshow:yes;
	mso-style-parent:"";
	mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
	mso-para-margin:0cm;
	mso-para-margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:10.0pt;
	font-family:"Times New Roman";
	mso-ansi-language:#0400;
	mso-fareast-language:#0400;
	mso-bidi-language:#0400;}
</style>
<![endif]--><!--[if gte mso 9]><xml>
 <o:shapedefaults v:ext="edit" spidmax="2050"/>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <o:shapelayout v:ext="edit">
  <o:idmap v:ext="edit" data="1"/>
 </o:shapelayout></xml><![endif]-->
</head>

<body lang=ZH-CN style='tab-interval:21.0pt;text-justify-trim:punctuation'>

<div class=Section1 style='layout-grid:15.6pt'>

<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:
normal'><span style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:
"Times New Roman";mso-hansi-font-family:"Times New Roman"'>中国证券业协会远程培训系统</span></b><span
lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></p>

<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:
normal'><span style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:
"Times New Roman";mso-hansi-font-family:"Times New Roman"'>缓考申请表</span></b><b
style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>

<p class=MsoNormal ><b style='mso-bidi-font-weight:normal'><span
style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
mso-hansi-font-family:"Times New Roman"'>集团公司名称&nbsp;&nbsp;&nbsp;&nbsp;<%=group_name %></span></b><b style='mso-bidi-font-weight:
normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>

<p class=MsoNormal ><b style='mso-bidi-font-weight:normal'><span
style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
mso-hansi-font-family:"Times New Roman"'>工作单位名称：&nbsp;<%=enterprise_name %></span></b><b style='mso-bidi-font-weight:
normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>

<table class=MsoNormalTable  border=1 cellspacing=0 cellpadding=0
 style='margin-left:5.4pt;border-collapse:collapse;border:none;mso-border-alt:
 solid windowtext .5pt;mso-padding-alt:0cm 5.4pt 0cm 5.4pt;mso-border-insideh:
 .5pt solid windowtext;mso-border-insidev:.5pt solid windowtext'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:30.75pt'>
  <td width=60 valign=top style='width:45.0pt;border:solid windowtext 1.0pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:30.75pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>姓名</span></b><b style='mso-bidi-font-weight:
  normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=84 valign=center style='width:63.0pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:30.75pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span lang=EN-US
  style='font-size:12.0pt'><o:p><%=name %></o:p></span></b></p>
  </td>
  <td width=52 valign=top style='width:38.95pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:30.75pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>系统编号</span></b><b style='mso-bidi-font-weight:
  normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=168 valign=center style='width:126.0pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:30.75pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span lang=EN-US
  style='font-size:12.0pt'><o:p><%=reg_no %></o:p></span></b></p>
  </td>
  <td width=96 valign=top style='width:72.0pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:30.75pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>联系方式</span></b><b style='mso-bidi-font-weight:
  normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=96 valign=center style='width:72.0pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:30.75pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span lang=EN-US
  style='font-size:12.0pt'><o:p><%=phone %></o:p></span></b></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1;height:185.0pt'>
  <td width=556 colspan=6 valign=top style='width:416.95pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:185.0pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>缓考原因：</span></b><b style='mso-bidi-font-weight:
  normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:2;height:116.25pt'>
  <td width=556 colspan=6 valign=top style='width:416.95pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:116.25pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>本单位审核意见：</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span lang=EN-US
  style='font-size:14.0pt'><o:p>&nbsp;</o:p></span></b></p>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span lang=EN-US
  style='font-size:14.0pt'><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span></span></b><b style='mso-bidi-font-weight:normal'><span
  style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>（盖章）</span></b><b style='mso-bidi-font-weight:
  normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span lang=EN-US
  style='font-size:14.0pt'><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span></span></b><b style='mso-bidi-font-weight:normal'><span
  style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>年</span></b><b style='mso-bidi-font-weight:
  normal'><span lang=EN-US style='font-size:14.0pt'><span
  style='mso-spacerun:yes'>&nbsp;&nbsp; </span></span></b><b style='mso-bidi-font-weight:
  normal'><span style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>月</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:14.0pt'><span
  style='mso-spacerun:yes'>&nbsp;&nbsp; </span></span></b><b style='mso-bidi-font-weight:
  normal'><span style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>日</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:3;mso-yfti-lastrow:yes;height:114.85pt'>
  <td width=556 colspan=6 valign=top style='width:416.95pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:114.85pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>一级集团管理员意见：</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span lang=EN-US
  style='font-size:14.0pt'><o:p>&nbsp;</o:p></span></b></p>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span lang=EN-US
  style='font-size:14.0pt'><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span></b><b
  style='mso-bidi-font-weight:normal'><span style='font-size:14.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>（盖章）</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span lang=EN-US
  style='font-size:14.0pt'><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span></span></b><b style='mso-bidi-font-weight:normal'><span
  style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>年</span></b><b style='mso-bidi-font-weight:
  normal'><span lang=EN-US style='font-size:14.0pt'><span
  style='mso-spacerun:yes'>&nbsp;&nbsp; </span></span></b><b style='mso-bidi-font-weight:
  normal'><span style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>月</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:14.0pt'><span
  style='mso-spacerun:yes'>&nbsp;&nbsp; </span></span></b><b style='mso-bidi-font-weight:
  normal'><span style='font-size:14.0pt;font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>日</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:14.0pt'><o:p></o:p></span></b></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span lang=EN-US
style='font-size:12.0pt'><o:p>&nbsp;</o:p></span></b></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

</div>

</body>

</html>
