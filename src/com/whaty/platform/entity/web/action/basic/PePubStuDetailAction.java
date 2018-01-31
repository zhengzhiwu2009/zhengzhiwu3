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
 * 公共课程查看学员_学习情况
 * 
 * @author fan
 * 
 */
public class PePubStuDetailAction extends MyBaseAction<StudentBatch> {
	private String id;
	private String isFree;// 课程是否免费

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
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
		this.getGridConfig().addColumn(this.getText("资格类型"), "zigename", false, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("从事业务"), "work", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("职务"), "position", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("支付时间"), "payment_date", false, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("支付时间起"), "psDate", true, false, false, "textField");
		this.getGridConfig().addColumn(this.getText("支付时间止"), "peDate", true, false, false, "textField");
		this.getGridConfig().addColumn(this.getText("获得学时时间"), "completed_time", false, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("获得学时时间起"), "csDate", true, false, false, "textField");
		this.getGridConfig().addColumn(this.getText("获得学时时间止"), "ceDate", true, false, false, "textField");

		ColumnConfig ccLearnStatus = new ColumnConfig(this.getText("学习结果"), "Combobox_iscompleted", true, true, true, "TextField", false, 100, "");
		String sqlLearnStatus = "select a.id ,a.name from enum_const a where a.namespace='LearnStatus'";
		ccLearnStatus.setComboSQL(sqlLearnStatus);
		this.getGridConfig().addColumn(ccLearnStatus);

		ColumnConfig ccExamStatus = new ColumnConfig(this.getText("考试结果"), "Combobox_nopassexam", true, true, true, "TextField", false, 100, "");
		String sqlExamStatus = "select a.id ,a.name from enum_const a where a.namespace='ExamStatus'";
		ccExamStatus.setComboSQL(sqlExamStatus);
		this.getGridConfig().addColumn(ccExamStatus);

		this.getGridConfig().addRenderFunction(this.getText("详细信息"), "<a target=\"_blank\" href=\"peDetail_stuviewDetail.action?id=${value}&type=1\">查看详细信息</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("学习详情"), "<a href=\"/entity/basic/pePubStuLearnDetail.action?id=${value}\">学习详情</a>", "id");
		// this.getGridConfig().addMenuScript(this.getText("返回"),
		// "{history.back()}");

	}

	public void setEntityClass() {
		this.entityClass = StudentBatch.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/pePubStuDetail";
	}

	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		// Lee 新SQL
		sqlBuffer.append(" SELECT * FROM ( ");
		sqlBuffer.append(" SELECT DISTINCT PBS.ID, PBS.REG_NO, PBS.TRUE_NAME, PBS.CARD_NO, PE.NAME AS ORGNAME, ");
		sqlBuffer.append(" ECZIGE.NAME ZIGENAME, PBS.WORK, PBS.POSITION, PBOI.PAYMENT_DATE, ");
		sqlBuffer.append(" '' AS PSDATE, '' AS PEDATE, DECODE(PBTSE.COMPLETED_TIME, NULL, '-', TO_CHAR(PBTSE.COMPLETED_TIME, 'yyyy-MM-dd hh24:mi:ss')) COMPLETED_TIME, ");
		sqlBuffer
				.append(" '' AS CSDATE, '' AS CEDATE, CASE WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN '已完成学习' WHEN TCS.LEARN_STATUS = 'INCOMPLETE' THEN '正在学习' ELSE '未开始学习' END AS COMBOBOX_ISCOMPLETED, ");
		sqlBuffer.append(" CASE WHEN EC2.CODE = '1' AND PBTSE.ISPASS = '1' THEN '已通过考试' WHEN EC2.CODE = '0' THEN '不考试' ELSE '未通过考试' END AS COMBOBOX_NOPASSEXAM, PBTC.ID PBTC_ID ");
		sqlBuffer.append(" FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE INNER JOIN PE_BZZ_STUDENT PBS ON PBS.ID = PBTSE.FK_STU_ID ");
		if (us.getRoleId().equals("2") || us.getRoleId().equals("1")) {// 集体用户或二级用户
			sqlBuffer
					.append(" AND PBS.FK_ENTERPRISE_ID IN (SELECT ID FROM PE_ENTERPRISE WHERE ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER A WHERE A.FK_SSO_USER_ID = '"
							+ us.getSsoUser().getId()
							+ "') OR FK_PARENT_ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER A WHERE A.FK_SSO_USER_ID = '"
							+ us.getSsoUser().getId() + "'))");
		}
		sqlBuffer.append(" LEFT JOIN PE_BZZ_ORDER_INFO PBOI ON PBOI.ID = PBTSE.FK_ORDER_ID LEFT JOIN ENUM_CONST EC ON PBOI.FLAG_PAYMENT_STATE = EC.ID  ");
		sqlBuffer.append(" LEFT JOIN PE_ENTERPRISE PE ON PBS.FK_ENTERPRISE_ID = PE.ID ");
		sqlBuffer
				.append(" INNER JOIN (SELECT PBTO.FK_COURSE_ID, PBTO.ID FROM PR_BZZ_TCH_OPENCOURSE PBTO JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
		sqlBuffer.append(" LEFT JOIN PE_BZZ_ORDER_INFO PBC ON PBC.ID = PBTSE.FK_ORDER_ID LEFT JOIN ENUM_CONST EC ON PBC.FLAG_PAYMENT_STATE = EC.ID AND EC.CODE = '1' ");
		sqlBuffer.append(" WHERE PBTO.FK_COURSE_ID = '" + this.getId() + "' GROUP BY PBTO.FK_COURSE_ID, PBTO.ID) OPE ON OPE.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		sqlBuffer.append(" LEFT JOIN TRAINING_COURSE_STUDENT TCS ON PBTSE.FK_TRAINING_ID = TCS.ID AND TCS.COURSE_ID = OPE.ID ");
		sqlBuffer.append(" INNER JOIN PE_BZZ_TCH_COURSE PBTC ON PBTC.ID = OPE.FK_COURSE_ID ");
		sqlBuffer.append(" INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sqlBuffer.append(" LEFT JOIN ENUM_CONST ECZIGE ON PBS.ZIGE = ECZIGE.ID ");
		// 查询条件修改 2014-10-09 zgl
		sqlBuffer.append(" INNER JOIN ENUM_CONST EC2 ON PBTC.FLAG_IS_EXAM = EC2.ID WHERE 1 = 1");
		try {
			// 处理时间的查询参数
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					/* 支付时间起止 */
					if (params.get("search__psDate") != null) {
						String[] startDate = (String[]) params.get("search__psDate");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND PBOI.PAYMENT_DATE >= to_date('" + startDate[0] + "','yyyy-MM-dd')");
							params.remove("search__psDate");
						}
					}
					if (params.get("search__peDate") != null) {
						String[] startDate = (String[]) params.get("search__peDate");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND PBOI.PAYMENT_DATE <= to_date('" + startDate[0] + "','yyyy-MM-dd')");
							params.remove("search__peDate");
						}
					}
					/* 获得学时时间起止 */
					if (params.get("search__csDate") != null) {
						String[] startDate = (String[]) params.get("search__csDate");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND PBTSE.COMPLETED_TIME >= to_date('" + startDate[0] + "','yyyy-MM-dd')");
							params.remove("search__csDate");
						}
					}
					if (params.get("search__ceDate") != null) {
						String[] startDate = (String[]) params.get("search__ceDate");
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							sqlBuffer.append(" AND PBTSE.COMPLETED_TIME <= to_date('" + startDate[0] + "','yyyy-MM-dd')");
							params.remove("search__ceDate");
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
