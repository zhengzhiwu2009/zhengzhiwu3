package com.whaty.platform.quartz;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.whaty.platform.entity.bean.AutoRefund;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzRefundInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.quartz.QuartzService;
import com.whaty.platform.entity.util.PaymentUtil;
import com.whaty.util.SpringUtil;

/**
 * 退款自动确认定时任务
 * 
 * @author linjie
 * 
 */
public class QuartzRefundJob extends QuartzJobBean implements Serializable {

	/**
	 * 
	 */
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
			// 快钱退款中订单确认
			this.confirm99BillRefund();
		} else {
			// 退款中订单确认
			this.confirmRefund();
		}
		long end = new Date().getTime();
		System.out.println("退费定时任务" + sdf.format(new Date()) + "完成，耗时：" + (end - start) + "ms");
	}

	/**
	 * 退款自动确认
	 * 
	 * @param
	 * @return
	 * @author linjie
	 */
	private List confirmRefund() {

		// 网银订单
		EnumConst enumConstByFlagPaymentMethod = this.getEnumConstService().getByNamespaceCode("FlagPaymentMethod", "0");
		// 到账订单
		EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "1");
		// 退费中订单
		EnumConst enumConstByFlagRefundState = this.getEnumConstService().getByNamespaceCode("FlagRefundState", "3");
		// 有效订单
		EnumConst enumConstByFlagOrderIsValid = this.getEnumConstService().getByNamespaceCode("FlagOrderIsValid", "1");

		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("enumConstByFlagPaymentMethod", enumConstByFlagPaymentMethod));// 网银订单
		dc.add(Restrictions.eq("enumConstByFlagPaymentState", enumConstByFlagPaymentState));// 到账订单
		dc.add(Restrictions.eq("enumConstByFlagOrderIsValid", enumConstByFlagOrderIsValid));// 有效订单
		dc.add(Restrictions.eq("enumConstByFlagRefundState", enumConstByFlagRefundState));// 退费中订单
		dc.add(Restrictions.lt("autoCheckTimes", 100));// 自动确认100次以下订单，用户每次点击网银支付或有6次自动确认的机会

		try {
			List<PeBzzOrderInfo> peOrderList = this.getGeneralService().getList(dc);
			PaymentUtil pu = new PaymentUtil();
			for (PeBzzOrderInfo po : peOrderList) {
				String refundId = po.getRefundId();
				String orderId = po.getSeq();
				DetachedCriteria reDc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
				reDc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState");
				reDc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
				reDc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentMethod", "enumConstByFlagPaymentMethod");
				reDc.createCriteria("peBzzOrderInfo.ssoUser", "ssoUser");
				reDc.add(Restrictions.eq("peBzzOrderInfo.seq", orderId));
				PeBzzRefundInfo peBzzRefundInfo = (PeBzzRefundInfo) this.getGeneralService().getList(reDc).get(0);
				if (refundId != null && !"".equals(refundId)) {
					String re = this.getGeneralService().confirmRefund(po.getSeq(), refundId);
					if (re != null && !"".equals(re)) {
						System.out.println("订单：" + orderId + "--" + re);
					} else {
						// 修改退费信息状态
						EnumConst ec2 = this.getEnumConstService().getByNamespaceCode("FlagRefundState", "1");
						peBzzRefundInfo.setEnumConstByFlagRefundState(ec2);

						// 修改订单退费状态
						EnumConst ec3 = this.getEnumConstService().getByNamespaceCode("FlagOrderState", "0");
						po.setEnumConstByFlagOrderState(ec3);
						po.setEnumConstByFlagRefundState(ec2);

						EnumConst paymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "2");
						po.setEnumConstByFlagPaymentState(paymentState);

						po.setRefundDate(new Date());
						System.out.println("订单：" + orderId + "自动退费成功!");
					}
				} else {
					AutoRefund autoRefund = this.getGeneralService().refundOnlineApply(orderId, "定时任务，自动执行", peBzzRefundInfo.getReason(),
							po.getAmount());
					String status = autoRefund.getStatus();// 返回状态
					String refundId_rec = autoRefund.getRefundId();// 退款流水号，用于查询退款状态
					if (refundId_rec != null || !"".equals(refundId_rec)) {
						if ("0000".equalsIgnoreCase(status) || "0008".equalsIgnoreCase(status)) {// 申请成功||存在申请记录
							// 执行判断并操作平台内退款
							String re = this.getGeneralService().confirmRefund(orderId, refundId_rec);
							if (re != null && !"".equals(re)) {
								System.out.println("订单：" + orderId + "--" + re);
							} else {
								// 修改退费信息状态
								EnumConst ec2 = this.getEnumConstService().getByNamespaceCode("FlagRefundState", "1");
								peBzzRefundInfo.setEnumConstByFlagRefundState(ec2);

								// 修改订单退费状态
								EnumConst ec3 = this.getEnumConstService().getByNamespaceCode("FlagOrderState", "0");
								po.setEnumConstByFlagOrderState(ec3);
								po.setEnumConstByFlagRefundState(ec2);

								EnumConst paymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "2");
								po.setEnumConstByFlagPaymentState(paymentState);

								po.setRefundDate(new Date());

								System.out.println("订单：" + orderId + "自动退费成功!");
							}
						} else {
							System.out.println("订单：" + orderId + "退费,等待平台确认。!");
						}
					} else {
						System.out.println("订单：" + orderId + "退费,平台无返回,等待平台确认。!");
					}
				}
				po.setAutoCheckTimes(po.getAutoCheckTimes() + 1);
				this.getGeneralService().save(peBzzRefundInfo);
				this.getGeneralService().save(po);
			}
			System.out.println("订单退款任务结束！！");
		} catch (EntityException e) {
			System.out.println("订单退款失败！！--" + e.getMessage());
		}
		return null;
	}

	private List confirm99BillRefund() {
		// 网银订单
		EnumConst enumConstByFlagPaymentMethod = this.getEnumConstService().getByNamespaceCode("FlagPaymentMethod", "0");
		// 到账订单
		EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "1");
		// 退费中订单
		EnumConst enumConstByFlagRefundState = this.getEnumConstService().getByNamespaceCode("FlagRefundState", "3");
		// 有效订单
		EnumConst enumConstByFlagOrderIsValid = this.getEnumConstService().getByNamespaceCode("FlagOrderIsValid", "1");

		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("enumConstByFlagPaymentMethod", enumConstByFlagPaymentMethod));// 网银订单
		dc.add(Restrictions.eq("enumConstByFlagPaymentState", enumConstByFlagPaymentState));// 到账订单
		dc.add(Restrictions.eq("enumConstByFlagOrderIsValid", enumConstByFlagOrderIsValid));// 有效订单
		dc.add(Restrictions.eq("enumConstByFlagRefundState", enumConstByFlagRefundState));// 退费中订单
		dc.add(Restrictions.lt("autoCheckTimes", 100));// 自动确认100次以下订单，用户每次点击网银支付或有6次自动确认的机会

		try {
			List<PeBzzOrderInfo> peOrderList = this.getGeneralService().getList(dc);
			//
			for (PeBzzOrderInfo po : peOrderList) {
				// 退款ID
				String refundId = po.getRefundId();
				// 订单号
				String orderId = po.getSeq();
				DetachedCriteria reDc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
				reDc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState");
				reDc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
				reDc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentMethod", "enumConstByFlagPaymentMethod");
				reDc.createCriteria("peBzzOrderInfo.ssoUser", "ssoUser");
				reDc.add(Restrictions.eq("peBzzOrderInfo.seq", orderId));
				PeBzzRefundInfo peBzzRefundInfo = (PeBzzRefundInfo) this.getGeneralService().getList(reDc).get(0);
				if (refundId != null && !"".equals(refundId)) {
					String re = this.getGeneralService().confirm99BillRefund(po.getSeq(), refundId);
					if (re != null && !"".equals(re) && !"退费成功".equals(re)) {
						System.out.println("订单：" + orderId + "--" + re);
					} else {
						// 修改退费信息状态
						EnumConst ec2 = this.getEnumConstService().getByNamespaceCode("FlagRefundState", "1");
						peBzzRefundInfo.setEnumConstByFlagRefundState(ec2);

						// 修改订单退费状态
						EnumConst ec3 = this.getEnumConstService().getByNamespaceCode("FlagOrderState", "0");
						po.setEnumConstByFlagOrderState(ec3);
						po.setEnumConstByFlagRefundState(ec2);

						EnumConst paymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "2");
						po.setEnumConstByFlagPaymentState(paymentState);

						po.setRefundDate(new Date());
						System.out.println("订单：" + orderId + "自动退费成功!");
					}
				} else {
					AutoRefund autoRefund = null;
					// 退款失败输出错误，后续退款继续执行。
					try {
						autoRefund = this.getGeneralService().refundOnline99BillApply(orderId, "定时任务，自动执行", peBzzRefundInfo.getReason(),
								po.getAmount());
					} catch (EntityException eex) {
						System.out.println("退款失败！！--" + eex.getMessage());
					}
					if (autoRefund != null) {
						String status = autoRefund.getStatus();// 返回状态
						String refundId_rec = autoRefund.getRefundId();// 退款流水号，用于查询退款状态
						if (refundId_rec != null || !"".equals(refundId_rec)) {
							// 退款申请成功 申请成功||存在申请记录
							if ("y".equalsIgnoreCase(status)) {
								// 执行判断并操作平台内退款
								String re = this.getGeneralService().confirm99BillRefund(orderId, refundId_rec);
								if (re != null && !"".equals(re) && !"退费成功".equals(re)) {
									System.out.println("订单：" + orderId + "--" + re);
								} else {
									// 修改退费信息状态
									EnumConst ec2 = this.getEnumConstService().getByNamespaceCode("FlagRefundState", "1");
									peBzzRefundInfo.setEnumConstByFlagRefundState(ec2);

									// 修改订单退费状态
									EnumConst ec3 = this.getEnumConstService().getByNamespaceCode("FlagOrderState", "0");
									po.setEnumConstByFlagOrderState(ec3);
									po.setEnumConstByFlagRefundState(ec2);

									EnumConst paymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "2");
									po.setEnumConstByFlagPaymentState(paymentState);

									po.setRefundDate(new Date());
									System.out.println("订单：" + orderId + "自动退费成功!");
								}
							} else {// 退款申请失败
								System.out.println("订单：" + orderId + "退费,等待平台确认。!");
							}
						} else {
							System.out.println("订单：" + orderId + "退费,平台无返回,等待平台确认。!");
						}
					}
				}
				po.setAutoCheckTimes(po.getAutoCheckTimes() + 1);
				this.getGeneralService().save(peBzzRefundInfo);
				this.getGeneralService().save(po);
			}
			System.out.println("订单退款任务结束！！");
		} catch (EntityException e) {
			System.out.println("订单退款失败！！--" + e.getMessage());
		}
		return null;
	}
}
