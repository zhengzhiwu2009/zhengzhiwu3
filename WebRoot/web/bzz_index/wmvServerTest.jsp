<%@ page language="java" import="java.util.*,java.io.*,java.net.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'wmvServerTest.jsp' starting page</title>
    
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
<%
URL url = null;
out.println("开始测试<br/>");
try {
	url = new URL("http://1.tel.webtrn.cn:8080/whaty/038/1.wmv");
	InputStream in = url.openStream();
	out.println("连接正常：" + url.toString()+"<br/>");
	in.close();
	} catch (IOException e) {
	out.println("无法连接到：" + url.toString()+"<br/>");
}
try {
	url = new URL("http://2.tel.webtrn.cn:8080/whaty/038/1.wmv");
	InputStream in = url.openStream();
	out.println("连接正常：" + url.toString()+"<br/>");
	in.close();
	} catch (IOException e) {
	out.println("无法连接到：" + url.toString()+"<br/>");
}
try {
	url = new URL("http://3.tel.webtrn.cn:8080/whaty/038/1.wmv");
	InputStream in = url.openStream();
	out.println("连接正常：" + url.toString()+"<br/>");
	in.close();
	} catch (IOException e) {
	out.println("无法连接到：" + url.toString()+"<br/>");
}
try {
	url = new URL("http://4.tel.webtrn.cn:8080/whaty/038/1.wmv");
	InputStream in = url.openStream();
	out.println("连接正常：" + url.toString()+"<br/>");
	in.close();
	} catch (IOException e) {
	out.println("无法连接到：" + url.toString()+"<br/>");
}
try {
	url = new URL("http://5.tel.webtrn.cn:8080/whaty/038/1.wmv");
	InputStream in = url.openStream();
	out.println("连接正常：" + url.toString()+"<br/>");
	in.close();
	} catch (IOException e) {
	out.println("无法连接到：" + url.toString()+"<br/>");
}
try {
	url = new URL("http://6.tel.webtrn.cn:8080/whaty/038/1.wmv");
	InputStream in = url.openStream();
	out.println("连接正常：" + url.toString()+"<br/>");
	in.close();
	} catch (IOException e) {
	out.println("无法连接到：" + url.toString()+"<br/>");
}
try {
	url = new URL("http://1.cnc.webtrn.cn:8080/whaty/038/1.wmv");
	InputStream in = url.openStream();
	out.println("连接正常：" + url.toString()+"<br/>");
	in.close();
	} catch (IOException e) {
	out.println("无法连接到：" + url.toString()+"<br/>");
}
try {
	url = new URL("http://2.cnc.webtrn.cn:8080/whaty/038/1.wmv");
	InputStream in = url.openStream();
	out.println("连接正常：" + url.toString()+"<br/>");
	in.close();
	} catch (IOException e) {
	out.println("无法连接到：" + url.toString()+"<br/>");
}
try {
	url = new URL("http://3.cnc.webtrn.cn:8080/whaty/038/1.wmv");
	InputStream in = url.openStream();
	out.println("连接正常：" + url.toString()+"<br/>");
	in.close();
	} catch (IOException e) {
	out.println("无法连接到：" + url.toString()+"<br/>");
}
try {
	url = new URL("http://4.cnc.webtrn.cn:8080/whaty/038/1.wmv");
	InputStream in = url.openStream();
	out.println("连接正常：" + url.toString()+"<br/>");
	in.close();
	} catch (IOException e) {
	out.println("无法连接到：" + url.toString()+"<br/>");
}
try {
	url = new URL("http://1.edu.webtrn.cn:8080/whaty/038/1.wmv");
	InputStream in = url.openStream();
	out.println("连接正常：" + url.toString()+"<br/>");
	in.close();
	} catch (IOException e) {
	out.println("无法连接到：" + url.toString()+"<br/>");
}
out.println("测试结束");
%>    

  </body>
</html>
