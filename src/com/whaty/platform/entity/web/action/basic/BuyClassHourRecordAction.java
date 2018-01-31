package com.whaty.platform.entity.web.action.basic;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzRefundInfo;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class BuyClassHourRecordAction extends MyBaseAction<PeBzzOrderInfo> {
	private String id;
	private String refundReason;
	private EnumConstService enumConstService;
	private PeBzzInvoiceInfo peBzzInvoiceInfo;
	private PeBzzRefundInfo peBzzRefundInfo;
	private PeBzzOrderInfo peBzzOrderInfo;
	private List<PrBzzTchStuElective> electiveList;
	private SsoUser ssoUser;
	private String method;

	private String userId;
	private String forward;
	
	private String postType;//邮寄方式

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	/**
	 * 初始化列表 购买消费学时订单
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		String tuifeiString = ServletActionContext.getRequest().getParameter("tuifei");//判断是否是退费
		this.getGridConfig().setTitle(this.getText("消费记录"));
		if ("1".equals(tuifeiString))
			this.getGridConfig().setTitle(this.getText("退费记录"));
		if ("2".equals(tuifeiString))
			this.getGridConfig().setTitle(this.getText("充值记录"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("订单号"), "seq", true);
		this.getGridConfig().addColumn(this.getText("订单别名"), "cname", true);
		this.getGridConfig().addColumn(this.getText("下单时间"), "createDate", true);
		this.getGridConfig().addColumn(this.getText("到账时间"), "paymentDate", true);
		if ("1".equals(tuifeiString))
			this.getGridConfig().addColumn(this.getText("退费时间"), "refundDate", true);
		this.getGridConfig().addColumn(this.getText("订单金额"), "amount", false);
		this.getGridConfig().addColumn(this.getText("购买学时数"), "classHour", false, false, false, "");

		ColumnConfig c_name2 = new ColumnConfig(this.getText("支付方式"), "combobox_paymentMethod", true, false, true, "TextField", true, 50, "");
		c_name2.setComboSQL("select id, e.name as name from enum_const e where e.namespace = 'FlagPaymentMethod'");
		this.getGridConfig().addColumn(c_name2);
		this.getGridConfig().addColumn(this.getText("到账状态"), "paymentState", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("订单状态"), "orderState", false, false, false, "");
		this.getGridConfig().addColumn(this.getText("发票状态"), "invoiceState", false, false, true, "");

		// this.getGridConfig().addRenderScript(this.getText("发票状态"),
		// "{if(record.data['invoiceState']==''||record.data['invoiceState']==null)
		// {" +
		// "return '未开'" +
		// "} else {" +
		// "return record.data['invoiceState']" +
		// "}}", "id");

		// this.getGridConfig().addColumn(this.getText("退费状态"),
		// "refundState",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("支付编号"), "paymentCode", false, false, false, "");

		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		ActionContext.getContext().getSession().put("id", id);

		// 未明确信息 暂时注释 dgh 7-25
		if ("consume".equalsIgnoreCase(method)) {
			this.getGridConfig().addRenderScript(this.getText("详情"), "{return '<a href=\"collectiveOrderQuery_viewDeatail.action?id='+record.data['id']+'&flag=viewDetail\">查看</a>'" + "}", "id");
		}
		/*
		 * if((this.userId != null || !"".equalsIgnoreCase(this.userId)) &&
		 * "3".equalsIgnoreCase(us.getRoleId())){//协会管理员查看充值记录
		 * this.getGridConfig().addMenuScript(this.getText("返回"),"{window.location.href='/entity/basic/prepaidAccountManage.action'}");
		 * }else{ this.getGridConfig().addRenderScript(this.getText("开具发票"),
		 * "{if(record.data['invoiceState']=='未开') {" + "return '<a
		 * href=\"/entity/basic/buyClassHourRecord_toInvoiceApplyJsp.action?id='+record.data['id']+'\">申请发票</a>'" + "}
		 * else {" + "return '申请发票'" + "}}", "id");
		 * 
		 * this.getGridConfig().addRenderScript(this.getText("申请退费"),
		 * "{if(record.data['paymentState']=='已到账'&&(record.data['refundState']==null ||
		 * record.data['refundState']=='无退费记录')&&(record.data['invoiceState'] ==
		 * '未开' || record.data['invoiceState']==null)) {" + "return '<a
		 * href=\"/entity/basic/buyClassHourRecord_toApplyRefund.action?id='+record.data['id']+'\">申请退费</a>'" + "}
		 * else if(record.data['refundState']=='无退费记录'){" + "return '无退费记录'" + "}
		 * else {" + "return '<a
		 * href=\"/entity/basic/buyClassHourRecord_viewReason.action?id='+record.data['id']+'\">'+record.data['refundState']+'</a>'" +
		 * "}}", "id");
		 * 
		 * this.getGridConfig().addRenderScript(this.getText("再次支付"),
		 * "{if(record.data['paymentState']=='已到账') {" + "return '已支付'" + "}
		 * else if(record.data['paymentCode']=='0') {" + "return '<a
		 * href=\"/entity/basic/buyClassHour_continuePaymentOnline.action?merorderid='+record.data['seq']+'\",
		 * target=\"_blank\">去支付</a>'" + "} else {" + "return '等待确认'" + "}}",
		 * "id");
		 * 
		 * if(this.userId!=null && !"".equals(this.userId)){
		 * this.getGridConfig().addMenuScript(this.getText("返回"),"{window.location.href='/entity/basic/prepaidAccountManage.action'}");
		 * }else{
		 * this.getGridConfig().addMenuScript(this.getText("返回"),"{window.location.href='/entity/basic/prepaidAccountManage_toAccountManage.action'}"); } }
		 */
		// this.getGridConfig().addMenuScript(this.getText("返回"),"{window.location.href='/entity/basic/prepaidAccountManage_toAccountManage.action'}");
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back()}");
		ActionContext.getContext().getSession().put("id", id);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzOrderInfo.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/buyClassHourRecord";
	}

	/**
	 * 查看详情
	 * 
	 * @author linjie
	 * @return
	 */
	public String viewDeatail() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		PeBzzOrderInfo peBzzOrderInfo = null;
		try {
			peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getById(this.id);
			DetachedCriteria dcInvoice = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
			dcInvoice.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));
			List<PeBzzInvoiceInfo> peBzzInvoiceInfoList = this.getGeneralService().getList(dcInvoice);
			if (peBzzInvoiceInfoList != null && peBzzInvoiceInfoList.size() > 0) {
				this.peBzzInvoiceInfo = (PeBzzInvoiceInfo) peBzzInvoiceInfoList.get(0);
				peBzzOrderInfo = this.peBzzInvoiceInfo.getPeBzzOrderInfo();
				this.ssoUser = this.peBzzInvoiceInfo.getPeBzzOrderInfo().getSsoUser();
			} else {
				DetachedCriteria dcOrder = DetachedCriteria.forClass(PeBzzOrderInfo.class);
				dcOrder.createCriteria("ssoUser");
				dcOrder.add(Restrictions.eq("id", this.id));
				peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getList(dcOrder).get(0);
				this.ssoUser = peBzzOrderInfo.getSsoUser();
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}

		try {
			this.peBzzOrderInfo = peBzzOrderInfo;
			Page page = this.getGeneralService().getOrderDetail(peBzzOrderInfo, us.getSsoUser(), 10000, 0);
			if (page != null) {
				this.electiveList = page.getItems();
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return "viewOrder";
	}

	/**
	 * 重写框架方法：学时购买记录（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if ((this.userId == null || "".equalsIgnoreCase(this.userId)) && !"3".equalsIgnoreCase(us.getRoleId())) {
			this.userId = us.getLoginId();
		}
		String tuifeiString = ServletActionContext.getRequest().getParameter("tuifei");
		if ("1".equals(tuifeiString)) {

		}
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT * FROM (SELECT t.id          AS id, ");
		sqlBuffer.append("       t.seq         AS seq, ");
		sqlBuffer.append("       t.name        AS cname, ");
		sqlBuffer.append("       t.create_date AS createDate, ");
		sqlBuffer
				.append("		 (case when to_char(t.payment_date,'yyyy-mm-dd hh24:mi:ss') is null then null else  to_date(to_char(t.payment_date, 'yy-mm-dd hh24:mi:ss'), 'yy-mm-dd hh24:mi:ss') end) AS paymentDate,				");
		if ("1".equals(tuifeiString))
			sqlBuffer.append(" to_char(t.refund_Date,'yyyy-MM-dd hh24:mi:ss') refundDate, ");
		sqlBuffer.append("       to_number(t.amount)      AS amount, ");
		sqlBuffer.append("       to_number(t.class_hour)  AS classHour, ");
		sqlBuffer.append("       e1.name       AS combobox_paymentMethod, ");
		sqlBuffer.append("       e3.name       AS paymentState, ");
		sqlBuffer.append("       e2.name       AS orderState, ");
		sqlBuffer.append("       nvl(e4.name,'未开')       AS invoiceState, ");
		sqlBuffer.append("       nvl(e5.name,'无退费记录')  AS refundState, ");
		sqlBuffer.append("       e1.code       AS paymentCode ");
		sqlBuffer.append("FROM   pe_bzz_order_info t ");
		sqlBuffer.append("       LEFT JOIN pe_bzz_invoice_info pbii ");
		sqlBuffer.append("         ON t.id = pbii.fk_order_id ");
		sqlBuffer.append("       LEFT JOIN pe_bzz_refund_info pbri ");
		sqlBuffer.append("         ON t.id = pbri.fk_order_id ");
		sqlBuffer.append("       LEFT JOIN enum_const e1 ");
		sqlBuffer.append("         ON t.flag_payment_method = e1.id ");
		sqlBuffer.append("       LEFT JOIN enum_const e2 ");
		sqlBuffer.append("         ON t.flag_order_state = e2.id ");
		sqlBuffer.append("       LEFT JOIN enum_const e3 ");
		sqlBuffer.append("         ON t.FLAG_PAYMENT_STATE = e3.id ");
		sqlBuffer.append("       LEFT JOIN enum_const e4 ");
		sqlBuffer.append("         ON pbii.flag_FP_open_state = e4.id ");
		sqlBuffer.append("       LEFT JOIN enum_const e5 ");
		sqlBuffer.append("         ON pbri.flag_refund_state = e5.id ");
		sqlBuffer.append("       LEFT JOIN enum_const e6 ");
		sqlBuffer.append("         ON e6.namespace = 'ClassHourRate' and e6.code='0' ");
		sqlBuffer.append("       LEFT join enum_const e8 on e8.id = t.FLAG_PAYMENT_TYPE and e8.code = '1' ");
		sqlBuffer.append("       LEFT JOIN sso_user u ");
		sqlBuffer.append("         ON u.id = t.create_user ");
		sqlBuffer.append("        LEFT JOIN enum_const e7 on e7.id = t.flag_order_type and" + " e7.namespace='FlagOrderType' and e7.code='1'    where 1=1  AND t.ID = '" + this.id + "'"
				+ ") WHERE 1=1");

		this.setSqlCondition(sqlBuffer);
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return page;
	}

	public String toInvoiceApplyJsp() {
		return "invoiceApply";
	}

	/**
	 * 退费
	 * 
	 * @author linjie
	 * @return
	 */
	public String toApplyRefund() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("id", id));
		try {
			PeBzzOrderInfo order = (PeBzzOrderInfo) this.getGeneralService().getList(dc).get(0);
			Set set = order.getPeBzzInvoiceInfos();
			if (set.size() > 0) {
				this.setMsg("以开具发票不能申请退费");
				this.setTogo("/entity/basic/buyClassHourRecord.action");
				return "msg";
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(order.getCreateDate());
			cal.add(Calendar.DAY_OF_MONTH, +7);
			Date date = cal.getTime();
			if (date.before(new Date())) {
				this.setMsg("支付时间以超过7天，不能申请退费");
				this.setTogo("/entity/basic/buyClassHourRecord.action");
				return "msg";
			}
			String sql = "select pbtse.id\n" + "          from pr_bzz_tch_stu_elective pbtse\n" + "         where pbtse.fk_order_id = '" + this.id + "'\n" + "           and pbtse.online_time > '0'";
			List list = this.getGeneralService().getBySQL(sql);
			if (list.size() > 0) {
				this.setMsg("购买课程已有开始学习，不能申请退费");
				this.setTogo("/entity/basic/buyClassHourRecord.action");
				return "msg";
			}

		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		forward = "/entity/basic/buyClassHourRecord_refundReason.action";
		return "refundApply";
	}

	/**
	 * 申请发票
	 * 
	 * @author linjie
	 * @return
	 */
	public String applyInvoice() {
		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("id", id));
		EnumConst enumConstByFlagFpOpenState = this.getEnumConstService().getByNamespaceCode("FlagFpOpenState", "0");
		EnumConst enumConstByFlagPostType = this.getEnumConstService().getByNamespaceCode("FlagPostType", postType);
		try {
			List<PeBzzOrderInfo> orderList = this.getGeneralService().getList(dc);
			this.peBzzInvoiceInfo.setPeBzzOrderInfo(orderList.get(0));
			this.peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);
			this.peBzzInvoiceInfo.setEnumConstByFlagPostType(enumConstByFlagPostType);
			this.getGeneralService().save(this.peBzzInvoiceInfo);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("申请失败！");
			this.setTogo("/entity/basic/buyClassHourRecord.action");
		}
		this.setMsg("申请成功！");
		this.setTogo("/entity/basic/buyClassHourRecord.action");
		return "msg";
	}

	/**
	 * 保存退费申请记录
	 */
	public String refundReason() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("id", this.id));
		try {
			List<PeBzzOrderInfo> orderList = this.getGeneralService().getList(dc);
			EnumConst enumConstByFlagRefundState = this.getEnumConstService().getByNamespaceCode("FlagRefundState", "0");
			PeBzzRefundInfo peBzzRefundInfo = new PeBzzRefundInfo();
			peBzzRefundInfo.setApplyDate(new Date());
			peBzzRefundInfo.setPeBzzOrderInfo(orderList.get(0));
			peBzzRefundInfo.setReason(this.refundReason);
			peBzzRefundInfo.setEnumConstByFlagRefundState(enumConstByFlagRefundState);
			this.getGeneralService().save(peBzzRefundInfo);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("申请失败！");
			this.setTogo("/entity/basic/buyClassHourRecord.action");
		}
		this.setMsg("申请成功，请等待协会管理员审核！");
		this.setTogo("/entity/basic/buyClassHourRecord.action");
		return "msg";
	}

	/**
	 * 查看缘由
	 * 
	 * @return
	 */
	public String viewReason() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
		dc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState");
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.add(Restrictions.eq("peBzzOrderInfo.id", this.id));
		try {
			List list = this.getGeneralService().getList(dc);
			if (list.size() > 0) {
				this.peBzzRefundInfo = (PeBzzRefundInfo) list.get(0);
			} else {
				this.peBzzRefundInfo = new PeBzzRefundInfo();
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "viewReason";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public PeBzzInvoiceInfo getPeBzzInvoiceInfo() {
		return peBzzInvoiceInfo;
	}

	public void setPeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) {
		this.peBzzInvoiceInfo = peBzzInvoiceInfo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public PeBzzRefundInfo getPeBzzRefundInfo() {
		return peBzzRefundInfo;
	}

	public void setPeBzzRefundInfo(PeBzzRefundInfo peBzzRefundInfo) {
		this.peBzzRefundInfo = peBzzRefundInfo;
	}

	public PeBzzOrderInfo getPeBzzOrderInfo() {
		return peBzzOrderInfo;
	}

	public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.peBzzOrderInfo = peBzzOrderInfo;
	}

	public List<PrBzzTchStuElective> getElectiveList() {
		return electiveList;
	}

	public void setElectiveList(List<PrBzzTchStuElective> electiveList) {
		this.electiveList = electiveList;
	}

	public SsoUser getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
