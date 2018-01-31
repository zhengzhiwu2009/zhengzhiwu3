<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>




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
</style>
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" ></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="/entity/function/images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>报名条件</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/table_02.gif" >
                      <table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" class="bg4" bgcolor="#D7D7D7">
					      <tr>
					        <td align="center" bgcolor="#E9F4FF" class="STYLE1" height="45">批次所属学期：<s:if test="peBzzStudent.peBzzBatch.name == null "></s:if>
					            <s:property value="peBzzStudent.peBzzBatch.name"/>  </td>
					      </tr> 
					         <tr>
						  		 <td align="center" bgcolor="#E9F4FF" class="STYLE1" height="45">未通过审核条件，尽快完成基础课程、在线作业、在线自测和课程评估</td><br/>
						   </tr>
						    
						   <tr>
						  		 <td align="center" bgcolor="#E9F4FF" class="STYLE1" height="60"><font color='red'>原因：</font><br/><br/>
						   <font color='red'><s:property value="reason"/></font></td><br/>
						   </tr>
						  <tr>
						    <td align="center" bgcolor="#E9F4FF" class="STYLE1"><a href="/entity/workspaceStudent/bzzstudent_examBat.action">返回</a></td>
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