<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'tokenError.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<table width="70%"  border="0" align="center" cellpadding="10" cellspacing="0">
  <tr>
    <td bgcolor="#A4A4A4"><table width="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td bgcolor="#FFFFFF"><table width="100%"  border="0" cellpadding="15" cellspacing="1">
          <tr>
            <td bgcolor="#E1E1E1"><div align="center">请不要重复提交表单 谢谢！<a href='javascript:history.back();'>返回</a></div></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
  </body>
</html>
