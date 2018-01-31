package com.whaty.platform.entity.web.action.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzExamBatch;
import com.whaty.platform.entity.bean.PeBzzExamBatchSite;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzExamSite;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;
import com.whaty.util.RandomString;

public class PeBzzExamBatchSiteAction extends MyBaseAction<PeBzzExamBatchSite>{

	@Override
	public void initGrid() {
		//this.getGridConfig().setCapability(true, true, true);
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getUserLoginType().equals("3")){
			this.getGridConfig().setCapability(false, true, false);
			
		}else{
			this.getGridConfig().setCapability(false,false,false);
		}
		this.getGridConfig().setTitle("批次-考点关系");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("所在考试批次"),"examBatchName",true,false,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("考点名称"),"examSiteName",true,false,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("考点所在省"),"examSiteSheng",true,false,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("考点所在市"),"examSiteShi",true,false,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("考点容量"),"examSiteCapacity",false, true, true,Const.number_for_extjs);
		this.getGridConfig().addColumn(this.getText("已报人数"), "stunum", false,false, true, "");
		this.getGridConfig().addColumn(this.getText("考点详细地址"),"ExamSiteAddress",false,false,true,"TextField",false,200);
		this.getGridConfig().addColumn(this.getText("考点备注"),"examSiteNote",false,false,true,"TextField",false,100);
		//this.setBackQueryString("search__peBzzExamBatch_name="+this.getText("当前批次"));

	}
	public String abstractList() {
		ActionContext context = ActionContext.getContext();
		Map map1 = context.getParameters();
		Iterator it = map1.entrySet().iterator();
		int k = 0;
		int n = 0;
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		String tempsql= 
			"select pp.id as id,\n" +
			"       pp.examBatchName,\n" + 
			"       pp.examSiteName,\n" + 
			"       pp.examSiteSheng,\n" + 
			"       pp.examSiteShi,\n" + 
			"       pp.examSiteCapacity,\n" + 
			"       pp.stunum, \n" + 
			"       pp.examSiteAddress,\n" + 
			"       pp.examSiteNote\n" + 
			"  from (select a.id as id,\n" + 
			"               b.name as examBatchName,\n" + 
			"               c.name as examSiteName,\n" + 
			"               c.sheng as examSiteSheng,\n" + 
			"               c.shi as examSiteShi,\n" + 
			"               c.capacity as examSiteCapacity,\n" + 
			"               c.address as examSiteAddress,\n" + 
			"               c.note as examSiteNote,\n" + 
			"               count(d.id) as stunum\n" + 
			"          from pe_bzz_exambatch_site a,\n" + 
			"               pe_bzz_exambatch      b,\n" + 
			"               pe_bzz_examsite       c,\n" + 
			"               pe_bzz_examscore      d\n" + 
			"         where a.exambatch_id = b.id\n" + 
			"           and a.site_id = c.id\n" + 
			"           and d.site_id(+) = a.id\n" + 
			"         group by a.id,\n" + 
			"                  b.name,\n" + 
			"                  c.name,\n" + 
			"                  c.sheng,\n" + 
			"                  c.shi,\n" + 
			"                  c.capacity,\n" + 
			"                  c.address,\n" + 
			"                  c.note) pp\n" + 
			" where 1 = 1";

		StringBuffer buffer = new StringBuffer(tempsql);
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			String name = entry.getKey().toString();
			String sq ="";
			if (name.startsWith("search__")) {
				n++;
				String value = ((String[]) entry.getValue())[0];
				
				if (value == "" || "".equals(value)) {
					k++;
				} else {
					name = name.substring(8);
					if(name.contains("examBatchName")){
						sq = "like '%" + value + "%'";
					}else if(name.contains("examSiteName")){
						sq = "like '%" + value + "%'";
					}
					else if(name.contains("examSiteSheng")){
						sq = "like '%" + value + "%'";
					}
					else if(name.contains("examSiteShi")){
						sq = "like '%" + value + "%'";
					}
					buffer.append("and " + name + " "+sq);
				}
			}
		}
		String js = null;
		if (k - n == 0 ? true : false) {
			js = super.abstractList();
		} else {
			initGrid();
			Page page = null;
			String sql = buffer.toString();
			try {
				page = this.getGeneralService().getByPageSQL(sql,
						Integer.parseInt(this.getLimit()),
						Integer.parseInt(this.getStart()));
				List jsonObjects = JsonUtil.ArrayToJsonObjects(page.getItems(),
						this.getGridConfig().getListColumnConfig());
				Map map = new HashMap();
				if (page != null) {
					map.put("totalCount", page.getTotalCount());
					map.put("models", jsonObjects);
				}
				this.setJsonString(JsonUtil.toJSONString(map));
				JsonUtil.setDateformat("yyyy-MM-dd");
				js = this.json();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		return js;
	}

	public Page list() {
		Page page = null;

		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		String sql = 
			"select pp.id as id,\n" +
			"       pp.examBatchName,\n" + 
			"       pp.examSiteName,\n" + 
			"       pp.examSiteSheng,\n" + 
			"       pp.examSiteShi,\n" + 
			"       pp.examSiteCapacity,\n" + 
			"       pp.stunum, \n" + 
			"       pp.examSiteAddress,\n" + 
			"       pp.examSiteNote\n" + 
			"  from (select a.id as id,\n" + 
			"               b.name as examBatchName,\n" + 
			"               c.name as examSiteName,\n" + 
			"               c.sheng as examSiteSheng,\n" + 
			"               c.shi as examSiteShi,\n" + 
			"               c.capacity as examSiteCapacity,\n" + 
			"               c.address as examSiteAddress,\n" + 
			"               c.note as examSiteNote,\n" + 
			"               count(d.id) as stunum\n" + 
			"          from pe_bzz_exambatch_site a,\n" + 
			"               pe_bzz_exambatch      b,\n" + 
			"               pe_bzz_examsite       c,\n" + 
			"               pe_bzz_examscore      d\n" + 
			"         where a.exambatch_id = b.id\n" + 
			"           and a.site_id = c.id\n" + 
			"           and d.site_id(+) = a.id\n" + 
			"         group by a.id,\n" + 
			"                  b.name,\n" + 
			"                  c.name,\n" + 
			"                  c.sheng,\n" + 
			"                  c.shi,\n" + 
			"                  c.capacity,\n" + 
			"                  c.address,\n" + 
			"                  c.note) pp\n" + 
			" where 1 = 1";
		try {
			StringBuffer sql_temp = new StringBuffer(sql);
			this.setSqlCondition(sql_temp);
			page = this.getGeneralService().getByPageSQL(sql_temp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	public void setEntityClass() {
		this.entityClass = PeBzzExamBatchSite.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/exam/peBzzExamBatchSite";
	}

	public void setBean(PeBzzExamBatchSite instance) {
		super.superSetBean(instance);
	}

	public PeBzzExamBatchSite getBean() {
		return super.superGetBean();
	}
	
	//MenuFunction  方法
	
	@Override

	//add校验
	
	public void checkBeforeAdd() throws EntityException {
		
	}
	
	//update校验
	
	public void checkBeforeUpdate() throws EntityException {
		
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamBatchSite.class);
		dc.createCriteria("peBzzExamBatch", "peBzzExamBatch", DetachedCriteria.INNER_JOIN);
		dc.createCriteria("peBzzExamSite", "peBzzExamSite", DetachedCriteria.INNER_JOIN);
		return dc;
	}
	
	//懒加载

}
