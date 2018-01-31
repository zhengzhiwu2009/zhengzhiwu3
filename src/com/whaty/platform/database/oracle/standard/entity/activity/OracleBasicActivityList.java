package com.whaty.platform.database.oracle.standard.entity.activity;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import oracle.jdbc.OracleResultSet;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.basic.Courseware;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.activity.uniteexam.OracleUniteExamCourse;
import com.whaty.platform.database.oracle.standard.entity.activity.uniteexam.OracleUniteExamScore;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleExecutePlan;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleExecutePlanCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleExecutePlanCourseGroup;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleGrade;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSemester;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleTeachBook;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleTeachPlan;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleTeachPlanCourse;
import com.whaty.platform.database.oracle.standard.entity.fee.deal.OracleUserFeeReturnApply;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacher;
import com.whaty.platform.database.oracle.standard.test.exam.OracleBasicSequence;
import com.whaty.platform.entity.activity.BasicActivityList;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.activity.TeachClass;
import com.whaty.platform.entity.activity.score.ElectiveScoreType;
import com.whaty.platform.entity.activity.score.ScoreDef;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.entity.basic.TeachBook;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.fee.deal.FeeType;
import com.whaty.platform.entity.fee.deal.PayoutType;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.entity.user.TeacherEduInfo;
import com.whaty.platform.util.Address;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author Administrator
 * 
 */
public class OracleBasicActivityList implements BasicActivityList {

	// private String OPENCOURSESQL = "select
	// id,course_id,course_name,semester_id,examsequence,semester_name,credit,course_time,major_id,teachclass_id,assistance_note,assistance_release_status,assistance_title
	// from "
	// + "(select a.id,a.course_id,b.major_id,b.name as
	// course_name,a.semester_id as semester_id,a.examsequence,c.name as
	// semester_name,b.credit,b.course_time,e.id as
	// teachclass_id,e.assistance_note,e.assistance_release_status,e.assistance_title
	// from "
	// + "entity_course_active a,entity_course_info b,entity_semester_info
	// c,entity_teach_class e where a.course_id=b.id "
	// + "and a.semester_id=c.id and a.id=e.open_course_id) ";

//	private String OPENCOURSESQL = "select id,course_id,course_name,semester_id,examsequence,semester_name,credit,course_time,major_id,teachclass_id,assistance_note,assistance_release_status,assistance_title,cw_num from " +
//			"(select id,course_id,course_name,semester_id,examsequence,semester_name,credit,course_time,major_id,teachclass_id,assistance_note,assistance_release_status,assistance_title,nvl(cw_num,'0') as cw_num from "
//			+ "(select a.id,a.course_id,b.major_id,b.name as course_name,a.semester_id as semester_id,a.examsequence,c.name as semester_name,b.credit,b.course_time,e.id as teachclass_id,e.assistance_note,e.assistance_release_status,e.assistance_title from "
//			+ "entity_course_active a,entity_course_info b,entity_semester_info c,entity_teach_class e where a.course_id=b.id "
//			+ "and a.semester_id=c.id and a.id=e.open_course_id) x,(select count(id) as cw_num,teachclass_id as tc_id from PR_TCH_OPENCOURSE_COURSEWARE group by teachclass_id) y where x.teachclass_id=y.tc_id(+)) ";

	//bjsy2
	private String OPENCOURSESQL ="select id ,course_id,course_name,credit,course_time,semester_id,semester_name,examsequence from (select op.id,c.id as course_id,c.name as course_name,c.credit as credit,c.course_time as course_time,se.id as semester_id,se.name as semester_name, ex.id as examsequence from pe_semester se,pe_examno ex ,pr_tch_opencourse op,pe_tch_course c where se.id=ex.fk_semester_id and op.fk_examno_id=ex.id and op.fk_course_id =c.id)";
	
	
	public static String TEACHCLASSSQL = "select teachclass_id, teachclass_name, id,course_id,course_name,semester_id,semester_name,credit,course_time,major_id,assistance_note,assistance_release_status,assistance_title from "
			+ "(select e.id as teachclass_id, e.name as teachclass_name, a.id,a.course_id,b.major_id,b.name as course_name,a.semester_id as semester_id,c.name as semester_name,b.credit,b.course_time,e.assistance_note,e.assistance_release_status,e.assistance_title from "
			+ "entity_course_active a,entity_course_info b,entity_semester_info c,entity_teach_class e where a.course_id=b.id "
			+ "and a.semester_id=c.id and a.id=e.open_course_id) ";

	private String OPENCOURSESQLWithEleNum = "select id,course_id,course_name,semester_id,semester_name,credit,course_time,"
			+ "major_id,assistance_note,assistance_release_status,assistance_title,elective_num,e_id,teacher_class_id,cw_num from "
			+	"(select id,course_id,course_name,semester_id,semester_name,credit,course_time,"
			+ "major_id,assistance_note,assistance_release_status,assistance_title,nvl(elective_num,0) as elective_num,e_id,teacher_class_id,nvl(cw_num,0) as cw_num from "
			+ "(select a.id,a.course_id,b.major_id,b.name as course_name,a.semester_id as semester_id,c.name as semester_name,"
			+ "b.credit,b.course_time,e.id as e_id,e.assistance_note,e.assistance_release_status,"
			+ "e.assistance_title, e.id as teacher_class_id from entity_course_active a,entity_course_info b,entity_semester_info c,"
			+ "entity_teach_class e where a.course_id=b.id and a.semester_id=c.id and a.id=e.open_course_id)x,"
			+ "(select count(id) as elective_num,teachclass_id from entity_elective group by teachclass_id)y,(select count(id) as cw_num,teachclass_id from PR_TCH_OPENCOURSE_COURSEWARE group by teachclass_id)z where x.e_id = y.teachclass_id(+) and x.e_id=z.teachclass_id(+)) where 1=1 ";

	private String USERFEERETURNAPPLY = "select id,user_id,amount,apply_time,"
			+ "ischecked,check_time,isreturned,"
			+ "return_time,note from (select id,user_id,amount,to_char(apply_time,'yyyy-mm-dd hh24:mi:ss') as apply_time,"
			+ "ischecked,to_char(check_time,'yyyy-mm-dd hh24:mi:ss') as check_time,isreturned,"
			+ "to_char(return_time,'yyyy-mm-dd hh24:mi:ss') as return_time,note "
			+ "from entity_userfee_returnapply)";

	private String getRegStudentSql(String con, String orderby,
			Semester semester) {
		String sql = "";
		if (con == null)
			con = "";
		if (orderby == null)
			orderby = "";
		if (semester == null)
			sql = "select id,name,id_card,m_name,g_name,e_name,s_name,s_id,reg_no,se_name "
					+ "from (select u.id as id,u.name as name,u.id_card as id_card,u.reg_no as reg_no,m.name as m_name,g.name as g_name,e.name as e_name,s.name as s_name,s.id as s_id,se.name as se_name "
					+ " from (select id,name,major_id,grade_id,site,edu_type_id,id_card,reg_no from entity_user_info where isgraduated = '0' and status = '0000') u,entity_register_info r,entity_semester_info se"
					+ ","
					+ "(select id,name from entity_major_info where id<>'0') m"
					+ ","
					+ "(select id,name from entity_grade_info) g"
					+ ","
					+ "(select id,name from entity_edu_type) e"
					+ ","
					+ "(select id,name from entity_site_info) s"
					+ " where u.major_id = m.id and u.grade_id = g.id and u.site = s.id and u.edu_type_id = e.id and u.id=r.user_id and r.semester_id=se.id "
					+ con + ") " + orderby;
		else
			sql = "select id,name,id_card,m_name,g_name,e_name,s_name,s_id,reg_no,se_name "
					+ "from (select u.id as id,u.name as name,u.id_card as id_card,u.reg_no as reg_no,m.name as m_name,g.name as g_name,e.name as e_name,s.name as s_name,s.id as s_id,se.name as se_name "
					+ " from (select id,name,major_id,grade_id,site,edu_type_id,id_card,reg_no from entity_user_info where isgraduated = '0' and status = '0000') u,entity_register_info r,(select id from entity_semester_info where id='"
					+ semester.getId()
					+ "') se"
					+ ","
					+ "(select id,name from entity_major_info where id<>'0') m"
					+ ","
					+ "(select id,name from entity_grade_info) g"
					+ ","
					+ "(select id,name from entity_edu_type) e"
					+ ","
					+ "(select id,name from entity_site_info) s"
					+ " where u.major_id = m.id and u.grade_id = g.id and u.site = s.id and u.edu_type_id = e.id and u.id=r.user_id and r.semester_id=se.id "
					+ con + ") " + orderby;
		return sql;
	}

	private String getUnRegStudentSql(String con, String orderby,
			Semester semester) {
		String sql = "";
		if (con == null)
			con = "";
		if (orderby == null)
			orderby = "";
		if (semester == null)
			sql = "select id,name,id_card,m_name,g_name,e_name,s_name,s_id,reg_no,se_name "
					+ "from (select u.id as id,u.name as name,u.id_card as id_card,u.reg_no as reg_no,m.name as m_name,g.name as g_name,e.name as e_name,s.name as s_name,s.id as s_id,se.name as se_name "
					+ " from (select id,name,major_id,grade_id,site,edu_type_id,id_card,reg_no from entity_user_info where isgraduated = '0' and status = '0000') u,entity_register_info r,entity_semester_info se"
					+ ","
					+ "(select id,name from entity_major_info where id<>'0') m"
					+ ","
					+ "(select id,name from entity_grade_info) g"
					+ ","
					+ "(select id,name from entity_edu_type) e"
					+ ","
					+ "(select id,name from entity_site_info) s"
					+ " where u.major_id = m.id and u.grade_id = g.id and u.site = s.id and u.edu_type_id = e.id and u.id=r.user_id and r.semester_id!=se.id "
					+ con + ") " + orderby;
		else
			sql = "select id,name,id_card,m_name,g_name,e_name,s_name,s_id,reg_no,se_name "
					+ "from (select u.id as id,u.name as name,u.id_card as id_card,u.reg_no as reg_no,m.name as m_name,g.name as g_name,e.name as e_name,s.name as s_name,s.id as s_id,se.name as se_name "
					+ " from (select id,name,major_id,grade_id,site,edu_type_id,id_card,reg_no from entity_user_info where isgraduated = '0' and status = '0000') u,entity_register_info r,(select id from entity_semester_info where id='"
					+ semester.getId()
					+ "') se"
					+ ","
					+ "(select id,name from entity_major_info where id<>'0') m"
					+ ","
					+ "(select id,name from entity_grade_info) g"
					+ ","
					+ "(select id,name from entity_edu_type) e"
					+ ","
					+ "(select id,name from entity_site_info) s"
					+ " where u.major_id = m.id and u.grade_id = g.id and u.site = s.id and u.edu_type_id = e.id and u.id=r.user_id and r.semester_id!=se.id "
					+ con + ") " + orderby;
		return sql;
	}

