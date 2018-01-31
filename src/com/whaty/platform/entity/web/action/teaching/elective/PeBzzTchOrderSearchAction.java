package com.whaty.platform.entity.web.action.teaching.elective;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PeBzzFinancialService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 按订单查询
 * 
 * @author hdg 2014-07-17
 */
public class PeBzzTchOrderSearchAction extends MyBaseAction<PeBzzOrderInfo> {

	private static final long serialVersionUID = 3039541579877071957L;
	private PeBzzFinancialService peBzzFinancialService;
	private List<PrBzzTchStuElective> electiveList = new ArrayList<PrBzzTchStuElective>();
	private List<PrBzzTchStuElectiveBack> electiveBackList = new ArrayList<PrBzzTchStuElectiveBack>();
	private PeBzzInvoiceInfo peBzzInvoiceInfo;
	private PeBzzStudent peBzzStudent;
	private SsoUser ssoUser;
	private PeEnterpriseManager peEnterpriseManager;
	private EnumConstService enumConstService;
	private double price;
	private String seq;
	private String name;
	private String loginid;
	private String amount;

	/**
	 * 初始化列表
	 * 
	 * @author hdg
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if ((null != seq && !"".equals(seq)) || (name != null && !"".equals(name)) || (null != loginid && !"".equals(loginid)) || (null != amount && !"".equals(amount))) {
			//this.getGridConfig().setTitle(this.getText("单一订单查询"));
			this.getGridConfig().setTitle(this.getText("按订单查询"));
			this.getGridConfig().setCapability(false, false, false, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("订单号"), "seq", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("订单金额"), "amount", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("支付账号"), "login_id", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("订单生成时间起始"), "createStartDatetime", false, false, false, "");
			this.getGridConfig().addColumn(this.getText("订单生成时间"), "createDatetime", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("订单生成时间结束"), "createEndDatetime", false, false, false, "");
			this.getGridConfig().addColumn(this.getText("支付时间起始"), "paymentStartDatetime", false, false, false, "");
			this.getGridConfig().addColumn(this.getText("支付时间"), "paymentdatetime", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("支付时间结束"), "paymentEndDatetime", false, false, false, "");
			this.getGridConfig().addColumn(this.getText("总报名人数"), "bmrs", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("报名总学时数"), "bmxss", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("报名课程数"), "bmkcs", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("已获得学时总数"), "hdxss", false, false, true, "");
			this.getGridConfig().addRenderScript(this.getText("查看学员"), "{return '<a href=\"/entity/basic/peBzzTchOrderStuDetail.action?id='+${value}+'\" target=\"_blank\">查看学员</a>';}", "id");
			this.getGridConfig().addRenderScript(this.getText("订单详情"),
					"{if( record.data['combobox_orderType'] != '购买学时订单' ) {" + "   return '<a href=\"/entity/basic/collectiveOrderQuery_viewDeatail.action?id='+record.data['id']+'\" target=\"_blank\">订单详情</a>'" + "} else {" + "   return '--'" + "}}", "id");
		} else {
			this.getGridConfig().setTitle(this.getText("按订单查询"));
			this.getGridConfig().setCapability(false, false, false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("订单号"), "seq", true, false, true, "");
			this.getGridConfig().addColumn(this.getText("订单金额"), "amount", true, false, true, "");
			this.getGridConfig().addColumn(this.getText("支付账号"), "login_id", true, false, true, "");
			this.getGridConfig().addColumn(this.getText("订单生成时间起始"), "createStartDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("订单生成时间"), "createDatetime", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("订单生成时间结束"), "createEndDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("支付时间起始"), "paymentStartDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("支付时间"), "paymentdatetime", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("支付时间结束"), "paymentEndDatetime", true, false, false, "");
			this.getGridConfig().addColumn(this.getText("总报名人数"), "bmrs", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("报名总学时数"), "bmxss", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("报名课程数"), "bmkcs", false, false, true, "");
			this.getGridConfig().addColumn(this.getText("已获得学时总数"), "hdxss", false, false, true, "");
			this.getGridConfig().addRenderScript(this.getText("查看学员"), "{return '<a href=\"/entity/basic/peBzzTchOrderStuDetail.action?id='+${value}+'\" target=\"_blank\">查看学员</a>';}", "id");
			this.getGridConfig().addRenderScript(this.getText("订单详情"),
					"{if( record.data['combobox_orderType'] != '购买学时订单' ) {" + "   return '<a href=\"/entity/basic/collectiveOrderQuery_viewDeatail.action?id='+record.data['id']+'\" target=\"_blank\">订单详情</a>'" + "} else {" + "   return '--'" + "}}", "id");
		}
	}

	/**
	 * 重写框架方法：按订单查询列表（带sql条件）
	 * 
	 * @author hdg
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		if ((null != seq && !"".equals(seq)) || (name != null && !"".equals(name)) || (null != loginid && !"".equals(loginid)) || (null != amount && !"".equals(amount))) {// 单一订单查询
			sqlBuffer.append("SELECT * ");
			sqlBuffer.append("FROM   (SELECT A.ID AS id,");
			sqlBuffer.append("   A.SEQ AS seq,");// 订单号
			sqlBuffer.append("   A.AMOUNT AS amount,");// 订单金额
			sqlBuffer.append("   B.LOGIN_ID AS login_id,");// 支付账号
			sqlBuffer.append("   '' CREATESTARTDATETIME,");// 订单生成时间 起始
			sqlBuffer.append("   A.CREATE_DATE CREATEDATETIME,");// 订单生成时间
			sqlBuffer.append("   '' CREATEENDDATETIME,");// 订单生成时间 结束
			sqlBuffer.append("   '' PAYMENTSTARTDATETIME,");// 支付时间 起始
			sqlBuffer.append("   A.PAYMENT_DATE PAYMENTDATETIME,");// 支付时间
			sqlBuffer.append("   '' PAYMENTENDDATETIME,"); // 支付时间结束
			// 报名人数去重 zgl
			sqlBuffer.append("   COUNT(DISTINCT C.FK_STU_ID) BMRS,");// 总报名人数
			sqlBuffer.append("   SUM(E.TIME) BMXSS,");// 报名总学时数
			sqlBuffer.append("     COUNT(E.ID) BMKCS,");// 报名课程数
			// 计算获得学时方式修改
			sqlBuffer.append("                        SUM(CASE ");
			sqlBuffer.append("                              WHEN (F.CODE = '1' AND C.ISPASS = '1') THEN ");
			sqlBuffer.append("                               TO_NUMBER(NVL(E.TIME, 0)) ");
			sqlBuffer.append("                              WHEN (F.CODE = '0' AND C.COMPLETED_TIME IS NOT NULL) THEN ");
			sqlBuffer.append("                               TO_NUMBER(NVL(E.TIME, 0)) ");
			sqlBuffer.append("                              ELSE ");
			sqlBuffer.append("                               0 ");
			sqlBuffer.append("                            END) HDXSS ");// 已获得学时总数
			sqlBuffer.append("     FROM PE_BZZ_ORDER_INFO A");
			sqlBuffer.append(" INNER JOIN SSO_USER B ON A.CREATE_USER = B.ID ");
			if (seq != null && !"".equals(seq)) {
				sqlBuffer.append(" AND A.SEQ = '" + seq + "' ");
			}
			sqlBuffer.append("    INNER JOIN (SELECT FK_STU_ID,FK_ORDER_ID,FK_TCH_OPENCOURSE_ID,COMPLETED_TIME,ISPASS FROM PR_BZZ_TCH_STU_ELECTIVE UNION SELECT FK_STU_ID,FK_ORDER_ID,FK_TCH_OPENCOURSE_ID,COMPLETED_TIME,ISPASS FROM ELECTIVE_HISTORY) C ON A.ID = C.FK_ORDER_ID");
			sqlBuffer.append("   INNER JOIN PR_BZZ_TCH_OPENCOURSE D ON C.FK_TCH_OPENCOURSE_ID = D.ID");
			sqlBuffer.append("     INNER JOIN PE_BZZ_TCH_COURSE E ON D.FK_COURSE_ID = E.ID");
			// 计算获得学时方式修改
			sqlBuffer.append("         INNER JOIN ENUM_CONST F ON E.FLAG_IS_EXAM = F.ID ");
			sqlBuffer.append(" WHERE 1 = 1 ");
			if (name != null && !"".equals(name)) {
				try {
					sqlBuffer.append(" AND A.NAME LIKE '%" + URLDecoder.decode(name, "utf-8") + "%' ");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if (loginid != null && !"".equals(loginid)) {
				sqlBuffer.append(" AND UPPER(B.LOGIN_ID) = '" + loginid.toUpperCase() + "' ");
			}
			if (amount != null && !"".equals(amount)) {
				sqlBuffer.append(" AND TO_NUMBER(A.AMOUNT) = " + amount );
			}
			if (!"3".equals(us.getUserLoginType())) {
				// 查询机构和机构下的订单
				// 一级集体管理员、二级集体管理员及所属学员对应的用户ID
				// 获取登陆集体账号的机构ID
				String enterpriseIdString = us.getPriEnterprises().get(0).getId();
				// 查询本机构及本机构下属机构ID集合
				String sql_enterprises = "SELECT ID FROM PE_ENTERPRISE WHERE ID = '" + enterpriseIdString + "' OR FK_PARENT_ID = '" + enterpriseIdString + "'";
				// 本机构及下属机构账号对应的用户ID
				String sql_enterprisesUserIds = "SELECT DISTINCT FK_SSO_USER_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_ENTERPRISE_ID IN (SELECT ID FROM PE_ENTERPRISE WHERE ID = '" + enterpriseIdString + "' OR FK_PARENT_ID = '" + enterpriseIdString + "')";
				sqlBuffer.append("   AND A.CREATE_USER IN " + "(SELECT FK_SSO_USER_ID " + " FROM PE_BZZ_STUDENT WHERE FK_ENTERPRISE_ID IN (" + sql_enterprises + ") UNION ALL (" + sql_enterprisesUserIds + ")) ");
			}

			try {
				ActionContext context = ActionContext.getContext();
				if (context.getParameters() != null) {
					Map params = this.getParamsMap();
					if (params != null) {

						// 处理时间的查询参数
						if (params.get("search__createStartDatetime") != null) {// 订单生成时间
							// 起始
							String[] startDate = (String[]) params.get("search__createStartDatetime");
							String tempDate = startDate[0];
							if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
								sqlBuffer.append("           and A.CREATE_DATE >= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
								params.remove("search__createStartDatetime");
							}
						}
						if (params.get("search__createEndDatetime") != null) {// 订单生成时间
							// 结束
							String[] startDate = (String[]) params.get("search__createEndDatetime");
							String tempDate = startDate[0];
							if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
								sqlBuffer.append("           and A.CREATE_DATE <= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
								params.remove("search__createEndDatetime");
							}
						}
						if (params.get("search__paymentStartDatetime") != null) {// 支付时间起始
							String[] startDate = (String[]) params.get("search__paymentStartDatetime");
							String tempDate = startDate[0];
							if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
								sqlBuffer.append("           and PAYMENT_DATE >= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
								params.remove("search__paymentStartDatetime");
							}
						}
						if (params.get("search__paymentEndDatetime") != null) {// 支付时间结束
							String[] startDate = (String[]) params.get("search__paymentEndDatetime");
							String tempDate = startDate[0];
							if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
								sqlBuffer.append("           and PAYMENT_DATE <= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
								params.remove("search__paymentEndDatetime");
							}
						}
						context.setParameters(params);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			sqlBuffer.append("   GROUP BY A.ID, A.SEQ, A.AMOUNT, B.LOGIN_ID, A.CREATE_DATE, A.PAYMENT_DATE");
			sqlBuffer.append("   )WHERE  1 = 1");
		} else {
			sqlBuffer.append("SELECT * ");
			sqlBuffer.append("FROM   (SELECT A.ID AS id,");
			sqlBuffer.append("   A.SEQ AS seq,");// 订单号
			sqlBuffer.append("   A.AMOUNT AS amount,");// 订单金额
			sqlBuffer.append("   B.LOGIN_ID AS login_id,");// 支付账号
			sqlBuffer.append("   '' CREATESTARTDATETIME,");// 订单生成时间 起始
			sqlBuffer.append("   A.CREATE_DATE CREATEDATETIME,");// 订单生成时间
			sqlBuffer.append("   '' CREATEENDDATETIME,");// 订单生成时间 结束
			sqlBuffer.append("   '' PAYMENTSTARTDATETIME,");// 支付时间 起始
			sqlBuffer.append("   A.PAYMENT_DATE PAYMENTDATETIME,");// 支付时间
			sqlBuffer.append("   '' PAYMENTENDDATETIME,"); // 支付时间结束
			// 报名人数去重 zgl
			sqlBuffer.append("   COUNT(DISTINCT C.FK_STU_ID) BMRS,");// 总报名人数
			sqlBuffer.append("   SUM(E.TIME) BMXSS,");// 报名总学时数
			sqlBuffer.append("     COUNT(E.ID) BMKCS,");// 报名课程数
			// 计算获得学时方式修改
			sqlBuffer.append("                        SUM(CASE ");
			sqlBuffer.append("                              WHEN (F.CODE = '1' AND C.ISPASS = '1') THEN ");
			sqlBuffer.append("                               TO_NUMBER(NVL(E.TIME, 0)) ");
			sqlBuffer.append("                              WHEN (F.CODE = '0' AND C.COMPLETED_TIME IS NOT NULL) THEN ");
			sqlBuffer.append("                               TO_NUMBER(NVL(E.TIME, 0)) ");
			sqlBuffer.append("                              ELSE ");
			sqlBuffer.append("                               0 ");
			sqlBuffer.append("                            END) HDXSS ");// 已获得学时总数
			sqlBuffer.append("     FROM PE_BZZ_ORDER_INFO A");
			sqlBuffer.append(" INNER JOIN SSO_USER B ON A.CREATE_USER = B.ID");
			sqlBuffer.append("    INNER JOIN (SELECT FK_STU_ID,FK_ORDER_ID,FK_TCH_OPENCOURSE_ID,COMPLETED_TIME,ISPASS FROM PR_BZZ_TCH_STU_ELECTIVE UNION SELECT FK_STU_ID,FK_ORDER_ID,FK_TCH_OPENCOURSE_ID,COMPLETED_TIME,ISPASS FROM ELECTIVE_HISTORY) C ON A.ID = C.FK_ORDER_ID");
			sqlBuffer.append("   INNER JOIN PR_BZZ_TCH_OPENCOURSE D ON C.FK_TCH_OPENCOURSE_ID = D.ID");
			sqlBuffer.append("     INNER JOIN PE_BZZ_TCH_COURSE E ON D.FK_COURSE_ID = E.ID");
			// 计算获得学时方式修改
			sqlBuffer.append("         INNER JOIN ENUM_CONST F ON E.FLAG_IS_EXAM = F.ID ");
			sqlBuffer.append(" WHERE 1 = 1 ");
			if (!"3".equals(us.getUserLoginType())) {
				// 查询机构和机构下的订单
				// 一级集体管理员、二级集体管理员及所属学员对应的用户ID
				// 获取登陆集体账号的机构ID
				String enterpriseIdString = us.getPriEnterprises().get(0).getId();
				// 查询本机构及本机构下属机构ID集合
				String sql_enterprises = "SELECT ID FROM PE_ENTERPRISE WHERE ID = '" + enterpriseIdString + "' OR FK_PARENT_ID = '" + enterpriseIdString + "'";
				// 本机构及下属机构账号对应的用户ID
				String sql_enterprisesUserIds = "SELECT DISTINCT FK_SSO_USER_ID FROM PE_ENTERPRISE_MANAGER WHERE FK_ENTERPRISE_ID IN (SELECT ID FROM PE_ENTERPRISE WHERE ID = '" + enterpriseIdString + "' OR FK_PARENT_ID = '" + enterpriseIdString + "')";
				sqlBuffer.append("   AND A.CREATE_USER IN " + "(SELECT FK_SSO_USER_ID " + " FROM PE_BZZ_STUDENT WHERE FK_ENTERPRISE_ID IN (" + sql_enterprises + ") UNION ALL (" + sql_enterprisesUserIds + ")) ");
			}
			try {
				ActionContext context = ActionContext.getContext();
				if (context.getParameters() != null) {
					Map params = this.getParamsMap();
					if (params != null) {

						// 处理时间的查询参数
						if (params.get("search__createStartDatetime") != null) {// 订单生成时间
							// 起始
							String[] startDate = (String[]) params.get("search__createStartDatetime");
							String tempDate = startDate[0];
							if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
								sqlBuffer.append("           and A.CREATE_DATE >= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
								params.remove("search__createStartDatetime");
							}
						}
						if (params.get("search__createEndDatetime") != null) {// 订单生成时间
							// 结束
							String[] startDate = (String[]) params.get("search__createEndDatetime");
							String tempDate = startDate[0];
							if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
								sqlBuffer.append("           and A.CREATE_DATE <= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
								params.remove("search__createEndDatetime");
							}
						}
						if (params.get("search__paymentStartDatetime") != null) {// 支付时间起始
							String[] startDate = (String[]) params.get("search__paymentStartDatetime");
							String tempDate = startDate[0];
							if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
								sqlBuffer.append("           and PAYMENT_DATE >= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
								params.remove("search__paymentStartDatetime");
							}
						}
						if (params.get("search__paymentEndDatetime") != null) {// 支付时间结束
							String[] startDate = (String[]) params.get("search__paymentEndDatetime");
							String tempDate = startDate[0];
							if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
								sqlBuffer.append("           and PAYMENT_DATE <= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
								params.remove("search__paymentEndDatetime");
							}
						}
						context.setParameters(params);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			sqlBuffer.append("   GROUP BY A.ID, A.SEQ, A.AMOUNT, B.LOGIN_ID, A.CREATE_DATE, A.PAYMENT_DATE");
			sqlBuffer.append("   )WHERE  1 = 1");
		}
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

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzOrderInfo.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peBzzTchOrderSearch";
	}

	public PeBzzOrderInfo getBean() {
		return super.superGetBean();
	}

	public void setBean(PeBzzOrderInfo instance) {
		super.superSetBean(instance);
	}

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author hdg
	 * @return
	 */
	@Override
	public DetachedCriteria initDetachedCriteria() {

		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.createCriteria("enumConstByFlagPaymentType", "enumConstByFlagPaymentType", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagPaymentState", "enumConstByFlagCreditState", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagPaymentMethod", "enumConstByFlagPaymentMethod", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagOrderState", "enumConstByFlagOrderState", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagCheckState", "enumConstByFlagCheckState", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.or(Restrictions.ne("enumConstByFlagOrderState.code", "0"), Restrictions.isNull("enumConstByFlagOrderState")));

		return dc;
	}

