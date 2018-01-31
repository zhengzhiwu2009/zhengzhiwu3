<%@ page language="java" import="java.net.*,java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>


<%
String d=String.valueOf(Math.random());
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


response.addHeader("Cache-Control", "no-cache");
response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT"); 


%>

<%!
   String fixnull1(String str){ 
   		if(str==null || str.equals("null") || str.equals("")){
   			str= "";
   			return str;
   		}
   		return str;
   }
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<link href="/entity/bzz-students/exam/css/css1.css" rel="stylesheet" type="text/css" />
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
<script type="text/javascript">

function showDiv(objID){
		var tempObj;
		if(document.getElementById(objID)){	
		tempObj = document.getElementById(objID);
		if(tempObj.style.display == "none")	
		{
		tempObj.style.display = "block";}
		else{	
		tempObj.style.display = "none";
		}
		}
	}
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
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr><td height="25" valign="top" ></td></tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="/entity/function/images/table_01.gif">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>考试报名及缓考申请</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                       </table>
                       </td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/table_02.gif" >
                      <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                      	<tr>
					        <td align="center" class="text1"><strong>考试相关信息</strong></td>
					    </tr>
					  </table>
                      <table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" class="bg4" bgcolor="#D7D7D7">
                      	<tr>
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>报名开始时间：</strong></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzExamBatch.startEntryDate == null "></s:if><s:date name="peBzzExamBatch.startEntryDate" format="yyyy-MM-dd"/> </td>
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>到</strong></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzExamBatch.endEntryDate == null "></s:if><s:date name="peBzzExamBatch.endEntryDate" format="yyyy-MM-dd"/> </td>
					      </tr>
					       <tr>
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>缓考申请开始时间：</strong></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzExamBatch.startDelayDate == null "></s:if><s:date name="peBzzExamBatch.startDelayDate" format="yyyy-MM-dd"/> </td>
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>到</strong></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzExamBatch.endDelayDate == null "></s:if><s:date name="peBzzExamBatch.endDelayDate" format="yyyy-MM-dd"/> </td>
					      </tr>
					       <tr>
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>补考申请开始时间：</strong></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzExamBatch.againStartTime == null "></s:if><s:date name="peBzzExamBatch.againStartTime" format="yyyy-MM-dd"/> </td>
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>到</strong></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzExamBatch.againEndTime == null "></s:if><s:date name="peBzzExamBatch.againEndTime" format="yyyy-MM-dd"/> </td>
					      </tr>
				  </table>
				  <table border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
			      	<td>&nbsp;</td>
			      </tr>
			      <tr>
			      	<td align="left">
			      		您当前的考试状态是：<s:if test='flagExamState==1'><font color="red">未报名</font></s:if><font color="red"><s:if test='examNote==null&&flagExamAgain!=1&&flagExamLate!=1'>未报名</s:if>
			      		<s:if test='flagExamAgain==1'>您的补考申请已经通过审核，已经可以开始学习。</s:if>
			      		<s:if test='flagExamAgain==0'>补考申请成功，请等待审核</s:if>
			      		<s:if test='flagExamLate==1'>重新申请考试成功</s:if>
			      		<s:if test='flagExamLate==2&&flagExamAgain==2'>未报名</s:if>
			      		<s:if test='flagExamAgain==2'>未报名</s:if>
			      		<s:if test='flagExamAgain==3'>补考申请没有通过，<span onclick="showDiv('cnt1')" style="cursor: pointer"> 查看原因</span>
			      		<div id="cnt1" align="center" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none ;">
                <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
                 <tr>
                    <td height="22" align="center">
 					<s:property value="peBzzExamAgain.note"/><br /><br /><br /><br />
				</td>
                </tr> 
                <tr><td valign="bottom"  align="center"><input type='button' onclick="showDiv('cnt1')" value="确定"></td></tr>
                </table>
              </div>
			      		</s:if>
			      		&nbsp;&nbsp;&nbsp;&nbsp;
			      		 <s:if test='examNote=="已报名"'></font>您选择的考点是：<font color="red"><s:property value='peBzzExamScore.peBzzExamBatchSite.peBzzExamSite.name'/></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可在个人信息中修改考点</s:if>
			      	</td>
			      </tr>
			      <tr>
			      	<td>&nbsp;</td>
			      </tr>
			      <tr>
			      <td>
			      <s:if test='timeTemp==1'>
                     <input type="button" value="个人信息"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toExamModifyN.action'"/>
                  </s:if>
                  <s:elseif test='timeTemp==0'>
                     <input type="button" value="个人信息"  onClick="javascript:window.location ='/entity/workspaceStudent/bzzstudent_toExamInfo.action'" />
                  </s:elseif>
                  <s:if test='peBzzExamScore==null && peBzzExamLate==null'>
                      <s:if test='flagExamState!=1&&timeTemp==1&&flagExamAgain!=1&&flagExamLate!=1&&flagExamAgain!=0&&flagExamAgain!=2&&flagExamLate!=2'>
	                  <input type="button" value="考试报名"  onClick="javascript:window.location ='/entity/workspaceStudent/bzzstudent_toExamIntroduce.action'" />
	                  </s:if>
	                  <s:if test='flagExamState!=1&&lateTimeTemp==1&&flagExamAgain!=1&&flagExamLate!=1&&flagExamAgain!=0&&flagExamAgain!=2&&flagExamLate!=2'>
	                  <input type="button" value="缓考申请"  onClick="javascript:window.location ='/entity/workspaceStudent/bzzstudent_toLateConditionViewN.action'" />
	                  </s:if>
                  </s:if>
                  <s:elseif test='peBzzExamScore!=null && peBzzExamLate==null&&flagExamAgain!=1&&flagExamLate!=1&&flagExamAgain!=0&&flagExamAgain!=2&&flagExamLate!=2'>
	                   <input type="button" value="查看审核状态"  onClick="javascript:window.location ='/entity/workspaceStudent/bzzstudent_examstudentn.action'" />
	                  <s:if test='examTemp==0 && timeTemp==1'>
		                <input type="button" value="取消报名"  onClick="javascript:if(confirm('您确定要取消考试报名吗?'))window.location ='/entity/workspaceStudent/bzzstudent_cancelBao.action'" />
		              </s:if>
                  </s:elseif>
                  <s:elseif test='peBzzExamScore==null && peBzzExamLate!=null&&flagExamAgain!=1&&flagExamLate!=1&&flagExamAgain!=0&&flagExamAgain!=2&&flagExamLate!=2'>
	                   <input type="button" value="查看审核状态"  onClick="javascript:window.location ='/entity/workspaceStudent/bzzstudent_latestudentn.action'" />
	                  <s:if test='lateTemp==0 && lateTimeTemp==1'>
	                    <input type="button" value="取消缓考申请"  onClick="javascript:if(confirm('您确定要取消缓考申请吗?'))window.location ='/entity/workspaceStudent/bzzstudent_cancelLa.action'" />
	                  </s:if>
                  </s:elseif>
                  </td>
				 </tr>
</table>
				</td>
			  </tr>
              <tr>
		         <td><img src="/entity/function/images/table_03.gif" width="765" height="21"></td>
              </tr>
          </table>
         </td>
        </tr>
</table>
</td>
</tr>
</table>
</body>
</html>