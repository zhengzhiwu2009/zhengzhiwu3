package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.entity.recruit.RecruitMatricaluteCondition;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleRecruitMatricaluteCondition extends
		RecruitMatricaluteCondition {
	/** Creates a new instance of OracleRecruitMatricaluteCondition */
	public OracleRecruitMatricaluteCondition() {
	}

	private String sql_condition = "select id,batch_id,major_id,edutype_id,score,score1,photo_status,idcard_status,graduatecard_status from recruit_matriculate_condition";

	public OracleRecruitMatricaluteCondition(String aid) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = "select id,batch_id,major_id,edutype_id,score,score1,photo_status,idcard_status,graduatecard_status from recruit_matriculate_condition where id = '"
				+ aid + "'";
		EntityLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				this.setId(rs.getString("id"));
				OracleRecruitBatch recruitBatch = new OracleRecruitBatch();
				recruitBatch.setId(rs.getString("batch_id"));
				this.setBatch(recruitBatch);
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				this.setMajor(major);
				OracleEduType eduType = new OracleEduType();
				eduType.setId(rs.getString("edutype_id"));
				this.setEduType(eduType);
				this.setScore(rs.getString("score"));
				this.setScore1(rs.getString("score1"));
				this.setPhoto_status(rs.getString("photo_status"));
				this.setIdcard_status(rs.getString("idcard_status"));
				this
						.setGraduatecard_status(rs
								.getString("graduatecard_status"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleRecruitMatricaluteCondition(String aid) error"
					+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into recruit_matriculate_condition (id,batch_id,major_id,edutype_id,score,score1,photo_status,idcard_status,graduatecard_status) values(to_char(s_matriculate_condition_id.nextval),'"
				+ this.getBatch().getId()
				+ "','"
				+ this.getMajor().getId()
				+ "','"
				+ this.getEduType().getId()
				+ "','"
				+ this.getScore()
				+ "','"
				+ this.getScore1()
				+ "','"
				+ this.getPhoto_status()
				+ "','"
				+ this.getIdcard_status()
				+ "','"
				+ this.getGraduatecard_status() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitMatricaluteCondition.add() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int update() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_matriculate_condition set batch_id='"
				+ this.getBatch().getId() + "',major_id='"
				+ this.getMajor().getId() + "',edutype_id='"
				+ this.getEduType().getId() + "',score='" + this.getScore()
				+ "',score1='" + this.getScore1() + "',photo_status='"
				+ this.getPhoto_status() + "',idcard_status='"
				+ this.getIdcard_status() + "',graduatecard_status='"
				+ this.getGraduatecard_status() + "' where id='" + this.getId()
				+ "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleRecruitMatricaluteCondition.update() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_matriculate_condition where id='"
				+ this.getId() + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitMatricaluteCondition.delete() SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int add(String batch_id, String major_id, String edutype_id,
			String score) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into recruit_matriculate_condition (id,batch_id,major_id,edutype_id,score) values(to_char(s_matriculate_condition_id.nextval),'"
				+ batch_id
				+ "','"
				+ major_id
				+ "','"
				+ edutype_id
				+ "','"
				+ score + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitMatricaluteCondition.add(String batch_id,String major_id,String edutype_id,String score) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int deleteByMajorandBatch(String batch_id, String major_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_matriculate_condition where batch_id='"
				+ batch_id + "' and major_id='" + major_id + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleRecruitMatricaluteCondition.delete(String batch_id,String major_id) SQL=" + sql + "COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public String getIdByMajorandBatch(String batch_id, String major_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		String id = "";
		sql = "select id  from recruit_matriculate_condition where batch_id='"
				+ batch_id + "' and major_id='" + major_id + "'";
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				id = rs.getString("id");
			}
		} catch (Exception e) {
			
		}
		return id;
	}

	public List getMatricaluteCondition(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List recruitmat = null;

		sql_condition = sql_condition
				+ Conditions.convertToCondition(searchProperty, null);

		// System.out.println("sql_condition:"+sql_condition);

		try {

			recruitmat = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql_condition, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql_condition);
			}

			while (rs != null && rs.next()) {
				RecruitMatricaluteCondition recruitMatcon = new OracleRecruitMatricaluteCondition();

				recruitMatcon.setId(rs.getString("id"));
				OracleRecruitBatch recruitBatch = new OracleRecruitBatch();
				recruitBatch.setId(rs.getString("batch_id"));
				recruitMatcon.setBatch(recruitBatch);
				OracleMajor major = new OracleMajor(rs.getString("major_id"));
				// major.setId(rs.getString("major_id"));
				recruitMatcon.setMajor(major);
				OracleEduType eduType = new OracleEduType(rs
						.getString("edutype_id"));
				// eduType.setId(rs.getString("edutype_id"));
				recruitMatcon.setEduType(eduType);
				recruitMatcon.setScore(rs.getString("score"));
				recruitMatcon.setScore1(rs.getString("score1"));
				recruitMatcon.setPhoto_status(rs.getString("photo_status"));
				recruitMatcon.setIdcard_status(rs.getString("idcard_status"));
				recruitMatcon.setGraduatecard_status(rs
						.getString("graduatecard_status"));
				recruitmat.add(recruitMatcon);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleRecruitMatricaluteCondition error"
					+ sql_condition);
		} finally {
			db.close(rs);
			db = null;
		}
		return recruitmat;
	}

	public int getMatricaluteConditionNum(List searchProperty) {

		dbpool db = new dbpool();
		String sql = "";
		sql = sql_condition
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;

	}

}
