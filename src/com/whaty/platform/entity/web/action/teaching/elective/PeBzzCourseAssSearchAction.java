package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeBzzCourseAssSearchAction extends MyBaseAction<PeBzzTchCourse> {
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
		
		this.getGridConfig().setTitle("协会内训查询");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("系统编号"), "login_id");
		
		this.getGridConfig().addColumn(this.getText("姓名"), "true_name");
		
//		this.getGridConfig().addColumn(this.getText("所在部门"), "depart");
		
		this.getGridConfig().addColumn(this.getText("获得学时总数"), "sumTime", false, false, true, "TextField");
		
		this.getGridConfig().addColumn(this.getText("内训时长总数(分钟)"), "sumSecs", false, false, true, "TextField");
		
		this.getGridConfig().addColumn(this.getText("已完成课程数"), "overCourse", false, false, true, "TextField");
		
		this.getGridConfig().addColumn(this.getText("正在学习课程数"), "learningCourse", false, false, true, "TextField");
		
		this.getGridConfig().addRenderScript(this.getText("学习详情"),
				"{return '<a href=\"/entity/teaching/peAssCourseDetail.action?id='+${value}+'&type=0\">学习详情</a>';}", "id");

	}
	
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeBzzTchCourse.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/peBzzCourseAssSearch";
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
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			sqlBuffer.append("SELECT * FROM ( ");
			sqlBuffer.append("select su.id,su.login_id,pm.true_name,nvl(a.course_times,0) sumTime, ");
			sqlBuffer.append("nvl(b.course_lens,0) sumSecs,nvl(c.com_cun,0) overCourse,nvl(d.in_cun,0) learningCourse ");
			sqlBuffer.append("from sso_user su ");
			sqlBuffer.append("  join pe_manager pm on pm.fk_sso_user_id = su.id ");
			sqlBuffer.append("  join pe_pri_role ppr on su.fk_role_id = ppr.id ");
			sqlBuffer.append("  left join (select sum(to_number(pbtc.time)) course_times, tcs.student_id ");
			sqlBuffer.append("               from pe_bzz_tch_course pbtc ");
			sqlBuffer.append("               join pr_bzz_tch_opencourse pbto on pbtc.id = pbto.fk_course_id ");
			sqlBuffer.append("               join training_course_student tcs on tcs.course_id = pbto.id ");
			sqlBuffer.append("               join enum_const ec on pbtc.flag_coursearea = ec.id ");
			sqlBuffer.append("               join pr_bzz_tch_user_elective pbtue on pbtue.fk_training_id = tcs.id ");
			sqlBuffer.append("               join enum_const ec1 on pbtc.flag_is_exam = ec1.id ");
			sqlBuffer.append("				 join enum_const ec2 on pbtc.flag_isvalid = ec2.id ");
			sqlBuffer.append("				 join enum_const ec3 on pbtc.flag_offline = ec3.id ");
			sqlBuffer.append("              where tcs.learn_status = 'COMPLETED' ");
			sqlBuffer.append("                and ec.code in ('1', '2') ");
			sqlBuffer.append("				  and ec2.code = '1' and ec3.code = '0' ");
			sqlBuffer.append("                and ((ec1.code = '1' and pbtue.ispass = '1') or ec1.code = '0') ");
			sqlBuffer.append("              group by tcs.student_id) a on a.student_id = su.id ");
			sqlBuffer.append("  left join (select sum(to_number(pbtc.course_len)) course_lens, tcs.student_id ");
			sqlBuffer.append("               from pe_bzz_tch_course pbtc ");
			sqlBuffer.append("               join pr_bzz_tch_opencourse pbto on pbtc.id = pbto.fk_course_id ");
			sqlBuffer.append("               join training_course_student tcs on tcs.course_id = pbto.id ");
			sqlBuffer.append("               join enum_const ec on pbtc.flag_coursearea = ec.id ");
			sqlBuffer.append("               join pr_bzz_tch_user_elective pbtue on pbtue.fk_training_id = tcs.id ");
			sqlBuffer.append("               join enum_const ec1 on pbtc.flag_is_exam = ec1.id ");
			sqlBuffer.append("				 join enum_const ec2 on pbtc.flag_isvalid = ec2.id ");
			sqlBuffer.append("				 join enum_const ec3 on pbtc.flag_offline = ec3.id ");
			sqlBuffer.append("              where tcs.learn_status = 'COMPLETED' ");
			sqlBuffer.append("                and ec.code in ('3', '4', '5') ");
			sqlBuffer.append("				  and ec2.code = '1' and ec3.code = '0' ");
			sqlBuffer.append("                and ((ec1.code = '1' and pbtue.ispass = '1') or ec1.code = '0') ");
			sqlBuffer.append("              group by tcs.student_id) b on b.student_id = su.id ");
			sqlBuffer.append("  left join (select count(*) com_cun, tcs.student_id ");
			sqlBuffer.append("               from pe_bzz_tch_course pbtc ");
			sqlBuffer.append("               join pr_bzz_tch_opencourse pbto on pbtc.id = pbto.fk_course_id ");
			sqlBuffer.append("               join training_course_student tcs on tcs.course_id = pbto.id ");
			sqlBuffer.append("               join enum_const ec on pbtc.flag_coursearea = ec.id ");
			sqlBuffer.append("               join pr_bzz_tch_user_elective pbtue on pbtue.fk_training_id = tcs.id ");
//			sqlBuffer.append("               join enum_const ec1 on pbtc.flag_is_exam = ec1.id ");
			sqlBuffer.append("				 join enum_const ec2 on pbtc.flag_isvalid = ec2.id ");
			sqlBuffer.append("				 join enum_const ec3 on pbtc.flag_offline = ec3.id ");
			sqlBuffer.append("              where tcs.learn_status = 'COMPLETED' ");
			sqlBuffer.append("                and ec.code in ('1', '2', '3', '4', '5') ");
			sqlBuffer.append("				  and ec2.code = '1' and ec3.code = '0' ");
//			sqlBuffer.append("                and ((ec1.code = '1' and pbtue.ispass = '1') or ec1.code = '0') ");
			sqlBuffer.append("              group by tcs.student_id) c on su.id = c.student_id ");
			sqlBuffer.append("  left join (select count(*) in_cun, tcs.student_id ");
			sqlBuffer.append("               from pe_bzz_tch_course pbtc ");
			sqlBuffer.append("               join pr_bzz_tch_opencourse pbto on pbtc.id = pbto.fk_course_id ");
			sqlBuffer.append("				 join pr_bzz_tch_user_elective pbtue on pbtue.fk_tch_opencourse_id = pbto.id ");
			sqlBuffer.append("               join training_course_student tcs on pbtue.fk_training_id = tcs.id ");
			sqlBuffer.append("               join enum_const ec on pbtc.flag_coursearea = ec.id ");
			sqlBuffer.append("				 join enum_const ec1 on pbtc.flag_isvalid = ec1.id ");
			sqlBuffer.append("				 join enum_const ec2 on pbtc.flag_offline = ec2.id ");
			sqlBuffer.append("              where tcs.learn_status = 'INCOMPLETE' ");
			sqlBuffer.append("                and ec.code in ('1', '2', '3', '4', '5') ");
			sqlBuffer.append("				  and ec1.code = '1' and ec2.code = '0' ");
			sqlBuffer.append("              group by tcs.student_id) d on su.id = d.student_id ");
			sqlBuffer.append("where ppr.name like '%协会管理员%' ");
			sqlBuffer.append(") where 1=1");
			StringBuffer sql = new StringBuffer(sqlBuffer);
			this.setSqlCondition(sql);
			page = this.getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
}
