<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.util.string.*,com.whaty.platform.util.*"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.standard.interaction.answer.OracleQuestion"/>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<%@ page import="com.whaty.util.string.*"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<%@taglib uri="/struts-tags" prefix="s" %>


<%
	//gaoyuan 09-09-01 begin
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
	
	/*String id = request.getParameter("resId");
	Resource res = resManage.getResource(id);
	List contentList = res.getResourceContentList();
	ResourceContent detail = (ResourceContent)contentList.get(0);
	String xml=detail.getContent();
	xml = com.whaty.util.string.encode.HtmlEncoder.decode(xml);
	List list = XMLParserUtil.parserFaqDetail(xml);
	String author_temp = (String)list.get(0);// System.out.println("author_temp->"+author_temp);
	String question_id = (String)list.get(2);
	String body = new OracleQuestion(question_id).getBody();
	String time = (String)list.get(1); */
 
%>

<html>
<head>
<title>清华大学继续教育学院</title>
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
                            		<td class="text4" style="padding-left:8px">反馈: </td>
  								</tr>
								</table>
                            </td>
                          </tr>
                          <tr align="center"> 
                            <td class="mc1">作者：<font color="#FF0000"><s:property value="peStudent.trueName"/></font> 
                              发布时间：<s:date name="peBzzFeedbackInfo.addDate" format="yyyy-MM-dd HH:mm:ss"/></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_02.gif" width="680" height="10"></td>
                    </tr>
                    <tr> 
                      <td valign="top" background="/entity/function/images/answer_03.gif"><table width="94%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="22%"><img src="/entity/function/images/answer_04.gif" width="132" height="93"></td>
                            <td valign="top" class="text2"><s:property value="peBzzFeedbackInfo.content"/></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_05.gif" width="680" height="12"></td>
                    </tr>
                    <tr> 
                      <td height="23" align="center" background="/entity/function/images/answer_03.gif"></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_07.gif" width="680" height="19"></td>
                    </tr>
                    <tr>
                      <td align="center">
                      <table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td align="center" valign="bottom"> 
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td valign="top"><img src="/entity/function/images/answer_xb.gif" width="15" height="16"></td>
                                  <td class="text4" style="padding-left:8px">回复</td>
                                </tr>
                            </table>
                            </td>
                          </tr>
                      </table>
                      </td>
                    </tr>
                    
<s:if test="null != peBzzReplyInfoList && peBzzReplyInfoList.size()>0">
	<s:iterator value="peBzzReplyInfoList" id="peBzzReplyInfo">
                    <tr>
                      <td align="center">
                      <table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr align="center"> 
                            <td class="mc1">作者：<font color="#FF0000"><s:property value="#peBzzReplyInfo.author"/></font> 
                              回复时间：<s:date name="#peBzzReplyInfo.addDate" format="yyyy-MM-dd HH:mm:ss"/></td>
                          </tr>
                          <tr align="center"> 
                            <td class="mc1" colspan="2"><s:property value="#peBzzReplyInfo.content"/></td>
                          </tr>
                      </table>
                      </td>
                    </tr>
                    <tr>
                      <td valign="top" class="bg4">
						<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td class="text2"></td>
                          </tr>           
                          <tr> 
                            <td height="9" background="/entity/function/images/answer_09.gif"> 
                            
                            </td>
                          </tr>
                        </table>
                        <br/>
                      </td>
					</tr>
			</s:iterator>
</s:if>
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
