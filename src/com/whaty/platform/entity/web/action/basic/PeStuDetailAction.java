package com.whaty.platform.entity.web.action.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.service.basic.PeDetailService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 专项查看学员
 * 
 * @author fan
 * 
 */
public class PeStuDetailAction extends MyBaseAction<StudentBatch> {

	private PeDetailService peDetailService;

	private String id;
	private String num;
	private PeEnterprise peEnterprise;
	private PeBzzStudent peBzzStudent;
	private String type;
	private String tempFlag;
	private String method;
	private String sumTimes;// 获取学时总数

	private String batchid;

	public String getSumTimes() {
		return sumTimes;
	}

	public void setSumTimes(String sumTimes) {
		this.sumTimes = sumTimes;
	}

	private String jfrs;

	public String getJfrs() {
		return jfrs;
	}

	public void setJfrs(String jfrs) {
		this.jfrs = jfrs;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public PeEnterprise getPeEnterprise() {
		return peEnterprise;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

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
		this.getGridConfig().addColumn(this.getText("系统编号"), "regNo", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("证件号码"), "CARDNO", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("所在机构"), "ORGNAME", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("资格类型"), "ZIGENAME", false, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("从事业务"), "WORK", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("职务"), "POSITION", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("报名专项时间起"), "bmsDate", true, false, false, "textField");
		this.getGridConfig().addColumn(this.getText("报名专项时间止"), "bmeDate", true, false, false, "textField");
		this.getGridConfig().addColumn(this.getText("报名专项时间"), "PAYMENT_DATE", false, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("报名专项学时"), "S_HR", false);
		this.getGridConfig().addColumn(this.getText("获得专项学时"), "Y_HR", false);
		this.getGridConfig().addColumn(this.getText("缴费金额(元)"), "P_AMOUNT", false);
		this.getGridConfig().addRenderFunction(this.getText("详细信息"), "<a target=\"_blank\" href=\"peDetail_stuviewDetail.action?id=${value}&type=1\">查看详细信息</a>", "id");
		this.getGridConfig().addRenderScript(
				this.getText("查看学习详情"),
				"{return '<a target=\"_blank\" href=\"/entity/teaching/searchAnyStudent_electivedCourse.action?batchId=" + this.getId()
						+ "&flag=stu&method=mystudent&stuId='+record.data['id']+'\">查看学习详情</a>';}", "id");
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");

	}

	public void setEntityClass() {
		this.entityClass = StudentBatch.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peStuDetail";
	}

	/**
	 * @author Lee 2014年1月6日 替代上边的public DetachedCriteria
	 *         initDetachedCriteria()方法 原方法体移到else中
	 */
	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT * FROM (");
		sqlBuffer.append(" SELECT A.ID,REG_NO  AS REGNO,");
		sqlBuffer.append(" TRUE_NAME AS TRUENAME,");
		sqlBuffer.append(" CARD_NO  AS CARDNO,");
		sqlBuffer.append(" E.NAME AS ORGNAME,");
		sqlBuffer.append(" F.NAME AS ZIGENAME,");
		sqlBuffer.append(" A.WORK,");
		sqlBuffer.append(" A.POSITION,");
		sqlBuffer.append(" '' AS BMSDATE,");// 报名专项时间起
		sqlBuffer.append(" '' AS BMEDATE,");// 报名专项时间止
		sqlBuffer.append(" DECODE(PAY_INFO.PAYMENT_DATE,NULL,'-',TO_CHAR(PAY_INFO.PAYMENT_DATE,'yyyy-MM-dd')) PAYMENT_DATE,");
		sqlBuffer.append(" DECODE(PAYMENT_DATE,NULL,0,S_HOUR.S_HR) S_HR,");
		sqlBuffer.append(" DECODE(PAYMENT_DATE,NULL,0,NVL(Y_HOUR.Y_HR,0)) Y_HR,");
		sqlBuffer.append(" DECODE(PAYMENT_DATE,NULL,0,PP.P_AMOUNT) P_AMOUNT");
		sqlBuffer.append(" FROM PE_BZZ_STUDENT A ");
		sqlBuffer.append(" inner join SSO_USER su on A.FK_SSO_USER_ID = SU.ID  "); // lzh
																					// 解决应培训人员与查看人员数据不一致
		sqlBuffer.append(" INNER JOIN STU_BATCH B  ON A.ID = B.STU_ID ");
		sqlBuffer.append(" INNER JOIN PE_BZZ_BATCH C ON B.BATCH_ID = C.ID  AND c.id='" + this.getId() + "'");
		sqlBuffer.append(" INNER JOIN ENUM_CONST D ON B.FLAG_BATCHPAYSTATE = D.ID ");
		sqlBuffer.append(" LEFT JOIN PE_ENTERPRISE E ON A.FK_ENTERPRISE_ID = E.ID ");
		sqlBuffer.append(" LEFT JOIN ENUM_CONST F ON F.ID=A.ZIGE ");
		sqlBuffer.append(" LEFT JOIN ( ");// 因为添加了报名专项时间搜索条件
											// 所以必须要用inner才可以查询出效果
		sqlBuffer.append(" SELECT DISTINCT PBOI.ID,PBOI.PAYMENT_DATE,PBTSE.FK_STU_ID FROM PE_BZZ_ORDER_INFO  PBOI ");
		sqlBuffer.append(" JOIN PR_BZZ_TCH_STU_ELECTIVE  PBTSE ON PBOI.ID=PBTSE.FK_ORDER_ID ");
		sqlBuffer.append(" WHERE pboi.fk_batch_id='" + this.getId() + "' ");

		sqlBuffer.append(" union SELECT DISTINCT PBOI.ID,PBOI.PAYMENT_DATE,PBTSE.FK_STU_ID FROM PE_BZZ_ORDER_INFO  PBOI ");
		sqlBuffer.append(" JOIN ELECTIVE_HISTORY  PBTSE ON PBOI.ID=PBTSE.FK_ORDER_ID ");
		sqlBuffer.append(" WHERE pboi.fk_batch_id='" + this.getId() + "' ");
		// 添加报名专项时间查询条件
		try {
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					if (params.get("search__bmsDate") != null) {// 报名时间起
						String[] startDateST = (String[]) params.get("search__bmsDate");
						String tempDate = startDateST[0];
						if (startDateST.length == 1 && !StringUtils.defaultString(startDateST[0]).equals("")) {
							params.remove("search__bmsDate");
							context.setParameters(params);
							sqlBuffer.append(" AND PBOI.PAYMENT_DATE >= TO_DATE('" + tempDate + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
						}
					}
					if (params.get("search__bmeDate") != null) {// 报名时间止
						String[] startDateED = (String[]) params.get("search__bmeDate");
						String tempDate = startDateED[0];
						if (startDateED.length == 1 && !StringUtils.defaultString(startDateED[0]).equals("")) {
							params.remove("search__bmeDate");
							context.setParameters(params);
							sqlBuffer.append(" AND PBOI.PAYMENT_DATE <= TO_DATE('" + tempDate + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		sqlBuffer.append(" ) PAY_INFO ON PAY_INFO.FK_STU_ID=B.STU_ID  ");
		sqlBuffer.append(" LEFT JOIN ( ");
		sqlBuffer.append(" SELECT PBTSE.FK_STU_ID,SUM(PBTC.TIME) AS S_HR  from pr_bzz_tch_opencourse PBTO ");
		sqlBuffer.append(" JOIN PE_BZZ_BATCH PBB ON PBB.ID=PBTO.FK_BATCH_ID ");
		sqlBuffer.append(" JOIN PE_BZZ_TCH_COURSE PBTC ON PBTC.ID=PBTO.FK_COURSE_ID ");
		sqlBuffer.append(" JOIN PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSE ON PBTSE.FK_TCH_OPENCOURSE_ID=PBTO.ID ");
		sqlBuffer.append(" WHERE PBB.ID='" + this.getId() + "' GROUP BY PBTSE.FK_STU_ID  ");
		sqlBuffer.append(" )S_HOUR ON S_HOUR.FK_STU_ID=B.STU_ID ");
		sqlBuffer.append(" LEFT JOIN ( ");
		sqlBuffer.append(" SELECT PBTSE.FK_STU_ID, SUM(PBTC.TIME) Y_HR ");
		sqlBuffer.append("   FROM PR_BZZ_TCH_OPENCOURSE PBTO ");
		sqlBuffer.append("   JOIN PE_BZZ_BATCH PBB ");
		sqlBuffer.append("     ON PBB.ID = PBTO.FK_BATCH_ID ");
		sqlBuffer.append("   JOIN PE_BZZ_TCH_COURSE PBTC ");
		sqlBuffer.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sqlBuffer.append("   JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sqlBuffer
				.append("     ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID JOIN PE_BZZ_ORDER_INFO PBOI ON PBTSE.FK_ORDER_ID = PBOI.ID JOIN ENUM_CONST EC ON PBOI.FLAG_ORDER_TYPE = EC.ID AND EC.CODE != '6' ");
		sqlBuffer.append("   JOIN ENUM_CONST EC2 ");
		sqlBuffer.append("     ON PBTC.FLAG_IS_EXAM = EC2.ID ");
		sqlBuffer.append("  WHERE PBB.ID = '" + this.getId() + "' ");
		sqlBuffer.append("    AND PBTC.FLAG_IS_EXAM = EC2.ID ");
		sqlBuffer.append("    AND ((EC2.NAME = '是' AND PBTSE.ISPASS = '1') OR ");
		sqlBuffer.append("        (EC2.NAME <> '是' AND PBTSE.ISPASS <> '1')) ");
		sqlBuffer.append("    AND PBTSE.COMPLETED_TIME IS NOT NULL ");
		sqlBuffer.append("    AND PBTSE.FK_TRAINING_ID IS NOT NULL ");
		sqlBuffer.append("  GROUP BY PBTSE.FK_STU_ID ");
		sqlBuffer.append(" )Y_HOUR ON Y_HOUR.FK_STU_ID=B.STU_ID ");
		/* 付费部分SQL 关联专项、订单、选课、课程相关表 条件为专项ID、订单为已到账 */
		sqlBuffer.append(" LEFT JOIN (SELECT PBTSE.FK_STU_ID, SUM(PBTC.PRICE) P_AMOUNT ");
		sqlBuffer.append("   FROM PR_BZZ_TCH_OPENCOURSE PBTO ");
		sqlBuffer.append("   JOIN PE_BZZ_BATCH PBB ");
		sqlBuffer.append("     ON PBB.ID = PBTO.FK_BATCH_ID ");
		sqlBuffer.append("   JOIN PE_BZZ_TCH_COURSE PBTC ");
		sqlBuffer.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sqlBuffer.append("   JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sqlBuffer.append("     ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
		sqlBuffer.append("   JOIN PE_BZZ_ORDER_INFO PBOI ");
		sqlBuffer.append("     ON PBTSE.FK_ORDER_ID = PBOI.ID ");
		sqlBuffer.append("   JOIN ENUM_CONST EC ");
		sqlBuffer.append("     ON PBOI.FLAG_PAYMENT_STATE = EC.ID ");
		sqlBuffer.append("    AND EC.CODE = '1' ");
		sqlBuffer.append("  WHERE PBB.ID = '" + this.getId() + "' ");
		sqlBuffer.append("  GROUP BY PBTSE.FK_STU_ID) PP ON PP.FK_STU_ID=B.STU_ID ");
		sqlBuffer.append(" WHERE c.id='" + this.getId() + "' ");
		if (us.getRoleId().equals("2") || us.getRoleId().equals("1")) {// 集体用户或二级用户
			sqlBuffer.append(" and A.fk_enterprise_id in (select id from pe_enterprise where id = (select fk_enterprise_id from pe_enterprise_manager where fk_sso_user_id = '"
					+ us.getId() + "') or fk_parent_id = (select fk_enterprise_id from pe_enterprise_manager where fk_sso_user_id = '" + us.getId() + "')) ");
		}
		sqlBuffer.append(" ) where 1=1 ");
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

	/**
	 * 查看机构信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String viewDetail() {
		DetachedCriteria viwedc = DetachedCriteria.forClass(PeEnterprise.class);
		viwedc.add(Restrictions.eq("id", id));
		PeEnterprise enterprise = null;
		try {
			enterprise = this.getGeneralService().getSubEnterprise(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setPeEnterprise(enterprise);
		return "viewDetail";
	}

	/**
	 * 查看机构管理员信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String viewPeDetail() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc.add(Restrictions.eq("loginId", us.getLoginId()));
		List peList = new ArrayList();
		try {
			peList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}

		PeEnterpriseManager pe = (PeEnterpriseManager) peList.get(0);

		PeEnterprise enterprise = null;
		try {
			enterprise = this.getGeneralService().getSubEnterprise(pe.getPeEnterprise().getId());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setPeEnterprise(enterprise);

		String sql = "";
		if (us.getUserLoginType().equals("2") || us.getRoleId().equals("402880a92137be1c012137db62100006")) // 一级企业
		{
			sql = "select s.id as id from pe_bzz_student s ,pe_enterprise p,pe_enterprise p1,sso_user su where su.id=s.fk_sso_user_id and p.fk_parent_id is null and p1.fk_parent_id=p.id and s.fk_enterprise_id=p1.id and p.id='"
					+ enterprise.getId()
					+ "' and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' union "
					+ "select s.id as id from pe_bzz_student s ,pe_enterprise p,sso_user su "
					+ " where su.id=s.fk_sso_user_id and s.fk_enterprise_id=p.id and p.id='"
					+ enterprise.getId() + "'" + " and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006'";
		} else // 二级企业
		{
			sql = "select a.id as id from pe_bzz_student a,sso_user su where a.fk_enterprise_id='" + enterprise.getId()
					+ "' and su.id=a.fk_sso_user_id and su.flag_isvalid='2' and a.flag_rank_state='402880f827f5b99b0127f5bdadc70006'";
		}
		List stuList = null;
		try {
			stuList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setNum(stuList.size() + "");

		return "viewPeDetail";
	}

	/**
	 * 查看学生信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String stuviewDetail() {

		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us.getUserLoginType().equals("3")) {
			tempFlag = "1";
		} else {
			tempFlag = "0";
		}
		this.setType(type);
		DetachedCriteria criteria = DetachedCriteria.forClass(PeBzzStudent.class);
		// criteria.createCriteria("peBzzBatch", "peBzzBatch");
		// criteria.createCriteria("peEnterprise", "peEnterprise");
		criteria.add(Restrictions.eq("id", id));
		try {
			this.setPeBzzStudent(this.getGeneralService().getStudentInfo(criteria));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "stuinfo";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	public PeDetailService getPeDetailService() {
		return peDetailService;
	}

	public void setPeDetailService(PeDetailService peDetailService) {
		this.peDetailService = peDetailService;
	}

	private PeBzzstudentbacthService peBzzstudentbacthService;
	private int count;

	/**
	 * 更新学生信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String fixStudent() {
		String cardNo = this.getIds();
		if (cardNo == null || "".equals(cardNo)) {
			cardNo = "1";
		}
		String m = this.peBzzstudentbacthService.updatestu(cardNo, "2", this.getCount());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		out.print(m);
		this.setMsg(m);
		return "msg";
	}

	public PeBzzstudentbacthService getPeBzzstudentbacthService() {
		return peBzzstudentbacthService;
	}

	public void setPeBzzstudentbacthService(PeBzzstudentbacthService peBzzstudentbacthService) {
		this.peBzzstudentbacthService = peBzzstudentbacthService;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

}
