<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
	<head>
		<script type="text/javascript">
			function formLoad(){
				var payForm = document.getElementById("payForm");
				payForm.submit();
			}
		</script>
	<%--  onload="document.forms.payform.submit();" --%>
	</head>
	<body onload="formLoad()">
		<%-- Lee sandbox为测试地址www为生产地址 --%>
		<form id="payForm" action="https://www.99bill.com/gateway/recvMerchantInfoAction.htm" method="post">
			<input type="hidden" name="inputCharset" value="<s:property value='inputCharset'/>" />
			<input type="hidden" name="pageUrl" value="<s:property value='pageUrl'/>" /><br />
			<input type="hidden" name="bgUrl" value="<s:property value='bgUrl'/>" /><br />
			<input type="hidden" name="version" value="<s:property value='version'/>" />
			<input type="hidden" name="language" value="<s:property value='language'/>" />
			<input type="hidden" name="signType" value="<s:property value='signType'/>" />
			<input type="hidden" name="signMsg" value="<s:property value='signMsg'/>" />
			<input type="hidden" name="merchantAcctId" value="<s:property value='merchantAcctId'/>" />
			<input type="hidden" name="payerName" value="<s:property value='payerName'/>" /><br />
			<input type="hidden" name="payerContactType" value="<s:property value='payerContactType'/>" /><br />
			<input type="hidden" name="payerContact" value="<s:property value='payerContact'/>" /><br />
			<input type="hidden" name="orderId" value="<s:property value='orderId'/>" />
			<input type="hidden" name="orderAmount" value="<s:property value='orderAmount'/>" />
			<input type="hidden" name="orderTime" value="<s:property value='orderTime'/>" />
			<input type="hidden" name="productName" value="<s:property value='productName'/>" /><br />
			<input type="hidden" name="productNum" value="<s:property value='productNum'/>" /><br />
			<input type="hidden" name="productId" value="<s:property value='productId'/>" /><br />
			<input type="hidden" name="productDesc" value="<s:property value='productDesc'/>" /><br />
			<input type="hidden" name="ext1" value="<s:property value='ext1'/>" /><br />
			<input type="hidden" name="ext2" value="<s:property value='ext2'/>" /><br />
			<input type="hidden" name="payType" value="<s:property value='payType'/>" />
			<input type="hidden" name="bankId" value="<s:property value='bankId'/>" /><br />
			<input type="hidden" name="redoFlag" value="<s:property value='redoFlag'/>" /><br />
			<input type="hidden" name="pid" value="<s:property value='pid'/>" /><br />
		</form>
	</body>
</html>
