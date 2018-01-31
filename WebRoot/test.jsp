<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<title>demo</title>
<head>
<script type="text/javascript">
function doSubmit(){
	window.location.href="/servlet/TestServlet?k=" + document.getElementById('k').value;
}
</script>
</head>
<body>
<form>
	<input type="text" id="k" value="" />
	<input type="button" onclick="doSubmit()" value="查询"/>
</form>
</body>
</html>
