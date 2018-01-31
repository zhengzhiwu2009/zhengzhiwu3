package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.OnlineOrderInfo;
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
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PaymentManageAction extends MyBaseAction<PeBzzOrderInfo> {

	private PeBzzFinancialService peBzzFinancialService;
	private List<PrBzzTchStuElective> electiveList = new ArrayList<PrBzzTchStuElective>();
	private List<PrBzzTchStuElectiveBack> electiveBackList = new ArrayList<PrBzzTchStuElectiveBack>();
	private PeBzzInvoiceInfo peBzzInvoiceInfo;
	private PeBzzStudent peBzzStudent;
	private SsoUser ssoUser;
	private PeEnterpriseManager peEnterpriseManager;
	private EnumConstService enumConstService;
	private double price; // 课程的价格

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		this.getGridConfig().setTitle(this.getText("缴费管理"));
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("订单号"), "seq", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("支付名称"), "payer", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("支付金额"), "amount", true, false, true, "");

		ColumnConfig c_name1 = new ColumnConfig(this.getText("支付方式"), "combobox_methodName", true, false, true, "TextField", true, 50, "");
		c_name1.setComboSQL("select id, e.name as name from enum_const e where e.namespace = 'FlagPaymentMethod' and id <> '40288a7b394207de01394212e47f0005x' UNION SELECT '10', '在线支付-个人网银'  FROM DUAL UNION SELECT '14', '在线支付-企业网银' FROM DUAL ");
		this.getGridConfig().addColumn(c_name1);

		ColumnConfig c_name2 = new ColumnConfig(this.getText("订单类型"), "combobox_orderType", true, false, true, "TextField", true, 50, "");
		c_name2.setComboSQL("select id, e.name as name from enum_const e where e.namespace = 'FlagOrderType'");
		this.getGridConfig().addColumn(c_name2);

		this.getGridConfig().addColumn(this.getText("单据号码"), "num", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("订单生成时间"), "createDate", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("支付时间"), "paymentDate", false, false, true, "");

		ColumnConfig c_name3 = new ColumnConfig(this.getText("到账情况"), "combobox_stateName", true, false, true, "TextField", true, 50, "");
		c_name3.setComboSQL("select id, e.name as name from enum_const e where e.namespace = 'FlagPaymentState'");
		this.getGridConfig().addColumn(c_name3);

		ColumnConfig c_name4 = new ColumnConfig(this.getText("支付状态"), "combobox_isPaied", true, false, true, "TextField", true, 50, "");
		c_name4.setComboSQL("select id, e.name as name from enum_const e where e.namespace = 'FlagElectivePayStatus'");
		this.getGridConfig().addColumn(c_name4);

		ColumnConfig c_name5 = new ColumnConfig(this.getText("支付类型"), "combobox_typeName", true, false, true, "TextField", true, 50, "");
		c_name5.setComboSQL("select id, e.name as name from enum_const e where e.namespace = 'FlagPaymentType'");
		this.getGridConfig().addColumn(c_name5);

		ColumnConfig c_name6 = new ColumnConfig(this.getText("是否有效"), "combobox_isvalid", true, false, true, "TextField", true, 50, "");
		c_name6.setComboSQL("select id, e.name as name from enum_const e where e.namespace = 'FlagOrderIsValid'");
		this.getGridConfig().addColumn(c_name6);

		this.getGridConfig().addColumn(this.getText("支付账号"), "loginId", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("订单备忘录"), "note", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("订单有效位"), "code", false, false, false, "");

		ColumnConfig c_name7 = new ColumnConfig(this.getText("对账状态"), "combobox_checkState", true, false, true, "TextField", true, 50, "");
		c_name7.setComboSQL("select id, e.name as name from enum_const e where e.namespace = 'FlagCheckState'");
		this.getGridConfig().addColumn(c_name7);

		this.getGridConfig().addColumn(this.getText("对账时间"), "checkDate", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("支付时间起始"), "payStartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("支付时间结束"), "payEndDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("生成时间起始"), "createStartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("生成时间结束"), "createEndDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("对账时间起始"), "checkStartDatetime", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("对账时间结束"), "checkEndDatetime", true, false, false, "");
		// this.getGridConfig().addRenderFunction("订单详情", "<a
		// href='/entity/basic/paymentManage_orderDetail.action?bean.id=${value}'
		// >详情</a>", "id");
		this.getGridConfig().addRenderScript(this.getText("订单详情"), "{if( record.data['combobox_orderType'] != '购买学时订单' ) {" + "   return '<a href=\"/entity/basic/paymentManage_orderDetail.action?bean.id='+record.data['id']+'\">详情</a>'" + "} else {" + "   return '--'" + "}}", "id");
		if (capabilitySet.contains(this.servletPath + "_check.action")) {// 对账权限判断
			this.getGridConfig().addMenuFunction(this.getText("到账确认"), "creditState.true");
			this.getGridConfig().addMenuFunction(this.getText("取消到账"), "creditState.false");
			this.getGridConfig().addMenuFunction(this.getText("对账确认"), "checkState.true");
			this.getGridConfig().addMenuFunction(this.getText("撤销对账"), "checkState.false");
			this.getGridConfig().addMenuScript(this.getText("批量导入对账信息"), "{window.location='/entity/basic/paymentManage_toExcelCheckIn.action?flag=" + this.getFlag() + "';}");
		}
	}

	/**
	 * 重写框架方法：缴费管理列表（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		sqlBuffer.append("select t.id as id,\n");
		sqlBuffer.append("               t.seq as seq,\n");
		sqlBuffer.append("               case\n");
		sqlBuffer.append("                 when c.code = '1' then\n");
		sqlBuffer.append("                  (select pe.name\n");
		sqlBuffer.append("                     from pe_enterprise pe, pe_enterprise_manager pem\n");
		sqlBuffer.append("                    where pe.id = pem.FK_ENTERPRISE_ID\n");
		sqlBuffer.append("                      and pem.login_id = e.login_id)\n");
		sqlBuffer.append("                 else\n");
		sqlBuffer.append("                  t.payer\n");
		sqlBuffer.append("               end as payer,\n");
		sqlBuffer.append("               to_number(t.amount) as amount,\n");
		sqlBuffer.append("               a.name     || '' || CASE WHEN t.payment_method ='10' THEN '-个人网银' WHEN t.payment_method ='14' THEN '-企业网银' ELSE '' END  as combobox_methodName,\n");
		sqlBuffer.append("               f.name as combobox_orderType,\n");
		sqlBuffer.append("               t.num as num,\n");
		sqlBuffer.append("               t.create_date as createDate,\n");
		sqlBuffer.append("               t.payment_date as paymentDate,\n");
		sqlBuffer.append("               b.name as combobox_stateName,\n");
		sqlBuffer.append("               case\n");
		sqlBuffer.append("                 when b.code = '1' or b.code = '2' or t.num is not null then\n");
		sqlBuffer.append("                  '已支付'\n");
		sqlBuffer.append("                 when 1<>1 then 'a' else\n");
		sqlBuffer.append("                  '未支付'\n");
		sqlBuffer.append("               end as combobox_isPaied,\n");
		sqlBuffer.append("               c.name as combobox_typeName,\n");
		sqlBuffer.append("               d.name as combobox_isvalid,\n");
		sqlBuffer.append("               e.login_id as loginId,\n");
		sqlBuffer.append("               t.name as note,\n");
		sqlBuffer.append("               d.code as code,\n");
		sqlBuffer.append("               g.name as combobox_checkState,\n");
		sqlBuffer.append("               t.check_date as checkDate,\n");
		sqlBuffer.append("               '' as xx,\n");
		sqlBuffer.append("               '' as xx1,\n");
		sqlBuffer.append("               '' as xx2,\n");
		sqlBuffer.append("               '' as xx3,\n");
		sqlBuffer.append("               '' as xx4,\n");
		sqlBuffer.append("               '' as xxx\n");
		sqlBuffer.append("          from pe_bzz_order_info t\n");
		sqlBuffer.append("         left join sso_user e\n");
		sqlBuffer.append("            on t.create_user = e.id\n");
		sqlBuffer.append("         inner join enum_const a\n");
		sqlBuffer.append("            on a.id = t.flag_payment_method  \n");
		sqlBuffer.append("         inner join enum_const b\n");
		sqlBuffer.append("            on b.id = t.flag_payment_state\n");
		sqlBuffer.append("         inner join enum_const c\n");
		sqlBuffer.append("            on c.id = t.flag_payment_type\n");
		sqlBuffer.append("         inner join enum_const d\n");
		sqlBuffer.append("            on d.id = t.FLAG_ORDER_ISVALID\n");
		sqlBuffer.append("         inner join enum_const f\n");
		sqlBuffer.append("            on f.id = t.FLAG_ORDER_TYPE\n");
		sqlBuffer.append("         left join enum_const g\n");
		sqlBuffer.append("            on g.id = t.FLAG_CHECK_STATE\n   where 1 = 1  ");
		try {
			// 处理时间的查询参数
			ActionContext context = ActionContext.getContext();
			if (context.getParameters() != null) {
				Map params = this.getParamsMap();
				if (params != null) {
					if (params.get("search__payStartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__payStartDatetime");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							// startDate[0] = ">=" + startDate[0];
							params.remove("search__payStartDatetime");
							// params.put("search__peBzzOrderInfo.paymentDate",
							// startDate);
							context.setParameters(params);
							// SimpleDateFormat format = new
							// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							// Date sDate = format.parse(tempDate + " 00:00:00
							// ");
							// Date sDate = format.parse(tempDate);
							sqlBuffer.append("           and t.payment_date >= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					if (params.get("search__payEndDatetime") != null) {
						String[] startDate = (String[]) params.get("search__payEndDatetime");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__payEndDatetime");
							context.setParameters(params);
							sqlBuffer.append("           and t.payment_date <= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					if (params.get("search__createStartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__createStartDatetime");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__createStartDatetime");
							context.setParameters(params);
							sqlBuffer.append("           and t.create_date >= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					if (params.get("search__createEndDatetime") != null) {
						String[] startDate = (String[]) params.get("search__createEndDatetime");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__createEndDatetime");
							context.setParameters(params);
							sqlBuffer.append("           and t.create_date <= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					if (params.get("search__checkStartDatetime") != null) {
						String[] startDate = (String[]) params.get("search__checkStartDatetime");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__checkStartDatetime");
							context.setParameters(params);
							sqlBuffer.append("           and t.check_date >= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
					if (params.get("search__checkEndDatetime") != null) {
						String[] startDate = (String[]) params.get("search__checkEndDatetime");
						String tempDate = startDate[0];
						if (startDate.length == 1 && !StringUtils.defaultString(startDate[0]).equals("")) {
							params.remove("search__checkEndDatetime");
							context.setParameters(params);
							sqlBuffer.append("           and t.check_date <= to_date('" + tempDate + "','yyyy-MM-dd hh24:mi:ss') \n");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// this.setSort("createDate");
			if (StringUtils.defaultString(this.getSort()).equals("") || StringUtils.defaultString(this.getSort()).equals("id")) {
				this.setSort("createDate");
				this.setDir("desc");
			}
			this.setSqlCondition(sqlBuffer);
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzOrderInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/paymentManage";
	}

	public PeBzzOrderInfo getBean() {
		return super.superGetBean();
	}

	public void setBean(PeBzzOrderInfo instance) {
		super.superSetBean(instance);
	}

	/**
	 * 重写框架方法--列更新
	 * 
	 * @author linjie
	 * @return
	 */
	public Map updateColumn() {
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = this.getIds().split(",");
			List idList = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			dc.createCriteria("enumConstByFlagOrderType");
			dc.createCriteria("enumConstByFlagPaymentType");
			dc.createCriteria("enumConstByFlagPaymentMethod");
			dc.createCriteria("ssoUser", "ssoUser");
			dc.add(Restrictions.in("id", ids));
			try {
				List<PeBzzOrderInfo> list = this.getGeneralService().getList(dc);
				if (action.equals("creditState.true")) {
					try {
						checkBeforeUpdateColumn(idList);
					} catch (EntityException e1) {
						map.put("success", "false");
						map.put("info", e1.getMessage());
						return map;
					}
					for (PeBzzOrderInfo order : list) {
						this.getGeneralService().updatePeBzzOrderInfo(order, "confirm", null);
					}
					msg = "到账确认";
				} else if (action.equals("creditState.false")) {
					for (PeBzzOrderInfo order : list) {
						this.getGeneralService().updatePeBzzOrderInfo(order, "cancel", null);
					}
					msg = "取消到账";
				} else if (action.equals("checkState.true")) {
					Boolean exeFlag = true;
					for (PeBzzOrderInfo order : list) {

						String seq = order.getSeq();
						String amount = order.getAmount();
						OnlineOrderInfo onlineOrderInfo = new OnlineOrderInfo();
						onlineOrderInfo.setMerOrderId(seq);
						onlineOrderInfo.setAmountSum(amount);
						onlineOrderInfo.setState("1");
						boolean reb = this.getGeneralService().reconciliation(onlineOrderInfo);// 执行对账
						if (!reb) {
							exeFlag = false;
							msg += "订单" + seq + "对账异常，可能存在金额不符或订单未到账等情况，不要操作已对账订单！<br/>";
						}
					}
					if (msg.length() < 1) {
						msg = "对账成功！";
					}
					map.put("success", exeFlag.toString());
					map.put("info", msg);
					return map;
				} else if (action.equals("checkState.false")) {
					Boolean exeFlag = true;
					for (PeBzzOrderInfo order : list) {

						String seq = order.getSeq();
						String amount = order.getAmount();
						OnlineOrderInfo onlineOrderInfo = new OnlineOrderInfo();
						onlineOrderInfo.setMerOrderId(seq);
						onlineOrderInfo.setAmountSum(amount);
						onlineOrderInfo.setState("1");
						boolean reb = this.getGeneralService().unReconciliation(onlineOrderInfo);// 执行撤销对账
						if (!reb) {
							exeFlag = false;
							msg += "订单" + seq + "撤销对账异常，请选择已到账的订单进行操作！<br/>";
						}
						if (msg.length() < 1) {
							msg = "撤销成功！";
						}
					}
					map.put("success", exeFlag.toString());
					map.put("info", msg);
					return map;
				}
				map.put("success", "true");
				map.put("info", ids.length + "条记录" + msg + "操作成功");
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}

		return map;
	}

	/**
	 * 重写框架方法--列更新前的验证
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void checkBeforeUpdateColumn(List idList) throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.createCriteria("enumConstByFlagOrderType");
		dc.createCriteria("enumConstByFlagPaymentType");
		dc.createCriteria("enumConstByFlagPaymentMethod");
		dc.createCriteria("ssoUser", "ssoUser");
		dc.add(Restrictions.in("id", idList));
		try {
			List<PeBzzOrderInfo> list = this.getGeneralService().getList(dc);
			for (PeBzzOrderInfo order : list) {
				if (order.getEnumConstByFlagPaymentState().getCode() != null && "1".equals(order.getEnumConstByFlagPaymentState().getCode())) {
					throw new EntityException("所选订单存在已到账的的订单，不能重复到账确认！");
				} else if (order.getEnumConstByFlagPaymentState().getCode() != null && "2".equals(order.getEnumConstByFlagPaymentState().getCode())) {
					throw new EntityException("所选订单存在已退费的的订单，不能进行到账确认操作！");
				} else if (order.getEnumConstByFlagPaymentMethod().getCode().equals("2") || order.getEnumConstByFlagPaymentMethod().getCode().equals("3")) {
					if (order.getNum() == null || order.getNum().equals("")) {
						throw new EntityException("所选订单为电汇或支票支付订单，尚未填写单据号码，不能进行到账确认操作！");
					}
				}

			}
		} catch (EntityException e) {
			throw e;
		}
	}

	/**
	 * 订单详情
	 * 
	 * @author linjie
	 * @return
	 */
	public String orderDetail() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		PeBzzOrderInfo peBzzOrderInfo = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			this.setBean(this.getGeneralService().getById(this.getBean().getId()));
			DetachedCriteria dcInvoice = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
			dcInvoice.add(Restrictions.eq("peBzzOrderInfo", this.getBean()));
			List peBzzInvoiceInfoList = this.getGeneralService().getList(dcInvoice);
			if (peBzzInvoiceInfoList != null && peBzzInvoiceInfoList.size() > 0) {
				this.peBzzInvoiceInfo = (PeBzzInvoiceInfo) peBzzInvoiceInfoList.get(0);
				peBzzOrderInfo = this.peBzzInvoiceInfo.getPeBzzOrderInfo();
				this.ssoUser = this.peBzzInvoiceInfo.getPeBzzOrderInfo().getSsoUser();
			} else {
				String hql = "from PeBzzOrderInfo p where p.id='" + this.getBean().getId() + "'";
				peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getByHQL(hql).get(0);
				this.ssoUser = peBzzOrderInfo.getSsoUser();
				String abc = peBzzOrderInfo.getSsoUser().getAmount();
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		// orderDetailElective();
		DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);
		stuDc.add(Restrictions.eq("ssoUser", this.ssoUser));
		try {
			List stuList = this.getGeneralService().getList(stuDc);
			if (stuList != null && stuList.size() > 0) {
				this.peBzzStudent = (PeBzzStudent) stuList.get(0);
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Page page = this.getGeneralService().getOrderDetail(peBzzOrderInfo, null, 10000, 0);
			if (page != null) {
				this.electiveList = page.getItems();
			}
			EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
			price = Double.parseDouble(ec.getName());
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (this.peBzzStudent == null) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			dc.add(Restrictions.eq("ssoUser", this.ssoUser));
			dc.createCriteria("peEnterprise", "peEnterprise");
			try {
				List list = this.getGeneralService().getList(dc);
				if (list.size() > 0) {
					this.peEnterpriseManager = (PeEnterpriseManager) list.get(0);
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		 * else
		 * if("2".equals(this.getBean().getEnumConstByFlagOrderType().getCode())) {
		 * DetachedCriteria dcStu =
		 * DetachedCriteria.forClass(PeBzzStudent.class);
		 * dcStu.add(Restrictions.eq("ssoUser", this.ssoUser)); try { List list =
		 * this.getGeneralService().getList(dcStu); } catch (EntityException e) {
		 * e.printStackTrace(); } } else { DetachedCriteria dc =
		 * DetachedCriteria.forClass(PeEnterpriseManager.class);
		 * dc.add(Restrictions.eq("ssoUser", this.ssoUser));
		 * dc.createCriteria("peEnterprise", "peEnterprise"); try {
		 * this.peEnterpriseManager =
		 * (PeEnterpriseManager)this.getGeneralService().getList(dc).get(0); }
		 * catch (EntityException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */
		return "orderDetail";
	}

	/**
	 * 暂不使用
	 * 
	 * @author linjie
	 * @return
	 */
	public void orderDetailElective() {
		try {
			DetachedCriteria electiveDc = null;
			if (this.getBean().getEnumConstByFlagPaymentState().getCode().equalsIgnoreCase("0")) {
				electiveDc = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
				electiveDc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
				electiveDc.createCriteria("prBzzTchOpencourse.peBzzTchCourse", "peBzzTchCourse");
				electiveDc.add(Restrictions.eq("peBzzOrderInfo", this.getBean()));
				electiveDc.createAlias("peBzzStudent", "peBzzStudent");
				electiveDc.createCriteria("peBzzStudent.peEnterprise", "peEnterprise", electiveDc.LEFT_JOIN);
				electiveDc.addOrder(Order.asc("peBzzStudent.trueName"));
				this.electiveBackList = this.getGeneralService().getList(electiveDc);
			} else {
				electiveDc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
				electiveDc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
				electiveDc.createCriteria("prBzzTchOpencourse.peBzzTchCourse", "peBzzTchCourse");
				electiveDc.add(Restrictions.eq("peBzzOrderInfo", this.getBean()));
				electiveDc.createAlias("peBzzStudent", "peBzzStudent");
				electiveDc.createCriteria("peBzzStudent.peEnterprise", "peEnterprise", electiveDc.LEFT_JOIN);
				electiveDc.addOrder(Order.asc("peBzzStudent.trueName"));
				this.electiveList = this.getGeneralService().getList(electiveDc);

			}

		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toExcelCheckIn() {
		return "toExcelCheckIn";
	}

	/**
	 * 上传
	 * 
	 * @author linjie
	 * @return
	 */
	public String batchUpload() {
		int re = 0;
		int re_error = 0;
		StringBuffer msg = new StringBuffer();
		Workbook work = null;
		try {
			work = Workbook.getWorkbook(this.get_upload());
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量更新失败！<br/>");
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		Map orderMap = new HashMap();

		Pattern pOrder = Pattern.compile("^\\d{5,15}$");// 5-15数字
		Pattern pNum = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,3})?$");// 金额精确到3位小数
		for (int i = 1; i < rows; i++) {
			String orderSeq = sheet.getCell(0, i).getContents().trim();// 订单号
			String orderAmount = sheet.getCell(1, i).getContents().trim();// 订单金额
			// 验证格式
			if (orderSeq == null || "".equals(orderSeq.trim())) {
				msg.append("第" + (i + 1) + "行数据，订单号不能为空！<br/>");
				continue;
			}
			if (orderAmount == null || "".equals(orderAmount.trim())) {
				msg.append("第" + (i + 1) + "行数据，金额不能为空！<br/>");
				continue;
			}

			if (!pOrder.matcher(orderSeq).matches()) {
				msg.append("第" + (i + 1) + "行数据，订单号格式不正确！<br/>");
				continue;
			}
			if (!pNum.matcher(orderAmount).matches()) {
				msg.append("第" + (i + 1) + "行数据，金额格式不正确！<br/>");
				continue;
			}

			String checkSql = "select o.flag_payment_method, o.flag_payment_state, o.flag_check_state\n" + "  from pe_bzz_order_info o\n" + " where o.seq = '" + orderSeq + "'";
			try {
				List orderList = this.getGeneralService().getBySQL(checkSql);
				if (orderList != null && orderList.size() > 0) {
					// 40288a7b394207de01394212e47f0005 在线
					// 40288a7b394207de0139421358110006 预付费
					// 40288a7b394207de013942141b340007 公司转账
					// 40288a7b394207de013942147c520008 支票

					// 40288a7b394207de01394221a6ff000e 已到账
					// 40288a7b394207de013942221104000f 未到账
					// 40288a7b394207de013942221104000f 已退费

					// 402880a91ecade03011ecae12bcd0002x 已对账
					// 402880a91ecade03011ecae12bcd0001x 未对账
					// 402880a91ecade03011ecae12bcd0003x 对账异常
					for (int x = 0; x < orderList.size(); x++) {
						Object[] order = (Object[]) orderList.get(x);
						String flag_payment_state = order[1].toString();
						String flag_check_state = order[2].toString();
						if (!"40288a7b394207de01394221a6ff000e".equals(flag_payment_state) && !"40288a7b394207de013942221104000f".equals(flag_payment_state)) {
							msg.append("第" + (i + 1) + "行数据，订单未到账！<br/>");
							continue;
						}
						if ("402880a91ecade03011ecae12bcd0002x".equals(flag_check_state)) {// 已对账的不处理
							continue;
						}
					}

				} else {
					msg.append("第" + (i + 1) + "行数据，订单不存在！<br/>");
					continue;
				}

				if (!orderMap.containsKey(orderSeq)) {
					orderMap.put(orderSeq, orderAmount);
				} else {
					msg.append("第" + (i + 1) + "行数据，存在与前部分重复的订单号！<br/>");
					continue;
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (msg.length() > 0) {
			this.setMsg("对账失败！<br/>" + msg.toString());
			this.setTogo(this.servletPath + ".action");
			return "msg";
		} else {
			Set kSet = orderMap.keySet();// 没有格式错误数据数量
			int ms = kSet.size();
			// re = ms;
			if (ms > 0) {
				Iterator it = kSet.iterator();
				for (; it.hasNext();) {
					String seq = (String) it.next();
					String amount = (String) orderMap.get(seq);
					OnlineOrderInfo onlineOrderInfo = new OnlineOrderInfo();
					onlineOrderInfo.setMerOrderId(seq);
					onlineOrderInfo.setAmountSum(amount);
					onlineOrderInfo.setState("1");
					boolean reb = this.getGeneralService().reconciliation(onlineOrderInfo);// 执行对账
					if (reb) {
						re += 1;
					} else {
						re_error += 1;
						msg.append("订单" + seq + "对账异常，可能存在金额不符或订单未到账等情况！<br/>");
					}
				}
			}
		}
		String remsg = "成功对账" + re + "个订单数据<br/>";
		if (re_error > 0) {
			remsg += "失败" + re_error + "个订单数据<br/>";
		}
		this.setMsg(remsg + msg.toString());
		this.setTogo(this.servletPath + ".action");
		return "msg";
	}

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author linjie
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
}
