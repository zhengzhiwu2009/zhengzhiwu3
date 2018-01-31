<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<link href="css/style.css" rel="stylesheet" type="text/css">
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<title>中国证券业协会远程培训平台</title>
</head>
<html>
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
	String id = request.getParameter("id");
	String pageInt = request.getParameter("pageInt");
	long now = new Date().getTime();
 %>
<frameset rows="60,*" cols="*" frameborder=0 border="0" framespacing="0">
  <frame src="testpaper_top.jsp?id=<%=id%>&pageInt=<%=pageInt %>&x=<%=now %>" name="topFrame" scrolling="NO" frameborder="0" marginwidth="0" marginheight="0" noresize >
  <frameset rows="*" cols="0,0,*" framespacing="0" frameborder="0" border="0" marginwidth="0" marginheight="0" name="main_set" id="main_set">
    <frame src="testpaper_left.jsp?id=<%=id%>&pageInt=<%=pageInt %>&x=<%=now %>" scrolling="auto" name=rightmarin" frameborder="0" marginwidth="0" marginheight="0"  noresize>
    <frame src="" scrolling="no" name="right">
	<frame src="testpaper_main.jsp?&x=<%=now %>" scrolling="auto" name="submain" frameborder="0" marginwidth="0" marginheight="0"  noresize>
  </frameset>
  <noframes>
  <body>
  <p>此网页使用了框架，但您的浏览器不支持框架。</p>
  </body>
  </noframes>
</frameset>

</html>