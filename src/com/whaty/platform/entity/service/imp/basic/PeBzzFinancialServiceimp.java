package com.whaty.platform.entity.service.imp.basic;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.training.basic.StudyProgress;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzRefundInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.dao.EnumConstDAO;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PeBzzFinancialService;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.util.Const;
import com.whaty.util.RandomString;

public class PeBzzFinancialServiceimp implements PeBzzFinancialService {

	private GeneralDao generalDao;
	private EnumConstDAO enumConstDao;

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public EnumConstDAO getEnumConstDao() {
		return enumConstDao;
	}

	public void setEnumConstDao(EnumConstDAO enumConstDao) {
		this.enumConstDao = enumConstDao;
	}

	@Override
	public PeBzzOrderInfo updatePeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo)
			throws EntityException {
		// TODO Auto-generated method stub
		try {
			EnumConst enumConstByFlagPaymentState = this.getEnumConstDao()
					.getByNamespaceCode("FlagPaymentState", "1");
			peBzzOrderInfo
					.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
			if ("1".equalsIgnoreCase(peBzzOrderInfo
					.getEnumConstByFlagOrderType().getCode())) {// 购买学时订单
				SsoUser user = peBzzOrderInfo.getSsoUser();
				user.setAmount(peBzzOrderInfo.getClassHour());
				this.getGeneralDao().saveSsoUser(user);
			} else if ("0".equalsIgnoreCase(peBzzOrderInfo
					.getEnumConstByFlagOrderType().getCode())) {// 选课订单
				DetachedCriteria dc = DetachedCriteria
						.forClass(PrBzzTchStuElective.class);
				dc.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));
				List<PrBzzTchStuElective> electiveList = this.getGeneralDao()
						.getList(dc);
				EnumConst enumConstByFlagElectivePayStatus = this
						.getEnumConstDao().getByNamespaceCode(
								"FlagElectivePayStatus", "1");
				for (PrBzzTchStuElective prBzzTchStuElective : electiveList) {
					prBzzTchStuElective
							.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
					this.getGeneralDao().save(prBzzTchStuElective);
				}
			}
			peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralDao().save(
					peBzzOrderInfo);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return peBzzOrderInfo;
	}

