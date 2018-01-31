<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>发票冲红</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/shared.css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<script language="JavaScript" src="js/shared.js"></script>
		<script language="JavaScript"
			src="/web/bzz_index/password/js/jquery-1.7.1.min.js"></script>
		<script src="/js/shengshi.js" type="text/javascript"></script>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<form action="/entity/basic/buyClassHourRecord_applyInvoice.action"
			method="post" id="form" name="form">
			<input type="hidden" name="id" value="<s:property value="id" />" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="28">
						<div class="content_title" id="zlb_title_start">
							发票冲红
						</div id="zlb_title_end">
					</td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder" style="background: none;">
						<div class="border" style="padding-left: 30px">
							<table style="text-align: left;" width="96%" border="0"
								cellpadding="0" cellspacing="0">
								<tr class="postFormBox">
									<td colspan="2" style="color: red;">
										&nbsp;请认真如实填写或补充完整下面的地址信息，我们将发票邮寄至您填写的地址<br>
										《当此订单金额小于1000元时，学员将无法点击选择增值税专用发票，
										因财务制度要求如需申请增值税专用发票，则可通过多笔订单合并(金额>=1000)方式申请》<br>
									
									</td>
								</tr>
								<s:if test="strSeq != null">
								<tr class="postFormBox">
									<td colspan="2" style="">
										合并的订单号：<s:property value ="strSeq" /> &nbsp;&nbsp;
										合并金额：<s:property value="amount" /> &nbsp;&nbsp;&nbsp;
										<span style="width: 200px; height: 15px; padding-top: 3px"><a
										href="#" onclick="avascript:window.history.back()">取消合并订单</a> </span>		
									</td>
								</tr>
								</s:if>
								<tr class="postFormBox">
									<td>
										<span class="name">发票类型：</span> 
									</td>
									<td align="left">
										<input type="radio" id="PTFP" name="invoiceType" onclick="invoice(this.id)" <s:if test="lastInvoiceInfo.enumConstByInvoiceType.id == '4028808c1e1f9f08011e1fa750cc000dx'" >checked="checked"</s:if>
											value="01" />
										增值税普通电子发票&nbsp;&nbsp;
										<input type="radio" id ="ZYFP" name="invoiceType" disabled="disabled" onclick="invoice(this.id)" <s:if test="lastInvoiceInfo.enumConstByInvoiceType.id == '4028808c1e1f9f08011e1fa750cc000ex'" >checked="checked"</s:if>value="02" />
										增值税专用发票&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr class="postFormBox" id="a1">
									<td>
										<span class="name">付款单位（个人）：</span>
									</td>
									<td>
										<input name="peBzzInvoiceInfo.title" id="invoiceTitle"
											type="text"
											value="<s:property value="lastInvoiceInfo.title"/>" size="85"
											maxlength="100">
									</td>
								</tr>
								<tr class="postFormBox" id="a2" style="display:none">
									<td>
										<span class="name">邮寄地址：</span>
									</td>
									<td align="left">
										省（市）：
										<select name="province" id="province"
											onChange="selectShengShi()">
											<s:property value="lastInvoiceInfo.province" escape="false" />
										</select>
										市（区）：
										<select name="city" id="city" onChange="selectShengShi()">
											<s:property value="lastInvoiceInfo.city" escape="false" />
										</select>
										<br />
										<input id="hd_address" type="hidden" size="60" class="txt1"
											maxlength="100" />
										<input name="addressx" id="addressx" type="text" size="60"
											class="txt1" maxlength="100"
											value="<s:property value="lastInvoiceInfo.address" escape="false"/>" />
										<input name="peBzzInvoiceInfo.address" id="address"
											type="hidden" size="60" class="txt1" maxlength="100" />
									</td>
								</tr>
								<tr class="postFormBox" id="a3" style="display:none">
									<td>
										<span class="name">邮政编码：</span>
									</td>
									<td>
										<input name="peBzzInvoiceInfo.zipCode" id="post" type="text"
											size="85"
											value="<s:property value="lastInvoiceInfo.zipCode"/>"
											maxlength="15">
									</td>
								</tr>
								<tr class="postFormBox" id="a4" style="display:none">
									<td>
										<span class="name">收&nbsp;&nbsp;件&nbsp;&nbsp;人：</span>
									</td>
									<td>
										<input name="peBzzInvoiceInfo.addressee" id="addressee"
											type="text" size="85"
											value="<s:property value="lastInvoiceInfo.addressee"/>"
											maxlength="15">
									</td>
								</tr>
								<tr class="postFormBox" id="a5">
									<td>
										<span class="name">联系电话：</span>
									</td>
									<td>
										<input name="peBzzInvoiceInfo.phone" id="tel" type="text"
											size="85" value="<s:property value="lastInvoiceInfo.phone"/>"
											maxlength="30">
									</td>
								</tr>
								<tr class="postFormBox" id="a6" style="display:none">
									<td>
										<span class="name">购买方纳税人识别号：</span>
									</td>
									<td>
										<input name="peBzzInvoiceInfo.gmfnsrsbh" id="gmfnsrsbh" type="text"
											size="85" value="<s:property value="lastInvoiceInfo.gmfnsrsbh"/>"
											maxlength="30">
									</td>
								</tr>
								<tr class="postFormBox" id="a7" style="display:none">
									<td>
										<span class="name">购买方地址：</span>
									</td>
									<td>
										<input name="peBzzInvoiceInfo.gmfdz" id="gmfdz" type="text"
											size="85" value="<s:property value="lastInvoiceInfo.gmfdz"/>"
											maxlength="30">
									</td>
								</tr>
								<tr class="postFormBox" id="a8" style="display:none">
									<td>
										<span class="name">购买方电话：</span>
									</td>
									<td>
										<input name="peBzzInvoiceInfo.gmfdh" id="gmfdh" type="text"
											size="85" value="<s:property value="lastInvoiceInfo.gmfdh"/>"
											maxlength="30">
									</td>
								</tr>
								<tr class="postFormBox" id="a18">
									<td>
										<span class="name">购买方邮箱：</span>
									</td>
									<td>
										<input name="peBzzInvoiceInfo.email" id="email" type="text"
											size="85" value="<s:property value="lastInvoiceInfo.email"/>"
											maxlength="30">
									</td>
								</tr>
								<tr class="postFormBox" id="a9" style="display:none">
									<td>
										<span class="name">购买方开户行：</span>
									</td>
									<td>
										<input name="peBzzInvoiceInfo.gmfkhyh" id="gmfkhyh" type="text"
											size="85" value="<s:property value="lastInvoiceInfo.gmfkhyh"/>"
											maxlength="30">
									</td>
								</tr>
								<tr class="postFormBox" id="a10" style="display:none">
									<td>
										<span class="name">购买方银行账号：</span>
									</td>
									<td>
										<input name="peBzzInvoiceInfo.gmfyhzh" id="gmfyhzh" type="text"
											size="85" value="<s:property value="lastInvoiceInfo.gmfyhzh"/>"
											maxlength="30">
									</td>
								</tr>
								<tr class="postFormBox" id="postType"  style="display:none"> 
									<td>
										<span class="name">邮寄方式：</span>
									</td>
									<td align="left">
										<input type="radio" name="postType"<s:if test="lastInvoiceInfo.enumConstByFlagPostType.code==01"> checked="checked"</s:if>
											value="01" />
										挂号信&nbsp;&nbsp;
										<input type="radio" name="postType" value="02" <s:if test="lastInvoiceInfo.enumConstByFlagPostType.code==02"> checked="checked"</s:if>/>
										快递到付&nbsp;&nbsp;&nbsp;&nbsp;
										<span style="color:red"> 如选择快递到付方式，则请务必填写准确的联系电话</span>
									</td>
								</tr>
								<tr class="postFormBox" id="postType" style=""> 
									<td colspan="2" >
									</td>
								</tr>
								<tr class="postFormBox" id="a5">
									<td ><span class="name">发票冲红原因：</span></td>
									<td> <textarea name="resetRemark" onfocus="onsub();"  id ="area" cols ="50" rows = "2">请填写冲红原因</textarea></td>
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
									<span style="width: 46px; height: 15px; padding-top: 3px"><a
										onclick="isSubmit();" style="cursor: pointer;">冲红</a> </span>
										&nbsp;&nbsp;&nbsp;
									<span style="width: 46px; height: 15px; padding-top: 3px"><a
										href="#" onclick="qingkong();">清空</a> </span>									
									&nbsp;&nbsp;&nbsp;
									<span style="width: 46px; height: 15px; padding-top: 3px"><a
										href="#" onclick="javascript:window.history.back()">返回</a> </span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<input type="hidden" name="peBzzInvoiceInfo.province" id="provincex" />
			<input type="hidden" name="peBzzInvoiceInfo.city" id="cityx" />
		</form>
		<input type="hidden" id="forward"
			value="<s:property value="forward"/>">
	</body>
	<script type="text/javascript">
	 	 if(document.getElementById('PTFP').checked == true){
	    	document.getElementById('postType').style.display="none";
	   	 }
	     if(document.getElementById('ZYFP').checked == true){
	    	document.getElementById('postType').style.display="";
	    	document.getElementById('a6').style.display="";
			document.getElementById('a7').style.display="";
			document.getElementById('a9').style.display="";
			document.getElementById('a10').style.display="";
	    }
		function isSubmit() {
			//var orderAlias = $('#orderAlias').val();
			//var isInvoice = $('#isInvoice').val();
			var forward = $('#forward').val();
			
			if(forward==null||forward==''){
				$('form').attr('action','/entity/basic/buyClassHourRecord_applyInvoice.action');
			}else{
				$('form').attr('action',forward);
			}
			if(checkInvoice()){
				$('#form').submit();
			}
		}
		function invoice(id){
			if(id =='PTFP'){
				document.getElementById('postType').style.display="none";
			}
			if(id =='ZYFP'){
				document.getElementById('postType').style.display="";
				}
		}
		function qingkong(){
			document.getElementById('invoiceTitle').value ="";
			document.getElementById('province').value ="";
			document.getElementById('city').value="";
			document.getElementById('addressx').value="";
			document.getElementById('post').value ="";
			document.getElementById('addressee').value ="";
			document.getElementById('tel').value ="";
			document.getElementById('area').value="";
		}
		function onsub(){
			document.getElementById('area').value="";
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
