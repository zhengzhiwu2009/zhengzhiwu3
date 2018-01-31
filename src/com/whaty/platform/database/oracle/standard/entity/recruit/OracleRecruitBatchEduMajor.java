package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.entity.recruit.RecruitBatchEduMajor;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.util.log.Log;

public class OracleRecruitBatchEduMajor extends RecruitBatchEduMajor {

	public OracleRecruitBatchEduMajor() {
	}

	public List getMajors(String batchId, String edu_type_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "select m.id as major_id ,m.name as major_name from entity_major_info m,recruit_batch_edu_major r where m.id =r.major_id  and r.batch_id='"
				+ batchId
				+ "' and  r.edu_type_id='"
				+ edu_type_id
				+ "' order by m.id asc";
		try {
			rs = db.executeQuery(sql);

			while (rs != null && rs.next()) {
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				major.setName(rs.getString("major_name"));
				list.add(major);
			}
		} catch (Exception e) {
			EntityLog
					.setError(" OracleRecruitBatchEduMajor() getMajors() error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public List getMajors(Page page, String batchId, String edu_type_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "select m.id as major_id ,m.name as major_name from entity_major_info m,recruit_batch_edu_major r where m.id =r.major_id  and r.batch_id='"
				+ batchId
				+ "' and  r.edu_type_id='"
				+ edu_type_id
				+ "' order by m.id asc";
		try {
			if (page != null) {
				int pageInt = page.getPageInt();
				int pageSize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageInt, pageSize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				major.setName(rs.getString("major_name"));
				list.add(major);
			}
		} catch (Exception e) {
			EntityLog
					.setError(" OracleRecruitBatchEduMajor() getMajors() error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getMajorsNum(String batchId, String edu_type_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select m.id as major_id ,m.name as major_name from entity_major_info m,recruit_batch_edu_major r where m.id =r.major_id  and r.batch_id='"
				+ batchId + "' and  r.edu_type_id='" + edu_type_id + "'";
		int count = 0;
		count = db.countselect(sql);
		return count;
	}

	public int add() throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into recruit_batch_edu_major (id,batch_id,edu_type_id,major_id) values(to_char(S_RECRUIT_BATCH_EDU_MAJOR_ID.nextval),'"
				+ this.getBatch_id()
				+ "','"
				+ this.getEdu_type_id()
				+ "','"
				+ this.getMajor_id() + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleRecruitBatchEduMajor.add() SQL=" + sql
				+ " COUNT=" + i + " DATE=" + new Date());
		return i;

	}

	public int delete(String batchId, String edu_type_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_batch_edu_major where batch_id='" + batchId
				+ "' and edu_type_id='" + edu_type_id + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog
				.setDebug("OracleRecruitBatchEduMajor.delete(String batchId,String edu_type_id) SQL="
						+ sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int delete(String batchId, String edu_type_id, String major_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_batch_edu_major where batch_id='" + batchId
				+ "' and edu_type_id='" + edu_type_id + "' and major_id='"
				+ major_id + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog
				.setDebug("OracleRecruitBatchEduMajor.delete(String batchId,String edu_type_id,String major_id) SQL="
						+ sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int IsExsit(String batchId) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select *  from recruit_batch_edu_major where batch_id='"
				+ batchId + "'";
		int i = db.countselect(sql);
		return i;
	}

	public List getAddBatchMajors(Page page, String batchId, String edu_type_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "select id ,name from entity_major_info  where id<>'0' and id not in (select major_id  as id from recruit_batch_edu_major  where batch_id='"
				+ batchId
				+ "'  and edu_type_id='"
				+ edu_type_id
				+ "') order by id asc";
		EntityLog.setDebug(sql);
		try {
			if (page != null) {
				int pageInt = page.getPageInt();
				int pageSize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageInt, pageSize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("id"));
				major.setName(rs.getString("name"));
				list.add(major);
			}
		} catch (Exception e) {
			Log
					.setError(" OracleRecruitBatchEduMajor() getAddBatchMajors() error"
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getAddBatchMajorsNum(String batchId, String edu_type_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select id ,name from entity_major_info  where id<>'0' and id not in (select major_id  as id from recruit_batch_edu_major  where batch_id='"
				+ batchId + "'  and edu_type_id='" + edu_type_id + "')";
		EntityLog.setDebug(sql);
		int count = 0;
		count = db.countselect(sql);
		return count;
	}

	public List getBatchEduTypes(String batchId) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "select distinct b.id as edutype_id ,b.name as edutype_name from recruit_batch_edu_major a,  entity_edu_type b  where a.edu_type_id=b.id and a.batch_id='"
				+ batchId + "'";
		EntityLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);

			while (rs != null && rs.next()) {
				OracleEduType edu = new OracleEduType();
				edu.setId(rs.getString("edutype_id"));
				edu.setName(rs.getString("edutype_name"));
				list.add(edu);
			}
		} catch (Exception e) {
			Log.setError(" OracleRecruitBatchEduMajor() getMajors() error"
					+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public List getBatchMajors(String batchId, String edu_type_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "select m.id as major_id ,m.name as major_name from entity_major_info m,recruit_batch_edu_major r where m.id =r.major_id  and r.batch_id='"
				+ batchId
				+ "' and  r.edu_type_id='"
				+ edu_type_id
				+ "' and m.id not in(select major_id from recruit_plan_info where batch_id='"
				+ batchId
				+ "' and edutype_id='"
				+ edu_type_id
				+ "') order by m.id asc";
		EntityLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				major.setName(rs.getString("major_name"));
				list.add(major);
			}
		} catch (Exception e) {
			Log.setError(" OracleRecruitBatchEduMajor() getBatchMajors() error"
					+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public List getBatchEduTypeMajors(String batchId, String edu_type_id)
			throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "select m.id as major_id ,m.name as major_name from entity_major_info m,recruit_batch_edu_major r where m.id =r.major_id  and r.batch_id='"
				+ batchId
				+ "' and  r.edu_type_id='"
				+ edu_type_id
				+ "' order by m.id asc";
		EntityLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				major.setName(rs.getString("major_name"));
				list.add(major);
			}
		} catch (Exception e) {
			Log.setError(" OracleRecruitBatchEduMajor() getBatchMajors() error"
					+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public List getBatchMajors1(String batchId, String edu_type_id,
			String site_id) throws PlatformException {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List list = new ArrayList();
		String sql = "select m.id as major_id ,m.name as major_name from entity_major_info m,recruit_batch_edu_major r where m.id =r.major_id  and r.batch_id='"
				+ batchId
				+ "' and  r.edu_type_id='"
				+ edu_type_id
				+ "' and m.id not in(select major_id from recruit_plan_info where batch_id='"
				+ batchId
				+ "' and edutype_id='"
				+ edu_type_id
				+ "' and site_id='"
				+ site_id
				+ "' and status='1' ) order by m.id asc";
		EntityLog.setDebug(sql);
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleMajor major = new OracleMajor();
				major.setId(rs.getString("major_id"));
				major.setName(rs.getString("major_name"));
				list.add(major);
			}
		} catch (Exception e) {
			Log.setError(" getBatchMajors1() getBatchMajors() error" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

}
