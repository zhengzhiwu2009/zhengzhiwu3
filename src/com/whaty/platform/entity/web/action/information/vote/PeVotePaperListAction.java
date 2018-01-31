package com.whaty.platform.entity.web.action.information.vote;

import java.util.Iterator;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * @param
 * @version 创建时间：2009-8-3 上午10:59:18
 * @return
 * @throws PlatformException
 *             类说明
 */
public class PeVotePaperListAction extends MyBaseAction {

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setTitle(this.getText("调查问卷列表"));
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "title");
		ColumnConfig columnQualificationsType = new ColumnConfig(this.getText("类型"), "enumConstByFlagQualificationsType.name", true, true,
				true, "TextField", false, 100, "");
		String sql = "";
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if ("24".indexOf(us.getUserLoginType()) >= 0)
			sql = "select a.id ,a.name from enum_const a where a.namespace='FlagQualificationsType' AND A.CODE IN ('9','91')";
		else
			sql = "select a.id ,a.name from enum_const a where a.namespace='FlagQualificationsType' AND A.CODE IN ('9') UNION SELECT 'jgman', '面向监管类集体管理员' FROM DUAL";
		columnQualificationsType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnQualificationsType);
		this.getGridConfig().addColumn(this.getText("结束时间"), "endDate", false);
		this.getGridConfig().addRenderFunction(this.getText("问卷填写"),
				"<a href=\"/entity/first/firstPeVotePaper_toVote.action?bean.id=${value}\" target=\"_blank\">进入</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看结果"),
				"<a href=\"/entity/first/firstPeVotePaper_voteResult.action?bean.id=${value}\" target=\"_blank\">查看</a>", "id");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeVotePaper.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/information/peVotePaperList";
	}

	public Page list() {
		Page page = null;
		StringBuffer sb = new StringBuffer();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			sb.append(" SELECT PVP.ID, ");
			sb.append("        PVP.TITLE, ");
			sb.append("        EC2.NAME, ");
			sb.append("        TO_CHAR(PVP.END_DATE, 'yyyy-MM-dd') ENDDATE ");
			sb.append("   FROM PE_VOTE_PAPER PVP ");
			sb.append("  INNER JOIN ENUM_CONST EC1 ");
			sb.append("     ON PVP.FLAG_ISVALID = EC1.ID ");
			sb.append("  INNER JOIN ENUM_CONST EC2 ");
			sb.append("     ON PVP.FLAG_TYPE = EC2.ID ");
			sb.append("  WHERE EC1.CODE = '1' ");
			if ("24".indexOf(us.getUserLoginType()) >= 0)
				sb.append("    AND (EC2.CODE = '9' OR EC2.CODE = '91') ");
			else
				sb.append("    AND (EC2.CODE = '9' OR EC2.id = 'jgman') ");
			sb.append("    AND PVP.TYPE IS NULL ");
			// 拼查询条件
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
				/* 类型 */
				if (name.equalsIgnoreCase("ENUMCONSTBYFLAGQUALIFICATIONSTYPE.NAME")) {
					name = "EC2.NAME";
				}
				sb.append(" and UPPER(" + name + ") LIKE UPPER('%" + value + "%')");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 类型 */
				if (temp.equalsIgnoreCase("ENUMCONSTBYFLAGQUALIFICATIONSTYPE.NAME")) {
					temp = "EC2.NAME";
				}
				if (temp.equals("id")) {
					temp = "PVP.END_DATE";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					sb.append(" order by " + temp + " desc ");
				} else {
					sb.append(" order by " + temp + " asc ");
				}
			} else {
				sb.append(" order by PVP.END_DATE desc");
			}
			page = this.getGeneralService().getByPageSQL(sb.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	public void setBean(PeVotePaper instance) {
		super.superSetBean(instance);

	}

	public PeVotePaper getBean() {
		return (PeVotePaper) super.superGetBean();
	}

}
