<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.sso.web.action.SsoConstant, com.whaty.platform.sso.web.servlet.UserSession;"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="../entity/function/css/css.css" rel="stylesheet" type="text/css">
	<% UserSession su=(UserSession)request.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);%>
<script type="text/javascript">
	function closeWin(roleId) {
		if (roleId=='0') {
			window.location.href("/entity/workspaceStudent/studentWorkspace_tositeemail.action");
		} else {
			window.close();
		}
		
	}
</script>
</head>

<body leftmargin="0" topmargin="0"  background="../entity/function/lore/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="../entity/function/lore/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="../entity/function/lore/images/wt_02.gif" width="78" height="52"></td>
                            <td width="100" valign="top" class="text3" style="padding-top:25px">站内信箱</td>
                            <td background="../entity/function/lore/images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><img src="../entity/function/lore/images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  	<tr>
                  		<td><img src="../entity/function/lore/images/wt_04.gif" width="604" height="13"></td>
                    </tr>
                    <tr>
                    	<td background="../entity/function/lore/images/wt_05.gif">
                    	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td><img src="../entity/function/lore/images/wt_08.gif" width="572" height="11"></td>
                          	</tr>
                          	<tr>
                            	<td background="../entity/function/lore/images/wt_10.gif">
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                            	 <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="../entity/function/lore/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">标题</td>
		                                  <td valign="middle"><s:property value="getSiteEmail().title"/></td>		                                  
		                                </tr>
	                            	</table>
	                            	</td>
	                           
								
                          		</tr>
                                <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="../entity/function/lore/images/ggzt.gif" width="20" height="32"></td>
		                                  <s:if test="getTemp()==1">
				                          <td width="80" class="text3" valign="top">发件人</td>
		                                  <td valign="middle"><s:property value="getSiteEmail().senderName"/></td>
		                                  </s:if>
		                                  <s:else>
		                                    <td width="80" class="text3" valign="top">收件人</td>
		                                  <td valign="middle"><s:property value="getSiteEmail().addresseeName"/></td>
		                
		                                  </s:else>		                                  
		                                </tr>
	                            	</table>
	                            	</td>
	                           
								
                          		</tr>
                          		               <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="../entity/function/lore/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">时间</td>
		                                  <td valign="middle"><s:date name="getSiteEmail().senddate" format="yyyy年MM月dd日 HH:mm:ss"/></td>		                                  
		                                </tr>
	                            	</table>
	                            	</td>
	                           
						
                          		</tr>
                         
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="../entity/function/lore/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">内容:</td>
		                                  <td valign="top"><textarea name="content" rows="10" cols="50" readonly="readonly" ><s:property value="getSiteEmail().content"/></textarea></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                         
                          		<br/>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"></td>
				                          <td width="80" class="text3"></td>
				                          <%
				                            if(!("0".equals(su.getRoleId()))&&"1".equals(request.getParameter("temp"))){
				                          %>
				                          <td>
				                          <form action="<%=request.getContextPath() %>/siteEmail/recipientEmail_EmailInfo.action" method="post">
				                          <input type=hidden name="id" value=<s:property value="getSiteEmail().id"/>>
				                          <input type=hidden name="temp" value='re'>
		                                  <input type="submit" value="回复"></td>	
		                                  </form>
		                                  <% }%>	                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                              	</table>
                              	</td>
                          	</tr>
                          <tr>
                            <td><img src="../entity/function/lore/images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table>
                        </td>
                    </tr>
                    <tr>                            
                      <td><img src="../entity/function/lore/images/wt_06.gif" width="604" height="11"></td>
                    </tr>
                    <tr>
                      <td align="center"><a href="#" class="tj" onclick="closeWin('<%=su.getRoleId() %>');">[关闭]</a> </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br/>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>



</body>
</html>
