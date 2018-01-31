<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<%	response.setHeader("Content-Disposition", "attachment;filename=progress_list.xls"); %>

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
	String type=fixnull(request.getParameter("type"));
	String sql_con = "";
	String sql_con1="";
	String sql_con2="";
	String title="";
	
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
		sql_con+=" and pe.id in "+sql_ent;
	}
	
	
	if(type.equals("1"))
	{
		sql_con1=" and per<25";
		title="  学习进度低于25%学员列表";
	}
	else if(type.equals("2"))
	{
		sql_con1=" and per>=25 and per<50";
		title="  学习进度在25%-50%的学员列表";
	}
	else if(type.equals("3"))
	{
		sql_con1=" and per>=50 and per<80";
		title="  学习进度在50%-80%的学员列表";
	}
	else if(type.equals("4"))
	{
		sql_con1=" and per>=80 and per<100";
		title="  学习进度在80%-100%的学员列表";
	}
	else if(type.equals("5"))
	{
		sql_con1=" and per=100";
		title="   已完成基础课程学习的学员列表";
	}
	else if(type.equals("6"))
	{
		sql_con2=" and ps.photo is null";
		title="  未上传照片学员列表";
	}
		
	dbpool db = new dbpool();
	MyResultSet rs = null;
	
	String batchName = "";
	
	String sql = "";
	if(batch_id != null && !batch_id.equals("")) {
		sql_con+= " and ps.fk_batch_id='" + batch_id + "' \n";
		
		sql = "select batch.name as bname from pe_bzz_batch batch where batch.id='"+batch_id+"'";
		rs = db.executeQuery(sql);
		
		if(rs.next()) {
			batchName = rs.getString("bname");
		}
		db.close(rs);
	}
	
	//针对2010春季学员，企业管理概览和企业管理概览（新），班组安全管理和班组安全管理（新）中选取进度高的一门，保证基础课共16门
	String sql_training=" training_course_student ts,";
	if(batch_id.equals("ff8080812824ae6f012824b0a89e0008")){
		sql_training="("+
		"select course_id,percent,learn_status,student_id from training_course_student s where s.course_id not in\n" +
		"('ff8080812910e7e601291150ddc70419','ff8080812bf5c39a012bf6a1bab80820','ff8080812910e7e601291150ddc70413','ff8080812bf5c39a012bf6a1baba0821')\n" + 
		"union\n" + 
		"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
		"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"+
		"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
		"select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812910e7e601291150ddc70419' )a,\n" + 
		"(select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812bf5c39a012bf6a1bab80820')b\n" + 
		"where a.student_id=b.student_id\n" + 
		"union\n" + 
		"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
		"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"+
		"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
		"select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812910e7e601291150ddc70413' )a,\n" + 
		"(select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812bf5c39a012bf6a1baba0821')b\n" + 
		"where a.student_id=b.student_id"+
		") ts,";
	}
	
    sql = 
		"select s.true_name,\n" +
		"       s.reg_no,\n" + 
		"       s.mobile_phone,\n" + 
		"       s.phone,\n" + 
		"       e.name as ename,\n" + 
		"       to_char(per, '990.9') || '%' as per\n" + 
		"  from (select ps.id,\n" + 
		"               (nvl(sum(ce.time * (ts.percent / 100)), 0) / sum(ce.time)) * 100 as per\n" + 
		"          from sso_user                su,\n" + 
		sql_training + 
		"               pe_bzz_student          ps,\n" + 
		"               pr_bzz_tch_opencourse   co,\n" + 
		"               pe_bzz_tch_course       ce,\n" + 
		"               pe_enterprise           pe\n" + 
		"         where ps.fk_sso_user_id = su.id\n" + 
		"           and su.id = ts.student_id\n" + 
		"           and pe.id = ps.fk_enterprise_id\n" + 
		"           and co.id = ts.course_id\n" + 
		"           and co.fk_course_id = ce.id\n" + 
		"           and ps.fk_batch_id = co.fk_batch_id\n" + sql_con2+
		sql_con + 
		"           and su.flag_isvalid = '2'\n" + 
		"           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
		"           and co.flag_course_type = '402880f32200c249012200c780c40001'\n" + 
		"           and su.login_num > 0\n" + 
		"         group by ps.id) a,\n" + 
		"       pe_bzz_student s,\n" + 
		"       pe_enterprise e\n" + 
		" where s.id = a.id\n" + 
		"   and e.id = s.fk_enterprise_id\n" + sql_con1+
		" order by e.name, s.reg_no";

    //System.out.println("sql:"+sql);
        
      rs = db.executeQuery(sql);
			
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 12">
<link rel=File-List href="学习进度学员列表.files/filelist.xml">
<style id="学习进度学员列表_10770_Styles">
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
.font510770
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font610770
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.xl1510770
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
.xl6310770
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
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl6410770
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

