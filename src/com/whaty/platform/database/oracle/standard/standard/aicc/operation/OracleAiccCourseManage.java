package com.whaty.platform.database.oracle.standard.standard.aicc.operation;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.aicc.course.CourseTreeNode;
import com.whaty.platform.standard.aicc.operation.AiccCourseManage;
import com.whaty.platform.standard.aicc.util.AiccLog;

public class OracleAiccCourseManage extends AiccCourseManage {

	public List getCourseTree() {
		dbpool dbCourse = new dbpool();
		String sql = "";
		List courseTreeList = null;
		MyResultSet rs = null;
		try {
			courseTreeList = new ArrayList();
			sql = "select a.system_id,a.alevel,a.system_type,b.develop_id,b.title,b.description,c.afile from "
					+ "(select system_id,level as alevel,system_type from aicc_course_cst where course_id='"
					+ this.getCourseId()
					+ "' "
					+ "start with parent_id='root' CONNECT BY PRIOR system_id = parent_id) a,(select SYSTEM_ID,"
					+ "DEVELOP_ID,TITLE,DESCRIPTION,COURSE_ID from aicc_course_des "
					+ "where  course_id='"
					+ this.getCourseId()
					+ "') b,(select system_id,afile from aicc_course_au "
					+ "where course_id='"
					+ this.getCourseId()
					+ "') c where a.system_id=b.system_id(+)"
					+ "and a.system_id=c.system_id(+)";
//			AiccLog.setDebug(sql);
			rs = dbCourse.executeQuery(sql);
			while (rs != null && rs.next()) {
				CourseTreeNode node = new CourseTreeNode();
				node.setSystemId(rs.getString("system_id"));
				node.setDevelopId(rs.getString("develop_id"));
				node.setType(rs.getString("system_type"));
				node.setSequence(rs.getInt("alevel"));
				node.setTitle(rs.getString("title"));
				node.setUrl(rs.getString("afile"));
				node.setDescription(rs.getString("description"));
				courseTreeList.add(node);
			}
		} catch (java.sql.SQLException e) {
			AiccLog.setError("OracleAiccCourse@Method:getCourseTree()="+sql);
		} finally {
			dbCourse.close(rs);
			dbCourse = null;
			return courseTreeList;
		}
	}

}
