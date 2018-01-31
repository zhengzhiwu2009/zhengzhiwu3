<%@ page language="java" import="java.util.*"%>
<%
	String token = new Date().getTime()+"";
	session.setAttribute("formToken", token);
%>
<input type="hidden" name="formToken" id="formToken" value="<%=token %>" />