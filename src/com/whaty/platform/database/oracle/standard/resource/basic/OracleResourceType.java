/**
 * 
 */
package com.whaty.platform.database.oracle.standard.resource.basic;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.resource.basic.ResourceType;
import com.whaty.platform.util.XMLParserUtil;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;

/**
 * @author wq
 * 
 */
public class OracleResourceType extends ResourceType {
	public OracleResourceType() {

	}
	
	public OracleResourceType(String id) {
		String sql = "select id,name,note,status,xml from resource_type where id='" + id + "'";
		//System.out.println("SQL:" + sql);
		MyResultSet rs = null;
		dbpool db = new dbpool();
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setNote(rs.getString("note"));
				this.setStatus(rs.getString("status"));
				this.setResourceFieldList(XMLParserUtil.getResourceFieldList(rs
						.getString("xml")));

			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}

	public int add() {
		String sql = "insert into resource_type(id,name,note,status,xml) values (to_char(s_resource_type_id.nextval),'"
				+ this.getName()
				+ "','"
				+ this.getNote()
				+ "','"
				+ this.getStatus()
				+ "','"
				+ XMLParserUtil.getResourceFieldXML(this)
				+ "')";
		//System.out.println("SQL:" + sql);
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleResourceType.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() {
		String sql = "delete from resource_type where id='"
				+ this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleResourceType.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		String sql = "update resource_type set name='" + this.getName()
				+ "', note='" + this.getNote() + "', status='"
				+ this.getStatus() + "',xml='"
				+ XMLParserUtil.getResourceFieldXML(this) + "' where id='"
				+ this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleResourceType.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
