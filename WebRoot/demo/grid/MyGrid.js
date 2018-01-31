
/**
 * @author bc_qi 
 */

Ext.namespace('Ext.peixun.grid.*');
Ext.peixun.grid.MyGrid = Ext.extend(Ext.whaty.ux.grid.PageGridPanel,{
	title:'测试Grid',
	
	allowAdd:false,
	allowDelete:false,
	allowRead:false,
	ActionUrl:'/peixun/WhatyAction',
	//proxyUrl:CONTEXT_PATH + '/peixun/MyAction_doList.action',
	
	initComponent : function(){
		
		this.cmField = [
			   	         {header:'姓名',dataIndex:'name',sortable:true,width:200}
		   		     ];
		
		this.addButton = new Ext.Button({
			text:'添加',
			iconCls:'add',
			listeners:{
				scope:this,
				click:this.handleAddClick
			}
		});
		
		this.toolbar=[this.addButton];
		
		this.formWindow = new Ext.peixun.grid.MyFormWindow({
			listeners:{
				scope:this,
				afterSubmit:this.handleAfterSubmit//保存成功后触发的事件
			}
		});
		
		Ext.peixun.grid.MyGrid.superclass.initComponent.call(this);
		this.on('rowdblclick',this.handleRowdblclick,this);
	},
	
	//添加按钮
	handleAddClick :function(){
		this.formWindow.show();
	},
	
	//保存成功后，刷新
	handleAfterSubmit : function(){
		this.reload();
	},
	
	//双击查看详细信息
	handleRowdblclick : function(grid){
		var id = grid.getSelectedRecordValue('id');
		var response = ExtUtil.doAjax({
			params  : {id:id},
        	url 	: CONTEXT_PATH + '/peixun/MyAction_getById.action'
		});
		if(response.logicSuccess){
			this.formWindow.show();
			this.formWindow.setWindowFormValue(response.DemoVO);
			this.formWindow.setMyFormValue();
		}
	},
	
	
	//显示tip信息
	showTip : function(data, metadata, record, rowIndex, columnIndex, store){
		//moviesColumn 如果为null，可以通过record获取title值
        var title=record.get('password');
        metadata.attr = 'ext:qtitle="'+title+'" ext:qtip="' + data + '"';  
        //metadata.attr = 'ext:qtip="' + data + '"';
        //return "<div ext:qtip='<img src=\""+value+"\" width=100 hight=100/>'>"+value+"</div>";   
        return data;
	}
	
});


