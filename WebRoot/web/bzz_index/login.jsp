<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="oscache" prefix="cache" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD /xhtml1-transitional.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base href="<%=basePath%>">
<title>中国证劵业协会培训平台</title>
<link href="/web/bzz_index/style/index.css" rel="stylesheet" type="text/css">
<link href="/web/zhibo/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
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
//-->
</script>
<script type="text/javascript">
function myonload(){
MM_preloadImages('/web/bzz_index/images/anniu_over_03.jpg','/web/bzz_index/images/anniu_over_05.jpg');
<s:if test="loginErrMessage!=null&& loginErrMessage!='clear'">
	window.document.loginform.loginId.value="<s:property value='loginId'/>";
	window.alert("<s:property value='loginErrMessage'/>");
</s:if>
}

function goUrl()
{	
	window.open(document.form1.select.value,"newwindow");
}
function window.onunload() {
	if(window.screenLeft>10000|| window.screenTop>10000){ 
			var url="/sso/login_close.action?loginErrMessage=clear";
			if (window.XMLHttpRequest) {
		        req = new XMLHttpRequest();
		    }
		    else if (window.ActiveXObject) {
		        req = new ActiveXObject("Microsoft.XMLHTTP");
		    }
		    req.open("Get",url,true);
		    req.onreadystatechange = function(){
		    	if(req.readyState == 4);
		    };
	  		req.send(null);
  		}
}
</script>
</head>
<body onLoad="myonload()"> 
            <form id="loginform" name="loginform" method="post" action="/sso/login_login.action" target="_parent" >
              <table width="279" border="0" cellspacing="0" cellpadding="0" style="margin:0px 0px;">
              <tr>
              <td align="center" background="/web/bzz_index/images/left_12.jpg" style="padding:5px 0px;">
              <table width="78%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="27%" align="center" class="dengl_font">帐　号：</td>
                  <td width="73%" height="25">                    <input name="loginId" type="text" class="input">                </td>
                </tr>
                <tr>
                  <td align="center" class="dengl_font">密　码：</td>
                  <td height="28"><input name="passwd" type="password" class="input"></td>
                </tr>
                <tr>
                  <td align="center" class="dengl_font">验　证：</td>
                  <td height="25"><input name="authCode" type="text" class="input" style="width:96px;">  <img src="/sso/authimg" width="50" height="20" align="absmiddle" onclick="this.src=this.src+'?'+(new Date()).getTime()"></td>
                </tr>
				<tr>
				 <td colspan="2" align="center" height="35">
				 <input type="image" src="/web/bzz_index/images/denglu_out_05.jpg" width="84" height="25" hspace="5" vspace="5" border="0" id="Image2" onMouseOver="MM_swapImage('Image2','','/web/bzz_index/images/anniu_over_05.jpg',1)" onMouseOut="MM_swapImgRestore()"/>
                  <a href="/web/bzz_index/czmm/find.jsp" target="_blank"><img src="/web/bzz_index/images/denglu_out_03.jpg" width="84" height="25" hspace="5" vspace="5" border="0" id="Image1" onMouseOver="MM_swapImage('Image1','','/web/bzz_index/images/anniu_over_03.jpg',1)" onMouseOut="MM_swapImgRestore()"></a>
                  <br/>&nbsp;
                  </td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="14"><img src="/web/bzz_index/images/left_14.jpg" width="279" height="14"></td>
            </tr>
          </table>
            </form>
</body>
</html>