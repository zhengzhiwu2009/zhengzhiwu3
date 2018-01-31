package com.whaty.platform.database.oracle.standard.interaction.announce;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.interaction.announce.Announce;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleAnnounce extends Announce {
	public OracleAnnounce() {
	}

	public OracleAnnounce(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,title,body,publisher_id,publisher_name,publisher_type,publish_date,course_id"
				+ " from (select id,title,body,publisher_id,publisher_name,publisher_type,to_char(publish_date,'yyyy-mm-dd') as publish_date,course_id from interaction_announce_info where id = '"
				+ aid + "')";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setBody(rs.getString("body"));
				this.setPublisherId(rs.getString("publisher_id"));
				this.setPublisherName(rs.getString("publisher_name"));
				this.setPublisherType(rs.getString("publisher_type"));
				this.setDate(rs.getString("publish_date"));
				this.setCourseId(rs.getString("course_id"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleAnnounce(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		int id = -1;
		MyResultSet rs = null;
		sql = "select s_announce_info_id.nextval as id from dual";
		Log.setDebug("OracleAnnounce@Method:add()="+sql);
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
		}
		db.close(rs);

		sql = "insert into interaction_announce_info (id,title,body,publisher_id,publisher_name,publisher_type,course_id) values('"
				+ id
				+ "','"
				+ this.getTitle()
				+ "',?,'"
				+ this.getPublisherId()
				+ "','"
				+ this.getPublisherName()
				+ "','"
				+ this.getPublisherType()
				+ "','"
				+ this.getCourseId()
				+ "')";
		int i = db.executeUpdate(sql, this.getBody());
		UserAddLog.setDebug("OracleAnnounce.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (i > 0)
			return id;
		else
			return 0;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from interaction_announce_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleAnnounce.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update interaction_announce_info set title='" + this.getTitle()
				+ "',publish_date=sysdate,publisher_id='"
				+ this.getPublisherId() + "',publisher_name='"
				+ this.getPublisherName() + "',publisher_type='"
				+ this.getPublisherType() + "',course_id='"
				+ this.getCourseId() + "',body=? where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql, this.getBody());
		UserUpdateLog.setDebug("OracleAnnounce.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public void sendEmail() {
		// TODO 自动生成方法存根

	}

	public void sendSMS() {
		// TODO 自动生成方法存根

	}

	public void check() {
		// TODO 自动生成方法存根

	}

	public void uncheck() {
		// TODO 自动生成方法存根

	}

	public void putTop() {
		// TODO 自动生成方法存根

	}

	public void unPutTop() {
		// TODO 自动生成方法存根

	}

}
