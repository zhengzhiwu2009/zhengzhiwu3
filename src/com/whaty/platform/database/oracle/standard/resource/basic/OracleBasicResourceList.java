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
import com.whaty.platform.resource.basic.BasicResourceList;
import com.whaty.platform.resource.basic.ResourceDir;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.XMLParserUtil;
import com.whaty.platform.util.log.UserAddLog;

/**
 * @author wq
 *
 */
public class OracleBasicResourceList implements BasicResourceList {
	
	private String SQLRESOURCE = "select id,title,language,description,keywords,creatuser,type_id, dir_id from resource_info ";
	private String SQLRESOURCETYPE = "select id,name,note,status,xml from resource_type ";
	private String SQLRESOURCEDIR = "select id,name,parent,note,status,isinherit,keyid from resource_dir ";

	/* (non-Javadoc)
	 * @see com.whaty.platform.resource.basic.BasicResourceList#getResourceTypesNum(java.util.List)
	 */
	public int getResourceTypesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLRESOURCETYPE + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.resource.basic.BasicResourceList#getResourcesNum(java.util.List)
	 */
	public int getResourcesNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLRESOURCE + Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.resource.basic.BasicResourceList#getResourceTypes(java.util.List, java.util.List)
	 */
	public List getResourceTypes(List searchproperty, List orderproperty) {
		List typeList = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLRESOURCETYPE + Conditions.convertToCondition(searchproperty, null);
		MyResultSet rs = null;
		rs = db.executeQuery(sql);
		try {
			while(rs != null && rs.next()) {
				OracleResourceType type = new OracleResourceType();
				type.setId(rs.getString("id"));
				type.setName(rs.getString("name"));
				type.setNote(rs.getString("note"));
				type.setStatus(rs.getString("status"));
				type.setResourceFieldList(XMLParserUtil.getResourceFieldList(rs.getString("xml")));
				typeList.add(type);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		
		return typeList;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.resource.basic.BasicResourceList#getResources(java.util.List, java.util.List)
	 */
	public List getResources(List searchproperty, List orderproperty) {
		List resList = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLRESOURCE + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		rs = db.executeQuery(sql);
		try {
			while(rs != null && rs.next()) {
				OracleResource res = new OracleResource();
				res.setId(rs.getString("id"));
				res.setTitle(rs.getString("title"));
				res.setLanguage(rs.getString("language"));
				res.setDiscription(rs.getString("description"));
				res.setKeyWords(rs.getString("keywords"));
				res.setCreatUser(rs.getString("creatuser"));
				res.setResourceType(new OracleResourceType(rs.getString("type_id")));
				res.setResourceDir(new OracleResourceDir(rs.getString("dir_id")));
				resList.add(res);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		
		return resList;
	}

	public List getResourceDirs(List searchproperty, List orderproperty) {
		List dirList = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLRESOURCEDIR + Conditions.convertToCondition(searchproperty, orderproperty);
		MyResultSet rs = null;
		rs = db.executeQuery(sql);
		try {
			while(rs != null && rs.next()) {
				OracleResourceDir dir = new OracleResourceDir();
				dir.setId(rs.getString("id"));
				dir.setName(rs.getString("name"));
				dir.setParent(rs.getString("parent"));
				dir.setNote(rs.getString("note"));
				dir.setStatus(rs.getString("status"));
				dir.setIsInherit(rs.getString("isinherit"));
				dir.setKeyId(rs.getString("keyid"));
				dirList.add(dir);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		
		return dirList;
	}

	public int setResourceRight(List userList, List resourceDirList) {
		dbpool db = new dbpool();
		List sqlList = new ArrayList();
		for(int i=0; i<userList.size(); i++) {
			String userId = (String)userList.get(i);
			for(int j=0; j<resourceDirList.size(); j++) {
				String dirId = (String)resourceDirList.get(j);
				String sql = "insert into resource_right(id, user_id, dir_id) values (to_char(s_resource_right_id.nextval), '"
					+ userId + "', '" + dirId + "')";
				sqlList.add(sql);
				UserAddLog.setDebug("OracleBasicResourceList.setResourceRight(List userList, List resourceDirList) SQL=" + sql + " DATE=" + new Date());
			}
		}
		
		return db.executeUpdateBatch(sqlList);
	}

	public List searchResourceDirListByUserId(String userId) {
		List dirList = new ArrayList();
		dbpool db = new dbpool();
		String sql = "select user_id,dir_id from resource_right where user_id='" + userId + "'";
		MyResultSet rs = db.executeQuery(sql);
		
		try {
			while(rs != null && rs.next()) {
				OracleResourceDir dir = new OracleResourceDir(rs.getString("dir_id"));
				dirList.add(dir);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return dirList;
	}
	
	public ResourceDir getResourceDirByKeyId(String keyId) {
		OracleResourceDir dir = new OracleResourceDir();
		dbpool db = new dbpool();
		String sql = "select id,name,parent,note,status,isinherit,keyid from resource_dir where keyid='"
			+ keyId + "'";
		MyResultSet rs = db.executeQuery(sql);
		
		try {
			if(rs != null && rs.next()) {
				dir.setId(rs.getString("id"));
				dir.setName(rs.getString("name"));
				dir.setParent(rs.getString("parent"));
				dir.setNote(rs.getString("note"));
				dir.setStatus(rs.getString("status"));
				dir.setIsInherit(rs.getString("isinherit"));
				dir.setKeyId(rs.getString("keyid"));
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return dir;
	}
}
