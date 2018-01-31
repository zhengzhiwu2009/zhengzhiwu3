<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/include/common.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="ExQueryPageGrid.js"></script>

<script type="text/javascript" src="ColsMyView.js"></script>

<script type="text/javascript">
Ext.onReady(function() {

	var cmFields = [
		    {mtext:"基本信息",mcol:2,mwidth:200,header:"rid",dataIndex:"rid",width:100,align:"center"}, 
		    {header:"rolename",dataIndex:"rolename",width:100,align:"center"}, 
		    
		    {mtext:"角色信息",mcol:2,header:"角色",mwidth:200,width:100,dataIndex:"rolename",align:"center"}, 
		    {header : "性别",width:100,align:"center",dataIndex:"sex",align:"center"}, 
		   
		    {mtext: "个人信息",mcol:3,header:"单位",mwidth:300,width:100,align:"center",dataIndex:"unit"}, 
		    {header : "地址",width:100,align:"center",dataIndex:"address"}, 
		    {header : "父母",width:100,align:"center",dataIndex : "parents"}
	    ];
    
	var grid = new Ext.whaty.ux.grid.ExQueryPageGrid({
		 ck:false,
		 proxyUrl:'/whatyessh/demo/peixun/927/grid/colSpanGridData3.json',
	     region : "center",
	     renderTo : Ext.getBody(),
	     cmField : cmFields,
	     view : new MyGridView(viewConfig),//忘加了就绝对不会出来
	     height : 500,
	     bodyStyle : "width:33%",
	     width:800

	 });

});
</script>


</head>  
<body>
</body>  
</html>

