<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>二级机构信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="./css/css.css" rel="stylesheet" type="text/css">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">


  </head>
    <script type="text/javascript">
  
  function checkall(){
  	if(document.getElementById('name').value=='') {
  		window.alert('二级机构名称不能为空');
  		document.getElementById('name').focus();
  		return false;
  	}
  /*	if(document.getElementById('zhiCheng').value=='') {
  		window.alert('职务不能为空');
  		return false;
  	}
  	
	if(document.getElementById('phone').value==''){
			window.alert("固定电话号码不能为空！");
  			document.getElementById('phone').focus();
			return false;
	}
	else{
	//var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[3]\d{9}$)/;
	var reg = /^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
	
		if(!reg.test(document.getElementById('phone').value)) {
			alert('固定电话号码不合法。');
			document.getElementById('phone').focus();
			return false;
		}
	}
*/	
	if(document.getElementById('phone').value == '') {
		alert("联系电话不能为空");
		document.getElementById('phone').focus();
		return false;
	}
	if(document.getElementById('mobilephone').value != ''){
		var myMobile_no=document.getElementById('mobilephone').value;
		var myreg=/^\d{11}$/;
		if(!myreg.test(myMobile_no))
		{
			alert("手机号码不合法！\n请重新输入");
			document.getElementById('mobilephone').focus();
			return false;
		}
	}
	if(document.getElementById('email').value==''){
  			window.alert("邮箱不能为空！");
  			document.getElementById('email').focus();
			return false;
	}else{
		var email=document.getElementById('email').value;
		var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		if(!pattern.test(email))
		{
			alert("邮箱格式不正确！\n请重新输入");
			document.getElementById('email').focus();
			return false;
		}
	}
	
	
  }
  
	/*function checkEmail()
	{
		if(document.getElementById('email').value==''){
			return true;
		}
		var email=document.getElementById('email').value;
		var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		if(!pattern.test(email))
		{
			alert("邮箱格式不正确！\n请重新输入");
			document.getElementById('email').focus();
		}
		return true;
	}
	
	function checkAddress(){
		if(document.getElementById('address').value.length > 25) {
			alert('通信地址字数不能超过25个字符。');
			document.getElementById('address').focus();
		}
	}
	
	function checkMobilePhone()
	{
		
		var myMobile_no=document.getElementById('mobilephone').value;
		var myreg=/^\d{11}$/;
		if(myMobile_no=='')
		{
			alert("移动电话不能为空");
			document.getElementById('mobilephone').focus();
			return false;
		}
		if(!myreg.test(myMobile_no))
		{
			alert("移动电话不合法！\n请重新输入");
			document.getElementById('mobilephone').focus();
		}
		//return true;
	}
	
	function checkPhone() {
		var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[3]\d{9}$)/;
		var phone=document.getElementById('phone').value;
		if(phone=='')
		{
			alert("固定电话不能为空");
			document.getElementById('phone').focus();
			return false;
		}
		if(!reg.test(phone)) {
			alert('固定电话号码不合法。');
			document.getElementById('phone').focus();
			return false;
		}
	}*/
</script>
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">


<div id="main_content">
	<div class="content_title">二级机构信息修改</div>
    <div class="cntent_k"><div class="k_cc">
    <form id='enterpriseInfo' action="/entity/basic/peSubEnterprise_editInfo.action" method="post" onsubmit="return checkall();">
    <input type="hidden" value="<s:property value="manager.peEnterprise.id"/>" name="id"/>
	<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">                          
         <tr>
           <td height="26" valign="middle" align="center" colspan="10"><h4>机构信息</h4>                                               </td>
         </tr>
         <tr>
           <td height="8" colspan="10"> </td>
         </tr>
		 <tr valign="middle">
			 <td width="18%" height="30" align="left" class="postFormBox"><span class="name">用&nbsp;&nbsp;户&nbsp;&nbsp;名：</span></td>
		     <td width="50%" class="postFormBox" style="padding-left:18px" align="left">
		     	<s:property value="manager.loginId"/> 
		     </td>
		 </tr>
 		 <tr>
			 <td width="12%" height="20" align="left" class="postFormBox"><span class="name"><font color="red" size="3">*</font>二级机构名称：</span></td>
		     <td width="35%" class="postFormBox" style="padding-left:18px" align="left">
		     <input type="text" name="name" value="<s:property value="manager.peEnterprise.subName"/>"/>
		     </td>
		 </tr>
 		 <tr>
			 <td width="12%" height="20" align="left" class="postFormBox"><span class="name">管理员姓名：</span></td>
		     <td width="35%" class="postFormBox" style="padding-left:18px" align="left">
		     <input type="text" name="manager.name" value="<s:property value="manager.name"/>"/>
		     </td>
		 </tr>
  		 <tr>
			 <td width="12%" height="20" align="left" class="postFormBox"><span class="name">所在部门：</span></td>
		     <td width="35%" class="postFormBox" style="padding-left:18px" align="left">
		     <input type="text" name="fzrDepart" value="<s:property value="manager.peEnterprise.fzrDepart"/>"/>
		     </td>
		 </tr>
  		 <tr>
			 <td width="12%" height="20" align="left" class="postFormBox"><span class="name"><font color="red" size="3">*</font>联系电话：</span></td>
		     <td width="35%" class="postFormBox" style="padding-left:18px" align="left">
		     <input type="text" id="phone" name="manager.phone" value="<s:property value="manager.phone"/>"/>
		     </td>
		 </tr>
  		 <tr>
			 <td width="12%" height="20" align="left" class="postFormBox"><span class="name">手机号码：</span></td>
		     <td width="35%" class="postFormBox" style="padding-left:18px" align="left">
		     <input type="text" id="mobilephone" name="manager.mobilePhone" value="<s:property value="manager.mobilePhone"/>"/>
		     </td>
		 </tr>
  		 <tr>
			 <td width="12%" height="20" align="left" class="postFormBox"><span class="name"><font color="red" size="3">*</font>电子邮箱：</span></td>
		     <td width="35%" class="postFormBox" style="padding-left:18px" align="left">
		     <input type="text" id="email" name="manager.email" value="<s:property value="manager.email"/>"/>
		     </td>
		 </tr>
		 <tr>
             <td  height="10"> </td>
         </tr>
         <tr>
            <td colspan="2" align="center">
				<input type="submit" value="提交"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="返回" 

onClick="history.back();"/>  
			</td>
         </tr>
   	</table>
	  </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>
