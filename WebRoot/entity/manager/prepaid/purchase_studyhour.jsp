<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<script language="JavaScript" src="js/shared.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>购买学时</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="/entity/manager/css/pan_Order.css" rel="stylesheet"
			type="text/css">
		<script language="JavaScript"
			src="/web/bzz_index/password/js/jquery-1.7.1.min.js"></script>
	</head>
	<body>
		<form action="/entity/basic/buyClassHour_hourNum.action" method="post"
			id="form">
			<div class="warp">
					<h1>购买学时</h1>
							<table width="890" border="0" cellspacing="0" cellpadding="0" class="datalist">
								<tr>
									<th width="164">
										<div align="right">输入购买课时数：</div>
									</th>
									<td width="726">
										<input name="hourNum" style="width:150px" id="hourNum" type="text"
											size="3" maxlength="15" class="txt1">
									</td>
								</tr>
								<tr>
									<th>
										<div align="right">课时单价：</div>
									</th>
									<td>
										<font color="red"><s:property value="enumConst.name"/>元</font>/课时
									</td>
								</tr>
								<tr>
									<th>
										<div align="right">总&nbsp;&nbsp;金&nbsp;&nbsp;额：</div>
									</th>
									<td>
										<span id="totalAmount"><font color="red">0</font> </span>&nbsp;&nbsp;元
									</td>
								</tr>
							</table>
							<div class="btn">
								<a href="#" onclick="isSubmit();"><img src="/web/bzz_index/images/btn99.png" width="97" height="25" /></a>
							</div>
		</form>
	</body>
	<script type="text/javascript">
$(document).ready(function(){
	$('#hourNum').focus();
	
	$('#hourNum').change(function(){
		var num = $.trim($('#hourNum').val());
		var len = num.length;
		if(len==0){
			alert('请输入数字');
			return;
		}
		for(var i=0; i<len; i++) {
			var c=num.charAt(i).charCodeAt(0); 
			if(c<48 || c>57) {
				alert('数据不合法');
				return;
			} 
		}
		var x = (parseFloat(num)*parseFloat(<s:property value="enumConst.name"/>)).toString();
		var n = x.indexOf('.');
		if (n==-1) {
			var money = x+".00";
		} else {
			var money = x.substring(0, n+3);
		}
		$('#totalAmount').html('<font color="red">'+(money)+'</font>');
	});
 	
 });
function isSubmit() {

		var num = $.trim($('#hourNum').val());
		var len = num.length;
		if(len==0) {
			alert('请输入数字');
			return;
		}
		for(var i=0; i<len; i++) {
			var c=num.charAt(i).charCodeAt(0); 
			if(c<48 || c>57) {
				alert('数据不合法');
				return;
			} 
		}
		if(!(num*1 > 0)){
			alert('请输入大于0的学时数');
			return;
		}
	$('#form').submit();
}

</script>
</html>
