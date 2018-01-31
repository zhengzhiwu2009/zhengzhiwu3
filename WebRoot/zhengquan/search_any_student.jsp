<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<title>全系统学员查询</title>
<link rel="stylesheet" type="text/css" href="/zhengquan/css/shared.css">
<link rel="stylesheet" type="text/css" href="./js/extjs/resources/css/ext-all.css">
<script language="JavaScript" src="js/shared.js"></script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="101.5%" border="0" cellspacing="0" cellpadding="4" align="center">
    <tr> 
      <td class="tdsectionbar">全系统学员查询</td>
    </tr>
</table>
<div id="tab">
<div id="tab_user2course" style="width:100.73%">
<form name="form1" method="post" action="/entity/teaching/searchAnyStudent_electivedCourse.action">
   <table width="98%" border="0" align="center" cellpadding="2" cellspacing="3" class="tdbg2">
    <tr>
    <td width="10%"></td> 
    <td width="20%" nowrap class="tdbg2" align="right"> 学员姓名
      <input name="peBzzStudent.trueName" type="text" size="15" maxlength="30"></td>
	   <td width="20%" nowrap class="tdbg2" align="right">身份证号
      <input name="peBzzStudent.cardNo" type="text" size="15" maxlength="30"></td>
		<td width="40%" nowrap class="tdbg2" align="left">
	  <INPUT class=button type=submit value="搜索"></td>
    </tr>
  </table>
  </form>
</div>
</div>
</body>
</html>