	public PeBzzFinancialService getPeBzzFinancialService() {
		return peBzzFinancialService;
	}

	public void setPeBzzFinancialService(PeBzzFinancialService peBzzFinancialService) {
		this.peBzzFinancialService = peBzzFinancialService;
	}

	public List<PrBzzTchStuElective> getElectiveList() {
		return electiveList;
	}

	public void setElectiveList(List<PrBzzTchStuElective> electiveList) {
		this.electiveList = electiveList;
	}

	public PeBzzInvoiceInfo getPeBzzInvoiceInfo() {
		return peBzzInvoiceInfo;
	}

	public void setPeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) {
		this.peBzzInvoiceInfo = peBzzInvoiceInfo;
	}

	public PeEnterpriseManager getPeEnterpriseManager() {
		return peEnterpriseManager;
	}

	public void setPeEnterpriseManager(PeEnterpriseManager peEnterpriseManager) {
		this.peEnterpriseManager = peEnterpriseManager;
	}

	public SsoUser getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public List<PrBzzTchStuElectiveBack> getElectiveBackList() {
		return electiveBackList;
	}

	public void setElectiveBackList(List<PrBzzTchStuElectiveBack> electiveBackList) {
		this.electiveBackList = electiveBackList;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String seqOrderSearch() {
		String seq = ServletActionContext.getRequest().getParameter("seq");
		ServletActionContext.getRequest().setAttribute("seq", seq);
		String name = ServletActionContext.getRequest().getParameter("name");
		// try {
		// name = new
		// String(ServletActionContext.getRequest().getParameter("name").getBytes("ISO8859-1"),
		// "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		ServletActionContext.getRequest().setAttribute("name", name);
		return "seqOrderSearch";
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
