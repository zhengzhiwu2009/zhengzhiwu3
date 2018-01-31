<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
  				alert("取消报名成功！");
  				window.parent.middle.location.reload();
  				window.location="/entity/workspaceStudent/bzzstudent_examBat.action";
  	  </script>
	</head>
	<body>
	</body>
</html>