<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/include/common.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style type="text/css">
	/*rowspan grid合并行效果*/  
	.rowspan-grid .x-grid3-body .x-grid3-row {  
	    border:none;  
	    cursor:default;  
	    width:100%;  
	}  
	.rowspan-grid .x-grid3-header .x-grid3-cell{  
	    /*border-left: 2px solid transparent;*//*ie6的transparent下显示黑色?*/  
	    border-left: 2px solid #fff;  
	}  
	.rowspan-grid .x-grid3-body .x-grid3-row{  
	    overflow: hidden;  
	    border-right: 1px solid #ccc;  
	}  
	.rowspan-grid .x-grid3-body .x-grid3-cell {  
	    border: 1px solid #ccc;  
	    border-top:none;  
	    border-right:none;  
	}  
	.rowspan-grid .x-grid3-body .x-grid3-cell-first {  
	    /*border-left: 1px solid transparent;*/  
	    border-left: 1px solid #fff;  
	}  
	.rowspan-grid .x-grid3-body .rowspan-unborder {  
	    /*border-bottom:1px solid transparent;*/  
	    border-bottom:1px solid #fff;  
	}  
	.rowspan-grid .x-grid3-body .rowspan {  
	    border-bottom: 1px solid #ccc;  
	}
</style>

<script type="text/javascript">  
        Ext.onReady(function() {  
         var myData = [  
                ['供电所1',6090,'大工业用电户数',15,'公用线',5,100,100,'公用变',57,100,100],  
                ['供电所1',6090,'非普工业用电户数',15,'公用线',5,100,100,'公用变',57,100,100],  
                ['供电所1',6090,'商业用电户数',15,'公用线',5,100,100,'公用变',57,100,100],  
                ['供电所1',6090,'住宅用电户数',15,'公用线',5,100,100,'公用变',57,100,100],  
                ['供电所1',6090,'售户数',15,'公用线',5,100,100,'公用变',57,100,100],  
                  
                ['供电所1',6090,'城区用户数',0,'专用线',0,100,100,'专用变',51,100,100],  
                ['供电所1',6090,'非城区用户数',0,'专用线',0,100,100,'专用变',51,100,100],  
                ['供电所1',6090,'低压用户数',41,'专用线',0,100,100,'专用变',51,100,100],  
                ['供电所1',6090,'未完成一户一表改造户数',0,'专用线',0,100,100,'专用变',51,100,100],  
                  
                ['供电所2',6090,'大工业用电户数',15,'公用线',5,100,100,'公用变',57,100,100],  
                ['供电所2',6090,'非普工业用电户数',15,'公用线',5,100,100,'公用变',57,100,100],  
                ['供电所2',6090,'商业用电户数',15,'公用线',5,100,100,'公用变',57,100,100],  
                ['供电所2',6090,'住宅用电户数',15,'公用线',5,100,100,'公用变',57,100,100],  
                ['供电所2',6090,'售户数',15,'公用线',5,100,100,'公用变',57,100,100],  
                  
                ['供电所2',6090,'城区用户数',0,'专用线',0,100,100,'专用变',51,100,100],  
                ['供电所2',6090,'非城区用户数',0,'专用线',0,100,100,'专用变',51,100,100],  
                ['供电所2',6090,'低压用户数',41,'专用线',0,100,100,'专用变',51,100,100],  
                ['供电所2',6090,'未完成一户一表改造户数',0,'专用线',0,100,100,'专用变',51,100,100]  
               ];  
               // create the data store  
            var store = new Ext.data.ArrayStore({  
                fields: [  
                   {name: 'pass_name'},  
                   {name: 'user_num'},  
                   {name: 'user_name' },  
                   {name: 'user_count'},  
                   {name: 'dedicated_line' },  
                   {name: 'dedicated_num'},  
                   {name: 'colnum7'},  
                   {name: 'colnum8'},  
                   {name: 'colnum9'},  
                   {name: 'colnum10'},  
                   {name: 'colnum11'},  
                   {name: 'colnum12'}  
                ]  
            });
            store.loadData(myData);
            
            var grid = new Ext.grid.GridPanel({  
                region:'center',  
                store: store,  
                columns: [  
                  	{id:'pass_name', header: 'pass_name', width: 75,dataIndex: 'pass_name', rowspan: ['pass_name']},  
                    {header: 'user_num', width: 75,dataIndex: 'user_num', rowspan: ['pass_name']},  
                    {header: 'user_name', width: 75,dataIndex: 'user_name'},  
                    {header: 'user_count', width: 75,dataIndex: 'user_count'},  
                    {header: 'dedicated_line', width: 75,dataIndex: 'dedicated_line', rowspan: ['dedicated_line']},  
                    {header: 'dedicated_num', width: 75,dataIndex: 'dedicated_num', rowspan: ['dedicated_line']},  
                    {header: 'colnum7', width: 75,dataIndex: 'colnum7', rowspan: ['pass_name']},  
                    {header: 'colnum8', width: 75,dataIndex: 'colnum8', rowspan: ['pass_name']},  
                    {header: 'colnum9', width: 75,dataIndex: 'colnum9', rowspan: ['colnum9']},  
                    {header: 'colnum10', width: 75,dataIndex: 'colnum10', rowspan: ['colnum9']},  
                    {header: 'colnum11', width: 75,dataIndex: 'colnum11', rowspan: ['pass_name']},  
                    {header: 'colnum12', width: 75,dataIndex: 'colnum12', rowspan: ['pass_name']}  
                ],  
                stripeRows: true,  
                autoExpandColumn: 'pass_name',  
                title: 'Array Grid',  
                stateful: true,  
                stateId: 'grid',  
                renderTo:'TestGridDiv',
                height:500,
                cls: 'rowspan-grid',//******必须配置此样式  
                view: new Ext.whaty.ux.grid.RowSpanView() //****使用view  
            });
        });  
    </script>  
</head>  
<body>
	<div id="TestGridDiv" style="height: 500px;"></div>
</body>  
</html>

