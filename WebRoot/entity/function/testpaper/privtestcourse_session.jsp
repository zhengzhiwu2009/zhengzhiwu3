<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*"%>
<%@ page import="com.whaty.util.Cookie.*,com.whaty.platform.test.TestManage"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>

<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	String testCourseId = request.getParameter("testCourseId");
	//String openCourseId = request.getParameter("pbtc");
		%>

<%
	String isHiddenAnswer = request.getParameter("isHiddenAnswer");
	String isAutoCheck = request.getParameter("isAutoCheck");
	String pageInt = request.getParameter("pageInt");
	session.setAttribute("testCourseId", testCourseId);
	session.setAttribute("isHiddenAnswer", isHiddenAnswer);
	session.setAttribute("isAutoCheck", isAutoCheck);
%>
<script>
		location.href = "testpaper_gotest.jsp";
</script>