<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/common.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="ColsMyView.js"></script>

<title>Insert title here</title>

<script type="text/javascript">
Ext.onReady(function() {

	var cmFields = [
		    {mtext:"基本信息",mcol:2,header:"rid",dataIndex:"rid",width:100,align:"center"}, 
		    {header:"rolename",dataIndex:"rolename",width:100,align:"center"}, 
		    
		    {mtext:"角色信息",mcol:2,header:"角色",width:110,dataIndex:"rolename",align:"center"}, 
		    {header : "性别",width:110,align:"center",dataIndex:"sex",align:"center"}, 
		   
		    {mtext: "个人信息",mcol:3,header:"单位",width:110,align:"center",dataIndex:"unit"}, 
		    {header : "地址",width:110,align:"center",dataIndex:"address"}, 
		    {header : "父母",width:110,align:"center",dataIndex : "parents"}
	    ];
    
	var grid = new Ext.whaty.ux.grid.QueryPageGrid({
		 ck:false,
		 proxyUrl:'/whatyessh/demo/peixun/927/grid/colSpanGridData.json',
	     region : "center",
	     renderTo : Ext.getBody(),
	     cmField : cmFields,
	     id : "centerid",
	     view : new MyGridView(viewConfig),//忘加了就绝对不会出来
	     height : 500,
	     loadMask : {
	      msg : '正在加载数据,请稍后...'
	     },
	     bodyStyle : "width:33%",
	     width:800

	 });

});
</script>


</head>  
<body>
</body>  
</html>

