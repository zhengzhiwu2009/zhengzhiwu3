<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	session.removeAttribute("paperQuestions");
	String paperId = request.getParameter("paperId");
	String type1 = request.getParameter("type1");//这个就是添加试题页面的返回按钮。
	String policyId = "";
	
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
	TestManage testManage = interactionManage.creatTestManage();
	
	policyId = testManage.getPolicyIdByPaperId(paperId);
	
	HashMap paperQuestions = (HashMap) session.getAttribute("paperQuestions");
	
	if(paperQuestions!=null)
	{
%>
<script>
	alert("正在使用组卷策略进行组卷，请稍候在完成现有组卷操作后再重试！");
	window.history.back(-1);
</script>
<%
	}
	int count = 0;
	try	{
		if(policyId.equals("0"))
		{
			session.setAttribute("paperQuestions",null);
		}
		else
		{
			paperQuestions = testManage.getQuestionsByPaperPolicy(policyId);
			session.setAttribute("paperQuestions",paperQuestions);
		}
		count = 1;
	} catch(Exception e) { 
		out.print(e.getMessage());
		return;
	}

	if (count == 0) {
%>
<script>
	alert("抽取试题失败！");
	window.history.back(-1);
</script>
<%
	} else {
%>
<script>
	window.navigate("homeworkpaper_addexe_bypolicy_random.jsp?paperId=<%=paperId%>");
</script>
<%
	}
%>