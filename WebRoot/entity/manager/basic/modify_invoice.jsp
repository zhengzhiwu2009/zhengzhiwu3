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
  <script src="/js/shengshi.js" type="text/javascript"></script>
</head>
	<body leftmargin="0" topmargin="0">
		<form
			action="/entity/basic/modifyInvoice_modifyInvoiceInfo.action"
			method="post" id="form" name="form">
			
			<input type="hidden" name="id" value="<s:property value="peBzzInvoiceInfo.id"/>"/>	
			<div  style="width: 100%; padding: 0; margin: 0;"
				align="center">
						<div class="main_title">
							发票信息修改
						</div>
						<div class="main_txt">
									<table width="100%" id="invoice" class="datalist" width="100%"  border="0" cellpadding="0" cellspacing="0" >
											<tr class="postFormBox">
												<td colspan="2" style="color: red;">
													请认真如实填写或补充完整下面的地址信息，我们将发票邮寄至您填写的地址，请务必如实填写<br/>
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF" width="15%">
													<span class="name" style="float:right"><font color="red" >*</font>付款单位（个人）：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.title" id="invoiceTitle" type="text" size="85" maxlength="100"
														style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
														 value="<s:property value="peBzzInvoiceInfo.title"/>">
												</td>
											</tr>
											
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float:right"><font color="red">*</font>邮政编码：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.zipCode" id="post" type="text" size="85"
														style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
														value="<s:property value="peBzzInvoiceInfo.zipCode"/>">
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float:right"><font color="red">*</font>收件人：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.addressee" id="addressee" type="text" size="85" maxlength="30"
														style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
														value="<s:property value="peBzzInvoiceInfo.addressee"/>">
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float:right"><font color="red">*</font>联系电话：</span>
												</td>
												<td>
													<input name="peBzzInvoiceInfo.phone" id="tel" type="text" size="85"
														style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;"
														value="<s:property value="peBzzInvoiceInfo.phone"/>">
												</td>
											</tr>
											<tr class="postFormBox">
												<td bgcolor="#E9F4FF">
													<span class="name" style="float:right"><font color="red" >*</font>邮寄地址：</span>
												</td>
												<td align="left">
												省（市）：<select name="province" id="province" onChange = "selectShengShi()"><s:property value="peBzzInvoiceInfo.province" escape="false"/></select> 
												市（区）：<select name="city" id="city" onChange = "selectShengShi()" ><s:property value="peBzzInvoiceInfo.city" escape="false" /></select>
													<input  id="hd_address"  type="hidden" size="85" class="txt1" maxlength="100"/>
													<input  id="addressx"  type="text" size="85" class="txt1" maxlength="100"/>
													<input name="peBzzInvoiceInfo.address" id="address"  type="hidden" size="85" maxlength="100"
														style="float:left;height:20px;background-color:#FAFAFA;border: #7F9DB9 1px solid;">
													
												</td>
											</tr>
											<s:if test="peBzzInvoiceInfo.enumConstByFlagFpOpenState.name == '待开'">
												<tr class="postFormBox">
													<td bgcolor="#E9F4FF">
														<span class="name" style="float:right"><font color="red">&nbsp;</font>邮寄方式：</span>
													</td>
													<td>
														<input type="radio" name="postType" <s:if test="peBzzInvoiceInfo.enumConstByFlagPostType.code == '01'">checked=checked</s:if> value="01" />挂号信&nbsp;&nbsp;
														<input type="radio" name="postType" <s:if test="peBzzInvoiceInfo.enumConstByFlagPostType.code == '02'">checked=checked</s:if> value="02" />快递到付&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">订单金额在10万元以上的，无需选择发票邮寄方式。协会将采用快递方式寄送发票，快递费用由协会承担。</span>
													</td>
												</tr>
											</s:if>
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
	<script type="text/javascript">
		function modify() {
			if(checkInvoice()){
				$('#form').submit();
			}
		}
		
		function returnOrder() {
			history.go(-1);
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
