<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%
	String id = request.getParameter("id");
	
	String sql = "select twy.id,twy.querstion,su.login_id,twy.twy_date \n"
		+ "from pe_bzz_onlinecourse_twy twy inner join sso_user su on twy.fk_sso_user_id=su.id \n" 
		+ "where twy.id='"+id+"' ";
	dbpool db = new dbpool();
	MyResultSet rs = null;
	rs = db.executeQuery(sql);
	String querstion = "";
	String login_id = "";
	String twy_date = "";
	if(rs.next()) {
		querstion = rs.getString("querstion");
		login_id = rs.getString("login_id");
		twy_date = rs.getString("twy_date");
	}
	db.close(rs);
%>
<html>
	<head>

		<title></title>
		<meta http-equiv="expires" content="0">
		<link href="./css/css.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
	</head>

	<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">

		<div id="main_content">
			<div class="content_title">
				在线课堂问题
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<table width="60%" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="26" align="center" valign="middle" colspan="2">
								在线课堂问题
							</td>
						</tr>
						<tr>
							<td height="8">
							</td>
						</tr>

						<tr valign="middle">
							<td width="15%" height="30" align="left" class="postFormBox">
								<span class="name">内容：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><textarea rows="15" cols="70"><%=querstion %></textarea>
							</td>
						</tr>
						
						<tr valign="middle">
							<td width="15%" height="30" align="left" class="postFormBox">
								<span class="name"> 提问时间：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=twy_date%></td>
						</tr>
						<tr>
							<td height="8">
							</td>
						</tr>

						<tr valign="middle">
							<td width="15%" height="30" align="left" class="postFormBox">
								<span class="name"> 提问人：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=login_id%></td>
						</tr>
						<tr>
							<td height="8">
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<button value="关闭" onclick="window.close();" class="name">关闭</button>
							</td>
						</tr>
					</table>

				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
