package com.whaty.platform.entity.web.action.recruit.baoming;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeRegisterAction extends MyBaseAction<PeEnterprise> {

	private String eId;

	private List<PeBzzStudent> stuList;

	private PeEnterprise peEnterprise;

	private int stuCount;

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);

		this.getGridConfig().setTitle("二级企业列表");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("企业编号"), "code");
		this.getGridConfig().addColumn(this.getText("企业名称"), "name");
		
		ColumnConfig c_name0 = new ColumnConfig(this.getText("所在集团"),"peEnterprise.name");
		c_name0.setComboSQL("select e.id,e.name from pe_enterprise e where e.fk_parent_id is null");
		this.getGridConfig().addColumn(c_name0);

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
		this.getGridConfig().addColumn(this.getText("收到人数"),
				"mail_received_num", false, false, true, "");
		
		//不显示到列表的属性
		
		this.getGridConfig().addColumn(this.getText("所属行业"), "industry", false,
				true, false, "TextField", true, 150);
		this.getGridConfig().addColumn(this.getText("省"), "infoSheng", false,
				true, false, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("市"), "infoShi", false,
				true, false, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("地址"), "address", false,
				true, false, "TextField", true, 150);
		this.getGridConfig().addColumn(this.getText("邮编"), "zipcode", false,
				true, false, "TextField", true, 10, Const.zip_for_extjs);
		// this.getGridConfig().addColumn(this.getText("传真"), "fax", false);
		this.getGridConfig().addColumn(this.getText("传真"), "fax", false, true,
				false, "TextField", true, 20);
		
		this.getGridConfig().addColumn(this.getText("负责人姓名"), "fzrName", false,
				true, false, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("负责人电话"), "fzrMobile",
				false, true, false, "TextField", true, 12,
				Const.fiftyNum_for_extjs);
		this.getGridConfig().addColumn(this.getText("负责人性别"), "fzrXb", false,
				true, false, "TextField", true, 10, Const.sex_for_extjs);
		this.getGridConfig().addColumn(this.getText("负责人职务"), "fzrPosition",
				false, true, false, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("负责人办公电话区号"),
				"fzrAreacode", false, true, false, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("负责人办公电话"), "fzrPhone",
				false, true, false, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("负责人电子邮件"), "fzrEmail",
				false, true, false, "TextField", true, 50,
				Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("负责人通讯地址"), "fzrAddress",
				false, true, false, "TextField", true, 300);

		this.getGridConfig().addColumn(this.getText("学习管理员姓名"), "lxrName",
				false, true, false, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("学习管理员性别"), "lxrXb", false,
				true, false, "TextField", true, 10, Const.sex_for_extjs);
		this.getGridConfig().addColumn(this.getText("学习管理员职务"), "lxrPosition",
				false, true, false, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("学习管理员移动电话"), "lxrMobile",
				false, true, false, "TextField", true, 50,
				Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("学习管理员办公电话区号"),
				"lxrAreacode", false, true, false, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("学习管理员办公电话"), "lxrPhone",
				false, true, false, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("学习管理员电子邮件"), "lxrEmail",
				false, true, false, "TextField", true, 50,
				Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("学习管理员通讯地址"), "lxrAddress",
				false, true, false, "TextField", true, 150);

		this.getGridConfig().addColumn(this.getText("汇款方式"), "hkType", false,
				true, false, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("汇款全称"), "hkFullname",
				false, true, false, "TextField", true, 150);
		this.getGridConfig().addColumn(this.getText("接收省"), "receiverSheng",
				false, true, false, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("接收市"), "receiverShi",
				false, true, false, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("接收详细地址"),
				"receiverAddress", false, true, false, "TextField", true, 300);
		this.getGridConfig().addColumn(this.getText("接收邮编"), "receiverZipcode",
				false, true, false, "TextField", true, 10, Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("接收人"), "receiverUsername",
				false, true, false, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("接收人固定电话"),
				"receiverPhone", false, true, false, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("接收人移动电话"),
				"receiverMobile", false, true, false, "TextField", true, 100);
		
		this.getGridConfig().addColumn(this.getText("备注"), "bz", false, true,
				false, "TextField", true, 200);

		this
		.getGridConfig()
		.addRenderScript(
				this.getText("操作"),
				"{return '<a href=peBzzRegist.action?e_id='+record.data['id']+'>注册</a>|" 
				+ "<a href=peBzzZkInfo.action?e_id='+record.data['id']+'>制卡信息录入</a><br />" 
				+ "<a href=peRegister_exportZk.action?eId='+record.data['id']+'>导出制卡</a>|"
				+ "<a href=peBzzMailInfo.action?bean.peEnterprise.id='+record.data['id']+'>邮寄信息录入</a>';}",
				"");
		this.getGridConfig().addMenuScript(this.getText("导出邮寄地址"),
		"{" + "var searchData = s_formPanel.getForm().getValues(true);" +
		"window.location='/entity/manager/recruit/recruit_enterprise_address_excel.jsp?'+searchData;}");
}

	@Override
	public void setEntityClass() {
		this.entityClass = PeEnterprise.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peRegister";
	}

	public PeEnterprise getBean() {
		return (PeEnterprise) super.superGetBean();
	}

	public void setBean(PeEnterprise enterprise) {
		super.superSetBean(enterprise);
	}
	
	@Override
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

	public String abstractList() {
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
		int k = 0;
		int n = 0;
		String tempsql = " select rs10.ent_id as id, rs10.code as code, rs10.name as name, rs10.parent_name, rs10.bmnum, rs10.jfnum, rs10.zcnum, rs10.dcnum, rs10.zknum, rs10.mailnum, nvl(rs11.mail_received_num,0) as mail_received_num \n"
				+ this.getRedundancyField(32)  //为init_grid添加字段，以匹配grid字段的个数	
				+ " from  \n"
				+ "   (select rs8.*, nvl(rs9.mail_num,0) as mailnum  \n"
				+ "    from  \n"
				+ "        (select rs6.*, nvl(rs7.zknum,0) as zknum  \n"
				+ "         from  \n"
				+ "          (select rs4.*, nvl(rs5.dcnum,0) as dcnum  \n"
				+ "           from  \n"
				+ "            (select rs2.*, nvl(rs3.zcnum,0) as zcnum  \n"
				+ "             from  \n"
				+ "                 (select a.* ,nvl(b.jfnum,0) as jfnum  \n"
				+ "                 from \n"
				+ "                 (select a.code as code,a.ent_id as ent_id, a.ent_name as name, a.parent_name as parent_name, nvl(a1.bmnum,0) as bmnum  \n"
				+ "                  from  \n"
				+ "                  ( select ent.code as code,ent.id as ent_id, ent.name as ent_name, ent1.name as parent_name \n"
				+ "                    from  Pe_Enterprise ent left outer join pe_enterprise ent1 on ent1.id = ent.fk_parent_id \n"
				+ "                    where ent.fk_parent_id is not null \n"
				+ "                   ) a  \n"
				+ "                   left outer join \n"
				+ "                   (select recruit.fk_enterprise_id as ent_id, count(recruit.id) as bmnum \n"
				+ "                    from pe_bzz_recruit recruit inner join pe_bzz_batch b5 on recruit.fk_batch_id = b5.id \n"
				+ "                    where b5.recruit_selected='1' \n"
				+ "                    group by recruit.fk_enterprise_id) a1 \n"
				+ "                    on a.ent_id = a1.ent_id) a \n"
				+ "                  left outer join  \n"
				+ "                  ( select fax.fk_enterprise_id as fax_ent_id, sum(fax.num) as jfnum  \n"
				+ "                    from pe_bzz_fax_info fax,enum_const const ,pe_bzz_batch b1  \n"
				+ "                    where fax.flag_fp_open_state=const.id and fax.fk_batch_id=b1.id and b1.recruit_selected='1' --and const.name='已开' \n"
				+ "                    group by  fax.fk_enterprise_id) b   \n"
				+ "                  on a.ent_id=b.fax_ent_id) rs2  \n"
				+ "                left outer join  \n"
				+ "                ( select stu.fk_enterprise_id as stu_ent_id, count(stu.reg_no) as zcnum  \n"
				+ "                  from pe_bzz_student stu inner join  pe_bzz_batch batch on stu.fk_batch_id = batch.id  \n"
				+ "                  where batch.recruit_selected='1' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'  \n"
				+ "                  group by stu.fk_enterprise_id) rs3  \n"
				+ "                on rs2.ent_id=rs3.stu_ent_id) rs4  \n"
				+ "             left outer join   \n"
				+ "               (select  stu.fk_enterprise_id as stu_ent_id, count(stu.reg_no) as dcnum  \n"
				+ "                from pe_bzz_student stu inner join  pe_bzz_batch batch on stu.fk_batch_id = batch.id  \n"
				+ "                where batch.recruit_selected='1' and stu.export_state='1' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
				+ "                group by stu.fk_enterprise_id) rs5  \n"
				+ "             on rs4.ent_id = rs5.stu_ent_id) rs6   \n"
				+ "          left outer join  \n"
				+ "            (select zk_info.fk_enterprise_id as zk_ent_id, sum(zk_info.received_person_num) as zknum  \n"
				+ "             from pe_bzz_zk_info zk_info inner join pe_bzz_batch batch on zk_info.fk_batch_id = batch.id  \n"
				+ "             where batch.recruit_selected='1'  \n"
				+ "             group by zk_info.fk_enterprise_id) rs7  \n"
				+ "          on rs6.ent_id=rs7.zk_ent_id) rs8   \n"
				+ "       left outer join   \n"
				+ "         (select mail_info.fk_enterprise_id as mail_ent_id, sum(mail_info.num) as mail_num  \n"
				+ "          from pe_bzz_mail_info mail_info , pe_bzz_batch b2  \n"
				+ "          where b2.id=mail_info.FK_BATCH_ID and b2.recruit_selected='1' \n" 
				+ "          group by mail_info.fk_enterprise_id) rs9  \n"
				+ "       on rs8.ent_id = rs9.mail_ent_id) rs10  \n"
				+ "    left outer join   \n"
				+ "     (select mail_info.fk_enterprise_id as mail_ent_id, sum(mail_info.num) as mail_received_num  \n"
				+ "       from pe_bzz_batch b3,pe_bzz_mail_info mail_info inner join enum_const const on mail_info.flag_mail_send_state = const.id   \n"
				+ "       where const.name='已收' and b3.id=mail_info.FK_BATCH_ID and b3.recruit_selected='1'  \n"
				+ "       group by mail_info.fk_enterprise_id) rs11  \n"
				+ "    on rs10.ent_id = rs11.mail_ent_id  \n"
				+ "  where 1=1";
		StringBuffer buffer = new StringBuffer(tempsql);
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			String name = entry.getKey().toString();
			if (name.startsWith("search__")) {
				n++;
				String value = ((String[]) entry.getValue())[0];
				if (value == "" || "".equals(value)) {
					k++;
				} else {
					name = name.substring(8);
					if(name.toUpperCase().equals("NAME")) {
						
						name="name";
						buffer.append(" and rs10." + name + " like '%" + value + "%'");
					} else if(name.toUpperCase().equals("PEENTERPRISE.NAME")) {
						
						name="parent_name";
						buffer.append(" and rs10." + name + " = '" + value + "'");
					} else {
						
						buffer.append(" and rs10." + name + " like '%" + value + "%'");
					}
				}
			}
		}
		if (this.getSort() == null || this.getSort().equals("id")) {
			this.setSort("rs10.bmnum desc,rs10.zcnum");
			this.setDir("desc");
		}
		String js = null;
		if (k - n == 0 ? true : false) {
			js = super.abstractList();
		} else {
			initGrid();
			Page page = null;
			this.setSqlCondition(buffer);
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
	@Override
	public void setSqlCondition(StringBuffer sql,String groupBy){
		ActionContext context = ActionContext.getContext(); 
		Map params = context.getParameters();
		//为支持2009-01-13之前所写的sql，特加上如下处理
		this.arrangeSQL(sql, params);
		//setCondition(sql,params);
		if(groupBy != null && !"".equals(groupBy)){
			sql.append(groupBy);
		}
		/**
		 * 对于表中含有下划线"_"的字段暂不支持排序
		 * (如果需要对含下划线的字段也支持排序，则命名时要求命名为与数据库字段名相同)
		 */
		String temp = this.getSort();
		//截掉前缀 Combobox_PeXxxxx.
		if(temp != null && temp.indexOf(".") > 1){
			if(temp.toLowerCase().startsWith("combobox_")){
				temp = temp.substring(temp.indexOf(".") + 1);
			}
		}
		if (this.getSort() != null && temp != null) {

			if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
				if(temp.toUpperCase().equals("PEENTERPRISE.NAME")) {
					sql.append(" order by name desc ");
				} else {
					sql.append(" order by " +temp+ " desc ");
				}
			} else{
				if(temp.toUpperCase().equals("PEENTERPRISE.NAME")) {
					sql.append(" order by name asc ");
				} else {
					sql.append(" order by " +temp+ " asc ");
				}
			}
				
		} else {
			sql.append(" order by id desc");
		}
	}
	private void arrangeSQL(StringBuffer sql,Map params){
		
		Iterator iterator = params.entrySet().iterator();
		
		do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String name = entry.getKey().toString();
            String value = ((String[])entry.getValue())[0].toString();
            if(!name.startsWith("search__"))
            	continue;
            
            if(name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")){
            	name=name.substring(name.lastIndexOf(".", name.lastIndexOf(".")-1)+1);
			}else{
				name=name.substring(8);
			}
            
            if(name.toLowerCase().startsWith("combobox_")){
            	sql.insert(0, "select * from (");
        		sql.append(") t where 1 = 1 ");
        		return;
            }
        }while(true);
	}

	public Page list() {
		Page page = null;

		String sql = " select rs10.ent_id as id, rs10.code as code, rs10.name as name, rs10.parent_name, rs10.bmnum, rs10.jfnum, rs10.zcnum, rs10.dcnum, rs10.zknum, rs10.mailnum, nvl(rs11.mail_received_num,0) as mail_received_num \n"
			+ this.getRedundancyField(32)  //为init_grid添加字段，以匹配grid字段的个数
			+ " from  \n"
			+ "   (select rs8.*, nvl(rs9.mail_num,0) as mailnum  \n"
			+ "    from  \n"
			+ "        (select rs6.*, nvl(rs7.zknum,0) as zknum  \n"
			+ "         from  \n"
			+ "          (select rs4.*, nvl(rs5.dcnum,0) as dcnum  \n"
			+ "           from  \n"
			+ "            (select rs2.*, nvl(rs3.zcnum,0) as zcnum  \n"
			+ "             from  \n"
			+ "                 (select a.* ,nvl(b.jfnum,0) as jfnum  \n"
			+ "                 from \n"
			+ "                 (select a.code as code,a.ent_id as ent_id, a.ent_name as name, a.parent_name as parent_name, nvl(a1.bmnum,0) as bmnum  \n"
			+ "                  from  \n"
			+ "                  ( select ent.code as code,ent.id as ent_id, ent.name as ent_name, ent1.name as parent_name \n"
			+ "                    from  Pe_Enterprise ent left outer join pe_enterprise ent1 on ent1.id = ent.fk_parent_id \n"
			+ "                    where ent.fk_parent_id is not null \n"
			+ "                   ) a  \n"
			+ "                   left outer join \n"
			+ "                   (select recruit.fk_enterprise_id as ent_id, count(recruit.id) as bmnum \n"
			+ "                    from pe_bzz_recruit recruit inner join pe_bzz_batch b5 on recruit.fk_batch_id = b5.id \n"
			+ "                    where b5.recruit_selected='1' \n"
			+ "                    group by recruit.fk_enterprise_id) a1 \n"
			+ "                    on a.ent_id = a1.ent_id) a \n"
			+ "                  left outer join  \n"
			+ "                  ( select fax.fk_enterprise_id as fax_ent_id, sum(fax.num) as jfnum  \n"
			+ "                    from pe_bzz_fax_info fax,enum_const const ,pe_bzz_batch b1  \n"
			+ "                    where fax.flag_fp_open_state=const.id and fax.fk_batch_id=b1.id and b1.recruit_selected='1' --and const.name='已开' \n"
			+ "                    group by  fax.fk_enterprise_id) b   \n"
			+ "                  on a.ent_id=b.fax_ent_id) rs2  \n"
			+ "                left outer join  \n"
			+ "                ( select stu.fk_enterprise_id as stu_ent_id, count(stu.reg_no) as zcnum  \n"
			+ "                  from pe_bzz_student stu inner join  pe_bzz_batch batch on stu.fk_batch_id = batch.id  \n"
			+ "                  where batch.recruit_selected='1'  and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'  \n"
			+ "                  group by stu.fk_enterprise_id) rs3  \n"
			+ "                on rs2.ent_id=rs3.stu_ent_id) rs4  \n"
			+ "             left outer join   \n"
			+ "               (select  stu.fk_enterprise_id as stu_ent_id, count(stu.reg_no) as dcnum  \n"
			+ "                from pe_bzz_student stu inner join  pe_bzz_batch batch on stu.fk_batch_id = batch.id  \n"
			+ "                where batch.recruit_selected='1' and stu.export_state='1' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
			+ "                group by stu.fk_enterprise_id) rs5  \n"
			+ "             on rs4.ent_id = rs5.stu_ent_id) rs6   \n"
			+ "          left outer join  \n"
			+ "            (select zk_info.fk_enterprise_id as zk_ent_id, sum(zk_info.received_person_num) as zknum  \n"
			+ "             from pe_bzz_zk_info zk_info inner join pe_bzz_batch batch on zk_info.fk_batch_id = batch.id  \n"
			+ "             where batch.recruit_selected='1'  \n"
			+ "             group by zk_info.fk_enterprise_id) rs7  \n"
			+ "          on rs6.ent_id=rs7.zk_ent_id) rs8   \n"
			+ "       left outer join   \n"
			+ "         (select mail_info.fk_enterprise_id as mail_ent_id, sum(mail_info.num) as mail_num  \n"
			+ "          from pe_bzz_mail_info mail_info , pe_bzz_batch b2  \n"
			+ "          where b2.id=mail_info.FK_BATCH_ID and b2.recruit_selected='1' \n" 
			+ "          group by mail_info.fk_enterprise_id) rs9  \n"
			+ "       on rs8.ent_id = rs9.mail_ent_id) rs10  \n"
			+ "    left outer join   \n"
			+ "     (select mail_info.fk_enterprise_id as mail_ent_id, sum(mail_info.num) as mail_received_num  \n"
			+ "       from pe_bzz_batch b3,pe_bzz_mail_info mail_info inner join enum_const const on mail_info.flag_mail_send_state = const.id   \n"
			+ "       where const.name='已收' and b3.id=mail_info.FK_BATCH_ID and b3.recruit_selected='1'  \n"
			+ "       group by mail_info.fk_enterprise_id) rs11  \n"
			+ "    on rs10.ent_id = rs11.mail_ent_id  \n"
			+ "  where 1=1";

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
			dc.createCriteria("peBzzBatch", "peBzzBatch");
			
			dc.add(Restrictions.eq("peBzzBatch.recruitSelected", "1"));
			dc.add(Restrictions.eq("peEnterprise.id", this.getEId()));

			this.stuList = this.getGeneralService().getDetachList(dc);

			updatePeBzzStudent(stuList);// 导出状态置'1'

			dc = DetachedCriteria.forClass(PeBzzStudent.class);
			dc.createCriteria("peEnterprise", "peEnterprise");
			dc.createCriteria("peEnterprise.peEnterprise", "peEnterprise.peEnterprise");
			dc.createCriteria("peBzzBatch", "peBzzBatch");
			dc.createCriteria("ssoUser", "ssoUser");
			
			dc.add(Restrictions.eq("peBzzBatch.recruitSelected", "1"));
			dc.add(Restrictions.eq("peEnterprise.id", this.getEId()));
			dc.addOrder(Order.desc("exportDate"));
			
			this.stuList = this.getGeneralService().getDetachList(dc);
			dc = null;

		} catch (EntityException e) {
			e.printStackTrace();
		}

		return "exportExcel";
	}
	
	/**
	 * action 导出邮寄地址
	 * @return 
	 */
	public String exportAddress() {
		Map<String, String[]> map = ServletActionContext.getRequest().getParameterMap();
		Set<String> kSey = map.keySet();
		for(String key : kSey) {
			String[] vals = (String[])map.get(key);
			for(String val : vals) {
				System.out.println("<"+key+","+val+">");
			}
		}
		return "exportAddressExcel";
	}

	private void updatePeBzzStudent(List<PeBzzStudent> stuList) {

		if (stuList == null || stuList.size() == 0 || stuList.get(0) == null) {
			return;
		}
		String ent = this.getPeEnterprise().getId();
		String batch = stuList.get(0).getPeBzzBatch().getId();

		try {
			UserSession us = (UserSession)ServletActionContext
			.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			if (us == null) {
				throw new EntityException("无操作权限");
			}
			String exportPeople=us.getLoginId();
			
			String sql = "update pe_bzz_student set export_state='1', " +
					"export_date=to_date('"+this.dateFormat(new Date())+"','yyyy-mm-dd'), " +
					"export_people='" + exportPeople + "'" +
					" where export_state='0' and fk_enterprise_id='"
					+ ent + "' and fk_batch_id ='" + batch + "'";
			int n =this.getGeneralService().executeBySQL(sql);
			n = n;
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	private String dateFormat(Date date) {
		if(date == null) {
			return null;
		}
		
  		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
  		return sf.format(date);
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
		if (stuList != null && stuList.size() != 0 && stuList.get(0) != null) {

			return this.peEnterprise = stuList.get(0).getPeEnterprise();
		}

		try {

			this.peEnterprise = this.getGeneralService().getById(this.getEId());
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return this.peEnterprise;
	}

	public int getStuCount() {
		if (stuList != null && stuList.size() != 0 && stuList.get(0) != null) {

			return stuCount = stuList.size();
		}

		return stuCount = 0;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}
	
	/**
	 * 为SQL添加冗余的字段，以匹配init_grid中不显示字段的个数
	 * @param n
	 * @return
	 */
	private String getRedundancyField(int n) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < n ; i++) {
			sb.append(",'field").append(i).append("' as f").append(i);
		}
		return sb.toString();
	}

}