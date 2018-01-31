<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>发票详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/shared.css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<script language="JavaScript" src="js/shared.js"></script>
		<script language="JavaScript"
			src="/web/bzz_index/password/js/jquery-1.7.1.min.js"></script>
		<script src="/js/shengshi.js" type="text/javascript"></script>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<form action="/entity/basic/buyClassHourRecord_applyInvoice.action"
			method="post" id="form" name="form">
			<input type="hidden" name="id" value="<s:property value="id" />" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="28">
						<div class="content_title" id="zlb_title_start">
							发票详情
						</div id="zlb_title_end">
					</td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder" style="background: none;">
						<div class="border" style="padding-left: 30px">
							<table style="text-align: left;" width="65%" border="0"
								cellpadding="0" cellspacing="0">
								<tr class="postFormBox" id="a1">
									<td>
										<span class="name">付款单位（个人）：</span>
									</td>
									<td>
										<s:property value="peBzzInvoiceInfo.title" />
									</td>
								</tr>
								<s:if test="peBzzInvoiceInfo.enumConstByInvoiceType.code !=3">
									<tr class="postFormBox" id="a2">
										<td>
											<span class="name">邮寄地址：</span>
										</td>
										<td align="left">
											省（市）：
											<s:property value="peBzzInvoiceInfo.province" escape="false" />
											市（区）：
											<s:property value="peBzzInvoiceInfo.city" escape="false" />
											<br />
											<s:property value="peBzzInvoiceInfo.address" escape="false" />
										</td>
									</tr>
									<tr class="postFormBox" id="a3">
										<td>
											<span class="name">邮政编码：</span>
										</td>
										<td>
											<s:property value="peBzzInvoiceInfo.zipCode" />
										</td>
									</tr>
									<tr class="postFormBox" id="a4">
										<td>
											<span class="name">收&nbsp;&nbsp;件&nbsp;&nbsp;人：</span>
										</td>
										<td>
											<s:property value="peBzzInvoiceInfo.addressee" />
										</td>
									</tr>
									<tr class="postFormBox" id="a5">
										<td>
											<span class="name">收件人电话：</span>
										</td>
										<td>
											<s:property value="peBzzInvoiceInfo.phone" />
										</td>
									</tr>
									<tr class="postFormBox" id="a6">
										<td>
											<span class="name">购买方纳税人识别号：</span>
										</td>
										<td>
											<s:property value="peBzzInvoiceInfo.gmfnsrsbh" default="--" />
										</td>
									</tr>
									<tr class="postFormBox" id="a7">
										<td>
											<span class="name">购买方地址：</span>
										</td>
										<td>
											<s:property value="peBzzInvoiceInfo.gmfdz" />
										</td>
									</tr>
								</s:if>
								<s:if test="peBzzInvoiceInfo.enumConstByInvoiceType.code !=3">
									<tr class="postFormBox" id="a8">
										<td>
											<span class="name">购买方电话：</span>
										</td>
										<td>
											<s:property value="peBzzInvoiceInfo.gmfdh" />
										</td>
									</tr>
								</s:if>
								<s:if test="peBzzInvoiceInfo.enumConstByInvoiceType.code ==3">
									<tr class="postFormBox" id="a8">
										<td>
											<span class="name">购买手机号码：</span>
										</td>
										<td>
											<s:property value="peBzzInvoiceInfo.gmfsjhm" />
										</td>
									</tr>
								</s:if>
								<tr class="postFormBox" id="a18">
									<td>
										<span class="name">购买方邮箱：</span>
									</td>
									<td>
										<s:property value="peBzzInvoiceInfo.email" />
									</td>
								</tr>
								<s:if test="peBzzInvoiceInfo.enumConstByInvoiceType.code !=3" >
								<tr class="postFormBox" id="a9">
									<td>
										<span class="name">购买方开户行：</span>
									</td>
									<td>
										<s:property value="peBzzInvoiceInfo.gmfkhyh" />
									</td>
								</tr>
								<tr class="postFormBox" id="a10">
									<td>
										<span class="name">购买方银行账号：</span>
									</td>
									<td>
										<s:property value="peBzzInvoiceInfo.gmfyhzh" />
									</td>
								</tr>
								</s:if>
								<tr class="postFormBox" id="invoiceType">
									<td>
										<span class="name" color="red">发票类型</span>
									</td>
									<td>
										<s:property
											value="peBzzInvoiceInfo.enumConstByInvoiceType.name"
											default="--" />
									</td>
								</tr>
								<tr class="postFormBox" id="a12">
									<td>
										<span class="name">发票状态</span>
									</td>
									<td>
										<s:property
											value="#request.peBzzInvoiceInfo.enumConstByFlagFpOpenState.name"
											default="--" />
									</td>
								</tr>
								<tr class="postFormBox" id="a13" >
									<td>
										<span class="name">发票代码</span>
									</td>
									<td>
										<s:property value="#request.peBzzInvoiceInfo.fpdmNum" default ="--" />
									</td>
								</tr>
								<tr class="postFormBox" id="a14" >
									<td>
										<span class="name">发票号码</span>
									</td>
									<td>
										<s:property value="#request.peBzzInvoiceInfo.num" default ="--" />
									</td>
								</tr>
								<tr class="postFormBox" id="a15" >
									<td>
										<span class="name">发票校验码</span>
									</td>
									<td>
										<s:property value="#request.peBzzInvoiceInfo.fpjymNum" default ="--" />
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<table width="98%" height="100%" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td align="center" valign="middle">
									<span style="width: 46px; height: 15px; padding-top: 3px"><a
										onclick="window.close();" style="cursor: pointer;">关闭</a> </span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<input type="hidden" name="peBzzInvoiceInfo.province" id="provincex" />
			<input type="hidden" name="peBzzInvoiceInfo.city" id="cityx" />
		</form>
		<input type="hidden" id="forward"
			value="<s:property value="forward"/>">
	</body>
</html>
