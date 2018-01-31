  <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String d=String.valueOf(Math.random());
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


response.addHeader("Cache-Control", "no-cache");
response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
<META HTTP-EQUIV="expires" CONTENT="0">


<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css1.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {font-size: 12px; color: #0041A1; font-weight: bold; }
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
.kctx_zi1 {
	font-size: 12px;
	color: #0041A1;
}
.kctx_zi2 {
	font-size: 12px;
	color: #54575B;
	line-height: 24px;
	padding-top:2px;
}
-->
</style></head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
		<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
		  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
		  <tr>
		    <td height="13"></td>
		  </tr>
		</table>
		<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=580 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp?d=<%=d %>" scrolling=no allowTransparency="true"></IFRAME>
		    </td>
		    <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr align="center" valign="top">
		        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：缓考说明</td>
		      </tr>
		      <tr><td height="30">&nbsp;</td></tr> 
		      <tr>
		      	<td>
		      		<table width="85%" border="0" align="left" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:15px 0px 15px 0px;">  
			  			<tr bgcolor="#ECECEC">
       			 			<td align="left" bgcolor="#E9F4FF" class="kctx_zi1"><s:property value="peBzzExamBatch.late_explain"  escape="false"/></td>
       		  			</tr>
		      		</table>
		      </td>
		      </tr>
		      <tr><td height="10">&nbsp;</td></tr> 
		      <tr>
			     <td align="center" width="600">
			         <input type="button" value="下一步"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toLateCondition.action'"/>
			         <!-- <input type="button" value="下一步"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_latestudentInfo.action'"/> -->
			         &nbsp;&nbsp;&nbsp;&nbsp;
			        <input type="button" value="返回"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_examBatch.action'"/>
			     </td>
		      </tr>
		  </tr>
		</table>
    </td>
    <td width="13">&nbsp;</td>
  </tr>
  
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</body>
</html>