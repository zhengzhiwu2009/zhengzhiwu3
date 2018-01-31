<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" content="-10"/>  
<meta http-equiv="Pragma" content="no-cache"/>  
<meta http-equiv="cache-control" content="no-cache" />
<%  
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache,must-revalidate"); 
	response.setDateHeader("Expires", -10); 
%> 
 
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<link href="/entity/bzz-students/css/style.css" rel="stylesheet" type="text/css" /> 
<link href="/entity/bzz-students/css/reset.css" rel="stylesheet" type="text/css" /> 
<link href="/entity/manager/css/pan_Order.css" rel="stylesheet" type="text/css"/>
<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
<script src="/js/shengshi.js" type="text/javascript"></script>
</head>
	<body>
	<center>
		<form action="/entity/workspaceStudent/studentWorkspace_confirmOrder.action" method="post" id="form" name="form">
			<s:token/>
				<div class="warp">
					<h1>订单确认</h1>
					<table width="890" border="0" cellspacing="0" cellpadding="0" class="datalist">
						<tr >
							<th width="164" align="right">
								<div align="right">本次共选课时：</div>
							</th>
							<td width="726">
								<s:property value="classHour" />学时
							</td>
						</tr>
						<tr align="right">
							<th>
								<div align="right">共需支付：</div>
							</th>
							<td>
								<s:property value="amount" />&nbsp;元 
							</td>
						</tr>
						<s:if test="#session.peBzzBatch == null">
							<tr align="right">
								<th align="right">
									<div align="right">预付费账户剩余学时数：</div>
								</th>
								<td>
									<s:property value="#request.subAmount" />&nbsp;学时
								</td>
							</tr>
						</s:if>
						<tr  align="right">
							<th>
								<div align="right">订单备忘录：</div>
							</th>
							<td>
								<input size="25" maxlength="15" name="peBzzOrderInfo.cname" id="orderAlias" type="text" class="txt1"/>*便于您个人记住订单的标识
							</td>
						</tr>
						<tr >
							<th><div align="right" ><font color="red" >*</font>选择支付方式：</div></th>
							<td>
								<input class="radioItem" type="radio" name="paymentMethod" id="paymentMethod" value="0" checked="checked"/>在线支付
							 <s:if test="#session.peBzzBatch == null"> 
								<input class="radioItem" type="radio" name="paymentMethod" value="1"  <s:if test="amount * 1 /classHour * 1 != 20"> disabled="disabled" </s:if>/>预付费账户支付(仅能支付20元/学时的课程)
							  </s:if>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<font color="red"><label id="tishi"></label>
								</font>
							</td>
							</tr>
						<!--  
						<tr >
							<th><div align="right">开具发票：</div></th>
							<td>
								<select id="isInvoice" name="isInvoice" onchange="">
									<option id="op1" selected value="0">
										否
									</option>
									<option value="1">
										是
									</option>
								</select>
							</td>
						</tr>
						-->
						<tr>
							<td colspan="2">
							 <b> 根据财务要求，小于1000元的订单（包含合并订单）无法开具增值税专用发票。学员进行个人报名时请考虑上述要求，如个人订单无法达到1000元，请集体管理员统一报名并申请发票。</b>
							</td>
						</tr>
					</table>
					<!--  
				<div>
					<table id="invoice" width="890" border="0" cellspacing="0" cellpadding="0" class="datalist" style="display:none;">
							<tr >
								<td colspan="2" bgcolor="#cff2f6" class="title" >
									请认真如实填写或补充完整下面的地址信息，我们将发票邮寄到你填写的地址，请务必如实填写
								</td>
							</tr>
							<tr >
								<th width="164">
									<div align="right"><font color="red" >*</font>付款单位（个人）：</div>
								</th>
								<td width="726">
									<input name="peBzzInvoiceInfo.title" id="invoiceTitle" type="text"
									 value="<s:property value="lastInvoiceInfo.title"/>"
									 class="txt1" size="60" maxlength="100"/>
								</td>
							</tr>
							
							<tr >
								<th><div align="right"><font color="red" >*</font>邮政编码：</div></th>
								<td>
									<input name="peBzzInvoiceInfo.zipCode" id="post"
									 value="<s:property value="lastInvoiceInfo.zipCode"/>"
									 type="text" size="60" class="txt1" maxlength="100" />
								</td>
							</tr>
							<tr >
								<th><div align="right"><font color="red" >*</font>收件人：</div></th>
								<td>
									<input name="peBzzInvoiceInfo.addressee" id="addressee"
									 value="<s:property value="lastInvoiceInfo.addressee"/>"
									 type="text" size="60" class="txt1" maxlength="15" />
								</td>
							</tr>
							<tr >
								<th><div align="right"><font color="red" >*</font>联系电话：</div></th>
								<td>
									<input name="peBzzInvoiceInfo.phone" id="tel"
									 value="<s:property value="lastInvoiceInfo.phone"/>"
									 type="text" size="60" class="txt1" />
								</td>
							</tr>
							<tr >
								<th><div align="right"><font color="red" >*</font>邮寄地址：</div></th>
								<td>
									省（市）：<select name="province" id="province" onChange = "selectShengShi()"><s:property value="lastInvoiceInfo.province" escape="false"/></select>
									市（区）：<select name="city" id="city" onChange = "selectShengShi()"><s:property value="lastInvoiceInfo.city" escape="false"/></select><br/>
									<input  id="hd_address"  type="hidden" size="60" class="txt1" maxlength="100"/>
									<input name="addressx" id="addressx"  type="text" size="60" class="txt1" maxlength="100"
									value="<s:property value="lastInvoiceInfo.address" escape="false"/>"/>
									<input name="peBzzInvoiceInfo.address" id="address"  type="hidden" size="60" class="txt1" maxlength="100"/>
								</td>
							</tr>
							<tr class="postFormBox" id="a5">
								<td>
									<span class="name">邮寄方式：</span>
								</td>
								<td align="left">
									<input type="radio" name="postType" checked="checked" value="01" />挂号信&nbsp;&nbsp;
									<input type="radio" name="postType" value="02" />快递到付
								</td>
							</tr>
						</table>
					</div>
					-->
							<!-- 			
							<table width="100%" id="invoice" width="100%"  border="0" cellpadding="0" cellspacing="0" >
								<tr align="center">
									<td  style="border:none;">
										<th align="right" width="50%">	
											<font size="3" style="font-weight:500;">支付方式：</font>&nbsp;&nbsp;&nbsp;
										</th>
										<td height="28" class="" align="left" >
											<input type="radio" name="paymentMethod" value="0" />
											<font size="3" style="font-weight:500;">网银支付</font>
												&nbsp;&nbsp;&nbsp;
											<s:if test="#session.orderType!=2&&#session.isInvoice!=1">
												<input type="radio" name="paymentMethod" value="1" />
												<font size="3" style="font-weight:500;">预付费账户支付</font>
											</s:if>
										</td>
									</td>
								</tr>
							</table>
							 -->			
										
						<div class="btn" >
							<a id="payBtn" href="#" onclick="checkSubmit();"><img src="/web/bzz_index/images/btn100.png" width="97" height="25" /></a>
							<a href="#" onclick="javascript:close123();" ><img src="/web/bzz_index/images/btn_26.png" width="80" height="25" /></a>
						</div>
			</div>
			<!--  
			<input type="hidden" name="peBzzInvoiceInfo.province" id="provincex"/>
			<input type="hidden" name="peBzzInvoiceInfo.city" id="cityx"/>
			-->
			<%-- 支付接口切换99bill --%>
			<input type="hidden" name="paymentType" id="paymentType" value="99bill"/>
		</form>
		</center>
	</body>
<script>
function checkSubmit(){
	//订单确认按钮隐藏
	$('#payBtn').hide();
	//提示信息
	$('#tishi').text($('#tishi').text() + '    处理中，请耐心等待！');
	
	if(confirm('根据财务要求，小于1000元的订单（包含合并订单）无法开具增值税专用发票。学员进行个人报名时请考虑上述要求，如个人订单无法达到1000元，请集体管理员统一报名并申请发票')){
	document.forms.form.submit();		
	}
}
function balanceTip(){
 	<s:if test="isEnough(#session.user_session.ssoUser, #session.allAmount)">
		<s:set name="msg" value="'余额不足'" />
			alert("预付费账户余额不足");
			return false;
	</s:if>
}
$(document).ready(
	function() {
		var config = {
			p:'<s:property value="lastInvoiceInfo.province" escape='false'/>',
			c:'<s:property value="lastInvoiceInfo.city" escape='false'/>',
			d:'<s:property value="lastInvoiceInfo.address" escape='false'/>',
			goalP:'<s:property value="lastInvoiceInfo.province" escape='false'/>',
			goal:'<s:property value="lastInvoiceInfo.city" escape='false'/>'
		};
		initShengShi(config);
	}
);
</script>
</html>
