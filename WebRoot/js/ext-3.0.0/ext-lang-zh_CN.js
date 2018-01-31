/*
 * Simplified Chinese translation
 * By DavidHu
 * 09 April 2007
 */

Ext.UpdateManager.defaults.indicatorText = '<div class="loading-indicator">加载中...</div>';

if(Ext.View){
   Ext.View.prototype.emptyText = "";
}

if(Ext.grid.Grid){
   Ext.grid.Grid.prototype.ddText = "{0} 选择行";
}

if(Ext.TabPanelItem){
   Ext.TabPanelItem.prototype.closeText = "关闭";
}

if(Ext.form.Field){
   Ext.form.Field.prototype.invalidText = "输入值非法";
}

Date.monthNames = [
   "一月",
   "二月",
   "三月",
   "四月",
   "五月",
   "六月",
   "七月",
   "八月",
   "九月",
   "十月",
   "十一月",
   "十二月"
];

Date.dayNames = [
   "日",
   "一",
   "二",
   "三",
   "四",
   "五",
   "六"
];

if(Ext.MessageBox){
   Ext.MessageBox.buttonText = {
      ok     : "确定",
      cancel : "取消",
      yes    : "是",
      no     : "否"
   };
}

if(Ext.util.Format){
   Ext.util.Format.date = function(v, format){
      if(!v) return "";
      if(!(v instanceof Date)) v = new Date(Date.parse(v));
      return v.dateFormat(format || "Y-m-d");
   };
}

if(Ext.DatePicker){
   Ext.apply(Ext.DatePicker.prototype, {
      todayText         : "今天",
      minText           : "日期在最小日期之前",
      maxText           : "日期在最大日期之后",
      disabledDaysText  : "",
      disabledDatesText : "",
      monthNames        : Date.monthNames,
      dayNames          : Date.dayNames,
      nextText          : '下月 (Control+Right)',
      prevText          : '上月 (Control+Left)',
      monthYearText     : '选择一个月 (Control+Up/Down 来改变年)',
      todayTip          : "{0} (空格键选择)",
      format            : "Y-m-d",
      okText            : "确定",
      cancelText        : "取消"
   });
}

if(Ext.PagingToolbar){
   Ext.apply(Ext.PagingToolbar.prototype, {
      beforePageText : "第",
      afterPageText  : "页共 {0} 页",
      firstText      : "第一页",
      prevText       : "前一页",
      nextText       : "下一页",
      lastText       : "最后页",
      refreshText    : "刷新",
      displayMsg     : "显示 {0} - {1}共 {2} 条",
      emptyMsg       : '没有数据需要显示'
   });
}

if(Ext.form.TextField){
   Ext.apply(Ext.form.TextField.prototype, {
      minLengthText : "该输入项的最小长度是 {0}",
      maxLengthText : "该输入项的最大长度是 {0}",
      blankText     : "该输入项为必填",
      regexText     : "",
      emptyText     : null
   });
}

if(Ext.form.NumberField){
   Ext.apply(Ext.form.NumberField.prototype, {
      minText : "该输入项的最小值是 {0}",
      maxText : "该输入项的最大值是 {0}",
      nanText : "{0} 不是有效值"
   });
}

if(Ext.form.DateField){
   Ext.apply(Ext.form.DateField.prototype, {
      disabledDaysText  : "禁用",
      disabledDatesText : "禁用",
      minText           : "该输入项的日期必须在 {0} 之后",
      maxText           : "该输入项的日期必须在 {0} 之前",
      invalidText       : "{0} 是无效的日期 - 必须符合格式: {1}",
      format            : "Y-m-d"
   });
}

if(Ext.form.ComboBox){
   Ext.apply(Ext.form.ComboBox.prototype, {
      loadingText       : "加载...",
      valueNotFoundText : undefined
   });
}

if(Ext.form.VTypes){
   Ext.apply(Ext.form.VTypes, {
      emailText    : '该输入项必须是邮件地址，格式如： "user@domain.com"',
      urlText      : '该输入项必须是URL地址，格式如： "http:/'+'/www.domain.com"',
      alphaText    : '该输入项只能包含字符和_',
      alphanumText : '该输入项只能包含字符，数字和_'
   });
}

if(Ext.grid.GridView){
   Ext.apply(Ext.grid.GridView.prototype, {
      sortAscText      : "正序",
      sortDescText     : "逆序",
      lockText         : "锁列",
      unlockText       : "解锁列",
      columnsText      : "列"
   });
}

if(Ext.grid.GroupingView){
   Ext.apply(Ext.grid.GroupingView.prototype,{
      groupByText      : "根据该列分组",
      showGroupsText   : "分组显示"
   });
}

if(Ext.grid.PropertyColumnModel){
   Ext.apply(Ext.grid.PropertyColumnModel.prototype, {
      nameText   : "名称",
      valueText  : "值",
      dateFormat : "Y-m-d"
   });
}

