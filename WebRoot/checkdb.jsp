<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="com.whaty.platform.database.oracle.dbconn"%>
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<html>
<meta http-equiv= "refresh" content= "3">
<body>
<%
	dbpool db = new dbpool();
	out.println("共有"+db.threadConnectionMap.size()+"个连接没有释放") ;
	for(Object o : db.threadConnectionMap.keySet()) {
		dbconn dbcon = (dbconn)db.threadConnectionMap.get(o);
		out.print("date"+dbcon.getDate()+",sql:"+dbcon.getSql());
		out.print("<br/>");
	}
%>
</body>
</html>
