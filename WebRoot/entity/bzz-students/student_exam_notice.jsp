<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String d=String.valueOf(Math.random());
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


response.addHeader("Cache-Control", "no-cache");
response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
<META HTTP-EQUIV="expires" CONTENT="0">


<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css1.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
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


function selectServer() {
	window.open ('<%=basePath%>training/student/course/serverSelect.jsp', 'newwindow', 
	'height=600, width=800, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
}
//-->
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {font-size: 12px; color: #0041A1; font-weight: bold; }
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
.kctx_zi1 {
	font-size: 12px;
	color: #0041A1;
}
.kctx_zi2 {
	font-size: 12px;
	color: #54575B;
	line-height: 24px;
	padding-top:2px;
}
-->
</style></head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=580 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp?d=<%=d %>" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop">	<div class="content_title"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" />当前位置：考试专栏</div>
	</td>
      </tr>
      <tr>
      	<td>&nbsp;</td>
      </tr>
      
      <tr>
            <td align="left">
            <table width="100%">
            <tr>
            	<td width="80%">
            		<a href="/entity/workspaceStudent/bzzstudent_toExamExplain.action"><img border="0" src="/entity/bzz-students/images/kssm1_on.jpg" width="124" height="25" name="Image6" onMouseOver="MM_swapImage('Image6','','/entity/bzz-students/images/kssm1__over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;<img border="0" src="/entity/bzz-students/images/jiantou.jpg" width="25" height="25"/>&nbsp;
            		<a href="/entity/workspaceStudent/bzzstudent_toExamNotice.action"><img border="0" src="/entity/bzz-students/images/kstz_on.jpg" width="124" height="25" name="Image7" onMouseOver="MM_swapImage('Image7','','/entity/bzz-students/images/kstz_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;<img border="0" src="/entity/bzz-students/images/jiantou.jpg" width="25" height="25"/>&nbsp;
            		<a href="/entity/workspaceStudent/bzzstudent_examBatch.action"><img border="0" src="/entity/bzz-students/images/ksbm_on.jpg" width="144" height="25" name="Image1" onMouseOver="MM_swapImage('Image1','','/entity/bzz-students/images/ksbm_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;<img border="0" src="/entity/bzz-students/images/jiantou.jpg" width="25" height="25"/>&nbsp;
            		<a href="/entity/workspaceStudent/bzzstudent_toExamCard.action"><img border="0" src="/entity/bzz-students/images/zkzdy2_on.jpg" width="124" height="25" name="Image3" onMouseOver="MM_swapImage('Image3','','/entity/bzz-students/images/zkzdy2_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;<img border="0" src="/entity/bzz-students/images/jiantou.jpg" width="25" height="25"/>&nbsp;
            	</td>
            </tr>
            <tr>
            	<td width="80%">
              		<img border="0" src="/entity/bzz-students/images/jiantou.jpg" width="25" height="25"/>
              		<a href="/entity/workspaceStudent/bzzstudent_toExamTutor.action"><img border="0" src="/entity/bzz-students/images/fddy1_on.jpg" width="124" height="25" name="Image8" onMouseOver="MM_swapImage('Image8','','/entity/bzz-students/images/fddy1_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;<img border="0" src="/entity/bzz-students/images/jiantou.jpg" width="25" height="25"/>&nbsp;
              		<a href="/entity/workspaceStudent/bzzstudent_toSimulatedExam.action"><img border="0" src="/entity/bzz-students/images/zxmiks2_on.jpg" width="124" height="25" name="Image2" onMouseOver="MM_swapImage('Image2','','/entity/bzz-students/images/zxmiks2_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;<img border="0" src="/entity/bzz-students/images/jiantou.jpg" width="25" height="25"/>&nbsp;
              		<a href="/entity/workspaceStudent/bzzstudent_toStudentScore.action"><img border="0" src="/entity/bzz-students/images/cjcx2_on.jpg" width="144" height="25" name="Image4" onMouseOver="MM_swapImage('Image4','','/entity/bzz-students/images/cjcx2_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;<img border="0" src="/entity/bzz-students/images/jiantou.jpg" width="25" height="25"/>&nbsp;
             		<a href="/entity/workspaceStudent/bzzstudent_toCertificate.action"><img border="0" src="/entity/bzz-students/images/zsff2_on.jpg" width="124" height="25" name="Image5" onMouseOver="MM_swapImage('Image5','','/entity/bzz-students/images/zsff2_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;&nbsp;
           		</td>
            </tr>
            </table>
            </td>
          </tr>
      
       	  	<%--<tr ><td  align='center' colspan=2><font color=red size=10>考试报名</font></td></tr>--%>
      <!--     <tr>
          <td><br/><img name="two2_r5_c5" src="/entity/bzz-students/images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> </td>
      </tr>
   <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="5%" align="center"></td>
            <td width="45%" align="left">&lt;&lt; <font color="red"><s:property value="peBzzStudent.getTrueName()"/></font>&gt;&gt; 的个人信息: </td>
          </tr>
        </table></td>
      </tr>   -->
        <table width="80%" border="0" align="left" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:15px 0px 15px 0px;">

  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
      <tr bgcolor="#ECECEC">
        <td align="left" bgcolor="#E9F4FF" class="kctx_zi1"><s:property value="peBzzExamBatch.exam_notice"  escape="false"/></td>
        </tr>
    </table></td>
  </tr>
</table>
    </table></td>
    <td width="13">&nbsp;</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
</tr>
</table>
</body>
</html>