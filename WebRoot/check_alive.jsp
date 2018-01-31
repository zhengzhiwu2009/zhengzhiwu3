<%@page language="java" %>
<%@page contentType="text/html;charset=UTF-8" %>
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<%
	dbpool db = new dbpool();
	int x = db.countselect("select 1 from dual");
	out.print(x);
	db=null;
%>