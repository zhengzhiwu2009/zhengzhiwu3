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
    
    <title>My JSP 'loginError.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<script type="text/javascript">
		<s:if test="loginErrMessage!=null&& loginErrMessage!='clear'">
			// window.document.loginform.loginType.value="<s:property value='loginType'/>";
			window.alert("<s:property value='loginErrMessage'/>");
		</s:if>
		window.navigate("/index.htm");
	</script>
  </head>
  
  <body>
    This is my JSP page. <br/>
  </body>
</html>
