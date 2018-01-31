package com.whaty.platform.entity.web.action.basic;

import java.util.List;
import java.util.Set;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * @param
 * @version 创建时间：2009-6-22 下午03:29:27
 * @return
 * @throws PlatformException
 *             类说明
 */
public class PeBzzStudentSubAction extends PeBzzStudentAction {

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.setCanProjections(true);
		boolean canUpdate = true;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		this.getGridConfig().setCapability(false, false, false, true, false);
		this.getGridConfig().setTitle(this.getText("学员信息列表"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("系统编号"), "regNo", true, true, true, "TextField", false, 100, true);
		this.getGridConfig().addColumn(this.getText("具有从业资格"), "enumConstByIsEmployee.name", true, false, true, "TextField", false, 100, true);
		ColumnConfig c_name = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name");
		c_name.setAdd(true);
		c_name.setSearch(true);
		c_name.setList(true);
		c_name.setComboSQL("SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagQualificationsType' and t.code not in('9','90','91')");
		this.getGridConfig().addColumn(c_name);
		// this.getGridConfig().addColumn(this.getText("资格类型"),
		// "enumConstByFlagQualificationsType.name", true, false, true,
		// "TextField", false, 100, true);
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName", true, canUpdate, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("性别"), "gender", false, canUpdate, false, "TextField", true, 10, true);
		this.getGridConfig().addColumn(this.getText("民族"), "folk", false, canUpdate, false, "TextField", true, 40);
		this.getGridConfig().addColumn(this.getText("学历"), "education", false, canUpdate, false, "TextField", true, 10, true);
		this.getGridConfig().addColumn(this.getText("出生日期"), "birthdayDate", false, canUpdate, false, "TextField", true, 10);
		// if("self".equals(flag)){//本机构
		ColumnConfig c_name1 = new ColumnConfig(this.getText("所在机构"), "peEnterprise.name", true, canUpdate, true, "TextField", true, 50, "");
		if (us.getUserLoginType().equals("2") || us.getUserLoginType().equals("4")) {
			String sql = "select t.id, t.name as p_name\n" + "  from pe_enterprise t\n" + " where t.id in\n" + "       (select s.fk_parent_id\n"
					+ "          from pe_enterprise s\n" + "         inner join pe_enterprise_manager t\n" + "            on s.id = t.fk_enterprise_id\n"
					+ "           and t.login_id = '" + us.getLoginId() + "')\n" + " order by t.code";
			c_name1.setComboSQL(sql);
		} else {
			c_name1.setComboSQL("select t.id,t.name as p_name from pe_enterprise t order by t.code ");
		}
		this.getGridConfig().addColumn(c_name1);
		this.getGridConfig().addColumn(this.getText("从事业务"), "work", true, canUpdate, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("职务"), "position", true, canUpdate, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("工作部门"), "department", true, canUpdate, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("是否有效"), "ssoUser.enumConstByFlagIsvalid.name", true, false, true, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("办公电话"), "phone", false, canUpdate, false, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("移动电话"), "mobilePhone", false, canUpdate, false, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("电子邮件"), "email", false, canUpdate, false, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("学员照片"), "photo", false, false, false, "File", true, 50);
		this.getGridConfig().addColumn(this.getText("国籍"), "cardType", false, canUpdate, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo", true, canUpdate, true, "TextField", true, 100);
		ColumnConfig c_name2 = null;
		String peEnterpriseCode = (String) ServletActionContext.getRequest().getSession().getAttribute("peEnterprisemanager");
		// StringBuffer sql = new StringBuffer();
		//
		// if (us.getUserLoginType().equals("4")) {
		// sql.append("  SELECT DISTINCT t.SUB_GROUPS\n ");
		// } else {
		// sql.append("  SELECT DISTINCT t.GROUPS\n ");
		// }
		//
		// sql.append("  FROM   PE_BZZ_STUDENT t\n ");
		// sql.append("  LEFT JOIN pe_enterprise s\n");
		// sql.append("       ON t.fk_enterprise_id = s.id\n ");
		// sql.append("          LEFT JOIN pe_enterprise a\n");
		// sql.append("         ON s.fk_parent_id = a.id\n");
		// sql.append("            AND a.code = '" + peEnterpriseCode + "'\n");
		//
		// sql.append("  order by t.GROUPS");
		// c_name2.setComboSQL(sql.toString());
		// this.getGridConfig().addColumn(c_name2);
		UserSession userSession = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = userSession.getUserPriority().keySet();
		boolean password = false;
		boolean FlagIsvalid = false;
		if (capabilitySet.contains(this.servletPath + "_password.action")) {
			password = true;
		}
		if (capabilitySet.contains(this.servletPath + "_FlagIsvalid.action")) {
			FlagIsvalid = true;
		}
		if (us.getUserLoginType().equals("4")) {
			this.getGridConfig().addMenuFunction(this.getText("学员调配"), "/entity/basic/peBzzStudentSub_toCheckIn.action?flag=" + this.getFlag() + "", false, false);
			this.getGridConfig().addMenuScript(this.getText("Excel导入学员调配"), "{window.location='/entity/basic/peBzzStudent_toExcelCheckIn.action?flag=" + this.getFlag() + "';}");
		}
		ServletActionContext.getRequest().getSession().setAttribute("flag", "stu");

	}

	public DetachedCriteria initDetachedCriteria() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("ssoUser", "ssoUser").createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN);
		;
		dc.createAlias("peEnterprise", "peEnterprise", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType", DetachedCriteria.LEFT_JOIN);
		if (!us.getUserLoginType().equals("3")) {
			String peEnterpriseCode = (String) ServletActionContext.getRequest().getSession().getAttribute("peEnterprisemanager");

			DetachedCriteria dc2 = DetachedCriteria.forClass(PeEnterprise.class);
			dc2.add(Restrictions.eq("code", peEnterpriseCode));
			dc2.setProjection(Property.forName("id"));
			if ("self".equalsIgnoreCase(getFlag())) {
				dc.add(Restrictions.eq("peEnterprise.code", peEnterpriseCode));
			} else {
				String sql = "select pePar.id\n" + "  from pe_enterprise pe, pe_enterprise pePar, pe_enterprise_manager pem\n" + " where pe.fk_parent_id = pePar.Id\n"
						+ "   and pe.Id = pem.fk_enterprise_id\n" + "   and pem.login_id = '" + us.getLoginId() + "'";

				List list = null;
				try {
					list = this.getGeneralService().getBySQL(sql);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// dc.add(Restrictions.or(Restrictions.eq("peEnterprise.code",
				// peEnterpriseCode),
				// Property.forName("peEnterprise.peEnterprise.id").in(dc2)));
				dc.add(Restrictions.in("peEnterprise.id", list.toArray(new String[list.size()])));
			}

		}
		return dc;
	}

	public void setEntityClass() {
		this.entityClass = PeBzzStudent.class;

	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peBzzStudentSub";
	}
}