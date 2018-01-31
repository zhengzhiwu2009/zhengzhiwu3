<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="com.whaty.platform.quartz.QuartzTrainingJob;"%>
<html>
<body>
<%
//格式 2012-01-01
QuartzTrainingJob qtj = new QuartzTrainingJob();
String d1 = (String)request.getAttribute("d1");
String d2 = (String)request.getAttribute("d2");
if(d1!=null && !"".equals(d1) && !"null".equals(d1)){
	qtj.setDate(d1);
}
if(d2!=null && !"".equals(d2) && !"null".equals(d2)){
	qtj.setDateCourse(d2);
}
qtj.doJob();
%>
</body>
</html>
