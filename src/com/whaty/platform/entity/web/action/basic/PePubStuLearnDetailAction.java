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
 * 公共课程查看学员
 * 
 * @author fan
 * 
 */
public class PePubStuLearnDetailAction extends MyBaseAction<StudentBatch> {

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
		this.getGridConfig().setTitle(this.getText("课程列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		;
		this.getGridConfig().addColumn(this.getText("课程编号"), "code", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("学时"), "time", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("开始学习时间"), "start_time", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("课程完成时间"), "completed_time", true, false, true, "textField");
		// this.getGridConfig().addColumn(this.getText("学习状态"), "is_completed",
		// false);
		// this.getGridConfig().addColumn(this.getText("考试结果"), "nopassexam",
		// false);

		ColumnConfig columnLearnType = new ColumnConfig(this.getText("学习状态"), "Combobox_completed", true, true, true, "TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='LearnStatus'";
		columnLearnType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnLearnType);

		ColumnConfig ccExamStatus = new ColumnConfig(this.getText("考试结果"), "Combobox_nopassexam", true, true, true, "TextField", false, 100, "");
		String sqlExamStatus = "select a.id ,a.name from enum_const a where a.namespace='ExamStatus'";
		ccExamStatus.setComboSQL(sqlExamStatus);
		this.getGridConfig().addColumn(ccExamStatus);

		// this.getGridConfig().addRenderScript(
		// this.getText("查看学习详情"),
		// "{return '<a target=\"_blank\"
		// href=\"/entity/teaching/searchAnyStudent_courseInfo.action?allid=${value}'\">查看学习详情</a>';}",
		// "id");
		this.getGridConfig().addRenderFunction(this.getText("查看学习详情"), "<a target=\"_blank\" href=\"/entity/teaching/searchAnyStudent_courseInfo.action?allid=${value}\">查看学习详情</a>", "id");
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");

	}

	public void setEntityClass() {
		this.entityClass = StudentBatch.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/pePubStuLearnDetail";
	}

	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT * ");
		sqlBuffer.append("   FROM (SELECT DISTINCT PBS.ID || '-' || PBTC.ID || '-' || OPE.ID AS ID, ");
		sqlBuffer.append("                         PBTC.CODE, ");
		sqlBuffer.append("                         PBTC.NAME, ");
		sqlBuffer.append("                         PBTC.TIME, ");
		sqlBuffer.append("                         PBTSE.START_TIME, ");
		sqlBuffer.append("                         PBTSE.COMPLETED_TIME, ");
		sqlBuffer.append("                         CASE ");
		sqlBuffer.append("                           WHEN TCS.LEARN_STATUS = 'COMPLETED' THEN ");
		sqlBuffer.append("                            '已完成学习' ");
		sqlBuffer.append("                           WHEN TCS.LEARN_STATUS = 'INCOMPLETE' THEN ");
		sqlBuffer.append("                            '正在学习' ");
		sqlBuffer.append("                           ELSE ");
		sqlBuffer.append("                            '未开始学习' ");
		sqlBuffer.append("                         END AS COMBOBOX_COMPLETED, ");
		sqlBuffer.append("                         CASE ");
		sqlBuffer.append("                           WHEN PBTC.FLAG_IS_EXAM = ");
		sqlBuffer.append("                                '40288acf3ae01103013ae012940b0001' AND ");
		sqlBuffer.append("                                PBTSE.ISPASS = '1' THEN ");
		sqlBuffer.append("                            '已通过考试' ");
		sqlBuffer.append("                           WHEN PBTC.FLAG_IS_EXAM = ");
		sqlBuffer.append("                                '40288acf3ae01103013ae0130d030002' THEN ");
		sqlBuffer.append("                            '不考试' ");
		sqlBuffer.append("                           ELSE ");
		sqlBuffer.append("                            '未通过考试' ");
		sqlBuffer.append("                         END AS COMBOBOX_NOPASSEXAM, ");
		sqlBuffer.append("                         PBTC.ID PBTC_ID, ");
		sqlBuffer.append("                         PE.CODE AS PCODE ");
		sqlBuffer.append("           FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sqlBuffer.append("           JOIN PE_BZZ_STUDENT PBS ");
		sqlBuffer.append("             ON PBS.ID = PBTSE.FK_STU_ID ");
		sqlBuffer.append("            AND PBS.ID = '" + this.getId() + "' ");
		sqlBuffer.append("           LEFT JOIN PE_BZZ_ORDER_INFO PBOI ");
		sqlBuffer.append("             ON PBOI.ID = PBTSE.FK_ORDER_ID ");
		sqlBuffer.append("           LEFT JOIN PE_ENTERPRISE PE ");
		sqlBuffer.append("             ON PBS.ENTERPRISE_ID = PE.ID ");
		sqlBuffer.append("           JOIN (SELECT PBTO.FK_COURSE_ID, PBTO.ID ");
		sqlBuffer.append("                  FROM PR_BZZ_TCH_OPENCOURSE PBTO ");
		sqlBuffer.append("                  JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSEB ");
		sqlBuffer.append("                    ON PBTSEB.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
		sqlBuffer.append("                   AND PBTSEB.FK_STU_ID = '" + this.getId() + "' ");
		sqlBuffer.append("                  LEFT JOIN PE_BZZ_ORDER_INFO PBC ");
		sqlBuffer.append("                    ON PBC.ID = PBTSEB.FK_ORDER_ID ");
		sqlBuffer.append("                 GROUP BY PBTO.FK_COURSE_ID, PBTO.ID) OPE ");
		sqlBuffer.append("             ON OPE.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		sqlBuffer.append("           JOIN TRAINING_COURSE_STUDENT TCS ");
		sqlBuffer.append("             ON PBS.FK_SSO_USER_ID = TCS.STUDENT_ID ");
		sqlBuffer.append("            AND TCS.COURSE_ID = OPE.ID ");
		sqlBuffer.append("           JOIN PE_BZZ_TCH_COURSE PBTC ");
		sqlBuffer.append("             ON PBTC.ID = OPE.FK_COURSE_ID) ");
		sqlBuffer.append("  WHERE 1 = 1 ");
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
