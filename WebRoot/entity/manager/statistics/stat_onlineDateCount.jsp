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
				在线数据统计
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<table width="554" border="1" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="26" align="center" valign="middle" colspan="3">
								在线数据统计
							</td>
						</tr>

						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								统计名称：
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								说明
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								浏览
							</td>
						</tr>

						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								每天最大在线人数统计：
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								以当前时间往前十天内的每天最大在线人数的数据统计
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<a href="stat_online_one.jsp" ><span class="name">浏览</span></a>
							</td>
						</tr>
						
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								 每天学习总时长增长值：
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								以当前时间往前数十天内，每两天学习总时长差值的数据统计
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<a href="stat_online_two.jsp" ><span class="name">浏览</span></a>
						    </td>
						</tr>

						
					</table>

				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
