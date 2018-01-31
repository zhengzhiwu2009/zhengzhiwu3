package com.whaty.platform.entity.web.action.teaching.elective;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.CourseStudentStatVO;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.training.basic.StudyProgress;

public class CourseStudentAction extends MyBaseAction{

	private String id;
	private PeBzzTchCourse peBzzTchCourse; 
	private List list;
	private List<Object[]> couStuList;
	private String courseId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public PeBzzTchCourse getPeBzzTchCourse() {
		return this.peBzzTchCourse;
	}
	public void setPeBzzTchCourse(PeBzzTchCourse peBzzTchCourse) {
		this.peBzzTchCourse = peBzzTchCourse;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	/**
	 * 初始化列表
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
			this.getGridConfig().setTitle("课程—学员");
			this.getGridConfig().setCapability(false, false, false,false);
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "code", false);
			this.getGridConfig().addColumn(this.getText("课程名称"), "name", false);
			this.getGridConfig().addColumn(this.getText("课程学时"), "time", false);
			this.getGridConfig().addColumn(this.getText("是否必修"), "coursetype",false);
			this.getGridConfig().addColumn(this.getText("已缴费人数"), "stuNum", false);
			this.getGridConfig().addColumn(this.getText("缴费总金额"), "totalMoney", false);
			this.getGridConfig().addColumn(this.getText("学习完成率"), "com", false);
			this.getGridConfig().addColumn(this.getText("考试通过率"), "pass", false);
			this
			.getGridConfig()
			.addRenderFunction(
					this.getText("查看学员"),
					"<a href=\"/entity/teaching/courseStudentViewStudent.action?id=${value}\">查看学员</a>",
					"id");
			this
			.getGridConfig()
			.addRenderFunction(
					this.getText("课程信息"),
					"<a href=\"/entity/teaching/courseStudent_viewCourse.action?id=${value}\">课程信息</a>",
					"id");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath="/entity/teaching/courseStudent";
	}
	
	/**
	 * 重写框架方法：课程信息（带sql条件）
	 * @author linjie
	 * @return
	 */
	@Override
	public Page list() {
		// TODO Auto-generated method stub
		EnumConst classHourRate = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY); 
		
		String enterpriseId = us.getUserLoginType().equals("3")?"":us.getPriEnterprises().get(0).getId();
		String paystatus = this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagElectivePayStatus", "1").getId();
		
		if(!StringUtils.defaultString(this.getCourseId()).equals("")){
			ServletActionContext.getContext().getSession().put("course_student_courseId", StringUtils.defaultString(this.getCourseId()));
		}
		String courseId = (String)ServletActionContext.getContext().getSession().get("course_student_courseId");
		
		StringBuffer countSQL = new StringBuffer(" select count(pbtc.id) from pe_bzz_tch_course pbtc,pr_bzz_tch_opencourse pbto where  pbtc.id = pbto.fk_course_id  and pbtc.id = '" + courseId + "'");
		StringBuffer sqlBuf = new StringBuffer();
		StringBuffer sqlElective = new StringBuffer();
		StringBuffer sqlTraining = new StringBuffer();
		try{
			if(us.getUserLoginType().equals("3")){//协会管理员
				sqlElective.append(" 	pr_bzz_tch_stu_elective ");
				sqlTraining.append("	training_course_student	");
			}else if(us.getUserLoginType().equals("2")){//一级集体管理员
                sqlElective.append("   ( select fk_tch_opencourse_id,flag_elective_pay_status,fk_order_id,fk_stu_id,score_exam from  pr_bzz_tch_stu_elective	\n");
                sqlElective.append("   where exists (                                                                                                       \n");
                sqlElective.append("   select pbs.id,pbs.fk_sso_user_id from pe_bzz_student pbs                                                             \n");
                sqlElective.append("   where fk_stu_id =  pbs.id and exists                                                                                 \n");
                sqlElective.append("   ( select a.id                                                                                                        \n");
                sqlElective.append("     from ( select pe.id, pe.name                                                                                       \n");
                sqlElective.append("             from pe_enterprise pe,                                                                                     \n");
                sqlElective.append("                  pe_enterprise pe_parent                                                                               \n");
                sqlElective.append("            where pe_parent.id = pe.fk_parent_id                                                                        \n");
                sqlElective.append("              and pe_parent.id = '"+ enterpriseId +"'                                                                   \n");
                sqlElective.append("           union                                                                                                        \n");
                sqlElective.append("           select pe.id, pe.name                                                                                        \n");
                sqlElective.append("             from pe_enterprise pe                                                                                      \n");
                sqlElective.append("            where pe.id = '"+ enterpriseId +"' ) a                                                                      \n");
                sqlElective.append("    where a.id = pbs.fk_enterprise_id )                                                                                 \n");
                sqlElective.append("    and exists                                                                                                          \n");
                sqlElective.append("    (                                                                                                                   \n");
                sqlElective.append("        select id from sso_user su where su.id = pbs.fk_sso_user_id                                                     \n");
                sqlElective.append("    ) ) )                                                                                                                \n");

                sqlTraining.append("  ( select  stu.student_id,stu.learn_status,stu.course_id from training_course_student stu \n");
                sqlTraining.append("  where exists (                                                                         \n");
                sqlTraining.append("  select pbs.id,pbs.fk_sso_user_id from pe_bzz_student pbs                               \n");
                sqlTraining.append("  where stu.student_id =  pbs.fk_sso_user_id and exists                                  \n");
                sqlTraining.append("  ( select a.id                                                                          \n");
                sqlTraining.append("    from ( select pe.id, pe.name                                                         \n");
                sqlTraining.append("            from pe_enterprise pe,                                                       \n");
                sqlTraining.append("                 pe_enterprise pe_parent                                                 \n");
                sqlTraining.append("           where pe_parent.id = pe.fk_parent_id                                          \n");
                sqlTraining.append("             and pe_parent.id = '"+ enterpriseId +"'                                     \n");
                sqlTraining.append("          union                                                                          \n");
                sqlTraining.append("          select pe.id, pe.name                                                          \n");
                sqlTraining.append("            from pe_enterprise pe                                                        \n");
                sqlTraining.append("           where pe.id = '"+ enterpriseId +"' ) a                                        \n");
                sqlTraining.append("   where a.id = pbs.fk_enterprise_id )                                                   \n");
                sqlTraining.append("   and exists                                                                            \n");
                sqlTraining.append("   (                                                                                     \n");
                sqlTraining.append("       select id from sso_user su where su.id = pbs.fk_sso_user_id                       \n");
                sqlTraining.append("   ) ) )                                                                                  \n");				

			}else if(us.getUserLoginType().equals("4")){//二级集体管理员
				
                sqlElective.append("   ( select fk_tch_opencourse_id,flag_elective_pay_status,fk_order_id,fk_stu_id,score_exam from  pr_bzz_tch_stu_elective	\n");
                sqlElective.append("   where exists (                                                                                                       \n");
                sqlElective.append("   select pbs.id,pbs.fk_sso_user_id from pe_bzz_student pbs                                                             \n");
                sqlElective.append("   where fk_stu_id =  pbs.id and exists                                                                                 \n");
                sqlElective.append("   ( select a.id                                                                                                        \n");
                sqlElective.append("     from (                                                                                      \n");
                sqlElective.append("           select pe.id, pe.name                                                                                        \n");
                sqlElective.append("             from pe_enterprise pe                                                                                      \n");
                sqlElective.append("            where pe.id = '"+ enterpriseId +"' ) a                                                                                    \n");
                sqlElective.append("    where a.id = pbs.fk_enterprise_id )                                                                                 \n");
                sqlElective.append("    and exists                                                                                                          \n");
                sqlElective.append("    (                                                                                                                   \n");
                sqlElective.append("        select id from sso_user su where su.id = pbs.fk_sso_user_id                                                     \n");
                sqlElective.append("    ) ) )                                                                                                                 \n");

                sqlTraining.append("  ( select  stu.student_id,stu.learn_status,stu.course_id from training_course_student stu \n");
                sqlTraining.append("  where exists (                                                                         \n");
                sqlTraining.append("  select pbs.id,pbs.fk_sso_user_id from pe_bzz_student pbs                               \n");
                sqlTraining.append("  where stu.student_id =  pbs.fk_sso_user_id and exists                                  \n");
                sqlTraining.append("  ( select a.id                                                                          \n");
                sqlTraining.append("    from (                                                     \n");
                sqlTraining.append("          select pe.id, pe.name                                                          \n");
                sqlTraining.append("            from pe_enterprise pe                                                        \n");
                sqlTraining.append("           where pe.id = '"+ enterpriseId +"' ) a                                        \n");
                sqlTraining.append("   where a.id = pbs.fk_enterprise_id )                                                   \n");
                sqlTraining.append("   and exists                                                                            \n");
                sqlTraining.append("   (                                                                                     \n");
                sqlTraining.append("       select id from sso_user su where su.id = pbs.fk_sso_user_id                       \n");
                sqlTraining.append("   ) ) )                                                                                  \n");				
				
			}
			sqlBuf.append(" select pbtc.id,pbtc.code,pbtc.name,pbtc.time,ec.name as coursetype,stat.stuNum,stat.totalMoney,stat.com,stat.pass from  ( select id,flag_coursetype,time,name,code,PASSROLE from pe_bzz_tch_course course where ");
			sqlBuf.append(" course.id = '" + courseId + "'");
			sqlBuf.append(" ) pbtc,( select a.courseid,a.stuNum,a.totalMoney,a.pass,b.com from ");
			sqlBuf.append(" ( select pbto.fk_course_id as courseid,count(pbtse.fk_stu_id) as stuNum,sum(to_number(nvl(course.time, 0))*"+classHourRate.getName()+") as totalMoney,  ");
			sqlBuf.append(" round(sum(case when pbtse.score_exam  >= nvl(course.passrole,0) then 1 else 0 end) / count(pbtse.fk_stu_id),3)*100||'%' as pass   ");
			sqlBuf.append(" from " + sqlElective.toString() + " pbtse,pr_bzz_tch_opencourse pbto, pe_bzz_tch_course course where pbtse.fk_tch_opencourse_id = pbto.id  ");
			sqlBuf.append(" and pbto.fk_course_id=course.id");
			sqlBuf.append(" and course.id = '" + courseId + "'");
			sqlBuf.append(" group  by  pbto.fk_course_id  ) a, ");
			sqlBuf.append(" ( select pbto.fk_course_id,round(sum(case when tcs.learn_status = 'COMPLETED' then 1 else 0 end) / count(tcs.student_id),3)*100||'%' as com from   ");
			sqlBuf.append(" "+ sqlTraining.toString()  +" tcs,pr_bzz_tch_opencourse pbto where tcs.course_id = pbto.id group by pbto.fk_course_id ) b where a.courseid = b.fk_course_id ) stat,enum_const ec  ");
			sqlBuf.append(" where pbtc.id = stat.courseid(+)      and ec.id = pbtc.flag_coursetype      ");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Page page = null;
		try {
			page = getGeneralService().getByPageSQLDBPool(countSQL.toString(),sqlBuf.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
			List<Map<String,String>> sourceList = page.getItems();
			List<CourseStudentStatVO> targetList = new ArrayList<CourseStudentStatVO>();
			for(Map<String,String> map : sourceList){
				CourseStudentStatVO temp = new CourseStudentStatVO();
				temp.setId(map.get("ID"));
				temp.setCode(map.get("CODE"));
				temp.setCom(map.get("COM"));
				temp.setCoursetype(map.get("COURSETYPE"));
				temp.setName(map.get("NAME"));
				temp.setStuNum(map.get("STUNUM"));
				temp.setTotalMoney(map.get("TOTALMONEY"));
				temp.setTime(map.get("TIME"));
				temp.setPass(map.get("PASS"));
				targetList.add(temp);
			}
			page.setItems(targetList);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 获得课程信息
	 * @author linjie
	 * @return
	 */
	public void getPeBzzTchCourseInfo() {
		HttpSession session = ServletActionContext.getRequest().getSession(); 
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");
		dc.createCriteria("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory");
		dc.add(Restrictions.eq("id", this.getId()));
		
		BigDecimal price = new BigDecimal(this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0").getName()).setScale(2, BigDecimal.ROUND_HALF_UP);
		//将课程单价放在session中，页面调用
		session.setAttribute("ClassHourRate", price);
		try {
			peBzzTchCourse = (PeBzzTchCourse)this.getGeneralService().getList(dc).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得列表学生信息
	 * @author linjie
	 * @return
	 */
	public void listStudent() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY); 
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select stu.true_name,\n" +
				"       stu.card_no,\n" + 
				"       stu.department,\n" + 
				"       stu.position,\n" + 
				"       o.payment_date,\n" + 
				"       tr.get_date,\n" + 
				"       c.end_date,\n" + 
				"       nvl(tr.percent, 0.0),\n" + 
				"		nvl(tr.learn_status, '未开始'),\n"+
				"       nvl(his.score, '0.0'),\n" + 
				"		c.PASSROLE\n"+
				"  from pr_bzz_tch_stu_elective t\n" + 
				" inner join pr_bzz_tch_opencourse op on t.fk_tch_opencourse_id = op.id\n" + 
				" inner join pe_bzz_tch_course c on c.id = op.fk_course_id\n" + 
				" inner join pe_bzz_student stu on stu.id = t.fk_stu_id\n");
		if(!us.getUserLoginType().equals("3")) {
			sqlBuf.append(" inner join pe_enterprise_manager man on man.fk_enterprise_id =\n" + 
			"                                         stu.fk_enterprise_id\n");
			sqlBuf.append(" inner join sso_user s on man.fk_sso_user_id =\n" + 
			"                                         s.id\n");
		}
		sqlBuf.append("  left join pe_bzz_order_info o on t.fk_order_id = o.id\n" + 
			"  left join training_course_student tr on tr.student_id = stu.id\n" + 
			"  left join onlinetest_course_paper pa on pa.testcourse_id = c.code\n" + 
			"  left join test_testpaper_history his on his.testpaper_id = pa.paper_id\n" + 
			" where c.id = '"+this.getId()+"'\n");
		if(!us.getUserLoginType().equals("3")) {
			sqlBuf.append(" and s.id= '"+us.getSsoUser().getId()+"'");
		}
		
		try {
			list = this.getGeneralService().getBySQL(sqlBuf.toString());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 是否完成
	 * @author linjie
	 * @return
	 */
	public boolean isCompleted(String isCompleted) {
		if(isCompleted.equals(StudyProgress.COMPLETED)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否通过
	 * @author linjie
	 * @return
	 */
	public boolean isPassed(String percent, String score) {
		double d_percent = Double.parseDouble(percent);
		double d_score = Double.parseDouble(score);
		if(d_score>=d_percent) {
			return true;
		}
		return false;
	}
	
	/**
	 * 查看学生详情
	 * @author linjie
	 * @return
	 */
	public String viewStudent() {
		this.getPeBzzTchCourseInfo();
		this.listStudent();
		return "viewStudent";
	}
	
	/**
	 * 查看课程详情
	 * @author linjie
	 * @return
	 */
	public String viewCourse() {
		this.getPeBzzTchCourseInfo();
		return "viewCourse";
	}

	/**
	 * 按课程查看学生
	 * @author linjie
	 * @return
	 */
	public String toCourseStudentSearch(){
		//加载课程列表
		String sql	= " select id,name,code from pe_bzz_tch_course pbtc where  pbtc.flag_isvalid='2' or pbtc.flag_canjoinbatch='40288acf3aaa56d5013aaa5b8ccc0001' order by code asc ";
		try{
			List<Object[]> list = this.getGeneralService().getBySQL(sql);
			this.setCouStuList(list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "toCourseStudentSearch";
	}

	public List<Object[]> getCouStuList() {
		return couStuList;
	}

	public void setCouStuList(List<Object[]> couStuList) {
		this.couStuList = couStuList;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
}
