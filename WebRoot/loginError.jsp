<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>


  </head>
       <script>
       var logintype = '<s:property value='loginType'/>';
       alert("<%=request.getAttribute("loginErrMessage")%>");
       if(logintype=='1' || logintype=='3'){
	   		window.location.href="/cms/tologin.htm";
       }else{
			window.location.href="/";
       }
       </script>
</html>
