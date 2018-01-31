package com.whaty.platform.sso.web.action.admin;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PePriority;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PePriorityManageAction extends MyBaseAction<PePriority	> {
	private Page page;
	private int totalCount; // 总条数
	private int startIndex = 0; // 开始数
	private int pageSize = 10; // 页面显示数
	
	private String const_ids[];
	
	private String qx_info;
	private String qx_id;
	 
	private String id;
	private String method;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 权限项功能点管理列表加载
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("prioritys.gridtitle"));
		this.getGridConfig().addColumn(this.getText("priority.id"), "id", false);
		this.getGridConfig().addColumn(this.getText("priority.name"), "name");
		this.getGridConfig().addColumn(this.getText("priority.category"), "pePriCategory.name");
		this.getGridConfig().addColumn(this.getText("priority.namespace"), "namespace");
		this.getGridConfig().addColumn(this.getText("priority.action"), "action");
		this.getGridConfig().addColumn(this.getText("priority.method"), "method");
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		//dinggh 添加权限类别设置
		//this.getGridConfig().addMenuFunction(this.getText("添加所属工作室"),
		//		"/sso/admin/priority_toSelectPriCategory.action",
		//		true,
		//		false);
		//this.getGridConfig().addMenuFunction(this.getText("取消所属工作室"),
		//		"/sso/admin/priority_toCancelPriCategory.action",
		//		true,
		//		false);	
		//over 
		this.getGridConfig().addMenuFunction(this.getText("添加所属工作室"),
				"/sso/admin/priority_toSelectPriCategory.action?method=addenum",
				true,
				false);
		this.getGridConfig().addMenuFunction(this.getText("取消所属工作室"),
				"/sso/admin/priority_toSelectPriCategory.action?method=cancelenum",
				true,
				false);	
		
//		this.getGridConfig().addMenuFunction(this.getText("添加所属工作室"),
//				"/sso/admin/pePriEnumView.action?method=addenum",
//				true,
//				false);
//		this.getGridConfig().addMenuFunction(this.getText("取消所属工作室"),
//				"/sso/admin/pePriEnumView.action?method=cancelenum",
//				true,
//				false);	
		
	 }
	/**
	 * 去选择工作室（添加）
	 * dinggh
	 * @return
	 */
	public String toSelectPriCategory(){
		try{
			if (this.getIds() != null && this.getIds().length() > 0) {
				String[] ids = getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					id=ids[i];
					this.setId(id);
				}
			} 
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return "toSelectPriCategory";
	}
	 
	@Override
	public void setEntityClass() {
		this.entityClass = PePriority.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/priority";
	}
	
	public void setBean(PePriority instance) {
		super.superSetBean(instance);
	}
	
	public PePriority getBean(){
		return super.superGetBean();
	}
	/**
	 * 权限项功能点管理列表查询
	 */
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		dc.createAlias("pePriCategory", "pePriCategory");
		return dc;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getQx_info() {
		return qx_info;
	}
	public void setQx_info(String qx_info) {
		this.qx_info = qx_info;
	}
	public String getQx_id() {
		return qx_id;
	}
	public void setQx_id(String qx_id) {
		this.qx_id = qx_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getConst_ids() {
		return const_ids;
	}
	public void setConst_ids(String[] const_ids) {
		this.const_ids = const_ids;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}

}
