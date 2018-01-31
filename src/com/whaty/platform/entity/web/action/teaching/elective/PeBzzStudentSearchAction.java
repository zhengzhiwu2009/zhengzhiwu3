package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 按人员查询 Action 2014年7月18日
 * 
 * @author dgh
 * 
 */
public class PeBzzStudentSearchAction extends MyBaseAction<PeBzzTchCourse> {
	private String studentParams;

	/**
	 * 初始化列表
	 * 
	 * @author dgh
	 * @return
	 */
	@Override
	public void initGrid() {
		studentParams = ServletActionContext.getRequest().getSession().getAttribute("studentParams") + "";
		if (null != studentParams && !"".equals(studentParams) && !"null".equals(studentParams)) {
			this.getGridConfig().setTitle("单一人员查询");
			this.getGridConfig().setCapability(false, false, false, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("系统编号"), "REG_NO");
			this.getGridConfig().addColumn(this.getText("姓名"), "TRUE_NAME");
			ColumnConfig ccZG = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name");
			ccZG.setAdd(false);
			ccZG.setSearch(true);
			ccZG.setList(true);
			ccZG.setComboSQL("SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagQualificationsType' and t.code not in('9','90','91')");
			this.getGridConfig().addColumn(ccZG);
			this.getGridConfig().addColumn(this.getText("证件号码"), "CARD_NO", true, true, true, null);
			this.getGridConfig().addColumn(this.getText("所在机构"), "NAME", true, true, true, null);// 李文强
			// 2014-09-09
			// 所在机构增加为检索条件
			this.getGridConfig().addColumn(this.getText("工作部门"), "DEPARTMENT", true, true, true, null);

			this.getGridConfig().addColumn(this.getText("获得学时开始时间"), "operateTimeSTDatetime", true, false, false, "TextField", false, 200, "");
			this.getGridConfig().addColumn(this.getText("获得学时结束时间"), "operateTimeEDDatetime", true, false, false, "TextField", false, 200, "");

			this.getGridConfig().addColumn(this.getText("报名课程数"), "PBTCCOUNT", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("报名学时数"), "TIMECOUNT", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("已完成课程数"), "GPBTCOUT", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("已获得学时数"), "GTIME", false, true, true, null);
			this.getGridConfig().addRenderScript(this.getText("学习详情"), "{return '<a target=\"_blank\" href=\"/entity/teaching/searchAnyStudent_electivedCourse.action?flag=stu&stuId='+record.data['id']+'\">学习详情</a>';}", "id");
		} else {
			this.getGridConfig().setTitle("全部人员查询");
			this.getGridConfig().setCapability(false, false, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("系统编号"), "REG_NO");
			this.getGridConfig().addColumn(this.getText("姓名"), "TRUE_NAME");
			ColumnConfig ccZG = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name");
			ccZG.setAdd(false);
			ccZG.setSearch(true);
			ccZG.setList(true);
			ccZG.setComboSQL("SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagQualificationsType' and t.code not in('9','90','91')");
			this.getGridConfig().addColumn(ccZG);
			this.getGridConfig().addColumn(this.getText("证件号码"), "CARD_NO", true, true, true, null);
			this.getGridConfig().addColumn(this.getText("所在机构"), "NAME", true, true, true, null);// 李文强
			// 2014-09-09
			// 所在机构增加为检索条件
			this.getGridConfig().addColumn(this.getText("工作部门"), "DEPARTMENT", true, true, true, null);

			this.getGridConfig().addColumn(this.getText("获得学时开始时间"), "operateTimeSTDatetime", true, false, false, "TextField", false, 200, "");
			this.getGridConfig().addColumn(this.getText("获得学时结束时间"), "operateTimeEDDatetime", true, false, false, "TextField", false, 200, "");

			this.getGridConfig().addColumn(this.getText("报名课程数"), "PBTCCOUNT", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("报名学时数"), "TIMECOUNT", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("已完成课程数"), "GPBTCOUT", false, true, true, null);
			this.getGridConfig().addColumn(this.getText("已获得学时数"), "GTIME", false, true, true, null);
			this.getGridConfig().addRenderScript(this.getText("学习详情"), "{return '<a target=\"_blank\" href=\"/entity/teaching/searchAnyStudent_electivedCourse.action?flag=stu&stuId='+record.data['id']+'\">学习详情</a>';}", "id");
		}
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzTchCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peBzzStudentSearch";
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
		String[] studentParamsArr = null;
		studentParams = ServletActionContext.getRequest().getSession().getAttribute("studentParams") + "";
		ServletActionContext.getRequest().getSession().removeAttribute("studentParams");
		if (null != studentParams && !"".equals(studentParams) && !"null".equals(studentParams))
			studentParamsArr = studentParams.split("-");
		String studentRegNo = "";
		if (null != studentParamsArr)
			studentRegNo = studentParamsArr[0].replace("null", "");
		String studentName = "";
		if (null != studentParamsArr)
			studentName = studentParamsArr[1].replace("null", "");
		String studentCardNo = "";
		if (null != studentParamsArr)
			studentCardNo = studentParamsArr[2].replace("null", "");
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		/* 拼接查询条件 */
		StringBuffer lsqlBuffer = new StringBuffer();
		StringBuffer olsqlBuffer = new StringBuffer();
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
			/* 操作时间开始时间 to_date('2014-07-25 08:00:00','yyyy-MM-dd hh24:mi:ss') */
			if (name.equals("operateTimeSTDatetime")) {
				lsqlBuffer.append(" and COMPLETED_TIME >= to_date('" + value + "','yyyy-MM-dd hh24:mi:ss')");
			}
			/* 操作时间结束时间 */
			if (name.equals("operateTimeEDDatetime")) {
				lsqlBuffer.append(" and COMPLETED_TIME <= to_date('" + value + "','yyyy-MM-dd hh24:mi:ss')");
			}
			/* 系统编号 */
			if (name.equals("REG_NO")) {
				olsqlBuffer.append(" and REG_NO LIKE '%" + value + "%'");
			}
			/* 姓名 */
			if (name.equals("TRUE_NAME")) {
				olsqlBuffer.append(" and TRUE_NAME LIKE '%" + value + "%'");
			}
			/* 资格类型 */
			if (name.equals("enumConstByFlagQualificationsType.name")) {
				olsqlBuffer.append(" and ZI_NAME LIKE '%" + value + "%'");
			}
			/* 证件号码 */
			if (name.equals("CARD_NO")) {
				olsqlBuffer.append(" and CARD_NO LIKE '%" + value + "%'");
			}
			/* 所在机构 */
			if (name.equals("NAME")) {
				olsqlBuffer.append(" and NAME LIKE '%" + value + "%'");
			}
			/* 工作部门 */
			if (name.equals("DEPARTMENT")) {
				olsqlBuffer.append(" and DEPARTMENT LIKE '%" + value + "%'");
			}

		} while (true);
		Page page = null;
		try {
			StringBuffer sqlBuffer = new StringBuffer();
			// 获取登陆集体账号的机构ID
			String enterpriseIdString = us.getPriEnterprises().get(0).getId();
			// 查询本机构及本机构下属机构ID集合
			String sql_enterprises = "SELECT ID FROM PE_ENTERPRISE WHERE ID = '" + enterpriseIdString + "' OR FK_PARENT_ID = '" + enterpriseIdString + "'";
			// 查询本机构及下属机构ID、NAME
			String sql_enterprisesIdsNames = "SELECT ID, NAME FROM PE_ENTERPRISE WHERE ID = '" + enterpriseIdString + "' OR FK_PARENT_ID = '" + enterpriseIdString + "'";
			sqlBuffer.append(" SELECT * FROM (");
			sqlBuffer.append("SELECT PBS.ID, ");
			sqlBuffer.append("       PBS.REG_NO, ");
			sqlBuffer.append("       PBS.TRUE_NAME, ");
			sqlBuffer.append("       ZI_EC.NAME ZI_NAME, ");
			sqlBuffer.append("       PBS.CARD_NO, ");
			sqlBuffer.append("       PE.NAME, ");
			sqlBuffer.append("       PBS.DEPARTMENT, ");
			sqlBuffer.append("       '' OPERATETIMESTDATETIME, ");
			sqlBuffer.append("       '' OPERATETIMEEDDATETIME, ");
			sqlBuffer.append("       NVL(GET_INFO.PBTCCOUNT, 0) PBTCCOUNT, ");
			sqlBuffer.append("       NVL(GET_INFO.TIMECOUNT, 0) TIMECOUNT, ");
			sqlBuffer.append("       NVL(GET_INFO.GPBTCOUT, 0) GPBTCOUT, ");
			sqlBuffer.append("       NVL(GET_INFO.GTIME, 0) GTIME ");
			sqlBuffer.append("  FROM PE_BZZ_STUDENT PBS ");
			sqlBuffer.append("  JOIN SSO_USER SU ");
			sqlBuffer.append("    ON SU.ID = PBS.FK_SSO_USER_ID ");
			sqlBuffer.append("    AND SU.FLAG_ISVALID = '2' ");
			if (null != studentRegNo && !"".equals(studentRegNo))
				sqlBuffer.append("    AND UPPER(SU.LOGIN_ID) LIKE '%" + studentRegNo.trim().toUpperCase() + "%' ");
			if (null != studentName && !"".equals(studentName))
				sqlBuffer.append("    AND UPPER(PBS.TRUE_NAME) LIKE '%" + studentName.trim().toUpperCase() + "%' ");
			if (null != studentCardNo && !"".equals(studentCardNo))
				sqlBuffer.append("    AND UPPER(PBS.CARD_NO) LIKE '%" + studentCardNo.trim().toUpperCase() + "%' ");
			sqlBuffer.append("  LEFT JOIN ENUM_CONST ZI_EC ");
			sqlBuffer.append("    ON PBS.ZIGE = ZI_EC.ID ");
			sqlBuffer.append("           JOIN (" + sql_enterprisesIdsNames + ") PE ");
			sqlBuffer.append("    ON PBS.FK_ENTERPRISE_ID = PE.ID ");
			sqlBuffer.append("  LEFT JOIN (SELECT PBTSE.FK_STU_ID, ");
			sqlBuffer.append("                    COUNT(PBTC.ID) AS PBTCCOUNT, ");
			sqlBuffer.append("                    SUM(PBTC.TIME) AS TIMECOUNT, ");
			sqlBuffer.append("                    COUNT(DECODE(TCS.COMPLETE_DATE, NULL, NULL, 1)) AS GPBTCOUT, ");
			sqlBuffer.append("                    SUM(DECODE(PBTSE.ISPASS, '1', PBTC.TIME, 0)) AS GTIME ");
			sqlBuffer.append("               FROM PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuffer.append("               JOIN PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("                 ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sqlBuffer.append("               JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sqlBuffer.append("                 ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
			sqlBuffer.append("               JOIN TRAINING_COURSE_STUDENT TCS ");
			sqlBuffer.append("                 ON PBTSE.FK_TRAINING_ID = TCS.ID ");
			sqlBuffer.append("               JOIN PE_BZZ_STUDENT PBS2 ");
			sqlBuffer.append("                 ON PBTSE.FK_STU_ID = PBS2.ID ");
			sqlBuffer.append("                 AND PBS2.FK_ENTERPRISE_ID IN (" + sql_enterprises + ")");
			if (!"".equals(lsqlBuffer.toString())) {// 拼接时间查询条件
				sqlBuffer.append(" " + lsqlBuffer);
			}
			sqlBuffer.append("                      GROUP BY PBTSE.FK_STU_ID) GET_INFO ");
			sqlBuffer.append("             ON GET_INFO.FK_STU_ID = PBS.ID ");
			sqlBuffer.append("          ) where 1=1 ");// 账号为有效状态//增加查询条件 李文强
			// 2014-09-09
			if (!"".equals(olsqlBuffer.toString())) {// 拼接其他查询条件
				sqlBuffer.append(" " + olsqlBuffer);
			}
			// 注释掉自动拼接查询条件方法，因为上边以手动处理
			// this.setSqlCondition(sqlBuffer);
			// 添加排序2012-10-09 zgl
			String temp = this.getSort();
			// 截掉前缀 Combobox_PeXxxxx.
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getGridConfig().getColumByDateIndex(temp) != null) {
				if (this.getSort() != null && temp != null) {
					if (temp.equals("enumConstByFlagQualificationsType.name")) {
						temp = "ZI_NAME";
					}
					if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc") && !"id".equals(temp)) {
						sqlBuffer.append(" order by " + temp + " desc ");
					}
					if (this.getDir() != null && this.getDir().equalsIgnoreCase("asc") && !"id".equals(temp)) {
						sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
			}
			// end
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	public String preView() {
		return "noPreView";
	}

	public String singleStudentSearch() {
		String studentRegNo = ServletActionContext.getRequest().getParameter("studentRegNo");
		if (null == studentRegNo || "".equals(studentRegNo))
			studentRegNo = "null";
		String studentCardNo = ServletActionContext.getRequest().getParameter("studentCardNo");
		if (null == studentCardNo || "".equals(studentCardNo))
			studentCardNo = "null";
		String studentName = ServletActionContext.getRequest().getParameter("studentName");
		if (null == studentName || "".equals(studentName))
			studentName = "null";
		String studentParams = studentRegNo + "-" + studentName + "-" + studentCardNo;
		ServletActionContext.getRequest().getSession().setAttribute("studentParams", studentParams);
		return "singleStudentSearch";
	}

	public String getStudentParams() {
		return studentParams;
	}

	public void setStudentParams(String studentParams) {
		this.studentParams = studentParams;
	}
}
