<%@ page language="java" import="com.whaty.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>

<%
	String student_id = fixnull(request.getParameter("student_id"));
	String sql_con = "";
	if(student_id == null || student_id.equals("")) {
		%>
		<script type="text/javascript">
			alert("请选择学生");
			window.close();
		</script>
		<%
		return ;
	}
	
	String batch_id="";
	
	dbpool db = new dbpool();
	MyResultSet rs=null;
	String stu_sql="select fk_batch_id from pe_bzz_student where id='"+student_id+"'";
	rs=db.executeQuery(stu_sql);
	if(rs!=null && rs.next())
	{
		batch_id=fixnull(rs.getString("fk_batch_id"));
	}
	db.close(rs);
	
	String sql="";
	if(batch_id!=null && (batch_id.equals("ff8080812824ae6f012824b0a89e0008")||batch_id.equals("ff8080812253f04f0122540a58000004")))
	{
	sql = "select ps.true_name as stu_name,ps.reg_no,ps.gender,pe.name as enterprise_name,ps.phone,ps.mobile_phone,btc.name as course_name, \n"
		+ "       (case when tcs.percent=0 then '未开始' when tcs.percent=100 then '已学完' else '未学完' end) as course_status, \n"
		+ "       (case when exists(select id from pe_bzz_assess ba where ba.fk_course_id=bto.id and ba.fk_student_id=ps.fk_sso_user_id) then '已评估' else '未评估' end) as pgstatus, \n"
		+ "       (select (select count(*) from test_homeworkpaper_history hh ,test_homeworkpaper_info hi  \n"
		+ "               where hh.testpaper_id=hi.id and hi.group_id=btc.id and hh.t_user_id='"+student_id+"')||'/'|| \n"
		+ "               (select count(*) from test_homeworkpaper_info hi where hi.group_id=btc.id and hi.batch_id is null) from dual) as zystatus, \n"
		+ "       (select (select count(*) from test_testpaper_history th ,test_testpaper_info ti  \n"
		+ "where th.testpaper_id=ti.id and ti.group_id=btc.id and th.score>=60 and th.t_user_id = '"+student_id+"')||'/'||  \n"
		+ "(select count(*) from onlinetest_course_info ti where ti.group_id=btc.id and ti.fk_batch_id is null ) from dual) as zcstatus \n"
		+ "from pe_bzz_student ps,pe_enterprise pe,training_course_student tcs,pr_bzz_tch_opencourse bto,pe_bzz_tch_course btc \n"
		+ "where ps.fk_enterprise_id=pe.id and tcs.student_id=ps.fk_sso_user_id and tcs.course_id=bto.id and bto.fk_course_id=btc.id and ps.id='"+student_id+"'and bto.flag_course_type='402880f32200c249012200c780c40001'";
	}
	else
	{
		sql = "select ps.true_name as stu_name,ps.reg_no,ps.gender,pe.name as enterprise_name,ps.phone,ps.mobile_phone,btc.name as course_name, \n"
		+ "       (case when tcs.percent=0 then '未开始' when tcs.percent=100 then '已学完' else '未学完' end) as course_status, \n"
		+ "       (case when exists(select id from pe_bzz_assess ba where ba.fk_course_id=bto.id and ba.fk_student_id=ps.fk_sso_user_id) then '已评估' else '未评估' end) as pgstatus, \n"
		+ "       (select (select count(*) from test_homeworkpaper_history hh ,test_homeworkpaper_info hi  \n"
		+ "               where hh.testpaper_id=hi.id and hi.group_id=btc.id and hh.t_user_id='"+student_id+"')||'/'|| \n"
		+ "               (select count(*) from test_homeworkpaper_info hi where hi.group_id=btc.id and hi.batch_id='"+batch_id+"') from dual) as zystatus, \n"
		+ "       (select (select count(*) from test_testpaper_history th ,test_testpaper_info ti  \n"
		+ "where th.testpaper_id=ti.id and ti.group_id=btc.id and th.score>=60 and th.t_user_id = '"+student_id+"')||'/'||  \n"
		+ "(select count(*) from onlinetest_course_info ti where ti.group_id=btc.id and ti.fk_batch_id='"+batch_id+"') from dual) as zcstatus \n"
		+ "from pe_bzz_student ps,pe_enterprise pe,training_course_student tcs,pr_bzz_tch_opencourse bto,pe_bzz_tch_course btc \n"
		+ "where ps.fk_enterprise_id=pe.id and tcs.student_id=ps.fk_sso_user_id and tcs.course_id=bto.id and bto.fk_course_id=btc.id and ps.id='"+student_id+"'and bto.flag_course_type='402880f32200c249012200c780c40001'";
	}
		//System.out.println(sql); 
	
