<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page
	import="com.whaty.platform.sso.web.action.SsoConstant" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'deleteCourse.jsp' starting page</title>
    
    <%
    
    if(session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY) == null) {
		response.sendRedirect("/error/priv_error.htm");
		return ;
	}
	
    String id = request.getParameter("id");
    String code = request.getParameter("code");
    String sql = "delete from scorm_sco_launch where id='"+id+"'";
    dbpool db = new dbpool();
    if(db.executeUpdate(sql) > 0)
	{
		%>
		<script type="text/javascript">
			alert('删除成功');
			window.navigate("/entity/basic/onlineCourseMutiDisplay.action?code=<%=code%>");
		</script>
		<%
	} else {
		%>
		<script type="text/javascript">
			alert('删除失败');
			window.navigate("/entity/basic/onlineCourseMutiDisplay.action?code=<%=code%>");
		</script>
		<%
	}
    
     %>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    
  </body>
</html>
