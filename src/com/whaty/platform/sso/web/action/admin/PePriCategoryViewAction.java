package com.whaty.platform.sso.web.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PePriCategory;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PePriority;
import com.whaty.platform.entity.bean.PrPriRole;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PePriCategoryViewAction extends MyBaseAction {

	private String role;
	private String method;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 *权限组设置权限列表加载
	 *@author huguolong
	 */
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("设置权限-权限分类"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("类别名称"), "name");
		
		// this.getGridConfig().addRenderFunction(this.getText("设置权限"), "<a
		// href='prPriRole.action?category=${value}&bean.pePriRole.id="+role+"'
		// target='_self'>设置</a>", "id");
		this.getGridConfig().addMenuScript(this.getText("test.back"),"{window.history.back();}");
		if (method.equals("Category")) {
			this.getGridConfig().addMenuFunction(this.getText("设置权限"),"CategorySet_"+this.getRole());
		}
		if (method.equals("myCategory")) {
			this.getGridConfig().addMenuFunction(this.getText("取消权限"),"CategoryDel_"+this.getRole());
		}
	}
	/**
	 * 设置权限和取消权限方法
	 * @author huguolong
	 */
	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		String ap[] = action.split("_");
		String role = ap[1];//ActionContext.getContext().getSession().get("role").toString().trim();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
					DetachedCriteria rodc = DetachedCriteria.forClass(PePriRole.class);
					rodc.add(Restrictions.eq("id", role));
					List<PePriRole> priRolelist = this.getGeneralService().getList(rodc);
					DetachedCriteria peprdc = DetachedCriteria.forClass(PePriority.class);
					peprdc.add(Restrictions.in("id", ids));
					List<PePriority> pePrioritylist = this.getGeneralService().getList(peprdc);
					
					if(priRolelist.size()>0){
						if(pePrioritylist.size()>0){
							if(action.indexOf("CategorySet")>=0){
								for(int n =0 ;n<pePrioritylist.size();n++){
									PrPriRole priRole = new PrPriRole();
									priRole.setPePriority(pePrioritylist.get(n));
									priRole.setPePriRole(priRolelist.get(0));
									priRole.setFlagIsvalid("1");
									this.getGeneralService().save(priRole);
								}
							}
							if(action.indexOf("CategoryDel")>=0){
								DetachedCriteria dedc = DetachedCriteria.forClass(PrPriRole.class);
								dedc.createCriteria("pePriority","pePriority");
								dedc.createCriteria("pePriRole","pePriRole");
								dedc.add(Restrictions.eq("pePriRole.id", role));
								dedc.add(Restrictions.in("pePriority.id", ids));
								List<PrPriRole> prilist = this.getGeneralService().getList(dedc);
								if(prilist.size()>0){
									for(int m=0;m<prilist.size();m++){
										this.getGeneralService().delete(prilist.get(m));
									}
								}else{
									map.clear();
									map.put("success", "false");
									map.put("info", "没有此权限");
								}
							}
							map.clear();
							map.put("success", "true");
							map.put("info", ids.length + "条记录操作成功");
							}
					}else{
						map.clear();
						map.put("success", "false");
						map.put("info", "没有当前角色");
					}
			} catch (Exception e) {
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
		}else{
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setEntityClass() {
		this.entityClass = PePriCategory.class;

	}

	public void setServletPath() {
		this.servletPath = "/sso/admin/pePriCategoryView";

	}
	/**
	 * 修改列表页面
	 * dgh
	 */
	public Page list() {
		Page page = null;
		String wk_name="";
		ActionContext context = ActionContext.getContext();
		if (context.getParameters() != null) {
			Map params = this.getParamsMap();
			if (params != null) {
				/*工作室名称*/
				if (params.get("search__name") != null) {
					String[] wk_name_arry = (String[]) params.get("search__name");
					wk_name=wk_name_arry[0];
				}
			}
		}
		try {
			String sql="SELECT id,name,fk_pri_cat_id,namespace,action,method FROM PE_PRIORITY PP " +
					"WHERE PP.ID " ;
					if (method.equals("Category")){
						sql=sql+" NOT IN (";
					}else if(method.equals("myCategory")){
						sql=sql+" IN (";
					}
					 
					sql=sql+" SELECT DISTINCT PPY.ID AS Y0_ FROM PR_PRI_ROLE PP  " +
					"INNER JOIN PE_PRI_ROLE PPR ON PP.FK_ROLE_ID = PPR.ID " +
					"INNER JOIN PE_PRIORITY PPY ON PP.FK_PRIORITY_ID = PPY.ID WHERE 1=1" ;
					 
					if(null!=role && !"".equals(role)){
						 sql=sql+" AND PPR.ID = '"+role+"') ";
					 }
					if(null!=wk_name && !"".equals(wk_name)){
						 sql=sql+" AND PP.NAME LIKE '%"+wk_name+"%' ";
					 }
					 
			     sql=sql+" AND PP.ID IN (SELECT PC.PRIORITY_ID FROM PRIORITY_CONST PC WHERE PC.PRIORITY_ID=PP.ID ) ORDER BY PP.NAME DESC ";
			
			page = this.getGeneralService().getByPageSQL(sql,
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
	 
	return page;
	}
	/**
	 * 权限组设置权限列表查询
	 * @author huguolong
	 */	
//	public DetachedCriteria initDetachedCriteria() {
//		DetachedCriteria dc = null;
//		DetachedCriteria expcetdc = null;
//		//dgh
//		DetachedCriteria priec = null;
//		//
//		ActionContext.getContext().getSession().put("role", role);
//		expcetdc = DetachedCriteria.forClass(PrPriRole.class);
//		expcetdc.setProjection(Projections.distinct(Property.forName("pePriority.id")));
//		expcetdc.createCriteria("pePriRole", "pePriRole");
//		expcetdc.createCriteria("pePriority", "pePriority");
//		expcetdc.add(Restrictions.eq("pePriRole.id", role));
//		dc = DetachedCriteria.forClass(PePriority.class);
//		if (method.equals("Category")) {
//			dc.add(Subqueries.propertyNotIn("id", expcetdc));
//		}
//		if (method.equals("myCategory")) {
//			dc.add(Subqueries.propertyIn("id", expcetdc));
//		}
//		return dc;
//	}

}
