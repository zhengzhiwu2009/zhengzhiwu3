package com.whaty.platform.database.oracle.standard.entity.activity;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.courseware.basic.OracleCourseware;
import com.whaty.platform.entity.activity.TeachClassCware;
import com.whaty.platform.util.log.EntityLog;

public class OracleTeachClassCware extends TeachClassCware {

	public OracleTeachClassCware() {

	}

	public OracleTeachClassCware(String teachClass_id, String courseware_id){
		dbpool db = new dbpool();
		String sql = "select a.id as cware_id, a.teachclass_id, a.courseware_id, a.active," +
				"b.name as teachclass_name, c.name as courseware_name " +
				"from PR_TCH_OPENCOURSE_COURSEWARE a, entity_teach_class b, " +
				"entity_PE_TCH_COURSEWARE c where a.teachclass_id=b.id and " +
				"a.courseware_id=c.id and " +
				"b.id='" + teachClass_id + "' and " +
				"c.id='" + courseware_id + "'";
		MyResultSet rs = null;
		
		try{
//			InfoLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while(rs!=null && rs.next()){
				OracleTeachClass teachClass = new OracleTeachClass();
				OracleCourseware courseware = new OracleCourseware();
				teachClass.setId(rs.getString("teachClass_id"));
				teachClass.setName(rs.getString("teachClass_name"));
				this.setTeachClass(teachClass);
				courseware.setId(rs.getString("courseware_id"));
				courseware.setName(rs.getString("courseware_name"));
				this.setCourseware(courseware);
				this.setActive(rs.getString("active"));
			}
		}catch(java.sql.SQLException e){
			EntityLog.setError("OracleClasse(String aid) error " + sql);
		}finally{
			db.close(rs);
			rs = null;
		}
	}
	
	public OracleTeachClassCware(String teachClassCware_id) throws PlatformException{
		dbpool db = new dbpool();
		String sql = "select a.id as cware_id, a.teachclass_id, " +
				"a.courseware_id, a.active,b.name as teachclass_name, " +
				"c.name as courseware_name " +
				"from PR_TCH_OPENCOURSE_COURSEWARE a, entity_teach_class b, " +
				"entity_PE_TCH_COURSEWARE c where a.teachclass_id=b.id " +
				"and a.courseware_id=c.id and " +
				"a.id = '" + teachClassCware_id +"'";
		MyResultSet rs = null;
		
		try{
//			InfoLog.setDebug(sql);
			rs = db.executeQuery(sql);
			while(rs!=null && rs.next()){
				OracleTeachClass teachClass = new OracleTeachClass();
				OracleCourseware courseware = new OracleCourseware();
				teachClass.setId(rs.getString("teachclass_id"));
				teachClass.setName(rs.getString("teachclass_name"));
				this.setTeachClass(teachClass);
				courseware.setId(rs.getString("courseware_id"));
				courseware.setName(rs.getString("courseware_name"));
				this.setCourseware(courseware);
				this.setActive(rs.getString("active"));
			}
		}catch(java.sql.SQLException e){
			EntityLog.setError("OracleClasse(String aid) error " + sql);
		}finally{
			db.close(rs);
			rs = null;
		}
	}
}
