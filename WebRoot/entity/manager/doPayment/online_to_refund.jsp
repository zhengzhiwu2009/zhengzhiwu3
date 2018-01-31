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
				<input name="amount" type="hidden" value="<s:property value="amountsum"/>">	
				<input name="cause" type="hidden" value="<s:property value="cause"/>">
				 <input name="appuser" type="hidden" value="<s:property value="appuser"/>">		
				 <input name="mac" type="hidden" value="<s:property value="mac"/>">		
				 <input name="version" type="hidden" value="<s:property value="version"/>">		
	</form>
</body>
</html>
