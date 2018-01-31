<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
  				alert("您选择的考点容量已满，请重新选择。。。。。。。");
  				window.location="/entity/workspaceStudent/bzzstudent_examSiteExplain.action";
  	  </script>
	</head>
	<body>
	</body>
</html>
