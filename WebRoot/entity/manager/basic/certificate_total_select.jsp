<%@ page language="java" import="com.whaty.util.*,java.util.*"
	pageEncoding="UTF-8"%>
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

<html>
	<head>

		<title></title>
		<meta http-equiv="expires" content="0">
		<link href="./css/css.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
	</head>
    <%
    dbpool db = new dbpool();
	MyResultSet rs = null;
    %>
	<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">

		<div id="main_content">
			<div class="content_title">
				结业资料汇总表
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<form id="form1" action="certificate_total_excel.jsp" method="post">
						<table width="554" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="26" align="center" valign="middle" colspan="2">
									请选择导出考试批次:
								</td>
							</tr>
							<tr>
								<td height="10">
								</td>
							</tr>

							<tr valign="middle">
								<td width="140" height="30" align="left" class="postFormBox">
									<span class="name">考试批次：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<select name="exambatch_id">
										<option value="">
											---请选择---
										</option>
										<%
											String sql_t = "select id,name from pe_bzz_exambatch order by id";
											rs = db.executeQuery(sql_t);
											while (rs != null && rs.next()) {
												String id = fixnull(rs.getString("id"));
												String batch_name = fixnull(rs.getString("name"));
										%>
										<option value=<%=id%>><%=batch_name%></option>
										<%
											}
											db.close(rs);
										%>
									</select>
								</td>
							</tr>
							
							<tr>
								<td height="10">
								</td>
							</tr>
							
							<tr valign="middle">
								<td align="center">
									<input type="button" value="返回" onclick="history.back();">
								</td>
								<td align="center">
									<input type="submit" value="导出报表">
								</td>
							</tr>


							<tr>
								<td height="10">
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
