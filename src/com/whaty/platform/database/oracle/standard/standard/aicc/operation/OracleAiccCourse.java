package com.whaty.platform.database.oracle.standard.standard.aicc.operation;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.operation.AiccCourse;
import com.whaty.platform.standard.aicc.util.AiccLog;

public class OracleAiccCourse extends AiccCourse {
	
	
	public OracleAiccCourse() {
		this.setCourse(new Course());
		this.setCourseBehavior(new CourseBehavior());
		this.setCourseDes(new CourseDescription());
	}

	public OracleAiccCourse(String course_id) throws AiccException {
		this.setCourse(new Course());
		this.setCourseBehavior(new CourseBehavior());
		this.setCourseDes(new CourseDescription());
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		List courseList=new ArrayList();
		try {
			
			sql = "select ID,TITLE,SYSTEM_VENDOR,CREATER,ALEVEL,CST_MAXFIELDS,ORT_MAXFIELDS,TOTAL_AUS," +
				"TOTAL_BLOCKS, TOTAL_OBJECTIVES,TOTAL_COMPLEX_OBJECTIVES,VERSION,MAX_NORMAL,DESCRIPTION " +
				" from AICC_COURSE_INFO where  id='"+course_id+"'";
			AiccLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.getCourse().setCourseID(rs.getString("ID"));
				this.getCourse().setCourseTitle(rs.getString("TITLE"));
				this.getCourse().setCourseCreator(rs.getString("CREATER"));
				this.getCourse().setLevel(rs.getString("ALEVEL"));
				this.getCourse().setMaxFieldsCST(rs.getString("CST_MAXFIELDS"));
				this.getCourse().setMaxFieldsORT(rs.getString("ORT_MAXFIELDS"));
				this.getCourse().setTotalAus(rs.getString("TOTAL_AUS"));
				this.getCourse().setTotalBlocks(rs.getString("TOTAL_BLOCKS"));
				this.getCourse().setTotalObjectives(rs.getString("TOTAL_OBJECTIVES"));
				this.getCourse().setTotalComplexObjectives(rs.getString("TOTAL_COMPLEX_OBJECTIVES"));
				this.getCourse().setVersion(rs.getString("VERSION"));
				this.getCourseBehavior().setMaxNormal(rs.getString("MAX_NORMAL"));
				this.getCourseDes().setCourseDescription(rs.getString("DESCRIPTION"));
			}
		} catch (java.sql.SQLException e) {
			throw new AiccException("init aiccCourse error!");
		} finally {
			db.close(rs);
			db = null;

		}
	
	}
	
	public int add() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int update() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isExist() {
		String sql="select * from aicc_course_info where id='"+this.getCourse().getCourseID()+"'";
		dbpool db=new dbpool();
		if(db.countselect(sql)>0)
			return true;
		else
			return false;
	}

}
