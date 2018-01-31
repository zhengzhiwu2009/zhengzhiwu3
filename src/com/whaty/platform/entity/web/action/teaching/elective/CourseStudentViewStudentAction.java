package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class CourseStudentViewStudentAction extends MyBaseAction {

	private String id;
	private PeBzzTchCourse peBzzTchCourse;
	private List list;

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public PeBzzTchCourse getPeBzzTchCourse() {
		return peBzzTchCourse;
	}

	public void setPeBzzTchCourse(PeBzzTchCourse peBzzTchCourse) {
		this.peBzzTchCourse = peBzzTchCourse;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("学员信息");
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学员姓名"), "true_name", true);
		this.getGridConfig().addColumn(this.getText("身份证号"), "card_no", false);
		this.getGridConfig().addColumn(this.getText("工作部门"), "department", false);
		this.getGridConfig().addColumn(this.getText("职务"), "position", false);
		this.getGridConfig().addColumn(this.getText("支付时间"), "payment_date", false);
		this.getGridConfig().addColumn(this.getText("开始学习时间"), "get_date", false);
		// this.getGridConfig().addColumn(this.getText("结束学习时间"), "end_date",
		// false);
		this.getGridConfig().addColumn(this.getText("学习结果"), "learn_status", false);
		this.getGridConfig().addColumn(this.getText("考试结果"), "exam_status", false);
		// Lee start 2014年1月21日
		this.getGridConfig().addColumn(this.getText("获取学时时间"), "cDate", false);
		this.getGridConfig().addColumn(this.getText("获取学时开始时间"), "csDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("获取学时结束时间"), "ceDate", true, false, false, "");
		// Lee end
		this.getGridConfig().addMenuScript(this.getText("返回"), "{history.back();}");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/courseStudentViewStudent";
	}

	/**
	 * 课程详情
	 * 
	 * @author linjie
	 * @return
	 */
	public void getPeBzzTchCourseInfo() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");
		dc.createCriteria("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory");
		dc.add(Restrictions.eq("id", this.getId()));
		try {
			peBzzTchCourse = (PeBzzTchCourse) this.getGeneralService().getList(dc).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 重写框架方法：课程信息（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		// this.getPeBzzTchCourseInfo();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		EnumConst ec = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagIsExam", "1");
		StringBuffer sql = new StringBuffer();

		// Lee start 2014年1月3日
		// 处理时间的查询参数
		ActionContext context = ActionContext.getContext();
		String csDate = "";
		String ceDate = "";
		if (context.getParameters() != null) {
			Map params = this.getParamsMap();
			if (params != null) {
				if (params.get("search__csDate") != null) {
					String[] completeDate = (String[]) params.get("search__csDate");
					if (completeDate.length == 1 && !StringUtils.defaultString(completeDate[0]).equals("")) {
						csDate = completeDate[0];
						params.remove("search__csDate");
					}
				}
				if (params.get("search__ceDate") != null) {
					String[] completeDate = (String[]) params.get("search__ceDate");
					if (completeDate.length == 1 && !StringUtils.defaultString(completeDate[0]).equals("")) {
						ceDate = completeDate[0];
						params.remove("search__ceDate");
					}
				}
				context.setParameters(params);
			}
		}
		// Lee end
		if (us.getUserLoginType().equals("3")) {// 协会管理员
			sql.append(" select * from ( select stu.id,stu.true_name,stu.card_no, stu.department, stu.position, to_char(o.payment_date,'yyyy-mm-dd HH24:mi:ss') as payment_date, to_char(tcs.get_date,'yyyy-mm-dd HH24:mi:ss') as get_date,  \n");
			sql.append("    ( case when tcs.learn_status = 'COMPLETED' then '已完成学习' when tcs.learn_status = 'UNCOMPLETE' then '未开始学习'    \n");
			sql.append("      when tcs.learn_status = 'INCOMPLETE' then '正在学习' when 1<>1 then 'a' else  '未开始学习' end ) as learn_status,  \n");
			sql.append("     ( case  when c.flag_is_exam <> '" + ec.getId() + "' then '不考试' ");
			sql.append("     		 when c.flag_is_exam = '" + ec.getId() + "' and t.SCORE_EXAM >= nvl(c.PASSROLE,0) then '已通过考试'   \n");
			sql.append("             when c.flag_is_exam = '" + ec.getId() + "' and t.SCORE_EXAM < nvl(c.PASSROLE,0)  then '未通过考试' else '未考试' end ) as exam_status     \n");
			// Lee start 2014年1月3日
			sql.append(" , to_char(completed_time,'yyyy-MM-dd hh24:mi') as cDate, '' as csDate, '' as ceDate ");
			// Lee end
			sql.append(" from ( select fk_tch_opencourse_id,fk_stu_id,fk_training_id, fk_order_id,SCORE_EXAM,completed_time from pr_bzz_tch_stu_elective  where exists (                                   \n");
			sql.append("              select id,fk_course_id from pr_bzz_tch_opencourse where fk_course_id = '" + this.getId()
					+ "' and id = FK_TCH_OPENCOURSE_ID                               \n");
			sql.append("  ) )  t    \n");
			sql.append(" inner join ( select id,fk_course_id from pr_bzz_tch_opencourse where fk_course_id = '" + this.getId()
					+ "' )  op on t.fk_tch_opencourse_id = op.id                     \n");
			sql.append(" inner join ( select id,PASSROLE,flag_is_exam from pe_bzz_tch_course where id = '" + this.getId()
					+ "' ) c on c.id = op.fk_course_id                                    \n");
			sql.append(" inner join ( select id,position,department,card_no,true_name from pe_bzz_student student where exists ( select fk_stu_id from pr_bzz_tch_stu_elective  where exists (              \n");
			sql.append("              select id,fk_course_id from pr_bzz_tch_opencourse where fk_course_id = '" + this.getId()
					+ "' and id = FK_TCH_OPENCOURSE_ID ) and fk_stu_id = student.id  \n");
			sql.append("  )) stu on stu.id = t.fk_stu_id    \n");
			sql.append(" inner join ( select id,get_date,learn_status,complete_date from training_course_student  where exists ( \n");
			sql.append("              select id,fk_course_id from pr_bzz_tch_opencourse where fk_course_id = '" + this.getId()
					+ "' and id = course_id                                          \n");
			sql.append("  ) ) tcs on tcs.id = t.fk_training_id     \n");
			sql.append(" left join pe_bzz_order_info o on t.fk_order_id = o.id  \n) \n");
		} else {
			String enterpriseId = us.getPriEnterprises().get(0).getId();
			StringBuffer sqlStudent = new StringBuffer("");
			if (us.getUserLoginType().equals("2")) {// 一级集体管理员
				sqlStudent
						.append(" and exists ( select id from (                                                                                                                                                                           \n");
				sqlStudent
						.append("            select pe.id                                                                                                                                                                                 \n");
				sqlStudent
						.append("            from pe_enterprise pe,                                                                                                                                                                       \n");
				sqlStudent
						.append("                 pe_enterprise pe_parent                                                                                                                                                                 \n");
				sqlStudent
						.append("            where pe_parent.id = pe.fk_parent_id                                                                                                                                                         \n");
				sqlStudent
						.append("            and pe_parent.id = '"
								+ enterpriseId
								+ "'                                                                                                                                                        \n");
				sqlStudent
						.append("            union                                                                                                                                                                                        \n");
				sqlStudent
						.append("            select pe.id                                                                                                                                                                                 \n");
				sqlStudent
						.append("              from pe_enterprise pe                                                                                                                                                                      \n");
				sqlStudent
						.append("             where pe.id = '"
								+ enterpriseId
								+ "' ) where id = pbs.fk_enterprise_id )                                                                                                                        \n");
			} else if (us.getUserLoginType().equals("4")) {// 二级集体管理员
				sqlStudent.append(" and exists ( select pe.id from pe_enterprise pe where pe.id = '" + enterpriseId + "' and pe.id = pbs.fk_enterprise_id ) \n");
			}
			sql.append(" select * from (select pbs.id,pbs.true_name,pbs.card_no, pbs.department, pbs.position, to_char(pboi.payment_date,'yyyy-mm-dd HH24:mi:ss') as payment_date, to_char(tcs.get_date,'yyyy-mm-dd HH24:mi:ss') as get_date,   \n");
			sql.append("   ( case when tcs.learn_status = 'COMPLETED' then '已完成学习' when tcs.learn_status = 'UNCOMPLETE' then '未开始学习'                                                                                           \n");
			sql.append("     when tcs.learn_status = 'INCOMPLETE' then '正在学习' when 1<>1 then 'a' else  '未开始学习' end ) as learn_status,                                                                                         \n");
			sql.append("    ( case when pbtc.flag_is_exam = '" + ec.getId() + "' and pbtse.SCORE_EXAM >= nvl(pbtc.PASSROLE,0) then '已通过考试'  \n");
			sql.append("     when pbtc.flag_is_exam = '" + ec.getId() + "' and pbtse.SCORE_EXAM < nvl(pbtc.PASSROLE,0) then '未通过考试' when 1<>1 then 'a'"
			// Lee start 2014年2月26日
					+ "WHEN pbtc.flag_is_exam = '40288acf3ae01103013ae0130d030002' THEN '不考试' "
					// Lee end
					+ "else '未考试' end ) as exam_status  \n");
			// Lee start 2014年1月3日
			sql.append(" , to_char(pbtse.completed_time,'yyyy-MM-dd hh24:mi') as cDate, '' as csDate, '' as ceDate ");
			// Lee end
			sql.append(" from                                                                                                                                                                                                    \n");
			sql.append(" pe_bzz_student pbs,                                                                                                                                                                                     \n");
			sql.append(" training_course_student tcs,                                                                                                                                                                            \n");
			sql.append(" pr_bzz_tch_stu_elective pbtse,                                                                                                                                                                          \n");
			sql.append(" pr_bzz_tch_opencourse pbto,                                                                                                                                                                             \n");
			sql.append(" pe_bzz_order_info pboi,                                                                                                                                                                                 \n");
			sql.append(" pe_bzz_tch_course pbtc,                                                                                                                                                                                 \n");
			sql.append(" sso_user sso                                                                                                                                                                                            \n");
			sql.append(" where pbtc.id = '" + this.getId()
					+ "'                                                                                                                                                      \n");
			sql.append(" and pbtc.id = pbto.fk_course_id                                                                                                                                                                         \n");
			sql.append(" and pbto.id = pbtse.fk_tch_opencourse_id                                                                                                                                                                \n");
			sql.append(" and pbtse.fk_training_id = tcs.id                                                                                                                                                                       \n");
			sql.append(" and pboi.id(+) = pbtse.fk_order_id                                                                                                                                                                      \n");
			sql.append(" and pbs.id = pbtse.fk_stu_id                                                                                                                                                                            \n");
			sql.append(" and sso.id = tcs.student_id                                                                                                                                                                             \n");
			sql.append(" and sso.id = pbs.fk_sso_user_id                                                                                                                                                                        \n");
			sql.append(sqlStudent.toString() + ")");
		}
		// Lee start 2014年1月21日
		if (!"".equals(csDate) && "".equals(ceDate)) {
			sql.append(" where to_date(cDate,'yyyy-MM-dd hh24:mi') >= to_date('" + csDate + " 00:00','yyyy-MM-dd hh24:mi')");
		} else if (!"".equals(ceDate) && "".equals(csDate)) {
			sql.append(" where to_date(cDate,'yyyy-MM-dd hh24:mi') <= to_date('" + ceDate + " 23:59','yyyy-MM-dd hh24:mi')");
		} else if (!"".equals(csDate) && !"".equals(ceDate)) {
			sql.append(" where to_date(cDate,'yyyy-MM-dd hh24:mi') between to_date('" + csDate + " 00:00','yyyy-MM-dd hh24:mi') and to_date('" + ceDate
					+ " 23:59','yyyy-MM-dd hh24:mi')");
		}
		// Lee end

		// Lee start 原版
		// this.setSqlCondition(sql);
		// Lee end
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
}
