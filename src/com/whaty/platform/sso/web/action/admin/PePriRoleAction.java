package com.whaty.platform.sso.web.action.admin;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PePriRoleAction extends MyBaseAction<PePriRole> {
	
	

	@Override
	public void setGeneralService(GeneralService<PePriRole> generalService) {
		
	}
	
	public void setPePriRoleService(GeneralService generalService) {
		super.setGeneralService(generalService);
	}

	public PePriRole getBean() {
		return super.superGetBean();
	}

	public void setBean(PePriRole bean) {
		this.superSetBean(bean);
	}
	/**
	 * 权限组管理列表加载
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("权限组管理"));

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("权限组名称"), "name");
		this.getGridConfig().addColumn(this.getText("所属工作室"), "enumConstByFlagRoleType.name");
		this.getGridConfig().addColumn(this.getText("所属工作室"), "enumConstByFlagRoleType.code", false, false, false, "", false, 0);
		this.getGridConfig().addRenderFunction(this.getText("已有管理员用户"), "<a href='pePriRoleUser.action?id=\"+record.data['id']+\"&type=\"+record.data['enumConstByFlagRoleType.code']+\"' target='_self'>查看</a>");
		this.getGridConfig().addRenderFunction(this.getText("设置权限"), "<a href='pePriCategoryView.action?role=${value}&method=Category' target='_self'>设置</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看权限"), "<a href='pePriCategoryView.action?role=${value}&method=myCategory' target='_self'>查看</a>", "id");
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		//dgh
		this.getGridConfig().addColumn(this.getText("描述"), "roledesc");
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PePriRole.class;
		 
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/pePriRole";
		
	}
	/**
	 * 权限组管理列表查询
	 */
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
		dc.createAlias("enumConstByFlagRoleType", "enumConstByFlagRoleType");
		return dc;
	}
	

}
