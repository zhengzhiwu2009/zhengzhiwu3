package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeResource;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class TagManagerAction extends MyBaseAction<EnumConst>  {

	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		boolean canAdd = false;
		boolean canUpdate = false;
		boolean canDelete = false;
		/* 添加按钮 */
		if (capabilitySet.contains(this.servletPath + "_add.action")) {
			canAdd = true;
		}
		/* 删除按钮 */
		if (capabilitySet.contains(this.servletPath + "_delete.action")) {
			canDelete = true;
		}
		/* 修改按钮 */
		if (capabilitySet.contains(this.servletPath + "_update.action")) {
			canDelete = true;
		}
		this.getGridConfig().setCapability(true, true, true);

		this.getGridConfig().setTitle("资料标签管理");
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("标签名称"), "name");
		this.getGridConfig().addColumn(this.getText("CODE"), "code", false, false, false, "TextField", false, 200, "");
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = EnumConst.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/tagManager";
	}
	
	public void setBean(EnumConst instance) {
		super.superSetBean(instance);
	}
	
	public EnumConst getBean(){
		return super.superGetBean();
	}
	
	/**
	 * 资料标签管理列表
	 * 
	 * @author lwq
	 * @return
	 */
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("SELECT * FROM (SELECT EC.ID, EC.NAME as name , EC.CODE as code FROM ENUM_CONST EC WHERE EC.NAMESPACE = 'FlagResourceTag')");
			sqlBuffer.append(" WHERE 1 = 1  ");
			/* 拼接查询条件 */
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}
				if (name.equals("name")) {
					name = "name";
				}
				sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");	
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("name")) {
					temp = "name";
				}
				if (temp.equals("id")) {
					temp = "code";
				}
				
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {					
					sqlBuffer.append(" order by " + temp + " desc ");
					
				}else{
					sqlBuffer.append(" order by " + temp + " asc ");
				}
				
			} else {
				sqlBuffer.append(" order by code");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	
	public Map add() {
		Map map = new HashMap();

		this.superSetBean((EnumConst) setSubIds(this.getBean()));

		EnumConst ec = this.getEnumConstService().getByNamespaceName("FlagResourceTag", this.getBean().getName());
		
		if(ec != null) {
			map.put("success", "false");
			map.put("info", "标签已经存在,请重新添加");
			return map;
		}
		
		String sql = "SELECT MAX(CODE) FROM ENUM_CONST WHERE NAMESPACE = 'FlagResourceTag'";
		String code = "";
		try {
			code = (String) this.getGeneralService().getBySQL(sql).get(0);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		
		this.getBean().setCode((Integer.parseInt(code) + 1) + "");
		this.getBean().setNamespace("FlagResourceTag");
		this.getBean().setCreateDate(new Date());
		this.getBean().setNote("资料库资料标签");
		
		try {
			this.getGeneralService().save(this.getBean());
			map.put("success", "true");
			map.put("info", "一门课程添加成功");

		} catch (Exception e) {
			return this.checkAlternateKey(e, "添加");
		}
		return map;
	}
	
	/**
	 * 加载标签信息
	 * 
	 * @author Lee
	 * @return
	 */
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(dc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			String id = this.getBean().getId();

			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig()));
				Container o = new Container();
				o.setBean(page.getItems().get(0));
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount", 1);
				map.put("models", list);
			}
			this.setJsonString(JsonUtil.toJSONString(map));
			JsonUtil.setDateformat("yyyy-MM-dd");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}
	
	
	
	/**
	 * 重写更新方法
	 * 2015-12-31 lwq
	 */
	@Override
	public Map update() {
		checkBeforeUpdate();
		return super.update();
	}

	/**
	 * 重写删除方法
	 * 2015-12-31 lwq
	 */
	@Override
	public Map delete() {
		checkBeforeDelete();
		return super.delete();
	}
	
	/**
	 * 删除标签时对资料中标签进行联调
	 * 2015-12-31 lwq
	 */
	public void checkBeforeDelete() {
		String str = getIds();
		String[] ids = str.split(",");
		for(int i = 0; i < ids.length; i++) {
			String hql = "from PeResource pr where pr.resourceTagIds like '%" + ids[i] + "%'";
			try {
				EnumConst ec = this.getGeneralService().getById(ids[i]);
				List<PeResource> list = this.getGeneralService().getByHQL(hql);
				for(PeResource pr : list) {
					if(pr.getResourceTagIds().indexOf(ids[i] + ",") >= 0) {
						pr.setResourceTagIds(pr.getResourceTagIds().replace(ids[i] + ",", ""));
						pr.setResourceTagNames(pr.getResourceTagNames().replaceAll(" ", "").replace(ec.getName() + ",", ""));
					} else {
						pr.setResourceTagIds(pr.getResourceTagIds().replace(ids[i], ""));
						pr.setResourceTagNames(pr.getResourceTagNames().replaceAll(" ", "").replace(ec.getName(), ""));
					}
					String sql = "update pe_resource pr set pr.RESOURCETAGIDS = '" + pr.getResourceTagIds() + "', pr.RESOURCETAGS = '" + pr.getResourceTagNames() + "' where pr.id = '" + pr.getId() + "'";
					this.getGeneralService().executeBySQL(sql);
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void checkBeforeUpdate() {
		String id = this.getBean().getId();
		String hql = "from PeResource pr where pr.resourceTagIds like '%" + id + "%'";
		try {
			EnumConst ec = this.getGeneralService().getById(id);
			List<PeResource> list = this.getGeneralService().getByHQL(hql);
			for(PeResource pr : list) {
				pr.setResourceTagNames(pr.getResourceTagNames().replaceAll(" ", "").replace(ec.getName(), this.getBean().getName()));
				String sql = "update pe_resource pr set pr.RESOURCETAGS = '" + pr.getResourceTagNames() + "' where pr.id = '" + pr.getId() + "'";
				this.getGeneralService().executeBySQL(sql);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}


}
