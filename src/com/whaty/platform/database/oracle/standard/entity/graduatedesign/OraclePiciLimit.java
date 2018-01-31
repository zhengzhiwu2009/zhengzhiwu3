package com.whaty.platform.database.oracle.standard.entity.graduatedesign;

import java.sql.SQLException;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleGrade;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.entity.graduatedesign.PiciLimit;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * 描述：某一毕业设计批次下的层次专业信息.
 * 
 * @author Administrator
 * 
 */
public class OraclePiciLimit extends PiciLimit {
    public OraclePiciLimit(){
    	
    }
	public OraclePiciLimit(String piciId) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
//		String sql = "select id, pici_name, grade_name, edutype_name, major_name, pici_status from (select egp.id,egc.name as pici_name,egi.name as grade_name,eet.name as edutype_name,emi.name as major_name,egc.status as pici_status from entity_graduate_picilimit egp,entity_grade_info egi,entity_edu_type eet,entity_major_info emi,entity_graduate_pici egc where egp.grade_id  = egi.id(+) and egp.edutype_id = eet.id(+) and egp.major_id = emi.id(+) and egp.pici_id  = egc.id(+))";
		String sql = "select id,pici_id,grade_id,edutype_id,major_id from entity_graduate_picilimit where pici_id ='"+piciId+"'";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleGraduatePici pici = new OracleGraduatePici(rs.getString("pici_id"));
				OracleMajor major = new OracleMajor(rs.getString("major_id"));
				OracleEduType eduType = new OracleEduType(rs.getString("edutype_id"));
				OracleGrade grade = new OracleGrade(rs.getString("grade_id"));
				this.setId(rs.getString("id"));
				this.setPiCi(pici);
				this.setEduType(eduType);
				this.setMajor(major);
				this.setGrade(grade);
			}
		} catch (SQLException es) {
			EntityLog
					.setDebug("OraclePiciLimit@Method:OraclePiciLimit(String piciId) error! sql="
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into entity_graduate_picilimit (id ,pici_id,grade_id,edutype_id,major_id) values (to_char(s_entity_graduate_picilimit_id.nextval),to_char('"
				+ this.getPiCi().getId()
				+ "'),to_char('"
				+ this.getGrade().getId()
				+ "'),to_char('"
				+ this.getEduType().getId()
				+ "'),to_char('"
				+ this.getMajor().getId() + "'))";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OraclePiciLimit.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update entity_graduate_picilimit set " +
				"pici_id =to_char('" + this.getPiCi().getId() 
				+ "'),grade_id=to_char('" + this.getGrade().getId()
				+ "'),edutype_id=to_char('"	+ this.getEduType().getId()
				+ "'),major_id=to_char('" + this.getMajor().getId()
				+ "') where id ='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OraclePiciLimit.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "delete from entity_graduate_picilimit where id ='" + this.getId()+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OraclePiciLimit.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}
	public int IsExsit(String batchId) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select *  from entity_graduate_picilimit where pici_id ='"
				+ batchId + "'";
		int i = db.countselect(sql);
		return i;
	}
	public int IsExistStudent(String id) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select * from entity_graduate_subjectsearch egs, entity_student_info esi where egs.student_id = esi.id and esi.grade_id||esi.edutype_id||esi.major_id in ( select egp2.grade_id||egp2.edutype_id||egp2.major_id from entity_graduate_picilimit egp2 where egp2.id ='"+id+"')";
		int i = db.countselect(sql);
		return i;
	}
	

}
