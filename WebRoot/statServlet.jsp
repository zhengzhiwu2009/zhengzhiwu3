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
		<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		function checkAndSubmit(){
			var act = document.getElementById("form1").action;
			act = act + document.getElementById("u").value;
			document.getElementById("form1").action = act;
			document.getElementById("form1").submit();
		}
		</script>
	</head>
	<body onload="checkAndSubmit()">
		<form action="/birt/zqyxh/" id="form1" method="post">
			<% 
				String u = request.getParameter("u");
				String organId = request.getParameter("organId");
			%>
			<input type="hidden" id="u" value="<%=u %>" />
			<input type="hidden" id="organId" value="<%=organId %>" />
		</form>
	</body>
</html>
