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
        <td height="17" align="left" class="twocentop">	<div class="content_title"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" />当前位置：确认信息</div>
	</td>
      </tr>
      <tr>
      	<td>&nbsp;</td>
      </tr>
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
       <table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:15px 0px 15px 0px;">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
      <tr bgcolor="#ECECEC">
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>学    号：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" width=300> &nbsp; <s:property value="peBzzStudent.getRegNo()"/></td>
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
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">通信地址：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <s:if test="peBzzStudent.peEnterprise.address == null "></s:if>
                                  		<s:property value="peBzzStudent.peEnterprise.address"/>  </td>
      </tr>
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
      
    </table></td>
  </tr>
</table>
<table border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    <input type="button" value="确认信息"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toLate.action'"/>
    </td>
	<td width="20"></td>
    <td>
    <input type="button" value="修改个人信息"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toLateModifyInfo.action'"/>
    </td>
    <td width="20"></td>
    <td>
    <input type="button" value="返回"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toLateConditionView.action'"/>
    </td>
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