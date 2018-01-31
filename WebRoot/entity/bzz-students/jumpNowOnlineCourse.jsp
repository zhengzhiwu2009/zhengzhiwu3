<%@ page language="java" import="java.util.*,com.whaty.platform.sso.web.action.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.util.OnlineCourseCounter;"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'jumpNowOnlineCourse.jsp' starting page</title>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<% 
		String url = "/web/bzz_index/zhibo/index.jsp";
		
		String loginUrl = "/web/bzz_index/zhibo/login/login.jsp";
		if(session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY) == null) {
		%>
			<script type="text/javascript">
				alert("您尚未登陆，请先登陆!");
				window.navigate("<%= loginUrl%>");
			</script>		
		<%
			return;
		}
		//OnlineCourseCounter.increaseOnlineCourseSessions();
		response.sendRedirect(url);
	%>

  </head>
  
  <body>
  </body>
</html>
