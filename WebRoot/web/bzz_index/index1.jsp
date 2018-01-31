<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证券协会远程培训系统</title>
		<link href="/web/bzz_index/style/login.css" rel="stylesheet"
			type="text/css" />
		<script language="JavaScript" type="text/javascript"
			src="/web/bzz_index/base64.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="/web/bzz_index/md5.js"></script>
		<script type="text/javascript">
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
</script>
		<script type="text/javascript">
function check(){
	var loginform = document.getElementById("loginform");
	var loginId = document.getElementById("loginId").value;
	var passwd = document.getElementById("passwd").value;
	var authCode = document.getElementById("authCode").value;
	var authCode0 = document.getElementById("authCode0").value;
	if(loginId.length<1){
		alert("用户名不能为空!");
		loginform.loginId.focus();
		window.event.returnValue = false;
		return false;
	}
	if(passwd.length<1){
		alert("密码不能为空!");
		loginform.passwd.focus();
		window.event.returnValue = false;
		return false;
	}
	if(authCode.length<1 && authCode0.length<1){
		alert("验证码不能为空!");
		window.event.returnValue = false;
		return false;
	}
	var str = base64encode(passwd);
	document.getElementById("passwd").value = str;
	loginform.action="/sso/login_login.action";
	loginform.submit();
}
function logout(){
	var req;
	var url="/sso/login_close.action?loginErrMessage=clear&date="+new Date();
	if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("GET",url,true);
    req.onreadystatechange = function(){
    	if(req.readyState == 4){
	    	window.top.navigate("/");
    	}
    };
 		req.send(null);
}
document.onkeydown = function(evt){
var evt = window.event?window.event:evt;
if(evt.keyCode==13)
{
	//document.forms["myform"].submit();
	return check();
}
}
</script>
		<script language="javascript">

function myonload(){
MM_preloadImages('/web/bzz_index/images/anniu_over_03.jpg','/web/bzz_index/images/anniu_over_05.jpg');
<s:if test="loginErrMessage!=null&& loginErrMessage!='clear'">
	window.document.loginform.loginId.value="<s:property value='loginId'/>";
	window.alert("<s:property value='loginErrMessage'/>");
</s:if>

	var loginInput = document.getElementById("loginId");
	if(loginInput){
		loginInput.focus();
	}
}
function createCode(){
 	 var img = document.getElementById("lyt_img_id");
 	 img.src = '/sso/authimg?flag=0&'+(new Date()).getTime();
}
function createCodeJt(){
 	 var img = document.getElementById("lyt_img_id_jt");
 	 img.src = '/sso/authimg?'+(new Date()).getTime();
}
/*验证码刷新 */
function chk_image() {
	var img = document.getElementById("checkCode");
	var d = new Date(); 
	img.src = "/sso/authimg?"+d.toString(38); 
	return true;
}    

/*Lee 调用验证码图片单击事件*/
function chk_code(){
	document.getElementById("chk_img").click();
}
function reset(){
	document.getElementById("loginId").value = "";
	document.getElementById("passwd").value = "";
	chk_code();
}
function changeCodeDiv(op){
	if(op == 1){
		document.getElementById("ac").style.display = "block";
		document.getElementById("ac0").style.display = "none";
	}else{
		document.getElementById("ac0").style.display = "block";
		document.getElementById("ac").style.display = "none";
	}
}
</script>
	</head>
	<body onLoad="myonload();">
		<div class="mian">
			<div class="back">
				<form action="" method="post" name="loginform" id="loginform"
					style="padding: 0px">
					<div class="name">
						<div class="font">
							用户名:
							<input name="loginId" id="loginId" type="text"
								style="width: 200px" value="" />
						</div>
						<div class="font">
							密&nbsp;&nbsp;&nbsp;码:
							<input name="passwd" id="passwd" type="password"
								style="width: 200px" value="" />
						</div>
						<div class="font">
							<div id="ac">
								验证码:<input id="authCode0" name="authCode0" type="text" size="4" maxlength="4" />
								<img src="/sso/authimg?flag=0" id="lyt_img_id"
									style="padding-top: 3px; width: 70px; height: 26px;"
									align="absmiddle" onclick="createCode()" />
							</div>
							<div id="ac0" style="display:none;">
								验证码:<input id="authCode" name="authCode" type="text" size="4" maxlength="4" />
								<img src="/sso/authimg" id='lyt_img_id_jt'
									style="padding-top: 3px; width: 70px; height: 26px;"
									align="absmiddle" onclick="createCodeJt()" />
							</div>
						</div>
						<div class="font">
							<input type="radio" name="loginType" value="2" checked
								onclick="changeCodeDiv(1)" />
							学员&nbsp;&nbsp;
							<input type="radio" name="loginType" value="0"
								onclick="changeCodeDiv(2)" />
							集体&nbsp;&nbsp;
							<input type="radio" name="loginType" value="3"
								onclick="changeCodeDiv(1)" />
							监管&nbsp;&nbsp;
							<input type="radio" name="loginType" value="1"
								onclick="changeCodeDiv(2)" />
							协会
						</div>
						<div>
							<div class="bont">
								<a href="#" onclick="check()" onmouseout="MM_swapImgRestore()"
									onmouseover="MM_swapImage('Image1','','/web/bzz_index/images/2_11.jpg',1)"><img
										src="/web/bzz_index/images/2_1.jpg" name="Image1" width="82"
										height="26" border="0" id="Image1" /> </a>
							</div>
							<div class="bont1">
								<a href="#" onclick="reset();" onmouseout="MM_swapImgRestore()"
									onmouseover="MM_swapImage('Image2','','/web/bzz_index/images/2_00.jpg',1)"><img
										src="/web/bzz_index/images/2_0.jpg" name="Image2" width="82"
										height="26" border="0" id="Image2" /> </a>
							</div>
						</div>
						<div
							style="clear: both; margin: 0px 20px 0px 0px; font-size: 12px; text-align: center;">
							版本：2.4.1
						</div>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>
