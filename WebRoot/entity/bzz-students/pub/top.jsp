<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder, com.whaty.platform.config.*" %>
<%@ page import ="com.whaty.platform.config.ForumConfig" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

ForumConfig forumConfig = (ForumConfig)application.getAttribute("forumConfig");
	
String forumbasePath =forumConfig.getForum_Url();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中国证劵业协会培训平台</title>
<link href="/web/bzz_index/style/index.css" rel="stylesheet" type="text/css">
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
</head>

<body style="margin:0;overflow:auto;" onLoad="MM_preloadImages('/web/bzz_index/images/topover_18.jpg','/web/bzz_index/images/topover_06.jpg','/web/bzz_index/images/topover_08.jpg','/web/bzz_index/images/topover_10.jpg','/web/bzz_index/images/topover_12.jpg','/web/bzz_index/images/topover_14.jpg','/web/bzz_index/images/topover_16.jpg','/web/bzz_index/images/topover_20.jpg')">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td><img width="100%" src="/web/bzz_index/images/top_pic.jpg"  height="153"></td>
  </tr>
</table>
</body>
</html>
