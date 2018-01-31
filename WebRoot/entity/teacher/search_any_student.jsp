<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<title>全系统学员查询</title>
<link rel="stylesheet" type="text/css" href="./js/extjs/resources/css/ext-all.css">
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="js/shared.js"></script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="101.5%" border="0" cellspacing="0" cellpadding="4" align="center">
    <tr> 
    
      <td><div class="content_title">全系统学员查询</div></td>
    </tr>
</table>
<div id="tab">
<div id="tab_user2course" style="width:100.73%">
<form name="form1" method="post" action="/entity/teaching/searchAnyStudent_electivedCourse.action" id="form1" target="_blank">
<div class="cntent_k">
   <table width="98%" border="0" align="center" cellpadding="2" cellspacing="3" class="tdbg2">
    <tr >
    <td width="10%"></td> 
    <td width="20%" nowrap class="tdbg2" align="right">学员姓名：
      <input name="peBzzStudent.trueName" type="text" size="15" maxlength="30" id="stuName">
    </td>
	<td width="20%" nowrap class="tdbg2" align="center"><font color="red">*</font>证件号码：
      <input name="peBzzStudent.cardNo" type="text" size="16" maxlength="30" id="cardNo"></td>
	<td width="40%" nowrap class="tdbg2" align="left">
	  <INPUT class=button onclick="doSubmit();" type="button" value="&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;"></td>
    </tr>
    <tr>
    	<td>&nbsp;</td>
    </tr>
    <tr>
    	<td colspan="4" align="center"><font color="red">注：搜索时必须将证件号码填写才可进行搜索</font></td>
    </tr>
  </table>
  </div>
  </form>
</div>
</div>
</body>
<script type="text/javascript">
	function doSubmit() {
//		var name = document.getElementById('stuName').value;
		var cardNo = document.getElementById('cardNo').value;
//		if(name == null || name == '') {
//			alert('请填写学员姓名');
//			return;
//		}
		if(cardNo == null || cardNo == '') {
			alert('请填写证件号码');
			return;
		}
		document.getElementById('form1').submit();
	}
	
</script>
</html>
