<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/common/ext.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/tj.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>


<script type="text/javascript" src="ExQueryPageGrid.js"></script>

<script type="text/javascript">  
        Ext.onReady(function() {

            var grid = new Ext.whaty.ux.grid.ExQueryPageGrid({
            	title:'统计信息',
            	border:false,
                ck:false,  
            	proxyUrl:'/entity/statistics/prBzzStatistics_registStat.action',
				
                region:'center',
                cmField: [  
                  	{header: '&nbsp;',mcol:1,mtext:"年度",dataIndex: 'year'},  

                    {header: '新增',mcol:3,mtext:"注册单位数",sortable:true, width: 100,dataIndex: 'dept_create'},  
                    {header: '累计',dataIndex: 'dept_sum'},  
                    {header: '累计同比', dataIndex: 'dept_persent'},  
                    
                    {header: '新增',mcol:3,mtext:" 注册人数",dataIndex:'user_create'},  
                    {header: '累计', dataIndex: 'user_sum'},
                    {header: '累计同比', dataIndex: 'user_persent'}
                ],  

                stripeRows: true,  
     			//autoExpandColumn: 'pass_name',  
                //stateful: true,  
                //stateId: 'grid',  
                renderTo:'TestGridDiv',
               
                cls: 'rowspan-grid',//******必须配置此样式  
                
                view: new Ext.whaty.ux.grid.RowColSpanView(viewConfig)
            });
        });  
    </script>  
</head>  
<body>
	<div id="TestGridDiv" style="margin-left: 10px;margin-top: 10px;"></div>
</body>  
</html>