if(Ext.layout.BorderLayout.SplitRegion){
   Ext.apply(Ext.layout.BorderLayout.SplitRegion.prototype, {
      splitTip            : "拖动来改变尺寸.",
      collapsibleSplitTip : "拖动来改变尺寸.双击隐藏"
   });
}

// whaty frame work js
if(Ext.whaty.framework.ContainerTabPanel){
   Ext.apply(Ext.whaty.framework.ContainerTabPanel.prototype.localmsg, {
	   maskInfoText			: "数据加载中...",
	   menuCloseText        : "关闭",
	   menuCloseOtherText 	: "关闭其他",
	   menuCloseAllText 	: "全部关闭",			
	   menuRefreshText		: "刷新"				
   });
}

if(Ext.whaty.framework.FrameWorkViewport){
   Ext.apply(Ext.whaty.framework.FrameWorkViewport.prototype.localmsg, {
	   navigationMenuText	:"导航菜单"		
   });
}

if(Ext.whaty.framework.TreeMenuPanel){
   Ext.apply(Ext.whaty.framework.TreeMenuPanel.prototype.localmsg, {
	   expandTooltipText	:"展开全部",	
	   collapseTooltipText	:"收缩全部",
	   searchBtnText		:"搜索"
   });
}

if(Ext.whaty.ux.form.BaseSearchForm){
   Ext.apply(Ext.whaty.ux.form.BaseSearchForm.prototype.localmsg, {
	   searchText 			: "搜索",
	   searchResetText 		: "重置"
   });
}

if(Ext.whaty.ux.form.BaseForm){
   Ext.apply(Ext.whaty.ux.form.BaseForm.prototype.localmsg, {
	   saveText 			: "保存",							
	   resetText			: "重置",							
	   cancelText			: "取消",							
	   msgTitle				: "消息",		
	   maskInfoText			: "正在保存...",
	   saveSuccessText		: "保存成功",					
	   saveFailureText		: "保存失败"					
   });
}

if(Ext.whaty.ux.form.LogicBaseForm){
   Ext.apply(Ext.whaty.ux.form.LogicBaseForm.prototype.localmsg, {
	   blankText 			: " 不能为空 ",
	   invalidText 			: " 输入值不合法 ",
	   saveText 			: "保存",							
	   resetText			: "重置",							
	   cancelText			: "取消",							
	   msgTitle				: "消息",						
	   saveSuccessText		: "保存成功",					
	   saveFailureText		: "保存失败"	
   });
}

if(Ext.whaty.ux.form.TextField){
   Ext.apply(Ext.whaty.ux.form.TextField.prototype, {
	   maxLengthText 		: "最大长度为20 "
   });
}

if(Ext.whaty.ux.form.WhatyComboBox){
   Ext.apply(Ext.whaty.ux.form.WhatyComboBox.prototype, {
	   emptyText 			: "请选择"
   });
}

if(Ext.whaty.ux.grid.GridImportWindow){
   Ext.apply(Ext.whaty.ux.grid.GridImportWindow.prototype.localmsg, {
	    titleText 			: "附件上载",
		fileLabelText		: "文件",
		uploadBtnText		: "上传",
		templateDownloadText: "模板下载",
		btnCancelText		: "取消",
		msgTitle			: "消息",
		importExecelFailure	: "无法为该表导入数据",
		filePathBlank		: "文件路径不能为空",
		uploadFailure		: "上载失败",
		updateDataEmpty		: "需要更新数据为空，请检查Excel",
		correctFileType		: "请选择正确的文件格式上传"
   });
}

if(Ext.whaty.ux.grid.GridWindowForm){
   Ext.apply(Ext.whaty.ux.grid.GridWindowForm.prototype.localmsg, {
	   saveText 				: "保存",
	   resetText				: "重置",
	   cancelText				: "取消",
	   loadRecordFailureText	: "获取记录失败",
	   msgTitle					: "消息",
	   maskInfoText				: "正在保存...",
	   saveSuccessText			: "保存成功",
	   saveFailureText			: "保存失败"
   });
}

if(Ext.whaty.ux.grid.QueryPageGrid){
   Ext.apply(Ext.whaty.ux.grid.QueryPageGrid.prototype.baselocalmsg, {
	   msgTitle					: "消息",
	   dataLoadFailure			: "数据加载失败",
	   noDataMsg				: "没有数据需要显示"
   });
}

