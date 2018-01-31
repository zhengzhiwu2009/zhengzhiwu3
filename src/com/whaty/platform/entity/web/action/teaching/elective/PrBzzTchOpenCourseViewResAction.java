package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzAssess;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

@SuppressWarnings("unchecked")
public class PrBzzTchOpenCourseViewResAction extends MyBaseAction {

	private String bid; // 学期id
	private String cid; // 课程id
	private String tagShow; // 操作类型
							// courseSelect：显未课程；showStuPG：显示参加评估学员；showStuNPG：显示未参加评估学员

	private Map userCourseMap;

	private String excelName = "";

	private List<PeBzzStudent> stuResultList;

	@Override
	public void setEntityClass() {
		this.entityClass = PrBzzTchOpencourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/prBzzTchOpenCourseViewRes";

	}

	public PrBzzTchOpencourse getBean() {
		return (PrBzzTchOpencourse) super.superGetBean();
	}

	public void setBean(PrBzzTchOpencourse opencourse) {
		super.superSetBean(opencourse);
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);

		if (this.getTagShow() == null
				|| this.getTagShow().equals("courseSelect")) { // 学生列表
			this.initCourseGrid();
		} else if (this.getTagShow().startsWith("showStu")) { // 课程列表
			this.initStuGrid();
		}

