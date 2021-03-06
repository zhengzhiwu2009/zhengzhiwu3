<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage,com.whaty.util.string.encode.*" %>
<%@ page import="com.whaty.util.string.*"%>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点题库--填空题修改</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<%
	String id = request.getParameter("id");
	String loreId = request.getParameter("loreId");
	String loreDirId = request.getParameter("loreDirId");
	
	String diff = request.getParameter("diff");
	String cognizetype = request.getParameter("cognizetype");
	String referencetime = request.getParameter("referencetime");
	String referencescore = request.getParameter("referencescore");
	String title = request.getParameter("title");
	String[] purposeVal = request.getParameterValues("purpose");
	String type = request.getParameter("type");
	WhatyStrManage str = new WhatyStrManage(request.getParameter("body"));
	String questioncore = str.htmlEncode();
	
	String answer = request.getParameter("answer");
	String studentnote = request.getParameter("studentnote");
	String teachernote = request.getParameter("teachernote");
	
	String purpose = "";
	for(int i=0; i<purposeVal.length; i++)
		purpose = purpose + purposeVal[i] + "|";
	if(purpose.length() > 0)
		purpose = purpose.substring(0, purpose.length()-1);
	
	String xml = "<question><body>" + HtmlEncoder.encode(questioncore) + "</body><answer>" + HtmlEncoder.encode(answer) + "</answer></question>";
	
	//out.print(questioncore);
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		String creatuser = user.getName();
//		String creatdate = DateFormat.getDateTimeInstance().format(new Date());
//		creatdate = creatdate.substring(0, creatdate.indexOf(" "));
	 java.util.Date sDate = new java.util.Date();
	    	 SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    	 String creatdate=sDateFormat.format(sDate);	
		int i = testManage.updateStoreQuestion(id, title, creatuser, creatdate, diff, "keyword", xml,
			loreId, cognizetype, purpose, referencescore, referencetime, studentnote, teachernote, type);
		if(i > 0) {
%>
<script type="text/javascript">
	alert("修改成功");
	location.href = "lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("修改失败");
	window.history.back();
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>