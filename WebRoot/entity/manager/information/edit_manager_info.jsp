<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>个人信息修改</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="./css/css.css" rel="stylesheet" type="text/css">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">


  </head>
  
  <script type="text/javascript">
  
  function checkall(){
  	if(document.getElementById('trueName').value=='') {
  		window.alert('姓名不能为空');
  		return false;
  	}
  	if(document.getElementById('zhiCheng').value=='' || document.getElementById('zhiCheng').value==null ) {
	  	if(document.getElementById('zc').value=='')
	  		window.alert('部门不能为空');
		else
	  		window.alert('职务不能为空');
  		return false;
  	}
  	if(document.getElementById('email').value==''){
  			window.alert("邮箱不能为空！");
  			document.getElementById('email').focus();
			return false;
	}else{
		var email=document.getElementById('email').value;
		//var  email_check=/^\w+([-+.]\w+)*@\w+([-+.]\w+)*$/;
		var email_check=/^([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		//var pattern=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		if(!email_check.test(email))
		{
			alert("邮箱格式不正确！\n请重新输入");
			document.getElementById('email').focus();
			return false;
		}
	}
	
	if(document.getElementById('phone').value==''){
			window.alert("固定电话号码不能为空！");
  			document.getElementById('phone').focus();
			return false;
	}else{
//	var reg = /^(\d{3}-\d{8}|\d{4}-\d{7,8})|(\d{7,8})$/;
		var reg = /^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
	//var reg=/^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$/;
		
		if(!reg.test(document.getElementById('phone').value)) {
			alert('固定电话号码不合法。');
			document.getElementById('phone').focus();
			return false;
		}
	}
	
	if(document.getElementById('mobilephone').value==''){
			window.alert("手机号码不能为空！");
  			document.getElementById('mobilephone').focus();
			return false;
	}else{
		var myMobile_no=document.getElementById('mobilephone').value;
		var myreg=/^1[3|4|5|8][0-9]\d{8}$/;
		var myreg11=/^1[0-9]{10}$/;
		if(!myreg.test(myMobile_no)||!myreg11.test(myMobile_no))
		{
			alert("手机号码不合法！\n请重新输入");
			document.getElementById('mobilephone').focus();
			return false;
		}
	}
	
  }
  
	function checkEmail()
	{
		if(document.getElementById('email').value==''){
			return true;
		}
		var email=document.getElementById('email').value;
		var  email_check=/^\w+([-+.]\w+)*@\w+([-+.]\w+)*$/;
		if(!email_check.test(email))
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
	}
</script>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">


<div id="main_content">
    <div class="content_title">个人信息修改</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<form id='teacherInfo' action="/entity/information/personalInfo_editInfo.action" method="post" onsubmit="return checkall();">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
 							  <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name"><font color="red">*</font>用 户 名：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"> <s:property value="peManager.loginId" /><s:property value="peEnterpriseManager.loginId" /> </td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name"><font color="red">*</font>管理员姓名：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><input class=selfScale1 id="trueName" name='trueName' value='<s:property value="peManager.trueName"/><s:property value="peEnterpriseManager.name" />' /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  <s:if test="peEnterpriseManager!=null">
                                  	<s:if test="peEnterpriseManager.enumConstByGender.name=='男'.toString()">
                                  		<input type="radio" name="gender" value="1" checked="checked">男 
                                  		<input type="radio" name="gender" value="0">女
                              		</s:if>
                                  	<s:elseif test="peEnterpriseManager.enumConstByGender.name=='女'.toString()">
                                  	<input type="radio" name="gender" value="1">男 
                                  		<input type="radio" name="gender" value="0" checked="checked">女
                              		</s:elseif>
                              		<s:else>
                                  		<input type="radio" name="gender" value="1">男
                              			<input type="radio" name="gender" value="0">女
                                  </s:else>
                                  </s:if>
                                  <s:elseif test="peManager!=null">
                                  	<s:if test="peManager.enumConstByGender.name=='男'.toString()">
                                  		<input type="radio" name="gender" value="1" checked="checked">男 
                                  		<input type="radio" name="gender" value="0">女
                              		</s:if>
                                  	<s:elseif test="peManager.enumConstByGender.name=='女'.toString()">
                                  		<input type="radio" name="gender" value="1">男
                                  		<input type="radio" name="gender" value="0" checked="checked">女
                              		</s:elseif>
                              		<s:else>
                                  		<input type="radio" name="gender" value="1">男
                              			<input type="radio" name="gender" value="0">女
                                 	 </s:else>
                                  </s:elseif>
                                  </td>
                                 </tr>
                                
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox">
                                  <input type="hidden" name="zc" value="<s:property value="peManager" />" />
                                  	<span class="name"><font color="red">*</font><s:if test="peManager !=null">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</s:if><s:else>所在部门：</s:else></span></td>
                                  <td class="postFormBox" style="padding-left:18px"><input class=selfScale1 id="zhiCheng" name='zhiCheng' value='<s:property value="peManager.zhiCheng"/><s:property value="peEnterpriseManager.peEnterprise.fzrDepart" />'/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name"> 
                                   <font color="red">*</font> 电子邮件：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><input class=selfScale1 id="email" name='email' value='<s:property value="peManager.email"/><s:property value="peEnterpriseManager.email" />' /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name"><font color="red">*</font>固定电话：</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                    <input class=selfScale1 name='phone' id="phone" value='<s:property value="peManager.phone"/><s:property value="peEnterpriseManager.phone" />'  />                                  </td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name"><font color="red">*</font>移动电话：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><input id="mobilephone" class=selfScale1 name='mobilePhone' value='<s:property value="peManager.mobilePhone"/><s:property value="peEnterpriseManager.mobilePhone" />' /></td>
                                </tr>
                                <s:if test="peEnterpriseManager !=null" >
	                                <tr valign="middle"> 
	                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">办公地址：</span></td>
	                                  <td class="postFormBox" style="padding-left:18px"><input id="address" class=selfScale1 name='address' value='<s:property value="peEnterpriseManager.peEnterprise.address"/>' /></td>
	                                </tr>
	                                <s:if test='peEnterpriseManager.peEnterprise.enumConstByFlagEnterpriseType.code != "V" && peEnterpriseManager.peEnterprise.peEnterprise.enumConstByFlagEnterpriseType.code != "V"'>
	                                <tr valign="middle">
	                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">从业人员规模：</span></td>
	                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#num"/>
	                                  <select name="num" id="num">
	                                  <option  value="">请选择从业人员规模</option>
	                                  <option  value="0-100人" <s:if test="peEnterpriseManager.peEnterprise.num=='0-100人'" >selected</s:if>>0-100人</option>
	                                  <option  value="100-500人"<s:if test="peEnterpriseManager.peEnterprise.num=='100-500人'" >selected</s:if>>100-500人</option>
	                                  <option  value="500-2000人"<s:if test="peEnterpriseManager.peEnterprise.num=='500-2000人'" >selected</s:if>>500-2000人</option>
	                                  <option  value="2000-5000人"<s:if test="peEnterpriseManager.peEnterprise.num=='2000-5000人'" >selected</s:if>>2000-5000人</option>
	                                  <option  value="5000人以上"<s:if test="peEnterpriseManager.peEnterprise.num=='5000人以上'" >selected</s:if>>5000人以上</option>
	                                  </select>
	                                  </td>
	                                </tr>
	                                </s:if>
                                </s:if>
                           <tr>
                            <td height="50" align="center" colspan="2">
                              <input type="submit" value="提交"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="返回" onClick="javascript:window.location.href='/entity/information/personalInfo_viewInfo.action';"/>                            </td>
						  </tr>
						    <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>
<input type="hidden" name="id" value='<s:property value="peManager.id"/><s:property value="peEnterpriseManager.id"/>'/>
</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>
