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
		<script type="text/javascript">
$(document).ready(function() {
	window.opener.document.location.reload();
	$("a").click(
			function() {
				var radioTemp = $(":radio[value='"+$(this).attr('id')+"']");
				radioTemp.attr("checked",true);
	});
	if(navigator.userAgent.indexOf("MSIE")>0) {  
    } 
	else{
		alert("系统检测到您的浏览器与大部分银行支付不兼容\n为了您能顺利完成支付，建议使用IE8及以上版本浏览器进行付款。")
	}
});
 function doSubmit(){
  	var flag = false;
  		 $(":radio").each(function(){
   			 if($(this).attr("checked") == "checked"){
   			 	flag = true;
   			 }
 	 });
 	 	if(flag == false){
 	 		alert("请选择支付银行！");
 	 		return false;
 	 	}
  		document.forms.batch.submit();
  		//opener.location.reload();
  		//window.opener = null;
  		//window.close();
  }
</script>
	</head>
	<body leftmargin="0" topmargin="0">
		<form name="batch" action="/entity/basic/onlinePayment_doPayment.action" method="post">
			<input type="hidden" name="amountsum" value='<s:property value="#session.peBzzOrderInfo.amount" />' />
			<input type="hidden" name="merorderid" value='<s:property value="#session.peBzzOrderInfo.seq" />' />
			<input type="hidden" name="pdtdnm" value='<s:property value="#session.peBzzOrderInfo.seq" />' />
			<div class="" style="width: 100%; padding: 0; margin: 0;" align="center">
				<div class="main_title">
					选择支付银行
				</div>
				<div class="msg" width="98%" style="background-color: #FAFAFA">
					订单号：
					<font color="red"><s:property value="#session.peBzzOrderInfo.seq" /> </font> &nbsp;|&nbsp; 支付金额：
					<font color="red"><s:property value="#session.peBzzOrderInfo.amount" />&nbsp;元&nbsp;&nbsp;</font>
				</div>
				<div class="main_txt">
					<table width="96%" border="0" align="left" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="content_title" id="zlb_title_start">
									选择支付银行
								</div id="zlb_title_end">
							</td>
						</tr>
						<tr>
							<td valign="top" class="pageBodyBorder" style="background: none;">
								<div class="border" style="padding-left: 20px;">
									<table width="96%" cellpadding="0" cellspacing="0">
										<tr>
											<td height="28" class="">
												订单号：
												<font color="red"><s:property value="#session.peBzzOrderInfo.seq" /> </font> &nbsp;|&nbsp; 支付金额：
												<font color="red"><s:property value="#session.peBzzOrderInfo.amount" />&nbsp;元&nbsp;&nbsp;</font>
											</td>
										</tr>
									</table>
									<br />
									<table width="96%" cellpadding="0" cellspacing="5" border="0">
										<tr height="50">
											<td>
												<input type="radio" name="merbank" value="CCB" />
											</td>
											<td>
												<a href="javascript:void(0);" id="CCB" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_ccb_s.gif" width="129" height="25" border="0" title="中国建设银行" /> </a>	
											</td>
											<td>
												<input type="radio" name="merbank" value="CEB" />
											</td>
											<td>
												<a href="javascript:void(0);" id="CEB" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_ceb_s.gif" width="129" height="25" border="0" title="光大银行" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="ICBC" />						
											</td>
											<td>
												<a href="javascript:void(0);" id="ICBC" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_icbc_s.gif" width="129" height="25" border="0" title="中国工商银行" /> </a>	
											</td>
											<td>
												<input type="radio" name="merbank" value="BOC" />
											</td>
											<td>
												<a href="javascript:void(0);" id="BOC" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_boc_s.gif" width="129" height="25" border="0" title="交通银行" /> </a>					
											</td>
										</tr>
										<tr height="50">
											<td>
												<input type="radio" name="merbank" value="CITIC" />
											</td>
											<td>
												<a href="javascript:void(0);" id="CITIC" onclick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_itic_s.gif" width="129" height="25" border="0" title="中信银行" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="GDB" />			
											</td>
											<td>
												<a href="javascript:void(0);" id="GDB" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_gdb_s.gif" width="129" height="25" border="0" title="广东发展银行" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="CMB" />
											</td>
											<td>
												<a href="javascript:void(0);" id="CMB" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_cmb_s.gif" width="129" height="25" border="0" title="招商银行" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="CMSB" />		
											</td>
											<td>
												<a href="javascript:void(0);" id="CMSB" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_cmsb_s.gif" width="129" height="25" border="0" title="中国民生银行" /> </a>
											</td>
										</tr>
										<tr height="50">
											<td>
												<input type="radio" name="merbank" value="POST" />
											</td>
											<td>
												<a href="javascript:void(0);" id="POST" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_post_s.gif" width="129" height="25" border="0" title="中国邮政" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="BCN" />
											</td>
											<td>
												<a href="javascript:void(0);" id="BCN" onclick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_bcn_s.gif" width="129" height="25" border="0" title="中国银行" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="ABC" />
											</td>
											<td>
												<a href="javascript:void(0);" id="ABC" onclick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_abc_s.gif" width="129" height="25" border="0" title="中国农业银行" /> </a>
											</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										<tr style="display:none;">
											<td colspan="8">------------------隐藏银行------------------隐藏银行------------------隐藏银行------------------隐藏银行------------------</td>
										</tr>
										<tr height="50" style="display:none;">
											<td>
												<input type="radio" name="merbank" value="BOS" />
											</td>
											<td>
												<a href="javascript:void(0);" id="BOS" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_bos_s.gif" width="129" height="25" border="0" title="上海银行" /> </a>			
											</td>
											<td>
												<input type="radio" name="merbank" value="NBBANK" />		
											</td>
											<td>
												<a href="javascript:void(0);" id="NBBANK" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_nbbank_s.gif" width="129" height="25" border="0" title="宁波银行" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="FUDIAN" />	
											</td>
											<td>
												<a href="javascript:void(0);" id="FUDIAN" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_fudian_s.gif" width="129" height="25" border="0" title="富滇银行" /> </a>	
											</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										<tr height="50" style="display:none;">
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>
												<input type="radio" name="merbank" value="CIB" />
											</td>
											<td>
												<a href="javascript:void(0);" id="CIB" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_cib_s.gif" width="129" height="25" border="0" title="兴业银行" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="SPDB" />
											</td>
											<td>
												<a href="javascript:void(0);" id="SPDB" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_spdb_s.gif" width="129" height="25" border="0" title="上海浦东发展银行" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="HZCB" />
											</td>
											<td>
												<a href="javascript:void(0);" id="HZCB" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_hzcb_s.gif" width="129" height="25" border="0" title="杭州银行" /> </a>
											</td>
										</tr>
										<tr height="50" style="display:none;">
											<td>
												<input type="radio" name="merbank" value="BJRCB" />
											</td>
											<td>
												<a href="javascript:void(0);" id="BJRCB" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_bjrcb_s.gif" width="129" height="25" border="0" title="北京农村商业银行" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="PAB" />
											</td>
											<td>
												<a href="javascript:void(0);" id="PAB" onClick=""> <img src="/entity/manager/images/bankLogo/b2c/icon_pab_s.gif" width="129" height="25" border="0" title="平安银行" /> </a>
											</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										<tr height="50" style="display:none;">
										  	<td>
										  		<input type="radio" name="merbank" value="B2B_ZSYH" />
									  		</td>
											<td>
												<a href="javascript:void(0);" id="B2B_ZSYH" onClick=""> <img src="/entity/manager/images/bankLogo/b2b/icon_b2b_zsyh_s.gif" width="129" height="25" border="0" title="招商银行B2B" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="B2B_ABC" />
											</td>
											<td>
												<a href="javascript:void(0);" id="B2B_ABC" onClick=""> <img src="/entity/manager/images/bankLogo/b2b/icon_b2b_abc_s.gif" width="129" height="25" border="0" title="中国农业银行B2B" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="B2B_BCN" />
											</td>
											<td>
												<a href="javascript:void(0);" id="B2B_BCN" onClick=""> <img src="/entity/manager/images/bankLogo/b2b/icon_b2b_bcn_s.gif" width="129" height="25" border="0" title="中国银行B2B" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="B2B_SPDB" />
											</td>
											<td>
												<a href="javascript:void(0);" id="B2B_SPDB" onClick=""> <img src="/entity/manager/images/bankLogo/b2b/icon_b2b_spdb_s.gif" width="129" height="25" border="0" title="上海浦东发展银行B2B" /> </a>
											</td>
									    </tr>
										<tr style="display:none;">
											<td colspan="8">------------------隐藏银行------------------隐藏银行------------------隐藏银行------------------隐藏银行------------------</td>
										</tr>
										<tr height="50">
											<td>
												<input type="radio" name="merbank" value="B2B_CCB" />
											</td>
											<td>
												<a href="javascript:void(0);" id="B2B_CCB" onClick=""> <img src="/entity/manager/images/bankLogo/b2b/icon_b2b_ccb_s.gif" width="129" height="25" border="0" title="中国建设银行B2B" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="B2B_ICBC" />
											</td>
											<td>
												<a href="javascript:void(0);" id="B2B_ICBC" onClick=""> <img src="/entity/manager/images/bankLogo/b2b/icon_b2b_icbc_s.gif" width="129" height="25" border="0" title="中国工商银行B2B" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="B2B_CEB" />
											</td>
											<td>
												<a href="javascript:void(0);" id="B2B_CEB" onClick=""> <img src="/entity/manager/images/bankLogo/b2b/icon_b2b_ceb_s.gif" width="129" height="25" border="0" title="光大银行B2B" /> </a>
											</td>
											<td>
												<input type="radio" name="merbank" value="B2B_CMSB" />
											</td>
											<td>
												<a href="javascript:void(0);" id="B2B_CMSB" onClick=""> <img src="/entity/manager/images/bankLogo/b2b/icon_b2b_cmsb_s.gif" width="129" height="25" border="0" title="民生银行B2B" /> </a>
											</td>
										</tr>
									</table>
									<br />
									<table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
										<tr align="center">
											<td width="30%">&nbsp;</td>
											<td style="border: none; padding-right: 5px;">
												<div class="ck" style="width: 70px; background-image: url('/entity/bzz-students/images/index_21.jpg')">
													<a href="#" onclick="doSubmit();">支付</a>
												</div>
											</td>
											<td style="border: none; padding-left: 5px;">
												<div class="ck" style="width: 70px; background-image: url('/entity/bzz-students/images/index_23.jpg')">
													<a href="JavaScript:window.close();">关闭</a>
												</div>
											</td>
											<td width="30%">&nbsp;</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>
