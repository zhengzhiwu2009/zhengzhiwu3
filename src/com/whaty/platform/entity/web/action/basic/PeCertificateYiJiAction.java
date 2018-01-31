package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzExamBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class PeCertificateYiJiAction extends MyBaseAction<PeEnterprise> {
	
	private String eId;
	
	private List<PeBzzStudent> stuList;
	
	private PeEnterprise peEnterprise;
	
	private int stuCount;
	
	private PeBzzExamBatch peBzzExamBatch = null;
	
	
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().setTitle("一级企业列表");
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("考试批次"),"peBzzExamBatch.name",true,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("企业编号"), "code");		
		this.getGridConfig().addColumn(this.getText("企业名称"), "name");		
		
		this.getGridConfig().addColumn(this.getText("学习人数"), "xxnum", false,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("证书人数"), "zsnum", false,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("邮寄人数"), "mailnum", false,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("收到人数"), "mail_received_num", false,
				false, true, "");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeEnterprise.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peCertificateYiJi";
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
		int k = 0;
		int n = 0;
		String tempsql =
			"--合并sql\n" +
			"select one.* from(\n" + 
			"select e1.id,eb.name as exambatch_name,\n" +
			"       e1.code,\n" + 
			"       e1.name,\n" + 
			"       nvl(sum(xxnum), 0) as xxnum,\n" + 
			"       nvl(sum(zsnum), 0) as zsnum,\n" + 
			"       nvl(sum(mail_num), 0) as mailnum,\n" + 
			"       nvl(sum(mail_received_num), 0) as mail_received_num,\n" + 
			"       eb.id as exambatch_id \n" + 
			"  from (select rs10.p_id as id,\n" + 
			"               rs10.p_code as code,\n" + 
			"               rs10.parent_name as name,\n" + 
			"               rs10.xxnum,\n" + 
			"               rs10.zsnum,\n" + 
			"               rs10.mail_num,\n" + 
			"               rs10.mail_received_num,\n" + 
			"               rs10.exambatch_id,\n" + 
			"               rs10.exambatch_name\n" + 
			"          from (select rs4.*,\n" + 
			"                       nvl(rs5.mail_received_num, 0) as mail_received_num\n" + 
			"                  from (select rs2.*, nvl(rs3.mail_num, 0) as mail_num\n" + 
			"                          from (select a.code as code,\n" + 
			"                                       a.ent_id as ent_id,\n" + 
			"                                       a.ent_name as ent_name,\n" + 
			"                                       a.parent_name as parent_name,\n" + 
			"                                       a.p_id as p_id,\n" + 
			"                                       a.p_code as p_code,\n" + 
			"                                       a.xxnum as xxnum,\n" + 
			"                                       nvl(b.zsnum, 0) as zsnum,\n" + 
			"                                       a.exambatch_id,\n" + 
			"                                       a.exambatch_name\n" + 
			"                                  from ( --学习人数\n" + 
			"                                        select ent.code as code,\n" + 
			"                                                ent.id as ent_id,\n" + 
			"                                                ent.name as ent_name,\n" + 
			"                                                ent1.name as parent_name,\n" + 
			"                                                ent1.id as p_id,\n" + 
			"                                                ent1.code as p_code,\n" + 
			"                                                count(student.id) as xxnum,\n" + 
			"                                                embatch.name as exambatch_name,\n" + 
			"                                                embatch.id as exambatch_id\n" + 
			"                                          from Pe_Enterprise ent\n" + 
			"                                          left outer join pe_bzz_student student on ent.id =\n" + 
			"                                                                                    student.fk_enterprise_id,\n" + 
			"                                         pe_enterprise ent1,\n" + 
			"                                         pe_bzz_batch\n" + 
			"                                         batch,\n" + 
			"                                         pe_bzz_exambatch\n" + 
			"                                         embatch,\n" + 
			"                                         sso_user sso\n" + 
			"                                         where ent.fk_parent_id is not null\n" + 
			"                                           and ent1.id = ent.fk_parent_id\n" + 
			"                                           and batch.id = student.FK_BATCH_ID\n" + 
			"                                           and embatch.batch_id = batch.id\n" + 
			"                                           and student.fk_sso_user_id = sso.id\n" + 
			"                                           and student.flag_rank_state =\n" + 
			"                                               '402880f827f5b99b0127f5bdadc70006'\n" + 
			"                                           and sso.flag_isvalid = '2'\n" + 
			"                                        -- and embatch.selected = '402880a91dadc115011dadfcf26b00aa'\n" + 
			"                                         group by ent.code,\n" + 
			"                                                   ent.id,\n" + 
			"                                                   ent.name,\n" + 
			"                                                   ent1.name,\n" + 
			"                                                   ent1.id,\n" + 
			"                                                   ent1.code,\n" + 
			"                                                   embatch.id,\n" + 
			"                                                   embatch.name) a\n" + 
			"                                  left outer join ( --证书人数\n" + 
			"                                                  select stu.fk_enterprise_id as stu_ent_id,\n" + 
			"                                                          b4.id as exambatch_id,\n" + 
			"                                                          b4.name as exambatch_name,\n" + 
			"                                                          count(certificate.id) as zsnum\n" + 
			"                                                    from pe_bzz_exambatch   b4,\n" + 
			"                                                          pe_bzz_batch       b5,\n" + 
			"                                                          pe_bzz_student     stu,\n" + 
			"                                                          pe_bzz_certificate certificate\n" + 
			"                                                   where certificate.student_id =\n" + 
			"                                                         stu.id\n" + 
			"                                                     and b4.batch_id = b5.id\n" + 
			"                                                        --and b4.selected = '402880a91dadc115011dadfcf26b00aa'\n" + 
			"                                                     and b5.id = stu.fk_batch_id\n" + 
			"                                                   group by stu.fk_enterprise_id,\n" + 
			"                                                             b4.id,\n" + 
			"                                                             b4.name) b on a.ent_id =\n" + 
			"                                                                           b.stu_ent_id\n" + 
			"                                                                       and a.exambatch_id =\n" + 
			"                                                                           b.exambatch_id) rs2\n" + 
			"                          left outer join ( --邮寄人数\n" + 
			"                                          select mail_info.fk_enterprise_id as mail_ent_id,\n" + 
			"                                                  sum(mail_info.num) as mail_num,\n" + 
			"                                                  b2.id as exambatch_id,\n" + 
			"                                                  b2.name as exambatch_name\n" + 
			"                                            from pe_bzz_certificate_mail_info mail_info,\n" + 
			"                                                  pe_bzz_exambatch             b2\n" + 
			"                                           where b2.id =\n" + 
			"                                                 mail_info.FK_EXAM_BATCH_ID\n" + 
			"                                          --and b2.selected = '402880a91dadc115011dadfcf26b00aa'\n" + 
			"                                           group by mail_info.fk_enterprise_id,\n" + 
			"                                                     b2.id,\n" + 
			"                                                     b2.name) rs3 on rs2.ent_id =\n" + 
			"                                                                     rs3.mail_ent_id\n" + 
			"                                                                 and rs2.exambatch_id =\n" + 
			"                                                                     rs3.exambatch_id) rs4\n" + 
			"                  left outer join ( --收到人数\n" + 
			"                                  select mail_info.fk_enterprise_id as received_ent_id,\n" + 
			"                                          sum(mail_info.num) as mail_received_num,\n" + 
			"                                          b3.id as exambatch_id,\n" + 
			"                                          b3.name as exambatch_name\n" + 
			"                                    from pe_bzz_exambatch             b3,\n" + 
			"                                          pe_bzz_certificate_mail_info mail_info\n" + 
			"                                   inner join enum_const const on mail_info.flag_mail_send_status =\n" + 
			"                                                                  const.id\n" + 
			"                                   where const.name = '已收'\n" + 
			"                                     and b3.id = mail_info.FK_EXAM_BATCH_ID\n" + 
			"                                  --and b3.selected = '402880a91dadc115011dadfcf26b00aa'\n" + 
			"                                   group by mail_info.fk_enterprise_id,\n" + 
			"                                             b3.id,\n" + 
			"                                             b3.name) rs5 on rs4.ent_id =\n" + 
			"                                                             rs5.received_ent_id\n" + 
			"                                                         and rs4.exambatch_id =\n" + 
			"                                                             rs5.exambatch_id) rs10) a1,\n" + 
			"       pe_enterprise e1,\n" + 
			"       pe_bzz_exambatch eb\n" + 
			" where e1.fk_parent_id is null\n" + 
			"   and e1.id = a1.id(+)\n" + 
			"   and eb.id(+)= a1.exambatch_id\n" + 
			" group by e1.id, e1.code, e1.name, eb.id, eb.name)one where 1=1";

		StringBuffer buffer = new StringBuffer(tempsql);
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			String name = entry.getKey().toString();
			if (name.startsWith("search__")) {
				n++;
				String value = ((String[]) entry.getValue())[0];
				name = name.substring(8);
				if (value == "" || "".equals(value)) {
					if(name.toUpperCase().equals("PEBZZEXAMBATCH.NAME")){
						name="exambatch_name";
						
						try {
							buffer.append("  and  " + name + " = '" + this.getCurBatch() + "'");
						} catch (EntityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						k++;
					}
					
				} else {
					
					if(name.toUpperCase().equals("NAME")) {
						
						name="name";
						buffer.append("  and  " + name + " like '%" + value + "%'");
					} else if(name.toUpperCase().equals("PEBZZEXAMBATCH.NAME")) {
						
						name="exambatch_name";
						buffer.append("  and  " + name + " = '" + value + "'");
					} else {
						
						buffer.append("  and  " + name + " like '%" + value + "%'");
					}
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

		String sql = 
			"--合并sql\n" +
			"select one.* from(\n" + 
			"select e1.id,\n" + 
			"       e1.code,\n" + 
			"       e1.name,\n" + 
			"       nvl(sum(xxnum), 0) as xxnum,\n" + 
			"       nvl(sum(zsnum), 0) as zsnum,\n" + 
			"       nvl(sum(mail_num), 0) as mailnum,\n" + 
			"       nvl(sum(mail_received_num), 0) as mail_received_num\n" + 
			"  from (select rs10.p_id as id,\n" + 
			"               rs10.p_code as code,\n" + 
			"               rs10.parent_name as name,\n" + 
			"               rs10.xxnum,\n" + 
			"               rs10.zsnum,\n" + 
			"               rs10.mail_num,\n" + 
			"               rs10.mail_received_num\n" + 
			"          from (select rs4.*,\n" + 
			"                       nvl(rs5.mail_received_num, 0) as mail_received_num\n" + 
			"                  from (select rs2.*, nvl(rs3.mail_num, 0) as mail_num\n" + 
			"                          from (select a.code as code,\n" + 
			"                                       a.ent_id as ent_id,\n" + 
			"                                       a.ent_name as ent_name,\n" + 
			"                                       a.parent_name as parent_name,\n" + 
			"                                       a.p_id as p_id,\n" + 
			"                                       a.p_code as p_code,\n" + 
			"                                       a.xxnum as xxnum,\n" + 
			"                                       nvl(b.zsnum, 0) as zsnum\n" + 
			"                                  from ( --学习人数\n" + 
			"                                        select ent.code as code,\n" + 
			"                                                ent.id as ent_id,\n" + 
			"                                                ent.name as ent_name,\n" + 
			"                                                ent1.name as parent_name,\n" + 
			"                                                ent1.id as p_id,\n" + 
			"                                                ent1.code as p_code,\n" + 
			"                                                count(student.id) as xxnum\n" + 
			"                                          from Pe_Enterprise ent\n" + 
			"                                          left outer join pe_bzz_student student on ent.id =\n" + 
			"                                                                                    student.fk_enterprise_id,\n" + 
			"                                         pe_enterprise ent1,\n" + 
			"                                         pe_bzz_batch\n" + 
			"                                         batch,\n" + 
			"                                         pe_bzz_exambatch\n" + 
			"                                         embatch,\n" + 
			"                                         sso_user sso\n" + 
			"                                         where ent.fk_parent_id is not null\n" + 
			"                                           and ent1.id = ent.fk_parent_id\n" + 
			"                                           and batch.id = student.FK_BATCH_ID\n" + 
			"                                           and embatch.batch_id = batch.id\n" + 
			"                                           and student.fk_sso_user_id = sso.id\n" + 
			"                                           and student.flag_rank_state =\n" + 
			"                                               '402880f827f5b99b0127f5bdadc70006'\n" + 
			"                                           and sso.flag_isvalid = '2'\n" + 
			"                                           and embatch.selected =\n" + 
			"                                               '402880a91dadc115011dadfcf26b00aa'\n" + 
			"                                         group by ent.code,\n" + 
			"                                                   ent.id,\n" + 
			"                                                   ent.name,\n" + 
			"                                                   ent1.name,\n" + 
			"                                                   ent1.id,\n" + 
			"                                                   ent1.code) a\n" + 
			"                                  left outer join ( --证书人数\n" + 
			"                                                  select stu.fk_enterprise_id as stu_ent_id,\n" + 
			"                                                          count(certificate.id) as zsnum\n" + 
			"                                                    from pe_bzz_exambatch   b4,\n" + 
			"                                                          pe_bzz_batch       b5,\n" + 
			"                                                          pe_bzz_student     stu,\n" + 
			"                                                          pe_bzz_certificate certificate\n" + 
			"                                                   where certificate.student_id =\n" + 
			"                                                         stu.id\n" + 
			"                                                     and b4.batch_id = b5.id\n" + 
			"                                                     and b4.selected =\n" + 
			"                                                         '402880a91dadc115011dadfcf26b00aa'\n" + 
			"                                                     and b5.id = stu.fk_batch_id\n" + 
			"                                                   group by stu.fk_enterprise_id) b on a.ent_id =\n" + 
			"                                                                                       b.stu_ent_id) rs2\n" + 
			"                          left outer join ( --邮寄人数\n" + 
			"                                          select mail_info.fk_enterprise_id as mail_ent_id,\n" + 
			"                                                  sum(mail_info.num) as mail_num\n" + 
			"                                            from pe_bzz_certificate_mail_info mail_info,\n" + 
			"                                                  pe_bzz_exambatch             b2\n" + 
			"                                           where b2.id =\n" + 
			"                                                 mail_info.FK_EXAM_BATCH_ID\n" + 
			"                                             and b2.selected =\n" + 
			"                                                 '402880a91dadc115011dadfcf26b00aa'\n" + 
			"                                           group by mail_info.fk_enterprise_id) rs3 on rs2.ent_id =\n" + 
			"                                                                                       rs3.mail_ent_id) rs4\n" + 
			"                  left outer join ( --收到人数\n" + 
			"                                  select mail_info.fk_enterprise_id as received_ent_id,\n" + 
			"                                          sum(mail_info.num) as mail_received_num\n" + 
			"                                    from pe_bzz_exambatch             b3,\n" + 
			"                                          pe_bzz_certificate_mail_info mail_info\n" + 
			"                                   inner join enum_const const on mail_info.flag_mail_send_status =\n" + 
			"                                                                  const.id\n" + 
			"                                   where const.name = '已收'\n" + 
			"                                     and b3.id = mail_info.FK_EXAM_BATCH_ID\n" + 
			"                                     and b3.selected =\n" + 
			"                                         '402880a91dadc115011dadfcf26b00aa'\n" + 
			"                                   group by mail_info.fk_enterprise_id) rs5 on rs4.ent_id =\n" + 
			"                                                                               rs5.received_ent_id) rs10) a1,\n" + 
			"       pe_enterprise e1\n" + 
			" where e1.fk_parent_id is null\n" + 
			"   and e1.id = a1.id(+)\n" + 
			" group by e1.id, e1.code, e1.name)one where 1=1";


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
	private String getCurBatch() throws EntityException {
		if(this.peBzzExamBatch == null) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamBatch.class);
			DetachedCriteria sel= dc.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
			sel.add(Restrictions.eq("id","402880a91dadc115011dadfcf26b00aa"));
			
			List<PeBzzExamBatch> list = this.getGeneralService().getList(dc);
			if(list == null || list.size() == 0) {
				throw new EntityException("无当前考试批次");
			} else {
				this.peBzzExamBatch= list.get(0);
			}
		}
		String batchname=peBzzExamBatch.getName();
		
		return batchname;
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