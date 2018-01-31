Ext.define('Ext.ux.mm.PageResizer', {
	extend: 'Ext.form.ComboBox',
	width: 50,
	queryMode: 'local',
	store: Ext.create('Ext.data.Store', {
		fields: ['id'],
		data : [{"id":"10"},{"id":"20"},{"id":"50"},{"id":"100"}]
	}),
	displayField: 'id',
	valueField: 'id',
	autoSelect: true,
	forceSelection: false,
	listeners: {
		'change': function(combo, newValue, oldValue) {
			newValue = parseInt(newValue, 10);
			if (!newValue) {return;}
			var store = this.up('#bbar').store;
			store.pageSize = parseInt(newValue, 10);
			var lastPage = parseInt(store.totalCount / store.pageSize);
			if ( store.totalCount % store.pageSize != 0 ) {
				lastPage += 1;
			}
			var page = store.currentPage <= lastPage ? store.currentPage : lastPage;
			if ( page <= 0 ) {
				page = 1;
			}
			store.loadPage(page);
		}
	}
});