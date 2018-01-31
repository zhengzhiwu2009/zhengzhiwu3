
//重写Ext.Window的render方法
Ext.override(Ext.Window, {
	render : function(ct, position){ 
		Ext.Window.superclass.render.call(this, ct, position);
		ExtUtil.filterPriority(window._OPT_IDS);//权限过滤
	}
});

////重写FormPanel的render方法
//Ext.override(Ext.FormPanel, {
//	render : function(ct, position){ 
//		Ext.FormPanel.superclass.render.call(this, ct, position);
//		ExtUtil.filterPriority(window._OPT_IDS);//权限过滤
//	}
//});

