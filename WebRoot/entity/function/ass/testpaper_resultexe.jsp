<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.history.*"%>
<%@ page import="com.whaty.platform.test.TestManage"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.entity.bean.SsoUser"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<jsp:directive.page import="com.whaty.platform.Exception.PlatformException" />
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.whaty.platform.entity.service.ass.PeBzzAssService" %>
<%@ include file="priv.jsp"%>
<%
	try {
		String id = request.getParameter("id");
		String totalScore = request.getParameter("totalScore");
		HashMap userAnswer = (HashMap) session.getAttribute("UserAnswer");
		HashMap standardAnswer = (HashMap) session.getAttribute("StandardAnswer");
		HashMap Title = (HashMap) session.getAttribute("Title");
		HashMap Type = (HashMap) session.getAttribute("Type");
		HashMap standardScore = (HashMap) session.getAttribute("Score");
		HashMap userScore = (HashMap) session.getAttribute("UserScore");
		HashMap map = new HashMap();
		map.put("idList", session.getAttribute("idList"));
		session.removeAttribute("idList");
		map.put("userAnswer", userAnswer);
		map.put("standardAnswer", standardAnswer);
		map.put("title", Title);
		map.put("type", Type);
		map.put("standardScore", standardScore);
		if (((String) session.getAttribute("isAutoCheck")).equals("1")) {
			map.put("userScore", userScore);
			map.put("totalScore", totalScore);
		}
		//InteractionFactory interactionFactory = InteractionFactory.getInstance();
		//InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
		//BasicScoreManage basicScoreManage = interactionManage.createBasicScoreManage();
		//BasicActivityManage basicActivityManage = interactionManage.createBasicActivityManage();
		//TestManage testManage = interactionManage.creatTestManage();
		PeBzzAssService peBzzAssService = new PeBzzAssService();

		ArrayList scoreList = new ArrayList();
		scoreList.add(totalScore);
		ArrayList idList = new ArrayList();
		idList.add(user.getId());

		String user_id = "(" + user.getId() + ")" + user.getName();
		int count = 0;
		if (count != -1)
			count = peBzzAssService.addTestPaperHistory(us.getSsoUser().getId(), id, totalScore, map);
		
		if (count > 0) {
%>
			<script type="text/javascript">
				alert("答卷结果保存成功!");
				//查看考试结果
				location.href = "testhistory_info2.jsp?tsId=<%=id%>";
			</script>
<%
	} else {
%>
			<script type="text/javascript">
				alert("答卷结果保存失败!");
				if(opener !=null && opener.location != null){
					window.opener.location.reload();
				}
				window.close();
				//window.history.back(-2);
			</script>
<%
		}
	} catch (Exception e) {
		out.print(e.toString());
		//e.printStackTrace();
	}
	session.removeAttribute("UserAnswer");
	session.removeAttribute("StandardAnswer");
	session.removeAttribute("Title");
	session.removeAttribute("Type");
	session.removeAttribute("Score");
%>
考试已经结束，请关闭当前页面。
