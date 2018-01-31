package com.whaty.platform.entity.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.web.util.GridSearchHelper;




public class GridConfig {

	private MyBaseAction action;
	
	private String start; // 列表开始的位置

	private String limit; // 每页要显示的条目

	private String sort; // 排序列名

	private String dir; // 排序asc/desc

	private String title; //表格标题
	
	private String note; //页面提示信息(HTML格式)

	private String entityMemberName;

	private List<ColumnConfig> listColumnConfig;

	private String extColumnModel;

	private String extFields;
	
	private String extUpdateFields;

	private String extSearchVars;
	
	private String extSearchValues;

	private String extSearchItems;

	private String extAddVars;

	private String extAddItems;
	
	private String extMenus;
	
	private String extMenuFunctions;
	
	private String extMenuScripts;
	
	private List<MenuConfig> listMenuConfig;
	
	private String extRenderFunctions;
	
	private String extRenderColumns;
	
	private List<RenderConfig> listRenderConfig;

	private boolean isPrepared;
	
	private boolean canAdd;
	
	private boolean canBatchAdd;
	
	private boolean canDelete;
	
	private boolean canUpdate;
	
	private boolean canExcelUpdate = false;
	
	private String updateName;
	
	private boolean canSearch;
	
	private boolean preview;
	
	private String previewDataIndex;
	
	private String fieldsFilter;
	
	private String fieldsClean;
	
	private String copyPreSearch;
	
	private String extPreSearchItems;
		
	private boolean containFile;
	
	private boolean containFCKEditor;
	
	private String extHighlightKeywordFunctions;
	
	private String extValueRenderFunctions;
	
	private String excelFiledsFilter;

	private boolean showCheckBox = true;
	
	private String comboboxStores ;	//用于保存通过ajax加载的combobox数据
	
	private Map<String, String> defaultValueMap;
	
	/**
	 * public方法，代Action存储通用变量
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	// return a default value if not set
	public String getLimit() {
		if (limit == null || limit.equals(""))
			return "20";
		
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getStart() {
		if (limit == null || limit.equals(""))
			return "0";
		
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEntityMemberName() {
		return entityMemberName;
	}

	public void setEntityMemberName(String entityMemberName) {
		this.entityMemberName = entityMemberName;
	}
	
	public String getCopyPreSearch() {
		return copyPreSearch;
	}
	
	public boolean isContainFCKEditor() {
		return containFCKEditor;
	}

	public void setContainFCKEditor(boolean containFCKEditor) {
		this.containFCKEditor = containFCKEditor;
	}

	public boolean isContainFile() {
		return containFile;
	}

	public void setContainFile(boolean containFile) {
		this.containFile = containFile;
	}

	/**
	 * 此方法已废弃，使用setCapability(boolean, boolean, boolean, boolean)代替
	 * 设置grid的add、delte、update能力
	 * @param canAdd
	 * @param canDelete
	 * @param canUpdate
	 */
	public void setCapability(boolean canAdd, boolean canDelete, boolean canUpdate) {
		this.setCapability(canAdd, canDelete, canUpdate, true);
	}
	
	/**
	 * 设置grid的add、delte、update能力
	 * @param canAdd 是否有添加功能
	 * @param canDelete 是否有删除功能
	 * @param canUpdate 是否有修改功能（双击一条记录）
	 * @param canSearch 是否可以搜索（标题右边下拉按钮）
	 */
	public void setCapability(boolean canAdd, boolean canDelete, boolean canUpdate, boolean canSearch) {
		this.setCapability(canAdd,  canDelete,canUpdate, canSearch , false); 
	}

	/**
	 * 设置grid的add、delte、update能力
	 * @param canAdd 是否有添加功能
	 * @param canDelete 是否有删除功能
	 * @param canUpdate 是否有修改功能（双击一条记录）
	 * @param canSearch 是否可以搜索（标题右边下拉按钮）
	 *  @param canBatchAdd 是否可以excel导入
	 */
	public void setCapability(boolean canAdd, boolean canDelete, boolean canUpdate, boolean canSearch , boolean canBatchAdd) {
		this.canAdd = canAdd;
		this.canDelete = canDelete;
		this.canUpdate = canUpdate;
		this.canSearch = canSearch;
		this.canBatchAdd = canBatchAdd;
	}
	
	/**
	 * 设置grid的add、delte、update能力
	 * @param canAdd 是否有添加功能
	 * @param canDelete 是否有删除功能
	 * @param canUpdate 是否有修改功能（双击一条记录）
	 * @param canSearch 是否可以搜索（标题右边下拉按钮）
	 * @param canBatchAdd 是否可以excel导入
	 * @param comboboxType 下拉框类型
	 */
	public void setCapability(boolean canAdd, boolean canDelete, boolean canUpdate, boolean canSearch , String comboboxType ) {
		this.canAdd = canAdd;
		this.canDelete = canDelete;
		this.canUpdate = canUpdate;
		this.canSearch = canSearch;
		this.canBatchAdd = canBatchAdd;
	}
	
