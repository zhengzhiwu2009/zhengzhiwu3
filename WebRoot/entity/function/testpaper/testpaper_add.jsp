<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加试卷</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script src="../js/check.js"></script>
<script>

	function KeyPress(objTR) {
		//只允许录入数据字符 0-9 和小数点 
			//var objTR = element.document.activeElement;		
		var txtval=objTR.value;	
		var key = event.keyCode;
	
		if((key < 48||key > 57)&&key != 46)	{		
			event.keyCode = 0;
			alert("请输入数字");
		} else {
			if(key == 46) {
				if(txtval.indexOf(".") != -1||txtval.length == 0)
					event.keyCode = 0;
			}
		}
	}
	function showPrivText(){
		var v = element.document.getElementById();
	}
	
	function checkNum(obj){
			if(document.getElementById("title").value.replace(/[ ]/g,"")==""){
				alert("标题为空");
				return false;
			}
			if (document.getElementById("note").value.replace(/[ ]/g,"")=="") {
				alert("内容为空");
				return false;
			}
			if(obj.zujuan.checked){
			
				 if(!isInt(obj.paper_num.value)){
					alert("组卷数量必须为数字");	
					obj.paper_num.value="3";
					return false;
				}
				if(obj.paper_num.value==""||obj.paper_num.value==null){
					alert("组卷数量必须为数字");
					obj.paper_num.value="3";
					return false;
				}
			}
	}
	
   function checkMaxLen(obj,maxlength){
	   if(obj.value.length>maxlength){
	     obj.value = obj.value.substr(0,maxlength);
	   }
  }
	
	function changeStatus(){
		var celue=document.getElementById("zujuan");
		var shougong=document.getElementById("shougong");
		var paperNum=document.getElementById("paper_num");
		
		if(celue.checked==true){
			document.getElementById("ssss").style.cssText ="display:block;";
		}else{
			paperNum.setAttribute("readonly","readonly");
			paperNum.value="";
			document.getElementById("ssss").style.cssText ="display:none;";
		}
	}	

	
</script>
<script type="text/javascript" src="checkform.js"></script>

<%
String title = request.getParameter("title");

InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
List testPolicyList = testManage.getPaperPolicys(null,null,title,null,null,null,null,"test",teachclass_id);
 %>

</head>

<body leftmargin="0" topmargin="0"  background="images/bg2.gif" >
<form name="paperForm" action="testpaper_addexe.jsp" method="post" onsubmit="return checkNum(this)">
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
                            <td width="100" valign="top" class="text3" style="padding-top:25px">添加试卷</td>
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
				                          <td width="80" class="text3" valign="top">标题:<font color=red>*</font></td>
		                                  <td valign="top" class="text1"><input name="title" id="title" min="3"  msg="标题长度应在3-50字符" type="text" size="30" maxlength="20"></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr style="visibility:hidden">
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3">测验时限:<font color=red>*</font></td>
		                                  <td class="text1"><input name="time" id="time" value="250"  min="1" max="17" msg="时限不合法,只能为数字" type="text" size="30" maxlength="50" >分钟（请填写数字）</td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">说明: <font color=red>*</font></td><br/><font>(请控制在150字以内)</font></td>
		                                  <td valign="top"><textarea name="note" id="note" rows="10" cols="49" min="3" max="50" msg="说明长度应在3-60字符"   onpropertychange="checkMaxLen(this,150)"></textarea></td>		                                  
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
		                                  	<INPUT type="radio" name="status" value="1" >是&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="status" value="0" checked>否
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
		                                  <%
		                                  if(testPolicyList!=null&&testPolicyList.size()>0){
		                                  %>
		                                  
		                                  	<INPUT type="radio" id="zujuan" name="types" value="1" onpropertychange="changeStatus()"  >使用组卷策略&nbsp;&nbsp;&nbsp;
		                                    <span id="ssss" style="display: none;">组卷数量：<font color=red>*</font><input style="" id="paper_num" name="paper_num" type="text" value="3"  ></span>
		                                  <%}else{
		                                  %>
		                                  		暂无组卷策略可选
		                                  <%}
		                                   %>
		                                  	<br/><INPUT type="radio" id="shougong" name="types" value="0" checked onpropertychange="changeStatus()"  >手工组卷<input   name="paper_num2" type="hidden" value="1" >
		                                  </td>		                                  
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
                    
                      <td align="center">
                      <input type="submit" value="提交" />&nbsp;&nbsp;&nbsp;&nbsp;
                      <button onclick="javascript:window.history.back()">返回</button>
      
                      </td>
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
