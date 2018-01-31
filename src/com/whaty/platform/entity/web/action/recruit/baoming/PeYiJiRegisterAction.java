package com.whaty.platform.entity.web.action.recruit.baoming;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class PeYiJiRegisterAction extends MyBaseAction<PeEnterprise> {
	
	private String eId;
	
	private List<PeBzzStudent> stuList;
	
	private PeEnterprise peEnterprise;
	
	private int stuCount;
	
	
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().setTitle("一级企业列表");
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		
		this.getGridConfig().addColumn(this.getText("企业编号"), "code");		
		this.getGridConfig().addColumn(this.getText("企业名称"), "name");		
		
		this.getGridConfig().addColumn(this.getText("报名人数"), "bmnum", false,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("缴费人数"), "jfnum", false,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("注册人数"), "zcnum", false,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("导出人数"), "dcnum", false,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("制卡人数"), "zknum", false,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("邮寄人数"), "mailnum", false,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("收到人数"), "mail_received_num", false,
				false, true, "");
		//新增学期信息
		ColumnConfig c_name2 = new ColumnConfig(this.getText("学期"), "peBzzBatch.name", true,false, false,"TextField",false,25,"");
		c_name2.setComboSQL("select b.id,b.name from pe_bzz_batch b");
		this.getGridConfig().addColumn(c_name2);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeEnterprise.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peYiJiRegister";
	}

	public PeEnterprise getBean() {
		return (PeEnterprise) super.superGetBean();
	}

	public void setBean(PeEnterprise enterprise) {
		super.superSetBean(enterprise);
	}

	public String abstractList() {
		ActionContext context = ActionContext.getContext(); 
		Map map1 = context.getParameters(); 
		
		Iterator it = map1.entrySet().iterator();
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql_con = "";
		if (us.getUserLoginType().equals("2"))
			sql_con = " and pe.id='" + us.getPriEnterprises().get(0).getId()
					+ "'";
		//学期处理start
		String[] val = (String[])map1.get("search__peBzzBatch.name");
		String sql_start = "select rs.*,'batch_name' from (";
		String sql_con1 = "";
		String sql_con2 = "";
		String sql_con3 = "";
		String sql_con4 = "";
		String sql_con5 = "";
		String sql_con6 = "";
		String sql_con7 = "";
		String sql_end = " ) rs where 1=1 ";
		if(val != null && val.length > 0 && val[0]!=null && !val[0].equals("")) {
			sql_con1=" and batch.name='"+val[0]+"' ";
			sql_con2 = " and b1.name ='"+val[0]+"' ";
			sql_con3=" batch.name='"+val[0]+"' and ";
			sql_con4 = " where batch.name='"+val[0]+"' ";
			sql_con5 = " and b2.name ='"+val[0]+"' ";
			sql_con6 = " and b3.name ='"+val[0]+"' ";
			sql_con7 = " and one.bmnum > 0 ";
			sql_start = "select rs.* from (select rs.*,batch.name as batch_name from ( ";
			sql_end = " ) rs, pe_bzz_batch batch where batch.name='"+val[0]+"' ) rs where 1=1 ";
		}
		//学期处理end
		
		int k = 0;
		int n = 0;
		String tempsql = sql_start + " select one.* from ("
			+ "select e1.id,e1.code,e1.name,nvl(sum(bmnum),0) as bmnum,nvl(sum(jfnum),0) as jfnum,nvl(sum(zcnum),0) as zcnum,nvl(sum(dcnum),0) as dcnum,nvl(sum(zknum),0) as zknum,nvl(sum(mailnum),0) as mailnum,nvl(sum(mail_received_num),0) as mail_received_num from ("
			+ "select rs10.p_id as id, rs10.p_code as code, rs10.parent_name as name, rs10.bmnum, rs10.jfnum, rs10.zcnum, rs10.dcnum, rs10.zknum, rs10.mailnum, nvl(rs11.mail_received_num,0) as mail_received_num \n"
			+ "from \n"
			+ "  (select rs8.*, nvl(rs9.mail_num,0) as mailnum \n"
			+ "   from \n "
			+ "      (select rs6.*, nvl(rs7.zknum,0) as zknum \n "
			+ "       from \n "
			+ "        (select rs4.*, nvl(rs5.dcnum,0) as dcnum \n "
			+ "         from \n "
			+ "          (select rs2.*, nvl(rs3.zcnum,0) as zcnum \n "
			+ "           from \n "
			+ "               (select a.code as code,a.ent_id as ent_id, a.ent_name as ent_name, a.parent_name as parent_name,a.p_id as p_id,a.p_code as p_code, a.bmnum as bmnum ,nvl(b.jfnum,0) as jfnum  \n "
			+ "                from \n "
			+ "                ( select ent.code as code,ent.id as ent_id, ent.name as ent_name, ent1.name as parent_name,ent1.id as p_id,ent1.code as p_code,count(recruit.id) as bmnum \n "
			+ "                  from  Pe_Enterprise ent  left outer join pe_bzz_recruit recruit on ent.id = recruit.fk_enterprise_id, pe_enterprise ent1 ,pe_bzz_batch batch \n "
//			+ "                  where ent.fk_parent_id is not null and ent1.id = ent.fk_parent_id and batch.id= recruit.FK_BATCH_ID and batch.recruit_selected='1' \n "
			+ "                  where ent.fk_parent_id is not null and ent1.id = ent.fk_parent_id and batch.id= recruit.FK_BATCH_ID "+sql_con1+" \n "
			+ "                  group by ent.code,ent.id, ent.name,ent1.name,ent1.id,ent1.code) a \n "
			+ "                left outer join \n "
			+ "                ( select fax.fk_enterprise_id as fax_ent_id, sum(fax.num) as jfnum \n "
			+ "                  from pe_bzz_fax_info fax,enum_const const,pe_bzz_batch b1 \n "
//			+ "                  where fax.flag_fp_open_state=const.id and fax.fk_batch_id=b1.id and b1.recruit_selected='1' --and const.name='已开'\n "
			+ "                  where fax.flag_fp_open_state=const.id and fax.fk_batch_id=b1.id "+sql_con2+" \n "
			+ "                  group by  fax.fk_enterprise_id) b  \n "
			+ "                on a.ent_id=b.fax_ent_id) rs2 \n "
			+ "              left outer join \n "
			+ "              ( select stu.fk_enterprise_id as stu_ent_id, count(stu.reg_no) as zcnum \n "
			+ "                from pe_bzz_student stu inner join  pe_bzz_batch batch on stu.fk_batch_id = batch.id \n "
//			+ "                where batch.recruit_selected='1' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n "
			+ "                where "+sql_con3+" stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n "
			+ "                group by stu.fk_enterprise_id) rs3 \n "
			+ "              on rs2.ent_id=rs3.stu_ent_id) rs4 \n "
			+ "           left outer join  \n "
			+ "             (select  stu.fk_enterprise_id as stu_ent_id, count(stu.reg_no) as dcnum \n "
			+ "              from pe_bzz_student stu inner join  pe_bzz_batch batch on stu.fk_batch_id = batch.id \n "
//			+ "              where batch.recruit_selected='1' and stu.export_state='1' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n "
			+ "              where "+sql_con3+" stu.export_state='1' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n "
			+ "              group by stu.fk_enterprise_id) rs5 \n "
			+ "           on rs4.ent_id = rs5.stu_ent_id) rs6  \n "
			+ "        left outer join \n "
			+ "          (select zk_info.fk_enterprise_id as zk_ent_id, sum(zk_info.received_person_num) as zknum \n "
			+ "           from pe_bzz_zk_info zk_info inner join pe_bzz_batch batch on zk_info.fk_batch_id = batch.id \n "
//			+ "           where batch.recruit_selected='1' \n "
			+ sql_con4
			+ "           group by zk_info.fk_enterprise_id) rs7 \n "
			+ "        on rs6.ent_id=rs7.zk_ent_id) rs8  \n "
			+ "     left outer join  \n "
			+ "       (select mail_info.fk_enterprise_id as mail_ent_id, sum(mail_info.num) as mail_num \n "
			+ "        from pe_bzz_mail_info mail_info, pe_bzz_batch b2 \n "
//			+ "        where b2.id=mail_info.FK_BATCH_ID and b2.recruit_selected='1' "
			+ "        where b2.id=mail_info.FK_BATCH_ID "+sql_con5
			+ "        group by mail_info.fk_enterprise_id) rs9 \n "
			+ "     on rs8.ent_id = rs9.mail_ent_id) rs10 \n "
			+ "  left outer join  \n "
			+ "   (select mail_info.fk_enterprise_id as mail_ent_id, sum(mail_info.num) as mail_received_num \n "
			+ "     from pe_bzz_batch b3,pe_bzz_mail_info mail_info inner join enum_const const on mail_info.flag_mail_send_state = const.id  \n "
//			+ "     where const.name='已收' and b3.id=mail_info.FK_BATCH_ID and b3.recruit_selected='1' \n "
			+ "     where const.name='已收' and b3.id=mail_info.FK_BATCH_ID "+sql_con6+" \n "
			+ "     group by mail_info.fk_enterprise_id) rs11 \n "
			+ "  on rs10.ent_id = rs11.mail_ent_id \n "	
			+" ) a1,pe_enterprise e1 where e1.fk_parent_id is null and e1.id=a1.id(+) group by e1.id,e1.code,e1.name) one "
			+" where 1=1 " + sql_con7 + sql_end;
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
					buffer
							.append(" and "
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
		Page page = null;
		//学期处理start
		ActionContext context = ActionContext.getContext(); 
		Map map1 = context.getParameters(); 
		String[] val = (String[])map1.get("search__peBzzBatch.name");
		String sql_start = "select rs.*,'batch_name' from (";
		String sql_con1 = "";
		String sql_con2 = "";
		String sql_con3 = "";
		String sql_con4 = "";
		String sql_con5 = "";
		String sql_con6 = "";
		String sql_con7 = "";
		String sql_end = " ) rs where 1=1 ";
		if(val != null && val.length > 0 && val[0]!=null && !val[0].equals("")) {
			sql_con1=" and batch.name='"+val[0]+"' ";
			sql_con2 = " and b1.name ='"+val[0]+"' ";
			sql_con3=" batch.name='"+val[0]+"' and ";
			sql_con4 = " where batch.name='"+val[0]+"' ";
			sql_con5 = " and b2.name ='"+val[0]+"' ";
			sql_con6 = " and b3.name ='"+val[0]+"' ";
//			sql_con3 = " and a.num > 0 ";
			sql_con7 = " and one.bmnum > 0 ";
			sql_start = "select rs.* from (select rs.*,batch.name as batch_name from ( ";
			sql_end = " ) rs, pe_bzz_batch batch where batch.name='"+val[0]+"' ) rs where 1=1 ";
		}
		//学期处理end
		String sql = sql_start + " select one.* from ("
			+ "select e1.id,e1.code,e1.name,nvl(sum(bmnum),0) as bmnum,nvl(sum(jfnum),0) as jfnum,nvl(sum(zcnum),0) as zcnum,nvl(sum(dcnum),0) as dcnum,nvl(sum(zknum),0) as zknum,nvl(sum(mailnum),0) as mailnum,nvl(sum(mail_received_num),0) as mail_received_num from ("
			+ "select rs10.p_id as id, rs10.p_code as code, rs10.parent_name as name, rs10.bmnum, rs10.jfnum, rs10.zcnum, rs10.dcnum, rs10.zknum, rs10.mailnum, nvl(rs11.mail_received_num,0) as mail_received_num \n"
			+ "from \n"
			+ "  (select rs8.*, nvl(rs9.mail_num,0) as mailnum \n"
			+ "   from \n "
			+ "      (select rs6.*, nvl(rs7.zknum,0) as zknum \n "
			+ "       from \n "
			+ "        (select rs4.*, nvl(rs5.dcnum,0) as dcnum \n "
			+ "         from \n "
			+ "          (select rs2.*, nvl(rs3.zcnum,0) as zcnum \n "
			+ "           from \n "
			+ "               (select a.code as code,a.ent_id as ent_id, a.ent_name as ent_name, a.parent_name as parent_name,a.p_id as p_id,a.p_code as p_code, a.bmnum as bmnum ,nvl(b.jfnum,0) as jfnum  \n "
			+ "                from \n "
			+ "                ( select ent.code as code,ent.id as ent_id, ent.name as ent_name, ent1.name as parent_name,ent1.id as p_id,ent1.code as p_code,count(recruit.id) as bmnum \n "
			+ "                  from  Pe_Enterprise ent  left outer join pe_bzz_recruit recruit on ent.id = recruit.fk_enterprise_id, pe_enterprise ent1 ,pe_bzz_batch batch \n "
//			+ "                  where ent.fk_parent_id is not null and ent1.id = ent.fk_parent_id and batch.id= recruit.FK_BATCH_ID and batch.recruit_selected='1' \n "
			+ "                  where ent.fk_parent_id is not null and ent1.id = ent.fk_parent_id and batch.id= recruit.FK_BATCH_ID "+sql_con1+" \n "
			+ "                  group by ent.code,ent.id, ent.name,ent1.name,ent1.id,ent1.code) a \n "
			+ "                left outer join \n "
			+ "                ( select fax.fk_enterprise_id as fax_ent_id, sum(fax.num) as jfnum \n "
			+ "                  from pe_bzz_fax_info fax,enum_const const,pe_bzz_batch b1 \n "
//			+ "                  where fax.flag_fp_open_state=const.id and fax.fk_batch_id=b1.id and b1.recruit_selected='1' --and const.name='已开' \n "
			+ "                  where fax.flag_fp_open_state=const.id and fax.fk_batch_id=b1.id "+sql_con2+" \n "
			+ "                  group by  fax.fk_enterprise_id) b  \n "
			+ "                on a.ent_id=b.fax_ent_id) rs2 \n "
			+ "              left outer join \n "
			+ "              ( select stu.fk_enterprise_id as stu_ent_id, count(stu.reg_no) as zcnum \n "
			+ "                from pe_bzz_student stu inner join  pe_bzz_batch batch on stu.fk_batch_id = batch.id \n "
//			+ "                where batch.recruit_selected='1'  and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n "
			+ "                where "+sql_con3+" stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n "
			+ "                group by stu.fk_enterprise_id) rs3 \n "
			+ "              on rs2.ent_id=rs3.stu_ent_id) rs4 \n "
			+ "           left outer join  \n "
			+ "             (select  stu.fk_enterprise_id as stu_ent_id, count(stu.reg_no) as dcnum \n "
			+ "              from pe_bzz_student stu inner join  pe_bzz_batch batch on stu.fk_batch_id = batch.id \n "
//			+ "              where batch.recruit_selected='1' and stu.export_state='1' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n "
			+ "              where "+sql_con3+" stu.export_state='1' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n "
			+ "              group by stu.fk_enterprise_id) rs5 \n "
			+ "           on rs4.ent_id = rs5.stu_ent_id) rs6  \n "
			+ "        left outer join \n "
			+ "          (select zk_info.fk_enterprise_id as zk_ent_id, sum(zk_info.received_person_num) as zknum \n "
			+ "           from pe_bzz_zk_info zk_info inner join pe_bzz_batch batch on zk_info.fk_batch_id = batch.id \n "
//			+ "           where batch.recruit_selected='1' \n "
			+ sql_con4
			+ "           group by zk_info.fk_enterprise_id) rs7 \n "
			+ "        on rs6.ent_id=rs7.zk_ent_id) rs8  \n "
			+ "     left outer join  \n "
			+ "       (select mail_info.fk_enterprise_id as mail_ent_id, sum(mail_info.num) as mail_num \n "
			+ "        from pe_bzz_mail_info mail_info, pe_bzz_batch b2 \n "
//			+ "        where b2.id=mail_info.FK_BATCH_ID and b2.recruit_selected='1' "
			+ "        where b2.id=mail_info.FK_BATCH_ID "+sql_con5
			+ "        group by mail_info.fk_enterprise_id) rs9 \n "
			+ "     on rs8.ent_id = rs9.mail_ent_id) rs10 \n "
			+ "  left outer join  \n "
			+ "   (select mail_info.fk_enterprise_id as mail_ent_id, sum(mail_info.num) as mail_received_num \n "
			+ "     from pe_bzz_batch b3,pe_bzz_mail_info mail_info inner join enum_const const on mail_info.flag_mail_send_state = const.id  \n "
//			+ "     where const.name='已收' and b3.id=mail_info.FK_BATCH_ID and b3.recruit_selected='1' \n "
			+ "     where const.name='已收' and b3.id=mail_info.FK_BATCH_ID "+sql_con6+" \n "
			+ "     group by mail_info.fk_enterprise_id) rs11 \n "
			+ "  on rs10.ent_id = rs11.mail_ent_id \n "	
			+" ) a1,pe_enterprise e1 where e1.fk_parent_id is null and e1.id=a1.id(+) group by e1.id,e1.code,e1.name) one "
			+" where 1=1 " + sql_con7 + sql_end;

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
	
	public String exportZk() {
		
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
			dc.createCriteria("peEnterprise", "peEnterprise");
			dc.createCriteria("peEnterprise.peEnterprise", "peEnterprise.peEnterprise");
			dc.createCriteria("peBzzBatch", "peBzzBatch");
			dc.createCriteria("ssoUser", "ssoUser");
			dc.add(Restrictions.eq("exportState", "0"));
			dc.add(Restrictions.eq("peEnterprise.id", this.getEId()));
			
			this.stuList = this.getGeneralService().getDetachList(dc);
			
			updatePeBzzStudent(stuList);//导出状态置'1'
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "exportExcel";
	}

	private void updatePeBzzStudent(List<PeBzzStudent> stuList) {
		
		 if(stuList == null || stuList.size() == 0 || stuList.get(0) == null ) {
			 return ;
		 } 
		 String ent = this.getPeEnterprise().getId();
		 String batch = stuList.get(0).getPeBzzBatch().getId();
		 
			String sql = "update pe_bzz_student set export_state='1' where export_state='0' and fk_enterprise_id='"
				+ent
				+"' and fk_batch_id ='"
				+batch
				+"'";

			try {
				this.getGeneralService().executeBySQL(sql);
			} catch (EntityException e) {
				e.printStackTrace();
			}
	}

	public String getEId() {
		return eId;
	}

	public void setEId(String id) {
		eId = id;
	}

	public List<PeBzzStudent> getStuList() {
		return stuList;
	}

	public void setStuList(List<PeBzzStudent> stuList) {
		this.stuList = stuList;
	}
	
	public PeEnterprise getPeEnterprise() {
		if(stuList != null 
				&& stuList.size() != 0 
				&& stuList.get(0) != null) {
			
			return this.peEnterprise = stuList.get(0).getPeEnterprise();
		}
		
		try {

			this.peEnterprise =  this.getGeneralService().getById(this.getEId());			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		
		return this.peEnterprise;
	}
	
	public int getStuCount() {
		if(stuList != null 
				&& stuList.size() != 0 
				&& stuList.get(0) != null) {
			
			return stuCount = stuList.size();
		}
		
		return stuCount = 0;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}

}