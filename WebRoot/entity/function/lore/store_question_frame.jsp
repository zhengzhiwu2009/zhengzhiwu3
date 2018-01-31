<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<link href="css/style.css" rel="stylesheet" type="text/css">
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<title>中国证券业协会</title>
</head>
<%
	String loreId = request.getParameter("loreId");
	String loreDirId = request.getParameter("loreDirId");
%>
<FRAMESET border=0 frameSpacing=0 rows=50,* frameBorder=0>
  <frame name="top" scrolling="no" noresize src="store_question_add.jsp?loreId=<%=loreId%>&loreDirId=<%=loreDirId%>">
  <frame name="ctcontent" scrolling="auto"  src="store_question_single.jsp?loreId=<%=loreId%>&loreDirId=<%=loreDirId%>&type=danxuan">
  <noframes>
  <body>

  <p>此网页使用了框架，但您的浏览器不支持框架。</p>

  </body>
  </noframes>
</frameset>

</html>