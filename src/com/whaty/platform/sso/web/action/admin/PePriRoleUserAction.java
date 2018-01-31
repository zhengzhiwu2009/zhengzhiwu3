package com.whaty.platform.sso.web.action.admin;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.web.action.information.sms.SiteManagerPhoneViewAction;

public class PePriRoleUserAction extends MyBaseAction {
	
	private String type;
	private String id;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 权限组已有用户列表加载
	 * @author huguolong
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().setTitle(this.getText("查看用户"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		if(type.equals("2")){
			this.getGridConfig().addColumn(this.getText("姓名"), "name");
		}else{
			this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		}
		this.getGridConfig().addColumn(this.getText("用户名"), "ssoUser.loginId");
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
	}

	@Override
	public void setEntityClass() {
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/pePriRoleUser";
		
	}
	/**
	 * 权限组已有用户列表查询，根据不同用户类型查询出不同结果集
	 * @author huguolong
	 */
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc ;
		if("0".equals(type)){
			dc = DetachedCriteria.forClass(PeBzzStudent.class);
		}else if("1".equals(type)){
			dc = DetachedCriteria.forClass(PeTeacher.class);
		}else if("2".equals(type)){
			dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		}else if("3".equals(type)){
			dc = DetachedCriteria.forClass(PeManager.class);
		}else{
			dc = DetachedCriteria.forClass(PeBzzStudent.class);
		}
		dc.createCriteria("ssoUser", "ssoUser").createAlias("pePriRole", "pePriRole");
		dc.add(Restrictions.eq("pePriRole.id", id));
		return dc;
	}


}
