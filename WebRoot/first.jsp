<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	response.setHeader("expires", "0"); 
	response.sendRedirect("/cms/");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>中国证券业协会远程培训平台</title>
<style type="text/css">
     frameset {border:0px;}
     frame {border:0px;}
 </style> 

</head>
<frameset rows="*,0" frameborder="0" border="0" framespacing="0" >
	<frame src="/cms/" name="cccontent" scrolling="auto" noresize>
</frameset>
<noframes></noframes>
<body>
</body>
</html>
