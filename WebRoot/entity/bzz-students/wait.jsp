<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
  				alert("您的姓名修改未审核，请等待。。。。。。。");
  				window.location="/entity/workspaceStudent/bzzstudent_examBatch.action";
  	  </script>
	</head>
	<body>
	</body>
</html>
