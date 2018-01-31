<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单确认</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%-- <link rel="stylesheet" type="text/css" href="css/shared.css">
		<!--<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			 type="text/css">-->
		<style type="text/css"><!--<%@ include file="/entity/manager/css/admincss.css"%>--></style>
		--%>
		<link href="/entity/manager/css/pan_Order.css" rel="stylesheet"
			type="text/css">
		<script language="JavaScript" src="js/shared.js"></script>
		<script src="/js/shengshi.js" type="text/javascript"></script>
		<script language="JavaScript"
			src="/web/bzz_index/password/js/jquery-1.7.1.min.js"></script>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<form action="/entity/basic/buyClassHour_saveHour.action" method="post" id="form" name="form" target="_blank">
			<input type="hidden" name="peBzzOrderInfo.seq" value="<s:property value="peBzzOrderInfo.seq"/>" />
			<input type="hidden" name="peBzzOrderInfo.classHour" value="<s:property value="peBzzOrderInfo.classHour"/>" />
			<input type="hidden" name="peBzzOrderInfo.amount" value="<s:property value="peBzzOrderInfo.amount"/>" />
			<input type="hidden" name="amount" value="<s:property value="#session.user_session.ssoUser.balance" />" />
			<div class="warp">
			<h1>订单确认</h1>
			<table width="890" border="1" cellspacing="0" cellpadding="0" class="datalist">
		
				<tr>
					<th><div align="right">总金额：</div></th>
					<td><s:property value="peBzzOrderInfo.amount"/>元
					</td>
				</tr>
				<tr>
					<th><div align="right">订单备忘录：</div></th>
					<td>
						<input size="25" name="peBzzOrderInfo.cname" id="orderAlias" type="text" size="15" class="txt1" 
							maxlength="15"/>*便于您个人记住订单内容的标识。
					</td>
				</tr>
				<tr>
					<th><div align="right">选择支付方式：</div></th>
					<td>
						<input class="radioItem" type="radio" name="paymentMethod" value="0"/>
						在线支付
						<s:if test="#session.Logintype == 2">
							<input class="radioItem" type="radio" name="paymentMethod" value="2" <s:if test="peBzzOrderInfo.amount < 10000">disabled="disabled"</s:if>/>
							公司转账(当订单金额大于或等于一万元时，方可使用)
						</s:if>
							
<%-- 						<input class="radioItem" type="radio" name="paymentMethod" value="3" />
						支票付款
--%>					</td>
				</tr>
				<tr style ="display:none">
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
									 type="text" size="60" class="txt1" maxlength="100" >
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
									<input name="peBzzInvoiceInfo.address" id="address" type="hidden" size="60" class="txt1" maxlength="100"/>
									<input type="hidden" name="peBzzInvoiceInfo.province" id="provincex"/>
									<input type="hidden" name="peBzzInvoiceInfo.city" id="cityx"/>
									<%-- 支付接口切换99bill --%>
									<input type="hidden" name="paymentType" id="paymentType" value="99bill"/>
								</td>
							</tr>
							<tr class="postFormBox">
								<td bgcolor="#E9F4FF">
									<span class="name" style="float:right"><font color="red">*</font>邮寄方式：</span>
								</td>
								<td>
									<input type="radio" name="postType" checked="checked" value="01" />挂号信&nbsp;&nbsp;
									<input type="radio" name="postType" value="02" />快递到付&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">订单金额在10万元以上的，无需选择发票邮寄方式。协会将采用快递方式寄送发票，快递费用由协会承担。</span>
								</td>
							</tr>
						</table>
		</div>
		<div>	
			<table id="paymentInfo" width="890" border="0" cellspacing="0" cellpadding="0" class="datalist" style="display:none;">
			    <tr>
			      <td colspan="2" bgcolor="#cff2f6"  class="title">如本次不填写银行账号，请到“报名付费历史查询”中的“公司转账订单”页面填写，银行账号填写完成方可确认到款。
			      <br/><!-- 公司转账包括电汇、公司网银转账、支票等方式 -->
			      </td>
			    </tr>
				<tr>
			        <th width="164"><div align="right">填写汇款银行账号：</div></th>
			        <td width="726">
						<input name="peBzzOrderInfo.num" id="numNo" type="text" size="30"
							maxlength="200" class="txt1" >
						<font color="red">&nbsp;&nbsp;银行账号请谨慎填写
							， 填写后不可更改</font>
					</td>
				</tr>
				<tr>
					<th><div align="right">再次填写汇款银行账号：</div></th>
					<td>
						<input name="" id="numNo_con" type="text" size="30"
							maxlength="200" class="txt1" >
					</td>
				</tr>
				<tr>
				    <th colspan="2">
					<table width="380" border="0" cellspacing="0" cellpadding="0"  class="datalist1" align="center">
						<tr>
					    	<td colspan="2"><div align="left" class="STYLE1">汇款支付说明</br>请会员单位汇款到以下账户</div></td>
					    </tr>
					  	<tr>
					    	<td width="80"><div align="right">户　名：</div></td>
					    	<td width="79%">中国证券业协会培训中心</td>
					  	</tr>
					  	<tr>
					    	<td><div align="right">账　号：</div></td>
					    	<td style="padding-bottom: 3px;">1100 1020 5000 5908 0060</td>
					  	</tr>
					  	<tr>
					    	<td><div align="right">开户行：</div></td>
					    	<td>建行北京月坛支行</td>
					  	</tr>
					  	<tr>
					    	<td>&nbsp;</td>
					    	<td>&nbsp;</td>
					  	</tr>
					</table>
						<div align="right"></div>
					</th>
				</tr>	
			</table>
		</div>
			<div class="btn" >
				<a href="#" onclick="isSubmit();"><img src="/web/bzz_index/images/btn100.png" width="97" height="25" /></a>
				<a href="#" onclick="javascript:window.history.back();" ><img src="/web/bzz_index/images/btn2.png" width="80" height="25" /></a>
			</div>
		</div>
	</form>
</body>
<script>
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
