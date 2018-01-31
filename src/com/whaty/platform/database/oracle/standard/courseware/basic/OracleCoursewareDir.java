package com.whaty.platform.database.oracle.standard.courseware.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.courseware.basic.CoursewareDir;
import com.whaty.platform.courseware.exception.CoursewareException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.SearchProperty;
import com.whaty.util.log.Log;

public class OracleCoursewareDir extends CoursewareDir {

	public OracleCoursewareDir() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleCoursewareDir(String dirId) throws CoursewareException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,name,found_date,founder_id,parent_id,note from courseware_dir where id = '"
				+ dirId + "'";
		try {
//			CoursewareLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				this.setName(rs.getString("name"));
				this.setFounderId(rs.getString("founder_id"));
				this.setFoundDate(rs.getString("found_date"));
				this.setParentId(rs.getString("parent_id"));
				this.setNote(rs.getString("note"));

			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleCoursewareDir(String dirId) error " + sql);
			throw new CoursewareException(
					"OracleCoursewareDir(String dirId) error!");
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public List getCoursewareDirs() throws CoursewareException {
		dbpool db = new dbpool();
		String sql = null;
		MyResultSet rs = null;
		List dirList = new ArrayList();
		try {
			sql = "select * from courseware_dir where parent_id='"
					+ this.getId() + "'";
//			CoursewareLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleCoursewareDir dir = new OracleCoursewareDir();
				dir.setId(rs.getString("id"));
				dir.setName(rs.getString("name"));
				dir.setParentId(rs.getString("parent_id"));
				dirList.add(dir);
			}
		} catch (SQLException e) {
			throw new CoursewareException("getCoursewareDirs error!");
		} finally {
			db.close(rs);
			db = null;
		}
		return dirList;
	}

	public List getCoursewareDirs(String dirName) throws CoursewareException {
		dbpool db = new dbpool();
		String sql = null;
		MyResultSet rs = null;
		List dirList = new ArrayList();
		String con = "";
		if (dirName!=null&&!dirName.equals("")&&!dirName.equalsIgnoreCase("null")) {
			con += " and name like '%"+dirName +"%'";
		}
		try {
			sql = "select * from courseware_dir where parent_id='"
					+ this.getId() + "'" + con;
//			CoursewareLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleCoursewareDir dir = new OracleCoursewareDir();
				dir.setId(rs.getString("id"));
				dir.setName(rs.getString("name"));
				dir.setParentId(rs.getString("parent_id"));
				dirList.add(dir);
			}
		} catch (SQLException e) {
			throw new CoursewareException("getCoursewareDirs error!");
		} finally {
			db.close(rs);
			db = null;
		}
		return dirList;
	}

	
	public List getCoursewares() {
		OracleCoursewareList cwList = new OracleCoursewareList();
		List searchList = new ArrayList();
		List orderList = new ArrayList();
		searchList.add(new SearchProperty("courseware_dir", this.getId()));
		orderList.add(new OrderProperty("id"));
		return cwList.searchCourseware(null, searchList, orderList);
	}
	
	public List getCoursewares(String coursewareName) {
		OracleCoursewareList cwList = new OracleCoursewareList();
		List searchList = new ArrayList();
		List orderList = new ArrayList();
		if(coursewareName != null && !coursewareName.equalsIgnoreCase("null") && !coursewareName.equals("")) {
			searchList.add(new SearchProperty("name", coursewareName , "like"));
		}
		searchList.add(new SearchProperty("courseware_dir", this.getId()));
		orderList.add(new OrderProperty("id"));
		return cwList.searchCourseware(null, searchList, orderList);
	}


	public void moveCoursewares(List coursewareIds, String dirId) {
		// TODO Auto-generated method stub

	}

}
