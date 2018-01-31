<%@ page language="java"  pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=GBK"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<%
	String value = request.getParameter("scoID");
	if(value == null || "".equals(value) || "null".equals(value)){
		value = "";
	}
	session.setAttribute("SCOID",value);
	%>