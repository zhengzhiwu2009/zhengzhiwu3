/**
 * A Provider implementation which saves and retrieves state via database.
 *
 * Example:
 *
 *     var cp = Ext.create('Ext.ux.mm.HttpStateProvider', {
 *         path: "/cgi-bin/",
 *         url: "../bin/MyAction?mode=save_cookie"
 *     });
 *
 *     Ext.state.Manager.setProvider(cp);
 *
 * @constructor
 * Creates a new HttpStateProvider.
 * @param {Object} config (optional) Config object.
 * @return {Object}
 */
Ext.define('Ext.ux.mm.HttpStateProvider', {
    extend: 'Ext.state.Provider',
    constructor : function(config){
        var me = this;
        me.path = window.location.pathname;
        me.url = '.';
        me.callParent(arguments);
        me.state = me.readValues();
    },

    // private
    set : function(name, value){
        var me = this;

       
        me.setValue(name, value);
        me.callParent(arguments);
    },

    // private
    readValues : function(){
    	var state = {};
    	try {
    		var dom_node = Ext.get('cookies');
            var cookies = dom_node ? Ext.decode( dom_node.getValue() ) : {};
         	for (var name in cookies) {
         		state[name] = this.decodeValue( cookies[name] );
            }
    	} catch( e ) {
    		//console.error(e.message);
    	}
        return state;
    },

    // private
    setValue : function(name, value){
    	var me = this;
    
    	Ext.Ajax.request({
    	    url: me.url,
    	    params: {path: me.path, name: name, value: this.encodeValue(value)},
    	    success: function(response, opts) {
    	       
    	    },
    	    failure: function(response, opts) {

    	    }
    	});
    }
    
});

