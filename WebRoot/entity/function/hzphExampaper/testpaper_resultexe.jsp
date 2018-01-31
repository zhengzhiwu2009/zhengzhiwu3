<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.history.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<jsp:directive.page import="com.whaty.platform.Exception.PlatformException"/>
<%@ include file="../pub/priv.jsp"%>
<%
	
	try {
		String id = request.getParameter("id");
		String totalScore = request.getParameter("totalScore");
		String openDate = request.getParameter("openDate");
		String isSub = request.getParameter("isSub");
		HashMap userAnswer = (HashMap)session.getAttribute("UserAnswer");
		HashMap standardAnswer = (HashMap)session.getAttribute("StandardAnswer");
		HashMap Title = (HashMap)session.getAttribute("Title");
		HashMap Type = (HashMap)session.getAttribute("Type");
		HashMap standardScore = (HashMap)session.getAttribute("Score");
		HashMap userScore = (HashMap)session.getAttribute("UserScore");
		HashMap map = new HashMap();
		map.put("idList",session.getAttribute("idList"));
		session.removeAttribute("idList");
		map.put("userAnswer",userAnswer);
		map.put("standardAnswer",standardAnswer);
		map.put("title",Title);
		map.put("type",Type);
		map.put("standardScore",standardScore);
		if(((String)session.getAttribute("isAutoCheck")).equals("1"))
		{
			map.put("userScore",userScore);
			map.put("totalScore",totalScore);
		}
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		//BasicScoreManage basicScoreManage = interactionManage.createBasicScoreManage();
		//BasicActivityManage basicActivityManage = interactionManage.createBasicActivityManage();
		TestManage testManage = interactionManage.creatTestManage();
		
		ArrayList scoreList = new ArrayList();
		scoreList.add(totalScore);
		ArrayList idList = new ArrayList();
		idList.add(user.getId());
		
		String user_id = "("+user.getId()+")"+user.getName();
		int count = 0;
		try{
		//try{
	//	String openCourseId = (String)session.getAttribute("openCourseId");
		
		//basicScoreManage.updateScoreBatch(null,null,null,null,null,scoreList,null, idList, null,null,openCourseId);
		//}
		//catch(PlatformException e)
		//{
		//}
		}catch(Exception e)
		{
			count=-1;
		}
		if(count!=-1)
			count=testManage.addExamTestPaperHistory(user_id,id,totalScore,map,openDate ,isSub);
			testManage.saveLoginfo("在线考试",user_id,"exam_management","学员端","","127.0.0.1");
	if(count>0) {
%>
<script type="text/javascript">
	
	if(opener !=null && opener.location != null){
		window.opener.location.reload();
	}
//	window.opener.location.reload();
	window.close();
	//location.href = "testpaper_list.jsp";
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