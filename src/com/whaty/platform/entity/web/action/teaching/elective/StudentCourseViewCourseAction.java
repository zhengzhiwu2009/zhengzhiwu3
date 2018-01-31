package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class StudentCourseViewCourseAction extends MyBaseAction {

	private String id;

	public String getId() {
		return id;
	}

	public String getCsDate() {
		return csDate;
	}

	public void setCsDate(String csDate) {
		this.csDate = csDate;
	}

	public String getCeDate() {
		return ceDate;
	}

	public void setCeDate(String ceDate) {
		this.ceDate = ceDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String csDate;
	private String ceDate;

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("学员—课程");
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程编号"), "code", true);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name", true);
		this.getGridConfig().addColumn(this.getText("课程学时"), "time", true);
		this.getGridConfig().addColumn(this.getText("是否必修"), "courseType",
				false);
		this.getGridConfig().addColumn(this.getText("支付时间"), "paymentDate",
				false);
		this.getGridConfig()
				.addColumn(this.getText("开始学习时间"), "getDate", false);
		this.getGridConfig().addColumn(this.getText("学习状态"), "learnStatus",
				false, false, false, "");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("学习状态"),
						"{if(record.data['learnStatus'] =='INCOMPLETE'){return '未完成';}else if(record.data['learnStatus'] =='UNCOMPLETE') {return '未开始';} else {return '已完成';}}",
						"");
		this.getGridConfig().addColumn(this.getText("考试结果"), "score", false);
	//	this.getGridConfig().addColumn(this.getText("是否发布"), "isValid", false);
		this.getGridConfig().addColumn(this.getText("课程完成时间"),
				"completed_time", false);
		this.getGridConfig().addColumn(this.getText("支付开始时间"), "paysStartDate",
				true, false, false, "");
		this.getGridConfig().addColumn(this.getText("支付结束时间"), "payEndDate",
				true, false, false, "");
		this.getGridConfig().addColumn(this.getText("学习开始时间"),
				"learnStartDate", true, false, false, "");
		this.getGridConfig().addColumn(this.getText("学习结束时间"), "learnEndDate",
				true, false, false, "");
		// Lee start 2014年1月3日

		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		/*if ("24".indexOf(us.getUserLoginType()) != -1) {
			this.getGridConfig().addColumn(this.getText("获得学时开始时间"), "csDate",
					true, true, true, "");
			this.getGridConfig().addColumn(this.getText("获得学时结束时间"), "ceDate",
					true, true, true, "");
		}*/
		// Lee end
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("操作"),
						"<a href='/entity/teaching/studentCourse_studyDetail.action?courseId=${value}&id="
								+ this.id + "'>查看学习详情</a>", "id");
		String firstSiteId = (String) ServletActionContext.getContext()
				.getSession().get("student_course_firstSiteId");
		String secondSiteId = (String) ServletActionContext.getContext()
				.getSession().get("student_course_secondSiteId");
		this.getGridConfig()
				.addMenuScript(
						this.getText("返回"),
						"{window.location.href='/entity/teaching/studentCourse.action?firstSiteId="
								+ firstSiteId + "&secondSiteId=" + secondSiteId
								+ "';}");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/studentCourseViewCourse";
	}

	/**
	 * 重写框架方法：课程信息（带sql条件）
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		StringBuffer sqlBuf = new StringBuffer();
		String sql =

		"  select * from (	select c.id as id,\n"
				+ "       c.code as code,\n"
				+ "       c.name as name,\n"
				+ "       c.time as time,\n"
				+ "       ec.name as courseType,\n"
				+ "       o.payment_date as paymentDate,\n"
				+ "       t.get_date as getDate,\n"
				+ "       t.learn_status as learnStatus,\n"
				+ "		nvl(ele.score_exam, 0) as score,\n"
				+ "       isv.name as isValid,\n"
				+ "       nvl(to_char(ele.completed_time,'yyyy-MM-dd'),'--') as completed_time,\n"
				+ "       to_date('2012-12-21','yyyy-MM-dd') as paysStartDate,\n"
				+ "       to_date('2012-12-21','yyyy-MM-dd') as payEndDate,\n"
				+ "       to_date('2012-12-21','yyyy-MM-dd') as learnStartDate,\n"
				+ "       to_date('2012-12-21','yyyy-MM-dd') as learnEndDate,\n "
				// Lee start 2014年1月3日
				+ "       to_char(ele.completed_time,'yyyy-MM-dd hh24:mi') as cDate\n"
				// Lee end
				+ "  from PR_BZZ_TCH_STU_ELECTIVE ele\n"
				+ "  inner join pr_bzz_tch_opencourse op on ele.fk_tch_opencourse_id = op.id\n"
				+ "  inner join pe_bzz_tch_course c on op.fk_course_id = c.id\n"
				+
				// " inner join enum_const ec1 on
				// ele.flag_elective_pay_status=ec1.id\n" +
				// //正式平台数据迁移，选课表中没有购买状态，导致查看学员详细课程信息时数据加载不上，注释掉
				"  left join enum_const ec on c.flag_coursetype = ec.id\n"
				+ "  left join pe_bzz_order_info o on ele.fk_order_id = o.id\n"
				+ "  left join training_course_student t on ele.fk_training_id = t.id\n"
				+ "  left join enum_const isv on c.flag_isvalid = isv.id\n"
				+ "  \n" + "  where ele.fk_stu_id = '" + this.getId() + "'";
		if (!"".equals(csDate)&&!"undefined".equals(csDate)) {
			sql += "					and \n		";
			sql += "					to_char(ele.completed_time,'yyyy-mm-dd') >= '" + csDate + "'	";
		}
		if (!"".equals(ceDate)&&!"undefined".equals(ceDate)) {
			sql += ("					and \n		");
			sql += ("					to_char(ele.completed_time,'yyyy-mm-dd') <= '" + ceDate + "'	");
		}

		sqlBuf.append(sql);

		// 处理时间的查询参数
		ActionContext context = ActionContext.getContext();
		if (context.getParameters() != null) {
			Map params = this.getParamsMap();
			if (params != null) {

				if (params.get("search__paysStartDate") != null) {
					String[] startDate = (String[]) params
							.get("search__paysStartDate");
					if (startDate.length == 1
							&& !StringUtils.defaultString(startDate[0]).equals(
									"")) {
						sqlBuf.append("and o.payment_date  >= to_date('"
								+ startDate[0] + "','yyyy-MM-dd')");
						params.remove("search__paysStartDate");
					}
				}

				if (params.get("search__payEndDate") != null) {
					String[] endDate = (String[]) params
							.get("search__payEndDate");
					if (endDate.length == 1
							&& !StringUtils.defaultString(endDate[0])
									.equals("")) {
						sqlBuf.append(" and o.payment_date  <= to_date('"
								+ endDate[0]
								+ " 23:59:59','yyyy-MM-dd HH24:mi:ss')");
						params.remove("search__payEndDate");
					}
				}

				if (params.get("search__learnStartDate") != null) {
					String[] startDate = (String[]) params
							.get("search__learnStartDate");
					if (startDate.length == 1
							&& !StringUtils.defaultString(startDate[0]).equals(
									"")) {
						sqlBuf.append(" and t.get_date  >= to_date('"
								+ startDate[0] + "','yyyy-MM-dd')");
						params.remove("search__learnStartDate");
					}
				}

				if (params.get("search__learnEndDate") != null) {
					String[] endDate = (String[]) params
							.get("search__learnEndDate");
					if (endDate.length == 1
							&& !StringUtils.defaultString(endDate[0])
									.equals("")) {
						sqlBuf.append(" and t.get_date  <= to_date('"
								+ endDate[0]
								+ " 23:59:59','yyyy-MM-dd HH24:mi:ss')");
						params.remove("search__learnEndDate");
					}
				}
				// Lee start 2014年1月3日
				if (params.get("search__cDate") != null) {
					String[] completeDate = (String[]) params
							.get("search__cDate");
					if (completeDate.length == 1
							&& !StringUtils.defaultString(completeDate[0])
									.equals("")) {
						sqlBuf
								.append(" and to_char(ele.completed_time,'yyyy-MM-dd') = '"
										+ completeDate[0] + "'");
						params.remove("search__cDate");
					}
				}
				// Lee end
				context.setParameters(params);
			}
		}
		sqlBuf.append("	) where 1=1	");
		this.setSqlCondition(sqlBuf);
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(sqlBuf.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

}
