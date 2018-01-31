package com.whaty.platform.database.oracle.standard.entity.user;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.user.SiteTeacherLimit;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleSiteTeacherLimit extends SiteTeacherLimit {
    public OracleSiteTeacherLimit(){
    	
    }
    public OracleSiteTeacherLimit(String id){
    	dbpool db = new dbpool();
    	MyResultSet rs = null;
    	String sql = "select id,siteteacher_id,student_id from entity_graduate_siteteachlimit where id = '"+id+"'";
    	try{
    		rs = db.executeQuery(sql);
    		while(rs!=null&&rs.next()){
    			OracleSiteTeacher siteTeacher = new OracleSiteTeacher(rs.getString("siteteacher_id"));
    			OracleStudent student  = new OracleStudent(rs.getString("student_id"));
    			this.setSiteTeacher(siteTeacher);
    			this.setStudent(student);
    		}
    	}catch(SQLException es){
    		EntityLog.setError("OracleSiteTeacherLimit@Method:OracleSiteTeacherLimit(String id) error!!  sql="+sql);
    	}finally{
    		db.close(rs);
    		db =null;
    	}
    }
    public OracleSiteTeacherLimit(String id,String type){
    	dbpool db = new dbpool();
    	MyResultSet rs = null;
    	String sql = "";
    	if(type.equals("STUDENT")){
    		sql = "select id,siteteacher_id,student_id from entity_graduate_siteteachlimit where student_id = '"+id+"'";
    	}else {
    	    sql = "select id,siteteacher_id,student_id from entity_graduate_siteteachlimit where id = '"+id+"'";
    	}
    	try{
    		rs = db.executeQuery(sql);
    		while(rs!=null&&rs.next()){
    			this.setId(rs.getString("id")) ;
    			OracleSiteTeacher siteTeacher = new OracleSiteTeacher(rs.getString("siteteacher_id"));
    			OracleStudent student  = new OracleStudent(rs.getString("student_id"));
    			this.setSiteTeacher(siteTeacher);
    			this.setStudent(student);
    		}
    	}catch(SQLException es){
    		EntityLog.setError("OracleSiteTeacherLimit@Method:OracleSiteTeacherLimit(String id) error!!  sql="+sql);
    	}finally{
    		db.close(rs);
    		db =null;
    	}
    }
	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into entity_graduate_siteteachlimit (id,siteteacher_id,student_id) " +
				"values (to_char(s_entity_graduate_sthrlimit_id.nextval),to_char('" + this.getSiteTeacher().getId()
				+"'),to_char('" + this.getStudent().getId()
				+"'))";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleSiteTeacherLimit.add() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_siteteachlimit set siteteacher_id = to_char('" + this.getSiteTeacher().getId()
				+"'),student_id =to_char('" + this.getStudent().getId()
				+"') where id ='" + this.getId()
				+"'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleSiteTeacherLimit.update() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from entity_graduate_siteteachlimit where id = '"+this.getId()+"'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleSiteTeacherLimit.delete() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	
	public int isHaveTutorStudent(String id) throws PlatformException{
		String sql ="";
		int count =0;
		dbpool db= null;
			 db = new dbpool();
			sql = "select * from entity_graduate_siteteachlimit where siteteacher_id='" + id +"'";
			count = db.countselect(sql);
		return count;
	}
	 
}
