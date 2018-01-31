<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String d = String.valueOf(Math.random());
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.	w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="JavaScript"
			src="/web/bzz_index/password/js/jquery-1.7.1.min.js"></script>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}

.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
-->
</style>
	</head>
	<body>
		<table width="1002" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td>
					<table width="1002" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<!-- <IFRAME NAME="top" width=100% height=150 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME> -->
					</table>
					<table width="1002" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<form
							action="/entity/workspaceStudent/bzzstudent_paymentMethod.action"
							name="form" id="form" method="post">
						<tr>
							<!-- <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=850 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp?d=<%=d%>" scrolling=no allowTransparency="true"></IFRAME></td> -->
							<td width="752" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr align="center" valign="top">
										<td height="17" align="left" class="twocentop">
											<img src="/entity/bzz-students/images/two/1.jpg" width="11"
												height="12" />
											当前位置：专项培训支付
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<table width="100%" border="0" cellpadding="0" cellspacing="0">

											<tr>
												<td valign="top" class="pageBodyBorder"
													style="background: none;">
													<div class="border" style="padding-left: 30px">

														<table style="text-align: left;" width="96%" border="0"
															cellpadding="0" cellspacing="0">
															<tr vlign="bottom" class="postFormBox">
																<td width="100px">
																	<span class="name">订&nbsp;&nbsp;单&nbsp;&nbsp;号：</span>
																</td>
																<td width="">
																	<s:property value="#session.order.seq" />
																</td>
															</tr>
															<tr align="bottom" class="postFormBox">
																<td width="">
																	<span class="name">学&nbsp;&nbsp;时&nbsp;&nbsp;数：</span>
																</td>
																<td width="">
																	<s:property value="#session.order.classHour" />
																</td>
															</tr>
															<tr class="postFormBox">
																<td width="">
																	<span class="name">总&nbsp;&nbsp;金&nbsp;&nbsp;额：</span>
																</td>
																<td width="">
																	<s:property value="#session.order.amount" />

																</td>
															</tr>
															<tr class="postFormBox">
																<td>
																	<span class="name">备注：</span>
																</td>
																<td>
																	<input name="orderAlias" id="orderAlias" type="text"
																		size="15" maxlength="15"><span>*订单备忘录。</span>
																</td>
															</tr>
															<tr class="postFormBox">
																<td>
																	<span class="name">开具发票：</span>
																</td>
																<td>
																	<input type="hidden" id="invoice" name="invoice" />
																	<select id="isInvoice" onchange="hiddenOrShow();">
																		<option selected value="0">
																			否
																		</option>
																		<option value="1">
																			是
																		</option>
																	</select>
																</td>
															</tr>
															<tr class="postFormBox">
																<td colspan="2" style="color: red;">
																	请认真如实填写或补充完整下面的地址信息，我们将发票邮寄至您填写的地址，请务必如实填写
																</td>
															</tr>

															<tr class="postFormBox" id="a1">
																<td>
																	<span class="name">付款单位（个人）：</span>
																</td>
																<td>
																	<input name="invoiceTitle" id="invoiceTitle"
																		type="text" size="15" maxlength="100">
																</td>
															</tr>
															<tr class="postFormBox" id="a2">
																<td>
																	<span class="name">邮寄地址：</span>
																</td>
																<td>
																	<input name="address" id="address" type="text"
																		size="15" maxlength="100">
																</td>
															</tr>
															<tr class="postFormBox" id="a3">
																<td>
																	<span class="name">邮政编码：</span>
																</td>
																<td>
																	<input name="post" id="post" type="text" size="15"
																		maxlength="15">
																</td>
															</tr>
															<tr class="postFormBox" id="a4">
																<td>
																	<span class="name">收&nbsp;&nbsp;件&nbsp;&nbsp;人：</span>
																</td>
																<td>
																	<input name="addressee" id="addressee" type="text"
																		size="15" maxlength="100">
																</td>
															</tr>
															<tr class="postFormBox" id="a5">
																<td>
																	<span class="name">联系电话：</span>
																</td>
																<td>
																	<input name="tel" id="tel" type="text" size="15"
																		maxlength="15">
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
																<span
																	style="width: 86px; height: 15px; padding-top: 3px"><a
																	onclick="isSubmit();" href="#">确认支付</a> </span>
																&nbsp;&nbsp;&nbsp;

															</td>
														</tr>
													</table>
												</td>
											</tr>

										</table>
									</tr>



									<tr>
										<td width="13">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
							<td width="13">
								&nbsp;
							</td>
						</tr>
						</form>
					</table>
					<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0
						marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp"
						scrolling=no allowTransparency="true" align=center></IFRAME>
				</td>
			</tr>
		</table>
	</body>
	<script type="text/javascript">
		function isSubmit() {
			var orderAlias = $('#orderAlias').val();
			var isInvoice = $('#isInvoice').val();
			var invoiceTitle = $('#invoiceTitle').val();
			
			var address = $('#address').val();
			var post = $('#post').val();
			var addressee = $('#addressee').val();
			var tel = $('#tel').val();
			if(orderAlias == '' || orderAlias == null) {
				alert('备注不能为空');
				return;
			}
			if(isInvoice == 1) {
				if(invoiceTitle == '' || invoiceTitle == null) {
					alert('付款单位（个人）不能为空');
					return;
				} else if(address == '' || address == null) {
					alert('收件地址不能为空');
					return;
				} else if(post=='' || post == null) {
					alert('邮政编码不能为空');
					return;
				} else if(addressee=='' || addressee == null) {
					alert('收件人不能为空');
					return;
				} else if(tel == '' || tel == null) {
					alert('电话不能为空');
					return;
				}
				var reg = /^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;//固话
				var myreg1=/^1[3|4|5|8][0-9]\d{4,8}$/; //手机
				var myreg2=/^1[0-9]{10}$/;

					
				if(!reg.test(tel)&&(!myreg1.test(tel)||!myreg2.test(tel))){
					alert("电话号码应为座机或手机！\n请重新输入");
					$('#tel').focus();
					return false;
				}
			}
			$('#invoice').val(isInvoice);
			$('#form').submit();
		}
		function hiddenOrShow() {
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
 		});
	</script>
</html>