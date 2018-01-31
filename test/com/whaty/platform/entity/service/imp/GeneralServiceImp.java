package com.whaty.platform.entity.service.imp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.bill99.seashell.domain.dto.gatewayquery.GatewayOrderDetail;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.GlobalProperties;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.AssignRecord;
import com.whaty.platform.entity.bean.AutoRefund;
import com.whaty.platform.entity.bean.CourseInfo;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.IndustryTeacherInfo;
import com.whaty.platform.entity.bean.OnlineOrderInfo;
import com.whaty.platform.entity.bean.OnlineRefundInfo;
import com.whaty.platform.entity.bean.PeBalanceHistory;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzCertificate;
import com.whaty.platform.entity.bean.PeBzzEditStudent;
import com.whaty.platform.entity.bean.PeBzzExamBatch;
import com.whaty.platform.entity.bean.PeBzzExamBatchSite;
import com.whaty.platform.entity.bean.PeBzzExamLate;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzExamSite;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzRecruitBefore;
import com.whaty.platform.entity.bean.PeBzzRefundInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeElectiveCoursePeriod;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSignUpOrder;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBackHistory;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveHistory;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.bean.TrainingCourseStudentHistory;
import com.whaty.platform.entity.bean.WeHistory;
import com.whaty.platform.entity.bean.WeQA;
import com.whaty.platform.entity.bean.WeRollcall;
import com.whaty.platform.entity.bean.WeRollcallUser;
import com.whaty.platform.entity.bean.WeSurveyQuestion;
import com.whaty.platform.entity.bean.WeSurveyResponse;
import com.whaty.platform.entity.bean.WeSurveySurveylist;
import com.whaty.platform.entity.bean.WeVoteOption;
import com.whaty.platform.entity.bean.WeVoteQuestion;
import com.whaty.platform.entity.bean.WeVoteResult;
import com.whaty.platform.entity.bean.WeVoteVotelist;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.dao.EnumConstDAO;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.util.Payment99BillUtil;
import com.whaty.platform.entity.util.PaymentUtil;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.training.basic.StudyProgress;
import com.whaty.util.SpringUtil;

