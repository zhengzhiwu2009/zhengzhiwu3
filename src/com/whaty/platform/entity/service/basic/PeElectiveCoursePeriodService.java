package com.whaty.platform.entity.service.basic;

import java.util.List;

import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeElectiveCoursePeriod;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;

/**
 * 集体支付选课期
 * @author Administrator
 *
 */
public interface PeElectiveCoursePeriodService {
	/**
	 * 此方法用于选课期支付时，将选课表中的信息更新
	 * @param electivelist  
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 * @param ssoUser
	 * @param listSb
	 * @return
	 * @throws EntityException
	 */
	public List updateElectiveList(List electivelist,PeBzzOrderInfo peBzzOrderInfo,PeBzzInvoiceInfo peBzzInvoiceInfo, PeElectiveCoursePeriod peElectiveCoursePeriod, SsoUser user) throws EntityException;

}
