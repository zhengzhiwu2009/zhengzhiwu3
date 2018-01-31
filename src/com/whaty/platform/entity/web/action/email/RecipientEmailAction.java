package com.whaty.platform.entity.web.action.email;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.CoursePeriodStudent;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeElectiveCoursePeriod;
import com.whaty.platform.entity.bean.SiteEmail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class RecipientEmailAction extends MyBaseAction{
	private String id;
	private SiteEmail siteEmail;
	private String temp; //判断是收件箱查看是1 发件箱是2
	public SiteEmail getSiteEmail() {
		return siteEmail;
	}

	public void setSiteEmail(SiteEmail siteEmail) {
		this.siteEmail = siteEmail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 初始化列表
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addMenuFunction("删除", "del");
		this.getGridConfig().setTitle("收件箱");
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		
		this.getGridConfig().addColumn(this.getText("标题"),"title");
		this.getGridConfig().addColumn(this.getText("发件人"),"true_name");
		this.getGridConfig().addColumn(this.getText("开始时间"),"startDate",true, false, false, "");
		this.getGridConfig().addColumn(this.getText("时间"),"sendDate",false, false, true, "");
		this.getGridConfig().addColumn(this.getText("结束时间"),"endDate",true, false, false, "");
		this.getGridConfig().addColumn(this.getText("阅读状态"),"status",false,false,false,"TextField",false,30);
		this.getGridConfig().addRenderScript(this.getText("阅读状态"),"{if(${value}==0) return '未阅读';else return '已阅读';}","status");
		this.getGridConfig().addRenderFunction("查看内容", "<a href=\"/siteEmail/recipientEmail_EmailInfo.action?id=${value}&&temp=1\" target=\"_blank\"><font color=#0000ff>查看内容<font></a>","id");
		ColumnConfig c_name1 = new ColumnConfig(this.getText("所在机构"),
				"combobox_peName",true,false,true,"TextField",true,50,"");
		if (us.getUserLoginType().equals("2") ||us.getUserLoginType().equals("4")) {	
			String sql = 
				"select t.id, t.name as p_name\n" +
				"  from pe_enterprise t\n" + 
				" where t.fk_parent_id in\n" + 
				"       (select s.id\n" + 
				"          from pe_enterprise s\n" + 
				"         inner join pe_enterprise_manager t\n" + 
				"            on s.id = t.fk_enterprise_id\n" + 
				"           and t.login_id = '"+us.getLoginId()+"')\n" + 
				"    or t.id in (select s.id\n" + 
				"                  from pe_enterprise s\n" + 
				"                 inner join pe_enterprise_manager t\n" + 
				"                    on s.id = t.fk_enterprise_id\n" + 
				"                   and t.login_id = '"+us.getLoginId()+"')\n" + 
				" order by t.code";

			c_name1
					.setComboSQL(sql);
		}
		else {
			c_name1.setComboSQL("select t.id,t.name as p_name from pe_enterprise t order by t.code ");
		}
		this.getGridConfig().addColumn(c_name1);
//		this.getGridConfig().addColumn(this.getText("组织机构"),"peName");
		
//		this.getGridConfig().addColumn(this.getText("账号类别"),"roleType");
		boolean isSearch = true;
		if(us.getUserLoginType().equals("3")) {
			isSearch = false;
		}
		ColumnConfig c_name2 = new ColumnConfig(this.getText("账号类别"),
				"combobox_roleType",isSearch,false,true,"TextField",true,50,"");
		String sql = "select id, name from pe_pri_role";
		c_name2.setComboSQL(sql);
		this.getGridConfig().addColumn(c_name2);
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass=SiteEmail.class;
		
	}	
	
	@Override
	public void setServletPath() {
		this.servletPath="/siteEmail/recipientEmail";
		
	}

	/**
	 * 重写框架方法--列更新
	 * @author linjie
	 * @return
	 */
	@Override
	public Map updateColumn() {
		  Map map = new HashMap();
			String action = this.getColumn();
			
				if (this.getIds() != null && this.getIds().length() > 0) {
					String[] ids = getIds().split(",");
					try {
						if(action.equals("del")) {
							DetachedCriteria dc = DetachedCriteria.forClass(SiteEmail.class);
							dc.add(Restrictions.in("id", ids));
							List<SiteEmail> periodList = this.getGeneralService().getList(dc);
							for(SiteEmail siteEmail : periodList) {
								
								siteEmail.setAddresseeDel((long)1);
								this.getGeneralService().save(siteEmail);
							}
						
	
						}
					} catch (EntityException e) {
						map.clear();
						map.put("success", "false");
						map.put("info", "操作失败");
						return map;
					}
					map.clear();
					map.put("success", "true");
					map.put("info", ids.length + "条记录操作成功");
	
				}else{
					map.clear();
					map.put("success", "false");
					map.put("info", "至少一条数据被选择");
					return map;
				}
		
	  return map;
			
	}
	
	/**
	 * 重写框架方法：收件箱信息（带sql条件）
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		Page page=null;
		ActionContext ac=ActionContext.getContext();
		UserSession us=(UserSession)ac.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sql=new StringBuffer();
		//协会管理员因为有发的权限所有 差所有人的
		//if("3".equals(us.getRoleId())){
			sql.append("select * from (");
			sql.append(" select         t.id,                       ");
			sql.append("               t.title,		      ");
			sql.append("               t.true_name as true_name,    ");
//			sql.append("               t.senddate as sendDate,		      ");
			sql.append("				to_date(to_char(sysdate, 'yyyy-mm-dd'), 'yyyy-mm-dd') as startDate,              ");
			sql.append("               to_date(to_char(t.senddate,'yyyy-mm-dd'),'yyyy-mm-dd') as sendDate,		      ");
			sql.append("				to_date(to_char(sysdate, 'yyyy-mm-dd'), 'yyyy-mm-dd') as endDate,              ");
			sql.append("               t.status	,		      ");
			sql.append("               t.combobox_peName   ,              ");
			sql.append("               t.combobox_roleType                 ");
			sql.append("  from (				      ");
			sql.append(" select s.id, s.title, pm.true_name as true_name,  s.senddate,s.status,'--' as combobox_peName, ppr.name as combobox_roleType");
			sql.append("   from site_email s, pe_manager pm, sso_user su, pe_pri_role ppr					    ");
			sql.append("  where s.sender_id = pm.login_id	and s.addressee_del=0 and s.addressee_id='"+us.getLoginId()+"' and  su.fk_role_id = ppr.id and pm.fk_sso_user_id = su.id  				    ");
			sql.append(" union								    ");
			sql.append(" select s.id, s.title, pem.name as true_name,  s.senddate,s.status,pe.name as combobox_peName, pr.name as combobox_roleType		    ");
			sql.append("   from site_email s, pe_enterprise_manager pem, pe_enterprise pe, pe_pri_role pr, sso_user u   ");
			sql.append("  where s.sender_id = pem.login_id	and s.addressee_del=0 and s.addressee_id='"+us.getLoginId()+"'	and pem.fk_enterprise_id=pe.id and pem.fk_sso_user_id=u.id and u.fk_role_id=pr.id     ");
			sql.append(" union								    ");
			sql.append(" select s.id, s.title, ps.true_name,  s.senddate ,s.status,pe.name as combobox_peName, pr.name as combobox_roleType ");
			sql.append("   from site_email s, pe_bzz_student ps	, pe_enterprise_manager pem, pe_enterprise pe, pe_pri_role pr, sso_user u ");
			sql.append("  where s.sender_id = ps.reg_no	 and s.addressee_del=0 and s.addressee_id='"+us.getLoginId()+"'	and ps.fk_enterprise_id=pe.id and ps.fk_sso_user_id = u.id and u.fk_role_id=pr.id");
			sql.append(")t order by t.status");
			sql.append(" )where 1=1 ");
			
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1
						&& name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name
							.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}

				if(name.equals("startDate")) {
					if(value!=null && !"".equals(value)) {
						sql.append(" and sendDate >= to_date('"+value+"', 'yyyy-mm-dd') --开始时间\n ");
					}
				} 
				if(name.equals("endDate")) {
					if(value != null && !"".equals(value)) {
						sql.append(" and sendDate <= to_date('"+value+"', 'yyyy-mm-dd') --结束时间\n ");
					}
				}
				if(name.equals("title") || name.equals("true_name")) {
					if(value != null && !"".equals(value)) {
						sql.append(" and "+name+" like '%"+value+"%'");
					}
				}
				
				if(name.equals("combobox_peName")) {
					if(value != null && !"".equals(value)) {
						sql.append(" and "+name+" = '"+value+"'");
					}
				}
				if("combobox_roleType".equals(name)) {
					if(value != null && !"".equals(value)) {
						sql.append(" and "+name+" = '"+value+"'");
					}
				}

			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 标题 */
				if (temp.equals("title")) {
					temp = "title";
				}
				/* 发件人 */
				if (temp.equals("true_name")) {
					temp = "true_name";
				}
				/* 时间 */
				if (temp.equals("sendDate")) {
					temp = "sendDate";
				}
				/* 所在机构 */
				if (temp.equals("peName")) {
					temp = "peName";
				}
				/* 账号类别 */
				if (temp.equals("roleType")) {
					temp = "roleType";
				}
				/* 阅读状态 */
				if (temp.equals("status")) {
					temp = "status";
				}
				if (temp.equals("id")) {
					temp = "sendDate";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sql.append(" order by " + temp + " desc ");
				} else {
					sql.append(" order by " + temp + " asc ");
				}
			} else {
				sql.append(" order by sendDate desc");
			}
		
		try {
			//this.setSqlCondition(sql);
			page=this.getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
		}
	/**
	 * 查看邮件内容
	 * @author linjie
	 * @return
	 */
	public String EmailInfo(){
		
		String login=null;
		List<SiteEmail> list=null;
		if(id!=null&&!("".equals(id))){
			
		String hql="from SiteEmail s where s.id='"+id+"'";
		try {
			list=this.getGeneralService().getByHQL(hql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			if(list!=null&&list.size()>0){
			  siteEmail=list.get(0);
			  ActionContext ac=ActionContext.getContext();
				UserSession us=(UserSession)ac.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
				StringBuffer sql=new StringBuffer();
				if("1".equals(this.getTemp())||"re".equals(this.getTemp())){
					if("0".equals(siteEmail.getStatus())){
					try {
					this.getGeneralService().executeByHQL("update SiteEmail s set s.status='1' where s.id='"+id+"'");
					} catch (EntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					login=siteEmail.getSenderId();
				}else if("2".equals(this.getTemp())){
					login=siteEmail.getAddresseeId();
				}
					sql.append(" select pm.true_name as true_name ");
					sql.append("   from pe_manager pm	      ");
					sql.append("  where pm.login_id='"+login+"'	      ");
					sql.append(" union			      ");
					sql.append(" select pem.name as true_name     ");
					sql.append("   from pe_enterprise_manager pem ");
					sql.append("  where pem.login_id='"+login+"'      ");
					sql.append(" union			      ");
					sql.append(" select ps.true_name	      ");
					sql.append("   from pe_bzz_student ps	      ");
					sql.append("  where ps.reg_no='"+login+"'	      ");
				try {
					List Namelist=this.getGeneralService().getBySQL(sql.toString());
					if("1".equals(this.getTemp())||"re".equals(this.getTemp())){
						siteEmail.setSenderName((String)Namelist.get(0));
					}else if("2".equals(this.getTemp())){
						siteEmail.setAddresseeName((String)Namelist.get(0));
					}
					
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if("re".equals(getTemp())){
//			if(!(siteEmail.getTitle().startsWith("回复:"))){
//			siteEmail.setTitle("回复:"+siteEmail.getTitle());
//			}
		
			return "re";
		}
		return "EmailInfo";
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
}
