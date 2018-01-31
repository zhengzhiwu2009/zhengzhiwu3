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
 * 公司内训课程查看学员
 * @author fan
 *
 */
public class PeInternalStuLearnDetailAction extends MyBaseAction<StudentBatch> {

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
		
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);;
		this.getGridConfig().addColumn(this.getText("课程编号"), "code",true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("课程名称"), "name",true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("学时"), "time",true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("开始学习时间"), "start_time",true, false, true, "textField"); 
		this.getGridConfig().addColumn(this.getText("课程完成时间"),"completed_time",true, false, true, "textField");
		
		ColumnConfig columnLearnType = new ColumnConfig(this.getText("学习状态"), "Combobox_completed", true, true, true, "TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='LearnStatus'";
		columnLearnType.setComboSQL(sql);
		this.getGridConfig().addColumn(columnLearnType);
		
		ColumnConfig ccExamStatus = new ColumnConfig(this.getText("考试结果"), "Combobox_nopassexam", true, true, true, "TextField", false, 100, "");
		String sqlExamStatus = "select a.id ,a.name from enum_const a where a.namespace='ExamStatus'";
		ccExamStatus.setComboSQL(sqlExamStatus);
		this.getGridConfig().addColumn(ccExamStatus);
		
		this.getGridConfig().addRenderFunction(
				this.getText("查看学习详情"),
				"<a target=\"_blank\" href=\"/entity/teaching/searchAnyStudent_courseInfo.action?allid=${value}\">查看学习详情</a>",
				"id");
		
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
		sqlBuffer.append("SELECT * FROM (");
		sqlBuffer.append(" SELECT DISTINCT PBS.ID||'-'||pbtc.id||'-'||ope.id as id,pbtc.code,pbtc.name,pbtc.time,pbtse.start_time,pbtse.completed_time, ");
		sqlBuffer.append(" CASE WHEN tcs.LEARN_STATUS = 'COMPLETED' THEN '已完成学习' ");
		sqlBuffer.append(" WHEN tcs.LEARN_STATUS = 'INCOMPLETE' THEN '正在学习' ");
		sqlBuffer.append(" ELSE '未开始学习' END AS Combobox_completed, ");
		sqlBuffer.append(" CASE WHEN pbtc.flag_is_exam ='40288acf3ae01103013ae012940b0001' AND pbtse.ispass='1' THEN '已通过考试' ");
		sqlBuffer.append(" WHEN pbtc.flag_is_exam ='40288acf3ae01103013ae0130d030002' THEN '不考试' ");
		sqlBuffer.append(" ELSE '未通过考试' END AS Combobox_nopassexam,pbtc.id pbtc_id,pe.code as pcode ");
		sqlBuffer.append(" FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sqlBuffer.append(" JOIN PE_BZZ_STUDENT PBS ON PBS.ID=PBTSE.FK_STU_ID ");
		sqlBuffer.append(" left JOIN PE_BZZ_ORDER_INFO pboi ON pboi.id=pbtse.fk_order_id "); //加了一左连接标识  lzh
		sqlBuffer.append(" LEFT JOIN PE_ENTERPRISE PE ON PBS.ENTERPRISE_ID = PE.ID  ");
		sqlBuffer.append(" JOIN ( ");
		sqlBuffer.append("      SELECT pbto.fk_course_id,pbto.id FROM PR_BZZ_TCH_OPENCOURSE pbto   ");
		sqlBuffer.append("      JOIN PR_BZZ_TCH_STU_ELECTIVE pbtseb ON pbtseb.fk_tch_opencourse_id=pbto.id   ");// 将PR_BZZ_TCH_STU_ELECTIVE_BACK 改为PR_BZZ_TCH_STU_ELECTIVE  lzh
		sqlBuffer.append("    left  JOIN PE_BZZ_ORDER_INFO PBC ON PBC.ID=pbtseb.FK_ORDER_ID  AND PBC.flag_payment_state='40288a7b394207de01394221a6ff000e'  ");//加了一左连接标识lzh
		sqlBuffer.append("      GROUP BY pbto.fk_course_id,pbto.id ");
		sqlBuffer.append(" ) ope ON ope.id=PBTSE.FK_TCH_OPENCOURSE_ID ");
		//学习状态判断
		sqlBuffer.append(" JOIN training_course_student tcs on PBS.ID=tcs.STUDENT_ID and tcs.course_id = ope.id");
		sqlBuffer.append(" JOIN PE_BZZ_TCH_COURSE pbtc ON pbtc.id=ope.fk_course_id WHERE PBS.ID='"+this.getId()+"' ");
		sqlBuffer.append(") WHERE 1=1 ");
		
		try {
			this.setSqlCondition(sqlBuffer);
			page = this.getGeneralService().getByPageSQL(
					sqlBuffer.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		} 
		return page;
	}
}
