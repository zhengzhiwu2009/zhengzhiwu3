﻿<%@ page language="java"  pageEncoding="UTF-8"%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%//@ include file="pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中国证券协会远程培训系统</title>
<script language="JavaScript" src="js/frame.js"></script>
<style type="text/css">
     frameset {border:0px;}
     frame {border:0px;}
 </style> 
<script type="text/javascript">
function logout(){
	window.top.location="/sso/login_close.action?loginErrMessage=clear";	
}

window.onunload=function (){
	if(window.screenLeft>10000|| window.screenTop>10000){ 
		var url="/sso/login_close.action?loginErrMessage=clear";
		//开始初始化XMLHttpRequest对象  
		if(window.XMLHttpRequest) { //Mozilla 浏览器  
			xmlHttp = new XMLHttpRequest();  
			if (xmlHttp.overrideMimeType) {//设置MiME类别  
				xmlHttp.overrideMimeType("text/xml");  
			}  
		}else if (window.ActiveXObject) { // IE浏览器  
			try {  
				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");  
			} catch (e) {  
			 try {  
				 xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
			 } catch (e) {}  
			}  
		}  
		if (!xmlHttp) { // 异常，创建对象实例失败  
		 window.alert("不能创建XMLHttpRequest对象实例.");  
		}  
	    req.open("Get",url,true);
	    req.onreadystatechange = function(){
	    	if(req.readyState == 4);
	    };
  		req.send(null);
 		}
}
	
</script>
</head>
<%

/*
		String loginId = "guocheng";
		SsoFactory factory=SsoFactory.getInstance();
		SsoUserPriv ssoUserPriv=factory.creatSsoUserPriv(loginId);
		SsoUserOperation ssoUserOperation=factory.creatSsoUserOperation(ssoUserPriv);
		SsoManagerPriv ssoManagerPriv =factory.creatSsoManagerPriv();
		SsoManage  ssoManager =factory.creatSsoManage(ssoManagerPriv);
		
		session.setAttribute("ssoUserPriv",ssoUserPriv);
		
		PlatformFactory pfactory=PlatformFactory.getInstance();
		PlatformManage platformManage=pfactory.createPlatformManage();
		
		EntityUser enuser=platformManage.getEntityUser(ssoUserPriv.getId(),"manager");
		
			session.removeAttribute("infomanager_priv");	  
		  	session.removeAttribute("smsmanager_priv");  	  	
		  	session.setAttribute("eduplatform_user",enuser);
			ManagerPriv managerPriv= pfactory.getManagerPriv(enuser.getId());
			session.setAttribute("eduplatform_priv",managerPriv);
*/

        session.setAttribute("type",request.getParameter("type"));

 %>
<frameset rows="62,34,*" frameborder="no" border="0" framespacing="0" name="TCB">
	<frame src="/entity/manager/pub/topBar1.jsp" name="banner" scrolling="NO" noresize>
	<frame src="/entity/manager/pub/topBar2.jsp" name="banner2" scrolling="NO" noresize>
	<frameset cols="213,*" frameborder="no" border="0" framespacing="0" name="TC">
	<frameset rows="*,0" frameborder="no" border="0" framespacing="0" name="TH">
		<frame src="/entity/manager/pub/tree.htm" name="tree" scrolling="no" noresize>
		<!-- <frame src="/entity/manager/pub/help.htm" name="help" scrolling="no" noresize> -->
	</frameset>
	<frameset rows="0,*" frameborder="no" border="0" framespacing="0" name="MC">
		<frame src="/entity/manager/pub/menu.htm" name="menu" scrolling="no" noresize>			
		<frame src="/entity/manager/pub/content.htm" name="content" scrolling="no" noresize>
	</frameset>
	</frameset>
	<!--<frame src="/entity/manager/pub/bottom.jsp" name="bottom" scrolling="NO" noresize>-->
</frameset>
<noframes></noframes>
<body>
</body>
</html>
