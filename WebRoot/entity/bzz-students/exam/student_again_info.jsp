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
                              <strong>个人信息确认</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                       </table>
                       </td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/table_02.gif" >
				  <table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" class="bg4" bgcolor="#D7D7D7">
					      <tr bgcolor="#ECECEC">
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>学    号：</strong></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" width="300"> &nbsp; <s:property value="peBzzStudent.getRegNo()"/></td>
					        <td align="center" valign="middle" bgcolor="#FAFAFA" class="kctx_zi2" rowspan="6" width="120"><img src="/incoming/student-photo/<s:if test="peBzzStudent.photo != null"><s:property value="peBzzStudent.peBzzBatch.batchCode"/>/<s:if test="peBzzStudent.peEnterprise.peEnterprise != null"><s:property value="peBzzStudent.peEnterprise.peEnterprise.code"/></s:if><s:else><s:property value="peBzzStudent.peEnterprise.code"/></s:else>/<s:property value="peBzzStudent.photo"/>?timestamp=<%=new Date().getTime() %></s:if><s:else>noImage.jpg</s:else>" width='120' hight='174'/> </td>
					        </tr>
					        <tr>
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>姓    名：</strong></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:property value="peBzzStudent.getTrueName()"/> </td>
					        </tr>
					      <tr>
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>性    别：</strong></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzStudent.gender == null "></s:if>
					                                  		<s:property value="peBzzStudent.gender"/></td>
					      </tr> 
					      <tr>
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>出生日期：</strong></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzStudent.birthdayDate == null "></s:if><s:date name="peBzzStudent.birthdayDate" format="yyyy-MM-dd"/> </td>
					      </tr>
					      <tr>
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>民    族：</strong></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzStudent.folk == null "></s:if>
					                                  		<s:property value="peBzzStudent.folk"/></td>
					     </tr>
					     <tr>
					        <td align="center" bgcolor="#E9F4FF" class="STYLE1">学   历：</td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peBzzStudent.education == null "></s:if>
					                                  		<s:property value="peBzzStudent.education"/></td>
					      </tr>
					      <tr>
					        <td align="center" bgcolor="#E9F4FF" class="STYLE1">所在学期：</td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <s:if test="peBzzStudent.peBzzBatch.name == null "></s:if>
					                                  		<s:property value="peBzzStudent.peBzzBatch.name"/>  </td>
					      </tr>
					     <%--  <tr>
					        <td align="center" bgcolor="#E9F4FF" class="STYLE1">通信地址：</td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <s:if test="peBzzStudent.peEnterprise.address == null "></s:if>
					                                  		<s:property value="peBzzStudent.peEnterprise.address"/>  </td>
					      </tr>--%>
					      <tr>                            		
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2"><DIV align="center" class="STYLE1">办公电话：</DIV></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <s:if test="peBzzStudent.phone == null "></s:if>
					                                  		<s:property value="peBzzStudent.phone"/>  </td>
					     </tr>
					     <tr>                             		
					        <td align="center" bgcolor="#E9F4FF" class="STYLE1">移动电话：</td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <s:if test="peBzzStudent.mobilePhone == null "></s:if>
					                                  		<s:property value="peBzzStudent.mobilePhone"/>  </td>  
					      </tr>
					      <tr>
					       
					        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>所在企业：</strong></td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <s:if test="peBzzStudent.peEnterprise.name == null "></s:if>
					                                  		<s:property value="peBzzStudent.peEnterprise.name"/> </td>                        		
					      </tr>
					      <tr>
					        <td align="center" bgcolor="#E9F4FF" class="STYLE1">电子邮件：</td>
					        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <s:if test="peBzzStudent.email == null "></s:if>
					                                  		<s:property value="peBzzStudent.email"/>  </td>
					        </tr>
				  </table>
				  <table border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
			      	<td>&nbsp;</td>
			      </tr>
				  <tr>
				    <td>
				    <input type="button" value="修改个人信息"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toExamInfoModify.action'"/>
				    <input type="button" value="提交"  onClick="javascript:if(confirm('请确定您的个人信息都是正确无误的。如果都是正确的，请点击提交按钮进行报名。如果不正确，请点击取消进行修改个人信息。'))window.location = '/entity/workspaceStudent/bzzstudent_toExamAgainExe.action';"/>
				    <input type="button" value="返回"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toExamAgainIntroduce.action'"/>
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