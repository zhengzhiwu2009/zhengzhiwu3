package com.whaty.platform.entity.dao.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ParameterMode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
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
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBackHistory;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveHistory;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.bean.TrainingCourseStudentHistory;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;

public class GeneralHibernateDao<T extends AbstractBean> extends HibernateDaoSupport implements GeneralDao<T> {

	private Class entityClass;

	private static final Log log = LogFactory.getLog(GeneralHibernateDao.class);

	/**
	 * 保存
	 */
	public T save(T transientInstance) {
		log.debug("saving  instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("添加成功");
			return transientInstance;
		} catch (RuntimeException re) {
			log.error("添加失败", re);
			throw re;
		}
	}

	/**
	 * 保存
	 */
	public Object save(Object transientInstance) {
		log.debug("saving  instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("添加成功");
			return transientInstance;
		} catch (RuntimeException re) {
			log.error("添加失败", re);
			throw re;
		}
	}

	public PeBzzExamBatchSite savePeBzzExamBatchSite(PeBzzExamBatchSite transientInstance) {
		log.debug("saving  instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("添加成功");
			return transientInstance;
		} catch (RuntimeException re) {
			log.error("添加失败", re);
			throw re;
		}
	}

	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 */
	public T getById(String id) {
		try {
			T instance = (T) getHibernateTemplate().get(this.entityClass, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("查找失败", re);
			throw re;
		}
	}

	/**
	 * 根据样例查找
	 * 
	 * @param instance
	 * @return
	 */
	public List getByExample(final T instance) {
		try {

			return (List) this.getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					List results = session.createCriteria(entityClass.getName()).add(Example.create(instance)).list();
					log.debug("根据样例查找成功, result size: " + results.size());
					return results;
				}
			});
		} catch (RuntimeException re) {
			log.error("根据样例查找失败", re);
			throw re;
		}
	}

	/**
	 * 根据id列表 删除
	 */

	public int deleteByIds(final List ids) {

		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {

				int i = 0;

				Query query = session.createQuery("delete from " + entityClass.getName() + " where id in(:ids)");
				query.setParameterList("ids", ids);

				try {
					i = query.executeUpdate();
				} catch (RuntimeException re) {
					i = 0;
					// log.error("批量删除失败", re);
					for (Object object : ids) {
						try {
							T instance = getById((String) object);
							delete(instance);
							i++;
						} catch (RuntimeException re1) {
							log.error("单个删除失败", re1);
							throw re1;
						}
					}
				}

				return i;
			}
		});

	}

	/**
	 * 根据条件分页获得
	 */
	public Page getByPage(final DetachedCriteria detachedCriteria, final int pageSize, final int startIndex) {
		
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);

		CriteriaImpl impl = (CriteriaImpl) criteria;
		// 鍏堟妸Projection鍜孫rderBy鏉′欢鍙栧嚭锟�?娓呯┖涓よ�呮潵鎵цCount鎿嶄綔
		Projection projection = impl.getProjection();

		Integer totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

		// 灏嗕箣鍓嶇殑Projection鍜孫rderBy鏉′欢閲嶆柊璁惧洖锟�?
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		List<T> items = null;

		try {
			items = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
		} catch (RuntimeException re) {
			log.error("分页查询失败");
			throw re;
		}

		Page pg = new Page(items, totalCount, pageSize, startIndex);
		return pg;
	}

	/**
	 * 根据条件查找
	 */
	public List getList(final DetachedCriteria detachedCriteria) {
		return (List) this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	/**
	 * 删除单个
	 */
	public void delete(T persistentInstance) {
		try {
			getHibernateTemplate().delete(persistentInstance);
			// getHibernateTemplate().flush();
			log.debug("删除成功");
		} catch (RuntimeException re) {
			log.error("删除失败", re);
			throw re;
		}

	}

	public HibernateTemplate getMyHibernateTemplate() {
		return this.getHibernateTemplate();
	}

	/**
	 * 批量更新一个字段
	 */
	public int updateColumnByIds(final List ids, final String column, final String value) {

		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException {

				int count = 0;
				String[] columns = column.split(",");
				String[] values = value.split(",");
				if (columns.length != values.length) {
					throw new RuntimeException("更新操作 列与值数量不匹配");
				}

				String sqlPrepare = " set";
				for (int i = 0; i < columns.length; i++) {
					sqlPrepare += " n." + columns[i] + " = ? ,";
				}
				sqlPrepare = sqlPrepare.substring(0, sqlPrepare.length() - 1);

				String sql = "update " + entityClass.getName() + " n " + sqlPrepare + " where n.id in(:ids)";
				Query query = s.createQuery(sql);

				for (int j = 0; j < values.length; j++) {
					if (columns[j].toLowerCase().endsWith("date")) {
						query.setDate(j, new Date(Long.parseLong(values[j])));
					} else {
						query.setString(j, values[j]);
					}
				}
				query.setParameterList("ids", ids);

				try {
					count = query.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}

				return count;
			}
		});
	}

	/**
	 * 在service中拼好sql传入，返回的List为数组而非对象
	 */
	public List getBySQL(final String sql) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {

				List list = new ArrayList();

				Query query = session.createSQLQuery(sql);

				try {
					list = query.list();
				} catch (RuntimeException re) {
					log.error("getBySQL 失败", re);
					throw re;
				}

				return list;
			}
		});
	}

	/**
	 * 对多表进行连接查询时，如果查询结果中含有 多个表的相同字段，比如说学生和教师连表查询时， 如果查询结果包含学生和教师的姓名，即都是name字段时，
	 * 查询的SQL语句中必须对所查询的字段起别名
	 */
	public Page getByPageSQL(String sql, int pageSize, int startIndex) {

		List list = new ArrayList();
		Session session = getHibernateTemplate().getSessionFactory().openSession();

		/**
		 * 求总数
		 */
		String countSql = "select count(*) from ( " + sql + " )";

		String mySql = "select * from ( " + "select  a.*, rownum rownum_ from ( " + sql + ") a where rownum <= " + (startIndex + pageSize) + ") b where rownum_ > " + startIndex;
		/**
		 * 首先求出count
		 */

		int totalCount = 0;
		try {
			totalCount = Integer.parseInt(session.createSQLQuery(countSql).list().get(0).toString());
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}

		try {
			list = session.createSQLQuery(mySql).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		Page pg = new Page(list, totalCount, pageSize, startIndex);
		session.clear();
		session.close();
		return pg;
	}

	/**
	 * 用HQL语句查询的方法 张利斌 2008.8.2
	 * 
	 * @param hql
	 * @return
	 */
	public List getByHQL(final String hql) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {

				List list = new ArrayList();

				Query query = session.createQuery(hql);

				try {
					list = query.list();
				} catch (RuntimeException re) {
					log.error("getByHQL 失败", re);
					throw re;
				}

				return list;
			}
		});

	}

	public int executeByHQL(final String hql) {

		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {

				Integer i = new Integer(0);

				Query query = session.createQuery(hql);

				try {
					i = query.executeUpdate();
				} catch (RuntimeException re) {
					log.error("executeByHQL 失败", re);
					throw re;
				}

				return i;
			}
		});
	}

	public int executeBySQL(final String sql) {

		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {

				Integer i = new Integer(0);

				Query query = session.createSQLQuery(sql);

				try {
					i = query.executeUpdate();
				} catch (RuntimeException re) {
					log.error("executeBySQL 失败", re);
					throw re;
				}

				return i;
			}
		});
	}

	public EnumConst getEnumConstByNamespaceCode(String namespace, String code) {
		final String hql = "from EnumConst e where e.namespace='" + namespace + "' and e.code='" + code + "'";
		List list = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				List list = new ArrayList();
				Query query = session.createQuery(hql);

				try {
					list = query.list();
				} catch (RuntimeException re) {
					re.printStackTrace();
				}

				return list;
			}
		});
		if (list != null && list.size() == 1) {
			return (EnumConst) list.get(0);
		}
		return null;

	}

	public EnumConst getEnumConstByNamespaceName(String namespace, String name) {
		final String hql = "from EnumConst e where e.namespace='" + namespace + "' and e.name='" + name + "'";
		List list = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				List list = new ArrayList();
				Query query = session.createQuery(hql);

				try {
					list = query.list();
				} catch (RuntimeException re) {
					re.printStackTrace();
				}

				return list;
			}
		});
		if (list != null && list.size() == 1) {
			return (EnumConst) list.get(0);
		}
		return null;

	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public PeBulletin getPeBulletin(String id) {
		return (PeBulletin) getHibernateTemplate().get(PeBulletin.class, id);
	}

	public PeInfoNews getPeInfoNews(String id) {
		// TODO Auto-generated method stub
		return (PeInfoNews) getHibernateTemplate().get(PeInfoNews.class, id);
	}

	public List getNewBulletins(final String id) {
		List tlist = (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String sql = "select t.title, t.id,t.PUBLISH_DATE from pe_bulletin t where t.scope_string like '%students%' and t.scope_string like (select '%site:' || ps.id || '%' from PE_SITE ps, pe_student pt where pt.fk_site_id = ps.id and pt.fk_sso_user_id = '"
						+ id + "')";
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
		});
		return tlist;
	}

	public PeSitemanager getPeSitemanager(String loginId) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql = "from PeSitemanager pe where pe.loginId='" + loginId + "'";
		Query query = session.createQuery(hql);
		PeSitemanager sitemanager = (PeSitemanager) query.uniqueResult();
		return sitemanager;
	}

	public PeEnterpriseManager getEnterprisemanager(String loginId) {
		// TODO Auto-generated method stub
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql = "from PeEnterpriseManager pe where pe.loginId='" + loginId + "'";
		Query query = session.createQuery(hql);
		PeEnterpriseManager enterprisemanager = (PeEnterpriseManager) query.uniqueResult();
		return enterprisemanager;
	}

	public PeEnterprise getSubEnterprise(final String id) {
		return (PeEnterprise) this.getHibernateTemplate().get(PeEnterprise.class, id);
	}

	public List getExamSite() {
		List list = (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from PeBzzExamSite ";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
		return list;
	}

	public List getStuBulletins() {
		List list = (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from PeBulletin where scopeString like '%students%'";
				Query query = session.createQuery(hql);

				return query.list();
			}
		});
		return list;
	}

	public void saveTest(T entity, String id) {
		System.out.println(this.getHibernateTemplate().get(entity.getClass(), id));
	}

	public void saveSsoUser(SsoUser sso) {
		this.getHibernateTemplate().save(sso);

	}

	public void delete(SsoUser ssoUser) {
		this.getHibernateTemplate().delete(ssoUser);
	}

	public IndustryTeacherInfo getTeacherInfo(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		IndustryTeacherInfo teacherInfo = (IndustryTeacherInfo) criteria.uniqueResult();
		return teacherInfo;
	}
	public CourseInfo getCourseInfo(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		CourseInfo courseInfo = (CourseInfo) criteria.uniqueResult();
		return courseInfo;
	}
	public PeBzzStudent getStudentInfo(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		PeBzzStudent peBzzStudent = (PeBzzStudent) criteria.uniqueResult();
		return peBzzStudent;
	}

	public PeBzzExamBatch getExamBatch(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		PeBzzExamBatch peBzzStudent = (PeBzzExamBatch) criteria.uniqueResult();
		return peBzzStudent;
	}

	public PeBzzRecruitBefore getPeBzzRecruitBefore(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		PeBzzRecruitBefore bzzRecruit = (PeBzzRecruitBefore) criteria.uniqueResult();
		return bzzRecruit;
	}

	public PeBzzExamScore getExamScore(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		PeBzzExamScore peBzzStudent = (PeBzzExamScore) criteria.uniqueResult();
		return peBzzStudent;
	}

	public PeBzzTchCourse getPeBzzTchCourse(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		PeBzzTchCourse peBzzStudent = (PeBzzTchCourse) criteria.uniqueResult();
		return peBzzStudent;
	}

	public PeBzzCertificate getCertificate(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		PeBzzCertificate peBzzStudent = (PeBzzCertificate) criteria.uniqueResult();
		return peBzzStudent;
	}

	public PeBzzEditStudent getEditStudent(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		PeBzzEditStudent peBzzStudent = (PeBzzEditStudent) criteria.uniqueResult();
		return peBzzStudent;
	}

	public PeBzzExamLate getExamLate(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		PeBzzExamLate peBzzStudent = (PeBzzExamLate) criteria.uniqueResult();
		return peBzzStudent;
	}

	public PeBzzExamSite getExamSite(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		PeBzzExamSite peBzzStudent = (PeBzzExamSite) criteria.uniqueResult();
		return peBzzStudent;
	}

	public PeBzzExamBatchSite getExamBatchSite(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		PeBzzExamBatchSite peBzzStudent = (PeBzzExamBatchSite) criteria.uniqueResult();
		return peBzzStudent;
	}

	public List getExamBatchSites(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		List list = criteria.list();
		session.evict(list);
		session.close();
		return list;
	}

	public List getExamScores(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		List list = criteria.list();
		session.evict(list);
		session.close();
		return list;
	}

	public PrBzzTchOpencourse getPrBzzTchOpencourse(DetachedCriteria pdc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = pdc.getExecutableCriteria(session);
		PrBzzTchOpencourse tchOpencourse = (PrBzzTchOpencourse) criteria.uniqueResult();
		return tchOpencourse;
	}

	public List<PeBzzCourseFeedback> getFeeDbackList(DetachedCriteria feeDback) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = feeDback.getExecutableCriteria(session);
		List<PeBzzCourseFeedback> list = criteria.list();
		return list;
	}

	public void updatepeBzzSuggestion(PeBzzSuggestion peBzzSuggestion) {
		this.getHibernateTemplate().update(peBzzSuggestion);
	}

	public PeBzzSuggestion getPeBzzSuggestion(String sugid) {
		return (PeBzzSuggestion) this.getHibernateTemplate().get(PeBzzSuggestion.class, sugid);
	}

	public void updatePeBzzAssess(PeBzzAssess assess) {
		this.getHibernateTemplate().update(assess);
	}

	public void updateSsoUser(SsoUser ssoUser) {
		this.getHibernateTemplate().update(ssoUser);
	}

	public void updatePeEnterpriseManager(PeEnterpriseManager enterpriseManager) {
		this.getHibernateTemplate().update(enterpriseManager);
	}

	public void updatePeManager(PeManager peManager) {
		this.getHibernateTemplate().update(peManager);
	}

	public void updateEnterpriseManager(PeEnterpriseManager enterpriseManager) {
		this.getHibernateTemplate().update(enterpriseManager);
	}

	public void updateelective(PrBzzTchStuElective elective) {
		this.getHibernateTemplate().update(elective);
	}

	public void updateelective(TrainingCourseStudent trainingCourseStudent) {
		this.getHibernateTemplate().update(trainingCourseStudent);
	}

	public PeBzzBatch getPeBzzBatch(DetachedCriteria dCriteria) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = dCriteria.getExecutableCriteria(session);
		PeBzzBatch peBzzBatch = (PeBzzBatch) criteria.uniqueResult();
		return peBzzBatch;
	}

	public void update(T persistentInstance) {
		this.getHibernateTemplate().update(persistentInstance);
	}

	public void detachedSave(T detachedInstance) {
		detachedInstance = (T) this.getHibernateTemplate().merge(detachedInstance);
		this.getHibernateTemplate().saveOrUpdate(detachedInstance);
	}

	public void detachedSave(Object detachedInstance) {
		detachedInstance = (Object) this.getHibernateTemplate().merge(detachedInstance);
		this.getHibernateTemplate().saveOrUpdate(detachedInstance);
	}

	public List getDetachList(final DetachedCriteria detachedCriteria) {

		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		List list = criteria.list();
		try{
			session.evict(list);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		session.close();
		return list;

	}

	public PeBzzRecruit getBzzRecruitStudent(DetachedCriteria studc) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		PeBzzRecruit peBzzRecruit = (PeBzzRecruit) criteria.uniqueResult();
		return peBzzRecruit;
	}

	// @Override
	public int deleteByIds(final Class clazz, final List siteIds, final List ids) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				int i = 0;
				Query query = session.createQuery("delete from " + entityClass.getName() + " where id in(:ids) and peSite.id in(:siteIds)");
				query.setParameterList("ids", ids);
				query.setParameterList("siteIds", siteIds);
				try {
					i = query.executeUpdate();
				} catch (RuntimeException re) {
					i = 0;
					// log.error("批量删除失败", re);
					for (Object object : ids) {
						try {
							T instance = getById(clazz, (String) object);
							delete(instance);
							i++;
						} catch (RuntimeException re1) {
							log.error("单个删除失败", re1);
							throw re1;
						}
					}
				}
				return i;
			}
		});
	}

	// @Override
	public int deleteByIds(final Class clazz, final List ids) {
		// TODO Auto-generated method stub
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				int i = 0;
				// Query query = session.createQuery("delete from "
				// + entityClass.getName() + " where id in(:ids)");
				Query query = session.createQuery("delete from " + clazz.getName() + " where id in(:ids)");
				query.setParameterList("ids", ids);
				// query.setParameterList("siteIds", siteIds);
				try {
					i = query.executeUpdate();
				} catch (RuntimeException re) {
					i = 0;
					// log.error("批量删除失败", re);
					for (Object object : ids) {
						try {
							T instance = getById(clazz, (String) object);
							delete(instance);
							i++;
						} catch (RuntimeException re1) {
							log.error("单个删除失败", re1);
							throw re1;
						}
					}
				}
				return i;
			}
		});
	}

	public T getById(final Class entityClass, final List siteIds, final String id) throws EntityException {
		return (T) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery("from " + entityClass.getName() + " where id in(:id) and peSite.id in(:siteIds)");
				query.setParameter("id", id);
				query.setParameterList("siteIds", siteIds);
				List list = query.list();
				if (list != null && list.size() > 0)
					return list.get(0);
				return null;
			}
		});
	}

	public T getById(Class entityClass, String id) {
		try {
			T instance = (T) getHibernateTemplate().get(entityClass, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("查找失败", re);
			throw re;
		}
	}

	public PeBzzOrderInfo SavePeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.getHibernateTemplate().saveOrUpdate(peBzzOrderInfo);
		return peBzzOrderInfo;
	}

	public PeBzzInvoiceInfo SavePeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) {
		this.getHibernateTemplate().saveOrUpdate(peBzzInvoiceInfo);
		return peBzzInvoiceInfo;
	}

	@Override
	public PePriRole getPePriRole(String id) {
		PePriRole pePriRole = (PePriRole) this.getHibernateTemplate().get(PePriRole.class, id);
		return pePriRole;
	}

	public Page getByPageSQLDBPool(String sqlNum, String sqlList, int pageSize, int startIndex) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		dbpool db = new dbpool();
		int totalCount = 0;
		try {
			totalCount = db.countselect(sqlNum);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		MyResultSet rs = null;
		try {
			rs = db.executeOraclePage(sqlList, startIndex, pageSize);
			int columnCount = rs.getMyrset().getMetaData().getColumnCount();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < columnCount; i++) {
					String columnName = rs.getMyrset().getMetaData().getColumnName(i + 1);
					map.put(columnName, rs.getString(columnName));
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			db.close(rs);
			db = null;
		}
		Page pg = new Page(list, totalCount, pageSize, startIndex);
		return pg;
	}

	public TrainingCourseStudentHistory clearSession(TrainingCourseStudentHistory obj) {
		SessionFactory sessionFactory = getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.evict(obj);
		session.close();
		return obj;
	}

	public PrBzzTchStuElectiveHistory clearSession(PrBzzTchStuElectiveHistory obj) {
		SessionFactory sessionFactory = getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.evict(obj);
		session.close();
		return obj;
	}

	public PrBzzTchStuElectiveBackHistory clearSession(PrBzzTchStuElectiveBackHistory obj) {
		SessionFactory sessionFactory = getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.openSession();
		session.evict(obj);
		session.close();
		return obj;
	}

	/**
	 * 批量执行sql(有事务)
	 * 
	 * @author Lee
	 * @param sqlList
	 *            SQL集合 SQL格式为："SELECT ... FROM ... WHERE NAME=? AND
	 *            FK_STU_ID=?"
	 * @param paramsList
	 *            参数集合，参数为List集合，注意参数顺序！一定要与自定义SQL中的对应
	 * @return success为批量执行成功，error为批量执行失败
	 * @throws Exception
	 */
	public String executeBatchSql(List<String> sqlList, List<List<String>> paramsList) throws Exception {
		dbpool db = new dbpool();
		Connection conn = db.getConn();
		conn.setAutoCommit(false);
		try {
			if (null != conn) {
			}

		} catch (Exception e) {
			conn.rollback();
		}
		if (conn != null) {
			conn.commit();
			conn.close();
		}
		return null;
	}

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
	 * @return 查询结果List
	 * @throws Exception
	 */
	public List getBySQL(String sql, List<String> params, int pageSize, int startIndex) throws Exception {
		return null;
	}

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
		return null;
	}

	/**
	 * 保存多次学习记录
	 * 
	 * @param tcs
	 *            学习记录实体
	 * @throws Exception
	 */
	public void saveTrainingHis(String userId, Object courseId, Object opencourseId, Date startTime, Date endTime) throws Exception {
		int updateResult = 0;
		String totalTime = "0";
		String st = "";
		String et = "";
		String fkOrderId = "";
		dbpool db = new dbpool();
		MyResultSet mrs = null;
		MyResultSet orderRs = null;
		if (null != startTime && null != endTime) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			st = df.format(startTime);
			et = df.format(endTime);
			long diff = endTime.getTime() - startTime.getTime();
			long min = diff / (1000 * 60);
			totalTime = Long.toString(min);
		}
		try {
			String fkOrderIdSql = "SELECT FK_ORDER_ID FROM PR_BZZ_TCH_STU_ELECTIVE WHERE FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '" + userId
					+ "') AND FK_TCH_OPENCOURSE_ID = '" + opencourseId + "'";
			orderRs = db.executeQuery(fkOrderIdSql);
			while (null != orderRs && orderRs.next()) {
				fkOrderId = orderRs.getString("FK_ORDER_ID");
			}
			// 判断是否存在该学习记录(开始学习时间，课程ID，学员为条件)
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append(" SELECT ID FROM TRAINING_COURSE_STUDENT_HIS WHERE FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '" + userId + "') ");
			sBuffer.append(" AND COURSE_ID = '" + opencourseId + "' ");
			if (!"".equals(fkOrderId)) {
				sBuffer.append(" AND ROUND(TO_NUMBER(TO_DATE('" + st
						+ "','yyyy-MM-dd hh24:mi:ss') - (SELECT MAX(START_TIME) FROM TRAINING_COURSE_STUDENT_HIS WHERE FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '" + userId
						+ "') AND COURSE_ID = '" + opencourseId + "' AND FK_ORDER_ID = '" + fkOrderId + "')) * 24 * 60 * 60) < 30 ");
				sBuffer.append(" AND FK_ORDER_ID = '" + fkOrderId + "'");
			} else {
				sBuffer.append(" AND ROUND(TO_NUMBER(TO_DATE('" + st
						+ "','yyyy-MM-dd hh24:mi:ss') - (SELECT MAX(START_TIME) FROM TRAINING_COURSE_STUDENT_HIS WHERE FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '" + userId
						+ "') AND COURSE_ID = '" + opencourseId + "' AND FK_ORDER_ID IS NULL)) * 24 * 60 * 60) < 30 ");
				sBuffer.append(" AND FK_ORDER_ID IS NULL");
			}
			mrs = db.executeQuery(sBuffer.toString());
			String searchID = "";
			while (null != mrs && mrs.next()) {
				searchID = mrs.getString("ID");
				fkOrderId = mrs.getString("FK_ORDER_ID");
			}
			String updateOrInsertSql = "";
			// 存在更新下
			if (null != searchID && !"null".equalsIgnoreCase(searchID) && !"".equals(searchID)) {
				updateOrInsertSql = "UPDATE TRAINING_COURSE_STUDENT_HIS SET START_TIME = TO_DATE('" + st + "','yyyy-MM-dd hh24:mi:ss'),END_TIME = TO_DATE('" + et
						+ "','yyyy-MM-dd hh24:mi:ss'),TOTAL_TIME = '" + totalTime + "' WHERE ID = '" + searchID + "'";
			} else {// 不存在插入数据
				updateOrInsertSql = "INSERT INTO TRAINING_COURSE_STUDENT_HIS (ID,FK_STU_ID,COURSE_ID,START_TIME,END_TIME,TOTAL_TIME,FK_ORDER_ID) SELECT SEQ_TCS_HIS.NEXTVAL, ID,'" + opencourseId
						+ "',TO_DATE('" + st + "','yyyy-MM-dd hh24:mi:ss'),TO_DATE('" + et + "','yyyy-MM-dd hh24:mi:ss'),'" + totalTime + "','" + fkOrderId
						+ "' FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '" + userId + "'";
			}
			updateResult = db.executeUpdate(updateOrInsertSql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.close(mrs);
			db.close(orderRs);//2014-09-16 李文强 关闭resultSet
		}
		System.out.println("保存、更新学习记录：" + updateResult);
	}

	@Override
	public void executeCall(String callStr, List paramList) {
		SessionFactory sessionFactory = getHibernateTemplate()
				.getSessionFactory();
		Session session = sessionFactory.openSession();
		ProcedureCall pc = session.createStoredProcedureCall(callStr);
		pc.registerParameter("STUID", String.class, ParameterMode.IN).bindValue((String) paramList.get(0));
		pc.registerParameter("P_OUT_RESULTNUM", String.class, ParameterMode.OUT);
		ProcedureOutputs opt = pc.getOutputs();
		opt.release();
	}
}
