<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/include/common.jsp"></jsp:include>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="MyGrid.js"></script>
<script type="text/javascript" src="MyFormWindow.js"></script>

<script type="text/javascript">

	Ext.onReady(function(){
			var grid = new Ext.peixun.grid.MyGrid({
					region:'center',
					
					allowSearch:true,//toolbar上的搜索
					searchFieldName:'name'
				});
			new Ext.Viewport({
					layout:'border',
					items:[grid]
				});
		});
	
</script>

</head>
<body>
	
</body>
</html>

