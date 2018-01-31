package com.whaty.platform.sso.web.action.admin;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PePriCategory;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PePriEnumViewAction extends MyBaseAction {

	private String method;
	private String priority;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 *所在工作室加载列表
	 *@author dinggh
	 */
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("权限管理-添加所属工作室"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("工作室名称"), "name");
		this.getGridConfig().addMenuScript(this.getText("test.back"),"{window.history.back();}");
		if (method.equals("addenum")) {
			this.getGridConfig().addMenuFunction(this.getText("设置工作室"),"addenum_"+this.getPriority());
		}
		if (method.equals("cancelenum")) {
			this.getGridConfig().addMenuFunction(this.getText("取消工作室"),"cancelenum_"+this.getPriority());
		}
	}
	public Map updateColumn() {	
		Map map = new HashMap();
		try {
			String action = this.getColumn();
			String ap[] = action.split("_"); 
			if(action.indexOf("addenum")>=0){
				String msg = addPpriorityConst(ap[1]);
				map.clear();
				map.put("success", "true");
				map.put("info", msg);
				return map;
			}else if(action.indexOf("cancelenum")>=0){
				String msg = cancelPpriorityConst(ap[1]);
				map.clear();
				map.put("success", "true");
				map.put("info", msg);
				return map;
			}
			map.clear();
			map.put("success", "false");
			map.put("info", "数据有问题");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
		 
	}
/**
 * 保存选择的工作室(取消)
 * dinggh
 * @return
 */
public String cancelPpriorityConst(String pri_id){
	 try {
		 if(this.getIds() != null && this.getIds().length() > 0) {
			    StringBuffer sb = new StringBuffer();
			    String[] ids = getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					sb=new StringBuffer();
					sb.append("DELETE PRIORITY_CONST WHERE ENUM_CONST_ID='");
					sb.append(ids[i]+"'");
					sb.append(" AND PRIORITY_ID='"+pri_id+"'");
					this.getGeneralService().executeBySQL(sb.toString());
				}
		 }
	} catch (Exception e) {
		e.printStackTrace();
		return "操作失败";
	}
	return "操作成功";
}
/**
 * 保存选择的工作室(添加)
 * dinggh
 * @return
 */
public String addPpriorityConst(String pri_id){
	 try {
		 if(this.getIds() != null && this.getIds().length() > 0) {
			    StringBuffer sb = new StringBuffer();
			    String[] ids = getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					sb=new StringBuffer();
					sb.append("INSERT INTO PRIORITY_CONST (ID,PRIORITY_ID,ENUM_CONST_ID) VALUES(");
					sb.append("SEQ_PRIORITY_CONST.Nextval");
					sb.append(",'"+pri_id+"'");
					sb.append(",'"+ids[i]+"')");
//					System.out.println(sb);
					this.getGeneralService().executeBySQL(sb.toString());
				}
		 }
	} catch (Exception e) {
		e.printStackTrace();
		return "操作失败";
	}
	return "操作成功";
}
	 
 
	public void setEntityClass() {
		this.entityClass = PePriCategory.class;

	}

	public void setServletPath() {
		this.servletPath = "/sso/admin/pePriEnumView";

	}
	/**
	 * 所在工作室列表页面
	 * author dinggh
	 */
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
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
			String sql=null;
			if(this.getMethod().equals("addenum")){
			    sql="SELECT EC.ID,EC.NAME,EC.CODE,EC.NAMESPACE  FROM ENUM_CONST EC  " +
				" WHERE EC.ID NOT IN(SELECT PC.ENUM_CONST_ID FROM PRIORITY_CONST PC WHERE PC.ENUM_CONST_ID=EC.ID AND PC.PRIORITY_ID='"+priority+"') " +
				" AND EC.NAMESPACE='FlagRoleType' " ;
				if(null!=wk_name && !"".equals(wk_name)){
					sql=sql+" AND EC.NAME LIKE '%"+wk_name+"%'";
				}
				sql=sql+" ORDER BY EC.NAME";
			}else if(this.getMethod().equals("cancelenum")){
				sql="SELECT EC.ID,EC.NAME,EC.CODE,EC.NAMESPACE  FROM ENUM_CONST EC  " +
				" WHERE EC.ID  IN(SELECT PC.ENUM_CONST_ID FROM PRIORITY_CONST PC WHERE PC.ENUM_CONST_ID=EC.ID  AND PC.PRIORITY_ID='"+priority+"') " +
				" AND EC.NAMESPACE='FlagRoleType' ";
				if(null!=wk_name && !"".equals(wk_name)){
					sql=sql+" AND EC.NAME LIKE '%"+wk_name+"%'";
				}
				sql=sql+" ORDER BY EC.NAME";
			}
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
 
}
