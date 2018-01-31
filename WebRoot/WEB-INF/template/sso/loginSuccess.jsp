<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title></title>
    
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
   
	    <script language="JavaScript">
	    	//if(navigator.userAgent.indexOf("MSIE")>0) {  
				<s:if test="#attr.user_session.userLoginType==0">
					<s:if test='#session.enterpriseType == "V"'>
						window.top.location.href='/entity/workspaceRegStudent/regStudentWorkspace_toRegStudent.action';//监管学员
					</s:if>
					<s:else>
						window.top.location.href='/entity/workspaceStudent/studentWorkspace_toIndex.action';
					</s:else>
				</s:if>
				<s:elseif test="#attr.user_session.userLoginType==1">
					window.top.location.href='/entity/teacher/teacher_announce.jsp';		
				</s:elseif>
				<s:elseif test="#attr.user_session.userLoginType==2">
					window.top.location.href='/entity/manager/index.jsp';
				</s:elseif>
				<s:elseif test="#attr.user_session.userLoginType==3">
					window.top.location.href='/entity/manager/index.jsp';
				</s:elseif>
				<s:elseif test="#attr.user_session.userLoginType==4">
					window.top.location.href='/entity/manager/index.jsp';
				</s:elseif>
				<s:elseif test="#attr.user_session.userLoginType==5">
					window.top.location.href='/entity/manager/index.jsp';
				</s:elseif>
				<s:elseif test="#attr.user_session.userLoginType==6">
					window.top.location.href='/entity/manager/index.jsp';
				</s:elseif>
				<s:else>
					alert('登陆失败');
				</s:else>
	    	//} else {
	    	//	alert('请使用IE浏览器，暂不支持其它类型浏览器');
	    	//} 
　　 			
	    </script>
   
    
    
  </body>
</html>