	public boolean isCanAdd() {
		return canAdd;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public boolean isCanUpdate() {
		return canUpdate;
	}
	
	public boolean isCanSearch() {
		return canSearch;
	}

	public String getPreviewDataIndex() {
		return previewDataIndex;
	}

	public boolean isPreview() {
		return preview;
	}
	
	/**
	 * pre search
	 */
	public String getExtPreSearchVars() {
		return getExtSearchVars()
			.replace("_s_", "search_")
			.replace("});", ",anchor: '90%'});");
	}
	
	public String getExtPreSearchItems() {
		return extPreSearchItems;
	}
	
	public String getPreFieldsFilter() {
		return getFieldsFilter().replace("_s_", "search_");
	}

	/**
	 * 构造方法，初始化list成员
	 */
	public GridConfig(MyBaseAction action) {
		this.action = action;
		listColumnConfig = new ArrayList<ColumnConfig>();
		listMenuConfig = new ArrayList<MenuConfig>();
		listRenderConfig = new ArrayList<RenderConfig>();
	}

	/**
	 * 给Grid添加一个column
	 * @param name 列的自然语言名称
	 * @param dataIndex Json字符串索引字段名称
	 * @param search 是否被搜索
	 * @param add 是否添加、修改时显示此字段
	 * @param list 是否显示到列表
	 * @param textFieldParameters 给TextField设置的其他ExtJS参数，以","结尾。例如，校验3位数字："regex:new RegExp(/^\\d{3}$/),"
	 */
	public ColumnConfig addColumn(String name, String dataIndex, boolean search, boolean add, boolean list, String textFieldParameters) {
		return this.addColumn(name, dataIndex, search, add, list, "TextField", false, 10000, textFieldParameters,null);
	}

	/**
	 * 给Grid添加一个column
	 * @param name 列的自然语言名称
	 * @param dataIndex Json字符串索引字段名称
	 * @param search 是否被搜索
	 * @param add 是否添加、修改时显示此字段
	 * @param list 是否显示到列表
	 * @param type 数据类型: TextField, Date, TextArea, TextEditor, BasicRadio, GenderRadio
	 * @param allowBlank 编辑时是否允许为空
	 * @param maxLength 最大长度
	 */
	public ColumnConfig addColumn(String name, String dataIndex, boolean search, boolean add, boolean list, String type, boolean allowBlank, int maxLength) {
		return this.addColumn(name, dataIndex, search, add, list, type, allowBlank, maxLength, null,null);
	}
	
	/**
	 * 给Grid添加一个column
	 * @param name 列的自然语言名称
	 * @param dataIndex Json字符串索引字段名称
	 * @param search 是否被搜索
	 * @param add 是否添加、修改时显示此字段
	 * @param list 是否显示到列表
	 * @param type 数据类型: TextField, Date, TextArea, TextEditor, BasicRadio, GenderRadio
	 * @param allowBlank 编辑时是否允许为空
	 * @param maxLength 最大长度
	 * @param report 是否可以导出
	 */
	public ColumnConfig addColumn(String name, String dataIndex, boolean search, boolean add, boolean list, String type, boolean allowBlank, int maxLength, boolean report) {
		ColumnConfig col = new ColumnConfig(name, dataIndex, search, add, list, type, allowBlank, maxLength, null, report,null);
		listColumnConfig.add(col);
		return col;
	}
	
	
	/**
	 * 给Grid添加一个column
	 * @param name 列的自然语言名称
	 * @param dataIndex Json字符串索引字段名称
	 * @param search 是否被搜索
	 * @param add 是否添加、修改时显示此字段
	 * @param list 是否显示到列表
	 * @param type 数据类型: TextField, Date, TextArea, TextEditor, BasicRadio, GenderRadio
	 * @param allowBlank 编辑时是否允许为空
	 * @param maxLength 最大长度
	 * @param report 是否可以导出
	 * @param comboType 下拉菜单类型
	 */
	public ColumnConfig addColumn(String name, String dataIndex, boolean search, boolean add, boolean list, String type, boolean allowBlank, int maxLength, boolean report,String comboboxType) {
		ColumnConfig col = new ColumnConfig(name, dataIndex, search, add, list, type, allowBlank, maxLength, null, report,comboboxType);
		listColumnConfig.add(col);
		return col;
	}
	
	
	/**
	 * 给Grid添加一个column
	 * @param name 列的自然语言名称
	 * @param dataIndex Json字符串索引字段名称
	 * @param search 是否被搜索
	 */
	public ColumnConfig addColumn(String name, String dataIndex, boolean search) {
		return this.addColumn(name, dataIndex, search, true, true, "TextField", false, 50, null,null);
	}

	/**
	 * 给Grid添加一个参与搜索的column
	 * @param name
	 * @param dataIndex
	 */
	public ColumnConfig addColumn(String name, String dataIndex) {
		return this.addColumn(name, dataIndex, true);
	}
	/**
	 * 给Grid添加一个column 
	 * @param name
	 * @param dataIndex
	 * @param search
	 * @param add
	 * @param list
	 * @param type
	 * @param allowBlank
	 * @param maxLength
	 * @param textFieldParameters
	 */
	public ColumnConfig addColumn(String name, String dataIndex, boolean search, boolean add, boolean list, String type, boolean allowBlank, int maxLength, String textFieldParameters) {
		ColumnConfig col = new ColumnConfig(name, dataIndex, search, add, list, type, allowBlank, maxLength, textFieldParameters, false, null);
		listColumnConfig.add(col);
		return col;
	}
	/**
	 * 给Grid添加一个column (参数太多，所以目前是private方法)
	 * @param name
	 * @param dataIndex
	 * @param search
	 * @param add
	 * @param list
	 * @param type
	 * @param allowBlank
	 * @param maxLength
	 * @param textFieldParameters
	 */
	private ColumnConfig addColumn(String name, String dataIndex, boolean search, boolean add, boolean list, String type, boolean allowBlank, int maxLength, String textFieldParameters,String comboboxType) {
		ColumnConfig col = new ColumnConfig(name, dataIndex, search, add, list, type, allowBlank, maxLength, textFieldParameters, false, comboboxType);
		listColumnConfig.add(col);
		return col;
	}
	
	public void addColumn(ColumnConfig column) {
		listColumnConfig.add(column);
	}

	/**
	 * 增加一个在预览行显示的列
	 * @param dataIndex
	 */
	public void addPreviewColumn(String dataIndex) {
		this.addColumn(null, dataIndex, false, false, false, null, false, 0, null,null);
		this.preview = true;
		this.previewDataIndex = dataIndex;
	}
	
	/**
	 * 增加一个按钮 
	 * @param name 按钮的名字
	 * @param code 按钮类型（1为批量更新）
	 */
	public void addMenuFunction(String name, int code){
		if(!checkBeforeAddMenu(this.action.getServletPath()+"_excelUpdate.action")){
			this.setCanExcelUpdate(false);
			return;
		}
		if(code==1){
			this.setCanExcelUpdate(true);
			this.setUpdateName(name);
		}
	}
	
	/**
	 * 给Grid添加一个Menu，设置表格用户选定元素的的某一column为指定value
	 * @param name Menu的自然语言名称
	 * @param columnDataIndex 操作的Column数据索引
	 * @param value 要设置的value
	 */
//	public void addMenuFunction(String name, String columnDataIndex, String value) {
//		listMenuConfig.add(new MenuConfig(name, columnDataIndex, value, "function"));
//	}
	
	public void addMenuFunction(String name, String... args) {
		
		if(!checkBeforeAddMenu(this.action.getServletPath()+"_abstractUpdateColumn.action"))
			return;
		String columnDataIndex = "";
		String value = "";
		for (int i = 0; i < args.length; i++) {
			if(i == 0){
				columnDataIndex += args[i];
			}else if(i == 1){
				value += args[i]; 
			}else if(i%2 == 0){
				columnDataIndex += ","+args[i];
			}else{
				value += ","+args[i]; 
			}
		}
		listMenuConfig.add(new MenuConfig(name, columnDataIndex, value, "function"));
	}
	
	public void addMenuFunction(String name,StringBuffer sureTodo, String... args) {
		
		if(!checkBeforeAddMenu(this.action.getServletPath()+"_abstractUpdateColumn.action"))
			return;
		String columnDataIndex = "";
		String value = "";
		for (int i = 0; i < args.length; i++) {
			if(i == 0){
				columnDataIndex += args[i];
			}else if(i == 1){
				value += args[i]; 
			}else if(i%2 == 0){
				columnDataIndex += ","+args[i];
			}else{
				value += ","+args[i]; 
			}
		}
		listMenuConfig.add(new MenuConfig(name, columnDataIndex, value, "function",sureTodo.toString()));
	}
	/**
	 * 把选中的id post到一个目标action（的某一方法）
	 * @param name 菜单显示名称
	 * @param targetAction 目标action路径（或url）
	 * @param forceSingleSelect 是否限制只能选中一条记录
	 * @param postByAjax 是否使用ajax post数据
	 * postByAjax：true——使用ajax post数据（当前页面返回结果提示）；
	 * postByAjax：false——使用form post数据（本页面将跳转到目标action页面）；
	 */
	public void addMenuFunction(String name, String targetAction, boolean forceSingleSelect, boolean postByAjax){
		if(!checkBeforeAddMenu(targetAction))
			return;
		
		listMenuConfig.add(new MenuConfig(name, targetAction, "id", forceSingleSelect, postByAjax, "functionB"));
	}
	
	/**
	 * 把选中的列的 checkColumn 的值  post到一个目标action（的某一方法）
	 * @param name 菜单显示名称
	 * @param targetAction 目标action路径（或url）
	 * @param checkColumn 勾选后所传的列值（默认为id）
	 * @param forceSingleSelect 是否限制只能选中一条记录
	 * @param postByAjax 是否使用ajax post数据
	 * postByAjax：true——使用ajax post数据（当前页面返回结果提示）；
	 * postByAjax：false——使用form post数据（本页面将跳转到目标action页面）；
	 */
	public void addMenuFunction(String name, String targetAction, String checkColumn, boolean forceSingleSelect, boolean postByAjax ){
		if(!checkBeforeAddMenu(targetAction))
			return;
		
		listMenuConfig.add(new MenuConfig(name, targetAction, checkColumn, forceSingleSelect, postByAjax, "functionB"));
	}
	
	/**
	 * 给Grid添加一个Menu上的Script功能，直接书写javascript代码
	 * @param name 列标题显示的自然语言名称，例如: "状态"
	 * @param script javascript代码，以"{", "}"为起始结束边界
	 * 		例如: "{window.location='/test/somePage.action';}"
	 */
	public void addMenuScript(String name, String script) {
		if(checkBeforeAddMenuScript(script))
			listMenuConfig.add(new MenuConfig(name, null, script, "script"));
	}
	
	/**
	 * 给Grid添加一个Menu上的提示框功能，功能包含script的全部，不写value会自动弹窗提示，可直接书写javascript代码
	 * @param name 列标题显示的自然语言名称，例如: "状态"
	 * @param script javascript代码，以"{", "}"为起始结束边界
	 * 		例如: "{window.location='/test/somePage.action';}"
	 */
	public void addMenuTips(String name, String script) {
		if(checkBeforeAddMenuScript(script))
			listMenuConfig.add(new MenuConfig(name, null, script, "tips"));
	}
	/**
	 * 给Grid添加一个Column上的Render功能，将值做简单替换转换后输出到列
	 * @param name 列标题显示的自然语言名称，例如: "查看"
	 * @param renderer 转换规则，支持一个变量
	 * 		例如: "<a href=xxx_viewDetail.action?id=${value} target=_blank>查看详细信息</a>"
	 * @param columnDataIndex 从addColumn中已经添加的列中获取名为columnDataIndex的列的数据，
	 * 		作为变量传如render的 ${value}
	 */
	public void addRenderFunction(String name, String renderer, String columnDataIndex) {
		listRenderConfig.add(new RenderConfig(name, renderer, columnDataIndex, "function"));
	}
	
	/**
	 * 非常难用的方法！ 请注意引号的多层转换
	 * 给Grid添加一个Render功能，可以将当前行的记录做某种运算后输出
	 * @param name 列标题显示的自然语言名称，例如: "查看"
	 * @param renderer 转换规则。可以使用 record.data['columnDataIndex'] 来引用列名为 columnDataIndex 列的值
	 */
	public void addRenderFunction(String name, String renderer) {
		this.addRenderFunction(name, renderer, "");
	}
	
	/**
	 * 给Grid添加一个Column上的Script功能，直接书写javascript代码
	 * 将变量运算后return一个值，输出到列
	 * @param name 列标题显示的自然语言名称，例如: "状态"
	 * @param script javascript代码，以"{", "}"为起始结束边界
	 * 		例如: "{if (${value}=='0') return '未提交'; if (${value}=='1') return '已提交'; if (${value}=='2') return '已审核'; if (${value}=='3') return '审核未通过';}"
	 * @param columnDataIndex
	 */
	public void addRenderScript(String name, String script, String columnDataIndex) {
		listRenderConfig.add(new RenderConfig(name, script, columnDataIndex, "script"));
	}
	
	/**
	 * 非常难用的方法！ 暂不开放为public
	 * 给Grid添加一个Script功能，直接书写javascript代码
	 * @param name 列标题显示的自然语言名称，例如: "状态"
	 * @param script javascript代码，以"{", "}"为起始结束边界。可以使用 record.data['columnDataIndex'] 来引用列名为 columnDataIndex 列的值
	 */
	private void addRenderScript(String name, String script) {
		this.addRenderScript(name, script, "");
	}
	
	/**
	 * getExt... 方法，供页面struts标签输出调用 
	 * @return
	 */
	public String getExtFields() {

		this.prepare();

		return extFields;
	}
	
	public String getExtUpdateFields() {
		
		this.prepare();
		
		return extUpdateFields;
	}

	public String getExtColumnModel() {

		this.prepare();

		return extColumnModel;
	}

	public String getExtSearchItems() {

		this.prepare();

		return extSearchItems;
	}

	public String getExtSearchVars() {

		this.prepare();

		return extSearchVars;
	}
	public String getExtSearchVarsWithValue(){
		this.prepare();
		String extSearchVarsWithValue = extSearchVars.replaceAll("name:'(.*?)',\\s*id", "name:'$1',value: $1_value,id");
		return extSearchVarsWithValue;
	}
	public String getExtSearchValues() {

		this.prepare();

		return extSearchValues;
	}

	public String getExtAddItems() {
		
		this.prepare();
		
		return extAddItems;
	}

	public String getExtAddVars() {
		
		this.prepare();
		
		return extAddVars;
	}
	
	public String getExtMenus() {

		this.prepare();
		
		return extMenus;
	}
	
	public String getExtMenuFunctions() {
		
		this.prepare();
		
		return extMenuFunctions;
	}
	
	public String getExtMenuScripts() {
		
		this.prepare();
		
		return extMenuScripts;
	}

	public String getExtRenderColumns() {
		
		this.prepare();
		
		return extRenderColumns;
	}

	public String getExtRenderFunctions() {
		
		this.prepare();
		
		return extRenderFunctions;
	}

	public String getFieldsFilter() {
		
		this.prepare();
		
		return fieldsFilter;
	}
	
	public String getFieldsClean() {
		
		this.prepare();
		
		return fieldsClean;
	}

	public String getExtValueRenderFunctions() {
		
		this.prepare();
		
		return extValueRenderFunctions;
	}
	
	public String getComboboxStores() {
		this.prepare();
		return comboboxStores;
	}

	public void setComboboxStores(String comboboxStores) {
		this.comboboxStores = comboboxStores;
	}

	public List<ColumnConfig> getListColumnConfig() {
		return listColumnConfig;
	}

	public List<MenuConfig> getListMenuConfig() {
		return listMenuConfig;
	}
	
	/**
	 * prepare()方法
	 * 准备给页面层需要的输出元素
	 * 页面get...()方法之前需要先调用此方法
	 */
	private void prepare() {
		try {


			if (this.isPrepared) {
				return;
			}
			
			String backParam = action.getBackParam();
			
			Map<String,Object> sParams = null;
			if(backParam != null){
				String uri = action.getListAction();
				uri = uri.substring(0,uri.lastIndexOf("?"));
				sParams = GridSearchHelper.getSearchParamInSession(uri);//从Session中读取
			}
			
			
			extFields = "";
			
			extUpdateFields = "";

			extColumnModel = "";

			extSearchVars = "";
			
			extSearchValues = "";

			extSearchItems = "";
			
			extPreSearchItems = "";

			extAddVars = "";

			extAddItems = "";

			fieldsFilter = "";
			
			fieldsClean = "";
			
			copyPreSearch = "";
			
			extMenus = "";
			
			extMenuFunctions = "";
			
			extMenuScripts = "";
			
			extRenderFunctions = "";
			
			extRenderColumns = "";
			
			extValueRenderFunctions = "";
			
			excelFiledsFilter = "";
			
			comboboxStores = "";
			
			/**
			 * 构建Grid，与Column相关元素
			 */
			for (int i = 0; i < listColumnConfig.size(); i++) {

				ColumnConfig column = listColumnConfig.get(i);

				if (i == 0) {
					/**
					 * 第一列
					 */

					extFields += "'" + column.getDataIndex() + "'";
					
					extUpdateFields += "'bean." + column.getDataIndex() + "'";

					/**
					 * 第一列必须为id，id不显示。张利斌 20080805
					 */
//					extColumnModel += "{id:'" + column.getDataIndex()
//							+ "',header:'" + column.getName() + "',dataIndex:'"
//							+ column.getDataIndex() + "',width:100},\n";

					extAddItems += column.getDataIndex();

				} else {
					/**
					 * 其他列
					 */

					extFields += ",'" + column.getDataIndex() + "'";
					
					extUpdateFields += ",'bean." + column.getDataIndex() + "'";

					if (column.isList()) {
						if (!column.getDataIndex().endsWith(".id")) {
							extColumnModel += "{header:'" + column.getName()
								+ "',dataIndex:'" + column.getDataIndex()
								+ "',width:100";
							
							if (this.canSearch && column.isSearch()) {
								extColumnModel += ", renderer: ValueRender_" + column.getDataIndex_();
							}
							
							extColumnModel += "},\n";
						}
					}

					if (column.isAdd()) {
						if (column.getDataIndex().indexOf(".") < 0) {
							extAddItems += "," + column.getDataIndex_();
							if(column.getType().equals("File"))
								extAddItems += "," + column.getDataIndex_()+"upload";
						} else {
							String bean = column.getDataIndex();
							if (bean.endsWith(".name")) {
								bean = bean.substring(0, bean.length() - 5);
								if (bean.indexOf(".") >=0)
									bean = bean.substring(bean.indexOf(".")+1);
								if (bean.indexOf(".") < 0) {
									extAddItems += "," + column.getDataIndex_();
									if(column.getType().equals("File"))
										extAddItems += "," + column.getDataIndex_()+"upload";
								}
							}
						}
					}
				}

				/**
				 * 对所有列
				 */
				if (this.canSearch && column.isSearch()) {
					
					String indexValue = "";
					/**
					 * 如果session中已经存在搜索参数的缓存了，那么就直接填充搜索表单
					 */
					if(sParams != null){
						String dataIndex = column.getDataIndex();
						if(dataIndex != null){
							if(dataIndex.contains("_")){
								dataIndex = dataIndex.replace("_", ".");
							}
							if(sParams.containsKey("search__" + dataIndex)){
								String[] values = (String[])sParams.get("search__" + dataIndex);
								indexValue = values[0];
//								System.out.println(indexValue);
							}
						}
					}
					
					
					String searchBean = column.getDataIndex();
					
					//定义变量保存输入条件的值
					extSearchValues += "var _s_"+column.getDataIndex_()+"_value = document.getElementById('_s_"+column.getDataIndex_()+"').value; \n";
					
					if (column.getType() != null && column.getType().equals("TextField") && (searchBean.endsWith(".name") ||searchBean.toLowerCase().startsWith("combobox_")|| column.getComboList()!=null ) ) {
						
						//远程store定义
						//comboboxStores +="var "+column.getDataIndex_()+"Store = new Ext.data.SimpleStore( {proxy : new Ext.data.HttpProxy({url:'"+action.getServletPath()+"_comboDate.action?column="+column.getDataIndex()+"'}),fields: ['id', 'name'],remoteSort : true});\n";
						
						extSearchVars += "var _s_" + column.getDataIndex_() + " = new Ext.form."+column.getComboboxType()+"({" +			//不同于addVars
								"	store: "+ initComboStore( column,extSearchVars )+"," +
								/**
								 * 本地store 屏蔽，改为远程ajax请求
								 */
//								"	store: new Ext.data.SimpleStore({" +
//								"        fields: ['id', 'name']," +
//								"        data : [";
//
//						for (int j=0; j<comboList.size(); j++) {
//							Object[] s = (Object[])comboList.get(j);
//							if (j == 0)
//								extSearchVars += "        	['" + s[0].toString() + "', '" + s[1].toString() + "']";
//							else
//								extSearchVars += ",        	['" + s[0].toString() + "', '" + s[1].toString() + "']";
//						}
//						if(column.getComboList()!=null){
//							extSearchVars += ",        	['', '']";
//						}
//						
//						extSearchVars += "        ]" +
//								"   })," +
								
								"	valueField: 'id'," +
								"	value: '" + indexValue +"'," + 
								"	displayField:'name'," +
								"	selectOnFocus:true," +
								"  listeners:{'blur':function() {if(this.getRawValue()==''||this.getValue()==''){this.setRawValue('');this.setValue('');}}}," +
								"	allowBlank: true," +															//不同于addVars
								"	typeAhead:true," +																//不同于addVars
								"	fieldLabel: '" + column.getName() + "'," ;										//不同于addVars
							extSearchVars += "	name:'_s_" + column.getDataIndex_() + "',";	//不同于addVars
							extSearchVars += "	id:'_s_" + column.getDataIndex_() + "'," ;		//不同于addVars
							extSearchVars += " editable: true," ;
						extSearchVars += "	triggerAction: 'all'," +						//不同于addVars
							//	"	mode:'local'," +
								"	emptyText:''," +
								"	blankText:''" +
								"});\n";
					}else if((searchBean.endsWith("Date")&&(column.getType() == null||column.getType().equals("TextField")||column.getType().equals("")))
							||column.getType() != null && column.getType().equals("Date")){
						/**
						 * 创建日期框
						 */
						extSearchVars += "var _s_" + column.getDataIndex_()
						+ " = new Ext.form.DateField({" + "fieldLabel: '"
						+ column.getName() + "',"
						+ " format: 'Y-m-d', "
						+ "readOnly: false,"
						+ "	value: '" + indexValue +"'," 
						+ " width: 150,"
						+ " anchor: '100%', "
						+ "name:'_s_" + column.getDataIndex_() +"',"
						+ "id: '_s_" + column.getDataIndex_()
						+ "'" + "});\n ";
						
					}else if((searchBean.endsWith("Datetime")&&(column.getType() == null||column.getType().equals("TextField")||column.getType().equals("")))
							||column.getType() != null && column.getType().equals("Datetime")){
						/**
						 * 创建日期时间框
						 */
						extSearchVars += "var _s_" + column.getDataIndex_()
						+ " = new Ext.form.DateField({" + "fieldLabel: '"
						+ column.getName() + "',"
						+ "	value: '" + indexValue +"'," 
						+ " format: 'Y-m-d H:i:s', "
						+ "  menu:new DatetimeMenu(), "
						+ "readOnly: false,"
						+ " width: 150,"
						+ " anchor: '100%', "
						+ "name:'_s_" + column.getDataIndex_() +"',"
						+ "id: '_s_" + column.getDataIndex_()
						+ "'" + "});\n ";
					}else {
						/**
						 * 否则，创建一个普通文本框
						 */
						extSearchVars += "var _s_" + column.getDataIndex_()
								+ " = new Ext.form.TextField({"
								+ "	        fieldLabel: '" + column.getName() + "',"
								+ "			value: '" + indexValue +"'," 
								+ "	        name:'_s_" + column.getDataIndex_() +"',"
								+ "	        id: '_s_" + column.getDataIndex_()
								+ "'	    " + "});\n";
					}

					extSearchItems += "{" + "                columnWidth:280/panelW,"
							+ "                layout: 'form',"
							+ "                items: [_s_" + column.getDataIndex_()
							+ "]            " + "},\n";
					
					extPreSearchItems += "search_" + column.getDataIndex_() + ",\n";

					fieldsFilter += ", '" +"search__"+ column.getDataIndex() + "':Ext.get('_s_"
							+ column.getDataIndex_() + "').dom.value";
					
					fieldsClean += "Ext.get('_s_"+ column.getDataIndex_() + "').dom.value = '' ; ";
					
					excelFiledsFilter += " <input type='hidden' name='search__" + column.getDataIndex() + "' value='\" + Ext.get('_s_" + column.getDataIndex_() + "').dom.value +\"' />";

					copyPreSearch += "Ext.get('_s_" + column.getDataIndex_() + "').dom.value = Ext.get('search_" +column.getDataIndex_() + "').dom.value;\n";
					
					if(column.getComboMap()!=null){
						
						StringBuffer keyValue = new StringBuffer();
						Map map = column.getComboMap();
						for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
							String key = (String) iter.next();
							keyValue.append(" if(p_value=='"+key+"') return '"+map.get(key)+"';");
						}
						
						extValueRenderFunctions += "function ValueRender_" + column.getDataIndex_()
						+ "(p_value, metadata, record) {  "+ keyValue.toString() +" return p_value }";
						
					}else{
						extValueRenderFunctions += "function ValueRender_" + column.getDataIndex_()
							+ "(p_value, metadata, record) {" 
							+ "return p_value.replace("
							+ "Ext.get('_s_" + column.getDataIndex_() + "').dom.value,"
							+ "'<font color=#FF0000>'+Ext.get('_s_" + column.getDataIndex_() + "').dom.value+'</font>');"
							+ "}";
					}
					
				}

				String bean = column.getDataIndex();
				
				if (bean.indexOf(".") < 0) {
					
					/*
					 * if 主bean的字段，直接构造输入域
					 */
					//if(column.getComboList()!=null){
					if(comboboxStores.indexOf(column.getDataIndex_()+"Store")>=0){
						extAddVars += "var " + column.getDataIndex_() + " = new Ext.form.WhatyComboBox({" +
							"	store: "+initComboStore(column, extSearchVars)+ "," +
								"	valueField: 'id'," +
								"	displayField:'name'," +
								"	selectOnFocus:true," +
								"	forceSelection:true," +						//不同于search
								"	allowBlank:" + column.isAllowBlank() + "," +
								"	typeAhead:true," +
								"	fieldLabel: '" + column.getName() + (column.isAllowBlank()?"":"*") + "'," +
								"	name:'" + this.getEntityMemberName() + "." + column.getDataIndex() + "'," +
								"	id:'_" + this.getEntityMemberName() + "." + column.getDataIndex() + "'," +
								"	triggerAction: 'all'," +
								"	editable: true," +
								"	hiddenName: '"+ this.getEntityMemberName() + "." + column.getDataIndex() +"'," +
							//	"	mode:'local'," +
								"	emptyText:''," +
								"	anchor: '90%' ,"+ 
								"	blankText:''" +
								"});\n";
					}else if ((column.getType() != null && column.getType().equalsIgnoreCase("Date")) 
							|| (bean.endsWith("Date")&&(column.getType() == null||column.getType().equals("TextField")||column.getType().equals("")))) {
						/*
						 * 如果显式声明列Column为Date类型，或以Date结尾，认为是日期类型
						 */
						extAddVars += "var " + column.getDataIndex_()
							+ " = new Ext.form.DateField({" + "fieldLabel: '"
							+ column.getName() + (column.isAllowBlank()?"":"*") + "', allowBlank: " + column.isAllowBlank() +","
							+ " format: 'Y-m-d', "
							+ "readOnly: false,"
							+ "name: '" + this.getEntityMemberName() + "." + column.getDataIndex() + "', " + "anchor: '60%' "
							+ "});\n";
						
					} else if ((column.getType() != null && column.getType().equalsIgnoreCase("Datetime")) 
							|| (bean.endsWith("Datetime")&&(column.getType() == null||column.getType().equals("TextField")||column.getType().equals("")))) {
						/*
						 * 如果显式声明列Column为Datetime类型，认为是日期时间类型
						 */
						extAddVars += "var " + column.getDataIndex_()
							+ " = new Ext.form.DateField({" + "fieldLabel: '"
							+ column.getName() + (column.isAllowBlank()?"":"*") + "', allowBlank: " + column.isAllowBlank() +","
							+ " format: 'Y-m-d H:i:s', "
							+ "  menu:new DatetimeMenu(), "		//DatetimeMenu
							+ "readOnly: false,"
							+ "name: '" + this.getEntityMemberName() + "." + column.getDataIndex() + "', " + "anchor: '60%' "
							+ "});\n";
						
					} else if ((column.getType() != null && column.getType().equalsIgnoreCase("Textarea")) || bean.endsWith("Note")) {
						/*
						 * 如果显式声明列Column为Textarea类型，或以Note结尾，认为是长文本类型
						 */
	                    String parameters;
						
						if (column.getTextFieldParameters() != null) {
							parameters = column.getTextFieldParameters();
						} else {
							parameters = "maxLength:" + column.getMaxLength() + ", ";
						}
						
						extAddVars += "var " + column.getDataIndex_()
						+ " = new Ext.form.TextArea({"
						+ "fieldLabel: '" + column.getName() + (column.isAllowBlank()?"":"*") + "', "
						+ "name: '" + this.getEntityMemberName() + "." + column.getDataIndex() + "', "
						+ "allowBlank:" + column.isAllowBlank() + ","
						+ parameters
						+ "anchor: '90%'"
						+ "});\n";

					} else if ((column.getType() != null && column.getType().equalsIgnoreCase("TextEditor"))) {
						/*
						 * 如果显式声明列Column为TextEditor类型，认为是需要在添加修改时以FCKEditor编辑，弹出框将放大
						 */
	                    String parameters;
						
						if (column.getTextFieldParameters() != null) {
							parameters = column.getTextFieldParameters();
						} else {
							parameters = "maxLength:" + column.getMaxLength() + ", ";
						}
						
						extAddVars += "var " + column.getDataIndex_()
						+ " = new Ext.form.TextArea({"
						+ "fieldLabel: '" + column.getName() + (column.isAllowBlank()?"":"*") + "', "
						+ "xtype: 'textarea',hideLabel: true,"
						+ "name: '" + this.getEntityMemberName() + "." + column.getDataIndex() + "', "
						+ "id: '" + this.getEntityMemberName() + "." + column.getDataIndex() + "', "
						+ "allowBlank:" + column.isAllowBlank() + ","
						+ parameters
						+ "anchor: '100% -30'"
						+ "});\n";
						this.setContainFCKEditor(true);

					}else if ((column.getType() != null && column.getType().endsWith("Radio"))) {
						/*
						 * 如果显式声明列Column为Radio类型，认为是Radio，BasicRadio为是否选择 value:0/1  GenderRadio为男女选择 value:男/女
						 */
	                    String parameters;
						
						if (column.getTextFieldParameters() != null) {
							parameters = column.getTextFieldParameters();
						} else {
							parameters = "maxLength:" + column.getMaxLength() + ", ";
						}
						
						extAddVars += "var " + column.getDataIndex_()
						+ " = new Ext.Panel({" 
						+ "layout: 'table',"
						+ "isFormField: true,"
						+ "defaultType: 'radio',"
						+ "fieldLabel: '" + column.getName() + (column.isAllowBlank()?"":"*") + "', "
						+ "name: '" + this.getEntityMemberName() + "." + column.getDataIndex() + "', "
						+ "id: '" + this.getEntityMemberName() + "." + column.getDataIndex() + "', "
						+ "allowBlank:" + column.isAllowBlank() + ","
						+ parameters
						+ "anchor: '60%'," ;
						if(column.getType().equalsIgnoreCase("GenderRadio")){
							extAddVars += "items: [{" 
								+           "name: '"+ this.getEntityMemberName() + "."+column.getDataIndex_()+"'," +
										    "boxLabel: '男'," +
										    "inputValue: '男' " +
										  "},{" +
										    "name: '"+ this.getEntityMemberName() + "."+column.getDataIndex_()+"'," +
										    "checked:true," +
										    "boxLabel: '女'," +
										    "inputValue: '女' " +
										   "}]"
								+ "});  ";
						}else{
							extAddVars += "items: [{" 
								+           "name: '"+ this.getEntityMemberName() + "."+column.getDataIndex_()+"'," +
										    "boxLabel: '是'," +
										    "inputValue: '1' " +
										  "},{" +
										    "name: '"+ this.getEntityMemberName() + "."+column.getDataIndex_()+"'," +
										    "checked:true," +
										    "boxLabel: '否'," +
										    "inputValue: '0' " +
										   "}]"
								+ "});  ";
						}
						

					} else if ((column.getType() != null && column.getType().equals("File"))){
						
						String parameters = "";

						//文件类型输入本地路径实际不写入数据库，不应限制输入长度
						if (column.getTextFieldParameters() != null) {
							parameters = column.getTextFieldParameters();
						}

						/** 去掉不必要代码 张利斌 2009-1-21
						 * 文件类型不需要正则表达式校验
						 * 
						// 如果参数中不含有正则表达式，强制限制不能以空格开头和结尾
						if (parameters != null && parameters.indexOf("regex") < 0) {
							if(bean.equals("name")) {
								parameters += "regex:new RegExp(/^([^\\s()]|[^\\s()][^()]*[^\\s()])$/),regexText:'输入格式：名称字段不能以空格开头和结尾、不能包含半角括号',";
							}else{
								parameters += "regex:new RegExp(/^(\\S|\\S.*\\S)$/),regexText:'输入格式：不能以空格开头和结尾',";
							}
						}
						*/
						
						extAddVars += "var " + column.getDataIndex_()+"upload"
							+ " = new Ext.form.TextField({"
							+ "fieldLabel: '" + column.getName() + (column.isAllowBlank()?"":"*") + "', "
							+ "name: '_upload', "
							+ "allowBlank:" + column.isAllowBlank() + ","
							+ "inputType:'file',"
							+ parameters
							+ "anchor: '90%'"
							+ "});\n";
						
						extAddVars += "var " + column.getDataIndex_()
							+ " = new Ext.form.TextField({"
							+ "fieldLabel: '" + column.getName() + (column.isAllowBlank()?"":"*") + "', "
							+ "name: '_uploadField', "
							+ "value: '"+column.getDataIndex() + "', "
							+ "allowBlank:" + column.isAllowBlank() + ","
							+ "inputType:'hidden',"
							+ parameters
							+ "anchor: '90%'"
							+ "});\n";
							
						this.containFile = true;
					} else {
						/*
						 * 否则，按文本类型处理
						 */
						String parameters;
						
						if (column.getTextFieldParameters() != null) {
							parameters = column.getTextFieldParameters();
						} else {
							parameters = "maxLength:" + column.getMaxLength() + ", ";
						}
						
						// 如果参数中不含有正则表达式，强制限制不能以空格开头和结尾，name字段不允许包含半角括号
						if (parameters != null && parameters.indexOf("regex") < 0) {
							if(bean.equals("name")) {
								parameters += "regex:new RegExp(/^([^\\s()]|[^\\s()][^()]*[^\\s()])$/),regexText:'输入格式：名称字段不能以空格开头和结尾、不能包含半角括号',";
							}else{
								parameters += "regex:new RegExp(/^(\\S|\\S.*\\S)$/),regexText:'输入格式：不能以空格开头和结尾',";
							}
						}
						
						extAddVars += "var " + column.getDataIndex_()
							+ " = new Ext.form.TextField({"
							+ "fieldLabel: '" + column.getName() + (column.isAllowBlank()?"":"*") + "', "
							+ "name: '" + this.getEntityMemberName() + "." + column.getDataIndex() + "', "
							+ "allowBlank:" + column.isAllowBlank() + "," ;
							if (defaultValueMap != null){
                                String value = defaultValueMap .get(column.getDataIndex());
                                 if (value!= null&&value.length()>0){
                                       extAddVars += "   value:'" + value + "'," ;
                                }
							}
							extAddVars +=  parameters
							+ "anchor: '90%'"
							+ "});\n";
					}
				} else {
					
					/*
					 * else 子bean的字段，只取出name字段，构造为 ComboBox
					 */
					if (bean.endsWith(".name")) {
						
						bean = bean.substring(0, bean.length() - 5);
						String bean2 = bean;
						if(bean.indexOf(".")>0){
								bean2 = bean.substring(bean.indexOf(".")+1);
						}
						if (bean2.indexOf(".") < 0) {
							
							List comboList = null;
							if (column.getComboSQL() != null) {
								comboList = this.action.getGeneralService().getBySQL(column.getComboSQL());
							} else {
								bean2 = bean2.substring(0, 1).toUpperCase() + bean2.substring(1);
								comboList = this.action.getMyListService().getIdNameList(bean2);
							}
							extAddVars += "var " + column.getDataIndex_() + " = new Ext.form.WhatyComboBoxForAdd({" +
									"	store: "+ initComboStore(column, extSearchVars) +"," +
									"	valueField: 'id'," +
									"	displayField:'name'," +
									"	selectOnFocus:true," +
									"	forceSelection:true," +						//不同于search
									"	allowBlank:" + column.isAllowBlank() + "," +
									"	typeAhead:true," +
									"	fieldLabel: '" + column.getName() + (column.isAllowBlank()?"":"*") + "'," +
									"	name:'" + this.getEntityMemberName() + "." + column.getDataIndex() + "'," +
									"	id:'" + this.getEntityMemberName() + "." + column.getDataIndex() + "'," +
									"	triggerAction: 'all'," +
									"	editable: true," +
									"	mode:'local'," ;
							if(defaultValueMap != null){
								String value = defaultValueMap .get(column.getDataIndex());
	                            if (value!= null&&value.length()>0){
	                                  extAddVars += "  value:'" + value + "'," ;
	                            }
							}
                            extAddVars += "	emptyText:''," +
									"	anchor: '90%' ,"+ 
									"	blankText:''" +
									"});\n";
						}
					}
				}
			}	// end for (int i = 0; i < listColumnConfig.size(); i++)
			
			/**
			 * id由uuid产生，不加入到Add的Form中 和 显示的列表中
			 */
			if (extAddItems.startsWith("id,"))
				extAddItems = extAddItems.substring(3);
			
			if (extColumnModel.endsWith(",\n"))
				extColumnModel = extColumnModel.substring(0, extColumnModel.length() - 2);

			// after cycle

			extSearchVars += "var _s_search = new Ext.Button({			"
					+ "type: 'submit', "
					+ "text: '"+action.getText("entity.search")+"',			" + "handler: function() { "
					+ " var url = store.proxy.conn.url; var flag = url.lastIndexOf('backParam'); if(flag != -1){url = url.substring(0,flag); store.proxy.conn.url=url;}			"
					+ "store.load({params:{start:0, limit:g_limit"
					+ fieldsFilter + "}});		}		});\n";
			
			// 添加清空按钮    by czc 2010-10-18
			extSearchVars +="var _s_clean = new Ext.Button({			type: 'button', text: '"+action.getText("entity.clean")+"',			handler: function() {	cleanForm();	}		}); \n";

			extSearchItems += "{" + "                columnWidth:.1,"
					+ "                layout: 'form',"
					+ "                items: [_s_search]" + "            },\n";
			
			// 添加清空按钮    by czc 2010-10-18
			extSearchItems += "{" + "                columnWidth:.1,"
					+ "                layout: 'form',"
					+ "                items: [_s_clean]" + "            }\n";
			
			if (extPreSearchItems.length() > 1) {
				extPreSearchItems = extPreSearchItems.substring(0, extPreSearchItems.length()-2);
			}

			/**
			 * 构建Menu，Function，Script相关元素
			 */
			for (int i = 0; i < listMenuConfig.size(); i++) {
				
				MenuConfig menu = listMenuConfig.get(i);
				
				if (menu.getType().equals("function")) {
					
					extMenus += "'-',{" +
						"text:'" + menu.getName() +"'," +
						"iconCls:'selfDef'," +
						"handler: GridConfigMenuFunction_" + i+
						"},";
					
				}
				else if (menu.getType().equals("functionB")){
					extMenus += "'-',{" +
						"text:'" + menu.getName() +"'," +
						"iconCls:'selfDef'," +
						"handler: GridConfigMenuFunction_" + i +
						"},";
				}
				else if (menu.getType().equals("script")){
					
					extMenus += "'-',{" +
						"text:'" + menu.getName() +"'," +
						"iconCls:'selfDef'," +
						"handler: GridConfigMenuScript_" + i + "},";
					
					extMenuScripts += "function GridConfigMenuScript_" + i + "()" + menu.getValue();
				}
				else if (menu.getType().equals("tips")){
					
					extMenus += "'-',{" +
						"text:'" + menu.getName() +"'," +
						"color:'red',"+
						"handler: GridConfigMenuScript_" + i + "},";
					if(menu.getValue()==null || "".equals(menu.getValue())){
						extMenuScripts += "function GridConfigMenuScript_" + i + "(){Ext.MessageBox.alert('提示','"+ menu.getName() +"')}";
					}else{
						extMenuScripts += "function GridConfigMenuScript_" + i + "()" + menu.getValue();
					}
				}
				
			}
			
			/**
			 * 构建Render，Function，Script相关元素
			 */
			for (int i = 0; i < listRenderConfig.size(); i++) {
				
				RenderConfig render = listRenderConfig.get(i);

				if (render.getType().equals("function")) {
					
					if (render.getColumnDataIndex() == null || render.getColumnDataIndex().equals("")) {
						extRenderFunctions += "function GridConfigRenderFunction_" + i
							+ "(p_value, metadata, record){" + "return \""
							+ render.getRenderer()
							+ "\";}";
					} else {
						extRenderFunctions += "function GridConfigRenderFunction_" + i
								+ "(p_value, metadata, record){" + "return \""
								+ render.getRenderer().replace("\"", "\\\"").replace("${value}", "\"+p_value+\"")
								+ "\";}";
					}

				} else if (render.getType().equals("script")){
					
					if (render.getColumnDataIndex() == null || render.getColumnDataIndex().equals("")) {
						extRenderFunctions += "function GridConfigRenderFunction_" + i
							+ "(p_value, metadata, record)" + render.getRenderer();
					} else {
						extRenderFunctions += "function GridConfigRenderFunction_" + i
							+ "(p_value, metadata, record)" + render.getRenderer().replace("\"", "\\\"").replace("${value}", "p_value");
					}
					
				}

				extRenderColumns += ",{header: '" + render.getName() + "',"
						+ " width: 100, sortable: false,"
						+ " renderer: GridConfigRenderFunction_" + i + ","
						+ " dataIndex: '" + render.getColumnDataIndex() + "'}";
			}
			
			/**
			 * 准备完毕
			 */
			this.isPrepared = true;
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * menu, function配置私有类
	 * 配置一个名为name的menu，关联到一个function
	 * 完成功能将名为columnDataIndex的列的值置为value
	 */
	private class MenuConfig {
		
		private String name;
		
		private String columnDataIndex;
		
		private String value;
		
		private String type;
		
		private String action;
		
		private boolean single;
		
		private boolean ajax;
		
		private String checkColumn = "id"; // 勾选操作functionB， gridjs.jsp中的GridConfigMenuFunction 所取的列
		
		private String sureTodo;
		

		public MenuConfig(String name, String columnDataIndex, String value, String type) {
			this.name = name;
			this.columnDataIndex = columnDataIndex;
			this.value = value;
			this.type = type;
		}
		
		public MenuConfig(String name, String columnDataIndex, String value, String type,String sureTodo) {
			this.name = name;
			this.columnDataIndex = columnDataIndex;
			this.value = value;
			this.type = type;
			this.sureTodo = sureTodo;
		}
		
		public MenuConfig(String name, String action, String checkColumn, boolean single, boolean ajax, String type){
			this.name = name;
			this.action = action;
			this.checkColumn = checkColumn;
			this.single = single;
			this.ajax = ajax;
			this.type = type;
		}
		
		public String getSureTodo() {
			return sureTodo;
		}

		public void setSureTodo(String sureTodo) {
			this.sureTodo = sureTodo;
		}

		public String getColumnDataIndex() {
			return columnDataIndex;
		}
		
		public String getColumnDataIndex_() {
			return columnDataIndex.replace(".", "_");
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
		
		public String getType() {
			return type;
		}

		public String getAction() {
			return action;
		}

		public boolean isSingle() {
			return single;
		}

		public boolean isAjax() {
			return ajax;
		}
		
		public String getCheckColumn() {
			return checkColumn;
		}

		public void setCheckColumn(String checkColumn) {
			this.checkColumn = checkColumn;
		}
	}
	
	private class RenderConfig {
		
		private String name;
		
		private String renderer;
		
		private String columnDataIndex;
		
		private String type;

		public RenderConfig(String name, String renderer, String columnDataIndex, String type) {
			this.name = name;
			this.renderer = renderer;
			this.columnDataIndex = columnDataIndex;
			this.type = type;
		}

		public String getColumnDataIndex() {
			return columnDataIndex;
		}

		public String getName() {
			return name;
		}

		public String getRenderer() {
			return renderer;
		}
		
		public String getType() {
			return type;
		}
	}
	
	public String getExcelFiledsFilter() {
		return excelFiledsFilter;
	}

	public void setExcelFiledsFilter(String excelFiledsFilter) {
		this.excelFiledsFilter = excelFiledsFilter;
	}

	public boolean isCanBatchAdd() {
		return canBatchAdd;
	}

	/**
	 * 设置是否显示复选框一列
	 * @param isShow :  true 显示 ;false 不显示
	 */
	public void setShowCheckBox(boolean showCheckBox) {
		this.showCheckBox = showCheckBox;
	}
	
	public boolean isShowCheckBox(){
			return showCheckBox;
	}
	
	/**
	 * 权限设置之后打开不允许使用，拦截addMenuScript按钮，只拦截.action
	 * @param scriptAction
	 * @return
	 */
	public boolean checkBeforeAddMenuScript(String scriptAction){
		if(true){
			int indexOfaction = scriptAction.indexOf(".action");
			
			if(indexOfaction>0){
				String actionstr = scriptAction.substring(0,indexOfaction);
				while(actionstr.endsWith("myList")){
					indexOfaction = scriptAction.indexOf(".action",indexOfaction+1);
					if(indexOfaction>0)
						actionstr = scriptAction.substring(0,indexOfaction);
					else
						return true;
				}
				int indexOfeq = actionstr.lastIndexOf("=");//=等号
				if(indexOfeq >= 0){
					actionstr = actionstr.substring(indexOfeq+1);
				}
				int indexOfcn= actionstr.lastIndexOf(":");//:冒号
				if(indexOfcn >= 0){
					actionstr = actionstr.substring(indexOfcn+1);
				}
				int indexOfsq= actionstr.lastIndexOf("'");//'单引号
				if(indexOfsq >= 0){
					actionstr = actionstr.substring(indexOfsq+1);
				}
				int indexOfdq= actionstr.lastIndexOf("\"");//"双引号
				if(indexOfdq >= 0){
					actionstr = actionstr.substring(indexOfdq+1);
				}
				int indexOfbl= actionstr.lastIndexOf(" ");// 空格
				if(indexOfbl >= 0){
					actionstr = actionstr.substring(indexOfbl+1);
				}
				return checkBeforeAddMenu(actionstr+".action");
			}
		}
		return true;
	}
	
	// TODO: 权限设置之后打开不允许使用./../等
	public boolean checkBeforeAddMenu(String targetAction) {
//		if(BaseAction.isPriInterceptor){
//			if(targetAction.indexOf("?")>0){
//				targetAction= targetAction.substring(0,targetAction.indexOf("?"));
//			}
//			if(targetAction.indexOf("/")<0){
//				if(this.action!=null&&this.action.servletPath!=null)
//					targetAction = this.action.servletPath.substring(0,this.action.servletPath.lastIndexOf("/"))+"/"+targetAction;
//			}
//			if (targetAction.indexOf("/entity/workspaceStudent")>=0 || targetAction.indexOf("/entity/workspaceTeacher")>=0) {
//				return true;
//			}
//			UserSession us = (UserSession)ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
//			if(us != null){
//				Set capabilitySet=us.getUserPriority().keySet();
//				String fullCapability = "";
//				if(targetAction.indexOf("_")<0){
//					targetAction = targetAction.substring(0,targetAction.lastIndexOf(".")) + "_abstractList" + ".action";
//					fullCapability = targetAction.substring(0, targetAction.lastIndexOf("_")+1) + "*" + ".action";
//					
//				} else {
//					fullCapability = targetAction.substring(0, targetAction.lastIndexOf("_")+1) + "*" + ".action";
//				}
//				if(capabilitySet.contains(fullCapability) || capabilitySet.contains(targetAction)){
//					return true;
//				}
//				return false;
//			}
//		}
		return true;
	}
	
	//用于定义远程访问combobox store
	/*private String initComboStore(ColumnConfig column, String extSearchVars2) {
		// TODO Auto-generated method stub
		if(comboboxStores.indexOf(column.getDataIndex_()+"Store")<= 0){
			comboboxStores +="var "+column.getDataIndex_()+"Store = new Ext.data.SimpleStore( {proxy : new Ext.data.HttpProxy({url:'"+action.getServletPath()+"_comboDate.action?column="+column.getDataIndex()+"'}),fields: ['id', 'name'],remoteSort : true});\n";
		}
		
		return column.getDataIndex_()+"Store";
	}*/

	//用于定义远程访问combobox store
    private String initComboStore(ColumnConfig column, String extSearchVars2) {

            // TODO Auto-generated method stub
            if(comboboxStores .indexOf(column.getDataIndex_()+"Store")<= 0){
                  //增加请求所带的参数。防止initGrid中空指针。李冰
                 String queryString = "";
                 String tempString = ServletActionContext.getRequest().getQueryString();
                  if ( tempString!= null&&!"".equals(tempString))
                        queryString = tempString + "&";
                 
                  comboboxStores +="var " +column.getDataIndex_()+"Store = new Ext.data.SimpleStore( {proxy : new Ext.data.HttpProxy({url:'"+action.getServletPath()+ "_comboDate.action?" + queryString + "column=" +column.getDataIndex()+"'}),fields: ['id', 'name'],remoteSort : true});\n";
           }
            return column.getDataIndex_()+"Store" ;
     }
	
	//该方法用于返回指定 column 的combobox下拉数据
    public List getComboBoxDate(ColumnConfig column){
         List comboList = new ArrayList();
         try {
              String searchBean = column.getDataIndex();
              if(column.getComboList() != null){
                   comboList = column.getComboList();
              }else if (column.getComboSQL() != null) {
   
                        comboList = this.action.getGeneralService().getBySQL(column.getComboSQL());
   
   
              } else {
                  
                   if(searchBean.toLowerCase().startsWith("combobox_")){
                        searchBean = searchBean.substring(9,searchBean.indexOf("."));
                   }else if(searchBean.endsWith(".name")){
                        searchBean = searchBean.substring(0, searchBean.length() - 5);
                        while (searchBean.indexOf(".") > 0) {
                             searchBean = searchBean.substring(searchBean.indexOf(".") + 1);
                        }
                   }
                   searchBean = searchBean.substring(0, 1).toUpperCase() + searchBean.substring(1);
                   try {
                        comboList = this.action.getMyListService().getIdNameList(searchBean);
                   } catch (Exception e) {
                        e.printStackTrace();
                   }
              }
         } catch (EntityException e) {
              e.printStackTrace();
         }
         return comboList;
    }
	
	//该方法用于返回指定 column 的combobox下拉数据
	public List getComboBoxDate(ColumnConfig column,String siteId){
		List comboList;
		
		String searchBean = column.getDataIndex();
		if(column.getComboList() != null){
			comboList = column.getComboList();
		}else if (column.getComboSQL() != null) {
			comboList = this.action.getMyListService().queryBySQL(column.getComboSQL());

		} else {
			
			if(searchBean.toLowerCase().startsWith("combobox_")){
				searchBean = searchBean.substring(9,searchBean.indexOf("."));
			}else if(searchBean.endsWith(".name")){
				searchBean = searchBean.substring(0, searchBean.length() - 5);
				while (searchBean.indexOf(".") > 0) {
					searchBean = searchBean.substring(searchBean.indexOf(".") + 1);
				}
			}
			searchBean = searchBean.substring(0, 1).toUpperCase() + searchBean.substring(1);
//			String siteId = this.action.getSite_temp().getId();
//			comboList = this.action.getMyListService().getIdNameList(searchBean,siteId);
			comboList = this.action.getMyListService().getIdNameList(searchBean);
		}
		return comboList;
	}
	
	public ColumnConfig getColumByDateIndex(String dateIndex){
		for (int i = 0; i < listColumnConfig.size(); i++) {
			ColumnConfig columnConfig = listColumnConfig.get(i);
			if(columnConfig.getDataIndex().equalsIgnoreCase(dateIndex))
				return columnConfig;
			
		}
		return null;
	}

	public boolean isCanExcelUpdate() {
		return canExcelUpdate;
	}

	public void setCanExcelUpdate(boolean canExcelUpdate) {
		this.canExcelUpdate = canExcelUpdate;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Map<String, String> getDefaultValueMap() {
		return defaultValueMap;
	}

	public void setDefaultValueMap(Map<String, String> defaultValueMap) {
		this.defaultValueMap = defaultValueMap;
	}
	
}
