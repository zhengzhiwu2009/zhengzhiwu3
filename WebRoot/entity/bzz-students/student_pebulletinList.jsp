<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" /> 
<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" /> 
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}

-->
</style></head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=150 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
     <td width="130" valign="top"><td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17"  >
        <div class="main_title" >浏览公告</div>
        </td>
      </tr>
     
      <tr align="center">
        <td height="29" >
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="datalist" align="center">
          <tr bgcolor="#8dc6f6">
            <td width="50%"  align="center">标 &nbsp; 题</td>
            <td width="20%" align="center">发布时间</td>
            <td width="20%" align="center">发布人</td>
            <td width="10%" align="center">查看内容</td>
          </tr>
           <s:iterator value="#request.bList" id="stuts" >
          <tr>
            <td width="40%"  align="center">
				<a href='/entity/workspaceStudent/stuPeBulletinView_toInfo.action?bean.id=<s:property value="#stuts[0]"/>' target="_blank"><s:property value="#stuts[1]" /></a>
			</td>
			<td width="20%"  align="center">
				<s:date name="#stuts[2]" format="yyyy-MM-dd" />
			</td>
			<td width="20%" align="center">
				<s:property value="#stuts[3]"/>
			</td>
			
				<td width="20%" align="center"><div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_22.jpg')" ><a href='/entity/workspaceStudent/stuPeBulletinView_toInfo.action?bean.id=<s:property value="#stuts[0]"/>' target="_blank" >查看</a></div></td>
			
          </tr>
           </s:iterator>
        </table>
        </td>
      </tr>
   
      <tr>
       <td width="13">&nbsp;</td>
      </tr>
      
        </table></td>
    <td width="130">&nbsp;</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
</tr>
</table>
</body>
</html>