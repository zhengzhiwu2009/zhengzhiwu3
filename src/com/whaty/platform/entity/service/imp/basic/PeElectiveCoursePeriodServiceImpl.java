package com.whaty.platform.entity.service.imp.basic;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeElectiveCoursePeriod;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeElectiveCoursePeriodService;

public class PeElectiveCoursePeriodServiceImpl implements PeElectiveCoursePeriodService{

	private GeneralDao generalDao;
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
	/**
	 * 此方法用于选课期支付，网银支付前更新与支付相关的信息
	 */
	@Override
	public List updateElectiveList(List electivelist,
			PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo,
			PeElectiveCoursePeriod peElectiveCoursePeriod, SsoUser user) throws EntityException {
		List<PrBzzTchStuElectiveBack> eleBackList = new ArrayList<PrBzzTchStuElectiveBack>();
		try {
			if (peBzzInvoiceInfo != null) {
				peBzzOrderInfo = this.generalDao.SavePeBzzOrderInfo(peBzzOrderInfo);
				peBzzInvoiceInfo = this.generalDao.SavePeBzzInvoiceInfo(peBzzInvoiceInfo);
			} else if(peBzzOrderInfo != null){
				peBzzOrderInfo = this.generalDao.SavePeBzzOrderInfo(peBzzOrderInfo);
			}
			
			PrBzzTchStuElective prBzzTchStuElective;
			for (int i = 0; i < electivelist.size(); i++) {
				prBzzTchStuElective = (PrBzzTchStuElective)electivelist.get(i);
				//prBzzTchStuElective.setPeBzzOrderInfo(peBzzOrderInfo);
				prBzzTchStuElective = (PrBzzTchStuElective)this.generalDao.save(prBzzTchStuElective);
				electivelist.set(i, prBzzTchStuElective);
			}
			if(peElectiveCoursePeriod != null){
				 this.generalDao.save(peElectiveCoursePeriod);
			}
			if(user != null) {
				this.generalDao.save(user);
			}
			
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
//		throw new EntityException();
		return electivelist;
	}

	
	
	

}
