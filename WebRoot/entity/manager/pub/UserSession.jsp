<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>

<%
	UserSession userSession = (UserSession)session.getAttribute("user_session");

	if (userSession == null) {
%>
		<script>
			window.top.alert("长时间未操作或操作异常，为了您的账号安全，请重新登录！");
			window.top.location = "/"; 
		</script>
<%
		return;
	}
%>
