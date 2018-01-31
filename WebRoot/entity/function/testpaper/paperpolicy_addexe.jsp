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
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		String title=request.getParameter("title").trim();
		String groupId=	request.getParameter("teachclass_id");
		int checkName=testManage.getPaperPolicysNum(null,title,null,null,null,null,null,groupId);
		
		
		if(checkName>0){
			%>
<script type="text/javascript">
	alert("已存在相同名称组卷策略!请更换策略名称")
	window.history.back();
</script>
			
		<%}else{
			
		int id = testManage.addPaperPolicy(request,session);
		if(id > 0) {
%>
<script type="text/javascript">
	alert("添加成功!")
	location.href = "testpaperpolicy_list.jsp?type=list";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("添加失败!");
	window.history.back();
</script>
<%		
		}
	  }
	} catch (Exception e) {
		e.printStackTrace();
		out.print(e.toString());
	}
%>