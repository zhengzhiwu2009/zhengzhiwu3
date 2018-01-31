package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 在线直播设置建议学习人群action
 * 
 * @version 创建时间：2014年6月20日
 * @author Lee
 * @return
 * @throws PlatformException
 *             类说明
 */
public class SacLiveSuggAction extends MyBaseAction {

	/* 存储表名 用于区分在线直播表还是课程表 */
	private String tableName;

	/**
	 * 初始化列表
	 * 
	 * @author Lee
	 * @return
	 */
	@Override
	public void initGrid() {
		if (null == ServletActionContext.getRequest().getSession().getAttribute("tableName"))
			ServletActionContext.getRequest().getSession().setAttribute("tableName", this.getTableName());
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("建议学习人群"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("建议学习人群"), "name");
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		this.getGridConfig().addMenuFunction(this.getText("设置建议学习人群"), "setSuggList");
	}

	/**
	 * 重写框架方法：更新字段
	 * 
	 * @author Lee
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		try {
			if (ServletActionContext.getRequest().getSession().getAttribute("tableName") != null) {
				tableName = ServletActionContext.getRequest().getSession().getAttribute("tableName").toString().toUpperCase();
			} else {
				map.clear();
				map.put("success", "false");
				map.put("info", "程序错误");
				return map;
			}
			List<String> sqlList = new ArrayList<String>();
			if (this.getIds() != null && this.getIds().length() > 0) {
				/* ids：建议学习人群码值 */
				String[] ids = getIds().split(",");
				/* 根据表名区分课程IDS：课程ids */
				String[] courseIds = (String[]) ServletActionContext.getRequest().getSession().getAttribute("slaids");
				if ("PE_BZZ_TCH_COURSE".equalsIgnoreCase(tableName))
					courseIds = (String[]) ServletActionContext.getRequest().getSession().getAttribute("pbzpmpids");

				String delSql = "";
				/* 删除原来设置过的关系数据 */
				if (ids.length > 0 && courseIds.length > 0) {
					for (int i = 0; i < courseIds.length; i++) {
						delSql = "DELETE FROM PE_BZZ_TCH_COURSE_SUGGEST WHERE FK_COURSE_ID = '" + courseIds[i] + "' AND UPPER(TABLE_NAME) = '" + tableName + "'";
						sqlList.add(delSql);
						// this.getGeneralService().executeBySQL(delSql);
					}
				}
				String sql = "";
				/* 保存新关系数据 */
				for (int i = 0; i < ids.length; i++) {
					if (action.equals("setSuggList")) {
						for (int j = 0; j < courseIds.length; j++) {
							sql = "INSERT INTO PE_BZZ_TCH_COURSE_SUGGEST (ID, FK_COURSE_ID, FK_ENUM_CONST_ID, TABLE_NAME) VALUES (SEQ_PE_BZZ_TCH_COURSE_SUGGEST.NEXTVAL,'" + courseIds[j] + "','"
									+ ids[i] + "','" + tableName + "')";
							sqlList.add(sql);
							// this.getGeneralService().executeBySQL(sql);
						}
					}
				}
				StringBuffer params_ids = new StringBuffer();
				for (int i = 0; i < courseIds.length; i++) {
					params_ids.append("'" + courseIds[i] + "',");
				}
				params_ids.append("''");
				/* 更新课程的设置学习人群状态 */
				sql = "UPDATE " + tableName + " SET FLAG_SUGGESTSET = 'FlagSuggestSet1' WHERE ID IN (" + params_ids.toString() + ")";
				sqlList.add(sql);
				// int result = this.getGeneralService().executeBySQL(sql);
				dbpool db = new dbpool();
				int result = db.executeUpdateBatch(sqlList);
				if (result > 0) {
					map.clear();
					map.put("success", "true");
					map.put("info", "操作成功");
				} else {
					map.clear();
					map.put("success", "false");
					map.put("info", "操作失败");
				}
			} else {
				map.clear();
				map.put("success", "false");
				map.put("info", "至少一条数据被选择");
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 页面列表
	 */
	public Page list() {
		Page page = null;
		try {
			StringBuffer sql = new StringBuffer("SELECT ID, NAME FROM ENUM_CONST WHERE NAMESPACE = 'FlagSuggest' ");
			this.setSqlCondition(sql);
			page = this.getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return page;
	}

	public void setEntityClass() {

	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/sacLiveSugg";
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
