package com.whaty.platform.database.oracle.standard.info;

import java.util.Date;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.info.Template;
import com.whaty.platform.util.log.InfoLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleTemplate extends Template {

	/** Creates a new instance of OracleTemplate */
	public OracleTemplate() {
	}

	/**
	 * Creates a new instance of OracleTemplate with
	 * template id
	 */
	public OracleTemplate(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,content,type,put_type, note, mark from template where id = '"
				+ id + "'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setContent(rs.getString("content"));
				this.setType(rs.getString("type"));
				this.setNote(rs.getString("note"));
				this.setPub_type(rs.getString("pub_type"));
				this.setMark(rs.getString("mark"));
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("OracleSite(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public Template getTemplateByPub_type(String pub_type) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,content,type,pub_type, note, mark from template where pub_type = '"
				+ pub_type + "'";
		Template template = new OracleTemplate();
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				template.setId(rs.getString("id"));
				template.setName(rs.getString("name"));
				template.setContent(rs.getString("content"));
				template.setType(rs.getString("type"));
				template.setNote(rs.getString("note"));
				template.setPub_type(rs.getString("pub_type"));
				template.setMark(rs.getString("mark"));
			}
		} catch (java.sql.SQLException e) {
			InfoLog.setError("OracleSite(String aid) error" + sql);
			return null;
		} finally {
			db.close(rs);
			db = null;
		}
		return template;
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into template(id,name,content, type, put_type, note, mark) values('"
				+ this.getId()
				+ "','"
				+ this.getName()
				+ "','"
				+ this.getContent()
				+ "','"
				+ this.getType()
				+ "','"
				+ this.getPub_Type()
				+ "','"
				+ this.getNote()
				+ "','"
				+ this.getMark() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTemplate.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from template where id='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTemplate.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update template set name='" + this.getName()// 
				+ "',content='" + this.getContent()// 
				+ "',type='" + this.getType()//
				+ "',pub_type='" + this.getPub_Type()// 
				+ "',note='" + this.getNote() //
				+ "',mark='" + this.getMark() //
				+ "'";
		int i = db.executeUpdate(sql, this.getNote());
		UserUpdateLog.setDebug("OracleTemplate.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int isIdExist() {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select id from template where id = '" + this.getId() + "'";
		int i = db.countselect(sql);
		return i;
	}
}
