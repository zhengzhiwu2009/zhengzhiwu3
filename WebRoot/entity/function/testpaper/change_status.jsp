<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
			request.setCharacterEncoding("UTF-8");
		String paperId = request.getParameter("paperId");
		String title = request.getParameter("title");
		String pageInt = request.getParameter("pageInt");
		String status = request.getParameter("status");
		//String note = new String(request.getParameter("note").getBytes("iso8859-1"), "utf-8");
		String note=request.getParameter("note");
		String note_bak = note;
		if(note!=null&&note.equals("0")){
		note="无效";
		}
		if(note!=null&&note.equals("1")){
		note="有效";
		}
		// 20130219蔡磊添加，用于设为试卷无效前校验
		 //当flag为false时，有考试记录，那么试卷不能被设为无效
		 //
		Boolean flag = true;
		if(note_bak!=null&&note_bak.equals("0")){
			flag = testManage.deleteJudge(paperId);   //查找试卷是否有对应的考试记录
		} 
		int i = testManage.changeTestPaperStatus(paperId,status);
		if(i>0) {
%>
<script type="text/javascript">
		alert("设置为<%=note%>成功!");
		location.href = "testpaper_list.jsp?pageInt=<%=pageInt%>&title=<%=title%>";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("设置为<%=note%>失败!");
	window.history.back(-1);
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>