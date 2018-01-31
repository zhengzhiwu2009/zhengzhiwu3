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
	<link rel="stylesheet" type="text/css" href="/web/bzz_index/password/css/master.css" />
<link rel="stylesheet" type="text/css" href="/web/bzz_index/password/css/passport.css" />
<script src="/web/bzz_index/password/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	var message = "<s:property value='message' />";
	if(null != message && "" != message){
		if("0" == message){
			$("#errPassword").html("请输入密码").css({"display" : "inline-block"});
		}else if("1" == message){
			$("#errPassword").html("只能输入字母、数字或者字符").css({"display" : "inline-block"});
		}else if("2" == message){
			$("#errPassword").html("密码长度必须为6-16位").css({"display" : "inline-block"});
		}else if("3" == message){
			$("#errRepassword").html("请输入确认密码").css({"display" : "inline-block"});
		}else if("4" == message){
			$("#errRepassword").html("两次输入密码不一致").css({"display" : "inline-block"});
		}
	}
	
	$('#password').bind('keydown',function(e){ 
		  if( e.which == 13 ){
			  fmSubmt();
		  }
	});
	
	$('#repassword').bind('keydown',function(e){ 
		if( e.which == 13 ){
			fmSubmt();
		 }
	});
	
	$("#password").focus();
});

function fmSubmt(){
	var password = $.trim($("#password").val());
	var repassword = $.trim($("#repassword").val());
	
	if(password == ''){
		$("#errPassword").html("请输入密码").css({"display" : "inline-block"});
		return;
	}

    //判断是否含有中文      
    if (/.*[\u4e00-\u9fa5]+.*$/.test(password)) {
        //showNoticeMessage("wrong", "只能输入字母、数字或者字符", "passwWrap");
        $("#errPassword").html("只能输入字母、数字或者字符").css({"display" : "inline-block"});
        return;
    }
    
   /* 	if (password.length < 6 || password.length > 16){
   	 $("#errPassword").html("密码长度必须为6-16位").css({"display" : "inline-block"});
     return;
   	} */
   	
   	
   	$("#errPassword").html("").css({"display" : "none"});
   	
   	if(repassword == ''){
		$("#errRepassword").html("请输入确认密码").css({"display" : "inline-block"});
		return;
	}
   	
   	if( password != repassword ){
   		$("#errRepassword").html("两次输入密码不一致").css({"display" : "inline-block"});
   		return;
   	}
   	
   	$("#errRepassword").html("").css({"display" : "none"});
    
	$("#inputForm").submit();
}
</script>
  </head>
  
<body>
<!--头部 开始-->
<div class="header">
<div class="header_tool">
<br/>
</div>
<a href="#"><img src="/web/bzz_index/password/images/title.jpg" class="logo" width="274" height="36" border="0"/></a> 
</div>
<!--头部 结束-->
<!--头部 结束-->
<!--主体 开始-->
<div class="main">
	<div class="main_con1" style="border-top:1px solid #C8C8C8;">
		<span class="pass_text">&nbsp;</span>
		<div class="p_10">
			<ul class="m_step2">
				<li>1.验证身份</li>
				<li class="active">2.重置密码</li>
				<li>3.成功</li>
			</ul>
		</div>
		<div class=""  style="padding:45px 0 65px 200px;font:12px/1.8 "lucida grande", tahoma, verdana, arial, sans-serif, "宋体"; COLOR: #787878">
			<form name="form" id="inputForm" method="post" action="/password/getPsw_modifypassword.action">
				<input type="hidden" name="verifystr" value="<s:property value='verifystr' />"/>
				<ul class="main_form">
					<li>
						<div class="fm_left" >新密码：</div>
						<div class="fm_right">
						<span class="suggest_box" id="useremail_box">
						<input name="password" id="password" class="input c_888888 w_200"  type="password"/><span class="err" id="errPassword"></span>
						</span>
						</div>       
					</li>
					<li>
						<div class="fm_left" >确认密码：</div>
						<div class="fm_right">
						<span class="suggest_box" id="useremail_box">
						<input name="repassword" id="repassword" class="input c_888888 w_200"  type="password"/><span class="err" id="errRepassword"></span>
						</span>
						</div>       
					</li>
					<!-- <li>
						<div class="fm_left">验证码：</div>
						<div class="fm_right">
						<input type="text" id="usercheckcode" name="validateCode" class="input" title="验证码" style="width:100px" value=""/><span class="err" id="errUsercheckcode"></span>
						<div class="info p_t_5">不区分大小写，<a href="javascript:void(0)" onclick="fRefreshRandomImage('code')">换一张</a></div>
						<div class="info p_t_5" id="checkcode">
						<img id="code" src="/password/getValidateCode.action" onclick="fRefreshRandomImage('code')"/>
						</div>
						</div>
					</li> -->
					<li>
						<div class="fm_left">&nbsp;</div>
						<div class="fm_right"><span class="f_btn ">
						<input type="button" value="提交" class="r_btn w_85" onclick="fmSubmt()"/></span> </div>
					</li>
				</ul>
			</form>
		</div>  
	</div> 
	<div class="clear"></div>
	<div class="main_bottom1"></div>
</div>
<!--主体 结束-->
<!--版权 开始-->
<div class="footer"> </div>
<!--版权 结束-->
</body>
</html>