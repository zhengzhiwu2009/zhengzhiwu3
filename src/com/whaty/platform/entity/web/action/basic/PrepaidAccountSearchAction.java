package com.whaty.platform.entity.web.action.basic;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.prepaidAccount.PrepaidAccountService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PrepaidAccountSearchAction extends MyBaseAction<PeEnterpriseManager> {

	private String recordId = "";
	private String id = "";
	private String orderId = "";
	private String allotStudent = "";
	private String allotAmount = "";
	private String cardNo = "";
	// private String enterpriseName = "";
	private PrepaidAccountService prepaidAccountService;

	private int totalCount; // 总条数
	private int startIndex = 0; // 开始数
	private int pageSize = 10; // 页面显示数
	private Page page;
	private List lists = null;// 分配金额的学员集合

	public List getLists() {
		return lists;
	}

	public void setLists(List lists) {
		this.lists = lists;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("预付费账户管理");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("操作时间"), "operateTimeDate", false, false, true, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("操作时间开始时间"), "operateTimeSTDate", true, false, false, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("操作时间结束时间"), "operateTimeEDDate", true, false, false, "TextField", false, 200, "");

		ColumnConfig columnOperateType = new ColumnConfig(this.getText("操作类型"), "enumConstByFlagOperateType.name", true, true, true, "TextField", false, 100, "");
		String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagOperateType'";
		columnOperateType.setComboSQL(sql1);
		this.getGridConfig().addColumn(columnOperateType);
		this.getGridConfig().addColumn(this.getText("操作学时"), "operateAmount", false, false, true, "sumAmount");
		this.getGridConfig().addColumn(this.getText("账户剩余学时"), "accountAmount", false, false, true, "amount");
		this.getGridConfig().addColumn(this.getText("订单号"), "orderId", false, false, false, "amount");
		this.getGridConfig().addColumn(this.getText("记录号"), "recordId", false, false, false, "amount");

		if (capabilitySet.contains(this.servletPath + "_buyAmount.action")) {
			this
					.getGridConfig()
					.addMenuScript(
							this.getText("购买学时"),
							"{	var jsonData ='"
									+ us.getId()
									+ "' ;document.getElementById('user-defined-content').style.display='none'; "
									+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/prepaidAccountManage_toPurchaseStudyHour.action' method='post' name='formx1' style='display:none'><input type=hidden name=userId value='\"+jsonData+\"' ></form>\";"
									+ "	document.formx1.submit();" + "	document.getElementById('user-defined-content').innerHTML=\"\";};");
		}

		if (capabilitySet.contains(this.servletPath + "_studentAmount.action")) {
			this
					.getGridConfig()
					.addMenuScript(
							this.getText("分配/剥离学生学时"),
							"{	var jsonData ='"
									+ us.getId()
									+ "' ;document.getElementById('user-defined-content').style.display='none'; "
									+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/assignStudyHour.action?forg=y&flag=old' method='post' name='formx1' style='display:none'><input type=hidden name=userId value='\"+jsonData+\"' ></form>\";"
									+ "	document.formx1.submit();" + "	document.getElementById('user-defined-content').innerHTML=\"\";};");

		}

		if (capabilitySet.contains(this.servletPath + "_enterpriseAmount.action")) {
			String sql = "SELECT NVL(C.FK_PARENT_ID,-1)   FROM SSO_USER A   INNER JOIN PE_ENTERPRISE_MANAGER B        "
					+ "    ON A.ID = B.FK_SSO_USER_ID   INNER JOIN PE_ENTERPRISE C   ON B.FK_ENTERPRISE_ID = C.ID  " + " WHERE A.LOGIN_ID = '" + us.getLoginId() + "' ";
			try {
				List list = this.getGeneralService().getBySQL(sql);
				if (list.get(0).toString().equals("-1")) {
					this
							.getGridConfig()
							.addMenuScript(
									this.getText("分配/剥离二级集体学时"),
									"{	var jsonData ='"
											+ us.getId()
											+ "' ;document.getElementById('user-defined-content').style.display='none'; "
											+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/peSubEnterprise.action?flag=inner' method='post' name='formx1' style='display:none'><input type=hidden name=userId value='\"+jsonData+\"' ></form>\";"
											+ "	document.formx1.submit();" + "	document.getElementById('user-defined-content').innerHTML=\"\";};");

				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		this
				.getGridConfig()
				.addRenderScript(
						this.getText("查看详情"),
						"{if(record.data['enumConstByFlagOperateType.name']=='分配-学员') {return '<a href=\"/entity/basic/prepaidAccountSearch_toViewStudentAdd.action?recordId='+record.data['id']+'\"target=\"_blank\"><font color=#0000ff ><u>查看详情</u></font></a>';}"
								+ "else if(record.data['enumConstByFlagOperateType.name']=='剥离-学员'){return '<a href=\"/entity/basic/prepaidAccountSearch_toViewStudentDel.action?recordId='+record.data['id']+'\"target=\"_blank\"><font color=#0000ff ><u>查看详情</u></font></a>';}"
								+ "else if(record.data['enumConstByFlagOperateType.name']=='分配-二级集体'){return '<a href=\"/entity/basic/prepaidAccountSearch_toViewEnterpriseAdd.action?recordId='+record.data['id']+'\"target=\"_blank\"><font color=#0000ff ><u>查看详情</u></font></a>';}"
								+ "else if(record.data['enumConstByFlagOperateType.name']=='剥离-二级集体'){return '<a href=\"/entity/basic/prepaidAccountSearch_toViewEnterpriseDel.action?recordId='+record.data['id']+'\"target=\"_blank\"><font color=#0000ff ><u>查看详情</u></font></a>';}"
								+ "else if(record.data['enumConstByFlagOperateType.name']=='充值'){return '<a href=/entity/basic/buyClassHourRecord.action?id='+record.data['orderId']+'&method=purseAmount&tuifei=2><font color=#0000ff ><u>查看详情</u></font></a>';}"
								+ "else if(record.data['enumConstByFlagOperateType.name']=='消费'){return '<a href=/entity/basic/buyClassHourRecord.action?id='+record.data['orderId']+'&method=consume><font color=#0000ff ><u>查看详情</u></font></a>';}"
								+ "else if(record.data['enumConstByFlagOperateType.name']=='更新前余额'){return '-'}"
								+ "else{return '<a href=/entity/basic/buyClassHourRecord.action?id='+record.data['orderId']+'&method=consume&tuifei=1><font color=#0000ff ><u>查看详情</u></font></a>';}}",
						"id");

	}

	public PeEnterpriseManager getBean() {
		// TODO Auto-generated method stub
		return (PeEnterpriseManager) super.superGetBean();
	}

	public void setBean(PeEnterpriseManager enterpriseManager) {
		// TODO Auto-generated method stub
		super.superSetBean(enterpriseManager);
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeEnterpriseManager.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/prepaidAccountSearch";
	}

	public BigDecimal calClassHour(SsoUser ssoUser) {
		BigDecimal s = (ssoUser.getSumAmount() == null || ssoUser.getSumAmount().equals("")) ? new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP) : new BigDecimal(ssoUser.getSumAmount())
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal a = (ssoUser.getAmount() == null || ssoUser.getAmount().equals("")) ? new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP) : new BigDecimal(ssoUser.getAmount()).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		return s.subtract(a).setScale(1, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 初始化bean
	 * 
	 * @author linjie
	 * @return
	 */
	public void initBean() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc.createCriteria("ssoUser", "ssoUser");
		dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
		List<PeEnterpriseManager> list = null;
		PeEnterpriseManager peEnterpriseManager = null;
		try {
			list = this.getGeneralService().getList(dc);
			if (null != list && list.size() > 0) {
				peEnterpriseManager = list.get(0);
			} else {
				peEnterpriseManager = new PeEnterpriseManager();
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setBean(peEnterpriseManager);
	}

	public String toAccountManage() {
		initBean();
		return "accountManage";
	}

	public String viewPurchaseHistory() {

		return "viewPurchaseHistory";
	}

	public String viewAssignHistory() {
		return "viewAssignHistory";
	}

	/**
	 * 查看学员分配金额
	 * 
	 * @return
	 */
	public String toViewStudentAdd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cardNo = request.getParameter("cardNo");
		String recordId = request.getParameter("recordId");
		request.getSession().setAttribute("cardNo", cardNo);// Lee
		request.getSession().setAttribute("recordId", recordId);// Lee
		// 查询条件回显
		try {
			// 原来查询返回条件为page,现改为list,去掉分页
			lists = this.prepaidAccountService.viewStudentPrepaidAccountAdd(recordId, cardNo);

			List list = this.prepaidAccountService.getAmount(recordId);
			Object[] amountArray = new Object[list.size()];
			amountArray = (Object[]) list.get(0);

			this.allotStudent = amountArray[0].toString();
			this.allotAmount = amountArray[1].toString();

		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("allotStudent", allotStudent);
		request.setAttribute("allotAmount", allotAmount);

		return "viewStudentAdd";
	}

	/**
	 * 查看学员剥离金额
	 * 
	 * @return
	 */
	public String toViewStudentDel() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cardNo = request.getParameter("cardNo");
		String recordId = request.getParameter("recordId");
		request.getSession().setAttribute("cardNo", cardNo);// Lee
		request.getSession().setAttribute("recordId", recordId);// Lee
		// 查询条件回显
		try {
			lists = this.prepaidAccountService.viewStudentPrepaidAccountAdd(recordId, cardNo);

			List list = this.prepaidAccountService.getAmount(recordId);
			Object[] amountArray = new Object[list.size()];
			amountArray = (Object[]) list.get(0);

			this.allotStudent = amountArray[0].toString();
			this.allotAmount = amountArray[1].toString();

		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("allotStudent", allotStudent);
		request.setAttribute("allotAmount", allotAmount);

		return "viewStudentDel";
	}

	/**
	 * 查看集体分配金额
	 * 
	 * @return
	 */
	public String toViewEnterpriseAdd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String enterpriseName = request.getParameter("enterpriseName");
		String recordId = request.getParameter("recordId");
		request.getSession().setAttribute("enterpriseName", enterpriseName);// Lee
		request.getSession().setAttribute("recordId", recordId);// Lee
		// 查询条件回显
		try {
			lists = this.prepaidAccountService.viewEnterprisePrepaidAccountAdd(recordId, enterpriseName);

			List list = this.prepaidAccountService.getEnterpriseAmount(recordId);
			Object[] amountArray = new Object[list.size()];
			amountArray = (Object[]) list.get(0);

			this.allotStudent = amountArray[0].toString();
			this.allotAmount = amountArray[1].toString();

		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("allotStudent", allotStudent);
		request.setAttribute("allotAmount", allotAmount);

		return "viewEnterpriseAdd";
	}

	/**
	 * 查看集体剥离金额
	 * 
	 * @return
	 */
	public String toViewEnterpriseDel() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String enterpriseName = request.getParameter("enterpriseName");
		String recordId = request.getParameter("recordId");
		request.getSession().setAttribute("enterpriseName", enterpriseName);// Lee
		request.getSession().setAttribute("recordId", recordId);// Lee
		// 查询条件回显
		try {
			lists = this.prepaidAccountService.viewEnterprisePrepaidAccountAdd(recordId, enterpriseName);

			List list = this.prepaidAccountService.getEnterpriseAmount(recordId);
			Object[] amountArray = new Object[list.size()];
			amountArray = (Object[]) list.get(0);

			this.allotStudent = amountArray[0].toString();
			this.allotAmount = amountArray[1].toString();

		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("allotStudent", allotStudent);
		request.setAttribute("allotAmount", allotAmount);

		return "viewEnterpriseDel";
	}

	/**
	 * 分配学时初始化
	 * 
	 * @author linjie
	 * @return
	 */
	public List<PeBzzStudent> initAssignStudyHour() {

		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("ssoUser", "ssoUser").createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN);
		;
		dc.createAlias("peEnterprise", "peEnterprise", DetachedCriteria.LEFT_JOIN);

		if (!us.getUserLoginType().equals("3")) {
			String peEnterpriseCode = (String) ServletActionContext.getRequest().getSession().getAttribute("peEnterprisemanager");

			DetachedCriteria dc2 = DetachedCriteria.forClass(PeEnterprise.class);
			dc2.add(Restrictions.eq("code", peEnterpriseCode));
			dc2.setProjection(Property.forName("id"));
			dc.add(Restrictions.or(Restrictions.eq("peEnterprise.code", peEnterpriseCode), Property.forName("peEnterprise.peEnterprise.id").in(dc2)));
		}
		List<PeBzzStudent> list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 重写框架方法：数据库查询（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		Page page = null;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		// String sql = " select * from (select ar.id id,
		// TO_CHAR(ASSIGN_DATE,'yyyy-MM-dd hh24:mi:ss') operateTime, ''
		// operateTimeSTDatetime, '' operateTimeEDDatetime , ec_type.name
		// operateType, operate_amount operateAmount, account_amount
		// accountAmount, "
		// + " fk_order_id orderId from assign_record ar "
		// + " left join enum_const ec_type on ar.flag_operate_type = ec_type.id
		// and "
		// + " namespace='FlagOperateType' where fk_user_id='" + us.getId() + "'
		// order by assign_date desc) where 1=1 ";
		// 新查询SQL 充值、剥离、退费显示+(正号) 消费、分配显示-(负号)
		// 现在有条件用来屏蔽老数据 因为老数据没有操作学时数和当前余额AR.OPERATE_AMOUNT IS NOT NULL AND
		StringBuffer sBuffer = new StringBuffer();
		if (!"4".equals(us.getUserLoginType())) {// 一级集体管理员
			sBuffer
					.append(" SELECT * FROM (SELECT AR.ID ID, TO_DATE(TO_CHAR(ASSIGN_DATE, 'yyyy-MM-dd hh24:mi:ss'), 'yyyy-MM-dd hh24:mi:ss') OPERATETIME, '' OPERATETIMESTDATETIME, '' OPERATETIMEEDDATETIME, EC_TYPE.NAME OPERATETYPE, ");
			sBuffer
					.append(" CASE EC_TYPE.CODE WHEN '0' THEN '+' || TO_CHAR(ROUND(OPERATE_AMOUNT, 2),'fm9999990.0099') WHEN '3' THEN '+' || TO_CHAR(ROUND(OPERATE_AMOUNT, 2),'fm9999990.0099') WHEN '4' THEN '+' || TO_CHAR(ROUND(OPERATE_AMOUNT, 2),'fm9999990.0099') WHEN '6' THEN '+' || TO_CHAR(ROUND(OPERATE_AMOUNT, 2),'fm9999990.0099') WHEN '99' THEN '+' || TO_CHAR(ROUND(OPERATE_AMOUNT, 2),'fm9999990.0099') ELSE '-' || TO_CHAR(ROUND(OPERATE_AMOUNT, 2),'fm9999990.0099') END OPERATEAMOUNT,");
			sBuffer
					.append(" TO_CHAR(ROUND(ACCOUNT_AMOUNT, 2),'fm9999990.0099') ACCOUNTAMOUNT, FK_ORDER_ID ORDERID  FROM ASSIGN_RECORD AR LEFT JOIN ENUM_CONST EC_TYPE ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID AND NAMESPACE = 'FlagOperateType'");
			sBuffer.append(" WHERE AR.OPERATE_AMOUNT IS NOT NULL AND FK_USER_ID = '" + us.getId() + "') WHERE 1 = 1");
		} else {
			sBuffer
					.append(" SELECT * FROM ((SELECT AR.ID ID, TO_CHAR(ASSIGN_DATE, 'yyyy-MM-dd hh24:mi:ss') OPERATETIME, '' OPERATETIMESTDATETIME, '' OPERATETIMEEDDATETIME, EC_TYPE.NAME OPERATETYPE, ");
			sBuffer
					.append(" CASE EC_TYPE.CODE WHEN '0' THEN '+' || TO_CHAR(OPERATE_AMOUNT,'fm9999990.0099') WHEN '3' THEN '+' || TO_CHAR(OPERATE_AMOUNT,'fm9999990.0099') WHEN '4' THEN '+' || TO_CHAR(OPERATE_AMOUNT,'fm9999990.0099') WHEN '6' THEN '+' || TO_CHAR(OPERATE_AMOUNT,'fm9999990.0099') WHEN '99' THEN '+' || TO_CHAR(OPERATE_AMOUNT,'fm9999990.0099') ELSE '-' || TO_CHAR(OPERATE_AMOUNT,'fm9999990.0099') END OPERATEAMOUNT, ");
			sBuffer
					.append(" TO_CHAR(ACCOUNT_AMOUNT,'fm9999990.0099') ACCOUNTAMOUNT , FK_ORDER_ID ORDERID FROM ASSIGN_RECORD AR LEFT JOIN ENUM_CONST EC_TYPE ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID AND NAMESPACE = 'FlagOperateType' ");
			sBuffer.append(" WHERE AR.OPERATE_AMOUNT IS NOT NULL AND FK_USER_ID = '" + us.getId()
					+ "') UNION ALL (SELECT AR.ID, TO_CHAR(ASSIGN_DATE, 'yyyy-MM-dd hh24:mi:ss') OPERATETIME, '' OPERATETIMESTDATETIME, '' OPERATETIMEEDDATETIME, EC_TYPE.NAME OPERATETYPE, ");
			sBuffer
					.append(" CASE EC_TYPE.CODE WHEN '0' THEN '-' || ARS.CLASS_NUM WHEN '3' THEN '-' || ARS.CLASS_NUM WHEN '4' THEN '-' || ARS.CLASS_NUM WHEN '6' THEN '-' || ARS.CLASS_NUM WHEN '99' THEN '-' || ARS.CLASS_NUM ELSE '+' || ARS.CLASS_NUM END OPERATEAMOUNT, ");
			sBuffer
					.append(" SUBAMOUNT ACCOUNTAMOUNT, FK_ORDER_ID ORDERID  FROM ASSIGN_RECORD AR INNER JOIN ASSIGN_RECORD_STUDENT ARS ON AR.ID = ARS.FK_RECORD_ID LEFT JOIN ENUM_CONST EC_TYPE ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID ");
			sBuffer.append(" AND NAMESPACE = 'FlagOperateType' WHERE AR.OPERATE_AMOUNT IS NOT NULL AND ARS.FK_ENTERPRISE_MANAGER_ID = (SELECT ID FROM PE_ENTERPRISE_MANAGER WHERE FK_SSO_USER_ID = '"
					+ us.getId() + "'))) WHERE 1 = 1 ");
		}
		StringBuffer sqlBuffer = new StringBuffer(sBuffer.toString());
		try {
			// 处理时间的查询参数
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					if (params.get("search__operateTimeSTDate") != null) {
						String[] startDateST = (String[]) params.get("search__operateTimeSTDate");
						String tempDate = startDateST[0] + " 00:00:00";
						;
						if (startDateST.length == 1 && !StringUtils.defaultString(startDateST[0]).equals("")) {
							params.remove("search__operateTimeSTDate");
							context.setParameters(params);
							sqlBuffer.append("           and operateTime >= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					if (params.get("search__operateTimeEDDate") != null) {
						String[] startDateED = (String[]) params.get("search__operateTimeEDDate");
						String tempDate = startDateED[0] + " 23:59:59";
						if (startDateED.length == 1 && !StringUtils.defaultString(startDateED[0]).equals("")) {
							params.remove("search__operateTimeEDDate");
							context.setParameters(params);
							sqlBuffer.append("           and operateTime <= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					if (params.get("search__enumConstByFlagOperateType.name") != null) {
						String[] operateType = (String[]) params.get("search__enumConstByFlagOperateType.name");
						String operateTypeValue = operateType[0];
						if (operateType.length == 1 && !StringUtils.defaultString(operateType[0]).equals("")) {
							params.remove("search__enumConstByFlagOperateType.name");
							context.setParameters(params);
							sqlBuffer.append("      and operateType LIKE '%" + operateTypeValue + "%'");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
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
				if (name.equals("enumConstByFlagOperateType.name")) {
					name = "OperateType";
				}
			} while (true);
			// this.setSqlCondition(sqlBuffer);
			String temp = this.getSort();
			// 截掉前缀 Combobox_PeXxxxx.
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}

			if (this.getGridConfig().getColumByDateIndex(temp) != null) {
				if (this.getSort() != null && temp != null) {
					if (temp.equals("enumConstByFlagOperateType.name")) {
						temp = "OperateType";
					}
					if (temp.equals("operateTimeDate")) {
						temp = "operateTime";
					}
					if (temp.equals("accountAmount")) {
						temp = "to_number(accountAmount)";
					}
					if (temp.equals("operateAmount")) {
						temp = "to_number(operateAmount)";
					}
					if (!temp.equals("id")) {
						if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
							sqlBuffer.append(" order by " + temp + " desc ");
						}

						else {
							sqlBuffer.append(" order by " + temp + " asc ");
						}
					} else {
						sqlBuffer.append(" ORDER BY OPERATETIME DESC ");
					}

				}
			}
			page = getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 计算学时数
	 * 
	 * @author linjie
	 * @return
	 */
	public String countClassHour(int num, String price) {
		BigDecimal bnum = new BigDecimal(num);
		BigDecimal bprice = new BigDecimal(price);
		return bnum.multiply(bprice).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getAllotStudent() {
		return allotStudent;
	}

	public void setAllotStudent(String allotStudent) {
		this.allotStudent = allotStudent;
	}

	public String getAllotAmount() {
		return allotAmount;
	}

	public void setAllotAmount(String allotAmount) {
		this.allotAmount = allotAmount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public PrepaidAccountService getPrepaidAccountService() {
		return prepaidAccountService;
	}

	public void setPrepaidAccountService(PrepaidAccountService prepaidAccountService) {
		this.prepaidAccountService = prepaidAccountService;
	}

}
