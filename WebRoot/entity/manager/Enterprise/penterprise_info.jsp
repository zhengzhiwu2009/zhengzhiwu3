<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>机构信息</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="./css/css.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">


	</head>
	<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">


		<div id="main_content">
			<div align="center">
			</div>
			<div class="cntent_k">
				<div align="center">
				</div>
				<div class="k_cc">
					<div align="center">
					</div>
					<table width="780" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="26" valign="middle" align="center" colspan="10">
								<h4>
									机构信息
								</h4>
							</td>
						</tr>
						<tr>
							<td height="8" colspan="10">
							</td>
						</tr>
						<tr>
							<td width="100%" height="20" class="postFormBox" align="left"
								colspan="10">
								<strong>机构名称 ：<s:property value="peEnterprise.name" />
								</strong>
							</td>
							</td>
						</tr>
						<!-- <tr>
							<td colspan="10" align="center">
								<table border="0" align="center" cellpadding="0" cellspacing="0"
									width="100%">
									<tr>
										<td width="5%" height="20" align="left" class="postFormBox">
											<span class="name">机构编号：</span>
										</td>
										<td width="15%" class="postFormBox" style="padding-left: 18px"
											align="left">
											<s:property value="peEnterprise.code" />
										</td>
									</tr>
									<tr>	
										<td width="5%" height="30" align="left" class="postFormBox">
											<span class="name">
												机构名称：</span>
										</td>
										<td width="25%" class="postFormBox" style="padding-left: 18px">
											<s:property value="peEnterprise.name" />
										</td>
									</tr>
									<tr>
										 <td width="5%" width="80" height="30" align="left"
											class="postFormBox">
											<span class="name">
												地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</span>
										</td>
										<td class="postFormBox" style="padding-left: 18px">
											<s:property value="peEnterprise.address" />
										</td>
									</tr>
									
								</table>
							</td>
						</tr> -->
						<tr>
										<td width="5%" height="20" align="left" class="postFormBox">
											<span class="name">机构编号：</span>
										</td>
										<td width="15%" class="postFormBox" style="padding-left: 18px"
											align="left">
											<s:property value="peEnterprise.code" default="--"/>
										</td>
									</tr>
									<tr>	
										<td width="5%" height="30" align="left" class="postFormBox">
											<span class="name">
												机构名称：</span>
										</td>
										<td width="25%" class="postFormBox" style="padding-left: 18px">
											<s:property value="peEnterprise.name"  default="--"/>
										</td>
									</tr>
									<tr>
										 <td width="5%" width="80" height="30" align="left"
											class="postFormBox">
											<span class="name">
												办公地址：</span>
										</td>
										<td class="postFormBox" style="padding-left: 18px">
											<s:property value="peEnterprise.address"  default="--"/>
										</td>
									</tr>
						<!-- <tr>
							<td width="12%" height="20" align="left" class="postFormBox">
								<span class="name">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span>
							</td>
							<td width="35%" class="postFormBox" style="padding-left: 18px"
								align="left">
								<s:property value="peEnterprise.fzrXb"  default="--"/>
							</td>
							

						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</span>
							</td>
							<td width="35%" class="postFormBox" style="padding-left: 18px">
								<s:property value="peEnterprise.fzrPosition"  default="--"/>
							</td>
							
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">办公电话：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px">
								<s:property value="peEnterprise.fzrPhone"  default="--"/>
							</td>
							
						</tr>
						<tr valign="middle">
							<td width="12%" width="10%" height="30" align="left"
								class="postFormBox">
								<span class="name">移动电话：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px">
								<s:property value="peEnterprise.fzrMobile"  default="--"/>
							</td>
							
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">电子邮件：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px">
								<s:property value="peEnterprise.fzrEmail"  default="--"/>
							</td>
							
						</tr>
						<tr valign="middle">
							<td width="15%" height="30" align="left" class="postFormBox">
								<span class="name">通讯地址：</span>
							</td>
							<td width="35%" class="postFormBox" style="padding-left: 18px">
								<s:property value="peEnterprise.fzrAddress"  default="--"/>
							</td>
							
						</tr>
						 -->
						<tr valign="middle">
							<td width="15%" height="30" align="left" class="postFormBox">
								<span class="name">注册地址：</span>
							</td>
							<td width="35%" class="postFormBox" style="padding-left: 18px">
								<s:property value="peEnterprise.enrolAddress"  default="--"/>
							</td>
							
						</tr>
						<tr valign="middle">
							<td width="15%" height="30" align="left" class="postFormBox">
								<span class="name">网&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</span>
							</td>
							<td width="35%" class="postFormBox" style="padding-left: 18px">
								<s:property value="peEnterprise.netAddress"  default="--"/>
							</td>
							
						</tr>
						<tr>
							<td height="10">
							</td>
						</tr>
						<tr>
							<td colspan="10" align="center">
								<span
									style="width: 40px; height: 22px; line-height: 22px; background-image: url('/entity/manager/images/button_2.jpg');">
									<a href="###" onclick="history.back();">返回</a> </span>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
