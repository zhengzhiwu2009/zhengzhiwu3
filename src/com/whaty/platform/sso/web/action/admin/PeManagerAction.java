package com.whaty.platform.sso.web.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class PeManagerAction extends MyBaseAction<PeManager> {

	private GeneralService peManagerService;

	@Override
	public GeneralService getGeneralService() {
		return peManagerService;
	}

	public void setPeManagerService(GeneralService peManagerService) {
		this.peManagerService = peManagerService;
	}

	/**
	 * 列表加载
	 * 
	 * @author huguolong
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("协会管理员管理"));

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId", true, true, true, "", false, 25);
		// this.getGridConfig().addColumn(this.getText("权限组"), "pePriRole.name",
		// false, true, false, "", false, 25);
		ColumnConfig c_name = new ColumnConfig(this.getText("权限组"), "pePriRole.name");
		c_name.setAdd(true);
		c_name.setSearch(false);
		c_name.setList(false);
		c_name.setComboSQL("select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code='3' and e.namespace='FlagRoleType' order by t.name");
		this.getGridConfig().addColumn(c_name);

		// this.getGridConfig().addColumn(this.getText("权限组"),
		// "ssoUser.pePriRole.name");
		this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByGender.name", true, true, true, "", true, 25);
		this.getGridConfig().addColumn(this.getText("身份证号"), "idCard", true, true, true, "", true, 25);
		this.getGridConfig().addColumn(this.getText("电话"), "phone", true, true, true, Const.telephone_for_extjs, true, 50);
		this.getGridConfig().addColumn(this.getText("手机"), "mobilePhone", true, true, true, Const.mobile_for_extjs, true, 50);
		this.getGridConfig().addColumn(this.getText("邮箱"), "email", true, true, true, Const.email_for_extjs, true, 50);
		// this.getGridConfig().addColumn(this.getText("通信地址及邮编"), "address",
		// true, true, true, "", false, 150);
		// this.getGridConfig().addColumn(this.getText("毕业院校、专业"),
		// "graduationInfo");
		// this.getGridConfig().addColumn(this.getText("毕业时间"),
		// "graduationDate");
		this.getGridConfig().addColumn(this.getText("职务"), "position", true, true, true, "", true, 50);
		// this.getGridConfig().addColumn(this.getText("从事成人教育时间"), "workTime");
		// this.getGridConfig().addRenderFunction(this.getText("设置权限范围"), "<a
		// href='managerRangeRight_showRangeRight.action?managerId=${value}&managerType=3'
		// target='_self'>设置</a>", "id");

		this.getGridConfig().addMenuFunction("重置密码(" + Const.defaultPwd + ")", "pwsd");

		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.location='/admin/manager_manage.jsp'}");

	}

	/**
	 * 添加前的验证条件
	 * 
	 * @author huguolong
	 */
	@Override
	public void checkBeforeAdd() throws EntityException {
		AttributeManage manage = new WhatyAttributeManage();
		String msg = "";
		try {
			// if(!manage.isValidIdcard(this.getBean().getIdCard())){
			// msg = "身份证号码输入错误!";
			// throw new EntityException("身份证号码输入错误！");
			// }
			DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
			dc.add(Restrictions.eq("loginId", this.getBean().getLoginId()));
			List list = this.getGeneralService().getList(dc);
			if (list != null && list.size() > 0) {
				msg = "该用户名已在系统中存在!";
				throw new EntityException("该用户名已在系统中存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException(msg);
		}
	}

	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			String idStr = getIds().replace(",", "','");
			try {
				DetachedCriteria tempdc = DetachedCriteria.forClass(PeManager.class);
				if (action.equals("pwsd")) {
					// 从业人员不能重置密码
					tempdc.add(Restrictions.in("id", ids));
					List<PeManager> eclist = new ArrayList<PeManager>();
					eclist = this.getGeneralService().getList(tempdc);
					Iterator<PeManager> peIterator = eclist.iterator();
					Iterator<PeManager> itr = eclist.iterator();
					while (itr.hasNext()) {
						PeManager pbs = itr.next();
						SsoUser ssoUser = pbs.getSsoUser();
						ssoUser.setPasswordBk(MyUtil.md5(Const.defaultPwd));
						ssoUser.setPasswordMd5(MyUtil.md5(Const.defaultPwd));
						this.peManagerService.updateSsoUser(ssoUser);
					}
					msg = "重置密码";
				}
			} catch (Exception e) {
				map.clear();
				map.put("success", "false");
				map.put("info", msg + "操作失败");
				return map;
			}
			map.put("success", "true");
			map.put("info", msg + "共有" + ids.length + "条记录操作成功");
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少有一条记录被选择");
			return map;
		}
		return map;
	}

	// @Override
	// public void checkBeforeUpdate() throws EntityException {
	// AttributeManage manage=new WhatyAttributeManage();
	// try {
	// if(!manage.isValidIdcard(this.getBean().getIdCard())){
	// throw new EntityException("身份证号码输入错误！");
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new EntityException("身份证号码输入错误！");
	// }
	// }

	@Override
	public void setEntityClass() {
		this.entityClass = PeManager.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/peManager";
	}

	/**
	 * 列表查询
	 * 
	 * @author huguolong
	 */
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.createAlias("enumConstByGender", "enumConstByGender", DetachedCriteria.LEFT_JOIN).createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN).createCriteria("ssoUser", "ssoUser").createAlias("pePriRole", "pePriRole");
		return dc;
	}

	public PeManager getBean() {
		return super.superGetBean();
	}

	public void setBean(PeManager bean) {
		super.superSetBean(bean);
	}
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
				try {
					checkBeforeDelete(idList);
				} catch (EntityException e) {
					map.put("success", "false");
					map.put("info", e.getMessage());
					return map;
				}
				map.put("success", "true");
				map.put("info", "删除成功");
				try {
					//this.getGeneralService().deleteByIds(idList);
					String condition = this.getCondition(this.getIds(),",");
					String sql = " delete from pe_manager where id in ("+condition+")" ;
					this.getGeneralService().executeBySQL(sql);
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
	public String getCondition(String arg1, String arg2) {
		String condition = "";
		if (arg1 != null && arg2 != null) {
			String[] str = arg1.split(arg2);
			for (int i = 0; i < str.length; i++) {
				condition += "'" + str[i] + "',";
			}
			condition = condition.substring(0, condition.length() - 1);
		}
		return condition;
	}

}
