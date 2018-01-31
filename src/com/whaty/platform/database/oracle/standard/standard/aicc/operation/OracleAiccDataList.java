package com.whaty.platform.database.oracle.standard.standard.aicc.operation;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.aicc.operation.AiccCourse;
import com.whaty.platform.standard.aicc.util.AiccLog;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.util.log.Log;

public class OracleAiccDataList {

	private String SQLAICCCOURSE = "select ID,TITLE,SYSTEM_VENDOR,CREATER,ALEVEL,"
			+ "to_char(CST_MAXFIELDS) as CST_MAXFIELDS,to_char(ORT_MAXFIELDS) as ORT_MAXFIELDS,"
			+ "to_char(TOTAL_AUS) as TOTAL_AUS,to_char(TOTAL_BLOCKS) as TOTAL_BLOCKS,"
			+ " to_char(TOTAL_OBJECTIVES) as TOTAL_OBJECTIVES,"
			+ "to_char(TOTAL_COMPLEX_OBJECTIVES) as TOTAL_COMPLEX_OBJECTIVES,"
			+ "VERSION,MAX_NORMAL,DESCRIPTION " + " from AICC_COURSE_INFO";

	public List searchCourses(Page page, List searchproperty, List orderproperty) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		List courseList = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = SQLAICCCOURSE + condition;
			AiccLog.setDebug(sql);
			if (page != null) {
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			} else {
				rs = db.executeQuery(sql);
				AiccLog.setDebug("no page!!!!");
			}
			while (rs != null && rs.next()) {
				AiccCourse course = new OracleAiccCourse();
				course.getCourse().setCourseID(rs.getString("id"));
				course.getCourse().setCourseTitle(rs.getString("title"));
				course.getCourse().setCourseSystem(
						rs.getString("system_vendor"));
				course.getCourse().setCourseCreator(rs.getString("creater"));
				course.getCourse().setLevel(rs.getString("alevel"));
				course.getCourse().setMaxFieldsCST(
						rs.getString("cst_maxfields"));
				course.getCourse().setMaxFieldsORT(
						rs.getString("ort_maxfields"));
				course.getCourse().setTotalAus(rs.getString("total_aus"));
				course.getCourse().setTotalBlocks(rs.getString("total_blocks"));
				course.getCourse().setTotalObjectives(
						rs.getString("total_objectives"));
				course.getCourse().setTotalComplexObjectives(
						rs.getString("TOTAL_COMPLEX_OBJECTIVES"));
				course.getCourseBehavior().setMaxNormal(
						rs.getString("max_normal"));
				course.getCourseDes().setCourseDescription(
						rs.getString("DESCRIPTION"));
				courseList.add(course);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("AiccDataList.searchCourses() error" + sql);
		} finally {
			db.close(rs);
			db = null;
			return courseList;
		}

	}
}
