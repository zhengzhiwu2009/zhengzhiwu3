package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.basic.PeBzzGoodMagService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * @param
 * @version 创建时间：2009-7-3 下午04:35:51
 * @return
 * @throws PlatformException
 *             类说明
 */
public class EnterpriseManagerAction extends MyBaseAction<PeEnterpriseManager> {

	private GeneralService peEnterprisemanagerService;
	private String parantId;// 父类机构Id
	private EnumConstService enumConstService;
	private PeBzzGoodMagService peBzzGoodMagService;
	private String enterPage;

	private String organCode;
	private String organName;
	private String enTypeId;
	private List<EnumConst> organTypeList;

	public String getEnterPage() {
		return enterPage;
	}

	public void setEnterPage(String enterPage) {
		this.enterPage = enterPage;
	}

	public PeBzzGoodMagService getPeBzzGoodMagService() {
		return peBzzGoodMagService;
	}

	public void setPeBzzGoodMagService(PeBzzGoodMagService peBzzGoodMagService) {
		this.peBzzGoodMagService = peBzzGoodMagService;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		this.setCanNavigate(true);
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addMenuScript(this.getText("添加一级集体"), "{window.location='enterpriseManager_toAdd.action';}");
		this.clearNavigatePath();
		if (us.getUserLoginType().equals("3"))
			this.getGridConfig().setTitle("集体用户账户");
		else
			this.getGridConfig().setTitle("二级集体用户账户");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("用 户 名"), "loginId", true, true, true, "", false, 25);
		this.getGridConfig().addColumn(this.getText("姓名"), "name", true, true, true, "", false, 25);
		if ("2".equals(us.getUserLoginType()) || "4".equals(us.getUserLoginType())) {
			ColumnConfig c_name1 = new ColumnConfig(this.getText("组织机构"), "peEnterprise.name");
			String sql = "";
			if ("2".equals(us.getUserLoginType())) {
				sql = " select pe1.id,pe1.name from pe_enterprise pe,pe_enterprise_manager pem,pe_enterprise pe1  where pe.id=pem.fk_enterprise_id and pe.id=pe1.fk_parent_id and pem.login_id='"
						+ us.getLoginId() + "'";
			} else if ("4".equals(us.getUserLoginType())) {
				this.getParantPeEnterpriseId(us.getLoginId());
				sql = " select t.id,t.name from pe_enterprise t where t.fk_parent_id='" + this.parantId + "'";
			}
			c_name1.setComboSQL(sql);
			this.getGridConfig().addColumn(c_name1);
		}
		if (us.getUserLoginType().equals("3")) {
			this.getGridConfig().addColumn(this.getText("组织机构"), "peEnterprise.name");

			ColumnConfig c_name = new ColumnConfig(this.getText("账号类别"), "pePriRole.name");
			c_name
					.setComboSQL("select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code in ('2','4','5','6') and e.namespace='FlagRoleType' order by t.name");
			this.getGridConfig().addColumn(c_name);
			if (capabilitySet.contains(this.servletPath + "_entPassword.action")) {
				this.getGridConfig().addMenuFunction(this.getText("重置密码(默认密码" + Const.defaultPwd + ")"), "ssoUser.password");
			}
		}
		if (us.getUserLoginType().equals("2") && us.getSsoUser().getPePriRole().getName().indexOf("二级") == -1) {// 添加二级监管判断
			this.getGridConfig().addMenuFunction(this.getText("启用账号"), "FlagIsvalid.true");
			this.getGridConfig().addMenuFunction(this.getText("停用账号"), "FlagIsvalid.false");
		}

