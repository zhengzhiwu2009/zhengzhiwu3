package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.ExecutePlan;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleExecutePlan extends ExecutePlan {
	public OracleExecutePlan() {
	};

	public OracleExecutePlan(String semesterId, String teachPlanId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,title,semester_id,teachplan_id from entity_executeplan_info where semester_id = '"
				+ semesterId + "' and teachplan_id='" + teachPlanId + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this
						.setSemester(new OracleSemester(rs
								.getString("semester_id")));
				this.setPlan(new OracleTeachPlan(rs.getString("teachplan_id")));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleExecutePlan() error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public OracleExecutePlan(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,title,semester_id,teachplan_id from entity_executeplan_info where id='"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this
						.setSemester(new OracleSemester(rs
								.getString("semester_id")));
				this.setPlan(new OracleTeachPlan(rs.getString("teachplan_id")));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleExecutePlan(String aid) error " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_executeplan_info (id,title,semester_id,teachplan_id) values(to_char(s_executeplan_info_id.nextval),'"
				+ this.getTitle()
				+ "','"
				+ this.getSemester().getId()
				+ "','"
				+ this.getPlan().getId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleExecutePlan.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_executeplan_info set title='" + this.getTitle()
				+ "',semester_id='" + this.getSemester().getId()
				+ "',teachplan_id='" + this.getPlan().getId() + "' where id='"
				+ this.getId() + "'";
		EntityLog.setDebug("OracleExecutePlan@Method:update()="+sql);
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExecutePlan.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_executeplan_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleExecutePlan.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isExist() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from entity_executeplan_info where semester_id ='"
				+ this.getSemester().getId() + "' and teachplan_id='"
				+ this.getPlan().getId() + "'";
		int i = db.countselect(sql);
		return i;
	}

}
