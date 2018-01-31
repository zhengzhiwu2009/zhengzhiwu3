package com.whaty.platform.entity.web.action.email;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.SiteEmail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class SendEmailAction extends MyBaseAction {

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle("发件箱");
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("标题"), "title");
		this.getGridConfig().addColumn(this.getText("收件人"), "true_name");
		this.getGridConfig().addColumn(this.getText("时间"), "senddate", true, true, true, "Date", true, 50);
		this.getGridConfig().addColumn(this.getText("阅读状态"), "status", false, false, false, "TextField", false, 30);
		this.getGridConfig().addRenderScript(this.getText("阅读状态"), "{if(${value}==0) return '未阅读';else return '已阅读';}", "status");
		this
				.getGridConfig()
				.addRenderFunction(
						"查看内容",
						"<a href=\"/siteEmail/recipientEmail_EmailInfo.action?id=${value}&&temp=2\" target=\"_blank\"><font color=#0000ff>查看内容<font></a>",
						"id");
		this.getGridConfig().addMenuFunction("删除", "del");
		ColumnConfig c_name1 = new ColumnConfig(this.getText("所在机构"), "combobox_peName", true, false, true, "TextField", true, 50, "");
		if (us.getUserLoginType().equals("2") || us.getUserLoginType().equals("4")) {
			String sql = "select t.id, t.name as p_name\n" + "  from pe_enterprise t\n" + " where t.fk_parent_id in\n"
					+ "       (select s.id\n" + "          from pe_enterprise s\n" + "         inner join pe_enterprise_manager t\n"
					+ "            on s.id = t.fk_enterprise_id\n" + "           and t.login_id = '" + us.getLoginId() + "')\n"
					+ "    or t.id in (select s.id\n" + "                  from pe_enterprise s\n"
					+ "                 inner join pe_enterprise_manager t\n" + "                    on s.id = t.fk_enterprise_id\n"
					+ "                   and t.login_id = '" + us.getLoginId() + "')\n" + " order by t.code";

			c_name1.setComboSQL(sql);
		} else {
			c_name1.setComboSQL("select t.id,t.name as p_name from pe_enterprise t order by t.code ");
		}
		this.getGridConfig().addColumn(c_name1);
		// this.getGridConfig().addColumn(this.getText("组织机构"),"peName");

		// this.getGridConfig().addColumn(this.getText("账号类别"),"roleType");
		boolean isSearch = true;
		if (us.getUserLoginType().equals("3")) {
			isSearch = false;
		}
		ColumnConfig c_name2 = new ColumnConfig(this.getText("账号类别"), "combobox_roleType", isSearch, false, true, "TextField", true, 50, "");
		String sql = "select id, name from pe_pri_role";
		c_name2.setComboSQL(sql);
		this.getGridConfig().addColumn(c_name2);

	}

	@Override
	public void setEntityClass() {
		this.entityClass = SiteEmail.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/siteEmail/sendEmail";

	}

	/**
	 * 重写框架方法：发件箱信息（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		Page page = null;
		ActionContext ac = ActionContext.getContext();
		UserSession us = (UserSession) ac.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sql = new StringBuffer();
		// 协会管理员因为有发的权限所有 差所有人的
		sql.append(" SELECT ID, ");
		sql.append("        TITLE, ");
		sql.append("        TRUE_NAME, ");
		sql.append("        TO_DATE(TO_CHAR(SENDDATE, 'yyyy-mm-dd'), 'yyyy-mm-dd') AS SENDDATE, ");
		sql.append("        STATUS, ");
		sql.append("        COMBOBOX_PENAME, ");
		sql.append("        COMBOBOX_ROLETYPE ");
		sql.append("   FROM (SELECT S.ID, ");
		sql.append("                S.TITLE, ");
		sql.append("                CASE ");
		sql.append("                  WHEN PM.TRUE_NAME IS NOT NULL THEN ");
		sql.append("                   PM.TRUE_NAME ");
		sql.append("                  WHEN PEM.NAME IS NOT NULL THEN ");
		sql.append("                   PEM.NAME ");
		sql.append("                  ELSE ");
		sql.append("                   PBS.TRUE_NAME ");
		sql.append("                END TRUE_NAME, ");
		sql.append("                S.SENDDATE, ");
		sql.append("                S.STATUS, ");
		sql.append("                PE.NAME AS COMBOBOX_PENAME, ");
		sql.append("                PPR.NAME AS COMBOBOX_ROLETYPE ");
		sql.append("           FROM SITE_EMAIL S ");
		sql.append("           LEFT JOIN PE_MANAGER PM ");
		sql.append("             ON S.ADDRESSEE_ID = PM.LOGIN_ID ");
		sql.append("           LEFT JOIN PE_ENTERPRISE_MANAGER PEM ");
		sql.append("             ON S.ADDRESSEE_ID = PEM.LOGIN_ID ");
		sql.append("           LEFT JOIN PE_BZZ_STUDENT PBS ");
		sql.append("             ON S.ADDRESSEE_ID = PBS.REG_NO ");
		sql.append("           LEFT JOIN PE_ENTERPRISE PE ");
		sql.append("             ON (PEM.FK_ENTERPRISE_ID = PE.ID OR PBS.FK_ENTERPRISE_ID = PE.ID) ");
		sql.append("           LEFT JOIN SSO_USER SU ");
		sql.append("             ON (PM.FK_SSO_USER_ID = SU.ID OR PEM.FK_SSO_USER_ID = SU.ID OR ");
		sql.append("                PBS.FK_SSO_USER_ID = SU.ID) ");
		sql.append("           LEFT JOIN PE_PRI_ROLE PPR ");
		sql.append("             ON SU.FK_ROLE_ID = PPR.ID ");
		sql.append("          WHERE SU.ID IS NOT NULL ");
		sql.append("            AND S.SENDER_DEL = 0 ");
		sql.append("            AND S.SENDER_ID = '" + us.getLoginId() + "') ");
		try {
			this.setSqlCondition(sql);
			page = this.getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
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
	 * 重写框架方法--列更新
	 * 
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
				if (action.equals("del")) {
					DetachedCriteria dc = DetachedCriteria.forClass(SiteEmail.class);
					dc.add(Restrictions.in("id", ids));
					List<SiteEmail> periodList = this.getGeneralService().getList(dc);
					for (SiteEmail siteEmail : periodList) {

						siteEmail.setSenderDel((long) 1);
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

		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}

		return map;

	}

}
