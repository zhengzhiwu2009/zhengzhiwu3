<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
			<s:if test=" msg != null && msg != '' ">
				alert('<s:property value="msg" escape="false"/>');
			</s:if>
			<s:else>
	  			alert("注册失败!\n该用户名已存在或该身份信息已注册！");
			</s:else>
  			window.history.back();
  	  </script>
	</head>
	<body>
	</body>
</html>
