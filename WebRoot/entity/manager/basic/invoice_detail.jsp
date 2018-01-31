<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>发票信息</title>

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
					<table width="500" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="26" valign="middle" align="center" colspan="10">
								<h4>
									发票信息
								</h4>
							</td>
						</tr>
						<tr>
							<td height="8" colspan="10">
							</td>
						</tr>
						<tr valign="middle">
							<td width="18%" height="30" align="left" class="postFormBox">
								<span class="name">付款单位（个人）：</span>
							</td>
							<td width="50%" class="postFormBox" style="padding-left: 18px">
								<s:property value="peBzzInvoiceInfo.title" />
							</td>
						</tr>
						<tr>
							<td width="12%" height="20" align="left" class="postFormBox">
								<span class="name"> 发票状态：</span>
							</td>
							<td width="35%" class="postFormBox" style="padding-left: 18px"
								align="left">
								<s:property
									value="peBzzInvoiceInfo.enumConstByFlagFpOpenState.name" />
							</td>

						</tr>
						<tr>
							<td width="12%" height="20" align="left" class="postFormBox">
								<span class="name"> 邮寄地址：</span>
							</td>
							<td width="35%" class="postFormBox" style="padding-left: 18px"
								align="left">
								<s:property value="peBzzInvoiceInfo.address" />
							</td>

						</tr>
						<tr valign="middle">
							<td width="12%" height="20" align="left" class="postFormBox">
								<span class="name">邮政编码：</span>
							</td>
							<td width="41%" class="postFormBox" style="padding-left: 18px">
								<s:property value="peBzzInvoiceInfo.zipCode" />
							</td>

						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">收&nbsp;&nbsp;件&nbsp;&nbsp;人：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px"
								colspan="2">
								<s:property value="peBzzInvoiceInfo.addressee" />
							</td>
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">收件人电话：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px"
								colspan="2">
								<s:property value="peBzzInvoiceInfo.phone" />
							</td>
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">购买方纳税人识别号：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px"
								colspan="2">
								<s:property value="peBzzInvoiceInfo.gmfnsrsbh" />
							</td>
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">购买方地址：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px"
								colspan="2">
								<s:property value="peBzzInvoiceInfo.gmfdz" />
							</td>
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">购买方电话：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px"
								colspan="2">
								<s:property value="peBzzInvoiceInfo.gmfdh" />
							</td>
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">购买方邮箱：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px"
								colspan="2">
								<s:property value="peBzzInvoiceInfo.email" />
							</td>
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">购买方开户行：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px"
								colspan="2">
								<s:property value="peBzzInvoiceInfo.gmfkhyh" />
							</td>
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">购买方银行账号：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px"
								colspan="2">
								<s:property value="peBzzInvoiceInfo.gmfyhzh" />
							</td>
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">合并订单号：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px"
								colspan="2">
								<s:property value="peBzzInvoiceInfo.peBzzOrderInfo.mergeSeq" default="--" />
							</td>
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">发票代码：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px"
								colspan="2">
								<s:property value="peBzzInvoiceInfo.fpdmNum" default="--"/>
							</td>
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">发票号码：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px"
								colspan="2">
								<s:property value="peBzzInvoiceInfo.num" default="--"/>
							</td>
						</tr>
						<tr valign="middle">
							<td width="12%" height="30" align="left" class="postFormBox">
								<span class="name">发票校验码：</span>
							</td>
							<td width="38%" class="postFormBox" style="padding-left: 18px"
								colspan="2">
								<s:property value="peBzzInvoiceInfo.fpjymNum" default="--"/>
							</td>
						</tr>
						<tr>
							<td height="10">
							</td>
						</tr>
						<tr>
							<td colspan="20" align="center">
								<span
									style="width: 40px; height: 22px; line-height: 22px; background-image: url('/entity/manager/images/button_2.jpg');">
									<a href="###" onclick="window.close();">关闭</a> </span>
								<!-- Lee 2014年3月5日 返回改为关闭history.back(); -->
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
