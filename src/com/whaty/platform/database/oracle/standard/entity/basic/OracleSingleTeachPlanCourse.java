package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.SingleTeachPlanCourse;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleSingleTeachPlanCourse extends SingleTeachPlanCourse {
	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		if (isDeletedCourse() > 0)
			sql = "delete from entity_singleteachplan_course where student_id='"
					+ this.getStudent().getId()
					+ "' and course_id='"
					+ this.getCourse().getId() + "' and status='0'";
		else
			sql = "insert into entity_singleteachplan_course (id,student_id,course_id,credit,type,isdegree,course_time,status) "
					+ "values( to_char(s_singleteachplan_course_id.nextval),'"
					+ this.getStudent().getId()
					+ "','"
					+ this.getCourse().getId()
					+ "','"
					+ this.getCourse().getCredit()
					+ "','"
					+ this.getCourse().getCourse_type()
					+ "','"
					+ this.getIsdegree()
					+ "','"
					+ this.getCourse().getCourse_timeString() + "','1')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleSingleTeachPlanCourse.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_singleteachplan_course set credit="
				+ this.getCredit() + ",coursetime=" + this.getCourseTime()
				+ ",type='" + this.getType() + "' where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSingleTeachPlanCourse.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		if (isAddedCourse() > 0)
			sql = "delete from entity_singleteachplan_course where student_id='"
					+ this.getStudent().getId()
					+ "' and course_id='"
					+ this.getCourse().getId() + "' and status='1'";
		else
			sql = "insert into entity_singleteachplan_course (id,student_id,course_id,status) "
					+ "values( to_char(s_singleteachplan_course_id.nextval),'"
					+ this.getStudent().getId()
					+ "','"
					+ this.getCourse().getId() + "','0')";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleSingleTeachPlanCourse.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isDeletedCourse() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_singleteachplan_course where student_id='"
				+ this.getStudent().getId() + "' and course_id='"
				+ this.getCourse().getId() + "' and status='0'";
		
		int i = db.executeUpdate(sql);
		return i;
	}

	public int isAddedCourse() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_singleteachplan_course where student_id='"
				+ this.getStudent().getId() + "' and course_id='"
				+ this.getCourse().getId() + "' and status='1'";
		int i = db.executeUpdate(sql);
		return i;
	}

}
