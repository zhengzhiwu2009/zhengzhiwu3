<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.standard.interaction.answer.OracleQuestion"/>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%! 
	String fixNull(String str){
		if(str==null || str.equalsIgnoreCase("null")) str = "";
		return str.trim();
	}
%>

<%
	ResourceFactory resFactory = ResourceFactory.getInstance();
	BasicResourceManage resManage = resFactory.creatBasicResourceManage();
	
	UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	if(us!=null)
	{
	}
	else
	{
	%>
	<script>
		window.alert("长时间未操作或操作异常，为了您的账号安全，请重新登录！");
		window.close();
		//window.location = "/";
	</script>
	<%
	return;
	}
	
%>

<html>
<head>
<title><s:property value="peBzzFeedbackInfo.title"/></title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif" style='overflow:scroll;overflow-x:hidden'>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3">
          	<table width="100%" border="0" cellspacing="0" cellpadding="0">              
              <tr>
                <td align="center" valign="top">
                <table width="680" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              	<tr>
              	<td bgcolor="#FFFFFF">
              	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="60" valign="bottom" background="/entity/function/images/answer_01.gif"> 
                        <table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td align="center" valign="bottom"> 
                            	<table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                	<td valign="top"><img src="/entity/function/images/answer_xb.gif" width="15" height="16"></td>
                            		<td class="text4" style="padding-left:8px">标题: <s:property value="peBzzFeedbackInfo.title"/></td>
  								</tr>
								</table>
                            </td>
                          </tr>
                          <tr align="center"> 
                            <td class="mc1">反馈者：<font color="#FF0000"><s:property value="peBzzFeedbackInfo.author"/></font> 
                              反馈时间：<s:date name="peBzzFeedbackInfo.addDate" format="yyyy-MM-dd hh:mm:ss"/>,联系方式：<s:property value="peBzzFeedbackInfo.phone"/></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_02.gif" width="680" height="10"></td>
                    </tr>
                    <tr> 
                      <td valign="top" background="/entity/function/images/answer_03.gif">
                      <table width="94%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="22%"><img src="/entity/function/images/answer_04.gif" width="132" height="93"></td>
                            <td valign="top" class="text2">反馈内容：
                            <s:property escape='0' value="peBzzFeedbackInfo.content"/></td>
                          </tr>
                        </table></td>
                    </tr>
                    
    <s:if test="peBzzReplyInfoList.size() > 0">
   <s:iterator value="peBzzReplyInfoList" id="course" status='stuts'>
                    <tr> 
                      <td valign="top" background="/entity/function/images/answer_03.gif">
                      <table width="94%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td rowspan='2' width="22%"><img src="/entity/function/images/answer_04.gif" width="132" height="93"></td>
                            <td valign="top" class="text2">&nbsp;&nbsp;
                            <s:property value="author"/>&nbsp;&nbsp;于：<s:date name="addDate" format="yyyy-MM-dd hh:mm:ss"/>，对您的反馈做了如下回复：</td>
                          </tr>
                          <tr>
                            <td valign="top" class="text2"><s:property escape='0' value="content"/></td>
                          </tr>
                        </table></td>
                    </tr>
    </s:iterator>
  </s:if>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_05.gif" width="680" height="12"></td>
                    </tr>
                    <tr> 
                      <td height="23" align="center" background="/entity/function/images/answer_03.gif"></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_07.gif" width="680" height="19"></td>
                    </tr>
                   
                    <tr align="center"><td> <input type="button" value="关闭" onclick="window.close()"> </td></tr>
                  </table></td>
                    </tr>
                  </table> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>
