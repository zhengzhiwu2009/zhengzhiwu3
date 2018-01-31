package com.whaty.platform.entity.web.action.basic;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 公司内训课程查看学员_学习情况
 * 
 * @author fan
 * 
 */
public class PeInternalStuDetailAction extends MyBaseAction<StudentBatch> {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("学员列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("系统编号"), "reg_no", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("姓名"), "true_name", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("证件号码"), "card_no", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("所在机构"), "orgname", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("从事业务"), "work", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("职务"), "position", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("完成学习时间"), "COMPLETE_DATE", false, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("完成学习时间起"), "psDate", true, false, false, "Datetime", true, 100);
		this.getGridConfig().addColumn(this.getText("完成学习时间止"), "peDate", true, false, false, "Datetime", true, 100);
		this.getGridConfig().addColumn(this.getText("通过考试时间"), "Completed_Time", false, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("通过考试时间起"), "csDate", true, false, false, "Datetime", true, 100);
		this.getGridConfig().addColumn(this.getText("通过考试时间止"), "ceDate", true, false, false, "Datetime", true, 100);
		ColumnConfig ccLearnStatus = new ColumnConfig(this.getText("学习结果"), "Combobox_iscompleted", true, true, true, "TextField", false, 100, "");
		String sqlLearnStatus = "select a.id ,a.name from enum_const a where a.namespace='LearnStatus'";
		ccLearnStatus.setComboSQL(sqlLearnStatus);
		this.getGridConfig().addColumn(ccLearnStatus);
		ColumnConfig ccExamStatus = new ColumnConfig(this.getText("考试结果"), "Combobox_nopassexam", true, true, true, "TextField", false, 100, "");
		String sqlExamStatus = "select a.id ,a.name from enum_const a where a.namespace='ExamStatus'";
		ccExamStatus.setComboSQL(sqlExamStatus);
		this.getGridConfig().addColumn(ccExamStatus);
		this.getGridConfig().addRenderFunction(this.getText("详细信息"), "<a target=\"_blank\" href=\"peDetail_stuviewDetail.action?id=${value}&type=1\">查看详细信息</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("查看学习详情"),
				"<a target=\"_blank\" href=\"/entity/teaching/searchAnyStudent_courseInfos.action?allid=${value}\">查看学习详情</a>", "id");
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
	}

