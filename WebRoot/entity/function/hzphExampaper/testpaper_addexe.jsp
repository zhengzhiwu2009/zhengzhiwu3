<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>

<%
	String testCourseId = (String) session.getValue("testCourseId");  
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		String title = request.getParameter("title");
		String time = request.getParameter("time");
		String note = request.getParameter("note");
		String status = request.getParameter("status");
		String types = request.getParameter("types");
		
		String paper_num = "";
		if(types!=null&&"1".equals(types)){
			paper_num =	request.getParameter("paper_num");
		}else{
			paper_num = request.getParameter("paper_num2");
		}
		//对试卷数目进一步判断
		if(paper_num==null||paper_num.equals("")){
			paper_num="1";
		}
		
		
		String creatUser = user.getName();
		
		
		//int paperId = testManage.addTestPaper(title,creatUser,status,note,time,teachclass_id);
		//int count = testManage.addTestPaperByOnlineTestCourse(Integer.toString(paperId),testCourseId);
		List<String> paperls = testManage.addTestPaper(title,creatUser,status,note,time,teachclass_id,paper_num ,types);
		String ss =paperls.toString();
		String paperIds = ss.substring(1,ss.length()-1);
					
		List<String> countsls = testManage.addTestPaperByOnlineTestCourse(paperls,testCourseId);
		
		//if(paperId>0 && types.equals("1")) {
		if(paperls!=null&&paperls.size()>0 && types.equals("1")) {
%>
<script type="text/javascript">
	alert("添加成功");	
	location.href = "testpaperpolicy_list.jsp?paperls=<%=paperIds%>";
</script>
<%
		}
		else if(paperls!=null&&paperls.size()>0 && types.equals("0")) {
%>
<script type="text/javascript">
	alert("添加成功");
	location.href = "testpaper_add_bypolicy.jsp?id=0&paperId=<%=paperIds%>";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("添加失败,请检查试卷信息,同一课程下试卷名称不能重复!");
	window.history.back(-1);
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>