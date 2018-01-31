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

         var myData = {root:[
                       {pass_name:'供电所1',user_num:'6090',user_name:'大工业用电户数',user_count:15,dedicated_line:'公用线',dedicated_num:5},
                       {pass_name:'供电所1',user_num:'6090',user_name:'非普工业用电户数',user_count:15,dedicated_line:'公用线',dedicated_num:5},
                       {pass_name:'供电所1',user_num:'6090',user_name:'商业用电户数',user_count:15,dedicated_line:'公用线',dedicated_num:5},
                       {pass_name:'供电所1',user_num:'6090',user_name:'住宅用电户数',user_count:15,dedicated_line:'公用线',dedicated_num:5},

                       {pass_name:'供电所1',user_num:'6090',user_name:'城区用户数',user_count:0,dedicated_line:'专用线',dedicated_num:0},
                       {pass_name:'供电所1',user_num:'6090',user_name:'非城区用户数',user_count:0,dedicated_line:'专用线',dedicated_num:0},
                       {pass_name:'供电所1',user_num:'6090',user_name:'低压用户数',user_count:41,dedicated_line:'专用线',dedicated_num:0},
                       {pass_name:'供电所1',user_num:'6090',user_name:'未完成一户一表改造户数',user_count:0,dedicated_line:'专用线',dedicated_num:0},

                       {pass_name:'供电所2',user_num:'6090',user_name:'城区用户数',user_count:0,dedicated_line:'公用线',dedicated_num:10},
                       {pass_name:'供电所2',user_num:'6090',user_name:'非城区用户数',user_count:0,dedicated_line:'公用线',dedicated_num:10},
                       {pass_name:'供电所2',user_num:'6090',user_name:'低压用户数',user_count:41,dedicated_line:'公用线',dedicated_num:10},
                       {pass_name:'供电所2',user_num:'6090',user_name:'未完成一户一表改造户数',user_count:0,dedicated_line:'公用线',dedicated_num:10},

                       {pass_name:'供电所2',user_num:'6090',user_name:'城区用户数',user_count:0,dedicated_line:'专用线',dedicated_num:0},
                       {pass_name:'供电所2',user_num:'6090',user_name:'非城区用户数',user_count:0,dedicated_line:'专用线',dedicated_num:0},
                       {pass_name:'供电所2',user_num:'6090',user_name:'低压用户数',user_count:41,dedicated_line:'专用线',dedicated_num:0},
                       {pass_name:'供电所2',user_num:'6090',user_name:'未完成一户一表改造户数',user_count:0,dedicated_line:'专用线',dedicated_num:0}
                       ]
         	};  
            // create the data store  
            var store = new Ext.data.JsonStore({  
            	root: 'root',
                fields: [  
                   {name: 'pass_name'},  
                   {name: 'user_num'},  
                   {name: 'user_name' },  
                   {name: 'user_count'},  
                   {name: 'dedicated_line' },  
                   {name: 'dedicated_num'}
                ]  
            });
            store.loadData(myData);
            
            var grid = new Ext.grid.GridPanel({  
                region:'center',  
                store: store,  
                columns: [  
                  	{id:'pass_name', header: 'pass_name', width: 200,dataIndex: 'pass_name', rowspan: ['pass_name']},  
                    {header: 'user_num', width: 100,dataIndex: 'user_num', rowspan: ['pass_name']},  
                    {header: 'user_name', width: 150,dataIndex: 'user_name'},  
                    {header: 'user_count', width: 100,dataIndex: 'user_count'},  
                    {header: 'dedicated_line', width: 150,dataIndex: 'dedicated_line', rowspan: ['dedicated_line']},  
                    {header: 'dedicated_num', width: 100,dataIndex: 'dedicated_num', rowspan: ['dedicated_line']}
                ],  

                stripeRows: true,  
     			//autoExpandColumn: 'pass_name',  
                title: 'Json Grid',  
                //stateful: true,  
                //stateId: 'grid',  
                renderTo:'TestGridDiv',
                height:500,
                cls: 'rowspan-grid',//******必须配置此样式  
                
                view: new Ext.whaty.ux.grid.RowSpanView({
                	forceFit: true
                }) //****使用view  
            });
        });  
    </script>  
</head>  
<body>
	<div id="TestGridDiv" style="height: 500px;"></div>
</body>  
</html>

