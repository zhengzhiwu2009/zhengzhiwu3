/*!
 * Ext JS Library 3.0.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
var courseSecondGridStore;
Ext.onReady(function(){

	var store = new Ext.data.JsonStore({
		root	:	'topics',
		totalProperty: 'totalCount',
		url		:	basePath + 'entity/basic/signUpOnline_getCourseListJson.action?type=lee',
		fields: ['id', 'code','name', 'courseType','courseCategory','itemType','isValid','canJoinBatch']
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
    	
	var grid_cou_source = new Ext.grid.GridPanel({
		region	:	'center',
		ddGroup : 'secondGridDDGroup',
		store : store,
		title:'课程列表',
		columns : [
			{id:'id',header:'课程编号',width:0,sortable:true,dataIndex:'id',hidden:true},
			{header:'课程编号',width:100,sortable:true,dataIndex:'code'},
			{header:'课程名称',width:250,sortable:true,dataIndex:'name'},
			{header:'课程性质',width:100,sortable:true,dataIndex:'courseType'},
			{header:'按业务分类',width:100,sortable:true,dataIndex:'courseCategory'},
			{header:'按大纲分类',width:100,sortable:true,dataIndex:'itemType'},
			{header:'发布状态',width:0,sortable:true,dataIndex:'isValid',hidden:true},
			{header:'专项培训课程',width:0,sortable:true,dataIndex:'canJoinBatch',hidden:true}
		],
		enableDragDrop : true,
		height	:	350,
		
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
					
                    var view = grid_cou_source.getView();
                    view.showPreview = pressed;
                    view.refresh();
                    
                }                
            }]
		})
	});

    courseSecondGridStore = new Ext.data.JsonStore({
			root	: 'topics',
			fields  : ['id', 'code','name', 'courseType','courseCategory','itemType','isValid','canJoinBatch']
    });
    	
	var grid_stu_target	= new Ext.grid.GridPanel({
			ddGroup : 'firstGridDDGroup',
			title:'选课列表',
			store : courseSecondGridStore,
			columns : [
				{id:'id',header:'课程编号',width:0,sortable:true,dataIndex:'id',hidden:true},
				{header:'课程编号',width:100,sortable:true,dataIndex:'code'},
				{header:'课程名称',width:140,sortable:true,dataIndex:'name'},
				{header:'课程性质',width:80,sortable:true,dataIndex:'courseType'},
				{header:'按业务分类',width:110,sortable:true,dataIndex:'courseCategory'},
				{header:'按大纲分类',width:110,sortable:true,dataIndex:'itemType'},
				{header:'发布状态',width:0,sortable:true,dataIndex:'isValid',hidden:true},
				{header:'专项培训课程',width:0,sortable:true,dataIndex:'canJoinBatch',hidden:true}
			],
			enableDragDrop : true,
			height	:	320,
			renderTo : 'stuPanel_4',
			
			viewConfig: {
				forceFit:true,
				enableRowBody:true,
				showPreview:true,
				getRowClass : function(record, rowIndex, p, store){
							
	            }			
			}
			
	});
	
	store.load({
	    params: {
	        start: 0,          
	        limit: myPageSize
	    }
	});

	var search_couCode = new Ext.form.TextField({id:'couCode',width:100,height:20});
	var search_couName = new Ext.form.TextField({id:'couName',width:100,height:20});
	var search_couButton = new Ext.Button({text:'搜索',handler:function(){
		grid_cou_source.getStore().reload({params:{courseCode:search_couCode.getValue(),courseName:search_couName.getValue(),limit:myPageSize}});
	}});
	var search_label_couCode = new Ext.form.Label({text:'课程编号:',height:20,width:60});
	var search_label_couName = new Ext.form.Label({text:'课程名称:',height:20,width:60});
	var search_label_blank_3 = new Ext.form.Label({text:'',height:20,width:10});
	var search_label_blank_4 = new Ext.form.Label({text:'',height:20,width:20});
	
	var searchForm_2 = new Ext.form.FormPanel({
		region	:	'north',
		height	:	30,
		border	:	false,
		layout	:	'column',
		items	:	[search_label_couCode,search_couCode,search_label_blank_3,search_label_couName,search_couName,search_label_blank_4,search_couButton]
	});
	
	var searchPanel_2 =  new Ext.Panel({
		layout	:  'border',
		renderTo : 'stuPanel_3',
		height	:	350,
		border	:	false,
		items	:  [searchForm_2,grid_cou_source]
	});
	
	var fields = [
	   {name: 'id', mapping : 'id'},
	   {name: 'code', mapping : 'code'},
	   {name: 'name', mapping : 'name'},
	   {name: 'courseType', mapping : 'courseType'},
	   {name: 'courseCategory', mapping : 'courseCategory'},
	   {name: 'itemType', mapping : 'itemType'},
	   {name: 'isValid', mapping : 'isValid'},
	   {name: 'canJoinBatch', mapping : 'canJoinBatch'}
	];
	
	// used to add records to the destination stores
	var blankRecord =  Ext.data.Record.create(fields);
	
	var firstGridDropTargetEl =  grid_cou_source.getView().el.dom.childNodes[0].childNodes[1];
	
	var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
		ddGroup    : 'firstGridDDGroup',
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
					store.sort('id', 'ASC');

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
	var secondGridDropTargetEl = grid_stu_target.getView().el.dom.childNodes[0].childNodes[1]

	var destGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
		ddGroup    : 'secondGridDDGroup',
		copy       : false,
		notifyDrop : function(ddSource, e, data){

			// Generic function to add records.
			function addRow(record, index, allItems) {

				// Search for duplicates
				var foundItem = courseSecondGridStore.findExact('id', record.data.id);
				// if not found
				if (foundItem  == -1) {
					courseSecondGridStore.add(record);
					// Call a sort dynamically
					courseSecondGridStore.sort('id', 'ASC');

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
					courseCode:search_couCode.getValue(),
					courseName:search_couName.getValue()
	  		};
	 });
	
});

function getCourseIds(){
	var courseIds = '';
	courseSecondGridStore.each(function(record){
		courseIds += record.get('id')+',';
	});
	return courseIds;
}