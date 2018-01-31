<%
	/*---------------------------------------------
	功能描述:解压缩
	编写时间:2004-8-29
	编写人:陈健
	email:陈健@whaty.com
	
	修改情况记录
	修改时间：  修改内容：   修改人：
	-----------------------------------------------*/
%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@page import="java.io.*"%>
<%@page import="java.lang.*"%>
<jsp:useBean id="file_work" scope="page" class="com.whaty.filemanager.filemanager"/>
<jsp:useBean id="file_zip" scope="page" class="com.whaty.filemanager.JavaUnzipAndZip"/>
<%  

try{
  String filename=request.getParameter("file");
  out.print(filename);
  String current_path=(String)session.getValue("s_current_path");
  out.print(current_path);
  String result=file_zip.Unzip(current_path,filename);
  out.print(result);
  }
  catch(Exception e) 
  {
  out.print("解压过程中出现错误："+e);
  }
  
%>
<html>
<META HTTP-EQUIV="Refresh" CONTENT="100;URL=filemanager_main02.jsp?dir=.">
<body>
<a href="filemanager_main02.jsp?dir=.">back</a>
</body>
</html>

