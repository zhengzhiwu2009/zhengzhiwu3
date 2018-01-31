/*!
 * Ext JS Library 3.0.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
 
var secondGridStore;
Ext.onReady(function(){

	var store = new Ext.data.JsonStore({
		root	:	'topics',
		totalProperty: 'totalCount',
		url		:	basePath + 'entity/basic/signUpOnline_getStudentListJson.action',
		fields: ['id','regNO', 'name', 'idCard','enterprise','isValid']
	});
	
	var myPageSize = 10; 

	function renderTopic(value, p, record){
        return String.format(
                '<b><a href="http://extjs.com/forum/showthread.php?t={2}" target="_blank">{0}</a></b><a href="http://extjs.com/forum/forumdisplay.php?f={3}" target="_blank">{1} Forum</a>',
                value, record.data.forumtitle, record.id, record.data.forumid);
    }
    
    function renderLast(value, p, r){
        return String.format('{0}<br/>by {1}', value.dateFormat('M j, Y, g:i a'), r.data['lastposter']);
    }
    	
	var grid_stu_source = new Ext.grid.GridPanel({
		region	:	'center',
		ddGroup : 'stuFecondGridDDGroup',
		store : store,
		title:'学员列表',
		columns : [
			{id:'id',header:'id',width:0,sortable:true,dataIndex:'id',hidden:true},
			{header:'系统编号',width:100,sortable:true,dataIndex:'regNO'},
			{header:'姓名',width:100,sortable:true,dataIndex:'name'},
			{header:'身份证号',width:200,sortable:true,dataIndex:'idCard'},
			{header:'所在机构',width:200,sortable:true,dataIndex:'enterprise'},
			{header:'是否有效',width:0,sortable:true,dataIndex:'isValid',hidden:true}
		],
		enableDragDrop : true,
		height	:	360,
		width	:	200,
		viewConfig: {
			forceFit:true,
			enableRowBody:true,
			showPreview:true,
			getRowClass : function(record, rowIndex, p, store){
						
            }			
		},
				
		bbar:new Ext.PagingToolbar({
			id:'bbar_stu_source',
            pageSize: 10,
            store: store,
            displayInfo: true,
            displayMsg: '显示 {0} - {1} of {2}',
            emptyMsg: "没有符合条件的数据",
            items:[
                '-', {
                pressed: false,
                enableToggle:false,
                cls: 'x-btn-text-icon details',
				toggleHandler: function(btn, pressed){
                    var view = grid_stu_source.getView();
                    view.showPreview = pressed;
                    view.refresh();
                }                
            }]
		})
	});

    secondGridStore = new Ext.data.JsonStore({
			root	: 'topics',
			fields  : ['id', 'regNO', 'name', 'idCard','enterprise','isValid']
    });
    	
	var grid_stu_target	= new Ext.grid.GridPanel({
			ddGroup : 'stuFirstGridDDGroup',
			title:'缴费学员列表',
			store : secondGridStore,
			columns : [
				{id:'id',header:'id',width:0,sortable:true,dataIndex:'id',hidden:true},
				{header:'系统编号',width:100,sortable:true,dataIndex:'regNO'},
				{header:'姓名',width:100,sortable:true,dataIndex:'name'},
				{header:'身份证号',width:150,sortable:true,dataIndex:'idCard'},
				{header:'所在机构',width:150,sortable:true,dataIndex:'enterprise'},
				{header:'是否有效',width:0,sortable:true,dataIndex:'isValid',hidden:true}
			],
			enableDragDrop : true,
			height	:	330,
			renderTo : 'stuPanel_2',

			viewConfig: {
				forceFit:true,
				enableRowBody:true,
				showPreview:true,
				getRowClass : function(record, rowIndex, p, store){
							
	            }			
			}
	});
	
	var search_stuCode = new Ext.form.TextField({id:'stuCode',width:100,height:20});
	var search_stuName = new Ext.form.TextField({id:'stuName',width:100,height:20});
	var search_button = new Ext.Button({text:'搜索',handler:function(){
		grid_stu_source.getStore().reload({params:{regNo:search_stuCode.getValue(),stuName:search_stuName.getValue(),limit:myPageSize}});
	}});
	var search_label_code = new Ext.form.Label({text:'系统编号:',height:20,width:60});
	var search_label_name = new Ext.form.Label({text:'姓名:',height:20,width:35});
	var search_label_blank_1 = new Ext.form.Label({text:'',height:20,width:10});
	var search_label_blank_2 = new Ext.form.Label({text:'',height:20,width:20});
	
	var searchForm_1 = new Ext.form.FormPanel({
		region	:	'north',
		height	:	30,
		border	:	false,
		layout	:	'column',
		items	:	[search_label_code,search_stuCode,search_label_blank_1,search_label_name,search_stuName,search_label_blank_2,search_button]
	});
	
	var searchPanel_1 =  new Ext.Panel({
		layout	:  'border',
		renderTo : 'stuPanel_1',
		height	:	360,
		border	:	false,
		items	:  [searchForm_1,grid_stu_source]
	});
	
	var fields = [
	   {name: 'id', mapping : 'id'},
	   {name: 'regNO', mapping : 'regNO'},
	   {name: 'name', mapping : 'name'},
	   {name: 'idCard', mapping : 'idCard'},
	   {name: 'enterprise', mapping : 'enterprise'},
	   {name: 'isValid', mapping : 'isValid'}
	];
	
	// used to add records to the destination stores
	var blankRecord =  Ext.data.Record.create(fields);
	
	var firstGridDropTargetEl =  grid_stu_source.getView().el.dom.childNodes[0].childNodes[1];
	
	var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
		ddGroup    : 'stuFirstGridDDGroup',
		copy       : true,
		notifyDrop : function(ddSource, e, data){

			// Generic function to add records.
			function addRow(record, index, allItems) {

				// Search for duplicates
				var foundItem = store.findExact('id', record.data.id);
				// if not found
				if (foundItem  == -1) {
					store.add(record);

					// Call a sort dynamically
					store.sort('regNO', 'ASC');

					//Remove Record from the source
					ddSource.grid.store.remove(record);
				}
			}

			// Loop through the selections
			Ext.each(ddSource.dragData.selections ,addRow);
			return(true);
		}
	});


	// This will make sure we only drop to the view container
	var secondGridDropTargetEl = grid_stu_target.getView().el.dom.childNodes[0].childNodes[1];

	var destGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
		ddGroup    : 'stuFecondGridDDGroup',
		copy       : false,
		notifyDrop : function(ddSource, e, data){

			// Generic function to add records.
			function addRow(record, index, allItems) {

				// Search for duplicates
				var foundItem = secondGridStore.findExact('id', record.data.id);
				// if not found
				if (foundItem  == -1) {
					secondGridStore.add(record);
					// Call a sort dynamically
					secondGridStore.sort('regNO', 'ASC');

					//Remove Record from the source
					ddSource.grid.store.remove(record);
				}
			}
			// Loop through the selections
			Ext.each(ddSource.dragData.selections ,addRow);
			return(true);
		}
	});	

	store.on('beforeload', function() {    
	  		this.baseParams = {
		        regNo:search_stuCode.getValue(),
		        stuName:search_stuName.getValue()
	  		};
	 });
 	
	store.load({
	    params: {
	        start: 0,          
	        limit: myPageSize
	    }
	});
	
	
});

function getStudentIds(){
	var studentIds = '';
	secondGridStore.each(function(record){
		studentIds += record.get('id')+',';
	});
	return studentIds;
}

