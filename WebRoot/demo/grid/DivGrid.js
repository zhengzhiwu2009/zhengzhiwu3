
/**
 * @author bc_qi 
 */

Ext.namespace('Ext.peixun.grid.*');
Ext.peixun.grid.DivGrid = Ext.extend(Ext.whaty.ux.grid.ExQueryPageGrid,{
	proxyUrl:'divgrid-data.json',
	height:400,
	initComponent : function(){
		this.cmField = [
			   	         {header:'用户名',dataIndex:'username',width:200},
			   	         {header:'密码',dataIndex:'password',width:200},
			   	         {header:'住址',dataIndex:'address',width:200}
		   		     ];
		Ext.peixun.grid.DivGrid.superclass.initComponent.call(this);
	}
});
