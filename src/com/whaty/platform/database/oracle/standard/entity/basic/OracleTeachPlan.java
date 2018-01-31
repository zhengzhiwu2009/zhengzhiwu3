package com.whaty.platform.database.oracle.standard.entity.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.TeachPlan;
import com.whaty.platform.entity.basic.TeachPlanCourseType;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleTeachPlan extends TeachPlan {

	public OracleTeachPlan() {

	}

	public OracleTeachPlan(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,title,major_id,grade_id,edutype_id from entity_teachplan_info where id = '"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				OracleMajor major = new OracleMajor(rs.getString("major_id"));
				this.setMajor(major);
				OracleGrade grade = new OracleGrade(rs.getString("grade_id"));
				this.setGrade(grade);
				OracleEduType eduType = new OracleEduType(rs
						.getString("edutype_id"));
				this.setEduType(eduType);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleTeachPlan(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public OracleTeachPlan(String majorId, String eduTypeId, String gradeId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,title,major_id,grade_id,edutype_id from entity_teachplan_info where major_id = '"
				+ majorId
				+ "' and edutype_id='"
				+ eduTypeId
				+ "' and grade_id='" + gradeId + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				OracleMajor major = new OracleMajor(rs.getString("major_id"));
				this.setMajor(major);
				OracleGrade grade = new OracleGrade(rs.getString("grade_id"));
				this.setGrade(grade);
				OracleEduType eduType = new OracleEduType(rs
						.getString("edutype_id"));
				this.setEduType(eduType);
			}
		} catch (java.sql.SQLException e) {
			EntityLog
					.setError("OracleTeachPlan(String majorId, String eduTypeId, String gradeId) error:"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		if (isExist() > 0)
			return 2;
		String sql = "";
		sql = "insert into entity_teachplan_info (id,title,major_id,grade_id,edutype_id) values(to_char(s_teachplan_info_id.nextval),'"
				+ this.getTitle()
				+ "','"
				+ this.getMajor().getId()
				+ "','"
				+ this.getGrade().getId()
				+ "','"
				+ this.getEduType().getId()
				+ "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTeachPlan.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		if (isExist() > 0)
			return 2;
		String sql = "";
		sql = "update entity_teachplan_info set title='" + this.getTitle()
				+ "',major_id='" + this.getMajor().getId() + "',grade_id='"
				+ this.getGrade().getId() + "',edutype_id='"
				+ this.getEduType().getId() + "' where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTeachPlan.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		if (isHasCourses() > 0)
			throw new PlatformException("The teachplan has courses");
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_teachplan_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTeachPlan.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getCourses(Page page, List searchProperty, List orderProperty)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		TeachPlanCourseType type = new TeachPlanCourseType();
		ArrayList courseList = new ArrayList();
		String sql = "select id,plancourse_id,isdegree,name,credit,credit1,course_type,course_type1,semester,course_time,course_time1,major_id,edutype_id,grade_id,m_name,e_name,g_name,type,teachplan_id from"
				+ "(select t.id as plancourse_id,t.isdegree,c.id,c.name,c.credit,t.credit as credit1,t.type as course_type1,t.semester,c.course_type,c.course_time,t.coursetime as course_time1,i.id as teachplan_id,c.major_id,i.edutype_id,i.grade_id,m.name as m_name,e.name as e_name,g.name as g_name,t.type as type "
				+ "from entity_teachplan_info i,entity_teachplan_course t,entity_course_info c,entity_major_info m,entity_edu_type e,entity_grade_info g "
				+ "where i.id = t.teachplan_id and c.id = t.course_id and i.major_id = m.id and i.edutype_id = e.id and i.grade_id = g.id)";
		sql += Conditions.convertToCondition(searchProperty, orderProperty);
		
		try {
			if (page != null)
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			else
				rs = db.executeQuery(sql);
			
			while (rs != null && rs.next()) {
				OracleTeachPlanCourse teachplancourse = new OracleTeachPlanCourse();
				OracleTeachPlan teachplan = new OracleTeachPlan();
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("name"));
				course.setCourse_time(rs.getString("course_time"));
				course.setCredit(rs.getString("credit"));
				course.setCourse_type(rs.getString("course_type"));
				teachplan.setId(rs.getString("teachplan_id"));
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				major.setName(rs.getString("m_name"));
				teachplan.setMajor(major);
				OracleEduType edutype = new OracleEduType();
				edutype.setId(rs.getString("edutype_id"));
				edutype.setName(rs.getString("e_name"));
				teachplan.setEduType(edutype);
				OracleGrade grade = new OracleGrade();
				grade.setId(rs.getString("grade_id"));
				grade.setName(rs.getString("g_name"));
				teachplan.setGrade(grade);
				teachplancourse.setId(rs.getString("plancourse_id"));
				teachplancourse.setIsdegree(rs.getString("isdegree"));
				teachplancourse.setCredit(rs.getFloat("credit1"));
				teachplancourse.setCoursetime(rs.getFloat("course_time1"));
			 	teachplancourse.setType(type.typeShow(rs
			 			.getString("course_type1")));
				teachplancourse.setSemester(rs.getString("semester"));
				teachplancourse.setCourse(course);
				courseList.add(teachplancourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;
	}

	public List getOtherCourses(Page page, List searchProperty,
			List orderProperty) throws PlatformException {
		SearchProperty teachPlanId = (SearchProperty) searchProperty.get(0);
		searchProperty.remove(0);
		dbpool db = new dbpool();
		MyResultSet rs = null;
		ArrayList courseList = new ArrayList();
		String sql = "";
		sql = "select id,course_name,credit,course_time from "
				+ "(select c.id as id,c.name as course_name,c.credit as credit,c.course_time as course_time"
				+ " from entity_course_info c "
				+ "where c.course_status = '0000' and "
				+ "c.id not in (select c.course_id from entity_teachplan_info i,entity_teachplan_course c "
				+ "where i.id=c.teachplan_id and i.id='"
				+ teachPlanId.getValue()
				+ "')"
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty) + ")";
		try {
			if (page != null)
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			else
				rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleCourse course = new OracleCourse();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("course_name"));
				course.setCourse_time(rs.getString("course_time"));
				course.setCredit(rs.getString("credit"));
				courseList.add(course);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return courseList;
	}

	public int getCoursesNum(List searchProperty) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select id,plancourse_id,isdegree,name,credit,credit1,course_type,course_type1,semester,course_time,course_time1,major_id,edutype_id,grade_id,m_name,e_name,g_name,type,teachplan_id from"
				+ "(select t.id as plancourse_id,t.isdegree,c.id,c.name,c.credit,t.credit as credit1,t.type as course_type1,t.semester,c.course_type,c.course_time,t.coursetime as course_time1,i.id as teachplan_id,c.major_id,i.edutype_id,i.grade_id,m.name as m_name,e.name as e_name,g.name as g_name,t.type as type "
				+ "from entity_teachplan_info i,entity_teachplan_course t,entity_course_info c,entity_major_info m,entity_edu_type e,entity_grade_info g "
				+ "where i.id = t.teachplan_id and c.id = t.course_id and i.major_id = m.id and i.edutype_id = e.id and i.grade_id = g.id)";
		sql += Conditions.convertToCondition(searchProperty, null);
		return db.countselect(sql);
	}

	public int getOtherCoursesNum(List searchProperty) throws PlatformException {
		SearchProperty teachPlanId = (SearchProperty) searchProperty.get(0);
		searchProperty.remove(0);
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id,course_name,credit,course_time from "
				+ "(select c.id as id,c.name as course_name,c.credit as credit,c.course_time as course_time"
				+ " from entity_course_info c "
				+ "where c.course_status = '0000' and "
				+ "c.id not in (select c.course_id from entity_teachplan_info i,entity_teachplan_course c "
				+ "where i.id=c.teachplan_id and i.id='"
				+ teachPlanId.getValue() + "')"
				+ Conditions.convertToAndCondition(searchProperty) + ")";
		return db.countselect(sql);
	}

	/**
	 * Ϊѧƻӿγ public int addCourses(List courseList) throws PlatformException {
	 * dbpool db = new dbpool(); String sql = ""; sql = "insert into
	 * entity_teachplan_course (id,teachplan_id,course_id) select
	 * to_char(s_teachplan_course_id.nextval),'" + this.getId() + "',id from
	 * entity_teachplan_info where id in ("; for (int i = 0; i <
	 * courseList.size(); i++) { OracleCourse course = (OracleCourse)
	 * courseList.get(i); if (i < courseList.size()) { sql = sql + "'" +
	 * course.getId() + "',"; } else { sql = sql + "'" + course.getId() + "')"; }
	 * 
	 * int count = db.executeUpdate(sql); return count; }
	 */

	/** Ϊѧƻӿγ */
	public int addCourses(List courseList) throws PlatformException {
		int count = 0;
		for (int i = 0; i < courseList.size(); i++) {
			OracleTeachPlanCourse teachplancourse = new OracleTeachPlanCourse();
			OracleCourse course = (OracleCourse) courseList.get(i);
			teachplancourse.setCourse(course);
			count += teachplancourse.add(this.getId());
		}
		return count;
	}

	/** ӽѧƻɾγ */
	public int removeCourses(List courseList) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_teachplan_course where id in (";
		for (int i = 0; i < courseList.size(); i++) {
			OracleCourse course = (OracleCourse) courseList.get(i);
			if (i < courseList.size() - 1) {
				sql = sql + "'" + course.getId() + "',";
			} else {
				sql = sql + "'" + course.getId() + "')";
			}
		}
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTeachPlan.removeCourses(List courseList) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isExist() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_teachplan_info where major_id ='"
				+ this.getMajor().getId() + "' and edutype_id ='"
				+ this.getEduType().getId() + "' and grade_id ='"
				+ this.getGrade().getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public int isHasCourses() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_teachplan_course where teachplan_id ='"
				+ this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

	public List getTeachPlanCourses() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id,credit,coursetime,type,isdegree,course_id from entity_teachplan_course tc,entity_course_info ci where teachplan_id ='"
				+ this.getId() + "'";
		List list = new ArrayList();
		MyResultSet rs = null;
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				OracleTeachPlanCourse teachPlanCourse = new OracleTeachPlanCourse();
				teachPlanCourse.setId(rs.getString("id"));
				teachPlanCourse.setCredit(rs.getFloat("credit"));
				teachPlanCourse.setCoursetime(rs.getFloat("coursetime"));
				teachPlanCourse.setType(rs.getString("type"));
				teachPlanCourse.setIsdegree(rs.getString("isdegree"));
				OracleCourse course = new OracleCourse(rs
						.getString("course_id"));
				teachPlanCourse.setCourse(course);
				list.add(teachPlanCourse);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return list;
	}

	public void copyCourses(TeachPlan teachplan) throws PlatformException {
		List list = new ArrayList();
		list = teachplan.getCourses(null, null, null);
		this.addCourses(list);
	}

}
