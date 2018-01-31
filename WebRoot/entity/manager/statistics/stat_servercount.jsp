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
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
 %>
<%
	String batch_id = fixnull(request.getParameter("batch_id"));
	String sql_con = "";
	if(!batch_id.equals("")) {
		sql_con = " and ps.fk_batch_id='" + batch_id + "'";
	}
	String sql = "  select count(*) as loginPersons \n"
		+ "from sso_user s ,pe_bzz_student ps\n"
		+ "where (s.login_num is not null and s.login_num<>0)  and fk_role_id ='0' and s.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n"
		+ "and ps.fk_sso_user_id = s.id \n"
		+ sql_con;
	//	System.out.println(sql);
	dbpool db = new dbpool();
	MyResultSet rs = db.executeQuery(sql);
	int loginPersons = 0; //已登录平台人数

	if (rs != null && rs.next()) {
		loginPersons = rs.getInt("loginPersons");
	}
	db.close(rs);

	sql = "select count(fk_sso_user_id) as loginAndStudyPersons from \n"
+ "(select fk_sso_user_id from  \n"
+ "(select ps.fk_sso_user_id \n"
+ "  from (select distinct student_id as student_id from scorm_stu_course) c, \n"
+ "       sso_user u, \n"
+ "       pe_bzz_student ps \n"
+ " where c.student_id = u.id \n"
+ "   and ps.fk_sso_user_id = u.id \n"
+ "   and u.flag_isvalid = '2'"+ sql_con + ") \n"
+ "   minus \n"
+ "   select ps.fk_sso_user_id from pe_bzz_student ps where ps.flag_rank_state='402880f827f5b99b0127f5bdadc70005' "+ sql_con+" ) ";
			;
//System.out.println(sql);
	rs = db.executeQuery(sql);
	int loginAndStudyPersons = 0; //已登录并有学习记录人数
	if (rs != null && rs.next()) {
		loginAndStudyPersons = rs.getInt("loginAndStudyPersons");
	}
	db.close(rs);
	sql = "  select count(*) as noLoginPersons \n"
			+ "from sso_user s , pe_bzz_student ps \n"
			+ "where (s.login_num is null or s.login_num=0)  and fk_role_id ='0' and s.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006'"
			+ "   and ps.fk_sso_user_id=s.id \n"
      		+ sql_con;
	//System.out.println(sql);
	rs = db.executeQuery(sql);
	int noLoginPersons = 0; //完全未登录人数
	if (rs != null && rs.next()) {
		noLoginPersons = rs.getInt("noLoginPersons");
	}
	db.close(rs);
%>
<html>
	<head>

		<title></title>
		<meta http-equiv="expires" content="0">
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript">
		function changeSelect(e)
		{
			e.form.submit();
		}
		</script>
	</head>

	<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">

		<div id="main_content">
			<div class="content_title">
				服务器状态
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<table width="554" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="26" align="center" valign="middle" colspan="2">
								服务器人数统计
							</td>
						</tr>
						<tr>
							<td height="8">
							</td>
						</tr>

						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">登陆人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=SessionCounter.getActiveSessions()%>
							</td>
						</tr>
						
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name"> 在线人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=SessionCounter.getOnLineCount()%></td>
						</tr>
						<tr>
							<td height="8">
							</td>
						</tr>

						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name"> 历史访问量：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=SessionCounter.getCount()%></td>
						</tr>
						<tr>
							<td height="8">
							</td>
						</tr>

						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">请选择学期：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<form
									action="/entity/manager/statistics/stat_servercount.jsp"
									method="post">
									<select id="batch_id" name="batch_id"
										onchange="changeSelect(this)">
										<option value="">
											所有学期
										</option>
										<%
											String t_sql = "select id,name from pe_bzz_batch order by name";
											rs = db.executeQuery(t_sql);
											while (rs != null && rs.next()) {
												String t_id = fixnull(rs.getString("id"));
												String t_name = fixnull(rs.getString("name"));
												String selected = "";
												//System.out.println(search);
												if (batch_id.equals(t_id))
													selected = "selected";
												//System.out.println(selected);
										%>
										<option value="<%=t_id%>" <%=selected%>><%=t_name%></option>
										<%
											}
											db.close(rs);
										%>
									</select>
								</form>
							</td>
						</tr>

						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name"> 已登录平台人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=loginPersons%></td>
						</tr>
						<tr valign="middle">
							<td width="200" height="30" align="left" class="postFormBox">
								<span class="name"> 已登录并有学习记录人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=loginAndStudyPersons%></td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name"> 完全未登录人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=noLoginPersons%></td>
						</tr>
	
	<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name"> 最大在线人数和学习时长统计：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><a href="<%=basePath %>/entity/manager/statistics/stat_onlineDateCount.jsp">查看</a></td>
						</tr>

						<tr>
							<td height="10">
							</td>
						</tr>
					</table>

				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
