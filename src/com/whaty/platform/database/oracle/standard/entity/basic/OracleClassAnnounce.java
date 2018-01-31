/*
 * OracleClassAnnounce.java
 *
 * Created on 2005年5月8日, 上午10:39
 */

package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.Date;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.ClassAnnounce;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

/**
 *
 * @author Administrator
 */
public class OracleClassAnnounce extends ClassAnnounce {

	/** Creates a new instance of OracleClassAnnounce */
	public OracleClassAnnounce() {
	}

	public OracleClassAnnounce(String aid) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select title,body,class_id,to_char(time,'yyyy-mm-dd hh24:Mi:ss') as time from lrn_class_announce where id='"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setBody(rs.getString("body"));
				this.setTitle(rs.getString("title"));
				this.setClasse_id(rs.getString("class_id"));
				this.setTime(rs.getString("time"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("ClassAnnounce(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into lrn_class_announce (id,title,body,time,status,class_id) values (to_char(class_announce_seq.nextval),'"
				+ this.getTitle()
				+ "','"
				+ this.getBody()
				+ "',sysdate,'00000000','" + this.getClass_id() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleClasse.add() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from lrn_class_announce where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleClasse.add() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update lrn_class_announce set title='" + this.getTitle()
				+ "',body='" + this.getBody() + "' where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleClasse.update() SQL=" + sql + " COUNT" + i + " DATE=" + new Date());
		return i;
	}

}
