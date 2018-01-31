<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ include file="../pub/priv.jsp"%>
<%@ include file="../pub/commonfuction.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课后测验试卷删除</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<%
	String pageInt = request.getParameter("pageInt");
	
	String title= fixnull(request.getParameter("title"));//java.net.URLDecoder.decode(fixnull(request.getParameter("titleVal")),"UTF-8");
	//System.out.println("titleVal:"+titleVal);
	
	String[] ids = request.getParameterValues("idaaa");
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
	if(!"".equals(ids)){
	try {
		System.out.println(ids[0]);
		int i = testManage.deleteSelectPaper(ids);
		if(i > 0) {
%>
<script type="text/javascript">
	alert("删除成功");
//	window.history.back();
	location.href = "testpaper_list.jsp?pageInt=<%=pageInt%>&title=<%=java.net.URLEncoder.encode(title,"UTF-8")%>";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("删除失败");
	window.history.back();
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
	}
	
%>
