<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/common/ext.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../css/tj.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>


<script type="text/javascript">  
        Ext.onReady(function() {

        	var jsonData = {root:[{text:'2010',value:'2010'},
    		                            {text:'2011',value:'2011'},
    		                            {text:'2012',value:'2012'}]};
        	
        	var monthCB = new Ext.whaty.ux.form.WhatyComboBox({
    			fieldLabel:'请选择年度',
    			name:'name',
    			allowEmpty:false,
    			allowBlank:false,
    			jsonData:jsonData,
    			value:'2012',
    			anchor:'100%'
    		}); 

        	var deptNameTF = new Ext.form.TextField({
    			fieldLabel:'机构名称',
    			name:'dept_name',
    			anchor:'100%'
    		}); 
    		
    		var form = new Ext.form.FormPanel({
    			renderTo:'MonthSearchForm', 
    			frame:true,
    			labelAlign:'right',
    			items:[{
					layout:'column',
					items:[
					       {width:300,layout:'form',items:monthCB},
					       {width:300,layout:'form',items:deptNameTF}
					       ]
				}]
    		});
			
            var grid = new Ext.whaty.ux.grid.ExQueryPageGrid({
                allowPage:true,
            	proxyUrl:'jijin_jsonData.json',
                cmField: [  
                  	{header: '序号',mcol:1,mtext:'&nbsp;',mwidth:50,width: 50,dataIndex: 'year'},  
                    {header: '机构名称',mcol:1,mtext:'&nbsp;',mwidth:150,width: 150,dataIndex: 'dept_name'},  
                    
                    {header: '新增',mtext:"注册单位", mcol:2, mwidth:200, width: 100,dataIndex: 'dept_grown'},  
                    {header: '累计', width: 100, dataIndex: 'dept_sum'},  
                    
                    {header: '新增',mcol:2,mtext:"注册人数",mwidth:200, width:100,dataIndex:'user_grown'},  
                    {header: '累计', width: 100,dataIndex: 'user_sum'}
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
		<h2>2012年远程培训注册情况表（按基金公司）</h2>
	</div>
	<div id="MonthStatisticsGrid"></div>
</body>  
</html>

