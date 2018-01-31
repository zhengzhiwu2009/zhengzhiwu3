package com.whaty.platform.database.oracle.standard.courseware.basic;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.CoursewareType;
import com.whaty.platform.courseware.basic.WhatyUploadCourseware;
import com.whaty.platform.courseware.exception.CoursewareException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleDep;
import com.whaty.platform.entity.basic.Dep;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleWhatyUploadCourseware extends WhatyUploadCourseware {

	public OracleWhatyUploadCourseware(String coursewareId) {
		super();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		// sql = "select
		// ID,NAME,AUTHOR,PUBLISHER,NOTE,FOUNDER_ID,FOUND_DATE,COURSEWARE_TYPE,"
		// + "LINK,ACTIVE,courseware_dir ,OPENCOURSEDEPID from PE_TCH_COURSEWARE
		// where id = '"
		// + coursewareId + "'";
		sql = "select ID,NAME,AUTHOR,PUBLISHER,NOTE,FOUNDER_ID,FOUND_DATE,COURSEWARE_TYPE,LINK,ACTIVE,courseware_dir,OPENCOURSEDEPID,depname"
				+ " from (select ci.ID as id,ci.NAME as NAME,ci.AUTHOR as AUTHOR,ci.PUBLISHER as PUBLISHER,ci.NOTE as NOTE,ci.FOUNDER_ID as FOUNDER_ID,ci.FOUND_DATE as FOUND_DATE,ci.COURSEWARE_TYPE as COURSEWARE_TYPE, ci.LINK as LINK,ci.ACTIVE as ACTIVE,ci.courseware_dir as courseware_dir,ci.OPENCOURSEDEPID as OPENCOURSEDEPID,edi.name as depname from PE_TCH_COURSEWARE ci,entity_dep_info edi where ci.opencoursedepid = edi.id(+))"
				+ " where id = '" + coursewareId + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setAuthor(rs.getString("author"));
				this.setPublisher(rs.getString("publisher"));
				this.setFounderId(rs.getString("founder_id"));
				this.setFoundDate(rs.getString("found_date"));
				this.setCoursewareType(rs.getString("courseware_type"));
				this.setEnterFileName(rs.getString("link"));
				if (rs.getInt("active") == 1)
					this.setActive(true);
				else
					this.setActive(false);
				this.setCoursewareDirId(rs.getString("courseware_dir"));

				Dep dep = new OracleDep();
				dep.setId(rs.getString("OPENCOURSEDEPID"));
				dep.setName(rs.getString("depname"));
				this.setDep(dep);
			}
		} catch (java.sql.SQLException e) {
			Log
					.setError("OracleWhatyOnlineCourseware(String templateId) error "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		String sql = "update PE_TCH_COURSEWARE set LINK='"
				+ this.getEnterFileName() + "COURSEWARE_TYPE='"
				+ CoursewareType.UPLOADHTTP + "' where id='" + this.getId()
				+ "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSmsSystemPoint.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		OracleCourseware cw;
		try {
			cw = new OracleCourseware(this.getId());
		} catch (CoursewareException e) {
			throw new PlatformException(
					"oraclecourseware construct error!(coursewareid='"
							+ this.getId() + "'");
		}
		return cw.delete();
	}

	public int update() throws PlatformException {
		String sql = "update PE_TCH_COURSEWARE set LINK='"
				+ this.getEnterFileName() + "' " + "where id='" + this.getId()
				+ "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSmsSystemPoint.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
