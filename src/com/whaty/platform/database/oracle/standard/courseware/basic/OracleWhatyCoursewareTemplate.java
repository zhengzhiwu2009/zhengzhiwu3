package com.whaty.platform.database.oracle.standard.courseware.basic;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.basic.WhatyCoursewareTemplate;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleWhatyCoursewareTemplate extends WhatyCoursewareTemplate {

	public OracleWhatyCoursewareTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleWhatyCoursewareTemplate(String templateId) {
		super();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,found_date,founder_id,"
				+ "image_filename,active,note from courseware_template where id = '"
				+ templateId + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setFounderDate(rs.getString("found_date"));
				this.setFounderId(rs.getString("founder_id"));
				this.setImageFileName(rs.getString("image_filename"));
				if (rs.getInt("active") == 1)
					this.setActive(true);
				else
					this.setActive(false);
				this.setNote(rs.getString("note"));
			}
		} catch (java.sql.SQLException e) {
			Log
					.setError("OracleWhatyCoursewareTemplate(String templateId) error "
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}

	}

	public int add() throws PlatformException {
		String sql = "insert into courseware_template values(id,name,found_date,founder_id,"
				+ "image_filename) values('"
				+ this.getId()
				+ "','"
				+ this.getName()
				+ "',to_char(sysdate,'yyyy-mm-dd'),"
				+ "'"
				+ this.getFounderId() + "','" + this.getImageFileName() + ")";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleWhatyCoursewareTemplate.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		String sql = "delete from  courseware_template where id='"
				+ this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleWhatyCoursewareTemplate.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		String sql = "update courseware_template set name='" + this.getName()
				+ "'," + "found_date='" + this.getFounderDate() + "',"
				+ "founder_id='" + this.getFounderId() + "',"
				+ "image_filename='" + this.getImageFileName() + "' where id='"
				+ this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleWhatyCoursewareTemplate.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
