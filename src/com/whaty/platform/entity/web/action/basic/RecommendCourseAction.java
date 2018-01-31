package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.RecommendCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 单一推荐课程
 * 
 * @author zhang
 * 
 */
public class RecommendCourseAction extends MyBaseAction<RecommendCourse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8815974408789182749L;

	@Override
	public void initGrid() {

		this.getGridConfig().setCapability(false, true, false);
		this.getGridConfig().setTitle(this.getText("单一推荐课程"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("课程编号"), "code");
		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true, false, true, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("学时"), "time", false, false, true, "TextField", false, 200, "");
		ColumnConfig columnConfigType = new ColumnConfig(this.getText("课程性质"), "enumConstByFlagCourseType.name", true, false, true, "TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseType'";
		columnConfigType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnConfigType);
		this.getGridConfig().addColumn(this.getText("主讲人"), "TEACHER", false, false, true, "TextField", false, 200, "");
		ColumnConfig columnConfigCategory = new ColumnConfig(this.getText("业务分类"), "enumConstByFlagCourseCategory.name", true, false, false, "TextField", false, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseCategory'";
		columnConfigCategory.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigCategory);

		ColumnConfig columnCourseItemType = new ColumnConfig(this.getText("大纲分类"), "enumConstByFlagCourseItemType.name", true, false, false, "TextField", false, 100, "");
		String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagCourseItemType'";
		columnCourseItemType.setComboSQL(sql7);
		this.getGridConfig().addColumn(columnCourseItemType);

		ColumnConfig columnContentProperty = new ColumnConfig(this.getText("内容属性分类"), "enumConstByFlagContentProperty.name", true, false, false, "TextField", false, 100, "");
		String sql9 = "select a.id ,a.name from enum_const a where a.namespace='FlagContentProperty'";
		columnContentProperty.setComboSQL(sql9);
		this.getGridConfig().addColumn(columnContentProperty);

		ColumnConfig isshow = new ColumnConfig(this.getText("是否首页显示"), "enumConstByisShowIndex.name", false, false, true, "TextField", false, 100, "");
		this.getGridConfig().addColumn(isshow);

		ColumnConfig isAreashow = new ColumnConfig(this.getText("是否课程区显示"), "enumConstByisAreaShowIndex.name", false, false, true, "TextField", false, 100, "");
		this.getGridConfig().addColumn(isAreashow);

		ColumnConfig ccFlagSuggest = new ColumnConfig(this.getText("建议学习人群"), "enumConstByFlagSuggest.name", true, false, false, "TextField", false, 100, "");
		String sqlFlagSuggest = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
		ccFlagSuggest.setComboSQL(sqlFlagSuggest);
		this.getGridConfig().addColumn(ccFlagSuggest);

		this.getGridConfig().addMenuScript(this.getText("添加课程"), "{window.location = '/entity/basic/recommendCourseAdd.action';}");
		this.getGridConfig().addMenuFunction(this.getText("设为首页显示"), "IsShow.true");
		this.getGridConfig().addMenuFunction(this.getText("取消首页显示"), "IsShow.false");
		this.getGridConfig().addMenuFunction(this.getText("设为课程区显示"), "IsAreaShow.true");
		this.getGridConfig().addMenuFunction(this.getText("取消课程区显示"), "IsAreaShow.false");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = RecommendCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/recommendCourse";

	}

	@SuppressWarnings("unchecked")
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT * FROM (");
		sqlBuffer.append("SELECT REM.ID," + " COURSE.CODE," + " COURSE.NAME," + " COURSE.TIME," + " ETYPE.NAME AS FLAG_COURSETYPE," + " COURSE.TEACHER,"
				+ " ECOURSECATEGORY.NAME AS FLAG_COURSECATEGORY," + " EITEM.NAME AS FLAG_COURSE_ITEM_TYPE," + " EPROPERTY.NAME AS FLAG_CONTENT_PROPERTY,"
				+ " ESHOW.NAME AS ISSHOW, " + " EASHOW.NAME AS ISAREASHOW, " + " SUGG.SUGGNAME AS FLAG_SUGGEST " + " FROM  RECOMMEND_COURSE REM "
				+ " INNER JOIN PE_BZZ_TCH_COURSE COURSE ON REM.PK_COURSE_ID  = COURSE.ID"
				+ " LEFT JOIN (SELECT  A.FK_COURSE_ID, WMSYS.WM_CONCAT(E.NAME) AS SUGGNAME FROM PE_BZZ_TCH_COURSE_SUGGEST A "
				+ " LEFT JOIN ENUM_CONST E ON E.ID = A.FK_ENUM_CONST_ID GROUP BY A.FK_COURSE_ID ) SUGG ON  SUGG.FK_COURSE_ID = COURSE.ID"
				+ " LEFT JOIN ENUM_CONST ETYPE ON ETYPE.ID = COURSE.FLAG_COURSETYPE " + " LEFT JOIN ENUM_CONST ECOURSECATEGORY ON ECOURSECATEGORY.ID = COURSE.FLAG_COURSECATEGORY "
				+ " LEFT JOIN ENUM_CONST EITEM ON EITEM.ID = COURSE.FLAG_COURSE_ITEM_TYPE " + " LEFT JOIN ENUM_CONST EPROPERTY ON EPROPERTY.ID = COURSE.FLAG_CONTENT_PROPERTY "
				+ " LEFT JOIN ENUM_CONST ESHOW ON ESHOW.ID = REM.ISSHOW " + " LEFT JOIN ENUM_CONST EASHOW ON EASHOW.ID = REM.ISAREASHOW " + " WHERE PK_SERIES_ID IS NULL ");
		sqlBuffer.append(") WHERE 1= 1 ");
		try {
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
				if (name.equals("enumConstByFlagCourseType.name")) {
					name = "FLAG_COURSETYPE";
				}
				if (name.equals("enumConstByFlagCourseCategory.name")) {
					name = "FLAG_COURSECATEGORY";
				}
				if (name.equals("enumConstByFlagCourseItemType.name")) {
					name = "FLAG_COURSE_ITEM_TYPE";
				}
				if (name.equals("enumConstByFlagContentProperty.name")) {
					name = "FLAG_CONTENT_PROPERTY";
				}
				if (name.equals("enumConstByFlagSuggest.name")) {
					name = "FLAG_SUGGEST";
				}
				sqlBuffer.append(" and " + name + " like '%" + value + "%'");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("enumConstByFlagCourseType.name")) {
					temp = "FLAG_COURSETYPE";
				}
				if (temp.equals("enumConstByFlagSuggest.name")) {
					temp = "FLAG_SUGGEST";
				}
				if (temp.equals("enumConstByisShowIndex.name")) {
					temp = "ISSHOW";
				}
				if (temp.equals("enumConstByisAreaShowIndex.name")) {
					temp = "ISAREASHOW";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {

					sqlBuffer.append(" order by " + temp + " desc ");

				} else {

					sqlBuffer.append(" order by " + temp + " asc ");

				}
			} else {
				sqlBuffer.append(" order by id desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	@SuppressWarnings("unchecked")
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		if (this.getIds() != null && this.getIds().length() > 0) {
			try {
				DetachedCriteria rodc = DetachedCriteria.forClass(EnumConst.class);
				rodc.add(Restrictions.eq("namespace", "isShowIndex"));
				List<EnumConst> enumlist = this.getGeneralService().getList(rodc);
				EnumConst eshow = null;
				EnumConst enot = null;
				for (int i = 0; i < enumlist.size(); i++) {
					if ("1".equals(enumlist.get(i).getCode())) {
						eshow = enumlist.get(i);
					} else {
						enot = enumlist.get(i);
					}
				}
				String[] ids = getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					RecommendCourse course = this.getGeneralService().getById(ids[i]);
					if (action.equals("IsShow.true")) {
						course.setEnumConstByisShowIndex(eshow);
						course.setShowDate(new Date());
					} else if (action.equals("IsShow.false")) {
						course.setEnumConstByisShowIndex(enot);
					}
					if (action.equals("IsAreaShow.true")) {
						course.setEnumConstByisAreaShowIndex(eshow);
						course.setShowDate(new Date());
					} else if (action.equals("IsAreaShow.false")) {
						course.setEnumConstByisAreaShowIndex(enot);
					}
					this.getGeneralService().update(course);
				}
				map.put("success", "true");
				map.put("info", "操作成功");
			} catch (Exception e) {
				e.printStackTrace();
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
		return map;
	}

	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				try {
					String condition = "";
					for (int i = 0; i < idList.size(); i++) {
						condition += "'" + idList.get(i) + "',";
					}
					condition = "(" + condition.substring(0, condition.length() - 1) + ")";
					String sql = " select 1 from RECOMMEND_COURSE RC inner join enum_const e on e.id=RC.Isshow  and e.code='1'  and e.namespace='isShowIndex'  where  RC.id in "
							+ condition + "";
					List list = this.getGeneralService().getBySQL(sql);
					if (list.size() > 0 && null != list) {
						throw new EntityException("设置为首页的推荐课程不能删除");
					} else {
						String deleteSql = " delete from RECOMMEND_COURSE where  id in " + condition + "";
						int sum = this.getGeneralService().executeBySQL(deleteSql);
						map.put("success", "true");
						map.put("info", "共有" + sum + "门课程删除成功");
						if (0 == sum) {
							map.clear();
							map.put("success", "false");
							map.put("info", "删除失败");
						}

					}

				} catch (EntityException e) {
					map.put("success", "false");
					map.put("info", e.getMessage() + "，无法删除！");
					return map;
				}

			}
		}
		return map;
	}
}
