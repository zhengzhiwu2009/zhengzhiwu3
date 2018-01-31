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
<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
<style>
input{line-height:20px;padding-left:8px;padding-right:8px}
</style>
</head>
	<body leftmargin="0" topmargin="0">
	<center>
		<form
			action="/entity/workspaceStudent/studentWorkspace_confirmOrder.action"
			method="post" id="form">
			<input type="hidden" name="peBzzOrderInfo.amount"
				value="<s:property value="amount" />" />
			<input type="hidden" name="peBzzOrderInfo.classHour"
				value="<s:property value="classHour" />" />
			<div  style="width:50%; padding: 0; margin: 0;"
				align="center">
						<div class="main_title">
							订单确认
						</div>
						<div class="main_txt">
							<table class="datalist"  width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FAFAFA">
								<tr class="postFormBox">
									<td width="83" align="right" bgcolor="#E9F4FF">
										<span class="name">总金额：</span>
									</td>
									<td width="" bgcolor="#FAFAFA">
										<s:property value="amount" />元
									</td>
								</tr>
								<tr class="postFormBox" align="right">
									<td bgcolor="#E9F4FF" width="83">
										<span class="name"><font color="red">*</font>备注：</span>
									</td>
									<td bgcolor="#FAFAFA" >
										<input name="peBzzOrderInfo.cname" id="orderAlias" type="text" 
											size="50"  style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;" ><span style="color:red;">*温馨提示：请自定义订单名，方便您记住您的订单。</span>
									</td>
									
									
								</tr>
								<tr class="postFormBox">
									<td width="83" align="right" bgcolor="#E9F4FF">
										<span class="name">开具发票：</span>
									</td>
									<td bgcolor="#FAFAFA">
										<select id="isInvoice" name="isInvoice"
											onchange="">
											<option id="op1" selected value="0">
												否
											</option>
											<option value="1">
												是
											</option>
										</select>
									</td>
								</tr>
								</table>
									<table width="100%" id="invoice" class="datalist"  width="100%"  border="0" cellpadding="0" cellspacing="0" style="display:none;">
											<tr class="postFormBox">
												<td colspan="2" style="color: red;">
													请认真如实填写或补充完整下面的地址信息，我们将发票邮寄至您填写的地址，请务必如实填写<br/>
													*若支付方式选择--预付费支付--无法开具发票，您填写的发票信息将被忽略
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF" width="83">
													<span class="name" style="float:right"><font color="red" >*</font>付款单位（个人）：</span>
												</td>
												<td bgcolor="#FAFAFA">

													<input name="peBzzInvoiceInfo.title" maxlength="100"  id="invoiceTitle" type="text" size="85" style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
													><span></span>
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float:right"><font color="red" >*</font>邮寄地址：</span>
												</td>
												<td bgcolor="#FAFAFA">


													<input name="peBzzInvoiceInfo.address" id="address" maxlength="100"  type="text" size="85" style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
													><span></span>

											


													<!-- <input name="peBzzInvoiceInfo.address" id="address"  type="text" size="15" style="float:left"> -->
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float:right"><font color="red">*</font>邮政编码：</span>
												</td>
												<td bgcolor="#FAFAFA">


													<input name="peBzzInvoiceInfo.zipCode" id="post" type="text" size="85" style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
														>


												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float:right"><font color="red">*</font>收件人：</span>
												</td>
												<td bgcolor="#FAFAFA">


													<input name="peBzzInvoiceInfo.addressee" id="addressee" type="text" size="85" style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
														>


												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float:right"><font color="red">*</font>联系电话：</span>
												</td>
												<td bgcolor="#FAFAFA">


													<input name="peBzzInvoiceInfo.phone" id="tel" type="text" size="85" style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
														>

												</td>
											</tr>
										</table>
										<br/>
							<table width="100%" id="invoice" width="100%"  border="0" cellpadding="0" cellspacing="0" >
								<tr align="center">
									<td  style="border:none;">
									<div class="ck" style="margin-left:120px;width:90px;background-image:url('/entity/bzz-students/images/index_21.jpg')" >
										<span><a onclick="isSubmit();" style="width:100px;" href="#">确认支付</a></span>
									</div>
									</td>
    								<!-- <td  style="border:none;"><div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_23.jpg')" ><a
										href="#" onclick="javascript:window.history.back()">返回</a></div></td>	 -->							
							</tr>
							</table>
						</div>
				</div>
		</form>
		</center>
	</body>
	<script type="text/javascript">
	function test(obj){

	obj.nextSibling.innerHTML="<font color=red>此处填写订单号</font>";
	}		
function test1(obj){

obj.nextSibling.innerHTML=" ";
	
}
		
	
	
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
				
				
				var pattern =/^[0-9]{6}$/;
                var pattern_tel = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$|(13\d{9}$)|(15[0135-9]\d{8}$)|(18[267]\d{8}$)/;
                var patten_phone= /^(((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?)|(((\\(\\d{2,3}\\))|(\\d{3}\\-))?1[35]\\d{9})$/;
               var patten_2=/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
                 if(!pattern.exec(post))
                 {
                    alert('请输入正确的邮政编码');
                    return;
                 }
              //   if(!pattern_tel.exec(tel)&(!patten_phone.exec(tel))) {
             
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
	});
	var val = $('#isInvoice').val();
	if(val == 1) {
		$("#invoice").css("display","block");
	}
	





});
/*
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
		}**/
		
		
		
	</script>
</html>
