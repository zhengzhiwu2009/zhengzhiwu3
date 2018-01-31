<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<% response.setHeader("expires", "0"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="test.info"/></title>
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" /> 
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" /> 
	</head>
		<body>
		<form name="print">
			<div class="" style="width: 100%; padding: 0; margin: 0;"
				align="center">
				<div class="main_title">
					<s:text name="test.info" />
				</div>
				<div class="msg" style="height:100%" width="98%">
					<img src="/entity/bzz-students/images/index_04.jpg" align="center" />
					<s:fielderror name="file"/>
				</div>
				<div class="main_txt">
					<input type=button value="返回" onclick="window.history.back();"/>
				</div>
				<div style="height:10px"></div>
			</div>
		</form>
	</body>
	<script>
		function turnTo(url){
			try{
				if(opener !=null && opener.location != null){
					
					opener.location.href=url;
					window.close();
				}else{
					
				}
			}catch(e){}
			window.location.href=url;
		}
	</script>