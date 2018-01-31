/*
 * OracleTeachClass.java
 *
 * Created on 2005年5月17日, 下午9:24
 */

package com.whaty.platform.database.oracle.standard.entity.activity.score;

import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.activity.score.ScoreModify;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;

/**
 * 
 * @author Administrator
 */
public class OracleScoreModify extends ScoreModify {

	/** Creates a new instance of OracleTeachClass */
	public OracleScoreModify() {
	}

	public OracleScoreModify(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select id,elective_id,old_score,which_score,modify_score,status,teacher_name,modify_date,reason,student_id,teacher_id from entity_score_modifyrecord where id='"
					+ id + "'";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {

				ScoreModify modifyScore = (ScoreModify) new OracleScoreModify();
				modifyScore.setId(rs.getString("id"));
				modifyScore.setElective_id(rs.getString("elective_id"));
				modifyScore.setOld_score(rs.getFloat("old_score"));
				modifyScore.setType(rs.getString("which_score"));
				modifyScore.setStatus(rs.getString("status"));
				modifyScore.setNew_score(rs.getFloat("modify_score"));
				modifyScore.setTeacher_name(rs.getString("teacher_name"));
				modifyScore.setModifyDate(rs.getString("modify_date"));
				modifyScore.setModifyReason(rs.getString("reason"));
				modifyScore.setStudent_id(rs.getString("student_id"));
				modifyScore.setTeacher_id(rs.getString("teacher_id"));

			}
		} catch (java.sql.SQLException e) {
             EntityLog.setError("OracleScoreModify@Method:OracleScoreModify(String id) Error! (SQL = "+sql+")");
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public ScoreModify getScoreModify(String id) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		ScoreModify modifyScore = (ScoreModify) new OracleScoreModify();
		try {
			sql = "select id,elective_id,old_score,which_score,modify_score,status,teacher_name,modify_date,reason,student_id,teacher_id from entity_score_modifyrecord where id='"
					+ id + "'";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				modifyScore.setId(rs.getString("id"));
				modifyScore.setElective_id(rs.getString("elective_id"));
				modifyScore.setOld_score(rs.getFloat("old_score"));
				modifyScore.setType(rs.getString("which_score"));
				modifyScore.setStatus(rs.getString("status"));
				modifyScore.setNew_score(rs.getFloat("modify_score"));
				modifyScore.setTeacher_name(rs.getString("teacher_name"));
				modifyScore.setModifyDate(rs.getString("modify_date"));
				modifyScore.setModifyReason(rs.getString("reason"));
				modifyScore.setStudent_id(rs.getString("student_id"));
				modifyScore.setTeacher_id(rs.getString("teacher_id"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleScoreModify@Method:getScoreModify(String id) Error! (SQL = "+sql+")");
		} finally {
			db.close(rs);
			db = null;
		}
		return modifyScore;
	}

	public int updateScoreModify(String id, String elective_id,
			String old_score, String which_score, String modify_score,
			String status, String teacher_name, String modify_date,
			String reason, String student_id) {
		dbpool db = new dbpool();

		int count = 0;
		String sql = "";
		sql = "update  entity_score_modifyrecord set elective_id='"
				+ elective_id + "'  ,old_score='" + old_score
				+ "',which_score='" + which_score + "',modify_score='"
				+ modify_score + "',status='" + status + "',teacher_name='"
				+ teacher_name + "',modify_date='" + modify_date + "',reason='"
				+ reason + "',student_id='" + student_id
				+ "'  from entity_score_modifyrecord where id='" + id + "'";
		count = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleScoreModify.updateScoreModify(String id, String elective_id,String old_score, String which_score, String modify_score,String status, String teacher_name, String modify_date,String reason, String student_id) SQL=" + sql + " COUNT=" + count + " DATE=" + new Date());
		return count;
	}

	public int updateScoreModifyStatus(String id, String status) {
		dbpool db = new dbpool();

		int count = 0;
		String sql = "";
		sql = "update  entity_score_modifyrecord set status='" + status
				+ "' where id='" + id + "'";
		count = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleScoreModify.updateScoreModifyStatus(String id, String status) SQL=" + sql + " COUNT=" + count + " DATE=" + new Date());
		return count;
	}

	public int deleteScoreModify(String id) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from entity_score_modifyrecord where id='" + id + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleScoreModify.deleteScoreModify(String id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int addScoreModify(String elective_id, String old_score,
			String which_score, String modify_score, String status,
			String teacher_name, String reason, String student_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_score_modifyrecord (id,elective_id,old_score,which_score,modify_score,status,teacher_name,modify_date,reason,student_id) values(to_char(S_ENTITY_SCORE_MODIFYRECORD_ID.nextval),'"
				+ elective_id
				+ "','"
				+ old_score
				+ "','"
				+ which_score
				+ "','"
				+ modify_score
				+ "','"
				+ status
				+ "','"
				+ teacher_name
				+ "',sysdate,'" + reason + "','" + student_id + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleScoreModify.addScoreModify(String elective_id, String old_score,String which_score, String modify_score, String status,String teacher_name, String reason, String student_id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int addScoreModify(String elective_id, String old_score,
			String which_score, String modify_score, String status,
			String teacher_name, String reason, String student_id,
			String teacher_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into entity_score_modifyrecord (id,elective_id,old_score,which_score,modify_score,status,teacher_name,modify_date,reason,student_id,teacher_id) values(to_char(S_ENTITY_SCORE_MODIFYRECORD_ID.nextval),'"
				+ elective_id
				+ "','"
				+ old_score
				+ "','"
				+ which_score
				+ "','"
				+ modify_score
				+ "','"
				+ status
				+ "','"
				+ teacher_name
				+ "',sysdate,'"
				+ reason
				+ "','"
				+ student_id
				+ "','"
				+ teacher_id + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleScoreModify.addScoreModify(String elective_id, String old_score,String which_score, String modify_score, String status,String teacher_name, String reason, String student_id,String teacher_id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public ScoreModify getScoreModifyStatus(String elective_id,
			String which_score, String student_id) {

		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		String status = "";
		ScoreModify scoremodi = new ScoreModify();

		try {
			sql = "select id,status,modify_score,old_score,teacher_name,reason from entity_score_modifyrecord where elective_id='"
					+ elective_id
					+ "' and which_score='"
					+ which_score
					+ "' and student_id='" + student_id + "'";
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				scoremodi.setId(rs.getString("id"));
				scoremodi.setStatus(rs.getString("status"));
				scoremodi.setNew_score(rs.getFloat("modify_score"));
				scoremodi.setOld_score(rs.getFloat("old_score"));
				scoremodi.setTeacher_name(rs.getString("teacher_name"));
				scoremodi.setModifyReason(rs.getString("reason"));
			}
		} catch (java.sql.SQLException e) {
			EntityLog.setError("OracleScoreModify@Method:getScoreModifyStatus(String elective_id,String which_score, String student_id) Error! (SQL = "+sql+")");
		} finally {
			db.close(rs);
			db = null;
		}
		return scoremodi;
	}
}