		this.getGridConfig().addColumn(this.getText("固定电话"), "phone", false, false, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("移动电话"), "mobilePhone", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("电子邮件"), "email", false, true, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("是否可用"), "enumConstByFlagIsvalid.name", true, true, true, "TextField", false, 5, true);
		this.getGridConfig().addColumn(this.getText("从业人员规模"), "peEnterprise.num", false, false, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("学员人数"), "countrs", false, false, true, "");
		this.getGridConfig().addRenderScript(this.getText("学员信息"),
				"{return '<a href=\"/entity/basic/peBzzStudent.action?enterPage='+${value}+'\">查看学员</a>';}", "id");
		this.getGridConfig().addRenderScript(this.getText("机构信息"),
				"{return '<a href=\"/entity/basic/enterpriseManager_listjigou.action?enterPage='+${value}+'\">查看</a>';}", "id");
	}

	public String listjigou() {

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("   select pem.id,   pem.login_id, pem.name,    peenterpri3_.name zuzhi,   peprirole5_.name jg, pem.phone,");
		sqlBuffer.append("  pem.mobile_phone,     pem.email,   peenterpri3_.address,        peenterpri3_.num");
		sqlBuffer.append("    from PE_ENTERPRISE_MANAGER pem  ");
		sqlBuffer.append("   inner join SSO_USER ssouser4_ on pem.FK_SSO_USER_ID =        ssouser4_.ID");
		sqlBuffer.append("      inner join PE_ENTERPRISE peenterpri3_ on pem.FK_ENTERPRISE_ID = peenterpri3_.ID ");
		sqlBuffer.append("      inner join PE_PRI_ROLE peprirole5_ on ssouser4_.FK_ROLE_ID =     peprirole5_.ID");
		sqlBuffer.append("          left outer join ENUM_CONST enumconstb1_ on pem.GENDER =              enumconstb1_.ID ");
		sqlBuffer.append("          left outer join ENUM_CONST enumconstb2_ on pem.FLAG_ISVALID =  enumconstb2_.ID ");
		sqlBuffer.append("  WHERE pem.id='" + enterPage + "'");

		List listManager;
		try {
			listManager = this.getGeneralService().getBySQL(sqlBuffer.toString());
			if (null != listManager && listManager.size() > 0) {
				ServletActionContext.getRequest().setAttribute("listti", listManager);
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "jigou";

	}

	/**
	 * 修改时列更新
	 * 
	 * @author linjie
	 * @return
	 */
	public Map updateColumn() {
		Map map = new HashMap();
		String msg = ""; // 用于记录更加具体的信息
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String manager = "";
		if ("3".equalsIgnoreCase(us.getRoleId())) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
			dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
			List<PeManager> peManagerList = new ArrayList<PeManager>();
			try {
				peManagerList = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			manager = peManagerList.get(0).getName();
		} else {
			DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
			List<PeEnterpriseManager> peManagerList = new ArrayList<PeEnterpriseManager>();
			try {
				peManagerList = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			manager = peManagerList.get(0).getName();
		}
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {

			String[] ids = getIds().split(",");
			List idList = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				checkBeforeUpdateColumn(idList);
			} catch (EntityException e) {
				map.clear();
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}

			String acrion = this.getColumn();
			try {

				if (acrion.equals("ssoUser.password")) {
					DetachedCriteria sdc = DetachedCriteria.forClass(PeEnterpriseManager.class);
					sdc.setProjection(Projections.property("ssoUser.id"));
					sdc.add(Restrictions.in("id", ids));

					DetachedCriteria tampdc = DetachedCriteria.forClass(SsoUser.class);
					tampdc.add(Subqueries.propertyIn("id", sdc));
					List<SsoUser> slist = this.peEnterprisemanagerService.getList(tampdc);
					Iterator<SsoUser> iterator = slist.iterator();
					while (iterator.hasNext()) {
						SsoUser ssoUser = iterator.next();
						ssoUser.setPassword("");
						ssoUser.setPasswordMd5(MyUtil.md5(Const.defaultPwd));
						ssoUser.setPasswordBk(MyUtil.md5(Const.defaultPwd));
						this.peEnterprisemanagerService.updateSsoUser(ssoUser);
						count++;
					}
					msg = "重置密码";
				}
				if (acrion.equals("FlagIsvalid.true")) {

					EnumConst enumConstByFlagIsvalid = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "1");
					DetachedCriteria sdc = DetachedCriteria.forClass(PeEnterpriseManager.class);
					sdc.add(Restrictions.in("id", ids));
					List<PeEnterpriseManager> Pelist = this.peEnterprisemanagerService.getList(sdc);
					Iterator<PeEnterpriseManager> iterator = Pelist.iterator();
					while (iterator.hasNext()) {
						PeEnterpriseManager enterpriseManager = iterator.next();
						enterpriseManager.setConfirmDate(new Date());
						enterpriseManager.setConfirmManagerId(manager);
						enterpriseManager.setEnumConstByFlagIsvalid(enumConstByFlagIsvalid);
						this.peEnterprisemanagerService.updatePeEnterpriseManager(enterpriseManager);
						count++;
					}
					msg = "账户启用";
				}
				if (acrion.equals("FlagIsvalid.false")) {

					EnumConst enumConstByFlagIsvalid = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "0");

					DetachedCriteria sdc = DetachedCriteria.forClass(PeEnterpriseManager.class);
					sdc.add(Restrictions.in("id", ids));
					List<PeEnterpriseManager> Pelist = this.peEnterprisemanagerService.getList(sdc);
					Iterator<PeEnterpriseManager> iterator = Pelist.iterator();
					while (iterator.hasNext()) {
						PeEnterpriseManager enterpriseManager = iterator.next();
						enterpriseManager.setConfirmDate(new Date());
						enterpriseManager.setConfirmManagerId(manager);
						enterpriseManager.setEnumConstByFlagIsvalid(enumConstByFlagIsvalid);
						this.peEnterprisemanagerService.updatePeEnterpriseManager(enterpriseManager);
						count++;
					}
					msg = "账户停用";
				}
			} catch (EntityException e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", msg + "共有" + this.getText(String.valueOf(count) + "条记录操作成功"));
		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}

	public void setEntityClass() {
		this.entityClass = PeEnterpriseManager.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/enterpriseManager";
	}

	public GeneralService getGeneralService() {
		return peEnterprisemanagerService;
	}

	public void setPeEnterprisemanagerService(GeneralService peEnterprisemanagerService) {
		this.peEnterprisemanagerService = peEnterprisemanagerService;
	}

	/**
	 * 删除前验证
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (!"3".equals(us.getRoleId())) {
			PeEnterprise peEnterprise = null;
			DetachedCriteria dc = null;
			dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.createCriteria("ssoUser", "ssoUser");
			dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
			List<PeEnterpriseManager> listPeEnterpriseManager = null;
			listPeEnterpriseManager = this.getGeneralService().getList(dc);
			if (null != listPeEnterpriseManager && listPeEnterpriseManager.size() > 0) {
				peEnterprise = listPeEnterpriseManager.get(0).getPeEnterprise(); // 当前管理员所在企业
			}
			dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			DetachedCriteria dcd = dc.createCriteria("peEnterprise", "peEnterprise");
			dcd.add(Restrictions.ne("peEnterprise", peEnterprise));
			dc.add(Restrictions.in("id", idList));
			List list = null;
			list = this.getGeneralService().getList(dc);
			if (null != list && list.size() > 0) {
				throw new EntityException("您只能删除您管理范围之内的管理员！");
			}
		}
	}

	/**
	 * 删除信息
	 * 
	 * @author linjie
	 * @return
	 */
	public Map delete() {
		Map map = new HashMap();
		String msg = ""; // 用于记录更加具体的信息
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				DetachedCriteria criteria = DetachedCriteria.forClass(PeBulletin.class);
				criteria.createCriteria("enterpriseManager", "enterpriseManager");
				criteria.add(Restrictions.in("enterpriseManager.id", ids));
				try {
					List<PeBulletin> plist = this.getGeneralService().getList(criteria);
					if (plist.size() > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "该企业管理员下存在相关联的公告信息,无法删除!");
						return map;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
				try {
					checkBeforeDelete(idList);
				} catch (EntityException e2) {
					// TODO Auto-generated catch block
					// e2.printStackTrace();
					map.clear();
					map.put("success", "false");
					map.put("info", e2.getMessage());
					return map;
				}
				try {
					int i = 0;
					List<SsoUser> ssoUserList = new ArrayList<SsoUser>();
					PeEnterpriseManager peEnterpriseManager = null;
					for (int temp = 0; temp < idList.size(); temp++) {
						peEnterpriseManager = (PeEnterpriseManager) this.getGeneralService().getById(idList.get(temp).toString());
						ssoUserList.add(peEnterpriseManager.getSsoUser());
					}
					i = this.getGeneralService().deleteByIds(idList);
					map.put("success", "true");
					map.put("info", "删除成功");
				} catch (RuntimeException e) {
					return this.checkForeignKey(e);
				} catch (Exception e1) {
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
	 * 查询集体管理员
	 * 
	 * @author linjie
	 * @return
	 */
	public Page list() {
		Page page = null;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String peprirole5_ = "";// 检索列表中账号类别
		String loininID = "";// 当前用户登录号
		us.getLoginId();
		if ("3".equals(us.getRoleId())) {
			peprirole5_ = "peprirole5_.name jg,";
		}
		try {
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("   select pem.id,   pem.login_id, pem.name,    peenterpri3_.name zuzhi,  " + peprirole5_ + "  pem.phone,");
			sqlBuffer
					.append("  pem.mobile_phone,     pem.email,  enumconstb2_.name youxiao,            peenterpri3_.num, to_number(nvl(tongji.renshu,0)) renshu");
			sqlBuffer.append("    from PE_ENTERPRISE_MANAGER pem  ");
			sqlBuffer.append("   inner join SSO_USER ssouser4_ on pem.FK_SSO_USER_ID =        ssouser4_.ID");
			sqlBuffer.append("      inner join PE_ENTERPRISE peenterpri3_ on pem.FK_ENTERPRISE_ID = peenterpri3_.ID ");
			sqlBuffer.append("      inner join PE_PRI_ROLE peprirole5_ on ssouser4_.FK_ROLE_ID =     peprirole5_.ID");
			sqlBuffer.append("          left outer join ENUM_CONST enumconstb1_ on pem.GENDER =              enumconstb1_.ID ");
			sqlBuffer.append("          left outer join ENUM_CONST enumconstb2_ on pem.FLAG_ISVALID =               enumconstb2_.ID ");
			sqlBuffer.append("  left join (select pbs.fk_enterprise_id, count(su.id) as renshu    from pe_bzz_student pbs ,sso_user su");
			sqlBuffer
					.append("    where pbs.fk_sso_user_id = su.id   group by pbs.fk_enterprise_id) tongji on tongji.fk_enterprise_id =  pem.fk_enterprise_id ");
			sqlBuffer.append("  WHERE 1 = 1");
			if ("2".equals(us.getUserLoginType())) {// 一级集体
				sqlBuffer
						.append(" AND PEENTERPRI3_.FK_PARENT_ID IN (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '"
								+ us.getSsoUser().getId() + "') ");
			}
			if ("4".equals(us.getUserLoginType())) {// 二级集体
				sqlBuffer
						.append(" AND PEENTERPRI3_.FK_PARENT_ID IN (SELECT FK_PARENT_ID FROM PE_ENTERPRISE WHERE ID IN (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '"
								+ us.getSsoUser().getId() + "')) ");
			}
			/* 拼接查询条件 */
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
				/* 类别 */
				if (name.equals("pePriRole.name")) {
					name = "peprirole5_.name";
				}
				/* 机构 */
				if (name.equals("peEnterprise.name")) {
					name = "peenterpri3_.name";
				}
				/* 可用 */
				if (name.equals("enumConstByFlagIsvalid.name")) {
					name = "enumconstb2_.name";
				}
				/* 用户 */
				if (name.equals("loginId")) {
					name = "pem.login_id";
				}
				/* 电联 */
				if (name.equals("mobilePhone")) {
					name = "pem.mobile_phone";
				}
				/* 名字 */
				if (name.equals("name")) {
					name = "pem.name";
				}
				sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");

			} while (true);
			String temp = this.getSort();
			/* 用户名 */
			if (temp.equals("loginId")) {
				temp = "pem.login_id";
			}
			/* 名字 */
			if (temp.equals("name")) {
				temp = "pem.name";
			}
			/* 类别 */
			if (temp.equals("pePriRole.name")) {
				temp = "peprirole5_.name";
			}
			/* 机构 */
			if (temp.equals("peEnterprise.name")) {
				temp = "peenterpri3_.name";
			}
			/* 可用 */
			if (temp.equals("enumConstByFlagIsvalid.name")) {
				temp = "enumconstb2_.name";
			}
			/* 电联 */
			if (temp.equals("mobilePhone")) {
				temp = "pem.mobile_phone";
			}

			/* 固联 */
			if (temp.equals("phone")) {
				temp = "pem.phone";
			}

			/* 从业规模 */
			if (temp.equals("peEnterprise.num")) {
				temp = " peenterpri3_.num";
			}
			/* 人数 */
			if (temp.equals("countrs")) {
				temp = "to_number(nvl(tongji.renshu,0))";
			}
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sqlBuffer.append(" order by " + temp + " desc ");

				} else {
					sqlBuffer.append(" order by " + temp + " asc ");
				}
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	// @Override
	// public DetachedCriteria initDetachedCriteria() {
	// UserSession us = (UserSession) ServletActionContext.getRequest()
	// .getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	// DetachedCriteria dc = DetachedCriteria
	// .forClass(PeEnterpriseManager.class);
	// dc.createCriteria("enumConstByGender", "enumConstByGender",
	// DetachedCriteria.LEFT_JOIN);
	// dc.createCriteria("enumConstByFlagIsvalid",
	// "enumConstByFlagIsvalid",DetachedCriteria.LEFT_JOIN);
	// if (us.getUserLoginType().equals("2")) {
	//			
	// DetachedCriteria dc2 =
	// DetachedCriteria.forClass(PeEnterpriseManager.class);
	// dc2.createAlias("peEnterprise", "peEnterprise");
	// dc2.add(Restrictions.eq("ssoUser", us.getSsoUser()));
	// dc2.setProjection(Projections.property("peEnterprise.id"));
	//				
	// dc.createCriteria("ssoUser",
	// "ssoUser").createAlias("pePriRole","pePriRole");
	// dc.createCriteria("peEnterprise",
	// "peEnterprise").createCriteria("peEnterprise",
	// "supEnterprise").add(Property.forName("supEnterprise.id").in(dc2));
	// }else if(us.getUserLoginType().equals("4")){
	// this.getParantPeEnterpriseId(us.getLoginId());
	// if(this.parantId!=null&&!"".equals(this.parantId)){//父类机构管理
	// //dc.createCriteria("ssoUser",
	// "ssoUser").createAlias("pePriRole","pePriRole");
	// dc.createCriteria("ssoUser",
	// "ssoUser").createAlias("pePriRole","pePriRole").add(Restrictions.eq("pePriRole.id",
	// "1"));
	// dc.createCriteria("peEnterprise",
	// "peEnterprise").createCriteria("peEnterprise",
	// "supEnterprise").add(Property.forName("supEnterprise.id").eq(this.parantId));
	// }else{//父类机构为空则查询结果为空
	// dc.add(Restrictions.sqlRestriction(" 1<>1 "));
	// }
	// }else{
	// dc.createAlias("peEnterprise", "peEnterprise");
	// dc.createCriteria("ssoUser", "ssoUser").createAlias("pePriRole",
	// "pePriRole");
	// }
	// if(StringUtils.defaultString(this.getSort()).equals("") ||
	// StringUtils.defaultString(this.getSort()).equals("id")){
	// dc.addOrder(Order.asc("peEnterprise.code"));
	// }
	// return dc;
	// }
	/**
	 * 获取父类机构
	 * 
	 * @param loginId
	 * @return
	 */
	public void getParantPeEnterpriseId(String loginId) {
		String sql = "select pe.fk_parent_id from pe_enterprise pe,pe_enterprise_manager pem where pe.id=pem.fk_enterprise_id and pem.login_id='"
				+ loginId + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list != null && list.size() > 0 && list.get(0) != null) {
				this.parantId = list.get(0).toString();
			} else {
				this.parantId = "0";
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PeEnterpriseManager getBean() {
		return (PeEnterpriseManager) super.superGetBean();
	}

	public void setBean(PeEnterpriseManager bean) {
		super.superSetBean(bean);
	}

	/**
	 * 
	 */
	public String toAdd() {
		String hql =" from EnumConst ec where ec.namespace='FlagEnterpriseType' AND ec.id <>'402880a91dadc115011dadfcf26b9003' " ;
		try {
			organTypeList = this.getGeneralService().getByHQL(hql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "add_enterprise";
	}

	public String saveEnterprise() {
		List list = null;
		int i = 0;
		String sql = " SELECT * FROM PE_ENTERPRISE WHERE ID = '" + this.organCode + "' OR CODE = '" + this.organCode + "' OR NAME like  '%"
				+ organName + "%'  ";
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list != null && list.size() > 0) {
			this.setMsg("添加失败，机构号或机构名称已被占用 ，请联系技术人员");
			return "m_msg";
		}
		String addSql = " INSERT INTO PE_ENTERPRISE (ID,NAME,CODE,DATA_DATE,ENTYPE)  VALUES ('" + this.organCode + "','" + this.organName
				+ "','" + this.organCode + "',SYSDATE,'" + this.enTypeId + "') ";
		try {
			i = this.getGeneralService().executeBySQL(addSql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (i > 0) {
			this.setMsg("机构号:"+ this.organCode+"\n"+this.organName+ "\r 机构添加成功，请到后台管理中配置管理员");
			return "m_msg";
		}
		this.setMsg("添加失败");
		return "m_msg";
	}

	/**
	 * 添加前验证账号
	 * 
	 * @author linjie
	 * @return
	 */
	public void checkBeforeAdd() throws EntityException {
		DetachedCriteria criteria = DetachedCriteria.forClass(SsoUser.class);
		criteria.add(Restrictions.eq("loginId", this.getBean().getLoginId()));
		List pList = null;
		pList = this.getGeneralService().getList(criteria);
		if (pList != null && pList.size() > 0) {
			throw new EntityException("该登录已经账号存在，请重新输入新账号");
		}
	}

	/**
	 * 修改前验证账号
	 * 
	 * @author linjie
	 * @return
	 */
	public void checkBeforeUpdate() throws EntityException {
		String sql = "select id from pe_enterprise_manager where login_id='" + this.getBean().getLoginId() + "'";
		List pList = null;
		pList = this.getGeneralService().getBySQL(sql);
		String id = "";
		if (pList != null && pList.size() > 0)
			id = pList.get(0).toString();
		if (pList != null && id != null && pList.size() > 0 && !id.equals(this.getBean().getId())) {
			throw new EntityException("该登录已经账号存在，请重新输入新账号");
		}
	}

	/**
	 * 添加时默认调用的方法
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Map add() {
		Map map = new HashMap();
		String linkUrl = null;

		this.superSetBean((PeEnterpriseManager) setSubIds(this.getBean()));

		try {
			checkBeforeAdd();
		} catch (EntityException e1) {
			if (linkUrl != null)
				new File(ServletActionContext.getRequest().getRealPath(linkUrl)).delete();
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}

		PeEnterpriseManager instance = null;

		try {

			UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			if (!"3".equals(us.getRoleId())) { // 企业管理员
				DetachedCriteria dc = null;
				dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
				dc.createCriteria("ssoUser", "ssoUser");
				dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
				List<PeEnterpriseManager> list = null;
				PeEnterpriseManager peEnterpriseManager = null;
				try {
					list = this.getGeneralService().getList(dc);
					if (null != list && list.size() > 0) {
						peEnterpriseManager = list.get(0);
					}
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.getBean().setPeSite(peEnterpriseManager.getPeEnterprise());
			}

			instance = (PeEnterpriseManager) this.getGeneralService().save(this.getBean());
			String roleId = instance.getPePriRole().getId();
			String sql = null;
			// 一级企业管理员
			if (roleId.equals("402880a92137be1c012137db62100006") || roleId.equals("2")) {
				String entId = instance.getPeEnterprise().getId();
				// 用户在二级企业
				DetachedCriteria dc = DetachedCriteria.forClass(PeEnterprise.class);
				dc.createCriteria("peEnterprise");
				dc.add(Restrictions.eq("id", entId));
				List<PeEnterprise> tpeList = (List<PeEnterprise>) this.getGeneralService().getList(dc);
				if (tpeList != null && tpeList.size() != 0) {
					PeEnterprise tpe = tpeList.get(0);
					String tentId = entId;
					entId = tpe.getPeEnterprise().getId();
					sql = "insert into pr_bzz_pri_manager_enterprise \n" + "select to_char(s_training_id.nextval),'"
							+ instance.getSsoUser().getId() + "',e.id from pe_enterprise e " + "where (e.id='" + entId
							+ "' or e.fk_parent_id='" + entId + "') and e.id <> '" + tentId + "'";

				} else {
					sql = "insert into pr_bzz_pri_manager_enterprise \n" + "select to_char(s_training_id.nextval),'"
							+ instance.getSsoUser().getId() + "',e.id from pe_enterprise e " + "where e.fk_parent_id='" + entId
							+ "' and e.fk_parent_id is not null";
				}

				int n = this.getGeneralService().executeBySQL(sql);

				// 二级企业管理员
			} else if (roleId.equals("402880f322736b760122737a968a0010") || roleId.equals("402880f322736b760122737a60c40008")) {

				// 已在其它位置处理
			}

			map.put("success", "true");
			map.put("info", "添加成功");

		} catch (Exception e) {
			if (linkUrl != null)
				new File(ServletActionContext.getRequest().getRealPath(linkUrl)).delete();
			return this.checkAlternateKey(e, "添加");

		}

		return map;
	}

	private PeEnterprise getPeEnterpriseByUs(UserSession us) {
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc.createCriteria("ssoUser", "ssoUser");
		dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
		List<PeEnterpriseManager> list = null;
		PeEnterpriseManager peEnterpriseManager = null;
		try {
			list = this.getGeneralService().getList(dc);
			if (null != list && list.size() > 0) {
				peEnterpriseManager = list.get(0);
			} else {
				peEnterpriseManager = new PeEnterpriseManager();
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return peEnterpriseManager.getPeEnterprise();
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public String getParantId() {
		return parantId;
	}

	public void setParantId(String parantId) {
		this.parantId = parantId;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getEnTypeId() {
		return enTypeId;
	}

	public void setEnTypeId(String enTypeId) {
		this.enTypeId = enTypeId;
	}

	public List<EnumConst> getOrganTypeList() {
		return organTypeList;
	}

	public void setOrganTypeList(List<EnumConst> organTypeList) {
		this.organTypeList = organTypeList;
	}

}
