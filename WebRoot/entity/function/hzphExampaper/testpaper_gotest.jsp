<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*,com.whaty.platform.test.history.TestPaperHistory"%>
<%@ include file="../pub/priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>中国证券业协会</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<%
	String mock_login = (String)session.getAttribute("mock_login");
%>
<%!
String fixnull(String str)
{
    if(str == null || str.equals("") || str.equals("null"))
		str = "";
	return str;
}
%>	
<%
	String title = fixnull(request.getParameter("title"));
	session.removeAttribute("historyPaperId");
	
	//try {
		String testCourseId = (String) session.getValue("testCourseId");  
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		String link = "&title="+title;
		//----------分页结束---------------
		List testPaperList = testManage.getTestPapersByOnlineTestCourse(null,testCourseId);
		
		//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		String paperId = "";
		
		if(testPaperList!=null && testPaperList.size()>0){
			int ran = 0;
			if(testPaperList.size()!=1){
				ran = new Random().nextInt(testPaperList.size());		
			}
			TestPaper testPaper = (TestPaper)testPaperList.get(ran);
			paperId = testPaper.getId();//得到 开始自测 的ID。
%>
		<script type="text/javascript">
			//alert('<%=testPaperList.size() %>   <%=paperId %>  <%=ran %>');
			//window.open('testpaper_pre.jsp?id=<%=paperId%>','zc','resizable,width=800,height=600');
			window.location = 'testpaper_pre.jsp?id=<%=paperId%>';
		</script>
<%
		}else{
%>
		<script type="text/javascript">
			alert("当前测验没有可用试卷！");
			window.close();
		</script>
<%
		}
%>
