<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.util.OnlineCourseCounter;"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'closeWindow.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<% 
		
		//session.removeAttribute("OnlineCourseSessionsIncreased");
		if(session.getAttribute("OnlineCourseSessionsIncreased") != null) {
			session.setAttribute("OnlineCourseSessionsIncreased",null);
			OnlineCourseCounter.decreaseOnlineCourseSessions();
			//System.out.println("--");
		}
	%>
	<script type="text/javascript">
		window.close();
	</script>
  </head>
  
  <body>
    
  </body>
</html>
