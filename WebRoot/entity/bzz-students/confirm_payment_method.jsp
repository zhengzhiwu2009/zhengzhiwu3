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
</head>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"
		class="" >
		<form action="/entity/workspaceStudent/studentWorkspace_submitOrder.action" method="post" id="form">
		<div class="" style="width:100%;padding:0;margin:0; " align="center">
      	<div class="main_title">选择支付方式</div>
     	<div class="main_txt">
	  		<table style="border-color:#c7c7c7;no-repeat;border-width:1px; 
	  		border-style:solid;font-family:Verdana, Arial, Helv, Helvetica, sans-serif;" cellpadding="0" cellspacing="0"  width="100%">
	  			<tr style="font-weight:600;">
	  				<td>
	  					<table width="100%" border="0" cellpadding="0"
							cellspacing="0" bgcolor="#FAFAFA">
							<tr>
								<th align="right" width="50%">
									<font size="3" style="font-weight:500;">本次选课共：</font>&nbsp;&nbsp;&nbsp;</th>
								<td height="28" class=""  align="left" width="15%">	
									<font color="red" size="3" style="font-weight:500;"><s:property value="#session.peBzzOrderInfo.classHour"/>&nbsp;</font>
								</td>
								<td align="left" width="35%">
									<font size="3" style="font-weight:500;">学时&nbsp;&nbsp;</font>
								</td>
							</tr>
							<tr>
							<th align="right" width="50%">
								<font size="3" style="font-weight:500;">需要支付共：</font>&nbsp;&nbsp;&nbsp;
							</th>
							<td height="28" class=""  align="left" width="15%">	
								<font color="red" size="3" style="font-weight:500;"><s:property value="#session.peBzzOrderInfo.amount"/>&nbsp;</font>
							</td>
							<td align="left" width="35%">
									<font size="3" style="font-weight:500;">元&nbsp;&nbsp;</font>
							</td>
							</tr>
							<s:if test="#session.orderType!=2">
							<tr>
								<th align="right" width="50%">	
									<font size="3" style="font-weight:500;">预付费账户余额：</font>&nbsp;&nbsp;&nbsp;
								</th>
								<td height="28" class=""  align="left" width="15%">	
									<font color="red" size="3" style="font-weight:500;"><s:property value="#request.sumAmount-#request.amount"/>&nbsp;</font>
								</td>
								<td align="left" width="35%">
									<font size="3" style="font-weight:500;">学时&nbsp;&nbsp;</font>
								</td>
							</tr>
							</s:if>
							<tr align="center">
								<table width="100%" border="0" cellpadding="0"
							cellspacing="0" align="center" bgcolor="#FAFAFA">
									<tr>
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
									</tr>
								</table>
							</tr>
						</table>
						<br />
						<table width="98%" height="100%" border="0" cellpadding="0"
							cellspacing="0">
							<tr align="center">
								<td width="30%">&nbsp;</td>
    								<td  style="border:none; padding-left:5px;"><div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_23.jpg')" >
	    								<a href="#" onclick="javascript:window.history.back()">上一步</a></div></td>	
									<td  style="border:none; padding-right:5px;"><div class="ck" style="width:70px;background-image:url('/entity/bzz-students/images/index_21.jpg')" >
										<a onclick="check();" href="#">支&nbsp;付</a> </div></td>
								<td width="30%">&nbsp;</td>
							</tr>
						</table>
						</td>
						</tr>
						</table>
					</div>
				</div>
		</form>
	</body>
	<script type="text/javascript">
		function check() {
			var val = $(":radio[name='paymentMethod'][checked]").val();
			if(val == null){
				alert("请选择支付方式");
				return;
			}
			var answer = confirm("确定要支付吗");
			if (answer) {
				$("#form").submit();
			} else {
				return;
			}
		}
	</script>
</html>