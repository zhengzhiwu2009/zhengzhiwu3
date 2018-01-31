
package com.whaty.platform.entity.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.bill99.seashell.domain.dto.gatewayquery.GatewayOrderDetail;
import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.AutoRefund;
import com.whaty.platform.entity.bean.CourseInfo;
import com.whaty.platform.entity.bean.IndustryTeacherInfo;
import com.whaty.platform.entity.bean.OnlineOrderInfo;
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
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.sso.web.servlet.UserSession;

public interface GeneralService<T extends AbstractBean> {

	public T save(T transientInstance) throws EntityException;

	public Object save(Object transientInstance) throws EntityException;

	/**
	 * 批量保存
	 * 
	 * @param list
	 * @return
	 * @throws EntityException
	 */
	public List saveList(List list) throws EntityException;

	public void delete(T persistentInstance) throws EntityException;

	public int deleteByIds(List ids) throws EntityException;

	public int updateColumnByIds(List ids, String column, String value) throws EntityException;

	public T getById(String id) throws EntityException;

	public PeInfoNews getPeInfoNews(String id) throws EntityException;

	public PeBulletin getPeBulletin(String id) throws EntityException;

	public List getByExample(final T instance) throws EntityException;

	public List getList(DetachedCriteria detachedCriteria) throws EntityException;

	public Page getByPage(DetachedCriteria detachedCriteria, int pageSize, int startIndex) throws EntityException;

	public List getBySQL(final String sql) throws EntityException;

	public Page getByPageSQL(String sql, int pageSize, int startIndex) throws EntityException;

	public List getByHQL(final String hql) throws EntityException;

	public int executeByHQL(final String hql) throws EntityException;

	public int executeBySQL(final String sql) throws EntityException;

	public void saveError() throws EntityException;

	public GeneralDao<T> getGeneralDao();

	public List getNewBulletins(String id) throws EntityException;

	/**
	 * @getDetachList(DetachedCriteria detachedCriteria)
	 * @该方法返回是托管状态的List!
	 */
	public List getDetachList(DetachedCriteria detachedCriteria) throws EntityException;

	public PeEnterprise getSubEnterprise(String id) throws EntityException;;

	public List getStuBulletins() throws EntityException;;

	public List getExamSite() throws EntityException;;

	public PeBzzStudent getStudentInfo(DetachedCriteria studc) throws EntityException;;

	public PeBzzExamBatch getExamBatch(DetachedCriteria studc) throws EntityException;;

	public PeBzzExamScore getExamScore(DetachedCriteria studc) throws EntityException;;

	public PeBzzTchCourse getPeBzzTchCourse(DetachedCriteria studc) throws EntityException;;

	public IndustryTeacherInfo getTeacherInfo(DetachedCriteria studc) throws EntityException;;

	public CourseInfo getCourseInfo(DetachedCriteria studc) throws EntityException;;

	public PeBzzCertificate getCertificate(DetachedCriteria studc) throws EntityException;;

	public PeBzzEditStudent getEditStudent(DetachedCriteria studc) throws EntityException;;

	public PeBzzRecruitBefore getPeBzzRecruitBefore(DetachedCriteria studc) throws EntityException;;

	public PeBzzExamLate getExamLate(DetachedCriteria studc) throws EntityException;;

	public PeBzzExamSite getExamSite(DetachedCriteria studc) throws EntityException;;

	public PeBzzExamBatchSite getExamBatchSite(DetachedCriteria studc) throws EntityException;;

	public List getExamBatchSites(DetachedCriteria studc) throws EntityException;;

	public List getExamScores(DetachedCriteria studc) throws EntityException;;

	public void update(T persistentInstance) throws EntityException;;

	public void updateSsoUser(SsoUser ssoUser) throws EntityException;;

	public void updatePeEnterpriseManager(PeEnterpriseManager enterpriseManager) throws EntityException;;

	public void updatePeManager(PeManager peManager) throws EntityException;;

	public void updateEnterpriseManager(PeEnterpriseManager enterpriseManager) throws EntityException;

	public PeBzzExamBatchSite savePeBzzExamBatchSite(PeBzzExamBatchSite batch_site) throws EntityException;

	public int deleteByIds(Class clazz, List siteIds, List ids) throws EntityException;

	public T getById(Class entityClass, List siteIds, String id) throws EntityException;

	public T getById(Class entityClass, String id) throws EntityException;

	public PeBzzOrderInfo getOrderBySeq(String seq) throws EntityException;

	public String getOrderSeq() throws EntityException;

	public List saveElectiveList(List electivelist, PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, SsoUser ssoUser)
			throws EntityException;

	public List saveElectiveList(List electivelist, PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, SsoUser ssoUser,
			PeElectiveCoursePeriod peElectiveCoursePeriod) throws EntityException;

	public List saveElectiveListBatch(List electivelist, PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, SsoUser ssoUser,
			List<StudentBatch> listSb) throws EntityException;

	public List<PrBzzTchStuElectiveBack> saveElectiveBackListBatch(List<PrBzzTchStuElectiveBack> electiveBackList,
			PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, SsoUser user, List<StudentBatch> listSb)
			throws EntityException;

	/**
	 * 网银支付时，首先产生的订单先存放在备份表中
	 * 
	 * @param electiveBacklist
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 * @param ssoUser
	 * @param peElectiveCoursePeriod
	 * @return
	 * @throws EntityException
	 */
	public List<PrBzzTchStuElectiveBack> saveElectiveBackList(List<PrBzzTchStuElectiveBack> electivelist, PeBzzOrderInfo peBzzOrderInfo,
			PeBzzInvoiceInfo peBzzInvoiceInfo, SsoUser ssoUser, PeElectiveCoursePeriod peElectiveCoursePeriod, List<StudentBatch> sbList)
			throws EntityException;

