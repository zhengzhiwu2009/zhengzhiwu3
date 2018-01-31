<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@page import="com.whaty.platform.quartz.QuartzMoveStudent"%>
<%@ page import="java.util.*" %>
<% response.setHeader("expires", "0"); %>

<%
	QuartzMoveStudent qd = new QuartzMoveStudent();

	qd.execute();

%>
123123
