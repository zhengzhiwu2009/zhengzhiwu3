package com.whaty.platform.database.oracle.standard.resource.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.resource.basic.Resource;
import com.whaty.platform.util.XMLParserUtil;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleResource extends Resource {
	public OracleResource() {

	}
   
	public OracleResource(String id) {
		String sql = "select id,title,language,description,keywords,creatuser,type_id,dir_id,content,status from resource_info where id='"
				+ id + "'";
		MyResultSet rs = null;
		dbpool db = new dbpool();
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setTitle(rs.getString("title"));
				this.setLanguage(rs.getString("language"));
				this.setDiscription(rs.getString("description"));
				this.setKeyWords(rs.getString("keywords"));
				this.setCreatUser(rs.getString("creatuser"));
				OracleResourceType type = new OracleResourceType(rs
						.getString("type_id"));
				this.setResourceType(type);
				OracleResourceDir dir = new OracleResourceDir(rs
						.getString("dir_id"));
				this.setResourceDir(dir);

				this.setResourceContentList(XMLParserUtil.getResourceContentList(rs.getString("content")));
				
				this.setStatus(rs.getString("status"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}

	public int add() {
		dbpool db = new dbpool();
		String sql = "select id from resource_info where type_id='"+this.getResourceType().getId()+"' and dir_id='"+this.getResourceDir().getId()+"' and title='"+this.getTitle()+"'";
		if(db.countselect(sql) > 0)
			return -1;
		sql = "insert into resource_info(id,title,language,description,keywords,is_index_show,creatUser,type_id,dir_id,content) values (to_char(s_resource_info_id.nextval),'"
				+ this.getTitle()
				+ "','"
				+ this.getLanguage()
				+ "','"
				+ this.getDiscription()
				+ "','"
				+ this.getKeyWords()
				+ "','"
				+ this.getIs_index_show()
				+ "','"
				+ this.getCreatUser()
				+ "','"
				+ this.getResourceType().getId()
				+ "','"
				+ this.getResourceDir().getId()
				+ "',?)";
		int i = db.executeUpdate(sql, XMLParserUtil.getResourceXML(this));
		UserAddLog.setDebug("OracleResource.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() {
		String sql = "delete from resource_info where id='" + this.getId()
				+ "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleResource.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		String sql = "update resource_info set title='" + this.getTitle()
				+ "', language='" + this.getLanguage() + "', description='"
				+ this.getDiscription() + "', keywords='" + this.getKeyWords()
				+ "', creatuser='" + this.getCreatUser() + "', type_id='"
				+ this.getResourceType().getId() + "', dir_id='"
				+ this.getResourceDir().getId() + "',content='"
				+ XMLParserUtil.getResourceXML(this) + "', status='" + this.getStatus() + "' where id='"
				+ this.getId() + "'";

		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleResource.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getResourceContent() {
		String sql = "select content resource_info where id='" + this.getId()
				+ "'";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String contentXml = null;
		List resourceContentList = new ArrayList();
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				contentXml = rs.getString("content");
				resourceContentList = XMLParserUtil
						.getResourceContentList(contentXml);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}

		return resourceContentList;
	}
}
