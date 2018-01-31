<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" /> 
<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" /> 
<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<link href="/entity/manager/css/pan_Order.css" rel="stylesheet" type="text/css"/>
<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
</head>
	<body leftmargin="0" topmargin="0">
		<form
			action="/entity/workspaceStudent/studentWorkspace_onlinePayment.action"
			method="post" id="form">
			<input type="hidden" name="peBzzOrderInfo.amount"
				value="<s:property value="#session.peBzzOrderInfo.amount" />" />
			<input type="hidden" name="peBzzOrderInfo.classHour"
				value="<s:property value="classHour" />" />
			<div class="warp">
				<h1>订单确认</h1>
				<table width="890" border="0" cellspacing="0" cellpadding="0" class="datalist">
					<tr>
						<th width="164" align="right">
							<div align="right">总学时：</div>
						</th>
						<td width="726">
							<s:property value="#session.peBzzOrderInfo.amount"/>
						</td>
					</tr>
					<tr>
						<th>
							<div align="right">备注：</div>
						</th>
						<td bgcolor="#FAFAFA">
							<input name="peBzzOrderInfo.cname" id="orderAlias" type="text"
								size="25" value="<s:property value="#session.peBzzOrderInfo.cname"/>" class="txt1">
								*订单备忘录。
						</td>
					</tr>
					<tr>
						<th><div align="right">开具发票：</div></th>
						<td>
							<select id="isInvoice" name="isInvoice">
								<option selected value="0">
									否
								</option>
								<option value="1">
									是
								</option>
							</select>
						</td>
					</tr>
				</table>
				<div>
					<table id="invoice" width="890" border="0" cellspacing="0" cellpadding="0" class="datalist" style="display:none;">
						<tr >
							<td colspan="2" bgcolor="#cff2f6" class="title" >
								请认真如实填写或补充完整下面的地址信息，我们将发票邮寄到你填写的地址，请务必如实填写
							</td>
						</tr>
						<th width="164">
							<div align="right"><font color="red" >*</font>付款单位（个人）：</div>
						</th>
						<td width="726">
								<input name="peBzzInvoiceInfo.title" id="invoiceTitle" type="text" 
									class="txt1" size="60" maxlength="100" value="<s:property value="#session.peBzzInvoiceInfo.title"/>"/>
							</td>
						</tr>
						<tr>
							<th><div align="right"><font color="red" >*</font>邮寄地址：</div></th>
							<td> 
								<input name="peBzzInvoiceInfo.address" id="address"  type="text" 
									class="txt1" size="60" maxlength="100" value="<s:property value="#session.peBzzInvoiceInfo.title"/>"/>
							</td>
						</tr>
						<tr>
							<th><div align="right"><font color="red" >*</font>邮政编码：</div></th>
							<td>
								<input name="peBzzInvoiceInfo.zipCode" id="post" type="text" 
									class="txt1" size="60" maxlength="15" value="<s:property value="#session.peBzzInvoiceInfo.zipCode"/>"/>
							</td>
						</tr>
						<tr>
							<th><div align="right"><font color="red" >*</font>收件人：</div></th>
							<td>
								<input name="peBzzInvoiceInfo.addressee" id="addressee" type="text" 
									class="txt1" size="60" maxlength="100" value="<s:property value="#session.peBzzInvoiceInfo.addressee"/>"/>
							</td>
						</tr>
						<tr>
							<th><div align="right"><font color="red" >*</font>联系电话：</div></th>
							<td>
								<input name="peBzzInvoiceInfo.phone" id="tel" type="text" 
									class="txt1" size="60" maxlength="15" value="<s:property value="#session.peBzzInvoiceInfo.phone"/>"/>
							</td>
						</tr>
					</table>
				</div>
				
				<div class="btn" >
					<a href="#" onclick="isSubmit();"><img src="/web/bzz_index/images/btn100.png" width="97" height="25" /></a>
					<a href="#" onclick="javascript:close123();" ><img src="/web/bzz_index/images/btn_26.png" width="80" height="25" /></a>
				</div>
			</div>
		</form>
	</body>
	<script type="text/javascript">
		function close123() {
			var answer = confirm("确定要关闭吗?");
			if (!answer) {
				return;
			} 
			window.close();
		}
		function isSubmit() {
			var orderAlias = $('#orderAlias').val();
			var isInvoice = $('#isInvoice').val();
			var invoiceTitle = $('#invoiceTitle').val();
			
			var address = $('#address').val();
			var post = $('#post').val();
			var addressee = $('#addressee').val();
			var tel = $('#tel').val();
			var pattern =/^[0-9]\d{5}(?!d)$/;//邮编
            var reg = /^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;//固话
			var myreg1=/^1[3|4|5|8][0-9]\d{4,8}$/; //手机
			var myreg2=/^1[0-9]{10}$/;
			if(orderAlias == '' || orderAlias == null) {
				alert('备注不能为空');
				return;
			}
			if(isInvoice == 1) {
				if(invoiceTitle == '' || invoiceTitle == null) {
					alert('付款单位（个人）不能为空');
					return;
				} 
				if(address == '' || address == null) {
					alert('邮寄地址不能为空');
					return;
				} 
				if(post=='' || post == null) {
					alert('邮政编码不能为空');
					return;
				} else {
					if(!pattern.exec(post)){
	                   alert("邮编格式不正确！\n请重新输入");
	                   $('#post').focus();
	                   return;
	                }
				}
				if(addressee=='' || addressee == null) {
					alert('收件人不能为空');
					return;
				} 
				if(tel == '' || tel == null) {
					alert('电话不能为空');
					return;
				} else {
					if(!reg.test(tel)&&(!myreg1.test(tel)||!myreg2.test(tel))){
						alert("电话号码应为座机或手机！\n请重新输入");
						$('#tel').focus();
						return false;
					}
				}
			}
			$('#form').submit();
		}
$(document).ready(function() {
	$("#isInvoice").change(
			function() {
				if ($("#isInvoice").val() == '1') {
					$("#invoice").css("display","block");
				}else{
					$("#invoice").css("display","none");	
				}
});});
function close123() {
			var answer = confirm("确定要关闭吗?");
			if (!answer) {
				return;
			} 
			window.close();
		}
	</script>
</html>
