<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/include/common.jsp"></jsp:include>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="SearchForm.js"></script>
<script type="text/javascript" src="MyGrid.js"></script>
<script type="text/javascript" src="MyFormWindow.js"></script>

<script type="text/javascript">

	Ext.onReady(function(){

			var searchForm = new Ext.peixun.grid.SearchForm({
					title:'搜索Form',
					region:'north',
					height:150,
					listeners:{
						scope:this,
						search:function(param){
							searchGrid.reload(param);//底下的grid
						}
					}
				});

			var searchGrid = new Ext.peixun.grid.MyGrid({
					region:'center'
				});
			
			new Ext.Viewport({
					layout:'border',
					items:[searchForm,searchGrid]
				});
			
		});
		
</script>

</head>
<body>
	
</body>
</html>

