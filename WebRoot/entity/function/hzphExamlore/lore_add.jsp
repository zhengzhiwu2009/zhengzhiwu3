<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加知识点</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all"
	href="../../../js/calendar/calendar-win2000.css">
<script type="text/javascript" src="../../../js/calendar/calendar.js"></script>
<script type="text/javascript" src="../../../js/calendar/calendar-zh_utf8.js"></script>
<script type="text/javascript" src="../../../js/calendar/calendar-setup.js"></script>
<script type="text/javascript">
	function pageGuarding() {
		if(document.loreForm.name.value.replace(/[ ]/g,"") == "") {
			alert("知识点名称不能为空！");
			document.loreForm.name.focus();
			return false;
		}
		if(document.loreForm.creatDate.value == "") {
			alert("创建日期不能为空！");
			return false;
		}
		if(document.loreForm.content.value.replace(/[ ]/g,"") == "") {
			alert("知识点内容不能为空！");
			document.loreForm.content.focus();
			return false;
		}
		
		return true;
	}
	
  function checkMaxLen(obj,maxlength){
   if(obj.value.length>maxlength){
     obj.value = obj.value.substr(0,maxlength);
   }
  }
</script>
</head>

<%
	String loreDirId = request.getParameter("loreDirId");
	String today;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	today = sdf.format(new Date());
	 
%>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<form name="loreForm" action="lore_addexe.jsp" method="post" onsubmit="return pageGuarding();">
<INPUT type="hidden" name="loreDirId" value=<%=loreDirId%>>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zsd.gif" width="217" height="86"></td>
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
                            <td width="100" valign="top" class="text3" style="padding-top:25px">添加知识点</td>
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
				                          <td width="80" class="text3" valign="top">名称:<font color=red>*</font></td>
		                                  <td valign="top"><input name="name" type="text" size="30" maxlength="20"></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
                          		<td>
                          			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">考点描述:<font color=red>*</font></td>
		                                  <td valign="top"><textarea name="discription" cols="40" ></textarea></td>		                                  
		                                </tr>
	                            	</table>
                          		</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3">创建日期:<font color=red>*</font></td>
		                                  <td>
		                                  	<span id="showCreatDate" style="width:135px; font-size: 15px; padding-top:0px; padding-bottom:1px;"><%=today %></span> 
		                                  	<input name="creatDate" id="creatDate" type="hidden" value="<%=today %>"/>
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          <!--  <tr style="display:none">
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">内容:<font color=red> <br/>(选填项，请控制在150字以内)</font></td>
		                                  <td valign="top"><textarea name="content" rows="10" cols="49"  onpropertychange="checkMaxLen(this,150)">默认内容</textarea></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>-->	
                          		<input type="hidden" name="active" value='1'>
                          		<%--
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">是否激活:</td>
		                                  <td valign="top">
		                                  	<INPUT type="radio" name="active" value="1" checked>是&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="active" value="0">否
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		--%>
                          		<br/>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"></td>
				                          <td width="80" class="text3"></td>
		                                  <td><input type="submit" value="添加"></td>		                                  
		                                </tr>
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
                      <td align="center"><a href="lore_dir_list.jsp?loreDirId=<%=loreDirId%>" class="tj">[返回]</a> </td>
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