//	public PeBzzOrderInfo updatePeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo,
//			String flag) throws EntityException {
//		// TODO Auto-generated method stub
//		EnumConst enumConstByFlagPaymentState = null;
//		EnumConst enumConstByFlagElectivePayStatus = null;
//		EnumConst enumConstByFlagBatchPayState = null;
//		Double amount = 0.0;
//		try {
//			if ("confirm".equalsIgnoreCase(flag)) {// 确认到账
//				enumConstByFlagPaymentState = this.getEnumConstDao()
//						.getByNamespaceCode("FlagPaymentState", "1");
//				enumConstByFlagElectivePayStatus = this.getEnumConstDao()
//						.getByNamespaceCode("FlagElectivePayStatus", "1");
//				enumConstByFlagBatchPayState = this.getGeneralDao()
//						.getEnumConstByNamespaceCode("FlagBatchPayState", "1");
//				if (peBzzOrderInfo.getClassHour() != null) {
//					amount = Double.parseDouble(peBzzOrderInfo.getClassHour());
//				}
//
//				peBzzOrderInfo.setPaymentDate(new Date());
//			} else if ("cancel".equalsIgnoreCase(flag)) {// 取消到账
//				enumConstByFlagPaymentState = this.getEnumConstDao()
//						.getByNamespaceCode("FlagPaymentState", "0");
//				enumConstByFlagElectivePayStatus = this.getEnumConstDao()
//						.getByNamespaceCode("FlagElectivePayStatus", "0");
//				enumConstByFlagBatchPayState = this.getGeneralDao()
//						.getEnumConstByNamespaceCode("FlagBatchPayState", "0");
//				if (peBzzOrderInfo.getClassHour() != null) {
//					amount = -Double.parseDouble(peBzzOrderInfo.getClassHour());
//				}
//			}
//			peBzzOrderInfo
//					.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
//			if ("1".equalsIgnoreCase(peBzzOrderInfo
//					.getEnumConstByFlagOrderType().getCode())) {// 购买学时订单
//				SsoUser user = peBzzOrderInfo.getSsoUser();
//				double zcount = user.getSumAmount() == null ? 0.0 : Double
//						.parseDouble(user.getSumAmount());
//				user.setSumAmount(String.valueOf(zcount + amount));
//				this.getGeneralDao().saveSsoUser(user);
//			} else if ("0".equalsIgnoreCase(peBzzOrderInfo
//					.getEnumConstByFlagOrderType().getCode())) {// 选课订单
//				/**
//				 * 判断是否是网银支付
//				 */
//				EnumConst enumConstByFlagPaymentMethod = peBzzOrderInfo
//						.getEnumConstByFlagPaymentMethod();
//				if (enumConstByFlagPaymentMethod.getCode().equals("0")) { // 网银支付
//					/**
//					 * 未到账
//					 */
//					DetachedCriteria electiveBack = DetachedCriteria
//							.forClass(PrBzzTchStuElectiveBack.class);
//					electiveBack.createCriteria("prBzzTchOpencourse");
//					electiveBack.createCriteria("peBzzStudent");
//					electiveBack.add(Restrictions.eq("peBzzOrderInfo",
//							peBzzOrderInfo));
//					List<PrBzzTchStuElectiveBack> backList = this
//							.getGeneralDao().getList(electiveBack);
//					List<PrBzzTchStuElective> eleList = new ArrayList<PrBzzTchStuElective>();
//					for (PrBzzTchStuElectiveBack back : backList) {
//
//						TrainingCourseStudent trainingCourseStudent = new TrainingCourseStudent();
//						trainingCourseStudent.setPrBzzTchOpencourse(back
//								.getPrBzzTchOpencourse());
//						trainingCourseStudent.setSsoUser(back.getPeBzzStudent()
//								.getSsoUser());
//						trainingCourseStudent.setPercent(0.00);
//						trainingCourseStudent
//								.setLearnStatus(StudyProgress.UNCOMPLETE);
//
//						PrBzzTchStuElective elective = new PrBzzTchStuElective();
//						elective.setElectiveDate(new Date());
//						elective
//								.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
//						elective.setPeBzzOrderInfo(peBzzOrderInfo);
//						elective.setPeBzzStudent(back.getPeBzzStudent());
//						elective.setPrBzzTchOpencourse(back
//								.getPrBzzTchOpencourse());
//						elective.setSsoUser(peBzzOrderInfo.getSsoUser());
//
//						elective
//								.setTrainingCourseStudent(trainingCourseStudent);
//						
//						this.generalDao.save(elective);
//						eleList.add(elective);
//
//					}
//				} else {
//
//					DetachedCriteria dc = DetachedCriteria
//							.forClass(PrBzzTchStuElective.class);
//					dc.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));
//					List<PrBzzTchStuElective> electiveList = this
//							.getGeneralDao().getList(dc);
//					for (PrBzzTchStuElective prBzzTchStuElective : electiveList) {
//						prBzzTchStuElective
//								.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
//						this.getGeneralDao().save(prBzzTchStuElective);
//					}
//				}
//			} else if ("2".equalsIgnoreCase(peBzzOrderInfo
//					.getEnumConstByFlagOrderType().getCode())) {// 专项支付订单
//
//				DetachedCriteria dc = DetachedCriteria
//						.forClass(PrBzzTchStuElective.class);
//				dc.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));
//				List<PrBzzTchStuElective> electiveList = this.getGeneralDao()
//						.getList(dc);
//				for (PrBzzTchStuElective prBzzTchStuElective : electiveList) {
//					prBzzTchStuElective
//							.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
//					this.getGeneralDao().save(prBzzTchStuElective);
//				}
//
//				DetachedCriteria dcStuBatch = DetachedCriteria
//						.forClass(StudentBatch.class);
//				dcStuBatch.add(Restrictions
//						.eq("peBzzOrderInfo", peBzzOrderInfo));
//				List<StudentBatch> stubBatchList = this.getGeneralDao()
//						.getList(dcStuBatch);
//				for (StudentBatch studentBatch : stubBatchList) {
//					studentBatch
//							.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);
//					this.getGeneralDao().save(studentBatch);
//				}
//			}
//			peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralDao().save(
//					peBzzOrderInfo);
//		} catch (RuntimeException e) {
//			throw new EntityException(e);
//		}
//		return peBzzOrderInfo;
//	}

	public PeBzzInvoiceInfo savePeBzzInvoiceInfo(
			PeBzzInvoiceInfo peBzzInvoiceInfo) throws EntityException {
		try {
			this.getGeneralDao().save(peBzzInvoiceInfo.getPeBzzOrderInfo());
			peBzzInvoiceInfo = (PeBzzInvoiceInfo) this.getGeneralDao().save(
					peBzzInvoiceInfo);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return peBzzInvoiceInfo;
	}

	@Override
	public PeBzzRefundInfo saveRefund(PeBzzRefundInfo peBzzRefundInfo,
			List electiveIdList, SsoUser ssoUser) throws EntityException {
		try {
			if (electiveIdList != null && electiveIdList.size() > 0) {
				this.generalDao.deleteByIds(PrBzzTchStuElective.class,
						electiveIdList);
			}
			if (ssoUser != null) {
				this.getGeneralDao().saveSsoUser(ssoUser);
			}
			this.generalDao.detachedSave(peBzzRefundInfo.getPeBzzOrderInfo());
			this.generalDao.detachedSave(peBzzRefundInfo);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return peBzzRefundInfo;
	}

	@Override
	public PeBzzOrderInfo savePeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo,
			PeBzzInvoiceInfo peBzzInvoiceInfo) throws EntityException {
		if (peBzzOrderInfo != null) {
			peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralDao().save(
					peBzzOrderInfo);
			if (peBzzOrderInfo.getSsoUser() != null) {
				this.getGeneralDao().save(peBzzOrderInfo.getSsoUser());
			}
		}

		if (peBzzInvoiceInfo != null) {
			peBzzInvoiceInfo.setPeBzzOrderInfo(peBzzOrderInfo);
			this.getGeneralDao().save(peBzzInvoiceInfo);
		}
		return null;
	}

}
