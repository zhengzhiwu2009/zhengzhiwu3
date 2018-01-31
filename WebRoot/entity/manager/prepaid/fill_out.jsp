<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import = "java.util.*,com.whaty.platform.training.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(Object str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
	    	 return "";
	    else return str.toString();
	   
		
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>培训平台－后台管理</title>
<link href="<%=request.getContextPath()%>/work/manager/css/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="../../../js/frame.js"></script>
<script language="JavaScript"
			src="/web/bzz_index/password/js/jquery-1.7.1.min.js"></script>
<style type="text/css">
<!--
.w1 {
	font-family: "宋体";
	font-size: 12px;
	line-height: 24px;
	color: #333333;
	text-decoration: none;
}
.w2 {
	font-family: "宋体";
	font-size: 14px;
	line-height: 24px;
	font-weight: bold;
	color: #265A8E;
	text-decoration: none;
}
-->
</style>
<script>
	function checkValues(){
      if (document.getElementById("orderNum").value== ""){
         alert( "请输入汇款人银行账号！" );
         document.getElementById("orderNum").focus();
         return false;
      }
      if (document.getElementById("reOrderNum").value== ""){
         alert( "请输入汇款人银行账号！" );
         document.getElementById("reOrderNum").focus();
         return false;
      }
      if(document.getElementById("orderNum").value!=document.getElementById("reOrderNum").value){
   		alert("两次输入的汇款人银行账号不一致，请重新输入！");
   		document.getElementById("orderNum").focus();
   		return false;
      }
   }
   
   function isSubmit() {
   if (document.getElementById("orderNum").value== ""){
         alert( "请输入汇款人银行账号！" );
         document.getElementById("orderNum").focus();
         return false;
      }
      if (document.getElementById("reOrderNum").value== ""){
         alert( "请输入汇款人银行账号！" );
         document.getElementById("reOrderNum").focus();
         return false;
      }
      if(document.getElementById("orderNum").value!=document.getElementById("reOrderNum").value){
   		alert("两次输入的汇款人银行账号不一致，请重新输入！");
   		document.getElementById("orderNum").focus();
   		return false;
      }
      
/*      var forward = $('#forward').val();
 
      if(forward!=null || forward != '') {
      	$('#order').attr('action',forward);
      }
*/
   	 $('#order').submit();	
   }
</script>
</head>
<%
	String id = fixnull(request.getAttribute("id"));
 %>
<body leftmargin="0" rightmargin="0" style="background-color:transparent;" class="scllbar">
	<!-- start:图标区域 -->
	<!-- end:图标区域 -->
	<form name='order' id='order' action='<s:property value="getServletPath()"/>_applyFillout.action' method='post' class='nomargin'  enctype="multipart/form-data">
	<!-- start:内容区域1 -->
	<input type="hidden" name='id' value="<%=id %>">
	<div unselectable="on" class="border">   
		<table width=60%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61">
		<tr bgcolor="#FFFFFF">
			<td align='center' bgColor=#E6EFF9 class="w2" colspan="3">请输入汇款人银行账号</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' bgColor=#ffffff class="w1"><span>请输入汇款人银行账号：</span></td>
			<td bgColor=#ffffff class="w1"><input class="xmlInput textInput" name="orderNum" id="orderNum" maxlength="200"></td>
			<td bgColor=#ffffff class="w1">*必填</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' bgColor=#ffffff class="w1"><span>请再次输入汇款人银行账号：</span></td>
			<td bgColor=#ffffff class="w1"><input class="xmlInput textInput" name="reOrderNum" id="reOrderNum" style="ime-mode:disabled" onpaste="return false" maxlength="200"></td>
			<td bgColor=#ffffff class="w1">*必填</td>
		</tr>		
		<tr bgcolor="#FFFFFF">
		<td colspan="3" align="center" bgColor=#ffffff class="w1">
		 <input name="ok" value="提 交" type="button" onclick="isSubmit();" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">		
    	<input name="cancel" type="button" value=" 返 回" onclick="history.go(-1)" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">
		</td>
		</tr>
		</table>
	</div>
	<input  type="hidden" id="forward" value="<s:property value="forward"/>">
</form>
</body>
</html>

