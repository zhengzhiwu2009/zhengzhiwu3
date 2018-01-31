<%@ page contentType="text/html;charset=utf-8" %>
<%@page import="java.io.*"%>
<%@page import="java.lang.*"%>
<%@page import="com.oreilly.servlet.*"%>
<%@ page language="java" import="javazoom.upload.*,java.util.*" %>
<jsp:useBean id="file_work" scope="page" class="com.whaty.filemanager.filemanager"/>
<%  

//=====================================================
//   



      String saveDirectory =(String)session.getValue("s_current_path");


//      每个文件最大5m,最多3个文件,所以...
      int maxPostSize =1024 * 1024 * 1024;
//      response的编码为"gb2312",同时采用相应的命名策略（我用了自己的实现方法）冲突解决策略,实现上传
      MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize,"utf-8");
//=====================================================
%>
<html>
<META HTTP-EQUIV="Refresh" CONTENT="0.1;URL=filemanager_main.jsp?dir=.">
<body>
<a href="filemanager_main.jsp?dir=.">返回</a>
</body>
</html>

