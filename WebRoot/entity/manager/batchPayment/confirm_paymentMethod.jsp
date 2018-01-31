<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券业协会远程 培训系统</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script type="text/javascript">
$(document).ready(function() {
	$(".radioItem").change(
			function() {
				var $selectedvalue = $("input[name='payMethod']:checked").val();
				if ($selectedvalue == 1) {
					$("#msg").css("display","block");
				}else{
					$("#msg").css("display","none");
				}
});});
  
  function isInvoice(flag){
  	if(flag=="1"){
  		document.getElementById().style.display="block";
  	}else{
  		document.getElementById().style.display="none";
  	}
  }
  
  function doSubmit(){
  var $selectedvalue = $("input[name='payMethod']:checked").val();
  	if($selectedvalue == null) {
  		alert('请选择一种支付方式');
  		return;
  	}
  	document.forms.batch.submit();
  }
</script>
	</head>
	<body leftmargin="0" topmargin="0" >
		<s:form name="batch"
			action="/entity/basic/paymentBatch_toPayment.action" method="POST">
			<input type="hidden" name="amount" value="<s:property value="#session.user_session.ssoUser.amount" />" />
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
					<td valign="top" class="pageBodyBorder" style="padding-left: 5px; text-align: center; background: none;">
						<div class="border" style="padding-left: 30px;">
							<div align="left">
								<span></span>
							</div>
							<p>
								&nbsp;
							</p>
							<table style="text-align: left">
								<tr>
									<td height="28" class="" colspan="2" align="center">
										<font size="6">共需要支付</font>&nbsp;&nbsp;&nbsp;
										<font color="red" size="14"><s:property value="#session.batchPrice"/>&nbsp;元&nbsp;&nbsp;</font>
										
									</td>
								</tr>
								<!-- <tr>
									<td width="10%" height="28" align="center">
										<input type="radio" class="radioItem" name="payMethod" value="1" />
										<font size="4">预付费账户支付</font><span id="msg" style="display:none;" align="center">当前账户余额 ：
											<font color="red"><s:property value="getSubAmount(#session.user_session.ssoUser)"/></font>&nbsp;&nbsp;学时
											&nbsp;&nbsp;&nbsp;&nbsp;本次需支付：<font color="red"><s:property value="paymentHour(#request.peBzzOrderInfo.amount)" /></font>&nbsp;&nbsp;学时
											<font color="red"><s:property value="msg" /></font>
										</span>
									</td>
								</tr> -->
								<tr>
									<td height="28" class="" colspan="2" align="center">
										<input type="radio" class="radioItem" name="payMethod" value="0" />
										<font size="4">网银支付</font>
									</td>
								</tr>
								<tr>
									<td height="26" class="" colspan="2" align="center">
										<input type="radio" class="radioItem" name="payMethod" value="2" />
										<font size="4">电汇支付</font>
									</td>
								</tr>
								<tr>
									<td height="27" class="" colspan="2" align="center">
										<input type="radio" class="radioItem" name="payMethod" value="3" />
										<font size="4">支票付款</font>
									</td>
								</tr>
								<tr>
									<td width="81%" height="29" colspan="2" align="center">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td height="29" colspan="2" style="color: red;" align="center">
										<font size="4">汇款支付说明</font>
									</td>
								</tr>
								<tr>
									<td colspan="2" height="29" style="color: red;" align="center">
										<font size="4">请会员单位汇款至以下账户：</font>
									</td>
								</tr>
								<tr>
									<td colspan="2" height="29" style="color: red;" align="center">
										<font size="4">户名：中国证券业协会培训中心</font>
									</td>
								</tr>
								<tr>
									<td colspan="2" height="29" style="color: red;" align="center">
										<font size="4">账号：1100 1020 5000 5908 0060</font>
									</td>
								</tr>
								<tr>
									<td colspan="2" height="29" style="color: red;" align="center">
										<font size="4">开户行：建行北京月坛支行</font>
									</td>
								</tr>
							</table>
							<table width="98%" height="100%" border="0" cellpadding="0"
							cellspacing="0" align="center">
							<tr>
								<td align="center" valign="middle">
									<span style="width: 40px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_2.jpg');">
										<a href="###" onclick="doSubmit();">支付</a> </span>
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
		</s:form>
	</body>
</html>