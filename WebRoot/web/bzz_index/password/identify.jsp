<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>找回密码</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css"> 
.notice_wrap{ border:1px solid #8bb2d9; background-color:#F2F7FF; margin-top:15px; padding:13px;width:450px;}
.notice_wrap .notice_main{_height:20px; border:1px solid #E3EFFC; background-color:#fff; padding:25px 30px;}
.notice_wrap .notice_main p{ line-height:180%; text-indent:2em;}
</style>
<link rel="stylesheet" type="text/css" href="/web/bzz_index/password/css/master.css" />
<link rel="stylesheet" type="text/css" href="/web/bzz_index/password/css/passport.css" />
<script src="/web/bzz_index/password/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
var isUserEmail = false;
$(function(){
	var errorLoginIdMessage = "<s:property value='errorLoginIdMessage' />";
	if(null != errorLoginIdMessage && "" != errorLoginIdMessage){
		$("#errUserLoginId").html("该登录账号不存在").css({"display" : "inline-block"});
	}
	var errorValidateCodeMessage = "<s:property value='errorValidateCodeMessage' />";
	if(null != errorValidateCodeMessage && "" != errorValidateCodeMessage){
		$("#errUsercheckcode").html("验证码输入不正确").css({"display" : "inline-block"});
	}
	var errorEmailMessage = "<s:property value='errorEmailMessage' />";
	if(null != errorEmailMessage && "" != errorEmailMessage){
		$("#errUsername").html("该邮箱未在平台注册").css({"display" : "inline-block"});
	}
	var message = "<s:property value='message' />";
	if(null != message && "" != message){
		$("#msg").html("用户中心连接超时，用户信息验证失败！");
		$("#msgBox").fadeIn(2000).fadeOut(3000);
	}
	
	$('#email').bind('keydown',function(e){ 
		  if( e.which == 13 ){
			  fmSubmt();
		  }
	});
	
	$('#usercheckcode').bind('keydown',function(e){ 
		if( e.which == 13 ){
			fmSubmt();
		 }
	});
	
	$("#loginId").focus();
	
	$("#email").change(function(){
		var email = $(this).val();
		if(email == ''){
			$("#errUsername").html("请输入注册邮箱").css({"display" : "inline-block"});
		}else{
			if( !isEmail(email) ){
				$("#errUsername").html("请输入正确格式的邮箱").css({"display" : "inline-block"});
			}else{
				$("#errUsername").html("").css({"display" : "none"});
			}
		}
	});
	$("#usercheckcode").change(function(){
		var usercheckcode = $(this).val();
		if(usercheckcode == ''){
			$("#errUsercheckcode").html("请输入验证码").css({"display" : "inline-block"});
		}else{
			$("#errUsercheckcode").html("").css({"display" : "none"});
		}
	});
});

function isEmail(email){
	 // var regu = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$";
  var regu =/^\w+([-+.]\w+)*@\w+([-+.]\w+)*$/;
    var re = new RegExp(regu);
    if (email.search(re) != -1) {
    	return true;
    } else {
    	return false;
    }
}



function fmSubmt(){
	var loginId = $("#loginId").val();
	var email = $("#email").val();
	var usercheckcode = $("#usercheckcode").val();
	
	if(loginId == ''){
		$("#errUserLoginId").html("请输入登录账号").css({"display" : "inline-block"});
	}
	
	if(email == ''){
		$("#errUsername").html("请输入正确的注册邮箱").css({"display" : "inline-block"});
		return;
	}
	
	if( !isEmail(email) ){
		$("#errUsername").html("请输入正确格式的邮箱").css({"display" : "inline-block"});
		return;
	}
	
	$("#inputForm").submit();
}


function fRefreshRandomImage(id) {
	 var obj = document.getElementById(id);  
     //获取当前的时间作为参数，无具体意义      
     var timenow = new Date().getTime();      
     //每次请求需要一个不同的参数，否则可能会返回同样的验证码      
     //这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。      
    obj.src="/password/getValidateCode.action?d="+timenow;      
}  


</script>
  </head>
  
  <body>
<!--头部 开始-->
<div class="header" style="margin-left:300px;">
<div class="header_tool">
<br/>
</div>
<a href="#"><img src="/web/bzz_index/password/images/title.jpg" class="logo" width="274" height="36" border="0"/></a> 
</div>
<!--头部 结束-->
<!--头部 结束-->
<!--主体 开始-->
<div class="main" style="width:800px;margin-left:300px;">
	<div class="main_con1" style="border-top:1px solid #C8C8C8;">
		<span class="pass_text">&nbsp;</span>
		<div class="p_10" style="padding-left:20px;">
			<ul class="m_step1">
				<li class="active">1.验证身份</li>
				<li>2.重置密码</li>
				<li>3.成功</li>
			</ul>
		</div>
		<div id="msgBox" style="border:1px solid #e6db55;background:#fffbcc; text-align :center;width:400px;height:24px;line-height:24px;margin:0 auto;color:red;font-weight:bold;display:none;">
			<img src="/web/bzz_index/password/images/warn.png" style="vertical-align: middle;"/>
			<span id="msg"></span>  
		</div>
		<div class=""  style="padding:45px 0 65px 200px;font:12px/1.8 "lucida grande", tahoma, verdana, arial, sans-serif, "宋体"; COLOR: #787878"">
			<form name="form" id="inputForm" method="post" action="/password/getPsw_sendEmail.action" >
				<ul class="main_form">
					<li>
						<div class="fm_left" >登录账号：</div>
						<div class="fm_right">
						<span class="suggest_box" id="loginid_box">
						<input name="loginId" id="loginId" class="input c_888888 w_200"  type="text" value="<s:property value="loginId"/>" /><span class="err" id="errUserLoginId">请输入正确的通行证用户名</span>
						</span>
						</div>       
					</li>
					<li>
						<div class="fm_left" >注册邮箱：</div>
						<div class="fm_right">
						<span class="suggest_box" id="useremail_box">
						<input name="email" id="email" class="input c_888888 w_200"  type="text" value="<s:property value="email"/>" /><span class="err" id="errUsername">请输入正确的邮箱</span>
						</span>
						</div>       
					</li>
					<li>
						<div class="fm_left">验证码：</div>
						<div class="fm_right">
						<input type="text" id="usercheckcode" name="validateCode" class="input" title="验证码" style="width:100px" value=""/>
						<span class="info p_t_5" id="checkcode" style="padding-top:5px;">
							<img id="code" src="/sso/authimg" onclick="this.src=this.src+'?'+(new Date()).getTime()" style="vertical-align:middle;" title="看不清楚，请点击切换"/>
						</span>
						<span class="err" id="errUsercheckcode"></span>
						<!--<div class="info p_t_5">不区分大小写 ，<a href="javascript:void(0)" onclick="fRefreshRandomImage('code')">换一张</a> </div>-->
						</div>
					</li>
					<li>
						<div class="fm_left">&nbsp;</div>
						<div class="fm_right"><span class="f_btn ">
						<input type="button" id="next" value="下一步" class="r_btn w_85" onclick="fmSubmt()"/></span> </div>
					</li>
				</ul>
				<div id="box">
				</div>
			</form>
		</div>  
	</div> 
	<div class="clear"></div>
	<div class="main_bottom1" style="border-right:1px solid #C8C8C8;"></div>
</div>
<!--主体 结束-->
<!--版权 开始-->
<div class="footer"> </div>
<!--版权 结束-->
</body>
</html>
