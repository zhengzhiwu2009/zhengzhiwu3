<%@ page language="java"
	import="java.util.*,com.whaty.platform.database.oracle.*"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%!String fixnull(String str) {
		if (str == null)
			str = "";
		return str;
	}%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台-在线直播</title>
		<link href="/web/bzz_index/zhibo/login/style_login.css"
			rel="stylesheet" type="text/css" />
		<script type="text/JavaScript">
	function checkForm() {
		var loginid = document.getElementById("loginId");
		var pwd = document.getElementById("passwd");
		
		if(loginid.value == null || loginid.value.length == 0) {
			alert("请输入用户名!");
			return false;
		} 
		
		if(pwd.value == null || pwd.value.length == 0) {
			alert("请输入密码!");
			return false;
		} 
		
		return true;
	}
	
	function cancelForm() {
		window.close();
	}
</script>

		<%
			String sql = "select * from pe_bzz_onlinecourse e where e.start_date <= sysdate and e.end_date >= sysdate";
			dbpool db = new dbpool();
			int n = db.countselect(sql);
			if (n == 0) {
		%>
		<script type="text/javascript">
		alert("目前没有正在直播的讲座，请观看点播。");
		window.close();
	</script>
		<%
			return;
			}
			String loginid = fixnull((String) request.getAttribute("loginId"));
			String errMessage = (String) request
					.getAttribute("loginErrMessage");
			if (errMessage != null) {
		%>
		<script type="text/JavaScript">
	alert("<%=errMessage%>");
</script>
		<%
			}
		%>
	</head>
	<body>
		<table width="912" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td>
					<img src="/web/bzz_index/zhibo/login/images/login_01.jpg"
						width="912" height="139" />
				</td>
			</tr>
			<tr>
				<td>
					<img src="/web/bzz_index/zhibo/login/images/login_02.jpg" />
				</td>
			</tr>
		</table>
		<table width="912" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td width="440">
					<table width="440" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="2">
								<img src="/web/bzz_index/zhibo/login/images/login_03.jpg"
									width="440" height="311" />
							</td>
						</tr>
						<tr>
							<td width="296">
								<img src="/web/bzz_index/zhibo/login/images/login_07.jpg"
									width="296" height="44" />
							</td>
							<td width="144">
								<a href="/web/bzz_index/zxkt/zxkt.jsp" target="_blank"><img
										src="/web/bzz_index/zhibo/login/images/zxkt_on.jpg" border="0"
										onMouseOver="this.src='/web/bzz_index/zhibo/login/images/zxkt_on.jpg'"
										onMouseOut="this.src='/web/bzz_index/zhibo/login/images/zxkt_off.jpg'" />
								</a>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<img src="/web/bzz_index/zhibo/login/images/login_09.jpg" />
							</td>
						</tr>
					</table>
				</td>
				<td valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="2">
								<img src="/web/bzz_index/zhibo/login/images/login_04.jpg" />
							</td>
						</tr>
						<tr>
							<td width="392" height="231" class="login_bg">
								<form action="/sso/login_onlineCourseLogin.action" method="post"
									id="loginForm" name="loginForm" onsubmit="return checkForm();">
									<table width="260" border="0" cellspacing="0" cellpadding="0"
										align="center">
										<tr>
											<td width="74" style="padding-bottom: 45px;">
												用户名：
											</td>
											<td width="186" style="padding-bottom: 45px;">
												<input id="loginId" name="loginId" type="text"
													value="<%=loginid%>" class="login_input" />
											</td>
										</tr>
										<tr>
											<td>
												密 码：
											</td>
											<td>
												<input id="passwd" name="passwd" type="password"
													class="login_input" />
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<table width="260" border="0" cellspacing="0"
													cellpadding="0" style="margin-top: 50px;">
													<tr>
														<td width="130" align="center">
															<a href="#"><input type="image"
																	src="/web/bzz_index/zhibo/login/images/login_off.jpg"
																	onMouseOver="this.src='/web/bzz_index/zhibo/login/images/login_on.jpg'"
																	onMouseOut="this.src='/web/bzz_index/zhibo/login/images/login_off.jpg'" />
															</a>
														</td>
														<td width="130" align="center">
															<a href="#"><img
																	src="/web/bzz_index/zhibo/login/images/qx_off.jpg"
																	onclick="cancelForm()"
																	onMouseOver="this.src='/web/bzz_index/zhibo/login/images/qx_on.jpg'"
																	onMouseOut="this.src='/web/bzz_index/zhibo/login/images/qx_off.jpg'" />
															</a>
														</td>
													</tr>
												</table>

											</td>
										</tr>
									</table>
								</form>
							</td>
							<td width="80">
								<img src="/web/bzz_index/zhibo/login/images/login_06.jpg"
									width="80" height="231" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="912" border="0" cellspacing="0" cellpadding="0"
			align="center" style="border-top: 1px solid #c1c1c1">
			<tr>
				<td class="footer">
					版权所有：中国证劵业协会
					<br />
					投诉：010-11110000 传真：010-11116666 咨询服务电话：010-62415115
				</td>
			</tr>
		</table>

	</body>
</html>