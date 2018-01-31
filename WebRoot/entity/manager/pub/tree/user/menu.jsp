﻿<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<% response.setHeader("expires", "0"); %>	

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:property value="leftMenu"/></title>

<link href="/entity/manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
<script src="/entity/manager/pub/tree/js/tree/menu.js" type="text/javascript"></script>
<script language="JavaScript">
function window_onload()
{
	initialize();
	setTimeout(expandall,500);
}
//
var judge=1;
function expandall()
{
	var o = document.getElementById('topB');
	if (judge==1)
	{
	
		closeAll();
		judge=0;
		o.src='/entity/manager/pub/tree/js/tree/icon-closeall.gif';
		o.alt='全部展开';
		document.getElementById("topText").innerText = "( 展开全部 )";
	}
	else
	{
		openAll();
		judge=1;
		o.src='/entity/manager/pub/tree/js/tree/icon-expandall.gif';
		o.alt='全部折叠';
		document.getElementById("topText").innerText = "( 折叠全部 )";
	}
}
</SCRIPT>
<style>
body{margin-left:0px; background-color:#E0F2FF;}
.aaul{
    font-size:12px;
	color:#0D2F4F;
	background-color:#E0F2FF;
	margin-left:0px;}
</style>
</head>
<body onLoad="window_onload()" onselectstart="return false;" >
<!--left_menu-->
<div id="left_menu">
  <div class="menu_bg">
 
        <div class="menu_content">
<table id=control width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="16" align="left" valign="top" style="font-size:9pt;color:#666666;cursor:hand"  onclick="expandall(document.getElementById('topB'))"><img src="/entity/manager/pub/tree/js/tree/icon-expandall.gif" id="topB" width="16" height="15" class="button" vspace="2" alt="全部展开" /></td>
    <td id="topText" align="left" valign="top" style="font-size:9pt;color:#666666;cursor:hand;padding-top:4px"  onclick="expandall(document.getElementById('topB'))">( 折叠全部 )</td>
  </tr>
</table>
<table border=0>
  <tr>
    <td >
<%
	if(session.getAttribute("type") != null){
		if (session.getAttribute("type").equals("1")) {
%>
		<%@include file="menu_manager.jsp"%>
<%
		} else {
%>
		<%@include file="menu_sitemanager.jsp"%>
<%
		}
	}else{
	 	UserSession userSession = (UserSession)session.getAttribute("user_session");

		if (userSession == null) {
%>
		<script>
			window.top.alert("长时间未操作或操作异常，为了您的账号安全，请重新登录！");
			window.top.location = "/";
		</script>
<%
		return;
		}
		
%>	
		<script language="javascript" type="text/javascript">
		<s:property value="leftMenu" escape="false"/>
		document.write(menu(0));
		</script>
<%
		
	}
%>
    </td>
  </tr>
</table>
        </div>
    </div>
</div>
</body>
</html>
