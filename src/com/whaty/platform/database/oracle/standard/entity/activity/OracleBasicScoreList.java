package com.whaty.platform.database.oracle.standard.entity.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourse;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSemester;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.entity.activity.BasicScoreList;
import com.whaty.platform.entity.activity.score.ElectiveScoreType;
import com.whaty.platform.entity.activity.score.ScoreDef;
import com.whaty.platform.entity.activity.score.ScoreModify;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.EntityLog;

public class OracleBasicScoreList extends BasicScoreList {

	public List searchElectiveScores(Page page, List searchproperties,
			List orderproperties) throws PlatformException {
		dbpool db = new dbpool();
		/*
		 * String SQL = "select distinct
		 * id,student_name,student_id,reg_no,major_id,site_id,site_name,edutype_id,grade_id,grade_name,edutype_name,major_name,open_course_id,course_id,course_name,course_credit,course_time,semester_id,semester_name,status,score_status,total_score,total_scorelevel,total_score_viewlevel,total_scoredes,total_score_zerodes,total_score_zerocause,usual_score,usual_scorelevel,usual_score_viewlevel,usual_scoredes,usual_score_zerodes,usual_score_zerocause,exam_score,exam_scorelevel,exam_score_viewlevel,exam_scoredes,exam_score_zerodes,exam_score_zerocause,experiment_score,experiment_scorelevel,experiment_score_viewlevel,experiment_scoredes,experiment_score_zerodes,experiment_score_zerocause,expend_score,expend_scorelevel,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,expend_score_zerocause,renew_score,renew_scorelevel,renew_score_viewlevel,renew_scoredes,renew_score_zerodes,renew_score_zerocause,expend_score_status,expend_score_student_status
		 * from (select e.id as id,s.name as student_name,s.id as
		 * student_id,s.reg_no as reg_no,s.major_id,s.site_id,site.name as
		 * site_name,s.edutype_id,s.grade_id,grade.name as
		 * grade_name,edutype.name as edutype_name,major.name as
		 * major_name,t.open_course_id,ca.course_id,ci.name as
		 * course_name,ci.credit as course_credit,ci.course_time as
		 * course_time,si.id as semester_id,si.name as
		 * semester_name,e.status,e.score_status,e.total_score,e.total_scorelevel,e.total_score_viewlevel,e.total_scoredes,e.total_score_zerodes,e.total_score_zerocause,e.usual_score,e.usual_scorelevel,e.usual_score_viewlevel,e.usual_scoredes,e.usual_score_zerodes,e.usual_score_zerocause,e.exam_score,e.exam_scorelevel,e.exam_score_viewlevel,e.exam_scoredes,e.exam_score_zerodes,e.exam_score_zerocause,e.experiment_score,experiment_scorelevel,e.experiment_score_viewlevel,e.experiment_scoredes,e.experiment_score_zerodes,e.experiment_score_zerocause,e.expend_score,e.expend_scorelevel,e.expend_score_viewlevel,e.expend_scoredes,e.expend_score_zerodes,e.expend_score_zerocause,e.renew_score,e.renew_scorelevel,e.renew_score_viewlevel,e.renew_scoredes,e.renew_score_zerodes,e.renew_score_zerocause,e.expend_score_status
		 * as expend_score_status,e.expend_score_student_status as
		 * expend_score_student_status from entity_elective
		 * e,entity_student_info s,entity_teach_class t,entity_course_active
		 * ca,entity_semester_info si,entity_course_info ci,entity_site_info
		 * site,entity_edu_type edutype,entity_major_info
		 * major,entity_grade_info grade where s.id=e.student_id and
		 * e.teachclass_id=t.id and t.open_course_id=ca.id and
		 * ca.semester_id=si.id and ca.course_id=ci.id and site.id=s.site_id and
		 * s.isgraduated='0' and s.status='0' and s.edutype_id=edutype.id and
		 * s.grade_id=grade.id and s.major_id=major.id" +
		 * Conditions.convertToCondition(null, orderproperties) + ")";
		 * 
		 * String sql = SQL+
		 * Conditions.convertToCondition(searchproperties,orderproperties);
		 */

		String t_class_where = "";
		String stu_where = "";
		String cour_where = "";
		if (searchproperties != null) {
			for (int ii = 0; ii < searchproperties.size(); ii++) {
				SearchProperty prop = (SearchProperty) searchproperties.get(ii);
				String field = prop.getField();
				String val = prop.getValue();
				if (field != null && field.equals("site_id"))
					stu_where += " and site_id='" + val + "'";
				if (field != null && field.equals("major_id"))
					stu_where += " and major_id='" + val + "'";
				if (field != null && field.equals("edutype_id"))
					stu_where += " and edutype_id='" + val + "'";
				if (field != null && field.equals("grade_id"))
					stu_where += " and grade_id='" + val + "'";

				if (field != null && field.equals("course_id"))
					cour_where += " and course_id='" + val + "'";
				if (field != null && field.equals("semester_id"))
					cour_where += " and semester_id='" + val + "'";

				if (field != null && field.equals("open_course_id"))
					t_class_where += " and open_course_id='" + val + "'";
			}
		}
		String SQL = "select distinct id,student_name,student_id,reg_no,major_id,site_id,site_name,edutype_id,grade_id,grade_name,edutype_name,major_name,open_course_id,course_id,course_name,course_credit,course_time,semester_id,semester_name,status,score_status,total_score,total_scorelevel,total_score_viewlevel,total_scoredes,total_score_zerodes,total_score_zerocause,total_score_status,usual_score,usual_scorelevel,usual_score_viewlevel,usual_scoredes,usual_score_zerodes,usual_score_zerocause,usual_score_status,exam_score,exam_scorelevel,exam_score_viewlevel,exam_scoredes,exam_score_zerodes,exam_score_zerocause,exam_score_status,experiment_score,experiment_scorelevel,experiment_score_viewlevel,experiment_scoredes,experiment_score_zerodes,experiment_score_zerocause,experiment_score_status,expend_score,expend_scorelevel,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,expend_score_zerocause,expend_score_status,expend_score_student_status,renew_score,renew_scorelevel,renew_score_viewlevel,renew_scoredes,renew_score_zerodes,renew_score_zerocause,free_total_score_status,total_expend_score,test_score from (select e.id as id,s.name as student_name,s.id as student_id,s.reg_no as reg_no,s.major_id,s.site_id,site.name as site_name,s.edutype_id as edutype_id,s.grade_id as grade_id,grade.name as grade_name,edutype.name as edutype_name,major.name as major_name,t.open_course_id as open_course_id,ca.course_id as course_id,ci.name as course_name,ci.credit as course_credit,ci.course_time as course_time,si.id as semester_id,si.name as semester_name,e.status as status,e.score_status as score_status,e.total_score as total_score,e.total_scorelevel as total_scorelevel,e.total_score_viewlevel as total_score_viewlevel,e.total_scoredes as total_scoredes,e.total_score_zerodes as total_score_zerodes,e.total_score_zerocause as total_score_zerocause,e.total_score_status as total_score_status,e.usual_score as usual_score,e.usual_scorelevel as usual_scorelevel,e.usual_score_viewlevel as usual_score_viewlevel ,e.usual_scoredes as usual_scoredes,e.usual_score_zerodes as usual_score_zerodes,e.usual_score_zerocause as usual_score_zerocause,e.usual_score_status as usual_score_status,e.exam_score as exam_score,e.exam_scorelevel as exam_scorelevel,e.exam_score_viewlevel as exam_score_viewlevel,e.exam_scoredes as exam_scoredes,e.exam_score_zerodes as exam_score_zerodes,e.exam_score_zerocause as exam_score_zerocause,e.exam_score_status as exam_score_status,e.experiment_score as experiment_score,experiment_scorelevel as experiment_scorelevel,e.experiment_score_viewlevel as experiment_score_viewlevel,e.experiment_scoredes as experiment_scoredes ,e.experiment_score_zerodes as experiment_score_zerodes,e.experiment_score_zerocause as experiment_score_zerocause,e.experiment_score_status as experiment_score_status,e.expend_score as expend_score,e.expend_scorelevel as expend_scorelevel,e.expend_score_viewlevel as expend_score_viewlevel,e.expend_scoredes as expend_scoredes,e.expend_score_zerodes as expend_score_zerodes ,e.expend_score_zerocause as expend_score_zerocause,e.expend_score_status as expend_score_status,e.expend_score_student_status as expend_score_student_status ,e.renew_score as renew_score,e.renew_scorelevel as renew_scorelevel,e.renew_score_viewlevel as renew_score_viewlevel,e.renew_scoredes as renew_scoredes,e.renew_score_zerodes as renew_score_zerodes,e.renew_score_zerocause as renew_score_zerocause,e.free_total_score_status as free_total_score_status,e.total_expend_score as total_expend_score,e.test_score as test_score  from entity_elective e,(select * from entity_student_info where 1=1 "
				+ stu_where
				+ ") s,(select * from entity_teach_class where 1=1 "
				+ t_class_where
				+ ") t,(select * from entity_course_active where 1=1 "
				+ cour_where
				+ ") ca,entity_semester_info si,entity_course_info ci,entity_site_info site,entity_edu_type edutype,entity_major_info major,entity_grade_info grade where s.id=e.student_id and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.semester_id=si.id and ca.course_id=ci.id and site.id=s.site_id and s.isgraduated='0' and s.status='0' and s.edutype_id=edutype.id and s.grade_id=grade.id and s.major_id=major.id"
				+ Conditions.convertToCondition(null, orderproperties) + ")";
		String sql = SQL
				+ Conditions.convertToCondition(searchproperties,
						orderproperties);

		MyResultSet rs = null;
		ArrayList electiveScoreList = new ArrayList();
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleElectiveScore electivescore = new OracleElectiveScore();
				OracleElective elective = new OracleElective();
				List scoreList = new ArrayList();
				OracleTeachClass teachclass = new OracleTeachClass();
				OracleSemester semester = new OracleSemester();
				OracleOpenCourse opencourse = new OracleOpenCourse();
				OracleCourse course = new OracleCourse();
				OracleStudent student = new OracleStudent();
				StudentEduInfo eduinfo = new StudentEduInfo();

				elective.setId(rs.getString("id"));
				student.setId(rs.getString("student_id"));
				student.setName(rs.getString("student_name"));
				eduinfo.setReg_no(rs.getString("reg_no"));
				eduinfo.setMajor_id(rs.getString("major_id"));
				eduinfo.setSite_id(rs.getString("site_id"));
				eduinfo.setSite_name(rs.getString("site_name"));
				eduinfo.setEdutype_id(rs.getString("edutype_id"));
				eduinfo.setGrade_id(rs.getString("grade_id"));
				eduinfo.setGrade_name(rs.getString("grade_name"));
				eduinfo.setEdutype_name(rs.getString("edutype_name"));
				eduinfo.setMajor_name(rs.getString("major_name"));

				student.setStudentInfo(eduinfo);
				elective.setStudent(student);
				opencourse.setId(rs.getString("open_course_id"));
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				course.setCredit(rs.getString("course_credit"));
				course.setCourse_time(rs.getString("course_time"));
//				opencourse.setCourse(course);
				semester.setId(rs.getString("semester_id"));
				semester.setName(rs.getString("semester_name"));
//				opencourse.setSemester(semester);
				teachclass.setOpenCourse(opencourse);
				elective.setTeachClass(teachclass);
				elective.setElectiveStatus(rs.getString("status"));
				elective.setExpendScoreStatus(rs
						.getString("expend_score_status"));
				elective.setExpendScoreStudentStatus(rs
						.getString("expend_score_student_status"));
				elective
						.setUsualScoreStatus(rs.getString("usual_score_status"));
				elective.setExperimentScoreStatus(rs
						.getString("experiment_score_status"));
				elective.setExamScoreStatus(rs.getString("exam_score_status"));
				elective
						.setTotalScoreStatus(rs.getString("total_score_status"));
				elective.setFree_total_score_status(rs
						.getString("free_total_score_status"));
				elective.setTotal_expend_score(rs
						.getString("total_expend_score"));
				elective.setTest_score(rs.getString("test_score"));
				electivescore.setElective(elective);
				electivescore.setScore_Status(rs.getString("score_status"));

				ScoreDef score_total = new ScoreDef();
				score_total.setTitle(ElectiveScoreType
						.typeShow(ElectiveScoreType.TOTAL));
				score_total.setType(ElectiveScoreType.TOTAL);
				score_total.setScore(rs.getFloat("total_score"));
				score_total.setScoreDes(rs.getString("total_scoredes"));
				score_total.setScoreLevel(rs.getString("total_scorelevel"));
				if (rs.getInt("total_score_viewlevel") == 1) {
					score_total.setLevelScore(true);
				} else {
					score_total.setLevelScore(false);
				}
				score_total.setZeroCause(rs.getString("total_score_zerocause"));
				score_total.setZeroDes(rs.getString("total_score_zerodes"));
				scoreList.add(score_total);

				ScoreDef score_usual = new ScoreDef();
				score_usual.setTitle(ElectiveScoreType
						.typeShow(ElectiveScoreType.USUAL));
				score_usual.setType(ElectiveScoreType.USUAL);
				score_usual.setScore(rs.getFloat("usual_score"));
				score_usual.setScoreDes(rs.getString("usual_scoredes"));
				score_usual.setScoreLevel(rs.getString("usual_scorelevel"));
				if (rs.getInt("usual_score_viewlevel") == 1) {
					score_usual.setLevelScore(true);
				} else {
					score_usual.setLevelScore(false);
				}
				score_usual.setZeroCause(rs.getString("usual_score_zerocause"));
				score_usual.setZeroDes(rs.getString("usual_score_zerodes"));
				scoreList.add(score_usual);

				ScoreDef score_exam = new ScoreDef();
				score_exam.setTitle(ElectiveScoreType
						.typeShow(ElectiveScoreType.EXAM));
				score_exam.setType(ElectiveScoreType.EXAM);
				score_exam.setScore(rs.getFloat("exam_score"));
				score_exam.setScoreDes(rs.getString("exam_scoredes"));
				score_exam.setScoreLevel(rs.getString("exam_scorelevel"));
				if (rs.getInt("exam_score_viewlevel") == 1) {
					score_exam.setLevelScore(true);
				} else {
					score_exam.setLevelScore(false);
				}
				score_exam.setZeroCause(rs.getString("exam_score_zerocause"));
				score_exam.setZeroDes(rs.getString("exam_score_zerodes"));
				scoreList.add(score_exam);

				ScoreDef score_experiment = new ScoreDef();
				score_experiment.setTitle(ElectiveScoreType
						.typeShow(ElectiveScoreType.EXPERIMENT));
				score_experiment.setType(ElectiveScoreType.EXPERIMENT);
				score_experiment.setScore(rs.getFloat("experiment_score"));
				score_experiment.setScoreDes(rs
						.getString("experiment_scoredes"));
				score_experiment.setScoreLevel(rs
						.getString("experiment_scorelevel"));
				if (rs.getInt("experiment_score_viewlevel") == 1) {
					score_experiment.setLevelScore(true);
				} else {
					score_experiment.setLevelScore(false);
				}
				score_experiment.setZeroCause(rs
						.getString("experiment_score_zerocause"));
				score_experiment.setZeroDes(rs
						.getString("experiment_score_zerodes"));
				scoreList.add(score_experiment);

				ScoreDef score_expend = new ScoreDef();
				score_expend.setTitle(ElectiveScoreType
						.typeShow(ElectiveScoreType.EXPEND));
				score_expend.setType(ElectiveScoreType.EXPEND);
				score_expend.setScore(rs.getFloat("expend_score"));
				score_expend.setScoreDes(rs.getString("expend_scoredes"));
				score_expend.setScoreLevel(rs.getString("expend_scorelevel"));
				if (rs.getInt("expend_score_viewlevel") == 1) {
					score_expend.setLevelScore(true);
				} else {
					score_expend.setLevelScore(false);
				}
				score_expend.setZeroCause(rs
						.getString("expend_score_zerocause"));
				score_expend.setZeroDes(rs.getString("expend_score_zerodes"));
				scoreList.add(score_expend);

				ScoreDef score_renew = new ScoreDef();
				score_renew.setTitle(ElectiveScoreType
						.typeShow(ElectiveScoreType.RENEW));
				score_renew.setType(ElectiveScoreType.RENEW);
				score_renew.setScore(rs.getFloat("renew_score"));
				score_renew.setScoreDes(rs.getString("renew_scoredes"));
				score_renew.setScoreLevel(rs.getString("renew_scorelevel"));
				if (rs.getInt("renew_score_viewlevel") == 1) {
					score_renew.setLevelScore(true);
				} else {
					score_renew.setLevelScore(false);
				}
				score_renew.setZeroCause(rs.getString("renew_score_zerocause"));
				score_renew.setZeroDes(rs.getString("renew_score_zerodes"));
				scoreList.add(score_renew);
				electivescore.setScoreList(scoreList);

				electiveScoreList.add(electivescore);
			}
		} catch (Exception e) {
			EntityLog
					.setError("OracleBasicScoreList@searchElectiveScores(Page page, List searchproperties,List orderproperties) Error! SQL = "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return electiveScoreList;
	}

	public List searchElectiveScoresStatics(Page page, List searchproperties,
			List orderproperties) throws PlatformException {
		dbpool db = new dbpool();

		String t_class_where = "";
		String stu_where = "";
		String cour_where = "";
		if (searchproperties != null) {
			for (int ii = 0; ii < searchproperties.size(); ii++) {
				SearchProperty prop = (SearchProperty) searchproperties.get(ii);
				String field = prop.getField();
				String val = prop.getValue();
				if (field != null && field.equals("site_id"))
					stu_where += " and site_id='" + val + "'";
				if (field != null && field.equals("major_id"))
					stu_where += " and major_id='" + val + "'";
				if (field != null && field.equals("edutype_id"))
					stu_where += " and edutype_id='" + val + "'";
				if (field != null && field.equals("grade_id"))
					stu_where += " and grade_id='" + val + "'";

				if (field != null && field.equals("course_id"))
					cour_where += " and course_id='" + val + "'";
				if (field != null && field.equals("semester_id"))
					cour_where += " and semester_id='" + val + "'";

				if (field != null && field.equals("open_course_id"))
					t_class_where += " and open_course_id='" + val + "'";
			}
		}
		String SQL = "select distinct id,student_name,student_id,reg_no,major_id,site_id,site_name,edutype_id,grade_id,open_course_id,course_id,semester_id,status,score_status,total_score,exam_score from (select e.id as id,s.name as student_name,s.id as student_id,s.reg_no as reg_no,s.major_id,s.site_id,site.name as site_name,s.edutype_id,s.grade_id,edutype.name as edutype_name,major.name as major_name,t.open_course_id,ca.course_id,ci.name as course_name,ci.credit as course_credit,ci.course_time as course_time,si.id as semester_id,si.name as semester_name,e.status,e.score_status,e.total_score,e.total_scorelevel,e.total_score_viewlevel,e.total_scoredes,e.total_score_zerodes,e.total_score_zerocause,e.usual_score,e.usual_scorelevel,e.usual_score_viewlevel,e.usual_scoredes,e.usual_score_zerodes,e.usual_score_zerocause,e.exam_score,e.exam_scorelevel,e.exam_score_viewlevel,e.exam_scoredes,e.exam_score_zerodes,e.exam_score_zerocause,e.expend_score,e.expend_scorelevel,e.expend_score_viewlevel,e.expend_scoredes,e.expend_score_zerodes,e.expend_score_zerocause,e.renew_score,e.renew_scorelevel,e.renew_score_viewlevel,e.renew_scoredes,e.renew_score_zerodes,e.renew_score_zerocause,e.expend_score_status as expend_score_status,e.expend_score_student_status as expend_score_student_status from entity_elective e,(select * from entity_student_info where 1=1 "
				+ stu_where
				+ ") s,(select * from entity_teach_class where 1=1 "
				+ t_class_where
				+ ") t,(select * from entity_course_active where 1=1 "
				+ cour_where
				+ ") ca,entity_semester_info si,entity_course_info ci,entity_site_info site,entity_edu_type edutype,entity_major_info major where s.id=e.student_id and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.semester_id=si.id and ca.course_id=ci.id and site.id=s.site_id and s.isgraduated='0' and s.status='0' and s.edutype_id=edutype.id and s.major_id=major.id"
				+ Conditions.convertToCondition(null, orderproperties) + ")";
		String sql = SQL
				+ Conditions.convertToCondition(searchproperties,
						orderproperties);
		MyResultSet rs = null;

		ArrayList scoreList = new ArrayList();

		String site_name = "";

		float total_score = 0f;
		int countA = 0;// big or equal 90
		int countB = 0;// between 80-89
		int countC = 0;// between 70-79
		int countD = 0;// between 60-69
		int countE = 0;// less 59.0
		int countF = 0;// equal 0
		int countTotal = 0;// all num

		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {

				site_name = rs.getString("site_name");
				total_score = rs.getFloat("total_score");

				if (total_score >= 90)
					countA++;
				if (total_score >= 80 && total_score <= 89)
					countB++;
				if (total_score >= 70 && total_score <= 79)
					countC++;
				if (total_score >= 60 && total_score <= 69)
					countD++;
				if (total_score > 0 && total_score <= 59)
					countE++;
				if (total_score == 0)
					countF++;

				countTotal++;

				HashMap score = new HashMap();
				score.put("siteName", site_name);

				score.put("countTotal", Integer.toString(countTotal));
				score.put("countA", Integer.toString(countA));
				score.put("countB", Integer.toString(countB));
				score.put("countC", Integer.toString(countC));
				score.put("countD", Integer.toString(countD));
				score.put("countE", Integer.toString(countE));
				score.put("countF", Integer.toString(countF));
				scoreList.add(score);

			}
		} catch (Exception e) {
			EntityLog
					.setError("OracleBasicScoreList@searchElectiveScoresStatics(Page page, List searchproperties,List orderproperties) Error! SQL = "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return scoreList;
	}

	public List searchElectiveScoresStatics1(String open_course_id,
			String grade_id, String edutype_id, String major_id,
			String scoretype) throws PlatformException {
		dbpool db = new dbpool();
		List score = new ArrayList();
		MyResultSet rs = null;
		String sql = "";
		String teachClassId = "";
		OracleTeachClass teach = new OracleTeachClass();
		teachClassId = teach.getTeachClassId(open_course_id);
		try {
			StringBuffer conditionbuffer = new StringBuffer();
			String condition = "";
			if (teachClassId == null || teachClassId.equals("")
					|| teachClassId.equals("null")) {
			} else {

				conditionbuffer.append("  and e.teachclass_id= '").append(
						teachClassId).append("' ");
			}

			if (grade_id == null || grade_id.equals("")
					|| grade_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.grade_id = '").append(grade_id)
						.append("' ");
			}

			if (edutype_id == null || edutype_id.equals("")
					|| edutype_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.edutype_id = '").append(
						edutype_id).append("' ");
			}

			if (major_id == null || major_id.equals("")
					|| major_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.major_id = '").append(major_id)
						.append("' ");
			}
			condition = conditionbuffer.toString();
			if (scoretype.equals("total_score")) {
				sql = "select a.site_id as site_id,a.site_name as site_name,nvl(a.total_num,0) as total_num,"
						+ "nvl(b.best_num,0) as best_num,nvl(c.better_num,0) as better_num,nvl(d.good_num,0) as good_num,"
						+ "nvl(e.bad_num,0) as bad_num,nvl(f.worse_num,0) as worse_num from  "
						+ "(select site_id,site_name, count(id) as total_num  from "
						+ "(select e.id as id,s.site_id as site_id,site.name as site_name from entity_elective e,"
						+ "entity_student_info s,entity_site_info site where e.student_id=s.id and e.status='1'"
						+ " and s.isgraduated='0' and s.site_id=site.id"
						+ condition
						+ " ) group by site_id,site_name) a,"
						+ "(select site_id,site_name, count(id) as best_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site where "
						+ "e.student_id=s.id and e.status='1' and s.isgraduated='0' "
						+ condition
						+ "and s.site_id=site.id and e.total_score>=90 ) group by site_id,site_name) b,"
						+ "(select site_id,site_name, count(id) as better_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0'"
						+ condition
						+ " and s.site_id=site.id and e.total_score>=80 and e.total_score<=89 ) group by site_id,site_name) c,"
						+ "(select site_id,site_name, count(id) as good_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "and e.total_score>=70 and e.total_score<=79 ) group by site_id,site_name) d,"
						+ "(select site_id,site_name, count(id) as bad_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ " and e.total_score>=60 and e.total_score<=69 ) group by site_id,site_name) e,"
						+ "(select site_id,site_name, count(id) as worse_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site"
						+ " where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and e.total_score<60 ) group by site_id,site_name) f where a.site_id=b.site_id(+) and "
						+ "a.site_id=c.site_id(+) and a.site_id=d.site_id(+) and a.site_id=e.site_id(+) and a.site_id=f.site_id(+)";

			} else if (scoretype.equals("exam_score")) {

				sql = "select a.site_id as site_id,a.site_name as site_name,nvl(a.total_num,0) as total_num,"
						+ "nvl(b.best_num,0) as best_num,nvl(c.better_num,0) as better_num,nvl(d.good_num,0) as good_num,"
						+ "nvl(e.bad_num,0) as bad_num,nvl(f.worse_num,0) as worse_num from  "
						+ "(select site_id,site_name, count(id) as total_num  from "
						+ "(select e.id as id,s.site_id as site_id,site.name as site_name from entity_elective e,"
						+ "entity_student_info s,entity_site_info site where e.student_id=s.id and e.status='1'"
						+ " and s.isgraduated='0' and s.site_id=site.id"
						+ condition
						+ " ) group by site_id,site_name) a,"
						+ "(select site_id,site_name, count(id) as best_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site where "
						+ "e.student_id=s.id and e.status='1' and s.isgraduated='0' "
						+ condition
						+ "and s.site_id=site.id and e.exam_score>=90 ) group by site_id,site_name) b,"
						+ "(select site_id,site_name, count(id) as better_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0'"
						+ condition
						+ " and s.site_id=site.id and e.exam_score>=80 and e.exam_score<=89 ) group by site_id,site_name) c,"
						+ "(select site_id,site_name, count(id) as good_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "and e.exam_score>=70 and e.exam_score<=79 ) group by site_id,site_name) d,"
						+ "(select site_id,site_name, count(id) as bad_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and e.exam_score>=60 and e.exam_score<=69 ) group by site_id,site_name) e,"
						+ "(select site_id,site_name, count(id) as worse_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site"
						+ " where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and  e.exam_score<60 ) group by site_id,site_name) f where a.site_id=b.site_id(+) and "
						+ "a.site_id=c.site_id(+) and a.site_id=d.site_id(+) and a.site_id=e.site_id(+) and a.site_id=f.site_id(+)";

			}
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("site_id", rs.getString("site_id"));
				hash.put("site_name", rs.getString("site_name"));
				hash.put("total_num", rs.getString("total_num"));
				hash.put("best_num", rs.getString("best_num"));
				hash.put("better_num", rs.getString("better_num"));
				hash.put("good_num", rs.getString("good_num"));
				hash.put("bad_num", rs.getString("bad_num"));
				hash.put("worse_num", rs.getString("worse_num"));
				score.add(hash);
			}
		} catch (Exception e) {
			EntityLog
					.setError("OracleBasicScoreList@searchElectiveScoresStatics1(String open_course_id,String grade_id, String edutype_id, String major_id,String scoretype)  Error! SQL = "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return score;
	}

	public List searchElectiveScoresStatics1(String open_course_id,
			String grade_id, String edutype_id, String major_id,
			String scoretype, String[] sec) throws PlatformException {
		dbpool db = new dbpool();
		List score = new ArrayList();
		MyResultSet rs = null;
		String sql = "";
		String teachClassId = "";
		OracleTeachClass teach = new OracleTeachClass();
		teachClassId = teach.getTeachClassId(open_course_id);
		try {
			StringBuffer conditionbuffer = new StringBuffer();
			String condition = "";
			if (teachClassId == null || teachClassId.equals("")
					|| teachClassId.equals("null")) {
			} else {

				conditionbuffer.append("  and e.teachclass_id= '").append(
						teachClassId).append("' ");
			}

			if (grade_id == null || grade_id.equals("")
					|| grade_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.grade_id = '").append(grade_id)
						.append("' ");
			}

			if (edutype_id == null || edutype_id.equals("")
					|| edutype_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.edutype_id = '").append(
						edutype_id).append("' ");
			}

			if (major_id == null || major_id.equals("")
					|| major_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.major_id = '").append(major_id)
						.append("' ");
			}
			condition = conditionbuffer.toString();
			if (scoretype.equals("total_score")) {
				sql = "select a.site_id as site_id,a.site_name as site_name,nvl(a.total_num,0) as total_num,"
						+ "nvl(b.best_num,0) as best_num,nvl(c.better_num,0) as better_num,nvl(d.good_num,0) as good_num,"
						+ "nvl(e.bad_num,0) as bad_num,nvl(f.worse_num,0) as worse_num from  "
						+ "(select site_id,site_name, count(id) as total_num  from "
						+ "(select e.id as id,s.site_id as site_id,site.name as site_name from entity_elective e,"
						+ "entity_student_info s,entity_site_info site where e.student_id=s.id and e.status='1'"
						+ " and s.isgraduated='0' and s.site_id=site.id"
						+ condition
						+ " ) group by site_id,site_name) a,"
						+ "(select site_id,site_name, count(id) as best_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site where "
						+ "e.student_id=s.id and e.status='1' and s.isgraduated='0' "
						+ condition
						+ "and s.site_id=site.id and e.free_total_score_status='0' and to_number(e.total_score)>="
						+ sec[0]
						+ " ) group by site_id,site_name) b,"
						+ "(select site_id,site_name, count(id) as better_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0'"
						+ condition
						+ " and s.site_id=site.id and e.free_total_score_status='0' and to_number(e.total_score)>="
						+ sec[1]
						+ " and to_number(e.total_score)<"
						+ sec[0]
						+ " ) group by site_id,site_name) c,"
						+ "(select site_id,site_name, count(id) as good_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and e.free_total_score_status='0' and to_number(e.total_score)>="
						+ sec[2]
						+ " and to_number(e.total_score)<"
						+ sec[1]
						+ " ) group by site_id,site_name) d,"
						+ "(select site_id,site_name, count(id) as bad_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ " and e.free_total_score_status='0' and to_number(e.total_score)>="
						+ sec[3]
						+ " and to_number(e.total_score)<"
						+ sec[2]
						+ " ) group by site_id,site_name) e,"
						+ "(select site_id,site_name, count(id) as worse_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site"
						+ " where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ " and e.free_total_score_status='0' and to_number(e.total_score)>="
						+ sec[4]
						+ " and to_number(e.total_score)<"
						+ sec[3]
						+ ") group by site_id,site_name) f where a.site_id=b.site_id(+) and "
						+ "a.site_id=c.site_id(+) and a.site_id=d.site_id(+) and a.site_id=e.site_id(+) and a.site_id=f.site_id(+)";

			} else if (scoretype.equals("exam_score")) {

				sql = "select a.site_id as site_id,a.site_name as site_name,nvl(a.total_num,0) as total_num,"
						+ "nvl(b.best_num,0) as best_num,nvl(c.better_num,0) as better_num,nvl(d.good_num,0) as good_num,"
						+ "nvl(e.bad_num,0) as bad_num,nvl(f.worse_num,0) as worse_num from  "
						+ "(select site_id,site_name, count(id) as total_num  from "
						+ "(select e.id as id,s.site_id as site_id,site.name as site_name from entity_elective e,"
						+ "entity_student_info s,entity_site_info site where e.student_id=s.id and e.status='1'"
						+ " and s.isgraduated='0' and s.site_id=site.id"
						+ condition
						+ " ) group by site_id,site_name) a,"
						+ "(select site_id,site_name, count(id) as best_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site where "
						+ "e.student_id=s.id and e.status='1' and s.isgraduated='0' "
						+ condition
						+ "and s.site_id=site.id and e.free_total_score_status='0' and to_number(e.exam_score)>="
						+ sec[0]
						+ " ) group by site_id,site_name) b,"
						+ "(select site_id,site_name, count(id) as better_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0'"
						+ condition
						+ " and s.site_id=site.id and e.free_total_score_status='0' and to_number(e.exam_score)>="
						+ sec[1]
						+ " and to_number(e.exam_score)<"
						+ sec[0]
						+ " ) "
						+ "group by site_id,site_name) c,"
						+ "(select site_id,site_name, count(id) as good_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "and e.free_total_score_status='0' and to_number(e.exam_score)>="
						+ sec[2]
						+ " and to_number(e.exam_score)<"
						+ sec[1]
						+ " ) group by site_id,site_name) d,"
						+ "(select site_id,site_name, count(id) as bad_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and e.free_total_score_status='0' and to_number(e.exam_score)>="
						+ sec[3]
						+ " and to_number(e.exam_score)<"
						+ sec[2]
						+ " ) group by site_id,site_name) e,"
						+ "(select site_id,site_name, count(id) as worse_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site"
						+ " where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and e.free_total_score_status='0' and to_number(e.exam_score)>="
						+ sec[4]
						+ " and to_number(e.exam_score)<"
						+ sec[3]
						+ ") group by site_id,site_name) f where a.site_id=b.site_id(+) and "
						+ "a.site_id=c.site_id(+) and a.site_id=d.site_id(+) and a.site_id=e.site_id(+) and a.site_id=f.site_id(+)";

			} else if (scoretype.equals("total_expend_score")) {

				sql = "select a.site_id as site_id,a.site_name as site_name,nvl(a.total_num,0) as total_num,"
						+ "nvl(b.best_num,0) as best_num,nvl(c.better_num,0) as better_num,nvl(d.good_num,0) as good_num,"
						+ "nvl(e.bad_num,0) as bad_num,nvl(f.worse_num,0) as worse_num from  "
						+ "(select site_id,site_name, count(id) as total_num  from "
						+ "(select e.id as id,s.site_id as site_id,site.name as site_name from entity_elective e,"
						+ "entity_student_info s,entity_site_info site where e.student_id=s.id and e.status='1'"
						+ " and s.isgraduated='0' and s.site_id=site.id"
						+ condition
						+ " ) group by site_id,site_name) a,"
						+ "(select site_id,site_name, count(id) as best_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site where "
						+ "e.student_id=s.id and e.status='1' and s.isgraduated='0' "
						+ condition
						+ "and s.site_id=site.id and e.free_total_score_status='0' and to_number(e.total_expend_score)>="
						+ sec[0]
						+ " ) group by site_id,site_name) b,"
						+ "(select site_id,site_name, count(id) as better_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0'"
						+ condition
						+ " and s.site_id=site.id and e.free_total_score_status='0' and to_number(e.total_expend_score)>="
						+ sec[1]
						+ " and to_number(e.total_expend_score)<"
						+ sec[0]
						+ " ) "
						+ "group by site_id,site_name) c,"
						+ "(select site_id,site_name, count(id) as good_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "and e.free_total_score_status='0' and to_number(e.total_expend_score)>="
						+ sec[2]
						+ " and to_number(e.total_expend_score)<"
						+ sec[1]
						+ " ) group by site_id,site_name) d,"
						+ "(select site_id,site_name, count(id) as bad_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and e.free_total_score_status='0' and to_number(e.total_expend_score)>="
						+ sec[3]
						+ " and to_number(e.total_expend_score)<"
						+ sec[2]
						+ " ) group by site_id,site_name) e,"
						+ "(select site_id,site_name, count(id) as worse_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site"
						+ " where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and e.free_total_score_status='0' and to_number(e.total_expend_score)>="
						+ sec[4]
						+ " and to_number(e.total_expend_score)<"
						+ sec[3]
						+ ") group by site_id,site_name) f where a.site_id=b.site_id(+) and "
						+ "a.site_id=c.site_id(+) and a.site_id=d.site_id(+) and a.site_id=e.site_id(+) and a.site_id=f.site_id(+)";

			}
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("site_id", rs.getString("site_id"));
				hash.put("site_name", rs.getString("site_name"));
				hash.put("total_num", rs.getString("total_num"));
				hash.put("best_num", rs.getString("best_num"));
				hash.put("better_num", rs.getString("better_num"));
				hash.put("good_num", rs.getString("good_num"));
				hash.put("bad_num", rs.getString("bad_num"));
				hash.put("worse_num", rs.getString("worse_num"));
				score.add(hash);
			}
		} catch (Exception e) {
			EntityLog
					.setError("OracleBasicScoreList@searchElectiveScoresStatics1(String open_course_id,String grade_id, String edutype_id, String major_id,String scoretype, String[] sec)  Error! SQL = "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return score;
	}

	public List searchElectiveScoresStatics1(String open_course_id,
			String grade_id, String edutype_id, String major_id,
			String scoretype, String[] sec, String status)
			throws PlatformException {
		dbpool db = new dbpool();
		List score = new ArrayList();
		MyResultSet rs = null;
		String sql = "";
		String teachClassId = "";
		OracleTeachClass teach = new OracleTeachClass();
		teachClassId = teach.getTeachClassId(open_course_id);
		try {
			StringBuffer conditionbuffer = new StringBuffer();
			String condition = "";
			if (teachClassId == null || teachClassId.equals("")
					|| teachClassId.equals("null")) {
			} else {

				conditionbuffer.append("  and e.teachclass_id= '").append(
						teachClassId).append("' ");
			}

			if (grade_id == null || grade_id.equals("")
					|| grade_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.grade_id = '").append(grade_id)
						.append("' ");
			}

			if (edutype_id == null || edutype_id.equals("")
					|| edutype_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.edutype_id = '").append(
						edutype_id).append("' ");
			}

			if (major_id == null || major_id.equals("")
					|| major_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.major_id = '").append(major_id)
						.append("' ");
			}
			condition = conditionbuffer.toString();
			if (scoretype.equals("total_score")) {
				sql = "select a.site_id as site_id,a.site_name as site_name,nvl(a.total_num,0) as total_num,"
						+ "nvl(b.best_num,0) as best_num,nvl(c.better_num,0) as better_num,nvl(d.good_num,0) as good_num,"
						+ "nvl(e.bad_num,0) as bad_num,nvl(f.worse_num,0) as worse_num from  "
						+ "(select site_id,site_name, count(id) as total_num  from "
						+ "(select e.id as id,s.site_id as site_id,site.name as site_name from entity_elective e,"
						+ "entity_student_info s,entity_site_info site where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' "
						+ " and s.isgraduated='0' and s.site_id=site.id"
						+ condition
						+ " ) group by site_id,site_name) a,"
						+ "(select site_id,site_name, count(id) as best_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site where "
						+ "e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' and s.isgraduated='0' "
						+ condition
						+ "and s.site_id=site.id and e.free_total_score_status='0' and to_number(e.total_score)>="
						+ sec[0]
						+ " ) group by site_id,site_name) b,"
						+ "(select site_id,site_name, count(id) as better_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' and s.isgraduated='0'"
						+ condition
						+ " and s.site_id=site.id and e.free_total_score_status='0' and to_number(e.total_score)>="
						+ sec[1]
						+ " and to_number(e.total_score)<"
						+ sec[0]
						+ " ) group by site_id,site_name) c,"
						+ "(select site_id,site_name, count(id) as good_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and e.free_total_score_status='0' and to_number(e.total_score)>="
						+ sec[2]
						+ " and to_number(e.total_score)<"
						+ sec[1]
						+ " ) group by site_id,site_name) d,"
						+ "(select site_id,site_name, count(id) as bad_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ " and e.free_total_score_status='0' and to_number(e.total_score)>="
						+ sec[3]
						+ " and to_number(e.total_score)<"
						+ sec[2]
						+ " ) group by site_id,site_name) e,"
						+ "(select site_id,site_name, count(id) as worse_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site"
						+ " where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "'  and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ " and e.free_total_score_status='0' and to_number(e.total_score)>="
						+ sec[4]
						+ " and to_number(e.total_score)<"
						+ sec[3]
						+ ") group by site_id,site_name) f where a.site_id=b.site_id(+) and "
						+ "a.site_id=c.site_id(+) and a.site_id=d.site_id(+) and a.site_id=e.site_id(+) and a.site_id=f.site_id(+)";

			} else if (scoretype.equals("exam_score")) {

				sql = "select a.site_id as site_id,a.site_name as site_name,nvl(a.total_num,0) as total_num,"
						+ "nvl(b.best_num,0) as best_num,nvl(c.better_num,0) as better_num,nvl(d.good_num,0) as good_num,"
						+ "nvl(e.bad_num,0) as bad_num,nvl(f.worse_num,0) as worse_num from  "
						+ "(select site_id,site_name, count(id) as total_num  from "
						+ "(select e.id as id,s.site_id as site_id,site.name as site_name from entity_elective e,"
						+ "entity_student_info s,entity_site_info site where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' "
						+ " and s.isgraduated='0' and s.site_id=site.id"
						+ condition
						+ " ) group by site_id,site_name) a,"
						+ "(select site_id,site_name, count(id) as best_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site where "
						+ "e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "'  and s.isgraduated='0' "
						+ condition
						+ "and s.site_id=site.id and e.free_total_score_status='0' and to_number(e.exam_score)>="
						+ sec[0]
						+ " ) group by site_id,site_name) b,"
						+ "(select site_id,site_name, count(id) as better_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' and s.isgraduated='0'"
						+ condition
						+ " and s.site_id=site.id and e.free_total_score_status='0' and to_number(e.exam_score)>="
						+ sec[1]
						+ " and to_number(e.exam_score)<"
						+ sec[0]
						+ " ) "
						+ "group by site_id,site_name) c,"
						+ "(select site_id,site_name, count(id) as good_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "and e.free_total_score_status='0' and to_number(e.exam_score)>="
						+ sec[2]
						+ " and to_number(e.exam_score)<"
						+ sec[1]
						+ " ) group by site_id,site_name) d,"
						+ "(select site_id,site_name, count(id) as bad_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and e.free_total_score_status='0' and to_number(e.exam_score)>="
						+ sec[3]
						+ " and to_number(e.exam_score)<"
						+ sec[2]
						+ " ) group by site_id,site_name) e,"
						+ "(select site_id,site_name, count(id) as worse_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site"
						+ " where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and e.free_total_score_status='0' and to_number(e.exam_score)>="
						+ sec[4]
						+ " and to_number(e.exam_score)<"
						+ sec[3]
						+ ") group by site_id,site_name) f where a.site_id=b.site_id(+) and "
						+ "a.site_id=c.site_id(+) and a.site_id=d.site_id(+) and a.site_id=e.site_id(+) and a.site_id=f.site_id(+)";

			} else if (scoretype.equals("total_expend_score")) {

				sql = "select a.site_id as site_id,a.site_name as site_name,nvl(a.total_num,0) as total_num,"
						+ "nvl(b.best_num,0) as best_num,nvl(c.better_num,0) as better_num,nvl(d.good_num,0) as good_num,"
						+ "nvl(e.bad_num,0) as bad_num,nvl(f.worse_num,0) as worse_num from  "
						+ "(select site_id,site_name, count(id) as total_num  from "
						+ "(select e.id as id,s.site_id as site_id,site.name as site_name from entity_elective e,"
						+ "entity_student_info s,entity_site_info site where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' "
						+ " and s.isgraduated='0' and s.site_id=site.id"
						+ condition
						+ " ) group by site_id,site_name) a,"
						+ "(select site_id,site_name, count(id) as best_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site where "
						+ "e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' and s.isgraduated='0' "
						+ condition
						+ "and s.site_id=site.id and e.free_total_score_status='0' and to_number(e.total_expend_score)>="
						+ sec[0]
						+ " ) group by site_id,site_name) b,"
						+ "(select site_id,site_name, count(id) as better_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' and s.isgraduated='0'"
						+ condition
						+ " and s.site_id=site.id and e.free_total_score_status='0' and to_number(e.total_expend_score)>="
						+ sec[1]
						+ " and to_number(e.total_expend_score)<"
						+ sec[0]
						+ " ) "
						+ "group by site_id,site_name) c,"
						+ "(select site_id,site_name, count(id) as good_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "and e.free_total_score_status='0' and to_number(e.total_expend_score)>="
						+ sec[2]
						+ " and to_number(e.total_expend_score)<"
						+ sec[1]
						+ " ) group by site_id,site_name) d,"
						+ "(select site_id,site_name, count(id) as bad_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site "
						+ "where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "' and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and e.free_total_score_status='0' and to_number(e.total_expend_score)>="
						+ sec[3]
						+ " and to_number(e.total_expend_score)<"
						+ sec[2]
						+ " ) group by site_id,site_name) e,"
						+ "(select site_id,site_name, count(id) as worse_num  from (select e.id as id,s.site_id as site_id,"
						+ "site.name as site_name from entity_elective e,entity_student_info s,entity_site_info site"
						+ " where e.student_id=s.id and e.status='1' and e.EXPEND_SCORE_STUDENT_STATUS = '"
						+ status
						+ "'  and s.isgraduated='0' and s.site_id=site.id "
						+ condition
						+ "  and e.free_total_score_status='0' and to_number(e.total_expend_score)>="
						+ sec[4]
						+ " and to_number(e.total_expend_score)<"
						+ sec[3]
						+ ") group by site_id,site_name) f where a.site_id=b.site_id(+) and "
						+ "a.site_id=c.site_id(+) and a.site_id=d.site_id(+) and a.site_id=e.site_id(+) and a.site_id=f.site_id(+)";

			}
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("site_id", rs.getString("site_id"));
				hash.put("site_name", rs.getString("site_name"));
				hash.put("total_num", rs.getString("total_num"));
				hash.put("best_num", rs.getString("best_num"));
				hash.put("better_num", rs.getString("better_num"));
				hash.put("good_num", rs.getString("good_num"));
				hash.put("bad_num", rs.getString("bad_num"));
				hash.put("worse_num", rs.getString("worse_num"));
				score.add(hash);
			}
		} catch (Exception e) {
			EntityLog
					.setError("OracleBasicScoreList@searchElectiveScoresStatics1(String open_course_id,String grade_id, String edutype_id, String major_id,String scoretype, String[] sec,String status)  Error! SQL = "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return score;
	}

	public List searchElectiveScoresStatics2(String open_course_id,
			String grade_id, String edutype_id, String site_id,
			String scoretype, String[] sec) throws PlatformException {
		dbpool db = new dbpool();
		List score = new ArrayList();
		MyResultSet rs = null;
		String sql = "";
		String teachClassId = "";
		OracleTeachClass teach = new OracleTeachClass();
		teachClassId = teach.getTeachClassId(open_course_id);

		try {

			StringBuffer conditionbuffer = new StringBuffer();

			String condition = "";

			if (teachClassId == null || teachClassId.equals("")
					|| teachClassId.equals("null")) {
			} else {

				conditionbuffer.append("  and e.teachclass_id= '").append(
						teachClassId).append("' ");
			}

			if (grade_id == null || grade_id.equals("")
					|| grade_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.grade_id = '").append(grade_id)
						.append("' ");
			}

			if (edutype_id == null || edutype_id.equals("")
					|| edutype_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.edutype_id = '").append(
						edutype_id).append("' ");
			}

			if (site_id == null || site_id.equals("") || site_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.site_id = '").append(site_id)
						.append("' ");
			}

			condition = conditionbuffer.toString();
			if (scoretype.equals("total_score")) {
				sql = "select a.major_id as major_id,a.major_name as major_name,nvl(a.total_num,0) as total_num,"
						+ "nvl(b.best_num,0) as best_num,nvl(c.better_num,0) as better_num,nvl(d.good_num,0) as good_num,"
						+ "nvl(e.bad_num,0) as bad_num,nvl(f.worse_num,0) as worse_num from  "
						+ "(select major_id,major_name, count(id) as total_num  from "
						+ "(select e.id as id,s.major_id as major_id,major.name as major_name from entity_elective e,"
						+ "entity_student_info s,entity_major_info major where e.student_id=s.id and e.status='1'"
						+ " and s.isgraduated='0' and s.major_id=major.id"
						+ condition
						+ " ) group by major_id,major_name) a,"
						+ "(select major_id,major_name, count(id) as best_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major where "
						+ "e.student_id=s.id and e.status='1' and s.isgraduated='0' "
						+ condition
						+ "and s.major_id=major.id and to_number(e.total_score)>="
						+ sec[0]
						+ " ) group by major_id,major_name) b,"
						+ "(select major_id,major_name, count(id) as better_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0'"
						+ condition
						+ " and s.major_id=major.id and to_number(e.total_score)>="
						+ sec[1]
						+ " and to_number(e.total_score)<"
						+ sec[0]
						+ " ) group by major_id,major_name) c,"
						+ "(select major_id,major_name, count(id) as good_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.major_id=major.id "
						+ condition
						+ "and to_number(e.total_score)>="
						+ sec[2]
						+ " and to_number(e.total_score)<"
						+ sec[1]
						+ " ) group by major_id,major_name) d,"
						+ "(select major_id,major_name, count(id) as bad_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.major_id=major.id "
						+ condition
						+ " and to_number(e.total_score)>="
						+ sec[3]
						+ " and to_number(e.total_score)<"
						+ sec[2]
						+ " ) group by major_id,major_name) e,"
						+ "(select major_id,major_name, count(id) as worse_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major"
						+ " where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.major_id=major.id "
						+ condition
						+ "  and to_number(e.total_score)>="
						+ sec[4]
						+ " and to_number(e.total_score)<"
						+ sec[3]
						+ " ) group by major_id,major_name) f where a.major_id=b.major_id(+) and "
						+ "a.major_id=c.major_id(+) and a.major_id=d.major_id(+) and a.major_id=e.major_id(+) and a.major_id=f.major_id(+)";

			} else if (scoretype.equals("exam_score")) {

				sql = "select a.major_id as major_id,a.major_name as major_name,nvl(a.total_num,0) as total_num,"
						+ "nvl(b.best_num,0) as best_num,nvl(c.better_num,0) as better_num,nvl(d.good_num,0) as good_num,"
						+ "nvl(e.bad_num,0) as bad_num,nvl(f.worse_num,0) as worse_num from  "
						+ "(select major_id,major_name, count(id) as total_num  from "
						+ "(select e.id as id,s.major_id as major_id,major.name as major_name from entity_elective e,"
						+ "entity_student_info s,entity_major_info major where e.student_id=s.id and e.status='1'"
						+ " and s.isgraduated='0' and s.major_id=major.id"
						+ condition
						+ " ) group by major_id,major_name) a,"
						+ "(select major_id,major_name, count(id) as best_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major where "
						+ "e.student_id=s.id and e.status='1' and s.isgraduated='0' "
						+ condition
						+ "and s.major_id=major.id and to_number(e.exam_score)>="
						+ sec[0]
						+ " ) group by major_id,major_name) b,"
						+ "(select major_id,major_name, count(id) as better_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0'"
						+ condition
						+ " and s.major_id=major.id and to_number(e.exam_score)>="
						+ sec[1]
						+ " and to_number(e.exam_score)<"
						+ sec[0]
						+ " ) group by major_id,major_name) c,"
						+ "(select major_id,major_name, count(id) as good_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.major_id=major.id "
						+ condition
						+ "and to_number(e.exam_score)>="
						+ sec[2]
						+ " and to_number(e.exam_score)<"
						+ sec[1]
						+ " ) group by major_id,major_name) d,"
						+ "(select major_id,major_name, count(id) as bad_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.major_id=major.id "
						+ condition
						+ " and to_number(e.exam_score)>="
						+ sec[3]
						+ " and to_number(e.exam_score)<"
						+ sec[2]
						+ " ) group by major_id,major_name) e,"
						+ "(select major_id,major_name, count(id) as worse_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major"
						+ " where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.major_id=major.id "
						+ condition
						+ "  and to_number(e.exam_score)>="
						+ sec[4]
						+ " and to_number(e.exam_score)<"
						+ sec[3]
						+ " ) group by major_id,major_name) f where a.major_id=b.major_id(+) and "
						+ "a.major_id=c.major_id(+) and a.major_id=d.major_id(+) and a.major_id=e.major_id(+) and a.major_id=f.major_id(+)";

			}
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("major_id", rs.getString("major_id"));
				hash.put("major_name", rs.getString("major_name"));
				hash.put("total_num", rs.getString("total_num"));
				hash.put("best_num", rs.getString("best_num"));
				hash.put("better_num", rs.getString("better_num"));
				hash.put("good_num", rs.getString("good_num"));
				hash.put("bad_num", rs.getString("bad_num"));
				hash.put("worse_num", rs.getString("worse_num"));
				score.add(hash);
			}
		} catch (Exception e) {
			EntityLog
					.setError("OracleBasicScoreList@searchElectiveScoresStatics2(String open_course_id,String grade_id, String edutype_id, String site_id,String scoretype, String[] sec)  Error! SQL = "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return score;
	}

	public List searchElectiveScoresStatics2(String open_course_id,
			String grade_id, String edutype_id, String site_id, String scoretype)
			throws PlatformException {
		dbpool db = new dbpool();
		List score = new ArrayList();
		MyResultSet rs = null;
		String sql = "";
		String teachClassId = "";
		OracleTeachClass teach = new OracleTeachClass();
		teachClassId = teach.getTeachClassId(open_course_id);

		try {

			StringBuffer conditionbuffer = new StringBuffer();

			String condition = "";

			if (teachClassId == null || teachClassId.equals("")
					|| teachClassId.equals("null")) {
			} else {

				conditionbuffer.append("  and e.teachclass_id= '").append(
						teachClassId).append("' ");
			}

			if (grade_id == null || grade_id.equals("")
					|| grade_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.grade_id = '").append(grade_id)
						.append("' ");
			}

			if (edutype_id == null || edutype_id.equals("")
					|| edutype_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.edutype_id = '").append(
						edutype_id).append("' ");
			}

			if (site_id == null || site_id.equals("") || site_id.equals("null")) {
			} else {
				conditionbuffer.append(" and s.site_id = '").append(site_id)
						.append("' ");
			}

			condition = conditionbuffer.toString();
			if (scoretype.equals("total_score")) {
				sql = "select a.major_id as major_id,a.major_name as major_name,nvl(a.total_num,0) as total_num,"
						+ "nvl(b.best_num,0) as best_num,nvl(c.better_num,0) as better_num,nvl(d.good_num,0) as good_num,"
						+ "nvl(e.bad_num,0) as bad_num,nvl(f.worse_num,0) as worse_num from  "
						+ "(select major_id,major_name, count(id) as total_num  from "
						+ "(select e.id as id,s.major_id as major_id,major.name as major_name from entity_elective e,"
						+ "entity_student_info s,entity_major_info major where e.student_id=s.id and e.status='1'"
						+ " and s.isgraduated='0' and s.major_id=major.id"
						+ condition
						+ " ) group by major_id,major_name) a,"
						+ "(select major_id,major_name, count(id) as best_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major where "
						+ "e.student_id=s.id and e.status='1' and s.isgraduated='0' "
						+ condition
						+ "and s.major_id=major.id and e.total_score>=90 ) group by major_id,major_name) b,"
						+ "(select major_id,major_name, count(id) as better_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0'"
						+ condition
						+ " and s.major_id=major.id and e.total_score>=80 and e.total_score<=89 ) group by major_id,major_name) c,"
						+ "(select major_id,major_name, count(id) as good_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.major_id=major.id "
						+ condition
						+ "and e.total_score>=70 and e.total_score<=79 ) group by major_id,major_name) d,"
						+ "(select major_id,major_name, count(id) as bad_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.major_id=major.id "
						+ condition
						+ " and e.total_score>=60 and e.total_score<=69 ) group by major_id,major_name) e,"
						+ "(select major_id,major_name, count(id) as worse_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major"
						+ " where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.major_id=major.id "
						+ condition
						+ "  and e.total_score<60 ) group by major_id,major_name) f where a.major_id=b.major_id(+) and "
						+ "a.major_id=c.major_id(+) and a.major_id=d.major_id(+) and a.major_id=e.major_id(+) and a.major_id=f.major_id(+)";

			} else if (scoretype.equals("exam_score")) {

				sql = "select a.major_id as major_id,a.major_name as major_name,nvl(a.total_num,0) as total_num,"
						+ "nvl(b.best_num,0) as best_num,nvl(c.better_num,0) as better_num,nvl(d.good_num,0) as good_num,"
						+ "nvl(e.bad_num,0) as bad_num,nvl(f.worse_num,0) as worse_num from  "
						+ "(select major_id,major_name, count(id) as total_num  from "
						+ "(select e.id as id,s.major_id as major_id,major.name as major_name from entity_elective e,"
						+ "entity_student_info s,entity_major_info major where e.student_id=s.id and e.status='1'"
						+ " and s.isgraduated='0' and s.major_id=major.id"
						+ condition
						+ " ) group by major_id,major_name) a,"
						+ "(select major_id,major_name, count(id) as best_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major where "
						+ "e.student_id=s.id and e.status='1' and s.isgraduated='0' "
						+ condition
						+ "and s.major_id=major.id and e.exam_score>=90 ) group by major_id,major_name) b,"
						+ "(select major_id,major_name, count(id) as better_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0'"
						+ condition
						+ " and s.major_id=major.id and e.exam_score>=80 and e.exam_score<=89 ) group by major_id,major_name) c,"
						+ "(select major_id,major_name, count(id) as good_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.major_id=major.id "
						+ condition
						+ "and e.exam_score>=70 and e.exam_score<=79 ) group by major_id,major_name) d,"
						+ "(select major_id,major_name, count(id) as bad_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major "
						+ "where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.major_id=major.id "
						+ condition
						+ " and e.exam_score>=60 and e.exam_score<=69 ) group by major_id,major_name) e,"
						+ "(select major_id,major_name, count(id) as worse_num  from (select e.id as id,s.major_id as major_id,"
						+ "major.name as major_name from entity_elective e,entity_student_info s,entity_major_info major"
						+ " where e.student_id=s.id and e.status='1' and s.isgraduated='0' and s.major_id=major.id "
						+ condition
						+ "  and e.exam_score<60 ) group by major_id,major_name) f where a.major_id=b.major_id(+) and "
						+ "a.major_id=c.major_id(+) and a.major_id=d.major_id(+) and a.major_id=e.major_id(+) and a.major_id=f.major_id(+)";

			}
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("major_id", rs.getString("major_id"));
				hash.put("major_name", rs.getString("major_name"));
				hash.put("total_num", rs.getString("total_num"));
				hash.put("best_num", rs.getString("best_num"));
				hash.put("better_num", rs.getString("better_num"));
				hash.put("good_num", rs.getString("good_num"));
				hash.put("bad_num", rs.getString("bad_num"));
				hash.put("worse_num", rs.getString("worse_num"));
				score.add(hash);
			}
		} catch (Exception e) {
			EntityLog
					.setError("OracleBasicScoreList@searchElectiveScoresStatics2(String open_course_id,String grade_id, String edutype_id, String site_id, String scoretype)  Error! SQL = "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return score;
	}

	public List searchElectiveScoresStaticsExamScore(Page page,
			List searchproperties, List orderproperties)
			throws PlatformException {
		dbpool db = new dbpool();

		String t_class_where = "";
		String stu_where = "";
		String cour_where = "";
		if (searchproperties != null) {
			for (int ii = 0; ii < searchproperties.size(); ii++) {
				SearchProperty prop = (SearchProperty) searchproperties.get(ii);
				String field = prop.getField();
				String val = prop.getValue();
				if (field != null && field.equals("site_id"))
					stu_where += " and site_id='" + val + "'";
				if (field != null && field.equals("major_id"))
					stu_where += " and major_id='" + val + "'";
				if (field != null && field.equals("edutype_id"))
					stu_where += " and edutype_id='" + val + "'";
				if (field != null && field.equals("grade_id"))
					stu_where += " and grade_id='" + val + "'";

				if (field != null && field.equals("course_id"))
					cour_where += " and course_id='" + val + "'";
				if (field != null && field.equals("semester_id"))
					cour_where += " and semester_id='" + val + "'";

				if (field != null && field.equals("open_course_id"))
					t_class_where += " and open_course_id='" + val + "'";
			}
		}
		String SQL = "select distinct id,student_name,student_id,reg_no,major_id,site_id,site_name,edutype_id,grade_id,open_course_id,course_id,semester_id,status,score_status,total_score,exam_score from (select e.id as id,s.name as student_name,s.id as student_id,s.reg_no as reg_no,s.major_id,s.site_id,site.name as site_name,s.edutype_id,s.grade_id,edutype.name as edutype_name,major.name as major_name,t.open_course_id,ca.course_id,ci.name as course_name,ci.credit as course_credit,ci.course_time as course_time,si.id as semester_id,si.name as semester_name,e.status,e.score_status,e.total_score,e.total_scorelevel,e.total_score_viewlevel,e.total_scoredes,e.total_score_zerodes,e.total_score_zerocause,e.usual_score,e.usual_scorelevel,e.usual_score_viewlevel,e.usual_scoredes,e.usual_score_zerodes,e.usual_score_zerocause,e.exam_score,e.exam_scorelevel,e.exam_score_viewlevel,e.exam_scoredes,e.exam_score_zerodes,e.exam_score_zerocause,e.expend_score,e.expend_scorelevel,e.expend_score_viewlevel,e.expend_scoredes,e.expend_score_zerodes,e.expend_score_zerocause,e.renew_score,e.renew_scorelevel,e.renew_score_viewlevel,e.renew_scoredes,e.renew_score_zerodes,e.renew_score_zerocause,e.expend_score_status as expend_score_status,e.expend_score_student_status as expend_score_student_status from entity_elective e,(select * from entity_student_info where 1=1 "
				+ stu_where
				+ ") s,(select * from entity_teach_class where 1=1 "
				+ t_class_where
				+ ") t,(select * from entity_course_active where 1=1 "
				+ cour_where
				+ ") ca,entity_semester_info si,entity_course_info ci,entity_site_info site,entity_edu_type edutype,entity_major_info major where s.id=e.student_id and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.semester_id=si.id and ca.course_id=ci.id and site.id=s.site_id and s.isgraduated='0' and s.status='0' and s.edutype_id=edutype.id and s.major_id=major.id"
				+ Conditions.convertToCondition(null, orderproperties) + ")";
		String sql = SQL
				+ Conditions.convertToCondition(searchproperties,
						orderproperties);
		MyResultSet rs = null;
		ArrayList scoreList = new ArrayList();
		String site_name = "";
		float exam_score = 0f;
		int countA = 0;// big or equal 90
		int countB = 0;// between 80-89
		int countC = 0;// between 70-79
		int countD = 0;// between 60-69
		int countE = 0;// less 59.0
		int countF = 0;// equal 0
		int countTotal = 0;// all num
		try {
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {

				site_name = rs.getString("site_name");
				exam_score = rs.getFloat("exam_score");

				if (exam_score >= 90)
					countA++;
				if (exam_score >= 80 && exam_score <= 89)
					countB++;
				if (exam_score >= 70 && exam_score <= 79)
					countC++;
				if (exam_score >= 60 && exam_score <= 69)
					countD++;
				if (exam_score > 0 && exam_score <= 59)
					countE++;
				if (exam_score == 0)
					countF++;

				countTotal++;

				HashMap score = new HashMap();
				score.put("siteName", site_name);

				score.put("countTotal", Integer.toString(countTotal));
				score.put("countA", Integer.toString(countA));
				score.put("countB", Integer.toString(countB));
				score.put("countC", Integer.toString(countC));
				score.put("countD", Integer.toString(countD));
				score.put("countE", Integer.toString(countE));
				score.put("countF", Integer.toString(countF));
				scoreList.add(score);

			}
		} catch (Exception e) {
			EntityLog
					.setError("OracleBasicScoreList@searchElectiveScoresStaticsExamScore(Page page,List searchproperties, List orderproperties)  Error! SQL = "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return scoreList;
	}

	public int searchElectiveScoresNum(List searchproperties,
			List orderproperties) throws PlatformException {
		dbpool db = new dbpool();

		String t_class_where = "";
		String stu_where = "";
		String cour_where = "";
		if (searchproperties != null) {
			for (int ii = 0; ii < searchproperties.size(); ii++) {
				SearchProperty prop = (SearchProperty) searchproperties.get(ii);
				String field = prop.getField();
				String val = prop.getValue();
				if (field != null && field.equals("site_id"))
					stu_where += " and site_id='" + val + "'";
				if (field != null && field.equals("major_id"))
					stu_where += " and major_id='" + val + "'";
				if (field != null && field.equals("edutype_id"))
					stu_where += " and edutype_id='" + val + "'";
				if (field != null && field.equals("grade_id"))
					stu_where += " and grade_id='" + val + "'";

				if (field != null && field.equals("course_id"))
					cour_where += " and course_id='" + val + "'";
				if (field != null && field.equals("semester_id"))
					cour_where += " and semester_id='" + val + "'";

				if (field != null && field.equals("open_course_id"))
					t_class_where += " and open_course_id='" + val + "'";
			}
		}

		String SQL = "select distinct id,student_name,student_id,reg_no,major_id,site_id,site_name,edutype_id,grade_id,open_course_id,course_id,course_name,semester_id,semester_name,status,score_status,total_score,total_scorelevel,total_score_viewlevel,total_scoredes,total_score_zerodes,total_score_zerocause,usual_score,usual_scorelevel,usual_score_viewlevel,usual_scoredes,usual_score_zerodes,usual_score_zerocause,exam_score,exam_scorelevel,exam_score_viewlevel,exam_scoredes,exam_score_zerodes,exam_score_zerocause,expend_score,expend_scorelevel,expend_score_viewlevel,expend_scoredes,expend_score_zerodes,expend_score_zerocause,renew_score,renew_scorelevel,renew_score_viewlevel,renew_scoredes,renew_score_zerodes,renew_score_zerocause,expend_score_student_status,expend_score_status from (select e.id as id,s.name as student_name,s.id as student_id,s.reg_no as reg_no,s.major_id,s.site_id,site.name as site_name,s.edutype_id,s.grade_id,t.open_course_id,ca.course_id,ci.name as course_name,si.id as semester_id,si.name as semester_name,e.status,e.score_status,e.total_score,e.total_scorelevel,e.total_score_viewlevel,e.total_scoredes,e.total_score_zerodes,e.total_score_zerocause,e.usual_score,e.usual_scorelevel,e.usual_score_viewlevel,e.usual_scoredes,e.usual_score_zerodes,e.usual_score_zerocause,e.exam_score,e.exam_scorelevel,e.exam_score_viewlevel,e.exam_scoredes,e.exam_score_zerodes,e.exam_score_zerocause,e.expend_score,e.expend_scorelevel,e.expend_score_viewlevel,e.expend_scoredes,e.expend_score_zerodes,e.expend_score_zerocause,e.renew_score,e.renew_scorelevel,e.renew_score_viewlevel,e.renew_scoredes,e.renew_score_zerodes,e.renew_score_zerocause,e.expend_score_student_status as expend_score_student_status,e.expend_score_status from entity_elective e,(select * from entity_student_info where 1=1 "
				+ stu_where
				+ ") s,(select * from entity_teach_class where 1=1 "
				+ t_class_where
				+ ") t,(select * from entity_course_active where 1=1 "
				+ cour_where
				+ ") ca,entity_semester_info si,entity_course_info ci,entity_site_info site where s.id=e.student_id and e.teachclass_id=t.id and t.open_course_id=ca.id and ca.semester_id=si.id and ca.course_id=ci.id and site.id=s.site_id and s.isgraduated='0' and s.status='0')";

		String sql = SQL
				+ Conditions.convertToCondition(searchproperties,
						orderproperties);

		return db.countselect(sql);

	}

	public List searchMaxElectiveScores(String scoretype,
			List searchproperties, List orderproperties)
			throws PlatformException {
		dbpool db = new dbpool();
		String scoreName = "";
		if (scoretype.equals(ElectiveScoreType.TOTAL))
			scoreName = "total_score";
		else if (scoretype.equals(ElectiveScoreType.USUAL))
			scoreName = "usual_score";
		else if (scoretype.equals(ElectiveScoreType.EXAM))
			scoreName = "exam_score";
		else if (scoretype.equals(ElectiveScoreType.EXPERIMENT))
			scoreName = "experiment_score";
		else if (scoretype.equals(ElectiveScoreType.EXPEND))
			scoreName = "expend_score";
		else if (scoretype.equals(ElectiveScoreType.RENEW))
			scoreName = "renew_score";
		String SQL = "select open_course_id,course_id,course_name,maxscore,semester_id from (select a.id as open_course_id,a.course_id,b.name as course_name,c.maxscore,a.semester_id from entity_course_active a,entity_course_info b,(select max(nvl("
				+ scoreName
				+ ",0)) as maxscore,teachclass_id from entity_elective group by teachclass_id) c,entity_teach_class d where a.id=d.open_course_id and b.id=a.course_id and c.teachclass_id=d.id order by a.course_id) ";
		String sql = SQL
				+ Conditions.convertToCondition(searchproperties,
						orderproperties);
		MyResultSet rs = null;
		ArrayList electiveScoreList = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleElectiveScore electivescore = new OracleElectiveScore();
				OracleElective elective = new OracleElective();
				List scoreList = new ArrayList();
				OracleTeachClass teachclass = new OracleTeachClass();
				OracleSemester semester = new OracleSemester();
				OracleOpenCourse opencourse = new OracleOpenCourse();
				OracleCourse course = new OracleCourse();
				OracleStudent student = new OracleStudent();
				StudentEduInfo eduinfo = new StudentEduInfo();

				opencourse.setId(rs.getString("open_course_id"));
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
//				opencourse.setCourse(course);
				semester.setId(rs.getString("semester_id"));
//				opencourse.setSemester(semester);
				teachclass.setOpenCourse(opencourse);
				elective.setTeachClass(teachclass);
				electivescore.setElective(elective);

				if (scoretype.equals(ElectiveScoreType.TOTAL)) {
					ScoreDef score_total = new ScoreDef();
					score_total.setTitle(ElectiveScoreType
							.typeShow(ElectiveScoreType.TOTAL));
					score_total.setType(ElectiveScoreType.TOTAL);
					score_total.setScore(rs.getFloat("maxscore"));
					scoreList.add(score_total);
				}

				if (scoretype.equals(ElectiveScoreType.USUAL)) {
					ScoreDef score_usual = new ScoreDef();
					score_usual.setTitle(ElectiveScoreType
							.typeShow(ElectiveScoreType.USUAL));
					score_usual.setType(ElectiveScoreType.USUAL);
					score_usual.setScore(rs.getFloat("maxscore"));
					scoreList.add(score_usual);
				}

				if (scoretype.equals(ElectiveScoreType.EXAM)) {
					ScoreDef score_exam = new ScoreDef();
					score_exam.setTitle(ElectiveScoreType
							.typeShow(ElectiveScoreType.EXAM));
					score_exam.setType(ElectiveScoreType.EXAM);
					score_exam.setScore(rs.getFloat("maxscore"));
					scoreList.add(score_exam);
				}
				if (scoretype.equals(ElectiveScoreType.EXPERIMENT)) {
					ScoreDef score_experiment = new ScoreDef();
					score_experiment.setTitle(ElectiveScoreType
							.typeShow(ElectiveScoreType.EXPERIMENT));
					score_experiment.setType(ElectiveScoreType.EXPERIMENT);
					score_experiment.setScore(rs.getFloat("maxscore"));
					scoreList.add(score_experiment);
				}

				if (scoretype.equals(ElectiveScoreType.EXPEND)) {
					ScoreDef score_expend = new ScoreDef();
					score_expend.setTitle(ElectiveScoreType
							.typeShow(ElectiveScoreType.EXPEND));
					score_expend.setType(ElectiveScoreType.EXPEND);
					score_expend.setScore(rs.getFloat("maxscore"));
					scoreList.add(score_expend);
				}

				if (scoretype.equals(ElectiveScoreType.RENEW)) {
					ScoreDef score_renew = new ScoreDef();
					score_renew.setTitle(ElectiveScoreType
							.typeShow(ElectiveScoreType.RENEW));
					score_renew.setType(ElectiveScoreType.RENEW);
					score_renew.setScore(rs.getFloat("maxscore"));
					scoreList.add(score_renew);
				}
				electivescore.setScoreList(scoreList);

				electiveScoreList.add(electivescore);
			}
		} catch (Exception e) {
			EntityLog
					.setError("OracleBasicScoreList@searchMaxElectiveScores(String scoretype,List searchproperties, List orderproperties) Error! SQL = "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return electiveScoreList;
	}

	public int searchMaxElectiveScoresNum(String scoretype,
			List searchproperties, List orderproperties)
			throws PlatformException {
		dbpool db = new dbpool();
		String scoreName = "";
		if (scoretype.equals(ElectiveScoreType.TOTAL))
			scoreName = "total_score";
		else if (scoretype.equals(ElectiveScoreType.USUAL))
			scoreName = "usual_score";
		else if (scoretype.equals(ElectiveScoreType.EXAM))
			scoreName = "exam_score";
		else if (scoretype.equals(ElectiveScoreType.EXPERIMENT))
			scoreName = "experiment_score";
		else if (scoretype.equals(ElectiveScoreType.EXPEND))
			scoreName = "expend_score";
		else if (scoretype.equals(ElectiveScoreType.RENEW))
			scoreName = "renew_score";
		String SQL = "select open_course_id,course_id,course_name,maxscore,semester_id from (select a.id as open_course_id,a.course_id,b.name as course_name,c.maxscore,a.semester_id from entity_course_active a,entity_course_info b,(select max(nvl("
				+ scoreName
				+ ",0)) as maxscore,teachclass_id from entity_elective group by teachclass_id) c,entity_teach_class d where a.id=d.open_course_id and b.id=a.course_id and c.teachclass_id=d.id order by a.course_id) ";
		String sql = SQL
				+ Conditions.convertToCondition(searchproperties,
						orderproperties);
		return db.countselect(sql);
	}

	public List getStudentScoreReport(String student_id, String major_id,
			String edutype_id) throws PlatformException {
		dbpool db = new dbpool();
		List report = new ArrayList();

		MyResultSet rs = null;
		String sql = "select course_name,semester_name, total_score  from (select e.id as id, e.total_score as total_score,s.name as stu_name,course.name as course_name,semester.name as semester_name from entity_elective e,entity_student_info s,entity_course_info course,entity_semester_info semester,entity_course_active opencourse,entity_teach_class teachclass  where e.student_id=s.id and e.status='1' and e.total_score_status='1'  and e.teachclass_id=teachclass.id and teachclass.open_course_id=opencourse.id and opencourse.course_id=course.id and opencourse.semester_id=semester.id and s.major_id='"
				+ major_id
				+ "'  and s.edutype_id='"
				+ edutype_id
				+ "' and s.id='" + student_id + "' )";
		rs = db.executeQuery(sql);

		try {
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("course_name", rs.getString("course_name"));
				hash.put("semester_name", rs.getString("semester_name"));
				hash.put("total_score", rs.getString("total_score"));
				report.add(hash);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return report;
	}

	public List getStudentScoreReport(String student_id)
			throws PlatformException {
		dbpool db = new dbpool();
		List report = new ArrayList();

		MyResultSet rs = null;
		String sql = "select course_name,semester_name, total_score  from "
				+ "(select e.total_score as total_score,s.name as stu_name,course.name as course_name,"
				+ "semester.name as semester_name from entity_elective e,entity_student_info s,entity_course_info course,"
				+ "entity_semester_info semester,entity_course_active opencourse,entity_teach_class teachclass  "
				+ "where e.student_id=s.id and e.status='1'  and e.teachclass_id=teachclass.id "
				+ "and teachclass.open_course_id=opencourse.id and opencourse.course_id=course.id and opencourse.semester_id=semester.id and  "
				+ "s.id='" + student_id + "' )";
		rs = db.executeQuery(sql);

		try {
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("course_name", rs.getString("course_name"));
				hash.put("semester_name", rs.getString("semester_name"));
				hash.put("total_score", rs.getString("total_score"));
				report.add(hash);
			}
		} catch (Exception e) {
			EntityLog
					.setError("OracleBasicScoreList@getStudentScoreReport(String student_id) Error! SQL = "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return report;
	}

	public List getStudentScoreReport1(String student_id)
			throws PlatformException {
		dbpool db = new dbpool();
		List report = new ArrayList();

		MyResultSet rs = null;
		String sql = "select course_name,semester_name, total_score,usual_score,experiment_score,test_score,exam_score,expend_score,total_expend_score,total_score_status,exam_score_status,expend_score_student_status  from (select e.total_score as total_score,e.usual_score as usual_score,e.experiment_score as experiment_score,e.exam_score as exam_score,e.test_score as test_score,e.expend_score as expend_score,e.total_expend_score as total_expend_score,e.total_score_status as total_score_status,e.exam_score_status as exam_score_status, s.name as stu_name,course.name as course_name,semester.name as semester_name, e.expend_score_student_status as expend_score_student_status from entity_elective e,entity_student_info s,entity_course_info course,entity_semester_info semester,entity_course_active opencourse,entity_teach_class teachclass  where e.student_id=s.id and e.status='1'  and e.teachclass_id=teachclass.id and teachclass.open_course_id=opencourse.id and opencourse.course_id=course.id and opencourse.semester_id=semester.id and  s.id='"
				+ student_id + "' ) order by semester_name desc";
		rs = db.executeQuery(sql);

		try {
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("course_name", rs.getString("course_name"));
				hash.put("semester_name", rs.getString("semester_name"));
				hash.put("total_score", rs.getString("total_score"));
				hash.put("usual_score", rs.getString("usual_score"));
				hash.put("experiment_score", rs.getString("experiment_score"));
				hash.put("test_score", rs.getString("test_score"));
				hash.put("exam_score", rs.getString("exam_score"));
				hash.put("expend_score", rs.getString("expend_score"));
				hash.put("total_expend_score", rs
						.getString("total_expend_score"));
				hash.put("total_score_status", rs
						.getString("total_score_status"));
				hash
						.put("exam_score_status", rs
								.getString("exam_score_status"));
				hash.put("expend_score_student_status", rs
						.getString("expend_score_student_status"));
				report.add(hash);
			}

		} catch (Exception e) {
			EntityLog
					.setError("OracleBasicScoreList@getStudentScoreReport1(String student_id) Error! SQL = "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return report;
	}

	public List<ScoreModify> getScoreModifyRecords(Page page,
			List<SearchProperty> searchList, List<OrderProperty> orderList)
			throws PlatformException {
		List<ScoreModify> smList = new ArrayList<ScoreModify>();
		
		dbpool db = new dbpool();
		String sql = "select esm.id," +
			"       esi.id as stu_id," + 
			"       esi.reg_no," + 
			"       esi.name," + 
			"       es.name as semester_name," + 
			"       eci.name as course_name," + 
			"       esm.which_score," + 
			"       esm.old_score," + 
			"       esm.modify_score," + 
			"       esm.teacher_name," + 
			"       esm.status," + 
			"       to_char(esm.modify_date, 'yyyy-mm-dd hh24:mi:ss') as modify_date," + 
			"       esm.elective_id," + 
			"       esm.reason" + 
			"  from entity_score_modifyrecord esm," + 
			"       entity_elective           ee," + 
			"       entity_teach_class        etc," + 
			"       entity_student_info       esi," + 
			"       entity_course_info        eci," + 
			"       entity_semester_info      es," + 
			"       entity_course_active      eca" + 
			" where esm.student_id = esi.id" + 
			"   and esm.elective_id = ee.id" + 
			"   and ee.student_id = esi.id" + 
			"   and ee.teachclass_id = etc.id" + 
			"   and etc.open_course_id = eca.id" + 
			"   and eca.course_id = eci.id" + 
			"   and eca.semester_id = es.id";

		sql += Conditions.convertToAndCondition(searchList);
		sql = "select id,stu_id,reg_no,name,semester_name,course_name,which_score,old_score,modify_score,teacher_name,status,modify_date,elective_id,reason"
			+ " from (" + sql + ")" + Conditions.convertToCondition(null, orderList);
		
		MyResultSet rs = null;
		if(page != null) {
			int pageSize = page.getPageSize();
			int pageInt = page.getPageInt();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		
		try {
			while(rs != null && rs.next()) {
				ScoreModify sm = new ScoreModify();
				sm.setId(rs.getString("id"));
				sm.setStudent_id(rs.getString("stu_id"));
				sm.setElective_id(rs.getString("elective_id"));
				sm.setModifyReason(rs.getString("reason"));
				sm.setModifyDate(rs.getString("modify_date"));
				sm.setOld_score(rs.getFloat("old_score"));
				sm.setNew_score(rs.getFloat("modify_score"));
				sm.setTeacher_name(rs.getString("teacher_name"));
				
				smList.add(sm);
			}
		} catch (SQLException e) {
			throw new PlatformException(e.getMessage());
		} finally {
			db.close(rs);
		}
		
		return smList;
	}

	public int getScoreModifyRecordsNum(List<SearchProperty> searchList)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select esm.id," +
			"       esi.id as stu_id," + 
			"       esi.reg_no," + 
			"       esi.name," + 
			"       es.name as semester_name," + 
			"       eci.name as course_name," + 
			"       esm.which_score," + 
			"       esm.old_score," + 
			"       esm.modify_score," + 
			"       esm.teacher_name," + 
			"       esm.status," + 
			"       to_char(esm.modify_date, 'yyyy-mm-dd hh24:mi:ss') as modify_date," + 
			"       esm.elective_id," + 
			"       esm.reason" + 
			"  from entity_score_modifyrecord esm," + 
			"       entity_elective           ee," + 
			"       entity_teach_class        etc," + 
			"       entity_student_info       esi," + 
			"       entity_course_info        eci," + 
			"       entity_semester_info      es," + 
			"       entity_course_active      eca" + 
			" where esm.student_id = esi.id" + 
			"   and esm.elective_id = ee.id" + 
			"   and ee.student_id = esi.id" + 
			"   and ee.teachclass_id = etc.id" + 
			"   and etc.open_course_id = eca.id" + 
			"   and eca.course_id = eci.id" + 
			"   and eca.semester_id = es.id";

		sql += Conditions.convertToAndCondition(searchList);
		sql = "select id,stu_id,reg_no,name,semester_name,course_name,which_score,old_score,modify_score,teacher_name,status,modify_date,elective_id,reason"
			+ " from (" + sql + ")";
		return db.countselect(sql);
	}
}