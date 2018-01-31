package com.whaty.platform.entity.web.action.recruit.baoming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeEnterpriseBacthService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeRecFinanceAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().setTitle("二级企业列表");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("企业名称"), "name");
		this.getGridConfig().addColumn(this.getText("企业编号"), "code", true,
				false, true, "");
		
		this.getGridConfig().addColumn(this.getText("上级企业"), "p_name", false,
				false, true, "");
		
		//this.getGridConfig().addColumn(this.getText("报名学期"), "batchname", false,
		//		false, true, "");

		this.getGridConfig().addColumn(this.getText("学员人数"), "num", false,
				false, true, "");
	/*	this
		.getGridConfig()
		.addRenderScript(
				this.getText("学员人数"),
				"{return '<a href=peDetail.action?id='+record.data['id']+'&type=enterprise><font color=#0000ff ><u>'+record.data['num']+'</u></font></a>';}",
				""); */
		
		this.getGridConfig().addColumn(this.getText("缴费人数"), "paynum", false,
				false, true, "");
		//新增学期信息
		ColumnConfig c_name2 = new ColumnConfig(this.getText("学期"), "peBzzBatch.name", true,true, false,"TextField",false,25,"");
		c_name2.setComboSQL("select b.id,b.name from pe_bzz_batch b");
		this.getGridConfig().addColumn(c_name2);
		this.getGridConfig().addColumn(this.getText("学期id号"), "select_batch_id", false, false, false, "TextField", true, 200, "");
		
		this
		.getGridConfig()
		.addRenderScript(
				this.getText("缴费信息"),
				"{return '<a href=prBzzFaxDetail.action?e_id='+record.data['id']+'&num='+record.data['num']+'&select_batch_id='+record.data['select_batch_id']+'>管理</a>';}",
				"");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeEnterprise.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/recruit/peRecFinance";
	}

	public PeEnterprise getBean() {
		// TODO Auto-generated method stub
		return (PeEnterprise) super.superGetBean();
	}

	public void setBean(PeEnterprise enterprise) {
		// TODO Auto-generated method stub
		super.superSetBean(enterprise);
	}
	
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria enterdc = DetachedCriteria
				.forClass(PeEnterprise.class);
		enterdc.createCriteria("peEnterprise", "peEnterprise");
		enterdc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(enterdc,
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
			String id = this.getBean().getId();
			
			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this
						.getGridConfig().getListColumnConfig()));
				Container o = new Container();
				o.setBean(page.getItems().get(0));
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount",1);
				map.put("models", list);
			}
			this.setJsonString(JsonUtil.toJSONString(map));
			JsonUtil.setDateformat("yyyy-MM-dd");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}
	

	public String abstractList() {
		super.saveDetachedCriteriaInSession(null);
//		ActionContext context = ActionContext.getContext();
//		Map map1 = context.getParameters();
		this.setCanNavigate(true);
		this.clearNavigatePath();
		
		Map map1 = this.getParamsMap();
		Iterator it = map1.entrySet().iterator();
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql_con = "";
		if (us.getUserLoginType().equals("2"))
			sql_con = " and pe.id='" + us.getPriEnterprises().get(0).getId()
					+ "'";
		//学期处理start
		String[] val = (String[])map1.get("search__peBzzBatch.name");
		String sql_start = "select rs.*,'batch_name','select_batch_id' from (";
		String sql_con1 = "";
		String sql_con2 = "";
		String sql_con3 = "";
		String sql_end = " ) rs where 1=1 ";
		if(val != null && val.length > 0 && val[0]!=null && !val[0].equals("")) {
			sql_con1=" and bb.name='"+val[0]+"' ";
			sql_con2 = " and b.name ='"+val[0]+"' ";
			sql_con3 = " and a.num > 0 ";
			sql_start = "select rs.* from (select rs.*,batch.name as batch_name,batch.id as select_batch_id from ( ";
			sql_end = " ) rs, pe_bzz_batch batch where batch.name='"+val[0]+"' ) rs where 1=1 ";
		}
		//学期处理end
		int k = 0;
		int n = 0;
		String tempsql =sql_start + " select * from ( select a.id as id, a.name  as name, a.code as code,a.p_name as p_name,a.num, nvl(b.paynum,0) as paynum from (" +
				"select p.id as id, p.name  as name, p.code as code,pp.name  as p_name,count(ps.id) as num\n"
				+ "  from pe_enterprise p  inner join pe_enterprise pp on p.fk_parent_id = pp.id\n"
//				+ "  left outer join (select br.fk_enterprise_id,br.id,bb.name as batchname from pe_bzz_recruit br,pe_bzz_batch bb where br.fk_batch_id=bb.id and bb.recruit_selected='1') ps on p.id = ps.fk_enterprise_id\n"
				+ "  left outer join (select br.fk_enterprise_id,br.id,bb.name as batchname from pe_bzz_recruit br,pe_bzz_batch bb where br.fk_batch_id=bb.id "+sql_con1+") ps on p.id = ps.fk_enterprise_id\n"
				+ "  where p.fk_parent_id is not null\n "
				+"   group by  p.id, p.name, p.code, pp.name )a," 
				+"(select p.id as id, p.name  as name, p.code as code,pp.name  as p_name,sum(ps.num) as paynum\n"
				+ "  from pe_enterprise p  inner join pe_enterprise pp on p.fk_parent_id = pp.id\n"
				+ "  left outer join (select ps.fk_enterprise_id,ps.num from pe_bzz_fax_info ps,enum_const e,pe_bzz_batch b "
//				+ "where e.id=ps.flag_fp_open_state and b.id=ps.fk_batch_id and b.recruit_selected='1' --and e.code='1' \n" +
				+ "where e.id=ps.flag_fp_open_state and b.id=ps.fk_batch_id "+sql_con2+" \n" +
						") ps on p.id = ps.fk_enterprise_id\n"
				+ "  where p.fk_parent_id is not null\n"
				+ "  group by  p.id, p.name, p.code, pp.name) b where a.id=b.id "+sql_con3+") p where 1=1 " + sql_end;
		StringBuffer buffer = new StringBuffer(tempsql);
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			String name = entry.getKey().toString();
			if (name.startsWith("search__")) {
				n++;
				if(name.endsWith("peBzzBatch.name")) {
					it.remove();
					continue;
				}
				String value = ((String[]) entry.getValue())[0];
				if (value == "" || "".equals(value)) {
					k++;
				} else {
					name = name.substring(8);
					buffer.append(" and rs."
									+ name
									+ " like '%"
									+ value
									+ "%'");
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
		super.saveDetachedCriteriaInSession(null);
		Page page = null;
		Map map1 = this.getParamsMap();
		Iterator it = map1.entrySet().iterator();
		//学期处理start
		String[] val = (String[])map1.get("search__peBzzBatch.name");
		String sql_start = "select rs.*,'batch_name','select_batch_id' from (";
		String sql_con1 = "";
		String sql_con2 = "";
		String sql_con3 = "";
		String sql_end = " ) rs where 1=1 ";
		if(val != null && val.length > 0 && val[0]!=null && !val[0].equals("")) {
			sql_con1=" and bb.name='"+val[0]+"' ";
			sql_con2 = " and b.name ='"+val[0]+"' ";
			sql_con3 = " and a.num > 0 ";
			sql_start = "select rs.* from (select rs.*,batch.name as batch_name,batch.id as select_batch_id from ( ";
			sql_end = " ) rs, pe_bzz_batch batch where batch.name='"+val[0]+"' ) rs where 1=1 ";
		}
		//学期处理end
		
		String sql = sql_start+ "  select a.id as id, a.name  as name, a.code as code,a.p_name as p_name,a.num, nvl(b.paynum,0) as paynum from (\n"
				+ "  select p.id as id, p.name  as name, p.code as code,pp.name  as p_name,count(ps.id) as num\n"
				+ "  from pe_enterprise p  inner join pe_enterprise pp on p.fk_parent_id = pp.id\n"
//				+ "  left outer join (select br.fk_enterprise_id,br.id,bb.name as batchname from pe_bzz_recruit br,pe_bzz_batch bb where br.fk_batch_id=bb.id and bb.recruit_selected='1') ps on p.id = ps.fk_enterprise_id\n"
				+ "  left outer join (select br.fk_enterprise_id,br.id,bb.name as batchname from pe_bzz_recruit br,pe_bzz_batch bb where br.fk_batch_id=bb.id "+sql_con1+") ps on p.id = ps.fk_enterprise_id\n"
				+ "  where p.fk_parent_id is not null\n"
				+ "  group by  p.id, p.name, p.code, pp.name ) a," 
				+ "  (select p.id as id, p.name  as name, p.code as code,pp.name  as p_name,sum(ps.num) as paynum\n"
				+ "  from pe_enterprise p  inner join pe_enterprise pp on p.fk_parent_id = pp.id\n"
				+ "  left outer join (select ps.fk_enterprise_id,ps.num from pe_bzz_fax_info ps,enum_const e,pe_bzz_batch b "
//				+ " where e.id=ps.flag_fp_open_state and b.id=ps.fk_batch_id and b.recruit_selected='1' --and e.code='1' \n" +
				+ " where e.id=ps.flag_fp_open_state and b.id=ps.fk_batch_id "+sql_con2+" \n" +
						") ps on p.id = ps.fk_enterprise_id\n"
				+ "  where p.fk_parent_id is not null\n"
				+ "  group by  p.id, p.name, p.code, pp.name) b where a.id=b.id " + sql_con3 + sql_end;

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
}
