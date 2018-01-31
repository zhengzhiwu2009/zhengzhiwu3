package com.whaty.platform.quartz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.bill99.seashell.domain.dto.gatewayquery.GatewayOrderDetail;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.OnlineOrderInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.quartz.QuartzService;
import com.whaty.platform.entity.util.Payment99BillUtil;
import com.whaty.platform.entity.util.PaymentUtil;
import com.whaty.util.SpringUtil;

/**
 * 订单自动确认定时任务
 * 
 * @author linjie
 * 
 */
public class QuartzOrderJob extends QuartzJobBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private QuartzService quartzService;
	private GeneralService generalService;
	private EnumConstService enumConstService;

	public void setQuartzService(QuartzService quartzService) {
		this.quartzService = quartzService;
	}

	public GeneralService getGeneralService() {
		if (generalService == null) {
			generalService = (GeneralService) SpringUtil.getBean("generalService");
		}
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	public QuartzService getQuartzService() {
		if (quartzService == null) {
			quartzService = (QuartzService) SpringUtil.getBean("quartzService");
		}
		return quartzService;
	}

	public EnumConstService getEnumConstService() {
		if (enumConstService == null) {
			enumConstService = (EnumConstService) SpringUtil.getBean("enumConstService");
		}
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long start = new Date().getTime();
		// Lee 支付接口切换
		if (true) {
			// 快钱
			this.confirm99BillOrder();
		} else {
			// 未确认订单检查
			this.confirmOrder();
		}
		long end = new Date().getTime();
		System.out.println("订单定时任务" + sdf.format(new Date()) + "完成，耗时：" + (end - start) + "ms");
	}

	/**
	 * 确认订单
	 * 
	 * @param
	 * @return
	 * @author linjie
	 */
	private List confirmOrder() {

		// 网银订单
		EnumConst enumConstByFlagPaymentMethod = this.getEnumConstService().getByNamespaceCode("FlagPaymentMethod", "0");
		// 未到账订单
		EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");
		// 有效订单
		EnumConst enumConstByFlagOrderIsValid = this.getEnumConstService().getByNamespaceCode("FlagOrderIsValid", "1");

		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.createCriteria("enumConstByFlagPaymentMethod");
		dc.createCriteria("enumConstByFlagPaymentState");
		dc.createCriteria("enumConstByFlagOrderIsValid");
		dc.createCriteria("enumConstByFlagOrderType");
		dc.add(Restrictions.eq("enumConstByFlagPaymentMethod", enumConstByFlagPaymentMethod));// 网银订单
		dc.add(Restrictions.eq("enumConstByFlagPaymentState", enumConstByFlagPaymentState));// 未到账订单
		dc.add(Restrictions.eq("enumConstByFlagOrderIsValid", enumConstByFlagOrderIsValid));// 有效订单
		dc.add(Restrictions.lt("autoCheckTimes", 6));// 自动确认6次以下订单，用户每次点击网银支付或有6次自动确认的机会

		try {
			List<PeBzzOrderInfo> peOrderList = this.getGeneralService().getList(dc);
			PaymentUtil pu = new PaymentUtil();
			for (PeBzzOrderInfo po : peOrderList) {
				String seq = po.getSeq();
				List<OnlineOrderInfo> onlineOrderList = pu.checkOrderState(seq);
				if (onlineOrderList != null && onlineOrderList.size() > 0) {
					for (OnlineOrderInfo o : onlineOrderList) {
						if ("1".equalsIgnoreCase(o.getState()) && seq.equals(o.getMerOrderId())) {// 支付成功
							if (Double.parseDouble(o.getAmountSum()) == Double.parseDouble(po.getAmount())) {
								try {
									this.getGeneralService().updatePeBzzOrderInfo(po, "autoCheck", null);
									System.out.println("订单" + o.getMerOrderId() + "确认成功！");
								} catch (Exception e) {
									System.out.println("订单" + o.getMerOrderId() + "确认失败！--" + e.getMessage());
								}
							} else {
								System.out.println("订单" + o.getMerOrderId() + "确认失败！--付款金额不符");
							}
						}
					}
				}
				po.setAutoCheckTimes(po.getAutoCheckTimes() + 1);
				this.getGeneralService().save(po);
			}
			System.out.println("订单自动确认任务完成！！");
		} catch (EntityException e) {
			System.out.println("订单自动确认失败！！--" + e.getMessage());
		}
		return null;
	}

	/**
	 * 确认订单
	 * 
	 * @param
	 * @return
	 */
	private List confirm99BillOrder() {
		// 网银订单
		EnumConst enumConstByFlagPaymentMethod = this.getEnumConstService().getByNamespaceCode("FlagPaymentMethod", "0");
		// 未到账订单
		EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");
		// 有效订单
		EnumConst enumConstByFlagOrderIsValid = this.getEnumConstService().getByNamespaceCode("FlagOrderIsValid", "1");

		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.createCriteria("enumConstByFlagPaymentMethod");
		dc.createCriteria("enumConstByFlagPaymentState");
		dc.createCriteria("enumConstByFlagOrderIsValid");
		dc.createCriteria("enumConstByFlagOrderType");
		dc.add(Restrictions.eq("enumConstByFlagPaymentMethod", enumConstByFlagPaymentMethod));// 网银订单
		dc.add(Restrictions.eq("enumConstByFlagPaymentState", enumConstByFlagPaymentState));// 未到账订单
		dc.add(Restrictions.eq("enumConstByFlagOrderIsValid", enumConstByFlagOrderIsValid));// 有效订单
		dc.add(Restrictions.lt("autoCheckTimes", 6));// 自动确认6次以下订单，用户每次点击网银支付或有6次自动确认的机会

		try {
			List<PeBzzOrderInfo> peOrderList = this.getGeneralService().getList(dc);

			Payment99BillUtil pu = new Payment99BillUtil();
			for (PeBzzOrderInfo po : peOrderList) {
				// 订单号
				String seq = po.getSeq();

				List<GatewayOrderDetail> orderList = new ArrayList<GatewayOrderDetail>();
				// 根据订单号查询第三方订单列表(用来查看支付状态等)
				orderList = pu.checkOrderState(seq);

				// 循环判断订单到账状态并修改数据库标识
				if (orderList != null && orderList.size() > 0) {
					for (GatewayOrderDetail detail : orderList) {
						// 订单号相同、支付结果为10(到账)
						if (seq.equals(detail.getOrderId()) && "10".equals(detail.getPayResult())) {
							// 远程订单与第三方订单金额对比(快钱金额以"分"为单位,远程金额以元为单位)
							String _99BillAmount = BigDecimal.valueOf(Long.valueOf(detail.getOrderAmount())).divide(new BigDecimal(100))
									.toString();
							if (Double.parseDouble(po.getAmount()) == Double.parseDouble(_99BillAmount)) {
								try {
									this.getGeneralService().updatePeBzzOrderInfo(po, "autoCheck", null);
									System.out.println("订单" + detail.getOrderId() + "确认成功！");
								} catch (Exception e) {
									System.out.println("订单" + detail.getOrderId() + "确认失败！--" + e.getMessage());
								}
							} else {
								System.out.println("订单" + detail.getOrderId() + "确认失败！--付款金额不符");
							}
						}
					}
				}
				po.setAutoCheckTimes(po.getAutoCheckTimes() + 1);
				this.getGeneralService().save(po);
			}
			System.out.println("订单自动确认任务完成！！");
		} catch (EntityException e) {
			System.out.println("订单自动确认失败！！--" + e.getMessage());
		}
		return null;
	}
}
