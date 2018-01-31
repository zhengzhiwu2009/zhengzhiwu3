<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title> </title>
	<meta http-equiv="expires" content="0">    
	<link href="./css/css.css" rel="stylesheet" type="text/css">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
  
  	<script type="text/javascript">
  	    function backPrivWin(){
    	<s:if test="gridConfig.getBackUrl() != null">
    		var uri = encodeURI('<s:property value="gridConfig.getBackUrl()"/>');
    		//window.navigate(uri);
    		window.location.href=uri;
    	</s:if>
    	<s:else>
    		history.back();
    	</s:else>
    }
  	</script>
  </head>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">

   <div id="main_content">
    <div class="content_title">
    	注册结果
    </div>
    <div class="cntent_k">
   	  <div class="k_cc">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td height="26" align="center" valign="middle"  colspan="2">注册结果                           </td>
                          </tr>
                          <tr>
                            <td height="8"> </td>
                          </tr>
						 	 <tr valign="middle"> 
    							<s:if test="insertNum!=0">
								 　<td width="140" height="30" align="left" class="postFormBox"><span class="name">成功注册：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="insertNum" />&nbsp;人 </td>
								</s:if>
    							<s:else>
    								<td align="center" valign="middle"  colspan="2">
    									没有可注册人员信息
    								</td>	
    							</s:else>
                                 
                                </tr>

                                <s:if test="ExistLoginId.length()!=0">
                                <tr>
                            	   <td height="26" align="center" valign="middle" colspan="2" class="postFormBox">
                            	   	 以下<s:property value="repeatNum" escape="false"/>名学生登陆账户已存在</td>
                         		</tr>
                                <tr valign="middle" > 
                                  
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="existLoginId" escape="false"/></td>
                                </tr>
                                </s:if>
             
                           <tr>
                            <td height="50" align="center">
                             <input type=button value="返回" onclick="backPrivWin();"> 
                             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                             <input type=button value="查看" onclick="window.location.href='/entity/recruit/peBzzRegist_showResult.action'"> 
                             
						  </tr>
						   <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>

	  </div>
    </div>
</div>
<div class="clear"></div>
  </body>
</html>
