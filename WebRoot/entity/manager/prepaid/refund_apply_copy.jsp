<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>退费申请</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/shared.css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<script language="JavaScript" src="js/shared.js"></script>
		<script language="JavaScript"
			src="/web/bzz_index/password/js/jquery-1.7.1.min.js"></script>
	</head>
	<script type="text/javascript">
		alert("因第三方支付平台限制，采用“企业网银”支付的订单未能实现自动退款，需通过线下转账方式完成。请您在提交退款申请的同时发送退款说明（包括退款原因、退款订单号、原付款帐号、原付款人姓名、开户行、公司名称并盖章）至咨询服务邮箱training@sac.net.cn，我们将尽快为您办理退款。");
	</script>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<form action="<s:property value="getServletPath()"/>_refundReason.action" method="post"
			id="form">
			<input type="hidden" name="id" value="<s:property value="id" />" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="28">
						<div class="content_title" id="zlb_title_start">
							退费申请
						</div id="zlb_title_end">
					</td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder" style="background: none;">
						<div class="border" style="padding-left: 30px">

							<table style="text-align: left;" width="96%" border="0"
								cellpadding="0" cellspacing="0">

								<tr class="postFormBox" id="a2">
									<td>
										<span class="name">退费原因：</span>
									</td>
									<td>
										<textarea name="refundReason" id="refundReason" cols="50" rows="10"></textarea>
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
									<span class="name" style="width: 46px; height: 15px; padding-top: 3px"><a
										onclick="isSubmit();" href="#">申请</a> </span> &nbsp;&nbsp;&nbsp;
									<span class="name" style="width: 46px; height: 15px; padding-top: 3px"><a
										href="#" onclick="javascript:window.history.back()">返回</a> </span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			
			</table>
		</form>
		<input  type="hidden" id="forward" value="<s:property value="forward"/>">
	</body>
	<script type="text/javascript">
		function isSubmit() {
		var res = $('#refundReason').val();
		if(res == null || res == '') {
			alert('退费原因不能为空');
			return;
		}
			var forward = $('#forward').val();
			if(forward!=null || forward!='') {
				$('form').attr('action',forward);
			}
			$('#form').submit();
		}
	/*	function hiddenOrShow() {
			var val = $('#isInvoice').val();
			if(val == 1) {
				for(var i=1; i<6; i++) {
					$("#a"+i).show();  
				}
			} else {
				for(var i=1; i<6; i++) {
					$("#a"+i).hide();  
				}
			}
		}
		$(document).ready(function(){
			var val = $('#isInvoice').val();
			if(val == 0) {
				for(var i=1; i<6; i++) {
					$("#a"+i).hide();  
				}
			}
 		});**/
	</script>
</html>
