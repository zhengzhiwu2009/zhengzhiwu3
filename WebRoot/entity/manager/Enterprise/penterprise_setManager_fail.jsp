<%@ page language="java" import="com.whaty.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

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
				设置管理员失败
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<table width="554" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="26" align="center" valign="middle" colspan="2">
								管理员设置失败：
							</td>
						</tr>
						

						<tr>
							<td height="26" align="center" valign="middle" colspan="2">
								<font color="red"><%=request.getAttribute("msg") %></font>
							</td>
						</tr>
						
						<tr>
							<td height="26" align="center" valign="middle" colspan="2">
								<button onclick="history.back();">返回</button>
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
