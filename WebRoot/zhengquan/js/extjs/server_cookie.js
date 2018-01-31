Ext.onReady(function(){
	Ext.state.Manager.setProvider(Ext.create('Ext.ux.mm.HttpStateProvider', {
		url: window.location.href+'?mode=saveCookie'
	}));
});
