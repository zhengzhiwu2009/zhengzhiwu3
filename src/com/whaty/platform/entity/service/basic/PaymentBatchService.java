package com.whaty.platform.entity.service.basic;

import java.util.List;

import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.exception.EntityException;

public interface PaymentBatchService {

	/**
	 * 此方法用来保存专项支付,网银支付,集体支付
	 */
	public void saveElecitvedCourse(PeBzzBatch peBzzBatch);
	/**
	 * 用于支付前为保存订单及选课记录座准备
	 * @param electiveList
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 */
	public void saveElectiveCourseAndPebzzOrderInfo(List<PrBzzTchStuElectiveBack> electiveList, PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, SsoUser user, List<StudentBatch> sbList) throws EntityException;
	
	public boolean checkDatabase();
}