%>
<html>
	<head>

		<title></title>
		<meta http-equiv="expires" content="0">
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		
	</head>

	<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">

		<div id="main_content">
			<div class="content_title">学员学习明细 
			</div>
			<div class="cntent_k">
				<div class="k_cc">
				<%
				
					 rs = db.executeQuery(sql);
					if(rs.next()) {
					
					String name = fixnull(rs.getString("stu_name"));
					String regNo = fixnull(rs.getString("reg_no"));
					String gender = fixnull(rs.getString("gender"));
					String phone = fixnull(rs.getString("phone"));
					String mobile = fixnull(rs.getString("mobile_phone"));
					String enterpriseName = fixnull(rs.getString("enterprise_name"));
				 %>
					<table width="800" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="26" align="center" valign="middle" colspan="2">
								学员学习明细
							</td>
						</tr>
						<tr>
							<td colspan="3">
							<table width="100%">
								<tr>
								<td width="10%"><span class="name">姓&nbsp;&nbsp; &nbsp; &nbsp;&nbsp; 名：</span></td><td class="postFormBox" width="30%"><%=name %></td><td width="10%"><span class="name">学&nbsp;&nbsp; &nbsp; &nbsp;&nbsp; 号：</span></td><td class="postFormBox" width="30%"><%=regNo %></td><td width="10%"><span class="name">性&nbsp; &nbsp; &nbsp; &nbsp; 别：</span></td><td class="postFormBox" width="10%"><%=gender %></td>
								</tr>
								<tr>
								<td><span class="name">固定电话：</span></td><td colspan="1" class="postFormBox"><%=phone %></td><td><span class="name">移动电话：</span></td><td colspan="" class="postFormBox"><%=mobile %></td>
								</tr>
								<tr>
								<td><span class="name">所在企业：</span></td><td colspan="5" class="postFormBox"><%=enterpriseName %></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td><br /><br /></td>
						</tr>
						<tr><td>
						<table width="100%">
						<tr>
							<td><span class="name">课程名称</span></td>
							<td align="center" width="13%"><span class="name">学习情况</span></td>
							<td align="center" width="13%"><span class="name">评估状态</span></td>
							<td align="center" width="13%"><span class="name">作业情况</span></td>
							<td align="center" width="13%"><span class="name">自测情况</span></td>
						</tr>
						<%
							do {
								String courseName = fixnull(rs.getString("course_name"));
								String courseStatus = fixnull(rs.getString("course_status"));
								String pgStatus = fixnull(rs.getString("pgstatus"));
								String zyStatus = fixnull(rs.getString("zystatus"));
								String zcstatus = fixnull(rs.getString("zcstatus"));
								
							%>
								<tr>
									<td class="postFormBox"><%=courseName %></td>
									<td class="postFormBox" align="center"><%=courseStatus %></td>
									<td class="postFormBox" align="center"><%=pgStatus %></td>
									<td class="postFormBox" align="center"><%=zyStatus %></td>
									<td class="postFormBox" align="center"><%=zcstatus %></td>								
								</tr>
							<%
							} while(rs.next());
						 %>
						</table>
						</td></tr>
					</table>
					
				<%
				}
				db.close(rs); %>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
