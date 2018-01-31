/**
 * 
 */
package com.whaty.platform.database.oracle.standard.resource.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.resource.basic.ResourceDir;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * @author wq
 * 
 */
public class OracleResourceDir extends ResourceDir {

	public OracleResourceDir() {

	}
	
	public OracleResourceDir(String id) {
		String sql = "select id,name,parent,note,status,isinherit,keyid from resource_dir where id='"
				+ id + "'";
		MyResultSet rs = null;
		dbpool db = new dbpool();
		rs = db.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setParent(rs.getString("parent"));
				this.setNote(rs.getString("note"));
				this.setStatus(rs.getString("status"));
				this.setIsInherit(rs.getString("isinherit"));
				this.setKeyId(rs.getString("keyid"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.resource.basic.ResourceDir#getSubDir()
	 */
	public List getSubDir() {
		String sql = "select id,name,parent,note,status,isinherit,keyid from resource_dir where parent='"
				+ this.getId() + "'";
		MyResultSet rs = null;
		dbpool db = new dbpool();
		rs = db.executeQuery(sql);
		List subDirList = new ArrayList();
		try {
			while(rs != null && rs.next()) {
				OracleResourceDir subDir = new OracleResourceDir();
				subDir.setId(rs.getString("id"));
				subDir.setName(rs.getString("name"));
				subDir.setParent(rs.getString("parent"));
				subDir.setNote(rs.getString("note"));
				subDir.setStatus(rs.getString("status"));
				subDir.setIsInherit(rs.getString("isinherit"));
				subDir.setKeyId(rs.getString("keyid"));

				subDirList.add(subDir);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return subDirList;
	}

	public List getResourceList() {
		String sql = "select id,title,language,description,keywords,creatuser,type_id,dir_id from resource_info where dir_id='"
				+ this.getId() + "'";
		MyResultSet rs = null;
		dbpool db = new dbpool();
		rs = db.executeQuery(sql);
		List resList = new ArrayList();
		try {
			while (rs != null && rs.next()) {
				OracleResource res = new OracleResource();
				res.setId(rs.getString("id"));
				res.setTitle(rs.getString("title"));
				res.setDiscription(rs.getString("description"));
				res.setKeyWords(rs.getString("keywords"));
				res.setCreatUser(rs.getString("creatuser"));
				OracleResourceType type = new OracleResourceType(rs
						.getString("type_id"));
				res.setResourceType(type);
				OracleResourceDir dir = new OracleResourceDir(rs
						.getString("dir_id"));
				res.setResourceDir(dir);

				resList.add(res);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return resList;
	}

	public int add() {
		String sql = "insert into resource_dir (id, name, parent, note, status, isinherit, keyId) values "
				+ "(to_char(s_resource_dir_id.nextval), '"
				+ this.getName()
				+ "','"
				+ this.getParent()
				+ "','"
				+ this.getNote()
				+ "','"
				+ this.getStatus()
				+ "','"
				+ this.getIsInherit()
				+ "','"
				+ this.getKeyId() + "')";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleResourceDir.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() {
		String sql = "update resource_dir set name='" + this.getName()
				+ "', parent='" + this.getParent() + "', note='"
				+ this.getNote() + "', status='" + this.getStatus()
				+ "', isinherit='" + this.getIsInherit() + "' where id='"
				+ this.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleResourceDir.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() {
		String sql = "delete from resource_dir  where id='" + this.getId()
				+ "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleResourceDir.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
