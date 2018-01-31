<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券业协会远程培训系统</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script>
$(document).ready(function() {
	$("#isInvoice").change(
			function() {
				if ($("#isInvoice").val() == 1) {
					$("#invoice").css("display","block");
				}else{
					$("#invoice").css("display","none");
				}
});});
</script>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<s:form name="batch"
			action="/entity/basic/doPayment_toConfirmPaymentMethod.action" method="POST"
			onsubmit="">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="28">
						<div class="content_title" id="zlb_title_start">
							订单确认
						</div id="zlb_title_end">
					</td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder" style="background: none;">
						<div class="border" style="padding-left: 30px">
							<table width="96%"  border="0"cellpadding="0" cellspacing="0">
								<tr>
									<td >
										<img src="/entity/manager/images/jd_03.jpg" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
							</table>
							<table style="text-align: left;" width="96%" border="0"
								cellpadding="0" cellspacing="0">
								<tr align="bottom" class="postFormBox">
									<td width="">
										<span class="name">学&nbsp;&nbsp;时&nbsp;&nbsp;数：</span>
									</td>
									<td width="">
										<font color="red"><s:property value="#session.allCredit"/></font>
									</td>
								</tr>
								<tr class="postFormBox">
									<td width="">
										<span class="name">总&nbsp;&nbsp;金&nbsp;&nbsp;额：</span>
									</td>
									<td width="">
										<font color="red"><s:property value="#session.allAmount"/>&nbsp;元</font>
									</td>
								</tr>
								<tr class="postFormBox">
									<td>
										<span class="name">备注：</span>
									</td>
									<td>
										<input name="peBzzOrderInfo.cname" type="text" size="15" maxlength="15">
										<span style="color:red;">*订单备忘录。</span>
									</td>
								</tr>
								<tr class="postFormBox" style="display:none">
									<td>
										<span class="name">开具发票：</span>
									</td>
									<td>
										<select name="isInvoice" id="isInvoice" >
											<option value="n" selected>
												否
											</option>
											<option value="y">
												是
											</option>
										</select>
									</td>
								</tr>
								<tr class="postFormBox">
									<td colspan="2">
										<table width="100%" id="invoice" border="0" cellpadding="0" cellspacing="0" style="display:none;">
											<tr class="postFormBox">
												<td colspan="2" style="color: red;">
													请认真如实填写或补充完整下面的地址信息，我们将发票邮寄至您填写的地址，请务必如实填写
												</td>
											</tr>
											<tr class="postFormBox">
												<td>
													<span class="name">付款单位（个人）：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.title" type="text" size="100"
														maxlength="15">
												</td>
											</tr>
											<tr class="postFormBox">
												<td>
													<span class="name">邮寄地址：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.address" type="text" size="100"
														maxlength="15">
												</td>
											</tr>
											<tr class="postFormBox">
												<td>
													<span class="name">邮政编码：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.zipCode" type="text" size="15"
														maxlength="15">
												</td>
											</tr>
											<tr class="postFormBox">
												<td>
													<span class="name">收&nbsp;&nbsp;件&nbsp;&nbsp;人：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.addressee" type="text" size="15"
														maxlength="15">
												</td>
											</tr>
											<tr class="postFormBox">
												<td>
													<span class="name">联系电话：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.phone" type="text" size="15"
														maxlength="15">
												</td>
											</tr>
										</table>
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
										href="###" onclick="Javascript:document.forms.batch.submit();">确认支付</a>
									</span> &nbsp;&nbsp;&nbsp;
									<span style="width: 46px; height: 15px; padding-top: 3px"><a
										href="/entity/basic/collectiveElective_toEelectiveBatchUpload.action" >重新选课</a>
									</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>

			</table>
		</s:form>
	</body>
</html>