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

<script type="text/javascript" src="ExQueryPageGrid.js"></script>

<script type="text/javascript">  
        Ext.onReady(function() {

            var grid = new Ext.whaty.ux.grid.ExQueryPageGrid({
                ck:false,  
            	proxyUrl:'/whatyessh/demo/peixun/927/grid/rowspandata4.json',

                region:'center',
                cmField: [  
                  	{header: 'pass_name', width: 200,dataIndex: 'pass_name', rowspan: ['pass_name']},  
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
                })
              
            });
        });  
    </script>  
</head>  
<body>
	<div id="TestGridDiv" style="height: 500px;"></div>
</body>  
</html>

