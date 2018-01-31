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
<script src="/js/shengshi.js" type="text/javascript"></script>
 <!-- script language="JavaScript" src="/entity/bzz-students/js/page.js"></script-->

</head>
	<body leftmargin="0" topmargin="0">
		<form
			action="/entity/workspaceStudent/studentWorkspace_modifyInvoiceInfo.action"
			method="post" id="form" name="form">
			<input type="hidden" name="id" id="id"
				value="<s:property value="#request.id" />" />
			<input type="hidden" name="peBzzInvoiceInfo.id" value="<s:property value="peBzzInvoiceInfo.id"/>"/>	
			<div  style="width: 100%; padding: 0; margin: 0;"
				align="center">
						<div class="main_title">
							发票信息修改
						</div>
						<div class="main_txt">
									<table width="100%" id="invoice" class="datalist" width="100%"  border="0" cellpadding="0" cellspacing="0" >
											<tr class="postFormBox">
												<td colspan="2" style="color: red;" align="left">
													1、请认真如实填写或补充完整下面的地址信息，我们将发票邮寄至您填写的地址
													<br />
													2、当此订单金额小于1000元时，学员将无法点击选择增值税专用发票，页面提供提示信息，因财务制度要求如需申请增值税专用发票，
													<br/>
													则可通过多笔订单合并(金额>=1000)方式申请
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF" width="20%">
													<span class="name" style="float: right"><font color="red">*</font>发票类型：</span>
												</td>
												<td align="left">
													<input type="radio" name="invoiceType" value="3" disabled="true"/>电子发票&nbsp;&nbsp;&nbsp;
													<input type="radio" name="invoiceType" value="4" disabled="true" checked/>增值税专用发票
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF" width="15%">
													<span class="name" style="float:right"><font color="red" >*</font>付款单位（个人）：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.title" id="invoiceTitle" type="text" size="85" maxlength="100"
														style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
														 value="<s:property value="peBzzInvoiceInfo.title"/>"/>
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float:right"><font color="red" >*</font>邮寄地址：</span>
												</td>
												<td align="left">
													省（市）：<select name="province" id="province" onChange = "selectShengShi()"></select>
													市（区）：<select name="city" id="city" onChange = "selectShengShi()"></select><br/>
													<input  id="hd_address"  type="hidden" size="60" class="txt1" maxlength="100"/>
													<input name="addressx" id="addressx"  type="text" size="60" class="txt1" maxlength="100"
													value="<s:property value="peBzzInvoiceInfo.address" escape="false"/>"/>
													<input name="peBzzInvoiceInfo.address" id="address"  type="hidden" size="60" class="txt1" maxlength="100"/>
														
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float:right"><font color="red">*</font>邮政编码：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.zipCode" id="post" type="text" size="85"
														style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
														value="<s:property value="peBzzInvoiceInfo.zipCode"/>"/>
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float:right"><font color="red">*</font>收件人：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.addressee" id="addressee" type="text" size="85" maxlength="30"
														style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
														value="<s:property value="peBzzInvoiceInfo.addressee"/>"/>
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float:right"><font color="red">*</font>收件人电话：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.phone" id="tel" type="text" size="85"
														style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
														value="<s:property value="peBzzInvoiceInfo.phone"/>"/>
												</td>
											</tr>
											<tr id="gmfnsrsbhTr">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float: right"><font color="red">*</font>购买方纳税人识别号：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.gmfnsrsbh" id="gmfnsrsbh" type="text"
														size="85" value="<s:property value="peBzzInvoiceInfo.gmfnsrsbh"/>"
														style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
												</td>
											</tr>
											<tr id="gmfdzTr">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float: right"><font color="red">*</font>购买方地址：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.gmfdz" id="gmfdz" type="text"
														size="85" value="<s:property value="peBzzInvoiceInfo.gmfdz"/>"
														style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
												</td>
											</tr>
											<tr>
												<td bgcolor="#E9F4FF">
													<span class="name" style="float: right"><font color="red">*</font>购买方电话：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.gmfdh" id="gmfdh" type="text"
														size="85" value="<s:property value="peBzzInvoiceInfo.gmfdh"/>"
														style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
												</td>
											</tr>
											<tr>
												<td bgcolor="#E9F4FF">
													<span class="name" style="float: right"><font color="red">*</font>购买方邮箱：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.email" id="email" type="text"
														size="85" value="<s:property value="peBzzInvoiceInfo.email"/>"
														style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
												</td>
											</tr>
											<tr id="gmfkhyhTr">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float: right"><font color="red">*</font>购买方开户行：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.gmfkhyh" id="gmfkhyh" type="text"
														size="85" value="<s:property value="peBzzInvoiceInfo.gmfkhyh"/>"
														style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
												</td>
											</tr>
											<tr id="gmfyhzhTr">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float: right"><font color="red">*</font>购买方银行账号：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.gmfyhzh" id="gmfyhzh" type="text"
														size="85" value="<s:property value="peBzzInvoiceInfo.gmfyhzh"/>"
														style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
												</td>
											</tr>
											<!--  <tr class="postFormBox" id="postTypeTr">
												<td>
													<span class="name">邮寄方式：</span>
												</td>
												<td align="left">
													<input type="radio" name="postType" <s:if test="peBzzInvoiceInfo.enumConstByFlagPostType.code == '01' || peBzzInvoiceInfo.enumConstByFlagPostType == null || peBzzInvoiceInfo.enumConstByFlagPostType == ''">checked=checked</s:if> value="01" />挂号信&nbsp;&nbsp;
													<input type="radio" name="postType" <s:if test="peBzzInvoiceInfo.enumConstByFlagPostType.code == '02'">checked=checked</s:if> value="02" />快递到付
													&nbsp;&nbsp;<font color="red">*如选择快递到付方式，则请务必填写准确的联系电话</font>
												</td>
											</tr>-->
											<tr>
												<td bgcolor="#E9F4FF">
													<span class="name" style="float: right"><font color="red">&nbsp;</font>发票开具描述：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.invoiceRemark" id="invoiceRemark" type="text"
														size="85" value="<s:property value="peBzzInvoiceInfo.invoiceRemark"/>"
														style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
												</td>
											</tr>
											<tr class="postFormBox">
												<td colspan="2" style="color: red;">
													"发票开具描述"中填写的内容将打印在增值税专用发票的"备注"项中，请慎重填写！
													<br>
												</td>
											</tr>
										</table>
										<br/>
							<table width="100%" id="invoice" width="100%"  border="0" cellpadding="0" cellspacing="0" >
								<tr align="center">
								<td width=10%>&nbsp;</td>
									<td  style="border:none;"><div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_21.jpg')" ><a
										onclick="modify();" href="#">修改</a></div>
										</td>
										<td>
							
									<div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_21.jpg')" ><a onclick="returnOrder();" href="#">返回</a></div></td>
							<td width=10%>&nbsp;</td>
							</tr>
							</table>
							<div style="height:10px"></div>	
						</div>
				</div>
			<input type="hidden" name="peBzzInvoiceInfo.province" id="provincex"/>
			<input type="hidden" name="peBzzInvoiceInfo.city" id="cityx"/>
		</form>
	</body>
