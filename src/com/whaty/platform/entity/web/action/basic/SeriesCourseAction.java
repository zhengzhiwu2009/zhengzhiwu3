package com.whaty.platform.entity.web.action.basic;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.RecommendCourse;
import com.whaty.platform.entity.bean.RecommendSeries;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
/**
 * 查看推荐系列已选课程
 * @author zhang
 *
 */
public class SeriesCourseAction extends MyBaseAction<RecommendCourse>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6687032994814879422L;
	private String seriesid;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("已选课程"));
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
		
		ColumnConfig ccFlagSuggest = new ColumnConfig(this.getText("建议学习人群"), "enumConstByFlagSuggest.name", true, false, true, "TextField", false, 100, "");
		String sqlFlagSuggest = "select a.id ,a.name from enum_const a where a.namespace='FlagSuggest' and name not in ('面向所有人投票','面向学员投票','面向集体管理员投票')";
		ccFlagSuggest.setComboSQL(sqlFlagSuggest);
		this.getGridConfig().addColumn(ccFlagSuggest);
		this.getGridConfig().addMenuFunction(this.getText("取消推荐系列"), "SeriesDelete_" + this.getSeriesid());
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = RecommendCourse.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/seriesCourse";
	}
	@SuppressWarnings("unchecked")
	public Page list(){
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT * FROM (");
		sqlBuffer.append("SELECT COURSE.ID," +
				" COURSE.CODE," +
				" COURSE.NAME," +
				" COURSE.TIME," +
				" ETYPE.NAME AS FLAG_COURSETYPE," +
				" COURSE.TEACHER," +				
				" ECOURSECATEGORY.NAME AS FLAG_COURSECATEGORY," +
				" EITEM.NAME AS FLAG_COURSE_ITEM_TYPE," +
				" EPROPERTY.NAME AS FLAG_CONTENT_PROPERTY," +
				" SUGG.SUGGNAME AS FLAG_SUGGEST " +
				" FROM PE_BZZ_TCH_COURSE COURSE INNER JOIN RECOMMEND_COURSE REM ON COURSE.ID = REM.PK_COURSE_ID " +
				" LEFT JOIN (SELECT  A.FK_COURSE_ID, to_char(WMSYS.WM_CONCAT(E.NAME)) AS SUGGNAME FROM PE_BZZ_TCH_COURSE_SUGGEST A " +
				" LEFT JOIN ENUM_CONST E ON E.ID = A.FK_ENUM_CONST_ID GROUP BY A.FK_COURSE_ID ) SUGG ON  SUGG.FK_COURSE_ID = COURSE.ID" +
				" LEFT JOIN ENUM_CONST ETYPE ON ETYPE.ID = COURSE.FLAG_COURSETYPE " +
				" LEFT JOIN ENUM_CONST ECOURSECATEGORY ON ECOURSECATEGORY.ID = COURSE.FLAG_COURSECATEGORY " +
				" LEFT JOIN ENUM_CONST EITEM ON EITEM.ID = COURSE.FLAG_COURSE_ITEM_TYPE " +
				" LEFT JOIN ENUM_CONST EPROPERTY ON EPROPERTY.ID = COURSE.FLAG_CONTENT_PROPERTY " +
				" WHERE REM.PK_SERIES_ID ='" + this.getSeriesid() + "' AND (COURSE.FLAG_ISVALID = '2' AND COURSE.FLAG_OFFLINE = '22')");
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
	@SuppressWarnings({ "unchecked" })
	public Map updateColumn() {
		Map map = new HashMap();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String action = this.getColumn();
		String ap[] = action.split("_");
		if (this.getIds() != null && this.getIds().length() > 0) {
			try{
				DetachedCriteria rodc = DetachedCriteria.forClass(RecommendSeries.class);
				rodc.add(Restrictions.eq("id", ap[1]));
				List<RecommendSeries> list = this.getGeneralService().getList(rodc);
				RecommendSeries series = list.get(0);
				
				rodc = DetachedCriteria.forClass(EnumConst.class);
				rodc.add(Restrictions.eq("namespace", "isShowIndex"));
				rodc.add(Restrictions.eq("code", "1"));
				List<EnumConst> enumlist = this.getGeneralService().getList(rodc);
				EnumConst econst = enumlist.get(0);			
	
				String[] ids = getIds().split(",");
				for(int i = 0 ; i < ids.length; i++){
					String sql="delete from Recommend_Course  e where e.pk_course_id='"+ids[i]+"'";
					this.getGeneralService().executeBySQL(sql);
				}
				map.put("success", "true");
				map.put("info", "操作成功");
			}catch(Exception e){
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
	
	
	
	public String getSeriesid() {
		return seriesid;
	}

	public void setSeriesid(String seriesid) {
		this.seriesid = seriesid;
	}
	
}
