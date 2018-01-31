package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeBzzTchCourseware;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchCourseService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 直播课程查询Action 2014年11月30日
 * 
 * @author Lee
 * 
 */
public class PeBzzCourseLiveSearchAction extends MyBaseAction<PeBzzTchCourse> {

	private List peBzzTchCourses;
	private String tempFlag;
	private PeBzzTchCourseware peBzzTchCourseware;
	private PeTchCourseService peTchCourseService;
	private String courseware_id;
	private String courseware_code;
	private String courseware_name;
	private String scormType;
	private String indexFile;
	private String _uploadContentType; // 文件类型属性
	private EnumConstService enumConstService;
	private double price; // 课程的价格

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * 初始化列表
	 * 
	 * @author dgh
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setTitle("直播课程查询");
		this.getGridConfig().setCapability(false, false, false);
		if ("2".equals(us.getUserLoginType()) || "4".equals(us.getUserLoginType())) {// 集体端
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("专项ID"), "batchid", false, false, false, null);
			this.getGridConfig().addColumn(this.getText("课程名称"), "name");
			this.getGridConfig().addColumn(this.getText("课程编号"), "code");
			this.getGridConfig().addColumn(this.getText("价格(元)"), "price", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("报名开始时间"), "signStartDatetime", false, true, true, Const.fee_for_extjs);
			this.getGridConfig().addColumn(this.getText("报名开始时间起"), "ssstartDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("报名开始时间止"), "ssendDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("报名结束时间"), "signEndDatetime", false, true, true, Const.fee_for_extjs);
			this.getGridConfig().addColumn(this.getText("报名结束时间起"), "sestartDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("报名结束时间止"), "seendDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("预计直播开始时间"), "estimateStartDatetime", false, true, true, Const.fee_for_extjs);
			this.getGridConfig().addColumn(this.getText("预计直播开始时间起"), "ysstartDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("预计直播开始时间止"), "ysendDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("预计直播结束时间"), "estimateEndDatetime", false, true, true, Const.fee_for_extjs);
			this.getGridConfig().addColumn(this.getText("预计直播结束时间起"), "yestartDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("预计直播结束时间止"), "yeendDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("直播学时"), "time", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("应培训人数"), "ystus", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("实际报名人数"), "stus", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("应收费金额"), "yprices", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("实际收费金额"), "prices", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("完成培训人数"), "completes", false, true, true, null);
			this.getGridConfig().addRenderScript(this.getText("查看学员"),
					"{return '<a href=\"/entity/basic/peBatchDetailEnter.action?batchid='+record.data['batchid']+'&method=list&actionUrl=liveSearch\">查看学员</a>';}", "id");
			this.getGridConfig().addRenderScript(this.getText("课程信息"), "{return '<a href=\"/entity/teaching/peBzzCourseLiveManager_sacLiveView.action?id='+${value}+'\" target=\"_blank\">课程信息</a>';}",
					"id");
		} else {
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("专项ID"), "batchid", false, false, false, null);
			this.getGridConfig().addColumn(this.getText("课程名称"), "name");
			this.getGridConfig().addColumn(this.getText("课程编号"), "code");
			this.getGridConfig().addColumn(this.getText("价格(元)"), "price", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("报名开始时间"), "signStartDatetime", false, true, true, Const.fee_for_extjs);
			this.getGridConfig().addColumn(this.getText("报名开始时间起"), "ssstartDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("报名开始时间止"), "ssendDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("报名结束时间"), "signEndDatetime", false, true, true, Const.fee_for_extjs);
			this.getGridConfig().addColumn(this.getText("报名结束时间起"), "sestartDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("报名结束时间止"), "seendDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("预计直播开始时间"), "estimateStartDatetime", false, true, true, Const.fee_for_extjs);
			this.getGridConfig().addColumn(this.getText("预计直播开始时间起"), "ysstartDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("预计直播开始时间止"), "ysendDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("预计直播结束时间"), "estimateEndDatetime", false, true, true, Const.fee_for_extjs);
			this.getGridConfig().addColumn(this.getText("预计直播结束时间起"), "yestartDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("预计直播结束时间止"), "yeendDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("学时"), "time", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("报名上限"), "maxStus", false, true, true, Const.oneFiveNum_for_extjs);
			this.getGridConfig().addColumn(this.getText("实际报名人数"), "STUS", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("实际收费金额"), "PRICES", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("完成培训人数"), "COMPLETES", false, true, true, null);
			this.getGridConfig().addRenderScript(this.getText("查看报名学员"),
					"{return '<a href=\"/entity/basic/peBatchDetail.action?batchid='+record.data['batchid']+'&method=mystudent&actionUrl=liveSearch\">查看报名学员</a>';}", "id");
			this.getGridConfig().addRenderScript(this.getText("课程信息"), "{return '<a href=\"/entity/teaching/peBzzCourseLiveManager_sacLiveView.action?id='+${value}+'\" target=\"_blank\">课程信息</a>';}",
					"id");
		}
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzTchCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peBzzCourseLiveSearch";
	}

	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);
	}

	public PeBzzTchCourse getBean() {
		return super.superGetBean();
	}

	/**
	 * 课程管理列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page list() {
		Page page = null;
		try {
			StringBuffer sqlBuffer = new StringBuffer();
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			sqlBuffer.append(" SELECT * FROM( ");
			if ("2".equals(us.getUserLoginType()) || "4".equals(us.getUserLoginType())) {// 集体端
				sqlBuffer.append(" SELECT PBTC.ID, ");
				sqlBuffer.append("        PBB.ID BATCHID, ");
				sqlBuffer.append("        PBTC.NAME, ");
				sqlBuffer.append("        PBTC.CODE, ");
				sqlBuffer.append("        PBTC.PRICE, ");
				sqlBuffer.append("        TO_CHAR(PBTC.SIGNSTARTDATE, 'yyyy-MM-dd hh24:mi') SIGNSTARTDATETIME, ");
				sqlBuffer.append("        '' SSSTARTDATETIME, ");
				sqlBuffer.append("        '' SSENDDATETIME, ");
				sqlBuffer.append("        TO_CHAR(PBTC.SIGNENDDATE, 'yyyy-MM-dd hh24:mi') SIGNENDDATETIME, ");
				sqlBuffer.append("        '' SESTARTDATETIME, ");
				sqlBuffer.append("        '' SEENDDATETIME, ");
				sqlBuffer.append("        TO_CHAR(PBTC.ESTIMATESTARTDATE, 'yyyy-MM-dd hh24:mi') ESTIMATESTARTDATE, ");
				sqlBuffer.append("        '' YSSTARTDATETIME, ");
				sqlBuffer.append("        '' YSENDDATETIME, ");
				sqlBuffer.append("        TO_CHAR(PBTC.ESTIMATEENDDATE, 'yyyy-MM-dd hh24:mi') ESTIMATEENDDATE, ");
				sqlBuffer.append("        '' YESTARTDATETIME, ");
				sqlBuffer.append("        '' YEENDDATETIME, ");
				sqlBuffer.append("        PBTC.TIME, ");
				sqlBuffer.append("        COUNT(DISTINCT SB.STU_ID) YSTUS, ");
				sqlBuffer.append("        COUNT(DECODE(EC.CODE, '1', SB.STU_ID, NULL)) STUS, ");
				sqlBuffer.append("        SUM(PBTC.PRICE) YPRICES, ");
				sqlBuffer.append("        SUM(CASE EC.CODE ");
				sqlBuffer.append("              WHEN '1' THEN ");
				sqlBuffer.append("               PBTC.PRICE ");
				sqlBuffer.append("              ELSE ");
				sqlBuffer.append("               NULL ");
				sqlBuffer.append("            END) PRICES, ");
				sqlBuffer.append("        COUNT(DECODE(PBTSE.COMPLETED_TIME, NULL, NULL, PBTSE.ID)) COMPLETES ");
				sqlBuffer.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
				sqlBuffer.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
				sqlBuffer.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
				sqlBuffer.append("    AND PBTC.FLAG_COURSEAREA = 'Coursearea0' ");
				sqlBuffer.append("    AND PBTC.FLAG_ISVALID = '2' ");
				sqlBuffer.append("  INNER JOIN PE_BZZ_BATCH PBB ");
				sqlBuffer.append("     ON PBTO.FK_BATCH_ID = PBB.ID ");
				sqlBuffer.append("  INNER JOIN ENUM_CONST EC2 ");
				sqlBuffer.append("     ON EC2.ID = PBB.FLAG_BATCH_TYPE ");
				sqlBuffer.append("    AND EC2.CODE = '2' ");
				sqlBuffer.append("  INNER JOIN STU_BATCH SB ");
				sqlBuffer.append("     ON PBB.ID = SB.BATCH_ID ");
				sqlBuffer.append("  INNER JOIN PE_BZZ_STUDENT PBS ");
				sqlBuffer.append("     ON PBS.ID = SB.STU_ID ");
				sqlBuffer.append("  INNER JOIN PE_ENTERPRISE PE ");
				sqlBuffer.append("     ON PBS.FK_ENTERPRISE_ID = PE.ID ");
				sqlBuffer.append("    AND PE.ID IN ");
				sqlBuffer.append("        (SELECT FK_ENTERPRISE_ID ");
				sqlBuffer.append("           FROM PE_ENTERPRISE_MANAGER ");
				sqlBuffer.append("          WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "' ");
				sqlBuffer.append("         UNION ALL (SELECT ID ");
				sqlBuffer.append("                     FROM PE_ENTERPRISE ");
				sqlBuffer.append("                    WHERE FK_PARENT_ID = ");
				sqlBuffer.append("                          (SELECT FK_ENTERPRISE_ID ");
				sqlBuffer.append("                             FROM PE_ENTERPRISE_MANAGER ");
				sqlBuffer.append("                            WHERE FK_SSO_USER_ID = ");
				sqlBuffer.append("                                  '" + us.getSsoUser().getId() + "'))) ");
				sqlBuffer.append("  INNER JOIN ENUM_CONST EC ");
				sqlBuffer.append("     ON SB.FLAG_BATCHPAYSTATE = EC.ID ");
				sqlBuffer.append("   LEFT JOIN PE_BZZ_ORDER_INFO PBOI ");
				sqlBuffer.append("     ON SB.FK_ORDER_ID = PBOI.ID ");
				sqlBuffer.append("   LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
				sqlBuffer.append("     ON SB.STU_ID = PBTSE.FK_STU_ID ");
				sqlBuffer.append("    AND PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
				sqlBuffer.append("  GROUP BY PBTC.ID, ");
				sqlBuffer.append("           PBB.ID, ");
				sqlBuffer.append("           PBTC.NAME, ");
				sqlBuffer.append("           PBTC.CODE, ");
				sqlBuffer.append("           PBTC.PRICE, ");
				sqlBuffer.append("           PBTC.SIGNSTARTDATE, ");
				sqlBuffer.append("           PBTC.SIGNENDDATE, ");
				sqlBuffer.append("           PBTC.ESTIMATESTARTDATE, ");
				sqlBuffer.append("           PBTC.ESTIMATEENDDATE, ");
				sqlBuffer.append("           PBTC.TIME, ");
				sqlBuffer.append("           PBTC.MAXSTUS ");
				sqlBuffer.append(" HAVING 1 = 1 ");
			} else {
				sqlBuffer.append(" SELECT PBTC.ID, ");
				sqlBuffer.append("        PBB.ID BATCHID, ");
				sqlBuffer.append("        PBTC.NAME, ");
				sqlBuffer.append("        PBTC.CODE, ");
				sqlBuffer.append("        PBTC.PRICE, ");
				sqlBuffer.append("        TO_CHAR(PBTC.SIGNSTARTDATE, 'yyyy-MM-dd hh24:mi') SIGNSTARTDATETIME, ");
				sqlBuffer.append("        '' SSSTARTDATETIME, ");
				sqlBuffer.append("        '' SSENDDATETIME, ");
				sqlBuffer.append("        TO_CHAR(PBTC.SIGNENDDATE, 'yyyy-MM-dd hh24:mi') SIGNENDDATETIME, ");
				sqlBuffer.append("        '' SESTARTDATETIME, ");
				sqlBuffer.append("        '' SEENDDATETIME, ");
				sqlBuffer.append("        TO_CHAR(PBTC.ESTIMATESTARTDATE, 'yyyy-MM-dd hh24:mi') ESTIMATESTARTDATE, ");
				sqlBuffer.append("        '' YSSTARTDATETIME, ");
				sqlBuffer.append("        '' YSENDDATETIME, ");
				sqlBuffer.append("        TO_CHAR(PBTC.ESTIMATEENDDATE, 'yyyy-MM-dd hh24:mi') ESTIMATEENDDATE, ");
				sqlBuffer.append("        '' YESTARTDATETIME, ");
				sqlBuffer.append("        '' YEENDDATETIME, ");
				sqlBuffer.append("        PBTC.TIME, ");
				sqlBuffer.append("        PBTC.MAXSTUS, ");
				sqlBuffer.append("        COUNT(DISTINCT SB.STU_ID) STUS, ");
				sqlBuffer.append("        SUM(CASE EC.CODE ");
				sqlBuffer.append("              WHEN '1' THEN ");
				sqlBuffer.append("               PBTC.PRICE ");
				sqlBuffer.append("              ELSE ");
				sqlBuffer.append("               NULL ");
				sqlBuffer.append("            END) PRICES, ");
				sqlBuffer.append("        COUNT(DECODE(PBTSE.COMPLETED_TIME, NULL, NULL, PBTSE.ID)) COMPLETES ");
				sqlBuffer.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
				sqlBuffer.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
				sqlBuffer.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
				sqlBuffer.append("    AND PBTC.FLAG_COURSEAREA = 'Coursearea0' ");// 直播课程
				sqlBuffer.append("    AND PBTC.FLAG_ISVALID = '2' ");// 已发布
				sqlBuffer.append("  INNER JOIN PE_BZZ_BATCH PBB ");
				sqlBuffer.append("     ON PBTO.FK_BATCH_ID = PBB.ID ");
				sqlBuffer.append("  INNER JOIN ENUM_CONST EC2 ");
				sqlBuffer.append("     ON EC2.ID = PBB.FLAG_BATCH_TYPE ");
				sqlBuffer.append("    AND EC2.CODE = '2' ");// 直播专项
				sqlBuffer.append("  INNER JOIN STU_BATCH SB ");
				sqlBuffer.append("     ON PBB.ID = SB.BATCH_ID ");
				sqlBuffer.append("  INNER JOIN PE_BZZ_STUDENT PBS ");
				sqlBuffer.append("     ON PBS.ID = SB.STU_ID ");

				sqlBuffer.append("  INNER JOIN ENUM_CONST EC ");
				sqlBuffer.append("     ON SB.FLAG_BATCHPAYSTATE = EC.ID ");
				sqlBuffer.append("    AND EC.CODE = '1' ");// 已支付
				sqlBuffer.append("   LEFT JOIN PE_BZZ_ORDER_INFO PBOI ");
				sqlBuffer.append("     ON SB.FK_ORDER_ID = PBOI.ID ");
				sqlBuffer.append("   LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
				sqlBuffer.append("     ON SB.STU_ID = PBTSE.FK_STU_ID ");
				sqlBuffer.append("    AND PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
				sqlBuffer.append("  GROUP BY PBTC.ID, ");
				sqlBuffer.append("           PBB.ID, ");
				sqlBuffer.append("           PBTC.NAME, ");
				sqlBuffer.append("           PBTC.CODE, ");
				sqlBuffer.append("           PBTC.PRICE, ");
				sqlBuffer.append("           PBTC.SIGNSTARTDATE, ");
				sqlBuffer.append("           PBTC.SIGNENDDATE, ");
				sqlBuffer.append("           PBTC.ESTIMATESTARTDATE, ");
				sqlBuffer.append("           PBTC.ESTIMATEENDDATE, ");
				sqlBuffer.append("           PBTC.TIME, ");
				sqlBuffer.append("           PBTC.MAXSTUS ");
				sqlBuffer.append(" HAVING 1 = 1 ");
			}
			/* 时间查询条件 */
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					/* 报名开始时间起止 */
					if (params.get("search__ssstartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__ssstartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND PBTC.SIGNSTARTDATE >= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__ssstartDatetime");
						}
					}
					if (params.get("search__ssendDatetime") != null) {
						String[] startDate = (String[]) params.get("search__ssendDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND PBTC.SIGNSTARTDATE <= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__ssendDatetime");
						}
					}
					/* 报名结束时间起止 */
					if (params.get("search__sestartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__sestartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND PBTC.SIGNENDDATE >= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__sestartDatetime");
						}
					}
					if (params.get("search__seendDatetime") != null) {
						String[] startDate = (String[]) params.get("search__seendDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND PBTC.SIGNENDDATE <= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__seendDatetime");
						}
					}
					/* 预计开始时间起止 */
					if (params.get("search__ysstartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__ysstartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND PBTC.ESTIMATESTARTDATE >= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__ysstartDatetime");
						}
					}
					if (params.get("search__ysendDatetime") != null) {
						String[] startDate = (String[]) params.get("search__ysendDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND PBTC.ESTIMATESTARTDATE <= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__ysendDatetime");
						}
					}
					/* 预计结束时间起止 */
					if (params.get("search__yestartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__yestartDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND PBTC.ESTIMATEENDDATE >= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__yestartDatetime");
						}
					}
					if (params.get("search__yeendDatetime") != null) {
						String[] startDate = (String[]) params.get("search__yeendDatetime");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND PBTC.ESTIMATEENDDATE <= to_date('" + startDate[0] + "','yyyy-MM-dd hh24:mi:ss')");
							params.remove("search__yeendDatetime");
						}
					}
					context.setParameters(params);
				}
			}
			sqlBuffer.append(" ) WHERE 1 = 1 ");
			/* 下拉列表查询条件 */
			Map params1 = this.getParamsMap();
			Iterator iterator = params1.entrySet().iterator();
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
				sqlBuffer.append(" and UPPER(" + name + ") LIKE '%" + value.toUpperCase() + "%'");
			} while (true);
			String temp = this.getSort();
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
			} else {
				sqlBuffer.append(" order by signStartDate,estimateStartDatetime desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	public String preView() {
		return "noPreView";
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public String get_uploadContentType() {
		return _uploadContentType;
	}

	public void set_uploadContentType(String contentType) {
		_uploadContentType = contentType;
	}

	public String getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	public List getPeBzzTchCourses() {
		return peBzzTchCourses;
	}

	public void setPeBzzTchCourses(List peBzzTchCourses) {
		this.peBzzTchCourses = peBzzTchCourses;
	}

	public PeBzzTchCourseware getPeBzzTchCourseware() {
		return peBzzTchCourseware;
	}

	public void setPeBzzTchCourseware(PeBzzTchCourseware peBzzTchCourseware) {
		this.peBzzTchCourseware = peBzzTchCourseware;
	}

	public PeTchCourseService getPeTchCourseService() {
		return peTchCourseService;
	}

	public void setPeTchCourseService(PeTchCourseService peTchCourseService) {
		this.peTchCourseService = peTchCourseService;
	}

	public String getCourseware_id() {
		return courseware_id;
	}

	public void setCourseware_id(String courseware_id) {
		this.courseware_id = courseware_id;
	}

	public String getCourseware_name() {
		return courseware_name;
	}

	public void setCourseware_name(String courseware_name) {
		this.courseware_name = courseware_name;
	}

	public String getCourseware_code() {
		return courseware_code;
	}

	public void setCourseware_code(String courseware_code) {
		this.courseware_code = courseware_code;
	}

	public String getIndexFile() {
		return indexFile;
	}

	public void setIndexFile(String indexFile) {
		this.indexFile = indexFile;
	}

	public String getScormType() {
		return scormType;
	}

	public void setScormType(String scormType) {
		this.scormType = scormType;
	}
}
