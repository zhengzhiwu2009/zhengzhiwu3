<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中国证劵业协会培训平台</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/style.css" rel="stylesheet"
			type="text/css" />
		<link href="/entity/bzz-students/css/reset.css" rel="stylesheet"
			type="text/css" />
		<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script language="JavaScript" src="/entity/bzz-students/js/page.js"></script>
		<script src="/js/shengshi.js" type="text/javascript"></script>
	</head>
	<%
		String amount = (String)request.getAttribute("amount");
		double a = Double.parseDouble(amount);
	 %>
	 <script>
	 	function canChose() {
	 		var amount = <%=a%>;
	 		if(amount < 1000) {
	 			var invoices = document.getElementsByName("invoiceType");
	 			invoices[1].disabled = true;
	 		}
	 	}
	 </script>
	<body onload="initShengShi();" leftmargin="0" topmargin="0">
		<form
			action="/entity/workspaceStudent/studentWorkspace_creditNote.action"
			method="post" id="form" name="form">&nbsp; 
			<input type="hidden" name="id" id="id"
				value="<s:property value="#request.id" />" />
			<div style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					发票冲红
				</div>
				<div class="main_txt">
					<table width="100%" id="invoice" class="datalist" width="100%"
						border="0" cellpadding="0" cellspacing="0">
						<tr class="postFormBox">
							<td colspan="2" style="color: red;" align="left">
								1、请认真如实填写或补充完整下面的地址信息，我们将发票邮寄至您填写的地址
								<br />
								2、当此订单金额小于1000元时，学员将无法点击选择增值税专用发票，页面提供提示信息，因财务制度要求如需申请
								<br/>
								增值税专用发票，则可通过多笔订单合并(金额>=1000)方式申请
							</td>
						</tr>
						<tr class="postFormBox">
							<td bgcolor="#E9F4FF" width="20%">
								<span class="name" style="float: right"><font color="red">*</font>发票类型：</span>
							</td>
							<td align="left">
								<input type="radio" name="invoiceType" value="3" <s:if test='lastInvoiceInfo.enumConstByInvoiceType.code == "3"'>checked=checked</s:if>/>电子发票&nbsp;&nbsp;&nbsp;
								<input type="radio" name="invoiceType" value="4" <s:if test='lastInvoiceInfo.enumConstByInvoiceType.code == "4"'>checked=checked</s:if>/>增值税专用发票
							</td>
						</tr>
						<tr class="postFormBox">
							<td bgcolor="#E9F4FF" width="20%">
								<span class="name" style="float: right"><font color="red">*</font>付款单位(个人)：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.title" id="invoiceTitle"
									type="text" size="85" maxlength="100"
									value="<s:property value="lastInvoiceInfo.title"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
							</td>
						</tr>
						<tr class="postFormBox">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>收件人：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.addressee" id="addressee"
									type="text" size="85" maxlength="30"
									value="<s:property value="lastInvoiceInfo.addressee"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
							</td>
						</tr>
						<tr class="postFormBox">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>联系电话：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.phone" id="tel" type="text"
									size="85" value="<s:property value="lastInvoiceInfo.phone"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
							</td>
						</tr>
						<tr>
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>购买方电话：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.gmfdh" id="gmfdh" type="text"
									size="85" value="<s:property value="lastInvoiceInfo.gmfdh"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
							</td>
						</tr>
						<tr>
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>购买方邮箱：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.email" id="email" type="text"
									size="85" value="<s:property value="lastInvoiceInfo.email"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
							</td>
						</tr>
						<tr class="postFormBox">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>冲红原因：</span>
							</td>
							<td>
								<input name="remark" id="tel" type="text"
									size="85" value=""
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;"/>
							</td>
						</tr>
						<tr>
							<td style="border: none; text-align: center;" colspan="2">
								<table border="0" width="100%">
									<tr>
										<td style="border: none; width: 40%;">
											<div class="ck"
												style="float: right; width: 70px; background-image: url('/entity/bzz-students/images/index_21.jpg')">
												<a onclick="isSubmit();" href="#">确定</a>
											</div>
										</td>
										<td style="border: none; width: 20%;">
											<div class="ck"
												style="float: center; width: 70px; background-image: url('/entity/bzz-students/images/index_21.jpg')">
												<a onclick="cleanAll();" href="#">清除</a>
											</div>
										</td>
										<td style="border: none; width: 40%;">
											<div class="ck"
												style="float: left; width: 70px; background-image: url('/entity/bzz-students/images/index_21.jpg')">
												<a onclick="returnOrder();" href="#">返回</a>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<input type="hidden" name="peBzzInvoiceInfo.province" id="provincex" />
			<input type="hidden" name="peBzzInvoiceInfo.city" id="cityx" />
		</form>
	</body>
	<script type="text/javascript">
		function isSubmit() {
			var invoiceTitle = $('#invoiceTitle').val();
			
			var addressee = $('#addressee').val();
			var tel = $('#tel').val();
				if(invoiceTitle == '' || invoiceTitle == null) {
					alert('付款单位（个人）不能为空');
					return;
				} else if(addressee=='' || addressee == null) {
					alert('收件人不能为空');
					return;
				} else if(tel == '' || tel == null) {
					alert('联系电话不能为空');
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
			$('#form').submit();
		}
		
		function returnOrder() {
			window.location.href="/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action";
		}
function initShengShi() {
	with (document.form.province) {
		length = where.length;
		var ss = 0;
		var goalP = '<s:property value='lastInvoiceInfo.province' escape='false'/>';
		for (k = 0; k < where.length; k++) {
			options[k].text = where[k].loca;
			options[k].value = where[k].loca;
			if(goalP == where[k].loca) {
				ss = k;
			}
		}
		options[selectedIndex].text = where[ss].loca;
		options[selectedIndex].value = where[ss].loca;
	}
	with (document.form.city) {
		loca3 = (where[ss].locacity).split("|");
		length = loca3.length;
		var s = 0;
		var goal = '<s:property value='lastInvoiceInfo.city' escape='false'/>';
		for (l = 0; l < length; l++) {
			options[l].text = loca3[l];
			options[l].value = loca3[l];
			if(goal == loca3[l]) {
				s = l;
			}
		}
		options[selectedIndex].text = loca3[s];
		options[selectedIndex].value = loca3[s];
	}
}
$(document).ready(function() {
	var p = '<s:property value="lastInvoiceInfo.province" escape='false'/>';
	var c = '<s:property value="lastInvoiceInfo.city" escape='false'/>';
	var d = '<s:property value="lastInvoiceInfo.address" escape='false'/>'
	document.getElementById('addressx').value = d.substring((p+c).length);
		
	$("#isInvoice").change(
			function() {
				if ($("#isInvoice").val() == '1') {
					$("#invoice").css("display","block");
				}else{
					$("#invoice").css("display","none");	
				}
});});
		
		function cleanAll(){
			var invoices = document.getElementsByName("invoiceType");
			invoices[0].checked = true;
			document.getElementById("invoiceTitle").value = "";
			document.getElementById("province").options[0].selected = true;
			selectShengShi();
			document.getElementById("city").options[1].selected = true;
			document.getElementById("hd_address").value="";
			document.getElementById("addressx").value="";
			document.getElementById("address").value="";
			document.getElementById("post").value="";
			document.getElementById("addressee").value="";
			document.getElementById("tel").value="";
			showPostType();
		}
		
	</script>
</html>
<script type="text/javascript">
	canChose();
</script>
