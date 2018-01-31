package com.whaty.platform.sso.web.action.admin;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrPriRole;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrPriRoleAction extends MyBaseAction<PrPriRole>{
	
	private String category;
	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public PrPriRole getBean() {
		return super.superGetBean();
	}

	public void setBean(PrPriRole bean) {
		super.superSetBean(bean);
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().setTitle(this.getText("设置权限"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("权限"), "pePriority.name");
		this.getGridConfig().addColumn(this.getText("权限所属类别"), "pePriority.pePriCategory.name");
		this.getGridConfig().addColumn(this.getText("状态"), "flagIsvalid", false, false, false, "", false, 0);
		this.getGridConfig().addRenderScript(this.getText("状态"), "{if(${value}==1) return '有效';else return '禁用';}", "flagIsvalid");
		this.getGridConfig().addMenuFunction(this.getText("权限启用"), "flagIsvalid", "1");
		this.getGridConfig().addMenuFunction(this.getText("权限禁用"), "flagIsvalid", "0");
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
	
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrPriRole.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/prPriRole";
		
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		String sql = "select b.id " +
				"from pe_priority b," +
					"(select a.id aid " +
						"from pe_pri_category a " +
						"connect by prior a.id = a.fk_parent_id " +
						"start with a.id = '"+category+"') c " +
				"where b.fk_pri_cat_id = c.aid";
		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PrPriRole.class);
		dc.createAlias("pePriRole", "pePriRole").createCriteria("pePriority", "pePriority").createAlias("pePriCategory", "pePriCategory")
			.add(Restrictions.eq("pePriRole.id", this.getBean().getPePriRole().getId()));
		if(list !=null && !list.isEmpty()){
			dc.add(Restrictions.in("pePriority.id", list));
		}else{
			dc.add(Restrictions.isNull("pePriority.id"));
		}
			
		return dc;
	}

}
