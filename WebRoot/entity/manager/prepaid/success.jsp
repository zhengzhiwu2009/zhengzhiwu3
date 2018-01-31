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
	<input  type="hidden" id="forward" value="<s:property value="forward"/>">
		<script type="text/javascript">
  		 var operateresult = '<s:property value='operateresult'/>';
  		if(operateresult=='1'){
			alert("回填单据成功！");
			var forward = document.getElementById('forward').value;
			if(forward!=null || forward != '') {
				window.location.href = forward;
			} 
  		}else{
  			alert("操作失败!");
  			window.history.back();
  		}
  	  </script>
	</head>
	<body>
	</body>
</html>
