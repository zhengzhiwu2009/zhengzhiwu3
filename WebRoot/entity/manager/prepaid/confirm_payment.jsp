<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>选择支付方式</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/shared.css">
		<style type="text/css"><!--<%@ include file="/entity/manager/css/admincss.css"%>--></style>
		<script language="JavaScript" src="js/shared.js"></script>
		<script language="JavaScript"
			src="/web/bzz_index/password/js/jquery-1.7.1.min.js"></script>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"
		class="scllbar">
		<form action="/entity/basic/buyClassHour_saveHour.action" method="post" id="form">
		<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<div class="content_title" id="zlb_title_start">
						选择支付方式
					</div id="zlb_title_end">
				</td>
			</tr>
			<tr>
				<td style="padding-left: 20;"></td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder"
					style="padding-left: 5px; text-align: center; background: none;">
					<div class="border" style="padding-left: 30px;">
						<table style="text-align: left">
							<tr>
								<td height="28" class="" colspan="2">
									<font size="6">共需要支付</font>&nbsp;&nbsp;&nbsp;
									<font color="red" size="14"><s:property value="#session.peBzzOrderInfo.amount"/>&nbsp;元&nbsp;&nbsp;</font>
								</td>
							</tr>
							<tr>
								<td height="28" class="" colspan="2">
									<input type="radio" name="paymentMethod" value="0"/>
									<font size="4">网银支付</font>
								</td>
							</tr>
							<tr>
								<td height="26" class="" colspan="2">
									<input type="radio" name="paymentMethod" value="2" />
									<font size="4">电汇支付</font>
								</td>

							</tr>
							<tr>
								<td height="27" class="" colspan="2">
									<input type="radio" name="paymentMethod" value="3" />
									<font size="4">支票付款</font>
								</td>
							</tr>
							<tr>
								<td width="81%" height="29" colspan="2">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td height="29" colspan="2" style="color: red;">
									<font size="4">汇款支付说明</font>
								</td>
							</tr>

							<tr>
								<td colspan="2" height="29" style="color: red;">
									<font size="4">请会员单位汇款至以下账户：</font>
								</td>
							</tr>
							<tr>
								<td colspan="2" height="29" style="color: red;">
									<font size="4">户名：中国证券业协会培训中心</font>
								</td>
							</tr>
							<tr>
								<td colspan="2" height="29" style="color: red;">
									<font size="4">账号：1100 1020 5000 5908 0060</font>
								</td>
							</tr>
							<tr>
								<td colspan="2" height="29" style="color: red;">
									<font size="4">开户行：建行北京月坛支行</font>
								</td>
							</tr>
						</table>
						<br />
						<table width="98%" height="100%" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
							<td align="center" valign="middle">
							<span style="width: 40px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_2.jpg');">
									<a href="###" onclick="check();">支付</a> </span>
							&nbsp;&nbsp;&nbsp;
							<span style="width: 50px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_3.jpg');"><a
										href="###" onclick="javascript:window.history.back()">上一步</a> </span>
							</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		</form>
	</body>
	<script type="text/javascript">
		function check() {
			var val = $("input[name='paymentMethod'][checked]").val();
			if(val == null){
				alert("请选择支付方式");
				return;
			}
			var answer = confirm("确定要支付吗");
			if (answer) {
				$("#form").submit();		
			} else {
				return;
			}
		}
	</script>
</html>