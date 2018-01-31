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
										<s:property value="peBzzInvoiceInfo.title"/>
									</td>
								</tr>
								
								<tr class="postFormBox" id="a5">
									<td>
										<span class="name">购买方手机号码：</span>
									</td>
									<td>
										<s:property value="peBzzInvoiceInfo.gmfsjhm"/>
									</td>
								</tr>
								<tr class="postFormBox" id="a5">
									<td>
										<span class="name">购买方邮箱：</span>
									</td>
									<td>
										<s:property value="peBzzInvoiceInfo.email"/>
									</td>
								</tr>
								<tr class="postFormBox" id="invoiceType">
									<td>
										<span class="name">发票类型</span>
									</td>
									<td>
										<s:property
														value="peBzzInvoiceInfo.enumConstByInvoiceType.name" default ="--"/>
									</td>
								</tr>
								<tr class="postFormBox" id="a11" >
									<td>
										<span class="name">合并订单号：</span>
									</td>
									<td>
										<s:property
														value="#request.strSeq" default ="--"/>
									</td>
								</tr>
								<tr class="postFormBox" id="a12" >
									<td>
										<span class="name">发票状态：</span>
									</td>
									<td>
										<s:property	value="#request.peBzzInvoiceInfo.enumConstByFlagFpOpenState.name"
															default="--" />
									</td>
								</tr>
								<tr class="postFormBox" id="a13" >
									<td>
										<span class="name">发票代码</span>
									</td>
									<td>
										<s:property value="peBzzInvoiceInfo.fpdmNum" default ="--" />
									</td>
								</tr>
								<tr class="postFormBox" id="a14" >
									<td>
										<span class="name">发票号码</span>
									</td>
									<td>
										<s:property value="peBzzInvoiceInfo.num" default ="--" />
									</td>
								</tr>
								<tr class="postFormBox" id="a15" >
									<td>
										<span class="name">发票校验码</span>
									</td>
									<td>
										<s:property value="peBzzInvoiceInfo.fpjymNum" default ="--" />
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
