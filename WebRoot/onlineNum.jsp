<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="com.whaty.platform.database.oracle.dbconn"%>
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<%@page import ="com.whaty.util.SessionCounter" %>
<html>
<meta http-equiv= "refresh" content= "10">
<body>
<%
	
	SessionCounter sc = new SessionCounter();
	int i = sc.getOnlineNum();
	out.println("当前在线人数:"+i);

%>


</body>
</html>
