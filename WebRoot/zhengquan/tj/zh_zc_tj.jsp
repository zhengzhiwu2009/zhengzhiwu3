<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/common/ext.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../css/tj.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>



<script type="text/javascript" src="ExQueryPageGrid.js"></script>

<script type="text/javascript">  
        Ext.onReady(function() {

        	var jsonStartData = {root:[{text:'2008',value:'2008'},
    		                            {text:'2011',value:'2011'},
    		                            {text:'2012',value:'2012'}]};
        	
        	var monthStartCB = new Ext.whaty.ux.form.WhatyComboBox({
    			fieldLabel:'请选择年度 从',
    			name:'name',
    			allowBlank:false,
    			jsonData:jsonStartData,
    			value:'2008',
    			anchor:'100%'
    		}); 

        	var jsonEndData = {root:[{text:'2010',value:'2010'},
		                            {text:'2011',value:'2011'},
		                            {text:'2012',value:'2012'}]};
  	
		  	var monthEndCB = new Ext.whaty.ux.form.WhatyComboBox({
					fieldLabel:'到',
					name:'name',
					allowBlank:false,
					jsonData:jsonEndData,
					value:'2012',
					anchor:'100%'
				}); 
				
    		var searchBtn = new Ext.Button({
					text:'&nbsp;&nbsp;	查	询	&nbsp;&nbsp;',
					listeners:{
						click:function(){
							grid.reload();
						}
					}
        		});
        		
    		var form = new Ext.form.FormPanel({
    			title:'注册统计概述',
    			renderTo:'MonthSearchForm', 
    			frame:true,
    			labelAlign:'right',
    			items:[{
					layout:'column',
					items:[
					       {width:300,layout:'form',items:monthStartCB},
					       {width:300,layout:'form',items:monthEndCB},
					        {layout:'form',items:searchBtn}
					       ]
					       
				}]
    		});
			var url = '/entity/statistics/prBzzStatistics_registStat.action';
			var value =  monthStartCB.value; 
			var value1 =  monthEndCB.value; 
			
            var grid = new Ext.whaty.ux.grid.ExQueryPageGrid({
            	extraParam:{startYear:value,endYear:value1},
            	proxyUrl:url,
                cmField: [  
                  	{header: '年度',mcol:1,mtext:'&nbsp;',mwidth:100,width: 100,dataIndex: 'year'},  
                    {header: '新增',mtext:"注册单位数", mcol:3, mwidth:300, width: 100,dataIndex: 'dept_grown'},
                    {header: '累计',mcol:1,mwidth:100,width: 100,dataIndex: 'dept_sum'},   
                    {header: '同比增长', width: 100, dataIndex: 'dept_num_growth'},  
                    {header: '新增',mcol:3,mtext:"注册人数",mwidth:300, width:100,dataIndex:'dept_grown'},  
                    {header: '累计', width: 100,dataIndex: 'user_sum'},
                    {header: '同比增长', width: 100,dataIndex: 'user_num_growth'}
                ],  

                stripeRows: true,  
                renderTo:'MonthStatisticsGrid',
                //height:500,
                cls: 'rowspan-grid'//******必须配置此样式  
                ,view: new Ext.whaty.ux.grid.RowColSpanView(viewConfig)
            });
        });  
    </script>  
</head>  
<body>
	<div id="MonthSearchForm"></div>
	<div style="height: 50px; text-align:center; line-height: 50px; color: blue">
		<h2>年度远程培训注册情况汇总表</h2>
	</div>
	<div id="MonthStatisticsGrid"></div>
	<%
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
	sdf.format(new java.util.Date());
	%>
	<span class="timestmap">制表时间：<%=sdf.format(new java.util.Date()) %> 00:00</span>
</body>  
</html>

