<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>学籍变动学生查询</title>
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
	</head>
	<body>
		<form name="print" method="get" action="/entity/studentStatus/peChangeSite_turntochange.action">
			<div id="main_content">
			   <div class="content_title">请输入要做学习中心变动学生的学号</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><font color="red"><s:property value='msg'/></font></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="right" class="postFormBox" ><span class="name"><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></span></div>
			   			</td>
			   			<td>
			   				<div align="center" class="postFormBox"><input type="text" name="bean.peStudent.regNo" value="<s:property value='bean.peStudent.regNo'/>" size="21"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><input type="submit" name="Submit" value="提交" /></div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>