<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/common.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="ColsMyView.js"></script>
<script type="text/javascript" src="cols.js"></script>

<title>Insert title here</title>

<script type="text/javascript">
Ext.onReady(function() {

	var centersm = new Ext.grid.CheckboxSelectionModel();
	var centercm = new Ext.grid.ColumnModel([
		    {mtext:"基本信息",mcol:2,mwidth:200,header:"rid",dataIndex:"rid",width:100,align:"center"}, 
		    {header:"rolename",dataIndex:"rolename",width:100,align:"center"}, 
		    
		    {mtext:"角色信息",mcol:2,header:"角色",mwidth:220,width:110,dataIndex:"rolename",align:"center"}, 
		    {header : "性别",width:110,align:"center",dataIndex:"sex",align:"center"}, 
		   
		    {mtext: "个人信息",mcol:3,header:"单位",mwidth:330,width:110,align:"center",dataIndex:"unit"}, 
		    {header : "地址",width:110,align:"center",dataIndex:"address"}, 
		    {header : "父母",width:110,align:"center",dataIndex : "parents"}
	    ]);

	var centerdata = [{
	     rid : 423,
	     rolename : "管理员",
	     sex:"女",
	     unit:"sjdjja",
	     address:"jjdsjjfs",
	     parents:"xiaoming"
	    }, {
	     rid : 434,
	     rolename : "库管",
	     sex:"男",
	     unit:"sjdjja",
	     address:"jjdsjjfs",
	     parents:"xiaoming"
	    }, {
	     rid : 546,
	     rolename : "采样员",
	     sex:"女",
	     unit:"sjdjja",
	     address:"jjdsjjfs",
	     parents:"xiaoming"
	    }, {
	     rid : 778,
	     rolename : "注射员",
	     sex:"男",
	     unit:"sjdjja",
	     address:"jjdsjjfs",
	     parents:"xiaoming"
	    }, {
	     rid : 443,
	     rolename : "化验员",
	     sex:"女",
	     unit:"sjdjja",
	     address:"jjdsjjfs",
	     parents:"xiaoming"
	    }]
	    
	var centerproxy = new Ext.data.MemoryProxy(centerdata);
	var centeruser = Ext.data.Record.create([{
	     name : "rid",
	     type : "int",
	     mapping : "rid"
	    }, {
	     name : "rolename",
	     type : "string",
	     mapping : "rolename"
	    }, {
	     name : "sex",
	     type : "string",
	     mapping : "sex"
	    }, {
	     name : "unit",
	     type : "string",
	     mapping : "unit"
	    }, {
	     name : "address",
	     type : "string",
	     mapping : "address"
	    }, {
	     name : "parents",
	     type : "string",
	     mapping : "parents"
	    }]);

	var centerreader = new Ext.data.JsonReader({}, centeruser);

	var centerstore = new Ext.data.GroupingStore({
	     proxy : centerproxy,
	     reader : centerreader,
	     sortInfo : {
	        field : 'rid',
	        direction : "ASC"
	       },
	     groupField : 'sex',
	     autoLoad : true
	    });

	var grid = new Ext.grid.GridPanel({
	     region : "center",
	     renderTo : Ext.getBody(),
	     store : centerstore,
	     cm : centercm,
	     sm : centersm,
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