<script>
function modify() {
			var invoiceTitle = $('#invoiceTitle').val();
			
			var ss = document.getElementById('hd_address').value;
			var de = document.getElementById('addressx').value;
			document.getElementById('address').value = ss+de;
			
			var address = $('#address').val();
			var post = $('#post').val();
			var addressee = $('#addressee').val();
			
			var gmfnsrsbh = document.getElementById('gmfnsrsbh').value;
			var gmfdz = document.getElementById('gmfdz').value;
			var gmfkhyh = document.getElementById('gmfkhyh').value;
			var gmfyhzh = document.getElementById('gmfyhzh').value;
			var gmfdh = document.getElementById('gmfdh').value;
			var gmfyx = document.getElementById('email').value;
			var len = gmfdz.replace(/[^\x00-\xff]/g, "**").length;
			
			var tel = $('#tel').val();
				if(invoiceTitle == '' || invoiceTitle == null) {
					alert('付款单位（个人）不能为空');
					return;
				} else if(invoiceTitle.length >50){
					alert('付款单位（个人）长度不能大于50个汉字');
					return ;
				}else if(address == '' || address == null) {
					alert('收件地址不能为空');
					return;
				} else if(address.length >90){
					alert('收件人地址长度不能大于90个汉字');
					return;
				}else if(post=='' || post == null) {
					alert('邮政编码不能为空');
					return;
				} else if(addressee=='' || addressee == null) {
					alert('收件人不能为空');
					return;
				} else if(addressee.length >10){
					alert('收件人长度不能大于10个汉字');
					return;
				} else if(tel == '' || tel == null) {
					alert('收件人电话不能为空');
					return;
				} else if (tel.length >20){
					alert('收件人电话不正确，\n 请重新输入');
					return;
				}else if(gmfnsrsbh == '' || gmfnsrsbh == null) {
					alert('购买方纳税人识别号不能为空');
					return;
				} else if(gmfnsrsbh.length > 20 ||gmfnsrsbh.length <15) {
					alert('购买方纳税人识别号不正确');
					return;
				} else if(gmfdh == '' || gmfdh == null) {
					alert('购买方手机号码不能为空');
					return;
				} else if(gmfdh.length > 20) {
					alert('购买方电话不正确，\n请重新输入');
					return;
				} else if(gmfyx == '' || gmfyx == null) {
					alert('购买方邮箱不能为空');
					return;
				} else if(gmfyx.length > 50) {
					alert('购买方邮箱长度不能超过50');
					return;
				} else if(gmfdz == '' || gmfdz == null) {
					alert('购买方地址不能为空');
					return;
				} else if(len > 80) {
					alert('购买方地址超长');
					return;
				} else if(gmfkhyh == '' || gmfkhyh == null) {
					alert('购买方开户行不能为空');
					return;
				} else if(gmfkhyh.length > 25) {
					alert('购买方开户行长度不能大于25个汉字');
					return;
				} else if(gmfyhzh == '' || gmfyhzh == null) {
					alert('购买方银行账号不能为空');
					return;
				} else if(gmfyhzh.length > 25) {
					alert('购买方银行账号不正确');
					return;
				}
				var reg1 = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
			  	ismail= reg1.test(gmfyx);
			  	if (!ismail ) {
				   	alert("邮箱格式不正确！");
				   	return;
			 	}
			 	
				var pattern =/^[0-9]\d{5}(?!d)$/;//邮编
				if(!pattern.exec(post)){
	            	alert("邮政编码格式不正确！\n请重新输入");
	                $('#post').focus();
	                return;
	            }	
			$('#form').submit();
		}
		
		function returnOrder() {
			window.location.href="/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action";
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
			p:'<s:property value="peBzzInvoiceInfo.province" escape='false'/>',
			c:'<s:property value="peBzzInvoiceInfo.city" escape='false'/>',
			d:'<s:property value="peBzzInvoiceInfo.address" escape='false'/>',
			goalP:'<s:property value="peBzzInvoiceInfo.province" escape='false'/>',
			goal:'<s:property value="peBzzInvoiceInfo.city" escape='false'/>'
		};
		initShengShi(config);
	}
);
</script>
</html>
