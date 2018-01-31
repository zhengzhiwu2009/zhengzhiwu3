<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>中国证劵业协会培训平台</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<style type="text/css">
	.aaa {
	font-size: 18px;
	color: #0B748E;
	text-decoration: none;
	padding-left: 12px;
	letter-spacing: 1px;
	filter: DropShadow(Color=#ffffff, OffX=1, OffY=1, Positive=135);
	font-family:"Times New Roman",Georgia,Serif;
	cursor: default;
	padding-top: 2px;
} 
</style>
  </head>
  
  <body>
<div id="main_content">
    <div class="content_title">信息内容</div>
    <div class="cntent_k">
   	  <div class="k_cc" style="height:380px">
	  		<table align="center" border="0" width="700">
				<tr><td height="25" align="center" class="aaa"><s:property value="bean.title"/> </td></tr>
				<tr><td class="tab_dc_Mid"  align="right">发布时间：<s:date name="bean.publishDate" format="yyyy-MM-dd"/> &nbsp; </td></tr>
				<tr valign="top"><td height="300px" style="background-color: #E4F0F0;
						border-bottom-width: 3px;
						border-bottom-style: solid;
						border-bottom-color: #FFFFFF;"><br/><p><s:property value="bean.note" escape="false"/>

</p></td></tr>
			</table>
	  </div>
	<div align="center"><img src="/entity/student/images/close.gif" title="关闭" style="cursor: hand" onclick="javascript:window.close();" ></div>
    </div>
</div>
<div class="clear"></div>         
</body>
</html>
