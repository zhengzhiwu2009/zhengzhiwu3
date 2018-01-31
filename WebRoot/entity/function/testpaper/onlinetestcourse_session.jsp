<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*"%>
<%@ page import="com.whaty.util.Cookie.*,com.whaty.platform.test.TestManage"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>

<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	String testCourseId = request.getParameter("testCourseId");
	String openCourseId = request.getParameter("pbtc");
	Boolean be = false;
	if(!"teacher".equalsIgnoreCase(userType)){
	String sql1 = "SELECT 1 FROM PR_VOTE_RECORD WHERE SSOID IN ('" + us.getId() + "') AND FK_VOTE_PAPER_ID IN (SELECT ID FROM PE_VOTE_PAPER WHERE COURSEID = '" + openCourseId + "')";
	String sql2 = "SELECT 1 FROM TRAINING_COURSE_STUDENT WHERE LEARN_STATUS = 'COMPLETED' AND STUDENT_ID = '" + us.getId() + "' AND COURSE_ID IN (SELECT ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_COURSE_ID = '" + openCourseId
			+ "')";
	dbpool db = new dbpool();
	if(db.countselect(sql1) < 1 || db.countselect(sql2) < 1)
		be = true;
	}
	if (be) {
		%>
<script type="text/javascript">
		<!--
			alert("请确认学习完成并填写了满意度调查！");
			window.close();
		//-->
		</script>
<%
	}else{
	String isHiddenAnswer = request.getParameter("isHiddenAnswer");
	String isAutoCheck = request.getParameter("isAutoCheck");
	String pageInt = request.getParameter("pageInt");
	session.setAttribute("testCourseId", testCourseId);
	session.setAttribute("isHiddenAnswer", isHiddenAnswer);
	session.setAttribute("isAutoCheck", isAutoCheck);
%>
<script>
	<%if("teacher".equalsIgnoreCase(userType)){%>
		location.href = "testpaper_list.jsp?pageInt=<%=pageInt%>";
	<%}else{%>
		location.href = "testpaper_gotest.jsp";
		//window.open('testpaper_gotest.jsp');
		//history.go(-1);
	<%}
	}%>
</script>