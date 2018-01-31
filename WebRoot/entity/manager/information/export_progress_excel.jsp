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
	//System.out.println("type>>>>>>>>>>>>>>:"+type);
	
	UserSession usersession = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	List entList=us.getPriEnterprises();
	String sql_ent="";
	String sql_entbk="";
	String sql_entperise="";
	String sql_entperise1="";
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
		sql_entperise+=" and bs.erji_id in "+sql_ent;
		sql_entperise1=" and ps.fk_enterprise_id in "+sql_ent;
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
	
	String sql_batch="";
	
	String sql = "";
	if(batch_id != null && !batch_id.equals("")) {
		sql_con+= " and bs.fk_batch_id='" + batch_id + "' \n";
		sql_batch+= " and bs.batch_id='" + batch_id + "' \n";
		sql = "select batch.name as bname from pe_bzz_batch batch where batch.id='"+batch_id+"'";
		rs = db.executeQuery(sql);
		
		if(rs.next()) {
			batchName = rs.getString("bname");
		}
		db.close(rs);
	}
	
	sql = "select name,reg_no,mobile_phone,phone,ename,to_char(per,'990.9') as per,photo from ("+
		"select bs.name as name,\n" +
		"       bs.reg_no,\n" + 
		"       bs.mobile_phone,\n" + 
		"       bs.phone,\n" + 
		"       bs.erji_name as ename,\n" + 
		"       bs.login_num,(bs.percent) as per,bs.photo\n" + 
		"  from stat_study_summary bs\n" + 
		" where bs.login_num>=0 \n" + sql_batch+sql_entperise+
		" ) ps where 1=1 "+sql_con1+sql_con2+" union "+
		"select ps.true_name as name,ps.reg_no,ps.mobile_phone,ps.phone,e.name as enterprise_name,'' as per,'' as photo  from pe_bzz_student ps,sso_user u,pe_enterprise e where ps.fk_sso_user_id=u.id \n"
				+ "and  (u.login_num=0 or u.login_num is null) and ps.photo is null " + " and ps.fk_batch_id='"+batch_id+"' \n"
				+" and u.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and e.id=ps.fk_enterprise_id"+sql_entperise1;;
	sql+=" order by reg_no";
	//System.out.println("sql_con1:"+sql_con1);
  // System.out.println("sql:"+sql);
        
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
  <td class=xl6410770 style='border-top:none;border-left:none'><%=fixnull(rs.getString("name"))%></td>
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
