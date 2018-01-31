package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.RecommendSeries;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

/**
 * 推荐系列课程
 * @author zhang
 *
 */
public class RecommendSeriesAction extends MyBaseAction<RecommendSeries> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1262363652386734542L;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true);
		this.getGridConfig().setTitle(this.getText("系列课程管理"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name");
		ColumnConfig columnConfigDeploy = new ColumnConfig(this.getText("是否首页显示"), "enumConstByisShowIndex.name", false, true, true, "TextField", false, 100, "");
		String sql = "select b.id ,b.name from enum_const b where b.namespace='isShowIndex'";
		ColumnConfig columnConfigShowList = new ColumnConfig(this.getText("是否列表显示"), "enumConstByisShowList.name", false, true, true, "TextField", false, 100, "");
		this.getGridConfig().addColumn(columnConfigDeploy);
		String sqlList = "select b.id ,b.name from enum_const b where b.namespace='isShowList'";
		columnConfigShowList.setComboSQL(sqlList);
		this.getGridConfig().addColumn(columnConfigShowList);
		
		this.getGridConfig().addColumn(this.getText("是否置顶"), "isTop", false, false, true,  "textField");
		this.getGridConfig().addColumn(this.getText("创建日期"), "createDatetime", false, false, true, "TextField", true, 200);
		this.getGridConfig().addColumn(this.getText("系列图片"), "photoLink", false, true, false, "File", true, 200);
		this.getGridConfig().addColumn(this.getText("描述"), "descs", false, true, false, "TextEditor", true, 2000);
		this.getGridConfig().addRenderScript(this.getText("添加课程"),
				"{return '<a href=seriesCourseAdd.action?seriesid='+record.data['id']+'><font color=#0000ff ><u>添加课程</u></font></a>';}", "");
		this.getGridConfig().addRenderScript(this.getText("查看已选课程"),
				"{return '<a href=seriesCourse.action?seriesid='+record.data['id']+'><font color=#0000ff ><u>查看已选课程</u></font></a>';}", "");
		this.getGridConfig().addMenuFunction(this.getText("设为首页显示"), "IsShow.true");
		this.getGridConfig().addMenuFunction(this.getText("取消首页显示"), "IsShow.false");
		this.getGridConfig().addMenuFunction(this.getText("设为列表显示"), "IsList.true");
		this.getGridConfig().addMenuFunction(this.getText("取消列表显示"), "IsList.false");
		//this.getGridConfig().addMenuFunction(this.getText("设置建议学习人群"), "suggestCrowd");
		this.getGridConfig().addMenuFunction(this.getText("设置建议学习人群"), "/entity/teaching/peBzzCoursePubManager_setSuggest.action", false, false);		
		this.getGridConfig().addMenuFunction(this.getText("删除建议学习人群"), "recommendSeries_removeSuggest.action", false, false);
		this.getGridConfig().addMenuFunction(this.getText("设为置顶显示"), "IsTop.true");
		this.getGridConfig().addMenuFunction(this.getText("取消置顶显示"), "IsTop.false");
		this.getGridConfig().addRenderScript(this.getText("查看详情"),
				"{return '<a href=\"/entity/basic/recommendSeries_courseInfo.action?id='+${value}+'\" >查看详情</a>';}", "id");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = RecommendSeries.class;
		
	}
	public void setBean(RecommendSeries instance) {
		super.superSetBean(instance);
	}

	public RecommendSeries getBean() {
		return super.superGetBean();
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/recommendSeries";
	}
	@SuppressWarnings("unchecked")
	public Page list(){
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT * FROM (SELECT S.ID AS ID ,S.NAME AS NAME,E.NAME AS ISSHOW ,E1.NAME AS ISLIST, CASE WHEN IS_TOP = '1' THEN '是' ELSE '否' END AS ISTOP, TO_CHAR(S.CREATEDATE,'yyyy-MM-dd hh24:mi') createdatetime,S.PHOTO_LINK,S.DESCS FROM RECOMMEND_SERIES S LEFT JOIN enum_const E ON S.ISSHOW = E.ID LEFT JOIN enum_const E1 ON S.ISLIST = E1.ID WHERE 1 = 1 ");
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
				if (name.equals("enumConstByisShowIndex.name")) {
					name = "isshow";
				}
				if (name.equals("enumConstByisShowList.name")) {
					name = "islist";
				}
				if (name.equals("name")) {
					name = "s.name";
				}
				sqlBuffer.append(" and " + name + " like '%" + value + "%'");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			sqlBuffer.append(") where 1 = 1 ");
			if (this.getSort() != null && !"id".equals(this.getSort()) && temp != null && !"id".equals(temp)) {
				if (temp.equals("enumConstByisShowIndex.name")) {
					temp = "isshow";
				}
				if (temp.equals("enumConstByisShowList.name")) {
					temp = "islist";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sqlBuffer.append(" order by " + temp + " desc ");
				} else {
					sqlBuffer.append(" order by " + temp + " asc ");
				}
			} else {
				sqlBuffer.append(" order by isTop desc, createdatetime desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 课程名称不能重复
	 */
	@SuppressWarnings("unchecked")
	public void checkBeforeAdd() throws EntityException {
		String sql = " select * from RECOMMEND_SERIES where name = '" + this.getBean().getName() + "'";
		List list = this.getGeneralService().getBySQL(sql);
		if (list != null && list.size() > 0) {
			throw new EntityException("系列名称已存在，请重新命名");
		}
		this.getBean().setCreateDate(new Date());
	}
	/**
	 * 设置首页显示
	 */
	@SuppressWarnings("unchecked")
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		if (this.getIds() != null && this.getIds().length() > 0) {
			try{
				EnumConst eshow = null;
				EnumConst enot = null;
				EnumConst elshow = null;
				EnumConst elnot = null;
				if(action.startsWith("IsShow.")){//是否在首页显示
					DetachedCriteria rodc = DetachedCriteria.forClass(EnumConst.class);
					rodc.add(Restrictions.eq("namespace", "isShowIndex"));
					
					List<EnumConst> enumlist = this.getGeneralService().getList(rodc);
					for(int i = 0; i < enumlist.size(); i++){
						if("1".equals(enumlist.get(i).getCode())){
							eshow = enumlist.get(i);
						}else{
							enot = enumlist.get(i);
						}
					}
				}
				if(action.startsWith("IsList.")){//是否在列表显示
					DetachedCriteria rldc = DetachedCriteria.forClass(EnumConst.class);
					rldc.add(Restrictions.eq("namespace", "isShowList"));
					List<EnumConst> showlist = this.getGeneralService().getList(rldc);
					
					for(int i = 0; i < showlist.size(); i++){
						if("1".equals(showlist.get(i).getCode())){
							elshow = showlist.get(i);
						}else{
							elnot = showlist.get(i);
						}
					}
				}
				if(action.equals("suggestCrowd")){
					
				}
				
				String[] ids = getIds().split(",");
				for(int i = 0; i < ids.length; i++){
					RecommendSeries series = this.getGeneralService().getById(ids[i]);
					if (action.equals("IsShow.true")){
						series.setEnumConstByisShowIndex(eshow);
					}else if(action.equals("IsShow.false")){
						series.setEnumConstByisShowIndex(enot);
					}else if(action.equals("IsList.true")){
						series.setEnumConstByisShowList(elshow);
					}else if(action.equals("IsList.false")){
						series.setEnumConstByisShowList(elnot);
					}else if(action.equals("IsTop.true")) {
						series.setIsTop("1");
					}else if(action.equals("IsTop.false")) {
						series.setIsTop("99999");
					}
					this.getGeneralService().update(series);
				}
				map.put("success", "true");
				map.put("info", "操作成功");
			}catch(Exception e){
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
			}
		}else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}
	@SuppressWarnings({ "unchecked"})
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria enterdc = DetachedCriteria.forClass(RecommendSeries.class);
		enterdc.createCriteria("enumConstByisShowIndex", "enumConstByisShowIndex");
		enterdc.createCriteria("enumConstByisShowList", "enumConstByisShowList");
		enterdc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(enterdc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
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
					String condition ="";
					for(int i =0; i<idList.size(); i++){
						condition +="'" + idList.get(i) +"',";
					}
					condition = "(" + condition.substring(0, condition.length()-1)+")";
					String sql = " select 1 from RECOMMEND_SERIES RS inner join enum_const e on e.id=RS.Isshow  and e.code='1' and e.namespace='isShowIndex' where  RS.id in "+condition+"";
					List list = this.getGeneralService().getBySQL(sql);
					if (list.size() > 0 && null!=list) {
						throw new EntityException("设置为首页的推荐课程不能删除");
					}else {
						String deleteSql = " delete from RECOMMEND_SERIES where  id in "+condition+"";
						int  sum = this.getGeneralService().executeBySQL(deleteSql);
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
	public String courseInfo(){
		String id = ServletActionContext.getRequest().getParameter("id");
		String sql=" SELECT S.NAME AS NAME, S.DESCS, E.NAME AS ISSHOW,S.PHOTO_LINK ,listagg(ec.name,'，')WITHIN GROUP (ORDER BY ec.NAME) FROM RECOMMEND_SERIES S LEFT JOIN enum_const E ON S.ISSHOW = E.ID    LEFT JOIN PE_BZZ_TCH_COURSE_SUGGEST t ON  s.id =t.fk_course_id  " +
				" LEFT JOIN ENUM_CONST ec ON t.fk_enum_const_id =ec.id  WHERE S.ID='"+id+"' group by S.NAME , S.DESCS, E.NAME , S.PHOTO_LINK ";
		List list=null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		}
		if(list!=null && list.size()>0){
		ServletActionContext.getRequest().setAttribute("list", list);	
		}
		return "courseInfo";
		
	}
	public String  removeSuggest() {
		try {
			if (this.getIds() != null && this.getIds().length() > 0) {
				String sql = "";
				String[] ids = getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					sql = "DELETE FROM PE_BZZ_TCH_COURSE_SUGGEST WHERE FK_COURSE_ID = '" + ids[i] + "' AND UPPER(TABLE_NAME) = 'PE_BZZ_TCH_COURSE'";
					this.getGeneralService().executeBySQL(sql);
				}
				StringBuffer params_ids = new StringBuffer();
				for (int i = 0; i < ids.length; i++) {
					params_ids.append("'" + ids[i] + "',");
				}
				params_ids.append("''");
				/* 更新课程的设置学习人群状态 */
				//sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_SUGGESTSET = 'FlagSuggestSet0' WHERE ID IN (" + params_ids.toString() + ")";
				//int result = this.getGeneralService().executeBySQL(sql);
				
					this.setMsg("删除成功");
				
			} else {
				this.setMsg("删除失败");
			}
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("删除失败");
		}
		this.setTogo("back");
		return "lee";
		
	}
	
	@Override
	public Map add() {
		this.getBean().setIsTop("99999");
		return super.add();
	}

}