<div id="学习进度学员列表_10770" align=center x:publishsource="Excel">

<table border=0 cellpadding=0 cellspacing=0 width=822 style='border-collapse:
 collapse;table-layout:fixed;width:617pt'>
 <col width=43 style='mso-width-source:userset;mso-width-alt:1376;width:32pt'>
 <col width=60 style='mso-width-source:userset;mso-width-alt:1920;width:45pt'>
 <col width=111 style='mso-width-source:userset;mso-width-alt:3552;width:83pt'>
 <col width=106 style='mso-width-source:userset;mso-width-alt:3392;width:80pt'>
 <col width=105 style='mso-width-source:userset;mso-width-alt:3360;width:79pt'>
 <col width=293 style='mso-width-source:userset;mso-width-alt:9376;width:220pt'>
 <col width=104 style='mso-width-source:userset;mso-width-alt:3328;width:78pt'>
 <tr height=37 style='mso-height-source:userset;height:27.75pt'>
  <td colspan=7 height=37 class=xl6310770 width=822 style='height:27.75pt;
  width:617pt'><%=title%></td>
 </tr>
 <tr height=37 style='mso-height-source:userset;height:27.75pt'>
  <td colspan=7 height=37 class=xl6310770 width=822 style='height:27.75pt;
  width:617pt'>学期：<%=batchName%><span style='mso-spacerun:yes'>&nbsp;&nbsp;
  </span>导出时间：<%=getCurDate()%></td>
 </tr>
 <tr height=31 style='mso-height-source:userset;height:23.25pt'>
  <td height=31 class=xl6410770 style='height:23.25pt;border-top:none'>　</td>
  <td class=xl6410770 style='border-top:none;border-left:none'>姓名</td>
  <td class=xl6410770 style='border-top:none;border-left:none'>系统编号</td>
  <td class=xl6410770 style='border-top:none;border-left:none'>手机号</td>
  <td class=xl6410770 style='border-top:none;border-left:none'>固定电话</td>
  <td class=xl6410770 style='border-top:none;border-left:none'>所在企业</td>
  <td class=xl6410770 style='border-top:none;border-left:none'>已完成百分比</td>
 </tr>
  <%
 	int index = 1;
 	while(rs.next()) {
 %>
 <tr height=31 style='mso-height-source:userset;height:23.25pt'>
  <td height=31 class=xl6410770 style='height:23.25pt;border-top:none'><%=index%></td>
  <td class=xl6410770 style='border-top:none;border-left:none'><%=fixnull(rs.getString("true_name"))%></td>
  <td class=xl6410770 style='border-top:none;border-left:none'><%=fixnull(rs.getString("reg_no"))%></td>
  <td class=xl6410770 style='border-top:none;border-left:none'><%=fixnull(rs.getString("mobile_phone"))%></td>
  <td class=xl6410770 style='border-top:none;border-left:none'><%=fixnull(rs.getString("phone"))%></td>
  <td class=xl6410770 style='border-top:none;border-left:none'><%=fixnull(rs.getString("ename"))%></td>
  <td class=xl6410770 style='border-top:none;border-left:none'><span
  style='mso-spacerun:yes'>&nbsp;&nbsp; </span><%=fixnull(rs.getString("per"))%></td>
 </tr>
  <%
 		index++;
 	}
 	db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=43 style='width:32pt'></td>
  <td width=60 style='width:45pt'></td>
  <td width=111 style='width:83pt'></td>
  <td width=106 style='width:80pt'></td>
  <td width=105 style='width:79pt'></td>
  <td width=293 style='width:220pt'></td>
  <td width=104 style='width:78pt'></td>
 </tr>
 <![endif]>
</table>

</div>


<!----------------------------->
<!--“从 EXCEL 发布网页”向导结束-->
<!----------------------------->
</body>

</html>
