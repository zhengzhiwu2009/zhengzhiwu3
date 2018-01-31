<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="../entity/function/css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
 function sub(){
  if(loreDirForm.all.title.value==""){
   alert("请输入标题");
   return;
  }
  if(loreDirForm.all.sendIds.value==""){
   alert("请添加收件人,添加多个收件人时，请以英文分号“;”隔开");
   return;
  }
  if(loreDirForm.all.content.value==""){
   alert("请添加内容");
   return;
  }
   if(loreDirForm.all.title.value.length>50){
   alert("标题过长请不要超过50个字");
   return;
  }
  if(loreDirForm.all.content.value.length>2000){
   alert("标题过长请不要超过2000个字");
   return;
  }
 loreDirForm.all.su.disabled="false";
   loreDirForm.submit();
 }
  function addstu(){
  loreDirForm.action="<%=request.getContextPath()%>/siteEmail/editEmail_addStudent.action"
  loreDirForm.submit();
 }
</script>
</head>

<body leftmargin="0" topmargin="0"  background="../entity/function/lore/images/bg2.gif">
<form name="loreDirForm" action="<%=request.getContextPath()%>/siteEmail/editEmail_sendexe.action" method="post" onsubmit="return pageGuarding();">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<s:token></s:token>
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
		                                  <td valign="top"><s:if test="getSiteEmail().title!=''"><s:property value="getSiteEmail().title"/></s:if><s:else><input name="title" type="text" size="50" maxlength="50" value="<s:property value='getTitle()'/>"></s:else></td>		                                  
		                                </tr>
	                            	</table>
	                            	</td>
	                           
								
                          		</tr>
                                <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="../entity/function/lore/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top"><label  title="多个联系人请用英文分号“;”分隔">收件人</label></td>
		                                  <td valign="top"><s:if test="getSiteEmail().senderName!=''"><s:property value="getSiteEmail().senderName"/></s:if><s:else><input title="多个联系人请用英文分号“;”分隔" name="sendIds" type="text" size="50"  value='<s:property value='getLogId()'/>'><a href="#"  class="button" onclick="addstu()"><span unselectable="on">添加收件人</span></a></s:else></td>		                                  
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
		                                  <td valign="top"><textarea name="content" rows="10" cols="50" ><s:if test="getContent()!=''&&getContent()!=null"><s:property value="getContent()"/></s:if><s:else><s:property value="getSiteEmail().content"/></s:else></textarea></td>		                                  
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
		                                  <td><input type="button" value="发送" onclick="sub()" name="su"></td>		                                  
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
                      <td align="center"> </td>
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
