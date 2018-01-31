package com.whaty.platform.entity.web.action.basic;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PeBzzFinancialService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class BuyClassHourAction extends MyBaseAction {

	private EnumConstService enumConstService;
	private PeBzzFinancialService peBzzFinancialService;
	private String hourNum;
	private String amount;
	private PeBzzOrderInfo peBzzOrderInfo;
	private PeBzzInvoiceInfo peBzzInvoiceInfo;
	private String paymentMethod;
	private String isInvoice;
	private String merorderid; // 单据号码
	private Object o;
	private String postType;

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getMerorderid() {
		return merorderid;
	}

	public void setMerorderid(String merorderid) {
		this.merorderid = merorderid;
	}

	public Object getO() {
		return o;
	}

	public void setO(Object o) {
		this.o = o;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/buyClassHour";
	}

	public String buyHour() {

		if ("1".equalsIgnoreCase(this.isInvoice)) {
			ServletActionContext.getRequest().getSession().setAttribute("peBzzOrderInfo", peBzzOrderInfo);
			ServletActionContext.getRequest().getSession().setAttribute("peBzzInvoiceInfo", peBzzInvoiceInfo);
		} else {
			ServletActionContext.getRequest().getSession().setAttribute("peBzzOrderInfo", peBzzOrderInfo);
		}
		return "confirmPayment";
	}

	/**
	 * 修改原来的购买学时为购买金额
	 * 
	 * @return
	 */
	public String hourNum() {
		// 清空session中订单信息
		this.destroyOrderSession();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			this.peBzzOrderInfo = new PeBzzOrderInfo();
			// this.peBzzOrderInfo.setClassHour(hourNum);
			// String price =
			// this.getEnumConstService().getByNamespaceCode("ClassHourRate",
			// "0").getName();
			// this.peBzzOrderInfo.setAmount(String.valueOf((Double.valueOf(hourNum)
			// * Double.parseDouble(price))));
			//学时转成金额
			this.peBzzOrderInfo.setAmount(new BigDecimal(amount).multiply(new BigDecimal(20)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			this.peBzzOrderInfo.setClassHour(this.amount);
			this.peBzzOrderInfo.setSeq(this.getGeneralService().getOrderSeq());
			this.peBzzOrderInfo.setSsoUser(us.getSsoUser());
			// 存入session
			this.buyHour();
			this.getInvoice();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "confirmOrder";
	}

	/**
	 * 清除session中订单信息
	 */
	public void destroyOrderSession() {

		if (ServletActionContext.getRequest().getSession().getAttribute("peBzzInvoiceInfo") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("peBzzInvoiceInfo");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("peBzzOrderInfo") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("peBzzOrderInfo");
		}
	}

	/**
	 * 从session中取出订单
	 */
	public void initOrder() {
		try {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			/**
			 * 如果直接从session中取peBzzInvoiceInfo或peBzzOrderInfo 会将本次传参覆盖掉，要中转一下
			 */
			PeBzzInvoiceInfo _peBzzInvoiceInfo = (PeBzzInvoiceInfo) ActionContext.getContext().getSession().get("peBzzInvoiceInfo");
			PeBzzOrderInfo _peBzzOrderInfo = (PeBzzOrderInfo) ActionContext.getContext().getSession().get("peBzzOrderInfo");
			if (_peBzzOrderInfo != null) {
				_peBzzOrderInfo.setCname(peBzzOrderInfo.getCname());
				_peBzzOrderInfo.setNum(peBzzOrderInfo.getNum());
				peBzzOrderInfo = _peBzzOrderInfo;
			}
			if ("1".equalsIgnoreCase(this.isInvoice)) {
				EnumConst enumConstByFlagFpOpenState = this.getEnumConstService().getByNamespaceCode("FlagFpOpenState", "0");// 发票状态未开,待开
//				EnumConst enumConstByFlagPostType = this.getEnumConstService().getByNamespaceCode("FlagPostType", postType);//邮寄方式
				if (_peBzzInvoiceInfo != null) {
					_peBzzInvoiceInfo.setAddress(peBzzInvoiceInfo.getAddress());
					_peBzzInvoiceInfo.setAddressee(peBzzInvoiceInfo.getAddressee());
					_peBzzInvoiceInfo.setNum(peBzzInvoiceInfo.getNum());
					_peBzzInvoiceInfo.setPhone(peBzzInvoiceInfo.getPhone());
					_peBzzInvoiceInfo.setTitle(peBzzInvoiceInfo.getTitle());
					_peBzzInvoiceInfo.setZipCode(peBzzInvoiceInfo.getZipCode());
					peBzzInvoiceInfo = _peBzzInvoiceInfo;
				}
				peBzzInvoiceInfo.setPeBzzOrderInfo(peBzzOrderInfo);
				peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);
//				peBzzInvoiceInfo.setEnumConstByFlagPostType(enumConstByFlagPostType);//邮寄方式

				this.peBzzOrderInfo.setTel(this.peBzzInvoiceInfo.getPhone());
			} else {
				peBzzInvoiceInfo = null;
			}

			EnumConst enumConstByFlagPaymentMethod = this.getEnumConstService().getByNamespaceCode("FlagPaymentMethod", this.paymentMethod);// 支付方式
			EnumConst enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "1");// 购买学时订单
			EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");// 到账情况，未到账
			EnumConst enumConstByFlagPaymentType = this.getEnumConstService().getByNamespaceCode("FlagPaymentType", "1");// 支付类型，集体支付
			EnumConst enumConstByFlagOrderState = this.getEnumConstService().getByNamespaceCode("FlagOrderState", "1");// 订单状态，正常
			EnumConst enumConstByFlagOrderIsValid = this.enumConstService.getByNamespaceCode("FlagOrderIsValid", "1"); // 订单有效状态，有效
			EnumConst enumConstByFlagCheckState = this.getEnumConstService().getByNamespaceCode("FlagCheckState", "0");// 对账状态

			this.peBzzOrderInfo.setCreateDate(new Date());
			this.peBzzOrderInfo.setEnumConstByFlagPaymentMethod(enumConstByFlagPaymentMethod);// 支付方式
			this.peBzzOrderInfo.setEnumConstByFlagOrderType(enumConstByFlagOrderType);// 购买学时订单
			this.peBzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);// 到账情况，未到账
			this.peBzzOrderInfo.setEnumConstByFlagPaymentType(enumConstByFlagPaymentType);// 支付类型，集体支付
			this.peBzzOrderInfo.setEnumConstByFlagOrderState(enumConstByFlagOrderState);// 订单状态，正常
			this.peBzzOrderInfo.setEnumConstByFlagOrderIsValid(enumConstByFlagOrderIsValid);// 后来添加,订单的有效状态
			this.peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState);// 订单对账状态
			this.peBzzOrderInfo.setSsoUser(us.getSsoUser());// 订单所属人
			this.peBzzOrderInfo.setPayer(this.getGeneralService().getEnterpriseManagerByLoginId(us.getLoginId()).getName());// 支付机构

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 实例化订单
	 * 
	 * @author linjie
	 * @throws EntityException
	 */
	private void generateOrder(SsoUser ssoUser) throws EntityException {
		this.getGeneralService().saveElectiveBackList(null, peBzzOrderInfo, peBzzInvoiceInfo, ssoUser, null, null);
	}

	public String saveHour() {
		if (this.paymentMethod == null || "".equalsIgnoreCase(this.paymentMethod) || "0,2,3".indexOf(this.paymentMethod) < 0) {
			this.setMsg("请正确选择支付方式！");
			this.setTogo("close");
			return "paymentMsg";
		}
		this.initOrder();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser user = us.getSsoUser();
		try {
			user = this.getGeneralService().initSsoUser(us);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		String amount = user.getSumAmount();
		// 用户当前总金额
		BigDecimal beforeAmount = new BigDecimal(amount);

		try {

			if ("0".equalsIgnoreCase(this.paymentMethod)) {
				// user.setSumAmount(Double.parseDouble(user.getSumAmount())+Double.parseDouble(this.peBzzOrderInfo.getClassHour())+"");
				// user.setSumAmount(new
				// BigDecimal(StringUtils.defaultIfEmpty(user.getSumAmount(),"0")).add(new
				// BigDecimal(this.peBzzOrderInfo.getClassHour())).setScale(2,
				// BigDecimal.ROUND_HALF_UP).toString());
				ServletActionContext.getRequest().getSession().setAttribute(this.peBzzOrderInfo.getSeq(), "buyHourPayment");
				this.generateOrder(user);
				// 新增快钱支付
				String paymentType = ServletActionContext.getRequest().getParameter("paymentType");
				ServletActionContext.getRequest().getSession().setAttribute("paymentType", paymentType);				
				return "onlinePayment";
			} else {
				this.generateOrder(user);
			}

		} catch (EntityException e) {
			e.printStackTrace();
			user.setSumAmount(amount);
			this.setMsg("购买学时失败，请联系协会管理员");
			this.setTogo("close");
			return "paymentMsg";
		}

		this.setMsg("支付学时信息成功提交，请及时付款。");
		// Lee 提示后 跳转到预付费账户管理页面
		this.setTogo("/entity/basic/prepaidAccountManage_toAccountManage.action");
		return "paymentMsg";
	}

	/**
	 * 第三方支付确认
	 * 
	 * @return
	 */
	public String confirmOnlinePayment() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			SsoUser ssoUser = this.getGeneralService().initSsoUser(us);
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			dc.add(Restrictions.eq("seq", this.merorderid));

			// 确认到账时会自动添加购买的学时，这里不需处理
			// ssoUser.setSumAmount(new
			// BigDecimal(StringUtils.defaultIfEmpty(ssoUser.getSumAmount(),"0")).add(new
			// BigDecimal(this.peBzzOrderInfo.getClassHour())).setScale(2,
			// BigDecimal.ROUND_HALF_UP).toString());

			this.peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getList(dc).get(0);
			this.getGeneralService().updatePeBzzOrderInfo(peBzzOrderInfo, "confirm", ssoUser);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setMsg("学时购买成功");
		return "paymentMsg";
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public PeBzzFinancialService getPeBzzFinancialService() {
		return peBzzFinancialService;
	}

	public void setPeBzzFinancialService(PeBzzFinancialService peBzzFinancialService) {
		this.peBzzFinancialService = peBzzFinancialService;
	}

	public String getHourNum() {
		return hourNum;
	}

	public void setHourNum(String hourNum) {
		this.hourNum = hourNum;
	}

	public PeBzzOrderInfo getPeBzzOrderInfo() {
		return peBzzOrderInfo;
	}

	public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.peBzzOrderInfo = peBzzOrderInfo;
	}

	public PeBzzInvoiceInfo getPeBzzInvoiceInfo() {
		return peBzzInvoiceInfo;
	}

	public void setPeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) {
		this.peBzzInvoiceInfo = peBzzInvoiceInfo;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	/**
	 * 后来添加，再次支付
	 */
	public String continuePaymentOnline() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("seq", this.merorderid));
		try {
			this.peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getList(dc).get(0);
			ServletActionContext.getRequest().getSession().setAttribute("peBzzOrderInfo", peBzzOrderInfo);
			ServletActionContext.getRequest().getSession().setAttribute(this.merorderid, "buyHourPayment");
			// 新增快钱支付
			String paymentType = ServletActionContext.getRequest().getParameter("paymentType");
			ServletActionContext.getRequest().getSession().setAttribute("paymentType", paymentType);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			this.setMsg("购买学时失败,请联系协会管理员");
			this.setTogo("/entity/basic/prepaidAccountManage_toAccountManage.action");
			return "paymentMsg";
		}
		return "onlinePayment";
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
