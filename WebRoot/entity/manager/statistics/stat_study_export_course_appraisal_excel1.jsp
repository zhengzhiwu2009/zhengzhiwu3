<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=stat_study_export_course_appraisal_excel1.xls"); %>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.SimpleDateFormat"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

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
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
  		return dateformat1.format(new Date());
	}
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 12">
<link rel=File-List href="课程评估.files/filelist.xml">
<style id="课程评估_5841_Styles">
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
.font55841
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font65841
	{color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.xl155841
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl635841
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl645841
	{padding-top:1px;
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
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl655841
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:left;
	vertical-align:bottom;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl665841
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:red;
	font-size:12.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:bottom;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl675841
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:bottom;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
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
	mso-char-type:none;}
-->
</style>
</head>

<body>
<!--[if !excel]>　　<![endif]-->
<!--下列信息由 Microsoft Office Excel 的“发布为网页”向导生成。-->
<!--如果同一条目从 Excel 中重新发布，则所有位于 DIV 标记之间的信息均将被替换。-->
<!----------------------------->
<!--“从 EXCEL 发布网页”向导开始-->
<!----------------------------->

<div id="课程评估_5841" align=center x:publishsource="Excel">
<%
	String sql="";
	dbpool db = new dbpool();
	MyResultSet rs = null;
	MyResultSet t_rs = null;
	
	String batch_id = fixnull(request.getParameter("batch_id"));
	String sql_btcon="";
		
	if (!batch_id.equals("")) {
		sql_btcon = " and bt.fk_batch_id='" + batch_id + "'";
		}
		
	sql=
"select distinct tc.name,bt.flag_course_type,tc.suqnum, tc.id\n" +
"  from pe_bzz_assess t, pe_bzz_tch_course tc, pr_bzz_tch_opencourse bt\n" + 
" where bt.fk_course_id = tc.id\n" + 
"   and t.fk_course_id = bt.id\n" + 
sql_btcon + 
" order by bt.flag_course_type,tc.suqnum";

	rs=db.executeQuery(sql);
%>
<table border=0 cellpadding=0 cellspacing=0 width=617 style='border-collapse:
 collapse;table-layout:fixed;width:464pt'>
 <col width=105 style='mso-width-source:userset;mso-width-alt:3360;width:79pt'>
 <col width=118 style='mso-width-source:userset;mso-width-alt:3776;width:89pt'>
 <col width=113 style='mso-width-source:userset;mso-width-alt:3616;width:85pt'>
 <col width=137 style='mso-width-source:userset;mso-width-alt:4384;width:103pt'>
 <col width=72 span=2 style='width:54pt'>
 <%
 while(rs!=null && rs.next())
 {
 	String course_id=rs.getString("id");
 	String course_name=rs.getString("name");
 %>
 <tr height=35 style='mso-height-source:userset;height:26.25pt'>
  <td colspan=6 height=35 class=xl655841 width=617 style='height:26.25pt;
  width:464pt'><%=course_name%></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl635841 style='height:14.25pt;border-top:none'>　</td>
  <td class=xl645841 style='border-top:none;border-left:none'>很好/有<font
  class="font65841">/合适</font></td>
  <td class=xl645841 style='border-top:none;border-left:none'>好/偏长<font
  class="font65841">/偏深</font></td>
  <td class=xl645841 style='border-top:none;border-left:none'>一般/偏短<font
  class="font65841">/偏浅</font></td>
  <td class=xl635841 style='border-top:none;border-left:none'>较差</td>
  <td class=xl645841 style='border-top:none;border-left:none'>差/无</td>
 </tr>
 <%
 	String[][] result_num=new String[9][5];
 	////很好、有、合适
 	//好/偏长/偏深
 	//一般/偏短/偏浅、偏易
 	//较差
 	//差/无/不合适
 	
 	String sql1=
"select tc.name,\n" +
"       bt.flag_course_type,\n" + 
"       tc.suqnum,\n" + 
"       f.name,\n" + 
"       f.px,\n" + 
"       decode(b.assess,'很好','1','好','2','一般','3','较差','4','差','5','有','1','无','5','合适','1','不合适','5','偏长','2','偏深','2','偏难','2','偏短','3','偏浅','3','偏易','3') as result,\n" + 
"       count(b.fk_student_id) as num\n" + 
"  from pe_bzz_assess          b,\n" + 
"       pe_bzz_course_feedback f,\n" + 
"       pe_bzz_tch_course      tc,\n" + 
"       pr_bzz_tch_opencourse  bt\n" + 
" where b.fk_feedback_id = f.id\n" + 
"   and tc.id = bt.fk_course_id\n" + 
"   and b.fk_course_id = bt.id\n" + 
"   and tc.id='"+course_id+"'\n" + sql_btcon +
"	and to_number(f.px)<10\n"+ 
" group by tc.name, bt.flag_course_type, tc.suqnum, f.name, f.px,b.assess\n" + 
" order by bt.flag_course_type, tc.suqnum, to_number(f.px)";
	
	t_rs=db.executeQuery(sql1);
	
	while(t_rs!=null && t_rs.next())
	{
		int result=t_rs.getInt("result");
		int px=t_rs.getInt("px");
		String num=t_rs.getString("num");
		result_num[px-1][result-1]=num;
	}
	db.close(t_rs);

 %>
 <%
 for(int i=0;i<9;i++)
 {
 %>
  <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl665841 style='height:14.25pt;border-top:none'><%=i+1%></td>
  <td class=xl675841 style='border-top:none;border-left:none'><%=fixnull(result_num[i][0])%></td>
  <td class=xl675841 style='border-top:none;border-left:none'><%=fixnull(result_num[i][1])%></td>
  <td class=xl675841 style='border-top:none;border-left:none'><%=fixnull(result_num[i][2])%></td>
  <td class=xl675841 style='border-top:none;border-left:none'><%=fixnull(result_num[i][3])%></td>
  <td class=xl675841 style='border-top:none;border-left:none'><%=fixnull(result_num[i][4])%></td>
 </tr>
 <%
 }
 %>
 <%
 }
 db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=105 style='width:79pt'><br/></td>
  <td width=118 style='width:89pt'><br/></td>
  <td width=113 style='width:85pt'><br/></td>
  <td width=137 style='width:103pt'><br/></td>
  <td width=72 style='width:54pt'><br/></td>
  <td width=72 style='width:54pt'><br/><br/><br/></td>
 </tr>
 <![endif]>
</table>

</div>


<!----------------------------->
<!--“从 EXCEL 发布网页”向导结束-->
<!----------------------------->
</body>

</html>
