package com.whaty.platform.entity.web.action.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzExamBatch;
import com.whaty.platform.entity.bean.PeBzzExamLate;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeBzzExamLateAction extends MyBaseAction<PeBzzExamLate> {

	private String id;
	private String note;

	@Override
	public void initGrid() {

		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle("缓考信息");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		// this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("学号"),
				"peBzzStudent.regNo", true, false, true, "TextField");
		this.getGridConfig().addColumn(this.getText("姓名"),
				"peBzzStudent.trueName", true, true, true, "TextField", false,
				50);
		this.getGridConfig().addColumn(this.getText("移动电话"),
				"peBzzStudent.mobilePhone", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("学期"),
				"peBzzStudent.peBzzBatch.name", true, false, true, "TextField",
				false, 50);
		ColumnConfig c_name1 = new ColumnConfig(this.getText("所在企业"),
				"peBzzStudent.peEnterprise.name");
		c_name1
				.setComboSQL("select t.id,t.name as p_name from pe_enterprise t where t.fk_parent_id is not null");
		this.getGridConfig().addColumn(c_name1);
		this.getGridConfig()
				.addColumn(this.getText("考试批次"), "peBzzExamBatch.name", true,
						true, true, "TextField", false, 50);
		this.getGridConfig()
				.addColumn(this.getText("申请时间"), "applyDate", false);
		this.getGridConfig()
				.addColumn(this.getText("初审时间"), "firstDate", false);
		this.getGridConfig()
				.addColumn(this.getText("终审时间"), "finalDate", false);
		this.getGridConfig().addColumn(this.getText("状态"),
				"enumConstByFlagExamLateStatus.name", true, false, true,
				"TextField");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("操作"),
						"{return '<a href=/entity/manager/exam/reject_examlate.jsp?id='+record.data['id']+'&type=enterprise>驳回</a>';}",
						"");
		// this
		// .getGridConfig()
		// .addRenderScript(
		// this.getText("学员人数"),
		// "{return '<a
		// href=peDetail.action?id='+record.data['id']+'&type=allnum><font
		// color=#0000ff ><u>'+record.data['stunum']+'</u></font></a>';}",
		// "");
		if (us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuFunction("终审", "checked", "1");
			this.getGridConfig().addMenuFunction("取消终审", "nochecked", "1");
			this.getGridConfig().addMenuFunction("直接终审", "checkedFinal", "1");
		} else if (us.getUserLoginType().equals("2")) {
			this.getGridConfig().addMenuFunction("初审", "checked", "1");
			this.getGridConfig().addMenuFunction("取消初审", "nochecked", "1");
		}

	}

	public String reject() {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(PeBzzExamLate.class);
		detachedCriteria.add(Restrictions.eq("id", id));
		try {
			PeBzzExamLate examLate = this.getGeneralService().getExamLate(
					detachedCriteria);
			EnumConst getEnumConstByFlagExamLateStatus = this
					.getMyListService().getEnumConstByNamespaceCode(
							"FlagExamLateStatus", "3");
			examLate.setNote(note);
			examLate
					.setEnumConstByFlagExamLateStatus(getEnumConstByFlagExamLateStatus);
			this.getGeneralService().save(examLate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "reject";
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzExamLate.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/exam/peBzzExamLate";
	}

	public void setBean(PeBzzExamLate instance) {
		super.superSetBean(instance);
	}

	public PeBzzExamLate getBean() {
		return super.superGetBean();
	}

	// MenuFunction 方法

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public Map updateColumn() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Map map = new HashMap();
		// if (this.getIds() == null || this.getIds().split(",").length > 1) {
		// map.put("success", false);
		// map.put("info", this.getText("只能操作一条记录!"));
		// return map;
		// }
		if (this.getColumn().equals("checked")) {
			for (int i = 0; i < this.getIds().split(",").length; i++) {
				DetachedCriteria detachedCriteria = DetachedCriteria
						.forClass(PeBzzExamLate.class);
				detachedCriteria.add(Restrictions.eq("id", this.getIds().split(
						",")[i]));
				List<PeBzzExamLate> list = null;
				try {
					list = this.getGeneralService().getList(detachedCriteria);
				} catch (EntityException e1) {
					e1.printStackTrace();
				}

				for (PeBzzExamLate peBzzBatch : list) {
					if (peBzzBatch.getEnumConstByFlagExamLateStatus().getCode()
							.equals("1")
							&& us.getUserLoginType().equals("3")) {
						Date final_date = new Date();
						EnumConst getEnumConstByFlagExamLateStatus = this
								.getMyListService()
								.getEnumConstByNamespaceCode(
										"FlagExamLateStatus", "2");
						peBzzBatch
								.setEnumConstByFlagExamLateStatus(getEnumConstByFlagExamLateStatus);
						peBzzBatch.setFinalDate(final_date);
						DetachedCriteria dc = DetachedCriteria
								.forClass(PeBzzExamScore.class);
						try {
							this.getGeneralService().save(peBzzBatch);
						} catch (EntityException e) {
							e.printStackTrace();
						}
					} else if ((peBzzBatch.getEnumConstByFlagExamLateStatus()
							.getCode().equals("0") || peBzzBatch
							.getEnumConstByFlagExamLateStatus().getCode()
							.equals("3"))
							&& us.getUserLoginType().equals("2")) {
						DetachedCriteria dc = DetachedCriteria
								.forClass(PeBzzExamScore.class);
						// 该考试批次报名条件
						Long time = peBzzBatch.getPeBzzExamBatch().getTime();
						String homework = peBzzBatch.getPeBzzExamBatch()
								.getEnumConstByFlagExamConditionHomework()
								.getName();
						String test = peBzzBatch.getPeBzzExamBatch()
								.getEnumConstByFlagExamConditionTest()
								.getName();
						String evaluate = peBzzBatch.getPeBzzExamBatch()
								.getEnumConstByFlagExamConditionEvaluate()
								.getName();

						String timeflag = "1";
						String homeworkflag = "1";
						String testflag = "1";
						String evaluateflag = "1";

						String sqlevaluate;
						String sqlhomework;
						String sqltest;
						String sqlpaper;

						String sql_training = " training_course_student ts,";
						if (peBzzBatch.getPeBzzStudent().getPeBzzBatch()
								.getId().equals(
										"ff8080812824ae6f012824b0a89e0008")) {
							sql_training = "("
									+ "select s.id,s.course_id,s.percent,s.learn_status,s.student_id from training_course_student s ,pe_bzz_student bs,pr_bzz_tch_opencourse pbto where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
									+ peBzzBatch.getPeBzzStudent()
											.getPeBzzBatch().getId()
									+ "'\n"
									+ "and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and (s.course_id <> 'ff8080812910e7e601291150ddc70419' and s.course_id <>'ff8080812bf5c39a012bf6a1bab80820' and s.course_id <>'ff8080812910e7e601291150ddc70413' and s.course_id <>'ff8080812bf5c39a012bf6a1baba0821')"
									+ " and pbto.id=s.course_id and pbto.flag_course_type='402880f32200c249012200c780c40001' and pbto.fk_batch_id=bs.fk_batch_id "
									+ "union\n"
									+ "select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,"
									+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n"
									+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"
									+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n"
									+ "select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
									+ peBzzBatch.getPeBzzStudent()
											.getPeBzzBatch().getId()
									+ "' and s.course_id='ff8080812910e7e601291150ddc70419' )a,\n"
									+ "(select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s ,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
									+ peBzzBatch.getPeBzzStudent()
											.getPeBzzBatch().getId()
									+ "' and s.course_id='ff8080812bf5c39a012bf6a1bab80820')b\n"
									+ "where a.student_id=b.student_id\n"
									+ "union\n"
									+ "select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,"
									+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n"
									+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"
									+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n"
									+ "select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s ,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
									+ peBzzBatch.getPeBzzStudent()
											.getPeBzzBatch().getId()
									+ "' and s.course_id='ff8080812910e7e601291150ddc70413' )a,\n"
									+ "(select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
									+ peBzzBatch.getPeBzzStudent()
											.getPeBzzBatch().getId()
									+ "' and s.course_id='ff8080812bf5c39a012bf6a1baba0821')b\n"
									+ "where a.student_id=b.student_id"
									+ ") ts, ";
						}

						String sql_hw = "";
						String sql_test = "";
						if (peBzzBatch.getPeBzzStudent().getPeBzzBatch()
								.getId().equals(
										"ff8080812253f04f0122540a58000004")
								|| peBzzBatch
										.getPeBzzStudent()
										.getPeBzzBatch()
										.getId()
										.equals(
												"ff8080812824ae6f012824b0a89e0008")) {
							sql_hw = " and thi.batch_id is null ";
							sql_test = " and oci.fk_batch_id is null ";
						} else {
							sql_hw = " and thi.batch_id='"
									+ peBzzBatch.getPeBzzStudent()
											.getPeBzzBatch().getId() + "'";
							sql_test = " and oci.fk_batch_id='"
									+ peBzzBatch.getPeBzzStudent()
											.getPeBzzBatch().getId() + "' ";
						}

						// 该生的选课情况（基础课程）
						String sqlcourse = "select s.true_name,\n"
								+ "       s.reg_no,\n"
								+ "       s.mobile_phone,\n"
								+ "       s.phone,\n"
								+ "       e.name as ename,\n"
								+ "       per\n"
								+ "  from (select ps.id,\n"
								+ "               (nvl(sum(ce.time * (ts.percent / 100)), 0) / sum(ce.time)) * 100 as per\n"
								+ "          from sso_user                su,\n"
								+ sql_training
								+ "               pe_bzz_student          ps,\n"
								+ "               pr_bzz_tch_opencourse   co,\n"
								+ "               pe_bzz_tch_course       ce,\n"
								+ "               pe_enterprise           pe\n"
								+ "         where ps.fk_sso_user_id = su.id\n"
								+ "           and su.id = ts.student_id\n"
								+ "           and pe.id = ps.fk_enterprise_id\n"
								+ "           and co.id = ts.course_id\n"
								+ "           and co.fk_course_id = ce.id\n"
								+ "           and ps.fk_batch_id = co.fk_batch_id\n"
								+ "           and su.flag_isvalid = '2'\n"
								+ "           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n"
								+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
								+ "           and su.login_num > 0\n"
								+ "           and ps.id='"
								+ peBzzBatch.getPeBzzStudent().getId()
								+ "'\n"
								+ "         group by ps.id) a,\n"
								+ "       pe_bzz_student s,\n"
								+ "       pe_enterprise e\n"
								+ " where s.id = a.id\n"
								+ "   and e.id = s.fk_enterprise_id  and per<"
								+ time + "  \n" + " order by e.name, s.reg_no";
						List courseList = null;
						try {
							courseList = this.getGeneralService().getBySQL(
									sqlcourse);
						} catch (EntityException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (courseList == null || courseList.size() > 0) {
							timeflag = "0";
							// System.out.println("courseList.size()="+courseList.size());
							// System.out.println("timeflag="+timeflag);
						}
						if (homework.equals("是")) {
							sqlhomework = "select t_id from\n"
									+ "(select a.id,a.testpaper_id,b.testpaper_id as t_id from\n"
									+ "(select ps.id,thh.testpaper_id\n"
									+ "          from sso_user                su,\n"
									+ "               test_homeworkpaper_history thh,\n"
									+ "               test_homeworkpaper_info thi,\n"
									+ "               pe_bzz_student          ps,\n"
									+ "               pr_bzz_tch_opencourse   co,\n"
									+ sql_training
									+ "               pe_bzz_tch_course       ce,\n"
									+ "               pe_enterprise           pe\n"
									+ "         where ps.fk_sso_user_id = su.id\n"
									+ "           and thi.group_id=ce.id and thh.testpaper_id=thi.id and thh.t_user_id=ps.id\n"
									+ "           and pe.id = ps.fk_enterprise_id\n"
									+ "           and co.fk_course_id = ce.id\n"
									+ "           and ps.fk_batch_id = co.fk_batch_id\n"
									+ "           and su.flag_isvalid = '2'\n"
									+ "           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n"
									+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
									+ "           and su.login_num > 0\n"
									+ "			and ts.course_id=co.id and ts.student_id=su.id "
									+ "           and ps.id='"
									+ peBzzBatch.getPeBzzStudent().getId()
									+ "'\n"
									+ sql_hw
									+ "           ) a,\n"
									+ "(select ps.id,thi.id as testpaper_id\n"
									+ "          from sso_user                su,\n"
									+ "               test_homeworkpaper_info thi,\n"
									+ "               pe_bzz_student          ps,\n"
									+ "               pr_bzz_tch_opencourse   co,\n"
									+ "               pe_bzz_tch_course       ce,\n"
									+ sql_training
									+ "               pe_enterprise           pe\n"
									+ "         where ps.fk_sso_user_id = su.id\n"
									+ "           and thi.group_id=ce.id\n"
									+ "           and pe.id = ps.fk_enterprise_id\n"
									+ "           and co.fk_course_id = ce.id\n"
									+ "           and ps.fk_batch_id = co.fk_batch_id\n"
									+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
									+ "           and su.login_num > 0\n"
									+ "			and ts.course_id=co.id and ts.student_id=su.id "
									+ "           and ps.id='"
									+ peBzzBatch.getPeBzzStudent().getId()
									+ "'\n"
									+ "           ) b where a.id(+)=b.id and a.testpaper_id(+)=b.testpaper_id\n"
									+ sql_hw + "           ) where id is null";
							List homeworkList = null;
							try {
								homeworkList = this.getGeneralService()
										.getBySQL(sqlhomework);
							} catch (EntityException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (homeworkList == null || homeworkList.size() > 0) {
								homeworkflag = "0";
								// System.out.println("homeworkflag="+homeworkflag);
							}
						}
						// 判断是否满足报名条件3：在线自测是否必须完成
						if (test.equals("是")) {
							sqltest = "select id,cnum,tnum from\n"
									+ "(select nvl(a.id,1) as id,nvl(a.cnum,0) as cnum,b.tnum from\n"
									+ "(select ps.id,count( distinct tth.testpaper_id) as cnum\n"
									+ "          from sso_user                su,\n"
									+ "               test_testpaper_history tth,\n"
									+ "               test_testpaper_info tti,\n"
									+ "               pe_bzz_student          ps,\n"
									+ "               pr_bzz_tch_opencourse   co,\n"
									+ sql_training
									+ "               pe_bzz_tch_course       ce,\n"
									+ "               pe_enterprise           pe\n"
									+ "         where ps.fk_sso_user_id = su.id and tth.score>=60\n"
									+ "           and tti.group_id=ce.id and tth.testpaper_id=tti.id and tth.t_user_id = ps.id\n"
									+ "           and pe.id = ps.fk_enterprise_id\n"
									+ "           and co.fk_course_id = ce.id\n"
									+ "           and ps.fk_batch_id = co.fk_batch_id\n"
									+ "           and su.flag_isvalid = '2'\n"
									+ "           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n"
									+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
									+ "           and su.login_num > 0\n"
									+ "			and ts.course_id=co.id and ts.student_id=su.id "
									+ "           and ps.id='"
									+ peBzzBatch.getPeBzzStudent().getId()
									+ "'\n"
									+ "           group by ps.id\n"
									+ "           ) a,\n"
									+ "(select ps.id,count(distinct oci.id) as tnum\n"
									+ "          from sso_user                su,\n"
									+ "               test_testpaper_info tti,\n"
									+ "               pe_bzz_student          ps,\n"
									+ "               pr_bzz_tch_opencourse   co,\n"
									+ sql_training
									+ "               onlinetest_course_info  oci,\n"
									+ "               pe_bzz_tch_course       ce,\n"
									+ "               pe_enterprise           pe\n"
									+ "         where ps.fk_sso_user_id = su.id\n"
									+ "           and tti.group_id=ce.id\n"
									+ "           and oci.group_id=ce.id\n"
									+ "           and pe.id = ps.fk_enterprise_id\n"
									+ "           and co.fk_course_id = ce.id\n"
									+ "           and ps.fk_batch_id = co.fk_batch_id\n"
									+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
									+ "           and su.login_num > 0\n"
									+ "			and ts.course_id=co.id and ts.student_id=su.id "
									+ "           and ps.id='"
									+ peBzzBatch.getPeBzzStudent().getId()
									+ "'\n"
									+ sql_test
									+ "           group by ps.id\n"
									+ "           ) b where a.id(+)=b.id\n"
									+ "           ) where cnum<>tnum";
							List testList = null;
							try {
								testList = this.getGeneralService().getBySQL(
										sqltest);
							} catch (EntityException e) {
								e.printStackTrace();
							}
							if (testList == null || testList.size() > 0) {
								testflag = "0";
								// System.out.println("testflag="+testflag);
							}
						}
						// 判断是否满足报名条件4：课程评估是否必须完成
						if (evaluate.equals("是")) {
							sqlevaluate = "select id,cnum,tnum from\n"
									+ "(select nvl(a.id,1) as id,nvl(a.cnum,0) as cnum,b.tnum from\n"
									+ "(select ps.id,count( distinct pba.fk_course_id) as cnum\n"
									+ "          from sso_user                su,\n"
									+ "               pe_bzz_assess pba,\n"
									+ "               pe_bzz_student          ps,\n"
									+ "               pr_bzz_tch_opencourse   co,\n"
									+ "               pe_bzz_tch_course       ce,\n"
									+ "               pe_enterprise           pe\n"
									+ "         where ps.fk_sso_user_id = su.id\n"
									+ "           and pba.fk_student_id=su.id and pba.fk_course_id=co.id\n"
									+ "           and pe.id = ps.fk_enterprise_id\n"
									+ "           and co.fk_course_id = ce.id\n"
									+ "           and ps.fk_batch_id = co.fk_batch_id\n"
									+ "           and su.flag_isvalid = '2'\n"
									+ "           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n"
									+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
									+ "           and su.login_num > 0\n"
									+ "           and ps.id='"
									+ peBzzBatch.getPeBzzStudent().getId()
									+ "'\n"
									+ "           group by ps.id\n"
									+ "           ) a,\n"
									+ "(select ps.id,count(distinct co.id) as tnum\n"
									+ "          from sso_user                su,\n"
									+ "               pe_bzz_student          ps,\n"
									+ "               pr_bzz_tch_opencourse   co,\n"
									+ "               pe_bzz_tch_course       ce,\n"
									+ "               pe_enterprise           pe\n"
									+ "         where ps.fk_sso_user_id = su.id\n"
									+ "           and pe.id = ps.fk_enterprise_id\n"
									+ "           and co.fk_course_id = ce.id\n"
									+ "           and ps.fk_batch_id = co.fk_batch_id\n"
									+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
									+ "           and su.login_num > 0\n"
									+ "           and ps.id='"
									+ peBzzBatch.getPeBzzStudent().getId()
									+ "'\n"
									+ "           group by ps.id\n"
									+ "           ) b where a.id(+)=b.id\n"
									+ "           ) where cnum<>tnum";
							List evaluateList = null;
							try {
								evaluateList = this.getGeneralService()
										.getBySQL(sqlevaluate);
							} catch (EntityException e) {
								e.printStackTrace();
							}
							if (evaluateList == null || evaluateList.size() > 0) {
								evaluateflag = "0";
								// System.out.println("evaluateflag="+evaluateflag);
							}
						}
						if (timeflag.equals("0") || homeworkflag.equals("0")
								|| testflag.equals("0")
								|| evaluateflag.equals("0")) {
							map.put("success", false);
							map.put("info", this.getText("该学员不满足考试要求,不能通过审核"));
							return map;
						} else {
							try {
								Date final_date = new Date();
								EnumConst getEnumConstByFlagExamLateStatus = this
										.getMyListService()
										.getEnumConstByNamespaceCode(
												"FlagExamLateStatus", "1");
								peBzzBatch
										.setEnumConstByFlagExamLateStatus(getEnumConstByFlagExamLateStatus);
								peBzzBatch.setFirstDate(final_date);
								this.getGeneralService().save(peBzzBatch);
							} catch (EntityException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			map.put("success", true);
			map.put("info", this.getText("状态已经成功改变"));

			return map;
		}
		if (this.getColumn().equals("nochecked")) {
			for (int i = 0; i < this.getIds().split(",").length; i++) {
				DetachedCriteria detachedCriteria = DetachedCriteria
						.forClass(PeBzzExamLate.class);
				detachedCriteria.add(Restrictions.eq("id", this.getIds().split(
						",")[i]));
				List<PeBzzExamLate> list = null;
				try {
					list = this.getGeneralService().getList(detachedCriteria);
				} catch (EntityException e1) {
					e1.printStackTrace();
				}
				for (PeBzzExamLate peBzzBatch : list) {
					if (peBzzBatch.getEnumConstByFlagExamLateStatus().getCode()
							.equals("2")
							&& us.getUserLoginType().equals("3")) {
						Date finalDate = null;
						EnumConst getEnumConstByFlagExamLateStatus = this
								.getMyListService()
								.getEnumConstByNamespaceCode(
										"FlagExamLateStatus", "1");
						peBzzBatch
								.setEnumConstByFlagExamLateStatus(getEnumConstByFlagExamLateStatus);
						peBzzBatch.setFinalDate(finalDate);
						try {
							this.getGeneralService().save(peBzzBatch);
						} catch (EntityException e) {
							e.printStackTrace();
						}

					} else if (peBzzBatch.getEnumConstByFlagExamLateStatus()
							.getCode().equals("1")
							&& us.getUserLoginType().equals("2")) {
						Date finalDate = null;
						EnumConst getEnumConstByFlagExamLateStatus = this
								.getMyListService()
								.getEnumConstByNamespaceCode(
										"FlagExamLateStatus", "0");
						peBzzBatch
								.setEnumConstByFlagExamLateStatus(getEnumConstByFlagExamLateStatus);
						peBzzBatch.setFirstDate(finalDate);
						try {
							this.getGeneralService().save(peBzzBatch);
						} catch (EntityException e) {
							e.printStackTrace();
						}
					}
				}
			}
			map.put("success", true);
			map.put("info", this.getText("状态已经成功改变"));

		}

		// 新增总管理员可以直接终审，不经过初审mhy2011-12-06
		if (this.getColumn().equals("checkedFinal")) {

			int totalNum = 0;	//选择的总人数
			int passedNum = 0;	//通过审核的人数
			totalNum = this.getIds().split(",").length;
			for (int i = 0; i < this.getIds().split(",").length; i++) {
				DetachedCriteria detachedCriteria = DetachedCriteria
						.forClass(PeBzzExamLate.class);
				detachedCriteria.add(Restrictions.eq("id", this.getIds().split(
						",")[i]));
				List<PeBzzExamLate> list = null;
				try {
					list = this.getGeneralService().getList(detachedCriteria);
//					totalNum = list.size();
				} catch (EntityException e1) {
					e1.printStackTrace();
				}
				
				
				for (PeBzzExamLate peBzzBatch : list) {
					DetachedCriteria dc = DetachedCriteria
							.forClass(PeBzzExamScore.class);
					// 该考试批次报名条件
					Long time = peBzzBatch.getPeBzzExamBatch().getTime();
					String homework = peBzzBatch.getPeBzzExamBatch()
							.getEnumConstByFlagExamConditionHomework()
							.getName();
					String test = peBzzBatch.getPeBzzExamBatch()
							.getEnumConstByFlagExamConditionTest().getName();
					String evaluate = peBzzBatch.getPeBzzExamBatch()
							.getEnumConstByFlagExamConditionEvaluate()
							.getName();

					String timeflag = "1";
					String homeworkflag = "1";
					String testflag = "1";
					String evaluateflag = "1";

					String sqlevaluate;
					String sqlhomework;
					String sqltest;
					String sqlpaper;

					String sql_training = " training_course_student ts,";
					if (peBzzBatch.getPeBzzStudent().getPeBzzBatch().getId()
							.equals("ff8080812824ae6f012824b0a89e0008")) {
						sql_training = "("
								+ "select s.id,s.course_id,s.percent,s.learn_status,s.student_id from training_course_student s ,pe_bzz_student bs,pr_bzz_tch_opencourse pbto where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
								+ peBzzBatch.getPeBzzStudent().getPeBzzBatch()
										.getId()
								+ "'\n"
								+ "and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and (s.course_id <> 'ff8080812910e7e601291150ddc70419' and s.course_id <>'ff8080812bf5c39a012bf6a1bab80820' and s.course_id <>'ff8080812910e7e601291150ddc70413' and s.course_id <>'ff8080812bf5c39a012bf6a1baba0821')"
								+ " and pbto.id=s.course_id and pbto.flag_course_type='402880f32200c249012200c780c40001' and pbto.fk_batch_id=bs.fk_batch_id "
								+ "union\n"
								+ "select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,"
								+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n"
								+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"
								+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n"
								+ "select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
								+ peBzzBatch.getPeBzzStudent().getPeBzzBatch()
										.getId()
								+ "' and s.course_id='ff8080812910e7e601291150ddc70419' )a,\n"
								+ "(select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s ,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
								+ peBzzBatch.getPeBzzStudent().getPeBzzBatch()
										.getId()
								+ "' and s.course_id='ff8080812bf5c39a012bf6a1bab80820')b\n"
								+ "where a.student_id=b.student_id\n"
								+ "union\n"
								+ "select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,"
								+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n"
								+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"
								+ "decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n"
								+ "select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s ,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
								+ peBzzBatch.getPeBzzStudent().getPeBzzBatch()
										.getId()
								+ "' and s.course_id='ff8080812910e7e601291150ddc70413' )a,\n"
								+ "(select  s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='"
								+ peBzzBatch.getPeBzzStudent().getPeBzzBatch()
										.getId()
								+ "' and s.course_id='ff8080812bf5c39a012bf6a1baba0821')b\n"
								+ "where a.student_id=b.student_id" + ") ts, ";
					}

					String sql_hw = "";
					String sql_test = "";
					if (peBzzBatch.getPeBzzStudent().getPeBzzBatch().getId()
							.equals("ff8080812253f04f0122540a58000004")
							|| peBzzBatch.getPeBzzStudent().getPeBzzBatch()
									.getId().equals(
											"ff8080812824ae6f012824b0a89e0008")) {
						sql_hw = " and thi.batch_id is null ";
						sql_test = " and oci.fk_batch_id is null ";
					} else {
						sql_hw = " and thi.batch_id='"
								+ peBzzBatch.getPeBzzStudent().getPeBzzBatch()
										.getId() + "'";
						sql_test = " and oci.fk_batch_id='"
								+ peBzzBatch.getPeBzzStudent().getPeBzzBatch()
										.getId() + "' ";
					}

					// 该生的选课情况（基础课程）
					String sqlcourse = "select s.true_name,\n"
							+ "       s.reg_no,\n"
							+ "       s.mobile_phone,\n"
							+ "       s.phone,\n"
							+ "       e.name as ename,\n"
							+ "       per\n"
							+ "  from (select ps.id,\n"
							+ "               (nvl(sum(ce.time * (ts.percent / 100)), 0) / sum(ce.time)) * 100 as per\n"
							+ "          from sso_user                su,\n"
							+ sql_training
							+ "               pe_bzz_student          ps,\n"
							+ "               pr_bzz_tch_opencourse   co,\n"
							+ "               pe_bzz_tch_course       ce,\n"
							+ "               pe_enterprise           pe\n"
							+ "         where ps.fk_sso_user_id = su.id\n"
							+ "           and su.id = ts.student_id\n"
							+ "           and pe.id = ps.fk_enterprise_id\n"
							+ "           and co.id = ts.course_id\n"
							+ "           and co.fk_course_id = ce.id\n"
							+ "           and ps.fk_batch_id = co.fk_batch_id\n"
							+ "           and su.flag_isvalid = '2'\n"
							+ "           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n"
							+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
							+ "           and su.login_num > 0\n"
							+ "           and ps.id='"
							+ peBzzBatch.getPeBzzStudent().getId()
							+ "'\n"
							+ "         group by ps.id) a,\n"
							+ "       pe_bzz_student s,\n"
							+ "       pe_enterprise e\n"
							+ " where s.id = a.id\n"
							+ "   and e.id = s.fk_enterprise_id  and per<"
							+ time + "  \n" + " order by e.name, s.reg_no";
					List courseList = null;
					try {
						courseList = this.getGeneralService().getBySQL(
								sqlcourse);
					} catch (EntityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (courseList == null || courseList.size() > 0) {
						timeflag = "0";
						// System.out.println("courseList.size()="+courseList.size());
						// System.out.println("timeflag="+timeflag);
					}
					if (homework.equals("是")) {
						sqlhomework = "select t_id from\n"
								+ "(select a.id,a.testpaper_id,b.testpaper_id as t_id from\n"
								+ "(select ps.id,thh.testpaper_id\n"
								+ "          from sso_user                su,\n"
								+ "               test_homeworkpaper_history thh,\n"
								+ "               test_homeworkpaper_info thi,\n"
								+ "               pe_bzz_student          ps,\n"
								+ "               pr_bzz_tch_opencourse   co,\n"
								+ sql_training
								+ "               pe_bzz_tch_course       ce,\n"
								+ "               pe_enterprise           pe\n"
								+ "         where ps.fk_sso_user_id = su.id\n"
								+ "           and thi.group_id=ce.id and thh.testpaper_id=thi.id and thh.t_user_id=ps.id\n"
								+ "           and pe.id = ps.fk_enterprise_id\n"
								+ "           and co.fk_course_id = ce.id\n"
								+ "           and ps.fk_batch_id = co.fk_batch_id\n"
								+ "           and su.flag_isvalid = '2'\n"
								+ "           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n"
								+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
								+ "           and su.login_num > 0\n"
								+ "			and ts.course_id=co.id and ts.student_id=su.id "
								+ "           and ps.id='"
								+ peBzzBatch.getPeBzzStudent().getId()
								+ "'\n"
								+ sql_hw
								+ "           ) a,\n"
								+ "(select ps.id,thi.id as testpaper_id\n"
								+ "          from sso_user                su,\n"
								+ "               test_homeworkpaper_info thi,\n"
								+ "               pe_bzz_student          ps,\n"
								+ "               pr_bzz_tch_opencourse   co,\n"
								+ "               pe_bzz_tch_course       ce,\n"
								+ sql_training
								+ "               pe_enterprise           pe\n"
								+ "         where ps.fk_sso_user_id = su.id\n"
								+ "           and thi.group_id=ce.id\n"
								+ "           and pe.id = ps.fk_enterprise_id\n"
								+ "           and co.fk_course_id = ce.id\n"
								+ "           and ps.fk_batch_id = co.fk_batch_id\n"
								+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
								+ "           and su.login_num > 0\n"
								+ "			and ts.course_id=co.id and ts.student_id=su.id "
								+ "           and ps.id='"
								+ peBzzBatch.getPeBzzStudent().getId()
								+ "'\n"
								+ "           ) b where a.id(+)=b.id and a.testpaper_id(+)=b.testpaper_id\n"
								+ sql_hw + "           ) where id is null";
						List homeworkList = null;
						try {
							homeworkList = this.getGeneralService().getBySQL(
									sqlhomework);
						} catch (EntityException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (homeworkList == null || homeworkList.size() > 0) {
							homeworkflag = "0";
							// System.out.println("homeworkflag="+homeworkflag);
						}
					}
					// 判断是否满足报名条件3：在线自测是否必须完成
					if (test.equals("是")) {
						sqltest = "select id,cnum,tnum from\n"
								+ "(select nvl(a.id,1) as id,nvl(a.cnum,0) as cnum,b.tnum from\n"
								+ "(select ps.id,count( distinct tth.testpaper_id) as cnum\n"
								+ "          from sso_user                su,\n"
								+ "               test_testpaper_history tth,\n"
								+ "               test_testpaper_info tti,\n"
								+ "               pe_bzz_student          ps,\n"
								+ "               pr_bzz_tch_opencourse   co,\n"
								+ sql_training
								+ "               pe_bzz_tch_course       ce,\n"
								+ "               pe_enterprise           pe\n"
								+ "         where ps.fk_sso_user_id = su.id and tth.score>=60\n"
								+ "           and tti.group_id=ce.id and tth.testpaper_id=tti.id and tth.t_user_id = ps.id\n"
								+ "           and pe.id = ps.fk_enterprise_id\n"
								+ "           and co.fk_course_id = ce.id\n"
								+ "           and ps.fk_batch_id = co.fk_batch_id\n"
								+ "           and su.flag_isvalid = '2'\n"
								+ "           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n"
								+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
								+ "           and su.login_num > 0\n"
								+ "			and ts.course_id=co.id and ts.student_id=su.id "
								+ "           and ps.id='"
								+ peBzzBatch.getPeBzzStudent().getId()
								+ "'\n"
								+ "           group by ps.id\n"
								+ "           ) a,\n"
								+ "(select ps.id,count(distinct oci.id) as tnum\n"
								+ "          from sso_user                su,\n"
								+ "               test_testpaper_info tti,\n"
								+ "               pe_bzz_student          ps,\n"
								+ "               pr_bzz_tch_opencourse   co,\n"
								+ sql_training
								+ "               onlinetest_course_info  oci,\n"
								+ "               pe_bzz_tch_course       ce,\n"
								+ "               pe_enterprise           pe\n"
								+ "         where ps.fk_sso_user_id = su.id\n"
								+ "           and tti.group_id=ce.id\n"
								+ "           and oci.group_id=ce.id\n"
								+ "           and pe.id = ps.fk_enterprise_id\n"
								+ "           and co.fk_course_id = ce.id\n"
								+ "           and ps.fk_batch_id = co.fk_batch_id\n"
								+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
								+ "           and su.login_num > 0\n"
								+ "			and ts.course_id=co.id and ts.student_id=su.id "
								+ "           and ps.id='"
								+ peBzzBatch.getPeBzzStudent().getId()
								+ "'\n"
								+ sql_test
								+ "           group by ps.id\n"
								+ "           ) b where a.id(+)=b.id\n"
								+ "           ) where cnum<>tnum";
						List testList = null;
						try {
							testList = this.getGeneralService().getBySQL(
									sqltest);
						} catch (EntityException e) {
							e.printStackTrace();
						}
						if (testList == null || testList.size() > 0) {
							testflag = "0";
							// System.out.println("testflag="+testflag);
						}
					}
					// 判断是否满足报名条件4：课程评估是否必须完成
					if (evaluate.equals("是")) {
						sqlevaluate = "select id,cnum,tnum from\n"
								+ "(select nvl(a.id,1) as id,nvl(a.cnum,0) as cnum,b.tnum from\n"
								+ "(select ps.id,count( distinct pba.fk_course_id) as cnum\n"
								+ "          from sso_user                su,\n"
								+ "               pe_bzz_assess pba,\n"
								+ "               pe_bzz_student          ps,\n"
								+ "               pr_bzz_tch_opencourse   co,\n"
								+ "               pe_bzz_tch_course       ce,\n"
								+ "               pe_enterprise           pe\n"
								+ "         where ps.fk_sso_user_id = su.id\n"
								+ "           and pba.fk_student_id=su.id and pba.fk_course_id=co.id\n"
								+ "           and pe.id = ps.fk_enterprise_id\n"
								+ "           and co.fk_course_id = ce.id\n"
								+ "           and ps.fk_batch_id = co.fk_batch_id\n"
								+ "           and su.flag_isvalid = '2'\n"
								+ "           and ps.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n"
								+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
								+ "           and su.login_num > 0\n"
								+ "           and ps.id='"
								+ peBzzBatch.getPeBzzStudent().getId()
								+ "'\n"
								+ "           group by ps.id\n"
								+ "           ) a,\n"
								+ "(select ps.id,count(distinct co.id) as tnum\n"
								+ "          from sso_user                su,\n"
								+ "               pe_bzz_student          ps,\n"
								+ "               pr_bzz_tch_opencourse   co,\n"
								+ "               pe_bzz_tch_course       ce,\n"
								+ "               pe_enterprise           pe\n"
								+ "         where ps.fk_sso_user_id = su.id\n"
								+ "           and pe.id = ps.fk_enterprise_id\n"
								+ "           and co.fk_course_id = ce.id\n"
								+ "           and ps.fk_batch_id = co.fk_batch_id\n"
								+ "           and co.flag_course_type = '402880f32200c249012200c780c40001'\n"
								+ "           and su.login_num > 0\n"
								+ "           and ps.id='"
								+ peBzzBatch.getPeBzzStudent().getId()
								+ "'\n"
								+ "           group by ps.id\n"
								+ "           ) b where a.id(+)=b.id\n"
								+ "           ) where cnum<>tnum";
						List evaluateList = null;
						try {
							evaluateList = this.getGeneralService().getBySQL(
									sqlevaluate);
						} catch (EntityException e) {
							e.printStackTrace();
						}
						if (evaluateList == null || evaluateList.size() > 0) {
							evaluateflag = "0";
							// System.out.println("evaluateflag="+evaluateflag);
						}
					}
					if (timeflag.equals("0") || homeworkflag.equals("0")
							|| testflag.equals("0") || evaluateflag.equals("0")) {
//						map.put("success", false);
//						map.put("info", this.getText("该学员不满足考试要求,不能通过审核"));
//						return map;
					} else {
						try {
							Date final_date = new Date();
							EnumConst getEnumConstByFlagExamLateStatus = this
									.getMyListService()
									.getEnumConstByNamespaceCode(
											"FlagExamLateStatus", "2");
							peBzzBatch
									.setEnumConstByFlagExamLateStatus(getEnumConstByFlagExamLateStatus);
							peBzzBatch.setFinalDate(final_date);
							this.getGeneralService().save(peBzzBatch);
							passedNum++;
						} catch (EntityException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			String message = "审核通过";
			if(passedNum != totalNum){
				message = "共选择" + totalNum + "条记录<br/>";
				if(passedNum != 0){
					message += "通过审核" + passedNum + "条<br/>";
				}
				message += (totalNum - passedNum) + "条不满足考试要求,不能通过审核";
			}
			
			map.put("success", true);
			map.put("info", this.getText(message));

			return map;
		}

		return map;
	}

	// add校验

	public void checkBeforeAdd() throws EntityException {
		EnumConst getEnumConstByFalgExamLateStatus = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagExamLateStatus", "2");
		this.getBean().setEnumConstByFlagExamLateStatus(
				getEnumConstByFalgExamLateStatus);
	}

	// update校验

	public void checkBeforeUpdate() throws EntityException {

	}

	// 懒加载

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamLate.class);
		// dc.add(Restrictions.ne("enumConstByFlagExamLateStatus.id",
		// "aa2880a91dadc115011dadfcf26b0002"));
		// dc.createCriteria("peBzzStudent", "peBzzStudent",
		// DetachedCriteria.INNER_JOIN);
		dc.createCriteria("peBzzExamBatch", "peBzzExamBatch",
				DetachedCriteria.INNER_JOIN);
		dc.createCriteria("enumConstByFlagExamLateStatus",
				"enumConstByFlagExamLateStatus", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria pebzz = dc.createCriteria("peBzzStudent",
				"peBzzStudent", DetachedCriteria.INNER_JOIN);
		pebzz.createAlias("peBzzBatch", "peBzzBatch",
				DetachedCriteria.LEFT_JOIN);
		pebzz.createAlias("peEnterprise", "peEnterprise",
				DetachedCriteria.LEFT_JOIN);

		DetachedCriteria dct = DetachedCriteria.forClass(PeBzzStudent.class);
		dct.createAlias("peEnterprise", "peEnterprise",
				DetachedCriteria.LEFT_JOIN);
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		if (!us.getUserLoginType().equals("3")) {
			List<String> priEntIds = new ArrayList<String>();
			for (PeEnterprise pe : us.getPriEnterprises()) {
				priEntIds.add(pe.getId());
			}
			if (priEntIds.size() != 0) {
				dc.add(Restrictions.in("peBzzStudent.peEnterprise.id",
						priEntIds));
			} else {
				dc.add(Expression.eq("2", "1"));
			}
		}
		return dc;
	}

}
