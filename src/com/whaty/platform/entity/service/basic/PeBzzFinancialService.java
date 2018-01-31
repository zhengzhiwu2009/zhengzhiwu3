package com.whaty.platform.entity.service.basic;


import java.util.List;

import com.whaty.platform.entity.bean.PeBzzAssess;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzRefundInfo;
import com.whaty.platform.entity.bean.PeBzzSuggestion;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;

public interface PeBzzFinancialService {

	/**
	 * 订单确认状态修改
	 * @param peBzzOrderInfo
	 * @return
	 * @throws EntityException
	 */
	public 	PeBzzOrderInfo updatePeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo)throws EntityException;
//	/**
//	 * 订单确认状态修改
//	 * @param peBzzOrderInfo
//	 * @param flag "confirm",确认到账,"cancel",取消到账
//	 * @return
//	 * @throws EntityException
//	 */(已经迁移至generalService)
//	public 	PeBzzOrderInfo updatePeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo,String flag)throws EntityException;
//	
	/**
	 * 发票状态修改
	 * @param peBzzInvoiceInfo
	 * @return
	 * @throws EntityException
	 */
	public PeBzzInvoiceInfo savePeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) throws EntityException;
	
	/**
	 * 保存订单，购买学时使用
	 * @param peBzzInvoiceInfo
	 * @return
	 * @throws EntityException
	 */
	public PeBzzOrderInfo savePeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo,PeBzzInvoiceInfo peBzzInvoiceInfo) throws EntityException;
	
	/**
	 * 退费确认
	 * @param peBzzRefundInfo
	 * @param electiveIdList 要删除的选课id
	 * @param ssoUser 当订单为预付费支付时的退款账户
	 * @return
	 * @throws EntityException
	 */
	public PeBzzRefundInfo saveRefund(PeBzzRefundInfo peBzzRefundInfo,List electiveIdList,SsoUser ssoUser) throws EntityException;
}
