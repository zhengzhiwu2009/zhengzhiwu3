/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.test.exam.OracleBasicSequence;
import com.whaty.platform.entity.basic.ExecutePlanCourse;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author wangqiang
 * 
 */
public class OracleExecutePlanCourse extends ExecutePlanCourse {
	public OracleExecutePlanCourse() {

	}

	public OracleExecutePlanCourse(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,group_id,teachplan_course_id,examsequence_id,exam_type from entity_executeplan_course where id='"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setGroup(new OracleExecutePlanCourseGroup(rs
						.getString("group_id")));
				this.setCourse(new OracleTeachPlanCourse(rs
						.getString("teachplan_course_id")));
				this.setSequence(new OracleBasicSequence(rs
						.getString("examsequence_id")));
				this.setExamType(rs.getString("exam_type"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleExecutePlanCourseGroup(String id) error: "
					+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_executeplan_course (id,group_id,teachplan_course_id,examsequence_id) values ("
				+ "to_char(s_executeplan_course_id.nextval), '"
				+ this.getGroup().getId()
				+ "', '"
				+ this.getCourse().getId()
				+ "', '" + this.getSequence().getId() + "')";
		EntityLog.setDebug("OracleExecutePlanCourse@Method:add()="+sql);
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleExecutePlanCourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_executeplan_course set group_id='"
				+ this.getGroup().getId() + "', teachplan_course_id='"
				+ this.getCourse().getId() + "', examsequence_id='"
				+ this.getSequence().getId() + "',exam_type='"
				+ this.getExamType() + "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExecutePlanCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_executeplan_course where id='" + this.getId()
				+ "'";
		EntityLog.setDebug("OracleExecutePlanCourse@Method:delete()="+sql);
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleExecutePlanCourse.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
