<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>中国证券业远程培新平台</title>
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
  	<s:if test="togo != null">
  		<s:if test="togo == 'back'">
  			<script>
  				alert("该课程暂无调查问卷");
  				window.close();
  			</script>
  		</s:if>
  		<s:elseif test="togo == 'togo'">
  			<script>
  				alert("<s:property value='msg' />");
  				window.history.back(-1);
  			</script>
  		</s:elseif>
  		<s:else>
  			<script>
  				window.location = "<s:property value='togo'/>";
  			</script>
  		</s:else>
  	</s:if>
  </body>
</html>
