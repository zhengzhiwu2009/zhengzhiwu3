package com.whaty.platform.database.oracle.standard.entity.basic;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.TeachPlanCourse;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleTeachPlanCourse extends TeachPlanCourse {
	public OracleTeachPlanCourse() {

	}

	public OracleTeachPlanCourse(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id,teachplan_id,course_id,credit,type,isdegree,coursetime,semester from entity_teachplan_course "
				+ "where id='" + id + "'";
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTeachPlan(new OracleTeachPlan(rs
						.getString("teachplan_id")));
				this.setCourse(new OracleCourse(rs.getString("course_id")));
				this.setCredit(Float.parseFloat(rs.getString("credit")));
				this.setType(rs.getString("type"));
				this.setIsdegree(rs.getString("isdegree"));
				this
						.setCoursetime(Float.parseFloat(rs
								.getString("coursetime")));
				this.setSemester(rs.getString("semester"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_teachplan_course (id,teachplan_id,course_id,type,isdegree,semester) values( to_char(s_teachplan_course_id.nextval),"
				+ this.getTeachPlan().getId()
				+ "','"
				+ this.getCourse().getId()
				+ "','"
				+ this.getCourse().getCourse_type()
				+ "','"
				+ this.getIsdegree() + "','" + this.getSemester() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTeachPlanCourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int add(String teachPlanId) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_teachplan_course (id,teachplan_id,course_id,type,isdegree,credit,coursetime,semester) values( to_char(s_teachplan_course_id.nextval),'"
				+ teachPlanId
				+ "','"
				+ this.getCourse().getId()
				+ "','"
				+ this.getCourse().getCourse_type()
				+ "','"
				+ this.getIsdegree()
				+ "','"
				+ this.getCourse().getCreditString()
				+ "','"
				+ this.getCourse().getCourse_time()
				+ "','"
				+ this.getCourse().getRedundance1() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTeachPlanCourse.add(String teachPlanId) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	/*
	 * public TeachPlan getTeachPlan() throws PlatformException { dbpool db =
	 * new dbpool(); String sql = ""; sql = "select teachplan_id from
	 * entity_teachplan_course where id ='" + this.getId() + "'"; MyResultSet rs =
	 * null; OracleTeachPlan teachplan = null; rs = db.executeQuery(sql); try {
	 * if (rs != null && rs.next()) { teachplan = new
	 * OracleTeachPlan(rs.getString("teachplan_id")); } } catch (SQLException e) {
	 *  } finally { db.close(rs); } return teachplan; }
	 */
	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_teachplan_course set credit=" + this.getCredit()
				+ ",coursetime=" + this.getCoursetime() + ",type='"
				+ this.getType() + "',semester='" + this.getSemester()
				+ "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTeachPlanCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		// TODO Auto-generated method stub
		return 0;
	}

}
