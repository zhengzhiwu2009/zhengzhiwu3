package com.whaty.platform.entity.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate4.HibernateTemplate;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.CourseInfo;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.IndustryTeacherInfo;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzAssess;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzCertificate;
import com.whaty.platform.entity.bean.PeBzzCourseFeedback;
import com.whaty.platform.entity.bean.PeBzzEditStudent;
import com.whaty.platform.entity.bean.PeBzzExamBatch;
import com.whaty.platform.entity.bean.PeBzzExamBatchSite;
import com.whaty.platform.entity.bean.PeBzzExamLate;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzExamSite;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzRecruit;
import com.whaty.platform.entity.bean.PeBzzRecruitBefore;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzSuggestion;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBackHistory;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveHistory;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.bean.TrainingCourseStudentHistory;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.bean.PePriRole;

public interface AssignDao<T extends AbstractBean> {
	/**
	 * 保存
	 * 
	 * @param transientInstance
	 * @return
	 */
	public T save(T transientInstance);

	/**
	 * 保存
	 * 
	 * @param transientInstance
	 * @return
	 */
	public Object save(Object transientInstance);

	/**
	 * 根据id列表 删除
	 * 
	 * @param ids
	 */
	public int deleteByIds(final List ids);

	/**
	 * 删除单个
	 * 
	 * @param persistentInstance
	 */
	public void delete(T persistentInstance);

	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 */
	public T getById(String id);

	/**
	 * 根据样例
	 * 
	 * @param instance
	 * @return
	 */
	public List getByExample(final T instance);

	/**
	 * 根据条件查找
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	public List getList(final DetachedCriteria detachedCriteria);

	/**
	 * 根据条件分页获得
	 */
	public Page getByPage(final DetachedCriteria detachedCriteria, final int pageSize, final int startIndex);

	/**
	 * 批量更新一个字段为某??
	 * 
	 * @param ids
	 * @param column
	 * @param value
	 * @return
	 */
	public int updateColumnByIds(final List ids, final String column, final String value);

	/**
	 * 使用sql查询的接口
	 * 
	 * @param sql
	 * @return
	 */
	public List getBySQL(final String sql);

	/**
	 * 根据sql分页获得
	 * 
	 * @param sql
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public Page getByPageSQL(String sql, int pageSize, int startIndex);

	public List getByHQL(final String hql);

	public int executeByHQL(final String hql);

	public int executeBySQL(final String sql);

	public EnumConst getEnumConstByNamespaceCode(String namespace, String code);

	public EnumConst getEnumConstByNamespaceName(String namespace, String name);

	public void setEntityClass(Class entityClass);

	public HibernateTemplate getMyHibernateTemplate();

	public PeBulletin getPeBulletin(String id);

	public PeInfoNews getPeInfoNews(String id);

	public List getNewBulletins(String id);

	public PeSitemanager getPeSitemanager(String loginId);

	public PeEnterprise getSubEnterprise(String id);

	public List getStuBulletins();

	public List getExamSite();

	public PeEnterpriseManager getEnterprisemanager(String loginId);

	public void saveSsoUser(SsoUser sso);

	public void delete(SsoUser ssoUser);

	public PeBzzStudent getStudentInfo(DetachedCriteria studc);

	public IndustryTeacherInfo getTeacherInfo(DetachedCriteria studc);

	public CourseInfo getCourseInfo(DetachedCriteria studc);

	public PeBzzExamBatch getExamBatch(DetachedCriteria studc);

	public PeBzzRecruitBefore getPeBzzRecruitBefore(DetachedCriteria studc);

	public PeBzzExamScore getExamScore(DetachedCriteria studc);

	public PeBzzTchCourse getPeBzzTchCourse(DetachedCriteria studc);

	public PeBzzCertificate getCertificate(DetachedCriteria studc);

	public PeBzzEditStudent getEditStudent(DetachedCriteria studc);

	public PeBzzExamLate getExamLate(DetachedCriteria studc);

	public PeBzzExamSite getExamSite(DetachedCriteria studc);

	public PeBzzExamBatchSite getExamBatchSite(DetachedCriteria studc);

	public List getExamBatchSites(DetachedCriteria studc);

	public List getExamScores(DetachedCriteria studc);

	public PrBzzTchOpencourse getPrBzzTchOpencourse(DetachedCriteria pdc);

	public List<PeBzzCourseFeedback> getFeeDbackList(DetachedCriteria feeDback);

	public PeBzzSuggestion getPeBzzSuggestion(String sugid);

	public void updatepeBzzSuggestion(PeBzzSuggestion peBzzSuggestion);

	public void updatePeBzzAssess(PeBzzAssess assess);

	public void updateSsoUser(SsoUser ssoUser);

	public void updatePeEnterpriseManager(PeEnterpriseManager enterpriseManager);

	public void updatePeManager(PeManager peManager);

	public void updateEnterpriseManager(PeEnterpriseManager enterpriseManager);

	public void updateelective(TrainingCourseStudent trainingCourseStudent);

	public PeBzzBatch getPeBzzBatch(DetachedCriteria criteria);

	public void update(T persistentInstance);

	public void detachedSave(T detachedInstance);

	public void detachedSave(Object detachedInstance);

	public List getDetachList(DetachedCriteria detachedCriteria);

	public void saveTest(T entity, String id);

	public PeBzzRecruit getBzzRecruitStudent(DetachedCriteria studc);

	public PeBzzExamBatchSite savePeBzzExamBatchSite(PeBzzExamBatchSite transientInstance);

	public int deleteByIds(final Class clazz, final List siteIds, final List ids);

	public T getById(final Class entityClass, final List siteIds, final String id) throws EntityException;

	public T getById(final Class entityClass, final String id) throws EntityException;

	public int deleteByIds(final Class clazz, final List ids);

	public PeBzzOrderInfo SavePeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo);

	public PeBzzInvoiceInfo SavePeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo);

	public PePriRole getPePriRole(String id);

	public Page getByPageSQLDBPool(String sqlNum, String sqlList, int pageSize, int startIndex);

	/**
	 * 清除缓存中对象
	 * 
	 * @param obj
	 * @return
	 */
	public TrainingCourseStudentHistory clearSession(TrainingCourseStudentHistory obj);

	public PrBzzTchStuElectiveHistory clearSession(PrBzzTchStuElectiveHistory obj);

	public PrBzzTchStuElectiveBackHistory clearSession(PrBzzTchStuElectiveBackHistory obj);

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
	 * 传参调用存储过程
	 * 
	 * @param string
	 * @param paramList
	 * @throws Exception
	 */
	public void executeCall(String string, List paramList) throws Exception;
}
