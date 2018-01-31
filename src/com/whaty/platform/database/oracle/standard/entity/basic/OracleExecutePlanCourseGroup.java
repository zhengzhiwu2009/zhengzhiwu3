/**
 * 
 */
package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.ExecutePlan;
import com.whaty.platform.entity.basic.ExecutePlanCourseGroup;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author wangqiang
 * 
 */
public class OracleExecutePlanCourseGroup extends ExecutePlanCourseGroup {
	public OracleExecutePlanCourseGroup() {

	}

	public OracleExecutePlanCourseGroup(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,title,executeplan_id,type,max,min from entity_executeplan_group where id='"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				ExecutePlan executePlan = new OracleExecutePlan(rs
						.getString("executeplan_id"));
				this.setExecutePlan(executePlan);
				this.setType(rs.getString("type"));
				this.setMax(rs.getInt("max"));
				this.setMin(rs.getInt("min"));
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
		sql = "insert into entity_executeplan_group (id,title,executeplan_id,type,max,min) values ("
				+ "to_char(s_executeplan_group_id.nextval), '"
				+ this.getTitle()
				+ "', '"
				+ this.getExecutePlan().getId()
				+ "', '"
				+ this.getType()
				+ "', '"
				+ this.getMax()
				+ "', '"
				+ this.getMin() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleExecutePlanCourseGroup.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_executeplan_group set title='" + this.getTitle()
				+ "', executeplan_id='" + this.getExecutePlan().getId()
				+ "', type='" + this.getType() + "',max='" + this.getMax()
				+ "',min='" + this.getMin() + "' where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleExecutePlanCourseGroup.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_executeplan_group where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleExecutePlanCourseGroup.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
}
