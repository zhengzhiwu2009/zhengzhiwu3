<%@ page language="java" import="com.whaty.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.entity.bean.PeEnterpriseManager"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	PeEnterpriseManager manager = (PeEnterpriseManager)request.getAttribute("manager");
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
				设置管理员成功
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<table width="554" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="26" align="center" valign="middle" colspan="2">
								管理员信息：
							</td>
						</tr>
						

						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">管理员姓名：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=manager.getName() %>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">管理员性别：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=manager.getEnumConstByGender().getName() %>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">管理员移动电话：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=manager.getMobilePhone() %>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">所在企业：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=manager.getPeEnterprise().getName() %>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">账号：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=manager.getLoginId() %>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">密码：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=manager.getSsoUser().getPassword() %>
							</td>
						</tr>
						
						
						<tr>
							<td height="26" align="center" valign="middle" colspan="2">
								<button onclick="window.navigate('/entity/basic/peEnterprise.action');">完成</button>
							</td>
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
