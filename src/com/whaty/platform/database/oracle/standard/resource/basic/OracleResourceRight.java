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
import com.whaty.platform.resource.basic.ResourceRight;
import com.whaty.platform.resource.basic.ResourceUser;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;

/**
 * @author wq
 * 
 */
public class OracleResourceRight extends ResourceRight {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.resource.basic.ResourceRight#getDirUser(com.whaty.platform.resource.basic.ResourceDir)
	 */
	public List getDirUser(ResourceDir dir) {
		List userList = new ArrayList();
		String sql = "select id,login_id,password,name from resource_user where id in ("
				+ "select user_id from resource_right where dir_id='"
				+ dir.getId() + "')";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				OracleResourceUser user = new OracleResourceUser();
				user.setId(rs.getString("id"));
				user.setLoginId(rs.getString("login_id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				userList.add(user);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return userList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.resource.basic.ResourceRight#getUserDir(com.whaty.platform.resource.basic.ResourceUser)
	 */
	public List getUserDir(ResourceUser user) {
		List dirList = new ArrayList();
		String sql = "select id,name,parent,note,status,isinherit from resource_dir where id in ("
				+ "select dir_id from resource_right where user_id='"
				+ user.getId() + "')";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				OracleResourceDir dir = new OracleResourceDir();
				dir.setId(rs.getString("id"));
				dir.setName(rs.getString("name"));
				dir.setParent(rs.getString("parent"));
				dir.setNote(rs.getString("note"));
				dir.setStatus(rs.getString("status"));
				dir.setIsInherit(rs.getString("isinherit"));
				dirList.add(dir);
			}
		} catch (SQLException e) {
			
		} finally {
			db.close(rs);
		}
		return dirList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.resource.basic.ResourceRight#setUserDir(com.whaty.platform.resource.basic.ResourceUser,
	 *      com.whaty.platform.resource.basic.ResourceDir)
	 */
	public void setUserDir(ResourceUser user, ResourceDir dir) {
		String sql = "insert into resource_right(id,user_id,dir_id) values (to_char(s_resource_id.nextval),'"
				+ user.getId() + "','" + dir.getId() + "')";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleResourceRight.setUserDir(ResourceUser user, ResourceDir dir) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.resource.basic.ResourceRight#delUserDir(com.whaty.platform.resource.basic.ResourceUser,
	 *      com.whaty.platform.resource.basic.ResourceDir)
	 */
	public void delUserDir(ResourceUser user, ResourceDir dir) {
		String sql = "delete from resource_right where user_id='"
				+ user.getId() + "' and dir_id='" + dir.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleResourceRight.delUserDir(ResourceUser user, ResourceDir dir) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.resource.basic.ResourceRight#copyUserDir(com.whaty.platform.resource.basic.ResourceUser,
	 *      com.whaty.platform.resource.basic.ResourceUser)
	 */
	public void copyUserDir(ResourceUser srcUser, ResourceUser destUser) {
		String sql = "insert into resource_right (id,user_id,dir_id) select to_char(s_resource_right_id.nextval)," 
			+ destUser.getId() + " dir_id from resource_right where user_id='" + srcUser.getId() + "'";
		dbpool db = new dbpool();
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleResourceRight.copyUserDir(ResourceUser srcUser, ResourceUser destUser) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	}

}
