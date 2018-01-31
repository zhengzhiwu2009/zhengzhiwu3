<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.text.*"%>
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
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
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
            		<a href="/entity/workspaceStudent/bzzstudent_toExamNotice.action"><img border="0" src="/entity/bzz-students/images/kstz2_on.jpg" width="124" height="25" name="Image7" onMouseOver="MM_swapImage('Image7','','/entity/bzz-students/images/kstz1_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;<img border="0" src="/entity/bzz-students/images/jiantou.jpg" width="25" height="25"/>&nbsp;
            		<a href="/entity/workspaceStudent/bzzstudent_examBatch.action"><img border="0" src="/entity/bzz-students/images/ksbm2_on.jpg" width="144" height="25" name="Image1" onMouseOver="MM_swapImage('Image1','','/entity/bzz-students/images/ksbm2_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;<img border="0" src="/entity/bzz-students/images/jiantou.jpg" width="25" height="25"/>&nbsp;
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
       <table width="75%" border="0" align="left" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:15px 0px 15px 0px;">

  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
      <!-- <tr bgcolor="#ECECEC">
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>批次名称：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" width=300> &nbsp; <s:property value="peBzzExamBatch.name"/></td>
        </tr>
         <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">所在学期：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <s:if test="peBzzExamBatch.peBzzBatch.name == null "></s:if>
                                  		<s:property value="peBzzExamBatch.peBzzBatch.name"/>  </td>
      </tr> -->
      <s:if test="peBzzExamBatch == null">
      <tr>
      	<td>
      	<font color='red'>考试报名还未开始，请注意留意考试通知！</font>
      	</td>
      </tr>
      </s:if>
      <s:else>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>报名开始时间：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzExamBatch.startEntryDate == null "></s:if><s:date name="peBzzExamBatch.startEntryDate" format="yyyy-MM-dd"/> </td>
      </tr>
       <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>报名结束时间：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzExamBatch.endEntryDate == null "></s:if><s:date name="peBzzExamBatch.endEntryDate" format="yyyy-MM-dd"/> </td>
      </tr>
       <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>缓考申请开始时间：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzExamBatch.startDelayDate == null "></s:if><s:date name="peBzzExamBatch.startDelayDate" format="yyyy-MM-dd"/> </td>
      </tr>
       <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>缓考申请结束时间：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzExamBatch.endDelayDate == null "></s:if><s:date name="peBzzExamBatch.endDelayDate" format="yyyy-MM-dd"/> </td>
      </tr>
       <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>考试开始时间：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzExamBatch.startExamDate == null "></s:if><s:date name="peBzzExamBatch.startExamDate" format="yyyy-MM-dd"/> </td>
      </tr>
       <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>考试结束时间：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzExamBatch.ednExamDate == null "></s:if><s:date name="peBzzExamBatch.ednExamDate" format="yyyy-MM-dd"/> </td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>基础课学习进度要求：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp;<s:property value="peBzzExamBatch.time"/>% </td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>作业是否必须全部完成：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp;<s:property value="peBzzExamBatch.enumConstByFlagExamConditionHomework.name" /> </td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>在线自测是否必须全部完成：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp;<s:property value="peBzzExamBatch.enumConstByFlagExamConditionTest.name" /> </td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>课程评估是否必须全部完成：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:property value="peBzzExamBatch.enumConstByFlagExamConditionEvaluate.name" /> </td>
      </tr>
    </table>
    </td>
    </tr>
    <tr>
    <td>
    
  
<table border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    <s:if test="peBzzExamScore != null && operateresult == 2">
    	<input type="button" value="修改报名信息"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_studentIn.action'"/>
    	<input type="button" value="取消报名"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_cancelBaoming.action'"/>
    </s:if>
    <s:if test="peBzzExamScore != null && operateresult == 1">
     <font color='red'>不在报名时间范围内！</font>
    </s:if>
    <s:elseif test="peBzzExamLate != null && examtemp == 3" >
    	<input type="button" value="查看缓考申请状态"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_latestudentInfo.action'"/>
    	<input type="button" value="开始报名"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toScore.action'"/>
    </s:elseif>
    
     <s:elseif test="peBzzExamLate != null && examtemp == 1 && operateresult == 2" >
    	<input type="button" value="查看缓考申请状态"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_latestudentInfo.action'"/>
    	<input type="button" value="取消缓考申请"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_cancelLate.action'"/>
    	<input type="button" value="打印缓考申请表"  onClick="window.open('/entity/bzz-students/print_late_apply.jsp?student_id=<s:property value="peBzzStudent.id" /> ') "/>
    </s:elseif>
    <s:elseif test="peBzzExamLate != null && examtemp == 1 && operateresult ==1" >
    	<input type="button" value="查看缓考申请状态"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_latestudentInfo.action'"/>
    </s:elseif>
    
    <s:elseif test="peBzzExamLate != null && examtemp == 2" >
    	<input type="button" value="查看缓考申请状态"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_latestudentInfo.action'"/>
    </s:elseif>
    
     <s:elseif test="peBzzExamScore == null && peBzzExamLate == null">
    	<input type="button" value="开始报名"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toScore.action'"/>
    	<input type="button" value="缓考申请"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toLateConditionView.action'"/>
    </s:elseif>
    </td>
    </s:else>
<!--  	<td width="20"></td>
    <td><a href="/entity/workspaceStudent/bzzstudent_toModifyInfo.action"><img src="/entity/bzz-students/images/two/an_03_1.gif"  border="0"></a></td>
-->  </tr>
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