package com.whaty.platform.database.oracle.standard.entity.activity;

import java.util.Date;
import java.util.Hashtable;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.activity.ChangeMajorApply;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

public class OracleChangeMajorApply extends ChangeMajorApply {

	public OracleChangeMajorApply() {

	}

	public OracleChangeMajorApply(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select a.id , a.student_id, j.name as student_name,j.reg_no, a.status, a.APPLY_DATE, a.old_major_id, a.new_major_id,a.old_grade_id, "
				+ " a.new_grade_id, a.old_edu_type, a.new_edu_type, a.old_site_id, a.new_site_id, b.name as old_site_name, "
				+ " c.name as new_site_name, d.name as old_grade_name, e.name as new_grade_name, f.name as old_edu_type_name,"
				+ " g.name as new_edu_type_name, h.name as old_major_name, i.name as new_major_name "
				+ " from (select id , student_id, status, APPLY_DATE, old_major_id, new_major_id,old_grade_id,"
				+ " new_grade_id, old_edu_type, new_edu_type, old_site_id, new_site_id from entity_change_major_apply "
				+ " where id = '"
				+ id
				+ "') a, entity_site_info b, entity_site_info c, entity_grade_info d, entity_grade_info e,"
				+ " entity_edu_type f, entity_edu_type g, entity_major_info h, entity_major_info i , entity_student_info j "
				+ " where b.id = a.old_site_id and c.id = a.new_site_id and d.id = a.old_grade_id and e.id=a.new_grade_id "
				+ " and f.id=a.old_edu_type and g.id=a.new_edu_type and h.id=a.old_major_id and i.id=a.new_major_id and j.id=a.student_id ";
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(id);
				this.setStudentId(rs.getString("student_id"));
				this.setStudent_name(rs.getString("student_name"));
				this.setStudent_reg_no(rs.getString("reg_no"));
				this.setStatus(rs.getString("status"));
				this.setApplyDate(rs.getString("apply_date"));
				this.setOldMajorId(rs.getString("old_major_id"));
				this.setNewMajorId(rs.getString("new_major_id"));
				this.setOldGradeId(rs.getString("old_grade_id"));
				this.setNewGradeId(rs.getString("new_grade_id"));
				this.setOldEduTypeId(rs.getString("old_edu_type"));
				this.setNewEduTypeId(rs.getString("new_edu_type"));
				this.setOldSiteId(rs.getString("old_site_id"));
				this.setNewSiteId(rs.getString("new_site_id"));
				this.setOldMajorName(rs.getString("old_major_name"));
				this.setNewMajorName(rs.getString("new_major_name"));
				this.setOldSiteName(rs.getString("old_site_name"));
				this.setNewSiteName(rs.getString("new_site_name"));
				this.setOldGradeName(rs.getString("old_grade_name"));
				this.setNewGradeName(rs.getString("new_grade_name"));
				this.setOldEduTypeName(rs.getString("old_edu_type_name"));
				this.setNewEduTypeName(rs.getString("new_edu_type_name"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleSite(String aid) error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_change_major_apply("
				+ " id , student_id, old_major_id, new_major_id,old_grade_id, "
				+ "new_grade_id, old_edu_type, new_edu_type, old_site_id, new_site_id) "
				+ " values (s_change_major_apply_id.nextval," + "'"
				+ this.getStudentId() + "','" + this.getOldMajorId() + "','"
				+ this.getNewMajorId() + "','" + this.getOldGradeId() + "','"
				+ this.getNewGradeId() + "','" + this.getOldEduTypeId() + "','"
				+ this.getNewEduTypeId() + "','" + this.getOldSiteId() + "','"
				+ this.getNewSiteId() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleChangeMajorApply.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_change_major_apply set " + "apply_date=sysdate, "
				+ "old_major_id='" + this.getOldMajorId() + "',"
				+ "new_major_id='" + this.getNewMajorId() + "',"
				+ "old_grade_id='" + this.getOldGradeId() + "',"
				+ "new_grade_id='" + this.getNewGradeId() + "',"
				+ "old_edu_type='" + this.getOldEduTypeId() + "',"
				+ "new_edu_type='" + this.getNewEduTypeId() + "',"
				+ "old_site_id='" + this.getOldSiteId() + "',"
				+ "new_site_id='" + this.getNewSiteId() + "'," + "status='"
				+ this.getStatus() + "' where id = '" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleChangeMajorApply.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int reject(String note) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update entity_change_major_apply set status='3',reject_note = '"
				+ note + "' where id ='" + this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleChangeMajorApply.reject(String note) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int changeStauts(String status) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		if ("2".equals(status)) {
			String upSql;
			String upSql1;
			String insSql;
			sql = "update entity_change_major_apply set status='" + status
					+ "' where id='" + this.getId() + "'";
			upSql = "update entity_elective set change_major='1111' where student_id='"
					+ this.getStudentId() + "'";
			upSql1 = "update entity_student_info set major_id='"
					+ this.getNewMajorId() + "',grade_id='"
					+ this.getNewGradeId() + "',edutype_id='"
					+ this.getNewEduTypeId() + "',site_id='"
					+ this.getNewSiteId() + "' where id='"
					+ this.getStudentId() + "'";
			insSql = "insert into entity_change_major (id,student_id,old_major_id,new_major_id,old_grade_id,"
					+ "new_grade_id,old_edu_type,new_edu_type,old_site,new_site,status,c_date) values"
					+ "(to_char(s_change_major_id.nextval),'"
					+ this.getStudentId()
					+ "','"
					+ this.getOldMajorId()
					+ "','"
					+ this.getNewMajorId()
					+ "','"
					+ this.getOldGradeId()
					+ "','"
					+ this.getNewGradeId()
					+ "','"
					+ this.getOldEduTypeId()
					+ "','"
					+ this.getNewEduTypeId()
					+ "','"
					+ this.getOldSiteId()
					+ "','" + this.getNewSiteId() + "','00000000',sysdate)";
			Hashtable ht = new Hashtable();
			ht.put(new Integer(1), sql);
			ht.put(new Integer(2), upSql1);
			ht.put(new Integer(3), insSql);
			int i = db.executeUpdateBatch(ht);
			UserUpdateLog.setDebug("OracleChangeMajorApply.changeStauts(String status) SQL1=" + sql + " SQL2=" + upSql + " COUNT=" + i + " DATE=" + new Date());
			UserAddLog.setDebug("OracleChangeMajorApply.changeStauts(String status) SQL=" + insSql + " COUNT=" + i + " DATE=" + new Date());
			return i;
		} else {
			sql = "update entity_change_major_apply set status='" + status
					+ "' where id='" + this.getId() + "'";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleChangeMajorApply.changeStauts(String status) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			return i;
		}
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_change_major_apply where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleChangeMajorApply.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

}
