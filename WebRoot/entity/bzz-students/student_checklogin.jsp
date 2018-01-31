<%@ page language="java"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool" />
<jsp:directive.page
	import="com.whaty.platform.database.oracle.MyResultSet" />
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page import="java.util.*,java.text.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>中国证劵业协会培训平台</title>
		<link href="/web/bzz_index/style/index.css" rel="stylesheet"
			type="text/css">
		<link href="/web/bzz_index/style/xytd.css" rel="stylesheet"
			type="text/css">
		<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
		<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
		<script language="javascript">
	function check()
	{
		if(document.findpwd.stuName.value=="")
		{
			alert("请输入姓名！");
			document.findpwd.stuName.focus();
			return false;
		}
		if(document.findpwd.stuMobile.value=="")
		{
			alert("手机号码不能为空！");
			document.findpwd.stuMobile.focus();
			return false;
		}
		return true;
	}
</script>


		<style>
.font_czmm {
	font-size: 12px;
	color: #0A0A64;
	padding: 0px 0px 0px 10px;
	line-height: 30px;
}

input {
	border: solid 1px #1E61C9;
	width: 150px;
	height: 20px;
	margin: 0px 0px 0px 10px;
}

select {
	border: solid 1px #1E61C9;
	width: 180px;
	height: 20px;
	margin: 0px 0px 0px 10px;
	font-size: 12px;
	color: #000000;
}
</style>
	</head>

	<body>
		<table width="993" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td height="153">
					<img src="/web/bzz_index/images/top_02.jpg" width="993"
						height="153">
				</td>
			</tr>
			<tr>
				<td align="center"
					style="background-image: url(/web/bzz_index/czmm/images/czmm_05.jpg); background-position: bottom; background-repeat: repeat-x; padding: 30px 0px 30px 0px;">
					<table width="367" height="150" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="40" align="center">
								报名信息验证
							</td>
						</tr>
						<form name="findpwd" action="/sso/login_checkRecruitInfo.action"
							method="post" onsubmit="return check();">
						<tr>
							<td>
								<table width="100%" border="3" cellpadding="0" cellspacing="1"
									bordercolor="#CCE2FF" bgcolor="#FFFFFF">

									<tr>
										<td bgcolor="#A1CBFF" class="font_czmm">
											姓 名：
										</td>
										<td>
											<input type="text" name="stuName">
										</td>
									</tr>
									<tr>
										<td bgcolor="#A1CBFF" class="font_czmm">
											手机号码：
										</td>
										<td>
											<input type="text" name="stuMobile">
										</td>
									</tr>
									<tr>
								</table>
							</td>
						</tr>
						</form>

						<tr>
							<td>
								<br/>
								说明：请填写报名时姓名和手机号码。
							</td>
						</tr>
						<tr>
							<td height="50" align="center">
								<a href="javascript:window.document.findpwd.submit();"><img
										src="/web/bzz_index/czmm/images/czmm_12.jpg" width="71"
										height="23" border="0"> </a><a
									href="javascript:window.document.findpwd.reset();"><img
										src="/web/bzz_index/czmm/images/czmm_14.jpg" width="71"
										height="23" hspace="10" border="0"> </a><a
									href="javascript:window.close();"><img
										src="/web/bzz_index/czmm/images/czmm_16.jpg" width="71"
										height="23" border="0"> </a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="54" align="center" valign="middle"
					background="web/bzz_index/images/bottom_08.jpg">
					<span class="down">版权所有：中国证劵业协会 <BR> 投诉：010-11110000
						传真：010-11116666 咨询服务电话：010-62415115</span>
				</td>
			</tr>
		</table>


	</body>
</html>

