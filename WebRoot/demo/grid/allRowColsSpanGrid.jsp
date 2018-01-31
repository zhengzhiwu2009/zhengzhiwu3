<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/common/ext.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style type="text/css">
	.x-grid3-header-inner{
		float: left;
    	overflow: hidden;
    	background:#dfe8f6;
	}
	.rowspan-grid .x-grid3-body .x-grid3-row {  
	    border:#dfe8f6;  
	}  
	.rowspan-grid .x-grid3-header .x-grid3-cell{  
	    height:35px;
	    font-size:14px;
	    text-align:center;
	    background:#dfe8f6;
	}  
	.rowspan-grid .x-grid3-body .x-grid3-row{  
	    overflow: hidden;  
	    border-right: 1px solid #dfe8f6; 
	}  
	.rowspan-grid .x-grid3-body .x-grid3-cell {  
	    border: 1px solid #dfe8f6;  
	    border-top:none;  
	    border-right:none;  
	}  
	.rowspan-grid .x-grid3-body .x-grid3-cell-first {  
	    border-left: 1px solid #fff;  
	}  
	.rowspan-grid .x-grid3-body .rowspan-unborder {  
	    border-bottom:1px solid #fff;  
	}  
	.rowspan-grid .x-grid3-body .rowspan {  
	    border-bottom: 1px solid #dfe8f6;  
	}
	.x-grid3-hd-inner{
		height:34px;
		cursor: inherit;
		line-height:34px;
	    position: relative;
	}
</style>

<script type="text/javascript" src="ExQueryPageGrid.js"></script>

<script type="text/javascript">  
        Ext.onReady(function() {

            var grid = new Ext.whaty.ux.grid.ExQueryPageGrid({
                ck:false,  
            	proxyUrl:'/entity/statistics/prBzzStatistics_registStat.action',

                region:'center',
                cmField: [  
                  	{header: 'pass_name',mcol:1,mtext:"基本信息",,dataIndex: 'pass_name', rowspan: ['pass_name']},  

                    {header: 'user_num',mcol:3,mtext:"用户信息",,dataIndex: 'user_num', rowspan: ['pass_name']},  
                    {header: 'user_name', dataIndex: 'user_name'},  
                    {header: 'user_count', dataIndex: 'user_count'},  
                    
                    {header: 'dedicated_line',mcol:2,mtext:"其他信息",dataIndex:'dedicated_line', rowspan:['dedicated_line']},  
                    {header: 'dedicated_num', dataIndex: 'dedicated_num', rowspan: ['dedicated_line']}
                ],  

                stripeRows: true,  
     			//autoExpandColumn: 'pass_name',  
                //stateful: true,  
                //stateId: 'grid',  
                renderTo:'TestGridDiv',
                height:100%,
                width:100%,
                cls: 'rowspan-grid',//******必须配置此样式  
                
                view: new Ext.whaty.ux.grid.RowColSpanView(viewConfig)
            });
        });  
    </script>  
</head>  
<body>
	<div id="TestGridDiv" style="height: 500px;"></div>
</body>  
</html>