		this.getGridConfig().addMenuScript(this.getText("返回"),
				"{history.back();}");
	}

	/**
	 * 初始化课程列表
	 */
	private void initCourseGrid() {
		this.entityClass = PrBzzTchOpencourse.class;

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"),
				"peBzzTchCourse.name");
		this.getGridConfig().addColumn(this.getText("课程代码"),
				"peBzzTchCourse.code");
		this.getGridConfig().addColumn(this.getText("学时"),
				"peBzzTchCourse.time", false, true, true, null);
		this.getGridConfig().addColumn(this.getText("课程性质"),
				"enumConstByFlagCourseType.name");
		this.getGridConfig().addColumn(this.getText("授课教师"),
				"peBzzTchCourse.teacher", false, true, true, "");
		this.getGridConfig().addColumn(this.getText("教师简介"),
				"peBzzTchCourse.teacherNote", false, true, true, "");

		this
				.getGridConfig()
				.addRenderScript(
						this.getText("已完成评估学员"),
						"{return '<a href=prBzzTchOpenCourseViewRes.action?cid='+record.data['id']+'&bid="
								+ this.getBid()
								+ "&tagShow=showStuPG>查看</a>';}", "");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("达到要求未参加评估学员"),
						"{return '<a href=prBzzTchOpenCourseViewRes.action?cid='+record.data['id']+'&bid="
								+ this.getBid()
								+ "&tagShow=showStuNPG>查看</a>';}", "");

		this
				.getGridConfig()
				.addMenuScript(
						this.getText("导出全部完成加评估学员"),
						"{window.navigate('/entity/teaching/prBzzTchOpenCourseViewRes_exportPGStu.action?bid="
								+ this.getBid() + "');}");

		this
				.getGridConfig()
				.addMenuScript(
						this.getText("导出全部达到要求达到要求未参加评估学员"),
						"{window.navigate('/entity/teaching/prBzzTchOpenCourseViewRes_exportNPGStu.action?bid="
								+ this.getBid() + "');}");
	}

	/**
	 * 初始化学生列表
	 */
	private void initStuGrid() {
		this.entityClass = PeBzzStudent.class;

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("性别"), "gender");
		this.getGridConfig().addColumn(this.getText("民族"), "folk");
		this.getGridConfig().addColumn(this.getText("学历"), "education");
		this.getGridConfig().addColumn(this.getText("出生日期"), "birthdayDate");
		this.getGridConfig().addColumn(this.getText("办公电话"), "phone");
		this.getGridConfig().addColumn(this.getText("移动电话"), "mobilePhone");
		this.getGridConfig().addColumn(this.getText("电子邮件"), "email");

		this.getGridConfig().addColumn(this.getText("课程名称"), "cname");

		this.getGridConfig().addColumn(this.getText("所在企业"),
				"peEnterprise.name");
		this.getGridConfig().addColumn(this.getText("所在集团"),
				"peEnterprise.peEnterprise.name");

	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria prdc = null;

		// 查询课程
		if (this.getTagShow() == null
				|| this.getTagShow().equals("courseSelect")) {
			this.entityClass = PrBzzTchOpencourse.class;

			prdc = getCourseDc();

			// 查询已参加评估学生和达到要求未参加评估学生
		} else if (this.getTagShow().equals("showStuPG")
				|| this.getTagShow().equals("showStuNPG")) {

			this.entityClass = PeBzzStudent.class;

			prdc = getStuDc();

		} else {
			prdc = super.initDetachedCriteria();
		}
		return prdc;
	}

	/**
	 * 查询课程DC
	 * 
	 * @return
	 */
	private DetachedCriteria getCourseDc() {
		DetachedCriteria prdc = DetachedCriteria
				.forClass(PrBzzTchOpencourse.class);
		prdc.createCriteria("peBzzTchCourse");
		prdc.createCriteria("enumConstByFlagCourseType");

		prdc.add(Restrictions.eq("peBzzBatch.id", this.getBid()));
		return prdc;
	}

	/**
	 * 查询学生,不是真正的查询，真正的查询在list() 和 abstractList()
	 * 
	 * @return
	 */
	private DetachedCriteria getStuDc() {
		// 获得管理员的企业管理范围
		// List<String> priIdList = getPriIds();

		// 用list 和 abstractList 查询数据
		DetachedCriteria prdc = null;
		prdc = DetachedCriteria.forClass(PeBzzStudent.class);
		prdc.add(Expression.sql("1=2"));

		return prdc;
	}

	@Override
	public Page list() {
		// 查询课程
		Page page = null;
		if (this.getTagShow() == null
				|| this.getTagShow().equals("courseSelect")) {
			this.entityClass = PrBzzTchOpencourse.class;

			page = super.list();

		} else {// 查询评估学生

			this.entityClass = PeBzzStudent.class;

			page = getList();
		}

		return page;
	}

	public Page getList() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		String tc = "";
		if (!us.getUserLoginType().equals("3")) {
			List<PeEnterprise> priList = us.getPriEnterprises();

			for (PeEnterprise e : priList) {
				
				tc += "'" + e.getId() + "',";
			}

			// System.out.println("----------tc: " + tc);
		}
		if (tc.length() == 0) {
			tc = "'noPriEnterprise'";
		}
		tc = "(" + tc.substring(0, tc.length() - 1) + ")";

		Page page = null;
		String sql = "";

		if (this.getTagShow().equals("showStuPG")) {

			sql = this.getPGSql(this.getCid(), this.getBid(), tc);

			// 查询达到要求未参加评估学生
		} else if (this.getTagShow().equals("showStuNPG")) {

			sql = this.getNPGSql(this.getCid(), this.getBid(), tc);
		}

		//System.out.println("~~sql :" + sql);
		try {
			StringBuffer sql_temp = new StringBuffer(sql);
			// this.setSort("code");
			this.setSort("regNo");
			this.setSqlCondition(sql_temp);
			page = this.getGeneralService().getByPageSQL(sql_temp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}

	private String getPGSql(String courseId, String batchId, String tc) {
		String sql_con1 = "";
		String sql_con2 = "";

		if (courseId != null) {
			sql_con1 = "      and ass.fk_course_id='" + courseId + "' \n";
		}
		if (tc != null) {
			sql_con2 = "      and bs.fk_enterprise_id in " + tc + " \n ";
		}

		String sql = "select * from ( \n"
				+ "select bs.id as id, bs.true_name as trueName, bs.reg_no as regNo, bs.gender, bs.folk, bs.education, bs.birthday as birthdayDate, \n"
				+ "       bs.phone,bs.mobile_phone as mobilePhone, bs.email, \n"
				+ "       tc.name as cname, e2.name as ent2Name, e1.name as ent1Name \n"
				+ "from pe_bzz_assess ass,pr_bzz_tch_opencourse oc, pe_bzz_tch_course tc,pe_bzz_student bs, pe_enterprise e2 left outer join pe_enterprise e1 on e2.fk_parent_id=e1.id \n"
				+ "where ass.fk_course_id=oc.id and oc.fk_course_id=tc.id and bs.fk_sso_user_id=ass.fk_student_id and bs.fk_enterprise_id=e2.id \n"
				+ sql_con1 + "      and oc.fk_batch_id='" + this.getBid()
				+ "' \n" + sql_con2 + "order by tc.code)";

		return sql;
	}

	private String getNPGSql(String courseId, String batchId, String tc) {
		String sql_con1 = "";
		String sql_con2 = "";
		String sql_con3 = "";

		if (courseId != null) {
			sql_con1 = "         and tcs.course_id = '" + courseId + "' \n";
			sql_con2 = "         and ass.fk_course_id='" + courseId + "' \n";
		}
		if (tc != null) {
			sql_con3 = "      and bs.fk_enterprise_id in " + tc + " \n ";
		}

		String sql = "select * from ("
				+ "select bs.id as id, bs.true_name as trueName, bs.reg_no as regNo, bs.gender, bs.folk, bs.education, bs.birthday as birthdayDate, \n"
				+ "       bs.phone,bs.mobile_phone as mobilePhone, bs.email,  \n"
				+ "       tc.name as cname, e2.name as ent2Name, e1.name as ent1Name \n"
				+ "from pe_bzz_student bs,  pe_bzz_tch_course tc, \n"
				+ "     pe_enterprise e2 left outer join pe_enterprise e1 on e2.fk_parent_id=e1.id, ( \n"
				+ "   select distinct tcs.student_id,tcs.course_id,oc.fk_course_id \n"
				+ "   from training_course_student tcs,pr_bzz_tch_opencourse oc \n"
				+ "   where tcs.course_id=oc.id and tcs.percent>50 \n"
				+ sql_con1
				+ "         and oc.fk_batch_id='"
				+ batchId
				+ "' \n"
				+ "   minus \n"
				+ "   select distinct ass.fk_student_id, ass.fk_course_id ,oc.fk_course_id \n"
				+ "   from pe_bzz_assess ass ,pr_bzz_tch_opencourse oc, pe_bzz_tch_course tc \n"
				+ "   where ass.fk_course_id=oc.id and oc.fk_course_id=tc.id \n"
				+ sql_con2
				+ "         and oc.fk_batch_id='"
				+ batchId
				+ "' \n"
				+ "      ) c \n"
				+ "where c.fk_course_id=tc.id and bs.fk_sso_user_id=c.student_id and bs.fk_enterprise_id=e2.id \n"
				+ sql_con3 + " order by tc.code)";

		return sql;
	}

	/**
	 * 获得当前用户的管理企业范围
	 * 
	 * @return
	 */
	private List<String> getPriIds() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		List<PeEnterprise> priEnt = us.getPriEnterprises();
		List<String> priIdList = new ArrayList<String>();
		for (PeEnterprise e : priEnt) {
			priIdList.add(e.getId());
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeEnterprise.class);
		dc.setProjection(Property.forName("id"));
		dc.add(Restrictions.or(Restrictions.in("id", priIdList), Restrictions
				.in("peEnterprise.id", priIdList)));
		try {
			priIdList = this.getGeneralService().getList(dc);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		return priIdList;
	}

	/**
	 * action 导出全部已参加评估学生
	 * 
	 * @return
	 */
	public String exportPGStu() {
		this.excelName = "";
		this.excelName += this.getBatchName();
		this.excelName += " 已参加评估学生";
		return "prBzzTchOpenCourseExportStu";
	}

	/**
	 * action 导出全部达到要求未参加评估学生
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportNPGStu() {
		this.excelName = "";
		excelName = getBatchName();
		
		this.excelName += this.getBatchName();
		this.excelName += " 达到要求未参加评估学生";
		
		return "prBzzTchNOpenCourseExportStu";
	}

	private String getBatchName() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzBatch.class);

		dc.add(Restrictions.eq("id", this.getBid()));
		List<PeBzzBatch> batch = null;
		try {
			batch = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return batch.get(0).getName();
	}

	public String getTagShow() {
		return tagShow;
	}

	public void setTagShow(String tagShow) {
		this.tagShow = tagShow;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public List<PeBzzStudent> getStuResultList() {
		return stuResultList;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public Map getUserCourseMap() {
		return userCourseMap;
	}

	public void setUserCourseMap(Map userCourseMap) {
		this.userCourseMap = userCourseMap;
	}
}
