<%
	/*---------------------------------------------
	功能描述:添加新目录
	编写时间:2004-8-28
	编写人:陈健
	email:陈健@whaty.com
	
	修改情况记录
	修改时间：  修改内容：   修改人：
	-----------------------------------------------*/
%>
<%@page import="java.io.*,com.whaty.*"%>
<%@page import="java.lang.*"%>
<%@page import="com.whaty.encrypt.*" %>
<%!String s_newdir;
%>

<jsp:useBean id="file_work" scope="page" class="com.whaty.filemanager.filemanager"/>
<%s_newdir=request.getParameter("newdir");
  String s_current_path=(String)session.getValue("s_current_path");
  file_work.mkdirFile(appbox.convertEncoding("ISO8859-1","GB2312",s_newdir.trim()),s_current_path);
  %>
<html>
<META HTTP-EQUIV="Refresh" CONTENT="0.1;URL=filemanager_main02.jsp?dir=.">
<body>
<a href="filemanager_main.jsp?dir=.">back</a>
</body>
</html>
  

