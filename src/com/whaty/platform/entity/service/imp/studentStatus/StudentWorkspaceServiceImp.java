package com.whaty.platform.entity.service.imp.studentStatus;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.FrequentlyAskedQuestions;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.SiteEmail;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.dao.EnumConstDAO;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.studentStatas.StudentWorkspaceService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.training.basic.StudyProgress;

public class StudentWorkspaceServiceImp implements StudentWorkspaceService {

	private GeneralDao generalDao;
	private EnumConstDAO enumConstDao;

	public EnumConstDAO getEnumConstDao() {
		return enumConstDao;
	}

	public void setEnumConstDao(EnumConstDAO enumConstDao) {
		this.enumConstDao = enumConstDao;
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public void saveOrderInvoiceEletive(PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, List<PrBzzTchStuElective> eleList)
			throws EntityException {
		this.saveOrderInvoiceEletive(peBzzOrderInfo, peBzzInvoiceInfo, eleList, null, null);
	}

	public void saveOrderInvoiceEletive(PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo,
			List<PrBzzTchStuElective> eleList, List<StudentBatch> stuBatchList, String flag) throws EntityException {
		try {

			if (peBzzOrderInfo != null) {
				peBzzOrderInfo.setCreateDate(new Date());
				peBzzOrderInfo.setPaymentDate(peBzzOrderInfo.getCreateDate());
				peBzzOrderInfo = (PeBzzOrderInfo) this.generalDao.save(peBzzOrderInfo);
			}
			if (peBzzInvoiceInfo != null) {
				peBzzInvoiceInfo.setPeBzzOrderInfo(peBzzOrderInfo);
				peBzzInvoiceInfo = (PeBzzInvoiceInfo) this.generalDao.save(peBzzInvoiceInfo);
			}
			if (eleList != null) {
				for (PrBzzTchStuElective p : eleList) {
					p.setPeBzzOrderInfo(peBzzOrderInfo);
					this.generalDao.save(p);
				}
			}
			if (stuBatchList != null) {
				for (StudentBatch stuBath : stuBatchList) {
					this.generalDao.update(stuBath);
				}
			}
			if (peBzzOrderInfo.getSsoUser() != null) {
				this.generalDao.detachedSave(peBzzOrderInfo.getSsoUser());
			}
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
	}

	public void deleteElective(PrBzzTchStuElective prBzzTchStuElective) throws EntityException {
		// TODO Auto-generated method stub
		try {
			TrainingCourseStudent trainingCourseStudent = prBzzTchStuElective.getTrainingCourseStudent();
			String eleSql = "delete from pr_bzz_tch_stu_elective where id = '" + prBzzTchStuElective.getId() + "'";
			this.getGeneralDao().executeBySQL(eleSql);
			if (trainingCourseStudent != null) {
				String trSql = "delete from training_course_student where id ='" + trainingCourseStudent.getId() + "'";
				this.getGeneralDao().executeBySQL(trSql);
			}
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
	}

	public void saveOrderInvoiceEletiveBack(PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo,
			List<PrBzzTchStuElective> eleList, List<StudentBatch> stuBatchList) throws EntityException {
		try {
			if (peBzzOrderInfo != null) {
				peBzzOrderInfo.setCreateDate(new Date());
				// peBzzOrderInfo.setPaymentDate(peBzzOrderInfo.getCreateDate());//网银支付，到账时间要后加不能直接带过来
				peBzzOrderInfo = (PeBzzOrderInfo) this.generalDao.save(peBzzOrderInfo);
			}
			if (peBzzInvoiceInfo != null) {
				peBzzInvoiceInfo.setPeBzzOrderInfo(peBzzOrderInfo);
				peBzzInvoiceInfo = (PeBzzInvoiceInfo) this.generalDao.save(peBzzInvoiceInfo);
			}
			DetachedCriteria ssoUserDc = DetachedCriteria.forClass(PeBzzStudent.class);
			ssoUserDc.createCriteria("ssoUser", "ssoUser");
			ssoUserDc.add(Restrictions.eq("ssoUser", peBzzOrderInfo.getSsoUser()));
			PeBzzStudent s = (PeBzzStudent) this.generalDao.getList(ssoUserDc).get(0);
			if (eleList != null) {
				for (PrBzzTchStuElective p : eleList) {
					PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
					eleBack.setPeBzzOrderInfo(peBzzOrderInfo);
					eleBack.setPrBzzTchOpencourse(p.getPrBzzTchOpencourse());
					eleBack.setPeBzzStudent(s);
					eleBack.setSsoUser(s.getSsoUser());
					this.generalDao.save(eleBack);
				}
			}

			if (stuBatchList != null) {
				for (StudentBatch stuBath : stuBatchList) {
					this.generalDao.update(stuBath);
				}
			}
			if (peBzzOrderInfo.getSsoUser() != null) {
				this.generalDao.detachedSave(peBzzOrderInfo.getSsoUser());
			}
			// throw new EntityException();
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}

	}

	/**
	 * 用于学员专项支付网银支付的前期支付
	 */
	public void saveElectiveCourseAndPebzzOrderInfo(List<PrBzzTchStuElective> electiveList, PeBzzOrderInfo peBzzOrderInfo,
			PeBzzInvoiceInfo peBzzInvoiceInfo, List<StudentBatch> sbList) {
		if (peBzzOrderInfo != null) {
			peBzzOrderInfo = (PeBzzOrderInfo) this.generalDao.save(peBzzOrderInfo);
		}

		if (electiveList != null) {
			for (PrBzzTchStuElective prBzzTchStuElective : electiveList) {
				prBzzTchStuElective.setPeBzzOrderInfo(peBzzOrderInfo);
				this.generalDao.save(prBzzTchStuElective);
			}
		}
		if (peBzzInvoiceInfo != null) {
			this.generalDao.save(peBzzInvoiceInfo);
		}
		if (sbList != null) {
			for (StudentBatch sb : sbList) {
				this.generalDao.save(sb);
			}
		}

	}

	/**
	 * 用于获取登陆的学生的对象
	 */

	// @Cacheable(extension = "[0]", tTLSeconds = 30)
	public PeBzzStudent initStudentInfo(String userId) {
		DetachedCriteria peStudentDC = DetachedCriteria.forClass(PeBzzStudent.class);
		peStudentDC.createAlias("ssoUser", "ssoUser");
		peStudentDC.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee", peStudentDC.LEFT_JOIN);
		peStudentDC.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType", DetachedCriteria.LEFT_JOIN);
		peStudentDC.createCriteria("peEnterprise", "peEnterprise");
		peStudentDC.add(Restrictions.eq("ssoUser.id", userId));
		List peStudentList = new ArrayList();
		peStudentList = this.generalDao.getList(peStudentDC);
		return (PeBzzStudent) peStudentList.get(0);
	}

	/**
	 * Lee 2013年12月26日 选课期数量查询
	 */

	public String unChooseCourseNum(String id) {
		String sql = "SELECT COUNT (*) AS Y0_ FROM COURSE_PERIOD_STUDENT THIS_ "
				+ " INNER JOIN PE_BZZ_STUDENT PEBZZSTUDE1_ ON THIS_.STUDENT_ID = PEBZZSTUDE1_.ID"
				+ " INNER JOIN PE_ELECTIVE_COURSE_PERIOD PEELECTIVE2_ ON THIS_.COURSE_PERIOD_ID = PEELECTIVE2_.ID "
				+ " WHERE PEBZZSTUDE1_.ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = (SELECT ID FROM SSO_USER WHERE LOGIN_ID ='"
				+ id + "'))";
		String count = this.generalDao.getBySQL(sql).get(0).toString();
		return count;
	}

	/**
	 * Lee 2013年12月26日 通知公告数量查询
	 */

	public String peBulletinnum(String id) {
		// 1查出当前登陆学员所属一级机构的机构号。
		String sqlCode = "(select code from pe_enterprise where id=(select t1.id from(\n"
				+ "     select (case when pe.fk_parent_id is null then pe.id else pe.fk_parent_id end)id \n"
				+ "     from pe_enterprise pe, pe_bzz_student stu where pe.id=stu.fk_enterprise_id and stu.reg_no='" + id + "')t1))";
		List list = this.generalDao.getBySQL(sqlCode);
		// 2查出机构
		String code = list.get(0).toString();
		String sql = "select count(*)\n" + "  from pe_bulletin pb, pe_manager pe, enum_const ec \n" + " where pe.id = pb.fk_manager_id\n"
				+ "	and ec.id = pb.flag_isvalid\n" + "       and ec.code='1'\n" + "      and pb.scope_string like '%student%'\n"
				+ "      and pb.scope_string like '%" + code + "%'\n"
				+ "     order by pb.flag_istop desc,pb.publish_date desc, ec.name desc ";
		String count = this.generalDao.getBySQL(sql).get(0).toString();
		return count;
	}

	public String UnreadNum(String regNo) {
		String unReadnum = "";
		DetachedCriteria dc = DetachedCriteria.forClass(SiteEmail.class).add(Restrictions.eq("addresseeId", regNo)).add(
				Restrictions.eq("status", "0")).add(Restrictions.eq("addresseeDel", (long) 0));
		List list = this.generalDao.getDetachList(dc);
		if (list != null) {
			unReadnum = "" + list.size();
		} else {
			unReadnum = "0";
		}
		return unReadnum;
	}

	public BigDecimal balance(String loginId) {
		BigDecimal balancenum = new BigDecimal(0);
		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
		dc.add(Restrictions.eq("loginId", loginId));
		SsoUser user = (SsoUser) this.generalDao.getList(dc).get(0);
		if (user.getSumAmount() != null && !("".equals(user.getSumAmount())) && !("".equals(user.getAmount())) && user.getAmount() != null) {
			balancenum = new BigDecimal(user.getSumAmount()).subtract(new BigDecimal(user.getAmount()));
			BigDecimal mData = new BigDecimal(balancenum.toString()).setScale(1, BigDecimal.ROUND_HALF_UP);
			balancenum = mData;
		} else {
			balancenum = new BigDecimal(0.0);
		}
		return balancenum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public Page paymentCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName,
			String courseItemType, int pageSize, int startIndex) throws EntityException {
		Page page = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
		dc.createAlias("enumConstByFlagElectivePayStatus", "enumConstByFlagElectivePayStatus");
		dc.add(Restrictions.eq("enumConstByFlagElectivePayStatus.code", "1"));
		dc.createCriteria("peBzzStudent", "peBzzStudent");
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peBzzOrderInfo.enumConstByFlagRefundState", "enumConstByFlagRefundState", DetachedCriteria.LEFT_JOIN);

		DetachedCriteria prdc = dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
		prdc.createCriteria("peBzzTchCourse", "peBzzTchCourse").createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");
		prdc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.createCriteria("trainingCourseStudent", "trainingCourseStudent");
		dc.add(Restrictions.eq("peBzzStudent.id", stuId));
		dc.add(Restrictions.ne("trainingCourseStudent.learnStatus", StudyProgress.COMPLETED));

		dc.addOrder(Order.desc("peBzzTchCourse.code"));
		if (courseCode != null && !"".equalsIgnoreCase(courseCode)) {
			dc.add(Restrictions.like("peBzzTchCourse.code", courseCode, MatchMode.ANYWHERE));
		}
		if (courseName != null && !"".equalsIgnoreCase(courseName)) {
			dc.add(Restrictions.like("peBzzTchCourse.name", courseName, MatchMode.ANYWHERE));
		}
		if (courseItemType != null && !"".equalsIgnoreCase(courseItemType)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCourseItemType.id", courseItemType));
		}
		if (courseCategory != null && !"".equalsIgnoreCase(courseCategory)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCourseCategory.id", courseCategory));
		}
		if (courseType != null && !"".equalsIgnoreCase(courseType)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCourseType.id", courseType));
		}
		page = this.generalDao.getByPage(dc, pageSize, startIndex);

		return page;
	}

	/**
	 * 选课期列表
	 */

	// @Cacheable(extension = "[0]+'_'+[1]+'_'+[2]+'_'+[3]", tTLSeconds = 30)
	public Page initCoursePeriodList(String stuId, String coursename, int pageSize, int startIndex, String ktimestart, String ktimeend,
			String etimestart, String etimeend) throws EntityException {
		// Lee start 2014年04月10日 重写查询
		String sql = "SELECT A.ID,A.NAME, to_char( A.BEGIN_DATE, 'yyyy-mm-dd hh24:mi:ss') BEGIN_DATE, to_char(A.END_DATE, 'yyyy-mm-dd hh24:mi:ss') END_DATE,A.STU_TIME,DECODE(SUM(B.TIME),NULL,0,SUM(B.TIME)), A.AMOUNT_UP_LIMIT FROM (SELECT AA.ID,AA.NAME,AA.BEGIN_DATE,AA.END_DATE,AA.STU_TIME,AA.AMOUNT_UP_LIMIT FROM PE_ELECTIVE_COURSE_PERIOD AA RIGHT JOIN (SELECT * FROM COURSE_PERIOD_STUDENT WHERE STUDENT_ID = '"
				+ stuId
				+ "') BB ON AA.ID = BB.COURSE_PERIOD_ID )A LEFT JOIN ( "
				+ "SELECT E.ID,C.TIME "
				+ "FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A "
				+ "INNER JOIN PR_BZZ_TCH_OPENCOURSE B "
				+ "ON A.FK_TCH_OPENCOURSE_ID = B.ID "
				+ "INNER JOIN PE_BZZ_TCH_COURSE C "
				+ "ON B.FK_COURSE_ID = C.ID "
				+ "INNER JOIN ENUM_CONST D "
				+ "ON C.FLAG_COURSECATEGORY = D.ID "
				+ "INNER JOIN PE_ELECTIVE_COURSE_PERIOD E "
				+ "ON A.FK_ELE_COURSE_PERIOD_ID = E.ID "
				+ "WHERE A.FK_STU_ID = '" + stuId + "') B ON A.ID = B.ID WHERE 1=1 ";
		if (null != ktimestart && !ktimestart.equals("")) {
			sql += " AND to_date(to_char(A.BEGIN_DATE, 'yyyy-MM-dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss') >= to_date('" + ktimestart
					+ " 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		if (null != ktimeend && !ktimeend.equals("")) {
			sql += " AND to_date(to_char(A.BEGIN_DATE, 'yyyy-MM-dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss') <= to_date('" + ktimeend
					+ " 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		if (null != etimestart && !etimestart.equals("")) {
			sql += " AND to_date(to_char(A.END_DATE, 'yyyy-MM-dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss') >= to_date('" + etimestart
					+ " 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		if (null != etimeend && !etimeend.equals("")) {
			sql += " AND to_date(to_char(A.END_DATE, 'yyyy-MM-dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss') <= to_date('" + etimeend
					+ " 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		sql += " GROUP BY A.ID,A.NAME,A.BEGIN_DATE,A.END_DATE,A.STU_TIME,A.AMOUNT_UP_LIMIT ";
		if (coursename != null) {
			if (!coursename.equals("")) {
				sql += "HAVING A.NAME LIKE '%" + coursename + "%'";
			}

		}
		sql += " ORDER BY A.ID DESC";
		Page page = null;
		page = this.getGeneralDao().getByPageSQL(sql, pageSize, startIndex);
		return page;
		// Lee end
		// Lee start 原版↓
		// System.out.println("pageSize=" + pageSize +
		// "**************startIndex="
		// + startIndex);
		// Page page = null;
		// DetachedCriteria dc = DetachedCriteria
		// .forClass(CoursePeriodStudent.class);
		// dc.createCriteria("peBzzStudent", "peBzzStudent");
		// dc.createCriteria("peElectiveCoursePeriod",
		// "peElectiveCoursePeriod");
		// if (coursename != null) {
		// if (!coursename.equals("")) {
		// dc.add(Restrictions.like("peElectiveCoursePeriod.name",
		// coursename, MatchMode.ANYWHERE));
		// }
		// }
		// dc.add(Restrictions.eq("peBzzStudent.id", stuId));
		// dc.addOrder(Order.desc("id"));
		// page = this.generalDao.getByPage(dc, pageSize, startIndex);
		// return page;
		// Lee end
	}

	/**
	 * 专项列表
	 */

	// //@Cacheable(extension = "[0]+'_'+[1]+'_'+[2]+'_'+[3]", tTLSeconds = 30)
	public Page initBatchList(String loginId, String batchName, int pageSize, int startIndex) throws EntityException {
		// TODO Auto-generated method stub
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		/***********************************************************************
		 * sqlBuffer.append("SELECT * "); sqlBuffer.append("FROM (SELECT pb.id
		 * AS id, "); sqlBuffer.append(" pb.name AS name, "); sqlBuffer.append("
		 * pb.start_time AS startDate, "); sqlBuffer.append(" pb.end_time AS
		 * endDate, "); sqlBuffer.append(" pb.standards AS standards, ");
		 * sqlBuffer.append(" b.totalTime, "); sqlBuffer.append(" a.stunum, ");
		 * sqlBuffer.append(" a.paystunum "); sqlBuffer .append(" FROM (SELECT
		 * pb.id AS id, "); sqlBuffer .append(" Count(DISTINCT ps.id) AS stunum,
		 * "); sqlBuffer .append(" Count(t.id) AS paystunum ");
		 * sqlBuffer.append(" FROM pe_bzz_batch pb "); sqlBuffer .append(" INNER
		 * JOIN (SELECT ps.fk_enterprise_id, "); sqlBuffer .append(" ps.id, ");
		 * sqlBuffer .append(" sb.batch_id, "); sqlBuffer .append("
		 * sb.flag_batchpaystate "); sqlBuffer .append(" FROM pe_bzz_student ps,
		 * "); sqlBuffer .append(" sso_user su, "); sqlBuffer .append("
		 * stu_batch sb "); sqlBuffer .append(" WHERE ps.fk_sso_user_id = su.id
		 * "); sqlBuffer .append(" AND su.flag_isvalid = '2' "); sqlBuffer
		 * .append(" AND ps.reg_no = '" + loginId + "' "); sqlBuffer .append("
		 * AND sb.stu_id = ps.id) ps "); sqlBuffer.append(" ON ps.batch_id =
		 * pb.id "); sqlBuffer.append(" LEFT JOIN enum_const t "); sqlBuffer
		 * .append(" ON t.id = ps.flag_batchpaystate "); // sqlBuffer //
		 * .append(" -- AND t.namespace = 'FlagBatchPayState' "); //
		 * sqlBuffer.append(" -- AND t.code = '1' "); sqlBuffer.append(" GROUP
		 * BY pb.id) a, "); sqlBuffer.append(" (SELECT pb.id AS id, ");
		 * sqlBuffer.append(" Sum(pc.time) AS totalTime "); sqlBuffer.append("
		 * FROM pe_bzz_batch pb "); sqlBuffer.append(" LEFT JOIN (SELECT
		 * ptc.time, "); sqlBuffer .append(" pto.fk_batch_id "); sqlBuffer
		 * .append(" FROM pe_bzz_tch_course ptc, "); sqlBuffer .append("
		 * pr_bzz_tch_opencourse pto "); sqlBuffer .append(" WHERE ptc.id =
		 * pto.fk_course_id) pc "); sqlBuffer.append(" ON pc.fk_batch_id = pb.id
		 * "); sqlBuffer.append(" GROUP BY pb.id) b, "); sqlBuffer.append("
		 * pe_bzz_batch pb "); sqlBuffer.append(" INNER JOIN enum_const e ");
		 * sqlBuffer.append(" ON pb.flag_batch_type = e.id "); // sqlBuffer //
		 * .append(" -- AND e.namespace = 'FlagBatchType' "); //
		 * sqlBuffer.append(" -- AND e.code = '0' "); sqlBuffer.append(" WHERE
		 * pb.id = a.id "); sqlBuffer.append(" AND pb.id = b.id ");
		 * sqlBuffer.append(" ORDER BY ID DESC) "); sqlBuffer.append("WHERE 1 =
		 * 1 ");
		 **********************************************************************/
		String sql = "SELECT ID,FK_SSO_USER_ID FROM PE_BZZ_STUDENT PBS WHERE PBS.REG_NO = '" + loginId + "'";
		List list = this.getGeneralDao().getBySQL(sql);
		String stuId = ((Object[]) list.get(0))[0].toString();
		String ssoId = ((Object[]) list.get(0))[1].toString();
		sqlBuffer
				.append("select *\n"
						+ "  from (select pb.id AS id,\n"
						+ "               pb.name AS name,\n"
						+ "               pb.start_time AS startDate,\n"
						+ "               pb.end_time AS endDate,\n"
						+ "               pb.standards AS standards,\n"
						+ "               sum(pc.time) as totalTime,\n"
						+ "               ec.code,\n"
						+ "               pboi.id as orderId,\n"
						+ "               pb.sugg_time as suggtime, NVL(SB.FK_ORDER_ID,0) AS FOID,\n"
						+ "               pb.batch_note as dis"
						+ "          from pe_bzz_batch pb\n"
						+ "         inner join stu_batch sb\n"
						+ "            on sb.batch_id = pb.id and pb.flag_batch_type = '40288a7b394d676d01394db30248003d' \n"
						+ "         inner join (SELECT ptc.time, pto.fk_batch_id\n"
						+ "                      FROM pe_bzz_tch_course ptc, pr_bzz_tch_opencourse pto\n"
						+ "                     WHERE ptc.id = pto.fk_course_id) pc\n"
						+ "            on pc.fk_batch_id = pb.id\n"
						+ "         inner join enum_const ec\n"
						+ "            on ec.id = sb.flag_batchpaystate  \n"
						+ " inner join enum_const e on e.id = pb.flag_deploy and e.code = '1' \n"
						+ "          left join (select id\n"
						+ "                      from pe_bzz_order_info\n"
						+ "                     where create_user = '"
						+ ssoId
						+ "') pboi\n"
						+ "            on sb.fk_order_id = pboi.id\n"
						+ "         where sb.stu_id = '"
						+ stuId
						+ "'\n"
						+ "         group by pb.id,\n"
						+ "                  pb.name,\n"
						+ "                  pb.start_time,\n"
						+ "                  pb.end_time,\n"
						+ "                  pb.standards,\n"
						+ "                  pb.batch_note,\n"
						+ "                  ec.code,\n"
						+ "                  pboi.id,pb.sugg_time,SB.FK_ORDER_ID)  AA LEFT JOIN (SELECT DISTINCT B.FK_BATCH_ID FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID WHERE A.FK_STU_ID = '"
						+ stuId + "') BB ON AA.ID = BB.FK_BATCH_ID \n" + " where 1 = 1");
		if (batchName != null && !batchName.trim().equals("")) {
			sqlBuffer.append("and name like '%" + batchName + "%'");
		}
		sqlBuffer.append(" order by AA.startDate desc ");
		page = this.generalDao.getByPageSQL(sqlBuffer.toString(), pageSize, startIndex);
		return page;
	}

	/**
	 * 正在学习课程列表
	 */
	public Page initLearingCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName,
			String courseItemType, int pageSize, int startIndex) throws EntityException {
		Page page = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
		// dc.createAlias("enumConstByFlagElectivePayStatus",
		// "enumConstByFlagElectivePayStatus");
		// dc.add(Restrictions.eq("enumConstByFlagElectivePayStatus.code",
		// "1"));
		dc.createCriteria("peBzzStudent", "peBzzStudent");
		dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peBzzOrderInfo.enumConstByFlagRefundState", "peBzzOrderInfo.enumConstByFlagRefundState",
				DetachedCriteria.LEFT_JOIN);

		dc.createCriteria("trainingCourseStudent", "trainingCourseStudent");
		dc.add(Restrictions.eq("peBzzStudent.id", stuId));
		dc.add(Restrictions.ne("trainingCourseStudent.learnStatus", StudyProgress.COMPLETED));
		dc.addOrder(Order.desc("peBzzTchCourse.code"));

		if (courseCode != null && !"".equalsIgnoreCase(courseCode)) {
			dc.add(Restrictions.like("peBzzTchCourse.code", courseCode, MatchMode.ANYWHERE));
		}
		if (courseName != null && !"".equalsIgnoreCase(courseName)) {
			dc.add(Restrictions.like("peBzzTchCourse.name", courseName, MatchMode.ANYWHERE));
		}
		if (courseItemType != null && !"".equalsIgnoreCase(courseItemType)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCourseItemType.id", courseItemType));
		}
		if (courseCategory != null && !"".equalsIgnoreCase(courseCategory)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCourseCategory.id", courseCategory));
		}
		if (courseType != null && !"".equalsIgnoreCase(courseType)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCourseType.id", courseType));
		}

		try {
			page = this.generalDao.getByPage(dc, pageSize, startIndex);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return page;
	}

	/**
	 * 免费课程加载
	 */

	// @Cacheable(extension = "[0]+'_'+[1]+'_'+[2]+'_'+[3]+'_'+[4]+'_'+[5]",
	// tTLSeconds = 30)
	public Page initFreeCourse(String userId, String courseCode, String courseName, String courseCategory, int pageSize, int startIndex,
			String time, String teacher) throws EntityException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Page page = null;
		EnumConst enumConstByFlagIsFree = this.enumConstDao.getByNamespaceCode("FlagIsFree", "1");// 免费课程
		EnumConst enumConstByFlagIsvalid = this.enumConstDao.getByNamespaceCode("FlagIsvalid", "1");// 已发布的
		EnumConst enumConstByFlagCoursearea = this.enumConstDao.getByNamespaceCode("FlagCoursearea", "1");// 课程所属区域
		// 用于查找默认专项id
		String sqlBatch = "select pb.id from pe_bzz_batch pb\n" + "inner join enum_const ec on ec.id = pb.flag_batch_type\n"
				+ "where ec.namespace = 'FlagBatchType'\n" + "and ec.code = '1'";
		String batchId = "";
		List list1 = this.getGeneralDao().getBySQL(sqlBatch);
		if (list1 != null) {
			if (list1.size() > 0) {
				batchId = list1.get(0).toString();
			}
		}

		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		dc.createCriteria("peBzzTchCourse", "peBzzTchCourse").createAlias("enumConstByFlagCourseType", "enumConstByFlagCourseType") // 课程性质
				.createAlias("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory") // 业务分类
				.createAlias("enumConstByFlagContentProperty", "enumConstByFlagContentProperty") // 内容属性分类
				.createAlias("enumConstByFlagCourseItemType", "enumConstByFlagCourseItemType") // 大纲分类
				.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");//
		dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagIsFree", enumConstByFlagIsFree));
		dc.add(Restrictions.isNotNull("id"));
		dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCoursearea", enumConstByFlagCoursearea));
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagIsvalid", enumConstByFlagIsvalid));
		dc.add(Restrictions.or(Restrictions.isNull("peBzzTchCourse.endDate"), Restrictions.ge("peBzzTchCourse.endDate", cal.getTime())));
		if (batchId != null) {
			if (!batchId.equals("")) {
				dc.add(Restrictions.eq("peBzzBatch.id", batchId));
			}
		}
		if (courseCategory != null && !"".equalsIgnoreCase(courseCategory)) {
			dc.add(Restrictions.eq("enumConstByFlagCourseCategory.id", courseCategory));
		}
		if (courseCode != null && !"".equalsIgnoreCase(courseCode)) {
			dc.add(Restrictions.like("peBzzTchCourse.code", courseCode, MatchMode.ANYWHERE));
		}
		if (courseName != null && !"".equalsIgnoreCase(courseName)) {
			dc.add(Restrictions.like("peBzzTchCourse.name", courseName, MatchMode.ANYWHERE));
		}
		if (time != null && !"".equalsIgnoreCase(time)) {
			dc.add(Restrictions.like("peBzzTchCourse.time", time, MatchMode.ANYWHERE));
		}
		if (teacher != null && !"".equalsIgnoreCase(teacher)) {
			dc.add(Restrictions.like("peBzzTchCourse.teacher", teacher, MatchMode.ANYWHERE));
		}
		// Lee 2014年2月19日 添加条件 and pebzztchco1_.FLAG_OFFLINE = '22'
		// dc.add(Restrictions.sqlRestriction(" 1=1 and
		// pebzztchco1_.FLAG_OFFLINE = '22' and this_.id not in(select
		// FK_TCH_OPENCOURSE_ID from PR_BZZ_TCH_STU_ELECTIVE where FK_STU_ID='"
		// + userId
		// + "' AND FK_TCH_OPENCOURSE_ID IS NOT NULL) " + " and this_.id not
		// in(select p.fk_tch_opencourse_id from Pr_Bzz_Tch_Stu_Elective_Back p
		// where p.fk_stu_id='" + userId
		// + "' AND P.FK_TCH_OPENCOURSE_ID IS NOT NULL) order by
		// substr(pebzztchco1_.code,1,1),pebzztchco1_.code desc"));
		dc
				.add(Restrictions
						.sqlRestriction(" 1=1 and pebzztchco1_.FLAG_OFFLINE = '22' and this_.id not in(select p.fk_tch_opencourse_id from  Pr_Bzz_Tch_Stu_Elective_Back p where p.fk_stu_id='"
								+ userId
								+ "' AND P.FK_TCH_OPENCOURSE_ID IS NOT NULL)  order by substr(pebzztchco1_.code,1,1),pebzztchco1_.code desc"));
		// 加两个条件 判断 此课程 是否在报名表里和选课表里。。 lzh

		// dc.addOrder(Order.asc("peBzzTchCourse.code"));
		// dc.addOrder(Order.desc("peBzzTchCourse.code"));

		/**
		 * 学员显示免费课程时，必须是已经有购买过选课了（选课的状态必须是已支付状态）
		 */
		// DetachedCriteria eleDc = DetachedCriteria
		// .forClass(PrBzzTchStuElective.class);
		// eleDc.createCriteria("ssoUser", "ssoUser");
		// eleDc.add(Restrictions.eq("ssoUser.id", userId));
		// eleDc.createCriteria("enumConstByFlagElectivePayStatus",
		// "enumConstByFlagElectivePayStatus");
		// eleDc
		// .add(Restrictions.eq("enumConstByFlagElectivePayStatus.code",
		// "1"));
		// Lee start 根据业务需求，学员可直接学习免费课程，不需要先购买一门收费课程，因此注释下代码
		/*
		 * StringBuffer sql = new StringBuffer(); sql.append("select pbtse.id
		 * "); sql.append(" from (select pbtse.id, "); sql.append("
		 * pbtse.FLAG_ELECTIVE_PAY_STATUS, "); sql.append("
		 * pbtse.FK_TCH_OPENCOURSE_ID "); sql.append(" from
		 * PR_BZZ_TCH_STU_ELECTIVE pbtse,pe_bzz_student s "); sql.append(" where
		 * s.fk_sso_user_id= '" + userId + "' and pbtse.fk_stu_id=s.id) pbtse,
		 * "); sql.append(" (select ec.id from ENUM_CONST ec where ec.code =
		 * '1') ec "); sql.append(" where pbtse.FLAG_ELECTIVE_PAY_STATUS = ec.ID
		 * ");
		 * 
		 * List list = this.generalDao.getBySQL(sql.toString()); //
		 * 如果选课表中存在记录，且已经支付，那么免费将显示，否则不显示 if (list.size() > 0) { page =
		 * this.generalDao.getByPage(dc, pageSize, startIndex); }
		 */
		page = this.generalDao.getByPage(dc, pageSize, startIndex);
		return page;
	}

	// @Cacheable(extension =
	// "[0]+'_'+[1]+'_'+[2]+'_'+[3]+'_'+[4]+'_'+[5]+'_'+[6]+'_'+[7]+'_'+[8]+'_'+[9]+'_'+[10]+'_'+[11]",
	// tTLSeconds = 30)
	public Page initFreeCourse(String userId, String courseCode, String courseName, String courseType, String courseItemType,
			String courseCategory, String courseContent, String time, String teacher, String suggestren, int pageSize, int startIndex,
			String seriseCourse) throws EntityException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Page page = null;
		EnumConst enumConstByFlagIsFree = this.enumConstDao.getByNamespaceCode("FlagIsFree", "1");// 免费课程
		EnumConst enumConstByFlagIsvalid = this.enumConstDao.getByNamespaceCode("FlagIsvalid", "1");// 已发布的
		EnumConst enumConstByFlagCoursearea = this.enumConstDao.getByNamespaceCode("FlagCoursearea", "1");// 课程所属区域
		// 用于查找默认专项id
		String sqlBatch = "select pb.id from pe_bzz_batch pb\n" + "inner join enum_const ec on ec.id = pb.flag_batch_type\n"
				+ "where ec.namespace = 'FlagBatchType'\n" + "and ec.code = '1'";
		String batchId = "";
		List list1 = this.getGeneralDao().getBySQL(sqlBatch);
		if (list1 != null) {
			if (list1.size() > 0) {
				batchId = list1.get(0).toString();
			}
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		dc.createCriteria("peBzzTchCourse", "peBzzTchCourse").createAlias("enumConstByFlagCourseType", "enumConstByFlagCourseType") // 课程性质
				.createAlias("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory") // 业务分类
				.createAlias("enumConstByFlagContentProperty", "enumConstByFlagContentProperty") // 内容属性分类
				.createAlias("enumConstByFlagCourseItemType", "enumConstByFlagCourseItemType") // 大纲分类
				.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");//
		dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagIsFree", enumConstByFlagIsFree));
		dc.add(Restrictions.isNotNull("id"));
		dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCoursearea", enumConstByFlagCoursearea));
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagIsvalid", enumConstByFlagIsvalid));
		dc.add(Restrictions.or(Restrictions.isNull("peBzzTchCourse.endDate"), Restrictions.ge("peBzzTchCourse.endDate", cal.getTime())));
		if (batchId != null) {
			if (!batchId.equals("")) {
				dc.add(Restrictions.eq("peBzzBatch.id", batchId));
			}
		}
		StringBuffer sbWhere = new StringBuffer(" 1 = 1 ");
		if (courseCode != null && !"".equalsIgnoreCase(courseCode)) {
			sbWhere.append(" AND UPPER(pebzztchco1_.CODE) LIKE '%" + courseCode.toUpperCase() + "%' ");
		}
		if (courseName != null && !"".equalsIgnoreCase(courseName)) {
			sbWhere.append(" AND UPPER(pebzztchco1_.NAME) LIKE '%" + courseName.toUpperCase() + "%' ");
		}
		if (courseType != null && !"".equalsIgnoreCase(courseType)) {
			sbWhere.append(" AND pebzztchco1_.FLAG_COURSETYPE = '" + courseType + "'");
		}
		if (courseItemType != null && !"".equalsIgnoreCase(courseItemType)) {
			sbWhere.append(" AND pebzztchco1_.FLAG_COURSE_ITEM_TYPE = '" + courseItemType + "'");
		}
		if (courseCategory != null && !"".equalsIgnoreCase(courseCategory)) {
			sbWhere.append(" AND pebzztchco1_.FLAG_COURSECATEGORY = '" + courseCategory + "'");
		}
		if (courseContent != null && !"".equalsIgnoreCase(courseContent)) {
			sbWhere.append(" AND pebzztchco1_.FLAG_CONTENT_PROPERTY = '" + courseContent + "'");
		}
		if (time != null && !"".equalsIgnoreCase(time)) {
			sbWhere.append(" AND pebzztchco1_.TIME = '" + time + "'");
		}
		if (teacher != null && !"".equalsIgnoreCase(teacher)) {
			sbWhere.append(" AND UPPER(pebzztchco1_.TEACHER) LIKE '%" + teacher.toUpperCase() + "%' ");
		}
		if (suggestren != null && !"".equalsIgnoreCase(suggestren)) {
			sbWhere
					.append(" AND pebzztchco1_.ID IN (SELECT DISTINCT PBTCS.FK_COURSE_ID FROM PE_BZZ_TCH_COURSE_SUGGEST PBTCS, ENUM_CONST EC WHERE PBTCS.FK_ENUM_CONST_ID = EC.ID AND EC.NAMESPACE = 'FlagSuggest' AND EC.NAME = '"
							+ suggestren + "') ");
		}
		if (seriseCourse != null && !"".equalsIgnoreCase(seriseCourse)) {
			sbWhere.append(" AND pebzztchco1_.ID IN (SELECT r.Pk_Course_Id FROM RECOMMEND_COURSE R WHERE r.Pk_Series_Id ='" + seriseCourse
					+ "')");
		}
		dc
				.add(Restrictions
						.sqlRestriction(sbWhere.toString()
								+ " and pebzztchco1_.FLAG_OFFLINE = '22' and this_.id not in(select p.fk_tch_opencourse_id from  Pr_Bzz_Tch_Stu_Elective_Back p where p.fk_stu_id='"
								+ userId
								+ "' AND P.FK_TCH_OPENCOURSE_ID IS NOT NULL)  order by substr(pebzztchco1_.code,1,1),pebzztchco1_.code desc"));
		page = this.generalDao.getByPage(dc, pageSize, startIndex);
		return page;
	}

	// @Cacheable(extension = "[0]+'_'+[1]+'_'+[2]+'_'+[3]+'_'+[4]", tTLSeconds
	// = 30)
	public Page initCompletedCourses(String stuId, String courseType, String classHour, int pageSize, int startIndex)
			throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sid = us.getSsoUser().getId();
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		// sqlBuffer.append("SELECT DISTINCT pbtc.id AS courseId, ");
		// sqlBuffer.append(" pbtc.code AS courseCode, ");
		// sqlBuffer.append(" pbtc.name AS courseName, ");
		// sqlBuffer.append(" pbtc.time AS courseTime, ");
		// sqlBuffer.append(" ec.name AS courseType, ");
		// sqlBuffer.append(" ssc.first_date AS startDate, ");
		// sqlBuffer.append(" ssc.last_date AS endDate, ");
		// sqlBuffer.append(" tcs.learn_status AS leatnResult, ");
		// sqlBuffer.append(" pbto.id AS opcourseId, ");
		// sqlBuffer.append(" pbtc.teacher AS pbtcTeacher ");
		// sqlBuffer.append("FROM pe_bzz_tch_course pbtc ");
		// sqlBuffer.append(" INNER JOIN pr_bzz_tch_opencourse pbto ");
		// sqlBuffer.append(" ON pbtc.id = pbto.fk_course_id ");
		// sqlBuffer.append(" INNER JOIN pr_bzz_tch_stu_elective pbtse ");
		// sqlBuffer.append(" ON pbto.id = pbtse.fk_tch_opencourse_id ");
		// sqlBuffer.append(" INNER JOIN training_course_student tcs ");
		// sqlBuffer.append(" ON pbtse.fk_training_id = tcs.id ");
		// sqlBuffer.append(" left JOIN scorm_course_info sci ");
		// sqlBuffer.append(" ON pbtc.code = sci.id ");
		// sqlBuffer.append(" left JOIN scorm_stu_course ssc ");
		// sqlBuffer.append(" ON sci.id = ssc.course_id ");
		// sqlBuffer.append(" INNER JOIN pe_bzz_student pbs ");
		// sqlBuffer.append(" ON pbtse.fk_stu_id = pbs.id ");
		// sqlBuffer.append(" left JOIN sso_user su ");
		// sqlBuffer.append(" ON pbs.fk_sso_user_id = su.id ");
		// sqlBuffer.append(" AND ssc.student_id = su.id ");
		// sqlBuffer.append(" LEFT JOIN enum_const ec ");
		// sqlBuffer.append(" ON pbtc.flag_coursetype = ec.id ");
		// sqlBuffer.append("WHERE tcs.learn_status = '"
		// + StudyProgress.COMPLETED + "' ");
		// sqlBuffer.append(" AND pbs.id = '" + stuId + "' ");
		// sqlBuffer.append("and pbtse.fk_stu_id='" + stuId + "' ");
		sqlBuffer.append("SELECT DISTINCT pbtc.id          AS courseId,            ");
		sqlBuffer.append("                pbtc.code        AS courseCode,			  ");
		sqlBuffer.append("                pbtc.name        AS courseName,			  ");
		sqlBuffer.append("                pbtc.time        AS courseTime,			  ");
		sqlBuffer.append("                ec.name          AS courseType,			  ");
		sqlBuffer.append("                ssc.first_date   AS startDate,			  ");
		sqlBuffer.append("                ssc.last_date    AS endDate,			  ");
		sqlBuffer.append("                tcs.learn_status AS leatnResult,			  ");
		sqlBuffer.append("                pbto.id          AS opcourseId,			  ");
		sqlBuffer.append("                pbtc.teacher     AS pbtcTeacher			  ");
		sqlBuffer.append("  FROM pe_bzz_tch_course pbtc	 ");
		sqlBuffer.append(" INNER JOIN pr_bzz_tch_opencourse pbto ");
		sqlBuffer.append("    ON pbtc.id = pbto.fk_course_id	 ");
		sqlBuffer.append(" INNER JOIN (select fk_stu_id, fk_training_id, fk_tch_opencourse_id	  ");
		sqlBuffer.append("               from pr_bzz_tch_stu_elective ");
		sqlBuffer.append("              where fk_stu_id = '" + stuId + "' ) pbtse			  ");
		sqlBuffer.append("    ON pbto.id = pbtse.fk_tch_opencourse_id ");
		sqlBuffer.append(" INNER JOIN (select id, learn_status ");
		sqlBuffer.append("               from training_course_student ");
		sqlBuffer.append("              where student_id = '" + sid + "'			  ");
		sqlBuffer.append("                and learn_status = '" + StudyProgress.COMPLETED + "') tcs			  ");
		sqlBuffer.append("    ON pbtse.fk_training_id = tcs.id ");
		sqlBuffer.append("  left JOIN scorm_course_info sci	 ");
		sqlBuffer.append("    ON pbtc.code = sci.id		 ");
		sqlBuffer.append("  left JOIN scorm_stu_course ssc	 ");
		sqlBuffer.append("    ON sci.id = ssc.course_id	 ");
		sqlBuffer.append(" INNER JOIN pe_bzz_student pbs	 ");
		sqlBuffer.append("    ON pbtse.fk_stu_id = pbs.id	 ");
		sqlBuffer.append("  left JOIN sso_user su		 ");
		sqlBuffer.append("    ON pbs.fk_sso_user_id = su.id	 ");
		sqlBuffer.append("   AND ssc.student_id = su.id	 ");
		sqlBuffer.append("  LEFT JOIN enum_const ec		 ");
		sqlBuffer.append("    ON pbtc.flag_coursetype = ec.id	 ");
		sqlBuffer.append(" WHERE tcs.learn_status = '" + StudyProgress.COMPLETED + "' ");
		sqlBuffer.append("   AND pbs.id = '" + stuId + "'	 ");
		sqlBuffer.append("   and pbtse.fk_stu_id = '" + stuId + "'			  ");

		if (courseType != null && !"".equalsIgnoreCase(courseType)) {
			sqlBuffer.append(" AND pbtc.flag_coursetype = '" + courseType + "'");
		}
		if (classHour != null && !"".equalsIgnoreCase(classHour)) {
			sqlBuffer.append(" AND pbtc.time = '" + classHour + "'");
		}
		sqlBuffer.append("order by  pbtc.code");
		page = this.generalDao.getByPageSQL(sqlBuffer.toString(), pageSize, startIndex);

		return page;
	}

	public Page initSiteemail(String loginId, String selTitle, String selSendDateStart, String selSendDateEnd, String selSendName,
			int pageSize, int startIndex) throws EntityException {
		Page page = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select         id,          ");
		sql.append("               title,		      ");
		sql.append("               true_name as true_name,    ");
		sql.append("               senddate,		      ");
		sql.append("               status		      ");
		sql.append("  from (				      ");
		sql.append(" select s.id, s.title, pm.true_name as true_name,  s.senddate,s.status ");
		sql.append("   from site_email s, pe_manager pm					    ");
		sql.append("  where s.sender_id = pm.login_id	and s.addressee_del=0 and s.addressee_id='" + loginId + "'				    ");
		sql.append(" union								    ");
		sql.append(" select s.id, s.title, pem.name as true_name,  s.senddate,s.status	    ");
		sql.append("   from site_email s, pe_enterprise_manager pem			    ");
		sql.append("  where s.sender_id = pem.login_id	and s.addressee_del=0 and s.addressee_id='" + loginId + "'				    ");
		sql.append(" union								    ");
		sql.append(" select s.id, s.title, ps.true_name,  s.senddate ,s.status		    ");
		sql.append("   from site_email s, pe_bzz_student ps				    ");
		sql.append("  where s.sender_id = ps.reg_no	 and s.addressee_del=0 and s.addressee_id='" + loginId + "'				    ");
		sql.append(")t where 1=1");
		if (selTitle != null && !("".equals(selTitle.trim()))) {
			sql.append(" and t.title like '%" + selTitle + "%'");
		}
		if (selSendDateStart != null && !("".equals(selSendDateStart.trim()))) {
			sql.append(" and to_date(to_char(t.senddate,'yyyy-mm-dd'), 'yyyy-mm-dd') >= to_date('" + selSendDateStart + "', 'yyyy-mm-dd')");
		}
		if (selSendDateEnd != null && !("".equals(selSendDateEnd.trim()))) {
			sql.append(" and to_date(to_char(t.senddate,'yyyy-mm-dd'), 'yyyy-mm-dd') <= to_date('" + selSendDateEnd + "', 'yyyy-mm-dd')");
		}
		if (selSendName != null && !("".equals(selSendName.trim()))) {
			sql.append("and  t.true_name like '%" + selSendName + "%'");
		}
		sql.append(" order by t.senddate desc");
		try {
			page = this.generalDao.getByPageSQL(sql.toString(), pageSize, startIndex);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

	// @Cacheable(extension = "[0]+'_'+[1]", tTLSeconds = 30)
	public Page initVoteList(int pageSize, int startIndex) throws EntityException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String now = formatter.format(new Date());
		Page page = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeVotePaper.class);
		dc.add(Restrictions.isNull("type"));
		DetachedCriteria dcEnumConstByFlagIsvalid = dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		DetachedCriteria enumConstByFlagQualificationsType = dc.createCriteria("enumConstByFlagQualificationsType",
				"enumConstByFlagQualificationsType");
		dcEnumConstByFlagIsvalid.add(Restrictions.eq("code", "1"));
		enumConstByFlagQualificationsType.add(Restrictions.or(Restrictions.eq("code", "0"), Restrictions.eq("code", "1")));
		dc.add(Restrictions.sqlRestriction("to_date(to_char(start_Date, 'yyyy-MM-dd'), 'yyyy-mm-dd') <= to_date('" + now
				+ "', 'yyyy-mm-dd')"));
		dc.addOrder(Order.desc("foundDate"));
		page = this.generalDao.getByPage(dc, pageSize, startIndex);
		return page;
	}

	// 按照资格类型查询
	// @Cacheable(extension = "[0]+'_'+[1]+'_'+[2]", tTLSeconds = 30)
	public Page initVoteListByQfType(int pageSize, int startIndex, EnumConst enumConstByFlagQualificationsType) throws EntityException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String now = formatter.format(new Date());
		Page page = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeVotePaper.class);
		dc.add(Restrictions.isNull("type"));
		DetachedCriteria dcEnumConstByFlagIsvalid = dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		DetachedCriteria enumConstByFlagQT = dc.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType");
		dcEnumConstByFlagIsvalid.add(Restrictions.eq("code", "1"));
		enumConstByFlagQT.add(Restrictions.or(Restrictions.eq("code", "9"), Restrictions.or(Restrictions.eq("code", "90"), Restrictions.eq(
				"code", enumConstByFlagQualificationsType.getCode()))));
		dc.add(Restrictions.sqlRestriction("to_date(to_char(start_Date, 'yyyy-MM-dd'), 'yyyy-mm-dd') <= to_date('" + now
				+ "', 'yyyy-mm-dd')"));
		dc.addOrder(Order.desc("foundDate"));
		page = this.generalDao.getByPage(dc, pageSize, startIndex);
		return page;
	}

	public Page initVoteListByQfType(int pageSize, int startIndex, EnumConst enumConstByFlagQualificationsType, PeBzzStudent pbs)
			throws EntityException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String now = formatter.format(new Date());
		Page page = null;
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT PVP.ID, PVP.TITLE, PVP.START_DATE STARTDATE, PVP.END_DATE ENDDATE ");
		sb.append("   FROM PE_VOTE_PAPER PVP ");
		sb.append("  INNER JOIN ENUM_CONST EC1 ");
		sb.append("     ON PVP.FLAG_ISVALID = EC1.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC2 ");
		sb.append("     ON PVP.FLAG_TYPE = EC2.ID ");
		sb.append("  WHERE PVP.TYPE IS NULL ");
		sb.append("    AND EC1.CODE = '1' ");
		if (null != pbs.getPeEnterprise().getEnumConstByFlagEnterpriseType()
				&& !"V".equals(pbs.getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode()))
			sb.append("    AND (EC2.CODE = '9' OR (EC2.CODE = '90' OR EC2.CODE = '" + enumConstByFlagQualificationsType.getCode() + "')) ");
		else
			sb
					.append("    AND (EC2.CODE = '9' OR (EC2.ID = 'jgstu' OR EC2.CODE = '" + enumConstByFlagQualificationsType.getCode()
							+ "')) ");
		sb.append("    AND TO_DATE(TO_CHAR(START_DATE, 'yyyy-MM-dd'), 'yyyy-mm-dd') <= ");
		sb.append("        TO_DATE('" + now + "', 'yyyy-mm-dd') ");
		sb.append("  ORDER BY PVP.FOUND_DATE DESC ");
		page = this.generalDao.getByPageSQL(sb.toString(), pageSize, startIndex);
		return page;
	}

	// @Cacheable(extension = "[0]", tTLSeconds = 30)
	public List<EnumConst> initCouresType(String namespace) throws EntityException {
		// TODO Auto-generated method stub
		DetachedCriteria dcType = DetachedCriteria.forClass(EnumConst.class);
		dcType.add(Restrictions.eq("namespace", "FlagCourseType"));
		List<EnumConst> courseTypeList = this.generalDao.getList(dcType);
		return courseTypeList;
	}

	// @Cacheable(extension = "[0]", tTLSeconds = 30)
	public List<EnumConst> initCourseCategory(String namespace) throws EntityException {
		// TODO Auto-generated method stub
		DetachedCriteria dcCategory = DetachedCriteria.forClass(EnumConst.class);
		dcCategory.add(Restrictions.eq("namespace", "FlagCourseCategory"));
		List<EnumConst> courseCategoryList = this.generalDao.getList(dcCategory);
		return courseCategoryList;
	}

	// @Cacheable(extension = "[0]", tTLSeconds = 30)
	public List<EnumConst> initCourseItemType(String namespace) throws EntityException {
		// TODO Auto-generated method stub
		DetachedCriteria dcEnumConst = DetachedCriteria.forClass(EnumConst.class);
		dcEnumConst.add(Restrictions.eq("namespace", namespace));
		List<EnumConst> courseCategoryList = this.generalDao.getList(dcEnumConst);
		return courseCategoryList;
	}

	// @Cacheable(extension = "[0]+'_'+[1]+'_'+[2]+'_'+[3]+'_'+[4]+'_'+[5]",
	// tTLSeconds = 3)
	public Page initUnelectivedCourses(String stuId, String courseCategory, String courseType, String classHour, int pageSize,
			int startIndex) throws EntityException {
		Page page = null;
		DetachedCriteria dceletived = DetachedCriteria.forClass(PrBzzTchStuElective.class);
		dceletived.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
		dceletived.createAlias("peBzzStudent", "peBzzStudent");
		dceletived.add(Restrictions.eq("peBzzStudent.id", stuId));
		dceletived.setProjection(Property.forName("peBzzTchCourse.id"));
		// dceletived.add(Restrictions.eq("enumConstByFlagElectivePayStatus",
		// ec));
		EnumConst enumConstByFlagIsFree = this.enumConstDao.getByNamespaceCode("FlagIsFree", "1");// 收费

		DetachedCriteria dcCourseFree = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dcCourseFree.setProjection(Property.forName("id"));
		dcCourseFree.add(Restrictions.eq("enumConstByFlagIsFree", enumConstByFlagIsFree));

		EnumConst enumConstByFlagBatchType = this.enumConstDao.getByNamespaceCode("FlagBatchType", "1");

		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		dc.createCriteria("peBzzTchCourse", "peBzzTchCourse").createAlias("enumConstByFlagCourseType", "enumConstByFlagCourseType")
				.createAlias("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory");
		dc.add(Property.forName("peBzzTchCourse.id").notIn(dceletived));// 排除已选课程
		dc.add(Property.forName("peBzzTchCourse.id").notIn(dcCourseFree)); // 排除免费课程
		dc.createAlias("peBzzBatch", "peBzzBatch");
		dc.add(Restrictions.eq("peBzzBatch.enumConstByFlagBatchType", enumConstByFlagBatchType));

		// 排除选课单中的课程
		List opencourseIdList;// 选课单
		if (ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList") != null) {
			opencourseIdList = (List) ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList");
			if (opencourseIdList.size() > 0) {
				dc.add(Restrictions.not(Restrictions.in("id", opencourseIdList)));
			}
		}

		if (courseCategory != null && !"".equalsIgnoreCase(courseCategory)) {
			dc.add(Restrictions.eq("enumConstByFlagCourseCategory.id", courseCategory));
		}
		if (courseType != null && !"".equalsIgnoreCase(courseType)) {
			dc.add(Restrictions.eq("enumConstByFlagCourseType.id", courseType));
		}
		if (classHour != null && !"".equalsIgnoreCase(classHour)) {
			dc.add(Restrictions.like("peBzzTchCourse.time", classHour, MatchMode.START));
		}

		try {
			page = this.generalDao.getByPage(dc, pageSize, startIndex);
			BigDecimal price = new BigDecimal(this.enumConstDao.getByNamespaceCode("ClassHourRate", "0").getName()).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			ServletActionContext.getRequest().setAttribute("price", price);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return page;
	}

	// @Cacheable(extension = "[0]", tTLSeconds = 60)
	public BigDecimal initCoursePrice(String namespace) throws EntityException {
		// TODO Auto-generated method stub
		BigDecimal price = new BigDecimal(this.enumConstDao.getByNamespaceCode(namespace, "0").getName());
		return price.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	// @Cacheable(extension = "[0]+'_'+[1]+'_'+[2]", tTLSeconds = 60)
	public Page initTrainview(String batchId, int pageSize, int startIndex) throws EntityException {
		// TODO Auto-generated method stub
		Page page = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		dc.createCriteria("peBzzBatch", "peBzzBatch").add(Restrictions.eq("id", batchId));

		dc.createAlias("peBzzTchCourse", "pc").createAlias("pc.enumConstByFlagCourseType", "enumConstByFlagCourseType");

		page = this.generalDao.getByPage(dc, pageSize, startIndex);
		return page;
	}

	public void updateOrderAndInvoice(PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo) throws EntityException {
		// TODO Auto-generated method stub
		if (peBzzOrderInfo != null) {
			this.generalDao.save(peBzzOrderInfo);
		}
		if (peBzzInvoiceInfo != null) {
			this.generalDao.save(peBzzInvoiceInfo);
		}

	}

	// //@Cacheable(extension = "[0]+'_'+[1]+'_'+[2]+'_'+[3]", tTLSeconds = 30)
	public Page initHistoryRecord(String userId, String paymentDateStart, String paymentDateEnd, int pageSize, int startIndex,
			String orderno, String price, String cname) throws ParseException {
		Page page = null;
		// Lee 重新编写SQL查询订单列表 计算学时
		String sql = "";
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		//dc.createCriteria("enumConstByFlagOrderIsValid", "enumConstByFlagOrderIsValid"); // 订单是否有效
		//dc.add(Restrictions.eq("enumConstByFlagOrderIsValid.code", "1"));
		dc.createCriteria("enumConstByFlagPaymentState", "enumConstByFlagPaymentState");
		dc.createCriteria("enumConstByFlagPaymentMethod", "enumConstByFlagPaymentMethod");
		dc.createCriteria("ssoUser", "ssoUser");
		dc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState", DetachedCriteria.LEFT_JOIN);
		// dc.add(Restrictions.eq("enumConstByFlagOrderType",
		// enumConstByFlagOrderType));
		dc.add(Restrictions.eq("ssoUser.id", userId));
		// dc.addOrder(Order.desc("seq"));
		if (paymentDateStart != null && !paymentDateStart.trim().equals("")) {

			dc.add(Restrictions.sqlRestriction("to_date(to_char(PAYMENT_DATE, 'yyyy-MM-dd'), 'yyyy-mm-dd') >= to_date('" + paymentDateStart
					+ "', 'yyyy-mm-dd')"));
			// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss");
			// Date date1 = format.parse(paymentDate + " 00:00:00 ");
			// Date date2 = format.parse(paymentDate + " 24:00:00 ");
			// date2.setDate(date1.getDate()+1);
			// dc.add(Restrictions.between("paymentDate", date1, date2));
		}
		if (paymentDateEnd != null && !paymentDateEnd.trim().equals("")) {
			dc.add(Restrictions.sqlRestriction("to_date(to_char(PAYMENT_DATE, 'yyyy-MM-dd'), 'yyyy-mm-dd') <= to_date('" + paymentDateEnd
					+ "', 'yyyy-mm-dd')"));
		}
		if (orderno != null && !orderno.trim().equals("")) {
			dc.add(Restrictions.sqlRestriction(" seq like '%" + orderno + "%' "));
		}
		if (price != null && !price.trim().equals("")) {
			dc.add(Restrictions.sqlRestriction(" THIS_.AMOUNT = " + price));
		}
		if (cname != null && !cname.trim().equals("")) {
			dc.add(Restrictions.sqlRestriction(" THIS_.name like '%" + cname + "%'"));
		}
		dc.addOrder(Order.desc("paymentDate")).addOrder(Order.desc("seq"));
		page = this.generalDao.getByPage(dc, pageSize, startIndex);
		/*
		 * Set set =
		 * ((PeBzzOrderInfo)page.getItems().get(0)).getPeBzzInvoiceInfos();
		 * Iterator<PeBzzInvoiceInfo> it = set.iterator(); PeBzzInvoiceInfo i =
		 * it.next(); String name = i.getEnumConstByFlagFpOpenState().getName();
		 * System.out.println("*********************************"+name);
		 */
		return page;
	}

	// //@Cacheable(extension = "[0]", tTLSeconds = 30) 去掉缓存注解
	public List initHistoryRecordStatistics(String userId) {
		// TODO Auto-generated method stub
		/*
		 * String sql = "select o1.yufufei, o2.wangyin\n" + " from (select
		 * count(o.id) as yufufei\n" + " from pe_bzz_order_info o\n" + " inner
		 * join enum_const ec1 on ec1.id = o.flag_order_isvalid\n" + " and
		 * ec1.code = '1'\n" + " inner join sso_user u on u.id =
		 * o.create_user\n" + " and u.id = '"+userId+"'\n" + " inner join
		 * enum_const ec2 on ec2.id = o.flag_payment_method\n" + " and ec2.code =
		 * '1' --预付费\n" + " ) o1\n" + " inner join\n" + "\n" + " (select
		 * count(o.id) as wangyin\n" + " from pe_bzz_order_info o\n" + " inner
		 * join enum_const ec1 on ec1.id = o.flag_order_isvalid\n" + " and
		 * ec1.code = '1'\n" + " inner join sso_user u on u.id =
		 * o.create_user\n" + " and u.id = '"+userId+"'\n" + " inner join
		 * enum_const ec2 on ec2.id = o.flag_payment_method\n" + " and ec2.code =
		 * '0' --网银\n" + " ) o2 on 1 = 1";
		 */
		StringBuffer sql = new StringBuffer();
		sql.append(" select sum(case when ecmethod.code = '1'  then 1 else 0 end) as yufufei,	\n");
		sql.append(" sum(case when ecmethod.code = '0' then 1 else 0 end) as wangyin           \n");
		sql.append("	from pe_bzz_order_info pboi, enum_const ec,sso_user su,enum_const ecstat, \n");
		sql.append("	enum_const ecmethod,enum_const ectype where                               \n");
		sql.append(" pboi.flag_order_isvalid = ec.id and ec.code = '1'                         \n");
		sql.append(" and su.id = pboi.create_user                                              \n");
		sql.append(" and ecstat.id = pboi.FLAG_PAYMENT_STATE                                   \n");
		sql.append(" and ecmethod.id = pboi.FLAG_PAYMENT_METHOD                                \n");
		sql.append(" and ectype.id = pboi.flag_order_type and ectype.code = '0'                \n");
		sql.append(" and pboi.create_user = '" + userId + "'                 \n");
		List list = this.generalDao.getBySQL(sql.toString());
		return list;
	}

	// @Cacheable(extension = "[0]", tTLSeconds = 30)
	public String countStudentTotalLearningHours(Map<String, String> params) {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sid = us.getSsoUser().getId();

		String result = "";
		String studentId = params.get("studentId");
		String courseCategory = params.get("courseCategory");
		String courseType = params.get("courseType");
		String courseItemType = params.get("courseItemType");
		String courseCode = params.get("courseCode");
		String courseName = params.get("courseName");
		StringBuffer sql = new StringBuffer();

		sql.append(" select nvl(sum(to_number(nvl(pbtc.time, 0))),0) as total_time from ");
		sql.append(" PR_BZZ_TCH_STU_ELECTIVE pbtse,         ");
		sql.append(" enum_const ec                          ");
		// sql.append(" ,PE_BZZ_STUDENT pbs ");
		sql.append(" ,PR_BZZ_TCH_OPENCOURSE pbto            ");
		sql.append(" ,PE_BZZ_TCH_COURSE pbtc                ");
		sql.append(" ,TRAINING_COURSE_STUDENT tcs           ");
		sql.append(" ,enum_const ec_category                ");
		sql.append(" ,enum_const ec_coursetype              ");
		sql.append(" ,enum_const ec_courseitem              ");
		sql.append("                                        ");
		sql.append(" where pbtse.FLAG_ELECTIVE_PAY_STATUS = ec.id        ");
		sql.append(" and ec.code = '1'                      ");
		sql.append(" and pbtse.FK_STU_ID =  '" + studentId + "'            ");
		// sql.append(" and pbs.id = '"+ studentId +"' ");
		sql.append(" and pbtse.Fk_Tch_Opencourse_Id = pbto.id            ");
		sql.append(" and pbto.fk_course_id = pbtc.id        ");
		sql.append(" and pbtse.FK_TRAINING_ID = tcs.id      ");
		sql.append(" and tcs.learn_status <> 'COMPLETED'    ");
		sql.append(" and tcs.student_id = '" + sid + "'       ");
		sql.append(" and pbtc.flag_coursecategory = ec_category.id       ");
		sql.append(" and pbtc.flag_coursetype = ec_coursetype.id         ");
		sql.append(" and pbtc.FLAG_COURSE_ITEM_TYPE = ec_courseitem.id         ");

		if (!StringUtils.defaultString(courseCategory).equals("")) {
			sql.append(" and ec_category.id  = '" + courseCategory + "'    ");
		}
		if (!StringUtils.defaultString(courseType).equals("")) {
			sql.append(" and ec_coursetype.id  = '" + courseType + "'  	 ");
		}
		if (!StringUtils.defaultString(courseItemType).equals("")) {
			sql.append(" and ec_courseitem.id  = '" + courseItemType + "'  	 ");
		}
		if (!StringUtils.defaultString(courseCode).equals("")) {
			sql.append(" and pbtc.code  like '%" + courseCode + "%'  	 ");
		}
		if (!StringUtils.defaultString(courseName).equals("")) {
			sql.append(" and pbtc.name  like '%" + courseName + "%'  	 ");
		}

		List list = this.getGeneralDao().getBySQL(sql.toString());
		DecimalFormat df = new DecimalFormat("#.0");
		result = df.format(Double.valueOf(list.get(0).toString()));
		BigDecimal b = new BigDecimal(result);
		b.setScale(1);
		return b.toString();
	}

	// @Cacheable(extension = "[0]+'_'+[1]+'_'+[2]+'_'+[3]+'_'+[4]+'_'+[5]",
	// tTLSeconds = 30)
	public Page initCompletedCourses(String stuId, String courseType, String examStatus, String classHour, int pageSize, int startIndex)
			throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		EnumConst ec = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		EnumConst ec_isExam = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		String sid = us.getSsoUser().getId();
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT DISTINCT pbtc.id          AS courseId, ");
		sqlBuffer.append("                pbtc.code        AS courseCode,			  ");
		sqlBuffer.append("                pbtc.name        AS courseName,			  ");
		sqlBuffer.append("                pbtc.time        AS courseTime,			  ");
		sqlBuffer.append("                ec.name          AS courseType,			  ");
		sqlBuffer.append("                tcs.GET_DATE 	   AS startDate,			  ");
		sqlBuffer.append("                ssc.last_date    AS endDate,			  	  ");
		sqlBuffer.append("                tcs.learn_status AS leatnResult,			  ");
		sqlBuffer.append("                pbto.id          AS opcourseId,			  ");
		sqlBuffer.append("                pbtc.teacher     AS pbtcTeacher,			  ");
		sqlBuffer.append("  case when pbtc.FLAG_IS_EXAM = '" + ec.getId() + "' then 'NOEXAM' ");
		sqlBuffer.append("	when nvl(pbtse.score_exam,0) >= nvl(pbtc.passrole,0) then 'PASS' else 'FAILED' end as score_exam, ");
		sqlBuffer.append("                pbtc.studydates  AS studydates,ec2.code as visitcode,			  ");
		sqlBuffer.append(" case  when pbtc.FLAG_IS_EXAM = '" + ec_isExam.getId()
				+ "' and nvl(pbtse.score_exam, 0) < nvl(pbtc.passrole, 0) \n");
		sqlBuffer.append(" and pbtse.exam_times >= pbtc.examtimes_allow then 'GOBUY' else 'GOON' end as exam_status \n");
		// Lee start 2014年3月11日 课程有效日期
		sqlBuffer
				.append(" ,CASE WHEN PBTC.STUDYDATES = 0 THEN '-' ELSE TO_CHAR(PBTSE.START_TIME + PBTC.STUDYDATES,'yyyy-MM-dd hh24:mi:ss') END AS GQSJ ");
		// Lee end
		sqlBuffer.append("  FROM pe_bzz_tch_course pbtc	 ");
		sqlBuffer.append(" INNER JOIN pr_bzz_tch_opencourse pbto ");
		sqlBuffer.append("    ON pbtc.id = pbto.fk_course_id	 ");
		sqlBuffer.append(" INNER JOIN (select fk_stu_id, fk_training_id, fk_tch_opencourse_id,score_exam,exam_times,COMPLETED_TIME  ");
		// Lee start 2014年3月11日 课程有效日期
		sqlBuffer.append(",START_TIME ");
		// Lee end
		sqlBuffer.append("               from pr_bzz_tch_stu_elective ");
		sqlBuffer.append("              where fk_stu_id = '" + stuId + "' ) pbtse			  ");
		sqlBuffer.append("    ON pbto.id = pbtse.fk_tch_opencourse_id ");
		sqlBuffer.append(" INNER JOIN (select id, learn_status,GET_DATE ");
		sqlBuffer.append("               from training_course_student ");
		sqlBuffer.append("              where student_id = '" + sid + "'			  ");
		sqlBuffer.append("                and learn_status = '" + StudyProgress.COMPLETED + "') tcs			  ");
		sqlBuffer.append("    ON pbtse.fk_training_id = tcs.id ");
		sqlBuffer.append("  left JOIN scorm_course_info sci	 ");
		sqlBuffer.append("    ON pbtc.code = sci.id		 ");
		sqlBuffer.append("  left JOIN (select * from scorm_stu_course where student_id='" + stuId + "') ssc	 ");
		sqlBuffer.append("    ON sci.id = ssc.course_id	 ");
		sqlBuffer.append(" INNER JOIN pe_bzz_student pbs	 ");
		sqlBuffer.append("    ON pbtse.fk_stu_id = pbs.id	 ");
		sqlBuffer.append("  left JOIN sso_user su		 ");
		sqlBuffer.append("    ON pbs.fk_sso_user_id = su.id	 ");
		sqlBuffer.append("  LEFT JOIN enum_const ec		 ");
		sqlBuffer.append("    ON pbtc.flag_coursetype = ec.id	LEFT JOIN enum_const ec2 ON pbtc.FLAG_ISVISITAFTERPASS = ec2.id		where 1=1	");
		// sqlBuffer.append(" and
		// pbtc.flag_isfree='40288a7b39968644013996bf8714004c' ");
		sqlBuffer.append("	and ( ( ");
		sqlBuffer.append("  pbtc.FLAG_IS_EXAM = '" + ec.getId() + "' ");
		sqlBuffer.append("  and pbtse.COMPLETED_TIME is null ) or ( pbtc.FLAG_IS_EXAM = '" + ec_isExam.getId()
				+ "' and nvl(pbtse.score_exam,0) < nvl(pbtc.passrole,0) ) )");
		// Lee start 2014年2月18日 添加查询条件 课程为收费课程
		sqlBuffer.append(" AND PBTC.FLAG_ISFREE <> '40288a7b39968644013996bf01e7004b' ");
		// Lee end
		if (courseType != null && !"".equalsIgnoreCase(courseType)) {
			sqlBuffer.append(" AND pbtc.flag_coursetype = '" + courseType + "'");
		}
		if (classHour != null && !"".equalsIgnoreCase(classHour)) {
			sqlBuffer.append(" AND to_number(pbtc.time)-to_number( '" + classHour + "') = 0 ");
		}
		if (StudyProgress.REQUIRED.equals(examStatus)) {
			sqlBuffer.append(" AND pbtc.FLAG_IS_EXAM = '" + ec_isExam.getId() + "' ");
		} else if (StudyProgress.NOTREQUIRED.equals(examStatus)) {
			sqlBuffer.append(" AND pbtc.FLAG_IS_EXAM = '" + ec.getId() + "' ");
		}
		sqlBuffer.append(" order by  pbtc.code ");
		page = this.generalDao.getByPageSQL(sqlBuffer.toString(), pageSize, startIndex);
		return page;
	}

	/**
	 * 报名历史，最后一行订单数统计
	 * 
	 * @param userId
	 * @return
	 */
	public List initHistoryRecordStatistics(Map params) {
		String userId = (String) params.get("userId");
		// String payDate = (String) params.get("payDate");
		String paymentDateStart = (String) params.get("paymentDateStart");
		String paymentDateEnd = (String) params.get("paymentDateEnd");
		String orderno = (String) params.get("orderno");
		String orderprice = (String) params.get("orderprice");
		String tchprice = (String) params.get("tchprice");
		String fpstatus = (String) params.get("fpstatus");
		String cname = (String) params.get("cname");
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT SUM(CASE WHEN C.CODE = '1' THEN 1 ELSE 0 END) AS YUFUFEI, ");
		sql.append("        SUM(CASE WHEN C.CODE = '0' THEN 1 ELSE 0 END) AS WANGYIN ");
		sql.append("  FROM PE_BZZ_ORDER_INFO A ");
		sql.append("  INNER JOIN ENUM_CONST B ON A.FLAG_PAYMENT_STATE = B.ID ");
		sql.append("  INNER JOIN ENUM_CONST C ON A.FLAG_PAYMENT_METHOD = C.ID ");
		sql.append("  LEFT OUTER JOIN ENUM_CONST D ON A.FLAG_REFUND_STATE = D.ID ");
		sql.append("  INNER JOIN SSO_USER E ON A.CREATE_USER = E.ID ");
		sql.append("  INNER JOIN ENUM_CONST F ON A.FLAG_ORDER_ISVALID = F.ID ");
		sql.append("  LEFT OUTER JOIN PE_BZZ_BATCH G ON A.FK_BATCH_ID = G.ID ");
		sql.append("  LEFT OUTER JOIN PE_ELECTIVE_COURSE_PERIOD H ON A.FK_COURSE_PERIOD_ID = H.ID ");
		sql.append("  LEFT OUTER JOIN PE_SIGNUP_ORDER I ON A.FK_SIGNUP_ORDER_ID = I.ID ");
		// 添加连接表zgl 9.29
		sql.append(" LEFT JOIN PE_BZZ_INVOICE_INFO pbii ON pbii.fk_order_id= A.id ");
		sql.append("  WHERE F.CODE = '1' AND E.ID = '" + userId + "' ");
		if (paymentDateStart != null && !paymentDateStart.equals("")) {
			sql.append(" and to_date(to_char(a.PAYMENT_DATE,'yyyy-mm-dd'),'yyyy-mm-dd') >= to_date('" + paymentDateStart
					+ "','yyyy-mm-dd') ");
		}
		if (paymentDateEnd != null && !paymentDateEnd.equals("")) {
			sql
					.append(" and to_date(to_char(a.PAYMENT_DATE,'yyyy-mm-dd'),'yyyy-mm-dd') <= to_date('" + paymentDateEnd
							+ "','yyyy-mm-dd') ");
		}
		if (orderno != null && !orderno.equals("")) {
			sql.append(" and a.seq like '%" + orderno + "%'");
		}
		if (orderprice != null && !orderprice.equals("")) {
			sql.append(" and a.amount='" + orderprice + "'");
		}
		if (tchprice != null && !tchprice.equals("")) {
			sql.append(" and a.amount=" + tchprice + "");
		}
		if (fpstatus != null && !fpstatus.equals("")) {
			sql.append(" and pbii.FLAG_PRINT_STATUS='" + fpstatus + "'");
		}
		if (cname != null && !cname.equals("")) {
			sql.append(" and a.name like '%" + cname + "%'");
		}
		List list = null;
		try {
			list = this.generalDao.getBySQL(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList();
			list.add(0);
			list.add(0);
		}
		return list;
	}

	public String initTrainviewClassNum(String id) {
		// TODO Auto-generated method stub
		String sql = "select sum(pbtc.time) from pr_bzz_tch_opencourse op\n"
				+ " inner join pe_bzz_tch_course pbtc on pbtc.id = op.fk_course_id\n" + " where  op.fk_batch_id = '" + id + "'";
		List list = this.generalDao.getBySQL(sql);
		if (list.size() > 0) {
			return list.get(0).toString();
		} else {
			return "0";
		}

	}

	public List tongjiCompletedCourse(String stuId, String courseType, String examStatus, String classHour) throws EntityException {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sid = us.getSsoUser().getId();
		EnumConst ec = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		EnumConst ec_isExam = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT 		  nvl(sum(pbtc.time), 0) as time ");
		sqlBuffer.append("  FROM pe_bzz_tch_course pbtc	 ");
		sqlBuffer.append(" INNER JOIN pr_bzz_tch_opencourse pbto ");
		sqlBuffer.append("    ON pbtc.id = pbto.fk_course_id	 ");
		sqlBuffer.append(" INNER JOIN (select fk_stu_id, fk_training_id, fk_tch_opencourse_id,score_exam,COMPLETED_TIME  ");
		sqlBuffer.append("               from pr_bzz_tch_stu_elective ");
		sqlBuffer.append("              where fk_stu_id = '" + stuId + "' ) pbtse			  ");
		sqlBuffer.append("    ON pbto.id = pbtse.fk_tch_opencourse_id ");
		sqlBuffer.append(" INNER JOIN (select id, learn_status,GET_DATE ");
		sqlBuffer.append("               from training_course_student ");
		sqlBuffer.append("              where student_id = '" + sid + "'			  ");
		sqlBuffer.append("                and learn_status = '" + StudyProgress.COMPLETED + "') tcs			  ");
		sqlBuffer.append("    ON pbtse.fk_training_id = tcs.id ");
		sqlBuffer.append("  left JOIN scorm_course_info sci	 ");
		sqlBuffer.append("    ON pbtc.code = sci.id		 ");
		sqlBuffer.append("  left JOIN (select * from scorm_stu_course where student_id='" + stuId + "') ssc	 ");
		sqlBuffer.append("    ON sci.id = ssc.course_id	 ");
		sqlBuffer.append(" INNER JOIN pe_bzz_student pbs	 ");
		sqlBuffer.append("    ON pbtse.fk_stu_id = pbs.id	 ");
		sqlBuffer.append("  left JOIN sso_user su		 ");
		sqlBuffer.append("    ON pbs.fk_sso_user_id = su.id	 ");
		sqlBuffer.append("  LEFT JOIN enum_const ec		 ");
		sqlBuffer.append("    ON pbtc.flag_coursetype = ec.id	LEFT JOIN enum_const ec2 ON pbtc.FLAG_ISVISITAFTERPASS = ec2.id		where 1=1	");
		sqlBuffer.append("	and ( pbtc.FLAG_IS_EXAM = '" + ec_isExam.getId() + "' and nvl(pbtse.score_exam,0) < nvl(pbtc.passrole,0) ) ");
		if (courseType != null && !"".equalsIgnoreCase(courseType)) {
			sqlBuffer.append(" AND pbtc.flag_coursetype = '" + courseType + "'");
		}
		if (classHour != null && !"".equalsIgnoreCase(classHour)) {
			sqlBuffer.append(" AND to_number(pbtc.time)-to_number( '" + classHour + "') = 0 ");
		}
		if (StudyProgress.REQUIRED.equals(examStatus)) {
			sqlBuffer.append(" AND pbtc.FLAG_IS_EXAM = '" + ec_isExam.getId() + "' ");
		} else if (StudyProgress.NOTREQUIRED.equals(examStatus)) {
			sqlBuffer.append(" AND pbtc.FLAG_IS_EXAM = '" + ec.getId() + "' ");
		}
		List list = this.generalDao.getBySQL(sqlBuffer.toString());
		return list;
	}

	public List tongjiCompletedCourse(String stuId, String courseType, String courseCode, String courseName, String time, String teacher,
			String coursearea) throws EntityException {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sid = us.getSsoUser().getId();
		EnumConst ec_isExam = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT 		  nvl(sum(pbtc.time), 0) as time ");
		sqlBuffer.append("  FROM pe_bzz_tch_course pbtc	 ");
		sqlBuffer.append(" INNER JOIN pr_bzz_tch_opencourse pbto ");
		sqlBuffer.append("    ON pbtc.id = pbto.fk_course_id	 ");
		sqlBuffer.append(" INNER JOIN (select fk_stu_id, fk_training_id, fk_tch_opencourse_id,score_exam,COMPLETED_TIME  ");
		sqlBuffer.append("               from pr_bzz_tch_stu_elective ");
		sqlBuffer.append("              where fk_stu_id = '" + stuId + "' ) pbtse			  ");
		sqlBuffer.append("    ON pbto.id = pbtse.fk_tch_opencourse_id ");
		sqlBuffer.append(" INNER JOIN (select id, learn_status,GET_DATE ");
		sqlBuffer.append("               from training_course_student ");
		sqlBuffer.append("              where student_id = '" + sid + "'			  ");
		sqlBuffer.append("                and learn_status = '" + StudyProgress.COMPLETED + "') tcs			  ");
		sqlBuffer.append("    ON pbtse.fk_training_id = tcs.id ");
		sqlBuffer.append("  left JOIN scorm_course_info sci	 ");
		sqlBuffer.append("    ON pbtc.code = sci.id		 ");
		sqlBuffer.append("  left JOIN (select * from scorm_stu_course where student_id='" + stuId + "') ssc	 ");
		sqlBuffer.append("    ON sci.id = ssc.course_id	 ");
		sqlBuffer.append(" INNER JOIN pe_bzz_student pbs	 ");
		sqlBuffer.append("    ON pbtse.fk_stu_id = pbs.id	 ");
		sqlBuffer.append("  left JOIN sso_user su		 ");
		sqlBuffer.append("    ON pbs.fk_sso_user_id = su.id	 ");
		sqlBuffer.append("  LEFT JOIN enum_const ec		 ");
		sqlBuffer.append("    ON pbtc.flag_coursetype = ec.id	LEFT JOIN enum_const ec2 ON pbtc.FLAG_ISVISITAFTERPASS = ec2.id		where 1=1	");
		sqlBuffer.append("	and ( pbtc.FLAG_IS_EXAM = '" + ec_isExam.getId() + "' and nvl(pbtse.score_exam,0) < nvl(pbtc.passrole,0) ) ");
		if (null != courseType && !"".equals(courseType))
			sqlBuffer.append(" AND PBTC.FLAG_COURSETYPE = '" + courseType + "' ");
		if (null != courseCode && !"".equals(courseCode))
			sqlBuffer.append(" AND UPPER(PBTC.CODE) LIKE '%" + courseCode.toUpperCase() + "%'");
		if (null != courseName && !"".equals(courseName))
			sqlBuffer.append(" AND UPPER(PBTC.NAME) LIKE '%" + courseName.toUpperCase() + "%'");
		if (null != time && !"".equals(time))
			sqlBuffer.append(" AND PBTC.TIME = '" + time + "'");
		if (null != teacher && !"".equals(teacher))
			sqlBuffer.append(" AND UPPER(PBTC.TEACHER) LIKE '%" + teacher.toUpperCase() + "%'");
		if (null != coursearea && !"".equals(coursearea))
			sqlBuffer.append(" AND PBTC.FLAG_COURSEAREA = '" + coursearea + "'");
		List list = this.generalDao.getBySQL(sqlBuffer.toString());
		return list;
	}

	// @Cacheable(extension = "[0]", tTLSeconds = 30)
	public Page initPassedCourses(Map params) throws EntityException {
		String stuId = (String) params.get("studentId");
		String courseType = (String) params.get("courseType");
		String examStatus = (String) params.get("examStatus");
		String classHour = (String) params.get("classHour");
		// Lee start 2014年3月5日 添加获得学时时间查询条件
		String startDate = (String) params.get("startDate");
		String endDate = (String) params.get("endDate");
		// Lee end
		int pageSize = (Integer) params.get("pageSize");
		int startIndex = (Integer) params.get("startIndex");

		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		EnumConst ec = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		EnumConst ec_isExam = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		EnumConst ec_isNotExam = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		String sid = us.getSsoUser().getId();
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT DISTINCT pbtc.id          AS courseId, ");
		sqlBuffer.append("                pbtc.code        AS courseCode,			  ");
		sqlBuffer.append("                pbtc.name        AS courseName,			  ");
		sqlBuffer.append("                pbtc.time        AS courseTime,			  ");
		sqlBuffer.append("                ec.name          AS courseType,			  ");
		sqlBuffer.append("                tcs.GET_DATE 	   AS startDate,			  ");
		sqlBuffer.append("                ssc.last_date    AS endDate,			  	  ");
		sqlBuffer.append("                tcs.learn_status AS leatnResult,			  ");
		sqlBuffer.append("                pbto.id          AS opcourseId,			  ");
		sqlBuffer.append("                pbtc.teacher     AS pbtcTeacher,			  ");
		sqlBuffer.append("  case when pbtc.FLAG_IS_EXAM = '" + ec.getId() + "' then 'NOEXAM' ");
		sqlBuffer.append("	when nvl(pbtse.score_exam,0) >= nvl(pbtc.passrole,0) then 'PASS' else 'FAILED' end as score_exam, ");
		sqlBuffer.append("                pbtc.studydates  AS studydates,ec2.code as visitcode			  ");
		sqlBuffer.append("  FROM pe_bzz_tch_course pbtc	 ");
		sqlBuffer.append(" INNER JOIN pr_bzz_tch_opencourse pbto ");
		sqlBuffer.append("    ON pbtc.id = pbto.fk_course_id	 ");
		sqlBuffer.append(" INNER JOIN (select fk_stu_id, fk_training_id, fk_tch_opencourse_id,score_exam,COMPLETED_TIME  ");
		sqlBuffer.append("               from pr_bzz_tch_stu_elective ");
		sqlBuffer.append("              where fk_stu_id = '" + stuId + "' ");
		// Lee start 2014年3月5日 判断开始时间结束时间拼接sql
		if ((startDate != null && !"".equals(startDate) && !"null".equals(startDate))
				&& (endDate == null || "".equals(endDate) || "null".equals(endDate))) {// 有开始没结束
			sqlBuffer.append(" AND COMPLETED_TIME >= TO_DATE('" + startDate + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
		}
		if ((endDate != null && !"".equals(endDate) && !"null".equals(endDate))
				&& (startDate == null || "".equals(startDate) || "null".equals(startDate))) {// 有结束没开始
			sqlBuffer.append(" AND COMPLETED_TIME <= TO_DATE('" + endDate + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
		if ((startDate != null && !"".equals(startDate) && !"null".equals(startDate))
				&& (endDate != null && !"".equals(endDate) && !"null".equals(endDate))) {// 有开始有结束
			sqlBuffer.append(" AND COMPLETED_TIME BETWEEN TO_DATE('" + startDate + " 00:00:00','yyyy-MM-dd hh24:mi:ss') AND TO_DATE('"
					+ endDate + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
		// Lee end
		sqlBuffer.append(" ) pbtse			  ");
		sqlBuffer.append("    ON pbto.id = pbtse.fk_tch_opencourse_id ");
		sqlBuffer.append(" INNER JOIN (select id, learn_status,GET_DATE ");
		sqlBuffer.append("               from training_course_student ");
		sqlBuffer.append("              where student_id = '" + sid + "'			  ");
		sqlBuffer.append("                and learn_status = '" + StudyProgress.COMPLETED + "') tcs			  ");
		sqlBuffer.append("    ON pbtse.fk_training_id = tcs.id ");
		sqlBuffer.append("  left JOIN scorm_course_info sci	 ");
		sqlBuffer.append("    ON pbtc.code = sci.id		 ");
		sqlBuffer.append("  left JOIN (select * from scorm_stu_course where student_id='" + stuId + "') ssc	 ");
		sqlBuffer.append("    ON sci.id = ssc.course_id	 ");
		sqlBuffer.append(" INNER JOIN pe_bzz_student pbs	 ");
		sqlBuffer.append("    ON pbtse.fk_stu_id = pbs.id	 ");
		sqlBuffer.append("  left JOIN sso_user su		 ");
		sqlBuffer.append("    ON pbs.fk_sso_user_id = su.id	 ");
		sqlBuffer.append("  LEFT JOIN enum_const ec		 ");
		sqlBuffer.append("    ON pbtc.flag_coursetype = ec.id	LEFT JOIN enum_const ec2 ON pbtc.FLAG_ISVISITAFTERPASS = ec2.id		where 1=1	");
		// sqlBuffer.append(" and pbtc.flag_isfree='"+ec_isFree.getId()+"' ");
		if (courseType != null && !"".equalsIgnoreCase(courseType)) {
			sqlBuffer.append(" AND pbtc.flag_coursetype = '" + courseType + "'");
		}
		if (classHour != null && !"".equalsIgnoreCase(classHour)) {
			sqlBuffer.append(" AND to_number(pbtc.time)-to_number( '" + classHour + "') = 0 ");
		}
		if (StudyProgress.PASSED.equals(examStatus)) {
			sqlBuffer.append(" and ( ( pbtc.FLAG_IS_EXAM = '" + ec_isExam.getId()
					+ "' AND nvl(pbtse.score_exam,0) >= nvl(pbtc.passrole,0) ) ");
			sqlBuffer.append(" or ( pbtc.FLAG_IS_EXAM = '" + ec_isNotExam.getId() + "' and  pbtse.COMPLETED_TIME is not null ) ) ");
		} else if (StudyProgress.FAILED.equals(examStatus)) {
			sqlBuffer.append(" and pbtc.FLAG_IS_EXAM = '" + ec_isExam.getId() + "' AND nvl(pbtse.score_exam,0) < nvl(pbtc.passrole,0) ");
		}
		// Lee start 2014年2月18日 免费课程 完成学习不用填写调查问卷即可出现在已通过课程中
		// sqlBuffer
		// .append(" OR PBTC.FLAG_ISFREE = '40288a7b39968644013996bf01e7004b'
		// ");
		// Lee end
		sqlBuffer.append("order by  pbtc.code");
		page = this.generalDao.getByPageSQL(sqlBuffer.toString(), pageSize, startIndex);
		return page;
	}

	public List statisPassedCourse(Map params) throws EntityException {
		String stuId = (String) params.get("studentId");
		String courseCode = (String) params.get("courseCode");
		String courseName = (String) params.get("courseName");
		String courseType = (String) params.get("courseType");
		String examStatus = (String) params.get("examStatus");
		String classHour = (String) params.get("classHour");
		// Lee start 2014年3月5日 添加获得学时时间查询条件
		String startDate = (String) params.get("startDate");
		String endDate = (String) params.get("endDate");
		// Lee end

		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		EnumConst ec_isNotExam = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		EnumConst ec_isExam = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		EnumConst ec_isFree = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsFree", "0");

		String sid = us.getSsoUser().getId();
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT nvl(sum(pbtc.time), 0) as time ");
		sqlBuffer.append("  FROM pe_bzz_tch_course pbtc	 ");
		sqlBuffer.append(" INNER JOIN pr_bzz_tch_opencourse pbto ");
		sqlBuffer.append("    ON pbtc.id = pbto.fk_course_id	 ");
		sqlBuffer.append(" INNER JOIN (select fk_stu_id, fk_training_id, fk_tch_opencourse_id,score_exam,COMPLETED_TIME  ");
		sqlBuffer.append("               from pr_bzz_tch_stu_elective ");
		sqlBuffer.append("              where fk_stu_id = '" + stuId + "' ");
		// Lee start 2014年3月5日 判断开始时间结束时间拼接sql
		if ((startDate != null && !"".equals(startDate) && !"null".equals(startDate))
				&& (endDate == null || "".equals(endDate) || "null".equals(endDate))) {// 有开始没结束
			sqlBuffer.append(" AND COMPLETED_TIME >= TO_DATE('" + startDate + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
		}
		if ((endDate != null && !"".equals(endDate) && !"null".equals(endDate))
				&& (startDate == null || "".equals(startDate) || "null".equals(startDate))) {// 有结束没开始
			sqlBuffer.append(" AND COMPLETED_TIME <= TO_DATE('" + endDate + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
		if ((startDate != null && !"".equals(startDate) && !"null".equals(startDate))
				&& (endDate != null && !"".equals(endDate) && !"null".equals(endDate))) {// 有开始有结束
			sqlBuffer.append(" AND COMPLETED_TIME BETWEEN TO_DATE('" + startDate + " 00:00:00','yyyy-MM-dd hh24:mi:ss') AND TO_DATE('"
					+ endDate + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
		// Lee end
		sqlBuffer.append(" ) pbtse ");
		sqlBuffer.append("    ON pbto.id = pbtse.fk_tch_opencourse_id ");
		sqlBuffer.append(" INNER JOIN (select id, learn_status,GET_DATE ");
		sqlBuffer.append("               from training_course_student ");
		sqlBuffer.append("              where student_id = '" + sid + "'			  ");
		sqlBuffer.append("                and learn_status = '" + StudyProgress.COMPLETED + "') tcs			  ");
		sqlBuffer.append("    ON pbtse.fk_training_id = tcs.id ");
		sqlBuffer.append("  left JOIN scorm_course_info sci	 ");
		sqlBuffer.append("    ON pbtc.code = sci.id		 ");
		sqlBuffer.append("  left JOIN (select * from scorm_stu_course where student_id='" + stuId + "') ssc	 ");
		sqlBuffer.append("    ON sci.id = ssc.course_id	 ");
		sqlBuffer.append(" INNER JOIN pe_bzz_student pbs	 ");
		sqlBuffer.append("    ON pbtse.fk_stu_id = pbs.id	 ");
		sqlBuffer.append("  left JOIN sso_user su		 ");
		sqlBuffer.append("    ON pbs.fk_sso_user_id = su.id	 ");
		sqlBuffer.append("  LEFT JOIN enum_const ec		 ");
		sqlBuffer.append("    ON pbtc.flag_coursetype = ec.id	LEFT JOIN enum_const ec2 ON pbtc.FLAG_ISVISITAFTERPASS = ec2.id		where 1=1	");
		// sqlBuffer.append(" and pbtc.flag_isfree='"+ec_isFree.getId()+"' ");
		if (courseCode != null && !"".equals(courseCode)) {
			sqlBuffer.append(" and pbtc.code like '%" + courseCode.toUpperCase() + "%'");
		}
		if (courseName != null && !"".equals(courseName)) {
			sqlBuffer.append(" and pbtc.name like '%" + courseName + "%'");
		}
		if (courseType != null && !"".equalsIgnoreCase(courseType)) {
			sqlBuffer.append(" AND pbtc.flag_coursetype = '" + courseType + "'");
		}
		if (classHour != null && !"".equalsIgnoreCase(classHour)) {
			sqlBuffer.append(" AND to_number(pbtc.time)-to_number( '" + classHour + "') = 0 ");
		}
		if (StudyProgress.PASSED.equals(examStatus)) {
			sqlBuffer.append(" and ( ( pbtc.FLAG_IS_EXAM = '" + ec_isExam.getId()
					+ "' AND nvl(pbtse.score_exam,0) >= nvl(pbtc.passrole,0) ) ");
			sqlBuffer.append(" or ( pbtc.FLAG_IS_EXAM = '" + ec_isNotExam.getId() + "' and  pbtse.COMPLETED_TIME is not null ) ) ");
		} else if (StudyProgress.FAILED.equals(examStatus)) {
			sqlBuffer.append(" and pbtc.FLAG_IS_EXAM = '" + ec_isExam.getId() + "' AND nvl(pbtse.score_exam,0) < nvl(pbtc.passrole,0) ");
		}
		List re_list = new ArrayList();
		List list = this.generalDao.getBySQL(sqlBuffer.toString());
		re_list.add(list);
		sqlBuffer.append(" AND FLAG_COURSETYPE = '402880f32200c249012200c780c40001'");
		List b_list = this.generalDao.getBySQL(sqlBuffer.toString());
		re_list.add(b_list);
		return re_list;
	}

	/**
	 * 初始化内容属性列表 Lee 2014年03月12日
	 */

	public List<EnumConst> initCourseContent(String params) {
		DetachedCriteria dcCategory = DetachedCriteria.forClass(EnumConst.class);
		dcCategory.add(Restrictions.eq("namespace", "FlagContentProperty"));
		List<EnumConst> courseCategoryList = this.generalDao.getList(dcCategory);
		return courseCategoryList;
	}

	/**
	 * 获取已选课程学时数 Lee 2014年04月03日
	 * 
	 * @param stuId
	 * @param coursename
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws EntityException
	 */

	public String getTimes(String stuId, String coursename, String ktimestart, String ktimeend, String etimestart, String etimeend)
			throws EntityException {
		String sql = "SELECT SUM(C.TIME) FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID INNER JOIN ENUM_CONST D ON C.FLAG_COURSECATEGORY = D.ID INNER JOIN PE_ELECTIVE_COURSE_PERIOD E ON A.FK_ELE_COURSE_PERIOD_ID = E.ID WHERE A.FK_STU_ID = '"
				+ stuId + "'";
		if (null != ktimestart && !ktimestart.equals("")) {
			sql += " AND to_date(to_char(E.BEGIN_DATE, 'yyyy-MM-dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss') >= to_date('" + ktimestart
					+ " 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		if (null != ktimeend && !ktimeend.equals("")) {
			sql += " AND to_date(to_char(E.BEGIN_DATE, 'yyyy-MM-dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss') <= to_date('" + ktimeend
					+ " 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		if (null != etimestart && !etimestart.equals("")) {
			sql += " AND to_date(to_char(E.END_DATE, 'yyyy-MM-dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss') >= to_date('" + etimestart
					+ " 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		if (null != etimeend && !etimeend.equals("")) {
			sql += " AND to_date(to_char(E.END_DATE, 'yyyy-MM-dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss') <= to_date('" + etimeend
					+ " 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ";
		}
		if (coursename != null && !"".equals(coursename))
			sql += " AND E.NAME LIKE '%" + coursename + "%'";
		List res_list = this.getGeneralDao().getBySQL(sql);
		String res_ = "0";
		if (res_list != null && res_list.size() > 0)
			res_ = res_list.get(0) + "";
		return (res_ == null || "".equals(res_) || "null".equals(res_)) ? "0" : res_;
	}

	/**
	 * 在线直播报名列表
	 * 
	 * @author Lee
	 * @return
	 */

	public Page initSacLive(String courseCode, String courseName, String selSendDateStart, String selSendDateEnd, String teacher,
			int pageSize, int startIndex) {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT A.*, ");
		sb.append("        DECODE(A.PRICE, ");
		sb.append("               0, ");
		sb.append("               A.MAXSTUS - NVL(CNTSB, 0), ");
		sb.append("               A.MAXSTUS - NVL(CNTPBTSE, 0)) CNT ");
		sb.append("   FROM (SELECT BATCH_ID, ");
		sb.append("                '' LIVEURL, ");
		sb.append("                '' LIVESTUPWD, ");
		sb.append("                MAXSTUS, ");
		sb.append("                ID, ");
		sb.append("                CODE, ");
		sb.append("                NAME, ");
		sb.append("                SIGNSTARTDATE, ");
		sb.append("                SIGNENDDATE, ");
		sb.append("                ESTIMATESTARTDATE, ");
		sb.append("                ESTIMATEENDDATE, ");
		sb.append("                '' ESTIMATETIME, ");
		sb.append("                TIME, ");
		sb.append("                PRICE, ");
		sb.append("                '' SUMLEARNTIME, ");
		sb.append("                '' ROLENAME, ");
		sb.append("                TEACHER, ");
		sb.append("                '' GETTIME, ");
		sb.append("                PAYSTATECODE, ");// 支付CODE
		sb.append("                PAYCODE, ");// 到账CODE
		sb.append("                REFUNDCODE, ");// 退费CODE
		sb.append("                '' PAYSTATENAME, ");// 支付NAME
		sb.append("                '' PAYNAME, ");// 到账NAME
		sb.append("                '' REFUNDNAME, ");// 退费NAME
		sb.append("                '' STATE, ");// 是否过期1过期0未过期
		sb.append("                PBTSEID ");// 选课ID
		sb.append("           FROM (SELECT PBTO.FK_BATCH_ID BATCH_ID, ");
		sb.append("                        '' LIVEURL, ");
		sb.append("                        '' LIVESTUPWD, ");
		sb.append("                        PBTC.MAXSTUS MAXSTUS, ");
		sb.append("                        PBTC.ID, ");
		sb.append("                        PBTC.CODE, ");
		sb.append("                        PBTC.NAME, ");
		sb.append("                        TO_CHAR(PBTC.SIGNSTARTDATE, 'yyyy-MM-dd hh24:mi') SIGNSTARTDATE, ");
		sb.append("                        TO_CHAR(PBTC.SIGNENDDATE, 'yyyy-MM-dd hh24:mi') SIGNENDDATE, ");
		sb.append("                        TO_CHAR(PBTC.ESTIMATESTARTDATE, 'yyyy-MM-dd hh24:mi') ESTIMATESTARTDATE, ");
		sb.append("                        TO_CHAR(PBTC.ESTIMATEENDDATE, 'yyyy-MM-dd hh24:mi') ESTIMATEENDDATE, ");
		sb.append("                        '' ESTIMATETIME, ");
		sb.append("                        PBTC.TIME, ");
		sb.append("                        PBTC.PRICE, ");
		sb.append("                        '' SUMLEARNTIME, ");
		sb.append("                        '' ROLENAME, ");
		sb.append("                        PBTC.TEACHER, ");
		sb.append("                        '' GETTIME, ");
		sb.append("                        ECPAYSTATE.CODE PAYSTATECODE, ");
		sb.append("                        ECPAY.CODE PAYCODE, ");
		sb.append("                        ECREFUND.CODE REFUNDCODE, ");
		sb.append("                        '' PAYSTATENAME, ");
		sb.append("                        '' PAYNAME, ");
		sb.append("                        '' REFUNDNAME, ");
		sb.append("                        '' STATE, ");
		sb.append("                        PBTSE.ID PBTSEID ");
		sb.append("                   FROM PE_BZZ_TCH_COURSE PBTC ");
		sb.append("                  INNER JOIN ENUM_CONST ECFREEZE ");
		sb.append("                     ON PBTC.FLAG_FREEZE = ECFREEZE.ID ");
		sb.append("                  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("                     ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("                  INNER JOIN STU_BATCH SB ");
		sb.append("                     ON PBTO.FK_BATCH_ID = SB.BATCH_ID ");
		sb.append("                  INNER JOIN PE_BZZ_STUDENT PBS ");
		sb.append("                     ON SB.STU_ID = PBS.ID ");
		sb.append("                    AND PBS.FK_SSO_USER_ID = ");
		sb.append("                        '" + us.getSsoUser().getId() + "' ");
		sb.append("                  INNER JOIN ENUM_CONST ECSIGNTYPE ");
		sb.append("                     ON PBTC.FLAG_SIGNTYPE = ECSIGNTYPE.ID ");
		sb.append("                    AND ECSIGNTYPE.CODE = '0' ");
		sb.append("                    AND PBTC.FLAG_ISVALID = '2' ");
		sb.append("                    AND PBTC.FLAG_COURSEAREA = 'Coursearea0' ");
		sb.append("                  INNER JOIN ENUM_CONST ECROLE ");
		sb.append("                     ON PBTC.FLAG_LIVEPASSROLE = ECROLE.ID ");
		sb.append("                   LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sb.append("                     ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		sb.append("                    AND PBTSE.FK_STU_ID = SB.STU_ID ");
		sb.append("                   LEFT JOIN ENUM_CONST ECPAYSTATE ");
		sb.append("                     ON SB.FLAG_BATCHPAYSTATE = ECPAYSTATE.ID ");
		sb.append("                   LEFT JOIN PE_BZZ_ORDER_INFO PBOI ");
		sb.append("                     ON SB.FK_ORDER_ID = PBOI.ID ");
		sb.append("                   LEFT JOIN ENUM_CONST ECPAY ");
		sb.append("                     ON PBOI.FLAG_PAYMENT_STATE = ECPAY.ID ");
		sb.append("                   LEFT JOIN ENUM_CONST ECREFUND ");
		sb.append("                     ON PBOI.FLAG_REFUND_STATE = ECREFUND.ID ");
		sb.append("                   LEFT JOIN LIVE_SEQ_STU LSS ");
		sb.append("                     ON PBS.FK_SSO_USER_ID = LSS.LOGIN_ID ");
		sb.append("                   LEFT JOIN WE_HISTORY WH ");
		sb.append("                     ON WH.WH_UID = LSS.ID ");
		sb.append("                    AND PBTC.LIVEID = WH.WH_WEBCASTID ");
		sb.append("                  GROUP BY PBTO.FK_BATCH_ID, ");
		sb.append("                           PBTC.ID, ");
		sb.append("                           PBTC.CODE, ");
		sb.append("                           PBTC.NAME, ");
		sb.append("                           PBTC.SIGNSTARTDATE, ");
		sb.append("                           PBTC.SIGNENDDATE, ");
		sb.append("                           PBTC.ESTIMATESTARTDATE, ");
		sb.append("                           PBTC.ESTIMATEENDDATE, ");
		sb.append("                           PBTC.TIME, ");
		sb.append("                           PBTC.PRICE, ");
		sb.append("                           ECROLE.NAME, ");
		sb.append("                           PBTC.TEACHER, ");
		sb.append("                           PBTSE.COMPLETED_TIME, ");
		sb.append("                           ECPAYSTATE.CODE, ");
		sb.append("                           ECPAY.CODE, ");
		sb.append("                           ECREFUND.CODE, ");
		sb.append("                           ECPAYSTATE.NAME, ");
		sb.append("                           ECPAY.NAME, ");
		sb.append("                           ECREFUND.NAME, ");
		sb.append("                           ECFREEZE.CODE, ");
		sb.append("                           PBTSE.ISPASS, ");
		sb.append("                           PBTSE.ID, PBTC.MAXSTUS) ");
		sb.append("         UNION ALL (SELECT PBB.ID BATCH_ID, ");
		sb.append("                          '' LIVEURL, ");
		sb.append("                          '' LIVESTUPWD, ");
		sb.append("                          PBTC.MAXSTUS MAXSTUS, ");
		sb.append("                          PBTC.ID, ");
		sb.append("                          PBTC.CODE, ");
		sb.append("                          PBTC.NAME, ");
		sb.append("                          TO_CHAR(PBTC.SIGNSTARTDATE, 'yyyy-MM-dd hh24:mi') SIGNSTARTDATE, ");
		sb.append("                          TO_CHAR(PBTC.SIGNENDDATE, 'yyyy-MM-dd hh24:mi') SIGNENDDATE, ");
		sb.append("                          TO_CHAR(PBTC.ESTIMATESTARTDATE, ");
		sb.append("                                  'yyyy-MM-dd hh24:mi') ESTIMATESTARTDATE, ");
		sb.append("                          TO_CHAR(PBTC.ESTIMATEENDDATE, 'yyyy-MM-dd hh24:mi') ESTIMATEENDDATE, ");
		sb.append("                          '' ESTIMATETIME, ");
		sb.append("                          PBTC.TIME, ");
		sb.append("                          PBTC.PRICE, ");
		sb.append("                          '' SUMLEARNTIME, ");
		sb.append("                          '' ROLENAME, ");
		sb.append("                          PBTC.TEACHER, ");
		sb.append("                          '' GETTIME, ");
		sb.append("                          SPPEE.PAYSTATECODE, ");
		sb.append("                          SPPEE.PAYCODE, ");
		sb.append("                          SPPEE.REFUNDCODE, ");
		sb.append("                          '' PAYSTATENAME, ");
		sb.append("                          '' PAYNAME, ");
		sb.append("                          '' REFUNDNAME, ");
		sb.append("                          '' STATE, ");
		sb.append("                          PBTSE.ID PBTSEID ");
		sb.append("                     FROM PE_BZZ_TCH_COURSE PBTC ");
		sb.append("                    INNER JOIN ENUM_CONST ECFREEZE ");
		sb.append("                       ON PBTC.FLAG_FREEZE = ECFREEZE.ID ");
		sb.append("                    INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("                       ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("                      AND PBTC.FLAG_ISVALID = '2' ");
		sb.append("                      AND PBTC.FLAG_COURSEAREA = 'Coursearea0' ");
		sb.append("                    INNER JOIN PE_BZZ_BATCH PBB ");
		sb.append("                       ON PBTO.FK_BATCH_ID = PBB.ID ");
		sb.append("                    INNER JOIN ENUM_CONST ECROLE ");
		sb.append("                       ON PBTC.FLAG_LIVEPASSROLE = ECROLE.ID ");
		sb.append("                    INNER JOIN ENUM_CONST ECSIGNTYPE ");
		sb.append("                       ON PBTC.FLAG_SIGNTYPE = ECSIGNTYPE.ID ");
		sb.append("                      AND ECSIGNTYPE.CODE = '1' ");
		sb.append("                    INNER JOIN ENUM_CONST ECBATCHTYPE ");
		sb.append("                       ON PBB.FLAG_BATCH_TYPE = ECBATCHTYPE.ID ");
		sb.append("                      AND ECBATCHTYPE.CODE = '2' ");
		sb.append("                     LEFT JOIN (SELECT SB.STU_ID, ");
		sb.append("                                      SB.BATCH_ID, ");
		sb.append("                                      ECPAYSTATE.CODE PAYSTATECODE, ");
		sb.append("                                      ECPAY.CODE PAYCODE, ");
		sb.append("                                      ECREFUND.CODE REFUNDCODE, ");
		sb.append("                                      '' PAYSTATENAME, ");
		sb.append("                                      '' PAYNAME, ");
		sb.append("                                      '' REFUNDNAME ");
		sb.append("                                 FROM STU_BATCH SB ");
		sb.append("                                INNER JOIN PE_BZZ_STUDENT PBS ");
		sb.append("                                   ON SB.STU_ID = PBS.ID ");
		sb.append("                                  AND PBS.FK_SSO_USER_ID = ");
		sb.append("                                      '" + us.getSsoUser().getId() + "' ");
		sb.append("                                 LEFT JOIN PE_BZZ_ORDER_INFO PBOI ");
		sb.append("                                   ON SB.FK_ORDER_ID = PBOI.ID ");
		sb.append("                                 LEFT JOIN ENUM_CONST ECPAYSTATE ");
		sb.append("                                   ON SB.FLAG_BATCHPAYSTATE = ECPAYSTATE.ID ");
		sb.append("                                 LEFT JOIN ENUM_CONST ECPAY ");
		sb.append("                                   ON PBOI.FLAG_PAYMENT_STATE = ECPAY.ID ");
		sb.append("                                 LEFT JOIN ENUM_CONST ECREFUND ");
		sb.append("                                   ON PBOI.FLAG_REFUND_STATE = ECREFUND.ID) SPPEE ");
		sb.append("                       ON PBB.ID = SPPEE.BATCH_ID ");
		sb.append("                     LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sb.append("                       ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		sb.append("                      AND PBTSE.FK_STU_ID = SPPEE.STU_ID ");
		sb.append("                     LEFT JOIN PE_BZZ_STUDENT PBS ");
		sb.append("                       ON PBTSE.FK_STU_ID = PBS.ID ");
		sb.append("                     LEFT JOIN LIVE_SEQ_STU LSS ");
		sb.append("                       ON PBS.FK_SSO_USER_ID = LSS.LOGIN_ID ");
		sb.append("                     LEFT JOIN WE_HISTORY WH ");
		sb.append("                       ON WH.WH_UID = LSS.ID ");
		sb.append("                      AND PBTC.LIVEID = WH.WH_WEBCASTID ");
		sb.append("                    GROUP BY PBB.ID, ");
		sb.append("                             PBTC.ID, ");
		sb.append("                             PBTC.CODE, ");
		sb.append("                             PBTC.NAME, ");
		sb.append("                             PBTC.SIGNSTARTDATE, ");
		sb.append("                             PBTC.SIGNENDDATE, ");
		sb.append("                             PBTC.ESTIMATESTARTDATE, ");
		sb.append("                             PBTC.ESTIMATEENDDATE, ");
		sb.append("                             PBTC.TIME, ");
		sb.append("                             PBTC.PRICE, ");
		sb.append("                             ECROLE.NAME, ");
		sb.append("                             PBTC.TEACHER, ");
		sb.append("                             PBTSE.COMPLETED_TIME, ");
		sb.append("                             SPPEE.PAYSTATECODE, ");
		sb.append("                             SPPEE.PAYCODE, ");
		sb.append("                             SPPEE.REFUNDCODE, ");
		sb.append("                             ECFREEZE.CODE, ");
		sb.append("                             PBTSE.ISPASS, ");
		sb.append("                             PBTSE.ID, PBTC.MAXSTUS)) A ");
		sb.append("   LEFT JOIN (SELECT BATCH_ID, COUNT(STU_ID) CNTSB ");
		sb.append("                FROM STU_BATCH SB ");
		sb.append("               INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("                  ON SB.BATCH_ID = PBTO.FK_BATCH_ID ");
		sb.append("               INNER JOIN PE_BZZ_TCH_COURSE PBTC ");
		sb.append("                  ON PBTO.FK_COURSE_ID = PBTC.ID ");
		sb.append("                 AND PBTC.FLAG_COURSEAREA = 'Coursearea0' ");
		sb.append("               GROUP BY BATCH_ID) B ");
		sb.append("     ON A.BATCH_ID = B.BATCH_ID ");
		sb.append("   LEFT JOIN (SELECT FK_BATCH_ID BATCH_ID, COUNT(SB.STU_ID) CNTPBTSE ");
		sb.append("                FROM STU_BATCH SB ");
		sb.append("               INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("                  ON SB.BATCH_ID = PBTO.FK_BATCH_ID ");
		sb.append("                 AND SB.FK_ORDER_ID IS NOT NULL ");
		sb.append("               INNER JOIN PE_BZZ_TCH_COURSE PBTC ");
		sb.append("                  ON PBTO.FK_COURSE_ID = PBTC.ID ");
		sb.append("                 AND PBTC.FLAG_COURSEAREA = 'Coursearea0' ");
		sb.append("               GROUP BY PBTO.FK_BATCH_ID) C ");
		sb.append("     ON A.BATCH_ID = C.BATCH_ID ");
		// end
		sb.append(" WHERE 1 = 1 ");
		// 课程编号
		if (null != courseCode && !"".equals(courseCode))
			sb.append(" AND UPPER(CODE) LIKE '%" + courseCode.trim().toUpperCase() + "%'");
		// 课程名称
		if (null != courseName && !"".equals(courseName))
			sb.append(" AND UPPER(NAME) LIKE '%" + courseName.trim().toUpperCase() + "%'");
		// 预计直播开始时间
		if (null != selSendDateStart && !"".equals(selSendDateStart))
			sb.append(" AND TO_DATE(ESTIMATESTARTDATE,'yyyy-MM-dd hh24:mi:ss') >= TO_DATE('" + selSendDateStart
					+ "','yyyy-MM-dd hh24:mi:ss')");
		// 预计直播结束时间
		if (null != selSendDateEnd && !"".equals(selSendDateEnd))
			sb.append(" AND TO_DATE(ESTIMATESTARTDATE,'yyyy-MM-dd hh24:mi:ss') <= TO_DATE('" + selSendDateEnd
					+ "','yyyy-MM-dd hh24:mi:ss')");
		// 主讲人
		if (null != teacher && !"".equals(teacher))
			sb.append(" AND UPPER(TEACHER) LIKE '%" + teacher.trim().toUpperCase() + "%'");
		sb.append("  ORDER BY TO_DATE(SIGNSTARTDATE, 'yyyy-MM-dd hh24:mi:ss') DESC ");
		Page page = null;
		page = this.getGeneralDao().getByPageSQL(sb.toString(), pageSize, startIndex);
		return page;
	}

	/**
	 * 在线直播学习列表
	 * 
	 * @author Lee
	 * @return
	 */

	public Page initSacLiveStudy(String courseCode, String courseName, String selSendDateStart, String selSendDateEnd, String teacher, String orderInfo, String orderType,
			int pageSize, int startIndex) {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM ( ");
		sb.append(" SELECT PBTO.FK_BATCH_ID, ");
		sb.append("        PBTC.CODE, ");
		sb.append("        PBTC.NAME, ");
		sb.append("        TO_CHAR(PBTC.ESTIMATESTARTDATE, 'yyyy-MM-dd hh24:mi') ESTIMATESTARTDATE, ");
		sb.append("        TO_CHAR(PBTC.ESTIMATEENDDATE, 'yyyy-MM-dd hh24:mi') ESTIMATEENDDATE, ");
		sb.append("        EC1.NAME PASSROLE, ");
		sb.append("        PBTC.TIME, ");
		sb.append("        PBTC.TEACHER, ");
		sb.append("        CASE ");
		sb.append("          WHEN PBTC.ESTIMATESTARTDATE > SYSDATE AND EC2.CODE = '0' THEN ");
		sb.append("           'UNCOMPLETED' ");
		sb.append("          WHEN EC2.CODE = '1' AND PBTSE.ISPASS = '1' THEN ");
		sb.append("           'COMPLETED' ");
		sb.append("          ELSE ");
		sb.append("           'UNSTART' ");
		sb.append("        END LEARNSTATUS, ");
		sb.append("        DECODE(NVL(PBTC.PRICE, 0), 0, '1', ECPAY.CODE) PAY, ");
		sb.append("        DECODE(NVL(PBTC.PRICE, 0), 0, NULL, ECREFUND.CODE) REFUND, ");
		sb.append("        PBTC.ID, ");
		sb.append("        NVL(TRUNC(ROUND(SUM(WH.WH_LEAVETIME - WH.WH_JOINTIME) / 60 / 1000), ");
		sb.append("                  2), ");
		sb.append("            0) LEIJI, ");
		sb.append("        DECODE(PBTSE.ISPASS, 1, PBTC.TIME, 0) YIHUODE, ");
		sb.append("        SSL.COURSE_ID COURSEWAREID, MIN(H.D1) START_TIME, MAX(H.D2) LAST_TIME ");
		sb.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
		sb.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("    AND PBTC.FLAG_COURSEAREA = 'Coursearea0' ");
		sb.append("  INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sb.append("     ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
		sb.append("  INNER JOIN PE_BZZ_STUDENT PBS ");
		sb.append("     ON PBTSE.FK_STU_ID = PBS.ID ");
		sb.append("    AND PBS.FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "' ");
		sb.append("  INNER JOIN ENUM_CONST EC1 ");
		sb.append("     ON PBTC.FLAG_LIVEPASSROLE = EC1.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC2 ");
		sb.append("     ON PBTC.FLAG_FREEZE = EC2.ID ");
		sb.append("   LEFT JOIN LIVE_SEQ_STU LSS ");
		sb.append("     ON PBS.FK_SSO_USER_ID = LSS.LOGIN_ID ");
		sb.append("   LEFT JOIN WE_HISTORY WH ");
		sb.append("     ON LSS.ID = WH.WH_UID ");
		sb.append("    AND LSS.ID = WH.WH_UID ");
		sb.append("    AND WH.WH_WEBCASTID = PBTC.LIVEID ");
		sb.append("   LEFT JOIN PE_BZZ_ORDER_INFO PBOI ");
		sb.append("     ON PBTSE.FK_ORDER_ID = PBOI.ID ");
		sb.append("   LEFT JOIN ENUM_CONST ECPAY ");
		sb.append("     ON PBOI.FLAG_PAYMENT_STATE = ECPAY.ID ");
		sb.append("   LEFT JOIN ENUM_CONST ECREFUND ");
		sb.append("     ON PBOI.FLAG_REFUND_STATE = ECREFUND.ID ");
		sb.append("   LEFT JOIN SCORM_SCO_LAUNCH SSL ");
		sb.append("     ON PBTC.CODE = SSL.COURSE_ID ");
		sb.append("   LEFT JOIN (SELECT TO_CHAR(TO_DATE('1970-01-01 00:00:00', ");
		sb.append("                               'yyyy-mm-dd hh24:mi:ss') + ");
		sb.append("                       (WH_JOINTIME + 8 * 1000 * 3600) / 1000 / 24 / 60 / 60, ");
		sb.append("                       'yyyy-MM-dd hh24:mi:ss') D1, ");
		sb.append("               TO_CHAR(TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sb.append("                       (WH_LEAVETIME + 8 * 1000 * 3600) / 1000 / 24 / 60 / 60, ");
		sb.append("                       'yyyy-MM-dd hh24:mi:ss') D2, PBTC.CODE ");
		sb.append("          FROM WE_HISTORY WH ");
		sb.append("          JOIN LIVE_SEQ_STU LSS ");
		sb.append("            ON WH.WH_UID = LSS.ID ");
		sb.append("          JOIN PE_BZZ_TCH_COURSE PBTC ");
		sb.append("            ON WH.WH_WEBCASTID = PBTC.LIVEID ");
		sb.append("         WHERE ");
		sb.append("            LSS.LOGIN_ID = '" + us.getSsoUser().getLoginId() + "' ");
		sb.append("         ORDER BY TO_DATE(D1, 'yyyy-MM-dd hh24:mi:ss') ASC) H ");
		sb.append("     ON H.CODE = PBTC.CODE ");
		sb.append("  GROUP BY PBTO.FK_BATCH_ID, ");
		sb.append("           PBTC.CODE, ");
		sb.append("           PBTC.NAME, ");
		sb.append("           PBTC.ESTIMATESTARTDATE, ");
		sb.append("           PBTC.ESTIMATEENDDATE, ");
		sb.append("           EC1.NAME, ");
		sb.append("           PBTC.TIME, ");
		sb.append("           PBTC.TEACHER, ");
		sb.append("           PBTC.ESTIMATESTARTDATE, ");
		sb.append("           ECPAY.CODE, ");
		sb.append("           ECREFUND.CODE, ");
		sb.append("           EC2.CODE, ");
		sb.append("           PBTC.ID, ");
		sb.append("           PBTC.PRICE, ");
		sb.append("           PBTSE.ISPASS, ");
		sb.append("           LSS.LOGIN_ID, ");
		sb.append("           SSL.COURSE_ID ");
		sb.append(" ) WHERE 1 = 1 ");
		// 课程编号
		if (null != courseCode && !"".equals(courseCode))
			sb.append(" AND UPPER(CODE) LIKE '%" + courseCode.trim().toUpperCase() + "%'");
		// 课程名称
		if (null != courseName && !"".equals(courseName))
			sb.append(" AND UPPER(NAME) LIKE '%" + courseName.trim().toUpperCase() + "%'");
		// 预计直播开始时间
		if (null != selSendDateStart && !"".equals(selSendDateStart))
			sb.append(" AND TO_DATE(ESTIMATESTARTDATE,'yyyy-MM-dd hh24:mi:ss') >= TO_DATE('" + selSendDateStart
					+ "','yyyy-MM-dd hh24:mi:ss')");
		// 预计直播结束时间
		if (null != selSendDateEnd && !"".equals(selSendDateEnd))
			sb.append(" AND TO_DATE(ESTIMATESTARTDATE,'yyyy-MM-dd hh24:mi:ss') <= TO_DATE('" + selSendDateEnd
					+ "','yyyy-MM-dd hh24:mi:ss')");
		// 主讲人
		if (null != teacher && !"".equals(teacher))
			sb.append(" AND UPPER(TEACHER) LIKE '%" + teacher.trim().toUpperCase() + "%'");
		
		if(orderInfo != null && !"".equals(orderInfo)) {
			sb.append(" ORDER BY " + orderInfo + " " + orderType);
		}
		Page page = null;
		page = this.getGeneralDao().getByPageSQL(sb.toString(), pageSize, startIndex);
		return page;
	}

	public List initSuggestRen(String params) {
		DetachedCriteria dcCategory = DetachedCriteria.forClass(EnumConst.class);
		dcCategory.add(Restrictions.eq("namespace", "FlagSuggest"));
		List<EnumConst> suggestRenList = this.generalDao.getList(dcCategory);
		return suggestRenList;
	}

	public List initCourseArea(String params) {
		DetachedCriteria dcCategory = DetachedCriteria.forClass(EnumConst.class);
		dcCategory.add(Restrictions.eq("namespace", "FlagCoursearea"));
		List<EnumConst> courseAreaList = this.generalDao.getList(dcCategory);
		return courseAreaList;
	}

	/**
	 * 正在学习课程-Lee 正在使用
	 */
	public Page initLearingCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName,
			String time, String courseItemType, String courseContent, String suggestren, String teacher, String coursearea, String orderInfo, String orderType, int pageSize,
			int startIndex) throws EntityException {
		Page page = null;
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT PBTSE_ID, ");
		sb.append("        PBTO_ID, ");
		sb.append("        PBTC_ID, ");
		sb.append("        PBTC_CODE, ");
		sb.append("        PBTC_NAME, ");
		sb.append("        PBTC_TEACHER, ");
		sb.append("        COURSEAREA, ");
		sb.append("        COURSETYPE, ");
		sb.append("        PBTC_TIME, ");
		sb.append("        TCS_GET_DATE, ");
		sb.append("        PBTC_STUDYDATES, ");
		sb.append("        PBOI_CODE, ");
		sb.append("        TCS_LEARN_STATUS, ");
		sb.append("        PBTC_ISFREE, ");
		sb.append("        FS, ");
		sb.append("        ECCODE ");
		sb.append("   FROM (SELECT A.ID PBTSE_ID, ");
		sb.append("                B.ID PBTO_ID, ");
		sb.append("                C.ID PBTC_ID, ");
		sb.append("                C.CODE PBTC_CODE, ");
		sb.append("                C.NAME PBTC_NAME, ");
		sb.append("                C.TEACHER PBTC_TEACHER, ");
		sb.append("                EC1.NAME COURSEAREA, ");
		sb.append("                EC2.NAME COURSETYPE, ");
		sb.append("                C.TIME PBTC_TIME, ");
		sb.append("                TO_CHAR(D.GET_DATE, 'yyyy-MM-dd hh24:mi:ss') TCS_GET_DATE, ");
		sb.append("                C.STUDYDATES PBTC_STUDYDATES, ");
		sb.append("                EC3.CODE PBOI_CODE, ");
		sb.append("                D.LEARN_STATUS TCS_LEARN_STATUS, ");
		sb.append("                EC4.CODE PBTC_ISFREE, ");
		sb.append("                F.FS, ");
		sb.append("                EC1.CODE ECCODE, SSS.LAST_ACCESSDATE LASTTIME ");
		sb.append("           FROM PR_BZZ_TCH_STU_ELECTIVE A ");
		sb.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE B ");
		sb.append("             ON A.FK_TCH_OPENCOURSE_ID = B.ID ");
		sb.append("            AND A.FK_STU_ID = '" + stuId + "' ");
		sb.append("            AND A.ISPASS <> '1' ");
		sb.append("          INNER JOIN TRAINING_COURSE_STUDENT D ");
		sb.append("             ON A.FK_TRAINING_ID = D.ID ");
		sb.append("            AND D.LEARN_STATUS <> 'COMPLETED' ");
		sb.append("          INNER JOIN PE_BZZ_TCH_COURSE C ");
		sb.append("             ON B.FK_COURSE_ID = C.ID ");
		sb.append("          INNER JOIN ENUM_CONST EC1 ");
		sb.append("             ON C.FLAG_COURSEAREA = EC1.ID ");
		sb.append("            AND EC1.CODE <> '0' ");
		sb.append("          INNER JOIN ENUM_CONST EC2 ");
		sb.append("             ON C.FLAG_COURSETYPE = EC2.ID ");
		sb.append("          INNER JOIN ENUM_CONST EC4 ");
		sb.append("             ON C.FLAG_ISFREE = EC4.ID ");
		sb.append("           LEFT JOIN PE_BZZ_ORDER_INFO E ");
		sb.append("             ON A.FK_ORDER_ID = E.ID ");
		sb.append("           LEFT JOIN ENUM_CONST EC3 ");
		sb.append("             ON E.FLAG_REFUND_STATE = EC3.ID ");
		sb.append("    		 INNER JOIN SSO_USER SU ON D.STUDENT_ID = SU.ID ");
		sb.append("			  LEFT JOIN SCORM_STU_SCO SSS ON SU.ID = SSS.STUDENT_ID AND C.CODE = SSS.COURSE_ID ");
		sb.append("           LEFT JOIN (SELECT FK_COURSE_ID, ");
		sb.append("                            TO_CHAR(WM_CONCAT(FK_ENUM_CONST_ID)) FS ");
		sb.append("                       FROM PE_BZZ_TCH_COURSE_SUGGEST ");
		sb.append("                      WHERE TABLE_NAME = 'PE_BZZ_TCH_COURSE' ");
		sb.append("                      GROUP BY FK_COURSE_ID) F ");
		sb.append("             ON C.ID = F.FK_COURSE_ID WHERE 1 = 1 ");
		if (null != courseCategory && !"".equals(courseCategory))// 业务分类
			sb.append(" AND C.FLAG_COURSECATEGORY = '" + courseCategory + "' ");
		if (null != courseType && !"".equals(courseType))// 课程性质
			sb.append(" AND C.FLAG_COURSETYPE = '" + courseType + "' ");
		if (null != courseCode && !"".equals(courseCode))// 课程编号
			sb.append(" AND UPPER(C.CODE) LIKE '%" + courseCode.toUpperCase() + "%' ");
		if (null != courseName && !"".equals(courseName))// 课程名称
			sb.append(" AND UPPER(C.NAME) LIKE '%" + courseName.toUpperCase() + "%' ");
		if (null != time && !"".equals(time))// 学时
			sb.append(" AND C.TIME LIKE '%" + time + "%' ");
		if (null != courseItemType && !"".equals(courseItemType))// 大纲分类
			sb.append(" AND C.FLAG_COURSE_ITEM_TYPE = '" + courseItemType + "' ");
		if (null != courseContent && !"".equals(courseContent))// 内容属性
			sb.append(" AND C.FLAG_CONTENT_PROPERTY = '" + courseContent + "' ");
		if (null != coursearea && !"".equals(coursearea))// 所属区域
			sb.append(" AND C.FLAG_COURSEAREA = '" + coursearea + "' ");
		if (null != teacher && !"".equals(teacher))// 主讲人
			sb.append(" AND UPPER(C.TEACHER) LIKE '%" + teacher.toUpperCase() + "%' ");
		if (null != suggestren && !"".equals(suggestren))// 建议学习人群
			sb.append(" AND INSTR(FS, '" + suggestren + "', 1, 1) > 0 ");
		sb.append("          GROUP BY A.ID, ");
		sb.append("                   B.ID, ");
		sb.append("                   C.ID, ");
		sb.append("                   C.CODE, ");
		sb.append("                   C.NAME, ");
		sb.append("                   C.TEACHER, ");
		sb.append("                   EC1.NAME, ");
		sb.append("                   EC2.NAME, ");
		sb.append("                   C.TIME, ");
		sb.append("                   D.GET_DATE, ");
		sb.append("                   C.STUDYDATES, ");
		sb.append("                   D.LEARN_STATUS, ");
		sb.append("                   EC3.CODE, ");
		sb.append("                   EC4.CODE, ");
		sb.append("                   FS, ");
		sb.append("                   EC1.CODE, ");
		sb.append("                   A.ELECTIVE_DATE,SSS.LAST_ACCESSDATE ");
		sb.append("          ORDER BY ELECTIVE_DATE DESC) ");
		if (orderInfo != null && !"".equals(orderInfo))//排序方式
			sb.append(" ORDER BY " + orderInfo + " " + orderType);
		try {
			page = this.generalDao.getByPageSQL(sb.toString(), pageSize, startIndex);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 正在学习课程列表统计
	 */
	public List tongjiLearningCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName,
			String time, String courseItemType, String courseContent, String suggestren, String teacher, String coursearea) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT NVL(SUM(C.TIME),0)||','|| ");
		sb.append("        NVL(SUM(CASE ");
		sb.append("              WHEN D.LEARN_STATUS = 'INCOMPLETE' THEN ");
		sb.append("               C.TIME ");
		sb.append("              ELSE ");
		sb.append("               NULL ");
		sb.append("            END),0)||','|| ");
		sb.append("        NVL(SUM(CASE ");
		sb.append("              WHEN D.LEARN_STATUS = 'UNCOMPLETE' THEN ");
		sb.append("               C.TIME ");
		sb.append("              ELSE ");
		sb.append("               NULL ");
		sb.append("            END),0) ");

		sb.append("           FROM PR_BZZ_TCH_STU_ELECTIVE A ");
		sb.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE B ");
		sb.append("             ON A.FK_TCH_OPENCOURSE_ID = B.ID ");
		sb.append("            AND A.FK_STU_ID = '" + stuId + "' ");
		sb.append("            AND A.ISPASS <> '1' ");
		sb.append("          INNER JOIN TRAINING_COURSE_STUDENT D ");
		sb.append("             ON A.FK_TRAINING_ID = D.ID ");
		sb.append("            AND D.LEARN_STATUS <> 'COMPLETED' ");
		sb.append("          INNER JOIN PE_BZZ_TCH_COURSE C ");
		sb.append("             ON B.FK_COURSE_ID = C.ID ");
		sb.append("          INNER JOIN ENUM_CONST EC1 ");
		sb.append("             ON C.FLAG_COURSEAREA = EC1.ID ");
		sb.append("            AND EC1.CODE <> '0' ");
		sb.append("          INNER JOIN ENUM_CONST EC2 ");
		sb.append("             ON C.FLAG_COURSETYPE = EC2.ID ");
		sb.append("          INNER JOIN ENUM_CONST EC4 ");
		sb.append("             ON C.FLAG_ISFREE = EC4.ID ");
		sb.append("           LEFT JOIN PE_BZZ_ORDER_INFO E ");
		sb.append("             ON A.FK_ORDER_ID = E.ID ");
		sb.append("           LEFT JOIN ENUM_CONST EC3 ");
		sb.append("             ON E.FLAG_REFUND_STATE = EC3.ID ");
		sb.append("           LEFT JOIN (SELECT FK_COURSE_ID, ");
		sb.append("                            TO_CHAR(WM_CONCAT(FK_ENUM_CONST_ID)) FS ");
		sb.append("                       FROM PE_BZZ_TCH_COURSE_SUGGEST ");
		sb.append("                      WHERE TABLE_NAME = 'PE_BZZ_TCH_COURSE' ");
		sb.append("                      GROUP BY FK_COURSE_ID) F ");
		sb.append("             ON C.ID = F.FK_COURSE_ID WHERE 1 = 1 ");
		
		if (null != courseCategory && !"".equals(courseCategory))// 业务分类
			sb.append(" AND C.FLAG_COURSECATEGORY = '" + courseCategory + "' ");
		if (null != courseType && !"".equals(courseType))// 课程性质
			sb.append(" AND C.FLAG_COURSETYPE = '" + courseType + "' ");
		if (null != courseCode && !"".equals(courseCode))// 课程编号
			sb.append(" AND UPPER(C.CODE) LIKE '%" + courseCode.toUpperCase() + "%' ");
		if (null != courseName && !"".equals(courseName))// 课程名称
			sb.append(" AND UPPER(C.NAME) LIKE '%" + courseName.toUpperCase() + "%' ");
		if (null != time && !"".equals(time))// 学时
			sb.append(" AND C.TIME LIKE '%" + time + "%' ");
		if (null != courseItemType && !"".equals(courseItemType))// 大纲分类
			sb.append(" AND C.FLAG_COURSE_ITEM_TYPE = '" + courseItemType + "' ");
		if (null != courseContent && !"".equals(courseContent))// 内容属性
			sb.append(" AND C.FLAG_CONTENT_PROPERTY = '" + courseContent + "' ");
		if (null != coursearea && !"".equals(coursearea))// 所属区域
			sb.append(" AND C.FLAG_COURSEAREA = '" + coursearea + "' ");
		if (null != teacher && !"".equals(teacher))// 主讲人
			sb.append(" AND UPPER(C.TEACHER) LIKE '%" + teacher.toUpperCase() + "%' ");
		if (null != suggestren && !"".equals(suggestren))// 建议学习人群
			sb.append(" AND INSTR(FS, '" + suggestren + "', 1, 1) > 0 ");
		List list = this.getGeneralDao().getBySQL(sb.toString());
		return list;
	}

	// @Cacheable(extension =
	// "[0]+'_'+[1]+'_'+[2]+'_'+[3]+'_'+[4]+'_'+[5]+'_'+[6]+'_'+[7]+'_'+[8]+'_'+[9]",
	// tTLSeconds = 30)
	public List tongjiFreeCourse(String stuId, String courseCode, String courseName, String courseType, String courseItemType,
			String courseCategory, String courseContent, String time, String teacher, String suggestren) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT NVL(SUM(PBTC.TIME), 0) ");
		sb.append("   FROM PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("  INNER JOIN PE_BZZ_BATCH PBB ");
		sb.append("     ON PBTO.FK_BATCH_ID = PBB.ID ");
		sb.append("    AND PBB.ID = '40288a7b394d676d01394dad824c003b' ");
		sb.append("    AND PBTO.ID NOT IN ");
		sb.append("        (SELECT P.FK_TCH_OPENCOURSE_ID ");
		sb.append("           FROM PR_BZZ_TCH_STU_ELECTIVE_BACK P ");
		sb.append("          WHERE P.FK_STU_ID = '" + stuId + "' ");
		sb.append("            AND P.FK_TCH_OPENCOURSE_ID IS NOT NULL) ");
		sb.append("  INNER JOIN PE_BZZ_TCH_COURSE PBTC ");
		sb.append("     ON PBTO.FK_COURSE_ID = PBTC.ID ");
		sb.append("    AND PBTO.ID IS NOT NULL ");
		sb.append("    AND PBTC.FLAG_COURSEAREA = 'Coursearea1' ");
		sb.append("    AND PBTC.FLAG_ISVALID = '2' ");
		sb.append("    AND (PBTC.END_DATE IS NULL OR PBTC.END_DATE >= SYSDATE) ");
		sb.append("    AND PBTC.FLAG_OFFLINE = '22' ");
		sb.append("  INNER JOIN ENUM_CONST EC1 ");
		sb.append("     ON PBTC.FLAG_COURSETYPE = EC1.ID ");
		sb.append("  INNER JOIN ENUM_CONST E2 ");
		sb.append("     ON PBTC.FLAG_ISVALID = E2.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC3 ");
		sb.append("     ON PBTC.FLAG_COURSECATEGORY = EC3.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC4 ");
		sb.append("     ON PBTC.FLAG_CONTENT_PROPERTY = EC4.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC5 ");
		sb.append("     ON PBTC.FLAG_COURSE_ITEM_TYPE = EC5.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC6 ");
		sb.append("     ON PBTC.FLAG_ISFREE = EC6.ID ");
		sb.append("     AND EC6.CODE = '1' ");
		sb.append("  WHERE 1 = 1 ");
		if (courseCode != null && !"".equalsIgnoreCase(courseCode)) {
			sb.append(" AND UPPER(PBTC.CODE) LIKE '%" + courseCode.toUpperCase() + "%' ");
		}
		if (courseName != null && !"".equalsIgnoreCase(courseName)) {
			sb.append(" AND UPPER(PBTC.NAME) LIKE '%" + courseName.toUpperCase() + "%' ");
		}
		if (courseType != null && !"".equalsIgnoreCase(courseType)) {
			sb.append(" AND PBTC.FLAG_COURSETYPE = '" + courseType + "'");
		}
		if (courseItemType != null && !"".equalsIgnoreCase(courseItemType)) {
			sb.append(" AND PBTC.FLAG_COURSE_ITEM_TYPE = '" + courseItemType + "'");
		}
		if (courseCategory != null && !"".equalsIgnoreCase(courseCategory)) {
			sb.append(" AND PBTC.FLAG_COURSECATEGORY = '" + courseCategory + "'");
		}
		if (courseContent != null && !"".equalsIgnoreCase(courseContent)) {
			sb.append(" AND PBTC.FLAG_CONTENT_PROPERTY = '" + courseContent + "'");
		}
		if (time != null && !"".equalsIgnoreCase(time)) {
			sb.append(" AND PBTC.TIME = '" + time + "'");
		}
		if (teacher != null && !"".equalsIgnoreCase(teacher)) {
			sb.append(" AND UPPER(PBTC.TEACHER) LIKE '%" + teacher.toUpperCase() + "%' ");
		}
		if (suggestren != null && !"".equalsIgnoreCase(suggestren)) {
			sb
					.append(" AND PBTC.ID IN (SELECT DISTINCT PBTCS.FK_COURSE_ID FROM PE_BZZ_TCH_COURSE_SUGGEST PBTCS, ENUM_CONST EC WHERE PBTCS.FK_ENUM_CONST_ID = EC.ID AND EC.NAMESPACE = 'FlagSuggest' AND EC.NAME = '"
							+ suggestren + "') ");
		}
		List list = this.getGeneralDao().getBySQL(sb.toString());
		return list;
	}

	/**
	 * 查询个人主页条目数量的方法
	 */
	public List getNum(String method, String loginId, String studentId) throws Exception {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sb = new StringBuffer();
		if ("zhanneixin".equals(method)) {// 未读站内信
			sb.append(" SELECT COUNT(*) FROM ( ");
			sb.append(" SELECT S.ID, S.TITLE, PM.TRUE_NAME AS TRUE_NAME, S.SENDDATE, S.STATUS ");
			sb.append("   FROM SITE_EMAIL S, PE_MANAGER PM ");
			sb.append("  WHERE S.SENDER_ID = PM.LOGIN_ID ");
			sb.append("    AND S.ADDRESSEE_DEL = 0 ");
			sb.append("    AND S.STATUS = 0 ");
			sb.append("    AND S.ADDRESSEE_ID = '" + loginId + "' ");
			sb.append(" UNION ");
			sb.append(" SELECT S.ID, S.TITLE, PEM.NAME AS TRUE_NAME, S.SENDDATE, S.STATUS ");
			sb.append("   FROM SITE_EMAIL S, PE_ENTERPRISE_MANAGER PEM ");
			sb.append("  WHERE S.SENDER_ID = PEM.LOGIN_ID ");
			sb.append("    AND S.ADDRESSEE_DEL = 0 ");
			sb.append("    AND S.STATUS = 0 ");
			sb.append("    AND S.ADDRESSEE_ID = '" + loginId + "' ");
			sb.append(" UNION ");
			sb.append(" SELECT S.ID, S.TITLE, PS.TRUE_NAME, S.SENDDATE, S.STATUS ");
			sb.append("   FROM SITE_EMAIL S, PE_BZZ_STUDENT PS ");
			sb.append("  WHERE S.SENDER_ID = PS.REG_NO ");
			sb.append("    AND S.ADDRESSEE_DEL = 0 ");
			sb.append("    AND S.STATUS = 0 ");
			sb.append("    AND S.ADDRESSEE_ID = '" + loginId + "') ");
		}
		if ("tongzhigonggao".equals(method)) {// 通知公告
			StringBuffer sbCodeBuffer = new StringBuffer();
			sbCodeBuffer.append(" SELECT CODE FROM PE_ENTERPRISE WHERE ID = (SELECT T1.ID ");
			sbCodeBuffer.append(" FROM (SELECT (CASE WHEN PE.FK_PARENT_ID IS NULL THEN ");
			sbCodeBuffer.append(" PE.ID ELSE PE.FK_PARENT_ID END) ID ");
			sbCodeBuffer.append(" FROM PE_ENTERPRISE PE, PE_BZZ_STUDENT STU ");
			sbCodeBuffer.append(" WHERE PE.ID = STU.FK_ENTERPRISE_ID ");
			sbCodeBuffer.append(" AND STU.FK_SSO_USER_ID = (SELECT ID FROM SSO_USER WHERE LOGIN_ID = '" + loginId + "')) T1) ");
			List codeList = this.getGeneralDao().getBySQL(sbCodeBuffer.toString());
			String enterpriseCode = "-1";
			if (null != codeList && codeList.size() > 0)
				enterpriseCode = codeList.get(0).toString();
			sb.append(" SELECT COUNT(PB.ID) FROM PE_BULLETIN PB, PE_MANAGER PE, ENUM_CONST EC ");
			sb.append(" WHERE PE.ID = PB.FK_MANAGER_ID AND EC.ID = PB.FLAG_ISVALID ");
			sb.append(" AND EC.CODE = '1' AND PB.SCOPE_STRING LIKE '%student%' AND PB.SCOPE_STRING LIKE '%" + enterpriseCode + "%' ");
		}
		if ("diaochawenjuan".equals(method)) {// 可填写的调查问卷
			String sql = "select ec.code from pe_bzz_student pbs join sso_user su on pbs.fk_sso_user_id = su.id join enum_const ec on ec.id = pbs.zige where su.login_id = '"
					+ userSession.getLoginId() + "' ";
			String zige = (String) this.getGeneralDao().getBySQL(sql).get(0);
			sb.append(" SELECT COUNT(*) ");
			sb.append("   FROM PE_VOTE_PAPER A ");
			sb.append("  INNER JOIN ENUM_CONST B ");
			sb.append("     ON A.FLAG_ISVALID = B.ID ");
			sb.append("  INNER JOIN ENUM_CONST C ");
			sb.append("     ON A.FLAG_TYPE = C.ID ");
			sb.append("  WHERE A.TYPE IS NULL ");
			sb.append("    AND B.CODE = '1' ");
			sb.append("    AND (C.CODE = '" + zige + "' OR (C.CODE = '9' OR C.CODE = '90'))");
			sb
					.append("    AND TO_DATE(TO_CHAR(START_DATE, 'yyyy-MM-dd'), 'yyyy-mm-dd') <= to_date(to_char(SYSDATE,'yyyy-mm-dd'),'yyyy-mm-dd') and   TO_DATE(TO_CHAR(end_date, 'yyyy-MM-dd'), 'yyyy-mm-dd') >= to_date(to_char(SYSDATE,'yyyy-mm-dd'),'yyyy-mm-dd')");// 加了一结束日期判断
		}
		if ("weizhifudingdan".equals(method)) {// 未支付的订单
			sb.append(" SELECT COUNT(A.ID) FROM PE_BZZ_ORDER_INFO A ");
			sb.append(" INNER JOIN ENUM_CONST B ON A.FLAG_PAYMENT_STATE = B.ID ");
			sb.append(" INNER JOIN ENUM_CONST C ON A.FLAG_PAYMENT_METHOD = C.ID ");
			sb.append(" LEFT OUTER JOIN ENUM_CONST D ON A.FLAG_REFUND_STATE = D.ID ");
			sb.append(" INNER JOIN SSO_USER E ON A.CREATE_USER = E.ID ");
			sb.append(" INNER JOIN ENUM_CONST F ON A.FLAG_ORDER_ISVALID = F.ID ");
			sb.append(" LEFT OUTER JOIN PE_BZZ_BATCH G ON A.FK_BATCH_ID = G.ID ");
			sb.append(" LEFT OUTER JOIN PE_ELECTIVE_COURSE_PERIOD H ON A.FK_COURSE_PERIOD_ID = H.ID ");
			sb.append(" LEFT OUTER JOIN PE_SIGNUP_ORDER I ON A.FK_SIGNUP_ORDER_ID = I.ID ");
			sb
					.append(" WHERE F.CODE = '1' AND E.LOGIN_ID = '" + loginId
							+ "' AND B.CODE <> '1' AND B.CODE <> '2' AND C.NAME <> '预付费账户支付' ");
		}
		if ("xuankeqi".equals(method)) {// 选课期
			sb.append(" SELECT COUNT(DISTINCT A.ID) FROM ");
			sb.append(" (SELECT AA.ID, AA.NAME, AA.BEGIN_DATE, AA.END_DATE, AA.STU_TIME FROM PE_ELECTIVE_COURSE_PERIOD AA ");
			sb.append(" RIGHT JOIN (SELECT * FROM COURSE_PERIOD_STUDENT WHERE STUDENT_ID = '" + studentId + "') BB ");
			sb.append(" ON AA.ID = BB.COURSE_PERIOD_ID WHERE SYSDATE BETWEEN AA.BEGIN_DATE AND AA.END_DATE) A ");
			sb.append(" LEFT JOIN  ");
			sb.append(" (SELECT E.ID, C.TIME ");
			sb.append(" FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A ");
			sb.append(" INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID ");
			sb.append(" INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID ");
			sb.append(" INNER JOIN ENUM_CONST D ON C.FLAG_COURSECATEGORY = D.ID ");
			sb.append(" INNER JOIN PE_ELECTIVE_COURSE_PERIOD E ON A.FK_ELE_COURSE_PERIOD_ID = E.ID ");
			sb.append(" WHERE A.FK_STU_ID = '" + studentId + "' AND SYSDATE BETWEEN E.BEGIN_DATE AND E.END_DATE) B ");
			sb.append(" ON A.ID = B.ID ");

		}
		if ("yufufeizhanghuyue".equals(method)) {// 预付费账户余额
			sb.append(" SELECT ABS(SUM_AMOUNT-AMOUNT) FROM SSO_USER WHERE LOGIN_ID = '" + loginId + "' ");
		}
		if ("zhengzaixuexikecheng".equals(method)) {// 正在学习课程
			sb
					.append(" SELECT COUNT(*),SUM(C.TIME) FROM PR_BZZ_TCH_STU_ELECTIVE A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID INNER JOIN PE_BZZ_TCH_COURSE C ");
			sb
					.append(" ON B.FK_COURSE_ID = C.ID LEFT JOIN TRAINING_COURSE_STUDENT D ON A.FK_TRAINING_ID = D.ID LEFT JOIN PE_BZZ_ORDER_INFO E ON A.FK_ORDER_ID = E.ID ");
			sb
					.append(" LEFT JOIN (SELECT FK_COURSE_ID, TO_CHAR(WM_CONCAT(FK_ENUM_CONST_ID)) FS FROM PE_BZZ_TCH_COURSE_SUGGEST WHERE TABLE_NAME = 'PE_BZZ_TCH_COURSE' ");
			sb
					.append(" GROUP BY FK_COURSE_ID) F ON C.ID = F.FK_COURSE_ID INNER JOIN ENUM_CONST EC1 ON C.FLAG_COURSEAREA = EC1.ID INNER JOIN ENUM_CONST EC2 ");
			sb.append(" ON C.FLAG_COURSETYPE = EC2.ID LEFT JOIN ENUM_CONST EC3 ON E.FLAG_REFUND_STATE = EC3.ID INNER JOIN ENUM_CONST EC4 ");
			sb.append(" ON C.FLAG_ISFREE = EC4.ID WHERE A.FK_STU_ID = '" + studentId
					+ "' AND UPPER(A.ISPASS) <> '1' AND D.LEARN_STATUS <> 'COMPLETED' AND C.FLAG_COURSEAREA != 'Coursearea0'");
		}
		if ("daikaoshikecheng".equals(method)) {// 待考试课程
			sb.append(" SELECT COUNT(PBTC.ID), SUM(PBTC.TIME) ");
			sb.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
			sb.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sb.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sb.append("  INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sb.append("     ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
			sb.append("    AND FK_STU_ID = '" + studentId + "' ");
			sb.append("    AND (PBTC.FLAG_IS_EXAM = '40288acf3ae01103013ae012940b0001' AND ");
			sb.append("        NVL(PBTSE.SCORE_EXAM, 0) < NVL(PBTC.PASSROLE, 0)) ");
			sb.append("  INNER JOIN TRAINING_COURSE_STUDENT TCS ");
			sb.append("     ON PBTSE.FK_TRAINING_ID = TCS.ID ");
			sb.append("    AND STUDENT_ID = (SELECT FK_SSO_USER_ID FROM PE_BZZ_STUDENT WHERE ID = '" + studentId + "') ");
			sb.append("    AND LEARN_STATUS = 'COMPLETED' ");
			sb.append("  INNER JOIN PE_BZZ_STUDENT PBS ");
			sb.append("     ON PBTSE.FK_STU_ID = PBS.ID ");
			sb.append("  INNER JOIN SSO_USER SU ");
			sb.append("     ON PBS.FK_SSO_USER_ID = SU.ID ");
			sb.append("  INNER JOIN ENUM_CONST EC3 ");
			sb.append("     ON PBTC.FLAG_COURSEAREA = EC3.ID ");
			sb.append("  INNER JOIN ENUM_CONST EC4 ");
			sb.append("     ON PBTC.FLAG_ISFREE = EC4.ID ");
		}
		if ("zhuanxiangpeixun".equals(method)) {// 专项培训
			sb.append(" select count(*) from (");
			sb.append(" SELECT COUNT(DISTINCT PB.ID) as id FROM PE_BZZ_BATCH PB INNER JOIN STU_BATCH SB ON SB.BATCH_ID = PB.ID ");
			sb
					.append(" INNER JOIN (SELECT PTC.TIME, PTO.FK_BATCH_ID FROM PE_BZZ_TCH_COURSE PTC, PR_BZZ_TCH_OPENCOURSE PTO WHERE PTC.ID = PTO.FK_COURSE_ID) PC ON PC.FK_BATCH_ID = PB.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC ON EC.ID = SB.FLAG_BATCHPAYSTATE ");
			sb.append(" INNER JOIN ENUM_CONST E ON E.ID = PB.FLAG_DEPLOY AND E.CODE = '1' ");
			sb.append(" INNER JOIN ENUM_CONST ECTYPE ON PB.FLAG_BATCH_TYPE = ECTYPE.ID AND ECTYPE.CODE = '0' ");// 普通直播
			sb
					.append(" WHERE SB.STU_ID = '"
							+ studentId
							+ "' and ec.code='0' GROUP BY PB.ID, PB.NAME, PB.START_TIME, PB.END_TIME, PB.STANDARDS, PB.BATCH_NOTE, EC.CODE, PB.SUGG_TIME, SB.FK_ORDER_ID ");
			// sb.append(" HAVING TO_DATE(TO_CHAR(PB.START_TIME, 'yyyy-MM-dd')
			// || ' 00:00:00', 'yyyy-MM-dd hh24:mi:ss') <= SYSDATE AND
			// TO_DATE(TO_CHAR(PB.END_TIME, 'yyyy-MM-dd') || ' 23:59:59',
			// 'yyyy-MM-dd hh24:mi:ss') >= SYSDATE ");
			sb.append(" HAVING TO_DATE(TO_CHAR(PB.END_TIME, 'yyyy-MM-dd') || ' 23:59:59', 'yyyy-MM-dd hh24:mi:ss') >= SYSDATE  "
					+ " and TO_DATE(TO_CHAR(PB.START_TIME, 'yyyy-MM-dd') || ' 00:00:00', 'yyyy-MM-dd hh24:mi:ss') <= SYSDATE )");
		}
		if ("zaixianzhibokecheng".equals(method)) {// 在线直播课程
			// Lee：二期功能、暂时注释
			sb.append(" SELECT SUM(COUNT) ");
			sb.append("   FROM (SELECT COUNT(*) COUNT ");
			sb.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
			sb.append("          INNER JOIN ENUM_CONST ECFREEZE ON PBTC.FLAG_FREEZE = ECFREEZE.ID ");
			sb.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sb.append("          INNER JOIN STU_BATCH SB ON PBTO.FK_BATCH_ID = SB.BATCH_ID AND SB.FK_ORDER_ID IS NULL ");
			sb.append("          INNER JOIN PE_BZZ_STUDENT PBS ON SB.STU_ID = PBS.ID AND PBS.id = '" + studentId + "' ");
			sb.append("          INNER JOIN ENUM_CONST ECSIGNTYPE ON PBTC.FLAG_SIGNTYPE = ECSIGNTYPE.ID AND ECSIGNTYPE.CODE = '0' ");
			sb.append("                                          AND PBTC.FLAG_ISVALID = '2' AND PBTC.FLAG_COURSEAREA = 'Coursearea0' ");
			sb.append("          INNER JOIN ENUM_CONST ECROLE ON PBTC.FLAG_LIVEPASSROLE = ECROLE.ID ");
			sb.append("           LEFT JOIN ENUM_CONST ECPAYSTATE ON SB.FLAG_BATCHPAYSTATE = ECPAYSTATE.ID ");
			sb.append(" LEFT JOIN LIVE_SEQ_STU LSS ON PBS.FK_SSO_USER_ID = LSS.LOGIN_ID ");
			sb.append("           LEFT JOIN WE_HISTORY WH ON WH.WH_UID = LSS.ID ");
			sb.append("                                  AND PBTC.LIVEID = WH.WH_WEBCASTID ");
			sb.append("          WHERE PBTC.SIGNSTARTDATE <= SYSDATE ");
			sb.append("            AND PBTC.SIGNENDDATE >= SYSDATE ");
			sb.append("         UNION ALL ");
			sb.append("         SELECT COUNT(*) COUNT ");
			sb.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
			sb.append("          INNER JOIN ENUM_CONST ECFREEZE ON PBTC.FLAG_FREEZE = ECFREEZE.ID ");
			sb.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID ");
			sb
					.append("                                               AND PBTC.FLAG_ISVALID = '2' AND PBTC.FLAG_COURSEAREA = 'Coursearea0' ");
			sb.append("          INNER JOIN PE_BZZ_BATCH PBB ON PBTO.FK_BATCH_ID = PBB.ID ");
			sb.append("          INNER JOIN ENUM_CONST ECROLE ON PBTC.FLAG_LIVEPASSROLE = ECROLE.ID ");
			sb.append("          INNER JOIN ENUM_CONST ECSIGNTYPE ON PBTC.FLAG_SIGNTYPE = ECSIGNTYPE.ID AND ECSIGNTYPE.CODE = '1' ");
			sb.append("          INNER JOIN ENUM_CONST ECBATCHTYPE ON PBB.FLAG_BATCH_TYPE = ECBATCHTYPE.ID ");
			sb.append("                                           AND ECBATCHTYPE.CODE = '2' ");
			sb.append("           LEFT JOIN (SELECT SB.STU_ID, SB.BATCH_ID ");
			sb.append("                        FROM STU_BATCH SB ");
			sb.append("                       INNER JOIN PE_BZZ_STUDENT PBS ON SB.STU_ID = PBS.ID ");
			sb.append("                                                    AND PBS.FK_SSO_USER_ID = '" + studentId + "' ");
			sb.append("                        LEFT JOIN ENUM_CONST ECPAYSTATE ON SB.FLAG_BATCHPAYSTATE = ECPAYSTATE.ID ");
			sb.append("                       WHERE SB.FK_ORDER_ID IS NULL) SPPEE ON PBB.ID = SPPEE.BATCH_ID ");
			sb.append("           LEFT JOIN PE_BZZ_STUDENT PBS ON SPPEE.STU_ID = PBS.ID ");
			sb.append("           LEFT JOIN LIVE_SEQ_STU LSS ON PBS.FK_SSO_USER_ID = LSS.LOGIN_ID ");
			sb.append("           LEFT JOIN WE_HISTORY WH ON WH.WH_UID = LSS.ID ");
			sb.append("                                  AND PBTC.LIVEID = WH.WH_WEBCASTID ");
			sb.append("          WHERE PBTC.SIGNSTARTDATE <= SYSDATE ");
			sb.append("            AND PBTC.SIGNENDDATE >= SYSDATE)");
		}
		if ("zaixiankaoshi".equals(method)) {// 在线考试--未写
			sb.append(" SELECT count(PBPS.STU_ID) ");
			sb.append("   FROM PE_BZZ_PICI_STUDENT pbps ");
			sb.append("   JOIN PE_BZZ_STUDENT PBS ON PBPS.STU_ID = PBS.ID ");
			sb.append("   JOIN PE_BZZ_PICI PBP ON PBPS.PC_ID = PBP.ID ");
			sb.append("   JOIN ENUM_CONST EC ON PBP.STATUS = EC.ID ");
			sb.append("  WHERE PBP.START_TIME <= SYSDATE ");
			sb.append("    AND PBP.END_TIME >= SYSDATE ");
			sb.append("    AND EC.CODE = '0' ");
			sb.append("    AND PBS.ID = '" + studentId + "' ");
		}
		List list = null;
		list = this.getGeneralDao().getBySQL(sb.toString());
		return list;
	}

	/**
	 * 待考试课程-Lee正在使用
	 * 
	 * @author Lee
	 * @param stuId学员ID
	 * @param courseType课程性质
	 * @param courseCode课程编号
	 * @param courseName课程名称
	 * @param time学时
	 * @param teacher主讲人
	 * @param coursearea所属区域
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws Exception
	 */
	public Page initCompletedCourses(String stuId, String courseType, String courseCode, String courseName, String time, String teacher,
			String coursearea, String orderInfo, String orderType, int pageSize, int startIndex) throws Exception {
		StringBuffer sb = new StringBuffer();
		EnumConst ec = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		EnumConst ec_isExam = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sid = us.getSsoUser().getId();
		sb.append(" SELECT DISTINCT PBTC.ID AS COURSEID, ");
		sb.append("                 PBTC.CODE AS COURSECODE, ");
		sb.append("                 PBTC.NAME AS COURSENAME, ");
		sb.append("                 PBTC.TIME AS COURSETIME, ");
		sb.append("                 EC.NAME AS COURSETYPE, ");
		sb.append("                 TO_CHAR(TCS.GET_DATE,'yyyy-MM-dd hh24:mi:ss') AS STARTDATE, ");
		sb.append("                 SSC.LAST_DATE AS ENDDATE, ");
		sb.append("                 TCS.LEARN_STATUS AS LEATNRESULT, ");
		sb.append("                 PBTO.ID AS OPCOURSEID, ");
		sb.append("                 PBTC.TEACHER AS PBTCTEACHER, ");
		sb.append("                 CASE ");
		sb.append("                   WHEN PBTC.FLAG_IS_EXAM = ");
		sb.append("                        '40288acf3ae01103013ae0130d030002' THEN ");
		sb.append("                    'NOEXAM' ");
		sb.append("                   WHEN NVL(PBTSE.SCORE_EXAM, 0) >= NVL(PBTC.PASSROLE, 0) THEN ");
		sb.append("                    'PASS' ");
		sb.append("                   ELSE ");
		sb.append("                    'FAILED' ");
		sb.append("                 END AS SCORE_EXAM, ");
		sb.append("                 PBTC.STUDYDATES AS STUDYDATES, ");
		sb.append("                 EC2.CODE AS VISITCODE, ");
		sb.append("                 CASE ");
		sb.append("                   WHEN PBTC.FLAG_IS_EXAM = ");
		sb.append("                        '40288acf3ae01103013ae012940b0001' AND ");
		sb.append("                        NVL(PBTSE.SCORE_EXAM, 0) < NVL(PBTC.PASSROLE, 0) AND ");
		sb.append("                        PBTSE.EXAM_TIMES >= PBTC.EXAMTIMES_ALLOW THEN ");
		sb.append("                    'GOBUY' ");
		sb.append("                   ELSE ");
		sb.append("                    'GOON' ");
		sb.append("                 END AS EXAM_STATUS, ");
		sb.append("                 CASE ");
		sb.append("                   WHEN PBTC.STUDYDATES = 0 THEN ");
		sb.append("                    '-' ");
		sb.append("                   ELSE ");
		sb.append("                    TO_CHAR(TCS.GET_DATE + PBTC.STUDYDATES, ");
		sb.append("                            'yyyy-MM-dd hh24:mi:ss') ");
		sb.append("                 END AS GQSJ, ");
		sb.append("                 EC3.NAME AS COURSEAREA, ");
		sb.append("                 EC4.CODE, ");
		sb.append("                 PBTC.FLAG_ISFREE, ");
		sb.append("                 EC3.ID,SSS.LAST_ACCESSDATE ");
		sb.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
		sb.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("  INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sb.append("     ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		sb.append("    AND FK_STU_ID = '" + stuId + "' ");
		sb.append("    AND (PBTC.FLAG_IS_EXAM = '" + ec_isExam.getId() + "' AND ");
		sb.append("        NVL(PBTSE.SCORE_EXAM, 0) < NVL(PBTC.PASSROLE, 0)) ");
		sb.append("  INNER JOIN TRAINING_COURSE_STUDENT TCS ");
		sb.append("     ON PBTSE.FK_TRAINING_ID = TCS.ID ");
		sb.append("    AND STUDENT_ID = '" + sid + "' ");
		sb.append("    AND LEARN_STATUS = '" + StudyProgress.COMPLETED + "' ");
		sb.append("   LEFT JOIN SCORM_COURSE_INFO SCI ");
		sb.append("     ON PBTC.CODE = SCI.ID ");
		sb.append("   LEFT JOIN SCORM_STU_COURSE SSC ");
		sb.append("     ON SCI.ID = SSC.COURSE_ID ");
		sb.append("    AND SSC.STUDENT_ID = '" + stuId + "' ");
		sb.append("  INNER JOIN PE_BZZ_STUDENT PBS ");
		sb.append("     ON PBTSE.FK_STU_ID = PBS.ID ");
		sb.append("  INNER JOIN SSO_USER SU ");
		sb.append("     ON PBS.FK_SSO_USER_ID = SU.ID ");
		sb.append("   LEFT JOIN ENUM_CONST EC ");
		sb.append("     ON PBTC.FLAG_COURSETYPE = EC.ID ");
		sb.append("   LEFT JOIN ENUM_CONST EC2 ");
		sb.append("     ON PBTC.FLAG_ISVISITAFTERPASS = EC2.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC3 ");
		sb.append("     ON PBTC.FLAG_COURSEAREA = EC3.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC4 ");
		sb.append("     ON PBTC.FLAG_ISFREE = EC4.ID ");
		sb.append("	  LEFT JOIN SCORM_STU_SCO SSS ON SU.ID = SSS.STUDENT_ID AND PBTC.CODE = SSS.COURSE_ID ");
		sb.append("	WHERE 1 = 1 ");
		if (null != courseType && !"".equals(courseType))
			sb.append(" AND PBTC.FLAG_COURSETYPE = '" + courseType + "' ");
		if (null != courseCode && !"".equals(courseCode))
			sb.append(" AND UPPER(PBTC.CODE) LIKE '%" + courseCode.toUpperCase() + "%'");
		if (null != courseName && !"".equals(courseName))
			sb.append(" AND UPPER(PBTC.NAME) LIKE '%" + courseName.toUpperCase() + "%'");
		if (null != time && !"".equals(time))
			sb.append(" AND PBTC.TIME = '" + time + "'");
		if (null != teacher && !"".equals(teacher))
			sb.append(" AND UPPER(PBTC.TEACHER) LIKE '%" + teacher.toUpperCase() + "%'");
		if (null != coursearea && !"".equals(coursearea))
			sb.append(" AND PBTC.FLAG_COURSEAREA = '" + coursearea + "'");
		if(orderInfo != null && !"".equals(orderInfo)) {
			sb.append(" ORDER BY ");
			if("pbtc_code".equals(orderInfo)) {
				sb.append(" PBTC.CODE ");
			} else if("tcs_get_date".equals(orderInfo)) {
				sb.append(" STARTDATE ");
			} else if("lastTime".equals(orderInfo)) {
				sb.append(" SSS.LAST_ACCESSDATE ");
			}
			sb.append(orderType);
		} else {
			sb.append(" ORDER BY  PBTC.CODE ");
		}
		Page page = null;
		page = this.generalDao.getByPageSQL(sb.toString(), pageSize, startIndex);
		return page;
	}

	/**
	 * 已通过课程-正在使用
	 * 
	 * @author Lee
	 * @param stuId学员ID
	 * @param courseType课程性质
	 * @param time学时
	 * @param teacher主讲人
	 * @param coursearea所属区域
	 * @param selSendDateStart获得学时时间起
	 * @param selSendDateEnd获得学时时间止
	 * @return
	 */
	public Page initPassedCourses(String stuId, String courseType, String time, String teacher, String coursearea, String selSendDateStart,
			String selSendDateEnd, String courseCode, String courseName, String orderInfo, String orderType, int pageSize, int startIndex) throws Exception {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		EnumConst ec = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		String sid = us.getSsoUser().getId();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ID, ");
		sb.append("        CODE, ");
		sb.append("        NAME, ");
		sb.append("        TIME, ");
		sb.append("        COURSETYPE, ");
		sb.append("        LEARN_STATUS, ");
		sb.append("        ID2, ");
		sb.append("        TEACHER, ");
		sb.append("        CODE2, ");
		sb.append("        NAME2, ");
		sb.append("        TO_CHAR(COMPLETED_TIME, 'yyyy-MM-dd hh24:mi:ss'), ");
		sb.append("        SCORE_EXAM, ");
		sb.append("        VISITCODE, ");
		sb.append("        CODE3, GET_DATE, LAST_ACCESSDATE ");
		sb.append("   FROM (SELECT DISTINCT PBTC.ID, ");
		sb.append("                         PBTC.CODE, ");
		sb.append("                         PBTC.NAME, ");
		sb.append("                         PBTC.TIME, ");
		sb.append("                         EC.NAME AS COURSETYPE, ");
		sb.append("                         TCS.LEARN_STATUS, ");
		sb.append("                         PBTO.ID ID2, ");
		sb.append("                         PBTC.TEACHER, ");
		sb.append("                         EC2.CODE CODE2, ");
		sb.append("                         EC3.NAME NAME2, ");
		sb.append("                         PBTSE.COMPLETED_TIME, ");
		sb.append("                         CASE ");
		sb.append("                           WHEN PBTC.FLAG_IS_EXAM = '" + ec.getId() + "' THEN ");
		sb.append("                            'NOEXAM' ");
		sb.append("                           WHEN NVL(PBTSE.SCORE_EXAM, 0) >= ");
		sb.append("                                NVL(PBTC.PASSROLE, 0) THEN ");
		sb.append("                            'PASS' ");
		sb.append("                           ELSE ");
		sb.append("                            'FAILED' ");
		sb.append("                         END AS SCORE_EXAM, ");
		sb.append("                         EC2.CODE AS VISITCODE, ");
		sb.append("                         EC3.CODE CODE3, TCS.GET_DATE, SSS.LAST_ACCESSDATE ");
		sb.append("           FROM PE_BZZ_TCH_COURSE PBTC ");
		sb.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("             ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("          INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sb.append("             ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		sb.append("            AND FK_STU_ID = '" + stuId + "' ");
		sb.append("          INNER JOIN TRAINING_COURSE_STUDENT TCS ");
		sb.append("             ON PBTSE.FK_TRAINING_ID = TCS.ID ");
		sb.append("            AND STUDENT_ID = '" + sid + "' ");
		sb.append("            AND LEARN_STATUS = '" + StudyProgress.COMPLETED + "' ");
		sb.append("           LEFT JOIN SCORM_COURSE_INFO SCI ");
		sb.append("             ON PBTC.CODE = SCI.ID ");
		sb.append("           LEFT JOIN SCORM_STU_COURSE SSC ");
		sb.append("             ON SCI.ID = SSC.COURSE_ID ");
		sb.append("            AND SSC.STUDENT_ID = '" + stuId + "' ");
		sb.append("          INNER JOIN PE_BZZ_STUDENT PBS ");
		sb.append("             ON PBTSE.FK_STU_ID = PBS.ID ");
		sb.append("           LEFT JOIN SSO_USER SU ");
		sb.append("             ON PBS.FK_SSO_USER_ID = SU.ID ");
		sb.append("			  LEFT JOIN SCORM_STU_SCO SSS ON SU.ID = SSS.STUDENT_ID AND PBTC.CODE = SSS.COURSE_ID ");
		sb.append("           LEFT JOIN ENUM_CONST EC ");
		sb.append("             ON PBTC.FLAG_COURSETYPE = EC.ID ");
		sb.append("           LEFT JOIN ENUM_CONST EC2 ");
		sb.append("             ON PBTC.FLAG_ISVISITAFTERPASS = EC2.ID ");
		sb.append("          INNER JOIN ENUM_CONST EC3 ");
		sb.append("             ON PBTC.FLAG_COURSEAREA = EC3.ID ");
		sb.append("          INNER JOIN ENUM_CONST EC4 ");
		sb.append("             ON PBTC.FLAG_IS_EXAM = EC4.ID ");
		sb.append("          WHERE (PBTSE.ISPASS = 1 OR EC4.CODE = '0') ");
		if (null != courseType && !"".equals(courseType))// 课程性质
			sb.append(" AND PBTC.FLAG_COURSETYPE = '" + courseType + "'");
		if (null != time && !"".equals(time))// 课程学时
			sb.append(" AND PBTC.TIME = '" + time + "'");
		if (null != teacher && !"".equals(teacher))// 主讲人
			sb.append(" AND UPPER(PBTC.TEACHER) LIKE '%" + teacher.toUpperCase() + "%'");
		if (null != coursearea && !"".equals(coursearea))// 所属区域
			sb.append(" AND PBTC.FLAG_COURSEAREA = '" + coursearea + "'");
		if (null != selSendDateStart && !"".equals(selSendDateStart))// 获得学时时间起
			sb.append(" AND PBTSE.COMPLETED_TIME >= TO_DATE('" + selSendDateStart + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
		if (null != selSendDateEnd && !"".equals(selSendDateEnd))// 获得学时时间止
			sb.append(" AND PBTSE.COMPLETED_TIME <= TO_DATE('" + selSendDateEnd + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
		if (null != courseCode && !"".equals(courseCode))// 课程编号
			sb.append(" AND UPPER(PBTC.CODE) LIKE '%" + courseCode.toUpperCase() + "%'");
		if (null != courseName && !"".equals(courseName))// 课程名称
			sb.append(" AND UPPER(PBTC.NAME) LIKE '%" + courseName.toUpperCase() + "%'");
		sb.append(" ORDER BY  PBTSE.COMPLETED_TIME desc ) ");
		if(orderInfo != null && !"".equals(orderInfo)) {
			sb.append(" ORDER BY " + orderInfo + " " + orderType);
		}
		Page page = null;
		page = this.getGeneralDao().getByPageSQL(sb.toString(), pageSize, startIndex);
		return page;
	}

	public List statisPassedCourse(String stuId, String courseType, String time, String teacher, String coursearea,
			String selSendDateStart, String selSendDateEnd, String courseCode, String courseName) {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		EnumConst ec = this.getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "0");
		String sid = us.getSsoUser().getId();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT NVL(SUM(PBTC.TIME), 0) || ',' || ");
		sb.append("        NVL(SUM(CASE ");
		sb.append("                  WHEN EC.CODE = '0' THEN ");
		sb.append("                   PBTC.TIME ");
		sb.append("                  ELSE ");
		sb.append("                   NULL ");
		sb.append("                END), ");
		sb.append("            0) || ',' || NVL(SUM(CASE ");
		sb.append("                                   WHEN EC.CODE = '1' THEN ");
		sb.append("                                    PBTC.TIME ");
		sb.append("                                   ELSE ");
		sb.append("                                    NULL ");
		sb.append("                                 END), ");
		sb.append("                             0) ");
		sb.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
		sb.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("  INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		sb.append("     ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		sb.append("    AND FK_STU_ID = '" + stuId + "' ");
		sb.append("  INNER JOIN TRAINING_COURSE_STUDENT TCS ");
		sb.append("     ON PBTSE.FK_TRAINING_ID = TCS.ID ");
		sb.append("    AND STUDENT_ID = '" + sid + "' ");
		sb.append("    AND LEARN_STATUS = '" + StudyProgress.COMPLETED + "' ");
		sb.append("  INNER JOIN PE_BZZ_STUDENT PBS ");
		sb.append("     ON PBTSE.FK_STU_ID = PBS.ID ");
		sb.append("   LEFT JOIN ENUM_CONST EC ");
		sb.append("     ON PBTC.FLAG_COURSETYPE = EC.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC3 ");
		sb.append("     ON PBTC.FLAG_COURSEAREA = EC3.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC4 ");
		sb.append("     ON PBTC.FLAG_IS_EXAM = EC4.ID ");
		sb.append("  WHERE (PBTSE.ISPASS = 1 OR EC4.CODE = '0') ");
		if (null != courseType && !"".equals(courseType))// 课程性质
			sb.append(" AND PBTC.FLAG_COURSETYPE = '" + courseType + "'");
		if (null != time && !"".equals(time))// 课程学时
			sb.append(" AND PBTC.TIME = '" + time + "'");
		if (null != teacher && !"".equals(teacher))// 主讲人
			sb.append(" AND UPPER(PBTC.TEACHER) LIKE '%" + teacher.toUpperCase() + "%'");
		if (null != coursearea && !"".equals(coursearea))// 所属区域
			sb.append(" AND PBTC.FLAG_COURSEAREA = '" + coursearea + "'");
		if (null != selSendDateStart && !"".equals(selSendDateStart))// 获得学时时间起
			sb.append(" AND PBTSE.COMPLETED_TIME >= TO_DATE('" + selSendDateStart + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
		if (null != selSendDateEnd && !"".equals(selSendDateEnd))// 获得学时时间止
			sb.append(" AND PBTSE.COMPLETED_TIME <= TO_DATE('" + selSendDateEnd + " 00:00:00','yyyy-MM-dd hh24:mi:ss')");
		if (null != courseCode && !"".equals(courseCode))// 课程编号
			sb.append(" AND UPPER(PBTC.CODE) LIKE '%" + courseCode.toUpperCase() + "%'");
		if (null != courseName && !"".equals(courseName))// 课程名称
			sb.append(" AND UPPER(PBTC.NAME) LIKE '%" + courseName.toUpperCase() + "%'");
		List list = this.getGeneralDao().getBySQL(sb.toString());
		return list;
	}

	public Page initQuestion(String title, String types, String keywords, int pageSize, int startIndex) {
		String sql = "select faq.id,faq.title,faq.types flag_question_type,faq.keywords, faq.views " + "from Frequently_Asked_Questions faq "
				+ " where faq.roles like '%学员%' ";
		if (title != null && !"".equalsIgnoreCase(title)) {
			sql += " and faq.title like '%" + title + "%'";
		}
		if (types != null && !"".equalsIgnoreCase(types)) {
			String[] type = types.split(" ");
			sql += " and (";
			for (int i = 0; i < type.length; i++) {
				sql += " faq.types like '%" + type[i] + "%' or";
			}
			sql = sql.substring(0, sql.lastIndexOf("or")) + ") ";
		}
		if (keywords != null && !"".equalsIgnoreCase(keywords)) {
			sql += " and faq.keywords like '%" + keywords + "%'";
		}
		sql += "order by faq.create_date desc";
		Page page = this.getGeneralDao().getByPageSQL(sql, pageSize, startIndex);
		return page;
	}

	public List<EnumConst> initQuestionType() throws Exception {
		DetachedCriteria dcType = DetachedCriteria.forClass(EnumConst.class);
		dcType.add(Restrictions.eq("namespace", "FlagFrequentlyQuestionType"));
		List<EnumConst> questionType = this.generalDao.getList(dcType);
		return questionType;
	}

	public List<EnumConst> initIssueType() throws Exception {
		DetachedCriteria dcType = DetachedCriteria.forClass(EnumConst.class);
		dcType.add(Restrictions.eq("namespace", "FlagFrequentlyQuestionType"));
		List<EnumConst> questionType = this.generalDao.getList(dcType);
		return questionType;
	}

	public FrequentlyAskedQuestions showQuestion(String qid) throws Exception {
		String hql = "from FrequentlyAskedQuestions where id = '" + qid + "' order by create_date";
		FrequentlyAskedQuestions questions = (FrequentlyAskedQuestions) this.getGeneralDao().getByHQL(hql).get(0);
		return questions;
	}

	public List<EnumConst> iniziLiao(String params) {
		DetachedCriteria dcCategory = DetachedCriteria.forClass(EnumConst.class);
		dcCategory.add(Restrictions.eq("namespace", "FlagZltype"));
		List<EnumConst> ziLiaolist = this.generalDao.getList(dcCategory);
		return ziLiaolist;
	}

	// 资料库

	public Page initZiLiaolist(String name, String ziliaotype, String fabuunit, String ktimestart, String ktimeend, String kname,
			String kbianhao, String tagIds, int pageSize, int startIndex) throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String entype = (String) ServletActionContext.getRequest().getSession().getAttribute("enterpriseType");
		Page page = null;
		StringBuffer sb = new StringBuffer();
		sb
				.append("select zl.id,zl.name,zl.RESETYPE , nvl(to_char(zl.replydate, 'yyyy-MM-dd') ,'-') replydate,zl.FABUUNIT, zl.resourcetags, zl.views, zl.creater from PE_RESOURCE zl inner join enum_const e1 on zl.flag_isvalid=e1.id  and e1.code='1' "
						+ " inner join enum_const e2 on zl.flag_offline = e2.id   and e2.code = '0' ");
		if (null != ziliaotype && !"".equals(ziliaotype))//
			sb.append(" AND zl.RESETYPE like '%" + ziliaotype + "%' ");
		if (null != name && !"".equals(name))// 
			sb.append(" AND zl.name LIKE '%" + name + "%' ");
		if (null != fabuunit && !"".equals(fabuunit))// 
			sb.append(" AND zl.fabuunit LIKE '%" + fabuunit + "%' ");
		if (null != ktimestart && !ktimestart.equals("")) {

			sb.append(" AND zl.replydate >= 	  to_date( '" + ktimestart + "' ,'yyyy-mm-dd') ");
		}
		if (null != ktimeend && !ktimeend.equals("")) {
			// 修改时间格式
			ktimeend = ktimeend;
			sb.append(" AND zl.replydate <=	  to_date( '" + ktimeend + "' ,'yyyy-mm-dd')");
		}
		if (null != kname && !kname.equals("")) {
			sb.append(" and zl.id in (  select zl.id  from PE_RESOURCE zl "
					+ "  inner join interaction_teachclass_info ii on ii.fk_ziliao = zl.id     and ii.type = 'KCZL' "
					+ "   inner join pe_bzz_tch_course pbtc on pbtc.id = ii.teachclass_id and pbtc.name like'%" + kname + "%') ");
		}
		if (null != kbianhao && !kbianhao.equals("")) {
			sb.append(" and zl.id in (  select zl.id  from PE_RESOURCE zl "
					+ "  inner join interaction_teachclass_info ii on ii.fk_ziliao = zl.id     and ii.type = 'KCZL' "
					+ "   inner join pe_bzz_tch_course pbtc on pbtc.id = ii.teachclass_id and pbtc.code like'%" + kbianhao + "%') ");
		}
		
		if(null != tagIds && !"".equals(tagIds)) {
			sb.append(" and zl.resourcetagids like '%" + tagIds + "%'");
		}

		sb.append(" where (zl.flag_isaudit = '2' or zl.flag_isaudit is null) and (zl.flag_isopen = '2' or zl.flag_isopen is null) ");
		sb.append(" and zl.id not in(SELECT PR.ID FROM PE_RESOURCE PR JOIN INTERACTION_TEACHCLASS_INFO ITI ON ITI.FK_ZILIAO = PR.ID AND ITI.TYPE != 'JSJJ' JOIN PE_BZZ_TCH_COURSE PBTC ON ITI.TEACHCLASS_ID = PBTC.ID");
		sb.append(" JOIN ENUM_CONST EC ON PBTC.FLAG_COURSEAREA = EC.ID AND EC.CODE = '0')");
		if("0".equals(us.getSsoUser().getPePriRole().getId()) && "V".equals(entype)) {
			sb.append(" AND (ZL.SHOWUSERS LIKE '%jgstudent%' OR ZL.SHOWUSERS IS NULL)");
		} else {
			sb.append(" AND (ZL.SHOWUSERS LIKE '%jtstudent%' OR ZL.SHOWUSERS IS NULL)");
		}
		sb.append(" order by flag_top,replydate desc ");
		page = this.getGeneralDao().getByPageSQL(sb.toString(), pageSize, startIndex);
		return page;
	}
	
	public Page getMyResource(String name, String ziliaotype, String fabuunit, String ktimestart, String ktimeend, String kname,
			String tagIds, int pageSize, int startIndex) throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String entype = (String) ServletActionContext.getRequest().getSession().getAttribute("enterpriseType");
		Page page = null;
		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT * FROM (select zl.id,zl.name,zl.RESETYPE, to_char(zl.replyDate, 'yyyy-mm-dd') replyDate, zl.fabuunit, zl.resourcetags, zl.views, to_char(zl.CREATIONDATE, 'yyyy-mm-dd hh24:mi:ss') CREATIONDATE, zl.creater, ZL.RESOURCETAGIDS, e1.name as isaudit, e2.name as isopen from PE_RESOURCE zl join enum_const e1 on zl.FLAG_ISAUDIT=e1.id join enum_const e2 on zl.flag_isopen = e2.id ");
		sb.append(" where zl.creater = '" + us.getSsoUser().getId() + "'");
		sb.append(" union select PR.id,PR.name,PR.RESETYPE, to_char(PR.replyDate, 'yyyy-mm-dd') replyDate, PR.fabuunit, PR.resourcetags, PR.views, to_char(PR.CREATIONDATE, 'yyyy-mm-dd hh24:mi:ss') CREATIONDATE, PR.creater, PR.RESOURCETAGIDS, e1.name as isaudit, e2.name as isopen from PE_RESOURCE PR LEFT JOIN ENUM_CONST E1 ");
		sb.append(" ON PR.FLAG_ISAUDIT = E1.ID LEFT join enum_const e2 on pr.flag_isopen = e2.id inner join enum_const e3 on pr.flag_offline = e3.id and e3.code = '0'");
		sb.append(" join enum_const e4 on pr.flag_isvalid=e4.id  and e4.code='1'");
		sb.append(" WHERE PR.ID IN (SELECT RESOURCEID FROM SC_RESOURCE SR WHERE SR.USERID = '" + us.getId() + "' AND SR.TYPE = '1')");
		if("0".equals(us.getSsoUser().getPePriRole().getId()) && "V".equals(entype)) {
			sb.append(" AND (PR.SHOWUSERS LIKE '%jgstudent%' OR PR.SHOWUSERS IS NULL)");
		} else {
			sb.append(" AND (PR.SHOWUSERS LIKE '%jtstudent%' OR PR.SHOWUSERS IS NULL)");
		}
		sb.append(" )ZL WHERE 1 = 1");
		
		if (null != ziliaotype && !"".equals(ziliaotype))//
			sb.append(" AND zl.RESETYPE like '%" + ziliaotype + "%' ");
		if (null != name && !"".equals(name))// 
			sb.append(" AND zl.name LIKE '%" + name + "%' ");
		if (null != fabuunit && !"".equals(fabuunit))// 
			sb.append(" AND zl.fabuunit LIKE '%" + fabuunit + "%' ");
		if (null != ktimestart && !ktimestart.equals("")) {

			sb.append(" AND zl.replydate >= 	  to_date( '" + ktimestart + "' ,'yyyy-mm-dd') ");
		}
		if (null != ktimeend && !ktimeend.equals("")) {
			// 修改时间格式
			ktimeend = ktimeend;
			sb.append(" AND zl.replydate <=	  to_date( '" + ktimeend + "' ,'yyyy-mm-dd')");
		}
		if (null != kname && !kname.equals("")) {
			sb.append(" and zl.id in (  select zl.id  from PE_RESOURCE zl "
					+ "  inner join interaction_teachclass_info ii on ii.fk_ziliao = zl.id     and ii.type = 'KCZL' "
					+ "   inner join pe_bzz_tch_course pbtc on pbtc.id = ii.teachclass_id and pbtc.name like'%" + kname + "%') ");
		}
		if(null != tagIds && !"".equals(tagIds)) {
			sb.append(" and zl.resourcetagids like '%" + tagIds + "%'");
		}
		

		sb.append(" order by creationdate desc ");
		page = this.getGeneralDao().getByPageSQL(sb.toString(), pageSize, startIndex);
		return page;
	}

	// 公司内训学生端报名学习 Lzh

	public Page initInternal(String userId, String courseCode, String courseType, String courseName, String courseCategory,
			String courseItemType, int pageSize, int startIndex, String time, String teacher, String courseContent, String suggestren,
			String coursearea) throws EntityException {
		Page page = null;
		StringBuffer sb = new StringBuffer();
		sb
				.append(" select pbtc.id,pbtc.name , pbtc.code,EC1.Name xz,ec2.name yf,ec3.name dg,ec4.name nrxs,pbtc.TEACHER,pbtc.studydates  "
						+ "   from PE_BZZ_TCH_COURSE pbtc  "
						+ "  JOIN ENUM_CONST EC1 ON pbtc.FLAG_COURSETYPE = EC1.ID "
						+ "  JOIN ENUM_CONST EC2 ON pbtc.FLAG_COURSECATEGORY = EC2.ID "
						+ "  JOIN ENUM_CONST EC3 ON pbtc.FLAG_COURSE_ITEM_TYPE = EC3.ID "
						+ "  JOIN ENUM_CONST EC4 ON pbtc.FLAG_CONTENT_PROPERTY = EC4.ID "
						+ "  JOIN ENUM_CONST EC5  ON pbtc.FLAG_OFFLINE = EC5.ID "
						+ "  JOIN ENUM_CONST EC6 ON pbtc.FLAG_IS_EXAM = EC6.ID "
						+ "  JOIN ENUM_CONST EC7 ON PBTC.FLAG_ISVALID = EC7.ID and EC7.Code='1' "
						+ "  join enum_const ec9 on pbtc.FLAG_COURSEAREA=ec9.id and  ec9.code='3' "
						+ "  and   pbtc.id   in (select pbto.fk_course_id from PR_BZZ_TCH_OPENCOURSE pbto  "
						+ " ,STU_INTERNAL  pbtse where  pbtse.fk_tch_opencourse_id= pbto.id and pbtse.fk_stu_id in ( select ID FROM PE_BZZ_STUDENT WHERE    fk_sso_user_id= '"
						+ userId
						+ "') )"
						+ " and pbtc.id  not in (select pbto.fk_course_id from PR_BZZ_TCH_OPENCOURSE pbto  "
						+ " ,PR_BZZ_TCH_STU_ELECTIVE  pbtse where  pbtse.fk_tch_opencourse_id= pbto.id and pbtse.fk_stu_id in ( select ID FROM PE_BZZ_STUDENT WHERE    fk_sso_user_id= '"
						+ userId + "') )");
		if (null != courseCategory && !"".equals(courseCategory))// 业务分类
			sb.append(" AND pbtc.FLAG_COURSECATEGORY = '" + courseCategory + "' ");
		if (null != courseType && !"".equals(courseType))// 课程性质
			sb.append(" AND pbtc.FLAG_COURSETYPE= '" + courseType + "' ");
		if (null != courseCode && !"".equals(courseCode))// 课程编号
			sb.append(" AND UPPER(pbtc.CODE) LIKE '%" + courseCode.toUpperCase() + "%' ");
		if (null != courseName && !"".equals(courseName))// 课程名称
			sb.append(" AND UPPER(pbtc.NAME) LIKE '%" + courseName.toUpperCase() + "%' ");
		if (null != courseItemType && !"".equals(courseItemType))// 大纲分类
			sb.append(" AND pbtc.FLAG_COURSE_ITEM_TYPE = '" + courseItemType + "' ");
		if (null != courseContent && !"".equals(courseContent))// 内容属性
			sb.append(" AND pbtc.FLAG_CONTENT_PROPERTY = '" + courseContent + "' ");
		if (null != teacher && !"".equals(teacher))// 主讲人
			sb.append(" AND UPPER(pbtc.TEACHER) LIKE '%" + teacher.toUpperCase() + "%' ");
		page = this.getGeneralDao().getByPageSQL(sb.toString(), pageSize, startIndex);
		return page;
	}

	public Page initPeResourceList(String name, String code, String id, int pageSize, int startIndex) throws EntityException {
		Page page = null;
		StringBuffer sb = new StringBuffer();
		sb
				.append(" select pbtc.id,pbtc.name,pbtc.code from pe_bzz_tch_course pbtc left join enum_const ec1 on pbtc.flag_coursecategory=ec1.id "
						+ " inner join enum_const ec2 on pbtc.flag_isvalid=ec2.id and ec2.code='1'  inner join enum_const ec3 on pbtc.flag_offline=ec3.id and ec3.code='0' "
						+ " where pbtc.id  in (select ii.teachclass_id from interaction_teachclass_info ii inner join PE_RESOURCE zl on zl.id=ii.fk_ziliao where zl.id='"
						+ id + "')  and pbtc.FLAG_COURSEAREA <> 'Coursearea0'   ");
		if (null != name && !"".equals(name))// 
			sb.append(" AND pbtc.name LIKE '%" + name + "%' ");
		if (null != code && !"".equals(code))// 
			sb.append(" AND pbtc.code LIKE '%" + code + "%' ");
		page = this.getGeneralDao().getByPageSQL(sb.toString(), pageSize, startIndex);
		return page;
	}

	public List resourceList(String id) throws EntityException {
		// TODO Auto-generated method stub
		String sql1 = "select zl.name,zl.RESETYPE ,zl.DESCRIBE,zl.fabuunit,nvl(to_char(zl.REPLYDATE, 'yyyy-MM-dd') ,'-')replydate from PE_RESOURCE zl inner join enum_const e1 on zl.flag_isvalid=e1.id "
				+ " where zl.id='" + id + "'";
		List list = this.getGeneralDao().getBySQL(sql1);
		if (list != null && list.size() > 0) {
			// ServletActionContext.getRequest().setAttribute("list", list);
		}
		// ServletActionContext.getRequest().setAttribute("filelinkList",
		// filelinkb);

		return list;
	}

	public List resourceListc(String id) throws EntityException {
		// TODO Auto-generated method stub
		String resourcec = "select *\n" + "  from (select a.*, rownum rownum_\n" + "          from (SELECT *\n"
				+ "                  FROM (select id, name,  RESETYPE, fabuunit,nvl(to_char(REPLYDATE, 'yyyy-MM-dd') ,'-')replydate\n"
				+ "                          from pe_resource pr\n" + "                         where pr.id in (\n" + "\n"
				+ "                                         select iti.fk_ziliao\n"
				+ "                                           from interaction_teachclass_info iti\n"
				+ "                                          where iti.teachclass_id in\n"
				+ "                                                (select pbtc.id\n"
				+ "                                                   from pe_bzz_tch_course pbtc\n"
				+ "                                                  inner join enum_const ec1 on pbtc.flag_coursecategory =\n"
				+ "                                                                               ec1.id\n"
				+ "                                                  inner join enum_const ec2 on pbtc.flag_isvalid =\n"
				+ "                                                                               ec2.id\n"
				+ "                                                                           and ec2.code = '1'\n"
				+ "                                                  inner join enum_const ec3 on pbtc.flag_offline =\n"
				+ "                                                                               ec3.id\n"
				+ "                                                                           and ec3.code = '0'\n"
				+ "                                                  where pbtc.id in\n"
				+ "                                                        (select ii.teachclass_id\n"
				+ "                                                           from interaction_teachclass_info ii\n"
				+ "                                                          inner join PE_RESOURCE zl on zl.id =\n"
				+ "                                                                                       ii.fk_ziliao\n"
				+ "                                                          where zl.id =\n"
				+ "                                                                '" + id + "')\n"
				+ "                                                    and pbtc.FLAG_COURSEAREA <>\n"
				+ "                                                        'Coursearea0')) and pr.id<> '" + id
				+ "'  and pr.flag_isvalid='2' and pr.flag_offline='3' )\n" + // pr.flag_isvalid='2'[表示资料有效]
				// and
				// pr.flag_offline='3'[表示资料未下线]
				"\n" + "                ) a\n" + "         where rownum <= 5) b\n" + " where rownum_ > 0";
		List courseZl = this.getGeneralDao().getBySQL(resourcec);
		/*
		 * if(courseZl!=null && courseZl.size()>0){
		 * //ServletActionContext.getRequest().setAttribute("courseZl",
		 * courseZl); }
		 */
		return courseZl;
	}

	public List resourcefilelink(String id) throws EntityException {
		// TODO Auto-generated method stub
		String filelink = " select zl.FILE_LINK  from PE_RESOURCE zl " + " where zl.id='" + id + "' and zl.FILE_LINK is not null ";
		List filelinkb = new ArrayList();
		List filelinkList = this.getGeneralDao().getBySQL(filelink);
		if (null == filelinkList || filelinkList.size() < 1) {

		} else {
			String fklint = filelinkList.get(0).toString();
			String[] file = fklint.split(",");
			for (int i = 0; i < file.length; i++) {
				List filelinka = new ArrayList();
				String link = file[i];
				String fileName = file[i].substring(link.lastIndexOf("/") + 1);
				filelinka.add(link);
				filelinka.add(fileName);
				filelinkb.add(filelinka);
			}
		}
		return filelinkb;
	}

	/**
	 * 问题及建议
	 */
	public Page initQuestionAdvice(String topic, String type, int pageSize, int startIndex) throws Exception {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql = "SELECT UI.ID, UI.TOPIC, EC.NAME NAME1, TO_CHAR(UI.WHODATE,'yyyy-MM-dd hh24:mi:ss'),TO_CHAR(UI.REPLYDATE,'yyyy-MM-dd hh24:mi:ss') FROM USER_ISSUE UI INNER JOIN ENUM_CONST EC ON UI.UITYPE = EC.ID WHERE UI.FK_SSO_USER_ID = '"
				+ us.getSsoUser().getId() + "'";
		if (topic != null && !"".equalsIgnoreCase(topic)) {
			sql += " AND UI.TOPIC LIKE '%" + topic.trim() + "%' ";
		}
		if (type != null && !"".equalsIgnoreCase(type)) {
			sql += " AND UI.UITYPE = '" + type.trim() + "' ";
		}
		sql += " ORDER BY UI.REPLYDATE DESC ";
		Page page = this.getGeneralDao().getByPageSQL(sql, pageSize, startIndex);
		return page;
	}

	public List showQuestionAdvice(String qid) throws Exception {
		String sql = "SELECT UI.TOPIC, EC.NAME NAME1, UI.PHONE, TO_CHAR(UI.WHODATE,'yyyy-MM-dd hh24:mi:ss'), UI.ISSUEDESCRIBE, UI.FILELINK FROM USER_ISSUE UI INNER JOIN ENUM_CONST EC ON UI.UITYPE = EC.ID WHERE UI.ID = '"
				+ qid + "'";
		List list = this.getGeneralDao().getBySQL(sql);
		return list;
	}

	public List showQuestionAdviceReplys(String qid) throws Exception {
		StringBuffer sb = new StringBuffer();
		// Lee 只能协会管理员回复？
		// sb.append(" SELECT UR.REPLY, PBS.TRUE_NAME,
		// TO_CHAR(UR.REPLYDATE,'yyyy-MM-dd hh24:mi:ss') REPLYDATE ");
		// sb.append(" FROM USER_REPLY UR ");
		// sb.append(" INNER JOIN PE_BZZ_STUDENT PBS ");
		// sb.append(" ON UR.FK_SSO_USER_ID = PBS.FK_SSO_USER_ID ");
		// sb.append(" AND UR.FK_ISSUE_ID = '" + qid + "' ");
		// sb.append(" UNION ");
		// sb.append(" SELECT UR.REPLY, PBS.NAME,
		// TO_CHAR(UR.REPLYDATE,'yyyy-MM-dd hh24:mi:ss') REPLYDATE ");
		// sb.append(" FROM USER_REPLY UR ");
		// sb.append(" INNER JOIN PE_ENTERPRISE_MANAGER PBS ");
		// sb.append(" ON UR.FK_SSO_USER_ID = PBS.FK_SSO_USER_ID ");
		// sb.append(" AND UR.FK_ISSUE_ID = '" + qid + "' ");
		// sb.append(" UNION ");
		sb.append(" SELECT UR.REPLY, ");
		sb.append("        PPR.NAME TRUE_NAME, ");
		sb.append("        TO_CHAR(UR.REPLYDATE, 'yyyy-MM-dd hh24:mi:ss') REPLYDATE ");
		sb.append("   FROM USER_REPLY UR ");
		sb.append("  INNER JOIN PE_MANAGER PBS ");
		sb.append("     ON UR.FK_SSO_USER_ID = PBS.FK_SSO_USER_ID ");
		sb.append("    AND UR.FK_ISSUE_ID = '" + qid + "' ");
		sb.append("  INNER JOIN SSO_USER SU ");
		sb.append("     ON PBS.FK_SSO_USER_ID = SU.ID ");
		sb.append("  INNER JOIN PE_PRI_ROLE PPR ");
		sb.append("     ON SU.FK_ROLE_ID = PPR.ID ");
		sb.append("  ORDER BY REPLYDATE DESC ");
		List list = this.getGeneralDao().getBySQL(sb.toString());
		return list;
	}

	/**
	 * 根据namespace加载码值
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<EnumConst> initEnumConst(String namespace) throws Exception {
		DetachedCriteria dcCategory = DetachedCriteria.forClass(EnumConst.class);
		dcCategory.add(Restrictions.eq("namespace", namespace));
		List<EnumConst> courseCategoryList = this.generalDao.getList(dcCategory);
		return courseCategoryList;
	}
	/**
	 * 选课单记录保存 shopping_course 表中
	 * */
	public void addHoppingCourse(String stuId ,String opencourseId){
		String sql =" insert into shopping_course values ('"+UUID.randomUUID()+ "','"+ opencourseId +"',sysdate, '"+ stuId +"' ,'0' ) ";
		this.getGeneralDao().executeBySQL(sql);
	}
	public void  updateShoppingCourse (String stuId ){
		String opids ="";
		List shoppingIdList =null;
		if (ServletActionContext.getRequest().getSession().getAttribute(
				"shoppingIdList") != null) {
			shoppingIdList =(List) ServletActionContext.getRequest().getSession().getAttribute("shoppingIdList");
				for (int i = 0; i < shoppingIdList.size(); i++) {
					opids += "'" + shoppingIdList.get(i) + "',";
				}
			
			if (opids.length() > 0) {
				opids = opids.substring(0, opids.length() - 1);
			}
			String shoppingSql = " update shopping_course sc set sc.status = '1' where sc.fk_opencourse_id in ("+ opids +") and sc.fk_stu_id ='"+stuId+"'" ;
				this.getGeneralDao().executeBySQL(shoppingSql);
				System.out.print( "-------------"+ shoppingSql);
		}
	}
}
