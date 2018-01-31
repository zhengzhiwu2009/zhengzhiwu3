<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
	<head>
		<title>订单查询</title>
		<link rel="stylesheet" type="text/css" href="./js/extjs/resources/css/ext-all.css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript" src="js/shared.js"></script>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<table width="101.5%" border="0" cellspacing="0" cellpadding="4" align="center">
			<tr>

				<td>
					<div class="content_title">
						按订单查询
					</div>
				</td>
			</tr>
		</table>
		<div id="tab">
			<div id="tab_user2course" style="width: 100.73%">
				<form name="form1" method="post" action="" id="form1" target="_blank">
					<div class="cntent_k">
						<table width="98%" border="0" align="center" cellpadding="2" cellspacing="3" class="tdbg2">
							<tr>
								
								<td class="tdbg2" align="center"  width="25%">
									订单号：
									<input name="seq" type="text" size="16" maxlength="30" id="seq">
								</td>
								<td width="25%" align="center">
									订单别名：
									<input name="name" type="text" size="16" maxlength="30" id="name">
								</td>
								<td class="tdbg2" align="center"  width="25%">
									支付账号：
									<input name="loginid" type="text" size="16" maxlength="30" id="loginid">
								</td>
								<td width="25%" align="center">
									订单金额：
									<input name="amount" type="text" size="16" maxlength="30" id="amount">
								</td>								
								<td width="10%" align="center">
									<INPUT class=button onclick="doSubmit();" type="button" value="&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;">
								</td>
								<td width="25%">&nbsp;</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	function doSubmit() {
		var seq = document.getElementById('seq').value;
		var name = document.getElementById('name').value;
		var loginid = document.getElementById('loginid').value;
		var amount = document.getElementById('amount').value;
		if((seq == null || seq == '') && (name == null || name == '') &&
			(loginid == null || loginid == '') && (amount == null || amount == '')) {
			alert('请至少填写一项!');
			return;
		}
		var r = /^\+?[1-9][0-9]*$/;　　//正整数 
		if(amount != null &&　amount != ''){
      		if(!r.test(amount)){
      			alert('订单金额请输入正整数!');
      			return;
      		}
      	}
		document.getElementById('form1').action = "/entity/teaching/peBzzTchOrderSearch.action?seq=" + seq + "&name=" + encodeURI(name,"UTF-8") + "&loginid=" + loginid + "&amount=" + amount;
		document.getElementById('form1').submit();
	}
	
</script>
</html>
