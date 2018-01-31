package com.whaty.platform.sso.web.action.admin;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.util.Const;

/**
 * @param
 * @version 创建时间：2009-6-29 上午11:21:06
 * @return
 * @throws PlatformException
 *             类说明
 */
public class PeEnterpriseManagerAction extends MyBaseAction<PeEnterpriseManager> {

	private String type;// 管理员类型：1普通集体管理员2监管集体管理员
	private GeneralService peEnterprisemanagerService;

	/**
	 * 列表加载
	 * 
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, false, false);
		this.getGridConfig().setTitle("机构管理员管理");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId", true, true, true, "", false, 25);
		ColumnConfig c_name = new ColumnConfig(this.getText("权限组"), "pePriRole.name");
		if (null == type || "".equals(type) || "1".equals(type)) {
			c_name.setComboSQL("select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code='2' and e.namespace='FlagRoleType' order by t.name");
		} else if (null != type && "2".equals(type)) {
			c_name.setComboSQL("select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code IN ('5','6') and e.namespace='FlagRoleType' order by t.name");
		}
		this.getGridConfig().addColumn(c_name);

		ColumnConfig pe_name = new ColumnConfig(this.getText("所在机构"), "peEnterprise.name");
		if (null == type || "".equals(type) || "1".equals(type)) {
			pe_name.setComboSQL("SELECT PE.ID, PE.NAME FROM PE_ENTERPRISE PE INNER JOIN ENUM_CONST EC ON PE.ENTYPE = EC.ID AND EC.CODE != 'V'");
		} else if (null != type && "2".equals(type)) {
			pe_name.setComboSQL("SELECT PE.ID, PE.NAME FROM PE_ENTERPRISE PE INNER JOIN ENUM_CONST EC ON PE.ENTYPE = EC.ID AND EC.CODE = 'V'");
		}
		this.getGridConfig().addColumn(pe_name);

		ColumnConfig valid_name = new ColumnConfig(this.getText("是否有效"), "enumConstByFlagIsvalid.name");
		valid_name.setComboSQL("SELECT ID, NAME FROM ENUM_CONST WHERE NAMESPACE = 'FlagIsvalid'");
		this.getGridConfig().addColumn(valid_name);

		ColumnConfig gender_name = new ColumnConfig(this.getText("性别"), "enumConstByGender.name", false, true, true, "ComboBox", true, 100, "");
		gender_name.setComboSQL("SELECT ID, NAME FROM ENUM_CONST WHERE NAMESPACE = 'Gender'");
		this.getGridConfig().addColumn(gender_name);
		this.getGridConfig().addColumn(this.getText("办公电话"), "phone", true, true, true, Const.phone_number_for_extjs, true, 50);
		this.getGridConfig().addColumn(this.getText("移动电话"), "mobilePhone", true, true, true, Const.mobile_for_extjs, true, 50);
		this.getGridConfig().addColumn(this.getText("电子邮件"), "email", true, true, true, Const.email_for_extjs, true, 50);
	}

	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeEnterpriseManager.class;
	}

	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/sso/admin/peEnterprisemanager";
	}

	public Page list() {
		Page page = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT * ");
			sb.append("   FROM (SELECT PEM.ID, ");
			sb.append("                PEM.NAME, ");
			sb.append("                SU.LOGIN_ID      LOGINID, ");
			sb.append("                PPR.NAME         PPRNAME, ");
			sb.append("                PE.NAME          PENAME, ");
			sb.append("                EC.NAME          ECNAME, ");
			sb.append("                EC3.NAME         EC3NAME, ");
			sb.append("                PEM.PHONE, ");
			sb.append("                PEM.MOBILE_PHONE, ");
			sb.append("                PEM.EMAIL ");
			sb.append("           FROM PE_ENTERPRISE_MANAGER PEM ");
			sb.append("           LEFT JOIN PE_ENTERPRISE PE ");
			sb.append("             ON PEM.FK_ENTERPRISE_ID = PE.ID ");
			sb.append("           LEFT JOIN SSO_USER SU ");
			sb.append("             ON PEM.FK_SSO_USER_ID = SU.ID ");
			sb.append("           LEFT JOIN PE_PRI_ROLE PPR ");
			sb.append("             ON SU.FK_ROLE_ID = PPR.ID ");
			sb.append("           LEFT JOIN ENUM_CONST EC ");
			sb.append("             ON SU.FLAG_ISVALID = EC.ID ");
			sb.append("           LEFT JOIN ENUM_CONST EC2 ");
			sb.append("             ON PE.ENTYPE = EC2.ID ");
			sb.append("           LEFT JOIN ENUM_CONST EC3 ");
			sb.append("             ON PEM.GENDER = EC3.ID ");
			if (null == type || "".equals(type) || "1".equals(type)) {// 普通集体管理员
				sb.append("          WHERE EC2.CODE != 'V' ");
			} else if (null != type && "2".equals(type)) {// 监管集体管理员
				sb.append("          WHERE EC2.CODE = 'V' ");
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
				if (name.equals("email")) {
					sb.append(" AND PEM.EMAIL LIKE '%" + value + "%' ");
				}
				if (name.equals("enumConstByFlagIsvalid.name")) {
					sb.append(" AND EC.NAME = '" + value + "' ");
				}
				if (name.equals("loginId")) {
					sb.append(" AND PEM.LOGIN_ID LIKE '%" + value + "%' ");
				}
				if (name.equals("mobilePhone")) {
					sb.append(" AND PEM.MOBILE_PHONE = '" + value + "' ");
				}
				if (name.equals("name")) {
					sb.append(" AND PEM.NAME LIKE '%" + value + "%' ");
				}
				if (name.equals("peEnterprise.name")) {
					sb.append(" AND PE.NAME = '" + value + "' ");
				}
				if (name.equals("pePriRole.name")) {
					sb.append(" AND PPR.NAME = '" + value + "' ");
				}
				if (name.equals("phone")) {
					sb.append(" AND PEM.PHONE = '" + value + "' ");
				}
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("email")) {
					temp = "PEM.EMAIL";
				}
				if (temp.equals("enumConstByFlagIsvalid.name")) {
					temp = "EC.NAME";
				}
				if (temp.equals("loginId")) {
					temp = "PEM.LOGIN_ID";
				}
				if (temp.equals("mobilePhone")) {
					temp = "PEM.MOBILE_PHONE";
				}
				if (temp.equals("name")) {
					temp = "PEM.NAME";
				}
				if (temp.equals("peEnterprise.name")) {
					temp = "PE.NAME";
				}
				if (temp.equals("pePriRole.name")) {
					temp = "PPR.NAME";
				}
				if (temp.equals("phone")) {
					temp = "PEM.PHONE";
				}
				if (temp.equals("enumConstByGender.name")) {
					temp = "EC3.NAME";
				}

				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc"))
					sb.append(" order by " + temp + " desc ");
				else
					sb.append(" order by " + temp + " asc ");
			} else {
				sb.append("  ORDER BY PEM.DATA_DATE DESC ");
			}
			sb.append(" )WHERE 1 = 1 ");
			page = this.getGeneralService().getByPageSQL(sb.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	public void checkBeforeAdd() throws EntityException {
		DetachedCriteria dc2 = DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc2.add(Restrictions.eq("loginId", this.getBean().getLoginId()));
		List courseList = this.getGeneralService().getList(dc2);
		if (courseList != null && courseList.size() > 0) {
			throw new EntityException("用户名已存在，请修改。");
		}
	}

	public void checkBeforeUpdate() throws EntityException {
		DetachedCriteria dc2 = DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc2.add(Restrictions.eq("loginId", this.getBean().getLoginId()));
		List courseList = this.getGeneralService().getList(dc2);
		if (courseList != null && courseList.size() > 0) {
			throw new EntityException("用户名已存在，请修改。");
		}
	}

	public PeEnterpriseManager getBean() {
		return super.superGetBean();
	}

	public void setBean(PeEnterpriseManager bean) {
		super.superSetBean(bean);
	}

	public GeneralService getGeneralService() {
		return peEnterprisemanagerService;
	}

	public void setPeEnterprisemanagerService(GeneralService peEnterprisemanagerService) {
		this.peEnterprisemanagerService = peEnterprisemanagerService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
