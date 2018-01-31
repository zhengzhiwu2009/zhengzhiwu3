<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'accessError.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<% 
		String errorMessage = (String)request.getAttribute("errorMessage"); 
		if(errorMessage == null) {
			errorMessage = "出错了!";
		}
	%>
	<script type="text/javascript">
		alert("<%=errorMessage%>");
		window.close();
	</script>
  </head>
  
  <body>
  </body>
</html>
