<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/common/ext.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="DivGrid.js"></script>

<script type="text/javascript">

	Ext.onReady(function(){
		var grid = new Ext.peixun.grid.DivGrid({
				renderTo:'TestGridDiv'
			});
	});
		
</script>

</head>
<body>
	<div id="TestGridDiv" style="height: 500px;"></div>
</body>
</html>

