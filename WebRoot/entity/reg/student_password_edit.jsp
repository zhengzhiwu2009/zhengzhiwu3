<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String d=String.valueOf(Math.random());
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<link href="/entity/bzz-students/css/style.css" rel="stylesheet"
			type="text/css" />
<link href="/entity/bzz-students/css/reset.css" rel="stylesheet"
			type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
</head>
<script>
	function check()
	{
		var r = /^\d+$/;    //正则纯数字
		var m = /^[a-zA-Z]+$/;  //纯字母
		var result ='';
		var flag = true;
		if(document.pass_chg.password_old.value == '')
		{
			result +='请输入旧密码!\n';
			flag = false;
		}
		
		if(document.pass_chg.password_new.value=="")
		{
			result +='新密码不能为空!\n';
			flag = false;
		}
		var password = document.pass_chg.password_new.value;
		if(password.length < 8 || password.length >16) {
			result += '密码必须有8-16位组成，且不能为纯数字或纯字母!\n';
			
			flag = false;
		}
		if(r.test(password)) {
			result += '您的密码安全等级太低，不能由纯数字组成!\n';
			
			flag = false;
		}
		if(m.test(password)) {
			result += '您的密码安全等级太低，不能由纯字母组成!\n';
			
			flag = false;
		}

		if(document.pass_chg.password_recheck.value=="")
		{
			result += '确认密码不能为空\n';
			flag = false;
		}
		if(document.pass_chg.password_recheck.value!=document.pass_chg.password_new.value)
		{
			result += '密码与确认密码不符!\n';
			flag = false;
		}
		if(!flag){
			result += '请确认后重新添加!';
			alert(result);
			document.pass_chg.password_recheck.value="";
			document.pass_chg.password_new.value="";
			return false;
		}
		
		
		
		return true;
	}
	/**
	*用于校验密码是否符合规则
	*/
	function checkPassword() {
		var password=document.getElementById("password_new").value;
		var r = /^\d+$/;    //正则纯数字
		var m = /^[a-zA-Z]+$/;  //纯字母
		if(password.length < 8 || password.length >16) {
			alert('密码必须有8-16位组成，且不能为纯数字或纯字母');
			document.getElementById("password_new").value = '';
			return false;
		}
		if(r.test(password)) {
			alert('您的密码安全等级太低，不能由纯数字组成');
			document.getElementById("password_new").value = '';
			return false;
		}
		if(m.test(password)) {
			alert('您的密码安全等级太低，不能由纯字母组成');
			document.getElementById("password_new").value = '';
			return false;
		}
	}
	/**
	*用于校验两次密码是否相等
	**/
	function checkEqu() {
		var password=document.getElementById("password_new").value;
		var confirmpassword =document.getElementById("password_recheck").value;
		if(password != confirmpassword) {
			alert('两次输入密码不同');
			document.getElementById("password_recheck").value = '';
			return false;
		}
		
	}
</script>
<body>
<div class="" style="width: 100%; padding: 0; margin: 0;"
			align="center">
			<div class="main_title">
				修改密码
			</div>
			<div class="main_txt">
		<form method="post" action="/entity/workspaceRegStudent/regStudentWorkspace_passwordexe.action" name="pass_chg" onsubmit="return check();">
		<table class="datalist" border="0" cellpadding="0" cellspacing="0" align="center"  width="100%">
		  <tr>
		    <td  align="center" bgcolor="#E9F4FF" class="kctx_zi1">旧密码：</td>
		    <td  align="left" bgcolor="#FAFAFA" class="kctx_zi2">
		    	<input type="password" value="" name="password_old" />
		    	<input type="hidden" name="ssoid" value="<s:property value="peBzzStudent.ssoUser.id" />" />
		    </td>
		  </tr>
		  <tr>
		    <td align="center" bgcolor="#E9F4FF" class="kctx_zi1">新密码：</td>
		    <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"><input type="password" value="" id="password_new" name="password_new" onchange="checkPassword();"/></td>
		  </tr>
		  <tr >
		    <td  align="center" bgcolor="#E9F4FF" class="kctx_zi1">确认新密码：</td>
		    <td  align="left" bgcolor="#FAFAFA" class="kctx_zi2"><input type="password" value="" id="password_recheck" name="password_recheck" onchange="checkEqu();"/></td>
		  </tr>
		  <tr class="table_bg1">
		    <td colspan="2"><input type="submit" value="提交" name="submit" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <input type="button" value="返回" onclick="javascript:window.location='<%=basePath%>/entity/workspaceRegStudent/regStudentWorkspace_StudentInfo.action'"/>
		    </td>
		   </tr>
		</table>
	</form>
			<br />
			</div>
		</div>
</body>
</html>