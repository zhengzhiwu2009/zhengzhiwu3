package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.service.basic.PeEnterpriseBacthService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzRecruit;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * @param
 * @version 创建时间：2009-6-18 上午10:07:47
 * @author gy
 * @return
 * @throws PlatformException
 *             类说明
 */
public class PeEnterpriseAction extends MyBaseAction<PeEnterprise> {

	

	private PeEnterpriseBacthService enterpriseBacthService;

	public PeEnterpriseBacthService getEnterpriseBacthService() {
		return enterpriseBacthService;
	}

	public void setEnterpriseBacthService(
			PeEnterpriseBacthService enterpriseBacthService) {
		this.enterpriseBacthService = enterpriseBacthService;
	}
	
	/**
	 * 初始化列表
	 * @author 魏慧宁
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("组织机构");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("单位名称"), "name");
		this.getGridConfig().addColumn(this.getText("机构代码"), "code",true,true,true,Const.code_for_extjs);
		
		this.getGridConfig().addColumn(this.getText("办公地址"), "address", false,
				true, true, "TextField",true,150);
		this.getGridConfig().addColumn(this.getText("从业人员规模"), "num", false,
				false, true, "");
//		this.getGridConfig().addColumn(this.getText("是否注册"), "status", false,
//				false, true, "");
		this.getGridConfig().addColumn(this.getText("负责人姓名"), "fzrName", false,
				true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("负责人性别"), "fzrXb", false,
				true, false, "TextField",true,5);
		this.getGridConfig().addColumn(this.getText("负责人部门"), "fzrDepart",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("负责人职务"), "fzrPosition",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("负责人办公电话"), "fzrPhone",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("负责人移动电话"), "fzrMobile",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("负责人电子邮件"), "fzrEmail",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("负责人通讯地址"), "fzrAddress",
				false, true, false, "TextField",true,150);
		this.getGridConfig().addColumn(this.getText("联系人姓名"), "lxrName", false,
				true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("联系人性别"), "lxrXb", false,
				true, false, "TextField",true,5);
		this.getGridConfig().addColumn(this.getText("联系人部门"), "lxrDepart",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("联系人职务"), "lxrPosition",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("联系人办公电话"), "lxrPhone",
				false, true, false,"TextField",true,25);
		this.getGridConfig().addColumn(this.getText("联系人移动电话"), "lxrMobile",
				false, true, false,"TextField",true,25);
		this.getGridConfig().addColumn(this.getText("联系人电子邮件"), "lxrEmail",
				false, true, false, "TextField",true,25);
		this.getGridConfig().addColumn(this.getText("联系人通讯地址"), "lxrAddress",
				false, true, false, "TextField",true,150);
		
		/*
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("学员信息"),
						"{return '<a href=peDetail.action?id='+record.data['id']+'&type=enterprise>查看</a>';}",
						"");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("详细信息"),
						"{return '<a href=peDetail_viewDetail.action?id='+record.data['id']+'&num='+record.data['num']+'>查看</a>';}",
						"");*/
		this
		.getGridConfig()
		.addRenderScript(
				this.getText("单位信息"),
				"{return '<a href=peDetail_viewDetail.action?id='+record.data['id']+'&num='+record.data['num']+'>查看</a>';}",
				"");
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("下属二级机构单位	"),
						"<a href=\"peSubEnterprise.action?bean.peEnterprise.id=${value}\">查看</a>","id");
		if (us.getUserLoginType().equals("3")) {
			String javaScript="{Ext.Ajax.request({url: '"+this.getServletPath()+"_electiveOff.action',"
			   + "success: function(){Ext.MessageBox.alert('Status','操作成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');}, "
			   + "failure: function(){Ext.MessageBox.alert('Status','操作失败&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');} });}";

//			this.getGridConfig().addMenuScript(
//					this.getText("将无学员的企业设为无效"),javaScript);

		}
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeEnterprise.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peEnterprise";
	}

	public void setBean(PeEnterprise instance) {
		super.superSetBean(instance);
	}

	public PeEnterprise getBean() {
		return super.superGetBean();
	}	

	/**
	 * 取得管理员类型.总站管理员不限制
	 * @author 魏慧宁
	 * @return
	 */
	public String batch() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";// 保存管理员类型
		if (us != null) {
			userLoginType = us.getUserLoginType();
		}
		if (userLoginType.equals("3")) {
			return "batch";
		} else {
			this.setMsg("您的权限不够，无法进行此项操作!!!!");
		}
		return "msg";
	}
	
	/**
	 * 废弃
	 * @author 魏慧宁
	 * @return
	 */
	public String electiveOff() {
		String sql="update pe_enterprise pe set pe.flag_elective='402880f827f5b99b0127f5bdadc70008' \n"
			+ "where pe.flag_elective='402880f827f5b99b0127f5bdadc70007' and id in( \n"
			+ "select id from ( \n"
			+ "select pe.id,count(ps.id) as ct from pe_enterprise pe left outer join pe_bzz_student ps on ps.fk_enterprise_id=pe.id \n"
			+ "where pe.fk_parent_id is not null \n"
			+ "group by pe.id ) \n"
			+ "where ct=0)";
		try {
			this.getGeneralService().executeBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}
	
	/**
	 * 重写框架方法：删除数据
	 * @author weihuining
	 * @return
	 */
	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				DetachedCriteria criteria = DetachedCriteria.forClass(PeEnterprise.class);
				criteria.createCriteria("peEnterprise", "peEnterprise");
				criteria.add(Restrictions.in("peEnterprise.id", ids));
				
				DetachedCriteria studc = DetachedCriteria.forClass(PeBzzStudent.class);
				studc.createCriteria("peEnterprise", "peEnterprise");
				studc.add(Restrictions.in("peEnterprise.id", ids));
				
				/*DetachedCriteria recdc = DetachedCriteria.forClass(PeBzzRecruit.class);
				recdc.add(Restrictions.in("peEnterprise.id", ids));*/
				
				
				try {
					List<PeEnterprise> plist = this.getGeneralService().getList(criteria);
					List<PeBzzStudent> stulist = this.getGeneralService().getList(studc);
//					List<PeBzzRecruit> recruitList = this.getGeneralService().getList(recdc);
					if(plist.size()>0||stulist.size()>0){
						map.clear();
						map.put("success", "false");
						map.put("info", "该企业存在相关联的下级企业或者学员信息,无法删除!");
						return map;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
				try{
//					this.getGeneralService().deleteByIds(idList);
					UserSession us = (UserSession) ServletActionContext.getRequest()
					.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
					List<PeEnterprise> priList = us.getPriEnterprises();

					List siteList=new ArrayList();
					
					for(int i=0;i<priList.size();i++)
					{
						siteList.add(priList.get(i).getId());
					}
					
					int i = this.getGeneralService().deleteByIds(this.getEntityClass(), siteList, idList);
					map.put("success", "true");
					map.put("info", "删除成功");
					if(0 == i){
						map.clear();
						map.put("success", "false");
						map.put("info", "对不起，您的没有删除的权限");
					}
				}catch(RuntimeException e){
					return this.checkForeignKey(e);
				}catch(Exception e1){
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}

	/**
	 * 重写框架方法：机构信息
	 * @author weihuining
	 * @return
	 */
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria enterdc = DetachedCriteria
				.forClass(PeEnterprise.class);
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
	/*public String abstractList() {
		super.saveDetachedCriteriaInSession(null);
		ActionContext context = ActionContext.getContext();
		Map map1 = context.getParameters();
		Iterator it  = map1.entrySet().iterator();
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql_con="";
		if (!us.getUserLoginType().equals("3")) {
			List<PeEnterprise> priList = us.getPriEnterprises();
			String tc = "";
			for(PeEnterprise e : priList) {
				if(e.getPeEnterprise() == null) {
					tc += "'" + e.getId() + "',";
				} else {
					tc += "'" + e.getPeEnterprise().getId() + "',";
				}
			}
			tc = "(" + tc.substring(0, tc.length()-1) + ")";
//			System.out.println("----------tc: " + tc);
			sql_con=" and pe.id in " + tc;
		}
		
		String[] val = (String[])map1.get("search__peBzzBatch.name");
		String sql_con1 = "";
		String sql_con2 = "";
		if(val != null && val.length > 0 && val[0]!=null && !val[0].equals("")) {
			sql_con1=" and b.name='"+val[0]+"' ";
			sql_con2=" and one.num>0 ";
		} 
		int k=0;
		int n=0;
		String tempsql = " select one.*  from (select pe.id as id, pe.name as name, pe.code as code, pe.industry as industry, pe.address as address, pe.zipcode as zipcode,\n"
			+ "                pe.fax as fax,pe.info_sheng as infoSheng,pe.info_shi as infoShi,pe.info_jiedao as infoJiedao, pe.fzr_name as fzrName, pe.fzr_xb as fzrXb, pe.fzr_depart as fzrDepart, pe.fzr_position as fzrPosition, pe.fzr_phone as fzrPhone,\n"
			+ "                pe.fzr_mobile as fzrMobile,  pe.fzr_email as fzrEmail, pe.fzr_address as fzrAddress, pe.lxr_name as lxrName,pe.lxr_xb as lxrXb, pe.lxr_depart as lxrDepart ,\n"
			+ "                pe.lxr_position as lxrPosition, pe.lxr_phone as lxrPhone, pe.lxr_mobile as lxrMobile, pe.lxr_email as lxrEmail,\n"
			+ "                pe.lxr_address as lxrAddress, count(pp.sid) as num ,'batch_id' from (select s.id as sid, p.id as pid, p.name, p.code, p.industry,\n"
			+ "                 p.address, p.zipcode, p.fax,p.info_sheng,p.info_shi,p.info_jiedao, p.fzr_name, p.fzr_xb, p.fzr_depart, p.fzr_position, p.fzr_phone, p.fzr_mobile, p.fzr_email,p.fzr_address,\n"
			+ "                 p.lxr_name, p.lxr_xb, p.lxr_depart, p.lxr_position, p.lxr_phone, p.lxr_mobile, p.lxr_email, p.lxr_address from pe_bzz_student s, pe_enterprise p,sso_user su,pe_bzz_batch b\n"
			+ "                 where p.fk_parent_id is null and s.fk_enterprise_id = p.id and s.fk_sso_user_id=su.id and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_batch_id=b.id " +sql_con1+
					"union" +
					" select s.id as sid, p.id as pid,p.name, p.code, p.industry,\n"
			+ "                 p.address,p.zipcode, p.fax, p.info_sheng,p.info_shi,p.info_jiedao,p.fzr_name, p.fzr_xb, p.fzr_depart, p.fzr_position, p.fzr_phone, p.fzr_mobile, p.fzr_email, p.fzr_address,\n"
			+ "                 p.lxr_name, p.lxr_xb, p.lxr_depart, p.lxr_position, p.lxr_phone, p.lxr_mobile, p.lxr_email, p.lxr_address from pe_bzz_student s, pe_enterprise p, pe_enterprise p1,sso_user su,pe_bzz_batch b \n"
			+ "                 where p.fk_parent_id is null and p1.fk_parent_id = p.id and p1.id = s.fk_enterprise_id and s.fk_sso_user_id=su.id and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_batch_id=b.id "+sql_con1+") pp right outer join pe_enterprise pe on pe.id = pp.pid\n"
			+ "                 where pe.fk_parent_id is null "+sql_con+" group by pe.code,pe.name,pe.industry,\n"
			+ "                 pe.address, pe.zipcode,pe.fax, pe.info_sheng,pe.info_shi,pe.info_jiedao,pe.id, pe.fzr_name, pe.fzr_xb , pe.fzr_depart, pe.fzr_position,\n"
			+ "                 pe.fzr_phone , pe.fzr_mobile, pe.fzr_email , pe.fzr_address,\n"
			+ "                 pe.lxr_name ,pe.lxr_xb , pe.lxr_depart , pe.lxr_position , pe.lxr_phone, pe.lxr_mobile ,\n"
			+ "                 pe.lxr_email , pe.lxr_address ) one  where 1=1 "+sql_con2 ;
		StringBuffer buffer = new StringBuffer(tempsql);
		while(it.hasNext()){
			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
			String name = entry.getKey().toString();
			if(name.startsWith("search__")){
				n++;
				if(name.endsWith("peBzzBatch.name")) {
					it.remove();
					continue;
				}
				String value = ((String[])entry.getValue())[0];
				if(value==""||"".equals(value)){
					k++;
				}else{
					name = name.substring(8);
					
					buffer.append(" and " + name + " like '%"+value+"%'");
				}
			}
		}
		String js =null;
		if(k-n==0?true:false){
			js = super.abstractList();
		}else{
			initGrid();
			Page page = null;
			setSqlCondition(buffer);
			String sql = buffer.toString();
			try {
				page = this.getGeneralService().getByPageSQL(sql, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
				List jsonObjects = JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig());
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
		ActionContext context = ActionContext.getContext();
		Map map1 = context.getParameters();
		Iterator it  = map1.entrySet().iterator();
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql_con="";
		if (!us.getUserLoginType().equals("3")) {
			List<PeEnterprise> priList = us.getPriEnterprises();
			String tc = "";
			for(PeEnterprise e : priList) {
				if(e.getPeEnterprise() == null) {
					tc += "'" + e.getId() + "',";
				} else {
					tc += "'" + e.getPeEnterprise().getId() + "',";
				}
			}
			tc = "(" + tc.substring(0, tc.length()-1) + ")";
//			System.out.println("----------tc: " + tc);
			sql_con=" and pe.id in " + tc;
		}
		
		String[] val = (String[])map1.get("search__peBzzBatch.name");
		String sql_con1 = "";
		String sql_con2 = "";
		if(val != null && val.length > 0 && val[0]!=null && !val[0].equals("")) {
			sql_con1=" and b.name='"+val[0]+"' ";
			sql_con2=" and one.num>0 ";
		} 
		int k=0;
		int n=0;
		
		Page page = null;
		String sql = " select one.*  from (select pe.id as id, pe.name as name, pe.code as code, pe.industry as industry, pe.address as address, pe.zipcode as zipcode,\n"
			+ "                pe.fax as fax,pe.info_sheng as infoSheng,pe.info_shi as infoShi,pe.info_jiedao as infoJiedao, pe.fzr_name as fzrName, pe.fzr_xb as fzrXb, pe.fzr_depart as fzrDepart, pe.fzr_position as fzrPosition, pe.fzr_phone as fzrPhone,\n"
			+ "                pe.fzr_mobile as fzrMobile,  pe.fzr_email as fzrEmail, pe.fzr_address as fzrAddress, pe.lxr_name as lxrName,pe.lxr_xb as lxrXb, pe.lxr_depart as lxrDepart ,\n"
			+ "                pe.lxr_position as lxrPosition, pe.lxr_phone as lxrPhone, pe.lxr_mobile as lxrMobile, pe.lxr_email as lxrEmail,\n"
			+ "                pe.lxr_address as lxrAddress, count(pp.sid) as num ,'batch_id' from (select s.id as sid, p.id as pid, p.name, p.code, p.industry,\n"
			+ "                 p.address, p.zipcode, p.fax,p.info_sheng,p.info_shi,p.info_jiedao, p.fzr_name, p.fzr_xb, p.fzr_depart, p.fzr_position, p.fzr_phone, p.fzr_mobile, p.fzr_email,p.fzr_address,\n"
			+ "                 p.lxr_name, p.lxr_xb, p.lxr_depart, p.lxr_position, p.lxr_phone, p.lxr_mobile, p.lxr_email, p.lxr_address from pe_bzz_student s, pe_enterprise p,sso_user su,pe_bzz_batch b\n"
			+ "                 where p.fk_parent_id is null and s.fk_enterprise_id = p.id and s.fk_sso_user_id=su.id and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_batch_id=b.id " +sql_con1+
					"union" +
					" select s.id as sid, p.id as pid,p.name, p.code, p.industry,\n"
			+ "                 p.address,p.zipcode, p.fax, p.info_sheng,p.info_shi,p.info_jiedao,p.fzr_name, p.fzr_xb, p.fzr_depart, p.fzr_position, p.fzr_phone, p.fzr_mobile, p.fzr_email, p.fzr_address,\n"
			+ "                 p.lxr_name, p.lxr_xb, p.lxr_depart, p.lxr_position, p.lxr_phone, p.lxr_mobile, p.lxr_email, p.lxr_address from pe_bzz_student s, pe_enterprise p, pe_enterprise p1,sso_user su,pe_bzz_batch b \n"
			+ "                 where p.fk_parent_id is null and p1.fk_parent_id = p.id and p1.id = s.fk_enterprise_id and s.fk_sso_user_id=su.id and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_batch_id=b.id "+sql_con1+") pp right outer join pe_enterprise pe on pe.id = pp.pid\n"
			+ "                 where pe.fk_parent_id is null "+sql_con+" group by pe.code,pe.name,pe.industry,\n"
			+ "                 pe.address, pe.zipcode,pe.fax, pe.info_sheng,pe.info_shi,pe.info_jiedao,pe.id, pe.fzr_name, pe.fzr_xb , pe.fzr_depart, pe.fzr_position,\n"
			+ "                 pe.fzr_phone , pe.fzr_mobile, pe.fzr_email , pe.fzr_address,\n"
			+ "                 pe.lxr_name ,pe.lxr_xb , pe.lxr_depart , pe.lxr_position , pe.lxr_phone, pe.lxr_mobile ,\n"
			+ "                 pe.lxr_email , pe.lxr_address ) one  where 1=1 "+sql_con2 ;
		
		try {
			StringBuffer sql_temp = new StringBuffer(sql);
			//this.setSort("code");
			while(it.hasNext()){
				java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
				String name = entry.getKey().toString();
				if(name.startsWith("search__")){
					n++;
					if(name.endsWith("peBzzBatch.name")) {
						it.remove();
						continue;
					}
					String value = ((String[])entry.getValue())[0];
					if(value==""||"".equals(value)){
						k++;
					}else{
						name = name.substring(8);
						
						sql_temp.append(" and " + name + " like '%"+value+"%'");
					}
				}
			}
			this.setSqlCondition(sql_temp);
			page = this.getGeneralService().getByPageSQL(sql_temp.toString(),Integer.parseInt(this.getLimit()),Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	*/

/*	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql_con="";
		
		if (!us.getUserLoginType().equals("3")) {
			List<PeEnterprise> priList = us.getPriEnterprises();
			String tc = "";
			for(PeEnterprise e : priList) {
				if(e.getPeEnterprise() == null) {
					tc += "'" + e.getId() + "',";
				} else {
					tc += "'" + e.getPeEnterprise().getId() + "',";
				}
			}
			tc = "(" + tc.substring(0, tc.length()-1) + ")";
			//System.out.println("----------tc: " + tc);
			sql_con=" and pe.id in " + tc;
		}
		
		Page page = null;
		String sql = " select one.*  from (select pe.id as id, pe.name as name, pe.code as code, pe.industry as industry, pe.address as address, pe.zipcode as zipcode,\n"
				+ "                pe.fax as fax,pe.info_sheng as infoSheng,pe.info_shi as infoShi,pe.info_jiedao as infoJiedao, pe.fzr_name as fzrName, pe.fzr_xb as fzrXb,pe.fzr_depart as fzrDepart, pe.fzr_position as fzrPosition, pe.fzr_phone as fzrPhone,\n"
				+ "                pe.fzr_mobile as fzrMobile,  pe.fzr_email as fzrEmail, pe.fzr_address as fzrAddress, pe.lxr_name as lxrName,pe.lxr_xb as lxrXb,pe.lxr_depart as lxrDepart,\n"
				+ "                pe.lxr_position as lxrPosition, pe.lxr_phone as lxrPhone, pe.lxr_mobile as lxrMobile, pe.lxr_email as lxrEmail,\n"
				+ "                pe.lxr_address as lxrAddress, count(pp.sid) as num ,'batch_id' from (select s.id as sid, p.id as pid, p.name, p.code, p.industry,\n"
				+ "                 p.address, p.zipcode, p.fax,p.info_sheng,p.info_shi,p.info_jiedao, p.fzr_name, p.fzr_xb,p.fzr_depart, p.fzr_position, p.fzr_phone, p.fzr_mobile, p.fzr_email,p.fzr_address,\n"
				+ "                 p.lxr_name, p.lxr_xb,p.lxr_depart, p.lxr_position, p.lxr_phone, p.lxr_mobile, p.lxr_email, p.lxr_address from pe_bzz_student s, pe_enterprise p,sso_user su \n"
				+ "                 where p.fk_parent_id is null and s.fk_enterprise_id = p.id and s.fk_sso_user_id=su.id and su.flag_isvalid='2' union select s.id as sid, p.id as pid,p.name, p.code, p.industry,\n"
				+ "                 p.address,p.zipcode, p.fax,p.info_sheng,p.info_shi,p.info_jiedao, p.fzr_name, p.fzr_xb,p.fzr_depart, p.fzr_position, p.fzr_phone, p.fzr_mobile, p.fzr_email, p.fzr_address,\n"
				+ "                 p.lxr_name, p.lxr_xb,p.lxr_depart, p.lxr_position, p.lxr_phone, p.lxr_mobile, p.lxr_email, p.lxr_address from pe_bzz_student s, pe_enterprise p, pe_enterprise p1,sso_user su \n"
				+ "                 where p.fk_parent_id is null and p1.fk_parent_id = p.id and p1.id = s.fk_enterprise_id and s.fk_sso_user_id=su.id and su.flag_isvalid='2') pp right outer join pe_enterprise pe on pe.id = pp.pid\n"
				+ "                 where pe.fk_parent_id is null "+sql_con+" group by pe.code,pe.name,pe.industry,\n"
				+ "                 pe.address, pe.zipcode,pe.fax, pe.info_sheng,pe.info_shi,pe.info_jiedao ,pe.id, pe.fzr_name, pe.fzr_xb , pe.fzr_depart , pe.fzr_position,\n"
				+ "               	pe.fzr_phone , pe.fzr_mobile, pe.fzr_email , pe.fzr_address,\n"
				+ "                 pe.lxr_name ,pe.lxr_xb , pe.lxr_depart, pe.lxr_position , pe.lxr_phone, pe.lxr_mobile ,\n"
				+ "                 pe.lxr_email , pe.lxr_address ) one" ;

		try {
			StringBuffer sql_temp = new StringBuffer(sql);
			//this.setSort("code");
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
	}*/

	/**
	 * 框架方法：或者查询列表所需数据
	 * @author weihuining
	 * @return
	 */
	public DetachedCriteria initDetachedCriteria() {

		/*UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);*/

		DetachedCriteria criteria = DetachedCriteria
				.forClass(PeEnterprise.class);
		/*if (us.getUserLoginType().equals("2"))
			criteria.add(Restrictions.eq("id", us.getPriEnterprises().get(0)
					.getId()));*/
		criteria.createCriteria("peEnterprise", "peEnterprise",
				DetachedCriteria.LEFT_JOIN);
		criteria.add(Restrictions.isNull("peEnterprise.id"));
		return criteria;
	}
	
	/**
	 * 重写框架方法：添加数据前的校验
	 * @author weihuining
	 * @return
	 */
	public void checkBeforeAdd() throws EntityException {
		DetachedCriteria criteria = DetachedCriteria
		.forClass(PeEnterprise.class);
		criteria.add(Restrictions.eq("code", this.getBean().getCode()));
		List pList=null;
		pList=this.getGeneralService().getList(criteria);
		if (pList!=null && pList.size()>0) {
			throw new EntityException("该机构编号已经存在，请重新输入新编号");
		}
		
		//判断企业名称不重复
		criteria = DetachedCriteria.forClass(PeEnterprise.class);
		criteria.add(Restrictions.eq("name", this.getBean().getName()));
		pList = this.getGeneralService().getList(criteria);
		if(pList != null && pList.size() > 0) {
			throw new EntityException("该机构名称已经存在，请重新输入机构名称");
		}
		this.getBean().setEnumConstByFlagEnterpriseState(getDefaultFlagElective());
	}
	
	private EnumConst getDefaultFlagElective() {
		EnumConst ec = new EnumConst();
		ec.setId("402880f827f5b99b0127f5bdadc70007");
		return ec;
	}
	
	/**
	 * 重写框架方法：更细数据前的校验
	 * @author weihuining
	 * @return
	 */
	public void checkBeforeUpdate() throws EntityException {
		String sql="select id from pe_enterprise where code='"+this.getBean().getCode()+"'";
		List pList=null;
		pList=this.getGeneralService().getBySQL(sql);
		String id="";
		if(pList!=null && pList.size()>0)
			id = pList.get(0).toString();
		if (pList!=null && id!=null && pList.size()>0 && !id.equals(this.getBean().getId())) {
			throw new EntityException("该机构编号已经存在，请重新输入新编号");
		}
		
		//判断企业名称不重复
		DetachedCriteria criteria = DetachedCriteria.forClass(PeEnterprise.class);
		criteria.add(Restrictions.eq("id", this.getBean().getId()));
		criteria.setProjection(Property.forName("code"));
		List codeList = this.getGeneralService().getList(criteria);
		
		criteria = DetachedCriteria.forClass(PeEnterprise.class);
		criteria.add(Restrictions.eq("name", this.getBean().getName()));
		criteria.add(Restrictions.not(Restrictions.in("code", codeList)));
		pList = this.getGeneralService().getList(criteria);
		if(pList != null && pList.size() > 0) {
			PeEnterprise e = (PeEnterprise) pList.get(0);
			
			throw new EntityException("该机构名称已经存在，请重新输入机构名称");
		}
	}
	
	
}
