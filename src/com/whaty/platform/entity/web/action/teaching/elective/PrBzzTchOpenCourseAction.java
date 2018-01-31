package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PrBzzTchOpenCourseAction extends MyBaseAction {

	private List<PeBzzTchCourse> tchCourse;
	private String id;
	private PeEnterprise peEnterprise;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<PeBzzTchCourse> getTchCourse() {
		return tchCourse;
	}

	public void setTchCourse(List<PeBzzTchCourse> tchCourse) {
		this.tchCourse = tchCourse;
	}

	/**
	 * 初始化列表
	 * 
	 * @author wuhao
	 * @return
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("专项培训查询");
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("专项培训名称"), "name", true);
		this.getGridConfig().addColumn(this.getText("发布时间"), "fabuDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("发布时间"), "fd", false);
		this.getGridConfig().addColumn(this.getText("报名开始时间起"), "startsDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("报名开始时间"), "sd", false);
		this.getGridConfig().addColumn(this.getText("报名开始时间止"), "starteDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("报名结束时间起"), "endsDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("报名结束时间"), "ed", false);
		this.getGridConfig().addColumn(this.getText("报名结束时间止"), "endeDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("建议学时数"), "SUGG_TIME", false);
		this.getGridConfig().addColumn(this.getText("应培训人数"), "STUNUM", false);
		this.getGridConfig().addColumn(this.getText("实际报名人数"), "PAYSTUNUM", false);
		this.getGridConfig().addColumn(this.getText("实际收费金额(元)"), "SFFEE", false, true, true, "");
		this.getGridConfig().addRenderFunction(this.getText("查看必选课程"), "<a href=\"prBzzTchOpenCourseDetailSearch.action?id=${value}&method=bxCourse\">查看必选课程</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看自选课程"), "<a href=\"prBzzTchOpenCourseDetailSearch.action?id=${value}&method=xxCourse\">查看自选课程</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看学员"), "<a href=\"/entity/basic/peStuDetail.action?id=${value}\">查看学员</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看描述"), "<a href=\"prBzzTchOpenCourseDetailSearch_showDiscription.action?id=${value}\" target=_blank>查看描述</a>", "id");
	}

	/**
	 * 重写框架方法：专项信息（带sql条件）
	 * 
	 * @author wuhao
	 * @return
	 */
	public Page list() {
		Page page = null;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String condition = "";
		if (us.getUserLoginType().equals("3")) {// 协会管理员
			condition = "LEFT";
		}
		if (us.getUserLoginType().equals("2") || us.getUserLoginType().equals("4")) {// 一级集体管理员、二级集体管理员
			condition = "INNER";
		}
		String sql_con = "";

		if (!us.getUserLoginType().equals("3")) {
			try {
				this.peEnterprise = this.getGeneralService().getEnterpriseManagerByLoginId(us.getLoginId()).getPeEnterprise();
				ServletActionContext.getRequest().getSession().setAttribute("peEnterprise", this.peEnterprise);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
			if (us.getUserLoginType().equals("2") || us.getUserLoginType().equals("4")) {// 一级集体管理员、二级集体管理员
				sql_con = " and ps.fk_enterprise_id in (SELECT ID FROM PE_ENTERPRISE WHERE ID IN (select fk_enterprise_id from pe_enterprise_manager where fk_sso_user_id='" + us.getSsoUser().getId() + "') OR FK_PARENT_ID IN (select fk_enterprise_id from pe_enterprise_manager where fk_sso_user_id='"
						+ us.getSsoUser().getId() + "'))";
			}
		}
		StringBuffer sqlBuffer = new StringBuffer();
		if (us.getUserLoginType().equals("2")) {
			sqlBuffer.append(" SELECT * ");
			sqlBuffer.append("   FROM (SELECT PB.ID AS ID, ");
			sqlBuffer.append("                PB.NAME AS NAME, ");
			sqlBuffer.append(" '' fabudate,");
			sqlBuffer.append("		 to_char(pb.announce_date,'yyyy-MM-dd')  as fd ,");
			sqlBuffer.append("                '' STARTSDATE, ");
			sqlBuffer.append("                PB.START_TIME AS SD, ");
			sqlBuffer.append("                '' STARTEDATE, ");
			sqlBuffer.append("                '' ENDSDATE, ");
			sqlBuffer.append("                PB.END_TIME AS ED, ");
			sqlBuffer.append("                '' ENDEDATE, ");
			sqlBuffer.append("                NVL(PB.SUGG_TIME, 0) SUGG_TIME, ");
			sqlBuffer.append("                NVL(A.STUNUM, 0) STUNUM, ");
			sqlBuffer.append("                NVL(A.PAYSTUNUM, 0) PAYSTUNUM ");
			sqlBuffer.append("                ,NVL(POIPB.SFFEE, 0) SFFEE ");
			sqlBuffer.append("           FROM (SELECT PB.ID AS ID, ");
			sqlBuffer.append("                        COUNT(DISTINCT PS.ID) AS STUNUM, ");
			sqlBuffer.append("                        COUNT(T.ID) AS PAYSTUNUM ");
			sqlBuffer.append("                   FROM PE_BZZ_BATCH PB ");
			sqlBuffer.append(" INNER JOIN ENUM_CONST ECD ON PB.FLAG_DEPLOY = ECD.ID AND ECD.CODE = '1' ");// 已发布状态
			sqlBuffer.append("                    " + condition + " JOIN (SELECT PS.ID, SB.BATCH_ID, SB.FLAG_BATCHPAYSTATE ");
			sqlBuffer.append("                               FROM PE_BZZ_STUDENT PS, ");
			sqlBuffer.append("                                    SSO_USER       SU, ");
			sqlBuffer.append("                                    STU_BATCH      SB ");
			sqlBuffer.append("                              WHERE PS.FK_SSO_USER_ID = SU.ID ");
			sqlBuffer.append(sql_con);
			sqlBuffer.append("                                AND SB.STU_ID = PS.ID ");
			sqlBuffer.append(" ) PS ");
			sqlBuffer.append("                     ON PS.BATCH_ID = PB.ID ");
			sqlBuffer.append("                 LEFT JOIN ENUM_CONST T ");
			sqlBuffer.append("                     ON T.ID = PS.FLAG_BATCHPAYSTATE ");
			sqlBuffer.append("                    AND T.CODE = '1' ");// 已支付
			sqlBuffer.append("                  GROUP BY PB.ID) A ");
			sqlBuffer.append("          INNER JOIN PE_BZZ_BATCH PB ");
			sqlBuffer.append("             ON A.ID = PB.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST E ");
			sqlBuffer.append("             ON PB.FLAG_BATCH_TYPE = E.ID ");
			sqlBuffer.append("            AND E.CODE = '0' ");
			sqlBuffer.append("            LEFT JOIN  ( " );
			sqlBuffer.append(" SELECT SUM(PBTC.PRICE) AS SFFEE, PBTO.FK_BATCH_ID ");
			sqlBuffer.append("   FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE, ");
			sqlBuffer.append("        PE_BZZ_STUDENT          PBS, ");
			sqlBuffer.append("        PR_BZZ_TCH_OPENCOURSE   PBTO, ");
			sqlBuffer.append("        PE_BZZ_TCH_COURSE       PBTC ");
			sqlBuffer.append("  WHERE PBTSE.FK_STU_ID = PBS.ID ");
			sqlBuffer.append("    AND PBS.FK_ENTERPRISE_ID IN ");
			sqlBuffer.append("        (SELECT ID ");
			sqlBuffer.append("           FROM PE_ENTERPRISE ");
			sqlBuffer.append("          WHERE FK_PARENT_ID = '" + peEnterprise.getId() + "' ");
			sqlBuffer.append("         UNION ");
			sqlBuffer.append("         SELECT '" + peEnterprise.getId() + "' FROM DUAL) ");
			sqlBuffer.append("    AND PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
			sqlBuffer.append("    AND PBTO.FK_COURSE_ID = PBTC.ID ");
			sqlBuffer.append("     GROUP BY PBTO.FK_BATCH_ID ");
			sqlBuffer.append("                                  ) POIPB	ON POIPB.FK_BATCH_ID = PB.ID");
		} else if (us.getUserLoginType().equals("4")) {
			sqlBuffer.append(" SELECT * ");
			sqlBuffer.append("   FROM (SELECT PB.ID AS ID, ");
			sqlBuffer.append("                PB.NAME AS NAME, ");
			sqlBuffer.append("                '' FABUDATE, ");
			sqlBuffer.append("                TO_CHAR(PB.ANNOUNCE_DATE, 'yyyy-MM-dd') AS FD, ");
			sqlBuffer.append("                '' STARTSDATE, ");
			sqlBuffer.append("                PB.START_TIME AS SD, ");
			sqlBuffer.append("                '' STARTEDATE, ");
			sqlBuffer.append("                '' ENDSDATE, ");
			sqlBuffer.append("                PB.END_TIME AS ED, ");
			sqlBuffer.append("                '' ENDEDATE, ");
			sqlBuffer.append("                NVL(PB.SUGG_TIME, 0) SUGG_TIME, ");
			sqlBuffer.append("                NVL(A.STUNUM, 0) STUNUM, ");
			sqlBuffer.append("                NVL(A.PAYSTUNUM, 0) PAYSTUNUM, ");
			sqlBuffer.append("                NVL(A.PRICES, 0) SFFEE ");
			sqlBuffer.append("           FROM (SELECT PB.ID AS ID, ");
			sqlBuffer.append("                        COUNT(DISTINCT PS.ID) AS STUNUM, ");
			sqlBuffer.append("                        COUNT(DISTINCT CASE WHEN T.CODE = '1' THEN PS.ID ELSE NULL END) AS PAYSTUNUM, ");
			sqlBuffer.append("                        SUM(DECODE(PBTSE.FK_STU_ID, NULL, NULL, PBTC.PRICE)) AS PRICES ");
			sqlBuffer.append("                   FROM PE_BZZ_BATCH PB ");
			sqlBuffer.append("                  INNER JOIN ENUM_CONST ECD ");
			sqlBuffer.append("                     ON PB.FLAG_DEPLOY = ECD.ID ");
			sqlBuffer.append("                    AND ECD.CODE = '1' ");
			sqlBuffer.append("                  INNER JOIN (SELECT PS.ID, SB.BATCH_ID, SB.FLAG_BATCHPAYSTATE ");
			sqlBuffer.append("                               FROM PE_BZZ_STUDENT PS, ");
			sqlBuffer.append("                                    SSO_USER       SU, ");
			sqlBuffer.append("                                    STU_BATCH      SB ");
			sqlBuffer.append("                              WHERE PS.FK_SSO_USER_ID = SU.ID ");
			sqlBuffer.append("                                AND PS.FK_ENTERPRISE_ID IN ");
			sqlBuffer.append("                                    (SELECT ID ");
			sqlBuffer.append("                                       FROM PE_ENTERPRISE ");
			sqlBuffer.append("                                      WHERE ID IN ");
			sqlBuffer.append("                                            (SELECT FK_ENTERPRISE_ID ");
			sqlBuffer.append("                                               FROM PE_ENTERPRISE_MANAGER ");
			sqlBuffer.append("                                              WHERE FK_SSO_USER_ID = ");
			sqlBuffer.append("                                                    '" + us.getSsoUser().getId() + "') ");
			sqlBuffer.append("                                         OR FK_PARENT_ID IN ");
			sqlBuffer.append("                                            (SELECT FK_ENTERPRISE_ID ");
			sqlBuffer.append("                                               FROM PE_ENTERPRISE_MANAGER ");
			sqlBuffer.append("                                              WHERE FK_SSO_USER_ID = ");
			sqlBuffer.append("                                                    '" + us.getSsoUser().getId() + "')) ");
			sqlBuffer.append("                                AND SB.STU_ID = PS.ID) PS ");
			sqlBuffer.append("                     ON PS.BATCH_ID = PB.ID ");
			sqlBuffer.append("                  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuffer.append("                     ON PS.BATCH_ID = PBTO.FK_BATCH_ID ");
			sqlBuffer.append("                  INNER JOIN PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("                     ON PBTO.FK_COURSE_ID = PBTC.ID ");
			sqlBuffer.append("                   LEFT JOIN ENUM_CONST T ");
			sqlBuffer.append("                     ON T.ID = PS.FLAG_BATCHPAYSTATE ");
			sqlBuffer.append("                    AND T.CODE = '1' ");
			sqlBuffer.append("                   LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sqlBuffer.append("                     ON PS.ID = PBTSE.FK_STU_ID ");
			sqlBuffer.append("                    AND PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
			sqlBuffer.append("                  GROUP BY PB.ID) A ");
			sqlBuffer.append("          INNER JOIN PE_BZZ_BATCH PB ");
			sqlBuffer.append("             ON A.ID = PB.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST E ");
			sqlBuffer.append("             ON PB.FLAG_BATCH_TYPE = E.ID ");
			sqlBuffer.append("            AND E.CODE = '0' ");
		} else {
			sqlBuffer.append(" SELECT * ");
			sqlBuffer.append("   FROM (SELECT PB.ID AS ID, ");
			sqlBuffer.append("                PB.NAME AS NAME, ");
			sqlBuffer.append(" '' fabudate,");
			sqlBuffer.append("		 to_char(pb.announce_date,'yyyy-MM-dd')  as fd ,");
			sqlBuffer.append("                '' STARTSDATE, ");
			sqlBuffer.append("                PB.START_TIME AS SD, ");
			sqlBuffer.append("                '' STARTEDATE, ");
			sqlBuffer.append("                '' ENDSDATE, ");
			sqlBuffer.append("                PB.END_TIME AS ED, ");
			sqlBuffer.append("                '' ENDEDATE, ");
			sqlBuffer.append("                NVL(PB.SUGG_TIME, 0) SUGG_TIME, ");
			sqlBuffer.append("                NVL(A.STUNUM, 0) STUNUM, ");
			sqlBuffer.append("                NVL(A.PAYSTUNUM, 0) PAYSTUNUM ");
			sqlBuffer.append("                ,NVL(POIPB.SFFEE, 0) SFFEE ");
			sqlBuffer.append("           FROM (SELECT PB.ID AS ID, ");
			sqlBuffer.append("                        COUNT(DISTINCT PS.ID) AS STUNUM, ");
			sqlBuffer.append("                        COUNT(T.ID) AS PAYSTUNUM ");
			sqlBuffer.append("                   FROM PE_BZZ_BATCH PB ");
			sqlBuffer.append(" INNER JOIN ENUM_CONST ECD ON PB.FLAG_DEPLOY = ECD.ID AND ECD.CODE = '1' ");// 已发布状态
			sqlBuffer.append("                    " + condition + " JOIN (SELECT PS.ID, SB.BATCH_ID, SB.FLAG_BATCHPAYSTATE ");
			sqlBuffer.append("                               FROM PE_BZZ_STUDENT PS, ");
			sqlBuffer.append("                                    SSO_USER       SU, ");
			sqlBuffer.append("                                    STU_BATCH      SB ");
			sqlBuffer.append("                              WHERE PS.FK_SSO_USER_ID = SU.ID ");
			sqlBuffer.append(sql_con);
			sqlBuffer.append("                                AND SB.STU_ID = PS.ID ");
			sqlBuffer.append(" ) PS ");
			sqlBuffer.append("                     ON PS.BATCH_ID = PB.ID ");
			sqlBuffer.append("                 LEFT JOIN ENUM_CONST T ");
			sqlBuffer.append("                     ON T.ID = PS.FLAG_BATCHPAYSTATE ");
			sqlBuffer.append("                    AND T.CODE = '1' ");// 已支付
			sqlBuffer.append("                  GROUP BY PB.ID) A ");
			sqlBuffer.append("          INNER JOIN PE_BZZ_BATCH PB ");
			sqlBuffer.append("             ON A.ID = PB.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST E ");
			sqlBuffer.append("             ON PB.FLAG_BATCH_TYPE = E.ID ");
			sqlBuffer.append("            AND E.CODE = '0' ");
			sqlBuffer.append("           LEFT JOIN (SELECT SUM(PBTC.PRICE) AS SFFEE,PBTO.FK_BATCH_ID ");
			sqlBuffer.append("                       FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sqlBuffer.append("                      INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuffer.append("                         ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
			sqlBuffer.append("                      INNER JOIN PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("                         ON PBTO.FK_COURSE_ID = PBTC.ID ");
			sqlBuffer.append("                         WHERE PBTO.FK_BATCH_ID IS NOT NULL ");
			sqlBuffer.append("                      GROUP BY PBTO.FK_BATCH_ID) POIPB ");
			sqlBuffer.append("             ON POIPB.FK_BATCH_ID = PB.ID ");
		}
		sqlBuffer.append("          WHERE PB.ID = A.ID ) WHERE 1 = 1 ");
		try {
			// 处理时间的查询参数
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					/* 发布时间 */
					if (params.get("search__fabuDate") != null) {
						String[] startDate = (String[]) params.get("search__fabuDate");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND FD ='" + startDate[0] + "'");
							params.remove("search__fabuDate");
						}
					}
					/* 报名开始时间起止 */
					if (params.get("search__startsDate") != null) {
						String[] startDate = (String[]) params.get("search__startsDate");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND SD >= to_date('" + startDate[0] + "','yyyy-MM-dd')");
							params.remove("search__startsDate");
						}
					}
					if (params.get("search__starteDate") != null) {
						String[] startDate = (String[]) params.get("search__starteDate");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND SD <= to_date('" + startDate[0] + "','yyyy-MM-dd')");
							params.remove("search__starteDate");
						}
					}
					/* 报名结束时间起止 */
					if (params.get("search__endsDate") != null) {
						String[] startDate = (String[]) params.get("search__endsDate");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND ED >= to_date('" + startDate[0] + "','yyyy-MM-dd')");
							params.remove("search__endsDate");
						}
					}
					if (params.get("search__endeDate") != null) {
						String[] startDate = (String[]) params.get("search__endeDate");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND ED <= to_date('" + startDate[0] + "','yyyy-MM-dd')");
							params.remove("search__endeDate");
						}
					}
					context.setParameters(params);
				}
			}
			this.setSqlCondition(sqlBuffer);
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	public void setEntityClass() {
		this.entityClass = PrBzzTchOpencourse.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/teaching/prBzzTchOpenCourse";

	}

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author wuhao
	 * @return
	 */
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dcd = DetachedCriteria.forClass(PeBzzBatch.class);
		return dcd;
	}

	/**
	 * 框获得课程类型
	 * 
	 * @author wuhao
	 * @return
	 */
	public String getType() {
		DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("note", "课程性质"));
		dc.addOrder(Order.asc("code"));
		try {
			this.setTchCourse(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "front";
	}

	public PeEnterprise getPeEnterprise() {
		return peEnterprise;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}
}
