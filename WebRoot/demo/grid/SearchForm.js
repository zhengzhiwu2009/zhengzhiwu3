
Ext.namespace('Ext.peixun.grid.*');
Ext.peixun.grid.SearchForm = Ext.extend(Ext.whaty.ux.form.BaseSearchForm,{
	labelAlign		:'right',
	initComponent 	: function(){
	
		this.nameTF = new Ext.form.TextField({
			fieldLabel:'姓名 ',
			name:'searchDemoVO.name',
			anchor:'100%'
		});
		
		this.pwdTF = new Ext.form.TextField({
			fieldLabel:'密码',
			name:'searchDemoVO.password',
			anchor:'100%'
		});
		
		this.birthdayTF = new Ext.form.DateField({
			fieldLabel:'出生日期',
			name:'searchDemoVO.birthday',
			anchor:'100%'
		});
		
		var subDmoJsonData = {root:[{text:'语文',value:'1'},
		                            {text:'物理',value:'2'},
		                            {text:'化学',value:'3'}]};
		this.courseCb = new Ext.whaty.ux.form.WhatyComboBox({
			fieldLabel:'学科',
			jsonData:subDmoJsonData,
			anchor:'100%',
//			name:'searchDemoVO.subDemoVO.name',
//			hiddenName:'searchDemoVO.subDemoVO.id',
			hiddenName:'searchBean.subDemo.id',
			listeners:{
				scope:this,
				select:this.handleSearch
			}
		});
		
		this.items=[
			        {
						layout:'column',
						items:[
						       {width:240,layout:'form',items:this.nameTF},
						       {width:240,layout:'form',items:this.pwdTF},
						       {width:240,layout:'form',items:this.birthdayTF},
						       {width:240,layout:'form',items:this.courseCb}
						       ]
					}
				];
		
		Ext.peixun.grid.SearchForm.superclass.initComponent.call(this);
	},
	
	handleSearch : function(cb){
		var values = this.getSearchFormValue();
		this.fireEvent('search',values);
	}
	
});


