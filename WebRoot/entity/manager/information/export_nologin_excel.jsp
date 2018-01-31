<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<%	response.setHeader("Content-Disposition", "attachment;filename=nologin_list.xls"); %>

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
	
	String getDate(Date d) 
	{
		if(d == null) {
			return "";
		}
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy年MM月dd日");
  		return dateformat1.format(d);
	}
%>

<%
	
	String batch_id=fixnull(request.getParameter("batch_id"));
	String sql_con = "";
	
	UserSession usersession = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	List entList=us.getPriEnterprises();
	String sql_ent="";
	String sql_entbk="";
	for(int i=0;i<entList.size();i++)
	{
		PeEnterprise e=(PeEnterprise)entList.get(i);
		sql_ent+="'"+e.getId()+"',";
	}
	sql_entbk=sql_ent;
	if(!sql_ent.equals(""))
	{
		sql_ent="("+sql_ent.substring(0,sql_ent.length()-1)+")";
	}
	
	//System.out.println("sql_ent:"+sql_ent);
	if(!us.getRoleId().equals("3")&&!sql_ent.equals(""))
	{
		sql_con+=" and e.id in "+sql_ent;
	}
	dbpool db = new dbpool();
	MyResultSet rs = null;
	
	String batchName = "";
	String sql_con1="";
	
	String sql = "";
	if(batch_id != null && !batch_id.equals("")) {
		sql_con+= " and bs.fk_batch_id='" + batch_id + "' \n";
		sql_con1+= " and bs.batch_id='" + batch_id + "' \n";
		sql = "select batch.name as bname from pe_bzz_batch batch where batch.id='"+batch_id+"'";
		rs = db.executeQuery(sql);
		
		if(rs.next()) {
			batchName = rs.getString("bname");
		}
		db.close(rs);
	}

    sql = 
		"select bs.true_name as name,\n" +
		"       bs.reg_no,\n" + 
		"       bs.mobile_phone,\n" + 
		"       bs.phone,\n" + 
		"       e.name as ename,\n" + 
		"       s.login_num\n" + 
		"  from sso_user s,\n" + 
		"       pe_bzz_student bs,\n" + 
		"       pe_enterprise e\n" + 
	//	"       (select distinct student_id from training_course_student) cs\n" + 
		" where (s.login_num = 0 or s.login_num is null)\n" + 
		"   and s.id = bs.fk_sso_user_id\n" + 
		"   and e.id = bs.fk_enterprise_id\n" + 
		"   and s.flag_isvalid = '2'\n" + 
		"   and bs.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
		sql_con+ 
	//	"   and cs.student_id = s.id\n" + 
		" order by e.name, bs.reg_no";
		/*sql = 
		"select bs.name as name,\n" +
		"       bs.reg_no,\n" + 
		"       bs.mobile_phone,\n" + 
		"       bs.phone,\n" + 
		"       bs.erji_name as ename,\n" + 
		"       bs.login_num\n" + 
		"  from stat_study_summary bs\n" + 
		" where (bs.login_num = 0 or bs.login_num is null)\n" + sql_con1+
		" order by bs.name, bs.reg_no";*/
      // System.out.println(sql);
        
      rs = db.executeQuery(sql);
			
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 12">
<link rel=File-List href="未登录.files/filelist.xml">
<style id="未登录_24062_Styles">
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
.font524062
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font624062
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.xl6324062
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
	mso-number-format:"\@";
	text-align:general;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl6424062
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
	mso-number-format:"\@";
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl6524062
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
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
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

<div id="未登录_24062" align=center x:publishsource="Excel">

<table border=0 cellpadding=0 cellspacing=0 width=846 class=xl6324062
 style='border-collapse:collapse;table-layout:fixed;width:635pt'>
 <col class=xl6324062 width=20 style='mso-width-source:userset;mso-width-alt:
 640;width:15pt'>
 <col class=xl6324062 width=65 style='mso-width-source:userset;mso-width-alt:
 2080;width:49pt'>
 <col class=xl6324062 width=111 style='mso-width-source:userset;mso-width-alt:
 3552;width:83pt'>
 <col class=xl6324062 width=102 style='mso-width-source:userset;mso-width-alt:
 3264;width:77pt'>
 <col class=xl6324062 width=115 style='mso-width-source:userset;mso-width-alt:
 3680;width:86pt'>
 <col class=xl6324062 width=361 style='mso-width-source:userset;mso-width-alt:
 11552;width:271pt'>
 <col class=xl6324062 width=72 style='width:54pt'>
 <tr height=35 style='mso-height-source:userset;height:26.25pt'>
  <td colspan=7 height=35 class=xl6524062 width=846 style='height:26.25pt;
  width:635pt'>学期：<%=batchName%><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span>导出时间：<%=getCurDate()%></td>
 </tr>
 <tr height=25 style='mso-height-source:userset;height:18.75pt'>
  <td height=25 class=xl6424062 style='height:18.75pt;border-top:none'>　</td>
  <td class=xl6424062 style='border-top:none;border-left:none'>姓名</td>
  <td class=xl6424062 style='border-top:none;border-left:none'>系统编号</td>
  <td class=xl6424062 style='border-top:none;border-left:none'>手机号</td>
  <td class=xl6424062 style='border-top:none;border-left:none'>固定电话</td>
  <td class=xl6424062 style='border-top:none;border-left:none'>所在企业</td>
  <td class=xl6424062 style='border-top:none;border-left:none'>登录次数</td>
 </tr>
  <%
 	int index = 1;
 	while(rs.next()) {
 %>
 <tr height=31 style='mso-height-source:userset;height:23.25pt'>
  <td height=31 class=xl6424062 style='height:23.25pt;border-top:none'><%=index%></td>
  <td class=xl6424062 style='border-top:none;border-left:none'><%=fixnull(rs.getString("name"))%></td>
  <td class=xl6424062 style='border-top:none;border-left:none'><%=fixnull(rs.getString("reg_no"))%></td>
  <td class=xl6424062 style='border-top:none;border-left:none'><%=fixnull(rs.getString("mobile_phone"))%></td>
  <td class=xl6424062 style='border-top:none;border-left:none'><%=fixnull(rs.getString("phone"))%></td>
  <td class=xl6424062 style='border-top:none;border-left:none'><%=fixnull(rs.getString("ename"))%></td>
  <td class=xl6424062 style='border-top:none;border-left:none'><%=fixnull(rs.getString("login_num"))%></td>
 </tr>
  <%
 		index++;
 	}
 	db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=20 style='width:15pt'></td>
  <td width=65 style='width:49pt'></td>
  <td width=111 style='width:83pt'></td>
  <td width=102 style='width:77pt'></td>
  <td width=115 style='width:86pt'></td>
  <td width=361 style='width:271pt'></td>
  <td width=72 style='width:54pt'></td>
 </tr>
 <![endif]>
</table>

</div>


<!----------------------------->
<!--“从 EXCEL 发布网页”向导结束-->
<!----------------------------->
</body>

</html>