public class GeneralServiceImp<T extends AbstractBean> implements GeneralService<T>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final Log logger = LogFactory.getLog("EntityLogger");
	private GeneralDao<T> generalDao;
	private EnumConstDAO enumConstDao;

	public EnumConstDAO getEnumConstDao() {
		return enumConstDao;
	}

	public void setEnumConstDao(EnumConstDAO enumConstDao) {
		this.enumConstDao = enumConstDao;
	}

	public GeneralDao<T> getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao<T> generalDao) {
		this.generalDao = generalDao;
	}

	public void delete(T persistentInstance) throws EntityException {
		try {
			this.generalDao.delete(persistentInstance);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}

	}

	public int deleteByIds(List ids) throws EntityException {
		int i = 0;
		try {
			i = this.generalDao.deleteByIds(ids);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return i;
	}

	public int executeByHQL(String hql) throws EntityException {
		int i = 0;
		try {
			i = this.generalDao.executeByHQL(hql);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return i;
	}

	public int executeBySQL(String sql) throws EntityException {
		int i = 0;
		try {
			i = this.generalDao.executeBySQL(sql);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return i;
	}

	public List getByExample(T instance) throws EntityException {
		List list = null;
		try {
			list = this.generalDao.getByExample(instance);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return list;
	}

	public List getByHQL(String hql) throws EntityException {
		List list = null;
		try {
			list = this.generalDao.getByHQL(hql);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return list;
	}

	public T getById(String id) throws EntityException {
		T instance = null;
		try {
			instance = this.generalDao.getById(id);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return instance;
	}

	public Page getByPage(DetachedCriteria detachedCriteria, int pageSize, int startIndex) throws EntityException {
		Page page = null;
		try {
			page = this.generalDao.getByPage(detachedCriteria, pageSize, startIndex);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return page;
	}

	public Page getByPageSQL(String sql, int pageSize, int startIndex) throws EntityException {
		Page page = null;
		try {
			page = this.generalDao.getByPageSQL(sql, pageSize, startIndex);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return page;
	}

	public List getBySQL(String sql) throws EntityException {
		List list = null;
		try {
			list = this.generalDao.getBySQL(sql);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return list;
	}

	public List getList(DetachedCriteria detachedCriteria) throws EntityException {
		List list = null;
		try {
			list = this.generalDao.getList(detachedCriteria);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return list;
	}

	public T save(T transientInstance) throws EntityException {
		T instance = null;
		try {
			instance = this.generalDao.save(transientInstance);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return instance;
	}

	public Object save(Object transientInstance) throws EntityException {
		Object instance = null;
		try {
			instance = this.generalDao.save(transientInstance.getClass().cast(transientInstance));
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return instance;
	}

	public PeBzzExamBatchSite savePeBzzExamBatchSite(PeBzzExamBatchSite transientInstance) throws EntityException {
		PeBzzExamBatchSite instance = null;
		try {
			instance = this.generalDao.savePeBzzExamBatchSite(transientInstance);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return instance;
	}

	public List saveList(List list) throws EntityException {
		for (int i = 0; i < list.size(); i++) {
			list.set(i, save((T) list.get(i)));
		}
		return list;
	}

	public int updateColumnByIds(List ids, String column, String value) throws EntityException {
		int i = 0;
		try {
			i = this.generalDao.updateColumnByIds(ids, column, value);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return i;
	}

	public void saveError() throws EntityException {
		throw new EntityException();
	}

	public PeBulletin getPeBulletin(String id) throws EntityException {

		return this.getGeneralDao().getPeBulletin(id);
	}

	public PeInfoNews getPeInfoNews(String id) throws EntityException {
		// TODO Auto-generated method stub
		return this.getGeneralDao().getPeInfoNews(id);
	}

	public List getNewBulletins(String id) {

		return this.getGeneralDao().getNewBulletins(id);
	}

	public PeEnterprise getSubEnterprise(String id) {
		return this.getGeneralDao().getSubEnterprise(id);
	}

	public List getStuBulletins() {

		return this.getGeneralDao().getStuBulletins();
	}

	public List getExamSite() {

		return this.getGeneralDao().getExamSite();
	}

	public PeBzzStudent getStudentInfo(DetachedCriteria studc) {
		return this.getGeneralDao().getStudentInfo(studc);
	}

	public PeBzzExamBatch getExamBatch(DetachedCriteria studc) {
		return this.getGeneralDao().getExamBatch(studc);
	}

	public PeBzzExamScore getExamScore(DetachedCriteria studc) {
		return this.getGeneralDao().getExamScore(studc);
	}

	public IndustryTeacherInfo getTeacherInfo(DetachedCriteria studc) {
		return this.getGeneralDao().getTeacherInfo(studc);
	}

	public CourseInfo getCourseInfo(DetachedCriteria studc) {
		return this.getGeneralDao().getCourseInfo(studc);
	}

	public PeBzzTchCourse getPeBzzTchCourse(DetachedCriteria studc) {
		return this.getGeneralDao().getPeBzzTchCourse(studc);
	}

	public PeBzzCertificate getCertificate(DetachedCriteria studc) {
		return this.getGeneralDao().getCertificate(studc);
	}

	public PeBzzEditStudent getEditStudent(DetachedCriteria studc) {
		return this.getGeneralDao().getEditStudent(studc);
	}

	public PeBzzRecruitBefore getPeBzzRecruitBefore(DetachedCriteria studc) {
		return this.getGeneralDao().getPeBzzRecruitBefore(studc);
	}

	public PeBzzExamLate getExamLate(DetachedCriteria studc) {
		return this.getGeneralDao().getExamLate(studc);
	}

	public PeBzzExamSite getExamSite(DetachedCriteria studc) {
		return this.getGeneralDao().getExamSite(studc);
	}

	public PeBzzExamBatchSite getExamBatchSite(DetachedCriteria studc) {
		return this.getGeneralDao().getExamBatchSite(studc);
	}

	public List getExamBatchSites(DetachedCriteria studc) {

		return this.getGeneralDao().getExamBatchSites(studc);
	}

	public List getExamScores(DetachedCriteria studc) {

		return this.getGeneralDao().getExamScores(studc);
	}

	public void update(T persistentInstance) {
		this.generalDao.update(persistentInstance);
	}

	public void updateSsoUser(SsoUser ssoUser) {
		this.generalDao.updateSsoUser(ssoUser);
	}

	public void updatePeEnterpriseManager(PeEnterpriseManager enterpriseManager) {
		this.generalDao.updatePeEnterpriseManager(enterpriseManager);
	}

	public void updatePeManager(PeManager peManager) {
		this.generalDao.updatePeManager(peManager);
	}

	public void updateEnterpriseManager(PeEnterpriseManager enterpriseManager) {
		this.generalDao.updateEnterpriseManager(enterpriseManager);
	}

	public List getDetachList(DetachedCriteria detachedCriteria) throws EntityException {
		return this.getGeneralDao().getDetachList(detachedCriteria);
	}

	// @Override
	public int deleteByIds(Class clazz, List siteIds, List ids) throws EntityException {
		// TODO Auto-generated method stub
		int i = 0;
		try {
			if (hasMethod(clazz, "getPeSite")) {
				i = this.generalDao.deleteByIds(clazz, siteIds, ids);
			} else {
				i = this.generalDao.deleteByIds(clazz, ids);
			}
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return i;
	}

	private boolean hasMethod(Class clazz, String methodName) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(methodName))
			return false;
		Method[] methods = clazz.getDeclaredMethods();
		for (Method m : methods) {
			if (methodName.equals(m.getName())) {
				return true;
			}
		}
		return false;
	}

	public T getById(Class entityClass, List siteIds, String id) throws EntityException {
		T instance = null;
		try {
			instance = this.generalDao.getById(entityClass, siteIds, id);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return instance;
	}

	public T getById(Class entityClass, String id) throws EntityException {
		T instance = null;
		try {
			instance = this.generalDao.getById(entityClass, id);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return instance;
	}

	public String getOrderSeq() throws EntityException {
		String seq = "";
		try {
			// 订单号生成规则
			String orderFormat = GlobalProperties.getProperty("sys.order.order_format");
			String dfStr = "0000000000";// 默认生成规则
			if (orderFormat != null && orderFormat.length() > 0) {// 如果有配置规则的话，使用配置的规则
				dfStr = orderFormat;
			}
			DecimalFormat df = new DecimalFormat(dfStr);

			// 读取订单号序列
			List temList = new ArrayList();
			temList = this.generalDao.getBySQL("SELECT S_PE_BZZ_ORDER_SEQ.NEXTVAL FROM DUAL");

			seq = df.format(temList.get(0)).toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("获取订单号失败：" + e.getMessage());
		}
		return seq;
	}

	public List saveElectiveList(List electivelist, PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, SsoUser ssoUser)
			throws EntityException {
		return saveElectiveList(electivelist, peBzzOrderInfo, peBzzInvoiceInfo, ssoUser, null);
	}

	public List saveElectiveList(List electivelist, PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, SsoUser ssoUser,
			PeElectiveCoursePeriod peElectiveCoursePeriod) throws EntityException {
		try {
			PrBzzTchStuElective prBzzTchStuElective;
			for (int i = 0; i < electivelist.size(); i++) {
				prBzzTchStuElective = (PrBzzTchStuElective) electivelist.get(i);
				this.save((T) prBzzTchStuElective.getTrainingCourseStudent());
				electivelist.set(i, save((T) prBzzTchStuElective));
			}

			if (peElectiveCoursePeriod != null) {
				this.save((T) peElectiveCoursePeriod);
			}

			if (peBzzInvoiceInfo != null) {
				peBzzOrderInfo = this.generalDao.SavePeBzzOrderInfo(peBzzOrderInfo);
				peBzzInvoiceInfo = this.generalDao.SavePeBzzInvoiceInfo(peBzzInvoiceInfo);
			} else if (peBzzOrderInfo != null) {
				peBzzOrderInfo = this.generalDao.SavePeBzzOrderInfo(peBzzOrderInfo);
			}
			if (ssoUser != null) {
				this.generalDao.detachedSave((T) ssoUser);
			}
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return electivelist;
	}

	public List saveElectiveListBatch(List electivelist, PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, SsoUser ssoUser,
			List<StudentBatch> listSb) throws EntityException {
		try {
			if (peBzzInvoiceInfo != null) {
				peBzzOrderInfo = this.generalDao.SavePeBzzOrderInfo(peBzzOrderInfo);
				peBzzInvoiceInfo = this.generalDao.SavePeBzzInvoiceInfo(peBzzInvoiceInfo);
			} else if (peBzzOrderInfo != null) {
				peBzzOrderInfo = this.generalDao.SavePeBzzOrderInfo(peBzzOrderInfo);
			}
			if (ssoUser != null) {
				this.generalDao.detachedSave((T) ssoUser);
			}
			PrBzzTchStuElective prBzzTchStuElective;
			for (int i = 0; i < electivelist.size(); i++) {
				prBzzTchStuElective = (PrBzzTchStuElective) electivelist.get(i);
				prBzzTchStuElective.setPeBzzOrderInfo(peBzzOrderInfo);
				this.save((T) prBzzTchStuElective.getTrainingCourseStudent());
				electivelist.set(i, save((T) prBzzTchStuElective));
			}
			if (listSb != null) {
				for (StudentBatch sb : listSb) {
					sb.setPeBzzOrderInfo(peBzzOrderInfo);
					this.generalDao.save((T) sb);
				}
			}

		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return electivelist;
	}

	/**
	 * 网银支付时，首先产生的订单先存放在备份表中
	 * 
	 * @param electiveBacklist
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 * @param ssoUser
	 * @param peElectiveCoursePeriod
	 * @return
	 */
	public List<PrBzzTchStuElectiveBack> saveElectiveBackList(List<PrBzzTchStuElectiveBack> electiveBacklist,
			PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, SsoUser ssoUser,
			PeElectiveCoursePeriod peElectiveCoursePeriod, List<StudentBatch> sbList) throws EntityException {
		List<PrBzzTchStuElectiveBack> eleBackList = new ArrayList<PrBzzTchStuElectiveBack>();
		try {
			if (peBzzOrderInfo != null) {
				// 保存账户信息
				if (ssoUser != null) {
					this.generalDao.detachedSave((T) ssoUser);
					peBzzOrderInfo.setSsoUser(ssoUser);
				}
				// 保存选课期
				if (peElectiveCoursePeriod != null) {
					this.generalDao.detachedSave(peElectiveCoursePeriod);
					peBzzOrderInfo.setPeElectiveCoursePeriod(peElectiveCoursePeriod);
				}

				// 保存发票
				if (peBzzInvoiceInfo != null) {
					peBzzOrderInfo = this.generalDao.SavePeBzzOrderInfo(peBzzOrderInfo);
					EnumConst enumConstByFlagFpOpenState = this.enumConstDao.getByNamespaceCode("FlagFpOpenState", "0");
					peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);// 待开
					peBzzInvoiceInfo.setPeBzzOrderInfo(peBzzOrderInfo);
					peBzzInvoiceInfo = this.generalDao.SavePeBzzInvoiceInfo(peBzzInvoiceInfo);
				} else {
					// 保存订单
					peBzzOrderInfo = this.generalDao.SavePeBzzOrderInfo(peBzzOrderInfo);
				}

				// 保存back和订单关联
				if (electiveBacklist != null && electiveBacklist.size() > 0) {
					for (int i = 0; i < electiveBacklist.size(); i++) {
						PrBzzTchStuElectiveBack prBzzTchStuElectiveBack = electiveBacklist.get(i);
						prBzzTchStuElectiveBack.setPeBzzOrderInfo(peBzzOrderInfo);
						this.generalDao.save(prBzzTchStuElectiveBack);
						// System.out.println(i);
						electiveBacklist.set(i, prBzzTchStuElectiveBack);
					}
				}

				// 保存专项信息
				if (sbList != null) {
					for (StudentBatch sb : sbList) {
						sb.setPeBzzOrderInfo(peBzzOrderInfo);
						EnumConst enumConstByFlagBatchPayState = this.getGeneralDao().getEnumConstByNamespaceCode("FlagBatchPayState", "2");// 已支付，待确认
						sb.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);
						this.generalDao.save(sb);
					}
				}

			} else {
				throw new EntityException("订单错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException(e);
		}
		return eleBackList;
	}

	@Override
	public PeEnterpriseManager getEnterpriseManagerByLoginId(String loginId) throws EntityException {
		return this.getGeneralDao().getEnterprisemanager(loginId);
	}

	@Override
	public PeBzzBatch getDefaultPeBzzBatch() throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzBatch.class);
		dc.createAlias("enumConstByFlagBatchType", "enumConstByFlagBatchType");
		dc.add(Restrictions.eq("enumConstByFlagBatchType.namespace", "FlagBatchType"));
		dc.add(Restrictions.eq("enumConstByFlagBatchType.code", "1"));
		PeBzzBatch peBzzBatch = this.getGeneralDao().getPeBzzBatch(dc);
		return peBzzBatch;
	}

	@Override
	public void deleteElective(PrBzzTchStuElective prBzzTchStuElective) throws EntityException {
		// TODO Auto-generated method stub
		int i = 0;
		try {
			TrainingCourseStudent trainingCourseStudent = prBzzTchStuElective.getTrainingCourseStudent();
			if (trainingCourseStudent != null) {
				this.getGeneralDao().delete((T) trainingCourseStudent);
			}
			this.getGeneralDao().delete((T) prBzzTchStuElective);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
	}

	public Page getByPageSQLDBPool(String sqlNum, String sqlList, int pageSize, int startIndex) throws EntityException {
		Page page = null;
		try {
			page = this.generalDao.getByPageSQLDBPool(sqlNum, sqlList, pageSize, startIndex);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return page;
	}

	public String getSignUpOrderSeq() throws EntityException {
		String seq = "";
		try {
			List temList = new ArrayList();
			temList = this.generalDao.getBySQL("SELECT S_PE_SIGNUP_ORDER_SEQ.NEXTVAL FROM DUAL");
			DecimalFormat df = new DecimalFormat("00000000");
			seq = df.format(temList.get(0)).toString();
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return seq;
	}

	public PeBzzOrderInfo updatePeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo, String flag, SsoUser ssoUser) throws EntityException {

		EnumConst enumConstByFlagPaymentState = null;
		EnumConst enumConstByFlagElectivePayStatus = null;
		EnumConst enumConstByFlagBatchPayState = null;
		EnumConst enumConstByFlagElePeriodPayStatus = null;
		Double amount = 0.00;

		Double classHour = 0.00;

		try {
			// 保存确认订单人

			String operator = "";
			if ("autoCheck".equalsIgnoreCase(flag)) {// 主动查询方式
				operator = "自动三方确认";
				flag = "confirm";
				peBzzOrderInfo.setAutoCheckTimes(peBzzOrderInfo.getAutoCheckTimes() + 1);
			} else {// 被动接口通知触发
				UserSession us = (UserSession) ServletActionContext.getRequest().getSession()
						.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
				if (us == null) {
					operator = "通知接口确认";
				} else {
					operator = us.getLoginId();
				}
			}
			peBzzOrderInfo.setOperater(operator);
			peBzzOrderInfo.setOperateDate(new Date());
			peBzzOrderInfo.setOperateType(flag);

			if ("confirm".equalsIgnoreCase(flag)) {// 确认到账
				enumConstByFlagPaymentState = this.getEnumConstDao().getByNamespaceCode("FlagPaymentState", "1");// 到账情况-已到账
				enumConstByFlagElectivePayStatus = this.getEnumConstDao().getByNamespaceCode("FlagElectivePayStatus", "1");// 选课支付状态-已支付
				enumConstByFlagBatchPayState = this.getGeneralDao().getEnumConstByNamespaceCode("FlagBatchPayState", "1");// 专项培训支付情况-已支付
				enumConstByFlagElePeriodPayStatus = this.getGeneralDao().getEnumConstByNamespaceCode("FlagElePeriodPayStatus", "1");// 选课期支付状态-已支付

				if (peBzzOrderInfo.getAmount() != null) {
					amount = Double.parseDouble(peBzzOrderInfo.getAmount());
				}
				if (peBzzOrderInfo.getClassHour() != null) {
					classHour = Double.parseDouble(peBzzOrderInfo.getClassHour());
				}

				if (!"0".equals(peBzzOrderInfo.getEnumConstByFlagPaymentMethod().getCode())) {// 线下支付订单，在确认到账同时完成对账。
					EnumConst enumConstByFlagCheckState = this.getGeneralDao().getEnumConstByNamespaceCode("FlagCheckState", "1");// 已对账
					peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState);
					peBzzOrderInfo.setCheckNote("线下支付，到账同时完成对账");
					peBzzOrderInfo.setCheckDate(new Date());
				}
				peBzzOrderInfo.setPaymentDate(new Date());
			} else if ("cancel".equalsIgnoreCase(flag)) {// 取消到账
				enumConstByFlagPaymentState = this.getEnumConstDao().getByNamespaceCode("FlagPaymentState", "0");
				enumConstByFlagElectivePayStatus = this.getEnumConstDao().getByNamespaceCode("FlagElectivePayStatus", "0");
				enumConstByFlagBatchPayState = this.getGeneralDao().getEnumConstByNamespaceCode("FlagBatchPayState", "0");
				enumConstByFlagElePeriodPayStatus = this.getGeneralDao().getEnumConstByNamespaceCode("FlagElePeriodPayStatus", "0");
				if (peBzzOrderInfo.getAmount() != null) {
					amount = -Double.parseDouble(peBzzOrderInfo.getAmount());
				}
				if (peBzzOrderInfo.getClassHour() != null) {
					classHour = Double.parseDouble(peBzzOrderInfo.getClassHour());
				}
				peBzzOrderInfo.setPaymentDate(null);
			}
			Date now = new Date();
			String nowS = String.valueOf(now.getTime());
			if ("1".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 购买学时订单
				if (amount < 0) {// 预付费支付，消费
					// 记录余额变动
					this.saveBalanceHistory(peBzzOrderInfo.getSsoUser(), peBzzOrderInfo, classHour, amount, "购买学时取消订单", nowS);
				} else if (amount > 0) {// 预付费支付，缴费
					// 记录余额变动
					this.saveBalanceHistory(peBzzOrderInfo.getSsoUser(), peBzzOrderInfo, classHour, amount, "购买学时", nowS);
				}
			} else if ("1".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagPaymentMethod().getCode())) {
				if (amount < 0) {// 预付费支付，消费
					// 记录余额变动
					this.saveBalanceHistory(peBzzOrderInfo.getSsoUser(), peBzzOrderInfo, classHour, amount, "预付费账户取消到账", nowS);
				} else if (amount > 0) {// 预付费支付，缴费
					// 记录余额变动
					this.saveBalanceHistory(peBzzOrderInfo.getSsoUser(), peBzzOrderInfo, classHour, amount, "预付费账户消费", nowS);
				}
			}

			peBzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);

			if ("1".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 购买学时订单
				SsoUser user = peBzzOrderInfo.getSsoUser();
				if (user == null) {
					throw new EntityException("账户信息获取失败");
				}
				double zcount = user.getSumAmount() == null ? 0.00 : Double.parseDouble(user.getSumAmount());
				double usedAmount = user.getAmount() == null ? 0.00 : Double.parseDouble(user.getAmount());
				user.setSumAmount(String.valueOf(zcount + classHour));
				/**
				 * start 新增用户余额表 2016-09-06
				 */
				String userSql = "insert into sso_user_balance (id ,fk_user_id ,operator ,create_date ,amount ,sum_amount) values (sys_guid(),"
						+ "'" + user.getId() + "','auto', sysdate,'" + usedAmount + "','" + String.valueOf(zcount + classHour) + "')";
				int i = this.executeBySQL(userSql);
				if (i != i) {
					throw new EntityException("账户余额变动失败");
				}

				/**
				 * end
				 */
				// this.getGeneralDao().saveSsoUser(user);
				this.getGeneralDao().detachedSave(user);// 更新user金额情况
				String fk_user_id = peBzzOrderInfo.getSsoUser().getId();
				String orderId = peBzzOrderInfo.getId();
				String operate_amount = classHour.toString();

				BigDecimal zcount1 = new BigDecimal(user.getSumAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal usedAmount1 = new BigDecimal(user.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
				// String account_amount = String.valueOf(zcount - usedAmount +
				// amount);
				BigDecimal account_amount = zcount1.subtract(usedAmount1);
				String sqlSearch = "select * from assign_record where fk_order_id = '" + orderId + "'";
				List list = getBySQL(sqlSearch);
				if (list.size() == 0) {
					SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String assignDate = sdFormat.format(new Date());
					String sql = " insert into assign_record             " + "   (id,                                "
							+ "    fk_user_id,                        " + "    assign_style,                      "
							+ "    assign_date,                       " + "    flag_record_assign_method,         "
							+ "    flag_operate_type,                 " + "    operate_amount,                    "
							+ "    account_amount,                    " + "    fk_order_id)                       "
							+ " select seq_assign_record.nextval, " + "'" + fk_user_id + "','0'," + "TO_DATE('" + assignDate
							+ "','yyyy-MM-dd hh24:mi:ss'),''," + "'operate0'," + "'" + operate_amount + "','" + account_amount.toString()
							+ "','" + orderId + "' from dual ";
					executeBySQL(sql);
				}

			} else if ("2".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 专项支付订单

				// 确认选课记录
				this.changeElective(peBzzOrderInfo, enumConstByFlagElectivePayStatus);

				// 确认专项支付中学员专项关系
				this.changeStuBatch(peBzzOrderInfo, enumConstByFlagBatchPayState);
			} else if ("3".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 选课期选课选课

				// 确认选课记录
				this.changeElective(peBzzOrderInfo, enumConstByFlagElectivePayStatus);

				// 确认选课期支付
				this.changeCoursePeriod(peBzzOrderInfo, enumConstByFlagElePeriodPayStatus);
			} else if ("5".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 报名单选课

				// 确认选课记录
				this.changeElective(peBzzOrderInfo, enumConstByFlagElectivePayStatus);

				// 确认报名单支付
				this.changeSignupOrder(peBzzOrderInfo, enumConstByFlagElectivePayStatus);

				// } else if ("6".equalsIgnoreCase(peBzzOrderInfo
				// .getEnumConstByFlagOrderType().getCode())) {// 未通过课程继续购买
				// this.transferIncompleteRecord(peBzzOrderInfo,
				// enumConstByFlagElectivePayStatus);

			} else if ("7".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 视频直播订单

				// 确认选课记录
				this.changeElective(peBzzOrderInfo, enumConstByFlagElectivePayStatus);

				// 确认专项支付中学员专项关系
				this.changeStuBatch(peBzzOrderInfo, enumConstByFlagBatchPayState);
			} else {// 选课订单（学生选课，直接选课）
				EnumConst enumConstByFlagPaymentMethod = peBzzOrderInfo.getEnumConstByFlagPaymentMethod();
				// 确认选课记录
				this.changeElective(peBzzOrderInfo, enumConstByFlagElectivePayStatus);
			}
			peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralDao().save(peBzzOrderInfo);

			// 账户余额改变保存用户
			if (ssoUser != null) {
				if (Double.parseDouble(ssoUser.getBalance()) < 0) {
					throw new EntityException("余额不足，请检查账户信息并充值！");
				}
				this.getGeneralDao().detachedSave(ssoUser);
			}
		} catch (Exception e) {
			throw new EntityException(e);
		}
		return peBzzOrderInfo;
	}

	/**
	 * 上边方法重载
	 * 
	 * @param peBzzOrderInfo
	 *            订单Bean
	 * @param flag
	 *            confirm:确认到账 cancel:取消到账
	 * @param ssoUser
	 *            user
	 * @param paymentMethod
	 *            支付类型(企业/个人)
	 * @return
	 * @author //TODO YCL 2016-03-15 到账方法重载：增加支付方式类型
	 * @throws EntityException
	 */
	public PeBzzOrderInfo updatePeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo, String flag, SsoUser ssoUser, String paymentMethod)
			throws EntityException {

		EnumConst enumConstByFlagPaymentState = null;
		EnumConst enumConstByFlagElectivePayStatus = null;
		EnumConst enumConstByFlagBatchPayState = null;
		EnumConst enumConstByFlagElePeriodPayStatus = null;
		Double amount = 0.00;

		Double classHour = 0.00;

		try {
			// 设置支付方式(企业、个人)
			if (null != paymentMethod && !"".equals(paymentMethod)) {
				peBzzOrderInfo.setPaymentMethod(paymentMethod);
			}

			// 保存确认订单人
			String operator = "";
			if ("autoCheck".equalsIgnoreCase(flag)) {// 主动查询方式
				operator = "自动三方确认";
				flag = "confirm";
				peBzzOrderInfo.setAutoCheckTimes(peBzzOrderInfo.getAutoCheckTimes() + 1);
			} else {// 被动接口通知触发
				UserSession us = (UserSession) ServletActionContext.getRequest().getSession()
						.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
				if (us == null) {
					operator = "通知接口确认";
				} else {
					operator = us.getLoginId();
				}
			}
			peBzzOrderInfo.setOperater(operator);
			peBzzOrderInfo.setOperateDate(new Date());
			peBzzOrderInfo.setOperateType(flag);

			if ("confirm".equalsIgnoreCase(flag)) {// 确认到账
				enumConstByFlagPaymentState = this.getEnumConstDao().getByNamespaceCode("FlagPaymentState", "1");// 到账情况-已到账
				enumConstByFlagElectivePayStatus = this.getEnumConstDao().getByNamespaceCode("FlagElectivePayStatus", "1");// 选课支付状态-已支付
				enumConstByFlagBatchPayState = this.getGeneralDao().getEnumConstByNamespaceCode("FlagBatchPayState", "1");// 专项培训支付情况-已支付
				enumConstByFlagElePeriodPayStatus = this.getGeneralDao().getEnumConstByNamespaceCode("FlagElePeriodPayStatus", "1");// 选课期支付状态-已支付

				if (peBzzOrderInfo.getAmount() != null) {
					amount = Double.parseDouble(peBzzOrderInfo.getAmount());
				}
				if (peBzzOrderInfo.getClassHour() != null) {
					classHour = Double.parseDouble(peBzzOrderInfo.getClassHour());
				}

				if (!"0".equals(peBzzOrderInfo.getEnumConstByFlagPaymentMethod().getCode())) {// 线下支付订单，在确认到账同时完成对账。
					EnumConst enumConstByFlagCheckState = this.getGeneralDao().getEnumConstByNamespaceCode("FlagCheckState", "1");// 已对账
					peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState);
					peBzzOrderInfo.setCheckNote("线下支付，到账同时完成对账");
					peBzzOrderInfo.setCheckDate(new Date());
				}
				peBzzOrderInfo.setPaymentDate(new Date());
			} else if ("cancel".equalsIgnoreCase(flag)) {// 取消到账
				enumConstByFlagPaymentState = this.getEnumConstDao().getByNamespaceCode("FlagPaymentState", "0");
				enumConstByFlagElectivePayStatus = this.getEnumConstDao().getByNamespaceCode("FlagElectivePayStatus", "0");
				enumConstByFlagBatchPayState = this.getGeneralDao().getEnumConstByNamespaceCode("FlagBatchPayState", "0");
				enumConstByFlagElePeriodPayStatus = this.getGeneralDao().getEnumConstByNamespaceCode("FlagElePeriodPayStatus", "0");
				if (peBzzOrderInfo.getAmount() != null) {
					amount = -Double.parseDouble(peBzzOrderInfo.getAmount());
				}
				if (peBzzOrderInfo.getClassHour() != null) {
					classHour = Double.parseDouble(peBzzOrderInfo.getClassHour());
				}
				peBzzOrderInfo.setPaymentDate(null);
			}
			Date now = new Date();
			String nowS = String.valueOf(now.getTime());
			if ("1".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 购买学时订单
				if (amount < 0) {// 预付费支付，消费
					// 记录余额变动
					this.saveBalanceHistory(peBzzOrderInfo.getSsoUser(), peBzzOrderInfo, classHour, amount, "购买学时取消订单", nowS);
				} else if (amount > 0) {// 预付费支付，缴费
					// 记录余额变动
					this.saveBalanceHistory(peBzzOrderInfo.getSsoUser(), peBzzOrderInfo, classHour, amount, "购买学时", nowS);
				}
			} else if ("1".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagPaymentMethod().getCode())) {
				if (amount < 0) {// 预付费支付，消费
					// 记录余额变动
					this.saveBalanceHistory(peBzzOrderInfo.getSsoUser(), peBzzOrderInfo, classHour, amount, "预付费账户取消到账", nowS);
				} else if (amount > 0) {// 预付费支付，缴费
					// 记录余额变动
					this.saveBalanceHistory(peBzzOrderInfo.getSsoUser(), peBzzOrderInfo, classHour, amount, "预付费账户消费", nowS);
				}
			}

			peBzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);

			if ("1".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 购买学时订单
				SsoUser user = peBzzOrderInfo.getSsoUser();
				double zcount = user.getSumAmount() == null ? 0.00 : Double.parseDouble(user.getSumAmount());
				double usedAmount = user.getAmount() == null ? 0.00 : Double.parseDouble(user.getAmount());
				user.setSumAmount(String.valueOf(zcount + classHour));
				// this.getGeneralDao().saveSsoUser(user);
				this.getGeneralDao().detachedSave(user);// 更新user金额情况
				this.getGeneralDao().save(user);
				String fk_user_id = peBzzOrderInfo.getSsoUser().getId();
				String orderId = peBzzOrderInfo.getId();
				String operate_amount = classHour.toString();

				BigDecimal zcount1 = new BigDecimal(user.getSumAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal usedAmount1 = new BigDecimal(user.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
				// String account_amount = String.valueOf(zcount - usedAmount +
				// amount);
				BigDecimal account_amount = zcount1.subtract(usedAmount1);
				String sqlSearch = "select * from assign_record where fk_order_id = '" + orderId + "'";
				List list = getBySQL(sqlSearch);
				if (list.size() == 0) {
					SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String assignDate = sdFormat.format(new Date());
					String sql = " insert into assign_record             " + "   (id,                                "
							+ "    fk_user_id,                        " + "    assign_style,                      "
							+ "    assign_date,                       " + "    flag_record_assign_method,         "
							+ "    flag_operate_type,                 " + "    operate_amount,                    "
							+ "    account_amount,                    " + "    fk_order_id)                       "
							+ " select seq_assign_record.nextval, " + "'" + fk_user_id + "','0'," + "TO_DATE('" + assignDate
							+ "','yyyy-MM-dd hh24:mi:ss'),''," + "'operate0'," + "'" + operate_amount + "','" + account_amount.toString()
							+ "','" + orderId + "' from dual ";
					executeBySQL(sql);
				}

			} else if ("2".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 专项支付订单

				// 确认选课记录
				this.changeElective(peBzzOrderInfo, enumConstByFlagElectivePayStatus);

				// 确认专项支付中学员专项关系
				this.changeStuBatch(peBzzOrderInfo, enumConstByFlagBatchPayState);
			} else if ("3".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 选课期选课选课

				// 确认选课记录
				this.changeElective(peBzzOrderInfo, enumConstByFlagElectivePayStatus);

				// 确认选课期支付
				this.changeCoursePeriod(peBzzOrderInfo, enumConstByFlagElePeriodPayStatus);
			} else if ("5".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 报名单选课

				// 确认选课记录
				this.changeElective(peBzzOrderInfo, enumConstByFlagElectivePayStatus);

				// 确认报名单支付
				this.changeSignupOrder(peBzzOrderInfo, enumConstByFlagElectivePayStatus);

				// } else if ("6".equalsIgnoreCase(peBzzOrderInfo
				// .getEnumConstByFlagOrderType().getCode())) {// 未通过课程继续购买
				// this.transferIncompleteRecord(peBzzOrderInfo,
				// enumConstByFlagElectivePayStatus);

			} else if ("7".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 视频直播订单

				// 确认选课记录
				this.changeElective(peBzzOrderInfo, enumConstByFlagElectivePayStatus);

				// 确认专项支付中学员专项关系
				this.changeStuBatch(peBzzOrderInfo, enumConstByFlagBatchPayState);
			} else {// 选课订单（学生选课，直接选课）
				EnumConst enumConstByFlagPaymentMethod = peBzzOrderInfo.getEnumConstByFlagPaymentMethod();
				// 确认选课记录
				this.changeElective(peBzzOrderInfo, enumConstByFlagElectivePayStatus);
			}
			peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralDao().save(peBzzOrderInfo);

			// 账户余额改变保存用户
			if (ssoUser != null) {
				if (Double.parseDouble(ssoUser.getBalance()) < 0) {
					throw new EntityException("余额不足，请检查账户信息并充值！");
				}
				this.getGeneralDao().detachedSave(ssoUser);
			}
		} catch (Exception e) {
			throw new EntityException(e);
		}
		return peBzzOrderInfo;
	}

	/**
	 * 修改报名单状态，确认到账使用
	 * 
	 * @param peBzzOrderInfo
	 * @param enumConstByFlagElectivePayStatus
	 */
	private void changeSignupOrder(PeBzzOrderInfo peBzzOrderInfo, EnumConst enumConstByFlagElectivePayStatus) {
		PeSignUpOrder peSignUpOrder = peBzzOrderInfo.getPeSignUpOrder();
		peSignUpOrder.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
		this.getGeneralDao().save(peSignUpOrder);
	}

	/**
	 * 修改选课期状态，确认到账使用
	 * 
	 * @param peBzzOrderInfo
	 * @param enumConstByFlagElePeriodPayStatus
	 */
	private void changeCoursePeriod(PeBzzOrderInfo peBzzOrderInfo, EnumConst enumConstByFlagElePeriodPayStatus) {
		DetachedCriteria dcStuBatch = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
		dcStuBatch.add(Restrictions.eq("id", peBzzOrderInfo.getPeElectiveCoursePeriod().getId()));

		List<PeElectiveCoursePeriod> peElectiveCoursePeriodList = this.getGeneralDao().getList(dcStuBatch);

		for (PeElectiveCoursePeriod peElectiveCoursePeriod : peElectiveCoursePeriodList) {
			peElectiveCoursePeriod.setEnumConstByFlagElePeriodPayStatus(enumConstByFlagElePeriodPayStatus);
			this.getGeneralDao().save(peElectiveCoursePeriod);
		}
	}

	/**
	 * 修改专项状态，确认到账使用
	 * 
	 * @param peBzzOrderInfo
	 * @param enumConstByFlagBatchPayState
	 */
	private void changeStuBatch(PeBzzOrderInfo peBzzOrderInfo, EnumConst enumConstByFlagBatchPayState) {

		DetachedCriteria dcStuBatch = DetachedCriteria.forClass(StudentBatch.class);
		dcStuBatch.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));

		List<StudentBatch> stubBatchList = this.getGeneralDao().getList(dcStuBatch);

		for (StudentBatch studentBatch : stubBatchList) {
			studentBatch.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);
			this.getGeneralDao().save(studentBatch);
		}
	}

	/**
	 * 修改选课记录，确认到账使用
	 * 
	 * @param peBzzOrderInfo
	 * @param enumConstByFlagElectivePayStatus
	 */
	private void changeElective(PeBzzOrderInfo peBzzOrderInfo, EnumConst enumConstByFlagElectivePayStatus) throws EntityException {
		try {
			DetachedCriteria electiveBack = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
			electiveBack.createCriteria("prBzzTchOpencourse");
			electiveBack.createCriteria("peBzzStudent", "peBzzStudent").createCriteria("ssoUser", "ssoUser");
			electiveBack.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));

			List<PrBzzTchStuElectiveBack> backList = this.getGeneralDao().getList(electiveBack);

			List<PrBzzTchStuElective> eleList = new ArrayList<PrBzzTchStuElective>();
			if (enumConstByFlagElectivePayStatus != null && "1".equals(enumConstByFlagElectivePayStatus.getCode())) {// 确认到账，添加选课
				for (PrBzzTchStuElectiveBack back : backList) {
					DetachedCriteria studc = DetachedCriteria.forClass(PeBzzStudent.class);
					studc.createCriteria("ssoUser");
					studc.add(Restrictions.eq("id", back.getPeBzzStudent().getId()));

					List<PeBzzStudent> stuList = this.getGeneralDao().getList(studc);
					PeBzzStudent peBzzStudent = (PeBzzStudent) stuList.get(0);

					TrainingCourseStudent trainingCourseStudent = new TrainingCourseStudent();
					trainingCourseStudent.setPrBzzTchOpencourse(back.getPrBzzTchOpencourse());
					trainingCourseStudent.setSsoUser(peBzzStudent.getSsoUser());
					trainingCourseStudent.setPercent(0.00);
					trainingCourseStudent.setLearnStatus(StudyProgress.UNCOMPLETE);

					// trainingCourseStudent = (TrainingCourseStudent)
					// this.generalDao.save(trainingCourseStudent);

					PrBzzTchStuElective elective = new PrBzzTchStuElective();
					elective.setElectiveDate(new Date());
					elective.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
					elective.setPeBzzOrderInfo(peBzzOrderInfo);
					elective.setPeElectiveCoursePeriod(peBzzOrderInfo.getPeElectiveCoursePeriod());
					elective.setPeBzzStudent(back.getPeBzzStudent());
					elective.setPrBzzTchOpencourse(back.getPrBzzTchOpencourse());
					elective.setSsoUser(peBzzOrderInfo.getSsoUser());

					elective.setTrainingCourseStudent(trainingCourseStudent);

					this.generalDao.save(elective);
					eleList.add(elective);
				}
			} else {// 取消到账，删除选课
				DetachedCriteria electiveDC = DetachedCriteria.forClass(PrBzzTchStuElective.class);
				electiveDC.createCriteria("prBzzTchOpencourse");
				electiveDC.createCriteria("peBzzStudent");
				electiveDC.createCriteria("trainingCourseStudent");
				electiveDC.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));
				List<PrBzzTchStuElective> electiveList = this.getGeneralDao().getList(electiveDC);
				for (PrBzzTchStuElective elective : electiveList) {
					try {
						this.deleteElective(elective);
					} catch (EntityException e) {
						e.printStackTrace();

					}
				}
			}
		} catch (Exception e) {
			throw new EntityException("购买后续处理失败");
		}
	}

	@Override
	public List<PrBzzTchStuElectiveBack> saveElectiveBackListBatch(List<PrBzzTchStuElectiveBack> electiveBackList,
			PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, SsoUser user, List<StudentBatch> listSb)
			throws EntityException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 此方法用于选课期支付，网银支付前更新与支付相关的信息
	 */
	@Override
	public List<PrBzzTchStuElectiveBack> updateElectiveBackList(List<PrBzzTchStuElectiveBack> electiveBacklist,
			PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, PeElectiveCoursePeriod peElectiveCoursePeriod, SsoUser user)
			throws EntityException {
		List<PrBzzTchStuElectiveBack> eleBackList = new ArrayList<PrBzzTchStuElectiveBack>();
		try {
			if (peBzzInvoiceInfo != null) {
				peBzzOrderInfo = this.generalDao.SavePeBzzOrderInfo(peBzzOrderInfo);
				peBzzInvoiceInfo = this.generalDao.SavePeBzzInvoiceInfo(peBzzInvoiceInfo);
			} else if (peBzzOrderInfo != null) {
				peBzzOrderInfo = this.generalDao.SavePeBzzOrderInfo(peBzzOrderInfo);
			}

			PrBzzTchStuElectiveBack prBzzTchStuElectiveBack;
			for (int i = 0; i < electiveBacklist.size(); i++) {
				prBzzTchStuElectiveBack = (PrBzzTchStuElectiveBack) electiveBacklist.get(i);
				prBzzTchStuElectiveBack.setPeBzzOrderInfo(peBzzOrderInfo);
				prBzzTchStuElectiveBack = (PrBzzTchStuElectiveBack) this.generalDao.save(prBzzTchStuElectiveBack);
				electiveBacklist.set(i, prBzzTchStuElectiveBack);
			}
			if (peElectiveCoursePeriod != null) {
				this.generalDao.save(peElectiveCoursePeriod);
			}
			if (user != null) {
				this.generalDao.save(user);
			}

		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		// throw new EntityException();
		return electiveBacklist;
	}

	/**
	 * 获得托管状态的SsoUser
	 */
	@Override
	public SsoUser initSsoUser(UserSession us) throws EntityException {
		SsoUser ssoUser = us.getSsoUser();
		DetachedCriteria dcSsouser = DetachedCriteria.forClass(SsoUser.class);
		dcSsouser.add(Restrictions.eq("id", us.getSsoUser().getId()));

		try {
			ssoUser = (SsoUser) this.getDetachList(dcSsouser).get(0);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		return ssoUser;
	}

	/**
	 * 20130108 学生未通过课程再支付，删除历史课程，迁移至备份表(撤销订单和退费可调用)
	 * 
	 * @param eleList
	 * @throws EntityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public void transferIncompleteRecord(String studentId, String openCourseId) throws EntityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
		dc.createCriteria("ssoUser");
		dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
		dc.createCriteria("prBzzTchOpencourse.peBzzTchCourse", "peBzzTchCourse");
		dc.createCriteria("peBzzStudent");
		dc.createCriteria("enumConstByFlagElectivePayStatus");
		dc.createCriteria("peBzzOrderInfo", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("trainingCourseStudent");
		dc.add(Restrictions.eq("peBzzStudent.id", studentId));
		dc.add(Restrictions.eq("prBzzTchOpencourse.id", openCourseId));

		DetachedCriteria backDc = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
		backDc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
		backDc.add(Restrictions.eq("peBzzStudent.id", studentId));
		backDc.add(Restrictions.eq("prBzzTchOpencourse.id", openCourseId));

		List<PrBzzTchStuElective> eleList = this.generalDao.getDetachList(dc);
		List<PrBzzTchStuElectiveBack> eleBackList = this.generalDao.getDetachList(backDc);

		this.transferElectiveHistory(eleList, eleBackList);
	}

	/**
	 * 将选课信息备份到备份表并删除选课信息
	 * 
	 * @author linjie
	 * @param eleList
	 * @param eleBackList
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected void transferElectiveHistory(List<PrBzzTchStuElective> eleList, List<PrBzzTchStuElectiveBack> eleBackList)
			throws IllegalAccessException, InvocationTargetException {
		if (eleList != null) {
			for (PrBzzTchStuElective p : eleList) {
				// PrBzzTchStuElective p = (PrBzzTchStuElective) eleList.get(0);
				PrBzzTchStuElectiveHistory ph = new PrBzzTchStuElectiveHistory();
				// BeanUtils.copyProperties(ph, p);//迁移到备份表,右向左迁移

				ph.setElectiveDate(p.getElectiveDate());
				ph.setEnumConstByFlagElectivePayStatus(p.getEnumConstByFlagElectivePayStatus());
				ph.setEnumConstByFlagScorePub(p.getEnumConstByFlagScorePub());
				ph.setEnumConstByFlagScoreStatus(p.getEnumConstByFlagScoreStatus());
				ph.setExamTimes(p.getExamTimes());
				ph.setOnlineTime(p.getOnlineTime());
				ph.setPeBzzOrderInfo(p.getPeBzzOrderInfo());
				ph.setPeBzzStudent(p.getPeBzzStudent());
				ph.setPeElectiveCoursePeriod(p.getPeElectiveCoursePeriod());
				ph.setPrBzzTchOpencourse(p.getPrBzzTchOpencourse());
				ph.setScoreExam(p.getScoreExam());
				ph.setSsoUser(p.getSsoUser());

				TrainingCourseStudent tcs = p.getTrainingCourseStudent();

				String eleSql = "delete from pr_bzz_tch_stu_elective ele where ele.id = '" + p.getId() + "'";

				String updateScormStuCourse = "delete from scorm_stu_course ssc\n" + " where ssc.student_id =\n"
						+ "       (select su.id from sso_user su where su.login_id = '" + p.getPeBzzStudent().getRegNo() + "')\n"
						+ "   and ssc.course_id = '" + p.getPrBzzTchOpencourse().getPeBzzTchCourse().getId() + "'";

				String updateScormStuSco = "delete from scorm_stu_sco sss\n" + "  where sss.student_id = \n"
						+ " 	(select su.id from sso_user su " + "			where su.login_id = '" + p.getPeBzzStudent().getRegNo() + "' \n"
						+ "		)\n" + "  and sss.course_id = '" + p.getPrBzzTchOpencourse().getPeBzzTchCourse().getId() + "'";
				// 2014年12月23日 Lee 备份学员重新购买课程的考试记录
				String bakTestHisSql = " INSERT INTO TEST_TESTPAPER_HISTORY_HIS (ID, USER_ID, TEST_DATE, TEST_RESULT, TESTPAPER_ID, SCORE, ISCHECK, T_USER_ID, MARK_DATE, TEST_OPEN_DATE)  SELECT ID, USER_ID, TEST_DATE, TO_LOB(TEST_RESULT), TESTPAPER_ID, SCORE, ISCHECK, T_USER_ID, MARK_DATE, TEST_OPEN_DATE FROM TEST_TESTPAPER_HISTORY TTH    WHERE TTH.T_USER_ID = '"
						+ p.getPeBzzStudent().getId()
						+ "' AND TTH.TESTPAPER_ID IN (SELECT TTI.ID FROM TEST_TESTPAPER_INFO TTI WHERE TTI.GROUP_ID = '"
						+ p.getPrBzzTchOpencourse().getPeBzzTchCourse().getId() + "') ";
				/**
				 * 20130114添加，删除学员考试结果记录
				 */
				String updateTestRecord = "delete from test_testpaper_history tth\n" + " where tth.t_user_id = '"
						+ p.getPeBzzStudent().getId() + "'\n" + "   and tth.testpaper_id in\n" + "       (select tti.id\n"
						+ "          from test_testpaper_info tti\n" + "         where tti.group_id = '"
						+ p.getPrBzzTchOpencourse().getPeBzzTchCourse().getId() + "')";
				/**
				 * 20130114添加，删除学员课程满意度调查问卷记录
				 */
				String updateVoteRecord = "delete from pr_vote_record pvr\n" + " where pvr.fk_vote_paper_id in\n"
						+ "       (select id from pe_vote_paper pvp where pvp.courseid = '"
						+ p.getPrBzzTchOpencourse().getPeBzzTchCourse().getId() + "')\n"
						+ "   and pvr.ssoid = (select id from sso_user su where su.login_id = '" + p.getPeBzzStudent().getRegNo() + "')";

				String tcssql = "";
				if (tcs != null) {
					TrainingCourseStudentHistory th = new TrainingCourseStudentHistory();
					// BeanUtils.copyProperties(th,tcs);//迁移到备份表,右向左迁移
					th.setCompleteDate(tcs.getCompleteDate());
					th.setGetDate(tcs.getGetDate());
					th.setLearnStatus(tcs.getLearnStatus());
					th.setPercent(tcs.getPercent());
					th.setPrBzzTchOpencourse(tcs.getPrBzzTchOpencourse());
					th.setScore(tcs.getScore());
					th.setSsoUser(tcs.getSsoUser());
					th.setStatus(tcs.getStatus());

					// th = (TrainingCourseStudentHistory)
					// this.generalDao.clearSession(th);//清缓存后才能删除ID
					th.setId(null);
					this.generalDao.detachedSave(th);// 保存training历史

					tcssql = "delete from training_course_student tcs where tcs.id = '" + tcs.getId() + "'";

					ph.setTrainingCourseStudentHistory(th);
				}

				// ph = (PrBzzTchStuElectiveHistory)
				// this.generalDao.clearSession(ph);//清缓存后才能删除ID
				ph.setId(null);
				this.generalDao.detachedSave(ph);// 保存elective历史

				this.generalDao.executeBySQL(updateScormStuCourse);// 删除课件记录
				this.generalDao.executeBySQL(updateScormStuSco);// 删除课件记录
				this.generalDao.executeBySQL(bakTestHisSql); // 备份学员重新购买课程的考试记录
				this.generalDao.executeBySQL(updateTestRecord); // 删除学员考试记录
				this.generalDao.executeBySQL(updateVoteRecord); // 删除学员满意度调查问卷
				this.generalDao.executeBySQL(eleSql); // 删除选课
				this.generalDao.executeBySQL(tcssql);// 删除Training记录
			}
		}
		if (eleBackList != null) {
			for (PrBzzTchStuElectiveBack p : eleBackList) {
				// PrBzzTchStuElectiveBack p = (PrBzzTchStuElectiveBack)
				// eleBackList.get(0);
				PrBzzTchStuElectiveBackHistory pbh = new PrBzzTchStuElectiveBackHistory();

				// BeanUtils.copyProperties(pbh, p);//迁移到备份表,右向左迁移
				pbh.setPeBzzOrderInfo(p.getPeBzzOrderInfo());
				pbh.setPeBzzStudent(p.getPeBzzStudent());
				pbh.setPeElectiveCoursePeriod(p.getPeElectiveCoursePeriod());
				pbh.setPrBzzTchOpencourse(p.getPrBzzTchOpencourse());
				pbh.setSsoUser(p.getSsoUser());
				// 删除报名表记录
				String eleSql = "delete from pr_bzz_tch_stu_elective_back ele where ele.id = '" + p.getId() + "'";
				System.out.print("\n\n\n" + eleSql);
				// Lee 判断删除的课程是否是专项中的必选课程
				pbh.setId(null);
				this.generalDao.detachedSave(pbh);// 保存electiveback历史
				this.generalDao.executeBySQL(eleSql); // 删除选课
				/*
				 * pbh.setId(null); this.generalDao.detachedSave(pbh);//
				 * 保存electiveback历史 this.generalDao.executeBySQL(eleSql); //
				 * 删除选课 StringBuffer sbBiXuanSql = new StringBuffer();
				 * sbBiXuanSql .append("SELECT DISTINCT A.ID FROM
				 * PR_BZZ_TCH_OPENCOURSE A INNER JOIN PE_BZZ_BATCH B ON
				 * A.FK_BATCH_ID = B.ID INNER JOIN ENUM_CONST C ON A.FLAG_CHOOSE =
				 * C.ID INNER JOIN PR_BZZ_TCH_STU_ELECTIVE_BACK D ON A.ID =
				 * D.FK_TCH_OPENCOURSE_ID INNER JOIN ENUM_CONST E ON
				 * B.FLAG_BATCH_TYPE = E.ID WHERE C.CODE = '1' AND E.CODE = '0'
				 * AND D.ID = '" + p.getId() + "'"); List biXuanList =
				 * this.getGeneralDao().getBySQL(sbBiXuanSql.toString()); if
				 * (biXuanList == null || biXuanList.size() == 0) {// 专项的必选课程 //
				 * 不可以删除 // 否则影响专项报名 // pbh = (PrBzzTchStuElectiveBackHistory) //
				 * this.generalDao.clearSession(pbh);//清缓存后才能删除ID
				 * pbh.setId(null); this.generalDao.detachedSave(pbh);//
				 * 保存electiveback历史 this.generalDao.executeBySQL(eleSql); //
				 * 删除选课 }
				 */
			}
		}
	}

	public void copyElectiveBackHistory(List<PrBzzTchStuElectiveBack> eleBackList) throws EntityException {
		try {
			if (eleBackList != null) {
				for (PrBzzTchStuElectiveBack p : eleBackList) {
					// PrBzzTchStuElectiveBack p = (PrBzzTchStuElectiveBack)
					// eleBackList.get(0);
					PrBzzTchStuElectiveBackHistory pbh = new PrBzzTchStuElectiveBackHistory();

					pbh.setPeBzzOrderInfo(p.getPeBzzOrderInfo());
					pbh.setPeBzzStudent(p.getPeBzzStudent());
					pbh.setPeElectiveCoursePeriod(p.getPeElectiveCoursePeriod());
					pbh.setPrBzzTchOpencourse(p.getPrBzzTchOpencourse());
					pbh.setSsoUser(p.getSsoUser());

					// String eleSql = "delete from pr_bzz_tch_stu_elective_back
					// ele where ele.id = '" + p.getId() + "'";

					pbh.setId(null);
					this.generalDao.detachedSave(pbh);// 保存electiveback历史
					// this.generalDao.executeBySQL(eleSql); //删除选课
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("复制选课历史信息出错。");
		}
	}

	/**
	 * 检查electiveBacklist中的学生课程关系时候已经存在back记录，
	 * 
	 * @author linjie
	 * @return
	 */
	public String checkElective(PrBzzTchStuElectiveBack eleBack) {
		DetachedCriteria electiveBackDC = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
		electiveBackDC.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
		electiveBackDC.createAlias("peBzzStudent", "peBzzStudent");
		electiveBackDC.add(Restrictions.eq("peBzzStudent", eleBack.getPeBzzStudent()));
		electiveBackDC.add(Restrictions.or(Restrictions.eq("prBzzTchOpencourse", eleBack.getPrBzzTchOpencourse()), Restrictions.eq(
				"prBzzTchOpencourse.peBzzTchCourse", eleBack.getPrBzzTchOpencourse().getPeBzzTchCourse())));
		List eleBackList = this.getGeneralDao().getList(electiveBackDC);
		if (eleBackList != null && eleBackList.size() > 0) {
			PeBzzTchCourse course = eleBack.getPrBzzTchOpencourse().getPeBzzTchCourse();
			PeBzzStudent student = eleBack.getPeBzzStudent();
			return "学员" + student.getName() + "存在(" + course.getCode() + ")" + course.getName() + "的选课记录";
		} else {
			return null;
		}

		// 选课期
		// 专项
		// 集体报名
		// 个人选课

	}

	/**
	 * 通用退费方法
	 * 
	 * @author linjie
	 * @param peBzzRefundInfo
	 */
	public void executeRefund(PeBzzRefundInfo peBzzRefundInfo) throws EntityException {
		UserSession us = null;
		try {
			if (ServletActionContext.getRequest() != null && ServletActionContext.getRequest().getSession() != null) {
				us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			}
		} catch (Exception e) {

		}
		try {
			PeBzzOrderInfo peBzzOrderInfo = peBzzRefundInfo.getPeBzzOrderInfo();

			DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
			dc.createCriteria("ssoUser");
			dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
			dc.createCriteria("prBzzTchOpencourse.peBzzTchCourse", "peBzzTchCourse");
			dc.createCriteria("peBzzStudent");
			dc.createCriteria("enumConstByFlagElectivePayStatus");
			dc.createCriteria("peBzzOrderInfo", DetachedCriteria.LEFT_JOIN);
			dc.createCriteria("trainingCourseStudent");
			dc.add(Restrictions.eq("peBzzOrderInfo.id", peBzzOrderInfo.getId()));

			DetachedCriteria backDc = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
			backDc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
			backDc.createCriteria("peBzzOrderInfo", DetachedCriteria.LEFT_JOIN);
			backDc.add(Restrictions.eq("peBzzOrderInfo.id", peBzzOrderInfo.getId()));

			List<PrBzzTchStuElective> eleList = this.getGeneralDao().getList(dc);
			List<PrBzzTchStuElectiveBack> eleBackList = this.getGeneralDao().getList(backDc);

			// 将专项信息设置为未支付状态
			this.recoverStuBatch(peBzzOrderInfo);

			// 备份选课信息
			this.transferElectiveHistory(eleList, eleBackList);

			// 预付费支付余额变更
			if (peBzzOrderInfo.getEnumConstByFlagPaymentMethod().getCode().equals("1")) { // 预付费支付
				// EnumConst ec =
				// this.getGeneralDao().getEnumConstByNamespaceCode("ClassHourRate",
				// "0");
				SsoUser user = peBzzOrderInfo.getSsoUser();
				BigDecimal yue = new BigDecimal(user.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(
						new BigDecimal(peBzzOrderInfo.getClassHour()).setScale(2, BigDecimal.ROUND_HALF_UP));
				user.setAmount(yue.toString());
				this.getGeneralDao().detachedSave(user);
				// 预付费余额变更记录
				Date now = new Date();
				String nowS = String.valueOf(now.getTime());// 时间戳，标记同事操作的数据
				this.saveBalanceHistory(user, peBzzOrderInfo, Double.valueOf(yue.toString()), 0, "预付费支付退费", nowS);

				// 预付费退费结算金额修改为学时
				BigDecimal zhyAmountnew = new BigDecimal(user.getSumAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(
						new BigDecimal(user.getAmount())).setScale(2, BigDecimal.ROUND_HALF_UP);// 账户余额

				EnumConst enumConstByFlagOperateType = this.getGeneralDao().getEnumConstByNamespaceCode("FlagOperateType", "6"); // 退费

				AssignRecord assignRecord = new AssignRecord();
				assignRecord.setSsoUser(user);// 退费用户
				assignRecord.setAssignStyle("operate6");
				assignRecord.setAssignDate(new Date());// 操作日期
				assignRecord.setAccountAmount(zhyAmountnew.toString());// 账户余额
				assignRecord.setOperateAmount(peBzzOrderInfo.getClassHour());// 操作学时数
				// 退费金额修改为按学时结算
				assignRecord.setEnumConstByFlagOperateType(enumConstByFlagOperateType);
				assignRecord.setPeBzzOrderInfo(peBzzOrderInfo);
				this.getGeneralDao().save(assignRecord);
			}

			// 修改退费信息状态
			EnumConst ec2 = this.getEnumConstDao().getByNamespaceCode("FlagRefundState", "1");
			peBzzRefundInfo.setEnumConstByFlagRefundState(ec2);
			String logs = peBzzRefundInfo.getOperationLogs();
			if (logs == null) {
				logs = "";
			}
			if (us != null) {
				StringBuffer buf = new StringBuffer(logs);
				buf.append("|" + us.getLoginId() + "/" + us.getUserName() + "执行了退费终审确认操作\n");
				peBzzRefundInfo.setOperationLogs(buf.toString());
			} else {
				peBzzRefundInfo.setOperationLogs("定时确认退费。");
			}
			this.getGeneralDao().save(peBzzRefundInfo);

			// 修改订单退费状态
			EnumConst ec3 = this.getEnumConstDao().getByNamespaceCode("FlagOrderState", "1");
			peBzzOrderInfo.setEnumConstByFlagOrderState(ec3);
			peBzzOrderInfo.setEnumConstByFlagRefundState(ec2);

			EnumConst paymentState = this.getEnumConstDao().getByNamespaceCode("FlagPaymentState", "2");
			peBzzOrderInfo.setEnumConstByFlagPaymentState(paymentState);

			peBzzOrderInfo.setRefundDate(new Date());
			this.getGeneralDao().detachedSave(peBzzOrderInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("退费操作失败！");
		}
	}

	/**
	 * 将退费或者关闭订单的学员专项关系设置成为支付
	 * 
	 * @param peBzzOrderInfo
	 */
	private void recoverStuBatch(PeBzzOrderInfo peBzzOrderInfo) throws EntityException {
		// Lee start 2015年11月5日
		// 如果为直播订单，则直接清除STU_BATCH中该订单数据
		// 学员如想报名直播，需重新添加学员。
		// 理由：直播与专项不同，直播insert STU_BATCH的同时会insert PR_BZZ_TCH_STU_ELECTIVE_BACK
		if ("7".equals(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 视频直播订单
			String delSql = "DELETE FROM STU_BATCH SB WHERE SB.FK_ORDER_ID = '" + peBzzOrderInfo.getId() + "'";
			try {
				this.getGeneralDao().executeBySQL(delSql);
			} catch (Exception e) {
				throw new EntityException("撤销直播支付状态失败！err_code：" + e.getMessage());
			}
		}
		// Lee end

		EnumConst enumConstByFlagBatchPayState = this.getGeneralDao().getEnumConstByNamespaceCode("FlagBatchPayState", "0");// 专项未支付状态
		String reSql = "update\n" + "   stu_batch sb\n" + "   set sb.flag_batchpaystate = '" + enumConstByFlagBatchPayState.getId()
				+ "',\n" + "       sb.fk_order_id = ''\n" + " where sb.fk_order_id = '" + peBzzOrderInfo.getId() + "'";
		try {
			this.getGeneralDao().executeBySQL(reSql);
		} catch (Exception e) {
			throw new EntityException("撤销专项支付状态失败！err_code：" + e.getMessage());
		}
	}

	public void checkRefundPermission(PeBzzOrderInfo peBzzOrderInfo) throws EntityException {
		if (peBzzOrderInfo.getEnumConstByFlagPaymentState().getCode().equals("0")) {
			throw new EntityException("订单尚未到账,不能申请退费");
		}
		Set<PeBzzInvoiceInfo> set = peBzzOrderInfo.getPeBzzInvoiceInfos();
		EnumConst fpOpenState = this.getGeneralDao().getEnumConstByNamespaceCode("FlagFpOpenState", "1");
		if (set.size() > 0) {// 存在多个发票记录时，判断是否存在状态为已开发票的发票记录
			throw new EntityException("已申请或开具发票不能申请退费");
			/*
			 * for(PeBzzInvoiceInfo invoice:set){
			 * if(invoice.getEnumConstByFlagFpOpenState()!=null &&
			 * invoice.getEnumConstByFlagFpOpenState().equals(fpOpenState)){ } }
			 */}
		EnumConst enumConst = this.getGeneralDao().getEnumConstByNamespaceCode("FlagRefundDays", "0");
		Calendar cal = Calendar.getInstance();
		cal.setTime(peBzzOrderInfo.getPaymentDate());
		cal.add(Calendar.DAY_OF_MONTH, +Integer.parseInt(enumConst.getName()));
		Date date = cal.getTime();
		if (peBzzOrderInfo.getPaymentDate() != null && date.before(new Date())) {
			throw new EntityException("支付时间已超过" + enumConst.getName() + "天，不能申请退费");
		} else if (peBzzOrderInfo.getPaymentDate() == null) {
			throw new EntityException("支付时间不存在，不能申请退费");
		}
		// 增加该订单下是否有课程开始学习了，如果有，则不允许退费
		StringBuffer sql = new StringBuffer();
		sql.append("  select pboi.id \n	");
		sql.append("  from pe_bzz_order_info pboi, pr_bzz_tch_stu_elective pbtse \n ");
		sql.append("  where pboi.id = pbtse.fk_order_id  and pboi.id = '" + peBzzOrderInfo.getId() + "' \n ");
		sql.append("  and exists ( select tcs.id from training_course_student tcs where tcs.id  \n");
		sql.append("  = pbtse.fk_training_id and tcs.learn_status != '" + StudyProgress.UNCOMPLETE + "' ) \n ");
		List list = this.getGeneralDao().getBySQL(sql.toString());
		if (list.size() > 0) {
			throw new EntityException("购买课程已有开始学习，不能申请退费");
		}
		// 检查是否已有课程需要重新购买
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select pboi.id from pe_bzz_order_info pboi, elective_history pbtse ");
		sqlBuff.append(" where pboi.id = pbtse.fk_order_id and pboi.id = '" + peBzzOrderInfo.getId() + "'");
		List historyList = this.getGeneralDao().getBySQL(sqlBuff.toString());
		if (historyList.size() > 0) {
			throw new EntityException("该订单下存在重新购买的课程，不能申请退费");
		}
		if ("7".equalsIgnoreCase(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {// 视频直播订单
			// 24小时内
			String liveSql = "SELECT 1 FROM PE_BZZ_TCH_COURSE PBTC WHERE PBTC.ID IN (SELECT FK_COURSE_ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_BATCH_ID  = '"
					+ peBzzOrderInfo.getPeBzzBatch().getId() + "') AND PBTC.ESTIMATESTARTDATE - 1 - SYSDATE < 0";
			List liveList = this.getGeneralDao().getBySQL(liveSql);
			if (liveList.size() > 0)
				throw new EntityException("直播开始前24小时以后不能申请退费");
		}
	}

	/**
	 * 获取二级订单详情
	 * 
	 * @author lipp
	 * @throws EntityException
	 */

	public Page getOrderDetailSub(PeBzzOrderInfo order, SsoUser ssoUser, int pageSize, int startIndex) throws EntityException {

		String sql = "";
		String userSql = "";
		if (ssoUser != null) {
			userSql = "   and pboi.create_user = '" + ssoUser.getId() + "'\n";
		}
		// 添加空判断zgl
		if ((order.getEnumConstByFlagPaymentState() != null && !"0".equalsIgnoreCase(order.getEnumConstByFlagPaymentState().getCode()))
				&& !(order.getEnumConstByFlagRefundState() != null && "1".equalsIgnoreCase(order.getEnumConstByFlagRefundState().getCode()))) {
			sql = "select pbs.reg_no, pbs.true_name, pbtc.code,pbtc.name,pbtc.price ,pbtc.time,tcs.learn_status,pbtc.id,ec.name as ecname,'1' as valid\n"
					+ "  ,enumconstb3_.NAME as categoryName,enumconstb4_.NAME as itemName,enumconstb5_.NAME AS courseContent,pbtc.studydates from pr_bzz_tch_stu_elective ele\n"
					+ "  left join training_course_student tcs\n"
					+ "    on tcs.id = ele.fk_training_id\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end

					"  left join pe_bzz_student pbs\n"
					+ "    on ele.fk_stu_id = pbs.id\n"
					+ "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n" + " where pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ "union\n"
					+ "select pbs.reg_no, pbs.true_name, pbtc.code,pbtc.name,pbtc.price ,pbtc.time,tcs.learn_status,pbtc.id,ec.name as ecname,'0' as valid\n"
					+ "  ,enumconstb3_.NAME as categoryName,enumconstb4_.NAME as itemName,enumconstb5_.NAME AS courseContent,pbtc.studydates from elective_history ele\n"
					+ "  left join tcs_history tcs\n"
					+ "    on tcs.id = ele.fk_training_id\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					// **dgh end
					+ "  left join pe_bzz_student pbs\n"
					+ "    on ele.fk_stu_id = pbs.id\n"
					+ "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n" + " where pboi.id = '" + order.getId() + "'\n" + userSql;
		} else {
			sql = "select pbs.reg_no, pbs.true_name, pbtc.code,pbtc.name,pbtc.price, pbtc.time,'',pbtc.id,ec.name as ecname,'1' as valid\n"
					+ "  ,enumconstb3_.NAME as categoryName,enumconstb4_.NAME as itemName,enumconstb5_.NAME AS courseContent,pbtc.studydates from pr_bzz_tch_stu_elective_back ele\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n" + "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n" + "    on ele.fk_stu_id = pbs.id\n" + "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n" + " where pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ "union\n"
					+ "select pbs.reg_no, pbs.true_name, pbtc.code,pbtc.name,pbtc.price ,pbtc.time,'',pbtc.id,ec.name as ecname,'0' as valid\n"
					+ "  ,enumconstb3_.NAME as categoryName,enumconstb4_.NAME as itemName,enumconstb5_.NAME AS courseContent,pbtc.studydates  from elective_back_history ele\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ "  LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n"
					+ "    on ele.fk_stu_id = pbs.id\n"
					+ "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n" + " where pboi.id = '" + order.getId() + "'\n" + userSql;
		}
		Page page = this.getByPageSQL(sql, pageSize, startIndex);
		return page;
	}

	/**
	 * 获取订单详情
	 * 
	 * @author linjie
	 * @throws EntityException
	 */

	public Page getOrderDetail(PeBzzOrderInfo order, SsoUser ssoUser, int pageSize, int startIndex) throws EntityException {

		String sql = "";
		String userSql = "";
		if (ssoUser != null) {
			userSql = "   and pboi.create_user = '" + ssoUser.getId() + "'\n";
		}
		if (!order.getEnumConstByFlagPaymentState().getCode().equalsIgnoreCase("0")
				&& !(order.getEnumConstByFlagRefundState() != null && "1".equalsIgnoreCase(order.getEnumConstByFlagRefundState().getCode()))) {
			sql = "select pbs.reg_no, pbs.true_name, pbtc.code,pbtc.name, tcs.learn_status,pbtc.time,pbtc.id,ec.name as ecname,'1' as valid\n"
					+ "  ,enumconstb3_.NAME as categoryName,enumconstb4_.NAME as itemName,enumconstb5_.NAME AS courseContent,pbtc.studydates,pbtc.price from pr_bzz_tch_stu_elective ele\n"
					+ "  left join training_course_student tcs\n"
					+ "    on tcs.id = ele.fk_training_id\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end

					"  left join pe_bzz_student pbs\n"
					+ "    on ele.fk_stu_id = pbs.id\n"
					+ "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n" + " where pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ "union\n"
					+ "select pbs.reg_no, pbs.true_name, pbtc.code,pbtc.name, tcs.learn_status,pbtc.time,pbtc.id,ec.name as ecname,'0' as valid\n"
					+ "  ,enumconstb3_.NAME as categoryName,enumconstb4_.NAME as itemName,enumconstb5_.NAME AS courseContent,pbtc.studydates,pbtc.price from elective_history ele\n"
					+ "  left join tcs_history tcs\n"
					+ "    on tcs.id = ele.fk_training_id\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					// **dgh end
					+ "  left join pe_bzz_student pbs\n"
					+ "    on ele.fk_stu_id = pbs.id\n"
					+ "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n" + " where pboi.id = '" + order.getId() + "'\n" + userSql;
		} else {
			sql = "select pbs.reg_no, pbs.true_name, pbtc.code,pbtc.name, '',pbtc.time,pbtc.id,ec.name as ecname,'1' as valid\n"
					+ "  ,enumconstb3_.NAME as categoryName,enumconstb4_.NAME as itemName,enumconstb5_.NAME AS courseContent,pbtc.studydates,pbtc.price from pr_bzz_tch_stu_elective_back ele\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n" + "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n" + "    on ele.fk_stu_id = pbs.id\n" + "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n" + " where pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ "union\n"
					+ "select pbs.reg_no, pbs.true_name, pbtc.code,pbtc.name, '',pbtc.time,pbtc.id,ec.name as ecname,'0' as valid\n"
					+ "  ,enumconstb3_.NAME as categoryName,enumconstb4_.NAME as itemName,enumconstb5_.NAME AS courseContent,pbtc.studydates,pbtc.price from elective_back_history ele\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ "  LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n"
					+ "    on ele.fk_stu_id = pbs.id\n"
					+ "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n" + " where pboi.id = '" + order.getId() + "'\n" + userSql;
		}
		Page page = this.getByPageSQL(sql, pageSize, startIndex);
		return page;
	}

	/**
	 * 关闭订单
	 * 
	 * @author NaN
	 * @modify by linjie
	 * @throws Exception
	 */
	public void closeOrder(PeBzzOrderInfo peBzzOrderInfo) throws Exception {
		String strDate = "2015-12-31";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(strDate);
		if (!peBzzOrderInfo.getEnumConstByFlagPaymentState().getCode().equalsIgnoreCase("0")
				&& !(peBzzOrderInfo.getEnumConstByFlagRefundState() != null && "1".equalsIgnoreCase(peBzzOrderInfo
						.getEnumConstByFlagRefundState().getCode()))) {
			// 订单已支付|订单已退费
			throw new EntityException("未支付且未退费的订单才可以关闭！");
		} else {
			EnumConst enumConstByFlagPaymentMethod = this.enumConstDao.getByNamespaceCode("FlagPaymentMethod", "0");//
			String meId = peBzzOrderInfo.getEnumConstByFlagPaymentMethod().getId();
			Date createDate = peBzzOrderInfo.getCreateDate();
			Date now = new Date();
			Long diff = now.getTime() - createDate.getTime();
			long days = diff / (1000 * 60 * 30);
			if (enumConstByFlagPaymentMethod.getId().equals(meId)) {
				if (days < 1) {
					throw new EntityException("线上支付订单生成后30分钟内不可关闭！");
				}
				String merorderid = peBzzOrderInfo.getSeq();
				try {
					if (peBzzOrderInfo.getCreateDate().after(date)) {
						this.check99BillOrder(merorderid);
					} else {
						this.checkOnlineOrder(merorderid);
					}
				} catch (EntityException ee) {
					if (ee.getMessage().indexOf("支付接口更换") == 0) {
						// 老订单查询，符合关闭条件
					} else {
						try {
							this.check99BillOrder(merorderid);
						} catch (Exception e) {
							throw new Exception(e.getMessage());
						}

						// throw new EntityException(ee.getMessage());
					}
				}
				DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
				orderDc.add(Restrictions.eq("seq", merorderid));
				List<PeBzzOrderInfo> oList = this.getList(orderDc);
				if (oList != null && oList.size() > 0) {
					peBzzOrderInfo = oList.get(0);
					if (peBzzOrderInfo.getEnumConstByFlagPaymentState() != null
							&& "1".equals(peBzzOrderInfo.getEnumConstByFlagPaymentState().getCode())) {
						throw new EntityException("订单已支付到账，不能关闭，如显示错误请刷新订单列表！");
					}
				}
			}

			// 订单有效状态：否；
			EnumConst enumConstByFlagOrderIsValid = this.enumConstDao.getByNamespaceCode("FlagOrderIsValid", "0");
			// 专项：未支付
			EnumConst enumConstByFlagBatchPayState = this.enumConstDao.getByNamespaceCode("FlagBatchPayState", "0");
			// 选课期：未支付
			EnumConst enumConstByFlagElePeriodPayStatus = this.enumConstDao.getByNamespaceCode("FlagElePeriodPayStatus", "0");
			try {

				// 删除和备份学员选课数据
				UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
				DetachedCriteria dcEle = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
				dcEle.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
				dcEle.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
				dcEle.add(Restrictions.eq("peBzzOrderInfo.id", peBzzOrderInfo.getId()));
				List<PrBzzTchStuElectiveBack> eleBackList = this.getGeneralDao().getList(dcEle);

				// 专项订单要特殊处理,因为专项是先生成eleback记录后与订单关联 所以不能删除back数据
				// 专项处理,将“学员--专项”关系中的订单置空。
				if (peBzzOrderInfo.getEnumConstByFlagOrderType() != null
						&& "2".equals(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {
					// 复制选课记录供历史查询
					this.copyElectiveBackHistory(eleBackList);

					List<StudentBatch> sbList = new ArrayList();
					DetachedCriteria sbDC = DetachedCriteria.forClass(StudentBatch.class);
					sbDC.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));
					sbList = this.getGeneralDao().getList(sbDC);
					if (sbList != null && sbList.size() > 0) {
						for (StudentBatch sb : sbList) {
							sb.setPeBzzOrderInfo(null);// 去掉订单关联
							sb.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);// 支付状态，未支付
							this.getGeneralDao().save(sb);
						}
					}
					if (eleBackList != null) {
						for (PrBzzTchStuElectiveBack p : eleBackList) {// 去掉eleback与订单的关联
							p.setPeBzzOrderInfo(null);
							this.generalDao.detachedSave(p);
						}
					}
				} else if (peBzzOrderInfo.getEnumConstByFlagOrderType() != null
						&& "3".equals(peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {
					// 复制选课记录供历史查询
					this.copyElectiveBackHistory(eleBackList);
					// 选课期订单要修改选课期支付状态
					if (peBzzOrderInfo.getPeElectiveCoursePeriod() != null) {
						PeElectiveCoursePeriod peElectiveCoursePeriod = peBzzOrderInfo.getPeElectiveCoursePeriod();
						peElectiveCoursePeriod.setEnumConstByFlagElePeriodPayStatus(enumConstByFlagElePeriodPayStatus);
						this.getGeneralDao().save(peElectiveCoursePeriod);
					}
					// 去掉eleback与订单的关联
					if (eleBackList != null) {
						for (PrBzzTchStuElectiveBack p : eleBackList) {
							p.setPeBzzOrderInfo(null);
							this.generalDao.detachedSave(p);
						}
					}

				} else {// 其他选课
					this.transferElectiveHistory(null, eleBackList);
				}

				// 将专项信息设置为未支付状态
				this.recoverStuBatch(peBzzOrderInfo);

				// 去除报名单关联
				peBzzOrderInfo.setPeSignUpOrder(null);

				// 改变订单的状态,有效状态为否
				peBzzOrderInfo.setEnumConstByFlagOrderIsValid(enumConstByFlagOrderIsValid);
				// 保存修改后的订单状态
				this.getGeneralDao().save(peBzzOrderInfo);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("关闭订单失败--" + e.getMessage());
			}
		}
	}

	public void closeOrderList(List orderIdList, SsoUser ssoUser) throws Exception {
		if (orderIdList != null && orderIdList.size() > 0) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			dc.createCriteria("enumConstByFlagOrderIsValid", "enumConstByFlagOrderIsValid");
			dc.add(Restrictions.eq("ssoUser", ssoUser));
			dc.add(Restrictions.in("id", orderIdList));
			List<PeBzzOrderInfo> orderList = this.getGeneralDao().getList(dc);
			if (orderIdList != null && orderList.size() > 0) {
				if (orderIdList.size() > orderList.size()) {
					throw new EntityException("只能操作自己的订单，不要错误操作！");
				} else {
					for (PeBzzOrderInfo order : orderList) {
						if ("0".equals(order.getEnumConstByFlagPaymentState().getCode()) && order.getEnumConstByFlagRefundState() == null) {// 未支付且未退款的订单才可以
							// 关闭订单
							this.closeOrder(order);
						} else {
							throw new EntityException("未支付且未申请退款的订单才可以！");

						}
					}
				}
			}
		} else {
			throw new EntityException("请选择后提交！");
		}
	}

	public AutoRefund refundOnlineApply(String merorderid, String appuser, String cause, String amountsum) throws EntityException {
		PaymentUtil pu = new PaymentUtil();// 支付工具类

		AutoRefund autoRefund = pu.doRefundApply(merorderid, appuser, cause, amountsum);
		// String status = autoRefund.getStatus();//返回状态
		String refundId_rec = autoRefund.getRefundId();// 退款流水号，用于查询退款状态
		DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		orderDc.add(Restrictions.eq("seq", merorderid));
		List orderList = this.getList(orderDc);
		PeBzzOrderInfo order = null;
		if (orderList != null && orderList.size() > 0) {
			order = (PeBzzOrderInfo) orderList.get(0);
			if (order.getRefundId() == null || "".equals(order.getRefundId())) {
				order.setRefundId(refundId_rec);
				order = (PeBzzOrderInfo) this.save(order);// //退款流水号回写到订单中
			}
		} else {
			throw new EntityException("订单号不存在");
		}

		return autoRefund;
	}

	public AutoRefund refundOnline99BillApply(String merorderid, String appuser, String cause, String amountsum) throws EntityException {
		Payment99BillUtil pu = new Payment99BillUtil();// 支付工具类
		AutoRefund autoRefund = pu.doRefundApply(merorderid, appuser, cause, amountsum);
		String refundId_rec = autoRefund.getRefundId();// 退款流水号，用于查询退款状态
		DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		orderDc.add(Restrictions.eq("seq", merorderid));
		List orderList = this.getList(orderDc);
		PeBzzOrderInfo order = null;
		if (orderList != null && orderList.size() > 0) {
			order = (PeBzzOrderInfo) orderList.get(0);
			if (order.getRefundId() == null || "".equals(order.getRefundId())) {
				order.setRefundId(refundId_rec);
				order = (PeBzzOrderInfo) this.save(order);// //退款流水号回写到订单中
			}
		} else {
			throw new EntityException("订单号不存在");
		}
		return autoRefund;
	}

	/**
	 * 执行退费，先去三方判断退费状态。 如果没有refundId，只能等待通过时间段验证退费的定时任务执行
	 * refundId易宝存在bug（预计20130121解决）
	 * 
	 * @author linjie
	 * @param merorderid
	 * @param refundId
	 * @throws EntityException
	 */
	public String confirmRefund(String merorderid, String refundId) throws EntityException {
		PaymentUtil pu = new PaymentUtil();// 支付工具类
		List<OnlineRefundInfo> refundList = pu.checkRefoundState(refundId, "", "");
		if (refundList != null && refundList.size() > 0) {
			for (int i = 0; i < refundList.size(); i++) {
				OnlineRefundInfo refund = refundList.get(i);
				if ("1".equals(refund.getState()) || "3".equals(refund.getState())) {// 退款成功的订单
					// 退款申请成功，后续处理
					DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
					dc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState");
					dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
					dc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentMethod", "enumConstByFlagPaymentMethod");
					dc.createCriteria("peBzzOrderInfo.ssoUser", "ssoUser");
					dc.add(Restrictions.eq("peBzzOrderInfo.seq", merorderid));
					try {
						PeBzzRefundInfo peBzzRefundInfo = (PeBzzRefundInfo) this.getList(dc).get(0);
						this.executeRefund(peBzzRefundInfo);

					} catch (EntityException e) {
						throw new EntityException("退款后续处理失败");
					}
				} else {
					return "请等待平台确认退款。";
				}
			}
		}
		return "";
	}

	public String confirm99BillRefund(String merorderid, String refundId) throws EntityException {
		Payment99BillUtil pu = new Payment99BillUtil();// 支付工具类
		List<OnlineRefundInfo> refundList = pu.checkRefoundState(refundId, "", "");
		if (refundList != null && refundList.size() > 0) {
			for (int i = 0; i < refundList.size(); i++) {
				OnlineRefundInfo refund = refundList.get(i);
				// 1处理中3成功
				if ("1".equals(refund.getState()) || "3".equals(refund.getState())) {// 退款成功的订单
					// 退款申请成功，后续处理
					DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
					dc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState");
					dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
					dc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentMethod", "enumConstByFlagPaymentMethod");
					dc.createCriteria("peBzzOrderInfo.ssoUser", "ssoUser");
					dc.add(Restrictions.eq("peBzzOrderInfo.seq", merorderid));
					try {
						PeBzzRefundInfo peBzzRefundInfo = (PeBzzRefundInfo) this.getList(dc).get(0);
						this.executeRefund(peBzzRefundInfo);

					} catch (EntityException e) {
						throw new EntityException("退款后续处理失败");
					}
					return "退费成功";
				} else {
					return "请等待平台确认退款。";
				}
			}
		}
		return "";
	}

	@Override
	public PeBzzOrderInfo getOrderBySeq(String seq) throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("seq", seq));
		List<PeBzzOrderInfo> orderList = this.getGeneralDao().getList(dc);
		if (orderList != null && orderList.size() > 0) {
			return orderList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public PeBzzRefundInfo initOnlineRefund(PeBzzRefundInfo peBzzRefundInfo) throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		PeBzzOrderInfo peBzzOrderInfo = peBzzRefundInfo.getPeBzzOrderInfo();
		EnumConst enumConstByFlagRefundState = this.getEnumConstDao().getByNamespaceCode("FlagRefundState", "3");// 退费中
		peBzzOrderInfo.setEnumConstByFlagRefundState(enumConstByFlagRefundState);
		peBzzOrderInfo = (PeBzzOrderInfo) this.save(peBzzOrderInfo);
		peBzzRefundInfo.setPeBzzOrderInfo(peBzzOrderInfo);
		peBzzRefundInfo.setEnumConstByFlagRefundState(enumConstByFlagRefundState);
		String logs = peBzzRefundInfo.getOperationLogs();
		if (logs == null) {
			logs = "";
		}
		StringBuffer buf = new StringBuffer(logs);
		buf.append("|" + us.getLoginId() + "/" + us.getUserName() + "执行了退费终审确认操作\n");
		peBzzRefundInfo.setOperationLogs(buf.toString());
		peBzzRefundInfo = (PeBzzRefundInfo) this.save(peBzzRefundInfo);
		return null;
	}

	@Override
	public void checkOnlineOrder(String merorderid) throws EntityException {
		PaymentUtil pu = new PaymentUtil();// 支付工具类
		List<OnlineOrderInfo> onlineOrderList = null;
		try {
			onlineOrderList = pu.checkOrderState(merorderid);
			if (onlineOrderList != null && onlineOrderList.size() > 0) {
				for (OnlineOrderInfo o : onlineOrderList) {
					if ("1".equalsIgnoreCase(o.getState()) && merorderid.equals(o.getMerOrderId())) {// 支付成功
						DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
						orderDc.add(Restrictions.eq("seq", merorderid));
						PeBzzOrderInfo pbo = null;
						List orderList = this.getList(orderDc);
						if (orderList != null && orderList.size() > 0) {
							pbo = (PeBzzOrderInfo) orderList.get(0);
							if (Double.parseDouble(pbo.getAmount()) == Double.parseDouble(o.getAmountSum())) {
								confirmOnlineOrder(merorderid); // 确认订单
							} else {
								System.out.println("订单:" + merorderid + "金额不符--" + pbo.getAmount() + "-" + o.getAmountSum() + "--"
										+ (new Date().toString()));
								throw new EntityException("订单金额不符，请联系管理员！");
							}
						} else {
							throw new EntityException("返回订单查询错误，请联系管理员！");
						}

					}
				}
			}
		} catch (EntityException e) {
			throw e;
		}

	}

	@Override
	public PeBzzOrderInfo confirmOnlineOrder(String merorderid) throws EntityException {
		// 未到账的，防止定时任务与平台返回同时操作造成的异常。
		EnumConst enumConstByFlagPaymentState = this.getEnumConstDao().getByNamespaceCode("FlagPaymentState", "0");
		DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		orderDc.add(Restrictions.eq("enumConstByFlagPaymentState", enumConstByFlagPaymentState));
		orderDc.add(Restrictions.eq("seq", merorderid));
		List orderList = this.getList(orderDc);
		PeBzzOrderInfo pbo = null;
		if (orderList != null && orderList.size() > 0) {
			pbo = this.updatePeBzzOrderInfo((PeBzzOrderInfo) orderList.get(0), "confirm", null);
		}
		return pbo;

	}

	/**
	 * 重写到账方法，增加订单支付方式
	 * 
	 * @param merorderid
	 *            订单号
	 * @param paymentMethod
	 *            支付类型(企业、个人)
	 * @return 订单Bean
	 * @author //TODO YCL 2016-03-15 到账方法重载：增加支付方式类型
	 * @throws EntityException
	 */
	@Override
	public PeBzzOrderInfo confirmOnlineOrder(String merorderid, String paymentMethod) throws EntityException {
		// 未到账的，防止定时任务与平台返回同时操作造成的异常。
		EnumConst enumConstByFlagPaymentState = this.getEnumConstDao().getByNamespaceCode("FlagPaymentState", "0");
		DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		orderDc.add(Restrictions.eq("enumConstByFlagPaymentState", enumConstByFlagPaymentState));
		orderDc.add(Restrictions.eq("seq", merorderid));
		List orderList = this.getList(orderDc);
		PeBzzOrderInfo pbo = null;
		if (orderList != null && orderList.size() > 0) {
			pbo = this.updatePeBzzOrderInfo((PeBzzOrderInfo) orderList.get(0), "confirm", null, paymentMethod);
		}
		return pbo;
	}

	/**
	 * 余额变动记录
	 * 
	 * @param ssoUser
	 * @param peBzzOrderInfo
	 * @param classHour
	 * @param amountNum
	 * @param note
	 * @throws EntityException
	 */
	public void saveBalanceHistory(SsoUser ssoUser, PeBzzOrderInfo peBzzOrderInfo, double classHour, double amountNum, String note,
			String note2) throws EntityException {
		try {
			PeBalanceHistory pbh = new PeBalanceHistory();
			pbh.setClassHour(classHour);
			pbh.setAmountNum(amountNum);
			pbh.setPeBzzOrderInfo(peBzzOrderInfo);
			pbh.setSsoUser(ssoUser);
			pbh.setOperateDate(new Date());
			pbh.setNote(note);
			pbh.setNote2(note2);
			this.save(pbh);
		} catch (EntityException e) {
			throw new EntityException("账户操作失败！");
		}
	}

	/**
	 * 自动对账
	 * 
	 * @param startTime
	 * @param endTime
	 * @param payBank
	 */
	public void checkReconciliation(String startTime, String endTime, String payBank) throws EntityException {
		Payment99BillUtil pu = new Payment99BillUtil();// 支付工具类
		List<GatewayOrderDetail> onlineOrder99BillList = null;
		try {
			onlineOrder99BillList = pu.checkOrderList(startTime, endTime, payBank);
			if (onlineOrder99BillList != null && onlineOrder99BillList.size() > 0) {
				int num = onlineOrder99BillList.size();
				int size = 500;// 每次执行500个
				int x = (num % size == 0) ? (num / size) : ((num / size) + 1);
				for (int i = 0; i < x; i++) {
					for (int j = i * size; j < (i + 1) * size && j < num; j++) {
						// 调用单个订单对账
						this.reconciliation(onlineOrder99BillList.get(j));
					}
				}
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			throw e;
		}

	}

	/**
	 * 通过单个返回对订单对账
	 * 
	 * @param onlineOrderInfo
	 */
	public boolean reconciliation(OnlineOrderInfo onlineOrderInfo) {
		String oId = onlineOrderInfo.getMerOrderId();
		DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		orderDc.add(Restrictions.eq("seq", oId));
		List orderList;
		PeBzzOrderInfo peBzzOrderInfo;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		boolean checked = false;
		String msg = "";
		try {
			if ("1".equals(onlineOrderInfo.getState())) {// 只处理远程状态为到账的
				orderList = this.getList(orderDc);
				if (orderList != null && orderList.size() > 0) {
					peBzzOrderInfo = (PeBzzOrderInfo) orderList.get(0);
					if (enumConstDao == null) {
						enumConstDao = (EnumConstDAO) SpringUtil.getBean("enumConstDao");
					}
					EnumConst enumConstByFlagCheckState0 = this.enumConstDao.getByNamespaceCode("FlagCheckState", "0");// 未对账
					EnumConst enumConstByFlagCheckState1 = this.enumConstDao.getByNamespaceCode("FlagCheckState", "1");// 已对账
					EnumConst enumConstByFlagCheckState2 = this.enumConstDao.getByNamespaceCode("FlagCheckState", "2");// 对账异常
					EnumConst enumConstByFlagCheckState3 = this.enumConstDao.getByNamespaceCode("FlagCheckState", "3");// 退费处理

					EnumConst enumConstByFlagPaymentState = peBzzOrderInfo.getEnumConstByFlagPaymentState();
					EnumConst enumConstByFlagCheckState = peBzzOrderInfo.getEnumConstByFlagCheckState();

					try {
						// 跳过已对账，和退费的
						if (enumConstByFlagCheckState == null
								|| (!"1".equals(enumConstByFlagCheckState.getCode()) && !"3".equals(enumConstByFlagCheckState.getCode()))) {
							// !验证金额!
							if (peBzzOrderInfo != null
									&& Double.parseDouble(peBzzOrderInfo.getAmount()) == Double.parseDouble(onlineOrderInfo.getAmountSum())) {
								// //已经退费的订单
								// if("2".equals(enumConstByFlagPaymentState.getCode())){//退费处理
								// peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState3);
								// peBzzOrderInfo.setCheckNote("退费处理");
								// this.save(peBzzOrderInfo);
								// }
								// 未到账，但三方到账 先确认到账，再标记对账完成
								if ("0".equals(enumConstByFlagPaymentState.getCode())) {// 未到账
									msg = "订单" + onlineOrderInfo.getMerOrderId() + "未到账,不能进行对账操作";
									// peBzzOrderInfo =
									// confirmOnlineOrder(onlineOrderInfo.getMerOrderId());
									// //确认订单
									peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState2);
									peBzzOrderInfo.setCheckNote(msg);
									peBzzOrderInfo.setCheckDate(new Date());
									this.save(peBzzOrderInfo);
									checked = false;
								}
								// 已到账，标记对账完成
								else if ("1".equals(enumConstByFlagPaymentState.getCode())
										|| "2".equals(enumConstByFlagPaymentState.getCode())) {// 已到账
									// 或已退费
									msg = "对账处理";
									peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState1);
									peBzzOrderInfo.setCheckNote(msg);
									peBzzOrderInfo.setCheckDate(new Date());
									this.save(peBzzOrderInfo);
									checked = true;
								}

								// 平台已到，三方未到，错误数据，需记录

								// 退费中，标记退费
							} else {
								msg = "对账错误：" + onlineOrderInfo.getMerOrderId() + "金额不符--" + peBzzOrderInfo.getAmount() + "--"
										+ onlineOrderInfo.getAmountSum();
								peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState2);
								peBzzOrderInfo.setCheckNote(msg);
								peBzzOrderInfo.setCheckDate(new Date());
								this.save(peBzzOrderInfo);
								checked = false;
							}
						}
					} catch (Exception e) {
						msg = "对账错误：" + e.getMessage();
						peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState2);
						peBzzOrderInfo.setCheckNote(msg);
						peBzzOrderInfo = (PeBzzOrderInfo) this.save(peBzzOrderInfo);
						checked = false;
					}
					// if(checked){//处理过的记录才进行记录
					WhatyuserLog4j log = new WhatyuserLog4j();
					// log.setIp(ServletActionContext.getRequest().getRemoteAddr());
					log.setBehavior("GeneralServiceImp_reconciliation"); // 动作
					log.setNotes("对账完成，订单：" + peBzzOrderInfo.getSeq() + "--" + msg);// 说明；
					log.setStatus(peBzzOrderInfo.getEnumConstByFlagCheckState().getName());
					log.setOperateTime(new Date()); // 时间
					if (us == null) {
						log.setUserid("系统处理"); // SSO LOGINID
						log.setLogtype("系统处理");
					} else {
						log.setUserid(us.getLoginId()); // SSO LOGINID
						log.setLogtype(us.getUserLoginType());
					}
					this.save(log);
					// }
				}
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checked;
	}

	/**
	 * 通过单个返回对订单对账
	 * 
	 * @param GatewayOrderDetail
	 */
	public boolean reconciliation(GatewayOrderDetail detail) {
		String oId = detail.getOrderId();
		DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		orderDc.add(Restrictions.eq("seq", oId));
		List orderList;
		PeBzzOrderInfo peBzzOrderInfo;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		boolean checked = false;
		String msg = "";
		try {
			if ("10".equals(detail.getPayResult())) {// 只处理远程状态为到账的
				orderList = this.getList(orderDc);
				if (orderList != null && orderList.size() > 0) {
					peBzzOrderInfo = (PeBzzOrderInfo) orderList.get(0);
					if (enumConstDao == null) {
						enumConstDao = (EnumConstDAO) SpringUtil.getBean("enumConstDao");
					}
					EnumConst enumConstByFlagCheckState0 = this.enumConstDao.getByNamespaceCode("FlagCheckState", "0");// 未对账
					EnumConst enumConstByFlagCheckState1 = this.enumConstDao.getByNamespaceCode("FlagCheckState", "1");// 已对账
					EnumConst enumConstByFlagCheckState2 = this.enumConstDao.getByNamespaceCode("FlagCheckState", "2");// 对账异常
					EnumConst enumConstByFlagCheckState3 = this.enumConstDao.getByNamespaceCode("FlagCheckState", "3");// 退费处理

					EnumConst enumConstByFlagPaymentState = peBzzOrderInfo.getEnumConstByFlagPaymentState();
					EnumConst enumConstByFlagCheckState = peBzzOrderInfo.getEnumConstByFlagCheckState();

					try {
						// 跳过已对账，和退费的
						if (enumConstByFlagCheckState == null
								|| (!"1".equals(enumConstByFlagCheckState.getCode()) && !"3".equals(enumConstByFlagCheckState.getCode()))) {
							// 远程订单与第三方订单金额对比(快钱金额以"分"为单位,远程金额以元为单位)
							String _99BillAmount = BigDecimal.valueOf(Long.valueOf(detail.getOrderAmount())).divide(new BigDecimal(100))
									.toString();
							// !验证金额!
							if (peBzzOrderInfo != null
									&& Double.parseDouble(peBzzOrderInfo.getAmount()) == Double.parseDouble(_99BillAmount)) {
								// 未到账，但三方到账 先确认到账，再标记对账完成
								if ("0".equals(enumConstByFlagPaymentState.getCode())) {// 未到账
									msg = "订单" + detail.getOrderId() + "未到账,不能进行对账操作";
									// //确认订单
									peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState2);
									peBzzOrderInfo.setCheckNote(msg);
									peBzzOrderInfo.setCheckDate(new Date());
									this.save(peBzzOrderInfo);
									checked = false;
								}
								// 已到账，标记对账完成
								else if ("1".equals(enumConstByFlagPaymentState.getCode())
										|| "2".equals(enumConstByFlagPaymentState.getCode())) {// 已到账
									// 或已退费
									msg = "对账处理";
									peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState1);
									peBzzOrderInfo.setCheckNote(msg);
									peBzzOrderInfo.setCheckDate(new Date());
									this.save(peBzzOrderInfo);
									checked = true;
								}

								// 平台已到，三方未到，错误数据，需记录

								// 退费中，标记退费
							} else {
								msg = "对账错误：" + detail.getOrderId() + "金额不符--" + peBzzOrderInfo.getAmount() + "--" + _99BillAmount;
								peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState2);
								peBzzOrderInfo.setCheckNote(msg);
								peBzzOrderInfo.setCheckDate(new Date());
								this.save(peBzzOrderInfo);
								checked = false;
							}
						}
					} catch (Exception e) {
						msg = "对账错误：" + e.getMessage();
						peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState2);
						peBzzOrderInfo.setCheckNote(msg);
						peBzzOrderInfo = (PeBzzOrderInfo) this.save(peBzzOrderInfo);
						checked = false;
					}
					// if(checked){//处理过的记录才进行记录
					WhatyuserLog4j log = new WhatyuserLog4j();
					log.setBehavior("GeneralServiceImp_reconciliation"); // 动作
					log.setNotes("对账完成，订单：" + peBzzOrderInfo.getSeq() + "--" + msg);// 说明；
					log.setStatus(peBzzOrderInfo.getEnumConstByFlagCheckState().getName());
					log.setOperateTime(new Date()); // 时间
					if (us == null) {
						log.setUserid("系统处理"); // SSO LOGINID
						log.setLogtype("系统处理");
					} else {
						log.setUserid(us.getLoginId()); // SSO LOGINID
						log.setLogtype(us.getUserLoginType());
					}
					this.save(log);
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return checked;
	}

	/**
	 * 通过单个返回对订单对账
	 * 
	 * @param onlineOrderInfo
	 */
	public boolean unReconciliation(OnlineOrderInfo onlineOrderInfo) {
		String oId = onlineOrderInfo.getMerOrderId();
		DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		orderDc.add(Restrictions.eq("seq", oId));
		List orderList;
		PeBzzOrderInfo peBzzOrderInfo;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		boolean checked = false;
		String msg = "";
		try {
			if ("1".equals(onlineOrderInfo.getState())) {// 只处理远程状态为到账的
				orderList = this.getList(orderDc);
				if (orderList != null && orderList.size() > 0) {
					peBzzOrderInfo = (PeBzzOrderInfo) orderList.get(0);
					if (enumConstDao == null) {
						enumConstDao = (EnumConstDAO) SpringUtil.getBean("enumConstDao");
					}
					EnumConst enumConstByFlagCheckState0 = this.enumConstDao.getByNamespaceCode("FlagCheckState", "0");// 未对账

					try {
						peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState0);
						peBzzOrderInfo.setCheckNote("撤销对账");
						peBzzOrderInfo.setCheckDate(null);
						this.save(peBzzOrderInfo);
						checked = true;
					} catch (Exception e) {
						checked = false;
					}
					WhatyuserLog4j log = new WhatyuserLog4j();
					// log.setIp(ServletActionContext.getRequest().getRemoteAddr());
					log.setBehavior("GeneralServiceImp_reconciliation"); // 动作
					log.setNotes("撤销对账，订单：" + peBzzOrderInfo.getSeq() + "--" + msg);// 说明；
					log.setStatus(peBzzOrderInfo.getEnumConstByFlagCheckState().getName());
					log.setOperateTime(new Date()); // 时间
					if (us == null) {
						log.setUserid("系统处理"); // SSO LOGINID
						log.setLogtype("系统处理");
					} else {
						log.setUserid(us.getLoginId()); // SSO LOGINID
						log.setLogtype(us.getUserLoginType());
					}
					this.save(log);
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return checked;
	}

	@Override
	public PeBzzInvoiceInfo getLastInvoice(String userId) throws EntityException {

		PeBzzInvoiceInfo peBzzInvoiceInfo = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.createCriteria("peBzzOrderInfo.ssoUser", "peBzzOrderInfo.ssoUser");
		dc.add(Restrictions.eq("peBzzOrderInfo.ssoUser.id", userId));
		dc.add(Restrictions.isNotNull("createDate"));
		dc.addOrder(Order.desc("createDate"));
		List<PeBzzInvoiceInfo> iList = null;
		try {
			iList = this.getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (iList != null && iList.size() > 0) {
			peBzzInvoiceInfo = iList.get(0);
		} else {
			peBzzInvoiceInfo = new PeBzzInvoiceInfo();
		}
		return peBzzInvoiceInfo;
	}

	public PeBzzInvoiceInfo getLastInvoice(String userId, String order_id) throws EntityException {

		PeBzzInvoiceInfo peBzzInvoiceInfo = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.add(Restrictions.eq("peBzzOrderInfo.id", order_id));
		dc.addOrder(Order.desc("peBzzOrderInfo.createDate"));
		List<PeBzzInvoiceInfo> iList = null;
		try {
			iList = this.getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (iList != null && iList.size() > 0) {
			peBzzInvoiceInfo = iList.get(0);
		} else {
			peBzzInvoiceInfo = new PeBzzInvoiceInfo();
		}
		return peBzzInvoiceInfo;
	}

	// /**
	// * 批量执行sql(有事务)
	// *
	// * @author Lee
	// * @param sqlList
	// * SQL集合 SQL格式为："SELECT ... FROM ... WHERE NAME=? AND
	// * FK_STU_ID=?"
	// * @param paramsList
	// * 参数集合，参数为List集合，注意参数顺序！一定要与自定义SQL中的对应
	// * @return success为批量执行成功，error为批量执行失败
	// * @throws Exception
	// */
	// public String executeBatchSql(List<String> sqlList, List<List<String>>
	// paramsList) throws Exception {
	// return this.getGeneralDao().executeBatchSql(sqlList, paramsList);
	// }

	// /**
	// * 自定义sql查询
	// *
	// * @author Lee
	// * @param sqlList
	// * 自定义SQL，SQL格式为："SELECT ... FROM ... WHERE NAME=? AND
	// * FK_STU_ID=?"
	// * @param paramsList
	// * 参数集合，参数为List集合，注意参数顺序！一定要与自定义SQL中的对应
	// * @param pageSize
	// * 页面显示数
	// * @param startIndex
	// * 页面开始数
	// * @return 查询结果List
	// * @throws Exception
	// */
	// public List getBySQL(String sql, List<String> params, int pageSize, int
	// startIndex) throws Exception {
	// return this.getGeneralDao().getBySQL(sql, params, pageSize, startIndex);
	// }

	/**
	 * 自定义sql查询
	 * 
	 * @author Lee
	 * @param sqlList
	 *            自定义SQL，SQL格式为："SELECT ... FROM ... WHERE NAME=? AND
	 *            FK_STU_ID=?"
	 * @param paramsList
	 *            参数集合，参数为List集合，注意参数顺序！一定要与自定义SQL中的对应
	 * @param pageSize
	 *            页面显示数
	 * @param startIndex
	 *            页面开始数
	 * @return 查询结果Page
	 * @throws Exception
	 */
	public Page getByPageSQL(String sql, List<String> params, int pageSize, int startIndex) throws Exception {
		return this.getByPageSQL(sql, params, pageSize, startIndex);
	}

	/**
	 * 保存多次学习记录
	 * 
	 * @param tcs
	 *            学习记录实体
	 * @throws Exception
	 */
	public void saveTrainingHis(String userId, Object courseId, Object opencourseId, Date startTime, Date endTime) throws Exception {
		this.getGeneralDao().saveTrainingHis(userId, courseId, opencourseId, startTime, endTime);
	}

	public void saveLoginfo(String executeDetail, String userId, String modeType, String loginPost, String writeValue, String ipAdress,
			String url) {
		String sql = " insert into log_info(id,execute_Detail,user_code,mode_type ,login_post ,write_value,ip_adress ,execute_date ,url) values("
				+ "sys_guid(),"
				+ "'"
				+ executeDetail
				+ "',"
				+ "'"
				+ userId
				+ "',"
				+ "'"
				+ modeType
				+ "',"
				+ "'"
				+ loginPost
				+ "',"
				+ "'"
				+ writeValue + "'," + "'" + ipAdress + "'," + "sysdate ,'" + url + "')";
		dbpool db = new dbpool();
		db.executeUpdate(sql);
		db = null;
	}

	/**
	 * 分配学时方法
	 * 
	 * @param tcsInsert
	 *            学习记录sql
	 * @param pbtseUpdate
	 *            选课表sql
	 * @return 成功与否
	 * @throws Exception
	 */
	public int saveLiveTime(List<StringBuffer> tcsInsert, String pbtseUpdate) throws Exception {
		int res1 = 0;
		for (StringBuffer tcsIns : tcsInsert) {
			this.getGeneralDao().executeBySQL(tcsIns.toString());
			res1++;
		}
		int res2 = this.getGeneralDao().executeBySQL(pbtseUpdate);
		if (res1 <= 0 || res2 <= 0) {
			throw new Exception();
		}
		if (res1 != res2) {
			throw new Exception();
		}
		if (res1 == res2)
			return res2;
		return 0;
	}

	/**
	 * 采集并保存数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int saveDataSource(String id) {
		// 查询直播ID
		String sql = "SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "'";
		List result = new ArrayList();
		try {
			result = this.getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
			return 61;
		}
		String webcastId = "";
		if (result == null || result.size() == 0) {
			return 61;
		} else {
			webcastId = result.get(0).toString();
		}
		String loginName = GlobalProperties.getProperty("sys.zhibo.user");
		String password = GlobalProperties.getProperty("sys.zhibo.pwd");
		String service_host = GlobalProperties.getProperty("sys.zhibo.url");

		// //测试直播地址
		// //-start
		// loginName = "admin@sactest.com";
		// password = "sactest";
		// service_host = "http://sactest.gensee.com/integration/site/webcast/";
		// //-end
		//		

		/* 导出直播用户访问历史记录 */
		String service_method01 = GlobalProperties.getProperty("sys.zhibo.service_method01");
		/* 导出直播投票结果 */
		String service_method02 = GlobalProperties.getProperty("sys.zhibo.service_method02");
		/* 导出直播问卷结果 */
		String service_method03 = GlobalProperties.getProperty("sys.zhibo.service_method03");
		/* 导出直播用户提问数据 */
		String service_method04 = GlobalProperties.getProperty("sys.zhibo.service_method04");
		/* 导出直播点名签到数据 */
		String service_method05 = GlobalProperties.getProperty("sys.zhibo.service_method05");
		String service_params = "?webcastId=" + webcastId + "&loginName=" + loginName + "&password=" + password;

		Map dataMap1 = getReturnMap(service_host, service_method01, service_params);
		Map dataMap2 = getReturnMap(service_host, service_method02, service_params);
		Map dataMap3 = getReturnMap(service_host, service_method03, service_params);
		Map dataMap4 = getReturnMap(service_host, service_method04, service_params);
		Map dataMap5 = getReturnMap(service_host, service_method05, service_params);
		if (dataMap1.containsKey("error"))
			return 2210;
		if (dataMap2.containsKey("error"))
			return 2212;
		if (dataMap3.containsKey("error"))
			return 2214;
		if (dataMap4.containsKey("error"))
			return 2216;
		if (dataMap5.containsKey("error"))
			return 2218;
		return this.saveDatatoDB(id, webcastId, dataMap1, dataMap2, dataMap3, dataMap4, dataMap5);
	}

	/**
	 * 采集返回数据
	 * 
	 * @param service_host
	 * @param service_method
	 * @param service_params
	 * @return
	 */
	@Override
	public Map getReturnMap(String service_host, String service_method, String service_params) {
		try {
			URL url = new URL(service_host + service_method + service_params);
			URLConnection connection = url.openConnection();
			if (null != connection) {
				InputStream in = connection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf8"));
				String temp = "";
				String s = "";
				while ((temp = br.readLine()) != null) {
					s = s + temp;
				}
				StringBuffer out = new StringBuffer(s);

				byte[] b = new byte[4096];
				for (int n; (n = in.read(b)) != -1;) {
					out.append(new String(b, 0, n));
				}

				String service_return = out.toString();
				JSONObject jso = JSONObject.fromObject(service_return);

				Iterator keyIter = jso.keys();
				String key;
				Object value;
				Map valueMap = new HashMap();
				while (keyIter.hasNext()) {
					key = (String) keyIter.next();
					value = jso.get(key);
					valueMap.put(key, value);
				}
				return valueMap;
			} else {
				return (Map) (new HashMap().put("error", "获取失败2264!"));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return (Map) (new HashMap().put("error", e));
		} catch (IOException e) {
			e.printStackTrace();
			return (Map) (new HashMap().put("error", e));
		}
	}

	/**
	 * 保存数据到数据库
	 * 
	 * @param map1直播用户访问历史记录
	 * @param map2直播投票结果
	 * @param map3直播问卷结果
	 * @param map4直播用户提问数据
	 * @param map5直播点名签到数据
	 * @return
	 */
	public int saveDatatoDB(String sacLiveId, String webcastId, Map map1, Map map2, Map map3, Map map4, Map map5) {
		/* 直播用户访问历史记录 */
		JSONArray json_list01 = (JSONArray) map1.get("list");
		if (json_list01 != null) {
			for (int i = 0; i < json_list01.size(); i++) {
				WeHistory wh = new WeHistory();
				/* 学员LOGIN_ID-昵称 */
				String nickname = ((JSONObject) json_list01.get(i)).containsKey("nickname") ? ((JSONObject) json_list01.get(i))
						.getString("nickname") : "";
				wh.setWhNickname(nickname);
				/* 加入时间 */
				String joinTime = ((JSONObject) json_list01.get(i)).containsKey("joinTime") ? ((JSONObject) json_list01.get(i))
						.getString("joinTime") : "";
				wh.setWhJointime(joinTime);
				/* 离开时间 */
				String leaveTime = ((JSONObject) json_list01.get(i)).containsKey("leaveTime") ? ((JSONObject) json_list01.get(i))
						.getString("leaveTime") : "";
				wh.setWhLeavetime(leaveTime);
				/* IP地址 */
				String ip = ((JSONObject) json_list01.get(i)).containsKey("ip") ? ((JSONObject) json_list01.get(i)).getString("ip") : "";
				wh.setWhIp(ip);
				/* 用户ID */
				String uid = ((JSONObject) json_list01.get(i)).containsKey("uid") ? ((JSONObject) json_list01.get(i)).getString("uid") : "";
				wh.setWhUid(uid);
				/* 用户名称 */
				String nick_name = ((JSONObject) json_list01.get(i)).containsKey("nickname") ? ((JSONObject) json_list01.get(i))
						.getString("nickname") : "";
				wh.setWhName(nick_name);
				/* 区域-文档中为are实际为area */
				String are = ((JSONObject) json_list01.get(i)).containsKey("area") ? ((JSONObject) json_list01.get(i)).getString("area")
						: "";
				wh.setWhAre(are);
				/* 直播ID */
				wh.setWhWebcastid(webcastId);
				/* 采集日期 */
				wh.setWhDate(new Date());
				try {
					this.save(wh);
				} catch (EntityException e) {
					e.printStackTrace();
					return 2320;
				}
			}
		}
		/* 直播投票结果 */
		JSONArray json_list02 = (JSONArray) map2.get("voteList");
		if (json_list02 != null) {
			for (int i = 0; i < json_list02.size(); i++) {
				WeVoteVotelist wvvl = new WeVoteVotelist();
				/* 投票的主题 */
				String subject = ((JSONObject) json_list02.get(i)).containsKey("subject") ? ((JSONObject) json_list02.get(i))
						.getString("subject") : "";
				wvvl.setWvvSubject(subject);
				/* 直播ID */
				wvvl.setWvvWebcastid(webcastId);
				/* 采集日期 */
				wvvl.setWvvDate(new Date());
				try {
					wvvl = (WeVoteVotelist) this.save(wvvl);
					/* 上一步save失败导致没有ID */
					if (wvvl.getWvvId() == null || "".equals(wvvl.getWvvId())) {
						return 21;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 21;
				}
				/* 问题内容 */
				JSONArray json_list0201 = ((JSONObject) json_list02.get(i)).containsKey("questions") ? (JSONArray) ((JSONObject) json_list02
						.get(i)).get("questions")
						: null;
				if (json_list0201 != null) {
					for (int ii = 0; ii < json_list0201.size(); ii++) {
						WeVoteQuestion wvq = new WeVoteQuestion();
						/* 问题内容 */
						String content = ((JSONObject) json_list0201.get(ii)).containsKey("content") ? ((JSONObject) json_list0201.get(ii))
								.getString("content") : "";
						wvq.setWvqContent(content);
						/* 问题ID */
						wvq.setWvqWvvid(wvvl.getWvvId());
						try {
							wvq = (WeVoteQuestion) this.save(wvq);
							/* 上一步save失败导致没有ID */
							if (wvq.getWvqId() == null || "".equals(wvq.getWvqId())) {
								return 21;
							}
						} catch (EntityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return 21;
						}
						JSONArray json_list020101 = ((JSONObject) json_list0201.get(ii)).containsKey("options") ? (JSONArray) ((JSONObject) json_list0201
								.get(ii)).get("options")
								: null;
						if (json_list020101 != null) {
							for (int iii = 0; iii < json_list020101.size(); iii++) {
								WeVoteOption wvo = new WeVoteOption();
								/* 问题的值 */
								String value = ((JSONObject) json_list020101.get(iii)).containsKey("value") ? ((JSONObject) json_list020101
										.get(iii)).getString("value") : "";
								wvo.setWvoValue(value);
								/* 投票结果 */
								String result = ((JSONObject) json_list020101.get(iii)).containsKey("result") ? ((JSONObject) json_list020101
										.get(iii)).getString("result")
										: "";
								wvo.setWvoResult(result);
								/* 百分比 */
								String precentage = ((JSONObject) json_list020101.get(iii)).containsKey("percentage") ? ((JSONObject) json_list020101
										.get(iii)).getString("percentage")
										: "";
								wvo.setWvoPrecentage(precentage);
								/* 问题内容ID */
								wvo.setWvoWvqId(wvq.getWvqId());
								try {
									wvo = (WeVoteOption) this.save(wvo);
									/* 上一步save失败导致没有ID */
									if (wvo.getWvoId() == null || "".equals(wvo.getWvoId())) {
										return 21;
									}
								} catch (EntityException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return 21;
								}
								JSONArray json_list02010101 = ((JSONObject) json_list020101.get(iii)).containsKey("results") ? (JSONArray) ((JSONObject) json_list020101
										.get(iii)).get("results")
										: null;
								if (json_list02010101 != null) {
									for (int iiii = 0; iiii < json_list02010101.size(); iiii++) {
										WeVoteResult wvr = new WeVoteResult();
										/* 学员LOGIN_ID-昵称 */
										String nickName = ((JSONObject) json_list02010101.get(iiii)).containsKey("nickname") ? ((JSONObject) json_list02010101
												.get(iiii)).getString("nickname")
												: "";
										wvr.setWvrNickname(nickName);
										/* 回答（当非选择题） */
										String answer = ((JSONObject) json_list02010101.get(iiii)).containsKey("answer") ? ((JSONObject) json_list02010101
												.get(iiii)).getString("answer")
												: "";
										wvr.setWvrAnswer(answer);
										/* 用户ID */
										String uid = ((JSONObject) json_list02010101.get(iiii)).containsKey("uid") ? ((JSONObject) json_list02010101
												.get(iiii)).getString("uid")
												: "";
										wvr.setWvrUid(uid);
										/* 问题值ID */
										wvr.setWvrWvoId(wvo.getWvoId());
										try {
											wvr = (WeVoteResult) this.save(wvr);
										} catch (EntityException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											return 21;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		/* 直播问卷结果 */
		JSONArray json_list03 = (JSONArray) map3.get("surveyList");
		if (json_list03 != null) {
			for (int i = 0; i < json_list03.size(); i++) {
				WeSurveySurveylist wss = new WeSurveySurveylist();
				/* 问卷主题 */
				String subject = ((JSONObject) json_list03.get(i)).containsKey("subject") ? ((JSONObject) json_list03.get(i))
						.getString("subject") : "";
				wss.setWssSubject(subject);
				/* 回答时间 */
				String submitTime = ((JSONObject) json_list03.get(i)).containsKey("submitTime") ? ((JSONObject) json_list03.get(i))
						.getString("submitTime") : "";
				wss.setWssSubmittime(submitTime);
				/* 直播ID */
				wss.setWssWebcastid(webcastId);
				/* 采集时间 */
				wss.setWssDate(new Date());
				try {
					wss = (WeSurveySurveylist) this.save(wss);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 31;
				}
				JSONArray json_list0301 = json_list03.getJSONObject(i).getJSONArray("questions");
				if (json_list0301 != null) {
					for (int ii = 0; ii < json_list0301.size(); ii++) {
						WeSurveyQuestion wsq = new WeSurveyQuestion();
						/* 问题 */
						String question = ((JSONObject) json_list0301.get(ii)).containsKey("question") ? ((JSONObject) json_list0301
								.get(ii)).getString("question") : "";
						wsq.setWsqQuestion(question);
						/* 上一步save失败导致没有ID */
						if (wss.getWssId() == null || "".equals(wss.getWssId())) {
							return 31;
						}
						/* 问卷结果表_主题id */
						wsq.setWsqWssId(wss.getWssId());
						try {
							wsq = (WeSurveyQuestion) this.save(wsq);
							/* 上一步save失败导致没有ID */
							if (wsq.getWsqId() == null || "".equals(wsq.getWsqId())) {
								return 31;
							}
						} catch (EntityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return 31;
						}
						JSONArray json_list030101 = json_list0301.getJSONObject(ii).getJSONArray("responses");
						if (json_list030101 != null) {
							for (int iii = 0; iii < json_list030101.size(); iii++) {
								WeSurveyResponse wsr = new WeSurveyResponse();
								/* 学员LOGIN_ID-用户名字 */
								String name = ((JSONObject) json_list030101.get(iii)).containsKey("name") ? ((JSONObject) json_list030101
										.get(iii)).getString("name") : "";
								wsr.setWsrName(name);
								/* 用户ID */
								String uid = ((JSONObject) json_list030101.get(iii)).containsKey("uid") ? ((JSONObject) json_list030101
										.get(iii)).getString("uid") : "";
								wsr.setWsrUid(uid);
								/* 回答 */
								String response = ((JSONObject) json_list030101.get(iii)).containsKey("response") ? ((JSONObject) json_list030101
										.get(iii)).getString("response")
										: "";
								wsr.setWsrResponse(response);
								/* 问题id */
								wsr.setWsrWsqId(wsq.getWsqId());
								try {
									wsr = (WeSurveyResponse) this.save(wsr);
								} catch (EntityException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return 31;
								}
							}
						}
					}
				}
			}
		}
		/* 直播用户提问数据 */;
		JSONArray json_list04 = (JSONArray) map4.get("qaList");
		if (json_list04 != null) {
			if (json_list04 != null) {
				for (int i = 0; i < json_list04.size(); i++) {
					WeQA wq = new WeQA();
					/* 提问时间 */
					String submitTime = ((JSONObject) json_list04.get(i)).containsKey("submitTime") ? ((JSONObject) json_list04.get(i))
							.getString("submitTime") : "";
					wq.setWqSubmittime(submitTime);
					/* 是否发布 */
					String published = ((JSONObject) json_list04.get(i)).containsKey("published") ? ((JSONObject) json_list04.get(i))
							.getString("published") : "";
					wq.setWqPublished(published);
					/* 问题 */
					String question = ((JSONObject) json_list04.get(i)).containsKey("question") ? ((JSONObject) json_list04.get(i))
							.getString("question") : "";
					wq.setWqQuestion(question);
					/* 回答 */
					String response = ((JSONObject) json_list04.get(i)).containsKey("response") ? ((JSONObject) json_list04.get(i))
							.getString("response") : "";
					wq.setWqResponse(response);
					/* 提问者UserID */
					String submitter = ((JSONObject) json_list04.get(i)).containsKey("submitter") ? ((JSONObject) json_list04.get(i))
							.getString("submitter") : "";
					wq.setWqSubmitter(submitter);
					/* 回答者UserID */
					String answerBy = ((JSONObject) json_list04.get(i)).containsKey("answerBy") ? ((JSONObject) json_list04.get(i))
							.getString("answerBy") : "";
					wq.setWqAnswerby(answerBy);
					/* 回答用户的名字 */
					String name = ((JSONObject) json_list04.get(i)).containsKey("name") ? ((JSONObject) json_list04.get(i))
							.getString("name") : "";
					wq.setWqName(name);
					/* 文档中无此字段 */
					// responder
					/* 直播id */
					wq.setWqWebcastid(webcastId);
					/* 采集日期 */
					wq.setWqDate(new Date());
					try {
						wq = (WeQA) this.save(wq);
					} catch (EntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return 41;
					}
				}
			}
		}
		/* 直播点名签到数据 */
		JSONArray json_list05 = (JSONArray) map5.get("rollcall");
		if (json_list05 != null) {
			for (int i = 0; i < json_list05.size(); i++) {
				WeRollcall wr = new WeRollcall();
				/* 开始点名时间 */
				String startTime = ((JSONObject) json_list05.get(i)).containsKey("startTime") ? ((JSONObject) json_list05.get(i))
						.getString("startTime") : "";
				wr.setWrStarttime(startTime);
				/* 结束点名时间 */
				String endTime = ((JSONObject) json_list05.get(i)).containsKey("endTime") ? ((JSONObject) json_list05.get(i))
						.getString("endTime") : "";
				wr.setWrEndtime(endTime);
				/* 总数 */
				String total = ((JSONObject) json_list05.get(i)).containsKey("total") ? ((JSONObject) json_list05.get(i))
						.getString("total") : "";
				wr.setWrTotal(total);
				/* 出席人数 */
				String present = ((JSONObject) json_list05.get(i)).containsKey("present") ? ((JSONObject) json_list05.get(i))
						.getString("present") : "";
				wr.setWrPresent(present);
				/* 缺席人数 */
				String absent = ((JSONObject) json_list05.get(i)).containsKey("absent") ? ((JSONObject) json_list05.get(i))
						.getString("absent") : "";
				wr.setWrAbsent(absent);
				/* 直播id */
				wr.setWrWebcastid(webcastId);
				/* 采集日期 */
				wr.setWrDate(new Date());
				try {
					wr = (WeRollcall) this.save(wr);
					/* 上一步save失败导致没有ID */
					if (wr.getWrId() == null || "".equals(wr.getWrId())) {
						return 51;
					}
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 51;
				}
				JSONArray json_list0501 = ((JSONObject) json_list05.get(i)).getJSONArray("users");
				// (JSONArray) map5.get("users");
				if (json_list0501 != null) {
					for (int ii = 0; ii < json_list0501.size(); ii++) {
						WeRollcallUser wru = new WeRollcallUser();
						/* 用户ID */
						String id = ((JSONObject) json_list0501.get(ii)).containsKey("id") ? ((JSONObject) json_list0501.get(ii))
								.getString("id") : "";
						wru.setWruUid(id);
						/* 用户名字 */
						String name = ((JSONObject) json_list0501.get(ii)).containsKey("name") ? ((JSONObject) json_list0501.get(ii))
								.getString("name") : "";
						wru.setWruName(name);
						/* 该用户是否出席(true|false) */
						String pre = ((JSONObject) json_list0501.get(ii)).containsKey("present") ? ((JSONObject) json_list0501.get(ii))
								.getString("present") : "";
						wru.setWruPresent(pre);
						/* 签到表ID */
						wru.setWruWrId(wr.getWrId());
						try {
							wru = (WeRollcallUser) this.save(wru);
						} catch (EntityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return 51;
						}
					}
				}
			}
		}
		return 0;
	}

	@Override
	public int cleanDataSource(String id) {
		try {
			/* 直播用户访问历史记录表 */
			String sql = "DELETE FROM WE_HISTORY WHERE WH_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "')";
			this.executeBySQL(sql);
			/* 直播用户提问数据表 */
			sql = "DELETE FROM WE_QA WHERE WQ_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "')";
			this.executeBySQL(sql);
			/* 直播用户点名签到明细表_USERS */
			sql = "DELETE FROM WE_ROLLCALL_USERS WHERE WRU_WR_ID IN (SELECT WR_ID FROM WE_ROLLCALL WHERE WR_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '"
					+ id + "'))";
			this.executeBySQL(sql);
			/* 直播用户点名签到表 */
			sql = "DELETE FROM WE_ROLLCALL WHERE WR_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "')";
			this.executeBySQL(sql);
			/* 直播用户问卷结果表_回答 */
			sql = "DELETE FROM WE_SURVEY_RESPONSE WHERE WSR_WSQ_ID IN (SELECT WSQ_ID FROM WE_SURVEY_QUESTIONS WHERE WSQ_WSS_ID IN (SELECT WSS_ID FROM WE_SURVEY_SURVEYLIST WHERE WSS_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '"
					+ id + "')))";
			this.executeBySQL(sql);
			/* 直播用户问卷结果表_问题 */
			sql = "DELETE FROM WE_SURVEY_QUESTIONS WHERE WSQ_WSS_ID IN (SELECT WSS_ID FROM WE_SURVEY_SURVEYLIST WHERE WSS_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '"
					+ id + "'))";
			this.executeBySQL(sql);
			/* 直播用户问卷结果表_主题 */
			sql = "DELETE FROM WE_SURVEY_SURVEYLIST WHERE WSS_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "')";
			this.executeBySQL(sql);
			/* 直播投票结果表_问题的回答 */
			sql = "DELETE FROM WE_VOTE_RESULTS WHERE WVR_WVO_ID IN (SELECT WVO_ID FROM WE_VOTE_OPTIONS WHERE WVO_WVQ_ID IN (SELECT WVQ_ID FROM WE_VOTE_QUESTIONS WHERE WVQ_WVV_ID IN (SELECT WVV_ID FROM WE_VOTE_VOTELIST WHERE WVV_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '"
					+ id + "'))))";
			this.executeBySQL(sql);
			/* 直播投票结果表_问题的值 */
			sql = "DELETE FROM WE_VOTE_OPTIONS WHERE WVO_WVQ_ID IN (SELECT WVQ_ID FROM WE_VOTE_QUESTIONS WHERE WVQ_WVV_ID IN (SELECT WVV_ID FROM WE_VOTE_VOTELIST WHERE WVV_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '"
					+ id + "')))";
			this.executeBySQL(sql);
			/* 直播投票结果表_问题内容 */
			sql = "DELETE FROM WE_VOTE_QUESTIONS WHERE WVQ_WVV_ID IN (SELECT WVV_ID FROM WE_VOTE_VOTELIST WHERE WVV_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '"
					+ id + "'))";
			this.executeBySQL(sql);
			/* 直播投票结果表_投票的主题 */
			sql = "DELETE FROM WE_VOTE_VOTELIST WHERE WVV_WEBCASTID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "')";
			this.executeBySQL(sql);
			/* 在线直播采集数据整理表 */
			sql = "DELETE FROM SAC_LIVE_GETDATA WHERE LIVE_ID IN (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "')";
			this.executeBySQL(sql);
			/* 修改直播信息表中采集数据状态 */
			sql = "UPDATE PE_BZZ_TCH_COURSE SET FLAG_LIVEDATA = 'FlagLiveData0' WHERE ID = '" + id + "'";
			this.executeBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	public boolean getIsEqual(String loginId, String appointId) {
		if (loginId.equals(appointId)) {
			return true;
		}
		return false;
	}

	/**
	 * 快钱订单状态
	 */
	@Override
	public void check99BillOrder(String merorderid) throws EntityException {
		Payment99BillUtil pu = new Payment99BillUtil();// 支付工具类
		List<GatewayOrderDetail> onlineOrderList = null;
		try {
			onlineOrderList = pu.checkOrderState(merorderid);
			if (onlineOrderList != null && onlineOrderList.size() > 0) {
				for (GatewayOrderDetail o : onlineOrderList) {
					if (null != o.getPayResult() && "10".equals(o.getPayResult()) && merorderid.equals(o.getOrderId())) {// 支付成功
						DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
						orderDc.add(Restrictions.eq("seq", merorderid));
						PeBzzOrderInfo pbo = null;
						List orderList = this.getList(orderDc);
						if (orderList != null && orderList.size() > 0) {
							pbo = (PeBzzOrderInfo) orderList.get(0);
							/** 更新订单、支付方式 */
							// pbo.setPaymentMethod(o.getPayType());
							if (Double.parseDouble(pbo.getAmount()) == (double) o.getPayAmount() / 100) {
								confirmOnlineOrder(merorderid); // 确认订单
							} else {
								System.out.println("订单:" + merorderid + "金额不符--" + pbo.getAmount() + "-" + (double) o.getPayAmount() + "--"
										+ (new Date().toString()));
								throw new EntityException("订单金额不符，请联系管理员！");
							}
						} else {
							throw new EntityException("返回订单查询错误，请联系管理员！");
						}

					}
				}
			}
		} catch (EntityException e) {
			throw e;
		}
	}

	/**
	 * 根据订单号更新发票为已开
	 * 
	 * @param list
	 * @throws EntityException
	 */
	public void uptInvoiceState(List<Map> list) throws EntityException {
		if (null != list && list.size() > 0) {
			for (Map map : list) {
				List invoiceList = null;
				String invoiceSql = " SELECT * FROM PE_BZZ_INVOICE_INFO pbii WHERE pbii.invoice_num = '" + map.get("FPLSH") + "'";
				invoiceList = this.getBySQL(invoiceSql);
				if (null != invoiceList && invoiceList.size() > 1) {
					String uptSql = "UPDATE PE_BZZ_INVOICE_INFO PBII " + "SET PBII.FLAG_FP_OPEN_STATE = "
							+ "(SELECT ID FROM ENUM_CONST WHERE NAMESPACE = 'FlagFpOpenState' AND CODE = '1'), "
							+ "PBII.NUM = '" + map.get("FPHM") + "'," + "PBII.FPDM_NUM = '" + map.get("FPDM")
							+ "', " + "PBII.FPJYM_NUM = '" + map.get("FPJYM") + "', " + "PBII.OPEN_DATE = TO_DATE('"
							+ map.get("KPRQ") + "','yyyy-mm-dd hh24:mi:ss') " + "WHERE PBII.INVOICE_NUM = '" + map.get("FPLSH") + "'";
					this.executeBySQL(uptSql);
				} else {
					String uptSql = "UPDATE PE_BZZ_INVOICE_INFO PBII " + "SET PBII.FLAG_FP_OPEN_STATE = "
							+ "(SELECT ID FROM ENUM_CONST WHERE NAMESPACE = 'FlagFpOpenState' AND CODE = '1'), "
							+ "PBII.NUM = PBII.NUM ||'" + map.get("FPHM") + ",'," + "PBII.FPDM_NUM = PBII.FPDM_NUM ||'" + map.get("FPDM")
							+ ",', " + "PBII.FPJYM_NUM = PBII.FPJYM_NUM ||'" + map.get("FPJYM") + ",', " + "PBII.OPEN_DATE = TO_DATE('"
							+ map.get("KPRQ") + "','yyyy-mm-dd hh24:mi:ss') " + "WHERE PBII.INVOICE_NUM = '" + map.get("FPLSH") + "'";
					this.executeBySQL(uptSql);
				}
			}
		}
	}
}