if(Ext.whaty.ux.grid.LogicQueryPageGrid){
   Ext.apply(Ext.whaty.ux.grid.LogicQueryPageGrid.prototype.localmsg, {
	   addBtnText				: "添加",
	   updateBtnText			: "修改",
	   deleteBtnText			: "删除",
	   searchBtnText			: "搜索",
	   exportColsTipText		: "导出所有列",
	   currentPageText			: "Excel当前页",							
	   allRecordText			: "Excel全部",
	   excelEmptyText			: "请选择报表形式",
	   excelUploadText			: "上载",
	   msgTitle					: "消息",
	   editRecordMsgText		: "请选择需要修改的数据",
	   sureDeleteInfo			: "确定删除？",
	   deleteSuccessText		: "删除成功",
	   deleteFailureText		: "删除失败",
	   deleteRecordMsgText		: "请选择需要删除的数据",
	   sureExportCurrentText	: "确定导出当前页数据报表？(xls)",
	   sureExportAllText		: "确定导出所有数据报表？(xls)",
	   exportFailureText		: "下载失败（文件不存在或者服务器超时，请稍候再试）"
   });
}

if(Ext.whaty.ux.tree.WhatyTreePanel){
   Ext.apply(Ext.whaty.ux.tree.WhatyTreePanel.prototype.localmsg, {
	   msgTitle					: "消息",
	   setStaticDataText		: "请设置staticData",
	   setProxyUrlText			: "请设置proxyUrl",
	   rootText					: "根节点"
   });
}

if(Ext.whaty.ux.window.BaseWindow){
   Ext.apply(Ext.whaty.ux.window.BaseWindow.prototype.localmsg, {
	   saveText					: "保存",
	   resetText				: "重置",
	   cancelText				: "取消"
   });
}

if(Ext.whaty.framework.ThemeHelper){
   Ext.apply(Ext.whaty.framework.ThemeHelper.prototype.localmsg, {
	   themeTitle			: "主题",
	   themeSaveText		: "保存",
	   themeCancelText		: "取消",
	   themeConfirmTitle	: "消息",
	   themeConfirmText		: "主题没有保存，确定退出？",
	   themeDefaultText 	: "默认",
	   themeOliveText 	 	: "绿色",
	   themePurpleText 		: "紫色",
	   themeChocolateText 	: "巧克力",
	   themeGrayText 		: "暗灰色"
   });
}

Ext.apply(
  Ext.ux.UploadDialog.Dialog.prototype.i18n,{
	    title: '文件上传.',
	    id_col_title: 'ID',
	    id_col_width: 150,
	    state_col_title: '状态',
	    state_col_width: 70,
	    filename_col_title: '文件名',
	    filename_col_width: 230,
	    note_col_title: '备注',
	    note_col_width: 150,
	    add_btn_text: '增加',
	    add_btn_tip: '增加一个文件到上传列表.',
	    remove_btn_text: '删除',
	    remove_btn_tip: '从上传列表中删除文件.',
	    reset_btn_text: '重置',
	    reset_btn_tip: '重置队列.',
	    upload_btn_start_text: '上传',
	    upload_btn_stop_text: '停止',
	    upload_btn_start_tip: '上传列表中的文件.',
	    upload_btn_stop_tip: '从列表中删除文件.',
	    close_btn_text: '关闭',
	    close_btn_tip: '关闭上传窗口.',
	    progress_waiting_text: '上传...',
	    progress_uploading_text: '上传: {0} 的 {1} 完成.',
	    error_msgbox_title: '错误',
	    permitted_extensions_join_str: ',',
	    err_file_type_not_permitted: '选择的文件类型禁止上传.<br/>允许类型: {1}',
	    note_queued_to_upload: '上传队列.',
	    note_processing: '上传中...',
	    note_upload_failed: '服务器不可用或内部服务器出错.',
	    note_upload_success: '已上传.',//成功
	    note_upload_error: '上传错误.',
	    note_aborted: '由用户终止.',
	    note_canceled: '取消.',
	    msgTitle: '消息',
	    msg_delete_success: '删除成功',
	    msg_delete_failed: '删除失败',
	    msg_download_failed: '下载失败'
  }
);

/**
 * services - system
 */
if(Ext.whaty.services.system.AllocateRoleWindow){
	Ext.apply(Ext.whaty.services.system.AllocateRoleWindow.prototype.localmsg, {
		titleText			: "用户角色分配",
		deleteText			: "删除",
		allocateText		: "分配",
		roleAllocateText	: "角色",
		allocatedRoleText	: "已分配角色",
		searchTipText		: "角色名称搜索",
		messageText         : "消息",
		AddSuccessText      : "添加成功",
		AddFailureText      : "添加失败",
		DelSuccessText      : "删除成功",
		DelFailureText      : "删除失败",
		DeleteMsgText		: "确定删除？"
	});
}


if(Ext.whaty.services.system.AllocateRoleGrid){
	Ext.apply(Ext.whaty.services.system.AllocateRoleGrid.prototype.localmsg, {
		roleIdText			: "角色ID",
		roleNameText		: "角色名称"
	});
}


