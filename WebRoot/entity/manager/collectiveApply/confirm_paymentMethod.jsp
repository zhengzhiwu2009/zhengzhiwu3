<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券业协会远程培训系统</title>
		<!-- <link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"> -->
		<style type="text/css"><!--<%@ include file="/entity/manager/css/admincss.css"%>--></style>
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
  	var val = $(":radio[name='payMethod'][checked]").val();

	if(val == null){
		alert("请选择支付方式");
		return;
	}
	var answer = confirm("确定要支付吗");
	if (answer) {
	  	if(document.getElementById("yufu").checked){
		  	<s:if test="compareResult==2">
				<s:set name="msg" value="'余额不足'" />    
				alert("预付费账户余额不足");
				return;
			</s:if>
	  	}
	  	document.forms.batch.submit();
	} else {
		return;
	}
  }
</script>
	</head>
	<body leftmargin="0" topmargin="0" >
		<s:form name="batch"
			action="/entity/basic/collectiveElective_toPayment.action" method="POST">
			<input type="hidden" name="amount" value="<s:property value="#session.user_session.ssoUser.balance" />" />
			<table width="96%" border="0" align="left" cellpadding="0"
				cellspacing="0">
				<tr>
					<td>
						<div class="content_title" id="zlb_title_start">
							选择支付方式
						</div id="zlb_title_end">
					</td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder" style="background: none;">
						<div class="border" style="padding-left: 20px;">
							<div align="left">
								<img src="/entity/manager/images/jd_04.jpg" />
							</div>
							<p>
								&nbsp;
							</p>
							<table width="96%"  cellpadding="0" cellspacing="0" >
								<tr>
									<td align="left" height="28" class="" colspan="2">
										共需要支付&nbsp;&nbsp;&nbsp;
										<font color="red" size="4"><s:property value="#session.allAmount"/>&nbsp;元&nbsp;&nbsp;</font>
										
									</td>
								</tr>
								<tr>
									<td align="left" width="10%" height="28" >
										<input type="radio" class="radioItem" name="payMethod" value="1" id="yufu"/>
										预付费账户 
									</td>
									<td align="left"><span id="msg" style="display:none;" >
										<font size="4">预付费账户支付</font><span id="msg" style="display:none;" align="left">当前账户余额 ：
											<font color="red"><s:property value="balance(#session.user_session.ssoUser)"/></font>&nbsp;&nbsp;学时
											&nbsp;&nbsp;&nbsp;&nbsp;本次共需支付：<font color="red"><s:property value="#session.allTime"/></font>&nbsp;&nbsp;学时
											<font color="red"><s:property value="msg" /></font>
											
										</span>
									<br/></td>
								</tr>
								<tr>
									<td height="28" class="" colspan="2">
										<input type="radio" class="radioItem" name="payMethod" value="0" />
										网银支付
									</td>
								</tr>
								<tr>
									<td height="26" class="" colspan="2">
										<input type="radio" class="radioItem" name="payMethod" value="2" />
										电汇支付
									</td>
								</tr>
								<tr>
									<td height="27" class="" colspan="2">
										<input type="radio" class="radioItem" name="payMethod" value="3" />
										支票付款
									</td>
								</tr>
								<tr>
									<td width="81%" height="29" colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td height="29" colspan="2" style="color: red;">
										汇款支付说明
									</td>
								</tr>
								<tr>
									<td colspan="2" height="29" style="color: red;">
										请会员单位汇款至以下账户：
									</td>
								</tr>
								<tr>
									<td colspan="2" height="29" style="color: red;">
										户名：中国证券业协会培训中心
									</td>
								</tr>
								<tr>
									<td colspan="2" height="29" style="color: red;">
										账号：1100 1020 5000 5908 0060
									</td>
								</tr>
								<tr>
									<td colspan="2" height="29" style="color: red;">
										开户行：建行北京月坛支行
									</td>
								</tr>
							</table>
							<table width="98%" height="100%" border="0" cellpadding="0"
							cellspacing="0">
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