	public void setEntityClass() {
		this.entityClass = StudentBatch.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peInternalStuDetail";
	}

	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		// 查询集体Code
		String comCodeSql = "";
		// 机构ID
		String enterpriseIdSql = "";
		if ("2".equals(us.getUserLoginType())) {// 一级集体
			comCodeSql = " SELECT LOGIN_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'";
			enterpriseIdSql = "SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId()
					+ "' UNION SELECT ID FROM PE_ENTERPRISE WHERE FK_PARENT_ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '"
					+ us.getSsoUser().getId() + "')";
		}
		if ("4".equals(us.getUserLoginType())) {// 二级集体
			comCodeSql = "SELECT CODE FROM PE_ENTERPRISE WHERE ID = (SELECT FK_PARENT_ID FROM PE_ENTERPRISE WHERE ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '"
					+ us.getSsoUser().getId() + "'))";
			enterpriseIdSql = "SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'";
		}
		if ("3".equals(us.getUserLoginType())) {// 协会端
			sqlBuffer.append(" SELECT * ");
			sqlBuffer.append("   FROM (SELECT DISTINCT PBS.ID || '-' || PBTC.ID || '-' || PBTO.ID AS ID, ");
			sqlBuffer.append("                         PBS.REG_NO, ");
			sqlBuffer.append("                         PBS.TRUE_NAME, ");
			sqlBuffer.append("                         PBS.CARD_NO, ");
			sqlBuffer.append("                         PE.NAME AS ORGNAME, ");
			sqlBuffer.append("                         PBS.WORK, ");
			sqlBuffer.append("                         PBS.POSITION, ");
			sqlBuffer.append("                         DECODE(TCS.COMPLETE_DATE,NULL,'-',TO_CHAR(TCS.COMPLETE_DATE, 'yyyy-MM-dd hh24:mi:ss')) COMPLETE_DATE, ");
			sqlBuffer.append("                         '' AS PSDATE, ");
			sqlBuffer.append("                         '' AS PEDATE, ");
			sqlBuffer.append("                         DECODE(EC2.CODE, ");
			sqlBuffer.append("                                0, ");
			sqlBuffer.append("                                '-', ");
			sqlBuffer.append("                                DECODE(PBTSE.COMPLETED_TIME,NULL,'-',TO_CHAR(PBTSE.COMPLETED_TIME, ");
			sqlBuffer.append("                                        'yyyy-MM-dd hh24:mi:ss'))) COMPLETED_TIME, ");
			sqlBuffer.append("                         '' AS CSDATE, ");
			sqlBuffer.append("                         '' AS CEDATE, ");
			sqlBuffer.append("                         CASE ");
			sqlBuffer.append("                           WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
			sqlBuffer.append("                            '已完成学习' ");
			sqlBuffer.append("                           WHEN TCS.LEARN_STATUS = 'INCOMPLETE' THEN ");
			sqlBuffer.append("                            '正在学习' ");
			sqlBuffer.append("                           ELSE ");
			sqlBuffer.append("                            '未开始学习' ");
			sqlBuffer.append("                         END AS Combobox_iscompleted, ");
			sqlBuffer.append("                         CASE ");
			sqlBuffer.append("                           WHEN EC2.CODE = '1' AND PBTSE.ISPASS = '1' THEN ");
			sqlBuffer.append("                            '已通过考试' ");
			sqlBuffer.append("                           WHEN EC2.CODE = '0' THEN ");
			sqlBuffer.append("                            '不考试' ");
			sqlBuffer.append("                           ELSE ");
			sqlBuffer.append("                            '未通过考试' ");
			sqlBuffer.append("                         END AS COMBOBOX_NOPASSEXAM, ");
			sqlBuffer.append("                         PBTC.ID PBTC_ID ");
			sqlBuffer.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuffer.append("             ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuffer.append("            AND PBTC.ID = '" + this.getId() + "' ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST EC2 ");
			sqlBuffer.append("             ON PBTC.FLAG_IS_EXAM = EC2.ID ");
			sqlBuffer.append("          INNER JOIN STU_INTERNAL SI ");
			sqlBuffer.append("             ON PBTO.ID = SI.FK_TCH_OPENCOURSE_ID ");
			sqlBuffer.append("          INNER JOIN PE_BZZ_STUDENT PBS ");
			sqlBuffer.append("             ON SI.FK_STU_ID = PBS.ID ");
			sqlBuffer.append("          INNER JOIN SSO_USER SU ");
			sqlBuffer.append("             ON PBS.FK_SSO_USER_ID = SU.ID ");
			sqlBuffer.append("            AND SU.FLAG_ISVALID = '2' ");
			sqlBuffer.append("          INNER JOIN PE_ENTERPRISE PE ");
			sqlBuffer.append("             ON PBS.FK_ENTERPRISE_ID = PE.ID ");
			sqlBuffer.append("           LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sqlBuffer.append("             ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
			sqlBuffer.append("            AND PBTSE.FK_STU_ID = PBS.ID ");
			sqlBuffer.append("           LEFT JOIN TRAINING_COURSE_STUDENT TCS ");
			sqlBuffer.append("             ON PBTSE.FK_TRAINING_ID = TCS.ID WHERE 1=1 ");
		}
		if ("2".equals(us.getUserLoginType()) || "4".equals(us.getUserLoginType())) {// 集体管理员
			sqlBuffer.append(" SELECT * ");
			sqlBuffer.append("   FROM (SELECT DISTINCT PBS.ID || '-' || PBTC.ID || '-' || PBTO.ID AS ID, ");
			sqlBuffer.append("                         PBS.REG_NO, ");
			sqlBuffer.append("                         PBS.TRUE_NAME, ");
			sqlBuffer.append("                         PBS.CARD_NO, ");
			sqlBuffer.append("                         PE.NAME AS ORGNAME, ");
			sqlBuffer.append("                         PBS.WORK, ");
			sqlBuffer.append("                         PBS.POSITION, ");
			sqlBuffer.append("                         DECODE(TCS.COMPLETE_DATE,NULL,'-',TO_CHAR(TCS.COMPLETE_DATE, 'yyyy-MM-dd hh24:mi:ss')) COMPLETE_DATE, ");
			sqlBuffer.append("                         '' AS PSDATE, ");
			sqlBuffer.append("                         '' AS PEDATE, ");
			sqlBuffer.append("                         DECODE(EC2.CODE, ");
			sqlBuffer.append("                                0, ");
			sqlBuffer.append("                                '-', ");
			sqlBuffer.append("                                DECODE(PBTSE.COMPLETED_TIME,NULL,'-',TO_CHAR(PBTSE.COMPLETED_TIME, ");
			sqlBuffer.append("                                        'yyyy-MM-dd hh24:mi:ss'))) COMPLETED_TIME, ");
			sqlBuffer.append("                         '' AS CSDATE, ");
			sqlBuffer.append("                         '' AS CEDATE, ");
			sqlBuffer.append("                         CASE ");
			sqlBuffer.append("                           WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
			sqlBuffer.append("                            '已完成学习' ");
			sqlBuffer.append("                           WHEN TCS.LEARN_STATUS = 'INCOMPLETE' THEN ");
			sqlBuffer.append("                            '正在学习' ");
			sqlBuffer.append("                           ELSE ");
			sqlBuffer.append("                            '未开始学习' ");
			sqlBuffer.append("                         END AS Combobox_iscompleted, ");
			sqlBuffer.append("                         CASE ");
			sqlBuffer.append("                           WHEN EC2.CODE = '1' AND PBTSE.ISPASS = '1' THEN ");
			sqlBuffer.append("                            '已通过考试' ");
			sqlBuffer.append("                           WHEN EC2.CODE = '0' THEN ");
			sqlBuffer.append("                            '不考试' ");
			sqlBuffer.append("                           ELSE ");
			sqlBuffer.append("                            '未通过考试' ");
			sqlBuffer.append("                         END AS COMBOBOX_NOPASSEXAM, ");
			sqlBuffer.append("                         PBTC.ID PBTC_ID ");
			sqlBuffer.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuffer.append("             ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuffer.append("            AND PBTC.ID = '" + this.getId() + "' ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST EC2 ");
			sqlBuffer.append("             ON PBTC.FLAG_IS_EXAM = EC2.ID ");
			sqlBuffer.append("          INNER JOIN STU_INTERNAL SI ");
			sqlBuffer.append("             ON PBTO.ID = SI.FK_TCH_OPENCOURSE_ID ");
			sqlBuffer.append("          INNER JOIN PE_BZZ_STUDENT PBS ");
			sqlBuffer.append("             ON SI.FK_STU_ID = PBS.ID ");
			sqlBuffer.append("             AND PBS.FK_ENTERPRISE_ID IN (" + enterpriseIdSql + ") ");
			sqlBuffer.append("          INNER JOIN SSO_USER SU ");
			sqlBuffer.append("             ON PBS.FK_SSO_USER_ID = SU.ID ");
			sqlBuffer.append("            AND SU.FLAG_ISVALID = '2' ");
			sqlBuffer.append("          INNER JOIN PE_ENTERPRISE PE ");
			sqlBuffer.append("             ON PBS.FK_ENTERPRISE_ID = PE.ID ");
			sqlBuffer.append("           LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sqlBuffer.append("             ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
			sqlBuffer.append("            AND PBTSE.FK_STU_ID = PBS.ID ");
			sqlBuffer.append("           LEFT JOIN TRAINING_COURSE_STUDENT TCS ");
			sqlBuffer.append("             ON PBTSE.FK_TRAINING_ID = TCS.ID WHERE 1=1");
		}
		try {
			// 处理时间的查询参数
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					/* 获得学时时间起止 */
					if (params.get("search__psDate") != null) {
						String[] startDateST = (String[]) params.get("search__psDate");
						String tempDate = startDateST[0];
						if (startDateST.length == 1 && !StringUtils.defaultString(startDateST[0]).equals("")) {
							params.remove("search__psDate");
							context.setParameters(params);
							sqlBuffer.append("           and  tcs.complete_date >= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					if (params.get("search__peDate") != null) {
						String[] startDateED = (String[]) params.get("search__peDate");
						String tempDate = startDateED[0];
						if (startDateED.length == 1 && !StringUtils.defaultString(startDateED[0]).equals("")) {
							params.remove("search__peDate");
							context.setParameters(params);
							sqlBuffer.append("           and tcs.complete_date <= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					/* 通过考试时间 */
					if (params.get("search__csDate") != null) {
						String[] endDateST = (String[]) params.get("search__csDate");
						String tempDate = endDateST[0];
						if (endDateST.length == 1 && !StringUtils.defaultString(endDateST[0]).equals("")) {
							params.remove("search__csDate");
							context.setParameters(params);
							sqlBuffer.append("           and  PBTSE.Completed_Time >= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					if (params.get("search__ceDate") != null) {
						String[] endDateED = (String[]) params.get("search__ceDate");
						String tempDate = endDateED[0];
						if (endDateED.length == 1 && !StringUtils.defaultString(endDateED[0]).equals("")) {
							params.remove("search__ceDate");
							context.setParameters(params);
							sqlBuffer.append("           and PBTSE.Completed_Time <= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					/* 学习状态 */
					if (params.get("Combobox_iscompleted") != null) {
						String[] iscompletedArr = (String[]) params.get("Combobox_iscompleted");
						if (iscompletedArr.length == 1 && !StringUtils.defaultString(iscompletedArr[0]).equals("")) {
							sqlBuffer.append(" AND COMBOBOX_ISCOMPLETED LIKE '%" + iscompletedArr[0] + "%' ");
							params.remove("iscompletedArr");
						}
					}
					/* 考试结果 */
					if (params.get("Combobox_nopassexam") != null) {
						String[] nopassexamArr = (String[]) params.get("Combobox_nopassexam");
						if (nopassexamArr.length == 1 && !StringUtils.defaultString(nopassexamArr[0]).equals("")) {
							sqlBuffer.append(" AND COMBOBOX_NOPASSEXAM LIKE '%" + nopassexamArr[0] + "%' ");
							params.remove("iscompletedArr");
						}
					}
					context.setParameters(params);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		sqlBuffer.append(" ) WHERE 1 = 1 ");
		try {
			this.setSqlCondition(sqlBuffer);
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
}
