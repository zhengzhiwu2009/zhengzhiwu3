package com.whaty.platform.entity.service.imp.basic;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PaymentBatchService;

/**
 * 用于集体专项支付
 * @author Administrator
 *
 */
public class PaymentBatchServiceImpl implements PaymentBatchService{

	private GeneralDao generalDao;

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
	
	
	/**
	 * 此方法用来保存专项支付,网银支付,集体支付
	 */
	public void saveElecitvedCourse(PeBzzBatch peBzzBatch) {
		/**
		 *第三方支付成功后，更新选课状态和订单状态 
		 */
		EnumConst enumConstByFlagPaymentState = this.generalDao.getEnumConstByNamespaceCode("FlagPaymentState", "1");  //网银支付，订单状态改为已支付状态
		EnumConst enumConstByFlagElectivePayStatus = this.generalDao.getEnumConstByNamespaceCode("FlagElectivePayStatus", "1");  //网银支付，选课状态改为已支付状态
		/**
		 * 用专项来查找专项中的选课信息
		 */
		DetachedCriteria dcElective = DetachedCriteria.forClass(PrBzzTchStuElective.class);
		dcElective.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dcElective.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
		dcElective.add(Restrictions.eq("prBzzTchOpencourse.peBzzBatch", peBzzBatch));
		List<PrBzzTchStuElective> electiveList = null;
		electiveList = this.generalDao.getDetachList(dcElective);
		/**
		 * 更新选课状态
		 */
		PrBzzTchStuElective prBzzTchStuElective;
		for (int i = 0; i < electiveList.size(); i++) {
			prBzzTchStuElective = electiveList.get(i);
			PeBzzOrderInfo bzzOrderInfo = prBzzTchStuElective.getPeBzzOrderInfo();  //获取与选课关联的订单
			bzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);  //改变订单状态
			prBzzTchStuElective.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
			prBzzTchStuElective.setPeBzzOrderInfo(bzzOrderInfo);
			this.generalDao.save(prBzzTchStuElective);
		}
		
	}
	
	/**
	 * 用于支付前为保存订单及选课记录做准备
	 * @param electiveList
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 */
	@Override
	public void saveElectiveCourseAndPebzzOrderInfo(
			List<PrBzzTchStuElectiveBack> electiveBackList,
			PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo,
			SsoUser user,
			List<StudentBatch> sbList) throws EntityException{
		
		if(peBzzOrderInfo != null) {
			peBzzOrderInfo.setSsoUser(user);
			peBzzOrderInfo = (PeBzzOrderInfo)this.generalDao.save(peBzzOrderInfo);
		}
		if (user != null) {
			this.generalDao.detachedSave(user);
		}
		if(electiveBackList != null) {
			for(PrBzzTchStuElectiveBack prBzzTchStuElectiveBack : electiveBackList) {
				prBzzTchStuElectiveBack.setPeBzzOrderInfo(peBzzOrderInfo);
				this.generalDao.save(prBzzTchStuElectiveBack);
			}
		}
		if(peBzzInvoiceInfo != null) {
			this.generalDao.save(peBzzInvoiceInfo);
		}
		if(sbList != null) {
			for(StudentBatch sb : sbList) {
				sb.setPeBzzOrderInfo(peBzzOrderInfo);
				this.generalDao.save(sb);
			}
		}
	}

	@Override
	public boolean checkDatabase() {
		try {
			int i = this.getGeneralDao().executeBySQL("select 1 from dual");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
