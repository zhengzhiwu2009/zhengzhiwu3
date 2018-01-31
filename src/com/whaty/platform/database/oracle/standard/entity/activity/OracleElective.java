package com.whaty.platform.database.oracle.standard.entity.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.activity.Elective;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleElective extends Elective {

	public OracleElective() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleElective(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select a.id,b.id as teachclass_id,b.name as teachclass_name,"
					+ "c.id as student_id,c.name as studenet_name,c.reg_no as reg_no,c.site_id as site_id,site.name as site_name,c.major_id as major_id,major.name as major_name,c.edutype_id as edutype_id,edutype.name as edutype_name,c.grade_id as grade_id,grade.name as grade_name from "
					+ " (select id,student_id,teachclass_id from entity_elective where id='"
					+ id
					+ "') a,"
					+ "entity_teach_class b,entity_student_info c,entity_site_info site,entity_major_info major,entity_edu_type edutype, entity_grade_info grade "
					+ "where a.teachclass_id=b.id and a.student_id=c.id  and c.site_id=site.id and c.major_id=major.id and c.edutype_id=edutype.id and c.grade_id=grade.id";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleTeachClass teachClass = new OracleTeachClass();
				teachClass.setId(rs.getString("teachclass_id"));
				teachClass.setName(rs.getString("teachclass_name"));
				this.setTeachClass(teachClass);
				OracleStudent student = new OracleStudent();
				student.setId(rs.getString("student_id"));
				student.setName(rs.getString("student_name"));
				StudentEduInfo studentInfo = new StudentEduInfo();
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
				this.setStudent(student);

			}
		} catch (java.sql.SQLException e) {

		} finally {
			db.close(rs);
			db = null;
		}
	}

	public List getElectiveId(String teachclassId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		List elect = new ArrayList();
		try {
			sql = "select  id  from entity_elective where teachclass_id='"
					+ teachclassId + "'";
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				elect.add(rs.getString("id"));

			}
		} catch (java.sql.SQLException e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return elect;
	}

	public List getElective(String teachclassId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		List elect = new ArrayList();
		try {
			sql = "select  id,free_total_score_status  from pr_tch_stu_elective where teachclass_id='"
					+ teachclassId + "'";
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("id", rs.getString("id"));
				hash.put("free_total_score_status", rs
						.getString("free_total_score_status"));
				elect.add(hash);

			}
		} catch (java.sql.SQLException e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return elect;
	}

	public String getElectiveTimes(String courseId, String student_id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		String second = "0";
		try {
//			sql = "select online_time from pr_tch_stu_elective where fk_opencourse_id='"
//					+ teachclass_id + "' and fk_stu_id ='" + student_id + "'";
			sql = " select e1.online_time						  " +
			"   from pr_bzz_tch_stu_elective e1, pr_bzz_tch_opencourse o  " +
			"  where e1.fk_tch_opencourse_id = o.id                   " +
			"    and o.fk_course_id = '" + courseId + "'" +
			"    and e1.fk_stu_id = '" + student_id + "'";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				second = rs.getString("online_time");
			}
		} catch (java.sql.SQLException e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return second;
	}

	public int setElectiveTimes(String courseId, String student_id,
			String time) {
		dbpool db = new dbpool();
		String sql = "";
//		sql = "update pr_tch_stu_elective set online_time='" + time
//				+ "' where fk_opencourse_id='" + teachclass_id
//				+ "' and fk_stu_id='" + student_id + "'";
		sql = "update pr_bzz_tch_stu_elective el																				" +
		"   set el.online_time = '" + time +"'                                " +
		" where el.fk_tch_opencourse_id in                                        " +
		"       (select o.id                                                  " +
		"          from pr_bzz_tch_opencourse o                                   " +
		"         where o.fk_course_id = '" + courseId+ "')  " +
		"   and el.fk_stu_id = '" + student_id + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleElective.setElectiveTimes(String courseId, String student_id,String time) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	/**
	 * 判断课程是否完成学习
	 */
	public boolean isLearningCompleted(String courseId, String student_id) {
		dbpool db = new dbpool();
		int cou=0;
		String sql =
				"select 1\n" +
				"  from training_course_student tcs\n" + 
				" where tcs.student_id = '" + student_id + "'\n" + 
				"   and tcs.course_id = '" + courseId + "'\n" + 
				"   and tcs.learn_status = 'COMPLETED'";
			cou = db.countselect(sql);
		return cou>0;
	}
	
	/**
	 * 判断课程是否完成满意度调查
	 */
	public boolean isManyiCompleted(String courseId, String student_id) {
		dbpool db = new dbpool();
		int cou=0;
		String sql =
			"select pvp.id\n" +
			"  from pe_vote_paper pvp, pr_vote_record pvr\n" + 
			" where pvp.id = pvr.fk_vote_paper_id\n" + 
			"   and pvp.courseid = '"+courseId+"'\n" + 
			"   and pvr.ssoid = '"+student_id+"'";
			cou = db.countselect(sql);
		return cou>0;
	}
	/**
	 * 用于判断选课表中是否存在考试成绩，如果存在考试成绩，则不进行满意度调查问卷提示，直接进入考试
	 * @param student_id
	 * @param course_id
	 * @return
	 */
	public boolean isShowManyi(String student_id, String course_id) {
		dbpool db = new dbpool();
		int cou=0;
		String sql =

			"select case\n" +
			"         when nvl(ele.exam_times,0)<1 then\n" + 
			"          0\n" + 
			"         else\n" + 
			"          1\n" + 
			"       END as score\n" + 
			"  from pr_bzz_tch_stu_elective ele\n" + 
			" inner join pr_bzz_tch_opencourse pbto on ele.fk_tch_opencourse_id =\n" + 
			"                                          pbto.id\n" + 
			" inner join pe_bzz_student pbs on ele.fk_stu_id = pbs.id\n" + 
			" inner join pe_bzz_tch_course pbtc on pbtc.id = pbto.fk_course_id\n" + 
			"\n" + 
			" where pbtc.id = '"+course_id+"'\n" + 
			"   and pbs.fk_sso_user_id = '"+student_id+"'";




			MyResultSet rs = db.executeQuery(sql);
			try {
				if(rs.next()) {
					cou = rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				db.close(rs);
				db=null;
			}
		return cou>0;
		
	}
}
