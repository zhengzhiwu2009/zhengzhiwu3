/**
 * 
 */
package com.whaty.platform.database.oracle.standard.standard.scorm.operation;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.operation.ScormCourse;
import com.whaty.platform.standard.scorm.util.ScormLog;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.SearchProperty;

/**
 * @author Administrator
 * 
 */
public class OracleScormCourse extends ScormCourse {

	public OracleScormCourse() {

	}

	public OracleScormCourse(String courseId) throws ScormException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = "select ID,TITLE,CONTROL_TYPE,VERSION,DESCRIPTION,navigate "
					+ " from SCORM_COURSE_INFO where  id='" + courseId + "'";
			ScormLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setCourseId(rs.getString("id"));
				this.setCourseTitle(rs.getString("title"));
				this.setControlType(rs.getString("control_type"));
				this.setVersion(rs.getString("version"));
				this.setDescription(rs.getString("description"));
				this.setNavigate(rs.getString("navigate"));
			}
		} catch (java.sql.SQLException e) {
			throw new ScormException("init ScormCourse error!");
		} finally {
			db.close(rs);
			db = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.standard.scorm.operation.ScormCourse#getItems()
	 */
	public List getItems() {
		OracleScormDataList dataList = new OracleScormDataList();
		List searchproperty = new ArrayList();
		List orderproperty = new ArrayList();
		searchproperty.add(new SearchProperty("course_id", this.getCourseId()));
		orderproperty.add(new OrderProperty("sequence"));
		return dataList.searchScormItems(null, searchproperty, orderproperty);
	}
	
	public List getScos() {
		OracleScormDataList dataList = new OracleScormDataList();
		List searchproperty = new ArrayList();
		List orderproperty = new ArrayList();
		searchproperty.add(new SearchProperty("course_id", this.getCourseId()));
		//query sco items only
		searchproperty.add(new SearchProperty("type", new String("sco")));
		orderproperty.add(new OrderProperty("sequence"));
		return dataList.searchScormItems(null, searchproperty, orderproperty);
	}
	
	public List getScos(String scormType) {
		OracleScormDataList dataList = new OracleScormDataList();
		List searchproperty = new ArrayList();
		List orderproperty = new ArrayList();
		searchproperty.add(new SearchProperty("course_id", this.getCourseId()));
		//query sco items only
		searchproperty.add(new SearchProperty("type", new String("sco")));
		orderproperty.add(new OrderProperty("sequence"));
		return dataList.searchScormItems(null, searchproperty, orderproperty,scormType);
	}
}
