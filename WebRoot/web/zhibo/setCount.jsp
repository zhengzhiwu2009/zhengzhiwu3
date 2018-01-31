<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.whaty.util.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'setCount.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%
	if(session.getAttribute("user_session")!=null) {
		String psnNum = request.getParameter("psnNum");
		if(psnNum != null && psnNum.equals("")) {
			int num = Integer.parseInt(psnNum);
			if(num >= 0) {
				OnlineCourseCounter.setActiveSessions(num);
			}
		}
	}
	 %>

  </head>
  
  <body>
    <form action="setCount.jsp">
    设置人数：<input name="psnNum" value="<%= OnlineCourseCounter.getActiveSessions()%>"><input type="submit" value="设置">
    </form>
  </body>
</html>
