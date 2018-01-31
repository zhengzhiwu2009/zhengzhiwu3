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
function goToMail(){
	var email = "<s:property value='email' />";
	var emailHost = email;
	if(email != ''){
		var parts = email.split("@");
		emailHost = "http://mail." + parts[parts.length-1];
		//alert(email);
		//document.location.href = emailHost;
		window.open(emailHost);
	}
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
		<ul class="main_form" style="margin-left:-120px;">
			<li>
				<div class="fm_left" ><img src="/web/bzz_index/password/images/email_send.png"/></div>
				<div class="fm_right">
				<p style="font-weight:bold">重置密码的邮件已发出，请24小时内登录邮箱 <s:property value="email" /> 查收。</p>
				<p>登录邮箱后，请按邮件提示进行操作  或者 <a href="/">返回首页</a> </p>
				<br/>
				<p>
					没有收到邮件？
				</p>
					<div style="list-style-type: disc;color:#999;font-size:12px;">
						看看是否被误认为垃圾邮件放到垃圾箱了。<br/>
						如果超过10分钟您仍然未收到邮件，请您： <a href="/password/getPsw_resendEmail.action?verifystr=<s:property value='verifystr' />&email=<s:property value='email' />">重发一封</a>
					</div>
				</div>       
			</li>
			<li>
				<div class="fm_left">&nbsp;</div>
				<div class="fm_right"><span class="f_btn ">
				<input type="button" value="登录邮箱" class="r_btn w_85" onclick="goToMail()"/></span> 
					</div>
				</li>
			</ul>
			
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