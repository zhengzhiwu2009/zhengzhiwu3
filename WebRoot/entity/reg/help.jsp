<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/manager/images/css_welcome.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="title">
			获得帮助
		</div>
		<div class="content">
			<table width="100%">
				<tr>
					<td class="red">
						<p>
							尊敬的学员：
						</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您可以拨打远程培训咨询服务热线010-62415115或发邮件至training@sac.net.cn获得帮助。
						</p>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>