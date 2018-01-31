package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Set;
import org.apache.struts2.ServletActionContext;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeBzzCourseRegSearchAction extends MyBaseAction<PeBzzTchCourse> {
	/**
	 * 初始化列表
	 * 
	 * @author Lee
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();

		this.getGridConfig().setTitle("监管机构内训查询");
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("系统编号"), "login_id");

		this.getGridConfig().addColumn(this.getText("姓名"), "true_name");

		this.getGridConfig().addColumn(this.getText("获得学时总数"), "course_times", false, false, true, "TextField");

		this.getGridConfig().addColumn(this.getText("内训时长总数(分钟)"), "course_lens", false, false, true, "TextField");

		this.getGridConfig().addColumn(this.getText("已完成课程数"), "com_count", false, false, true, "TextField");

		this.getGridConfig().addColumn(this.getText("正在学习课程数"), "in_count", false, false, true, "TextField");

		this.getGridConfig().addRenderScript(this.getText("学习详情"), "{return '<a href=\"/entity/teaching/peRegCourseDetail.action?id='+${value}+'&type=0\">学习详情</a>';}", "id");

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzTchCourse.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/peBzzCourseRegSearch";
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
			String enterpriseIdSql = "SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "' UNION SELECT ID FROM PE_ENTERPRISE WHERE FK_PARENT_ID = (SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '"
					+ us.getSsoUser().getId() + "')";
			sqlBuffer.append("SELECT * FROM ( ");
			sqlBuffer.append("SELECT PBS.ID, SU.LOGIN_ID, PBS.TRUE_NAME, A.COURSE_TIMES, B.COURSE_LENS, C.COM_COUNT, D.IN_COUNT ");
			sqlBuffer.append("  FROM PE_BZZ_STUDENT PBS ");
			sqlBuffer.append("  JOIN SSO_USER SU ON SU.ID = PBS.FK_SSO_USER_ID ");
			sqlBuffer.append("  LEFT JOIN (SELECT SUM(PBTC.TIME) COURSE_TIMES, TCS.STUDENT_ID ");
			sqlBuffer.append("               FROM PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("               JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuffer.append("               JOIN ENUM_CONST EC ON PBTC.FLAG_COURSEAREA = EC.ID ");
			sqlBuffer.append("               JOIN ENUM_CONST EC1 ON PBTC.FLAG_IS_EXAM = EC1.ID ");
			sqlBuffer.append("               JOIN TRAINING_COURSE_STUDENT TCS ON TCS.COURSE_ID = PBTO.ID ");
			sqlBuffer.append("               JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBTSE.FK_TRAINING_ID = TCS.ID ");
			sqlBuffer.append("              WHERE TCS.LEARN_STATUS = 'COMPLETED' ");
			sqlBuffer.append("                AND EC.CODE IN ('1', '2', '5') ");
			sqlBuffer.append("                AND ((EC1.CODE = '1' AND PBTSE.ISPASS = '1') OR  ");
			sqlBuffer.append("                    EC1.CODE = '0') ");
			sqlBuffer.append("              GROUP BY TCS.STUDENT_ID) A ON SU.ID = A.STUDENT_ID ");
			sqlBuffer.append("  LEFT JOIN (SELECT SUM(PBTC.Course_Len) COURSE_LENS, TCS.STUDENT_ID ");
			sqlBuffer.append("               FROM PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("               JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuffer.append("               JOIN ENUM_CONST EC ON PBTC.FLAG_COURSEAREA = EC.ID ");
			sqlBuffer.append("               JOIN ENUM_CONST EC1 ON PBTC.FLAG_IS_EXAM = EC1.ID ");
			sqlBuffer.append("               JOIN TRAINING_COURSE_STUDENT TCS ON TCS.COURSE_ID = PBTO.ID ");
			sqlBuffer.append("               JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBTSE.FK_TRAINING_ID = TCS.ID ");
			sqlBuffer.append("              WHERE TCS.LEARN_STATUS = 'COMPLETED' ");
			sqlBuffer.append("                AND EC.CODE IN ('1', '2', '5') ");
			sqlBuffer.append("                AND ((EC1.CODE = '1' AND PBTSE.ISPASS = '1') OR EC1.CODE = '0') ");
			sqlBuffer.append("              GROUP BY TCS.STUDENT_ID) B ON B.STUDENT_ID = SU.ID ");
			sqlBuffer.append("  LEFT JOIN (SELECT COUNT(*) COM_COUNT, TCS.STUDENT_ID ");
			sqlBuffer.append("               FROM PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("               JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuffer.append("               JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
			sqlBuffer.append("               JOIN TRAINING_COURSE_STUDENT TCS ON PBTSE.FK_TRAINING_ID = TCS.ID ");
			sqlBuffer.append("               JOIN ENUM_CONST EC1 ON PBTC.FLAG_ISVALID = EC1.ID ");
			sqlBuffer.append("               JOIN ENUM_CONST EC2 ON PBTC.FLAG_OFFLINE = EC2.ID ");
			sqlBuffer.append("               JOIN ENUM_CONST EC3 ON PBTC.FLAG_COURSEAREA = EC3.ID ");
			sqlBuffer.append("              WHERE TCS.LEARN_STATUS = 'COMPLETED' ");
			sqlBuffer.append("                AND EC1.CODE = '1' ");
			sqlBuffer.append("                AND EC2.CODE = '0' ");
			sqlBuffer.append("                AND EC3.CODE IN ('1', '2', '5') ");
			sqlBuffer.append("              GROUP BY TCS.STUDENT_ID) C ON SU.ID = C.STUDENT_ID ");
			sqlBuffer.append("  LEFT JOIN (SELECT COUNT(*) IN_COUNT, TCS.STUDENT_ID ");
			sqlBuffer.append("               FROM PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("               JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuffer.append("               JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
			sqlBuffer.append("               JOIN TRAINING_COURSE_STUDENT TCS ON PBTSE.FK_TRAINING_ID = TCS.ID ");
			sqlBuffer.append("               JOIN ENUM_CONST EC1 ON PBTC.FLAG_ISVALID = EC1.ID ");
			sqlBuffer.append("               JOIN ENUM_CONST EC2 ON PBTC.FLAG_OFFLINE = EC2.ID ");
			sqlBuffer.append("               JOIN ENUM_CONST EC3 ON PBTC.FLAG_COURSEAREA = EC3.ID ");
			sqlBuffer.append("              WHERE TCS.LEARN_STATUS = 'INCOMPLETE' ");
			sqlBuffer.append("                AND EC1.CODE = '1' ");
			sqlBuffer.append("                AND EC2.CODE = '0' ");
			sqlBuffer.append("                AND EC3.CODE IN ('1', '2', '5') ");
			sqlBuffer.append("              GROUP BY TCS.STUDENT_ID) D ON SU.ID = D.STUDENT_ID ");
			// sqlBuffer.append(" JOIN (SELECT SU1.LOGIN_ID, PE.ID ");
			// sqlBuffer.append(" FROM PE_ENTERPRISE PE ");
			// sqlBuffer.append(" JOIN PE_ENTERPRISE_MANAGER PEM ON
			// PEM.FK_ENTERPRISE_ID = PE.ID ");
			// sqlBuffer.append(" JOIN SSO_USER SU1 ON SU1.ID =
			// PEM.FK_SSO_USER_ID) ENTERPRISE ON PBS.FK_ENTERPRISE_ID =
			// ENTERPRISE.ID ");
			sqlBuffer.append(" WHERE PBS.FK_ENTERPRISE_ID IN ( ");
			sqlBuffer.append("		SELECT PES.ID FROM PE_ENTERPRISE PES ");
			sqlBuffer.append("		 WHERE PES.ID IN (SELECT PE.ID ");
			sqlBuffer.append("		                    FROM PE_ENTERPRISE PE ");
			sqlBuffer.append("		                    JOIN ENUM_CONST EC ON PE.ENTYPE = EC.ID ");
			sqlBuffer.append("		                   WHERE EC.CODE = 'V') ");
			sqlBuffer.append("		    OR PES.FK_PARENT_ID IN (SELECT PE.ID ");
			sqlBuffer.append("		                              FROM PE_ENTERPRISE PE ");
			sqlBuffer.append("		                              JOIN ENUM_CONST EC ON PE.ENTYPE = EC.ID ");
			sqlBuffer.append("		                             WHERE EC.CODE = 'V')) ");
			// sqlBuffer.append(" WHERE ENTERPRISE.LOGIN_ID = 'A124' ");
			sqlBuffer.append(") WHERE 1=1");
			StringBuffer sql = new StringBuffer(sqlBuffer);
			this.setSqlCondition(sql);
			page = this.getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
}
