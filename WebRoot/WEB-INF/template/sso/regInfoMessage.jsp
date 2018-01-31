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
		function goFirstPage() {
			window.location.href="/";
		}
  		 var operateresult = '<s:property value='operateresult'/>';
  		 var operateresult1 = '<s:property value='operateresult1'/>';
  		if(operateresult=='1'){
  			if(operateresult1=='2'){//集体
  				alert(" 注册成功，已加入管理员审核队列！请保存好用户名！");
  			}else{//个人
  				alert(" 注册成功！请保存好用户名！");
  			}
			//window.location.href="/";			
  		}else{
  			alert("系统繁忙，请稍后再试!");
  			window.history.back();
  		}
  	  </script>
	</head>
	<body bgcolor="#FAFAFA">
		<div align="center"><br/><br/><br/><br/><br/><br/>
			<div >
				<img src="/web/bzz_index/images/register.jpg"/><h2>恭喜您，注册成功!<font style="font:16px/1.6; color:#0092d2; "><a href="#" onclick="goFirstPage();"><u>去首页看看</u></a></font></h2>
			</div>
			<br/>
			
			<p>账号名：<font style="font:18px/1.2 "宋体",tahoma,arial; line-height:16px;"><s:property value="re"/></font><br/><br/></p>
		</div>
	</body>
</html>
