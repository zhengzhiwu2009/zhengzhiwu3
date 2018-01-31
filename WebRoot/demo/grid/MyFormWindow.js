

Ext.namespace('Ext.peixun.grid.MyFormWindow');
Ext.peixun.grid.MyFormWindow = Ext.extend(Ext.whaty.ux.window.FormWindow,{
	initComponent : function(){
	
		this.idH = new Ext.form.Hidden({
			name:'id'
		});
	
		this.nameTF = new Ext.form.TextField({
			fieldLabel:'姓名',
			name:'name',
			allowBlank:false,
			anchor:'80%'
		}); 
		
		this.passwordTF = new Ext.form.TextField({
			fieldLabel:'密码',
			name:'password',
			anchor:'80%'
		});
		
		var jsonData = {root:[{text:'男',value:'1'},{text:'女',value:'0'}]};
		this.sexCB = new Ext.whaty.ux.form.WhatyComboBox({
			fieldLabel:'性别',
			name:'sex',
			hiddenName:'sexValue',
			ck:true,
			jsonData:jsonData
		});
		
		var url = CONTEXT_PATH + '/peixun/MyAction_getComboboxData.action';
		this.clazzCB = new Ext.whaty.ux.form.WhatyComboBox({
			fieldLabel:'班级',
			proxyUrl:url,
			width:200,
			name:'subDemoVO.name',//对象中的属性
			hiddenName:'subDemoVO.id',
			//allowPage:true,//允许分页,
			ck:true
		});
		
		this.form = new Ext.whaty.ux.form.BaseForm({//这里要使用BaseForm
			region:'center',
			actionUrl : CONTEXT_PATH + '/peixun/MyAction_doSubmit.action',
			items:[this.idH,this.nameTF,this.passwordTF,this.sexCB,this.clazzCB]
		});
		
		this.items = [this.form];
		Ext.peixun.grid.MyFormWindow.superclass.initComponent.call(this);
	},
	
	setMyFormValue : function(){
		this.sexCB.setComboxValue('男,女','1,0')
	}
	
});

