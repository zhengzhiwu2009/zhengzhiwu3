package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeAssCourseDetailAction extends MyBaseAction<PeBzzTchCourse> {
	
	private String id;
	
	private String courseId;
	
	private String openCourseId;
	
	private String exam_y_n;
	
	private String type;
	
	/**
	 * 初始化列表
	 * 
	 * @author Lee
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle("学习详情");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("课程编号"), "code");
		
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		
		this.getGridConfig().addColumn(this.getText("学时"), "time", false, false, true, "TextField");
		
		this.getGridConfig().addColumn(this.getText("开始学习时间"), "get_date", false, false, true, "TextField");
		
		this.getGridConfig().addColumn(this.getText("课程完成时间"), "complete_date", true, false, true, "TextField");
		
		ColumnConfig columnConfigFlagLearnStatus= new ColumnConfig(this.getText("学习状态"), "enumConstByFlagLearnStatus.name", true, false, true, "TextField", false, 100, "");
		String sql = "select a.id ,a.name from enum_const a where a.namespace='LearnStatus'";
		columnConfigFlagLearnStatus.setComboSQL(sql);
		this.getGridConfig().addColumn(columnConfigFlagLearnStatus);
		
		ColumnConfig columnConfigFlagExamStatus= new ColumnConfig(this.getText("考试结果"), "enumConstByFlagExamStatus.name", true, false, true, "TextField", false, 100, "");
		String sql1 = "select a.id ,a.name from enum_const a where a.namespace='ExamStatus'";
		columnConfigFlagExamStatus.setComboSQL(sql1);
		this.getGridConfig().addColumn(columnConfigFlagExamStatus);
		
//		this.getGridConfig().addColumn(this.getText("考试结果"), "learningCourse", false, false, true, "TextField");
		
		this.getGridConfig().addRenderScript(this.getText("查看学习详情"),
				"{return '<a href=\"/entity/teaching/peAssCourseDetail_learnCourseDetail.action?courseId='+${value}+'&id=" + this.getId() + "&type=" + type + "\" target=\"_blank\">查看学习详情</a>';}", "id");

		this.getGridConfig().addMenuScript(this.getText("返回"), "{location.href='entity/basic/peBzzCourseAssSearch.action'};");

	}
	
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzTchCourse.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/peAssCourseDetail";
	}

	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);

	}

	public PeBzzTchCourse getBean() {
		return super.superGetBean();
	}
	
	/**
	 * 课程管理列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page list() {
		Page page = null;
		try {
			StringBuffer sqlBuffer = new StringBuffer();
//			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			sqlBuffer.append("SELECT * FROM ( ");
			sqlBuffer.append("select pbtc.id,pbtc.code,pbtc.name,translate(pbtc.time, 0, '-') time,tcs.get_date,tcs.complete_date, ");
			sqlBuffer.append("       case when tcs.learn_status = 'COMPLETED' then '已完成学习' ");
			sqlBuffer.append("            when tcs.learn_status = 'INCOMPLETE' then '正在学习' ");
			sqlBuffer.append("       else '未开始学习' ");
			sqlBuffer.append("       end FlagLearnStatus, ");
			sqlBuffer.append("       case when ec1.code = '1' and pbtue.ispass = '1' then '已通过考试' ");
			sqlBuffer.append("            when ec1.code = '0' then '不考试' ");
			sqlBuffer.append("       else '未通过考试' ");
			sqlBuffer.append("       end FlagExamStatus,pbtc.create_date as createDate ");
			sqlBuffer.append("  from pe_bzz_tch_course pbtc ");
			sqlBuffer.append("  join pr_bzz_tch_opencourse pbto on pbtc.id = pbto.fk_course_id ");
			sqlBuffer.append("  left join pe_bzz_batch pbb on pbto.fk_batch_id = pbb.id ");
			sqlBuffer.append("  join training_course_student tcs on tcs.course_id = pbto.id ");
			sqlBuffer.append("  join sso_user su on tcs.student_id = su.id ");
			sqlBuffer.append("  join pr_bzz_tch_user_elective pbtue on pbtue.fk_tch_opencourse_id = pbto.id");
			sqlBuffer.append("  									and pbtue.fk_user_id = su.id ");
			sqlBuffer.append("  join enum_const ec1 on pbtc.flag_is_exam = ec1.id ");
			sqlBuffer.append("  join enum_const ec2 on pbtc.flag_isvalid = ec2.id ");
			sqlBuffer.append("  join enum_const ec3 on pbtc.flag_offline = ec3.id ");
			sqlBuffer.append(" where ec2.code = '1' and ec3.code = '0' ");
			sqlBuffer.append(" and (pbb.id = '40288a7b394d676d01394dad824c003b' or pbto.fk_batch_id is null) and su.id = '" + this.getId() + "'");
			sqlBuffer.append(") where 1=1");
			/* 拼接查询条件 */
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}
				/* 是否有效-发布 */
				if (name.equals("enumConstByFlagLearnStatus.name")) {
					name = "FlagLearnStatus";
				}
				/* 下线 */
				if (name.equals("enumConstByFlagExamStatus.name")) {
					name = "FlagExamStatus";
				}
				sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 学习状态 */
				if (temp.equals("enumConstByFlagLearnStatus.name")) {
					temp = "FlagLearnStatus";
				}
				/* 考试结果 */
				if (temp.equals("enumConstByFlagExamStatus.name")) {
					temp = "FlagExamStatus";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					if ("time".equalsIgnoreCase(temp) || "price".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by to_number(" + temp + ") desc ");
					} else {
						if (!temp.equals("enumConstByFlagSuggest.name"))
							sqlBuffer.append(" order by " + temp + " desc ");
					}
				} else {
					if ("time".equalsIgnoreCase(temp) || "price".equalsIgnoreCase(temp)) {
						sqlBuffer.append(" order by to_number(" + temp + ") asc ");
					} else {
						if (!temp.equals("enumConstByFlagSuggest.name"))
							sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
			} else {
				sqlBuffer.append(" order by createDate desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	
	public String learnCourseDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select ec.code from Pe_Bzz_Tch_Course PBTC ");
		sqlBuffer.append("  join pr_bzz_tch_opencourse pbto on pbto.fk_course_id = pbtc.id ");
		sqlBuffer.append("  left join pe_bzz_batch pbb on pbb.id = pbto.fk_batch_id ");
		sqlBuffer.append("  join enum_const ec on pbtc.flag_is_exam = ec.id ");
		sqlBuffer.append(" where pbtc.id='" + this.getCourseId() + "'");
		sqlBuffer.append(" and (pbb.id = '40288a7b394d676d01394dad824c003b' or pbto.fk_batch_id is null)");
		try {
			String course_exam = (String) this.getGeneralService().getBySQL(sqlBuffer.toString()).get(0);
			this.exam_y_n = "1".equals(course_exam) ? "true" : "false";
			request.setAttribute("exam_y_n", exam_y_n);
			request.setAttribute("type", type);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "learnCourseDetail";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getOpenCourseId() {
		return openCourseId;
	}

	public void setOpenCourseId(String openCourseId) {
		this.openCourseId = openCourseId;
	}

	public String getExam_y_n() {
		return exam_y_n;
	}

	public void setExam_y_n(String exam_y_n) {
		this.exam_y_n = exam_y_n;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
