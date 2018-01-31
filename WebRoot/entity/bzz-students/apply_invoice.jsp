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
		double a = (Double) request.getAttribute("amount");
		String orderSeqs = (String) request.getAttribute("orderSeqs");
		orderSeqs = orderSeqs.substring(0, orderSeqs.length() - 1);
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
	<body onload="" leftmargin="0" topmargin="0">
		<form
			action="/entity/workspaceStudent/studentWorkspace_saveInvoiceInfo.action"
			method="post" id="form" name="form">
			&nbsp;
			<input type="hidden" name="id" id="id"
				value="<s:property value="#request.id" />" />
			<div style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					发票申请
				</div>
				<div class="main_txt">
					<table width="100%" id="invoice" class="datalist" width="100%"
						border="0" cellpadding="0" cellspacing="0">
						<tr class="postFormBox">
							<td colspan="2" style="color: red;" align="left">
								1、请认真如实填写或补充完整下面的地址信息，我们将发票邮寄至您填写的地址
								<br />
								2、当此订单金额小于1000元时，学员将无法点击选择增值税专用发票，页面提供提示信息，因财务制度要求如需申请
								<br />
								增值税专用发票，则可通过多笔订单合并(金额>=1000)方式申请
							</td>
						</tr>
						<%
							if (orderSeqs != null && !"".equals(orderSeqs) && orderSeqs.split("|").length > 1) {
						%>
						<tr class="postFormBox">
							<td colspan="2" style="color: red;" align="left">
								合并申请包含订单：<%=orderSeqs%>
							</td>
						</tr>
						<%
							}
						%>
						<tr class="postFormBox">
							<td bgcolor="#E9F4FF" width="20%">
								<span class="name" style="float: right"><font color="red">*</font>发票类型：</span>
							</td>
							<td align="left">
								<input type="radio" name="invoiceType" value="3" checked
									onclick="showPostType()" />
								电子发票&nbsp;&nbsp;&nbsp;
								<input type="radio" name="invoiceType" value="4"
									onclick="showPostType()" />
								增值税专用发票
							</td>
						</tr>
						<tr class="postFormBox">
							<td bgcolor="#E9F4FF" width="20%">
								<span class="name" style="float: right"><font color="red">*</font>发票金额：</span>
							</td>
							<td align="left">
								<%=a%>
							</td>
						</tr>
						<tr class="postFormBox">
							<td bgcolor="#E9F4FF" width="20%">
								<span class="name" style="float: right"><font color="red">*</font>付款单位(个人)：</span>
							</td>
							<td >
								<input name="peBzzInvoiceInfo.title" id="invoiceTitle" title="请慎重填写！如发票抬头为单位或机构名称，则务必与所在单位或机构财务确认后再进行填写。"
									type="text" size="85" maxlength="100" 
									value="<s:property value="lastInvoiceInfo.title"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
							</td>
						</tr>
						<tr class="postFormBox" id="yjdzTr" style="display: none">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>邮寄地址：</span>
							</td>
							<td align="left">
								省（市）：
								<select id="province" name="province"
									onChange="selectShengShi()">
									<s:property value="lastInvoiceInfo.province" escape="false" />
								</select>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 市（区）：
								<select id="city" name="city" onChange="selectShengShi()">
									<s:property value="lastInvoiceInfo.city" escape="false" />
								</select>
								<input id="hd_address" type="hidden" size="60" class="txt1"
									maxlength="100" />
								<input name="addressx" id="addressx" type="text" size="85"
									class="txt1" maxlength="100"
									value="<s:property value="lastInvoiceInfo.address" escape="false"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
								<input name="peBzzInvoiceInfo.address" id="address"
									type="hidden" size="60" class="txt1" maxlength="100" />
							</td>
						</tr>
						<tr class="postFormBox" id="yzbmTr" style="display: none">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>邮政编码：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.zipCode" id="post" type="text"
									size="85" value="<s:property value="lastInvoiceInfo.zipCode"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
							</td>
						</tr>
						<tr class="postFormBox" id="sjrTr" style="display: none">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>收件人：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.addressee" id="addressee"
									type="text" size="85" maxlength="30"
									value="<s:property value="lastInvoiceInfo.addressee"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
							</td>
						</tr>
						<tr class="postFormBox" id="sjrdhTr" style="display: none">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>收件人电话：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.phone" id="tel" type="text"
									size="85" value="<s:property value="lastInvoiceInfo.phone"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
							</td>
						</tr>
						<tr id="gmfnsrsbhTr" style="display: none">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>购买方纳税人识别号：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.gmfnsrsbh" id="gmfnsrsbh"
									type="text" size="85"
									value="<s:property value="lastInvoiceInfo.gmfnsrsbh"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
							</td>
						</tr>
						<tr id="gmfdzTr" style="display: none">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>购买方地址：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.gmfdz" id="gmfdz" type="text"
									size="85" value="<s:property value="lastInvoiceInfo.gmfdz"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
							</td>
						</tr>
						<tr id="gmfsjhmTr">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>购买方手机号码：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.gmfsjhm" id="gmfsjhm" type="text"
									size="85" value="<s:property value="lastInvoiceInfo.gmfsjhm"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
							</td>
						</tr>
						<tr id="gmfdhTr" style ="display:none">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>购买方电话：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.gmfdh" id="gmfdh" type="text"
									size="85" value="<s:property value="lastInvoiceInfo.gmfdh"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
							</td>
						</tr>
						<tr>
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>购买方邮箱：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.email" id="email" type="text"
									size="85" value="<s:property value="lastInvoiceInfo.email"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
							</td>
						</tr>
						<tr id="gmfkhyhTr" style="display: none">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>购买方开户行：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.gmfkhyh" id="gmfkhyh" type="text"
									size="85" value="<s:property value="lastInvoiceInfo.gmfkhyh"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
							</td>
						</tr>
						<tr id="gmfyhzhTr" style="display: none">
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">*</font>购买方银行账号：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.gmfyhzh" id="gmfyhzh" type="text"
									size="85" value="<s:property value="lastInvoiceInfo.gmfyhzh"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
							</td>
						</tr>
						<tr>
							<td bgcolor="#E9F4FF">
								<span class="name" style="float: right"><font color="red">&nbsp;</font>发票开具描述：</span>
							</td>
							<td>
								<input name="peBzzInvoiceInfo.invoiceRemark" id="invoiceRemark"
									type="text" size="85"
									value="<s:property value="lastInvoiceInfo.invoiceRemark"/>"
									style="float: left; height: 20px; background-color: #FAFAFA; border: #7F9DB9 1px solid;" />
							</td>
						</tr>
						<tr class="postFormBox">
									<td colspan="2" style="color: red;">
										"发票开具描述"中填写的内容将打印在增值税专用发票的"备注"项中，请慎重填写！
										<br>
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
		
			var reg = /^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;//固话
			var myreg1=/^1[3|4|5|8][0-9]\d{4,8}$/; //手机
			var myreg2=/^1[0-9]{10}$/;
			
			var invoiceTitle = $('#invoiceTitle').val();
			var address = $('#address').val();
			var post = $('#post').val();
			
			var ss = document.getElementById('hd_address').value;
			var de = document.getElementById('addressx').value;
			address = document.getElementById('address').value = ss+de;
			
			var addressee = $('#addressee').val();
			
			var invoiceType = document.getElementsByName('invoiceType');
			if(invoiceType[0].checked) {
			var gmfsjhm = $('#gmfsjhm').val();
				if(invoiceTitle == '' || invoiceTitle == null) {
					alert('付款单位（个人）不能为空');
					return;
				} else{
					if(invoiceTitle.length >50){
					alert('付款单位名称长度不能超过50个汉字');
					return ;
					}
				}
				if(gmfsjhm == '' || gmfsjhm == null) {
					alert('购买方手机号码不能为空');
					return;
				}else{
					if(gmfsjhm.length >20 ){
						alert('购买方手机号码不正确');
						return;
					}
				}
			}	
				if(invoiceType[1].checked) {
					var invoiceType = $('#invoiceType').val();
					var province = $('#province').val();
					var city = $('#city').val();
					var tel = $('#tel').val();
					var gmfnsrsbh = document.getElementById('gmfnsrsbh').value;
					var gmfdz = document.getElementById('gmfdz').value;
					var gmfkhyh = document.getElementById('gmfkhyh').value;
					var gmfyhzh = document.getElementById('gmfyhzh').value;
					var gmfdh = document.getElementById('gmfdh').value;
					var gmfyx = document.getElementById('email').value;
					var len = gmfdz.replace(/[^\x00-\xff]/g, "**").length;
					if(invoiceTitle == '' || invoiceTitle == null) {
						alert('付款单位（个人）不能为空');
						return;
					} else{
						if(invoiceTitle.length >50){
						alert('付款单位名称长度不能超过50个汉字');
						return ;
						}
					}
					if(province == null || province == '' || province == '请选择省份名') {
						alert('请选择省市');
						return false;
					}
					if(city == null || city == '' || city == '请选择省份名') {
						alert('请选择市');
						return false;
					} 
					if(gmfnsrsbh == '' || gmfnsrsbh == null) {
						alert('购买方纳税人识别号不能为空');
						return;
					} else if(gmfnsrsbh.length > 20 || gmfnsrsbh.length <15) {
						alert('购买方发票识别号不正确 ！\请重新输入');
						return ;
					} else if(addressee=='' || addressee == null) {
						alert('收件人不能为空');
						return;
					} else if(addressee.length >10){
						alert('收件人长度不能大于10个汉字');
						return ;
					} else if (tel =='' || tel == null ){
						alert('收件人电话不能为空');
						return false;
					} else if(tel.length >20){
						alert('收件人电话不正确，\n请重新输入');
						return false;
					}else if(address == '' || address == null || de=='' || de == null) {
						alert('收件地址不能为空');
						return;
					} else if(address.length >90){
						alert('收件人地址长度不能大于90个汉字');
						return ;
					}else if(post=='' || post == null) {
						alert('邮政编码不能为空');
						return;
					} else if(gmfdz == '' || gmfdz == null) {
						alert('购买方地址不能为空');
						return;
					} else if(len > 80) {
						alert('购买方地址长度过长');
						return ;
					} else if(gmfdh == '' || gmfdh == null) {
						alert('购买方电话不能为空');
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
						alert('购买方银行账号输入不正确，\n请重新输入');
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
				}

					//if(!reg.test(tel)&&(!myreg1.test(tel)||!myreg2.test(tel))){
						//alert("收件人电话应为座机或手机！\n请重新输入");
					//	$('#tel').focus();
					//	return false;
				//	}
					
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
		function showPostType(){
			var invoices = document.getElementsByName("invoiceType");
			if(invoices[0].checked) {
				document.getElementById("gmfsjhmTr").style.display = "";
				document.getElementById("sjrTr").style.display = "none";
				document.getElementById("yjdzTr").style.display = "none";
				document.getElementById("yzbmTr").style.display = "none";
				document.getElementById("gmfnsrsbhTr").style.display = "none";
				document.getElementById("gmfdhTr").style.display = "none";
				document.getElementById("gmfdzTr").style.display = "none";
				document.getElementById("gmfkhyhTr").style.display = "none";
				document.getElementById("gmfyhzhTr").style.display = "none";
				document.getElementById("sjrdhTr").style.display = "none";
			} else {
				document.getElementById("gmfdhTr").style.display = "";
				document.getElementById("gmfsjhmTr").style.display = "none";
				document.getElementById("sjrTr").style.display = "block";
				document.getElementById("yjdzTr").style.display = "block";
				document.getElementById("yzbmTr").style.display = "block";
				document.getElementById("gmfnsrsbhTr").style.display = "block";
				document.getElementById("gmfdzTr").style.display = "block";
				document.getElementById("gmfkhyhTr").style.display = "block";
				document.getElementById("gmfyhzhTr").style.display = "block";
				document.getElementById("sjrdhTr").style.display = "block";
			}
		}
		
		function cleanAll(){
			var invoices = document.getElementsByName("invoiceType");
			invoices[0].checked = true;
			document.getElementById("invoiceTitle").value = "";
			document.getElementById("province").options[0].selected = true;
			selectShengShi();
			document.getElementById("hd_address").value="";
			document.getElementById("addressx").value="";
			document.getElementById("address").value="";
			document.getElementById("post").value="";
			document.getElementById("addressee").value="";
			document.getElementById("tel").value="";
			document.getElementById("gmfnsrsbh").value="";
			document.getElementById("gmfdz").value="";
			document.getElementById("gmfdh").value="";
			document.getElementById("email").value="";
			document.getElementById("gmfkhyh").value="";
			document.getElementById("gmfyhzh").value="";
			document.getElementById("invoiceRemark").value="";
			showPostType();
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
		
		function initShengShi(config) {
	var goalP,goal,p,c,d,ss=0,s=0;
	if(config){
		goalP=config.goalP; //省份初始值
		goal=config.goal;//城市初始值
		p=config.p;//城市初始值
		c=config.c;//城市初始值
		d=config.d;//城市初始值
	}
	if(p&&c&&d){
		document.getElementById('addressx').value = d.substring((p+c).length);
	}
	
	if($("#isInvoice").length>0 && $(".radioItem").length>0){
		$("#isInvoice").change(
				function() {
					if ($("#isInvoice").val() == '1' || $("#isInvoice").val()== 'y') {
						$("#invoice").css("display","block");
					}else{
						$("#invoice").css("display","none");
					}
		});
		$(".radioItem").change(
			function() {
				var val,$selectedvalue;
				if($(":radio[name='paymentMethod'][checked]").val()){
					val = $(":radio[name='paymentMethod'][checked]").val();
					$selectedvalue = $("input[name='paymentMethod']:checked").val();
				}else if($(":radio[name='payMethod'][checked]").val()){
					val = $(":radio[name='payMethod'][checked]").val();
					$selectedvalue = $("input[name='payMethod']:checked").val();
				}
				if ($selectedvalue == 1 || $selectedvalue == '1') {
					//$("#msg").css("display","block");
					$("#paymentInfo").css("display","none");
					
					$("#op1").attr("selected",true); 
					$("#isInvoice").change();
					$("#isInvoice").attr("disabled",true);
				}else if($selectedvalue != 0 && $selectedvalue != '0'){
					$("#paymentInfo").css("display","block");
					//$("#msg").css("display","none");
					$("#isInvoice").attr("disabled",false); 
				}else{
					//$("#msg").css("display","none");
					$("#paymentInfo").css("display","none");
					$("#isInvoice").attr("disabled",false); 
				}
			}
		);
	}
	if(document.form.province){
		with (document.form.province) {
			length = where.length;
			
			
			for (k = 0; k < where.length; k++) {
				options[k].text = where[k].loca;
				options[k].value = where[k].loca;
				if(goalP && goalP == where[k].loca) {
					ss = k;
				}
			}
			options[ss].selected=true;
			//options[selectedIndex].text = where[ss].loca;
			//options[selectedIndex].value = where[ss].loca;
		}
	}
	if(document.form.city){
		with (document.form.city) {
			loca3 = (where[ss].locacity).split("|");
			length = loca3.length;
			
			for (l = 0; l < length; l++) {
				options[l].text = loca3[l];
				options[l].value = loca3[l];
				if(goal && goal == loca3[l]) {
					s = l;
				}
			}
			options[s].selected=true;
			//options[selectedIndex].text = loca3[s];
			//options[selectedIndex].value = loca3[s];
		}
	}
	selectShengShi();
}
	</script>
</html>
<script type="text/javascript">
	canChose();
</script>
