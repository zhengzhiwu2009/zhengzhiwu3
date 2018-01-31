package com.whaty.platform.entity.service.basic;

import com.whaty.platform.entity.bean.PeBzzRefundInfo;
import com.whaty.platform.entity.exception.EntityException;

public interface RefoundManageService {

//	/**
//	 * 预付费支付退费操作
//	 * @param peBzzRefundInfo
//	 */
//	public void deleteEleCourse(PeBzzRefundInfo peBzzRefundInfo) ;
	/**
	 * 拒绝退费操作
	 * @param peBzzRefundInfo
	 */
	public void refuseRefundApply(PeBzzRefundInfo peBzzRefundInfo);
//	/**
//	 * 在线支付退款
//	 * @param peBzzRefundInfo
//	 */
//	public boolean saveOnlineRefund(PeBzzRefundInfo peBzzRefundInfo);
	
//	/**
//	 * 备份退费会删除的数据
//	 * @param peBzzRefundInfo
//	 */
//	public void backupRefundData(PeBzzRefundInfo peBzzRefundInfo) throws EntityException;
	 
}
