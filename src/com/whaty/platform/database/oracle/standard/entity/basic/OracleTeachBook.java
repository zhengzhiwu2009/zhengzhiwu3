package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.TeachBook;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleTeachBook extends TeachBook {
	public OracleTeachBook() {
	}

	public OracleTeachBook(String aid) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		sql = "select teachbook_name,publishhouse,maineditor,isbn,publish_date,price,note from entity_teachbook_info where id='"
				+ aid + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				this.setId(aid);
				this.setTeachbook_name(rs.getString("teachbook_name"));
				this.setPublishhouse(rs.getString("publishhouse"));
				this.setMaineditor(rs.getString("maineditor"));
				this.setIsbn(rs.getString("isbn"));
				this.setPublish_date(rs.getString("publish_date"));
				this.setPrice(rs.getString("price"));
				this.setNote(rs.getString("note"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleTeachBook(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_teachbook_info (id,teachbook_name,publishhouse,maineditor,isbn,publish_date,price,note) values ('"
				+ this.getId()
				+ "','"
				+ this.getTeachbook_name()
				+ "','"
				+ this.getPublishhouse()
				+ "','"
				+ this.getMaineditor()
				+ "','"
				+ this.getIsbn()
				+ "','"
				+ this.getPublish_date()
				+ "','"
				+ this.getPrice() + "','" + this.getNote() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTeachBook.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_teachbook_info set teachbook_name='"
				+ this.getTeachbook_name() + "',publishhouse='"
				+ this.getPublishhouse() + "',maineditor='"
				+ this.getMaineditor() + "',isbn='" + this.getIsbn()
				+ "',publish_date='" + this.getPublish_date() + "',price='"
				+ this.getPrice() + "',note='" + this.getNote()
				+ "' where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTeachBook.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_teachbook_info where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTeachBook.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int removeCourseFromTeachBook(String[] courseIdSet) {
		String courseIds = "";
		for (int i = 0; i < courseIdSet.length; i++)
			courseIds = courseIds + courseIdSet[i] + ",";
		if (courseIds.length() > 0)
			courseIds = courseIds.substring(0, courseIds.length() - 1);
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_teachbook_course where teachbook_id='"
				+ this.getId() + "' and course_id in (" + courseIds + ")";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTeachBook.removeCourseFromTeachBook(String[] courseIdSet) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int addCourseToTeachBook(String[] courseIdSet) {
		String courseIds = "";
		for (int i = 0; i < courseIdSet.length; i++)
			courseIds = courseIds + courseIdSet[i] + ",";
		if (courseIds.length() > 0)
			courseIds = courseIds.substring(0, courseIds.length() - 1);
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_teachbook_course(id,teachbook_id,course_id) select to_char(s_entity_teachbook_course_id.nextval),'"
				+ this.getId()
				+ "',id from entity_course_info where id in ("
				+ courseIds
				+ " ) and id not in (select course_id from entity_teachbook_course where teachbook_id='"
				+ this.getId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTeachBook.addCourseToTeachBook(String[] courseIdSet) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
