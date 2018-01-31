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
    		//alert("back");
    		history.back();
    	</s:else>
    }
	</script>
  </head>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
   <div id="main_content">
    <div class="content_title">底联详细信息</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td height="26" align="center" valign="middle" >底联详细信息                            </td>
                          </tr>
                          <tr>
                            <td height="8"> </td>
                          </tr>
						 
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">序&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="pebzzfaxInfo.seq" /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name"> 
                                    汇款人/汇款企业全称：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="pebzzfaxInfo.fullName" /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">所属企业：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="pebzzfaxInfo.peEnterprise.name" /></td>
                                </tr>
                                  <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">所在学期：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="pebzzfaxInfo.peBzzBatch.name" /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">金额：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="pebzzfaxInfo.amount" /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">人数：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="pebzzfaxInfo.num" /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">发票信息：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="pebzzfaxInfo.fpInfo" /></td>
                                </tr>
                                 <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">发票开具状态：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="pebzzfaxInfo.enumConstByFlagFpOpenState.name" /></td>
                                </tr>
                                 <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">发票邮寄状态：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="pebzzfaxInfo.enumConstByFlagFpMailState.name" /></td>
                                </tr>
                                 <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">备注：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="pebzzfaxInfo.bz" /></td>
                                </tr>
                                 <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">录入人：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="pebzzfaxInfo.luruPeople" /></td>
                                </tr>
                                 <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">录入时间：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:date name="pebzzfaxInfo.luruDate" format="yyyy年MM月dd日"/></td>
                                </tr>
                           <tr>
                            <td height="50" align="center" colspan="2">
                             <input type=button value="返回" onclick="backPrivWin();"></td>
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
