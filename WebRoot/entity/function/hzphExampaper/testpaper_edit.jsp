<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改试卷</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<%
	String id = request.getParameter("id");
	String pageInt = request.getParameter("pageInt");
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	TestPaper testPaper = testManage.getTestPaper(id);
	String notes=testPaper.getNote();
	if(notes!=null&&!notes.equals("")){
	}else{
	notes="";
	}
	
	//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
%>
<script>
	function del(){
		if(confirm("是否确定删除？"))
			window.navigate("testpaper_delete.jsp?id=<%=testPaper.getId()%>&pageInt=<%=pageInt%>");
	}
</script>
<script type="text/javascript" src="checkform.js"></script>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<form name="paperForm" action="testpaper_editexe.jsp" method="post" onsubmit="return checkAll('title,time')">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/khcy.jpg" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="images/wt_02.gif" width="78" height="52"></td>
                            <%if("teacher".equalsIgnoreCase(userType)&&us.getRoleId().equals("3")) {%>
                            <td width="120" valign="top" class="text3" style="padding-top:25px">修改试卷基本信息</td>
                            <%} 
                            else { %>
                            <td width="120" valign="top" class="text3" style="padding-top:25px">查看试卷基本信息</td>
                            <%} %>
                            <td background="images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><img src="images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  	<tr>
                  		<td><img src="images/wt_04.gif" width="604" height="13"></td>
                    </tr>
                    <tr>
                    	<td background="images/wt_05.gif">
                    	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td><img src="images/wt_08.gif" width="572" height="11"></td>
                          	</tr>
                          	<tr>
                            	<td background="images/wt_10.gif">
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">标题:</td>
		                                  <td valign="top">
		                                  <input name="title" id="title" min="3" max="50" msg="标题长度应在3-50字符" type="text" size="30" maxlength="50" value="<%=testPaper.getTitle()%>">
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr style="display:none">
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3">测验时限:</td>
		                                  <td class="text1"><input name="time" id="time" min="1" max="17" msg="时限不合法,只能为数字" type="text" size="30" maxlength="50" value="999">分钟（请填写数字）</td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">说明:</td>
		                                  <td valign="top"><textarea name="note"  id="note" rows="10" cols="50" min="3" max="50" msg="说明长度应在3-50字符"><%=notes%></textarea></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">是否激活:</td>
		                                  <td valign="top" class="text1">
		                                  	<INPUT type="radio" name="status" value="1" <%if(testPaper.getStatus().equals("1"))out.print("checked");%>>是&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="status" value="0" <%if(testPaper.getStatus().equals("0"))out.print("checked");%>>否
		                                  </td>		                                  
		                                </tr>
	                            	</table> 
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">组卷方法:</td>
		                                  <td valign="top" class="text1">
		                                  	<INPUT type="radio" name="status" value="1" <%if(testPaper.getPaper_fun() !=null && testPaper.getPaper_fun().equals("1")) out.print("checked");%>>已使用组卷策略&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="status" value="0" <%if(testPaper.getPaper_fun() !=null && testPaper.getPaper_fun().equals("0"))out.print("checked");%>>手工组卷
		                                  </td>		                                  
		                                </tr>
	                            	</table> 
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                            
	                            	</table>
									</td>
                          		</tr>
                              	</table>
                              	</td>
                          	</tr>
                          <tr>
                            <td><img src="images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table>
                        </td>
                    </tr>
                    <tr>                            
                      <td><img src="images/wt_06.gif" width="604" height="11"></td>
                    </tr>
                    <tr>
                      <td align="center">
                       <%if("teacher".equalsIgnoreCase(userType)&&us.getRoleId().equals("3")) {%>
		                                 
		                                  <input type="hidden" name="id" value="<%=testPaper.getId()%>">
		                                  <input type="hidden" name="pageInt" value="<%=pageInt%>">
		                                  <input type="submit" value="修改">
		                                  <!-- input type="button" value="删除" onclick=del();-->
		                        
		                                  <%}%>
		                                 <button onclick="javascript:window.history.back()">返回</button>
                    <!--  <a href="testpaper_list.jsp?pageInt=<%=pageInt%>" class="tj">[返回]</a> -->  </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br/>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>
</form>
</body>
</html>