	public List searchUnConfirmElective(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderproperty;
		}
		String SQL = "select id,student_id,status,student_name,teachclass_id,teachclass_name,course_id,course_name,major_id,edu_type_id,site_id,grade_id,semester_id from "
				+ "(select a.id,a.student_id as student_id,a.status as status,b.name as student_name,a.teachclass_id "
				+ "as teachclass_id,c.name as teachclass_name,d.semester_id,d.course_id as course_id,b.major_id,b.edu_type_id,b.site_id,b.grade_id"
				+ "e.name as course_name from (select id,student_id,status,teachclass_id from entity_elective where "
				+ "')a,entity_student_info b,"
				+ "entity_teach_class c,entity_course_active d,entity_course_info e where a.student_id=b.id "
				+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id) ";
		String sql = SQL
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		MyResultSet rs = null;
		ArrayList electives = null;
		try {
			db = new dbpool();
			electives = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleElective elective = new OracleElective();
				elective.setId(rs.getString("id"));
				OracleStudent astudent = new OracleStudent();
				astudent.setId(rs.getString("student_id"));
				astudent.setName(rs.getString("u_name"));
				elective.setStudent(astudent);
				OracleTeachClass newTeachClass = new OracleTeachClass();
				OracleOpenCourse openCourse = new OracleOpenCourse();
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
//				openCourse.setCourse(course);
				newTeachClass.setId(rs.getString("teachclass_id"));
				newTeachClass.setName(rs.getString("teachclass_name"));
				newTeachClass.setOpenCourse(openCourse);
				elective.setTeachClass(newTeachClass);
				elective.setElectiveStatus(rs.getString("status"));
				electives.add(elective);
			}
		} catch (Exception e) {
			EntityLog.setError("OracleUniteExamScore@Method:searchUnConfirmElective(String id) Error! (SQL = "+sql+")");
		} finally {
			db.close(rs);
			db = null;
		}
		return electives;
	}

	public int searchUnConfirmElectiveNum(List searchproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String SQL = "select id,student_id,status,student_name,teachclass_id,teachclass_name,course_id,course_name,major_id,edu_type_id,site_id,grade_id,semester_id from "
				+ "(select a.id,a.student_id as student_id,a.status as status,b.name as student_name,a.teachclass_id "
				+ "as teachclass_id,c.name as teachclass_name,d.semester_id,d.course_id as course_id,b.major_id,b.edu_type_id,b.site_id,b.grade_id"
				+ "e.name as course_name from (select id,student_id,status,teachclass_id from entity_elective where "
				+ "')a,entity_student_info b,"
				+ "entity_teach_class c,entity_course_active d,entity_course_info e where a.student_id=b.id "
				+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id) ";
		String sql = SQL + Conditions.convertToCondition(searchproperty, null);

		return db.countselect(sql);
	}

	public List searchElective(Page page, List searchproperty,
			List orderproperty, Student student) throws PlatformException {
		dbpool db = new dbpool();
		String SQL;
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderproperty;
		}
		if (student != null)
			// SQL = "select
			// id,student_id,status,student_name,teachclass_id,teachclass_name,course_id,course_name,semester_id
			// from "
			SQL = "select distinct id,teachclass_id,elective_status,expend_score_student_status,course_id,course_name,course_credit,course_time,open_course_id,semester_id,semester_name,semester_start_elective,semester_end_elective,usual_score,test_score,exam_score,total_score,expend_score,total_expend_score,usual_score_status,exam_score_status,total_score_status,expend_score_status,experiment_score from "
					+ "(select a.id,a.status as elective_status,a.student_id as student_id,c.open_course_id,a.status as status,b.name as student_name,a.teachclass_id "
					+ "as teachclass_id,b.site_id, b.major_id, b.grade_id, b.edutype_id,c.name as teachclass_name,d.course_id as course_id,d.semester_id as semester_id,"
					+ "f.name as semester_name,to_char(f.start_elective,'yyyy-mm-dd') as semester_start_elective, "
					+ "to_char(f.end_elective, 'yyyy-mm-dd') as semester_end_elective, e.name as course_name,e.credit as course_credit,e.course_time as course_time, "
					+ "a.usual_score as usual_score,a.exam_score as exam_score,a.total_score as total_score,a.expend_score as expend_score,a.total_expend_score as total_expend_score, "
					+ "a.usual_score_status as usual_score_status,a.exam_score_status as exam_score_status,a.total_score_status as total_score_status,a.expend_score_status as expend_score_status,a.expend_score_student_status as expend_score_student_status,a.test_score as test_score,a.experiment_score as experiment_score from (select id,student_id,status,teachclass_id,usual_score,exam_score,total_score,expend_score,total_expend_score,expend_score_student_status,expend_score_status,total_score_status,exam_score_status,usual_score_status,test_score,experiment_score from entity_elective where "
					+ "student_id='"
					+ student.getId()
					+ "')a,entity_student_info b, "
					+ "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f where a.student_id=b.id "
					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id ) ";

		else

			SQL = "select distinct id,teachclass_id,elective_status,course_id,course_name,course_credit,course_time,open_course_id,semester_id,semester_name,semester_start_elective,semester_end_elective,usual_score,test_score,exam_score,total_score,expend_score,total_expend_score,usual_score_status,exam_score_status,total_score_status,expend_score_status,expend_score_student_status,expend_score_status,experiment_score  from "
					+ "(select a.id,a.status as elective_status,a.student_id as student_id,c.open_course_id,a.status as status,b.name as student_name,a.teachclass_id "
					+ "as teachclass_id,b.site_id, b.major_id, b.grade_id, b.edutype_id,c.name as teachclass_name,d.course_id as course_id,d.semester_id as semester_id, "
					+ "f.name as semester_name, to_char(f.start_elective,'yyyy-mm-dd') as semester_start_elective,a.test_score as test_score, "
					+ "to_char(f.end_elective, 'yyyy-mm-dd') as semester_end_elective, e.name as course_name,e.credit as course_credit,e.course_time as course_time,a.usual_score as usual_score,a.exam_score as exam_score,a.total_score as total_score,a.expend_score as expend_score,a.total_expend_score as total_expend_score,a.usual_score_status as usual_score_status,a.exam_score_status as exam_score_status,a.total_score_status as total_score_status,a.expend_score_status as expend_score_status,a.expend_score_student_status as expend_score_student_status,a.experiment_score  from entity_elective a, entity_student_info b,"
					+ "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f where a.student_id=b.id "
					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id  ) ";

		String sql = SQL
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		MyResultSet rs = null;
		ArrayList electives = null;
		try {
			db = new dbpool();
			electives = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleElective elective = new OracleElective();
				OracleTeachClass newTeachClass = new OracleTeachClass();
				OracleOpenCourse openCourse = new OracleOpenCourse();
				// OracleSemester semester = new
				// OracleSemester(rs.getString("semester_id"));
				OracleSemester semester = new OracleSemester();
				OracleCourse course = new OracleCourse();
				elective.setId(rs.getString("id"));
				elective.setUsualScore(rs.getString("usual_score"));
				elective.setTest_score(rs.getString("test_score"));
				elective.setExamScore(rs.getString("exam_score"));
				elective.setTotalScore(rs.getString("total_score"));
				elective.setExpendScore(rs.getString("expend_score"));
				elective.setTotal_expend_score(rs
						.getString("total_expend_score"));
				elective
						.setUsualScoreStatus(rs.getString("usual_score_status"));
				elective.setExamScoreStatus(rs.getString("exam_score_status"));
				elective
						.setTotalScoreStatus(rs.getString("total_score_status"));
				elective.setExpendScoreStatus(rs
						.getString("expend_score_status"));
				elective.setExpendScoreStudentStatus(rs
						.getString("expend_score_student_status"));
				elective.setElectiveStatus(rs.getString("elective_status"));
				elective.setExperimentScore(rs.getString("experiment_score"));

				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCredit(rs.getString("course_credit"));
				course.setCourse_time(rs.getString("course_time"));
				semester.setId(rs.getString("semester_id"));
				semester.setName(rs.getString("semester_name"));
				semester.setStart_elective(rs
						.getString("semester_start_elective"));
				semester.setEnd_elective(rs.getString("semester_end_elective"));
//				openCourse.setCourse(course);
				openCourse.setId(rs.getString("open_course_id"));
//				openCourse.setSemester(semester);
				newTeachClass.setOpenCourse(openCourse);
				newTeachClass.setId(rs.getString("teachclass_id"));
				elective.setTeachClass(newTeachClass);
				electives.add(elective);
			}
		} catch (Exception e) {
			EntityLog.setError("OracleUniteExamScore@Method:searchElective(Page page, List searchproperty,List orderproperty, Student student) Error! (SQL = "+sql+")");
		} finally {
			db.close(rs);
			db = null;
		}
		return electives;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.activity.BasicActivityList#confirmElective(javax.servlet.http.HttpServletRequest)
	 */
	public int confirmElective(HttpServletRequest request) {
		String[] electiveIds = request.getParameterValues("id");
		String electiveIdStr = "";
		ArrayList sqlgroup = new ArrayList();

		dbpool db = new dbpool();

		for (int i = 0; i < electiveIds.length; i++) {
			electiveIdStr += electiveIds[i] + ",";
		}

		if (electiveIdStr.length() > 0)
			electiveIdStr = electiveIdStr.substring(0,
					electiveIdStr.length() - 1);

		String sql = "update entity_elective set status = '1' where id in ("
				+ electiveIdStr + ")";
		sqlgroup.add(sql);
		String feeSql = "insert into entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note,teachclass_id) "
				+ "select s_entity_fee_id.nextval,student_id,value,'"
				+ FeeType.CREDIT
				+ "','"
				+ PayoutType.CONSUME
				+ "','1',note,teachclass_id "
				+ "from (select -(e.credit* f.creditfee) as value,f.student_id as student_id,concat(concat(concat(concat(concat(concat('ѡ�� �γ���ƣ�','('||e.course_id||')'||e.course_name),'���γ�ѧ�֣�'),e.credit),'��ѧ�ֱ�׼��ÿѧ�֣�'),f.creditfee),'Ԫ') as note,teachclass_id "
				+ "from (select nvl(a.credit,b.credit) as credit,a.course_id,b.course_name,b.student_id,b.teachclass_id from "
				+ "(select tc.credit,tc.course_id,c.name as course_name  from entity_course_active a,"
				+ "entity_course_info c,entity_teachplan_course tc,entity_teachplan_info i,entity_student_info s,"
				+ "entity_elective e,entity_teach_class t where e.id in ("
				+ electiveIdStr
				+ ")  and tc.course_id = c.id and a.course_id = c.id "
				+ "and i.edutype_id = s.edutype_id and i.major_id = s.major_id and i.grade_id = s.grade_id and s.id = e.student_id and "
				+ "tc.teachplan_id = i.id and a.id = t.open_course_id and t.id = e.teachclass_id)a,(select  c.name as course_name,a.course_id as course_id,c.credit "
				+ ",e.student_id,t.id as teachclass_id from entity_course_info c,entity_course_active a,entity_teach_class t,entity_elective e where a.id = t.open_course_id and "
				+ "a.course_id = c.id and e.teachclass_id = t.id and e.id in ("
				+ electiveIdStr
				+ "))b where b.course_id = a.course_id(+))e,"
				+ "(select distinct type1_value as creditfee,e.student_id from entity_userfee_level f,entity_elective e "
				+ "where f.user_id = e.student_id and e.id in ("
				+ electiveIdStr + "))f  where e.student_id = f.student_id) ";
//		
//		String feeSql = "insert into entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note,teachclass_id) "
//			+ "select s_entity_fee_id.nextval,'"
//			+ studentId
//			+ "',value,'"
//			+ FeeType.CREDIT
//			+ "','"
//			+ PayoutType.CONSUME
//			+ "','1',note,teachclass_id "
//			+ "from (select -(e.credit* f.creditfee) as value,concat(concat(concat(concat(concat(concat('ѡ�� �γ���ƣ�',e.course_name),'���γ�ѧ�֣�'),e.credit),'��ѧ�ֱ�׼��ÿѧ�֣�'),f.creditfee),'Ԫ') as note,teachclass_id "
//			+ "from (select nvl(a.credit,b.credit) as credit,a.course_id,a.course_name,a.teachclass_id from (select tc.credit,tc.course_id,c.name as course_name ,etc.id as teachclass_id from "
//			+ "entity_course_active a,entity_course_info c,entity_teachplan_course tc,entity_teachplan_info i,entity_student_info s,  entity_teach_class      etc where a.id in ("
//			+ select_opencourse_str
//			+ ")  "
//			+ "and tc.course_id = c.id and a.course_id = c.id and i.edutype_id = s.edutype_id and i.major_id = s.major_id and i.grade_id = s.grade_id  and etc.open_course_id = a.id and s.id = '"
//			+ studentId
//			+ "' "
//			+ "and tc.teachplan_id = i.id)a,(select a.course_id as course_id,c.credit  from entity_course_info c,entity_course_active a where a.id in ("
//			+ select_opencourse_str
//			+ ") and a.course_id = c.id)b "
//			+ "where b.course_id = a.course_id(+))e,(select type1_value as creditfee from entity_userfee_level where user_id = '"
//			+ studentId + "')f) ";
		sqlgroup.add(feeSql);
		return db.executeUpdateBatch(sqlgroup);
	}

	public int rejectElective(HttpServletRequest request) {
		String[] electiveIds = request.getParameterValues("id");
		String electiveIdStr = "";
		dbpool db = new dbpool();
		String sql = "";
		List sqlgroup = new ArrayList();
		String note = "";
		for (int i = 0; i < electiveIds.length; i++) {
			note = request.getParameter("note_" + electiveIds[i]);
			sql = "insert into entity_elective_reject (id,old_id,student_id,teachclass_id,note) "
					+ "select to_char(s_elective_reject_id.nextval),id,student_id,teachclass_id,'"
					+ note
					+ "' from entity_elective where id ='"
					+ electiveIds[i] + "'";
			sqlgroup.add(sql);
		}
		for (int i = 0; i < electiveIds.length; i++)
			electiveIdStr += ",'" + electiveIds[i] + "'";
		if (electiveIdStr.length() > 0)
			electiveIdStr = electiveIdStr.substring(1);
		sql = "delete from entity_elective where id in (" + electiveIdStr + ")";
 		sqlgroup.add(sql);
		return db.executeUpdateBatch(sqlgroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchElective(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List)
	 */
	public List searchElective(Page page, List searchproperty,
			List orderproperty, Student student, boolean confirmStatus)
			throws PlatformException {
		dbpool db = new dbpool();
		String SQL;
		String statusCondition = "";
		if (confirmStatus) {
			statusCondition = " and a.status = '1'";
		} else {
			statusCondition = " and a.status = '0'";
		}
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderproperty;
		}
//		if (student != null)
//			SQL = "select distinct id,teachclass_id,student_id,student_name,reg_no,major_id,edutype_id,grade_id,elective_status,expend_score_student_status,expend_score_status,course_id,course_name,course_credit,course_time,"
//					+ "open_course_id,semester_id,semester_name,semester_start_elective,semester_end_elective,usual_score,exam_score,total_score,usual_score_status,exam_score_status,total_score_status from "
//					+ "(select a.id,a.status as elective_status,a.student_id as student_id,c.open_course_id,a.status as status,a.teachclass_id "
//					+ "as teachclass_id,b.site_id,b.reg_no,b.name as student_name, b.major_id, b.grade_id, b.edutype_id,c.name as teachclass_name,d.course_id as course_id,d.semester_id as semester_id,"
//					+ "f.name as semester_name,to_char(f.start_elective,'yyyy-mm-dd') as semester_start_elective, "
//					+ "to_char(f.end_elective, 'yyyy-mm-dd') as semester_end_elective, e.name as course_name,h.credit as course_credit,h.coursetime as course_time, "
//					+ "a.usual_score as usual_score,a.exam_score as exam_score,a.total_score as total_score, "
//					+ "a.usual_score_status as usual_score_status,a.exam_score_status as exam_score_status,a.total_score_status as total_score_status,a.expend_score_status as expend_score_status,"
//					+ "a.expend_score_student_status as expend_score_student_status from (select id,student_id,status,teachclass_id,usual_score,exam_score,total_score,expend_score_student_status,"
//					+ "expend_score_status,total_score_status,exam_score_status,usual_score_status from entity_elective a where "
//					+ "a.student_id='"
//					+ student.getId()
//					+ "' "
//					+ statusCondition
//					+ ")a,entity_student_info b, "
//					+ "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f,entity_teachplan_info g,entity_teachplan_course h where a.student_id=b.id "
//					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id and b.major_id=g.major_id(+) and b.grade_id=g.grade_id(+) and b.edutype_id=g.edutype_id(+) and g.id=h.teachplan_id(+) and h.course_id=e.id(+)) ";
//
//		else
//
//			SQL = "select distinct id,teachclass_id,student_id,student_name,reg_no,major_id,edutype_id,grade_id,elective_status,course_id,course_name,course_credit,course_time,open_course_id,semester_id,semester_name,"
//					+ "semester_start_elective,semester_end_elective,usual_score,exam_score,total_score,usual_score_status,exam_score_status,total_score_status,"
//					+ "expend_score_status,expend_score_student_status  from "
//					+ "(select a.id,a.status as elective_status,a.student_id as student_id,c.open_course_id,a.status as status,a.teachclass_id "
//					+ "as teachclass_id,b.site_id,b.reg_no,b.name as student_name, b.major_id, b.grade_id, b.edutype_id,c.name as teachclass_name,d.course_id as course_id,d.semester_id as semester_id, "
//					+ "f.name as semester_name, to_char(f.start_elective,'yyyy-mm-dd') as semester_start_elective, "
//					+ "to_char(f.end_elective, 'yyyy-mm-dd') as semester_end_elective, e.name as course_name,e.credit as course_credit,e.course_time as course_time,"
//					+ "a.usual_score as usual_score,a.exam_score as exam_score,a.total_score as total_score,a.usual_score_status as usual_score_status,"
//					+ "a.exam_score_status as exam_score_status,a.total_score_status as total_score_status,a.expend_score_status as expend_score_status,a.expend_score_student_status "
//					+ "as expend_score_student_status  from entity_elective a, entity_student_info b,"
//					+ "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f where a.student_id=b.id "
//					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id"
//					+ statusCondition + ") ";
        if (student != null)
            SQL = "select distinct id,teachclass_id,student_id,student_name,reg_no,major_id,edutype_id,grade_id,elective_status,expend_score_student_status,expend_score_status,course_id,course_name,course_credit,course_time,"
                    + "open_course_id,semester_id,semester_name,semester_start_elective,semester_end_elective,usual_score,exam_score,total_score,usual_score_status,exam_score_status,total_score_status from "
                    + "(select a.id,a.status as elective_status,a.student_id as student_id,c.open_course_id,a.status as status,a.teachclass_id "
                    + "as teachclass_id,b.site_id,b.reg_no,b.name as student_name, b.major_id, b.grade_id, b.edutype_id,c.name as teachclass_name,d.course_id as course_id,d.semester_id as semester_id,"
                    + "f.name as semester_name,to_char(f.start_elective,'yyyy-mm-dd') as semester_start_elective, "
                    + "to_char(f.end_elective, 'yyyy-mm-dd') as semester_end_elective, e.name as course_name,e.credit as course_credit,e.course_time as course_time, "
                    + "a.usual_score as usual_score,a.exam_score as exam_score,a.total_score as total_score, "
                    + "a.usual_score_status as usual_score_status,a.exam_score_status as exam_score_status,a.total_score_status as total_score_status,a.expend_score_status as expend_score_status,"
                    + "a.expend_score_student_status as expend_score_student_status from (select id,student_id,status,teachclass_id,usual_score,exam_score,total_score,expend_score_student_status,"
                    + "expend_score_status,total_score_status,exam_score_status,usual_score_status from entity_elective a where "
                    + "a.student_id='"
                    + student.getId()
                    + "' "
                    + statusCondition
                    + ")a,entity_student_info b, "
                    + "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f where a.student_id=b.id "
                    + "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id ) ";

        else

            SQL = "select distinct id,teachclass_id,student_id,student_name,reg_no,major_id,edutype_id,grade_id,elective_status,course_id,course_name,course_credit,course_time,open_course_id,semester_id,semester_name,"
                    + "semester_start_elective,semester_end_elective,usual_score,exam_score,total_score,usual_score_status,exam_score_status,total_score_status,"
                    + "expend_score_status,expend_score_student_status  from "
                    + "(select a.id,a.status as elective_status,a.student_id as student_id,c.open_course_id,a.status as status,a.teachclass_id "
                    + "as teachclass_id,b.site_id,b.reg_no,b.name as student_name, b.major_id, b.grade_id, b.edutype_id,c.name as teachclass_name,d.course_id as course_id,d.semester_id as semester_id, "
                    + "f.name as semester_name, to_char(f.start_elective,'yyyy-mm-dd') as semester_start_elective, "
                    + "to_char(f.end_elective, 'yyyy-mm-dd') as semester_end_elective, e.name as course_name,e.credit as course_credit,e.course_time as course_time,"
                    + "a.usual_score as usual_score,a.exam_score as exam_score,a.total_score as total_score,a.usual_score_status as usual_score_status,"
                    + "a.exam_score_status as exam_score_status,a.total_score_status as total_score_status,a.expend_score_status as expend_score_status,a.expend_score_student_status "
                    + "as expend_score_student_status  from entity_elective a, entity_student_info b,"
                    + "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f where a.student_id=b.id "
                    + "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id " 
                    + statusCondition + ") ";

        
        
		String sql = SQL
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		MyResultSet rs = null;
		ArrayList electives = null;
		try {
			db = new dbpool();
			electives = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleElective elective = new OracleElective();
				OracleTeachClass newTeachClass = new OracleTeachClass();
				OracleOpenCourse openCourse = new OracleOpenCourse();
				OracleSemester semester = new OracleSemester();
				OracleCourse course = new OracleCourse();
				OracleStudent astudent = new OracleStudent();
				StudentEduInfo studentInfo = new StudentEduInfo();
				astudent.setId(rs.getString("student_id"));
				astudent.setName(rs.getString("student_name"));
				studentInfo.setReg_no(rs.getString("reg_no"));
				astudent.setStudentInfo(studentInfo);
				elective.setStudent(astudent);
				elective.setId(rs.getString("id"));
				elective.setUsualScore(rs.getString("usual_score"));
				elective.setExamScore(rs.getString("exam_score"));
				elective.setTotalScore(rs.getString("total_score"));
				elective
						.setUsualScoreStatus(rs.getString("usual_score_status"));
				elective.setExamScoreStatus(rs.getString("exam_score_status"));
				elective.setExamScoreStatus(rs.getString("total_score_status"));
				elective.setExpendScoreStatus(rs
						.getString("expend_score_status"));
				elective.setExpendScoreStudentStatus(rs
						.getString("expend_score_student_status"));
				elective.setElectiveStatus(rs.getString("elective_status"));

				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCredit(rs.getString("course_credit"));
				course.setCourse_time(rs.getString("course_time"));
				semester.setId(rs.getString("semester_id"));
				semester.setName(rs.getString("semester_name"));
				semester.setStart_elective(rs
						.getString("semester_start_elective"));
				semester.setEnd_elective(rs.getString("semester_end_elective"));
//				openCourse.setCourse(course);
				openCourse.setId(rs.getString("open_course_id"));
//				openCourse.setSemester(semester);
				newTeachClass.setOpenCourse(openCourse);
				newTeachClass.setId(rs.getString("teachclass_id"));
				elective.setTeachClass(newTeachClass);
				electives.add(elective);
			}
		} catch (Exception e) {
			EntityLog.setError("OracleUniteExamScore@Method:searchElective(Page page, List searchproperty,List orderproperty, Student student, boolean confirmStatus) Error! (SQL = "+sql+")");
		} finally {
			db.close(rs);
			db = null;
		}
		return electives;
	}

	public int searchElectiveNum(List searchproperty, Student student,
			boolean confirmStatus) throws PlatformException {
		dbpool db = new dbpool();
		String SQL;
		String statusCondition = "";
		if (confirmStatus) {
			statusCondition = " and a.status = '1' ";
		} else {
			statusCondition = " and a.status = '0' ";
		}
		if (student != null)
			SQL = "select distinct id,teachclass_id,student_id,student_name,reg_no,major_id,edutype_id,grade_id,elective_status,expend_score_student_status,expend_score_status,course_id,course_name,course_credit,course_time,"
					+ "open_course_id,semester_id,semester_name,semester_start_elective,semester_end_elective,usual_score,exam_score,total_score,usual_score_status,exam_score_status,total_score_status from "
					+ "(select a.id,a.status as elective_status,a.student_id as student_id,c.open_course_id,a.status as status,a.teachclass_id "
					+ "as teachclass_id,b.site_id,b.reg_no,b.name as student_name, b.major_id, b.grade_id, b.edutype_id,c.name as teachclass_name,d.course_id as course_id,d.semester_id as semester_id,"
					+ "f.name as semester_name,to_char(f.start_elective,'yyyy-mm-dd') as semester_start_elective, "
					+ "to_char(f.end_elective, 'yyyy-mm-dd') as semester_end_elective, e.name as course_name,e.credit as course_credit,e.course_time as course_time, "
					+ "a.usual_score as usual_score,a.exam_score as exam_score,a.total_score as total_score, "
					+ "a.usual_score_status as usual_score_status,a.exam_score_status as exam_score_status,a.total_score_status as total_score_status,a.expend_score_status as expend_score_status,"
					+ "a.expend_score_student_status as expend_score_student_status from (select id,student_id,status,teachclass_id,usual_score,exam_score,total_score,expend_score_student_status,"
					+ "expend_score_status,total_score_status,exam_score_status,usual_score_status from entity_elective a where "
					+ "a.student_id='"
					+ student.getId()
					+ "' "
					+ statusCondition
					+ ")a,entity_student_info b, "
					+ "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f,entity_major_info g where a.student_id=b.id "
					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id) ";

		else

			SQL = "select distinct id,teachclass_id,student_id,student_name,reg_no,major_id,edutype_id,grade_id,elective_status,course_id,course_name,course_credit,course_time,open_course_id,semester_id,semester_name,"
					+ "semester_start_elective,semester_end_elective,usual_score,exam_score,total_score,usual_score_status,exam_score_status,total_score_status,"
					+ "expend_score_status,expend_score_student_status  from "
					+ "(select a.id,a.status as elective_status,a.student_id as student_id,c.open_course_id,a.status as status,a.teachclass_id "
					+ "as teachclass_id,b.site_id,b.reg_no,b.name as student_name, b.major_id, b.grade_id, b.edutype_id,c.name as teachclass_name,d.course_id as course_id,d.semester_id as semester_id, "
					+ "f.name as semester_name, to_char(f.start_elective,'yyyy-mm-dd') as semester_start_elective, "
					+ "to_char(f.end_elective, 'yyyy-mm-dd') as semester_end_elective, e.name as course_name,e.credit as course_credit,e.course_time as course_time,"
					+ "a.usual_score as usual_score,a.exam_score as exam_score,a.total_score as total_score,a.usual_score_status as usual_score_status,"
					+ "a.exam_score_status as exam_score_status,a.total_score_status as total_score_status,a.expend_score_status as expend_score_status,a.expend_score_student_status "
					+ "as expend_score_student_status  from entity_elective a, entity_student_info b,"
					+ "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f where a.student_id=b.id "
					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id"
					+ statusCondition + ") ";

		String sql = SQL + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List searchElectiveStudents(Page page, List searchproperty,
			List orderproperty, List searchproperty1, boolean confirmStatus)
			throws PlatformException {
		dbpool db = new dbpool();
		String statusCondition = "";
		if (confirmStatus) {
			statusCondition = "and a.status = '1'";
		} else {
			statusCondition = "and a.status = '0'";
		}
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = "select id,name,reg_no,site_id,site_name,major_id,major_name,edutype_id,edutype_name,grade_id,grade_name from "
				+ "(select a.id,a.name,a.reg_no,a.site_id,b.name as site_name,a.major_id,c.name as major_name,"
				+ "a.edutype_id,d.name as edutype_name,a.grade_id,e.name as grade_name from entity_student_info a,"
				+ "entity_site_info b,entity_major_info c,entity_edu_type d,entity_grade_info e,"
				+ "(select distinct a.student_id from entity_elective a,entity_teach_class b,entity_course_active c,"
				+ "entity_student_info d where a.student_id=d.id and a.teachclass_id = b.id and b.open_course_id = c.id "
				+ statusCondition
				+ Conditions.convertToAndCondition(searchproperty1, null)
				+ ") f "
				+ "where a.site_id=b.id and a.major_id=c.id and a.edutype_id=d.id and a.grade_id=e.id "
				+ "and a.id=f.student_id  "
				+ Conditions.convertToAndCondition(searchproperty,
						orderproperty) + ")";
		// EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList students = null;
		try {
			db = new dbpool();
			students = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				StudentEduInfo studentInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				studentInfo.setReg_no(rs.getString("reg_no"));
				studentInfo.setSite_id(rs.getString("site_id"));
				studentInfo.setSite_name(rs.getString("site_name"));
				studentInfo.setMajor_id(rs.getString("major_id"));
				studentInfo.setMajor_name(rs.getString("major_name"));
				studentInfo.setEdutype_id(rs.getString("edutype_id"));
				studentInfo.setEdutype_name(rs.getString("edutype_name"));
				studentInfo.setGrade_id(rs.getString("grade_id"));
				studentInfo.setGrade_name(rs.getString("grade_name"));
				student.setStudentInfo(studentInfo);
				students.add(student);
			}
		} catch (Exception e) {
			EntityLog.setError("OracleUniteExamScore@Method:searchElective(Page page, List searchproperty,List orderproperty, Student student, boolean confirmStatus) Error! (SQL = "+sql+")");
		} finally {
			db.close(rs);
			db = null;
		}
		return students;
	}

	public int searchElectiveStudentsNum(List searchproperty,
			List searchproperty1, boolean confirmStatus)
			throws PlatformException {
		dbpool db = new dbpool();
		String statusCondition = "";
		if (confirmStatus) {
			statusCondition = "and a.status = '1'";
		} else {
			statusCondition = "and a.status = '0'";
		}
		String sql = "select id,name,reg_no,site_id,site_name,major_id,major_name,edutype_id,edutype_name,grade_id,grade_name from "
				+ "(select a.id,a.name,a.reg_no,a.site_id,b.name as site_name,a.major_id,c.name as major_name,"
				+ "a.edutype_id,d.name as edutype_name,a.grade_id,e.name as grade_name from entity_student_info a,"
				+ "entity_site_info b,entity_major_info c,entity_edu_type d,entity_grade_info e,"
				+ "(select distinct a.student_id from entity_elective a,entity_teach_class b,entity_course_active c,"
				+ "entity_student_info d where a.student_id=d.id and a.teachclass_id = b.id and b.open_course_id = c.id "
				+ statusCondition
				+ Conditions.convertToAndCondition(searchproperty1, null)
				+ ") f "
				+ "where a.site_id=b.id and a.major_id=c.id and a.edutype_id=d.id and a.grade_id=e.id "
				+ "and a.id=f.student_id "
				+ Conditions.convertToAndCondition(searchproperty, null) + ")";
		// System.out.println("searchElectiveStudentsNum="+sql);
		// EntityLog.setDebug(sql);
		return db.countselect(sql);
	} /*
		 * (non-Javadoc)
		 * 
		 * @see com.whaty.platform.entity.activity.BasicActivityList#searchElective(com.whaty.platform.util.Page,
		 *      java.util.List, java.util.List)
		 */

	public List searchElectiveCourse(Page page, List searchproperty,
			List orderproperty, Student student) throws PlatformException {
		dbpool db = new dbpool();
		String SQL;
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderproperty;
		}
		if (student != null)
			// SQL = "select
			// id,student_id,status,student_name,teachclass_id,teachclass_name,course_id,course_name,semester_id
			// from "
			SQL = "select distinct id,elective_status,course_id,course_name,course_credit,course_time,major_name,open_course_id,semester_id,semester_name,semester_start_elective,semester_end_elective,usual_score,exam_score,total_score,usual_score_status,exam_score_status,total_score_status from "
					+ "(select a.id,a.status as elective_status,a.student_id as student_id,c.open_course_id,a.status as status,b.name as student_name,a.teachclass_id "
					+ "as teachclass_id,b.site_id, b.major_id, b.grade_id, b.edutype_id,c.name as teachclass_name,d.course_id as course_id,d.semester_id as semester_id,"
					+ "f.name as semester_name,to_char(f.start_elective,'yyyy-mm-dd') as semester_start_elective, "
					+ "to_char(f.end_elective, 'yyyy-mm-dd') as semester_end_elective, e.name as course_name,e.credit as course_credit,e.course_time as course_time,g.name as major_name from,a.usual_score as usual_score,a.exam_score as exam_score,a.total_score as total_score,a.usual_score_status as usual_score_status,a.exam_score_status as exam_score_status,a.total_score_status as total_score_status  (select id,student_id,status,teachclass_id,usual_score,exam_score,total_score,usual_score_status,exam_score_status,total_score_status from entity_elective where "
					+ "student_id='"
					+ student.getId()
					+ "' and (usual_score_status='1' or exam_score_status='1' or total_score_status='1') )a,entity_student_info b, "
					+ "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f,entity_major_info g where a.student_id=b.id "
					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id and e.major_id=g.id,and (a.usual_score_status='1' or a.exam_score_status='1' or a.total_score_status='1')) ";

		else

			SQL = "select distinct id,elective_status,course_id,course_name,course_credit,course_time,major_name,open_course_id,semester_id,semester_name,semester_start_elective,semester_end_elective,usual_score,exam_score,total_score,usual_score_status,exam_score_status,total_score_status  from "
					+ "(select a.id,a.status as elective_status,a.student_id as student_id,c.open_course_id,a.status as status,b.name as student_name,a.teachclass_id "
					+ "as teachclass_id,b.site_id, b.major_id, b.grade_id, b.edutype_id,c.name as teachclass_name,d.course_id as course_id,d.semester_id as semester_id, "
					+ "f.name as semester_name, to_char(f.start_elective,'yyyy-mm-dd') as semester_start_elective, "
					+ "to_char(f.end_elective, 'yyyy-mm-dd') as semester_end_elective, e.name as course_name,e.credit as course_credit,e.course_time as course_time,g.name as major_name,a.usual_score as usual_score,a.exam_score as exam_score,a.total_score as total_score,a.usual_score_status as usual_score_status,a.exam_score_status as exam_score_status,a.total_score_status as total_score_status  from entity_elective a, entity_student_info b,"
					+ "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f,entity_major_info g where a.student_id=b.id "
					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id and e.major_id=g.id and (a.usual_score_status='1' or a.exam_score_status='1' or a.total_score_status='1')) ";

		String sql = SQL
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		// System.out.println(sql);
		// EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList electives = null;
		try {
			db = new dbpool();
			electives = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleElective elective = new OracleElective();
				OracleTeachClass newTeachClass = new OracleTeachClass();
				OracleOpenCourse openCourse = new OracleOpenCourse();
				// OracleSemester semester = new
				// OracleSemester(rs.getString("semester_id"));
				OracleSemester semester = new OracleSemester();
				OracleCourse course = new OracleCourse();
				elective.setId(rs.getString("id"));
				elective.setUsualScore(rs.getString("usual_score"));
				elective.setExamScore(rs.getString("exam_score"));
				elective.setTotalScore(rs.getString("total_score"));
				elective
						.setUsualScoreStatus(rs.getString("usual_score_status"));
				elective.setExamScoreStatus(rs.getString("exam_score_status"));
				elective.setExamScoreStatus(rs.getString("total_score_status"));
				elective.setElectiveStatus(rs.getString("elective_status"));
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCredit(rs.getString("course_credit"));
				course.setCourse_time(rs.getString("course_time"));
				course.setMajor_name(rs.getString("major_name"));
				semester.setId(rs.getString("semester_id"));
				semester.setName(rs.getString("semester_name"));
				semester.setStart_elective(rs
						.getString("semester_start_elective"));
				semester.setEnd_elective(rs.getString("semester_end_elective"));
//				openCourse.setCourse(course);
				openCourse.setId(rs.getString("open_course_id"));
//				openCourse.setSemester(semester);
				newTeachClass.setOpenCourse(openCourse);
				elective.setTeachClass(newTeachClass);
				electives.add(elective);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return electives;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchElectiveNum(java.util.List)
	 */
	public int searchElectiveNum(List searchproperty, Student student)
			throws PlatformException {
		dbpool db = new dbpool();
		String SQL = "";
		if (student != null)
			SQL = "select distinct id,teachclass_id,elective_status,expend_score_student_status,course_id,course_name,course_credit,course_time,open_course_id,semester_id,semester_name,semester_start_elective,semester_end_elective,usual_score,exam_score,total_score,expend_score,usual_score_status,exam_score_status,total_score_status,expend_score_status from "
					+ "(select a.id,a.status as elective_status,a.student_id as student_id,c.open_course_id,a.status as status,b.name as student_name,a.teachclass_id "
					+ "as teachclass_id,b.site_id, b.major_id, b.grade_id, b.edutype_id,c.name as teachclass_name,d.course_id as course_id,d.semester_id as semester_id,"
					+ "f.name as semester_name,to_char(f.start_elective,'yyyy-mm-dd') as semester_start_elective, "
					+ "to_char(f.end_elective, 'yyyy-mm-dd') as semester_end_elective, e.name as course_name,e.credit as course_credit,e.course_time as course_time, "
					+ "a.usual_score as usual_score,a.exam_score as exam_score,a.total_score as total_score,a.expend_score as expend_score, "
					+ "a.usual_score_status as usual_score_status,a.exam_score_status as exam_score_status,a.total_score_status as total_score_status,a.expend_score_status as expend_score_status,a.expend_score_student_status as expend_score_student_status from (select id,student_id,status,teachclass_id,usual_score,exam_score,total_score,expend_score,expend_score_student_status,expend_score_status,total_score_status,exam_score_status,usual_score_status from entity_elective where "
					+ "student_id='"
					+ student.getId()
					+ "')a,entity_student_info b, "
					+ "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f where a.student_id=b.id "
					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id ) ";

		else

			SQL = "select distinct id,teachclass_id,elective_status,course_id,course_name,course_credit,course_time,open_course_id,semester_id,semester_name,semester_start_elective,semester_end_elective,usual_score,exam_score,total_score,expend_score,usual_score_status,exam_score_status,total_score_status,expend_score_status,expend_score_student_status,expend_score_status  from "
					+ "(select a.id,a.status as elective_status,a.student_id as student_id,c.open_course_id,a.status as status,b.name as student_name,a.teachclass_id "
					+ "as teachclass_id,b.site_id, b.major_id, b.grade_id, b.edutype_id,c.name as teachclass_name,d.course_id as course_id,d.semester_id as semester_id, "
					+ "f.name as semester_name, to_char(f.start_elective,'yyyy-mm-dd') as semester_start_elective, "
					+ "to_char(f.end_elective, 'yyyy-mm-dd') as semester_end_elective, e.name as course_name,e.credit as course_credit,e.course_time as course_time,a.usual_score as usual_score,a.exam_score as exam_score,a.total_score as total_score,a.expend_score as expend_score,a.usual_score_status as usual_score_status,a.exam_score_status as exam_score_status,a.total_score_status as total_score_status,a.expend_score_status as expend_score_status,a.expend_score_student_status as expend_score_student_status  from entity_elective a, entity_student_info b,"
					+ "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f where a.student_id=b.id "
					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id  ) ";

		String sql = SQL + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List getOpenCoursesInTeachPlan(Page page, String semesterId,
			String gradeId, String eduTypeId, String majorId, String studentId,
			List searchProperty, List orderProperty) throws PlatformException {

		dbpool db = new dbpool();
		MyResultSet rs = null;
		String con = "";
		if (studentId != null)
			con = " and s.id = '" + studentId + "'";
		ArrayList openCourses = null;
		List OrderProperties = new ArrayList();
		if (orderProperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderProperty;
		}
		String sql = "select course_id,open_course_id,course_name,credit,default_credit,course_time,default_time,course_type,isselected,status,teachclass_id from "
				+ "(select a.course_id,a.open_course_id,a.course_name,a.credit,a.default_credit,a.course_time,a.default_time,a.course_type,"
				+ "nvl(b.course_id,'NOTSELECTED') as isselected,b.status,a.teachclass_id from (select a.id as open_course_id,ci.id as course_id,ci.name as course_name,"
				+ "ci.credit as default_credit,ci.course_time as default_time,tc.credit as credit,tc.coursetime as course_time,"
				+ "tc.type as course_type,c.id as teachclass_id from entity_course_active a,entity_teachplan_info t,entity_teachplan_course tc,"
				+ "entity_teach_class c,entity_course_info ci where a.id = c.open_course_id and a.semester_id = '"
				+ semesterId
				+ "' "
				+ "and t.id = tc.teachplan_id and tc.course_id = ci.id and a.course_id = ci.id and t.grade_id='"
				+ gradeId
				+ "' and "
				+ "t.major_id='"
				+ majorId
				+ "' and t.edutype_id='"
				+ eduTypeId
				+ "') a ,(select c.id as course_id,e.status from "
				+ "entity_elective e,entity_student_info s,entity_teach_class t,entity_course_active ca,entity_course_info c "
				+ "where e.student_id = s.id and e.teachclass_id = t.id and t.open_course_id = ca.id and ca.course_id = c.id and "
				+ "s.edutype_id = '"
				+ eduTypeId
				+ "' and s.major_id='"
				+ majorId
				+ "' and s.grade_id='"
				+ gradeId
				+ "' "
				+ con
				+ ")b where b.course_id(+) = a.course_id)"
				+ Conditions
						.convertToCondition(searchProperty, OrderProperties);

		ArrayList teachPlanCourse = null;
		ArrayList teachPlanOpenCourseList = new ArrayList();
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				teachPlanCourse = new ArrayList();
				teachPlanCourse.add(rs.getString("course_id"));
				teachPlanCourse.add(rs.getString("course_name"));
				teachPlanCourse.add(rs.getString("credit"));
				teachPlanCourse.add(rs.getString("default_credit"));
				teachPlanCourse.add(rs.getString("course_time"));
				teachPlanCourse.add(rs.getString("default_time"));
				teachPlanCourse.add(rs.getString("course_type"));
				teachPlanCourse.add(rs.getString("isselected"));
				teachPlanCourse.add(rs.getString("status"));
				teachPlanCourse.add(rs.getString("open_course_id"));
				teachPlanCourse.add(rs.getString("teachclass_id"));
				teachPlanOpenCourseList.add(teachPlanCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachPlanOpenCourseList;
	}

	public Map getElectiveStudentsByExecutePlanCourses(String semesterId,
			String majorId, String eduTypeId, String gradeId)
			throws PlatformException {

		Map map = new HashMap();
		String sql = "";
		ArrayList studentIdList = null;
		List openCourseList = this.getOpenCoursesInExecutePlan(null,
				semesterId, gradeId, eduTypeId, majorId, null, null, null);

		for (int i = 0; i < openCourseList.size(); i++) {
			dbpool db = new dbpool();
			MyResultSet rs = null;
			sql = "select student_id from entity_elective where teachclass_id = '"
					+ ((List) openCourseList.get(i)).get(8) + "'";
			try {
				rs = db.executeQuery(sql);
				studentIdList = new ArrayList();
				while (rs != null && rs.next()) {

					studentIdList.add(rs.getString("student_id"));
				}
				map.put(((List) openCourseList.get(i)).get(8), studentIdList);
			} catch (Exception e) {
				
			} finally {
				db.close(rs);
				db = null;
			}
		}
		return map;
	}

	public List getOpenCoursesInExecutePlan(Page page, String semesterId,
			String gradeId, String eduTypeId, String majorId, String studentId,
			List searchProperty, List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String con = "";
		if (studentId != null)
			con = " and s.id = '" + studentId + "'";
		List OrderProperties = new ArrayList();
		if (orderProperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderProperty;
		}
		String sql = "select distinct course_id,open_course_id,course_name,credit,course_time,"
				+ "course_type,isselected,status,teachclass_id from "
				+ "(select a.course_id,a.open_course_id,a.course_name,a.credit,a.course_time,a.course_type,"
				+ "nvl(b.course_id,'NOTSELECTED') as isselected,b.status,a.teachclass_id from "
				+ "(select a.id as open_course_id,tc.COURSE_ID,tc.credit as credit,tc.coursetime as course_time,"
				+ "tc.type as course_type,etc.id as teachclass_id,c.name as course_name from entity_teachplan_info t,entity_teachplan_course tc,"
				+ "entity_executeplan_info ei,entity_executeplan_group eg,entity_executeplan_course ec,entity_course_active a,"
				+ "entity_teach_class etc,entity_course_info c where a.course_id = c.id and a.semester_id = ei.semester_id and a.id = etc.open_course_id and ec.group_id = eg.id "
				+ "and eg.executeplan_id = ei.id and ei.semester_id = '"
				+ semesterId
				+ "' and ei.teachplan_id = t.id "
				+ "and t.id = tc.teachplan_id and ec.teachplan_course_id = tc.id and t.grade_id='"
				+ gradeId
				+ "' and "
				+ "t.major_id='"
				+ majorId
				+ "' and t.edutype_id='"
				+ eduTypeId
				+ "' and tc.course_id = c.id) a ,(select c.id as course_id,e.status "
				+ "from entity_elective e,entity_student_info s,entity_teach_class t,entity_course_active ca,entity_course_info c "
				+ "where e.student_id = s.id and e.teachclass_id = t.id and t.open_course_id = ca.id and ca.course_id = c.id and "
				+ "s.edutype_id = '"
				+ eduTypeId
				+ "' and s.major_id='"
				+ majorId
				+ "' and s.grade_id='"
				+ gradeId
				+ "' "
				+ con
				+ ")b "
				+ "where b.course_id(+) = a.course_id)"
				+ Conditions
						.convertToCondition(searchProperty, OrderProperties);

		ArrayList teachPlanCourse = null;
		ArrayList teachPlanOpenCourseList = new ArrayList();
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				teachPlanCourse = new ArrayList();
				teachPlanCourse.add(rs.getString("course_id"));
				teachPlanCourse.add(rs.getString("course_name"));
				teachPlanCourse.add(rs.getString("credit"));
				teachPlanCourse.add(rs.getString("course_time"));
				teachPlanCourse.add(rs.getString("course_type"));
				teachPlanCourse.add(rs.getString("isselected"));
				teachPlanCourse.add(rs.getString("status"));
				teachPlanCourse.add(rs.getString("open_course_id"));
				teachPlanCourse.add(rs.getString("teachclass_id"));
				teachPlanOpenCourseList.add(teachPlanCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachPlanOpenCourseList;
	}

	public int getOpenCoursesInExecutePlanNum(String semesterId,
			String gradeId, String eduTypeId, String majorId, String studentId,
			List searchProperty, List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		String con = "";
		if (studentId != null)
			con = " and s.id = '" + studentId + "'";
		List OrderProperties = new ArrayList();
		if (orderProperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderProperty;
		}
		String sql = "select course_id,open_course_id,course_name,credit,course_time,"
				+ "course_type,isselected,status,teachclass_id from "
				+ "(select a.course_id,a.open_course_id,a.course_name,a.credit,a.course_time,a.course_type,"
				+ "nvl(b.course_id,'NOTSELECTED') as isselected,b.status,a.teachclass_id from "
				+ "(select a.id as open_course_id,tc.COURSE_ID,tc.credit as credit,tc.coursetime as course_time,"
				+ "tc.type as course_type,etc.id as teachclass_id,c.name as course_name from entity_teachplan_info t,entity_teachplan_course tc,"
				+ "entity_executeplan_info ei,entity_executeplan_group eg,entity_executeplan_course ec,entity_course_active a,"
				+ "entity_teach_class etc,entity_course_info c where a.course_id = c.id and a.semester_id = ei.semester_id and a.id = etc.open_course_id and ec.group_id = eg.id "
				+ "and eg.executeplan_id = ei.id and ei.semester_id = '"
				+ semesterId
				+ "' and ei.teachplan_id = t.id "
				+ "and t.id = tc.teachplan_id and ec.teachplan_course_id = tc.id and t.grade_id='"
				+ gradeId
				+ "' and "
				+ "t.major_id='"
				+ majorId
				+ "' and t.edutype_id='"
				+ eduTypeId
				+ "' and tc.course_id = c.id) a ,(select c.id as course_id,e.status "
				+ "from entity_elective e,entity_student_info s,entity_teach_class t,entity_course_active ca,entity_course_info c "
				+ "where e.student_id = s.id and e.teachclass_id = t.id and t.open_course_id = ca.id and ca.course_id = c.id and "
				+ "s.edutype_id = '"
				+ eduTypeId
				+ "' and s.major_id='"
				+ majorId
				+ "' and s.grade_id='"
				+ gradeId
				+ "' "
				+ con
				+ ")b "
				+ "where b.course_id(+) = a.course_id)"
				+ Conditions
						.convertToCondition(searchProperty, OrderProperties);
		return db.countselect(sql);
	}

	public List getExecutePlanCourses(Page page, String semesterId,
			String gradeId, String eduTypeId, String majorId, String studentId,
			List searchProperty, List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String con = "";
		if (studentId != null)
			con = " and s.id = '" + studentId + "'";
		List OrderProperties = new ArrayList();
		if (orderProperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderProperty;
		}
		String sql = "select course_id,open_course_id,course_name,credit,course_time,"
				+ "course_type,teachclass_id from "
				+ "(select a.id as open_course_id,tc.COURSE_ID,tc.credit as credit,tc.coursetime as course_time,"
				+ "tc.type as course_type,etc.id as teachclass_id,c.name as course_name from entity_teachplan_info t,entity_teachplan_course tc,"
				+ "entity_executeplan_info ei,entity_executeplan_group eg,entity_executeplan_course ec,entity_course_active a,"
				+ "entity_teach_class etc,entity_course_info c where a.course_id = c.id and a.semester_id = ei.semester_id and a.id = etc.open_course_id and ec.group_id = eg.id "
				+ "and eg.executeplan_id = ei.id and ei.semester_id = '"
				+ semesterId
				+ "' and ei.teachplan_id = t.id "
				+ "and t.id = tc.teachplan_id and ec.teachplan_course_id = tc.id and t.grade_id='"
				+ gradeId
				+ "' and "
				+ "t.major_id='"
				+ majorId
				+ "' and t.edutype_id='"
				+ eduTypeId
				+ "' and tc.course_id = c.id) "
				+ Conditions
						.convertToCondition(searchProperty, OrderProperties);

		ArrayList excutePlanCourse = null;
		ArrayList excutelanOpenCourseList = new ArrayList();
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				excutePlanCourse = new ArrayList();
				excutePlanCourse.add(rs.getString("course_id"));
				excutePlanCourse.add(rs.getString("course_name"));
				excutePlanCourse.add(rs.getString("credit"));
				excutePlanCourse.add(rs.getString("course_time"));
				excutePlanCourse.add(rs.getString("course_type"));
				excutePlanCourse.add(rs.getString("open_course_id"));
				excutePlanCourse.add(rs.getString("teachclass_id"));
				excutelanOpenCourseList.add(excutePlanCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return excutelanOpenCourseList;
	}

	public List getOpenCoursesNotInExecutePlan(Page page, String semesterId,
			String gradeId, String eduTypeId, String majorId, String studentId,
			List searchProperty, List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String con = "";
		if (studentId != null)
			con = " and s.id = '" + studentId + "'";
		List OrderProperties = new ArrayList();
		if (orderProperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderProperty;
		}
		String sql = "select course_id,"
				     +"  open_course_id,"
				     +"  course_name,"
				     +"  credit,"
				     +"  course_time,"
				     +"  course_type,"
				     +"  isselected,"
				     +"  status,"
				     +"  teachclass_id"
				     +" from (select a.course_id,"
				     +"          a.open_course_id,"
				     +"          a.course_name,"
				     +"          a.credit,"
				     +"          a.course_time,"
				     +"          a.course_type,"
				     +"          nvl(b.course_id, 'NOTSELECTED') as isselected,"
				     +"          b.status,"
				     +"          a.teachclass_id"
				     +"     from (select a.id          as open_course_id,"
				     +"                  ci.id         as course_id,"
				     +"                  ci.name       as course_name,"
				     +"                  tc.credit     as credit,"
				     +"                  tc.coursetime as course_time,"
				     +"                  tc.type       as course_type,"
				     +"                  c.id          as teachclass_id"
				     +"             from entity_course_active    a,"
				     +"                  entity_teachplan_info   t,"
				     +"                  entity_teachplan_course tc,"
				     +"                  entity_teach_class      c,"
				     +"                  entity_course_info      ci"
				     +"            where a.id = c.open_course_id"
				     +"              and a.semester_id = '"+semesterId+"'"
				     +"              and t.id = tc.teachplan_id"
				     +"              and tc.course_id = ci.id"
				     +"              and a.course_id = ci.id"
				     +"              and t.grade_id = '"+gradeId+"'"
				     +"              and t.major_id = '"+majorId+"'"
				     +"              and t.edutype_id = '"+eduTypeId+"'"
				     +"              and ci.id not in (select course_id"
				     +"                                  from entity_singleteachplan_course"
				     +"                                 where student_id = '"+studentId+"'"
				     +"                                   and status in ('0','1'))"
				     +"           union"
				     +"           select a.id         as open_course_id,"
				     +"                  c.id         as course_id,"
				     +"                  c.name       as course_name,"
				     +"                  b.credit     as credit,"
				     +"                  b.course_time as course_time,"
				     +"                  b.type       as course_type,"
				     +"                  d.id         as teachclass_id"
				     +"             from entity_course_active          a,"
				     +"                  entity_singleteachplan_course b,"
				     +"                  entity_course_info            c,"
				     +"                  entity_teach_class            d"
				     +"            where a.course_id = c.id"
				     +"              and a.course_id = b.course_id"
				     +"              and a.id = d.open_course_id"
				     +"              and b.student_id = '"+studentId+"'"
				     +"              and b.status = '1'"
				     +"              and a.semester_id = '"+semesterId+"'"
				     +"           minus"
				     +"           select a.id          as open_course_id,"
				     +"                  tc.COURSE_ID  as course_id,"
				     +"                  c.name        as course_name,"
				     +"                  tc.credit     as credit,"
				     +"                  tc.coursetime as course_time,"
				     +"                  tc.type       as course_type,"
				     +"                  etc.id        as teachclass_id"
				     +"             from entity_teachplan_info     t,"
				     +"                  entity_teachplan_course   tc,"
				     +"                  entity_executeplan_info   ei,"
				     +"                  entity_executeplan_group  eg,"
				     +"                  entity_executeplan_course ec,"
				     +"                  entity_course_active      a,"
				     +"                  entity_teach_class        etc,"
				     +"                  entity_course_info        c"
				     +"            where a.course_id = c.id"
				     +"              and a.semester_id = ei.semester_id"
				     +"              and a.id = etc.open_course_id"
				     +"              and ec.group_id = eg.id"
				     +"              and eg.executeplan_id = ei.id"
				     +"              and ei.semester_id = '"+semesterId+"'"
				     +"              and ei.teachplan_id = t.id"
				     +"              and t.id = tc.teachplan_id"
				     +"              and ec.teachplan_course_id = tc.id"
				     +"              and t.grade_id = '"+gradeId+"'"
				     +"              and t.major_id = '"+majorId+"'"
				     +"              and t.edutype_id = '"+eduTypeId+"'"
				     +"              and tc.course_id = c.id) a,"
				     +"          (select c.id as course_id, e.status"
				     +"             from entity_elective      e,"
				     +"                  entity_student_info  s,"
				     +"                  entity_teach_class   t,"
				     +"                  entity_course_active ca,"
				     +"                  entity_course_info   c"
				     +"            where e.student_id = s.id"
				     +"              and e.teachclass_id = t.id"
				     +"              and t.open_course_id = ca.id"
				     +"              and ca.course_id = c.id"
				     +"              and s.edutype_id = '"+eduTypeId+"'"
				     +"              and s.major_id = '"+majorId+"'"
				     +"              and s.grade_id = '"+gradeId+"'"
				     +"              and s.id = '"+studentId+"') b"
				     +"    where b.course_id(+) = a.course_id)"
				+ Conditions
						.convertToCondition(searchProperty, OrderProperties);

		ArrayList teachPlanCourse = null;
		ArrayList teachPlanOpenCourseList = new ArrayList();
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				teachPlanCourse = new ArrayList();
				teachPlanCourse.add(rs.getString("course_id"));
				teachPlanCourse.add(rs.getString("course_name"));
				teachPlanCourse.add(rs.getString("credit"));
				teachPlanCourse.add(rs.getString("course_time"));
				teachPlanCourse.add(rs.getString("course_type"));
				teachPlanCourse.add(rs.getString("isselected"));
				teachPlanCourse.add(rs.getString("status"));
				teachPlanCourse.add(rs.getString("open_course_id"));
				teachPlanCourse.add(rs.getString("teachclass_id"));
				teachPlanOpenCourseList.add(teachPlanCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachPlanOpenCourseList;
	}

	public List getOpenCoursesNotInTeachPlan(Page page, String semesterId,
			String gradeId, String eduTypeId, String majorId, String studentId,
			List searchProperty, List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList openCourses = null;
		String con = "";
		List OrderProperties = new ArrayList();
		if (orderProperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderProperty;
		}
		if (studentId != null)
			con = " and s.id = '" + studentId + "'";
		String sql = "select course_id,open_course_id,course_name,default_credit,default_time,isselected,status,teachclass_id "
				+ "from (select a.course_id,a.course_name,a.open_course_id,a.default_credit,a.default_time,nvl(b.course_id,'NOTSELECTED') "
				+ "as isselected,b.status,a.teachclass_id from (select a.id as open_course_id,c.id as course_id,c.name as course_name,"
				+ "c.credit as default_credit,c.course_time as default_time,t.id as teachclass_id from entity_course_active a,"
				+ "entity_course_info c,entity_teach_class t where a.semester_id='"
				+ semesterId
				+ "' and a.course_id = c.id and "
				+ "t.open_course_id = a.id and c.id in (select id from entity_course_info minus select course_id from "
				+ "entity_teachplan_info t,entity_teachplan_course tc where t.id = tc.teachplan_id and t.grade_id='"
				+ gradeId
				+ "' "
				+ "and t.major_id='"
				+ majorId
				+ "' and t.edutype_id='"
				+ eduTypeId
				+ "'))a,(select c.id as course_id,e.status from "
				+ "entity_elective e,entity_student_info s,entity_teach_class t,entity_course_active ca,entity_course_info c "
				+ "where e.student_id = s.id and e.teachclass_id = t.id and t.open_course_id = ca.id and ca.course_id = c.id and "
				+ "s.edutype_id = '"
				+ eduTypeId
				+ "' and s.major_id='"
				+ majorId
				+ "' and s.grade_id='"
				+ gradeId
				+ "' "
				+ con
				+ ")b where b.course_id(+) = a.course_id)"
				+ Conditions
						.convertToCondition(searchProperty, OrderProperties);

		ArrayList notInTeachPlanCourse = null;
		ArrayList notInTeachPlanOpenCourseList = new ArrayList();
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				notInTeachPlanCourse = new ArrayList();
				notInTeachPlanCourse.add(rs.getString("course_id"));
				notInTeachPlanCourse.add(rs.getString("course_name"));
				notInTeachPlanCourse.add(rs.getString("default_credit"));
				notInTeachPlanCourse.add(rs.getString("default_time"));
				notInTeachPlanCourse.add(rs.getString("isselected"));
				notInTeachPlanCourse.add(rs.getString("status"));
				notInTeachPlanCourse.add(rs.getString("open_course_id"));
				notInTeachPlanCourse.add(rs.getString("teachclass_id"));
				notInTeachPlanOpenCourseList.add(notInTeachPlanCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return notInTeachPlanOpenCourseList;
	}
	
	public HashMap getElectiveRejectNote(String student_id){
		dbpool db = null;
		MyResultSet rs = null;
		
		String sql = "select teachclass_id,note from  entity_elective_reject where student_id ='"+student_id+"' order by to_number(teachclass_id),to_number(id) desc";
		
		HashMap map = new HashMap();
		db = new dbpool();
		rs = db.executeQuery(sql);
		try {
			while(rs!=null&&rs.next()){
				String key = (String)rs.getString("teachclass_id");
				String value = (String)rs.getString("note");
				
				String temp= (String)map.get(key);
				if(temp==null){
					map.put(key,value);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}finally{
			db.close(rs);
			db =null;
		}
		
		
		return map;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchOpenCourse(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List)
	 */
	public List searchOpenCourse(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		dbpool db = null;
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = OPENCOURSESQL
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		MyResultSet rs = null;
		ArrayList openCourses = null;
		try {
			db = new dbpool();
			openCourses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleOpenCourse openCourse = new OracleOpenCourse();
				openCourse.setId(rs.getString("id"));
				OracleCourse course = new OracleCourse();
//				course.setMajor_id(rs.getString("major_id"));
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCredit(rs.getString("credit"));
				try {
					course.setCourse_time(rs.getString("course_time"));
				} catch (Exception e) {
				}
				course.setName(rs.getString("course_name"));
				PeTchCourse pc = new PeTchCourse();
				pc.setId(course.getId());
				pc.setName(course.getName());
				openCourse.setCourse(pc);
				OracleSemester semester = new OracleSemester();
				semester.setId(rs.getString("semester_id"));
				semester.setName(rs.getString("semester_name"));
				PeSemester ps = new PeSemester();
				ps.setId(semester.getId());
				ps.setName(semester.getName());
				openCourse.setSemester(ps);
				OracleBasicSequence es = new OracleBasicSequence(rs
						.getString("examsequence"));
				openCourse.setExamSequence(es);
				openCourses.add(openCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return openCourses;
	}

	public List searchOpenCourseWithConditionEleNum(Page page,
			List searchproperty1, List searchproperty2, List orderproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = "select id,course_id,course_name,semester_id,semester_name,credit,course_time,"
				+ "major_id,major_name,assistance_note,assistance_release_status,assistance_title,nvl(elective_num,0) as elective_num,e_id from "
				+ "(select a.id,a.course_id,b.major_id,b.name as course_name,a.semester_id as semester_id,c.name as semester_name,"
				+ "b.credit,b.course_time,d.name as major_name,e.id as e_id,e.assistance_note,e.assistance_release_status,"
				+ "e.assistance_title from entity_course_active a,entity_course_info b,entity_semester_info c,entity_major_info d,"
				+ "entity_teach_class e where a.course_id=b.id and a.semester_id=c.id and b.major_id=d.id and a.id=e.open_course_id)x,"
				+ "(select count(id) as elective_num,teachclass_id from entity_elective where student_id in (select id from entity_student_info "
				+ Conditions.convertToCondition(searchproperty1, null)
				+ ") group by teachclass_id)y where x.e_id = y.teachclass_id(+) "
				+ Conditions.convertToAndCondition(searchproperty2,
						OrderProperties);

		MyResultSet rs = null;
		ArrayList openCourse = null;
		ArrayList openCoursesWithNum = null;
		try {
			db = new dbpool();

			openCoursesWithNum = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				openCourse = new ArrayList();
				openCourse.add(rs.getString("course_id"));
				openCourse.add(rs.getString("course_name"));
				openCourse.add(rs.getString("elective_num"));
				openCourse.add(rs.getString("e_id"));
				openCourse.add(rs.getString("credit"));
				openCourse.add(rs.getString("course_time"));
				openCoursesWithNum.add(openCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return openCoursesWithNum;
	}

	public List searchOpenCourseWithEleNum(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = OPENCOURSESQLWithEleNum
				+ Conditions.convertToAndCondition(searchproperty,
						orderproperty);

		MyResultSet rs = null;
		ArrayList openCourse = null;
		ArrayList openCoursesWithNum = null;
		try {
			db = new dbpool();

			openCoursesWithNum = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				openCourse = new ArrayList();
				openCourse.add(rs.getString("course_id"));
				openCourse.add(rs.getString("course_name"));
				openCourse.add(rs.getString("elective_num"));
				openCourse.add(rs.getString("e_id"));
				openCourse.add(rs.getString("credit"));
				openCourse.add(rs.getString("course_time"));
				openCoursesWithNum.add(openCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return openCoursesWithNum;
	}

	public List searchOpenCourseWithEleNum(Page page, List searchproperty,
			List orderproperty, String site_id) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		if (site_id == null || site_id.equals(""))
			sql = "select distinct id,course_id,course_name,semester_id,semester_name,credit,course_time,"
					+ "assistance_note,assistance_release_status,assistance_title,nvl(elective_num,0) as elective_num,e_id from "
					+ "(select a.id,a.course_id,b.name as course_name,a.semester_id as semester_id,c.name as semester_name,"
					+ "b.credit,b.course_time,e.id as e_id,e.assistance_note,e.assistance_release_status,"
					+ "e.assistance_title from entity_course_active a,entity_course_info b,entity_semester_info c,"
					+ "entity_teach_class e where a.course_id=b.id and a.semester_id=c.id and a.id=e.open_course_id)x,"
					+ "(select count(id) as elective_num,teachclass_id from entity_elective group by teachclass_id)y where x.e_id = y.teachclass_id(+)"
					+ Conditions.convertToAndCondition(searchproperty,
							orderproperty);
		else
			sql = "select distinct id,course_id,course_name,semester_id,semester_name,credit,course_time,"
					+ "assistance_note,assistance_release_status,assistance_title,nvl(elective_num,0) as elective_num,e_id from "
					+ "(select a.id,a.course_id,b.name as course_name,a.semester_id as semester_id,c.name as semester_name,"
					+ "b.credit,b.course_time,e.id as e_id,e.assistance_note,e.assistance_release_status,"
					+ "e.assistance_title from entity_course_active a,entity_course_info b,entity_semester_info c,"
					+ "entity_teach_class e where a.course_id=b.id and a.semester_id=c.id and a.id=e.open_course_id)x,"
					+ "(select count(a.id) as elective_num,teachclass_id from entity_elective a,entity_student_info b where a.student_id=b.id and b.site_id='"
					+ site_id
					+ "' group by teachclass_id)y where x.e_id = y.teachclass_id(+)"
					+ Conditions.convertToAndCondition(searchproperty,
							orderproperty);

		MyResultSet rs = null;
		ArrayList openCourse = null;
		ArrayList openCoursesWithNum = null;
		try {
			db = new dbpool();

			openCoursesWithNum = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				openCourse = new ArrayList();
				openCourse.add(rs.getString("course_id"));
				openCourse.add(rs.getString("course_name"));
				openCourse.add(rs.getString("elective_num"));
				openCourse.add(rs.getString("e_id"));
				openCourse.add(rs.getString("credit"));
				openCourse.add(rs.getString("course_time"));
				openCoursesWithNum.add(openCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return openCoursesWithNum;
	}

	public List searchOpenCourses(Page page, List searchproperty,
			List orderproperty, String semester_id) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select distinct id,course_id,course_name,semester_id,semester_name,credit,course_time,"
				+ "assistance_note,assistance_release_status,assistance_title,nvl(elective_num,0) as elective_num,e_id from "
				+ "(select a.id,a.course_id,b.name as course_name,a.semester_id as semester_id,c.name as semester_name,"
				+ "b.credit,b.course_time,e.id as e_id,e.assistance_note,e.assistance_release_status,"
				+ "e.assistance_title from entity_course_active a,entity_course_info b,entity_semester_info c,"
				+ "entity_teach_class e where a.course_id=b.id and a.semester_id=c.id and a.id=e.open_course_id)x,"
				+ "(select count(a.id) as elective_num,teachclass_id from entity_elective a,entity_student_info b where a.student_id=b.id "
				+ Conditions.convertToAndCondition(searchproperty)
				+ " group by teachclass_id)y where x.e_id = y.teachclass_id "
				+ " and semester_id = '" + semester_id + "' order by course_id";

		MyResultSet rs = null;
		ArrayList openCourse = null;
		ArrayList openCoursesWithNum = null;
		try {
			db = new dbpool();

			openCoursesWithNum = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				openCourse = new ArrayList();
				openCourse.add(rs.getString("course_id"));
				openCourse.add(rs.getString("course_name"));
				openCourse.add(rs.getString("elective_num"));
				openCourse.add(rs.getString("e_id"));
				openCourse.add(rs.getString("credit"));
				openCourse.add(rs.getString("course_time"));
				openCoursesWithNum.add(openCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return openCoursesWithNum;
	}

	public int searchOpenCoursesNum(Page page, List searchproperty,
			List orderproperty, String semester_id) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select distinct id,course_id,course_name,semester_id,semester_name,credit,course_time,"
				+ "assistance_note,assistance_release_status,assistance_title,nvl(elective_num,0) as elective_num,e_id from "
				+ "(select a.id,a.course_id,b.name as course_name,a.semester_id as semester_id,c.name as semester_name,"
				+ "b.credit,b.course_time,e.id as e_id,e.assistance_note,e.assistance_release_status,"
				+ "e.assistance_title from entity_course_active a,entity_course_info b,entity_semester_info c,"
				+ "entity_teach_class e where a.course_id=b.id and a.semester_id=c.id and a.id=e.open_course_id)x,"
				+ "(select count(a.id) as elective_num,teachclass_id from entity_elective a,entity_student_info b where a.student_id=b.id "
				+ Conditions.convertToAndCondition(searchproperty)
				+ " group by teachclass_id)y where x.e_id = y.teachclass_id "
				+ " and semester_id = '" + semester_id + "' order by course_id";

		return db.countselect(sql);
	}

	public List getTeachPlanCourses(Page page, String major_id,
			String edutype_id, String grade_id, String studentId,
			List searchProperty, List orderProperty) throws PlatformException {

		dbpool db = new dbpool();
		String con = "";
		List OrderProperties = new ArrayList();
		if (orderProperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderProperty;
		}
		if (studentId != null)
			con = " and s.id = '" + studentId + "'";
		String sql = "select course_id, course_name, course_time1, course_type1, credit, credit1, course_type, course_time,isselected " 
 + "  from (select a.course_id, a.course_name, a.course_time1, a.credit1, a.course_type1, a.credit, a.course_type,"
              + " a.course_time,nvl(b.course_id, 'NOTSELECTED') as isselected from (select * from((select c.id as course_id," 
                       +" c.name as course_name, c.credit, t.credit as credit1, t.type as course_type1, c.course_type,"
                       +" c.course_time, t.coursetime as course_time1, t.type as type from entity_teachplan_info   i, entity_teachplan_course t,"
                       +" entity_course_info c where i.id = t.teachplan_id and c.id = t.course_id and i.major_id = '" +
                       major_id + "' and i.edutype_id = '"+
                       edutype_id+"' and i.grade_id = '" +
                       grade_id
                       + "') union "
               + " (select f.id as course_id, f.name as course_name, f.credit, b.credit as credit1, b.type as course_type1,"
                      +"  f.course_type, f.course_time, b.course_time as course_time1,  b.type from entity_singleteachplan_course b, entity_course_info f "
                 + " where b.course_id = f.id and b.student_id = '"+
                 studentId+"' and b.status = '1'))) a, (select c.id as course_id"
        +"  from entity_elective  e, entity_student_info  s, entity_teach_class   t, entity_course_active ca,entity_course_info   c "
        +" where e.student_id = s.id  and e.teachclass_id = t.id and t.open_course_id = ca.id and ca.course_id = c.id"
        +"   and s.edutype_id = '"+
        edutype_id+"' and s.major_id = '"+
        major_id+"' and s.grade_id = '"+
        grade_id+"' and s.id = '"+
        studentId+"') b "
        +" where a.course_id = b.course_id(+))"
				+ Conditions
						.convertToCondition(searchProperty, OrderProperties);

		MyResultSet rs = null;
		ArrayList teachPlanCourse = null;
		ArrayList teachPlanCourseList = new ArrayList();
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				teachPlanCourse = new ArrayList();
				teachPlanCourse.add(rs.getString("course_id"));
				teachPlanCourse.add(rs.getString("course_name"));
				teachPlanCourse.add(rs.getString("course_time1"));
				teachPlanCourse.add(rs.getString("course_type1"));
				teachPlanCourse.add(rs.getString("course_time"));
				teachPlanCourse.add(rs.getString("credit"));
				teachPlanCourse.add(rs.getString("credit1"));
				teachPlanCourse.add(rs.getString("isselected"));
				teachPlanCourseList.add(teachPlanCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachPlanCourseList;
	}

	public List searchTeachClasses(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = TEACHCLASSSQL
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		// System.out.println("TEACHCLASSSQL:" + sql);
		MyResultSet rs = null;
		MyResultSet countRs = null;
		ArrayList teachClasses = null;
		try {
			db = new dbpool();
			teachClasses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTeachClass teachClass = new OracleTeachClass();
				OracleOpenCourse openCourse = new OracleOpenCourse();

				openCourse.setId(rs.getString("id"));
				OracleCourse course = new OracleCourse();

				course.setMajor_id(rs.getString("major_id"));
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_time(rs.getString("course_time"));
				course.setName(rs.getString("course_name"));
//				openCourse.setCourse(course);
				OracleSemester semester = new OracleSemester();
				semester.setId(rs.getString("semester_id"));
				semester.setName(rs.getString("semester_name"));
//				openCourse.setSemester(semester);

				teachClass.setId(rs.getString("teachclass_id"));
				teachClass.setName(rs.getString("teachclass_name"));
				teachClass.setOpenCourse(openCourse);
				teachClasses.add(teachClass);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(countRs);
			db.close(rs);
			db = null;
		}
		return teachClasses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchOpenCourseNum(java.util.List)
	 */
	public int searchOpenCourseNum(List searchproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = OPENCOURSESQLWithEleNum
				+ Conditions.convertToAndCondition(searchproperty, null);
		// String sql = OPENCOURSESQL
		// + Conditions
		// .convertToCondition(searchproperty, OrderProperties);
		// System.out.print(sql);
		return db.countselect(sql);
	}

	public int searchOpenCourseNum(List searchproperty, String site_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		if (site_id == null || site_id.equals(""))
			sql = "select distinct id,course_id,course_name,semester_id,semester_name,credit,course_time,"
					+ "assistance_note,assistance_release_status,assistance_title,nvl(elective_num,0) as elective_num,e_id from "
					+ "(select a.id,a.course_id,b.name as course_name,a.semester_id as semester_id,c.name as semester_name,"
					+ "b.credit,b.course_time,e.id as e_id,e.assistance_note,e.assistance_release_status,"
					+ "e.assistance_title from entity_course_active a,entity_course_info b,entity_semester_info c,"
					+ "entity_teach_class e where a.course_id=b.id and a.semester_id=c.id and a.id=e.open_course_id)x,"
					+ "(select count(id) as elective_num,teachclass_id from entity_elective group by teachclass_id)y where x.e_id = y.teachclass_id(+)"
					+ Conditions.convertToAndCondition(searchproperty, null);
		else
			sql = "select distinct id,course_id,course_name,semester_id,semester_name,credit,course_time,"
					+ "assistance_note,assistance_release_status,assistance_title,nvl(elective_num,0) as elective_num,e_id from "
					+ "(select a.id,a.course_id,b.name as course_name,a.semester_id as semester_id,c.name as semester_name,"
					+ "b.credit,b.course_time,e.id as e_id,e.assistance_note,e.assistance_release_status,"
					+ "e.assistance_title from entity_course_active a,entity_course_info b,entity_semester_info c,"
					+ "entity_teach_class e where a.course_id=b.id and a.semester_id=c.id and a.id=e.open_course_id)x,"
					+ "(select count(a.id) as elective_num,teachclass_id from entity_elective a,entity_student_info b where a.student_id=b.id and b.site_id='"
					+ site_id
					+ "' group by teachclass_id)y where x.e_id = y.teachclass_id(+)"
					+ Conditions.convertToAndCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public int searchTeachClassesNum(List searchproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = TEACHCLASSSQL
				+ Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List getTeachersByTeachClass(String teachclass_id) {
		dbpool db = new dbpool();
		String sql = "select t.id,t.name,t.phone,t.mobilephone,t.title,t.work_place,t.email,t.teach_level,t.gender,t.note "
				+ "from PR_TCH_OPENCOURSE_TEACHER c,pe_teacher t where c.FK_OPENCOURSE_ID = '"
				+ teachclass_id + "' and t.id = c.FK_TEACHER_ID ";
		MyResultSet rs = null;
		ArrayList teacherList = new ArrayList();
		ResultSet reSet = null;
		Connection con = null;
		try {
			con = db.getConn();
			PreparedStatement ps = con.prepareStatement(sql);
			reSet = ps.executeQuery();
			
			rs = db.executeQuery(sql);
			while (rs != null && rs.next() && reSet!=null && reSet.next()) {
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				OracleTeacher teacher = new OracleTeacher();
				teacher.setId(rs.getString("id"));
				teacher.setName(rs.getString("name"));
//				eduInfo.setGh(rs.getString("gh"));
//				eduInfo.setTeach_level(rs.getString("teach_level"));
//				eduInfo
//						.setResearchDirection(rs
//								.getString("RESEARCH_DIRECTION"));
				if (rs.getString("email") != null) {
					normalInfo.setEmail(rs.getString("email").split(","));
				}
				if (rs.getString("mobilephone") != null) {
					normalInfo.setMobilePhone(rs.getString("mobilephone").split(","));
				}
//				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setWorkplace(rs.getString("work_place"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setNote(((OracleResultSet)reSet).getString("note"));
				if (rs.getString("phone") != null) {
					normalInfo.setPhone(rs.getString("phone").split(","));
				}
				teacher.setTeacherInfo(eduInfo);
				teacher.setNormalInfo(normalInfo);
				teacherList.add(teacher);
			}
		} catch (Exception e) {
			
		} finally {
			try{
				db.close(rs);
				con.close();
				reSet = null;
				db = null;
			}catch(SQLException  e){
				
			}
			
		}
		return teacherList;
	}

	public List getTeachers(String opencourse_id, String teachclass_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select t.id,t.name,t.gh,t.position,t.phone,t.title,t.workplace,t.email,t.teach_level "
				+ "from entity_teacher_course c,entity_teacher_info t,entity_teach_class a where t.id = c.teacher_id and c.teachclass_id=a.id";
		if (teachclass_id != null && !teachclass_id.equals(""))
			sql += " and c.teachclass_id = '" + teachclass_id + "'";
		if (opencourse_id != null && !opencourse_id.equals(""))
			sql += " and a.open_course_id = '" + opencourse_id + "'";
		MyResultSet rs = null;
		ArrayList teacherList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				OracleTeacher teacher = new OracleTeacher();
				teacher.setId(rs.getString("id"));
				teacher.setName(rs.getString("name"));
				eduInfo.setGh(rs.getString("gh"));
				eduInfo.setTeach_level(rs.getString("teach_level"));
				if (rs.getString("email") != null) {
					normalInfo.setEmail(rs.getString("email").split(","));
				}
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setTitle(rs.getString("title"));
				if (rs.getString("phone") != null) {
					normalInfo.setPhone(rs.getString("phone").split(","));
				}
				teacher.setTeacherInfo(eduInfo);
				teacher.setNormalInfo(normalInfo);
				teacherList.add(teacher);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teacherList;
	}

	public List getSiteTeachersByTeachClass(String teachclass_id) {
		dbpool db = new dbpool();
		String sql = "select t.id,t.site_id,t.name,t.gh,t.position,t.phone,t.title,t.workplace,t.email,t.teach_level "
				+ "from entity_siteteacher_course c,entity_siteteacher_info t where c.teachclass_id = '"
				+ teachclass_id
				+ "' and t.id = c.teacher_id and t.isconfirm='1'";
		MyResultSet rs = null;
		ArrayList teacherList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				OracleSiteTeacher teacher = new OracleSiteTeacher();
				teacher.setId(rs.getString("id"));
				teacher.setSiteId(rs.getString("site_id"));
				teacher.setName(rs.getString("name"));
				eduInfo.setGh(rs.getString("gh"));
				if (rs.getString("email") != null) {
					normalInfo.setEmail(rs.getString("email").split(","));
				}
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setTitle(rs.getString("title"));
				if (rs.getString("phone") != null) {
					normalInfo.setPhone(rs.getString("phone").split(","));
				}
				teacher.setTeacherInfo(eduInfo);
				teacher.setNormalInfo(normalInfo);
				teacherList.add(teacher);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teacherList;
	}

	public List getTeachBookByCourse(String courseId) {
		dbpool db = new dbpool();
		String sql = "select t.id,t.teachbook_name,t.publishhouse,t.maineditor,t.isbn,t.publish_date,t.price,t.note "
				+ "from entity_teachbook_course c,entity_teachbook_info t where c.course_id = '"
				+ courseId + "' and t.id = c.teachbook_id";
		MyResultSet rs = null;
		ArrayList teachbookList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				TeachBook teachbook = new OracleTeachBook();
				teachbook.setId(rs.getString("id"));
				teachbook.setTeachbook_name(rs.getString("teachbook_name"));
				teachbook.setPublishhouse(rs.getString("publishhouse"));
				teachbook.setMaineditor(rs.getString("maineditor"));
				teachbook.setIsbn(rs.getString("isbn"));
				teachbook.setPublish_date(rs.getString("publish_date"));
				teachbook.setPrice(rs.getString("price"));
				teachbook.setNote(rs.getString("note"));

				teachbookList.add(teachbook);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachbookList;
	}
	
	
	public List getTeachBookByCourse(List searchProperties) {
		dbpool db = new dbpool();
		String sql = "select entity_teachbook_info.id,teachbook_name,publishhouse,maineditor,isbn,publish_date,price,entity_teachbook_info.note from"
				+ " entity_teachbook_course,entity_teachbook_info"
				+ " where entity_teachbook_info.id = entity_teachbook_course.teachbook_id"
				+ Conditions.convertToAndCondition(searchProperties);
		MyResultSet rs = null;
		ArrayList teachbookList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				TeachBook teachbook = new OracleTeachBook();
				teachbook.setId(rs.getString("id"));
				teachbook.setTeachbook_name(rs.getString("teachbook_name"));
				teachbook.setPublishhouse(rs.getString("publishhouse"));
				teachbook.setMaineditor(rs.getString("maineditor"));
				teachbook.setIsbn(rs.getString("isbn"));
				teachbook.setPublish_date(rs.getString("publish_date"));
				teachbook.setPrice(rs.getString("price"));
				teachbook.setNote(rs.getString("note"));

				teachbookList.add(teachbook);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachbookList;
	}

	public int getTeachBookNumByCourse(List searchProperties) {
		dbpool db = new dbpool();
		String sql = "select entity_teachbook_info.id,teachbook_name,publishhouse,maineditor,isbn,publish_date,price,entity_teachbook_info.note from"
				+ " entity_teachbook_course,entity_teachbook_info "
				+ "where entity_teachbook_info.id=entity_teachbook_course.teachbook_id"
				+ Conditions.convertToAndCondition(searchProperties);
		return db.countselect(sql);
	}

	public int getCourseNumByTeachBook(List searchProperties) {
		dbpool db = new dbpool();
		String sql = "select *"
				+ " from entity_teachbook_course, entity_course_info "
				+ "where  entity_course_info.id=entity_teachbook_course.course_id"
				+ Conditions.convertToAndCondition(searchProperties);
		return db.countselect(sql);
	}

	public List getCourseByTeachBook(Page page, List searchProperties) {
		dbpool db = new dbpool();
		String sql = "select entity_course_info.id,entity_course_info.name,entity_course_info.major_id,entity_course_info.credit,entity_course_info.course_time,entity_major_info.name major_name"
				+ " from entity_teachbook_course, entity_course_info,entity_major_info "
				+ "where  entity_course_info.id=entity_teachbook_course.course_id and entity_major_info.id(+)=entity_course_info.major_id "
				+ Conditions.convertToAndCondition(searchProperties);
		MyResultSet rs = null;
		ArrayList courseList = new ArrayList();
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				Course course = new OracleCourse();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("name"));
				course.setMajor_id(rs.getString("major_id"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_time(rs.getString("course_time"));
				course.setMajor_name(rs.getString("major_name"));

				courseList.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;
	}

	public List getCourseIdByTeachbook(String bookId) {
		dbpool db = new dbpool();
		String sql = "select eci.id"
				+ " from entity_teachbook_course etc, entity_course_info eci "
				+ "where etc.teachbook_id='" + bookId
				+ "' and eci.id=etc.course_id";
		MyResultSet rs = null;
		ArrayList courseList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				courseList.add(rs.getString("id"));
			}
			return courseList;
		} catch (Exception e) {
			
			return null;
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public List getCourseByTeachBook(String bookId) {
		dbpool db = new dbpool();
		String sql = "select eci.id, eci.name, eci.major_id, eci.credit, eci.course_time"
				+ " from entity_teachbook_course etc, entity_course_info eci "
				+ "where etc.teachbook_id='"
				+ bookId
				+ "' and eci.id=etc.course_id";
		MyResultSet rs = null;
		ArrayList courseList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				Course course = new OracleCourse();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("name"));
				course.setMajor_id(rs.getString("major_id"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_time(rs.getString("course_time"));

				courseList.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;
	}

	public List getAllTeachersByTeachClass(String teachclass_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select id,name,gh,position,phone,title,workplace,email,teach_level,status from (select id,name,gh,position,phone,title,workplace,email,teach_level,'1' as status from entity_teacher_info where id in(select teacher_id from entity_teacher_course where teachclass_id = '"
				+ teachclass_id
				+ "') union select id,name,gh,position,phone,title,workplace,email,teach_level,'0' as status from entity_teacher_info where id not in(select teacher_id from entity_teacher_course where teachclass_id = '"
				+ teachclass_id + "'))";
		MyResultSet rs = null;
		ArrayList teacherList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				OracleTeacher teacher = new OracleTeacher();
				teacher.setId(rs.getString("id"));
				teacher.setName(rs.getString("name"));
				eduInfo.setGh(rs.getString("gh"));
				eduInfo.setTeach_level(rs.getString("teach_level"));
				eduInfo.setStatus(rs.getString("status"));
				normalInfo.setEmail(rs.getString("email").split(","));
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setPhone(rs.getString("phone").split(","));
				teacher.setTeacherInfo(eduInfo);
				teacher.setNormalInfo(normalInfo);
				teacherList.add(teacher);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teacherList;
	}

	public List getTeachersByOpenCourse(String open_course_id) {
		dbpool db = new dbpool();
		String sql = "select id,name,gh,position,phone,title,workplace,email,teach_level "
				+ "from entity_teacher_info  where id in (select teacher_id from entity_teacher_course c,"
				+ "entity_teach_class tc,entity_course_active a where c.teachclass_id = tc.id and "
				+ "tc.open_course_id = a.id and a.id =  '"
				+ open_course_id
				+ "' and t.id = c.teacher_id)";
		MyResultSet rs = null;
		ArrayList teacherList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				OracleTeacher teacher = new OracleTeacher();
				teacher.setId(rs.getString("id"));
				teacher.setName(rs.getString("name"));
				eduInfo.setGh(rs.getString("gh"));
				eduInfo.setTeach_level(rs.getString("teach_level"));
				normalInfo.setEmail(rs.getString("email").split(","));
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setPhone(rs.getString("phone").split(","));
				teacher.setTeacherInfo(eduInfo);
				teacher.setNormalInfo(normalInfo);
				teacherList.add(teacher);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teacherList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchTeachClass(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,
	 *      com.whaty.platform.entity.activity.OpenCourse)
	 */
	public List searchTeachClass(Page page, List searchproperty,
			List orderproperty, OpenCourse openCourse) throws PlatformException {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderproperty;
		}
		String TEACHCLASSSQL = "select id,name,open_course_id,course_id,course_name from "
				+ "(select a.id,a.name,a.open_course_id,b.course_id as course_id,c.name as course_name from "
				+ " (select id,name,open_course_id from entity_teach_class where open_course_id='"
				+ openCourse.getId()
				+ ") a,"
				+ "entity_course_active b,entity_course_info c where a.open_course_id=b.id and b.course_id=c.id)";
		String sql = TEACHCLASSSQL
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		MyResultSet rs = null;
		ArrayList teachClasses = null;
		try {
			db = new dbpool();
			teachClasses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTeachClass teachClass = new OracleTeachClass();
				teachClass.setId(rs.getString("id"));
				teachClass.setName(rs.getString("name"));
				OracleOpenCourse newOpenCourse = new OracleOpenCourse();
				newOpenCourse.setId(rs.getString("open_course_id"));
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
//				newOpenCourse.setCourse(course);
				teachClass.setOpenCourse(newOpenCourse);
				teachClasses.add(teachClass);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachClasses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchTeachClassNum(java.util.List,
	 *      com.whaty.platform.entity.activity.OpenCourse)
	 */
	public int searchTeachClassNum(List searchproperty, OpenCourse openCourse)
			throws PlatformException {
		dbpool db = new dbpool();
		String TEACHCLASSSQL = "select id,name,open_course_id,course_id,course_name from "
				+ "(select a.id,a.name,a.open_course_id,b.course_id as course_id,c.name as course_name from "
				+ " (select id,name,open_course_id from entity_teach_class where open_course_id='"
				+ openCourse.getId()
				+ ") a,"
				+ "entity_course_active b,entity_course_info c where a.open_course_id=b.id and b.course_id=c.id)";
		String sql = TEACHCLASSSQL
				+ Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchUnRegStudent(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,
	 *      com.whaty.platform.entity.basic.Semester)
	 */
	public List searchUnRegStudent(Page page, List searchproperty,
			List orderproperty, Semester semester) throws PlatformException {
		// TODO Auto-generated method stub
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = getUnRegStudentSql(Conditions
					.convertToAndCondition(searchproperty), Conditions
					.convertToCondition(null, orderproperty), semester);
			if (page != null)
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			else
				rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				student.setNickname(rs.getString("nickname"));
				student.setPassword(rs.getString("password"));
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setDegree(rs.getString("degree"));
				normalInfo.setEdu(rs.getString("edu"));
				normalInfo.setEmail(rs.getString("").split(","));
				normalInfo.setFax(rs.getString("fax").split(","));
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setGraduated_major(rs.getString("graduate_major"));
				normalInfo.setGraduated_sch(rs.getString("graduate_sch"));
				normalInfo.setGraduated_time(rs.getString("graduate_time"));
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("home_address"));
				homeaddress.setPostalcode(rs.getString("home_postalcode"));
				Address workaddress = new Address();
				workaddress.setAddress(rs.getString("work_address"));
				workaddress.setPostalcode(rs.getString("work_postalcode"));
				normalInfo.setHome_address(homeaddress);
				normalInfo.setHometown(rs.getString("hometown"));
				normalInfo.setIdcard(rs.getString("idcard"));
				normalInfo.setMobilePhone(rs.getString("mobilephone")
						.split(","));
				normalInfo.setNote(rs.getString("note"));
				normalInfo.setPhone(rs.getString("phone").split(","));
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setWork_address(workaddress);
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				student.setNormalInfo(normalInfo);
				eduInfo.setClass_id(rs.getString("class_id"));
				eduInfo.setClass_name(rs.getString("class_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setEntrance_date(rs.getString("entrance_date"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setStudy_form(rs.getString("study_form"));
				eduInfo.setStudy_status(rs.getString("study_status"));
				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db = null;
			return studentlist;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchUnRegStudentNum(java.util.List,
	 *      com.whaty.platform.entity.basic.Semester)
	 */
	public int searchUnRegStudentNum(List searchproperty, Semester semester)
			throws PlatformException {
		dbpool db = new dbpool();

		String sql = getUnRegStudentSql(Conditions
				.convertToAndCondition(searchproperty), Conditions
				.convertToCondition(null, null), semester);
		return db.countselect(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchRegStudent(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,
	 *      com.whaty.platform.entity.basic.Semester)
	 */
	public List searchRegStudent(Page page, List searchproperty,
			List orderproperty, Semester semester) throws PlatformException {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = getRegStudentSql(Conditions
					.convertToAndCondition(searchproperty), Conditions
					.convertToCondition(null, orderproperty), semester);
			if (page != null)
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			else
				rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				student.setNickname(rs.getString("nickname"));
				student.setPassword(rs.getString("password"));
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setDegree(rs.getString("degree"));
				normalInfo.setEdu(rs.getString("edu"));
				normalInfo.setEmail(rs.getString("").split(","));
				normalInfo.setFax(rs.getString("fax").split(","));
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setGraduated_major(rs.getString("graduate_major"));
				normalInfo.setGraduated_sch(rs.getString("graduate_sch"));
				normalInfo.setGraduated_time(rs.getString("graduate_time"));
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("home_address"));
				homeaddress.setPostalcode(rs.getString("home_postalcode"));
				Address workaddress = new Address();
				workaddress.setAddress(rs.getString("work_address"));
				workaddress.setPostalcode(rs.getString("work_postalcode"));
				normalInfo.setHome_address(homeaddress);
				normalInfo.setHometown(rs.getString("hometown"));
				normalInfo.setIdcard(rs.getString("idcard"));
				normalInfo.setMobilePhone(rs.getString("mobilephone")
						.split(","));
				normalInfo.setNote(rs.getString("note"));
				normalInfo.setPhone(rs.getString("phone").split(","));
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setWork_address(workaddress);
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				student.setNormalInfo(normalInfo);
				eduInfo.setClass_id(rs.getString("class_id"));
				eduInfo.setClass_name(rs.getString("class_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setEntrance_date(rs.getString("entrance_date"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setStudy_form(rs.getString("study_form"));
				eduInfo.setStudy_status(rs.getString("study_status"));
				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db = null;
			return studentlist;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchRegStudentNum(java.util.List,
	 *      com.whaty.platform.entity.basic.Semester)
	 */
	public int searchRegStudentNum(List searchproperty, Semester semester)
			throws PlatformException {
		dbpool db = new dbpool();

		String sql = getRegStudentSql(Conditions
				.convertToAndCondition(searchproperty), Conditions
				.convertToCondition(null, null), semester);
		return db.countselect(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchElective(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,
	 *      com.whaty.platform.entity.activity.TeachClass)
	 */
	public List searchElective(Page page, List searchproperty,
			List orderproperty, List teachClasses) throws PlatformException {
//		String SQL = getSearchElectiveSQL(teachClasses);
//		String SQL  = "select id,usual_score,exam_score,expend_score,total_score,student_id,reg_no,name,gender,site_name,major_name,edu_name,grade_name,open_id from (select distinct el.id as id,pest.id as student_id,pest.reg_no,pest.true_name as name,prst.gender," +
//		"si.name as site_name,ma.name as major_name,edu.name as edu_name,gr.name as grade_name,op.id as open_id ,el.score_usual as usual_score,el.score_exam as exam_score ,el.score_total as total_score,el.score_exam1 as  expend_score" +
//		"	from pr_tch_opencourse op ,pr_tch_stu_elective el ,pr_student_info prst,pe_student pest ,pr_tch_opencourse_teacher prt" +
//		"		,pe_teacher t,pe_site si,pe_major ma,pe_edutype edu,pe_grade gr where op.id=el.fk_opencourse_id and prst.id=pest.id " +
//		"and el.fk_stu_id=pest.id and prt.fk_opencourse_id=op.id and t.id=prt.fk_teacher_id and si.id=pest.fk_site_id and ma.id=pest.fk_major_id " +
//		"and edu.id=pest.fk_edutype_id and gr.id=pest.fk_grade_id and pest.flag_abort_school = '0' and pest.graduation_status = '0') se ";
		String SQL = "select id,                                                                    " +
		"       usual_score,                                                           " +
		"		homework_score,														   " +
		"       exam_score,                                                            " +
		"       total_score,                                                           " +
		"       student_id,                                                            " +
		"       reg_no,                                                                " +
		"       name,                                                                  " +
		"       gender,                                                                " +
		"       site_name,                                                             " +
		"       major_name,                                                            " +
		"       edu_name,                                                              " +
		"       grade_name,                                                            " +
		"       open_id                                                                " +
		"  from (select one.id,                                                        " +
		"               one.student_id,                                                " +
		"               one.reg_no,                                                    " +
		"               one.name,                                                      " +
		"               one.gender,                                                    " +
		"               one.site_name,                                                 " +
		"               one.major_name,                                                " +
		"               one.edu_name,                                                  " +
		"               one.grade_name,                                                " +
		"               one.usual_score,                                               " +
		"               one.exam_score,                                                " +
		"               one.total_score,                                               " +
		"				one.homework_score,											   " +
		"               two.open_id                                                    " +
		"          from (select distinct el.id as id,                                  " +
		"                                pest.id as student_id,                        " +
		"                                pest.reg_no,                                  " +
		"                                pest.true_name as name,                       " +
		"                                prst.gender,                                  " +
		"                                si.name as site_name,                         " +
		"                                ma.name as major_name,                        " +
		"                                edu.name as edu_name,                         " +
		"                                gr.name as grade_name,                        " +
		"                                course.id as c_id,                            " +
		"								 el.score_homework as homework_score,		   " +
		"                                el.score_usual as usual_score,                " +
		"                                el.score_exam as exam_score,                  " +
		"                                el.score_total as total_score                 " +
		"                  from pr_tch_opencourse         op,                          " +
		"                       pr_tch_stu_elective       el,                          " +
		"                       pr_student_info           prst,                        " +
		"                       pe_student                pest,                        " +
		"                       pe_teacher                t,                           " +
		"                       pe_site                   si,                          " +
		"                       pe_major                  ma,                          " +
		"                       pe_edutype                edu,                         " +
		"                       pe_grade                  gr,                          " +
		"                       pe_tch_course             course,                      " +
		"                       pe_semester               semester,                    " +
		"                       enum_const                enum                         " +
		"                 where op.id = el.fk_tch_opencourse_id                        " +
		"                   and prst.id = pest.fk_student_info_id                      " +
		"                   and el.fk_stu_id = pest.id                                 " +
		"                   and si.id = pest.fk_site_id                                " +
		"                   and ma.id = pest.fk_major_id                               " +
		"                   and edu.id = pest.fk_edutype_id                            " +
		"                   and gr.id = pest.fk_grade_id                               " +
		"                   and op.fk_course_id = course.id                            " +
		"                   and op.fk_semester_id = semester.id                        " +
		"                   and pest.flag_student_status = enum.id                     " +
		"                   and enum.code = '4'                                        " +
		"                   and (semester.flag_active = '1' or                         " +
		"                       (semester.flag_active = '0' and                        " +
		"                       (el.score_total is null or el.score_total < 60)))) one," +
		"               (select distinct course.id as c_id, op.id as open_id           " +
		"                  from pr_tch_opencourse op, pe_tch_course course             " +
		"                 where op.fk_course_id = course.id) two                       " +
		"         where one.c_id = two.c_id) se                                        ";

		dbpool db = new dbpool();
		String sql = SQL
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		// System.out.print(sql);
		MyResultSet rs = null;
		List electives = new ArrayList();
		try {
			db = new dbpool();
			// electives = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleElective elective = new OracleElective();
				elective.setId(rs.getString("id"));
				OracleStudent student = new OracleStudent();
				student.setId(rs.getString("student_id"));
				student.setName(rs.getString("name"));
				StudentEduInfo studentinfo = new StudentEduInfo();
				studentinfo.setReg_no(rs.getString("reg_no"));
				studentinfo.setSite_name(rs.getString("site_name"));
				studentinfo.setGrade_name(rs.getString("grade_name"));
				studentinfo.setMajor_name(rs.getString("major_name"));
				studentinfo.setEdutype_name(rs.getString("edu_name"));
				student.setStudentInfo(studentinfo);
				elective.setStudent(student);
				OracleTeachClass newTeachClass = new OracleTeachClass();
				OracleOpenCourse openCourse = new OracleOpenCourse();
				OracleSemester semester = new OracleSemester();
				OracleCourse course = new OracleCourse();
//				course.setId(rs.getString("course_id"));
//				course.setName(rs.getString("course_name"));
//				openCourse.setCourse(course);
//				semester.setId(rs.getString("semester_id"));
//				semester.setName(rs.getString("semester_name"));
//				openCourse.setSemester(semester);
//				newTeachClass.setId(rs.getString("teachclass_id"));
//				newTeachClass.setName(rs.getString("teachclass_name"));

//				newTeachClass.setOpenCourse(openCourse);
				elective.setUsualScore(rs.getString("usual_score"));
				elective.setExamScore(rs.getString("exam_score"));
				elective.setExpendScore(rs.getString("homework_score"));
				elective.setTotalScore(rs.getString("total_score"));
				elective.setTeachClass(newTeachClass);
//				elective.setElectiveStatus(rs.getString("status"));
				electives.add(elective);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return electives;
	}

	/**
	 * @return
	 */
	private String getSearchElectiveSQL(List teachClasses) {
		String SQL = "";
		String teachClassStr = "";
		for (int i = 0; i < teachClasses.size(); i++) {
			teachClassStr = teachClassStr + "'"
					+ ((TeachClass) teachClasses.get(i)).getId() + "',";
		}
		if (teachClassStr.length() > 1) {
			teachClassStr = teachClassStr.substring(0,
					teachClassStr.length() - 1);

			SQL = "select id,student_id,reg_no, status,student_name,teachclass_id,teachclass_name,course_id,course_name,"
					+ "major_id,edu_type_id,grade_id,site_id,semester_id,semester_name,site_name,grade_name,major_name,edutype_name, usual_score, total_score, exam_score, expend_score from "
					+ "(select a.id,a.student_id as student_id,b.reg_no as reg_no, a.status as status,"
					+ "b.name as student_name,b.major_id as major_id,a.usual_score, a.total_score, a.exam_score, a.expend_score,"
					+ "b.edutype_id as edu_type_id,b.grade_id as grade_id,b.site_id as site_id,a.teachclass_id "
					+ "as teachclass_id,c.name as teachclass_name,d.course_id as course_id,"
					+ "f.id as semester_id,f.name as semester_name,e.name as course_name,s.name as site_name,m.name as major_name,g.name as grade_name,et.name as edutype_name from "
					+ "(select id,student_id,status,teachclass_id, usual_score, total_score, exam_score, expend_score from entity_elective where "
					+ "teachclass_id in ("
					+ teachClassStr
					+ ")) a,entity_student_info b,"
					+ "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f,entity_site_info s,entity_major_info m,entity_grade_info g,entity_edu_type et"
					+ " where a.student_id=b.id and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id and b.site_id = s.id and b.major_id = m.id and b.grade_id = g.id and b.edutype_id = et.id and b.isgraduated = '0' and b.status = '0') ";
		} else {
			SQL = "select id,student_id,reg_no, status,student_name,teachclass_id,teachclass_name,course_id,course_name,"
					+ "major_id,edu_type_id,grade_id,site_id,semester_id,semester_name,site_name,grade_name,major_name,edutype_name,  usual_score, total_score, exam_score, expend_score from "
					+ "(select a.id,a.student_id as student_id,b.reg_no as reg_no, a.status as status,b.name as student_name,b.major_id as major_id,"
					+ "b.edutype_id as edu_type_id,b.grade_id as grade_id,b.site_id as site_id,a.usual_score, a.total_score, a.exam_score, a.expend_score,a.teachclass_id "
					+ "as teachclass_id,c.name as teachclass_name,d.course_id as course_id,"
					+ "f.id as semester_id,f.name as semester_name,e.name as course_name,s.name as site_name,m.name as major_name,g.name as grade_name,et.name as edutype_name  from "
					+ "(select id,student_id,status,teachclass_id , usual_score, total_score, exam_score, expend_score from entity_elective) a,entity_student_info b,"
					+ "entity_teach_class c,entity_course_active d,entity_course_info e,entity_semester_info f,entity_site_info s,entity_major_info m,entity_grade_info g,entity_edu_type et where a.student_id=b.id "
					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id and d.semester_id=f.id and b.site_id = s.id and b.major_id = m.id and b.grade_id = g.id and b.edutype_id = et.id and b.isgraduated = '0' and b.status = '0') ";
		}
		// System.out.println(SQL+"////////////////////");
		return SQL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchElectiveNum(java.util.List,
	 *      com.whaty.platform.entity.activity.TeachClass)
	 */
	public int searchElectiveNum(List searchproperty, List teachClasses)
			throws PlatformException {
//		String SQL = getSearchElectiveSQL(teachClasses);
//		String SQL  = "select id,reg_no,name,gender,site_name,major_name,edu_name,grade_name,open_id from (select distinct prst.id as id,pest.reg_no,pest.name,prst.gender," +
//		"si.name as site_name,ma.name as major_name,edu.name as edu_name,gr.name as grade_name,op.id as open_id  " +
//		"	from pr_tch_opencourse op ,pr_tch_stu_elective el ,pr_student_info prst,pe_student pest ,pr_tch_opencourse_teacher prt" +
//		"		,pe_teacher t,pe_site si,pe_major ma,pe_edutype edu,pe_grade gr where op.id=el.fk_opencourse_id and prst.id=pest.id " +
//		"and el.fk_stu_id=pest.id and prt.fk_opencourse_id=op.id and t.id=prt.fk_teacher_id and si.id=pest.fk_site_id and ma.id=pest.fk_major_id " +
//		"and edu.id=pest.fk_edutype_id and gr.id=pest.fk_grade_id and pest.flag_abort_school = '0' and pest.graduation_status = '0') se ";
		String SQL = "select id,                                                                    " +
		"       usual_score,                                                           " +
		"       exam_score,                                                            " +
		"       total_score,                                                           " +
		"       student_id,                                                            " +
		"       reg_no,                                                                " +
		"       name,                                                                  " +
		"       gender,                                                                " +
		"       site_name,                                                             " +
		"       major_name,                                                            " +
		"       edu_name,                                                              " +
		"       grade_name,                                                            " +
		"       open_id                                                                " +
		"  from (select one.id,                                                        " +
		"               one.student_id,                                                " +
		"               one.reg_no,                                                    " +
		"               one.name,                                                      " +
		"               one.gender,                                                    " +
		"               one.site_name,                                                 " +
		"               one.major_name,                                                " +
		"               one.edu_name,                                                  " +
		"               one.grade_name,                                                " +
		"               one.usual_score,                                               " +
		"               one.exam_score,                                                " +
		"               one.total_score,                                               " +
		"               two.open_id                                                    " +
		"          from (select distinct el.id as id,                                  " +
		"                                pest.id as student_id,                        " +
		"                                pest.reg_no,                                  " +
		"                                pest.true_name as name,                       " +
		"                                prst.gender,                                  " +
		"                                si.name as site_name,                         " +
		"                                ma.name as major_name,                        " +
		"                                edu.name as edu_name,                         " +
		"                                gr.name as grade_name,                        " +
		"                                course.id as c_id,                            " +
		"                                el.score_usual as usual_score,                " +
		"                                el.score_exam as exam_score,                  " +
		"                                el.score_total as total_score                 " +
		"                  from pr_tch_opencourse         op,                          " +
		"                       pr_tch_stu_elective       el,                          " +
		"                       pr_student_info           prst,                        " +
		"                       pe_student                pest,                        " +
		"                       pe_teacher                t,                           " +
		"                       pe_site                   si,                          " +
		"                       pe_major                  ma,                          " +
		"                       pe_edutype                edu,                         " +
		"                       pe_grade                  gr,                          " +
		"                       pe_tch_course             course,                      " +
		"                       pe_semester               semester,                    " +
		"                       enum_const                enum                         " +
		"                 where op.id = el.fk_tch_opencourse_id                        " +
		"                   and prst.id = pest.fk_student_info_id                      " +
		"                   and el.fk_stu_id = pest.id                                 " +
		"                   and si.id = pest.fk_site_id                                " +
		"                   and ma.id = pest.fk_major_id                               " +
		"                   and edu.id = pest.fk_edutype_id                            " +
		"                   and gr.id = pest.fk_grade_id                               " +
		"                   and op.fk_course_id = course.id                            " +
		"                   and op.fk_semester_id = semester.id                        " +
		"                   and pest.flag_student_status = enum.id                     " +
		"                   and enum.code = '4'                                        " +
		"                   and (semester.flag_active = '1' or                         " +
		"                       (semester.flag_active = '0' and                        " +
		"                       (el.score_total is null or el.score_total < 60)))) one," +
		"               (select distinct course.id as c_id, op.id as open_id           " +
		"                  from pr_tch_opencourse op, pe_tch_course course             " +
		"                 where op.fk_course_id = course.id) two                       " +
		"         where one.c_id = two.c_id) se                                        ";

		dbpool db = new dbpool();
		String sql = SQL + Conditions.convertToCondition(searchproperty, null);

		// System.out.println("Elective SQL:" + sql);
		return db.countselect(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchOpenCourse(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,
	 *      com.whaty.platform.entity.basic.Semester)
	 */
	public List searchOpenCourse(Page page, List searchproperty,
			List orderproperty, Semester semester) throws PlatformException {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderproperty;
		}
		String SQL = "select id,course_id,course_name,semester_id,semester_name from "
				+ "(select a.id,a.course_id,b.name as course_name,a.semester_id as semester_id,c.name as semester_name from "
				+ "(select id,course_id,semester_id from entity_course_active where semester_id='"
				+ semester.getId()
				+ "') a,"
				+ "entity_course_info b,entity_semester_info c where a.course_id=b.id and a.semester_id=c.id) ";
		String sql = SQL
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);

		MyResultSet rs = null;
		ArrayList openCourses = null;
		try {
			db = new dbpool();
			openCourses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleOpenCourse openCourse = new OracleOpenCourse();
				openCourse.setId(rs.getString("id"));
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
//				openCourse.setCourse(course);
				OracleSemester newSemester = new OracleSemester();
				newSemester.setId(rs.getString("semester_id"));
				newSemester.setName(rs.getString("semester_name"));
//				openCourse.setSemester(newSemester);
				openCourses.add(openCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return openCourses;
	}

	public List searchUnOpenCourses(Page page, List searchproperty,
			List orderproperty, Semester semester) throws PlatformException {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderproperty;
		}
		String SQL = "select course_id,course_name,major_id,credit,course_time,exam_type from "
				+ "(select b.id as course_id,b.name as course_name,major_id,credit,course_time,exam_type "
				+ "from entity_course_info b where b.id not in "
				+ "(select course_id from entity_course_active where semester_id='"
				+ semester.getId() + "') and b.course_status ='0000')";
		String sql = SQL
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		MyResultSet rs = null;
		ArrayList coursesList = null;
		try {
			db = new dbpool();
			coursesList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			if (rs == null)
				System.out.println("rs==null");
			while (rs != null && rs.next()) {
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCourse_time(rs.getFloat("course_time"));
				course.setCredit(rs.getString("credit"));
				course.setMajor_id(rs.getString("major_id"));
				// course.setMajor_name(rs.getString("major_name"));
				course.setExam_type(rs.getString("exam_type"));
				coursesList.add(course);
			}
		} catch (Exception e) {
			System.out.println("exception=" + e);
			
		} finally {
			db.close(rs);
			db = null;
		}
		return coursesList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchOpenCourseNum(java.util.List,
	 *      com.whaty.platform.entity.basic.Semester)
	 */
	public int searchUnOpenCourseNum(List searchproperty, Semester semester)
			throws PlatformException {
		dbpool db = new dbpool();
		String SQL = "select course_id,course_name,major_id,credit,course_time,exam_type from "
				+ "(select b.id as course_id,b.name as course_name,major_id,credit,course_time,exam_type "
				+ "from entity_course_info b where b.id not in "
				+ "(select course_id from entity_course_active where semester_id='"
				+ semester.getId() + "') and b.course_status ='0000')";
		String sql = SQL + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchOpenCourseNum(java.util.List,
	 *      com.whaty.platform.entity.basic.Semester)
	 */
	public int searchOpenCourseNum(List searchproperty, Semester semester)
			throws PlatformException {
		dbpool db = new dbpool();
		String SQL = "select id,course_id,course_name,semester_id,semester_name from "
				+ "(select a.id,a.course_id,b.name as course_name,a.semester_id as semester_id,c.name as semester_name from "
				+ "(select id,course_id,semester_id from entity_course_active where semester_id='"
				+ semester.getId()
				+ " )a,"
				+ "entity_course_info b,entity_semester_info c where a.course_id=b.id and a.semester_id=c.id) ";
		String sql = SQL + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}


	/**
	 * @param teachClasses
	 * @return
	 */
	private String getSearchElectiveScoreSQL(List teachClasses) {
		String SQL = "";
		String teachClassStr = "";
		for (int i = 0; i < teachClasses.size(); i++) {
			teachClassStr = teachClassStr + "'"
					+ ((TeachClass) teachClasses.get(i)).getId() + "',";
		}
		if (teachClassStr.length() > 1) {
			teachClassStr = teachClassStr.substring(0,
					teachClassStr.length() - 1);
			SQL = "select id,student_id,status,total_score,total_scorelevel,total_score_viewlevel,total_scoredes,total_score_zerodes,"
					+ "total_score_zerocause,usual_score,usual_scorelevel,usual_score_viewlevel,usual_scoredes,"
					+ "usual_score_zerodes ,usual_score_zerocause,exam_score,exam_scorelevel,exam_score_viewlevel,"
					+ "exam_scoredes,exam_score_zerodes,exam_score_zerocause,expend_score,"
					+ "expend_scorelevel,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,"
					+ "expend_score_zerocause,renew_score,renew_scorelevel,renew_score_viewlevel,"
					+ "renew_scoredes,renew_score_zerodes,renew_score_zerocause,student_name,teachclass_id,"
					+ "teachclass_name,course_id,course_name from "
					+ "(select a.id,a.student_id as student_id,a.status as status,a.total_score,"
					+ "a.total_scorelevel,a.total_score_viewlevel,a.total_scoredes,a.total_score_zerodes,a.total_score_zerocause,"
					+ "a.usual_score,a.usual_scorelevel,a.usual_score_viewlevel,a.usual_scoredes,a.usual_score_zerodes ,"
					+ "a.usual_score_zerocause,a.exam_score,a.exam_scorelevel,a.exam_score_viewlevel,a.exam_scoredes,"
					+ "a.exam_score_zerodes,a.exam_score_zerocause,a.expend_score,a.expend_scorelevel,a.expend_score_viewlevel,"
					+ "a.expend_scoredes,a.expend_score_zerodes,a.expend_score_zerocause,"
					+ "a.renew_score,a.renew_scorelevel,a.renew_score_viewlevel,a.renew_scoredes,a.renew_score_zerodes,"
					+ "a.renew_score_zerocause,b.name as student_name,a.teachclass_id "
					+ "as teachclass_id,c.name as teachclass_name,d.course_id as course_id,"
					+ "e.name as course_name from (select id,student_id,status,teachclass_id,"
					+ " total_score,total_scorelevel,total_score_viewlevel,total_scoredes,total_score_zerodes,"
					+ "total_score_zerocause,usual_score,usual_scorelevel,usual_score_viewlevel,usual_scoredes,"
					+ "usual_score_zerodes ,usual_score_zerocause,exam_score,exam_scorelevel,exam_score_viewlevel,"
					+ "exam_scoredes,exam_score_zerodes,exam_score_zerocause,expend_score,"
					+ "expend_scorelevel,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,"
					+ "expend_score_zerocause,renew_score,renew_scorelevel,renew_score_viewlevel,"
					+ "renew_scoredes,renew_score_zerodes,renew_score_zerocause "
					+ "from entity_elective where "
					+ "teachclass_id in("
					+ teachClassStr
					+ "))a,entity_student_info b,"
					+ "entity_teach_class c,entity_course_active d,entity_course_info e where a.student_id=b.id "
					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id) ";
		} else {
			SQL = "select id,student_id,status,total_score,total_scorelevel,total_score_viewlevel,total_scoredes,total_score_zerodes,"
					+ "total_score_zerocause,usual_score,usual_scorelevel,usual_score_viewlevel,usual_scoredes,"
					+ "usual_score_zerodes ,usual_score_zerocause,exam_score,exam_scorelevel,exam_score_viewlevel,"
					+ "exam_scoredes,exam_score_zerodes,exam_score_zerocause,expend_score,"
					+ "expend_scorelevel,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,"
					+ "expend_score_zerocause,renew_score,renew_scorelevel,renew_score_viewlevel,"
					+ "renew_scoredes,renew_score_zerodes,renew_score_zerocause,student_name,teachclass_id,"
					+ "teachclass_name,course_id,course_name from "
					+ "(select a.id,a.student_id as student_id,a.status as status,a.total_score,"
					+ "a.total_scorelevel,a.total_score_viewlevel,a.total_scoredes,a.total_score_zerodes,a.total_score_zerocause,"
					+ "a.usual_score,a.usual_scorelevel,a.usual_score_viewlevel,a.usual_scoredes,a.usual_score_zerodes ,"
					+ "a.usual_score_zerocause,a.exam_score,a.exam_scorelevel,a.exam_score_viewlevel,a.exam_scoredes,"
					+ "a.exam_score_zerodes,a.exam_score_zerocause,a.expend_score,a.expend_scorelevel,a.expend_score_viewlevel,"
					+ "a.expend_scoredes,a.expend_score_zerodes,a.expend_score_zerocause,"
					+ "a.renew_score,a.renew_scorelevel,a.renew_score_viewlevel,a.renew_scoredes,a.renew_score_zerodes,"
					+ "a.renew_score_zerocause,b.name as student_name,a.teachclass_id "
					+ "as teachclass_id,c.name as teachclass_name,d.course_id as course_id,"
					+ "e.name as course_name from (select id,student_id,status,teachclass_id,"
					+ " total_score,total_scorelevel,total_score_viewlevel,total_scoredes,total_score_zerodes,"
					+ "total_score_zerocause,usual_score,usual_scorelevel,usual_score_viewlevel,usual_scoredes,"
					+ "usual_score_zerodes ,usual_score_zerocause,exam_score,exam_scorelevel,exam_score_viewlevel,"
					+ "exam_scoredes,exam_score_zerodes,exam_score_zerocause,expend_score,"
					+ "expend_scorelevel,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,"
					+ "expend_score_zerocause,renew_score,renew_scorelevel,renew_score_viewlevel,"
					+ "renew_scoredes,renew_score_zerodes,renew_score_zerocause "
					+ "from entity_elective) a,entity_student_info b,"
					+ "entity_teach_class c,entity_course_active d,entity_course_info e where a.student_id=b.id "
					+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id) ";
		}
		return SQL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchElectiveScoreNum(java.util.List,
	 *      com.whaty.platform.entity.activity.TeachClass)
	 */
	public int searchElectiveScoreNum(List searchproperty, List teachClasses)
			throws PlatformException {
		dbpool db = new dbpool();
		String SQL = getSearchElectiveScoreSQL(teachClasses);
		String sql = SQL + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}


	public List getUnConfirmEleCourses(Page page, String studentId,
			String semesterId, List searchProperty, List orderProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList courseList = new ArrayList();
		OracleStudent student = new OracleStudent(studentId);
		String sql = "select course_id,name,credit,course_time from (select a.course_id,a.name,nvl(b.credit,a.credit) as credit,nvl(b.coursetime,a.course_time) "
				+ "as course_time from(select c.id as course_id,c.credit,c.course_time,c.name from entity_student_info s,"
				+ "entity_elective e,entity_course_active a,entity_teach_class tc ,entity_course_info c where s.id = e.student_id "
				+ "and e.teachclass_id = tc.id and a.id = tc.open_course_id and a.course_id = c.id and e.student_id = '"
				+ studentId
				+ "' and e.status = '0') a,"
				+ "(select tc.course_id,tc.credit,tc.coursetime from entity_teachplan_info ti,entity_teachplan_course tc where "
				+ "ti.major_id = '"
				+ student.getStudentInfo().getMajor_id()
				+ "' and ti.edutype_id = '"
				+ student.getStudentInfo().getEdutype_id()
				+ "' "
				+ "and ti.grade_id = '"
				+ student.getStudentInfo().getGrade_id()
				+ "' and tc.teachplan_id = ti.id)b "
				+ "where a.course_id = b.course_id(+))"
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty);
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("name"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_time(rs.getString("course_time"));
				courseList.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchElectiveScoreNum(java.util.List,
	 *      com.whaty.platform.entity.user.Student)
	 */
	public int searchElectiveScoreNum(List searchproperty, Student student)
			throws PlatformException {
		dbpool db = new dbpool();
		String SQL = "select id,student_id,status,total_score,total_scorelevel,total_score_viewlevel,total_scoredes,total_score_zerodes,"
				+ "total_score_zerocause,usual_score,usual_scorelevel,usual_score_viewlevel,usual_scoredes,"
				+ "usual_score_zerodes ,usual_score_zerocause,exam_score,exam_scorelevel,exam_score_viewlevel,"
				+ "exam_scoredes,exam_score_zerodes,exam_score_zerocause,expend_score,"
				+ "expend_scorelevel,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,"
				+ "expend_score_zerocause,renew_score,renew_scorelevel,renew_score_viewlevel,"
				+ "renew_scoredes,renew_score_zerodes,renew_score_zerocause,student_name,teachclass_id,"
				+ "teachclass_name,course_id,course_name from "
				+ "(select a.id,a.student_id as student_id,a.status as status,a.total_score,"
				+ "a.total_scorelevel,a.total_score_viewlevel,a.total_scoredes,a.total_score_zerodes,a.total_score_zerocause,"
				+ "a.usual_score,a.usual_scorelevel,a.usual_score_viewlevel,a.usual_scoredes,a.usual_score_zerodes ,"
				+ "a.usual_score_zerocause,a.exam_score,a.exam_scorelevel,a.exam_score_viewlevel,a.exam_scoredes,"
				+ "a.exam_score_zerodes,a.exam_score_zerocause,a.expend_score,a.expend_scorelevel,a.expend_score_viewlevel,"
				+ "a.expend_scoredes,a.expend_score_zerodes,a.expend_score_zerocause,"
				+ "a.renew_score,a.renew_scorelevel,a.renew_score_viewlevel,a.renew_scoredes,a.renew_score_zerodes,"
				+ "a.renew_score_zerocause,b.name as student_name,a.teachclass_id "
				+ "as teachclass_id,c.name as teachclass_name,d.course_id as course_id,"
				+ "e.name as course_name from (select id,student_id,status,teachclass_id,"
				+ " total_score,total_scorelevel,total_score_viewlevel,total_scoredes,total_score_zerodes,"
				+ "total_score_zerocause,usual_score,usual_scorelevel,usual_score_viewlevel,usual_scoredes,"
				+ "usual_score_zerodes ,usual_score_zerocause,exam_score,exam_scorelevel,exam_score_viewlevel,"
				+ "exam_scoredes,exam_score_zerodes,exam_score_zerocause,expend_score,"
				+ "expend_scorelevel,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,"
				+ "expend_score_zerocause,renew_score,renew_scorelevel,renew_score_viewlevel,"
				+ "renew_scoredes,renew_score_zerodes,renew_score_zerocause "
				+ "from entity_elective where "
				+ "student_id='"
				+ student.getId()
				+ "')a,entity_student_info b,"
				+ "entity_teach_class c,entity_course_active d,entity_course_info e where a.student_id=b.id "
				+ "and a.teachclass_id=c.id and c.open_course_id=d.id and d.course_id=e.id) ";
		String sql = SQL + Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchElectiveOtherScore(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List, java.util.List)
	 */
	public List searchElectiveOtherScore(Page page, List searchproperty,
			List orderproperty, List teachClasses) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchElectiveOtherScoreNum(java.util.List,
	 *      java.util.List)
	 */
	public int searchElectiveOtherScoreNum(List searchproperty,
			List teachClasses) throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchElectiveOtherScore(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,
	 *      com.whaty.platform.entity.user.Student)
	 */
	public List searchElectiveOtherScore(Page page, List searchproperty,
			List orderproperty, Student student) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchElectiveOtherScoreNum(java.util.List,
	 *      com.whaty.platform.entity.user.Student)
	 */
	public int searchElectiveOtherScoreNum(List searchproperty, Student student)
			throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchTeachClass(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,
	 *      com.whaty.platform.entity.user.Teacher)
	 */
	public List searchTeachClass(Page page, List searchproperty,
			List orderproperty, Teacher teacher) throws PlatformException {
		dbpool db = new dbpool();
		String TEACHCLASSSQL = "select id,name,open_course_id,course_id,course_name,semester_id,semester_name,credit,course_time from "
				+ "(select t.id,t.name,t.open_course_id,b.course_id as course_id,c.name as course_name,"
				+ " s.id as semester_id,s.name as semester_name,c.credit,c.course_time from "
				+ " (select id,teachclass_id from entity_teacher_course where teacher_id='"
				+ teacher.getId()
				+ "') a,"
				+ "entity_teach_class t,entity_course_active b,entity_course_info c,entity_semester_info s "
				+ "where a.teachclass_id=t.id and t.open_course_id=b.id and b.course_id=c.id and b.semester_id=s.id)";
		String sql = TEACHCLASSSQL
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList teachClasses = null;
		try {
			db = new dbpool();
			teachClasses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTeachClass teachClass = new OracleTeachClass();
				teachClass.setId(rs.getString("id"));
				teachClass.setName(rs.getString("name"));
				OracleOpenCourse newOpenCourse = new OracleOpenCourse();
				OracleSemester semester = new OracleSemester();
				newOpenCourse.setId(rs.getString("open_course_id"));
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_time(rs.getString("course_time"));
				semester.setId(rs.getString("semester_id"));
				semester.setName(rs.getString("semester_name"));
//				newOpenCourse.setCourse(course);
//				newOpenCourse.setSemester(semester);
				teachClass.setOpenCourse(newOpenCourse);
				teachClasses.add(teachClass);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachClasses;
	}

	public List searchSiteTeachClass(Page page, List searchproperty,
			List orderproperty, SiteTeacher teacher) throws PlatformException {
		dbpool db = new dbpool();
		String TEACHCLASSSQL = "select id,name,open_course_id,course_id,course_name,semester_id,semester_name,credit,course_time from "
				+ "(select t.id,t.name,t.open_course_id,b.course_id as course_id,c.name as course_name,"
				+ " s.id as semester_id,s.name as semester_name,c.credit,c.course_time from "
				+ " (select id,teachclass_id from entity_siteteacher_course where teacher_id='"
				+ teacher.getId()
				+ "') a,"
				+ "entity_teach_class t,entity_course_active b,entity_course_info c,entity_semester_info s "
				+ "where a.teachclass_id=t.id and t.open_course_id=b.id and b.course_id=c.id and b.semester_id=s.id��and s.selected = '1')";
		String sql = TEACHCLASSSQL
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList teachClasses = null;
		try {
			db = new dbpool();
			teachClasses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleTeachClass teachClass = new OracleTeachClass();
				teachClass.setId(rs.getString("id"));
				teachClass.setName(rs.getString("name"));
				OracleOpenCourse newOpenCourse = new OracleOpenCourse();
				OracleSemester semester = new OracleSemester();
				newOpenCourse.setId(rs.getString("open_course_id"));
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_time(rs.getString("course_time"));
				semester.setId(rs.getString("semester_id"));
				semester.setName(rs.getString("semester_name"));
//				newOpenCourse.setCourse(course);
//				newOpenCourse.setSemester(semester);
				teachClass.setOpenCourse(newOpenCourse);
				teachClasses.add(teachClass);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachClasses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#searchTeachClassNum(java.util.List,
	 *      com.whaty.platform.entity.user.Teacher)
	 */
	public int searchTeachClassNum(List searchproperty, Teacher teacher)
			throws PlatformException {
		dbpool db = new dbpool();
		String TEACHCLASSSQL = "select id,name,open_course_id,course_id,course_name,semester_id,semester_name from "
				+ "(select a.id,a.name,a.open_course_id,b.course_id as course_id,c.name as course_name,"
				+ " s.id as semester_id,s.name as semester_name from "
				+ " (select id,name,open_course_id from entity_teach_class where teacher_id='"
				+ teacher.getId()
				+ ") a,"
				+ "entity_course_active b,entity_course_info c,entity_semester_info s "
				+ "where a.open_course_id=b.id and b.course_id=c.id and b.semester_id=s.id)";
		String sql = TEACHCLASSSQL
				+ Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public int searchSiteTeachClassNum(List searchproperty, SiteTeacher teacher)
			throws PlatformException {
		dbpool db = new dbpool();
		String TEACHCLASSSQL = "select id,name,open_course_id,course_id,course_name,semester_id,semester_name,credit,course_time from "
				+ "(select t.id,t.name,t.open_course_id,b.course_id as course_id,c.name as course_name,"
				+ " s.id as semester_id,s.name as semester_name,c.credit,c.course_time from "
				+ " (select id,teachclass_id from entity_siteteacher_course where teacher_id='"
				+ teacher.getId()
				+ "') a,"
				+ "entity_teach_class t,entity_course_active b,entity_course_info c,entity_semester_info s "
				+ "where a.teachclass_id=t.id and t.open_course_id=b.id and b.course_id=c.id and b.semester_id=s.id and s.selected = '1')";
		String sql = TEACHCLASSSQL
				+ Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List searchGraduateConditions(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public int searchGraduateConditionsNum(List searchproperty)
			throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List searchAllGraduateConditions(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select major_id,major_name,grade_id,grade_name,edutype_id,edutype_name,id from (select a.major_id,a.major_name,a.grade_id,a.grade_name,a.edutype_id,a.edutype_name,nvl(d.id,'0') as id from (select distinct a.id as major_id,a.name as major_name,b.id as grade_id,b.name as grade_name,c.id as edutype_id,c.name as edutype_name from entity_major_info a,entity_grade_info b,entity_edu_type c,(select distinct major_id,grade_id,edutype_id from entity_student_info where status='0') s where a.id<>'0' and a.id=s.major_id and b.id=s.grade_id and c.id=s.edutype_id) a,entity_graduate_record d where a.major_id=d.major_id(+) and a.grade_id=d.grade_id(+) and a.edutype_id=d.edutype_id(+)) "
				+ Conditions.convertToCondition(searchproperty, null);
		MyResultSet rs = null;
		ArrayList conditions = null;
		try {
			db = new dbpool();
			conditions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleGraduateCondition condition = new OracleGraduateCondition();
				condition.setId(rs.getString("id"));
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				major.setName(rs.getString("major_name"));
				condition.setMajor(major);
				OracleGrade grade = new OracleGrade();
				grade.setId(rs.getString("grade_id"));
				grade.setName(rs.getString("grade_name"));
				condition.setGrade(grade);
				OracleEduType eduType = new OracleEduType();
				eduType.setId(rs.getString("edutype_id"));
				eduType.setName(rs.getString("edutype_name"));
				condition.setEduType(eduType);
				conditions.add(condition);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return conditions;
	}

	public int searchAllGraduateConditionsNum(List searchproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select major_id,major_name,grade_id,grade_name,edutype_id,edutype_name,id from (select a.major_id,a.major_name,a.grade_id,a.grade_name,a.edutype_id,a.edutype_name,nvl(d.id,'0') as id from (select distinct a.id as major_id,a.name as major_name,b.id as grade_id,b.name as grade_name,c.id as edutype_id,c.name as edutype_name from entity_major_info a,entity_grade_info b,entity_edu_type c,(select distinct major_id,grade_id,edutype_id from entity_student_info where status='0') s where a.id<>'0' and a.id=s.major_id and b.id=s.grade_id and c.id=s.edutype_id) a,entity_graduate_record d where a.major_id=d.major_id(+) and a.grade_id=d.grade_id(+) and a.edutype_id=d.edutype_id(+)) "
				+ Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List searchOpenCourseMajor(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		String SQL = "select course_id,course_name, credit,course_time,major_name,open_course_id,semester_id from (select a.id as course_id,a.name as course_name,a.credit as credit,a.course_time as course_time,b.name as major_name, c.id as open_course_id,c.semester_id as semester_id  from entity_course_info a,entity_major_info b,entity_course_active c where a.major_id=b.id and c.course_id=a.id) ";
		String sql = SQL
				+ Conditions.convertToCondition(searchproperty, orderproperty);

		MyResultSet rs = null;
		ArrayList coursesList = null;
		try {
			db = new dbpool();
			coursesList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			if (rs == null)
				System.out.println("rs==null");
			while (rs != null && rs.next()) {
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCourse_time(rs.getFloat("course_time"));
				course.setCredit(rs.getString("credit"));
				course.setMajor_id(rs.getString("open_course_id"));
				course.setMajor_name(rs.getString("major_name"));

				coursesList.add(course);
			}
		} catch (Exception e) {
			System.out.println("exception=" + e);
			
		} finally {
			db.close(rs);
			db = null;
		}
		return coursesList;
	}

	
	
	
	public List getOpencourseAndTeachersBySemester(Page page, List searchproperty, List orderproperty) throws PlatformException {
		dbpool db = null;
		List OrderProperties = new ArrayList();
		if (orderproperty == null) {
			OrderProperties.add(new OrderProperty("course_id"));
		} else {
			OrderProperties = orderproperty;
		}
		String sql = "select course_id,course_name,teacher_id,teacher_gh,teacher_name from (select distinct eci.id as course_id,eci.name as course_name,eti.id as teacher_id,eti.gh as teacher_gh,eti.name as teacher_name,eca.semester_id as semester_id from entity_course_info eci,entity_course_active eca,entity_teach_class etc1,entity_teacher_course etc2,entity_teacher_info eti where eci.id = eca.course_id and eca.id = etc1.open_course_id and etc1.id = etc2.teachclass_id and etc2.teacher_id = eti.id) "
				+ Conditions
						.convertToCondition(searchproperty, OrderProperties);
		MyResultSet rs = null;
		ArrayList openCourses = null;
		try {
			db = new dbpool();
			openCourses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				List list = new ArrayList() ;
				String course_id = rs.getString("course_id") ;
				String course_name = rs.getString("course_name") ;
				String teacher_id = rs.getString("teacher_id") ;
				String teacher_gh = rs.getString("teacher_gh") ;
				String teacher_name = rs.getString("teacher_name") ;
				list.add(course_id) ;
				list.add(course_name) ;
				list.add(teacher_id) ;
				list.add(teacher_gh) ;
				list.add(teacher_name) ;
				openCourses.add(list) ;
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return openCourses;
	}

	public int getOpencourseAndTeachersBySemesterNum(List searchproperty) throws PlatformException {
		dbpool db = new dbpool();
		String SQL = "select course_id,course_name,teacher_id,teacher_gh,teacher_name from (select distinct eci.id as course_id,eci.name as course_name,eti.id as teacher_id,eti.gh as teacher_gh,eti.name as teacher_name,eca.semester_id as semester_id from entity_course_info eci,entity_course_active eca,entity_teach_class etc1,entity_teacher_course etc2,entity_teacher_info eti where eci.id = eca.course_id and eca.id = etc1.open_course_id and etc1.id = etc2.teachclass_id and etc2.teacher_id = eti.id) ";
		String sql = SQL
				+ Conditions.convertToCondition(searchproperty,null);

		return db.countselect(sql);
	}

	public int searchOpenCourseMajorNum(List searchproperty, List orderproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String SQL = "select course_id,course_name, credit,course_time,major_name,open_course_id,semester_id from (select a.id as course_id,a.name as course_name,a.credit as credit,a.course_time as course_time,b.name as major_name, c.id as open_course_id,c.semester_id as semester_id  from entity_course_info a,entity_major_info b,entity_course_active c where a.major_id=b.id and c.course_id=a.id) ";
		String sql = SQL
				+ Conditions.convertToCondition(searchproperty, orderproperty);

		return db.countselect(sql);
	}

	public List searchRegSemester(Page page, Student student)
			throws PlatformException {
		// TODO �Զ���ɷ������
		dbpool db = new dbpool();
		String user_id = student.getId();
		String sql = "select semester_id, user_id, semester_name, start_elective, end_elective  "
				+ "from (select t.semester_id, t.user_id as user_id, esi.name as semester_name, "
				+ "to_char(esi.start_elective,'yyyy-mm-dd') as start_elective, "
				+ "to_char(esi.end_elective,'yyyy-mm-dd') as end_elective "
				+ "from entity_register_info t, sso_user su, entity_semester_info esi "
				+ "where t.user_id=su.id and esi.id=t.semester_id  and t.user_id='"
				+ user_id + "') order by start_elective desc";

		MyResultSet rs = null;
		ArrayList semesters = null;
		try {
			db = new dbpool();
			semesters = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}

			while (rs != null && rs.next()) {
				OracleSemester semester = new OracleSemester();
				semester.setId(rs.getString("semester_id"));
				semester.setName(rs.getString("semester_name"));
				semester.setStart_elective(rs.getString("start_elective"));
				semester.setEnd_elective(rs.getString("end_elective"));
				semesters.add(semester);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return semesters;
	}

	public int searchRegSemesterNum(Student student) throws PlatformException {
		// TODO �Զ���ɷ������
		dbpool db = new dbpool();
		String user_id = student.getId();
		String sql = "select semester_id, user_id, semester_name, start_elective, end_elective  "
				+ "from (select t.semester_id, t.user_id as user_id, esi.name as semester_name, "
				+ "to_char(esi.start_elective,'yyyy-mm-dd') as start_elective, "
				+ "to_char(esi.end_elective,'yyyy-mm-dd') as end_elective "
				+ "from entity_register_info t, sso_user su, entity_semester_info esi "
				+ "where t.user_id=su.id and esi.id=t.semester_id  and t.user_id='"
				+ user_id + "')";

		return db.countselect(sql);
	}

	public int addElectives(String[] opencourse_ids, String semester_id,
			Student student) {
		// TODO �Զ���ɷ������
		String student_id = student.getId();
		dbpool db = new dbpool();
		String sql = "delete from entity_elective t where t.student_id='"
				+ student_id + "' "
				+ "and t.teachclass_id in (select a.id from "
				+ "entity_teach_class a, entity_course_active b "
				+ "where a.open_course_id=b.id and b.semester_id='"
				+ semester_id + "') and t.status='0'";
		db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleBasicActivityList.addElectives(String[] opencourse_ids, String semester_id,Student student) SQL=" + sql + " DATE=" + new Date());
		int count = 0;
		for (int i = 0; i < opencourse_ids.length; i++) {

			sql = "insert into entity_elective(id, student_id, "
					+ "teachclass_id, status) "
					+ "values(s_Elective_Id.Nextval, " + "'" + student_id
					+ "', (select id from entity_teach_class t "
					+ "where t.open_course_id = '" + opencourse_ids[i] + "'), "
					+ "'0' )";
			count = count + db.executeUpdate(sql);
			UserAddLog.setDebug("OracleBasicActivityList.addElectives(String[] opencourse_ids, String semester_id,Student student) SQL=" + sql + " DATE=" + new Date());
		}

		return count;
	}

 
	public void addElectives(String[] opencourse_ids,
			String[] select_opencourse_ids, String semester_id,
			String studentId, boolean needConfirm) {

		String status;
		if (needConfirm)
			status = "0";
		else
			status = "1";
		String opencourse_str = "";
		String select_opencourse_str = "";
		for (int i = 0; i < opencourse_ids.length; i++) {
			opencourse_str += opencourse_ids[i] + ",";
		}
		if (opencourse_str.length() > 0)
			opencourse_str = opencourse_str.substring(0, opencourse_str
					.length() - 1);

		for (int i = 0; i < select_opencourse_ids.length; i++) {
			select_opencourse_str += select_opencourse_ids[i] + ",";
		}
		if (select_opencourse_str.length() > 0)
			select_opencourse_str = select_opencourse_str.substring(0,
					select_opencourse_str.length() - 1);
		ArrayList sqlgroup = new ArrayList();
		dbpool db = new dbpool();
		String sql = "delete from entity_elective t where t.student_id='"
				+ studentId + "' "
				+ "and t.teachclass_id in (select a.id from "
				+ "entity_teach_class a, entity_course_active b "
				+ "where a.open_course_id=b.id and b.semester_id='"
				+ semester_id + "' and a.open_course_id in (" + opencourse_str
				+ ") and " + "a.open_course_id not in ("
				+ select_opencourse_str + ")) and t.status='0'";
		sqlgroup.add(sql);
		UserDeleteLog.setDebug("OracleBasicActivityList.addElectives(String[] opencourse_ids,String[] select_opencourse_ids, String semester_id,String studentId, boolean needConfirm) SQL=" + sql + " DATE=" + new Date());
		String esql = "insert into entity_elective (id, student_id, "
				+ "teachclass_id, status) select s_Elective_Id.Nextval,'"
				+ studentId
				+ "',id,'"
				+ status
				+ "'"
				+ " from (select id from entity_teach_class where open_course_id in ("
				+ select_opencourse_str
				+ ") "
				+ "minus select teachclass_id from entity_elective where student_id = '"
				+ studentId + "')";
		sqlgroup.add(esql);
		UserAddLog.setDebug("OracleBasicActivityList.addElectives(String[] opencourse_ids,String[] select_opencourse_ids, String semester_id,String studentId, boolean needConfirm) SQL=" + esql + " DATE=" + new Date());
		db.executeUpdateBatch(sqlgroup);
	}

	public int addElectivesWithFee(String[] opencourse_ids,
			String[] select_opencourse_ids, String semester_id,
			String studentId, boolean needConfirm) {

		String status;
		if (needConfirm)
			status = "0";
		else
			status = "1";
		String opencourse_str = "";
		String select_opencourse_str = "";
		for (int i = 0; i < opencourse_ids.length; i++) {
			opencourse_str += opencourse_ids[i] + ",";
		}
		if (opencourse_str.length() > 0)
			opencourse_str = opencourse_str.substring(0, opencourse_str
					.length() - 1);

		for (int i = 0; i < select_opencourse_ids.length; i++) {
			select_opencourse_str += select_opencourse_ids[i] + ",";
		}
		if (select_opencourse_str.length() > 0)
			select_opencourse_str = select_opencourse_str.substring(0,
					select_opencourse_str.length() - 1);
		ArrayList sqlgroup = new ArrayList();
		dbpool db = new dbpool();
		String sql = "delete from entity_elective t where t.student_id='"
				+ studentId + "' "
				+ "and t.teachclass_id in (select a.id from "
				+ "entity_teach_class a, entity_course_active b "
				+ "where a.open_course_id=b.id and b.semester_id='"
				+ semester_id + "' and a.open_course_id in (" + opencourse_str
				+ ") and " + "a.open_course_id not in ("
				+ select_opencourse_str + ")) and t.status='0'";
		
		// String feeUpdateSql = "update entity_userfee_record set payout_type = '"+PayoutType.ROLLBACK+"' where user_id = '"+studentId+"' and teachclass_id  in (select a.id from entity_teach_class a, entity_course_active b where a.open_course_id = b.id  and b.semester_id = '" + semester_id + "' and a.open_course_id in ("+opencourse_str +")  and + a.open_course_id not in ("+select_opencourse_str +"))  and payout_type = '"+PayoutType.CONSUME+"'";
		//��ԭ�ȵ��޸ķ��ü�¼״̬��Ϊ�����������ü�¼�����һ���˷��ü�¼
		String feeUpdateSql ="insert into entity_userfee_record (id,user_id,date_time,fee_value,fee_type,payout_type,note,checked,invoice_status,teachclass_id)  select s_entity_fee_id.nextval, b.user_id,sysdate,abs(b.fee_value),b.fee_type,b.payout_type,b.note,b.checked,b.invoice_status,b.teachclass_id from (select distinct src.user_id,sysdate,src.fee_value,src.fee_type,'"+PayoutType.ROLLBACK+"' as PAYOUT_TYPE,src.note,src.checked,src.invoice_status,src.teachclass_id from entity_userfee_record src where src.user_id = '"+studentId+"' and src.teachclass_id  in (select a.id from entity_teach_class a, entity_course_active b,entity_elective e where a.open_course_id = b.id    and e.teachclass_id = a.id and e.student_id = '"+studentId+"' and b.semester_id = '" + semester_id + "' and a.open_course_id in ("+opencourse_str +")  and  a.open_course_id not in ("+select_opencourse_str +"))  and src.payout_type = '"+PayoutType.CONSUME+"') b";
			 
		 
		sqlgroup.add(feeUpdateSql);
		sqlgroup.add(sql);
		
		UserDeleteLog.setDebug("OracleBasicActivityList.addElectivesWithFee(String[] opencourse_ids,String[] select_opencourse_ids, String semester_id,String studentId, boolean needConfirm) SQL=" + sql + " DATE=" + new Date());
		String esql = "insert into entity_elective (id, student_id, "
				+ "teachclass_id, status) select s_Elective_Id.Nextval,'"
				+ studentId
				+ "',id,'"
				+ status
				+ "'"
				+ " from (select id from entity_teach_class where open_course_id in ("
				+ select_opencourse_str
				+ ") "
				+ "minus select teachclass_id from entity_elective where student_id = '"
				+ studentId + "')";
		sqlgroup.add(esql);
		UserAddLog.setDebug("OracleBasicActivityList.addElectivesWithFee(String[] opencourse_ids,String[] select_opencourse_ids, String semester_id,String studentId, boolean needConfirm) SQL=" + esql + " DATE=" + new Date());
		String feeSql = "insert into entity_userfee_record(id,user_id,fee_value,fee_type,payout_type,checked,note,teachclass_id) "
				+ "select s_entity_fee_id.nextval,'"
				+ studentId
				+ "',value,'"
				+ FeeType.CREDIT
				+ "','"
				+ PayoutType.CONSUME
				+ "','1',note,teachclass_id "
				+ "from (select distinct -(e.credit* f.creditfee) as value,concat(concat(concat(concat(concat(concat('ѡ�� �γ���ƣ�','('||e.course_id||')'||e.course_name),'���γ�ѧ�֣�'),e.credit),'��ѧ�ֱ�׼��ÿѧ�֣�'),f.creditfee),'Ԫ') as note,teachclass_id "
				+ "from (select nvl(a.credit,b.credit) as credit,a.course_id,a.course_name,a.teachclass_id from (select tc.credit,tc.course_id,c.name as course_name ,etc.id as teachclass_id from "
				+ "entity_course_active a,entity_course_info c,entity_teachplan_course tc,entity_teachplan_info i,entity_student_info s,  entity_teach_class      etc where a.id in ("
				+ select_opencourse_str
				+ ")  "
				+ "and tc.course_id = c.id and a.course_id = c.id and i.edutype_id = s.edutype_id and i.major_id = s.major_id and i.grade_id = s.grade_id  and etc.open_course_id = a.id and s.id = '"
				+ studentId
				+ "' "
				+ "and tc.teachplan_id = i.id)a,(select a.course_id as course_id,c.credit  from entity_course_info c,entity_course_active a where a.id in ("
				+ select_opencourse_str
				+ ") and a.course_id = c.id)b "
				+ "where b.course_id = a.course_id(+))e,(select type1_value as creditfee from entity_userfee_level where user_id = '"
				+ studentId + "')f) ";
		sqlgroup.add(feeSql);
		UserAddLog.setDebug("OracleBasicActivityList.addElectivesWithFee(String[] opencourse_ids,String[] select_opencourse_ids, String semester_id,String studentId, boolean needConfirm) SQL=" + feeSql + " DATE=" + new Date());
		// System.out.print(feeSql);
		return db.executeUpdateBatch(sqlgroup);
	}

	// ��ÿγ̵�ѡ��ѧ����
	public int getElectiveStudentNumByOpenCourseId(List searchProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String ELECTIVE_STUDENTNUM_SQL = "select open_course_id,status,count "
				+ "from (select open_course_id,status,count(student_id) as count "
				+ "from (select a.student_id,a.status,b.open_course_id "
				+ "from entity_elective a,entity_teach_class b where a.teachclass_id=b.id) group by open_course_id,status) ";
		String sql = ELECTIVE_STUDENTNUM_SQL
				+ Conditions.convertToCondition(searchProperty, null);
		return db.countselect(sql);
	}

	public List getGraduateStat(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List statList = new ArrayList();
		String sql = "select nvl(graduate_num,0) as graduate_num,total_num,b.grade_id,c.name as grade_name,b.edutype_id,d.name as edutype_name,b.major_id,e.name as major_name from (select count(id) as graduate_num,grade_id,edutype_id,major_id from entity_student_info where isgraduated='1' "
				+ Conditions.convertToAndCondition(searchproperty,
						orderproperty)
				+ " group by grade_id,edutype_id,major_id) a,(select count(id) as total_num,grade_id,edutype_id,major_id from entity_student_info "
				+ Conditions.convertToCondition(searchproperty, orderproperty)
				+ " group by grade_id,edutype_id,major_id) b,entity_grade_info c,entity_edu_type d,entity_major_info e where b.grade_id=a.grade_id(+) and b.major_id=a.major_id(+) and b.edutype_id=a.edutype_id(+) and b.grade_id=c.id and b.edutype_id=d.id and b.major_id=e.id";
		rs = db.executeQuery(sql);
		int total_num = 0;
		int graduate_num = 0;
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String per = "";
		try {
			while (rs != null && rs.next()) {
				String[] stat = new String[7];
				stat[0] = rs.getString("grade_name");
				stat[1] = rs.getString("edutype_name");
				stat[2] = rs.getString("major_name");
				stat[3] = rs.getString("total_num");
				total_num = rs.getInt("total_num");
				stat[4] = rs.getString("graduate_num");
				graduate_num = rs.getInt("graduate_num");
				stat[5] = Integer.toString(total_num - graduate_num);
				if (total_num != 0)
					if (graduate_num != 0 && total_num != 0)
						per = df.format((100 * graduate_num) / total_num);
					else
						per = "0";
				else
					per = "0";
				stat[6] = per;
				statList.add(stat);
			}
		} catch (SQLException e) {
			
		}
		db.close(rs);
		return statList;
	}

	public int AddTeachClassCware(TeachClass teachClass, Courseware courseware)
			throws PlatformException {
		// TODO �Զ���ɷ������
		OracleTeachClassCware teachClassCware = new OracleTeachClassCware();
		teachClassCware.setCourseware(courseware);
		teachClassCware.setTeachClass(teachClass);
		// teachClassCware.setActive("1");
		teachClassCware.add();
		return 0;
	}

	// ���ͳ���γ�
	public List getUniteExamCoursesByName(Page page, List searchProperty,
			List orderProperty) throws PlatformException {

		dbpool db = new dbpool();
		String UNITEEXAMCOURSESQL = "select id,name,note from entity_uniteexam_course ";
		String sql = UNITEEXAMCOURSESQL
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList uniteExamCourses = null;
		try {
			db = new dbpool();
			uniteExamCourses = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleUniteExamCourse uniteExamCourse = new OracleUniteExamCourse();
				uniteExamCourse.setId(rs.getString("id"));
				uniteExamCourse.setName(rs.getString("name"));
				uniteExamCourse.setNote(rs.getString("note"));
				uniteExamCourses.add(uniteExamCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return uniteExamCourses;
	}

	public int getUniteExamCourseNumByName(List searchProperty)
			throws PlatformException {

		dbpool db = new dbpool();
		String UNITEEXAMCOURSESQL = "select id,name,note from entity_uniteexam_course where 1=1 ";
		String sql = UNITEEXAMCOURSESQL
				+ Conditions.convertToAndCondition(searchProperty);
		return db.countselect(sql);
	}

	public List getUniteExamScores(Page page, List searchProperty,
			List orderProperty) throws PlatformException {
		dbpool db = new dbpool();
		String UNITEEXAMSCORESQL = "select id,reg_no,name,site_id,site_name,major_id,major_name,"
				+ "edutype_id,edutype_name,grade_id,course_id,course_name,score "
				+ "from (select rsi.id,rsi.reg_no,rsi.name,rsi.site_id,esi.name as site_name,"
				+ "rsi.major_id,emi.name as major_name,rsi.edutype_id,eet.name as edutype_name,"
				+ "rsi.grade_id,euc.id as course_id,euc.name as course_name,eus.score "
				+ "from entity_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet,"
				+ "entity_uniteexam_score eus,entity_uniteexam_course euc "
				+ "where rsi.reg_no in (select distinct reg_no from entity_uniteexam_score) "
				+ "and rsi.major_id=emi.id and rsi.edutype_id=eet.id and rsi.site_id=esi.id "
				+ "and eus.reg_no=rsi.reg_no and eus.course_id=euc.id) ";
		String sql = UNITEEXAMSCORESQL
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList uniteExamScores = null;
		try {
			db = new dbpool();
			uniteExamScores = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleUniteExamScore uniteExamScore = new OracleUniteExamScore();
				OracleUniteExamCourse course = new OracleUniteExamCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				uniteExamScore.setCourse(course);
				OracleStudent student = new OracleStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				StudentEduInfo studentInfo = new StudentEduInfo();
				studentInfo.setReg_no(rs.getString("reg_no"));
				studentInfo.setSite_id(rs.getString("site_id"));
				studentInfo.setSite_name(rs.getString("site_name"));
				studentInfo.setEdutype_id(rs.getString("edutype_id"));
				studentInfo.setEdutype_name(rs.getString("edutype_name"));
				studentInfo.setMajor_id(rs.getString("major_id"));
				studentInfo.setMajor_name(rs.getString("major_name"));
				studentInfo.setGrade_id(rs.getString("grade_id"));
				// studentInfo.setGrade_name(rs.getString("grade_name"));
				student.setStudentInfo(studentInfo);
				uniteExamScore.setStudent(student);
				uniteExamScore.setScore(rs.getString("score"));
				uniteExamScores.add(uniteExamScore);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return uniteExamScores;
	}

	public int getUniteExamScoreNum(List searchProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String UNITEEXAMSCORESQL = "select id,reg_no,name,site_id,site_name,major_id,major_name,"
				+ "edutype_id,edutype_name,grade_id,course_id,course_name,score "
				+ "from (select rsi.id,rsi.reg_no,rsi.name,rsi.site_id,esi.name as site_name,"
				+ "rsi.major_id,emi.name as major_name,rsi.edutype_id,eet.name as edutype_name,"
				+ "rsi.grade_id,euc.id as course_id,euc.name as course_name,eus.score "
				+ "from entity_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet,"
				+ "entity_uniteexam_score eus,entity_uniteexam_course euc "
				+ "where rsi.reg_no in (select distinct reg_no from entity_uniteexam_score) "
				+ "and rsi.major_id=emi.id and rsi.edutype_id=eet.id and rsi.site_id=esi.id "
				+ "and eus.reg_no=rsi.reg_no and eus.course_id=euc.id) where 1=1 ";
		String sql = UNITEEXAMSCORESQL
				+ Conditions.convertToAndCondition(searchProperty);
		return db.countselect(sql);
	}

	public List addBatchUniteExamScore(String src, String filename,
			boolean isUpdate) throws PlatformException {
		List retList = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			Workbook w = Workbook.getWorkbook(new File(src + File.separator
					+ filename));
			Sheet sheet = w.getSheet(0);
			// int columns = sheet.getColumns();
			int rows = sheet.getRows();
			Cell cell;
			String id = "";
			String regNo = "";
			String name = "";
			String courseName = "";
			String courseId = "";
			String score = "";
			String resultStr[][] = new String[rows - 2][2];
			int success = 0;// ��¼����ɹ���¼����
			for (int i = 2; i < rows; i++) {
				cell = sheet.getCell(0, i);
				if (cell.getContents() == null || cell.getContents().equals("")) {
					resultStr[i - 2][0] = "0";// 0Ϊʧ��;1Ϊ�ɹ�
					resultStr[i - 2][1] = "ѧ������Ϊ��";
					continue;
				} else {
					regNo = cell.getContents().trim();
					String sqlRegNo = "select id,name from entity_student_info where reg_no='"
							+ regNo + "'";
					int r = db.countselect(sqlRegNo);
					if (r < 1) {
						resultStr[i - 2][0] = "0";// 0Ϊʧ��;1Ϊ�ɹ�
						resultStr[i - 2][1] = "��ѧ��ѧ�����";
						continue;
					}
				}
				cell = sheet.getCell(1, i);
				if (cell.getContents() == null || cell.getContents().equals("")) {
					resultStr[i - 2][0] = "0";// 0Ϊʧ��;1Ϊ�ɹ�
					resultStr[i - 2][1] = "��������Ϊ��";
					continue;
				} else {
					name = cell.getContents().trim();
					String sqlName = "select id from entity_student_info where reg_no='"
							+ regNo + "' and name='" + name + "'";
					int r = db.countselect(sqlName);
					if (r < 1) {
						resultStr[i - 2][0] = "0";// 0Ϊʧ��;1Ϊ�ɹ�
						resultStr[i - 2][1] = "������ѧ�����";
						continue;
					}
				}
				int courseCount = getUniteExamCourseNumByName(null);
				List courseList = getUniteExamCoursesByName(null, null, null);
				// �жϵ�ǰ�гɼ���Ϣ�Ƿ��п�ֵ�������򷵻�ʧ��
				boolean isFail = false;
				for (int l = 0; l < courseCount; l++) {
					cell = sheet.getCell(2 + l, i);
					if (cell.getContents() != null
							&& !cell.getContents().equals(""))
						continue;
					else {
						courseName = ((OracleUniteExamCourse) courseList.get(l))
								.getName();
						resultStr[i - 2][0] = "0";// 0Ϊʧ��;1Ϊ�ɹ�
						resultStr[i - 2][1] = "��Ŀ:" + courseName + " �ɼ�����Ϊ��";
						isFail = true;
						break;
					}
				}
				if (isFail)
					continue;
				// �жϽ���
				for (int j = 0; j < courseCount; j++) {

					cell = sheet.getCell(2 + j, i);
					sql = "select to_char(s_uniteexam_course_id.nextval) as id from dual";
					rs = db.executeQuery(sql);
					if (rs != null && rs.next())
						id = rs.getString("id");
					db.close(rs);
					score = cell.getContents().trim();
					courseName = ((OracleUniteExamCourse) courseList.get(j))
							.getName();
					courseId = ((OracleUniteExamCourse) courseList.get(j))
							.getId();
					sql = "select id from entity_uniteexam_score where reg_no='"
							+ regNo + "' and course_id='" + courseId + "'";
					int res = db.countselect(sql);
					String sqlUpdate = "update entity_uniteexam_score set score='"
							+ score
							+ "' where reg_no='"
							+ regNo
							+ "' and course_id='" + courseId + "'";
					String sqlInsert = "insert into entity_uniteexam_score values('"
							+ id
							+ "','"
							+ courseId
							+ "','"
							+ courseName
							+ "','"
							+ score
							+ "','"
							+ regNo
							+ "','"
							+ name
							+ "')";
					if (res > 0) {
						if (isUpdate) {
							res = db.executeUpdate(sqlUpdate);
							UserUpdateLog.setDebug("OracleBasicActivityList.addBatchUniteExamScore(String src, String filename,boolean isUpdate) SQL=" + sqlUpdate + " DATE=" + new Date());
						} else {
							resultStr[i - 2][0] = "0";
							resultStr[i - 2][1] = "��Ŀ: " + courseName
									+ "  �ɼ���Ϣ�Ѵ���,����ʧ��!";
							break;
						}
					} else {
						res = db.executeUpdate(sqlInsert);
						UserAddLog.setDebug("OracleBasicActivityList.addBatchUniteExamScore(String src, String filename,boolean isUpdate) SQL=" + sqlInsert + " DATE=" + new Date());
					}
					if (res < 1) {
						resultStr[i - 2][0] = "0";
						resultStr[i - 2][1] = "�����Ŀ: " + courseName
								+ "  �ɼ�����ݿ�ʧ��!";
						break;
					} else {
						resultStr[i - 2][0] = "1";
						resultStr[i - 2][1] = "";
					}
				}
				if (resultStr[i - 2][0].equals("1"))
					success++;
			}
			retList.add(new Integer(success));
			retList.add(resultStr);
			return retList;
		} catch (IOException e) {
			throw new PlatformException("���ص�excel�ļ�������!(" + src
					+ File.separator + filename + ")");
		} catch (BiffException e) {
			throw new PlatformException("��ȡexcel�ļ����!");
		} catch (SQLException e) {
			throw new PlatformException("SQL�쳣: sql: " + sql);
		}
	}

	public List getElectiveStudentsByCourseAndSemester(Page page,
			String siteId, String semesterId, String courseId)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select id,name,reg_no,id_card,card_type,major_id,major_name,edutype_id,edutype_name,grade_id"
				+ ",grade_name from (select a.id,a.name,a.reg_no,a.id_card,card_type,b.id as major_id,b.name as major_name,"
				+ "c.id as edutype_id,c.name as edutype_name,d.id as grade_id,d.name as grade_name "
				+ "from entity_student_info a,entity_major_info b,entity_edu_type c,entity_grade_info d "
				+ "where a.id in (select distinct student_id from entity_elective "
				+ "where status='1' and teachclass_id in (select id from entity_teach_class "
				+ "where open_course_id in (select id from entity_course_active "
				+ "where course_id='"
				+ courseId
				+ "' and semester_id='"
				+ semesterId
				+ "'))) "
				+ "and site_id='"
				+ siteId
				+ "' and a.major_id=b.id and a.edutype_id=c.id and a.grade_id=d.id)";
		MyResultSet rs = null;
		ArrayList students = null;
		try {
			db = new dbpool();
			students = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				StudentEduInfo studentInfo = new StudentEduInfo();
				studentInfo.setReg_no(rs.getString("reg_no"));
				studentInfo.setEdutype_id(rs.getString("edutype_id"));
				studentInfo.setEdutype_name(rs.getString("edutype_name"));
				studentInfo.setMajor_id(rs.getString("major_id"));
				studentInfo.setMajor_name(rs.getString("major_name"));
				studentInfo.setGrade_id(rs.getString("grade_id"));
				studentInfo.setGrade_name(rs.getString("grade_name"));
				student.setStudentInfo(studentInfo);
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				normalInfo.setIdcard(rs.getString("id_card"));
				normalInfo.setCardType(rs.getString("card_type"));
				student.setNormalInfo(normalInfo);
				students.add(student);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return students;
	}

	public int getElectiveStudentsByCourseAndSemesterNum(String siteId,
			String semesterId, String courseId) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select id,name,reg_no,id_card,card_type,major_id,major_name,edutype_id,edutype_name,grade_id"
				+ ",grade_name from (select a.id,a.name,a.reg_no,a.id_card,card_type,b.id as major_id,b.name as major_name,"
				+ "c.id as edutype_id,c.name as edutype_name,d.id as grade_id,d.name as grade_name "
				+ "from entity_student_info a,entity_major_info b,entity_edu_type c,entity_grade_info d "
				+ "where a.id in (select distinct student_id from entity_elective "
				+ "where status='1' and teachclass_id in (select id from entity_teach_class "
				+ "where open_course_id in (select id from entity_course_active "
				+ "where course_id='"
				+ courseId
				+ "' and semester_id='"
				+ semesterId
				+ "'))) "
				+ "and site_id='"
				+ siteId
				+ "' and a.major_id=b.id and a.edutype_id=c.id and a.grade_id=d.id)";
		return db.countselect(sql);
	}

	/*
	 * ��ѯѧ��ѡ����Ϣ�����ѧ�ڣ�ѧ��ID��
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#getElectiveByUserIdAndSemester(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,Student)
	 */
	public List getElectiveByUserIdAndSemester(Page page, List searchproperty,
			List orderproperty, Student student) throws PlatformException {
		dbpool db = new dbpool();
		String SQL;
		if (student != null)
			SQL = "select id,student_id,student_name,teachplan_id,course_id,plan_credit,plan_course_time,"
					+ "course_plantype,credit,course_name,course_time,status,semester_id "
					+ " from (select e.id,e.student_id,f.student_name,f.teachplan_id,e.course_id,nvl(f.plan_credit,'0') as plan_credit,nvl(f.plan_course_time,'0') as plan_course_time,"
					+ "nvl(course_plantype,'0') as course_plantype,e.credit,e.course_name,e.course_time,e.status,e.semester_id "
					+ "from (select ee.id,ee.student_id,ee.status,eci.id as course_id,eci.name as course_name,"
					+ "eci.credit,eci.course_time,eca.semester_id from entity_elective ee,entity_teach_class etc,"
					+ "entity_course_active eca,entity_course_info eci where ee.student_id='"
					+ student.getId()
					+ "'"
					+ " and ee.teachclass_id=etc.id and etc.open_course_id=eca.id and eca.course_id=eci.id) e left join "
					+ "(select c.id as student_id,c.name as student_name,d.teachplan_id,d.course_id,"
					+ "d.credit as plan_credit,d.coursetime as plan_course_time,d.type as course_plantype "
					+ "from (select a.id,a.name,b.id as plan_id "
					+ "from (select id,name,major_id,edutype_id,grade_id "
					+ "from entity_student_info where id='"
					+ student.getId()
					+ "') a "
					+ "left join entity_teachplan_info b on a.major_id=b.major_id "
					+ "and a.edutype_id=b.edutype_id and a.grade_id=b.grade_id) c "
					+ "left join entity_teachplan_course d on c.plan_id=d.teachplan_id) f "
					+ "on f.course_id=e.course_id and f.student_id=e.student_id)";
		else
			SQL = "select id,student_id,student_name,teachplan_id,course_id,plan_credit,plan_course_time,"
					+ "course_plantype,credit,course_name,course_time,status,semester_id"
					+ " from (select e.id,e.student_id,f.student_name,f.teachplan_id,e.course_id,nvl(f.plan_credit,'0') as plan_credit,nvl(f.plan_course_time,'0') as plan_course_time,"
					+ "nvl(course_plantype,'0') as course_plantype,e.credit,e.course_name,e.course_time,e.status,e.semester_id "
					+ "from (select ee.id,ee.student_id,ee.status,eci.id as course_id,eci.name as course_name,"
					+ "eci.credit,eci.course_time,eca.semester_id from entity_elective ee,entity_teach_class etc,"
					+ "entity_course_active eca,entity_course_info eci where "
					+ " ee.teachclass_id=etc.id and etc.open_course_id=eca.id and eca.course_id=eci.id) e left join "
					+ "(select c.id as student_id,c.name as student_name,d.teachplan_id,d.course_id,"
					+ "d.credit as plan_credit,d.coursetime as plan_course_time,d.type as course_plantype "
					+ "from (select a.id,a.name,b.id as plan_id "
					+ "from (select id,name,major_id,edutype_id,grade_id "
					+ "from entity_student_info) a "
					+ "left join entity_teachplan_info b on a.major_id=b.major_id "
					+ "and a.edutype_id=b.edutype_id and a.grade_id=b.grade_id) c "
					+ "left join entity_teachplan_course d on c.plan_id=d.teachplan_id) f "
					+ "on f.course_id=e.course_id and f.student_id=e.student_id)";

		String sql = SQL
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		ArrayList electives = null;
		try {
			db = new dbpool();
			electives = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleElective elective = new OracleElective();
				OracleTeachClass newTeachClass = new OracleTeachClass();
				OracleOpenCourse openCourse = new OracleOpenCourse();
				OracleSemester semester = new OracleSemester();
				semester.setId(rs.getString("semester_id"));
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_time(rs.getString("course_time"));
				course.setRedundance1(rs.getString("plan_credit"));
				course.setRedundance2(rs.getString("plan_course_time"));
				course.setCourse_type(rs.getString("course_plantype"));
//				openCourse.setCourse(course);
//				openCourse.setSemester(semester);
				newTeachClass.setOpenCourse(openCourse);
				elective.setId(rs.getString("id"));
				elective.setElectiveStatus(rs.getString("status"));
				elective.setTeachClass(newTeachClass);
				electives.add(elective);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return electives;
	}

	/*
	 * ��ѯѧ��ѡ����Ϣ����ѧ�ڣ�ѧ��ID��
	 * 
	 * @see com.whaty.platform.entity.activity.BasicActivityList#getElectiveByUserIdAndSemester(com.whaty.platform.util.Page,
	 *      java.util.List, java.util.List,Student)
	 */
	public int getElectiveByUserIdAndSemesterNum(List searchproperty,
			List orderproperty, Student student) throws PlatformException {
		dbpool db = new dbpool();
		String SQL;
		if (student != null)
			SQL = "select id,student_id,student_name,teachplan_id,course_id,plan_credit,plan_course_time,"
					+ "course_plantype,credit,course_name,course_time,status,semester_id "
					+ " from (select e.id,e.student_id,f.student_name,f.teachplan_id,e.course_id,f.plan_credit,f.plan_course_time,"
					+ "course_plantype,e.credit,e.course_name,e.course_time,e.status,e.semester_id "
					+ "from (select ee.id,ee.student_id,ee.status,eci.id as course_id,eci.name as course_name,"
					+ "eci.credit,eci.course_time,eca.semester_id from entity_elective ee,entity_teach_class etc,"
					+ "entity_course_active eca,entity_course_info eci where ee.student_id='"
					+ student.getId()
					+ "'"
					+ " and ee.teachclass_id=etc.id and etc.open_course_id=eca.id and eca.course_id=eci.id) e left join "
					+ "(select c.id as student_id,c.name as student_name,d.teachplan_id,d.course_id,"
					+ "d.credit as plan_credit,d.coursetime as plan_course_time,d.type as course_plantype "
					+ "from (select a.id,a.name,b.id as plan_id "
					+ "from (select id,name,major_id,edutype_id,grade_id "
					+ "from entity_student_info where id='"
					+ student.getId()
					+ "') a "
					+ "left join entity_teachplan_info b on a.major_id=b.major_id "
					+ "and a.edutype_id=b.edutype_id and a.grade_id=b.grade_id) c "
					+ "left join entity_teachplan_course d on c.plan_id=d.teachplan_id) f "
					+ "on f.course_id=e.course_id and f.student_id=e.student_id)";
		else
			SQL = "select id,student_id,student_name,teachplan_id,course_id,plan_credit,plan_course_time,"
					+ "course_plantype,credit,course_name,course_time,status,semester_id"
					+ " from (select e.id,e.student_id,f.student_name,f.teachplan_id,e.course_id,f.plan_credit,f.plan_course_time,"
					+ "course_plantype,e.credit,e.course_name,e.course_time,e.status,e.semester_id "
					+ "from (select ee.id,ee.student_id,ee.status,eci.id as course_id,eci.name as course_name,"
					+ "eci.credit,eci.course_time,eca.semester_id from entity_elective ee,entity_teach_class etc,"
					+ "entity_course_active eca,entity_course_info eci where "
					+ " ee.teachclass_id=etc.id and etc.open_course_id=eca.id and eca.course_id=eci.id) e left join "
					+ "(select c.id as student_id,c.name as student_name,d.teachplan_id,d.course_id,"
					+ "d.credit as plan_credit,d.coursetime as plan_course_time,d.type as course_plantype "
					+ "from (select a.id,a.name,b.id as plan_id "
					+ "from (select id,name,major_id,edutype_id,grade_id "
					+ "from entity_student_info) a "
					+ "left join entity_teachplan_info b on a.major_id=b.major_id "
					+ "and a.edutype_id=b.edutype_id and a.grade_id=b.grade_id) c "
					+ "left join entity_teachplan_course d on c.plan_id=d.teachplan_id) f "
					+ "on f.course_id=e.course_id and f.student_id=e.student_id)";

		String sql = SQL
				+ Conditions.convertToCondition(searchproperty, orderproperty);
		return db.countselect(sql);
	}

	public List searchDegreeConditions(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		// TODO �Զ���ɷ������
		return null;
	}

	public int searchDegreeConditionsNum(List searchproperty)
			throws PlatformException {
		// TODO �Զ���ɷ������
		return 0;
	}

	public List searchAllDegreeConditions(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select major_id,major_name,grade_id,grade_name,edutype_id,edutype_name,id from (select a.major_id,a.major_name,a.grade_id,a.grade_name,a.edutype_id,a.edutype_name,nvl(d.id,'0') as id from (select distinct a.id as major_id,a.name as major_name,b.id as grade_id,b.name as grade_name,c.id as edutype_id,c.name as edutype_name from entity_major_info a,entity_grade_info b,entity_edu_type c,(select distinct major_id,grade_id,edutype_id from entity_student_info where status='0') s where a.id<>'0' and a.id=s.major_id and b.id=s.grade_id and c.id=s.edutype_id) a,entity_degree_record d where a.major_id=d.major_id(+) and a.grade_id=d.grade_id(+) and a.edutype_id=d.edutype_id(+)) "
				+ Conditions.convertToCondition(searchproperty, null);
		MyResultSet rs = null;
		ArrayList conditions = null;
		try {
			db = new dbpool();
			conditions = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleDegreeCondition condition = new OracleDegreeCondition();
				condition.setId(rs.getString("id"));
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				major.setName(rs.getString("major_name"));
				condition.setMajor(major);
				OracleGrade grade = new OracleGrade();
				grade.setId(rs.getString("grade_id"));
				grade.setName(rs.getString("grade_name"));
				condition.setGrade(grade);
				OracleEduType eduType = new OracleEduType();
				eduType.setId(rs.getString("edutype_id"));
				eduType.setName(rs.getString("edutype_name"));
				condition.setEduType(eduType);
				conditions.add(condition);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return conditions;
	}

	public int searchAllDegreeConditionsNum(List searchproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select major_id,major_name,grade_id,grade_name,edutype_id,edutype_name,id from (select a.major_id,a.major_name,a.grade_id,a.grade_name,a.edutype_id,a.edutype_name,nvl(d.id,'0') as id from (select distinct a.id as major_id,a.name as major_name,b.id as grade_id,b.name as grade_name,c.id as edutype_id,c.name as edutype_name from entity_major_info a,entity_grade_info b,entity_edu_type c,(select distinct major_id,grade_id,edutype_id from entity_student_info where status='0') s where a.id<>'0' and a.id=s.major_id and b.id=s.grade_id and c.id=s.edutype_id) a,entity_degree_record d where a.major_id=d.major_id(+) and a.grade_id=d.grade_id(+) and a.edutype_id=d.edutype_id(+)) "
				+ Conditions.convertToCondition(searchproperty, null);
		return db.countselect(sql);
	}

	public List getDegreeStat(Page page, List searchproperty, List orderproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List statList = new ArrayList();
		String sql = "select nvl(degree_num,0) as degree_num,total_num,b.grade_id,c.name as grade_name,b.edutype_id,d.name as edutype_name,b.major_id,e.name as major_name from (select count(id) as degree_num,grade_id,edutype_id,major_id from entity_student_info where isdegreed='1' "
				+ Conditions.convertToAndCondition(searchproperty,
						orderproperty)
				+ " group by grade_id,edutype_id,major_id) a,(select count(id) as total_num,grade_id,edutype_id,major_id from entity_student_info "
				+ Conditions.convertToCondition(searchproperty, orderproperty)
				+ " group by grade_id,edutype_id,major_id) b,entity_grade_info c,entity_edu_type d,entity_major_info e where b.grade_id=a.grade_id(+) and b.major_id=a.major_id(+) and b.edutype_id=a.edutype_id(+) and b.grade_id=c.id and b.edutype_id=d.id and b.major_id=e.id";
		rs = db.executeQuery(sql);
		int total_num = 0;
		int degree_num = 0;
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String per = "";
		try {
			while (rs != null && rs.next()) {
				String[] stat = new String[7];
				stat[0] = rs.getString("grade_name");
				stat[1] = rs.getString("edutype_name");
				stat[2] = rs.getString("major_name");
				stat[3] = rs.getString("total_num");
				total_num = rs.getInt("total_num");
				stat[4] = rs.getString("degree_num");
				degree_num = rs.getInt("degree_num");
				stat[5] = Integer.toString(total_num - degree_num);
				if (total_num != 0)
					if (degree_num != 0 && total_num != 0)
						per = df.format((100 * degree_num) / total_num);
					else
						per = "0";
				else
					per = "0";
				stat[6] = per;
				statList.add(stat);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return statList;
	}

	public List getTeacherInteractionStatistic(Page page, String semesterId,
			String orderBy, String order) throws PlatformException {
		dbpool db = new dbpool();
		// String sql = "select d.gh, d.name, a.forum_times, b.answer_times,
		// c.not_answer_times from "
		// + "(select d.id, d.name, count(c.id) as forum_times from
		// entity_teach_class a, entity_course_active b, interaction_forum_info
		// c, entity_teacher_info d where a.open_course_id = b.id and a.id =
		// c.course_id and c.submituser_id = d.id and b.semester_id = '"
		// + semesterId
		// + "' group by d.id, d.name) a, "
		// + "(select e.id, e.name, count(distinct(c.id)) as answer_times from
		// entity_teach_class a, entity_course_active b,
		// interaction_question_info c, interaction_answer_info d,
		// entity_teacher_info e where a.open_course_id = b.id and b.semester_id
		// = '"
		// + semesterId
		// + "' and a.id = c.course_id and c.id = d.question_id and d.reuser_id
		// = e.id group by e.id, e.name) b, "
		// + "(select e.id, e.name, count(distinct(c.id)) as not_answer_times
		// from entity_teach_class a, entity_course_active b,
		// interaction_question_info c, entity_teacher_course d,
		// entity_teacher_info e where a.id = d.teachclass_id and
		// a.open_course_id = b.id and b.semester_id = '"
		// + semesterId
		// + "' and a.id = c.course_id and d.teacher_id = e.id and c.id not in
		// (select question_id from interaction_answer_info) group by e.id,
		// e.name) c, "
		// + "entity_teacher_info d where d.id = a.id(+) and d.id = b.id(+) and
		// d.id = c.id(+)";
		// sql = "select gh, name, forum_times, answer_times, not_answer_times
		// from ("
		// + sql + ") order by ";
		String sql = "select d.gh, d.name, a.forum_times, b.answer_times, c.not_answer_times ,d.id from "
				+ "(select d.id, d.name, count(c.id) as forum_times from entity_teach_class a, entity_course_active b, interaction_forum_info c, entity_teacher_info d where a.open_course_id = b.id and a.id = c.course_id and c.submituser_id = d.id and b.semester_id = '"
				+ semesterId
				+ "' group by d.id, d.name) a, "
				+ "(select e.id, e.name, count(distinct(c.id)) as answer_times from entity_teach_class a, entity_course_active b, interaction_question_info c, interaction_answer_info d, entity_teacher_info e where a.open_course_id = b.id and b.semester_id = '"
				+ semesterId
				+ "' and a.id = c.course_id and c.id = d.question_id and d.reuser_id = e.id group by e.id, e.name) b, "
				+ "(select e.id, e.name, count(distinct(c.id)) as not_answer_times from entity_teach_class a, entity_course_active b, interaction_question_info c, entity_teacher_course d, entity_teacher_info e where  a.id = d.teachclass_id and a.open_course_id = b.id and b.semester_id = '"
				+ semesterId
				+ "' and a.id = c.course_id  and d.teacher_id = e.id and c.id not in (select question_id from interaction_answer_info)  group by e.id, e.name) c, "
				+ "entity_teacher_info d where d.id = a.id(+) and d.id = b.id(+) and d.id = c.id(+) ";
		sql = "select gh, name, forum_times, answer_times, not_answer_times ,id from ("
				+ sql + ") order by ";

		if ("forum_count".equals(orderBy))
			sql += "forum_times";
		else if ("answer_count".equals(orderBy))
			sql += "answer_times";
		else if ("unanswer_count".equals(orderBy))
			sql += "not_answer_times";
		else if ("name".equals(orderBy))
			sql += "name";
		else
			sql += "gh";
		if ("desc".equals(order))
			sql += " desc";
		else
			sql += " asc";
		MyResultSet rs = null;
		ArrayList teachers = new ArrayList();
		try {
			db = new dbpool();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				List statistic = new ArrayList();
				statistic.add(rs.getString("gh"));
				statistic.add(rs.getString("name"));
				if (null != rs.getString("forum_times"))
					statistic.add(rs.getString("forum_times"));
				else
					statistic.add("0");
				if (null != rs.getString("answer_times"))
					statistic.add(rs.getString("answer_times"));
				else
					statistic.add("0");
				if (null != rs.getString("not_answer_times"))
					statistic.add(rs.getString("not_answer_times"));
				else
					statistic.add("0");
				statistic.add(rs.getString("id"));
				teachers.add(statistic);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachers;
	}
	
	public List getTeacherInteractionStatistic(Page page, String semesterId,
			String orderBy, String order,String dep_id) throws PlatformException {
		dbpool db = new dbpool();
		// String sql = "select d.gh, d.name, a.forum_times, b.answer_times,
		// c.not_answer_times from "
		// + "(select d.id, d.name, count(c.id) as forum_times from
		// entity_teach_class a, entity_course_active b, interaction_forum_info
		// c, entity_teacher_info d where a.open_course_id = b.id and a.id =
		// c.course_id and c.submituser_id = d.id and b.semester_id = '"
		// + semesterId
		// + "' group by d.id, d.name) a, "
		// + "(select e.id, e.name, count(distinct(c.id)) as answer_times from
		// entity_teach_class a, entity_course_active b,
		// interaction_question_info c, interaction_answer_info d,
		// entity_teacher_info e where a.open_course_id = b.id and b.semester_id
		// = '"
		// + semesterId
		// + "' and a.id = c.course_id and c.id = d.question_id and d.reuser_id
		// = e.id group by e.id, e.name) b, "
		// + "(select e.id, e.name, count(distinct(c.id)) as not_answer_times
		// from entity_teach_class a, entity_course_active b,
		// interaction_question_info c, entity_teacher_course d,
		// entity_teacher_info e where a.id = d.teachclass_id and
		// a.open_course_id = b.id and b.semester_id = '"
		// + semesterId
		// + "' and a.id = c.course_id and d.teacher_id = e.id and c.id not in
		// (select question_id from interaction_answer_info) group by e.id,
		// e.name) c, "
		// + "entity_teacher_info d where d.id = a.id(+) and d.id = b.id(+) and
		// d.id = c.id(+)";
		// sql = "select gh, name, forum_times, answer_times, not_answer_times
		// from ("
		// + sql + ") order by ";
		String sql = "select d.gh, d.name, a.forum_times, b.answer_times, c.not_answer_times ,d.id,d.dep_id,di.name as dep_name from "
			+ "(select d.id, d.name, count(c.id) as forum_times from entity_teach_class a, entity_course_active b, interaction_forum_info c, entity_teacher_info d where a.open_course_id = b.id and a.id = c.course_id and c.submituser_id = d.id and b.semester_id = '"
			+ semesterId
			+ "' group by d.id, d.name) a, "
			+ "(select e.id, e.name, count(distinct(c.id)) as answer_times from entity_teach_class a, entity_course_active b, interaction_question_info c, interaction_answer_info d, entity_teacher_info e where a.open_course_id = b.id and b.semester_id = '"
			+ semesterId
			+ "' and a.id = c.course_id and c.id = d.question_id and d.reuser_id = e.id group by e.id, e.name) b, "
			+ "(select e.id, e.name, count(distinct(c.id)) as not_answer_times from entity_teach_class a, entity_course_active b, interaction_question_info c, entity_teacher_course d, entity_teacher_info e where  a.id = d.teachclass_id and a.open_course_id = b.id and b.semester_id = '"
			+ semesterId
			+ "' and a.id = c.course_id  and d.teacher_id = e.id and c.id not in (select question_id from interaction_answer_info)  group by e.id, e.name) c, "
			+ "entity_teacher_info d, entity_dep_info di where d.id = a.id(+) and d.id = b.id(+) and d.id = c.id(+) and d.dep_id = di.id(+) ";
		
		if(dep_id!=null&&dep_id.trim().length()>0){
           sql +="	and d.dep_id = '"+dep_id+"'";			
		}
	   sql = "select gh, name, forum_times, answer_times, not_answer_times ,id ,dep_id ,dep_name from ("
			+ sql + ") order by ";
	
		if ("forum_count".equals(orderBy))
			sql += "forum_times";
		else if ("answer_count".equals(orderBy))
			sql += "answer_times";
		else if ("unanswer_count".equals(orderBy))
			sql += "not_answer_times";
		else if ("name".equals(orderBy))
			sql += "name";
		else
			sql += "gh";
		if ("desc".equals(order))
			sql += " desc";
		else
			sql += " asc";
		MyResultSet rs = null;
		ArrayList teachers = new ArrayList();
		try {
			db = new dbpool();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				List statistic = new ArrayList();
				statistic.add(rs.getString("gh"));
				statistic.add(rs.getString("name"));
				if (null != rs.getString("forum_times"))
					statistic.add(rs.getString("forum_times"));
				else
					statistic.add("0");
				if (null != rs.getString("answer_times"))
					statistic.add(rs.getString("answer_times"));
				else
					statistic.add("0");
				if (null != rs.getString("not_answer_times"))
					statistic.add(rs.getString("not_answer_times"));
				else
					statistic.add("0");
				statistic.add(rs.getString("id"));
				statistic.add(rs.getString("dep_id"));
				statistic.add(rs.getString("dep_name"));
				teachers.add(statistic);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachers;
	}

	public List getTeacherInteractionStatistic(Page page, List searchproperty, List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select course_id,course_name,teach_class_id,total_questions,total_answers,total_not_answers from ( select distinct eci.id as course_id,eci.name as course_name,etc.id as teach_class_id,nvl(a.total_questions,0) as total_questions,nvl(b.total_answers,0) as total_answers,nvl(c.total_not_answers,0) as total_not_answers,eca.semester_id from entity_course_info eci, entity_course_active eca, entity_teach_class etc, (select course_id ,count(iqi.id) as total_questions from interaction_question_info iqi group by course_id) a , (select course_id , sum(simple) as total_answers from (select course_id,count(iai.id) as simple from interaction_question_info iqi,interaction_answer_info iai where iai.question_id = iqi.id group by iqi.id,iqi.course_id) group by course_id) b , (select course_id , count(iqi.id) as total_not_answers from interaction_question_info iqi where iqi.id not in (select iai.question_id from interaction_answer_info iai) group by course_id) c where a.course_id = b.course_id(+) and a.course_id = c.course_id(+) and eci.id = eca.course_id and eca.id = etc.open_course_id and a.course_id = etc.id(+) ) ";
		sql += Conditions.convertToCondition(searchproperty, orderproperty) ;
		MyResultSet rs = null;
		ArrayList courses = new ArrayList();
		try {
			db = new dbpool();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				List statistic = new ArrayList();
				String course_id = rs.getString("course_id") ;
				String course_name = rs.getString("course_name") ;
				String questions = rs.getString("total_questions") ;
				String answers = rs.getString("total_answers") ;
				String notanswer = rs.getString("total_not_answers") ;
				String teachclass = rs.getString("teach_class_id") ;
				statistic.add(course_id) ;
				statistic.add(course_name) ;
				statistic.add(questions) ;
				statistic.add(answers) ;
				statistic.add(notanswer) ;
				statistic.add(teachclass) ;
				courses.add(statistic) ;
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courses;
	}


	
	public List getTeacherInteractionStatistic2(Page page, List searchproperty, List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql2 = Conditions.convertToCondition(searchproperty, orderproperty) ;
		String sql = "select course_id,course_name,teacher_id,teacher_name,teacher_title,total_questions,total_answers,total_not_answers from (select eci.id as course_id, eci.name as course_name, eti.name as teacher_name, eti.id as teacher_id, nvl(eti.title,'��') as teacher_title, etc2.teachclass_id as teachclass_id, nvl(a.total_questions, 0) as total_questions, nvl(b.total_answers, 0) as total_answers, nvl(c.total_not_answers, 0) as total_not_answers from entity_teacher_info eti, entity_course_active eca, entity_course_info eci, entity_teach_class etc, entity_teacher_course etc2, (select teachclass_id,teacher_id,count(id) as total_questions from (select course_id as teachclass_id, submituser_id as teacher_id, id from interaction_question_info iqi) "
			+ sql2 + " group by teachclass_id, teacher_id) a, (select teachclass_id, teacher_id, count(id) as total_answers from (select distinct iqi.course_id as teachclass_id, iai.reuser_id as teacher_id, iai.id from interaction_question_info iqi, interaction_answer_info iai where iqi.id = iai.question_id) "
			+ sql2 + " group by teachclass_id, teacher_id) b, (select teachclass_id,teacher_id,count(id) as total_not_answers from (select course_id as teachclass_id, submituser_id as teacher_id, iqi.id from interaction_question_info iqi where iqi.id not in (select iai.question_id from interaction_answer_info iai)) " 
			+ sql2 + " group by teachclass_id, teacher_id) c where eti.id = a.teacher_id (+) and eti.id = b.teacher_id (+) and eti.id = c.teacher_id (+) and etc2.teacher_id = eti.id and etc2.teachclass_id = etc.id and eci.id = eca.course_id and etc.open_course_id = eca.id ) "
			+ sql2 ;
		
		MyResultSet rs = null;
		ArrayList teachers = new ArrayList();
		try {
			db = new dbpool();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				List list = new ArrayList() ;
				String course_id = rs.getString("course_id") ;
				String course_name = rs.getString("course_name") ;
				String questions = rs.getString("total_questions") ;
				String answers = rs.getString("total_answers") ;
				String notanswer = rs.getString("total_not_answers") ;
				String teacher_id = rs.getString("teacher_id") ;
				String teacher_name = rs.getString("teacher_name") ;
				String teacher_title = rs.getString("teacher_title") ;
				list.add(course_id) ;
				list.add(course_name) ;
				list.add(teacher_id) ;
				list.add(teacher_name) ;
				list.add(teacher_title) ;
				list.add(questions) ;
				list.add(answers) ;
				list.add(notanswer) ;
				teachers.add(list) ;
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return teachers;
	}

	public int getTeacherInteractionStatisticNum(List searchproperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select course_id,course_name,teach_class_id,total_questions,total_answers,total_not_answers from ( select distinct eci.id as course_id,eci.name as course_name,etc.id as teach_class_id,a.total_questions,b.total_answers,c.total_not_answers,eca.semester_id from entity_course_info eci, entity_course_active eca, entity_teach_class etc, (select course_id ,count(iqi.id) as total_questions from interaction_question_info iqi group by course_id) a , (select course_id , sum(simple) as total_answers from (select course_id,count(iai.id) as simple from interaction_question_info iqi,interaction_answer_info iai where iai.question_id = iqi.id group by iqi.id,iqi.course_id) group by course_id) b , (select course_id , count(iqi.id) as total_not_answers from interaction_question_info iqi where iqi.id not in (select iai.question_id from interaction_answer_info iai) group by course_id) c where a.course_id = b.course_id(+) and a.course_id = c.course_id(+) and eci.id = eca.course_id and eca.id = etc.open_course_id and a.course_id = etc.id(+) ) ";
		sql += Conditions.convertToCondition(searchproperty, null) ;
		return db.countselect(sql);
	}

	public int getTeacherInteractionStatisticNum(String semesterId)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select d.id, d.name, a.forum_times, b.answer_times, c.not_answer_times from "
				+ "(select d.id, d.name, count(c.id) as forum_times from entity_teach_class a, entity_course_active b, interaction_forum_info c, entity_teacher_info d where a.open_course_id = b.id and a.id = c.course_id and c.submituser_id = d.id and b.semester_id = '"
				+ semesterId
				+ "' group by d.id, d.name) a, "
				+ "(select e.id, e.name, count(distinct(c.id)) as answer_times from entity_teach_class a, entity_course_active b, interaction_question_info c, interaction_answer_info d, entity_teacher_info e where a.open_course_id = b.id and b.semester_id = '"
				+ semesterId
				+ "' and a.id = c.course_id and c.id = d.question_id and d.reuser_id = e.id group by e.id, e.name)b, "
				+ "(select e.id, e.name, count(distinct(c.id)) as not_answer_times from entity_teach_class a, entity_course_active b, interaction_question_info c, entity_teacher_course d, entity_teacher_info e where  a.id = d.teachclass_id and a.open_course_id = b.id and b.semester_id = '"
				+ semesterId
				+ "' and a.id = c.course_id  and d.teacher_id = e.id and c.id not in (select question_id from interaction_answer_info)  group by e.id, e.name) c, "
				+ "entity_teacher_info d where d.id = a.id(+) and d.id = b.id(+) and d.id = c.id(+)";
		sql = "select id, name, forum_times, answer_times, not_answer_times from ("
				+ sql + ") ";
		return db.countselect(sql);
	}
	
	public int getTeacherInteractionStatisticNum(String semesterId,String dep_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select d.id, d.name, a.forum_times, b.answer_times, c.not_answer_times from "
				+ "(select d.id, d.name, count(c.id) as forum_times from entity_teach_class a, entity_course_active b, interaction_forum_info c, entity_teacher_info d where a.open_course_id = b.id and a.id = c.course_id and c.submituser_id = d.id and d.dep_id = '"+dep_id+"' and b.semester_id = '"
				+ semesterId
				+ "' group by d.id, d.name) a, "
				+ "(select e.id, e.name, count(distinct(c.id)) as answer_times from entity_teach_class a, entity_course_active b, interaction_question_info c, interaction_answer_info d, entity_teacher_info e where a.open_course_id = b.id and e.dep_id = '"+dep_id+"' and b.semester_id = '"
				+ semesterId
				+ "' and a.id = c.course_id and c.id = d.question_id and d.reuser_id = e.id group by e.id, e.name) b, "
				+ "(select e.id, e.name, count(distinct(c.id)) as not_answer_times from entity_teach_class a, entity_course_active b, interaction_question_info c, entity_teacher_course d, entity_teacher_info e where  a.id = d.teachclass_id and a.open_course_id = b.id and e.dep_id = '"+dep_id+"' and b.semester_id = '"
				+ semesterId
				+ "' and a.id = c.course_id  and d.teacher_id = e.id and c.id not in (select question_id from interaction_answer_info)  group by e.id, e.name) c, "
				+ "entity_teacher_info d,entity_dep_info di  where d.id = a.id(+) and d.id = b.id(+) and d.id = c.id(+)	 and d.dep_id = di.id(+) ";
		
		if(dep_id!=null&&dep_id.trim().length()>0){
	           sql +=" and d.dep_id = '"+dep_id+"'";			
		}
		
		sql = "select id, name, forum_times, answer_times, not_answer_times from ("
				+ sql + ") ";
		return db.countselect(sql);
	}

	public List getActiveTeachplanCourseList(Page page, String semesterId,
			String gradeId, String eduTypeId, String majorId) {
		List courseList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id, teachplan_id, course_id,credit,type,isdegree,coursetime from ("
				+ "select c.id as id, teachplan_id, c.course_id as course_id,credit,type,isdegree,coursetime "
				+ " from (select course_id,semester_id from entity_course_active where semester_id='"
				+ semesterId
				+ "') a,(select id from entity_teachplan_info where major_id='"
				+ majorId
				+ "' and grade_id='"
				+ gradeId
				+ "' and edutype_id='"
				+ eduTypeId
				+ "') b,(select id,teachplan_id,course_id,credit,type,isdegree,coursetime from entity_teachplan_course) c "
				+ "where c.course_id=a.course_id and c.teachplan_id=b.id)";
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}

		try {
			while (rs != null && rs.next()) {
				OracleTeachPlanCourse teachPlanCourse = new OracleTeachPlanCourse();
				OracleCourse course = new OracleCourse(rs
						.getString("course_id"));
				OracleTeachPlan teachPlan = new OracleTeachPlan(rs
						.getString("teachplan_id"));
				teachPlanCourse.setId(rs.getString("id"));
				teachPlanCourse.setCourse(course);
				teachPlanCourse.setTeachPlan(teachPlan);
				teachPlanCourse.setCredit(Float.parseFloat(rs
						.getString("credit")));
				teachPlanCourse.setCoursetime(Float.parseFloat(rs
						.getString("coursetime")));
				teachPlanCourse.setIsdegree(rs.getString("isdegree"));

				courseList.add(teachPlanCourse);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return courseList;
	}

	public int getActiveTeachplanCoursesNum(String semesterId, String gradeId,
			String eduTypeId, String majorId) {
		List courseList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id, teachplan_id, course_id,credit,type,isdegree,coursetime from ("
				+ "select c.id as id, teachplan_id, c.course_id as course_id,credit,type,isdegree,coursetime "
				+ " from (select course_id,semester_id from entity_course_active where semester_id='"
				+ semesterId
				+ "') a,(select id from entity_teachplan_info where major_id='"
				+ majorId
				+ "' and grade_id='"
				+ gradeId
				+ "' and edutype_id='"
				+ eduTypeId
				+ "') b,(select id,teachplan_id,course_id,credit,type,isdegree,coursetime from entity_teachplan_course) c "
				+ "where c.course_id=a.course_id and c.teachplan_id=b.id)";

		return db.countselect(sql);
	}

	public List getExecutePlans(Page page, List searchProperty,
			List orderProperty) {
		List planList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id, title, semester_id, teachplan_id from entity_executeplan_info ";
		sql += Conditions.convertToCondition(searchProperty, orderProperty);
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}

		try {
			while (rs != null && rs.next()) {
				OracleExecutePlan plan = new OracleExecutePlan();
				plan.setId(rs.getString("id"));
				plan.setTitle(rs.getString("title"));
				plan
						.setSemester(new OracleSemester(rs
								.getString("semester_id")));
				plan.setPlan(new OracleTeachPlan(rs.getString("teachplan_id")));

				planList.add(plan);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return planList;
	}

	public int getExecutePlansNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "select id, title, semester_id, teachplan_id from entity_executeplan_info ";
		sql += Conditions.convertToCondition(searchProperty, null);
		return db.countselect(sql);
	}

	public List getExecutePlanCourseGroups(Page page, List searchProperty,
			List orderProperty) {
		List groupList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id, title, executeplan_id, type, max, min from entity_executeplan_group ";
		sql += Conditions.convertToCondition(searchProperty, orderProperty);
		// System.out.print(sql);
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}

		try {
			while (rs != null && rs.next()) {
				OracleExecutePlanCourseGroup group = new OracleExecutePlanCourseGroup();
				group.setId(rs.getString("id"));
				group.setTitle(rs.getString("title"));
				group.setExecutePlan(new OracleExecutePlan(rs
						.getString("executeplan_id")));
				group.setType(rs.getString("type"));
				group.setMax(rs.getInt("max"));
				group.setMin(rs.getInt("min"));

				groupList.add(group);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return groupList;
	}

	public List getExecutePlanCourses(Page page, List searchProperty,
			List orderProperty) {
		List courseList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id, group_id, teachplan_course_id from entity_executeplan_course ";
		sql += Conditions.convertToCondition(searchProperty, orderProperty);
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}

		try {
			while (rs != null && rs.next()) {
				OracleExecutePlanCourse course = new OracleExecutePlanCourse();
				course.setId(rs.getString("id"));
				course.setGroup(new OracleExecutePlanCourseGroup(rs
						.getString("group_id")));
				course.setCourse(new OracleTeachPlanCourse(rs
						.getString("teachplan_course_id")));

				courseList.add(course);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return courseList;
	}

	public int getExecutePlanCoursesNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "select id, group_id, teachplan_course_id from entity_executeplan_course ";
		sql += Conditions.convertToCondition(searchProperty, null);

		return db.countselect(sql);
	}

	public List getExecutePlanWithConditionEleNum(Page page, String semesterId,
			String siteId, String majorId, String eduTypeId, String gradeId,
			String reg_no, List orderProperty) {
		dbpool db = new dbpool();
		String con = " and s.major_id = '" + majorId + "' and s.edutype_id = '"
				+ eduTypeId + "' and s.grade_id = '" + gradeId + "' ";
		if (siteId != null && !siteId.equals("")) {
			con += " and s.site_id = '" + siteId + "' ";
		}
		if (reg_no != null && !reg_no.equals("")) {
			con += " and s.reg_no = '" + reg_no + "' ";
		}
		String sql = "select course_id,open_course_id,course_name,credit,course_time,course_type,elnum,teachclass_id from "
				+ "(select a.course_id,a.open_course_id,a.course_name,a.credit,a.course_time,a.course_type,nvl(b.elnum,'0') as elnum,"
				+ "a.teachclass_id from (select a.id as open_course_id,tc.COURSE_ID,tc.credit as credit,tc.coursetime as course_time,"
				+ "tc.type as course_type,etc.id as teachclass_id,c.name as course_name from entity_teachplan_info t,entity_teachplan_course tc,"
				+ "entity_executeplan_info ei,entity_executeplan_group eg,entity_executeplan_course ec,entity_course_active a,entity_teach_class etc,"
				+ "entity_course_info c where a.course_id = c.id and a.semester_id = ei.semester_id and a.id = etc.open_course_id and "
				+ "ec.group_id = eg.id and eg.executeplan_id = ei.id and ei.semester_id = '"
				+ semesterId
				+ "' and ei.teachplan_id = t.id "
				+ "and t.id = tc.teachplan_id and ec.teachplan_course_id = tc.id and t.grade_id='"
				+ gradeId
				+ "' and t.major_id='"
				+ majorId
				+ "' "
				+ "and t.edutype_id='"
				+ eduTypeId
				+ "' and tc.course_id = c.id)a,(select count(student_id) as elnum,teachclass_id from entity_elective e,entity_student_info s "
				+ "where s.id = e.student_id "
				+ con
				+ ""
				+ "group by teachclass_id)b where a.teachclass_id = b.teachclass_id(+))"
				+ Conditions.convertToCondition(null, orderProperty);
		// System.out.print(sql);
		MyResultSet rs = null;
		ArrayList excutePlanCourse = null;
		ArrayList excutePlanOpenCourseList = new ArrayList();
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				excutePlanCourse = new ArrayList();
				excutePlanCourse.add(rs.getString("course_id"));
				excutePlanCourse.add(rs.getString("course_name"));
				excutePlanCourse.add(rs.getString("credit"));
				excutePlanCourse.add(rs.getString("course_time"));
				excutePlanCourse.add(rs.getString("course_type"));
				excutePlanCourse.add(rs.getString("elnum"));
				excutePlanCourse.add(rs.getString("open_course_id"));
				excutePlanCourse.add(rs.getString("teachclass_id"));
				excutePlanOpenCourseList.add(excutePlanCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return excutePlanOpenCourseList;
	}

	public List getExecutePlanCoursesByExecutePlanId(Page page,
			String executePlanId) {
		List courseList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id, group_id, teachplan_course_id,examsequence_id,exam_type from entity_executeplan_course "
				+ "where group_id in (select id from entity_executeplan_group where executeplan_id='"
				+ executePlanId + "') order by examsequence_id";
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}

		try {
			while (rs != null && rs.next()) {
				OracleExecutePlanCourse course = new OracleExecutePlanCourse();
				course.setId(rs.getString("id"));
				course.setGroup(new OracleExecutePlanCourseGroup(rs
						.getString("group_id")));
				course.setCourse(new OracleTeachPlanCourse(rs
						.getString("teachplan_course_id")));
				course.setSequence(new OracleBasicSequence(rs
						.getString("examsequence_id")));
				course.setExamType(rs.getString("exam_type"));

				courseList.add(course);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return courseList;
	}

	public int getExecutePlanCoursesNumByExecutePlanId(String executePlanId) {
		dbpool db = new dbpool();
		String sql = "select id, group_id, teachplan_course_id from entity_executeplan_course "
				+ "where group_id in (select id from entity_executeplan_group where executeplan_id='"
				+ executePlanId + "')";

		return db.countselect(sql);
	}

	public int getToBeSelectedTeachPlanCoursesNumOfExecutePlan(
			String executePlanId) {
		dbpool db = new dbpool();
		String sql = "select id, teachplan_id, course_id, credit, type, isdegree, coursetime from entity_teachplan_course "
				+ "where id not in (select teachplan_course_id from entity_executeplan_course where group_id in "
				+ "(select id from entity_executeplan_group where executeplan_id='"
				+ executePlanId
				+ "')) and course_id in (select course_id from entity_course_active where semester_id = ("
				+ "select semester_id from entity_executeplan_info where id='"
				+ executePlanId
				+ "')) and teachplan_id =("
				+ "select teachplan_id from entity_executeplan_info where id='"
				+ executePlanId + "') order by type,course_id";

		return db.countselect(sql);
	}

	public List getToBeSelectedTeachPlanCoursesOfExecutePlan(Page page,
			String executePlanId) {
		List courseList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id, teachplan_id, course_id, credit, type, isdegree, coursetime,SEMESTER from entity_teachplan_course "
				+ "where id not in (select teachplan_course_id from entity_executeplan_course where group_id in "
				+ "(select id from entity_executeplan_group where executeplan_id='"
				+ executePlanId
				+ "')) and course_id in (select course_id from entity_course_active where semester_id = ("
				+ "select semester_id from entity_executeplan_info where id='"
				+ executePlanId
				+ "')) and teachplan_id =("
				+ "select teachplan_id from entity_executeplan_info where id='"
				+ executePlanId + "') order by SEMESTER desc,course_id asc,type desc";
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}

		try {
			while (rs != null && rs.next()) {
				OracleTeachPlanCourse course = new OracleTeachPlanCourse();
				course.setId(rs.getString("id"));
				course.setTeachPlan(new OracleTeachPlan(rs
						.getString("teachplan_id")));
				course.setCourse(new OracleCourse(rs.getString("course_id")));
				course.setCredit(Float.parseFloat(rs.getString("credit")));
				course.setType(rs.getString("type"));
				course.setIsdegree(rs.getString("isdegree"));
				course.setCoursetime(Float.parseFloat(rs
						.getString("coursetime")));
                course.setSemester(rs.getString("SEMESTER"));
				courseList.add(course);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return courseList;
	}

	public int deleteExecutePlanCourses(String[] ids) {
		String idSet = "";
		for (int i = 0; i < ids.length; i++) {
			idSet = idSet + ids[i] + ",";
		}
		if (idSet.length() > 0)
			idSet = idSet.substring(0, idSet.length() - 1);
		dbpool db = new dbpool();
		String sql = "delete from entity_executeplan_course where id in ("
				+ idSet + ")";
		UserDeleteLog.setDebug("OracleBasicActivityList.deleteExecutePlanCourses(String[] ids) SQL=" + sql + " DATE=" + new Date());
		return db.executeUpdate(sql);
	}

	public List getUserFeeReturnApplyList(Page page, List searchProperty,
			List orderProperty) {
		List applyList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = USERFEERETURNAPPLY
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}

		try {
			while (rs != null && rs.next()) {
				OracleUserFeeReturnApply apply = new OracleUserFeeReturnApply();
				apply.setId(rs.getString("id"));
				apply.setUserId(rs.getString("user_id"));
				apply.setAmount(rs.getDouble("amount"));
				apply.setApplyTime(rs.getString("apply_time"));
				apply.setIsChecked(rs.getString("ischecked"));
				apply.setCheckTime(rs.getString("check_time"));
				apply.setIsReturned(rs.getString("isreturned"));
				apply.setReturnTime(rs.getString("return_time"));
				apply.setNote(rs.getString("note"));

				applyList.add(apply);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return applyList;
	}

	public int getUserFeeReturnApplyNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = USERFEERETURNAPPLY
				+ Conditions.convertToCondition(searchProperty, null);
		return db.countselect(sql);
	}

	public List searchOpenCourse(Page page, List searchproperty,
			List orderproperty, String site_id) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public List searchElectiveStat(Page page, List searchproperty,
			List orderproperty) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "select site_id,site_name,num from (select count(a.id) as num,c.id as site_id,c.name as site_name "
				+ "from entity_elective a,entity_student_info b,entity_site_info c "
				+ "where a.student_id=b.id and b.site_id=c.id "
				+ Conditions.convertToAndCondition(searchproperty, null)
				+ " group by c.id,c.name) order by site_id";
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		try {
			while (rs != null && rs.next()) {
				List item = new ArrayList();
				item.add(rs.getString("site_id"));
				item.add(rs.getString("site_name"));
				item.add(rs.getString("num"));
				list.add(item);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return list;
	}

	public int searchElectiveStatNum(List searchproperty)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select site_id from (select count(a.id) as num,c.id as site_id,c.name as site_name "
				+ "from entity_elective a,entity_student_info b,entity_site_info c "
				+ "where a.student_id=b.id and b.site_id=c.id "
				+ Conditions.convertToAndCondition(searchproperty, null)
				+ " group by c.id,c.name)";
		return db.countselect(sql);
	}

	public int getChangeMajorApplysNum(List searchProperties)
			throws PlatformException {
		String sql = "";
		dbpool db = new dbpool();
		sql = "select a.id , a.student_id,a.reject_note,j.name as student_name,j.reg_no, a.status, a.APPLY_DATE, a.old_major_id, a.new_major_id,a.old_grade_id, "
				+ " a.new_grade_id, a.old_edu_type, a.new_edu_type, a.old_site_id, a.new_site_id, b.name as old_site_name, "
				+ " c.name as new_site_name, d.name as old_grade_name, e.name as new_grade_name, f.name as old_edu_type_name,"
				+ " g.name as new_edu_type_name, h.name as old_major_name, i.name as new_major_name "
				+ " from entity_change_major_apply a, entity_site_info b, entity_site_info c, entity_grade_info d, entity_grade_info e,"
				+ " entity_edu_type f, entity_edu_type g, entity_major_info h, entity_major_info i, entity_student_info j "
				+ " where b.id = a.old_site_id and c.id = a.new_site_id and d.id = a.old_grade_id and e.id=a.new_grade_id "
				+ " and f.id=a.old_edu_type and g.id=a.new_edu_type and h.id=a.old_major_id and i.id=a.new_major_id and j.id=a.student_id ";
		sql = "select id , student_id,reject_note, student_name, reg_no, status, APPLY_DATE, old_major_id, new_major_id,old_grade_id,"
				+ " new_grade_id, old_edu_type, new_edu_type, old_site_id, new_site_id,  "
				+ " old_site_name, new_site_name, old_grade_name, new_grade_name, old_major_name, new_major_name,"
				+ " old_edu_type_name, new_edu_type_name from (" + sql + ")";
		sql += Conditions.convertToCondition(searchProperties, null);
		return db.countselect(sql);
	}

	public List getChangeMajorApplys(Page page, List searchProperties,
			List orderProperties) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "";
		sql = "select a.id , a.student_id,a.reject_note,j.name as student_name,j.reg_no, a.status, a.APPLY_DATE, a.old_major_id, a.new_major_id,a.old_grade_id, "
				+ " a.new_grade_id, a.old_edu_type, a.new_edu_type, a.old_site_id, a.new_site_id, b.name as old_site_name, "
				+ " c.name as new_site_name, d.name as old_grade_name, e.name as new_grade_name, f.name as old_edu_type_name,"
				+ " g.name as new_edu_type_name, h.name as old_major_name, i.name as new_major_name "
				+ " from entity_change_major_apply a, entity_site_info b, entity_site_info c, entity_grade_info d, entity_grade_info e,"
				+ " entity_edu_type f, entity_edu_type g, entity_major_info h, entity_major_info i, entity_student_info j "
				+ " where b.id = a.old_site_id and c.id = a.new_site_id and d.id = a.old_grade_id and e.id=a.new_grade_id "
				+ " and f.id=a.old_edu_type and g.id=a.new_edu_type and h.id=a.old_major_id and i.id=a.new_major_id and j.id=a.student_id ";
		sql = "select id , student_id,reject_note, student_name, reg_no, status, APPLY_DATE, old_major_id, new_major_id,old_grade_id,"
				+ " new_grade_id, old_edu_type, new_edu_type, old_site_id, new_site_id,  "
				+ " old_site_name, new_site_name, old_grade_name, new_grade_name, old_major_name, new_major_name,"
				+ " old_edu_type_name, new_edu_type_name from (" + sql + ")";
		sql += Conditions.convertToCondition(searchProperties, orderProperties);
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		try {
			while (rs != null && rs.next()) {
				OracleChangeMajorApply apply = new OracleChangeMajorApply();
				apply.setId(rs.getString("id"));
				apply.setStudentId(rs.getString("student_id"));
				apply.setStudent_name(rs.getString("student_name"));
				apply.setStudent_reg_no(rs.getString("reg_no"));
				apply.setStatus(rs.getString("status"));
				apply.setApplyDate(rs.getString("apply_date"));
				apply.setOldMajorId(rs.getString("old_major_id"));
				apply.setNewMajorId(rs.getString("new_major_id"));
				apply.setOldGradeId(rs.getString("old_grade_id"));
				apply.setNewGradeId(rs.getString("new_grade_id"));
				apply.setOldEduTypeId(rs.getString("old_edu_type"));
				apply.setNewEduTypeId(rs.getString("new_edu_type"));
				apply.setOldSiteId(rs.getString("old_site_id"));
				apply.setNewSiteId(rs.getString("new_site_id"));
				apply.setOldMajorName(rs.getString("old_major_name"));
				apply.setNewMajorName(rs.getString("new_major_name"));
				apply.setOldSiteName(rs.getString("old_site_name"));
				apply.setNewSiteName(rs.getString("new_site_name"));
				apply.setOldGradeName(rs.getString("old_grade_name"));
				apply.setNewGradeName(rs.getString("new_grade_name"));
				apply.setOldEduTypeName(rs.getString("old_edu_type_name"));
				apply.setNewEduTypeName(rs.getString("new_edu_type_name"));
				apply.setRejectNote(rs.getString("reject_note"));
				list.add(apply);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return list;
	}

	public List getTeachClass(String teacher_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "";
		sql = "select d.id, d.name, d.credit, d.course_time "
				+ "from entity_teacher_course a, entity_teach_class b, entity_course_active c,entity_course_info d "
				+ "where a.teachclass_id = b.id "
				+ "and b.open_course_id = c.id " + "and c.course_id = d.id "
				+ "and a.teacher_id = '" + teacher_id + "'";
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("name"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_time(rs.getString("course_time"));
				list.add(course);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return list;
	}

	public List getTeachClass(String teacher_id,String semester_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "";
		sql = "select d.id, d.name, d.credit, d.course_time "
				+ "from entity_teacher_course a, entity_teach_class b, entity_course_active c,entity_course_info d "
				+ "where a.teachclass_id = b.id "
				+ "and b.open_course_id = c.id " + "and c.course_id = d.id "
				+ "and a.teacher_id = '" + teacher_id + "' and c.semester_id = '" + semester_id + "'";
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("name"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_time(rs.getString("course_time"));
				list.add(course);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return list;
	}

	
	public String getTeachclass_cware() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "";
		sql = "select teachclass_id from PR_TCH_OPENCOURSE_COURSEWARE ";
		rs = db.executeQuery(sql);
		String ids = "";
		try {
			while (rs != null && rs.next()) {
				String id = rs.getString("teachclass_id");
				ids += "'" + id + "',";
			}
			ids = ids.substring(0, ids.trim().length() - 1);
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return ids;
	}

	public String getTeacherForTeacherClass() {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "";
		// sql = "select c.teachclass_id as
		// teachclass_id,t.id,t.name,t.gh,t.position,t.phone,t.title,t.workplace,t.email,t.teach_level,t.gender,t.RESEARCH_DIRECTION,t.note
		// "
		// + "from entity_teacher_course c,entity_teacher_info t where t.id =
		// c.teacher_id ";
		sql = "select teachclass_id from entity_teacher_course";
		rs = db.executeQuery(sql);
		String ids = "";
		try {
			while (rs != null && rs.next()) {
				String id = rs.getString("teachclass_id");
				ids += "'" + id + "',";
			}
			ids = ids.substring(0, ids.trim().length() - 1);
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return ids;
	}

	public HashMap getTeachClassTeachers(String semester_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		HashMap map = new HashMap() ; 
		String sql = "select teacher_id,teacher_name,teachclass_id from ( select distinct eti.id as teacher_id, eti.name as teacher_name, etc.teachclass_id,eca.semester_id from entity_teacher_course etc, entity_teacher_info eti ,entity_course_active eca,entity_teach_class etc2 where etc.teacher_id = eti.id and eca.id = etc2.open_course_id and etc2.id = etc.teachclass_id order by to_number(etc.teachclass_id) )";
		if(semester_id != null && !semester_id.equalsIgnoreCase("") && !semester_id.equalsIgnoreCase("null")) {
			sql += " where semester_id ='" + semester_id + "'" ;
		}
		rs = db.executeQuery(sql);
		String ids = "";
		try {
			while (rs != null && rs.next()) {
				String id = rs.getString("teacher_id") ;
				String name = rs.getString("teacher_name") ;
				String teachclass = rs.getString("teachclass_id") ;
				if(map.get(teachclass)==null) {
					List list = new ArrayList() ;
					list.add(id) ;
					list.add(name) ;
					map.put(teachclass,list) ;
				}
				else {
					List list = (List)map.get(teachclass) ;
					map.remove(teachclass) ;
					list.add(id) ;
					list.add(name) ;
					map.put(teachclass,list) ;
				}
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null ;
		}

		return map;
	}

	public HashMap getOpenCourseTeachers(String semester_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		HashMap map = new HashMap() ;
		String sql = "select teacher_id, teacher_name,course_id from (select distinct eti.id as teacher_id, eti.name as teacher_name, eci.id as course_id, eca.semester_id from entity_teacher_course etc, entity_teacher_info eti, entity_course_active eca, entity_teach_class etc2, entity_course_info eci where etc.teacher_id = eti.id and eca.id = etc2.open_course_id and etc2.id = etc.teachclass_id and eca.course_id = eci.id order by to_number(eci.id)) ";
		if(semester_id != null && !semester_id.equalsIgnoreCase("") && !semester_id.equalsIgnoreCase("null")) {
			sql += " where semester_id ='" + semester_id + "'" ;
		}
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				String id = rs.getString("teacher_id") ;
				String name = rs.getString("teacher_name") ;
				String course_id = rs.getString("course_id") ;
				if(map.get(course_id)==null) {
					List list = new ArrayList() ;
					list.add(id) ;
					list.add(name) ;
					map.put(course_id,list) ;
				}
				else {
					List list = (List)map.get(course_id) ;
					map.remove(course_id) ;
					list.add(id) ;
					list.add(name) ;
					map.put(course_id,list) ;
				}
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
			db = null ;
		}

		return map;
	}

}
