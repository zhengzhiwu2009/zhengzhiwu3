<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@page import="com.whaty.platform.quartz.QuartzMoveStudent,com.whaty.platform.quartz.QuartzMoveSite"%>
<%@ page import="java.util.*" %>
<% response.setHeader("expires", "0"); %>

<%
	QuartzMoveStudent qd = new QuartzMoveStudent();

	//QuartzMoveSite qs = new QuartzMoveSite();

	qd.execute();
	//qs.execute();
%>
执行完毕！