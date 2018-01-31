<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
<head>
<script type="text/javascript">

</script>
</head>
<body onload="document.forms.payform.submit();">
	<form action="<s:property value="url"/>" method="post" name="payform">
		<input name="merchantid" type="hidden" value="<s:property value="merchantid"/>">
		<input name="merorderid" type="hidden" value="<s:property value="merorderid"/>">
		<input name="amountsum" type="hidden" value="<s:property value="amountsum"/>">
		<input name="subject" type="hidden" value="<s:property value="subject"/>">
		<input name="currencytype" type="hidden" value="<s:property value="currencytype"/>">
		<input name="autojump" type="hidden" value="<s:property value="autojump"/>">
		<input name="waittime" type="hidden" value="<s:property value="waittime"/>">
		<input name="merurl" type="hidden" value="<s:property value="merurl"/>">
		<input name="informmer" type="hidden" value="<s:property value="informmer"/>">
		<input name="informurl" type="hidden" value="<s:property value="informurl"/>">
		<input name="confirm" type="hidden" value="<s:property value="confirm"/>">
		<input name="merbank" type="hidden" value="<s:property value="merbank"/>">
		<input name="tradetype" type="hidden" value="<s:property value="tradetype"/>">
		<input name="bankInput" type="hidden" value="<s:property value="bankInput"/>">
		<input name="interface" type="hidden" value="<s:property value="strInterface"/>">
		<input name="mac" type="hidden" value="<s:property value="mac"/>">
		<input name="remark" type="hidden" value="<s:property value="remark"/>">
		
		<input name="bankcardtype" type="hidden" value="<s:property value="bankcardtype"/>">
		<input name="pdtdetailurl" type="hidden" value="<s:property value="pdtdetailurl"/>">
		<input name="pdtdnm" type="hidden" value="<s:property value="pdtdnm"/>">
	</form>
</body>
</html>
