package com.whaty.platform.database.oracle.standard.entity.user;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleGrade;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.entity.user.TeacherLimit;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleTeacherLimit extends TeacherLimit {
	public OracleTeacherLimit() {

	}

	public OracleTeacherLimit(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select teacher_id,grade_id,edutype_id,major_id from entity_graduate_teacherlimit where id ='"+id+"'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
                OracleGrade grade = new OracleGrade(rs.getString("grade_id"));
                OracleMajor major = new OracleMajor(rs.getString("major_id"));
                OracleEduType eduType = new OracleEduType(rs.getString("edutype_id"));
			    OracleTeacher teacher = new OracleTeacher(rs.getString("teacher_id"));
			    this.setGrade(grade);
			    this.setEduType(eduType);
			    this.setMajor(major);
			    this.setTeacher(teacher);
			}
		} catch (java.sql.SQLException es) {
              EntityLog.setDebug("OracleTeacherLimit@Method:OracleTeacherLimit(String id)  sql="+sql);
		} finally {
            db.close(rs);
            db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into entity_graduate_teacherlimit (id,teacher_id,grade_id,edutype_id,major_id) values (to_char(s_entity_graduate_thrlimit_id.nextval),to_char('" +this.getTeacher().getId()
				+"'),to_char('" + this.getGrade().getId()
				+"'),to_char('" + this.getEduType().getId()
				+"'),to_char('" + this.getMajor().getId()
				+"'))";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleTeacherLimit.add() SQL=" + sql + " COUNT="
				+ i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_teacherlimit set teacher_id = to_char('" + this.getTeacher().getId()
				+"'),grade_id= to_char('" + this.getGrade().getId()
				+"'),edutype_id = to_char('" + this.getEduType().getId()
				+"'),major_id = to_char('" + this.getMajor().getId()
				+"') where id ='"+this.getId()+"'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleTeacherLimit.update() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from entity_graduate_teacherlimit where id = '"+this.getId()+"'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleTeacherLimit.delete() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int IsExsitGradeEduMajor(String teacherId) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select * from entity_graduate_teacherlimit where teacher_id ='"
				+ teacherId + "'";
		int i = db.countselect(sql);
		return i;
	}
	 
	
	 
}
