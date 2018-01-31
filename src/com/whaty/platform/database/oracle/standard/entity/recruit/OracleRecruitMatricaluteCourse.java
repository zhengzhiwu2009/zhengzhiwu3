package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.recruit.RecruitMatricaluteCourse;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleRecruitMatricaluteCourse extends RecruitMatricaluteCourse {
	/** Creates a new instance of OracleRecruitMatricaluteCourse */
	public OracleRecruitMatricaluteCourse() {
	}

	public OracleRecruitMatricaluteCourse(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,mc_id,course_id,score from recruit_matriculate_course where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleRecruitMatricaluteCondition matricaluteCondition = new OracleRecruitMatricaluteCondition();
				matricaluteCondition.setId(rs.getString("mc_id"));
				this.setMatricaluteCondition(matricaluteCondition);
				OracleRecruitTestCourse testCourse = new OracleRecruitTestCourse();
				testCourse.setId(rs.getString("course_id"));
				this.setTestCourse(testCourse);
				this.setScore(rs.getString("score"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog
					.setError("OracleRecruitMatricaluteCourse(String aid) error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into recruit_matriculate_course (id,mc_id,course_id,score) values(to_char(S_MATRICULATE_COURSE_ID.nextval),'"
				+ this.getMatricaluteCondition().getId()
				+ "','"
				+ this.getTestCourse().getId() + "','" + this.getScore() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitMatricaluteCourse.add() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_matriculate_course set mc_id='"
				+ this.getMatricaluteCondition().getId() + "',course_id='"
				+ this.getTestCourse().getId() + "',score='" + this.getScore()
				+ "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitMatricaluteCourse.update() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_matriculate_course where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitMatricaluteCourse.delete() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getMatricaluteCourse(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List recruitmat = null;

		String sql_condition = "select course_id,course_name,major_id from(select a.id as course_id,a.name as course_name,b.major_id as major_id  from recruit_course_info a,recruit_majorsort_relation b,recruit_sortcourse_relation c where a.id=c.course_id and b.sort_id=c.sort_id )";
		sql_condition = sql_condition
				+ Conditions.convertToCondition(searchProperty, null);

		// System.out.println("sql_condition:"+sql_condition);

		try {

			recruitmat = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql_condition, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql_condition);
			}

			while (rs != null && rs.next()) {
				RecruitMatricaluteCourse recruitCourse = new OracleRecruitMatricaluteCourse();

				recruitCourse.setId(rs.getString("course_id"));
				recruitCourse.setScore(rs.getString("course_name"));

				recruitmat.add(recruitCourse);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleRecruitMatricaluteCondition error"
					+ sql_condition);
		} finally {
			db.close(rs);
			db = null;
		}
		return recruitmat;
	}

	public int deleteByMajor(String mc_id) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_matriculate_course where mc_id='" + mc_id
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitMatricaluteCourse.deleteByMajor(String mc_id) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int add(String mc_id, String course_id, String score)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into recruit_matriculate_course (id,mc_id,course_id,score) values(to_char(S_MATRICULATE_COURSE_ID.nextval),'"
				+ mc_id + "','" + course_id + "','" + score + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitMatricaluteCourse.add(String mc_id,String course_id,String score) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateStudentStatus(String batch_id, String major_id,
			String edutype_id) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info  set status='2' where id in (select s.id as sid  from recruit_student_info s,recruit_matriculate_condition m where s.id in (select t.recruitstudent_id as mid  from recruit_test_desk t,recruit_test_course c,recruit_matriculate_course n where t.testcourse_id=c.id and n.course_id=c.course_id and c.testsequence_id='"
				+ batch_id
				+ "' and n.score>t.score) and  s.major_id=m.major_id and s.edutype_id=m.edutype_id and s.batch_id=m.batch_id and s.score>m.score and s.batch_id='"
				+ batch_id
				+ "' and s.major_id='"
				+ major_id
				+ "' and s.edutype_id='" + edutype_id + "')";

		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitMatricaluteCourse.updateStudentStatus(String batch_id,String major_id,String edutype_id) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
