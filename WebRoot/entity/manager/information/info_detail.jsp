<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title> </title>
	<meta http-equiv="expires" content="0">    
	<link href="./css/css.css" rel="stylesheet" type="text/css">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
  </head>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
  <s:if test="message=='editOK'">
  <script type="text/javascript">
  				alert("修改成功");
  </script>
  </s:if>
   <div id="main_content">
    <div class="content_title">账户基本信息</div>
    <div class="cntent_k">
   	  <div class="k_cc">
					<table width="500px" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="8" colspan="4">
								&nbsp;
							</td>
						</tr>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox" width="200" >
								<span class="name">用&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="peManager.loginId" />
								<s:property value="peEnterpriseManager.loginId" />
							</td>
						</tr>
						<tr>
							<td height="30" align="left" class="postFormBox">
								<span class="name">账&nbsp;号&nbsp;类&nbsp;别&nbsp;：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="peManager.pePriRole.name" />
								<s:property value="peEnterpriseManager.pePriRole.name" />
							</td>
						</tr>
						<!--  
						<s:if test="peEnterpriseManager !=null" >
							<tr valign="middle">
								<td height="30" align="left" class="postFormBox">
									<span class="name">机&nbsp;构&nbsp;代&nbsp;码&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="peEnterpriseManager.peEnterprise.code" />
								</td>
							</tr>
							<tr>
								<td height="30" align="left" class="postFormBox">
									<span class="name">单&nbsp;位&nbsp;名&nbsp;称&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="peEnterpriseManager.peEnterprise.name" />
								</td>
							</tr>
						</s:if>
						-->
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">管理员姓名：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="peManager.trueName" />
								<s:property value="peEnterpriseManager.Name" />
							</td>
						</tr>
						<tr>
							<td height="30" align="left" class="postFormBox">
								<span class="name">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="peManager.enumConstByGender.name" />
								<s:property value="peEnterpriseManager.enumConstByGender.name" />
							</td>
						</tr>
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name"><s:if test="peManager !=null">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</s:if><s:else>所&nbsp;在&nbsp;部&nbsp;门&nbsp;：</s:else> </span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="peManager.zhiCheng" />
								<s:property value="peEnterpriseManager.peEnterprise.fzrDepart" />
							</td>
						</tr>
						
						<tr valign="middle">
							<td height="30" align="left" class="postFormBox">
								<span class="name">电&nbsp;子&nbsp;邮&nbsp;箱&nbsp;：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="peManager.email" />
								<s:property value="peEnterpriseManager.email" />
							</td>
						</tr>
						<tr>
							<td height="30" align="left" class="postFormBox">
							<span class="name">固&nbsp;定&nbsp;电&nbsp;话&nbsp;：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
							<s:property value="peManager.phone" />
								<s:property value="peEnterpriseManager.phone" />
							</td>
						</tr>
						<tr>
							<td height="30" align="left" class="postFormBox">
								<span class="name">移&nbsp;动&nbsp;电&nbsp;话&nbsp;：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<s:property value="peManager.mobilePhone" />
								<s:property value="peEnterpriseManager.mobilePhone" />
							</td>
						</tr>
						<s:if test="peEnterpriseManager !=null">	
							<tr>
								<td height="30" align="left" class="postFormBox">
								<span class="name">办&nbsp;公&nbsp;地&nbsp;址&nbsp;：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<s:property value="peEnterpriseManager.peEnterprise.address" />
								</td>
							</tr>
							<s:if test="peEnterpriseManager.getSsoUser().getPePriRole().getId() != '131AF5EC87836928E0530100007F9F54'">
							<tr valign="middle">
								<td height="30" align="left" class="postFormBox">
									<span class="name">从业人员规模：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px" colspan="3">
									<s:property value="peEnterpriseManager.peEnterprise.num" />
								</td>
							</tr>
							</s:if>
						</s:if>
						<tr>
							<td height="50" align="center" colspan="4">
							<span style="width: 70px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_4.jpg');">
								<a href="/entity/information/personalInfo_showPwd.action">
									修改密码
								</a>
							</span>
								&nbsp;&nbsp;&nbsp;
								<span style="width: 70px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_4.jpg');">
								<a href="/entity/information/personalInfo_turnToEditInfo.action">
									修改信息
								</a>
								</span>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
								
							</td>
						</tr>
						<tr>
							<td height="10">
							</td>
						</tr>
					</table>

				</div>
    </div>
</div>
<div class="clear"></div>
  </body>
</html>