	public List<PrBzzTchStuElectiveBack> updateElectiveBackList(List<PrBzzTchStuElectiveBack> electiveBacklist,
			PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, PeElectiveCoursePeriod peElectiveCoursePeriod, SsoUser user)
			throws EntityException;

	public PeEnterpriseManager getEnterpriseManagerByLoginId(String loginId) throws EntityException;

	/**
	 * 获取默认隐藏专项
	 * 
	 * @return
	 * @throws EntityException
	 */
	public PeBzzBatch getDefaultPeBzzBatch() throws EntityException;

	public void deleteElective(PrBzzTchStuElective prBzzTchStuElective) throws EntityException;

	public Page getByPageSQLDBPool(String sqlNum, String sqlList, int pageSize, int startIndex) throws EntityException;

	public String getSignUpOrderSeq() throws EntityException;

	public PeBzzOrderInfo updatePeBzzOrderInfo(PeBzzOrderInfo order, String string, SsoUser ssoUser) throws EntityException;

	public SsoUser initSsoUser(UserSession us) throws EntityException;

	public void transferIncompleteRecord(String studentId, String openCourseId) throws EntityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException;

	public String checkElective(PrBzzTchStuElectiveBack eleBack);

	public void executeRefund(PeBzzRefundInfo peBzzRefundInfo) throws EntityException;

	public void checkRefundPermission(PeBzzOrderInfo peBzzOrderInfo) throws EntityException;

	public Page getOrderDetail(PeBzzOrderInfo order, SsoUser ssoUser, int pageSize, int startIndex) throws EntityException;

	public Page getOrderDetailSub(PeBzzOrderInfo order, SsoUser ssoUser, int pageSize, int startIndex) throws EntityException;

	public void closeOrder(PeBzzOrderInfo peBzzOrderInfo) throws Exception;

	public void closeOrderList(List orderIdList, SsoUser ssoUser) throws Exception;

	public AutoRefund refundOnlineApply(String merorderid, String appuser, String cause, String amountsum) throws EntityException;

	public AutoRefund refundOnline99BillApply(String merorderid, String appuser, String cause, String amountsum) throws EntityException;

	public String confirmRefund(String merorderid, String refundId) throws EntityException;

	public String confirm99BillRefund(String merorderid, String refundId) throws EntityException;

	public PeBzzRefundInfo initOnlineRefund(PeBzzRefundInfo peBzzRefundInfo) throws EntityException;

	public void checkOnlineOrder(String merorderid) throws EntityException;

	public void check99BillOrder(String merorderid) throws EntityException;

	public PeBzzOrderInfo confirmOnlineOrder(String merorderid) throws EntityException;
	
	//TODO YCL 2016-03-15 到账方法重载：增加支付方式类型 star 
	public PeBzzOrderInfo confirmOnlineOrder(String merorderid, String paymentMethod) throws EntityException;
	// end

	// public void reconciliation(OnlineOrderInfo onlineOrderInfo);

	public void checkReconciliation(String s, String e, String string) throws EntityException;

	public boolean reconciliation(OnlineOrderInfo onlineOrderInfo);
	
	public boolean reconciliation(GatewayOrderDetail detail);
	
	public boolean unReconciliation(OnlineOrderInfo onlineOrderInfo);

	public PeBzzInvoiceInfo getLastInvoice(String string) throws EntityException;
	
	public PeBzzInvoiceInfo getLastInvoice(String string,String order_id) throws EntityException;

	/**
	 * operateType 操作类型 operateStatus 操作状态 opcontent 操作内容 opkey 热键 opip IP
	 */

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
	// paramsList) throws Exception;
	//
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
	// startIndex) throws Exception;
	//
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
	// * @return 查询结果Page
	// * @throws Exception
	// */
	// public Page getByPageSQL(String sql, List<String> params, int pageSize,
	// int startIndex) throws Exception;
	/**
	 * 保存多次学习记录
	 * 
	 * @param tcs
	 *            学习记录实体
	 * @throws Exception
	 */
	public void saveTrainingHis(String userId, Object courseId, Object opencourseId, Date startTime, Date endTime) throws Exception;

	/**
	 * 手动调用方法 记录日志
	 */
	public abstract void saveLoginfo(String executeDetail, String userId, String modeType, String loginPost, String writeValue,
			String ipAdress, String url) throws NoRightException, PlatformException;

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
	public int saveLiveTime(List<StringBuffer> tcsInsert, String pbtseUpdate) throws Exception;

	/**
	 * 采集并保存数据
	 * 
	 * @param id
	 * @return
	 */
	public int saveDataSource(String id);

	/**
	 * 采集返回数据
	 * 
	 * @param service_host
	 * @param service_method
	 * @param service_params
	 * @return
	 */
	public Map getReturnMap(String service_host, String service_method, String service_params);

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
	public int saveDatatoDB(String sacLiveId, String webcastId, Map map1, Map map2, Map map3, Map map4, Map map5);

	public int cleanDataSource(String id);

	public boolean getIsEqual(String loginId, String appointId) throws Exception;
	
	/**
	 * 根据订单号更新发票为已开
	 * @param list
	 */
	public void uptInvoiceState(List<Map> list) throws EntityException;
}
